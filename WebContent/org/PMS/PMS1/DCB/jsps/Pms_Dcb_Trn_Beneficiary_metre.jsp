<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">

<html>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
		<%@ page import="Servlets.Security.classes.UserProfile"%>
    <head>
    		<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
       <meta http-equiv="Content-Type"  content="text/html; charset=windows-1252 "/>
       <link href='../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
       <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
       <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>       
       <script type="text/javascript" src="../scripts/Pms_Dcb_Beneficiary_metre.js"></script>
       <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
       <script type="text/javascript" src="../scripts/msg.js"></script>
       <script type="text/javascript" src="../scripts/cellcreate.js"></script>          
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
        <title> Beneficiary Meter Details | TWAD Nest - Phase II</title>
        <script type="text/javascript">
	        function focusloss()
			{	
				 document.getElementById("Beneficiary_type").selectedIndex = "0";
			} 
        </script>
  	  </head><!--doFunction('get');-->
    	<body onload="divisionname();loadbeneficiarytype();loadschemes();loadsubdivision();">
    	<form name="beneficary_meter" action="">
    	<%
    		String OFFICE_LEVEL_ID="0",Office_id="0",Office_Name="0",Office_id_sub="0",userid="0";
    	 	Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
							String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			
			Controller obj=new Controller();
			Connection con;
			try
			{
					   	con=obj.con();
					   	obj.createStatement(con);
					   	try
						{
						userid=(String)session.getAttribute("UserId");
						}catch(Exception e) {userid="0";}
						if(userid==null)
						{
				 			response.sendRedirect(request.getContextPath()+"/index.jsp");
						}
							
						Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
						
					Office_id_sub=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
					
							   	if (Office_id.equals("")) Office_id="0";
							   	Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");			
							
			OFFICE_LEVEL_ID=obj.getValue("com_mst_offices", "OFFICE_LEVEL_ID","where OFFICE_ID="+Office_id+ "  ");
			String division_list="";
			try
			{
		
				 division_list=obj.combo_str("com_mst_offices","office_name","office_id","where   office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map)  ","div","class='select' onchange=focusloss()");
				
				
			//	division_list=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where   circle_office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","div","class='select' onchange=loadsubdivision(),focusloss();");
			Office_Name="";
			}catch(Exception e) 
			{
			out.println(e);
			}				
			
			
    	 %>
            <table  width="75%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" >
                <tr class="tdH"  style="color:black">
                    <td colspan="4" align="center">                    
                        <strong>  Beneficiary Meter Details 
                            <label id="divisionname"></label>  </strong>                             
                    </td>
                </tr>
                <tr class="tdText">
                    <td style="padding-left:10px;" width="30%" colspan="2">Meter Location ID </td>
                    <td colspan="2" style="padding-left:10px;">
                        <input type="text" name="Meter_Sno" id="Meter_Sno"
                               maxlength="5" size="5" readonly="readonly"
                               style="background-color: #ececec" tabindex="1"/>
                        <small>(Auto generated)</small>
                    </td>
                </tr>
                	<%
					if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
					{
					%> 
					<tr>
				 	 <td style="padding-left:10px;" colspan="2"><font class="tdText">  Division</font> </td>
				 	 <td>
				 	&nbsp;&nbsp;<%=division_list%>
				 	</td>
				 	</tr>
				 	<%}%> 
                <tr class="tdText">
                    <td style="padding-left:10px;" colspan="2"> Beneficiary Type  
                        <font color="#ff2121">*</font>
                    </td>
                    <td style="padding-left:10px;" colspan="2">
                        <select id="Beneficiary_type" onchange="districload(); loadhabitations(); loadbeneficiaryname();"  tabindex="2" class="select">
                            <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                      
                    </td>
                </tr>
                </table>
                <div id="district">
                <table  width="75%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" >
                    <tr class="tdText">
                    	<td width="30%" colspan="1" style="padding-left:10px;">District</td>
                        <td style="padding-left:10px;">
		  						<select id="district_Name" name="district_Name"  class="select"   onchange="loadblocks();" style="min-width: 190px; max-width: 190px; width : 190px;" tabindex="3">
                           		<option value="" selected="selected">- - - Select - - -</option>
         			           	</select> 
						</td>
                        <td  style="padding-left:10px;">Block&nbsp;&nbsp;</td>
                        <td style="padding-left:10px;" width="25%">
                            <select id="block_Name" name="block_Name" class="select" onchange="loadbenname();" style="min-width: 190px; max-width: 190px; width : 190px;" size="1" tabindex="4">
                           <option value="" selected="selected">- - - Select - - -</option>
                    </select> 
                        </td>
                    </tr>
                </table>
                </div>
                 <table  width="75%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse">
               <tr class="tdText">
                    <td style="padding-left:10px;" colspan="2" width="30%">
                        <label id="benname" >     Beneficiary Name </label> <font color="#ff2121">*</font>
                            
                  </td>
                        <td style="padding-left:10px;">
                        <select  class="select"   id="Beneficiary_Name" onchange="getvaluegrid();loadcategory(); loadhabitationlist();cbenable('Schemes',false);cbenable('SubDivision',false);" tabindex="5">
                            <option value="" selected="selected"> - - - Select -- -</option>
                        </select> 
             <input type="hidden" name="BENEFICIARY_TYPE_ID" maxlength="35" size="6" id="BENEFICIARY_TYPE_ID" />
             <input type="hidden" name="OTHERS_PRIVATE_SNO" maxlength="35" size="6" id="OTHERS_PRIVATE_SNO"/>
             <input type="hidden" name="VILLAGE_PANCHAYAT_SNO" maxlength="35" size="6" id="VILLAGE_PANCHAYAT_SNO"/>
             <input type="hidden" name="URBANLB_SNO" maxlength="35" size="6" id="URBANLB_SNO"/>
             <input type="hidden" name="Consumption_Category" maxlength="35" size="35" id="Consumption_Category" style="background-color: #ececec" tabindex="6"/>
             <input type="hidden" name="Consumption_Categoryid" maxlength="35" id="Consumption_Categoryid"/>
             <input type="hidden" id="div1" name="div1"  value="<%=Office_id_sub%>">
        </td>
            
               <!-- <tr class="tdText">
                        <td style="padding-left:10px;" colspan="2">    Consumption Category</td>
                        <td style="padding-left:10px;">
                            <input type="text" name="Consumption_Category"
                               maxlength="35" readonly="readonly" size="35"
                               id="Consumption_Category"
                               style="background-color: #ececec" tabindex="6"/>
                               <input type="hidden" name="Consumption_Categoryid"
                               maxlength="35" id="Consumption_Categoryid"/>
                        </td>
                </tr>-->
                 
                <!--  <tr class="tdText">
                 	 <td style="padding-left:10px;" colspan="2"> Mode of Tariff</td>
                 	  <td style="padding-left:10px;"><label id="mode_of_tariff"></label>
                       </td>
                   </tr>   
               -->
               	
                 <tr class="tdText">
                    <td style="padding-left:10px;" colspan="2">
                            Sub Division <font color="#ff2121">*</font>
                            
                        </td>
                        <td style="padding-left:10px;" colspan="2"><!--<select id="SubDivision" onchange="subdivision();">-->
                        <select class="select"    id="SubDivision" tabindex="2" onchange="schemecheck();habcheckdup();metercheck()" tabindex="6">
                            <option value="" selected="selected">- - - Select - - -</option>
                        </select></td>
                    </tr>
                
               
                    <tr class="tdText">
                        <td style="padding-left:10px;" colspan="2"> Schemes <font color="#ff2121">* &nbsp;&nbsp;</font>&nbsp;&nbsp;</td>
                        <td style="padding-left:10px;" colspan="2">
                            <!--<select id="Schemes" onchange="Schemesval();">-->
                                                            
                            <select class="select"   id="Schemes" tabindex="3" onchange="loadschname(); schemecheck();habcheckdup();metercheck();" tabindex="7"  style="width: 20em;" >
                            <option value="" selected="selected">- - - Select - - -</option>
                        </select>
                         <input type="hidden" name="SCH_TYPE_ID" maxlength="35"
                               size="6" id="SCH_TYPE_ID"/>
                        </td>

                </tr>
              
               
            </table>
            <div id="Habitation">
                <table  width="75%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse"  borderColor="#92c2d8">
                    <tr class="tdText">
                        <td style="padding-left:10px;" width="30%">Habitation Name</td>
                        <td style="padding-left:10px;">
                            <select class="select" id="Habitation_Name" onchange="loadhabname(); habcheckdup();" tabindex="8" >
                                <option value="" selected="selected">- - -
                                                                     Select - - -</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
             <div id="Metre_Location_div">
            <table  width="75%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse"  borderColor="#92c2d8">
                <tr class="tdText">
               
                    <td style="padding-left:10px;" colspan="2"  width="30%"><label id="location">Meter Location</label><font color="#ff2121">*</font></td>
                   	<td style="padding-left:10px;" colspan="2" ><input type="text" name="Metre_Location"  class="tb7" maxlength="35" size="35" id="Metre_Location" tabindex="9" onkeyup="this.value = (this.value).toUpperCase();" onblur="metercheck()";/></td>
                </tr>
                </table>
                 </div>
                  <div id="tariff_mode_div">
                <table  width="75%" align="center" cellpadding="0" cellspacing="0" class="border1">
               
                </table>
                </div>
                <table  width="75%" align="center" cellpadding="0" cellspacing="0" style="BORDER-COLLAPSE: collapse"  borderColor="#92c2d8">
                <tr class="tdText">
                    <td style="padding-left:10px;" colspan="2" width="30%" >Whether Meter fixed? <font color="#ff2121">*</font></td>
                    <td style="padding-left:10px;" colspan="2">
                        <input type="radio" name="meterfixed" id="meterfixed" value="y" onclick="meterdisplay()"></input>Yes
                        <input type="radio" name="meterfixed" id="meterfixed" value="n" onclick="meterdisplay()"></input>No
                    </td>
                </tr>
            </table>  
            <div id="prevref">
                <table  width="75%" align="center" cellpadding="0" cellspacing="0" style="BORDER-COLLAPSE: collapse"  borderColor="#92c2d8">
                    <tr class="tdText">
                        <td style="padding-left:10px;"  width="25%"  >Whether Meter Working?  <font color="#ff2121">*</font></td>
                        <td colspan="2"  style="padding-left:10px;" >
                            <input type="radio" name="meterworking" id="meterworking" value="y" onclick="chmeterworking()"></input>Yes
                            <input type="radio" name="meterworking" id="meterworking" value="n" onclick="chmeterworking()"></input>No
                        </td>
                    </tr> 
                    <tr class="tdText">
                        <td style="padding-left:10px;"  width="25%">Meter Type<font color="#ff2121">*</font></td>
                        <td colspan="2" style="padding-left:10px;">
                        <input type="radio" name="Metre_Type" id="Metre_Type" value="1" onclick="MetreType()"></input>KL
                         <input type="radio" name="Metre_Type" id="Metre_Type" value="2" onclick="MetreType()"></input>ML
                          
                        </td>
                    </tr>
                    <tr class="tdText">
                        <td   style="padding-left:10px;">Multiplying factor</td>
                        <td colspan="2" style="padding-left:10px;">
                            <input type="text" name="Multiply_factor"
                                   maxlength="35" size="4" id="Multiply_factor"  class="tb4" value="1" onkeyPress="return numonly(event);" onblur="checkzero();"/>
                        </td>
                    </tr>
                    <tr class="tdText">
                       <td width="30%" colspan="1" style="padding-left:10px;">Meter initial reading <font color="#ff2121">*</font></td>
                        <td width="18%" style="padding-left:10px;">
                        	<input type="text" name="Metre_init_reading"   class="tb5" id="Metre_init_reading" maxlength="35" size="6"  onkeyPress="return numonly(event);"/></td>
                        <td width="31%" style="padding-left:10px;">Initial Reading Record date&nbsp;&nbsp;</td>
                        <td style="padding-left:10px;">
                            <input type="text" name="Init_Reading_Record_date"  class="tb4" maxlength="35" size="8" id="Init_Reading_Record_date" />
                            <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.beneficary_meter.Init_Reading_Record_date)"></img>
                        </td>
                    </tr>
                   </table> 
            </div>
            <table  width="75%" align="center" cellpadding="0" cellspacing="0"style="BORDER-COLLAPSE: collapse"  borderColor="#92c2d8">
                     <tr class="tdText">
                        <td  style="padding-left:10px;" >Whether Parent Meter Reading Applicable?<font color="#ff2121">*</font></td>
                        <td colspan="2" style="padding-left:10px;">
                            <input type="radio" name="parentmetre" id="parentmetre" value="y" onclick="parentmetrefun()"></input>Yes
                            <input type="radio" name="parentmetre" id="parentmetre" value="n" checked='checked' onclick="parentmetrefun()"></input>No
                        </td>
                    </tr>
                </table>
               <table  width="75%" align="center" cellpadding="0" cellspacing="0" class="border1">
                <tr class="tdText">
                    	<td width="32%" colspan="1" style="padding-left:10px;">Service Connection No&nbsp;&nbsp;&nbsp;&nbsp;</td>
                 		<td width="18%" style="padding-left:10px;">
                  		<input type="text" name="Service_Connection" class="tb5" maxlength="35" size="6" id="Service_Connection" onkeyPress="return numonly(event);"/></td>
                        <td width="31%" style="padding-left:10px;">Service Connection Date</td>
                        <td width="19%" style="padding-left:10px;">
                           <input type="text" name="Service_Connection_date"  class="tb4" maxlength="35" size="8" id="Service_Connection_date" />
                               <img src="../../../../../images/calendr3.gif" alt="calendar" onclick="showCalendarControl(document.beneficary_meter.Service_Connection_date)"></img>                            
                  		</td>
                </tr>
                <tr class="tdText">
                	 <td>Cancel   (w.e.f) </td>
                	 <td colspan="1" align="left"><input class="tb5" type="text" name="cdate" id="cdate" maxlength="10" size="10" onFocus="javascript:vDateType='3'"  value="" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" readonly="readonly"></input>
        <img src="../../../../../images/calendr3.gif" onclick=" showCalendarControl(document.beneficary_meter.cdate);"
        alt="Show Calendar" height="24" width="19"></img>
                	
                	</td>
                </tr>
                   
            </table>
            <table  width="75%" align="center" cellpadding="0" cellspacing="0" class="border1">
                <tr class="tdH" align="center">
                    <td>
                        <input type="button" name="cmdadd" value="Add" id="cmdadd" onclick="doFunction('Add');" class="fb2"/>
                        <input type="button" name="cmdupdate" value="Update"  class="fb2" id="cmdupdate" onclick="doFunction('Update');"/> 
                        <input type="button" name="cmddelete" value="Delete"  class="fb2" id="cmddelete" onclick="doFunction('Delete');"/>
                        <input type="button" name="cmdreport" value="View"  class="fb2" id="cmdreport" onclick="popup();"/>
                        <input type="button" name="cmdclear" value="Clear"  class="fb2" id="cmdclear" onclick="refresh()"/>
                        <input type="button" name="cmdexit" value="Exit"  class="fb2" id="cmdlist" onclick="exitwindow();"/> <img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=7','mywindow','scrollbars=yes')">
                    </td>
                </tr>
            </table>
       <table id="existing"  width="75%" align="center"   class="table">
                <tr>                 
                   <th class="tdH">Edit</th>
                   <th class="tdH">Sno</th>
                   <th class="tdH" id="meterlabel">Meter Location</th>
                   <th class="tdH">Sub Division</th>
                    <th class="tdH">Scheme</th>
                    <th class="tdH">Meter fixed</th>
                    <th class="tdH">Meter Working</th>
                </tr>
                <tbody id="getvaluerows" class="tdText"></tbody>
            </table>
        </form>
        <%
			}catch(Exception e) 
			{
			out.println(e);
			}	
        %>
        </body>
</html>