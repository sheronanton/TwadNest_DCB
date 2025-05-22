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
  *---------|---------------|--------------------------------------------------- 
  */

package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
public class pms_dcb_ledger extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
     
	String new_cond=Controller.new_cond; 
    public pms_dcb_ledger() 
    {
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
        Controller obj=null;
        try 
 		 {
              
              obj = new Controller();
              connection =obj.con();
              obj.createStatement(connection);
          } catch (Exception ex)
          {
              String connectMsg ="Could not create the connection" + ex.getMessage() + " " +
                  ex.getLocalizedMessage();
              
          }
          command_var=request.getParameter("TEST");
          try
          {
              command_var=request.getParameter("command");             
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
        	  System.out.println("Error in reteriving the divisionid"+e);
          }
         
          
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
   			    " nvl(pms_dcb_ob_yearly_report_third.opening_bal_mc,   0) / 100000 AS opening_bal_mc_3,"+
   			    " nvl(ob_yearly_first.addns_if_any,   0) / 100000 AS addns_if_any_3a,"+
   			    " nvl(ob_yearly_first.colln_upto_prev_month_mc,   0) / 100000 AS col_uto_prv_mnh_mc_4,"+
   			    " nvl(cb_monthly_second.colln_for_mth_maint,   0) / 100000 AS coll_for_mth_maint_5,"+
   			    " (nvl(ob_yearly_first.colln_upto_prev_month_mc,   0) + nvl(cb_monthly_second.colln_for_mth_maint,   0)) / 100000 AS total_colln_6,"+
   			    " ((nvl(pms_dcb_ob_yearly_report_third.opening_bal_mc,   0) + nvl(ob_yearly_first.addns_if_any,   0)) -(nvl(ob_yearly_first.colln_upto_prev_month_mc,   0) +  nvl"+

   			  " (cb_monthly_second.colln_for_mth_maint,   0)))/ 100000 AS balance_7,"+
   			    " nvl(pms_dcb_ob_yearly_report_third.opening_bal_wc,   0) / 100000 AS opening_bal_wc_8,"+
   			    " nvl(ob_yearly_first.demand_upto_prev_month_wc,   0) / 100000 AS dmd_upto_prv_mnth_wc_9,"+
   			    " nvl(cb_monthly_second.dmd_for_mth_wc,   0) / 100000 AS dmd_for_mth_wc_10,"+
   			    " (nvl(pms_dcb_ob_yearly_report_third.opening_bal_wc,   0) + nvl(ob_yearly_first.demand_upto_prev_month_wc,   0) + nvl"+

   			  " (cb_monthly_second.dmd_for_mth_wc,   0)) / 100000 AS total_dues_11,"+
   			    " nvl(ob_yearly_first.coln_upto_prv_mth_yester_yr_wc,   0) / 100000 AS coln_upto_prv_mth_yes_yr_wc_12,"+
   			    " nvl(ob_yearly_first.coln_upto_prv_mth_cr_yr_dmd,   0) / 100000 AS coln_upto_prv_mth_cr_yr_dmd_13,"+
   			    " nvl(cb_monthly_second.coln_for_mth_yester_yr_wc,   0) / 100000 AS coln_for_mth_yes_yr_wc_14,"+
   			    " nvl(cb_monthly_second.coln_for_mth_wc,   0) / 100000 AS coln_for_mth_wc_15,"+
   			    " (nvl(ob_yearly_first.coln_upto_prv_mth_yester_yr_wc,   0) + nvl(cb_monthly_second.coln_for_mth_yester_yr_wc,   0)) / 100000 AS  tot_coln_yes_yr_dmd_16,"+
   			    " (nvl(ob_yearly_first.coln_upto_prv_mth_cr_yr_dmd,   0) + nvl(cb_monthly_second.coln_for_mth_wc,   0)) / 100000 AS tot_coln_for_yr_dmd_17,"+
   			    " (((nvl(pms_dcb_ob_yearly_report_third.opening_bal_wc,   0) + nvl(ob_yearly_first.demand_upto_prev_month_wc,   0) + nvl"+

   			  " (cb_monthly_second.dmd_for_mth_wc,   0))) -((nvl(ob_yearly_first.coln_upto_prv_mth_yester_yr_wc,   0) + nvl (cb_monthly_second.coln_for_mth_yester_yr_wc,  "+

   			   " 0))) -((nvl(ob_yearly_first.coln_upto_prv_mth_cr_yr_dmd,   0) + nvl(cb_monthly_second.coln_for_mth_wc,   0)))) / 100000 AS balance_18,"+
   			    " (((nvl(pms_dcb_ob_yearly_report_third.opening_bal_mc,   0) + nvl(ob_yearly_first.addns_if_any,   0)) -(nvl(ob_yearly_first.colln_upto_prev_month_mc,   0) + "+

   			  " nvl(cb_monthly_second.colln_for_mth_maint,   0))) +((((nvl(pms_dcb_ob_yearly_report_third.opening_bal_wc,   0) + nvl"+

   			  " (ob_yearly_first.demand_upto_prev_month_wc,   0) + nvl(cb_monthly_second.dmd_for_mth_wc,   0))) -((nvl(ob_yearly_first.coln_upto_prv_mth_yester_yr_wc,   "+

   			  " 0) + nvl(cb_monthly_second.coln_for_mth_yester_yr_wc,   0))) -((nvl(ob_yearly_first.coln_upto_prv_mth_cr_yr_dmd,   0) + nvl"+

   			  " (cb_monthly_second.coln_for_mth_wc,   0)))))) / 100000 AS net_due_19"+
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
   			   " AND ob_yearly_first.sch_sno = cb_monthly_second.sch_sno left JOIN"+
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
   			       " pms_dcb_ob_yearly.fin_year )"+//HAVING pms_dcb_ob_yearly.MONTH = 7)"+
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
   			       " FROM pms_dcb_mst_beneficiary JOIN pms_dcb_trn_cb_monthly ON pms_dcb_mst_beneficiary.status='L' and pms_dcb_mst_beneficiary.beneficiary_sno = pms_dcb_trn_cb_monthly.beneficiary_sno "+
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
   			    " pms_dcb_mst_beneficiary_five.ben_type_id)where MONTH=? and YEAR=?  "+contqry;
            	
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
            	//  System.out.println("countval"+countval);
            	  try
            	  {
            		  String qry="select count(*) as countvalue from PMS_DCB_LEDGER where MONTH=? and YEAR=? "+contqry;
            		  ps=connection.prepareStatement(qry);
            		  ps.setInt(1, month_var);
                      ps.setInt(2, year_var);
                      
            		  res=ps.executeQuery();
            		  if(res.next())
            		  {	  
            			 
            		      countvalue=res.getInt("countvalue");
            		  }
            	  }
            	  catch(Exception e)
            	  {
            		  System.out.println("Error in insertion"+e);
            	  }
            	  if(countvalue>=0)
            	  {
            		  xmlvariable += "<countvalue>" +1 + "</countvalue>";
            		  try
            		  {
        		
          int month_process =0;
          int year_process=0;
          if ( month_var==1)
          {
        	  month_process=12;
        	  year_process=year_var-1;
        	  
          }
            			  
    String qry=" INSERT INTO PMS_DCB_LEDGER  ( OFFICE_ID, "+
					" OFFICE_NAME, "+
					" SCH_SNO, "+
					" SCH_NAME,BEN_TYPE_GROUP, "+
					" BEN_TYPE_ID, "+
					" BEN_TYPE_DESC, "+
					" DISTRICT_CODE, "+
					" DISTRICT_NAME, "+
					" BENEFICIARY_SNO, "+
					" BENEFICIARY_NAME, "+
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
					" COLN_UPTO_PRV_MTH_CR_YR_DMD, "+
					" COLN_FOR_MTH_YES_YR_WC, "+
					" COLN_FOR_MTH_WC, "+
					" TOT_COLN_YES_YR_DMD, "+
					" TOT_COLN_FOR_YR_DMD, "+
					" BALANCE_18, "+
					" NET_DUE "+
					" ) "+
					" SELECT "+
					" PMS_DCB_MST_BENEFICIARY_FIVE.OFFICE_ID, "+
					" PMS_DCB_MST_BENEFICIARY_FIVE.OFFICE_NAME, "+
					" CB_MONTHLY_SECOND.SCH_SNO, "+
					" PMS_SCH_MASTER_SIX.SCH_NAME,PMS_DCB_MST_BENEFICIARY_FIVE.BEN_TYPE_GROUP, "+
					" PMS_DCB_MST_BENEFICIARY_FIVE.BEN_TYPE_ID, "+
					" PMS_DCB_MST_BENEFICIARY_FIVE.BEN_TYPE_DESC, "+
					" PMS_DCB_MST_BENEFICIARY_FIVE.DISTRICT_CODE, "+
					" PMS_DCB_MST_BENEFICIARY_FIVE.DISTRICT_NAME, "+
					" CB_MONTHLY_SECOND.BENEFICIARY_SNO, "+
					" PMS_DCB_MST_BENEFICIARY_FIVE.BENEFICIARY_NAME, "+
					" PMS_SCH_MASTER_SIX.SCH_TYPE_ID, "+
					" PMS_SCH_MASTER_SIX.SCH_TYPE_DESC, "+ 
					" OB_YEARLY_FIRST.MONTH, "+
					"   OB_YEARLY_FIRST.FIN_YEAR,"+
					" round(NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_MC,0)/100000,2) AS OPENING_BAL_MC_3 , "+
					" round(NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.ADDNS_IF_ANY,0)/100000,2) AS ADDNS_IF_ANY_3A, "+""+
					" round(NVL(CB_MONTHLY_SECOND.COLLN_UPTO_PREV_MONTH_MC,0)/100000,2) AS COL_UTO_PRV_MNH_MC_4, "+
					" round(NVL(OB_YEARLY_FIRST.COLLN_FOR_MTH_MAINT,0)/100000,2) AS COLL_FOR_MTH_MAINT_5, "+
					" (NVL(CB_MONTHLY_SECOND.COLLN_UPTO_PREV_MONTH_MC,0)+NVL(OB_YEARLY_FIRST.COLLN_FOR_MTH_MAINT,0))/100000 AS TOTAL_COLLN_6, "+
					" (( "+
							" NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_MC,0) "+
					" + "+
					" NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.ADDNS_IF_ANY,0)) "+
					" - "+
					" (NVL(CB_MONTHLY_SECOND.COLLN_UPTO_PREV_MONTH_MC,0) "+
							" + "+
					" NVL(OB_YEARLY_FIRST.COLLN_FOR_MTH_MAINT,0))) "+
					" /100000 AS BALANCE_7, "+
					" round(NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_WC,0)/100000,2) AS OPENING_BAL_WC_8, "+
					" round(NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_YESTER_YR,0)/100000,2) AS OPENING_BAL_YESTER_YR_8A, "+
					" round(NVL(CB_MONTHLY_SECOND.DEMAND_UPTO_PREV_MONTH_WC,0)/100000,2) AS DMD_UPTO_PRV_MNTH_WC_9, "+
					" round(NVL(OB_YEARLY_FIRST.DMD_FOR_MTH_WC,0)/100000,2) AS DMD_FOR_MTH_WC_10, "+
					" (NVL(PMS_DCB_OB_YEARLY_REPORT_THIRD.OPENING_BAL_WC,0)+NVL(CB_MONTHLY_SECOND.DEMAND_UPTO_PREV_MONTH_WC,0)+NVL(OB_YEARLY_FIRST.DMD_FOR_MTH_WC,0))/100000 AS TOTAL_DUES_11, "+
					" round(NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_YESTER_YR_WC,0)/100000,2) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
					" round(NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_CR_YR_DMD,0)/100000,2) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
					" round(NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_YESTER_YR_WC,0)/100000,2) AS COLN_FOR_MTH_YES_YR_WC_14, "+
					" round(NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_WC,0)/100000,2) AS COLN_FOR_MTH_WC_15, "+ 
					" round((NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_YESTER_YR_WC,0)+NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_YESTER_YR_WC,0))/100000,2) AS TOT_COLN_YES_YR_DMD_16, "+
					" round((NVL(CB_MONTHLY_SECOND.COLN_UPTO_PRV_MTH_CR_YR_DMD,0)+NVL(OB_YEARLY_FIRST.COLN_FOR_MTH_WC,0))/100000,2) AS TOT_COLN_FOR_YR_DMD_17, "+
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
					" PMS_DCB_TRN_CB_MONTHLY.MONTH ,PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR"+
					"  "+
					" FROM "+
					" PMS_DCB_TRN_CB_MONTHLY "+
					" GROUP BY PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO,PMS_DCB_TRN_CB_MONTHLY.MONTH ,PMS_DCB_TRN_CB_MONTHLY.SCH_SNO,PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR having PMS_DCB_TRN_CB_MONTHLY.MONTH="+month_var+" and PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR="+year_var+")OB_YEARLY_FIRST "+
					" JOIN "+
					" (SELECT "+
							" PMS_DCB_TRN_CB_MONTHLY.SCH_SNO, "+
					" PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO, "+
					" PMS_DCB_TRN_CB_MONTHLY.MONTH, "+
					" PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR, "+
					"   "+
					" SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_UPTO_MTH_MAINT) AS COLLN_UPTO_PREV_MONTH_MC, "+
					" SUM(PMS_DCB_TRN_CB_MONTHLY.DMD_UPTO_MTH_WC) AS DEMAND_UPTO_PREV_MONTH_WC, "+
					" SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_UPTO_MTH_YESTER_YR) AS COLN_UPTO_PRV_MTH_YESTER_YR_WC, "+
					" SUM(PMS_DCB_TRN_CB_MONTHLY.COLN_UPTO_MTH_WC) AS COLN_UPTO_PRV_MTH_CR_YR_DMD "+
					" FROM "+
					" PMS_DCB_TRN_CB_MONTHLY "+
					" GROUP BY PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO,PMS_DCB_TRN_CB_MONTHLY.MONTH,PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR,PMS_DCB_TRN_CB_MONTHLY.SCH_SNO "+
					" HAVING PMS_DCB_TRN_CB_MONTHLY.MONTH="+month_process+" and PMS_DCB_TRN_CB_MONTHLY.FIN_YEAR="+year_process+" )CB_MONTHLY_SECOND "+
					" ON "+
					" OB_YEARLY_FIRST.BENEFICIARY_SNO=CB_MONTHLY_SECOND.BENEFICIARY_SNO "+
					" AND "+
					" OB_YEARLY_FIRST.SCH_SNO=CB_MONTHLY_SECOND.SCH_SNO "+
					"left JOIN "+
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
					" HAVING PMS_DCB_OB_YEARLY.MONTH=4 and PMS_DCB_OB_YEARLY.FIN_YEAR="+year_process+" )PMS_DCB_OB_YEARLY_REPORT_THIRD "+
					" ON "+
					" OB_YEARLY_FIRST.BENEFICIARY_SNO=PMS_DCB_OB_YEARLY_REPORT_THIRD.BENEFICIARY_SNO "+
					" AND "+
					" OB_YEARLY_FIRST.SCH_SNO=PMS_DCB_OB_YEARLY_REPORT_THIRD.SCH_SNO "+
					" JOIN "+
					" (SELECT PMS_DCB_BEN_TYPE.BEN_TYPE_DESC AS BEN_TYPE_DESC, "+
							" PMS_DCB_BEN_TYPE.BEN_TYPE_ID AS BEN_TYPE_ID,PMS_DCB_BEN_TYPE.BEN_TYPE_GROUP, "+
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
					" PMS_DCB_MST_BENEFICIARY.STATUS='L'  and PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=PMS_DCB_TRN_CB_MONTHLY.BENEFICIARY_SNO "+
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
					" "+contqry_1+"  "+
					" ORDER BY PMS_DCB_MST_BENEFICIARY_FIVE.OFFICE_ID,PMS_DCB_MST_BENEFICIARY_FIVE.BEN_TYPE_ID    "; 		  
	  
	                  
	        		  
	        		  String sel_qry="";
	        		  		 sel_qry="select count(*) from PMS_DCB_LEDGER  where MONTH="+month_var+" and YEAR="+year_var+" "+contqry;
	        		  		 
	        		  		 ps=connection.prepareStatement(sel_qry);
	        		  		 ResultSet rs_count=ps.executeQuery();
	        		  		 int count_record_found=0;
	        		  		 if (rs_count.next())
	        		  		 {
	        		  			 
	        		  			count_record_found=rs_count.getInt(1);
	        		  		 }
	        		  		
	        		  		 if (count_record_found==0)
	        		  		 {
	        		  			 ps=null;
	        		  			  ps=connection.prepareStatement(qry);
	        		  			 ps.executeUpdate();
	        		  			 xmlvariable += "<flag>success</flag>";
	        		  			 xmlvariable += "<countinsert>" + 1 + "</countinsert>";
	        		  			 xmlvariable += "<month_var>"+month_var+"</month_var>";
	        		  			 xmlvariable += "<year_var>"+year_var+"</year_var>";
	        		  			 String up_load="UPDATE PMS_DCB_LEDGER "+ 
	        		  			 				" SET TOTAL_COLLN      =COL_UTO_PRV_MNH_MC         +COLL_FOR_MTH_MAINT, "+
	        		  			 				" BALANCE            =(OPENING_BAL_MC            +ADDNS_IF_ANY)-(COL_UTO_PRV_MNH_MC+COLL_FOR_MTH_MAINT), "+
	        		  			 				" TOTAL_DUES         =OPENING_BAL_WC             +DMD_UPTO_PRV_MNTH_WC+DMD_FOR_MTH_WC, "+
	        		  			 				" TOT_COLN_YES_YR_DMD=COLN_UPTO_PRV_MTH_YES_YR_WC+COLN_FOR_MTH_YES_YR_WC, "+
	        		  			 				"  TOT_COLN_FOR_YR_DMD=COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_FOR_MTH_WC, "+
	        		  			 				
	        		  			 				" BALANCE_18=(OPENING_BAL_WC+ DMD_UPTO_PRV_MNTH_WC+DMD_FOR_MTH_WC)- "+
	        		  			 				" (COLN_UPTO_PRV_MTH_YES_YR_WC+COLN_FOR_MTH_YES_YR_WC+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_FOR_MTH_WC) "+
	        		  			 				" , "+    		  			 				
	        		  			 				" NET_DUE=(OPENING_BAL_WC+ DMD_UPTO_PRV_MNTH_WC+DMD_FOR_MTH_WC)- "+
	        		  			 				" (COLN_UPTO_PRV_MTH_YES_YR_WC+COLN_FOR_MTH_YES_YR_WC+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_FOR_MTH_WC)+(OPENING_BAL_MC+ADDNS_IF_ANY)-(COL_UTO_PRV_MNH_MC+COLL_FOR_MTH_MAINT) where month="+month_var+" and year= "+year_var;
	        		  			 
	        		  			 
	        		  			ps=null;
	        		  			ps=connection.prepareStatement(up_load);
	        		  			ps.executeUpdate();
	        		  		 
	        		  			 
	        		  			sel_qry="";
	        		  			sel_qry="select distinct BENEFICIARY_SNO from PMS_DCB_LEDGER  where MONTH="+month_var+" and YEAR="+year_var+" "+contqry;
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
		        		  			
		        		  			
		        		  			up_load="update PMS_DCB_LEDGER set BLOCK_SNO=?," +  		        		  				
		        		  					"BLOCK_NAME=?," +
		        		  					"OTHERS_PRIVATE_SNO=?,"+
		        		  					"PRIVATE_NAME=?,"  +
		        		  					"URBANLB_SNO=?,"  +
		        		  					"URBANLB_NAME=? where BENEFICIARY_SNO="+ssno;
 
		        		  		 
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
	        		  			 
	        		  		 }
	        		  		 else
	        		  		 {
	        		  			 
	        		  			 
	        		  			xmlvariable += "<flag>success</flag>";
	        		  			xmlvariable += "<countvalue>" +1 + "</countvalue>";
	        		  			xmlvariable += "<countinsert>" + 0 + "</countinsert>";
	        		  		 }
            		  }
		        	  catch(Exception e)
		        	  {
		        		  xmlvariable += "<flag>success</flag>";
		        		  xmlvariable += "<countvalue>" +1 + "</countvalue>";
		        		  xmlvariable += "<countinsert>" + 2 + "</countinsert>";
		              }
            	  }
	              else
	              {
	            	  
	            	  xmlvariable += "<flag>success</flag>";
	            	  xmlvariable += "<countvalue>" +1 + "</countvalue>";
	        		  xmlvariable += "<countinsert>" + 0 + "</countinsert>";
	        		  xmlvariable += "<month_var>"+month_var+"</month_var>";
	             	 xmlvariable += "<year_var>"+year_var+"</year_var>";
	              
	              }
            	
              }
              else
              {
            	  xmlvariable += "<flag>success</flag>";
            	  xmlvariable += "<countvalue>" + 0 + "</countvalue>";
            	  xmlvariable += "<month_var>"+month_var+"</month_var>";
             	 xmlvariable += "<year_var>"+year_var+"</year_var>";
              }
              xmlvariable += "</response>";
          }
         
          
          if (command_var.equalsIgnoreCase("deletegenerate"))
          {
        	  xmlvariable = "<response>";
              xmlvariable += "<command>deletegenerate</command>";
              try
              {
            	String sql="delete PMS_DCB_LEDGER where month=? and year=?" +contqry;          	
            	ps=connection.prepareStatement(sql);
            	ps.setInt(1,month_var);
            	ps.setInt(2,year_var);
            	ps.executeUpdate();            	
            	sql="delete PMS_DCB_LEDGER_ACTUAL where month=? and year=?" +contqry;            	
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
         out.println(xmlvariable);
        //  System.out.println("xmlvariable"+xmlvariable);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  Auto-generated method stub
	}

}
