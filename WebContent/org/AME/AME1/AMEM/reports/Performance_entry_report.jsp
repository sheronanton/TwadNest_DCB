<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>	
<%
/* 
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 28/01/2013
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 		  Type
 *-----------------------------------------------------------------------------
 * 25/01/2013	District wise Report in HO				Panneer Selvam.k	N
 *-----------------------------------------------------------------------------
 */
 

%>
 <html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scheme Performance Month Wise - Status</title>
</head>
<body>
<%
	String userid="",Office_id="",Office_Name="",smonth="",syear="",table_column="";
	String table_header="",table_heading="",html="";
	Controller obj=null;
	Connection con;
	String cond="",addcolumn="",addhead="",addcolumn1="",addset,head="";
try {
  	
	  obj=new Controller();  
	con = obj.con();
	obj.createStatement(con);
	try {
		userid = (String) session.getAttribute("UserId");
	} catch (Exception e) {
		userid = "0";
	}
	if (userid == null) {
		response.sendRedirect(request.getContextPath()+ "/index.jsp");
	}
	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",
			"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
					+ userid + "')");
	
	
	String month=obj.setValue("month",request);
	String year=obj.setValue("year",request);
	String mvalue=obj.month_val(month);
	if (!Office_id.trim().equalsIgnoreCase("0"))
		Office_id=Office_id;  
		addcolumn =" sname , cs   ";  
		head="Scheme Performance ";
		addhead="  Scheme Name,Scheme Performance Entry Status "; 
		addset="  'align=left width='25%','align=center  width='15%'' ";
		Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	");
		String qry="SELECT sch.sch_sno , sch.sname, mth.sch_sno,case when mth.c is null then ' - '"+
			" else  '  Completed'  End as cs "+
		" FROM "+
		" ( SELECT sch_sno , sch_name AS sname FROM pms_sch_master  WHERE sch_sno   IN "+
		" ( SELECT sch_sno FROM PMS_DCB_DIV_SCHEME_MAP WHERE office_id="+Office_id+"   ) "+
		" ) sch "+
		" LEFT JOIN "+
		"  ( SELECT DISTINCT sch_sno AS sch_sno,count(*) as c  FROM PMS_AME_TRN_SCH_PERFORM_MTH "+ 
		"  WHERE office_id  ="+Office_id+"  AND MONTH ="+month+" AND YEAR="+year+" HAVING COUNT (*) > 0 "+
		"  GROUP BY sch_sno,MONTH,YEAR  "+
		"  ) mth "+
			" ON mth.sch_sno=sch.sch_sno order by cs ASC ";	
		 
	table_column = addcolumn;	   	     
	table_header =addhead;
	String table_td_set =addset;
	table_heading = "   "+Office_Name+" <br> "+ head + "   "+mvalue+","+year;  
	String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
	html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);
	con.close();
	 
}catch(Exception e) 
{
out.println(e);	
}  
 


%>

<table align="center" width="75%" border=1>   
	<tr>	
		<td align="center"><%=table_heading %></td>
	</tr>
	
	<tr>
		<td> <%=html %></td>
	</tr>
	<tr> 
		<td align="center"><input  type=button value="Exit" onclick="window.close()" > </td>
	</tr>
</table>
</body>
</html>