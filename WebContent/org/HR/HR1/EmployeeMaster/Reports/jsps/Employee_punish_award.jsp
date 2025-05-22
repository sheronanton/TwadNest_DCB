<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>  
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>  Employee profile with punishment details </title>
<link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
<style type="text/css">





</style>
   <script type="text/javascript"
            src="../scripts/Hrm_Employee_Punish_award.js"></script>
</head>
<body>
<%

  Connection connection=null;
  PreparedStatement ps=null,pss=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null,rss=null;
  
  
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
 
  %>
<form name="punish_emp_award" method="POST" action="../../../../../../punish_emp_award_rep">

<input type="hidden" name="emp_id" id="emp_id" value="<%=empid%>"> 
<input type="hidden" name="output" id="output" value="PDF" />

<table width="117%" style="border:1px solid #0489B1" >
<tr style="height:30px" >

<td width="30%" align="right" style="color: #fff;padding-left:5px;" bgcolor="#006699"><img alt="Back" src="back.png" width="30" height="30" style="cursor:pointer" onclick="window.history.go(-1)"  title="Go Back" > &nbsp; &nbsp; <img alt="Back" src="close.png" width="30" height="30" style="cursor:pointer" onclick="self.close()" title="Close" ></td>
<td align="center" style="color: #fff;padding-left:5px;" bgcolor="#006699"> Employee profile with punishment details</td>
<td   width="30%" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699" > <img  alt="PDF" src="pdf.png" width="30" height="30" style="cursor:pointer" onclick="download('PDF')" title="Download PDF" > &nbsp; &nbsp; <img style="display:none" alt="Excel" width="30" height="30" src="excel.png"   style="cursor:pointer" onclick="download('EXCEl')" title="Download Excel" ></td>
</tr>
</table>
<table width="117%">
<%
int emp=0;
int ctrl_of_id=0;
String empnames=null;
String desigs=null;
String grade=null;
String office=null;
String cur_status=null;
String cur_dte=null;
String dt_birth=null;
String dt_retire=null;
String gender=null;
String comm=null;
String dist=null;
String taluk=null;
String ctrl_name=null;

String sql="SELECT * " +
"FROM " +
"  (SELECT a.EMPLOYEE_ID, " +
"    DECODE(a.EMPLOYEE_INITIAL,NULL,' ',a.EMPLOYEE_INITIAL " +
"    ||'.') " +
"    ||a.EMPLOYEE_NAME EMPLOYEE_NAME, " +
"    a.GPF_NO, " +
"    TO_CHAR(a.DATE_OF_BIRTH,'dd/mm/yyyy') as DATE_OF_BIRTH, " +
"    a.GENDER, " +
"    a.REMARKS, " +
"    a.MARITAL_STATUS, " +
"    decode(COMMUNITY_NAME,null, ' ',COMMUNITY_NAME) as COMMUNITY_NAME, " +
"    c.District_Name, " +
"    d.Taluk_Name, " +
"    e.EMPLOYMENT_STATUS, " +
"    g.photo, " +
"    g.process_flow_status_id, " +
"    CASE a.is_consolidate " +
"      WHEN 'N' " +
"      THEN '' " +
"      ELSE '(Consolidated)' " +
"    END AS consolidate " +
"  FROM HRM_MST_EMPLOYEES a " +
"  LEFT OUTER JOIN HRM_MST_COMMUNITY b " +
"  ON to_number(b.COMMUNITY_CODE)=to_number(a.COMMUNITY_ID) " +
"  LEFT OUTER JOIN COM_MST_DISTRICTS c " +
"  ON c.District_Code=a.NATIVE_DISTRICT_CODE " +
"  LEFT OUTER JOIN COM_MST_TALUKS d " +
"  ON d.Taluk_Code    =a.NATIVE_TALUK_CODE " +
"  AND d.DISTRICT_CODE=a.NATIVE_DISTRICT_CODE " +
"  LEFT OUTER JOIN HRM_MST_EMPLOYMENT_STATUS e " +
"  ON e.EMPLOYMENT_STATUS_ID=a.EMPLOYMENT_STATUS_ID " +
"  LEFT OUTER JOIN hrm_emp_addl_details f " +
"  ON f.EMPLOYEE_ID=a.EMPLOYEE_ID " +
"  LEFT OUTER JOIN HRM_EMP_ADDL_PHOTO_view g " +
"  ON g.employee_id   =a.employee_id " +
"  WHERE a.EMPLOYEE_ID="+empid+" " +
"  ) x " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id, " +
"    employee_name, " +
"    to_char(date_of_birth,'dd/mm/yyyy') as date_of_birth, " +
"    to_char(retiredate,'dd/mm/yyyy') as retiredate " +
"  FROM ALLRETIREMENTVIEW " +
"  ) y " +
"ON y.employee_id="+empid+"  " +
"LEFT OUTER JOIN " +
"  (SELECT * " +
"  FROM " +
"    (SELECT EMPLOYEE_ID, " +
"      to_char(DATE_EFFECTIVE_FROM,'dd/mm/yyyy') as DATE_EFFECTIVE_FROM, " +
"      OFFICE_GRADE, " +
"      DEPARTMENT_ID, " +
"      OFFICE_ID, " +
"      designation_id, " +
"      employee_status_id " +
"    FROM HRM_EMP_CURRENT_POSTING " +
"    ) a " +
"  LEFT OUTER JOIN " +
"    ( SELECT * FROM HRM_ALL_DEPT_OFFICES " +
"    ) b " +
"  ON b.dept_id   =a.department_id " +
"  AND b.office_id=a.office_id " +
"  LEFT OUTER JOIN " +
"    (SELECT DESIGNATION_id AS current_designation, " +
"      designation " +
"    FROM HRM_MST_DESIGNATIONS " +
"    ) c " +
"  ON c.current_designation=a.DESIGNATION_ID " +
"  LEFT OUTER JOIN " +
"    (SELECT EMPLOYEE_STATUS_DESC, " +
"      employee_status_id " +
"    FROM HRM_MST_EMPLOYEE_STATUS " +
"    ) d " +
"  ON a.employee_status_id=d.employee_status_id " +
"  ) z ON z.EMPLOYEE_ID   ="+empid+"  " +
"LEFT OUTER JOIN " +
"  (SELECT bb.EMPLOYEE_ID, " +
"    bb.DATE_FROM , " +
"    cc.DESIGNATION joining_designation " +
"  FROM HRM_EMP_SERVICE_DATA bb " +
"  LEFT OUTER JOIN HRM_MST_DESIGNATIONS cc " +
"  ON cc.DESIGNATION_ID= bb.DESIGNATION_ID " +
"  WHERE bb.DATE_FROM  = " +
"    (SELECT MIN(aa.DATE_FROM) " +
"    FROM HRM_EMP_SERVICE_DATA aa " +
"    WHERE aa.EMPLOYEE_ID="+empid+"  " +
"    ) " +
"  ) p ON p.EMPLOYEE_ID="+empid+"  " +
"LEFT OUTER JOIN " +
"  (SELECT a.EMPLOYEE_ID, " +
"    a.CONTROLLING_OFFICE_ID cntrl_office_id, " +
"    b.OFFICE_NAME ctrl_office_name " +
"  FROM HRM_EMP_CONTROLLING_OFFICE a, " +
"    COM_MST_OFFICES b " +
"  WHERE b.OFFICE_ID=a.CONTROLLING_OFFICE_ID " +
"  AND a.EMPLOYEE_ID="+empid+"  " +
"  ) q " +
"ON q.employee_id="+empid+" ";
ps=connection.prepareStatement(sql);
results=ps.executeQuery();
while(results.next())
{
emp=results.getInt("EMPLOYEE_ID");
empnames=results.getString("EMPLOYEE_NAME");
desigs=results.getString("designation");
grade=results.getString("OFFICE_GRADE");
office=results.getString("OFFICE_NAME");
cur_dte=results.getString("DATE_EFFECTIVE_FROM");
cur_status=results.getString("EMPLOYEE_STATUS_DESC");
dt_birth=results.getString("DATE_OF_BIRTH");
dt_retire=results.getString("RETIREDATE");
 gender=results.getString("GENDER");
 comm=results.getString("COMMUNITY_NAME");
 dist=results.getString("DISTRICT_NAME");
 taluk=results.getString("TALUK_NAME");
 ctrl_of_id=results.getInt("CNTRL_OFFICE_ID");
 ctrl_name=results.getString("CTRL_OFFICE_NAME");
 }
if(ctrl_name=="null" || ctrl_name==null )
{
	ctrl_name="";
}
if(office=="null" || office==null)
{
	office="";
}
if(gender.equalsIgnoreCase("M"))
{
	gender="Male";
}
if(gender.equalsIgnoreCase("F"))
{
	gender="Female";
}
 %>
 <tr style="color:#fff" bgcolor="#006699"><td colspan="16">Employee Details</td></tr>
<tr>
<td width="30%">Employee Id :</td>
<td><%=emp%>
<%
                             String newpath="";
                           
                            //newpath="../../../../../../org/HR/HR1/EmployeeMaster/jsps/ShowImageProfile.jsp?empid="+strEmpId;
                            newpath="../../../EmployeeMaster/jsps/ShowImageProfile.jsp?empid="+empid;
                            %>
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <img  src=<%=newpath%> align="top" alt="Photo" width="90" height="90" id="EmpImage"></img>
</td>

                            
                       
</tr>
<tr>
<td >Employee Name :</td>
<td><%=empnames%></td>
</tr>
<tr>
<td>Designation  :</td>
<td><%=desigs%></td>
</tr>
<tr>
<td>Grade   :</td>
<td><%=grade%></td>
</tr>
<tr>
<td>Office   :</td>
<td><%=office%></td>
</tr>
<tr>
<td>Date of Current Posting  :</td>
<td><%=cur_dte%></td>
<tr>
<td>Current Status  :</td>
<td><%=cur_status%></td>
</tr>
<tr style="color:#fff" bgcolor="#006699"><td colspan="16"> General Particulars</td></tr>
<tr>
<td>Date Of Birth   :</td>
<td><%=dt_birth%></td>
</tr>
<tr>
<td>Date Of Retirement   :</td>
<td><%=dt_retire%></td>
</tr>
<tr>
<td>Gender  :</td>
<td><%=gender%></td>
</tr>
<tr>
<td>Community :</td>
<td><%=comm%></td>
</tr>
<tr>
<td>District   :</td>
<td><%=dist%></td>
</tr>
<tr>
<td>Taluk   :</td>
<td><%=taluk%></td>
</tr>
<tr  style="color:#fff" bgcolor="#006699"><td colspan="16"> SR Controlling Office Details </td></tr>
<tr>
<td>SR Controlling Office ID    :</td>
<td><%=ctrl_of_id%></td>
</tr>
<tr>
<td>SR Controlling Office Name    :</td>
<td><%=ctrl_name%></td>
</tr>
<tr style="color:#fff" bgcolor="#006699"><td colspan="16">Punishment Details</td></tr>
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
if(officename==null || officename=="null")
{
	officename="";
}
if((c % 2)==0)
{
    out.print("<tr bgcolor='#C2DFFF'><td>"+cnt+"</td><td>"+empid1+"</td><td>"+empname+"</td><td>"+desig+"</td><td>"+officename+"</td><td align='center'>"+typcharge+"</td><td>"+gistchrge+"</td><td>"+tensure+"</td><td>"+punish_dt+"</td><td>"+punish_typ+"</td><td align='center'>"+W_app+"</td><td>"+app_order_dt+"</td><td align='center'>"+punisg_in_appl+"</td><td align='center'>"+court_case+"</td><td align='center'>"+stay+"</td align='center'><td align='center'>"+con_promo+"</td><td>"+no_date+"</td></tr>");
}
else
{
    out.print("<tr><td>"+cnt+"</td><td>"+empid1+"</td><td>"+empname+"</td><td>"+desig+"</td><td>"+officename+"</td><td align='center'>"+typcharge+"</td><td>"+gistchrge+"</td><td>"+tensure+"</td><td>"+punish_dt+"</td><td>"+punish_typ+"</td><td align='center'>"+W_app+"</td><td>"+app_order_dt+"</td><td align='center'>"+punisg_in_appl+"</td><td align='center'>"+court_case+"</td><td align='center'>"+stay+"</td align='center'><td align='center'>"+con_promo+"</td><td>"+no_date+"</td></tr>");
}
}

if(cnt==0)
{
	out.print("<tr align='center'><td colspan='16'>There is no Data</td></tr>");
}
 %> 
</table>





 
<table width="117%" style="border:1px solid #0489B1" >
<tr style="color:#fff" bgcolor="#006699"><td colspan="16">Service Details</td></tr>
<tr style="color:#fff" bgcolor="#4D94B8" align="center"  >
<td width="3%" >Slno</td>
<td width="10%" >Category </td>
<td width="5%">EmpCode </td>
<td width="12%">Date From </td>
<td width="12%">Date From Session</td>
<td width="16%">Date To </td>
<td width="16%">Date To Session </td>
<td width="9%">Service Designation </td>
<td width="12%">Office Name </td>
<td width="12%">Employee Status </td>
<td width="9%">Controlling Office Id </td>
<td width="9%">Controlling Office Name </td>

</tr>


<%


String Category="",date_from="",date_from_session="",date_to="",date_to_session="",service_designation="",office_name="",employee_status_desc="";
String controlling_office_id="",controlling_office_name="";
int employee_id;

String sql2=
"SELECT Category, " +
"  employee_id, " +
"  to_char(date_from, 'dd/mm/yyyy') as date_from," +
"  decode(date_from_session, 'null','',date_from_session) as date_from_session," +
"  DECODE((TO_CHAR(date_to, 'dd/mm/yyyy')) ,null,'',(TO_CHAR(date_to, 'dd/mm/yyyy'))) AS date_to," +
"  decode(date_to_session, null,'',date_to_session) as date_to_session," +
"  designation AS service_designation, " +
"  office_name, " +
"  employee_status_desc, " +
"  controlling_office_id, " +
"  controlling_office_name " +
"FROM " +
"  (SELECT * " +
"  FROM " +
"    (SELECT * " +
"    FROM " +
"      (SELECT 'Service Particulars' AS Category, " +
"        employee_id, " +
"        date_from , " +
"        date_from_session, " +
"        date_to, " +
"        date_to_session, " +
"        designation_id, " +
"        office_id, " +
"        office_dept_id, " +
"        employee_status_id, " +
"        process_flow_status_id " +
"      FROM hrm_emp_service_data " +
"      WHERE employee_id         ="+empid+ " "+
"      AND process_flow_status_id='FR' " +
"      ) " +
"    UNION " +
"    SELECT 'Current Posting' AS Category, " +
"      employee_id, " +
"      date_effective_from         AS date_from , " +
"      DATE_EFFECTIVE_FROM_SESSION AS date_from_session, " +
"      TO_DATE('', 'DD-MON-YY')    AS date_to, " +
"      ''                          AS date_to_session, " +
"      designation_id, " +
"      office_id, " +
"      department_id AS office_dept_id, " +
"      employee_status_id, " +
"      process_flow_status_id " +
"    FROM hrm_emp_current_posting " +
"    WHERE employee_id="+empid+ " "+
"    ) " +
"  ORDER BY category DESC, " +
"    date_from " +
"  ) aa " +
"LEFT OUTER JOIN " +
"  ( SELECT designation_id, designation FROM hrm_mst_designations " +
"  ) bb " +
"ON aa.designation_id = bb.designation_id " +
"LEFT OUTER JOIN " +
"  ( SELECT 'TWAD' AS dept_id, office_id, office_name FROM com_mst_offices " +
"  UNION " +
"  SELECT other_dept_id     AS dept_id, " +
"    other_dept_office_id   AS office_id, " +
"    other_dept_office_name AS office_name " +
"  FROM hrm_mst_other_dept_offices " +
"  ) cc " +
"ON aa.office_dept_id = cc.dept_id " +
"AND aa.office_id     = cc.office_id " +
"LEFT OUTER JOIN " +
"  (SELECT employee_status_id AS empstat_id, " +
"    employee_status_desc " +
"  FROM hrm_mst_employee_status " +
"  ) dd " +
"ON aa.employee_status_id = dd.empstat_id " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id AS empid4, " +
"    controlling_office_id, " +
"    office_name AS controlling_office_name " +
"  FROM " +
"    ( SELECT employee_id, controlling_office_id FROM hrm_emp_controlling_office " +
"    ) a " +
"  LEFT OUTER JOIN " +
"    ( SELECT office_id, office_name FROM com_mst_offices " +
"    ) b " +
"  ON a.controlling_office_id = b.office_id " +
"  ) ee ON aa.employee_id     = ee.empid4 " +
"ORDER BY aa.DATE_FROM ASC, " +
"  aa.date_from_session DESC"

;

System.out.println("sql2 is----->"+sql2);



ps=connection.prepareStatement(sql2);
results=ps.executeQuery();
int cnt1=0;
int  c1=0;
while(results.next())
{
cnt1++;
 c1=c1+1;
 
employee_id=results.getInt("employee_id");
Category=results.getString("Category");
date_from=results.getString("date_from");
//System.out.println("date_from   ===   "+date_from);
date_from_session=results.getString("date_from_session");
date_to=results.getString("date_to");
if(date_to==null)
date_to="";
date_to_session=results.getString("date_to_session");
if(date_to_session==null)
date_to_session="";
service_designation=results.getString("service_designation");
//System.out.println("service_designation   ===   "+service_designation);
office_name=results.getString("office_name");
employee_status_desc=results.getString("employee_status_desc");
controlling_office_id=results.getString("controlling_office_id");
controlling_office_name=results.getString("controlling_office_name");

//System.out.println("controlling_office_name   ===   "+controlling_office_name);
if(office_name==null || office_name=="null")
{
	office_name="";
}
if((c1 % 2)==0)
{
    out.print("<tr bgcolor='#C2DFFF'><td>"+cnt1+"</td><td>"+Category+"</td><td>"+employee_id+"</td><td>"+date_from+"</td><td>"+date_from_session+"</td><td align='center'>"+date_to+"</td><td>"+date_to_session+"</td><td>"+service_designation+"</td><td>"+office_name+"</td><td>"+employee_status_desc+"</td><td align='center'>"+controlling_office_id+"</td><td>"+controlling_office_name+"</td></tr>");
}
else
{
    out.print("<tr><td>"+cnt1+"</td><td>"+Category+"</td><td>"+employee_id+"</td><td>"+date_from+"</td><td>"+date_from_session+"</td><td align='center'>"+date_to+"</td><td>"+date_to_session+"</td><td>"+service_designation+"</td><td>"+office_name+"</td><td>"+employee_status_desc+"</td><td align='center'>"+controlling_office_id+"</td><td>"+controlling_office_name+"</td></tr>");
}
}

%>


</table>
 









<table width="117%" style="border:1px solid #0489B1" >
<tr style="height:30px" >

<td width="30%" align="right" style="color: #fff;padding-left:5px;" bgcolor="#006699"><img alt="Back" src="back.png" width="30" height="30" style="cursor:pointer" onclick="window.history.go(-1)"  title="Go Back" > &nbsp; &nbsp; <img alt="Back" src="close.png" width="30" height="30" style="cursor:pointer" onclick="self.close()" title="Close" ></td>
<td width="40%" align="center" style="color: #fff;padding-left:5px;" bgcolor="#006699"  > </td>
<td width="30%" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"> <img alt="PDF" src="pdf.png" width="30" height="30" style="cursor:pointer" onclick="download('PDF')" title="Download PDF" > &nbsp; &nbsp; <img style="display:none" alt="Excel" width="30" height="30" src="excel.png" style="cursor:pointer" onclick="download('EXCEl')" title="Download Excel" ></td>
</tr>
<tr><td colspan="3" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"><font size="2">1. The report based on the service entry made in the online system. If any unfreezed records of the employee or overlapping in the service particulars, then it will give wrong report
on the individual .</font> </td></tr>
<tr><td colspan="3" align="left" style="color: #fff;padding-left:5px;" bgcolor="#006699"><font size="2">2. Division details only captured for all old offices in the service particulars. Hence the work nature is based on the division only. If the individual worked in the sub division and it
contains different nature of work, then it will give the wrong result.</font></td></tr>
</table>


</form>
</body>
</html>