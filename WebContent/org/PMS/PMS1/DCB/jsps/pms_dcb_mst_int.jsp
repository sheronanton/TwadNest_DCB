<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
  <%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link href='../../../../../css/CalendarControl.css' rel='stylesheet'media='screen'/>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/pms_dcb_mst_int.js"></script>
    <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
    <title>Interest Details | TWAD Nest - Phase II </title>
  </head>
 <body onload="loadbeneficiary();doFunction('get');">
<form action="" name="interest">
 <table  class="tab" border="0" width="80%" align="center"   cellpadding="0" cellspacing="0">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"><div align="center"><strong>Interest Details</strong></div></td>
    </tr>
    <tr >
        <td>Interest No</td>
        <td><input type="text" name="Interest_Id" id="Interest_Id" maxlength="5" size="7" readonly="readonly" style="background-color: #ececec"  class="tb4"/><small>(Auto generated)</small></td>
    </tr>
    <tr >
        <td>Beneficiary Type</td>
        <td>
          <select name="Beneficiary_Type" id="Beneficiary_Type" tabindex="2" onchange="bentypecheck();" class="select">
        <!--  <select name="Beneficiary_Type" id="Beneficiary_Type" tabindex="2">-->
            <option value="" selected="selected">- - - Select - - -</option>
        </select>
        </td>
    </tr>
    <tr >
        <td>Interest Rate <small>(%)</small></td>
        <td><input type="text" name="Interest_Rate" maxlength="35" size="7" id="Interest_Rate" onkeyPress="return numonly(event);" class="tb4"/></td>
    </tr>
    <tr >
        <td>Interest w.e.f</td>
        <td><input type="text" name="Interest_wef" maxlength="35" size="8" id="Interest_wef" class="tb4" />
        <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.interest.Interest_wef)"></img>
        </td>
    </tr>
    <tr  >
        <td> <div id="statusdivname">Status</div></td>
        <td><div id="statusdiv"><select id="status" class="select">
            <option value="" selected="selected"> - - - Select - - -</option>
            <option value="A">Active</option>
            <option value="D">Defunct</option>
        </select>
        </div>
        </td>
    </tr>
    
</table>
<table  class="table"  id="existing" border="1" width="80%" align="center" cellpadding="0" cellspacing="0"  >
    <tr class="tdH">
        <th class="tdText" align="center">Select</th>
       <!-- <th class="tdText">No</th>-->
        <th class="tdText" align="center">Beneficiary Type</th>
        <th class="tdText" align="center">Interest Rate</th>
        <th class="tdText" align="center">Interest w.e.f</th>
        <th class="tdText" align="center">Status</th>
    </tr>
    <tbody id="getvaluerows"  class="table" ></tbody>
    <tr class="tdText" align="center">
        <td colspan="5" >               
                <input type="button" name="cmdadd" value="Add" id="cmdadd" onclick="doFunction('Add');" class="fb2"/>
                <input type="button" name="cmdupdate" value="Update" id="cmdupdate" onclick="doFunction('Update');" class="fb2" />
                <input type="button" name="cmddelete" value="Delete" id="cmddelete" onclick="doFunction('Delete');"  class="fb2"/>
                <input type="button" name="cmdclear" value="Clear"  id="cmdclear" onclick="refresh()" class="fb2"/>
                <img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=18','mywindow','width=600,height=400,scrollbars=yes')"><input type="button" name="cmdexit" value="Exit"  id="cmdlist" onclick="exitwindow();" class="fb2"/>              
        </td>
    </tr>
</table>
</form>
  
  
  </body>
</html>