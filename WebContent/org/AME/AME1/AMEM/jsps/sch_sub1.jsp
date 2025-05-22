<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle"%>
 			<%@ page session="false" contentType="text/html;charset=windows-1252" errorPage="../../../../../error.jsp"%>
 			<%@ page import="java.util.Calendar" %>
 			<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
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
			 function ext()
			 {
				 var s=new Boolean(confirm ("Do You want To exit ? "))
				 if (s==true)
				 {
					 alert("Expenditure Details will not be saved !!! ");
					 try  
					 {  
					 	transaction(5,9);
					 }catch(e) {}  
					 window.close();    
				 }
			 } 
			</script>   
			</head>
				<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
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
 			PMS_WB_BILL_DEDNS=""; 
 			//  PMS_WB_BILL_DEDNS=obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cond","");
 			}catch(Exception e) { out.println(e);}
 			String aggr_contract="  WHERE TENDER_SNO IN " +
 			" (SELECT TENDER_SNO FROM PMS_TENDER_SCHEME_DETAILS WHERE sch_sno="+sch+"    )";
 		//	String aggrcon_combo=obj.combo_str("PMS_TENDER_AGREEMENT","AGREE_REF_NO","AGREE_SNO",aggr_contract,"cond","");
 			String aggrcon_combo="<select id='cond'><option value=0>Select Aggrement </option> <option value=1>TWAD/2011/123</option><option value=2>B</option></select>";
 			String condcombo="<select id='cond'> <option value=0>Select Contractor</option><option value=1>Raja</option><option value=2>Babu</option><option value=3>Balaji</option><option value=4>Gokul</option></select>";
 			String flag_c="Y";
 			
 			String head1="";
 			String head2="";
 			String head1_amount="";
 			String head2_amount="";
 			try
 			{  
 				String pid=obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_ID"," where SCH_SNO="+sch);   	

 			 head1=obj.getValue("COM_MST_ACCOUNT_HEADS", "ACCOUNT_HEAD_DESC","where ACCOUNT_HEAD_CODE=211906");;
 			 head2=obj.getValue("COM_MST_ACCOUNT_HEADS", "ACCOUNT_HEAD_DESC","where ACCOUNT_HEAD_CODE=212006");;
 			 String cond=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear 
		 			+" and CASHBOOK_MONTH="+pmonth+" and SUB_LEDGER_TYPE_CODE= 10 " 
		 			+" and ACCOUNT_HEAD_CODE in (222102,222103,222104,222105,222106,222107,222108,222109,222111) and VOUCHER_NO in ( " 
	    			+" select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
	    			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+" and JOURNAL_TYPE_CODE =11 " 
	      			+" and JOURNAL_STATUS='L')"; 			 
 			 head1_amount=obj.getValue("FAS_JOURNAL_TRANSACTION", "ROUND(( sum(AMOUNT)*(2/100) ) /100000,2) ","val1",cond); 		 
 			 head2_amount=obj.getValue("FAS_JOURNAL_TRANSACTION", "ROUND(( sum(AMOUNT)*(1/100) ) / 100000 ,2)","val2",cond); 
 			}catch(Exception e) {
 				out.println(e);
 			} 			
			%> 
			<body onload="  transaction(3,5),toolout('dv1')">
			<form  action="../../../../../PMS_AME_SCH_MONTHLY" method="get">
			<table id="etable"  style="border-collapse: collapse;border-color: skyblue"   align="center" width="100%" border="0" cellspacing="0" cellpadding="1">
			<tr class="tdH">
			<th  align="center" colspan="3"><label class="lbl">Actual Item Wise Expenditure</label></th>
			</Tr>
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
			<table    style="border-collapse: collapse;border-color: skyblue"  border="0"  align="center" width="100%"  cellspacing="1" cellpadding="1">
			<tr>
			<td> 
				<table id="etable"  style="border-collapse: collapse;border-color: skyblue"     align="center" width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr><td align="center" colspan="2">						
					<table id="etablesub"   border="1"   style="border-collapse: collapse;border-color: skyblue"  align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
						<tbody id="edatasub" align="center" valign="top"/>
					</table> 
					</td>
			 	</tr>
			 	<tr class="tdH">
					<td   align="center">
						  
						<input type=hidden class="btn" value="Submit" onclick="transaction(2,10)"/>
						<input  type=button value="Exit"  class="btn" onclick="ext()" />
					</td>
				</tr>
				<tr>     
				<td  colspan="4">
					<iframe  frameborder="0" allowtransparency="true" id='test' align="centre"  style="width: 100%;height:10cm;scrollbar-face-color:#9898D0;">
				 	</iframe>
				</td> 
			</tr>
			</table>
			</td> 
			</tr>
			</table>
			<input type="hidden" name="" value="submit">
				<input type="hidden" id="sch_sno" name="sch_sno" value="<%=sch%>"/>
				<input type="hidden" id="pyear" name="pyear" value="<%=pyear%>"/>
				<input type="hidden" id="pmonth" name="pmonth" value="<%=pmonth%>"/>
				<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
				<input type="hidden" id="flag_c" name="flag_c" value="<%=flag_c%>"/>
				<input type="hidden" id="pr_status" name="pr_status" value="0"/>
				<div id='dv1' style="height: 20;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" ></div>
				</form>			
			</body>			
		</html> 			