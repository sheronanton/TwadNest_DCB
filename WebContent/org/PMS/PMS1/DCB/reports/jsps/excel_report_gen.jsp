<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"  errorPage="../errorpage.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.*, java.util.*,dcb.reports.excel2,java.io.*"%><html>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="../css/txtbox.css" />
<link rel="stylesheet" href="../menu/menunew_files/css3menu1/style.css" type="text/css" />
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<body>
 
<div id="inner">
<table align="center" border="1" width="890" class="newtab">
<tr>
<td>
<%
try
{
	
%>
 
<%

		Calendar cs=Calendar.getInstance();

		int year=cs.get(Calendar.YEAR);
		int month=cs.get(Calendar.MONTH)+1;
		int day=cs.get(Calendar.DAY_OF_MONTH);
		
		
		String report_title="Report_"+day+"_"+month+"_"+year;
		
		
		Controller obj = new Controller();
		Connection con = obj.con();
		DatabaseMetaData mtdt = con.getMetaData();
		ResultSet rs = mtdt.getTables(con.getCatalog(), null, null,new String[] { "TABLE" });
		String flag=obj.setValue("flag",request);
		String spl=obj.setValue("spl",request);
		HttpSession session2=request.getSession(true);
		String report_type=(String)session2.getAttribute("report_type");;
		 if (report_type==null)
		  {
			  %>
			  	<script type="text/javascript">
			  		alert("Report Setting Not Yet to be done");
			  	</script>
			  <%	 
			  response.sendRedirect("../process/report_set.jsp");
		  }
		File f= new File(application.getRealPath("/excel"));
		if (!f.exists())
		{ 
			f.mkdir();
		} 
		if (spl.equalsIgnoreCase("SUBMIT"))
		{
			String tab_name=obj.setValue("tab",request);
			String selection_qry=obj.setValue("columns_list",request); 
			String columns_order_list=obj.setValue("columns_order_list",request);
			if (!columns_order_list.equalsIgnoreCase("0")) columns_order_list=" order by "+columns_order_list;
			 String query = "select "+selection_qry+" from "+tab_name+" "+columns_order_list;

			 excel2.excel_write(new File(application.getRealPath("/excel")),report_title+".xls",con,query);
		}
		response.sendRedirect("../excel_report.jsp");  
}catch(Exception e) { out.println(e);}    
%></td></tr></table>
 </div>
</body>

</html>