/* 
  * Created on : dd-mm-yy 
  * Author     : sathya
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description
  * 17/09/2011		Add the Beneficiary Status to 'L'  
  * 20/09/2011		Add the Meter Status to 'L'
  *  08/11/2011     Changes in upload qry (refer #UP1 )
  *---------|---------------|--------------------------------------------------- 
  */

package Servlets.PMS.PMS1.DCB.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class pms_dcb_ledger_actual_backup extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	String new_cond=Controller.new_cond; 
    public pms_dcb_ledger_actual_backup() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps=null;
        ResultSet res=null;
        int month_var=0;
        int year_var=0;
        int countvalue=0;
        String command_var="";
        String xmlvariable="";
        int countval=0;
        String contqry=null;
        String contqry_1=null;
        String divisionid="0";
        Controller obj=null,obj2=null;
        try 
 		 {
                obj = new Controller();
                obj2= new Controller();
                connection=obj.con();
                obj2.createStatement(connection);
                obj.createStatement(connection);
          } catch (Exception ex)
          {
              String connectMsg ="Could not create the connection" + ex.getMessage() + " " +
                  ex.getLocalizedMessage();
          }
          
          command_var=request.getParameter("TEST");
          try
          {
              command_var=  request.getParameter("command");
            
          }
          catch(Exception e)
          {
              System.out.println("Error in reteriving the command");
          }
          try
          {
        	  month_var=Integer.parseInt(request.getParameter("month_var"));
          }
          catch(Exception e)
          {
              System.out.println("Error in reteriving the month_var");
          }
          try
          {
        	  year_var=Integer.parseInt(request.getParameter("year_var"));
             
          }
          catch(Exception e)
          {
              System.out.println("Error in reteriving the year_var");
          }
          try
          {
          }
          catch(Exception e)
          {
              System.out.println("Error in reteriving the radiovalue");
          }
          try
          {
        	  divisionid=request.getParameter("divname");
          }
          catch(Exception e) 
          {
        	  divisionid="0"; 
          }
  			obj.testQry("DCB------->pms_dcb_ledger_actual---->command( "+command_var+" )  ");
          if(divisionid.equals("0"))
          {
        	  contqry=""; 
        	  contqry_1="";
          }
          else 
          {
        	  contqry=" and office_id="+divisionid;
        	  contqry_1=" where office_id="+divisionid;
          }
          if (command_var.equalsIgnoreCase("generate"))
          {
        	     
        	  xmlvariable = "<response>";
              xmlvariable += "<command>generate</command>";
              int month_process =0;
              int year_process=0;
              if ( month_var==1)  
              {
            	  month_process=12;
            	  year_process=year_var-1;
            	  
              } 
              else
              {
            	  month_process=month_var-1;
            	  year_process=year_var;
              }
              System.out.println("Stage 1 - Start " + divisionid);
              try
              {
            	  String countqry="SELECT count(*) as countvalue FROM("+
    			  " SELECT pms_dcb_mst_beneficiary_five.office_id,"+
   			   " pms_dcb_mst_beneficiary_five.office_name,"+
   			    " ob_yearly_first.sch_sno,"+
   			    " pms_sch_master_six.sch_name,"+
   			    " pms_dcb_mst_beneficiary_five.ben_type_id,"+
   			    " pms_dcb_mst_beneficiary_five.ben_type_desc,"+
   			    " pms_dcb_mst_beneficiary_five.district_code,"+
   			    " pms_dcb_mst_beneficiary_five.district_name,"+
   			    " ob_yearly_first.beneficiary_sno,"+
   			    " pms_dcb_mst_beneficiary_five.beneficiary_name,"+
   			    " pms_sch_master_six.sch_type_id,"+
   			    " pms_sch_master_six.sch_type_desc,"+
   			   " MONTH,"+
   			    " cb_monthly_second.fin_year as YEAR,"+
   			    " nvl(pms_dcb_ob_yearly_report_third.opening_bal_mc,   0)  AS opening_bal_mc_3,"+
   			    " nvl(ob_yearly_first.addns_if_any,   0)   AS addns_if_any_3a,"+
   			    " nvl(ob_yearly_first.colln_upto_prev_month_mc,   0)  AS col_uto_prv_mnh_mc_4,"+
   			    " nvl(cb_monthly_second.colln_for_mth_maint,   0)  AS coll_for_mth_maint_5,"+
   			    " (nvl(ob_yearly_first.colln_upto_prev_month_mc,   0) + nvl(cb_monthly_second.colln_for_mth_maint,   0))   AS total_colln_6,"+
   			    " ((nvl(pms_dcb_ob_yearly_report_third.opening_bal_mc,   0) + nvl(ob_yearly_first.addns_if_any,   0)) -(nvl(ob_yearly_first.colln_upto_prev_month_mc,   0) +  nvl"+

   			  " (cb_monthly_second.colln_for_mth_maint,   0)))  AS balance_7,"+
   			    " nvl(pms_dcb_ob_yearly_report_third.opening_bal_wc,   0)  AS opening_bal_wc_8,"+
   			    " nvl(ob_yearly_first.demand_upto_prev_month_wc,   0)   AS dmd_upto_prv_mnth_wc_9,"+
   			    " nvl(cb_monthly_second.dmd_for_mth_wc,   0)   AS dmd_for_mth_wc_10,"+
   			    " (nvl(pms_dcb_ob_yearly_report_third.opening_bal_wc,   0) + nvl(ob_yearly_first.demand_upto_prev_month_wc,   0) + nvl"+

   			  " (cb_monthly_second.dmd_for_mth_wc,   0))  AS total_dues_11,"+
   			    " nvl(ob_yearly_first.coln_upto_prv_mth_yester_yr_wc,   0)  AS coln_upto_prv_mth_yes_yr_wc_12,"+
   			    " nvl(ob_yearly_first.coln_upto_prv_mth_cr_yr_dmd,   0)   AS coln_upto_prv_mth_cr_yr_dmd_13,"+
   			    " nvl(cb_monthly_second.coln_for_mth_yester_yr_wc,   0)   AS coln_for_mth_yes_yr_wc_14,"+
   			    " nvl(cb_monthly_second.coln_for_mth_wc,   0)   AS coln_for_mth_wc_15,"+
   			    " (nvl(ob_yearly_first.coln_upto_prv_mth_yester_yr_wc,   0) + nvl(cb_monthly_second.coln_for_mth_yester_yr_wc,   0))   AS  tot_coln_yes_yr_dmd_16,"+
   			    " (nvl(ob_yearly_first.coln_upto_prv_mth_cr_yr_dmd,   0) + nvl(cb_monthly_second.coln_for_mth_wc,   0))   AS tot_coln_for_yr_dmd_17,"+
   			    " (((nvl(pms_dcb_ob_yearly_report_third.opening_bal_wc,   0) + nvl(ob_yearly_first.demand_upto_prev_month_wc,   0) + nvl"+

   			  " (cb_monthly_second.dmd_for_mth_wc,   0))) -((nvl(ob_yearly_first.coln_upto_prv_mth_yester_yr_wc,   0) + nvl (cb_monthly_second.coln_for_mth_yester_yr_wc,  "+

   			   " 0))) -((nvl(ob_yearly_first.coln_upto_prv_mth_cr_yr_dmd,   0) + nvl(cb_monthly_second.coln_for_mth_wc,   0)))) AS balance_18,"+
   			    " (((nvl(pms_dcb_ob_yearly_report_third.opening_bal_mc,   0) + nvl(ob_yearly_first.addns_if_any,   0)) -(nvl(ob_yearly_first.colln_upto_prev_month_mc,   0) + "+

   			  " nvl(cb_monthly_second.colln_for_mth_maint,   0))) +((((nvl(pms_dcb_ob_yearly_report_third.opening_bal_wc,   0) + nvl"+

   			  " (ob_yearly_first.demand_upto_prev_month_wc,   0) + nvl(cb_monthly_second.dmd_for_mth_wc,   0))) -((nvl(ob_yearly_first.coln_upto_prv_mth_yester_yr_wc,   "+

   			  " 0) + nvl(cb_monthly_second.coln_for_mth_yester_yr_wc,   0))) -((nvl(ob_yearly_first.coln_upto_prv_mth_cr_yr_dmd,   0) + nvl"+

   			  " (cb_monthly_second.coln_for_mth_wc,   0))))))   AS net_due_19"+
   			  " FROM"+
   			    " (SELECT pms_dcb_ob_yearly.beneficiary_sno,"+
   			      "  pms_dcb_ob_yearly.sch_sno,"+
   			       " pms_dcb_ob_yearly.MONTH,"+
   			       " SUM(pms_dcb_ob_yearly.ob_maint_charges) AS"+
   			    " opening_bal_mc,"+
   			      "  SUM(pms_dcb_ob_yearly.ob_int_prv_yr_maint + pms_dcb_ob_yearly.ob_int_cur_yr_maint) AS"+
   			    " addns_if_any,"+
   			      "  SUM(pms_dcb_ob_yearly.coln_upto_prv_mth_maint) AS"+
   			    " colln_upto_prev_month_mc,"+
   			      "  SUM(pms_dcb_ob_yearly.ob_cur_yr_wc + pms_dcb_ob_yearly.ob_yester_yr_wc) AS"+
   			    " opening_bal_wc,"+
   			      "  SUM(pms_dcb_ob_yearly.dmd_upto_prv_mth_wc) AS"+
   			    " demand_upto_prev_month_wc,"+
   			      "  SUM(pms_dcb_ob_yearly.coln_upto_prv_mth_yester_yr) AS"+
   			    " coln_upto_prv_mth_yester_yr_wc,"+
   			      "  SUM(pms_dcb_ob_yearly.coln_upto_prv_mth_wc) AS"+
   			    " coln_upto_prv_mth_cr_yr_dmd"+
   			     " FROM pms_dcb_ob_yearly"+
   			     " GROUP BY pms_dcb_ob_yearly.beneficiary_sno,"+
   			       " pms_dcb_ob_yearly.MONTH,"+
   			       " pms_dcb_ob_yearly.sch_sno,"+
   			       " pms_dcb_ob_yearly.fin_year)"+ //HAVING pms_dcb_ob_yearly.MONTH = 7)"+
   			  " ob_yearly_first JOIN"+
   			    " (SELECT pms_dcb_trn_cb_monthly.sch_sno,"+
   			      "  pms_dcb_trn_cb_monthly.beneficiary_sno,"+
   			       " pms_dcb_trn_cb_monthly.MONTH,"+
   			       " pms_dcb_trn_cb_monthly.fin_year,"+
   			       " SUM(pms_dcb_trn_cb_monthly.coln_for_mth_maint) AS"+
   			    " colln_for_mth_maint,"+
   			       " SUM(pms_dcb_trn_cb_monthly.dmd_for_mth_wc) AS"+
   			    " dmd_for_mth_wc,"+
   			      "  SUM(pms_dcb_trn_cb_monthly.coln_for_mth_yester_yr) AS"+
   			    " coln_for_mth_yester_yr_wc,"+
   			      "  SUM(pms_dcb_trn_cb_monthly.coln_for_mth_wc) AS"+
   			  " coln_for_mth_wc"+
   			    "  FROM pms_dcb_trn_cb_monthly"+
   			     " GROUP BY pms_dcb_trn_cb_monthly.beneficiary_sno,"+
   			       " pms_dcb_trn_cb_monthly.MONTH,"+
   			       " pms_dcb_trn_cb_monthly.fin_year,"+
   			       " pms_dcb_trn_cb_monthly.sch_sno)"+
   			  " cb_monthly_second ON ob_yearly_first.beneficiary_sno = cb_monthly_second.beneficiary_sno"+
   			   " AND ob_yearly_first.sch_sno = cb_monthly_second.sch_sno   JOIN"+
   			    " (SELECT pms_dcb_ob_yearly.beneficiary_sno,"+
   			      "  pms_dcb_ob_yearly.sch_sno,"+
   			       " pms_dcb_ob_yearly.MONTH,"+
   			       " SUM(pms_dcb_ob_yearly.ob_maint_charges) AS"+
   			    " opening_bal_mc,"+
   			      "  SUM(pms_dcb_ob_yearly.ob_cur_yr_wc + pms_dcb_ob_yearly.ob_yester_yr_wc) AS"+
   			    " opening_bal_wc"+
   			     " FROM pms_dcb_ob_yearly"+
   			     " GROUP BY pms_dcb_ob_yearly.beneficiary_sno,"+
   			       " pms_dcb_ob_yearly.sch_sno,"+
   			       " pms_dcb_ob_yearly.MONTH,"+
   			       " pms_dcb_ob_yearly.fin_year ) "+//HAVING pms_dcb_ob_yearly.MONTH = 7)"+
   			  " pms_dcb_ob_yearly_report_third ON ob_yearly_first.beneficiary_sno = pms_dcb_ob_yearly_report_third.beneficiary_sno"+
   			   " AND ob_yearly_first.sch_sno = pms_dcb_ob_yearly_report_third.sch_sno JOIN"+
   			    " (SELECT pms_dcb_ben_type.ben_type_desc AS ben_type_desc,"+
   			      "  pms_dcb_ben_type.ben_type_id AS ben_type_id,"+
   			       " pms_dcb_mst_beneficiary_four.beneficiary_sno,"+
   			       " pms_dcb_mst_beneficiary_four.office_id,"+
   			       " pms_dcb_mst_beneficiary_four.office_name,"+
   			       " pms_dcb_mst_beneficiary_four.district_code,"+
   			       " pms_dcb_mst_beneficiary_four.district_name,"+
   			       " pms_dcb_mst_beneficiary_four.beneficiary_name"+
   			     " FROM pms_dcb_ben_type JOIN"+
   			      " (SELECT DISTINCT pms_dcb_mst_beneficiary.beneficiary_type_id AS beneficiary_type_id,"+
   			        "  pms_dcb_mst_beneficiary.beneficiary_sno,"+
   			         " pms_dcb_mst_beneficiary.beneficiary_name,"+
   			         " pms_dcb_mst_beneficiary.office_id,"+
   			         " com_mst_offices.office_name,"+
   			         " pms_dcb_mst_beneficiary.district_code,"+
   			         " com_mst_districts.district_name"+
   			       " FROM pms_dcb_mst_beneficiary JOIN pms_dcb_trn_cb_monthly ON  pms_dcb_mst_beneficiary.status='L' and pms_dcb_mst_beneficiary.beneficiary_sno = pms_dcb_trn_cb_monthly.beneficiary_sno "+
   			  " JOIN com_mst_districts ON com_mst_districts.district_code = pms_dcb_mst_beneficiary.district_code JOIN com_mst_offices ON com_mst_offices.office_id = "+
   			  " pms_dcb_mst_beneficiary.office_id)"+
   			    " pms_dcb_mst_beneficiary_four ON pms_dcb_mst_beneficiary_four.beneficiary_type_id = pms_dcb_ben_type.ben_type_id)"+
   			  " pms_dcb_mst_beneficiary_five ON pms_dcb_mst_beneficiary_five.beneficiary_sno = ob_yearly_first.beneficiary_sno JOIN"+
   			    " (SELECT DISTINCT pms_sch_master.sch_sno AS sch_sno,"+
   			       " pms_sch_master.sch_name AS sch_name,"+
   			       " pms_sch_master.sch_type_id AS sch_type_id,"+
   			       " pms_sch_lkp_type.sch_type_desc AS sch_type_desc"+
   			     " FROM pms_sch_master JOIN pms_dcb_trn_cb_monthly ON pms_dcb_trn_cb_monthly.sch_sno = pms_sch_master.sch_sno JOIN pms_sch_lkp_type ON "+
   			  " pms_sch_master.sch_type_id = pms_sch_lkp_type.sch_type_id)"+
   			  " pms_sch_master_six ON pms_sch_master_six.sch_sno = ob_yearly_first.sch_sno"+
   			  " ORDER BY pms_dcb_mst_beneficiary_five.office_id,"+
   			    " pms_dcb_mst_beneficiary_five.ben_type_id) where MONTH=? and YEAR=?  "+contqry;
            	  ps=connection.prepareStatement(countqry);
        		  ps.setInt(1, month_var);
                  ps.setInt(2, year_var);
        		  res=ps.executeQuery();
        		  if(res.next())
        		  {
        			  countval=res.getInt("countvalue");
        		  }
              }
              catch(Exception e)
              {
            	  System.out.println("Error in insertion"+e);
              }
              if(countval>0)
              {
            	  try
            	  {
            		  String qry="select count(*)   from PMS_DCB_LEDGER_ACTUAL where MONTH="+month_var+" and YEAR="+year_var+" "+contqry;
            	      countvalue=0;
            	   	  ps=null;
            	   	  res=null;
            		  ps=connection.prepareStatement(qry);
            		  res=ps.executeQuery();
            		  if(res.next())
            		  {	  
            		      countvalue=res.getInt(1);
            		  }
            		  ps=null;
            	   	  res=null;
            	  }
            	  catch(Exception e)
            	  {
            		  System.out.println("Error in insertion"+e);
            	  }
            	  if(countvalue==0)
            	  {
            		  xmlvariable += "<countvalue>" +1 + "</countvalue>";
            		  try
            		  {        		 
            		   		 
            			  				int apr_year=0;
            			  				if (month_process==1 || month_process==2 || month_process==3)
            			  				{
            			  					if (month_var==4)
            			  						apr_year=year_process;
            			  					else            			  				  	
            			  						apr_year=year_process-1;	
            			  				}else
            			  				{
            			  					apr_year=year_process;
            			  				}
            			  				
            			  				 String qry=" INSERT INTO PMS_DCB_LEDGER_ACTUAL  ( OFFICE_ID, "+
													" OFFICE_NAME, "+
													" SCH_SNO, "+
													" SCH_NAME,BEN_TYPE_GROUP,MD_GROUP, "+
													" BEN_TYPE_ID, "+
													" BEN_TYPE_DESC, "+
													" DISTRICT_CODE, "+
													" DISTRICT_NAME, "+
													" BENEFICIARY_SNO, "+
													" BENEFICIARY_NAME, SCH_TYPE_DESC_REPORT,"+
													" SCH_TYPE_ID, "+
													" SCH_TYPE_DESC, "+
													" MONTH, "+
													" YEAR, "+
													" OPENING_BAL_MC, "+
													" ADDNS_IF_ANY, "+
													" COL_UTO_PRV_MNH_MC, "+
													" COLL_FOR_MTH_MAINT, "+
													" TOTAL_COLLN, "+
													" BALANCE, "+
													" OPENING_BAL_WC, "+
													" OPENING_BAL_YESTER_YR, "+  
													" DMD_UPTO_PRV_MNTH_WC, "+
													" DMD_FOR_MTH_WC, "+
													" TOTAL_DUES, "+
													" COLN_UPTO_PRV_MTH_YES_YR_WC, "+
													//" COLN_UPTO_PRV_MTH_CR_YR_DMD, "+
													" COLN_FOR_MTH_YES_YR_WC, "+
													" COLN_FOR_MTH_WC, "+
													" TOT_COLN_YES_YR_DMD, "+
													" TOT_COLN_FOR_YR_DMD, "+
													" BALANCE_18, "+
													" NET_DUE "+
													" ) "+
													" SELECT "+
													"        PMS_DCB_MST_BENEFICIARY_FIVE.OFFICE_ID, "+
													" 		 PMS_DCB_MST_BENEFICIARY_FIVE.OFFICE_NAME, "+
													" 		 OB_YEARLY_FIRST.SCH_SNO, "+
													" 		 PMS_SCH_MASTER_SIX.SCH_NAME,PMS_DCB_MST_BENEFICIARY_FIVE.BEN_TYPE_GROUP,PMS_DCB_MST_BENEFICIARY_FIVE.MD_GROUP, "+
													" 		 PMS_DCB_MST_BENEFICIARY_FIVE.BEN_TYPE_ID, "+
													" 		 PMS_DCB_MST_BENEFICIARY_FIVE.BEN_TYPE_DESC, "+
													" 		 PMS_DCB_MST_BENEFICIARY_FIVE.DISTRICT_CODE, "+
													" 		 PMS_DCB_MST_BENEFICIARY_FIVE.DISTRICT_NAME, "+
													" 		 OB_YEARLY_FIRST.BENEFICIARY_SNO, "+      
													" 		 PMS_DCB_MST_BENEFICIARY_FIVE.BENEFICIARY_NAME, "+
            		  								" case when PMS_SCH_MASTER_SIX.SCH_TYPE_ID in (12,34)  then 'CWSS' else PMS_SCH_MASTER_SIX.SCH_TYPE_DESC end  as SCH_TYPE_DESC_REPORT,"+
													" 		 PMS_SCH_MASTER_SIX.SCH_TYPE_ID, "+
													" 		 PMS_SCH_MASTER_SIX.SCH_TYPE_DESC, "+ 
													" 		 OB_YEARLY_FIRST.MONTH, "+
													" 		 OB_YEARLY_FIRST.FIN_YEAR, "+
													" 				NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_MC,0) AS OPENING_BAL_MC_3 , "+
													" 				NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.ADDNS_IF_ANY,0) AS ADDNS_IF_ANY_3A, "+""+
													" 				NVL(CB_MONTHLY_SECOND.COLLN_UPTO_PREV_MONTH_MC,0) AS COL_UTO_PRV_MNH_MC_4, "+
													" 				NVL(OB_YEARLY_FIRST.COLLN_FOR_MTH_MAINT,0) AS COLL_FOR_MTH_MAINT_5, "+
													" 				  ( NVL(CB_MONTHLY_SECOND.COLLN_UPTO_PREV_MONTH_MC,0)+NVL(OB_YEARLY_FIRST.COLLN_FOR_MTH_MAINT,0))/100000 AS TOTAL_COLLN_6, "+
													"				  ( (  "+
															" 			NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_MC,0) "+
																		" + "+
																		" NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.ADDNS_IF_ANY,0)) "+
																		" - "+
															"		 ( NVL(CB_MONTHLY_SECOND.COLLN_UPTO_PREV_MONTH_MC,0) "+
															" + "+
													" NVL(OB_YEARLY_FIRST.COLLN_FOR_MTH_MAINT,0))) "+ 
													" /100000 AS BALANCE_7, "+
													" NVL((PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_WC+PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_YESTER_YR),0) AS OPENING_BAL_WC_8, "+
													" NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_YESTER_YR,0) AS OPENING_BAL_YESTER_YR_8A, "+
													" NVL(CB_MONTHLY_SECOND.DEMAND_UPTO_PREV_MONTH_WC,0) AS DMD_UPTO_PRV_MNTH_WC_9, "+
													" NVL(OB_YEARLY_FIRST.DMD_FOR_MTH_WC,0) AS DMD_FOR_MTH_WC_10, "+
													" (NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_WC,0)+NVL(CB_MONTHLY_SECOND.DEMAND_UPTO_PREV_MONTH_WC,0)+NVL(OB_YEARLY_FIRST.DMD_FOR_MTH_WC,0))/100000 AS TOTAL_DUES_11, "+
													" NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_YESTER_YR_WC,0) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
												//	" NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_CR_YR_DMD,0) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+													
													" NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_YESTER_YR_WC,0) AS COLN_FOR_MTH_YES_YR_WC_14, "+
													" NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_WC,0) AS COLN_FOR_MTH_WC_15, "+ 
													" (NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_YESTER_YR_WC,0)+NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_YESTER_YR_WC,0)) AS TOT_COLN_YES_YR_DMD_16, "+
													" (NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_CR_YR_DMD,0)+NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_WC,0)) AS TOT_COLN_FOR_YR_DMD_17, "+
													"  ( "+
															" ((NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_WC,0)+NVL(CB_MONTHLY_SECOND.DEMAND_UPTO_PREV_MONTH_WC,0)+NVL(OB_YEARLY_FIRST.DMD_FOR_MTH_WC,0))) "+
													" - "+
													" ((NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_YESTER_YR_WC,0)+NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_YESTER_YR_WC,0))) "+
													" - "+
													" ((NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_CR_YR_DMD,0)+ NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_WC,0))) "+
													" )/100000 AS BALANCE_18, "+
													"   "+
													" ((( "+  
															" NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_MC,0) "+
													" + "+
													" NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.ADDNS_IF_ANY,0)) "+
													" - "+
													" (NVL(CB_MONTHLY_SECOND.COLLN_UPTO_PREV_MONTH_MC,0) "+
															" + "+
													" NVL(OB_YEARLY_FIRST.COLLN_FOR_MTH_MAINT,0))) "+
													" + "+
													" (( "+
															" ((NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_WC,0)+NVL(CB_MONTHLY_SECOND.DEMAND_UPTO_PREV_MONTH_WC,0)+NVL(OB_YEARLY_FIRST.DMD_FOR_MTH_WC,0))) "+
													" - "+
													" ((NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_YESTER_YR_WC,0)+NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_YESTER_YR_WC,0))) "+
													" - "+
													" ((NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_CR_YR_DMD,0)+ NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_WC,0))) "+
													" )))/100000 as NET_DUE_19 "+
													" FROM "+  
													" (SELECT "+
															" SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_FOR_MTH_MAINT) AS COLLN_FOR_MTH_MAINT, "+
													" SUM(PMS_DCB_TRN_CB_MONTHLY.DMD_FOR_MTH_WC) AS DMD_FOR_MTH_WC, "+
													" SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_FOR_MTH_YESTER_YR) AS COLN_FOR_MTH_YESTER_YR_WC, "+
													" SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_FOR_MTH_WC) AS COLN_FOR_MTH_WC, "+
													" PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO, "+
													" PMS_DCB_TRN_CB_MONTHLY.SCH_SNO, "+
													" PMS_DCB_TRN_CB_MONTHLY.MONTH," +
													" PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR "+
													"  "+  
													" FROM "+  
													" PMS_DCB_TRN_CB_MONTHLY "+
													" GROUP BY PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO,PMS_DCB_TRN_CB_MONTHLY.MONTH ,PMS_DCB_TRN_CB_MONTHLY.SCH_SNO,PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR having PMS_DCB_TRN_CB_MONTHLY.MONTH="+month_var+" and PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR="+year_var+")OB_YEARLY_FIRST "+
													" left  JOIN "+
													" (SELECT "+
															" PMS_DCB_TRN_CB_MONTHLY.SCH_SNO, "+
													" PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO, "+
													" PMS_DCB_TRN_CB_MONTHLY.MONTH, "+
													" PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR, "+												
													" CASE  when "+month_process+"= 3   THEN 0    ELSE SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_UPTO_MTH_MAINT)    END  AS COLLN_UPTO_PREV_MONTH_MC, "+    
													" CASE  when  "+month_process+" = 3   THEN 0    ELSE SUM(PMS_DCB_TRN_CB_MONTHLY.DMD_UPTO_MTH_WC)        END   AS DEMAND_UPTO_PREV_MONTH_WC, "+
													" CASE  when  "+month_process+"= 3   THEN 0    ELSE SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_UPTO_MTH_YESTER_YR)END   AS COLN_UPTO_PRV_MTH_YESTER_YR_WC, "+
													" CASE  when  "+month_process+" = 3   THEN 0    ELSE SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_UPTO_MTH_WC)       END   AS COLN_UPTO_PRV_MTH_CR_YR_DMD "+													"  FROM "+
													" PMS_DCB_TRN_CB_MONTHLY   "+
													" GROUP BY PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO,PMS_DCB_TRN_CB_MONTHLY.MONTH,PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR,PMS_DCB_TRN_CB_MONTHLY.SCH_SNO "+
													" HAVING PMS_DCB_TRN_CB_MONTHLY.MONTH="+month_process+"   and PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR="+year_process+" )CB_MONTHLY_SECOND "+
													" ON "+
													" OB_YEARLY_FIRST.BENEFICIARY_SNO=CB_MONTHLY_SECOND.BENEFICIARY_SNO "+
													" AND "+
													" OB_YEARLY_FIRST.SCH_SNO=CB_MONTHLY_SECOND.SCH_SNO "+
													"   JOIN "+
													" (SELECT PMS_DCB_OB_YEARLY.BENEFICIARY_SNO, "+
															" PMS_DCB_OB_YEARLY.SCH_SNO, "+
													" PMS_DCB_OB_YEARLY.MONTH, "+
													" SUM(PMS_DCB_OB_YEARLY.OB_MAINT_CHARGES) AS OPENING_BAL_MC, "+
													" SUM(PMS_DCB_OB_YEARLY.OB_CUR_YR_WC) as OPENING_BAL_WC, "+
													" sum(PMS_DCB_OB_YEARLY.OB_YESTER_YR_WC) AS OPENING_BAL_YESTER_YR, "+
													" SUM(PMS_DCB_OB_YEARLY.OB_INT_PRV_YR_MAINT+PMS_DCB_OB_YEARLY.OB_INT_CUR_YR_MAINT) AS ADDNS_IF_ANY "+
													" FROM "+
													" PMS_DCB_OB_YEARLY "+
													" GROUP BY PMS_DCB_OB_YEARLY.BENEFICIARY_SNO,PMS_DCB_OB_YEARLY.SCH_SNO,PMS_DCB_OB_YEARLY.MONTH,PMS_DCB_OB_YEARLY.FIN_YEAR "+
													" HAVING PMS_DCB_OB_YEARLY.MONTH=4 and PMS_DCB_OB_YEARLY.FIN_YEAR="+apr_year+" ) PMS_DCB_OB_YEARLY_REPORT_THIRD "+
													" ON "+
													" OB_YEARLY_FIRST.BENEFICIARY_SNO=PMS_DCB_OB_YEARLY_REPORT_THIRD.BENEFICIARY_SNO "+
													" AND "+
													" OB_YEARLY_FIRST.SCH_SNO=PMS_DCB_OB_YEARLY_REPORT_THIRD.SCH_SNO "+
													" JOIN "+ 
													" (SELECT PMS_DCB_BEN_TYPE.BEN_TYPE_DESC AS BEN_TYPE_DESC, "+
															" PMS_DCB_BEN_TYPE.BEN_TYPE_ID AS BEN_TYPE_ID,PMS_DCB_BEN_TYPE.BEN_TYPE_GROUP,PMS_DCB_BEN_TYPE.MD_GROUP, "+
													" PMS_DCB_MST_BENEFICIARY_FOUR.BENEFICIARY_SNO, "+
													" PMS_DCB_MST_BENEFICIARY_FOUR.OFFICE_ID, "+
													" PMS_DCB_MST_BENEFICIARY_FOUR.OFFICE_NAME, "+
													" PMS_DCB_MST_BENEFICIARY_FOUR.DISTRICT_CODE, "+
													" PMS_DCB_MST_BENEFICIARY_FOUR.DISTRICT_NAME, "+
													" PMS_DCB_MST_BENEFICIARY_FOUR.BENEFICIARY_NAME "+
													" "+
													" FROM "+
													" PMS_DCB_BEN_TYPE "+
													" left outer JOIN "+  
												" (SELECT DISTINCT PMS_DCB_MST_BENEFICIARY.BENEFICIARY_TYPE_ID AS BENEFICIARY_TYPE_ID, "+
												//" (SELECT DISTINCT PMS_DCB_MST_BENEFICIARY.BENEFICIARY_TYPE_ID_SUB AS BENEFICIARY_TYPE_ID_SUB, "+
													" PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO, "+
													" PMS_DCB_MST_BENEFICIARY.BENEFICIARY_NAME, "+
													" PMS_DCB_MST_BENEFICIARY.OFFICE_ID, "+
													" COM_MST_OFFICES.OFFICE_NAME, "+
													" PMS_DCB_MST_BENEFICIARY.DISTRICT_CODE, "+
													" COM_MST_DISTRICTS.DISTRICT_NAME "+
													" FROM "+
													" PMS_DCB_MST_BENEFICIARY "+
													" JOIN "+
													" PMS_DCB_TRN_CB_MONTHLY "+
													" ON "+
													" PMS_DCB_MST_BENEFICIARY.status       ='L' and PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO "+
													" JOIN "+
													" COM_MST_DISTRICTS "+
													" ON "+
													" COM_MST_DISTRICTS.DISTRICT_CODE=PMS_DCB_MST_BENEFICIARY.DISTRICT_CODE "+
													" JOIN "+
													" COM_MST_OFFICES "+
													" ON "+
													" COM_MST_OFFICES.OFFICE_ID=PMS_DCB_MST_BENEFICIARY.OFFICE_ID "+
													" )PMS_DCB_MST_BENEFICIARY_FOUR "+
													" ON "+
												  	" PMS_DCB_MST_BENEFICIARY_FOUR.BENEFICIARY_TYPE_ID=PMS_DCB_BEN_TYPE.BEN_TYPE_ID)PMS_DCB_MST_BENEFICIARY_FIVE "+
													//	" PMS_DCB_MST_BENEFICIARY_FOUR.BENEFICIARY_TYPE_ID_SUB=PMS_DCB_BEN_TYPE.BEN_TYPE_ID)PMS_DCB_MST_BENEFICIARY_FIVE "+
													" ON "+  
													" PMS_DCB_MST_BENEFICIARY_FIVE.BENEFICIARY_SNO=OB_YEARLY_FIRST.BENEFICIARY_SNO "+
													" JOIN "+
													" (SELECT DISTINCT PMS_SCH_MASTER.SCH_SNO AS SCH_SNO, "+
															" PMS_SCH_MASTER.SCH_NAME AS SCH_NAME , "+
													" PMS_SCH_MASTER.SCH_TYPE_ID AS SCH_TYPE_ID , "+
													" PMS_SCH_LKP_TYPE.SCH_TYPE_DESC AS SCH_TYPE_DESC "+
													" FROM "+
													" PMS_SCH_MASTER "+
													" JOIN "+
													" PMS_DCB_TRN_CB_MONTHLY "+
													" ON "+  
													" PMS_DCB_TRN_CB_MONTHLY.SCH_SNO=PMS_SCH_MASTER.SCH_SNO "+
													" JOIN "+
													" PMS_SCH_LKP_TYPE "+     
								 					" ON "+
													" PMS_SCH_MASTER.SCH_TYPE_ID=PMS_SCH_LKP_TYPE.SCH_TYPE_ID "+
													" )PMS_SCH_MASTER_SIX "+    
													" ON "+   
													" PMS_SCH_MASTER_SIX.SCH_SNO=OB_YEARLY_FIRST.SCH_SNO  " +
													" "+contqry_1+ // " and  BENEFICIARY_SNO in(2396) "+        	  	  									  	
													" ORDER BY PMS_DCB_MST_BENEFICIARY_FIVE.OFFICE_ID,PMS_DCB_MST_BENEFICIARY_FIVE.BEN_TYPE_ID    "; 		  
            			  				 String sel_qry="";
            			  				 
	        		  		 sel_qry="select count(*) from PMS_DCB_LEDGER_ACTUAL  where MONTH="+month_var+" and YEAR="+year_var+" "+contqry;	        		  		      
	        		  		 ps=connection.prepareStatement(sel_qry);
	        		  		 ResultSet rs_count=ps.executeQuery();  
	        		  		 int count_record_found=0;
	        		  		 if (rs_count.next())
	        		  		 {	        		  			       
	        		  			count_record_found=rs_count.getInt(1);
	        		  		 }
	        		  		 if (count_record_found==0)
	        		  		 {  
	        		  			 System.out.println("Stage 1 - End  " + divisionid);
	        		             System.out.println("Stage 2 -- Batch Update Start " + divisionid);
	        		  			 ps=null;   
	        		  			 ps=connection.prepareStatement(qry);
	        		  			 ps.executeUpdate();	        		  		  
	        		  			 sel_qry="select count(*) from PMS_DCB_LEDGER_ACTUAL  where MONTH="+month_var+" and YEAR="+year_var+" "+contqry;	        		  		    
		        		  		 ps=connection.prepareStatement(sel_qry);
		        		  		 ResultSet rs_count_new=ps.executeQuery();
		        		  		 int record_insert=0;
		        		  		 if (rs_count_new.next())  
		        		  		 {
		        		  			 record_insert=rs_count_new.getInt(1);
		        		  		 } 
		        		  		 
	        		  		 if (record_insert!=0)
			        		 {
	        		  			 		System.out.print("Stage 2.0  Grand Total and Scheme Cat Update " + divisionid);
			        		  			PreparedStatement  proc_stmt=null;
			        		  			try
			        		  			{
				        		  			proc_stmt = connection.prepareCall("{call PMS_DCB_GRAND_TOTAL (?,?,?,?,?) }");		        		  		 
				        		  			proc_stmt.setInt(1, month_var);
				        					proc_stmt.setInt(2, year_var);		        					 
				        					proc_stmt.setInt(3, month_process);
				        					proc_stmt.setInt(4, year_process);	        					
				        					proc_stmt.setInt(5, Integer.parseInt(divisionid));	        		  			   // P Month         month_process      
				        					proc_stmt.execute();
			        		  			}catch(Exception e)
			        		  			{
			        		  			}
			        					
			        		  			 xmlvariable += "<flag>success</flag>";
			        		  			 xmlvariable += "<countinsert>" + 1 + "</countinsert>";
			        		  			 xmlvariable += "<month_var>"+month_var+"</month_var>";
			        		  			 xmlvariable += "<year_var>"+year_var+"</year_var>";  
			        		  			 xmlvariable += "<error_splflag>" + 0 + "</error_splflag>"; 
			        		  			 String up_load="";
			        		  			// Transfer from  here 
			        		  			sel_qry="";
			        		  		   
			        		  			proc_stmt=null;
				        		  		proc_stmt = connection.prepareCall("{call PMS_DCB_LEDGER_SCH_CAT_UP (?,?,?) }");
				        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
			        					proc_stmt.setInt(2, month_var);	          					
			        					proc_stmt.setInt(3,year_var );	          		  			   // P Month         month_process       
			        					proc_stmt.execute();	
			        		  			up_load="";
			        		  			int mm=0 , yy=0;
		        		  				if (month_var==1)
		        						{
		        		  					mm=12;
		        		  					yy=year_var-1;
		        						}  
		        						else
		        						{  
		        							mm=month_var-1 ;
		        							yy=year_var;
		        						}
		        		  				String ben_new="0";
		        		  				String sch_new="0";
		        		  				System.out.println(" Stage 2.1 Previous Month Collection  start -  ( New 18/11/2014) "+divisionid);
		        		  				proc_stmt=null;
				        		  		proc_stmt = connection.prepareCall("{call PMS_DCB_PRV_DMD_UPDATE (?,?,?) }");
				        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
			        					proc_stmt.setInt(2, month_var);	          					
			        					proc_stmt.setInt(3,year_var );	           		  			   // P Month         month_process       
			        					proc_stmt.execute();	 
			        					System.out.println("Stage 2.2   Add/Sub Charge Start  "+divisionid);
			        					 
			        		  			PreparedStatement ps_new=connection.prepareStatement(" select  distinct BENEFICIARY_SNO from PMS_DCB_LEDGER_ACTUAL where  MONTH="+month_var+" and YEAR="+year_var+" "+contqry);
			        					String schsno_other="",ben_sno_other="";
			        		  			ResultSet rs_outer= ps_new.executeQuery();
			        		  			while (rs_outer.next())
			        		  			{
			        		  				ben_sno_other=rs_outer.getString("BENEFICIARY_SNO");
			        		  				sel_qry="select  SCH_SNO,OFFICE_ID, BENEFICIARY_SNO from PMS_DCB_LEDGER_ACTUAL  where BENEFICIARY_SNO="+ben_sno_other+" and   ROWNUM <=1 and MONTH="+month_var+" and YEAR="+year_var+" "+contqry +" ";
			        		  				rs_count=null;
			        		  				ps=null;
			        		  				ps=connection.prepareStatement(sel_qry);
			        		  				rs_count=ps.executeQuery();
			        		  				count_record_found=0;
			        		  				if (rs_count.next()) 
			        		  				{	  
			        		  					schsno_other=rs_count.getString("SCH_SNO");		        		  			
			        		  					String off=rs_count.getString("OFFICE_ID");
			        		  					//03/09/2012
			        		  					 
			        		  					String Other_charge_cond1 = " where  CASHBOOK_MONTH="+ month_var+ " and CASHBOOK_YEAR="+ year_var
			        		  					+ " and BENEFICIARY_SNO ="+ ben_sno_other+ " and OFFICE_ID ="
			        		  					+ off+ " and CR_DR_INDICATOR='DR' " 
			        		  					+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7 ) and ACCOUNT_HEAD_CODE not in (780406) "   
			        		  					+ " group by BENEFICIARY_SNO"; 
			        		  					   

			        		  					/*String Other_charge_cond1 = " where  PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month_var+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year_var
			        		  					+ " and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno_other+ " and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="
			        		  					+ off+ " and PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='DR' " 
			        		  					+ " and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE=PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=7 and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE not in (780406) "   
			        		  					+ " group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";
			        		  					*/
			        		  					 String Other_charge_cond1_dmd = " where  CASHBOOK_MONTH="+ month_var+ " and CASHBOOK_YEAR="+ year_var
			        		  					+ " and BENEFICIARY_SNO ="+ ben_sno_other+ " and OFFICE_ID ="
			        		  					+ off+ " and CR_DR_INDICATOR='DR' " 
			        		  					+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7 ) and ACCOUNT_HEAD_CODE in (780406)"
			        		  					+ " group by BENEFICIARY_SNO";  
			        		  					
			        		  					/*String Other_charge_cond1_dmd = " where  PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month_var+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year_var
			        		  					+ " and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno_other+ " and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="
			        		  					+ off+ " and PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='DR' " 
			        		  					//+ " and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7 ) and ACCOUNT_HEAD_CODE in (780406)"
			        		  					+ " and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE=PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=7 and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE in (780406)"
			        		  					+ " group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";  */
			        		  					
			        		  				
			        		  					 String Other_charge_cond2 = " where CASHBOOK_MONTH="+ month_var+ " and CASHBOOK_YEAR="+ year_var
			        		  					+ " and BENEFICIARY_SNO ="+ ben_sno_other+ " and OFFICE_ID ="+ off
			        		  					+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7 )and ACCOUNT_HEAD_CODE not in (780406) "
			        		  					+ " and CR_DR_INDICATOR='CR' group by BENEFICIARY_SNO";
			        		  					 
			        		  					
			        		  				/*	String Other_charge_cond2 = " where PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month_var+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year_var
			        		  					+ " and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno_other+ " and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="+ off
			        		  					+ " and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE=PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=7 and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE not in (780406) "
			        		  					+ " and PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='CR' group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";
			        		  					*/
			        		  					
			        		  				 	String Other_charge_cond1_mc = " where  CASHBOOK_MONTH="+ month_var+ " and CASHBOOK_YEAR="+ year_var
			        		  					+ " and BENEFICIARY_SNO ="+ ben_sno_other+ " and OFFICE_ID ="
			        		  					+ off+ " and CR_DR_INDICATOR='DR' " 
			        		  					+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8 ) "
			        		  					+ " group by BENEFICIARY_SNO";
			        		  				 
			        		  					 
			        		  					/*String Other_charge_cond1_mc = " where  PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month_var+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year_var
			        		  					+ " and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno_other+ " and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="
			        		  					+ off+ " and PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='DR' " 
			        		  					+ " and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE =PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=8  "
			        		  					+ " group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";*/
			        		  				 
			        		  					
			        		  					
			        		  				String Other_charge_cond2_mc = " where CASHBOOK_MONTH="+ month_var+ " and CASHBOOK_YEAR="+ year_var
			        		  					+ " and BENEFICIARY_SNO ="+ ben_sno_other+ " and OFFICE_ID ="+ off
			        		  					+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8 ) "
			        		  					+ " and CR_DR_INDICATOR='CR' group by BENEFICIARY_SNO";

			        		  					/*String Other_charge_cond2_mc = " where PMS_DCB_OTHER_CHARGES.CASHBOOK_MONTH="+ month_var+ " and PMS_DCB_OTHER_CHARGES.CASHBOOK_YEAR="+ year_var
			        		  					+ " and PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO ="+ ben_sno_other+ " and PMS_DCB_OTHER_CHARGES.OFFICE_ID ="+ off
			        		  					+ " and PMS_DCB_OTHER_CHARGES.ACCOUNT_HEAD_CODE  =PMS_DCB_RECEIPT_ACCOUNT_MAP.ACCOUNT_HEAD_CODE and PMS_DCB_RECEIPT_ACCOUNT_MAP.COLLECTION_TYPE=8 "
			        		  					+ " and PMS_DCB_OTHER_CHARGES.CR_DR_INDICATOR='CR' group by PMS_DCB_OTHER_CHARGES.BENEFICIARY_SNO";*/
			        		  					
			        		  					String ADD_CHARGES_MC_PRV_UPTO="", MINUS_CHARGES_MC_PRV_UPTO = "",ADD_CHARGES_MC_PRV="",MINUS_CHARGES_MC_PRV="";
			        		  					String ADD_CHARGES_WC_PRV_UPTO="", MINUS_CHARGES_WC_PRV_UPTO = "",ADD_CHARGES_WC_PRV="",MINUS_CHARGES_WC_PRV="";
			        		  					  
			        		  					int prv_month=0,prv_year=0;;
			        		  					
			        		  					if (month_var == 1) 
												{
														prv_month = 12;  
														prv_year =year_var - 1;
												} else
												{
														prv_month = month_var - 1;
														prv_year = year_var;
														
												}
			        		  					String Cond_prv_other_charge=" where MONTH="+prv_month+
													 " and YEAR="+prv_year+
													 " and OFFICE_ID="+off+
													 " and BENEFICIARY_SNO="+ben_sno_other+  
													 " and SCH_SNO="+schsno_other;
			        		  					String Ben_Cond=" where MONTH="+month_var+
												 " and FIN_YEAR="+year_var+    
												 " and OFFICE_ID="+off+
												 " and BENEFICIARY_SNO="+ben_sno_other+  
												 " and SCH_SNO="+schsno_other;  
			        		  					String ADD_DMD_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",Other_charge_cond1_dmd);
			        		  					
			        		  					String ADD_CHARGES_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",Other_charge_cond1);
			        		  					String MINUS_CHARGES_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",Other_charge_cond2);
			        		  					String ADD_CHARGES_MC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",Other_charge_cond1_mc);
			        		  					String MINUS_CHARGES_MC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",Other_charge_cond2_mc);       
			        		  					String CB_INT_AMT_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY", "sum(CB_INT_AMT_WC)",Ben_Cond);
			        		  					ADD_CHARGES_MC_PRV="0";
		        		  						MINUS_CHARGES_MC_PRV="0";
		        		  						ADD_CHARGES_MC_PRV_UPTO="0";
		        		  						MINUS_CHARGES_MC_PRV_UPTO="0";        		  					 
			        		  					ADD_CHARGES_WC_PRV="0"; 
		        		  						MINUS_CHARGES_WC_PRV="0";
		        		  						ADD_CHARGES_WC_PRV_UPTO="0";
		        		  						MINUS_CHARGES_WC_PRV_UPTO="0";
			        		  					String upd="";        		   						        		  					
			        		  					// 07 11 2013      
			        		  				//	if (Double.parseDouble(MINUS_CHARGES_WC) > 0)  
			        		  					//	MINUS_CHARGES_WC=Double.toString(Double.parseDouble(MINUS_CHARGES_WC)*-1); 
			        		  					if (month_var==4)    
			        		  					{  
			        		  						upd="update PMS_DCB_LEDGER_ACTUAL set" +
			        		  							" CB_INT_AMT_WC="+CB_INT_AMT_WC+",ADD_CHARGES_WC="+ADD_CHARGES_WC+" , " +
			        		  							" MINUS_CHARGES_WC="+MINUS_CHARGES_WC +" ," +
			        		  							" ADD_CHARGES_WC_UPTO=0," +
			        		  							" MINUS_CHARGES_WC_UPTO=0,"+
			        		  							" ADD_CHARGES_MC="+ADD_CHARGES_MC+" , " +	
			        		  							" MINUS_CHARGES_MC="+MINUS_CHARGES_MC+" , " +	
			        		  							" ADD_CHARGES_MC_UPTO=0," +
			        		  							" MINUS_CHARGES_MC_UPTO=0 " +
			        		  							" where  MONTH="+month_var+" and YEAR="+year_var+" and  OFFICE_ID="+off+" and  BENEFICIARY_SNO="+ben_sno_other+" and SCH_SNO="+schsno_other;
			        		  					}
			        		  					else
			        		  					{
			        		  					String qry_new1="Select  ADD_CHARGES_WC_UPTO,MINUS_CHARGES_WC_UPTO,ADD_CHARGES_WC,MINUS_CHARGES_WC,ADD_CHARGES_MC,MINUS_CHARGES_MC,ADD_CHARGES_MC_UPTO,MINUS_CHARGES_MC_UPTO from PMS_DCB_LEDGER_ACTUAL"+Cond_prv_other_charge;
			        		  					ResultSet rs_new1=obj.getRS(qry_new1);
			        		  						if (rs_new1.next())
			        		  						{	
			        		  							ADD_CHARGES_WC_PRV_UPTO = obj.isNull(rs_new1.getString("ADD_CHARGES_WC_UPTO"), 1);				
			        		  							MINUS_CHARGES_WC_PRV_UPTO = obj.isNull(rs_new1.getString("MINUS_CHARGES_WC_UPTO"), 1);	
			        		  							ADD_CHARGES_WC_PRV = obj.isNull(rs_new1.getString("ADD_CHARGES_WC"), 1);					
			        		  							MINUS_CHARGES_WC_PRV =  obj.isNull(rs_new1.getString("MINUS_CHARGES_WC" ), 1);			        		  				
			        		  							ADD_CHARGES_MC_PRV = obj.isNull(rs_new1.getString("ADD_CHARGES_MC"), 1);					
			        		  							MINUS_CHARGES_MC_PRV = obj.isNull(rs_new1.getString("MINUS_CHARGES_MC"), 1);			        		  				   
			        		  							ADD_CHARGES_MC_PRV_UPTO =  obj.isNull(rs_new1.getString("ADD_CHARGES_MC_UPTO"), 1);	
			        		  							MINUS_CHARGES_MC_PRV_UPTO= obj.isNull(rs_new1.getString("MINUS_CHARGES_MC_UPTO"), 1);	
			        		  						}
			        		  						rs_new1.close();
			        		  					 
					        		  				if (month_var==1)
					        						{
					        		  					mm=12;
					        		  					yy=year_var-1;
					        						}
					        						else
					        						{  
					        							mm=month_var-1 ;
					        							yy=year_var;
					        						}
				        		  					upd="update PMS_DCB_LEDGER_ACTUAL set " +    
		        		  								"	CB_INT_AMT_WC="+CB_INT_AMT_WC+",ADD_CHARGES_WC="+ADD_CHARGES_WC+" , " +  
		        		  								" 	MINUS_CHARGES_WC="+MINUS_CHARGES_WC +" ," +      
		        		  								"	ADD_CHARGES_WC_UPTO=" +(Double.parseDouble(ADD_CHARGES_WC_PRV_UPTO))+" ," +        		  			    	  				
		        		  								"	MINUS_CHARGES_WC_UPTO=" +(Double.parseDouble(MINUS_CHARGES_WC_PRV_UPTO))+" ," +
		        		  								"	ADD_CHARGES_MC="+ADD_CHARGES_MC+" , " +		  
		        		  								"	ADD_DMD="+ADD_DMD_WC+","+
		        		  								"	DMD_FOR_MTH_WC=DMD_FOR_MTH_WC,"+ 
		        		  								//old code : 10/9/14 "	DMD_FOR_MTH_WC=DMD_FOR_MTH_WC+"+ADD_DMD_WC+","+		        		  								
		        		  								" 	MINUS_CHARGES_MC="+MINUS_CHARGES_MC +" ," +
		        		  								"	ADD_CHARGES_MC_UPTO=" +(Double.parseDouble(ADD_CHARGES_MC_PRV_UPTO)+Double.parseDouble(ADD_CHARGES_MC_PRV))+" ," +
		        		  								"	MINUS_CHARGES_MC_UPTO=" +(Double.parseDouble(MINUS_CHARGES_MC_PRV_UPTO)+Double.parseDouble(MINUS_CHARGES_MC_PRV))+" "+        		  								
		        		  								" where  MONTH="+month_var+" and YEAR="+year_var+" and  OFFICE_ID="+off+" and  BENEFICIARY_SNO="+ben_sno_other+" and SCH_SNO="+schsno_other;
			        		  					}         
			        		  					ps=null;  
			        		  					try    
			        		  					{			
			        		  						ps=connection.prepareStatement(upd);	        		  					
			        		  						ps.execute();
			        		  					}catch(Exception e)   
			        		  					{
			        		  						System.out.println("Error "+e+" --- Add / Sub Other charges ");
			        		  					}
			        		  				}  
			        		  				rs_count.close();
			        		  				System.out.println(" Stage 2.3 DCB BALANCE_18_UPDATE update --- new code () ");
			        		  			}	
			        		  			// ** Add / Sub Charges Procedure Completed........
			        		  			proc_stmt=null;
				        		  		proc_stmt = connection.prepareCall("{call PMS_DCB_BALANCE_18_UPDATE (?,?,?) }");
				        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
			        					proc_stmt.setInt(2, month_var);	          					
			        					proc_stmt.setInt(3,year_var );  	             		  			   // P Month         month_process       
			        					proc_stmt.execute();				          		  			
			        		  			System.out.println(" Stage 3 Deamnd Arrear Month Update---"+divisionid);
		        		  				if (month_var==1)
		        						{
		        		  					mm=12;
		        		  					yy=year_var-1;
		        						}
		        						else
		        						{   
		        							mm=month_var-1 ;
		        							yy=year_var;
		        						}
			        		  			String net_due_ben="",net_due_sch="",net_due_off="";        		  			
			        		  			String prvdmd="0",cmcollection="0",prv_net="0";
			        		  			String prv_maint_upto="",prv_maint="0";
			        		  			String process_colllection="0",coln_include_others_charges="0";
			        		  			String coln_arr_mth_wc="0",dmd_arr_uptp_mth_wc="0";
			        		  			String acharges="0",scharges="0";
			        		  			String dmd_for_mth_wc="0",netdue="0";
			        		  			// Below All Process Batch Update Start for All BENEFICIARY  
			        		  			// Stage : 7
			        		  			System.out.println(" Stage 7.1 ");
			        		  			sel_qry=" select  " +
			        		  					"	BENEFICIARY_SNO," +
			        		  					" 	SCH_SNO," +  
			        		  					"	OFFICE_ID,coln_for_mth_yes_yr_wc,COLN_FOR_MTH_WC,ADD_CHARGES_WC,MINUS_CHARGES_WC,DMD_FOR_MTH_WC,NET_DUE " +
			        		  					" from " +
			        		  					"	PMS_DCB_LEDGER_ACTUAL  " +
			        		  					" where" +
			        		  					"     MONTH="+month_var+"" +  
			        		  					" And YEAR="+year_var+" "+contqry;
			        		  		// Stage : 8
			        		  		    //   8.1  to update maintenance Charges with net due
			        		  					System.out.println(" Stage 3.1  Select values " );	 
			        		  		 
			        		  		 int rr=0;
			        		  			ResultSet rs_local_net_dues=obj.getRS(sel_qry);	        		  			
			        		  			while (rs_local_net_dues.next())
			        		  			{ 
			        		  			 try
			        		  			 {
			        		  				prvdmd="0";prv_net="0";
			        		  				cmcollection="0";
			        		  				process_colllection="0";
			        		  				coln_include_others_charges="0";
			        		  				coln_arr_mth_wc="0";
			        		  				dmd_arr_uptp_mth_wc="0";
			        		  				acharges="0";scharges="0";dmd_for_mth_wc="0";netdue="0";
			        		  				net_due_off=obj.isNull(rs_local_net_dues.getString("OFFICE_ID"),1);
			        		  				net_due_ben=obj.isNull(rs_local_net_dues.getString("BENEFICIARY_SNO"),1);
			        		  				net_due_sch=obj.isNull(rs_local_net_dues.getString("SCH_SNO"),1);
			        		  				cmcollection=obj.isNull(rs_local_net_dues.getString("COLN_FOR_MTH_WC"),1);    		  				
			        		  				acharges=obj.isNull(rs_local_net_dues.getString("ADD_CHARGES_WC"),1);    
			        		  				scharges=obj.isNull(rs_local_net_dues.getString("MINUS_CHARGES_WC"),1);
			        		  				netdue=obj.isNull(rs_local_net_dues.getString("NET_DUE"),1);
			        		  				dmd_for_mth_wc=obj.isNull(rs_local_net_dues.getString("DMD_FOR_MTH_WC"),1);
			        		  				String qry_new2="select COLN_INCLUDE_CHARGES,DMD_FOR_MTH_WC,net_due,COL_UTO_PRV_MNH_MC,COLL_FOR_MTH_MAINT from PMS_DCB_LEDGER_ACTUAL where MONTH="+mm+" and YEAR="+yy+" and  OFFICE_ID="+net_due_off+" and  BENEFICIARY_SNO="+net_due_ben+" and SCH_SNO="+net_due_sch;
			        		  				ResultSet rs_new2=obj2.getRS(qry_new2);
		    		  						if (rs_new2.next())
		    		  						{	  
		    		  							prvdmd=obj.isNull(rs_new2.getString("DMD_FOR_MTH_WC"), 1);
		    		  							prvdmd=obj.isNull(rs_new2.getString("DMD_FOR_MTH_WC"), 1);
		    		  							
		    		  							prv_net=obj.isNull(rs_new2.getString("net_due"), 1);
		    		  							prv_maint_upto=obj.isNull(rs_new2.getString("COL_UTO_PRV_MNH_MC"), 1);
		    		  							prv_maint=obj.isNull(rs_new2.getString("COLL_FOR_MTH_MAINT"), 1);
		    		  						} 
		    		  						/* old code : 08 08 2014 
			        		  				if (Double.parseDouble(cmcollection) >= Double.parseDouble(prvdmd))
			        		  					process_colllection=prvdmd;
			        		  				else
			        		  					process_colllection=cmcollection;
			        		  				*/
		    		  						//	cmcollection=Double.toString(Double.parseDouble(cmcollection)+Double.parseDouble(col14));
		    		  							cmcollection=Double.toString(Double.parseDouble(cmcollection));
			        		  				if (cmcollection.equalsIgnoreCase("0"))     
			        		  				{
			        		  					coln_arr_mth_wc="0";
			        		  				}  
			        		  				else
			        		  				{
			        		  					coln_arr_mth_wc=Double.toString((Double.parseDouble(cmcollection)-Double.parseDouble(prvdmd)));
			        		  				}
			        		  					cmcollection=Double.toString(Double.parseDouble(cmcollection));
			        		  					coln_include_others_charges=Double.toString( ( Double.parseDouble(cmcollection)-(Double.parseDouble(acharges) )+Double.parseDouble(scharges)));
			        		  					
			        		  					// new Code : 08 08 2014 
			        		  					if (Double.parseDouble(coln_include_others_charges) >= Double.parseDouble(prvdmd))
				        		  					{
			        		  							process_colllection=prvdmd;
				        		  					}
				        		  				else
				        		  					{
				        		  						//process_colllection=cmcollection;  old code as an 5/9/2014
				        		  					//	process_colllection= Double.toString(Double.parseDouble(prvdmd)-Double.parseDouble(coln_include_others_charges));
				        		  					process_colllection=coln_include_others_charges; 
				        		  					}
			        		  					  
			        		  					
			        		  					dmd_arr_uptp_mth_wc=Double.toString((Double.parseDouble(netdue)-Double.parseDouble(dmd_for_mth_wc)));
			        		  				    up_load=" update PMS_DCB_LEDGER_ACTUAL " +   
			        		  							" set NET_DUE_PRV="+prv_net+","+
			        		  							" DMD_PRV_MTH_WC="+prvdmd+","+
				        		  						" DMD_ARR_UPTO_MTH_WC="+dmd_arr_uptp_mth_wc+"," +
				        		  						" COLN_INCLUDE_CHARGES="+coln_include_others_charges+"," +
				        		  						" COLN_ARR_MTH_WC="+coln_arr_mth_wc+", " +      
				        		  						" COLN_ADJUSTED="+process_colllection+", " ;
				        		  						// prv _ 11 08 2014 " COLN_FOR_MTH_WC=COLN_FOR_MTH_WC+DECODE((ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO)-(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO),null,0.0,(ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO)-(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO)) ," ;		        		  						
				        		  						//" COLN_FOR_MTH_WC=(COLN_FOR_MTH_WC+(ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO))-(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO) ," ;
				        		  						// new code   
				        		  					 //	" COLN_FOR_MTH_WC=COLN_FOR_MTH_WC+DECODE((MINUS_CHARGES_WC)-(ADD_CHARGES_WC),null,0.0,(MINUS_CHARGES_WC)-(ADD_CHARGES_WC)) ," ;
			        		  							
				        		  						if (month_var==4)
					        		  					{
				        		  							up_load+=" COL_UTO_PRV_MNH_MC=0,";
					        		  					}  
				        		  						else
				        		  						{
				        		  							up_load+=" COL_UTO_PRV_MNH_MC="+(Double.parseDouble(prv_maint_upto)+Double.parseDouble(prv_maint))+",";
				        		  						}
				        		  					 
				        		  						up_load+= "  COLL_FOR_MTH_MAINT=COLL_FOR_MTH_MAINT+DECODE((ADD_CHARGES_MC)+(MINUS_CHARGES_MC),null,0.0,(ADD_CHARGES_MC)+(MINUS_CHARGES_MC) ) , " +
				        		  					// old code : 11 08 2014	" NET_DUE=NET_DUE+DECODE((ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO)-(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO),null,0.0,(ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO)-(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO)) ," +  		  						
				        		  						 	" NET_DUE=NET_DUE-DECODE((ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO)+(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO),null,0.0,(ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO)+(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO)) ," +
				        		  						 "    BALANCE_18=BALANCE_18+DECODE((ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO)-(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO),null,0.0,(ADD_CHARGES_WC+ADD_CHARGES_WC_UPTO)-(MINUS_CHARGES_WC+MINUS_CHARGES_WC_UPTO) ) " +	
				        		  						 " where " +  
			        		  						"        MONTH="+month_var+
			        		  						"    And YEAR="+year_var+
			        		  						"    And OFFICE_ID="+net_due_off+
			        		  						"    And SCH_SNO="+net_due_sch+
			        		  						"  	 And BENEFICIARY_SNO="+net_due_ben;      		  			  			  
				        		  						 
			        		  					 ps=null; 
			        		  					 ps=connection.prepareStatement(up_load);	        		  						
				        		  			 	 ps.execute();    
				        		  				 ps.close();
				        		  					
				        		  				try  
				        		  				{
				        		  					proc_stmt=null;
							        		  		proc_stmt = connection.prepareCall("{call pms_dcb_net_due_update (?,?,?,?,?) }");
							        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
						        					proc_stmt.setInt(2, month_var);	        					
						        					proc_stmt.setInt(3,year_var );	        		  			   // P Month         month_process
						        					proc_stmt.setInt(4, Integer.parseInt(net_due_ben));
						        					proc_stmt.setInt(5, Integer.parseInt(net_due_sch));      
						        					proc_stmt.execute();
						        					proc_stmt=null;
				        		  				}catch(SQLException e) {
				        		  					System.out.println("Error -----> Previouse Record " + e );
				        		  				} 	 		     
				        		  			}catch(Exception e) {
			        		  					System.out.println("Error -----> Previouse Record " + e );
			        		  				}
			        		  			}   
			        		  			 System.out.println(" Stage 5 Master Data Values Update " +divisionid);
			        		  			proc_stmt=null;
				        		  		proc_stmt = connection.prepareCall("{call PMS_DCB_NULLUPDATE (?,?,?) }");
				        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
			        					proc_stmt.setInt(2, month_var);	        					
			        					proc_stmt.setInt(3,year_var );	        		  			   // P Month         month_process       
			        					proc_stmt.execute();
			        		  			up_load="";
			        		  			sel_qry="";
			        		  			sel_qry="select distinct BENEFICIARY_SNO from PMS_DCB_LEDGER_ACTUAL  where MONTH="+month_var+" and YEAR="+year_var+" "+contqry;
			        		  			rs_count=null;
			        		  			ps=null;
				        		  		ps=connection.prepareStatement(sel_qry);
				        		  		rs_count=ps.executeQuery();
				        		  		count_record_found=0;
				        		  		String urb_no,urb_name,ssno="0", prv_sno="0";
				        		  		String prv_name="",blk_sno="",blk_value="";
				        		  		while (rs_count.next())
				        		  		{		        	  	  			 
				        		  			
				        		  			ssno=rs_count.getString(1);
				        		  			up_load="";
				        		  			urb_no=obj.getValue("PMS_DCB_MST_BENEFICIARY", "URBANLB_SNO", "where "+new_cond+" BENEFICIARY_SNO="+ssno);
				        		  			urb_name=obj.getValue("COM_MST_URBAN_LB", "URBANLB_NAME", "where URBANLB_SNO="+urb_no);
				        		  			blk_sno=obj.getValue("PMS_DCB_MST_BENEFICIARY", "BLOCK_SNO", "where "+new_cond+" BENEFICIARY_SNO="+ssno);
				        		  			blk_value=obj.getValue("COM_MST_BLOCKS", "BLOCK_NAME", "where BLOCK_SNO="+blk_sno);
				        		  			prv_sno=obj.getValue("PMS_DCB_MST_BENEFICIARY", "OTHERS_PRIVATE_SNO", "where "+new_cond+" BENEFICIARY_SNO="+ssno);
				        		  			prv_name=obj.getValue("COM_MST_PRIVATE", "PRIVATE_DESC", "where PRIVATE_SNO="+blk_sno);
				        		  			up_load="update PMS_DCB_LEDGER_ACTUAL set " +
				        		  					//" TOT_COLN_FOR_YR_DMD=(TOT_COLN_FOR_YR_DMD-ADD_CHARGES_WC),COLN_FOR_MTH_WC=(COLN_FOR_MTH_WC-ADD_CHARGES_WC),DMD_FOR_MTH_WC=(DMD_FOR_MTH_WC-MINUS_CHARGES_WC),TOTAL_DUES=(TOTAL_DUES-MINUS_CHARGES_WC), "  +
		        		  							" TOT_COLN_FOR_YR_DMD=(TOT_COLN_FOR_YR_DMD-ADD_CHARGES_WC),COLN_FOR_MTH_WC=(COLN_FOR_MTH_WC-ADD_CHARGES_WC),DMD_FOR_MTH_WC=(DMD_FOR_MTH_WC),TOTAL_DUES=(TOTAL_DUES), "  +
				        		  					" BLOCK_SNO=?," +  		        		  				
				        		  					"BLOCK_NAME=?," +
				        		  					"OTHERS_PRIVATE_SNO=?,"+  
				        		  					"PRIVATE_NAME=?,"  +
				        		  					"URBANLB_SNO=?,"  +
				        		  					"URBANLB_NAME=? ,BALANCE=(OPENING_BAL_MC+ADDNS_IF_ANY)-(COL_UTO_PRV_MNH_MC+COLL_FOR_MTH_MAINT)  where MONTH="+month_var+" and YEAR="+year_var+" "+contqry+" and BENEFICIARY_SNO="+ssno;
				        		  			ps=null;  
				        		  			ps=connection.prepareStatement(up_load);  
				        		  			ps.setString(1, blk_sno);
				        		  			ps.setString(2, blk_value);
				        		  			ps.setString(3, prv_sno);
				        		  			ps.setString(4, prv_name);
				        		  			ps.setString(5, urb_no);
				        		  			ps.setString(6, urb_name);
				        		  			
				        		  			ps.execute();
				        		  			ps.close();  
				        		  		}
				        		  		/* 10.2 for outside update   */
				        		  		System.out.println(" 5.1 for outside update  of Attributes of Ben and Balance " );
				        		  		proc_stmt=null;
				        		  		proc_stmt = connection.prepareCall("{call PMS_DCB_NEW_BEN_UPDATE_ACTUAL (?,?,?) }");
				        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
			        					proc_stmt.setInt(2, month_var);	        					
			        					proc_stmt.setInt(3,year_var );	          		  			   // P Month         month_process       
			        					proc_stmt.execute();
			        					
			        					System.out.println(" 5.2 called PMS_DCB_LEDGER_ACTUALREVIEW procedure to insert new table per ben."+divisionid );
			        					proc_stmt=null;
				        		  		proc_stmt = connection.prepareCall("{call PMS_DCB_LEDGER_ACTUALREVIEW (?,?,?) }");
				        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
			        					proc_stmt.setInt(2, month_var);	        					
			        					proc_stmt.setInt(3,year_var );	          		  			   // P Month         month_process       
			        					proc_stmt.execute();
			        					System.out.println(" 5.3 called PMS_DCB_LEDGER_PERCENT_UPDATE (percentage update)"+divisionid );
			        					proc_stmt=null;  
				        		  		proc_stmt = connection.prepareCall("{call PMS_DCB_LEDGER_PERCENT_UPDATE (?,?,?) }");
				        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
			        					proc_stmt.setInt(2, month_var);	        					
			        					proc_stmt.setInt(3,year_var );	          		  			   // P Month         month_process       
			        					proc_stmt.execute();
			        					
			        					System.out.println(" 5.4 called PMS_DCB_NETDUE_LKS (percentage update)"+divisionid );
			        					try
			        					{
			        						proc_stmt=null;
					        		  		proc_stmt = connection.prepareCall("{call PMS_DCB_NETDUE_LKS (?,?,?) }");
					        		  		proc_stmt.setInt(1, Integer.parseInt(divisionid));
				        					proc_stmt.setInt(2, month_var);	          					
				        					proc_stmt.setInt(3,year_var );	          		  			   // P Month         month_process       
				        					proc_stmt.execute();	
				        					proc_stmt=null;
			        					 
			        					}catch(Exception e) 
			        					{
			        						System.out.println("Exception : Last Update" + e);
			        					}  					
			        					
			        					System.out.println(" Stage 5 End ...Process done  " );
			        		}else
			        		{
			        			xmlvariable += "<flag>success</flag>";
	        		  			xmlvariable += "<countvalue>" +0 + "</countvalue>";
	        		  			xmlvariable += "<countinsert>" + 0 + "</countinsert>"; 
	        		  			xmlvariable += "<error_splflag>" + 3 + "</error_splflag>";
			        		}
	        		  		 }  
	        		  		 else
	        		  		 {
	        		  			xmlvariable += "<flag>success</flag>";
	        		  			xmlvariable += "<countvalue>" +1 + "</countvalue>";
	        		  			xmlvariable += "<countinsert>" + 0 + "</countinsert>";
	        		  			xmlvariable += "<error_splflag>" +2 + "</error_splflag>";
	        		  		 }
	        		  		System.out.println(" Stage  Last " );
            		  }
		        	  catch(Exception e)
		        	  {
		        		  System.out.println("Error in insertion"+e);
		        		  xmlvariable += "<flag>success</flag>";
		        		  xmlvariable += "<countvalue>" +1 + "</countvalue>";
		        		  xmlvariable += "<countinsert>" + 2 + "</countinsert>";
		        		  xmlvariable += "<error_splflag>" + 1 + "</error_splflag>";
		              }
            	  }
	              else
	              {
	            	  xmlvariable += "<flag>success</flag>";
	            	  xmlvariable += "<countvalue>" +1 + "</countvalue>";
	        		  xmlvariable += "<countinsert>" + 0 + "</countinsert>";
	        		  xmlvariable += "<month_var>"+month_var+"</month_var>";
	             	 xmlvariable += "<year_var>"+year_var+"</year_var>";
	             	xmlvariable += "<error_splflag>" +0 + "</error_splflag>";
	              
	              }
              }
              else
              {
            	  System.out.println("Else part");
            	  xmlvariable += "<flag>success</flag>";
            	  xmlvariable += "<countvalue>" + 0 + "</countvalue>";
            	  xmlvariable += "<month_var>"+month_var+"</month_var>";
             	 xmlvariable += "<year_var>"+year_var+"</year_var>";
             	xmlvariable += "<error_splflag>" +0 + "</error_splflag>";
              }
              xmlvariable += "</response>";
          }
          if (command_var.equalsIgnoreCase("deletegenerate"))
          {
        	  xmlvariable = "<response>";
              xmlvariable += "<command>deletegenerate</command>";
              try
              {
            	String sql="delete PMS_DCB_LEDGER_ACTUAL where month=? and year=?" +contqry;            	
            	ps=connection.prepareStatement(sql);
            	ps.setInt(1,month_var);
            	ps.setInt(2,year_var);
            	ps.executeUpdate();
            	 sql="delete PMS_DCB_LEDGER where month=? and year=?" +contqry;
             	ps=connection.prepareStatement(sql);
             	ps.setInt(1,month_var);
             	ps.setInt(2,year_var);
             	ps.executeUpdate();
            	 xmlvariable += "<flag>success</flag>";
              }
              catch(Exception e)
              {
            	  System.out.println("Error in deletion"+e);
            	  xmlvariable += "<month_var>"+month_var+"</month_var>";
             	 xmlvariable += "<year_var>"+year_var+"</year_var>";
            	  xmlvariable += "<flag>failure</flag>";
              }
              xmlvariable += "</response>";
          }
          out.write(xmlvariable);
          out.flush();
          out.close();
	}  
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  Auto-generated method stub
	}

}
