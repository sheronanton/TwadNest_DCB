<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.Connection"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand and Collection for selected period </title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>

<script type="text/javascript">
function rld5()
{
	var pyear=document.getElementById("finyear").value;	
	var pmonth=document.getElementById("pmonth").value;	 
	var reporttype =document.getElementById("reporttype").value;
	//var s="../../../../../../Water_Charges_Report?pmonth="+pmonth+"&command=FIN_YEAR_REPORT_HO&pyear="+pyear+"&option="+reporttype;	
	window.open("../../../../../../Water_Charges_Report?pmonth="+pmonth+"&command=FIN_YEAR_REPORT_HO&pyear="+pyear+"&option="+reporttype);	 
}
function rld4()
{
	var reporttype =document.getElementById("reporttype").value;
	var pyear=document.getElementById("finyear").value;	 
	
	var pmonth=document.getElementById("pmonth").value;	 
	window.open("../../../../../../Water_Charges_Report?pmonth="+pmonth+"&command=FIN_YEAR_REPORT&pyear="+pyear+"&option="+reporttype);	 
}	

function qua()
{

	var reporttype =document.getElementById("reporttype").value;
	var pyear=document.getElementById("finyear").value;	 
	var pmonth=document.getElementById("pmonth").value;	 
	var ftype =document.getElementById("ftype").value;
	if(ftype == 1)
		ftype = 1;	
	if(ftype == 2)
		ftype = 100000;	
	if(ftype == 3)
		ftype = 10000000;	
	window.open("../../../../../../Water_Charges_Report?pmonth="+pmonth+"&command=Quan&pyear="+pyear+"&option="+reporttype+"&ftype="+ftype);	 
}

function dmd()
{
	
	var today = new Date();
	var date = today.getDate();	
	 var chyear = today.getFullYear(); 
	 var prmonth = today.getMonth(); 
	 prmonth;
	var Lmonth=3;
	var ftype =document.getElementById("ftype").value;
	if(ftype == 1)
		ftype = 1;	
	if(ftype == 2)
		ftype = 100000;	
	if(ftype == 3)
		ftype = 10000000;	
//		alert("chyear is --->"+chyear)
	var reporttype =document.getElementById("reporttype").value;
	var pyear=document.getElementById("finyear").value;	 
//	alert("pyear is --> "+ pyear)
//	alert("prmonth is --> "+ prmonth)
	
	var Lyear=pyear;
	
	if(chyear==pyear)
			{
				Lmonth=prmonth;
				Lyear=pyear;
			}else
				{
				Lmonth=3;
				Lyear++;
				}
//	alert("Lmonth is --> "+ Lmonth)
//	alert("Lyear is --> "+ Lyear)
//	var pmonth=document.getElementById("pmonth").value;	 
	window.open("../../../../../../Water_Charges_Report?command=DmDandcoll&pyear="+pyear+"&option="+reporttype+"&ftype="+ftype+"&Lmonth="+Lmonth+"&Lyear="+Lyear);	 
}	  


function dmd2()
{
	
	var today = new Date();
	var date = today.getDate();	
	 var chyear = today.getFullYear(); 
	 var prmonth = today.getMonth(); 
	 prmonth;
	var Lmonth=3;
	var ftype =document.getElementById("ftype").value;
	if(ftype == 1)
		ftype = 1;	
	if(ftype == 2)
		ftype = 100000;	
	if(ftype == 3)
		ftype = 10000000;	
//		alert("chyear is --->"+chyear)
	var reporttype =document.getElementById("reporttype").value;
	var pyear=document.getElementById("finyear").value;	 
//	alert("pyear is --> "+ pyear)
//	alert("prmonth is --> "+ prmonth)
	
	var Lyear=pyear;
	
	if(chyear==pyear)
			{
				Lmonth=prmonth;
				Lyear=pyear;
			}else
				{
				Lmonth=3;
				Lyear++;
				}
//	alert("Lmonth is --> "+ Lmonth)
// alert("ftype is --> "+ ftype)
//	var pmonth=document.getElementById("pmonth").value;	 
	window.open("../../../../../../Water_Charges_Report?command=AllDandcoll&pyear="+pyear+"&option="+reporttype+"&ftype="+ftype+"&Lmonth="+Lmonth+"&Lyear="+Lyear);	 
}	

function dmd1()
{
	
	var today = new Date();
	var date = today.getDate();	
	 var chyear = today.getFullYear(); 
	 var prmonth = today.getMonth(); 
	 prmonth;
	var Lmonth=3;
	var ftype =document.getElementById("ftype").value;
	if(ftype == 1)
		ftype = 1;	
	if(ftype == 2)
		ftype = 100000;	
	if(ftype == 3)
		ftype = 10000000;	
//		alert("chyear is --->"+chyear)
	var reporttype =document.getElementById("reporttype").value;
	var pyear=document.getElementById("finyear").value;	 
//	alert("pyear is --> "+ pyear)
//	alert("prmonth is --> "+ prmonth)
	
	var Lyear=pyear;
	
	if(chyear==pyear)
			{
				Lmonth=prmonth;
				Lyear=pyear;
			}else
				{
				Lmonth=3;
				Lyear++;
				}
//	alert("Lmonth is --> "+ Lmonth)
// alert("ftype is --> "+ ftype)
//	var pmonth=document.getElementById("pmonth").value;	 
	window.open("../../../../../../Water_Charges_Report?command=CopDandcoll&pyear="+pyear+"&option="+reporttype+"&ftype="+ftype+"&Lmonth="+Lmonth+"&Lyear="+Lyear);	 
}	  


function rld4_new()
{
	var reporttype =document.getElementById("reporttype").value;
	var pyear=document.getElementById("finyear").value;	 
	var pmonth=document.getElementById("pmonth").value;	 
//	alert("pyear is --> "+ pyear)
//	alert("pmonth is --> "+ pmonth)
//	if (parseInt(pmonth) > 3 && parseInt(pmonth)<=12)
//		pyear=pyear;
//	else
//		pyear=parseInt(pyear)+1;
//	alert("pyear is --> "+ pyear)
	window.open("../../../../../../Water_Charges_Report?pmonth="+pmonth+"&command=FIN_YEAR_REPORT_HO_MTH&pyear="+pyear+"&option="+reporttype);	 
}
function rld2()
{
	var reporttype =document.getElementById("reporttype").value;
	var pyear=document.getElementById("finyear").value;	 
	window.open("../../../../../../Water_Charges_Report?command=FIN_YEAR_REPORT&pyear="+pyear+"&option="+reporttype);	 
}
function rld3()
{
	var reporttype =document.getElementById("reporttype").value;
	var pyear=document.getElementById("finyear").value;
 	window.open("../../../../../../Water_Charges_Report?command=FIN_YEAR_REPORT_DCB&pyear="+pyear+"&option="+reporttype);	 
}		
</script>
 <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>  
 <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
</head>
<body>
<%
		Connection con=null;String Schemestr="",Office_id="0",Office_name="0";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
	try
	{
		HttpSession session1 = request.getSession(false);
		String userid = (String) session1.getAttribute("UserId");	
		Controller obj = new Controller();		
		con=obj.con();
		obj.createStatement(con);
		if (userid == null) {		
		//	response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	  //  Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;   
	   
	   Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	    
	    if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
		String cyear="";
	    Schemestr=obj.combo_str("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","BENEFICIARY_SNO","where office_id="+Office_id+" and status='L' order by  BENEFICIARY_NAME asc","ben","  style='width: 150;font-size: 0.7em; color: blue;' class='select'  ");
	    Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
		String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		int year_incr=0;
		if (month <4)
			  year_incr=1;  
		//year_incr=0;  
		else
			//  year_incr=1;
		     year_incr=2;  
		int prvfinyear=obj.prvfinyear(year,month);
		int prvmonth=obj.prv_month(year,month);
%>  
	<table align="center" width="60%" border=1 class="table" cellpadding="20" cellspacing="0" style="vertical-align: top;">
		<tr class="tdH">
			<th colspan="4" align="center"> DCB - FAS CheckList of Demand ,Collection for the  Financial Year  <br><%=Office_name %></th>
		</tr>			
	    <Tr>
		    <td>  
				 Financial Year 
			</td>
			 	<td colspan="2">
            	<select id="finyear" class="select"  tabindex="5" style="width:120pt"  >
                	<option value="0" selected="selected">Select</option>
                	<%                	
                	for (int j=year-6;j<=year+year_incr;j++)
                	{
                		if (j-1==prvfinyear)
                		{
                	%>
                	<option value="<%=j-1%>" selected="selected"><%=j-1%>-<%=j%></option>
                	<%
                		}
                		else
                		{
                			%>
                        	<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
                        	<%		
                		}
                		 
                	}
                	%>               	
                 </select>
             </td>
        </Tr> 
        <Tr>
		    <td> 
				 Month Upto  
			</td>
				<td colspan="2">
			 <select class="select" id="pmonth" name="pmonth" onchange="report_period_verify(this,document.getElementById('finyear'))" style="width: 80pt;"  >
				<option value="0">Select</option>
					<%  
						for (int j = 1; j <=12  ; j++) 
						{
						%>
						<option value="<%=monthArrv[j]%>" <%=((j==prvmonth)?"selected":"")%>><%=monthArr[j]%></option>
						<%} %>
				 
				</select>
			 </td>
			 
			 </Tr>
			 <tr class="tdText"> <td>Report in</td>
	 	<td>
	 		<select id="ftype" name="ftype"  style="width: 100pt" class="select" >
	 			<option value="1" >Actual</option>			
	 			<option value="2"  selected="selected">Lakhs</option>
	 			<option value="3">Crores</option>
	 		</select>
	 	</td>
	 </tr>
         <tr>
		<td>Report Type</td>  
		<td colspan="2"> 
			<select class="select" id="reporttype" name="reporttype">
				<option value="1">PDF</option>
				<option value="2">Excel</option>
				<option value="3">HTML</option>
				
			</select>
		</td> 
	</tr>
	<tr>
			 	<th colspan=3 align="center" > 
			    <% if (!Office_id.equalsIgnoreCase("5000")) {%>
				<input type="button" value="View Report for Financial Year" class="fb2"   onclick="rld4()">
				<% } else { %>
				<input type="button" value="View Report for Financial Year" class="fb2"   onclick="rld5()">
				<% } %>
			  
			   <% 
			   %> <% if (Office_id.equalsIgnoreCase("5000")) {%>
				<input type="button" value="View Report only for Selected Month" class="fb2"   onclick="rld4_new()">
					<input type="button" value="Ben_Typewise Dmd and Coll" class="fb2"   onclick="dmd()">
					<input type="button" value="Dmd and Coll for Corp & MUL)" class="fb2"   onclick="dmd1()">
					<input type="button" value="Dmd and Coll for All ben)" class="fb2"   onclick="dmd2()">
					<input type="button" value="Divisionwise Supply and Demand" class="fb2"   onclick="qua()">
				<% } %><input type="button" value="EXIT" class="fb2"  onclick="window.close()">
			</th>	
			 </tr>
	 	 
	<a href="Receipt_Troubleshoot_1.jsp">.</a>
	</table>
	<%
	}catch(Exception e) 
	{ 
		out.println(e);	
	}
	%>
</body>
</html>