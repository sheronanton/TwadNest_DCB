<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transmission Details</title>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Transmission_main.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>

</head>
<script type="text/javascript">
function fun()
{
	window.close();
//	window.open("SchemeTechDetails.jsp");
}

function rep()
{
	
	var sno=document.getElementById("sid").value;
	
	   window.open("../../../../../Transmission_main?comment=PDF&sno="+sno);

}
function prev()
{
	   var i = document.getElementById('rc');
	   
	   if(new Number(i.value>=1))
	   {
		   (i.value)--;
	   }
	  
}
function tech(a)
{
	disabled1('addb1');
	var sno = document.getElementById("PMS_ASSET_TMAIN_SNO" + a).value;
	var pipe_type = document.getElementById("TYPE_OF_PIPE" + a).value;
	var speci = document.getElementById("SPECIFICATION" + a).innerHTML;
	var location = document.getElementById("LOCATION" + a).innerHTML;
	var length = document.getElementById("LENGTH" + a).innerHTML;
	var no_reach = document.getElementById("REACH_NO" + a).innerHTML;
 
	document.getElementById('sid').value = sno;
    document.getElementById('length').value = length;
	document.getElementById('pipe_type').value = pipe_type;
	document.getElementById('speci').value = speci;
	document.getElementById('loc').value = location;
	document.getElementById('no_pump').value=no_reach;
	var sel_id=document.getElementById("pipe_type");
	var len=sel_id.options.length;
		for ( var i = 0; i <parseInt(len); i++ ) 
	{
			 var val=sel_id.options[i].value;
		  	 
			 if(val==pipe_type)  
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
	var combo=document.getElementById("combo").value;
	 if (no_pump=="") no_pump=1;
    var i=parseInt(v)+1;
 var j=parseInt(no_pump)-1;
    
   // alert(i);
   // alert(no_pump);
	if (i<=no_pump)
	{
			var mydiv=document.getElementById("my");
			
			mydiv.innerHTML="";
			mydiv.innerHTML="";
			if (no_pump==1 || no_pump==i )
			{
				document.getElementById('exitb1').style.visibility="visible";
				document.getElementById("noftrans_main").value=i;  
				mydiv.innerHTML=mydiv.innerHTML = mydiv.innerHTML
				
	+ "<table cellspacing='0' cellpadding='10' border='1' width='70%' align='center' ><tr class='tdH'>"
						+ "<td colspan='4' align='center'>Details of Reach "
						+ i
						+ "</td></tr><tr><td>Location:</td>"
						+ "<td colspan='3'><input type='text' id='loc' name='loc'></td> </tr>"
						+ "<tr><td>Type of Pipes</td><td colspan='3'>"
						+ combo
						+ "</td></tr>"
						+ "<tr><td>Specification</td><td><input type='text' id='speci' name='speci' >"
						+ "<td>Length&nbsp;</td><td><input type='text' id='length' name='length' onchange='validateNumber(this)' maxlength='5'></td>"
						+ "</tr>"
						+ "<tr><td colspan='4' align='center'><input type='button' id='addb1' value='ADD' class='btn' onclick='addition_transmission()' /><input type='button' id='b1' value='VIEW' onclick='display_transmission()'>"
						+ "<input type='button' id='b1' value='UPDATE' onclick='update_transmission()'>	"
						+ "<input type='button' id='b1' value='DELETE' class='btn' onclick='delete_transmission()'><input type='reset' value='CLEAR' onclick=enabledtb('addb1')></center></td></tr></table>";

				+"</table><br><br>";
			} else {
				document.getElementById('exitb1').style.visibility = "hidden";
				document.getElementById("noftrans_main").value = i;

				mydiv.innerHTML = mydiv.innerHTML
						+ "<table cellspacing='0' cellpadding='10' border='1' width='70%' align='center' ><tr class='tdH'><td colspan='4' align='center'>Details of Reach "

						+ i
						+ "</td></tr><tr><td>Location:</td>"
						+ "<td colspan='3'><input type='text' id='loc' name='loc'></td> </tr>"
						+ "<tr><td>Type of Pipes</td><td colspan='3'>"
						+ combo
						+ ""
						+ "</td></tr>"
						+ "<tr><td>Specification</td><td><input type='text' id='speci' name='speci' >"
						+ "<td>Length&nbsp;</td><td><input type='text' id='length' name='length' onchange='validateNumber(this)' maxlength='5'> </td>"

						+ "<tr><td colspan='4' align='center'><input type='button' id='addb1' value='ADD' class='btn' onclick='addition_transmission()' /><input type='button' id='b1' value='VIEW' onclick='display_transmission()'>"
						+ "<input type='button' id='b1' value='UPDATE' onclick='update_transmission()'><input type='button' id='b1' value='DELETE' class='btn' onclick='delete_transmission()' />"
						+ "<input type='reset' value='CLEAR' onclick=enabledtb('addb1')><input type='button' name='sub' value='NEXT' onclick=rld()>"
						+ "</td></tr></table>";
			}
			document.getElementById("rc").value = i;
		}
	}
</script>

<body onload="display_transmission()">
<%
String  id="",pipe_type="";
String sch_name="",type_pipe="",src_comp="",sub_src_comp="";;
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
	    id=obj.setValue("sch_sno",request);
	sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+id+ "  ");
	pipe_type=obj.setValue("pipe_type",request);
	
	type_pipe=obj.combo_str("PMS_SCH_ASSET_PIPE_TYPE","TYPE_DESC","PIPE_TYPE_ID"," order by PIPE_TYPE_ID","pipe_type","class='pmscombo' style='width: 19mm;'" );
	 src_comp=obj.setValue("src_comp",request);
		sub_src_comp=obj.setValue("sub_src_comp",request);
	}	
	catch (Exception e) 
	{
	
	}

%>
<form>
<input type="hidden" id="sid" name="sid" value="<%=id %>">
<input type="hidden" id='noftrans_main' name='noftrans_main' value=''>
<input type="hidden" id="com_id" readonly="readonly"  name="com_id" value="<%=src_comp %>">
<input type="hidden" id="subcom_id" readonly="readonly"  name="subcom_id" value="<%=sub_src_comp %>">
<table cellspacing="0" cellpadding="15" border="1" width="70%"
	align="center" style="border-collapse: collapse">
	<tr class="tdH">
		<td colspan="2" align="center">Transmission Main</td>
	</tr>
	<tr><td>Scheme Name</td><td colspan="5"><%= sch_name%></td></tr>
	<tr>
		<td>Number of Reach</td>
		<td><input type="text" id="no_pump" name="no_pump">
	
		
		<input type="button" id="b1" value="SUBMIT"
			onclick="rld()"> 
			<input type="button" id="b2" value="REPORT"
			onclick="rep()"> <input type="button" value="EXIT" id="exitb1"
			class="btn" onclick="fun()" /></td>
			
			</tr>
	<tr><td colspan="2"><div id="my"></div></td></tr>
</table>
<input type="hidden" name="combo" id="combo" value="<%=type_pipe%>"> 
<input type="hidden" id="rc" name="rc" value="0">




<table width="70%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr class="tdH">
		<td class="tdText" align="center">Select</td>
		<td class="tdText" align="center">Pipe No.</td>
		<td class="tdText" align="center">Type of Pipe</td>
		<td class="tdText" align="center">Specification</td>
		<td class="tdText" align="center">Length</td>
		<td class="tdText" align="center">Location</td>
		
		</tr>
		<tbody id="tblList" name="tblList" class="tdText">
		</tbody>
	</table>
</form>
</body>
</html>