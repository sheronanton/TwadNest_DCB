		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle" %>
 			<%@ page session="false" contentType="text/html;charset=windows-1252" %>
 			<%@ page import="java.util.Calendar" %>
 			<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title> Scheme Performance </title>
			<script type="text/javascript">
			function page_size()
			{
				window.resizeTo( 800,700 )
			}
			 
			function exp()
			{
				var rowcount2 = document.getElementById("rowcount2").value;
				var table = document.getElementById("etablesub");
				var t=0;
				var sum=0;
				for(t=1;t<=rowcount2;t++)
				{
				sum+=parseFloat(document.getElementById("eamt"+t).value)	
				}
				document.getElementById("famt3").value=sum;
				var famt2=parseFloat(document.getElementById("famt2").value);
				document.getElementById("famt4").value= roundNumber( (parseFloat(sum)*100000/(parseFloat(famt2)*1000)),2);
				var famt5=document.getElementById("famt5").value;
				document.getElementById("famt6").value=parseFloat(famt5)-parseFloat(sum);
				 
			}
			function rld_(a)
			{
				document.getElementById("dataflag").value=a;
				transaction(4,1);
			}
						
			function exp2()
			{
			
				var t=0;
				var sum=0;
				sum=document.getElementById("famt3").value;
				var famt2=parseFloat(document.getElementById("famt2").value);
				document.getElementById("famt4").value= roundNumber( (parseFloat(sum)*100000/(parseFloat(famt2)*1000)),2);
				var famt5=document.getElementById("famt5").value;
				document.getElementById("famt6").value=parseFloat(famt5)-parseFloat(sum);
				 
			}
			</script>
			</head>
 			 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
  			<script type="text/javascript" src="../scripts/transcat.js"></script>
  			<script type="text/javascript" src="../scripts/reports.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
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
			
			String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO", 
					 " where  sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and  SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by sch_sno","sch_sno","","class='select' style='width: 262'  " );
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String flag_c=obj.setValue("flag_c",request);
			 int year_incr=0;
			  if (month <4)
				  year_incr=0;    
			  else
				  year_incr=1;
			  int prvfinyear=obj.prvfinyear(year,month); 
			%>			 
			<form action="" > 
			<table width="100%"  style="border-collapse: collapse;border-color: skyblue" align="center" border=1 cellpadding="5" cellspacing="0">
			 <tr> <th colspan="3" align="center"  class="tdH">Scheme Performance For Financial Year <label id='msg'></label></th></tr>
			 <tr> <td width="25%"><label class="lbl">Division Office Name :</label></td><td align="left" colspan="2"> <label class="lbl"><%=Office_Name%></label></td></tr>
			 			  	
			 <tr> 
	         <td><label class="lbl">Financial Year</label> </td>
			 <td colspan="3">
			 <select class="select"  id="pyear" name="pyear"  style="width:100pt;" >
			 		 <option value="0">--Select Year--</option>
			 		 <%
                	 for (int j=year-1;j<=year+year_incr;j++)
                	 { 
                	 	if (j-1==prvfinyear)
                		{
                		%>    
                		<option value="<%=j-1%>-<%=j%>" selected><%=j-1%>-<%=j%></option>
                		<%	}else{
                			%>    
                    		<option value="<%=j-1%>-<%=j%>"><%=j-1%>-<%=j%></option>
                    		<%
                			}
                	} %>
                	</select> 
			  </td>
			  </tr>
			 
			 <tr><td width="25%"><label class="lbl">Scheme  Name : </label></td><td align="left" colspan="2"> <%=sch%></td></tr>
			 <tr class="tdH">
			 	<th align="center" colspan="4"> 
	 					<input type="button" value="Report" onclick="report(8)" class="btn" >
						<input type="button" value="Exit"  class="btn" onclick="window.close()"/> 
				</th>
			</tr>
			</table>
			<input type="hidden" id="flow_" name="flow_" value="2"/>  
	</form>
	<input type="hidden" id="row_count" name="row_count" value="0"/>
	<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
	<input type="hidden" id="flag_c" name="flag_c" value="<%=flag_c%>"/>
				<%}catch(Exception e) 
				{
				response.sendRedirect("../../../../../index.jsp");
				} %>
				<input type="hidden" id="pr_status" name="pr_status" value="1"/>
			</body>
		</html>