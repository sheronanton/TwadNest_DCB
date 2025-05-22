<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@page import="java.util.Calendar"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Schemewise Posted Data</title> 
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
     <script type="text/javascript" src="../../../../../Library/scripts/CalendarControl.js"></script>
<link href="../../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
 <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>

<script type="text/javascript">
function report_new_2()
{  
	var txtFrom_date1=document.getElementById("txtFrom_date1").value;
	var txtFrom_date2=document.getElementById("txtFrom_date2").value; 
	var reporttype =document.getElementById("reporttype").value;
	if (txtFrom_date1!="" && txtFrom_date2!=="" && reporttype!=="") 
	{
		var src="../../../../../../Water_Charges_Report?option="+reporttype+"&command=Date_Wise_Journal&date1="+txtFrom_date1+"&date2="+txtFrom_date2;
		window.open(src);
	}else 
	{  
		alert("Please Select Date");
	}
}
function Waterdetail()
{  
	
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value; 
	var rep =document.getElementById("rep").value;
	var divby =document.getElementById("repin").value;
	var dis =document.getElementById("dis").value;
	var src="../../../../../../Water_Charges_Report?command=Waterdetail&year="+year+"&month="+month+"&rep="+rep+"&divby="+divby+"&dis="+dis;
	window.open(src);
}
function Waterdetail1()
{  
	
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value; 
	var rep =document.getElementById("rep").value;
	var divby =document.getElementById("repin").value;
	var dis =document.getElementById("dis").value;
	var src="../../../../../../Water_Charges_Report?command=Waterdetail1&year="+year+"&month="+month+"&rep="+rep+"&divby="+divby+"&dis="+dis;
	window.open(src);
}
function Watercharge()
{  
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value; 
	var rep =document.getElementById("rep").value;
	var repin =document.getElementById("repin").value;
	
		var src="../../../../../../Water_Charges_Report?command=Watercharge&year="+year+"&month="+month+"&rep="+rep+"&repin="+repin;
		window.open(src);
	}

function Watercharge3()
{  
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value; 
	var rep =document.getElementById("rep").value;

		var src="../../../../../../Water_Charges_Report?command=Watercharge3&year="+year+"&month="+month+"&rep="+rep;
		window.open(src);
		
	}
	
function Watercharge66()
{  
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value; 
	var repin =document.getElementById("repin").value;
	
	
//var src="../../../../../../Water_Charges_Report?command=Watercharge66&year="+year+"&month="+month+"&rep="+rep;
var src="../../../../../../Water_Charges_Report?command=Ldata&year="+year+"&month="+month+"&rep="+rep+"&repin="+repin;

	
//	var src="../../../../../../Water_Charges_Report?command=Watercharge66";
	window.open(src);
		
	}
	
	
function Watercharge4()
{  
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value; 
	var rep =document.getElementById("rep").value;
	var repin =document.getElementById("repin").value;

	//	var src="../../../../../../Water_Charges_Report?command=Watercharge4&year="+year+"&month="+month+"&rep="+rep+"&repin="+repin;
		var src="../../../../../../Water_Charges_Report?command=Ldata&year="+year+"&month="+month+"&rep="+rep+"&repin="+repin;

		window.open(src);
		
	}	
	

function Watercharge2()
{  
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value; 
	var rep =document.getElementById("rep").value;
	var repin =document.getElementById("repin").value;
	
		var src="../../../../../../Water_Charges_Report?command=Watercharge2&year="+year+"&month="+month+"&rep="+rep+"&repin="+repin;
		window.open(src);
	}

function Watercharge1()
{  
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value; 
	var rep =document.getElementById("rep").value;
	var repin =document.getElementById("repin").value;
		
		var src="../../../../../../Water_Charges_Report?command=Watercharge1&year="+year+"&month="+month+"&rep="+rep+"&repin="+repin;
		window.open(src);
	
}

function report_new()
{  	 
	var txtFrom_date1=new String(document.getElementById("txtFrom_date1").value);
	var txtFrom_date2=new String(document.getElementById("txtFrom_date2").value); 
	var reporttype =document.getElementById("reporttype").value;

	var dd=txtFrom_date1.split('/');
	var dd_1=new Date();
	
	dd_1.setFullYear(parseInt(dd[2]),parseInt(dd[1]),parseInt(dd[0]));
	
 
	if (txtFrom_date1!="" && txtFrom_date2!=="" && reporttype!=="") 
	{
		var src="../../../../../../Water_Charges_Report?option="+reporttype+"&command=Date_Wise_Collection&date1="+txtFrom_date1+"&date2="+txtFrom_date2;
		window.open(src);
	}else
	{
		alert("Please Select Date");
	}
}
function report(a,b)
{
	var year=document.getElementById("year").value;
	var month=document.getElementById("month").value;
	var res=month_year_check(month,year);

	var src="";

	if (parseInt(b)==1)
	src="../../../../../../Water_Charges_Report?option=2&month="+month+"&year="+year+"&command="+a;
	else if (parseInt(b)==2)
	 src="../../../../../../Bill_Demand?command=mdrpt&process_code=333&option_pdf_xls=2&fmonth="+month+"&fyear="+year;
	else
		src="../../../../../../Water_Charges_Report?option=1&pmonth="+month+"&pyear="+year+"&command="+a;
		window.open(src);  
}
</script>
</head>
<body>

<%		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		int year_incr=0;
		if (month <4)
			  year_incr=0;    
		  else
			  year_incr=1;    
		 int prv_month=0;
		  if (month==1) prv_month=12;
		  else
		  prv_month=month-1;   
		  int prv_year=year;
		  if (prv_month==12)  prv_year=year-1;
		  String userid="",Office_id="0";
		  try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {
			}
			System.out.println("user id " + userid);
			if (userid == null) 
			{
					response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			Controller obj=new Controller();
			Connection con = obj.con();
			obj.createStatement(con);
		    Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			
			if (Office_id.equalsIgnoreCase("0"))
				Office_id=obj.isNull(obj.setValue("office_id", request),1);
			if (Office_id.equalsIgnoreCase("0") || Office_id.equalsIgnoreCase("")) Office_id="5000";
			//String Office_name=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where office_id in (selet office_id from pms_dcb_mst_beneficiary where status='L')","off","  style='width: 150;font-size: 0.7em; color: blue;' class='select'  ");

%> 
<form name="collect_ListAll_Form" >
<table align="center" width="70%" border="1" style="border-collapse: collapse; border-color: skyblue" cellpadding="10">
	<tr>
		<td align="center" colspan="2"><font size='3' color="darkblue"><b>DCB DATA</b></font></td>
	</tr>
	 <tr><td>&nbsp;Year</td> 
	            <td><select id="year" name="year"  tabindex="5" onchange="report_period_verify(document.getElementById('month'),this)">
                <option value="0" selected="selected">Select</option>
                <%
                for (int j=year-6;j<=year;j++)
                {
                	if (j==prv_year) 
    		   		{
    		   		%>
    		   		<option value="<%=j%>" selected="selected"><%=j%></option>
    		  		<% } else {	%> <option value="<%=j%>"><%=j%></option><%}
                 } %>  
    </select> </td>
		</tr>
		 <tr> 
			<td width="20%"  align="left">&nbsp;Month</td>
			<td>
			 <select name="month" id="month" onchange="report_period_verify(this,document.getElementById('year'))">
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++) {
	         		  if (i==prv_month) { 
					   		%>  <option value="<%=i%>" selected="selected"><%=amonth[i]%></option><% }  else { %>
						    	<option value="<%=i%>"><%=amonth[i]%></option> <% }
	         	%>
	         		  
	         	<%} %>
	          </select>
	         </td>
	       </tr>
	        <tr>
		<td>Report Type &nbsp;&nbsp;&nbsp;&nbsp;
			<select id="rep" name="rep">
				<option value="1">PDF</option>
				<option value="2">Excel</option>
							
			</select>
		</td>
		<td>Report in  &nbsp;&nbsp;&nbsp;&nbsp;
			<select id="repin" name="repin">
				<option value="1">Lakhs</option>
				<option value="2">Actual</option>
							
			</select>
	<!-- 	&nbsp;&nbsp;&nbsp;&nbsp;
			District   &nbsp;&nbsp;&nbsp;&nbsp;
			<select id=dis name="dis">
				<option value="31">Ariyalur</option>
				<option value="10">Coimbatore</option>
				<option value="6">Cuddalore</option>
				<option value="4">Dharmapuri</option>
				<option value="11">Dindugul</option>
				<option value="8">Erode</option>
				<option value="2">Kancheepuram</option>
				<option value="21">Kanyakumari</option>
				<option value="25">Karur</option>
				<option value="30">Krishnagiri</option>
				<option value="16">Madurai</option>
				<option value="22">Nagapattinam</option>
				<option value="27">Namakkal</option>
				<option value="9">Nilgiris</option>
				<option value="24">Perambalur</option>
				<option value="14">Pudukkottai</option>
				<option value="18">Ramanathapuram</option>
				<option value="7">Salem</option>
				<option value="15">Sivagangai</option>
				<option value="13">Thanjavur</option>
				<option value="28">Theni</option>
				<option value="29">Thiruvarur</option>
				<option value="12">Tiruchirapalli</option>
				<option value="20">Tirunelveli</option>
				<option value="32">Tiruppur</option>
				<option value="5">Tiruvannamalai</option>
				<option value="26">Tiruvellore</option>
				<option value="19">Tuticorin</option>
				<option value="3">Vellore</option>
				<option value="23">Villupuram</option>
				<option value="17">Virudhunagar</option>
							
			</select>
		</td> -->	
			       </tr> 
	       
	      <Br>
	   
	 <table align="center" width="70%" border="1" style="border-collapse: collapse; border-color: skyblue" cellpadding="10">
	<tr>
		<td colspan="2" align="left">
			<ul>
			    <li>DCB DATA <a href="#"  onclick="Watercharge3()"><font color='blue'>Download</font></a> 
			    <li>FAS(from Accounting Office) and DCB(Adjusted) Credit Advise <a href="#"  onclick="Watercharge4()"><font color='blue'>Download</font></a> 
			    <li>Schemewise Quantity <a href="#"  onclick="Watercharge2()"><font color='blue'>Download</font></a> 
	<!-- 	   	<li>WATER CHARGES DUE Details 1)with current Demand- <a href="#"  onclick="Watercharge()"><font color='blue'>Download</font></a> &nbsp;&nbsp;2)without current Demand- <a href="#"  onclick="Watercharge1()" ><font color='blue'>Download</font></a></li>
			   	<li>DISTRICTWISE WATER CHARGES DUE 1)with current Demand- <a href="#"  onclick="Waterdetail()"><font color='blue'>Download</font></a> &nbsp;&nbsp;2)without current Demand- <a href="#"  onclick="Waterdetail1()" ><font color='blue'>Download</font></a></li>
			   	 -->	
				<li>Beneficiary Wise Collection (new) - <a href="#" onclick="report('Head_Office_Data_new',1)"><font color='blue'>Download</font></a></li>
				<li>Beneficiary Wise Demand and Collection - <a href="#" onclick="report('Head_Office_Data1',1)"><font color='blue'>Download</font></a></li>
				<li>Division wise Scheme Wise Receipt - <a href="#" onclick="report('Scheme Wise Collection',2)"><font color='blue'>Download</font></a></li>
				<li>Division wise Opening Balance - <a href="#" onclick="report('Division_OpeningBalance',3)"><font color='blue'>View PDF</font></a></li>
				<li>Beneficiary Wise Demand and Collection -Tentative <a href="#" onclick="report('Head_Office_DataTentative',1)"><font color='blue'>Download</font></a></li>
				<li>Beneficiary Wise Demand and Collection -Tentative (Division Wise) <a href="#" onclick="report('Head_Office_DataTentative2',1)"><font color='blue'>Download</font></a></li>
				
					<li>BLEDGERntative (Division Wise) <a href="#" onclick="report('Ledger_data_Report4',3)"><font color='blue'>Download</font></a></li>
			
			         <li>DCB DATA <a href="#"  onclick="Watercharge66()"><font color='blue'>Ledger do</font></a> 
			 
			 
			     
			</ul>  
			</td>
			</tr> 

<tr>
	<td colspan="2" align="center"><font size='3' color="darkblue"><b>Collection Data Download</b></font></td>
</tr>
 <Tr> 	<td><font color='green'><b>Select Date</b></font></td>
 		<td >  <font color='green'><b>From</b></font> &nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong>
                         <input type="text" name="txtFrom_date1" id="txtFrom_date1" readonly="readonly"/>   
	                	<img src="../../../../../../images/calendr3.gif" 
	                     onclick="showCalendarControl(document.getElementById('txtFrom_date1'));"
	                     alt="Show Calendar"></img>&nbsp;&nbsp;<font color='green'><b>To</b></font>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>:</strong>
                         <input type="text" name="txtFrom_date2" id="txtFrom_date2" readonly="readonly"/><img src="../../../../../../images/calendr3.gif" 
	                     onclick="showCalendarControl(document.getElementById('txtFrom_date2'));"
	                     alt="Show Calendar"></img> 
      </td> </tr>
  <tr>
		<td>Report Type &nbsp;&nbsp;&nbsp;&nbsp;
			<select id="reporttype" name="reporttype">
				<option value="1">PDF</option>
				<option value="2">Excel</option>
				<option value="3">HTML</option>
				
			</select>
		</td>
		<td align="center">
			<input type="button" name="" value="DCB Receipt" onclick="report_new()">
			<input type="button" name="" value="Journal Adjustment" onclick="report_new_2()">
		</td> 
	</tr><tr> 
			<td colspan="2" align="center">			 
			<input type="button" value="Exit" class="btn" onclick="window.close()" />
		</td>
	</tr>	
 </table>  </table>                       
</form>
</body>
</html>