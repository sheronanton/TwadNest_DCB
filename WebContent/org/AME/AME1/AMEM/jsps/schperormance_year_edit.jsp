
		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle" %>
 			<%@ page session="false" contentType="text/html;charset=windows-1252" %>
 			<%@ page import="java.util.Calendar" %>
 			<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
			<title> Scheme Performance </title>
			<script type="text/javascript">
			function page_size()
			{
				window.resizeTo( 800,700 )
			}
			function divclr()
			{
				var tbody = document.getElementById("edatasub");
				var table = document.getElementById("etablesub");
				var t=0;
				for(t=tbody.rows.length-1;t>=0;t--)
				{
				tbody.deleteRow(0);
				}
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
				divclr();
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
				divclr();
			} 
			</script>
			</head> 			 
  			<script type="text/javascript" src="../scripts/transcat.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<body onload="page_size(),transaction(2,5),flash()">
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
					 " where   sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by sch_sno","sch_sno","","class='select' style='width: 262'" );
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String flag_c=obj.setValue("flag_c",request);
			%>
			 
			<form action="" >
			<table width="100%" class="tab" align="center" border=1 cellpadding="5" cellspacing="0">
			 <tr> <th colspan="3" align="center">Scheme Performance For Financial Year - (Edit)  <label id='msg'></label></th></tr>
			 <tr> <td width="25%"><label class="lbl">Division Office Name :</label></td><td align="left" colspan="2"> <label class="lbl"><%=Office_Name%></label></td></tr>
			 <tr> <td width="25%"><label class="lbl">Scheme  Name : </label></td><td align="left" colspan="2"> <%=sch%></td></tr>
			 
			  	
			 <tr>
	         <td><label class="lbl">Financial Year</label> </td>
			 <td colspan="3"><select class="select"  id="pyear" name="pyear"  style="width:75pt;"  onchange="transaction(2,13) " >
			 				 <option value="0">Select</option>
			  				<%
			  				  for (int i=2010;i<=year;i++)
			  					{
			     		    %>
			  				 <option value="<%=i%>-<%=i+1%>"><%=i%>-<%=i+1%></option>
			  				<%} %>
			  				</select> 
			  </td>
			  </tr>
			 
			   
			  
			   
			 
			    
			 
			  </table>	
				<table id="etable" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
					
					 
					<tr><td align="center">						
					<table id="etable" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
						
		 				<tbody id="edata" align="center" valign="top"/>		 			 
		 				
			      	</table>
				</td></tr>
				<tr><th align="center">
					<input type="hidden" value="Update" id="save" class="btn" onclick=" transaction(4,1)"/>
					<input type="button" value="Delete"  class="btn" onclick="transaction(2,4)"/> 	
					<input type="Reset" value="Reset" class="btn" />				 
					<input type="button" value="Close"  class="btn" onclick="window.close()"/> 
				</th></tr>
				</table> 
				<table id="enttable" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
						
		 				<tbody id="entdata" align="center" valign="top"/>		 			 
		 				
			</table>
			<table   style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
			<tr><td >
				<table id="etable" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
					
					 
					 
				
				</table>
				</td>
				</tr>
				</table>
				<input type="hidden" id="flow_" name="flow_" value="2"/>  
				</form>
				<input type="hidden" id="flag_c" name="flag_c" value="Y"/>
				<input type="hidden" id="row_count" name="row_count" value="0"/>
					<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
				<input type="hidden" id="flag_c" name="flag_c" value="<%=flag_c%>"/>
				<%}catch(Exception e) {
				
			 
				response.sendRedirect("../../../../../index.jsp");
				
				} %>
				<input type="hidden" id="pr_status" name="pr_status" value="1"/>
				<input type="hidden" id="dataflag" name="dataflag" value="2"/>   
			</body>
		</html>  
