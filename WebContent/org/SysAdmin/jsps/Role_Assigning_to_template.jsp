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

       //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
    <title> Role Assigning  </title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Role_Assigning_to_template.js"></script>
    <link href="../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
    .navigation {
  height:100px; width:283px; 
  border-style:1px solid #666;
  border-width:1px; 
  background-color: #fff;
  float:left; 
  overflow: auto;
}

      
       <!-- div.scroll {	
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
  <body ><form   name="frmValidationSummaryRep" method="POST" 
                       action="../../../../../../New_Role_Assigning">
  
         
            
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
            <td  align="left" valign="top" class="table" width="100%">
              <table border="0" width="100%">
             
                <tr>
                  <td  class="tdH" align="center" colspan=4><strong>  Roles Template</strong></td>
                </tr>
                
      <tr class="table" id="divrank" >
                  <td colspan="2" >
                  Select Role Template
                    
                    <select name="roletemp" id="roletemp" onchange="CallRoles()">
                <option value="0">Select Role Template</option>
                        <%
                         ResultSet rs=null;
           
           try
           {
           ps=connection.prepareStatement("select role_template_id,role_template_name from sec_mst_templates order by 2");
            rs=ps.executeQuery();
              int strcode=0;
            String strname="";          
            while(rs.next())
            {
              
               
                strcode=rs.getInt("role_template_id");
                strname=rs.getString("role_template_name");
                
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
                
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in grid.."+e);
          }
            
              
        %>
               </select> </td>
                
               
               <tr class="tdH">              
               
                  <td width="20%" align="left" valign="top" colspan=2 >Available Roles </td>
                  <td width="9%" rowspan="2" align="center" >
                  <input type="button" name="Button" value="&gt;" style="width:30px;" onclick="moveSelected(document.getElementById('Roles'),document.getElementById('URoles'))">
                  <br>
                  <input type="button" name="Submit2" value="&gt;&gt;" style="width:30px;" onclick="moveAll(document.getElementById('Roles'),document.getElementById('URoles'))">
                  <br>
                  <input type="button" name="Submit3" value="&lt;" style="width:30px;" onclick="moveSelected(document.getElementById('URoles'),document.getElementById('Roles'))">
                  <br>
                  <input type="button" name="Submit4" value="&lt;&lt;" style="width:30px;" onclick="moveAll(document.getElementById('URoles'),document.getElementById('Roles'))">
                 <br></td>
                  <td width="24%" align="left" valign="top" ><label>Roles to be allocate </label></td>
               </tr>
               <tr class="tdH">
                 <td width="20%" align="left" valign="top" colspan=2><select name="Roles" id="Roles" size="10" multiple>
                 </select></td>
                 <td align="left" valign="top" ><select name="URoles" id="URoles" size="10" multiple>
                 </select></td>
               </tr>
                  
                  <tr>
                   
                           <td  class="tdH" align="center" colspan=4><input type="button" value="Submit" onclick="frmsubmit()">
            <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> </td>
          </tr>
        </table>
     
     
    </form></body>
</html>
