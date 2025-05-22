/* 
 * Created on : dd-mm-yy 
 * Author     : TAMIL
 * Last Date  : 21/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 
 *-----------------------------------------------------------------------------
 *-----------------------------------------------------------------------------
 */
package Servlets.PMS.PMS1.DCB.reports;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.PMS.PMS1.DCB.servlets.Controller;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
public class OpeningBalanceReport extends HttpServlet 
{
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
	}
	String new_cond=Controller.new_cond; 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		// PreparedStatement ps=null;
		Controller obj = null;
		String option="0";
		String process="";
		try {
			ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs.getString("Config.DSN");
			String strhostname = rs.getString("Config.HOST_NAME");
			String strportno = rs.getString("Config.PORT_NUMBER");
			String strsid = rs.getString("Config.SID");
			String strdbusername = rs.getString("Config.USER_NAME");
			String strdbpassword = rs.getString("Config.PASSWORD");
	//		ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,strdbusername.trim(), strdbpassword.trim());
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		response.setContentType(CONTENT_TYPE);
		String strCommand = "", mvalue = "";
		String flag = "DCB";
		String xml = "";
		int month = 0;
		int year = 0;
		String Office_id = "0", Office_name = "";
		String userid = "0";
		HttpSession session = request.getSession(false);
		userid = (String) session.getAttribute("UserId");
		if (userid == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		try {
			Connection con = null;
			obj = new Controller();
			con = obj.con();
			Statement stmt = null;
			stmt = con.createStatement();
		//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",		"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"		+ userid + "')");
			
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			
			month =Integer.parseInt(request.getParameter("month"));
			year =Integer.parseInt(request.getParameter("year"));// Integer.parseInt(obj.isNull(obj.getValue("PMS_DCB_SETTING","YEAR", "where OFFICE_ID=" + Office_id), 1));
			process=obj.setValue("process", request);			
			mvalue = obj.month_val(Integer.toString(month));			
			if (Office_name.equals(""))
				Office_name = "";
			
			option=obj.setValue("option", request);  
		String off=obj.setValue("report_id", request);
		if ( !off.equalsIgnoreCase("0")) Office_id=off;    
		Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		} catch (Exception e) {
		}  
		int ben = 0;       
		int count = 2;  
		int oid = 0;
		try {
			if (session == null) 
			{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
		} catch (Exception e) 
		{
			System.out.println("Redirect Error :" + e);
		}
		try {
			strCommand = request.getParameter("command");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			flag = request.getParameter("flag").trim();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.testQry("DCB->OpeningBalanceReport->command->"+strCommand+"->flag->"+flag);
		try {
			count = Integer.parseInt(request.getParameter("count"));
		} catch (Exception e) {
		}
		try {
			month = Integer.parseInt(request.getParameter("month"));
		} catch (Exception e) {
		}
		try {
			year = Integer.parseInt(request.getParameter("year"));
		} catch (Exception e) {
		}
		try {
			ben = Integer.parseInt(request.getParameter("ben"));
		} catch (Exception e) {
		}
		File reportFile = null;
		String inp_month = "", inp_year = "";int report_office_id=0;
		if (strCommand.equalsIgnoreCase("Region_Wise_Op")) {
			try {
				try {
					report_office_id = Integer.parseInt(request.getParameter("report_office_id"));
				} catch (Exception e) {
				}
				reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Ob_Rpt.jasper"));
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
				Map map1 = new HashMap(); 
			 	map1.put("office_id", report_office_id);
				map1.put("year", year);
				map1.put("month", month);
				map1.put("mvalue", mvalue);  
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map1, connection);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				response.setHeader("Content-Disposition","attachment;filename=\"OpeningBalanceDCB_All_BLK.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf);
			}catch (Exception e) {
			}
		}
		System.out.println(strCommand);
		if (strCommand.equalsIgnoreCase("Report")) 
		{
			   response.setContentType("application/pdf");  
			try {
				if ("missing".equalsIgnoreCase(flag)) 
				{
					if (inp_month.equalsIgnoreCase("")|| inp_month.equalsIgnoreCase("0"))
					{
						month = month;
						year = year;
					} 
					else {
						month = Integer.parseInt(inp_month);
						year = Integer.parseInt(inp_year);
					}
					Map map=new HashMap();
					map.put("office_id", Office_id);
					map.put("year", Integer.toString(year));
					map.put("month", Integer.toString(month));
					map.put("mvalue", mvalue);  
					map.put("divname", Office_name);     
					String imagespath = getServletContext().getRealPath("/images/")+File.separator;
					map.put("imgpath", imagespath);
		        	reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OB_Missing.jasper"));
		            if (!reportFile.exists())
		            	throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		            String ctxpath = request.getRequestURL().toString();
		            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
		            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
		         
		            response.setContentLength(buf.length);
		            response.setHeader ("Content-Disposition", "attachment;filename=\"OB_Missing.pdf\"");
		            OutputStream out = response.getOutputStream();
		            out.write(buf);
				}else 	if ("missing2".equalsIgnoreCase(flag)) 
				{  
					if (inp_month.equalsIgnoreCase("")|| inp_month.equalsIgnoreCase("0"))
					{
						month = month;
						year = year;
					} 
					else {
						month = Integer.parseInt(inp_month);
						year = Integer.parseInt(inp_year);
					} 
					Map map=new HashMap();
					map.put("office_id", Office_id);
					map.put("year", Integer.toString(year));
					map.put("month", Integer.toString(month));
					map.put("mvalue", mvalue);
					map.put("divname", Office_name);
					  System.out.println("mapppppppppppppppp"+map);
					String imagespath = getServletContext().getRealPath("/images/")+File.separator;
					map.put("imgpath", imagespath);    
		        	reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/obmissing_sch_wise.jasper"));
		            if (!reportFile.exists())
		            	throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		            String ctxpath = request.getRequestURL().toString();
		            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
		          
		            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
		            response.setContentType("application/pdf");
		            response.setContentLength(buf.length);
		            response.setHeader ("Content-Disposition", "attachment;filename=\"OB_Missing.pdf\"");
		            OutputStream out = response.getOutputStream();
		            out.write(buf);
		            
		            
		            
		            
				}
				Map map = new HashMap();
				map.put("office_id", Office_id);
				map.put("year", year);
				map.put("month", month);
				map.put("mvalue", mvalue);
				map.put("divname", Office_name);
				if ("DCB".equalsIgnoreCase(flag))
				{
								
								map.put("office_id", Office_id);  
								map.put("year", year);
								map.put("month", month);
								map.put("mvalue", mvalue);  
								map.put("divname", Office_name);
								inp_month = obj.setValue("month", request);
								inp_year = obj.setValue("year", request);
								String imagespath = getServletContext().getRealPath("/images/")+File.separator;
								map.put("imgpath", imagespath);
								if (inp_month.equalsIgnoreCase("")
										|| inp_month.equalsIgnoreCase("0")) {
									month = month;  
									year = year;
								} else {
									month = Integer.parseInt(inp_month);
									year = Integer.parseInt(inp_year);
								}
								if (Integer.parseInt(inp_month) <=3 )
								{
									map.put("apryear", Integer.toString(year-1));									
								}
								else 
								{
									map.put("apryear", Integer.toString(year));  
								}
								 System.out.println("mappinggggggg"+map);
								if (process.equalsIgnoreCase("1")) 
								{
									System.out.println("iffffffffffffffffffffff");
									reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OpeningBalanceDCB_All_BLK.jasper"));
								}
								else
								{
									System.out.println("elseiffffffffffffffffffffff");
									reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OpeningBalanceDCB_BEN_All_BLK.jasper"));
								}
				} else if ("DivisionWise".equalsIgnoreCase(flag))
						{
							map.put("year", year);
							map.put("month", month);
							map.put("mvalue", mvalue);    
							inp_month = obj.setValue("month", request);
							inp_year = obj.setValue("year", request);
							String imagespath = getServletContext().getRealPath("/images/")+File.separator;
							map.put("imgpath", imagespath);
							if (inp_month.equalsIgnoreCase("")  
									|| inp_month.equalsIgnoreCase("0")) {
								month = month;  
								year = year;
							} else {
								month = Integer.parseInt(inp_month);
								year = Integer.parseInt(inp_year);
							}
							if (Integer.parseInt(inp_month) <=3 )
							{
								map.put("apryear", Integer.toString(year-1));									
							}
							else 
							{
								map.put("apryear", Integer.toString(year));  
							}
							System.out.println("dhinesh2");
								reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_wise_OB.jasper"));
							 
			} else      
				{
				System.out.println("dhinesh1");
//							obj.getValue("COM_MST_OFFICES","OFFICE_NAME", "where OFFICE_ID=" + Office_id)
							map.put("office_name",Office_name );
							reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Int_localbody_new.jasper"));
				}
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
				
				OutputStream out = response.getOutputStream();
				 if (option.equalsIgnoreCase("1"))
				{ 
					//byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					//response.setContentType("application/pdf");
				//	response.setContentLength(buf.length);
					/*if ("DCB".equalsIgnoreCase(flag)) 
					{  
						response.setHeader("Content-Disposition","attachment;filename=\"OpeningBalanceDCB_AllBLK.pdf\"");
					}else if ("DivisionWise".equalsIgnoreCase(flag)) 
					{ 
						response.setHeader("Content-Disposition","attachment;filename=\"Division_wise_OB.pdf\"");
					}else {
						response.setHeader("Content-Disposition","attachment;filename=\"OpeningBalanceInt.pdf\"");
					}*/
					reportPdf( response, jasperPrint, out); 
				//	out.write(buf);
					//out.flush();  
				}else if (option.equalsIgnoreCase("2")) 
				{
					excelshow( response, jasperPrint, out); 
				}else if (option.equalsIgnoreCase("3")) 
				{  
					htmlshow( response, jasperPrint, out); 
				}
			} catch (Exception ex) {
				String connectMsg = "Could not create the report "+ ex.getMessage() + " " + ex.getLocalizedMessage();
			}
		} else if (strCommand.equals("Year")) 
		{
			response.setContentType("text/xml");
			PrintWriter pw = response.getWriter();
			response.setHeader("Cache-Control", "no-cache");
			xml = "<response><command>Year</command>";
			try {
				 
				int yr = java.util.Calendar.getInstance().get(Calendar.YEAR);
				for (int i = count - 1; i >= 0; i--) {
					xml += "<row>";
					xml += "<year>" + (yr - i) + "</year>";
					xml += "</row>";
				}
				xml += "<flag>success</flag>";
			} catch (Exception e)
			{
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			pw.write(xml);
			pw.flush();
			pw.close();
		}
	}
	
	protected void reportPdf(HttpServletResponse response,JasperPrint jasperPrint, OutputStream outuputStream2) 
	{
		
		System.out.println("Report Design Start"); 
		JasperPrint jasperPrint2 = null;
		try {
			JRExporter exporter = null;
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"Report.pdf" + "\"");
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
			response.setHeader("Content-Disposition", "attachment;filename=\"Report.xls\"");
			System.out.println("excel"+response);
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
			response.setContentType("application/html");
			response.setHeader("Content-Disposition","attachment; filename=\"Report.html\"");
			
			System.out.println("response"+response);
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

	
	
	
	