
<%@page import="java.sql.ResultSet,java.sql.Connection"%><html>
<head>
<%@page import="java.util.*"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Actual Expenditure BreakUp</title>
 
 
<%  
			int frow=0;
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			
			Controller obj=new Controller();
			Controller obj2=new Controller();
			Connection con=obj.con();
			Connection con1=obj2.con();
			obj2.createStatement(con1); 
			obj.createStatement(con);  
			if (userid == null) {

				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String row="1";
			String erow=obj.setValue("erow",request);
			String desc_Sno=obj.setValue("desc",request);  
			String pyear=obj.setValue("pyear",request);
			String pmonth=obj.setValue("pmonth",request);
			String sch=obj.setValue("sch_sno",request);
			
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String desc=obj.getValue("PMS_AME_MST_MAIN_ITEM", "MAIN_ITEM_DESC","where MAIN_ITEM_SNO="+desc_Sno+ "  ");
			String sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+sch);
 			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
 			
 			String PMS_WB_BILL_DEDNS="",PMS_WB_BILL_DEDNS1="";
 			try {
 				PMS_WB_BILL_DEDNS1="";
 		 	 // PMS_WB_BILL_DEDNS=obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cb1","");
 			  //PMS_WB_BILL_DEDNS1=obj.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cb#","");
 			}catch(Exception e)
 			{
 				 
 				
 			}
			String MAIN_ITEM_SNO=obj.setValue("MAIN_ITEM_SNO",request);
			String SUB_ITEM_SNO=obj.setValue("SUB_ITEM_SNO",request);
			String GROUP_SNO=obj.setValue("GROUP_SNO",request);
			
 			String aggr_contract="  WHERE TENDER_SNO IN " + 
 			" (SELECT TENDER_SNO FROM PMS_TENDER_SCHEME_DETAILS WHERE sch_sno="+sch+" )";
 			// String aggrcon_combo=obj.combo_str("PMS_TENDER_AGREEMENT","AGREE_REF_NO","AGREE_SNO","","cond","onchange=transaction(3,12)");
 			String aggrcon_combo="<select id='cond'><option value=0>Select Aggrement </option> <option value=1>TWAD/2011/123</option><option value=2>B</option></select>";
 			String condcombo="<select id='cb1' name='cb1' > <option value=0>Select Contractor</option><option value=1>Raja</option><option value=2>Babu</option><option value=3>Balaji</option><option value=4>Gokul</option></select>";
 			
 			// Mapping New Scheme 
			
 			
 			int count=obj.getCount("PMS_AME_MST_SCH_MAPPING"," where SCH_SNO="+sch +" and OFFICE_ID="+Office_id);
 			Hashtable ht=new Hashtable();  
 			if (count==0)
 			{ 
 				
 				int max=0;
 				 try
 				 {
 					 max=obj.getMax("PMS_AME_MST_SCH_MAPPING","SCH_MAPPING_SNO","");
 				 }catch(Exception e)
 				 {
 					 
 					 max=obj.getMax("PMS_AME_MST_SCH_MAPPING","SCH_MAPPING_SNO","");
 				 }
 				 String pr_id=obj.getValue("PMS_SCH_MASTER","PROJECT_ID"," where SCH_SNO="+sch);    
 			 
 				 
 				 try  
				 {
 				 	ht.put("SCH_MAPPING_SNO" ,max);	  
 				 	ht.put("SCH_SNO" ,sch);	
 				 	ht.put("OFFICE_ID" ,Office_id);  
 				 	ht.put("UPDATED_BY_USER_ID" , "'"+userid+"'");
 				 	ht.put("UPDATED_TIME" ,"clock_timestamp()");
 				 	ht.put("TWAD_PROJECT_ID" ,"0");  
 				 	ht.put("FAS_PROJECT_ID" ,pr_id);
 				 	int urow=obj.recordSave(ht,"PMS_AME_MST_SCH_MAPPING",con);
				 }catch(Exception e)
 				 { }  	
 			}
 			
 			String ame_project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING","FAS_PROJECT_ID"," where SCH_SNO="+sch +" and OFFICE_ID="+Office_id);
 			String cont=obj.combo_str("PMS_CONT_REQUEST_REGN","CONTRACTOR_NAME","CONTRACTOR_ID"," where OFFICE_ID="+Office_id,"cb1","","");
 			
			String pid=ame_project_id;//obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_ID"," where SCH_SNO="+sch);
			
			String ACCOUNT_HEAD_CODE=obj.getValue("PMS_AME_MST_ITEM_ACC_CODE","ACCOUNT_HEAD_CODE","  where MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);
			 //String qry="SELECT * FROM FAS_PAYMENT_TRANSACTION WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+" AND sub_ledger_type_code=10 and ACCOUNT_HEAD_CODE="+ACCOUNT_HEAD_CODE+" and SUB_LEDGER_CODE="+pid;
			// old 	 
			 String cond=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear 
	 			+" and CASHBOOK_MONTH="+pmonth+" and SUB_LEDGER_TYPE_CODE= 10 " 
	 			+" and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+") and VOUCHER_NO in ( " 
 			+" select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
 			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+// and JOURNAL_TYPE_CODE =11 "  
   			" and JOURNAL_STATUS='L')";
			
			 String cond_payment="   ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear 
	 			+" and CASHBOOK_MONTH="+pmonth+" and SUB_LEDGER_TYPE_CODE= 10 " 
	 			+" and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+") and VOUCHER_NO in ( " 
			+" select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+// and JOURNAL_TYPE_CODE =11 "  
			" and PAYMENT_STATUS='L')";
			
			
			
			String qry1="";
			String qry2="";
			String qry3="";
			if (!ACCOUNT_HEAD_CODE.equals("222102"))
			{
		
			}
			else
			{
				 
			}
			qry1="SELECT * FROM FAS_PAYMENT_TRANSACTION WHERE "+cond_payment+"  and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+ame_project_id;
			qry2="SELECT * FROM FAS_JOURNAL_TRANSACTION  "+cond; // WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+" AND sub_ledger_type_code=10 and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+pid;
			String ac_st=ACCOUNT_HEAD_CODE.equalsIgnoreCase("0")?"":"("+ACCOUNT_HEAD_CODE+")";
 			qry3="SELECT * FROM fas_receipt_transaction   WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"   and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+ame_project_id;
			 	 String vno="",vdate="",amt="",remarks="",billno="",billdate="",crdr="";
			 		   double net_amt=0.0;
			 		  ResultSet rs_loc=obj.getRS(qry1);  
			 		   while (rs_loc.next()) 
			 		   {
				 			frow++;
				 			vno=rs_loc.getString("VOUCHER_NO");
				 			vdate="";
				 			amt=rs_loc.getString("AMOUNT");		 			
				 			
				 			crdr=rs_loc.getString("CR_DR_INDICATOR");
				 			if (crdr.equalsIgnoreCase("CR"))
				 			net_amt-=Double.parseDouble(amt);
				 			else
				 			net_amt+=Double.parseDouble(amt);  
				 			remarks=obj.isNull(rs_loc.getString("PARTICULARS"),2);
				 			billno=obj.isNull(rs_loc.getString("BILL_NO"),2);
				 			obj.createStatement(con);
							 try
							 {  
								 billdate=obj.isNull(rs_loc.getDate("BILL_DATE").toString().split("\\ ")[0],2);					 		
				 			}catch(Exception e) 
				 			{
				 				 				
				 			}
				 			try
				 			{ 
				 			vdate=obj.getDate("FAS_PAYMENT_MASTER","PAYMENT_DATE"," where VOUCHER_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
				 			}catch(Exception e) 
				 			{  
				 				 	 				
				 			}
				   } 
			 		  rs_loc=null;
			 		  ResultSet rs_loc_pay=obj.getRS(qry2);
			 		   while (rs_loc_pay.next()) 
			 		   {
				 			frow++;
				 			vno=rs_loc_pay.getString("VOUCHER_NO");
				 			vdate="";
				 			amt=rs_loc_pay.getString("AMOUNT");
				 			
				 			crdr=rs_loc_pay.getString("CR_DR_INDICATOR");
				 			if (crdr.equalsIgnoreCase("CR"))
					 		net_amt-=Double.parseDouble(amt);
					 		else
					 		net_amt+=Double.parseDouble(amt);    
				 			
				 			remarks=obj.isNull(rs_loc_pay.getString("PARTICULARS"),2);
				 			billno=obj.isNull(rs_loc_pay.getString("BILL_NO"),2);		 			
							 try  
							 {  
								billdate=obj.isNull(rs_loc_pay.getDate("BILL_DATE").toString().split("\\ ")[0],2);					
				 			 }catch(Exception e) 
				 			 {
				 				 
				 			 }
				 			try  
							{  							
				 			vdate=obj.getDate("FAS_JOURNAL_MASTER","VOUCHER_DATE"," where VOUCHER_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
							}catch(Exception e) 
							{
								 
							}
				  }  	rs_loc_pay=null;
			 			ResultSet rs_loc_rec=null;
			 			rs_loc_rec=obj2.getRS(qry3);
			 			while (rs_loc_rec.next()) 
			 		   {
				 			frow++;
				 			vno=rs_loc_rec.getString("RECEIPT_NO");
				 			vdate="";
				 			amt=rs_loc_rec.getString("AMOUNT");
				 			crdr="CR";
				 			net_amt-=Double.parseDouble(amt);
				 			System.out.println(net_amt);
				 			remarks=obj.isNull(rs_loc_rec.getString("PARTICULARS"),2);
				 		 
				 			try
				 			{    
				 				billno=obj.isNull(rs_loc_rec.getString("BILL_NO"),2);
						 		//	billdate=obj.isNull(rs_loc_rec.getString("BILL_DATE"),2);
						 			billdate=obj.isNull(rs_loc_rec.getDate("BILL_DATE").toString().split("\\ ")[0],2);

				 			}catch(Exception e)
				 			{
				 				 
				 				
				 			}
							 try
							 { 
					 			vdate=obj.getDate("FAS_RECEIPT_MASTER","RECEIPT_DATE"," where RECEIPT_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
					 			
				 			}catch(Exception e) { out.println(e); }
				  		// PMS_WB_BILL_DEDNS1=obj2.combo_str("PMS_WB_LKP_BILL_DEDNS","BILL_DEDN_DESC","BILL_DEDN_ID","","cb"+frow,"");
			 		   }
			 			 response.getWriter().write("text" + "|" +net_amt);  
				 %>