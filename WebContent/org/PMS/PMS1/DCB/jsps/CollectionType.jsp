<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Collection Types</title>
    <link href='../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
     <script type="text/javascript" src="../scripts/CollectionType.js"></script>
     <script type="text/javascript" src="../scripts/Pagination.js"></script>
  </head>
  
  <body onload="callServer('LoadCmb'); callServer('Get');">
  	<form name="frmMapDivDist" action="">
  		<table border="0" width="80%" align="center">

		    <tr class="tdH" align="center" style="color:black">
		        <td colspan="2"> <div align="center"><strong>Collection Types</strong></div></td>
		    </tr>
		
		    <tr class="table" style="display:none">
		        <td>Ref No</td>
		        <td>
		        	<input type="text" name="sno" id="sno" size="3" disabled/>
		        </td>
		    </tr>
		    
		    <tr class="tdText">
		        <td>Collection Type</td>
		        <td>
		        	<input type="text" name="ctyp" id="ctyp" class="tb6" />
		        </td>
		    </tr>
		    
		    <tr class="tdH" align="center">
		        <td colspan="2"  >               
		                <input type="button" name="Add" value="Add" id="Add" onclick="callServer('Add')" class="fb2"/>
		                <input type="button" name="Update" class="fb2" value="Update" id="Update" onclick="callServer('Update')" disabled/> 
		                <input type="button" name="Delete" class="fb2" value="Delete" id="Delete" onclick="callServer('Delete')" disabled/>
		                <input type="reset" name="Clear" class="fb2" value="Clear"  id="Clear" onclick="clearForm()"/>
		                <img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=19','mywindow','width=600,height=400,scrollbars=yes')"/><input type="button" name="Exit" class="fb2" value="Exit"  id="Exit" onclick="self.close()"/>              
		        </td>
		    </tr>
		   
    	</table>
     
     	<br/>
     	
	    <table id="existing" border="0" width="80%" align="center">
	    	<tr>
	    		<td colspan="2" align="left" class="tdText">
	    			Existing Mapping
	    		</td>
	  			<td align="right" class="tdText">
		                Page Size &nbsp;
		                <select name="cmbpagination" id="cmbpagination" onchange="search();">
	                  		<option value="5" >5</option>
		                  	<option value="10" selected="selected">10</option>
		                  	<option value="15">15</option>
		                  	<option value="20">20</option>
	                	</select>
        		</td>
	    	</tr>
	    	
	        <tr class="tdText">
	            <th class="tdH" >Select
	            <th class="tdH">S.No.</th>
	            <th class="tdH">Collection Type</th>
	        </tr>
	        
          	<tr>
        		<td id="tdNoData" align="center" colspan="4">
        			<div id="nodata" style="display:none">(No Data Found)</div>
        		</td>
        	</tr>
  
          <tbody id="tbody" name="tbody" class="tdText">
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
                                Page&nbsp;&nbsp;<select name="cmbpage"
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