package Servlets.PMS.PMS1.DCB.servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class demand_insert__ {
	Connection con = null;
	Connection con1 = null;
	Statement stmt = null, stmt2 = null, stmt3 = null, stmt4 = null, stmt5 = null;
	PreparedStatement ps = null;
	ResultSet rs = null, rs_wc = null, rs_coll = null, rs_sch_type = null;
	String xml = "", qry = "", Office_name = "", ACCOUNTING_UNIT_ID = "";
	String new_cond = Controller.new_cond;
	String meter_status = Controller.meter_status;
	String WC_MTH_TOTAL = "";
	String msg = " ", ins_qry2 = "";
	double amount = 0.0, excessamount = 0.0, netamount = 0.0;

	synchronized public String demand_ins(String BILL_PERIOD_FROM, String BILL_PERIOD_TO, String billyear,
			String billmonth, String date, String ben_type_val, String Office_id, String userid, String BENEFICIARY_SNO)
			throws Exception {
		Controller obj = new Controller();
		Connection con = obj.con();
		Connection con1 = obj.con();
		Connection con2 = obj.con();
		Connection con3 = obj.con();
		Connection con4 = obj.con();
		Connection con5 = obj.con();
		Controller obj2 = new Controller();
		System.out.println(" *** DEMAND INSERT FOR NORMAL SCHEME ***");
		System.out.println("PMS--->DCB--->demand_insert");
		int total_demand = 0, already_demand = 0, row = 0;
		String OB_INT_CUR_YR_MAINT = "0", OB_INT_AMT_WC = "", ben_int = "";
		obj2.createStatement(con);
		obj.createStatement(con);

		stmt = con.createStatement();
		stmt3 = con1.createStatement();
		stmt4 = con2.createStatement();
		stmt5 = con3.createStatement();
		stmt2 = con5.createStatement();
		int month_set = 0, // demand Gen month-1 setting
				year_set = 0, // Year Setting
				month_set2 = 0, // demand Gen Month-2 Setting
				year_set2 = 0;// Year Setting

		if ((Integer.parseInt(billmonth)) == 1) {
			month_set = 12;
			year_set = Integer.parseInt(billyear) - 1;
		} else {
			month_set = (Integer.parseInt(billmonth) - 1);
			year_set = Integer.parseInt(billyear);
		}

		if ((Integer.parseInt(billmonth)) == 1) {
			month_set2 = 11;
			year_set2 = Integer.parseInt(billyear) - 1;
		} else if ((Integer.parseInt(billmonth)) == 2) {
			month_set2 = 12;
			year_set2 = Integer.parseInt(billyear) - 1;
		} else {
			month_set2 = (Integer.parseInt(billmonth) - 2);
			year_set2 = Integer.parseInt(billyear);
		}

		String ben_sno_cond = "";
		if (!ben_type_val.equalsIgnoreCase("0"))
			ben_sno_cond = " and BENEFICIARY_SNO=" + BENEFICIARY_SNO;

		obj.testQry(" *** Bill Generation Start  for the month of " + billmonth + "-" + billyear);
		int already_demand2 = obj.getCount("PMS_DCB_TRN_BILL_DMD",
				"where OFFICE_ID=" + Office_id + " and BILL_MONTH=" + billmonth + " and BILL_YEAR=" + billyear + "");
		already_demand = obj.getCount("PMS_DCB_TRN_BILL_DMD", "where OFFICE_ID=" + Office_id + " and BILL_MONTH="
				+ billmonth + " and BILL_YEAR=" + billyear + " and BENEFICIARY_SNO in (" + BENEFICIARY_SNO + ")");
		String sel_qry = "SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME, ACCOUNTING_UNIT_OFFICE_ID	FROM FAS_MST_ACCT_UNITS	WHERE ACCOUNTING_UNIT_OFFICE_ID ="
				+ Office_id;
		rs_sch_type = stmt3.executeQuery(sel_qry);
		if (rs_sch_type.next()) {
			ACCOUNTING_UNIT_ID = obj.isNull(rs_sch_type.getString(1), 1);
		}
		String met_query = "select beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where " + meter_status
				+ "   OFFICE_ID=" + Office_id + " " + ben_sno_cond;
		int tot_ben = obj.getCount("pms_dcb_mst_beneficiary",
				"where status ='L' and OFFICE_ID=" + Office_id + " and beneficiary_sno in (" + met_query + ")");
		if (already_demand == tot_ben) {
			xml = "<result><row>" + 0;
			xml += "</row><total_demand>" + 0 + "</total_demand><already_demand>" + already_demand2
					+ "</already_demand></result>";
		} else {

			int type_tot_ben = obj.getCount("pms_dcb_mst_beneficiary", "where status ='L' and OFFICE_ID=" + Office_id
					+ " and beneficiary_sno in (" + met_query + ")  and  BENEFICIARY_TYPE_ID=" + ben_type_val + "");
			int type_tot_bill = already_demand;
			if (type_tot_ben == type_tot_bill && !ben_type_val.equalsIgnoreCase("0")) {
				xml = "<result><row>" + 0;
				xml += "</row><total_demand>" + 0 + "</total_demand><already_demand>" + type_tot_bill
						+ "</already_demand><msg2>Total Beneficiary of This Beneficiary Type : " + type_tot_ben
						+ " \n Total Bill is " + type_tot_bill + "</msg2></result>";
			} else {
				String qry_ins = " SELECT ben.BENEFICIARY_NAME,ben.beneficiary_sno,pr.net_consumed, "
						+ " monthly.CB_MAINT_CHARGES,monthly.CB_CUR_YR_WC, "
						+ " monthly.CB_YESTER_YR_WC,monthly.CB_INT_CUR_YR_MAINT, " + " monthly.CB_INT_AMT_WC, "
						+ " monthly.CB_YESTER_YR_WC+monthly.CB_CUR_YR_WC , "
						+ " ( monthly.DMD_INT_UPTO_MTH_WC)*(int.INT_RATE/100) as INT_CALC_WC, "
						+ "  int.INT_RATE as INT_PERCENT,wc.wc_amt as WC_MTH_TOTAL, "
						+ "  nvl(wc.wc_amt,0)  as MONTH_BILL_AMT , nvl(monthly.DMD_INT_FOR_MTH_WC,0)   as DMD_INT_FOR_MTH_WC "
						+ " FROM( " + " ( " + " SELECT beneficiary_sno, " + " BENEFICIARY_TYPE_ID,BENEFICIARY_NAME  "
						+ "  FROM pms_dcb_mst_beneficiary " + " WHERE     " + new_cond + "   office_id = " + Office_id
						+ " and beneficiary_sno  not in (select beneficiary_sno from PMS_DCB_TRN_BILL_DMD where office_id="
						+ Office_id + " and BILL_MONTH =" + billmonth + " and BILL_YEAR=" + billyear + ")" + "   "
						+ ben_sno_cond + "    )  ben JOIN " + "  (SELECT  SUM(QTY_CONSUMED_NET) as net_consumed, "
						+ "  beneficiary_sno  FROM PMS_DCB_TRN_MONTHLY_PR " + "  WHERE MONTH =  " + billmonth
						+ "  AND YEAR = " + billyear + "  group by beneficiary_sno) "
						+ "  pr ON pr.beneficiary_sno = ben.beneficiary_sno " + "  left " + "  join  " + "   ( "
						+ "   select " + "    sum(CB_MAINT_CHARGES) as CB_MAINT_CHARGES, "
						+ "    sum(CB_CUR_YR_WC) as CB_CUR_YR_WC, " + "    sum(CB_YESTER_YR_WC) as CB_YESTER_YR_WC, "
						+ "    sum(CB_INT_AMT_WC) as CB_INT_AMT_WC, "
						+ "    sum(CB_INT_CUR_YR_MAINT) as CB_INT_CUR_YR_MAINT, " + "     BENEFICIARY_SNO   ,"
						+ "    sum(DMD_INT_FOR_MTH_WC) as DMD_INT_FOR_MTH_WC, "
						+ "    sum(DMD_INT_UPTO_MTH_WC) as DMD_INT_UPTO_MTH_WC " + "   from   "
						+ "   PMS_DCB_TRN_CB_MONTHLY " + "   where  MONTH =  " + month_set + "   AND FIN_YEAR = "
						+ year_set + "  group by " + "      BENEFICIARY_SNO " + "  )monthly "
						+ "   on monthly.BENEFICIARY_SNO=ben.BENEFICIARY_SNO " + "  left join " + "   ( " + "  select  "
						+ "     sum(TOTAL_AMT) as wc_amt,BENEFICIARY_SNO " // Month Bill value got from wc_billing table
						+ "  from " + "      PMS_DCB_WC_BILLING " + "    where MONTH = " + billmonth + "and OFFICE_ID="
						+ Office_id + " AND YEAR =" + billyear + "    group by BENEFICIARY_SNO " + "  )wc  "
						+ "  on wc.BENEFICIARY_SNO=ben.BENEFICIARY_SNO " + "  join " + "   ( " + "     select  "
						+ "         INT_RATE ,BENEFICIARY_TYPE " + "    from  " + "          PMS_DCB_MST_INT "
						+ "    where  " + "          ACTIVE_STATUS='A' " + "  )int"
						+ "  on int.BENEFICIARY_TYPE=ben.BENEFICIARY_TYPE_ID " + "  ) order by beneficiary_sno";

				double net_COLN_CUR_YR_WC = 0, net_COLN_MAINT = 0, net_COLN_YESTER_YR_WC = 0;
				double net_CB_CUR_YR_WC = 0, net_CB_MAINT = 0, net_CB_YESTER_YR_WC = 0, DMD_INT_FOR_MTH_WC = 0.0;
				rs = stmt.executeQuery(qry_ins);

				String ben = "";
				int ben_dmd_count = 0;
				int BENEFICIARY_TYPE_ID_SUB = 0;
				while (rs.next()) {
					ben_dmd_count = 0;
					ben = rs.getString("beneficiary_sno");

					int DIV_BILL_NO = Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY", "BEN_DIV_SNO",
							"where " + new_cond + " BENEFICIARY_SNO=" + ben));
					ben_int = obj.getValue("PMS_DCB_MST_BENEFICIARY", "INT_RATE",
							"where " + new_cond + " BENEFICIARY_SNO=" + ben);
					WC_MTH_TOTAL = obj.isNull(rs.getString("WC_MTH_TOTAL"), 1); // Water charges for the month
					OB_INT_AMT_WC = obj.isNull(rs.getString("CB_INT_AMT_WC"), 1);
					BENEFICIARY_TYPE_ID_SUB = Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY",
							"BENEFICIARY_TYPE_ID_SUB", "where " + new_cond + " BENEFICIARY_SNO=" + ben));

					String Prv_for_int_OB_YESTER_YR_WC = "0", Prv_for_int_OB_CUR_YR_WC = "0", int_collection = "";
					String Cond_int = " where BENEFICIARY_SNO=" + ben + " and FIN_YEAR=" + year_set + " and MONTH="
							+ month_set + " and OFFICE_ID=" + Office_id;
					Prv_for_int_OB_YESTER_YR_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY", "sum(CB_YESTER_YR_WC)",
							Cond_int);
					Prv_for_int_OB_CUR_YR_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY", "sum(CB_CUR_YR_WC)", Cond_int);

					String int_cond4 = " and collection_type=7 )"; // Water Charges
					String int_cond9 = " and collection_type=9 )"; // Water Charges
					String int_main_wcCollection_cond2 = " " + " sub_ledger_type_code = 10" + " And SUB_LEDGER_CODE in"
							+ " ( select PROJECT_ID from PMS_MST_PROJECTS_VIEW where SCH_SNO in "
							+ "   ( select SCH_SNO from PMS_DCB_MST_BENEFICIARY_METRE where " + meter_status
							+ "    BENEFICIARY_SNO=" + ben + " and   OFFICE_ID=" + Office_id + " "
							+ "    ) and   OFFICE_ID=" + Office_id + " " + " )  AND receipt_no IN "
							+ " ( SELECT receipt_no FROM fas_receipt_master  WHERE "
							+ "   RECEIPT_STATUS='L' and sub_ledger_type_code = 14 " + "   AND sub_ledger_code=" + ben
							+ "   AND accounting_unit_id=" + ACCOUNTING_UNIT_ID + "   AND accounting_for_office_id="
							+ Office_id + "   AND cashbook_month=" + month_set + "   AND cashbook_year=" + year_set
							+ " ) and ACCOUNT_HEAD_CODE in  "
							+ " (  select ACCOUNT_HEAD_CODE   from PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ "      where sch_type_id in ( select SCH_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where "
							+ meter_status + "	  OFFICE_ID=" + Office_id + " and BENEFICIARY_SNO=" + ben + " "
							+ " )  and ACTIVE_STATUS='L'  AND cashbook_month=" + month_set + " AND cashbook_year="
							+ year_set + " AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ " AND accounting_for_office_id=" + Office_id + " ";
					String CurrentmonthCollection_cond2 = " (1=1) " + " AND cashbook_month=" + billmonth
							+ " AND cashbook_year=" + billyear + " AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ " AND accounting_for_office_id=" + Office_id + " And sub_ledger_type_code = 10"
							+ " And SUB_LEDGER_CODE in"
							+ " ( select PROJECT_ID from PMS_MST_PROJECTS_VIEW where SCH_SNO in "
							+ "   ( select SCH_SNO from PMS_DCB_MST_BENEFICIARY_METRE where " + meter_status
							+ "   OFFICE_ID=" + Office_id + " and   BENEFICIARY_SNO=" + ben + "  "
							+ "    ) and   OFFICE_ID=" + Office_id + " " + " )  AND receipt_no IN "
							+ "    ( SELECT receipt_no FROM fas_receipt_master  WHERE "
							+ "        RECEIPT_STATUS='L' and sub_ledger_type_code = 14 "
							+ "        AND cashbook_month=" + billmonth + "        AND cashbook_year=" + billyear
							+ "        AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ "   	 AND accounting_for_office_id=" + Office_id + "        AND sub_ledger_code=" + ben
							+ "    )  and ACCOUNT_HEAD_CODE in  "
							+ "	( select ACCOUNT_HEAD_CODE   from PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ "      where sch_type_id in ( select SCH_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where "
							+ meter_status + "		OFFICE_ID=" + Office_id + " and   BENEFICIARY_SNO=" + ben + "  "
							+ "	)  and ACTIVE_STATUS='L' ";
					String prv_month_collection = "0", // Previous Month Collection
							curmonthcollection = "0"; // Current Month Collection
					try {
						stmt5 = con.createStatement();
						System.out.println(" select sum(amount) from  fas_receipt_transaction where    "
								+ int_main_wcCollection_cond2 + "" + int_cond4);
						rs_coll = stmt5.executeQuery("select sum(amount) from  fas_receipt_transaction where    "
								+ int_main_wcCollection_cond2 + "" + int_cond4);

						if (rs_coll.next()) {
							prv_month_collection = obj2.isNull(rs_coll.getString(1), 1);
						} else {
							prv_month_collection = "0";
						}
						rs_coll.close();
					} catch (Exception e) {
						System.out.println(e + "  Current Month Collection try ");
					}
					stmt5.close();
					try {
						stmt5 = con.createStatement();
						System.out.println("  Current Month Collection try start "
								+ "select sum(amount) from  fas_receipt_transaction where    "
								+ CurrentmonthCollection_cond2 + "" + int_cond4);
						rs_coll = stmt5.executeQuery("select sum(amount) from  fas_receipt_transaction where    "
								+ CurrentmonthCollection_cond2 + "" + int_cond4);
						if (rs_coll.next()) {
							curmonthcollection = obj2.isNull(rs_coll.getString(1), 1);
						} else {
							curmonthcollection = "0";
						}
						rs_coll.close();
					} catch (Exception e) {
						System.out.println(" PMS--->DCB--->Bill_Demand ( 1095 )--> " + e);
					}

					/* B */ double prv_collection = Double.parseDouble(prv_month_collection);
					/* A */ double net_ob_prv_month = Double.parseDouble(Prv_for_int_OB_YESTER_YR_WC)
							+ Double.parseDouble(Prv_for_int_OB_CUR_YR_WC);
					/* D */ double dmd_for_month = Double
							.parseDouble(
									obj.isNull(
											obj.getValue("PMS_DCB_WC_BILLING", "sum(TOTAL_AMT)",
													"where MONTH=" + billmonth + " and OFFICE_ID=" + Office_id
															+ " and YEAR=" + billyear + " and BENEFICIARY_SNO=" + ben),
											1));
					/* C */ double Prvdmd_for_month = Double
							.parseDouble(obj.isNull(
									obj.getValue("PMS_DCB_TRN_BILL_DMD", "sum(MONTH_BILL_AMT)", "where BILL_MONTH="
											+ month_set + " and BILL_YEAR=" + year_set + " and BENEFICIARY_SNO=" + ben),
									1));
					double int_collection_ben = Double.parseDouble(obj.isNull(obj.getValue("fas_receipt_transaction",
							"sum(amount)",
							"where   sub_ledger_type_code = 10 " + " " + CurrentmonthCollection_cond2 + "" + int_cond9),
							1));
					double month_minus_2_dmd_for_month = Double.parseDouble(
							obj.isNull(obj.getValue("PMS_DCB_TRN_BILL_DMD", "sum(MONTH_BILL_AMT)", "where BILL_MONTH="
									+ month_set2 + " and BILL_YEAR=" + year_set2 + " and BENEFICIARY_SNO=" + ben), 1));
					// Dt : 27 12 2013 double amt=( ( (
					// net_ob_prv_month-Double.parseDouble(curmonthcollection))-Prvdmd_for_month));
					double amt = 0.0;
					if (BENEFICIARY_TYPE_ID_SUB <= 6)
						amt = (((net_ob_prv_month) - (Prvdmd_for_month + month_minus_2_dmd_for_month)));
					else
						amt = (((net_ob_prv_month) - (Prvdmd_for_month)));

					double int_calc2 = (amt * Double.parseDouble(ben_int) / 100);
					DMD_INT_FOR_MTH_WC = 0.0;
					if (int_calc2 < 0)
						int_calc2 = 0;
					/* New Code */
					/* Date : 14/09/2011 Replace Actual int amount with interest */
					// DMD_INT_FOR_MTH_WC =amt;
					DMD_INT_FOR_MTH_WC = Math.round(int_calc2);

					if (DMD_INT_FOR_MTH_WC < 0)
						DMD_INT_FOR_MTH_WC = 0;
					ben_dmd_count = obj.getCount("PMS_DCB_TRN_BILL_DMD", "where BILL_MONTH=" + billmonth
							+ " and BILL_YEAR=" + billyear + " and  BENEFICIARY_SNO=" + ben + " ");
					String ben_type = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID",
							"where " + new_cond + "  BENEFICIARY_SNO=" + ben), 1);
					String OTHERS_PRIVATE_SNO = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY", "OTHERS_PRIVATE_SNO",
							"where  " + new_cond + " BENEFICIARY_SNO=" + ben), 1);
					String short_name = "";
					if (Integer.parseInt(ben_type) <= 6)
						short_name = obj.isNull(
								obj.getValue("PMS_DCB_BEN_TYPE", "ADDRESS_TO", "where BEN_TYPE_ID=" + ben_type), 3);
					else
						short_name = obj.isNull(obj.getValue("COM_MST_PRIVATE", "ADDRESS_TO",
								"where PRIVATE_SNO=" + OTHERS_PRIVATE_SNO), 3);
					/* Date : 21/02/2011 : Other Charges Proces */
					/*
					 * old code String Other_charge_cond1 = " where  CASHBOOK_MONTH="+ billmonth+
					 * " and CASHBOOK_YEAR="+ billyear + " and BENEFICIARY_SNO ="+ ben+
					 * " and OFFICE_ID =" + Office_id+
					 * " and CR_DR_INDICATOR='DR' group by BENEFICIARY_SNO"; String
					 * Other_charge_cond2 = " where CASHBOOK_MONTH="+ billmonth+
					 * " and CASHBOOK_YEAR="+ billyear + " and BENEFICIARY_SNO ="+ ben+
					 * " and OFFICE_ID ="+ Office_id +
					 * " and CR_DR_INDICATOR='CR' group by BENEFICIARY_SNO"; String
					 * Other_charge_cond1_up = " where  CASHBOOK_MONTH="+ billmonth+
					 * " and CASHBOOK_YEAR="+ billyear + " and BENEFICIARY_SNO ="+ ben+
					 * " and  CONFIRM_FLAG='Y'  and OFFICE_ID ="+ Office_id+
					 * " and CR_DR_INDICATOR='DR' "; String Other_charge_cond2_up =
					 * " where CASHBOOK_MONTH="+ billmonth + " and CASHBOOK_YEAR=" + billyear +
					 * " and BENEFICIARY_SNO =" + ben+ "  and  CONFIRM_FLAG='Y'  and OFFICE_ID =" +
					 * Office_id+ " and CR_DR_INDICATOR='CR' ";
					 */

					String Other_charge_cond1 = " where  OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='DR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and BENEFICIARY_SNO =" + ben + "  ";
					Other_charge_cond1 += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7) group by BENEFICIARY_SNO";

					String Other_charge_cond2 = " where OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='CR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and BENEFICIARY_SNO =" + ben + "";
					Other_charge_cond2 += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7)  group by BENEFICIARY_SNO ";

					String Other_charge_cond1_MC = " where OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='DR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and  CONFIRM_FLAG='Y'  and BENEFICIARY_SNO =" + ben + " ";
					Other_charge_cond1_MC += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8)";

					String Other_charge_cond2_MC = " where OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='CR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and  CONFIRM_FLAG='Y'  and BENEFICIARY_SNO =" + ben + "  ";
					Other_charge_cond2_MC += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8)";

					String Other_charge_cond1_up = " where  OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='DR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and  CONFIRM_FLAG='Y'  and BENEFICIARY_SNO =" + ben + " ";
					Other_charge_cond1_up += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7)";

					String Other_charge_cond2_up = " where OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='CR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and  CONFIRM_FLAG='Y'  and BENEFICIARY_SNO =" + ben + "  ";
					Other_charge_cond2_up += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7)";

					String Other_charge_cond1_up_MC = " where  OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='DR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and  CONFIRM_FLAG='Y'  and BENEFICIARY_SNO =" + ben + " ";
					Other_charge_cond1_up_MC += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8)";

					String Other_charge_cond2_up_MC = " where OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='CR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and  CONFIRM_FLAG='Y'  and BENEFICIARY_SNO =" + ben + "  ";
					Other_charge_cond2_up_MC += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8)";
					// other charges add
					String ADD_CHARGES_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)", Other_charge_cond1); // get
																														// DR
																														// amount
					String MINUS_CHARGES_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)", Other_charge_cond2); // get
																														// CR
																														// amount
					// 03 - 09 - 2012
					String ADD_CHARGES_MC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)", Other_charge_cond1_MC); // get
																															// DR
																															// amount
					String MINUS_CHARGES_MC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",
							Other_charge_cond2_MC); // get CR amount
					// 03 - 09 - 2012

					int maxsno, tran_row = 0;
					int prv_month = 0, prv_year = 0, ben_sno = 0, sch_sno = 0, next_month = 0, nextyear = 0;
					/* Date : 21/02/2011 : Other Charges End */

					maxsno = obj.getMax("PMS_DCB_TRN_BILL_DMD", "BILL_SNO", "");
					String ins_qry = "insert into PMS_DCB_TRN_BILL_DMD (BILL_SNO,OFFICE_ID,BENEFICIARY_SNO,BILL_PERIOD_FROM,"
							+ "BILL_PERIOD_TO,BILL_MONTH,BILL_YEAR,DIV_BILL_NO,NET_CONSUMPTION,OB_MAINT_CHARGES,OB_CUR_YR_WC,"
							+ "OB_YESTER_YR_WC,WC_MTH_TOTAL,INT_PERCENT,INT_CALC_WC,MONTH_BILL_AMT,BILL_RAISED_BY,BILLING_DT,"
							+ "UPDATED_BY_USER_ID,UPDATED_DATE,DMD_INT_FOR_MTH_WC,OB_INT_AMT_WC,ADDRESS_TO,"
							+ "ADD_CHARGES_WC,MINUS_CHARGES_WC ,COLN_INT_WC,ADD_CHARGES_MAINT,MINUS_CHARGES_MAINT) "
							+ " values " + " (";

					// Demand Already Generate Test here

					if (ben_dmd_count == 0) {
						total_demand++;
						try {
							ins_qry = ins_qry + maxsno + "" + "," + Office_id + "" + "," + ben + ",to_date('"
									+ BILL_PERIOD_FROM + "','DD/MM/YYYY')," + " to_date('" + BILL_PERIOD_TO
									+ "','DD/MM/YYYY')," + "" + billmonth + "," + billyear + "," + DIV_BILL_NO + ","
									+ obj.isNull(rs.getString("net_consumed"), 1) + ","
									+ obj.isNull(rs.getString("CB_MAINT_CHARGES"), 1) + ","
									+ obj.isNull(rs.getString("CB_CUR_YR_WC"), 1) + ","
									+ obj.isNull(rs.getString("CB_YESTER_YR_WC"), 1) + "," + WC_MTH_TOTAL + ","
									+ ben_int + "," + Math.round(int_calc2) + ","
									+ Math.round(Double.parseDouble(obj.isNull(rs.getString("MONTH_BILL_AMT"), 1)))
									+ "," + Office_id + ",to_date('" + date + "','DD/MM/YYYY')" + ",'" + userid + "',"
									+ "clock_timestamp()," + DMD_INT_FOR_MTH_WC + ", " + OB_INT_AMT_WC + "" + " ,'"
									+ short_name + "'," + ADD_CHARGES_WC + "," + MINUS_CHARGES_WC + ","
									+ int_collection_ben + "," + ADD_CHARGES_MC + "," + MINUS_CHARGES_MC + ")";

						} catch (SQLException e) {
							maxsno = obj.getMax("PMS_DCB_TRN_BILL_DMD", "BILL_SNO", "");
							ins_qry = ins_qry + maxsno + "" + "," + Office_id + "" + "," + ben + ",to_date('"
									+ BILL_PERIOD_FROM + "','DD/MM/YYYY')," + " to_date('" + BILL_PERIOD_TO
									+ "','DD/MM/YYYY')," + "" + billmonth + "," + billyear + "," + DIV_BILL_NO + ","
									+ obj.isNull(rs.getString("net_consumed"), 1) + ","
									+ obj.isNull(rs.getString("CB_MAINT_CHARGES"), 1) + ","
									+ obj.isNull(rs.getString("CB_CUR_YR_WC"), 1) + ","
									+ obj.isNull(rs.getString("CB_YESTER_YR_WC"), 1) + "," + WC_MTH_TOTAL + ","
									+ ben_int + "," + Math.round(int_calc2) + ","
									+ Math.round(Double.parseDouble(obj.isNull(rs.getString("MONTH_BILL_AMT"), 1)))
									+ "," + Office_id + ",to_date('" + date + "','DD/MM/YYYY')" + ",'" + userid + "',"
									+ "clock_timestamp()," + DMD_INT_FOR_MTH_WC + ", " + OB_INT_AMT_WC + "" + " ,'"
									+ short_name + "'," + ADD_CHARGES_WC + "," + MINUS_CHARGES_WC + ","
									+ int_collection_ben + "," + ADD_CHARGES_MC + "," + MINUS_CHARGES_MC + ")";
							System.out.println(e);
						} catch (Exception es) {

						}
						int ben_dmd_count2 = obj.getCount("PMS_DCB_TRN_BILL_DMD", "where BENEFICIARY_SNO=" + ben
								+ " and BILL_YEAR=" + billyear + " and   BILL_MONTH=" + billmonth);
						if (ben_dmd_count2 == 0) {
							row += obj.setUpd(ins_qry);
							String bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO=" + maxsno + " "
									+ Other_charge_cond1_up;
							int b_up_row = obj.rowUpdate(bill_sno_update);
							bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO=" + maxsno + " "
									+ Other_charge_cond2_up;
							b_up_row = obj.rowUpdate(bill_sno_update);
							// 03 - 09 - 2012
							bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO=" + maxsno + " "
									+ Other_charge_cond1_up_MC;
							b_up_row = obj.rowUpdate(bill_sno_update);
							bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO=" + maxsno + " "
									+ Other_charge_cond2_up_MC;
							b_up_row = obj.rowUpdate(bill_sno_update);
						}
						// 03 - 09 - 2012
					} else {
						already_demand++;
					}

					double sch_OB_INT_AMT_WC = 0.0, str_CB_INT_AMT_WC = 0.0;
					String up_qry = "";
					String OB_MAINT_CHARGES = "0", OB_CUR_YR_WC = "0", OB_YESTER_YR_WC = "0";
					String COLN_CUR_YR_WC = "0", COLN_YESTER_YR_WC = "0", COLN_MAINT = "0", COLN_INT_FOR_MTH_WC = "0";
					String CB_MAINT_CHARGES = "0", CB_CUR_YR_WC = "0", CB_YESTER_YR_WC = "0";
					String CB_INT_CUR_YR_MAINT = "0", CB_INT_AMT_WC = "0";
					net_CB_CUR_YR_WC = 0;
					net_CB_MAINT = 0;
					net_CB_YESTER_YR_WC = 0;
					net_COLN_CUR_YR_WC = 0;
					net_COLN_MAINT = 0;
					net_COLN_YESTER_YR_WC = 0;

					qry_ins = "  select  wc.BENEFICIARY_SNO, wc.SCHEME_SNO,  wc.TOTAL_AMT, wc.QTY_CONSUMED,ob.CB_MAINT_CHARGES, "
							+ " ob.CB_CUR_YR_WC,ob.CB_YESTER_YR_WC,wc.SCHEME_SNO,ob.MONTH,ob.FIN_YEAR,"
							+ " ob.CB_INT_AMT_WC,ob.CB_INT_CUR_YR_MAINT from  "
							+ " (  (  select BENEFICIARY_SNO,SCHEME_SNO ,sum(TOTAL_AMT) as TOTAL_AMT, sum(QTY_CONSUMED) as QTY_CONSUMED "
							+ "       from PMS_DCB_WC_BILLING where MONTH=" + billmonth + " and OFFICE_ID=" + Office_id
							+ " and  " + "       YEAR =" + billyear + " and " + " BENEFICIARY_SNO=" + ben
							+ "   group by  BENEFICIARY_SNO ,MONTH  ,YEAR,SCHEME_SNO " + "    )wc  " + "  left join  "
							+ "  ( " + "  select  "
							+ "      CB_MAINT_CHARGES,CB_CUR_YR_WC,CB_YESTER_YR_WC,SCH_SNO,MONTH,FIN_YEAR,BENEFICIARY_SNO,CB_INT_AMT_WC,CB_INT_CUR_YR_MAINT "
							+ "  from  PMS_DCB_TRN_CB_MONTHLY  where BENEFICIARY_SNO=" + ben + " and MONTH=" + month_set
							+ " AND FIN_YEAR=" + year_set + "  )ob "
							+ " on ob.BENEFICIARY_SNO= wc.BENEFICIARY_SNO   and ob.SCH_SNO=wc.SCHEME_SNO " + "" + " ) ";

					rs_wc = stmt2.executeQuery(qry_ins);
					String sc = "";
					while (rs_wc.next()) {
						up_qry = "";
						OB_MAINT_CHARGES = obj.isNull(rs_wc.getString("CB_MAINT_CHARGES"), 1);
						OB_YESTER_YR_WC = obj.isNull(rs_wc.getString("CB_YESTER_YR_WC"), 1);
						OB_CUR_YR_WC = obj.isNull(rs_wc.getString("CB_CUR_YR_WC"), 1);
						OB_INT_CUR_YR_MAINT = obj.isNull(rs_wc.getString("CB_INT_CUR_YR_MAINT"), 1);
						sch_OB_INT_AMT_WC = Double.parseDouble(obj.isNull(rs_wc.getString("CB_INT_AMT_WC"), 1));
						ins_qry = " insert into PMS_DCB_TRN_BILL_DMD_SCH" + "( BILL_SNO," + " OFFICE_ID,"
								+ " BENEFICIARY_SNO," + " SCH_NO," + " NET_CONSUMPTION," + " OB_MAINT_CHARGES,"
								+ " OB_CUR_YR_WC," + " OB_YESTER_YR_WC," + "WC_MTH_TOTAL ," + "COLN_CUR_YR_WC,"
								+ "COLN_MAINT ," + "COLN_YESTER_YR_WC," + "CB_MAINT_CHARGES,"
								+ "CB_CUR_YR_WC,CB_YESTER_YR_WC,OB_INT_AMT_WC,CB_INT_AMT_WC ,COLN_INT_WC)";

						sc = obj.isNull(rs_wc.getString("SCHEME_SNO"), 1);
						String sch_amt = obj.isNull(rs_wc.getString("TOTAL_AMT"), 1);
						String stype = obj.isNull(obj.getValue("PMS_SCH_MASTER", "SCH_TYPE_ID", "where SCH_SNO=" + sc),
								1);

						ins_qry += " values (" + "" + maxsno + "," + Office_id + "," + ben + "," + sc + ","
								+ rs_wc.getString("QTY_CONSUMED") + "," + OB_MAINT_CHARGES + "," + OB_CUR_YR_WC + ","
								+ OB_YESTER_YR_WC + "," + sch_amt;

						// FAS RECEIPT COLLECTION

						String cond1 = " and collection_type=6 )"; // Yester Year Collection
						String cond2 = " and collection_type=7 )"; // Current Year Water Charges
						String cond3 = " and collection_type=8 )"; // maintence
						String cond4 = " and collection_type=9 )"; // Interest

						String main_cond = " AND cashbook_month =  " + billmonth + "	 AND cashbook_year =  "
								+ billyear + "    AND accounting_unit_id =  " + ACCOUNTING_UNIT_ID
								+ " 	 AND accounting_for_office_id = " + Office_id
								+ "    and sub_ledger_type_code = 10"
								+ " 	 and SUB_LEDGER_CODE in (select PROJECT_ID from PMS_MST_PROJECTS_VIEW where SCH_SNO= "
								+ sc + ")" + "	 AND receipt_no IN " + "   (SELECT receipt_no"
								+ "   FROM fas_receipt_master "
								+ "   WHERE RECEIPT_STATUS='L' and sub_ledger_type_code = 14"
								+ "    AND cashbook_month = " + billmonth + "    AND cashbook_year = " + billyear
								+ "    AND accounting_unit_id = " + ACCOUNTING_UNIT_ID
								+ "   AND accounting_for_office_id = " + Office_id + "    AND sub_ledger_code =" + ben
								+ "  )  and ACCOUNT_HEAD_CODE " + "  in " + "  ( select ACCOUNT_HEAD_CODE "
								+ "    from PMS_DCB_RECEIPT_ACCOUNT_MAP " + "   where sch_type_id =" + stype
								+ " and ACTIVE_STATUS='L' ";

						COLN_MAINT = "0";
						COLN_YESTER_YR_WC = "0";
						System.out.println("Stage Monthly Insert Start midde -1 ");
						try {
							stmt4 = con1.createStatement();
							rs_coll = stmt4.executeQuery(
									"select sum(amount)from fas_receipt_transaction where   sub_ledger_type_code = 10 "
											+ " " + main_cond + "" + cond1);
							if (rs_coll.next()) {
								COLN_YESTER_YR_WC = obj2.isNull(rs_coll.getString(1), 1);
							}
							stmt4.close();
							rs_coll.close();
						} catch (Exception e) {
							System.out.println(" PMS--->DCB--->Bill_Demand ( 583 )--> " + e);
							COLN_YESTER_YR_WC = "0";
							stmt4.close();
							rs_coll.close();
						}

						// Current Year Collection From FAS

						// System.out.println("Collection ===" +" select sum(amount) from
						// fas_receipt_transaction where sub_ledger_type_code = 10 "+ " " + main_cond +
						// "" + cond2);
						try {
							stmt4 = con1.createStatement();
							rs_coll = stmt4.executeQuery(
									"select sum(amount) from fas_receipt_transaction where   sub_ledger_type_code = 10 "
											+ " " + main_cond + "" + cond2);
							if (rs_coll.next()) {
								COLN_CUR_YR_WC = obj2.isNull(rs_coll.getString(1), 1);
							}
							stmt4.close();
							rs_coll.close();
						} catch (Exception e) {
							System.out.println(" PMS--->DCB--->Bill_Demand ( 610 )--> " + e);
							COLN_CUR_YR_WC = "0";
							stmt4.close();
							rs_coll.close();
						}
						// Current Year Collection From FAS End
						// MAINT Collection Start
						try {
							stmt4 = con1.createStatement();
							rs_coll = stmt4.executeQuery(
									"select sum(amount) from fas_receipt_transaction where sub_ledger_type_code=10 "
											+ " " + main_cond + "" + cond3);
							if (rs_coll.next()) {
								COLN_MAINT = obj2.isNull(rs_coll.getString(1), 1);
							}
							stmt4.close();
							rs_coll.close();
						} catch (Exception e) {
							System.out.println(" PMS--->DCB--->Bill_Demand ( 636 )--> " + e);
							COLN_MAINT = "0";
							stmt4.close();
							rs_coll.close();
						}
						// MAINT Collection End
						// Interest Collection Start

						try {
							stmt5 = con1.createStatement();
							rs_coll = stmt5.executeQuery(
									"select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
											+ " " + main_cond + "" + cond4);
							if (rs_coll.next()) {
								COLN_INT_FOR_MTH_WC = obj2.isNull(rs_coll.getString(1), 1);
							} else {
								COLN_INT_FOR_MTH_WC = "0";
							}
							stmt5.close();
							rs_coll.close();
						} catch (Exception e) {
							System.out.println(" PMS--->DCB--->Bill_Demand ( 662 )--> " + e);
							COLN_INT_FOR_MTH_WC = "0";
							stmt5.close();
							rs_coll.close();
						}
						// Interest Collection End
						OB_YESTER_YR_WC = obj.isNull(OB_YESTER_YR_WC, 1);
						OB_CUR_YR_WC = obj.isNull(OB_CUR_YR_WC, 1);
						OB_MAINT_CHARGES = obj.isNull(OB_MAINT_CHARGES, 1);
						// New 10 05 2013 START
						int cbyear_set = 0;
						if (Integer.parseInt(billmonth) < 4)
							cbyear_set = Integer.parseInt(billyear) - 1;
						else
							cbyear_set = Integer.parseInt(billyear);

						// ----------------------- New Code 07 05 2013
						String ob_cur_yr_wc_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_CUR_YR_WC", " where
														// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH=3 AND
														// FIN_YEAR="+cbyear_set);
						String ob_cur_yr_maint_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_MAINT_CHARGES", "
															// where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and
															// MONTH=3 AND FIN_YEAR="+cbyear_set);;
						String ob_cur_yr_maint_int_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_CUR_YR_MAINT",
																// " where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																// and MONTH=3 AND FIN_YEAR="+cbyear_set);;
						String ob_cur_yr_yester_year_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_YESTER_YR_WC",
																// " where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																// and MONTH=3 AND FIN_YEAR="+cbyear_set);;
						String ob_cur_yr_int_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_AMT_WC", " where
														// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH=3 AND
														// FIN_YEAR="+cbyear_set);;

						String qry_new_2 = "select CB_CUR_YR_WC,CB_MAINT_CHARGES,CB_INT_CUR_YR_MAINT,CB_YESTER_YR_WC,CB_INT_AMT_WC from PMS_DCB_TRN_CB_MONTHLY ";
						qry_new_2 += "where sch_sno=" + sc + " and MONTH=3 AND FIN_YEAR=" + cbyear_set
								+ "  and BENEFICIARY_SNO=" + ben + "";

						ResultSet rs_new_2 = null;
						rs_new_2 = obj.getRS(qry_new_2);
						if (rs_new_2.next()) {
							ob_cur_yr_wc_3rd = obj.isNull(rs_new_2.getString("CB_CUR_YR_WC"), 1);
							ob_cur_yr_maint_3rd = obj.isNull(rs_new_2.getString("CB_MAINT_CHARGES"), 1);
							ob_cur_yr_maint_int_3rd = obj.isNull(rs_new_2.getString("CB_INT_CUR_YR_MAINT"), 1);
							ob_cur_yr_yester_year_3rd = obj.isNull(rs_new_2.getString("CB_YESTER_YR_WC"), 1);
							ob_cur_yr_int_3rd = obj.isNull(rs_new_2.getString("CB_INT_AMT_WC"), 1);
						}
						rs_new_2.close();
						String DMD_UPTO_MTH_WC_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																// "DMD_UPTO_MTH_WC", " where BENEFICIARY_SNO="+ben+"
																// and sch_sno="+sc+" and MONTH="+month_set+" AND
																// FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_WC_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																// "COLN_UPTO_MTH_WC", " where BENEFICIARY_SNO="+ben+"
																// and sch_sno="+sc+" and MONTH="+month_set+" AND
																// FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_MC_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																// "COLN_UPTO_MTH_MAINT", " where
																// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and
																// MONTH="+month_set+" AND FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_yesteryr_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																		// "COLN_UPTO_MTH_YESTER_YR", " where
																		// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																		// and MONTH="+month_set+" AND
																		// FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_maint_int_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																		// "COLN_INT_UPTO_MTH_MAINT", " where
																		// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																		// and MONTH="+month_set+" AND
																		// FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_cur_yr_int_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																		// "COLN_INT_UPTO_MTH_WC", " where
																		// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																		// and MONTH="+month_set+" AND
																		// FIN_YEAR="+year_set);

						String qry_new = "select DMD_UPTO_MTH_WC,COLN_UPTO_MTH_WC,COLN_UPTO_MTH_MAINT,COLN_UPTO_MTH_YESTER_YR,COLN_INT_UPTO_MTH_MAINT,COLN_INT_UPTO_MTH_WC from PMS_DCB_TRN_CB_MONTHLY ";
						qry_new += "  where sch_sno=" + sc + " and MONTH=" + month_set + " AND FIN_YEAR=" + year_set
								+ "  and BENEFICIARY_SNO=" + ben + "";
						ResultSet rs_new_1 = null;
						rs_new_1 = obj.getRS(qry_new);
						if (rs_new_1.next()) {
							DMD_UPTO_MTH_WC_prv_month = obj.isNull(rs_new_1.getString("DMD_UPTO_MTH_WC"), 1);
							COLN_UPTO_MTH_WC_prv_month = obj.isNull(rs_new_1.getString("COLN_UPTO_MTH_WC"), 1);
							COLN_UPTO_MTH_MC_prv_month = obj.isNull(rs_new_1.getString("COLN_UPTO_MTH_MAINT"), 1);
							COLN_UPTO_MTH_yesteryr_prv_month = obj.isNull(rs_new_1.getString("COLN_UPTO_MTH_YESTER_YR"),
									1);
							COLN_UPTO_MTH_maint_int_prv_month = obj
									.isNull(rs_new_1.getString("COLN_INT_UPTO_MTH_MAINT"), 1);
							COLN_UPTO_MTH_cur_yr_int_prv_month = obj.isNull(rs_new_1.getString("COLN_INT_UPTO_MTH_WC"),
									1);
						}
						rs_new_1.close();

						// Prov Code : 07 05 2013 CB_CUR_YR_WC = Double.toString(
						// (Double.parseDouble(OB_CUR_YR_WC) + Double.parseDouble(sch_amt) )-
						// Double.parseDouble(COLN_CUR_YR_WC));
						double OB_CUR_YR_WC_NEW = (Double.parseDouble(ob_cur_yr_wc_3rd)
								+ Double.parseDouble(DMD_UPTO_MTH_WC_prv_month))
								- Double.parseDouble(COLN_UPTO_MTH_WC_prv_month);
						OB_CUR_YR_WC = Double.toString(OB_CUR_YR_WC_NEW);
						CB_CUR_YR_WC = Double.toString(
								(OB_CUR_YR_WC_NEW + Double.parseDouble(sch_amt)) - Double.parseDouble(COLN_CUR_YR_WC));
						CB_CUR_YR_WC = Double.toString(Double.parseDouble(CB_CUR_YR_WC)
								+ Double.parseDouble(ADD_CHARGES_WC) - Double.parseDouble(MINUS_CHARGES_WC));

						// Maint CB fin yr
						double OB_CUR_YR_maint_NEW = (Double.parseDouble(ob_cur_yr_maint_3rd))
								- Double.parseDouble(COLN_UPTO_MTH_MC_prv_month);
						OB_MAINT_CHARGES = Double.toString(OB_CUR_YR_maint_NEW);
						CB_MAINT_CHARGES = Double
								.toString(Double.parseDouble(OB_MAINT_CHARGES) - Double.parseDouble(COLN_MAINT));
						CB_MAINT_CHARGES = Double.toString(Double.parseDouble(CB_MAINT_CHARGES)
								+ Double.parseDouble(ADD_CHARGES_MC) - Double.parseDouble(MINUS_CHARGES_MC));
						// Yester yr CB fin yr
						double OB_CUR_YR_yesteryr_NEW = (Double.parseDouble(ob_cur_yr_yester_year_3rd))
								- Double.parseDouble(COLN_UPTO_MTH_yesteryr_prv_month);
						OB_YESTER_YR_WC = Double.toString(OB_CUR_YR_yesteryr_NEW);
						CB_YESTER_YR_WC = Double
								.toString(Double.parseDouble(OB_YESTER_YR_WC) - Double.parseDouble(COLN_YESTER_YR_WC));
						// maint int adds if any cb fin yr

						double OB_CUR_YR_maint_int_NEW = (Double.parseDouble(ob_cur_yr_maint_int_3rd));
						OB_INT_CUR_YR_MAINT = Double.toString(OB_CUR_YR_maint_int_NEW);
						CB_INT_CUR_YR_MAINT = Double
								.toString(Double.parseDouble(OB_INT_CUR_YR_MAINT) - Double.parseDouble("0"));
						// wc int cb fin yr
						CB_INT_AMT_WC = "0";
						double OB_CUR_YR_wc_int_NEW = (Double.parseDouble(ob_cur_yr_int_3rd))
								- Double.parseDouble(COLN_UPTO_MTH_cur_yr_int_prv_month);
						OB_INT_AMT_WC = Double.toString(OB_CUR_YR_wc_int_NEW);
						CB_INT_AMT_WC = Double.toString(int_calc2
								+ (Double.parseDouble(OB_INT_AMT_WC) - Double.parseDouble(COLN_INT_FOR_MTH_WC)));

						System.out.println("Stage Monthly Insert Start middel 0 ");

						// New 10 05 2013 END
						/*
						 * CB_YESTER_YR_WC = Double.toString(Double.parseDouble(OB_YESTER_YR_WC)-
						 * Double.parseDouble(COLN_YESTER_YR_WC)); //CB_WC=(OB_WC + Demand)-collection
						 * CB_CUR_YR_WC = Double.toString( (Double.parseDouble(OB_CUR_YR_WC) +
						 * Double.parseDouble(sch_amt) )- Double.parseDouble(COLN_CUR_YR_WC));
						 * CB_CUR_YR_WC= Double.toString(
						 * Double.parseDouble(CB_CUR_YR_WC)+Double.parseDouble(ADD_CHARGES_WC)-Double.
						 * parseDouble(MINUS_CHARGES_WC));
						 */

						String Other_charge_cond3_up = " where   OFFICE_ID::int =" + Office_id
								+ " and CR_DR_INDICATOR='DR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
								+ billyear + " and BENEFICIARY_SNO =" + ben + "  ";
						String Other_charge_cond4_up = " where  OFFICE_ID::int =" + Office_id
								+ " and CR_DR_INDICATOR='CR' and CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
								+ billyear + " and BENEFICIARY_SNO =" + ben + "  ";
						System.out.println("Stage Monthly Insert Start middel ");
						String include_CB_ADD = "update PMS_DCB_OTHER_CHARGES set INCLUDE_CB='Y'"
								+ Other_charge_cond3_up;
						int add_affected_row = obj.rowUpdate(include_CB_ADD);
						String include_CB_SUB = "update PMS_DCB_OTHER_CHARGES set INCLUDE_CB='Y'"
								+ Other_charge_cond4_up;
						int sub_affected_row = obj.rowUpdate(include_CB_SUB);

						CB_MAINT_CHARGES = Double
								.toString(Double.parseDouble(OB_MAINT_CHARGES) - Double.parseDouble(COLN_MAINT));
						net_CB_CUR_YR_WC += Double.parseDouble(CB_CUR_YR_WC);
						net_CB_MAINT += Double.parseDouble(CB_MAINT_CHARGES);
						net_CB_YESTER_YR_WC += Double.parseDouble(CB_YESTER_YR_WC);
						net_COLN_CUR_YR_WC += Double.parseDouble(COLN_CUR_YR_WC);
						net_COLN_MAINT += Double.parseDouble(COLN_MAINT);
						net_COLN_YESTER_YR_WC += Double.parseDouble(COLN_YESTER_YR_WC);

						str_CB_INT_AMT_WC = Math
								.round(int_calc2 + (sch_OB_INT_AMT_WC - Double.parseDouble(COLN_INT_FOR_MTH_WC)));

						ins_qry += "," + COLN_CUR_YR_WC + "," + COLN_MAINT + "," + COLN_YESTER_YR_WC + ","
								+ CB_MAINT_CHARGES + "," + "" + CB_CUR_YR_WC + "," + CB_YESTER_YR_WC + ","
								+ sch_OB_INT_AMT_WC + "," + str_CB_INT_AMT_WC + "," + COLN_INT_FOR_MTH_WC + ")";

						if (ben_int == null)
							ben_int = "0";
						int sch_count = obj.getCount("PMS_DCB_TRN_BILL_DMD_SCH", "where BENEFICIARY_SNO=" + ben
								+ " and   BILL_SNO in (select Bill_sno from PMS_DCB_TRN_BILL_DMD where BILL_MONTH="
								+ billmonth + " and  BILL_YEAR=" + billyear + "  and BENEFICIARY_SNO=" + ben
								+ "   ) and SCH_NO=" + sc);
						if (sch_count == 0)
							row += obj.setUpd(ins_qry);
						// Demand Scheme Complete here
						/*
						 * Data Store in PMS_DCB_TRN_CB_MONTHLY table for month by ben schecme wise
						 */
						// Monthly Table insert
						int c = obj.getCount("PMS_DCB_TRN_CB_MONTHLY", "where MONTH=" + billmonth + " and SCH_SNO=" + sc
								+ " and FIN_YEAR=" + billyear + " and BENEFICIARY_SNO=" + ben + "  ");
						if (c > 0) {
							String del_qry = "delete from PMS_DCB_TRN_CB_MONTHLY  where FIN_YEAR=" + billyear
									+ " and   MONTH=" + billmonth + " and SCH_SNO=" + sc + " and BENEFICIARY_SNO=" + ben
									+ "";
							ps = con.prepareStatement(del_qry);
							ps.executeUpdate();
						}

						// if Record is there in PMS_DCB_TRN_CB_MONTHLY

						c = obj.getCount("PMS_DCB_TRN_CB_MONTHLY", "where FIN_YEAR=" + billyear + " and   MONTH="
								+ billmonth + " and SCH_SNO=" + sc + "and  BENEFICIARY_SNO=" + ben + " ");
						int apr_year = 0;
						if (c == 0) {
							/* Date : 11/01/2011 */
							if (Integer.parseInt(billmonth) == 1) {
								prv_month = 12;
								prv_year = Integer.parseInt(billyear) - 1;
								apr_year = Integer.parseInt(billyear) - 1;
								ben_sno = Integer.parseInt(ben);
								sch_sno = Integer.parseInt(sc);
							} else {
								prv_month = Integer.parseInt(billmonth) - 1;
								if (Integer.parseInt(billmonth) <= 3) {
									apr_year = Integer.parseInt(billyear) - 1;
								} else {
									apr_year = Integer.parseInt(billyear);
								}
								prv_year = Integer.parseInt(billyear);
								ben_sno = Integer.parseInt(ben);
								sch_sno = Integer.parseInt(sc);
							}

							String cond_str = "where FIN_YEAR=" + prv_year + " and   MONTH=" + prv_month
									+ " and SCH_SNO=" + sch_sno + " and BENEFICIARY_SNO=" + ben_sno;
							String cond_str_apr = "where FIN_YEAR=" + apr_year + " and   MONTH=4" + " and SCH_SNO="
									+ sch_sno + " and BENEFICIARY_SNO=" + ben_sno;
							String cond_str_cur = "where FIN_YEAR=" + billyear + " and   MONTH=" + billmonth
									+ " and SCH_SNO=" + sch_sno + " and BENEFICIARY_SNO=" + ben_sno;
							String DMD_UPTO_MTH_WC = "0", COLN_UPTO_MTH_WC = "", COLN_UPTO_MTH_MAINT = "",
									COLN_UPTO_MTH_YESTER_YR = "", COLN_INT_UPTO_MTH_MAINT = "",
									COLN_INT_UPTO_MTH_WC = "", DMD_INT_UPTO_MTH_WC = "";
							// if month is 4 reset to 0 all UPTO field for yearending process
							if (billmonth.equalsIgnoreCase("4")) {
								DMD_UPTO_MTH_WC = "0";
								COLN_UPTO_MTH_WC = "0";
								COLN_UPTO_MTH_MAINT = "0";
								COLN_UPTO_MTH_YESTER_YR = "0";
								COLN_INT_UPTO_MTH_MAINT = "0";
								COLN_INT_UPTO_MTH_WC = "0";
								DMD_INT_UPTO_MTH_WC = "0";
							} else {
								String qry_new_3 = "select DMD_UPTO_MTH_WC,COLN_UPTO_MTH_WC,COLN_UPTO_MTH_MAINT,COLN_UPTO_MTH_YESTER_YR,COLN_INT_UPTO_MTH_MAINT,COLN_INT_UPTO_MTH_WC,DMD_INT_UPTO_MTH_WC from PMS_DCB_TRN_CB_MONTHLY ";
								qry_new_3 += cond_str;
								ResultSet rs_new_3 = null;
								rs_new_3 = obj.getRS(qry_new_3);
								if (rs_new_3.next()) {
									DMD_UPTO_MTH_WC = obj.isNull(rs_new_3.getString("DMD_UPTO_MTH_WC"), 1);
									COLN_UPTO_MTH_WC = obj.isNull(rs_new_3.getString("COLN_UPTO_MTH_WC"), 1);
									COLN_UPTO_MTH_MAINT = obj.isNull(rs_new_3.getString("COLN_UPTO_MTH_MAINT"), 1);
									COLN_UPTO_MTH_YESTER_YR = obj.isNull(rs_new_3.getString("COLN_UPTO_MTH_YESTER_YR"),
											1);
									COLN_INT_UPTO_MTH_MAINT = obj.isNull(rs_new_3.getString("COLN_INT_UPTO_MTH_MAINT"),
											1);
									COLN_INT_UPTO_MTH_WC = obj.isNull(rs_new_3.getString("COLN_INT_UPTO_MTH_WC"), 1);
									DMD_INT_UPTO_MTH_WC = obj.isNull(rs_new_3.getString("DMD_INT_UPTO_MTH_WC"), 1);
								}
								rs_new_3.close();

								/*
								 * DMD_UPTO_MTH_WC = obj.isNull(
								 * obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_UPTO_MTH_WC",
								 * cond_str),1);//cond_str prv COLN_UPTO_MTH_WC =
								 * obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_WC",
								 * cond_str),1); COLN_UPTO_MTH_MAINT =
								 * obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_MAINT",
								 * cond_str),1); COLN_UPTO_MTH_YESTER_YR =
								 * obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_YESTER_YR", cond_str);
								 * COLN_INT_UPTO_MTH_MAINT =
								 * obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_MAINT", cond_str);
								 * COLN_INT_UPTO_MTH_WC =
								 * obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_WC", cond_str);
								 * DMD_INT_UPTO_MTH_WC
								 * =obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_INT_UPTO_MTH_WC", cond_str);
								 */
							}
							DMD_UPTO_MTH_WC = Double
									.toString(Double.parseDouble(DMD_UPTO_MTH_WC) + Double.parseDouble(sch_amt)); // PRv
																													// month
																													// dmd_upto_wt_WC
																													// +
																													// cur
																													// month
																													// dmd_for_mth_Wc
							// COLN_UPTO_MTH_WC = Double.toString(Double.parseDouble(COLN_UPTO_MTH_WC)+
							// Double.parseDouble(COLN_CUR_YR_WC) );
							// 03/102/2012
							COLN_UPTO_MTH_WC = Double.toString(Double.parseDouble(COLN_UPTO_MTH_WC)
									+ ((Double.parseDouble(COLN_CUR_YR_WC) - Double.parseDouble(ADD_CHARGES_WC))
											+ Double.parseDouble(MINUS_CHARGES_WC)));
							COLN_UPTO_MTH_MAINT = Double
									.toString(Double.parseDouble(COLN_UPTO_MTH_MAINT) + Double.parseDouble(COLN_MAINT));
							COLN_UPTO_MTH_YESTER_YR = Double.toString(Double.parseDouble(COLN_UPTO_MTH_YESTER_YR)
									+ Double.parseDouble(COLN_YESTER_YR_WC));
							COLN_INT_UPTO_MTH_MAINT = Double
									.toString(Double.parseDouble(COLN_INT_UPTO_MTH_MAINT) + Double.parseDouble("0"));
							COLN_INT_UPTO_MTH_WC = Double.toString(
									Double.parseDouble(COLN_INT_UPTO_MTH_WC) + Double.parseDouble(COLN_INT_FOR_MTH_WC));
							DMD_INT_UPTO_MTH_WC = Double
									.toString(Math.round(Double.parseDouble(DMD_INT_UPTO_MTH_WC) + DMD_INT_FOR_MTH_WC));
							/*
							 * START if CB_INT_AMT_WC closing balacne of int is less than zero 0 as default
							 * 20/06/2011 & All closing Balance < 0 then store 0
							 * 
							 */
							if (Double.parseDouble(CB_INT_AMT_WC) < 0) {
								CB_INT_AMT_WC = "0";
							}
							// if (Double.parseDouble(CB_MAINT_CHARGES)<0) {CB_MAINT_CHARGES="0";}
							// if (Double.parseDouble(CB_CUR_YR_WC)<0) {CB_CUR_YR_WC="0";}
							// if (Double.parseDouble(CB_YESTER_YR_WC)<0) {CB_YESTER_YR_WC="0";}
							// if (Double.parseDouble(CB_INT_CUR_YR_MAINT)<0) {CB_INT_CUR_YR_MAINT="0";}

							/* Date : 11/01/2011 */
							System.out.println("Stage Monthly Insert Start");
							ins_qry2 = "insert into PMS_DCB_TRN_CB_MONTHLY ( BENEFICIARY_OB_SNO,BENEFICIARY_SNO,FIN_YEAR,MONTH,OFFICE_ID,SCH_SNO,"
									+ " CB_MAINT_CHARGES,CB_CUR_YR_WC,CB_YESTER_YR_WC,CB_INT_CUR_YR_MAINT,CB_INT_AMT_WC,DMD_FOR_MTH_WC,"
									+ " DMD_INT_FOR_MTH_WC,COLN_FOR_MTH_MAINT,COLN_FOR_MTH_YESTER_YR,COLN_FOR_MTH_WC,COLN_INT_FOR_MTH_MAINT,"
									+ " COLN_INT_FOR_MTH_WC,UPDATED_BY_USER_ID,UPDATED_DATE ,DMD_UPTO_MTH_WC ,COLN_UPTO_MTH_WC,COLN_UPTO_MTH_MAINT "
									+ ",COLN_UPTO_MTH_YESTER_YR,COLN_INT_UPTO_MTH_MAINT ,COLN_INT_UPTO_MTH_WC,DMD_INT_UPTO_MTH_WC)"
									+ "  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0," + COLN_INT_FOR_MTH_WC + ",'"
									+ userid + "',clock_timestamp(),?,?,?,?,?,?,?) " + "";

							int BENEFICIARY_CB_SNO = obj.getMax("PMS_DCB_TRN_CB_MONTHLY", "BENEFICIARY_OB_SNO", "");
							ps = con.prepareStatement(ins_qry2);
							ps.setInt(1, BENEFICIARY_CB_SNO);
							ps.setInt(2, Integer.parseInt(ben));
							ps.setInt(3, Integer.parseInt(billyear));
							ps.setInt(4, Integer.parseInt(billmonth));
							ps.setInt(5, Integer.parseInt(Office_id));
							ps.setInt(6, Integer.parseInt(sc));
							ps.setDouble(7, Double.parseDouble(CB_MAINT_CHARGES));
							ps.setDouble(8, Double.parseDouble(CB_CUR_YR_WC));
							ps.setDouble(9, Double.parseDouble(CB_YESTER_YR_WC));
							ps.setDouble(10, Double.parseDouble(CB_INT_CUR_YR_MAINT));
							ps.setDouble(11, str_CB_INT_AMT_WC);
							ps.setDouble(12, Double.parseDouble(sch_amt));
							ps.setDouble(13, DMD_INT_FOR_MTH_WC);
							ps.setDouble(14, Double.parseDouble(COLN_MAINT));
							ps.setDouble(15, Double.parseDouble(COLN_YESTER_YR_WC));
							ps.setDouble(16, Double.parseDouble(COLN_CUR_YR_WC));
							ps.setDouble(17, Double.parseDouble(DMD_UPTO_MTH_WC));
							ps.setDouble(18, Double.parseDouble(COLN_UPTO_MTH_WC));
							ps.setDouble(19, Double.parseDouble(COLN_UPTO_MTH_MAINT));
							ps.setDouble(20, Double.parseDouble(COLN_UPTO_MTH_YESTER_YR));
							ps.setDouble(21, Double.parseDouble(COLN_INT_UPTO_MTH_MAINT));
							ps.setDouble(22, Double.parseDouble(COLN_INT_UPTO_MTH_WC));
							ps.setDouble(23, Double.parseDouble(DMD_INT_UPTO_MTH_WC));
							ps.executeUpdate();
							System.out.println("Stage Monthly Insert Start 2");
							str_CB_INT_AMT_WC = 0;
							int_calc2 = 0;

							/* 12/01/2010 */

							String qry_new_apr = "select OB_MAINT_CHARGES,OB_CUR_YR_WC,OB_MAINT_CHARGES,OB_YESTER_YR_WC,OB_INT_PRV_YR_MAINT,OB_INT_CUR_YR_MAINT,OB_INT_AMT_WC from PMS_DCB_OB_YEARLY ";
							qry_new_apr += "" + cond_str_apr;

							String apr_OB_MAINT_CHfARGES = "0";// obj.getValue("PMS_DCB_OB_YEARLY","OB_MAINT_CHARGES",
																// cond_str_apr);
							String apr_OB_CUR_YR_WC = "0";// obj.getValue("PMS_DCB_OB_YEARLY",
															// "OB_CUR_YR_WC",cond_str_apr);
							String apr_OB_MAINT_CHARGES = "0";// obj.getValue("PMS_DCB_OB_YEARLY","OB_MAINT_CHARGES",
																// cond_str_apr);
							String apr_OB_YESTER_YR_WC = "0";// obj.getValue("PMS_DCB_OB_YEARLY",
																// "OB_YESTER_YR_WC",cond_str_apr);
							String apr_OB_INT_PRV_YR_MAINT = "0";// obj.getValue("PMS_DCB_OB_YEARLY","OB_INT_PRV_YR_MAINT",
																	// cond_str_apr);
							String apr_OB_INT_CUR_YR_MAINT = "0";// obj.getValue("PMS_DCB_OB_YEARLY","OB_INT_CUR_YR_MAINT",
																	// cond_str_apr);
							String apr_OB_INT_AMT_WC = "0";// obj.getValue("PMS_DCB_OB_YEARLY",
															// "OB_INT_AMT_WC",cond_str_apr);

							ResultSet rs_new_apr = null;
							rs_new_apr = obj.getRS(qry_new_apr);
							if (rs_new_apr.next()) {
								apr_OB_MAINT_CHfARGES = obj.isNull(rs_new_apr.getString("OB_MAINT_CHARGES"), 1);
								apr_OB_CUR_YR_WC = obj.isNull(rs_new_apr.getString("OB_CUR_YR_WC"), 1);
								apr_OB_MAINT_CHARGES = obj.isNull(rs_new_apr.getString("OB_MAINT_CHARGES"), 1);
								apr_OB_YESTER_YR_WC = obj.isNull(rs_new_apr.getString("OB_YESTER_YR_WC"), 1);
								apr_OB_INT_PRV_YR_MAINT = obj.isNull(rs_new_apr.getString("OB_INT_PRV_YR_MAINT"), 1);
								apr_OB_INT_CUR_YR_MAINT = obj.isNull(rs_new_apr.getString("OB_INT_CUR_YR_MAINT"), 1);
								apr_OB_INT_AMT_WC = obj.isNull(rs_new_apr.getString("OB_INT_AMT_WC"), 1);

							}
							rs_new_apr.close();
							System.out.println("Stage Monthly Insert Start III ");
							String qry_new_apr_yr = "select  DMD_UPTO_MTH_WC,DMD_INT_UPTO_MTH_WC,COLN_UPTO_MTH_MAINT,COLN_UPTO_MTH_YESTER_YR,COLN_UPTO_MTH_WC,COLN_INT_UPTO_MTH_MAINT,COLN_INT_UPTO_MTH_WC from PMS_DCB_TRN_CB_MONTHLY ";
							qry_new_apr_yr += cond_str_cur;
							String yr_OB_FOR_MTH_YESTER_YR_WC = "0", yr_OB_FOR_MTH_INT_CY_MAINT = "0",
									yr_OB_FOR_MTH_INT_AMT_WC = "0", yr_OB_FOR_MTH_MAINT_CHARGES = "0",
									yr_OB_FOR_MTH_CUR_YR_WC = "0", yr_DMD_INT_FOR_MTH_WC = "0";

							String yr_DMD_UPTO_PRV_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_UPTO_MTH_WC",
																// cond_str_cur);
							String yr_DMD_INT_UPTO_PRV_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_INT_UPTO_MTH_WC",cond_str_cur);
							String yr_COLN_UPTO_PRV_MTH_MAINT = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_MAINT",cond_str_cur);
							String yr_COLN_UPTO_PRV_MTH_YESTER_YR = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_YESTER_YR",cond_str_cur);
							String yr_COLN_UPTO_PRV_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_WC",
																	// cond_str_cur);
							String yr_COLN_INT_UPTO_PRV_MTH_MAINT = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_MAINT",cond_str_cur);
							String yr_COLN_INT_UPTO_PRV_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_WC",cond_str_cur);
							ResultSet rs_new_yr = null;
							rs_new_yr = obj.getRS(qry_new_apr_yr);
							if (rs_new_yr.next()) {
								yr_DMD_UPTO_PRV_MTH_WC = obj.isNull(rs_new_yr.getString("DMD_UPTO_MTH_WC"), 1);
								yr_DMD_INT_UPTO_PRV_MTH_WC = obj.isNull(rs_new_yr.getString("DMD_INT_UPTO_MTH_WC"), 1);
								yr_COLN_UPTO_PRV_MTH_MAINT = obj.isNull(rs_new_yr.getString("COLN_UPTO_MTH_MAINT"), 1);
								yr_COLN_UPTO_PRV_MTH_YESTER_YR = obj
										.isNull(rs_new_yr.getString("COLN_UPTO_MTH_YESTER_YR"), 1);
								yr_COLN_UPTO_PRV_MTH_WC = obj.isNull(rs_new_yr.getString("COLN_UPTO_MTH_WC"), 1);
								yr_COLN_INT_UPTO_PRV_MTH_MAINT = obj
										.isNull(rs_new_yr.getString("COLN_INT_UPTO_MTH_MAINT"), 1);
								yr_COLN_INT_UPTO_PRV_MTH_WC = obj.isNull(rs_new_yr.getString("COLN_INT_UPTO_MTH_WC"),
										1);
							}
							rs_new_yr.close();

							int Yearlysno = obj.getMax("PMS_DCB_OB_YEARLY", "BENEFICIARY_OB_SNO", "");
							next_month = 0;
							nextyear = 0;
							if (Integer.parseInt(billmonth) == 12) {
								next_month = 1;
								nextyear = Integer.parseInt(billyear) + 1;
							} else {
								next_month = Integer.parseInt(billmonth) + 1;
								nextyear = Integer.parseInt(billyear);
							}
							str_CB_INT_AMT_WC = 0;
							String cond_str_next = "where FIN_YEAR=" + nextyear + " and MONTH=" + next_month
									+ " and SCH_SNO=" + sch_sno + "  and BENEFICIARY_SNO=" + ben_sno;
							String yearly_insert = "", prv_ = "";
							prv_ = "delete from PMS_DCB_OB_YEARLY " + cond_str_next;

							ps = null;
							ps = con.prepareStatement(prv_);
							ps.execute();

							yearly_insert = "insert into PMS_DCB_OB_YEARLY ( "
									+ "BENEFICIARY_OB_SNO,BENEFICIARY_SNO,FIN_YEAR,MONTH,"
									+ "OFFICE_ID,SCH_SNO,AS_ON_DATE,OB_MAINT_CHARGES,OB_CUR_YR_WC,OB_YESTER_YR_WC,OB_INT_PRV_YR_MAINT,"
									+ "OB_INT_CUR_YR_MAINT,OB_INT_AMT_WC,DMD_UPTO_PRV_MTH_WC,DMD_INT_UPTO_PRV_MTH_WC,COLN_UPTO_PRV_MTH_MAINT,"
									+ "COLN_UPTO_PRV_MTH_YESTER_YR,COLN_UPTO_PRV_MTH_WC,COLN_INT_UPTO_PRV_MTH_MAINT,COLN_INT_UPTO_PRV_MTH_WC,"
									+ "UPDATED_BY_USER_ID,UPDATED_DATE,OB_FOR_MTH_YESTER_YR_WC,OB_FOR_MTH_INT_CY_MAINT,OB_FOR_MTH_INT_AMT_WC,"
									+ "OB_FOR_MTH_MAINT_CHARGES,OB_FOR_MTH_CUR_YR_WC,DMD_INT_FOR_MTH_WC ) ";
							yearly_insert += " values (?,?,?,?,?,?,clock_timestamp(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,clock_timestamp() ,?,?,?,?,?,?)";
							ps = null;
							ps = con.prepareStatement(yearly_insert);
							ps.setInt(1, Yearlysno);
							ps.setString(2, ben);
							ps.setInt(3, nextyear);
							ps.setInt(4, next_month);
							ps.setString(5, Office_id);
							ps.setInt(6, sch_sno);
							ps.setString(7, apr_OB_MAINT_CHARGES);
							ps.setString(8, apr_OB_CUR_YR_WC);
							ps.setString(9, apr_OB_YESTER_YR_WC);
							ps.setString(10, apr_OB_INT_PRV_YR_MAINT);
							ps.setString(11, apr_OB_INT_CUR_YR_MAINT);
							ps.setString(12, apr_OB_INT_AMT_WC);
							ps.setString(13, yr_DMD_UPTO_PRV_MTH_WC);
							ps.setString(14, yr_DMD_INT_UPTO_PRV_MTH_WC);
							ps.setString(15, yr_COLN_UPTO_PRV_MTH_MAINT);
							ps.setString(16, yr_COLN_UPTO_PRV_MTH_YESTER_YR);
							ps.setString(17, yr_COLN_UPTO_PRV_MTH_WC);
							ps.setString(18, yr_COLN_INT_UPTO_PRV_MTH_MAINT);
							ps.setString(19, yr_COLN_INT_UPTO_PRV_MTH_WC);
							ps.setString(20, userid);
							ps.setString(21, yr_OB_FOR_MTH_YESTER_YR_WC);
							ps.setString(22, yr_OB_FOR_MTH_INT_CY_MAINT);
							ps.setString(23, yr_OB_FOR_MTH_INT_AMT_WC);
							ps.setString(24, yr_OB_FOR_MTH_MAINT_CHARGES);
							ps.setString(25, yr_OB_FOR_MTH_CUR_YR_WC);
							ps.setString(26, yr_DMD_INT_FOR_MTH_WC);
							ps.executeUpdate();
							System.out.println(
									" ben ---> " + ben + " sch_sno---- " + sch_sno + " --- DIV_BILL_NO" + DIV_BILL_NO);
							ps = null;
							String prvOB_FOR_MTH_YESTER_YR_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_YESTER_YR_WC",
																	// cond_str_cur);
							String prvOB_FOR_MTH_INT_CY_MAINT = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_CUR_YR_MAINT",cond_str_cur);
							String prvOB_FOR_MTH_INT_AMT_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_AMT_WC",
																	// cond_str_cur);
							String prvOB_FOR_MTH_MAINT_CHARGES = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_MAINT_CHARGES",cond_str_cur);
							String prvOB_FOR_MTH_CUR_YR_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_CUR_YR_WC",
																	// cond_str_cur);
							String prvDMD_INT_FOR_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_INT_FOR_MTH_WC",
																// cond_str_cur);

							// CB Monthly Data update to Yearly Table for the auto ob of next month
							String qry_new_5 = "select CB_YESTER_YR_WC,CB_INT_CUR_YR_MAINT,CB_INT_AMT_WC,CB_MAINT_CHARGES,CB_CUR_YR_WC,DMD_INT_FOR_MTH_WC from PMS_DCB_TRN_CB_MONTHLY ";
							qry_new_5 += cond_str_cur;
							ResultSet rs_new_5 = null;
							rs_new_5 = obj.getRS(qry_new_5);
							if (rs_new_5.next()) {
								prvOB_FOR_MTH_YESTER_YR_WC = obj.isNull(rs_new_5.getString("CB_YESTER_YR_WC"), 1);
								prvOB_FOR_MTH_INT_CY_MAINT = obj.isNull(rs_new_5.getString("CB_INT_CUR_YR_MAINT"), 1);
								prvOB_FOR_MTH_INT_AMT_WC = obj.isNull(rs_new_5.getString("CB_INT_AMT_WC"), 1);
								prvOB_FOR_MTH_MAINT_CHARGES = obj.isNull(rs_new_5.getString("CB_MAINT_CHARGES"), 1);
								prvOB_FOR_MTH_CUR_YR_WC = obj.isNull(rs_new_5.getString("CB_CUR_YR_WC"), 1);
								prvDMD_INT_FOR_MTH_WC = obj.isNull(rs_new_5.getString("DMD_INT_FOR_MTH_WC"), 1);
							}
							rs_new_5.close();

							String yearly_ob_update = " update PMS_DCB_OB_YEARLY set OB_FOR_MTH_YESTER_YR_WC="
									+ prvOB_FOR_MTH_YESTER_YR_WC + ",OB_FOR_MTH_INT_CY_MAINT="
									+ prvOB_FOR_MTH_INT_CY_MAINT + ",OB_FOR_MTH_INT_AMT_WC=" + prvOB_FOR_MTH_INT_AMT_WC
									+ ",OB_FOR_MTH_MAINT_CHARGES=" + prvOB_FOR_MTH_MAINT_CHARGES
									+ ",OB_FOR_MTH_CUR_YR_WC=" + prvOB_FOR_MTH_CUR_YR_WC + ",DMD_INT_FOR_MTH_WC="
									+ prvDMD_INT_FOR_MTH_WC + "   " + cond_str_next;
							ps = con.prepareStatement(yearly_ob_update);
							ps.executeUpdate();
							/* 12/01/2010 END */
							String _zero_set_qry = "";
							/* if Month ==3 then */
							prvOB_FOR_MTH_INT_AMT_WC = "0";
							prvDMD_INT_FOR_MTH_WC = "0";
							apr_OB_INT_AMT_WC = "0";
							prvOB_FOR_MTH_INT_AMT_WC = "0";
							OB_INT_AMT_WC = "0";
							if (billmonth.equalsIgnoreCase("3")) {
								_zero_set_qry = "update PMS_DCB_OB_YEARLY set OB_CUR_YR_WC=OB_FOR_MTH_CUR_YR_WC,"
										+ "OB_YESTER_YR_WC=OB_FOR_MTH_YESTER_YR_WC,"
										+ "OB_MAINT_CHARGES=OB_FOR_MTH_MAINT_CHARGES,"
										+ "OB_INT_AMT_WC=OB_FOR_MTH_INT_AMT_WC,"
										+ "OB_INT_PRV_YR_MAINT=OB_FOR_MTH_INT_CY_MAINT   , COLN_UPTO_PRV_MTH_MAINT=0,COLN_UPTO_PRV_MTH_YESTER_YR=0,COLN_UPTO_PRV_MTH_WC=0,COLN_INT_UPTO_PRV_MTH_MAINT=0,COLN_INT_UPTO_PRV_MTH_WC=0 ,DMD_UPTO_PRV_MTH_WC=0,DMD_INT_UPTO_PRV_MTH_WC=0  WHERE BENEFICIARY_SNO= "
										+ ben + " AND fin_year=" + nextyear + "AND MONTH=" + next_month
										+ " and OFFICE_ID=" + Office_id + " and SCH_SNO=" + sch_sno;
								int up1 = obj.rowUpdate(_zero_set_qry);
								_zero_set_qry = "update PMS_DCB_OB_YEARLY_TOTAL set OB_CUR_YR_WC=OB_FOR_MTH_CUR_YR_WC,"
										+ " OB_YESTER_YR_WC=OB_FOR_MTH_YESTER_YR_WC,"
										+ " OB_MAINT_CHARGES=OB_FOR_MTH_MAINT_CHARGES,"
										+ " OB_INT_AMT_WC=OB_FOR_MTH_INT_AMT_WC,"
										+ " OB_INT_PRV_YR_MAINT=OB_FOR_MTH_INT_CY_MAINT    , COLN_UPTO_PRV_MTH_MAINT=0,COLN_UPTO_PRV_MTH_YESTER_YR=0,COLN_UPTO_PRV_MTH_WC=0,COLN_INT_UPTO_PRV_MTH_MAINT=0,COLN_INT_UPTO_PRV_MTH_WC=0 ,DMD_UPTO_PRV_MTH_WC=0,DMD_INT_UPTO_PRV_MTH_WC=0  WHERE BENEFICIARY_SNO= "
										+ ben + " AND fin_year= " + nextyear + " AND MONTH=" + next_month
										+ " and OFFICE_ID=" + Office_id;
								up1 = obj.rowUpdate(_zero_set_qry);
							}
						}

						ADD_CHARGES_WC = "0";
						MINUS_CHARGES_WC = "0";
						sch_OB_INT_AMT_WC = 0;
						OB_INT_AMT_WC = "0";
						DMD_INT_FOR_MTH_WC = 0;
					} // Phase 2 END
					rs_wc.close();

					up_qry = "update PMS_DCB_TRN_BILL_DMD set COLN_CUR_YR_WC=" + net_COLN_CUR_YR_WC + ",COLN_MAINT="
							+ net_COLN_MAINT + ",COLN_YESTER_YR_WC=" + net_COLN_YESTER_YR_WC + ",CB_MAINT_CHARGES="
							+ net_CB_MAINT + ",CB_CUR_YR_WC=" + net_CB_CUR_YR_WC + ",CB_YESTER_YR_WC="
							+ net_CB_YESTER_YR_WC + ",CB_INT_AMT_WC=" + CB_INT_AMT_WC + "   where BILL_SNO=" + maxsno;

					stmt3.executeUpdate(up_qry);

					String Demand_month_CB = obj.getValue("PMS_DCB_TRN_BILL_DMD", "sum(CB_CUR_YR_WC+CB_YESTER_YR_WC)",
							"where BILL_SNO=" + maxsno);
					String Demand_month_Demand = obj.getValue("PMS_DCB_TRN_BILL_DMD", "MONTH_BILL_AMT",
							"where BILL_SNO=" + maxsno);
					double Prv_dmd_for_month = Double
							.parseDouble(obj.getValue("PMS_DCB_TRN_BILL_DMD", "sum(MONTH_BILL_AMT)", "where BILL_MONTH="
									+ month_set + " and BILL_YEAR=" + year_set + " and BENEFICIARY_SNO=" + ben));

					String int_main_wcCollection_cond = " AND cashbook_month =" + month_set + " AND cashbook_year="
							+ year_set + " AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ " AND accounting_for_office_id=" + Office_id + " and sub_ledger_type_code = 10"
							+ " and SUB_LEDGER_CODE in" + "    (   "
							+ "  		select PROJECT_ID from PMS_MST_PROJECTS_VIEW where SCH_SNO in  (  "
							+ "       select SCH_SNO from PMS_DCB_MST_BENEFICIARY_METRE " + "       where "
							+ meter_status + " BENEFICIARY_SNO=" + ben + " and OFFICE_ID=" + Office_id + " )"
							+ "    )  " + " AND receipt_no IN "
							+ "  ( SELECT receipt_no FROM fas_receipt_master WHERE RECEIPT_STATUS='L' and sub_ledger_type_code = 14"
							+ " AND cashbook_month=" + month_set + " AND cashbook_year=" + year_set
							+ " AND accounting_unit_id=" + ACCOUNTING_UNIT_ID + " AND accounting_for_office_id="
							+ Office_id + " AND sub_ledger_code =" + ben + "   ) "
							+ " And ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE  from PMS_DCB_RECEIPT_ACCOUNT_MAP"
							+ "    where sch_type_id in  (select SCH_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where "
							+ meter_status + " BENEFICIARY_SNO=" + ben + " and   OFFICE_ID=" + Office_id + ")"
							+ " And ACTIVE_STATUS='L' ";

					try {
						stmt5 = con.createStatement();
						rs_coll = stmt5.executeQuery("select sum(amount) from  fas_receipt_transaction where     "
								+ int_main_wcCollection_cond2 + "" + int_cond4);
						if (rs_coll.next()) {
						} else {
						}
						rs_coll.close();

						rs_coll = stmt5.executeQuery(
								"select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
										+ " " + int_main_wcCollection_cond + "" + int_cond4);
						if (rs_coll.next()) {
							int_collection = obj2.isNull(rs_coll.getString(1), 1);
						} else {
							int_collection = "0";
						}
						rs_coll.close();

						stmt5.close();
					} catch (Exception e) {
						int_collection = "0";
						System.out.println(" PMS--->DCB--->Bill_Demand ( 1888 )--> " + e);
					}

					double int_calc_amt = ((Double.parseDouble(Demand_month_CB)
							- Double.parseDouble(Demand_month_Demand)) + Prv_dmd_for_month)
							- Double.parseDouble(int_collection);
					String ben_type_ben = obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID",
							"where " + new_cond + "  BENEFICIARY_SNO=" + ben);
					double int_rate = Double.parseDouble(obj.getValue("PMS_DCB_MST_INT", "INT_RATE",
							" where BENEFICIARY_TYPE=" + ben_type_ben + " and ACTIVE_STATUS='A'"));
					double int_calc = (int_calc_amt * int_rate) / 100;

					String total_ = " INSERT " + " INTO PMS_DCB_OB_YEARLY_TOTAL " + " ( " + " BENEFICIARY_SNO, "
							+ " FIN_YEAR, " + " MONTH, " + " OFFICE_ID, " + " OB_MAINT_CHARGES, " + " OB_CUR_YR_WC, "
							+ " OB_YESTER_YR_WC, " + " OB_INT_PRV_YR_MAINT, " + " OB_INT_CUR_YR_MAINT, "
							+ " OB_INT_AMT_WC, " + " DMD_UPTO_PRV_MTH_WC, " + " DMD_INT_UPTO_PRV_MTH_WC, "
							+ " COLN_UPTO_PRV_MTH_MAINT, " + " COLN_UPTO_PRV_MTH_YESTER_YR, "
							+ " COLN_UPTO_PRV_MTH_WC, " + " COLN_INT_UPTO_PRV_MTH_MAINT, "
							+ " COLN_INT_UPTO_PRV_MTH_WC, " + " OB_FOR_MTH_YESTER_YR_WC, "
							+ " OB_FOR_MTH_INT_CY_MAINT, " + " OB_FOR_MTH_INT_AMT_WC, " + " OB_FOR_MTH_MAINT_CHARGES, "
							+ " OB_FOR_MTH_CUR_YR_WC, " + " DMD_INT_FOR_MTH_WC " + " 		  ) "
							+ " SELECT BENEFICIARY_SNO, " + " FIN_YEAR, " + " MONTH, " + " OFFICE_ID, "
							+ " SUM(OB_MAINT_CHARGES), " + " SUM(OB_CUR_YR_WC), " + " SUM(OB_YESTER_YR_WC), "
							+ " SUM(OB_INT_PRV_YR_MAINT), " + " SUM(OB_INT_CUR_YR_MAINT), " + " SUM(OB_INT_AMT_WC), "
							+ " SUM(DMD_UPTO_PRV_MTH_WC), " + " SUM(DMD_INT_UPTO_PRV_MTH_WC), "
							+ " SUM(COLN_UPTO_PRV_MTH_MAINT), " + " SUM(COLN_UPTO_PRV_MTH_YESTER_YR), "
							+ " SUM(COLN_UPTO_PRV_MTH_WC), " + " SUM(COLN_INT_UPTO_PRV_MTH_MAINT), "
							+ " SUM(COLN_INT_UPTO_PRV_MTH_WC), " + " SUM(OB_FOR_MTH_YESTER_YR_WC), "
							+ " SUM(OB_FOR_MTH_INT_CY_MAINT), " + " SUM(OB_FOR_MTH_INT_AMT_WC), "
							+ " SUM(OB_FOR_MTH_MAINT_CHARGES), " + " SUM(OB_FOR_MTH_CUR_YR_WC), "
							+ " SUM(DMD_INT_FOR_MTH_WC) " + " FROM PMS_DCB_OB_YEARLY " + " WHERE BENEFICIARY_SNO= "
							+ ben + " AND fin_year         = " + nextyear + " AND MONTH            = " + next_month
							+ " and OFFICE_ID=" + Office_id + "  GROUP BY BENEFICIARY_SNO, " + " FIN_YEAR, "
							+ " MONTH, " + " OFFICE_ID ";

					int check_count = obj.getCount("PMS_DCB_OB_YEARLY_TOTAL", " where BENEFICIARY_SNO=" + ben
							+ " and MONTH=" + next_month + " and OFFICE_ID=" + Office_id);

					if (check_count > 0) {
						PreparedStatement del_ = null;
						try {
							del_ = con.prepareStatement("delete from PMS_DCB_OB_YEARLY_TOTAL where BENEFICIARY_SNO="
									+ ben + " and fin_year=" + nextyear + " and MONTH=" + next_month + " and OFFICE_ID="
									+ Office_id);
							del_.executeUpdate();
							del_.close();
						} catch (SQLException e) {
						}

					}

					stmt3.executeUpdate(total_);

					String wc_DR_total_cond = " where BENEFICIARY_SNO =" + ben + " and CASHBOOK_MONTH=" + billmonth
							+ " and CASHBOOK_YEAR=" + billyear
							+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ " where COLLECTION_TYPE=7 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='DR'   ";

					String wc_CR_total_cond = " where BENEFICIARY_SNO =" + ben + " and CASHBOOK_MONTH=" + billmonth
							+ " and CASHBOOK_YEAR=" + billyear
							+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP"
							+ " where COLLECTION_TYPE=7 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='CR'  ";

					String wc_DR_total_ = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(amount)",
							wc_DR_total_cond + " group by BENEFICIARY_SNO");
					String wc_CR_total_ = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(amount)",
							wc_CR_total_cond + " group by BENEFICIARY_SNO");

					String main_DR_total_cond = " where BENEFICIARY_SNO=" + ben + " and CASHBOOK_MONTH=" + billmonth
							+ " and CASHBOOK_YEAR=" + billyear
							+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ " where COLLECTION_TYPE=8 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='DR'   ";

					String main_CR_total_cond = " where BENEFICIARY_SNO=" + ben + " and CASHBOOK_MONTH=" + billmonth
							+ " and CASHBOOK_YEAR=" + billyear
							+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP  "
							+ " where COLLECTION_TYPE=8 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='CR'  ";

					String main_DR_total_ = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(amount)",
							main_DR_total_cond + " group by BENEFICIARY_SNO");
					String main_CR_total_ = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(amount)",
							main_CR_total_cond + " group by BENEFICIARY_SNO");

					String qry_up = "update PMS_DCB_OB_YEARLY_TOTAL set OB_FOR_MTH_CUR_YR_WC=(OB_FOR_MTH_CUR_YR_WC+"
							+ wc_DR_total_ + ")-(" + wc_CR_total_
							+ "),OB_FOR_MTH_MAINT_CHARGES=(OB_FOR_MTH_MAINT_CHARGES+" + main_DR_total_ + ")-("
							+ main_CR_total_ + ")   WHERE BENEFICIARY_SNO= " + ben + " AND fin_year         = "
							+ nextyear + " AND MONTH            = " + next_month + " and OFFICE_ID=" + Office_id;
					int up = obj.rowUpdate(qry_up);
					// System.out.println("Bill End for->> "+ ben);

				} // Phase 1 End
				System.out.println(" *** demand_insert End ***");
				xml = "<result><row>" + row;
				xml += "</row><total_demand>" + total_demand + "</total_demand><already_demand>" + already_demand
						+ "</already_demand></result>";
				con.close();
				con1.close();
				con2.close();
				con3.close();
				con4.close();
				con5.close();
			}

		}
		return xml;

	}

	synchronized public String demand_ins(String BILL_PERIOD_FROM, String BILL_PERIOD_TO, String billyear,
			String billmonth, String date, String ben_type_val, String Office_id, String userid) throws Exception {
		Controller obj = new Controller();
		Connection con = obj.con();
		Connection con1 = obj.con();
		Connection con2 = obj.con();
		Connection con3 = obj.con();
		Connection con4 = obj.con();
		Connection con5 = obj.con();
		Controller obj2 = new Controller();
		System.out.println(" *** DEMAND INSERT FOR NORMAL SCHEME ***");
		System.out.println("PMS--->DCB--->demand_insert");
		int total_demand = 0, already_demand = 0, row = 0;
		String OB_INT_CUR_YR_MAINT = "0", OB_INT_AMT_WC = "", ben_int = "";
		obj2.createStatement(con);
		obj.createStatement(con);
		stmt = con.createStatement();
		stmt3 = con1.createStatement();
		stmt4 = con2.createStatement();
		stmt5 = con3.createStatement();
		stmt2 = con5.createStatement();
		int month_set = 0, // demand Gen month-1 setting
				year_set = 0, // Year Setting
				month_set2 = 0, // demand Gen Month-2 Setting
				year_set2 = 0;// Year Setting

		if ((Integer.parseInt(billmonth)) == 1) {
			month_set = 12;
			year_set = Integer.parseInt(billyear) - 1;
		} else {
			month_set = (Integer.parseInt(billmonth) - 1);
			year_set = Integer.parseInt(billyear);
		}

		if ((Integer.parseInt(billmonth)) == 1) {
			month_set2 = 11;
			year_set2 = Integer.parseInt(billyear) - 1;
		} else if ((Integer.parseInt(billmonth)) == 2) {
			month_set2 = 12;
			year_set2 = Integer.parseInt(billyear) - 1;
		} else {
			month_set2 = (Integer.parseInt(billmonth) - 2);
			year_set2 = Integer.parseInt(billyear);
		}

		String ben_type_cond = "";
		if (!ben_type_val.equalsIgnoreCase("0"))
			ben_type_cond = " and BENEFICIARY_TYPE_ID=" + ben_type_val;

		obj.testQry(" *** Bill Generation Start  for the month of " + billmonth + "-" + billyear);
		int already_demand2 = obj.getCount("PMS_DCB_TRN_BILL_DMD",
				"where OFFICE_ID=" + Office_id + " and BILL_MONTH=" + billmonth + " and BILL_YEAR=" + billyear + "");
		already_demand = obj.getCount("PMS_DCB_TRN_BILL_DMD", "where OFFICE_ID=" + Office_id + " and BILL_MONTH="
				+ billmonth + " and BILL_YEAR=" + billyear
				+ " and BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_TYPE_ID="
				+ ben_type_val + " and OFFICE_ID=" + Office_id + ")");
		String sel_qry = "SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME, ACCOUNTING_UNIT_OFFICE_ID	FROM FAS_MST_ACCT_UNITS	WHERE ACCOUNTING_UNIT_OFFICE_ID ="
				+ Office_id;
		rs_sch_type = stmt3.executeQuery(sel_qry);
		if (rs_sch_type.next()) {
			ACCOUNTING_UNIT_ID = obj.isNull(rs_sch_type.getString(1), 1);
		}
		String met_query = "select beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where " + meter_status
				+ "   OFFICE_ID=" + Office_id;
		int tot_ben = obj.getCount("pms_dcb_mst_beneficiary",
				"where status ='L' and OFFICE_ID=" + Office_id + " and beneficiary_sno in (" + met_query + ")");
		if (already_demand == tot_ben) {
			xml = "<result><row>" + 0;
			xml += "</row><total_demand>" + 0 + "</total_demand><already_demand>" + already_demand2
					+ "</already_demand></result>";
		} else {

			int type_tot_ben = obj.getCount("pms_dcb_mst_beneficiary", "where status ='L' and OFFICE_ID=" + Office_id
					+ " and beneficiary_sno in (" + met_query + ")  and  BENEFICIARY_TYPE_ID=" + ben_type_val + "");
			int type_tot_bill = already_demand;

			if (type_tot_ben == type_tot_bill && !ben_type_val.equalsIgnoreCase("0")) {
				xml = "<result><row>" + 0;
				xml += "</row><total_demand>" + 0 + "</total_demand><already_demand>" + type_tot_bill
						+ "</already_demand><msg2>Total Beneficiary of This Beneficiary Type : " + type_tot_ben
						+ " \n Total Bill is " + type_tot_bill + "</msg2></result>";
			} else {
				// Collect Following Values from Previous Month table
				/*
				 * In this query contain Closing Balance of Main.charge,Current Year Closing
				 * Balance of Prv Month Water Charge, Yeaster Year,interest of Water
				 * Charge,Interest of maintain
				 */
				String qry_ins = " SELECT ben.BENEFICIARY_NAME,ben.beneficiary_sno,pr.net_consumed, "
						+ " monthly.CB_MAINT_CHARGES,monthly.CB_CUR_YR_WC, "
						+ " monthly.CB_YESTER_YR_WC,monthly.CB_INT_CUR_YR_MAINT, " + " monthly.CB_INT_AMT_WC, "
						+ " monthly.CB_YESTER_YR_WC+monthly.CB_CUR_YR_WC , "
						+ " ( monthly.DMD_INT_UPTO_MTH_WC)*(int.INT_RATE/100) as INT_CALC_WC, "
						+ "  int.INT_RATE as INT_PERCENT,wc.wc_amt as WC_MTH_TOTAL, "
						+ "  nvl(wc.wc_amt,0)  as MONTH_BILL_AMT , nvl(monthly.DMD_INT_FOR_MTH_WC,0)   as DMD_INT_FOR_MTH_WC "
						+ " FROM( " + " ( " + " SELECT beneficiary_sno, " + " BENEFICIARY_TYPE_ID,BENEFICIARY_NAME  "
						+ "  FROM pms_dcb_mst_beneficiary " + " WHERE     " + new_cond + "   office_id = " + Office_id
						+ " and beneficiary_sno  not in (select beneficiary_sno from PMS_DCB_TRN_BILL_DMD where office_id="
						+ Office_id + " and BILL_MONTH =" + billmonth + " and BILL_YEAR=" + billyear + ")" + "   "
						+ ben_type_cond + "    )  ben JOIN " + "  (SELECT sum(QTY_CONSUMED_NET) as net_consumed, "
						+ "  beneficiary_sno " + "  FROM PMS_DCB_TRN_MONTHLY_PR " + "  WHERE MONTH =  " + billmonth
						+ "  AND YEAR = " + billyear + " group by beneficiary_sno ) "
						+ "  pr ON pr.beneficiary_sno = ben.beneficiary_sno " + "  left " + "  join  " + "   ( "
						+ "   select " + "    sum(CB_MAINT_CHARGES) as CB_MAINT_CHARGES, "
						+ "    sum(CB_CUR_YR_WC) as CB_CUR_YR_WC, " + "    sum(CB_YESTER_YR_WC) as CB_YESTER_YR_WC, "
						+ "    sum(CB_INT_AMT_WC) as CB_INT_AMT_WC, "
						+ "    sum(CB_INT_CUR_YR_MAINT) as CB_INT_CUR_YR_MAINT, " + "     BENEFICIARY_SNO   ,"
						+ "    sum(DMD_INT_FOR_MTH_WC) as DMD_INT_FOR_MTH_WC, "
						+ "    sum(DMD_INT_UPTO_MTH_WC) as DMD_INT_UPTO_MTH_WC " + "   from   "
						+ "   PMS_DCB_TRN_CB_MONTHLY " + "   where  MONTH =  " + month_set + "   AND FIN_YEAR = "
						+ year_set + "  group by " + "      BENEFICIARY_SNO " + "  )monthly "
						+ "   on monthly.BENEFICIARY_SNO=ben.BENEFICIARY_SNO " + "  left join " + "   ( " + "  select  "
						+ "     sum(TOTAL_AMT) as wc_amt,BENEFICIARY_SNO " // Month
						// Bill value got from wc_billing table
						+ "  from " + "      PMS_DCB_WC_BILLING " + "    where MONTH = " + billmonth + "and OFFICE_ID="
						+ Office_id + " AND YEAR =" + billyear + "    group by BENEFICIARY_SNO "

						+ "  )wc  " + "  on wc.BENEFICIARY_SNO=ben.BENEFICIARY_SNO " + "  join " + "   ( "
						+ "     select  " + "         INT_RATE ,BENEFICIARY_TYPE " + "    from  "
						+ "          PMS_DCB_MST_INT " + "    where  " + "          ACTIVE_STATUS='A' " + "  )int"
						+ "  on int.BENEFICIARY_TYPE=ben.BENEFICIARY_TYPE_ID " + "  ) order by beneficiary_sno";

				double net_COLN_CUR_YR_WC = 0, net_COLN_MAINT = 0, net_COLN_YESTER_YR_WC = 0;
				double net_CB_CUR_YR_WC = 0, net_CB_MAINT = 0, net_CB_YESTER_YR_WC = 0, DMD_INT_FOR_MTH_WC = 0.0;
				rs = stmt.executeQuery(qry_ins);
				String ben = "";
				int ben_dmd_count = 0;
				int BENEFICIARY_TYPE_ID_SUB = 0;
				while (rs.next()) {

					ben_dmd_count = 0;
					ben = rs.getString("beneficiary_sno");

					// int DIV_BILL_NO = obj.getMax("PMS_DCB_TRN_BILL_DMD","DIV_BILL_NO", "where
					// OFFICE_ID=" + Office_id+ " and BILL_MONTH=" + billmonth+ " and BILL_YEAR=" +
					// billyear + "");
					int DIV_BILL_NO = Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY", "BEN_DIV_SNO",
							"where " + new_cond + " BENEFICIARY_SNO=" + ben));
					ben_int = obj.getValue("PMS_DCB_MST_BENEFICIARY", "INT_RATE",
							"where " + new_cond + " BENEFICIARY_SNO=" + ben);
					BENEFICIARY_TYPE_ID_SUB = Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY",
							"BENEFICIARY_TYPE_ID_SUB", "where " + new_cond + " BENEFICIARY_SNO=" + ben));
					WC_MTH_TOTAL = obj.isNull(rs.getString("WC_MTH_TOTAL"), 1); // Water charges for the month
					OB_INT_AMT_WC = obj.isNull(rs.getString("CB_INT_AMT_WC"), 1);
					//////////// INTEREST BLOCK ///////////////////////
					/*
					 * ### Intrest Calculation for bentype < 6 Pmonth OB OB_YESTER_YR_WC+
					 * OB_CUR_YR_WC A Coll COLN_CUR_YR_WC+COLN_YESTER_YR_WC B Demand MONTH_BILL_AMT
					 * C Cmonth Coll COLN_CUR_YR_WC+COLN_YESTER_YR_WC D Pmonth Int OB
					 * OB_FOR_MTH_INT_AMT_WC
					 * 
					 * Cmonth Calculation(((A-B)+C)-D)
					 */
					String Prv_for_int_OB_YESTER_YR_WC = "0", Prv_for_int_OB_CUR_YR_WC = "0", int_collection = "",
							int_collection2 = "";
					String Cond_int = " where BENEFICIARY_SNO=" + ben + " and FIN_YEAR=" + year_set + " and MONTH="
							+ month_set + " and OFFICE_ID=" + Office_id;
					// Get yester year charge from PMS_DCB_TRN_CB_MONTHLY
					Prv_for_int_OB_YESTER_YR_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY", "sum(CB_YESTER_YR_WC)",
							Cond_int);
					// Current Year of Water Charge of previouse Month from PMS_DCB_TRN_CB_MONTHLY
					Prv_for_int_OB_CUR_YR_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY", "sum(CB_CUR_YR_WC)", Cond_int);

					String int_cond4 = " and collection_type=7 )"; // Water Charges
					String int_cond9 = " and collection_type=9 )"; // Water Charges
					String int_main_wcCollection_cond2 = " " + " AND cashbook_month=" + month_set
							+ " AND cashbook_year=" + year_set + " AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ " AND accounting_for_office_id=" + Office_id + " And sub_ledger_type_code = 10"
							+ " And SUB_LEDGER_CODE in"
							+ " ( select PROJECT_ID from PMS_MST_PROJECTS_VIEW where SCH_SNO in "
							+ "   ( select SCH_SNO from PMS_DCB_MST_BENEFICIARY_METRE where " + meter_status
							+ "    BENEFICIARY_SNO=" + ben + " and   OFFICE_ID=" + Office_id + " "
							+ "    )  and   OFFICE_ID=" + Office_id + " " + " )  AND receipt_no IN "
							+ "    ( SELECT receipt_no FROM fas_receipt_master  WHERE "
							+ "        RECEIPT_STATUS='L' and sub_ledger_type_code = 14 "
							+ "        AND cashbook_month=" + month_set + "        AND cashbook_year=" + year_set
							+ "        AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ "   	 AND accounting_for_office_id=" + Office_id + "        AND sub_ledger_code=" + ben
							+ "    )  and ACCOUNT_HEAD_CODE in  "
							+ "	( select ACCOUNT_HEAD_CODE   from PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ "      where sch_type_id in ( select SCH_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where "
							+ meter_status + "		BENEFICIARY_SNO=" + ben + " and   OFFICE_ID=" + Office_id + " "
							+ "	)  and ACTIVE_STATUS='L' ";
					String CurrentmonthCollection_cond2 = " (1=1) " + " AND cashbook_month=" + billmonth
							+ " AND cashbook_year=" + billyear + " AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ " AND accounting_for_office_id=" + Office_id + " And sub_ledger_type_code = 10"
							+ " And SUB_LEDGER_CODE in"
							+ " ( select PROJECT_ID from PMS_MST_PROJECTS_VIEW where SCH_SNO in "
							+ "   ( select SCH_SNO from PMS_DCB_MST_BENEFICIARY_METRE where " + meter_status
							+ "    BENEFICIARY_SNO=" + ben + " and   OFFICE_ID=" + Office_id + " "
							+ "    ) and  OFFICE_ID=" + Office_id + " " + " )  AND receipt_no IN "
							+ "    ( SELECT receipt_no FROM fas_receipt_master  WHERE "
							+ "        RECEIPT_STATUS='L' and sub_ledger_type_code = 14 "
							+ "        AND cashbook_month=" + billmonth + "        AND cashbook_year=" + billyear
							+ "        AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ "   	 AND accounting_for_office_id=" + Office_id + "        AND sub_ledger_code=" + ben
							+ "    )  and ACCOUNT_HEAD_CODE in  "
							+ "	( select ACCOUNT_HEAD_CODE   from PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ "      where sch_type_id in ( select SCH_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where "
							+ meter_status + "		BENEFICIARY_SNO=" + ben + " and   OFFICE_ID=" + Office_id + " "
							+ "	)  and ACTIVE_STATUS='L' ";
					String prv_month_collection = "0", // Previous Month Collection
							curmonthcollection = "0"; // Current Month Collection
					try {
						stmt5 = con.createStatement();
						rs_coll = stmt5.executeQuery(
								"select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
										+ " " + int_main_wcCollection_cond2 + "" + int_cond4);
						if (rs_coll.next()) {
							prv_month_collection = obj2.isNull(rs_coll.getString(1), 1);
						} else {
							prv_month_collection = "0";
						}
						rs_coll.close();
					} catch (Exception e) {

						System.out.println(e);
					}
					stmt5.close();
					try {
						stmt5 = con.createStatement();
						rs_coll = stmt5.executeQuery(
								"select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
										+ " " + CurrentmonthCollection_cond2 + "" + int_cond4);
						if (rs_coll.next()) {
							curmonthcollection = obj2.isNull(rs_coll.getString(1), 1);
						} else {
							curmonthcollection = "0";
						}
						rs_coll.close();
					} catch (Exception e) {
						System.out.println(" PMS--->DCB--->Bill_Demand ( 1095 )--> " + e);
					}

					/* B */ double prv_collection = Double.parseDouble(prv_month_collection);
					/* A */ double net_ob_prv_month = Double.parseDouble(Prv_for_int_OB_YESTER_YR_WC)
							+ Double.parseDouble(Prv_for_int_OB_CUR_YR_WC);
					/* D */ double dmd_for_month = Double
							.parseDouble(
									obj.isNull(
											obj.getValue("PMS_DCB_WC_BILLING", "sum(TOTAL_AMT)",
													"where MONTH=" + billmonth + " and OFFICE_ID=" + Office_id
															+ " and YEAR=" + billyear + " and BENEFICIARY_SNO=" + ben),
											1));
					/* C */ double Prvdmd_for_month = Double
							.parseDouble(obj.isNull(
									obj.getValue("PMS_DCB_TRN_BILL_DMD", "sum(MONTH_BILL_AMT)", "where BILL_MONTH="
											+ month_set + " and BILL_YEAR=" + year_set + " and BENEFICIARY_SNO=" + ben),
									1));
					// Current Month Interest Collection From FAS
					double int_collection_ben = Double.parseDouble(obj.isNull(obj.getValue("fas_receipt_transaction",
							"sum(amount)",
							"where   sub_ledger_type_code = 10 " + " " + CurrentmonthCollection_cond2 + "" + int_cond9),
							1));
					// Date : 27/07/2011
					/*
					 * net_ob_prv_month =?> Prv_for_int_OB_YESTER_YR_WC of PMS_DCB_TRN_CB_MONTHLY
					 * //(((A-B)+C)-D) // Villuppuram logic //double
					 * amt=(((net_ob_prv_month-prv_collection)+Prvdmd_for_month)-Double.parseDouble(
					 * curmonthcollection)); // JAO logic // double
					 * amt=((net_ob_prv_month)-Prvdmd_for_month); // TVR Logic //double
					 * amt=((net_ob_prv_month)-(prv_collection))-(Double.parseDouble(
					 * curmonthcollection)) ; System.out.println(ben+"##"+
					 * BEN_NAME+"##"+net_ob_prv_month+"##"+Prvdmd_for_month+"##"+
					 * month_minus_2_dmd_for_month+"##"+dmd_for_month+"##"+amt+"##"+int_calc2);
					 * //////////// INTEREST BLOCK END /////////////////////// Old Code
					 * DMD_INT_FOR_MTH_WC =
					 * Double.parseDouble(obj.isNull(rs.getString("DMD_INT_FOR_MTH_WC"),1)); // if
					 * interest Calculation is negative we
					 */
					double month_minus_2_dmd_for_month = Double.parseDouble(
							obj.isNull(obj.getValue("PMS_DCB_TRN_BILL_DMD", "sum(MONTH_BILL_AMT)", "where BILL_MONTH="
									+ month_set2 + " and BILL_YEAR=" + year_set2 + " and BENEFICIARY_SNO=" + ben), 1));

					// System.out.println("net_ob_prv_month" + net_ob_prv_month);
					// System.out.println("Prvdmd_for_month" + Prvdmd_for_month);
					// System.out.println("month_minus_2_dmd_for_month" +
					// month_minus_2_dmd_for_month);

					// double
					// amt=(((net_ob_prv_month)-Double.parseDouble(curmonthcollection)-Prvdmd_for_month)-month_minus_2_dmd_for_month);

					// DIV Logic (TUT,VPM,KVP)

					// 27 12 13 Changes by Tutkukkudi
					// OLD ----> double amt=( ( (
					// net_ob_prv_month-Double.parseDouble(curmonthcollection))-Prvdmd_for_month));
					double amt = 0.0;
					if (BENEFICIARY_TYPE_ID_SUB <= 6)
						amt = (((net_ob_prv_month) - (Prvdmd_for_month + month_minus_2_dmd_for_month)));
					else
						amt = (((net_ob_prv_month) - (Prvdmd_for_month)));
					/*
					 * Final :
					 * 
					 * net_ob_prv_month -> Prv Month Closing (current month Opening)
					 * Prvdmd_for_month -> prv month demand month_minus_2_dmd_for_month -> prv prv
					 * month demand
					 */
					double int_calc2 = (amt * Double.parseDouble(ben_int) / 100);
					DMD_INT_FOR_MTH_WC = 0.0;
					if (int_calc2 < 0)
						int_calc2 = 0;
					/* New Code */
					/* Date : 14/09/2011 Replace Actual int amount with interest */
					// DMD_INT_FOR_MTH_WC =amt;
					DMD_INT_FOR_MTH_WC = Math.round(int_calc2);

					if (DMD_INT_FOR_MTH_WC < 0)
						DMD_INT_FOR_MTH_WC = 0;

					ben_dmd_count = obj.getCount("PMS_DCB_TRN_BILL_DMD", "where BENEFICIARY_SNO=" + ben
							+ " and BILL_YEAR=" + billyear + " and   BILL_MONTH=" + billmonth);
					String ben_type = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID",
							"where " + new_cond + "  BENEFICIARY_SNO=" + ben), 1);
					String OTHERS_PRIVATE_SNO = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY", "OTHERS_PRIVATE_SNO",
							"where  " + new_cond + " BENEFICIARY_SNO=" + ben), 1);
					String short_name = "";
					if (Integer.parseInt(ben_type) <= 6)
						short_name = obj.isNull(
								obj.getValue("PMS_DCB_BEN_TYPE", "ADDRESS_TO", "where BEN_TYPE_ID=" + ben_type), 3);
					else
						short_name = obj.isNull(obj.getValue("COM_MST_PRIVATE", "ADDRESS_TO",
								"where PRIVATE_SNO=" + OTHERS_PRIVATE_SNO), 3);
					/* Date : 21/02/2011 : Other Charges Proces */
					// 03 - 09 - 2012
					String Other_charge_cond1 = " where  CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR=" + billyear
							+ " and BENEFICIARY_SNO =" + ben + " and OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='DR' ";
					Other_charge_cond1 += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7)  group by BENEFICIARY_SNO";

					String Other_charge_cond2 = " where CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR=" + billyear
							+ " and BENEFICIARY_SNO =" + ben + " and OFFICE_ID =" + Office_id
							+ " and CR_DR_INDICATOR='CR' ";
					Other_charge_cond2 += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7)  group by BENEFICIARY_SNO";

					String Other_charge_cond1_MC = " where  CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and BENEFICIARY_SNO =" + ben + " and  CONFIRM_FLAG='Y'  and OFFICE_ID ="
							+ Office_id + " and CR_DR_INDICATOR='DR' ";
					Other_charge_cond1_MC += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8)";

					String Other_charge_cond2_MC = " where CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and BENEFICIARY_SNO =" + ben + "  and  CONFIRM_FLAG='Y'  and OFFICE_ID ="
							+ Office_id + " and CR_DR_INDICATOR='CR' ";
					Other_charge_cond2_MC += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8)";

					String Other_charge_cond1_up = " where  CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and BENEFICIARY_SNO =" + ben + " and  CONFIRM_FLAG='Y'  and OFFICE_ID ="
							+ Office_id + " and CR_DR_INDICATOR='DR' ";
					Other_charge_cond1_up += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7)";

					String Other_charge_cond2_up = " where CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and BENEFICIARY_SNO =" + ben + "  and  CONFIRM_FLAG='Y'  and OFFICE_ID ="
							+ Office_id + " and CR_DR_INDICATOR='CR' ";
					Other_charge_cond2_up += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7)";

					String Other_charge_cond1_up_MC = " where  CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and BENEFICIARY_SNO =" + ben + " and  CONFIRM_FLAG='Y'  and OFFICE_ID ="
							+ Office_id + " and CR_DR_INDICATOR='DR' ";
					Other_charge_cond1_up_MC += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8)";

					String Other_charge_cond2_up_MC = " where CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
							+ billyear + " and BENEFICIARY_SNO =" + ben + "  and  CONFIRM_FLAG='Y'  and OFFICE_ID ="
							+ Office_id + " and CR_DR_INDICATOR='CR' ";
					Other_charge_cond2_up_MC += " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8)";

					// 03 - 09 - 2012

					// other charges add
					String ADD_CHARGES_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)", Other_charge_cond1); // get
																														// DR
																														// amount
					String MINUS_CHARGES_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)", Other_charge_cond2); // get
																														// CR
																														// amount

					// 03 - 09 - 2012
					String ADD_CHARGES_MC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)", Other_charge_cond1_MC); // get
																															// DR
																															// amount
					String MINUS_CHARGES_MC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",
							Other_charge_cond2_MC); // get CR amount
					// 03 - 09 - 2012
					int maxsno, tran_row = 0;
					int prv_month = 0, prv_year = 0, ben_sno = 0, sch_sno = 0, next_month = 0, nextyear = 0;
					/* Date : 21/02/2011 : Other Charges End */

					maxsno = obj.getMax("PMS_DCB_TRN_BILL_DMD", "BILL_SNO", "");
					String ins_qry = "insert into PMS_DCB_TRN_BILL_DMD (BILL_SNO,OFFICE_ID,BENEFICIARY_SNO,BILL_PERIOD_FROM,BILL_PERIOD_TO,BILL_MONTH,"
							+ "BILL_YEAR,DIV_BILL_NO,NET_CONSUMPTION,OB_MAINT_CHARGES,OB_CUR_YR_WC,OB_YESTER_YR_WC,WC_MTH_TOTAL,"
							+ "INT_PERCENT,INT_CALC_WC,MONTH_BILL_AMT,BILL_RAISED_BY,BILLING_DT,UPDATED_BY_USER_ID,UPDATED_DATE,"
							+ "DMD_INT_FOR_MTH_WC ,OB_INT_AMT_WC,ADDRESS_TO,ADD_CHARGES_WC, MINUS_CHARGES_WC ,COLN_INT_WC,ADD_CHARGES_MAINT,MINUS_CHARGES_MAINT) "
							+ " values " + " (";
// Demand Already Generate Test here  
					if (ben_dmd_count == 0) {
						total_demand++;
						try {
							ins_qry = ins_qry + maxsno + "" + "," + Office_id + "" + "," + ben + ",to_date('"
									+ BILL_PERIOD_FROM + "','DD/MM/YYYY')," + " to_date('" + BILL_PERIOD_TO
									+ "','DD/MM/YYYY')," + "" + billmonth + "," + billyear + "," + DIV_BILL_NO + ","
									+ obj.isNull(rs.getString("net_consumed"), 1) + ","
									+ obj.isNull(rs.getString("CB_MAINT_CHARGES"), 1) + ","
									+ obj.isNull(rs.getString("CB_CUR_YR_WC"), 1) + ","
									+ obj.isNull(rs.getString("CB_YESTER_YR_WC"), 1) + "," + WC_MTH_TOTAL + ","
									+ ben_int + "," + Math.round(int_calc2) + ","
									+ Math.round(Double.parseDouble(obj.isNull(rs.getString("MONTH_BILL_AMT"), 1)))
									+ "," + Office_id + ",to_date('" + date + "','DD/MM/YYYY')" + ",'" + userid + "',"
									+ "clock_timestamp()," + DMD_INT_FOR_MTH_WC + ", " + OB_INT_AMT_WC + "" + " ,'"
									+ short_name + "'," + ADD_CHARGES_WC + "," + MINUS_CHARGES_WC + ","
									+ int_collection_ben + "," + ADD_CHARGES_MC + "," + MINUS_CHARGES_MC + ")";

						} catch (SQLException e) {
							maxsno = obj.getMax("PMS_DCB_TRN_BILL_DMD", "BILL_SNO", "");
							ins_qry = ins_qry + maxsno + "" + "," + Office_id + "" + "," + ben + ",to_date('"
									+ BILL_PERIOD_FROM + "','DD/MM/YYYY')," + " to_date('" + BILL_PERIOD_TO
									+ "','DD/MM/YYYY')," + "" + billmonth + "," + billyear + "," + DIV_BILL_NO + ","
									+ obj.isNull(rs.getString("net_consumed"), 1) + ","
									+ obj.isNull(rs.getString("CB_MAINT_CHARGES"), 1) + ","
									+ obj.isNull(rs.getString("CB_CUR_YR_WC"), 1) + ","
									+ obj.isNull(rs.getString("CB_YESTER_YR_WC"), 1) + "," + WC_MTH_TOTAL + ","
									+ ben_int + "," + Math.round(int_calc2) + ","
									+ Math.round(Double.parseDouble(obj.isNull(rs.getString("MONTH_BILL_AMT"), 1)))
									+ "," + Office_id + ",to_date('" + date + "','DD/MM/YYYY')" + ",'" + userid + "',"
									+ "clock_timestamp()," + DMD_INT_FOR_MTH_WC + ", " + OB_INT_AMT_WC + "" + " ,'"
									+ short_name + "'," + ADD_CHARGES_WC + "," + MINUS_CHARGES_WC + ","
									+ int_collection_ben + "," + ADD_CHARGES_MC + "," + MINUS_CHARGES_MC + ")";

						} catch (Exception es) {

						}

						row += obj.setUpd(ins_qry);
						String bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO=" + maxsno + " "
								+ Other_charge_cond1_up;
						int b_up_row = obj.rowUpdate(bill_sno_update);
						bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO=" + maxsno + " "
								+ Other_charge_cond2_up;
						b_up_row = obj.rowUpdate(bill_sno_update);

						// 03 - 09 - 2012
						bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO=" + maxsno + " "
								+ Other_charge_cond1_up_MC;
						b_up_row = obj.rowUpdate(bill_sno_update);
						bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO=" + maxsno + " "
								+ Other_charge_cond2_up_MC;
						b_up_row = obj.rowUpdate(bill_sno_update);
						// 03 - 09 - 2012

					} else {
						already_demand++;
					}

					double sch_OB_INT_AMT_WC = 0.0, str_CB_INT_AMT_WC = 0.0;
					String up_qry = "";
					String OB_MAINT_CHARGES = "0", OB_CUR_YR_WC = "0", OB_YESTER_YR_WC = "0";
					String COLN_CUR_YR_WC = "0", COLN_YESTER_YR_WC = "0", COLN_MAINT = "0", COLN_INT_FOR_MTH_WC = "0";
					String CB_MAINT_CHARGES = "0", CB_CUR_YR_WC = "0", CB_YESTER_YR_WC = "0";
					String CB_INT_CUR_YR_MAINT = "0", CB_INT_AMT_WC = "0";
					net_CB_CUR_YR_WC = 0;
					net_CB_MAINT = 0;
					net_CB_YESTER_YR_WC = 0;
					net_COLN_CUR_YR_WC = 0;
					net_COLN_MAINT = 0;
					net_COLN_YESTER_YR_WC = 0;

					qry_ins = "  select  wc.BENEFICIARY_SNO, wc.SCHEME_SNO,  wc.TOTAL_AMT, wc.QTY_CONSUMED,ob.CB_MAINT_CHARGES, "
							+ " ob.CB_CUR_YR_WC,ob.CB_YESTER_YR_WC,wc.SCHEME_SNO,ob.MONTH,ob.FIN_YEAR,"
							+ " ob.CB_INT_AMT_WC,ob.CB_INT_CUR_YR_MAINT from  "
							+ " (  (  select BENEFICIARY_SNO,SCHEME_SNO ,sum(TOTAL_AMT) as TOTAL_AMT, sum(QTY_CONSUMED) as QTY_CONSUMED "
							+ "       from PMS_DCB_WC_BILLING where MONTH=" + billmonth + " and OFFICE_ID=" + Office_id
							+ " and  " + "       YEAR =" + billyear + " and " + " BENEFICIARY_SNO=" + ben
							+ "   group by  BENEFICIARY_SNO ,MONTH  ,YEAR,SCHEME_SNO " + "    )wc  " + "  left join  "
							+ "  ( " + "  select  "
							+ "      CB_MAINT_CHARGES,CB_CUR_YR_WC,CB_YESTER_YR_WC,SCH_SNO,MONTH,FIN_YEAR,BENEFICIARY_SNO,CB_INT_AMT_WC,CB_INT_CUR_YR_MAINT "
							+ "  from  PMS_DCB_TRN_CB_MONTHLY  where BENEFICIARY_SNO=" + ben + " and MONTH=" + month_set
							+ " AND FIN_YEAR=" + year_set + "  )ob "
							+ " on ob.BENEFICIARY_SNO= wc.BENEFICIARY_SNO   and ob.SCH_SNO=wc.SCHEME_SNO " + "" + " ) ";

					rs_wc = stmt2.executeQuery(qry_ins);
					double sch_wise_int;
					String sc = "";
					while (rs_wc.next()) {
						up_qry = "";
						OB_MAINT_CHARGES = obj.isNull(rs_wc.getString("CB_MAINT_CHARGES"), 1);
						OB_YESTER_YR_WC = obj.isNull(rs_wc.getString("CB_YESTER_YR_WC"), 1);
						OB_CUR_YR_WC = obj.isNull(rs_wc.getString("CB_CUR_YR_WC"), 1);
						OB_INT_CUR_YR_MAINT = obj.isNull(rs_wc.getString("CB_INT_CUR_YR_MAINT"), 1);
						sch_OB_INT_AMT_WC = Double.parseDouble(obj.isNull(rs_wc.getString("CB_INT_AMT_WC"), 1));
						ins_qry = " insert into PMS_DCB_TRN_BILL_DMD_SCH" + "( BILL_SNO," + " OFFICE_ID,"
								+ " BENEFICIARY_SNO," + " SCH_NO," + " NET_CONSUMPTION," + " OB_MAINT_CHARGES,"
								+ " OB_CUR_YR_WC," + " OB_YESTER_YR_WC," + "WC_MTH_TOTAL ," + "COLN_CUR_YR_WC,"
								+ "COLN_MAINT ," + "COLN_YESTER_YR_WC," + "CB_MAINT_CHARGES,"
								+ "CB_CUR_YR_WC,CB_YESTER_YR_WC,OB_INT_AMT_WC,CB_INT_AMT_WC ,COLN_INT_WC)";

						sc = obj.isNull(rs_wc.getString("SCHEME_SNO"), 1);
						String sch_amt = obj.isNull(rs_wc.getString("TOTAL_AMT"), 1);
						String stype = obj.isNull(obj.getValue("PMS_SCH_MASTER", "SCH_TYPE_ID", "where SCH_SNO=" + sc),
								1);

						ins_qry += " values (" + "" + maxsno + "," + Office_id + "," + ben + "," + sc + ","
								+ rs_wc.getString("QTY_CONSUMED") + "," + OB_MAINT_CHARGES + "," + OB_CUR_YR_WC + ","
								+ OB_YESTER_YR_WC + "," + sch_amt;

						// FAS RECEIPT COLLECTION

						String cond1 = " and collection_type=6 )"; // Yester Year Collection
						String cond2 = " and collection_type=7 )"; // Current Year Water Charges
						String cond3 = " and collection_type=8 )"; // maintence
						String cond4 = " and collection_type=9 )"; // Interest

						String main_cond = " AND cashbook_month =  " + billmonth + "	 AND cashbook_year =  "
								+ billyear + "    AND accounting_unit_id =  " + ACCOUNTING_UNIT_ID
								+ " 	 AND accounting_for_office_id = " + Office_id
								+ "    and sub_ledger_type_code = 10"
								+ " 	 and SUB_LEDGER_CODE in (select PROJECT_ID from PMS_MST_PROJECTS_VIEW where SCH_SNO= "
								+ sc + ")" + "	 AND receipt_no IN " + "   (SELECT receipt_no"
								+ "   FROM fas_receipt_master "
								+ "   WHERE RECEIPT_STATUS='L' and sub_ledger_type_code = 14"
								+ "    AND cashbook_month = " + billmonth + "    AND cashbook_year = " + billyear
								+ "    AND accounting_unit_id = " + ACCOUNTING_UNIT_ID
								+ "   AND accounting_for_office_id = " + Office_id + "    AND sub_ledger_code =" + ben
								+ "  )  and ACCOUNT_HEAD_CODE " + "  in " + "  ( select ACCOUNT_HEAD_CODE "
								+ "    from PMS_DCB_RECEIPT_ACCOUNT_MAP " + "   where sch_type_id =" + stype
								+ " and ACTIVE_STATUS='L' ";

						COLN_MAINT = "0";
						COLN_YESTER_YR_WC = "0";

						try {
							stmt4 = con1.createStatement();
							rs_coll = stmt4.executeQuery(
									"select sum(amount)from fas_receipt_transaction where   sub_ledger_type_code = 10 "
											+ " " + main_cond + "" + cond1);
							if (rs_coll.next()) {
								COLN_YESTER_YR_WC = obj2.isNull(rs_coll.getString(1), 1);
							}
							stmt4.close();
							rs_coll.close();
						} catch (Exception e) {
							System.out.println(" PMS--->DCB--->Bill_Demand ( 583 )--> " + e);
							COLN_YESTER_YR_WC = "0";
							stmt4.close();
							rs_coll.close();
						}

						// Current Year Collection From FAS

//System.out.println("Collection ===" +"  select sum(amount) from fas_receipt_transaction where   sub_ledger_type_code = 10 "+ " " + main_cond + "" + cond2);
						try {
							stmt4 = con1.createStatement();
							rs_coll = stmt4.executeQuery(
									"select sum(amount) from fas_receipt_transaction where   sub_ledger_type_code = 10 "
											+ " " + main_cond + "" + cond2);
							if (rs_coll.next()) {
								COLN_CUR_YR_WC = obj2.isNull(rs_coll.getString(1), 1);
							}
							stmt4.close();
							rs_coll.close();
						} catch (Exception e) {
							System.out.println(" PMS--->DCB--->Bill_Demand ( 610 )--> " + e);
							COLN_CUR_YR_WC = "0";
							stmt4.close();
							rs_coll.close();
						}

						// Current Year Collection From FAS End

						// MAINT Collection Start
						try {
							stmt4 = con1.createStatement();
							rs_coll = stmt4.executeQuery(
									"select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
											+ " " + main_cond + "" + cond3);
							if (rs_coll.next()) {
								COLN_MAINT = obj2.isNull(rs_coll.getString(1), 1);
							}
							stmt4.close();
							rs_coll.close();
						} catch (Exception e) {
							System.out.println(" PMS--->DCB--->Bill_Demand ( 636 )--> " + e);
							COLN_MAINT = "0";
							stmt4.close();
							rs_coll.close();
						}
						// MAINT Collection End
						// Interest Collection Start

						try {
							stmt5 = con1.createStatement();
							rs_coll = stmt5.executeQuery(
									"select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
											+ " " + main_cond + "" + cond4);
							if (rs_coll.next()) {
								COLN_INT_FOR_MTH_WC = obj2.isNull(rs_coll.getString(1), 1);
							} else {
								COLN_INT_FOR_MTH_WC = "0";
							}
							stmt5.close();
							rs_coll.close();
						} catch (Exception e) {
							System.out.println(" PMS--->DCB--->Bill_Demand ( 662 )--> " + e);
							COLN_INT_FOR_MTH_WC = "0";
							stmt5.close();
							rs_coll.close();
						}
						// Interest Collection End
						OB_YESTER_YR_WC = obj.isNull(OB_YESTER_YR_WC, 1);
						OB_CUR_YR_WC = obj.isNull(OB_CUR_YR_WC, 1);
						OB_MAINT_CHARGES = obj.isNull(OB_MAINT_CHARGES, 1);
						int cbyear_set = 0;
						cbyear_set = 0;
						if (Integer.parseInt(billmonth) < 4)
							cbyear_set = Integer.parseInt(billyear) - 1;
						else
							cbyear_set = Integer.parseInt(billyear);

						String ob_cur_yr_wc_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_CUR_YR_WC", " where
														// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH=3 AND
														// FIN_YEAR="+cbyear_set);
						String ob_cur_yr_maint_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_MAINT_CHARGES", "
															// where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and
															// MONTH=3 AND FIN_YEAR="+cbyear_set);;
						String ob_cur_yr_maint_int_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_CUR_YR_MAINT",
																// " where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																// and MONTH=3 AND FIN_YEAR="+cbyear_set);;
						String ob_cur_yr_yester_year_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_YESTER_YR_WC",
																// " where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																// and MONTH=3 AND FIN_YEAR="+cbyear_set);;
						String ob_cur_yr_int_3rd = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_AMT_WC", " where
														// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH=3 AND
														// FIN_YEAR="+cbyear_set);;

						String qry_new_2 = "select CB_CUR_YR_WC,CB_MAINT_CHARGES,CB_INT_CUR_YR_MAINT,CB_YESTER_YR_WC,CB_INT_AMT_WC from PMS_DCB_TRN_CB_MONTHLY ";
						qry_new_2 += " where BENEFICIARY_SNO=" + ben + " and sch_sno=" + sc
								+ " and MONTH=3 AND FIN_YEAR=" + cbyear_set;

						ResultSet rs_new_2 = null;
						rs_new_2 = obj.getRS(qry_new_2);
						if (rs_new_2.next()) {
							ob_cur_yr_wc_3rd = obj.isNull(rs_new_2.getString("CB_CUR_YR_WC"), 1);
							ob_cur_yr_maint_3rd = obj.isNull(rs_new_2.getString("CB_MAINT_CHARGES"), 1);
							ob_cur_yr_maint_int_3rd = obj.isNull(rs_new_2.getString("CB_INT_CUR_YR_MAINT"), 1);
							ob_cur_yr_yester_year_3rd = obj.isNull(rs_new_2.getString("CB_YESTER_YR_WC"), 1);
							ob_cur_yr_int_3rd = obj.isNull(rs_new_2.getString("CB_INT_AMT_WC"), 1);
						}
						rs_new_2.close();

						/*
						 * String ob_cur_yr_wc_3rd=obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_CUR_YR_WC",
						 * "   where BENEFICIARY_SNO="+ben+" and sch_sno="
						 * +sc+" and MONTH=3 AND FIN_YEAR="+cbyear_set); String
						 * ob_cur_yr_maint_3rd=obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_MAINT_CHARGES",
						 * "   where BENEFICIARY_SNO="+ben+" and sch_sno="
						 * +sc+" and MONTH=3 AND FIN_YEAR="+cbyear_set);; String
						 * ob_cur_yr_maint_int_3rd=obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
						 * "CB_INT_CUR_YR_MAINT", "   where BENEFICIARY_SNO="+ben+" and sch_sno="
						 * +sc+" and MONTH=3 AND FIN_YEAR="+cbyear_set);; String
						 * ob_cur_yr_yester_year_3rd=obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
						 * "CB_YESTER_YR_WC", "   where BENEFICIARY_SNO="+ben+" and sch_sno="
						 * +sc+" and MONTH=3 AND FIN_YEAR="+cbyear_set);; String
						 * ob_cur_yr_int_3rd=obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_AMT_WC",
						 * "   where BENEFICIARY_SNO="+ben+" and sch_sno="
						 * +sc+" and MONTH=3 AND FIN_YEAR="+cbyear_set);;
						 * 
						 * String DMD_UPTO_MTH_WC_prv_month=obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
						 * "DMD_UPTO_MTH_WC",
						 * "  where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH="
						 * +month_set+" AND FIN_YEAR="+year_set); String
						 * COLN_UPTO_MTH_WC_prv_month=obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
						 * "COLN_UPTO_MTH_WC",
						 * "  where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH="
						 * +month_set+" AND FIN_YEAR="+year_set); String
						 * COLN_UPTO_MTH_MC_prv_month=obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
						 * "COLN_UPTO_MTH_MAINT",
						 * "  where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH="
						 * +month_set+" AND FIN_YEAR="+year_set); String
						 * COLN_UPTO_MTH_yesteryr_prv_month=obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
						 * "COLN_UPTO_MTH_YESTER_YR",
						 * "  where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH="
						 * +month_set+" AND FIN_YEAR="+year_set); String
						 * COLN_UPTO_MTH_maint_int_prv_month=obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
						 * "COLN_INT_UPTO_MTH_MAINT",
						 * "  where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH="
						 * +month_set+" AND FIN_YEAR="+year_set); String
						 * COLN_UPTO_MTH_cur_yr_int_prv_month=obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
						 * "COLN_INT_UPTO_MTH_WC",
						 * "  where BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and MONTH="
						 * +month_set+" AND FIN_YEAR="+year_set);
						 */
						String DMD_UPTO_MTH_WC_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																// "DMD_UPTO_MTH_WC", " where BENEFICIARY_SNO="+ben+"
																// and sch_sno="+sc+" and MONTH="+month_set+" AND
																// FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_WC_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																// "COLN_UPTO_MTH_WC", " where BENEFICIARY_SNO="+ben+"
																// and sch_sno="+sc+" and MONTH="+month_set+" AND
																// FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_MC_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																// "COLN_UPTO_MTH_MAINT", " where
																// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+" and
																// MONTH="+month_set+" AND FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_yesteryr_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																		// "COLN_UPTO_MTH_YESTER_YR", " where
																		// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																		// and MONTH="+month_set+" AND
																		// FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_maint_int_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																		// "COLN_INT_UPTO_MTH_MAINT", " where
																		// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																		// and MONTH="+month_set+" AND
																		// FIN_YEAR="+year_set);
						String COLN_UPTO_MTH_cur_yr_int_prv_month = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY",
																		// "COLN_INT_UPTO_MTH_WC", " where
																		// BENEFICIARY_SNO="+ben+" and sch_sno="+sc+"
																		// and MONTH="+month_set+" AND
																		// FIN_YEAR="+year_set);

						if (Integer.parseInt(billmonth) == 4) {
							DMD_UPTO_MTH_WC_prv_month = "0";
							COLN_UPTO_MTH_WC_prv_month = "0";
							COLN_UPTO_MTH_MC_prv_month = "0";
							COLN_UPTO_MTH_yesteryr_prv_month = "0";
							COLN_UPTO_MTH_maint_int_prv_month = "0";
							COLN_UPTO_MTH_cur_yr_int_prv_month = "0";
						} else {
							String qry_new = "select DMD_UPTO_MTH_WC,COLN_UPTO_MTH_WC,COLN_UPTO_MTH_MAINT,COLN_UPTO_MTH_YESTER_YR,COLN_INT_UPTO_MTH_MAINT,COLN_INT_UPTO_MTH_WC from PMS_DCB_TRN_CB_MONTHLY ";
							qry_new += "  where BENEFICIARY_SNO=" + ben + " and sch_sno=" + sc + " and MONTH="
									+ month_set + " AND FIN_YEAR=" + year_set;
							ResultSet rs_new_1 = null;
							rs_new_1 = obj.getRS(qry_new);
							if (rs_new_1.next()) {
								DMD_UPTO_MTH_WC_prv_month = obj.isNull(rs_new_1.getString("DMD_UPTO_MTH_WC"), 1);
								COLN_UPTO_MTH_WC_prv_month = obj.isNull(rs_new_1.getString("COLN_UPTO_MTH_WC"), 1);
								COLN_UPTO_MTH_MC_prv_month = obj.isNull(rs_new_1.getString("COLN_UPTO_MTH_MAINT"), 1);
								COLN_UPTO_MTH_yesteryr_prv_month = obj
										.isNull(rs_new_1.getString("COLN_UPTO_MTH_YESTER_YR"), 1);
								COLN_UPTO_MTH_maint_int_prv_month = obj
										.isNull(rs_new_1.getString("COLN_INT_UPTO_MTH_MAINT"), 1);
								COLN_UPTO_MTH_cur_yr_int_prv_month = obj
										.isNull(rs_new_1.getString("COLN_INT_UPTO_MTH_WC"), 1);
							}
							rs_new_1.close();
						}
// Prov Code : 07 05 2013 CB_CUR_YR_WC = Double.toString( (Double.parseDouble(OB_CUR_YR_WC) + Double.parseDouble(sch_amt) )- Double.parseDouble(COLN_CUR_YR_WC));
						double OB_CUR_YR_WC_NEW = (Double.parseDouble(ob_cur_yr_wc_3rd)
								+ Double.parseDouble(DMD_UPTO_MTH_WC_prv_month))
								- Double.parseDouble(COLN_UPTO_MTH_WC_prv_month);
						OB_CUR_YR_WC = Double.toString(OB_CUR_YR_WC_NEW);
						CB_CUR_YR_WC = Double.toString(
								(OB_CUR_YR_WC_NEW + Double.parseDouble(sch_amt)) - Double.parseDouble(COLN_CUR_YR_WC));
						CB_CUR_YR_WC = Double.toString(Double.parseDouble(CB_CUR_YR_WC)
								+ Double.parseDouble(ADD_CHARGES_WC) - Double.parseDouble(MINUS_CHARGES_WC));
// Maint CB fin yr
						double OB_CUR_YR_maint_NEW = (Double.parseDouble(ob_cur_yr_maint_3rd))
								- Double.parseDouble(COLN_UPTO_MTH_MC_prv_month);
						OB_MAINT_CHARGES = Double.toString(OB_CUR_YR_maint_NEW);
						CB_MAINT_CHARGES = Double
								.toString(Double.parseDouble(OB_MAINT_CHARGES) - Double.parseDouble(COLN_MAINT));
						CB_MAINT_CHARGES = Double.toString(Double.parseDouble(CB_MAINT_CHARGES)
								+ Double.parseDouble(ADD_CHARGES_MC) - Double.parseDouble(MINUS_CHARGES_MC));
// Yester yr CB fin yr

						double OB_CUR_YR_yesteryr_NEW = (Double.parseDouble(ob_cur_yr_yester_year_3rd))
								- Double.parseDouble(COLN_UPTO_MTH_yesteryr_prv_month);
						OB_YESTER_YR_WC = Double.toString(OB_CUR_YR_yesteryr_NEW);
						CB_YESTER_YR_WC = Double
								.toString(Double.parseDouble(OB_YESTER_YR_WC) - Double.parseDouble(COLN_YESTER_YR_WC));
// maint int adds if any cb fin yr
						double OB_CUR_YR_maint_int_NEW = (Double.parseDouble(ob_cur_yr_maint_int_3rd));
						OB_INT_CUR_YR_MAINT = Double.toString(OB_CUR_YR_maint_int_NEW);
						CB_INT_CUR_YR_MAINT = Double
								.toString(Double.parseDouble(OB_INT_CUR_YR_MAINT) - Double.parseDouble("0"));
// wc int  cb fin yr
						CB_INT_AMT_WC = "0";
						double OB_CUR_YR_wc_int_NEW = (Double.parseDouble(ob_cur_yr_int_3rd))
								- Double.parseDouble(COLN_UPTO_MTH_cur_yr_int_prv_month);
						OB_INT_AMT_WC = Double.toString(OB_CUR_YR_wc_int_NEW);
						CB_INT_AMT_WC = Double.toString(int_calc2
								+ (Double.parseDouble(OB_INT_AMT_WC) - Double.parseDouble(COLN_INT_FOR_MTH_WC)));

//CB_CUR_YR_WC = Double.toString( (Double.parseDouble(OB_CUR_YR_WC) + Double.parseDouble(sch_amt) )- Double.parseDouble(COLN_CUR_YR_WC));
//CB_CUR_YR_WC= Double.toString( Double.parseDouble(CB_CUR_YR_WC)+Double.parseDouble(ADD_CHARGES_WC)-Double.parseDouble(MINUS_CHARGES_WC));

						String Other_charge_cond3_up = " where  CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
								+ billyear + " and BENEFICIARY_SNO =" + ben + "  and OFFICE_ID =" + Office_id
								+ " and CR_DR_INDICATOR='DR' ";

						String Other_charge_cond4_up = " where  CASHBOOK_MONTH=" + billmonth + " and CASHBOOK_YEAR="
								+ billyear + " and BENEFICIARY_SNO =" + ben + "  and OFFICE_ID =" + Office_id
								+ " and CR_DR_INDICATOR='CR' ";

						String include_CB_ADD = "update PMS_DCB_OTHER_CHARGES set INCLUDE_CB='Y'"
								+ Other_charge_cond3_up;
						int add_affected_row = obj.rowUpdate(include_CB_ADD);
						String include_CB_SUB = "update PMS_DCB_OTHER_CHARGES set INCLUDE_CB='Y'"
								+ Other_charge_cond4_up;
						int sub_affected_row = obj.rowUpdate(include_CB_SUB);

						CB_MAINT_CHARGES = Double
								.toString(Double.parseDouble(OB_MAINT_CHARGES) - Double.parseDouble(COLN_MAINT));
						net_CB_CUR_YR_WC += Double.parseDouble(CB_CUR_YR_WC);

						net_CB_MAINT += Double.parseDouble(CB_MAINT_CHARGES);
						net_CB_YESTER_YR_WC += Double.parseDouble(CB_YESTER_YR_WC);
						net_COLN_CUR_YR_WC += Double.parseDouble(COLN_CUR_YR_WC);
						net_COLN_MAINT += Double.parseDouble(COLN_MAINT);
						net_COLN_YESTER_YR_WC += Double.parseDouble(COLN_YESTER_YR_WC);

						str_CB_INT_AMT_WC = Math
								.round(int_calc2 + (sch_OB_INT_AMT_WC - Double.parseDouble(COLN_INT_FOR_MTH_WC)));

						ins_qry += "," + COLN_CUR_YR_WC + "," + COLN_MAINT + "," + COLN_YESTER_YR_WC + ","
								+ CB_MAINT_CHARGES + "," + "" + CB_CUR_YR_WC + "," + CB_YESTER_YR_WC + ","
								+ sch_OB_INT_AMT_WC + "," + str_CB_INT_AMT_WC + "," + COLN_INT_FOR_MTH_WC + ")";

						if (ben_int == null)
							ben_int = "0";
						sch_wise_int = ((Double.parseDouble(OB_YESTER_YR_WC) + Double.parseDouble(OB_CUR_YR_WC)
								- Double.parseDouble(COLN_INT_FOR_MTH_WC)) * Double.parseDouble(ben_int) / 100);
						int_calc2 = int_calc2;

						int sch_count = obj.getCount("PMS_DCB_TRN_BILL_DMD_SCH", "where BENEFICIARY_SNO=" + ben
								+ " and   BILL_SNO in (select Bill_sno from PMS_DCB_TRN_BILL_DMD where  BENEFICIARY_SNO="
								+ ben + " and BILL_MONTH=" + billmonth + " and  BILL_YEAR=" + billyear + ") and SCH_NO="
								+ sc);
						if (sch_count == 0)
							row += obj.setUpd(ins_qry);
// Demand Scheme Complete here 
						/*
						 * Data Store in PMS_DCB_TRN_CB_MONTHLY table for month by ben schecme wise
						 */
// Monthly Table insert 
						int c = obj.getCount("PMS_DCB_TRN_CB_MONTHLY", "where BENEFICIARY_SNO=" + ben + " and FIN_YEAR="
								+ billyear + " and   MONTH=" + billmonth + " and SCH_SNO=" + sc);
						if (c > 0) {
							String del_qry = "delete from PMS_DCB_TRN_CB_MONTHLY  where BENEFICIARY_SNO=" + ben
									+ " and FIN_YEAR=" + billyear + " and   MONTH=" + billmonth + " and SCH_SNO=" + sc;
							ps = con.prepareStatement(del_qry);
							ps.executeUpdate();
						}

						// if Record is there in PMS_DCB_TRN_CB_MONTHLY

						c = obj.getCount("PMS_DCB_TRN_CB_MONTHLY", "where BENEFICIARY_SNO=" + ben + " and FIN_YEAR="
								+ billyear + " and   MONTH=" + billmonth + " and SCH_SNO=" + sc);
						int apr_year = 0;
						if (c == 0) {
							/* Date : 11/01/2011 */
							if (Integer.parseInt(billmonth) == 1) {
								prv_month = 12;
								prv_year = Integer.parseInt(billyear) - 1;
								apr_year = Integer.parseInt(billyear) - 1;
								ben_sno = Integer.parseInt(ben);
								sch_sno = Integer.parseInt(sc);
							} else {
								prv_month = Integer.parseInt(billmonth) - 1;
								if (Integer.parseInt(billmonth) <= 3) {
									apr_year = Integer.parseInt(billyear) - 1;
								} else {
									apr_year = Integer.parseInt(billyear);
								}
								prv_year = Integer.parseInt(billyear);
								ben_sno = Integer.parseInt(ben);
								sch_sno = Integer.parseInt(sc);
							}

							String cond_str = "where BENEFICIARY_SNO=" + ben_sno + " and FIN_YEAR=" + prv_year
									+ " and   MONTH=" + prv_month + " and SCH_SNO=" + sch_sno;
							String cond_str_apr = "where BENEFICIARY_SNO=" + ben_sno + " and FIN_YEAR=" + apr_year
									+ " and   MONTH=4" + " and SCH_SNO=" + sch_sno;
							String cond_str_cur = "where BENEFICIARY_SNO=" + ben_sno + " and FIN_YEAR=" + billyear
									+ " and   MONTH=" + billmonth + " and SCH_SNO=" + sch_sno;
							String DMD_UPTO_MTH_WC = "0", COLN_UPTO_MTH_WC = "", COLN_UPTO_MTH_MAINT = "",
									COLN_UPTO_MTH_YESTER_YR = "", COLN_INT_UPTO_MTH_MAINT = "",
									COLN_INT_UPTO_MTH_WC = "", DMD_INT_UPTO_MTH_WC = "";
							// if month is 4 reset to 0 all UPTO field for yearending process
							if (billmonth.equalsIgnoreCase("4")) {
								DMD_UPTO_MTH_WC = "0";
								COLN_UPTO_MTH_WC = "0";
								COLN_UPTO_MTH_MAINT = "0";
								COLN_UPTO_MTH_YESTER_YR = "0";
								COLN_INT_UPTO_MTH_MAINT = "0";
								COLN_INT_UPTO_MTH_WC = "0";
								DMD_INT_UPTO_MTH_WC = "0";
							} else {

								String qry_new_3 = "select DMD_UPTO_MTH_WC,COLN_UPTO_MTH_WC,COLN_UPTO_MTH_MAINT,COLN_UPTO_MTH_YESTER_YR,COLN_INT_UPTO_MTH_MAINT,COLN_INT_UPTO_MTH_WC,DMD_INT_UPTO_MTH_WC from PMS_DCB_TRN_CB_MONTHLY ";
								qry_new_3 += cond_str;
								ResultSet rs_new_3 = null;
								rs_new_3 = obj.getRS(qry_new_3);
								if (rs_new_3.next()) {
									DMD_UPTO_MTH_WC = obj.isNull(rs_new_3.getString("DMD_UPTO_MTH_WC"), 1);
									COLN_UPTO_MTH_WC = obj.isNull(rs_new_3.getString("COLN_UPTO_MTH_WC"), 1);
									COLN_UPTO_MTH_MAINT = obj.isNull(rs_new_3.getString("COLN_UPTO_MTH_MAINT"), 1);
									COLN_UPTO_MTH_YESTER_YR = obj.isNull(rs_new_3.getString("COLN_UPTO_MTH_YESTER_YR"),
											1);
									COLN_INT_UPTO_MTH_MAINT = obj.isNull(rs_new_3.getString("COLN_INT_UPTO_MTH_MAINT"),
											1);
									COLN_INT_UPTO_MTH_WC = obj.isNull(rs_new_3.getString("COLN_INT_UPTO_MTH_WC"), 1);
									DMD_INT_UPTO_MTH_WC = obj.isNull(rs_new_3.getString("DMD_INT_UPTO_MTH_WC"), 1);
								}
								rs_new_3.close();

								/*
								 * DMD_UPTO_MTH_WC = obj.isNull(
								 * obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_UPTO_MTH_WC",
								 * cond_str),1);//cond_str prv COLN_UPTO_MTH_WC =
								 * obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_WC",
								 * cond_str),1); COLN_UPTO_MTH_MAINT =
								 * obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_MAINT",
								 * cond_str),1); COLN_UPTO_MTH_YESTER_YR =
								 * obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_YESTER_YR", cond_str);
								 * COLN_INT_UPTO_MTH_MAINT =
								 * obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_MAINT", cond_str);
								 * COLN_INT_UPTO_MTH_WC =
								 * obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_WC", cond_str);
								 * DMD_INT_UPTO_MTH_WC
								 * =obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_INT_UPTO_MTH_WC", cond_str);
								 */
							}

							DMD_UPTO_MTH_WC = Double
									.toString(Double.parseDouble(DMD_UPTO_MTH_WC) + Double.parseDouble(sch_amt)); // PRv
																													// month
																													// dmd_upto_wt_WC
																													// +
																													// cur
																													// month
																													// dmd_for_mth_Wc
							// COLN_UPTO_MTH_WC = Double.toString(Double.parseDouble(COLN_UPTO_MTH_WC)+
							// Double.parseDouble(COLN_CUR_YR_WC) );
							// 03/102/2012
							COLN_UPTO_MTH_WC = Double.toString(Double.parseDouble(COLN_UPTO_MTH_WC)
									+ ((Double.parseDouble(COLN_CUR_YR_WC) - Double.parseDouble(ADD_CHARGES_WC))
											+ Double.parseDouble(MINUS_CHARGES_WC)));

							COLN_UPTO_MTH_MAINT = Double
									.toString(Double.parseDouble(COLN_UPTO_MTH_MAINT) + Double.parseDouble(COLN_MAINT));
							COLN_UPTO_MTH_YESTER_YR = Double.toString(Double.parseDouble(COLN_UPTO_MTH_YESTER_YR)
									+ Double.parseDouble(COLN_YESTER_YR_WC));
							COLN_INT_UPTO_MTH_MAINT = Double
									.toString(Double.parseDouble(COLN_INT_UPTO_MTH_MAINT) + Double.parseDouble("0"));
							COLN_INT_UPTO_MTH_WC = Double.toString(
									Double.parseDouble(COLN_INT_UPTO_MTH_WC) + Double.parseDouble(COLN_INT_FOR_MTH_WC));

							DMD_INT_UPTO_MTH_WC = Double
									.toString(Math.round(Double.parseDouble(DMD_INT_UPTO_MTH_WC) + DMD_INT_FOR_MTH_WC));

							/*
							 * START if CB_INT_AMT_WC closing balacne of int is less than zero 0 as default
							 * 20/06/2011 & All closing Balance < 0 then store 0
							 * 
							 */
							if (Double.parseDouble(CB_INT_AMT_WC) < 0) {
								CB_INT_AMT_WC = "0";
							}

							// if (Double.parseDouble(CB_MAINT_CHARGES)<0) {CB_MAINT_CHARGES="0";}
							// if (Double.parseDouble(CB_CUR_YR_WC)<0) {CB_CUR_YR_WC="0";}
							// if (Double.parseDouble(CB_YESTER_YR_WC)<0) {CB_YESTER_YR_WC="0";}
							// if (Double.parseDouble(CB_INT_CUR_YR_MAINT)<0) {CB_INT_CUR_YR_MAINT="0";}

							/* Date : 11/01/2011 */

							ins_qry2 = "insert into PMS_DCB_TRN_CB_MONTHLY (  " + "BENEFICIARY_OB_SNO,"
									+ "BENEFICIARY_SNO," + "FIN_YEAR," + "MONTH," + "OFFICE_ID," + "SCH_SNO,"
									+ "CB_MAINT_CHARGES," + "CB_CUR_YR_WC," + "CB_YESTER_YR_WC,"
									+ "CB_INT_CUR_YR_MAINT," + "CB_INT_AMT_WC," + "DMD_FOR_MTH_WC,"
									+ "DMD_INT_FOR_MTH_WC," + "COLN_FOR_MTH_MAINT," + "COLN_FOR_MTH_YESTER_YR,"
									+ "COLN_FOR_MTH_WC," + "COLN_INT_FOR_MTH_MAINT," + "COLN_INT_FOR_MTH_WC,"
									+ "UPDATED_BY_USER_ID," + "UPDATED_DATE " + ",DMD_UPTO_MTH_WC "
									+ ",COLN_UPTO_MTH_WC " + ",COLN_UPTO_MTH_MAINT " + ",COLN_UPTO_MTH_YESTER_YR "
									+ ",COLN_INT_UPTO_MTH_MAINT " + ",COLN_INT_UPTO_MTH_WC " + ",DMD_INT_UPTO_MTH_WC "
									+ " " + ")" + "  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0," + COLN_INT_FOR_MTH_WC
									+ ",'" + userid + "',clock_timestamp(),?,?,?,?,?,?,?) " + "";

							int BENEFICIARY_CB_SNO = obj.getMax("PMS_DCB_TRN_CB_MONTHLY", "BENEFICIARY_OB_SNO", "");
							ps = con.prepareStatement(ins_qry2);
							ps.setInt(1, BENEFICIARY_CB_SNO);
							ps.setInt(2, Integer.parseInt(ben));
							ps.setInt(3, Integer.parseInt(billyear));
							ps.setInt(4, Integer.parseInt(billmonth));
							ps.setInt(5, Integer.parseInt(Office_id));
							ps.setInt(6, Integer.parseInt(sc));
							ps.setDouble(7, Double.parseDouble(CB_MAINT_CHARGES));
							ps.setDouble(8, Double.parseDouble(CB_CUR_YR_WC));
							ps.setDouble(9, Double.parseDouble(CB_YESTER_YR_WC));
							ps.setDouble(10, Double.parseDouble(CB_INT_CUR_YR_MAINT));
							ps.setDouble(11, str_CB_INT_AMT_WC);
							ps.setDouble(12, Double.parseDouble(sch_amt));
							ps.setDouble(13, DMD_INT_FOR_MTH_WC);
							ps.setDouble(14, Double.parseDouble(COLN_MAINT));
							ps.setDouble(15, Double.parseDouble(COLN_YESTER_YR_WC));
							ps.setDouble(16, Double.parseDouble(COLN_CUR_YR_WC));
							ps.setDouble(17, Double.parseDouble(DMD_UPTO_MTH_WC));
							ps.setDouble(18, Double.parseDouble(COLN_UPTO_MTH_WC));
							ps.setDouble(19, Double.parseDouble(COLN_UPTO_MTH_MAINT));
							ps.setDouble(20, Double.parseDouble(COLN_UPTO_MTH_YESTER_YR));
							ps.setDouble(21, Double.parseDouble(COLN_INT_UPTO_MTH_MAINT));
							ps.setDouble(22, Double.parseDouble(COLN_INT_UPTO_MTH_WC));
							ps.setDouble(23, Double.parseDouble(DMD_INT_UPTO_MTH_WC));
							ps.executeUpdate();
							/* 12/01/2010 */

							String qry_new_apr = "select OB_MAINT_CHARGES,OB_CUR_YR_WC,OB_MAINT_CHARGES,OB_YESTER_YR_WC,OB_INT_PRV_YR_MAINT,OB_INT_CUR_YR_MAINT,OB_INT_AMT_WC from PMS_DCB_OB_YEARLY ";
							qry_new_apr += "" + cond_str_apr;

							String apr_OB_MAINT_CHfARGES = "0";// obj.getValue("PMS_DCB_OB_YEARLY","OB_MAINT_CHARGES",
																// cond_str_apr);
							String apr_OB_CUR_YR_WC = "0";// obj.getValue("PMS_DCB_OB_YEARLY",
															// "OB_CUR_YR_WC",cond_str_apr);
							String apr_OB_MAINT_CHARGES = "0";// obj.getValue("PMS_DCB_OB_YEARLY","OB_MAINT_CHARGES",
																// cond_str_apr);
							String apr_OB_YESTER_YR_WC = "0";// obj.getValue("PMS_DCB_OB_YEARLY",
																// "OB_YESTER_YR_WC",cond_str_apr);
							String apr_OB_INT_PRV_YR_MAINT = "0";// obj.getValue("PMS_DCB_OB_YEARLY","OB_INT_PRV_YR_MAINT",
																	// cond_str_apr);
							String apr_OB_INT_CUR_YR_MAINT = "0";// obj.getValue("PMS_DCB_OB_YEARLY","OB_INT_CUR_YR_MAINT",
																	// cond_str_apr);
							String apr_OB_INT_AMT_WC = "0";// obj.getValue("PMS_DCB_OB_YEARLY",
															// "OB_INT_AMT_WC",cond_str_apr);

							ResultSet rs_new_apr = null;
							rs_new_apr = obj.getRS(qry_new_apr);
							if (rs_new_apr.next()) {
								apr_OB_MAINT_CHfARGES = obj.isNull(rs_new_apr.getString("OB_MAINT_CHARGES"), 1);
								apr_OB_CUR_YR_WC = obj.isNull(rs_new_apr.getString("OB_CUR_YR_WC"), 1);
								apr_OB_MAINT_CHARGES = obj.isNull(rs_new_apr.getString("OB_MAINT_CHARGES"), 1);
								apr_OB_YESTER_YR_WC = obj.isNull(rs_new_apr.getString("OB_YESTER_YR_WC"), 1);
								apr_OB_INT_PRV_YR_MAINT = obj.isNull(rs_new_apr.getString("OB_INT_PRV_YR_MAINT"), 1);
								apr_OB_INT_CUR_YR_MAINT = obj.isNull(rs_new_apr.getString("OB_INT_CUR_YR_MAINT"), 1);
								apr_OB_INT_AMT_WC = obj.isNull(rs_new_apr.getString("OB_INT_AMT_WC"), 1);

							}
							rs_new_apr.close();

							String qry_new_apr_yr = "select  DMD_UPTO_MTH_WC,DMD_INT_UPTO_MTH_WC,COLN_UPTO_MTH_MAINT,COLN_UPTO_MTH_YESTER_YR,COLN_UPTO_MTH_WC,COLN_INT_UPTO_MTH_MAINT,COLN_INT_UPTO_MTH_WC from PMS_DCB_TRN_CB_MONTHLY ";
							qry_new_apr_yr += cond_str_cur;
							String yr_OB_FOR_MTH_YESTER_YR_WC = "0", yr_OB_FOR_MTH_INT_CY_MAINT = "0",
									yr_OB_FOR_MTH_INT_AMT_WC = "0", yr_OB_FOR_MTH_MAINT_CHARGES = "0",
									yr_OB_FOR_MTH_CUR_YR_WC = "0", yr_DMD_INT_FOR_MTH_WC = "0";

							String yr_DMD_UPTO_PRV_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_UPTO_MTH_WC",
																// cond_str_cur);
							String yr_DMD_INT_UPTO_PRV_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_INT_UPTO_MTH_WC",cond_str_cur);
							String yr_COLN_UPTO_PRV_MTH_MAINT = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_MAINT",cond_str_cur);
							String yr_COLN_UPTO_PRV_MTH_YESTER_YR = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_YESTER_YR",cond_str_cur);
							String yr_COLN_UPTO_PRV_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_WC",
																	// cond_str_cur);
							String yr_COLN_INT_UPTO_PRV_MTH_MAINT = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_MAINT",cond_str_cur);
							String yr_COLN_INT_UPTO_PRV_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_WC",cond_str_cur);
							ResultSet rs_new_yr = null;
							rs_new_yr = obj.getRS(qry_new_apr_yr);
							if (rs_new_yr.next()) {
								yr_DMD_UPTO_PRV_MTH_WC = obj.isNull(rs_new_yr.getString("DMD_UPTO_MTH_WC"), 1);
								yr_DMD_INT_UPTO_PRV_MTH_WC = obj.isNull(rs_new_yr.getString("DMD_INT_UPTO_MTH_WC"), 1);
								yr_COLN_UPTO_PRV_MTH_MAINT = obj.isNull(rs_new_yr.getString("COLN_UPTO_MTH_MAINT"), 1);
								yr_COLN_UPTO_PRV_MTH_YESTER_YR = obj
										.isNull(rs_new_yr.getString("COLN_UPTO_MTH_YESTER_YR"), 1);
								yr_COLN_UPTO_PRV_MTH_WC = obj.isNull(rs_new_yr.getString("COLN_UPTO_MTH_WC"), 1);
								yr_COLN_INT_UPTO_PRV_MTH_MAINT = obj
										.isNull(rs_new_yr.getString("COLN_INT_UPTO_MTH_MAINT"), 1);
								yr_COLN_INT_UPTO_PRV_MTH_WC = obj.isNull(rs_new_yr.getString("COLN_INT_UPTO_MTH_WC"),
										1);
							}
							rs_new_yr.close();
							int Yearlysno = obj.getMax("PMS_DCB_OB_YEARLY", "BENEFICIARY_OB_SNO", "");
							next_month = 0;
							nextyear = 0;
							if (Integer.parseInt(billmonth) == 12) {
								next_month = 1;
								nextyear = Integer.parseInt(billyear) + 1;
							} else {
								next_month = Integer.parseInt(billmonth) + 1;
								nextyear = Integer.parseInt(billyear);
							}

							String cond_str_next = "where BENEFICIARY_SNO=" + ben_sno + " and FIN_YEAR=" + nextyear
									+ " and MONTH=" + next_month + " and SCH_SNO=" + sch_sno;
							String yearly_insert = "", prv_ = "";
							prv_ = "delete from PMS_DCB_OB_YEARLY " + cond_str_next;

							ps = null;
							ps = con.prepareStatement(prv_);
							ps.execute();

							yearly_insert = "insert into PMS_DCB_OB_YEARLY ( " + "BENEFICIARY_OB_SNO,"
									+ "BENEFICIARY_SNO," + "FIN_YEAR," + "MONTH," + "OFFICE_ID," + "SCH_SNO,"
									+ "AS_ON_DATE," + "OB_MAINT_CHARGES," + "OB_CUR_YR_WC," + "OB_YESTER_YR_WC,"
									+ "OB_INT_PRV_YR_MAINT," + "OB_INT_CUR_YR_MAINT," + "OB_INT_AMT_WC,"
									+ "DMD_UPTO_PRV_MTH_WC," + "DMD_INT_UPTO_PRV_MTH_WC," + "COLN_UPTO_PRV_MTH_MAINT,"
									+ "COLN_UPTO_PRV_MTH_YESTER_YR," + "COLN_UPTO_PRV_MTH_WC,"
									+ "COLN_INT_UPTO_PRV_MTH_MAINT," + "COLN_INT_UPTO_PRV_MTH_WC,"
									+ "UPDATED_BY_USER_ID,UPDATED_DATE," + "OB_FOR_MTH_YESTER_YR_WC,"
									+ "OB_FOR_MTH_INT_CY_MAINT," + "OB_FOR_MTH_INT_AMT_WC,"
									+ "OB_FOR_MTH_MAINT_CHARGES," + "OB_FOR_MTH_CUR_YR_WC," + "DMD_INT_FOR_MTH_WC ) ";

							yearly_insert += " values (?,?,?,?,?,?,clock_timestamp(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,clock_timestamp() ,?,?,?,?,?,?)";
							ps = null;
							ps = con.prepareStatement(yearly_insert);
							ps.setInt(1, Yearlysno);
							ps.setString(2, ben);
							ps.setInt(3, nextyear);
							ps.setInt(4, next_month);
							ps.setString(5, Office_id);
							ps.setInt(6, sch_sno);
							ps.setString(7, apr_OB_MAINT_CHARGES);
							ps.setString(8, apr_OB_CUR_YR_WC);
							ps.setString(9, apr_OB_YESTER_YR_WC);
							ps.setString(10, apr_OB_INT_PRV_YR_MAINT);
							ps.setString(11, apr_OB_INT_CUR_YR_MAINT);
							ps.setString(12, apr_OB_INT_AMT_WC);
							ps.setString(13, yr_DMD_UPTO_PRV_MTH_WC);
							ps.setString(14, yr_DMD_INT_UPTO_PRV_MTH_WC);
							ps.setString(15, yr_COLN_UPTO_PRV_MTH_MAINT);
							ps.setString(16, yr_COLN_UPTO_PRV_MTH_YESTER_YR);
							ps.setString(17, yr_COLN_UPTO_PRV_MTH_WC);
							ps.setString(18, yr_COLN_INT_UPTO_PRV_MTH_MAINT);
							ps.setString(19, yr_COLN_INT_UPTO_PRV_MTH_WC);
							ps.setString(20, userid);
							ps.setString(21, yr_OB_FOR_MTH_YESTER_YR_WC);
							ps.setString(22, yr_OB_FOR_MTH_INT_CY_MAINT);
							ps.setString(23, yr_OB_FOR_MTH_INT_AMT_WC);
							ps.setString(24, yr_OB_FOR_MTH_MAINT_CHARGES);
							ps.setString(25, yr_OB_FOR_MTH_CUR_YR_WC);
							ps.setString(26, yr_DMD_INT_FOR_MTH_WC);

							ps.executeUpdate();

							// System.out.println(" ben ---> " + ben + " sch_sno---- " + sch_sno +" ---
							// DIV_BILL_NO" + DIV_BILL_NO );

							ps = null;
							String prvOB_FOR_MTH_YESTER_YR_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_YESTER_YR_WC",
																	// cond_str_cur);
							String prvOB_FOR_MTH_INT_CY_MAINT = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_CUR_YR_MAINT",cond_str_cur);
							String prvOB_FOR_MTH_INT_AMT_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_AMT_WC",
																	// cond_str_cur);
							String prvOB_FOR_MTH_MAINT_CHARGES = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_MAINT_CHARGES",cond_str_cur);
							String prvOB_FOR_MTH_CUR_YR_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_CUR_YR_WC",
																	// cond_str_cur);
							String prvDMD_INT_FOR_MTH_WC = "0";// obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_INT_FOR_MTH_WC",
																// cond_str_cur);

							String qry_new_5 = "select CB_YESTER_YR_WC,CB_INT_CUR_YR_MAINT,CB_INT_AMT_WC,CB_MAINT_CHARGES,CB_CUR_YR_WC,DMD_INT_FOR_MTH_WC from PMS_DCB_TRN_CB_MONTHLY ";
							qry_new_5 += cond_str_cur;
							ResultSet rs_new_5 = null;
							rs_new_5 = obj.getRS(qry_new_5);
							if (rs_new_5.next()) {
								prvOB_FOR_MTH_YESTER_YR_WC = obj.isNull(rs_new_5.getString("CB_YESTER_YR_WC"), 1);
								prvOB_FOR_MTH_INT_CY_MAINT = obj.isNull(rs_new_5.getString("CB_INT_CUR_YR_MAINT"), 1);
								prvOB_FOR_MTH_INT_AMT_WC = obj.isNull(rs_new_5.getString("CB_INT_AMT_WC"), 1);
								prvOB_FOR_MTH_MAINT_CHARGES = obj.isNull(rs_new_5.getString("CB_MAINT_CHARGES"), 1);
								prvOB_FOR_MTH_CUR_YR_WC = obj.isNull(rs_new_5.getString("CB_CUR_YR_WC"), 1);
								prvDMD_INT_FOR_MTH_WC = obj.isNull(rs_new_5.getString("DMD_INT_FOR_MTH_WC"), 1);

							}
							rs_new_5.close();

							String yearly_ob_update = " update PMS_DCB_OB_YEARLY set " + " OB_FOR_MTH_YESTER_YR_WC="
									+ prvOB_FOR_MTH_YESTER_YR_WC + ",OB_FOR_MTH_INT_CY_MAINT="
									+ prvOB_FOR_MTH_INT_CY_MAINT + ",OB_FOR_MTH_INT_AMT_WC=" + prvOB_FOR_MTH_INT_AMT_WC
									+ ",OB_FOR_MTH_MAINT_CHARGES=" + prvOB_FOR_MTH_MAINT_CHARGES
									+ ",OB_FOR_MTH_CUR_YR_WC=" + prvOB_FOR_MTH_CUR_YR_WC + ",DMD_INT_FOR_MTH_WC="
									+ prvDMD_INT_FOR_MTH_WC + "   " + cond_str_next;
							ps = con.prepareStatement(yearly_ob_update);
							ps.executeUpdate();

							prvOB_FOR_MTH_INT_AMT_WC = "0";
							prvDMD_INT_FOR_MTH_WC = "0";
							apr_OB_INT_AMT_WC = "0";
							prvOB_FOR_MTH_INT_AMT_WC = "0";
							OB_INT_AMT_WC = "0";

							/* 12/01/2010 END */
							String _zero_set_qry = "";

							/* if Month ==3 then */

							if (billmonth.equalsIgnoreCase("3")) {
								_zero_set_qry = "update PMS_DCB_OB_YEARLY set OB_CUR_YR_WC=OB_FOR_MTH_CUR_YR_WC,"
										+ "OB_YESTER_YR_WC=OB_FOR_MTH_YESTER_YR_WC,"
										+ "OB_MAINT_CHARGES=OB_FOR_MTH_MAINT_CHARGES,"
										+ "OB_INT_AMT_WC=OB_FOR_MTH_INT_AMT_WC,"
										+ "OB_INT_PRV_YR_MAINT=OB_FOR_MTH_INT_CY_MAINT   , COLN_UPTO_PRV_MTH_MAINT=0,COLN_UPTO_PRV_MTH_YESTER_YR=0,COLN_UPTO_PRV_MTH_WC=0,COLN_INT_UPTO_PRV_MTH_MAINT=0,COLN_INT_UPTO_PRV_MTH_WC=0 ,DMD_UPTO_PRV_MTH_WC=0,DMD_INT_UPTO_PRV_MTH_WC=0  WHERE BENEFICIARY_SNO= "
										+ ben + " AND fin_year=" + nextyear + "AND MONTH=" + next_month
										+ " and OFFICE_ID=" + Office_id + " and SCH_SNO=" + sch_sno;

								int up1 = obj.rowUpdate(_zero_set_qry);
								_zero_set_qry = "update PMS_DCB_OB_YEARLY_TOTAL set OB_CUR_YR_WC=OB_FOR_MTH_CUR_YR_WC,"
										+ " OB_YESTER_YR_WC=OB_FOR_MTH_YESTER_YR_WC,"
										+ " OB_MAINT_CHARGES=OB_FOR_MTH_MAINT_CHARGES,"
										+ " OB_INT_AMT_WC=OB_FOR_MTH_INT_AMT_WC,"
										+ " OB_INT_PRV_YR_MAINT=OB_FOR_MTH_INT_CY_MAINT    , COLN_UPTO_PRV_MTH_MAINT=0,COLN_UPTO_PRV_MTH_YESTER_YR=0,COLN_UPTO_PRV_MTH_WC=0,COLN_INT_UPTO_PRV_MTH_MAINT=0,COLN_INT_UPTO_PRV_MTH_WC=0 ,DMD_UPTO_PRV_MTH_WC=0,DMD_INT_UPTO_PRV_MTH_WC=0  WHERE BENEFICIARY_SNO= "
										+ ben + " AND fin_year         = " + nextyear + " AND MONTH            = "
										+ next_month + " and OFFICE_ID=" + Office_id;
								up1 = obj.rowUpdate(_zero_set_qry);
							}
						}

						ADD_CHARGES_WC = "0";
						MINUS_CHARGES_WC = "0";
						sch_OB_INT_AMT_WC = 0;
						DMD_INT_FOR_MTH_WC = 0;
					} // Phase 2 END
					rs_wc.close();

					/*
					 * Update the COLN_CUR_YR_WC, COLN_YESTER_YR_WC, COLN_MAINT, CB_MAINT_CHARGES,
					 * CB_CUR_YR_WC, CB_YESTER_YR_WC, CB_INT_AMT_WC To PMS_DCB_TRN_BILL_DMD
					 */
					up_qry = "update PMS_DCB_TRN_BILL_DMD set" + " COLN_CUR_YR_WC=" + net_COLN_CUR_YR_WC
							+ ",COLN_MAINT=" + net_COLN_MAINT + ",COLN_YESTER_YR_WC=" + net_COLN_YESTER_YR_WC
							+ ",CB_MAINT_CHARGES=" + net_CB_MAINT + ",CB_CUR_YR_WC=" + net_CB_CUR_YR_WC
							+ ",CB_YESTER_YR_WC=" + net_CB_YESTER_YR_WC + ",CB_INT_AMT_WC=" + CB_INT_AMT_WC
							+ "   where BILL_SNO=" + maxsno;

					stmt3.executeUpdate(up_qry);

					String Demand_month_CB = obj.getValue("PMS_DCB_TRN_BILL_DMD", "sum(CB_CUR_YR_WC+CB_YESTER_YR_WC)",
							"where BILL_SNO=" + maxsno);
					String Demand_month_Demand = obj.getValue("PMS_DCB_TRN_BILL_DMD", "MONTH_BILL_AMT",
							"where BILL_SNO=" + maxsno);
					double Prv_dmd_for_month = Double
							.parseDouble(obj.getValue("PMS_DCB_TRN_BILL_DMD", "sum(MONTH_BILL_AMT)", "where BILL_MONTH="
									+ month_set + " and BILL_YEAR=" + year_set + " and BENEFICIARY_SNO=" + ben));

					String int_main_wcCollection_cond = " AND cashbook_month =" + month_set + " AND cashbook_year="
							+ year_set + " AND accounting_unit_id=" + ACCOUNTING_UNIT_ID
							+ " AND accounting_for_office_id=" + Office_id + " and sub_ledger_type_code = 10"
							+ " and SUB_LEDGER_CODE in" + "    (   "
							+ "  		select PROJECT_ID from PMS_MST_PROJECTS_VIEW where SCH_SNO in  (  "
							+ "       select SCH_SNO from PMS_DCB_MST_BENEFICIARY_METRE " + "       where "
							+ meter_status + " BENEFICIARY_SNO=" + ben + " and OFFICE_ID=" + Office_id + " )"
							+ "    )  " + " AND receipt_no IN "
							+ "  ( SELECT receipt_no FROM fas_receipt_master WHERE RECEIPT_STATUS='L' and sub_ledger_type_code = 14"
							+ " AND cashbook_month=" + month_set + " AND cashbook_year=" + year_set
							+ " AND accounting_unit_id=" + ACCOUNTING_UNIT_ID + " AND accounting_for_office_id="
							+ Office_id + " AND sub_ledger_code =" + ben + "   ) "
							+ " And ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE  from PMS_DCB_RECEIPT_ACCOUNT_MAP"
							+ "    where sch_type_id in  (select SCH_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where "
							+ meter_status + " BENEFICIARY_SNO=" + ben + " and   OFFICE_ID=" + Office_id + ")"
							+ " And ACTIVE_STATUS='L' ";

					try {
						stmt5 = con.createStatement();
						rs_coll = stmt5.executeQuery(
								"select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
										+ " " + int_main_wcCollection_cond2 + "" + int_cond4);
						if (rs_coll.next()) {
							int_collection2 = obj2.isNull(rs_coll.getString(1), 1);
						} else {
							int_collection2 = "0";
						}
						rs_coll.close();

						rs_coll = stmt5.executeQuery(
								"select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
										+ " " + int_main_wcCollection_cond + "" + int_cond4);
						if (rs_coll.next()) {
							int_collection = obj2.isNull(rs_coll.getString(1), 1);
						} else {
							int_collection = "0";
						}
						rs_coll.close();

						stmt5.close();
					} catch (Exception e) {
						int_collection = "0";
						System.out.println(" PMS--->DCB--->Bill_Demand ( 1888 )--> " + e);
					}

					double int_calc_amt = ((Double.parseDouble(Demand_month_CB)
							- Double.parseDouble(Demand_month_Demand)) + Prv_dmd_for_month)
							- Double.parseDouble(int_collection);
					String ben_type_ben = obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID",
							"where " + new_cond + "  BENEFICIARY_SNO=" + ben);
					double int_rate = Double.parseDouble(obj.getValue("PMS_DCB_MST_INT", "INT_RATE",
							" where BENEFICIARY_TYPE=" + ben_type_ben + " and ACTIVE_STATUS='A'"));
					double int_calc = (int_calc_amt * int_rate) / 100;

					String total_ = " INSERT " + " INTO PMS_DCB_OB_YEARLY_TOTAL " + " ( " + " BENEFICIARY_SNO, "
							+ " FIN_YEAR, " + " MONTH, " + " OFFICE_ID, " + " OB_MAINT_CHARGES, " + " OB_CUR_YR_WC, "
							+ " OB_YESTER_YR_WC, " + " OB_INT_PRV_YR_MAINT, " + " OB_INT_CUR_YR_MAINT, "
							+ " OB_INT_AMT_WC, " + " DMD_UPTO_PRV_MTH_WC, " + " DMD_INT_UPTO_PRV_MTH_WC, "
							+ " COLN_UPTO_PRV_MTH_MAINT, " + " COLN_UPTO_PRV_MTH_YESTER_YR, "
							+ " COLN_UPTO_PRV_MTH_WC, " + " COLN_INT_UPTO_PRV_MTH_MAINT, "
							+ " COLN_INT_UPTO_PRV_MTH_WC, " + " OB_FOR_MTH_YESTER_YR_WC, "
							+ " OB_FOR_MTH_INT_CY_MAINT, " + " OB_FOR_MTH_INT_AMT_WC, " + " OB_FOR_MTH_MAINT_CHARGES, "
							+ " OB_FOR_MTH_CUR_YR_WC, " + " DMD_INT_FOR_MTH_WC " + " 		  ) "
							+ " SELECT BENEFICIARY_SNO, " + " FIN_YEAR, " + " MONTH, " + " OFFICE_ID, "
							+ " SUM(OB_MAINT_CHARGES), " + " SUM(OB_CUR_YR_WC), " + " SUM(OB_YESTER_YR_WC), "
							+ " SUM(OB_INT_PRV_YR_MAINT), " + " SUM(OB_INT_CUR_YR_MAINT), " + " SUM(OB_INT_AMT_WC), "
							+ " SUM(DMD_UPTO_PRV_MTH_WC), " + " SUM(DMD_INT_UPTO_PRV_MTH_WC), "
							+ " SUM(COLN_UPTO_PRV_MTH_MAINT), " + " SUM(COLN_UPTO_PRV_MTH_YESTER_YR), "
							+ " SUM(COLN_UPTO_PRV_MTH_WC), " + " SUM(COLN_INT_UPTO_PRV_MTH_MAINT), "
							+ " SUM(COLN_INT_UPTO_PRV_MTH_WC), " + " SUM(OB_FOR_MTH_YESTER_YR_WC), "
							+ " SUM(OB_FOR_MTH_INT_CY_MAINT), " + " SUM(OB_FOR_MTH_INT_AMT_WC), "
							+ " SUM(OB_FOR_MTH_MAINT_CHARGES), " + " SUM(OB_FOR_MTH_CUR_YR_WC), "
							+ " SUM(DMD_INT_FOR_MTH_WC) " + " FROM PMS_DCB_OB_YEARLY " + " WHERE BENEFICIARY_SNO= "
							+ ben + " AND fin_year         = " + nextyear + " AND MONTH            = " + next_month
							+ " and OFFICE_ID=" + Office_id + "  GROUP BY BENEFICIARY_SNO, " + " FIN_YEAR, "
							+ " MONTH, " + " OFFICE_ID ";

					int check_count = obj.getCount("PMS_DCB_OB_YEARLY_TOTAL", " where BENEFICIARY_SNO=" + ben
							+ " and MONTH=" + next_month + " and OFFICE_ID=" + Office_id);

					if (check_count > 0) {
						PreparedStatement del_ = null;
						try {
							del_ = con.prepareStatement("delete from PMS_DCB_OB_YEARLY_TOTAL where BENEFICIARY_SNO="
									+ ben + " and fin_year=" + nextyear + " and MONTH=" + next_month + " and OFFICE_ID="
									+ Office_id);
							del_.executeUpdate();
							del_.close();
						} catch (SQLException e) {
						}

					}

					stmt3.executeUpdate(total_);

					String wc_DR_total_cond = " where BENEFICIARY_SNO =" + ben + " and CASHBOOK_MONTH=" + billmonth
							+ " and CASHBOOK_YEAR=" + billyear
							+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ " where COLLECTION_TYPE=7 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='DR'   ";

					String wc_CR_total_cond = " where BENEFICIARY_SNO =" + ben + " and CASHBOOK_MONTH=" + billmonth
							+ " and CASHBOOK_YEAR=" + billyear
							+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP"
							+ " where COLLECTION_TYPE=7 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='CR'  ";

					String wc_DR_total_ = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(amount)",
							wc_DR_total_cond + " group by BENEFICIARY_SNO");
					String wc_CR_total_ = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(amount)",
							wc_CR_total_cond + " group by BENEFICIARY_SNO");

					String main_DR_total_cond = " where BENEFICIARY_SNO=" + ben + " and CASHBOOK_MONTH=" + billmonth
							+ " and CASHBOOK_YEAR=" + billyear
							+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ " where COLLECTION_TYPE=8 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='DR'   ";

					String main_CR_total_cond = " where BENEFICIARY_SNO=" + ben + " and CASHBOOK_MONTH=" + billmonth
							+ " and CASHBOOK_YEAR=" + billyear
							+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP  "
							+ " where COLLECTION_TYPE=8 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='CR'  ";

					String main_DR_total_ = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(amount)",
							main_DR_total_cond + " group by BENEFICIARY_SNO");
					String main_CR_total_ = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(amount)",
							main_CR_total_cond + " group by BENEFICIARY_SNO");

					String qry_up = "update PMS_DCB_OB_YEARLY_TOTAL set OB_FOR_MTH_CUR_YR_WC=(OB_FOR_MTH_CUR_YR_WC+"
							+ wc_DR_total_ + ")-(" + wc_CR_total_
							+ "),OB_FOR_MTH_MAINT_CHARGES=(OB_FOR_MTH_MAINT_CHARGES+" + main_DR_total_ + ")-("
							+ main_CR_total_ + ")   WHERE BENEFICIARY_SNO= " + ben + " AND fin_year         = "
							+ nextyear + " AND MONTH            = " + next_month + " and OFFICE_ID=" + Office_id;
					int up = obj.rowUpdate(qry_up);
//	System.out.println("Bill End for->> "+ ben);

				} // Phase 1 End
				System.out.println(" *** demand_insert End ***");
				xml = "<result><row>" + row;
				xml += "</row><total_demand>" + total_demand + "</total_demand><already_demand>" + already_demand
						+ "</already_demand></result>";
				con.close();
				con1.close();
				con2.close();
				con3.close();
				con4.close();
				con5.close();
			}

		}
		return xml;

	}

}
