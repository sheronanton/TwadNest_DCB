<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%> 
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>   
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="../scripts/Pms_Dcb_Beneficiary_Metre_Report_Pdf.js"></script>
    <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
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
				        <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
				
    <title> Beneficiary Meter Report | TWAD Nest - Phase II </title>
  </head>
  <body onload="divisionname();loadbeneficiarytype();loadsubdivision();">
  <form name="Pms_Dcb_Beneficiary_Metre_Report_Pdf"
                                                                               action="">
      <table  class="fb2" border="1" width="75%" align="center" cellpadding="10" style="BORDER-COLLAPSE: collapse"   borderColor="#92c2d8"
             cellspacing="0" class="border1">
        <tr class="tdH" align="center" style="color:black">
          <td colspan="6">
            <div align="center">
             
                <font size=3> Beneficiary Meter Report </font>
                <label id="divisionname"></label>
                
                <input type="hidden" name="divisionload" id="divisionload"/>
            </div>
          </td>
        </tr>
        <tr>
          <td  class="tdText" width="25%" >Beneficiary Type</td>
          <td style="padding-left:100px;">
            <select id="Beneficiary_type" name="Beneficiary_type"  class="select"
                    onchange="loadbeneficiaryname();">
              <option value="" selected="selected">- - - Select - - -</option>
            </select>
          </td>
        </tr>
        <tr>
          <td class="tdText">Sub Division</td>
          <td style="padding-left:100px;">
            <select id="SubDivision" name="SubDivision" tabindex="2"  class="select">
              <option value="" selected="selected">- - - Select - - -</option>
            </select>
          </td>
        </tr> 
        <tr>  
          <td class="tdText">Beneficiary Name </td>
          <td style="padding-left:100px;">
            <select id="Beneficiary_Name" name="Beneficiary_Name"  class="select">
              <option value="" selected="selected">- - - Select - - -</option>
            </select>
          </td>
        </tr>
        <tr>  <td class="tdText"   align="left" width="50%"><a href="pms_sch_map.jsp"><font color=green size=1><b>Mapped Schemes List</b></font></td>
          <td class="tdText"   align="right"><a href="benscheme.jsp"><font color=green size=1><b>Beneficiary Schemes List</b></font></td>
          </tr>
           <tr class="tdH" >
          <td colspan="2" align="center">
            <input type="button" name="cmdexit" value="Exit" id="cmdlist"  class="fb2"
                   onclick="exitwindow();"/>
            <input type="button" name="cmdexit" value="Print" id="cmdreport"  class="fb2"
                   onclick="reportlist();"/>
          </td>
        </tr>
      </table>
      
      
    </form></body>
</html>