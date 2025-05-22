
		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle"%>
 			<%@ page session="false" contentType="text/html;charset=windows-1252" errorPage="../../../../../error.jsp"%>
 			<%@page import="java.util.Calendar" %>
 			<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Annual Maintenance / Budget Estimate Entry - Edit</title>
			<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 800,700 )
			} 
			function frameclose()
			 { 
			 document.getElementById("test").src = "";
			 
			 //	var s=window.open("other_charges_collection.jsp");
			 }
			 function billdisplay()
			 { 
			 document.getElementById("test").src = "exp_break_up.jsp";
			 
			 //	var s=window.open("other_charges_collection.jsp");
			 }
			</script>   
			</head>
			
			 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
  			<script type="text/javascript" src="../scripts/transcat.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
 			
 			<%  
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
			String row=obj.setValue("row",request); 
			String desc_Sno=obj.setValue("desc",request);
			String pyear=obj.setValue("pyear",request);
			String pmonth=obj.setValue("pmonth",request);
			String sch=obj.setValue("sch_sno",request);
			String MAIN_ITEM_SNO=obj.setValue("MAIN_ITEM_SNO",request);
			String SUB_ITEM_SNO=obj.setValue("SUB_ITEM_SNO",request);
			String GROUP_SNO=obj.setValue("GROUP_SNO",request);
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String desc=obj.getValue("PMS_AME_MST_MAIN_ITEM", "MAIN_ITEM_DESC","where MAIN_ITEM_SNO="+desc_Sno+ "  ");
			String sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+sch);
 			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
 			
 			
 			
 			
 			String PMS_WB_BILL_DEDNS="";
 			try {
 			 PMS_WB_BILL_DEDNS=obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cond","");
 			}catch(Exception e) { out.println(e);}
 			String aggr_contract="  WHERE TENDER_SNO IN " +
 			" (SELECT TENDER_SNO FROM PMS_TENDER_SCHEME_DETAILS WHERE sch_sno=41     )";
 		//	String aggrcon_combo=obj.combo_str("PMS_TENDER_AGREEMENT","AGREE_REF_NO","AGREE_SNO",aggr_contract,"cond","");
 			String aggrcon_combo="<select id='cond'><option value=0>Select Aggrement </option> <option value=1>TWAD/2011/123</option><option value=2>B</option></select>";
 			String condcombo="<select id='cond'> <option value=0>Select Contractor</option><option value=1>Raja</option><option value=2>Babu</option><option value=3>Balaji</option><option value=4>Gokul</option></select>";
 			
 			String flag_c="Y";
			%>

 			
 			
 			 
			<body onload="transaction(3,14),toolout('dv1')">
			<form>
			<table id="etable"   align="center" width="100%" border="0" cellspacing="0" cellpadding="1">
			<tr class="tdH">
				<td  align="center" colspan="3"><label class="lbl"><%=Office_Name%></label></td>
			</tr>	
			<tr class="tdH">
				<td  align="left">
					<label class="lbl"> Month : &nbsp;&nbsp;<%=monthArr[Integer.parseInt(pmonth)]%>  &nbsp;&nbsp; Year :&nbsp;&nbsp;<%=pyear%> </label>
				</td>
				<td  align="left" width="70%" colspan="2" >
					<label class="lbl">  Scheme Name :  &nbsp;&nbsp; <%=sch_name%>&nbsp;&nbsp; </label>
				</td>
			</tr> 
			</table>
			
			
			
			 
			 
				<!-- <tr><td align="center"> <label id='msg'></label>
					<input type="button" value="Save" class="btn" onclick="transaction(2,1)"/><input type="Reset" value="Reset" class="btn" />				 
					<input type="button" value="Close"  class="btn" onclick="window.close()"/> 
				</td></tr>
				 --> 
			 
			 
			<table    border="0"  align="center" width="100%"  cellspacing="1" cellpadding="1">
			<tr>
			<td> 
				<table id="etable"     align="center" width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr><td align="center" colspan="2">						
					<table id="etablesub"   border="1"  align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
						<tbody id="edatasub" align="center" valign="top"/>
					</table> 
					</td>
				</tr>
				
				</table>
			</td>  
			</tr>
			<tr class="tdH">
					<td   align="center">
						  
						<input type=button class="btn" value="Submit" onclick="transaction(2,10)"/>
						<input  type=button value="Exit"  class="btn" onclick="window.close()" />
					</td>
				</tr>
			<tr> 
			<td  colspan="4">
				<iframe  frameborder="0" allowtransparency="true" id='test' align="centre"  style="width: 100%;height:10cm;scrollbar-face-color:#9898D0;">
				 </iframe>  </td> 
			</tr>    
			</table>
			<input type="hidden" id="sch_sno" name="sch_sno" value="<%=sch%>"/>
			<input type="hidden" id="pyear" name="pyear" value="<%=pyear%>"/>
			<input type="hidden" id="pmonth" name="pmonth" value="<%=pmonth%>"/>
			<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
			<input type="hidden" id="flag_c" name="flag_c" value="<%=flag_c%>"/>
			<input type="hidden" id="pr_status" name="pr_status" value="0"/>
			<div id='dv1' style="height: 20;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" >Click Here Bill Wise Break up</div>
		</form>
				</body>
		</html>  
				