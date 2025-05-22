<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@page import="java.util.Calendar" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Consumer Details  | TWAD Nest - Phase II </title>
     <script type="text/javascript">
	        function focusloss()
			{	
				 document.getElementById("ctype").selectedIndex = "0";
			} 
        </script>
    			<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
				<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
    
              <% 
		                   String    Office_id="",Office_Name="",serverstatus="Server : ",OFFICE_LEVEL_ID="0";
		                    Connection connection=null;
					        Statement statement=null;
					        ResultSet result=null;
					        PreparedStatement ps=null;
					        try
			                  {
			                         ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			                         String ConnectionString="";
			                        
			                         String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
			                         String strdsn=rs.getString("Config.DSN");
			                         String strhostname=rs.getString("Config.HOST_NAME");
			                         String strportno=rs.getString("Config.PORT_NUMBER");
			                         String strsid=rs.getString("Config.SID");
			                         String strdbusername=rs.getString("Config.USER_NAME");
			                         String strdbpassword=rs.getString("Config.PASSWORD");
			                           
			    //                     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
					 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			
			                          Class.forName(strDriver.trim());
			                          connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
						              try
						              {
						                statement=connection.createStatement();
						                connection.clearWarnings();
						              }
						              catch(SQLException e)
						              {
						                  System.out.println("Exception in creating statement:"+e);
						              }          
					           }
					          catch(Exception e)
					          {
					             System.out.println("Exception in openeing connection:"+e);
					          }
					       					ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
										   	String strhostname = rs1.getString("Config.HOST_NAME");
										   	System.out.println("strhostname" + strhostname);
										   	serverstatus=strhostname.equalsIgnoreCase("10.163.31.20") ? "<font size='3' color='green' ><b> Live </b> </font> " : " <font size='3' color='red' ><b> Test Site </b></font>";
							HttpSession session=request.getSession(false);
							try
					        {
					            
					            if(session==null)
					            {
					                System.out.println(request.getContextPath()+"/index.jsp");
					                response.sendRedirect(request.getContextPath()+"/index.jsp");
					               
					            }
					            System.out.println(session);
					                
					        }catch(Exception e)
					        {
					        	System.out.println("Redirect Error :"+e);
					        }
												
				         	UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
				         	System.out.println("user id::" + empProfile.getEmployeeId());
				         	
				         	
				         	int empid = empProfile.getEmployeeId();
				         	int oid = 0;
				         	String oname = "";
				
				         	try 
				         	{
				         		ps = connection.prepareStatement("select CASE WHEN OLD_OFFICE_ID IS NULL AND DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN  SWITCH_ID ELSE  OFFICE_ID	END	AS OFFICE_ID FROM PMS_DCB_COM_OFFICE_SWITCH WHERE EMPLOYEE_ID=?");
				         		ps.setInt(1, empid);
				         		result = ps.executeQuery();
				         		if(result.next()) 
				         		{
				         			oid = result.getInt("OFFICE_ID");
				         		}
				         		result.close();
				         		ps.close();
				         	} 
				         	catch (Exception e) 
				         	{
				         		System.out.println(e);
				         	}
				         	
				             Controller obj=new Controller();
						     Connection con;
								try
								{
								con=obj.con();
								obj.createStatement(con);
								String userid=(String)session.getAttribute("UserId");
								
								if(userid==null)
								{
								 response.sendRedirect(request.getContextPath()+"/index.jsp");
								}
								
								Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
						
						    //    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
						        if (Office_id.equals("")) Office_id="0";
							    Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
							
						         obj.conClose(con);
				            } 
				         	catch (Exception e) 
				         	{ 
				         		System.out.println(e);
				         	}
				         	
				         	OFFICE_LEVEL_ID=obj.getValue("com_mst_offices", "OFFICE_LEVEL_ID","where OFFICE_ID="+Office_id+ "  ");
							String division_list="";
							try
							{
							//division_list=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where   region_office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","div","class='select' onchange=focusloss()");
						//	 division_list=obj.combo_str("com_mst_offices","office_name","office_id","where   circle_office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","div","class='select' onchange=focusloss()");
						
								 division_list=obj.combo_str("com_mst_offices","office_name","office_id","where   office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map)  ","div","class='select' onchange=focusloss()");

							}catch(Exception e) 
							{
							out.println(e);
							}				
              %>
    <script type="text/javascript">
    	var OID = <%=Office_id%>    	
    </script>
    <script type="text/javascript" src="../scripts/Consumer.js"></script>
    <script type="text/javascript" src="../scripts/msg.js"></script>
    <script type="text/javascript" src="../scripts/Pagination.js"></script>
	<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  </head>
  <body onload=" callServer('Division'); callServer('Type'); callServer('Get'); //callServer('Group'); ">
    
  <form name="frmConsumer" method="get" action="Consumer?Report">
      <table width="90%" align="center"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  class="fb2">
        <tr>
          <th valign="top"  align="center">
          	 <font class="tdText">Beneficiary Master -  <%=Office_Name%> </font>               
            <br/>
          </th>
        </tr>
      </table>
		<table  class="tab2" cellspacing="1" cellpadding="1" border="1" width="90%" align="center"   style="BORDER-COLLAPSE: collapse" border="1"  >
            <tr>
            	<td colspan="2" align="right"><%=serverstatus%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            </tr>
           			 <%
					if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
					{
					%> 
					<tr>
				 	 <td  ><font class="tdText">  Division</font> </td>
				 	 <td>
				 	 <%=division_list%>
				 	</td>
				 	</tr>
				 	<%}%>
              <tr>
                <td class="tdText" >
                    Beneficiary No
                </td>       
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" class="tb4" name="cid" id="cid" readonly="readonly" size="4" /> (System generated) 
                  </div>
                </td>
              </tr>
              <!-- tr>
                <td class="tdText">
                  <div align="left">Beneficiary Code(User Reference)</div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="ccode" id="ccode" size="10" />
                  </div>
                </td>
              </tr-->

              
              <tr>
                <td class="tdText">
                  <div align="left">
                    Beneficiary Type <font color="red">*</font><label style="color:rgb(255,0,0);">&nbsp;</label>
                  </div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <select size="1" name="ctype" id="ctype" onchange="callServer('Get');" class="select">                    <!--  onchange="searchBen();" --> <!-- onchange="isPriv();"-->
                      <option value="">----Select Type----</option>
                    </select>
                  </div>
                </td>
              </tr>
              

              <!-- tr>
              	<td>
              		District
              	</td>
              	<td>
                    <select name="dis" id="dis" onchange="callServer('Block');">
                  		<option value="">--Select District--</option>
              		</select>
              	</td>
              </tr>
              <tr>
                <td>
                  <div align="left">
                    Block
                  </div>
                </td>
                
                <td>
                  <div align="left">
                    <select size="1" name="blk" id="blk" onchange="callServer('Panch');" disabled="disabled">
                      <option value="">----Select Block----</option>
                    </select>
                  </div>
                </td>
              </tr>
                            
              <tr>
                <td>
                  <div align="left">
                    Panchayat Name
                  </div>
                </td>
                
                <td>
                    <select size="1" name="pan" id="pan" disabled="disabled">
                      <option value="">----Select Panchayat----</option>
                    </select>
                </td>
              </tr-->
              <tr>
                <td class="tdText">
                  <div align="left">Beneficiary Name <font color="red">*</font></div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="cname" id="cname" class="tb6"  readonly="readonly"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="Click here" onclick="searchConsumer();"/>
                  </div>
                </td>
              </tr>
              
             


              <!-- tr>
                <td class="tdText">
                  <div align="left">Beneficiary Group Id</div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <select size="1" name="group" id="group">
                      <option value="">----Select Type----</option>
                    </select>
                  </div>
                </td>
              </tr-->

            


              <!-- tr>
                <td class="tdText">
                  <div align="left">Consumption Category</div>
                </td>
                
                <td class="tdText">
                  <div align="left">
					<input type="radio" name="consumption" id="petty" value="0" checked>Petty WS <input type="radio" name="consumption" id="bulk" value="1">Bulk WS </div>
                </td>
              </tr-->
			

<tr>


<td>
			<table cellspacing="1" cellpadding="1" border="0" width="100%" align="center">
			  <tr>
				  <td class="tdText" colspan="2" >
					  <u>Beneficiary Billing Address</u> 
				  </td>
			  </tr>

		
              <tr>
                <td class="tdText">
                  <div align="left">Billing address 1  <font color="red">*</font></div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" class="tb6" name="adr1" id="adr1" onkeyup="this.value = (this.value).toUpperCase();" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="tdText">
                  <div align="left">Billing address 2   <font color="red">*</font></div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="adr2" class="tb6"  id="adr2" onkeyup="this.value = (this.value).toUpperCase();" size="30" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="tdText">
                  <div align="left">Billing address 3 </div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="adr3" class="tb6"  id="adr3" onkeyup="this.value = (this.value).toUpperCase();" size="30" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="tdText">
                   Pincode  
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="pin" id="pin"  class="tb5" onkeypress="return numonly(this.value,6,event);" size="6" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="tdText">
                  Landline 
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="llno"  class="tb5"  id="llno" onkeypress="return landline(this.value,event);" onblur="llnoLastCheck(this.value)" size="14" />
                  </div>
                </td>
              </tr>


              <tr>
                <td class="tdText">
                   Mobile 
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="cell" id="cell"  class="tb5" onkeypress="return numonly(this.value,10,event);" size="10" />
                  </div>
                </td>
              </tr>

              <tr>
                <td class="tdText">
                  <div align="left">e-mail id</div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="email"   class="tb6" id="email" onchange="emailCheck(this.value);" />
                  </div>
                </td>
              </tr>
</table>
</td>		
<td>
			<table cellspacing="1" cellpadding="1" border="0" width="100%" align="center">
			  <tr>
				  <td class="tdText" colspan="2" >
					  <u>Beneficiary Office Address</u> &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" onclick="copyContact(this.checked)" /> <font size='2'>Tick if same as Billing Address </font> 
				  </td>
			  </tr>
              <tr>
                <td class="tdText">
                  <div align="left">Office address 1 </div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="adr1off"  class="tb6" id="adr1off" onkeyup="this.value = (this.value).toUpperCase();" />
                  </div>
                </td>
              </tr>
              <tr>
                <td class="tdText">
                  <div align="left">Office address 2 </div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="adr2off"  class="tb6" id="adr2off"  onkeyup="this.value = (this.value).toUpperCase();" size="30" />
                  </div>
                </td>
              </tr>
              <tr>
                <td class="tdText">
                  <div align="left">Office address  3  </div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="adr3off" class="tb6"  id="adr3off" onkeyup="this.value = (this.value).toUpperCase();" size="30" />
                  </div>
                </td>
              </tr>
              <tr>
                <td class="tdText">
                   Pincode  
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="pinoff" id="pinoff"  class="tb5" onkeypress="return numonly(this.value,6,event);" size="6"/>
                  </div>
                </td>
              </tr>
              <tr>
                <td class="tdText">
                  Landline 
                </td> 
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="llnooff" id="llnooff"  size="150" class="tb5" onkeypress="return landline(this.value,event);" onblur="llnoLastCheck(this.value)" size="14" />
                  </div>
                </td>
              </tr>
              <tr>
                <td class="tdText">
                   Mobile 
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text" name="celloff"  class="tb5" id="celloff" onkeypress="return numonly(this.value,10,event);" size="10" />
                  </div>
                </td>
              </tr>
              <tr>
                <td class="tdText">
                  <div align="left">e-mail id </div>
                </td>
                
                <td class="tdText">
                  <div align="left">
                    <input type="text"   class="tb6" name="emailoff" id="emailoff" />
                  </div>
                </td>
              </tr>
</table>
</td>
</tr>
</table>
        <table cellspacing="3" cellpadding="2" border="0" width="90%" align="center">
        	<tr>                            
          		<th width="85%">
          			<div align="center">
          				<input type="button" name="Add" value="ADD" class="fb2"
                   				id="Add" onclick="callServer('Add')" align="middle"/>
          				 <%--
          				<input type="button" name="Update" value="UPDATE" style="display:none" class="fb2"
                   				id="Update" onclick="callServer('Update')" align="middle"/>
                   				--%>
          				<input type="button" name="Delete" value="DELETE" style="display:none" class="fb2"
                   				id="Delete" onclick="callServer('Delete_ok')" align="middle"/>
          				<input type="button" name="Report" value="VIEW" class="fb2"
                   				id="Report" onclick="report();" align="middle"/>
          				<input type="button" name="clear" value="CLEAR" class="fb2"
                   				id="clear" align="middle" onclick="refresh();"/>
          				<input type="button" name="exit" value="EXIT" class="fb2"
                   				id="exit" onclick="self.close()" align="middle"/><img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=6','mywindow','width=600,height=400,scrollbars=yes')">
          			</div>                   
          		</th>
          		<th id="tblHead" align="right" colspan="1" class="tdH" width="15%">
		                Page Size &nbsp;&nbsp;&nbsp;&nbsp;
		                <select name="cmbpagination" id="cmbpagination" onchange="search();" class="select">
	                  		<option value="5" >5</option>
		                  	<option value="10" selected="selected">10</option>
		                  	<option value="15">15</option>
		                  	<option value="20">20</option>
	                	</select>
        		</th>
          		
          	</tr>
      </table>
                   		<!--input type="button" name="tmp" value="GENERATE CODE"
                   				id="exit" onclick="temp()" align="middle"/-->

      <table   class="fb2" width="90%" align="center"  style="BORDER-COLLAPSE: collapse" border="0" >
        	
        	<!-- tr>
        		<th id="tblHead" align="right" colspan="3">
        			<br>
        			
        			<br>
        		</th>
        		<th id="tblHead" align="right" colspan="1">
		                Page Size &nbsp;&nbsp;&nbsp;&nbsp;
		                <select name="cmbpagination" id="cmbpagination" onchange="search();">
	                  		<option value="5" >5</option>
		                  	<option value="10" selected="selected">10</option>
		                  	<option value="15">15</option>
		                  	<option value="20">20</option>
	                	</select>
        		</th>
        	</tr-->
  
        	<tr class="tdH" id="colHead">    
        		    
   			</tr>

  
			<tr class="tdH">
          		<td class="tdText">
          			Select
          		</td>
          		<td class="tdText">
          			Beneficiary Type
          		</td>
          		<td class="tdText">
          			Beneficiary Name
          		</td>
          		<td class="tdText">
          			Billing Address
          		</td>
			</tr>

          	<tr>
        		<td id="tdNoData" align="center" colspan="4" class="tdText">
        			<div id="nodata" style="display:none">(No Data Found)</div>
        		</td>
        	</tr>
  
  
	        <tbody name="tbody" id="tbody" class="tdText">
	   		</tbody>
   		



   	  </table>


    
        <table  cellspacing="0" cellpadding="0" border="0" width="90%" align="center" > 
            <tr  name="pgbar" id="pgbar" >
              <td> 
                <table align="center" cellspacing="3" cellpadding="0"  
                       border="0" width="100%">
                  <tr>
                    <th width="30%">
                      <div align="left">
                        <div id="divpre" style="display:none"><a href="javascript:prev()"><label><< </label>Previous</a></div>
                      </div>
                    </th>
                    <th width="40%">
                      <div align="center">
                        <table border="0">
                          <tr>
                            <td>
                              <div id="divcmbpage" style="display:none">
                                Page&nbsp;&nbsp;
                               <select name="cmbpage" id="cmbpage" onchange="callServer(CONS);" class="select">
                               
                               
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
                    </th>
                    <th width="30%">
                      <div align="right">
                        <div id="divnext" style="display:none"><a href="javascript:next()">Next <label>>></label></a></div>
                      </div>
                    </th>
                  </tr>
                </table>
              </td>
            </tr>
 		</table>           
    </form> 
  </body>
</html>