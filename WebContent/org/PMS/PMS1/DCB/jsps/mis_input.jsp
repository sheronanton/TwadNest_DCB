<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MIS-INPUT  | TWAD Nest - Phase II </title>


 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>

<style>
 
.FR{
scrollbar-face-color:#9898D0;
scrollbar-arrow-color:indigo;
scrollbar-track-color:#C0DCC0;
scrollbar-shadow-color:'';
scrollbar-highlight-color:'';
scrollbar-3dlight-color:'';
scrollbar-darkshadow-Color:'';
}
 
</style>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand_Report.js"></script>
<script type="text/javascript" src="../scripts/RIW.js"></script>

<script type="text/javascript">
function rld()
{
document.myform.submit();
}
</script>
</head>
 <include file="mismenu.jsp" />
 <%
 String amt="",billsno="",inp_month="",inp_year="",all_div="";
   		String combo="";
   				  combo="<select id='dmdlist' name='dmdlist' multiple class='select' >";
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String userid="0",Office_id="",Office_Name="";
			String Date_dis=day+"/"+month+"/"+year;
			   Controller obj=new Controller();
				Connection con;
				try
				{
				con=obj.con();
				obj.createStatement(con);
				  userid=(String)session.getAttribute("UserId");
				if(userid==null)
				{
				 response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				  Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				  if (Office_id.equals("")) Office_id="0";
		 		  Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		 		  inp_month=obj.setValue("pmonth",request);
		 		  inp_year=obj.setValue("pyear",request);
		 		  ResultSet rs_Rs=null;
				obj.conClose(con);
				}catch(Exception e) {
					
					userid="0";
				}
				
				String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
				  
%>
 
<body >
<form action="mis_input.jsp" method="get" name="myform"> 
<input type="hidden" id="subdiv" value="0"/>
<label id="msg"></label>
 <table width="50%" border="0" align="center"  cellpadding="0" cellspacing="0"  >
<tr>
	          <td colspan="4" align="center" ><font color="	#408080"> Tamil Nadu Water Supply and Drainage Board</font></td>
			  
			  </tr>
  			 <tr  >
	          <td colspan="4" align="center"> <%=all_div%></td>
			  
			  </tr >
			  <tr class="tdH">
	          <td colspan="4" align="center">MIS Report</td>
			  
			  </tr>
	         <tr  >
	          <td> <font color="#0000A0"> Year </font> </td>
			  <Td><select class="select"  id="pyear" name="pyear"  style="width:80pt" >
			  <%
			  for (int i=2009;i<=year;i++)
			  {
			   %>
			  <option value="<%=i%>"><%=i%></option>
			  <%} %>
			  </select> </tD>
			  </tr>
			 <Tr >
			   <td><font color="#0000A0">  Month </font></td>	  	          
			 <Td><select class="select" id="pmonth" name="pmonth"  style="width:80pt" >
			 
			  <%
			  for (int j=1;j<=12;j++)
			  {
			   %>
			    <option value="<%=j%>"><%=monthArr[j]%></option>
			  <%} %>
			 </select>   </Td>	        
			 
          	</tr><!-- data_show('show',14,'dmdlist')  -->
			 <tr>
			 <td colspan="4" align="right"><input type="submit" value="set"  class="fb2"><input type="button" value="Exit"  class="fb2" onclick="javascript:window.close()"> </td>
			 </Tr>
			 <tr>
			 <td  class="tdH" colspan="4"> </td>
			 </tr>
		</table>	
		 
		 
	<input type="hidden"   id="pr_status" name="pr_status" value="0"> 
 		<%
 			HttpSession sr=request.getSession(true);
 			sr.setAttribute("misyear",inp_year);
 			sr.setAttribute("misyear",inp_year);
 			out.println(sr.getAttribute("misyear"));
 		%>
 
 
 
 
</form>
     

</body>
</html>