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
  
  String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
System.out.println("url is---->"+url);
System.out.println("baseURL is---->"+baseURL);
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
int fmnt=c.get(Calendar.MONTH)+1;
int fin1=0,fin2=0;
System.out.println("fmnt-------------------->"+fmnt);
if(fmnt>3)
{
    fin2=fyr+1;
    fin1=fyr;
    }
    else
    {
    fin2=fyr;
    fin1=fyr-1;
 }
 int colspan=3; 
  
    String finyr=fin1+"-"+fin2;
    
    System.out.println("fin year is--->"+finyr);
    String san_str="";
  String post_rank_id="";
 String wrk_id="";
 wrk_id=request.getParameter("wrk_id");
 
  post_rank_id=request.getParameter("post_rank_id");
  int sl=0;
  
  String sql="",post_rank="";
  
		 System.out.println("post_rank_id--->"+post_rank_id);     
  
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
  
   %>
  <form name="employee_details" method="POST" ">    
    <table border='1' width="70%" align="center" >
     <tr>
      <td colspan="2" align="center" valign="top" class="table" width="55%">
       <table border="1" width="100%">
        <tr>
          	<td colspan="3" class="tdH" align="center"><%=post_rank %> - Vacancy  Details (Work Nature <%=wrk_desc %>)</td>
        </tr>
        <tr class="tdH">
        <td width="25">
        Sl. No
        </td>
         <td width="30">
        Office Name
        </td>
         <td width="35">
        Vacancy
        </td>
        
      
          <%
          
          sql="SELECT aaa.offid, " +
"  DECODE(office_name,NULL,off_name,office_name) AS office_name, " +
"  NVL(sanctioned_no_of_posts,0) , " +
"  NVL(no_of_filledup,0) , " +
"  (NVL(sanctioned_no_of_posts,0) -NVL(no_of_filledup,0)) AS VAC " +
"FROM " +
"  ( SELECT DISTINCT NVL(office_id,0) AS offid, " +
"    post_rank_id                     AS post_id1 " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR    ='"+finyr+"' " +
"  AND post_rank_id IN ("+post_rank_id+") " +
"  AND office_id    IN " +
"    (SELECT office_id " +
"    FROM com_mst_offices " +
"    WHERE office_status_id = 'CR' " +
"    AND primary_work_id    in('"+wrk_id+"') " +
"    ) " +
"  UNION " +
"  SELECT DISTINCT NVL(office_id,0) AS offid, " +
"    post_rank_id                   AS post_id1 " +
"  FROM HRM_EMP_CUR_POST_CADRE_VIEW " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"  AND post_rank_id             IN ("+post_rank_id+") " +
"  AND office_id                in " +
"    (SELECT office_id FROM com_mst_offices WHERE primary_work_id IN ('"+wrk_id+"') " +
"    ) " +
"  AND employee_id IN " +
"    ( SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  AND department_id='TWAD' " +
"  AND office_id   IS NOT NULL " +
"  ) aaa " +
"LEFT OUTER JOIN " +
"  (SELECT post_rank_id, " +
"    office_id, " +
"    office_name, " +
"    no_of_filledup " +
"  FROM " +
"    (SELECT post_rank_id, " +
"      office_id, " +
"      COUNT(*) AS no_of_filledup " +
"    FROM " +
"      (SELECT employee_id, " +
"        designation_id, " +
"        POST_RANK_ID AS rank_id, " +
"        office_id " +
"      FROM HRM_EMP_CUR_POST_CADRE_VIEW " +
"      WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"      AND employee_id              IN " +
"        ( SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"        ) " +
"      ) aa " +
"    LEFT OUTER JOIN " +
"      ( SELECT designation_id AS desig_id, post_rank_id FROM hrm_mst_designations " +
"      ) bb " +
"    ON aa.designation_id = bb.desig_id " +
"    GROUP BY post_rank_id, " +
"      office_id " +
"    ) xx " +
"  LEFT OUTER JOIN " +
"    ( SELECT OFFICE_ID AS off_id,OFFICE_NAME FROM COM_MST_OFFICES " +
"    ) yy " +
"  ON xx.office_id   = yy.off_id " +
"  ) mm ON aaa.offid = mm.office_id " +
"AND AAA.POST_ID1    = MM.POST_RANK_ID " +
"AND office_id      IN " +
"  (SELECT office_id FROM com_mst_offices WHERE primary_work_id in('"+wrk_id+"')" +
"  ) " +
"LEFT OUTER JOIN " +
"  ( SELECT OFFICE_ID AS OFF_ID,OFFICE_NAME AS off_name FROM COM_MST_OFFICES " +
"  ) YY1 " +
"ON aaa.offid = yy1.off_id " +
"LEFT OUTER JOIN " +
"  (SELECT post_rank_id AS post_id, " +
"    office_id          AS off_id1, " +
"    sanctioned_no_of_posts " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR ='"+finyr+"' " +
"  ) nn " +
"ON aaa.offid                                                 = nn.off_id1 " +
"AND AAA.POST_ID1                                             = NN.POST_ID " +
"WHERE (NVL(SANCTIONED_NO_OF_POSTS,0) -NVL(NO_OF_FILLEDUP,0))<>0 " +
"ORDER BY post_rank_id, " +
"  office_id";

				ps=connection.prepareStatement(sql);
				System.out.println("sql is===>"+sql);
				results=ps.executeQuery();
				int vacn=0;
				while(results.next())
				{
					int vacancy=results.getInt("VAC");
					vacn=vacn+vacancy;
					if( vacancy !=0)
					{
				sl=sl+1;
				 if((sl % 2)==0)
				 {
				out.print("<tr ><td>"+sl+"</td><td>"+results.getString("office_name")+"</td><td>"+results.getInt("VAC")+"</td></tr>");
				}
				else
				{
				out.print("<tr bgcolor='#C2DFFF'><td>"+sl+"</td><td>"+results.getString("office_name")+"</td><td>"+results.getInt("VAC")+"</td></tr>");
				}
				}
				}
  
           %>
     <tr><td colspan="2" align="right" >Total</td><td colspan=""><%=vacn%></td></tr>
      <tr>
			<td colspan="5" class="tdH" align="center">	
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