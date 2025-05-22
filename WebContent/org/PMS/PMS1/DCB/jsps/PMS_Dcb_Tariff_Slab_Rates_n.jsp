<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

      <%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href='../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
<link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/Pms_Dcb_Tariff_slab_rate_n.js"></script>
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

<title>Insert title here</title>
<body onload="loadbeneficiarytype();">
<form name="PMS_Dcb_Tariff_Slab_Rates" action="">
<table  class="fb2" border="0" width="85%" align="center" cellpadding="0" cellspacing="0">
<tr>
<td>
 <table border="1" width="75%" align="center" cellpadding="0" cellspacing="0" class="border1"  >
                <tr class="tdH"  style="color:black">
                    <td colspan="3"  width="60%" align="right" style="border-right: 0px solid #dddddd;">
                       
                        <strong>  Tariff Slab Rates </strong>
                       </td>
                      <td colspan="1" align="right">
                         <strong> <label id="tariff_flag_label" ></label> </strong> 
                         
                    </td>
                </tr>
          		<tr class="table">
                    <td style="padding-left:10px;" width="40%" colspan="2">
                         Beneficiary Type
                        <font color="#ff2121">*</font>
                    </td>
                    <td style="padding-left:10px;" colspan="2">
                    	<select id="Beneficiary_type" onchange="loadbeneficiaryname();" style="width: 220pt"  class="select">
                    	    <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                      
                    </td>
                </tr>
                <tr class="table">
                    <td style="padding-left:10px;" width="40%" colspan="2" >
                        <label id="benname" >     Beneficiary Name </label> <font color="#ff2121">*</font>
                            
                  </td>
                <td style="padding-left:10px;" colspan="2"><select id="Beneficiary_Name" onchange="loadtariffmode();" tabindex="5" style="width:220pt" class="select">
                            <option value="" selected="selected">- - - Select -
                                                      
                                                                 - -</option>
                        </select>
                  </td>
                  </tr>
               <!-- onchange="loadtariffmode();loadlocation();"   <tr class="table">
                    <td style="padding-left:10px;" colspan="2">Whether Tariff Slab Rates Applicable for ? <font color="#ff2121">*</font></td>
                    <td style="padding-left:10px;" colspan="2">
                        <input type="radio" name="tariff_flag" id="tariff_flag" value="L" onclick="funtariff_flag();"></input>Location
                        <input type="radio" name="tariff_flag" id="tariff_flag" value="S" onclick="funtariff_flag();"></input>Scheme
                    </td>
   				</tr>
   				-->
    			<!-- <tr class="table">
    				<td style="padding-left:10px;" colspan="2">Tariff Slab Rate Applicability </td>
    				<td style="padding-left:10px;" colspan="2"><label id="tariff_flag" ></label></td>
    			</tr>-->
                <tr class="table">
                    <td style="padding-left:10px;" width="40%" colspan="2" >
                    <input type="hidden" name="tariff_flag" id="tariff_flag"></input>
                         <label id="metreloc">Metre Location</label>
                        <font color="#ff2121">*</font>
                    </td>
                    <td style="padding-left:10px;" colspan="2">
                    	<!--  <select id="Metre_Location" style="width: 320pt">-->
                    	<select id="Metre_Location" onChange="loadtarffic();" style="width: 220pt"  class="select">
                    	    <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                      
                    </td>
                </tr>
                <tr>
                			<td style="padding-left:10px;" colspan="2">Minimum Billing Qty Applicable (Y/N)  </td>
                			<td width=10%><input type="radio" name="op" id="op" checked="checked" value="1">Yes  
                	        <input type="radio"  id="op"  value=2 name="op" onclick="loadschemes();">No</td>
                	        <td><input type="text" id="" name="" class="tb4" /> </td>
                	        
                
                </tr>
                <tr>
                			<td style="padding-left:10px;" colspan="2">Allotted Qty Applicable (Y/N)    </td>
                			<td width=10%><input type="radio" name="op1" id="op1" checked="checked" value="1">Yes
                	        <input type="radio"  id="op1"  value=2 name="op1" onclick="loadschemes();">No</td>
                			 <td><input type="text" id="" name="" class="tb4" /> </td>
                </tr>
                
                <tr class="table">
                <td style="padding-left:10px;" width="40%" colspan="2" >
                         Tariff w.e.f
                        
                    </td>
                <td style="padding-left:10px;" colspan="2">
                  	  <input type="text" name="Tariff_w_e_f_1" id="Tariff_w_e_f_1" size="10" class="tb4"/>
                  	  
                    	<img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.PMS_Dcb_Tariff_Slab_Rates.Tariff_w_e_f_1)"></img>
                     </td>
                 </tr>
				<!-- <tr>
						<td colspan="2"> Slab Based on </td>
						<td colspan="2"><input type="checkbox" id="benname" class="radioStyle"><label id="benname">Beneficiary</label>
							<input type="checkbox" id="Location"  class="radioStyle"><label id="Location">Location</label> 
							<input type="checkbox"  class="radioStyle"><label id="Scheme ">Scheme </label>  
				</tr> -->
                </table>
                <br>
                 <table border="0" width="53%" align="center" cellpadding="0" cellspacing="0" class="border1" id="my_div">
                <tr align="center" style="color:black">
                
                    <td colspan="5" align="right"> 
                        <b><font size="2">**Quantity Consumption(KL)</font></b>
                        
                    </td>
                </tr>
                <tr class="tdH">
                	<td style="padding-left:10px;" >
                       
                        
                    </td>
                    <td style="padding-left:10px;"  align="center">
                        From
                        
                    </td>
                    <td style="padding-left:10px;"   align="center">
                   	To
                    
                    </td>
                    <td style="padding-left:10px;"   align="center">
                   	Tariff Rate
                     
                    </td>
                    
                   </tr>
                   <tr>
                   <td style="padding-left:10px;" width="13%" >
                         <!-- <input type="checkbox" name="" />&nbsp;&nbsp;&nbsp;&nbsp;<strong><font size="5"> &gt;</font></strong>-->
                        
                    </td>
                    <td  align="center"><input type="text" name="valus_starts_from_1" id="valus_starts_from_1" size=10 onkeyPress="return numonly(event);" class="tb4"></input> </td>
                    <td  align="center"><input type="text" name="valus_starts_to_1" id="valus_starts_to_1" size=10 onkeyPress="return numonly(event);" onblur="greateronly();" class="tb4"></input>  </td>
                    <td  align="center"><input type="text" name="tariff_rate_1" id="tariff_rate_1" size=10 onkeyPress="return numonly(event);" class="tb4"></input> </td>
                   
                    
                </tr>
                
               
               
                </table>
                 </td>
         </tr>
         <tr>
         	 
                   
                    <td style="padding-top:20px;"> 
                    <div align="center"> 
                    <input type="button" name="cmdsubmit_addrow" value="Add Another Slab" id="cmdsubmit_addrow" onClick="validcheck();" class="fb2" />
                    <input type="button" name="Save" value="Save" id="cmdsave" onclick="validation();" class="fb2" /> 
                    <input type="button" name="Exit" value="Exit" id="exit" onclick="exitwindow();" class="fb2" />
                    <input type="button" name="Clear" value="Clear" id="exit" onClick="refresh(); " class="fb2" /> 
                    </div>
                    </td>
                
         </tr>
</table>
<table  class="fb2" border="0" width="45%" align="center" cellpadding="0" cellspacing="0" class="border1" id="existing">

 	<tr>
    	<th class="tdH">From</th>
        <th class="tdH">To</th>
        <th class="tdH">Tariff Rate</th>
        <th class="tdH">Tariff w.e.f</th>
   </tr>
   <tbody id="getvaluerows" class="table"></tbody>
  </table>
</form>
</body>
</html>