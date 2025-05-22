<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><%@page
	import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller"%>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="../scripts/Beneficiaries.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/master1.js"></script>
<script type="text/javascript"> 
function exit()
{
	window.close();
}
function tech1(a){
    var src_comp=document.getElementById('com_id').value;
	var sub_src_comp=document.getElementById('subcom_id').value;
	var sch_sno= document.getElementById('snoid').value;
	var loc = document.getElementById("LOCATION" + a).innerHTML;
	var loc_no = document.getElementById("LOCATION_NO" + a).innerHTML;
	document.getElementById("location").value = loc;
	document.getElementById("locno_id").value = loc_no;
	var cap= document.getElementById("capacity").value;
	var qty=document.getElementById("quantity").value;
	var sr_sno = document.getElementById("PMS_ASSET_SR_SNO" + a).value
	window.open("Ben.jsp?a="+a+"&sr_sno="+sr_sno+"&sch_sno="+sch_sno +"&loc_no=" + loc_no + "&loc="+loc  +"&src_comp=" + src_comp + "&sub_src_comp=" + sub_src_comp ,"mywindow", "location=1,status=1,scrollbars=1,resizable=1");
}

function tech(a){
	 disabled1('save');
	 var sno = document.getElementById("PMS_ASSET_SR_SNO" + a).value;
	 var loc = document.getElementById("LOCATION" + a).innerHTML;
	 var cap = document.getElementById("CAPACITY" + a).innerHTML;
	 var qty = document.getElementById("QTY" + a).innerHTML;
	 var loc_no = document.getElementById("LOCATION_NO" + a).innerHTML;
	 document.getElementById("locno_id").value = loc_no;
	 document.getElementById("sid").value = sno;
	 document.getElementById("location").value = loc;
	 document.getElementById("capacity").value = cap;
	 document.getElementById("quantity").value = qty;
}
</script>
</head>
<body onload="view_benval()">
<%
    String sch_name="",sch_name1="",location="";
   String ben_typeid="",type_ben="",id="",id1="",src_comp="",sub_src_comp="",Office_id="0",page_label="",page_label1="";
	try 
	{
		Controller obj = new Controller();
		Connection con = obj.con();
		 obj.createStatement(con);
		 id1=obj.setValue("sch_sno",request);
		 id=obj.setValue("sch_sno",request);
		 sch_name1=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+id+ "  ");
		 src_comp=obj.setValue("src_comp",request);
		sub_src_comp=obj.setValue("sub_src_comp",request) ;
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	%>
<form action=""><input type="hidden" id="sid" name="sid" value="<%=id1 %>">
	 <input type="hidden" id="snoid" name="snoid" value="<%=id %>"> 
	 <input type="hidden" id="com_id" readonly="readonly" name="com_id" value="<%=src_comp %>"> 
	 <input type="hidden" id="subcom_id" readonly="readonly" name="subcom_id"
	value="<%=sub_src_comp %>"> <input type="hidden" id="locno_id"
	readonly="readonly" name="locno_id" value="">
<table border="1" width="90%" cellpadding="7" cellspacing="0"
	align="center">
	<tr class="tdH">
		<td colspan="3" align="center">Service Reservoirs</td>
	</tr>
	<tr>
		<td colspan="">Scheme Name</td>
		<td colspan="2"><%= sch_name1%></td>
	</tr>
	<tr>
		<td>Name of Location</td>
		<td>Capacity of OHT&nbsp;(LL)</td>
		<td>Quantity&nbsp;(nos)</td>
	</tr>
	<tr>
		<td><input type="text" id="location" name="location"
			style="width: 30mm;"></td>
		<td><input type="text" id="capacity" name="capacity"
			style="width: 30mm;"></td>
		<td><input type="text" id="quantity" name="quantity"
			style="width: 30mm;"></td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input type="button" value="ADD"
			id="save" class="btn" onclick="insert_benval()" /> <input
			type="button" value="UPDATE" id="update" class="btn"
			onclick="update_benval() " /> <input type="button" value="DELETE"
			class="btn" id="delete" onclick="delete_loc()" /> <!-- <input type="button" value="REPORT" onclick=""> -->
		<input type="reset" value="CLEAR" class="btn" /> <input type="button"
			value="EXIT" class="btn" onclick="exit()" /></td>
	</tr>
	<tr>
		<td colspan="3">
		<table width="100%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
			<tr class="tdH">
				<td class="tdText" align="center">EDIT</td>
				<td class="tdText" align="center">Name of the Location</td>
				<td class="tdText" align="center">Capacity</td>
				<td class="tdText" align="center">Quantity</td>
				<td class="tdText" align="center">Add Beneficiary</td>
			</tr>
			<tbody id="tblList" name="tblList" class="tdText">
			</tbody>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>