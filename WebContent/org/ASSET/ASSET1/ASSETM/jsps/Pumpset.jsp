<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PumpSet</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Pumpset.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript">
function fun()
{
	self.close();  
}
function repdf()
{
	    var sno=document.getElementById("sid").value
	    var jsno=document.getElementById("jsno").value
	    window.open("../../../../../Pumpset?comment=PDF&sno="+sno+"&jsno="+jsno);
}
 
function tech(a)
{
		disabled1('addb1');
		var sno = document.getElementById("PMS_ASSET_HW_PS_SNO" + a).value;
		var pump_type = document.getElementById("TYPE_OF_PUMPSET" + a).innerHTML;
		var duty_type = document.getElementById("DUTY" + a).innerHTML;
		var head = document.getElementById("HEAD" + a).innerHTML;
		var Quantity = document.getElementById("QUANTITY" + a).innerHTML;
		var remark= document.getElementById("REMARKS" + a).innerHTML;
		var htype=document.getElementById("HORSE_POWER" + a).innerHTML;
				document.getElementById("sid").value = sno;
				document.getElementById("htype").value = htype;
				document.getElementById("duty_type").value = duty_type;
				document.getElementById("head").value = head;
				document.getElementById("qty").value = Quantity;
				document.getElementById("reid").value = remark;

				var sel_id=document.getElementById("pump_type");
				var len=sel_id.options.length;
		 		for ( var i = 0; i <parseInt(len); i++ ) 
				{
						 var val=sel_id.options[i].text;
					  	 
						 if(val==pump_type)    
						 {
									 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1) // IE 
									 {
										 sel_id.selectedIndex = i;
									 }
									 else
									 {
										sel_id.options[i].selected = true;
									 }
						} 
						else
						{
							 sel_id.options[i].selected = false;
						 }

			    }  
}

</script>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen" />
</head>
<%
   String  id="",pump_type="",pumpset_type="";
   String sch_name="",src_comp="",sub_src_comp="",jsno="0";
	try {
			Controller obj = new Controller();
			Connection con = obj.con();
			obj.createStatement(con);
			id=obj.setValue("sch_sno",request);
			jsno=obj.setValue("jsno",request);
			sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+id+ "  ");
			pump_type=obj.setValue("pump_type",request);
	 		pumpset_type=obj.combo_str("PMS_SCH_ASSET_PUMPSET_TYPE","TYPE_DESC","PUMPSET_TYPE_ID"," order by PUMPSET_TYPE_ID","pump_type","class='pmscombo' style='width: 262'" );
	 		src_comp=obj.setValue("src_comp",request);
			sub_src_comp=obj.setValue("sub_src_comp",request);
		}	
	catch (Exception e) 
	{
	}
%>
<body onload="display_()">
<form>
<input type="hidden" id="sid" name="sid" value="<%=id %>">
<input type="hidden" id="jsno" name="jsno" value="<%=jsno %>">

<input type="hidden" id="com_id" readonly="readonly"  name="com_id" value="<%=src_comp %>">
<input type="hidden" id="subcom_id" readonly="readonly"  name="subcom_id" value="<%=sub_src_comp %>">
<table cellspacing="0" cellpadding="10" border="1" width="70%" align="center"  >
	
	<tr class="tdH">
		<td colspan="6" align="center">HeadWorks-Pumpset Details</td>
	</tr>
	<tr>
		<td>Scheme Name</td><td colspan="5"><%= sch_name%></td>
	</tr>
	<tr>
		<td>Type of Pumpset</td>
		<td colspan="5"><%=pumpset_type%></td>
	</tr>
	<tr>
		<td colspan="1">Horse Power</td>	
		<td colspan="5" ><input type="text" id="htype" name="htype" size="7" maxlength="5" onchange="validateNumber(this)"></td>
	</tr>
	<tr>
		<td>Duty&nbsp;(lpm)</td>
		<td><input type="text" id="duty_type" name="duty_type" size="7" maxlength="7" onchange="validateNumber(this)"></td>
		<td>Head&nbsp;(m)</td>
		<td><input type="text" id="head" name="head" size="7" maxlength="7" onchange="validateNumber(this)"></td>
		<td>Drawing Quantity&nbsp;(nos)</td>
		<td><input type="text" id="qty" name="qty" size="7" maxlength="5" onchange="validateNumber(this)"></td>
  </tr>
  <tr>
		<td>Remarks:<br>(e.g stand bye)</td>
		<td colspan="5"><textarea rows="2" cols="80" id="reid"></textarea></td>
 </tr>
 <tr>
 	<td colspan="6" align="center">
			 <input type="button" value="ADD" id="addb1" class="btn" onclick="addition_()"> 
			 <input type="reset" value="CLEAR"  class="btn" onclick="enabledtb('addb1')"> 
			 <input type="button" value="UPDATE"  class="btn" onclick="modify()"> 
			 <input type="button" value="DELETE"  class="btn" onclick="delete_val()"> 
			 <input type="button" value="REPORT"  class="btn" onclick="repdf()"> 
			 <input type="button" value="EXIT"  class="btn" onclick="fun()"> 
	</td>
	</tr>	
	<tr>
		<td colspan="6">
			<table width="100%" align="center" cellspacing="0" cellpadding="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr class="tdH">
				<td class="tdText" align="center">Select</td>
				<td class="tdText" align="center">Type of Pumpset</td>
				<td class="tdText" align="center">Horse Power  (m)</td>
				<td class="tdText" align="center">Duty(lpm)</td>
				<td class="tdText" align="center">Head  (m)</td>
				<td class="tdText" align="center">Quantity  (nos)</td>
				<td class="tdText" align="center">Remarks</td>
				</tr>
				<tbody id="tblList" name="tblList" class="tdText"> </tbody>
			</table>
		</td>
	</tr>
 </table>
</form>
</body>
</html>