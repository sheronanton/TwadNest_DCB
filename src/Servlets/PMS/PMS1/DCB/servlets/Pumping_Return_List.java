package Servlets.PMS.PMS1.DCB.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Pumping_Return_List extends HttpServlet 
{
	private static final long serialVersionUID =1L;
	private static final String CONTENT_TYPE ="text/xml; charset=windows-1252";
	Controller obj =null;
	Controller obj_rs =null;
	private ResultSet rs, rs2, rs3, jou_rs1, jou_ben, jou_rs3, rs_sch_type,rs_new =null;
	public Pumping_Return_List() 
	{
		super();
	}
	String new_cond =Controller.new_cond;
	String meter_status =Controller.meter_status;
	String meter_status2 =Controller.meter_status2;
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
			double amt =0.0, Cal_Excess =0.0;
			Statement st =null, st3 =null, st2 =null, st4 =null, st_new =null;
			int row_count =0;
			String xml ="<result>", str_qry ="", Office_id ="0", userid ="0", AC_UNIT_ID ="0", Actual_amt ="";
			response.setContentType(CONTENT_TYPE);
			Connection con =null;
			obj =new Controller();
			obj_rs =new Controller();
			NumberFormat formatter =new DecimalFormat("#0.00");		
			try {
				con =obj.con();
				obj.createStatement(con);
				st =con.createStatement();
				st3 =con.createStatement();
				st2 =con.createStatement();
				st4 =con.createStatement();
				st_new =con.createStatement();
			} catch (SQLException e3) {
				e3.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Map parameters =new HashMap();
			String cond ="(1=1)";
			String condbase ="";
			String msg ="-";
			String subcount ="";
			String sel_qry ="", ben_sno ="", sch_sno ="0", sch_type, path ="";
			String BILL_MONTH ="", BILL_YEAR ="";
			sel_qry ="";
			String Office_name =null;
			String month =obj.setValue("month", request);
			String year =obj.setValue("year", request);
			String process =obj.setValue("process", request);
			String process_code =obj.setValue("process_code", request);
			String divsno =obj.setValue("subdiv", request);
			String bentype =obj.setValue("bentype", request);
			String command =request.getParameter("command");// Command
			HttpSession session =request.getSession(false);
			obj.testQry("DCB------->Pumping_Return_List---->command( "+command+ " ) ------->process_code( "+process+" ) ");
			
			if (command ==null || command.equals(""))
				command ="no command";
	
			String bensno =request.getParameter("ben_sno");// Command ->view,insert
			String Region_="";
			String Region_ac_id="0";
			String current_ac_unit_id="0";
			if (bensno ==null || bensno.equals(""))
				bensno ="0";
			userid =(String) session.getAttribute("UserId");
			if (userid ==null) 
			{
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			try {
			//	Office_id =obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid+"')");
				Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

				AC_UNIT_ID =obj.getValue("FAS_MST_ACCT_UNITS","ACCOUNTING_UNIT_ID", "where ACCOUNTING_UNIT_OFFICE_ID="+ Office_id);
				current_ac_unit_id=AC_UNIT_ID;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	
			if (Office_id.equals(""))
				Office_id ="0";
			String oid =obj.setValue("oid", request);
			if (!oid.trim().equalsIgnoreCase("0"))
				Office_id =oid;
	
			CallableStatement proc_stmt =null;
			try {
				Office_name =obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID="+Office_id);
				Region_=obj.getValue("com_mst_all_offices_view", "Region_OFFICE_ID","where OFFICE_ID="+Office_id);
				Region_ac_id=obj.getValue("FAS_MST_ACCT_UNITS", "ACCOUNTING_UNIT_ID","where ACCOUNTING_UNIT_OFFICE_ID="+Region_);
			} catch (Exception e4) {
				e4.printStackTrace(); 
			}  
			String imagespath ="";
			if (command.equals("pdf")) 
			{
				try 
				{
					String mvalue =obj.month_val(obj.setValue("month", request));
					parameters.put("year", Integer.parseInt(obj.setValue("year",request)));
					parameters.put("month", Integer.parseInt(obj.setValue("month",request)));
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", mvalue);
					int option =Integer.parseInt(process_code);
					imagespath =getServletContext().getRealPath("/images/")+ File.separator;
					parameters.put("imgpath", imagespath);
					if (option ==1) 
					{
						path =getServletContext().getRealPath("/WEB-INF/ReportSrc/wc.jasper");
						JasperPrint jasperPrint2 =JasperFillManager.fillReport(path, parameters, con);
						byte[] bytes;
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment; filename=\"wc.pdf\"");
						ServletOutputStream outuputStream2 =response.getOutputStream();
						jasperPrint2 =JasperFillManager.fillReport(path,parameters, con);
						JRExporter exporter =null;
						exporter =new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
						exporter.exportReport();
						outuputStream2.close();
					} else {
						path =getServletContext().getRealPath("/WEB-INF/ReportSrc/wc.jasper");
						JasperPrint jasperPrint2 =JasperFillManager.fillReport(path, parameters, con);
						byte[] bytes;
						response.setContentType("application/vnd.ms-excel");
						response.setHeader("Content-Disposition","attachment;filename=\"wc.xls\"");
						JRXlsExporter exporterXLS =new JRXlsExporter();
						exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint2);
						ServletOutputStream outuputStream2 =response.getOutputStream();
						ByteArrayOutputStream xlsReport =new ByteArrayOutputStream();
						exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
						exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
						exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
						exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
						exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
						exporterXLS.exportReport();
						bytes =xlsReport.toByteArray();
						outuputStream2.write(bytes, 0, bytes.length);
						outuputStream2.flush();
						outuputStream2.close();
					}
				} catch (JRException e) 
				{
					throw new ServletException(e);
				}
			} else if (command.equals("waterchargejournalpdf")) 
			{
				try {
					response.setContentType("application/pdf");
					month =obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
					year =obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);
					String ctxpath =request.getRequestURL().toString();
					OutputStream outuputStream2 =null;
					JasperPrint jasperPrint2 =null;
					parameters.put("year", year);
					parameters.put("month", month);
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", obj.month_val(month));
					parameters.put("office_name", Office_name);
					parameters.put("ctxpath", ctxpath);
					imagespath =getServletContext().getRealPath("/images/")+File.separator;
					parameters.put("imgpath", imagespath);
					path =getServletContext().getRealPath("/WEB-INF/ReportSrc/Water_Charges_Calculation.jasper");
					response.setHeader("Content-Disposition","attachment; filename=\"Water_Charges_Calculation.pdf\"");
					outuputStream2 =response.getOutputStream();
					jasperPrint2 =JasperFillManager.fillReport(path, parameters,con);
					JRExporter exporter =null;
					exporter =new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				} catch (Exception e) {
					System.out.println(e);
				}
	
			} else if (command.equals("waterchargejournalpdf")) {
				try {
					response.setContentType("application/pdf");
					month =obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
					year =obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);
					String ctxpath =request.getRequestURL().toString();
					OutputStream outuputStream2 =null;
					JasperPrint jasperPrint2 =null;
					parameters.put("year", year);
					parameters.put("month", month);
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", obj.month_val(month));
					parameters.put("office_name", Office_name);
					parameters.put("ctxpath", ctxpath);
					imagespath =getServletContext().getRealPath("/images/")+ File.separator;
					parameters.put("imgpath", imagespath);
					path =getServletContext().getRealPath("/WEB-INF/ReportSrc/Water_Charges_Calculation.jasper");
					response.setHeader("Content-Disposition","attachment; filename=\"Water_Charges_Calculation.pdf\"");
					outuputStream2 =response.getOutputStream();
					jasperPrint2 =JasperFillManager.fillReport(path, parameters,con);
					JRExporter exporter =null;
					exporter =new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				} catch (Exception e) 
				{
					System.out.println(e);
				}
			} else if (command.equals("journalpdfreportnew")) 
			{
				try {
					response.setContentType("application/pdf");
					month =obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
					year =obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);
					OutputStream outuputStream2 =null;
					JasperPrint jasperPrint2 =null;
					parameters.put("office_id", Office_id);
					parameters.put("year", year);
					parameters.put("month", month);
					parameters.put("mvalue", obj.month_val(month));
					parameters.put("office_name", Office_name);
					path =getServletContext().getRealPath("/WEB-INF/ReportSrc/journal_report_details_new.jasper");
					String ctxpath =path.substring(0, path.lastIndexOf("journal_report_details_new.jasper"));
					parameters.put("ctxpath", ctxpath);
					imagespath =getServletContext().getRealPath("/images/")+ File.separator;
					parameters.put("imgpath", imagespath);
					response.setHeader("Content-Disposition","attachment; filename=\"journal_report_details.pdf\"");
					outuputStream2 =response.getOutputStream();
					jasperPrint2 =JasperFillManager.fillReport(path, parameters,con);
					JRExporter exporter =null;
					exporter =new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				} catch (Exception e) 
				{
					System.out.println(e);
				}
			} else if (command.equals("journalpdfreport")) 
			{
				try {
					response.setContentType("application/pdf");
					month =obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
					year =obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);
					OutputStream outuputStream2 =null;
					JasperPrint jasperPrint2 =null;
					parameters.put("office_id", Office_id);
					parameters.put("year", year);
					parameters.put("month", month);
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", obj.month_val(month));
					parameters.put("office_name", Office_name);
					imagespath =getServletContext().getRealPath("/images/")+ File.separator;
					parameters.put("imgpath", imagespath);
					parameters.put("schtype", obj.setValue("val", request));
					path =getServletContext().getRealPath("/WEB-INF/ReportSrc/journal_report_details.jasper");
					response.setHeader("Content-Disposition","attachment; filename=\"journal_report_details.pdf\"");
					outuputStream2 =response.getOutputStream();
					jasperPrint2 =JasperFillManager.fillReport(path, parameters,con);
					JRExporter exporter =null;
					exporter =new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				} catch (Exception e) 
				{
					System.out.println(e);
				}
				
				
				
				
				
				
	
			}
			
			else if (command.equals("show")&&(process.equals("4")))
					{
					
				System.out.println("APSK1");
				
				try {
					response.setContentType("application/pdf");
					month =obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
					year =obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);
					OutputStream outuputStream2 =null;
					JasperPrint jasperPrint2 =null;
					parameters.put("office_id", Office_id);
					parameters.put("year", year);
					parameters.put("month", month);
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", obj.month_val(month));
					parameters.put("office_name", Office_name);
					imagespath =getServletContext().getRealPath("/images/")+ File.separator;
					parameters.put("imgpath", imagespath);
					parameters.put("schtype", obj.setValue("val", request));
					path =getServletContext().getRealPath("/WEB-INF/ReportSrc/journal_report_details.jasper");
					response.setHeader("Content-Disposition","attachment; filename=\"journal_report_details.pdf\"");
					outuputStream2 =response.getOutputStream();
					jasperPrint2 =JasperFillManager.fillReport(path, parameters,con);
					JRExporter exporter =null;
					exporter =new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				} catch (Exception e) 
				{
					System.out.println(e);
				}
				
				System.out.println("APSK2");
				
				
					}
			
			
			
			else {
				if (command.equals("WCUFR")) {
					xml ="<result>";
					PreparedStatement ps1 =null;
					String query ="";
					int ufr1 =0;
					int JOURNAL_POSTED =0;
					try 
					{
						
						
						JOURNAL_POSTED =obj.getCount("PMS_DCB_FREEZE_STATUS","where OFFICE_ID="+ Office_id+ " and  CASHBOOK_MONTH="
												+ month+ " and  CASHBOOK_YEAR="+ year
												+ " and (JOURNAL_POSTED <> 'Y' or JOURNAL_POSTED <> 'y')");
						
						
					} catch (Exception e1) 
					{
						e1.printStackTrace();
					}
	
					
					String rid=obj.accountUID(Integer.parseInt(Office_id));
					String temp_off=" ";
					if (!rid.equalsIgnoreCase("0")) temp_off="where OFFICE_ID="+ Office_id; else temp_off="where ACTUAL_OFFICE_ID="+ Office_id;
					
					if (JOURNAL_POSTED ==0) 
					{
						try {
							ufr1 =obj.getCount("PMS_DCB_FREEZE_STATUS",temp_off
													+ " "+ " and  CASHBOOK_MONTH="+ month
													+ " and  CASHBOOK_YEAR="+ year+ ""
													+ " and ( WC_FREEZED='Y' or WC_FREEZED='y' )");
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						if (ufr1 > 0) 
						{
							query ="update PMS_DCB_FREEZE_STATUS set WC_FREEZED='CR' "+temp_off//where OFFICE_ID="+ Office_id
									+ " and  CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year
									+ " and  JOURNAL_POSTED  is null  and   WC_FREEZED='Y'";
							try 
							{
								ps1 =con.prepareStatement(query);
								int record_c1 =ps1.executeUpdate();
								xml +="<prflag>1</prflag><record_c1>"+record_c1+"</record_c1>";
								Hashtable cols =new Hashtable();
								cols.put("PR_FREEZED", "'CR'");
								cols.put("PR_UNFREEZE_DATE", "clock_timestamp()");
								Hashtable condht =new Hashtable();
								condht.put("YEAR", year);
								condht.put("MONTH", month);
								condht.put("OFFICE_ID", Office_id);
									
								try {
									int res_va =obj.recordSave(cols, condht,"PMS_DCB_PR_FREEZE", con);
								} catch (SQLException e) 
								{
									e.printStackTrace();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						} else {
							int alread_rc =0;
							try {
								alread_rc =obj.getCount("PMS_DCB_FREEZE_STATUS",temp_off
									//	"  where OFFICE_ID="+Office_id
												+ " and  CASHBOOK_MONTH="+month
												+ " and  CASHBOOK_YEAR="+year
												+ " and  WC_FREEZED='CR'");
							} catch (Exception e1) {
								e1.printStackTrace();
							}
	
							if (alread_rc > 0)
								xml +="<prflag>2</prflag><record_c1>0</record_c1>";
							else
								xml +="<prflag>3</prflag><record_c1>0</record_c1>";
						}
	
					} else {
						xml +="<prflag>4</prflag><record_c1>0</record_c1>";
					}
	
				}
	
				if (command.equals("benschwise")) {
					xml ="<result>";
					xml +="</result>";
	
				}
	
				if (command.equals("UFR")) {
					// Un Freeze Process Start
					PreparedStatement ps =null;
					int unfr_val_flag =0;
					int off_pr_count =0;
					try {
						off_pr_count =obj
								.getCount(
										"PMS_DCB_TRN_MONTHLY_PR",
										" where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "
												+ new_cond+" OFFICE_ID="+Office_id+") and MONTH="
												+ month
												+ " and  YEAR="
												+ year
												+ " and OFFICE_ID="+Office_id);
						unfr_val_flag =obj
								.getCount(
										"PMS_DCB_TRN_MONTHLY_PR",
										" where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "
												+ new_cond
												+ " OFFICE_ID="
												+ Office_id
												+ ") and MONTH="
												+ month
												+ " and  YEAR="
												+ year
												+ " and  PROCESS_FLAG='FR' and OFFICE_ID="
												+ Office_id);
	
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	
					xml ="<result>";
					// if (off_pr_count ==unfr_val_flag)
					// {
					String ins_qry ="";
	
					try {
						// Journal Status if record_in_FREEZE_STATUS !=0 Pumping
						// Cannot UnFreeze
						// Journal Status if record_in_FREEZE_STATUS =0 Pumping Can
						// UnFreeze
	
						String acid=obj.accountUID(Integer.parseInt(Office_id));
						String tmp_str=" ";
						if (acid.equalsIgnoreCase("0")) 
							tmp_str=" and ACTUAL_OFFICE_ID="+Office_id;
						else
							tmp_str=" and OFFICE_ID="+Office_id;
						int record_in_FREEZE_STATUS =obj
								.getCount(
										"PMS_DCB_FREEZE_STATUS",
										"where CASHBOOK_MONTH="
												+ month
												+ " and CASHBOOK_YEAR="
												+ year
												+ " and ( JOURNAL_POSTED='Y' or JOURNAL_POSTED='y' )   " +tmp_str);
														//" and OFFICE_ID="+Office_id);
	
						int record_c1 =0, record_c2 =0, record_c3 =0, record_c4 =0, record_c5 =0, record_c6 =0, record_c7 =0;
						int srow =0;
						if (record_in_FREEZE_STATUS ==0) {
							srow =1;
							/*
							 * ins_qry =
							 * "delete from PMS_DCB_FREEZE_STATUS where OFFICE_ID=? and CASHBOOK_MONTH=? and  CASHBOOK_YEAR=? and "
							 * +
							 * "(JOURNAL_POSTED <> 'Y' or JOURNAL_POSTED <> 'y' or JOURNAL_POSTED is null )"
							 * ;
							 * 
							 * ps =con.prepareStatement(ins_qry); ps.setInt(1,
							 * Integer.parseInt(Office_id)); ps.setInt(2,
							 * Integer.parseInt(month)); ps.setInt(3,
							 * Integer.parseInt(year)); record_c1 =
							 * ps.executeUpdate();
							 */
							Hashtable cols_ =new Hashtable();
							cols_.put("WC_FREEZED", "''");
							cols_.put("WC_UNFREEZED_DATE", "clock_timestamp()");
							Hashtable condht_ =new Hashtable();
							condht_.put("CASHBOOK_YEAR", year);
							condht_.put("CASHBOOK_MONTH", month);
							condht_.put("ACTUAL_OFFICE_ID", "'"+Office_id+"'");
						 
							
							if (acid.equalsIgnoreCase("0"))
							condht_.put("OFFICE_ID", Region_);	
							else
								condht_.put("OFFICE_ID", Office_id);	
							try {
								int res_va =obj.recordSave(cols_, condht_,
										"PMS_DCB_FREEZE_STATUS", con);
							} catch (SQLException e) {
								e.printStackTrace();
							}
	
							// Delete PMS_DCB_PR_FREEZE Table Values
							/*
							 * ps =null; ins_qry =
							 * "delete from PMS_DCB_PR_FREEZE where OFFICE_ID=? and MONTH=? and YEAR=?"
							 * ; ps =con.prepareStatement(ins_qry); ps.setInt(1,
							 * Integer.parseInt(Office_id)); ps.setInt(2,
							 * Integer.parseInt(month)); ps.setInt(3,
							 * Integer.parseInt(year)); record_c2 =
							 * ps.executeUpdate();
							 */
							Hashtable cols =new Hashtable();
							cols.put("PR_UNFREEZE_DATE", "clock_timestamp()");
							Hashtable condht =new Hashtable();
							condht.put("YEAR", year);
							condht.put("MONTH", month);
							condht.put("OFFICE_ID", Office_id);
							try {
								int res_va =obj.recordSave(cols, condht,
										"PMS_DCB_PR_FREEZE", con);
							} catch (SQLException e) {
								e.printStackTrace();
							}
	
							// Delete PMS_DCB_JOURNAL_DETAILS Table Values
							ps =null;
							ins_qry ="delete from PMS_DCB_JOURNAL_DETAILS where OFFICE_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=?";
							ps =con.prepareStatement(ins_qry);
							
							ps.setInt(1, Integer.parseInt(Office_id));
							
							
							ps.setInt(2, Integer.parseInt(month));
							ps.setInt(3, Integer.parseInt(year));
							record_c2 =ps.executeUpdate();
	
							// Delete PMS_DCB_WC_BILLING Table Values
							ps =null;
							ins_qry ="delete from PMS_DCB_WC_BILLING where OFFICE_ID=? and MONTH=? and YEAR=? ";
							ps =con.prepareStatement(ins_qry);
							ps.setInt(1, Integer.parseInt(Office_id));
							ps.setInt(2, Integer.parseInt(month));
							ps.setInt(3, Integer.parseInt(year));
							record_c3 =ps.executeUpdate();
	
							// Delete PMS_DCB_WC_SCHEME Table Values
							ps =null;
							ins_qry ="delete from PMS_DCB_WC_SCHEME where OFFICE_ID=? and MONTH=? and YEAR=?";
							ps =con.prepareStatement(ins_qry);
							ps.setInt(1, Integer.parseInt(Office_id));
							ps.setInt(2, Integer.parseInt(month));
							ps.setInt(3, Integer.parseInt(year));
							record_c4 =ps.executeUpdate();
	
							// Delete PMS_DCB_WC_SLAB Table Values
							ps =null;
							ins_qry ="delete from PMS_DCB_WC_SLAB where OFFICE_ID=? and MONTH=? and YEAR=?";
							ps =con.prepareStatement(ins_qry);
							ps.setInt(1, Integer.parseInt(Office_id));
							ps.setInt(2, Integer.parseInt(month));
							ps.setInt(3, Integer.parseInt(year));
							record_c5 =ps.executeUpdate();
	
							// Set V flag of Pumping Return Ben Wise Table
	
							ps =null;
							ins_qry =" update PMS_DCB_MONTHLY_PR  "
									+ " set PROCESS_FLAG='V' "
									+ " where BENEFICIARY_SNO in ( "
									+ "    select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "
									+ new_cond+"  OFFICE_ID =? "+"	  ) and "
									+ " MONTH=? and "+"YEAR=? ";
							ps =con.prepareStatement(ins_qry);
							ps.setInt(1, Integer.parseInt(Office_id));
							ps.setInt(2, Integer.parseInt(month));
							ps.setInt(3, Integer.parseInt(year));
							record_c6 =ps.executeUpdate();
	
							ps =null;
							ins_qry =" update PMS_DCB_TRN_MONTHLY_PR "
									+ " set PROCESS_FLAG='V' "
									+ " where BENEFICIARY_SNO in "
									+ " (  select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "
									+ new_cond+"  OFFICE_ID =? ) and "
									+ " MONTH=? and "+"YEAR=? ";
							ps =con.prepareStatement(ins_qry);
							ps.setInt(1, Integer.parseInt(Office_id));
							ps.setInt(2, Integer.parseInt(month));
							ps.setInt(3, Integer.parseInt(year));
							record_c7 =ps.executeUpdate();
						}
	
						xml +="<srow>"+srow+"</srow>"+"<frow>"
								+ record_in_FREEZE_STATUS+"</frow>"
								+ "<record_c1>"+record_c1+"</record_c1>"
								+ "<record_c2>"+record_c2+"</record_c2>"
								+ "<record_c3>"+record_c3+"</record_c3>"
								+ "<record_c4>"+record_c4+"</record_c4>"
								+ "<record_c5>"+record_c5+"</record_c5>"
								+ "<record_c6>"+record_c6+"</record_c6>"
								+ "<record_c7>"+record_c7+"</record_c7>"
								+ "<sflag>0</sflag>";
					} catch (Exception e) {
						e.printStackTrace();
					}
	
					/*
					 * } else { xml +="<srow>0</srow>"+"<frow>1</frow>" +
					 * "<record_c1>0</record_c1>"+"<record_c2>0</record_c2>" +
					 * "<record_c3>0</record_c3>"+"<record_c4>0</record_c4>" +
					 * "<record_c5>0</record_c5>"+"<record_c6>0</record_c6>" +
					 * "<record_c7>0</record_c7>"+"<sflag>1</sflag>"; }
					 */
					xml +="</result>";
				} else {
	
					if (command.equals("journal_report")) {
						try {
							String off_check="0";
							String flag =obj.setValue("flag", request);
							System.out.println(" AC_UNIT_ID ="+AC_UNIT_ID);
							if (AC_UNIT_ID.equalsIgnoreCase("0"))
								off_check="  AND ACTUAL_OFFICE_ID="+Office_id;
							else  
								off_check="  AND OFFICE_ID="+Office_id;
							
							if (Integer.parseInt(flag) ==1) {
	
								sel_qry ="SELECT DR.dramt,"
										+ "  CR.cramt,"
										+ "  SCH.SCH_TYPE_DESC,"
										+ "  DR.SCHEME_TYPE_ID,"
										+ "  FAS.ACCOUNTING_UNIT_NAME,"
										+ "  FAS.ACCOUNTING_UNIT_ID	"
										+ " FROM ("
										+ " ( SELECT SUM(AMOUNT) AS dramt, " // Get
																				// DR
																				// Amount
																				// From
																				// Journal
										+ "       CASHBOOK_MONTH,"
										+ "       CASHBOOK_YEAR,"
										+ "       OFFICE_ID,"
										+ "       SCHEME_TYPE_ID "
										+ "   FROM PMS_DCB_JOURNAL_DETAILS "
										+ "   WHERE CR_DR_INDICATOR='DR' "+off_check									
										//+ "      AND OFFICE_ID="+ Office_id
										+ "      AND CASHBOOK_MONTH="
										+ month
										+ "      AND CASHBOOK_YEAR="
										+ year
										+ "   GROUP BY OFFICE_ID,"
										+ "      SCHEME_TYPE_ID,"
										+ "      CASHBOOK_MONTH,"
										+ "      CASHBOOK_YEAR "
										+ ")DR "
										+ " JOIN "
										+ "(  SELECT SUM(AMOUNT) AS cramt," // Get
																			// CR
																			// Amount
																			// From
																			// Journal
										+ "          CASHBOOK_MONTH,"
										+ "          CASHBOOK_YEAR,"
										+ "          OFFICE_ID,ACCOUNTING_UNIT_ID,"
										+ "          SCHEME_TYPE_ID"
										+ "   FROM PMS_DCB_JOURNAL_DETAILS"
										+ "   WHERE CR_DR_INDICATOR='CR' "+off_check
										//+ "       AND OFFICE_ID="+ Office_id
										+ "       AND CASHBOOK_MONTH="
										+ month
										+ "       AND CASHBOOK_YEAR="
										+ year
										+ "   GROUP BY OFFICE_ID,"
										+ "            SCHEME_TYPE_ID,"
										+ "            CASHBOOK_MONTH,ACCOUNTING_UNIT_ID,"
										+ "            CASHBOOK_YEAR"
										+ " )CR "
										+ "  ON DR.OFFICE_ID=CR.OFFICE_ID and  DR.SCHEME_TYPE_ID=CR.SCHEME_TYPE_ID "
										+ " JOIN "
										+ "( SELECT SCH_TYPE_DESC, SCH_TYPE_ID FROM PMS_SCH_LKP_TYPE "
										+ ")SCH "
										+ " ON SCH.SCH_TYPE_ID=DR.SCHEME_TYPE_ID"
										+ " left join ("
										+ " select ACCOUNTING_UNIT_ID,"
										+ " ACCOUNTING_UNIT_NAME,ACCOUNTING_UNIT_OFFICE_ID "
										+ " from FAS_MST_ACCT_UNITS  )FAS"
										+ " on FAS.ACCOUNTING_UNIT_ID=CR.ACCOUNTING_UNIT_ID"
										+ " )";
								System.out.println(sel_qry);
								xml ="<result>";
								rs =st.executeQuery(sel_qry);
								while (rs.next()) 
								{
									int flag_new=0;								
									if (Double.parseDouble(obj.isNull(rs.getString(1), 1))==Double.parseDouble(obj.isNull(rs.getString(2), 1)))
										flag_new=0;
										else  
											flag_new=1;
										 
									xml +="<dramt>"+formatter.format(Double.parseDouble(obj.isNull(rs.getString(1), 1)))+ "</dramt>";
									xml +="<cramt>"+ formatter.format(Double.parseDouble(obj.isNull(rs.getString(2), 1)))+ "</cramt>";
									xml +="<flag_new>"+ flag_new+ "</flag_new>";
									xml +="<SCH_TYPE_DESC>"+ obj.isNull(rs.getString(3), 1)+ "</SCH_TYPE_DESC>";
									xml +="<SCH_TYPE_ID>"+ obj.isNull(rs.getString(4), 1)+ "</SCH_TYPE_ID>";
									xml +="<ACCOUNTING_UNIT_NAME>"+ obj.isNull(rs.getString(6), 3)+ "</ACCOUNTING_UNIT_NAME>";
	
								}
							} else if (Integer.parseInt(flag) ==3) 
							{
								String qry ="";							
								if (AC_UNIT_ID.equalsIgnoreCase("0"))
								{
									sel_qry ="	  SELECT ACCOUNTING_UNIT_ID,"
										+ "	ACCOUNTING_UNIT_NAME," 
										+ "	 ACCOUNTING_UNIT_OFFICE_ID"
										+ "	FROM FAS_MST_ACCT_UNITS"
										+ "	WHERE ACCOUNTING_UNIT_OFFICE_ID ="
										+ Region_;
			
										rs_sch_type =st2.executeQuery(sel_qry);
										if (rs_sch_type.next()) 
										{
											AC_UNIT_ID =obj.isNull(rs_sch_type.getString(1), 1);
											 
										}
										qry ="update PMS_DCB_FREEZE_STATUS set ACTUAL_OFFICE_ID="+Office_id+", OFFICE_ID="+Region_+" ,WC_FREEZED ='Y' ,WC_FREEZED_DATE=clock_timestamp() ,ACCOUNTING_UNIT_ID=";
										qry+=AC_UNIT_ID+" where  ACTUAL_OFFICE_ID="+ Office_id+" and  CASHBOOK_MONTH="+Integer.parseInt(month)+" and  CASHBOOK_YEAR="+Integer.parseInt(year);
								}
								else 
								{
										qry ="update PMS_DCB_FREEZE_STATUS set ACTUAL_OFFICE_ID="+Office_id+", WC_FREEZED ='Y' ,WC_FREEZED_DATE=clock_timestamp() ,ACCOUNTING_UNIT_ID=";
										qry+=AC_UNIT_ID+" where  OFFICE_ID="+ Office_id+" and  CASHBOOK_MONTH="+Integer.parseInt(month)+" and  CASHBOOK_YEAR="+Integer.parseInt(year);
								}
								xml ="<result>";
								 
								System.out.println(qry);
								int rsrow =st.executeUpdate(qry);
								if (rsrow > 0)
									xml +="<msg> Water Charges Calculation  Freezed  !!! </msg>";
								else
									xml +="<msg> Water Charges Calculation Not Freezed  !!!</msg>";
	
								// PR_FREEZED set Y to PMS_DCB_PR_FREEZE 04/08/2011
	
								Hashtable cols =new Hashtable();
								cols.put("PR_FREEZED", "'Y'");
	
								Hashtable condht =new Hashtable();
								condht.put("YEAR", year);
								condht.put("MONTH", month);
								condht.put("OFFICE_ID", Office_id);
								try {
									int res_va =obj.recordSave(cols, condht,
											"PMS_DCB_PR_FREEZE", con);
								} catch (SQLException e) {
									e.printStackTrace();
								}
	
							} else
	
							{
								String schtype =obj.setValue("schtype", request);
								sel_qry ="select "
										+ " JOU.ACCOUNT_HEAD_CODE,JOU.CR_DR_INDICATOR,SCH.SCH_NAME, AMAS.ACCOUNT_HEAD_DESC,"
										+ " JOU.SCHEME_TYPE_ID, JOU.OFFICE_ID,JOU.AMOUNT, JOU.SUB_LEDGER_CODE,"
										+ " JOU.REMARKS "
										+ " from (  "
										+ " ("
										+ " SELECT AMOUNT,"
										+ "OFFICE_ID,"
										+ "SCHEME_TYPE_ID,"
										+ " ACCOUNT_HEAD_CODE,"
										+ "  CR_DR_INDICATOR,SUB_LEDGER_CODE,REMARKS "
										+ " FROM PMS_DCB_JOURNAL_DETAILS"
										+ " WHERE  OFFICE_ID="
										+ Office_id
										+ " AND CASHBOOK_MONTH="
										+ month
										+ " AND CASHBOOK_YEAR="
										+ year
										+ " AND SCHEME_TYPE_ID="
										+ schtype
										+ " ORDER BY CR_DR_INDICATOR DESC,OFFICE_ID,"
										+ " SCHEME_TYPE_ID,"
										+ " CASHBOOK_MONTH,"
										+ " CASHBOOK_YEAR"
										+ ") JOU "
										+ "LEFT JOIN "
										+ "  ( SELECT  PROJECT_ID, SCH_SNO FROM PMS_MST_PROJECTS_VIEW where status='L' and office_id="+Office_id+"  ) "
										+ " prv ON prv.PROJECT_ID=JOU.SUB_LEDGER_CODE  "
										+ " left join "
										+ ""
										+ "("
										+ " select "
										+ "   SCH_NAME,"
										+ "    SCH_SNO"
										+ " from"
										+ "      PMS_SCH_MASTER"
										+ "       "
										+ ")SCH  "
										+ " on SCH.SCH_SNO=prv.SCH_SNO"
										+ " "
										+ " join "
										+ "("
										+ "select"
										+ "    ACCOUNT_HEAD_CODE,"
										+ "     ACCOUNT_HEAD_DESC "
										+ " from  "
										+ "COM_MST_ACCOUNT_HEADS "
										+ ""
										+ ") AMAS "
										+ " on AMAS.ACCOUNT_HEAD_CODE=JOU.ACCOUNT_HEAD_CODE"
										+
	
										")";
	
								rs =st.executeQuery(sel_qry);
								while (rs.next()) {
									String amt_cal =obj.isNull(rs.getString(7), 1);
									if (!amt_cal.equalsIgnoreCase("0")) 
									{
										xml +="<ACCOUNT_HEAD_CODE>"+ obj.isNull(rs.getString(1), 3)+ ""+" - "+ obj.isNull(rs.getString(4), 3)+ ""+"</ACCOUNT_HEAD_CODE>";
										xml +="<CR_DR_INDICATOR>"+ obj.isNull(rs.getString(2), 1)+ "</CR_DR_INDICATOR>";xml +="<ACCOUNT_HEAD_DESC>"+ obj.isNull(rs.getString(4), 3)+ "</ACCOUNT_HEAD_DESC>";
										xml +="<AMOUNT>"+ formatter.format(Double.parseDouble(amt_cal))+ "</AMOUNT>";
										xml +="<SCH_TYPE_ID>"+ obj.isNull(rs.getString(5), 1)+ "</SCH_TYPE_ID>";
										xml +="<REMARKS>"+ obj.isNull(rs.getString(9), 1)+ "</REMARKS>";
										xml +="<SCH_NAME><![CDATA["+ obj.isNull(rs.getString(3), 3)+ "]]></SCH_NAME>";
										//+obj.isNull((rs.getString(8).equals("0")
										// ? "" : ""+rs.getString(8)), 3)+"  "+
										// "</SCH_NAME>";
	
										xml +="<Projectid>"+ obj.isNull((rs.getString(8).equals("0") ? "0" : ""+ rs.getString(8)), 3)+ "  "+"</Projectid>";
									}
								}
							}
						} catch (SQLException e) {
							// Auto-generated catch block
							e.printStackTrace();
						}
					}
					int jrow =0;
					if (command.equals("journal")) {
						try {
	
							String qty ="0", eqty ="0";
							amt =0.0;
							String SCHEME_SNO ="0", SCH_TYPE_ID ="0", sub_vaules ="0";
							sel_qry ="select sum(TOTAL_AMT),SCHEME_SNO,SCH_TYPE_ID from PMS_DCB_WC_BILLING where OFFICE_ID="+Office_id+" and month="+ month+ " and year="+ year+ " group by SCHEME_SNO,SCH_TYPE_ID";
							try {
								jou_rs3 =st2.executeQuery(sel_qry);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
	
							try {
								while (jou_rs3.next()) 
								{
									amt =Double.parseDouble(obj.isNull(jou_rs3.getString(1), 2));
									SCHEME_SNO =obj.isNull(jou_rs3.getString(2), 2);
									SCH_TYPE_ID =obj.isNull(jou_rs3.getString(3),2);
									String sub_ins ="insert into PMS_DCB_WC_SCHEME("
											+ "WC_SCH_TRN_SNO"
											+ ",OFFICE_ID"
											+ ",MONTH"
											+ ",YEAR"
											+ ",AMT"
											+ ",SCHEME_SNO"
											+ ",SCH_TYPE_ID"
											+ ",UPDATED_BY_USER_ID"
											+ ",UPDATED_DATE"+")";
	
									int WC_SCH_TRN_SNO =0;
									try {
										WC_SCH_TRN_SNO =obj.getMax("PMS_DCB_WC_SCHEME","WC_SCH_TRN_SNO", "");
									} catch (SQLException e) 
									{
										e.printStackTrace();
									}
	
									sub_vaules ="values  ("+WC_SCH_TRN_SNO+","+ Office_id+""+","+month+""+ ","+year+""+","
											+ Math.round(amt)+""+","
											+ SCHEME_SNO+""+","+SCH_TYPE_ID
											+ ""+",'"+userid+"'"
											+ ",clock_timestamp()"+")";
	
									int sch_count =0;
	
									try {
										sch_count =obj.getCount(
												"PMS_DCB_WC_SCHEME",
												" where  OFFICE_ID="+Office_id
														+ "  and  MONTH ="+month
														+ "  and YEAR="+year
														+ "  and SCHEME_SNO="
														+ SCHEME_SNO);
	
									} catch (Exception e) {
										System.out.println("TEST - ERROR"+e);
									}
	
									PreparedStatement ps_ins =null;
									try {
										ps_ins =con.prepareStatement(sub_ins
												+ sub_vaules);
									} catch (SQLException e) {
										e.printStackTrace();
									}
	
									int t_row =0;
									if (sch_count ==0)
										try {
											t_row =ps_ins.executeUpdate();
										} catch (SQLException e) {
											e.printStackTrace();
										}
	
								}
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								e.printStackTrace();
							}
	
							String ACCOUNTING_UNIT_ID ="0", ACCOUNTING_UNIT_OFFICE_ID ="0";
							
							 
							sel_qry ="	  SELECT ACCOUNTING_UNIT_ID,"
									+ "	ACCOUNTING_UNIT_NAME," 
									+ "	 ACCOUNTING_UNIT_OFFICE_ID"
									+ "	FROM FAS_MST_ACCT_UNITS"
									+ "	WHERE ACCOUNTING_UNIT_OFFICE_ID ="
									+ Office_id;
	
							rs_sch_type =st2.executeQuery(sel_qry);
							if (rs_sch_type.next()) 
							{
								ACCOUNTING_UNIT_ID =obj.isNull(rs_sch_type.getString(1), 1);
								ACCOUNTING_UNIT_OFFICE_ID =Office_id;
								
							} 
							 
							if (ACCOUNTING_UNIT_ID.equalsIgnoreCase("0"))
							{
									sel_qry ="	  SELECT ACCOUNTING_UNIT_ID,"
										+ "	ACCOUNTING_UNIT_NAME," 
										+ "	 ACCOUNTING_UNIT_OFFICE_ID"
										+ "	FROM FAS_MST_ACCT_UNITS"
										+ "	WHERE ACCOUNTING_UNIT_OFFICE_ID ="
										+ Region_;
		
								rs_sch_type =st2.executeQuery(sel_qry);
								if (rs_sch_type.next()) 
								{
									ACCOUNTING_UNIT_ID =obj.isNull(rs_sch_type.getString(1), 1);
									ACCOUNTING_UNIT_OFFICE_ID =Region_;
								}
							}
							
							
							
							
							// rs_sch_type.close();
							// rs_sch_type=null;
							String SCH_TYPE_DESC ="";
							sel_qry ="	SELECT  SCH_TYPE_ID ,SCH_TYPE_DESC FROM PMS_SCH_LKP_TYPE "; // where
																									// SCH_TYPE_ID
																									// in
																									// (16,12)
							rs_sch_type =st2.executeQuery(sel_qry);
							double net_amt =0.0;
							int row =0;
							while (rs_sch_type.next()) {
								sch_type =obj.isNull(rs_sch_type.getString(1), 1);
								SCH_TYPE_DESC =obj.isNull(
										rs_sch_type.getString(2), 1);
	//change for Fas dcb journal problem Sa
								sel_qry ="select sum(AMT),SCHEME_SNO,SCH_TYPE_ID from PMS_DCB_WC_SCHEME where  OFFICE_ID="+Office_id+" and month="
										+ month+" and year="+year+" and  SCH_TYPE_ID="+sch_type+" group by SCH_TYPE_ID,SCHEME_SNO,OFFICE_ID";
								
							//	sel_qry ="select sum(AMT),SCHEME_SNO,SCH_TYPE_ID from PMS_DCB_WC_SCHEME where  OFFICE_ID="+Office_id+" and month="
							//			+ month+" and year="+year+" and  SCH_TYPE_ID="+sch_type+" and SCHEME_SNO in (select sch_sno from  PMS_MST_PROJECTS_VIEW where OFFICE_ID="+ Office_id+ "  and   SCH_SNO ="+ sch_sno+")group by SCH_TYPE_ID,SCHEME_SNO,OFFICE_ID";
						
					
								net_amt =0.0f;
								rs_new =st_new.executeQuery(sel_qry);
								qty ="0";
								eqty ="0";
								String cr_ac ="0", dr_ac ="", fun_dr_id ="", RECEIPT_TRN_ID ="";	
								row =0;
								while (rs_new.next()) 
								{
									row++;
									amt =Double.parseDouble(obj.isNull(rs_new.getString(1), 1));
									sch_sno =obj.isNull(rs_new.getString(2), 1);
									int rc =0;
									sel_qry ="	SELECT 	SCH_SNO,FIN_TRAN_ID,ACC_HD_DR, ACC_HD_CR FROM	PMS_SCH_ACCOUNT_MAPPING  where SCH_SNO="+ sch_sno;
									rs2 =st.executeQuery(sel_qry);									
									if (rs2.next()) 
									{
									
										fun_dr_id =obj.isNull(rs2.getString(2), 1);
										dr_ac =obj.isNull(rs2.getString(3), 1);
										cr_ac =obj.isNull(rs2.getString(4), 1);
										
										
										String ac_id=obj.accountUID(Integer.parseInt(Office_id)); 
										rc =obj.getCount("PMS_DCB_JOURNAL_DETAILS","where CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="
														+year+" and SUB_LEDGER_CODE="+sch_sno+" and OFFICE_ID="
														+(ac_id.equalsIgnoreCase("0")?Region_:Office_id));
										String ins_qry ="";
										System.out.println("SCH_SNO="+ sch_sno+" ::::::: RC"+ rc+" ::::current_ac_unit_id"+current_ac_unit_id);
										if (rc ==0) 
							 			{ 
	
										ins_qry =" insert into PMS_DCB_JOURNAL_DETAILS (WC_JOURNAL_SNO,OFFICE_ID,CASHBOOK_MONTH,CASHBOOK_YEAR,SUB_LEDGER_CODE,SCHEME_TYPE_ID,AMOUNT,FIN_TRANS_ID,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,SUB_LEDGER_TYPE_CODE,sch_sno,ACTUAL_OFFICE_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,"
												 + "'"+userid+"'"
												 + ",clock_timestamp(),10,?,?)";   
											
										int maxsno =obj.getMax("PMS_DCB_JOURNAL_DETAILS","WC_JOURNAL_SNO", "");
										PreparedStatement ps =con.prepareStatement(ins_qry);
										ps.setInt(1, maxsno);
										
											if (current_ac_unit_id.equalsIgnoreCase("0"))
												ps.setInt(2, Integer.parseInt(Region_));
											else
												ps.setInt(2, Integer.parseInt(Office_id));
										
											ps.setInt(3, Integer.parseInt(month));
											ps.setInt(4, Integer.parseInt(year));
											ps.setInt(5,Integer.parseInt(obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_ID"," where OFFICE_ID="+ Office_id+ " and STATUS='L' and   SCH_SNO ="+ sch_sno)));
											ps.setInt(6, Integer.parseInt(sch_type));
											ps.setDouble(7, Math.round(amt));
											ps.setInt(8, Integer.parseInt(fun_dr_id));
											ps.setInt(9, Integer.parseInt(dr_ac));
											ps.setString(10, "DR");
											ps.setInt(11, Integer.parseInt(ACCOUNTING_UNIT_ID));
											ps.setInt(12,Integer.parseInt(ACCOUNTING_UNIT_OFFICE_ID));
											ps.setString(13,"Scheme Water Charges");
											ps.setInt(14, Integer.parseInt(sch_sno));
											ps.setInt(15, Integer.parseInt(Office_id));
											ps.executeUpdate();
											net_amt +=amt;
											amt =0.0f;
	
										}
									}// PMS_SCH_ACCOUNT_MAPPING END
	
								}// PMS_DCB_WC_BILLING_SCH END
	
								if (row !=0) {
									sel_qry ="	SELECT "+"	RECEIPT_TRN_ID "
											+ "	FROM			 	"
											+ " 		PMS_DCB_RECEIPT_ACCOUNT_MAP "
											+ " where SCH_TYPE_ID="+sch_type
											+ " and ACCOUNT_HEAD_CODE="+cr_ac;
									rs3 =st4.executeQuery(sel_qry);
	
									if (rs3.next()) 
									{
										RECEIPT_TRN_ID =obj.isNull(rs3.getString(1), 1);
									} else {
										RECEIPT_TRN_ID ="0";
									}
	
									int rc_cr =obj
											.getCount(
													"PMS_DCB_JOURNAL_DETAILS",
													"where CASHBOOK_MONTH="
															+ month
															+ " and CASHBOOK_YEAR="
															+ year
															+ " and SCHEME_TYPE_ID="
															+ sch_type
															+ " and CR_DR_INDICATOR='CR'  and OFFICE_ID="
															+ Office_id);
									String ins_qry ="";
	
									if (rc_cr ==0) {
	
										jrow++;
										ins_qry ="insert into PMS_DCB_JOURNAL_DETAILS ("
												+ " WC_JOURNAL_SNO,"
												+ " OFFICE_ID,"
												+ " CASHBOOK_MONTH,"
												+ " CASHBOOK_YEAR, "
												+ " SUB_LEDGER_CODE,"
												+ " SCHEME_TYPE_ID,"
												+ " AMOUNT,"
												+ " RECEIPT_TRN_ID,"
												+ " ACCOUNT_HEAD_CODE,"
												+ " CR_DR_INDICATOR,"
												+ " ACCOUNTING_UNIT_ID,"
												+ " ACCOUNTING_FOR_OFFICE_ID,"
												+ " REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,SUB_LEDGER_TYPE_CODE,ACTUAL_OFFICE_ID"
												+ " ) "
												+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,"  
												+ "'"+userid+"'"+",clock_timestamp(),16,?)";
	
												int maxsno =obj.getMax("PMS_DCB_JOURNAL_DETAILS","WC_JOURNAL_SNO", "");
												PreparedStatement ps =con.prepareStatement(ins_qry);
												ps.setInt(1, maxsno);
												
												if (current_ac_unit_id.equalsIgnoreCase("0"))
													ps.setInt(2, Integer.parseInt(Region_));
												else
													ps.setInt(2, Integer.parseInt(Office_id));
												
					
												
												
												ps.setInt(3, Integer.parseInt(month));
												ps.setInt(4, Integer.parseInt(year));
												
												
									// newly added							
												if (cr_ac.equalsIgnoreCase("459020"))
													ps.setInt(5, Integer.parseInt("0"));
												else
													ps.setInt(5, Integer.parseInt(sch_type));
												
					//		change          	ps.setInt(5, Integer.parseInt("0"));
										//		ps.setInt(5, Integer.parseInt(sch_type));
												ps.setInt(6, Integer.parseInt(sch_type));
												ps.setDouble(7, Math.round(net_amt));
												ps.setInt(8, Integer.parseInt(RECEIPT_TRN_ID));
												ps.setInt(9, Integer.parseInt(cr_ac));
												ps.setString(10, "CR");
												
												if (ACCOUNTING_UNIT_ID.equalsIgnoreCase("0"))
													ps.setString(11, Region_ac_id);
												else
													ps.setInt(11, Integer.parseInt(ACCOUNTING_UNIT_ID));
												 
												
												ps.setInt(12, Integer.parseInt(ACCOUNTING_UNIT_OFFICE_ID));
												ps.setString(13, "Total Water Charges for "+ SCH_TYPE_DESC);
												ps.setInt(14, Integer.parseInt(Office_id));
												ps.executeUpdate();
									}
								}
	
							}// SCHEME TYPE ID END
							xml +="<jrow>"+jrow+"</jrow>";
							// rs.close();
	
						} catch (Exception e) {
							// Auto-generated catch block
							e.printStackTrace();
						}
	
					}
	
					if (command.equals("show")) {
	
						if (process.equals("1")) {
							cond =cond;
							condbase =" where  "+new_cond+"   (1=1)";
						}
						if (process.equals("2")) {
							cond +=" and SUBDIV_OFFICE_ID="+divsno;
							condbase =" where  "+new_cond+" (1=1)";
						}
						if (process.equals("3")) {
							cond +=" and SUBDIV_OFFICE_ID="+divsno;
							condbase =" where  "+new_cond
									+ "  BENEFICIARY_TYPE_ID="+bentype;
						}
						try {
	
							str_qry =" select BEN.BENEFICIARY_SNO,BENEFICIARY_TYPE_ID, "
									+ " BEN.BENEFICIARY_NAME "
									+ ",BTYPE.BEN_TYPE_DESC ,PUMP.qty ,PUMP1.pr_record   from  "
									+ "("
									+ "( select  BENEFICIARY_TYPE_ID, "
									+ " BENEFICIARY_NAME,BENEFICIARY_SNO	 "
									+ " from PMS_DCB_MST_BENEFICIARY  "
									+ condbase
									+ " )BEN "
									+ "  left outer  JOIN "
									+ "( "
									+ " select "
									+ "BEN_TYPE_DESC ,BEN_TYPE_ID "
									+ " from PMS_DCB_BEN_TYPE "
									+ ")BTYPE "
									+ "  ON BTYPE.BEN_TYPE_ID =BEN.BENEFICIARY_TYPE_ID "
									+ " JOIN "
									+ " ( "
									+ " select BENEFICIARY_SNO,"
									+ "  sum(QTY_CONSUMED_CALC) as qty ,"
									+ "  OFFICE_ID,MONTH ,YEAR "
									+ "  from  PMS_DCB_TRN_MONTHLY_PR  "
									+ " where "
									+ cond
									+ " and MONTH="
									+ month
									+ " and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="
									+ Office_id
									+ " and meter_status='L') and  YEAR="
									+ year
									+ " and  OFFICE_ID ="
									+ Office_id
									+ " group by BENEFICIARY_SNO,OFFICE_ID,MONTH ,YEAR  "
									+ ")PUMP "
									+ "  ON PUMP.BENEFICIARY_SNO =BEN.BENEFICIARY_SNO "
									+ " left outer JOIN "
									+ " ( "
									+ " select  BENEFICIARY_SNO,count(*) as pr_record  from  PMS_DCB_TRN_MONTHLY_PR  "
									+ " where "
									+ cond
									+ " and MONTH="
									+ month
									+ " and YEAR="
									+ year
									+ " and PR_SNO  in (select PR_SNO from PMS_DCB_WC_BILLING where MONTH="
									+ month
									+ "  and YEAR="
									+ year
									+ " ) group by BENEFICIARY_SNO "
									+ ")PUMP1 "
									+ "  ON PUMP1.BENEFICIARY_SNO =BEN.BENEFICIARY_SNO  ) order by BENEFICIARY_TYPE_ID,BENEFICIARY_NAME";
	
								System.out.println("str_qry--------2-------->"+str_qry);
							
							ResultSet rs =obj.getRS(str_qry);
							row_count =0;
							String new_qry_ben ="", new_qry_qty ="";
							while (rs.next()) {
								row_count++;
	
								/* NEW ENTRy */
								new_qry_ben =obj.isNull(rs
										.getString("BENEFICIARY_SNO"), 1);
	
								String qry_new =" SELECT  QTY_CONSUMED_CALC "
										+ " FROM PMS_DCB_TRN_MONTHLY_PR "
										+ " WHERE  "
										+ " MONTH    ="
										+ month
										+ " AND YEAR       ="
										+ year
										+ " AND METRE_SNO IN "
										+ "  ( SELECT METRE_SNO FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE meter_status='L' AND OFFICE_ID="
										+ Office_id+" and beneficiary_sno="
										+ new_qry_ben+" ) "+"  AND OFFICE_ID ="
										+ Office_id;
								PreparedStatement new_ps =con
										.prepareStatement(qry_new);
								ResultSet new_rs_qry =new_ps.executeQuery();
	
								double new_qry_qty_ben =0.0;
								while (new_rs_qry.next()) {
									new_qry_qty_ben +=new_rs_qry
											.getDouble("QTY_CONSUMED_CALC");
								}
	
								/* NEW ENTRy */
	
								// Convert to Two Decimal
								new_qry_qty =formatter.format(new_qry_qty_ben);
								xml +="<BENEFICIARY_SNO>"+new_qry_ben
										+ "</BENEFICIARY_SNO>";
								xml +="<BENEFICIARY_NAME>"
										+ obj.isNull(rs
												.getString("BENEFICIARY_NAME"), 2)
										+ "</BENEFICIARY_NAME>";
								xml +="<BENEFICIARY_TYPE>"
										+ obj.isNull(rs.getString("BEN_TYPE_DESC"),
												1)+"</BENEFICIARY_TYPE>";
								xml +="<BENEFICIARY_TYPE_SNO>"
										+ obj.isNull(rs
												.getString("BENEFICIARY_TYPE_ID"),
												1)+"</BENEFICIARY_TYPE_SNO>";
								xml +="<qty>"+new_qry_qty+"</qty>";
								xml +="<pr_record>"
										+ obj.isNull(rs.getString("pr_record"), 1)
										+ "</pr_record>";
								new_qry_qty ="0.0";
							}
	
							if (row_count !=0) {
								xml +="<row_count>"
										+ row_count
										+ "</row_count><status>Data Found </status>";
							} else {
								xml +="<row_count>"+row_count
										+ "</row_count><status>No Data </status>";
							}
	
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
	
					String PR_SNO ="", count ="0";
					int del_row =0;
					int rc =0;
					int rc1 =0;
	
					// ///////// / // PUMPING FREEZE
	
					if (command.equals("FR")) {
	
						int val_flag =0;
						int sp_flag =0;
						int off_pr_count =0;// total PR record of Selected
												// Division,Month,Year
						int fr_flag =0;// Freeze Record Count for all BENEFICIARY
										// of Division
						int total_loc_ =0;// Total Location of Division
						int error_code_ =0;
						String ben_type_desc ="";
						try {
							bentype =obj.setValue("bentype", request);
							ben_type_desc =obj.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC", " where BEN_TYPE_ID="+bentype);
							// Total Location Count
							total_loc_ =obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE","where "+ meter_status
													+ " BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "
													+ new_cond+" OFFICE_ID="
													+ Office_id
													+ " and BENEFICIARY_TYPE_ID="
													+ bentype+" ) ");
							// Total Pumping Return Count
							off_pr_count =obj.getCount("PMS_DCB_TRN_MONTHLY_PR","where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "
													+ new_cond
													+" OFFICE_ID="+Office_id+" and BENEFICIARY_TYPE_ID="
													+bentype+" ) and MONTH="+month+" and  YEAR="+year
													+" and OFFICE_ID="+Office_id);
							// Total Valitdated Count
							val_flag =obj.getCount("PMS_DCB_TRN_MONTHLY_PR","where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "
													+new_cond+" OFFICE_ID="+Office_id+" and BENEFICIARY_TYPE_ID="
													+bentype+" ) and MONTH="+month
													+" and  YEAR="+year
													+" and  PROCESS_FLAG='V'"+" and OFFICE_ID="+Office_id);
							// Already Freezed Count
							fr_flag =obj.getCount("PMS_DCB_TRN_MONTHLY_PR","where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "
													+new_cond+"  OFFICE_ID="+Office_id+" and BENEFICIARY_TYPE_ID="+bentype
													+" ) and MONTH="+month+" and YEAR="+year
													+" and  PROCESS_FLAG='FR'"
													+" and OFFICE_ID="+Office_id);
						} catch (Exception e1) 
						{
							e1.printStackTrace();
						}
						// if Total PR Freeze Should not Allow Again to freeze
						if (fr_flag ==off_pr_count) 
						{
							sp_flag =1;
						}
						
						if (val_flag ==off_pr_count && sp_flag ==0) 
						{
							PreparedStatement ps;
							int pr_count2 =0;
							rc1 =0;
							try {
								rc1 =obj.getCount("PMS_DCB_TRN_MONTHLY_PR","where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "
														+new_cond+" OFFICE_ID="+Office_id+" and BENEFICIARY_TYPE_ID="
														+bentype+" ) and MONTH="+month+" and  YEAR="
														+year+" and  PROCESS_FLAG='FR'");
								} catch (Exception e1) 
								{
									e1.printStackTrace();
								}
								
							int rc_PMS_DCB_PR_FREEZE =0;
							try {
								rc_PMS_DCB_PR_FREEZE =obj.getCount("PMS_DCB_PR_FREEZE", "where OFFICE_ID="+Office_id+" and  MONTH="+month+" and YEAR="+year);
							} catch (Exception e3) {
								rc_PMS_DCB_PR_FREEZE =0;
							}
							String qry ="";
							int up_row =0;
							if (rc_PMS_DCB_PR_FREEZE ==0) 
							{
								qry ="insert into PMS_DCB_PR_FREEZE (OFFICE_ID,PR_FREEZED,PR_FREEZE_DATE,MONTH,YEAR,UPDATED_BY_USER_ID,UPDATED_DATE) values ("
										+Office_id+",'CR',clock_timestamp(),"+month+","+year+",'"+userid+"',clock_timestamp())";
								try {
									ps =con.prepareStatement(qry);
									ps.executeUpdate();
								} catch (SQLException e2) {
									e2.printStackTrace();
								}
							} else 
							{
								Hashtable cols =new Hashtable();
								cols.put("PR_FREEZE_DATE", "clock_timestamp()");
								Hashtable condht =new Hashtable();
								condht.put("YEAR", year);
								condht.put("MONTH", month);
								condht.put("OFFICE_ID", Office_id);
								try 
								{
									int res_va =obj.recordSave(cols, condht,"PMS_DCB_PR_FREEZE", con);
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
	
							try {
								
								
								// PMS_DCB_FREEZE_STATUS - for Journal Table
								
								
								pr_count2 =obj.getCount("PMS_DCB_FREEZE_STATUS"," where OFFICE_ID="+Office_id+" and  CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year);
								ps =null;
								qry ="insert into PMS_DCB_FREEZE_STATUS ( "+" OFFICE_ID ,"+" CASHBOOK_MONTH,"+" CASHBOOK_YEAR,"+" WC_FREEZED,"
										+" WC_UPDATED_DATE,"+" WC_UPDATED_BY_USER_ID,ACTUAL_OFFICE_ID)";
								qry +=" VALUES ("+Office_id+",?::numeric,?::numeric,'CR',clock_timestamp(),'"+userid+"',"+Office_id+")";
								
								
								
								
								ps =con.prepareStatement(qry);
								ps.setString(1, month);
								ps.setString(2, year);
								if (pr_count2 ==0) 
								{
									ps.executeUpdate();
									qry ="";
								} else {
									Hashtable cols_ =new Hashtable();
									cols_.put("WC_FREEZED", "'CR'");
									cols_.put("WC_FREEZED_DATE", "clock_timestamp()");
	
									Hashtable condht_ =new Hashtable();
									condht_.put("CASHBOOK_YEAR", year);
									condht_.put("CASHBOOK_MONTH", month);
									condht_.put("OFFICE_ID", Office_id);
									try {
										int res_va =obj.recordSave(cols_, condht_,"PMS_DCB_FREEZE_STATUS", con);
										} catch (SQLException e) 
										{
											e.printStackTrace();
										}
								}
							} catch (Exception e) 
							{
								e.printStackTrace();
							}
							// PMS_DCB_TRN_MONTHLY_PR & PMS_DCB_MONTHLY_PR ->
							// pumping
							// return table
							// if should not already freeze
							if (rc1 ==0) 
							{
								rc =0;
								try 
								{
									rc =obj.getCount("PMS_DCB_TRN_MONTHLY_PR","where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "
															+new_cond+" OFFICE_ID="+Office_id+" and BENEFICIARY_TYPE_ID="+bentype
															+" ) and MONTH="
															+month+" and  YEAR="+year+" ");
								} catch (Exception e1) 
								{
									e1.printStackTrace();
								}
	
								if (rc !=0) {
									up_row =0;
									int up_row1 =0;
	
									// Set FR status to Transaction Table
									str_qry =" update  PMS_DCB_TRN_MONTHLY_PR set PROCESS_FLAG ='FR' where BENEFICIARY_SNO in"
											+" ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where "
											+new_cond+" OFFICE_ID="+Office_id+" and BENEFICIARY_TYPE_ID="
											+bentype+" ) and MONTH="+month+" and YEAR="
											+year+" and  PROCESS_FLAG='V'";
									try {
										up_row =obj.setUpd(str_qry);
									} catch (SQLException e) {
										e.printStackTrace();
									}
	
									String qty ="0", eqty ="0";
									String SCH_TYPE_ID ="1", SCHEME_SNO ="0", METRE_SNO ="0";
									String sub_vaules ="";
	
									// Set FR status to Master Table of Pumping
									// Return Table (PMS_DCB_MONTHLY_PR)
	
									str_qry =" update  PMS_DCB_MONTHLY_PR  "
											+" set PROCESS_FLAG ='FR'  "
											+" where BENEFICIARY_SNO in ( "
											+"    select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  "
											+"      where  "+new_cond
											+" OFFICE_ID="+Office_id
											+" and BENEFICIARY_TYPE_ID="
											+bentype+" ) and MONTH="+month
											+" and YEAR="+year;
									try {
										up_row1 =obj.setUpd(str_qry);
										sel_qry ="select BENEFICIARY_SNO,TARIFF_MODE  from PMS_DCB_MST_BENEFICIARY where      "
												+new_cond
												+"   OFFICE_ID="
												+Office_id
												+"      and BENEFICIARY_TYPE_ID="
												+bentype;
										   
										jou_ben =st3.executeQuery(sel_qry);
										// Select BENEFICIARY_SNO from
										// PMS_DCB_MST_BENEFICIARY table
										while (jou_ben.next()) {
											String BENEFICIARY_SNO =obj.isNull(
													jou_ben.getString(1), 1);
											ben_sno =BENEFICIARY_SNO;
											SCH_TYPE_ID ="0";
											SCHEME_SNO ="0";
											String AMT_RECTIFY ="0";
											String min_value ="0", allot_qty ="0", allot_flag ="0", min_flag ="0";
											String Alloted_amt ="0.0"; 
											double cal_amt =0.0;
											sel_qry =" select  sum(QTY_CONSUMED_NET),"
													+"         SCH_SNO,BENEFICIARY_SNO ,"
													+"         MONTH,"
													+"         YEAR,sum(AMT_RECTIFY) as AMT_RECTIFY  "
													+" from    PMS_DCB_TRN_MONTHLY_PR  "
													+" where   BENEFICIARY_SNO="
													+BENEFICIARY_SNO
													+" and month="
													+month
													+" and year="
													+year
													+" And     METRE_SNO in (  "
													+"                select "
													+"                       METRE_SNO "
													+"                 from  PMS_DCB_MST_BENEFICIARY_METRE  "
													+"                 where "
													+meter_status
													+" BENEFICIARY_SNO="
													+BENEFICIARY_SNO
													+" and TARIFF_FLAG='S' "
													+"					   ) "
													+"				  group by SCH_SNO,BENEFICIARY_SNO, MONTH,YEAR  order by BENEFICIARY_SNO";
	
											jou_rs1 =st2.executeQuery(sel_qry);
	
											while (jou_rs1.next())
											{
												AMT_RECTIFY ="0";
												int WC_TRN_SNO =1;// obj.getMax("PMS_DCB_WC_BILLING","WC_TRN_SNO",
																	// "");
												BILL_MONTH =obj.isNull(jou_rs1.getString(4), 1);
												BILL_YEAR =obj.isNull(jou_rs1.getString(5), 1);
												AMT_RECTIFY =obj.isNull(jou_rs1.getString(6), 1);
												String QTY_CONSUMED =obj.isNull(jou_rs1.getString(1), 1);
												SCHEME_SNO =obj.isNull(jou_rs1.getString(2), 1);
												SCH_TYPE_ID =obj.getValue("PMS_SCH_MASTER","SCH_TYPE_ID","where SCH_SNO="+SCHEME_SNO);
												proc_stmt =null;
												min_value =obj.getValue("PMS_DCB_ALLOTTED","MIN_QTY","where SCH_SNO="
																		+SCHEME_SNO+" and BENEFICIARY_SNO="+ben_sno
																		+" and ACTIVE_STATUS='A'");
												allot_qty =obj.getValue("PMS_DCB_ALLOTTED","ALLOT_QTY","where SCH_SNO="
																		+SCHEME_SNO
																		+ " and BENEFICIARY_SNO="
																		+ ben_sno+ " and ACTIVE_STATUS='A'");
												
												String myear =obj.getValue("PMS_DCB_SETTING", "YEAR","where office_id="+ Office_id);
												String mmonth =obj.getValue("PMS_DCB_SETTING", "MONTH","where office_id="+ Office_id);
												Actual_amt ="0";
												int cmmonth =0;
												if (Integer.parseInt(mmonth) ==1) 
												{
													cmmonth =12;
												} else 
												{
													cmmonth =Integer.parseInt(mmonth) - 1;
												}
	
												String da =obj.month_val2(cmmonth);
												int cday =Integer.parseInt(da);
												if ((cmmonth ==2) && (Integer.parseInt(myear) % 4) ==0) 
												{
													cday =29;
												}
												allot_qty =Float.toString(Float.parseFloat(allot_qty)* cday);
												min_value =Float.toString(Float.parseFloat(min_value)* cday);
												
												allot_flag =obj.getValue("PMS_DCB_ALLOTTED","ALLOT_FLAG","where SCH_SNO="+ SCHEME_SNO
																		+ " and BENEFICIARY_SNO="+ ben_sno
																		+ " and ACTIVE_STATUS='A'");
												min_flag =obj.getValue("PMS_DCB_ALLOTTED","MIN_FLAG","where SCH_SNO="
																		+ SCHEME_SNO
																		+ " and BENEFICIARY_SNO="
																		+ ben_sno+ " and ACTIVE_STATUS='A'");
	
												if (min_flag.trim().equalsIgnoreCase("y")) 
												{
													if (Float.parseFloat(min_value) > Float.parseFloat(QTY_CONSUMED))
														QTY_CONSUMED =min_value;
													else
														QTY_CONSUMED =QTY_CONSUMED;
												}
	
												proc_stmt =con.prepareCall("call PMS_DCB_SLAB (?,?,?,?,?,?,?,?,?,?,?,?) ");
												proc_stmt.setInt(1, Integer.parseInt(month));
												proc_stmt.setInt(2, Integer.parseInt(year));
												proc_stmt.setInt(3, Integer.parseInt(Office_id));
												proc_stmt.setInt(4, Integer.parseInt(ben_sno));
												proc_stmt.setInt(5, Integer.parseInt("0"));
												proc_stmt.setInt(6, Integer.parseInt(SCHEME_SNO));
												proc_stmt.setFloat(7, Float.parseFloat(QTY_CONSUMED));
												proc_stmt.setFloat(8,0);
												proc_stmt.registerOutParameter(8,java.sql.Types.REAL);
												proc_stmt.setInt(9, Integer.parseInt("0"));
												
												// New Code 25/08/2011 Start ,
												// 23/09/2011
												String days =Integer.toString(cday);
												/*
												 * if (Integer.parseInt(year) % 4
												 * ==0 &&
												 * Integer.parseInt(mmonth)==2 ) {
												 * days="29"; } else {
												 * days=obj.month_val2
												 * (Integer.parseInt(mmonth)); }
												 */
	
												proc_stmt.setString(10, allot_flag);
												proc_stmt.setString(11, min_flag);
												proc_stmt.setInt(12, Integer.parseInt(days));
												// New Code 25/08/2011 End
	
												proc_stmt.execute();
												//String Tot_Amt =obj.isNull(proc_stmt.getString(8), 1);
												String Tot_Amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
												// alloted flag is true and
												// calculate
												// the amount for that qty ===Start
												Cal_Excess =0;
												cal_amt =0;
												Alloted_amt ="0";
												if (allot_flag.trim().equalsIgnoreCase("y") && Float.parseFloat(allot_qty) < Float.parseFloat(QTY_CONSUMED)) 
												{
													proc_stmt =con.prepareCall("call PMS_DCB_SLAB (?,?,?,?,?,?,?,?,?,?,?,?) ");
													proc_stmt.setInt(1, Integer.parseInt(month));
													proc_stmt.setInt(2, Integer.parseInt(year));
													proc_stmt.setInt(3, Integer.parseInt(Office_id));
													proc_stmt.setInt(4, Integer.parseInt(ben_sno));
													proc_stmt.setInt(5, Integer.parseInt("0"));
													proc_stmt.setInt(6, Integer.parseInt(SCHEME_SNO));
													proc_stmt.setFloat(7, Float.parseFloat(allot_qty));
													proc_stmt.setFloat(8,0);
													proc_stmt.registerOutParameter(8,java.sql.Types.REAL);
													proc_stmt.setInt(9, Integer.parseInt("1"));
													// New Code 25/08/2011 Start
													days =Integer.toString(cday);
													/*
													 * if (Integer.parseInt(year) %
													 * 4 ==0 &&
													 * Integer.parseInt(mmonth)==2 )
													 * { days="29"; } else {
													 * days=obj
													 * .month_val2(Integer.parseInt
													 * (mmonth)); }
													 */
													// days=obj.month_val2(Integer.parseInt(mmonth));
													proc_stmt.setString(10,allot_flag);
													proc_stmt.setString(11,min_flag);
													proc_stmt.setInt(12, Integer.parseInt(days));
													// New Code 25/08/2011 End
													proc_stmt.execute();
													//Alloted_amt =obj.isNull(proc_stmt.getString(8),2);
													Alloted_amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 2);
													
													if (Alloted_amt.equals(""))
														Alloted_amt ="0";
	
													Actual_amt =Alloted_amt;
													cal_amt =Double.parseDouble(Tot_Amt)- Double.parseDouble(Alloted_amt);
													Cal_Excess =(Float.parseFloat(QTY_CONSUMED) - Float.parseFloat(allot_qty));
												} else {
													Actual_amt =Tot_Amt;
												}
												// END ==> alloted flag is true and
												// calculate the amount for that qty
	
												int c =obj.getCount("PMS_DCB_WC_BILLING"," where BENEFICIARY_SNO="+ben_sno+" and  SCHEME_SNO="+ SCHEME_SNO+ ""+ " and OFFICE_ID="+ Office_id+" and  MONTH="+ month+""+" and  YEAR="+ year+"");
												if (c ==0) 
												{
													String sub_ins ="insert into PMS_DCB_WC_BILLING( WC_TRN_SNO,BENEFICIARY_SNO,OFFICE_ID,MONTH,YEAR,QTY_CONSUMED"
															+ ",AMT,EXCESS_AMT,TOTAL_AMT,SCHEME_SNO,SCH_TYPE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,ALLOT_QTY,EXCESS_QTY)";
													sub_vaules ="values  ("+ WC_TRN_SNO+ ","+ BENEFICIARY_SNO
															+ ","+ Office_id+ ","+ BILL_MONTH+ ","
															+ BILL_YEAR+ ","
															//+QTY_CONSUMED+
															// ","+Math.round(Double.parseDouble(Actual_amt))+","+Math.round(cal_amt)+
															// ","+
															// Math.round(Double.parseDouble(Tot_Amt)+Double.parseDouble(AMT_RECTIFY))+
															// ","
															+ QTY_CONSUMED+ ","+ Math.round(Double.parseDouble(Actual_amt))
															+ ","
															+ Math.round(cal_amt)
															+ ","
															+ Math.round(Double.parseDouble(Tot_Amt)+ Double.parseDouble(AMT_RECTIFY))
															+ ","+SCHEME_SNO
															+ ","+SCH_TYPE_ID
															+ ",'"+userid+"'"
															+ ",clock_timestamp(),"
															+ allot_qty+","
															+ Cal_Excess+")";
	
													PreparedStatement ps_ins =con.prepareStatement(sub_ins+ sub_vaules);
													int t_row =0;
													try 
													{
														t_row =ps_ins.executeUpdate();
													} catch (Exception e) 
													{
														System.out.println(e);
													}
												}// c=0 end
	
											} // jou_rs1.next - -- end
												//Scheme based process 
											// 
											sel_qry ="select QTY_CONSUMED_NET,SCH_SNO,BENEFICIARY_SNO ,MONTH,YEAR ,METRE_SNO,AMT_RECTIFY  from PMS_DCB_TRN_MONTHLY_PR where BENEFICIARY_SNO="
													+ BENEFICIARY_SNO
													+ " and month="
													+ month
													+ "  and year="
													+ year
													+ " and METRE_SNO in ( select METRE_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where "
													+ meter_status
													+ " BENEFICIARY_SNO ="
													+ BENEFICIARY_SNO
													+ " and TARIFF_FLAG='L' and BENEFICIARY_TYPE_ID="
													+ bentype
													+ ")  order by BENEFICIARY_SNO";
											jou_rs1 =st2.executeQuery(sel_qry);
	
											String Tot_Amt ="0";
											String QTY_CONSUMED ="0";
											while (jou_rs1.next()) {
												AMT_RECTIFY ="0";
												int WC_TRN_SNO =1;// obj.getMax("PMS_DCB_WC_BILLING","WC_TRN_SNO",
																	// "");
												BILL_MONTH =obj.isNull(jou_rs1.getString(4), 1);
												BILL_YEAR =obj.isNull(jou_rs1.getString(5), 1);
												QTY_CONSUMED ="0";
												QTY_CONSUMED =obj.isNull(jou_rs1.getString(1), 1);
												METRE_SNO =obj.isNull(jou_rs1.getString(6), 1);
												AMT_RECTIFY =obj.isNull(jou_rs1.getString(7), 1);
												SCHEME_SNO =obj.isNull(jou_rs1.getString(2), 1);
												String MULTIPLY_FACTOR =obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE",
																"MULTIPLY_FACTOR"," where "+ meter_status+ " METRE_SNO="+ METRE_SNO);
												SCH_TYPE_ID =obj.getValue("PMS_SCH_MASTER","SCH_TYPE_ID"," where SCH_SNO="+ SCHEME_SNO);
												min_value =obj.getValue("PMS_DCB_ALLOTTED","MIN_QTY"," where METRE_SNO="+METRE_SNO
																		+ " and BENEFICIARY_SNO="+ben_sno+" and ACTIVE_STATUS='A'");
												allot_qty =obj.getValue("PMS_DCB_ALLOTTED","ALLOT_QTY"," where METRE_SNO="
																		+ METRE_SNO+ " and BENEFICIARY_SNO="+ ben_sno
																		+ " and ACTIVE_STATUS='A'");
	
												String myear =obj.getValue("PMS_DCB_SETTING", "YEAR","");
												String mmonth =obj.getValue("PMS_DCB_SETTING", "MONTH","");
												int cmmonth =0;
	
												if (Integer.parseInt(mmonth) ==1) 
												{
													cmmonth =12;
												} else {
													cmmonth =Integer.parseInt(mmonth) - 1;
	
												}
												String da =obj.month_val2(cmmonth);
	
												int cday =Integer.parseInt(da);
												if ((cmmonth ==2)&& (Integer.parseInt(myear) % 4) ==0) 
												{
													cday =29;
												}
	
												allot_qty =Float.toString(Float.parseFloat(allot_qty)* cday);
												allot_flag =obj.getValue("PMS_DCB_ALLOTTED","ALLOT_FLAG"," where METRE_SNO="
																		+ METRE_SNO+" and BENEFICIARY_SNO="
																		+ ben_sno+" and ACTIVE_STATUS='A'");
												min_flag =obj.getValue("PMS_DCB_ALLOTTED","MIN_FLAG"," where METRE_SNO="
																		+ METRE_SNO+" and BENEFICIARY_SNO="+ben_sno
																		+ " and ACTIVE_STATUS='A'");
												min_value =Float.toString(Float.parseFloat(min_value)* cday);
												// QTY_CONSUMED =
												// Double.toString(Double.parseDouble(QTY_CONSUMED)*
												// Double.parseDouble(MULTIPLY_FACTOR));
												QTY_CONSUMED =Double.toString(Double.parseDouble(QTY_CONSUMED));
												if (min_flag.equalsIgnoreCase("y")) 
												{
													if (Float.parseFloat(min_value) > Float.parseFloat(QTY_CONSUMED))
														QTY_CONSUMED =min_value;
													else
														QTY_CONSUMED =QTY_CONSUMED;
												}
	
												String days1 =obj.month_val2(cmmonth);
												proc_stmt =null;
												proc_stmt =con.prepareCall("call PMS_DCB_SLAB_LOC (?,?,?,?,?,?,?,?,?,?,?,?) ");
												proc_stmt.setInt(1, Integer.parseInt(month));
												proc_stmt.setInt(2, Integer.parseInt(year));
												proc_stmt.setInt(3, Integer.parseInt(Office_id));
												proc_stmt.setInt(4, Integer.parseInt(ben_sno));
												proc_stmt.setInt(5, Integer.parseInt(METRE_SNO));
												proc_stmt.setInt(6, Integer.parseInt("0"));
												proc_stmt.setFloat(7, Float.parseFloat(QTY_CONSUMED));
												proc_stmt.setFloat(8,0);
												proc_stmt.registerOutParameter(8,java.sql.Types.REAL);
												int cflag =0;
												proc_stmt.setInt(9, cflag);
												// New Code 25/08/2011 Start
												proc_stmt.setString(10, allot_flag);
												proc_stmt.setString(11, min_flag);
												proc_stmt.setInt(12, Integer.parseInt(days1));
												// New Code 25/08/2011 End
												proc_stmt.execute();
												Tot_Amt ="0";
												Tot_Amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
												//Tot_Amt =obj.isNull(proc_stmt.getString(8), 1);
												Cal_Excess =0;
												cal_amt =0;
												Alloted_amt ="0";
												if (allot_flag.equalsIgnoreCase("y") && Float.parseFloat(allot_qty) < Float.parseFloat(QTY_CONSUMED)) 
												{
													proc_stmt =con.prepareCall("call PMS_DCB_SLAB_LOC (?,?,?,?,?,?,?,?,?,?,?,?) ");
													proc_stmt.setInt(1, Integer.parseInt(month));
													proc_stmt.setInt(2, Integer.parseInt(year));
													proc_stmt.setInt(3, Integer.parseInt(Office_id));
													proc_stmt.setInt(4, Integer.parseInt(ben_sno));
													proc_stmt.setInt(5, Integer.parseInt(METRE_SNO));
													proc_stmt.setInt(6, Integer.parseInt("0"));
													proc_stmt.setFloat(7, Float.parseFloat(allot_qty));
													proc_stmt.setFloat(8,0);
													proc_stmt.registerOutParameter(8,java.sql.Types.REAL);
													proc_stmt.setInt(9, Integer.parseInt("1"));
													// New Code 25/08/2011 Start
													days1 =obj.month_val2(cmmonth);
													proc_stmt.setString(10,allot_flag);
													proc_stmt.setString(11,min_flag);
													proc_stmt.setInt(12, Integer.parseInt(days1));
													// New Code 25/08/2011 End
													proc_stmt.execute();
													Alloted_amt = obj.isNull(String.valueOf(proc_stmt.getFloat(8)), 1);
													//Alloted_amt =obj.isNull(proc_stmt.getString(8),1);
													if (Alloted_amt.equals(""))
														Alloted_amt ="0";
													Actual_amt =Alloted_amt;
													cal_amt =Double.parseDouble(Tot_Amt)- Double.parseDouble(Alloted_amt);
													Cal_Excess =(Float.parseFloat(QTY_CONSUMED) - Float.parseFloat(allot_qty));
												}
												else 
												{
													Actual_amt =Tot_Amt;
												}
												// END ==> alloted flag is true and
												// calculate the amount for that qty
	
												int c =obj.getCount("PMS_DCB_WC_BILLING","where BENEFICIARY_SNO="+ben_sno
																		+""+" and  METRE_SNO="+METRE_SNO+""
																		+ "  and OFFICE_ID="+Office_id
																		+ "  and  MONTH="+month
																		+ ""+" and  YEAR="+ year+"");
												if (c ==0) 
												{
													String sub_ins ="insert into PMS_DCB_WC_BILLING(WC_TRN_SNO,BENEFICIARY_SNO,OFFICE_ID,MONTH,YEAR"
															+ ",QTY_CONSUMED,AMT,EXCESS_AMT,TOTAL_AMT,SCHEME_SNO"
															+ ",SCH_TYPE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,METRE_SNO"
															+ ", ALLOT_QTY  ,EXCESS_QTY"+")";
													sub_vaules ="values  ("+""+ WC_TRN_SNO+ ","+ BENEFICIARY_SNO+ ","+ Office_id+ ","+ BILL_MONTH+ ","+ BILL_YEAR+ ","
															+ QTY_CONSUMED+ ","+ Math.round(Double.parseDouble(Actual_amt))+ ","+ Math.round(cal_amt)+ ","
															+ Math.round(Double.parseDouble(Tot_Amt)+ Double.parseDouble(AMT_RECTIFY))
															+ ","+SCHEME_SNO+ ","+SCH_TYPE_ID+ ","+"'"+userid+ "'"+ ",clock_timestamp(),"+ METRE_SNO+","+ allot_qty+","
															+ Cal_Excess+")";
	
													// System.out.println(sub_ins+
													// sub_vaules);
													PreparedStatement ps_ins =con.prepareStatement(sub_ins+sub_vaules);
													int t_row =0;
													try 
													{
														t_row =ps_ins.executeUpdate();
														System.out.println("BENEFICIARY_SNO--->" + BENEFICIARY_SNO+"   Complete");
													} catch (Exception e) 
													{
														System.out.println(e);
													}
												}// c=0 end
											}
										} // BEN Process
												
										
										
										
									} catch (SQLException e) {
										e.printStackTrace();
									} catch (Exception e) {
										e.printStackTrace();
									}
									/*
									 * sel_qry =
									 * "select sum(TOTAL_AMT),SCHEME_SNO,SCH_TYPE_ID from PMS_DCB_WC_BILLING where OFFICE_ID="
									 *+Office_id +
									 * " and month="+month+" and year="
									 * +year+" group by SCHEME_SNO,SCH_TYPE_ID";
									 * 
									 * try { jou_rs3 =st2.executeQuery(sel_qry); }
									 * catch (SQLException e1) {
									 * e1.printStackTrace(); }
									 * 
									 * 
									 * try { while (jou_rs3.next()) { amt =
									 * Double.parseDouble
									 * (obj.isNull(jou_rs3.getString(1), 2));
									 * SCHEME_SNO =obj.isNull(jou_rs3.getString(2),
									 * 2); SCH_TYPE_ID =
									 * obj.isNull(jou_rs3.getString(3), 2); String
									 * sub_ins ="insert into PMS_DCB_WC_SCHEME(" +
									 * "WC_SCH_TRN_SNO"+",OFFICE_ID"+",MONTH" +
									 * ",YEAR"+",AMT"+",SCHEME_SNO" +
									 * ",SCH_TYPE_ID"+",UPDATED_BY_USER_ID" +
									 * ",UPDATED_DATE"+")";
									 * 
									 * 
									 * int WC_SCH_TRN_SNO =0; try { WC_SCH_TRN_SNO
									 * =
									 * obj.getMax("PMS_DCB_WC_SCHEME","WC_SCH_TRN_SNO"
									 * , ""); } catch (SQLException e) {
									 * e.printStackTrace(); }
									 * 
									 * 
									 * 
									 * sub_vaules ="values  ("+WC_SCH_TRN_SNO +
									 * ","+Office_id+""+","+month+""+","
									 *+year+""+","+Math.round(amt)+"" +
									 * ","+SCHEME_SNO+""+","+SCH_TYPE_ID +
									 * ""+",'"+userid+"'"+",clock_timestamp()" +
									 * ")";
									 * 
									 * int sch_count =0;
									 * 
									 * try { sch_count =
									 * obj.getCount("PMS_DCB_WC_SCHEME"
									 * ," where  OFFICE_ID="+ Office_id +
									 * "  and  MONTH ="+month+"  and YEAR=" +
									 * year+"  and SCHEME_SNO="+SCHEME_SNO);
									 * 
									 * } catch (Exception e) {
									 * System.out.println("TEST - ERROR"+e); }
									 * 
									 * PreparedStatement ps_ins =null; try { ps_ins
									 * =con.prepareStatement(sub_ins+sub_vaules); }
									 * catch (SQLException e) { e.printStackTrace();
									 * }
									 * 
									 * int t_row =0; if (sch_count ==0) try {
									 * t_row =ps_ins.executeUpdate(); } catch
									 * (SQLException e) { e.printStackTrace(); }
									 * 
									 * } } catch (NumberFormatException e) {
									 * e.printStackTrace(); } catch (SQLException e)
									 * { e.printStackTrace(); }
									 */
									
									if (up_row > 0 && up_row1 > 0)
										msg ="Pumping Return Freezed for  "
												+ ben_type_desc;
									else
										msg ="Already Pumping Return Freezed for "
												+ ben_type_desc;
									xml +="<msg>"+msg+"</msg><up_row>"
											+ up_row+"</up_row>";
								} else {
									msg ="Pumping Return  Not Validated !  ";
									xml +="<msg>"+msg
											+ "</msg><up_row>0</up_row>";
								}
							} else {
								msg ="Pumping return already freezed for "
										+ ben_type_desc;
								xml +="<msg>"+msg+"</msg><up_row>0</up_row>";
							}
	
						} // Valid Status Check
						else {
							if (sp_flag ==0)
								msg ="Pumping Return  Not Validated for all Beneficiaries !  ";
							else
								msg ="Pumping Return Already Freezed !  ";
	
							xml +="<msg>"+msg+"</msg><up_row>0</up_row>";
	
						}
	
					}
	
					if (command.equals("delete"))
	
					{
						try {
	
							PR_SNO =obj.isNull(obj.getValue("PMS_DCB_TRN_MONTHLY_PR","PR_SNO",
															"where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "
																	+new_cond+" OFFICE_ID="+Office_id+") and MONTH="+month+ " and YEAR="+ year+ " and PROCESS_FLAG <> 'FR'"),1);
							subcount =obj.isNull(obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)","where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "
																	+new_cond+" OFFICE_ID="+ Office_id+") and MONTH="
																	+ month+" and YEAR="+year+""), 1);
							//
							count =obj.getValue("PMS_DCB_WC_BILLING", "count(*)","where PR_SNO="+PR_SNO+" ");
							System.out.println("Count------1------>"+count);
	
							if (Integer.parseInt(count) !=0) 
							{
								xml +="<status>you cant delete</status><count>"+ count+"</count>";
								msg ="Record Not Deleted !";
							} else 
							{
	
								try {
									str_qry ="delete from PMS_DCB_MONTHLY_PR where BENEFICIARY_SNO="
											+ bensno
											+ " and MONTH="
											+ month
											+ " and YEAR="
											+ year
											+ " and PROCESS_FLAG <> 'FR'";
									del_row =obj.setUpd(str_qry);
	
									str_qry ="delete from PMS_DCB_TRN_MONTHLY_PR where BENEFICIARY_SNO="
											+ bensno
											+ " and MONTH="
											+ month
											+ " and YEAR="
											+ year
											+ " and PROCESS_FLAG <> 'FR'";
	
									del_row =obj.setUpd(str_qry);
	
									if (del_row >=1) {
										msg ="Record Deleted Successfully!";
									} else {
										msg ="Record Not Deleted !";
									}
								} catch (Exception e) {
									System.out.println(e);
									if (Integer.parseInt(subcount) >=1)
										msg ="Record Not Deleted ! , may be it freezed ";
									else
										msg ="Record Not Deleted !";
								}
							}
							xml +="<msg>"
									+ msg
									+ "</msg><del_row>"
									+ del_row
									+ "</del_row><status>valid for delete</status><count>"
									+ count+"</count>";
						} catch (Exception e) {
							System.out.println(e);
						}
						xml +="<count>"+count+"</count>";
					}
	
					if (command.equals("prv_show")) {
	
						String METER_SNO =obj.setValue("METER_SNO", request);
	
						try {
							String Ben_name =obj.getValue(
									"PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_NAME",
									"where  "+new_cond+" BENEFICIARY_SNO="
											+ bensno+" ");
							String METRE_LOCATION =obj.getValue(
									"PMS_DCB_MST_BENEFICIARY_METRE",
									"METRE_LOCATION", "where "+meter_status
											+ " METRE_SNO="+METER_SNO+" ");
	
							xml +="";
							xml +="<Ben_name>"+Ben_name+"</Ben_name>";
							xml +="<METRE_LOCATION>"+METRE_LOCATION
									+ "</METRE_LOCATION>";
	
							String METRE_TYPE =obj.getValue(
									"PMS_DCB_MST_BENEFICIARY_METRE", "METRE_TYPE",
									"where "+meter_status+" METRE_SNO="
											+ METER_SNO+" ");
	
							if (METRE_TYPE.equalsIgnoreCase(""))
								METRE_TYPE ="0";
	
							if (Integer.parseInt(METRE_TYPE) ==2)
								xml +="<METRE_TYPE>ML</METRE_TYPE>";
							else
								xml +="<METRE_TYPE>KL</METRE_TYPE>";
							int month_set =0;
							int year_set =0;
	
							if (Integer.parseInt(month) ==1) {
								month_set =12;
								year =Integer.toString(Integer.parseInt(year) - 1);
							} else {
								month_set =(Integer.parseInt(month) - 1);
								year =year;
	
							}
	
							String Prv_Metre_Work =obj
									.getValue("PMS_DCB_TRN_MONTHLY_PR",
											"METRE_WORKING", "where METRE_SNO="
													+ METER_SNO+" and  MONTH="
													+ month_set+" and YEAR="
													+ year+" ");
	
							if (Prv_Metre_Work.equalsIgnoreCase(""))
								Prv_Metre_Work ="-";
	
							xml +="<Prv_Metre_Work>"+Prv_Metre_Work
									+ "</Prv_Metre_Work>";
	
							String Prv_Metre_Fix =obj
									.getValue("PMS_DCB_TRN_MONTHLY_PR",
											"METRE_FIXED", "where METRE_SNO="
													+ METER_SNO+" and  MONTH="
													+ month_set+" and YEAR="
													+ year+" ");
							if (Prv_Metre_Fix.equalsIgnoreCase(""))
								Prv_Metre_Fix ="-";
							xml +="<Prv_Metre_Fix>"+Prv_Metre_Fix+ "</Prv_Metre_Fix>";
							String Prv_In_Read =obj.getValue("PMS_DCB_TRN_MONTHLY_PR","METRE_INITIAL_READING","where METRE_SNO="+METER_SNO
													+ " and  MONTH="+month_set
													+ " and YEAR="+year+" ");
							if (Prv_In_Read.equalsIgnoreCase(""))
								Prv_In_Read ="-";
	
							xml +="<Prv_In_Read>"+Prv_In_Read+"</Prv_In_Read>";
	
							String Prv_Cls_Read =obj.getValue("PMS_DCB_TRN_MONTHLY_PR","METRE_CLOSING_READING","where METRE_SNO="+METER_SNO
													+ " and  MONTH="+month_set
													+ " and YEAR="+year+" ");
							if (Prv_Cls_Read.equalsIgnoreCase(""))
								Prv_Cls_Read ="-";
	
							xml +="<Prv_Cls_Read>"+Prv_Cls_Read+ "</Prv_Cls_Read>";
							String Prv_Qty_Cons =obj.getValue("PMS_DCB_TRN_MONTHLY_PR","QTY_CONSUMED_NET", "where METRE_SNO="
													+ METER_SNO+" and  MONTH="
													+ month_set+" and YEAR="
													+ year+" ");
	
							if (Prv_Qty_Cons.equalsIgnoreCase(""))
								Prv_Qty_Cons ="-";
	
							xml +="<Prv_Qty_Cons>"+Prv_Qty_Cons+ "</Prv_Qty_Cons>";
							String Prv_EQty_Cons ="0";
							xml +="<Prv_EQty_Cons>"+Prv_EQty_Cons+ "</Prv_EQty_Cons>";
							String Act_Allow_Qty ="0";
	
						} catch (Exception e) {
							// Auto-generated catch block
							e.printStackTrace();
						}
					}
					// Beneficiary Water Charges Display
	
					if (command.equals("pr_select")) {
						// command => pr_select
	
						String ad_cond ="";
						String amt_col_get ="";
						String amt_col_query ="left join ( "
								+ "    select sum(WC_SLAB_AMT) as cal_amt,BENEFICIARY_SNO from "
								+ "     PMS_DCB_WC_SLAB    where  YEAR ="
								+ year
								+ "       AND MONTH ="
								+ month
								+ "     group by BENEFICIARY_SNO   ) benamt "
								+ "   on benamt.BENEFICIARY_SNO=ben.beneficiary_sno";
						String sp_flag =obj.setValue("sp_flag", request);
	
						if (Integer.parseInt(sp_flag) ==2) {
							amt_col_query ="  join ( "
									+ "    select sum(TOTAL_AMT) as cal_amt,BENEFICIARY_SNO from "
									+ "     PMS_DCB_WC_BILLING    where  YEAR = "
									+ year
									+ "  and office_id="
									+ Office_id
									+ "      AND MONTH ="
									+ month
									+ "      group by BENEFICIARY_SNO   ) benamt "
									+ "   on benamt.BENEFICIARY_SNO=PR.BENEFICIARY_SNO";
							amt_col_get ="  ,  benamt.cal_amt ";
	
						} else {
	
							amt_col_query ="";
	
						}
	
						if (Integer.parseInt(month) ==1) {
							ad_cond ="where  YEAR="+(Integer.parseInt(year) - 1)
									+ " and  MONTH=12";
	
						} else {
							ad_cond ="where  YEAR="+(Integer.parseInt(year))
									+ " and  MONTH="
									+ (Integer.parseInt(month) - 1);
	
						}
	
						str_qry ="select "+"PR.NET_CONSUMED,"
								+ "BEN.BENEFICIARY_NAME,"
								+ "BEN.BENEFICIARY_TYPE_ID,"
								+ "BEN.BENEFICIARY_SNO,BEN.BENEFICIARY_TYPE_ID,"
								+ "PRVPR.prv,"+"BEN_TYPE.BEN_TYPE_DESC,"
								+ " AMT.cal_amt"
								+ amt_col_get
								+ " from "
								+ "("
								+ "( "
								+ "select"
								+ " BENEFICIARY_SNO,"
								+ " sum(QTY_CONSUMED_NET) as NET_CONSUMED,"
								+ " YEAR,"
								+ " MONTH "
								+ " from "
								+ " PMS_DCB_TRN_MONTHLY_PR "
								+ " where "
								+ " YEAR="
								+ year
								+ " and "
								+ " MONTH="
								+ month
								+ " and METRE_SNO in (SELECT METRE_SNO FROM pms_dcb_mst_beneficiary_metre  WHERE office_id ="
								+ Office_id
								+ "  AND meter_status ='L') and"
								+ " BENEFICIARY_SNO in ("
								+ " select "
								+ " 		BENEFICIARY_SNO"
								+ " from "
								+ " 		PMS_DCB_MST_BENEFICIARY"
								+ " where "
								+ " 	 "
								+ new_cond
								+ " 	OFFICE_ID="
								+ Office_id
								+ ")   group by BENEFICIARY_SNO,YEAR,MONTH "
								+ ""
								+ " "
								+ " ) PR "
								+ "JOIN ("
								+ " select "
								+ " BENEFICIARY_NAME, "
								+ " BENEFICIARY_SNO, "
								+ " BENEFICIARY_TYPE_ID "
								+ " from"
								+ " PMS_DCB_MST_BENEFICIARY where status='L' "
								+ " order by BENEFICIARY_TYPE_ID,BENEFICIARY_NAME"
								+
	
								") BEN   "
								+ " on BEN.BENEFICIARY_SNO=PR.BENEFICIARY_SNO "
								+ " join "
								+ "  ("
								+ " select "
								+ "BEN_TYPE_DESC,"
								+ "BEN_TYPE_ID "
								+ " from "
								+ " PMS_DCB_BEN_TYPE "
								+ ""
								+ " "
								+ ")BEN_TYPE "
								+ " on BEN_TYPE.BEN_TYPE_ID=BEN.BENEFICIARY_TYPE_ID  "
								+ "   left join  "
								+ " ( "
								+ "select"
								+ " BENEFICIARY_SNO,"
								+ " sum(QTY_CONSUMED_CALC) as prv,"
								+ " YEAR,"
								+ " MONTH "
								+ " from "
								+ " PMS_DCB_TRN_MONTHLY_PR "
								+ ad_cond
								+ "  group by BENEFICIARY_SNO,YEAR,MONTH"
								+ " ) PRVPR "
								+ " on BEN.BENEFICIARY_SNO=PRVPR.BENEFICIARY_SNO"
								+ " left join  (   select sum(TOTAL_AMT) as cal_amt,BENEFICIARY_SNO"
								+ " from PMS_DCB_WC_BILLING  where year="
								+ year
								+ " and office_id="
								+ Office_id
								+ " and month="
								+ month
								+ " group by BENEFICIARY_SNO  )AMT   	on AMT.BENEFICIARY_SNO=ben.beneficiary_sno "
								+ ""+amt_col_query+"   )  ";
						
						System.out.println("str_qry===>"+str_qry);
	
						try {
							row_count =0;
							ResultSet rs =obj.getRS(str_qry);
							while (rs.next()) 
							{
								row_count++;
								String select_BENEFICIARY_SNO =obj.isNull(rs.getString("BENEFICIARY_SNO"), 1);
								xml +="<BENEFICIARY_SNO>"+select_BENEFICIARY_SNO+ "</BENEFICIARY_SNO>";
								xml +="<BENEFICIARY_NAME>"+ obj.isNull(rs.getString("BENEFICIARY_NAME"), 2)+ "</BENEFICIARY_NAME>";
								ad_cond +=" and BENEFICIARY_SNO="+ select_BENEFICIARY_SNO;
	
								xml +="<CUR_NET_CONSUMED>"+obj.isNull(rs.getString("NET_CONSUMED"),1)+"</CUR_NET_CONSUMED>";
								xml +="<Prv_NET_CONSUMED>"+obj.isNull(rs.getString("prv"), 1)+ "</Prv_NET_CONSUMED>";
								xml +="<BEN_TYPE_DESC>"+obj.isNull(rs.getString("BEN_TYPE_DESC"),1)+"</BEN_TYPE_DESC>";
	
								if (Integer.parseInt(sp_flag) ==2) 
								{
									xml +="<cal_amt>"+obj.isNull(rs.getString("cal_amt"),1)+"</cal_amt>";
								}
							}
							xml +="<row_count>"+row_count+"</row_count>";
							
						} catch (Exception e) {
							// Auto-generated catch block
							e.printStackTrace();
						}
					}
	
					if (command.equals("unpr")) 
					{
						PreparedStatement ps;
						row_count =0;
						row_count =Integer.parseInt(obj.setValue("count", request));
						String qry ="";
						int row =1;
						int up_row =0;
						for (row =1; row <=row_count; row++) 
						{
							String BEN_SNO =obj.isNull(obj.setValue("BENEFICIARY_SNO"+row, request), 1);
							qry =" update PMS_DCB_PR_FR "
									+ " set CUR_NET_CONSUMED=0,PR_FLAG='N',PR_UNFREEZE_DATE=clock_timestamp()"
									+ " where BENEFICIARY_SNO="+BEN_SNO
									+ " and MONTH="+month+" and YEAR="+year;
							try {
								ps =con.prepareStatement(qry);
								ps.executeUpdate();
								up_row++;
	
							} catch (Exception e) {
								// Auto-generated catch block
								e.printStackTrace();
							}
						}
						xml +="<ins_row>"+up_row+"</ins_row>";
					}
					if (command.equals("add")) {
						int freezs =Integer.parseInt(obj.setValue("freezs",request));
						if (freezs ==1) 
						{
							row_count =0;
							row_count =Integer.parseInt(obj.setValue("count",request));
							String qry ="";
							int row =1;
							int ins_row =0;
							for (row =1; row <=row_count; row++) 
							{
								String Prv_NET_CONSUMED =obj.isNull(obj.setValue("Prv_NET_CONSUMED"+row, request), 1);
								String CUR_NET_CONSUMED =obj.isNull(obj.setValue("CUR_NET_CONSUMED"+row, request), 1);
								String BENEFICIARY_SNO =obj.isNull(obj.setValue("BENEFICIARY_SNO"+row, request), 1);
								
								qry ="insert into  "+"PMS_DCB_PR_FR ("
										+ "OFFICE_ID,"+"BENEFICIARY_SNO,"
										+ "PRV_NET_CONSUMED,"+"CUR_NET_CONSUMED,"
										+ "PR_FREEZE,"+"MONTH,YEAR,"
										+ "PR_FREEZE_DATE,"+"UPDATE_USER_ID,"
										+ "UPDATE_DATE"+")"+" VALUES "+"  ("
										+ Office_id+",?,?,?,?,?,?,clock_timestamp(),'"
										+ userid+"',clock_timestamp())";
	
								PreparedStatement ps;
								try {
									ps =con.prepareStatement(qry);
									ps.setString(1, BENEFICIARY_SNO);
									ps.setString(2, CUR_NET_CONSUMED);
									ps.setString(3, Prv_NET_CONSUMED);
									ps.setString(4, "Y");
									ps.setString(5, month);
									ps.setString(6, year);
	
									try {
										String pr_count =obj.getValue("PMS_DCB_PR_FR", "count(*)","where OFFICE_ID="+Office_id
														+ " and  MONTH="+month
														+ " and YEAR="+year
														+ " and BENEFICIARY_SNO="
														+ BENEFICIARY_SNO);
	
										if (Integer.parseInt(pr_count) ==0) {
											// ps.executeUpdate();
											// ins_row++;
	
										}
	
									} catch (Exception e) 
									{
										e.printStackTrace();
									}
									String pr_count2 ="0";
									try 
									{
										pr_count2 =obj.getValue("PMS_DCB_MTH_JOURNAL", "count(*)"," where OFFICE_ID="+Office_id+ " and MONTH="+month+ " and YEAR="+year);
									} catch (Exception e) 
									{
										// Auto-generated catch block
										e.printStackTrace();
									}
									ps =null;
									qry ="insert into PMS_DCB_MTH_JOURNAL"+"(OFFICE_ID,"+"MONTH,"+"YEAR,"+ "JR_FLAG,"+"UPDATED_TIME,"+ "UPDATED_USERID,"+ "JOURNAL_PR_FREEZE_DATE) ";
									qry +=" VALUES ("+Office_id+ ",?,?,'Y',clock_timestamp(),'"+userid+ "',clock_timestamp())";
									ps =con.prepareStatement(qry);
									ps.setString(1, month);
									ps.setString(2, year);
									if (Integer.parseInt(pr_count2) ==0) {
										ps.executeUpdate();
										qry ="";
	
									}
									xml +="<ins_row>"+ins_row+"</ins_row>";
								} catch (SQLException e) {
									// Auto-generated catch block
									e.printStackTrace();
								}
							}
						} else {
	
							row_count =0;
							row_count =Integer.parseInt(obj.setValue("count",request));
							String qry ="";
							int row =1;
							int ins_row =0, up_row =0;
							for (row =1; row <=row_count; row++) 
							{
								String CUR_NET_CONSUMED =obj.isNull(obj.setValue("CUR_NET_CONSUMED"+row, request), 1);
								String BENEFICIARY_SNO =obj.isNull(obj.setValue("BENEFICIARY_SNO"+row, request), 1);
	
								qry ="update PMS_DCB_PR_FR set "+"PR_FLAG='Y',"
										+ "CUR_NET_CONSUMED="+CUR_NET_CONSUMED
										+ " where BENEFICIARY_SNO="
										+ BENEFICIARY_SNO+" and MONTH="+month
										+ " and YEAR="+year;
								try {
	
									PreparedStatement ps;
									ps =con.prepareStatement(qry);
								} catch (Exception e) 
								{
									e.printStackTrace();
								}
							}
							xml +="<ins_row>"+up_row+"</ins_row>";
						}
					}// Commend add end
					xml +="</result>";
	
				}
				PrintWriter pr =null;
				pr =response.getWriter();
				pr.write(xml);
				pr.flush();
				pr.close();
			}
			obj.conClose(con);
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
