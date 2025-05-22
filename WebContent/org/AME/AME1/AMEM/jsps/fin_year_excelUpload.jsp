<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*,java.io.*"%>
<%@page import="java.text.DecimalFormat"%><%@page import="java.util.Calendar" %>
<%@page import="Servlets.AME.AME1.AMEM.servlets.excel2"%><html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"  %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />
<title>Financial Year Data Upload </title>
<script type="text/javascript" src="./serv.js"></script>

<script type="text/javascript">
	function del()
	{ 
		document.location.href="fin_year_excelUpload.jsp?del=delete";
	}
	
</script>
</head>  
<body>    
<form action="fin_year_upload.jsp" method="POST" enctype="multipart/form-data">
<%
String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="",html="";
 Controller obj=new Controller(); 
 Connection con=obj.con();
 obj.createStatement(con);
 Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
 DecimalFormat df=new DecimalFormat();      
  
 String delete=obj.setValue("del",request);
 
 userid = (String) session.getAttribute("UserId");
 if (userid==null) userid="0";
 
	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals("")) Office_id="0";
  Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
	String msg=obj.isNull(obj.setValue("msg",request),3);
	if (msg.equals("0")) msg="";
%>
<table align="center" width="85%" border="1" cellpadding="5" style="border-collapse: collapse;border-color: skyblue" >
<tr  class = "tdH">
	<td colspan="2" align="center"> <%=Office_Name%></td>
</tr>
<tr  class = "tdH">  
	<td colspan="2" align="center">Upload Design and Pumped Qty -   Financial Year</td>
</tr>
<tr>
<td colspan="2">
  Select Excel File <input type="file" id="filename" name="filename" >
  <input type="submit" value="Upload">
  <input type="button" value="Delete" onclick="del()">
    <input type="button" value="Exit" onclick="javascript:window.close()">
  
 
 </td>  
</tr> 
<tr  class = "tdH"><td width=20%  >Uploaded Data </td><td align="right"><font color='green' size=4><b><%=msg%></b></font></td></tr> 
 <tr><td colspan="2">
 	 
 <%   
 	  out.println("<table border=1 width='100%'  style='border-collapse: collapse;border-color: skyblue'><tr><td>Sl.No</td><td>Scheme No</td><td width='10%'>TWAD Project ID</td><td>Scheme Name</td><td>Design Qty (MLD)</td><td>Pumped Qty (MLD)</td><td>Month </td><td>Year</td></tr>");
 	  String qry=" select OFFICE_ID,TWAD_SCH_SNO,SCH_SNO,DESIGN_QTY,PUMPING_QTY,MONTH,YEAR from PMS_AME_TRN_SCH_DPQTY where office_id="+Office_id +" order by SCH_SNO, YEAR,MONTH";
 	  if (delete.equalsIgnoreCase("delete"))
 	  {
 		  try  
 		  {
 		  String delrow=obj.delRecord("PMS_AME_TRN_SCH_DPQTY","where OFFICE_ID="+Office_id,con);
 		  
 		  out.println("<tr><td colspan=7><font color='red' size='3'>Record Deleted</font> </td></tr>");
 		  }catch(Exception e) {}
 	  }
 	  ResultSet rs=obj.getRS(qry);
 	  try 
 	  {
 		  int row=0;
 	 		 while (rs.next())  
 			 {
 					 String sch=rs.getString(3);   
 		 			 row++;
				 		out.println("<tr><td>"+row+"</td><td>"+sch+"</td>");
				 		out.println("<td>"+rs.getString(2)+"</td>");
				 		String schname=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+sch+ "  ");
				 		out.println("<td>"+schname+"</td>");
				 		out.println("<td>"+df.format(Double.parseDouble(rs.getString(4)))+"</td>");  
				 		out.println("<td>"+df.format(Double.parseDouble(rs.getString(5)))+"</td>");  
				 		out.println("<td>"+rs.getString(6)+"</td>");
				 		out.println("<td>"+rs.getString(7)+"</td></tr>");
 			}  
 	  out.println("</table>");
 	  }catch(Exception e)  {
 		//  out.println(e);
 	  }  
 %>
</td></tr></table>
 

 </form>
</body>
</html>