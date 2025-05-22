<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head><%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller"%>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheme Asset General Details</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">
function fun()
{
	window.open("SchemeTechDetails.jsp");

	self.close();  
}
function loadcall()
{
	document.forms['component'].submit();
}
function editfun()
{
var sch_sno=document.getElementById('sch_sno').value;
var src_comp=document.getElementById('src_comp').value;
	var sub_src_comp=document.getElementById('sub_src_comp').value;
window.open("LocBeneficiaries_Edit.jsp?sch_sno="+sch_sno  + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
}

function loadpage()
{
	var src_comp=document.getElementById('src_comp').value;
	var len_comp=document.getElementById('src_comp').length;
	var sub_src_comp=document.getElementById('sub_src_comp').value;
	var len_subcomp=document.getElementById('sub_src_comp').length;
	if(len_comp<=1){
		document.getElementById('ss').style.display = "none";
		alert("There are NO components related this Scheme!!!");
		}
	if(len_subcomp<=1){
	    document.getElementById('mm').style.display = "none";
	    
		alert(" There are NO sub components for selected Componenent!!!");}
	var sch_sno=document.getElementById('sch_sno').value;
	
	     if(parseInt(sub_src_comp)!=0)  
		 
	  { 			
			if( parseInt(sub_src_comp)==1)
		    {
				window.open("AssTechDetails1.jsp?sch_sno=" + sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp ,"self","location=1,status=1,scrollbars=1,resizable=1");
		    }
			else if (sub_src_comp==2)
			{  
				window.open("Pumpset.jsp?sch_sno="+sch_sno  + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
			}
		  
			else if (sub_src_comp==3)
			{
				window.open("Sump.jsp?sch_sno="+sch_sno  + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
				
			}else if (sub_src_comp==4)
			{
				window.open("Booster_station.jsp?sch_sno="+ sch_sno  + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
			}
     }  
	else 
	{
		  if(src_comp==2)  
			{
				window.open("Pumping_main.jsp?sch_sno="+sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
			}
			else if(src_comp==4)
			{
				window.open("Booster_Main.jsp?sch_sno="+sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
			}

			else if(src_comp==5)
			{
				window.open("BrPumping_main.jsp?sch_sno="+sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
			}
			else if(src_comp==6)
				
			{ 
			
			
				document.getElementById("ben_type").style.display="inline";
				var t1=document.getElementById("s1").value;
				if(t1==1){
					
				window.open("LocBeneficiaries.jsp?sch_sno="+sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp+"&t1="+t1,"self","location=1,status=1,scrollbars=1,resizable=1");
				}
				if(t1==2){
					
					window.open("LocBeneficiaries.jsp?sch_sno="+sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp+"&t1="+t1,"self","location=1,status=1,scrollbars=1,resizable=1");
					
				}
			}
			else if(src_comp==7)
			{
				window.open("Transmission_main.jsp?sch_sno="+sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
			}
			else if(src_comp==8)
			{
				window.open("Distribution_sys.jsp?sch_sno="+sch_sno + "&src_comp=" + src_comp + "&sub_src_comp=" +sub_src_comp,"self","location=1,status=1,scrollbars=1,resizable=1");
				
			}
			
			else
			{
				//alert("Select Components"); 
			}
 }		  
} 
</script>
</head>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />

<body>
<%
	String id = "", stype = "";
	String sch_name = "";
	String src_type = "", comp = "", sub_comp = "";
	String src_comp = "";
	String sub_src_comp = "";
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
		id = obj.setValue("sch_sno", request);
		src_comp = obj.setValue("src_comp", request);
		sub_src_comp = obj.setValue("sub_src_comp", request);
		sch_name = obj.getValue("PMS_SCH_MASTER", "SCH_NAME",
				"where SCH_SNO=" + id + "  ");
		comp = obj
				.combo_str("PMS_SCH_LKP_COMPONENT", "COMP_DESC",
						"COMP_ID",
						" where COMP_ID in ( select COMP_ID from PMS_SCH_COMPONENT where SCH_SNO="
								+ id + ")", "src_comp", src_comp,
						"class='pmscombo' style='width: 262' onchange=loadcall()");
		sub_comp = obj.combo_str("PMS_SCH_LKP_SUBCOMPONENT",
				"SUBCOMP_DESC", "SUBCOMP_ID", " where COMP_ID="
						+ src_comp + " order by SUBCOMP_ID",
				"sub_src_comp", sub_src_comp,
				"class='pmscombo' style='width: 262'  ");
	} catch (Exception e) {

	}
%>

<form name="component" id="component" method="get">
<input type="hidden" id="sch_sno" readonly="readonly" name="sch_sno" value="<%=id%>">
	
<table border="1" width="70%" align="center">
	<tr class="tdH">
		<td colspan="2">Scheme Technical Details Entry</td>
	</tr>
	<tr>
		<td>Scheme Name:</td>
		<td><label id="l1"><%=sch_name%></label></td>
	</tr>


	<tr class="tdH">
		<td colspan="2">Component and Sub Component Details Entry</td>
	</tr>
	<tr id="ss">
		<td>Select Scheme Component</td>
		<td><%=comp%></td>

	</tr>
	<tr id="mm">
		<td>Select Scheme Sub Component</td>
		<td><%=sub_comp%></td>
	</tr>
	<tr>
	 <tr><td colspan="2">
	 <div id="ben_type" style="display: none">
	 <table border="1" width="70%" align="center">
		<td>Select Beneficiary Type</td>
		<td><select name="s1" id="s1">
			<option value="0">----select----</option>
			<option value="1">Local Body Beneficiaries</option>
			<option value="2">Private Beneficiaries</option>
		</select>
		<input type='button' value='EDIT' onclick="editfun()"></td>
	</table></div></td>
	</tr>


	<tr>
		<td align="center" colspan="2"><input type="button"
			value="Submit" onclick="loadpage()"> <input type="button"
			value="Exit" onclick="javascript:window.close()"></td>
			
	</tr>


</table>
</form>
</body>
</html>