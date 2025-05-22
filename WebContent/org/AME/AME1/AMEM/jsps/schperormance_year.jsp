		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle" %>
 			<%@ page session="false" contentType="text/html;charset=windows-1252" %>
 			<%@ page import="java.util.Calendar" %>
 			<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>			
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
			<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
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
			<body onload="page_size(),transaction(2,16),flash()"> 
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
			if (userid == null) 
			{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
			
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			
			 String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO", 
				 	 " where   sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by sch_sno","sch_sno","","class='select' style='width: 262'  onchange='transaction(2,19)'" );
			String sch_sno=obj.setValue("sch_sno",request);
			//String sch= obj.getValue("PMS_SCH_MASTER", "SCH_NAME", " where SCH_SNO="+sch_sno);
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String flag_c=obj.setValue("flag_c",request);
			int year_incr=0;
			  if (month <4)
				  year_incr=0;    
			  else
				  year_incr=1; 
			%>
			 
			<form action="" >
			<table width="50%" style="border-collapse: collapse;border-color: skyblue"  align="center" border=1 cellpadding="5" cellspacing="0">
			 <tr> <th colspan="3" align="center"  class="tdH">Scheme Performance For Previous Financial Year - (New)<label id='msg'></label></th></tr>
			 <tr> <td width="25%"><label class="fnt">Division Office Name :</label></td><td align="left" colspan="2"> <label class="fnt"><%=Office_Name%></label></td></tr>
			 			  	
			 <tr>  
	         <td><label class="fnt">Financial Year</label> </td>
			 <td colspan="3"><select class="select"  id="pyear" name="pyear"  style="width:100pt;" onchange="">
			 				 <option value="0">Select</option>
			  				<%
							for (int i = year-2; i <= year+year_incr; i++) 
							{
							%>
							<option value="<%=i-1%>-<%=i%>"><%=i-1%>-<%=i%></option>
							<%
							}
							%>
			  				</select> 
			  </td>
			  </tr>
			 
			 <tr> <td width="25%"><label class="fnt">Scheme  Name : </label></td><td align="left" colspan="2"> <%=sch%><input type=button value="GO" onclick="transaction(2,13)"> </td></tr>
			 
			    
			  <tr> <td  align="right" colspan="3"> </td></tr>
			  </table>	
				<table id="etable"  style="border-collapse: collapse;border-color: skyblue" align="center" width="50%" border="1" cellspacing="0" cellpadding="1">
					
					 
					<tr><td align="center">						
					<table id="etable"  style="border-collapse: collapse;border-color: skyblue"  align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
						
		 				<tbody id="edata" align="center" valign="top"/>		 			 
		 				
			      	</table>
				</td></tr>
				
				
				 
				</table>
				<table id="enttable" style="BORDER-COLLAPSE: collapse" class="table" align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
						
		 				<tbody id="entdata" align="center" valign="top"/>		 			 
		 				
			</table>
	<table  style="border-collapse: collapse;border-color: skyblue" width="100%" align="center" border="1" cellpadding="5" id="etable" >
           	<!--  <tr class="tdH"> 
           
                <th class="fnt" width="10%">SELECT</th>
                <th class="fnt">Quantity of Water to be supplied as per design </th>
                <th class="fnt" width="10%">Quantity of Water actually Pumped  </th>
                <th class="fnt" width="10%">Quantity of Water actually Supplied </th>
                <th class="fnt" width="10%">Maintenance cost of the scheme per KL </th>
                <th class="fnt" width="10%">Actual Expenditure incurred  </th>
                <th class="fnt" width="10%">Demand Raised With Beneficiaries </th>
                <th class="fnt" width="10%">Profit/Loss In maintaining the Scheme</th>
                <th class="fnt" width="10%">Reason for the loss/higher maintenance cost</th>
            </tr>-->
                <tbody id="edata1" align="left" valign="top"> </tbody>
	           <tr><th align="center">
						<input type="hidden" id="dataflag" name="dataflag" value="1"/>   
						<input type="button" value="Update" class="btn" onclick="rld_(2)"/>
						<input type="button" value="Add" id="save" class="btn" onclick="rld_(1)"/>
						<input type="button" value="Profit/Loss" class="btn" onclick="exp2('famt8','famt2','famt9','famt10','famt11')"/>  
						<input type="button" value="Delete" class="btn" id="delete" onclick="transaction(2,4)" />
						<input type="button" value="Report" onclick="report(8)" class="btn"/>
	 					<input type="reset" value="Clear" class="btn" />				 
						<input type="button" value="EXIT" class="btn" onclick="window.close()"/> 
					</th></tr>
            </table>   
		 <input type="hidden" id="sch_sno" name="sch_sno" value="<%=sch_sno%>"/>
				<input type="hidden" id="flow_" name="flow_" value="2"/>  
				</form>
				
				<input type="hidden" id="row_count" name="row_count" value="0"/>
					<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
				<input type="hidden" id="flag_c" name="flag_c" value="Y"/>
				<%}catch(Exception e) { 
				
			 
				response.sendRedirect("../../../../../index.jsp");
				
				} %>
				<input type="hidden" id="pr_status" name="pr_status" value="1"/>
				
			</body>
		</html>  
