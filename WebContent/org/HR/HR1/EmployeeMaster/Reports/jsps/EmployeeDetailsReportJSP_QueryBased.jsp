<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Query Based on Employee Details </title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/ValidationEmployee2.js"></script>
    <script type="text/javascript" src="../scripts/EmployeeDetailsReportJS_QueryBased.js"></script>
     <script type="text/javascript"
            src="../scripts/emp_query_based_report.js"></script>
    <style type="text/css">
          body 
          {
                background-color: #ffffff; 
          }
          a:link { color: #002173; }
          
          div.scroll
          {	
              height: 100px;	
              width: 100%;	
              overflow: auto;	
              border: 1px solid #666;	
              background-color: #fff;	
              padding: 0px;
             visibility: hidden;
             position: relative;
          }
      
    </style>
  </head>
  <body>
  <form name="frmEmployee" method="GET" class="table">
  <%
          Connection connection=null;
          PreparedStatement ps=null;
          Statement statement=null;
          ResultSet results=null;
          ResultSet results1=null;
          ResultSet results2=null;
          int strNativeDistrict=0;
          int strNativeTaluk=0;
          String strEmpStatus="";
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
        
                   // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
        
                     Class.forName(strDriver.trim());
                     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
          }
          catch(Exception e)
          {
            System.out.println("Exception in connection...."+e);
          }
  %>
  
  <!-- OFFICE DETAILS -->
  <% 
          HttpSession session=request.getSession(false);
          UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");

          int empid=empProfile.getEmployeeId();
          //int empid=11268;
          int  oid=0;
          try
          {
                   
                    ps=connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                    ps.setInt(1,empid);
                    results=ps.executeQuery();
                    if(results.next()) 
                    {
                        oid=results.getInt("OFFICE_ID");
                     
                    }
                    results.close();
                    ps.close();
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
   %>
   <script language="javascript" type="text/javascript">
                   OfficeId="<%=oid%>";
                  // alert(OfficeId);
    </script>
          
            <table border="0" width="100%">
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> Query Based on Employee Details</strong></td>
                </tr>
                 <table border='2' width="100%">
          <tr>
            
            <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            <input type="hidden" name="designationlevel">
            <input type="hidden" name="designation">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
               <tr >
                  <td colspan="2"  class="tdH">Selection of Office Jurisdiction</td>
                </tr>
                <tr>
                  <td class="table" align="left">
                                            
                              <%
                                         String deptid="",offlevel="";
                    
                         try
                        {
                               
                                ps=connection.prepareStatement("select b.OFFICE_LEVEL_ID,b.office_id from hrm_emp_current_posting a "+
                                        " inner join com_mst_offices b on b.office_id=a.office_id "+
                                        " where a.employee_id=?" );
                                ps.setInt(1,empid);
                                results=ps.executeQuery();
                                     if(results.next()) 
                                     {
                                        offlevel=results.getString("OFFICE_LEVEL_ID");
                                        oid=results.getInt("office_id");
                                        
                                     }
                                results.close();
                                ps.close();
                                System.out.println("office id:"+oid);
                                System.out.println("dept id:"+deptid);
                                
                          }
                          catch(Exception e)
                          {
                            System.out.println(e);
                          }                  
                        
   
                    
                    %>
                         
                    <% String  profile=(String)session.getAttribute("profile");%>
                           
                     
                   <%  if(offlevel.equalsIgnoreCase("HO") || profile.equalsIgnoreCase("other"))
                       {%>
                       
                     
                     <div id="divallofficelevel" style="cursor:hand">    
                     <input type="radio" name="rad_off" id="rad_all" value="all" checked="checked" onclick="selectoption2()"></input>All                      
                      <input type="radio" name="rad_off" id="rad_ho" value="ho"  onclick="selectoption2()"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                             
                    </div>
                    
                    <% }
                    
                      else if(offlevel.equalsIgnoreCase("RN")) { %>
                      
                      <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                             
                    </div>
                    
                    <%}
                       else if(offlevel.equalsIgnoreCase("CL")) {%>
                       
                      <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                      </div>
   
                    <%}
                        else if(offlevel.equalsIgnoreCase("DN")) {%>
                        
                        <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()" disabled="disabled"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                      </div>
                        
                        <%}
                        
                           else
                           {
                             System.out.println("No Office id");
                           }%>
                    
                    </td>
                   
                </tr>
                
                <tr>
                  <td colspan="2" id="tithidden" class="tdH">Selection of Office</td>
                </tr>
                <tr class="table" id="trofficeselection" style="display:none">
                  <td colspan="2">
                    <div align="left">
                      <table border="0" width="100%">
                        <tr>
                          <td>
                            <div id="divregion" style="display:none">Region</div>
                          </td>
                          <td>
                            <div id="divcircle" style="display:none">Circle</div>
                          </td>
                          <td>
                            <div id="divofftype" style="display:none">Office Type</div>
                          </td>
                          <td>
                            <div id="divdivision" style="display:none">Division</div>
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbregion" id="cmbregion"
                                          style="display:none;width:100%"
                                          onclick="getRegion()">
                                    <option>Select the Regions</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframeregion">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                          <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbcircle" id="cmbcircle"
                                          style="display:none;width:100%"
                                          onclick="getCircle('null')">
                                    <option>Select the Circle</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframecircle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                          
                            <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbofftype" id="cmbofftype"
                                          style="display:none;width:100%"
                                          onclick="getofftype('null')">
                                    <option>Select the Office Type</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframeofftype">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                          
                          <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbdivision" id="cmbdivision"
                                          style="display:none;width:100%"
                                          onclick="getDivision('null')">
                                    <option>Select the Division</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframedivision">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          
          </tr>
          <tr class=table id="divallhier" style="display:none">
            <td colspan=2 align=left><input type="hidden" name="allhier" id="allhier" value="include"></input></td>
          </tr>
          
                  
                 
                 
                </table>
              </div>
            </td>
          </tr>
          
        </table>
    <table border="2" width="100%">
      <tr>
      <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            <input type="hidden" name="designationlevel">
            <input type="hidden" name="designation">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
   
                <tr>
                  <td colspan="2" class="tdH">Designation</td>
                </tr>
                <tr>
                    <td>
                     <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                    Service Group
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <select name="servicegroup" id="servicegroup" style="width:45%"
                                          onclick="loadServiceGroup()">
                                    <option>Select Service Group</option>
                                  </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                  <div class="scroll" style="width:45%" id="divframegroup">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                        </table>
                     </td>
                     <td>
                     <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                    Designation
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <select name="designation" id="designation" style="width:45%"
                                          onclick="loadDesignation()" disabled="disabled">
                                    <option>Select Designation</option>
                                  </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                  <div class="scroll" style="width:45%" id="divframedesign">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                        </table>
                     </td>
                </tr>
            </table>
            </td>
      </tr>
      </table>
      <table border="2" width="100%">
      <tr>
            <td colspan="2" align="left" valign="top" class="table" width="100%">
            <table cellspacing="2" cellpadding="3" border="0" width="100%">
                 
                 <tr>
                        <td>
                          <input type="checkbox" name="reg_chec" id="reg_chec" onclick="showReglion();">  Religion
                        </td>
                        <td>
                        
                              <select name="Religion" id="Religion" disabled="disabled">
                                    <option value="">--Select Religion--</option>
                                      <%
                                        try
                                        {
                                          ps=connection.prepareStatement("Select * from HRM_MST_RELIGIONS");
                                          results=ps.executeQuery();
                                          while(results.next())
                                          {
                                              out.println("<option value='" + results.getString("RELIGION_CODE") + "'>" + results.getString("RELIGION_NAME") +"</option>");
                                              
                                          }
                                           out.println("<option value='All'>All</option>");
                                          }
                                          catch(Exception e){System.out.println("error in the RELIGION_NAME code" + e);};
                                      %>
                              </select>
                        </td>
                  </tr>
                  
                  <tr>
                        <td>
                        <input type="checkbox" name="com_chec" id="com_chec" onclick="showcommunity();"/>    Community
                        </td>
                        <td>
                        
                              <select name="Community" id="Community" disabled="disabled">
                                    <option value="">--Select Community--</option>
                                      <%
                                        try
                                        {
                                          ps=connection.prepareStatement("Select * from HRM_MST_COMMUNITY");
                                          results=ps.executeQuery();
                                          while(results.next())
                                          {
                                              out.println("<option value='" + results.getString("Community_Code") + "'>" + results.getString("Community_Name") +"</option>");
                                              
                                          }
                                          out.println("<option value='All'>All</option>");
                                          }
                                          catch(Exception e){System.out.println("error in the community code" + e);};
                                      %>
                              </select>
                        </td>
                  </tr>
                  
                  <tr>
                        <td>
                            <input type="checkbox" name="dist_chec" id="dist_chec" onclick="showdist();"/>  Native&nbsp;District
                        </td>
                        <td>
                              <select name="Native_District" id="Native_District" disabled="disabled">
                                    <option value="">--Select District--</option>
                                    <% 
                                              try
                                              {
                                                  results=ps.executeQuery("select * from COM_MST_DISTRICTS order by District_Name");
                                                  while(results.next())
                                                  {
                                                      String temp=results.getString("District_Code");
                                                      %>
                                                        <option value=<%=temp%>><%= results.getString("District_Name")%></option>
                                                      <% 
                                                  }
                                                  out.println("<option value='All'>All</option>");
                                                  results.close();
                                              }
                                              catch(SQLException e)
                                              {
                                                      System.out.println("Exception in districts:"+e);
                                              }
                                %>        
                              </select>
                        </td>
                  </tr>
                  <tr>
                        <td>
                         <input type="checkbox" name="qua_chec" id="qua_chec" onclick="showqual();"/>  Qualification
                        </td>
                        <td>
                              <input type="text" id="Qualification" disabled="disabled"
                                     name="Qualification"
                                     maxlength="60"
                                     size="60"/>
                        </td>
                  </tr>
                  <tr>
                        <td>
                              Specialization
                        </td>
                        <td>
                              <input type="text"
                                     name="Specialisation"
                                     maxlength="50"
                                     size="50"/>
                        </td>
                  </tr>
                </table>
                </td>
      </tr>
      <tr>
            <td colspan="4" class="tdH"
                align="center">
                  <input type="button"
                         value="Submit" name="cmdSubmit" id="cmdSubmit" onclick=" callFunction()"></input>
                  &nbsp;
                  <input type="Button"
                         value=" Cancel "
                         name="cmdCancel"
                         onclick="self.close();"></input>
            </td>
      </tr>
    </table>
     
   </form>
  </body>
</html>