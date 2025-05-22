<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>  
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>  Employee Punishment Available Report </title>
<link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
<style type="text/css">





</style>
   <script type="text/javascript"
            src="../scripts/Hrm_Employee_Punish_avai.js"></script>
</head>
<body>
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
  
 int empid=0;
 String punish_avail="";
 empid=Integer.parseInt(request.getParameter("emp_id"));
 punish_avail=request.getParameter("punish_yes");
 System.out.println("empid---->"+empid);
 System.out.println("punish_avail--->"+punish_avail);
  %>
<form name="punish_emp_av" method="POST" action="../../../../../../punish_emp_av_rep">

<input type="hidden" name="emp_id" id="emp_id" value="<%=empid%>"> 
<input type="hidden" name="punish_av" id="punish_av" value="<%=punish_avail%>"> 
<input type="hidden" name="output" id="output" value="PDF" />

<table width="117%" style="border:1px solid #0489B1" >
<tr style="height:30px" >

<td width="30%" align="right" style="color: #fff;padding-left:5px;" bgcolor="#006699"><img alt="Back" src="back.png" width="30" height="30" style="cursor:pointer" onclick="window.history.go(-1)"  title="Go Back" > &nbsp; &nbsp; <img alt="Back" src="close.png" width="30" height="30" style="cursor:pointer" onclick="self.close()" title="Close" ></td>
<td align="center" style="color: #fff;padding-left:5px;" bgcolor="#006699">Employee Punishment  Available Report </td>
<td   width="30%" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699" > <img alt="PDF" src="pdf.png" width="30" height="30" style="cursor:pointer" onclick="download('PDF')" title="Download PDF" > &nbsp; &nbsp; <img alt="Excel" width="30" height="30" src="excel.png"   style="cursor:pointer" onclick="download('EXCEl')" title="Download Excel" ></td>
</tr>
</table>
<table width="117%" style="border:1px solid #0489B1" >
<tr style="color:#fff" bgcolor="#4D94B8" align="center"  >
<td width="3%" >Sl. No </td>
<td width="5%">EmpCode </td>
<td width="12%">Employee Name </td>
<td width="12%">Designation </td>
<td width="16%">Office Name </td>
<td width="10%">Type of charge </td>
<td width="25%">Gist of charge </td>
<td width="12%">Tenure </td>
<td width="12%">Punishment date </td>
<td width="9%">Type of punishment </td>
<td width="9%">Whether appealed </td>
<td width="9%">Appeal order date </td>
<td width="9%">Punishment in the appeal </td>
<td width="9%">Is it in court case </td>
<td width="9%">is it in stay </td>
<td width="9%">Can be considered for promotion </td>
<td width="9%">No, upto what date </td>
</tr>

<%
int empid1=0;
String empname,desig,officename,typcharge,gistchrge,tensure,tensure_frm,tensure_to,punish_dt,punish_typ,W_app,app_order_dt,punisg_in_appl,court_case,stay,con_promo,no_date="";
if(punish_avail.equalsIgnoreCase("Y"))
{
String sql="";


sql="SELECT mst.EMPLOYEE_ID, " +
"  empname, " +
"  DESIGNATION,PUNISHMENT_DESC, " +
"  Office_Name, " +
"  PUNISHMENT_SLNO, " +
"  CHARGE_TYPE, " +
"  GIST_OF_CHARGE, " +
"  DESIGATION_HELD, " +
"  OFFICE_WORKED, " +
"  TENURE_FROM, " +
"  TENURE_TO, " +
"  PUNISHMENT_TYPE_ID, " +
"  PUNISHMENT_DETAILS, " +
"  PUNISHMENT_REF_NO, " +
"  decode(PUNISHMENT_REF_DATE,null,' ',PUNISHMENT_REF_DATE) as PUNISHMENT_REF_DATE, " +
"  APPEAL_EXISTS, " +
"  APPEAL_REF_NO, " +
"   decode(APPEAL_REF_DATE,null,' ',APPEAL_REF_DATE) as APPEAL_REF_DATE, " +
"  decode(COURT_CASE_EXISTS,null,' ',COURT_CASE_EXISTS) as COURT_CASE_EXISTS, " +
"  decode(IS_STAY_OBTAINED,null,' ',IS_STAY_OBTAINED) as IS_STAY_OBTAINED, " +
"  decode(CONSIDER_FOR_PROMOTION,null,' ',CONSIDER_FOR_PROMOTION) as CONSIDER_FOR_PROMOTION, " +
"  decode(DATE_UPTO_NO_CONSIDER,null,' ',DATE_UPTO_NO_CONSIDER) as DATE_UPTO_NO_CONSIDER, " +
"  REMARKS, " +
"  UPDATED_BY_USER_ID, " +
"  UPDATED_DATE, " +
"  PROCESS_FLOW_ID, " +
"  APPEAL_DETAILS, " +
"  UPDATED_BY_OFFICE_ID, " +
"  APPEAL_DECISION_ORDER, " +
"  decode(PUNISHMENT_APPEAL,null,' ',PUNISHMENT_APPEAL) as PUNISHMENT_APPEAL, " +
"  STAY_INFO " +
"FROM " +
"  (SELECT EMPLOYEE_ID, " +
"    PUNISHMENT_SLNO, " +
"    CHARGE_TYPE, " +
"    GIST_OF_CHARGE, " +
"    DESIGATION_HELD, " +
"    OFFICE_WORKED, " +
"    TO_CHAR(TENURE_FROM,'dd/mm/yyyy') AS TENURE_FROM, " +
"    TO_CHAR(TENURE_TO,'dd/mm/yyyy')   AS TENURE_TO, " +
"    PUNISHMENT_TYPE_ID, " +
"    PUNISHMENT_DETAILS, " +
"    PUNISHMENT_REF_NO, " +
"    TO_CHAR(PUNISHMENT_REF_DATE,'dd/mm/yyyy') AS PUNISHMENT_REF_DATE, " +
"    APPEAL_EXISTS, " +
"    APPEAL_REF_NO, " +
"    TO_CHAR(APPEAL_REF_DATE,'dd/mm/yyyy') AS APPEAL_REF_DATE, " +
"    COURT_CASE_EXISTS, " +
"    IS_STAY_OBTAINED, " +
"    CONSIDER_FOR_PROMOTION, " +
"    TO_CHAR(DATE_UPTO_NO_CONSIDER,'dd/mm/yyyy') AS DATE_UPTO_NO_CONSIDER, " +
"    REMARKS, " +
"    UPDATED_BY_USER_ID, " +
"    UPDATED_DATE, " +
"    PROCESS_FLOW_ID, " +
"    APPEAL_DETAILS, " +
"    UPDATED_BY_OFFICE_ID, " +
"    APPEAL_DECISION_ORDER, " +
"    PUNISHMENT_APPEAL, " +
"    STAY_INFO " +
"  FROM HRM_DP_EMP_PUNISHMENTS_MST " +
"  WHERE employee_id="+empid+" " +
"  )mst " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_ID, " +
"    EMPLOYEE_INITIAL " +
"    || '.' " +
"    || EMPLOYEE_NAME AS empname, " +
"    DESIGNATION " +
"  FROM VIEW_EMPLOYEE2 " +
"  )sub " +
"ON sub.EMPLOYEE_ID=mst.EMPLOYEE_ID " +
"LEFT OUTER JOIN " +
"  (SELECT Office_Id,Office_Name FROM Com_Mst_Offices " +
"  )sub1 " +
"ON sub1.Office_Id=mst.OFFICE_WORKED" +
" left  OUTER JOIN " +
"  (SELECT PUNISHMENT_TYPE_ID,PUNISHMENT_DESC FROM HRM_DP_PUNISHMENT_TYPES_MST " +
"  )sub2 " +
"ON sub2.PUNISHMENT_TYPE_ID=mst.PUNISHMENT_TYPE_ID order by to_date(PUNISHMENT_REF_DATE,'dd/mm/yyyy')";
System.out.println("sql is----->"+sql);
ps=connection.prepareStatement(sql);
results=ps.executeQuery();
int cnt=0;
int c=0;
while(results.next())
{
cnt++;
 c=c+1;
empid1=results.getInt("EMPLOYEE_ID");
empname=results.getString("empname");
desig=results.getString("DESIGNATION");
officename=results.getString("OFFICE_NAME");
typcharge=results.getString("CHARGE_TYPE");
gistchrge=results.getString("GIST_OF_CHARGE");
tensure_frm=results.getString("TENURE_FROM");
tensure_to=results.getString("TENURE_TO");
punish_dt=results.getString("PUNISHMENT_REF_DATE");
punish_typ=results.getString("PUNISHMENT_DESC");
W_app=results.getString("APPEAL_EXISTS");
app_order_dt=results.getString("APPEAL_REF_DATE");
punisg_in_appl=results.getString("PUNISHMENT_APPEAL");
court_case=results.getString("COURT_CASE_EXISTS");
stay=results.getString("IS_STAY_OBTAINED");
con_promo=results.getString("CONSIDER_FOR_PROMOTION");
no_date=results.getString("DATE_UPTO_NO_CONSIDER");
tensure=tensure_frm+" to "+tensure_to;
if((c % 2)==0)
{
    out.print("<tr bgcolor='#C2DFFF'><td>"+cnt+"</td><td>"+empid1+"</td><td>"+empname+"</td><td>"+desig+"</td><td>"+officename+"</td><td align='center'>"+typcharge+"</td><td>"+gistchrge+"</td><td>"+tensure+"</td><td>"+punish_dt+"</td><td>"+punish_typ+"</td><td align='center'>"+W_app+"</td><td>"+app_order_dt+"</td><td align='center'>"+punisg_in_appl+"</td><td align='center'>"+court_case+"</td><td align='center'>"+stay+"</td align='center'><td align='center'>"+con_promo+"</td><td>"+no_date+"</td></tr>");
}
else
{
    out.print("<tr><td>"+cnt+"</td><td>"+empid1+"</td><td>"+empname+"</td><td>"+desig+"</td><td>"+officename+"</td><td align='center'>"+typcharge+"</td><td>"+gistchrge+"</td><td>"+tensure+"</td><td>"+punish_dt+"</td><td>"+punish_typ+"</td><td align='center'>"+W_app+"</td><td>"+app_order_dt+"</td><td align='center'>"+punisg_in_appl+"</td><td align='center'>"+court_case+"</td><td align='center'>"+stay+"</td align='center'><td align='center'>"+con_promo+"</td><td>"+no_date+"</td></tr>");
}
}
}
if(punish_avail.equalsIgnoreCase("N"))
{
out.print("<tr align='center'><td colspan='16' >There is No Data</td></tr>");
}
 %>


</table>
<table width="117%" style="border:1px solid #0489B1" >
<tr style="height:30px" >

<td width="30%" align="right" style="color: #fff;padding-left:5px;" bgcolor="#006699"><img alt="Back" src="back.png" width="30" height="30" style="cursor:pointer" onclick="window.history.go(-1)"  title="Go Back" > &nbsp; &nbsp; <img alt="Back" src="close.png" width="30" height="30" style="cursor:pointer" onclick="self.close()" title="Close" ></td>
<td width="40%" align="center" style="color: #fff;padding-left:5px;" bgcolor="#006699"  > </td>
<td width="30%" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"> <img alt="PDF" src="pdf.png" width="30" height="30" style="cursor:pointer" onclick="download('PDF')" title="Download PDF" > &nbsp; &nbsp; <img alt="Excel" width="30" height="30" src="excel.png" style="cursor:pointer" onclick="download('EXCEl')" title="Download Excel" ></td>
</tr>
<tr><td colspan="3" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"><font size="2">1. The report based on the service entry made in the online system. If any unfreezed records of the employee or overlapping in the service particulars, then it will give wrong report
on the individual .</font> </td></tr>
<tr><td colspan="3" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"><font size="2">2. Division details only captured for all old offices in the service particulars. Hence the work nature is based on the division only. If the individual worked in the sub division and it
contains different nature of work, then it will give the wrong result.</font></td></tr>
</table>


</form>
</body>
</html>