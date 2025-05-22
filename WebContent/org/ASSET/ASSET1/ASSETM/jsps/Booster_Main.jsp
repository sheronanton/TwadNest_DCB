<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Booster_Main</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Booster_Main.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript">
function fun2()
{
	
	window.close();
	//window.open("SchemeTechDetails.jsp");  
	
}
function pdf_rpt()
{ 
	var sno=document.getElementById("sid").value;
	   window.open("../../../../../Booster_Main?comment=PDF&sno="+sno);

}
function tech(a){
	disabled1('addb1');
var sno = document.getElementById("PMS_ASSET_BMAIN_SNO" + a).value;
var from = document.getElementById("LOCATION_FROM" + a).innerHTML;
var to = document.getElementById("LOCATION_TO" + a).innerHTML;
var main_type = document.getElementById("TYPE_OF_PMAIN" + a).value;
var class_type = document.getElementById("CLASS_OF_MAIN" + a).value;
var dim = document.getElementById("DIAMETER" + a).innerHTML;
var len_main = document.getElementById("LENGTH_MAIN" + a).innerHTML;
var boo_main=document.getElementById("BMAIN_NO" + a).innerHTML;
document.getElementById("sid").value = sno;
document.getElementById("from").value = from;
document.getElementById("to").value = to;

document.getElementById("dim").value = dim;
document.getElementById("len_main").value = len_main;
document.getElementById("boo_main").value=boo_main;
var sel_id=document.getElementById("main_type");
var len=sel_id.options.length;
	for ( var i = 0; i <parseInt(len); i++ ) 
{
		 var val=sel_id.options[i].value;
	  	 
		 if(val==main_type)  
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
	  sel_id=document.getElementById("class_type");
	var len=sel_id.options.length;
		for ( var i = 0; i <parseInt(len); i++ ) 
	{
			 var val=sel_id.options[i].value;
		  	 
			 if(val==class_type)  
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
	document.getElementById('exitb1').style.visibility="hidden";
	var v=document.getElementById("rc").value;
	var main_type=0,class_type=0;
	 main_combo=document.getElementById("combo1").value;
	 class_combo=document.getElementById("combo2").value;
	 var boo_main=document.getElementById("boo_main").value;
	 if (boo_main=="") boo_main=1;
    var i=parseInt(v)+1;
var j=i-1;
 
	if (i<=boo_main)
	{
			var mydiv=document.getElementById("my");
			mydiv.innerHTML="";
			mydiv.innerHTML="";
			if (boo_main==1 || boo_main==i )  
			{
				document.getElementById('exitb1').style.visibility="visible";
				document.getElementById("nofboo_main").value=i;
				mydiv.innerHTML=mydiv.innerHTML+"<table cellspacing='0' cellpadding='10' border='1' width='100%' align='center'>"
				+"<tr class='tdH'><td colspan='6' align='center'>Details of BoosterMain "+i+"</td></tr><tr><td>Location</td><td colspan='3'><br>From:&nbsp;&nbsp;&nbsp;<input type='text' id='from' name='from' style='width:70%'>"
				  +"<br><br>"
		          +"&nbsp;&nbsp;&nbsp; To:&nbsp;&nbsp;&nbsp;<input type='text' id='to' name='to' style='width:70%'></td></tr>"
		          +"<tr><td>   Type of Main</td><td>"+main_combo+"</td>"
		          +"<td>Class Of Main</td><td>"+class_combo+"</td></tr>"
		          +"<tr><td> Diameter(mm)</td><td><input type='text' id='dim' name='dim' size='7' maxlength='5' onchange='validateNumber(this)'></td>"
		          +"<td>Length Of Main(m)</td><td><input type='text' id='len_main' name='len_main' size='7' maxlength='5' onchange='validateNumber(this)'></td></tr>"
		          +"<TR><TD ALIGN='CENTER' COLSPAN='6'><input type='button' value='ADD' id='addb1' class='btn' onclick='addition_boostermain()'/><input type='button' id='viewb1' value='VIEW' onclick='display_boostermain()'>"
		          +"<input type='button' id='upb1' value='UPDATE' onclick='update_booster()' >"
		         
		      	+"<input type='button' id='delb1' value='DELETE' class='btn' onclick='delete_boostermain()'><input type='reset' value='CLEAR' onclick=enabledtb('addb1')></TD></TR></table>";
				
			}else
			{
				document.getElementById('exitb1').style.visibility="hidden";
				document.getElementById("nofboo_main").value=i;
			mydiv.innerHTML=mydiv.innerHTML+"<table cellspacing='0' cellpadding='10' border='1' width='100%' align='center' ><tr class='tdH'><td colspan='6' align='center'>Details of BoosterMain "+i+"</td></tr><tr><td>Location</td><td colspan='3'><br>From:&nbsp;&nbsp;&nbsp;<input type='text' id='from' name='from' style='width:70%'>"
			  +"<br><br>"
	          +"&nbsp;&nbsp;&nbsp; To:&nbsp;&nbsp;&nbsp;<input type='text' id='to' name='to' style='width:70%'></td></tr>"
	          +"<tr><td> Type of Main</td><td>"+main_combo+"</td>"
	          +"<td>Class Of Main</td><td>"+class_combo+"</td></tr>"
	          +"<tr><td> Diameter(mm)</td><td><input type='text' id='dim' name='dim' size='7' maxlength='5' onchange='validateNumber(this)'></td>"
	          +"<td>Length Of Main(m)</td><td><input type='text' id='len_main' name='len_main' size='7' maxlength='5' onchange='validateNumber(this)'></td><TR><TD ALIGN='CENTER' COLSPAN='6'>"
	          +"<input type='button' id='addb1' value='ADD'  class='btn' onclick='addition_boostermain()'/>"
	          +"<input type='button' id='viewb1' class='btn' value='VIEW' onclick='display_boostermain()'>"
	          +"<input type='button' id='upb1' class='btn' value='UPDATE' onclick='update_booster()' >"
		      +"<input type='button' id='delb1' value='DELETE'  class='btn' onclick='delete_boostermain()'>"
		      +"<input type='reset' class='btn' value='CLEAR' onclick=enabledtb('addb1')>"
		      +"<input type='button' class='btn' value='NEXT' onclick=rld()></td></tr></table>";
			}
			document.getElementById("rc").value=i;
	}
}

</script>

</head>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />
	<%
   String  id="",main_type="",class_type="";
   String sch_name="",main="",type_class="",src_comp="",sub_src_comp="";;
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
	    id=obj.setValue("sch_sno",request);
	sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+id+ "  ");
	main_type=obj.setValue("main_type",request);
	class_type=obj.setValue("class_type",request);
	
	main=obj.combo_str("PMS_SCH_ASSET_PMAIN_TYPE","TYPE_DESC","PMAIN_TYPE_ID"," order by PMAIN_TYPE_ID","main_type","class='pmscombo' style='width: 19mm;'" );
	type_class=obj.combo_str("PMS_SCH_ASSET_CLASS_TYPE","TYPE_DESC","CLASS_TYPE_ID"," order by CLASS_TYPE_ID","class_type","class='pmscombo' style='width: 19mm;'" );
	 src_comp=obj.setValue("src_comp",request);
		sub_src_comp=obj.setValue("sub_src_comp",request);
	}	
	catch (Exception e) 
	{
	
	}
%>
<body>
<form action="">
     <input type="hidden" id="sid" name="sid" value="<%=id %>"> 
     <input type="hidden" id="com_id" readonly="readonly"  name="com_id" value="<%=src_comp %>">
     <input type="hidden" id='nofboo_main' name='nofboo_main' value=''>
     <input type="hidden" id="subcom_id" readonly="readonly"  name="subcom_id" value="<%=sub_src_comp %>">

     <table cellspacing="0" cellpadding="2" border="1" width="70%" align="center" >
	    <tr class="tdH"><td colspan="4" align="center">Booster Main</td></tr>
	    <tr><td>Scheme Name</td><td colspan="3"><%= sch_name%></td></tr>
	    <tr><td>Total number of Booster Main</td><td><input type='text' id='boo_main' name='boo_main' style="width: 10mm;">
	    <input type="button" id="b1" value="SUBMIT" onclick="rld()">
	    <input type="button" id="b2" value="REPORT" onclick="pdf_rpt()">
	    <input type='button' value='EXIT' id="exitb1" class='btn' onclick='fun2()'/></td></tr>
		<tr><td colspan="2"><div id="my"></div></td></tr> 
       	
	 </table>
	 
	<table width="70%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr class="tdH">
		<td class="tdText" align="center">Select</td>
		<td class="tdText" align="center">Branch Main No.</td>
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
	 
	<input type="hidden" id="rc" name="rc" value="0">
    <input type="hidden" id="combo1" name="combo1" value="<%=main %>">    
    <input type="hidden" id="combo2" name="combo2" value="<%=type_class %>">
        
	</form>
</body>
</html>