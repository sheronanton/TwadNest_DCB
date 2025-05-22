<?xml version="1.0" encoding="ISO-8859-1" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.util.Calendar"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/transcat.js"></script>
<script type="text/javascript" src="../scripts/reports.js"></script>

	<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 700,700 )
			}
			function show()
			{
				var pyear=document.getElementById("pyear").value;
				if (pyear==0)
				{
					alert("Select Financial year ");
					
				}else
				{ 
					 window.open("Ame_scheme_Budget.jsp?pyear="+pyear);
					
				}
			}
			</script>
</head>
<body onload="page_size() ">
<form>
<%
	HttpSession session1 = request.getSession(false);
	String userid = (String) session1.getAttribute("UserId");
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);

	Controller obj = new Controller();
	if (userid == null) 
	{
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals(""))
		Office_id = "0";
	String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");
	String sch = obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="
							+ Office_id + ")", "sch_sno", "",
					"class='select' style='width: 262'");
	String[] monthArr = { "Select", "January", "February", "March","April", "May", "June", "July", "August", "September","October", "November", "December" };
	int year_incr=0;
	  if (month <4)
		  year_incr=0;    
	  else
		  year_incr=1;    
%>
<table width="100%" class="table" align="center" border=1 cellpadding="5">
	<tr>
		<th colspan="2" align="center" class="tdH">Annual Maintenance Estimate of Scheme</th>
	</tr>
	
	<tr>
		<td width="25%"><label class="lbl">Division Office Name :</label></td>
		<td align="left"><label class="lbl"><%=Office_Name%></label></td>
	</tr>
	 
	<tr> 
		<td><label class="lbl">Financial Year</label></td>
		<td colspan="2"><select class="select" id="pyear" name="pyear" style="width: 80pt;"  ><option value="0">Select</option>
		<% for (int j=year-1;j<=year+year_incr;j++) {
		%>    
        <option value="<%=j-1%>-<%=j%>"><%=j-1%>-<%=j%></option>
        <%} %>
		</select></td>
		
	</tr> 
	<tr><th colspan="2" align="center"> 
		<input type="button" value="View" onclick="show()" class="btn"/>
		<input type="button" value="Exit"  class="btn" onclick="window.close()"/> 
	</th></tr>

</table><a href="Ame_scheme_Item_Wise_UI.jsp">test</a>
<a href="Ame_scheme_Item_Wise_DVUI.jsp">test</a>
 <input type="hidden" value="<%=Office_id%>" id="Office_id"/>
</form>
</body>
</html>
