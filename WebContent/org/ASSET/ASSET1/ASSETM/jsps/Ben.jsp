<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller"%>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Service Reservoir - Beneficiaries details</title>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />
</head>
<script type="text/javascript" src="../scripts/Beneficiaries_type.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/master1.js"></script>
<script type="text/javascript">

	function loadcall() {
		 
		var sno1=document.getElementById("sch_sno").value;
		var sno = document.getElementById("sch_sno_id").value;
		document.getElementById("sch_sno").value = sno;
		document.forms['sr'].submit();
	}
	function loadcall1() {
	 	var sno = document.getElementById("sch_sno_id").value;alert("sno"+sno);
	 	var sno1 = document.getElementById("sch_sno").value;alert("sno1"+sno1);
	 	var sch_sno = document.getElementById("sch_sno1").value;alert("sch_sno"+sch_sno);
		document.getElementById("sch_sno").value = sno;
		document.getElementById("sch_sno1").value = sch_sno;
		document.forms['sr'].submit();
	//	view_beneficiary_type();
	}
	function exit() {
		window.close();
	}
	function tech(a) 
	{	
		disabled1('save');
		var sno = document.getElementById("PMS_ASSET_SR_BEN_SNO" + a).value;
		var beneficiary_val = document.getElementById("BENEFICIARY_TYPE_ID" + a).value;
		var beneficiary_sno = document.getElementById("BENEFICIARY_SNO" + a).value;
		var ben_name = document.getElementById("BENEFICIARY_NAME" + a).innerHTML;
		var loc = document.getElementById("LOCATION" + a).innerHTML;
		var sr_sno = document.getElementById("PMS_ASSET_SR_SNO" + a).value;
		document.getElementById("sch_sno").value = sno;
		document.getElementById("ben_name").value = ben_name;
		document.getElementById("sr_sno").value = sr_sno;
		document.getElementById("loc").value = loc;
		var sel_id = document.getElementById("beneficiary_val");
		var len = sel_id.options.length;
		for ( var i = 0; i < parseInt(len); i++) 
			{
			var val = sel_id.options[i].value;
			if (val == beneficiary_val) {
				if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
				{
					sel_id.selectedIndex = i;
				} else 
				{
					sel_id.options[i].selected = true;
				}

			} else {
				sel_id.options[i].selected = false;
			}
		   }
		   combo_value(beneficiary_sno);
	}
</script>
<body onload="view_beneficiary_type()">
<%
	String sch_name = "", sch_name1 = "", ben_id = "", project_id = "", ben_name = "", id1 = "", sr_sno = "";
	String ben_typeid = "", type_ben = "", id = "",  src_comp = "", sub_src_comp = "", Office_id = "", userid = "", page_label1 = "";
	String beneficiary_val = "",beneficiary_type_val="";
	String beneficiary_name_value = "";
	String beneficiary_sno = "", beneficiary_sno_name = "",location = "",ben_short_desc="";
	int loc_count = 0, sr_sno_count = 0;
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
		id = obj.setValue("sch_sno", request);
		id1 = obj.setValue("sch_sno", request);
		sr_sno = obj.setValue("sr_sno", request);
		src_comp = obj.setValue("src_comp", request);
		sub_src_comp = obj.setValue("sub_src_comp", request);
		location = obj.setValue("loc", request);
		userid = "0";
		if (userid == "0")
			userid = "twad10950";//response.sendRedirect(request.getContextPath() + "/index.jsp");
		sch_name1 = obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO=" + id + "  ");
		Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	from SEC_MST_USERS where USER_ID='"+ userid + "')");
		beneficiary_sno = obj.setValue("beneficiary_sno", request);
		beneficiary_val = obj.setValue("beneficiary_val", request);
		beneficiary_type_val = obj
				.combo_str(
						"PMS_DCB_BEN_TYPE",
						"BEN_TYPE_DESC",
						"BEN_TYPE_ID",
						" where BEN_TYPE_ID in (select BENEFICIARY_TYPE_ID from pms_dcb_mst_beneficiary_metre where SCHEME_SNO="
								+ id + ")order by BEN_TYPE_ID",
						"beneficiary_val", beneficiary_val,
						"class='pmscombo' style='width: 262' onchange=loadcall()");
		beneficiary_sno_name = obj
				.combo_str(
						"PMS_DCB_MST_BENEFICIARY",
						"BENEFICIARY_NAME",

						"BENEFICIARY_SNO",
						" where BENEFICIARY_SNO in ( select BENEFICIARY_SNO from pms_dcb_mst_beneficiary_metre where SCHEME_SNO="
								+ id
								+ ") and BENEFICIARY_TYPE_ID="
								+ beneficiary_val
								+ " order by BENEFICIARY_SNO",
						"beneficiary_sno", beneficiary_sno,
						"class='pmscombo' style='width: 262'   ");
		loc_count = obj.getCount("PMS_SCH_ASSET_SR_LOC_BEN"," where PMS_ASSET_SR_SNO=" + sr_sno );
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<form name="sr" id="sr" method="get">
<input type="hidden" id="sch_sno" name="sch_sno" value="<%=id%>">
<input type="hidden" id="sch_sno_id" name="sch_sno_id" value="<%=id1%>">
<input type="hidden" id="src_comp" readonly="readonly"  name="src_comp" value="<%=src_comp%>">
<input type="hidden" id="sub_src_comp" readonly="readonly"  name="sub_src_comp" value="<%=sub_src_comp%>">
<input type="hidden" id="loc" readonly="readonly"  name="loc" value="<%=location%>">
<input type="hidden" id="ben_name" readonly="readonly"  name="ben_name" value="<%=ben_name%>">
<input type="hidden" id="sr_sno" readonly="readonly"  name="sr_sno" value="<%=sr_sno%>">
<!--  
<input type="hidden" id="ben_sno" readonly="readonly"  name="ben_sno" value="<%=sr_sno_count%>">

-->
<input type="hidden" id="ben_short_desc" name="ben_short_desc" value="<%=ben_short_desc%>">
<table border="1" width="90%" cellpadding="7" cellspacing="0" align="center">
			<tr  class="tdH">
			<td colspan="3" align="center">Service Reservoir</td>
			</tr>
			<tr>
			<td colspan="">Scheme Name</td>  
			<td colspan="2"><%=sch_name1%></td>
			</tr>
			<tr>
			<td>Name of Location</td>
				<td id="loc_id"><%=location%></td></tr>
		
			<tr><td>Beneficiary Type</td>
			<td><%=beneficiary_type_val%>
			</td></tr>
			<tr><td>Beneficiary Name</td>
			<td><%=beneficiary_sno_name%>
			</td></tr>
			<tr class="tdH">
			<td colspan="2">
			<table width="100%">	
			<td  align="center">
				<input type="button" value="ADD" id="save" class="btn" onclick="insert_beneficiary_type()" />
				<input type="button" value="UPDATE" id="update" class="btn" onclick="update_beneficiaryval()"/>
				<input type="button" value="DELETE" class="btn" id="delete"  onclick="delete_beneficiaries_val()" />
				<input type="reset" value="CLEAR" class="btn" onclick="enabledtb('save')"/>				 
				<input type="button" value="EXIT"  class="btn" onclick="exit()"/>
			</td>
			<td align="right"><font color='blue'>Beneficiaries added so for</font> &nbsp; : &nbsp;&nbsp; <input type="text" id="srsno" align="right" name="srsno" value="<%=loc_count%>" style="width: 20mm"/>
			</td>
			</table>
			</td>
			</tr>
			<tr>
			<td colspan="3">
			<table width="100%" align="center" cellspacing="0" cellpadding="0"
					style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr class="tdH">
				<td class="tdText" align="left">EDIT</td>
				<td class="tdText" align="left">Name of the Location</td>
				<td class="tdText" align="left">Beneficiary Name</td>
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