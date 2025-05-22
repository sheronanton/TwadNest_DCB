<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="Servlets.Security.classes.UserProfile"%>
    <%@ page import="Servlets.Security.classes.UserProfile,java.sql.*,Servlets.PMS.PMS1.DCB.servlets.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href='../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
<link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/Pms_Dcb_Tariff_slab_rate.js"></script>
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
				
				<script type="text/javascript">
				function pre_check()
				{
				
						var min=document.getElementById("mini_flag").checked;
						var alt=document.getElementById("allotted_flag").checked;
						var rowcnt=document.getElementById("rowcnt").value;
						 
						if( (alt==true && rowcnt==1) || (min==true && rowcnt==1) )
						{ 
							alert("\n Click Add another Slab , and Check if last slab \n")
						 document.getElementById("cmdsave").disabled =true;
						}else
						{
							 validation();
						}
				
				}
				
				
				</script>
</head>

<title>Tariff Slab Rates  | TWAD Nest - Phase II</title>
<body onload="loadbeneficiarytype();">
<%
	String userid="112",Office_id="",Office_Name="";
	Controller obj=new Controller();
	Connection con=null;
	try
	{     
		con=obj.con();
		obj.createStatement(con);
		userid=(String)session.getAttribute("UserId");
		
		if(userid==null)
		{
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		 
		//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID  from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	}catch(Exception e) 
	{
		
	}
	
%>
<form name="PMS_Dcb_Tariff_Slab_Rates" action="">
<table  class="fb2" border="0" width="75%" align="center" cellpadding="4" cellspacing="0">
<tr>
<td>
 <table border="1" width="100%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" >
                <tr class="tdH"  style="color:black">
                    <td colspan="4"   align="center" style="border-right: 0px solid #dddddd;">
                        
                        <strong>  Tariff Slab Rates -<%=Office_Name%> </strong>
                      </td>
                      </tr>
                      <tr>
                      <td  colspan="4" align="right">
                         <font color="blue"><label  id="tariff_flag_label" ></label></font>  
                         
                    </td>
                </tr> 
          		<tr class="tdText">
                    <td style="padding-left:10px;" width="40%" colspan="2">
                         Beneficiary Type
                        <font color="#ff2121">*</font>
                    </td>
                    <td style="padding-left:10px;" colspan="2">
                    	<select id="Beneficiary_type" onchange="loadbeneficiaryname()" style="width: 220pt" class="select">
                    	    <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                      
                    </td>
                </tr>
                <tr class="tdText">
                    <td style="padding-left:10px;" width="40%" colspan="2" >
                        <label id="benname" >     Beneficiary Name </label> <font color="#ff2121">*</font>
                            
                  </td>
                <td style="padding-left:10px;" colspan="2"><select id="Beneficiary_Name" onchange="loadflag();" tabindex="5" style="width:220pt"  class="select">
                            <option value="" selected="selected">- - - Select -
                                                      
                                                                 - -</option>
                        </select>
                        <input type="hidden" id="tariff_flag" name="tariff_flag"  />
                        
                  </td>
                  </tr>
                  </table>
                  <div id="schemesload">
                 <table border="1" width="100%" align="center" cellpadding="4" cellspacing="0"style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
                 
                  <tr class="tdText">
                    <td style="padding-left:10px;" width="40%" colspan="2" >
                            Schemes  <font color="#ff2121">*</font>
                            
                  </td>
                <td style="padding-left:10px;" colspan="2"><select id="Schemes" tabindex="5" onchange="loadlocation();"  class="select">
                            <option value="" selected="selected" >- - - Select -
                                                      
                                                                 - -</option>
                        </select>
                         <input type="hidden" id="tariff_flag_one" name="tariff_flag_one"  />
                  </td>
                  </tr>
                  
                  </table>
                  </div>
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
    			<div id="locationload">
    			 <table   width="100%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
                <tr class="tdText">
                    <td style="padding-left:10px;" width="40%" colspan="2" >
                   <!-- > <input type="hidden" name="tariff_flag" id="tariff_flag"></input>-->
                         <label id="metreloc">Metre Location</label>
                        <font color="#ff2121">*</font>
                    </td>
                    <td style="padding-left:10px;" colspan="2">
                    	<!--  <select id="Metre_Location" style="width: 320pt">-->
                    	<select id="Metre_Location" style="width: 220pt"  class="select" onChange="loadlocationgrid();"	>
                    	    <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                      
                    </td>
                </tr>
                </table>
                </div>
                 <table border="1" width="100%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="skyblue">
                <tr class="tdText">
                <td style="padding-left:10px;" width="40%" colspan="2" >
                         Tariff w.e.f <font color="#ff2121">*</font>
                        
                    </td>
                <td style="padding-left:10px;" colspan="2">
                  	  <input class=tb4 type="text" name="Tariff_w_e_f_1" id="Tariff_w_e_f_1" size="10"/>
                  	  
                    	<img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.PMS_Dcb_Tariff_Slab_Rates.Tariff_w_e_f_1)"></img>
                     </td>
                 </tr>
				<tr class="tdText">
                			<td style="padding-left:10px;" colspan="2">Minimum Billing Qty Applicable (Y/N)  </td>
                			<td width=30%>
                				<input type="radio" name="mini_flag" id="mini_flag"  value="Y" onClick="fun_mini_flag();">Yes  
                	        	<input type="radio"  id="mini_flag"  name="mini_flag" checked="checked" value="N" onClick="fun_mini_flag();">No</td>
                	        <td><input class=tb4 type="text" id="min_qty" name=min_qty  style="background-color: #ececec" readOnly="readOnly" onkeyPress="return numonly(event);"/> <small>(KL) Per Day</small></td>
                	        
                
                </tr>
                <tr class="tdText">
                			<td style="padding-left:10px;" colspan="2">Allotted Qty Applicable (Y/N)    </td>
                			<td width=30%>
                				<input type="radio" name="allotted_flag" id="allotted_flag" checked="checked" value="Y" onClick="fun_allotted_flag();">Yes
                	        	<input type="radio"  id="allotted_flag" name="allotted_flag" checked="checked"  value="N" onClick="fun_allotted_flag();">No</td>
                			 <td><input type="text" class="tb4" id="allot_flag" name="allot_flag"   style="background-color: #ececec" readOnly="readOnly"  onkeyPress="return numonly(event);"/> <small>(KL)Per Day  </small>  </td>
                </tr>
               
                </table>
                <br>
                <table border="1" width="75%" align="center" cellpadding="4" cellspacing="0" id="my_div" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
                <tr align="center" style="color:black">
                	<td></td>
                    <td colspan="2" class="tdText">
                         Quantity in KL
                        
                    </td>
                    <td></td>
                </tr>
                <tr class="tdH">
                	<td style="padding-left:10px;" class="tdText">
                       
                        Check if <br> Last Slab
                    </td>
                    <td align="center" ="padding-left:10px;" class="tdText">
                        From(KLD)<br>Per Day</td>
                    <td align="center" style="padding-left:10px;" class="tdText">
                   	To (KLD)<br>Per Day</td>
                    <td align="center" style="padding-left:10px;" class="tdText">
                   	Tariff Rate <small>(Rs)</small>
                     
              </td>
       </tr>
       <tr>
           
        	<td align="center" width="15%"><input type="hidden" name="maxcheck_value" id="maxcheck_value" value="N"><input type="checkbox" name="maxcheck" id="maxcheck" value='Y' onClick="checkfun_main(this.value);">  </td>
        	<td align="center"><input type="text" name="valus_starts_from_1"  class="tb4"  id="valus_starts_from_1" value="0" readOnly="readonly" style="background-color: #ececec" size=10 onkeyPress="return numonly(event);" ></input> </td>
        	<td align="center"><input type="text" name="valus_starts_to_1"  class="tb4" id="valus_starts_to_1"  value="0" readOnly="readonly" style="background-color: #ececec" size=10 onkeyPress="return numonly(event);" onblur="greateronly();fun_alloted_gr();" ></input>  </td>
        	<td align="center"><input type="text" name="tariff_rate_1"  class="tb4" id="tariff_rate_1" size=10 onkeyPress="return numonly(event);"></input> </td>
      	</tr>
        <tbody id="test"></tbody>
      </table>
     
       </td>
   </tr>
   <tr>
        <td style="padding-top:20px;">  
              <div align="center"> 
                    <input type="button" name="cmdsubmit_addrow" value="Add Another Slab" id="cmdsubmit_addrow" onClick="return validcheck();" class="fb2"/>
                    <input type="button" name="Save" value="Save" id="cmdsave" onclick="pre_check();"  class="fb2"/> 
                    <input type="button" name="Exit" value="Exit" id="exit" onclick="exitwindow();"  class="fb2"/>
                    <input type="button" name="Clear" value="Clear" id="clear" onclick="refresh();"  class="fb2"/><img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=9','mywindow','scrollbars=yes')"> 
                    </div>
                    </td>
                
         </tr>
</table>
<table border="1" width="75%" align="center" cellpadding="4" cellspacing="0" id="existing" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
	<tr> 
		<td colspan="4">
			<font color=blue> <B>Entered Tariff Rate</B></font> 
		</td>
	</tr>
 	<tr class="tdText"> 
  	<th>From</th>
    <th>To</th>
    <th>Tariff Rate</th>
    <th>Tariff w.e.f</th>
 </tr>
 <tbody id="getvaluerows" class="table" style="background-color: white;"></tbody>
  </table>
  
  <input type="hidden" id="rowcnt" name="rowcnt" value="1">
</form> 
</body>
</html>