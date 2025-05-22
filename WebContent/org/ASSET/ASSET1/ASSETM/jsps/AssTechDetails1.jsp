<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller"%>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheme Technical Details</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/AssTechDetails1.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript">
function repdf()
{
	var sno=document.getElementById("sid").value
	   window.open("../../../../../AssTechDet_report?command=PDF&sno="+sno);
}
function fun()
{
	self.close();  
}
function tech(a)
{
	disabled1('save');
	var sno = document.getElementById("PMS_ASSET_HW_SNO" + a).value;
	var stype = document.getElementById("type" + a).innerHTML;
	var diam = document.getElementById("DIAMETER" + a).innerHTML;
	var depth = document.getElementById("DEPTH_WELL" + a).innerHTML;
	var pumphse = document.getElementById("DIA_PUMPHOUSE" + a).innerHTML;
	var length = document.getElementById("LENGTH_PUMPHOUSE" + a).innerHTML;
	var redial = document.getElementById("DIA_RADIAL" + a).innerHTML;
	var qty= document.getElementById("DRAW_QTY" + a).innerHTML;
	var loc = document.getElementById("SOURCE_LOCATION" + a).innerHTML;
	var remark = document.getElementById("REMARKS" + a).innerHTML;
	/*var sel_id=document.getElementById("stype");
		var len=sel_id.options.length;
 		for ( var i = 0; i <parseInt(len); i++ ) 
		{
				 var val=sel_id.options[i].text;
			  	 
				 if(val==stype)  
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

	    }   */
	document.getElementById("sid").value = sno;
	document.getElementById("diam").value = diam;
	document.getElementById("depth").value = depth;
	document.getElementById("hou").value = pumphse;
	document.getElementById("lenth").value = length;
	document.getElementById("redial").value = redial;
	document.getElementById("qty").value = qty;
	document.getElementById("location").value = loc;
	document.getElementById("remark").value = remark;
}
</script>
</head>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"	media="screen" />
<body onload="show_()">
<%
   String  id="",stype="";
   String sch_name="",src_comp="",sub_src_comp="";
   String src_type="";
	try {
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
		id=obj.setValue("sch_sno",request); 
		stype=obj.setValue("stype",request);
		sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+id+ "  ");
		src_comp=obj.setValue("src_comp",request);
		sub_src_comp=obj.setValue("sub_src_comp",request);
				//out.println("sch_name"+sch_name);
				/**String comp="SELECT cat.CATEGORY_DESC as desc_category,"
					  +"cnt.COMP_DESC as component_val,sub.SUBCOMP_DESC as sub_somponent"
					  +" FROM"
					  +"(SELECT  CATEGORY_DESC,UPDATED_BY_USER_ID"
					  +" FROM PMS_SCH_LKP_CATEGORY"
					  +" WHERE UPDATED_BY_USER_ID = 'twad10098'"
					  +" ) cat"
					  +" JOIN"
					  +"(SELECT  COMP_DESC,UPDATED_BY_USER_ID,COMP_ID"
					  +" FROM PMS_SCH_LKP_COMPONENT"
					  +" WHERE COMP_ID = '1'"
					  +" ) cnt"
					  +" ON cat.UPDATED_BY_USER_ID = cnt.UPDATED_BY_USER_ID"
					  +" JOIN"
					  +"(SELECT  SUBCOMP_DESC,UPDATED_BY_USER_ID,COMP_ID"
					  +" FROM PMS_SCH_LKP_SUBCOMPONENT"
					  +" WHERE COMP_ID = '1'"
					  +")sub"
					  +" ON cat.UPDATED_BY_USER_ID = sub.UPDATED_BY_USER_ID"
					  +" and cnt.COMP_ID=sub.COMP_ID";
				out.println(comp);
				PreparedStatement ps=con.prepareStatement(comp);
				ResultSet rs=ps.executeQuery();
				int i=0;
				while(rs.next()){
					sch_comp=rs.getString("component_val");
					sch_subcomp=rs.getString("sub_somponent");
				i+=1;
					}**/
	//	 src_type=obj.combo_str("PMS_SCH_ASSET_SOURCE_TYPE","TYPE_DESC","SOURCE_TYPE_ID","order by SOURCE_TYPE_ID","stype","class='pmscombo' style='width: 262'" ) ;
	}
	catch (Exception e) 
	{
	}
%>

<form>
<input type="hidden" id="sid" readonly="readonly"  name="sid" value="<%=id %>">
<input type="hidden" id="com_id" readonly="readonly"  name="com_id" value="<%=src_comp %>">
<input type="hidden" id="subcom_id" readonly="readonly"  name="subcom_id" value="<%=sub_src_comp %>">
<input type="hidden" id="stype" readonly="readonly"  name="stype" value="0">

<table border="1" width="70%" align="center">
	<tr class="tdH">
		<th colspan="2"> HeadWorks-Scheme Technical Details Entry</th>
	</tr>
	<tr>
		<td>Scheme Name:</td>
		<td><label id="l1"><%=sch_name %></label></td>
	</tr>
	<!--  
	
	 <tr class="tdH">
		<td colspan="2">Component and Sub Component Details Entry</td>
	</tr>
	<tr>
		<td>Select Scheme Component</td>
		<td><select id="sch_component" name="sch_component">
			<option value="0">Select Scheme Component</option>
			<option value="1"></option>
		</select></td>
	</tr>
	<tr>
		<td>Select Scheme Sub Component</td>
		<td><select id="sch_sub_component" name="sch_sub_component">
			<option value="0">Select Scheme Sub Component</option>
			<option value="1"></option>
		</select></td>
	</tr> -->
	<tr class="tdH">
		<td colspan="2">Scheme Details</td>
	</tr>
	 
	<tr>
		<td>Diameter of Well (m)</td>
		<td><input type="text" id="diam" name="diam"  onblur="val1(this,3,2)" maxlength="6" style="width: 10mm;"></td>
	</tr>
	<tr>
		<td>Depth of Well (cum)</td>
		<td><input type="text" id="depth" name="depth"  onkeypress="validateNumber(this)" maxlength="5" style="width: 15mm;"></td>
	</tr>
	<tr>
		<td>Diameter of Pump house (m)</td>
		<td><input type="text" id="hou" name="hou"  onblur="val(this,4,2)" maxlength="6" style="width: 10mm;"></td>
	</tr>
	<tr>
		<td>Length of Pump house (m)</td>
		<td><input type="text" id="lenth" name="lenth"  onkeypress="validateNumber(this)" maxlength="7" style="width: 15mm;"></td>
	</tr>
	<tr>
		<td>Radial arms Diameter (m)</td>
		<td><input type="text" id="redial" name="redial"  onkeypress="validateNumber(this)" maxlength="7" style="width: 15mm;"></td>
	</tr>
	<tr>
		<td>Quantity ( MLD )</td>
		<td><input type="text" id="qty" name="qty"   maxlength="7" style="width: 12mm;" onblur="val1(this,4,2)"></td>
	</tr>
	<tr>
		<td>Source Location</td>
		<td><input type="text" id="location" name="location"  style="width: 80mm;"></td>
	</tr>
	<tr>
		<td>Remarks</td>  
		<td><textarea   id="remark" name="remark"></textarea></td>
	</tr>
	<tr>
	<td colspan="2">        
	 <center>
	                <input type="button" value="ADD" id="save" class="btn" onclick="push_()"/>
					<input type="button" value="UPDATE" class="btn" onclick=" modify() "/>
					<input type="button" value="DELETE" class="btn" id="delete" onclick="delete_data()" />
 					<input type="reset" value="CLEAR" class="btn" onclick="enabledtb('save')"/>		
 					<input type="button" value="REPORT" class="btn" onclick="repdf()"/>		 
				    <input type="button" value="EXIT" class="btn" id="exit" onclick="javascript:fun();">
					</center>
	</td>
	</tr>
	<tr>
	<td colspan="2">
	<table width="100%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr class="tdH">
		<td class="tdText" align="center">Select</td>
	<!-- 	<td class="tdText" align="center">Type of Source</td> -->
		<td class="tdText" align="center">Diameter of Well  (m)</td>
		<td class="tdText" align="center">Depth of Well  (cum)</td>
		<td class="tdText" align="center">Diameter of Pump house  (m)</td>
		<td class="tdText" align="center">Length of Pump house  (m)</td>
		<td class="tdText" align="center">Radial arms Diameter  (m)</td>
		<td class="tdText" align="center">Quantity in MLD  (draw)</td>
		<td class="tdText" align="center">Source Location</td>
		<td class="tdText" align="center">Remarks</td>
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