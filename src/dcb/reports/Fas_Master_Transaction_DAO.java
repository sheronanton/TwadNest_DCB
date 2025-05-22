package dcb.reports;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import  Servlets.PMS.PMS1.DCB.servlets.Controller;

public class Fas_Master_Transaction_DAO 
{
	private Connection con;
	private Controller obj_new;
	private Fas_Master_Transaction dao_obj;
	public Fas_Master_Transaction_DAO()
	{
		
	}
	public Fas_Master_Transaction actualValue(int month,int year,int office_id)
	{
		Class c1; 
		try
		{
			Class c=Class.forName("Servlets.PMS.PMS1.DCB.servlets.Controller");
			obj_new=(Controller)c.newInstance();
			con=obj_new.con();
			c1=Class.forName("dcb.reports.Fas_Master_Transaction");
			dao_obj=(Fas_Master_Transaction)c1.newInstance();
			
			String cond_master="  receipt_status='L' and cashbook_month="+month+" and SUB_LEDGER_TYPE_CODE=14 and cashbook_year="+year+" and  accounting_for_office_id="+office_id+"  ";
			String cond_trans="  SUB_LEDGER_TYPE_CODE=10 and cashbook_month="+month+" and cashbook_year="+year+"  and accounting_for_office_id="+office_id+"  "; 
			String mas_res=obj_new.valueSum("FAS_RECEIPT_MASTER", "TOTAL_AMOUNT", "mas_collection", cond_master, "1");
			String trans_res=obj_new.valueSum("FAS_RECEIPT_TRANSACTION", "AMOUNT", "trns_collection", cond_trans+" and receipt_no in (select receipt_no from FAS_RECEIPT_MASTER where "+cond_master+")  ", "1");
			
			dao_obj.setMaster_amount(mas_res);
			dao_obj.setTransaction_amount(trans_res); 
		}catch(Exception e)
		{     
			
		}   
		return dao_obj;
		
		
	}
	
	
}
