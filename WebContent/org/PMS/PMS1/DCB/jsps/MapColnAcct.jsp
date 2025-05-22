<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%> 
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Account Mapping  | TWAD Nest - Phase II</title>
    <link href='../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
    <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
     <script type="text/javascript" src="../scripts/MapColnAcct.js"></script>
     <script type="text/javascript" src="../scripts/Pagination.js"></script>
     
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
     <script type="text/javascript" src="../../../../Library/scripts/CalendarControl.js"></script>
     
  </head>
  
  <body onload="callServer('LoadCmb'); callServer('Get');">
  	<form name="frmMapDivDist" action="">
  		<table  class="fb2" border="0" width="85%" align="center">

		    <tr class="tdH" align="center" style="color:black">
		        <td colspan="2" class="tdText"> <div align="center"><strong>DCB Receipts Account Mapping</strong></div></td>
		    </tr>
		
		    <tr class="tdText">
		        <td>Scheme Type</td>
		        <td>
		        	:&nbsp;&nbsp;<select name="sch" id="sch" onchange="callServer('Get');"  class="select">
		        		<option value="">--Select Scheme Type--</option>
		        	</select>
		        </td>
		    </tr>
		    
		    <tr class="tdText">
		        <td>Collection Type</td>
		        <td>:&nbsp;
		        	<select name="ctyp" id="ctyp" onchange="callServer('Get');"  class="select">
		        		<option value="">--Select Collection Type--</option>
		        	</select>
		        </td>
		    </tr>
		    
                          <tr class="tdText">
                            <td>Account Head Code</td>
                            <td>:&nbsp;
                                <input type=text name=txtaccountheadcode size=8 maxlength=8  class="tb3" onchange="callServer('AcctHead');" onkeypress="return numbersonly1(event,this)">
                                <input type="text" name="txtaccountheadname" size="90" disabled=""  class="select"/>
                                <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                            </td>
                          </tr>

		    		    
		    <tr class="tdText">
		        <td>With Effect From</td>
		        <td>
	              <div align="left">:&nbsp;
	               <input type="text" name="wef" id="wef"  class="tb4"
	                       maxlength="10" size="11"
	                       onfocus="javascript:vDateType='3';  "
	                       onkeypress="return calins(event,this);"/>
	                 
	                <img src="../../../../../images/calendr3.gif"
	                     onclick="showCalendarControl(document.getElementById('wef'));"
	                     alt="Show Calendar"></img>
                 </div>
             </td>
		        
		    </tr>
		    
		    		    
		    <tr class="tdText">
		        <td>Status</td>
		        <td>
		        	:&nbsp;&nbsp;<select name="status" id="status" onchange="callServer('Get');" class="select">
		        		<option value="L">Live</option>
		        		<option value="C">Cancel</option>
		        	</select>
		        </td>
		    </tr>
		     <tr class="tdText">
		        <td>Disable in FAS General Receipt </td>
		        <td>
		        	:&nbsp;&nbsp;<select name="fstatus" id="fstatus"  class="select">
		        	    <option value="0">Select</option>
		        		<option value="Y">Yes</option>
		        		<option value="N">No</option>
		        	</select>
		        </td>
		    </tr>
		    
		    <tr class="tdH" align="center">
		        <td colspan="2" >               
		                <input type="button" name="Add"  class="fb2" value="Add" id="Add" onclick="callServer('Add')"/>
		                <input type="button" name="Update"  class="fb2" value="Update" id="Update" onclick="callServer('Update')" disabled/>
		                <input type="button" name="Delete"  class="fb2" value="Delete" id="Delete" onclick="callServer('Delete')" disabled/>
		                <input type="reset" name="Clear" class="fb2"  value="Clear"  id="Clear" onclick="clearForm()"/>
		                <img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=20','mywindow','width=600,height=400,scrollbars=yes')"/><input type="button" name="Exit"  class="fb2" value="Exit"  id="Exit" onclick="self.close()"/>              
		        </td>
		    </tr>
		   
    	</table>
     
     	<br/>
     	
	    <table  class="fb2" border="0" width="85%" align="center"  class="tdText" >
	    	<tr>
	    		<td colspan="4" align="left">
	    			Existing Mapping Details
	    		</td>
	  			<td colspan="4" align="right">
		                Page Size &nbsp;
		                <select name="cmbpagination" id="cmbpagination" onchange="search();">
	                  		<option value="5" >5</option>
		                  	<option value="10" selected="selected">10</option>
		                  	<option value="15">15</option>
		                  	<option value="20">20</option>
	                	</select>
        		</td>
	    	</tr>
	    	
	        <tr>
	            <th class="tdH">Select
	            <th class="tdH">S.No.</th>
	            <th class="tdH">Scheme Type</th>
	            <th class="tdH">Collection Type</th>
	            <th class="tdH">Receipt Transaction</th>
	            <th class="tdH">A/c Head</th>
	            <th class="tdH">WEF</th>
	            <th class="tdH">Status</th>
	              <th class="tdH" align="center">FAS<br>Disable</th>
	        </tr>
	        
          	<tr>
        		<td id="tdNoData" align="center" colspan="9"  class="tdText" >
        			<div id="nodata" style="display:none"  class="tdText" >(No Data Found)</div>
        		</td>
        	</tr>
  
          <tbody id="tbody" name="tbody" class="tdText">
	        </tbody>
	    </table>
	    
	    
	    
	    
        <table cellspacing="3" cellpadding="2" border="0" width="85%" align="center">
            <tr class="tdH" name="pgbar" id="pgbar" >
              <td> 
                <table  class="fb2" align="center" cellspacing="3" cellpadding="2"
                       border="0" width="100%">
                  <tr>
                    <td width="30%">
                      <div align="left">
                        <div id="divpre" style="display:none"><a href="javascript:prev()"><label><< </label>Previous</a></div>
                      </div>
                    </td>
                    <td width="40%">
                      <div align="center">
                        <table border="0">
                          <tr style="width: 104px">
                            <td style="height: 27px; width: 108px">
                              <div id="divcmbpage" style="display:none">
                                Page&nbsp;&nbsp;
                              </div><select name="cmbpage" id="cmbpage" onchange="callServer(CONS);">
                                                	<option value="1">1</option>        
                                                </select>
                            </td>
                            <td>
                              <div id="divpage" style="display:none">1</div>
                            </td>
                          </tr>
                        </table>
                      </div>
                    </td>
                    <td width="30%">
                      <div align="right">
                        <div id="divnext" style="display:none"><a href="javascript:next()">Next <label>>></label></a></div>
                      </div>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
 		</table>           
            
 
    </form>
  </body>
</html>