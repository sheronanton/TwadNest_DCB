<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.*" %>
<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<html>
<head>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen" />
 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
</head>
<body>
<%
	String table_head="";     
	try 
	{  
		Controller obj=new Controller(); 
		Connection con=obj.con();
		String b=obj.setValue("b1",request);
		StringBuffer qry=new StringBuffer();
		HttpSession session1 = request.getSession(false);
		String	userid = (String) session1.getAttribute("UserId");
		if (userid == null) {

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		//String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",			"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"					+ userid + "')");
		
			String Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		
		
		if (Office_id.equals("")) Office_id="0";
		
		String cond=Office_id.equalsIgnoreCase("5000")?"":" and office_id="+Office_id;
		
		if (b.equalsIgnoreCase("DCB Schemes"))
		{	
			qry.append("SELECT DISTINCT  " +
			"  a.office_id,(select office_name from com_mst_offices where office_id=a.office_id) as  office_name, " +
			"  a.SCHEME_SNO,(SELECT PROJECT_ID FROM pms_sch_master WHERE sch_sno=a.SCHEME_SNO) as project_id, (SELECT sch_name FROM pms_sch_master WHERE sch_sno=a.SCHEME_SNO " +
			"  ) AS sch_name, " +
			
"  (SELECT SCH_CATEGORY_ID FROM pms_sch_master WHERE sch_sno=a.SCHEME_SNO )AS SCH_CATEGORY_ID,              "+
"  (SELECT sch_major_type_id FROM pms_sch_master WHERE sch_sno=a.SCHEME_SNO )AS sch_major_type_id,          "+
"  (SELECT SCH_STATUS_ID FROM pms_sch_master WHERE sch_sno=a.SCHEME_SNO )AS SCH_STATUS_ID,     "+		
			
			
			"  sch_type_id,(SELECT sch_type_desc FROM pms_sch_lkp_type WHERE sch_type_id=a.sch_type_id) as sch_type_desc " +
			" FROM PMS_DCB_MST_BENEFICIARY_METRE a " +
			"WHERE SCHEME_SNO<>0  " +  
			" and meter_status='L' and scheme_sno in (select sch_sno from PMS_DCB_TRN_MONTHLY_PR where  year=2018 )  ORDER BY office_id, " +
			"  SCHEME_SNO");
			
			table_head=" List of Maintenance Schemes (DCB) - Maintained by TWAD";
		}
		else     
		{ 
			qry.append(" SELECT a.office_id,(SELECT office_name FROM com_mst_offices WHERE office_id=a.office_id) AS office_name, a.sch_sno, (SELECT PROJECT_ID FROM pms_sch_master WHERE a.sch_sno=sch_sno ) AS PROJECT_ID,(SELECT sch_name FROM pms_sch_master WHERE a.sch_sno=sch_sno ) AS sch_name ,(SELECT SCH_TYPE_ID FROM pms_sch_master WHERE a.sch_sno=sch_sno ) AS SCH_TYPE_ID, (SELECT sch_type_desc FROM pms_sch_lkp_type  WHERE sch_type_id=(SELECT SCH_TYPE_ID FROM pms_sch_master WHERE a.sch_sno=sch_sno )) AS sch_type_desc FROM PMS_sch_master a	WHERE SCH_CATEGORY_ID=4" +  cond );
			qry.append("order by office_id,sch_sno ");
			table_head=" List of Maintenance Schemes - Maintained by TWAD ";
		}
		//out.println(qry.toString());
	%>
		<table class="table" id="etable"  border="1"  align="center" width="100%" border="1" style="border-collapse: collapse;border-color: skyblue">  
		<tr>
			<th  class="tdH"  colspan="8"><font size=2><%=table_head %></font></th>
		</tr>  
		<%
		if (b.equalsIgnoreCase("DCB Schemes"))
		{
	  	response.setContentType("application/vnd.ms-excel");
		response.setHeader ("Content-Disposition", "attachment;filename=\""+table_head+".xls\""); 
		PreparedStatement pn_=con.prepareStatement(qry.toString());
			  Hashtable hashprocess=new Hashtable();
			  ResultSet local_rs=pn_.executeQuery(); 
			  ResultSetMetaData rsmd = local_rs.getMetaData();
			  String ColumnName ="";
			  String ColumnValue="";
			  out.println("  <br><font color='red' size='4'><center><b>Note: This List of schemes is as referred in Pr Transations in 2018 <b></center> </font><br>");
			  out.println("  <br><font color='red'><b>If a particular Scheme is Not in use then concerned division Must Delete Those Meter Entry in Beneficiary Meter Master after discussing with TWAD.HO about the Cancelled Scheme.<b></font> <br>");
			   int NumOfCol=rsmd.getColumnCount();
			  out.println("  <tr class='tdH' ><th>Sl.No</th>");
			  for (int i=1;i<=NumOfCol;i++)
			  {
				  ColumnName = rsmd.getColumnName(i);
				  out.println(" <th align='center'>"+ColumnName+"</th>");
			  }
			  out.println(" </tr>");			  
			  int row_count=0;
			  while (local_rs.next())
			  {
				  row_count++;
				  out.println("<tr><td>"+row_count+"</td>");
				  for (int i=1;i<=NumOfCol;i++)
				  {
					  ColumnName = rsmd.getColumnName(i);
					  ColumnValue=local_rs.getString(ColumnName);
					  out.println("<td>"+ColumnValue+"</td>");
				  }
				  out.println(" </tr>");
			  }
		}else
		{
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader ("Content-Disposition", "attachment;filename=\""+table_head+".xls\""); 
			PreparedStatement pn_=con.prepareStatement(qry.toString());
				  Hashtable hashprocess=new Hashtable();
				  ResultSet local_rs=pn_.executeQuery(); 
				  ResultSetMetaData rsmd = local_rs.getMetaData();
				  String ColumnName ="";
				  String ColumnValue="";
				
				   int NumOfCol=rsmd.getColumnCount();
				  out.println("  <tr class='tdH' ><th>Sl.No</th>");
				  for (int i=1;i<=NumOfCol;i++)
				  {
					  ColumnName = rsmd.getColumnName(i);
					  out.println(" <th align='center'>"+ColumnName+"</th>");
				  }
				  out.println(" </tr>");			  
				  int row_count=0;
				  while (local_rs.next())
				  {
					  row_count++;
					  out.println("<tr><td>"+row_count+"</td>");
					  for (int i=1;i<=NumOfCol;i++)
					  {
						  ColumnName = rsmd.getColumnName(i);
						  ColumnValue=local_rs.getString(ColumnName);
						  out.println("<td>"+ColumnValue+"</td>");
					  }
					  out.println(" </tr>");
				  }			
			
		}
			 %>  
	</table>	
	<%
	}catch(Exception e) 
	{
		out.println(e);		
	}
 	%>
 	</body>
</html>