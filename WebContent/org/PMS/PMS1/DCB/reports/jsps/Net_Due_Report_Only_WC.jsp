<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Calendar,java.sql.Connection" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Net Due Report ( Only Water Charges)</title>
 <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script> 
<script type="text/javascript">
function rld_new7()    
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("month").value;
	var res=month_year_check(smonth,mon);
	if (res!=1)
	{
		var rpt=document.getElementById("rpt4").value;
		var rtype=document.getElementById("rtype").value;
		if (monthchk(smonth,mon)!=1)  
		{    
			window.open("../../../../../../Pms_Dcb_Ledger_Report?month="+smonth+"&option="+rtype+"&year="+mon+"&pr="+rpt);
		}       
	}
}  
function rld_new1()
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("month").value;
	var rpt=document.getElementById("rpt1").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)  
	{   
		if(rpt==0)
		{
			alert("Select report type ");
		}else
		{	
		window.open("../../../../../../reg_menu_index?month="+smonth+"&option="+rtype+"&year="+mon+"&ref_sno="+rpt);
		}
	}    
}
function monthchk(a,b)
{
	if (a==0 || b==0)
	{
		alert("Select Month and Year ");
		return 1;
	}else
	{
	return 0;
	}
} 

</script>
</head>
<body>
<%
Controller obj=null;
Connection con;
try
{
	  obj=new  Controller(); 
	  con=obj.con();
	  obj.createStatement(con);
	  Calendar cal = Calendar.getInstance();
	  int day = cal.get(Calendar.DATE);
	  int month = cal.get(Calendar.MONTH) + 1;
	  int year = cal.get(Calendar.YEAR);	
	  int prv_month=0;
	  if (month==1) prv_month=12;
	  else
	  prv_month=month-1;   
	  int prv_year=year;
	  if (prv_month==12)  prv_year=year-1;
	  
	  	obj.createStatement(obj.con());
	  	String userid = (String) session.getAttribute("UserId");
		if (userid == null) {
	 	response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		
		String	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	// 	 String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID  from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		String Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		
%>	
<table align="center" width="75%" border="1" cellpadding="5" cellspacing="0">
<tr class="tdH">
	<td colspan="2" align="center">
			Net Due Report ( Only Water Charges) - <%=Office_Name %>
	</td>
</tr>  

	       <tr><td>&nbsp;Year</td>
	            <td><select id="year" name="year"  tabindex="5" onchange="report_period_verify(document.getElementById('month'),this)">
                <option value="0" selected="selected">Select</option>
                <%
                for (int j=year;j>=2015;j--)
             //   for (int j=year-6;j<=year;j++)
                {
                 if (j==prv_year)
                 {
                %><option value="<%=j%>" selected="selected"><%=j%></option><%} else { %>
                <option value="<%=j%>"><%=j%></option><%} %>
                <%} %>  
    </select> </td>
		</tr><tr> 
			<td width="20%"  align="left">&nbsp;Month</td><td> <select name="month" id="month" onchange="report_period_verify(this,document.getElementById('year'))">
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++)
	         	{
	         		if (i==prv_month) 
	         		{ 
	         		%><option value="<%=i%>" selected="selected"><%=amonth[i]%></option><%} else { %>
	         		  <option value="<%=i%>"><%=amonth[i]%></option><%} %>
	         	<%} %>
	          </select></td>
	       </tr>
		<tr>
				<td><label class="lbl"> Net Due Report Abstract </label>  </td>
				<td> 
						<select id="rpt4" name="rpt4" class='select'>
						    <option value="0">Select</option>						   
							<!-- <option value="1">Corporation  </option> -->
							<!-- <option value="2">Municipalities</option> -->
							<!-- <option value="3">Rural Town Panchayat</option> -->
							<!-- <option value="4">Urban Town Panchayat</option>  -->
							<!-- <option value="5">Village Panchayat</option>		 -->					
							<!-- <option value="0">----------------------In Lakhs------------------------</option>-->
							<option value="451">Corporation </option>
							<option value="452">Municipalities </option>
							<option value="453">Rural Town Panchayat </option>
							<option value="454">Urban Town Panchayat</option> 
							<option value="455">Village Panchayat </option>							
							<!-- <option value="0">----------------------Actual--------------------------</option>-->
							<!-- <option value="201">Corporation </option> -->
							<!-- <option value="202">Municipalities </option> -->
							<!-- <option value="203">Rural Town Panchayat </option> -->
							<!-- <option value="204">Urban Town Panchayat </option>  -->
							<!-- <option value="205">Village Panchayat </option>	 -->
						</select>  
			 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input  class="fb2"  type="button" value="Print" onclick="rld_new7()" id="b1" >
			</td>
			</tr>
			<tr>
				<td><label class="lbl"> Net Due Report</label>  </td>
				<td> 
						<select id="rpt1" name="rpt1" class='select'>  
						    <option value="0">Select</option>						     
						    <!-- <option value="0">---------------In Crores--------  </option>-->
							<!-- <option value="1">Corporation  </option>-->
							<!-- <option value="2">Municipalities</option>-->
							<!-- <option value="3">Rural Town Panchayat</option>-->
							<!-- <option value="4">Urban Town Panchayat</option> -->
							<!-- <option value="5">Village Panchayat</option>-->
							<!-- <option value="86">Village Panchayat (Block and Ben Wise) </option>-->
							<!-- <option value="0">---------------In Lakhs-----------  </option>-->
							<option value="321">Corporation </option>
							<option value="322">Municipalities </option>
							<option value="323">Municipalities III Grade</option>
							<option value="324">Rural Town Panchayat </option>
							<option value="325">Urban Town Panchayat</option> 
							<option value="326">Village Panchayat (Block and Beneficiary Wise )</option>
							<option value="327">Village Panchayat (Block Wise) </option>  
							<!-- <option value="0">------------Actual-----------</option>-->
							<!-- <option value="21">Corporation </option>-->
							<!-- <option value="22">Municipalities </option>-->
							<!-- <option value="23">Rural Town Panchayat </option>-->
							<!-- <option value="24">Urban Town Panchayat </option> -->
							<!-- <option value="25">Village Panchayat </option>	-->
							<!-- <option value="286">Village Panchayat (Block and Ben Wise) </option>-->
							<!-- <option value="0">---------District Out standing-----------</option>-->
							<!-- <option value="26">All Type -- ( Excluding VP - Cr)   </option> -->
							<!-- <option value="27">All Type -- ( Excluding VP - Lacs)</option>-->
							<!-- <option value="28">All Type -- ( Excluding VP -Actual)</option>-->
					 		<!-- <option value="0">-------------------------------------------</option>-->
						</select>
			 			&nbsp;&nbsp;&nbsp;&nbsp;<input  class="fb2"  type="button" value="Print" onclick="rld_new1()" id="b1" >
			</td>
			</tr>  
			<tr> 
     	<td colspan="2" align="left">  
       	<select id="rtype" name="rtype" style="width: 110pt;">
       						<option value="0">  Report View Type</option>
       						<option value="1" selected="selected">PDF </option>  
							<option value="2" >  Excel </option>							
							<option value="3">  HTML </option>  
		</select></td>     		
     </tr>
    <tr>
    		<td colspan="2" align="center">
    			<input type=button value="Exit" onclick="javascript:window.close()">
    		</td>
    </tr> 
	    
			</table>
			<%}catch(Exception e){} %>
</body>
</html>