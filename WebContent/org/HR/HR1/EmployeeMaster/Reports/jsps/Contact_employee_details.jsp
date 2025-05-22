<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ResourceBundle,java.io.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Employees Contact Details</title>
    
<script type="text/javascript">

function back()
{
window.location.assign("HRM_Contact_employee_details.jsp");
}
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
Connection connection = null;
        PreparedStatement ps = null;
        Statement s = null;
        ResultSet result = null;
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
         //           strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
         //           ":" + strsid.trim();
          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
           
           
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
         //   System.out.println(connectMsg);
        }
String sql = "";
//                String workstat = request.getParameter("workstat");
String cadre_id="";
String oof="";
String address="";
int colspan=9; 
String hier="";
String offlevel="";
String officeselected ="";
String cadre_name="";
try
{
	hier=request.getParameter("allhier");
System.out.println("hier==>"+hier);
cadre_id=request.getParameter("cadre_id");
address=request.getParameter("Address");
officeselected  = request.getParameter("officeselected");
offlevel=request.getParameter("offlevel");
oof=request.getParameter("oof");
System.out.println("offlevel==>"+offlevel);
}
catch(Exception es)
{
	System.out.println("Error is-->"+es);
}


 sql="select cadre_id,cadre_name from HRM_MST_CADRE where cadre_id in("+cadre_id+")";
               ps=connection.prepareStatement(sql);
               result=ps.executeQuery();
               while(result.next())
               {
               cadre_name+=result.getString("cadre_name")+" , ";
               }
                cadre_name=cadre_name.substring(0,cadre_name.length()-2);

 %>
<br>
<table border='2' width="100%">
          <tr>
            <td colspan="2" align="left" valign="top" class="table" width="60%">
              <table border="0" width="100%">
             
                <tr>
                  <td colspan='2' class="tdH" align="center"><strong> <%=cadre_name %> Employees Contact Details</strong></td>
                  
                </tr><tr><td>
<!-- <div id="getemplist"></div></td>
 -->
 <%
  try {
         
       System.out.println("officeselected id==>"+officeselected);
   try
   {
if(hier.equalsIgnoreCase("include"))
{
	 if(offlevel.equalsIgnoreCase("ho"))
	 {
		 System.out.println("inside ho id==>");
		 try
		 {
	 sql="select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW order by off_order";
	 ps=connection.prepareStatement(sql);
     result=ps.executeQuery();
     while(result.next())
     {
    	 officeselected +=","+result.getString("OFFICE_ID");
     }
		 }
		 catch(Exception e)
		 {
			 System.out.println("Error is-->"+e);
			 
		 }
	 }
	 else if(offlevel.equalsIgnoreCase("rg"))
	 {
		try
		{
		sql="select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where REGION_OFFICE_ID in ('"+officeselected+"')";
		 ps=connection.prepareStatement(sql);
	     result=ps.executeQuery();
	     while(result.next())
	     {
	    	 officeselected +=","+result.getString("OFFICE_ID");
	     }
		 }
		 catch(Exception e)
		 {
			 System.out.println("Error is-->"+e);
		 }
	}
	 else if(offlevel.equalsIgnoreCase("cr"))
	 {
		try
		{
			
		sql="select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where CIRCLE_OFFICE_ID in ('"+officeselected+"')";
		 ps=connection.prepareStatement(sql);
	     result=ps.executeQuery();
	     while(result.next())
	     {
	    	 officeselected +=","+result.getString("OFFICE_ID");
	     }
		 }
		 catch(Exception e)
		 {
			 System.out.println("Error is-->"+e);
		 }
	 }
	 else if(offlevel.equalsIgnoreCase("dv"))
	 {
		try
		{
		sql="select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in ('"+officeselected+"')";
		 ps=connection.prepareStatement(sql);
	     result=ps.executeQuery();
	     while(result.next())
	     {
	    	 officeselected +=","+result.getString("OFFICE_ID");
	     }
		 }
		 catch(Exception e)
		 {
			 System.out.println("Error is-->"+e);
		 }
	 }
	 else if(offlevel.equalsIgnoreCase("aw"))
	 {
		try
		{
		sql="select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where AUDITWING_OFFICE_ID in ('"+officeselected+"')";
		 ps=connection.prepareStatement(sql);
	     result=ps.executeQuery();
	     while(result.next())
	     {
	    	 officeselected +=","+result.getString("OFFICE_ID");
	     }
		 }
		 catch(Exception e)
		 {
			 System.out.println("Error is-->"+e);
		 }
	 }
	 else if(offlevel.equalsIgnoreCase("lb"))
	 {
		try
		{
		sql="select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where LAB_OFFICE_ID in ('"+officeselected+"')";
		 ps=connection.prepareStatement(sql);
	     result=ps.executeQuery();
	     while(result.next())
	     {
	    	 officeselected +=","+result.getString("OFFICE_ID");
	     }
		 }
		 catch(Exception e)
		 {
			 System.out.println("Error is-->"+e);
		 }
	 }
	 
	else if(officeselected.equalsIgnoreCase("All"))
    {
	System.out.println("inside --->"+officeselected);
     sql="select OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where OFFICE_STATUS_ID='CR'";
                ps=connection.prepareStatement(sql);
                result=ps.executeQuery();
                
                while(result.next())
                {
                     if(officeselected.equals("All"))
                     
                        officeselected =result.getString("OFFICE_ID");
                        
                     else
                       	officeselected +=","+result.getString("OFFICE_ID");
                }
            
               
            
    }
	 
   officeselected=officeselected.substring(0,officeselected.length());  
   System.out.println("officeselected--->"+officeselected);
}

 if(officeselected.equalsIgnoreCase("All"))
    {
	System.out.println("inside --->"+officeselected);
     sql="select OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where OFFICE_STATUS_ID='CR'";
                ps=connection.prepareStatement(sql);
                result=ps.executeQuery();
                
                while(result.next())
                {
                     if(officeselected.equals("All"))
                     
                        officeselected =result.getString("OFFICE_ID");
                        
                     else
                       	officeselected +=","+result.getString("OFFICE_ID");
                }
            
                officeselected=officeselected.substring(3,officeselected.length());  
            
    }
   else

	System.out.println("direct--->"+officeselected);

                sql="SELECT mst.EMPLOYEE_ID, " +
"  empname, " +
"  designation, " +
"  OFFICE_ID, " +
"  DECODE(address,NULL,'  ', address)                                         AS address, " +
"  DECODE(EMAIL_ADDR,NULL,'  ', EMAIL_ADDR)                                   AS EMAIL_ADDR, " +
"  DECODE(PERMANENT_RES_DISTRICT_CODE,NULL,'  ', PERMANENT_RES_DISTRICT_CODE) AS PERMANENT_RES_DISTRICT_CODE, " +
"  DECODE(CONTACT_PHONE_NO,NULL,'  ', CONTACT_PHONE_NO)                       AS CONTACT_PHONE_NO, " +
"  DECODE(CONTACT_CELL_NO,NULL,'  ', CONTACT_CELL_NO)                         AS CONTACT_CELL_NO, " +
"  office_name " +
"FROM " +
"  (SELECT EMPLOYEE_ID, " +
"    DESIGNATION_ID, " +
"    OFFICE_ID " +
"  FROM HRM_EMP_CURRENT_POSTING " +
"  WHERE OFFICE_ID            IN("+officeselected+") " +
"  AND employee_status_id NOT IN ('RES','DIS','VRS','CMR','MEV','SAN','DTH') " +
"  AND DESIGNATION_ID         IN " +
"    (SELECT DESIGNATION_ID FROM HRM_MST_DESIGNATIONS WHERE CADRE_ID IN("+cadre_id+") " +
"    ) " +
"  )mst " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_ID, " +
"    EMPLOYEE_NAME " +
"    || '.' " +
"    || EMPLOYEE_INITIAL AS empname " +
"  FROM view_employee2 " +
"  )sub " +
"ON sub.EMPLOYEE_ID=mst.EMPLOYEE_ID " +
"LEFT OUTER JOIN " +
"  (SELECT EMPLOYEE_ID, " +
"    PRESENT_RES_ADDRESS1 " +
"    ||PRESENT_RES_ADDRESS2 " +
"    ||PRESENT_RES_ADDRESS3 AS address, " +
"    CONTACT_PHONE_NO, " +
"    CONTACT_CELL_NO , " +
"    email_addr, " +
"    PERMANENT_RES_DISTRICT_CODE " +
"  FROM HRM_MST_EMP_CONTACT_INFO " +
"  )sub1 " +
"ON sub1.EMPLOYEE_ID=sub.EMPLOYEE_ID " +
"LEFT OUTER JOIN " +
"  (SELECT DESIGNATION_ID,designation FROM hrm_mst_designations " +
"  )SUB2 " +
"ON SUB2.DESIGNATION_ID=MST.DESIGNATION_ID " +
"LEFT OUTER JOIN " +
"  (SELECT OFFICE_ID AS OFFID,OFFICE_NAME FROM COM_MST_OFFICES " +
"  )OFFICE " +
"ON mst .office_id=OFFICE.offid " +
"ORDER BY " +
"  empname" ;

                         System.out.println("sql--->"+sql);
               ps=connection.prepareStatement(sql);
              result=ps.executeQuery();
              
              
                System.out.println(sql);
               
                if(address.equalsIgnoreCase("WA") )
                	  colspan=10; 
   }
   catch(Exception e)
   
   {
	   System.out.println("exception "+e);
   }
                
                 %>
                
 </tr></table>
 <table  cellpadding=0 cellspacing=0 border=1  width='100%'>
 <tr class="tdH">
 <td>sno</td>
 <td>photo</td>
 <td>Emp id</td>
 <td>Name</td>
 <td>Designation</td>
 <td>Office Name</td>
  <% if(address.equalsIgnoreCase("WA") ){%>
 <td>Address</td>
   <% } %>
 <td>Phone no</td> 
 <td>Mobile no</td>
 <td>Email id</td>
 <%  boolean bool = false;
 int i=0;
 
                while (result.next()) {
                    i++;
                     %>
                     <%if((i%2)==0){ %>
                    <tr><td> <%=i %> </td><td ><img  src=ShowImageProfile.jsp?empid=<%=
result.getString("EMPLOYEE_ID")%> alt='image' height='55' width='70' class='magnify' ></img></td>
<td > <%=result.getString("EMPLOYEE_ID")  %> </td>
<td> <%=result.getString("empname")%>  </td>
<td> <%=result.getString("designation")%>  </td>
<td ><%=result.getString("office_name")%> </td>
<%
if(address.equalsIgnoreCase("WA"))
{
	%>
<td> <%=result.getString("address")%>  </td>
<%}
%>
<td ><%=result.getString("CONTACT_PHONE_NO")%></td>
<td ><%=result.getString("CONTACT_CELL_NO")%> </td><td ><%=result.getString("EMAIL_ADDR")%> </td><tr>
 <%}
 else 
	
  {
	 %>
     <tr bgcolor='#C2DFFF'><td> <%=i %> </td><td ><img  src=ShowImageProfile.jsp?empid=<%=
result.getString("EMPLOYEE_ID")%> alt='image' height='55' width='70' class='magnify' ></img></td>
<td > <%=result.getString("EMPLOYEE_ID")  %> </td>
<td> <%=result.getString("empname")%>  </td>
<td> <%=result.getString("designation")%>  </td>
<td ><%=result.getString("office_name")%> </td>
<%
if(address.equalsIgnoreCase("WA"))
{
%>
<td> <%=result.getString("address")%>  </td>
<%}
%>
<td ><%=result.getString("CONTACT_PHONE_NO")%></td>
<td ><%=result.getString("CONTACT_CELL_NO")%> </td><td ><%=result.getString("EMAIL_ADDR")%> </td><tr>
<%
 }%>
 <%}
                     
  if(i==0)
  {
   out.println("<tr bgcolor='#C2DFFF' align='center'><td colspan='9'><font color='green'><strong>There is No Data !</strong></font></td></tr>");
  }
 }
   catch (Exception e) {
                
                System.out.println(" error here---->" + e);
                

            } %>
            </table></center>

</table></td><td><center><input type="button" onclick="back();" value="Back"><input type="button" onclick="self.close();" value="close"></center></td></tr></table></body>
</html>