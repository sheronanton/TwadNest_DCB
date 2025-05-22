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
    <title> Addition / Incharge Details Report</title>
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
  
   //System.out.println("fyr--->"+fyr);
    // System.out.println("tyr--->"+tyr);
  String finyr=fyr+"-"+tyr;
  //System.out.println("finyr--->"+finyr);
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
  
		     
 // System.out.println("frm_date--->"+frm_date);
 // System.out.println("to_date--->"+to_date);

  

  // ...
  
   %>
  <form name="ddition_Incharge_Details" method="POST" action="../../../../../../Compassionate_details_report">    
    <table border='1' width="70%" align="center" >
     <tr>
      <td colspan="2" align="center" valign="top" class="table" width="55%">
       <table border="1" width="100%">
        <tr>
          	<td colspan="7" class="tdH" align="center"> <%=Title %> Charge Details Report</td>
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
        Designation
        </td>
       <td align="center">
        Office Name
        </td>
          <td align="center">
         From Date
        </td>
         <td align="center">
         To Date
        </td>
          <%
          if(add_inc.equalsIgnoreCase("Addi"))
          {
         sql="SELECT EMPLOYEE_ID, " +
        		 "  empname, " +
        		 "  DESIGNATION, " +
        		 "  office_name, " +
        		 "  ADDL_CHARGE_TYPE, " +
        		 "  to_char(DATE_EFFECTIVE_FROM,'dd-mm-yyyy') as DATE_EFFECTIVE_FROM, " +
        		 "  DECODE (CLOSURE_DATE,NULL, ' Till Date',CLOSURE_DATE) AS CLOSURE_DATE, " +
        		 "  ADDL_CHARGE_STATUS " +
        		 "FROM " +
        		 "  (SELECT EMPLOYEE_ID, " +
        		 "    OFFICE_ID, " +
        		 "    ADDL_CHARGE_TYPE, " +
        		 "    ADDL_CHARGE_STATUS, " +
        		 "    DATE_EFFECTIVE_FROM, " +
        		 "    DECODE(CLOSURE_DATE,NULL,sysdate, CLOSURE_DATE) AS till_date, " +
        		 "    CLOSURE_DATE, " +
        		 "    DESIGNATION_ID " +
        		 "  FROM HRM_EMP_ADDL_CHARGE where ADDL_CHARGE_TYPE='A' " +
        		 " AND EMPLOYEE_ID IN " 
				+ " (SELECT EMPLOYEE_ID " 
				+ " FROM HRM_EMP_CURRENT_POSTING " 
				+ " WHERE EMPLOYEE_STATUS_ID NOT IN ('SAN','VRS','DIS','DTH','RES','CMR','MEV') " 
				+ " ) " +
        		 "  )mst " +
        		 "LEFT OUTER JOIN " +
        		 "  (SELECT OFFICE_ID,office_name FROM com_mst_offices " +
        		 "  )office " +
        		 "ON office.OFFICE_ID=mst.OFFICE_ID " +
        		 "LEFT OUTER JOIN " +
        		 "  (SELECT DESIGNATION_ID,DESIGNATION FROM HRM_MST_DESIGNATIONS " +
        		 "  )desig " +
        		 "ON desig.DESIGNATION_ID=mst.DESIGNATION_ID " +
        		 "LEFT OUTER JOIN " +
        		 "  (SELECT EMPLOYEE_ID, " +
        		 "    EMPLOYEE_INITIAL " +
        		 "    || '.' " +
        		 "    || EMPLOYEE_NAME AS empname " +
        		 "  FROM VIEW_EMPLOYEE2 " +
        		 "  )emp " +
        		 "ON EMP.EMPLOYEE_ID          =MST.EMPLOYEE_ID " +
        		 "WHERE (DATE_EFFECTIVE_FROM <=TO_DATE('"+frm_date+"','dd-mm-yyyy') " +
        		 "AND (till_date             >=TO_DATE('"+to_date+"','dd-mm-yyyy'))) " +
        		 "OR (DATE_EFFECTIVE_FROM BETWEEN TO_DATE('"+frm_date+"','dd-mm-yyyy') AND TO_DATE('"+to_date+"','dd-mm-yyyy')) " +
        		// " AND  " +
        		 "ORDER BY DATE_EFFECTIVE_FROM, " +
        		 "  CLOSURE_DATE";
        		             
          }
          else if(add_inc.equalsIgnoreCase("Incharge"))
          {
        	  sql="SELECT EMPLOYEE_ID, " +
         		 "  empname, " +
         		 "  DESIGNATION, " +
         		 "  office_name, " +
         		 "  ADDL_CHARGE_TYPE, " +
         		 "  to_char(DATE_EFFECTIVE_FROM,'dd-mm-yyyy') as DATE_EFFECTIVE_FROM, " +
         		 "  DECODE (CLOSURE_DATE,NULL, ' Till Date',CLOSURE_DATE) AS CLOSURE_DATE, " +
         		 "  ADDL_CHARGE_STATUS " +
         		 "FROM " +
         		 "  (SELECT EMPLOYEE_ID, " +
         		 "    OFFICE_ID, " +
         		 "    ADDL_CHARGE_TYPE, " +
         		 "    ADDL_CHARGE_STATUS, " +
         		 "    DATE_EFFECTIVE_FROM, " +
         		 "    DECODE(CLOSURE_DATE,NULL,sysdate, CLOSURE_DATE) AS till_date, " +
         		 "    CLOSURE_DATE, " +
         		 "    DESIGNATION_ID " +
         		 "  FROM HRM_EMP_ADDL_CHARGE where  ADDL_CHARGE_TYPE='I'  " +
         		  " AND EMPLOYEE_ID IN " 
				+ " (SELECT EMPLOYEE_ID " 
				+ " FROM HRM_EMP_CURRENT_POSTING " 
				+ " WHERE EMPLOYEE_STATUS_ID NOT IN ('SAN','VRS','DIS','DTH','RES','CMR','MEV') " 
				+ " ) " +
         		 "  )mst " +
         		 "LEFT OUTER JOIN " +
         		 "  (SELECT OFFICE_ID,office_name FROM com_mst_offices " +
         		 "  )office " +
         		 "ON office.OFFICE_ID=mst.OFFICE_ID " +
         		 "LEFT OUTER JOIN " +
         		 "  (SELECT DESIGNATION_ID,DESIGNATION FROM HRM_MST_DESIGNATIONS " +
         		 "  )desig " +
         		 "ON desig.DESIGNATION_ID=mst.DESIGNATION_ID " +
         		 "LEFT OUTER JOIN " +
         		 "  (SELECT EMPLOYEE_ID, " +
         		 "    EMPLOYEE_INITIAL " +
         		 "    || '.' " +
         		 "    || EMPLOYEE_NAME AS empname " +
         		 "  FROM VIEW_EMPLOYEE2 " +
         		 "  )emp " +
         		 "ON EMP.EMPLOYEE_ID          =MST.EMPLOYEE_ID " +
         		 "WHERE  (DATE_EFFECTIVE_FROM <=TO_DATE('"+frm_date+"','dd-mm-yyyy') " +
         		 "AND (till_date             >=TO_DATE('"+to_date+"','dd-mm-yyyy'))) " +
         		 "OR (DATE_EFFECTIVE_FROM BETWEEN TO_DATE('"+frm_date+"','dd-mm-yyyy') AND TO_DATE('"+to_date+"','dd-mm-yyyy')) " +
         		// " " +
         		 "ORDER BY DATE_EFFECTIVE_FROM, " +
         		 "  CLOSURE_DATE";
        	  }
          else if(add_inc.equalsIgnoreCase("Both"))
          {
        	  sql="SELECT EMPLOYEE_ID, " +
         		 "  empname, " +
         		 "  DESIGNATION, " +
         		 "  office_name, " +
         		 "  ADDL_CHARGE_TYPE, " +
         		 "  to_char(DATE_EFFECTIVE_FROM,'dd-mm-yyyy') as DATE_EFFECTIVE_FROM, " +
         		 "  DECODE (CLOSURE_DATE,NULL, ' Till Date',CLOSURE_DATE) AS CLOSURE_DATE, " +
         		 "  ADDL_CHARGE_STATUS " +
         		 "FROM " +
         		 "  (SELECT EMPLOYEE_ID, " +
         		 "    OFFICE_ID, " +
         		 "    ADDL_CHARGE_TYPE, " +
         		 "    ADDL_CHARGE_STATUS, " +
         		 "    DATE_EFFECTIVE_FROM, " +
         		 "    DECODE(CLOSURE_DATE,NULL,sysdate, CLOSURE_DATE) AS till_date, " +
         		 "    CLOSURE_DATE, " +
         		 "    DESIGNATION_ID " +
         		 "  FROM HRM_EMP_ADDL_CHARGE " +
         		  " where EMPLOYEE_ID IN " 
				+ " (SELECT EMPLOYEE_ID " 
				+ " FROM HRM_EMP_CURRENT_POSTING " 
				+ " WHERE EMPLOYEE_STATUS_ID NOT IN ('SAN','VRS','DIS','DTH','RES','CMR','MEV') " 
				+ " ) " +
         		 "  )mst " +
         		 "LEFT OUTER JOIN " +
         		 "  (SELECT OFFICE_ID,office_name FROM com_mst_offices " +
         		 "  )office " +
         		 "ON office.OFFICE_ID=mst.OFFICE_ID " +
         		 "LEFT OUTER JOIN " +
         		 "  (SELECT DESIGNATION_ID,DESIGNATION FROM HRM_MST_DESIGNATIONS " +
         		 "  )desig " +
         		 "ON desig.DESIGNATION_ID=mst.DESIGNATION_ID " +
         		 "LEFT OUTER JOIN " +
         		 "  (SELECT EMPLOYEE_ID, " +
         		 "    EMPLOYEE_INITIAL " +
         		 "    || '.' " +
         		 "    || EMPLOYEE_NAME AS empname " +
         		 "  FROM VIEW_EMPLOYEE2 " +
         		 "  )emp " +
         		 "ON EMP.EMPLOYEE_ID          =MST.EMPLOYEE_ID " +
         		 "WHERE (DATE_EFFECTIVE_FROM <=TO_DATE('"+frm_date+"','dd-mm-yyyy') " +
         		 "AND (till_date             >=TO_DATE('"+to_date+"','dd-mm-yyyy'))) " +
         		 "OR (DATE_EFFECTIVE_FROM BETWEEN TO_DATE('"+frm_date+"','dd-mm-yyyy') AND TO_DATE('"+to_date+"','dd-mm-yyyy')) " +
         		 //"AND ADDL_CHARGE_TYPE='A' " +
         		 "ORDER BY DATE_EFFECTIVE_FROM, " +
         		 "  CLOSURE_DATE";
         		 }
          System.out.println("sql is---->"+sql);
          ps=connection.prepareStatement(sql);
          results=ps.executeQuery();
          
          while(results.next())
          {
        	  sl=sl+1;
        	  
        	 if((sl % 2)==0)
        	 {
        	  out.println("<tr bgcolor='#C2DFFF'><td>"+sl+"</td><td>"+results.getInt("EMPLOYEE_ID")+"</td><td>"+results.getString("empname")+"</td><td>"+results.getString("DESIGNATION")+"</td><td>"+results.getString("office_name")+"</td><td>"+results.getString("DATE_EFFECTIVE_FROM")+"</td><td>"+results.getString("CLOSURE_DATE")+"</td></tr>");
        	 }
        	 else
        	 {
        	  out.println("<tr ><td>"+sl+"</td><td>"+results.getInt("EMPLOYEE_ID")+"</td><td>"+results.getString("empname")+"</td><td>"+results.getString("DESIGNATION")+"</td><td>"+results.getString("office_name")+"</td><td>"+results.getString("DATE_EFFECTIVE_FROM")+"</td><td>"+results.getString("CLOSURE_DATE")+"</td></tr>");
        	 }
        	  
          }
          if(sl==0)
          {
        	  out.println("<tr bgcolor='#C2DFFF' align='center'><td colspan='7' >There is No Data !</td></tr>");
          }
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