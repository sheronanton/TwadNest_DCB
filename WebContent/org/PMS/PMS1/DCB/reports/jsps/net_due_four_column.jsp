<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Calendar"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../../css/txtbox.css" rel="stylesheet"	media="screen" />
<title>Region Wise Report | TWAD Phase2</title>
<link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script> 
<script type="text/javascript">
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
function rld_new7()    
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt4").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)  
	{   

		if (rpt==116)  
		{
			window.open("../../../../../../reg_menu_index?month="+smonth+"&option="+rtype+"&year="+mon+"&ref_sno="+(parseInt(rpt)+20));
		} 
		else
		{
			window.open("../../../../../../Pms_Dcb_Ledger_Report?month="+smonth+"&option="+rtype+"&year="+mon+"&pr="+(parseInt(rpt)+50));
		}
	}     
}  function rld_new5()    
{
	var mon=document.getElementById("year").value; 
	var smonth=document.getElementById("smonth").value;
	var rpt=document.getElementById("rpt5").value;
	var rtype=document.getElementById("rtype").value;
	if (monthchk(smonth,mon)!=1)  
	{   

		if (rpt==116)  
		{
			window.open("../../../../../../reg_menu_index?month="+smonth+"&option="+rtype+"&year="+mon+"&ref_sno="+(parseInt(rpt)+20));
		} 
		else
		{
			window.open("../../../../../../Pms_Dcb_Ledger_Report?month="+smonth+"&option="+rtype+"&year="+mon+"&pr="+(parseInt(rpt)+50));
		}
	}     
}  
</script>
</head>   
<body>
<form action="allben_with_met.jsp" method="get" name="myform">
<table  width="75%" align="center" border="1" cellspacing="0" cellpadding="10" style="BORDER-COLLAPSE: collapse"
	border="1" borderColor="#92c2d8">
	<Tr bgcolor="skyblue">
	<td colspan="4" align="center" ><font color="#003300"><b> Net Due Report ( Maintenance Charges,Water Charges and Interest)</b></font> </td></tr>
	<% 
	System.out.println("TEST"); 
	String new_cond=Controller.new_cond;
	Controller obj = new Controller();
	Calendar cal = Calendar.getInstance();
	int pr=Integer.parseInt( obj.setValue("process",request));
	int report=Integer.parseInt( obj.setValue("report",request));
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	int prv_month=0;
	if (month==1) prv_month=12;
	else
	prv_month=month-1;
	int prv_year=year;
	if (prv_month==12)  prv_year=year-1;
	String reg= obj.setValue("reg",request);
	String off_id= obj.setValue("off",request);
	String userid="0",Office_id="",Office_Name="";
	String Date_dis=day+"/"+month+"/"+year;
     Connection con=obj.con();
	 obj.createStatement(con);
	try { 
	 	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
		String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
				String prallow=obj.setValue("prallow",request);
				String mnth=obj.setValue("smonth",request);
				String syear=obj.setValue("year",request);
				String prset=obj.setValue("prset",request);
		%> 
				<tr>
					<td style="width: 326px"><label class="lbl"> Year &nbsp; &nbsp; &nbsp; &nbsp; </label>  
					       <select class="select" id="year" name="year" style="width: 110pt;" onchange="report_period_verify(document.getElementById('smonth'),this)" >
				    	   <option value="0">Select Year</option>			 
						 <% for (int i=year-6;i<=year;i++) 
						   { 
						   if (i==prv_year) {	%>  
						   <option value="<%=i%>" selected><%=i%></option>
						   <%  } else {%>  
						  <option value="<%=i%>"><%=i%></option>
						  <%}
				 			  }
						   %>    
							</select>
				      </td>
				      <td>Month  &nbsp; &nbsp; &nbsp; &nbsp;
					          <select class="select" id="smonth" name="smonth"  style="width: 110pt;" onchange="report_period_verify(this,document.getElementById('year'))">
							  <option value="0">-Select Month-</option>
					 		  	<% 	for (int j=1;j<=12;j++)	
				  				{
				  					if (j==prv_month) {	%>
				    			<option value="<%=j%>" selected><%=monthArr[j]%></option>
				  				<%  } else {%>
				  				<option value="<%=j%>" ><%=monthArr[j]%></option>
							  	<%
								  	} 
				  				}%>
							  </select>
				      </td>
				  </tr> 
			<tr>  
				<td><label class="lbl"> Net Due Report Abstract&nbsp;&nbsp;(Rs.In Lakhs) </label>  </td>
				<td>   
						<select id="rpt4" name="rpt4" class='select'>
				 			 <option value="0">Select</option>   				   
							<option value="206">Corporation Abstract </option>
							<option value="208">Municipalities Abstract </option>
							<option value="209">Rural Town Panchayat Abstract</option>
							<option value="210">Urban Town Panchayat Abstract</option>
							<option value="211">Village Panchayat Abstract</option>
						</select>  
			</td> 
			<td colspan="2">
						<input  class="fb2"  type="button" value="Print" onclick="rld_new7()" id="b1" >
			</td>
			</tr>
			<tr>   
				<td><label class="lbl"> Net Due Report Details&nbsp;&nbsp;(Rs.In Lakhs) </label>  </td>
				<td>  
						<select id="rpt5" name="rpt5" class='select'>
				 			 <option value="0">Select</option>   				     
		 					 <option value="207">Corporation Details</option>
		 					 <option value="212">Municipalities Details </option>
							 <option value="213">Rural Town Panchayats Details</option>
							 <option value="214">Urban Town Panchayats Details</option>
							 <option value="215">Village Panchayats Details</option>
						</select>
				</td> 
				<td colspan="2">
						<input  class="fb2"  type="button" value="Print" onclick="rld_new5()" id="b1" >
				</td>
		</tr>
		  
		 
	<tr> 
     	<td colspan="4" align="right">  
       	<select id="rtype" name="rtype" style="width: 110pt;">
       						<option value="0">  Report View Type</option>
       						<option value="1" selected="selected">PDF </option>  
							<option value="2" >  Excel </option>							
							<option value="3">  HTML </option>  
		</select></td>     		
     </tr>
	<tr>    
	<td colspan=4 align="center"><input type=button value="Exit" onclick="self.close()"></td></tr>	
	</table> 
		<%
			} catch (Exception e) {  
				out.println(e);
			}
		%>
		<% 
		con.close();
		%>
 
</form>
</body>
</html>