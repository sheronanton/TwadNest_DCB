<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<title>Beneficiary Meter Report  | TWAD Nest - Phase II</title>
<script type="text/javascript" src="../scripts/Pms_Dcb_Mst_Beneficiary_Scheme_Report.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
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
  </head>
  				        <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
  
  <body onload="divisionname();loadbeneficiarytype();loadsubdivision();loadReport();flash()">
  <form name="beneficary_meter_report" action="">
    <table  class="fb2" border="1" width="100%" align="center" cellpadding="0" cellspacing="0"  style="BORDER-COLLAPSE: collapse"  borderColor="#92c2d8">
    <tr  class="tdH" align="center" >
                    <td colspan="6">
                        <div align="center">
                          <font size=3>  Beneficiary Meter Report </font>
                            <label id="divisionname"></label> <label id="msg" name="msg"></label>
                             
                        </div>
                    </td>
                </tr>
        <tr>
                    <td  class="tdText"> 
   
                         Beneficiary Type
               
                    </td>
                   <td style="padding-left:100px;">
                         <select id="Beneficiary_type" class="select" onchange="loadbeneficiaryname();loadReport();">
                               <option value="0" selected="selected">- - - Select - - -</option>
                        </select>
                      
                    </td>
        </tr>
         <tr>
              <td class="tdText">
                            Sub Division 
                </td>
                <td style="padding-left:100px;">
              
                        <select id="SubDivision" tabindex="2" class="select" onchange="loadReport();" >
                            <option value="0" selected="selected">- - - Select - - -</option>
                        </select>
                </td>
        </tr>
         <tr>
                <td class="tdText">
                            Beneficiary Name 
                            
                 </td>
                 <td style="padding-left:100px;">
                        <select id="Beneficiary_Name" class="select" onchange="loadReport();">
                            <option value="0" selected="selected">- - - Select - - -</option>
                        </select> 
                </td>
        </tr>  
    </table>
    <br>
    
    <table  class="fb2" id="existing" border="1" width="100%" align="center"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
                <tr  class="tdH">
                   <th class="tdText">Sno</th>	
                   <th class="tdText">Beneficiary Name</th>
                     <th class="tdText">Beneficiary Type</th>
                     <th class="tdText">Sub Division</th>
                   <th class="tdText" id="meterlabel">Scheme Name</th>
                   <th class="tdText">Scheme Type</th>
                   <th class="tdText">Total<br>Meter<br>Locations</th>
                   <th class="tdText">Tariff Mode</th>	
                </tr>
                <tbody id="getvaluerows" class="tdText">
                
                    
                
                </tbody>
                
                 
            </table>
            <br>
            <table align="center"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
            <tr>
            <td>
             <input type="button" name="cmdexit" value="Exit" id="cmdlist" onclick="exitwindow();" class="fb2"/>
            </td>
             </tr>
             </table>
             <input type=hidden id="pr_status" name="pr_status" value="0">
        </form>
  			 
  </body>
</html>