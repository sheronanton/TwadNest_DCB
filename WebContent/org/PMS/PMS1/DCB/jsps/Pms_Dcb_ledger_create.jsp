<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@page import="java.util.Calendar" %>
      <%@page import="java.sql.*" %>
      <%@ page import="java.util.ResourceBundle" %>
      <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
 <%@ page import="Servlets.Security.classes.UserProfile"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script type="text/javascript" src="../scripts/cellcreate.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href='../../../../../css/txtbox.css' rel='stylesheet' media='screen'/>
 <script type="text/javascript" src="../scripts/Pms_Dcb_Ledger.js"></script>
  <script type="text/javascript" src="../scripts/msg.js"></script>
  <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<title>DCB Ledger Data Upload | TWAD Nest - Phase II</title>
</head>
<body onload="divload(),flash()">
<%
 
String userid="0",Office_id="",Office_Name="";

Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) + 1;
int year = cal.get(Calendar.YEAR);
%>
<form name="pms_dcb_ledger" action="pms_dcb_ledger" >
<table  class="fb2" border="1" width="75%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  >
	<tr class="tdH"  style="color:black">
   		<td colspan="4"   align="center" >
        	<strong>DCB Ledger Data Upload</strong>
        </td>
   </tr>
   <tr class="tdText">
    	<td  width="40%" colspan="2">  Month
                        

         </td>
         <td   colspan="2">
	         <select id="month"  style="width: 220pt" class="select">
	         
	         	<option value="0" selected="selected" >- - - Select - - -</option>
	         	<%
	         	
	         	String []amonth ={"-select month-","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};
	         	
	         	for (int i=1;i<=12;i++) {
	         	%>
	         	<option value="<%=i%>" ><%=amonth[i]%></option>
	         	<%} %>
	          </select>
         </td>
     </tr>
     <tr class="tdText">
     	<td  width="40%" colspan="2" >
        	<label id="benname" >     Year </label> 
                            
           </td>
            <td  colspan="2">
            	<select id="year"  tabindex="5" style="width:220pt"  class="select">
                	<option value="0" selected="selected">- - - Select - - -</option>
                	<%
                	
                	for (int j=2009;j<=year;j++)
                	{
                	%>
                	<option value="<%=j%>"><%=j%></option>
                	<%} %>
                	

                	
                 </select>
             </td>
     </tr>
      <tr class="tdText">
     	<td  width="40%" colspan="2" >
        	<label id="benname" >     Report generation for </label> 
                            
           </td>
            <td  colspan="2">
            	
                 <input type="radio" value="AllDivision" id="divselection "name="divselection"  checked="true" onclick="showdiv()";></input>All Division
                  <input type="radio" value="SelectDivision" id="divselection "name="divselection" onclick="showdiv()";></input>Select Division
             </td>
     </tr>
     <TR>
     <td colspan="4">&nbsp;
     <label id="msg"></label>
     </td>
     </TR>
     </table>
     <div id="showdivload">
     <table border="1" width="75%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"   >
      <tr class="tdText">
     	<td  width="40%" colspan="2" >
        	<label id="benname" >     Division </label> 
                            
           </td>
            <td  colspan="2">
            	 
                	
				<%
				 Connection connection = null;
				PreparedStatement ps;
				ResultSet res;
				Controller obj = new Controller();
				try 
		        {
		            ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
		            String ConnectionString = "";
		            String strdsn = rs.getString("Config.DSN");//jdbc:oracle:thin:
		            String strhostname = rs.getString("Config.HOST_NAME");//10.163.0.58
		            String strportno = rs.getString("Config.PORT_NUMBER");//1521
		            String strsid = rs.getString("Config.SID");//twadnest
		            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");//oracle.jdbc.OracleDriver
		            String strdbusername = rs.getString("Config.USER_NAME");//twadpms
		            String strdbpassword = rs.getString("Config.PASSWORD");//twadpms123
		       //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" + strsid.trim();
		        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
		            Class.forName(strDriver.trim());  
		            connection = DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
		            System.out.println("Paid by master");
		            try 
		            {
		                connection.clearWarnings();
		            } 
		            catch (SQLException e)  
		            {
		                System.out.println("Exception in creating statement:" + e);
		            }
		        } 
		        catch (Exception e) 
		        {
		            System.out.println("Exception in openeing connection:" + e); 
		        }
		        try 
	            {
	            	  userid = (String) session.getAttribute("UserId");
	            	  obj.createStatement(connection);
	           		 Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	                ps = connection.prepareStatement("select distinct COM_MST_OFFICES.OFFICE_ID,COM_MST_OFFICES.OFFICE_NAME from PMS_DCB_DIV_DIST_MAP join COM_MST_OFFICES on COM_MST_OFFICES.OFFICE_ID=PMS_DCB_DIV_DIST_MAP.OFFICE_ID where COM_MST_OFFICES.OFFICE_ID="+Office_id+" order by OFFICE_NAME");
	               
	              
	                res = ps.executeQuery();%>
	               <select name="divisionid" id="divisionid">
	               <option value=0>- - - Select - - -</option>
	              <% while(res.next())
	               {
	
				%>
				<option value="<%=res.getInt("OFFICE_ID")%>" selected><%=res.getString("OFFICE_NAME")%></option>    
				<% } %>            	
                 </select>
                <% } 
                catch (Exception e) 
		        {
		            System.out.println("Exception in openeing connection:" + e); 
		        }%>
             </td>
     </tr>
     </table>
     </div>
     <table border="1" width="75%" align="center" cellpadding="4" cellspacing="0" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"   >
       <tr>
         	 
                   
                    <td style="padding-top:20px;" colspan="4"> 
                  <input type="hidden" id="pr_status" name="pr_status" value="1"/>
                  <div align="center">
                    <input type="button" name="Generate" value="Upload Data" id="cmdgenerate" onclick="funcmdgenerate();"  class="fb2"/> 
                    <input type="button" name="Exit" value="Exit" id="exit" onclick="exitwindow();"  class="fb2"/>
                    <input type="button" name="Clear" value="Clear" id="clear" onclick="refresh();"  class="fb2"/>
                          <input type="button" name="Generateactual" value="Upload Data Actual Figure" id="cmdgenerate" onclick="funcmdgenerate_actual();"  class="fb2"/> 
                   </div>
                    </td>
                 
         </tr>
</table>
</form>
</body>
</html>