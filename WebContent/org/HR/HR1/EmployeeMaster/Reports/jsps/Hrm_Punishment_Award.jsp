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
    <title> Employee profile with punishment details</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Hrm_Punishment_Award.js"></script>
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
    <script type="text/javascript">
    
    </script>
  </head>

  <body><form name="HRM_punish_award" >
  
         
            <table border="0" width="100%">
            <tr class="tdH">
            
            <td align="center" colspan="2"><b>
           Employee profile with punishment details  </b>
            </td>
            </tr>
            <tr class="tdH">
            <td colspan="2">Employee Details</td>
            </tr>
            <tr>
            
            <td colspan="1">Employee Id</td>
            <td colspan="1"><input type="text" name="emp_id" id="emp_id" maxlength="5" size="5" onblur="employee_check()" onkeypress="return  numbersonly1(event,this)"> <img src="../../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img></td>
                    
            </tr>
            <tr>
            <td>Employee Name</td>
            <td><input type="text" name="emp_name" id="emp_name" size="30" disabled="disabled">  </td>
            </tr>
            
             <tr>
            <td>Employee Designation</td>
            <td><input type="text" name="desig" id="desig" size="30" disabled="disabled">  </td>
            </tr>
            
            
             <tr>
            <td>Employee Date of Birth</td>
            <td><input type="text" name="dob" id="dob" size="30" disabled="disabled">  </td>
            </tr>
           
            <tr>
            <td colspan="2" class="tdH" align="center">
            <input type="button" value="Submit" onclick="gosubmit();">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();">
            </td>
          </tr>
            </table>
           
            
  
     
    </form></body>
</html>