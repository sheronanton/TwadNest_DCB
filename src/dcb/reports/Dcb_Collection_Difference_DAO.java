package dcb.reports;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Servlets.ASSET.ASSET1.ASSETM.servlets.Controller;
public class Dcb_Collection_Difference_DAO 
{
	
	private Connection con_new;
	private  Controller obj_new;
	
	public Dcb_Collection_Difference_DAO()
	{
		obj_new=new Controller();
		try {
			setCon_new();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void  objectRemove() throws Throwable
	{
		obj_new=null;
		finalize();
	}
	public void setCon_new() throws Exception 
	{
		con_new=obj_new.con();
	}
 
	public ArrayList<Dcb_Collection_Difference> getValues(int month,int year)
	{
		ArrayList<Dcb_Collection_Difference> avalue=new ArrayList<Dcb_Collection_Difference>();
	
		StringBuffer qry=new StringBuffer("");
		qry.append(" select sum(collection1-collection2) as  collection_differ_count ,ACCOUNTING_FOR_OFFICE_ID ,( select distinct a.office_name from com_mst_all_offices_view a where  a.office_id=ACCOUNTING_FOR_OFFICE_ID ) as oname from ");
		qry.append(" (  select cr.CASHBOOK_YEAR,cr.CASHBOOK_MONTH,cr.ACCOUNTING_FOR_OFFICE_ID,(decode(cramt,null,0,cramt)- decode(dramt,null,0,dramt)) as collection1," );
		qry.append("   decode(collection,null,0,collection) collection2 ");
		qry.append(" from ( SELECT ACCOUNTING_FOR_OFFICE_ID,SUM(amount1) as cramt ,CASHBOOK_YEAR,CASHBOOK_MONTH FROM FAS_HEAD_SLTYPE_CR_VIEW");
		qry.append("   WHERE achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where acc_type ='C' and collection_type in (6,7) ) ");
		qry.append("  and sltypecode in (14,10)   and CASHBOOK_YEAR ="+year+" and cashbook_month="+month+" group by ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR,CASHBOOK_MONTH ");
		qry.append("  order by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH )cr ");
		qry.append("  left outer join  ( ");
		qry.append("  SELECT ACCOUNTING_FOR_OFFICE_ID,SUM(amount1) as dramt FROM FAS_HEAD_SLTYPE_DR_VIEW ");
		qry.append("  WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 782401 and 782406 ) ");
		qry.append("  and CASHBOOK_YEAR ="+year+"    and cashbook_month="+month+" group by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH ");
		qry.append("  order by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH ");
		qry.append("  ) dr  ");		
		qry.append("  on cr.ACCOUNTING_FOR_OFFICE_ID=dr.ACCOUNTING_FOR_OFFICE_ID ");
		qry.append("  left outer join  ");
		qry.append("  ( ");
		qry.append("    select office_id,  sum(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)  as collection  from PMS_DCB_LEDGER_ACTUAL ");
		qry.append("    where  year="+year+"   and month="+month+"  group by office_id   order by office_id ");
		qry.append("  )dcb   ");
		qry.append("  on dcb.office_id=cr.ACCOUNTING_FOR_OFFICE_ID ");  
		qry.append("  ) group by ACCOUNTING_FOR_OFFICE_ID having  sum(collection1-collection2) > 0 ");    
		System.out.println(qry.toString());  
		try 
		{
			PreparedStatement ps=con_new.prepareStatement(qry.toString());
			ResultSet rs=ps.executeQuery();
			
			double collection=0.0;
			while(rs.next())
			{
				Dcb_Collection_Difference obj_=new Dcb_Collection_Difference();
				collection=0.0;
				collection=Double.parseDouble(obj_new.isNull(rs.getString(1), 1));
				
				obj_.setCollection(new DecimalFormat("0.00").format(collection));
				obj_.setOfficename(rs.getString(3));
				
				avalue.add(obj_); 
			}
		
			objectRemove();
		} catch (SQLException e) 
		{
		
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return avalue;
	}
}