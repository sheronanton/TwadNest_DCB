/* 
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 21/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 
 *-----------------------------------------------------------------------------
 * 17/09/2011		Add the Beneficiary Status to 'L'   PS
 * 20/09/2011		Add the Meter Status to 'L'			PS
 *-----------------------------------------------------------------------------
 */
package Servlets.PMS.PMS1.DCB.reports;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class count_bean {
	private int rc;
	private int offid;
	private int prvmaster;
	private int local;
	private int prvben;
	private int totben;
	private int metercount;
	private int tariffset;
	private int tariffsetnotfr;
	private int locationmeter;
	private int schmeter;
	private int tariffcr;	
	private int pr_rec;
	private int cr_rec;
	private int val_rec;
	private int fr_rec;
	private int rateenter;
	private int ratenotenter;  
	private String monthval;
	private int year;
	private   double wc;
	private double collection;
	private BigDecimal collection2;
	private BigDecimal wc2;
	private BigDecimal otherchargescr;
	private BigDecimal othercharges;
	private BigDecimal netcollection;
	private double demand;
	private String wcstatus;
	private int demandedbillcount;
	private int acc_unit_id;
	private String tbstatus ;
	private String mset;
	private String yset;
	private int pmissing; 
	private int total_meter_net_set;
	private String set_month="0";
	private String set_year="0";
	String meter_status=Controller.meter_status;
	String meter_status2=Controller.meter_status2;
	String new_cond=Controller.new_cond; 
	public void setSet_month(String set_month) {
		this.set_month = set_month;
	}
	public void setSet_year(String set_year) {
		this.set_year = set_year;
	}
	public int getPmissing() {
		return pmissing;
	}
	public String getMset() {
		return mset;
	}
	public String getYset() {
		return yset;
	}
	public void setMset(String mset) {
		this.mset = mset;
	}
	public void setYset(String yset) {
		this.yset = yset;
	}
	public String getTbstatus() {
		return tbstatus;
	}
	public int getAcc_unit_id() {
		return acc_unit_id;
	}
	public int getDemandedbillcount() {
		return demandedbillcount;
	}
	public String getWcstatus() {
		return wcstatus;
	}
	public double getCollection() {
		return collection;
	}
	public BigDecimal getCollection2() {
		return collection2;
	}
	public double getDemand() {
		return demand;
	}
	public double getWc() {
		return wc;
	}
	public BigDecimal getWc2() {
		return wc2;
	}
	public String getMonthval() {
		return monthval;
	}
	public int getYear() {
		return year;
	}
	public int getCr_rec() {
		return cr_rec;
	}
	public int getPr_rec() {
		return pr_rec;
	}
	public int getVal_rec() {
		return val_rec;
	}
	public int getFr_rec() {
		return fr_rec;
	}
	public int getTariffsetnotfr() {
		return tariffsetnotfr;
	}
	public String getProcess() {
		Controller obj = new Controller();
		String Office_Name="";
		Connection con;
		try {
			con = obj.con();
			obj.createStatement(con);
			String com_cond=  " beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"  office_id="+offid+") and ";
			String met_cond=" METRE_SNO in (select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where office_id="+offid+" and meter_status ='L' )";
			Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+offid+ "");
			prvmaster=Integer.parseInt(obj.getValue("COM_MST_PRIVATE", "COUNT(*)", " where office_id="+offid));
			local=obj.getCount("PMS_DCB_MST_BENEFICIARY ", "where "+new_cond+"  BENEFICIARY_TYPE_ID <=6 and office_id="+offid);
			prvben=obj.getCount("PMS_DCB_MST_BENEFICIARY ", "where "+new_cond+"  BENEFICIARY_TYPE_ID > 6 and office_id="+offid);
			totben=local+prvben;
			total_meter_net_set =obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", " where meter_status='L' and  beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE status='L' and 	 office_id="+offid+") and office_id="+offid+" and SETTING_FLAG is null ");
			int met1=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where "+meter_status+" beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"  BENEFICIARY_TYPE_ID <= 6 and office_id="+offid+") and office_id="+offid);
			int met2=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where "+meter_status+" beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"  BENEFICIARY_TYPE_ID >6 and office_id="+offid+") and office_id="+offid);
			metercount=met1+met2;
			locationmeter=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where "+meter_status+" beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and  TARIFF_FLAG='L'  and office_id="+offid);
			Controller.condition(1, Integer.toString(offid));
			schmeter=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where "+meter_status+"  beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and  TARIFF_FLAG='S'  and office_id="+offid+" and "+Controller.meter_not_for_ben );
			tariffcr=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where "+meter_status+"  beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+")  and SETTING_FLAG ='CR' and office_id="+offid);
			tariffset=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", " where "+meter_status+" "+com_cond+" SETTING_FLAG ='FR' and beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE  "+new_cond+"  office_id="+offid+")  and office_id="+offid);
			tariffsetnotfr=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where  "+meter_status+" "+com_cond+"  ( SETTING_FLAG is null) and beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+")  and office_id="+offid);
			// old Query : rateenter=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where  "+meter_status+" "+com_cond+"  TARIFF_RATE is not null  and beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+")  and office_id="+offid);
			rateenter=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE a ", "where  "+meter_status+"  "+com_cond+" TARIFF_RATE IS not NULL	AND   exists ( SELECT BENEFICIARY_SNO FROM PMS_DCB_TARIFF_SLAB c WHERE c.office_id="+offid+" and c.beneficiary_sno=a.beneficiary_sno  ) "+
				 	" AND exists   (SELECT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY d  WHERE STATUS ='L'  AND d.office_id="+offid+"   and d.beneficiary_sno=a.beneficiary_sno  )"+ 
				 	" AND a.office_id="+offid+"");
			//old Query :  ratenotenter=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE ", "where  "+meter_status+"  "+com_cond+"  TARIFF_RATE is  null and BENEFICIARY_SNO not in ( select BENEFICIARY_SNO from PMS_DCB_TARIFF_SLAB where office_id="+offid+") and beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+")  and office_id="+offid);
			ratenotenter=obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE a ", "where  "+meter_status+"  "+com_cond+" TARIFF_RATE IS NULL	AND not exists ( SELECT BENEFICIARY_SNO FROM PMS_DCB_TARIFF_SLAB c WHERE c.office_id="+offid+" and c.beneficiary_sno=a.beneficiary_sno  ) "+
					 	" AND exists   (SELECT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY d  WHERE STATUS ='L'  AND d.office_id="+offid+"   and d.beneficiary_sno=a.beneficiary_sno  )"+ 
					 	" AND a.office_id="+offid+"");			
			String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+offid),1);
			String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+offid),1);
			if (set_month.equalsIgnoreCase("0"))
				smonth=smonth;
			else
				smonth=set_month;
			if (set_year.equalsIgnoreCase("0"))
				syear=syear;
			else
				syear=set_year;
			monthval=obj.month_val(smonth);
			year=Integer.parseInt(syear);
			int total_pp=obj.dist_getCount("PMS_DCB_TRN_MONTHLY_PR a,PMS_DCB_MST_BENEFICIARY_METRE b"," WHERE a.office_id=" +offid+
					" AND a.MONTH      = "+smonth+
					" AND a.YEAR       ="+syear+
					" and b.meter_status='L' "+
					" and a.beneficiary_sno=b.beneficiary_sno "+
					" and a.metre_sno=b.metre_sno "+
					" and a.office_id=b.office_id","a.metre_sno");
			int total_cr_rec=obj.dist_getCount("PMS_DCB_TRN_MONTHLY_PR a,PMS_DCB_MST_BENEFICIARY_METRE b"," WHERE a.office_id=" +offid+
					" AND a.MONTH      = "+smonth+
					" AND a.YEAR       ="+syear+
					" and b.meter_status='L' "+
					" and a.beneficiary_sno=b.beneficiary_sno "+
					" and a.metre_sno=b.metre_sno "+
					" and a.office_id=b.office_id and a.PROCESS_FLAG='CR'","a.metre_sno");
			int total_val_rec=obj.dist_getCount("PMS_DCB_TRN_MONTHLY_PR a,PMS_DCB_MST_BENEFICIARY_METRE b"," WHERE a.office_id=" +offid+
					" AND a.MONTH      = "+smonth+
					" AND a.YEAR       ="+syear+
					" and b.meter_status='L' "+
					" and a.beneficiary_sno=b.beneficiary_sno "+
					" and a.metre_sno=b.metre_sno "+
					" and a.office_id=b.office_id and a.PROCESS_FLAG='V'","a.metre_sno");
			int total_fr_rec=obj.dist_getCount("PMS_DCB_TRN_MONTHLY_PR a,PMS_DCB_MST_BENEFICIARY_METRE b"," WHERE a.office_id=" +offid+
					" AND a.MONTH      = "+smonth+
					" AND a.YEAR       ="+syear+
					" and b.meter_status='L' "+
					" and a.beneficiary_sno=b.beneficiary_sno "+
					" and a.metre_sno=b.metre_sno "+
					" and a.office_id=b.office_id and a.PROCESS_FLAG='FR'","a.metre_sno");  
			pr_rec=total_pp;  
			cr_rec=total_cr_rec;
			val_rec=total_val_rec;
			fr_rec=total_fr_rec;
			/*pmissing= obj.getCount("PMS_DCB_MST_BENEFICIARY ", "where "+new_cond+"  beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") " +
						 	" and beneficiary_sno   IN	 (  SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE  "+meter_status+" office_id="+offid+" AND METRE_SNO NOT   in ( " +
						 	"   select METRE_SNO from PMS_DCB_TRN_MONTHLY_PR where office_id="+offid+" and MONTH="+smonth+" and YEAR="+syear+" ) )") ;
			*/  
			pmissing= obj.getCount("PMS_DCB_MST_BENEFICIARY a", "where "+new_cond+" exists (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY b WHERE b.STATUS ='L' "+
					" AND b.office_id="+offid+"  and b.beneficiary_sno=a.beneficiary_sno  ) and a.office_id="+offid+" AND  exists (SELECT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE c "+
					" WHERE  METER_STATUS ='L'  AND c.office_id="+offid+" and c.beneficiary_sno = a.beneficiary_sno AND not exists (SELECT METRE_SNO FROM PMS_DCB_TRN_MONTHLY_PR d "+
					" WHERE d.office_id="+offid+"  AND d.MONTH="+smonth+"  AND d.YEAR="+syear+" and d.METRE_SNO=c.METRE_SNO ) ) ");    
			  
				String status="";
			 if (!obj.accountUID(offid).equalsIgnoreCase("0"))
				status=obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS", "WC_FREEZED"," where    OFFICE_ID="+offid+" and CASHBOOK_MONTH="+smonth+" and CASHBOOK_YEAR="+syear ),1);
			 else
				 status=obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS", "WC_FREEZED"," where    ACTUAL_OFFICE_ID="+offid+" and CASHBOOK_MONTH="+smonth+" and CASHBOOK_YEAR="+syear ),1);
			 
			  if (status.trim().equals("CR")) wcstatus="<font color='green' size='2'><b>Water Charges Calculation Finished....</b></font> <font color='red' size='2'><b>Not Yet Freezed !!</b></font>";
			  
	 		  else if (status.trim().equalsIgnoreCase("Y")) wcstatus="Water Charges  Freezed !";
	 		  else  wcstatus="<font color='red' size='2'><b>Water Charges yet to be Processed</b></font>...Please Freeze Pumping Return ! ";
			  wc=Double.parseDouble(obj.getValue("PMS_DCB_WC_BILLING", "sum(TOTAL_AMT)", "where OFFICE_ID="+offid+" and  month="+smonth+" and year="+syear));
			demandedbillcount=Integer.parseInt(obj.getValue("PMS_DCB_TRN_BILL_DMD", "count(*)", "where OFFICE_ID="+offid+" and  BILL_MONTH="+smonth+" and BILL_YEAR="+syear));			
			/*collection=Double.parseDouble(obj.getValue("FAS_RECEIPT_TRANSACTION", "sum(amount)", "WHERE ACCOUNTING_FOR_OFFICE_ID= "+offid+
											"  AND cashbook_month ="+smonth+" AND cashbook_year             ="+syear+" "+ 
											" and ACCOUNT_HEAD_CODE in (SELECT ACCOUNT_HEAD_CODE  from PMS_DCB_RECEIPT_ACCOUNT_MAP) AND receipt_no IN " + 
											" ( SELECT receipt_no  " +
										    " FROM FAS_RECEIPT_MASTER " +
										    "  WHERE SUB_LEDGER_TYPE_CODE  = 14 and SUB_LEDGER_CODE  in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where   OFFICE_ID="+offid+" and status='L') " +
										    "  AND ACCOUNTING_FOR_OFFICE_ID=" +offid +
										    "  AND cashbook_month          ="+smonth+"" +
										    "  AND cashbook_year           ="+syear+" and RECEIPT_STATUS='L')"));
			*/
			collection=Double.parseDouble(obj.getValue("FAS_RECEIPT_TRANSACTION a", "sum(amount)"," WHERE a.ACCOUNTING_FOR_OFFICE_ID= "+offid+" "+" AND a.cashbook_month="+smonth+" AND a.cashbook_year="+syear+" AND EXISTS " +
			"  (SELECT ACCOUNT_HEAD_CODE  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP b  WHERE b.account_head_code=a.account_head_code  ) AND EXISTS " +
			"  (SELECT receipt_no  FROM FAS_RECEIPT_MASTER b  WHERE b.SUB_LEDGER_TYPE_CODE = 14  AND a.receipt_no=b.receipt_no " +"  AND EXISTS  (SELECT BENEFICIARY_SNO " +
			"    FROM PMS_DCB_MST_BENEFICIARY d  WHERE d.OFFICE_ID="+offid+" AND d.status='L' AND d.beneficiary_sno=b.sub_ledger_code ) AND ACCOUNTING_FOR_OFFICE_ID="+offid+" " +
			"  AND cashbook_month="+smonth+" AND cashbook_year="+syear+" AND RECEIPT_STATUS='L')"));
			collection2 = new BigDecimal(collection); 
			wc2= new BigDecimal(wc);
			acc_unit_id=Integer.parseInt(obj.getValue("FAS_MST_ACCT_UNITS", "ACCOUNTING_UNIT_ID", " where ACCOUNTING_UNIT_OFFICE_ID="+offid));
			tbstatus=obj.getValue("FAS_TRIAL_BALANCE_STATUS", "TB_STATUS", " where ACCOUNTING_UNIT_ID = "+acc_unit_id+"  and ACCOUNTING_FOR_OFFICE_ID="+offid+" and CASHBOOK_YEAR =" +yset+ " and CASHBOOK_MONTH="+mset);
			othercharges =new BigDecimal(Double.parseDouble(obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)", "where OFFICE_ID::numeric="+offid+" and  CR_DR_INDICATOR='DR' and CASHBOOK_MONTH="+smonth+" and CASHBOOK_YEAR="+syear)));
			otherchargescr =new BigDecimal(Double.parseDouble(obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)", "where OFFICE_ID::numeric="+offid+" and  CR_DR_INDICATOR='CR' and CASHBOOK_MONTH="+smonth+" and CASHBOOK_YEAR="+syear)));
			netcollection=collection2.subtract(othercharges).add(otherchargescr);
		} catch (Exception e) {
			System.out.println(e);
		}
		return Office_Name;
	}
	public int getTotal_meter_net_set() {
		return total_meter_net_set;
	}
	public void setTotal_meter_net_set(int total_meter_net_set) {
		this.total_meter_net_set = total_meter_net_set;
	}
	public int getTariffcr() {
		return tariffcr;
	}
	public int getLocationmeter() {
		return locationmeter;
	}
	public int getSchmeter() {
		return schmeter;
	}
	public int getRc() {
		return rc;
	}
	public int getOffid() {
		return offid;
	}
	public void setOffid(int offid) {
		System.out.println(offid);
		this.offid = offid;
	}
	public int getPrvmaster() {
		return prvmaster;
	}
	public int getLocal() {
		return local;
	}
	public int getPrvben() {
		return prvben;
	}  
	public int getTotben() {
		return totben;
	}
	public int getMetercount() {
		return metercount;
	}
	public int getTariffset() {
		return tariffset;
	}
	public int getRateenter() {
		return rateenter;
	}
	public int getRatenotenter() {
		return ratenotenter;
	}
	public BigDecimal getOthercharges() {
		return othercharges;
	}
	public BigDecimal getNetcollection() {
		return netcollection;
	}
	public BigDecimal getOtherchargescr() {
		return otherchargescr;
	}
}
