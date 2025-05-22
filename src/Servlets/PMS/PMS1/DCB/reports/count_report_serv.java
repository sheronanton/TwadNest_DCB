/* 
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 21/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 
 *-----------------------------------------------------------------------------
 *-----------------------------------------------------------------------------
 */
package Servlets.PMS.PMS1.DCB.reports;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRExportProgressMonitor;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class count_report_serv extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public count_report_serv() 
    {
        super();
    }
	String new_cond=Controller.new_cond;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException
	{
		 				String userid="0",Office_id="";
		 				Controller obj=new Controller();
						Connection con;
						try
						{
								con=obj.con();
								obj.createStatement(con);
						 		HttpSession session=request.getSession(true);
						 		userid=(String)session.getAttribute("UserId");
								if(userid==null)
								{
								 	response.sendRedirect(request.getContextPath()+"/index.jsp");
								}
								
					//	 	 Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
								Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

								
					//			Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
								if (Office_id.equals("")) Office_id="0";
								 String cond=Office_id.equalsIgnoreCase("5000")?"":" and office_id::int="+Office_id;
								 String cond1=Office_id.equalsIgnoreCase("5000")?"":" and accounting_for_office_id="+Office_id;
				 		String command =obj.setValue("command", request);
				 		 System.out.println("DCB->count_report_serv->command->" + command 	+ " " );
				 		 System.out.println("Office_id is "+Office_id);
				 		if (command.equalsIgnoreCase("pdf"))
				 		{
				 			response.setContentType("application/pdf");
				 			Map p=new HashMap();
				 			p.put("month", obj.setValue("fmonth", request));
				 			p.put("year", obj.setValue("fyear", request));
				 			p.put("monthval",obj.month_val(obj.setValue("fmonth", request)));
				 			String path=getServletContext().getRealPath("/WEB-INF/ReportSrc/Collection_Wc_circle.jasper");  
				 			response.setHeader("Content-Disposition","attachment;filename=\"Collection_Wc_NEW.pdf\"");
				 			OutputStream outp=response.getOutputStream();
				 			JasperPrint jf;				 			 
				 			jf=JasperFillManager.fillReport(path,p,con);
				 			JRExporter jrf;  
				 			jrf=new JRPdfExporter();
				 			jrf.setParameter(JRExporterParameter.JASPER_PRINT, jf);
				 			jrf.setParameter(JRExporterParameter.OUTPUT_STREAM,outp);
				 			jrf.exportReport();
				 			outp.close();
				 		}else if (command.equals("mismatch"))
				 		{
				 			response.setContentType("application/pdf");
				 			Map p=new HashMap();
				 			p.put("month", "2");
				 			String path=getServletContext().getRealPath("/WEB-INF/ReportSrc/BEN_MET_DIFF.jasper");
				 			response.setHeader("Content-Disposition","attachment;filename=\"mismatch.pdf\"");
				 			OutputStream outp=response.getOutputStream();
				 			JasperPrint jrf=JasperFillManager.fillReport(path,p,con);
				 			JRExporter jr=new JRPdfExporter();
				 			jr.setParameter(JRExporterParameter.JASPER_PRINT,jrf);
				 			jr.setParameter(JRExporterParameter.OUTPUT_STREAM, outp);
				 			jr.exportReport();
				 			outp.close();
				 		}else if (command.equals("dataverificiaton_div"))
				 		{
				 			String xml="<response><command>dataverificiaton_div</command>";
				 			String pmonth=request.getParameter("pmonth");
				 			String pyear=request.getParameter("pyear");
				 			String CONTENT_TYPE = "text/xml; charset=windows-1252";
				 			response.setContentType(CONTENT_TYPE);
				 			/*String qry="select office_name,actdmd ,office_id,wcdmd2,actual_coll,fas_collection,extcoll_cr,extcoll_dr,(fas_collection+extcoll_cr)-(extcoll_dr) as fas_tot_collection ";
				 				qry+="from ( SELECT actdmd,wcdmd2,actual_coll,decode(fas_collection,null,0,fas_collection) as fas_collection, ";
				 				qry+="decode(extcoll_cr,null,0,extcoll_cr) as extcoll_cr,decode(extcoll_dr,null,0,extcoll_dr) as extcoll_dr, ";
				 				qry+="b.office_id,office_name FROM  ";
				 				qry+="(SELECT SUM(a.DMD_FOR_MTH_WC) AS actdmd,office_id  FROM PMS_DCB_LEDGER_ACTUAL a  WHERE a.MONTH ="+pmonth+"  AND a.YEAR="+pyear+"     GROUP BY office_id  ) a ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(SELECT SUM(b.total_amt) AS wcdmd2 ,office_id FROM PMS_DCB_WC_BILLING b WHERE b.month="+pmonth+" AND b.year="+pyear+"    GROUP BY office_id  )b ";
				 				qry+="ON (a.office_id=b.office_id) ";
				 				qry+="left OUTER JOIN ";    
				 				qry+="(select   SUM(COLN_INCLUDE_CHARGES) AS actual_coll,office_id from PMS_DCB_LEDGER_ACTUAL   WHERE MONTH ="+pmonth+"  AND YEAR="+pyear+"   GROUP BY office_id )c ";
				 				qry+="ON (c.office_id=b.office_id and c.office_id=a.office_id  ) ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(select   SUM(amount) AS fas_collection,ACCOUNTING_FOR_OFFICE_ID from PMS_DCB_FAS_RECEIPT_VIEW   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+"  and  account_head_code=782406   GROUP BY ACCOUNTING_FOR_OFFICE_ID )d ";
				 				qry+="ON (d.ACCOUNTING_FOR_OFFICE_ID=b.office_id and d.ACCOUNTING_FOR_OFFICE_ID=a.office_id  ) ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(select   SUM(amount1) AS extcoll_cr,ACCOUNTING_FOR_OFFICE_ID from FAS_HEAD_SLTYPE_CR_VIEW   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and achcode=782406   and SLTYPECODE=14   GROUP BY ACCOUNTING_FOR_OFFICE_ID )e ";
				 				qry+="ON (e.ACCOUNTING_FOR_OFFICE_ID=b.office_id and e.ACCOUNTING_FOR_OFFICE_ID=a.office_id  ) ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(select   SUM(amount1) AS extcoll_dr,ACCOUNTING_FOR_OFFICE_ID from FAS_HEAD_SLTYPE_DR_VIEW   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and achcode=782406   and SLTYPECODE=14    GROUP BY ACCOUNTING_FOR_OFFICE_ID )f ";
				 				qry+="ON (f.ACCOUNTING_FOR_OFFICE_ID=b.office_id and f.ACCOUNTING_FOR_OFFICE_ID=a.office_id  ) ";
				 				qry+="join ";
				 				qry+="( ";    
				 				qry+="select  office_name,office_id  from  com_mst_all_offices_view where  office_level_id='DN' ";
				 				qry+=")ben  ";
				 				qry+="on ben.office_id=b.office_id "; 
				 				qry+=")rec ";  
				 			*/    
				 		// String qry="select office_name,actdmd ,office_id,wcdmd2,actual_coll,fas_collection,extcoll_cr,extcoll_dr,(fas_collection+extcoll_cr)-(extcoll_dr) as fas_tot_collection ";
				 			 String qry="select office_name,actdmd ,office_id,round(wcdmd2,0) as wcdmd2,actual_coll,fas_collection,extcoll_cr,extcoll_dr,(extcoll_cr)-(extcoll_dr) as journal_adj,(fas_collection+extcoll_cr)-(extcoll_dr) as fas_tot_collection,arrdmd ";
			 				qry+="from ( SELECT actdmd,wcdmd2,actual_coll,COALESCE(fas_collection,null,0,fas_collection) as fas_collection, ";
			 				qry+="COALESCE(extcoll_cr,null,0,extcoll_cr) as extcoll_cr,COALESCE(extcoll_dr,null,0,extcoll_dr) as extcoll_dr, ";
			 				qry+="b.office_id,office_name,arrdmd FROM  ";
			 				qry+="(SELECT SUM(a.DMD_FOR_MTH_WC) AS actdmd,office_id  FROM PMS_DCB_LEDGER_ACTUAL a  WHERE a.MONTH ="+pmonth+"  AND a.YEAR="+pyear+" "+cond+"     GROUP BY office_id  ) a ";
			 				qry+="left OUTER JOIN ";
			 				qry+="(SELECT SUM(b.total_amt) AS wcdmd2 ,office_id FROM PMS_DCB_WC_BILLING b WHERE b.month="+pmonth+" AND b.year="+pyear+"  "+cond+"   GROUP BY office_id  )b ";
			 				qry+="ON (a.office_id=b.office_id) ";
			 				qry+="left OUTER JOIN ";       
			 				qry+="(select  ( SUM(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)+sum(COLL_FOR_MTH_MAINT)+sum(MINUS_CHARGES_INT)) AS actual_coll,office_id from PMS_DCB_LEDGER_ACTUAL   WHERE MONTH ="+pmonth+"  AND YEAR="+pyear+" "+cond+"  GROUP BY office_id )c ";
			 				qry+="ON (c.office_id=b.office_id and c.office_id=a.office_id  ) ";
			 				qry+="left OUTER JOIN ";
			 				qry+="(select   SUM(amount) AS fas_collection,ACCOUNTING_FOR_OFFICE_ID from PMS_DCB_FAS_RECEIPT_VIEW   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" "+cond1+"  and  account_head_code <> 120601   GROUP BY ACCOUNTING_FOR_OFFICE_ID )d ";
			 				qry+="ON (d.ACCOUNTING_FOR_OFFICE_ID=b.office_id and d.ACCOUNTING_FOR_OFFICE_ID=a.office_id  ) ";
			 				qry+="left OUTER JOIN ";
			 				qry+="(select   SUM(amount) AS extcoll_cr,office_id from PMS_DCB_OTHER_CHARGES   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+"  and CR_DR_INDICATOR='CR' "+cond+"  GROUP BY office_id )e ";
			 				qry+="ON (e.office_id::int=b.office_id and e.office_id::int=a.office_id ) ";
			 				qry+="left OUTER JOIN ";
			 				qry+="(select   SUM(amount) AS extcoll_dr,office_id from PMS_DCB_OTHER_CHARGES   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+"   and CR_DR_INDICATOR='DR' "+cond+"  and ACCOUNT_HEAD_CODE  not in (780406,780401,780402,780403,780404,780405,780407)  GROUP BY office_id )f ";
			 				qry+="ON (f.office_id::int=b.office_id and f.office_id::int=a.office_id  ) ";
			 			
			 				qry+="left OUTER JOIN ";
			 				qry+="(select   SUM(amount) AS arrdmd  FROM PMS_DCB_OTHER_CHARGES   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+"     AND CR_DR_INDICATOR ='DR' "+cond+" and ACCOUNT_HEAD_CODE  in (780406,780401,780402,780403,780404,780405,780407)  )q ";
			 				qry+="ON (1=1) ";
			 			
			 				
			 				qry+="join ";
			 				qry+="( ";    
			 				qry+="select  office_name,office_id  from  com_mst_offices where  office_level_id in('RN','DN') ";
			 				qry+=")ben  ";
			 				qry+="on ben.office_id=b.office_id "; 
			 				qry+=")rec ";  
			 		 System.out.println("qryyy"+qry);
				 				 ResultSet rs=obj.getRS(qry);
				 				 while(rs.next())
				 				 {
				 					xml+="<office_id>"+rs.getString("office_id")+"</office_id>";
				 					xml+="<office_name>"+rs.getString("office_name")+"</office_name>";
				 					xml+="<actdmd>"+rs.getString("actdmd")+"</actdmd>";
				 					xml+="<wcdmd2>"+rs.getString("wcdmd2")+"</wcdmd2>";
				 					xml+="<actual_coll>"+rs.getString("actual_coll")+"</actual_coll>";
				 					xml+="<fas_collection>"+rs.getString("fas_collection")+"</fas_collection>";
				 					xml+="<journal_adj>"+rs.getString("journal_adj")+"</journal_adj>";
				 					xml+="<fas_tot_collection>"+rs.getString("fas_tot_collection")+"</fas_tot_collection>";
				 					
				 					xml+="<arrdmd>"+rs.getString("arrdmd")+"</arrdmd>";

				 					
				 				 } 
				 				xml+="</response>";
				 				System.out.println("xmll"+xml);
				 			    PrintWriter pw=response.getWriter();    
				 				pw.write(xml);
				 		        pw.flush();
				 		        pw.close(); 
				 		}else if (command.equals("dataverificiaton_ben"))
				 		{
				 				String pmonth=request.getParameter("pmonth");
				 				String pyear=request.getParameter("pyear");
				 				String xml="<response><command>dataverificiaton_div</command>";
					 			String CONTENT_TYPE = "text/xml; charset=windows-1252";
					 			String office_id=obj.setValue("office_id", request);
					 			response.setContentType(CONTENT_TYPE);
					 			/*String qry="select BENEFICIARY_NAME,actdmd ,BENEFICIARY_SNO,wcdmd2,actual_coll,fas_collection,extcoll_cr,extcoll_dr,(fas_collection+extcoll_cr)-(extcoll_dr) as fas_tot_collection ";
					 				qry+="from ( SELECT actdmd,wcdmd2,actual_coll,decode(fas_collection,null,0,fas_collection) as fas_collection, ";
					 				qry+="decode(extcoll_cr,null,0,extcoll_cr) as extcoll_cr,decode(extcoll_dr,null,0,extcoll_dr) as extcoll_dr, ";
					 				qry+="b.BENEFICIARY_SNO,BENEFICIARY_NAME FROM  ";
					 				qry+="(SELECT SUM(a.DMD_FOR_MTH_WC) AS actdmd,BENEFICIARY_SNO  FROM PMS_DCB_LEDGER_ACTUAL a  WHERE a.MONTH ="+pmonth+"  AND a.YEAR="+pyear+" and a.office_id="+office_id+"    GROUP BY BENEFICIARY_SNO  ) a ";
					 				qry+="left OUTER JOIN ";
					 				qry+="(SELECT SUM(b.total_amt) AS wcdmd2 ,BENEFICIARY_SNO FROM PMS_DCB_WC_BILLING b WHERE b.month="+pmonth+" AND b.year="+pyear+" and office_id="+office_id+"  GROUP BY BENEFICIARY_SNO  )b ";
					 				qry+="ON (a.BENEFICIARY_SNO=b.BENEFICIARY_SNO) ";
					 				qry+="left OUTER JOIN ";
					 				qry+="(select   SUM(COLN_INCLUDE_CHARGES) AS actual_coll,BENEFICIARY_SNO from PMS_DCB_LEDGER_ACTUAL   WHERE MONTH ="+pmonth+"  AND YEAR="+pyear+" and office_id="+office_id+"   GROUP BY BENEFICIARY_SNO )c ";
					 				qry+="ON (c.BENEFICIARY_SNO=b.BENEFICIARY_SNO and c.BENEFICIARY_SNO=a.BENEFICIARY_SNO  ) ";
					 				qry+="left OUTER JOIN ";
					 				qry+="(select   SUM(amount) AS fas_collection,BENEFICIARY_SNO from PMS_DCB_FAS_RECEIPT_VIEW   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and accounting_for_office_id="+office_id+" and  account_head_code=782406   GROUP BY BENEFICIARY_SNO )d ";
					 				qry+="ON (d.BENEFICIARY_SNO=b.BENEFICIARY_SNO and d.BENEFICIARY_SNO=a.BENEFICIARY_SNO  ) ";
					 				qry+="left OUTER JOIN ";
					 				qry+="(select   SUM(amount1) AS extcoll_cr,slcode from FAS_HEAD_SLTYPE_CR_VIEW   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and achcode=782406  and accounting_for_office_id="+office_id+"  and SLTYPECODE=14   GROUP BY slcode )e ";
					 				qry+="ON (e.slcode=b.BENEFICIARY_SNO and e.slcode=a.BENEFICIARY_SNO  ) ";
					 				qry+="left OUTER JOIN ";
					 				qry+="(select   SUM(amount1) AS extcoll_dr,slcode from FAS_HEAD_SLTYPE_DR_VIEW   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and achcode=782406   and accounting_for_office_id="+office_id+" and SLTYPECODE=14    GROUP BY slcode )f ";
					 				qry+="ON (f.slcode=b.BENEFICIARY_SNO and f.slcode=a.BENEFICIARY_SNO  ) ";
					 				qry+="join ";
					 				qry+="( ";
					 				qry+="select BENEFICIARY_NAME,BENEFICIARY_SNO  from  PMS_DCB_MST_BENEFICIARY   ";
					 				qry+=")ben  ";
					 				qry+="on ben.BENEFICIARY_SNO=b.BENEFICIARY_SNO  order by BENEFICIARY_NAME"; 
					 				qry+=")rec ";
					 			*/ 
					 			String qry="select BENEFICIARY_NAME,BEN_TYPE_DESC,actdmd ,BENEFICIARY_SNO,wcdmd2,actual_coll,fas_collection,extcoll_cr,extcoll_dr,(extcoll_cr)-(extcoll_dr) as journal_adj,(fas_collection+extcoll_cr)-(extcoll_dr) as fas_tot_collection,arrdmd ";
				 				qry+="from ( SELECT actdmd,wcdmd2,actual_coll,COALESCE(fas_collection,null,0,fas_collection) as fas_collection, ";
				 				qry+="COALESCE(extcoll_cr,null,0,extcoll_cr) as extcoll_cr,COALESCE(extcoll_dr,null,0,extcoll_dr) as extcoll_dr, ";
				 				qry+="b.BENEFICIARY_SNO,BENEFICIARY_NAME,arrdmd,BEN_TYPE_DESC FROM  ";
				 				qry+="(SELECT SUM(a.DMD_FOR_MTH_WC) AS actdmd,BENEFICIARY_SNO,BEN_TYPE_DESC  FROM PMS_DCB_LEDGER_ACTUAL a  WHERE a.MONTH ="+pmonth+"  AND a.YEAR="+pyear+" and a.office_id="+office_id+"    GROUP BY BEN_TYPE_DESC,BENEFICIARY_SNO  ) a ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(SELECT ROUND(SUM(b.total_amt),0) AS wcdmd2 ,BENEFICIARY_SNO FROM PMS_DCB_WC_BILLING b WHERE b.month="+pmonth+" AND b.year="+pyear+" and office_id="+office_id+"  GROUP BY BENEFICIARY_SNO  )b ";
				 				qry+="ON (a.BENEFICIARY_SNO=b.BENEFICIARY_SNO) ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(select   (SUM(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)+sum(COLL_FOR_MTH_MAINT)+SUM(MINUS_CHARGES_INT)) AS actual_coll,BENEFICIARY_SNO from PMS_DCB_LEDGER_ACTUAL   WHERE MONTH ="+pmonth+"  AND YEAR="+pyear+" and office_id::int="+office_id+"   GROUP BY BENEFICIARY_SNO )c ";
				 				qry+="ON (c.BENEFICIARY_SNO=b.BENEFICIARY_SNO and c.BENEFICIARY_SNO=a.BENEFICIARY_SNO  ) ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(select   SUM(amount) AS fas_collection,BENEFICIARY_SNO from PMS_DCB_FAS_RECEIPT_VIEW   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and accounting_for_office_id="+office_id+" and account_head_code <> 120601      GROUP BY BENEFICIARY_SNO )d ";
				 				qry+="ON (d.BENEFICIARY_SNO=b.BENEFICIARY_SNO and d.BENEFICIARY_SNO=a.BENEFICIARY_SNO  ) ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(select   SUM(amount) AS extcoll_cr,BENEFICIARY_SNO from PMS_DCB_OTHER_CHARGES   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and OFFICE_ID::int="+office_id+"   and CR_DR_INDICATOR='CR'  GROUP BY BENEFICIARY_SNO )e ";
				 				qry+="ON (e.BENEFICIARY_SNO=b.BENEFICIARY_SNO and e.BENEFICIARY_SNO=a.BENEFICIARY_SNO  ) ";
				 				qry+="left OUTER JOIN ";
				 				qry+="(select   SUM(amount) AS extcoll_dr,BENEFICIARY_SNO from PMS_DCB_OTHER_CHARGES   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and OFFICE_ID::int="+office_id+"  and CR_DR_INDICATOR='DR' and ACCOUNT_HEAD_CODE not in (780406,780401,780402,780403,780404,780405,780407)   GROUP BY BENEFICIARY_SNO )f ";
				 				qry+="ON (f.BENEFICIARY_SNO=b.BENEFICIARY_SNO and f.BENEFICIARY_SNO=a.BENEFICIARY_SNO  ) ";
				 				
				 				qry+="left OUTER JOIN ";
				 				qry+="(select   SUM(amount) AS arrdmd,BENEFICIARY_SNO from PMS_DCB_OTHER_CHARGES   WHERE cashbook_month ="+pmonth+"  AND cashbook_year="+pyear+" and OFFICE_ID::int="+office_id+"  and CR_DR_INDICATOR='DR' and ACCOUNT_HEAD_CODE  in (780406,780401,780402,780403,780404,780405,780407)  GROUP BY BENEFICIARY_SNO )v ";
				 				qry+="ON (v.BENEFICIARY_SNO=b.BENEFICIARY_SNO and v.BENEFICIARY_SNO=a.BENEFICIARY_SNO  ) ";
				 								 				
				 				
				 				qry+="join ";  
				 				qry+="( "; 
				 				qry+="select BENEFICIARY_NAME,BENEFICIARY_SNO,BENEFICIARY_TYPE_ID  from  PMS_DCB_MST_BENEFICIARY   ";
				 				qry+=")ben  ";
				 				qry+="on ben.BENEFICIARY_SNO=b.BENEFICIARY_SNO  order by BENEFICIARY_TYPE_ID,BENEFICIARY_NAME"; 
				 				qry+=")rec ";
				 			 System.out.println(qry);
					 				 ResultSet rs=obj.getRS(qry);
					 				 while(rs.next())    
					 				 {
					 					xml+="<BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO")+"</BENEFICIARY_SNO>";
					 					xml+="<BENEFICIARY_NAME>"+rs.getString("BENEFICIARY_NAME")+"</BENEFICIARY_NAME>";
					 					xml+="<actdmd>"+rs.getString("actdmd")+"</actdmd>";
					 					xml+="<wcdmd2>"+rs.getString("wcdmd2")+"</wcdmd2>";
					 					xml+="<actual_coll>"+rs.getString("actual_coll")+"</actual_coll>";
					 					xml+="<journal_adj>"+rs.getString("journal_adj")+"</journal_adj>";  
					 					xml+="<fas_collection>"+rs.getString("fas_collection")+"</fas_collection>";
					 					xml+="<fas_tot_collection>"+rs.getString("fas_tot_collection")+"</fas_tot_collection>";
					 					
					 					xml+="<BEN_TYPE_DESC>"+rs.getString("BEN_TYPE_DESC")+"</BEN_TYPE_DESC>";

					 					xml+="<arrdmd>"+rs.getString("arrdmd")+"</arrdmd>";
					 					
					 				 }
					 				xml+="</response>";
					 			    PrintWriter pw=response.getWriter();    
					 				pw.write(xml); 
					 		        pw.flush();
					 		        pw.close();
				 		}
						}catch (Exception e) {
						}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
