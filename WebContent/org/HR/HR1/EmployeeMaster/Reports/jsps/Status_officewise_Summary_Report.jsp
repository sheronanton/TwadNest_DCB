<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
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

        //    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Designation Wise Summary Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Status_officewise_Summary_ReportJS.js"></script>
            
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
      <!--div.scroll {	
      height: 100px;	
      width: 100%;	
      overflow: auto;	
      border: 1px solid #666;	
      background-color: #fff;	
      padding: 0px;
     visibility: hidden;
     position: relative;
      }-->
      
    </style>
  </head>
  
  <body><form name="frmValidationSummaryRep" method="POST" onsubmit="return formsub()"
                       action="../../../../../../Status_Summary_ReportServ">
  
         
            
            <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            <input type="hidden" name="designationlevel">
            <input type="hidden" name="designation">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
            
  
      <div align="center">
        <table border='2' width="100%">
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> Designation Wise Summary Report</strong></td>
                </tr>
                
            
       
       <tr class="table" id="divselect" style="display:none" >                  
                <td> 
              
                       Status                      
                </td>               
        </tr>
                
                
               <tr style="display:none">
                        
                          <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbstatus" id="cmdstatus"
                                         
                                          onclick="getStatus()">
                                    <option>Select the Status</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframestatus" align='left' style='width:27%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
                
                  <tr class="tdH">
  <th colspan="3" align="left">Selection of Office </th>
  </tr>
  
  
   <tr>
            <td colspan="3" ><input type="radio" name="OffType1" value="All" id="OffType1" onclick="HideOfficeType1()" checked="checked" >All Office &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="radio" name="OffType1" id="OffType1" value="Specific" onclick="VisibleOfficeType1()">Specific Office	

            
        </tr>
      
      <tr>
            <td  colspan="4">
                <div style="display:none" id="SpecificOffType1" >
                        <table  border='1' width="100%">
                            <tr>
                                      <td  colspan="3" class="tdH">Selection of Office Jurisdiction</td>
                                    </tr>
                                    <tr>
                                      <td  colspan="3" class="table" align="left">
                                                                
                                                  <%
                                          HttpSession session=request.getSession(false);
                                          UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                                          
                                        System.out.println("user id::"+empProfile.getEmployeeId());
                                        int empid=empProfile.getEmployeeId();
                                        int oid=0;
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
                                             
                                                <%   
                                                String  profile=(String)session.getAttribute("profile");%>
                                              
                                         <%  if(offlevel.equalsIgnoreCase("HO") || profile.equalsIgnoreCase("other"))
                                           {%>
                                         
                                         <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                    </div>
                                        
                                        <% }
                                        
                                          else if(offlevel.equalsIgnoreCase("RN")) { %>
                                          
                                          <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                    </div>
                                        
                                        <%}
                                           else if(offlevel.equalsIgnoreCase("CL")) {%>
                                           
                                          <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                      </div>
                       
                                        <%}
                                            else if(offlevel.equalsIgnoreCase("DN")) {%>
                                            
                                            <div id="divallofficelevel" style="cursor:hand">                          
                                          <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                                          <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                                          <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()" disabled="disabled"></input>Circle
                                          <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division                      </div>
                                            
                                            <%}
                                            
                                               else
                                               {
                                                 System.out.println("No Office id");
                                               }%>                    </td>
                                    </tr>
                                
                                     
                                    <tr class="table" id="trofficeselection1" style="display:none">
                                      <th colspan="5" class="tdH" style="width: 1025px">Selection of Office</th>
                                    </tr>
                                    <tr class="table" id="trofficeselection" style="display:none" VALIGN="TOP">
                                      <td colspan="5">
                                        <div align="left">
                                          <table  border="0" width="100%">
                                            <tr>
                                              <td>
                                                <div id="divregion" style="display:none">Region</div>                          </td>
                                              <td>
                                                <div id="divcircle" style="display:none">Circle</div>                          </td>
                                              <td>
                                                <div id="divofftype" style="display:none">Office Type</div>                          </td>
                                              <td>
                                                <div id="divdivision" style="display:none">Division</div>                          </td>
                                            </tr>
                                            <tr VALIGN="TOP">
                                              <td>
                                                <table border="0" cellpadding="0" cellspacing="0"  width="100%">
                                                  <tr VALIGN="TOP">
                                                    <td>
                                                      <select name="cmbregion" id="cmbregion"
                                                              style="display:none;width:100%"
                                                              onclick="getRegion()">
                                                        <option>Select the Regions</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                             <div class="scroll" id="diviframeregion">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              <td>
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%" >
                                                  <tr VALIGN="TOP">
                                                    <td>
                                                      <select name="cmbcircle" id="cmbcircle"
                                                              style="display:none;width:100%"
                                                              onclick="getCircle('null')">
                                                        <option>Select the Circle</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframecircle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              
                                                <td>
                                                <table border="0" cellpadding="0" cellspacing="0"   width="100%">
                                                  <tr VALIGN="TOP">
                                                    <td>
                                                      <select name="cmbofftype" id="cmbofftype"
                                                              style="display:none;width:100%"
                                                              onclick="getofftype1('null')">
                                                        <option>Select the Office Type</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframeofftype1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                              
                                              <td>
                                                <table border="0" cellpadding="0" cellspacing="0"
                                                       width="100%">
                                                  <tr VALIGN="TOP">
                                                    <td>
                                                      <select name="cmbdivision" id="cmbdivision"
                                                              style="display:none;width:100%"
                                                              onclick="getDivision('null')">
                                                        <option>Select the Division</option>
                                                      </select>                                </td>
                                                  </tr>
                                                  <tr>
                                                    <td>
                                                      <div class="scroll" id="diviframedivision">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>                                </td>
                                                  </tr>
                                                </table>                          </td>
                                            </tr>
                                          </table>
                                        </div>                  </td>
                                    </tr>
                                </table>
                         </div>                        
                    </td>
                </tr>
           
            <tr class='table' id="divallhier" style="display:none">
            <td colspan=5 align=left><input type="checkbox" name="allhier" id="allhier" value="checked" onclick="clearvalue()">Include Hierarchy</input></td>
          </tr>
                
                
                
           
       
       <tr>
                  <th colspan="2" class="tdH">Selection of Designation</th>
                </tr>
        <tr class="table" id="divselect" style="display:block">                  
                 <td colspan="3" ><input type="radio" name="OffType" value="All" id="OffType" onclick="HideOfficeType();"  checked>All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="radio" name="OffType" id="OffType" value="Specific" onclick="VisibleOfficeType();">Specific	

                         
        </tr>
        <tr><td>
        <div style="display:none" id="SpecificOffType" >
        <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
       <tr class="table" id="divselect" style="display:block">                  
                <td> 
              
                       Designation                      
                </td>               
        </tr>
        
         <tr class="table" id="divdest" style="display:block;">
                  <td>
                    <div align="left">
                  
                   <select name="cmbsgroup" id="cmbsgroup"   >
                <option value="0">Select Service Group</option>
                        <%
           ResultSet rs=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("SERVICE_GROUP_ID");
                strname=rs.getString("SERVICE_GROUP_NAME");
                System.out.println("service group code:"+strcode);
                System.out.println("service gorup name:"+strname);
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                rs.close();
                ps.close();
          
          }    
                
        %>
               
               </select> 
               <div class="scroll" id="divdesignation" align='left' style='width:100%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
              
               </div>
                  </td>
                  
           </tr>
           </table>
                       </div>   </td></tr>
            
                 
           
           
           
                  <!--   <tr>
                        
                          <td>
                            <table border="0" cellpadding="0" cellspacing="0"
                                   width="100%">
                              <tr>
                                <td>
                                  <select name="cmbstatus" id="cmdstatus"
                                         
                                          onclick="getStatus()">
                                    <option>Select the Status</option>
                                  </select>
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  <div class="scroll" id="diviframestatus">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                                </td>
                              </tr>
                            </table>
                          </td>
                        </tr>
           
           
           -->
                 
            <tr>
                <td colspan="4" class="tdH" align="center">
                  <input type="Button" value="Submit" name="cmdSub" 
                         onclick="callsubmit()"></input>
         </td></tr><tr>
                    
             <td colspan="2" align="left" valign="top" class="table">
              <div  style="display:block" id="EmpType" align="center">
               
              </div>
            </td>
          </tr>    
        </table>
      </div>
    </form></body>
</html>