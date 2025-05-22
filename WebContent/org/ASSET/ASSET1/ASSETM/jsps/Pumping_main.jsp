<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pumping_Main</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Pumping_main.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript">
function fun2()
{
	window.close();
}
function report_pdf()
{
	var sno=document.getElementById("sid").value;
	   window.open("../../../../../Pumping_report?command=PDF&sno=" + sno);
}
function tech(a)
{
	disabled1('addb1');
//	var sno = document.getElementById("PMS_ASSET_PMAIN_SNO" + a).value;
	var sno = document.getElementById("sid").value;
	var from = document.getElementById("LOCATION_FROM" + a).innerHTML;
	var to = document.getElementById("LOCATION_TO" + a).innerHTML;
	var main_type = document.getElementById("TYPE_OF_PMAIN" + a).innerHTML;
	var class_type = document.getElementById("CLASS_OF_MAIN" + a).innerHTML;
	var dim = document.getElementById("DIAMETER" + a).innerHTML;
	var len_main = document.getElementById("LENGTH_MAIN" + a).innerHTML;
	document.getElementById("sid").value = sno;
	document.getElementById("from").value = from;
	document.getElementById("to").value = to;
	//document.getElementById("main_type").value = main_type;
	//document.getElementById("class_type").value = class_type;
	document.getElementById("dim").value = dim;
	document.getElementById("len_main").value = len_main;
	var sel_id=document.getElementById("main_type");
	var len=sel_id.options.length;
	
	for ( var i = 0; i < parseInt(len); i++) {
			var val = sel_id.options[i].text;

			if (val == main_type) {
				if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
				{
					sel_id.selectedIndex = i;
				} else {
					sel_id.options[i].selected = true;
				}
			} else {
				sel_id.options[i].selected = false;
			}
		}
		
	sel_id = document.getElementById("class_type");
		var len = sel_id.options.length;
		for ( var i = 0; i < parseInt(len); i++) {
			var val = sel_id.options[i].text;

			if (val == class_type) {
				if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
				{
					sel_id.selectedIndex = i;
				} else {
					sel_id.options[i].selected = true;
				}

			} else {
				sel_id.options[i].selected = false;
			}

		}

	}
</script>

</head>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />
	<%
		String id = "", main_type = "", class_type = "";
		String sch_name = "", main = "", type_class = "", src_comp = "", sub_src_comp = "";
		try {
			Controller obj = new Controller();
			Connection con = obj.con();
			obj.createStatement(con);
			id = obj.setValue("sch_sno", request);
			sch_name = obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO=" + id + "  ");
			//out.println(sch_name);
			main_type = obj.setValue("main_type", request);
			class_type = obj.setValue("class_type", request);
			main = obj.combo_str("PMS_SCH_ASSET_PMAIN_TYPE", "TYPE_DESC","PMAIN_TYPE_ID", " order by PMAIN_TYPE_ID","main_type", "class='pmscombo' style='width: 262'");
			type_class = obj.combo_str("PMS_SCH_ASSET_CLASS_TYPE","TYPE_DESC", "CLASS_TYPE_ID"," order by CLASS_TYPE_ID", "class_type","class='pmscombo' style='width: 262'");
			src_comp = obj.setValue("src_comp", request);
			sub_src_comp = obj.setValue("sub_src_comp", request);
		} catch (Exception e) {
		}
	%>
<body onload="display1_()">
<form name="pumpingmain" action="" method="get">
 <input type="hidden" id="com_id" readonly="readonly"  name="com_id" value="<%=src_comp%>">
 <input type="hidden" id='nofboo_main' name='nofboo_main' value=''>
 <input type="hidden" id="subcom_id" readonly="readonly"  name="subcom_id" value="<%=sub_src_comp%>">
 <input type="hidden" id='sid' name='sid' value='<%=id%>'>
     
<table cellspacing="0" cellpadding="7" border="1" width="70%"
	align="center" >
	<tr class="tdH">
		<td colspan="4" align="center">Pumping Main Details</td>
	</tr>
	<tr><td>Scheme Name</td><td colspan="3"><%=sch_name%></td></tr>
	<tr>
		<td>Location</td>
		<td colspan="3"><br>From:&nbsp;&nbsp;&nbsp;<input type="text" id="from" name="from" style="width:70%"><br><br>
          &nbsp;&nbsp;&nbsp; To:&nbsp;&nbsp;&nbsp;<input type="text" id="to" name="to" style="width:70%"></td></tr>
       <tr><td>   Type of Main</td><td><%=main%></td>
       <td>Class Of Main</td><td><%=type_class%></td></tr>
        <tr><td> Diameter &nbsp;(mm)</td><td><input type="text" id="dim" name="dim" size="7" maxlength="7" onchange="validateNumber(this)"></td>
       <td>Length Of Main&nbsp;(m)</td><td><input type="text" id="len_main" name="len_main" size="7" maxlength="7" onchange="validateNumber(this)"></td></tr>
       <tr>
       		<td  colspan="4">
       		<center>
	       		<input type="button" value="ADD" class="btn" id="addb1" onclick="insert_()" />
	       		<input type="reset" value="CLEAR" class="btn" onclick="enabledtb('addb1')" /> 
	       		<input type="button" value="UPDATE" class="btn" onclick="modify_data()" /> 
	       		<input type="button" value="DELETE" class="btn" onclick="delete_1()" /> 
	       		<input type="button" value="REPORT" class="btn" onclick="report_pdf()" /> 
	       		<input type="button" value="EXIT" class="btn1" id="exitb1" onclick="fun2()" />
       		</center> 
       		</td>
      </tr>
	<tr>
	<td colspan="6">
	<table width="100%" align="center" cellspacing="0" cellpadding="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr class="tdH">
			<td class="tdText" align="center">Select</td>
			<td class="tdText" align="center">Location From</td>
			<td class="tdText" align="center">Location To</td>
			<td class="tdText" align="center">Type of Main</td>
			<td class="tdText" align="center">Class of Main</td>
			<td class="tdText" align="center">Diameter</td>
			<td class="tdText" align="center">Length</td>
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