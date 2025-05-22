/* 
 * Created on : dd-mm-yy 
 * Author     : Tamil
 * Last Date  : 21/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 
 *-----------------------------------------------------------------------------
 */
package Servlets.PMS.PMS1.DCB.reports;
import java.sql.Connection;
import java.sql.ResultSet;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class Process_status_bean 
{
	private int dmd_count;
	private int ledger_count;
	private int total_ben;
	private int month;
	private String tariff_rate;
	private String tariff_img;
	private String pump_rtrn;
	private String pump_img;
	private String ob;
	private String ob_img;
	private String wc;
	private String wc_img;
	private String demand_generation;
	private String demand_generation_img;
	private String monthval;
	private int year;
	private int obstatusflag;
	private String msg_late1;
	private String msg_late2;  
	private String msg_late3;  
	private String msg_late4;
	private String msg_late5;
	String new_cond=Controller.new_cond; 
	String meter_status=Controller.meter_status; 
	//getter for images
	public String getMsg_late1() {
		return msg_late1;
	}
	public String getMsg_late2() {
		return msg_late2;
	}
	public String getMsg_late3() {
		return msg_late3;
	}
	public String getMsg_late4() {
		return msg_late4;
	}
	public String getMsg_late5() {
		return msg_late5;
	}
	//getter and setter for  counts
	public void setTariff_rate(String tariff_rate) {
		this.tariff_rate = tariff_rate;
	}
	public String getTariff_rate() {
		return tariff_rate;
	}
	public int getObstatusflag() {
		return obstatusflag;
	}
	public String getTariff_img() {
		return tariff_img;
	}
	public String getOb_img() {
		return ob_img;
	}
	public String getPump_img() {
		return pump_img;
	}
	public String getDemand_generation_img() {
		return demand_generation_img;
	}
	public void setPump_rtrn(String pump_rtrn) {
		this.pump_rtrn = pump_rtrn;
	}
	public String getWc_img() {
		return wc_img;
	}
	public String getPump_rtrn() {
		return pump_rtrn;
	}
	public void setOb(String ob) {
		this.ob = ob;
	} 
	public String getOb() {
		return ob;
	}
	public String getWc() {
		return wc;
	}
	public String getMonthval() {
		return monthval;
	}
	public int getYear() {
		return year;
	}
	private int offid;
	public String getDemand_generation() {
		return demand_generation;
	}
	public void setDemand_generation(String demand_generation) {
		this.demand_generation = demand_generation;
	}
	public String getProcess() {
		Controller obj = new Controller();
		String Office_Name="";
		int apr_year=0;
		String t_img="../../../../../images/tick_3.jpg";
		String c_img="../../../../../images/tick2.jpg";
		String msg_late="";
		Connection con;
		try {
			con = obj.con();    
			obj.createStatement(con);
			Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+offid+ " ");
			String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+offid),1);
			//String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+offid),1);
			String syear=Integer.toString(this.year);
			int apr_year_2=0;
			int sel_month=this.month;
			if (sel_month <=3 )
				apr_year_2=this.year-1;
			else
				apr_year_2=this.year;
			apr_year=apr_year_2; 
		    //apr_year=Integer.parseInt(obj.isNull(obj.getValue("PMS_DCB_SETTING", "FIN_YEAR","where OFFICE_ID="+offid),1));
			String tariff_count=obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "count(tariff_rate)"," where "+meter_status+" beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and  OFFICE_ID="+offid ),1);
			String metre_count=obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "count(metre_sno)"," where  "+meter_status+" beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and  OFFICE_ID="+offid ),1);
			if(Integer.parseInt(tariff_count)==Integer.parseInt(metre_count))
			{
				tariff_rate="Tariff Rate Entry Complete";
				tariff_img=t_img;
				msg_late1="Click";
			}
			else
			{
				tariff_rate="Tariff Rate Entry InComplete";
				tariff_img=c_img;
				msg_late1="<font color=red>Check</font>";
			}
			if (year==0)
			{
				monthval=obj.month_val(smonth);
				year=Integer.parseInt(syear);    
			}	
			else
			{
				year=this.year;
				smonth=Integer.toString(this.month);
				monthval=obj.month_val(smonth);
			}
			// status -> Process flag of pumping Return 
			String status=obj.isNull(obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "PROCESS_FLAG"," where beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and OFFICE_ID="+offid+" and MONTH="+smonth+" and YEAR="+syear ),1);
			// Total Meter Location 
		// old Code 	int total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where   "+meter_status+"  beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and  office_id="+offid);
		int	total_meter_location = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where  beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and   office_id="+offid+" and month="+smonth+" and year="+syear +"  ");
		// Total Pumping Count
 		int pumping_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and    office_id="+offid+" and month="+smonth+" and year="+syear);	
	 		if (status.trim().equals("FR") && total_meter_location == pumping_count && pumping_count!=0) {
	 			pump_rtrn="Pumping Return Entry Complete and Freezed";
	 			pump_img=t_img;
	 			msg_late3="<a href='data_not_submit.jsp?arg_year="+syear+"&arg_month="+smonth+"' target='_blank'> Click Pumping Return Process Status</a>";
	 		}
	 		else {  
	 			pump_rtrn="Pumping Return Omission Found or Not Validated";      
	 			pump_img=c_img;
	 			msg_late3="<a href='data_not_submit_ben.jsp?arg_year="+syear+"&arg_month="+smonth+"' target='_blank'><font color=red>Check Pumping Return Omission </font></a>";
	 		}
	 		/*if (Integer.parseInt(smonth)<=3)  
	 		{
	 			apr_year = Integer.parseInt(syear) - 1;  
	 		} 
	 		else
	 		{
	 			apr_year = Integer.parseInt(syear) ;
	 		}    
	 		*/      
	 		String ben_id=obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "BENEFICIARY_SNO"," where "+meter_status+" OFFICE_ID="+offid),1);
	 		String sch_id=obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE", "SCHEME_SNO"," where "+meter_status+" OFFICE_ID="+offid),1);
	 		ResultSet rs_loc=obj.getRS(" select  a.beneficiary_sno,b.scheme_sno from " +
	 				" PMS_DCB_MST_BENEFICIARY_METRE b,PMS_DCB_MST_BENEFICIARY a " +
	 				"  where a.status='L' and b.meter_status='L' and  a.beneficiary_sno=b.BENEFICIARY_SNO and a.OFFICE_ID=b.OFFICE_ID " +
	 				"  and a.office_id="+offid+" ORDER by a.beneficiary_sno");
	 		int y_count=0,dmdmonth=0;
	 		while (rs_loc.next())
	 		{
	 			ben_id=rs_loc.getString(1);
	 			sch_id=rs_loc.getString(2);
	 		}
	 		// y_count -> For April Opening Balance Count  
	 		// dmdmonth -> for Setting month Opening Balance count 
	 		// met_c ->  Total Meter Location For the Div 
	 		y_count=Integer.parseInt(obj.getValue("PMS_DCB_OB_YEARLY","sum(count(distinct beneficiary_sno))","ct", "where beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" office_id="+offid+") and office_id="+offid+" and  month=4 and fin_year="+apr_year+"  group by beneficiary_sno " ));
	 		//y_count=Integer.parseInt(obj.getValue("PMS_DCB_OB_YEARLY","sum(count(distinct beneficiary_sno))","ct", "where beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" office_id="+offid+") and office_id="+offid+" and  month=4 and fin_year="+apr_year+"  group by beneficiary_sno " ));
 			dmdmonth=Integer.parseInt(obj.getValue("PMS_DCB_OB_YEARLY","sum(count(distinct beneficiary_sno))","ct2","where    beneficiary_sno in (SELECT beneficiary_sno  FROM  PMS_DCB_MST_BENEFICIARY_METRE where  meter_STATUS ='L'  AND office_id="+offid+") and beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+") and office_id="+offid+" and  month="+smonth+" and fin_year="+syear+"  group by beneficiary_sno ") );
 			int met_c=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE","sum(count(distinct beneficiary_sno))","ct2","where  "+meter_status+" office_id="+offid+" and beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE "+new_cond+"   office_id="+offid+")    group by beneficiary_sno ") );
			String obmsg="";
	 		if (y_count==met_c && dmdmonth==met_c) 
	 			{ 
	 			obmsg="April "+apr_year+" Opening Balance <font color='green'  size=3>Complete</font> and "+monthval+","+year+" Opening Balance <font color='green' size=3>Complete</font>";
	 				ob_img=t_img;
	 				msg_late2="Click";
	 				obstatusflag=1; 
	 			}
	 		else if (y_count!=met_c &&  dmdmonth==met_c )
	 			{
	 				
	 				obmsg="April "+apr_year+" Opening Balance  <font color='red' size=3><b>Incomplete</b></font>  and "+monthval+","+year+" Opening Balance <font color='green' size=3><b>Complete</b></font>";
	 				ob_img=c_img;
	 				msg_late2="<font color=red>Check</font>";
	 				obstatusflag=2;
	 			}
	 		else if (y_count==met_c &&  dmdmonth!=met_c )
 			{
	 			String 	ob_missing_count=obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(distinct beneficiary_sno)","ct"," where BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" office_id="+this.offid+" ) and BENEFICIARY_SNO not in ( select BENEFICIARY_SNO from pms_dcb_ob_yearly where month="+this.month+" and  fin_year="+this.year+" and office_id="+this.offid+") and month="+this.month+" and year="+this.year+" and office_id="+this.offid+"  ");
 				System.out.println(y_count +"---" +ob_missing_count);
 				if (ob_missing_count.equalsIgnoreCase("0")) 	
 				{
 					obmsg="April "+apr_year+" Opening Balance  <font color='green' size=3><b>Complete</b></font>  and "+monthval+","+year+" Opening Balance  <font color='green' size=3><b>complete</b></font>";
 					ob_img=t_img;
 	 				msg_late2="<font color=green>Click</font>";
 		 			obstatusflag=1;
 				}
 				else   
 				{
 				obmsg="April "+apr_year+" Opening Balance  <font color='green' size=3><b>Complete</b></font>  and "+monthval+","+year+" Opening Balance  <font color='red' size=3><b>Incomplete</b></font>";	
	 			ob_img=c_img;
 				msg_late2="<font color=red>Check</font>";
	 			obstatusflag=2;
 				}
 			}else if (y_count!=met_c &&  dmdmonth!=met_c )
 			{
 	 			obmsg="April "+apr_year+" Opening Balance  <font color='red' size=3><b>Incomplete</b></font>  and "+monthval+","+year	+" Opening Balance <font color='red' size=3> <b>Incomplete</b></font>";
 	 			ob_img=c_img;
 	 			msg_late2="<font color=red>Check</font>";
 	 			obstatusflag=2;
 	 			}
	 		ob=obmsg;  
			//Water Charges 
	 		//old code : 10 10 2012  int ben_c=obj.getCount("PMS_DCB_MST_BENEFICIARY ", " where "+new_cond+" OFFICE_ID="+offid +" and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" office_id="+offid+") ");
	 		 int ben_c=obj.getCount("PMS_DCB_MST_BENEFICIARY ", " where "+new_cond+" OFFICE_ID="+offid +" and BENEFICIARY_SNO in ( select distinct BENEFICIARY_SNO from PMS_DCB_TRN_MONTHLY_PR where   MONTH="+smonth+" and  YEAR="+syear+" and  office_id="+offid+") ");	 		
	 		int wc_c=Integer.parseInt(obj.getValue("PMS_DCB_WC_BILLING" ,"count(distinct BENEFICIARY_SNO) ","c", "where OFFICE_ID="+offid +" and MONTH="+smonth+" and  YEAR="+syear));
	 		int pr_ben=Integer.parseInt(obj.getValue("PMS_DCB_TRN_MONTHLY_PR" ,"count(distinct BENEFICIARY_SNO) ","c", "where OFFICE_ID="+offid +" and MONTH="+smonth+" and  YEAR="+syear));   
	 		 if (  (status.trim().equals("FR") && total_meter_location == pumping_count)) 
 			{ 
	 			wc="Water Charges Calculation Completed For All Beneficiaries";
	 			wc_img=t_img;
	 			msg_late4="Click";  
 			}else  
 			{
 				wc="Water Charges Calculation Not Completed For All Beneficiaries";
	 			wc_img=c_img;
	 			msg_late4="<font color=red>Check</font>";
 			}
	 		//Demand notice generation
	 		 int demand_count=obj.getCount("PMS_DCB_TRN_BILL_DMD", " where office_id="+offid+" and bill_month="+smonth+" and bill_year="+year);
	 		 System.out.println("demand_count" + demand_count);
	 		 System.out.println("ben_c" + ben_c);
	 		 if(demand_count==ben_c && demand_count!=0)
	 		 {
	 			demand_generation="Demand Notice Generated for All Beneficiaries";
	 			demand_generation_img=t_img;
	 			msg_late5="Click";
	 		 }
	 		 else if(demand_count==0)
	 		 {
		 			demand_generation="Demand Notice Not Yet Generated for  "+monthval+" and "+year;
		 			demand_generation_img=c_img;
		 			msg_late5="<font color=red>Check</font>";
	 		 }
	 		 else
	 		 {
	 			demand_generation="Demand Notice Generated for Only "+demand_count+" Beneficiaries";
	 			demand_generation_img=c_img;
	 			msg_late5="<font color=red>Check</font>";
	 		 }
	 		con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return Office_Name;
	}
	public void setOffid(int offid) {
		this.offid = offid;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setMonth(int mv) {
		this.month = mv; 
	}
	public void setDmd_count(int dmd_count) {
		this.dmd_count = dmd_count;
	}
	public int getDmd_count() {
		return dmd_count;
	}
	public void setLedger_count(int ledger_count) {
		this.ledger_count = ledger_count;
	}
	public int getLedger_count() {
		return ledger_count;
	}
	public void setTotal_ben(int total_ben) {
		this.total_ben = total_ben;
	}
	public int getTotal_ben() {
		return total_ben;
	}
}
