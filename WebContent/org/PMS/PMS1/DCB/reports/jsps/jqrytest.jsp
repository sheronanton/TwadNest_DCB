<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<title>Insert title here</title>
<script src="../../../../../../jquery-3.6.0.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 
<script type="text/javascript">
	$(document).ready(function() {                              
		$('#but').click(function() {
			 var form = $(this);
				var  year=$("input[name='year']").val();
				var  month=$("input[name='month']").val();
				var dataString ={"syear":year,"smonth":month};
			   
				$.ajax({
	                type: "post",     
	                data:dataString,
	                url: "../../../../../../Journal_Type_Report",  
	                success: function(data){
	                //pairno ta dedomena
	                document.getElementById("dv").innerHTML=data;  
	            }  
	         });
});
});  
	 
</script>    
</head>  
<body id='fr'>
<%
		 String userid="0",Office_id="",Office_Name="";
		 Controller obj=new Controller();
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		userid=(String)session.getAttribute("UserId");		
		if(userid==null)
		{
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		 Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;		 
		if (Office_id.equals("")) Office_id="0";
 		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
%> 
		<table width="90%"     align="center"  cellpadding="6" cellspacing="0"  height="50%" border="1" style="border-color: skyblue;border-collapse: collapse">
		<tr>    
	          <td colspan="4" align="center" ><font color="	#408080"> Tamil Nadu Water Supply and Drainage Board</font></td>			   
			  </tr>
  			 <tr>
	          <td colspan="4" align="center"> <%=Office_Name%></td>			  
			  </tr>
			  <tr class="tdH">
	          <td colspan="3" align="center">Bill Demand List</td>
			  
			  </tr>
	         <tr  >
	          <td  align="left"> <font color="#0000A0"> Month And Year </font> </td>
			  <Td align="left"><select class="select" id="pmonth" name="pmonth"  style="width:90pt;"  onchange="rld()">
			  <option value="0">-Select Month-</option>
			  <%
			  for (int j=1;j<=12;j++)
			  { %>
			  <option value="<%=j%>"><%=monthArr[j]%></option>
			 <%} %>
			   
			 </select> <select class="select"  id="pyear" name="pyear"  style="width:80pt;" onchange="rld()" >
			   <option value="0">-Select Year-</option>
			  <%
			  for (int i=2010;i<=year;i++)
			  {
			   %>
			  <option value="<%=i%>"><%=i%></option>
			  <%} %>
			  </select></td><td  align="left"><input type="button"  id="but" value="Show"> </tD>
			  </tr>
	</table>
	<table width="90%"     align="center"  cellpadding="6" cellspacing="0"  height="50%" border="1" style="border-color: skyblue;border-collapse: collapse">
			  <tr>
			  	<td colspan="2">
			  			<div id="dv"></div>
			  	</td>
			  </tr>
	</table>
	
</body>
</html>