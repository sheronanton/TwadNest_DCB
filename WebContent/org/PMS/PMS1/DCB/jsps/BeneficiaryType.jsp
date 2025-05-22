<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>	
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Beneficiary Type | TWAD Nest - Phase II </title>
        <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    
     <script type="text/javascript" src="../scripts/BeneficiaryType.js"></script>
        <script type="text/javascript" src="../scripts/cellcreate.js"></script>
     <script >
     function callReport()
	{
	 
     window.open("../../../../../excel_report?option=1");
	}
     </script>
  </head>
  <body onload="callServer('Get'); callServer('Type');" >
  <form name="mstprivate" action="">
  <table  class="fb2" border="0" width="80%" align="center" cellspacing="0" style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"> <div align="center"><strong>Beneficiary Type</strong></div></td>
    </tr>
    <tr class="tdText">
        <td width="35%">Beneficiary Type Sl.No</td>
        <td><input type="text" name="sno" id="sno" class="tb4" maxlength="5" size="5" readonly="readonly" style="background-color: #ececec"/><small>(Auto generated)</small></td>
    </tr>
    
    
   
    <tr class="tdText">
        <td>Description</td>
        <td><input type="text" name="desc" maxlength="35" size="35" id="desc" class="tb6" /></td>
    </tr>
    
    
    <tr class="tdText">
        <td>Short Description</td>
        <td><input type="text" name="sdesc" maxlength="4" class="tb4" size="4" id="sdesc" style="TEXT-TRANSFORM:UPPERCASE"/></td>
    </tr>

    
    <tr class="tdText">
        <td>Private Beneficiary or Local Body?</td>
        <td class="tdText">
        	<input type="radio" name="prvlb" id="prv" value="P" onclick="dispAdr(this.value);" checked/> Private &nbsp; &nbsp;
        	<input type="radio" name="prvlb" id="lb" value="L" onclick="dispAdr(this.value);" /> Local Body
        </td>
    </tr>

    
    <tr  >
        <td class="tdText"> <span id="adrlbl" style="display:none">Addressed To</span></td>
        <td><input type="text" name="adr" maxlength="35" size="35" id="adr" style="display:none" class="tb6"/></td>
    </tr>

    <tr class="tdText" align="center">
        <td colspan="2" >               
                <input type="button" name="Add" value="Add" id="Add" onclick="callServer('Add')" class="fb2"/>
                <input type="button" name="Update" value="Update" id="Update" class="fb2" onclick="callServer('Update')"  />
                <input type="button" name="Delete" value="Delete" id="Delete"  class="fb2" onclick="callServer('Delete')"  />
                <input type="reset" name="Clear" value="Clear"  id="Clear" class="fb2" onclick="clearForm()"/>
               <img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=4','mywindow','width=600,height=400,scrollbars=yes')"/><input type="button" name="Exit" value="Exit"  id="Exit" class="fb2" onclick="self.close()"/>
                 
                            
        </td>
        <!-- td colspan="1" >               
                <input type="button" name="Tariff" value="Tariff" id="Tariff" class="fb2" onclick="openWindow('../../../../../org/PMS/PMS1/DCB/jsps/Pms_Dcb_Mst_Tariff.jsp','Tariff');"/>
                <input type="button" name="Interest" value="Interest" class="fb2" id="Interest" onclick="openWindow('../../../../../org/PMS/PMS1/DCB/jsps/pms_dcb_mst_int.jsp','Interest');" disabled/>
        </td-->
  </tr>
   
    </table>
     </form>
    <table  class="fb2" id="existing" border="1" width="80%" align="center" cellpadding="0" cellspacing="0"  style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>
        <tr>
            <th class="tdH">Select
            <th class="tdH">Beneficiary Type</th>
            <th class="tdH">In Short</th>
            <th class="tdH">Addressed To</th>
        </tr>
        <tbody id="tblList" name="tblList" class="tdText">
        </tbody>
    </table>
  </body>
</html>