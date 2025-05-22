<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>View GPF Slip Details for User</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/GPF_Slip_Details.js"></script>
      <script>
   
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
   </head>
  <body id="bodyid">
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <%try{ %>
  <!--form name="frmEmployeeProfile" id="frmEmployeeProfile"
                  action="../../../../../../EmployeeProfileServSR" method="POST"-->
      <form name="frmEmployeeProfile" id="frmEmployeeProfile">
        <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

          //  ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
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
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    System.out.println("empid"+empid);
    int gpfno=0;
    String gpf=null;
    String emp_name=null,emp_desig=null;
     try
    {
           
            ps=con.prepareStatement("select employee_id,gpf_no from HRM_Mst_employees where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    gpf=results.getString("gpf_no");
                   System.out.println("gpf"+gpf);
                 }
                   int len=gpf.length() ;
                   if(len>1)
                   {

       /*if(len==4)
            gpf="0"+gpf;
       if(len==3)     
            gpf="00"+gpf;
        if(len==2)     
            gpf="000"+gpf; 
        if(len==1)     
            gpf="0000"+gpf;*/     
          System.out.println("gpf"+gpf);
            results.close();
            ps.close();
            System.out.println("first Query");
           // ps.close();
            ps=con.prepareStatement("SELECT e.EMPLOYEE_ID, " 
            		+"  e.date_of_birth, " 
            		+"  e.EMPLOYEE_NAME " 
            		+"  ||DECODE(e.EMPLOYEE_INITIAL,NULL,' ','.' " 
            		+"  ||e.EMPLOYEE_INITIAL) AS EMPLOYEE_NAME , " 
            		+"  d.DESIGNATION " 
            		+"FROM HRM_MST_EMPLOYEES e, " 
            		+"  HRM_EMP_CURRENT_POSTING c, " 
            		+"  HRM_MST_DESIGNATIONS d " 
            		+"WHERE c.DESIGNATION_ID=d.DESIGNATION_ID " 
            		+"AND e.EMPLOYEE_ID     =c.EMPLOYEE_ID " 
            		+"AND e.gpf_no          =?");
            ps.setString(1,gpf);
            results=ps.executeQuery();
            while(results.next())
            {
                emp_name=results.getString("employee_name");
                emp_desig=results.getString("DESIGNATION");
            }
            }
            else
              System.out.println("No GPF no");
    }
    catch(Exception e)
    {
        System.out.println("Exception "+e);
    }
   %>
      <p>&nbsp;</p>
      <div align="center">
        <table width="100%">
          <tr>
            <td>
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <th align="center" colspan="2">
                    <b>View GPF Slip Details for User</b>
                  </th>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">GPF No</div>
                  </td>
                  <td>
                    <div align="left">
                    <input tabindex="1" type="text" name="txtEmpId1" value="<%=gpf%>" maxlength=5
                           id="txtEmpId1"  onkeypress="return  numbersonly1(event,this)" disabled/><input tabindex="1"
                                                                                 type="HIDDEN"
                                                                                 name="EmpId"
                                                                                 id="EmpId" value="<%=gpf%>"/>
                     
                   </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Employee Name</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtEmployee" id="txtEmployee" value="<%=emp_name%>"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"
                             maxlength="60" size="40" disabled="disabled"/>
                    </div>
                  </td>
                </tr>
                <tr class="table">
                  <td>
                    <div align="left">
                      <font color="#808080">Employee Id</font>
                    </div>
                  </td>
                  <td>
                    <div align="left">
                      <input type="text" name="txtGpf" id="txtGpf" value=<%=empid%>
                             maxlength="10" size="10" disabled="disabled"
                             style="TEXT-TRANSFORM:UPPERCASE; background-color: #ffffff"/>
                             <input type="hidden" name ="gpf">
                    </div>
                  </td>
                </tr>
                            <tr class="table">
          
          <td>
              <div align="left">Financial Year</div>
            </td>
            <td>
              <div align="left">
                <select name="fin_year" id="fin_year" >
                  <option >--Select Financial Year--</option>
                  
               <% try{ ps=con.prepareStatement("select (substr(SLIP_ISSUED_FIN_YEAR,0,4)-1||'-' ||substr(SLIP_ISSUED_FIN_YEAR,0,4)) as last_slip , SLIP_ISSUED_FIN_YEAR   from HRM_GPF_LAST_SLIP_ISSUED " );
               rs=ps.executeQuery();
               if(rs.next())
            	   {%>
            	     <option value="<%=rs.getString("last_slip") %>"><%=rs.getString("last_slip")  %></option>  
            	   <option value="<%=rs.getString("SLIP_ISSUED_FIN_YEAR") %>"><%=rs.getString("SLIP_ISSUED_FIN_YEAR")  %></option>  
            	   <%}
               }catch(Exception e){out.println(e);}
               %>
               
                </select>
              </div>
            </td>
          </tr>
                   <tr class="table">
                       <td align="left">Report&nbsp;Type</td>
                       <td align="left"><select name="cmbReportType">
                              <option value="PDF" selected="selected">PDF</option>
                              <option value="EXCEL">EXCEL</option>
                            <!--  <option value="TXT">TXT</option>-->
                              <option value="HTML">HTML</option>
                            </select> 
                        </td>
                </tr>      
                <tr class="tdH">
                  <td colspan="2">
                    <div align="center">
                    <!--input type="hidden" id="photoPath" name="photoPath"></input-->
                      <input type="button" id="cmdsubmit" name="Submit"
                             value="Submit" onclick="return btnsubmit()"></input>
                       
                      <input type="reset" value="Clear"></input>
                       
                      <input type="button" value="Exit" onclick="self.close()"></input>
                    </div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
    <%}catch(Exception e){out.println(e);} %>
</html>