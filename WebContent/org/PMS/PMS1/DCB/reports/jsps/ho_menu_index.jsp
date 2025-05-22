<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%><html>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD Nest II | Division Wise DCB Reports</title>
 <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
  <script type="text/javascript" src="../scripts/reg_menu_index.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<table align="center" width="75%" cellpadding="2">
	<tr  bgcolor="skyblue">
	 <td colspan="2" align="center" class="tdText"><b>Division Wise DCB Reports</b></td>
	</tr>
	 
<%
Controller obj=null;
Connection con;
try

{
		  
	   		   String userid=(String)session.getAttribute("UserId");
				 
				 
				if(userid==null)
				{ 
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				} 

		  obj=new  Controller();
		  con=obj.con();
		  obj.createStatement(con);
		  
		  String cb=obj.combo_str("PMS_DCB_MIS_REPORT_TITLE","REPORT_HEADING","REPORT_REF"," order by REPORT_REF","rpt","","class=select style='width:225px'  ");
		  Calendar cal = Calendar.getInstance();
		  int day = cal.get(Calendar.DATE);  
		  int month = cal.get(Calendar.MONTH) + 1;
		  int year = cal.get(Calendar.YEAR);
		  Common_Impl impl_obj=new Common_Impl();
		  String Region_id="0";		  
		  Region_id=impl_obj.regionId(userid,obj);		  
		  String combo=impl_obj.Div_RegionWise(Region_id,"off_id");
		     
		  
%> 
    <tr class="tdText">
    	<td >  Month
                        

         </td>
         <td >
	         <select id="month"  style="width: 220pt" class="select">
	         
	         	<option value="0" selected="selected" >- - - Select - - -</option>
	         	<%
	         	
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };
	         	
	         	for (int i=1;i<=12;i++) {
	         	%>
	         	<option value="<%=i%>" ><%=amonth[i]%></option>
	         	<%} %>
	          </select>
         </td>
     </tr>
     <tr class="tdText">
     	<td   >
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
	<tr>
	
	<td class="tdText" width="15%">Report Title</td><td><%=cb%></td>
	</tr>
	<% if (! Region_id.equalsIgnoreCase("0")) { %>
	<tr>	
	<td class="tdText" width="15%">Division Name </td><td><%=combo%></td>
	</tr>
	<%} %> 
	
	<!--  <Tr> <Td colspan="2" > <a href="sch_master_report.jsp">Report Condition</a></Td></Tr>  --> 
	<tr bgcolor="skyblue"  >
	 <td colspan="2" align="center">
	 	<input type="button" onclick="rpt_process()" value="Print" style="font-style: italic;font-size:7;color:green;font-weight: bold" class="fb2">&nbsp;&nbsp;
	 	<input type="button" onclick="window.close()" value="Exit" style="font-style: italic;font-size:7;color:red;font-weight: bold" class="fb2">
	 	<img src="../../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('../../jsps/help1.jsp?fn=67','mywindow','width=600,height=400,scrollbars=yes')">
 	</td>
	</tr> 
	
<%
  		  	  
		  
  	
}catch(Exception e) { 
	%>
	<tr  bgcolor="skyblue">
	 <td colspan="2" align="left" class="tdText"><%=e%></td>
	</tr>
	<% 
	
	
}

%>
</table>
</body>
</html>