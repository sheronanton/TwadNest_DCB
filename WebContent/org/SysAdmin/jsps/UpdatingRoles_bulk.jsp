


<!--
    File Name     : IntranetMajorSystems.jsp
    Purpose       : To create form that allows us add,modify and delete records residing in database
    References    : BenefitAjax.js,BenefitValidations.js,sample2.css
    Servlet Ref.  : ServletBenefitMaster.java
-->

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
      <title>Updating Roles</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
          <script type="text/javascript" src="../scripts/UpdatingRoles_bulk.js"></script>      
          <script type="text/javascript" src="../scripts/UpdatingValidation.js"></script>
          <link href="test2.css" rel=stylesheet media="screen"/>
      <script type="text/javascript" >
     var edit=false;
      </script>    
          <link href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
      <style type="text/css">

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
 <body > 
 <%
  Connection connection=null;
  Statement statement=null;
  ResultSet results=null;
   ResultSet results1=null;
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

       //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
    try
    {
      statement=connection.createStatement();
    }
    catch(SQLException e)
    {
    }
  }
  catch(Exception e)
  {
  }
  %>
        <form name="frmMasterSub" class="table" >
        <div id="dhtmltooltip"></div>
    <script type="text/javascript" src="test.js"></script>
                <%
                HttpSession session=request.getSession(false);
                String userid=request.getParameter("userid");
                session.setAttribute("userid",userid);
                %>
                <table  cellspacing="1" cellpadding="3" border="1" width="100%" class="table" >
                    <tr>
                        <td class="tdH">
                            <center><h2>Updating Roles Bulk</h2></center>
                        </td>
                    </tr>
                    <tr>
                        <td>                        
                              <table  cellspacing="3" cellpadding="1" border="1" width="100%" style="font-size:x-small;">
                                 <tr>
                                      <td class="td">User Id</td>
                                      <td>
                                        <input type="text" name="txtUserId" id="txtUserId" maxlength="10" size="10" onchange="callServer('Login','l')"/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Employee Id</td>
                                      <td style="color:rgb(255,51,102);">
                                     <!-- <select name=txtSysId id=txtSysId onchange="callServer('Login','null')">
                                      <option>--Select Employee Id--</option>
                                      </select>         -->
                                            <input type="text" name="txtSysId" id=txtSysId  maxlength="5" size="8"  onkeypress="return  numbersonly1(event,this)"  readonly/>  
                                           <!--onchange="callServer('Login','null')"
                                           onchange="callServer('Login','null')"
                                           <img src="../../../images/c-lovi.gif"
                                                                                                  width="20"
                                                                                                  height="20"
                                                                                                  alt="empList"
                                                                                                  onclick="servicepopup();"/> -->
                                              <input type=hidden name=EmpId>
                                              
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Employee Name</td>
                                      <td>
                                        <input type="text" name="txtEmpName" id="txtEmpName" maxlength="60" size="45" readonly/>
                                      </td>
                                  </tr>
                                  <tr>
                                      <td class="td">Designation</td>
                                      <td>
                                        <input type="text" name="desig" id="desig" maxlength="60" size="45" readonly/>
                                      </td>
                                  </tr>
                                   <tr>
                                      <td class="td">Office Name & Level</td>
                                      <td>
                                        <input type="text" name="Off_name" id="Off_name" maxlength="60" size="45" readonly/>
                                      </td>
                                  </tr>
                                  <tr><td>Major System</td><td><select name="major_sys" id="major_sys" onchange="callServer('Login','f')"><option value="">Select Major System</option>
        <%
                                        try
                                        {
                                            results1=statement.executeQuery("select Major_System_Id,Major_System_Desc from SEC_MST_MAJOR_SYSTEMS");
                                            while(results1.next())
                                            {
                                            String temp=results1.getString("Major_System_Id");
                                            System.out.println(temp);
                                              out.println("<option value='" + temp + "'>" + results1.getString("Major_System_Desc") + "</option>");
                                            
                                            }
                                            results1.close();
                                        }
                                        catch(Exception e)
                                        {}
                                      %>
        </select></td></tr>
                                  <tr id="role" style="display: none">
                                      <td class="td">RoleName</td>
                                      <td>
                                      <select name=txtName id=txtName>
                                      <option>--Select Role--</option>
                                      </select>
                                        <!--<input type="text" name="txtName" maxlength="50" size="25" />-->
                                      </td>
                                  </tr>
                                  <tr id="bulk">
                                  <td>RoleName</td>
                                  <td colspan="2">
                    <div align="left">
                    </div>
                     <div class="scroll" id="diviframestatus" align='left' style='width:100%;height:500 ' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                  </td>
                                  </tr>
                                  <tr id="seq" style="display:none ">
                                      <td class="td">Ordering Sequence Number</td>
                                      <td>
                                        <input type="text" name="txtOrderSeq" maxlength="3" size="25" />
                                      </td>
                                  </tr>
                                  
                                  <tr>
                                      <td colspan=3 class="td">
                                          <input type="Button" value="  Grant " id="Grant" onclick="callServer('Add','null')" name="cmdAdd"> 
                                          <input type="Button" value=" Revoke" id="Revoke" onclick="callServer('Delete','null')" disabled name="cmdDelete">
                                          <input type="Button" value="Clear All" onclick="clearAll()" name="cmdClearAll">                                 
                                          <input type="button" name="CmdExit" value="EXIT"
                   id="CmdExit" onclick="Exit()" align="middle"/>
                                      </td>                              
                                  </tr>      
                                  <tr>
                                  <td colspan="3">
                                   <!--<div id="mydiv" name="mydiv"> -->
                                    <table name="Existing" id="Existing"  border="1" width="100%"  style="font-family:arial;">
                                    <tr>
                                    Existing Details
                                    </tr>
                                    <tr>
                                    <p><th >View</th></p>
                                    <th >Role Id</th>
                                    <th >Role Name</th>
                                    <th >Ordering Seqence Number</th>
                                    </tr>
                                   
                                    <tbody id="tblList" name="tblList">
                                    </tbody>
                                  </table>
                                  
                                  </td>
                                  </tr>
                              </table>                              
                        </td>                        
                    </tr>
              </table>
        </form>
  </body>
</html>

