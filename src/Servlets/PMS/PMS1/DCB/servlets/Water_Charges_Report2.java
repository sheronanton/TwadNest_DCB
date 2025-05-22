package Servlets.PMS.PMS1.DCB.servlets;

/* * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 20/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description
 * */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class Water_Charges_Report2 extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	public String report_name = "Report";

	public Water_Charges_Report2() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		String  qry = "",
		Office_id = "0", 
		Office_name = "", 
		userid = "0", 
		ACCOUNTING_UNIT_ID = "",
		command="",
		process_code="0",
		month="0",
		year="0",
		sel_qry="",
		path="",ctxpath="",option="0";
		Statement stmt3 = null;  
		OutputStream outuputStream2 =null;
		JasperPrint jasperPrint2 =null;
		Map parameters = new HashMap();
		Controller obj = new Controller();
		Connection con=null;
		try 
		{
			con = obj.con();
			obj.createStatement(con);
			command = obj.setValue("command", request);// Command
			if (command == null || command.equals("")) 	command = "no command";
			option= obj.setValue("option", request);// option
			process_code = obj.setValue("process_code", request);// process
			HttpSession session = request.getSession(false);
			try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {
			}
			System.out.println("user id " + userid);
			if (userid == null) 
			{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} 
			System.out.println("DCB-------->Water_Charges_Report-->command--->"+command+"---------->--process_code ("+process_code+")-->option-->"+option);
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");

			if (Office_id.equalsIgnoreCase("0"))
				Office_id=obj.isNull(obj.setValue("office_id", request),1);
			if (Office_id.equalsIgnoreCase("0") || Office_id.equalsIgnoreCase("")) Office_id="5000";
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
			if (Office_name.equals("")) Office_name = "";
			response.setContentType("application/pdf");	   
			ACCOUNTING_UNIT_ID= obj.getValue("FAS_MST_ACCT_UNITS", "ACCOUNTING_UNIT_ID","where ACCOUNTING_UNIT_OFFICE_ID=" + Office_id);
			String month_value_display="",count="2";
			if (command.trim().equalsIgnoreCase("FAS_DCB_DMD")    )  
			{ 
				int month1=obj.intConv(obj.setValue("pmonth", request));
				int month2=obj.intConv(obj.setValue("pmonth1", request));
				int year2 =obj.intConv(obj.setValue("pyear", request));		
				parameters.put("year1", obj.setValue("pyear", request));
				parameters.put("month1", obj.setValue("pmonth", request));
				parameters.put("month2",  obj.setValue("pmonth1", request));					
				String days=obj.month_val2(obj.intConv(obj.setValue("pmonth1", request)));
				if (month1 > month2)
				{
					year2++;	  
				} 
				if (month1==month2)
				{
					count="1";
				}  
				parameters.put("year2", obj.strConv(year2));   
				if (count.equals("1"))
				{
					month_value_display=obj.month_val(obj.setValue("pmonth", request))+" "+obj.setValue("pyear", request);
				}else
				{ 
					month_value_display=obj.month_val(obj.setValue("pmonth", request))+" "+obj.setValue("pyear", request);						
					month_value_display+=" To "+obj.month_val(obj.setValue("pmonth1", request))+" "+obj.setValue("pyear", request);
				}
				if (Office_id.equalsIgnoreCase("5000"))
					Office_id=obj.setValue("office_id", request);
				Office_name=obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
				obj.dis(Office_id);   
				parameters.put("office_id", Office_id);  
				parameters.put("days", Integer.parseInt(days));  
				parameters.put("mvalue", month_value_display);
				parameters.put("count", count); 
				parameters.put("office_name", Office_name);

				String imagespath = getServletContext().getRealPath("/images/")+File.separator;
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION.jasper");					
				ctxpath = path.substring(0, path.lastIndexOf("PMS_DCB_DMD_COLLECTION.jasper"));
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition","attachment; filename=\"PMS_DCB_DMD_COLLECTION.pdf\"");
				outuputStream2 = response.getOutputStream();    
			} else if (command.trim().equalsIgnoreCase("FAS_DCB_DMD_BEN")    )  
			{  
				parameters.put("year1", obj.setValue("pyear", request));
				parameters.put("month1", obj.setValue("pmonth", request));
				parameters.put("month2",  obj.setValue("pmonth1", request));
				parameters.put("ben_sno",  obj.setValue("bensno", request));    
				month_value_display=obj.month_val(obj.setValue("pmonth", request))+" "+obj.setValue("pyear", request);
				month_value_display+=" To "+obj.month_val(obj.setValue("pmonth1", request))+" "+obj.setValue("pyear", request);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", month_value_display);  
				parameters.put("office_name", Office_name);  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_ben.jasper");  
				response.setHeader("Content-Disposition","attachment; filename=\"PMS_DCB_DMD_COLLECTION_BEN_WISE.pdf\"");   
				outuputStream2 = response.getOutputStream();    
			} else if (command.trim().equalsIgnoreCase("meter_installed_status_details")    )  
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/new_meter_report.jasper");					  
				response.setHeader("Content-Disposition","attachment; filename=\"meter_installed_status_details.pdf\"");   
				outuputStream2 = response.getOutputStream();
			}else if (command.trim().equalsIgnoreCase("meter_installed_status_summary")    )  
			{  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/new_report_summary.jasper");					  
				response.setHeader("Content-Disposition","attachment; filename=\"meter_installed_status_summary.pdf\"");   
				outuputStream2 = response.getOutputStream();
			}else if (command.trim().equalsIgnoreCase("FIN_YEAR_REPORT")    )
			{  
				
				
				int year1=Integer.parseInt(obj.setValue("pyear", request));
				System.out.println(year1);  
				int month_end=Integer.parseInt(obj.setValue("pmonth", request));
				//if (month_end <=3) year1=year1-1; 				
				int year2= (year1+1);;
				parameters.put("year1", Integer.toString(year1));
				parameters.put("year2", Integer.toString(year2));  	
				parameters.put("notmonth", month_end);     
				month_value_display=obj.month_val(obj.setValue("pmonth", request))+" "+year1;
				month_value_display+=" To "+obj.month_val(obj.setValue("pmonth1", request))+" "+year1;

				if (Office_id.equalsIgnoreCase("5000"))
					Office_id=obj.setValue("office_id", request);
				Office_name=obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
				obj.dis(Office_id);
				parameters.put("office_id", Office_id);  
				parameters.put("mvalue", "Financial Year  "+year1+"-"+year2);  
				parameters.put("office_name", Office_name);
				int month1_end =Integer.parseInt(obj.setValue("pmonth", request));
				String month_L=obj.month_List(month1_end) ;
				String month_value="";
				if (month1_end > 3 && month1_end <=12) 
					  month_value=obj.month_val(obj.setValue("pmonth", request))+" "+year1;
					else
					 month_value=obj.month_val(obj.setValue("pmonth", request))+" "+year2;  
				parameters.put("mvalue1", month_value);
				StringBuffer dynamic_query=new StringBuffer();
				dynamic_query.append(" select cr.CASHBOOK_YEAR,cr.CASHBOOK_MONTH,decode(cramt,null,0,cramt) as cramt,decode(dramt,null,0,dramt) as dramt,");
				dynamic_query.append(" decode(journal,null,0,journal) as journal,dcbdmd,decode(dcbcr,null,0,dcbcr) as dcbcr,");
				dynamic_query.append(" decode(dcbdr,null,0,dcbdr) as dcbdr,decode(collection,null,0,collection) as collection,");
				dynamic_query.append(" ( decode(dcbdmd,null,0,dcbdmd)-decode(journal,null,0,journal) ) as differ1,");
				dynamic_query.append(" (decode(cramt,null,0,cramt)- decode(dramt,null,0,dramt))-decode(collection,null,0,collection) as differ2,");
				dynamic_query.append(" CASE");
				dynamic_query.append(" WHEN cr.CASHBOOK_MONTH =1 THEN 'January' WHEN cr.CASHBOOK_MONTH =2 THEN 'February' WHEN cr.CASHBOOK_MONTH =3  THEN 'March'");
				dynamic_query.append(" WHEN cr.CASHBOOK_MONTH =4 THEN 'April'   WHEN cr.CASHBOOK_MONTH =5 THEN 'May'      WHEN cr.CASHBOOK_MONTH =6  THEN 'June'");
				dynamic_query.append(" WHEN cr.CASHBOOK_MONTH =7 THEN 'July'    WHEN cr.CASHBOOK_MONTH =8 THEN 'August'   WHEN cr.CASHBOOK_MONTH =9  THEN 'September'");
				dynamic_query.append(" WHEN cr.CASHBOOK_MONTH =10 THEN 'October' WHEN cr.CASHBOOK_MONTH =11  THEN 'November'  WHEN cr.CASHBOOK_MONTH =12   THEN 'December'");
				dynamic_query.append(" END AS mval"); 
				dynamic_query.append(" from");
				dynamic_query.append(" (");
				dynamic_query.append("  SELECT SUM(journal) AS journal ,CASHBOOK_MONTH, CASHBOOK_YEAR,  NEW_OFFICE_ID   AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" ( SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as journal,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_DR_VIEW");
				dynamic_query.append(" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 780401 and 780406 )");
				dynamic_query.append(" and sltypecode in (10,14)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy')>= to_date('1-4-"+year1+"','dd-mm-yyyy'))");
				dynamic_query.append(" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy'))");
				dynamic_query.append(" and CASHBOOK_MONTH  in ("+month_L+")");
				dynamic_query.append(" group by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH order by CASHBOOK_YEAR,CASHBOOK_MONTH ");
				dynamic_query.append(" )a");
				dynamic_query.append(" JOIN ");
				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID ="+Office_id);
				dynamic_query.append(" )b");
				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");
				dynamic_query.append(" )journal");
				dynamic_query.append(" right outer join");
				dynamic_query.append(" (");
				dynamic_query.append(" SELECT SUM(cramt) AS cramt ,CASHBOOK_MONTH, CASHBOOK_YEAR,NEW_OFFICE_ID    AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" ( SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as cramt,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_CR_VIEW");
				dynamic_query.append(" WHERE achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where COLLECTION_TYPE in (6,7))");
				dynamic_query.append(" and sltypecode in (10,14)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy'))");
				dynamic_query.append(" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy'))");
				dynamic_query.append("    and CASHBOOK_MONTH  in ("+month_L+")  group by ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR,CASHBOOK_MONTH  order by CASHBOOK_YEAR,CASHBOOK_MONTH");
				dynamic_query.append(" )a");
				dynamic_query.append(" JOIN ");     
				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID ="+Office_id);
				dynamic_query.append(" )b");
				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");				
				dynamic_query.append(" )cr");
				dynamic_query.append(" on cr.CASHBOOK_YEAR=journal.CASHBOOK_YEAR and cr.CASHBOOK_MONTH=journal.CASHBOOK_MONTH");
				dynamic_query.append(" left outer join");
				dynamic_query.append(" (");
				dynamic_query.append(" SELECT SUM(dramt) AS dramt ,CASHBOOK_MONTH, CASHBOOK_YEAR,NEW_OFFICE_ID    AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" (SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as dramt,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_DR_VIEW");
				dynamic_query.append(" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 782401 and 782406 )"); 
				dynamic_query.append("     AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy'))");
				dynamic_query.append(" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy'))");
				dynamic_query.append(" and CASHBOOK_MONTH  in ("+month_L+")   group by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH order by CASHBOOK_YEAR,CASHBOOK_MONTH");
				dynamic_query.append(" )a");
				dynamic_query.append(" JOIN ");
				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID ="+Office_id);
				dynamic_query.append(" )b");
				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");				

				dynamic_query.append(" )dr");
				dynamic_query.append(" on cr.CASHBOOK_YEAR=dr.CASHBOOK_YEAR and cr.CASHBOOK_MONTH=dr.CASHBOOK_MONTH");
				dynamic_query.append(" left outer join ");
				dynamic_query.append(" (");
				dynamic_query.append("  select YEAR,MONTH,  sum(DMD_FOR_MTH_WC) as dcbdmd, sum(ADD_CHARGES_WC)  as dcbdr,sum(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)   as collection  ,sum(MINUS_CHARGES_WC) as dcbcr from PMS_DCB_LEDGER_ACTUAL");
				dynamic_query.append(" where   (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy'))");
				dynamic_query.append(" and office_id="+Office_id+" AND (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy'))");
				dynamic_query.append(" and month  in ("+month_L+")  group by YEAR,MONTH  order by YEAR,MONTH");
				dynamic_query.append(" )dcb on dcb.YEAR=journal.CASHBOOK_YEAR and dcb.MONTH=journal.CASHBOOK_MONTH order by cr.CASHBOOK_YEAR,cr.CASHBOOK_MONTH");
				parameters.put("query", dynamic_query.toString());  
				 
				 System.out.println(dynamic_query.toString());
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_checking.jasper");
				ctxpath =request.getRequestURL().toString();		
				//path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_checking.jasper");
				parameters.put("ctxpath", ctxpath);    
				response.setHeader("Content-Disposition","attachment; filename=\"Fin_Year_Demand_Collection.pdf\"");
				outuputStream2 = response.getOutputStream();    
			}else if (command.trim().equalsIgnoreCase("FIN_YEAR_REPORT_HO")    )  
			{   
				int year1=Integer.parseInt(obj.setValue("pyear", request));
				System.out.println(obj.setValue("pyear", request));
				int month1_end =Integer.parseInt(obj.setValue("pmonth", request));
			//	if (month1_end <=3) year1=year1-1; 				
				int year2= (year1+1);;      
				parameters.put("year1", Integer.toString(year1)); 
				parameters.put("year2", Integer.toString(year2));						
				int month2_start=0,month2_end=0;
				parameters.put("month1_end", month1_end);    		
				parameters.put("month2_start", month2_start);    	
				parameters.put("month2_end", month2_end);    	
				parameters.put("notmonth", month1_end);   
				String month_L=obj.month_List(month1_end) ; 
				StringBuffer dynamic_query=new StringBuffer();
				dynamic_query.append("select off.office_id,office_name,decode(cramt,null,0,cramt) as cramt, ");
				dynamic_query.append(" decode(dramt,null,0,dramt) as dramt, ");
				dynamic_query.append(" decode(journal,null,0,journal) as journal,dcbdmd, ");
				dynamic_query.append(" decode(dcbcr,null,0,dcbcr) as dcbcr, ");
				dynamic_query.append(" decode(dcbdr,null,0,dcbdr) as dcbdr, ");
				dynamic_query.append("  decode(collection,null,0,collection) as collection ,  ");
				dynamic_query.append("  ( decode(dcbdmd,null,0,dcbdmd)-decode(journal,null,0,journal) ) as differ1,  ");
				dynamic_query.append(" (decode(cramt,null,0,cramt)- decode(dramt,null,0,dramt))-decode(collection,null,0,collection) as differ2  ");
				dynamic_query.append(" from (   ");
				dynamic_query.append(" select office_id ,office_name from com_mst_all_offices_view "); 
				dynamic_query.append(" where   office_id in (select office_id from PMS_DCB_DIV_DIST_MAP ) "); 
				dynamic_query.append(" )off  "); 
				dynamic_query.append(" left outer join  ");
				dynamic_query.append(" (   SELECT SUM(journal) as journal ,NEW_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" (");
				dynamic_query.append(" SELECT ACCOUNTING_FOR_OFFICE_ID,SUM(amount1) as journal FROM FAS_HEAD_SLTYPE_DR_VIEW  ");
				dynamic_query.append(" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 780401 and 780406 )  ");
				dynamic_query.append(" and sltypecode=10  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy')>= to_date('1-4-"+year1+"','dd-mm-yyyy'))   AND (to_date((01||'-'||CASHBOOK_MONTH||'-' ||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy')) ");
				dynamic_query.append(" and CASHBOOK_MONTH in ("+month_L+") ");
				dynamic_query.append("  group by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" order by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" )a JOIN ");
				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV ");
				dynamic_query.append(" )b ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID  GROUP BY NEW_OFFICE_ID ");			
				dynamic_query.append("   )journal ");
				dynamic_query.append(" on off.office_id=journal.ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" join ");
				dynamic_query.append(" (  SELECT SUM(cramt) as cramt ,NEW_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" (");				
				dynamic_query.append(" SELECT ACCOUNTING_FOR_OFFICE_ID,SUM(amount1) as cramt FROM FAS_HEAD_SLTYPE_CR_VIEW ");
				dynamic_query.append(" WHERE achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where COLLECTION_TYPE in (6,7) ) ");
				dynamic_query.append(" and sltypecode in (14,10)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy')) ");
				dynamic_query.append(" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy')) ");
				dynamic_query.append(" and CASHBOOK_MONTH in ("+month_L+")");
				dynamic_query.append(" group by ACCOUNTING_FOR_OFFICE_ID order by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" )a JOIN ");
				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV ");
				dynamic_query.append(" )b ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID  GROUP BY NEW_OFFICE_ID ");	
				dynamic_query.append(    " )cr ");
				dynamic_query.append(" on cr.ACCOUNTING_FOR_OFFICE_ID=off.office_id ");  
				dynamic_query.append(" left outer join ");
				dynamic_query.append(" ( SELECT SUM(dramt) as dramt ,NEW_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" (");	
				dynamic_query.append(" SELECT ACCOUNTING_FOR_OFFICE_ID,SUM(amount1) as dramt FROM FAS_HEAD_SLTYPE_DR_VIEW ");
				dynamic_query.append(" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 782401 and 782406 ) ");
				dynamic_query.append(" 				   and   (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy')) ");
				dynamic_query.append("   AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy')) ");
				dynamic_query.append(" and CASHBOOK_MONTH in ("+month_L+") ");
				dynamic_query.append( " group by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" order by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" )a JOIN ");
				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV ");
				dynamic_query.append(" )b ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID  GROUP BY NEW_OFFICE_ID ");				
				dynamic_query.append(   " )dr ");
				dynamic_query.append(" on off.office_id=dr.ACCOUNTING_FOR_OFFICE_ID "); 
				dynamic_query.append(" left outer join  ");
				dynamic_query.append(" ( ");
				dynamic_query.append(" select office_id, sum(DMD_FOR_MTH_WC) as dcbdmd, sum(ADD_CHARGES_WC)  as dcbdr,sum(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)  as collection  ,sum(MINUS_CHARGES_WC) as dcbcr from "); 
				dynamic_query.append(" PMS_DCB_LEDGER_ACTUAL ");
				dynamic_query.append(" where    (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy')) ");
				dynamic_query.append(" 					  and   (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy')) ");
				dynamic_query.append(" and month in ("+month_L+") ");
				dynamic_query.append(" group by office_id ");
				dynamic_query.append(" order by office_id ");
				dynamic_query.append(" )dcb on ");
				dynamic_query.append(" dcb.office_id=off.office_id  order by office_id");
				parameters.put("query", dynamic_query.toString());  
				System.out.println(dynamic_query.toString()); 
				month_value_display=obj.month_val(obj.setValue("pmonth", request))+" "+year1;
				month_value_display+=" To "+obj.month_val(obj.setValue("pmonth1", request))+" "+year1;
				String month_value="";
				if (month1_end > 3 && month1_end <=12) 
				  month_value=obj.month_val(obj.setValue("pmonth", request))+" "+year1;
				else
				 month_value=obj.month_val(obj.setValue("pmonth", request))+" "+year2;
				parameters.put("mvalue", "Financial Year  "+year1+"-"+year2);  
				parameters.put("mvalue1", month_value);
				
				//parameters.put("mvalue", " obj.month_val(obj.setValue("pmonth1", request))-"+year2);  
				parameters.put("office_name", Office_name);    				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_chk_HO.jasper");
				ctxpath =request.getRequestURL().toString();				  
				parameters.put("ctxpath", ctxpath);  
				response.setHeader("Content-Disposition","attachment; filename=\"DCB_FAS_dmd_collection_chk_HO.pdf\"");   
				outuputStream2 = response.getOutputStream();    
				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_chk_HO.jasper");
				ctxpath =request.getRequestURL().toString();				  
				parameters.put("ctxpath", ctxpath);  
				response.setHeader("Content-Disposition","attachment; filename=\"DCB_FAS_dmd_collection_chk_HO.pdf\"");   
				outuputStream2 = response.getOutputStream();    
			}else if (command.trim().equalsIgnoreCase("Private_PR") )  
			{ 
				parameters.put("year1", obj.setValue("pyear", request));
				int year2= Integer.parseInt(obj.setValue("pyear", request))+1;;
				parameters.put("year2", Integer.toString(year2));		
				parameters.put("finyear", obj.setValue("pyear", request)+"-"+Integer.toString(year2));
				int month1_end =Integer.parseInt(obj.setValue("pmonth", request));
				parameters.put("notmonth", month1_end);
				if (month1_end<=3)
				parameters.put("uptomonth", obj.month_val(Integer.toString(month1_end))+"   " +year2);
				else
				parameters.put("uptomonth", obj.month_val(Integer.toString(month1_end))+" " +obj.setValue("pyear", request));
				
				String cond_=(Integer.parseInt(Office_id)==5000)?"":" and office_id="+Office_id;
				String off_name=(Integer.parseInt(Office_id)==5000)?"":Office_name;
				parameters.put("off_name",off_name);
				String month_L=obj.month_List(month1_end) ;  
				StringBuffer dynamic_query=new StringBuffer();
				dynamic_query.append(" select sum(qty) as qty,ddesc from	( ");
				dynamic_query.append(" select ben.BENEFICIARY_TYPE_ID_SUB,(select a.BEN_TYPE_DESC from PMS_DCB_BEN_TYPE a where a.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID_SUB) as ddesc, ");
				dynamic_query.append(" ben.beneficiary_sno,	ben.beneficiary_name,ben.district_code,(select a.district_name from com_mst_districts a where a.district_code=ben.district_code) as distdesc,");
				dynamic_query.append(" decode(pr.qty,null,0,pr.qty) as qty  from (SELECT BENEFICIARY_TYPE_ID_SUB,");
				dynamic_query.append(" beneficiary_sno, beneficiary_name,  district_code	FROM PMS_DCB_MST_BENEFICIARY "); 
				dynamic_query.append(" WHERE status='L'	AND beneficiary_type_id_sub > 6	 "+cond_+"  ORDER BY beneficiary_type_id_sub ");
				dynamic_query.append(" )ben left outer join	(SELECT beneficiary_sno, SUM(QTY_CONSUMED_NET) as qty ");
				dynamic_query.append(" FROM PMS_DCB_TRN_MONTHLY_PR where (to_date((01||'-'||MONTH||'-'|| YEAR),'dd-MM-yyyy') >= to_date('1-4-"+obj.setValue("pyear", request)+"','dd-mm-yyyy')) ");
				dynamic_query.append(" AND (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy')) "); 
				dynamic_query.append(" and month<>0 and year<>0 and year  >  2012 and  month in ("+month_L+") "+cond_+" group by beneficiary_sno	)pr ");
				dynamic_query.append(" on pr.beneficiary_sno=ben.beneficiary_sno )	group by  ddesc ");
				parameters.put("query", dynamic_query.toString());
				System.out.println("queryyyyyyyyyyyyyyyyy"+dynamic_query.toString()); 
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/private_pumping_return_II.jasper");
				ctxpath =request.getRequestURL().toString();		
				//path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/private_pumping_return_II.jasper");
				parameters.put("ctxpath", ctxpath);    
				response.setHeader("Content-Disposition","attachment; filename=\"Private_Ben_Pumping_Return.pdf\"");
				outuputStream2 = response.getOutputStream();
			}
			
			else if (command.trim().equalsIgnoreCase("localbody_PR1") )  
			{ 
				parameters.put("year1", obj.setValue("pyear", request));
				int year2= Integer.parseInt(obj.setValue("pyear", request))+1;;
				parameters.put("year2", Integer.toString(year2));		
				parameters.put("finyear", obj.setValue("pyear", request)+"-"+Integer.toString(year2));
				int month1_end =Integer.parseInt(obj.setValue("pmonth", request));
				parameters.put("notmonth", month1_end);
				if (month1_end<=3)
				parameters.put("uptomonth", obj.month_val(Integer.toString(month1_end))+"   " +year2);
				else
				parameters.put("uptomonth", obj.month_val(Integer.toString(month1_end))+" " +obj.setValue("pyear", request));
				
				String cond_=(Integer.parseInt(Office_id)==5000)?"":" and office_id="+Office_id;
				String off_name=(Integer.parseInt(Office_id)==5000)?"":Office_name;
				parameters.put("off_name",off_name);
				String month_L=obj.month_List(month1_end) ;  
				StringBuffer dynamic_query=new StringBuffer();
				dynamic_query.append(" select SUM(qty)as qty,SUM(total)as total,ddesc from	( ");
				dynamic_query.append(" select ben.BENEFICIARY_TYPE_ID_SUB,(select a.BEN_TYPE_DESC from PMS_DCB_BEN_TYPE a where a.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID_SUB) as ddesc, ");
				dynamic_query.append(" ben.beneficiary_sno,	ben.beneficiary_name,ben.district_code,(select a.district_name from com_mst_districts a where a.district_code=ben.district_code) as distdesc,");
				dynamic_query.append(" decode(pr.qty,null,0,pr.qty) as qty, DECODE(pr.total,NULL,0,pr.total) AS total  from (SELECT BENEFICIARY_TYPE_ID_SUB,");
				dynamic_query.append(" beneficiary_sno, beneficiary_name,  district_code	FROM PMS_DCB_MST_BENEFICIARY "); 
				dynamic_query.append(" WHERE status='L'	AND beneficiary_type_id_sub between 1 and 6	 "+cond_+"  ORDER BY beneficiary_type_id_sub ");
				dynamic_query.append(" )ben left outer join	(SELECT beneficiary_sno, SUM(QTY_CONSUMED) AS qty,SUM(TOTAL_AMT) AS total ");
				dynamic_query.append(" FROM PMS_DCB_WC_BILLING where (to_date((01||'-'||MONTH||'-'|| YEAR),'dd-MM-yyyy') >= to_date('1-4-"+obj.setValue("pyear", request)+"','dd-mm-yyyy')) ");
				dynamic_query.append(" AND (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy')) "); 
				dynamic_query.append(" and month<>0 and year<>0 and year  >  2012 and  month in ("+month_L+") "+cond_+" group by beneficiary_sno	)pr ");
				dynamic_query.append(" on pr.beneficiary_sno=ben.beneficiary_sno )	group by  ddesc");
				parameters.put("query", dynamic_query.toString());
				System.out.println("queryyyyyyyyyyyyyyyyy"+dynamic_query.toString()); 
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/private_pumping_return_II.jasper");
				ctxpath =request.getRequestURL().toString();		
				//path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/private_pumping_return_II.jasper");
				parameters.put("ctxpath", ctxpath);    
				response.setHeader("Content-Disposition","attachment; filename=\"Private_Ben_Pumping_Return.pdf\"");
				outuputStream2 = response.getOutputStream();
			}
			
			else if (command.trim().equalsIgnoreCase("FIN_YEAR_REPORT_HO_MTH")    )  
			{ 
				parameters.put("year", obj.setValue("pyear", request));
				parameters.put("month", obj.setValue("pmonth", request));					  
				parameters.put("mvalue",obj.month_val(obj.setValue("pmonth", request)));  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_Mth_HO.jasper");
				ctxpath =request.getRequestURL().toString();		
				//path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_Mth_HO.jasper");
				parameters.put("ctxpath", ctxpath);    
				response.setHeader("Content-Disposition","attachment; filename=\"DCB_FAS_dmd_collection_Mth_HO.pdf\"");
				outuputStream2 = response.getOutputStream();    
			}
				else if (command.trim().equalsIgnoreCase("Division_OpeningBalance")    )  
			{  
				parameters.put("year", obj.setValue("pyear", request));
				parameters.put("month", obj.setValue("pmonth", request));					  
				parameters.put("mvalue",obj.month_val(obj.setValue("pmonth", request)));  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Opening_Balance.jasper");
				ctxpath =request.getRequestURL().toString();				  
				parameters.put("ctxpath", ctxpath);    
				response.setHeader("Content-Disposition","attachment; filename=\"Division_Opening_Balance.pdf\"");   
				outuputStream2 = response.getOutputStream();    
			}else if (command.trim().equalsIgnoreCase("Division_OpeningBalance_Details")    )  
			{  
				parameters.put("year", obj.setValue("pyear", request));
				parameters.put("month", obj.setValue("pmonth", request));					  
				parameters.put("mvalue",obj.month_val(obj.setValue("pmonth", request)));  
				Office_id=obj.setValue("office_id", request);
				Office_name=obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
				parameters.put("office_id", Office_id);
				parameters.put("office_name", Office_name);      				
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Opening_Balance_Details.jasper");
				ctxpath =request.getRequestURL().toString();				  
				parameters.put("ctxpath", ctxpath);  
				response.setHeader("Content-Disposition","attachment; filename=\"Division_Opening_Balance_Details.pdf\"");   
				outuputStream2 = response.getOutputStream();    
			}else if (command.trim().equalsIgnoreCase("FIN_YEAR_DCB")    )  
			{  
				parameters.put("year1", obj.setValue("pyear", request));
				int year2= Integer.parseInt(obj.setValue("pyear", request))+1;;
				parameters.put("year2", Integer.toString(year2));  				
				parameters.put("mvalue", "Financial Year : "+obj.setValue("pyear", request)+"-"+year2);  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Demand_Collection_Balance.jasper");  
				response.setHeader("Content-Disposition","attachment; filename=\"Demand_Collection_Balance.pdf\"");   
				outuputStream2 = response.getOutputStream();    
			} else if (command.trim().equalsIgnoreCase("FIN_YEAR_REPORT_DCB")    )  
			{  
				parameters.put("year1", obj.setValue("pyear", request));
				int year2= Integer.parseInt(obj.setValue("pyear", request))+1;;
				parameters.put("year2", Integer.toString(year2));
				month_value_display=obj.month_val(obj.setValue("pmonth", request))+" "+obj.setValue("pyear", request);
				month_value_display+=" To "+obj.month_val(obj.setValue("pmonth1", request))+" "+obj.setValue("pyear", request);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", "Financial Year : "+obj.setValue("pyear", request)+"-"+year2);  
				parameters.put("office_name", Office_name);  
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off_DCB.jasper");  
				response.setHeader("Content-Disposition","attachment; filename=\"Collection Stmt.pdf\"");   
				outuputStream2 = response.getOutputStream();    
			} else 	
				if (command.trim().equalsIgnoreCase("WCBenScheme")    )  
				{ 
					parameters.put("year1", obj.setValue("pyear", request));
					parameters.put("year2", obj.setValue("pyear1", request));
					parameters.put("month1", obj.setValue("pmonth", request));
					parameters.put("month2",  obj.setValue("pmonth1", request));

					if ( obj.setValue("pmonth", request).equalsIgnoreCase(obj.setValue("pmonth1", request)) 
							&& obj.setValue("pyear", request).equalsIgnoreCase(obj.setValue("pyear1", request)) )
					{
						month_value_display=obj.month_val(obj.setValue("pmonth", request))+"-"+obj.setValue("pyear", request);

					}else if ( obj.setValue("pmonth", request).equalsIgnoreCase(obj.setValue("pmonth1", request))
							&& !obj.setValue("pyear", request).equalsIgnoreCase(obj.setValue("pyear1", request)) 
					)
					{
						month_value_display=obj.month_val(obj.setValue("pmonth", request))+" "+obj.setValue("pyear", request);
						month_value_display+="-"+obj.month_val(obj.setValue("pmonth", request))+" "+obj.setValue("pyear1", request);
					}else
					{
						month_value_display=obj.month_val(obj.setValue("pmonth", request))+" "+obj.setValue("pyear", request);
						month_value_display+="-"+obj.month_val(obj.setValue("pmonth1", request))+" "+obj.setValue("pyear1", request);
					}
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", month_value_display);
					parameters.put("office_name", Office_name);
					String imagespath = getServletContext().getRealPath("/images/")+File.separator;
					parameters.put("imgpath", imagespath);
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Sch_Wc_new.jasper");  
					response.setHeader("Content-Disposition","attachment; filename=\"Ben_Sch_Wc_Pr.pdf\"");
					outuputStream2 = response.getOutputStream();    
				}else if (command.trim().equalsIgnoreCase("BenSchemePR")    )  
				{
					String imagespath = getServletContext().getRealPath("/images/")+File.separator;
					parameters.put("imgpath", imagespath);
					month= obj.isNull(obj.getValue("PMS_DCB_SETTING","MONTH", " where OFFICE_ID=" + Office_id), 1);
					year = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
					parameters.put("year", year);
					parameters.put("month", month);
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", obj.month_val(month));
					parameters.put("office_name", Office_name);
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Sch_Wc_Pr.jasper");
					response.setHeader("Content-Disposition","attachment; filename=\"Ben_Sch_Wc_new.pdf\"");
					outuputStream2 = response.getOutputStream();    
				} else if (command.trim().equalsIgnoreCase("supply_abstract")  )  
				{
					month= obj.setValue("month", request);
					year = obj.setValue("year", request);
					
					
					
					
					parameters.put("year", year);
					parameters.put("month", month);					
					parameters.put("mvalue", obj.month_val(month));

					if (Office_id.equalsIgnoreCase("5000"))
					{
						parameters.put("office_name", "");

						String off_id=obj.setValue("off_id", request);
						System.out.println(off_id);  
						parameters.put("office_id", off_id);
						if (off_id.equalsIgnoreCase("0")) {
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Supplied_Qty_review.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("Supplied_Qty_review.jasper"));
						}
						else
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Supplied_Qty_review_div.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("Supplied_Qty_review_div.jasper"));
						}
					}  
					else{
						parameters.put("office_id", Office_id);
						parameters.put("office_name", Office_name);
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Supplied_Qty_review_div.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("Supplied_Qty_review_div.jasper"));
					}

					response.setHeader("Content-Disposition","attachment; filename=\"Supplied_Qty_review.pdf\"");
					parameters.put("ctxpath", ctxpath);
					outuputStream2 = response.getOutputStream();    
				} else if (command.trim().equalsIgnoreCase("supply_details")  )  
				{
					month= obj.setValue("month", request);
					year = obj.setValue("year", request);
					parameters.put("year", year);
					parameters.put("month", month);					
					parameters.put("mvalue", obj.month_val(month));
					if (Office_id.equalsIgnoreCase("5000"))  
					{  
						parameters.put("office_name", "");						
						String off_id=obj.setValue("off_id", request);
						parameters.put("office_id", off_id);
						if (off_id.equalsIgnoreCase("0"))
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_private_indues.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("Ben_private_indues.jasper"));
						}
						else {
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_private_indues_div.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("Ben_private_indues_div.jasper"));
						}
					}  
					else{
						parameters.put("office_id", Office_id);
						parameters.put("office_name", Office_name);
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_private_indues_div.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("Ben_private_indues_div.jasper"));
					}  
					parameters.put("ctxpath", ctxpath);
					response.setHeader("Content-Disposition","attachment; filename=\"Supplied_Qty_review_Details.pdf\"");
					outuputStream2 = response.getOutputStream();    
				} else if (command.trim().equalsIgnoreCase("scheme_wise_demand_collection")  )  
				{
					month= obj.setValue("month", request);
					year = obj.setValue("year", request);
					parameters.put("year", year);
					parameters.put("office_name", Office_name);
					parameters.put("month", month);			
					parameters.put("office_id", Office_id);  
					parameters.put("mvalue", obj.month_val(month));
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/scheme_wise_dmd_collection.jasper");
					//response.setHeader("Content-Disposition","attachment; filename=\"scheme_wise_dmd_collection.pdf\"");
					outuputStream2 = response.getOutputStream();
				} else if (command.trim().equalsIgnoreCase("April_opening_balance")  )  
				{
					month= obj.setValue("month", request);
					year = obj.setValue("year", request);
					parameters.put("year", year);
					parameters.put("office_name", Office_name);
					parameters.put("month", month);			
					parameters.put("office_id", Office_id);     
					parameters.put("mvalue", obj.month_val(month));
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/ben_wise_april_ob.jasper");
					//response.setHeader("Content-Disposition","attachment; filename=\"scheme_wise_dmd_collection.pdf\"");
					outuputStream2 = response.getOutputStream();
				} else if (command.trim().equalsIgnoreCase("Date_Wise_Collection"))
				{
					parameters.put("date1", obj.setValue("date1", request));
					parameters.put("date2", obj.setValue("date2", request));
					
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Date_Wise_Collection.jasper");
					//response.setHeader("Content-Disposition","attachment; filename=\"scheme_wise_dmd_collection.pdf\"");
					outuputStream2 = response.getOutputStream();
				}else if (command.trim().equalsIgnoreCase("Date_Wise_Journal"))
				{
					parameters.put("date1", obj.setValue("date1", request)); 
					parameters.put("date2", obj.setValue("date2", request));     
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Date_Wise_Journal.jasper");
					//response.setHeader("Content-Disposition","attachment; filename=\"scheme_wise_dmd_collection.pdf\"");
					outuputStream2 = response.getOutputStream();
				}
			
				else if (command.trim().equalsIgnoreCase("Head_Office_Data1")  )  
				{
					int april_year=0;
					month= obj.setValue("month", request);
					year = obj.setValue("year", request);
					parameters.put("year", year);
					report_name="DCB Data ";
					if (Integer.parseInt(month) <= 3)
					{
						april_year=Integer.parseInt(year)-1;
					}else
					{
						april_year=Integer.parseInt(year);						
					}
					parameters.put("office_name", Office_name);
					parameters.put("april_year", Integer.toString(april_year));
					parameters.put("month", month);
					parameters.put("office_id", Office_id);    
					parameters.put("mvalue", obj.month_val(month));
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Head_office_data_Report.jasper");
					//response.setHeader("Content-Disposition","attachment; filename=\"scheme_wise_dmd_collection.pdf\"");
					outuputStream2 = response.getOutputStream();
				}  else if (command.trim().equalsIgnoreCase("Head_Office_DataTentative")  )  
				{
					int april_year=0;
					month= obj.setValue("month", request);
					year = obj.setValue("year", request);
					parameters.put("year", year);
					report_name="DCB Data ";
					if (Integer.parseInt(month) <= 3)
					{
						april_year=Integer.parseInt(year)-1;
					}else
					{
						april_year=Integer.parseInt(year);						
					}
					parameters.put("office_name", Office_name);
					parameters.put("april_year", Integer.toString(april_year));
					parameters.put("month", month);  
					parameters.put("office_id", Office_id);    
					parameters.put("mvalue", obj.month_val(month));
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Head_office_data_Report_tentat.jasper");
					//response.setHeader("Content-Disposition","attachment; filename=\"scheme_wise_dmd_collection.pdf\"");
					outuputStream2 = response.getOutputStream();
				} else if (command.trim().equalsIgnoreCase("Head_Office_DataTentative2")  )  
				{
					int april_year=0;
					month= obj.setValue("month", request);
					year = obj.setValue("year", request);
					parameters.put("year", year);
					report_name="DCB Data ";
					if (Integer.parseInt(month) <= 3)
					{
						april_year=Integer.parseInt(year)-1;
					}else
					{
						april_year=Integer.parseInt(year);						
					}
					parameters.put("office_name", Office_name);
					parameters.put("april_year", Integer.toString(april_year));
					parameters.put("month", month);  
					parameters.put("office_id", Office_id);    
					parameters.put("mvalue", obj.month_val(month));
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Div_wise_Report_tentat.jasper");
					//response.setHeader("Content-Disposition","attachment; filename=\"scheme_wise_dmd_collection.pdf\"");
					outuputStream2 = response.getOutputStream();
				}   
				else if (command.trim().equalsIgnoreCase("receipt_verificaiton_HO")  )  
				{
					year = obj.setValue("year", request);
					parameters.put("year", year);
					parameters.put("office_name", Office_name);
					parameters.put("office_id", Office_id);  
					parameters.put("year1", Integer.parseInt(year)+1);
					parameters.put("mvalue", year+"-"+(Integer.parseInt(year)+1));
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Receipt_Verification_Head_Office.jasper");
					//response.setHeader("Content-Disposition","attachment; filename=\"scheme_wise_dmd_collection.pdf\"");
					outuputStream2 = response.getOutputStream();
				}  
			jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);
			if (option.equalsIgnoreCase("0")) option="1";
			if (Integer.parseInt(option)==1)
				reportPdf(response, jasperPrint2, outuputStream2);
			else if(Integer.parseInt(option)==2)
				excelshow(response, jasperPrint2, outuputStream2);
			else  if(Integer.parseInt(option)==3)
				htmlshow(response, jasperPrint2, outuputStream2); 

			con.close();

		} catch (Exception e) 
		{
			response.sendRedirect("");
			e.printStackTrace();
		}	
	}

	protected void reportPdf(HttpServletResponse response,JasperPrint jasperPrint, OutputStream outuputStream2) 
	{
		
		System.out.println("Report Design Start"); 
		JasperPrint jasperPrint2 = null;
		try {
			JRExporter exporter = null;
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\""+ report_name + ".pdf" + "\"");
			exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
			exporter.exportReport();
			outuputStream2.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void excelshow(HttpServletResponse response,
			JasperPrint jasperPrint, OutputStream outuputStream) {
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=\""+ report_name + ".xls\"");
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
			exporterXLS.exportReport();
			byte[] bytes;
			bytes = xlsReport.toByteArray();
			outuputStream.write(bytes, 0, bytes.length);
			outuputStream.flush();
			outuputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void htmlshow(HttpServletResponse response, JasperPrint jasperPrint,
			OutputStream outuputStream) {
		try {
			JRExporter exporter = new JRHtmlExporter();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition","attachment; filename=\"Report.html\"");
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,"build/reports/BatchExportReport.html");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
			exporter.exportReport();
			outuputStream.close();
			outuputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
