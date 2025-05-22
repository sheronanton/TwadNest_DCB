<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ResourceBundle,java.io.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Cadre Wise -Panel</title>
    
<script type="text/javascript">


function popup(pUrl, pName, pWidth, pHeight, pScroll)
{

	LeftPosition = (screen.width) ? (screen.width-pWidth)   / 2 : 0;
	TopPosition  = (screen.height)? (screen.height-pHeight) / 2 : 0;
	settings = 'height='+pHeight+', width='+pWidth+', top='+TopPosition+', left='+LeftPosition+', scrollbars='+pScroll+', resizable';
	win = window.open(pUrl, pName, settings)
}
</script>
  <script src="<%=request.getContextPath()%>/jquerycalendar/jquery-1.6.2.js"></script>
  <script src="<%=request.getContextPath()%>/script/Proceeding_jquery-ui.min.js"></script>
 
 
  <script src="<%=request.getContextPath()%>/script/Proceeding_jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/jquery.magnifier.js"></script>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      
      <script type="text/javascript">
      function redirect()
      {
      window.location.assign("Cadrewise_Panel.jsp")
      }
      
      </script>
      
      <script>
$(document).ready(function(){

	   $('#imgSmile').width(200);
	   $('#imgSmile').mouseover(function()
	   {
	      $(this).css("cursor","pointer");
	   });
	   $("#imgSmile").toggle(function()
	     {$(this).animate({width: "500px"}, 'slow');},
	     function()
	     {$(this).animate({width: "200px"}, 'slow');
	   });
	});
	</script>
    <script type="text/javascript"
            src="../scripts/Status_officewise_Summary_ReportJS.js"></script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
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
<body onload="viewEmpbyDesg1('<%=request.getQueryString() %>')">
<%
 String title="";
 %>
<br>
<table border='2' width="100%">
          <tr>
            <td colspan="12" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             <%
  Connection connection = null;
        PreparedStatement ps1 = null;
        Statement s = null;
        ResultSet result1 = null;
        try {

            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

         //   ConnectionString =
          //          strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
           //         ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            PreparedStatement ps = null;
            ResultSet result=null;
           
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
         //   System.out.println(connectMsg);
        }
        try
        {
        String alldes=request.getParameter("alldes");
       PreparedStatement ps=connection.prepareStatement("Select cadre_name From Hrm_Mst_Cadre where cadre_id in("+alldes+")");
      ResultSet rs=ps.executeQuery();
       title="";
      while(rs.next())
      {
          title=title+rs.getString("cadre_name")+",";
      }
      title=title.substring(0,title.length()-1);
       
        
        }
        catch(Exception e)
        {
        System.out.print(e);
        }
        %>
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> <%=title %>- Panel Report </strong></td>
                  
                </tr><tr><td>
<!-- <div id="getemplist"></div></td>
 -->
 <%
        
                   String   officedetail="";  
                  
                   String eedesig="";
        try
        {
            officedetail=request.getParameter("alldes");
                
                 
                 System.out.print("title---"+title);
                // eedesig=request.getParameter("eedesig");
        }
        catch(Exception es)
        {
        System.out.println("error is-->"+es);
        }
        
        
        try {
//                String workstat = request.getParameter("workstat");
               // String designation = request.getParameter("desid");
               // String officedetail = request.getParameter("officeDetail");
                String offlvl="";
              //  int hlevel = Integer.parseInt(request.getParameter("hlevel"));
             /*   System.out.println("enter" +
                                   workstat.substring(0, workstat.length() -
                                                      1) +
                                   "5555555555555555555&&&&&&&&" + hlevel);*/
                String sql = "";
                
              //  System.out.println("workstat---33333" + workstat);
                
                
                 
                 
                 System.out.println("all--->"+officedetail);
                
                
           
                
               
                  //  System.out.println("---------------------------all part");
                    sql =
 

"SELECT PANEL_NUMBER, " +
"  decode(SENIORITY_NO,null,' ',SENIORITY_NO) as SENIORITY_NO,  " +
"  EMPLOYEE_ID, " +
"  TO_CHAR(DATE_FROM,'dd-mm-yy')            AS DATE_FROM, " +
"  TO_CHAR(joining_date,'dd-mm-yy')         AS joining_date , " +
"  TO_CHAR(current_posting_date,'dd-mm-yy') AS current_posting_date, " +
"  TO_CHAR(DOR,'dd-mm-yy')                  AS DATE_OF_RETIRE, " +
"  Employee_Name ,employee_status_id,decode(REMARKS,null,' ' ,REMARKS) as REMARKS, " +
"  EMPLOYEE_STATUS_DESC " +
"FROM " +
"  (SELECT PANEL_ID, " +
"    PANEL_CADRE " +
"  FROM Hrm_Panel_Master_New " +
"  WHERE PANEL_CADRE ="+officedetail+" " +

"  )s1 " +
"JOIN " +
"  (SELECT Panel_Id p_id, " +
"    PANEL_NUMBER ,REMARKS, " +
"    SENIORITY_NO, " +
"    SENIORITY_SUB_NUMBER, " +
"    POSTING_ISSUED, " +
"    POSTING_ORDER_DATE, " +
"    Employee_Id " +
"  FROM Hrm_Panel_Details_New " +
"  WHERE POSTING_ISSUED ='No'  or POSTING_ISSUED='N' " +
"  )P1 " +
"ON S1.Panel_Id=P1.P_Id " +
"LEFT OUTER JOIN " +
"  (SELECT A.EMPLOYEE_ID AS Eid, " +
"    A.Designation_Id, " +
"    DECODE(B1.DATE_FROM,NULL,A.DATE_EFFECTIVE_FROM,B1.DATE_FROM) AS DATE_FROM " +
"  FROM " +
"    (SELECT EMPLOYEE_ID, " +
"      DESIGNATION_ID, " +
"      DATE_EFFECTIVE_FROM " +
"    FROM HRM_EMP_CURRENT_POSTING " +
"    WHERE Employee_Id IN " +
"      (SELECT employee_id FROM Hrm_Panel_Details_New WHERE POSTING_ISSUED ='No' or POSTING_ISSUED='N' " +
"      ) " +
"    )A " +
"  LEFT OUTER JOIN ( " +
"    (SELECT EMPLOYEE_ID, " +
"      Designation_Id, " +
"      MIN(DATE_FROM) AS DATE_FROM " +
"    FROM HRM_EMP_SERVICE_DATA " +
"    WHERE Designation_Id IN " +
"      (SELECT Designation_Id " +
"      FROM Hrm_Emp_Current_Posting " +
"      WHERE Employee_Id IN " +
"        (SELECT Employee_Id FROM Hrm_Panel_Details_New WHERE Posting_Issued ='No' or POSTING_ISSUED='N' " +
"        ) " +
"      ) " +
"    AND Employee_Id IN " +
"      (SELECT Employee_Id FROM Hrm_Panel_Details_New WHERE Posting_Issued ='No' or POSTING_ISSUED='N' " +
"      ) " +
"    GROUP BY EMPLOYEE_ID, " +
"      Designation_Id " +
"    ) )B1 " +
"  ON A.EMPLOYEE_ID    =B1.EMPLOYEE_ID " +
"  AND A.DESIGNATION_ID=B1.DESIGNATION_ID " +
"  )B1 ON B1.Eid       =P1.Employee_Id " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id   AS emp_id2, " +
"    date_effective_from AS current_posting_date, " +
"    office_id, " +
"    employee_status_id, " +
"    department_id " +
"  FROM hrm_emp_current_posting " +
"  ) C " +
"ON P1.Employee_Id=C.Emp_Id2 " +
"LEFT OUTER JOIN " +
"  (SELECT e11.Employee_Id AS Emp_Id3, " +
"    e11.date_of_birth     AS DOB , " +
"    e11.employee_prefix " +
"    || '.' " +
"    || e11.Employee_Name " +
"    || '.' " +
"    || e11.Employee_Initial AS Employee_Name, " +
"    e11.Qualifications      AS Qualification " +
"  FROM Hrm_Mst_Employees E11 " +
"  ) W " +
"ON P1.Employee_Id=W.Emp_Id3 " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id AS emp_id1, " +
"    MIN(date_from)    AS joining_date, " +
"    designation_id, " +
"    service_list_slno " +
"  FROM hrm_emp_service_data " +
"  WHERE service_list_slno=1 " +
"  GROUP BY employee_id, " +
"    designation_id, " +
"    Service_List_Slno " +
"  ) B12 " +
"ON p1.employee_id=b12.emp_id1 " +
"LEFT OUTER JOIN " +
"  ( SELECT Employee_Id Empid,Retiredate AS Dor FROM Allretirementview " +
"  )X3 " +
"ON P1.EMPLOYEE_ID =X3.EMPID " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC FROM HRM_MST_EMPLOYEE_STATUS " +
"  )SUB " +
"ON c.EMPLOYEE_STATUS_ID=sub.EMPLOYEE_STATUS_ID " +
"ORDER BY P1.PANEL_NUMBER";
                
                System.out.println(sql);
                
                System.out.println("sql is 123==>"+sql);
                Statement st = connection.createStatement();
               ResultSet result = st.executeQuery(sql);
               System.out.println("suceess");
                %>
                
 </tr></table>
 <table cellpadding=0 cellspacing=0 border=1  width='100%'>
 <tr ><td class="tdH">slno</td><td class="tdH">photo</td><td class="tdH">Employee id</td>
 <td class="tdH">Panel Number</td><td class="tdH">Seniority No</td>
 <td class="tdH">Employee Name</td><td class="tdH">Designation Date</td>
   <td class="tdH">Joining Date</td><td class="tdH">Current Posting Date</td>
   <td class="tdH">Date of Retirement</td>
  <td class="tdH"> service Details</td></tr>
 <%  boolean bool = false;
 int i=0;
 int cnt=0;
 //if(result.next())
 System.out.println("suceess");
                while (result.next()) {
                if((result.getString("employee_status_id").equals("WKG"))||(result.getString("employee_status_id").equals("TRT")|| (result.getString("employee_status_id").equals("DPN")) || (result.getString("employee_status_id").equals("SUS") && result.getString("REMARKS").equalsIgnoreCase(" "))||(result.getString("employee_status_id").equals("DPT"))))
                {
                    i++;
                     %>
                   
               			<tr><td> <%=i %> </td>
                    		<td  ><img  src=ShowImageProfile.jsp?empid=<%=
									result.getString("EMPLOYEE_ID")%> alt='image' height='40' width='40' class='magnify' ></img></td>

	 						<td><%=result.getString("EMPLOYEE_ID")  %>  </td>
 							<td > <%=result.getString("PANEL_NUMBER")%> </td>
 							<td ><%=result.getString("SENIORITY_NO") %></td>

							 <td ><%=result.getString("Employee_Name") %></td>
							<td > <%=result.getString("DATE_FROM")%> </td>
							<td ><%=result.getString("joining_date") %></td>
							<td ><%=result.getString("current_posting_date")%></td>
							 
							<td > <%=result.getString("date_of_retire")%> </td>
							 <td><a  style='color:green' href='viewServiceParticular.jsp?txtEmpId=<%=result.getString("EMPLOYEE_ID")%> '> View Service Details </a></td></tr>
 <%}
 else
 {
 System.out.println("inside else part");
 cnt=1;
 
 }
 
 }
  
 System.out.println("cnt------>"+cnt);
  if(cnt==1)
 {
 
 int slno=0;
          sql =
 

"SELECT PANEL_NUMBER, " +
"  decode(SENIORITY_NO,null,' ',SENIORITY_NO) as SENIORITY_NO,  " +
"  EMPLOYEE_ID, " +
"  TO_CHAR(DATE_FROM,'dd-mm-yy')            AS DATE_FROM, " +
"  TO_CHAR(joining_date,'dd-mm-yy')         AS joining_date , " +
"  TO_CHAR(current_posting_date,'dd-mm-yy') AS current_posting_date, " +
"  TO_CHAR(DOR,'dd-mm-yy')                  AS DATE_OF_RETIRE, " +
"  Employee_Name ,employee_status_id,decode(REMARKS,null,' ' ,REMARKS) as REMARKS, " +
"  EMPLOYEE_STATUS_DESC " +
"FROM " +
"  (SELECT PANEL_ID, " +
"    PANEL_CADRE " +
"  FROM Hrm_Panel_Master_New " +
"  WHERE PANEL_CADRE ="+officedetail+" " +
"  AND PANEL_DATE   = " +
"    (SELECT MAX(PANEL_DATE) " +
"    FROM Hrm_Panel_Master_New " +
"    WHERE PANEL_CADRE = "+officedetail+"  " +
"    GROUP BY PANEL_CADRE " +
"    ) " +
"  )s1 " +
" JOIN " +
"  (SELECT Panel_Id p_id, " +
"    PANEL_NUMBER , " +
"    SENIORITY_NO, " +
"    SENIORITY_SUB_NUMBER, " +
"    POSTING_ISSUED, " +
"    POSTING_ORDER_DATE, REMARKS," +
"    Employee_Id " +
"  FROM Hrm_Panel_Details_New " +
"  WHERE POSTING_ISSUED ='No' or POSTING_ISSUED='N' " +
"  )P1 " +
"ON S1.Panel_Id=P1.P_Id " +
"LEFT OUTER JOIN " +
"  (SELECT A.EMPLOYEE_ID AS Eid, " +
"    A.Designation_Id, " +
"    DECODE(B1.DATE_FROM,NULL,A.DATE_EFFECTIVE_FROM,B1.DATE_FROM) AS DATE_FROM " +
"  FROM " +
"    (SELECT EMPLOYEE_ID, " +
"      DESIGNATION_ID, " +
"      DATE_EFFECTIVE_FROM " +
"    FROM HRM_EMP_CURRENT_POSTING " +
"    WHERE Employee_Id IN " +
"      (SELECT employee_id FROM Hrm_Panel_Details_New WHERE POSTING_ISSUED ='No' or POSTING_ISSUED='N' " +
"      ) " +
"    )A " +
"  LEFT OUTER JOIN ( " +
"    (SELECT EMPLOYEE_ID, " +
"      Designation_Id, " +
"      MIN(DATE_FROM) AS DATE_FROM " +
"    FROM HRM_EMP_SERVICE_DATA " +
"    WHERE Designation_Id IN " +
"      (SELECT Designation_Id " +
"      FROM Hrm_Emp_Current_Posting " +
"      WHERE Employee_Id IN " +
"        (SELECT Employee_Id FROM Hrm_Panel_Details_New WHERE Posting_Issued ='No' or POSTING_ISSUED='N' " +
"        ) " +
"      ) " +
"    AND Employee_Id IN " +
"      (SELECT Employee_Id FROM Hrm_Panel_Details_New WHERE Posting_Issued ='No' or POSTING_ISSUED='N' " +
"      ) " +
"    GROUP BY EMPLOYEE_ID, " +
"      Designation_Id " +
"    ) )B1 " +
"  ON A.EMPLOYEE_ID    =B1.EMPLOYEE_ID " +
"  AND A.DESIGNATION_ID=B1.DESIGNATION_ID " +
"  )B1 ON B1.Eid       =P1.Employee_Id " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id   AS emp_id2, " +
"    date_effective_from AS current_posting_date, " +
"    office_id, " +
"    employee_status_id, " +
"    department_id " +
"  FROM hrm_emp_current_posting " +
"  ) C " +
"ON P1.Employee_Id=C.Emp_Id2 " +
"LEFT OUTER JOIN " +
"  (SELECT e11.Employee_Id AS Emp_Id3, " +
"    e11.date_of_birth     AS DOB , " +
"    e11.employee_prefix " +
"    || '.' " +
"    || e11.Employee_Name " +
"    || '.' " +
"    || e11.Employee_Initial AS Employee_Name, " +
"    e11.Qualifications      AS Qualification " +
"  FROM Hrm_Mst_Employees E11 " +
"  ) W " +
"ON P1.Employee_Id=W.Emp_Id3 " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id AS emp_id1, " +
"    MIN(date_from)    AS joining_date, " +
"    designation_id, " +
"    service_list_slno " +
"  FROM hrm_emp_service_data " +
"  WHERE service_list_slno=1 " +
"  GROUP BY employee_id, " +
"    designation_id, " +
"    Service_List_Slno " +
"  ) B12 " +
"ON p1.employee_id=b12.emp_id1 " +
"LEFT OUTER JOIN " +
"  ( SELECT Employee_Id Empid,Retiredate AS Dor FROM Allretirementview " +
"  )X3 " +
"ON P1.EMPLOYEE_ID =X3.EMPID " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC FROM HRM_MST_EMPLOYEE_STATUS " +
"  )SUB " +
"ON c.EMPLOYEE_STATUS_ID=sub.EMPLOYEE_STATUS_ID " +
"ORDER BY P1.PANEL_NUMBER";
System.out.println("ss--->"+sql);
  st = connection.createStatement();
   result = st.executeQuery(sql);
   out.println("<tr height='40px'></tr>");
    out.println("<tr height='40px'></tr>");
   out.println("<table border='1' width='100%' colspan='12' ><tr ><td class='tdH' align='center'  ><strong>"+title+" Retired Employees Report</strong></td></tr></table>");%>
  <table cellpadding=0 cellspacing=0 border=1  width='100%'>
 <tr ><td class="tdH">slno</td><td class="tdH">photo</td><td class="tdH">Employee id</td>
 <td class="tdH">Panel Number</td><td class="tdH">Seniority No</td>
 <td class="tdH">Employee Name</td><td class="tdH">Designation Date</td>
   <td class="tdH">Joining Date</td><td class="tdH">Current Posting Date</td>
   <td class="tdH">Date of Retirement</td>
   <td class="tdH">Status</td>
  <td class="tdH"> service Details</td></tr>
 <%  while(result.next())
   {
   i++;
    if(result.getString("employee_status_id").equalsIgnoreCase("SAN") || (result.getString("employee_status_id").equalsIgnoreCase("VRS"))|| (result.getString("employee_status_id").equalsIgnoreCase("DIS"))|| (result.getString("employee_status_id").equalsIgnoreCase("DTH"))|| (result.getString("employee_status_id").equalsIgnoreCase("RES"))|| (result.getString("employee_status_id").equalsIgnoreCase("CMR"))|| (result.getString("employee_status_id").equalsIgnoreCase("MEV")|| (result.getString("employee_status_id").equals("SUS") && !result.getString("REMARKS").equalsIgnoreCase(" ") )) )
                {
                slno=slno+1;%>
               
  <tr><td> <%=slno %> </td>
                    		<td  ><img  src=ShowImageProfile.jsp?empid=<%=
									result.getString("EMPLOYEE_ID")%> alt='image' height='40' width='40' class='magnify' ></img></td>

	 						<td><%=result.getString("EMPLOYEE_ID")  %>  </td>
 							<td > <%=result.getString("PANEL_NUMBER")%> </td>
 							<td ><%=result.getString("SENIORITY_NO") %></td>

							 <td ><%=result.getString("Employee_Name") %></td>
							<td > <%=result.getString("DATE_FROM")%> </td>
							<td ><%=result.getString("joining_date") %></td>
							<td ><%=result.getString("current_posting_date")%></td>
							 
							<td > <%=result.getString("date_of_retire")%> </td>
								<td > <%=result.getString("EMPLOYEE_STATUS_DESC")%> </td>
							 <td><a  style='color:green' href='viewServiceParticular.jsp?txtEmpId=<%=result.getString("EMPLOYEE_ID")%> '> View Service Details </a></td></tr>
							 <%
   }
   }
 }
 if(i==0)
 {
 out.print("<td class='tdH' colspan='11' align='center'>No Panel data for these cadre</td>");
 }
 
 }
   catch (Exception e) {
                e.printStackTrace();
                System.out.println(" error " + e);
                

            } %>
            </table></center>

</table></td><td><center><input type="button" onclick="self.close();" value="close"><input type="button" name="back" id="back" value="BACK" onclick="redirect()" /></center>

</td></tr></table></body>
</html>