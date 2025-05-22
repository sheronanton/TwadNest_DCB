<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sump Details</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript" src="../scripts/Sump.js"></script>
<script type="text/javascript">
function fun()
{
	window.close();
	//window.open("SchemeTechDetails.jsp");
}
function repdf()
{
	var sno=document.getElementById("sid").value
	   window.open("../../../../../Sump?comment=PDF&sno="+sno);
}
	 


function tech(a)
{
	disabled1('addb1');
	var sno = document.getElementById("PMS_ASSET_BOOSTER_SUMP_SNO" + a).value;
	var capacity = document.getElementById("CAPACITY" + a).innerHTML;
	var depth = document.getElementById("DEPTH" + a).innerHTML;
	var dim = document.getElementById("DIAMETER" + a).innerHTML;
	var detention = document.getElementById("DETENTION" + a).innerHTML;
	var loc_from = document.getElementById("LOCATION_FROM" + a).innerHTML;
	document.getElementById("sid").value = sno;
    // alert("sid"+sno);
	document.getElementById("capacity").value = capacity;
	document.getElementById("depth").value = depth;
	document.getElementById("dim").value = dim;
	document.getElementById("detention").value = detention;
	document.getElementById("loc").value = loc_from;
		
		
}

</script>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />

</head>

<body onload="display_sump()">
<%
   String  id="",pump_type="",pumpset_type="";
   String sch_name="",src_comp="",sub_src_comp="";
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
	id=obj.setValue("sch_sno",request);
	sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+id+ "  ");
	 src_comp=obj.setValue("src_comp",request);
	sub_src_comp=obj.setValue("sub_src_comp",request);
//out.println(" sub_src_comp"+ sub_src_comp);
	
	}	
	catch (Exception e) 
	{
	
	}
%>


<form action="">
<input type="hidden" id="sid" name="sid" value="<%=id %>">
<input type="hidden" id="com_id" readonly="readonly"  name="com_id" value="<%=src_comp %>">
<input type="hidden" id="subcom_id" readonly="readonly"  name="subcom_id" value="<%=sub_src_comp %>">

<table cellspacing="0" cellpadding="7" border="1" width="70%"
	align="center"  >
	<tr class="tdH">
		<td colspan="4" align="center">Booster Station-Sump Details</td>
	</tr>
	<tr><td>Scheme Name</td><td colspan="3"><%= sch_name%></td></tr>
	<tr><td>Location</td><td colspan="3"><input type="text" id="loc" name="loc" style="width:40%"></td></tr>
     <tr>
       <td>Capacity&nbsp;(LL)</td><td><input type="text" id="capacity" name="capacity" size="7" maxlength="7" onchange="validateDecimal(this)"></td>
       <td> Diameter&nbsp;(mm)</td><td><input type="text" id="dim" name="dim" size="7" maxlength="7" onchange="validateDecimal(this)"></td>
    </tr>
    <tr> <td>Depth&nbsp;(m)</td><td><input type="text" id="depth" name="depth" size="7" maxlength="7" onchange="validateDecimal(this)"></td>
        <td>  Detention period&nbsp;(min)</td><td><input type="text" id="detention" name="detention" size="7" maxlength="7" onchange="validateNumber(this)"></td>  
    </tr>  <tr><td colspan="4" align="center">
    <center>
    <input type="button" value="ADD" id="addb1" onclick="addition_sump()">
	<!-- <input type="button" value="VIEW" onclick="display_sump()">  -->
	<input type="button" value="UPDATE" onclick="update_sump()">  
	<input type="button" value="DELETE" onclick="delete_sump()"> 
	<input type="reset" value="CLEAR" onclick="enabledtb('addb1')">
	 <input type="button" value="REPORT" onclick="repdf()">   
	 <input type="button" value="EXIT" id="exitb1" class="btn" onclick="fun()"/> 
	 </center>
	 </td></tr>
	<tr><td colspan="4"><table width="100%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr class="tdH">
		<td class="tdText" align="center">Select</td>
		<td class="tdText" align="center">Location</td>
		<td class="tdText" align="center">Capacity (LL)</td>
		<td class="tdText" align="center">Diameter (mm)</td>
		<td class="tdText" align="center">Depth (m)</td>
		<td class="tdText" align="center">Detention period (min)</td>
		
		</tr>
		<tbody id="tblList" name="tblList" class="tdText">
		</tbody>
	</table></td></tr>
          </table>
          </form>
</body>
</html>