<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheme Technical Details Report</title>
<script type="text/javascript" src="../scripts/master1.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">
function fun()
{
	window.close();
	
	//window.open("sch_master1.jsp");
}

function view(a)
{
  
		var win=window.open(a,'_blank',"mywindow"," width=990, height=600, location=1, status=1, scrollbars=yes, resizable=yes");
}
</script>  
</head>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen" />
<body>
<%
	//int id = 0;
   String  id="";
   String sch_name="";
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
		//id = obj.getMax("PMS_SCH_ASSET_HEADWORK", "PMS_ASSET_HW_SNO",
				//"");
	//out.println("id"+id);
	id=obj.setValue("sch_sno",request);
	sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+id+ "  ");
	session.setAttribute("sch_name",sch_name);
	//out.println("sch_name"+sch_name);
		}
	catch (Exception e) 
	{
	}
%>
<form action="">
<input type="hidden" id="aid" name="aid" readonly="readonly" value="<%=id %>">
<table width="80%" border="1" align="center">

	<tr class="tdH">
		<td colspan="2" align="center">Scheme Technical Details</td>
	</tr>
	<tr>
		<td>Headworks</td>
		<td><a href="javascript:view('AssTechDetails1.jsp')">Source</a><br>
		<a href="javascript:view('Pumpset.jsp')">Pumpsets</a></td>
	</tr>  
	<tr>
		<td>Pumping Main</td>

		<td><a href="javascript:view('Pumping_main.jsp')">Pumping Main</a></td>
		
	</tr>
	<tr>
		<td>Booster Station</td>
		<td><a href="javascript:view('Sump.jsp')">Sump</a><br>
		<a href="javascript:view('Booster_station.jsp')">Pumpsets</a></td>
	</tr>
	
	<tr>
		<td>Booster Main</td>

		<td><a href="javascript:view('Booster_Main.jsp')">Booster Main</a></td>
		
	</tr>
	<tr>
		<td>Branch Pumping Main</td>
		<td><a href="javascript:view('BrPumping_main.jsp')">Pumping Main</a>
		</td>
	</tr>
	<tr>
		<td>Service Reservoirs</td>
		<td><a href="javascript:view('LocBeneficiaries.jsp')">Local Body Beneficiaries</a><br>
		<a href="javascript:view('PriBen.jsp')">Private Beneficiaries</a></td>
		
	</tr>
	<tr>
		<td>Transmission Main</td>
		<td><a href="javascript:view('Transmission_main.jsp')">Transmission Main</a>
		</td>
	</tr>
	<tr>
		<td>Distribution System</td>
		<td><a href="javascript:view('Distribution_sys.jsp')">Distribution System</a>
		</td>
	</tr>
	
	
	
	<tr><td colspan="2" align="center"><input type="button" value="EXIT"  class="btn" onclick="fun()"/> </td></tr>
	
</table>

</form>
</body>
</html>