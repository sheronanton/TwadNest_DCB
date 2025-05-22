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
  String post_rank_id="",post_rank="";
  String empstatus="";
  String desig="";
  String des_id="";
  String rank_id="";
  String cadre_id="";
  String wrk_id="";
  post_rank_id=request.getParameter("post_rank_id");
    wrk_id=request.getParameter("wrk_id");
  int sl=0;
  
  String sql="";
  
		  PreparedStatement ps1=connection.prepareStatement("select post_rank_id,post_rank_name from hrm_mst_post_ranks where post_rank_id=?");
		  ps1.setInt(1,Integer.parseInt(post_rank_id));
		 results= ps1.executeQuery();
		  if(results.next())
		  {
		    post_rank=results.getString("post_rank_name"); 
		  }
		  
  String wrk_desc="";
  String sqls="select WORK_NATURE_ID,WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID in('"+wrk_id+"')";
  ps=connection.prepareStatement(sqls);
  results=ps.executeQuery();
  while(results.next())
  {
  wrk_desc=results.getString("WORK_NATURE_DESC");
  }
  

  // ...
  
   %>
  <form name="employee_details" method="POST" action="../../../../../../Compassionate_details_report">    
    <table border='1' width="70%" align="center" >
     <tr>
      <td colspan="2" align="center" valign="top" class="table" width="55%">
       <table border="1" width="100%">
        <tr>
          	<td colspan="6" class="tdH" align="center"><%=post_rank %> - Fill up Employee Details (Work Nature - <%=wrk_desc %>) </td>
        </tr>
        <tr class="tdH">
        <td width="25">
        Sl. No
        </td>
         <td width="30">
        E.code
        </td>
         <td width="35">
        Employee Name
        </td>
        <td align="center">
        Post Rank Name
        </td>
       <td align="center">
        Office Name
        </td>
        <td align="center">
        Date Effective from
        </td>
     
     
          <%
     try
     {    
          sql="SELECT DECODE(sub1.EMPLOYEE_ID,NULL,'', sub1.EMPLOYEE_ID) AS EMPLOYEE_ID, " +
"  sub1.POST_RANK_ID                                       AS POST_RANK_ID , " +
"  DECODE(EMPNAME,NULL,'',EMPNAME)                         AS EMPNAME, " +
"  POST_RANK_NAME, " +
"  DECODE(OFFICE_NAME,NULL,'',OFFICE_NAME)   AS OFFICE_NAME , " +
"  TO_CHAR(date_effective_from,'dd/mm/yyyy') AS date_effective_from " +
"FROM " +
"  (SELECT EMPLOYEE_ID, " +
"    DESIGNATION_ID, " +
"    OFFICE_ID, " +
"    DATE_EFFECTIVE_FROM, " +
"    POST_RANK_ID " +
"  FROM HRM_EMP_CUR_POST_CADRE_VIEW " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR','TRT','DPN','SUS','LLV','DPT','UAL') " +
"  AND employee_id              IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  AND POST_RANK_ID in ("+post_rank_id+") " +
"  AND office_id  IN " +
"    (SELECT office_id FROM com_mst_offices WHERE PRIMARY_WORK_ID in( '"+wrk_id+"') " +
"    ) " +
"  UNION " +
"  SELECT DEPUTATIONIST_ID, " +
"    DES_IN_TWAD, " +
"    JOINED_OFFICE_ID, " +
"    DATE_OF_JOINING, " +
"    POST_RANK_ID " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES " +
"  WHERE CLOSURE_DATE   IS NULL " +
"  AND POST_RANK_ID      in ("+post_rank_id+") " +
"  AND JOINED_OFFICE_ID IN " +
"    (SELECT office_id FROM com_mst_offices WHERE PRIMARY_WORK_ID IN('"+wrk_id+"') " +
"    ) " +
"  )SUB1 " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_ID, " +
"    EMPLOYEE_INITIAL " +
"    || '.' " +
"    || EMPLOYEE_NAME AS EMPNAME " +
"  FROM HRM_MST_EMPLOYEES " +
"  UNION " +
"  SELECT DEPUTATIONIST_ID, " +
"    DEPUTATIONIST_INITIAL " +
"    ||'.' " +
"    ||DEPUTATIONIST_NAME AS DEP_EMPNAME " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES " +
"  )SUB2 " +
"ON SUB2.EMPLOYEE_ID=SUB1.EMPLOYEE_ID " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
"  )SUB3 " +
"ON SUB3.OFFICE_ID=SUB1.OFFICE_ID " +
"LEFT OUTER JOIN " +
"  ( SELECT POST_RANK_ID AS postrank,POST_RANK_NAME FROM HRM_MST_POST_RANKS " +
"  )POST_RANK " +
"ON post_rank.postrank=SUB1.post_rank_id " +
"ORDER BY to_date(date_effective_from,'dd-mm-yyyy')";
System.out.println(sql);

				ps=connection.prepareStatement(sql);
				results=ps.executeQuery();
				while(results.next())
				{
				sl=sl+1;
				 if((sl % 2)==0)
				 {
				out.print("<tr bgcolor='#C2DFFF'><td>"+sl+"</td><td>"+results.getInt("EMPLOYEE_ID")+"</td><td>"+results.getString("EMPNAME")+"</td><td>"+results.getString("POST_RANK_NAME")+"</td><td>"+results.getString("OFFICE_NAME")+"</td><td>"+results.getString("date_effective_from")+"</td></tr>");
				}
				else
				{
				out.print("<tr><td>"+sl+"</td><td>"+results.getInt("EMPLOYEE_ID")+"</td><td>"+results.getString("EMPNAME")+"</td><td>"+results.getString("POST_RANK_NAME")+"</td><td>"+results.getString("OFFICE_NAME")+"</td><td>"+results.getString("date_effective_from")+"</td></tr>");
				}
				}
				}
catch(Exception e)
{
System.out.println(e);
}
  
           %>
    
      <tr>
			<td colspan="6" class="tdH" align="center">	
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