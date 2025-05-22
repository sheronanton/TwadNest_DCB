<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="javax.mail.Session"%>
<%@page import="org.syntax.jedit.InputHandler.insert_break"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="org.syntax.jedit.InputHandler.document_end"%>
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
    <title> Status wise Details Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
  <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
          <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script> 
    <script type="text/javascript"
            src="../scripts/Employee_relieval_board_rep.js"></script>
           <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
            
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          
           <style >
option.red {color: #6600FF; }
option.pink {background-color: #ffcccc;}

 table.tablesorterform {
               font:bold 14px times new roman;
               text-align: center;
               border-collapse:collapse;
               border: 1px solid #5FC1F5;
       }
       table.tablesorterform thead tr th {
               background-image: -moz-linear-gradient(top, #dcf4fe 25%, #b6eaff 80%);
               border: 1px solid #5FC1F5;
               font: 12px Verdana, Geneva, sans-serif;
               padding: 4px;
       }
       table.tablesorterform tr th {
               background-image: -moz-linear-gradient(top, #dcf4fe 25%, #b6eaff 80%);
               border: 1px solid #5FC1F5;
               font-size: 9pt;
               padding: 4px;
       }
       body{
       background-image: -moz-linear-gradient(bottom, #fff 10%, #fff 60%);
       }
.colorch tr:nth-child(odd) {
 background-color: #fff;
 border: 1px solid #5FC1F5;
}
.colorch tr:nth-child(even) {
 background-color: #eff8fd;
 border: 1px solid #5FC1F5;
}
.spname td
{
padding-left:10px;
}

 
 

</style>
          
          
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

function nullcheck()
{
alert("fahsdh")
if(document.getElementById("frm_date").value=="")
{
alert("please select From Date");
return false;
}
else if(document.getElementById("to_date").value=="")
{
alert("please select To Date");
return false;
}
return true;
}
function getreport()
{
alert("hai karhtik")
var val=nullcheck();
if(val==true)
{
var f_date=document.getElementById("frm_date").value;
var t_date=document.getElementById("to_date").value;
alert("f_date-->"+f_date);
alert("t_date-->"+t_date);
document.Employee_relieval_board_rep.action="Emp_relie_sts_wise_Report.jsp?f_date="+f_date+"&t_date="+t_date;
document.Employee_relieval_board_rep.submit();
}

}

</script>
    
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body bgcolor="blue">
  
  <%
  Calendar c = new GregorianCalendar();
int fyr=c.get(Calendar.YEAR);
int tyr=fyr+1;
 int colspan=3; 
  
 

  // ...
  
   %>
  <form name="Employee_relieval_board_rep" method="POST" >    
    <table border='1' width="70%" align="center" >
    <tr class="tdH" align="center">
    <td colspan="2">Employee Relieval from Board - Report</td>
    </tr>
     <tr>
     <td >Enter From Date</td>
     <td ><input type="text" name="frm_date" id="frm_date" size="10">
      <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.Employee_relieval_board_rep.frm_date);"
                       alt="Show Calendar" height="24" width="19"/>
      </td>
      </tr>
      <tr >
     <td>Enter To Date</td>
     <td><input type="text" name="to_date" id="to_date" size="10"> 
     <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.Employee_relieval_board_rep.to_date);"
                       alt="Show Calendar" height="24" width="19"/></td>
      </tr><tr class="tdH"  align="center">
      <td colspan="2"> <input type="button" id="cmdsub" name="cmdsub" value="Submit" onclick="getreport();">  </td>
      </tr>
  </table>

</form>
</body>
</html>