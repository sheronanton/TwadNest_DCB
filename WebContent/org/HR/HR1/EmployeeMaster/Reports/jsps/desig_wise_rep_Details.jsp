<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
		window.location.assign("Hrm_desig_wise_sum_rep.jsp");
		
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
  
   System.out.println("fyr--->"+fyr);
   
  String finyr=fin1+"-"+fin2;
    String san_str="";
  String status="";
  String empstatus="";
  String desig="";
  String des_id="";
  String rank_id="";
  String cadre_id="";
  
  san_str=request.getParameter("san");
  status=request.getParameter("status");
  empstatus=request.getParameter("option");
  desig=request.getParameter("optdes");
  des_id=request.getParameter("des_id");
  rank_id=request.getParameter("rand_id");
  cadre_id=request.getParameter("cadre_id");
  
  String tit=""; String tit1="";
  
  if(status.equalsIgnoreCase("OTHERS"))
  {
  tit="Deputation Exclude";
  }
  else if(status.equalsIgnoreCase("DPT"))
  {
  tit="Deputation Include";
  }
  
  
  if(desig.equalsIgnoreCase("Desig"))
  {
  tit1="Designation";
  }
  else if(desig.equalsIgnoreCase("Rank"))
  {
  tit1="Rank";
  }
  else if(desig.equalsIgnoreCase("Cadre"))
  {
  tit1="Cadre";
  }
  
  
  
  if(san_str.equalsIgnoreCase("INC") )
  colspan=4;
  
  
  System.out.println("san_str--->"+san_str);
  System.out.println("status--->"+status);
  System.out.println("empstatus--->"+empstatus);
  System.out.println("desig--->"+desig);
  System.out.println("des_id--->"+des_id);
 System.out.println("rank_id--->"+rank_id);
 System.out.println("cadre_id--->"+cadre_id);
   %>
  <form name="Desig_wise_summ_rep" method="POST" action="../../../../../../Compassionate_details_report">    
    <table border='1' width="70%" align="center" >
     <tr>
      <td colspan="2" align="center" valign="top" class="table" width="55%">
       <table border="1" width="100%">
        <tr>
          	<td colspan="<%=colspan %>" class="tdH" align="center"><%=tit1 %> wise Summary Report - (<%=tit%>)</td>
        </tr>
        <tr class="tdH">
        <td width="20">
        Sl. No
        </td>
        <td align="center">
        Designation
        </td>
        <% if(san_str.equalsIgnoreCase("INC") ){%>
        <td align="center" id="san_st">
        Sanction Strength
        </td>
        <% } %>
        <td align="center">
       Total No Of Employees
        </td>
      
          
     <%
     int sl=0;
     String sql="";
     if((san_str.equalsIgnoreCase("INC")) && (status.equalsIgnoreCase("OTHERS")) && (empstatus.equalsIgnoreCase("All")) && (desig.equalsIgnoreCase("Rank")) )
     
  {
  System.out.println("inside karthik-->");
   sql="SELECT a.post_rank_id, " +
"  post_rank_name, " +
"  DECODE(snfillt,NULL,'0',snfillt)       AS snfillt, " +
"  DECODE(total_sanc,NULL,'0',total_sanc) AS total_sanc " +
"FROM " +
"  ( SELECT post_rank_id,post_rank_name,ORDERING_SEQ_NO FROM hrm_mst_post_ranks " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )b " +
"ON a.post_rank_id=b.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT SUM(FILLUP) SNFILLT, " +
"    post_rank_id AS rank_id " +
"  FROM " +
"    (SELECT COUNT(*) AS fillup, " +
"      designation_id " +
"    FROM hrm_emp_current_posting c " +
"    WHERE employee_id IN " +
"      (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"      ) " +
"    AND c.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','DPN','CMR') " +
"    AND c.designation_id         IN " +
"      (SELECT designation_id " +
"      FROM hrm_mst_designations " +
"      WHERE post_rank_id IN " +
"        (SELECT post_rank_id FROM hrm_mst_post_ranks " +
"        ) " +
"      ) " +
"    GROUP BY DESIGNATION_ID " +
"    UNION " +
"    SELECT COUNT(*) AS FILLUP, " +
"      DES_IN_TWAD " +
"    FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"    GROUP BY DES_IN_TWAD " +
"    )curr " +
"  LEFT OUTER JOIN " +
"    ( SELECT post_rank_id,designation_id FROM hrm_mst_designations " +
"    )des " +
"  ON curr.designation_id =des.designation_id " +
"  GROUP BY post_rank_id " +
"  )x ON x.rank_id=a.post_rank_id " +
"ORDER BY a.ORDERING_SEQ_NO";

}
else  if((san_str.equalsIgnoreCase("INC")) && (status.equalsIgnoreCase("OTHERS")) && (desig.equalsIgnoreCase("Rank")) && (empstatus.equalsIgnoreCase("Spc")) )
{
System.out.println("inside otheres spx");
sql="SELECT a.post_rank_id, " +
"  post_rank_name, " +
"  DECODE(snfillt,NULL,'0',snfillt)       AS snfillt, " +
"  DECODE(total_sanc,NULL,'0',total_sanc) AS total_sanc " +
"FROM " +
"  (SELECT POST_RANK_ID, " +
"    POST_RANK_NAME , " +
"    ORDERING_SEQ_NO " +
"  FROM HRM_MST_POST_RANKS " +
"  WHERE post_rank_id IN("+rank_id+") " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )b " +
"ON a.post_rank_id=b.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT SUM(fillup) AS snfillt, " +
"    post_rank_id      AS rank_id " +
"  FROM " +
"    (SELECT COUNT(*) AS fillup, " +
"      designation_id " +
"    FROM hrm_emp_current_posting c " +
"    WHERE employee_id IN " +
"      (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"      ) " +
"    AND c.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','DPN','CMR') " +
"    AND c.designation_id         IN " +
"      (SELECT designation_id " +
"      FROM hrm_mst_designations " +
"      WHERE post_rank_id IN " +
"        (SELECT post_rank_id FROM hrm_mst_post_ranks " +
"        ) " +
"      ) " +
"    GROUP BY DESIGNATION_ID " +
"    UNION " +
"    SELECT COUNT(*) AS FILLUP, " +
"      DES_IN_TWAD " +
"    FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"    GROUP BY DES_IN_TWAD " +
"    )curr " +
"  LEFT OUTER JOIN " +
"    ( SELECT post_rank_id,designation_id FROM hrm_mst_designations " +
"    )des " +
"  ON curr.designation_id =des.designation_id " +
"  GROUP BY post_rank_id " +
"  )x ON x.rank_id=a.post_rank_id " +
"ORDER BY a.ORDERING_SEQ_NO";

}
 if((san_str.equalsIgnoreCase("INC")) && (status.equalsIgnoreCase("DPT")) && (empstatus.equalsIgnoreCase("All")) )
     
  {
   sql="SELECT a.post_rank_id, " +
"  post_rank_name, " +
"  DECODE(SNFILLT,NULL,'0',SNFILLT)       AS SNFILLT , " +
"  DECODE(total_sanc,NULL,'0',total_sanc) AS total_sanc, " +
"  ORDERING_SEQ_NO " +
"FROM " +
"  ( SELECT post_rank_id,post_rank_name,ORDERING_SEQ_NO FROM hrm_mst_post_ranks " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )b " +
"ON a.post_rank_id=b.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT SUM(fillup) snfillt, " +
"    post_rank_id AS rank_id " +
"  FROM " +
"    (SELECT COUNT(*) AS fillup, " +
"      designation_id " +
"    FROM hrm_emp_current_posting c " +
"    WHERE employee_id IN " +
"      (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"      ) " +
"    AND c.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"    AND c.designation_id         IN " +
"      (SELECT designation_id " +
"      FROM hrm_mst_designations " +
"      WHERE post_rank_id IN " +
"        (SELECT post_rank_id FROM hrm_mst_post_ranks " +
"        ) " +
"      ) " +
"    GROUP BY DESIGNATION_ID " +
"    UNION " +
"    SELECT COUNT(*) AS FILLUP, " +
"      DES_IN_TWAD " +
"    FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"    GROUP BY DES_IN_TWAD " +
"    )curr " +
"  LEFT OUTER JOIN " +
"    ( SELECT post_rank_id,designation_id FROM hrm_mst_designations " +
"    )des " +
"  ON curr.designation_id =des.designation_id " +
"  GROUP BY post_rank_id " +
"  )x ON x.rank_id=a.post_rank_id " +
"ORDER BY a.ORDERING_SEQ_NO";

}
else  if((san_str.equalsIgnoreCase("INC")) && (status.equalsIgnoreCase("DPT")) && (empstatus.equalsIgnoreCase("Spc")) )
{
sql="SELECT a.post_rank_id, " +
"  post_rank_name, " +
"  DECODE(SNFILLT,NULL,'0',SNFILLT)       AS SNFILLT , " +
"  DECODE(total_sanc,NULL,'0',total_sanc) AS total_sanc, " +
"  ORDERING_SEQ_NO " +
"FROM " +
"  (SELECT post_rank_id, " +
"    post_rank_name, " +
"    ORDERING_SEQ_NO " +
"  FROM hrm_mst_post_ranks " +
"  WHERE post_rank_id IN("+rank_id+") " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )b " +
"ON a.post_rank_id=b.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT SUM(fillup) snfillt, " +
"    post_rank_id AS rank_id " +
"  FROM " +
"    (SELECT COUNT(*) AS fillup, " +
"      designation_id " +
"    FROM hrm_emp_current_posting c " +
"    WHERE employee_id IN " +
"      (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"      ) " +
"    AND c.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"    AND c.designation_id         IN " +
"      (SELECT designation_id " +
"      FROM hrm_mst_designations " +
"      WHERE post_rank_id IN " +
"        (SELECT post_rank_id FROM hrm_mst_post_ranks " +
"        ) " +
"      ) " +
"    GROUP BY DESIGNATION_ID " +
"    UNION " +
"    SELECT COUNT(*) AS FILLUP, " +
"      DES_IN_TWAD " +
"    FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"    GROUP BY DES_IN_TWAD " +
"    )curr " +
"  LEFT OUTER JOIN " +
"    ( SELECT post_rank_id,designation_id FROM hrm_mst_designations " +
"    )des " +
"  ON curr.designation_id =des.designation_id " +
"  GROUP BY post_rank_id " +
"  )x ON x.rank_id=a.post_rank_id " +
"ORDER BY a.ORDERING_SEQ_NO";

}


if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("OTHERS")) && (empstatus.equalsIgnoreCase("All")) && (desig.equalsIgnoreCase("Rank")))
     
  {
  
   sql="SELECT a.post_rank_id, " +
"  post_rank_name, " +
"  DECODE(snfillt,NULL,'0',snfillt)       AS snfillt , " +
"  DECODE(total_sanc,NULL,'0',total_sanc) AS total_sanc " +
"FROM " +
"  ( SELECT post_rank_id,post_rank_name,ORDERING_SEQ_NO FROM hrm_mst_post_ranks " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )b " +
"ON a.post_rank_id=b.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT SUM(fillup) snfillt, " +
"    post_rank_id AS rank_id " +
"  FROM " +
"    (SELECT COUNT(*) AS fillup, " +
"      designation_id " +
"    FROM hrm_emp_current_posting c " +
"    WHERE employee_id IN " +
"      (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"      ) " +
"    AND c.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','DPN','CMR') " +
"    AND c.designation_id         IN " +
"      (SELECT designation_id " +
"      FROM hrm_mst_designations " +
"      WHERE post_rank_id IN " +
"        (SELECT post_rank_id FROM hrm_mst_post_ranks " +
"        ) " +
"      ) " +
"    GROUP BY designation_id " +
"    UNION " +
"    SELECT COUNT(*) AS FILLUP, " +
"      DES_IN_TWAD " +
"    FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"    GROUP BY DES_IN_TWAD " +
"    )curr " +
"  LEFT OUTER JOIN " +
"    ( SELECT post_rank_id,designation_id FROM hrm_mst_designations " +
"    )des " +
"  ON curr.designation_id =des.designation_id " +
"  GROUP BY post_rank_id " +
"  )x ON x.rank_id=a.post_rank_id " +
"ORDER BY a.ORDERING_SEQ_NO";

}
else  if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("OTHERS")) && (empstatus.equalsIgnoreCase("Spc")) && (desig.equalsIgnoreCase("Rank")))
{
sql="SELECT a.post_rank_id, " +
"  post_rank_name, " +
"  DECODE(snfillt,NULL,'0',snfillt)       AS snfillt, " +
"  DECODE(total_sanc,NULL,'0',total_sanc) AS total_sanc " +
"FROM " +
"  (SELECT POST_RANK_ID, " +
"    post_rank_name, " +
"    ORDERING_SEQ_NO " +
"  FROM HRM_MST_POST_RANKS " +
"  WHERE post_rank_id IN("+rank_id+") " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )b " +
"ON a.post_rank_id=b.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT SUM(fillup) AS snfillt, " +
"    post_rank_id      AS rank_id " +
"  FROM " +
"    (SELECT COUNT(*) AS fillup, " +
"      designation_id " +
"    FROM hrm_emp_current_posting c " +
"    WHERE employee_id IN " +
"      (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"      ) " +
"    AND c.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','DPN','CMR') " +
"    AND c.designation_id         IN " +
"      (SELECT designation_id " +
"      FROM hrm_mst_designations " +
"      WHERE post_rank_id IN " +
"        (SELECT post_rank_id FROM hrm_mst_post_ranks " +
"        ) " +
"      ) " +
"    GROUP BY DESIGNATION_ID " +
"    UNION " +
"    SELECT COUNT(*) AS FILLUP, " +
"      DES_IN_TWAD " +
"    FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"    GROUP BY DES_IN_TWAD " +
"    )curr " +
"  LEFT OUTER JOIN " +
"    ( SELECT post_rank_id,designation_id FROM hrm_mst_designations " +
"    )des " +
"  ON curr.designation_id =des.designation_id " +
"  GROUP BY post_rank_id " +
"  )x ON x.rank_id=a.post_rank_id " +
"ORDER BY a.ORDERING_SEQ_NO";

}
 if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("DPT")) && (empstatus.equalsIgnoreCase("All")) && (desig.equalsIgnoreCase("Rank")))
     
  {
   sql="SELECT a.post_rank_id, " +
"  post_rank_name, " +
"  DECODE(snfillt,NULL,'0',snfillt)       AS snfillt , " +
"  DECODE(total_sanc,NULL,'0',total_sanc) AS total_sanc " +
"FROM " +
"  ( SELECT post_rank_id,post_rank_name,ORDERING_SEQ_NO FROM hrm_mst_post_ranks " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )b " +
"ON a.post_rank_id=b.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT SUM(fillup) snfillt, " +
"    post_rank_id AS rank_id " +
"  FROM " +
"    (SELECT COUNT(*) AS fillup, " +
"      designation_id " +
"    FROM hrm_emp_current_posting c " +
"    WHERE employee_id IN " +
"      (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"      ) " +
"    AND c.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"    AND c.designation_id         IN " +
"      (SELECT designation_id " +
"      FROM hrm_mst_designations " +
"      WHERE post_rank_id IN " +
"        (SELECT post_rank_id FROM hrm_mst_post_ranks " +
"        ) " +
"      ) " +
"    GROUP BY DESIGNATION_ID " +
"    UNION " +
"    SELECT COUNT(*) AS FILLUP, " +
"      DES_IN_TWAD " +
"    FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"    GROUP BY DES_IN_TWAD " +
"    )curr " +
"  LEFT OUTER JOIN " +
"    ( SELECT post_rank_id,designation_id FROM hrm_mst_designations " +
"    )des " +
"  ON curr.designation_id =des.designation_id " +
"  GROUP BY post_rank_id " +
"  )x ON x.rank_id=a.post_rank_id " +
"ORDER BY a.ORDERING_SEQ_NO";

}
else  if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("DPT")) && (empstatus.equalsIgnoreCase("Spc")) && (desig.equalsIgnoreCase("Rank")))
{
sql="SELECT a.post_rank_id, " +
"  post_rank_name, " +
"  DECODE(snfillt,NULL,'0',snfillt)       AS snfillt, " +
"  DECODE(total_sanc,NULL,'0',total_sanc) AS total_sanc " +
"FROM " +
"  (SELECT POST_RANK_ID, " +
"    post_rank_name , " +
"    ORDERING_SEQ_NO " +
"  FROM HRM_MST_POST_RANKS " +
"  WHERE post_rank_id IN("+rank_id+") " +
"  )a " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS total_sanc " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )b " +
"ON a.post_rank_id=b.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT SUM(fillup) AS snfillt, " +
"    post_rank_id      AS rank_id " +
"  FROM " +
"    (SELECT COUNT(*) AS fillup, " +
"      designation_id " +
"    FROM hrm_emp_current_posting c " +
"    WHERE employee_id IN " +
"      (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"      ) " +
"    AND c.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"    AND c.designation_id         IN " +
"      (SELECT designation_id " +
"      FROM hrm_mst_designations " +
"      WHERE post_rank_id IN " +
"        (SELECT post_rank_id FROM hrm_mst_post_ranks " +
"        ) " +
"      ) " +
"    GROUP BY DESIGNATION_ID " +
"    UNION " +
"    SELECT COUNT(*) AS FILLUP, " +
"      DES_IN_TWAD " +
"    FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"    GROUP BY DES_IN_TWAD " +
"    )curr " +
"  LEFT OUTER JOIN " +
"    ( SELECT post_rank_id,designation_id FROM hrm_mst_designations " +
"    )des " +
"  ON curr.designation_id =des.designation_id " +
"  GROUP BY post_rank_id " +
"  )x ON x.rank_id=a.post_rank_id " +
"ORDER BY a.ORDERING_SEQ_NO";

}



 if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("OTHERS")) && (empstatus.equalsIgnoreCase("All")) && (desig.equalsIgnoreCase("Desig")))
{
System.out.println("inside Other");
sql=
"SELECT sub.designation_id, " +
"  DESIGNATION                    AS post_rank_name, " +
"  DECODE(FILLUP,NULL,'0',FILLUP) AS SNFILLT , " +
"  0                              AS total_sanc , " +
"  ORDERING_SEQUENCE_NO " +
"FROM " +
"  (SELECT COUNT(*) AS FILLUP , " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING C " +
"  WHERE EMPLOYEE_ID IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  AND C.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','DPN','CMR') " +
"  GROUP BY DESIGNATION_ID " +
"  UNION " +
"  SELECT COUNT(*) AS FILLUP, " +
"    DES_IN_TWAD " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"  GROUP BY DES_IN_TWAD " +
"  )MST " +
"LEFT OUTER JOIN " +
"  (SELECT DESIGNATION_ID, " +
"    DESIGNATION, " +
"    ORDERING_SEQUENCE_NO " +
"  FROM HRM_MST_DESIGNATIONS " +
"  )SUB " +
"ON sub.DESIGNATION_ID=mst.DESIGNATION_ID " +
"ORDER BY ORDERING_SEQUENCE_NO";
}
else if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("DPT")) && (empstatus.equalsIgnoreCase("All")) && (desig.equalsIgnoreCase("Desig")))
{
System.out.println("inside EXC 0000000");
sql= "SELECT mst.designation_id, " +
"  DESIGNATION                    AS post_rank_name, " +
"  DECODE(FILLUP,NULL,'0',FILLUP) AS SNFILLT , " +
"  0                              AS total_sanc , " +
"  ORDERING_SEQUENCE_NO " +
"FROM " +
"  (SELECT COUNT(*) AS FILLUP , " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING C " +
"  WHERE EMPLOYEE_ID IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  AND C.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"  GROUP BY DESIGNATION_ID " +
"  UNION " +
"  SELECT COUNT(*) AS FILLUP, " +
"    DES_IN_TWAD " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"  GROUP BY DES_IN_TWAD " +
"  )MST " +
"LEFT OUTER JOIN " +
"  (SELECT DESIGNATION_ID, " +
"    DESIGNATION, " +
"    ORDERING_SEQUENCE_NO " +
"  FROM HRM_MST_DESIGNATIONS " +
"  )SUB " +
"ON sub.DESIGNATION_ID=mst.DESIGNATION_ID " +
"ORDER BY ORDERING_SEQUENCE_NO";
}
if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("OTHERS")) && (empstatus.equalsIgnoreCase("Spc")) && (desig.equalsIgnoreCase("Desig")))
{
System.out.println("inside Other");
sql=  "SELECT mst.designation_id, " +
"  DESIGNATION                    AS post_rank_name, " +
"  DECODE(FILLUP,NULL,'0',FILLUP) AS SNFILLT , " +
"  0                              AS total_sanc " +
"FROM " +
"  (SELECT COUNT(*) AS FILLUP , " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING C " +
"  WHERE EMPLOYEE_ID IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  AND C.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','DPN','CMR') " +
"  AND DESIGNATION_ID           IN( "+ des_id+") " +
"  GROUP BY DESIGNATION_ID " +
"  UNION " +
"  SELECT COUNT(*) AS FILLUP, " +
"    DES_IN_TWAD " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL'  and  DES_IN_TWAD  IN( "+ des_id+") " +
"  GROUP BY DES_IN_TWAD " +
"  )MST " +
"LEFT OUTER JOIN " +
"  (SELECT DESIGNATION_ID, " +
"    DESIGNATION, " +
"    ORDERING_SEQUENCE_NO " +
"  FROM HRM_MST_DESIGNATIONS " +
"  )SUB " +
"ON sub.DESIGNATION_ID=mst.DESIGNATION_ID " +
"ORDER BY ORDERING_SEQUENCE_NO";
}
else if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("DPT")) && (empstatus.equalsIgnoreCase("Spc")) && (desig.equalsIgnoreCase("Desig")))
{
System.out.println("inside EXC");
sql= "SELECT mst.designation_id, " +
"  DESIGNATION                    AS post_rank_name, " +
"  DECODE(FILLUP,NULL,'0',FILLUP) AS SNFILLT , " +
"  0                              AS total_sanc, " +
"  ORDERING_SEQUENCE_NO " +
"FROM " +
"  (SELECT COUNT(*) AS FILLUP , " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING C " +
"  WHERE EMPLOYEE_ID IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  AND C.EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"     AND DESIGNATION_ID           IN("+ des_id+") " +
"  GROUP BY DESIGNATION_ID " +
"  UNION " +
"  SELECT COUNT(*) AS FILLUP, " +
"    DES_IN_TWAD " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' and  DES_IN_TWAD IN( "+ des_id+") " +
"  GROUP BY DES_IN_TWAD " +
"  )MST " +
"LEFT OUTER JOIN " +
"  (SELECT DESIGNATION_ID, " +
"    DESIGNATION, " +
"    ORDERING_SEQUENCE_NO " +
"  FROM HRM_MST_DESIGNATIONS " +
"  )SUB " +
"ON sub.DESIGNATION_ID=mst.DESIGNATION_ID " +
"ORDER BY ORDERING_SEQUENCE_NO";
}

if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("OTHERS")) && (empstatus.equalsIgnoreCase("All")) && (desig.equalsIgnoreCase("Cadre")))
{
sql="SELECT SUM(FILLUP) AS SNFILLT , " +
"  ORDERING_SEQUENCE_NO, " +
"  CADRE_ID, " +
"  CADRE_NAME AS POST_RANK_NAME, " +
"  0          AS total_sanc " +
"FROM " +
"  (SELECT COUNT(*) AS FILLUP, " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR','DPN') " +
"  AND EMPLOYEE_ID              IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  GROUP BY DESIGNATION_ID " +
"  UNION " +
"  SELECT COUNT(*) AS FILLUP, " +
"    DES_IN_TWAD " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL'  " +
"  GROUP BY DES_IN_TWAD " +
"  )A " +
"INNER JOIN " +
"  (SELECT DESIGNATION_ID desig , " +
"    CADRE_ID, " +
"    ORDERING_SEQUENCE_NO " +
"  FROM HRM_MST_DESIGNATIONS " +
"  )B " +
"ON B.DESIG=A.DESIGNATION_ID " +
"INNER JOIN " +
"  (SELECT MIN(B.ORDERING_SEQUENCE_NO) AS ORDERING_SEQUENCE_NO, " +
"    A.CADRE_ID, " +
"    A.CADRE_NAME " +
"  FROM HRM_MST_CADRE A , " +
"    HRM_MST_DESIGNATIONS B " +
"  WHERE A.CADRE_ID=B.CADRE_ID " +
"  GROUP BY A.CADRE_ID, " +
"    A.CADRE_NAME " +
"  )C " +
"ON C.CADRE_ID=B.CADRE_ID " +
"GROUP BY ORDERING_SEQUENCE_NO, " +
"  CADRE_ID, " +
"  CADRE_NAME, " +
"  0 " +
"ORDER BY ORDERING_SEQUENCE_NO" ;
}
if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("DPT")) && (empstatus.equalsIgnoreCase("All")) && (desig.equalsIgnoreCase("Cadre")))
{
System.out.println("carde-----99999999");
sql="SELECT SUM(FILLUP) AS SNFILLT , " +
"  ORDERING_SEQUENCE_NO, " +
"  CADRE_ID, " +
"  CADRE_NAME AS POST_RANK_NAME, " +
"  0          AS total_sanc " +
"FROM " +
"  (SELECT COUNT(*) AS FILLUP, " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"  AND EMPLOYEE_ID              IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  GROUP BY DESIGNATION_ID " +
"  UNION " +
"  SELECT COUNT(*) AS FILLUP, " +
"    DES_IN_TWAD " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"  GROUP BY DES_IN_TWAD " +
"  )A " +
"INNER JOIN " +
"  (SELECT DESIGNATION_ID desig , " +
"    CADRE_ID, " +
"    ORDERING_SEQUENCE_NO " +
"  FROM HRM_MST_DESIGNATIONS " +
"  )B " +
"ON B.DESIG=A.DESIGNATION_ID " +
"INNER JOIN " +
"  (SELECT MIN(B.ORDERING_SEQUENCE_NO) AS ORDERING_SEQUENCE_NO, " +
"    A.CADRE_ID, " +
"    A.CADRE_NAME " +
"  FROM HRM_MST_CADRE A , " +
"    HRM_MST_DESIGNATIONS B " +
"  WHERE A.CADRE_ID=B.CADRE_ID " +
"  GROUP BY A.CADRE_ID, " +
"    A.CADRE_NAME " +
"  )C " +
"ON C.CADRE_ID=B.CADRE_ID " +
"GROUP BY ORDERING_SEQUENCE_NO, " +
"  CADRE_ID, " +
"  CADRE_NAME, " +
"  0 " +
"ORDER BY ORDERING_SEQUENCE_NO" ;
}
if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("OTHERS")) && (empstatus.equalsIgnoreCase("Spc")) && (desig.equalsIgnoreCase("Cadre")))
{
System.out.println("insdie other");

sql="SELECT SUM(FILLUP) AS SNFILLT , " +
"  ORDERING_SEQUENCE_NO, " +
"  CADRE_ID, " +
"  CADRE_NAME AS POST_RANK_NAME, " +
"  0          AS total_sanc " +
"FROM " +
"  (SELECT COUNT(*) AS FILLUP, " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR','DPN') " +
"  AND EMPLOYEE_ID              IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  GROUP BY DESIGNATION_ID " +
"  UNION " +
"  SELECT COUNT(*) AS FILLUP, " +
"    DES_IN_TWAD " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES X WHERE PROCESS_FLOW_STATUS_ID!='CL' " +
"  GROUP BY DES_IN_TWAD " +
"  )A " +
"INNER JOIN " +
"  (SELECT DESIGNATION_ID desig , " +
"    CADRE_ID, " +
"    ORDERING_SEQUENCE_NO " +
"  FROM HRM_MST_DESIGNATIONS " +
"  WHERE CADRE_ID IN("+cadre_id+") " +
"  )B " +
"ON B.DESIG=A.DESIGNATION_ID " +
"INNER JOIN " +
"  (SELECT MIN(B.ORDERING_SEQUENCE_NO) AS ORDERING_SEQUENCE_NO, " +
"    A.CADRE_ID, " +
"    A.CADRE_NAME " +
"  FROM HRM_MST_CADRE A , " +
"    HRM_MST_DESIGNATIONS B " +
"  WHERE A.CADRE_ID=B.CADRE_ID " +
"  GROUP BY A.CADRE_ID, " +
"    A.CADRE_NAME " +
"  )C " +
"ON C.CADRE_ID=B.CADRE_ID " +
"GROUP BY ORDERING_SEQUENCE_NO, " +
"  CADRE_ID, " +
"  CADRE_NAME, " +
"  0 " +
"ORDER BY ORDERING_SEQUENCE_NO" ;
}
if((san_str.equalsIgnoreCase("EXC")) && (status.equalsIgnoreCase("DPT")) && (empstatus.equalsIgnoreCase("Spc")) && (desig.equalsIgnoreCase("Cadre")))
{
System.out.println("insdie other");

sql="SELECT SUM(FILLUP) AS SNFILLT , " +
"  ORDERING_SEQUENCE_NO, " +
"  CADRE_ID, " +
"  CADRE_NAME AS POST_RANK_NAME, " +
"  0          AS total_sanc " +
"FROM " +
"  (SELECT COUNT(*) AS FILLUP, " +
"    DESIGNATION_ID " +
"  FROM HRM_EMP_CURRENT_POSTING " +
"  WHERE EMPLOYEE_STATUS_ID NOT IN('SAN','DIS','RES','MEV','VRS','DTH','CMR') " +
"  AND EMPLOYEE_ID              IN " +
"    (SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE IS_CONSOLIDATE='N' " +
"    ) " +
"  GROUP BY DESIGNATION_ID " +
"  UNION " +
"  SELECT COUNT(*) AS FILLUP, " +
"    DES_IN_TWAD " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES X  WHERE PROCESS_FLOW_STATUS_ID!='CL' and DES_IN_TWAD IN( "+ cadre_id+") " +
"  GROUP BY DES_IN_TWAD " +
"  )A " +
"INNER JOIN " +
"  (SELECT DESIGNATION_ID desig , " +
"    CADRE_ID, " +
"    ORDERING_SEQUENCE_NO " +
"  FROM HRM_MST_DESIGNATIONS " +
"  WHERE CADRE_ID IN("+cadre_id+") " +
"  )B " +
"ON B.DESIG=A.DESIGNATION_ID " +
"INNER JOIN " +
"  (SELECT MIN(B.ORDERING_SEQUENCE_NO) AS ORDERING_SEQUENCE_NO, " +
"    A.CADRE_ID, " +
"    A.CADRE_NAME " +
"  FROM HRM_MST_CADRE A , " +
"    HRM_MST_DESIGNATIONS B " +
"  WHERE A.CADRE_ID=B.CADRE_ID " +
"  GROUP BY A.CADRE_ID, " +
"    A.CADRE_NAME " +
"  )C " +
"ON C.CADRE_ID=B.CADRE_ID " +
"GROUP BY ORDERING_SEQUENCE_NO, " +
"  CADRE_ID, " +
"  CADRE_NAME, " +
"  0 " +
"ORDER BY ORDERING_SEQUENCE_NO" ;
}
System.out.println("sql is--->"+sql);
int tot_sac=0;
int tot_emp=0;
ps=connection.prepareStatement(sql);
results=ps.executeQuery();

while(results.next())
{
int sanc=0;
int emp=0;
sanc=results.getInt("total_sanc");
emp=results.getInt("snfillt");
tot_sac=tot_sac+sanc;
tot_emp=tot_emp+emp;

if( sanc >0 || emp>0)
{
sl=sl+1;
if ( (sl % 2) == 0)
{
 out.print("<tr bgcolor='#C2DFFF'><td>"+sl+"</td><td>"+results.getString("post_rank_name")+"</td>");
 if(san_str.equalsIgnoreCase("INC"))
 out.print("<td id='tot_san' align='right' >"+sanc+"</td>");
 out.println("<td align='right'>"+emp+"</td></tr>");
 }
 else
 {
  out.print("<tr bgcolor=''><td>"+sl+"</td><td>"+results.getString("post_rank_name")+"</td>");
 if(san_str.equalsIgnoreCase("INC"))
 out.print("<td id='tot_san' align='right' >"+sanc+"</td>");
 out.println("<td align='right'>"+emp+"</td></tr>");
 }
 }
}

  
  
   %>
     
    <tr><td colspan="2" align="right" >Total</td>
     <% if(san_str.equalsIgnoreCase("INC") ){%>
    <td colspan="" align='right'><%=tot_sac %></td>
    <% } %>
    <td colspan="" align='right'><%=tot_emp %></td></tr>            
                
                
                
                
                
                
                
                
                
      
       <tr>
			<td colspan="<%= colspan %>" class="tdH" align="center">	
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