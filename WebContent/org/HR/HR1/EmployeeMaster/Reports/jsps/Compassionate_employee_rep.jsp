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

         //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
    <title> Compassionate Details Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Compassionate_employee_rep.js"></script>
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
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body>
  <form name="Compassionate_details_report" method="POST" action="../../../../../../Compassionate_details_report">    
    <table border='2' width="100%">
     <tr>
      <td colspan="2" align="left" valign="top" class="table" width="60%">
       <table border="0" width="100%">
        <tr>
          	<td colspan="8" class="tdH" align="center">Compassionate report</td>
        </tr>
        <tr><input type="hidden" name="officeselected">
	         <td class="tdH"> <input type="radio" id="All" name="com_repo"  value="All"></input>
	         All</td>
	        <td class="tdH"> <input type="radio" id="Seniority" name="com_repo"  value="Seniority" ></input>
	         Seniority Number based</td>
	        <td class="tdH"> <input type="radio" id="Scrutiny" name="com_repo"  value="US"></input>
	         Under Scrutiny</td>                  
	        <td class="tdH"> <input type="radio" id="Incomplete" name="com_repo"  value="Incomplete"></input>
	         Incomplete</td>
	          <td class="tdH"> <input type="radio" id="Rejected" name="com_repo"  value="RJ"></input>
	        Rejected</td> 
	        <td class="tdH"> <input type="radio" id="Qualification" name="com_repo"  value="Qualification"></input>
	        Qualification Certificate not Received</td> 
	                                                                                                                                        
       </tr>          
          <tr>
             <td colspan="8" class="tdH">Designation</td>
             
       </tr>
       <tr>
       <td class="tdH" colspan="2" > <input type="radio" id="All" name="com_repo_desig"  value="AllDes" onclick="hide()"></input>
	         All</td>
	          <td class="tdH" colspan="4"> <input type="radio" id="Designation" name="com_repo_desig"  value="Design" onclick="show()"></input>
	        Specific Designation
	        
	         <div  class="scroll" id="divdesignation" align='left' style='width:50%;display: none'></div>
               
	        </td>
       </tr>
       <tr>
             <td colspan="8" class="tdH">Report Output Format</td>
       </tr>
       <tr>
			<td colspan="8" class="tdH" align="center">
			        <select name="output" id="output">
					<option value="PDF">PDF</option>
					<option value="HTML">HTML</option>
					<option value="EXCEL">EXCEL</option>							
					</select>	
			     <input type="button" value="Submit"  onclick="frmsubmit()">
			    <input type="button" id="cmdcancel" name="cancel" value="Cancel" onclick="self.close();"> 
           </td>
       </tr>
  </table>
</td>
</tr>
</table>
</form>
</body>
</html>