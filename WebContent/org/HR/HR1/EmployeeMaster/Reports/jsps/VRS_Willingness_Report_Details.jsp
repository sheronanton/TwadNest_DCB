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
    <title> Report for VRS Willingness</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/VRS_Willingness_Report_Details.js"></script>
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
	function gosubmit()
		{		
		var val=checkNull();
			if(val==true)
			{
				
				 if(document.Vrs_willingness_report.vrs_repo[0].checked)
			   	 status='Accepted';
			    else
			   	 if(document.Vrs_willingness_report.vrs_repo[1].checked)
			   	 status='Rejected';
			   	 else
			    if(document.Vrs_willingness_report.vrs_repo[2].checked)
			   	 status='Withdraw';
			    else if(document.Vrs_willingness_report.vrs_repo[3].checked)
			   	 status='Applied';
			   	 else
			   	 status='Current';
			   	
			   	 var url="../../../../../../VRS_Willingness_Report_Details?status="+status;
					//alert(url);
				document.Vrs_willingness_report.submit();
			}
		}
</script>
    
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body>
  <form name="Vrs_willingness_report" method="POST" action="../../../../../../VRS_Willingness_Report_Details">    
    <table border='2' width="100%">
     <tr>
      <td colspan="2" align="left" valign="top" class="table" width="60%">
       <table border="0" width="100%">
        <tr>
          	<td colspan="8" class="tdH" align="center"><strong> VRS Willingness Report</strong></td>
        </tr>
        <tr>
	         <td class="tdH"> <input type="radio" id="accept" name="vrs_repo"  value="Accepted"></input>
	         Accepted</td>
	        <td class="tdH"> <input type="radio" id="reject" name="vrs_repo"  value="Rejected" ></input>
	         Rejected</td>
	        <td class="tdH"> <input type="radio" id="withdraw" name="vrs_repo"  value="Withdraw"></input>
	         Withdraw</td>                  
	        <td class="tdH"> <input type="radio" id="appl" name="vrs_repo"  value="Applied" checked></input>
	         ALL</td>
	          <td class="tdH"> <input type="radio" id="appl" name="vrs_repo"  value="Current"></input>
	        Currently Applied</td>                                                                                                                                   
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
			     <input type="button" value="Submit"  onclick="gosubmit()">
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