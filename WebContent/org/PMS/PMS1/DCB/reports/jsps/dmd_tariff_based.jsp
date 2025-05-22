<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" errorPage="../../../../../../../DCBErrorPage.jsp"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<title>Insert title here</title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script> 
<script type="text/javascript">
	function rld()
	{
		var month =document.getElementById("month").value;
		var year =document.getElementById("year").value;
		var off_id =document.getElementById("off_id").value; 
		window.open("../../../../../../Water_Charges_Report?year="+year+"&month="+month+"&command=supply_abstract&off_id="+off_id); 
	}	
	function rld1()
	{
		var month =document.getElementById("month").value;
		var year =document.getElementById("year").value;
		var off_id =document.getElementById("off_id").value; 
		window.open("../../../../../../Water_Charges_Report?year="+year+"&month="+month+"&command=supply_details&off_id="+off_id);  
	}	
</script>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
</head>
<body>
<%
Controller obj=null;
Connection con;
String userid="",Office_id="",Office_name="",off_set="";
 try
 {
	  obj=new  Controller();  
	  con=obj.con();
	 
	  obj.createStatement(con);
	  Calendar cal = Calendar.getInstance();
	  int day = cal.get(Calendar.DATE);
	  int month = cal.get(Calendar.MONTH) + 1;
	  int year = cal.get(Calendar.YEAR);
	  
	     userid=(String)session.getAttribute("UserId");
		if(userid==null)  
		{ 
		 response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
	    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		if (Office_id.equalsIgnoreCase("5000")) off_set=""; else off_set=" and office_id="+Office_id;
	  String combo=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where office_level_id='DN' "+off_set+" and office_id in (select office_id from PMS_DCB_DIV_DIST_MAP) and Region_office_id <> 6777 ","off_id","style='width:180pt'");
	  String bentype=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID","where BEN_TYPE_ID<>19","BEN_TYPE_ID","style='width:80pt'");
	  int prv_year=obj.prv_year(year,month);
	  int prv_month=obj.prv_month(year,month);
%>
<table width="50%" align="center" border="1" bordercolor="skyblue" cellpadding="20" cellspacing="0" style="border-collapse: collapse; ">
		<tr class='tdH'>
			<td colspan="8" align="center">
				&nbsp;Supply and Demand with Tariff Rate   <br><%=Office_name %>
			</td>
		</tr>
		<tr>  
			
	     <td width="2%">&nbsp;Year</td>
	            <td width="5%">
	            <select id="year" name="year"  tabindex="5" onchange="report_period_verify(document.getElementById('month'),this)">
                <option value="0" selected="selected">Select</option>
                <% 
                for (int j=year-6;j<=year;j++) 
                {
                	if (j==prv_year)
     				{
                %>    
                	<option value="<%=j%>" selected><%=j%></option> 
                <%	}
                else {
                	%>    
                	<option value="<%=j%>"><%=j%></option> 
                	<%
                	}
     			} %>  
   			 </select>
   		</td>
   		<td width="2%"  align="left">&nbsp;Month</td><td>  
			 <select name="month" id="month" onchange="report_period_verify(this,document.getElementById('year'))">
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++)
	         	{
	         		if (i==prv_month) 
			 		{
	         	%>
	         	<option value="<%=i%>" selected><%=amonth[i]%></option>
	         	<%  }
	         		else
	         		{
	         	%>
	    	         	<option value="<%=i%>"><%=amonth[i]%></option>
	    	    <% 		
	         		}
	         	}
	         		%>
	          </select>
	         </td>
     <td width="2%">Office </td><Td width="5%"><%=combo%></Td>
    <!--<td width="15%">Beneficiary Type</td><Td width="5%"><%=bentype%></Td> -->    
	</tr>
	<tr>
		<td colspan="7" align="center">
			<input type="button" onclick="rld()" value="Abstract Report">		
			<input type="button" onclick="rld1()" value="Abstract Details"> 
		</td>
	</tr>
	<tr class='tdH'><td colspan="7" align="center">
	<input type="button" onclick="javascript:window.close()" value="Exit">
	</td></tr>
</table>	  
<%	 
 }catch(Exception e)
 {
	 
	 response.sendRedirect(request.getContextPath()+"/Logout.jsp");
	 
 }
%>
</body>
</html>