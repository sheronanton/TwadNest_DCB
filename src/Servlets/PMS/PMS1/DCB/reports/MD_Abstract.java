package Servlets.PMS.PMS1.DCB.reports;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.lowagie.text.pdf.ArabicLigaturizer;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class MD_Abstract {
	private ArrayList type_wise;
    private Connection db_con;
    private ResultSet rs_new;
    private int schtype;
	private int month;
	private String  office_id;
	private int year;
	public String report_query;
	public void setYear(int year) {
		this.year = year;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}
	public ArrayList getType_wise()
	{   
		ArrayList temp=new ArrayList();
		Controller obj_new=new Controller();
		try
		{
			DecimalFormat df = new DecimalFormat("0.00");
			db_con=obj_new.con();  
			obj_new.createStatement(db_con);  
			String add_cond="";
			System.out.println("RTYpe"+ this.schtype);
			if (this.schtype==1)
				add_cond="";// and BEN_TYPE_GROUP not in (8)";//   
			else
				add_cond="  ";   
			 
			int prv_month=obj_new.prv_month(this.year,this.month);
			int prv_year=obj_new.prv_year(this.year,this.month);
			String prv_mvalue=obj_new.month_val(Integer.toString(prv_month));
			String userquery="select  bty.BEN_TYPE_GROUP_NAME as BEN_TYPE_DESC_SUB," +
					"  round(  trunc((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13)  ,4),4) as ob,"+
					"  round(trunc(DMD_FOR_MTH_WC_10,4),4) as dmd,"  +
					"  round(trunc((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),4),4) as collection,"  +
					"  round(trunc(BALANCE_18,4),4) as balance,rpt.BEN_TYPE_GROUP  " +					
						" from ( SELECT  sum( DECODE(ac.OPENING_BAL_WC_8 ,NULL,0.0, ac.OPENING_BAL_WC_8))  AS OPENING_BAL_WC_8, "+
						" sum( DECODE(ac.DMD_UPTO_PRV_MNTH_WC_9 ,NULL,0.0, ac.DMD_UPTO_PRV_MNTH_WC_9))    AS DMD_UPTO_PRV_MNTH_WC_9, "+
						" sum( DECODE( ac.DMD_FOR_MTH_WC_10,NULL,0.0,ac.DMD_FOR_MTH_WC_10)) AS DMD_FOR_MTH_WC_10,   "+
						" sum( DECODE( ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12,NULL,0.0,ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12)) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
						" sum( DECODE( ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13,NULL,0.0,ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13)) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
						" sum( DECODE( ac.COLN_FOR_MTH_YES_YR_WC_14,NULL,0.0,ac.COLN_FOR_MTH_YES_YR_WC_14))     AS COLN_FOR_MTH_YES_YR_WC_14, "+
						" sum( DECODE( ac.COLN_FOR_MTH_WC_15,NULL,0.0,ac.COLN_FOR_MTH_WC_15))  AS COLN_FOR_MTH_WC_15, "+
						" sum( DECODE( ac.BALANCE_18 ,NULL,0.0,ac.BALANCE_18))  AS BALANCE_18,     "+
						" ac.MONTH   AS MONTH,ac.YEAR AS YEAR, ac.SCH_TYPE_ID_SUB ,ty.SCH_TYPE_SUB_DESC  ,BEN_TYPE_GROUP "+
						" FROM  (  SELECT DECODE(SUM(OPENING_BAL_WC),NULL,0.0,SUM(OPENING_BAL_WC)) / 100000 AS OPENING_BAL_WC_8, "+
						" DECODE(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC))   / 100000 AS DMD_UPTO_PRV_MNTH_WC_9, "+
						" DECODE(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC)) / 100000 AS DMD_FOR_MTH_WC_10, "+
						" DECODE(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) / 100000 AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
						" DECODE(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) / 100000 AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
						" DECODE(SUM(COLN_FOR_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_FOR_MTH_YES_YR_WC))     / 100000 AS COLN_FOR_MTH_YES_YR_WC_14, "+
						" DECODE(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES))  / 100000 AS COLN_FOR_MTH_WC_15, "+
						" DECODE(SUM(TOT_COLN_FOR_YR_DMD),NULL,0.0,SUM(TOT_COLN_FOR_YR_DMD))  / 100000 AS TOT_COLN_FOR_YR_DMD_17, "+
						" DECODE(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))   / 100000 AS BALANCE_18,  "+
						" SCH_TYPE_ID_SUB, BEN_TYPE_GROUP,BEN_TYPE_DESC_SUB, MONTH, YEAR "+
						" FROM PMS_DCB_LEDGER_ACTUAL  WHERE "+   
						" YEAR="+this.year+" "+add_cond+" "+office_id+" AND MONTH ="+this.month+" and SCH_TYPE_ID_SUB="+this.schtype+" GROUP BY YEAR,MONTH,SCH_TYPE_ID_SUB,BEN_TYPE_GROUP  ,BEN_TYPE_DESC_SUB "+
						" )ac JOIN  ( SELECT DISTINCT SCH_TYPE_ID_SUB,SCH_TYPE_SUB_DESC  FROM PMS_DCB_APPLICABLE_SCH_TYPE  ORDER BY SCH_TYPE_ID_SUB  )ty ON ty.SCH_TYPE_ID_SUB=ac.SCH_TYPE_ID_SUB "+ 
						" group by YEAR,MONTH,ac.SCH_TYPE_ID_SUB , ty.SCH_TYPE_SUB_DESC,ac.BEN_TYPE_GROUP "+
						" )rpt  JOIN  (   SELECT DISTINCT  BEN_TYPE_GROUP ,BEN_TYPE_GROUP_NAME  FROM PMS_DCB_BEN_TYPE  ORDER BY BEN_TYPE_GROUP   )bty "+
						" ON bty.BEN_TYPE_GROUP=rpt.BEN_TYPE_GROUP  order by BEN_TYPE_GROUP"; 
			  
			/*userquery=" SELECT  BEN_TYPE_GROUP_NAME as BEN_TYPE_DESC_SUB,rpt_prv.BEN_TYPE_GROUP,ob,dmd,collection,(ob+dmd)-collection AS balance "+ 
					  " from ( "+
					  " SELECT sum(DMD_FOR_MTH_WC_10) as dmd,((sum(OPENING_BAL_WC_8)+sum(DMD_UPTO_PRV_MNTH_WC_9))-(sum(COLN_UPTO_PRV_MTH_YES_YR_WC_12)+sum(COLN_UPTO_PRV_MTH_CR_YR_DMD_13))) as ob, "+
					  " MONTH,YEAR ,SCH_TYPE_ID_SUB,BEN_TYPE_GROUP FROM PMS_DCB_NEW_REVIEW_DMD  "+
			              " WHERE  MONTH="+prv_month+" AND YEAR="+prv_year+" "+" "+add_cond+" "+office_id+" and SCH_TYPE_ID_SUB="+this.schtype+" GROUP BY YEAR,MONTH,SCH_TYPE_ID_SUB,BEN_TYPE_GROUP  ORDER BY SCH_TYPE_ID_SUB "+ 
			          " )rpt_prv "+    
			          " join "+ 
			          " ( "+
			    		   " SELECT  YEAR,MONTH,SUM(COLN_FOR_MTH_YES_YR_WC_14) + SUM(COLN_FOR_MTH_WC_15) as  collection, SCH_TYPE_ID_SUB,BEN_TYPE_GROUP from PMS_DCB_NEW_REVIEW_coll "+ 
			          " WHERE MONTH ="+this.month+" and year="+this.year+" "+add_cond+" "+office_id+" and  SCH_TYPE_ID_SUB="+this.schtype+                                   
			          " GROUP BY YEAR,MONTH,SCH_TYPE_ID_SUB,BEN_TYPE_GROUP "+
			          " )rpt_coll "+
			          " ON rpt_prv.BEN_TYPE_GROUP=rpt_coll.BEN_TYPE_GROUP and rpt_prv.SCH_TYPE_ID_SUB=rpt_coll.SCH_TYPE_ID_SUB "+ 
			          " JOIN ( SELECT DISTINCT  BEN_TYPE_GROUP ,BEN_TYPE_GROUP_NAME  FROM PMS_DCB_BEN_TYPE  ORDER BY BEN_TYPE_GROUP   )bty "+
			          " ON bty.BEN_TYPE_GROUP=rpt_prv.BEN_TYPE_GROUP  order by BEN_TYPE_GROUP "; 
			
						System.out.println(userquery); */
			
			
			
			report_query=userquery;
			  double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
		  int i=0;
			ResultSet rs=obj_new.getRS(userquery);
			while(rs.next())
			{  
				String BEN_TYPE_GROUP=rs.getString("BEN_TYPE_GROUP");
				i++;
				String ben_type=rs.getString("BEN_TYPE_DESC_SUB");
				//if (BEN_TYPE_GROUP.equalsIgnoreCase("8"))   
				//	ben_type="Village Panchayat";  
				String ob=rs.getString("ob"); 
				String dmd=rs.getString("dmd");
				String collection=rs.getString("collection");    
				String balance=rs.getString("balance"); 
				tot1+=Double.parseDouble(ob);
				tot2+=Double.parseDouble(dmd);
				tot3+=Double.parseDouble(collection);
				tot4+=Double.parseDouble(balance);   
				if (i%2==1)
					temp.add("<tr><td align='right'>"+i+"</td>");   
					else
					temp.add("<tr bgcolor='#CCFFFF'><td align='right'>"+i+"</td>");
				temp.add("<td align='left'><a href='md_abstract_typewise.jsp?BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"&pmonth="+this.month+"&pyear="+this.year+"&schtype="+this.schtype+"'>"+ben_type+"</a></td>");
				temp.add("<td align='right'>"+df.format(Double.parseDouble(ob))+"</td>");
				temp.add("<td align='right'>"+df.format(Double.parseDouble(dmd))+"</td>");
				temp.add("<td align='right'>"+df.format(Double.parseDouble(collection))+"</td>");
				temp.add("<td align='right'>"+df.format(Double.parseDouble(balance))+"</td></tr>");   
			}
		 temp.add("<tr bgcolor='#E8E8E8'><td align='left' > </td>");
			temp.add("<td align='right'>Total </td>");
			temp.add("<td align='right'>"+df.format(tot1)+"</td>");
			temp.add("<td align='right'>"+df.format(tot2)+"</td>");
			temp.add("<td align='right'>"+df.format(tot3)+"</td>");
			temp.add("<td align='right'>"+df.format(tot4)+"</td></tr>");   
			db_con.close(); 
		}catch(Exception e)      
		{
			e.printStackTrace();
		}
		return temp;    
	}public ArrayList getType_wise_new()
	{   
		ArrayList temp=new ArrayList();
		Controller obj_new=new Controller();
		try
		{
			DecimalFormat df = new DecimalFormat("0.00");
			db_con=obj_new.con();  
			obj_new.createStatement(db_con); 
			String add_cond="";
			System.out.println("RTYpe"+ this.schtype);
			if (this.schtype==1)
				add_cond="";// and BEN_TYPE_GROUP not in (8)";//   
			else
				add_cond="  ";   
			 
			int prv_month=obj_new.prv_month(this.year,this.month);
			int prv_year=obj_new.prv_year(this.year,this.month);
			String prv_mvalue=obj_new.month_val(Integer.toString(prv_month));
			String userquery="select  bty.BEN_TYPE_GROUP_NAME as BEN_TYPE_DESC_SUB," +
					"  round(  trunc((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13)  ,3),2) as ob,"+
					"  round(trunc(DMD_FOR_MTH_WC_10,3),2) as dmd,"  +
					"  round(trunc((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),3),2) as collection,"  +
					"  round(trunc(BALANCE_18,3),2) as balance,rpt.BEN_TYPE_GROUP  " +					
						" from ( SELECT  sum( DECODE(ac.OPENING_BAL_WC_8 ,NULL,0.0, ac.OPENING_BAL_WC_8))  AS OPENING_BAL_WC_8, "+
						" sum( DECODE(ac.DMD_UPTO_PRV_MNTH_WC_9 ,NULL,0.0, ac.DMD_UPTO_PRV_MNTH_WC_9))    AS DMD_UPTO_PRV_MNTH_WC_9, "+
						" sum( DECODE( ac.DMD_FOR_MTH_WC_10,NULL,0.0,ac.DMD_FOR_MTH_WC_10)) AS DMD_FOR_MTH_WC_10,   "+
						" sum( DECODE( ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12,NULL,0.0,ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12)) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
						" sum( DECODE( ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13,NULL,0.0,ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13)) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
						" sum( DECODE( ac.COLN_FOR_MTH_YES_YR_WC_14,NULL,0.0,ac.COLN_FOR_MTH_YES_YR_WC_14))     AS COLN_FOR_MTH_YES_YR_WC_14, "+
						" sum( DECODE( ac.COLN_FOR_MTH_WC_15,NULL,0.0,ac.COLN_FOR_MTH_WC_15))  AS COLN_FOR_MTH_WC_15, "+
						" sum( DECODE( ac.BALANCE_18 ,NULL,0.0,ac.BALANCE_18))  AS BALANCE_18,     "+
						" ac.MONTH   AS MONTH,ac.YEAR AS YEAR, ac.SCH_TYPE_ID_SUB ,ty.SCH_TYPE_SUB_DESC  ,BEN_TYPE_GROUP "+
						" FROM  (  SELECT DECODE(SUM(OPENING_BAL_WC),NULL,0.0,SUM(OPENING_BAL_WC)) / 100000 AS OPENING_BAL_WC_8, "+
						" DECODE(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC))   / 100000 AS DMD_UPTO_PRV_MNTH_WC_9, "+
						" DECODE(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC)) / 100000 AS DMD_FOR_MTH_WC_10, "+
						" DECODE(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) / 100000 AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
						" DECODE(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) / 100000 AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
						" DECODE(SUM(COLN_FOR_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_FOR_MTH_YES_YR_WC))     / 100000 AS COLN_FOR_MTH_YES_YR_WC_14, "+
						" DECODE(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES))  / 100000 AS COLN_FOR_MTH_WC_15, "+
						" DECODE(SUM(TOT_COLN_FOR_YR_DMD),NULL,0.0,SUM(TOT_COLN_FOR_YR_DMD))  / 100000 AS TOT_COLN_FOR_YR_DMD_17, "+
						" DECODE(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))   / 100000 AS BALANCE_18,  "+
						" SCH_TYPE_ID_SUB, BEN_TYPE_GROUP,BEN_TYPE_DESC_SUB, MONTH, YEAR "+
						" FROM PMS_DCB_LEDGER_ACTUAL  WHERE "+   
						" YEAR="+this.year+" "+add_cond+" "+office_id+" AND MONTH ="+this.month+" and SCH_TYPE_ID_SUB="+this.schtype+" GROUP BY YEAR,MONTH,SCH_TYPE_ID_SUB,BEN_TYPE_GROUP  ,BEN_TYPE_DESC_SUB "+
						" )ac JOIN  ( SELECT DISTINCT SCH_TYPE_ID_SUB,SCH_TYPE_SUB_DESC  FROM PMS_DCB_APPLICABLE_SCH_TYPE  ORDER BY SCH_TYPE_ID_SUB  )ty ON ty.SCH_TYPE_ID_SUB=ac.SCH_TYPE_ID_SUB "+ 
						" group by YEAR,MONTH,ac.SCH_TYPE_ID_SUB , ty.SCH_TYPE_SUB_DESC,ac.BEN_TYPE_GROUP "+
						" )rpt  JOIN  (   SELECT DISTINCT  BEN_TYPE_GROUP ,BEN_TYPE_GROUP_NAME  FROM PMS_DCB_BEN_TYPE  ORDER BY BEN_TYPE_GROUP   )bty "+
						" ON bty.BEN_TYPE_GROUP=rpt.BEN_TYPE_GROUP  order by BEN_TYPE_GROUP"; 
			  
			userquery=" SELECT  BEN_TYPE_GROUP_NAME as BEN_TYPE_DESC_SUB,rpt_prv.BEN_TYPE_GROUP,ob,dmd,collection,(ob+dmd)-collection AS balance "+ 
					  " from ( "+
					  " SELECT sum(DMD_FOR_MTH_WC_10) as dmd,((sum(OPENING_BAL_WC_8)+sum(DMD_UPTO_PRV_MNTH_WC_9))-(sum(COLN_UPTO_PRV_MTH_YES_YR_WC_12)+sum(COLN_UPTO_PRV_MTH_CR_YR_DMD_13))) as ob, "+
					  " MONTH,YEAR ,SCH_TYPE_ID_SUB,BEN_TYPE_GROUP FROM PMS_DCB_NEW_REVIEW_DMD  "+
			              " WHERE  MONTH="+prv_month+" AND YEAR="+prv_year+" "+" "+add_cond+" "+office_id+" and SCH_TYPE_ID_SUB="+this.schtype+" GROUP BY YEAR,MONTH,SCH_TYPE_ID_SUB,BEN_TYPE_GROUP  ORDER BY SCH_TYPE_ID_SUB "+ 
			          " )rpt_prv "+    
			          " join "+ 
			          " ( "+
			    		   " SELECT  YEAR,MONTH,SUM(COLN_FOR_MTH_YES_YR_WC_14) + SUM(COLN_FOR_MTH_WC_15) as  collection, SCH_TYPE_ID_SUB,BEN_TYPE_GROUP from PMS_DCB_NEW_REVIEW_coll "+ 
			          " WHERE MONTH ="+this.month+" and year="+this.year+" "+add_cond+" "+office_id+" and  SCH_TYPE_ID_SUB="+this.schtype+                                   
			          " GROUP BY YEAR,MONTH,SCH_TYPE_ID_SUB,BEN_TYPE_GROUP "+
			          " )rpt_coll "+
			          " ON rpt_prv.BEN_TYPE_GROUP=rpt_coll.BEN_TYPE_GROUP and rpt_prv.SCH_TYPE_ID_SUB=rpt_coll.SCH_TYPE_ID_SUB "+ 
			          " JOIN ( SELECT DISTINCT  BEN_TYPE_GROUP ,BEN_TYPE_GROUP_NAME  FROM PMS_DCB_BEN_TYPE  ORDER BY BEN_TYPE_GROUP   )bty "+
			          " ON bty.BEN_TYPE_GROUP=rpt_prv.BEN_TYPE_GROUP  order by BEN_TYPE_GROUP "; 
			
						System.out.println(userquery); 
			
			
			
			report_query=userquery;
			  double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
		  int i=0;
			ResultSet rs=obj_new.getRS(userquery);
			while(rs.next())
			{  
				String BEN_TYPE_GROUP=rs.getString("BEN_TYPE_GROUP");
				i++;
				String ben_type=rs.getString("BEN_TYPE_DESC_SUB");
				//if (BEN_TYPE_GROUP.equalsIgnoreCase("8"))   
				//	ben_type="Village Panchayat";  
				String ob=rs.getString("ob"); 
				String dmd=rs.getString("dmd");
				String collection=rs.getString("collection");    
				String balance=rs.getString("balance"); 
				tot1+=Double.parseDouble(ob);
				tot2+=Double.parseDouble(dmd);
				tot3+=Double.parseDouble(collection);
				tot4+=Double.parseDouble(balance);   
				if (i%2==1)
					temp.add("<tr><td align='right'>"+i+"</td>");   
					else
					temp.add("<tr bgcolor='#CCFFFF'><td align='right'>"+i+"</td>");
				temp.add("<td align='left'><a href='md_abstract_typewise_rev.jsp?BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"&pmonth="+this.month+"&pyear="+this.year+"&schtype="+this.schtype+"'>"+ben_type+"</a></td>");
				temp.add("<td align='right'>"+df.format(Double.parseDouble(ob))+"</td>");
				temp.add("<td align='right'>"+df.format(Double.parseDouble(dmd))+"</td>");
				temp.add("<td align='right'>"+df.format(Double.parseDouble(collection))+"</td>");
				temp.add("<td align='right'>"+df.format(Double.parseDouble(balance))+"</td></tr>");   
			}
		 temp.add("<tr bgcolor='#E8E8E8'><td align='left' > </td>");
			temp.add("<td align='right'>Total </td>");
			temp.add("<td align='right'>"+df.format(tot1)+"</td>");
			temp.add("<td align='right'>"+df.format(tot2)+"</td>");
			temp.add("<td align='right'>"+df.format(tot3)+"</td>");
			temp.add("<td align='right'>"+df.format(tot4)+"</td></tr>");   
			db_con.close(); 
		}catch(Exception e)      
		{
			e.printStackTrace();
		}
		return temp;    
	}
	public void setDb_con(Connection db_con) {
		this.db_con = db_con;
	}
	public void setSchtype(int schtype) {
		this.schtype = schtype;
	}
	public String getReport_query() {
		return report_query;
	}
}
