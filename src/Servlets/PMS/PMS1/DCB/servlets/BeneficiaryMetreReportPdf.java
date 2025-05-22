/* 
  * Created on : dd-mm-yy 
  * Author     : Sathya
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
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
public class BeneficiaryMetreReportPdf extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Connection connection = null;
		int BENEFICIARY_TYPE_ID_VAR = 0;
		int SUB_DIV_ID_VAR = 0;
		int BENEFICIARY_SNO_VAR = 0;
		int OFFICE_ID_VAR;
		int flag = 0;
		int divisionname = 0;
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
		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
		} catch (Exception ex) {
			String connectMsg = "Could not create the connection"
					+ ex.getMessage() + " " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
		try {
			BENEFICIARY_TYPE_ID_VAR = Integer.parseInt(request
					.getParameter("Beneficiary_type"));
		} catch (Exception e) {
			 
		}
		try {
			SUB_DIV_ID_VAR = Integer.parseInt(request
					.getParameter("SubDivision"));
		} catch (Exception e) {
			 
		}
		try {
			BENEFICIARY_SNO_VAR = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));
		} catch (Exception e) {
			 
		}
		try {
			divisionname = Integer.parseInt(request
					.getParameter("divisionname"));
		} catch (Exception e) {
			 
		}
		
		OFFICE_ID_VAR = divisionname;
		String action = request.getParameter("command");
		String reportvalue = request.getParameter("reportvalue");
		System.out.println("DCB-->BeneficiaryMetreReportPdf-->command-->"+action);
		if (action.equalsIgnoreCase("Ben_Met")) 
		{
			Map parameters = new HashMap();
			parameters.put("office_id", Integer.parseInt(request.getParameter("office_id")));
			File   reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/ben_with_meter.jasper"));
			if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");
			JasperReport jasperReport;
			try {
				jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			jasperReport.setWhenNoDataType(jasperReport.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
			byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
			response.setContentType("application/pdf");
			response.setContentLength(buf.length);
			OutputStream out = response.getOutputStream();
			out.write(buf, 0, buf.length);
			} catch (JRException e)
			{
				e.printStackTrace();
			}
		}
		if (action.equalsIgnoreCase("cmdreport")) 
		{
			Map parameters = new HashMap();
			String path = "";
			File reportFile = null;
			try {
				if (reportvalue.equals("1")) {
					parameters.put("BENEFICIARY_TYPE_ID",BENEFICIARY_TYPE_ID_VAR);
					parameters.put("OFFICE_ID", OFFICE_ID_VAR);
					parameters.put("DIVISION_ID", OFFICE_ID_VAR);
					reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_1.jasper"));
				}
				if (reportvalue.equals("2")) 
				{
					parameters.put("SUB_DIV_ID", SUB_DIV_ID_VAR);
					parameters.put("OFFICE_ID", OFFICE_ID_VAR);
					parameters.put("DIVISION_ID", OFFICE_ID_VAR);
					reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_2.jasper"));
				}
				if (reportvalue.equals("3")) 
				{
					parameters.put("BENEFICIARY_TYPE_ID",BENEFICIARY_TYPE_ID_VAR);
					parameters.put("SUB_DIV_ID", SUB_DIV_ID_VAR);
					parameters.put("OFFICE_ID", OFFICE_ID_VAR);
					parameters.put("DIVISION_ID", OFFICE_ID_VAR);
					reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_3A.jasper"));
				}
				if (reportvalue.equals("4")) 
				{
					parameters.put("BENEFICIARY_TYPE_ID",BENEFICIARY_TYPE_ID_VAR);
					parameters.put("BENEFICIARY_SNO", BENEFICIARY_SNO_VAR);
					parameters.put("OFFICE_ID", OFFICE_ID_VAR);
					parameters.put("DIVISION_ID", OFFICE_ID_VAR);
					reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_4.jasper"));
				}
				if (reportvalue.equals("5")) {
					parameters.put("BENEFICIARY_TYPE_ID",BENEFICIARY_TYPE_ID_VAR);
					parameters.put("SUB_DIV_ID", SUB_DIV_ID_VAR);
					parameters.put("BENEFICIARY_SNO", BENEFICIARY_SNO_VAR);
					parameters.put("OFFICE_ID", OFFICE_ID_VAR);
					parameters.put("DIVISION_ID", OFFICE_ID_VAR);
					reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_4A.jasper"));
				}
				if (reportvalue.equals("6")) {
					parameters.put("OFFICE_ID", OFFICE_ID_VAR);
					parameters.put("DIVISION_ID", OFFICE_ID_VAR);
					reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_list.jasper"));
				}
				if (!reportFile.exists())
					throw new JRRuntimeException("File J not found. The report design must be compiled first.");
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
				jasperReport.setWhenNoDataType(jasperReport.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				flag = 1;
			} catch (JRException e) {
				throw new ServletException(e);
			}
		}
	}
}
