package Servlets.AME.AME1.AMEM.servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

public class AME_BILL_WISE_AMOUNT 
{
	public double getAmount(Connection con,int Office_id,int sch,int pmonth,int pyear,int MAIN_ITEM_SNO,int SUB_ITEM_SNO,String userid)
	{
		Double net_amt=0.0;
		Controller obj_temp=new Controller();
		Controller obj=new Controller();
		Controller obj1=new Controller();
		Controller obj2=new Controller();
		Controller obj3=new Controller();
		try
		{
			obj_temp.createStatement(con);
			obj.createStatement(con);
			obj1.createStatement(con);     
			obj2.createStatement(con);
			obj3.createStatement(con);
			System.out.println("AME-------->AME_BILL_WISE_AMOUNT------sch-->(" + sch+ ")---->pmonth-->(" + pmonth + ")");

		String qry1="",qry2="",qry3="";
		String ame_project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING","FAS_PROJECT_ID"," where SCH_SNO="+sch +" and OFFICE_ID="+Office_id);
		String cont=obj.combo_str("PMS_CONT_REQUEST_REGN","CONTRACTOR_NAME","CONTRACTOR_ID"," where OFFICE_ID="+Office_id,"cb1","","");
		String pid=ame_project_id;//obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_ID"," where SCH_SNO="+sch);
		String SCHTYPEID=obj.getValue("PMS_SCH_MASTER","SCH_TYPE_ID"," where SCH_SNO="+sch);
		String ACCOUNT_HEAD_CODE=obj.getValue("PMS_AME_MST_ITEM_ACC_CODE","ACCOUNT_HEAD_CODE","  where SCH_TYPE_ID="+SCHTYPEID+" and  MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);
		 //String qry="SELECT * FROM FAS_PAYMENT_TRANSACTION WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+" AND sub_ledger_type_code=10 and ACCOUNT_HEAD_CODE="+ACCOUNT_HEAD_CODE+" and SUB_LEDGER_CODE="+pid;
		// old
		String ACCOUNT_HEAD_CODE_new=obj.getValue("PMS_AME_MST_ITEM_ACC_CODE","ACCOUNT_HEAD_CODE","  where SCH_TYPE_ID="+SCHTYPEID+" and  MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);
		PreparedStatement ps=con.prepareStatement("select ACCOUNT_HEAD_CODE from PMS_AME_MST_ACC_HEAD_MAP where SCH_TYPE_ID="+SCHTYPEID+" and MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);
		ResultSet rs=ps.executeQuery();
		int temp_account=0;
		int account=0;
		String account_="";
		int row=0;
		while (rs.next())
		{
			account=rs.getInt("ACCOUNT_HEAD_CODE");
			row++;	
			if (row>1)
			{
				account_+=","+account;
			}else
			{
				account_=""+account;
			}
			
		}
		ACCOUNT_HEAD_CODE_new=account_;
		ACCOUNT_HEAD_CODE_new=obj.isNull(ACCOUNT_HEAD_CODE_new, 1); 
		String cond=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear 
 			+" and CASHBOOK_MONTH="+pmonth+" and SUB_LEDGER_TYPE_CODE= 10 " 
 			+" and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE_new+") and VOUCHER_NO in ( " 
			+" select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+// and JOURNAL_TYPE_CODE =11 "  
			" and JOURNAL_STATUS='L')";
		
		 String cond_payment="   ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear 
 			+" and CASHBOOK_MONTH="+pmonth+" and SUB_LEDGER_TYPE_CODE= 10 " 
 			+" and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE_new+") and VOUCHER_NO in ( " 
 			+" select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
 			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+// and JOURNAL_TYPE_CODE =11 "  
 			" and PAYMENT_STATUS='L')";
		
		String GROUP_SNO=obj.getValue("PMS_AME_MST_MAIN_ITEM", "GROUP_SNO", "where MAIN_ITEM_SNO="+MAIN_ITEM_SNO);
		String drow="0";
		
		drow= obj_temp.delRecord("PMS_AME_EXP_ITEM_BREAKUP", " where OFFICE_ID="+Office_id+" and MONTH="+pmonth+"   and YEAR="+pyear+" and SCH_SNO="+sch+" and  GROUP_SNO="+GROUP_SNO+" and  MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO ="+SUB_ITEM_SNO, con);
		
		qry1="SELECT AMOUNT,CR_DR_INDICATOR,VOUCHER_NO,PARTICULARS,BILL_NO,BILL_DATE,account_head_code FROM FAS_PAYMENT_TRANSACTION WHERE "+cond_payment+"  and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE_new+" ) and SUB_LEDGER_CODE="+ame_project_id;
		qry2="SELECT AMOUNT,CR_DR_INDICATOR ,VOUCHER_NO,PARTICULARS,BILL_NO,BILL_DATE,account_head_code FROM FAS_JOURNAL_TRANSACTION  "+cond; // WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+" AND sub_ledger_type_code=10 and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+pid;
		String ac_st=ACCOUNT_HEAD_CODE.equalsIgnoreCase("0")?"":"("+ACCOUNT_HEAD_CODE+")";
		qry3="SELECT AMOUNT,RECEIPT_NO,PARTICULARS,RECEIPT_NO ,account_head_code FROM fas_receipt_transaction   WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"   and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE_new+" ) and SUB_LEDGER_CODE="+ame_project_id;
		String amt="",crdr="",vno="",remarks="",billno="",billdate="",vdate="",account_head_code="";
		
		System.out.println(" ::::::::::::: Item Wise Exp Stage FAS_PAYMENT_TRANSACTION ::::::::::::::");
		System.out.println(qry1);
		System.out.println(" ::::::::::::: Item Wise Exp Stage FAS_JOURNAL_TRANSACTION ::::::::::::::");
		System.out.println(qry2);
		System.out.println(" ::::::::::::: Item Wise Exp Stage fas_receipt_transaction ::::::::::::::");
		System.out.println(qry3);
		
		ResultSet rs_loc=obj.getRS(qry1);  
		while (rs_loc.next()) 
		{  
			amt=rs_loc.getString("AMOUNT");	 		
			account_head_code=rs_loc.getString("account_head_code");
			crdr=rs_loc.getString("CR_DR_INDICATOR");
			if (crdr.equalsIgnoreCase("CR"))
			net_amt-=Double.parseDouble(amt);
			else
			net_amt+=Double.parseDouble(amt);  
			vno=rs_loc.getString("VOUCHER_NO");
			remarks=obj.isNull(rs_loc.getString("PARTICULARS"),2);
			billno=obj.isNull(rs_loc.getString("BILL_NO"),2);			 
			try
			 {  
			  billdate=obj.isNull(rs_loc.getDate("BILL_DATE").toString().split("\\ ")[0],2);					 		
			}catch(Exception e) 
			{
			}
			try
			{ 
				vdate=obj_temp.getDate("FAS_PAYMENT_MASTER","PAYMENT_DATE"," where VOUCHER_NO="+vno+" and 	ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
			}catch(Exception e) 
			{  
							 				 	 				
			}
			
			Hashtable tab1 = new Hashtable();
			int tab_max_ = obj.getMax("PMS_AME_EXP_ITEM_BREAKUP","BILL_SNO", "");
			tab1.put("BILL_SNO", tab_max_);
			tab1.put("SCH_SNO", sch);
			tab1.put("MONTH", pmonth);
			tab1.put("YEAR",pyear); 
			tab1.put("PERFORM_DESC_SNO", "3");
			tab1.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);						
			tab1.put("SUB_ITEM_SNO", SUB_ITEM_SNO);						
			tab1.put("GROUP_SNO", GROUP_SNO);	
			tab1.put("BILL_NO",  "'" +billno+ "'");						
			tab1.put("BILL_DATE", "'"+billdate+"'");		
			tab1.put("VOUCHER_NO",  "'" +vno+ "'");					
			tab1.put("BILL_AMT", amt);	
			tab1.put("PARTICULARS","'" +remarks+ "'");
			tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
			tab1.put("UPDATED_TIME", "clock_timestamp()");
			tab1.put("account_head_code", account_head_code);			
			tab1.put("OFFICE_ID", Office_id);			 
			tab1.put("crdr", "'"+crdr+"'");
			String project_id=obj_temp.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+sch+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
			tab1.put("PROJECT_ID", project_id);
			int ins_row=obj_temp.recordSave(tab1,"PMS_AME_EXP_ITEM_BREAKUP", con);
		}
		
		 ResultSet rs_loc_pay=obj1.getRS(qry2);			
		 while (rs_loc_pay.next()) 
		  {
			amt=rs_loc_pay.getString("AMOUNT");	 
			account_head_code=rs_loc_pay.getString("account_head_code");
			crdr=rs_loc_pay.getString("CR_DR_INDICATOR");
			if (crdr.equalsIgnoreCase("CR"))
			net_amt-=Double.parseDouble(amt);
			else
			net_amt+=Double.parseDouble(amt);  
			vno=rs_loc_pay.getString("VOUCHER_NO");
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
		 		  vdate=obj_temp.getDate("FAS_JOURNAL_MASTER","VOUCHER_DATE"," where VOUCHER_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
				}catch(Exception e) 
				{  
					System.out.println(e);			 				 	 				
				}
		//	drow= obj_temp.delRecord("PMS_AME_EXP_ITEM_BREAKUP", " where OFFICE_ID="+Office_id+" and MONTH="+pmonth+" and YEAR="+pyear+" and SCH_SNO="+sch+" and  GROUP_SNO="+GROUP_SNO+" and  MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO ="+SUB_ITEM_SNO, con);
			Hashtable tab1 = new Hashtable();
			int tab_max_ = obj.getMax("PMS_AME_EXP_ITEM_BREAKUP","BILL_SNO", "");
			tab1.put("BILL_SNO", tab_max_);
			tab1.put("SCH_SNO", sch);
			tab1.put("MONTH", pmonth);
			tab1.put("YEAR",pyear); 
			tab1.put("PERFORM_DESC_SNO", "3");
			tab1.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);						
			tab1.put("SUB_ITEM_SNO", SUB_ITEM_SNO);						
			tab1.put("GROUP_SNO", GROUP_SNO);	
			tab1.put("BILL_NO",  "'" +billno+ "'");						
			tab1.put("BILL_DATE", "'"+billdate+"'");		
			tab1.put("VOUCHER_NO",  "'" +vno+ "'");					
			tab1.put("BILL_AMT", amt);	
			tab1.put("account_head_code", account_head_code);
			tab1.put("PARTICULARS","'" +remarks+ "'");
			tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
			tab1.put("UPDATED_TIME", "clock_timestamp()");
			tab1.put("OFFICE_ID", Office_id);			 
			tab1.put("crdr", "'"+crdr+"'");
			
			String project_id=obj_temp.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+sch+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
			tab1.put("PROJECT_ID", project_id);
			int ins_row=obj_temp.recordSave(tab1,"PMS_AME_EXP_ITEM_BREAKUP", con);
		} 
		 System.out.println("Stage 3 2 ");
		ResultSet rs_loc_rec=null;
		rs_loc_rec=obj2.getRS(qry3); 
		while (rs_loc_rec.next()) 
		{
			amt=rs_loc_rec.getString("AMOUNT");
			account_head_code=rs_loc_rec.getString("account_head_code");
			crdr="CR";  
			net_amt-=Double.parseDouble(amt);
			vno=rs_loc_rec.getString("RECEIPT_NO");
 			remarks=obj.isNull(rs_loc_rec.getString("PARTICULARS"),2);
 			try
 			{    
 				billno=obj.isNull(rs_loc_rec.getString("BILL_NO"),2);
		 		billdate=obj.isNull(rs_loc_rec.getDate("BILL_DATE").toString().split("\\ ")[0],2);
 			}catch(Exception e){ }
			 try
			 { 
	 			vdate=obj_temp.getDate("FAS_RECEIPT_MASTER","RECEIPT_DATE"," where RECEIPT_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
	 		  }catch(Exception e) {  }
	 		 //   drow= obj_temp.delRecord("PMS_AME_EXP_ITEM_BREAKUP", " where OFFICE_ID="+Office_id+" and MONTH="+pmonth+" and YEAR="+pyear+" and SCH_SNO="+sch+" and  GROUP_SNO="+GROUP_SNO+" and  MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO ="+SUB_ITEM_SNO, con);
				Hashtable tab1 = new Hashtable();
				int tab_max_ = obj_temp.getMax("PMS_AME_EXP_ITEM_BREAKUP","BILL_SNO", "");
				tab1.put("BILL_SNO", tab_max_);
				tab1.put("SCH_SNO", sch);
				tab1.put("MONTH", pmonth);
				tab1.put("YEAR",pyear); 
				tab1.put("PERFORM_DESC_SNO", "3");
				tab1.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);						
				tab1.put("SUB_ITEM_SNO", SUB_ITEM_SNO);						
				tab1.put("GROUP_SNO", GROUP_SNO);	
				tab1.put("BILL_NO",  "'" +billno+ "'");						
				tab1.put("BILL_DATE", "'"+billdate+"'");		
				tab1.put("VOUCHER_NO",  "'" +vno+ "'");					
				tab1.put("BILL_AMT", amt);	
				tab1.put("PARTICULARS","'" +remarks+ "'");
				tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
				tab1.put("account_head_code", account_head_code);
				tab1.put("UPDATED_TIME", "clock_timestamp()");
				tab1.put("OFFICE_ID", Office_id);			 
				tab1.put("crdr", "'"+crdr+"'");
				
				String project_id=obj_temp.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+sch+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
				tab1.put("PROJECT_ID", project_id);
				int ins_row=obj_temp.recordSave(tab1,"PMS_AME_EXP_ITEM_BREAKUP", con);
	 		  
		}
		}catch(Exception e )
		{
			System.out.println(e);
		}
		obj_temp=null;
		obj=null;
		obj1=null;
		obj2=null;
		return net_amt;
	}
}
