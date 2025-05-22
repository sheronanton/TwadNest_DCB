 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <html> 
 	<head>
 		<title>Opening Balance Report</title>
 		<script type="text/javascript" src="../scripts/TariffReport.js"></script>
 		<script type="text/javascript" src="../../scripts/Basic.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>	
		 <%
		 	String userid="112",Office_id="",Office_Name="";
		
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
			    if (Office_id.equals("")) Office_id="5082";
				Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
				
			    obj.conClose(con);
			}catch(Exception e) {
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
		 %>
 
 	</head>
 	    <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 	
	 <body onload="callServer('Type');callServer('Year');">
		<table class="table" id="data_table" width="65%" align="center" border="1" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  >
		 <tr class="tdH">
		          <td align="center" height="20px" colspan="4" height="10">
		          			Tariff Report - <jsp:expression>Office_Name</jsp:expression> <font class="tdText" color="red"><label id="msg"></label></font>
		          </td>
		 </tr> 
				<!-- tr class="tdText">
					<td width="10%" class="tdText"><b>Month&Year</b>  </td>
			        <td width="10%" align="left">
				        <select class="select" id="month" name="month">
				        	<option value="">-Month-</option>
				        	<option value="1">Jan</option>
				        	<option value="2">Feb</option>
				        	<option value="3">Mar</option>
				        	<option value="4">Apr</option>
				        	<option value="5">May</option>
				        	<option value="6">Jun</option>
				        	<option value="7">Jul</option>
				        	<option value="8">Aug</option>
				        	<option value="9">Sep</option>
				        	<option value="10">Oct</option>
				        	<option value="11">Nov</option>
				        	<option value="12">Dec</option>
				        </select>
				        <select class="select" id="year" name="year">
				        	<option value="">-Year-</option>
				        </select>
			        </td>
			       
		        </tr--> 
				<tr>
					<td width="10%" class="tdText"> <b> Beneficiary Type</b> </td>
					<td width="10%" align="left"> 
						<select class="select" id="bentype" name="bentype">
							<option value="">-Select Type-</option>  
						</select>
					</td>
					<!-- td width="10%" class="tdText">
						<b>District</b>  :  
						<select id="dis" class="select" name="dis" onchange="callServer('Block');callServer('Ben');" style="width:100pt" onchange="callServer('Ben');" disabled="true">
							<option value="">-Select District-</option>
						</select>
					</td>
					<td align="left" width="15%" class="tdText"> 
						<b>Block </b> : 
						<select class="select" id="blk" class="selectbox" name="blk" style="width:100pt" onchange="callServer('Ben');" disabled="true">
							<option value="">-Select Block-</option>
						</select>
					</td-->
					
				</tr>
				<Tr>
					<td valign="top" class="tdText"> <b>Beneficiary Name</b></td> 
					<td>
						<!-- select id="ben_value" class="select" name="ben_value" onchange="ckset(this)" style="width:250pt"></select-->
						<input type="hidden" id="ben" name="ben" />
						<input type="text" id="beneficiary" name="beneficiary" class="tb6" disabled/>
						<img src="../../../../../../images/c-lovi.gif" width="20" height="20" alt="Click here" onclick="searchConsumer(document.getElementById('bentype').value);"/>
					</td>
				</Tr>
				
				<tr>
					<td colspan="2">
						<center>
							<input type="button"  class="fb2" value="Print" onclick="callServer('Tariff');"/>
							<input type="button" class="fb2" value="Exit" onclick="self.close();"/>
						</center>
					</td>
				</tr>
				<tr class="tdH">
		          <td align="center" height="20px" colspan="4" height="10">
		          			
		          </td>
		 </tr> 
		</table>
	</body>
</html>