package Servlets.AME.AME1.AMEM.servlets;

import it.businesslogic.ireport.data.CincomMDXFieldsProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PMS_AME_SCH_MONTHLY extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public PMS_AME_SCH_MONTHLY() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
		Controller obj = new Controller();
		Controller obj2 = new Controller();
		String pyear_sel="0",pmonth_sel="0",pmonth_sch="0",process_f="0";
		String condition="";
		String sub_cond="";      
		String sub_cond_value="";
		Connection con=null,con_local=null ;
		response.setContentType(CONTENT_TYPE);  
		try {
			try {
				con = obj.con();
			} catch (Exception e) {
				 
				e.printStackTrace();
			}
			obj.createStatement(con);
			obj2.createStatement(con);
		} catch (SQLException e2) {
			 
			e2.printStackTrace();
		}
		String process_code = obj.setValue("process_code", request);
		String type = obj.setValue("type", request);
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");  
		if (userid == null) {

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String Office_id = null;
		try {
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		} catch (Exception e2) {
			
			e2.printStackTrace();
		} 
			pyear_sel = obj.setValue("pyear", request);
			pmonth_sel = obj.setValue("pmonth", request);
			pmonth_sch = obj.setValue("sch_sno", request);
 
			process_f = "";
			if (Integer.parseInt(pmonth_sel) <= 3) 
			{
				process_f = (Integer.parseInt(pyear_sel) - 1)+ "-" + pyear_sel;
			} else {
				process_f = pyear_sel + "-"+ (Integer.parseInt(pyear_sel) + 1);
			}	  

			  sub_cond_value=",exp. ACTUAL_EXP,exp.ACTUAL_EXP_SNO";
			sub_cond=" LEFT OUTER JOIN " +
			"  ( SELECT PERFORM_DESC_SNO,GROUP_SNO,MAIN_ITEM_SNO,SUB_ITEM_SNO,ACTUAL_EXP,ACTUAL_EXP_SNO " +
			"  FROM PMS_AME_TRN_SCH_ACT_EXP_ITEM " +
			"  WHERE FIN_YEAR='"+process_f+"' AND MONTH="+pmonth_sel+" AND YEAR="+pyear_sel+" AND SCH_SNO="+pmonth_sch+
			"  AND PERFORM_DESC_SNO=3 " +
			" )exp " +
			" ON exp.GROUP_SNO      =G.GROUP_SNO AND exp.MAIN_ITEM_SNO =M.MAIN_ITEM_SNO " +
			" AND ( exp.SUB_ITEM_SNO=S.SUB_ITEM_SNO OR exp.SUB_ITEM_SNO   =0)";
		  

		String qry = " SELECT G.GROUP_SNO,m.MAIN_ITEM_SNO,CASE WHEN S.SUB_ITEM_SNO IS NULL "
			+ " THEN 0 ELSE S.SUB_ITEM_SNO END AS SUB_ITEM_SNO, "
			+ "  G.GROUP_DESC,m.MAIN_ITEM_DESC,m.STATICFLAG,case  "
			+ "  when S.SUB_ITEM_DESC is null "
			+ "  then '' "
			+ "  else "
			+ "  S.SUB_ITEM_DESC "     
			+ " end  "
			+ " as  "  
			+ " SUB_ITEM_DESC "+ sub_cond_value 
			+ " FROM "
			+ " ( SELECT GROUP_SNO, GROUP_DESC FROM PMS_AME_MST_GROUP where GROUP_SNO=1 ORDER BY GROUP_SNO "
			+ " )G "  
			+ " JOIN "  
			+ " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC, GROUP_SNO,STATICFLAG FROM PMS_AME_MST_MAIN_ITEM "
			+ " )M "
			+ " ON M.GROUP_SNO=G.GROUP_SNO "
			+ " LEFT OUTER JOIN "
			+ " ( SELECT SUB_ITEM_SNO ,SUB_ITEM_DESC,MAIN_ITEM_SNO FROM PMS_AME_MST_SUB_ITEM "
			+ " )S "
			+ " ON S.MAIN_ITEM_SNO=M.MAIN_ITEM_SNO  " +sub_cond + 							
			"ORDER BY g.GROUP_SNO,  MAIN_ITEM_SNO  ,SUB_ITEM_SNO";
			double main_total=0.0;
			System.out.println(qry);
			String sch_sno=obj.setValue("sch_sno", request);
			
			String schtypeid=obj.getValue("PMS_SCH_MASTER","SCH_TYPE_ID", " where SCH_SNO=" + sch_sno);

			String pyear= obj.setValue("pyear", request);
			String pmonth=obj.setValue("pmonth", request);
			process_f = pyear + "-"+ (Integer.parseInt(pyear) + 1);
			String res="<response>"; 						 
			try {
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(qry);
				while (rs.next())
				{
					String MAIN_ITEM_SNO=obj.isNull(rs.getString("MAIN_ITEM_SNO"), 1) ;
					String SUB_ITEM_SNO=obj.isNull(rs.getString("SUB_ITEM_SNO"), 1) ;
					String GROUP_SNO=obj.isNull(rs.getString("GROUP_SNO"), 1) ;
					String ame_project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING","FAS_PROJECT_ID"," where SCH_SNO="+sch_sno +" and OFFICE_ID="+Office_id);
					String ACCOUNT_HEAD_CODE=obj.getValue("PMS_AME_MST_ITEM_ACC_CODE","ACCOUNT_HEAD_CODE","  where MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);
					String pid=ame_project_id;//obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_ID"," where SCH_SNO="+sch);
					 //String qry="SELECT * FROM FAS_PAYMENT_TRANSACTION WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+" AND sub_ledger_type_code=10 and ACCOUNT_HEAD_CODE="+ACCOUNT_HEAD_CODE+" and SUB_LEDGER_CODE="+pid;
					// old
					//////////	Lib ,PenSion and Super
					String head1="";
		 			String head2=""; 
		 			String head1_amount="0";
		 			String head2_amount="0";
		 			Double final_vlaue=0.0;
		 			BigDecimal bd = null  ;
		 			try
		 			{     
		 			 
		 			 head1=obj2.getValue("COM_MST_ACCOUNT_HEADS", "ACCOUNT_HEAD_DESC","where ACCOUNT_HEAD_CODE=211906");;
		 			 head2=obj2.getValue("COM_MST_ACCOUNT_HEADS", "ACCOUNT_HEAD_DESC","where ACCOUNT_HEAD_CODE=212006");;
		 			 String cond=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear
				 			+" and CASHBOOK_MONTH="+pmonth+" and SUB_LEDGER_TYPE_CODE= 10 " 
				 			+" and ACCOUNT_HEAD_CODE in (222102,222103,222104,222105,222106,222107,222108,222109,222111) and VOUCHER_NO in ( " 
			    			+" select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
			    			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth+" and JOURNAL_TYPE_CODE =11 " 
			      			+" and JOURNAL_STATUS='L')";
		 			  
		 			 String  ac=" 200607,200608,200609,200610,200611,200612,200613,200614,200615,200620,200621,200622,200623,200624,200625,200626,200627,200628,200629,200630,200631,200632,200633,200634,200635,200636,200637,200638,200639,200640,200642, 200651, 200652,  220601,200650 ";  
		 			 String cond1=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"    and CASHBOOK_YEAR="+pyear
			 			+" and CASHBOOK_MONTH="+pmonth+"   " 
			 			+" and ACCOUNT_HEAD_CODE in ("+ac+") and VOUCHER_NO in ( " 
		    			+" select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
		    			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth//and JOURNAL_TYPE_CODE =11 " 
		      			+" and JOURNAL_STATUS='L')";
		 			String cond2=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"    and CASHBOOK_YEAR="+pyear
		 			+" and CASHBOOK_MONTH="+pmonth+"   " 
		 			+" and ACCOUNT_HEAD_CODE in ("+ac+") and VOUCHER_NO in ( " 
					+" select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
					+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth//and JOURNAL_TYPE_CODE =11 " 
		  			+" and PAYMENT_STATUS='L')";
		 			 
		 			 String cond3=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"    and CASHBOOK_YEAR="+pyear
			 			+" and CASHBOOK_MONTH="+pmonth+"   " 
			 			+" and ACCOUNT_HEAD_CODE in ("+ac+") and RECEIPT_NO in ( " 
		    			+" select RECEIPT_NO from FAS_RECEIPT_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
		    			+" and CASHBOOK_YEAR="+pyear+" and CASHBOOK_MONTH="+pmonth//and JOURNAL_TYPE_CODE =11 " 
		      			+" and RECEIPT_STATUS='L')";
//		 			res+="<sup_cost>"+bd+"</sup_cost>";
//		 			res+="<head1_211906>"+head1_amount+"</head1_211906>";
//		 			res+="<head1_212006>"+head2_amount+"</head1_212006>";
//					res+= "</response>";
		 			 double net_amt=0.0;	
					//Supervisory Staff
					if (Integer.parseInt(MAIN_ITEM_SNO)==1 && Integer.parseInt(SUB_ITEM_SNO)==2)
					{
						 String head1_amount_j=obj.isNull(obj.getValue("FAS_JOURNAL_TRANSACTION", "sum(AMOUNT) ","val2",cond1),1);
			    		 String head1_amount_p=obj.isNull(obj.getValue("FAS_PAYMENT_TRANSACTION", "sum(AMOUNT) ","val3",cond2),1);
			 			 String head1_amount_r=obj.isNull(obj.getValue("fas_receipt_transaction", "sum(AMOUNT) ","val4",cond3),1);
			 			 String div_qty=obj.isNull(obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "sum(QTY_CONSUMED_NET) ","val5"," where OFFICE_ID="+Office_id+" and month="+pmonth+" and year="+pyear),1);
			 			 String div_sch_qty=obj.isNull(obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "sum(QTY_CONSUMED_NET) ","val5"," where OFFICE_ID="+Office_id+" and month="+pmonth+" and year="+pyear+" and SCH_SNO="+sch_sno),1);			 			
			 			 Double net_amt_calc=( ( Double.parseDouble(head1_amount_j)+Double.parseDouble(head1_amount_p) ) -Double.parseDouble(head1_amount_r));
			 			 final_vlaue= ( ( net_amt*Double.parseDouble(div_sch_qty) ) /Double.parseDouble(div_qty) ) ; // / 100000;
			 			 bd = new BigDecimal(Double.toString(final_vlaue));
			 		     bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			 		     net_amt=bd.doubleValue();  
					}else if (Integer.parseInt(MAIN_ITEM_SNO)==10)
					{	//Liablilities Charges
						head1_amount=obj.isNull(obj2.getValue("FAS_JOURNAL_TRANSACTION", "ROUND(( sum(AMOUNT)*(2/100) ) ,2) ","val1",cond),1);
						net_amt=Double.parseDouble(obj.isNull(head1_amount,1));
					}else if (Integer.parseInt(MAIN_ITEM_SNO)==11)
					{	//Others (Pension Contribution etc)
						head2_amount=obj.isNull(obj.getValue("FAS_JOURNAL_TRANSACTION", "ROUND(( sum(AMOUNT)*(1/100) )  ,2)","val2",cond),1);
						net_amt=Double.parseDouble(head2_amount);
					}else
					{
						// Remaining other Main item calculate based on Receipt ,Payment,Journal 
						
					cond=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear 
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
							qry1="SELECT * FROM FAS_PAYMENT_TRANSACTION WHERE "+cond_payment+"  and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+ame_project_id;
							qry2="SELECT * FROM FAS_JOURNAL_TRANSACTION  "+cond; // WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+" AND sub_ledger_type_code=10 and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+pid;
							String ac_st=ACCOUNT_HEAD_CODE.equalsIgnoreCase("0")?"":"("+ACCOUNT_HEAD_CODE+")";
				 			qry3="SELECT * FROM fas_receipt_transaction   WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"   and ACCOUNT_HEAD_CODE in ("+ACCOUNT_HEAD_CODE+" ) and SUB_LEDGER_CODE="+ame_project_id;
				 			String drow=obj.isNull(obj.delRecord("PMS_AME_EXP_ITEM_BREAKUP", " where OFFICE_ID="+Office_id+" and MONTH="+pmonth+" and YEAR="+pyear+" and SCH_SNO="+sch_sno+" and  GROUP_SNO="+GROUP_SNO+" and  MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO ="+SUB_ITEM_SNO, con),1);
				 			// Payment      
				 			Hashtable tab1 = new Hashtable();
				 			ResultSet rs_loc=obj.getRS(qry1);
				 			int tab_max_ =0,ins_row=0;
				 			String vno="",vdate="",amt="",remarks="",billno="",billdate="",crdr="" ;					 			 		
					 		while (rs_loc.next()) 
					 		{
						 			  vno=rs_loc.getString("VOUCHER_NO");
						 			  ACCOUNT_HEAD_CODE=rs_loc.getString("ACCOUNT_HEAD_CODE");
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
							 			
							 			tab_max_ = obj.getMax("PMS_AME_EXP_ITEM_BREAKUP","BILL_SNO", "");
							 			tab1.put("BILL_SNO", tab_max_);
										tab1.put("SCH_SNO", sch_sno);
										tab1.put("MONTH", pmonth);
										tab1.put("YEAR",pyear); 
										tab1.put("PERFORM_DESC_SNO", "3");
										tab1.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);
										tab1.put("ACCOUNT_HEAD_CODE", ACCOUNT_HEAD_CODE);
										tab1.put("SUB_ITEM_SNO", SUB_ITEM_SNO);
										tab1.put("GROUP_SNO", GROUP_SNO);					
										tab1.put("BILL_NO",  "'" +billno+ "'");
										tab1.put("BILL_DATE", "'"+billdate+"'");
										tab1.put("VOUCHER_NO",  "'" +vno+ "'");
										tab1.put("VOUCHER_DATE", "'"+vdate+"'");
										tab1.put("BILL_AMT", amt);
										tab1.put("PARTICULARS","'" +remarks+ "'");
										tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
										tab1.put("UPDATED_TIME", "clock_timestamp()");
										tab1.put("OFFICE_ID", Office_id);
										tab1.put("crdr", "'"+crdr+"'");						
										String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+sch_sno+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch_sno);
										tab1.put("PROJECT_ID", project_id);
										ins_row=obj.recordSave(tab1,"PMS_AME_EXP_ITEM_BREAKUP", con);
					 		   }
					 	// journal 
					 			ResultSet rs_loc_pay=obj2.getRS(qry2);
					 		   while (rs_loc_pay.next()) 
					 		   {
						 			 
						 			vno=rs_loc_pay.getString("VOUCHER_NO");
						 			ACCOUNT_HEAD_CODE=rs_loc.getString("ACCOUNT_HEAD_CODE");
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
									
									tab_max_ = obj.getMax("PMS_AME_EXP_ITEM_BREAKUP","BILL_SNO", "");
						 			tab1.put("BILL_SNO", tab_max_);
									tab1.put("SCH_SNO", sch_sno);
									tab1.put("MONTH", pmonth);
									tab1.put("YEAR",pyear); 
									tab1.put("PERFORM_DESC_SNO", "3");
									tab1.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);
									tab1.put("SUB_ITEM_SNO", SUB_ITEM_SNO);
									tab1.put("ACCOUNT_HEAD_CODE", ACCOUNT_HEAD_CODE);
									tab1.put("GROUP_SNO", GROUP_SNO);					
									tab1.put("BILL_NO",  "'" +billno+ "'");
									tab1.put("BILL_DATE", "'"+billdate+"'");
									tab1.put("VOUCHER_NO",  "'" +vno+ "'");
									tab1.put("VOUCHER_DATE", "'"+vdate+"'");
									tab1.put("BILL_AMT", amt);
									tab1.put("PARTICULARS","'" +remarks+ "'");
									tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
									tab1.put("UPDATED_TIME", "clock_timestamp()");
									tab1.put("OFFICE_ID", Office_id);
									tab1.put("crdr", "'"+crdr+"'");						
									String project_id=obj2.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+sch_sno+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch_sno);
									tab1.put("PROJECT_ID", project_id);
									ins_row=obj2.recordSave(tab1,"PMS_AME_EXP_ITEM_BREAKUP", con);
					 		   }
					 		   // Receipt 
					 		    rs_loc_pay=null;
					 			ResultSet rs_loc_rec=null;
					 			rs_loc_rec=obj2.getRS(qry3);
					 			while (rs_loc_rec.next()) 
					 		   {
						 			 
						 			vno=rs_loc_rec.getString("RECEIPT_NO");
						 			ACCOUNT_HEAD_CODE=rs_loc.getString("ACCOUNT_HEAD_CODE");
						 			vdate="";
						 			amt=rs_loc_rec.getString("AMOUNT");
						 			crdr="CR";
						 			net_amt-=Double.parseDouble(amt);
						 			remarks=obj.isNull(rs_loc_rec.getString("PARTICULARS"),2);				 		 
						 			try
						 			{    
						 				billno=obj.isNull(rs_loc_rec.getString("BILL_NO"),2);
								 		billdate=obj.isNull(rs_loc_rec.getDate("BILL_DATE").toString().split("\\ ")[0],2);
						 			}catch(Exception e)
						 			{
						 			}
									 try
									 { 
							 			vdate=obj.getDate("FAS_RECEIPT_MASTER","RECEIPT_DATE"," where RECEIPT_NO="+vno+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND CASHBOOK_YEAR ="+pyear+"  AND CASHBOOK_MONTH="+pmonth+"");
							 			
						 			}catch(Exception e) { }
						 			tab_max_ = obj.getMax("PMS_AME_EXP_ITEM_BREAKUP","BILL_SNO", "");
						 			tab1.put("BILL_SNO", tab_max_);
									tab1.put("SCH_SNO", sch_sno);
									tab1.put("MONTH", pmonth);
									tab1.put("YEAR",pyear); 
									tab1.put("PERFORM_DESC_SNO", "3");
									tab1.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);
									tab1.put("SUB_ITEM_SNO", SUB_ITEM_SNO);
									tab1.put("GROUP_SNO", GROUP_SNO);
									tab1.put("ACCOUNT_HEAD_CODE", ACCOUNT_HEAD_CODE);
									tab1.put("BILL_NO",  "'" +billno+ "'");
									tab1.put("BILL_DATE", "'"+billdate+"'");
									tab1.put("VOUCHER_NO",  "'" +vno+ "'");
									tab1.put("VOUCHER_DATE", "'"+vdate+"'");
									tab1.put("BILL_AMT", amt);
									tab1.put("PARTICULARS","'" +remarks+ "'");
									tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
									tab1.put("UPDATED_TIME", "clock_timestamp()");
									tab1.put("OFFICE_ID", Office_id);
									tab1.put("crdr", "'"+crdr+"'");						
									String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+sch_sno+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch_sno);
									tab1.put("PROJECT_ID", project_id);
									ins_row=obj.recordSave(tab1,"PMS_AME_EXP_ITEM_BREAKUP", con);
					 		   }
					}
					 	String cond_add=" and SUB_ITEM_SNO="+SUB_ITEM_SNO+" and MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and GROUP_SNO="+GROUP_SNO;
						String d_r=obj.delRecord("PMS_AME_TRN_SCH_ACT_EXP_ITEM", "where (1=1) "+cond_add+" and  PERFORM_DESC_SNO=3 and FIN_YEAR='"+process_f+"' and SCH_SNO="+sch_sno+" and year ="+pyear_sel+"  and month="+pmonth_sel+" and  office_id="+Office_id, con);
			 			Hashtable cols = new Hashtable();
						int tab_max_=obj.getMax("PMS_AME_TRN_SCH_ACT_EXP_ITEM","ACTUAL_EXP_SNO", "");
						cols.put("ACTUAL_EXP_SNO",tab_max_ );
				    	cols.put("FIN_YEAR", "'"+process_f+ "'");
				    	cols.put("YEAR", pyear_sel);
				    	cols.put("MONTH",pmonth_sel);
				    	cols.put("SCH_SNO",sch_sno);
				    	cols.put("ACTUAL_EXP", net_amt);
				    	cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
						cols.put("UPDATED_TIME", "clock_timestamp()");
						cols.put("OFFICE_ID", Office_id);
						cols.put("PERFORM_DESC_SNO","3");
						cols.put("GROUP_SNO", GROUP_SNO);
						cols.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);
						cols.put("SUB_ITEM_SNO",SUB_ITEM_SNO );
						cols.put("ENTRY_DATE", "clock_timestamp()");
						String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+sch_sno+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
						//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+obj.setValue("sch", request));
						cols.put("PROJECT_ID",project_id );  
						int r  =obj.recordSave(cols,"PMS_AME_TRN_SCH_ACT_EXP_ITEM", con);	
						
						
						
						main_total+=net_amt;
		 			}catch(Exception e) {         
		 				System.out.println(e);
		 			} 
					
				}
				BigDecimal s=new BigDecimal(main_total);
				DecimalFormat df=new DecimalFormat("0.00");
				res+="<main_total>"+ df.format(s)+"</main_total>";
 			res+= "</response>";
 			obj.resposeWr(response, res);
			obj=null;
			con=null;  
			
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
		}catch(Exception e){}
      }	

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet( request, response);
	}
}
