
		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle" errorPage="../../../../../error.jsp"%>
 			<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 			<%@page import="java.util.Calendar"  %>
 			<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Scheme Design Quantity Master</title>
			 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
			<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 700,700 )
			} 
			function lengthcount(a)
			{
				var year_length=new String(a.value);
				if (year_length.length < 4)
				{
					alert("Please Enter  valid year");
					var id=document.getElementById("yearcomm");
					document.getElementById('yearcomm').focus();           
				}
				
				
			}
			 function breakup(a)
			 {
			  var sch_sno=document.getElementById("sch_sno").value;
			 	var win=window.open("ben_scheme_desg.jsp?sch_sno="+sch_sno,"Breakup","status=1,height=900,width=1400,resizable=YES, scrollbars=yes");;
			 }
			</script>
			</head>
			 			 
			   			<script type="text/javascript" src="../scripts/transcat.js"></script>
			 <script type="text/javascript" src="../scripts/numeral.js"></script>
  			<script type="text/javascript" src="../scripts/master.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<body onload="page_size(),master(5,3);document.getElementById('sch_sno').focus(),toolout('dv')">
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
			
			String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME",  "SCH_SNO", 
					 " where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")  and   sch_sno in (select scheme_sno from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and office_id="+Office_id+") order by SCH_NAME","sch_sno","","class='select' style='width: 262'   onKeyDown=key_pass(event,'sch_sno','yearcomm',1)" );
			
			String sch1=obj.combo_str("PMS_MST_PROJECTS_VIEW","PROJECT_NAME","PROJECT_ID", 
					 " where     OFFICE_ID="+Office_id+"  order by PROJECT_NAME ","sch_sno1","","class='select' style='width: 262'   onKeyDown=key_pass(event,'sch_sno','yearcomm',1)" );
			
			
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

			String rate=obj.getValue("PMS_AME_MST_CENTAGE","CENTAGE"," where ACTIVE_STATUS='A'");
			
			   
 			obj=null;
			
			%>
			 
				<form action="" name="f1"> 
					<table width="100%" class="table" align="center" cellpadding="5" cellspacing="0" >
						 <tr> <td colspan="2" class="tdH" align="center">Scheme AME details</td></tr>
						 <tr> <td width="40%"><label class="lbl">Division Office Name :</label></td><td align="left"> <label class="lbl"><%=Office_Name%></label></td></tr>

					 	<tr> <td width="40%"><label class="lbl">Scheme  Name : </label></td><td align="left"> <%=sch%></td></tr>
					  
					 	
					 	<tr> <td width="40%"><label class="lbl">Year of Commissioning : </label></td><td align="left"> 
					 	
					 		<input type="text"  maxlength="4" size="4" id="yearcomm" name="yearcomm"  onkeyup="isInteger(this,9,event,'yearcomm');" onKeyDown="key_pass(event,'yearcomm','pumphours')" onblur="lengthcount(this)"> 
					 	</td></tr> 
					 	
					 	<tr> <td width="40%"><label class="lbl">Designed Pumping Hours  </label></td><td align="left"> <input type="text" maxlength="2" size="2"  id="pumphours" name="pumphours"  onkeyup="isInteger(this,9,event,'pumphours');" onKeyDown="key_pass(event,'pumphours','sch_qty')"> </td></tr>
   
						<tr> <td class="lbl">Scheme Design Quantity (MLD)  </td>
							 <td><input class="lbl" type=text id='sch_qty' onmouseover="toolin(event,'dv')" onmouseout="toolout('dv')" name='sch_qty'  onkeyup="isInteger(this,9,event,'sch_qty'); digit_control('sch_qty', 3)"  maxlength="8" size="8"  onKeyDown="key_pass(event,'sch_qty','noofstaff')">
							 <!--  <a href="javascript:breakup('sch_qty')">  Beneficiaries Wise Breakup -->						 
							 </td> </tr>						
							<tr> <td width="40%"><label class="lbl">Total Number of Operating Staff Sanctioned </label></td><td align="left"> <input type="text" maxlength="4" size="4"  id="noofstaff" name="noofstaff" onkeyup="isInteger(this,9,event,'noofstaff');" onKeyDown="key_pass(event,'noofstaff','noofhab')" /></td></tr>
							
								<tr> <td width="40%"><label class="lbl">Total Number of Habitations  </label></td><td align="left"><input type="text" maxlength="4" size="4"  id="noofhab" name="noofhab"  onkeyup="isInteger(this,9,event,'noofhab');" onKeyDown="key_pass(event,'noofhab','cmdadd')"/></td></tr>
						  		<!-- <tr>
						  			<td>FAS Scheme</td>
						  			<td><%=sch1%></td>
						  		</tr> -->
								<tr><td align="center" colspan="2">  
								<input type="button" value="Add" class="btn" id="cmdadd" onclick="master(5,1)"  />		
								<input type="button" value="Update" class="btn" id="cmdupdate" onclick="master(5,2)"  />
								<input type="button" value="Delete" class="btn" id="cmdupdate" onclick="master(5,7)"  />
								<input type="reset" value="Clear"  class="btn" />   
								<input type="button" value="HELP" onclick="javascript:window.open('./twad_note.html#m6','_blank','toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=yes, width=500, height=500');">
								<input type="button" value="Exit"  class="btn" onclick="window.close()"/>
	 
					
				</td></tr> 
			    
					</table>	
					<table id="etable"  align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
				<tr class="tdH">
					<th class="tdText" width="2%">Sl No. </th>
					<th class="tdText"   >Edit</th>
					<th class="tdText"   >Scheme Name</th>  
					<th class="tdText"  width="10%">Design <BR> Quantity(MLD)</th>
					<th class="tdText"  width="10%">Year of<BR>  Commissioning</th>
					<th class="tdText"  width="10%">Designed <BR> Pumping<BR>  Hours</th>					
					<th class="tdText"  width="10%">Total Number <BR> of Operating Staff</th>
					<th class="tdText"  width="10%">Total Number <BR> of Habitation</th>
					 
				</tr>
		 				<tbody id="edata" align="center" valign="top"/>		 			 
		 				
			      	</table>
 				<input type="hidden" name="key_value" id="key_value" value=""> 
				<div id='dv' style="height: 100;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" >Enter Scheme Earmarked Qty in MLD  <Br> as shown in Month wise Average Report <Br> (Refer URL http://218.248.23.4/maint-projects) TWAD,HO Daily P.R module 
				</div>
				<input type="hidden" id="SCH_DETAILS_SNO" name="SCH_DETAILS_SNO" value="0"/>
				<input type="hidden" id="rowcount" name="rowcount" value="0"/>
				</form>    
				<% }catch(Exception e)  {} %>
			</body>
		</html>  
