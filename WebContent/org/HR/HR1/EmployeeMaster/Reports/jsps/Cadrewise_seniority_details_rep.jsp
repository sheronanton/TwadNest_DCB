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
      window.location.assign("Cadre_wise_seniority_rep.jsp")
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
        String subqry="";
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
                  <td colspan='2' class="tdH" align="center"><strong> <%=title %>- Seniority  Report </strong></td>
                  
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
        if(officedetail.equalsIgnoreCase("5"))
        {
       subqry= "AND employee_id NOT IN " +
"(SELECT EMPLOYEE_ID " +
"FROM " +
"  (SELECT PANEL_ID,PANEL_CADRE FROM HRM_PANEL_MASTER_NEW WHERE PANEL_CADRE=4 " +
"  )mst " +
"JOIN " +
"  (SELECT PANEL_ID, " +
"    EMPLOYEE_ID " +
"  FROM HRM_PANEL_DETAILS_NEW " +
"  WHERE POSTING_ISSUED='Yes' " +
"  OR POSTING_ISSUED   ='Y' " +
"  )sub " +
"ON mst.PANEL_ID=sub.PANEL_ID " +
")";
        }
        else if(officedetail.equalsIgnoreCase("6"))
        {
        
       subqry= "AND employee_id NOT IN " +
"(SELECT EMPLOYEE_ID " +
"FROM " +
"  (SELECT PANEL_ID,PANEL_CADRE FROM HRM_PANEL_MASTER_NEW WHERE PANEL_CADRE=5 " +
"  )mst " +
"JOIN " +
"  (SELECT PANEL_ID, " +
"    EMPLOYEE_ID " +
"  FROM HRM_PANEL_DETAILS_NEW " +
"  WHERE POSTING_ISSUED='Yes' " +
"  OR POSTING_ISSUED   ='Y' " +
"  )sub " +
"ON mst.PANEL_ID=sub.PANEL_ID " +
")";
        
        }
         else if(officedetail.equalsIgnoreCase("7"))
        {
        
       subqry= "AND employee_id NOT IN " +
"(SELECT EMPLOYEE_ID " +
"FROM " +
"  (SELECT PANEL_ID,PANEL_CADRE FROM HRM_PANEL_MASTER_NEW WHERE PANEL_CADRE=6 " +
"  )mst " +
"JOIN " +
"  (SELECT PANEL_ID, " +
"    EMPLOYEE_ID " +
"  FROM HRM_PANEL_DETAILS_NEW " +
"  WHERE POSTING_ISSUED='Yes' " +
"  OR POSTING_ISSUED   ='Y' " +
"  )sub " +
"ON mst.PANEL_ID=sub.PANEL_ID " +
")";
        
        }
        else if(officedetail.equalsIgnoreCase("8") || officedetail.equalsIgnoreCase("129"))
        {
        
       subqry= "AND employee_id NOT IN " +
"(SELECT EMPLOYEE_ID " +
"FROM " +
"  (SELECT PANEL_ID,PANEL_CADRE FROM HRM_PANEL_MASTER_NEW WHERE PANEL_CADRE=7 " +
"  )mst " +
"JOIN " +
"  (SELECT PANEL_ID, " +
"    EMPLOYEE_ID " +
"  FROM HRM_PANEL_DETAILS_NEW " +
"  WHERE POSTING_ISSUED='Yes' " +
"  OR POSTING_ISSUED   ='Y' " +
"  )sub " +
"ON mst.PANEL_ID=sub.PANEL_ID " +
")";
        
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
 
"SELECT DISTINCT SENIORITY_LIST_ID, " +
"  x.CADRE_ID, " +
"  mst.EMPLOYEE_ID, " +
"  empanme, " +
"  c.designation_id designation, " +
"  SENIORITY_NO, " +
"  SENIORITY_SUB_NO, " +
"  SENIORITY_NO " +
"  || '' " +
"  || SENIORITY_SUB_NO AS seni_no, " +
"  TURN_NO, " +
"  COMMUNITY_ROTATION_CODE, " +
"  TO_CHAR(DATE_OF_JOINING,'dd/mm/yyyy')                           AS DATE_OF_JOINING, " +
"  TO_CHAR(current_posting_date,'dd/mm/yyyy')                      AS current_posting_date, " +
"  TO_CHAR(Dor,'dd/mm/yyyy')                                       AS Dor, " +
"  DECODE(Dor,NULL,' ' ,Dor)                                       AS Dor1, " +
"  DECODE(empanme,NULL,' ' ,empanme)                               AS empanme1, " +
"  DECODE(current_posting_date,NULL,' ' ,current_posting_date)     AS current_posting_date1, " +
"  DECODE(DATE_OF_REGULARISATION,NULL,' ' ,DATE_OF_REGULARISATION) AS DATE_OF_REGULARISATION, " +
"  REMARKS, " +
"  PROCESS_FLOW_ID, " +
"  UPDATED_BY_USER_ID, " +
"  UPDATED_DATE, " +
"  REFERENCE_IN_WHICH_POSTED , " +
"  Dor " +
"FROM " +
"  (SELECT DISTINCT designation_id, " +
"    cadre_id " +
"  FROM hrm_mst_designations " +
"  WHERE cadre_id="+officedetail+" " +
"  )x " +
"LEFT OUTER JOIN " +
"  (SELECT SENIORITY_LIST_ID, " +
"    CADRE_ID, " +
"    EMPLOYEE_ID, " +
"    SENIORITY_NO, " +
"    SENIORITY_SUB_NO, " +
"    TURN_NO, " +
"    COMMUNITY_ROTATION_CODE, " +
"    DATE_OF_JOINING, " +
"    DATE_OF_REGULARISATION, " +
"    REMARKS, " +
"    PROCESS_FLOW_ID, " +
"    UPDATED_BY_USER_ID, " +
"    UPDATED_DATE, " +
"    REFERENCE_IN_WHICH_POSTED " +
"  FROM HRM_SENIORITY_LIST_TRN " +
"  WHERE CADRE_ID IN("+officedetail+") " +
"  )mst " +
"ON x.cadre_id=mst.cadre_id " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id, " +
"    employee_name " +
"    || '.' " +
"    || employee_initial AS empanme, " +
"    designation " +
"  FROM view_employee2 " +
"  )sub " +
"ON sub.employee_id=mst.employee_id " +
"LEFT OUTER JOIN " +
"  (SELECT employee_id AS emp_id2, " +
"    designation_id, " +
"    date_effective_from AS current_posting_date, " +
"    office_id, " +
"    employee_status_id, " +
"    department_id " +
"  FROM hrm_emp_current_posting " +
"  ) C " +
"ON mst.Employee_Id  =C.Emp_Id2 " +
"AND x.designation_id=c.designation_id " +
"LEFT OUTER JOIN " +
"  ( SELECT Employee_Id Empid,Retiredate AS Dor FROM Allretirementview " +
"  )X3 " +
"ON mst.EMPLOYEE_ID            =X3.EMPID " +
"WHERE employee_status_id NOT IN('SAN','DTH','CMR','VRS','MEV','RES','DIS') " +
"AND Dor                      >=sysdate " +subqry+
"ORDER BY SENIORITY_NO";             
                System.out.println(sql);
                
                System.out.println("sql is----->"+sql);
                Statement st = connection.createStatement();
               ResultSet result = st.executeQuery(sql);
               System.out.println("suceess");
                %>
                
 </tr></table>
 <table cellpadding=0 cellspacing=0 border=1  width='100%'>
 <tr ><td class="tdH">slno</td><td class="tdH">photo</td><td class="tdH">Employee id</td>
 <td class="tdH">Seniority No</td>
 <td class="tdH">Employee Name</td>
   <td class="tdH">Joining Date</td><td class="tdH">Current Posting Date</td>
   <td class="tdH">Date of Retirement</td>
  <td class="tdH"> service Details</td></tr>
 <%  boolean bool = false;
 int i=0;
 int cnt=0;
 //if(result.next())
 System.out.println("suceess");
                while (result.next()) {
               // if((result.getString("employee_status_id").equals("WKG"))||(result.getString("employee_status_id").equals("TRT")|| (result.getString("employee_status_id").equals("DPN")) || (result.getString("employee_status_id").equals("SUS"))||(result.getString("employee_status_id").equals("DPT"))))
               // {
                    i++;
                     %>
                   
               			<tr><td> <%=i %> </td>
                    		<td  ><img  src=ShowImageProfile.jsp?empid=<%=
									result.getString("EMPLOYEE_ID")%> alt='image' height='40' width='40' class='magnify' ></img></td>

	 						<td><%=result.getString("EMPLOYEE_ID")  %>  </td>
 							
 							<td ><%=result.getString("seni_no") %></td>

							 <td ><%=result.getString("empanme1") %></td>
							<td > <%=result.getString("DATE_OF_JOINING")%> </td>
							
							<td ><%=result.getString("current_posting_date1")%></td>
							 
							<td > <%=result.getString("Dor1")%> </td>
							 <td><a  style='color:green' href='viewServiceParticular.jsp?txtEmpId=<%=result.getString("EMPLOYEE_ID")%> '> View Service Details </a></td></tr>
 
 
 <%
 
 System.out.print("cnt------>"+cnt);
 }
 if(i==0)
 {
 out.print("<tr style='color:green'><td colspan='9' align='center' >There is No Record</td></tr>");
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