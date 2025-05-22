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
    <title> Compassionate Details Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    
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



	function frmsubmit()
	{
	//alert("hai karthik");
	var alldes="";
	var aedesig="";
	if(document.Compassionate_details.panel_repo[0].checked==true)
	{
	alldes="all";
	title="Executive Engineer and  Superintending Engineer";
	}
	
	else if(document.Compassionate_details.panel_repo[1].checked==true)
	{
	alldes="AE";
	title="Superintending Engineer ";
	}
	else 
	{
	alldes="EE";
	title="Executive Engineer ";
	}
	//alert(alldes);
	document.Compassionate_details.action="Cadrewise_panel.jsp?alldes="+alldes+"&title="+title;
	
	//alert(url);
	document.Compassionate_details.submit();
	
	}
</script>
    
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body>
  <form name="Compassionate_details" method="POST" >    
    <table border='2' width="100%">
     <tr>
      <td colspan="2" align="left" valign="top" class="table" width="60%">
       <table border="0" width="100%">
        <tr>
          	<td colspan="8" class="tdH" align="center">Panel report</td>
        </tr>
        <tr>
	         <td class="tdH"> <input type="radio" id="All" name="panel_repo"  value="All" checked="checked"></input>
	         All</td>
	        <td class="tdH"> <input type="radio" id="Seniority" name="panel_repo"  value="AE" ></input>
	         SE</td>
	        <td class="tdH"> <input type="radio" id="Scrutiny" name="panel_repo"  value="EE"></input>
	         EE</td>                  
	       
	                                                                                                                                        
       </tr>          
         
       
       
       <tr>
			<td colspan="8" class="tdH" align="center">
			       
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