<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Distribution Details</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Distribution_sys.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript">
function fun()
{
	window.close();
	//window.open("SchemeTechDetails.jsp");
}
function rep()
{
	
	var sno=document.getElementById("sid").value;
	
	   window.open("../../../../../Distribution_sys?comment=PDF&sno="+sno);

}
function tech(a)
{
	disabled1("save");
	var sno = document.getElementById("PMS_ASSET_DIST_SNO" + a).value;
	var exit = document.getElementById("EXISTING" + a).innerHTML;
	var new_laid = document.getElementById("NEW" + a).innerHTML;
	document.getElementById("sid").value = sno;
	document.getElementById("exit").value = exit;
	document.getElementById("new_laid").value = new_laid;
}
</script>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />
</head>
<body onload="display_distribution()">
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
		<td colspan="4" align="center">Distribution System</td>
	</tr>

	   <tr><td>Scheme Name</td><td colspan="3"><%= sch_name%></td></tr>
       	<tr>
       	<td>Existing&nbsp;(km)</td>
	    <td><input type="text" id="exit" name="exit" ></td>
	    </tr>
       <tr>
       <td>Newly Laid&nbsp;(km)</td>
	   <td><input type="text" id="new_laid" name="new_laid" ></td>
      </tr>
   
   <tr><td colspan="2">	 
   <center>
    <input type="button" value="ADD" id="save" onclick="addition_distribution()">
	<input type="button" value="UPDATE" onclick="update_distribution()">  
	<input type="button" value="DELETE" onclick="delete_distribution()"> 
	<input type="reset" value="CLEAR" onclick="enabledtb('save')">
       <input type="button" value="REPORT"  class="btn"   onclick="rep()"/>  
	 <input type="button" value="EXIT"  class="btn" onclick="fun()"/> 
	 </center>
	 </td></tr>
	<tr><td colspan="4"><table width="100%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr class="tdH">
		<td class="tdText" align="center">Select</td>
		<td class="tdText" align="center">Existing (km)</td>
		<td class="tdText" align="center">Newly Laid (km)</td>
		</tr>
		<tbody id="tblList" name="tblList" class="tdText">
		</tbody>
	</table></td></tr>
          </table>
          </form>
</body>
</html>

	
	