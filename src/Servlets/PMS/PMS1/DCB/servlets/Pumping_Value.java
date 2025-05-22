package Servlets.PMS.PMS1.DCB.servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class Pumping_Value
{
	
	public long value_scheme(String qty, String TARIFF_FLAG, String ben_sno,String office_id, String SCHEME_SNO, String imonth, String iyear) 
	{
		String  Actual_amt= "0", lastamt = "0";
		try
		{
			String min_value = "0", allot_qty = "0", allot_flag = "", min_flag = "", QTY_CONSUMED = "0";
			double total_amount = 0;
			Controller obj = new Controller();
			Connection con = null;
			String Tot_Amt = "0", days = "0";
			double Cal_Excess = 0;
			double cal_amt = 0;
			String Alloted_amt = "0";
			CallableStatement proc_stmt = null;
			int sch_sno = 0;
			String AMT_RECTIFY ="0";
			int WC_TRN_SNO =1;// obj.getMax("PMS_DCB_WC_BILLING","WC_TRN_SNO",
			String QTY_CONSUMED1 =qty;
			proc_stmt =null;
			min_value =obj.getValue("PMS_DCB_ALLOTTED","MIN_QTY","where SCH_SNO="
									+SCHEME_SNO+" and BENEFICIARY_SNO="+ben_sno
									+" and ACTIVE_STATUS='A'");
			allot_qty =obj.getValue("PMS_DCB_ALLOTTED","ALLOT_QTY","where SCH_SNO="
									+SCHEME_SNO
									+ " and BENEFICIARY_SNO="
									+ ben_sno+ " and ACTIVE_STATUS='A'");
			
			String myear =iyear;
			String mmonth =imonth;
			Actual_amt ="0";
			int cmmonth =0;
			if (Integer.parseInt(mmonth) ==1) 
			{
				cmmonth =12;
			} else 
			{
				cmmonth =Integer.parseInt(mmonth) - 1;
			}
	
			String da =obj.month_val2(cmmonth);
			int cday =Integer.parseInt(da);
			if ((cmmonth ==2) && (Integer.parseInt(myear) % 4) ==0) 
			{
				cday =29;
			}
			allot_qty =Float.toString(Float.parseFloat(allot_qty)* cday);
			min_value =Float.toString(Float.parseFloat(min_value)* cday);
			allot_flag =obj.getValue("PMS_DCB_ALLOTTED","ALLOT_FLAG","where SCH_SNO="+ SCHEME_SNO
									+ " and BENEFICIARY_SNO="+ ben_sno
									+ " and ACTIVE_STATUS='A'");
			min_flag =obj.getValue("PMS_DCB_ALLOTTED","MIN_FLAG","where SCH_SNO="
									+ SCHEME_SNO
									+ " and BENEFICIARY_SNO="
									+ ben_sno+ " and ACTIVE_STATUS='A'");
	
			if (min_flag.trim().equalsIgnoreCase("y")) 
			{
				if (Float.parseFloat(min_value) > Float.parseFloat(QTY_CONSUMED1))
					QTY_CONSUMED1 =min_value;
				else
					QTY_CONSUMED1 =QTY_CONSUMED1;
			}
	
			proc_stmt =con.prepareCall("call PMS_DCB_SLAB (?,?,?,?,?,?,?,?,?,?,?,?) ");
			proc_stmt.setInt(1, Integer.parseInt(imonth));
			proc_stmt.setInt(2, Integer.parseInt(iyear));
			proc_stmt.setInt(3, Integer.parseInt(office_id));
			proc_stmt.setInt(4, Integer.parseInt(ben_sno));
			proc_stmt.setInt(5, Integer.parseInt("0"));
			proc_stmt.setInt(6, Integer.parseInt(SCHEME_SNO));
			proc_stmt.setFloat(7, Float.parseFloat(QTY_CONSUMED1));
			proc_stmt.registerOutParameter(8,java.sql.Types.VARCHAR);
			proc_stmt.setInt(9, Integer.parseInt("0"));
			
			// New Code 25/08/2011 Start ,
			// 23/09/2011
			  days =Integer.toString(cday);
			/*
			 * if (Integer.parseInt(year) % 4
			 * ==0 &&
			 * Integer.parseInt(mmonth)==2 ) {
			 * days="29"; } else {
			 * days=obj.month_val2
			 * (Integer.parseInt(mmonth)); }
			 */
	
			proc_stmt.setString(10, allot_flag);
			proc_stmt.setString(11, min_flag);
			proc_stmt.setInt(12, Integer.parseInt(days));
			// New Code 25/08/2011 End
	
			proc_stmt.execute();
			  Tot_Amt =obj.isNull(proc_stmt.getString(8), 1);
			// alloted flag is true and
			// calculate
			// the amount for that qty ===Start
			 System.out.println("Tot_Amt" + Tot_Amt);
			Cal_Excess =0;
			cal_amt =0;
			Alloted_amt ="0";
			if (allot_flag.trim().equalsIgnoreCase("y") && Float.parseFloat(allot_qty) < Float.parseFloat(QTY_CONSUMED1)) 
			{
				proc_stmt =con.prepareCall("call PMS_DCB_SLAB (?,?,?,?,?,?,?,?,?,?,?,?) ");
				proc_stmt.setInt(1, Integer.parseInt(imonth));
				proc_stmt.setInt(2, Integer.parseInt(iyear));
				proc_stmt.setInt(3, Integer.parseInt(office_id));
				proc_stmt.setInt(4, Integer.parseInt(ben_sno));
				proc_stmt.setInt(5, Integer.parseInt("0"));
				proc_stmt.setInt(6, Integer.parseInt(SCHEME_SNO));
				proc_stmt.setFloat(7, Float.parseFloat(allot_qty));
				proc_stmt.setFloat(8,0);
				proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
				proc_stmt.setInt(9, Integer.parseInt("1"));
				// New Code 25/08/2011 Start
				days =Integer.toString(cday);
				/*
				 * if (Integer.parseInt(year) %
				 * 4 ==0 &&
				 * Integer.parseInt(mmonth)==2 )
				 * { days="29"; } else {
				 * days=obj
				 * .month_val2(Integer.parseInt
				 * (mmonth)); }
				 */
				// days=obj.month_val2(Integer.parseInt(mmonth));
				proc_stmt.setString(10,allot_flag);
				proc_stmt.setString(11,min_flag);
				proc_stmt.setInt(12, Integer.parseInt(days));
				// New Code 25/08/2011 End
				proc_stmt.execute();
				Tot_Amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 2);
		
				
				if (Alloted_amt.equals(""))
					Alloted_amt ="0";
	
				Actual_amt =Alloted_amt;
				cal_amt =Double.parseDouble(Tot_Amt)- Double.parseDouble(Alloted_amt);
				Cal_Excess =(Float.parseFloat(QTY_CONSUMED1) - Float.parseFloat(allot_qty));
			} else {
				Actual_amt =Tot_Amt;
			}
		}
		catch(Exception  e)
		{
			
			System.out.println(e);
		}
		return (Math.round(Double.parseDouble(Actual_amt)  ));
	}
	public long Value(double qty, String TARIFF_FLAG, String ben_sno,String office_id, String meter_sno) 
	{
		String min_value = "0", allot_qty = "0", allot_flag = "", min_flag = "", QTY_CONSUMED = "0", Actual_amt = "0";
		double total_amount = 0;
		Controller obj = new Controller();
		Connection con = null;
		String Tot_Amt = "0", days = "0";
		double Cal_Excess = 0;
		double cal_amt = 0;
		String Alloted_amt = "0";
		CallableStatement proc_stmt = null;
		int sch_sno = 0;
		QTY_CONSUMED = Double.toString(qty);
		try {
			con = obj.con();
			obj.createStatement(con);
			int month = Integer.parseInt(obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH", " where OFFICE_ID="+ office_id), 1));
			int myear = Integer.parseInt(obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR", "where OFFICE_ID=" + office_id),1));
			
			int cmmonth = 0;
			if (month == 1) 
			{
				cmmonth = 12;
			} else 
			{
				cmmonth = month - 1;
			}

			String da = obj.month_val2(cmmonth);
			int cday = Integer.parseInt(da);
			if ((cmmonth == 2) && (myear % 4) == 0) {
				cday = 29;
			}
			if (TARIFF_FLAG.equalsIgnoreCase("S")) 
			{
				sch_sno = Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "SCHEME_SNO","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and METER_STATUS='L'"));
				
				min_value = obj.getValue("PMS_DCB_ALLOTTED", "MIN_QTY","where sch_sno=" + sch_sno + " and BENEFICIARY_SNO="
								+ ben_sno + " and ACTIVE_STATUS='A'");
				
				allot_qty = obj.getValue("PMS_DCB_ALLOTTED", "ALLOT_QTY","where sch_sno=" + sch_sno + " and BENEFICIARY_SNO="
								+ ben_sno + " and ACTIVE_STATUS='A'");
				
				Actual_amt = "0";
				allot_qty = Float.toString(Float.parseFloat(allot_qty) * cday);
				allot_flag = obj.getValue("PMS_DCB_ALLOTTED", "ALLOT_FLAG","where sch_sno=" + sch_sno + " and BENEFICIARY_SNO="+ ben_sno + " and ACTIVE_STATUS='A'");
				min_flag = obj.getValue("PMS_DCB_ALLOTTED", "MIN_FLAG","where sch_sno=" + sch_sno + " and BENEFICIARY_SNO="+ ben_sno + " and ACTIVE_STATUS='A'");
				min_value = Float.toString(Float.parseFloat(min_value) * cday);
				QTY_CONSUMED = Double.toString(Double.parseDouble(QTY_CONSUMED));
				
				if (min_flag.equalsIgnoreCase("y")) 
				{
					if (Float.parseFloat(min_value) > Float.parseFloat(QTY_CONSUMED))
						QTY_CONSUMED = min_value;
					else
						QTY_CONSUMED = QTY_CONSUMED;
				}
				proc_stmt = con.prepareCall("call PMS_DCB_WC_CAL_SLAB (?,?,?,?,?,?,?,?,?,?,?,?) ");
				proc_stmt.setInt(1, month);
				proc_stmt.setInt(2, myear);
				proc_stmt.setInt(3, Integer.parseInt(office_id));
				proc_stmt.setInt(4, Integer.parseInt(ben_sno));
				proc_stmt.setInt(5, Integer.parseInt("0"));
				proc_stmt.setInt(6, sch_sno);
				proc_stmt.setFloat(7, Float.parseFloat(QTY_CONSUMED));
				proc_stmt.setFloat(8,0);
				proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
				proc_stmt.setInt(9, Integer.parseInt("0"));
				days = Integer.toString(cday);
				proc_stmt.setString(10, allot_flag);
				proc_stmt.setString(11, min_flag);
				proc_stmt.setInt(12, Integer.parseInt(days));
				proc_stmt.execute();
				Tot_Amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
				Cal_Excess = 0;
				cal_amt = 0;
				Alloted_amt = "0";
				
				System.out.println(Float.parseFloat(QTY_CONSUMED));
				System.out.println(Tot_Amt);  
				
				if (allot_flag.trim().equalsIgnoreCase("y") && Float.parseFloat(allot_qty) < Float.parseFloat(QTY_CONSUMED)) 
				{
					proc_stmt = con.prepareCall("call PMS_DCB_WC_CAL_SLAB (?,?,?,?,?,?,?,?,?,?,?,?) ");
					proc_stmt.setInt(1, month);
					proc_stmt.setInt(2, myear);
					proc_stmt.setInt(3, Integer.parseInt(office_id));
					proc_stmt.setInt(4, Integer.parseInt(ben_sno));
					proc_stmt.setInt(5, Integer.parseInt("0"));
					proc_stmt.setInt(6, sch_sno);
					proc_stmt.setFloat(7, Float.parseFloat(allot_qty));
					proc_stmt.setFloat(8,0);
					proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
					proc_stmt.setInt(9, Integer.parseInt("1"));
					days = Integer.toString(cday);
					proc_stmt.setString(10, allot_flag);
					proc_stmt.setString(11, min_flag);
					proc_stmt.setInt(12, Integer.parseInt(days));
					proc_stmt.execute();
					Alloted_amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
					if (Alloted_amt.equals(""))
						Alloted_amt = "0";
					
					cal_amt = Double.parseDouble(Tot_Amt)- Double.parseDouble(Alloted_amt);
					Cal_Excess = (Float.parseFloat(QTY_CONSUMED) - Float.parseFloat(allot_qty));
				}
					Actual_amt = Tot_Amt;
			} else {
				sch_sno = Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "SCHEME_SNO","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and METER_STATUS='L'"));
				
				min_value = obj.getValue("PMS_DCB_ALLOTTED", "MIN_QTY","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and ACTIVE_STATUS='A'");
				
				allot_qty = obj.getValue("PMS_DCB_ALLOTTED", "ALLOT_QTY","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and ACTIVE_STATUS='A'");
				
				Actual_amt = "0";
				allot_qty = Float.toString(Float.parseFloat(allot_qty) * cday);
				
				allot_flag = obj.getValue("PMS_DCB_ALLOTTED", "ALLOT_FLAG","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and ACTIVE_STATUS='A'");
				
				min_flag = obj.getValue("PMS_DCB_ALLOTTED", "MIN_FLAG","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and ACTIVE_STATUS='A'");
				
				min_value = Float.toString(Float.parseFloat(min_value) * cday);
				
				QTY_CONSUMED = Double.toString(Double.parseDouble(QTY_CONSUMED));
				
				if (min_flag.equalsIgnoreCase("y")) 
				{

					if (Float.parseFloat(min_value) > Float.parseFloat(QTY_CONSUMED))						
						QTY_CONSUMED = min_value;
					else
						QTY_CONSUMED = QTY_CONSUMED;
				}
				String days1 = obj.month_val2(cmmonth);

				proc_stmt = con.prepareCall("call PMS_DCB_WC_CAL_SLAB_LOC (?,?,?,?,?,?,?,?,?,?,?,?) ");
				proc_stmt.setInt(1, month);
				proc_stmt.setInt(2, myear);
				
				proc_stmt.setInt(3, Integer.parseInt(office_id));
				proc_stmt.setInt(4, Integer.parseInt(ben_sno));
				proc_stmt.setInt(5, Integer.parseInt(meter_sno));
				
				proc_stmt.setInt(6, Integer.parseInt("0"));
				proc_stmt.setFloat(7, Float.parseFloat(QTY_CONSUMED));
				proc_stmt.setFloat(8,0);
				proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
				int cflag = 0;
				proc_stmt.setInt(9, cflag);
				proc_stmt.setString(10, allot_flag);
				proc_stmt.setString(11, min_flag);
				proc_stmt.setInt(12, Integer.parseInt(days1));
				proc_stmt.execute();
				//Tot_Amt = obj.isNull(proc_stmt.getString(8), 1);
				Tot_Amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
				Cal_Excess = 0;
				cal_amt = 0;
				Alloted_amt = "0";
				if (allot_flag.equalsIgnoreCase("y") && Float.parseFloat(allot_qty) < Float.parseFloat(QTY_CONSUMED)) 
				{
					proc_stmt = con.prepareCall("call PMS_DCB_WC_CAL_SLAB_LOC (?,?,?,?,?,?,?,?,?,?,?,?) ");
					proc_stmt.setInt(1, month);
					proc_stmt.setInt(2, myear);
					proc_stmt.setInt(3, Integer.parseInt(office_id));
					proc_stmt.setInt(4, Integer.parseInt(ben_sno));
					proc_stmt.setInt(5, Integer.parseInt(meter_sno));
					proc_stmt.setInt(6, Integer.parseInt("0"));
					proc_stmt.setFloat(7, Float.parseFloat(allot_qty));
					proc_stmt.setFloat(8,0);
					proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
					proc_stmt.setInt(9, Integer.parseInt("1"));
					days1 = obj.month_val2(cmmonth);
					proc_stmt.setString(10, allot_flag);
					proc_stmt.setString(11, min_flag);
					proc_stmt.setInt(12, Integer.parseInt(days1));
					proc_stmt.execute();
					
					//Alloted_amt = obj.isNull(proc_stmt.getString(8), 1);
					Alloted_amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
					if (Alloted_amt.equals(""))
						Alloted_amt = "0";
					Actual_amt = Alloted_amt;
					
					cal_amt = Double.parseDouble(Tot_Amt)- Double.parseDouble(Alloted_amt);
					Cal_Excess = (Float.parseFloat(QTY_CONSUMED) - Float.parseFloat(allot_qty));
					Actual_amt = Tot_Amt;
				}
				Actual_amt = Tot_Amt;
			}

		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return (Math.round(Double.parseDouble(Actual_amt)));
	}

	public long Value(double qty, String TARIFF_FLAG, String ben_sno,String office_id, String meter_sno, String imonth, String iyear) 
	{
		String min_value = "0", allot_qty = "0", allot_flag = "", min_flag = "", QTY_CONSUMED = "0", Actual_amt = "0";
		double total_amount = 0;
		Controller obj = new Controller();
		Connection con = null;
		String Tot_Amt = "0", days = "0";
		double Cal_Excess = 0;
		double cal_amt = 0;
		String Alloted_amt = "0";
		CallableStatement proc_stmt = null;
		int sch_sno = 0;

		try {
			con = obj.con();
			obj.createStatement(con);
			int month = Integer.parseInt(imonth);
			int myear = Integer.parseInt(iyear);
			int cmmonth = 0;
			if (month == 1) 
			{
				cmmonth = 12;
			} else 
			{
				cmmonth = month - 1;
			}
			double prv_qty=Double.parseDouble(obj.getValue("PMS_DCB_TRN_MONTHLY_PR","QTY_CONSUMED_NET", "where MONTH=" + month
					+ " and YEAR=" + myear + " and  METRE_SNO="+ meter_sno + " and BENEFICIARY_SNO="
					+ ben_sno + " and OFFICE_ID=" + office_id));
 
			qty = qty+ prv_qty;
			System.out.println(qty);System.out.println(prv_qty);
			
			QTY_CONSUMED = Double.toString(qty);
			String da = obj.month_val2(cmmonth);
			
			int cday = Integer.parseInt(da);
			if ((cmmonth == 2) && (myear % 4) == 0) 
			{
				cday = 29;
			}
			
			if (TARIFF_FLAG.equalsIgnoreCase("S")) 
			{
				sch_sno = Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "SCHEME_SNO","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and METER_STATUS='L'"));
				
				min_value = obj.getValue("PMS_DCB_ALLOTTED", "MIN_QTY","where sch_sno=" + sch_sno + " and BENEFICIARY_SNO="
								+ ben_sno + " and ACTIVE_STATUS='A'");
				
				allot_qty = obj.getValue("PMS_DCB_ALLOTTED", "ALLOT_QTY","where sch_sno=" + sch_sno + " and BENEFICIARY_SNO="
								+ ben_sno + " and ACTIVE_STATUS='A'");
				
				Actual_amt = "0";
				
				allot_qty = Float.toString(Float.parseFloat(allot_qty) * cday);
				allot_flag = obj.getValue("PMS_DCB_ALLOTTED", "ALLOT_FLAG","where sch_sno=" + sch_sno + " and BENEFICIARY_SNO="
								+ ben_sno + " and ACTIVE_STATUS='A'");
				
				min_flag = obj.getValue("PMS_DCB_ALLOTTED", "MIN_FLAG","where sch_sno=" + sch_sno + " and BENEFICIARY_SNO="
								+ ben_sno + " and ACTIVE_STATUS='A'");

				min_value = Float.toString(Float.parseFloat(min_value) * cday);
				QTY_CONSUMED = Double.toString(Double.parseDouble(QTY_CONSUMED));
				
				if (min_flag.equalsIgnoreCase("y")) 
				{
					if (Float.parseFloat(min_value) > Float.parseFloat(QTY_CONSUMED))
						QTY_CONSUMED = min_value;
					else
						QTY_CONSUMED = QTY_CONSUMED;
				}
				proc_stmt = con.prepareCall("call PMS_DCB_WC_CAL_SLAB (?,?,?,?,?,?,?,?,?,?,?,?) ");
				proc_stmt.setInt(1,month);
				proc_stmt.setInt(2,myear);
				proc_stmt.setString(3,office_id);
				proc_stmt.setString(4,ben_sno);
				proc_stmt.setInt(5,Integer.parseInt("0"));
				proc_stmt.setInt(6,sch_sno);
				proc_stmt.setFloat(7,Float.parseFloat(QTY_CONSUMED));
				proc_stmt.setFloat(8,0);
				proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
				proc_stmt.setInt(9,Integer.parseInt("0"));
				days = Integer.toString(cday);
				proc_stmt.setString(10,allot_flag);
				proc_stmt.setString(11,min_flag);
				proc_stmt.setInt(12,Integer.parseInt(days));
				proc_stmt.execute();
				//Tot_Amt = obj.isNull(proc_stmt.getString(8), 1);
				Tot_Amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
				Cal_Excess = 0;
				cal_amt = 0;
				Alloted_amt = "0";

				if (allot_flag.trim().equalsIgnoreCase("y") && Float.parseFloat(allot_qty) < Float.parseFloat(QTY_CONSUMED)) 
				{
					proc_stmt = con.prepareCall("call PMS_DCB_WC_CAL_SLAB (?,?,?,?,?,?,?,?,?,?,?,?) ");
					proc_stmt.setInt(1, month);
					proc_stmt.setInt(2, myear);
					proc_stmt.setString(3, office_id);
					proc_stmt.setString(4, ben_sno);
					proc_stmt.setInt(5, Integer.parseInt("0"));
					proc_stmt.setInt(6, sch_sno);
					proc_stmt.setFloat(7, Float.parseFloat(allot_qty));
					proc_stmt.setFloat(8,0);
					proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
					proc_stmt.setInt(9, Integer.parseInt("1"));
					days = Integer.toString(cday);
					proc_stmt.setString(10, allot_flag);
					proc_stmt.setString(11, min_flag);
					proc_stmt.setInt(12, Integer.parseInt(days));
					proc_stmt.execute();
					//Alloted_amt = obj.isNull(proc_stmt.getString(8), 2);
					Alloted_amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 2);
					if (Alloted_amt.equals(""))
						Alloted_amt = "0";
					cal_amt = Double.parseDouble(Tot_Amt)
							- Double.parseDouble(Alloted_amt);
					Cal_Excess = (Float.parseFloat(QTY_CONSUMED) - Float
							.parseFloat(allot_qty));
				}
				Actual_amt = Tot_Amt;
			} else {
				sch_sno = Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "SCHEME_SNO","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and METER_STATUS='L'"));
				
				min_value = obj.getValue("PMS_DCB_ALLOTTED", "MIN_QTY","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and ACTIVE_STATUS='A'");
				
				allot_qty = obj.getValue("PMS_DCB_ALLOTTED", "ALLOT_QTY","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and ACTIVE_STATUS='A'");
				Actual_amt = "0";
				allot_qty = Float.toString(Float.parseFloat(allot_qty) * cday);
				allot_flag = obj.getValue("PMS_DCB_ALLOTTED", "ALLOT_FLAG","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and ACTIVE_STATUS='A'");
				
				min_flag = obj.getValue("PMS_DCB_ALLOTTED", "MIN_FLAG","where METRE_SNO=" + meter_sno
								+ " and BENEFICIARY_SNO=" + ben_sno
								+ " and ACTIVE_STATUS='A'");
				min_value = Float.toString(Float.parseFloat(min_value) * cday);
				QTY_CONSUMED = Double.toString(Double.parseDouble(QTY_CONSUMED));
				
				if (min_flag.equalsIgnoreCase("y")) 
				{
					if (Float.parseFloat(min_value) > Float.parseFloat(QTY_CONSUMED))
						QTY_CONSUMED = min_value;
					else
						QTY_CONSUMED = QTY_CONSUMED;
				}
				String days1 = obj.month_val2(cmmonth);
				
				proc_stmt = con.prepareCall("call PMS_DCB_WC_CAL_SLAB_LOC (?,?,?,?,?,?,?,?,?,?,?,?) ");
				proc_stmt.setInt(1, month);
				proc_stmt.setInt(2, myear);
				proc_stmt.setInt(3, Integer.parseInt(office_id));
				proc_stmt.setInt(4, Integer.parseInt(ben_sno));
				proc_stmt.setInt(5, Integer.parseInt(meter_sno));
				proc_stmt.setInt(6, Integer.parseInt("0"));
				proc_stmt.setFloat(7, Float.parseFloat(QTY_CONSUMED));
				proc_stmt.setFloat(8,0);
				proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
				int cflag = 0;
				proc_stmt.setInt(9, cflag);
				proc_stmt.setString(10, allot_flag);
				proc_stmt.setString(11, min_flag);
				proc_stmt.setInt(12, Integer.parseInt(days1));
				proc_stmt.execute();
				//Tot_Amt = obj.isNull(proc_stmt.getString(8), 1);
				Tot_Amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
				Cal_Excess = 0;
				cal_amt = 0;
				Alloted_amt = "0";
				if (allot_flag.equalsIgnoreCase("y") && Float.parseFloat(allot_qty) < Float.parseFloat(QTY_CONSUMED)) 
				{
					proc_stmt = con.prepareCall("call PMS_DCB_WC_CAL_SLAB_LOC (?,?,?,?,?,?,?,?,?,?,?,?) ");
					proc_stmt.setInt(1, month);
					proc_stmt.setInt(2, myear);
					proc_stmt.setInt(3, Integer.parseInt(office_id));
					proc_stmt.setInt(4, Integer.parseInt(ben_sno));
					proc_stmt.setInt(5, Integer.parseInt(meter_sno));
					proc_stmt.setInt(6, Integer.parseInt("0"));
					proc_stmt.setFloat(7, Float.parseFloat(allot_qty));
					proc_stmt.setFloat(8,0);
					proc_stmt.registerOutParameter(8, java.sql.Types.REAL);
					proc_stmt.setInt(9, Integer.parseInt("1"));
					days1 = obj.month_val2(cmmonth);
					proc_stmt.setString(10, allot_flag);
					proc_stmt.setString(11, min_flag);
					proc_stmt.setInt(12, Integer.parseInt(days1));
					proc_stmt.execute();
					//Alloted_amt = obj.isNull(proc_stmt.getString(8), 1);
					Alloted_amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
					if (Alloted_amt.equals(""))
						Alloted_amt = "0";
					Actual_amt = Alloted_amt;
					cal_amt = Double.parseDouble(Tot_Amt)- Double.parseDouble(Alloted_amt);
					Cal_Excess = (Float.parseFloat(QTY_CONSUMED) - Float.parseFloat(allot_qty));
					Actual_amt = Tot_Amt;
				}
				Actual_amt = Tot_Amt;
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		double lastamt = 0;
		try {

			lastamt = Double.parseDouble(obj.getValue("PMS_DCB_WC_BILLING","sum(TOTAL_AMT)", " where BENEFICIARY_SNO=" + ben_sno
							+ "  and METRE_SNO="+meter_sno+" and OFFICE_ID=" + office_id + " and  MONTH="
							+ imonth + " and YEAR=" + iyear));
			
			System.out.println("lastamt -->"+lastamt);
			System.out.println("Actual_amt Now -->"+Actual_amt);
		} catch (NumberFormatException e) {
		} catch (Exception e) {
		}
		return (Math.round(Double.parseDouble(Actual_amt) - lastamt));
	}
}
