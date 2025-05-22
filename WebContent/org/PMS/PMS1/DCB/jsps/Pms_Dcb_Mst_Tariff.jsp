<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
    <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/pms_dcb_mst_tariff.js"></script>
    <style type="text/css">
					table.border1
					{
					color:#000000;
					padding:0px;
					border-top: 1px solid #dddddd;
					border-left: 1px solid #dddddd;
					border-bottom: 0px solid #dddddd;
					border-right: 0px solid #dddddd;
					font-size: 14  px;
					}
					table.border1 th, table.border1 td 
					{
					padding:5px;
					padding-bottom:2px;
					border-top: 0px solid #dddddd;
					border-left: 0px solid #dddddd;
					border-bottom: 1px solid #dddddd;
					border-right: 1px solid #dddddd;
					}
										
				</style>
    <title>Tariff for Local body | TWAD Nest - Phase II </title>
  </head>
  <body onload="loadbeneficiary();doFunction('get');">
  <form name="chargedetails" action="">
   <table  class="fb2" border="0" width="80%" align="center"   cellpadding="5" cellspacing="0"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"> <div align="center"><strong>Tariff for Local body</strong></div></td>
    </tr>
  <tr class="tdText">
        <td class="tdText">Tariff No</td>  
        <td><input type="text" name="tariff_Id" id="tariff_Id" class="tb4" maxlength="5" size="7" readonly="readonly" style="background-color: #ececec" class="tb3" /><small>(Auto generated)</small></td>
    </tr>
     <tr class="table">
        <td class="tdText">Beneficiary Type</td>
        <td>
            <select name="Beneficiary_Type" id="Beneficiary_Type" tabindex="2" onchange="bentypecheck();" class="select">
            <option value="" selected="selected" >- - - Select - - -</option>
        </select>
        </td>
    </tr>
 
    <tr class="table">
        <td class="tdText">Tariff Rate <small>(Rs)</small></td>
        <td><input type="text" class="tb4"  name="tariff_Rate" maxlength="35" size="7" id="tariff_Rate"  class="tb3" style="TEXT-TRANSFORM:UPPERCASE" onkeyPress="return numonly(event);" /></td>
    </tr>
    <tr class="table">
        <td class="tdText">Tariff w.e.f</td>
        <td><input type="text" name="Tariff_wef" maxlength="35" size="12" id="Tariff_wef" class="tb4" />
       <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.chargedetails.Tariff_wef)"></img>
        </td>
    </tr>
    
   <tr class="table">
        <td class="tdText"> <div  id="statusdiv_name"> Status </div>
        </td>
        <td><div  id="statusdiv"><select id="status" class="select">
            <option value="" selected="selected" > - - - Select - - -</option>
            <option value="A">Active</option>
            <option value="D">Defunct</option>
        </select></div>
        </td>
   </tr> 
   <tr>
   		<td align="right" colspan="2"><font color='blue' size=2><a href="tariff_rate_change.jsp" style="color: blue">Tariff Rate Revision </a></font></td>
   </tr>
   <tr class="tdH" align="center">
        <td colspan="2" >               
                <input type="button" name="cmdadd" value="Add" id="cmdadd" onclick="doFunction('Add');" class="fb2"/>
                <input type="button" name="cmdupdate" value="Update" id="cmdupdate" onclick="doFunction('Update');" class="fb2" />
                <input type="button" name="cmddelete" value="Delete" id="cmddelete" onclick="doFunction('Delete');" class="fb2" />
                <input type="button" name="cmdclear" value="Clear"  id="cmdclear" onclick="refresh()" class="fb2"/><img src="../../../../../images/help_images.jpg" height="18px" width="45px"  style="vertical-align: bottom;"  onClick="window.open('help1.jsp?fn=17','mywindow','width=600,height=400,scrollbars=yes')">
                <input type="button" name="cmdexit" value="Exit"  id="cmdlist" onclick="exitwindow();" class="fb2"/> 
        </td>
   </tr>  
    </table>
	    <table  class="fb2" id="existing" border="1" width="80%" align="center" cellpadding="5" cellspacing="0"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
	        <tr>
	           <td class="tdText">Select</td>
	           <td class="tdText">No</td>
	            <td class="tdText">Beneficiary Type</td>
	            <td class="tdText">Tariff Rate</td>
	            <td class="tdText">Tariff w.e.f</td>
	            <td class="tdText">Status</td>
	         </tr>
	        <tbody id="getvaluerows" class="table"></tbody>
	    </table>
     </form>
  </body>
</html>