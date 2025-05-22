<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.*"%>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheme General Details</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/AssGenDetail1.js"></script>
<script type="text/javascript" src="../scripts/impfunction.js"></script>
<script type="text/javascript">
function report_view()
	{
		var sno=document.getElementById("sno_id").value;
		window.open( "../../../../../AssGenDetail1?comment=PDF&sno="+sno);  
	}
function report_count()
{
	var count=document.getElementById("count_id1").value;
	if(count==1){document.getElementById("label1").value="Already Exit";}
	else{document.getElementById("label1").value="No Record";}
	
}
function report()
	{
		var sno=document.getElementById("sno_id").value;
		window.open( "../../../../../Asset_quick_report?comment=PDF1&sno="+sno);  
	}
function processaction(a) 
	{
		enabledtb("update");	
		enabledtb("delete");	
		document.getElementById('save').style.visibility="hidden";
		var sno = document.getElementById("PMS_ASSET_GEN_SNO" + a).value;
		document.getElementById("no_id").value = sno;
		var yearvalue=document.getElementById("YEAR_COMMISION"+a).innerHTML;
		check_val_zero(yearvalue,'yid');
		var poppre = document.getElementById("POP_PRESENT" + a).innerHTML;
		var poppreyr = document.getElementById("PRESENT_YR" + a).value;
		var popint = document.getElementById("POP_INTER" + a).innerHTML;
		var popintyr = document.getElementById("INTER_YR" + a).value;
		var popult = document.getElementById("POP_ULTIMATE" + a).innerHTML;
		var popultyr = document.getElementById("ULTIMATE_YR" + a).value;
		var hrs = document.getElementById("HRS_PUMPING" + a).innerHTML;
		var corp = document.getElementById("SUPPLY_CORP" + a).innerHTML;
		var muni = document.getElementById("SUPPLY_MUN" + a).innerHTML;
		var tp = document.getElementById("SUPPLY_TP" + a).innerHTML;
		var hab = document.getElementById("SUPPLY_HAB" + a).innerHTML;
		var actsup = document.getElementById("SUPPLY_ACTUAL" + a).innerHTML;
		var reqint = document.getElementById("DAILY_REQ_INTER" + a).innerHTML;
		var reqult = document.getElementById("DAILY_REQ_ULTIMATE" + a).innerHTML;
		var pumppre = document.getElementById("RATE_PUMP_PRESENT" + a).innerHTML;
		var pumpint = document.getElementById("RATE_PUMP_INTER" + a).innerHTML;
		var pumpult = document.getElementById("RATE_PUMP_ULTIMATE" + a).innerHTML;
	    var remark = document.getElementById("REMARKS" + a).value;
		var hrs_int = document.getElementById("HRS_PUMPING_INTER" + a).value;
		var hrs_ult = document.getElementById("HRS_PUMPING_ULTIMATE" + a).value;
	    check_val_zero(poppre,'pid');
		check_val_zero(poppreyr,'yr_pid');
		check_val_zero(popint,'iid');
		check_val_zero(popintyr,'yr_iid');
		check_val_zero(popult,'uid');
		check_val_zero(popultyr,'yr_uid');
		check_val_zero(hrs,'hr_id');
		check_val_zero(corp,'corid');
		check_val_zero(muni,'munid');
		check_val_zero(tp,'tid');
		check_val_zero(hab,'rid');
		check_val_zero(actsup,'sid');
		check_val_zero(reqint,'did');
		check_val_zero(reqult,'fid');
		check_val_zero(pumppre,'oid');
		check_val_zero(pumpint,'mid');
		check_val_zero(pumpult,'nid');
		check_val_zero(remark,'reid');
		check_val_zero(hrs_int,'int_hr');
		check_val_zero(hrs_ult,'ult_hr');
	}
	
</script>
</head>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen" />
<body onload="display_()">
<%

		String id = "",id1="";
        int chk_count=0,chk_count1=0;
		String sch_name="",refno="",san_no="";
		String refno1="",date_val="",auth_id="",cad_name="",off_name="",auth_ofid="";
		String admin_ref_label="";
		try 
		{
			Controller obj = new Controller();
			Connection con = obj.con();
			obj.createStatement(con);
			id=obj.setValue("sch_sno",request);
			id1=obj.setValue("sch_sno",request);
			sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+id+ "  ");
			refno=obj.getValue("PMS_SCH_ADMIN_SANCTION", "SANCTION_REFNO"," where SCH_SNO="+id+ "  ");
			if(refno==null || refno=="0")
			{
				refno=" ";
			}
			 chk_count=obj.getCount("PMS_SCH_ASSET_GENERAL"," where SCH_SNO='"+id+"'");
			 chk_count1=obj.getCount("PMS_SCH_ASSET_GENERAL"," where SCH_SNO='"+id+"'");
			san_no=obj.getValue("PMS_SCH_ADMIN_SANCTION", "ADMIN_SANCTION_SNO"," where SANCTION_REFNO='"+refno+ "'  ");
			date_val=obj.getValue("PMS_SCH_ADMIN_SANCTION","to_char(SANCTION_DATE,'dd/mm/yyyy')"," where ADMIN_SANCTION_SNO='"+san_no+ "'  ");
			auth_id=obj.getValue("PMS_SCH_ADMIN_SANCTION", "AUTHORITY_ID"," where SANCTION_REFNO='"+refno+ "'  ");
			cad_name=obj.getValue("HRM_MST_DESIGNATIONS","DESIGNATION"," where DESIGNATION_ID='"+auth_id+"' ");
			auth_ofid=obj.getValue("PMS_SCH_ADMIN_SANCTION","AUTH_OFFICE_ID"," where AUTHORITY_ID='"+auth_id+"'");
			off_name=obj.getValue("COM_MST_OFFICES","OFFICE_NAME"," where OFFICE_ID='"+auth_ofid+"'");
			if (!refno.equals("0") &&  !date_val.equalsIgnoreCase("0"))
			admin_ref_label=refno+"dated"+date_val+" by " + cad_name +" ,"+off_name;
			else
			admin_ref_label="<font color='blue'>No Data found for Selected Scheme</font>";
		}  
		catch (Exception e)
		{
			
			out.println(e);
		}
%>

<form>
<input type="hidden" id="count_id1" name="count_id1" readonly="readonly" value="<%=chk_count %>">
<input type="hidden" id="count_id" name="count_id" readonly="readonly" value="<%=chk_count1 %>">
<input type="hidden" id="sno_id" name="sno_id" readonly="readonly" value="<%=id1 %>">
<input type="hidden" id="no_id" name="no_id" readonly="readonly" value="<%=id %>">
<input type="hidden" id="reno" name="reno" readonly="readonly" value="<%=refno %>">
<input type="hidden" id="sanno" name="sanno" readonly="readonly" value="<%=san_no %>">
 <table width="92%" border="1" align="center" cellpadding="5"   style="border-collapse: collapse;border-color: skyblue" >
	<tr class="tdH">
		<th colspan="9">Scheme General Details Entry</th>
	</tr>
	<tr>
	
	
		<td>Scheme Name:</td>
		<td colspan="8"><label id="l2"><%=sch_name%></label></td>
	</tr>
	<tr>
		<td colspan="">Administrative Sanction Ref.No</td>
		<td colspan="8"><label id="l1"><%=admin_ref_label%></label></td>
	</tr>
	<tr>
		<td colspan="">Year of Commissioning</td>
		<td colspan="8"><input type="text" id="yid" name="yid" style="width: 10mm;" maxlength="4" onblur="yearcheck(this)"></td>
	</tr>
	<tr>
		<td colspan="">&nbsp; </td>
		<td colspan="">Present Year</td>
		<td><input type="text" id="yr_pid" name="yr_pid" onkeypress="validateNumber(this)" style="width: 10mm;" maxlength="4" onblur="yearcheck(this)"></td>
		<td colspan="">Intermediate Year</td>
		<td><input type="text" id="yr_iid" name="yr_iid" onkeypress="validateNumber(this)" style="width: 10mm;" maxlength="4" onblur="yearcheck(this)"></td>
		<td colspan="2">Ultimate Year</td>
		<td colspan=""><input type="text" id="yr_uid" name="yr_uid" onkeypress="validateNumber(this)" style="width: 10mm; " maxlength="4" onblur="yearcheck(this)"></td>
	</tr>
	<tr class="tdH"><td colspan="1">Description</td><td colspan="2">Present</td><td colspan="3">Intermediate</td><td colspan="3">Ultimate</td></tr>	
	<tr>
		 <td colspan="">Population Details</td>
		 <td colspan="2"><input type="text" id="pid" name="pid" onkeyup="validateNumber(this)" style="height: mm;width: mm" maxlength="9"></td>
     	 <td colspan="3"><input type="text" id="iid" name="iid" onkeypress="validateNumber(this)" style="height: mm;" maxlength="9"></td>
    	 <td colspan="3"><input type="text" id="uid" name="uid" onkeypress="validateNumber(this)" style="height: mm;" maxlength="9"></td>
	</tr>
		<tr>   
		<td>Daily Requirement(MLD)</td>      
     <td colspan="2"><input type="text" id="sid" name="sid" onkeypress="digit_control('sid',3)" maxlength="9" size="9"></td>
     <td colspan="3"><input type="text" id="did" name="did" onkeypress="digit_control('did',3)" maxlength="9" size="9"></td>  
     <td colspan="3"><input type="text" id="fid" name="fid" onkeypress="digit_control('fid',3)" maxlength="9" size="9"></td>
	</tr>
			<tr><td>Rate of Pumping(LPM)</td>
 <td colspan="2"><input type="text" id="oid" name="oid" onblur="val(this,4,2)" maxlength="9" size="9"></td>
     <td colspan="3"><input type="text" id="mid" name="mid" onblur="val(this,4,2)" maxlength="9" size="9"></td>
     <td colspan="3"><input type="text" id="nid" name="nid" onblur="val(this,4,2)" maxlength="9" size="9"></td>
	</tr>
	<!--  <tr><td colspan="2">Urben Type</td>
	<td colspan="2">
	<select id="urben" name="urben">
	<option value="0">--------Select--------</option>
	<option value="1">Corporation</option> 
	<option value="2">Municipality</option> 
	<option value="3">TownPanchayat</option>  
	</select><f
	</td><font size="15" color="red"></font>
	<td colspan="2"><input type="text" id="urben_val" name="urben_val" onkeyup="validateNumber(this)" style="height: mm;"></td>
	</tr>-->
	<tr>
		<td colspan="">Hours of Pumping</td>
		<td colspan="2"><input type="text" id="hr_id" name="hr_id" onkeypress="validateNumber(this)" maxlength="7"></td>
		<td colspan="3"><input type="text" id="int_hr" name="int_hr" onkeypress="validateNumber(this)" maxlength="7"></td>
		<td colspan="3"><input type="text" id="ult_hr" name="ult_hr" onkeypress="validateNumber(this)" maxlength="7"></td>
	</tr>
	<!--<tr class="tdH">
		<td colspan="9">Population Details </td>
	</tr>
	<tr>
		<td>Present</td>
			<td><label>&nbsp;&nbsp;&nbsp;(year)</label><input type="text" id="yr_pid" name="yr_pid" onkeyup="validateNumber(this)" align="bottom"></td>
		<td><input type="text" id="pid" name="pid" onkeyup="validateNumber(this)" style="height: 9mm;"></td>
		<td>Intermediate</td>
			<td><label>&nbsp;&nbsp;&nbsp;(year)</label><input type="text" id="yr_iid" name="yr_iid" onkeyup="validateNumber(this)" align="bottom"></td>
		<td><input type="text" id="iid" name="iid" onkeypress="validateNumber(this)" style="height: 9mm;"></td>
		<td>Ultimate</td>
			<td><label>&nbsp;&nbsp;&nbsp;(year)</label><input type="text" id="yr_uid" name="yr_uid" onkeyup="validateNumber(this)" align="bottom"></td>
		<td><input type="text" id="uid" name="uid" onkeypress="validateNumber(this)" style="height: 9mm;"></td>
	</tr>-->
	<tr class="tdH">
		<td colspan="9">Per capita supply(LPCD)</td>
	</tr>
	<tr>
	    <td colspan="">Corporation</td>
		<td colspan=""><input type="text" id="corid" name="corid" onkeypress="validateNumber(this)"></td>
		<td colspan="">Municipality</td>
		<td colspan=""><input type="text" id="munid" name="munid" onkeypress="validateNumber(this)"></td>
		<td colspan="">Town Panchayat</td>
		<td colspan=""><input type="text" id="tid" name="tid" onkeypress="validateNumber(this)"></td>
		<td colspan="2">Rural Habitations</td>
		<td colspan=""><input type="text" id="rid" name="rid" onkeypress="validateNumber(this)"></td>

	</tr>
<!--  	<tr>
		<td colspan="3">Hours of Pumping</td>
		<td colspan="6"><input type="text" id="hr_id" name="hr_id" onkeypress="validateNumber(this)"></td>
	</tr>
	<tr class="tdH">
		<td colspan="9">Daily Requirement(MLD)</td>
	</tr>
	<tr>
		<td colspan="2">Present Actual Supply</td>
		<td><input type="text" id="sid" name="sid" onkeypress="validateNumber(this)"></td>
		<td colspan="2">Intermediate</td>
		<td><input type="text" id="did" name="did" onkeypress="validateNumber(this)"></td>
		<td colspan="2">Ultimate</td>
		<td colspan=""><input type="text" id="fid" name="fid" onkeypress="validateNumber(this)"></td>
	</tr>
	<tr class="tdH">
		<td colspan="9">Rate of Pumping(LPM)</td>
	</tr>
	<tr>
	     <td colspan="2">Present</td>
		<td colspan=""><input type="text" id="oid" name="oid" onblur="val(this,4,2)"></td>
		<td colspan="2">Intermediate</td>
		<td colspan=""><input type="text" id="mid" name="mid" onblur="val(this,4,2)"></td>
		<td colspan="2">Ultimate</td>
		<td><input type="text" id="nid" name="nid" onblur="val(this,4,2)"></td>
	</tr>-->
	<tr>
		<td>Remarks:</td>
		<td colspan="8"><textarea rows="5" cols="80" id="reid"></textarea>
		
		</td>
	</tr>
	<tr>
		<td colspan="9"><center>
	                <input type="button" value="ADD"    class="btn" id="save"   onclick="insert()"/>
					<input type="button" value="UPDATE" class="btn" id="update" onclick="update_val() "/>
					<input type="button" value="DELETE" class="btn" id="delete" onclick="delete_val()" />
					<input type="button" value="REPORT" class="btn" onclick="report_view()">
 					<input type="reset"  value="CLEAR"  class="btn" onclick="enabledtb('save')"/>				 
					<input type="button" value="EXIT"   class="btn" onclick="window.close()"/> 
		</center>
		 </td>
	</tr>
	<tr>
		<td colspan="9">
		<table width="100%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1">
			<tr>
				<td class="tdText" rowspan="2">SELECT</td> 
				<td class="tdText" colspan="5">Present</td>
				<td class="tdText" colspan="5">Intermediate</td>
				<td class="tdText" colspan="5">Ultimate</td>
				<td colspan="4">Per capital supply(LPCD)</td>				
			</tr>
			<tr class="tdH" valign="top"> 
				<td class="tdText" align="center">Year of Commissioning </td>
				<td class="tdText" align="center">Year </td>
				<td class="tdText" align="center" width="5%">Population</td>
				<td class="tdText" align="center" width="5%">Daily Req.</td>
				<td class="tdText" align="center" width="5%">Pumping Rate</td>
				<td class="tdText" align="center" width="5%">Hours of Pumping</td>				
				<td class="tdText" align="center">Year </td>
				<td class="tdText" align="center" width="5%">Population</td>
				<td class="tdText" align="center" width="5%">Daily Req.</td>
				<td class="tdText" align="center" width="5%">Pumping Rate</td>
				<td class="tdText" align="center" width="5%">Hours of Pumping</td>				
				<td class="tdText" align="center">Year </td>
				<td class="tdText" align="center" width="5%">Population</td>
				<td class="tdText" align="center" width="5%">Daily Req.</td>
				<td class="tdText" align="center" width="5%">Pumping Rate</td>
				<td class="tdText" align="center" width="5%">Hours of Pumping</td>				
				<td class="tdText" align="center" width="5%">Per Capita Supply<br>- Corp.</td>
				<td class="tdText" align="center" width="5%">Per Capita Supply<br>- Municipality</td>
				<td class="tdText" align="center" width="5%">Per Capita Supply<br>- T.P.</td>
				<td class="tdText" align="center" width="5%">Per Capita Supply<br>- Rural</td>				
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