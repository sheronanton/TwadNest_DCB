<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@page import="java.util.Calendar"%>
<%@page	import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="Servlets.Security.classes.UserProfile"%>
<%@page import="java.sql.Connection"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quantity pumped to the Private beneficiaries</title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
		function rld3()
		{
			var pyear=document.getElementById("finyear").value;
			var notmonth=document.getElementById("pmonth").value;
			window.open("../../../../../../Water_Charges_Report?command=Private_PR&pyear="+pyear+"&pmonth="+notmonth);
		}
</script>

</head> 
<body>
<%
try
{
		Connection con=null;String Schemestr="",Office_id="0";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		HttpSession session1 = request.getSession(false);
		String userid = (String) session1.getAttribute("UserId");
		Controller obj = new Controller();
		con=obj.con();
		obj.createStatement(con);
		if (userid == null) 
		{
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		 Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		String cyear="";
		Schemestr=obj.combo_str("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","BENEFICIARY_SNO","where office_id="+Office_id+" and status='L' order by  BENEFICIARY_NAME asc","ben","  style='width: 150;font-size: 0.7em; color: blue;' class='select'  ");
		String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
		String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		int prvyear=obj.prvfinyear(year,month);
		 
%>
<table align="center" width="70%" border=1 class="table" cellpadding="10" cellspacing="0" style="color: white;border-collapse: collapse">
	<tr  bgcolor="#11297C">
		<td colspan="2" align="center" class="tdH">Quantity pumped to the Private beneficiaries</td>
	</tr>
	<Tr>
		<td><font color=blue>Financial Year </font></td>
		<td><select id="finyear" tabindex="5" style="width: 220pt" class="select">
			<option value="0" selected="selected">- - - Select - - -</option>
			<%
             for (int j=year-5;j<=year+1;j++)
             {
            	
            	 if (prvyear!=j-1)
					{
            		 
					%>  
					<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
					<% } else { %>
            		<option value="<%=j-1%>" selected><%=j-1%>-<%=j%></option>
				<%} 
			  }%>
		</select></td>
	</tr>
	 <Tr>
		    <td> 
				<font color=blue> Upto Month  </font>
			</td>
				<td colspan="2">
			 <select class="select" id="pmonth" name="pmonth" style="width: 80pt;"  >
				<option value="0">-Select-</option>
					<%  for (int j = 1; j <=12  ; j++) {
						  
						%>
						<option value="<%=monthArrv[j]%>" <%=((j==month-1)?"selected":"")%>><%=monthArr[j]%></option>
						<%} %>
				 
				</select>
			 </td>
			 
			 </Tr>
	<tr>
		<td colspan="2" align="center">
			<input type="button" value="REPORT" class="fb2" onclick="rld3()">
			<input type="button" value="EXIT" class="fb2" onclick="window.close()">
		</td>
	</tr>
	<tr>
		<td colspan="4" align="center"></td>
	</tr>
</table>
<%
}catch(Exception e) 
{ 
	out.println(e);
}
%>
</body>
</html>