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
    <title> Status wise Details Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Hrm_desig_wise_sum_rep.js"></script>
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
    

    
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body bgcolor="blue">
  
  <%
  Calendar c = new GregorianCalendar();
int fyr=c.get(Calendar.YEAR);
int tyr=fyr+1;
 int colspan=3; 
  
   System.out.println("fyr--->"+fyr);
     System.out.println("tyr--->"+tyr);
  String finyr=fyr+"-"+tyr;
  System.out.println("finyr--->"+finyr);
    String san_str="";
  String frm_date="";
  String to_date="";
  String add_inc="";
  String Title="";
  String rank_id="";
  String cadre_id="";
  frm_date=request.getParameter("frm_date");
  to_date=request.getParameter("to_date");
  add_inc=request.getParameter("add_inc");
  Title=request.getParameter("Title");
  int sl=0;
  
  String sql="";
  
		     
  System.out.println("frm_date--->"+frm_date);
  System.out.println("to_date--->"+to_date);

  int tot=0;

  // ...
  
   %>
  <form name="status_wise_report" method="POST" action="../../../../../../Compassionate_details_report">    
    <table border='1' width="70%" align="center" >
     <tr>
      <td colspan="2" align="center" valign="top" class="table" width="55%">
       <table border="1" width="100%">
        <tr>
          	<td colspan="3" class="tdH" align="center"><b>Status wise Details Report </b></td>
        </tr>
        <tr class="tdH">
        <td width="25" align="center">
        Sl. No
        </td>
         <td width="30" align="center">
        Status
        </td>
         <td width="35" align="center">
        No Of Employees
        </td>
       
          <%
         sql="SELECT status, " +
"  mst.employee_status_id AS employee_status_id, " +
"  EMPLOYEE_STATUS_DESC " +
"FROM " +
"  (SELECT COUNT(*) AS status, " +
"    employee_status_id " +
"  FROM HRM_EMP_CURRENT_POSTING " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR','TRT','DPT') AND  employee_id in (select employee_id from hrm_mst_employees where is_consolidate='N') " +
"  GROUP BY employee_status_id " +
"  UNION " +
"  SELECT status, " +
"    'TRT' AS employee_status_id " +
"  FROM " +
"    (SELECT COUNT(*) AS status " +
"    FROM HRM_EMP_CURRENT_POSTING " +
"    WHERE EMPLOYEE_STATUS_ID IN('TRT','DPT') AND  employee_id in (select employee_id from hrm_mst_employees where is_consolidate='N') " +
"    ) " +
"  )mst " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC FROM HRM_MST_EMPLOYEE_STATUS " +
"  )sub " +
"ON sub.EMPLOYEE_STATUS_ID=mst.EMPLOYEE_STATUS_ID " +
"ORDER BY status";

          ps=connection.prepareStatement(sql);
          System.out.println("sql is--->"+sql);
          results=ps.executeQuery();
          while(results.next())
          {
         int total=0;
        total =results.getInt("status");
        tot=tot+total;
          
        	  sl=sl+1;
        	  if((sl%2)==0)
        	  {
        		  out.print("<tr ><td>"+sl+"</td><td>"+results.getString("EMPLOYEE_STATUS_DESC")+"</td><td><a href='Emp_Status_wise_Report.jsp?employee_status_id="+results.getString("employee_status_id")+"&emplo_desc="+results.getString("EMPLOYEE_STATUS_DESC")+"'>"+results.getString("status")+"</a></td></tr>");
        	  }
        	  else
        	  {
        		  out.print("<tr bgcolor='#b6eaff'><td>"+sl+"</td><td>"+results.getString("EMPLOYEE_STATUS_DESC")+"</td><td><a href='Emp_Status_wise_Report.jsp?employee_status_id="+results.getString("employee_status_id")+"&emplo_desc="+results.getString("EMPLOYEE_STATUS_DESC")+"'>"+results.getString("status")+"</a></td></tr>");
        	  }
          }
          out.print("<tr ><td colspan='2' align='right'> Total</td><td>"+tot+"</tr>");
           %>
         
      <tr>
			<td colspan="3" class="tdH" align="center">	
			      
			      
			    <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="self.close();"> 
           </td>
       </tr>
      
  </table>
</td>
</tr>
</table>
</form>
</body>
</html>