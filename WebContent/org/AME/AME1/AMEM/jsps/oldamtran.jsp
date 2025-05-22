
		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle"  %>
 			<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 			<%@page import="java.util.Calendar"  %>
 			<%@page import="java.util.GregorianCalendar"  %>
 			<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Annual Maintenance / Budget Estimate Entry</title>
			<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 700,800 )
			} 

			  function clr()
			  {  
				  var rowcount = document.getElementById("rowcount").value;
				  try
				  {		 
				  		var i=0;
				 		for (i = 1; i <= parseInt(rowcount); i++)					 		 
				 		{
					  		document.getElementById("famt"+i).value="";
				  		}
				 		 document.getElementById("pyear").value=0;
				 		 
						try
						{
							document.getElementById("totalcentage").value="";
							document.getElementById("grandtotal").value="";
						}catch(e) {}
				 		
				  }catch(e)    
				  {
						 
				   }  
				}
			</script>
			<script type="text/javascript" src="../scripts/transcat.js"></script>
  			<script type="text/javascript" src="../scripts/reports.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>  
			</head>
			 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
			<body onload="page_size(),toolout('dv')">
			<%  
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			Controller obj=new Controller();
			if (userid == null) {
			//	response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String sch_sno=obj.setValue("sch_sno",request);
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			
			String sch= obj.getValue("PMS_SCH_MASTER", "SCH_NAME", " where SCH_SNO="+sch_sno);
			//obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")","sch_sno",sch_sno,"class='select' style='width: 262;' disabled='disabled'  font-weight: bold;   '" );
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String rate=obj.getValue("PMS_AME_MST_CENTAGE","CENTAGE"," where ACTIVE_STATUS='A' and SCH_SNO="+sch_sno);
			String rate_msg=" ";
		/*	if (!rate.equalsIgnoreCase("0"))	
			rate_msg="   Centage of this Scheme is "+rate+"%";
			else
			rate_msg=" <font color=red>please enter centage rate for this scheme</font>";
			*/
			int year_incr=0;
			  if (month <4)
				  year_incr=0;    
			  else
				  year_incr=1; 
			%>
			 
				<form action="" >
					<table width="100%"  style="border-collapse: collapse;border-color: skyblue" align="center" border=1 cellpadding="5" cellspacing="1" >
						 <tr> <td colspan="2" class=tdH align="center">Annual Maintenance Estimate Entry</td></tr>
						 <tr> <td width="25%"><label class="fnt">Division Office Name :</label></td><td align="left"> <label class="lbl"><%=Office_Name%></label></td></tr>
						 <tr>
	         			 <td><label class="fnt"> Financial Year</label> </td>
	          			<Td colspan="2">
	          			
	          			<!-- <select class="select"  id="pyear" name="pyear"  style="width:120pt;  " onchange='transaction(1,5),transaction(1,3)'> -->
	          			<select class="select"  id="pyear" name="pyear"  style="width:120pt;  " onchange='transaction(1,5)'>
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
			  </tD>
			  </tr>
					 	<tr> <td width="25%"><label class="fnt">Scheme  Name   </label></td><td align="left"><table width=100%><tr><td align="left"> <%=sch%></td><td align="right"><%=rate_msg%></td></tr> </table></td></tr>
					</table>	 
					<table id="etable"   border="1"   align="center" width="100%" border="1" cellspacing="1" cellpadding="5"  style="border-collapse: collapse;border-color: skyblue" >
					
					<tr><td align="right"><font size=3 color="red">(Rs. in lakhs)</font></td></tr>
					<tr><td align="center">						
					<table id="etable"  border="1" align="center" width="100%"  cellspacing="1" cellpadding="1"  style="border-collapse: collapse;border-color: skyblue" >
						
		 				<tbody id="edata" align="center" valign="top"/>		 			 
		 				
			      	</table>
				</td></tr>  
				<tr><td align="left"><font size=3 color=blue class="fnt"> Note 1.&nbsp; Items highlighted in green included for centage calculation </font> </td></tr>
				<tr><td align="left"><font size=3 color=blue class="fnt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.&nbsp;Any amount modification done in items included for centage calculation,please click Calculate Centage before clicking Update.</font> </td></tr>
				<tr class="tdH"><td align="center">
					<input type="button" value="ADD" class="btn" id="add" onclick="transaction(1,1)"  />
				 	<input type="button" value="UPDATE" class="btn" onclick="transaction(1,2)"  /> 
					<input type="button" value=" CALCULATE CENTAGE" class="btn" onclick="centage()">
					<input type="button" value="DELETE" class="btn" id="delete" onclick="transaction(1,4)"/>
					
					<input type="button" value="CLEAR"  class="btn" onclick="clr()" />							
					<input type="button" value="REPORT" class="btn" onclick="report(6)"/>
					<input type="button" value="EXIT"  class="btn" onclick="window.close()"/>
					<input type="button" value="HELP" onclick="javascript:window.open('./twad_note.html#a1','_blank','toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=yes, width=600, height=800');">
	 
					 
				</td></tr>
				</table><table id="etable4"  border="1" align="center" width="100%"  cellspacing="1" cellpadding="1">
						
		 				<tbody id="edata3" align="center" valign="top"/>		 			 
		 				
			      	</table>
				<table class="table" width="100%" align="center" border="1" cellpadding="5" id="etable">
			 

		<tbody id="edata1" align="left" valign="top">  
    </tbody></table>
				<input type="hidden" id="pr_status" name="pr_status" value="0"/>
				<input type="hidden" id="rowcount" name="rowcount" value="0"/>
				<input type="hidden" id="rate" name="rate" value="<%=rate%>"/>
				 <input type="hidden" id="flag_new" name="flag_new" value="0"/>
				<input type="hidden" id="sch_sno" name="sch_sno" value="<%=sch_sno%>"/>
				
				
				 				<div id='dv' style="height: 20;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" >Click Here For Centage Calculation</div>
				 
				</form>  
				  
				
			</body>
		</html>  
