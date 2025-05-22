package Servlets.AME.AME1.AMEM.servlets;

import java.sql.Connection;

public class AM_Items {

	private int group_sno;
	private int main_sno;
	private int sub_sno;
	private int sch_sno;
	private double amount;
	
	public AM_Items()
	{
		
	}
	public int getGroup_sno() {
		return group_sno;
	}
	public void setGroup_sno(int group_sno) {
		this.group_sno = group_sno;
	}
	public int getMain_sno() {
		return main_sno;
	}
	public void setMain_sno(int main_sno) {
		this.main_sno = main_sno;
	}
	public int getSub_sno() {
		return sub_sno;
	}
	public void setSub_sno(int sub_sno) {
		this.sub_sno = sub_sno;
	}
	public double getAmount() 
	{
	 try
	 {
		Controller obj=new Controller();
		Connection con=obj.con();
		obj.createStatement(con);
		amount=Double.parseDouble(obj.getValue("PMS_AME_TRN_ABSTRACT", "AM_EST_AMT", " where sch_sno="+sch_sno+"  and OFFICE_ID=5984 and MAIN_ITEM_SNO="+main_sno+" AND  SUB_ITEM_SNO ="+sub_sno+" FIN_YEAR   ='2015-2016'"));
	 }catch(Exception e ) {}
		return amount;
	}
	public void setSch_sno(int sch_sno) {
		this.sch_sno = sch_sno;
	}
	 
	 
}
