<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
  <%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    
    <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/pms_dcb_mst_maint_agmt.js"></script>
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
    <title>Agreement Details  | TWAD Nest - Phase II</title>
  </head>
  <body onload="loadbeneficiarytype();doFunction('get');">
  <FORM NAME="agreement" ACTION="">
  <table  class="fb2" border="0" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"> <div align="center"><strong>Agreement Details</strong></div></td>
    </tr>
    <tr class="tdText"> 
        <td>Agreement No</td>
        <td><input type="text" name="Agreement_No" id="Agreement_No" class="tb1" maxlength="5" size="8" readonly="readonly" style="background-color: #ececec" /><small>(Auto generated)</small></td>
    </tr>
    <tr class="tdText">
        <td>Beneficiary Name</td>
        <td><select id="Beneficiary_Sno" class="select"><option value="" selected="selected"> - - Select - -</option>
        <!--<input type="text" name="Beneficiary_Sno" id="Beneficiary_Sno" maxlength="5" size="8"  tabindex="2"/>-->
        </select>
        </td>
        
    </tr>
    <tr class="tdText">
        <td>Agreement File Number <small>(user reference)</small></td>
        <td><input type="text" name="Agreement_Code" maxlength="35" size="35" id="Agreement_Code"  class="tb6" /></td>
    </tr>
    <tr class="tdText">
        <td>Agreement Quantity</td>
        <td><input type="text" name="Agreement_Qty" maxlength="35" size="8" id="Agreement_Qty"  onkeypress="return numonly(event);"  class="tb4" />
        </td>
    </tr>
    
    <tr class="tdText">
        <td>Agreement Date</td>
        <td><input type="text" name="Agreement_DT_Wef" maxlength="35" size="8" id="Agreement_DT_Wef"    class="tb4" />
        <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.agreement.Agreement_DT_Wef)"></img>
        </td>
    </tr>
    
    <tr class="tdText">
        <td>Agreement Period <small>(From)</small></td>
        <td><input type="text" name="Agreement_DT_from" maxlength="35" size="8" id="Agreement_DT_from"   class="tb4"  />
        <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.agreement.Agreement_DT_from)"></img>
        </td>
    </tr>
    
    <tr class="tdText">
        <td>Agreement Period <small>(To)</small></td>
        <td><input type="text" name="Agreement_DT_to" maxlength="35" size="8" id="Agreement_DT_to"   class="tb4" />
        <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.agreement.Agreement_DT_to)"></img>
        </td>
    </tr>
    
     <tr class="tdText">
        <td>Remarks</td>
        <td><input type="text" name="Remarks" maxlength="35" size="30" id="Remarks"  class="tb6" /></td>
    </tr>
    <tr class="tdH" align="center">
        <td colspan="2" >               
                <input type="button" name="cmdadd" value="Add" id="cmdadd" onclick="doFunction('Add')" class="fb2"/>
                <input type="button" name="cmdupdate" value="Update" id="cmdupdate" onclick="doFunction('Update')" class="fb2"/>
                <input type="button" name="cmddelete" value="Delete" id="cmddelete" onclick="doFunction('Delete')" class="fb2"/>
                <input type="button" name="cmdclear" value="Clear"  id="cmdclear" onclick="refresh()"  class="fb2"/>
                <input type="button" name="cmdexit" value="Exit"  id="cmdlist" onclick="exitwindow()" class="fb2"/>              
        </td>
    </tr>
    </table>
    <table  class="fb2" id="existing" border="1" width="85%" align="center" cellpadding="0" cellspacing="0" class="border1">
        <tr>
            <th class="tdH">Edit
            <th class="tdH">Agreement No</th>
            <th class="tdH">Beneficiary</th>
            <th class="tdH">Agreement File Number</th>
            <th class="tdH">Agreement Quantity</th>
            <th class="tdH">Agreement Date </th>
            <th class="tdH">Agreement Period(From)</th>
            <th class="tdH">Agreement Period(To)</th>
            <th class="tdH">Remarks</th>
      
         </tr>
              <tbody id="getvaluerows" class="tdText"></tbody>
    </table>
  
  </form>
  </body>
</html>