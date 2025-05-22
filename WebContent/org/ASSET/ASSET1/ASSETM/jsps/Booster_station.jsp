<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <%@page import="java.sql.Connection"%><html>
<head>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pumping Set</title>
 <script type="text/javascript" src="../scripts/Booster_station.js"></script> 
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />
</head>

<script type="text/javascript">
function fun()
{
	window.close();
//	window.open("SchemeTechDetails.jsp");
}
function repdf()
{
	var sno=document.getElementById("sid").value
	   window.open("../../../../../Booster_report?command=PDF&sno="+sno);
}
	 

function tech(a)
{
disabled1('addb1');
	var sno = document.getElementById("PMS_ASSET_BOOSTER_PS_SNO" + a).value;
	var pump_type = document.getElementById("TYPE_OF_PUMPSET" + a).value;
	var duty_type = document.getElementById("DUTY" + a).innerHTML;
	var head = document.getElementById("HEAD" + a).innerHTML;
	var Quantity = document.getElementById("QUANTITY" + a).innerHTML;
	var remark = document.getElementById("REMARKS" + a).innerHTML;
	var hp = document.getElementById("HORSE_POWER" + a).innerHTML;
	var no_pump = document.getElementById("PUMPSET_NO" + a).innerHTML;
 
	document.getElementById('sid').value = sno;
	document.getElementById('pump_type').value = pump_type;
	document.getElementById('duty_type').value = duty_type;
	document.getElementById('head').value = head;
	document.getElementById('qty').value = Quantity;
	document.getElementById('reid').value = remark;
	document.getElementById('hp').value = hp;
	document.getElementById('no_pump').value=no_pump;
	var sel_id=document.getElementById("pump_type");
	var len=sel_id.options.length;
		for ( var i = 0; i <parseInt(len); i++ ) 
	{
			 var val=sel_id.options[i].value;
		  	 
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
function rld()
{ 
	var v=document.getElementById("rc").value;
	var no_pump=document.getElementById("no_pump").value;
	var combo=0;
	 try
	 {
		 combo= document.getElementById('combo').value;
	 }catch(e) {}
	 if (no_pump=="") no_pump=1;
    var i=parseInt(v)+1;
	if (i<=no_pump)
	{ 
			var mydiv=document.getElementById("my");
			mydiv.innerHTML="";
			mydiv.innerHTML="";
			if (no_pump==1 || no_pump==i )
			{
				document.getElementById('exitb1').style.visibility="visible";
				document.getElementById("nofboo_main").value=i;  
				mydiv.innerHTML=mydiv.innerHTML+"<table cellspacing='0' cellpadding='10' border='1' width='100%' align='center' ><tr class='tdH'>"
				+"<td colspan='6' align='center'>Details of Pumpset <label id='no_detail'>"+i+"</label></td></tr><tr><td>Type of Pumpset</td><td colspan='5'>"+combo+"</td>"
				
				+"</tr><tr><td colspan='1'>Horse Power</td><td colspan='5'>"
				+"<input type='text' id='hp' name='hp' size='7' maxlength='5' onchange='validateNumber(this)'></td></tr><tr><td>Duty(lpm)</td><td><input type='text' id='duty_type' name='duty_type' size='7' maxlength='5' onchange='validateNumber(this)'></td><td>Head(m)</td>"
				+"<td><input type='text' id='head' name='head' size='7' maxlength='5' onchange='validateNumber(this)'></td><td>Quantity(nos)</td><td><input type='text' id='qty' name='qty' size='7' maxlength='5' onchange='validateNumber(this)'></td> </tr><tr>"
				+"<td width=20%>Remarks:</td><td colspan='4' ><textarea rows='2' cols='30' id='reid'></textarea></td></tr>"
				+"<tr><td colspan='6' align='center' ><center><input type='button' id='addb1' value='ADD' class='btn' onclick='addition_booster()'><input type='button' id='viewb1' value='VIEW' onclick='display_booster()'>"
				+"<input type='button' id='upb1' value='UPDATE' onclick='update_booster()'>	"
				+"<input type='button' id='delb1' value='DELETE' class='btn' onclick='delete_booster()'><input type='reset' value='CLEAR' onclick=enabledtb('addb1')></center></td></tr></table>";
		}else
			{
			document.getElementById('exitb1').style.visibility="hidden";
			document.getElementById("nofboo_main").value=i;
			mydiv.innerHTML=mydiv.innerHTML+"<table cellspacing='0' cellpadding='10' border='1' width='100%' align='center' >"
			+"<tr class='tdH'><td colspan='6' align='center'>Details of Pumpset "+i+"</td></tr><tr><td>Type of Pumpset</td>"
			+"<td colspan='5'>"+combo+"</td></tr><tr><td colspan='1'>Horse Power</td><td colspan='5'><input type='text' id='hp' name='hp' size='7' maxlength='5' onchange='validateNumber(this)'>"
			+"</td></tr><tr><td>Duty(lpm)</td><td><input type='text' id='duty_type' name='duty_type' size='7' maxlength='5' onchange='validateNumber(this)'></td><td>Head(m)</td><td>"
			+"<input type='text' id='head' name='head' size='7' maxlength='5' onchange='validateNumber(this)'></td><td>Quantity(nos)</td><td><input type='text' id='qty' name='qty' size='7' maxlength='5' onchange='validateNumber(this)'>"
			+"</td> </tr><tr><td width=20%>Remarks:</td><td colspan='5'><textarea rows='2' cols='30' id='reid'></textarea></td></tr><tr><td colspan='6' align='center'>"
			+"<input type='button' id='addb1' value='ADD' class='btn' onclick='addition_booster()' /><input type='button' id='viewb1' value='VIEW' onclick='display_booster()'>"
			+"<input type='button' id='upb1' value='UPDATE' onclick='update_booster()'><input type='button' id='delb1' value='DELETE' class='btn' onclick='delete_booster()' />"
			+"<input type='reset' value='CLEAR' onclick=enabledtb('addb1')><input type='button' name='sub' value='NEXT' onclick=rld()>"
			+"</td></tr></table>";
			}
			document.getElementById("rc").value=i;
	}
	}
</script>
<body>
<%
String  id="",pump_type="",pumpset_type="";
String sch_name="",src_comp="",sub_src_comp="";
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
	id=obj.setValue("sch_sno",request);
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
<form action="">
<input type="hidden" id="sid" name="sid" value="<%=id %>">
<input type="hidden" id='nofboo_main' name='nofboo_main' value=''>
<input type="hidden" id="com_id" readonly="readonly"  name="com_id" value="<%=src_comp %>">
<input type="hidden" id="subcom_id" readonly="readonly"  name="subcom_id" value="<%=sub_src_comp %>">
<table cellspacing="0" cellpadding="2" border="1" width="70%"
	align="center" style="border-collapse: collapse">
	<tr class="tdH">
		<td colspan="2" align="center">Booster Station-PumpSet Details</td>
	</tr>
	   <tr><td>Scheme Name</td><td><%= sch_name%></td></tr>
	<tr> 
		<td>Total number of Pumpsets</td>
		<td><input type="text" id="no_pump" name="no_pump" style="width: 10mm;">
		<input type="button" id="b2" value="SUBMIT" onclick="rld()"> 
		<input type="button" id="b3" value="REPORT" onclick="repdf()"> 
		<input type="button" id="exitb1" value="EXIT" class="btn" onclick="fun()" />
		</td>
</tr>
	<tr>
		<td colspan="2">
		<div id="my"></div>
		</td>
	</tr>
</table>
<input type="hidden" name="combo" id="combo" value="<%=pumpset_type%>"> 
<input type="hidden" id="rc" name="rc" value="0">
<table width="70%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr class="tdH">
		<td class="tdText" align="center">Select</td>
		<td class="tdText" align="center">Pumpset No.</td>
		<td class="tdText" align="center">Type of Pumpset</td>
		<td class="tdText" align="center">Horse Power  (m)</td>
		<td class="tdText" align="center">Duty(lpm)</td>
		<td class="tdText" align="center">Head  (m)</td>
		<td class="tdText" align="center">Quantity  (nos)</td>
		<td class="tdText" align="center">Remarks</td>
		</tr>
		<tbody id="tblList" name="tblList" class="tdText">
		</tbody>
	</table>
</form>
</body>
</html>