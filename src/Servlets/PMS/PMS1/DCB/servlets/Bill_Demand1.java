
package Servlets.PMS.PMS1.DCB.servlets;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
public class Bill_Demand1 extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public void init(ServletConfig config) throws ServletException 
{
		super.init(config);
}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		Map parameters = new HashMap();
		Controller obj = new Controller();
		Controller obj2 = new Controller();		
		String new_cond=Controller.new_cond;
		String meter_status=Controller.meter_status;
		int maxsno = 0, tran_row = 0, row = 0;
		Connection con = null;
		Connection con1 = null;		
		Statement stmt = null, stmt2 = null, stmt3 = null, stmt4 = null, stmt5 = null;
		PreparedStatement ps = null;
		ResultSet rs = null, rs_wc = null, rs_coll = null, rs_sch_type = null;
		JasperPrint jasperPrint_dcb ;
		OutputStream outuputStream_dcb;
		String xml = "", qry = "", Office_id = "0", Office_name = "", userid = "0", ACCOUNTING_UNIT_ID = "";
		String WC_MTH_TOTAL = "";
		String msg = " ",ins_qry2 = "";
		double amount = 0.0, excessamount = 0.0, netamount = 0.0;
		String command = request.getParameter("command");// Command
		OutputStream outuputStream2 =null;
		JasperPrint jasperPrint2 =null;
		// ->view,insert etc
		if (command == null || command.equals("")) 	command = "no command";
		String input_value = request.getParameter("input_value"); // input value
		if (input_value == null || input_value.equals(""))	input_value = "0";
		String process_code = request.getParameter("process_code");// process
		String option_pdf_xls=obj.setValue("optionpdfxls" ,request);
		System.out.println("option_pdf_xls"+option_pdf_xls);
		System.out.println("DCB->Bill_Demand->command->" + command 	+ "->process_code->" + process_code);
		if (process_code == null || process_code.equals("")) process_code = "0";
		try {
				con = obj.con();
				stmt = con.createStatement();
				stmt2 = con.createStatement();
				stmt3 = con.createStatement();  
				obj.createStatement(con);
			 	con1= obj.con();
				HttpSession session = request.getSession(false);
				userid = (String) session.getAttribute("UserId");
				if (userid == null) 
				{
						response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
				if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
				Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
				if (Office_name.equals("")) Office_name = "";
				String sel_qry = "SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME, ACCOUNTING_UNIT_ID 	FROM FAS_MST_ACCT_UNITS	WHERE ACCOUNTING_UNIT_OFFICE_ID =" + Office_id;
				rs_sch_type = stmt3.executeQuery(sel_qry);					
				if (rs_sch_type.next()) 
				{
					ACCOUNTING_UNIT_ID = obj.isNull(rs_sch_type.getString(1), 1);
				}
				String month = obj.setValue("fmonth", request);// Command // ->view,insert etc
				String year = obj.setValue("fyear", request);
				String path = "";
				String cmonth = obj.setValue("fmonth", request);// Command ->view,insert etc
				String cyear = obj.setValue("fyear", request);// Command->view,insert etc
				String process = process_code;
				try 
				{
							process = process_code;
				} catch (Exception e) {
							process = process_code;
				}   
				String ctxpath = "";
				String mvalue = obj.month_val(obj.setValue("fmonth", request));
				// Interest Report 
				if (command.trim().equalsIgnoreCase("localbodyintreport"))
				{
				    response.setContentType("application/pdf");   
				   	String MONTH = obj.isNull(obj.getValue("PMS_DCB_SETTING","MONTH", " where OFFICE_ID=" + Office_id), 1);
					year = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
					parameters.put("year", year);
					parameters.put("month", MONTH);
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", obj.month_val(MONTH));
					parameters.put("office_name", obj.getValue("COM_MST_OFFICES","OFFICE_NAME", "where OFFICE_ID=" + Office_id));
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Int_localbody_new.jasper");
					response.setHeader("Content-Disposition","attachment; filename=\"Int_localbody_new.pdf\"");
					outuputStream2 = response.getOutputStream();  
					jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);
					JRExporter exporter = null;
					try {
							exporter = new JRPdfExporter();
							exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
							exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
							exporter.exportReport();
							outuputStream2.close();
						} catch (Exception e)
						{
							System.out.println(e);
						}
				}else if (command.trim().equalsIgnoreCase("ben_wise_dcb_"))
				{
				    response.setContentType("application/pdf");   
				   	String MONTH = obj.isNull(obj.getValue("PMS_DCB_SETTING","MONTH", " where OFFICE_ID=" + Office_id), 1);
					year = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
					parameters.put("year", year);
					parameters.put("month", MONTH);
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", obj.month_val(MONTH));
					parameters.put("office_name", obj.getValue("COM_MST_OFFICES","OFFICE_NAME", "where OFFICE_ID=" + Office_id));
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/dcb_stmt_ho.jasper");
					response.setHeader("Content-Disposition","attachment; filename=\"dcb_stmt_ho.pdf\"");
					outuputStream2 = response.getOutputStream();  
					jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);
					JRExporter exporter = null;
					try {
							exporter = new JRPdfExporter();
							exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
							exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
							exporter.exportReport();
							outuputStream2.close();
						} catch (Exception e)
						{
							System.out.println(e);
						}
				}
				else if (command.trim().equalsIgnoreCase("scheme_account_mapping_missing"))
				{  
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Schme_Account_Mapping_Missing.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
					OutputStream outuputStream = response.getOutputStream();
					JRExporter exporter = null;
					response.setContentType("application/pdf");     
					response.setHeader("Content-Disposition","attachment; filename=\"Schme_Account_Mapping_Missing.pdf\"");
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter( JRExporterParameter.OUTPUT_STREAM,outuputStream);
					exporter.exportReport();
					outuputStream.close();
				} else if (command.equalsIgnoreCase("mdrpt")) 
				{ 
					int month_set = 0,year_set = 0;
					String arr_set = "",curset = "";
					if ((Integer.parseInt(obj.setValue("fmonth", request))) == 1) {
						month_set = 12;
						year_set = Integer.parseInt(obj.setValue("fyear", request)) - 1;
						arr_set = month_set + "/" + year_set;
						curset = "   " + obj.setValue("fmonth", request)+ "/" + obj.setValue("fyear", request);
					} else {
						month_set = (Integer.parseInt(obj.setValue("fmonth",request)) - 1);
						year_set = Integer.parseInt(obj.setValue("fyear", request));
						arr_set = month_set + "/" + year_set;
						curset = "   " + obj.setValue("fmonth", request)+ "/" + obj.setValue("fyear", request);
						}
					if (Office_id.equalsIgnoreCase("5000"))
						Office_id=obj.setValue("div", request);
						Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
						String arr_label="",arr_year_label="";
							response.setContentType("application/pdf");
							outuputStream2 = response.getOutputStream();
							parameters.put("month", obj.setValue("fmonth", request));
							parameters.put("year", obj.setValue("fyear", request));
							parameters.put("pyear",Integer.toString( year_set));
							parameters.put("pmonth", Integer.toString( month_set));
							parameters.put("monthval", mvalue);
							parameters.put("off_name", Office_name);
							parameters.put("arr_set", arr_set);
							parameters.put("curset", curset); 
							 if (Integer.parseInt(month)==2)    
		    				 {
		    					 arr_label="12";
		    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
		    				 }else if(Integer.parseInt(month)==1)   
		    				 {
		    					 arr_label="11";    
		    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
		    				 }else   
		    				 {    					 
		    					 arr_label=Integer.toString(Integer.parseInt(month)-2);
		    					 arr_year_label=year;
		    				 }
							 parameters.put("arr_label",arr_label);
	 	    				 parameters.put("arr_year_label",arr_year_label);  
						if (process.equalsIgnoreCase("1")) 
						{
								parameters.put("office_id", Integer.parseInt(Office_id));
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/MIS_MAIN.jasper");
								ctxpath = path.substring(0, path.lastIndexOf("MIS_MAIN.jasper"));
								parameters.put("ctxpath", ctxpath);
								response.setHeader("Content-Disposition","attachment; filename=\"MIS_BEN_Actual.pdf\"");
						} else if (process.equalsIgnoreCase("2")) 
						{
								parameters.put("office_id", Office_id);
								parameters.put("off_name", Office_name);
								parameters.put("arr_set", arr_set);
								parameters.put("curset", curset);
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/newReviewReport12.jasper");														   
								ctxpath = path.substring(0, path.lastIndexOf("newReviewReport12.jasper"));	 
								parameters.put("ctxpath", ctxpath);
								response.setHeader("Content-Disposition","attachment; filename=\"newReviewReport12.pdf\"");
						} else if (process.equalsIgnoreCase("4")) 
						{
							parameters.put("office_id", Office_id);
							parameters.put("off_name", Office_name);
							parameters.put("arr_set", arr_set);
							parameters.put("curset", curset);
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/MD_Review_Ben_Type.jasper");
							ctxpath = request.getRequestURL().toString();    
							parameters.put("ctxpath", ctxpath);
							response.setHeader("Content-Disposition","attachment; filename=\"ANNEXURE_I_MD_Review_lks.pdf\"");
						}else if (process.equalsIgnoreCase("5")) 
						{
							parameters.put("office_id", Office_id);
							parameters.put("off_name", Office_name);
							parameters.put("arr_set", arr_set);
							parameters.put("curset", curset);
							parameters.put("ctxpath", ctxpath);
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Review_BEN.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("New_Review_BEN.jasper"));	 
							parameters.put("ctxpath", ctxpath);
							response.setHeader("Content-Disposition","attachment; filename=\"ANNEXURE_II_MD_Review_lks.pdf\"");
						}else if (process.equalsIgnoreCase("55")) 
						{
							parameters.put("office_id", Office_id);
							parameters.put("off_name", Office_name);
							parameters.put("arr_set", arr_set);
							parameters.put("curset", curset);
							parameters.put("ctxpath", ctxpath);  
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Review_BEN_Actual.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("New_Review_BEN_Actual.jasper"));	 
							parameters.put("ctxpath", ctxpath);
							response.setHeader("Content-Disposition","attachment; filename=\"ANNEXURE_II_MD_Review.pdf\"");
						}else if (process.equalsIgnoreCase("6")) 
						{
							parameters.put("office_id", Office_id);
							parameters.put("off_name", Office_name);
							parameters.put("arr_set", arr_set);
							parameters.put("curset", curset);
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Review_BEN_VP.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("New_Review_BEN_VP.jasper"));	 
							parameters.put("ctxpath", ctxpath);
							response.setHeader("Content-Disposition","attachment; filename=\"Beneficiary_wise_Review.pdf\"");
						}else if (process.equalsIgnoreCase("66")) 
						{
							parameters.put("office_id", Office_id);
							parameters.put("off_name", Office_name);
							parameters.put("arr_set", arr_set);
							parameters.put("curset", curset);
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Review_BEN_VP_LKS.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("New_Review_BEN_VP_LKS.jasper"));	 
							parameters.put("ctxpath", ctxpath);
							response.setHeader("Content-Disposition","attachment; filename=\"Beneficiary_wise_Review.pdf\"");
						}else if (process.equalsIgnoreCase("7")) 
						{
							parameters.put("office_id", Office_id);
							parameters.put("off_name", Office_name);
							parameters.put("arr_set", arr_set);
							parameters.put("curset", curset);
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Replaced_MD_Review_Actual.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("New_Replaced_MD_Review_Actual.jasper"));	 
							parameters.put("ctxpath", ctxpath);
							response.setHeader("Content-Disposition","attachment; filename=\"ANNEXURE_I_MD_Review.pdf\"");
						}else if (process.equalsIgnoreCase("9"))  
						{
							parameters.put("office_id", Office_id);
							parameters.put("off_name", Office_name);
							parameters.put("arr_set", arr_set);
							parameters.put("curset", curset);
							parameters.put("pyear",Integer.toString( year_set));
							parameters.put("pmonth", Integer.toString( month_set)); 
							if (Integer.parseInt(month)==2)    
		    				 {
		    					 arr_label="12";
		    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
		    				 }else if(Integer.parseInt(month)==1)   
		    				 {
		    					 arr_label="11";    
		    					 arr_year_label=Integer.toString(Integer.parseInt(year)-1);
		    				 }else   
		    				 {    					 
		    					 arr_label=Integer.toString(Integer.parseInt(month)-2);
		    					 arr_year_label=year;
		    				 }
							parameters.put("arr_label",arr_label);
	 	    				parameters.put("arr_year_label",arr_year_label); 
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/New_Review_BEN_Wint.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("New_Review_BEN_Wint.jasper"));	 
							parameters.put("ctxpath", ctxpath);
							response.setHeader("Content-Disposition","attachment; filename=\"New_Replaced_MD_Review_Actual.pdf\"");
						}
						else if (process.equalsIgnoreCase("3"))
						{
								if ( !Office_id.trim().equalsIgnoreCase("5000")) 
								{
									parameters.put("office_id", Office_id);
									parameters.put("monthval", mvalue);
									parameters.put("month", obj.setValue("fmonth", request));
									parameters.put("year", obj.setValue("fyear", request));
									path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCBRECEIPTSTMT_div.jasper");
									ctxpath = path.substring(0, path.lastIndexOf("DCBRECEIPTSTMT_div.jasper"));
								}
								else 
								{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCBRECEIPTSTMT.jasper");
								ctxpath = path.substring(0, path.lastIndexOf("DCBRECEIPTSTMT.jasper"));
								}
								response.setHeader("Content-Disposition","attachment; filename=\"DCB RECEIPT STMT.pdf\"");
								parameters.put("ctxpath", ctxpath);
						}else if (process.equalsIgnoreCase("333"))
						{  
							 
								parameters.put("office_id", Office_id);
								parameters.put("monthval", mvalue);
								parameters.put("month", obj.setValue("fmonth", request));
								parameters.put("year", obj.setValue("fyear", request));
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCBRECEIPTSTMT_HO.jasper");
								ctxpath = path.substring(0, path.lastIndexOf("DCBRECEIPTSTMT_HO.jasper"));
								response.setHeader("Content-Disposition","attachment; filename=\"Scheme Wise Collection .pdf\"");
								parameters.put("ctxpath", ctxpath);
					}
						String imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("imgpath", imagespath);
						   jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);
						     
						   if (option_pdf_xls.equalsIgnoreCase("1"))   
			               {  
								JRExporter exporter = null;
								try {
									exporter = new JRPdfExporter();
									exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
									exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
									exporter.exportReport();
									outuputStream2.close();
								} catch (Exception e) {    
									System.out.println(e);
								}
			               }
						   else
						   {
							   response.setContentType("application/vnd.ms-excel");
			                   response.setHeader ("Content-Disposition", "attachment;filename=\"Report.xls\"");
			                   JRXlsExporter exporterXLS = new JRXlsExporter(); 
			                   exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint2); 
			                   ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			                   exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
			                   exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
			                   exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
			                   exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
			                   exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
			                   exporterXLS.exportReport(); 
			                   byte []bytes;
			                   bytes = xlsReport.toByteArray();
			                   ServletOutputStream ouputStream = response.getOutputStream();
			                   ouputStream.write(bytes, 0, bytes.length);
			                   ouputStream.flush();
			                   ouputStream.close();
						   }
			} // command mdrpt is end 
			else
			{
				if (command.equalsIgnoreCase("newrecord_pdf"))
				{
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/count_report_new.jasper");
					Map parameters1 = new HashMap();
					parameters1.put("month", obj.setValue("fmonth", request));
					parameters1.put("year", obj.setValue("fyear", request));
					parameters1.put("monthval", mvalue);    
					parameters1.put("office_id", obj.setValue("office_id",request));
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
					OutputStream outuputStream_n = response.getOutputStream();
					JRExporter exporter = null;
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"Division_Record_Count.pdf\"");
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_n);
					exporter.exportReport();
					outuputStream_n.close();
				}  
				if (command.equalsIgnoreCase("record_pdf")&& process.equalsIgnoreCase("2")) 
				{
					parameters.put("month", obj.setValue("fmonth", request));
					parameters.put("year", obj.setValue("fyear", request));
					parameters.put("monthval", mvalue);    
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/count_report_new.jasper");
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition","attachment;filename=\"count_report.xls\"");
					JRXlsExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
					ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
					exporterXLS.exportReport();
					byte[] bytes;
					bytes = xlsReport.toByteArray();
					ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
				}else if (Integer.parseInt(process_code) == 555)
		        {
		        		path = getServletContext().getRealPath("/WEB-INF/ReportSrc/list_of_journals.jasper");
		        		ctxpath = path.substring(0, path.lastIndexOf("list_of_journals.jasper"));
						Map parameters2 = new HashMap();
						parameters2.put("unitId",3);
						parameters2.put("officeId", 5000);
						parameters2.put("year1",2011);
						parameters2.put("month1", 8);
						parameters2.put("voucherNo", 5);
						parameters2.put("SUBREPORT_DIR", ctxpath);
						  JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters2, con);
						OutputStream outuputStream_1n = response.getOutputStream();
						JRExporter exporter = null;
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment; filename=\"All_ben_list.pdf\"");
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_1n);
						exporter.exportReport();
						outuputStream_1n.close();
		        } 
				else if (command.equalsIgnoreCase("record_pdf")&& process.equalsIgnoreCase("1")) 
				{  int option = Integer.parseInt(obj.setValue("option",request));
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/count_report_new2.jasper");
						Map parameters1 = new HashMap();
						parameters1.put("month", obj.setValue("fmonth", request));
						parameters1.put("year", obj.setValue("fyear", request));
						parameters1.put("monthval", mvalue);    
						parameters1.put("office_id", obj.setValue("office_id",request));
						JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
									if (option == 1) 
									{					
									OutputStream outuputStream_n = response.getOutputStream();
									JRExporter exporter = null;
									response.setContentType("application/pdf");
									response.setHeader("Content-Disposition","attachment; filename=\"Division_Record_Count.pdf\"");
									exporter = new JRPdfExporter();
									exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
									exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_n);
									exporter.exportReport();
									outuputStream_n.close();
									} else {
									response.setContentType("application/vnd.ms-excel");
									response.setHeader("Content-Disposition","attachment;filename=\"Receipt.xls\"");
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
									ServletOutputStream ouputStream = response.getOutputStream();
									ouputStream.write(bytes, 0, bytes.length);
									ouputStream.flush();  
									ouputStream.close();
								}
				}else if (command.equalsIgnoreCase("record_pdf")&& process.equalsIgnoreCase("3")) 
				{  
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/count_report_new_div.jasper");
					Map parameters1 = new HashMap();
					parameters1.put("month", obj.setValue("fmonth", request));  
					parameters1.put("year", obj.setValue("fyear", request));
					parameters1.put("monthval", mvalue);    
					parameters1.put("office_id",Office_id);  
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
					OutputStream outuputStream_n = response.getOutputStream();
					JRExporter exporter = null;
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"Division_Record_Count.pdf\"");
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_n);
					exporter.exportReport();
					outuputStream_n.close();
				}else if (command.equalsIgnoreCase("record_pdf")&& process.equalsIgnoreCase("4")) 
				{  
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/count_report_cao.jasper");
					Map parameters1 = new HashMap();
					parameters1.put("month", obj.setValue("fmonth", request));  
					parameters1.put("year", obj.setValue("fyear", request));
					parameters1.put("monthval", mvalue);    					 
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
					OutputStream outuputStream_n = response.getOutputStream();
					JRExporter exporter = null;
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"count_report_cao.pdf\"");
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_n);
					exporter.exportReport();
					outuputStream_n.close();
				}
				else if (command.equalsIgnoreCase("All_benList")) 
				{
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/HO_All_BeneficiaryList.jasper");
					Map parameters1 = new HashMap();
					parameters1.put("office_id", obj.setValue("office_id",request));
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
					OutputStream outuputStream_1n = response.getOutputStream();
					JRExporter exporter = null;
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"All_ben_list.pdf\"");
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_1n);
					exporter.exportReport();
					outuputStream_1n.close();
				}else if (command.equalsIgnoreCase("All_benList_RO")) {
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/All_BeneficiaryList.jasper");
					Map parameters1 = new HashMap();
					parameters1.put("office_id", obj.setValue("office_id",request));
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
					OutputStream outuputStream_1n = response.getOutputStream();
					JRExporter exporter = null;
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"All_ben_list.pdf\"");
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_1n);
					exporter.exportReport();
					outuputStream_1n.close();
				}else if (command.equalsIgnoreCase("AllDiv_pumppdf")) 
				{
				    mvalue="0";
				    mvalue = obj.month_val(obj.setValue("fmonth",request));
					Office_id = obj.setValue("Office_id", request);
					Office_name = obj.getValue("COM_MST_OFFICES","OFFICE_NAME", "where OFFICE_ID=" + Office_id);
					parameters.put("month", obj.setValue("fmonth", request));
					parameters.put("year", obj.setValue("fyear", request));
					parameters.put("office_id", Office_id);
					parameters.put("mvalue", mvalue);
					String imagespath = getServletContext().getRealPath("/images/")+File.separator;
				 	parameters.put("imgpath", imagespath);  
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PumpingPR_All.jasper");
					  ctxpath = path.substring(0, path.lastIndexOf("PumpingPR_All.jasper"));
					jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
					outuputStream2 = response.getOutputStream();
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"PumpingReturn_Div.pdf\"");
					JRExporter exporter = null;
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				}else if (command.equalsIgnoreCase("PR_SCH_WISE")) 
				{
					mvalue="";
					mvalue = obj.month_val(obj.setValue("fmonth",request));
					Office_id = obj.setValue("Office_id", request);
					parameters.put("month", obj.setValue("fmonth", request));
					parameters.put("year", obj.setValue("fyear", request));
					parameters.put("office_id", Office_id);
					parameters.put("monthvalue", mvalue);  
					parameters.put("year1", obj.setValue("fyear", request) );
					parameters.put("month1", obj.setValue("fmonth", request)); 	  
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Total_pump_qty.jasper");
					  ctxpath = path.substring(0, path.lastIndexOf("Total_pump_qty.jasper"));
					jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
					   outuputStream2 = response.getOutputStream();
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"Pumping_Return_Scheme_wise.pdf\"");
					JRExporter exporter = null;
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				}else if (command.equalsIgnoreCase("AllDiv_recpdf")) {
					mvalue="";
					mvalue = obj.month_val(obj.setValue("fmonth",request));
					Office_id = obj.setValue("Office_id", request);
					Office_name = obj.getValue("COM_MST_OFFICES","OFFICE_NAME", "where OFFICE_ID=" + Office_id);
 					parameters.put("month", obj.setValue("fmonth", request));
					parameters.put("year", obj.setValue("fyear", request));
					parameters.put("Office_id", Office_id);
					parameters.put("office_name", Office_name);
					parameters.put("mvalue", mvalue);
					String imagespath = getServletContext().getRealPath("/images/")+File.separator;
				 	parameters.put("imgpath", imagespath);  
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/receipt_all.jasper");
					  ctxpath = path.substring(0, path.lastIndexOf("receipt_all.jasper"));
					  jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
					  outuputStream2 = response.getOutputStream();
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"Receipt.pdf\"");
					JRExporter exporter = null;
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				}
				if (command.equalsIgnoreCase("recpdf")) {
					try {
						int pro = Integer.parseInt(obj.setValue("pro", request));
						int option = Integer.parseInt(obj.setValue("option",request));
						mvalue ="";
						mvalue = obj.month_val(obj.setValue("fmonth",request));
						parameters.put("Office_id", Office_id);
						parameters.put("year", obj.setValue("fyear", request));
						parameters.put("month", obj.setValue("fmonth", request));
						parameters.put("office_name", Office_name);
						parameters.put("mvalue", mvalue);
						String imagespath = getServletContext().getRealPath("/images/")+File.separator;
					 	parameters.put("imgpath", imagespath);  
						ctxpath = null;
						if (pro == 1) 
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/receipt.jasper");
							parameters.put("ben_sel", obj.setValue("ben_sel",request));
							ctxpath = path.substring(0, path.lastIndexOf("receipt.jasper"));
						} else 	if (pro == 3) 
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/receipt_all_wc.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("receipt_all_wc.jasper"));
						} else 	if (pro == 4) 
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/receipt_all_int.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("receipt_all_int.jasper"));
						} else 	if (pro == 5) 
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/receipt_all_arrears_mc.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("receipt_all_arrears_mc.jasper"));
						}
						else 
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/receipt_all.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("receipt_all.jasper"));
						}   
						JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
						outuputStream2 = response.getOutputStream();
						JRExporter exporter = null;
						if (option == 1) {
							response.setContentType("application/pdf");
							response.setHeader("Content-Disposition","attachment; filename=\"Receipt.pdf\"");
							exporter = new JRPdfExporter();
							exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
							exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
							exporter.exportReport();
							outuputStream2.close();
						} else {
							response.setContentType("application/vnd.ms-excel");
							response.setHeader("Content-Disposition","attachment;filename=\"Receipt.xls\"");
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
							ServletOutputStream ouputStream = response.getOutputStream();
							ouputStream.write(bytes, 0, bytes.length);
							ouputStream.flush();
							ouputStream.close();
						}
					} catch (JRException e) {
						throw new ServletException(e);
					}
					xml = "<result>Report";
					xml += "</result>";
				}	else if (command.equalsIgnoreCase("new_DCB")) 
				{
					response.setContentType("application/pdf");
					String oid=obj.setValue("oid",request);
					String	imagespath = getServletContext().getRealPath("/images/")+File.separator;
					parameters.put("imgpath", imagespath);  
					if (oid.equalsIgnoreCase("0")) Office_id=Office_id; else Office_id=oid;					
					try {
						String sname= request.getParameter("sname");
						mvalue="";
						mvalue = obj.month_val(obj.setValue("fmonth",request));
						if (process_code.equalsIgnoreCase("1"))
						{
							int month_set = 0;
							int year_set = 0;  
							if ((Integer.parseInt(obj.setValue("fmonth",request))) == 1) {
								month_set = 12;
								year_set = Integer.parseInt(obj.setValue("fyear", request)) - 1;
							} else {
								month_set = (Integer.parseInt(obj.setValue("fmonth",request)) - 1);
								year_set = Integer.parseInt(obj.setValue("fyear", request));
							}
							if (Office_id.trim().equalsIgnoreCase("5000"))
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/gen_abs.jasper");
							}else
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/gen_abs_div.jasper");					 
								parameters.put("office_id", Office_id);
								parameters.put("office_name", Office_name);
							}
							parameters.put("pmonth", month_set);  
							parameters.put("pyear", year_set);
						}
						if (process_code.equalsIgnoreCase("2"))
						{
							if (Office_id.trim().equalsIgnoreCase("5000"))
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_abs.jasper");
							}else
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_abs_div.jasper");					 
								parameters.put("office_id", Office_id);
								parameters.put("office_name", Office_name);
							}
							parameters.put("schtype", obj.setValue("schtype",request));  
						}
						if (process_code.equalsIgnoreCase("3"))
						{
							if (Office_id.trim().equalsIgnoreCase("5000"))
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Cons_abstract.jasper");
							}
							else
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Cons_abstract_div.jasper");
								parameters.put("office_id", Office_id);
								parameters.put("office_name", Office_name);
							}
 						}
						if (process_code.equalsIgnoreCase("5"))
						{
							if (Office_id.trim().equalsIgnoreCase("5000"))
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_abs_stype.jasper");
							}else
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_abs_stype_div.jasper");
								parameters.put("office_id", Office_id);
								parameters.put("office_name", Office_name);
							}
							parameters.put("ben_type", obj.setValue("bentype",request));
							parameters.put("schtype", obj.setValue("schtype",request));
						}else if (process_code.equalsIgnoreCase("6"))
							{
							if (Office_id.trim().equalsIgnoreCase("5000"))
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_district_abs.jasper");
							}else
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_district_abs_div.jasper");
								parameters.put("office_id", Office_id);
								parameters.put("office_name", Office_name);
							}
								parameters.put("ben_type", obj.setValue("bentype",request));
								parameters.put("schtype", obj.setValue("schtype",request));
								String sch_cat_desc=obj.getValue("PMS_SCH_LKP_CATEGORY", "CATEGORY_DESC"," where SCH_CATEGORY_ID="+obj.setValue("schtype",request));
								String ben_type_desc=obj.getValue("PMS_DCB_BEN_TYPE", "BEN_TYPE_DESC"," where BEN_TYPE_ID="+obj.setValue("bentype",request));
								parameters.put("ben_type_desc", ben_type_desc);
								parameters.put("sch_cat_desc",sch_cat_desc);
							}
						else if (process_code.equalsIgnoreCase("4"))
						{
							if (Office_id.trim().equalsIgnoreCase("5000"))
							{	
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_abs_btype.jasper");
							}
							else
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_abs_btype_div.jasper");
								parameters.put("office_id", Office_id);
								parameters.put("office_name", Office_name);
							}
							parameters.put("schtype", obj.setValue("schtype",request));
						}
						else if (process_code.equalsIgnoreCase("7"))
						{
							if (Office_id.trim().equalsIgnoreCase("5000"))
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_vdis_abs.jasper");
							}
							else
							{
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidated_vdis_abs_div.jasper");
								parameters.put("office_id", Office_id);
								parameters.put("office_name", Office_name);
							}
						parameters.put("ben_type", obj.setValue("bentype",request));
						parameters.put("schtype", obj.setValue("schtype",request));
						String sch_cat_desc=obj.getValue("PMS_SCH_LKP_CATEGORY", "CATEGORY_DESC"," where SCH_CATEGORY_ID="+obj.setValue("schtype",request));
						String ben_type_desc=obj.getValue("PMS_DCB_BEN_TYPE", "BEN_TYPE_DESC"," where BEN_TYPE_ID="+obj.setValue("bentype",request));
					 	parameters.put("ben_type_desc", ben_type_desc);
					 	parameters.put("sch_cat_desc",sch_cat_desc);
						}
						parameters.put("year", obj.setValue("fyear", request));
						parameters.put("month", obj.setValue("fmonth", request));
						parameters.put("mvalue", mvalue);
						parameters.put("sname", sname);
						int p = 1;
						String cond = "";
						jasperPrint_dcb = JasperFillManager.fillReport(path, parameters, con);
						outuputStream_dcb = response.getOutputStream();
						JRExporter exporter = null;
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment; filename=\"DCBSTMT.pdf\"");
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint_dcb);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_dcb);
						exporter.exportReport();
						outuputStream_dcb.close();
					} catch (JRException e) {
						throw new ServletException(e);
					}
					xml = "<result>Report";
					xml += "</result>";
				} else if (command.equalsIgnoreCase("Collection3")) {
					try {
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB3.jasper");
						mvalue="";
						mvalue = obj.month_val(obj.setValue("fmonth",request));
						  ctxpath = null;
						ctxpath = path.substring(0, path.lastIndexOf("DCB3.jasper"));
						parameters.put("BEN_TYPE_ID", obj.setValue("bentype",request));
						parameters.put("SCH_TYPE_ID", obj.setValue("schtype",request));
						parameters.put("YEAR", obj.setValue("fyear", request));
						parameters.put("month", obj.setValue("fmonth", request));
						parameters.put("mvalue", mvalue);
						int p = 1;
						String cond = "";
						JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
						OutputStream outuputStream4 = response.getOutputStream();
						JRExporter exporter = null;
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment; filename=\"Collection.pdf\"");
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream4);
						exporter.exportReport();
						outuputStream4.close();
					} catch (JRException e) {
						throw new ServletException(e);
					}
					xml = "<result>Report";
					xml += "</result>";
				} else if (command.equalsIgnoreCase("UnitwiseAbstrct")) {
					try {
						String bensno = "";
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB4.jasper");
						mvalue="";
						mvalue = obj.month_val(obj.setValue("fmonth",request));
						  ctxpath = null;
						ctxpath = path.substring(0, path.lastIndexOf("DCB4.jasper"));
						parameters.put("YEAR", obj.setValue("fyear", request));
						parameters.put("month", obj.setValue("fmonth", request));
						parameters.put("mvalue", mvalue);
						JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
						OutputStream outuputStream3 = response.getOutputStream();
						JRExporter exporter = null;
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment; filename=\"UnitWiseAbstract.pdf\"");
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream3);
						exporter.exportReport();
						outuputStream3.close();
					} catch (JRException e) {
						throw new ServletException(e);
					}
					xml = "<result>Report";
					xml += "</result>";
				}
				else if (command.equalsIgnoreCase("Collection2")) {
					try {
						String bensno = "";
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Collection2.jasper");
						mvalue="";
						mvalue = obj.month_val(obj.setValue("fmonth",request));
						  ctxpath = null;
						ctxpath = path.substring(0, path.lastIndexOf("Collection2.jasper"));
						parameters.put("bentype", obj.setValue("bentype",request));
						parameters.put("yearp", Integer.parseInt(obj.setValue("fyear", request)));
						parameters.put("monthp", Integer.parseInt(obj.setValue("fmonth", request)));
						parameters.put("mvalue", mvalue);
						int p = 1;
						String cond = "";
						JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
						OutputStream outuputStream3 = response.getOutputStream();
						JRExporter exporter = null;
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment; filename=\"Collection.pdf\"");
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream3);
						exporter.exportReport();
						outuputStream3.close();
					} catch (JRException e) {
						throw new ServletException(e);
					}
					xml = "<result>Report";
					xml += "</result>";
				}
				else if (command.equalsIgnoreCase("prmispdf")) {
					try {
						String bensno = "",imagespath="";
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/prstatus.jasper");
						mvalue="";
						mvalue = obj.month_val(obj.setValue("fmonth",request));
						  ctxpath = null;
						ctxpath = path.substring(0, path.lastIndexOf("prstatus.jasper"));
						parameters.put("Office_id", Office_id);
						parameters.put("year", obj.setValue("fyear", request));
						parameters.put("month", obj.setValue("fmonth", request));
						parameters.put("office_name", Office_name);
						parameters.put("mvalue", mvalue);
						imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("imgpath", imagespath);    
						int p = 1;
						String cond = "";
						JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
						  outuputStream2 = response.getOutputStream();
						JRExporter exporter = null;
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment; filename=\"prstatus.pdf\"");
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
						exporter.exportReport();
						outuputStream2.close();
					} catch (JRException e) {
						throw new ServletException(e);
					}
					xml = "<result>Report";
					xml += "</result>";
				} else if (command.equalsIgnoreCase("mispdf")) {
					String office_id = obj.setValue("div", request);// Command->view,insert
					try {
						String bensno = "",imagespath="";
						mvalue="";
						mvalue = obj.month_val(obj.setValue("fmonth",request));
						ctxpath = null;
						path = "";
						String inp_div = obj.setValue("div", request);
						if (Integer.parseInt(inp_div) == 0) {
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DivisionWise.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("DivisionWise.jasper"));
						} else {
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DivisionWise_div.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("DivisionWise_div.jasper"));
						}
						parameters.put("Office_id", obj.setValue("div", request));
						parameters.put("year", obj.setValue("fyear", request));
						parameters.put("month", obj.setValue("fmonth", request));
						parameters.put("ctxpath", ctxpath);
						parameters.put("mvalue", mvalue);
						parameters.put("cond", obj.setValue("div", request));
						imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("imgpath", imagespath);    
						int p = 1;
						String cond = "";
						JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
						OutputStream outuputStream1 = response.getOutputStream();
						JRExporter exporter = null;
						response.setContentType("application/pdf");
						response.setHeader("Content-Disposition","attachment; filename=\"DivisionWiseDCB.pdf\"");
						exporter = new JRPdfExporter();
						exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
						exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream1);
						exporter.exportReport();
						outuputStream1.close();
					} catch (JRException e) {
						throw new ServletException(e);
					}
					xml = "<result>Report";
					xml += "</result>";
				}
				if (command.equals("frstatuscheck")) {
					xml = "<result>";
					String FR_row = obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)", "where  MONTH=" + cmonth + " and YEAR="+ cyear+ " and METRE_SNO in ( select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE   where OFFICE_ID="+Office_id+" and METER_STATUS='L')  and PROCESS_FLAG='FR' and OFFICE_ID="+ Office_id);
					xml += "<FR_row>" + FR_row + "</FR_row></result>";
				} else if (command.equals("demand_delete")) {
					try {
						String del_billyear = obj.setValue("billyear", request);
						String del_billmonth = obj.setValue("billmonth",request);
						String del_qry = "delete   from PMS_DCB_TRN_BILL_DMD_SCH where office_id="+ Office_id
								+ "  and BILL_SNO in ( select BILL_SNO from PMS_DCB_TRN_BILL_DMD where office_id="+ Office_id+ " and bill_month="+ del_billmonth+ " and BILL_YEAR=" + del_billyear + ")";
						PreparedStatement ps_del = con.prepareStatement(del_qry); 
						ps_del.executeUpdate();
						ps_del = null;
						del_qry = "delete from PMS_DCB_TRN_BILLING_DMD where office_id="+ Office_id+ "  and bill_month="+ del_billmonth+ " and BILL_YEAR="+ del_billyear + "";
						ps_del = con.prepareStatement(del_qry);
						ps_del.executeUpdate();
						ps_del = null;
						del_qry = "delete    from PMS_DCB_OB_YEARLY where office_id="+ Office_id+ " and month="+ del_billmonth+ " and fin_year=" + del_billyear;
						ps_del = con.prepareStatement(del_qry);
						ps_del.executeUpdate();
						ps_del = null;
						del_qry = "delete from PMS_DCB_TRN_CB_MONTHLY where office_id="+ Office_id+ " and month="+ (Integer.parseInt(del_billmonth) - 1)+ " and fin_year=" + del_billyear;
						ps_del = con.prepareStatement(del_qry);
						ps_del.executeUpdate();
					} catch (Exception e) {
					}
/****** DEMAND INSERT FOR NORMAL SCHEME****************************************************/
				} else if (command.equals("demand_insert")) 
				{
					String OB_INT_CUR_YR_MAINT = "0", OB_INT_AMT_WC = "" ,ben_int = "";
					String BILL_PERIOD_FROM = obj.setValue("datefrom", request);
					String BILL_PERIOD_TO = obj.setValue("dateto", request);
					String billyear = obj.setValue("billyear", request);
					String billmonth = obj.setValue("billmonth", request);
					String date = obj.setValue("date", request);
					int month_set = 0,// demand Gen month-1 setting 
					    year_set = 0, //Year Setting
					    month_set2 = 0,// demand Gen Month-2 Setting
					    year_set2 = 0;// Year Setting
					if ((Integer.parseInt(billmonth)) == 1) {
						month_set = 12;
						year_set = Integer.parseInt(billyear) - 1;
					} else {
						month_set = (Integer.parseInt(billmonth) - 1);
						year_set = Integer.parseInt(billyear);
					}
					if ((Integer.parseInt(billmonth)) == 1) {
						month_set2 = 11;
						year_set2 = Integer.parseInt(billyear) - 1;
					}else if ((Integer.parseInt(billmonth)) == 2) {
						month_set2 = 12;
						year_set2 = Integer.parseInt(billyear) - 1;
					} else {
						month_set2 = (Integer.parseInt(billmonth) - 2);
						year_set2 = Integer.parseInt(billyear);
					}
						String ben_type_val=obj.setValue("bentype", request); 
						String ben_combo=obj.setValue("ben_combo", request);
						
						
						demand_insert dins=new demand_insert();
						
						
						
						 if (Integer.parseInt(ben_combo)==0)
						 {
							 xml =dins.demand_ins(BILL_PERIOD_FROM,
										 BILL_PERIOD_TO,
										 billyear,
										 billmonth,date,ben_type_val,Office_id,userid);
						 }
						 else
						 {
							 xml =dins.demand_ins(BILL_PERIOD_FROM,
									 BILL_PERIOD_TO,
									 billyear,
									 billmonth,date,ben_type_val,Office_id,userid,ben_combo);
						 }
					}
					else if (command.equals("full_dep_demand_insert")) {// FULL DEPOSTIO
						// obj.testQry("TEST");
						System.out.println(" *** demand_insert Start Pls Wait ***");
						int total_demand = 0, already_demand = 0;
						String OB_INT_CUR_YR_MAINT = "0", OB_INT_AMT_WC = "";
						String ben_int = "0";
						String BILL_PERIOD_FROM = obj.setValue("datefrom", request);
						String BILL_PERIOD_TO = obj.setValue("dateto", request);
						String billyear = obj.setValue("billyear", request);
						String billmonth = obj.setValue("billmonth", request);
						String date = obj.setValue("date", request);
						int month_set = 0;
						int year_set = 0;
						if ((Integer.parseInt(billmonth)) == 1) {
							month_set = 12;
							year_set = Integer.parseInt(billyear) - 1;
						} else {
							month_set = (Integer.parseInt(billmonth) - 1);
							year_set = Integer.parseInt(billyear);
						}
						System.out.println(" *** demand_insert Start Pls Wait ***"+ billyear + "" + billmonth);
						String qry_ins = " SELECT "
								+ " ben.beneficiary_sno, "
								+ " monthly.CB_MAINT_CHARGES, "
								+ " monthly.CB_CUR_YR_WC, "
								+ "  monthly.CB_YESTER_YR_WC, "
								+ " monthly.CB_INT_CUR_YR_MAINT, "
								+ "  monthly.CB_INT_AMT_WC, "
								+ " monthly.CB_YESTER_YR_WC+monthly.CB_CUR_YR_WC , "					
								+ "  wc.wc_amt as WC_MTH_TOTAL, "
								+" nvl(wc.wc_amt,0)  as MONTH_BILL_AMT ,"
								+ " nvl(monthly.DMD_INT_FOR_MTH_WC,0)   as DMD_INT_FOR_MTH_WC "
								+
								" FROM( " + " ( " + " SELECT beneficiary_sno, "
								+ " BENEFICIARY_TYPE_ID "
								+ "  FROM pms_dcb_mst_beneficiary "
								+ " WHERE   "+new_cond+"  office_id = "
								+ Office_id
								+ ") ben   "
								+ "  left "
								+ "  join  "
								+ "   ( "
								+ "   select "
								+ "    sum(CB_MAINT_CHARGES) as CB_MAINT_CHARGES, "
								+ "    sum(CB_CUR_YR_WC) as CB_CUR_YR_WC, "
								+ "    sum(CB_YESTER_YR_WC) as CB_YESTER_YR_WC, "
								+ "    sum(CB_INT_AMT_WC) as CB_INT_AMT_WC, "
								+ "    sum(CB_INT_CUR_YR_MAINT) as CB_INT_CUR_YR_MAINT, "
								+ "     BENEFICIARY_SNO   ,"
								+ "    sum(DMD_INT_FOR_MTH_WC) as DMD_INT_FOR_MTH_WC, "
								+ "    sum(DMD_INT_UPTO_MTH_WC) as DMD_INT_UPTO_MTH_WC "
								+ "   from   "
								+ "   PMS_DCB_TRN_CB_MONTHLY "
								+ "   where MONTH =  "
								+ month_set
								+ "   AND FIN_YEAR = "
								+ year_set
								+ "  group by "
								+ "      BENEFICIARY_SNO "
								+ "  )monthly "
								+ "   on monthly.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "
								+ "  left join "
								+ "   (  SELECT sum(  b.AMOUNT ) AS wc_amt,b.sub_ledger_code " 
								+ " FROM FAS_JOURNAL_MASTER a, "
								+ "   FAS_JOURNAL_TRANSACTION b "
								+ " WHERE a.accounting_for_office_id=b.accounting_for_office_id "
								+ " AND a.JOURNAL_TYPE_CODE         =73 "
								+ " AND a.accounting_unit_id        = b.accounting_unit_id "
								+ " AND a.accounting_for_office_id  = "+Office_id+""
								+ " AND a.cashbook_month            ="+billmonth+""
								+ " AND a.cashbook_year             ="+billyear+""
								+ " AND b.cashbook_month            =a.cashbook_month "
								+ " AND b.cashbook_year             =a.cashbook_year "
								+ " AND b.accounting_for_office_id  ="+Office_id+""
								+ " AND a.voucher_no                = b.voucher_no "
								+ " AND b.cr_dr_indicator           ='DR' "
								+ " AND b.account_head_code        IN (459031,459037) "
								+ " group by b.sub_ledger_code " +
										")wc  "
								+ "  on wc.sub_ledger_code=ben.BENEFICIARY_SNO ) ";
						double net_COLN_CUR_YR_WC = 0, net_COLN_MAINT = 0, net_COLN_YESTER_YR_WC = 0;
						double net_CB_CUR_YR_WC = 0, net_CB_MAINT = 0, net_CB_YESTER_YR_WC = 0, DMD_INT_FOR_MTH_WC = 0.0;
						rs = stmt.executeQuery(qry_ins);
						String ben = "";
						int ben_dmd_count = 0;
						while (rs.next())
						// Phase 1 Start
						{
							ben_dmd_count = 0;
							ben = rs.getString("beneficiary_sno");
							maxsno = obj.getMax("PMS_DCB_TRN_BILL_DMD", "BILL_SNO","");
							int DIV_BILL_NO = obj.getMax("PMS_DCB_TRN_BILL_DMD","DIV_BILL_NO", "where OFFICE_ID=" + Office_id+ " and BILL_MONTH=" + billmonth+ " and BILL_YEAR=" + billyear + "");
						//	ben_int = rs.getString("INT_PERCENT");
							WC_MTH_TOTAL =obj.isNull(rs.getString("WC_MTH_TOTAL"),1);
							OB_INT_AMT_WC = obj.isNull(rs.getString("CB_INT_AMT_WC"), 1);
							DMD_INT_FOR_MTH_WC = Double.parseDouble(rs.getString("DMD_INT_FOR_MTH_WC"));
							ben_dmd_count = obj.getCount("PMS_DCB_TRN_BILL_DMD","where BENEFICIARY_SNO="+ben+ " and BILL_YEAR=" + billyear+ " and   BILL_MONTH=" + billmonth);
							String ben_type = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_TYPE_ID", "where  "+new_cond+"  BENEFICIARY_SNO="+ ben), 1);
							String OTHERS_PRIVATE_SNO = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY","OTHERS_PRIVATE_SNO", "where  "+new_cond+" BENEFICIARY_SNO="+ ben), 1);
							String short_name = "";
							if (Integer.parseInt(ben_type) <= 6)
								short_name = obj.isNull(obj.getValue("PMS_DCB_BEN_TYPE", "ADDRESS_TO","where BEN_TYPE_ID=" + ben_type), 3);
							else
								short_name = obj.isNull(obj.getValue("COM_MST_PRIVATE", "ADDRESS_TO","where PRIVATE_SNO=" + OTHERS_PRIVATE_SNO),	3);
							/* Date : 21/02/2011 : Other Charges Proces */
							String Other_charge_cond1 = " where  CASHBOOK_MONTH="+ billmonth+ " and CASHBOOK_YEAR="+ billyear+ " and BENEFICIARY_SNO ="
									+ ben+ " and OFFICE_ID ="+ Office_id+ " and CR_DR_INDICATOR='DR' group by BENEFICIARY_SNO";
							String Other_charge_cond2 = " where CASHBOOK_MONTH="+ billmonth+" and CASHBOOK_YEAR="+billyear
									+ " and BENEFICIARY_SNO ="+ben+" and OFFICE_ID ="+Office_id
									+ " and CR_DR_INDICATOR='CR' group by BENEFICIARY_SNO";
							String Other_charge_cond1_up=" where CASHBOOK_MONTH="+billmonth+" and CASHBOOK_YEAR="+billyear+" and BENEFICIARY_SNO ="
									+ben+" and OFFICE_ID ="+Office_id+" and CR_DR_INDICATOR='DR' ";
							String Other_charge_cond2_up = " where CASHBOOK_MONTH="+billmonth+" and CASHBOOK_YEAR=" + billyear
									+ " and BENEFICIARY_SNO ="+ben+" and OFFICE_ID ="+Office_id+" and CR_DR_INDICATOR='CR' ";
							
							String ADD_CHARGES_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",Other_charge_cond1);
							String MINUS_CHARGES_WC = obj.getValue("PMS_DCB_OTHER_CHARGES", "sum(AMOUNT)",Other_charge_cond2);
							int prv_month = 0;
							int prv_year = 0;
							int ben_sno = 0;
							int sch_sno = 0;
							int next_month = 0, nextyear = 0;
							/* Date : 21/02/2011 : Other Charges End */
							String ins_qry = "insert into PMS_DCB_TRN_BILL_DMD ("
									+ "BILL_SNO,"
									+ "OFFICE_ID,"
									+ " BENEFICIARY_SNO,"
									+ "BILL_PERIOD_FROM,"
									+ "BILL_PERIOD_TO,"
									+ "BILL_MONTH,"
									+ "BILL_YEAR,"
									+ "DIV_BILL_NO,"
								//	+ "NET_CONSUMPTION,"
									+ " OB_MAINT_CHARGES,"
									+ "OB_CUR_YR_WC,"
									+ "OB_YESTER_YR_WC,"
									+ "WC_MTH_TOTAL,"
							//		+ "INT_PERCENT,"
							//		+ "INT_CALC_WC,"
									+ "MONTH_BILL_AMT,"
									+ "BILL_RAISED_BY,"
									+ "BILLING_DT,"
									+ "UPDATED_BY_USER_ID,"
									+ " UPDATED_DATE,DMD_INT_FOR_MTH_WC  "
									+ ",OB_INT_AMT_WC,ADDRESS_TO,ADD_CHARGES_WC,MINUS_CHARGES_WC,DMD_FOR_MTH_MC) "
									+ " values " + " ("
									+ maxsno
									+ ""
									+ ","
									+ Office_id
									+ ""
									+ ","
									+ ben
									+ ",to_date('"
									+ BILL_PERIOD_FROM
									+ "','DD/MM/YYYY'),"
									+ "to_date('"
									+ BILL_PERIOD_TO
									+ "','DD/MM/YYYY'),"
									+ ""
									+ billmonth
									+ ","
									+ billyear
									+ ","
									+ DIV_BILL_NO
									+ ","
								//	+ rs.getString("net_consumed")	+ ","
									+ rs.getString("CB_MAINT_CHARGES")
									+ ","
									+ rs.getString("CB_CUR_YR_WC")
									+ ","
									+ rs.getString("CB_YESTER_YR_WC")
									+ ","
									+ WC_MTH_TOTAL
									+ ","
									//+ ben_int
								//	+ ","
								//	+ Math.round(Double.parseDouble(obj.isNull(rs.getString("INT_CALC_WC"), 1)))
								//	+ ","
									+ Math.round(Double.parseDouble(obj.isNull(rs.getString("MONTH_BILL_AMT"), 1)))
								 	+ ","
									+ Office_id
									+ ",to_date('"
									+ date
									+ "','DD/MM/YYYY')"
									+ ",'"
									+ userid
									+ "',"
									+ "clock_timestamp(),"
									+ DMD_INT_FOR_MTH_WC
									+ ", "
									+ OB_INT_AMT_WC
									+ ""
									+ " ,'"
									+ short_name
									+ "',"
									+ ADD_CHARGES_WC
									+ ","
									+ MINUS_CHARGES_WC + ","+WC_MTH_TOTAL+")";
						if (WC_MTH_TOTAL==null ) WC_MTH_TOTAL="0" ;
							// Demand Already Generate Test here
						 	if (Double.parseDouble(WC_MTH_TOTAL)!=0)
						 	{
									if (ben_dmd_count == 0) {
										total_demand++;
										row += obj.setUpd(ins_qry);
										 //System.out.println( ins_qry  );
										String bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO="
												+ maxsno + " " + Other_charge_cond1_up;
										//  obj.testQry(bill_sno_update);
										int b_up_row = obj.rowUpdate(bill_sno_update);
										bill_sno_update = "update PMS_DCB_OTHER_CHARGES set BILL_SNO="
												+ maxsno + " " + Other_charge_cond2_up;
										b_up_row = obj.rowUpdate(bill_sno_update);
									} else {
										already_demand++;
									}
						 	}				
							qry_ins = "  select " + "      "
									+ "     ob.CB_MAINT_CHARGES, "
									+ "     ob.CB_CUR_YR_WC, "
									+ "     ob.CB_YESTER_YR_WC, "
									+ "       wc.wcamt, " + "     ob.MONTH, "
									+ "     ob.FIN_YEAR,"
									+ "     ob.CB_INT_AMT_WC,"
									+ "     ob.CB_INT_CUR_YR_MAINT " + " from  "
									+ " ( " + "   (  SELECT SUM( b.AMOUNT ) AS wcamt , " 
									+" b.sub_ledger_code "
									+" FROM FAS_JOURNAL_MASTER a, "
									+" FAS_JOURNAL_TRANSACTION b "
									+" WHERE a.accounting_for_office_id=b.accounting_for_office_id "
									+" AND a.JOURNAL_TYPE_CODE         =73 "
									+" AND a.accounting_unit_id        = b.accounting_unit_id "
									+" AND a.accounting_for_office_id  = "+Office_id
									+" AND a.cashbook_month            = "+billmonth
									+" AND a.cashbook_year             = "+billyear
									+" AND b.cashbook_month            =a.cashbook_month "
									+" AND b.cashbook_year             =a.cashbook_year "
									+" AND b.accounting_for_office_id  = "+Office_id
									+" AND a.voucher_no                = b.voucher_no "
									+" AND b.cr_dr_indicator           ='DR' "
									+" AND b.account_head_code        IN (459031,459037) "
									+" AND b.sub_ledger_code           = "+ ben
									+" GROUP BY b.sub_ledger_code  )wc  "
									+ "     "
									+ "  left join  "
									+ " "
									+ "  ( "
									+ "  select  "
									+ "      CB_MAINT_CHARGES, "
									+ "    CB_CUR_YR_WC, "
									+ "    CB_YESTER_YR_WC, "
									+ "    SCH_SNO, "
									+ "    MONTH, "
									+ "     FIN_YEAR, "
									+ "    BENEFICIARY_SNO,"
									+ "    CB_INT_AMT_WC,"
									+ "    CB_INT_CUR_YR_MAINT "
									+ " from  "
									+ " "
									+ "PMS_DCB_TRN_CB_MONTHLY  where BENEFICIARY_SNO="
									+ ben
									+ "   and MONTH = "
									+ month_set
									+ "    AND FIN_YEAR = "
									+ year_set
									+ " )ob "
									+ "on ob.BENEFICIARY_SNO= wc.sub_ledger_code "
									+ "" + " ) ";
								String up_qry = "";
							String OB_MAINT_CHARGES = "", OB_CUR_YR_WC = "", OB_YESTER_YR_WC = "";
							String COLN_CUR_YR_WC = "0", COLN_YESTER_YR_WC = "0", COLN_MAINT = "", COLN_INT_FOR_MTH_WC = "";
							String CB_MAINT_CHARGES = "0", CB_CUR_YR_WC = "0", CB_YESTER_YR_WC = "0";
							String CB_INT_CUR_YR_MAINT = "0", CB_INT_AMT_WC = "";
							net_CB_CUR_YR_WC = 0;
							net_CB_MAINT = 0;
							net_CB_YESTER_YR_WC = 0;
							net_COLN_CUR_YR_WC = 0;
							net_COLN_MAINT = 0;
							net_COLN_YESTER_YR_WC = 0;
							rs_wc = stmt2.executeQuery(qry_ins);
							double sch_wise_int;
							while (rs_wc.next())
							// Phase 2 Start
							{
								up_qry = "";
								OB_MAINT_CHARGES = rs_wc
										.getString("CB_MAINT_CHARGES");
								OB_YESTER_YR_WC = rs_wc
										.getString("CB_YESTER_YR_WC");
								OB_CUR_YR_WC = rs_wc.getString("CB_CUR_YR_WC");
								OB_INT_CUR_YR_MAINT = obj.isNull(rs_wc
										.getString("CB_INT_CUR_YR_MAINT"), 1);
								ins_qry = " insert into PMS_DCB_TRN_BILL_DMD_SCH"
										+ "( BILL_SNO," + " OFFICE_ID,"
										+ " BENEFICIARY_SNO," + " SCH_NO,"
										+ " NET_CONSUMPTION,"
										+ " OB_MAINT_CHARGES," + " OB_CUR_YR_WC,"
										+ " OB_YESTER_YR_WC," + "WC_MTH_TOTAL ,"
										+ "COLN_CUR_YR_WC," + "COLN_MAINT ,"
										+ "COLN_YESTER_YR_WC,"
										+ "CB_MAINT_CHARGES,"
										+ "CB_CUR_YR_WC,CB_YESTER_YR_WC )";
								String sc =obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE","SCHEME_SNO", "where "+meter_status+" BENEFICIARY_SNO="+ ben+" and BENEFICIARY_TYPE_ID  = 18"), 1); 
								String sch_amt = WC_MTH_TOTAL;
								String stype = obj.isNull(obj.getValue("PMS_SCH_MASTER","SCH_TYPE_ID", "where SCH_SNO="+sc),2);
								ins_qry += " values (" +
								"" + maxsno + "," + Office_id + "," + ben + ","
										+ sc + ",0 ,"
										+ OB_MAINT_CHARGES + "," + OB_CUR_YR_WC
										+ "," + OB_YESTER_YR_WC + "," + sch_amt;
								String cond1 = " and collection_type=6 )"; // Yester
								// Year
								// Collection
								String cond2 = " and collection_type=10 )"; // Current
								// Year
								// Water
								// Charges
								String cond3 = " and collection_type=8 )"; // maintence
								String cond4 = " and collection_type=9 )";
								String main_cond = " AND cashbook_month =  "
										+ billmonth
										+ "	 AND cashbook_year =  "
										+ billyear
										+ "    AND accounting_unit_id =  "
										+ ACCOUNTING_UNIT_ID
										+ " 	 AND accounting_for_office_id = "
										+ Office_id
										+ "    and sub_ledger_type_code = 10"
										+ " 	 and SUB_LEDGER_CODE in (select PROJECT_ID from PMS_MST_PROJECTS_VIEW where STATUS='L' and SCH_SNO= "
										+ sc
										+ ")"
										+ "	 AND receipt_no IN "
										+ "   (SELECT receipt_no"
										+ "   FROM fas_receipt_master "
										+ "   WHERE RECEIPT_STATUS='L' and sub_ledger_type_code = 14"
										+ "    AND cashbook_month = " + billmonth
										+ "    AND cashbook_year = " + billyear
										+ "    AND accounting_unit_id = "
										+ ACCOUNTING_UNIT_ID
										+ "   AND accounting_for_office_id = "
										+ Office_id + "    AND sub_ledger_code ="
										+ ben + "  )  and ACCOUNT_HEAD_CODE "
										+ "  in " + "  ( select ACCOUNT_HEAD_CODE "
										+ "    from PMS_DCB_RECEIPT_ACCOUNT_MAP "
										+ "   where sch_type_id =" + stype
										+ " and ACTIVE_STATUS='L' ";
								COLN_MAINT = "";
								COLN_YESTER_YR_WC = "0";
								try {
									stmt4 = con.createStatement();
                                    rs_coll = stmt4.executeQuery("select sum(amount)from fas_receipt_transaction where   sub_ledger_type_code = 10 "+
                                    		" " + main_cond + "" + cond1 );
									if (rs_coll.next()) 
									{
										COLN_YESTER_YR_WC = obj2.isNull(rs_coll.getString(1), 1);
									}
									rs_coll.close();
									stmt4.close();
								} catch (Exception e) {
									System.out.println(e);
									COLN_YESTER_YR_WC = "0";
								}
								COLN_CUR_YR_WC = "0";
								try {
									stmt4 = con.createStatement();
									rs_coll = stmt4
											.executeQuery("select sum(amount) from fas_receipt_transaction where   sub_ledger_type_code = 10 "
													+ " " + main_cond + "" + cond2);
										//obj.testQry("select sum(amount) from fas_receipt_transaction where sub_ledger_type_code = 10"+" "+main_cond +""+cond2);
									if (rs_coll.next()) 
									{
										COLN_CUR_YR_WC = obj2.isNull(rs_coll.getString(1), 1);
									}
									rs_coll.close();
									stmt4.close();
								} catch (Exception e) {
									System.out.println(e);
									COLN_CUR_YR_WC = "0";
								}
								COLN_MAINT="0";
								try {
									stmt4 = con.createStatement();
									rs_coll = stmt4
											.executeQuery("select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
													+ " " + main_cond + "" + cond3);
									if (rs_coll.next()) 
									{
										COLN_MAINT = obj2.isNull(rs_coll.getString(1), 1);
									}
									rs_coll.close();
									stmt4.close();
								} catch (Exception e) {
									System.out.println(e);
									COLN_MAINT = "0";
								}
								COLN_INT_FOR_MTH_WC="0";
								try {
									stmt5 = con.createStatement();
									rs_coll = stmt5.executeQuery("select sum(amount) from  fas_receipt_transaction where   sub_ledger_type_code = 10 "
													+ " " + main_cond + "" + cond4);
									if (rs_coll.next()) 
									{
										COLN_INT_FOR_MTH_WC = obj2.isNull(rs_coll.getString(1), 1);
									} else 
									{
										COLN_INT_FOR_MTH_WC = "0";
									}
									rs_coll.close();
									stmt5.close();
								} catch (Exception e) {
									System.out.println(e);
									COLN_INT_FOR_MTH_WC = "0";
								}
								// --------------- (OB - Col )
								OB_YESTER_YR_WC = obj.isNull(OB_YESTER_YR_WC, 1);
								OB_CUR_YR_WC = obj.isNull(OB_CUR_YR_WC, 1);
								OB_MAINT_CHARGES = obj.isNull(OB_MAINT_CHARGES, 1);
								CB_YESTER_YR_WC = Double.toString(Double
										.parseDouble(OB_YESTER_YR_WC)
										- Double.parseDouble(COLN_YESTER_YR_WC));
								// CB_WC=(OB_WC + Demand)-collection
								
								CB_CUR_YR_WC = Double.toString((Double
										.parseDouble(OB_CUR_YR_WC) + Double
										.parseDouble(WC_MTH_TOTAL))
										- Double.parseDouble(COLN_CUR_YR_WC));
								
								CB_MAINT_CHARGES = Double.toString(Double
										.parseDouble(OB_MAINT_CHARGES)
										- Double.parseDouble(COLN_MAINT));
								net_CB_CUR_YR_WC += Double
										.parseDouble(CB_CUR_YR_WC);
								net_CB_MAINT += Double
										.parseDouble(CB_MAINT_CHARGES);
								net_CB_YESTER_YR_WC += Double
										.parseDouble(CB_YESTER_YR_WC);
								net_COLN_CUR_YR_WC += Double
										.parseDouble(COLN_CUR_YR_WC);
								net_COLN_MAINT += Double.parseDouble(COLN_MAINT);
								net_COLN_YESTER_YR_WC += Double
										.parseDouble(COLN_YESTER_YR_WC);
								// --------------- (OB - Col )
								CB_INT_CUR_YR_MAINT = Double.toString(Double
										.parseDouble(OB_INT_CUR_YR_MAINT)
										- Double.parseDouble("0"));
								CB_INT_AMT_WC = Double
										.toString(DMD_INT_FOR_MTH_WC
												+ (Double
														.parseDouble(OB_INT_AMT_WC) - Double
														.parseDouble(COLN_INT_FOR_MTH_WC)));
								ins_qry += "," + COLN_CUR_YR_WC + "," + COLN_MAINT
										+ "," + COLN_YESTER_YR_WC + ","
										+ CB_MAINT_CHARGES + "," + ""
										+ CB_CUR_YR_WC + "," + CB_YESTER_YR_WC
										+ ")";
			sch_wise_int = ((Double.parseDouble(OB_YESTER_YR_WC) + Double.parseDouble(OB_CUR_YR_WC))* Double.parseDouble(ben_int) / 100);
			//DMD_INT_FOR_MTH_WC = (Double.parseDouble(OB_CUR_YR_WC) - Double.parseDouble(COLN_CUR_YR_WC));
			// 
			int sch_count = obj.getCount("PMS_DCB_TRN_BILL_DMD_SCH","where BENEFICIARY_SNO="
					+ ben+ " and   BILL_SNO in (select Bill_sno from PMS_DCB_TRN_BILL_DMD where  BENEFICIARY_SNO="
					+ ben + " and BILL_MONTH="+ billmonth+ " and  BILL_YEAR="
					+ billyear+ ") and SCH_NO=" + sc);
								//System.out.println(ins_qry);
								if (sch_count == 0)
									row += obj.setUpd(ins_qry);
								/*
								 * Data Store in PMS_DCB_TRN_CB_MONTHLY table for
								 * month by ben schecme wise
								 */
								int c = obj.getCount("PMS_DCB_TRN_CB_MONTHLY",
										"where BENEFICIARY_SNO=" + ben
												+ " and FIN_YEAR=" + billyear
												+ " and   MONTH=" + billmonth
												+ " and SCH_SNO=" + sc);
								if (c > 0) {
									String del_qry = "delete from PMS_DCB_TRN_CB_MONTHLY  where BENEFICIARY_SNO="
											+ ben
											+ " and FIN_YEAR="
											+ billyear
											+ " and   MONTH="
											+ billmonth
											+ " and SCH_SNO=" + sc;
									ps = con.prepareStatement(del_qry);
									ps.executeUpdate();
								}
								c = obj.getCount("PMS_DCB_TRN_CB_MONTHLY",
										"where BENEFICIARY_SNO=" + ben
												+ " and FIN_YEAR=" + billyear
												+ " and   MONTH=" + billmonth
												+ " and SCH_SNO=" + sc);
								if (c == 0) {
									/* Date : 11/01/2011 */
									if (Integer.parseInt(billmonth) == 1) {
										prv_month = 12;
										prv_year = Integer.parseInt(billyear) - 1;
										ben_sno = Integer.parseInt(ben);
										sch_sno = Integer.parseInt(sc);
									} else
									{
										prv_month = Integer.parseInt(billmonth) - 1;
										prv_year = Integer.parseInt(billyear);
										ben_sno = Integer.parseInt(ben);
										sch_sno = Integer.parseInt(sc);
									}
									String cond_str = "where BENEFICIARY_SNO="+ ben_sno + " and FIN_YEAR=" + prv_year+ " and   MONTH=" + prv_month+ " and SCH_SNO=" + sch_sno;
									String cond_str_apr = "where BENEFICIARY_SNO="+ ben_sno + " and FIN_YEAR=" + prv_year+ " and   MONTH=4" + " and SCH_SNO="+ sch_sno;
									String cond_str_cur = "where BENEFICIARY_SNO="+ ben_sno + " and FIN_YEAR=" + billyear+ " and   MONTH=" + billmonth+ " and SCH_SNO=" + sch_sno;
									String DMD_UPTO_MTH_WC = "0", COLN_UPTO_MTH_WC = "", COLN_UPTO_MTH_MAINT = "", COLN_UPTO_MTH_YESTER_YR = "", COLN_INT_UPTO_MTH_MAINT = "", COLN_INT_UPTO_MTH_WC = "", DMD_INT_UPTO_MTH_WC = "";
									DMD_UPTO_MTH_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_UPTO_MTH_WC", cond_str);
									COLN_UPTO_MTH_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_WC", cond_str);
									COLN_UPTO_MTH_MAINT = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_MAINT", cond_str);
									COLN_UPTO_MTH_YESTER_YR = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_YESTER_YR", cond_str);
									COLN_INT_UPTO_MTH_MAINT = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_MAINT", cond_str);
									COLN_INT_UPTO_MTH_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_WC", cond_str);
									DMD_INT_UPTO_MTH_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_INT_UPTO_MTH_WC", cond_str);
									DMD_UPTO_MTH_WC = Double.toString(Double.parseDouble(DMD_UPTO_MTH_WC)+ Double.parseDouble(sch_amt));
									COLN_UPTO_MTH_WC = Double.toString(Double.parseDouble(COLN_UPTO_MTH_WC)+ Double.parseDouble(COLN_CUR_YR_WC));
									COLN_UPTO_MTH_MAINT = Double.toString(Double.parseDouble(COLN_UPTO_MTH_MAINT)+ Double.parseDouble(COLN_MAINT));
									COLN_UPTO_MTH_YESTER_YR = Double.toString(Double.parseDouble(COLN_UPTO_MTH_YESTER_YR)+ Double.parseDouble(COLN_YESTER_YR_WC));
									COLN_INT_UPTO_MTH_MAINT = Double.toString(Double.parseDouble(COLN_INT_UPTO_MTH_MAINT)+ Double.parseDouble("0"));
									COLN_INT_UPTO_MTH_WC = Double.toString(Double.parseDouble(COLN_INT_UPTO_MTH_WC)+ Double.parseDouble(COLN_INT_FOR_MTH_WC));
									DMD_INT_UPTO_MTH_WC = Double.toString(Double.parseDouble(DMD_INT_UPTO_MTH_WC)+ Double.parseDouble("0"));
									/* Date : 11/01/2011 */
									ins_qry2 = "insert into PMS_DCB_TRN_CB_MONTHLY (  "
											+ "BENEFICIARY_OB_SNO,"
											+ "BENEFICIARY_SNO,"
											+ "FIN_YEAR,"
											+ "MONTH,"
											+ "OFFICE_ID,"
											+ "SCH_SNO,"
											+ "CB_MAINT_CHARGES,"
											+ "CB_CUR_YR_WC,"
											+ "CB_YESTER_YR_WC,"
											+ "CB_INT_CUR_YR_MAINT,"
											+ "CB_INT_AMT_WC,"
											+ "DMD_FOR_MTH_WC,"
											+ "DMD_INT_FOR_MTH_WC,"
											+ "COLN_FOR_MTH_MAINT,"
											+ "COLN_FOR_MTH_YESTER_YR,"
											+ "COLN_FOR_MTH_WC,"
											+ "COLN_INT_FOR_MTH_MAINT,"
											+ "COLN_INT_FOR_MTH_WC,"
											+ "UPDATED_BY_USER_ID,"
											+ "UPDATED_DATE "
											+ ",DMD_UPTO_MTH_WC "
											+ ",COLN_UPTO_MTH_WC "
											+ ",COLN_UPTO_MTH_MAINT "
											+ ",COLN_UPTO_MTH_YESTER_YR "
											+ ",COLN_INT_UPTO_MTH_MAINT "
											+ ",COLN_INT_UPTO_MTH_WC "
											+ ",DMD_INT_UPTO_MTH_WC "
											+ " "
											+ ")"
											+ "  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0,"
											+ COLN_INT_FOR_MTH_WC
											+ ",'"
											+ userid
											+ "',clock_timestamp(),?,?,?,?,?,?,?) " + "";
									int BENEFICIARY_CB_SNO = obj.getMax(
											"PMS_DCB_TRN_CB_MONTHLY",
											"BENEFICIARY_OB_SNO", "");
								//	obj.testQry("ins_qry2"+ins_qry2 );
									ps = con.prepareStatement(ins_qry2);
									ps.setInt(1, BENEFICIARY_CB_SNO);
									ps.setInt(2, Integer.parseInt(ben));
									ps.setInt(3, Integer.parseInt(billyear));
									ps.setInt(4, Integer.parseInt(billmonth));
									ps.setInt(5, Integer.parseInt(Office_id));
									ps.setInt(6, Integer.parseInt(sc));
									ps.setDouble(7, Double.parseDouble(CB_MAINT_CHARGES));
									ps.setDouble(8, Double.parseDouble(CB_CUR_YR_WC));
									ps.setDouble(9, Double.parseDouble(CB_YESTER_YR_WC));
									ps.setDouble(10, Double.parseDouble(CB_INT_CUR_YR_MAINT));
									ps.setDouble(11, Double.parseDouble(CB_INT_AMT_WC));
									ps.setDouble(12, Double.parseDouble(sch_amt));
									ps.setDouble(13, DMD_INT_FOR_MTH_WC);
									ps.setDouble(14, Double.parseDouble(COLN_MAINT));
									ps.setDouble(15, Double.parseDouble(COLN_YESTER_YR_WC));
									ps.setDouble(16, Double.parseDouble(COLN_CUR_YR_WC));
									ps.setDouble(17, Double.parseDouble(DMD_UPTO_MTH_WC));
									ps.setDouble(18, Double.parseDouble(COLN_UPTO_MTH_WC));
									ps.setDouble(19, Double.parseDouble(COLN_UPTO_MTH_MAINT));
									ps.setDouble(20, Double.parseDouble(COLN_UPTO_MTH_YESTER_YR));
									ps.setDouble(21, Double.parseDouble(COLN_INT_UPTO_MTH_MAINT));
									ps.setDouble(22, Double.parseDouble(COLN_INT_UPTO_MTH_WC));
									ps.setDouble(23, Double.parseDouble(DMD_INT_UPTO_MTH_WC));
									ps.executeUpdate();
									/* 12/01/2010 */
									String apr_OB_MAINT_CHARGES = obj.getValue("PMS_DCB_OB_YEARLY","OB_MAINT_CHARGES", cond_str_apr);
									String apr_OB_CUR_YR_WC = obj.getValue("PMS_DCB_OB_YEARLY", "OB_CUR_YR_WC",cond_str_apr);
									String apr_OB_YESTER_YR_WC = obj.getValue("PMS_DCB_OB_YEARLY", "OB_YESTER_YR_WC",cond_str_apr);
									String apr_OB_INT_PRV_YR_MAINT = obj.getValue("PMS_DCB_OB_YEARLY","OB_INT_PRV_YR_MAINT", cond_str_apr);
									String apr_OB_INT_CUR_YR_MAINT = obj.getValue("PMS_DCB_OB_YEARLY","OB_INT_CUR_YR_MAINT", cond_str_apr);
									String apr_OB_INT_AMT_WC = obj.getValue("PMS_DCB_OB_YEARLY", "OB_INT_AMT_WC",cond_str_apr);
									String yr_DMD_UPTO_PRV_MTH_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_UPTO_MTH_WC", cond_str_cur);
									String yr_DMD_INT_UPTO_PRV_MTH_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","DMD_INT_UPTO_MTH_WC",cond_str_cur);
									String yr_OB_FOR_MTH_YESTER_YR_WC = "0", yr_OB_FOR_MTH_INT_CY_MAINT = "0", yr_OB_FOR_MTH_INT_AMT_WC = "0", yr_OB_FOR_MTH_MAINT_CHARGES = "0", yr_OB_FOR_MTH_CUR_YR_WC = "0", yr_DMD_INT_FOR_MTH_WC = "0";
									String yr_COLN_UPTO_PRV_MTH_MAINT = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_MAINT",cond_str_cur);
									String yr_COLN_UPTO_PRV_MTH_YESTER_YR = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_YESTER_YR",cond_str_cur);
									String yr_COLN_UPTO_PRV_MTH_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_UPTO_MTH_WC", cond_str_cur);
									String yr_COLN_INT_UPTO_PRV_MTH_MAINT = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_MAINT",cond_str_cur);
									String yr_COLN_INT_UPTO_PRV_MTH_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","COLN_INT_UPTO_MTH_WC",cond_str_cur);
									int Yearlysno = obj.getMax("PMS_DCB_OB_YEARLY","BENEFICIARY_OB_SNO", "");
									next_month = 0;
									nextyear = 0;
									if (Integer.parseInt(billmonth) == 12) {
										next_month = 1;
										nextyear = Integer.parseInt(billyear) + 1;
									} else
									{
										next_month = Integer.parseInt(billmonth) + 1;
										nextyear = Integer.parseInt(billyear);
									}
									String cond_str_next = "where BENEFICIARY_SNO="+ ben_sno + " and FIN_YEAR=" + nextyear+ " and   MONTH=" + next_month+ " and SCH_SNO=" + sch_sno;
									String yearly_insert = "", prv_ = "";
									prv_ = "delete from PMS_DCB_OB_YEARLY "+ cond_str_next;
									ps = null;
									ps = con.prepareStatement(prv_);
									ps.execute();
									yearly_insert = "insert into PMS_DCB_OB_YEARLY ( "
											+ "BENEFICIARY_OB_SNO,"
											+ "BENEFICIARY_SNO,"
											+ "FIN_YEAR,"
											+ "MONTH,"
											+ "OFFICE_ID,"
											+ "SCH_SNO,"
											+ "AS_ON_DATE,"
											+ "OB_MAINT_CHARGES,"
											+ "OB_CUR_YR_WC,"
											+ "OB_YESTER_YR_WC,"
											+ "OB_INT_PRV_YR_MAINT,"
											+ "OB_INT_CUR_YR_MAINT,"
											+ "OB_INT_AMT_WC,"
											+ "DMD_UPTO_PRV_MTH_WC,"
											+ "DMD_INT_UPTO_PRV_MTH_WC,"
											+ "COLN_UPTO_PRV_MTH_MAINT,"
											+ "COLN_UPTO_PRV_MTH_YESTER_YR,"
											+ "COLN_UPTO_PRV_MTH_WC,"
											+ "COLN_INT_UPTO_PRV_MTH_MAINT,"
											+ "COLN_INT_UPTO_PRV_MTH_WC,"
											+ "UPDATED_BY_USER_ID,"
											+ "UPDATED_DATE,"
											+ "OB_FOR_MTH_YESTER_YR_WC,"
											+ "OB_FOR_MTH_INT_CY_MAINT,"
											+ "OB_FOR_MTH_INT_AMT_WC,"
											+ "OB_FOR_MTH_MAINT_CHARGES,"
											+ "OB_FOR_MTH_CUR_YR_WC,"
											+ "DMD_INT_FOR_MTH_WC ) ";
									yearly_insert += " values (?,?,?,?,?,?,clock_timestamp(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,clock_timestamp() ,?,?,?,?,?,?)";
									ps = null;
									ps = con.prepareStatement(yearly_insert);
									ps.setInt(1, Yearlysno);
									ps.setString(2, ben);
									ps.setInt(3, nextyear);
									ps.setInt(4, next_month);
									ps.setString(5, Office_id);
									ps.setInt(6, sch_sno);
									ps.setString(7, apr_OB_MAINT_CHARGES);
									ps.setString(8, apr_OB_CUR_YR_WC);
									ps.setString(9, apr_OB_YESTER_YR_WC);
									ps.setString(10, apr_OB_INT_PRV_YR_MAINT);
									ps.setString(11, apr_OB_INT_CUR_YR_MAINT);
									ps.setString(12, apr_OB_INT_AMT_WC);
									ps.setString(13, yr_DMD_UPTO_PRV_MTH_WC);
									ps.setString(14, yr_DMD_INT_UPTO_PRV_MTH_WC);
									ps.setString(15, yr_COLN_UPTO_PRV_MTH_MAINT);
									ps
											.setString(16,
													yr_COLN_UPTO_PRV_MTH_YESTER_YR);
									ps.setString(17, yr_COLN_UPTO_PRV_MTH_WC);
									ps
											.setString(18,
													yr_COLN_INT_UPTO_PRV_MTH_MAINT);
									ps.setString(19, yr_COLN_INT_UPTO_PRV_MTH_WC);
									ps.setString(20, userid);
									ps.setString(21, yr_OB_FOR_MTH_YESTER_YR_WC);
									ps.setString(22, yr_OB_FOR_MTH_INT_CY_MAINT);
									ps.setString(23, yr_OB_FOR_MTH_INT_AMT_WC);
									ps.setString(24, yr_OB_FOR_MTH_MAINT_CHARGES);
									ps.setString(25, yr_OB_FOR_MTH_CUR_YR_WC);
									ps.setString(26, yr_DMD_INT_FOR_MTH_WC);
									ps.executeUpdate();
									ps = null;
									String prvOB_FOR_MTH_YESTER_YR_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_YESTER_YR_WC", cond_str_cur);
									String prvOB_FOR_MTH_INT_CY_MAINT = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_CUR_YR_MAINT",cond_str_cur);
									String prvOB_FOR_MTH_INT_AMT_WC = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_INT_AMT_WC", cond_str_cur);
									String prvOB_FOR_MTH_MAINT_CHARGES = obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_MAINT_CHARGES",
													cond_str_cur);
									String prvOB_FOR_MTH_CUR_YR_WC = obj.getValue(
											"PMS_DCB_TRN_CB_MONTHLY",
											"CB_CUR_YR_WC", cond_str_cur);
									String prvDMD_INT_FOR_MTH_WC = obj.getValue(
											"PMS_DCB_TRN_CB_MONTHLY",
											"DMD_INT_FOR_MTH_WC", cond_str_cur);
									String yearly_ob_update = " update PMS_DCB_OB_YEARLY set "
											+ " OB_FOR_MTH_YESTER_YR_WC="
											+ prvOB_FOR_MTH_YESTER_YR_WC
											+ ""
											+ ",OB_FOR_MTH_INT_CY_MAINT="
											+ prvOB_FOR_MTH_INT_CY_MAINT
											+ ",OB_FOR_MTH_INT_AMT_WC="
											+ prvOB_FOR_MTH_INT_AMT_WC
											+ ",OB_FOR_MTH_MAINT_CHARGES="
											+ prvOB_FOR_MTH_MAINT_CHARGES
											+ ",OB_FOR_MTH_CUR_YR_WC="
											+ prvOB_FOR_MTH_CUR_YR_WC
											+ ",DMD_INT_FOR_MTH_WC="
											+ prvDMD_INT_FOR_MTH_WC
											+ ""
											+ "   "
											+ cond_str_next;
									ps = con.prepareStatement(yearly_ob_update);
									ps.executeUpdate();
									// System.out.println("TEST4");
									/* 12/01/2010 END */
								}
								/*
								 * Data Store in PMS_DCB_TRN_CB_MONTHLY table for
								 * month by ben schecme wise
								 */
							}// Phase 2 END
							up_qry = "update PMS_DCB_TRN_BILL_DMD " + "set"
									+ " COLN_CUR_YR_WC =" + net_COLN_CUR_YR_WC
									+ ",COLN_MAINT=" + net_COLN_MAINT
									+ ",COLN_YESTER_YR_WC= "
									+ net_COLN_YESTER_YR_WC + ",CB_MAINT_CHARGES="
									+ net_CB_MAINT + ",CB_CUR_YR_WC="
									+ net_CB_CUR_YR_WC + "," + " CB_YESTER_YR_WC="
									+ net_CB_YESTER_YR_WC + " where BILL_SNO="
									+ maxsno;
							 // obj.testQry(up_qry);
							stmt3.executeUpdate(up_qry);
							String total_ = " INSERT "
									+ " INTO PMS_DCB_OB_YEARLY_TOTAL " + " ( "
									+ " BENEFICIARY_SNO, " + " FIN_YEAR, "
									+ " MONTH, " + " OFFICE_ID, "
									+ " OB_MAINT_CHARGES, " + " OB_CUR_YR_WC, "
									+ " OB_YESTER_YR_WC, "
									+ " OB_INT_PRV_YR_MAINT, "
									+ " OB_INT_CUR_YR_MAINT, " + " OB_INT_AMT_WC, "
									+ " DMD_UPTO_PRV_MTH_WC, "
									+ " DMD_INT_UPTO_PRV_MTH_WC, "
									+ " COLN_UPTO_PRV_MTH_MAINT, "
									+ " COLN_UPTO_PRV_MTH_YESTER_YR, "
									+ " COLN_UPTO_PRV_MTH_WC, "
									+ " COLN_INT_UPTO_PRV_MTH_MAINT, "
									+ " COLN_INT_UPTO_PRV_MTH_WC, "
									+ " OB_FOR_MTH_YESTER_YR_WC, "
									+ " OB_FOR_MTH_INT_CY_MAINT, "
									+ " OB_FOR_MTH_INT_AMT_WC, "
									+ " OB_FOR_MTH_MAINT_CHARGES, "
									+ " OB_FOR_MTH_CUR_YR_WC, "
									+ " DMD_INT_FOR_MTH_WC " + " 					  ) "
									+ " SELECT BENEFICIARY_SNO, " + " FIN_YEAR, "
									+ " MONTH, " + " OFFICE_ID, "
									+ " SUM(OB_MAINT_CHARGES), "
									+ " SUM(OB_CUR_YR_WC), "
									+ " SUM(OB_YESTER_YR_WC), "
									+ " SUM(OB_INT_PRV_YR_MAINT), "
									+ " SUM(OB_INT_CUR_YR_MAINT), "
									+ " SUM(OB_INT_AMT_WC), "
									+ " SUM(DMD_UPTO_PRV_MTH_WC), "
									+ " SUM(DMD_INT_UPTO_PRV_MTH_WC), "
									+ " SUM(COLN_UPTO_PRV_MTH_MAINT), "
									+ " SUM(COLN_UPTO_PRV_MTH_YESTER_YR), "
									+ " SUM(COLN_UPTO_PRV_MTH_WC), "
									+ " SUM(COLN_INT_UPTO_PRV_MTH_MAINT), "
									+ " SUM(COLN_INT_UPTO_PRV_MTH_WC), "
									+ " SUM(OB_FOR_MTH_YESTER_YR_WC), "
									+ " SUM(OB_FOR_MTH_INT_CY_MAINT), "
									+ " SUM(OB_FOR_MTH_INT_AMT_WC), "
									+ " SUM(OB_FOR_MTH_MAINT_CHARGES), "
									+ " SUM(OB_FOR_MTH_CUR_YR_WC), "
									+ " SUM(DMD_INT_FOR_MTH_WC) "
									+ " FROM PMS_DCB_OB_YEARLY "
									+ " WHERE BENEFICIARY_SNO= " + ben
									+ " AND fin_year         = " + nextyear
									+ " AND MONTH            = " + next_month
									+ " and OFFICE_ID=" + Office_id
									+ "  GROUP BY BENEFICIARY_SNO, "
									+ " FIN_YEAR, " + " MONTH, " + " OFFICE_ID ";
							int check_count = obj.getCount(
									"PMS_DCB_OB_YEARLY_TOTAL",
									" where BENEFICIARY_SNO=" + ben
											+ " and fin_year=" + nextyear
											+ " and MONTH=" + next_month
											+ " and OFFICE_ID=" + Office_id);
							if (check_count > 0) {
								PreparedStatement del_ = null;
								try { del_ = con.prepareStatement("delete from PMS_DCB_OB_YEARLY_TOTAL where BENEFICIARY_SNO="
													+ ben
													+ " and fin_year="
													+ nextyear
													+ " and MONTH="
													+ next_month
													+ " and OFFICE_ID=" + Office_id);
									del_.executeUpdate();
									del_.close();
								} catch (SQLException e) {
								}
							}
							stmt3.executeUpdate(total_);
							String wc_DR_total_cond = " where BENEFICIARY_SNO ="
									+ ben
									+ " and CASHBOOK_MONTH="
									+ billmonth
									+ " and CASHBOOK_YEAR="
									+ billyear
									+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='DR'   ";
							String wc_CR_total_cond = " where BENEFICIARY_SNO ="
									+ ben
									+ " and CASHBOOK_MONTH="
									+ billmonth
									+ " and CASHBOOK_YEAR="
									+ billyear
									+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=7 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='CR'  ";
							String wc_DR_total_ = obj.getValue(
									"PMS_DCB_OTHER_CHARGES", "sum(amount)",
									wc_DR_total_cond + " group by BENEFICIARY_SNO");
							String wc_CR_total_ = obj.getValue(
									"PMS_DCB_OTHER_CHARGES", "sum(amount)",
									wc_CR_total_cond + " group by BENEFICIARY_SNO");
							String main_DR_total_cond = " where BENEFICIARY_SNO ="
									+ ben
									+ " and CASHBOOK_MONTH="
									+ billmonth
									+ " and CASHBOOK_YEAR="
									+ billyear
									+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='DR'   ";
							String main_CR_total_cond = " where BENEFICIARY_SNO ="
									+ ben
									+ " and CASHBOOK_MONTH="
									+ billmonth
									+ " and CASHBOOK_YEAR="
									+ billyear
									+ " and ACCOUNT_HEAD_CODE in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE=8 and ACTIVE_STATUS='L') and CR_DR_INDICATOR='CR'  ";
							String main_DR_total_ = obj.getValue(
									"PMS_DCB_OTHER_CHARGES", "sum(amount)",
									main_DR_total_cond
											+ " group by BENEFICIARY_SNO");
							String main_CR_total_ = obj.getValue(
									"PMS_DCB_OTHER_CHARGES", "sum(amount)",
									main_CR_total_cond
											+ " group by BENEFICIARY_SNO");
							String qry_up = "update PMS_DCB_OB_YEARLY_TOTAL set OB_FOR_MTH_CUR_YR_WC=(OB_FOR_MTH_CUR_YR_WC+"
									+ wc_DR_total_
									+ ")-("
									+ wc_CR_total_
									+ "),OB_FOR_MTH_MAINT_CHARGES=(OB_FOR_MTH_MAINT_CHARGES+"
									+ main_DR_total_
									+ ")-("
									+ main_CR_total_
									+ ")   WHERE BENEFICIARY_SNO= "
									+ ben
									+ " AND fin_year         = "
									+ nextyear
									+ " AND MONTH            = "
									+ next_month
									+ " and OFFICE_ID=" + Office_id;
							int up = obj.rowUpdate(qry_up);
							/*
												 * 
												 * 
												 * 
												 * 
												 * 
												 * 
												 * */
						} // Phase 1 End
						System.out.println(" *** demand_insert End ***");
						xml = "<result><row>" + row;
						xml += "</row><total_demand>" + total_demand
								+ "</total_demand><already_demand>"
								+ already_demand + "</already_demand></result>";
				}
				if (command.equals("schemewiserevoke"))
				{
					int row1=0;
					String sch_sno = obj.setValue("scheme_select",request);
							month  = obj.setValue("fmonth",request);
							year  = obj.setValue("fyear",request);
					String sub_div= obj.setValue("subdiv",request);
					int rc_val = obj.getCount("PMS_DCB_TRN_MONTHLY_PR","where  upper(PROCESS_FLAG)=upper('FR') and MONTH="+month+"  and  YEAR="+year+" and    ( SCH_SNO="+sch_sno+" or SUBDIV_OFFICE_ID="+sub_div+"  )  and OFFICE_ID="+Office_id);
					if (rc_val == 0) 
					{
						//String rc = obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)","where MONTH="+month+"  and  YEAR="+year+" and SCH_SNO="+sch_sno+" and OFFICE_ID="+Office_id+" and PROCESS_FLAG='CR' or PROCESS_FLAG='ED'");
						//if (Integer.parseInt(rc) != 0)
						//{
							String up = "update PMS_DCB_TRN_MONTHLY_PR set  PROCESS_FLAG='CR' where MONTH="+month+" and YEAR="+year+" and  ( SCH_SNO="+sch_sno+" or SUBDIV_OFFICE_ID="+sub_div+"  )  and OFFICE_ID="+Office_id+" and PROCESS_FLAG='V'";
							  row1 = obj.setUpd(up);
							up = "";
							up = "update PMS_DCB_MONTHLY_PR set  PROCESS_FLAG='CR' where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" OFFICE_ID="
									+ Office_id
									+ ")  and MONTH="
									+ month
									+ "  and YEAR="
									+ year
									+ " and  PROCESS_FLAG='V'";
							row = obj.setUpd(up);
							msg="Revoke Completed";
						}
					else
					{
						msg = "Pumping Return Already Freezed.! ";
						row1=0;
					}
						xml += "<result><msg>"+msg+"</msg> <sp_flag>1</sp_flag><row>" + row1 + "</row></result>";
					//}
				}
				if (command.equals("schemewisevalidate"))
				{
					int row1=0;
					String sch_sno = obj.setValue("scheme_select",request);
							month  = obj.setValue("fmonth",request);
							year  = obj.setValue("fyear",request);
					String sub_div= obj.setValue("subdiv",request);
					int rc_val = obj.getCount("PMS_DCB_TRN_MONTHLY_PR","where  upper(PROCESS_FLAG)=upper('FR') and MONTH="+month+"  and  YEAR="+year+" and   ( SCH_SNO="+sch_sno+" or SUBDIV_OFFICE_ID="+sub_div+"  ) and OFFICE_ID="+Office_id);
					if (rc_val == 0) 
					{
						//String rc = obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)","where MONTH="+month+"  and  YEAR="+year+" and SCH_SNO="+sch_sno+" and OFFICE_ID="+Office_id+" and PROCESS_FLAG='CR' or PROCESS_FLAG='ED'");
						//if (Integer.parseInt(rc) != 0)
						//{
							String up = "update PMS_DCB_TRN_MONTHLY_PR set  PROCESS_FLAG='V' where MONTH="+month+" and YEAR="+year+" and ( SCH_SNO="+sch_sno+" or SUBDIV_OFFICE_ID="+sub_div+"  ) and OFFICE_ID="+Office_id+" and ( PROCESS_FLAG='V' or  PROCESS_FLAG='CR' or PROCESS_FLAG='ED' )";
							  row = obj.setUpd(up);
							up = "";
							up = "update PMS_DCB_MONTHLY_PR set  PROCESS_FLAG='V' where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+"  OFFICE_ID="
									+ Office_id
									+ ")  and MONTH="
									+ month
									+ "  and YEAR="
									+ year
									+ " and   (  PROCESS_FLAG='V' or PROCESS_FLAG='CR' or PROCESS_FLAG='ED' )";
							row1 = obj.setUpd(up);
							msg="Validation Completed";
						}
					else
					{
						msg = "Pumping Return Already Freezed.! ";
						row1=0;
					}
						xml += "<result><msg>"+msg+"</msg> <sp_flag>1</sp_flag><row>" + row + "</row><row1>" + row1 + "</row1></result>";
					//}
				}
				if (command.equals("pdf")) {
					try {
						String bensno = "",imagespath="";  
						mvalue="";
						mvalue = obj.month_val(obj.setValue("month",request));
						int option = Integer.parseInt(obj.setValue("option",request));
						
						imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("imgpath", imagespath); 
						if (Integer.parseInt(process_code) == 1) 
						{
							parameters.put("year", Integer.parseInt(obj.setValue("year", request)));
							parameters.put("month", Integer.parseInt(obj.setValue("month", request)));
							parameters.put("mvalue", mvalue);
							parameters.put("bensno", Integer.parseInt(obj.setValue("bensno", request)));
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PumpingReturn.jasper");
						} else {
							parameters.put("year", obj.setValue("year", request));
							parameters.put("month", obj.setValue("month",request));
							parameters.put("office_id", Office_id);
							parameters.put("mvalue", mvalue);
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PumpingPR_All.jasper");
						}
						JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
						OutputStream outuputStream = response.getOutputStream();
						JRExporter exporter = null;
						if (option == 1) 
						{
							response.setContentType("application/pdf");
							response.setHeader("Content-Disposition","attachment; filename=\"PumpingReturn.pdf\"");
							exporter = new JRPdfExporter();
							exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
							exporter.setParameter( JRExporterParameter.OUTPUT_STREAM,outuputStream);
							exporter.exportReport();
							outuputStream.close();
						}
						else {
							response.setContentType("application/vnd.ms-excel");
							response.setHeader("Content-Disposition","attachment;filename=\"PumpingReturn.xls\"");
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
							ServletOutputStream ouputStream = response.getOutputStream();
							ouputStream.write(bytes, 0, bytes.length);
							ouputStream.flush();
							ouputStream.close();
						}
					} catch (JRException e) {
						throw new ServletException(e);
					}
				} else
				{
					PrintWriter pr = response.getWriter();
					int sp_flag = 0;
					if (command.equals("validate")) {
						sp_flag = 1;
						xml = "<result>";
						String ben_sel = request.getParameter("ben_sel");
						String fyear1 = request.getParameter("fyear");
						String fmonth1 = request.getParameter("fmonth");
						int rc1 = obj.getCount("PMS_DCB_TRN_MONTHLY_PR",
								"where  upper(PROCESS_FLAG)=upper('V') and  PR_SNO="
										+ input_value + "");
						if (rc1 == 0) 
						{
							String rc = obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)","where PR_SNO ="+input_value+" and PROCESS_FLAG='CR' or PROCESS_FLAG='ED'");
							if (Integer.parseInt(rc) != 0) {
								String up = "update PMS_DCB_TRN_MONTHLY_PR set  PROCESS_FLAG='V' where PR_SNO ="
										+ input_value
										+ " and  ( PROCESS_FLAG='CR' or PROCESS_FLAG='ED' )";
								row = obj.setUpd(up);
								up = "";
								up = "update PMS_DCB_MONTHLY_PR set  PROCESS_FLAG='V' where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" OFFICE_ID="
										+ Office_id
										+ ")  and MONTH="
										+ fmonth1
										+ "  and YEAR="
										+ fyear1
										+ " and   ( PROCESS_FLAG='CR' or PROCESS_FLAG='ED' )";
								msg = "Done!!! ";
								row = obj.setUpd(up);
								xml += "<PR_SNO>" + input_value
										+ "</PR_SNO><sp_flag>1</sp_flag><msg>"
										+ msg + "</msg><row>" + row + "</row>";
							} else {
								String rc_fr = obj.getValue(
										"PMS_DCB_TRN_MONTHLY_PR", "count(*)",
										"where PR_SNO =" + input_value
												+ " and PROCESS_FLAG='FR'");
								if (Integer.parseInt(rc_fr) != 0) {
									xml += "<PR_SNO>"
											+ input_value
											+ "</PR_SNO><sp_flag>0</sp_flag><msg>Already Freezed!!!</msg><row>0</row>";
								}
							}
						} else {
							msg = "Validation process already done ! ! !";
							xml += "<PR_SNO>" + input_value
									+ "</PR_SNO><sp_flag>0</sp_flag><row>"
									+ row + "<msg>" + msg + "</msg></row>";
						}
						xml += "</result>";
					} else if (command.equals("Delete")) {
						String fyear1_del = request.getParameter("fyear");
						String fmonth1_del = request.getParameter("fmonth");
						xml = "<result>";
						// check the record should be in CR status other wise
						// can
						// not delete
						int rc_del = obj.getCount("PMS_DCB_TRN_MONTHLY_PR",
								"where  upper(PROCESS_FLAG)=upper('CR') and  PR_SNO="
										+ input_value + "");
						String ben_sel_del = obj.getValue(
								"PMS_DCB_TRN_MONTHLY_PR", "BENEFICIARY_SNO",
								"where PR_SNO =" + input_value + " ");
						if (rc_del != 0) {
							String QTY_CONSUMED_CALC = obj.getValue(
									"PMS_DCB_TRN_MONTHLY_PR",
									"QTY_CONSUMED_CALC", "where PR_SNO ="
											+ input_value + " ");
							String del_qry = "delete from PMS_DCB_TRN_MONTHLY_PR where PR_SNO ="
									+ input_value;
							int del_row = obj.setUpd(del_qry);
							if (del_row > 0) {
								// This is processed check if the record is
								// available .
								int record_ben = obj.getCount(
										"PMS_DCB_TRN_MONTHLY_PR",
										"where  BENEFICIARY_SNO=" + ben_sel_del
												+ " and  MONTH=" + fmonth1_del
												+ " and YEAR=" + fyear1_del);
								String up_qry = "";
								if (record_ben > 0) {
									// This is processed at when the delete of
									// each
									// transaction record...
									up_qry = "update PMS_DCB_MONTHLY_PR set  NET_CONSUMED=(NET_CONSUMED-"
											+ QTY_CONSUMED_CALC
											+ ") where  BENEFICIARY_SNO="
											+ ben_sel_del
											+ " and  MONTH="
											+ fmonth1_del
											+ " and YEAR="
											+ fyear1_del;
									row = obj.setUpd(up_qry);
									xml += "<PR_SNO>"
											+ input_value
											+ "</PR_SNO><sp_flag>3</sp_flag><msg>Record Deleted!!!</msg><row>"
											+ del_row + "</row>";
								} else {
									// This is processed at when the delete of
									// last
									// transaction record...
									up_qry = "delete from PMS_DCB_MONTHLY_PR where  BENEFICIARY_SNO="
											+ ben_sel_del
											+ " and  MONTH="
											+ fmonth1_del
											+ " and YEAR="
											+ fyear1_del;
									row = obj.setUpd(up_qry);
									xml += "<PR_SNO>"
											+ input_value
											+ "</PR_SNO><sp_flag>3</sp_flag><msg>Entire pumping return data Deleted !!!</msg><row>"
											+ del_row + "</row>";
								}
							}
						} else {
							xml += "<PR_SNO>"
									+ input_value
									+ "</PR_SNO><sp_flag>0</sp_flag><msg>Can not the Record!!!</msg><row>0</row>";
						}
						xml += "</result>";
					}
					else if (command.equals("notvalidate")) {
						xml = "<result>";
						String ben_sel = request.getParameter("ben_sel");
						String fyear1 = request.getParameter("fyear");
						String fmonth1 = request.getParameter("fmonth");
						int rc1 = obj.getCount("PMS_DCB_TRN_MONTHLY_PR","where  upper(PROCESS_FLAG)=upper('V') and  PR_SNO="+ input_value + "");
						if (rc1 == 1) 
						{
							String rc = obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)","where PR_SNO ="+ input_value+ " and PROCESS_FLAG='V' or PROCESS_FLAG='ED'");
							if (Integer.parseInt(rc) != 0) 
							{
								String up = "update PMS_DCB_TRN_MONTHLY_PR set  PROCESS_FLAG='CR' where PR_SNO ="+ input_value+ " and  ( PROCESS_FLAG='V' or PROCESS_FLAG='ED' )";
								row = obj.setUpd(up);
								up = "";
								up = "update PMS_DCB_MONTHLY_PR set  PROCESS_FLAG='CR' where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" OFFICE_ID="
										+Office_id+ ")  and MONTH="+fmonth1+"  and YEAR="+fyear1
										+" and   ( PROCESS_FLAG='V' or PROCESS_FLAG='ED' )";
								msg = "Done!!! ";
								row = obj.setUpd(up);
								xml += "<PR_SNO>" + input_value+ "</PR_SNO><sp_flag>2</sp_flag><msg>"+msg+"</msg><row>"+row+"</row>";
							} else 
							{
								String rc_fr = obj.getValue("PMS_DCB_TRN_MONTHLY_PR", "count(*)","where PR_SNO =" + input_value+ " and PROCESS_FLAG='V'");
								if (Integer.parseInt(rc_fr) != 0) 
								{
									xml += "<PR_SNO>"+ input_value+ "</PR_SNO><sp_flag>0</sp_flag><msg>Already Validated !!!</msg><row>0</row>";
								}
							}
						} else {
							msg = "InValidation process already done ! ! !";
							xml += "<PR_SNO>" + input_value+ "</PR_SNO><sp_flag>0</sp_flag><row>"+ row + "<msg>" + msg + "</msg></row>";
						}
						xml += "</result>";
					}
					else if (command.equals("show")) {
						// Command -> show & process_code 0,1 Start
						if (process_code.equals("14")) {
							xml = obj.combo_lkup("BILL_SNO", "DIV_BILL_NO","PMS_DCB_TRN_BILL_DMD", " where OFFICE_ID="+ Office_id + " and BILL_MONTH="+ month + " and BILL_YEAR=" + year+ " order by DIV_BILL_NO", 2,"--Select--");
						}
						if (process_code.equals("6")) 
						{
							xml = obj.combo_lkup("TYPE_SNO", "TYPE_DESC","PMS_DCB_OTHERCHARGES", "", 2,"--Select--");
						} else if (process_code.equals("0")|| process_code.equals("1")) 
						{
							xml = "<result>";
							qry = "select BENEFICIARY_NAME,BENEFICIARY_SNO  from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" BENEFICIARY_SNO="+ input_value;
							rs = stmt.executeQuery(qry);
							if (rs.next()) {
								row++;
								xml += "<name>"+ rs.getString("BENEFICIARY_NAME")+ "</name>";
								xml += "<sno>" + rs.getInt("BENEFICIARY_SNO")+ "</sno>";
							}
							if (row >= 1)
								xml += "<resultstatus>success</resultstatus>";
							else
								xml += "<resultstatus>norecord</resultstatus>";
							xml += "</result>";
						} else if (process_code.equals("11")) {
							xml = "<result>";
							String select_query = " select "
									+ "BEN.BENEFICIARY_NAME ,"
									+ "BEN_TYPE.BEN_TYPE_DESC,"
									+ " SCH.SCH_NAME,"
									+ " MET.METRE_SNO,"
									+ " MET.SCHEME_SNO,"
									+ " MET.METRE_LOCATION, "
									+ "MET.MULTIPLY_FACTOR,"
									+ "MET.METRE_TYPE,"
									+ "MET.PARENT_METRE,\n"
									+ "PR.PR_SNO, \n"
									+ "PR.BENEFICIARY_SNO, \n"
									+ "PR.OFFICE_ID, \n"
									+ "PR.SUBDIV_OFFICE_ID, \n"
									+ "PR.MONTH, \n"
									+ "PR.YEAR, \n"
									+ "PR.METRE_SNO, \n"
									+ "PR.METRE_INITIAL_READING, \n"
									+ "PR.METRE_CLOSING_READING, \n"
									+ "PR.QTY_CONSUMED,"
									+ "PR.CHILD_METER_QTY,PR.QTY_CONSUMED_NET,PR.PROCESS_FLAG, \n"
									+
									// "PR.ALLOTED_QTY, \n"+
									// "PR.EXCESS_QTY, \n"+
									"  case PR.METRE_FIXED  when 'y' Then 'Yes'when 'Y' Then 'Yes'when 'n' Then 'No'when 'N' Then 'No'When null Then 'NR'END as METRE_FIXED,"
									+ "case PR.METRE_WORKING  when 'y' Then 'Yes'when 'Y' Then 'Yes'when 'n' Then 'No'when 'N' Then 'No'When null Then 'NR'END as METRE_WORKING ,"
									+ " MR.NET_CONSUMED," + " MR.MONPR_SNO" +
									" " + " from \n" + "( "
									+ "( \n"
									+ "select  \n"
									+ "PR_SNO, \n"
									+ "BENEFICIARY_SNO, \n"
									+ "OFFICE_ID, \n"
									+ "SUBDIV_OFFICE_ID, \n"
									+ "MONTH, \n"
									+ "YEAR, \n"
									+ "METRE_SNO, \n"
									+ "METRE_INITIAL_READING, \n"
									+ "METRE_CLOSING_READING, \n"
									+ "QTY_CONSUMED,QTY_CONSUMED_NET , \n"
									+
									// "ALLOTED_QTY, \n"+
									// "EXCESS_QTY, \n"+
									"METRE_FIXED, \n" + "METRE_WORKING, \n"
									+ "PROCESS_FLAG ," + "CHILD_METER_QTY  \n"
									+ " from  \n" + "PMS_DCB_TRN_MONTHLY_PR \n"
									+ " where  BENEFICIARY_SNO="
									+ input_value
									+ " and MONTH="
									+ month
									+ " and YEAR="
									+ year
									+ " \n"
									+ " )PR \n"
									+ " join \n"
									+ " ( \n"
									+ " select \n"
									+ " BENEFICIARY_SNO,\n"
									+ " SCHEME_SNO,\n"
									+ " METRE_SNO,\n"
									+ " METRE_LOCATION,\n"
									+ " METRE_INIT_READING ,\n"
									+ " MULTIPLY_FACTOR ,METRE_TYPE,PARENT_METRE "
									+ " from \n" 
									+ " PMS_DCB_MST_BENEFICIARY_METRE  where meter_status='L' \n  order by   METRE_SNO"
									+ " )MET \n"
									+ " on MET.BENEFICIARY_SNO=PR.BENEFICIARY_SNO and MET.METRE_SNO=PR.METRE_SNO \n"
									+ " join ( \n"
									+ " select \n"
									+ " sch_name,\n"
									+ " sch_sno \n"
									+ " from \n"
									+ " PMS_SCH_MASTER SCH \n"
									+ " )SCH \n"
									+ " on SCH.sch_sno=MET.SCHEME_SNO \n"
									+ " join \n"
									+ "("
									+ "select \n"
									+ "BENEFICIARY_SNO,\n"
									+ "BENEFICIARY_NAME,"
									+ "BENEFICIARY_TYPE_ID \n"
									+ "from \n"
									+ "PMS_DCB_MST_BENEFICIARY where status='L' \n"
									+ ")BEN \n"
									+ " on BEN.BENEFICIARY_SNO=PR.BENEFICIARY_SNO \n "
									+ ""
									+ "join "
									+ "("
									+ " select "
									+ "BEN_TYPE_DESC,"
									+ "BEN_TYPE_ID "
									+ " from "
									+ " PMS_DCB_BEN_TYPE"
									+ ")BEN_TYPE "
									+ " on BEN_TYPE.BEN_TYPE_ID=BEN.BENEFICIARY_TYPE_ID "
									+ " join "
									+ " ( select "
									+ " NET_CONSUMED,"
									+ " MONPR_SNO,"
									+ " MONTH,"
									+ " YEAR,"
									+ " BENEFICIARY_SNO "
									+ " from "
									+ " PMS_DCB_MONTHLY_PR) MR "
									+ " on MR.BENEFICIARY_SNO=PR.BENEFICIARY_SNO and MR.MONTH=PR.MONTH and MR.YEAR=PR.YEAR  "
									+ "   )    ";
							
							
							String msno = "", mfixed = "", mworking = "", lastmonth = "";
							int i = 0;
							rs = obj.getRS(select_query);
							while (rs.next())
							{
								msno = rs.getString("METRE_SNO");
								mworking = obj.isNull(rs.getString("METRE_WORKING"), 2);
								if (mworking.equals(""))  mworking = "No";
								mfixed = obj.isNull(rs.getString("METRE_FIXED"), 2);
								xml += "<sno>" + msno+ "</sno><METRE_LOCATION>"+ rs.getString("METRE_LOCATION")+ "</METRE_LOCATION>";
								xml += "<NET_CONSUMED>"+ obj.isNull(rs.getString("NET_CONSUMED"), 1)+ "</NET_CONSUMED>";
								xml += "<MONPR_SNO>"+ obj.isNull(rs.getString("MONPR_SNO"),1) + "</MONPR_SNO>";
								xml += "<MULTIPLY_FACTOR>"+ obj.isNull(rs.getString("MULTIPLY_FACTOR"),1) + "</MULTIPLY_FACTOR>";
								xml += "<PARENT_METRE>"+ obj.isNull(rs.getString("PARENT_METRE"), 1)+ "</PARENT_METRE>";
								xml += "<ALLOTED_FLG>-</ALLOTED_FLG>";
								xml += "<METRE_TYPE>"+ obj.isNull(rs.getString("METRE_TYPE"), 1)+ "</METRE_TYPE>";
								xml += "<CHILD_METER_QTY>"+ obj.isNull(rs.getString("CHILD_METER_QTY"),1) + "</CHILD_METER_QTY>";
								xml += "<MONTH>"+ obj.isNull(rs.getString("MONTH"), 1)+ "</MONTH>";
								xml += "<PR_SNO>"+ obj.isNull(rs.getString("PR_SNO"), 1)+ "</PR_SNO>";
								xml += "<YEAR>"+ obj.isNull(rs.getString("YEAR"), 1)+ "</YEAR>";
								xml += "<metersno>"+ obj.isNull(rs.getString("METRE_SNO"),1) + "</metersno>";
								xml += "<QTY_CONSUMED_NET>"+ obj.isNull(rs.getString("QTY_CONSUMED_NET"),2) + "</QTY_CONSUMED_NET>";
								xml += "<BENEFICIARY_NAME>"+ obj.isNull(rs.getString("BENEFICIARY_NAME"),2) + "</BENEFICIARY_NAME>";
								xml += "<BEN_TYPE_DESC>"+ obj.isNull(rs.getString("BEN_TYPE_DESC"), 2)+ "</BEN_TYPE_DESC>";
								xml += "<METRE_FIXED>" + mfixed+ "</METRE_FIXED>";
								xml += "<QTY_CONSUMED>"+ obj.isNull(rs.getString("QTY_CONSUMED"), 1)+ "</QTY_CONSUMED>";
								xml += "<METRE_CLOSING_READING>"+ obj.isNull(rs.getString("METRE_CLOSING_READING"),1)+ "</METRE_CLOSING_READING>";
								xml += "<METRE_INITIAL_READING>"+ obj.isNull(rs.getString("METRE_INITIAL_READING"),1)+ "</METRE_INITIAL_READING>";
								xml += "<METRE_WORKING>" + mworking+ "</METRE_WORKING>";
								xml += "<ALLOTED_QTY>0</ALLOTED_QTY>";
								xml += "<SCHEME_NAME><![CDATA["+ obj.isNull(rs.getString("SCH_NAME"),1) + "]]></SCHEME_NAME>";
								xml += "<PROCESS_FLAG>"+ obj.isNull(rs.getString("PROCESS_FLAG"), 1)+ "</PROCESS_FLAG>";
							}
							if (i == 0)
								xml += "<status>no record for entry</status>";
							else
								xml += "<status>" + i + "</status>";
							xml += "</result>";
						} else if (process_code.equals("2")
								|| process_code.equals("4")
								|| process_code.equals("15")) {
							String consumer = obj.setValue("consumer", request);
							if (consumer == "0")
								consumer = input_value;
							xml = "<result>";
							String flag = obj.setValue("flag", request);
							String ben_type = obj.setValue("bentype", request);
							String SUB_DIV_ID = obj.setValue("subdiv", request);
							String SCH_SNO= obj.setValue("sch_load", request);
							String cond = "";
							String cond1 = "";
							if (Integer.parseInt(flag) == 0)
								flag = "3";
							if (Integer.parseInt(flag) == 3) {
								if (Integer.parseInt(SUB_DIV_ID) == 0)
									cond = ""+meter_status+"  BENEFICIARY_SNO=" + consumer;
								else
									cond = ""+meter_status+"  BENEFICIARY_SNO=" + consumer+ " and ( SCHEME_SNO=" + SCH_SNO+" or SUB_DIV_ID="+SUB_DIV_ID+")";
									//cond = "  BENEFICIARY_SNO=" + consumer+ " and SUB_DIV_ID=" + SUB_DIV_ID;
								cond1 = " BENEFICIARY_SNO=" + consumer + "  ";
							} else if (Integer.parseInt(flag) == 4) {
								 cond = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where status='L'   and OFFICE_ID="+Office_id+"  ) and SCHEME_SNO=" + SCH_SNO;
								 cond1 = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where status='L'  and OFFICE_ID="+Office_id+"  )";
							} else if (Integer.parseInt(flag) ==5) {
								 cond = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where status='L'  and BENEFICIARY_TYPE_ID="+ben_type+"   and OFFICE_ID="+Office_id+"  ) and SCHEME_SNO=" + SCH_SNO;
								 cond1 = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where status='L'   and BENEFICIARY_TYPE_ID="+ben_type+" and OFFICE_ID="+Office_id+"  )";
							}   else if (Integer.parseInt(flag) ==6) {
								 cond = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where status='L'  and BENEFICIARY_TYPE_ID="+ben_type+"   and OFFICE_ID="+Office_id+"  ) " ;
								 cond1 = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where status='L'   and BENEFICIARY_TYPE_ID="+ben_type+" and OFFICE_ID="+Office_id+"  )";
							} else if (Integer.parseInt(flag) == 1) {								
								// cond = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where status='L'  and OFFICE_ID="+Office_id+"  ) and SCHEME_SNO=" + SCH_SNO;
								cond = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  ) and SUB_DIV_ID=" + SUB_DIV_ID;
								cond1 = " BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY  where status='L'   and OFFICE_ID="+Office_id+"  )";
							} else if (Integer.parseInt(flag) == 2) {
								cond = " "+meter_status+" BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_TYPE_ID ="
								+ ben_type
								+ " and OFFICE_ID="
								+ Office_id
								+ ") and ( SCHEME_SNO="
								+ SCH_SNO+" or SUB_DIV_ID="+SUB_DIV_ID+" )";
								cond1 = "  BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where  "+new_cond+" BENEFICIARY_TYPE_ID ="
										+ ben_type
										+ " and OFFICE_ID="
										+ Office_id + ")";
							}     
						//	rs = null;
							qry = "select MET.METRE_SNO,"
									+ "BEN.TARIFF_MODE,\n"
									+
									// ",MET.TARIFF_ID, \n" +
									"PR.METRE_FIXED,\n" //  PR METER FIXED
									+
									// "MET.ALLOTED_QTY,\n" +
									// "MET.MIN_BILL_QTY,\n" +
									"MET.METRE_INIT_READING,\n"
									+ "MET.OFFICE_ID,\n"
									+ "MET.SCHEME_SNO,"
									+ "MET.SCH_TYPE_ID,\n"
									+ "PR.METRE_WORKING,\n"
									+ "MET.METRE_LOCATION, \n"
									+ 
									// "MET.TARIFF_RATE,"+
									// "MET.EXCESS_TARIFF_RATE," +
									"MET.MULTIPLY_FACTOR,"
									+
									// "MET.ALLOTED_FLG," +
									"BEN.BENEFICIARY_NAME,"
									+ "PR.PR_SNO,"
									+ "PR.BENEFICIARY_SNO,"
									+ "MET.BENEFICIARY_TYPE_ID,"
									+ "MET.SUB_DIV_ID,"
									+ "PR.METRE_INITIAL_READING   ,"
									+ "PR.METRE_CLOSING_READING,"
									+ "PR.QTY_CONSUMED ,"
									+ "PR.QTY_CONSUMED_CALC,"
									+ "PR.PROCESS_FLAG,PR.QTY_CONSUMED_NET,"
									+ "PR.CHILD_METER_QTY,"
									+ " SCH.SCH_NAME "
									+ "  FROM \n"
									+ "       ("
									+ "        (\n"
									+ "         select "
									+ " METRE_SNO,\n"+
									" METRE_FIXED,\n"+
									" METRE_INIT_READING,\n"
									+ " OFFICE_ID,\n"
									+ " SCHEME_SNO,"
									+ " SCH_TYPE_ID,\n"
									+ " METRE_WORKING,\n"
									+ " METRE_LOCATION , "
									+ " BENEFICIARY_SNO,"
									+ " BENEFICIARY_TYPE_ID,"+
									" MULTIPLY_FACTOR ,"+					
									" SUB_DIV_ID"
									+ "  "
									+ " from PMS_DCB_MST_BENEFICIARY_METRE where "
									+ cond
									+ "      order by METRE_SNO ) MET"
									+ ""
									+ " join ( "
									+ "select PR_SNO,"
									+ "SUBDIV_OFFICE_ID, "
									+ "METRE_INITIAL_READING, "
									+ "METRE_CLOSING_READING , "
									+ "QTY_CONSUMED_CALC,QTY_CONSUMED,"
									+
									// ",EXCESS_QTY," +
									"MONTH,YEAR, "
									+ "BENEFICIARY_SNO,"
									+ "METRE_SNO,"
									+ "CHILD_METER_QTY,PROCESS_FLAG ,QTY_CONSUMED_NET,METRE_WORKING,METRE_FIXED"
									+ " from  "
									+ " PMS_DCB_TRN_MONTHLY_PR  "
									+ " where   "
									+ cond1
									+ " and MONTH="
									+ month
									+ " and"
									+ " YEAR="
									+ year
									+ " ) PR "
									+ " on PR.BENEFICIARY_SNO=MET.BENEFICIARY_SNO "
									+ " and PR.METRE_SNO=MET.METRE_SNO "
									+ " join \n"
									+ " ( "
									+ " 	select \n"
									+ " 	BENEFICIARY_SNO,\n"
									+ "	BENEFICIARY_NAME,"
									+ "	BENEFICIARY_TYPE_ID,"
									+ "   TARIFF_MODE "
									+
									// "   ALLOTED_FLG  \n" +
									"	from \n"
									+ "	PMS_DCB_MST_BENEFICIARY where status='L'  and OFFICE_ID="+Office_id+"  order by BENEFICIARY_TYPE_ID \n"
									+ "	)BEN \n"
									+ " on BEN.BENEFICIARY_SNO=MET.BENEFICIARY_SNO  \n "
									+ " left join "
									+ "(select SCH_NAME,"
									+ " SCH_SNO from "
									+ " PMS_SCH_MASTER"
									+ ")SCH "
									+ " on SCH.SCH_SNO=MET.SCHEME_SNO "
									+
									")  order by BENEFICIARY_TYPE_ID, BENEFICIARY_NAME "
									+ "";
							
							 System.out.println(qry);
							String qty_com = "0", MIN_BILL_QTY = "", ALLOTED_QTY = "", ALLOTED_FLG = "0", excessrate = "0";
							Float rate = 0.0f;
							 ResultSet rs_r = null; 
							PreparedStatement st_r = con.prepareStatement(qry);
							rs_r = st_r.executeQuery();
							while (rs_r.next())   
							{
								String TARIFF_MODE = obj.isNull(rs_r.getString("TARIFF_MODE"), 1);
								if (TARIFF_MODE.equals("0"))
									TARIFF_MODE = "u";
								Double QTY_CONSUMED_NET = rs_r.getDouble("QTY_CONSUMED_NET");
								int msno = rs_r.getInt("METRE_SNO");
								Double mqty = rs_r.getDouble("QTY_CONSUMED_CALC");
								if (ALLOTED_FLG.equals("-"))  ALLOTED_FLG = "n";
								String meter_fixed_status= rs_r.getString("METRE_FIXED");
								row++;
								xml += "<CHILD_METER_QTY>"+ rs_r.getString("CHILD_METER_QTY")+ "</CHILD_METER_QTY>";
								xml += "<QTY_CONSUMED>"+ rs_r.getString("QTY_CONSUMED")+ "</QTY_CONSUMED>";
								xml += "<PROCESS_FLAG>"+ rs_r.getString("PROCESS_FLAG")+ "</PROCESS_FLAG>";
								xml += "<METRE_FIXED>"+ meter_fixed_status+ "</METRE_FIXED>";
								xml += "<METRE_WORKING>"+ rs_r.getString("METRE_WORKING")+ "</METRE_WORKING>";
								xml += "<SCH_NAME><![CDATA["+ rs_r.getString("SCH_NAME")+ "]]></SCH_NAME>";
								xml += "<BENEFICIARY_NAME>"+ rs_r.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
								xml += "<QTY_CONSUMED_NET>" + QTY_CONSUMED_NET+ "</QTY_CONSUMED_NET>";
								if (ALLOTED_FLG.equalsIgnoreCase("b"))
									xml += "<ALLOTED_FLG_DES>BENEFICIARY </ALLOTED_FLG_DES>";
								else if (ALLOTED_FLG.equalsIgnoreCase("n"))
									xml += "<ALLOTED_FLG_DES>NOT APPLICABLE </ALLOTED_FLG_DES>";
								else
									xml += "<ALLOTED_FLG_DES>SCHEME WISE</ALLOTED_FLG_DES>";
								if (TARIFF_MODE.equalsIgnoreCase("u"))
									xml += "<TARIFF_MODE_DES>Uniform </TARIFF_MODE_DES>";
								else if (TARIFF_MODE.equalsIgnoreCase("s"))
									xml += "<TARIFF_MODE_DES>SCHEME WISE</TARIFF_MODE_DES>";
								else
									xml += "<TARIFF_MODE_DES>LOCATION WISE</TARIFF_MODE_DES>";
								xml += "<TARIFF_MODE>" + TARIFF_MODE+ "</TARIFF_MODE>";
								xml += "<ALLOTED_FLG>-</ALLOTED_FLG>";
								xml += "<excessrate>" + excessrate+ "</excessrate>";
								xml += "<meterlocation>"+ rs_r.getString("METRE_LOCATION").replace('&', '*')+ "</meterlocation>";
								xml += "<MULTIPLY_FACTOR>"+ obj.isNull(rs_r.getString("MULTIPLY_FACTOR"),1) + "</MULTIPLY_FACTOR>";
								xml += "<metersno>" + msno + "</metersno>";
								// Meter Not Fixed Set - to MeterClosing Reading 
								if (meter_fixed_status.equalsIgnoreCase("n"))
										xml += "<meterreading>-</meterreading>";
								else
										xml += "<meterreading>"+ Math.round(Double.parseDouble(new String(new BigDecimal(rs_r.getDouble("METRE_INITIAL_READING")).toString())))+ "</meterreading>";
								xml += "<qty>" + mqty + "</qty>";
								xml += "<eqty>0</eqty>";
								// Meter Not Fixed Set - to MeterClosing Reading
								if (meter_fixed_status.equalsIgnoreCase("n"))
										xml += "<closingreading>'-'</closingreading>";
									else
										xml += "<closingreading>"+Math.round(Double.parseDouble(new String(new BigDecimal(rs_r.getDouble("METRE_CLOSING_READING")).toString())))+ "</closingreading>";
								xml += "<SCHEME_SNO>"+ obj.isNull(rs_r.getString("SCHEME_SNO"), 1)+ "</SCHEME_SNO>";
								xml += "<PR_SNO>"+ obj.isNull(rs_r.getString("PR_SNO"),1) + "</PR_SNO>";
								xml += "<SUBDIV_OFFICE_ID>"+ obj.isNull(rs_r.getString("SUB_DIV_ID"), 1)+ "</SUBDIV_OFFICE_ID>";
								xml += "<TARIFF_ID>0</TARIFF_ID>";
								xml += "<BENEFICIARY_SNO>"+ obj.isNull(rs_r.getString("BENEFICIARY_SNO"),1) + "</BENEFICIARY_SNO>";
								xml += "<SCH_TYPE_ID>"+ obj.isNull(rs_r.getString("SCH_TYPE_ID"), 1)+ "</SCH_TYPE_ID>";
								xml += "<BENEFICIARY_TYPE_ID>"+ obj.isNull(rs_r.getString("BENEFICIARY_TYPE_ID"),1)+ "</BENEFICIARY_TYPE_ID>";
							}// End While Loop
							String CB_CUR_YR_WC = "", CB_YESTER_YR_WC = "";
							try {
								xml += "<amount>"+ obj.isNull(obj.getValue("PMS_DCB_WC_BILLING","sum(TOTAL_AMT)","where BENEFICIARY_SNO="
														+ input_value+ "   and OFFICE_ID="+Office_id+" and  MONTH=" + month+ " and YEAR=" + year),2) + "</amount>";
								xml += "<eamount>"+ obj.isNull(obj.getValue("PMS_DCB_WC_BILLING","sum(EXCESS_AMT)","where BENEFICIARY_SNO="
														+ input_value+ " and OFFICE_ID="+Office_id+" and MONTH=" + month+ " and YEAR=" + year),2) + "</eamount>";
							} catch (Exception e) {
								xml += "<amount>0</amount>";
								xml += "<eamount>0</eamount>";
							}
							CB_CUR_YR_WC = obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY", "CB_CUR_YR_WC","where BENEFICIARY_SNO=" + input_value
											+ " and MONTH="
											+ (Integer.parseInt(month) - 1)
											+ " and FIN_YEAR="
											+ (Integer.parseInt(year))), 2);
							CB_YESTER_YR_WC = obj.isNull(obj.getValue("PMS_DCB_TRN_CB_MONTHLY","CB_YESTER_YR_WC", "where BENEFICIARY_SNO="
											+ input_value + " and MONTH="+ (Integer.parseInt(month) - 1)+ " and FIN_YEAR="+ (Integer.parseInt(year))), 2);
							// ////obj.testQry("month"+(Integer.parseInt(month)-1));
							double Due_as_per_Last = Double.parseDouble(CB_CUR_YR_WC)+ Double.parseDouble(CB_YESTER_YR_WC);
							netamount += amount + excessamount;
							String benname = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","where  "+new_cond+" BENEFICIARY_SNO=" + input_value), 2);
							String ben_type_ben = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_TYPE_ID","where  "+new_cond+" BENEFICIARY_SNO=" + input_value), 1);
							String ben_type_value = obj.isNull(obj.getValue("PMS_DCB_BEN_TYPE", "BEN_TYPE_DESC","where BEN_TYPE_ID=" + ben_type_ben), 2);
							String int_vlaue = obj.isNull(obj.getValue("PMS_DCB_MST_INT", "INT_RATE","where BENEFICIARY_TYPE=" + ben_type_ben),1);
							xml += "<Due_as_per_Last>" + Due_as_per_Last+ "</Due_as_per_Last>";
							xml += "<benname>" + benname + "</benname>";
							xml += "<bentype>" + ben_type_ben + "</bentype>";
							xml += "<int_vlaue>" + int_vlaue + "</int_vlaue>";
							if (Integer.parseInt(ben_type_ben) <= 6) 
							{
								String BLOCK_SNO = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY","BLOCK_SNO","where  "+new_cond+" BENEFICIARY_SNO="+ input_value), 2);
								String DISTRICT_CODE = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY","DISTRICT_CODE","where  "+new_cond+" BENEFICIARY_SNO="+ input_value), 2);
								xml += "<blockvalue>"+ obj.isNull(obj.getValue("com_mst_blocks","block_name","where block_sno="+ BLOCK_SNO), 2)+ "</blockvalue>";
								xml += "<distvalue>"+ obj.isNull(obj.getValue("COM_MST_DISTRICTS","DISTRICT_NAME","where DISTRICT_CODE="+ DISTRICT_CODE), 2)+ "</distvalue>";
							}
							xml += "<bentypevalue>" + ben_type_value+ "</bentypevalue>";
							xml += "<row>" + row + "</row>";
							xml += "</result>";
						} else if (process_code.equals("3")) 
						{
							xml = "<result>";
							rs = null;
							qry = "select MET.METRE_SNO,MET.METRE_FIXED,MET.METRE_INIT_READING,MET.OFFICE_ID,"
									+ "MET.SCHEME_SNO,MET.METRE_WORKING,MET.METRE_CODE,"+
									" FROM "
									+ "    ("
									+ "    ( \n"
									+ "    select METRE_SNO,\n"+
									"    METRE_FIXED,\n"+
									"    METRE_INIT_READING,\n"
									+ "    OFFICE_ID,\n"
									+ "    SCHEME_SNO,\n"
									+ "    METRE_WORKING,\n"
									+ "    METRE_CODE "
									+ "    from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" METRE_SNO="
									+ input_value + "       ) MET" + "       )";
							rs = stmt.executeQuery(qry);
							if (rs.next()) {
								xml += "<meterreading>"+ rs.getFloat("METRE_INIT_READING")+ "</meterreading>";
								xml += "<rate>0</rate>";
								xml += "<excessrate>"+ rs.getFloat("EXCESS_TARIFF_RATE")+ "</excessrate>";
							}
							// Collect CB From PMS_DCB_TRN_CB_MONTHLY
							String consumer = request.getParameter("consumer");
							String fyear = request.getParameter("fyear");
							String fmonth = request.getParameter("fmonth");
							if (consumer == null || consumer.equals(""))  consumer = "0";
							if (fyear == null || fyear.equals("")) 		fyear = "0";
							if (fmonth == null || fmonth.equals(""))    fmonth = "0";
							qry = "select WC_CB_TOTAL from  PMS_DCB_TRN_CB_MONTHLY where FIN_YEAR="+ fyear+ " and FIN_MONTH="
									+ (Integer.parseInt(fmonth) - 1)+ " and BENEFICIARY_SNO=" + consumer;
							rs = stmt.executeQuery(qry);
							if (rs.next()) {
								xml += "<cbtotal>"+ obj.isNull(rs.getString("WC_CB_TOTAL"), 2)+ "</cbtotal>";
							}
							xml += "</result>";
						}
					}
					if (command.equals("add")) {
						xml = "<result>";
						maxsno = obj.getMax("PMS_DCB_TRN_BILLING_DMD","BILL_SNO", "");
						String BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO", request);
						String BILLING_DT = obj.setValue("BILLING_DT", request);
						String DIV_BILL_NO = obj.setValue("DIV_BILL_NO",request);
						String BILL_PERIOD_FROM = obj.setValue("BILL_PERIOD_FROM", request);
						String BILL_MONTH = obj.setValue("billmonth", request);
						String BILL_YEAR = obj.setValue("billyear", request);
						String BILL_PERIOD_TO = obj.setValue("BILL_PERIOD_TO",request);
						String DIV_BILL_NO1 = obj.setValue("DIV_BILL_NO",request);
						String NET_CONSUMPTION = obj.setValue("NET_CONSUMPTION", request);
						String MONTH_BILL_AMT = obj.setValue("MONTH_BILL_AMT",request);
						String WC_MTH_TOTAL1 = obj.setValue("WC_MTH_TOTAL",request);
						String WC_CB_TOTAL = obj.setValue("WC_CB_TOTAL",request);
						String WC_INT_COLN = obj.setValue("WC_INT_COLN",request);
						String WC_OB = obj.setValue("WC_OB", request);
						String OTHER_MTH_TOTAL = obj.setValue("OTHER_MTH_TOTAL", request);
						String YY_COLN = obj.setValue("YY_COLN", request);
						String YY_OB = obj.setValue("YY_OB", request);
						String YY_CB = obj.setValue("YY_CB", request);
						String MAINT_OB = obj.setValue("MAINT_OB", request);
						String MAINT_COLN = obj.setValue("MAINT_COLN", request);
						String MAINT_CB = obj.setValue("MAINT_CB", request);
						String INT_CALC = obj.setValue("INT_CALC", request);
						String WC_COLN = obj.setValue("WC_COLN", request);
						String rs_value = obj.setValue("rs_value", request);
						String ins_qry = "insert into PMS_DCB_TRN_BILLING_DMD (BILL_SNO"
								+ ",OFFICE_ID	"
								+ ",BENEFICIARY_SNO"
								+ ",BILL_PERIOD_FROM"
								+ ",BILL_PERIOD_TO"
								+ ",BILL_MONTH"
								+ ",BILL_YEAR"
								+ ",DIV_BILL_NO"
								+ ",MAINT_OB"
								+ ",MAINT_COLN"
								+ ",MAINT_CB"
								+ ",ADDN_ANY"
								+ ",WC_OB"
								+ ",WC_MTH_TOTAL"
								+ ",INT_CALC"
								+ ",MONTH_BILL_AMT"
								+ ",WC_COLN"
								+ ",WC_INT_COLN"
								+ ",YY_OB"
								+ ",YY_COLN"
								+ ",YY_CB"
								+ ",WC_CB_TOTAL"
								+ ",BILL_RAISED_BY"
								+ ",BILLING_DT"
								+ ",BILL_DUE_DATE"
								+ ",BILL_CODE	"
								+ ",NET_CONSUMPTION"
								+ ",OTHER_MTH_TOTAL"
								+ ",UPDATED_BY_USER_ID"
								+ ",UPDATED_DATE," + "AMT_WORDS)";
						String ins_value = " values  (" + maxsno + ","
								+ Office_id + "," + "" + BENEFICIARY_SNO + ","
								+ "to_date('" + BILL_PERIOD_FROM
								+ "','DD/MM/YYYY')," + "to_date('"
								+ BILL_PERIOD_TO + "','DD/MM/YYYY')," + ""
								+ BILL_MONTH + "," + "" + BILL_YEAR + "," + ""
								+ DIV_BILL_NO + "," + "" + MAINT_OB + "," + ""
								+ MAINT_COLN + "," + "" + MAINT_CB + "," + ""
								+ OTHER_MTH_TOTAL + "," + "" + WC_OB + "," + ""
								+ WC_MTH_TOTAL + "," + "" + INT_CALC + "," + ""
								+ MONTH_BILL_AMT + "," + "" + WC_COLN + ","
								+ "" + WC_INT_COLN + "," + "" + YY_OB + ","
								+ "" + YY_COLN + "," + "" + YY_CB + "," + ""
								+ WC_CB_TOTAL + "," + "'" + userid + "',"
								+ "to_date('" + BILLING_DT + "','DD/MM/YYYY'),"
								+ "to_date('" + BILLING_DT + "','DD/MM/YYYY'),"
								+ "" + DIV_BILL_NO + "," + "" + NET_CONSUMPTION
								+ "," + OTHER_MTH_TOTAL + ",'" + userid + "',"
								+ "clock_timestamp()," + rs_value + "" + ")";
						tran_row = obj.setUpd(ins_qry + ins_value);
						//System.out.println("qry" + ins_qry + ins_value);
						String ins_qrySCH = "insert into PMS_DCB_TRN_BILLING_DMD_SCH (BILL_SNO"
								+ ",OFFICE_ID"
								+ ",BENEFICIARY_SNO"
								+ ",BILL_PERIOD_FROM"
								+ ",BILL_PERIOD_TO"
								+ ",BILL_MONTH"
								+ ",BILL_YEAR"
								+ ",DIV_BILL_NO"
								+ ",MAINT_OB"
								+ ",MAINT_COLN"
								+ ",MAINT_CB"
								+ ",ADDN_ANY"
								+ ",WC_OB"
								+ ",WC_MTH_TOTAL"
								+ ",INT_CALC"
								+ ",MONTH_BILL_AMT"
								+ ",WC_COLN"
								+ ",WC_INT_COLN"
								+ ",YY_OB"
								+ ",YY_COLN"
								+ ",YY_CB"
								+ ",WC_CB_TOTAL"
								+ ",BILL_RAISED_BY"
								+ ",BILLING_DT"
								+ ",BILL_DUE_DATE"
								+ ",BILL_CODE	"
								+ ",NET_CONSUMPTION"
								+ ",OTHER_MTH_TOTAL"
								+ ",UPDATED_BY_USER_ID"
								+ ",UPDATED_DATE," + "AMT_WORDS)";
						String ins_valueSCH = " values  (" + maxsno + ","
								+ Office_id + "," + "" + BENEFICIARY_SNO + ","
								+ "to_date('" + BILL_PERIOD_FROM
								+ "','DD/MM/YYYY')," + "to_date('"
								+ BILL_PERIOD_TO + "','DD/MM/YYYY')," + ""
								+ BILL_MONTH + "," + "" + BILL_YEAR + "," + ""
								+ DIV_BILL_NO + "," + "" + MAINT_OB + "," + ""
								+ MAINT_COLN + "," + "" + MAINT_CB + "," + ""
								+ OTHER_MTH_TOTAL + "," + "" + WC_OB + "," + ""
								+ WC_MTH_TOTAL + "," + "" + INT_CALC + "," + ""
								+ MONTH_BILL_AMT + "," + "" + WC_COLN + ","
								+ "" + WC_INT_COLN + "," + "" + YY_OB + ","
								+ "" + YY_COLN + "," + "" + YY_CB + "," + ""
								+ WC_CB_TOTAL + "," + "'" + userid + "',"
								+ "to_date('" + BILLING_DT + "','DD/MM/YYYY'),"
								+ "to_date('" + BILLING_DT + "','DD/MM/YYYY'),"
								+ "" + DIV_BILL_NO + "," + "" + NET_CONSUMPTION
								+ "," + OTHER_MTH_TOTAL + ",'" + userid + "',"
								+ "clock_timestamp()," + rs_value + "" + ")";
						//System.out.println("qry" + ins_qrySCH + ins_qrySCH);
						tran_row = obj.setUpd(ins_qrySCH + ins_qrySCH);
						xml += "<tran_row>" + tran_row + "</tran_row><maxsno>"+ maxsno + "</maxsno>";
						String rows = obj.setValue("rows", request);
						String ins_query1 = "";
						for (int i = 1; i <= Integer.parseInt(rows); i++) 
						{
						int WC_TRN_SNO = obj.getMax("PMS_DCB_WC_BILLING","WC_TRN_SNO", "");
						String PR_SNO = obj.setValue("PR_SNO" + i, request);
						String SCH_TYPE_ID = obj.setValue("SCH_TYPE_ID" + i, request);
						String SCHEME_SNO = obj.setValue("SCHEME_SNO" + i,request);
						String WC_TARIFF_ID = obj.setValue("WC_TARIFF_ID"+ i, request);
						String TOTAL_AMT = obj.setValue("TOTAL_AMT" + i,request);
						String EXCESS_AMT = obj.setValue("EXCESS_AMT" + i,request);
						String AMT = obj.setValue("AMT" + i, request);
						String EXCESS_QTY = obj.setValue("EXCESS_QTY" + i,request);
						String QTY_CONSUMED = obj.setValue("QTY_CONSUMED"+ i, request);
						String METRE_SNO = obj.setValue("METRE_SNO" + i,request);
						String SUBDIV_OFFICE_ID = obj.setValue("SUBDIV_OFFICE_ID" + i, request);
						String excessrate = obj.setValue("excessrate" + i,request);
						String rate = obj.setValue("rate" + i, request);
						String sub_ins = "insert into PMS_DCB_WC_BILLING("
									+ "WC_TRN_SNO" + ",BENEFICIARY_SNO"
									+ ",OFFICE_ID" + ",SUBDIV_OFFICE_ID"
									+ ",MONTH" + ",YEAR" + ",METRE_SNO"
									+ ",QTY_CONSUMED" + ",EXCESS_QTY" + ",AMT"
									+ ",EXCESS_AMT" + ",TOTAL_AMT"
									+ ",WC_TARIFF_ID" + ",SCHEME_SNO"
									+ ",SCH_TYPE_ID" + ",PR_SNO" + ",BILL_SNO "
									+ ",TARIFF_RATE" + ",EXCESS_RATE"
									+ ",UPDATED_BY_USER_ID" + ",UPDATED_DATE"
									+ ")";
						String sub_vaules = "values  (" + WC_TRN_SNO + ","
									+ BENEFICIARY_SNO + "" + "," + Office_id
									+ "" + "," + SUBDIV_OFFICE_ID + "" + ","
									+ BILL_MONTH + "" + "," + BILL_YEAR + ""
									+ "," + METRE_SNO + "" + "," + QTY_CONSUMED
									+ "" + "," + EXCESS_QTY + "" + "," + AMT
									+ "" + "," + EXCESS_AMT + "" + ","
									+ TOTAL_AMT + "" + "," + WC_TARIFF_ID + ""
									+ "," + SCHEME_SNO + "" + "," + SCH_TYPE_ID
									+ "" + "," + PR_SNO + "" + "," + maxsno
									+ "" + "," + rate + "" + "," + excessrate
									+ "" + ",'" + userid + "'"
									+ ",clock_timestamp()" + ")";
						row += obj.setUpd(sub_ins + sub_vaules);
						}
						xml += "<row>" + row + "</row>";
						qry = "";
						qry = "insert into PMS_DCB_TRN_CB_MONTHLY (BENEFICIARY_CB_SNO,"
								+ "BENEFICIARY_SNO,"
								+ "WC_DMD_UPTO_PREV_MONTH,"
								+ "WC_CB_TOTAL,"
								+ "INT_UPTO_CUR_MONTH_CALC,"
								+ "YESTERYR_DMD_UPTO_PREV_MONTH,"
								+ "YESTERYR_CB,"
								+ "FIN_YEAR,"
								+ "FIN_MONTH,"
								+ "UPDATED_BY_USER_ID,"
								+ "UPDATED_DATE,"
								+ "MAINT_DMD_UPTO_PREV_MONTH,"
								+ "MAINT_CB_TOTAL) values ";
						int max_PMS_DCB_TRN_CB_MONTHLY = obj.getMax("PMS_DCB_TRN_CB_MONTHLY", "BENEFICIARY_CB_SNO","");
						qry += "(" + max_PMS_DCB_TRN_CB_MONTHLY + "" + ","
								+ BENEFICIARY_SNO + "," + "" + WC_OB + "," + ""
								+ WC_CB_TOTAL + "," + "" + INT_CALC + "," + ""
								+ YY_OB + "," + "" + YY_CB + "" + ","
								+ BILL_MONTH + "" + "," + BILL_YEAR + "," + "'"
								+ userid + "'" + ",clock_timestamp()," + ""
								+ MAINT_OB + "," + "" + MAINT_CB + "" + ")";
						//System.out.println("qry" + qry);
						int ob_row = obj.setUpd(qry);
						xml += "</result>";
					}
					pr.write(xml);
					pr.flush();
					pr.close();
					obj.conClose(con);
					System.out.println("Phase 1");  
				}
				System.out.println("Phase 2");
			}
		} catch (Exception e) {
			xml = "<error>" + e + "</error>";
			System.out.println("Exception in opening connection:"+e.getStackTrace());  
		}
	}  
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}