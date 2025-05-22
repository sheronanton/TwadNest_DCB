<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Employee Status wise Details - Report</title>
  
  <meta http-equiv="Pragma" content="no-cache">
    
    <!-- MICROSOFT BROWSERS REQUIRE THIS ADDITIONAL META TAG AS WELL -->
    <meta http-equiv="Expires" content="-1">

    <meta name="archive" content="false">
    <meta http-equiv="imagetoolbar" content="no">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     
    <script type="text/javascript" src="../scripts/comJS.js"></script>
    <script type="text/javascript"       src="../scripts/checkDate.js"></script>
  
    <script type="text/javascript" src="../scripts/NominAjaxOfficeContactId.js"></script>
    <!-- <script type="text/javascript" src="../scripts/controllingOfficeContact.js"></script>-->
    <script type="text/javascript"     src="../scripts/Seniority_Proceeding_master_updation.js"></script>
   
    <style> 



  .karthik tr:nth-child(odd) {
   background-color: #0B615E;
}

.karthik tr:nth-child(even) {
   background-color: #b6eaff;
}

</style>
   <script type="text/javascript">
   function Redirect()
   {
   window.history.go(-1);
   }
   </script>
    <%
     HttpSession session=request.getSession(false);
     
     %>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"         media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"         media="screen"/>
    
      <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  </head>
  <body id="bodyid"  >
  <form name="Seniority_Proceeding_list" id="EmpQual">
      <p>
        <%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
  try
  {
  
	  LoadDriver driver=new LoadDriver();
  	connection=driver.getConnection();
              
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
   String emp_sts_id="";
   String emplo_desc="";
   emp_sts_id=request.getParameter("employee_status_id");
   System.out.print("emp_sts_id--->"+emp_sts_id);
   if(emp_sts_id.equalsIgnoreCase("TRT"))
   {
   emp_sts_id="TRT','DPT";
   System.out.print("------>"+emp_sts_id);
   }
   else
   {
   System.out.print("------esle part");
   //emp_sts_id=emp_sts_id;
   }
   emplo_desc=request.getParameter("emplo_desc");
   
  %>
     
      <div align="center">
        <table width="80%">
          <tr>
            <td>
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH">
                  <th align="center" class="tdH"  colspan="7" >
                    Employee Status wise Details Report - <%=emplo_desc %></th>
                </tr>
                <!-- OFFICE DETAILS -->
                <% 
         session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
  // int empid=11263;
    int  oid=0;
    String oname="",oadd1="",oadd2="",ocity="",olid="",owid="",odist="";
    String olname=""; 
    String ownature="",deptid="",type="";
    String fulladd="";
    
    try
    {
           
            ps=connection.prepareStatement("select OFFICE_ID,DEPARTMENT_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                    deptid=results.getString("DEPARTMENT_ID");
                 
                 }
            results.close();
            ps.close();
            System.out.println("office id:"+oid);
            System.out.println("dept id:"+deptid);
            if( deptid==null || deptid.equalsIgnoreCase("TWAD"))
            {
                    type="(TWAD)";
                    //ps=connection.prepareStatement("select OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,OFFICE_LEVEL_ID,PRIMARY_WORK_ID from COM_MST_OFFICES where OFFICE_ID=?" );
                    ps=connection.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,d.DISTRICT_NAME  from COM_MST_OFFICES a left outer join com_mst_districts d on d.DISTRICT_CODE=a.DISTRICT_CODE  where a.OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OFFICE_NAME");
                            oadd1=results.getString("OFFICE_ADDRESS1");
                            if(oadd1==null)oadd1="";
                            oadd2=results.getString("OFFICE_ADDRESS2");
                            if(oadd2==null)oadd2="";
                            ocity=results.getString("CITY_TOWN_NAME");
                            if(ocity==null)ocity="";
                            olid=results.getString("OFFICE_LEVEL_ID");
                            if(olid==null)olid="";
                            owid=results.getString("PRIMARY_WORK_ID");
                            odist =results.getString("DISTRICT_NAME");
                            if(odist==null)odist="";
                            
                            if(oadd1.length()>0)
                                fulladd=oadd1;
                            if(oadd2.length()>0)
                                fulladd+="\n"+oadd2;
                            if(ocity.length()>0)
                                fulladd+="\n"+ocity;
                            if(odist.length()>0)
                                fulladd+="\n"+odist;
                            
                            System.out.println("Full address:"+fulladd);
                            
                            
                          }
                          //System.out.println("office name:"+oname);
                    results.close();
                    ps.close();
                  
                    ps=connection.prepareStatement("select OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID=?" );
                    ps.setString(1,olid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            olname=results.getString("OFFICE_LEVEL_NAME");
                            
                          }
                    results.close();
                    ps.close();
                    
                    ps=connection.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?" );
                    ps.setString(1,owid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            ownature=results.getString("WORK_NATURE_DESC");
                            
                          }
                    results.close();
                    ps.close();
            }
            else
            {
                System.out.println("other");
                System.out.println("off id::"+oid);
                    type="(OTHER="+deptid+")";
                    String otherdeptid="";
                    ps=connection.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OTHER_DEPT_OFFICE_NAME");
                            System.out.println("oname::"+oname);
                            oadd1=results.getString("ADDRESS1");
                            if(oadd1==null)oadd1="";
                            oadd2=results.getString("ADDRESS2");
                            if(oadd2==null)oadd2="";
                            ocity=results.getString("CITY_TOWN");
                             if(ocity==null)ocity="";
                            otherdeptid=results.getString("OTHER_DEPT_ID");
                           // owid=results.getString("PRIMARY_WORK_ID");
                            if(oadd1.length()>0)
                                fulladd=oadd1;
                            if(oadd2.length()>0)
                                fulladd+="\n"+oadd2;
                            if(ocity.length()>0)
                                fulladd+="\n"+ocity;
                          }
                    results.close();
                    ps.close();
                  
                    ps=connection.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?" );
                    ps.setString(1,otherdeptid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            olname=results.getString("OTHER_DEPT_NAME");
                            
                          }
                    results.close();
                    ps.close();
                    
                  
            }
            
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
               <tr class="tdH"  align="center">
               <td>Sl.No</td>
               <td>Emp.NO</td>
               <td>Emp Name</td>
               <td>Designation</td>
               <td>Office</td>
               <td>Date Effective from</td>
               </tr>
               <%
						         String sql="SELECT mst.employee_id AS employee_id, " +
"  empname, " +
"  DESIGNATION, " +
"  DECODE(OFFICE_NAME,NULL, ' ',OFFICE_NAME)                       AS OFFICE_NAME, " +
"  DECODE(OTHER_DEPT_OFFICE_NAME,NULL, ' ',OTHER_DEPT_OFFICE_NAME) AS OTHER_DEPT_OFFICE_NAME, " +
"  CASE " +
"    WHEN OFFICE_NAME           IS NULL " +
"    AND OTHER_DEPT_OFFICE_NAME IS NULL " +
"    AND EMPLOYEE_STATUS_ID     !='TRT' " +
"    AND EMPLOYEE_STATUS_ID     !='DPT' " +
"    THEN ' ' " +
"    WHEN office_name           IS NULL " +
"    AND OTHER_DEPT_OFFICE_NAME IS NOT NULL " +
"    THEN OTHER_DEPT_OFFICE_NAME " +
"    WHEN office_name IS NOT NULL " +
"    THEN OFFICE_NAME " +
"    WHEN EMPLOYEE_STATUS_ID ='TRT' " +
"    THEN 'TRANSIT' " +
"    WHEN EMPLOYEE_STATUS_ID ='DPT' " +
"    THEN 'TRANSIT' " +
"  END                                                         AS Office_namess, " +
"  DECODE(OFFICE_NAME,NULL,OTHER_DEPT_OFFICE_NAME,OFFICE_NAME) AS OFFICE_NAMEs ,to_char(DATE_EFFECTIVE_FROM,'dd/mm/yyyy') as DATE_EFFECTIVE_FROM " +
"FROM " +
"  (SELECT employee_id, " +
"    OFFICE_ID, " +
"    DEPARTMENT_ID, " +
"    DESIGNATION_ID ,DATE_EFFECTIVE_FROM, " +
"    EMPLOYEE_STATUS_ID " +
"  FROM HRM_EMP_CURRENT_POSTING " +
"  WHERE EMPLOYEE_STATUS_ID IN('"+emp_sts_id+"') " +
"  AND employee_id          IN " +
"    (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"    ) " +
"  )mst " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id, " +
"    EMPLOYEE_INITIAL " +
"    || '.' " +
"    || EMPLOYEE_NAME AS empname, " +
"    DESIGNATION " +
"  FROM VIEW_EMPLOYEE2 " +
"  )sub " +
"ON sub.employee_id=mst.employee_id " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID,OFFICE_NAME FROM com_mst_offices " +
"  )sub1 " +
"ON sub1.OFFICE_ID=mst.OFFICE_ID " +
"LEFT OUTER JOIN " +
"  (SELECT OTHER_DEPT_ID, " +
"    OTHER_DEPT_OFFICE_ID, " +
"    OTHER_DEPT_OFFICE_NAME " +
"  FROM HRM_MST_OTHER_DEPT_OFFICES " +
"  )sub2 " +
"ON sub2.OTHER_DEPT_ID        =mst.DEPARTMENT_ID " +
"AND sub2.OTHER_DEPT_OFFICE_ID=mst.OFFICE_ID " +
"ORDER BY to_date(DATE_EFFECTIVE_FROM,'dd/mm/yyyy') ";

					ps=connection.prepareStatement(sql);
					
					results=ps.executeQuery();
					int i=0;
					while(results.next())
					{
					i++;
					if((i % 2) ==0 )
					{
					out.println("<tr bgcolor='#fff' ><td>"+i+"</td><td>"+results.getInt("EMPLOYEE_ID")+"</td><td>"+results.getString("empname")+"</td><td>"+results.getString("DESIGNATION")+"</td><td>"+results.getString("Office_namess")+"</td><td>"+results.getString("DATE_EFFECTIVE_FROM")+"</td></tr>");
					}
					else
					{
					out.println("<tr bgcolor='#b6eaff' ><td>"+i+"</td><td>"+results.getInt("EMPLOYEE_ID")+"</td><td>"+results.getString("empname")+"</td><td>"+results.getString("DESIGNATION")+"</td><td>"+results.getString("Office_namess")+"</td><td>"+results.getString("DATE_EFFECTIVE_FROM")+"</td></tr>");
					}
					}
					
                %>
      </table>
      <tr class="tdH" align="center">
      <td>
      <input type="button" name="Back" id="Back" value="Back" onclick="Redirect()" >
      <input type="button" name="exit" id="exit" value="Exit" onclick="self.close()" >
      </td>
      </tr>
    </form></body>
</html>