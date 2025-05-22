package Servlets.AME.AME1.AMEM.servlets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pms_Ame_Item_Breakup 
{
	private int sch_type;
	private int acgroup;
	private int year;
	private int pmonth;
	private int pmonth2;
	private int schsno;
	public void setYear(int year) {
		this.year = year;
	}
	private Controller obj_new=null;
	private Connection con_new=null;
	private Double amt_net=0.0;
	public String getFin_year() {
		return fin_year;
	}

	public void setSch_type(int sch_type) {
		this.sch_type = sch_type;
	}


	public void setOffice_id(int office_id) {
		this.office_id = office_id;
	}
	public void setAcgroup(int acgroup) {
		this.acgroup = acgroup;
	}

	private int office_id;
	private String fin_year;
	
	
	public Pms_Ame_Item_Breakup()
	{
		
		try
		{
			obj_new=new Controller();
			con_new=obj_new.con();
		}catch(Exception e) 
		{
			System.out.println("PMS---->AME---->Pms_Ame_Item_Breakup(40)"+e.getMessage());
		}
	}
	public void con_close()
	{
		try
		{
			con_new.close();
			obj_new=null;
			con_new=null;
		}catch(Exception e) 
		{
			System.out.println("PMS---->AME---->Pms_Ame_Item_Breakup(54)"+e.getMessage());
			obj_new=null;
			con_new=null;
		}
		
	}


	public Double getAmt_net()
	{
		StringBuffer br=new StringBuffer("");
			br.append(" SELECT sum(a.bill_amt) ");
			br.append(" FROM PMS_AME_EXP_ITEM_BREAKUP a  ");
			br.append(" where sch_sno="+schsno+"  and office_id="+office_id+" and exists( select distinct account_head_code, acc_group_sno,sch_type_id  FROM PMS_AME_MST_ACC_HEAD_MAP where "); 
			br.append(" account_head_code=a.account_head_code and sch_type_id="+sch_type+" and acc_group_sno in ("+acgroup+")) ");
			br.append(" and to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year+"','dd-MM-yyyy')  "); 
			br.append(" AND to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+(year+1)+"','dd-MM-yyyy') ");
			 
			PreparedStatement ps_new=null;
			try {
				ps_new=con_new.prepareStatement(br.toString());
				ResultSet rs_new=ps_new.executeQuery();				
				if (rs_new.next())
				{
					amt_net=Double.parseDouble(obj_new.isNull(rs_new.getString(1),1));
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return amt_net;
	}
	public Double getAmt_net_off( ) 
	{
		StringBuffer br=new StringBuffer("");
			br.append(" SELECT sum(a.bill_amt) ");
			br.append(" FROM PMS_AME_EXP_ITEM_BREAKUP a  ");
			br.append(" where office_id="+office_id+" and exists( select distinct account_head_code, acc_group_sno,sch_type_id  FROM PMS_AME_MST_ACC_HEAD_MAP where "); 
			br.append(" account_head_code=a.account_head_code and  acc_group_sno in ("+acgroup+")) ");
			br.append(" and to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year+"','dd-MM-yyyy')  "); 
			br.append(" AND to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+(year+1)+"','dd-MM-yyyy') ");
			 
			PreparedStatement ps_new=null; 
			try {
				ps_new=con_new.prepareStatement(br.toString());
				ResultSet rs_new=ps_new.executeQuery();				
				if (rs_new.next())
				{
					amt_net=Double.parseDouble(obj_new.isNull(rs_new.getString(1),1));
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return amt_net;
	}
	public Double getAmt_net_cir(int circle_office ) 
	{
		StringBuffer br=new StringBuffer("");
			br.append(" SELECT sum(a.bill_amt) ");
			br.append(" FROM PMS_AME_EXP_ITEM_BREAKUP a  ");
			br.append(" where office_id   in (select office_id from com_mst_all_offices_view where office_level_id='DN' and circle_office_id="+circle_office+")  and exists( select distinct account_head_code, acc_group_sno,sch_type_id  FROM PMS_AME_MST_ACC_HEAD_MAP where "); 
			br.append(" account_head_code=a.account_head_code and  acc_group_sno in ("+acgroup+")) ");
			br.append(" and to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year+"','dd-MM-yyyy')  "); 
			br.append(" AND to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+(year+1)+"','dd-MM-yyyy') ");
			 
			PreparedStatement ps_new=null;
			try {
				ps_new=con_new.prepareStatement(br.toString());
				ResultSet rs_new=ps_new.executeQuery();				
				if (rs_new.next())
				{
					amt_net=Double.parseDouble(obj_new.isNull(rs_new.getString(1),1));
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return amt_net;
	}public Double getAmt_net_reg(int region_office ) 
	{
		StringBuffer br=new StringBuffer("");
			br.append(" SELECT sum(a.bill_amt) ");
			br.append(" FROM PMS_AME_EXP_ITEM_BREAKUP a  ");
			br.append(" where office_id   in (select office_id from com_mst_all_offices_view where office_level_id='DN' and region_office_id="+region_office+")  and exists( select distinct account_head_code, acc_group_sno,sch_type_id  FROM PMS_AME_MST_ACC_HEAD_MAP where "); 
			br.append(" account_head_code=a.account_head_code and  acc_group_sno in ("+acgroup+")) ");
			br.append(" and to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year+"','dd-MM-yyyy')  "); 
			br.append(" AND to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+(year+1)+"','dd-MM-yyyy') ");
			
			 
			  
			PreparedStatement ps_new=null;
			try {
				ps_new=con_new.prepareStatement(br.toString());
				ResultSet rs_new=ps_new.executeQuery();				
				if (rs_new.next())
				{
					amt_net=Double.parseDouble(obj_new.isNull(rs_new.getString(1),1));
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return amt_net;  
	}  
	public String date_Display(int month1,int year1,int month2,int year2)
	{ 
		String edate=obj_new.month_val2(month2, year2);
		String val="(1-"+month1+"-"+year1+" to "+edate+"-"+month2+"-"+(year2)+")";
		return val;
	}
	public Double getAmt_net_regprv(int check_office,int month1,int year1,int month2,int year2,int flag ) 
	{
		
			String edate=obj_new.month_val2(month2, year2);
			StringBuffer br=new StringBuffer("");
			br.append(" SELECT sum(a.bill_amt) ");
			br.append(" FROM PMS_AME_EXP_ITEM_BREAKUP a  ");
			if (flag==1)
			br.append(" where office_id   in (select office_id from com_mst_all_offices_view where office_level_id='DN' and region_office_id="+check_office+")  and exists( select distinct account_head_code, acc_group_sno,sch_type_id  FROM PMS_AME_MST_ACC_HEAD_MAP where ");
			else if (flag==2)
			br.append(" where office_id   in (select office_id from com_mst_all_offices_view where office_level_id='DN' and circle_office_id="+check_office+")  and exists( select distinct account_head_code, acc_group_sno,sch_type_id  FROM PMS_AME_MST_ACC_HEAD_MAP where ");
			else if (flag==3)
			br.append(" where office_id="+office_id+" and exists( select distinct account_head_code, acc_group_sno,sch_type_id  FROM PMS_AME_MST_ACC_HEAD_MAP where ");
			else if (flag==4)
			br.append(" where sch_sno="+schsno+"  and office_id="+office_id+" and exists( select distinct account_head_code, acc_group_sno,sch_type_id  FROM PMS_AME_MST_ACC_HEAD_MAP where "); 

			br.append(" account_head_code=a.account_head_code and  acc_group_sno in ("+acgroup+")) ");
			br.append(" and to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-"+month1+"-"+year1+"','dd-MM-yyyy')  "); 
			br.append(" AND to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('"+edate+"-"+month2+"-"+(year2)+"','dd-MM-yyyy') ");
			   
			PreparedStatement ps_new=null;
			try {
				ps_new=con_new.prepareStatement(br.toString());
				ResultSet rs_new=ps_new.executeQuery();				
				if (rs_new.next())
				{
					amt_net=Double.parseDouble(obj_new.isNull(rs_new.getString(1),1));
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
		return amt_net;
	}
	public void setSchsno(int schsno) {
		this.schsno = schsno;
	}


	public void setPmonth(int pmonth) {
		this.pmonth = pmonth;
	}


	 

 
}
