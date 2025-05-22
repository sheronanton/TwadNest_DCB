<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.log4j.Logger,Servlets.ASSET.ASSET1.ASSETM.servlets.Controller,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
 * 28/01/2013	District wise Report in HO				Panneer Selvam.k	N
 *-----------------------------------------------------------------------------
 */


%>
<%@ page import="java.sql.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Item Wise Expenditure   </title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function report_new( )
{ 
		
		var pyear1=document.getElementById("pyear").value;
			window.open("./Ame_Scheme_Abstract_DV.jsp?&pyear="+pyear1);
	  
	}
  
</script>  
</head>
<body>
   <%  Logger logger = Logger.getLogger("Ame_scheme_Item_Wise_DVUI.jsp"); %>
<%
try
{
	 
	logger.info("WELCOME");
	Calendar cal = Calendar.getInstance();
	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
	String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Connection con=obj.con();
	obj.createStatement(con);
	String	userid = (String) session.getAttribute("UserId");
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals("")) Office_id="0";
	System.out.println("Test");	
	con = null;  
	String sel="",sely="";
	
	int prvyear=obj.prv_year(year,month);
	int prvmonth=obj.prv_month(year,month); 
	int year_incr=0;
	  if (month <4)
		  year_incr=0;     
	  else
		  year_incr=1; 
%>
<form action="" method="get">

<table align="center" border="1" bordercolor="skyblue" style="width: 610pt;border-collapse: collapse;" cellpadding="10" cellspacing="0">
		<tr>
			<th colspan="2" align="center"  class = "tdH"> Item Wise Expenditure  </th>
		</tr> 
	<tr>  
		<td><label class="lbl">Financial Year</label></td>
		<td  >
		<select class="select" id="pyear" name="pyear" style="width: 80pt;"  >
		<option value="0">Select</option>
		<% for (int j=year-1;j<=year+year_incr;j++) {
		%>    
        <option value="<%=j-1%>-<%=j%>"><%=j-1%>-<%=j%></option>
        <%} %>
		</select></td>
		
	</tr> 
			 
		<tr>     
			<th colspan="2" align="center">
			<input type="button" value="Report"   onclick="report_new()">
			<input type="reset" value="Exit" onclick= "javascript:self.close()" />  </th>
		</tr>
		</table>
		</form>  
<%
}catch(Exception e ) 
{
System.out.println(e);	
}
%>
</body>
</html>