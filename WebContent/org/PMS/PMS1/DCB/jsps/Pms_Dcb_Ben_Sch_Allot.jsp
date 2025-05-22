<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/Pms_Dcb_Ben_Sch_Allot.js"></script>
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
<title> Alloted Quantity  Details | TWAD Nest - Phase II</title>
</head>
<body onload="loadbeneficiarytype();">
<form name="Pms_Dcb_Ben_Sch_Allot" action="">
<table border="0" width="65%" align="center" cellpadding="0" cellspacing="0" class="border1">
	<tr class="tdH" align="center" style="color:black">
        <td colspan="4">
             <div align="center">
                 <strong>  Alloted Quantity  Details    </strong>
              </div> 
       </td>
   </tr>
   <tr class="table">
       <td style="padding-left:10px;" width="40%" colspan="2">
            Beneficiary Type
       		<font color="#ff2121">*</font>
       		
       </td>
       <td style="padding-left:10px;" colspan="2">
           	<select id="Beneficiary_type" onChange="fun_Alloted_flag();"  style="width: 220pt">
           	    <option value="" selected="selected">- - - Select - - -</option>
           </select>
       </td>
   </tr>
  <!--  <tr class="table">
                    <td style="padding-left:10px;" colspan="2">Whether Alloted Quantity based Billing Applicable?
 <font color="#ff2121">*</font></td>
                    <td style="padding-left:10px;" colspan="2">
                        <input type="radio" name="Alloted_flag" id="Alloted_flag" value="B" onclick="fun_Alloted_flag();"></input>Beneficiarywise
                        <input type="radio" name="Alloted_flag" id="Alloted_flag" value="S" onclick="fun_Alloted_flag();"></input>Beneficiary,Scheme wise
                    </td>
   </tr>-->
   <tr class="table">
       <td style="padding-left:10px;"  colspan="2" >
       	   <label id="benname" >     Beneficiary Name </label> <font color="#ff2121">*</font>
       </td>
      <td style="padding-left:10px;" colspan="2">
     
      	<!-- <select id="Beneficiary_Name" onchange="loadschemes(); loadvaluesgrid();" tabindex="5"  style="width: 320pt">-->
      	 <select id="Beneficiary_Name" onchange="loadallotedflag();" tabindex="5"  style="width: 220pt">
        	<option value="" selected="selected">- - - Select - - -</option>
        </select>
        <input type="hidden" name="Alloted_flag" id="Alloted_flag"></input>
      </td>
    </tr>
    </table>
    <div id="schemeload">
    <table border="0" width="65%" align="center" cellpadding="0" cellspacing="0" class="border1">
    <tr class="table">
       <td style="padding-left:10px;" width="40%" colspan="2" >
       	   <label id="benname" >     Scheme </label> <font color="#ff2121">*</font>
       </td> 
      <td style="padding-left:10px;" colspan="2">
      	<select id="schemename" tabindex="5" onchange="loadgrid();" style="width: 220pt">
        	<option value="" selected="selected">- - - Select - - -</option>
        </select>
      </td>
    </tr>
    </table>
    </div>
    <table border="0" width="65%" align="center" cellpadding="0" cellspacing="0" class="border1">
    <tr class="table">
    	<td colspan="2" style="padding-left:10px;" width="40%">
                 Allotted Quantity <small>(KL)</small>&nbsp;&nbsp;&nbsp;&nbsp;                        
        </td>
        <td colspan="2" style="padding-left:10px;"><input type="text" name="Allotted_Qty" maxlength="35" size="6" id="Allotted_Qty" onkeyPress="return numonly(event);"/>
        </td>
    </tr>
    <tr class="table">
    	<td colspan="2" style="padding-left:10px;" width="40%">Minimum Billing Quantity <small>(KL)</small>
        </td>
        <td colspan="2" style="padding-left:10px;">
        	 <input type="text" name="Min_bill_Qty" maxlength="35" size="6" id="Min_bill_Qty" onkeyPress="return numonly(event);"/>
        </td>
     </tr> 
     <tr class="table">
     	<td colspan="2" style="padding-left:10px;"> Excess Tariff Rate<small>(Rs)</small>
        </td>
        <td colspan="2" style="padding-left:10px;">
        	<input type="text" name="Excess_Tariff_Rate" maxlength="35" size="6" id="Excess_Tariff_Rate" onkeyPress="return numonly(event);"/>
        	
        </td>
        
     </tr>
     <tr>
     	<td style="padding-top:20px;" colspan="4"> 
        	<div align="center"> 
                   
                    <input type="button" name="Save" value="Save" id="cmdsave" onclick="funsave();"/> 
                    <input type="button" name="Exit" value="Exit" id="exit" onclick="exitwindow();"/>
                    <input type="button" name="Clear" value="Clear" id="exit" onClick="refresh();"/> 
                    </div>
          </td>
                
    </tr>
</table>
<table id="existing" border="0" width="65%" align="center" cellpadding="0" cellspacing="0" class="border1">
	<tr>
    	<th class="tdH">Beneficiary Name</th>
        <th class="tdH">Allotted Quantity<small>(KL)</small></th>
        <th class="tdH">Minimum Billing Quantity<small>(KL)</small></th>
        <th class="tdH">Excess Tariff Rate<small>(Rs)</small></th>
        
   </tr>
   <tbody id="getvaluerows" class="table"></tbody>
  </table>
</form>
</body>
</html>