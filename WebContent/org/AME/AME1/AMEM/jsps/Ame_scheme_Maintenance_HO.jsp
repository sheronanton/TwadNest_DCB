<?xml version="1.0" encoding="ISO-8859-1" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.util.Calendar"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Monthly Scheme Expenditure Summary </title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/transcat.js"></script>
<script type="text/javascript" src="../scripts/reports.js"></script>
 <script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 700,700 );  
			}
			function show()
			{
				var year=document.getElementById("pyear").value; 
				var month=document.getElementById("pmonth").value;
				var sch_sno=document.getElementById("sch_sno").value; 
				var res=month_year_check(month,year)
				
				if (res==1 || sch_sno==0)
				{
					alert("Select The Scheme Name ")
					  
				}else
				{ 
					report(2);
				}
			}
			 
			</script>
			<script type="text/javascript">

			function reload()
			{
				 var month=document.getElementById("pmonth").value;
				 var year=document.getElementById("pyear").value;
				 window.open("../reports/Ame_scheme_Maintenance_new_r.jsp?pmonth="+month+"&pyear="+year,"");
			  
			}

</script>  
</head>
<body onload="page_size() ">
<%
	HttpSession session1 = request.getSession(false);
	String userid = (String) session1.getAttribute("UserId");
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);

	Controller obj = new Controller();
	if (userid == null) {

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING",
			"OFFICE_ID",
			"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
					+ userid + "')");

	if (Office_id.equals(""))
		Office_id = "0";
	String Office_Name = obj.getValue("com_mst_all_offices_view",
			"OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");

	String sch = obj
			.combo_str(
					"PMS_SCH_MASTER",
					"SCH_NAME",
					"SCH_SNO",
					" where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="
							+ Office_id + ")", "sch_sno", "",
					"class='select' style='width: 380'");

	String[] monthArr = { "Select", "January", "February", "March",
			"April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
	int prvmonth=obj.prv_month(year,month);
	int prvyear=obj.prv_year(year,month);
%>
<form name="">

<table width="100%" class="table" align="center" border=1 cellpadding="5">
	<tr>
		<th colspan="2" align="center" class="tdH">Monthly Scheme Expenditure Summary </th>
	</tr>
	<tr>
		<td width="25%"><label class="lbl">Division Office Name :</label></td>
		<td align="left"><label class="lbl"><%=Office_Name%></label></td>
	</tr>
	<tr>
		<td width="25%"><label class="lbl">Scheme Name : </label></td>
		<td align="left"><%=sch%></td>
	</tr>
	<tr>
		<td><label class="lbl"> Year</label></td>


		<Td colspan="2"><select class="select" id="pyear" name="pyear" style="width: 80pt;" onchange="report_period_verify(document.getElementById('pmonth'),this)">
			<option value="0">Select</option>   
			<%
				for (int i = year-2; i <= year ; i++)
				{
					if (i==prvyear)
					{
					%><option value="<%=i%>" selected><%=i%></option><%
					}else{
					%><option value="<%=i%>"><%=i%></option><%
					}
				} 
			%>
		</select></tD>
		
	</tr>
	   <tr>  
			  	 <td><label class="lbl">  Month </label></td>	  	          
			 	 <td colspan="2" width="63%"><select class="select" id="pmonth" name="pmonth"  style="width:90pt;" onchange="report_period_verify(this,document.getElementById('pyear'))">
			  					 <option value="0">Select</option>
			  				  	<%for (int j=1;j<=12;j++)
			  				  	{
			  				  		if (j==prvmonth)
			  				  		{
			  				  		%>
			    					<option value="<%=j%>" selected><%=monthArr[j]%></option>
			    					<%			    					
			  				  		}else{
			    					%><option value="<%=j%>"><%=monthArr[j]%></option>
			    					<%
			  				  		}
			    				} %>  
			 				</select>   
			 	 </td> 
			 	
			 </tr>
	<tr><th colspan="2" align="center">
	 		<input type="button" value="Print" onclick="show()" class="btn" >
	 		<input type="button" value="Close"  class="btn" onclick="window.close()"/>
	 		<input type="button" value="All Scehme" onclick="reload()" >  
	 		<input type="hidden" value="R" id='flag' name="flag">	 
	 	</th>
	</tr>
<a href="../reports/Ame_scheme_Performance_new.jsp">test</a>
</table>
</form>
</body>  
</html>
