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
    <title> Designation wise Report</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      
      <script src="<%=request.getContextPath()%>/jquerycalendar/jquery-1.6.2.js"></script>
  <script src="<%=request.getContextPath()%>/script/Proceeding_jquery-ui.min.js"></script>
 <script src="<%=request.getContextPath()%>/script/Proceeding_jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/jquery.magnifier.js"></script>
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
	
	function redirect()
	{
	window.history.go(-1);
	}
	
	
</script>
    
  </head>

  <body>
  
  <%
 
   String sql="";
    String subquery="";
  String bc=request.getParameter("bc");
  String carde_id=request.getParameter("desig_id");
  
   System.out.println("bc bc-->"+bc);
    System.out.println("carde_id is-->"+carde_id);
    if(bc.equalsIgnoreCase("0"))
    {
    subquery="WHERE community_id is null";
    }
    else
    {
   subquery="WHERE COMMUNITY_ID IN(" +bc+ ")";
    }
  
   %>
  <form name="employee_details" method="POST" action="../../../../../../Compassionate_details_report">    
    <table border='1' align="center"  width="70%" style="border:1px solid #0489B1">
     <tr style="height:30px">
      <td colspan="2" align="center" valign="top" class="table" width="55%">
       <table border="1" width="100%" d='myTable' style='border:1px solid #0489B1'  border='1' bordercolor='#fffddd' >
        <tr style="color:#fff" bgcolor="#4D94B8" align="center">
          	<td colspan="7" style="color: #fff;padding-left:5px;" bgcolor="#006699">Community Based Employee Details </td>
        </tr>
        <tr  style="color:#fff" bgcolor="#4D94B8" align="center">
        <td width="25">
        Sl. No
        </td>
       <td width="30">
        Image
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
      
         </tr>
         <%
         
       sql="SELECT EMPLOYEE_ID, " +
"  empname, " +
"  DESIGNATION, " +
"   decode(DECODE(OFFICE_NAME,NULL,OTHER_DEPT_OFFICE_NAME,OFFICE_NAME),null,' ',OFFICE_NAME) AS OFFICE_NAME " +
" FROM " +
"  (SELECT * FROM HRM_MST_EMPLOYEES "+subquery+" " +
"  )A " +
"JOIN " +
"  (SELECT EMPLOYEE_ID, " +
"    OFFICE_ID,DEPARTMENT_ID, " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN ('SAN','VRS','DIS','DTH','RES','CMR','MEV') " +
"  AND DESIGNATION_ID           IN " +
"    (SELECT DESIGNATION_ID FROM HRM_MST_DESIGNATIONS WHERE DESIGNATION_ID IN("+carde_id+") " +
"    ) " +
"  )B " +
"ON A.EMPLOYEE_ID=B.EMPLOYEE_ID " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id, " +
"    employee_initial " +
"    || '.' " +
"    || employee_name AS empname, " +
"    DESIGNATION " +
"  FROM view_employee2 " +
"  )C " +
"ON B.employee_id=C.employee_id " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID,decode(OFFICE_NAME,null,' ',OFFICE_NAME) as OFFICE_NAME FROM COM_MST_OFFICES " +
"  )D " +
" ON D.OFFICE_ID=B.OFFICE_ID" +
" LEFT OUTER JOIN " +
" (SELECT OTHER_DEPT_ID, " +
"  OTHER_DEPT_OFFICE_ID, " +
"  decode(OTHER_DEPT_OFFICE_NAME,null,' ',OTHER_DEPT_OFFICE_NAME) as OTHER_DEPT_OFFICE_NAME " +
" FROM HRM_MST_OTHER_DEPT_OFFICES " +
")sub2 ON sub2.OTHER_DEPT_ID =B.DEPARTMENT_ID AND sub2.OTHER_DEPT_OFFICE_ID=B.OFFICE_ID order by EMPLOYEE_ID " ;
ps=connection.prepareStatement(sql);
System.out.println("sql is ----"+sql);
int i=0;
results1=ps.executeQuery();
while(results1.next())
{
i++;
int empid=results1.getInt("EMPLOYEE_ID");
String empname=results1.getString("empname");
String desig=results1.getString("DESIGNATION");
String off_name=results1.getString("OFFICE_NAME");
out.print("<tr style ='color: blue;padding-left:5px;font-size:14px;height:60px' align='center'><td>"+i+"</td><td><img  src=ShowImageProfile.jsp?empid="+results1.getString("EMPLOYEE_ID")+" alt='image' height='55' width='70' class='magnify' ></img></td><td><a href='viewServiceParticular.jsp?txtEmpId="+empid+"'>"+empid+"</td><td>"+empname+"</td><td>"+desig+"</td><td>"+off_name+"</td></tr>");

}
         
          %>
         
    
      <tr colspan="7" style="color: #fff;padding-left:5px;" bgcolor="#006699"  align='center'>
			<td colspan="7"  align="center">	
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