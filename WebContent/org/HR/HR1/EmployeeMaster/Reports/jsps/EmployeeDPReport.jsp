<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

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

          //  ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
    <title>  Employee Service Details Report </title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/EmployeeDPReport.js"></script>
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
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body><form name="frmValidationSummaryRep" method="POST" 
                       action="EmployeeDPReport">
  
         
            
            <input type="hidden" name="offlevel">
            <input type="hidden" name="office">
            <input type="hidden" name="officetype">
            <input type="hidden" name="officeselected">
            
            <input type="hidden" name="designationlevel">
            <input type="hidden" name="designation">
            <input type="hidden" name="outputtype">
            <input type="hidden" name="ordertype">
            <input type="hidden" name="selRank">
            
  
      <div align="center" >
        <table border='2' width="100%">
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
                <tr> 
                  <td colspan='2' class="tdH" align="center"><strong> Employee Service Details Report</strong></td>
                </tr>
                <tr>
                	<td colspan="2" class="tdH">Select Service Group</td>
                </tr>
               
          <tr class="table" id="divrank" >
                  <td colspan='2'>
                    <div align="left"  id='divrankwhole'>
                    <select name="cmbsgroup1" id="cmbsgroup1" onchange="getDesignation1()">
                <option value="0">Select Service Group</option>
                        <%
            results=null;
           try
           {
           ps=connection.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            results=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(results.next())
            {
              
               
                strcode=results.getInt("SERVICE_GROUP_ID");
                strname=results.getString("SERVICE_GROUP_NAME");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
           finally
          {
                results.close();
                ps.close();
          
          }    
                
        %>
               
               </select> 
                    <!---  cadre -->
                      <!--select name="cmbRank" id="cmbRank"
                              onclick="return checkGroup1();">
                        <option value="0"></option>
                        
                      </select-->
                    </div>
                  </td>
                </tr>   
                 <tr class="table" id="desigblock" style="display:none">
               <td>
               <div class="scroll" align='left' style='width:120%'  id="divdesignation">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
               </td>
               </tr>            
                <tr>
                  <td colspan="2" class="tdH">Selection of Office Jurisdiction</td>
                </tr>
                <tr>
                  <td class="table" align="left">
                                            
                              <%
                      HttpSession session=request.getSession(false);
                      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                      
                    System.out.println("user id::"+empProfile.getEmployeeId());
                    int empid=empProfile.getEmployeeId();
                    int oid=0;
                    String deptid="",offlevel="";
                    String sql="";
                         try
                        {
                               
                               sql="SELECT c.OFFICE_LEVEL_ID, " +
"  a.office_id, " +
"  OFF_ORDER " +
"FROM " +
"  (SELECT office_id " +
"  FROM " +
"    (SELECT office_id, " +
"      DEPARTMENT_ID " +
"    FROM hrm_emp_current_posting " +
"    WHERE employee_id=? " +
"    UNION " +
"    SELECT office_id, " +
"      DEPARTMENT_ID " +
"    FROM HRM_EMP_ADDL_CHARGE " +
"    WHERE employee_id=? " +
"    ) " +
"  WHERE DEPARTMENT_ID='TWAD' " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID, " +
"    OFF_ORDER, " +
"    OFFICE_LEVEL_ID " +
"  FROM COM_MST_ALL_OFFICES_VIEW " +
"  ORDER BY OFF_ORDER ASC " +
"  )c " +
"ON c.OFFICE_ID=a.OFFICE_ID where rownum=1" ;

                                ps=connection.prepareStatement(sql);

 System.out.println("sql------>:"+sql);
  ps.setInt(1,empid);
                                ps.setInt(2,empid);
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
                            String  profile=(String)session.getAttribute("profile");
                             System.out.println("profile------"+profile);
                            %>
                            
                           
                        
                     <%  if(offlevel.equalsIgnoreCase("HO") || profile.equalsIgnoreCase("other"))
                       {%>
                     
                     <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()"></input>Division
                      <input type="radio" name="rad_off" id="rad_all" value="all" onclick="selectoption2()"></input>All
                             
                    </div>
                    
                    <% }
                    
                      else if(offlevel.equalsIgnoreCase("RN")) { %>
                      
                      <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()" disabled="disabled"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()" disabled="disabled"></input>Division
                       <input type="radio" name="rad_off" id="rad_all" value="all" onclick="selectoption2()" disabled="disabled"></input>All      
                    </div>
                    
                    <%}
                       else if(offlevel.equalsIgnoreCase("CL")) {%>
                       
                      <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()" disabled="disabled"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()" disabled="disabled"></input>Division
                      <input type="radio" name="rad_off" id="rad_all" value="all" onclick="selectoption2()" disabled="disabled"></input>All
                      </div>
   
                    <%}
                        else if(offlevel.equalsIgnoreCase("DN")) {%>
                        
                        <div id="divallofficelevel" style="cursor:hand">                          
                      <input type="radio" name="rad_off" id="rad_ho" value="ho" checked="checked" onclick="selectoption2()" disabled="disabled"></input>HO
                      <input type="radio" name="rad_off" id="rad_reg" value="rg" onclick="selectoption2()"  disabled="disabled"></input>Region
                      <input type="radio" name="rad_off" id="rad_cir" value="cr" onclick="selectoption2()"  disabled="disabled"></input>Circle
                      <input type="radio" name="rad_off" id="rad_div" value="dv" onclick="selectoption2()" ></input>Division
                      <input type="radio" name="rad_off" id="rad_all" value="all" onclick="selectoption2()" disabled="disabled"></input>All
                      </div>
                        
                        <%}
                         
                        
                           else
                           {
                             System.out.println("No Office id");
                           }%>

                    </td>
                    
                </tr>
                 <tbody id="selectoff">
                <tr>
                  <td colspan="2" class="tdH">Selection of Office</td>
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
               
          <tr class=table id="divallhier" style="display:block">
            <td colspan=2 align=left><input type="checkbox" name="allhier" id="allhier" value="include">Include all Offices down the Hierarchy</input></td>
          </tr>
          </tbody>
            <tr class="tdH" height="30">
                	<td colspan="2" >
                	
                	<input type="radio" name="rType" value="POFFICE"  checked="checked" >In Present Office
					<input type="radio" name="rType" value="CONFFICE"   >In Entire Service On Controlling Office
					<input type="radio" name="rType" value="PRECONFFICE"   >In Present Designation On Controlling Office

</td>
                </tr>
           <tr>
                	<td colspan="2" class="tdH">Service Type</td>
                </tr>
               
                     <tr>
                	<td colspan="2" >
                	<input type="radio" name="yeartype" onchange="checkType()" value="All" checked >All
                	<input type="radio" name="yeartype" onchange="checkType()" value="manual" >Year of Service Above 
					<input type="text" name="nyear" id="nyear" maxlength="2" onchange="checkValue()" style="display:none" size="5" onkeypress="return numbersonly(event,this)" />

</td>
                </tr>
               
               
              </table>
            </td>
          
          </tr>
         
          <tr>
            <td colspan="2" class="tdH" align="center"><input type="button" value="Submit" onclick="frmsubmit()">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> </td>
          </tr>
        </table>
      </div>
    </form>
    </body>
</html>