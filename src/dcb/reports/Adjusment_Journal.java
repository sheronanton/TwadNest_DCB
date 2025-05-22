package dcb.reports;
import java.sql.Connection;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class Adjusment_Journal 
{
	private String Other_charge_cond1=null;
	private String Other_charge_cond1_dmd=null;
	private String Other_charge_cond2=null;
	private String Other_charge_cond1_mc =null; 
	private String Other_charge_cond2_mc =null;
	private String Cond_prv_other_charge =null;	
	private int month,year,ben_sno,office_id;
	private int prv_month,prv_year, sch_sno;
	private Controller obj_new=null;
	private Connection con_new=null;
	private String Ben_Cond =null;
	private String ADD_DMD_WC =null;
	private String ADD_CHARGES_WC =null;
	private String MINUS_CHARGES_WC =null;
	private String ADD_CHARGES_MC  =null;
	private String MINUS_CHARGES_MC =null;
	private String CB_INT_AMT_WC =null;
	public String getADD_DMD_WC() {
		return ADD_DMD_WC;
	}
	public String getADD_CHARGES_WC() {
		return ADD_CHARGES_WC;
	}
	public String getMINUS_CHARGES_WC() {
		return MINUS_CHARGES_WC;
	}
	public String getMINUS_CHARGES_MC() {
		return MINUS_CHARGES_MC;
	}
	public String getCB_INT_AMT_WC() {
		return CB_INT_AMT_WC;
	}
	public Adjusment_Journal()
	{
		try
		{
			obj_new=new Controller();
			con_new=obj_new.con();
			obj_new.createStatement(con_new);
		}catch(Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
	public void setPrv_month(int prv_month) {
		this.prv_month = prv_month;
	}
	public void setPrv_year(int prv_year) {
		this.prv_year = prv_year;
	}
	public void setSch_sno(int sch_sno) {
		this.sch_sno = sch_sno;
	} 
	public void setMonth(int month) {
		this.month = month;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setBen_sno(int ben_sno) {
		this.ben_sno = ben_sno;
	}
	public void setOffice_id(int office_id) {
		this.office_id = office_id;
	}	
	public synchronized void Condition_set()
	{
		  Other_charge_cond1 = " where  PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='DR' " 
			+" and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE=PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE " 
			+" and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=7 and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE not in (780406) "   
			+" and PMS_DCB_OTHER_CHARGES.SCH_TYPE_ID=PMS_DCB_RECEIPT_ACCOUNT_MAP.SCH_TYPE_ID  "
			+" and PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year
			+" and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="+office_id+"and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno 
			+" group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";
		  
		  Other_charge_cond1_dmd = " where PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='DR' " 
 			+" and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE=PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=7 and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE in (780406)"
 			+" and PMS_DCB_OTHER_CHARGES.SCH_TYPE_ID      =PMS_DCB_RECEIPT_ACCOUNT_MAP.SCH_TYPE_ID " 
 			+" and PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year
 	 		+"  and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="+office_id+" and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno
 	 		+" group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO"; 
		  
		  Other_charge_cond2 = " where PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE=PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=7 and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE not in (780406) "
 			+" and PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='CR' AND PMS_DCB_OTHER_CHARGES.SCH_TYPE_ID=PMS_DCB_RECEIPT_ACCOUNT_MAP.SCH_TYPE_ID  "
 			+" and PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year
 	 		+" and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="+ office_id+" and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno
 	 		+" group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";
		  
		  Other_charge_cond1_mc = " where PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='DR' and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE =PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=8  "
 			+" and PMS_DCB_OTHER_CHARGES.SCH_TYPE_ID  =PMS_DCB_RECEIPT_ACCOUNT_MAP.SCH_TYPE_ID and PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year
 	 		+" and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="+ office_id+ " and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno
 			+" group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";
		  
		  
		    Other_charge_cond2_mc = " where PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE=PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=8 "
 			+" and PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='CR' AND PMS_DCB_OTHER_CHARGES.SCH_TYPE_ID      =PMS_DCB_RECEIPT_ACCOUNT_MAP.SCH_TYPE_ID  " 
 			+" and PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year
 	 		+" and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="+office_id+" and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno
 	 		+" group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";
		
	}
	public synchronized void Condition_set2()
	{
		  Cond_prv_other_charge=" where MONTH="+this.prv_month+" and SCH_SNO="+this.sch_sno+" and OFFICE_ID="+this.office_id+" and YEAR="+this.prv_year+" and BENEFICIARY_SNO="+this.ben_sno;
		  Ben_Cond=" where MONTH="+this.month+" and OFFICE_ID="+this.office_id+" and SCH_SNO="+this.sch_sno+" and FIN_YEAR="+this.year+" and BENEFICIARY_SNO="+this.ben_sno;  
	}	
	public void valueGet()
	{
		System.out.println("Value Get"); 
		try {
			  this.ADD_DMD_WC = obj_new.getValue("PMS_DCB_OTHER_CHARGES,PMS_DCB_RECEIPT_ACCOUNT_MAP", "sum(AMOUNT)",Other_charge_cond1_dmd);			
			  this.ADD_CHARGES_WC = obj_new.getValue("PMS_DCB_OTHER_CHARGES,PMS_DCB_RECEIPT_ACCOUNT_MAP", "sum(AMOUNT)",Other_charge_cond1);
			  this.MINUS_CHARGES_WC = obj_new.getValue("PMS_DCB_OTHER_CHARGES,PMS_DCB_RECEIPT_ACCOUNT_MAP", "sum(AMOUNT)",Other_charge_cond2);
			  this.ADD_CHARGES_MC = obj_new.getValue("PMS_DCB_OTHER_CHARGES,PMS_DCB_RECEIPT_ACCOUNT_MAP", "sum(AMOUNT)",Other_charge_cond1_mc);
			  this.MINUS_CHARGES_MC = obj_new.getValue("PMS_DCB_OTHER_CHARGES,PMS_DCB_RECEIPT_ACCOUNT_MAP", "sum(AMOUNT)",Other_charge_cond2_mc);       
			  this.CB_INT_AMT_WC = obj_new.getValue("PMS_DCB_TRN_CB_MONTHLY", "sum(CB_INT_AMT_WC)",Ben_Cond);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
 
	public String getADD_CHARGES_MC() {
		return ADD_CHARGES_MC;
	}
}
