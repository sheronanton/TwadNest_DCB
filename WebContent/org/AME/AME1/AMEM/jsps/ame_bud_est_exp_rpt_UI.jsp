		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle" %>
 			<%@ page session="false" contentType="text/html;charset=windows-1252" %>
 			<%@ page import="java.util.Calendar" %>
 			<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Estimate , Budget and Item Wise Expenditure </title>
			<script type="text/javascript"><!--
			function report(a)
			{
			 	var flag_action=document.amebudestexprpt.flag_action.value;
				var pyear=document.amebudestexprpt.pyear.value; 
				var Office_id=document.amebudestexprpt.Office_id.value;

				var parms="?flag_action="+flag_action+"&pyear="+pyear+"&Office_id="+Office_id;
				 if (a==1)					
					document.location.href='ame_bud_est_exp_rpt.jsp'+parms;
				if (a==2)					
					document.location.href='ame_bud_est_exp_reg_rpt.jsp'+parms;
			}  
											
			
			</script>
			</head>
 			 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
			<body>
			<%  
			
			try			
			{
			
			
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			
			
			Controller obj=new Controller();
			if (userid == null) {

				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			 
			
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
			
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String flag_c=obj.setValue("flag_c",request);
			 int year_incr=0;
			  if (month <4)
				  year_incr=0;    
			  else
				  year_incr=1;
			  int prvfinyear=obj.prvfinyear(year,month);  
			%>			 
			<form name="amebudestexprpt" method="post">  
			<table width="100%"  style="border-collapse: collapse;border-color: skyblue" align="center" border=1 cellpadding="5" cellspacing="0">
			 <tr> <th colspan="3" align="center"  class="tdH">Estimate , Budget and Item Wise Expenditure  <label id='msg'></label></th></tr>
			 <tr> <td width="25%"><label class="lbl">Office Name :</label></td><td align="left" colspan="2"> <label class="lbl"><%=Office_Name%></label></td></tr>
			 			  	
			 <tr> 
	         <td><label class="lbl">Financial Year</label> </td>
			 <td colspan="3">
			 <select class="select"  name="pyear"  style="width:100pt;" >
			 		 <option value="0">--Select Year--</option>
			 		 <%
                	 for (int j=year-1;j<=year+year_incr;j++)
                	 { 
                		%>    
                    		<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
                  		<%
                	} %>
                	</select> 
			  </td>
			  </tr>
			   
			 <tr class="tdH">
			 	<th align="center" colspan="4"> 
	 					<input type="hidden" name="sub" value="Report" onclick="report(1)" class="btn" >
	 					<input type="button" name="sub" value="View" onclick="report(2)" class="btn" >
						<input type="button" value="Exit"  class="btn" onclick="window.close()"/> 
				</th>
			</tr>
			</table> 
			<input type="hidden" id="flow_" name="flow_" value="2"/>  
	 	<a href='ame_bud_est_exp_rpt_upto_UI.jsp'>.</a>
	   
	<input type="hidden" name="Office_id" value="<%=Office_id%>"/>
	<input type="hidden" name="flag_action" value="0"/>
	<input type="hidden" name="row_count" value="0"/>
	<input type="hidden" name="rowcount2" value="0"/>
	<input type="hidden" name="flag_c" value="<%=flag_c%>"/>
				<%}catch(Exception e) 
				{
				response.sendRedirect("../../../../../index.jsp");
				} %>
				<input type="hidden" id="pr_status" name="pr_status" value="1"/>
			</form>
			</body>
		</html>