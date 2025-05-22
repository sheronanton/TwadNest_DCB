package Servlets.PMS.PMS1.DCB.reports;
import java.sql.ResultSet;
import java.util.ArrayList;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class Scheme_Desi_Pump_Supp 
{
	private int promonth;
	private int proyear;
	private int days;  
	private int prooffice_id;
	public void setPromonth(int promonth) {
		this.promonth = promonth;
	}
	public void setProyear(int proyear) {
		this.proyear = proyear;
	}  
	public void setDays(int days) {
		this.days = days;
	}
	public void setProoffice_id(int prooffice_id) {
		this.prooffice_id = prooffice_id;
	}
	 public ArrayList Result(Controller obj)
	 {
		 ArrayList ar=new ArrayList();  
		 String qry="SELECT sch.SCH_SNO,sch.SCH_NAME AS SCH_NAME,smap.QTY_CONSUMED_NET AS QTY_CONSUMED_NET,ta.tot_QTY_CONSUMED_NET AS tot_QTY_CONSUMED_NET,c.OFFICE_NAME,design.QTY_DESIGN * "+this.days+" as DESIGN,pumped.PUMPING_QTY * "+this.days+" as pumped,"+
		 		"( design.QTY_DESIGN *"+this.days+" - smap.QTY_CONSUMED_NET) as diff1,( pumped.PUMPING_QTY * "+this.days+"- smap.QTY_CONSUMED_NET) as diff2 "+
		 		" FROM  (SELECT  OFFICE_ID,SCH_SNO,sum(QTY_CONSUMED_NET)/1000 as QTY_CONSUMED_NET FROM PMS_DCB_TRN_MONTHLY_PR WHERE office_id="+this.prooffice_id+" and MONTH = "+this.promonth+""+ 
		 		" AND YEAR="+this.proyear+"   GROUP BY OFFICE_ID,SCH_SNO )smap JOIN (SELECT DISTINCT SCH_SNO,office_id,SCH_NAME FROM PMS_SCH_MASTER WHERE SCH_SNO IN (SELECT DISTINCT SCHEME_SNO FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id="+this.prooffice_id+" ) "+
		 		" )sch ON sch.SCH_SNO=smap.SCH_SNO left JOIN  "+
		 		" (SELECT office_id,SCH_SNO,SUM(QTY_CONSUMED_NET) AS tot_QTY_CONSUMED_NET FROM PMS_DCB_TRN_MONTHLY_PR WHERE MONTH="+this.promonth+" AND YEAR="+this.proyear+" GROUP BY office_id, SCH_SNO  "+
		 		" )ta left JOIN (SELECT office_id,SCH_SNO,QTY_DESIGN  AS QTY_DESIGN FROM PMS_AME_MST_SCH_DETAILS )design "+
		 		" ON design.SCH_SNO   =ta.SCH_SNO AND design.office_id=ta.office_id left JOIN  "+
		 		" (SELECT office_id,SCH_SNO,PUMPING_QTY  AS PUMPING_QTY FROM PMS_AME_TRN_SCH_DP_QTY  WHERE MONTH ="+this.promonth+"  AND YEAR="+this.proyear+""+  
		 		" )pumped  ON pumped.SCH_SNO=ta.SCH_SNO AND pumped.office_id=ta.office_id ON sch.SCH_SNO=ta.SCH_SNO and sch.office_id=ta.office_id  JOIN  "+
		 		" ( SELECT OFFICE_ID, OFFICE_NAME FROM COM_MST_OFFICES    )c ON c.OFFICE_ID=smap.OFFICE_ID ORDER BY SCH_NAME";
		 try
		 {
			String SCH_NAME="",DESIGN="",pumped="",QTY_CONSUMED_NET="";
			ResultSet rs=obj.getRS(qry);
			while (rs.next())
			{
				SCH_NAME=obj.isNull(rs.getString("SCH_NAME"), 1);
				ar.add(SCH_NAME);
				DESIGN=obj.isNull(rs.getString("DESIGN"), 1);
				ar.add(DESIGN);
				pumped=obj.isNull(rs.getString("pumped"), 1);
				ar.add(pumped);
				QTY_CONSUMED_NET=obj.isNull(rs.getString("QTY_CONSUMED_NET"), 1);
				ar.add(QTY_CONSUMED_NET);
			}
		 } catch (Exception e) 
		 {
			e.printStackTrace();
		}
		 return ar;
	 }
}
