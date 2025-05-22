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
    <title> Designation wise Report</title>
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
    
<script type="text/javascript">   
	
	<%
	String ss=request.getParameter("status");
	%>
	
	function hidden()
	{
	document.getElementById("san_st").style.disply='none';
	document.getElementById("tot_san").style.disply='none';
	}
	function redirect()
	{
		window.history.go(-1);
		
	}
</script>
    
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body>
  
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
  String post_rank_id="";
  String empstatus="";
  String desig="";
  String des_id="";
  String rank_id="";
  String cadre_id="";
  post_rank_id=request.getParameter("post_rank_id");
  int sl=0;
  
  String sql="";
  
		     
  String url = request.getRequestURL().toString();
  String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
  System.out.println("url is-->"+url);
  
  System.out.println("baseURL is-->"+baseURL);
  
 String title="";
sql="SELECT POST_RANK_ID, POST_RANK_NAME FROM HRM_MST_POST_RANKS where POST_RANK_ID="+post_rank_id+"";
ps=connection.prepareStatement(sql);
results=ps.executeQuery();
while(results.next())
{
title=results.getString("POST_RANK_NAME");
}

  // ...
  
   %>
  <form name="employee_details" method="POST" action="../../../../../../Compassionate_details_report">    
    <table border='1' width="70%" align="center" >
     <tr>
      <td colspan="2" align="center" valign="top" class="table" width="55%">
       <table border="1" width="100%">
        <tr>
          	<td colspan="7" class="tdH" align="center">Retirement Employee Details (<%=title %>)</td>
        </tr>
        <tr class="tdH">
        <td width="25">
        Sl. No
        </td>
        <td width="25">
        Images
        </td>
         <td width="30">
        E.code
        </td>
         <td width="35">
        Employee Name
        </td>
        <td align="center">
        Designation
        </td>
       <td align="center">
        Office Name
        </td>
      <td align="center">
        Retirement Date
        </td>
          <%
         
          sql="SELECT DECODE(sub1.EMPLOYEE_ID,0,' ',sub1.EMPLOYEE_ID ) AS EMPLOYEE_ID , " +
"  MST.POST_RANK_ID                                     AS POST_RANK_ID , " +
"  DECODE(EMPNAME,NULL,NAMEA,EMPNAME)                   AS EMPNAME , " +
"  DECODE(RETIREDATE,NULL,' ',RETIREDATE)               AS RETIREDATE, " +
"  MST.POST_RANK_NAME, " +
"  DECODE(OFFICE_NAME,NULL,OTHER_DEPT_OFFICE_NAME,OFFICE_NAME) AS OFFICE_NAME, " +
"  DECODE(RETIRE_DATE,NULL,date_effective_from ,RETIRE_DATE)   AS RETIRE_DATE " +
"FROM " +
"  (SELECT EMPLOYEE_ID, " +
"    DESIGNATION_ID, " +
"    OFFICE_ID, " +
"    ''           AS namea, " +
"    POST_RANK_ID AS POST_RANK, " +
"    DEPARTMENT_ID, " +
"    date_effective_from " +
"  FROM HRM_EMP_CUR_POST_CADRE_VIEW " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR')  AND EMPLOYEE_ID IN (SELECT EMPLOYEE_ID FROM ALLRETIREMENTVIEW  WHERE RETIREDATE>SYSDATE)" +
"  AND employee_id              IN " +
"    (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"    ) " +
"  AND POST_RANK_ID="+post_rank_id+" " +
"  UNION " +
"  SELECT 0      AS EMPLOYEE_ID, " +
"    DES_IN_TWAD AS DESIGNATION_ID, " +
"    JOINED_OFFICE_ID, " +
"    DEPUTATIONIST_NAME, " +
"    POST_RANK_ID, " +
"    'TWAD', " +
"    DATE_OF_JOINING " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES " +
"  WHERE PROCESS_FLOW_STATUS_ID='FR' " +
"  AND POST_RANK_ID            ="+post_rank_id+" " +
"  )SUB1 " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_ID, " +
"    EMPLOYEE_INITIAL " +
"    || '.' " +
"    || EMPLOYEE_NAME AS EMPNAME " +
"  FROM VIEW_EMPLOYEE2 " +
"  )SUB2 " +
"ON SUB2.EMPLOYEE_ID=SUB1.EMPLOYEE_ID " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
"  )SUB3 " +
"ON SUB3.OFFICE_ID =SUB1.OFFICE_ID " +
"LEFT OUTER JOIN " +
"  (SELECT OTHER_DEPT_ID, " +
"    OTHER_DEPT_OFFICE_ID, " +
"    OTHER_DEPT_OFFICE_NAME " +
"  FROM HRM_MST_OTHER_DEPT_OFFICES " +
"  )SUB4 " +
"ON SUB1.OFFICE_ID     =SUB4.OTHER_DEPT_OFFICE_ID " +
"AND SUB1.DEPARTMENT_ID=SUB4.OTHER_DEPT_ID " +
"LEFT OUTER JOIN " +
"  ( SELECT POST_RANK_ID, POST_RANK_NAME FROM HRM_MST_POST_RANKS " +
"  )MST " +
"ON MST.POST_RANK_ID=SUB1.POST_RANK " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_ID, " +
"    TO_CHAR(RETIREDATE,'dd/mm/yyyy') AS RETIREDATE, " +
"    RETIREDATE                       AS RETIRE_DATE " +
"  FROM ALLRETIREMENTVIEW " +
"  )RET " +
"ON RET.EMPLOYEE_ID=SUB1.EMPLOYEE_ID " +
"ORDER BY RETIRE_DATE";
System.out.println("sql is--->"+sql);
				ps=connection.prepareStatement(sql);
				results=ps.executeQuery();
				while(results.next())
				{
				sl=sl+1;
				 if((sl % 2)==0)
				 {
				out.print("<tr bgcolor='#C2DFFF'><td>"+sl+"</td><td><img  src=ShowImageProfile.jsp?empid="+
results.getString("EMPLOYEE_ID")+" alt='image' height='80' width='70' class='magnify' ></img></td><td>"+results.getString("EMPLOYEE_ID")+"</td><td>"+results.getString("EMPNAME")+"</td><td>"+results.getString("Post_rank_name")+"</td><td>"+results.getString("OFFICE_NAME")+"</td><td>"+results.getString("RETIREDATE")+"</td></tr>");
				}
				else
				{
				out.print("<tr ><td>"+sl+"</td><td><img  src=ShowImageProfile.jsp?empid="+
results.getString("EMPLOYEE_ID")+" alt='image' height='80' width='70' class='magnify' ></img></td><td>"+results.getString("EMPLOYEE_ID")+"</td><td>"+results.getString("EMPNAME")+"</td><td>"+results.getString("Post_rank_name")+"</td><td>"+results.getString("OFFICE_NAME")+"</td><td>"+results.getString("RETIREDATE")+"</td></tr>");
				}
				}
				String post_name="";
					 ss="SELECT DECODE(POST_RANK_NAME,NULL,' ',POST_RANK_NAME) as POST_RANK_NAME,sub.POST_RANK_ID " +
						"FROM " +
						"  (SELECT POST_RANK_ID FROM HRM_MST_DEPUTN_EMPLOYEES where process_flow_status_id='FR'    " +
						"  )MST " +
						"LEFT OUTER JOIN " +
						"  (SELECT POST_RANK_ID,POST_RANK_NAME FROM HRM_MST_POST_RANKS " +
						"  )SUB " +
						"ON sub.POST_RANK_ID=mst.POST_RANK_ID order by POST_RANK_ID ";
							ps=connection.prepareStatement(ss);
							results=ps.executeQuery();
							while(results.next())
							{
							post_name=post_name+results.getString("POST_RANK_NAME")+" ,";
							}
					
					post_name=post_name.substring(0,post_name.length()-2);
  out.print("<tr><td colspan='7' color='blue'><font color='brown'><strong>In total employees, the employees in deputed from TWAD also included. However the same in considered in sancation strength</td></tr>");
      out.print("<tr><td colspan='7'><font color='brown'><strong>Employee Deputed From Other Offices : <font color='green'>"+post_name+"</td></tr>");
           %>
    
      <tr>
			<td colspan="7" class="tdH" align="center">	
			     <input type="button" id="cmdback" name="back" value="BACK" onclick="redirect();"> 
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