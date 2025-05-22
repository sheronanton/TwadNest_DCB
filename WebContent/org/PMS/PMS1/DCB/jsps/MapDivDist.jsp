<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%> 
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Division District Mapping  | TWAD Nest - Phase II</title>
    <link href='../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
     <script type="text/javascript" src="../scripts/MapDivDist.js"></script>
     <script type="text/javascript" src="../scripts/Pagination.js"></script>
     <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  </head>
  
  <body onload="callServer('LoadCmb'); callServer('Get');">
  	<form name="frmMapDivDist" action="">
  		<table border="0" width="80%" align="center" class="fb2">

		    <tr class="tdH" align="center" style="color:black">
		        <td colspan="2"> <div align="center"><strong>Division District Mapping</strong></div></td>
		    </tr>
		
		    <tr class="table">
		        <td>Division</td>
		        <td>
		        	<select class="select" name="divi" id="divi" onchange="callServer('Get');">
		        		<option value="">--Select Division--</option>
		        	</select>
		        </td>
		    </tr>
		    
		    <tr class="table">
		        <td>District</td>
		        <td>
		        	<select class="select" name="dis" id="dis" onchange="callServer('Get');">
		        		<option value="">--Select District--</option>
		        	</select>
		        </td>
		    </tr>
		    
		    <tr class="tdH" align="center">
		        <td colspan="2" >               
		                <input class="fb2" type="button" name="Add" value="Add" id="Add" onclick="callServer('Add')"/>
		                <input class="fb2" type="button" name="Update" value="Update" id="Update" onclick="callServer('Update')" disabled/>
		                <input class="fb2" type="button" name="Delete" value="Delete" id="Delete" onclick="callServer('Delete')" disabled/>
		                <input class="fb2" type="reset" name="Clear" value="Clear"  id="Clear" onclick="clearForm()"/>
		                <input class="fb2" type="button" name="Exit" value="Exit"  id="Exit" onclick="self.close()"/><img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=1','mywindow','scrollbars=yes')">              
		        </td>
		    </tr>
		   
    	</table>
     
     	<br/>
     	
	    <table id="existing" border="0" width="80%" align="center" class="fb2">
	    	<tr>
	    		<td colspan="3" align="left">
	    			Existing Mapping
	    		</td>
	  			<td align="right">
		                Page Size &nbsp;
		                <select class="select" name="cmbpagination" id="cmbpagination" onchange="search();">
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
	            <th class="tdH">Division</th>
	            <th class="tdH">District</th>
	        </tr>
	        
          	<tr>
        		<td id="tdNoData" align="center" colspan="4">
        			<div id="nodata" style="display:none">(No Data Found)</div>
        		</td>
        	</tr>
  
          <tbody id="tbody" name="tbody" class="table">
	        </tbody>
	    </table>
	    
	    
	    
	    
        <table cellspacing="3" cellpadding="2" border="0" width="80%" align="center">
            <tr class="tdH" name="pgbar" id="pgbar" >
              <td> 
                <table align="center" cellspacing="3" cellpadding="2"
                       border="0" width="80%">
                  <tr>
                    <td width="30%">
                      <div align="left">
                        <div id="divpre" style="display:none"><a href="javascript:prev()"><label><< </label>Previous</a></div>
                      </div>
                    </td>
                    <td width="40%">
                      <div align="center">
                        <table border="0">
                          <tr>
                            <td>
                              <div id="divcmbpage" style="display:none">
                                Page&nbsp;&nbsp;<select class="select" name="cmbpage"
                                                        id="cmbpage"
                                                        onchange="callServer(CONS);">
                                                	<option value="1">1</option>        
                                                </select>
                              </div>
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