<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.Connection"%>
<%@page import="java.util.Calendar"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<style type="text/css">
	table th
	{
	
		color: blue; 
		border: 0px solid black;
	}  
	 
</style>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/rectify_journal_data.js"></script>
</head>
<body>
<%
			Connection con=null;
			Controller obj=null;
			String Office_id=null;
			String Office_Name=null;
			Calendar cal=Calendar.getInstance();	
			int day=cal.get(Calendar.DATE);
			int month=cal.get(Calendar.MONTH)+1;
			int year=cal.get(Calendar.YEAR);
			int prv_month=0;
			if (month==1) prv_month=12;
			else
			prv_month=month-1;   
			int prv_year=year;
			if (prv_month==12)  prv_year=year-1; 
			try
			{  
				 
				
				obj=new Controller();
				
				String userid="";
				obj=new Controller();
				con=obj.con();
				obj.createStatement(con);
				try
				{
					   userid=(String)session.getAttribute("UserId");
				}catch(Exception e) {userid="0";}
				
				if(userid==null)
				{ 
						response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				 Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				 if (Office_id.equals("")) Office_id="0";
				Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
				if (Office_id.equals("")) Office_id="0";
			}catch(Exception e)
			{ 
				
			} 

			String combo1=obj.combo_str("PMS_DCB_BEN_TYPE  ","BEN_TYPE_DESC","BEN_TYPE_ID"," where exists (select district_code from PMS_DCB_MST_BENEFICIARY where office_id="+Office_id+" and   PMS_DCB_BEN_TYPE.BEN_TYPE_ID= BENEFICIARY_TYPE_ID) ","type","onchange=process(1,1)");
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

%>
<table align="center">
	<tr> 
		<td colspan="2" align="center"><%=Office_Name%></td>		
	</tr>
	<tr>
	     <th  align="left"><font color="#0000A0">Year </font></th>
		 <td colspan="2">
		  <select class="select"  id="pyear" name="pyear"  style="width:120pt;" onchange="report_period_verify(document.getElementById('pmonth'),this)">
		  <option value="0">-Select Year-</option>
		  <%
			  for (int i=year-2;i<=year;i++)
			  {
		   		if (i==prv_year)  
		   		{
		   		%>
		   		<option value="<%=i%>" selected="selected"><%=i%></option>
		  		<% } else {	%> <option value="<%=i%>"><%=i%></option><%}
			   }
		  %>
				  </select>
			   </td>
			  </tr>
			 <Tr >
			   <th  align="left"><font color="#0000A0">Month </font></th>	  	          
			   <td colspan="1">
			   		<select class="select" id="pmonth" name="pmonth"  style="width:120pt;" onchange="report_period_verify(this,document.getElementById('pyear'))">
					  	<option value="0">-Select Month-</option>
				  		<%
				  			for (int j=1;j<=12;j++)
				  			{
				  			  if (j==prv_month) { 
				   		%>  <option value="<%=j%>" selected="selected"><%=monthArr[j]%></option><% }  else { %>
					    	<option value="<%=j%>"><%=monthArr[j]%></option> <% } %>
				  		<%  } %>
				 </select>
			 </td>	        
          	</tr>
	<tr>
		<th  align="left">Beneficiary Type</th>	
		<td><%=combo1 %></td>
	</tr>
	<tr>
			<th  align="left">Beneficiary Name</th> 
			<td><select id="ben_name" name="ben_name"></select>
			<input type=button value="Select" onclick="process(2)"></td>
	</tr>
 	
</table>
	<table align="center" width="75%" height="" border="1"  style="border-collapse: collapse;border-color: purple">
				<tr>
					<td>	
						Beneficiary Name
					</td>
					<td>	
						Location Name
					</td>
					<td>	
						Scheme Name
					</td>
					<td>	
						Total Supplied
					</td>
					<td>	
						Previous Amount
					</td>
					<td>	
						Rectify Amount
					</td>
				</tr> 
				<tbody id='tbody'>
					<table id='ttable'></table>
				</tbody>
	</table>



</body>
</html>