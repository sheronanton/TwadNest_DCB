package Servlets.PMS.PMS1.DCB.Impl;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.http.HttpSession;
import Servlets.PMS.PMS1.DCB.Inter.Dcb_Common;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Common_Impl implements Dcb_Common 
{


	synchronized	public  String Div_RegionWise(String Region_id,String name) throws Exception 
	{
		Connection con=null;
		Controller cnt=new Controller();
		con=cnt.con();		 
		String combo=cnt.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID","WHERE REGION_OFFICE_ID="+Region_id+" and OFFICE_LEVEL_ID='DN' and OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP)",name,"");		
	    System.runFinalization();
	    System.gc();
	    con.close();
		return combo;
		
	} 	
	synchronized	public  String Div_RegionWise(String Region_id,String name,String inpdiv) throws Exception 
	{
		Connection con=null;  
		Controller cnt=new Controller();
		con=cnt.con();		 
		String combo="";
		if (!Region_id.equalsIgnoreCase("0")){
		combo=cnt.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID","WHERE REGION_OFFICE_ID="+Region_id+" and OFFICE_LEVEL_ID='DN' and OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP)",name," class=select ");
		System.out.println("ifcombo"+combo);}
		else
		combo=cnt.combo_str("com_mst_offices","OFFICE_NAME","OFFICE_ID","WHERE OFFICE_ID="+inpdiv+" and OFFICE_LEVEL_ID='DN' and OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP)",name," class=select ");
		System.out.println("elsecombo"+combo);
	    System.runFinalization();
	    System.gc();
	    con.close();
		return combo;  
		
	} 
	synchronized	public  String Div_RegionWise(String Region_id,String name,String inpdiv,String att) throws Exception 
	{
		Connection con=null;  
		Controller cnt=new Controller();
		con=cnt.con();		 
		String combo="";
		if (!Region_id.equalsIgnoreCase("0"))
		combo=cnt.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID","WHERE REGION_OFFICE_ID="+Region_id+"  and OFFICE_LEVEL_ID='DN' and OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP)",name,att);
		else
		combo=cnt.combo_str("com_mst_offices","OFFICE_NAME","OFFICE_ID","WHERE OFFICE_ID="+inpdiv+"  and  OFFICE_LEVEL_ID='DN' and OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP)",name,att);
		
	    System.runFinalization();
	    System.gc();
	    con.close();
		return combo;  
		
	}  
	public synchronized String regionId(String userid,Controller obj) throws Exception 
	{
			//String Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"') ") ;
		String Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			if (Office_id.equals("")) Office_id="0";		
			String Region_Id=obj.getValue("COM_MST_OFFICES","OFFICE_ID","where OFFICE_LEVEL_ID='RN' and  OFFICE_ID="+Office_id);
			return Region_Id;
	}
	public synchronized double sub_div_wise_pr(int office_id,int sub_div,int month,int year,Controller obj) throws Exception
	{ 
		double reading=0.0;
		String cond="select beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where office_id="+office_id+" and meter_status='L' and SUB_DIV_ID="+sub_div;
		String read=obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "SUM(QTY_CONSUMED_NET)", "WHERE MONTH="+month+" and office_id="+office_id+" and YEAR="+year+"   and SUBDIV_OFFICE_ID="+sub_div+" and beneficiary_sno in ("+cond+")");
		reading=Double.parseDouble(read); 	   
		return reading;	 
	}
	public  double schemewise_pr(int office_id,int schsno,int month,int year,Controller obj) throws Exception
	{  
		double reading=0.0;
		String cond="select beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where office_id="+office_id+" and meter_status='L' ";
		String read=obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "SUM(QTY_CONSUMED_NET)", "WHERE MONTH="+month+" and office_id="+office_id+" and YEAR="+year+" and SCH_SNO="+schsno+"  and beneficiary_sno in ("+cond+")");
		reading=Double.parseDouble(read);	 
		return reading;		
	}
}
