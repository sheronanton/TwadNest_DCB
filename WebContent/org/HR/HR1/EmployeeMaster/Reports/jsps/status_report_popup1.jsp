<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ResourceBundle,java.io.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Designation Wise Employee Summary Report</title>
    
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
<br>
<table border='2' width="100%">
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> <%=request.getParameter("designation") %> Employees List</strong></td>
                  
                </tr><tr><td>
<!-- <div id="getemplist"></div></td>
 -->
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

       //     ConnectionString =
        //            strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
        //            ":" + strsid.trim();
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
        }try {
//                String workstat = request.getParameter("workstat");
                String designation = request.getParameter("desid");
                String officedetail = request.getParameter("officeDetail");
                String offlvl="";
                int hlevel = Integer.parseInt(request.getParameter("hlevel"));
             /*   System.out.println("enter" +
                                   workstat.substring(0, workstat.length() -
                                                      1) +
                                   "5555555555555555555&&&&&&&&" + hlevel);*/
                String sql = "";
              //  System.out.println("workstat---33333" + workstat);
                
                
                
                if (officedetail.equalsIgnoreCase("all")) {
                  //  System.out.println("---------------------------all part");
                    sql =
 "SELECT EMPLOYEE_ID, " +
"  empname, " +
"  designation, " +
"  EMPLOYEE_STATUS_DESC, " +
"  TO_CHAR(DATE_EFFECTIVE_FROM,'dd/mm/yyyy')                   AS DATE_EFFECTIVE_FROM, " +
"  DECODE(OFFICE_NAME,NULL,decode(OTHER_DEPT_OFFICE_NAME,null,' ',OTHER_DEPT_OFFICE_NAME),OFFICE_NAME) AS OFFICE_NAME " +
" FROM " +
"  (SELECT EMPLOYEE_ID, " +
"    empname, " +
"    designation, " +
"    EMPLOYEE_STATUS_DESC, " +
"    DATE_EFFECTIVE_FROM, " +
"    OFFICE_ID, " +
"    DEPARTMENT_ID " +
"  FROM " +
"    (SELECT EMPLOYEE_ID, " +
"      empname, " +
"      designation, " +
"      EMPLOYEE_STATUS_ID, " +
"      DATE_EFFECTIVE_FROM, " +
"      OFFICE_ID, " +
"      DEPARTMENT_ID " +
"    FROM " +
"      (SELECT designation, " +
"        EMPLOYEE_ID AS id1, " +
"        EMPLOYEE_STATUS_ID, " +
"        DATE_EFFECTIVE_FROM, " +
"        DEPARTMENT_ID, " +
"        OFFICE_ID " +
"      FROM " +
"        (SELECT DESIGNATION_ID, " +
"          EMPLOYEE_ID, " +
"          EMPLOYEE_STATUS_ID, " +
"          DATE_EFFECTIVE_FROM, " +
"          OFFICE_ID, " +
"          DEPARTMENT_ID " +
"        FROM HRM_EMP_CURRENT_POSTING " +
"        WHERE EMPLOYEE_STATUS_ID NOT IN('SAN', 'VRS', 'DTH', 'DIS', 'MEV', 'CMR', 'RES') " +
"        AND DESIGNATION_ID            = " +  designation +

"        ) a " +
"      LEFT OUTER JOIN " +
"        (SELECT DESIGNATION_ID, designation FROM hrm_mst_designations " +
"        ) b " +
"      ON a.DESIGNATION_ID=b.DESIGNATION_ID " +
"      ) c " +
"    LEFT OUTER JOIN " +
"      (SELECT EMPLOYEE_ID, " +
"        EMPLOYEE_NAME " +
"        ||'.' " +
"        || EMPLOYEE_INITIAL AS empname " +
"      FROM HRM_MST_EMPLOYEES " +
"      ) d " +
"    ON c.id1=d.EMPLOYEE_ID " +
"    ) e " +
"  LEFT OUTER JOIN " +
"    (SELECT EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC FROM HRM_MST_EMPLOYEE_STATUS " +
"    )f " +
"  ON e.EMPLOYEE_STATUS_ID=f.EMPLOYEE_STATUS_ID " +
"  ) g " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID AS id2,OFFICE_NAME FROM COM_MST_OFFICES " +
"  ) h " +
"ON G.OFFICE_ID=H.ID2 " +
"LEFT OUTER JOIN " +
"  (SELECT OTHER_DEPT_ID, " +
"    OTHER_DEPT_OFFICE_ID, " +
"    OTHER_DEPT_OFFICE_NAME " +
"  FROM HRM_MST_OTHER_DEPT_OFFICES " +
"  )W " +
"ON w.OTHER_DEPT_ID        =g.DEPARTMENT_ID " +
"AND w.OTHER_DEPT_OFFICE_ID=G.OFFICE_ID " +
"ORDER BY empname";
System.out.println("sql is----------------->"+sql);
                } else {
                  //  officedetail = officedetail.replace(",", "','");
                    officedetail = "'" + officedetail.replace(",", "','") + "'";
                    sql="Select Office_Level_Id    FROM COM_MST_ALL_OFFICES_VIEW  where office_id in ("+officedetail+")";
                    
                 //   System.out.println("sql------------"+sql);
                    PreparedStatement ps11=connection.prepareStatement(sql);
                  //  ps11.setString(1,officedetail);
                   ResultSet result = ps11.executeQuery(sql);
                   
                    if(result.next())
                    {
                    	offlvl=result.getString("Office_Level_Id");
                    	
                    	
                    	}
                    
                    
                    String var="";
                    
                    
                    
               //     System.out.println("offlvl------------"+offlvl);
                     
                     
                 //   System.out.println("emp_name------------"+officedetail);
                  
                    
                    if(offlvl.equals("HO"))
                    {
                    	var=" ";
                    }
                     if(offlvl.equals("RN"))
                     {
                     	var="where REGION_OFFICE_ID in ("+officedetail+")";
                     }
                     else if(offlvl.equals("CL"))
                     {
                     	var="where CIRCLE_OFFICE_ID in ("+officedetail+")";
                     }
                     else if(offlvl.equals("DN"))
                     {
                     	var="where DIVISION_OFFICE_ID in ("+officedetail+")";
                     }
                     else if(offlvl.equals("SD"))
                     {
                     	var="where SUBDIVISION_OFFICE_ID  in ("+officedetail+")";
                     }
                     else if(offlvl.equals("AW"))
                     {
                     	var="where AUDITWING_OFFICE_ID in ("+officedetail+")";
                     }
                     else if(offlvl.equals("LB"))
                     {
                     	var="where LAB_OFFICE_ID in ("+officedetail+")";
                     }
                 
                    if (hlevel == 1) {
                        sql =
 "SELECT EMPLOYEE_ID, " +
"  empname, " +
"  designation, " +
"  EMPLOYEE_STATUS_DESC, " +
"  TO_CHAR(DATE_EFFECTIVE_FROM,'dd/mm/yyyy')                   AS DATE_EFFECTIVE_FROM, " +
"  DECODE(OFFICE_NAME,NULL,decode(OTHER_DEPT_OFFICE_NAME,null,' ',OTHER_DEPT_OFFICE_NAME),OFFICE_NAME) AS OFFICE_NAME " +
" FROM " +
"  (SELECT EMPLOYEE_ID, " +
"    empname, " +
"    designation, " +
"    EMPLOYEE_STATUS_DESC, " +
"    DATE_EFFECTIVE_FROM, " +
"    OFFICE_ID, " +
"    DEPARTMENT_ID " +
"  FROM " +
"    (SELECT EMPLOYEE_ID, " +
"      empname, " +
"      designation, " +
"      EMPLOYEE_STATUS_ID, " +
"      DATE_EFFECTIVE_FROM, " +
"      OFFICE_ID, " +
"      DEPARTMENT_ID " +
"    FROM " +
"      (SELECT designation, " +
"        EMPLOYEE_ID AS id1, " +
"        EMPLOYEE_STATUS_ID, " +
"        DATE_EFFECTIVE_FROM, " +
"        DEPARTMENT_ID, " +
"        OFFICE_ID " +
"      FROM " +
"        (SELECT DESIGNATION_ID, " +
"          EMPLOYEE_ID, " +
"          EMPLOYEE_STATUS_ID, " +
"          DATE_EFFECTIVE_FROM, " +
"          OFFICE_ID, " +
"          DEPARTMENT_ID " +
"        FROM HRM_EMP_CURRENT_POSTING " +
"        WHERE EMPLOYEE_STATUS_ID NOT IN('SAN', 'VRS', 'DTH', 'DIS', 'MEV', 'CMR', 'RES') " +
"        AND DESIGNATION_ID            = " +   designation +
" and OFFICE_ID in (select OFFICE_ID from COM_MST_ALL_OFFICES_VIEW "+var+")" +

"        ) a " +
"      LEFT OUTER JOIN " +
"        (SELECT DESIGNATION_ID, designation FROM hrm_mst_designations " +
"        ) b " +
"      ON a.DESIGNATION_ID=b.DESIGNATION_ID " +
"      ) c " +
"    LEFT OUTER JOIN " +
"      (SELECT EMPLOYEE_ID, " +
"        EMPLOYEE_NAME " +
"        ||'.' " +
"        || EMPLOYEE_INITIAL AS empname " +
"      FROM HRM_MST_EMPLOYEES " +
"      ) d " +
"    ON c.id1=d.EMPLOYEE_ID " +
"    ) e " +
"  LEFT OUTER JOIN " +
"    (SELECT EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC FROM HRM_MST_EMPLOYEE_STATUS " +
"    )f " +
"  ON e.EMPLOYEE_STATUS_ID=f.EMPLOYEE_STATUS_ID " +
"  ) g " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID AS id2,OFFICE_NAME FROM COM_MST_OFFICES " +
"  ) h " +
"ON G.OFFICE_ID=H.ID2 " +
"LEFT OUTER JOIN " +
"  (SELECT OTHER_DEPT_ID, " +
"    OTHER_DEPT_OFFICE_ID, " +
"    OTHER_DEPT_OFFICE_NAME " +
"  FROM HRM_MST_OTHER_DEPT_OFFICES " +
"  )W " +
"ON w.OTHER_DEPT_ID        =g.DEPARTMENT_ID " +
"AND w.OTHER_DEPT_OFFICE_ID=G.OFFICE_ID " +
"ORDER BY empname";
                    } else {


                        sql =
 "SELECT EMPLOYEE_ID, " +
"  empname, " +
"  designation, " +
"  EMPLOYEE_STATUS_DESC, " +
"  TO_CHAR(DATE_EFFECTIVE_FROM,'dd/mm/yyyy')                   AS DATE_EFFECTIVE_FROM, " +
"  DECODE(OFFICE_NAME,NULL,OTHER_DEPT_OFFICE_NAME,OFFICE_NAME) AS OFFICE_NAME " +
" FROM " +
"  (SELECT EMPLOYEE_ID, " +
"    empname, " +
"    designation, " +
"    EMPLOYEE_STATUS_DESC, " +
"    DATE_EFFECTIVE_FROM, " +
"    OFFICE_ID, " +
"    DEPARTMENT_ID " +
"  FROM " +
"    (SELECT EMPLOYEE_ID, " +
"      empname, " +
"      designation, " +
"      EMPLOYEE_STATUS_ID, " +
"      DATE_EFFECTIVE_FROM, " +
"      OFFICE_ID, " +
"      DEPARTMENT_ID " +
"    FROM " +
"      (SELECT designation, " +
"        EMPLOYEE_ID AS id1, " +
"        EMPLOYEE_STATUS_ID, " +
"        DATE_EFFECTIVE_FROM, " +
"        DEPARTMENT_ID, " +
"        OFFICE_ID " +
"      FROM " +
"        (SELECT DESIGNATION_ID, " +
"          EMPLOYEE_ID, " +
"          EMPLOYEE_STATUS_ID, " +
"          DATE_EFFECTIVE_FROM, " +
"          OFFICE_ID, " +
"          DEPARTMENT_ID " +
"        FROM HRM_EMP_CURRENT_POSTING " +
"        WHERE EMPLOYEE_STATUS_ID NOT IN('SAN', 'VRS', 'DTH', 'DIS', 'MEV', 'CMR', 'RES') " +
"        AND DESIGNATION_ID            = " +   designation +
" and OFFICE_ID in (" + officedetail +
   ")) a left outer join"+
"        (SELECT DESIGNATION_ID, designation FROM hrm_mst_designations " +
"        ) b " +
"      ON a.DESIGNATION_ID=b.DESIGNATION_ID " +
"      ) c " +
"    LEFT OUTER JOIN " +
"      (SELECT EMPLOYEE_ID, " +
"        EMPLOYEE_NAME " +
"        ||'.' " +
"        || EMPLOYEE_INITIAL AS empname " +
"      FROM HRM_MST_EMPLOYEES " +
"      ) d " +
"    ON c.id1=d.EMPLOYEE_ID " +
"    ) e " +
"  LEFT OUTER JOIN " +
"    (SELECT EMPLOYEE_STATUS_ID,EMPLOYEE_STATUS_DESC FROM HRM_MST_EMPLOYEE_STATUS " +
"    )f " +
"  ON e.EMPLOYEE_STATUS_ID=f.EMPLOYEE_STATUS_ID " +
"  ) g " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID AS id2,OFFICE_NAME FROM COM_MST_OFFICES " +
"  ) h " +
"ON G.OFFICE_ID=H.ID2 " +
"LEFT OUTER JOIN " +
"  (SELECT OTHER_DEPT_ID, " +
"    OTHER_DEPT_OFFICE_ID, " +
"    OTHER_DEPT_OFFICE_NAME " +
"  FROM HRM_MST_OTHER_DEPT_OFFICES " +
"  )W " +
"ON w.OTHER_DEPT_ID        =g.DEPARTMENT_ID " +
"AND w.OTHER_DEPT_OFFICE_ID=G.OFFICE_ID " +
"ORDER BY empname";
                    }
                }
                System.out.println(sql);
                
                
                Statement st = connection.createStatement();
               ResultSet result = st.executeQuery(sql); %>
                
 </tr></table>
 <table cellpadding=0 cellspacing=0 border=1  width='100%'>
 <%  boolean bool = false;
 int i=0;
                while (result.next()) {
                    i++;
                    System.out.println("result.getString('DATE_EFFECTIVE_FROM')"+result.getString("DATE_EFFECTIVE_FROM"));
                     %>
                    <tr><td rowspan='6'><img  src=ShowImageProfile.jsp?empid=<%=
result.getString("EMPLOYEE_ID")%> alt='image' height='115' width='100' class='magnify' ></img></td>
<td >SNO</td><td> <%=i %> </td></tr><tr><td>Employee ID</td><td >
 <%=result.getString("EMPLOYEE_ID")  %>  <a  style='color:green' href='viewServiceParticular.jsp?txtEmpId=<%=result.getString("EMPLOYEE_ID")%> '> View Service Details </a></td></tr>
 <tr><td >Employee Name</td><td ><%=result.getString("empname") %></td></tr>
 <tr><td>Office Name</td><td > <%=result.getString("OFFICE_NAME")%> </td></tr>
 <tr><td>Joining Date</td><td ><%=result.getString("DATE_EFFECTIVE_FROM") %></td></tr>
 <tr><td>Status</td><td ><%=result.getString("EMPLOYEE_STATUS_DESC")%></td></tr>
 
 <%}
 
 }
   catch (Exception e) {
                e.printStackTrace();
                System.out.println(" error " + e);
                

            } %>
            </table></center>

</table></td><td><center><input type="button" onclick="self.close();" value="close"></center></td></tr></table></body>
</html>