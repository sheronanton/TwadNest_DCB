<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="Servlets.AME.AME1.AMEM.servlets.*,java.util.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>A.M. Estimate Report</title>
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
</head>
<body>
<%
	java.text.DecimalFormat df =new java.text.DecimalFormat("0.00");
	Connection con=null;
try
{
	Calendar cal = Calendar.getInstance();
	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
	String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	 	Controller obj=new Controller();
	 	Controller obj2=new Controller();
	 	Controller obj3=new Controller();
		con=obj.con();
	 	obj.createStatement(con);
	 	obj2.createStatement(con);
	 	obj3.createStatement(con);
		 String	userid = (String) session.getAttribute("UserId");
		 String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		if (Office_id.equals("")) Office_id="5000";		
		String office_cond="";
		 
			if (Integer.parseInt(Office_id)==5000)
				office_cond=" (1=1) and  " ;  
			else   
				office_cond=" Office_id="+obj.setValue("Office_id",request)+" and ";// Office_id="+Office_id+" and ";
		String office_name=obj3.getValue("COM_MST_ALL_OFFICES_VIEW","office_name"," where Office_id="+Office_id);
		String fin_year=obj.setValue("fin_year",request);
		int r=0;
		 //String qry="select MAIN_ITEM_SNO,MAIN_ITEM_DESC from PMS_AME_MST_MAIN_ITEM order by Group_sno,MAIN_ITEM_SNO";
				 String table1="<table width='100%' align='center' >";
				table1+="<tr><td colspan='2'>&nbsp;</td>";
				//String table2="<table align='center' border='1' bordercolor='skyblue'  cellpadding='10' cellspacing='0'>";
				String table2="<tr><td width='3%' valign='top'>Sl.No</td><td width='10%' valign='top'>Scheme Name</td>";
			String qry=" SELECT a.main_item_sno as main_item_sno, " +
			"  a.main_item_desc as main_item_desc, " +
			"  b.sub_item_sno as sub_item_sno, " + 
			"  case when SUB_ITEM_SDESC='-' then b.sub_item_desc  else  b.SUB_ITEM_SDESC   end as sub_item_desc " +
			" FROM PMS_AME_MST_SUB_ITEM b, " +
			"  PMS_AME_MST_MAIN_ITEM a " +
			" WHERE a.main_item_sno=b.main_item_sno and  a.GROUP_SNO in (1,2) " +
			" ORDER BY a.group_sno, " + 
			"  a.main_item_sno,sub_item_sno";
		 PreparedStatement ps=con.prepareStatement(qry);
		 ResultSet rs=ps.executeQuery();
	String sel="",sely="";
%>   
<form action="./ame_new_reports.jsp" method="get">
<table align="center" border="1" width="100%">
		<tr><th  colspan="4" align='center'>Tamil Nadu Water Supply and Drainage Board</th></tr>
		<tr><th  colspan="4" align='center'><%=office_name%></th></tr>
		
		<tr>
      		<td width="25%">
      		      	<label class="fnt"> Financial Year</label> 
      		 &nbsp;: &nbsp;
	          <select class="select"  id="fin_year" name="fin_year"  style="width:120pt;  ">
			  <option value="0">Select</option>
			  <%
			  for (int i=2014;i<year+1;i++)
			  { 
			   %>
			  <option value="<%=i%>-<%=i+1%>"><%=i%>-<%=i+1%></option> 
			  <%} %>
			  </select> 
		 </td>
		 <td width="25%"><input type=submit value="View" name="b1"></td>
		 <td align="center" colspan="2"> 
		 	&nbsp; <label id='sel_year'><b><font size='2' color='blue'><%=(fin_year.equals("0")?"":" Annual Maintenance Estimate Abstract for Financial Year "+fin_year)%></font></b></label>
		 </td>
		 
		</tr>
</table>
<br>
			<%
			try
			{
				ArrayList main_=new ArrayList();
				ArrayList sub_=new ArrayList();				
				String msno="0";
				String ssno="0";
				int c1=obj2.getCount("PMS_AMT_ITEMS_COUNT","");
				int [][]csum= new int[30][c1];
				int []gnso=new int[c1];  
				int []msnoc=new int[c1];
				int []ssnoc=new int[c1];
				
				int row=0;
				int cols=1;
				String prv_msno="0";
				String main_desc="";
				String sub_desc="";
		 		while (rs.next())
		 		{
		 			row++;		 			
		 			msno=rs.getString("MAIN_ITEM_SNO");
		 			ssno=rs.getString("sub_item_sno");
		 			main_desc=rs.getString("MAIN_ITEM_DESC");
		 			int c=obj2.getCount("PMS_AME_MST_SUB_ITEM"," where main_item_sno="+msno);
		 		 
		 			sub_desc=rs.getString("sub_item_desc");
		 			gnso[row-1]=Integer.parseInt("1");
		 			msnoc[row-1]=Integer.parseInt(msno);
		 			ssnoc[row-1]=Integer.parseInt(ssno);
		 			if (Integer.parseInt(msno)!=Integer.parseInt(prv_msno))
		 			{ 
		 				table1+="<td align='center' width='25%' id=r"+row+" colspan="+c+" valign='top'>"+main_desc+"</td>";
		 				table2+="<td width='7%' valign='top'>"+sub_desc+"</td>";
		 				cols=1;
		 			}else
		 			{
		 				cols++;  
		 				table2+="<td width='7%' valign='top'>"+sub_desc+"</td>";
		 			}	 			
		 		 
		 			prv_msno=msno;
		 		}
		 		table1+="<th>Total</th></tr> "+table2+"<th>&nbsp;</th></tr> ";
		 	//	table1+=" </table>";
		 		String button_app=obj.setValue("b1",request);
		 		if (button_app.equals("View"))
		 		{
				 		int newrow=0;
				 		String sch_sno="0";
				 		String temp_sno="0";
				 		String qry_1=" select a.sch_name as sch_name,a.sch_sno as sch_sno from pms_sch_master a where  exists    (SELECT distinct sch_sno FROM PMS_AME_TRN_ABSTRACT where "+office_cond+" fin_year='"+fin_year+"' and sch_sno=a.sch_sno)";
				 		out.println(qry_1); 
				 		PreparedStatement ps1=con.prepareStatement(qry_1);
					 	ResultSet rs1=ps1.executeQuery();
					 	int erow=0;					 	
						double row_sum=0.0f;
					 	while(rs1.next())
					 	{
					 		erow++;
					 		sch_sno=rs1.getString("sch_sno"); 
					 		table1+="<tr><td valign='top'>"+erow+"</td><td width='10%' valign='top'>"+rs1.getString("sch_name")+"</td>";
					 		for(int i=0;i<row;i++)
					 		{
					 			String amt=obj3.getValue("PMS_AME_TRN_ABSTRACT","AM_EST_AMT"," where "+office_cond+"  sub_item_sno="+ssnoc[i]+" and main_item_sno="+msnoc[i]+" and fin_year='"+fin_year+"' and sch_sno="+sch_sno+"  ");
					 			if (!temp_sno.equals(sch_sno))
						 		{
						 			table1+="<td width='7%' align='right' valign='top'>"+df.format(Double.parseDouble(amt))+"</td>";
						 			row_sum+=Double.parseDouble(amt);
						 		} 
					 		}
					 		table1+="<th>"+df.format(row_sum)+"</th></tr>";
					 		row_sum=0.0f;
					 	} 
					 	 
		 		}
		 				table1+="<tr><td colspan='2' align='right'>Total";
			 	
			 			StringBuffer sum_qry=new StringBuffer();
			 			sum_qry.append(" select main_item_sno,sub_item_sno,group_sno,sum(am_est_amt) as amt from PMS_AMT_ESTIMATE_AMT where "+office_cond+"   (1=1) and fin_year='"+fin_year+"' " ); 
			 			sum_qry.append(" group by  main_item_sno,sub_item_sno,group_sno ");
			 			sum_qry.append(" order by  main_item_sno,sub_item_sno,group_sno " );
			 			ps=con.prepareStatement(sum_qry.toString());
			 			rs=null;
			 			double net_mat=0.0f;
			 			rs=ps.executeQuery();
			 			while(rs.next())
			 			{
			 				net_mat+=rs.getDouble("amt");
			 				table1+="<td width='7%' valign='top' align='right'>"+df.format(rs.getDouble("amt"))+"</td>";
			 			}
			 			
			 			table1+="<td align=right><b>"+df.format(net_mat)+"</b></td></tr> </table>";
		 	%>
		 	<%=table1%>
		 	<%
			}catch(Exception e) 
			{
				out.println(e);
				
			}
		 	%> 
		 	<a href="ame_new_reports_office.jsp">off</a>
		 	<table width='100%' align='center'  >
			<tr>    
				<th colspan="2" align="center">
				<input type="button" name="Report" value="Excel Download" onclick="window.open('ame_new_reports_excel.jsp?fin_year=<%=fin_year%>&office_id=<%=Office_id%>','Abstract ','width=400,height=200')">
				<input type="reset" value="Exit" onclick= "javascript:self.close()" />  </th>
			</tr> 
			</table>
<%
}catch(Exception e ) 
{
	out.println(e);
}
con.close();
	%>
</form>
</body>
</html>