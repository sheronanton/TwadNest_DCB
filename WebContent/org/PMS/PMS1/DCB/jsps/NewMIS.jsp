<%@ page language="java"  pageEncoding="ISO-8859-1" import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.util.Calendar,java.sql.Connection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Head Office --- Reports</title>
		<script type="text/javascript">
		function headreport()
		{  
			        var rpt=document.getElementById('rpt').value;
			 		var fyear=document.getElementById('pyear').value;
					var fmonth=document.getElementById('pmonth').value;
					 var s = window.open("../../../../../Beneficiary_DCB_ob?command=newmispdf&fmonth="+fmonth+"&fyear="+fyear+"&process_code="+rpt);
		}	     
		</script>
</head>
		<body>
		  <%
		 		    String new_cond=Controller.new_cond;
					Calendar cal = Calendar.getInstance();
					int day = cal.get(Calendar.DATE);
					int month = cal.get(Calendar.MONTH) + 1;
					int year = cal.get(Calendar.YEAR);
					String userid="0",Office_id="",Office_Name="";
					String Date_dis=day+"/"+month+"/"+year;
					Controller obj=new Controller();
					Connection con;
					con=obj.con();
					obj.createStatement(con);
					try 
					{
						userid = (String) session.getAttribute("UserId");
					} catch (Exception e) 
					{
					}
					if(userid==null)
					{
					 	response.sendRedirect(request.getContextPath()+"/index.jsp");
					}
					
					String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
						  
		%> 
			<table align="center" width="50%" border="1">
				     <tr>
				          <td> <font color="#0000A0">  Year And Month  </font> </td>
						  <Td> <select class="select"  id="pyear" name="pyear"  style="width:80pt;" onchange="rld()" >
						   		<option value="0">-Select Year-</option>
							  		<% for (int i=2011;i<=year;i++) { %>
							  		<option value="<%=i%>" selected><%=i%></option>
							  		<%} %>
						  		</select>
						  		
						  		<select class="select" id="pmonth" name="pmonth"  style="width:90pt;">
						  		 <option value="0">-Select Month-</option>
						  		 <% for (int j=1;j<=12;j++) { %>
						    	<option value="<%=j%>"><%=monthArr[j]%></option>
						  		 <% } %>  
						 		</select>
						 </td>
					</tr>
					<tr>
						<td> <font color="#0000A0"> Head Office Report List</font></td>
						<td>	
							<select id="rpt" onchange="headreport()">
								<option value="0">Select</option>
								<option value="1">Abstract of Water Charges Due</option>
								<option value="2">Water Charges Due from Village Panchayats</option>
								<option value="3">Water Charges Due from Town Panchayats</option>
								<option value="4">Water Charges Due from Municipalities</option>
								<option value="5">Water Charges Due from Corporations</option>
							</select> 
						</td>
					</tr>
			</table>
		</body>
</html>