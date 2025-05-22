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
import java.io.Closeable;
import java.io.File;
import org.apache.poi.hssf.util.Region;
import java.io.IOException;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import Servlets.PMS.PMS1.DCB.methods.MonthConverter;

import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class Water_Charges_Report extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	public String report_name = "Report";

	public Water_Charges_Report() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);

		File reportFile = null;
		String qry = "", Office_id = "0",filename="", Office_name = "", userid = "0", ACCOUNTING_UNIT_ID = "", command = "",
				process_code = "0", month = "0", year = "0", sel_qry = "", path = "", ctxpath = "", option = "0";
		Statement stmt3 = null;
		OutputStream outuputStream2 = null;
		JasperPrint jasperPrint2 = null;
		Map parameters = new HashMap();
		Controller obj = new Controller();
		Connection con = null;
		CallableStatement cs = null;
		try {
			con = obj.con();
			obj.createStatement(con);
			command = obj.setValue("command", request);// Command
			if (command == null || command.equals(""))
				command = "no command";
			option = obj.setValue("option", request);// option
			process_code = obj.setValue("process_code", request);// process
			int flag = 0;

			HttpSession session = request.getSession(false);
			try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {
			}
			System.out.println("user id " + userid);
			if (userid == null) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			System.out.println("DCB-------->Water_Charges_Report-->command--->" + command
					+ "---------->--process_code (" + process_code + ")-->option-->" + option);

			// Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS
			// NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >=
			// clock_timestamp() THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where
			// EMPLOYEE_ID in ( select EMPLOYEE_ID from SEC_MST_USERS where
			// USER_ID='"+userid+"')","OFFICE_ID") ;

			Office_id = obj.getValu("PMS_DCB_COM_OFFICE_SWITCH",
					"CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" + userid + "')",
					"OFFICE_ID");

			// Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where
			// EMPLOYEE_ID in ( select EMPLOYEE_ID from SEC_MST_USERS where USER_ID='"+
			// userid + "')");

			if (Office_id.equalsIgnoreCase("0"))
				Office_id = obj.isNull(obj.setValue("office_id", request), 1);
			if (Office_id.equalsIgnoreCase("0") || Office_id.equalsIgnoreCase(""))
				Office_id = "5000";
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
			if (Office_name.equals(""))
				Office_name = "";
			response.setContentType("application/pdf");
			ACCOUNTING_UNIT_ID = obj.getValue("FAS_MST_ACCT_UNITS", "ACCOUNTING_UNIT_ID",
					"where ACCOUNTING_UNIT_OFFICE_ID=" + Office_id);
			String month_value_display = "", count = "2";
			if (command.trim().equalsIgnoreCase("FAS_DCB_DMD")) {
				int month1 = obj.intConv(obj.setValue("pmonth", request));
				int month2 = obj.intConv(obj.setValue("pmonth1", request));
				int year2 = obj.intConv(obj.setValue("pyear", request));
				parameters.put("year1", obj.setValue("pyear", request));
				parameters.put("month1", obj.setValue("pmonth", request));
				parameters.put("month2", obj.setValue("pmonth1", request));
				String days = obj.month_val2(obj.intConv(obj.setValue("pmonth1", request)));
				if (month1 > month2) {
					year2++;
				}
				if (month1 == month2) {
					count = "1";
				}
				parameters.put("year2", obj.strConv(year2));
				if (count.equals("1")) {
					month_value_display = obj.month_val(obj.setValue("pmonth", request)) + " "
							+ obj.setValue("pyear", request);
				} else {
					month_value_display = obj.month_val(obj.setValue("pmonth", request)) + " "
							+ obj.setValue("pyear", request);
					month_value_display += " To " + obj.month_val(obj.setValue("pmonth1", request)) + " " + year2;
					// month_value_display+=" To "+obj.month_val(obj.setValue("pmonth1", request))+"
					// "+obj.setValue("pyear", request); Sheron
				}
				if (Office_id.equalsIgnoreCase("5000"))
					Office_id = obj.setValue("office_id", request);
				Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				obj.dis(Office_id);
				parameters.put("office_id", Office_id);
				parameters.put("days", Integer.parseInt(days));
				parameters.put("mvalue", month_value_display);
				parameters.put("count", count);
				parameters.put("office_name", Office_name);

				String imagespath = getServletContext().getRealPath("/images/") + File.separator;
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION.jasper");
				ctxpath = path.substring(0, path.lastIndexOf("PMS_DCB_DMD_COLLECTION.jasper"));
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"PMS_DCB_DMD_COLLECTION.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("FAS_DCB_DMD_BEN")) {
				parameters.put("year1", obj.setValue("pyear", request));
				parameters.put("month1", obj.setValue("pmonth", request));
				parameters.put("month2", obj.setValue("pmonth1", request));
				parameters.put("ben_sno", obj.setValue("bensno", request));
				month_value_display = obj.month_val(obj.setValue("pmonth", request)) + " "
						+ obj.setValue("pyear", request);
				month_value_display += " To " + obj.month_val(obj.setValue("pmonth1", request)) + " "
						+ obj.setValue("pyear", request);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", month_value_display);
				parameters.put("office_name", Office_name);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_ben.jasper");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"PMS_DCB_DMD_COLLECTION_BEN_WISE.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("meter_installed_status_details")) {
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/new_meter_report.jasper");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"meter_installed_status_details.pdf\"");
				outuputStream2 = response.getOutputStream();
			}

			else if (command.trim().equalsIgnoreCase("Quan")) {
				System.out.println("inside");
				int pmonth = Integer.parseInt(obj.setValue("pmonth", request));
				System.out.println("pmonth is" + pmonth);
				int year1 = Integer.parseInt(obj.setValue("pyear", request));
				System.out.println("year1 is" + year1);
				String month_label = obj.month_val(pmonth);
				System.out.println("month_label is" + month_label);
				int year2 = (year1 + 1);
				System.out.println("year2 is" + year2);
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				System.out.println("ftype is" + ftype);

				parameters.put("year1", year1);
				parameters.put("year2", year2);
				parameters.put("divby", ftype);
				parameters.put("pmonth", pmonth);
				parameters.put("month_label", month_label);

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Quan.jasper");
				// response.setHeader("Content-Disposition","attachment; filename=\"Quantity and
				// Demand.pdf\"");
				outuputStream2 = response.getOutputStream();

			}
			// New PQ and Demand
			else if (command.trim().equalsIgnoreCase("PQandDmd")) {

				int Lmonth = Integer.parseInt(obj.setValue("Lmonth", request));
				int Lyear = Integer.parseInt(obj.setValue("Lyear", request));
				String month_label = obj.month_val(Lmonth);
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				int year1 = Integer.parseInt(obj.setValue("pyear", request));

				System.out.println(year1);
				System.out.println(month_label);
				int year2 = (year1 + 1);

				parameters.put("year1", year1);
				parameters.put("year2", year2);
				parameters.put("ftype", ftype);
				// parameters.put("Lyear", Lyear);
				// parameters.put("month_label", month_label);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Bentype_pqanddmd.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"PQ and Demand.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}
			// New Report
			else if (command.trim().equalsIgnoreCase("PQandDmdmonth")) {

				int Lmonth = Integer.parseInt(obj.setValue("Lmonth", request));
				int Lyear = Integer.parseInt(obj.setValue("Lyear", request));
				String month_label = obj.month_val(Lmonth);
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				int year1 = Integer.parseInt(obj.setValue("pyear", request));

				System.out.println(year1);
				System.out.println(month_label);
				int year2 = (year1 + 1);

				parameters.put("year1", year1);
				parameters.put("year2", year2);
				parameters.put("ftype", ftype);
				// parameters.put("Lyear", Lyear);
				// parameters.put("month_label", month_label);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Bentype_pqanddmd_month.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"PQ and Demand.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}

			// New Demand and collection

			else if (command.trim().equalsIgnoreCase("DmDandcoll")) {

				int Lmonth = Integer.parseInt(obj.setValue("Lmonth", request));
				int Lyear = Integer.parseInt(obj.setValue("Lyear", request));
				String month_label = obj.month_val(Lmonth);
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				int year1 = Integer.parseInt(obj.setValue("pyear", request));

				System.out.println(year1);
				System.out.println(month_label);
				int year2 = (year1 + 1);

				parameters.put("year1", year1);
				parameters.put("year2", year2);
				parameters.put("ftype", ftype);
				parameters.put("Lyear", Lyear);
				parameters.put("month_label", month_label);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DmDandcoll.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Demand and Collection.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("tariff_ben_count")) {

				int pyear = Integer.parseInt(obj.setValue("pyear", request));
				int Lmonth = Integer.parseInt(obj.setValue("month", request));
				String month_label = obj.month_val(Lmonth);
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);

				// System.out.println(year1);
				// System.out.println(month_label);
				// int year2= (year1+1);

				parameters.put("year", pyear);
				parameters.put("month", Lmonth);
				parameters.put("ftype", ftype);
				// parameters.put("Lyear", Lyear);
				parameters.put("month_label", month_label);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_count_tariff.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Tariff Ben count.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("Ben_count_traiff_detail")) {

				int pyear = Integer.parseInt(obj.setValue("pyear", request));
				int pmonth = Integer.parseInt(obj.setValue("pmonth", request));
				// String month_label=obj.month_val(Lmonth);
				String Tariff = obj.setValue("Tariff", request);
				// Long Tariff=Long.parseLong(obj.setValue("Tariff", request));
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);

				// System.out.println(year1);
				// System.out.println(month_label);
				// int year2= (year1+1);

				parameters.put("year", pyear);
				parameters.put("month", pmonth);
				parameters.put("Tariff", Tariff);
				// parameters.put("Lyear", Lyear);
				// parameters.put("month_label", month_label);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_count_tariff_detail.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Tariff Ben Detail.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}

			// new for comm data
			else if (command.trim().equalsIgnoreCase("DmDandcoll_new")) {

				int Lmonth = Integer.parseInt(obj.setValue("Lmonth", request));
				int Lyear = Integer.parseInt(obj.setValue("Lyear", request));
				String month_label = obj.month_val(Lmonth);
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				int year1 = Integer.parseInt(obj.setValue("pyear", request));

				System.out.println(year1);
				System.out.println(month_label);
				int year2 = (year1 + 1);

				parameters.put("year1", year1);
				parameters.put("year2", year2);
				parameters.put("ftype", ftype);
				parameters.put("Lyear", Lyear);
				parameters.put("month_label", month_label);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pqDmDandcoll_fin.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Demand and Collection.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}
			// new comm month
			else if (command.trim().equalsIgnoreCase("AllDandcoll_mon")) {

				int month_pre = Integer.parseInt(obj.setValue("month", request));
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				int year1 = Integer.parseInt(obj.setValue("pyear", request));

				System.out.println(year1);

				parameters.put("year1", year1);
				parameters.put("Month", month_pre);
				parameters.put("ftype", ftype);

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pqDmDandcoll_month.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Demand and Collection.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("Ben_count")) {

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_count.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Beneficiary Count.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}

			// New Demand and collection

			else if (command.trim().equalsIgnoreCase("AllDandcoll")) {

				int Lmonth = Integer.parseInt(obj.setValue("Lmonth", request));
				int Lyear = Integer.parseInt(obj.setValue("Lyear", request));
				String month_label = obj.month_val(Lmonth);
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				int year1 = Integer.parseInt(obj.setValue("pyear", request));

				System.out.println(year1);
				System.out.println(month_label);
				System.out.println("ftype is" + ftype);
				int year2 = (year1 + 1);

				parameters.put("year1", year1);
				parameters.put("year2", year2);
				parameters.put("ftype", ftype);
				parameters.put("Lyear", Lyear);
				parameters.put("month_label", month_label);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Allben_Dandcoll.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Demand and Collection.pdf\"");
				outuputStream2 = response.getOutputStream();

			}

			else if (command.trim().equalsIgnoreCase("CopDandcoll")) {

				int Lmonth = Integer.parseInt(obj.setValue("Lmonth", request));
				int Lyear = Integer.parseInt(obj.setValue("Lyear", request));
				String month_label = obj.month_val(Lmonth);
				int ftype = Integer.parseInt(obj.setValue("ftype", request));
				int year1 = Integer.parseInt(obj.setValue("pyear", request));

				System.out.println(year1);
				System.out.println(month_label);
				System.out.println("ftype is" + ftype);
				int year2 = (year1 + 1);

				parameters.put("year1", year1);
				parameters.put("year2", year2);
				parameters.put("ftype", ftype);
				parameters.put("Lyear", Lyear);
				parameters.put("month_label", month_label);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/CopDandcoll.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Demand and Collection.pdf\"");
				outuputStream2 = response.getOutputStream();
				// jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}

			// New Code
			else if (command.trim().equalsIgnoreCase("Waterdetail")) {

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int dis = Integer.parseInt(obj.setValue("dis", request));
				int divby = Integer.parseInt(obj.setValue("divby", request));
				System.out.println(divby);
				if (divby == 1) {
					divby = 100000;
				} else
					divby = 1;
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				parameters.put("year", yearnew);
				parameters.put("divby", divby);
				parameters.put("month", monthnew);
				parameters.put("dic", dis);

				StringBuffer dynamic_query = new StringBuffer();
				dynamic_query.append(
						"SELECT BEN_TYPE_DESC, DISTRICT_CODE, DISTRICT_NAME, BENEFICIARY_NAME, SUM(Due) FROM (SELECT BEN_TYPE_DESC, DISTRICT_CODE, DISTRICT_NAME,BENEFICIARY_NAME, ");
				dynamic_query.append(
						" (BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+DMD_FOR_MTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES)) / "
								+ divby + " AS Due ");
				dynamic_query.append(" FROM PMS_DCB_LEDGER_ACTUAL WHERE MONTH  =" + monthnew + " AND YEAR  =" + yearnew
						+ " AND DISTRICT_CODE=" + dis
						+ " and BEN_TYPE_ID_SUB IN (1,2,3,4,6)  ) GROUP BY BEN_TYPE_DESC, ");
				dynamic_query.append(
						" DISTRICT_CODE, DISTRICT_NAME, BENEFICIARY_NAME order by BEN_TYPE_DESC,BENEFICIARY_NAME ");

				parameters.put("query", dynamic_query.toString());
				System.out.println(dynamic_query.toString());

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/waterdetail.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DISTRICT WATER CHARGES DUE.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			} else if (command.trim().equalsIgnoreCase("Waterdetail1")) {

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int dis = Integer.parseInt(obj.setValue("dis", request));
				int divby = Integer.parseInt(obj.setValue("divby", request));
				System.out.println(divby);
				if (divby == 1) {
					divby = 100000;
				} else
					divby = 1;
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				parameters.put("year", yearnew);
				parameters.put("divby", divby);
				parameters.put("month", monthnew);
				parameters.put("dic", dis);

				StringBuffer dynamic_query = new StringBuffer();
				dynamic_query.append(
						"SELECT BEN_TYPE_DESC, DISTRICT_CODE, DISTRICT_NAME, BENEFICIARY_NAME, SUM(Due) FROM (SELECT BEN_TYPE_DESC, DISTRICT_CODE, DISTRICT_NAME,BENEFICIARY_NAME, ");
				dynamic_query.append(
						" (BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES)) / "
								+ divby + " AS Due ");
				dynamic_query.append(" FROM PMS_DCB_LEDGER_ACTUAL WHERE MONTH  =" + monthnew + " AND YEAR  =" + yearnew
						+ " AND DISTRICT_CODE=" + dis
						+ " and BEN_TYPE_ID_SUB IN (1,2,3,4,6)  ) GROUP BY BEN_TYPE_DESC, ");
				dynamic_query.append(
						" DISTRICT_CODE, DISTRICT_NAME, BENEFICIARY_NAME order by BEN_TYPE_DESC,BENEFICIARY_NAME ");

				parameters.put("query", dynamic_query.toString());
				System.out.println(dynamic_query.toString());

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/waterdetail.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DISTRICT WATER CHARGES DUE.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("Watercharge")) {

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));

				int repin = Integer.parseInt(obj.setValue("repin", request));
				System.out.println(repin);
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				parameters.put("year", yearnew);
				parameters.put("divby", repin);
				parameters.put("month", monthnew);
				parameters.put("rep", rep);

				StringBuffer dynamic_query = new StringBuffer();
				dynamic_query.append(
						"select ob.district_code,ob.DISTRICT_NAME,coalesce(cor.cor,NULL,0,cor.cor)AS CORP,coalesce(mun.mun,NULL,0,mun.mun)AS MUN,coalesce(tp.tp,NULL,0,tp.tp)as TP,coalesce(vi.vi,NULL,0,vi.vi)as VP ");
				dynamic_query.append(
						" FROM ((SELECT distinct DISTRICT_CODE, DISTRICT_NAME from PMS_DCB_LEDGER_ACTUAL where month="
								+ monthnew + " and year=" + yearnew
								+ " )ob  LEFT OUTER JOIN (SELECT sum(BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+DMD_FOR_MTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES)) / "
								+ repin + " as cor,");
				dynamic_query.append(" DISTRICT_CODE from PMS_DCB_LEDGER_ACTUAL where month=" + monthnew + " and year="
						+ yearnew + " and BEN_TYPE_ID_SUB IN (1) ");
				dynamic_query.append(
						" GROUP BY DISTRICT_CODE )cor ON cor.DISTRICT_CODE       =ob.DISTRICT_CODE LEFT OUTER JOIN (SELECT sum(BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+DMD_FOR_MTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES) )/ "
								+ repin + " as mun,DISTRICT_CODE from PMS_DCB_LEDGER_ACTUAL where month=" + monthnew
								+ " and year=" + yearnew + " and BEN_TYPE_ID_SUB IN (2,3) ");
				dynamic_query.append(
						" GROUP BY DISTRICT_CODE )mun ON mun.DISTRICT_CODE       =ob.DISTRICT_CODE LEFT OUTER JOIN (SELECT sum(BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+DMD_FOR_MTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES) )/ "
								+ repin + " as tp,");
				dynamic_query.append(" DISTRICT_CODE from PMS_DCB_LEDGER_ACTUAL where month=" + monthnew + " and year="
						+ yearnew + " and BEN_TYPE_ID_SUB IN (4) GROUP BY DISTRICT_CODE )tp");
				dynamic_query.append(
						" ON tp.DISTRICT_CODE       =ob.DISTRICT_CODE LEFT OUTER JOIN  (SELECT sum(BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+DMD_FOR_MTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES) )/ "
								+ repin + " as vi, DISTRICT_CODE from PMS_DCB_LEDGER_ACTUAL where month=" + monthnew
								+ " and year=" + yearnew + " and BEN_TYPE_ID_SUB IN (6) ");
				dynamic_query.append(
						" GROUP BY DISTRICT_CODE )vi ON vi.DISTRICT_CODE       =ob.DISTRICT_CODE ) order by DISTRICT_NAME");

				parameters.put("query", dynamic_query.toString());
				System.out.println(dynamic_query.toString());

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Diswisewater.jasper");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"DISTRICTWISE WATER CHARGES DUE.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			// New DCB Data
			else if (command.trim().equalsIgnoreCase("Ldata11")) {

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));

				ctxpath = request.getRequestURL().toString();

				parameters.put("year", yearnew);
				parameters.put("month", monthnew);
				
				
				
				
				
//				Sheron
				
				
				

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/Ledger_data_report_29082022.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"Ledger_data_report_29082022.pdf\"");

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Dcb_data.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"DCB DATA.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			// Ledger Dat report

			else if (command.trim().equalsIgnoreCase("Watercharge3"))

			// else if (command.trim().equalsIgnoreCase("Ledger_Data_Report") )
			{

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));

				ctxpath = request.getRequestURL().toString();

				parameters.put("year", yearnew);
				parameters.put("month", monthnew);
				
				
				
//				Sheron DCB DATA EXCEL
				
				 qry= 		  " select "
					 		+ " coalesce(OFFICE_ID,0) as OFFICE_ID,"
					 		+ " coalesce(beneficiary_sno,0) as beneficiary_sno,"
					 		+ " coalesce(SCH_NO,0) as SCH_NO,"
					 		+ " coalesce(BENEFICIARY_NAME,NULL,'0',BENEFICIARY_NAME) AS  BENEFICIARY_NAME,"
					 		+ " coalesce(EMAIL_ID,NULL,'0',EMAIL_ID) AS  EMAIL_ID,"
					 		+ " coalesce(APPLICATION_NO,NULL,'0',APPLICATION_NO) AS  APPLICATION_NO,"
					 		+ " bill_month,bill_year,"
					 		+ " to_char (BILLING_DT, 'dd-MM-yyyy') AS  BILLING_DT,"
					 		+ " coalesce(wc_outstand,0) as wc_outstand,"
					 		+ " coalesce(wc_collection_du,0) as wc_collection_du,"
					 		+ " coalesce(wc_demand_of_this_bill,0) as wc_demand_of_this_bill,"
					 		+ " coalesce(other_char,0) as other_char,"
					 		+ " coalesce(mc_outstanding_due,0) as mc_outstanding_due,"
					 		+ " coalesce(mc_collection_durin,0) as mc_collection_durin,"
					 		+ " coalesce(int_outstanding_due,0) as int_outstanding_due,"
					 		+ " coalesce(int_collection_d,0) as int_collection_d,"
					 		+ " coalesce(int_demand,0) as int_demand,"
					 		+ " coalesce(other_int_char,0) as other_int_char,"
					 		+ " coalesce(sch_name,'') as sch_name"
					 		+ " "
					 		+ " from"
					 		+ " ("
					 		+ " select a.OFFICE_ID,a.beneficiary_sno,c.SCH_NO,m.METRE_SNO,k.EMAIL_ID,k.APPLICATION_NO,b.bill_month,b.bill_year,b.BILLING_DT,c.wc_outstand"
					 		+ " ,c.wc_collection_du,c.wc_demand_of_this_bill,(b.Add_char - b.minus_char) as other_char,c.mc_outstanding_due,c.mc_collection_durin,"
					 		+ " c.int_outstanding_due,c.int_collection_d,f.int_demand,a.BENEFICIARY_NAME,"
					 		+ " (b.ADD_CHARGES_INT - b.MINUS_CHARGES_INT) as other_int_char,g.sch_name"
					 		+ " "
					 		+ " from "
					 		+ " "
					 		+ " ("
					 		+ " select beneficiary_sno,BENEFICIARY_NAME,OFFICE_ID,beneficiary_type_id from pms_dcb_mst_beneficiary"
					 		+ " where status='L' "
					 		+ " and beneficiary_type_id_sub not in (1,2,3,4,5,6) and beneficiary_sno not in (9	,"
					 		+ " 17	,"
					 		+ " 19	,"
					 		+ " 729	,"
					 		+ " 1616	,"
					 		+ " 1910	,"
					 		+ " 1912	,"
					 		+ " 2054	,"
					 		+ " 2055	,"
					 		+ " 2056	,"
					 		+ " 2059	,"
					 		+ " 4619	,"
					 		+ " 7647	)"
					 		+ " "
					 		+ " )a"
					 		+ " left outer join"
					 		+ " "
					 		+ " (select bill_sno,BILLING_DT,(ADD_CHARGES_WC + ADD_CHARGES_MAINT ) as Add_char, ADD_CHARGES_INT  ,BILL_MONTH,bill_year,DMD_INT_FOR_MTH_WC,"
					 		+ " (MINUS_CHARGES_WC + MINUS_CHARGES_MAINT  ) as minus_char,MINUS_CHARGES_INT,beneficiary_sno from pms_dcb_trn_bill_dmd where bill_month="+monthnew+"::numeric and "
					 		+ " bill_year="+yearnew+"::numeric and beneficiary_sno in (select beneficiary_sno from pms_dcb_mst_beneficiary where status='L'"
					 		+ " and beneficiary_type_id_sub  not in (1,2,3,4,5,6)) and  beneficiary_sno not in (9	,"
					 		+ " 17	,"
					 		+ " 19	,"
					 		+ " 729	,"
					 		+ " 1910	,"
					 		+ " 1912	,"
					 		+ " 2054	,"
					 		+ " 2055	,"
					 		+ " 2056	,"
					 		+ " 2059	,"
					 		+ " 4619	,"
					 		+ " 7647	)"
					 		+ " )b"
					 		+ " on a.beneficiary_sno=b.beneficiary_sno"
					 		+ " left outer join"
					 		+ " ("
					 		+ " select BILL_SNO ,SCH_NO, ob_cur_yr_wc as wc_outstand,beneficiary_sno,"
					 		+ " coln_cur_yr_wc as wc_collection_du,"
					 		+ " wc_mth_total as wc_demand_of_this_bill	,"
					 		+ " ob_maint_charges as mc_outstanding_due,"
					 		+ " coln_maint as mc_collection_durin,"
					 		+ " ob_int_amt_wc as int_outstanding_due,"
					 		+ " COLN_INT_WC as int_collection_d"
					 		//+ " --cb_int_amt_wc-ob_int_amt_wc  as int_de"
					 		+ " from pms_dcb_trn_bill_dmd_sch "
					 		+ " )c"
					 		+ " on b.bill_sno=c.bill_sno and b.beneficiary_sno=c.beneficiary_sno"
					 		+ " "
					 		+ " left outer join"
					 		+ " ("
					 		+ " SELECT BENEFICIARY_SNO,DMD_INT_FOR_MTH_WC as int_demand,SCH_SNO"
					 		+ "      from PMS_DCB_TRN_CB_MONTHLY where    month="+monthnew+"::numeric and fin_year="+yearnew+"::numeric"
					 		+ " )f"
					 		+ " on  b.beneficiary_sno=f.beneficiary_sno and f.SCH_SNO=c.SCH_NO"
					 		+ " "
					 		+ " left outer join"
					 		+ " ("
					 		+ " select METRE_SNO,BENEFICIARY_SNO,SCHEME_SNO,ROW_NUMBER() OVER (PARTITION BY SCHEME_SNO,BENEFICIARY_SNO  ORDER BY BENEFICIARY_SNO) num"
					 		+ "    FROM PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L'and beneficiary_sno in (select beneficiary_sno from pms_dcb_mst_beneficiary where status='L' "
					 		+ " and beneficiary_type_id_sub not in (1,2,3,4,5,6) 	)    "
					 		+ " )m"
					 		+ " on m.BENEFICIARY_SNO=a.beneficiary_sno and m.SCHEME_SNO=c.SCH_NO and m.num=1"
					 		+ " "
					 		+ " left outer join"
					 		+ " ("
					 		+ " select APPLICATION_NO,EMAIL_ID,OFFICE_ID,BENEFICIARY_ID from PMS_DCB_MST_BEN_DETAIL "
					 		+ " )k"
					 		+ " on k.BENEFICIARY_ID=a.beneficiary_sno "
					 		+ " left outer join"
					 		+ " ("
					 		+ " select SCH_SNO,SCH_SHORT_DESC as sch_name from PMS_SCH_MASTER"
					 		+ " )g"
					 		+ " on m.SCHEME_SNO=g.SCH_SNO"
					 		+ " left outer join"
					 		+ " ("
					 		+ " select ben_type_id,"
					 		+ " BEN_TYPE_DESC from PMS_DCB_BEN_TYPE"
					 		+ " )d"
					 		+ " on a.beneficiary_type_id=d.ben_type_id"
					 		+ " order by a.beneficiary_sno"
					 		+ "  )k"
					 		+ " where SCH_NO IS NOT NULL"
//					 		+ " and EMAIL_ID!='N'"
					 		+ " order by OFFICE_ID,BENEFICIARY_NAME,sch_name";
				
				 				
				 

					System.out.println(qry);

					// Create a PreparedStatement and execute the query
					PreparedStatement ps = con.prepareStatement(qry);

					ResultSet rs = ps.executeQuery();

					generateExcelFile(rs, response, "DCB_DATA","DCB_DATA" );
				
				
			
				

//			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ledger_data_report_29082022.jasper");	
				// reportFile = new
				// File(getServletContext().getRealPath("/WEB-INF/PDF/Dcb_data.jasper"));

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Dcb_data.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"Ledger_data_report_29082022.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				// if (rep==1)
				// reportPdf(response, jasperPrint2, outuputStream2);
				// else if(rep==2)
				// excelshow(response, jasperPrint2, outuputStream2);

			}

			// over

			// else if (command.trim().equalsIgnoreCase("Watercharge4") )
			else if (command.trim().equalsIgnoreCase("Ldata")) {

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));
				int rep1 = Integer.parseInt(obj.setValue("reporttype", request));

				int office_id = Integer.parseInt(obj.setValue("report_id", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);
				System.out.println("rep is  " + rep);

				System.out.println("office_id is  " + office_id);

				System.out.println("rep1   is  " + rep1);

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);

				// parameters.put("Flag", "N"); // ok DCB_SCHEMEWISE_DETAIL_060920221.jrxml

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/Ledger_Data_Report_30082022.jasper");
				path = getServletContext()
						.getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_SCHEMEWISE_DETAIL_060920221.jasper");

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_STATEMENT_01092022.jasper");

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/Fas_credit_advise.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);

				// Added Excel download option by Sheron [2/1/2024]

				else if (option.equalsIgnoreCase("2")) {
					String rptqry = "SELECT ROW_NUMBER() OVER (ORDER BY b.beneficiary_type_id_sub,c.district_name,d.block_name,e.panch_name,b.beneficiary_name)::int AS SL_NO, A.BENEFICIARY_SNO as BENEFICIARY_SNO, A.Office_id, G.OFFICE_NAME as OFFICE_NAME,f.ben_type_desc AS BENEFICIARY_TYPE, "
							+ " case " + " when(BILL_MONTH='1') then 'January' "
							+ " when(BILL_MONTH='2') then 'February' " + " when(BILL_MONTH='3') then 'March' "
							+ " when(BILL_MONTH='4') then 'April' " + " when(BILL_MONTH='5') then 'May' "
							+ " when(BILL_MONTH='6') then 'June' " + " when(BILL_MONTH='7') then 'July' "
							+ " when(BILL_MONTH='8') then 'August' " + " when(BILL_MONTH='9') then 'September' "
							+ " when(BILL_MONTH='10') then 'October' " + " when(BILL_MONTH='11') then 'November' "
							+ " when(BILL_MONTH='12') then 'December' " + " else 'BILL_MONTH' end as MONTH, "
							+ " A.BILL_MONTH as BILL_MONTH, " + " A.BILL_YEAR as BILL_YEAR, "
							+ " B.DISTRICT_CODE||' ' AS DISTRICT_CODE,c.district_name||' ' AS DISTRICT_NAME, "
							+ " D.BLOCK_SNO||' ' AS BLOCK_SNO,D.BLOCK_NAME||' ' AS BLOCK_NAME,b.village_panchayat_sno||' ' AS village_panchayat_sno, "
							+ " E.PANCH_NAME||' ' AS PANCHAYAT_NAME, " + " A.BENEFICIARY_SNO as BENEFICIARY_SNO, "
							+ " b.beneficiary_name BENEFICIARY_NAME, " +

							" COALESCE(A.OUTSTANDING_DUE_WC,0) AS OUTSTANDING_DUE_WC, "
							+ " COALESCE(A.COLLECTION_WC,0) AS COLLECTION_WC, "
							+ " COALESCE(A.DEMAND_WC,0) AS DEMAND_WC, "
							+ " COALESCE(A.OTHER_CHARGES_WC,0) AS OTHER_CHARGES_WC, "
							+ " COALESCE(A.BALANCE_WC,0) AS BALANCE_WC, " +

							" COALESCE(A.OUTSTANDING_DUE_INT,0) AS OUTSTANDING_DUE_INT, "
							+ " COALESCE(A.COLLECTION_INT,0) AS COLLECTION_INT, "
							+ " COALESCE(A.DEMAND_INT,0) AS DEMAND_INT, "
							+ " COALESCE(A.OTHER_CHARGES_INT,0) AS OTHER_CHARGES_INT, "
							+ " COALESCE(A.BALANCE_INT,0) AS BALANCE_INT, " +

							" COALESCE(A.OUTSTANDING_DUE,0) AS OUTSTANDING_DUE, "
							+ " COALESCE(A.COLLECTION,0) AS COLLECTION, " + " COALESCE(A.DEMAND,0) AS DEMAND, "
							+ " COALESCE(A.OTHER_CHARGES,0) AS OTHER_CHARGES, " + " COALESCE(A.BALANCE,0) AS BALANCE " +

							" from FULL_VIEW_NEW a  "
							+ " LEFT JOIN pms_dcb_mst_beneficiary b ON b.beneficiary_sno = a.beneficiary_sno "
							+ " LEFT JOIN COM_MST_DISTRICTS C ON c.district_code = b.district_code "
							+ " LEFT JOIN COM_MST_BLOCKS D ON d.block_sno = B.BLOCK_SNO "
							+ " LEFT JOIN com_mst_panchayats E ON e.PANCH_SNO=B.VILLAGE_PANCHAYAT_SNO "
							+ " LEFT JOIN COM_MST_OFFICES G ON G.OFFICE_ID = A.OFFICE_ID "
							+ " LEFT JOIN PMS_DCB_BEN_TYPE F ON F.BEN_TYPE_ID = b.beneficiary_type_id_sub "
							+ " where a.office_id=?::int and a.bill_month=?::int and a.bill_year=?::int";

//					PreparedStatement ps = con.prepareStatement(rptqry);
					
					PreparedStatement ps = con.prepareStatement(rptqry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

					
					ps.setInt(1, office_id);
					ps.setInt(2, monthnew);
					ps.setInt(3, yearnew);

					ResultSet rs = ps.executeQuery();
					
					String monthInWords = MonthConverter.monthNumberToName(monthnew);
					String reportTitle = "Water Charges Report for the month of "+monthInWords+" -"+yearnew;

					generateExcelFile(rs, response, "WaterChargesReport", reportTitle);
				}

			}

			else if (command.trim().equalsIgnoreCase("dashboard")) {

				flag = 1;
				String dmd_cond;
				String coln_cond;
				int coln_month, coln_year;
				int year1 = Integer.parseInt(obj.setValue("year", request));

				int month1 = Integer.parseInt(obj.setValue("month", request));

				if (month1 == 12) {
					coln_month = 1;
					coln_year = year1 + 1;
				} else {
					coln_month = month1 + 1;
					coln_year = year1;
				}

				if (month1 > 3 && month1 !=4) {
				    dmd_cond = "where ((dmd1.bill_year = " + year1 + " and dmd1.bill_month >= 3) "
				             + "OR (dmd1.bill_year = " + year1 + " and dmd1.bill_month <= " + month1 + "))";
				    
				    coln_cond = "where ((dmd1.bill_year = " + year1 + " and dmd1.bill_month >= 4) "
				              + "OR (dmd1.bill_year = " + year1 + " and dmd1.bill_month <= " + month1 + "))";
				    
				    System.out.println("dmd_cond :" + dmd_cond);
				    System.out.println("coln_cond :" + coln_cond);
				    
				} else if (month1 == 3) {
				    dmd_cond = "where (dmd1.bill_year = " + year1 + " and dmd1.bill_month = 3)";
				    coln_cond = "where (dmd1.bill_year = " + year1 + " and dmd1.bill_month = 4)";
				    
				    System.out.println("dmd_cond :" + dmd_cond);
				    System.out.println("coln_cond :" + coln_cond);
				    
				} else if (month1 == 4){
					
//					  dmd_cond = "where ((dmd1.bill_year = " + year1 + " and dmd1.bill_month >= 3) "
//					             + "OR (dmd1.bill_year = " + year1 + " and dmd1.bill_month <= " + month1 + "))";
					  
					  dmd_cond = "where (dmd1.bill_year = "+year1+" and  dmd1.bill_month between 3 and "+month1+" )";
					  coln_cond = "where ((dmd1.bill_year = " + year1 + " and dmd1.bill_month = 4)) ";
					              	
				}
				
				else 
				{
				    dmd_cond = "where ((dmd1.bill_year = " + year1 + " and dmd1.bill_month >= 3) "
				             + "OR (dmd1.bill_year = " + (year1 + 1) + " and dmd1.bill_month <= " + month1 + "))";
				    
				    coln_cond = "where ((dmd1.bill_year = " + year1 + " and dmd1.bill_month >= 4) "
				              + "OR (dmd1.bill_year = " + (year1 + 1) + " and dmd1.bill_month <= " + month1 + "))";
				    
				    System.out.println("dmd_cond :" + dmd_cond);
				    System.out.println("coln_cond :" + coln_cond);
				}


				
				String sqlQuery = " select * from (select " + " REG.REGID AS REGION_CODE," + " REG.REG AS REGION_NAME,"
						+ " REG.CIRID AS CIRCLE_CODE," + " REG.CIR AS CIRCLE_NAME,"
						+ " cur_mth_cb.office_id AS DIVISION_CODE, " + " REG.DIV AS DIVISION_NAME,"
						+ " cur_mth_cb.beneficiary_sno AS BENEFICIARY_CODE," + " BEN.BENEFICIARY_NAME,"
						+ " BEN.BENEFICIARY_TYPE_ID_SUB AS BENEFICIARY_TYPE_ID,"
						+ " TYP.BEN_TYPE_DESC AS BENEFICIARY_TYPE ," + " BEN.DISTRICT_CODE," + " DIS.DISTRICT_NAME,"
						+ " BEN.BLOCK_SNO AS BLOCK_CODE," + " BLK.BLOCK_NAME,"
						+ " BEN.VILLAGE_PANCHAYAT_SNO AS PANCHAYAT_CODE," + " PCH.PANCH_NAME," + " "
						+ " COALESCE(CUR_MTH_CB.CB_WC,0) - COALESCE(CURRENT_YEAR_DMD_WC,0) + COALESCE(CURRENT_YEAR_COLN.COLN_WC,0) AS WC_ARREAR_01_04_2022,"
						+ " CURRENT_YEAR_DMD_WC AS WC_CURRENT_YEAR_DEMAND,"
						+ " CUR_MTH_DMD.CURRENT_MONTH_DMD_WC AS WC_CURRENT_MONTH_DEMAND,"
						+ "  COALESCE(CURRENT_YEAR_COLN.COLN_WC,0) + COALESCE(CURRENT_MTH_COLN_WC.AMOUNT ,0) AS WC_CURRENT_YEAR_COLLECTION,"
						+ "	COALESCE(CURRENT_MTH_COLN_WC.AMOUNT,0) AS WC_CURRENT_MONTH_COLLECTION," + "	"
						+ " COALESCE(CUR_MTH_CB.CB_INT,0) - COALESCE(CURRENT_YEAR_DMD_INT,0) + COALESCE(CURRENT_YEAR_COLN.COLN_INT,0) AS OB_INT_4,"
						+ "	CURRENT_YEAR_DMD_INT AS INTEREST_CURRENT_YEAR_DEMAND, "
						+ "	CUR_MTH_DMD.CURRENT_MONTH_DMD_INT AS INTEREST_CURRENT_MONTH_DEMAND , "
						+ "	COALESCE(CURRENT_YEAR_COLN.COLN_INT,0) + COALESCE(CURRENT_MTH_COLN_INT.AMOUNT,0) AS INT_CURRENT_YEAR_COLLECTION,"
						+ " COALESCE(CURRENT_MTH_COLN_INT.AMOUNT,0) AS INT_CURRENT_MONTH_COLLECTION, "
						+ " LAST_COL.MONTH AS LAST_COL_MONTH, LAST_COL.YEAR AS LAST_COL_YEAR," + " TO_CHAR("
						+ "    TO_DATE (LAST_COL.MONTH::text, 'MM'), 'Month'" + "    ) AS LAST_COLLECTED_MONTH" + " "
						+ " from ("
						+ " select office_id, beneficiary_sno, beneficiary_type_id_sub, sum(cb_wc) as cb_wc, sum(cb_int) as cb_int  from"
						+ "	(select " + " dmd1. office_id," + " ben1.beneficiary_type_id_sub," + " case"
						+ "		when ben1.BENEFICIARY_TYPE_ID_SUB = 1 then ben1.added_to_ben_sno"
						+ "		else ben1.beneficiary_sno END" + " beneficiary_sno," + " "
						+ " (coalesce(ob_yester_yr_wc,0)+coalesce(ob_cur_yr_wc,0)+coalesce(ob_maint_charges,0)) -" + " "
						+ " (coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0)-coalesce(add_charges_wc,0)+coalesce(minus_charges_wc,0)-coalesce(add_charges_maint,0)+coalesce(minus_charges_maint)) +"
						+ " " + " (coalesce(wc_mth_total,0)) as cb_wc," + " " + " (coalesce(ob_int_amt_wc,0)) -" + " "
						+ " (coalesce(coln_int_wc,0)-coalesce(add_charges_int,0)+coalesce(minus_charges_int,0)) +" + " "
						+ " (coalesce(dmd_int_for_mth_wc,0)) as cb_int" + " "
						+ " from pms_dcb_trn_bill_dmd dmd1 left join pms_dcb_mst_beneficiary ben1 "
						+ " on ben1.beneficiary_sno = dmd1.beneficiary_sno" + " where dmd1.bill_year=" + year1
						+ " and dmd1.bill_month =" + month1 + " ) opt1"
						+ " group by office_id, beneficiary_sno, beneficiary_type_id_sub" + " ) as CUR_MTH_CB" + " "
						+ " LEFT JOIN " + " ----CURRENT MONTH DMD----\r\n" + " " + " ("
						+ " select office_id, beneficiary_sno, beneficiary_type_id_sub, sum(dmd_wc) as CURRENT_MONTH_DMD_WC, sum(dmd_int) as CURRENT_MONTH_DMD_INT  from"
						+ "	(select " + " dmd1. office_id," + " ben1.beneficiary_type_id_sub," + " case"
						+ "		when ben1.beneficiary_type_id_sub=1 then ben1.added_to_ben_sno"
						+ "		else ben1.beneficiary_sno END" + " beneficiary_sno," + " "
						+ " coalesce(wc_mth_total) as dmd_wc, " + " coalesce(dmd_int_for_mth_wc) as dmd_int"
						+ " from pms_dcb_trn_bill_dmd dmd1 left join pms_dcb_mst_beneficiary ben1 "
						+ " on ben1.beneficiary_sno = dmd1.beneficiary_sno" + " where dmd1.bill_year=" + year1
						+ " and dmd1.bill_month = " + month1 + " ) opt1"
						+ " group by office_id, beneficiary_sno, beneficiary_type_id_sub" + " ) CUR_MTH_DMD" + " "
						+ " ON " + " CUR_MTH_DMD.OFFICE_ID = CUR_MTH_CB.OFFICE_ID AND "
						+ " CUR_MTH_DMD.BENEFICIARY_SNO = CUR_MTH_CB.BENEFICIARY_SNO " + "----------\r\n"
						+ " LEFT JOIN " + " ("
						+ " select office_id, beneficiary_sno, beneficiary_type_id_sub, sum(dmd_wc) as CURRENT_YEAR_DMD_WC, sum(dmd_int) as CURRENT_YEAR_DMD_INT  from"
						+ "	(select " + " dmd1. office_id," + " ben1.beneficiary_type_id_sub," + " case"
						+ "		when ben1.beneficiary_type_id_sub=1 then ben1.added_to_ben_sno"
						+ "		else ben1.beneficiary_sno END" + " beneficiary_sno," + " "
						+ " coalesce(wc_mth_total) as dmd_wc, " + " coalesce(dmd_int_for_mth_wc) as dmd_int"

						+ " from pms_dcb_trn_bill_dmd dmd1 left join pms_dcb_mst_beneficiary ben1 "
						+ " on ben1.beneficiary_sno = dmd1.beneficiary_sno " + dmd_cond + " ) opt1"
						+ " group by office_id, beneficiary_sno, beneficiary_type_id_sub" + " ) CUR_YR_DMD " + " "
						+ " ON " + " CUR_YR_DMD.OFFICE_ID = CUR_MTH_DMD.OFFICE_ID AND"
						+ "  CUR_YR_DMD.BENEFICIARY_SNO = CUR_MTH_DMD.BENEFICIARY_SNO" + " LEFT JOIN "
						+ "-----IF COLN YEAR IS SAME AS FINANCIAL YEAR\r\n " + " ("
						+ " select office_id, beneficiary_sno, beneficiary_type_id_sub, sum(coln_wc) as coln_wc, sum(coln_int) as coln_int  from"
						+ "	(select " + " dmd1. office_id," + " ben1.beneficiary_type_id_sub," + " case"
						+ "		when ben1.beneficiary_type_id_sub = 1 then ben1.added_to_ben_sno"
						+ "		else ben1.beneficiary_sno END" + " beneficiary_sno,"
						+ " (coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0)-coalesce(add_charges_wc,0)+coalesce(minus_charges_wc,0)-coalesce(add_charges_maint,0)+coalesce(minus_charges_maint)) as coln_wc,"
						+ " (coalesce(coln_int_wc,0)-coalesce(add_charges_int,0)+coalesce(minus_charges_int,0)) as coln_int "
						+ " " + " from pms_dcb_trn_bill_dmd dmd1 left join pms_dcb_mst_beneficiary ben1 "
						+ " on ben1.beneficiary_sno = dmd1.beneficiary_sno " + coln_cond + " ) opt1"
						+ " group by office_id, beneficiary_sno, beneficiary_type_id_sub" + " )  CURRENT_YEAR_COLN"
						+ " ON " + "	CURRENT_YEAR_COLN.OFFICE_ID = CUR_MTH_CB.OFFICE_ID AND "
						+ "	CURRENT_YEAR_COLN.BENEFICIARY_SNO = CUR_MTH_CB.BENEFICIARY_SNO " + " LEFT JOIN " 					                                      
						//----------CURRENT MONTH COLLECTION WC FROM FAS -----------------
						+ " ( "
						+ " select office_id,beneficiary_sno,beneficiary_type_id_sub, sum(amount) as amount from (  select total.office_id,ben.beneficiary_type_id_sub,"
						+ " case when ben.beneficiary_type_id_sub = 1 then  ben.added_to_ben_sno "
						+ "		 else ben.beneficiary_sno end as beneficiary_sno,"
						+ " amount from (select * from (select office_id, beneficiary_sno, sum(amount) as amount from (SELECT pms_dcb_fas_receipt_view.receipt_no,"
						+ "    pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
						+ "    pms_dcb_fas_receipt_view.cashbook_year AS year,"
						+ "    pms_dcb_fas_receipt_view.cashbook_month AS month,"
						+ "    pms_dcb_fas_receipt_view.sub_ledger_type_code,"
						+ "    pms_dcb_fas_receipt_view.sub_ledger_code,"
						+ "    pms_dcb_fas_receipt_view.sch_type_id,"
						+ "    pms_dcb_fas_receipt_view.sch_sno,"
						+ "    pms_dcb_fas_receipt_view.beneficiary_name,"
						+ "    pms_dcb_fas_receipt_view.beneficiary_sno,"
						+ "    pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
						+ "    pms_dcb_fas_receipt_view.amount"
						+ "   FROM pms_dcb_fas_receipt_view"
						+ "  WHERE (pms_dcb_fas_receipt_view.account_head_code = ANY (ARRAY[782401::numeric, 782402::numeric, 782403::numeric, 782404::numeric, 782405::numeric, 782406::numeric, 782407::numeric, 900108::numeric, 900109::numeric])) and cashbook_year="+coln_year+" and cashbook_month="+coln_month+") opt1 group by office_id, beneficiary_sno"
						+ "	) receipt "
						+ "	"
						+ "	union all "
						+ "	"
						+ "	select * from (SELECT opt4.office_id::numeric AS office_id,"
						+ "            opt4.beneficiary_sno,"
						+ "            sum(opt4.amount) as amount"
						+ "           FROM ( SELECT pms_dcb_other_charges.office_id,"
						+ "                    pms_dcb_other_charges.beneficiary_sno,"
						+ "                    pms_dcb_other_charges.cashbook_year AS year,"
						+ "                    pms_dcb_other_charges.cashbook_month AS month,"
						+ "                    sum(COALESCE("
						+ "                        CASE"
						+ "                            WHEN pms_dcb_other_charges.cr_dr_indicator::text = 'CR'::text THEN pms_dcb_other_charges.amount"
						+ "                            ELSE NULL::numeric"
						+ "                        END, 0::numeric)) AS amount"
						+ "                   FROM pms_dcb_other_charges"
						+ "                  WHERE (pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code"
						+ "                           FROM pms_dcb_receipt_account_map"
						+ "                          WHERE pms_dcb_receipt_account_map.collection_type = 7::numeric))"
						+ "                  GROUP BY pms_dcb_other_charges.office_id, pms_dcb_other_charges.beneficiary_sno, pms_dcb_other_charges.cashbook_year, pms_dcb_other_charges.cashbook_month"
						+ "                  ORDER BY pms_dcb_other_charges.office_id, pms_dcb_other_charges.beneficiary_sno) as opt4"
						+ "									where opt4.year="+coln_year+" and opt4.month = "+coln_month+" group by opt4.office_id , opt4.beneficiary_sno) adj "
						+ "									) total left join pms_dcb_mst_beneficiary ben on ben.beneficiary_sno = total.beneficiary_sno"
						+ "								) as opt1	group by office_id,beneficiary_sno,beneficiary_type_id_sub order by office_id, beneficiary_type_id_sub, amount   " 
						+ " ) "
						+ "	 CURRENT_MTH_COLN_WC ON CURRENT_MTH_COLN_WC.OFFICE_ID = CUR_MTH_CB.OFFICE_ID "
						+ "	AND CURRENT_MTH_COLN_WC.BENEFICIARY_SNO = CUR_MTH_CB.BENEFICIARY_SNO "
						+ " LEFT JOIN "
						
						//-------------------CURRENT MONTH COLLECTION INT FROM FAS----------------------------
						+ " ("
						+ " select office_id,beneficiary_sno,beneficiary_type_id_sub, sum(amount) as amount from (  select total.office_id,ben.beneficiary_type_id_sub,"
						+ " case when ben.beneficiary_type_id_sub =1 then  ben.added_to_ben_sno "
						+ "		 else ben.beneficiary_sno end as beneficiary_sno,"
						+ " amount from (select * from (select office_id, beneficiary_sno, sum(amount) as amount from (SELECT pms_dcb_fas_receipt_view.receipt_no,"
						+ "    pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
						+ "    pms_dcb_fas_receipt_view.cashbook_year AS year,"
						+ "    pms_dcb_fas_receipt_view.cashbook_month AS month,"
						+ "    pms_dcb_fas_receipt_view.sub_ledger_type_code,"
						+ "    pms_dcb_fas_receipt_view.sub_ledger_code,"
						+ "    pms_dcb_fas_receipt_view.sch_type_id,"
						+ "    pms_dcb_fas_receipt_view.sch_sno,"
						+ "    pms_dcb_fas_receipt_view.beneficiary_name,"
						+ "    pms_dcb_fas_receipt_view.beneficiary_sno,"
						+ "    pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
						+ "    pms_dcb_fas_receipt_view.amount"
						+ "   FROM pms_dcb_fas_receipt_view"
						+ "  WHERE (pms_dcb_fas_receipt_view.account_head_code = ANY (ARRAY[120601::numeric])) and cashbook_year="+coln_year+"::NUMERIC and cashbook_month="+coln_month+"::NUMERIC) opt1 group by office_id, beneficiary_sno"
						+ "	) receipt "
						+ "	"
						+ "	union all "
						+ "	"
						+ "	select * from (SELECT opt4.office_id::numeric AS office_id,"
						+ "            opt4.beneficiary_sno,"
						+ "            sum(opt4.amount) as amount"
						+ "           FROM ( SELECT pms_dcb_other_charges.office_id,"
						+ "                    pms_dcb_other_charges.beneficiary_sno,"
						+ "                    pms_dcb_other_charges.cashbook_year AS year,"
						+ "                    pms_dcb_other_charges.cashbook_month AS month,"
						+ "                    sum(COALESCE("
						+ "                        CASE"
						+ "                            WHEN pms_dcb_other_charges.cr_dr_indicator::text = 'CR'::text THEN pms_dcb_other_charges.amount"
						+ "                            ELSE NULL::numeric"
						+ "                        END, 0::numeric)) AS amount"
						+ "                   FROM pms_dcb_other_charges"
						+ "                  WHERE (pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code"
						+ "                           FROM pms_dcb_receipt_account_map"
						+ "                          WHERE pms_dcb_receipt_account_map.collection_type = 9::numeric))"
						+ "                  GROUP BY pms_dcb_other_charges.office_id, pms_dcb_other_charges.beneficiary_sno, pms_dcb_other_charges.cashbook_year, pms_dcb_other_charges.cashbook_month"
						+ "                  ORDER BY pms_dcb_other_charges.office_id, pms_dcb_other_charges.beneficiary_sno) as opt4"
						+ "									where opt4.year="+coln_year+"::NUMERIC and opt4.month = "+coln_month+"::numeric group by opt4.office_id , opt4.beneficiary_sno) adj "
						+ "									) total left join pms_dcb_mst_beneficiary ben on ben.beneficiary_sno = total.beneficiary_sno"
						+ "								) as opt1	group by office_id,beneficiary_sno,beneficiary_type_id_sub order by office_id, beneficiary_type_id_sub, amount  "
						+ " ) "
						+ " CURRENT_MTH_COLN_INT ON CURRENT_MTH_COLN_INT.OFFICE_ID = CUR_MTH_CB.OFFICE_ID "
						+ "	AND CURRENT_MTH_COLN_INT.BENEFICIARY_SNO = CUR_MTH_CB.BENEFICIARY_SNO "
						//-----------------------------------------------------------------------------------------------
						+ "	LEFT JOIN LAST_COL_CUR LAST_COL ON LAST_COL.BENEFICIARY_SNO = CUR_MTH_CB.BENEFICIARY_SNO"
						+ "	LEFT JOIN REGION_CIR_DIV_VIEW REG ON REG.DIDID = CUR_MTH_CB.OFFICE_ID"
					    + "	LEFT JOIN PMS_DCB_MST_BENEFICIARY BEN ON BEN.BENEFICIARY_SNO = CUR_MTH_CB.BENEFICIARY_SNO"
						+ "	LEFT JOIN PMS_DCB_BEN_TYPE TYP ON TYP.BEN_TYPE_ID = BEN.BENEFICIARY_TYPE_ID_SUB"
						+ "	LEFT JOIN COM_MST_DISTRICTS DIS ON DIS.DISTRICT_CODE = BEN.DISTRICT_CODE"
						+ "	LEFT JOIN COM_MST_BLOCKS BLK ON BLK.BLOCK_SNO = BEN.BLOCK_SNO"
						+ "	LEFT JOIN COM_MST_PANCHAYATS PCH ON PCH.PANCH_SNO = BEN.VILLAGE_PANCHAYAT_SNO "
						+ "	) AS fin "
						+ " WHERE"
						+ "	wc_arrear_01_04_2022 > 0 "
						+ "	OR wc_current_year_demand > 0 "
						+ "	OR wc_current_month_demand > 0 "
						+ "	OR wc_current_year_collection > 0 "
						+ "	OR wc_current_month_collection > 0 "
						+ "	OR ob_int_4 > 0 "
						+ "	OR int_current_year_collection > 0 "
						+ "	OR int_current_month_collection > 0 "
						+ "	OR interest_current_month_demand > 0 "
						+ "	OR interest_current_year_demand > 0";

				System.out.println(sqlQuery);
				
				
				
				String sqlQuery2 = 
						" select  "
								+ "region_code, "
								+ "region_name, "
								+ "circle_code, "
								+ "circle_name, "
								+ "division_code, "
								+ "division_name, "
								+ "beneficiary_code, "
								+ "beneficiary_name, "
								+ "beneficiary_type_id, "
								+ "beneficiary_type, "
								+ "district_code, "
								+ "district_name, "
								+ "block_code, "
								+ "block_name, "
								+ "panchayat_code, "
								+ "panch_name, "
								+ "wc_arrear_01_04_2022, "
								+ "wc_current_year_demand, "
								+ "wc_current_month_demand, "
								+ "wc_current_year_collection, "
								+ "wc_current_month_collection, "
								+ "ob_int_4, "
								+ "interest_current_year_demand, "
								+ "interest_current_month_demand, "
								+ "int_current_year_collection, "
								+ "int_current_month_collection, "
								+ "last_col_month, "
								+ "last_col_year, "
								+ "case last_col_month "
								+ "	when 1 then 'JANUARY' "
								+ "	when 2 then 'FEBRUARY' "
								+ "	when 3 then 'MARCH' "
								+ "	when 4 then 'APRIL' "
								+ "	when 5 then 'MAY' "
								+ "	when 6 then 'JUNE' "
								+ "	when 7 then 'JULY' "
								+ "	when 8 then 'AUGUST' "
								+ "	when 9 then 'SEPTEMBER' "
								+ "	when 10 then 'OCTOBER' "
								+ "	when 11 then 'NOVEMBER' "
								+ "	when 12 then 'DECEMBER' "
								+ "	end AS "
								+ "last_collected_month "
								+ "from ( "
								+ "WITH cur_mth_cb as  (select dmd1.office_id ,  dmd1.beneficiary_sno, dmd1.beneficiary_type_id_sub , sum(cb_wc) as cb_wc, sum(cb_int) as cb_int from "
								+ "( "
								+ "    SELECT "
								+ "        dmd1.office_id, "
								+ "        CASE "
								+ "            WHEN ben1.beneficiary_type_id_sub = 1 THEN ben1.added_to_ben_sno "
								+ "            ELSE ben1.beneficiary_sno "
								+ "        END AS beneficiary_sno, "
								+ "        ben1.beneficiary_type_id_sub, "
								+ "         "
								+ "            COALESCE(ob_yester_yr_wc, 0) + COALESCE(ob_cur_yr_wc, 0) + COALESCE(ob_maint_charges, 0) "
								+ "            - (COALESCE(coln_cur_yr_wc, 0) + COALESCE(coln_yester_yr_wc, 0) + COALESCE(coln_maint, 0)) "
								+ "            + COALESCE(add_charges_wc, 0) - COALESCE(minus_charges_wc, 0) "
								+ "            + COALESCE(wc_mth_total, 0) "
								+ "         AS cb_wc, "
								+ "     "
								+ "            COALESCE(ob_int_amt_wc, 0) "
								+ "            - (COALESCE(coln_int_wc, 0) + COALESCE(add_charges_int, 0) - COALESCE(minus_charges_int, 0)) "
								+ "            + COALESCE(dmd_int_for_mth_wc, 0) "
								+ "         AS cb_int "
								+ "    FROM "
								+ "        pms_dcb_trn_bill_dmd dmd1 "
								+ "    LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ "    WHERE "
								+ "        dmd1.bill_year = "+year1+" AND dmd1.bill_month =  "+month1
								+ "        ) dmd1  "
								+ "    GROUP BY "
								+ "        dmd1.office_id, dmd1.beneficiary_sno, dmd1.beneficiary_type_id_sub "
								+ "        ), "
								+ "         "
								+ "        cur_mth_dmd AS ( "
								+ "select dmd1.office_id, dmd1.beneficiary_sno, dmd1.beneficiary_type_id_sub, sum(current_month_dmd_wc) current_month_dmd_wc,sum(current_month_dmd_int) current_month_dmd_int  from  "
								+ "( "
								+ "    SELECT "
								+ "        dmd1.office_id, "
								+ "        CASE "
								+ "            WHEN ben1.beneficiary_type_id_sub = 1 THEN ben1.added_to_ben_sno "
								+ "            ELSE ben1.beneficiary_sno "
								+ "        END AS beneficiary_sno, "
								+ "        ben1.beneficiary_type_id_sub, "
								+ "        (COALESCE(wc_mth_total, 0)) AS current_month_dmd_wc, "
								+ "        (COALESCE(dmd_int_for_mth_wc, 0)) AS current_month_dmd_int "
								+ "    FROM "
								+ "        pms_dcb_trn_bill_dmd dmd1 "
								+ "    LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ "    WHERE "
								+ "        dmd1.bill_year = "+year1+" AND dmd1.bill_month = "+month1
								+ "        ) dmd1 "
								+ "    GROUP BY "
								+ "        dmd1.office_id, dmd1.beneficiary_sno, dmd1.beneficiary_type_id_sub "
								+ "), "
								+ " "
								+ "cur_yr_dmd AS ( "
								+ "select dmd1.office_id ,dmd1.beneficiary_sno, dmd1.beneficiary_type_id_sub,sum(dmd1.current_year_dmd_wc) as current_year_dmd_wc,  "
								+ "sum(dmd1.current_year_dmd_int) as current_year_dmd_int from   ( "
								+ "    SELECT "
								+ "        dmd1.office_id, "
								+ "        CASE "
								+ "            WHEN ben1.beneficiary_type_id_sub = 1 THEN ben1.added_to_ben_sno "
								+ "            ELSE ben1.beneficiary_sno "
								+ "        END AS beneficiary_sno, "
								+ "        ben1.beneficiary_type_id_sub, "
								+ "        (COALESCE(wc_mth_total, 0)) AS current_year_dmd_wc, "
								+ "        (COALESCE(dmd_int_for_mth_wc, 0)) AS current_year_dmd_int "
								+ "    FROM "
								+ "        pms_dcb_trn_bill_dmd dmd1 "
								+ "    LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ dmd_cond 
								+ ") dmd1 GROUP BY "
								+ "        dmd1.office_id, dmd1.beneficiary_sno, dmd1.beneficiary_type_id_sub), "
								+ "         "
								+ "         "
								+ "         "
								
								
								+ "        cur_yr_coln AS (select  dmd1.office_id,dmd1.beneficiary_sno,dmd1.beneficiary_type_id_sub, sum(dmd1.coln_wc) as coln_wc, sum(dmd1.coln_int) as coln_int   from ( "
								+ "    SELECT "
								+ "        dmd1.office_id, "
								+ "        CASE "
								+ "            WHEN ben1.beneficiary_type_id_sub = 1 THEN ben1.added_to_ben_sno "
								+ "            ELSE ben1.beneficiary_sno "
								+ "        END AS beneficiary_sno, "
								+ "        ben1.beneficiary_type_id_sub, "
								+ "        ( "
								+ "            COALESCE(coln_cur_yr_wc, 0) + COALESCE(coln_yester_yr_wc, 0) + COALESCE(coln_maint, 0) "
								+ "            - COALESCE(add_charges_wc, 0) + COALESCE(minus_charges_wc, 0) "
								+ "        ) AS coln_wc, "
								+ "        ( "
								+ "            COALESCE(coln_int_wc, 0) - COALESCE(add_charges_int, 0) + COALESCE(minus_charges_int, 0) "
								+ "        ) AS coln_int "
								+ "    FROM "
								+ "        pms_dcb_trn_bill_dmd dmd1 "
								+ "    LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ coln_cond 
								+ "        ) as dmd1 "
								
								
								+ "    GROUP BY "
								+ "        dmd1.office_id, dmd1.beneficiary_sno, dmd1.beneficiary_type_id_sub "
								+ "), "
								+ " "
								+ "cur_mth_coln_wc AS (select fas_coln_wc.office_id,fas_coln_wc.beneficiary_sno,fas_coln_wc.beneficiary_type_id_sub, sum(fas_coln_wc.amount) as fas_coln_wc   from ( "
								+ "    SELECT "
								+ "        total.office_id, "
								+ "        CASE "
								+ "            WHEN ben.beneficiary_type_id_sub = 1 THEN ben.added_to_ben_sno "
								+ "            ELSE ben.beneficiary_sno "
								+ "        END AS beneficiary_sno, "
								+ "        ben.beneficiary_type_id_sub, "
								+ "        (total.amount) AS amount "
								+ "    FROM ( "
								+ "        SELECT "
								+ "            pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id, "
								+ "            pms_dcb_fas_receipt_view.beneficiary_sno, "
								+ "            SUM(pms_dcb_fas_receipt_view.amount) AS amount "
								+ "        FROM "
								+ "            pms_dcb_fas_receipt_view "
								+ "        WHERE "
								+ "            pms_dcb_fas_receipt_view.account_head_code IN (782401, 782402, 782403, 782404, 782405, 782406, 782407, 900108, 900109) "
								+ "            AND pms_dcb_fas_receipt_view.cashbook_year =  "+coln_year+""
								+ "            AND pms_dcb_fas_receipt_view.cashbook_month = "+coln_month+""
								+ "        GROUP BY "
								+ "            pms_dcb_fas_receipt_view.accounting_for_office_id, pms_dcb_fas_receipt_view.beneficiary_sno "
								+ "             "
								+ "             "
								+ "        UNION all "
								+ "         "
								+ "        SELECT "
								+ "            pms_dcb_other_charges.office_id::numeric, "
								+ "            pms_dcb_other_charges.beneficiary_sno, "
								+ "            SUM(pms_dcb_other_charges.amount) AS amount "
								+ "        FROM "
								+ "            pms_dcb_other_charges "
								+ "        WHERE "
								+ "            pms_dcb_other_charges.account_head_code IN ( "
								+ "                SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code "
								+ "                FROM pms_dcb_receipt_account_map "
								+ "                WHERE pms_dcb_receipt_account_map.account_head_code IN (782401, 782402, 782403, 782404, 782405, 782406, 782407, 900108, 900109) "
								+ "            ) "
								+ "            AND pms_dcb_other_charges.cashbook_year =  "+coln_year+""
								+ "            AND pms_dcb_other_charges.cashbook_month =  "+coln_month+""
								+ "        GROUP BY "
								+ "            pms_dcb_other_charges.office_id, pms_dcb_other_charges.beneficiary_sno "
								+ "    ) total "
								+ "    LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
								+ "    ) fas_coln_wc "
								+ "    GROUP BY "
								+ "        fas_coln_wc.office_id,fas_coln_wc.beneficiary_sno,fas_coln_wc.beneficiary_type_id_sub "
								+ "),  "
								+ "cur_mth_coln_int as ( "
								+ "   select fas_coln_int.office_id,fas_coln_int.beneficiary_sno,fas_coln_int.beneficiary_type_id_sub, sum(fas_coln_int.amount) as fas_coln_int   "
								+ "    FROM ( "
								+ "        SELECT "
								+ "            total.office_id, "
								+ "            CASE "
								+ "                WHEN ben.beneficiary_type_id_sub = 1 THEN ben.added_to_ben_sno "
								+ "                ELSE ben.beneficiary_sno "
								+ "            END AS beneficiary_sno, "
								+ "            ben.beneficiary_type_id_sub, "
								+ "            amount "
								+ "        FROM ( "
								+ "            SELECT "
								+ "                pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id, "
								+ "                pms_dcb_fas_receipt_view.beneficiary_sno, "
								+ "                SUM(pms_dcb_fas_receipt_view.amount) AS amount "
								+ "            FROM "
								+ "                pms_dcb_fas_receipt_view "
								+ "            WHERE "
								+ "                pms_dcb_fas_receipt_view.account_head_code = 120601 "
								+ "                AND pms_dcb_fas_receipt_view.cashbook_year = "+coln_year+""
								+ "                AND pms_dcb_fas_receipt_view.cashbook_month = "+coln_month+""
								+ "            GROUP BY "
								+ "                pms_dcb_fas_receipt_view.accounting_for_office_id, pms_dcb_fas_receipt_view.beneficiary_sno "
								+ "            UNION ALL "
								+ "            SELECT "
								+ "                pms_dcb_other_charges.office_id::numeric, "
								+ "                pms_dcb_other_charges.beneficiary_sno, "
								+ "                SUM(pms_dcb_other_charges.amount) AS amount "
								+ "            FROM "
								+ "                pms_dcb_other_charges "
								+ "            WHERE "
								+ "                pms_dcb_other_charges.account_head_code = 120601 "
								+ "                AND pms_dcb_other_charges.cashbook_year = "+coln_year+""
								+ "                AND pms_dcb_other_charges.cashbook_month =  "+coln_month+""
								+ "            GROUP BY "
								+ "                pms_dcb_other_charges.office_id, pms_dcb_other_charges.beneficiary_sno "
								+ "                ) total "
								+ "                LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
								+ "    ) fas_coln_int "
								+ "    GROUP BY "
								+ "        fas_coln_int.office_id,fas_coln_int.beneficiary_sno,fas_coln_int.beneficiary_type_id_sub "
								+ "), "
								+ "region as ( "
								+ "	select * from REGION_CIR_DIV_VIEW "
								+ "), "
								+ "ben as ( "
								+ "select * from PMS_DCB_MST_BENEFICIARY "
								+ "), "
								+ "ben_type as ( "
								+ "select * from PMS_DCB_BEN_TYPE "
								+ "), "
								+ "district as( "
								+ "select * from com_mst_districts  "
								+ "), "
								+ "block as ( "
								+ "select * from com_mst_blocks "
								+ "), "
								+ "panchayat as ( "
								+ "select * from com_mst_panchayats cmp  "
								+ ")       "
								+ "select "
								+ "	reg.regid as region_code, "
								+ "	reg. reg as region_name, "
								+ "	reg.cirid as circle_code, "
								+ "	reg.cir as circle_name, "
								+ "    cb.office_id as division_code, "
								+ "    reg.div as division_name, "
								+ "      cb.beneficiary_sno as beneficiary_code, "
								+ "    ben.beneficiary_name, "
								+ "    ben_type.ben_type_id as beneficiary_type_id, "
								+ "    ben_type.ben_type_desc as beneficiary_type, "
								+ "    district.district_code, "
								+ "    district.district_name, "
								+ "    BLK.block_sno as block_code, "
								+ "    blk.block_name as block_name, "
								+ "    PCH.PANCH_SNO as panchayat_code, "
								+ "    pch.panch_name, "
								+ "     "
								+ "     "
								+ "	coalesce(cb.cb_wc,0) - coalesce(yr_dmd.current_year_dmd_wc,0) + coalesce(yr_coln.coln_wc,0) as WC_ARREAR_01_04_2022, "
								+ "	yr_dmd.current_year_dmd_wc as WC_CURRENT_YEAR_DEMAND, "
								+ "	cur_dmd.current_month_dmd_wc as WC_CURRENT_MONTH_DEMAND, "
								+ "	yr_coln.coln_wc + coalesce(cur_mth_coln_wc.fas_coln_wc,0) as WC_CURRENT_YEAR_COLLECTION, "
								+ "	coalesce(cur_mth_coln_wc.fas_coln_wc,0) as WC_CURRENT_MONTH_COLLECTION, "
								+ " "
								+ "	coalesce(cb.cb_int,0) - coalesce(yr_dmd.current_year_dmd_int,0) + coalesce(yr_coln.coln_int,0) as OB_INT_4, "
								+ "	yr_dmd.current_year_dmd_int as INTEREST_CURRENT_YEAR_DEMAND, "
								+ "	cur_dmd.current_month_dmd_int as INTEREST_CURRENT_MONTH_DEMAND, "
								+ "	yr_coln.coln_int + coalesce(cur_mth_coln_int.fas_coln_int,0) as INT_CURRENT_YEAR_COLLECTION, "
								+ "    coalesce(cur_mth_coln_int.fas_coln_int,0) as INT_CURRENT_MONTH_COLLECTION, "
								+ "     "
								+ "    case when (coalesce(cur_mth_coln_wc.fas_coln_wc,0) + coalesce(cur_mth_coln_INT.fas_coln_int,0) > 0) then "+coln_year+" else LAST_COL.BILL_YEAR end as LAST_COL_YEAR, "
								+ "    case when (coalesce(cur_mth_coln_wc.fas_coln_wc,0) + coalesce(cur_mth_coln_INT.fas_coln_int,0) > 0) then "+coln_month+" else LAST_COL.BILL_MONTH end as LAST_COL_MONTH "
								+ " "
								+ " "
								+ "FROM "
								+ "    cur_mth_cb cb "
								+ "LEFT JOIN cur_mth_dmd cur_dmd ON cb.office_id = cur_dmd.office_id "
								+ "AND cb.beneficiary_sno = cur_dmd.beneficiary_sno "
								+ "LEFT JOIN cur_yr_dmd yr_dmd ON cb.office_id = yr_dmd.office_id "
								+ "AND cb.beneficiary_sno = yr_dmd.beneficiary_sno "
								+ "LEFT JOIN cur_yr_coln yr_coln ON cb.office_id = yr_coln.office_id "
								+ "AND cb.beneficiary_sno = yr_coln.beneficiary_sno "
								+ "LEFT JOIN cur_mth_coln_wc ON cb.office_id = cur_mth_coln_wc.office_id "
								+ "AND cb.beneficiary_sno = cur_mth_coln_wc.beneficiary_sno "
								+ "left join cur_mth_coln_INT on cb.office_id = cur_mth_coln_int.office_id "
								+ "AND cb.beneficiary_sno = cur_mth_coln_int.beneficiary_sno "
								+ "left join region reg on reg.didid = cb.office_id    "
								+ "left join ben on ben.beneficiary_sno = cb.beneficiary_sno "
								+ "left join ben_type on ben_type.ben_type_id = ben.BENEFICIARY_TYPE_ID_SUB "
								+ "left join district on district.district_code  = BEN.DISTRICT_CODE "
								+ "LEFT JOIN block BLK ON BLK.BLOCK_SNO = BEN.BLOCK_SNO "
								+ "LEFT JOIN panchayat PCH ON PCH.PANCH_SNO = BEN.VILLAGE_PANCHAYAT_SNO  "
								+ "left JOIN "
								+ "( "
								+ "select * from ( "
								+ "select bill_year,bill_month, dmd1.beneficiary_sno, row_number()over(partition by  dmd1.beneficiary_sno order by  dmd1.beneficiary_sno , bill_year desc ,bill_month desc ) as rn  from   ( "
								+ "    select "
								+ "    	dmd1.bill_year, "
								+ "    	dmd1.bill_month, "
								+ "        dmd1.office_id, "
								+ "        CASE "
								+ "            WHEN ben1.beneficiary_type_id_sub = 1 THEN ben1.added_to_ben_sno "
								+ "            ELSE ben1.beneficiary_sno "
								+ "        END AS beneficiary_sno, "
								+ "        ben1.beneficiary_type_id_sub, "
								+ "        (COALESCE(wc_mth_total, 0)) AS current_year_dmd_wc, "
								+ "        (COALESCE(dmd_int_for_mth_wc, 0)) AS current_year_dmd_int, "
								+ "        (coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0) - coalesce(add_charges_wc,0) + coalesce(minus_charges_wc,0) - coalesce(add_charges_maint,0) + coalesce(minus_charges_maint,0)) as coln_wc, "
								+ "        (coalesce(coln_int_wc,0) - coalesce(add_charges_int,0) + coalesce(minus_charges_int,0)) as coln_int "
								+ "    FROM "
								+ "        pms_dcb_trn_bill_dmd dmd1 "
								+ "    LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ "    where (coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0) - coalesce(add_charges_wc,0) + coalesce(minus_charges_wc,0) - coalesce(add_charges_maint,0) + coalesce(minus_charges_maint,0)) > 0 "
								+ ") dmd1 GROUP BY "
								+ "         dmd1.beneficiary_sno, bill_year,bill_month "
								+ "         order by dmd1.beneficiary_sno "
								+ "      ) as OPT1 where rn = 1) last_COL on LAST_COL.beneficiary_sno  = cb.BENEFICIARY_SNO "
								+ " "
								+ " "
								+ ") as fin "
								+ "WHERE "
								+ "							wc_arrear_01_04_2022 > 0  "
								+ "							OR wc_current_year_demand > 0  "
								+ "							OR wc_current_month_demand > 0  "
								+ "							OR wc_current_year_collection > 0  "
								+ "							OR wc_current_month_collection > 0  "
								+ "							OR ob_int_4 > 0  "
								+ "							OR int_current_year_collection > 0  "
								+ "							OR int_current_month_collection > 0  "
								+ "							OR interest_current_month_demand > 0  "
								+ "							OR interest_current_year_demand > 0; ";
								

				// Create a PreparedStatement and execute the query
				PreparedStatement ps = con.prepareStatement(sqlQuery);

				System.out.println("Dashboard Query --------"+sqlQuery2);
				
				LocalTime currentTime = LocalTime.now();
		        
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		        
		        String formattedTime = currentTime.format(formatter);
		        
		        // Print the formatted time
		        System.out.println("Before time: " + formattedTime);
				ResultSet rs = ps.executeQuery();
				
				currentTime = LocalTime.now();
		        
		         formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		        
		         formattedTime = currentTime.format(formatter);
		        
		        // Print the formatted time
		        System.out.println("After time: " + formattedTime);

				

				generateExcelFile(rs, response, "DashboardReport", "DashboardReport");
				

				 
			}
			
			else if (command.trim().equalsIgnoreCase("dbd")) {
				
				flag = 1;
				
				int year1 = Integer.parseInt(obj.setValue("year", request));
				int month1 = Integer.parseInt(obj.setValue("month", request));
				
				
				cs = con.prepareCall("Select generate_dashboard_report (?, ?)");
				
				  cs.setInt(1,year1);
                  cs.setInt(2,month1);
				
                  cs.execute();
                  
                  
                  
                  String sqlQuery = "Select * from dbd_test";
                  
                  PreparedStatement ps = con.prepareStatement(sqlQuery);


				ResultSet rs = ps.executeQuery();

				generateExcelFile(rs, response, "DashboardReport");

				
			}

			// Created 05-01-2024
			else if (command.trim().equalsIgnoreCase("watercharges")) {
				flag = 1;
				int year1 = Integer.parseInt(obj.setValue("year", request));
				int month1 = Integer.parseInt(obj.setValue("month", request));
				int month2, year2;
				int rep1 = Integer.parseInt(obj.setValue("reporttype", request));

				if (month1 < 12) {
					month2 = month1 + 1;
					year2 = year1;
				} else {
					year2 = year1 + 1;
					month2 = 1;
				}

				LocalDate currentDate = LocalDate.now();
				int currentYear = currentDate.getYear();
				int currentMonth = currentDate.getMonthValue();

				YearMonth endYearMonth = YearMonth.of(currentYear, currentMonth);
				YearMonth startYearMonth = YearMonth.of(year1, month1);

				long monthsDifference = ChronoUnit.MONTHS.between(startYearMonth, endYearMonth);

				System.out.println(monthsDifference);

				if (monthsDifference >= 2) {
					int freezedCount = 0;
					String freezeCountQry = "SELECT COUNT(*) FROM ("
							+ " SELECT DISTINCT(OFFICE_ID) FROM PMS_DCB_TRN_BILL_DMD WHERE BILL_YEAR= " + year1
							+ " AND BILL_MONTH = " + month1 + ""
							+ " AND OFFICE_ID NOT IN (SELECT office_ID FROM pms_dcb_data_freeze WHERE cashbook_year="
							+ year2 + " AND cashbook_month=" + month2 + "" + " )) AS OPT1;";

					PreparedStatement ps = con.prepareStatement(freezeCountQry);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						freezedCount = rs.getInt(1);
					}

					if (freezedCount == 0) {

						System.out.println("Collection from DMD");

						qry = "SELECT * FROM " + "(SELECT" + "	REG.REGID AS REGION_CODE,"
								+ "		REG.REG AS REGION_NAME," + "		REG.CIRID AS CIRCLE_CODE,"
								+ "		REG.CIR AS CIRCLE_NAME," + "	DMD.OFFICE_ID AS DIVISION_CODE,"
								+ "	REG.DIV AS DIVISION_NAME," + "	BEN.DISTRICT_CODE AS DISTRICT_CODE ,"
								+ " DST.DISTRICT_NAME, " + "	BEN.BLOCK_SNO AS BLOCK_CODE," + "	BLK.BLOCK_NAME,"
								+ "	BEN.VILLAGE_PANCHAYAT_SNO AS PANCHAYAT_CODE,"
								+ "	PCH.PANCH_NAME AS PANCHAYAT_NAME," + "	DMD.BENEFICIARY_SNO,"
								+ "	BEN.BENEFICIARY_NAME," + "	BEN.BENEFICIARY_TYPE_ID_SUB AS BENEFICIARY_TYPE_ID,"
								+ "	TYP.BEN_TYPE_DESC AS BENEFICIARY_TYPE,"
								+ "	DMD.OPENING_BALANCE_WC AS OPENING_BALANCE_WC ,"
								+ "	DMD.CURRENT_MONTH_DMD_WC AS CURRENT_MONTH_DMD_WC,"
								+ "	COLN.CURRENT_MONTH_COLN_WC AS CURRENT_MONTH_COLN_WC,"
								+ "	COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) - COALESCE ( COLN.CURRENT_MONTH_COLN_WC, 0 ) AS BALANCE_WC,"
								+ "	DMD.OPENING_BALANCE_INT AS OPENING_BALANCE_INT, "
								+ "	DMD.CURRENT_MONTH_DMD_INT AS CURRENT_MONTH_DMD_INT, "
								+ "	COLN.CURRENT_MONTH_COLN_INT AS CURRENT_MONTH_COLN_INT, "
								+ "	COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) - COALESCE ( COLN.CURRENT_MONTH_COLN_INT, 0 ) AS BALANCE_INT,"
								+ "	COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) AS OPENING_BALANCE,"
								+ "	COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) AS CURRENT_MONTH_DMD,"
								+ "	COALESCE ( COLN.CURRENT_MONTH_COLN_WC, 0 ) + COALESCE ( COLN.CURRENT_MONTH_COLN_INT, 0 ) AS CURRENT_MONTH_COLN ,"
								+ "	( "
								+ "	COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) +"
								+ "	COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) -"
								+ "	COALESCE ( COLN.CURRENT_MONTH_COLN_WC, 0 ) + COALESCE ( COLN.CURRENT_MONTH_COLN_INT, 0 )"
								+ "	) AS BALANCE " + " FROM" + "	(" + "	SELECT" + "		office_id,"
								+ "		beneficiary_sno," + "		beneficiary_type_id_sub,"
								+ "		SUM ( ob_wc ) AS OPENING_BALANCE_WC,"
								+ "		SUM ( OB_INT ) AS OPENING_BALANCE_INT,"
								+ "		SUM ( dmd_wc ) AS CURRENT_MONTH_DMD_WC,"
								+ "		SUM ( dmd_int ) AS CURRENT_MONTH_DMD_INT " + "	FROM" + "		("
								+ "		SELECT" + "			dmd1.office_id,"
								+ "			ben1.beneficiary_type_id_sub," + "		CASE" + "				"
								+ "				WHEN ben1.beneficiary_type_id_sub = 1 THEN"
								+ "				ben1.added_to_ben_sno ELSE ben1.beneficiary_sno "
								+ "			END beneficiary_sno,"
								+ "	( COALESCE ( ob_yester_yr_wc, 0 ) + COALESCE ( ob_cur_yr_wc, 0 ) + COALESCE ( ob_maint_charges, 0 ) ) - ("
								+ "		COALESCE ( coln_cur_yr_wc, 0 ) + COALESCE ( coln_yester_yr_wc, 0 ) + COALESCE ( coln_maint, 0 ) - COALESCE ( add_charges_wc, 0 ) + COALESCE ( minus_charges_wc, 0 ) - COALESCE ( add_charges_maint, 0 ) + COALESCE ( minus_charges_maint ) "
								+ "	) AS ob_wc,"
								+ "	( COALESCE ( ob_int_amt_wc, 0 ) ) - ( COALESCE ( coln_int_wc, 0 ) - COALESCE ( add_charges_int, 0 ) + COALESCE ( minus_charges_int, 0 ) ) AS ob_int,"
								+ "	COALESCE ( wc_mth_total ) AS dmd_wc,"
								+ "	COALESCE ( dmd_int_for_mth_wc ) AS dmd_int " + "FROM"
								+ "	pms_dcb_trn_bill_dmd dmd1"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ "WHERE" + "	dmd1.bill_year = " + year1 + " " + "	AND dmd1.bill_month = " + month1
								+ " " + "	) opt1 " + "GROUP BY" + "	office_id," + "	beneficiary_sno,"
								+ "	beneficiary_type_id_sub " + "	) DMD" + "	LEFT JOIN (" + "	SELECT"
								+ "		office_id," + "		beneficiary_sno," + "		beneficiary_type_id_sub,"
								+ "		SUM ( coln_wc ) AS CURRENT_MONTH_COLN_WC,"
								+ "		SUM ( coln_int ) AS CURRENT_MONTH_COLN_INT " + "	FROM" + "		("
								+ "		SELECT" + "			dmd1.office_id,"
								+ "			ben1.beneficiary_type_id_sub," + "		CASE" + "				"
								+ "				WHEN ben1.beneficiary_type_id_sub = 1 THEN ben1.added_to_ben_sno"
								+ "				 ELSE  ben1.beneficiary_sno "
								+ "			END beneficiary_sno," + "	("
								+ "		COALESCE ( coln_cur_yr_wc, 0 ) + COALESCE ( coln_yester_yr_wc, 0 ) + COALESCE ( coln_maint, 0 ) - COALESCE ( add_charges_wc, 0 ) + COALESCE ( minus_charges_wc, 0 ) - COALESCE ( add_charges_maint, 0 ) + COALESCE ( minus_charges_maint ) "
								+ "	) AS coln_wc,"
								+ "	( COALESCE ( coln_int_wc, 0 ) - COALESCE ( add_charges_int, 0 ) + COALESCE ( minus_charges_int, 0 ) ) AS coln_int "
								+ "FROM" + "	pms_dcb_trn_bill_dmd dmd1"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ "WHERE" + "	dmd1.bill_year = " + year2 + " " + "	AND dmd1.bill_month = " + month2
								+ " " + "	) opt1 " + "GROUP BY" + "	office_id," + "	beneficiary_sno,"
								+ "	beneficiary_type_id_sub "
								+ "	) COLN ON COLN.BENEFICIARY_SNO = DMD.BENEFICIARY_SNO "
								+ "	AND COLN.OFFICE_ID = DMD.OFFICE_ID "
								+ "	LEFT JOIN PMS_DCB_MST_BENEFICIARY BEN ON BEN.BENEFICIARY_SNO = DMD.BENEFICIARY_SNO"
								+ "	LEFT JOIN REGION_CIR_DIV_VIEW REG ON REG.DIDID = DMD.OFFICE_ID"
								+ "	LEFT JOIN COM_MST_DISTRICTS DST ON DST.DISTRICT_CODE = BEN.DISTRICT_CODE "
								+ "	LEFT JOIN COM_MST_BLOCKS BLK ON BLK.BLOCK_SNO = BEN.BLOCK_SNO "
								+ "	LEFT JOIN COM_MST_PANCHAYATS PCH ON PCH.PANCH_SNO = BEN.VILLAGE_PANCHAYAT_SNO"
								+ "	LEFT JOIN PMS_DCB_BEN_TYPE TYP ON TYP.BEN_TYPE_ID = BEN.BENEFICIARY_TYPE_ID_SUB"
								+ "	) AS OPT1 ORDER BY REGION_NAME,CIRCLE_NAME, DIVISION_NAME, BENEFICIARY_TYPE_ID ;";

					}

					else {

						System.out.println("Collection from FAS");
						
						qry =     " SELECT"
								+ "	* "
								+ " FROM"
								+ "	("
								+ "	SELECT"
								+ "		REG.REGID AS REGION_CODE,"
								+ "		REG.REG AS REGION_NAME,"
								+ "		REG.CIRID AS CIRCLE_CODE,"
								+ "		REG.CIR AS CIRCLE_NAME,"
								+ "		DMD.OFFICE_ID AS DIVISION_CODE,"
								+ "		REG.DIV AS DIVISION_NAME,"
								+ "		BEN.DISTRICT_CODE AS DISTRICT_CODE,"
								+ "		BEN.BLOCK_SNO AS BLOCK_CODE,"
								+ "		BLK.BLOCK_NAME,"
								+ "		BEN.VILLAGE_PANCHAYAT_SNO AS PANCHAYAT_CODE,"
								+ "		PCH.PANCH_NAME AS PANCHAYAT_NAME,"
								+ "		DMD.BENEFICIARY_SNO,"
								+ "		BEN.BENEFICIARY_NAME,"
								+ "		BEN.BENEFICIARY_TYPE_ID_SUB AS BENEFICIARY_TYPE_ID,"
								+ "		TYP.BEN_TYPE_DESC AS BENEFICIARY_TYPE,"
								+ "		DMD.OPENING_BALANCE_WC,"
								+ "		DMD.CURRENT_MONTH_DMD_WC,"
								+ "		COALESCE ( COLN_WC.CURRENT_MONTH_COLN_WC, 0 ) AS CURRENT_MONTH_COLN_WC,"
								+ "		COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) - COALESCE ( COLN_WC.CURRENT_MONTH_COLN_WC, 0 ) AS BALANCE_WC,"
								+ "		DMD.OPENING_BALANCE_INT,"
								+ "		DMD.CURRENT_MONTH_DMD_INT,"
								+ "		COALESCE ( COLN_INT.CURRENT_MONTH_COLN_INT, 0 ) AS CURRENT_MONTH_COLN_INT,"
								+ "		COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) - COALESCE ( COLN_INT.CURRENT_MONTH_COLN_INT, 0 ) AS BALANCE_INT,"
								+ "		COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) AS OPENING_BALANCE,"
								+ "		COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) AS CURRENT_MONTH_DMD,"
								+ "		COALESCE ( COLN_WC.CURRENT_MONTH_COLN_WC, 0 ) + COALESCE ( COLN_INT.CURRENT_MONTH_COLN_INT, 0 ) AS CURRENT_MONTH_COLN,"
								+ "		("
								+ "			COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) - COALESCE ( COLN_WC.CURRENT_MONTH_COLN_WC, 0 ) - COALESCE ( COLN_INT.CURRENT_MONTH_COLN_INT, 0 ) "
								+ "		) AS BALANCE "
								+ "	FROM"
								+ "		("
								+ "		SELECT"
								+ "			office_id,"
								+ "			beneficiary_sno,"
								+ "			beneficiary_type_id_sub,"
								+ "			SUM ( ob_wc ) AS OPENING_BALANCE_WC,"
								+ "			SUM ( OB_INT ) AS OPENING_BALANCE_INT,"
								+ "			SUM ( dmd_wc ) AS CURRENT_MONTH_DMD_WC,"
								+ "			SUM ( dmd_int ) AS CURRENT_MONTH_DMD_INT "
								+ "		FROM"
								+ "			("
								+ "			SELECT"
								+ "				dmd1.office_id,"
								+ "				ben1.beneficiary_type_id_sub,"
								+ "			CASE"
								+ "					"
								+ "					WHEN ben1.beneficiary_type_id_sub = 1 THEN"
								+ "					ben1.added_to_ben_sno ELSE ben1.beneficiary_sno "
								+ "				END beneficiary_sno,"
								+ "	( COALESCE ( ob_yester_yr_wc, 0 ) + COALESCE ( ob_cur_yr_wc, 0 ) + COALESCE ( ob_maint_charges, 0 ) ) - ("
								+ "		COALESCE ( coln_cur_yr_wc, 0 ) + COALESCE ( coln_yester_yr_wc, 0 ) + COALESCE ( coln_maint, 0 ) - COALESCE ( add_charges_wc, 0 ) + COALESCE ( minus_charges_wc, 0 ) - COALESCE ( add_charges_maint, 0 ) + COALESCE ( minus_charges_maint ) "
								+ "	) AS ob_wc,"
								+ "	( COALESCE ( ob_int_amt_wc, 0 ) ) - ( COALESCE ( coln_int_wc, 0 ) - COALESCE ( add_charges_int, 0 ) + COALESCE ( minus_charges_int, 0 ) ) AS ob_int,"
								+ "	COALESCE ( wc_mth_total ) AS dmd_wc,"
								+ "	COALESCE ( dmd_int_for_mth_wc ) AS dmd_int "
								+ "FROM"
								+ "	pms_dcb_trn_bill_dmd dmd1"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ "WHERE"
								+ "	dmd1.bill_year = " +year1+ " "
								+ "	AND dmd1.bill_month = "+month1+" "
								+ "	) opt1 "
								+ "GROUP BY"
								+ "	office_id,"
								+ "	beneficiary_sno,"
								+ "	beneficiary_type_id_sub "
								+ "	) DMD"
							//	+ "	---------------"
								+ "	"
								+ "LEFT JOIN "
								+ "	"
							//	+ "	--------------"
							//	+ " -- 	new coln"
								+ "	"
								+ "	("
								+ "	SELECT"
								+ "		office_id,"
								+ "		beneficiary_sno,"
								+ "		beneficiary_type_id_sub,"
								+ "		SUM ( amount ) AS CURRENT_MONTH_COLN_WC "
								+ "	FROM"
								+ "		("
								+ "		SELECT"
								+ "			total.office_id,"
								+ "			ben.beneficiary_type_id_sub,"
								+ "		CASE"
								+ "				"
								+ "				WHEN ben.beneficiary_type_id_sub = 1 THEN"
								+ "				ben.added_to_ben_sno ELSE ben.beneficiary_sno "
								+ "			END AS beneficiary_sno,"
								+ "			amount "
								+ "		FROM"
								+ "			("
								+ "			SELECT"
								+ "				* "
								+ "			FROM"
								+ "				("
								+ "				SELECT"
								+ "					office_id,"
								+ "					beneficiary_sno,"
								+ "					SUM ( amount ) AS amount "
								+ "				FROM"
								+ "					("
								+ "					SELECT"
								+ "						pms_dcb_fas_receipt_view.receipt_no,"
								+ "						pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
								+ "						pms_dcb_fas_receipt_view.cashbook_year AS YEAR,"
								+ "						pms_dcb_fas_receipt_view.cashbook_month AS MONTH,"
								+ "						pms_dcb_fas_receipt_view.sub_ledger_type_code,"
								+ "						pms_dcb_fas_receipt_view.sub_ledger_code,"
								+ "						pms_dcb_fas_receipt_view.sch_type_id,"
								+ "						pms_dcb_fas_receipt_view.sch_sno,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_name,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_sno,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
								+ "						pms_dcb_fas_receipt_view.amount "
								+ "					FROM"
								+ "						pms_dcb_fas_receipt_view "
								+ "					WHERE"
								+ "						("
								+ "							pms_dcb_fas_receipt_view.account_head_code = ANY ("
								+ "								ARRAY [ 782401 :: NUMERIC,"
								+ "								782402 :: NUMERIC,"
								+ "								782403 :: NUMERIC,"
								+ "								782404 :: NUMERIC,"
								+ "								782405 :: NUMERIC,"
								+ "								782406 :: NUMERIC,"
								+ "								782407 :: NUMERIC,"
								+ "								900108 :: NUMERIC,"
								+ "								900109 :: NUMERIC ] "
								+ "							) "
								+ "						) "
								+ "						AND cashbook_year = "+year2+" "
								+ "						AND cashbook_month = "+month2+" "
								+ "					) opt1 "
								+ "				GROUP BY"
								+ "					office_id,"
								+ "					beneficiary_sno "
								+ "				) receipt UNION ALL"
								+ "			SELECT"
								+ "				* "
								+ "			FROM"
								+ "				("
								+ "				SELECT"
								+ "					opt4.office_id :: NUMERIC AS office_id,"
								+ "					opt4.beneficiary_sno,"
								+ "					SUM ( opt4.amount ) AS amount "
								+ "				FROM"
								+ "					("
								+ "					SELECT"
								+ "						pms_dcb_other_charges.office_id,"
								+ "						pms_dcb_other_charges.beneficiary_sno,"
								+ "						pms_dcb_other_charges.cashbook_year AS YEAR,"
								+ "						pms_dcb_other_charges.cashbook_month AS MONTH,"
								+ "						SUM ("
								+ "						COALESCE ( CASE WHEN pms_dcb_other_charges.cr_dr_indicator :: TEXT = 'CR' :: TEXT THEN pms_dcb_other_charges.amount ELSE NULL :: NUMERIC END, 0 :: NUMERIC ) "
								+ "		) AS amount "
								+ "	FROM"
								+ "		pms_dcb_other_charges "
								+ "	WHERE"
								+ "		( pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 7 :: NUMERIC ) ) "
								+ "	GROUP BY"
								+ "		pms_dcb_other_charges.office_id,"
								+ "		pms_dcb_other_charges.beneficiary_sno,"
								+ "		pms_dcb_other_charges.cashbook_year,"
								+ "		pms_dcb_other_charges.cashbook_month "
								+ "	ORDER BY"
								+ "		pms_dcb_other_charges.office_id,"
								+ "		pms_dcb_other_charges.beneficiary_sno "
								+ "	) AS opt4 "
								+ "WHERE"
								+ "	opt4.YEAR = "+year2+" "
								+ "	AND opt4.MONTH = "+month2+" "
								+ "GROUP BY"
								+ "	opt4.office_id,"
								+ "	opt4.beneficiary_sno "
								+ "	) adj "
								+ "	) total"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
								+ "	) AS opt1 "
								+ "GROUP BY"
								+ "	office_id,"
								+ "	beneficiary_sno,"
								+ "	beneficiary_type_id_sub "
								+ "	) COLN_WC ON COLN_WC.OFFICE_ID = DMD.OFFICE_ID "
								+ "	AND COLN_WC.BENEFICIARY_SNO = DMD.BENEFICIARY_SNO"
								+ "	"
							//	+ "-- 	new coln_int "
								+ " LEFT JOIN "
								+ " "
								+ " ("
								+ "	"
								+ "	SELECT"
								+ "		office_id,"
								+ "		beneficiary_sno,"
								+ "		beneficiary_type_id_sub,"
								+ "		SUM ( amount ) AS CURRENT_MONTH_COLN_INT "
								+ "	FROM"
								+ "		("
								+ "		SELECT"
								+ "			total.office_id,"
								+ "			ben.beneficiary_type_id_sub,"
								+ "		CASE"
								+ "				"
								+ "				WHEN ben.beneficiary_type_id_sub = 1 THEN"
								+ "				ben.added_to_ben_sno ELSE ben.beneficiary_sno "
								+ "			END AS beneficiary_sno,"
								+ "			amount "
								+ "		FROM"
								+ "			("
								+ "			SELECT"
								+ "				* "
								+ "			FROM"
								+ "				("
								+ "				SELECT"
								+ "					office_id,"
								+ "					beneficiary_sno,"
								+ "					SUM ( amount ) AS amount "
								+ "				FROM"
								+ "					("
								+ "					SELECT"
								+ "						pms_dcb_fas_receipt_view.receipt_no,"
								+ "						pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
								+ "						pms_dcb_fas_receipt_view.cashbook_year AS YEAR,"
								+ "						pms_dcb_fas_receipt_view.cashbook_month AS MONTH,"
								+ "						pms_dcb_fas_receipt_view.sub_ledger_type_code,"
								+ "						pms_dcb_fas_receipt_view.sub_ledger_code,"
								+ "						pms_dcb_fas_receipt_view.sch_type_id,"
								+ "						pms_dcb_fas_receipt_view.sch_sno,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_name,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_sno,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
								+ "						pms_dcb_fas_receipt_view.amount "
								+ "					FROM"
								+ "						pms_dcb_fas_receipt_view "
								+ "					WHERE"
								+ "						( pms_dcb_fas_receipt_view.account_head_code = ANY ( ARRAY [ 120601 :: NUMERIC ] ) ) "
								+ "						AND cashbook_year = "+year2+" :: NUMERIC "
								+ "						AND cashbook_month = "+month2+" :: NUMERIC "
								+ "					) opt1 "
								+ "				GROUP BY"
								+ "					office_id,"
								+ "					beneficiary_sno "
								+ "				) receipt UNION ALL"
								+ "			SELECT"
								+ "				* "
								+ "			FROM"
								+ "				("
								+ "				SELECT"
								+ "					opt4.office_id :: NUMERIC AS office_id,"
								+ "					opt4.beneficiary_sno,"
								+ "					SUM ( opt4.amount ) AS amount "
								+ "				FROM"
								+ "					("
								+ "					SELECT"
								+ "						pms_dcb_other_charges.office_id,"
								+ "						pms_dcb_other_charges.beneficiary_sno,"
								+ "						pms_dcb_other_charges.cashbook_year AS YEAR,"
								+ "						pms_dcb_other_charges.cashbook_month AS MONTH,"
								+ "						SUM ("
								+ "						COALESCE ( CASE WHEN pms_dcb_other_charges.cr_dr_indicator :: TEXT = 'CR' :: TEXT THEN pms_dcb_other_charges.amount ELSE NULL :: NUMERIC END, 0 :: NUMERIC ) "
								+ "		) AS amount "
								+ "	FROM"
								+ "		pms_dcb_other_charges "
								+ "	WHERE"
								+ "		( pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 9 :: NUMERIC ) ) "
								+ "	GROUP BY"
								+ "		pms_dcb_other_charges.office_id,"
								+ "		pms_dcb_other_charges.beneficiary_sno,"
								+ "		pms_dcb_other_charges.cashbook_year,"
								+ "		pms_dcb_other_charges.cashbook_month "
								+ "	ORDER BY"
								+ "		pms_dcb_other_charges.office_id,"
								+ "		pms_dcb_other_charges.beneficiary_sno "
								+ "	) AS opt4 "
								+ " WHERE"
								+ "	opt4.YEAR = "+year2+" :: NUMERIC "
								+ "	AND opt4.MONTH = "+month2+" :: NUMERIC "
								+ " GROUP BY"
								+ "	opt4.office_id,"
								+ "	opt4.beneficiary_sno "
								+ "	) adj "
								+ "	) total"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
								+ "	) AS opt1 "
								+ " GROUP BY"
								+ "	office_id,"
								+ "	beneficiary_sno,"
								+ "	beneficiary_type_id_sub "
								+ "	) COLN_INT ON COLN_INT.BENEFICIARY_SNO = DMD.BENEFICIARY_SNO "
								+ "	AND COLN_INT.OFFICE_ID = DMD.OFFICE_ID"
//								+ "	----"
								+ "	LEFT JOIN PMS_DCB_MST_BENEFICIARY BEN ON BEN.BENEFICIARY_SNO = DMD.BENEFICIARY_SNO"
								+ "	LEFT JOIN REGION_CIR_DIV_VIEW REG ON REG.DIDID = DMD.OFFICE_ID"
								+ "	LEFT JOIN COM_MST_DISTRICTS DST ON DST.DISTRICT_CODE = BEN.DISTRICT_CODE"
								+ "	LEFT JOIN COM_MST_BLOCKS BLK ON BLK.BLOCK_SNO = BEN.BLOCK_SNO"
								+ "	LEFT JOIN COM_MST_PANCHAYATS PCH ON PCH.PANCH_SNO = BEN.VILLAGE_PANCHAYAT_SNO"
								+ "	LEFT JOIN PMS_DCB_BEN_TYPE TYP ON TYP.BEN_TYPE_ID = BEN.BENEFICIARY_TYPE_ID_SUB "
								+ "	) AS OPT1 "
								+ " ORDER BY"
								+ "	REGION_NAME,"
								+ "	CIRCLE_NAME,"
								+ "	DIVISION_NAME,"
								+ "	BENEFICIARY_TYPE_ID " ;


					}
				}

				else if (monthsDifference == 1) {
					
					
					qry =   " SELECT"
							+ "	* "
							+ "FROM"
							+ "	("
							+ "	SELECT"
							+ "		REG.REGID AS REGION_CODE,"
							+ "		REG.REG AS REGION_NAME,"
							+ "		REG.CIRID AS CIRCLE_CODE,"
							+ "		REG.CIR AS CIRCLE_NAME,"
							+ "		DMD.OFFICE_ID AS DIVISION_CODE,"
							+ "		REG.DIV AS DIVISION_NAME,"
							+ "		BEN.DISTRICT_CODE AS DISTRICT_CODE,"
							+ "		BEN.BLOCK_SNO AS BLOCK_CODE,"
							+ "		BLK.BLOCK_NAME,"
							+ "		BEN.VILLAGE_PANCHAYAT_SNO AS PANCHAYAT_CODE,"
							+ "		PCH.PANCH_NAME AS PANCHAYAT_NAME,"
							+ "		DMD.BENEFICIARY_SNO,"
							+ "		BEN.BENEFICIARY_NAME,"
							+ "		BEN.BENEFICIARY_TYPE_ID_SUB AS BENEFICIARY_TYPE_ID,"
							+ "		TYP.BEN_TYPE_DESC AS BENEFICIARY_TYPE,"
							+ "		DMD.OPENING_BALANCE_WC,"
							+ "		DMD.CURRENT_MONTH_DMD_WC,"
							+ "		COALESCE ( COLN_WC.CURRENT_MONTH_COLN_WC, 0 ) AS CURRENT_MONTH_COLN_WC,"
							+ "		COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) - COALESCE ( COLN_WC.CURRENT_MONTH_COLN_WC, 0 ) AS BALANCE_WC,"
							+ "		DMD.OPENING_BALANCE_INT,"
							+ "		DMD.CURRENT_MONTH_DMD_INT,"
							+ "		COALESCE ( COLN_INT.CURRENT_MONTH_COLN_INT, 0 ) AS CURRENT_MONTH_COLN_INT,"
							+ "		COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) - COALESCE ( COLN_INT.CURRENT_MONTH_COLN_INT, 0 ) AS BALANCE_INT,"
							+ "		COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) AS OPENING_BALANCE,"
							+ "		COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) AS CURRENT_MONTH_DMD,"
							+ "		COALESCE ( COLN_WC.CURRENT_MONTH_COLN_WC, 0 ) + COALESCE ( COLN_INT.CURRENT_MONTH_COLN_INT, 0 ) AS CURRENT_MONTH_COLN,"
							+ "		("
							+ "			COALESCE ( DMD.OPENING_BALANCE_WC, 0 ) + COALESCE ( DMD.OPENING_BALANCE_INT, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_WC, 0 ) + COALESCE ( DMD.CURRENT_MONTH_DMD_INT, 0 ) - COALESCE ( COLN_WC.CURRENT_MONTH_COLN_WC, 0 ) - COALESCE ( COLN_INT.CURRENT_MONTH_COLN_INT, 0 ) "
							+ "		) AS BALANCE "
							+ "	FROM"
							+ "		("
							+ "		SELECT"
							+ "			office_id,"
							+ "			beneficiary_sno,"
							+ "			beneficiary_type_id_sub,"
							+ "			SUM ( ob_wc ) AS OPENING_BALANCE_WC,"
							+ "			SUM ( OB_INT ) AS OPENING_BALANCE_INT,"
							+ "			SUM ( dmd_wc ) AS CURRENT_MONTH_DMD_WC,"
							+ "			SUM ( dmd_int ) AS CURRENT_MONTH_DMD_INT "
							+ "		FROM"
							+ "			("
							+ "			SELECT"
							+ "				dmd1.office_id,"
							+ "				ben1.beneficiary_type_id_sub,"
							+ "			CASE"
							+ "					"
							+ "					WHEN ben1.added_to_ben_sno IS NULL THEN"
							+ "					ben1.beneficiary_sno ELSE ben1.added_to_ben_sno "
							+ "				END beneficiary_sno,"
							+ "	( COALESCE ( ob_yester_yr_wc, 0 ) + COALESCE ( ob_cur_yr_wc, 0 ) + COALESCE ( ob_maint_charges, 0 ) ) - ("
							+ "		COALESCE ( coln_cur_yr_wc, 0 ) + COALESCE ( coln_yester_yr_wc, 0 ) + COALESCE ( coln_maint, 0 ) - COALESCE ( add_charges_wc, 0 ) + COALESCE ( minus_charges_wc, 0 ) - COALESCE ( add_charges_maint, 0 ) + COALESCE ( minus_charges_maint ) "
							+ "	) AS ob_wc,"
							+ "	( COALESCE ( ob_int_amt_wc, 0 ) ) - ( COALESCE ( coln_int_wc, 0 ) - COALESCE ( add_charges_int, 0 ) + COALESCE ( minus_charges_int, 0 ) ) AS ob_int,"
							+ "	COALESCE ( wc_mth_total ) AS dmd_wc,"
							+ "	COALESCE ( dmd_int_for_mth_wc ) AS dmd_int "
							+ "FROM"
							+ "	pms_dcb_trn_bill_dmd dmd1"
							+ "	LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
							+ "WHERE"
							+ "	dmd1.bill_year = " +year1+ " "
							+ "	AND dmd1.bill_month = "+month1+" "
							+ "	) opt1 "
							+ "GROUP BY"
							+ "	office_id,"
							+ "	beneficiary_sno,"
							+ "	beneficiary_type_id_sub "
							+ "	) DMD"
						//	+ "	---------------"
							+ "	"
							+ "LEFT JOIN "
							+ "	"
						//	+ "	--------------"
						//	+ " -- 	new coln"
							+ "	"
							+ "	("
							+ "	SELECT"
							+ "		office_id,"
							+ "		beneficiary_sno,"
							+ "		beneficiary_type_id_sub,"
							+ "		SUM ( amount ) AS CURRENT_MONTH_COLN_WC "
							+ "	FROM"
							+ "		("
							+ "		SELECT"
							+ "			total.office_id,"
							+ "			ben.beneficiary_type_id_sub,"
							+ "		CASE"
							+ "				"
							+ "				WHEN ben.added_to_ben_sno IS NOT NULL THEN"
							+ "				added_to_ben_sno ELSE ben.beneficiary_sno "
							+ "			END AS beneficiary_sno,"
							+ "			amount "
							+ "		FROM"
							+ "			("
							+ "			SELECT"
							+ "				* "
							+ "			FROM"
							+ "				("
							+ "				SELECT"
							+ "					office_id,"
							+ "					beneficiary_sno,"
							+ "					SUM ( amount ) AS amount "
							+ "				FROM"
							+ "					("
							+ "					SELECT"
							+ "						pms_dcb_fas_receipt_view.receipt_no,"
							+ "						pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
							+ "						pms_dcb_fas_receipt_view.cashbook_year AS YEAR,"
							+ "						pms_dcb_fas_receipt_view.cashbook_month AS MONTH,"
							+ "						pms_dcb_fas_receipt_view.sub_ledger_type_code,"
							+ "						pms_dcb_fas_receipt_view.sub_ledger_code,"
							+ "						pms_dcb_fas_receipt_view.sch_type_id,"
							+ "						pms_dcb_fas_receipt_view.sch_sno,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_name,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_sno,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
							+ "						pms_dcb_fas_receipt_view.amount "
							+ "					FROM"
							+ "						pms_dcb_fas_receipt_view "
							+ "					WHERE"
							+ "						("
							+ "							pms_dcb_fas_receipt_view.account_head_code = ANY ("
							+ "								ARRAY [ 782401 :: NUMERIC,"
							+ "								782402 :: NUMERIC,"
							+ "								782403 :: NUMERIC,"
							+ "								782404 :: NUMERIC,"
							+ "								782405 :: NUMERIC,"
							+ "								782406 :: NUMERIC,"
							+ "								782407 :: NUMERIC,"
							+ "								900108 :: NUMERIC,"
							+ "								900109 :: NUMERIC ] "
							+ "							) "
							+ "						) "
							+ "						AND cashbook_year = "+year2+" "
							+ "						AND cashbook_month = "+month2+" "
							+ "					) opt1 "
							+ "				GROUP BY"
							+ "					office_id,"
							+ "					beneficiary_sno "
							+ "				) receipt UNION ALL"
							+ "			SELECT"
							+ "				* "
							+ "			FROM"
							+ "				("
							+ "				SELECT"
							+ "					opt4.office_id :: NUMERIC AS office_id,"
							+ "					opt4.beneficiary_sno,"
							+ "					SUM ( opt4.amount ) AS amount "
							+ "				FROM"
							+ "					("
							+ "					SELECT"
							+ "						pms_dcb_other_charges.office_id,"
							+ "						pms_dcb_other_charges.beneficiary_sno,"
							+ "						pms_dcb_other_charges.cashbook_year AS YEAR,"
							+ "						pms_dcb_other_charges.cashbook_month AS MONTH,"
							+ "						SUM ("
							+ "						COALESCE ( CASE WHEN pms_dcb_other_charges.cr_dr_indicator :: TEXT = 'CR' :: TEXT THEN pms_dcb_other_charges.amount ELSE NULL :: NUMERIC END, 0 :: NUMERIC ) "
							+ "		) AS amount "
							+ "	FROM"
							+ "		pms_dcb_other_charges "
							+ "	WHERE"
							+ "		( pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 7 :: NUMERIC ) ) "
							+ "	GROUP BY"
							+ "		pms_dcb_other_charges.office_id,"
							+ "		pms_dcb_other_charges.beneficiary_sno,"
							+ "		pms_dcb_other_charges.cashbook_year,"
							+ "		pms_dcb_other_charges.cashbook_month "
							+ "	ORDER BY"
							+ "		pms_dcb_other_charges.office_id,"
							+ "		pms_dcb_other_charges.beneficiary_sno "
							+ "	) AS opt4 "
							+ "WHERE"
							+ "	opt4.YEAR = "+year2+" "
							+ "	AND opt4.MONTH = "+month2+" "
							+ "GROUP BY"
							+ "	opt4.office_id,"
							+ "	opt4.beneficiary_sno "
							+ "	) adj "
							+ "	) total"
							+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
							+ "	) AS opt1 "
							+ "GROUP BY"
							+ "	office_id,"
							+ "	beneficiary_sno,"
							+ "	beneficiary_type_id_sub "
							+ "	) COLN_WC ON COLN_WC.OFFICE_ID = DMD.OFFICE_ID "
							+ "	AND COLN_WC.BENEFICIARY_SNO = DMD.BENEFICIARY_SNO"
							+ "	"
						//	+ "-- 	new coln_int "
							+ " LEFT JOIN "
							+ " "
							+ " ("
							+ "	"
							+ "	SELECT"
							+ "		office_id,"
							+ "		beneficiary_sno,"
							+ "		beneficiary_type_id_sub,"
							+ "		SUM ( amount ) AS CURRENT_MONTH_COLN_INT "
							+ "	FROM"
							+ "		("
							+ "		SELECT"
							+ "			total.office_id,"
							+ "			ben.beneficiary_type_id_sub,"
							+ "		CASE"
							+ "				"
							+ "				WHEN ben.added_to_ben_sno IS NOT NULL THEN"
							+ "				added_to_ben_sno ELSE ben.beneficiary_sno "
							+ "			END AS beneficiary_sno,"
							+ "			amount "
							+ "		FROM"
							+ "			("
							+ "			SELECT"
							+ "				* "
							+ "			FROM"
							+ "				("
							+ "				SELECT"
							+ "					office_id,"
							+ "					beneficiary_sno,"
							+ "					SUM ( amount ) AS amount "
							+ "				FROM"
							+ "					("
							+ "					SELECT"
							+ "						pms_dcb_fas_receipt_view.receipt_no,"
							+ "						pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
							+ "						pms_dcb_fas_receipt_view.cashbook_year AS YEAR,"
							+ "						pms_dcb_fas_receipt_view.cashbook_month AS MONTH,"
							+ "						pms_dcb_fas_receipt_view.sub_ledger_type_code,"
							+ "						pms_dcb_fas_receipt_view.sub_ledger_code,"
							+ "						pms_dcb_fas_receipt_view.sch_type_id,"
							+ "						pms_dcb_fas_receipt_view.sch_sno,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_name,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_sno,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
							+ "						pms_dcb_fas_receipt_view.amount "
							+ "					FROM"
							+ "						pms_dcb_fas_receipt_view "
							+ "					WHERE"
							+ "						( pms_dcb_fas_receipt_view.account_head_code = ANY ( ARRAY [ 120601 :: NUMERIC ] ) ) "
							+ "						AND cashbook_year = "+year2+" :: NUMERIC "
							+ "						AND cashbook_month = "+month2+" :: NUMERIC "
							+ "					) opt1 "
							+ "				GROUP BY"
							+ "					office_id,"
							+ "					beneficiary_sno "
							+ "				) receipt UNION ALL"
							+ "			SELECT"
							+ "				* "
							+ "			FROM"
							+ "				("
							+ "				SELECT"
							+ "					opt4.office_id :: NUMERIC AS office_id,"
							+ "					opt4.beneficiary_sno,"
							+ "					SUM ( opt4.amount ) AS amount "
							+ "				FROM"
							+ "					("
							+ "					SELECT"
							+ "						pms_dcb_other_charges.office_id,"
							+ "						pms_dcb_other_charges.beneficiary_sno,"
							+ "						pms_dcb_other_charges.cashbook_year AS YEAR,"
							+ "						pms_dcb_other_charges.cashbook_month AS MONTH,"
							+ "						SUM ("
							+ "						COALESCE ( CASE WHEN pms_dcb_other_charges.cr_dr_indicator :: TEXT = 'CR' :: TEXT THEN pms_dcb_other_charges.amount ELSE NULL :: NUMERIC END, 0 :: NUMERIC ) "
							+ "		) AS amount "
							+ "	FROM"
							+ "		pms_dcb_other_charges "
							+ "	WHERE"
							+ "		( pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 9 :: NUMERIC ) ) "
							+ "	GROUP BY"
							+ "		pms_dcb_other_charges.office_id,"
							+ "		pms_dcb_other_charges.beneficiary_sno,"
							+ "		pms_dcb_other_charges.cashbook_year,"
							+ "		pms_dcb_other_charges.cashbook_month "
							+ "	ORDER BY"
							+ "		pms_dcb_other_charges.office_id,"
							+ "		pms_dcb_other_charges.beneficiary_sno "
							+ "	) AS opt4 "
							+ " WHERE"
							+ "	opt4.YEAR = "+year2+" :: NUMERIC "
							+ "	AND opt4.MONTH = "+month2+" :: NUMERIC "
							+ " GROUP BY"
							+ "	opt4.office_id,"
							+ "	opt4.beneficiary_sno "
							+ "	) adj "
							+ "	) total"
							+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
							+ "	) AS opt1 "
							+ " GROUP BY"
							+ "	office_id,"
							+ "	beneficiary_sno,"
							+ "	beneficiary_type_id_sub "
							+ "	) COLN_INT ON COLN_INT.BENEFICIARY_SNO = DMD.BENEFICIARY_SNO "
							+ "	AND COLN_INT.OFFICE_ID = DMD.OFFICE_ID"
							+ "	LEFT JOIN PMS_DCB_MST_BENEFICIARY BEN ON BEN.BENEFICIARY_SNO = DMD.BENEFICIARY_SNO"
							+ "	LEFT JOIN REGION_CIR_DIV_VIEW REG ON REG.DIDID = DMD.OFFICE_ID"
							+ "	LEFT JOIN COM_MST_DISTRICTS DST ON DST.DISTRICT_CODE = BEN.DISTRICT_CODE"
							+ "	LEFT JOIN COM_MST_BLOCKS BLK ON BLK.BLOCK_SNO = BEN.BLOCK_SNO"
							+ "	LEFT JOIN COM_MST_PANCHAYATS PCH ON PCH.PANCH_SNO = BEN.VILLAGE_PANCHAYAT_SNO"
							+ "	LEFT JOIN PMS_DCB_BEN_TYPE TYP ON TYP.BEN_TYPE_ID = BEN.BENEFICIARY_TYPE_ID_SUB "
							+ "	) AS OPT1 "
							+ " ORDER BY"
							+ "	REGION_NAME,"
							+ "	CIRCLE_NAME,"
							+ "	DIVISION_NAME,"
							+ "	BENEFICIARY_TYPE_ID " ;
					
					
				}
				
				
				
				
				
				
				
				

				PreparedStatement ps = con.prepareStatement(qry);

				System.out.println(qry);
				ResultSet rs = ps.executeQuery();

				generateExcelFile(rs, response, "WaterChargesReport");

			}

			else if (command.trim().equalsIgnoreCase("monthEndBalance")) {
				
				System.out.println("Inside month End Water charges");
				option = obj.setValue("option", request);
				String off_cond= "";
				int bentype = Integer.parseInt(obj.setValue("bentype", request));

				String rep = obj.setValue("reporttype", request);
				int year1 = Integer.parseInt(obj.setValue("year", request));
				int month1 = Integer.parseInt(obj.setValue("month", request));
				int report_id = Integer.parseInt(obj.setValue("report_id", request));
				if(report_id!= 0 ) {
					off_cond = "and A.office_id = "+report_id;
				}

				System.out.println("Bentype: " + bentype);

				if (bentype == 1) {
					filename= "CORPORATION";
					flag = 0;
					qry = " select * from (SELECT Z.beneficiary_sno BENEFICIARY_SNO," + " Y.BENEFICIARY_NAME,"
							+ " Z.ob_wc OB_WC," + " Z.coln_wc COLN_WC," + " Z.balance_wc BALANCE_WC,"
							+ " Z.dmd_wc DMD_WC," + " Z.total_wc TOTAL_WC," + " Z.ob_int OB_INT,"
							+ " Z.coln_int COLN_INT," + " Z.balance_int BALANCE_INT," + " Z.dmd_int DMD_INT,"
							+ " Z.total_int TOTAL_INT," + " Z.total TOTAL"
							+ " FROM (select E.DISTRICT_CODE,E.DISTRICT_NAME,B.ADDED_TO_BEN_SNO BENEFICIARY_SNO, SUM(A.OB_WC) OB_WC , SUM(A.COLN_WC) COLN_WC, SUM(A.OB_WC - A.COLN_WC) as balance_wc, SUM(A.dmd_wc) AS DMD_WC, "
							+ " SUM(A.OB_WC - A.COLN_WC+ A.dmd_wc) as Total_wc,"
							+ " SUM(A.OB_int) OB_INT, SUM(A.COLN_int) COLN_INT,SUM(A.OB_int - A.COLN_int) as balance_int,SUM(A.dmd_int) DMD_INT, SUM(A.OB_int - A.COLN_int + A.dmd_int) as Total_int,"
							+ " SUM(A.OB_WC - A.COLN_WC + A.dmd_wc + A.OB_int - A.COLN_int + A.dmd_int) as Total from "
							+ " (select dmd.OFFICE_ID,dmd.beneficiary_sno,"
							+ " coalesce(ob_yester_yr_wc,0)+coalesce(ob_cur_yr_wc,0) +coalesce(ob_maint_charges,0) as OB_WC,"
							+ " coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0)-coalesce(add_charges_wc,0)+coalesce(minus_charges_wc,0)"
							+ " -coalesce(add_charges_maint,0)+coalesce(minus_charges_maint,0) as COLN_WC,"
							+ " coalesce(wc_mth_total,0) as DMD_WC," + " coalesce(ob_int_amt_wc,0) as OB_INT,"
							+ " coalesce(coln_int_wc,0)+coalesce(minus_charges_int,0)-coalesce(add_charges_int,0) as COLN_INT,"
							+ " coalesce(dmd_int_for_mth_wc,0) as DMD_INT" + " from pms_dcb_trn_bill_dmd dmd "
							+ " where bill_year=" + year1 + " and bill_month=" + month1 + ") A "
							+ " left join pms_dcb_mst_beneficiary B on B.beneficiary_sno = A.beneficiary_sno"
							+ " LEFT JOIN COM_MST_DISTRICTS E ON E.DISTRICT_CODE = B.DISTRICT_CODE"
							+ " LEFT JOIN region_cir_div_view F ON F.DIDID = A.OFFICE_ID"
							+ " WHERE B.BENEFICIARY_TYPE_ID_SUB IN (1) "+off_cond+" "
							+ " group by E.DISTRICT_CODE, E.DISTRICT_NAME, B.ADDED_TO_BEN_SNO"
							+ " ORDER BY E.DISTRICT_NAME, B.ADDED_TO_BEN_SNO) Z LEFT JOIN pms_dcb_mst_beneficiary y ON y.BENEFICIARY_SNO = z.BENEFICIARY_SNO) as opt1 ;";

					PreparedStatement ps = con.prepareStatement(qry);

					System.out.println(qry);
					ResultSet rs = ps.executeQuery();

					if(option.equalsIgnoreCase("2")) {
						flag = 1;
						generateExcelFile(rs, response, "Corporation");
					}
						
					if(option.equalsIgnoreCase("1")) {
					ctxpath = request.getRequestURL().toString();

					parameters.put("year", year1);
					parameters.put("month", month1);
					parameters.put("bentype","CORPORATION" );


					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Corporation_month_end_charges.jasper");
					response.setHeader("Content-Disposition", "attachment; filename=\"Corporation.pdf\"");
					outuputStream2 = response.getOutputStream();
					jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
					};

				}

				else if (bentype != 6 && bentype != 0) {
					String cond = "";
					if (bentype == 7) {
						cond = "7,8,9,10,11,12,13,14,15,16,17,18";
					} else if (bentype == 2) {
						cond = "2,3";
					} else if (bentype == 60) {
						cond = "6";
					} 
					else {
						cond = "4";
					}

					switch (bentype) {
					case 4:
						filename = "TOWNPANCHAYAT";
						break;
					case 2:
						filename = "MUNICIPALITY";
						break;
					case 60:
						filename = "VILLAGE_PANCHAYAT_DETAILED";
						break;
					case 7:
						filename = "OTHERS";
						break;
					}

					qry = "  select A.office_id,E.DISTRICT_CODE,E.DISTRICT_NAME,A.BENEFICIARY_SNO,b.beneficiary_name, A.OB_WC , A.COLN_WC as COLN_WC, A.OB_WC - A.COLN_WC as balance_wc, A.dmd_wc AS DMD_WC, "
							+ " A.OB_WC - A.COLN_WC+ A.dmd_wc as Total_wc,"
							+ " A.OB_int , A.COLN_int , A.OB_int - A.COLN_int as balance_int,A.dmd_int , A.OB_int - A.COLN_int + A.dmd_int as Total_int,"
							+ " A.OB_WC - A.COLN_WC + A.dmd_wc + A.OB_int - A.COLN_int + A.dmd_int as Total"
							+ " from (select OFFICE_ID,beneficiary_sno,"
							+ " coalesce(ob_yester_yr_wc,0)+coalesce(ob_cur_yr_wc,0) +coalesce(ob_maint_charges,0) as OB_WC,"
							+ " coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0)-coalesce(add_charges_wc,0)+coalesce(minus_charges_wc,0)"
							+ " -coalesce(add_charges_maint,0)+coalesce(minus_charges_maint,0) as COLN_WC,"
							+ " coalesce(wc_mth_total,0) as DMD_WC," + " coalesce(ob_int_amt_wc,0) as OB_INT,"
							+ " coalesce(coln_int_wc,0)+coalesce(minus_charges_int,0)-coalesce(add_charges_int,0) as COLN_INT,"
							+ " coalesce(dmd_int_for_mth_wc,0) as DMD_INT" + " from pms_dcb_trn_bill_dmd "
							+ " where bill_year=" + year1 + " and bill_month=" + month1 + ") A "
							+ " left join pms_dcb_mst_beneficiary B on B.beneficiary_sno = A.beneficiary_sno"
							+ " LEFT JOIN COM_MST_BLOCKS C ON c.block_sno = b.block_sno"
							+ " LEFT JOIN COM_MST_PANCHAYATS D ON d.panch_SNO = b.village_panchayat_sno"
							+ " LEFT JOIN COM_MST_DISTRICTS E ON E.DISTRICT_CODE = B.DISTRICT_CODE"
							+ " LEFT JOIN region_cir_div_view F ON F.DIDID = A.OFFICE_ID where b.beneficiary_type_id_sub in ("+cond+") "
							+ off_cond 
							+ " ORDER BY E.DISTRICT_NAME, B.BENEFICIARY_NAME";

					PreparedStatement ps = con.prepareStatement(qry);

					System.out.println(qry);
					ResultSet rs = ps.executeQuery();

					if(option.equalsIgnoreCase("2")) {
						flag = 1;
						generateExcelFile(rs, response, filename);
					}
						
					if(option.equalsIgnoreCase("1")) {
					ctxpath = request.getRequestURL().toString();

					
					parameters.put("dynamicQuery", qry);
					parameters.put("bentype", filename);

					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Mun_vp_tp_month_end_charges.jasper");
					response.setHeader("Content-Disposition", "attachment; filename="+filename+".pdf");
					outuputStream2 = response.getOutputStream();
					jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
					};
				}

				else if (bentype == 6) {
					
					filename = "VILLAGEPANCHAYAT";

					qry = " select  district_name,block_name , (ob_wc) as ob_wc, coln_wc ,  dmd_wc, total_wc, ob_int, coln_int,dmd_int,  total_int, "
							+ " coalesce(ob_wc,0) + coalesce(ob_int,0) as ob,  coalesce(coln_wc,0) + coalesce(coln_int,0) as coln, "
							+ " coalesce(dmd_wc,0) + coalesce(dmd_int,0) as dmd , total"
							+ " from (select E.DISTRICT_CODE,E.DISTRICT_NAME,C.BLOCK_SNO,C.BLOCK_NAME, SUM(A.OB_WC) AS OB_WC , SUM(A.COLN_WC) AS COLN_WC , SUM(A.OB_WC - A.COLN_WC) as balance_wc, SUM(A.dmd_wc) DMD_WC, "
							+ " SUM(A.OB_WC - A.COLN_WC+ A.dmd_wc) as Total_wc,"
							+ " SUM(A.OB_int) OB_INT, SUM(A.COLN_int) COLN_INT, SUM(A.OB_int - A.COLN_int) as balance_int, SUM(A.dmd_int) DMD_INT, SUM(A.OB_int - A.COLN_int + A.dmd_int) as Total_int,"
							+ " SUM(A.OB_WC - A.COLN_WC + A.dmd_wc + A.OB_int - A.COLN_int + A.dmd_int) as Total"
							+ " from (select OFFICE_ID,beneficiary_sno,"
							+ " coalesce(ob_yester_yr_wc,0)+coalesce(ob_cur_yr_wc,0) +coalesce(ob_maint_charges,0) as OB_WC,"
							+ " coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0)-coalesce(add_charges_wc,0)+coalesce(minus_charges_wc,0)"
							+ " -coalesce(add_charges_maint,0)+coalesce(minus_charges_maint,0) as COLN_WC,"
							+ " coalesce(wc_mth_total,0) as DMD_WC," + " coalesce(ob_int_amt_wc,0) as OB_INT,"
							+ " coalesce(coln_int_wc,0)+coalesce(minus_charges_int,0)-coalesce(add_charges_int,0) as COLN_INT,"
							+ " coalesce(dmd_int_for_mth_wc,0) as DMD_INT" + " from pms_dcb_trn_bill_dmd "
							+ " where bill_year=" + year1 + " and bill_month=" + month1 + "" + " ) A "
							+ " left join pms_dcb_mst_beneficiary B on B.beneficiary_sno = A.beneficiary_sno"
							+ " LEFT JOIN COM_MST_BLOCKS C ON c.block_sno = b.block_sno"
							+ " LEFT JOIN COM_MST_PANCHAYATS D ON d.panch_SNO = b.village_panchayat_sno"
							+ " LEFT JOIN COM_MST_DISTRICTS E ON E.DISTRICT_CODE = B.DISTRICT_CODE"
							+ " LEFT JOIN region_cir_div_view F ON F.DIDID = A.OFFICE_ID"
							+ " WHERE B.BENEFICIARY_TYPE_ID_SUB = 6 "
							+ off_cond+ " "
							+ " group by   C.BLOCK_SNO, E.DISTRICT_CODE, C.BLOCK_NAME, E.DISTRICT_NAME	"
							+ " ORDER BY E.DISTRICT_NAME, C.BLOCK_NAME) as opt1";

					PreparedStatement ps = con.prepareStatement(qry);

					System.out.println(qry);
					ResultSet rs = ps.executeQuery();
					
					if(option.equalsIgnoreCase("2")) {
						flag = 1;
						generateExcelFile(rs, response, filename);
					}
						
					if(option.equalsIgnoreCase("1")) {
					ctxpath = request.getRequestURL().toString();

					
					parameters.put("year", year1);
					parameters.put("month", month1);

					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/vp_month_end_charges.jasper");
					response.setHeader("Content-Disposition", "attachment; filename="+filename+".pdf");
					outuputStream2 = response.getOutputStream();
					jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
					};

				}

			}
			
			else if (command.trim().equalsIgnoreCase("monthEndAbstract")) {
				System.out.println("Inside month End Water charges Abstract");
				option = obj.setValue("option", request);
				int year1 = Integer.parseInt(obj.setValue("year", request));
				int month1 = Integer.parseInt(obj.setValue("month", request));
				filename = "Month_End_Abstract";
				
				qry =  	  " 	SELECT BENEFICIARY_TYPE , SUM(OB_WC) As OB_WC, SUM(COLN_WC) as COLN_WC, SUM(balance_wc) AS balance_wc, SUM(dmd_wc) AS dmd_wc, SUM(Total_wc) AS Total_wc,"
						+ "		SUM(OB_int) AS OB_int, SUM(COLN_INT) as COLN_INT, SUM(balance_INT) AS balance_INT, SUM(dmd_INT) AS dmd_INT, SUM(Total_INT) AS Total_INT,"
						+ "		SUM(TOTAL) AS TOTAL FROM ("
						+ "		SELECT "
						+ "		CASE "
						+ "	    WHEN BEN_TYPE_ID = 1 THEN 'CORPORATION' "
						+ "		WHEN BEN_TYPE_ID IN (2,3) THEN 'MUNICIPALITY'"
						+ "		WHEN BEN_TYPE_ID = 4 THEN 'TOWN PANCHAYAT'"
						+ "		WHEN BEN_TYPE_ID = 6 THEN 'VILLAGE PANCHAYAT'"
						+ "		ELSE 'OTHERS'"
						+ "		END AS BENEFICIARY_TYPE,"
						+ "		SUM(A.OB_WC) AS OB_WC , SUM(A.COLN_WC) as COLN_WC, SUM(A.OB_WC - A.COLN_WC) as balance_wc, SUM(A.dmd_wc) AS DMD_WC, "
						+ "	    SUM(A.OB_WC - A.COLN_WC + A.dmd_wc) as Total_wc,"
						+ "		SUM(A.OB_int) AS OB_INT , SUM(A.COLN_int) AS COLN_INT , SUM(A.OB_int - A.COLN_int) as balance_int,SUM(A.dmd_int) AS DMD_INT , SUM(A.OB_int - A.COLN_int + A.dmd_int) as Total_int,"
						+ "	    SUM(A.OB_WC - A.COLN_WC + A.dmd_wc + A.OB_int - A.COLN_int + A.dmd_int)  as Total"
						+ "	    from (select OFFICE_ID,beneficiary_sno,"
						+ "	    coalesce(ob_yester_yr_wc,0)+coalesce(ob_cur_yr_wc,0) +coalesce(ob_maint_charges,0) as OB_WC,"
						+ "     coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0)-coalesce(add_charges_wc,0)+coalesce(minus_charges_wc,0)"
						+ "	    -coalesce(add_charges_maint,0)+coalesce(minus_charges_maint,0) as COLN_WC,"
						+ "	    coalesce(wc_mth_total,0) as DMD_WC,   coalesce(ob_int_amt_wc,0) as OB_INT,"
						+ "	    coalesce(coln_int_wc,0)+coalesce(minus_charges_int,0)-coalesce(add_charges_int,0) as COLN_INT,"
						+ "	    coalesce(dmd_int_for_mth_wc,0) as DMD_INT   from pms_dcb_trn_bill_dmd "
						+ "	    where bill_year="+year1+" and bill_month= "+month1+" ) A "
						+ "	    left join pms_dcb_mst_beneficiary B on B.beneficiary_sno = A.beneficiary_sno"
						+ "	    LEFT JOIN COM_MST_BLOCKS C ON c.block_sno = b.block_sno"
						+ "	    LEFT JOIN COM_MST_PANCHAYATS D ON d.panch_SNO = b.village_panchayat_sno"
						+ "	    LEFT JOIN COM_MST_DISTRICTS E ON E.DISTRICT_CODE = B.DISTRICT_CODE"
						+ "		LEFT JOIN PMS_DCB_BEN_TYPE TYP ON TYP.BEN_TYPE_ID = B.BENEFICIARY_TYPE_ID_SUB "
						+ "	    LEFT JOIN region_cir_div_view F ON F.DIDID = A.OFFICE_ID"
						+ "		GROUP BY  TYP.BEN_TYPE_ID) OPT1 GROUP BY  BENEFICIARY_TYPE ";
				

								PreparedStatement ps = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

								
								System.out.println(qry);
								ResultSet rs = ps.executeQuery();
								
								
							
				
								if(option.equalsIgnoreCase("2")) {
									flag = 1;
									
									String monthName = MonthConverter.monthNumberToName(month1);
									
									generateExcelFile(rs, response, filename, "BENEFICIARY TYPEWISE ABSTRACT FOR "+ monthName+ " " +year1);
								}
								
								if(option.equalsIgnoreCase("1")) {
									ctxpath = request.getRequestURL().toString();

									
									parameters.put("year1", year1);
									parameters.put("month1", month1);
									parameters.put("bentype", "ABSTRACT");


									path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Water_Charges_MonthEnd_Abstract.jasper");
									response.setHeader("Content-Disposition", "attachment; filename="+filename+".pdf");
									outuputStream2 = response.getOutputStream();
									jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
									};
				
			}
			
			
			
			
			else if (command.trim().equalsIgnoreCase("DmdColn")) {
				
				int year1, year2, month1 ,month2;
				System.out.println("InsideDmd Coln ");
				option = obj.setValue("option", request);
					
				String rep = obj.setValue("reporttype", request);
				 year1 = Integer.parseInt(obj.setValue("year", request));
				 month1 = Integer.parseInt(obj.setValue("month", request));
				 filename = "DemandCollection";
				
				if(month1 == 12) {
					year2 = year1+1;
					month2 = 1;
				}else {
					year2 = year1;
					month2 = month1+1;
				}
				
				LocalDate currentDate = LocalDate.now();
				int currentYear = currentDate.getYear();
				int currentMonth = currentDate.getMonthValue();

				YearMonth endYearMonth = YearMonth.of(currentYear, currentMonth);
				YearMonth startYearMonth = YearMonth.of(year1, month1);

				long monthsDifference = ChronoUnit.MONTHS.between(startYearMonth, endYearMonth);

				System.out.println(monthsDifference);
				
				
				if (monthsDifference >= 2) {
					
					int freezedCount = 0;
					String freezeCountQry = "SELECT COUNT(*) FROM ("
							+ " SELECT DISTINCT(OFFICE_ID) FROM PMS_DCB_TRN_BILL_DMD WHERE BILL_YEAR= " + year1
							+ " AND BILL_MONTH = " + month1 + ""
							+ " AND OFFICE_ID NOT IN (SELECT office_ID FROM pms_dcb_data_freeze WHERE cashbook_year="
							+ year2 + " AND cashbook_month=" + month2 + "" + " )) AS OPT1;";
					
					
					PreparedStatement ps = con.prepareStatement(freezeCountQry);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						freezedCount = rs.getInt(1);
					}
					
					if (freezedCount == 0) {
						
						System.out.print("From dmd table ");

//						if(true) {
						qry =   " select reg.reg as region_name, reg.cir as circle_name, dmd.office_id as office_id, reg.div as division_name, "+year2+" as bill_year, "+month2+" as bill_month, dmd.beneficiary_sno as beneficiary_code, ben.beneficiary_name,dmd.dmd_wc as Current_month_dmd_wc, coln.coln_wc as Current_Month_Coln_wc, dmd.dmd_int as current_month_dmd_int,"
								+ " coln.coln_int as current_month_coln_int"
								+ " from ("
								+ " select office_id, beneficiary_sno, beneficiary_type_id_sub, sum(dmd_wc) as dmd_wc, sum(dmd_int) as dmd_int  from"
								+ "	(select "
								+ " dmd1. office_id,"
								+ " ben1.beneficiary_type_id_sub,"
								+ " case"
								+ "		when ben1.added_to_ben_sno is null then ben1.beneficiary_sno"
								+ "		else ben1.added_to_ben_sno END"
								+ " beneficiary_sno,"
								+ " wc_mth_total as dmd_wc, dmd_int_for_mth_wc dmd_int "
								+ " from pms_dcb_trn_bill_dmd dmd1 left join pms_dcb_mst_beneficiary ben1 "
								+ " on ben1.beneficiary_sno = dmd1.beneficiary_sno"
								+ " where dmd1.bill_year="+year1+" and dmd1.bill_month = "+month1+" ) opt1"
								+ " group by office_id, beneficiary_sno, beneficiary_type_id_sub) dmd left join "
								+ " ("
								+ " select office_id, beneficiary_sno, beneficiary_type_id_sub, sum(coln_wc) as coln_wc, sum(coln_int) as coln_int  from"
								+ "	(select "
								+ " dmd1. office_id,"
								+ " ben1.beneficiary_type_id_sub,"
								+ " case"
								+ "		when ben1.added_to_ben_sno is null then ben1.beneficiary_sno"
								+ "		else ben1.added_to_ben_sno END"
								+ " beneficiary_sno,"
								+ " (coalesce(coln_cur_yr_wc,0)+coalesce(coln_yester_yr_wc,0)+coalesce(coln_maint,0)-coalesce(add_charges_wc,0)+coalesce(minus_charges_wc,0)-coalesce(add_charges_maint,0)+coalesce(minus_charges_maint)) as coln_wc ,"
								+ " (coalesce(coln_int_wc,0)-coalesce(add_charges_int,0)+coalesce(minus_charges_int,0)) as coln_int "
								+ " from pms_dcb_trn_bill_dmd dmd1 left join pms_dcb_mst_beneficiary ben1 "
								+ " on ben1.beneficiary_sno = dmd1.beneficiary_sno"
								+ " where dmd1.bill_year="+year2+" and dmd1.bill_month = "+month2+" ) opt1"
								+ " group by office_id, beneficiary_sno, beneficiary_type_id_sub) coln "
								+ " on coln.office_id = dmd.office_id and coln.beneficiary_sno = dmd.beneficiary_sno  "
								+ " left join pms_dcb_ben_type typ on typ.ben_type_id = dmd.beneficiary_type_id_sub"
								+ " left join pms_dcb_mst_beneficiary ben on ben.beneficiary_sno = dmd.beneficiary_sno"
								+ "  left join region_cir_div_view reg on reg.didid = dmd.office_id";
						
						 ps = con.prepareStatement(qry);

						System.out.println(qry);
						 rs = ps.executeQuery();
						
						if(option.equalsIgnoreCase("2")) {
							flag = 1;
							generateExcelFile(rs, response, filename);
						}
						
					}else {
							System.out.print("----------------------------------------Demand Collection From fas coln-------------------------------------------------");
						qry = "SELECT"
								+ "	reg.reg AS region_name,"
								+ "	reg.cir AS circle_name,"
								+ "	dmd.office_id AS office_id,"
								+ "	reg.div AS division_name,"
								+ "	"+year2+" AS bill_year,"
								+ "	"+month2+" AS bill_month,"
								+ "	dmd.beneficiary_sno AS beneficiary_code,"
								+ "	ben.beneficiary_name,"
								+ "	dmd.dmd_wc AS Current_month_dmd_wc,"
								+ "	coln_wc.amount AS Current_Month_Coln_wc,"
								+ "	dmd.dmd_int AS current_month_dmd_int,"
								+ "	coln_int.amount AS current_month_coln_int "
								+ " FROM"
								+ "	("
								+ "	SELECT"
								+ "		office_id,"
								+ "		beneficiary_sno,"
								+ "		beneficiary_type_id_sub,"
								+ "		SUM ( dmd_wc ) AS dmd_wc,"
								+ "		SUM ( dmd_int ) AS dmd_int "
								+ "	FROM"
								+ "		("
								+ "		SELECT"
								+ "			dmd1.office_id,"
								+ "			ben1.beneficiary_type_id_sub,"
								+ "		CASE"
								+ "				"
								+ "				WHEN ben1.added_to_ben_sno IS NULL THEN"
								+ "				ben1.beneficiary_sno ELSE ben1.added_to_ben_sno "
								+ "			END beneficiary_sno,"
								+ "	wc_mth_total AS dmd_wc,"
								+ "	dmd_int_for_mth_wc dmd_int "
								+ "FROM"
								+ "	pms_dcb_trn_bill_dmd dmd1"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
								+ "WHERE"
								+ "	dmd1.bill_year = "+year1+" "
								+ "	AND dmd1.bill_month = "+month1+" "
								+ "	) opt1 "
								+ "GROUP BY"
								+ "	office_id,"
								+ "	beneficiary_sno,"
								+ "	beneficiary_type_id_sub "
								+ "	) dmd"
								+ "	LEFT JOIN ("
								+ "	SELECT"
								+ "		office_id,"
								+ "		beneficiary_sno,"
								+ "		beneficiary_type_id_sub,"
								+ "		SUM ( amount ) AS amount "
								+ "	FROM"
								+ "		("
								+ "		SELECT"
								+ "			total.office_id,"
								+ "			ben.beneficiary_type_id_sub,"
								+ "		CASE"
								+ "				"
								+ "				WHEN ben.added_to_ben_sno IS NOT NULL THEN"
								+ "				added_to_ben_sno ELSE ben.beneficiary_sno "
								+ "			END AS beneficiary_sno,"
								+ "			amount "
								+ "		FROM"
								+ "			("
								+ "			SELECT"
								+ "				* "
								+ "			FROM"
								+ "				("
								+ "				SELECT"
								+ "					office_id,"
								+ "					beneficiary_sno,"
								+ "					SUM ( amount ) AS amount "
								+ "				FROM"
								+ "					("
								+ "					SELECT"
								+ "						pms_dcb_fas_receipt_view.receipt_no,"
								+ "						pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
								+ "						pms_dcb_fas_receipt_view.cashbook_year AS YEAR,"
								+ "						pms_dcb_fas_receipt_view.cashbook_month AS MONTH,"
								+ "						pms_dcb_fas_receipt_view.sub_ledger_type_code,"
								+ "						pms_dcb_fas_receipt_view.sub_ledger_code,"
								+ "						pms_dcb_fas_receipt_view.sch_type_id,"
								+ "						pms_dcb_fas_receipt_view.sch_sno,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_name,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_sno,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
								+ "						pms_dcb_fas_receipt_view.amount "
								+ "					FROM"
								+ "						pms_dcb_fas_receipt_view "
								+ "					WHERE"
								+ "						("
								+ "							pms_dcb_fas_receipt_view.account_head_code = ANY ("
								+ "								ARRAY [ 782401 :: NUMERIC,"
								+ "								782402 :: NUMERIC,"
								+ "								782403 :: NUMERIC,"
								+ "								782404 :: NUMERIC,"
								+ "								782405 :: NUMERIC,"
								+ "								782406 :: NUMERIC,"
								+ "								782407 :: NUMERIC,"
								+ "								900108 :: NUMERIC,"
								+ "								900109 :: NUMERIC ] "
								+ "							) "
								+ "						) "
								+ "						AND cashbook_year = "+year2+ ""
								+ "						AND cashbook_month = "+month2+" "
								+ "					) opt1 "
								+ "				GROUP BY"
								+ "					office_id,"
								+ "					beneficiary_sno "
								+ "				) receipt UNION ALL"
								+ "			SELECT"
								+ "				* "
								+ "			FROM"
								+ "				("
								+ "				SELECT"
								+ "					opt4.office_id :: NUMERIC AS office_id,"
								+ "					opt4.beneficiary_sno,"
								+ "					SUM ( opt4.amount ) AS amount "
								+ "				FROM"
								+ "					("
								+ "					SELECT"
								+ "						pms_dcb_other_charges.office_id,"
								+ "						pms_dcb_other_charges.beneficiary_sno,"
								+ "						pms_dcb_other_charges.cashbook_year AS YEAR,"
								+ "						pms_dcb_other_charges.cashbook_month AS MONTH,"
								+ "						SUM ("
								+ "						COALESCE ( CASE WHEN pms_dcb_other_charges.cr_dr_indicator :: TEXT = 'CR' :: TEXT THEN pms_dcb_other_charges.amount ELSE NULL :: NUMERIC END, 0 :: NUMERIC ) "
								+ "		) AS amount "
								+ "	FROM"
								+ "		pms_dcb_other_charges "
								+ "	WHERE"
								+ "		( pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 7 :: NUMERIC ) ) "
								+ "	GROUP BY"
								+ "		pms_dcb_other_charges.office_id,"
								+ "		pms_dcb_other_charges.beneficiary_sno,"
								+ "		pms_dcb_other_charges.cashbook_year,"
								+ "		pms_dcb_other_charges.cashbook_month "
								+ "	ORDER BY"
								+ "		pms_dcb_other_charges.office_id,"
								+ "		pms_dcb_other_charges.beneficiary_sno "
								+ "	) AS opt4 "
								+ "WHERE"
								+ "	opt4.YEAR = "+year2+" "
								+ "	AND opt4.MONTH = "+month2+" "
								+ "GROUP BY"
								+ "	opt4.office_id,"
								+ "	opt4.beneficiary_sno "
								+ "	) adj "
								+ "	) total"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
								+ "	) AS opt1 "
								+ "GROUP BY"
								+ "	office_id,"
								+ "	beneficiary_sno,"
								+ "	beneficiary_type_id_sub "
								+ "ORDER BY"
								+ "	office_id,"
								+ "	beneficiary_type_id_sub,"
								+ "	amount "
								+ "	) coln_wc ON coln_wc.office_id = dmd.office_id "
								+ "	AND coln_wc.beneficiary_sno = dmd.beneficiary_sno"
								+ "	left join "
								+ "	("
								+ "	"
								+ "	SELECT"
								+ "		office_id,"
								+ "		beneficiary_sno,"
								+ "		beneficiary_type_id_sub,"
								+ "		SUM ( amount ) AS amount "
								+ "	FROM"
								+ "		("
								+ "		SELECT"
								+ "			total.office_id,"
								+ "			ben.beneficiary_type_id_sub,"
								+ "		CASE"
								+ "				"
								+ "				WHEN ben.added_to_ben_sno IS NOT NULL THEN"
								+ "				added_to_ben_sno ELSE ben.beneficiary_sno "
								+ "			END AS beneficiary_sno,"
								+ "			amount "
								+ "		FROM"
								+ "			("
								+ "			SELECT"
								+ "				* "
								+ "			FROM"
								+ "				("
								+ "				SELECT"
								+ "					office_id,"
								+ "					beneficiary_sno,"
								+ "					SUM ( amount ) AS amount "
								+ "				FROM"
								+ "					("
								+ "					SELECT"
								+ "						pms_dcb_fas_receipt_view.receipt_no,"
								+ "						pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
								+ "						pms_dcb_fas_receipt_view.cashbook_year AS YEAR,"
								+ "						pms_dcb_fas_receipt_view.cashbook_month AS MONTH,"
								+ "						pms_dcb_fas_receipt_view.sub_ledger_type_code,"
								+ "						pms_dcb_fas_receipt_view.sub_ledger_code,"
								+ "						pms_dcb_fas_receipt_view.sch_type_id,"
								+ "						pms_dcb_fas_receipt_view.sch_sno,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_name,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_sno,"
								+ "						pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
								+ "						pms_dcb_fas_receipt_view.amount "
								+ "					FROM"
								+ "						pms_dcb_fas_receipt_view "
								+ "					WHERE"
								+ "						( pms_dcb_fas_receipt_view.account_head_code = ANY ( ARRAY [ 120601 :: NUMERIC ] ) ) "
								+ "						AND cashbook_year = "+year2+" :: NUMERIC "
								+ "						AND cashbook_month = "+month2+" :: NUMERIC "
								+ "					) opt1 "
								+ "				GROUP BY"
								+ "					office_id,"
								+ "					beneficiary_sno "
								+ "				) receipt UNION ALL"
								+ "			SELECT"
								+ "				* "
								+ "			FROM"
								+ "				("
								+ "				SELECT"
								+ "					opt4.office_id :: NUMERIC AS office_id,"
								+ "					opt4.beneficiary_sno,"
								+ "					SUM ( opt4.amount ) AS amount "
								+ "				FROM"
								+ "					("
								+ "					SELECT"
								+ "						pms_dcb_other_charges.office_id,"
								+ "						pms_dcb_other_charges.beneficiary_sno,"
								+ "						pms_dcb_other_charges.cashbook_year AS YEAR,"
								+ "						pms_dcb_other_charges.cashbook_month AS MONTH,"
								+ "						SUM ("
								+ "						COALESCE ( CASE WHEN pms_dcb_other_charges.cr_dr_indicator :: TEXT = 'CR' :: TEXT THEN pms_dcb_other_charges.amount ELSE NULL :: NUMERIC END, 0 :: NUMERIC ) "
								+ "		) AS amount "
								+ "	FROM"
								+ "		pms_dcb_other_charges "
								+ "	WHERE"
								+ "		( pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 9 :: NUMERIC ) ) "
								+ "	GROUP BY"
								+ "		pms_dcb_other_charges.office_id,"
								+ "		pms_dcb_other_charges.beneficiary_sno,"
								+ "		pms_dcb_other_charges.cashbook_year,"
								+ "		pms_dcb_other_charges.cashbook_month "
								+ "	ORDER BY"
								+ "		pms_dcb_other_charges.office_id,"
								+ "		pms_dcb_other_charges.beneficiary_sno "
								+ "	) AS opt4 "
								+ "WHERE"
								+ "	opt4.YEAR = "+year2+" :: NUMERIC "
								+ "	AND opt4.MONTH = "+month2+" :: NUMERIC "
								+ "GROUP BY"
								+ "	opt4.office_id,"
								+ "	opt4.beneficiary_sno "
								+ "	) adj "
								+ "	) total"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
								+ "	) AS opt1 "
								+ "GROUP BY"
								+ "	office_id,"
								+ "	beneficiary_sno,"
								+ "	beneficiary_type_id_sub "
								+ "ORDER BY"
								+ "	office_id,"
								+ "	beneficiary_type_id_sub,"
								+ "	amount "
								+ "	"
								+ "	) coln_int on coln_int.beneficiary_sno = dmd.beneficiary_sno and coln_int.office_id = dmd.office_id"
								+ "	LEFT JOIN pms_dcb_ben_type typ ON typ.ben_type_id = dmd.beneficiary_type_id_sub"
								+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = dmd.beneficiary_sno"
								+ "	LEFT JOIN region_cir_div_view reg ON reg.didid = dmd.office_id";
						
						System.out.println(qry);
						 rs = ps.executeQuery();
						
						if(option.equalsIgnoreCase("2")) {
							flag = 1;
							generateExcelFile(rs, response, filename);
						}
					}
					
				}
				
				else if (monthsDifference == 1) {
					
					System.out.print("----------------------------------------DMD COLN FROM FAS");
					
					qry = "SELECT"
							+ "	reg.reg AS region_name,"
							+ "	reg.cir AS circle_name,"
							+ "	dmd.office_id AS office_id,"
							+ "	reg.div AS division_name,"
							+ "	"+year2+" AS bill_year,"
							+ "	"+month2+" AS bill_month,"
							+ "	dmd.beneficiary_sno AS beneficiary_code,"
							+ "	ben.beneficiary_name,"
							+ "	dmd.dmd_wc AS Current_month_dmd_wc,"
							+ "	coln_wc.amount AS Current_Month_Coln_wc,"
							+ "	dmd.dmd_int AS current_month_dmd_int,"
							+ "	coln_int.amount AS current_month_coln_int "
							+ " FROM"
							+ "	("
							+ "	SELECT"
							+ "		office_id,"
							+ "		beneficiary_sno,"
							+ "		beneficiary_type_id_sub,"
							+ "		SUM ( dmd_wc ) AS dmd_wc,"
							+ "		SUM ( dmd_int ) AS dmd_int "
							+ "	FROM"
							+ "		("
							+ "		SELECT"
							+ "			dmd1.office_id,"
							+ "			ben1.beneficiary_type_id_sub,"
							+ "		CASE"
							+ "				"
							+ "				WHEN ben1.added_to_ben_sno IS NULL THEN"
							+ "				ben1.beneficiary_sno ELSE ben1.added_to_ben_sno "
							+ "			END beneficiary_sno,"
							+ "	wc_mth_total AS dmd_wc,"
							+ "	dmd_int_for_mth_wc dmd_int "
							+ "FROM"
							+ "	pms_dcb_trn_bill_dmd dmd1"
							+ "	LEFT JOIN pms_dcb_mst_beneficiary ben1 ON ben1.beneficiary_sno = dmd1.beneficiary_sno "
							+ "WHERE"
							+ "	dmd1.bill_year = 2023 "
							+ "	AND dmd1.bill_month = 1 "
							+ "	) opt1 "
							+ "GROUP BY"
							+ "	office_id,"
							+ "	beneficiary_sno,"
							+ "	beneficiary_type_id_sub "
							+ "	) dmd"
							+ "	LEFT JOIN ("
							+ "	SELECT"
							+ "		office_id,"
							+ "		beneficiary_sno,"
							+ "		beneficiary_type_id_sub,"
							+ "		SUM ( amount ) AS amount "
							+ "	FROM"
							+ "		("
							+ "		SELECT"
							+ "			total.office_id,"
							+ "			ben.beneficiary_type_id_sub,"
							+ "		CASE"
							+ "				"
							+ "				WHEN ben.added_to_ben_sno IS NOT NULL THEN"
							+ "				added_to_ben_sno ELSE ben.beneficiary_sno "
							+ "			END AS beneficiary_sno,"
							+ "			amount "
							+ "		FROM"
							+ "			("
							+ "			SELECT"
							+ "				* "
							+ "			FROM"
							+ "				("
							+ "				SELECT"
							+ "					office_id,"
							+ "					beneficiary_sno,"
							+ "					SUM ( amount ) AS amount "
							+ "				FROM"
							+ "					("
							+ "					SELECT"
							+ "						pms_dcb_fas_receipt_view.receipt_no,"
							+ "						pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
							+ "						pms_dcb_fas_receipt_view.cashbook_year AS YEAR,"
							+ "						pms_dcb_fas_receipt_view.cashbook_month AS MONTH,"
							+ "						pms_dcb_fas_receipt_view.sub_ledger_type_code,"
							+ "						pms_dcb_fas_receipt_view.sub_ledger_code,"
							+ "						pms_dcb_fas_receipt_view.sch_type_id,"
							+ "						pms_dcb_fas_receipt_view.sch_sno,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_name,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_sno,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
							+ "						pms_dcb_fas_receipt_view.amount "
							+ "					FROM"
							+ "						pms_dcb_fas_receipt_view "
							+ "					WHERE"
							+ "						("
							+ "							pms_dcb_fas_receipt_view.account_head_code = ANY ("
							+ "								ARRAY [ 782401 :: NUMERIC,"
							+ "								782402 :: NUMERIC,"
							+ "								782403 :: NUMERIC,"
							+ "								782404 :: NUMERIC,"
							+ "								782405 :: NUMERIC,"
							+ "								782406 :: NUMERIC,"
							+ "								782407 :: NUMERIC,"
							+ "								900108 :: NUMERIC,"
							+ "								900109 :: NUMERIC ] "
							+ "							) "
							+ "						) "
							+ "						AND cashbook_year = "+year2+ ""
							+ "						AND cashbook_month = "+month2+" "
							+ "					) opt1 "
							+ "				GROUP BY"
							+ "					office_id,"
							+ "					beneficiary_sno "
							+ "				) receipt UNION ALL"
							+ "			SELECT"
							+ "				* "
							+ "			FROM"
							+ "				("
							+ "				SELECT"
							+ "					opt4.office_id :: NUMERIC AS office_id,"
							+ "					opt4.beneficiary_sno,"
							+ "					SUM ( opt4.amount ) AS amount "
							+ "				FROM"
							+ "					("
							+ "					SELECT"
							+ "						pms_dcb_other_charges.office_id,"
							+ "						pms_dcb_other_charges.beneficiary_sno,"
							+ "						pms_dcb_other_charges.cashbook_year AS YEAR,"
							+ "						pms_dcb_other_charges.cashbook_month AS MONTH,"
							+ "						SUM ("
							+ "						COALESCE ( CASE WHEN pms_dcb_other_charges.cr_dr_indicator :: TEXT = 'CR' :: TEXT THEN pms_dcb_other_charges.amount ELSE NULL :: NUMERIC END, 0 :: NUMERIC ) "
							+ "		) AS amount "
							+ "	FROM"
							+ "		pms_dcb_other_charges "
							+ "	WHERE"
							+ "		( pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 7 :: NUMERIC ) ) "
							+ "	GROUP BY"
							+ "		pms_dcb_other_charges.office_id,"
							+ "		pms_dcb_other_charges.beneficiary_sno,"
							+ "		pms_dcb_other_charges.cashbook_year,"
							+ "		pms_dcb_other_charges.cashbook_month "
							+ "	ORDER BY"
							+ "		pms_dcb_other_charges.office_id,"
							+ "		pms_dcb_other_charges.beneficiary_sno "
							+ "	) AS opt4 "
							+ "WHERE"
							+ "	opt4.YEAR = "+year2+" "
							+ "	AND opt4.MONTH = "+month2+" "
							+ "GROUP BY"
							+ "	opt4.office_id,"
							+ "	opt4.beneficiary_sno "
							+ "	) adj "
							+ "	) total"
							+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
							+ "	) AS opt1 "
							+ "GROUP BY"
							+ "	office_id,"
							+ "	beneficiary_sno,"
							+ "	beneficiary_type_id_sub "
							+ "ORDER BY"
							+ "	office_id,"
							+ "	beneficiary_type_id_sub,"
							+ "	amount "
							+ "	) coln_wc ON coln_wc.office_id = dmd.office_id "
							+ "	AND coln_wc.beneficiary_sno = dmd.beneficiary_sno"
							+ "	left join "
							+ "	("
							+ "	"
							+ "	SELECT"
							+ "		office_id,"
							+ "		beneficiary_sno,"
							+ "		beneficiary_type_id_sub,"
							+ "		SUM ( amount ) AS amount "
							+ "	FROM"
							+ "		("
							+ "		SELECT"
							+ "			total.office_id,"
							+ "			ben.beneficiary_type_id_sub,"
							+ "		CASE"
							+ "				"
							+ "				WHEN ben.added_to_ben_sno IS NOT NULL THEN"
							+ "				added_to_ben_sno ELSE ben.beneficiary_sno "
							+ "			END AS beneficiary_sno,"
							+ "			amount "
							+ "		FROM"
							+ "			("
							+ "			SELECT"
							+ "				* "
							+ "			FROM"
							+ "				("
							+ "				SELECT"
							+ "					office_id,"
							+ "					beneficiary_sno,"
							+ "					SUM ( amount ) AS amount "
							+ "				FROM"
							+ "					("
							+ "					SELECT"
							+ "						pms_dcb_fas_receipt_view.receipt_no,"
							+ "						pms_dcb_fas_receipt_view.accounting_for_office_id AS office_id,"
							+ "						pms_dcb_fas_receipt_view.cashbook_year AS YEAR,"
							+ "						pms_dcb_fas_receipt_view.cashbook_month AS MONTH,"
							+ "						pms_dcb_fas_receipt_view.sub_ledger_type_code,"
							+ "						pms_dcb_fas_receipt_view.sub_ledger_code,"
							+ "						pms_dcb_fas_receipt_view.sch_type_id,"
							+ "						pms_dcb_fas_receipt_view.sch_sno,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_name,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_sno,"
							+ "						pms_dcb_fas_receipt_view.beneficiary_type_id_sub,"
							+ "						pms_dcb_fas_receipt_view.amount "
							+ "					FROM"
							+ "						pms_dcb_fas_receipt_view "
							+ "					WHERE"
							+ "						( pms_dcb_fas_receipt_view.account_head_code = ANY ( ARRAY [ 120601 :: NUMERIC ] ) ) "
							+ "						AND cashbook_year = "+year2+" :: NUMERIC "
							+ "						AND cashbook_month = "+month2+" :: NUMERIC "
							+ "					) opt1 "
							+ "				GROUP BY"
							+ "					office_id,"
							+ "					beneficiary_sno "
							+ "				) receipt UNION ALL"
							+ "			SELECT"
							+ "				* "
							+ "			FROM"
							+ "				("
							+ "				SELECT"
							+ "					opt4.office_id :: NUMERIC AS office_id,"
							+ "					opt4.beneficiary_sno,"
							+ "					SUM ( opt4.amount ) AS amount "
							+ "				FROM"
							+ "					("
							+ "					SELECT"
							+ "						pms_dcb_other_charges.office_id,"
							+ "						pms_dcb_other_charges.beneficiary_sno,"
							+ "						pms_dcb_other_charges.cashbook_year AS YEAR,"
							+ "						pms_dcb_other_charges.cashbook_month AS MONTH,"
							+ "						SUM ("
							+ "						COALESCE ( CASE WHEN pms_dcb_other_charges.cr_dr_indicator :: TEXT = 'CR' :: TEXT THEN pms_dcb_other_charges.amount ELSE NULL :: NUMERIC END, 0 :: NUMERIC ) "
							+ "		) AS amount "
							+ "	FROM"
							+ "		pms_dcb_other_charges "
							+ "	WHERE"
							+ "		( pms_dcb_other_charges.account_head_code IN ( SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 9 :: NUMERIC ) ) "
							+ "	GROUP BY"
							+ "		pms_dcb_other_charges.office_id,"
							+ "		pms_dcb_other_charges.beneficiary_sno,"
							+ "		pms_dcb_other_charges.cashbook_year,"
							+ "		pms_dcb_other_charges.cashbook_month "
							+ "	ORDER BY"
							+ "		pms_dcb_other_charges.office_id,"
							+ "		pms_dcb_other_charges.beneficiary_sno "
							+ "	) AS opt4 "
							+ "WHERE"
							+ "	opt4.YEAR = "+year2+" :: NUMERIC "
							+ "	AND opt4.MONTH = "+month2+" :: NUMERIC "
							+ "GROUP BY"
							+ "	opt4.office_id,"
							+ "	opt4.beneficiary_sno "
							+ "	) adj "
							+ "	) total"
							+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno "
							+ "	) AS opt1 "
							+ "GROUP BY"
							+ "	office_id,"
							+ "	beneficiary_sno,"
							+ "	beneficiary_type_id_sub "
							+ "ORDER BY"
							+ "	office_id,"
							+ "	beneficiary_type_id_sub,"
							+ "	amount "
							+ "	"
							+ "	) coln_int on coln_int.beneficiary_sno = dmd.beneficiary_sno and coln_int.office_id = dmd.office_id"
							+ "	LEFT JOIN pms_dcb_ben_type typ ON typ.ben_type_id = dmd.beneficiary_type_id_sub"
							+ "	LEFT JOIN pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = dmd.beneficiary_sno"
							+ "	LEFT JOIN region_cir_div_view reg ON reg.didid = dmd.office_id";
					
					 PreparedStatement ps = con.prepareStatement(qry);

						System.out.println(qry);
						 ResultSet rs = ps.executeQuery();
						
						if(option.equalsIgnoreCase("2")) {
							flag = 1;
							generateExcelFile(rs, response, filename);
						}
				}
			
				else {
			        request.setAttribute("attributeName", "Not freezed");
					  RequestDispatcher dispatcher = request.getRequestDispatcher("/message.jsp");
				        dispatcher.forward(request, response);

				}
				
				
				
			}

			
			else if (command.trim().equalsIgnoreCase("CurColn")) {
				
				int wc_coln = 0;
				int int_coln = 0;
				 int year1 = Integer.parseInt(obj.setValue("year", request));
				 int month1 = Integer.parseInt(obj.setValue("month", request));
				 
			
				 
				 			qry = 	" select coln_wc, coln_int from (select 'wc' as type, sum(amount) as coln_wc  from ("
				 					+ "	WITH total_receipts AS ("
				 					+ "    SELECT"
				 					+ "        accounting_for_office_id as office_id ,"
				 					+ "        beneficiary_sno,"
				 					+ "        SUM(amount) AS amount"
				 					+ "    FROM"
				 					+ "        pms_dcb_fas_receipt_view"
				 					+ "    WHERE"
				 					+ "        pms_dcb_fas_receipt_view.account_head_code = ANY(ARRAY[782401, 782402, 782403, 782404, 782405, 782406, 782407, 900108, 900109]) "
				 					+ "        AND cashbook_year = "+year1+""
				 					+ "        AND cashbook_month = "+month1+""			 		
				 					+ "    GROUP BY"
				 					+ "        accounting_for_office_id,"
				 					+ "        beneficiary_sno"
				 					+ " ),"
				 					+ "total_other_charges AS ("
				 					+ "    SELECT"
				 					+ "        pms_dcb_other_charges.office_id::numeric,"
				 					+ "        pms_dcb_other_charges.beneficiary_sno,"
				 					+ "        SUM(COALESCE(CASE WHEN pms_dcb_other_charges.cr_dr_indicator = 'CR' THEN pms_dcb_other_charges.amount ELSE 0 END, 0)) AS amount"
				 					+ "    FROM"
				 					+ "        pms_dcb_other_charges"
				 					+ "    WHERE"
				 					+ "        pms_dcb_other_charges.account_head_code IN (SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 7)"
				 					+ "        AND pms_dcb_other_charges.cashbook_year = "+year1+""
				 					+ "        AND pms_dcb_other_charges.cashbook_month = "+month1+""				 				
				 					+ "    GROUP BY"
				 					+ "        pms_dcb_other_charges.office_id,"
				 					+ "        pms_dcb_other_charges.beneficiary_sno"
				 					+ " ) "
				 					+ " SELECT"
				 					+ "    total.office_id,"
				 					+ "    COALESCE(ben.beneficiary_sno, total.beneficiary_sno) AS beneficiary_sno,"
				 					+ "    SUM(total.amount) AS amount"
				 					+ " FROM"
				 					+ "    (SELECT * FROM total_receipts"
				 					+ "     UNION ALL"
				 					+ "     SELECT * FROM total_other_charges) AS total"
				 					+ " LEFT JOIN"
				 					+ "    pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno"
				 					+ " GROUP BY"
				 					+ "    total.office_id,"
				 					+ "    COALESCE(ben.beneficiary_sno, total.beneficiary_sno)"
				 					+ " ORDER BY"
				 					+ "    total.office_id,"
				 					+ "    amount"
				 					+ "		) as opt1"
				 					+ "		"
				 					+ "	) A left join "				 			
				 					+ " ( "
				 					+ "		select 'int' as type, sum(amount) as coln_int  from ("
				 					+ "	WITH total_receipts AS ("
				 					+ "    SELECT"
				 					+ "        accounting_for_office_id as office_id ,"
				 					+ "        beneficiary_sno,"
				 					+ "        SUM(amount) AS amount"
				 					+ "    FROM"
				 					+ "        pms_dcb_fas_receipt_view"
				 					+ "    WHERE"
				 					+ "        pms_dcb_fas_receipt_view.account_head_code = ANY(ARRAY[120601]) "
				 					+ "        AND cashbook_year = "+year1+""
				 					+ "        AND cashbook_month = "+month1+""				 				
				 					+ "    GROUP BY"
				 					+ "        accounting_for_office_id,"
				 					+ "        beneficiary_sno"
				 					+ " ),"
				 					+ " total_other_charges AS ("
				 					+ "    SELECT"
				 					+ "        pms_dcb_other_charges.office_id::numeric,"
				 					+ "        pms_dcb_other_charges.beneficiary_sno,"
				 					+ "        SUM(COALESCE(CASE WHEN pms_dcb_other_charges.cr_dr_indicator = 'CR' THEN pms_dcb_other_charges.amount ELSE 0 END, 0)) AS amount"
				 					+ "    FROM"
				 					+ "        pms_dcb_other_charges"
				 					+ "    WHERE"
				 					+ "        pms_dcb_other_charges.account_head_code IN (SELECT DISTINCT pms_dcb_receipt_account_map.account_head_code FROM pms_dcb_receipt_account_map WHERE pms_dcb_receipt_account_map.collection_type = 9)"
				 					+ "        AND pms_dcb_other_charges.cashbook_year = "+year1+""
				 					+ "        AND pms_dcb_other_charges.cashbook_month = "+month1+""				 					
				 					+ "    GROUP BY"
				 					+ "        pms_dcb_other_charges.office_id,"
				 					+ "        pms_dcb_other_charges.beneficiary_sno"
				 					+ " )"
				 					+ " SELECT"
				 					+ "    total.office_id,"
				 					+ "    COALESCE(ben.beneficiary_sno, total.beneficiary_sno) AS beneficiary_sno,"
				 					+ "    SUM(total.amount) AS amount"
				 					+ " FROM"
				 					+ "    (SELECT * FROM total_receipts"
				 					+ "     UNION ALL"
				 					+ "     SELECT * FROM total_other_charges) AS total"
				 					+ " LEFT JOIN"
				 					+ "    pms_dcb_mst_beneficiary ben ON ben.beneficiary_sno = total.beneficiary_sno"
				 					+ " GROUP BY"
				 					+ "    total.office_id,"
				 					+ "    COALESCE(ben.beneficiary_sno, total.beneficiary_sno)"
				 					+ " ORDER BY"
				 					+ "    total.office_id,"
				 					+ "    amount"
				 					+ "		) as opt1"
				 					+ "		"
				 					+ "		) B on 1=1 ";
				 				
				 
				 
								PreparedStatement ps = con.prepareStatement(qry); 
								ResultSet rs = ps.executeQuery();
				 
								while(rs.next()) {
									int_coln = rs.getInt("coln_int");
									wc_coln = rs.getInt("coln_wc");
								}
				  
				 
				 System.out.println("wc_coln   -> " + wc_coln);
				 System.out.println("int_coln   -> " + int_coln);

				 flag = 1;
				 
				 
				 qry = " select "+wc_coln+" as WC_COLN , "+int_coln+" as INT_COLN";
				 ps = con.prepareStatement(qry); 
					rs = ps.executeQuery();
					
					flag = 1;
					generateExcelFile(rs, response, "CURRENT_MONTH_COLLECTION");
					
					
					
				
			}
			
			else if (command.trim().equalsIgnoreCase("Scheme")) {

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));

				int office_id = Integer.parseInt(obj.setValue("report_id", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);

				System.out.println("office_id is  " + office_id);

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);

				// parameters.put("Flag", "N"); // ok DCB_SCHEMEWISE_DETAIL_060920221.jrxml

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/Ledger_Data_Report_30082022.jasper");
				path = getServletContext()
						.getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_SCHEMEWISE_DETAIL_090920221A.jasper");

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_STATEMENT_01092022.jasper");

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/Fas_credit_advise.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			// SCHEME WISE OVER

			// DCB_REP91 Starts

			else if (command.trim().equalsIgnoreCase("DCB_REP91")) {
				option = "2";
				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));

				int office_id = Integer.parseInt(obj.setValue("report_id", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);

				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("office_name", Office_name1);

				System.out.println("Office_name1 apsk  " + Office_name1);
				// parameters.put("Flag", "N"); // ok DCB_SCHEMEWISE_DETAIL_060920221.jrxml

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/Ledger_Data_Report_30082022.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Ben_Rep91.jasper");

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_STATEMENT_01092022.jasper");

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/Fas_credit_advise.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("DCB_REP92")) {
				option = "2";
				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));

				int office_id = Integer.parseInt(obj.setValue("report_id", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);

				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				int prvmonth = monthnew - 1;

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("office_name", Office_name1);

				parameters.put("prvmonth", prvmonth);

				System.out.println("Office_name1 apsk  " + Office_name1);
				// parameters.put("Flag", "N"); // ok DCB_SCHEMEWISE_DETAIL_060920221.jrxml

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/Ledger_Data_Report_30082022.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Ben_PrevMonthRep92.jasper");

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_STATEMENT_01092022.jasper");

				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/Fas_credit_advise.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			// DCB_REP93 starts

			else if (command.trim().equalsIgnoreCase("DCB_REP93")) {
				option = "2";
				System.out.println("Office_name1 apsk1");
				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));

				int office_id = Integer.parseInt(obj.setValue("report_id", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);

				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				int prvmonth = monthnew - 1;

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("office_name", Office_name1);

				parameters.put("prvmonth", prvmonth);

				System.out.println("Office_name1 apsk  " + Office_name1);

				System.out.println("Before apsk  " + Office_name1);

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Ben_FASvMonthRep93.jasper");

				System.out.println("After  apsk  " + Office_name1);

				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
				rep = 1;
				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			// DCB94

			else if (command.trim().equalsIgnoreCase("DCB_REP94"))

			{
				option = "2";

				int reporttype = Integer.parseInt(obj.setValue("reporttype", request));

				System.out.println("reporttype1 apsk1     " + reporttype);
				int yearnew = Integer.parseInt(obj.setValue("year1", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month1", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));
				// int office_id=Integer.parseInt(obj.setValue("Office_id", request));
				int office_id = Integer.parseInt(Office_id);

				int bentype = Integer.parseInt(obj.setValue("bentype", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);
				System.out.println("bentype is  " + bentype);
				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				int prvmonth = monthnew - 1;

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("office_name", Office_name1);
				parameters.put("ben_type", bentype);

				// parameters.put("prvmonth", prvmonth);

				System.out.println("Office_name1 apsk  " + Office_name1);

				System.out.println("Before apsk  " + Office_name1);

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Ben_TypeMonthRep94.jasper");

				System.out.println("After  apsk  " + Office_name1);

				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}
			// over DEB 94

			else if (command.trim().equalsIgnoreCase("DCB_REP95"))

			{
				option = "2";
				System.out.println("Office_name1 apsk1");
				int yearnew = Integer.parseInt(obj.setValue("year1", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month1", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));
				// int office_id=Integer.parseInt(obj.setValue("Office_id", request));
				int office_id = Integer.parseInt(Office_id);

				int bentype = Integer.parseInt(obj.setValue("bentype", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);
				System.out.println("bentype is  " + bentype);
				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				int prvmonth = monthnew - 1;

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("office_name", Office_name1);
				parameters.put("ben_type", bentype);

				// parameters.put("prvmonth", prvmonth);

				System.out.println("Office_name1 apsk  " + Office_name1);

				System.out.println("Before apsk  " + Office_name1);

				path = getServletContext()
						.getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Ben_Type_Corpn_DMD_COL_95.jasper");

				System.out.println("After  apsk  " + Office_name1);

				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("DCB_REP96"))

			{
				option = "2";
				System.out.println("Office_name1 apsk1");
				int yearnew = Integer.parseInt(obj.setValue("year1", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month1", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));
				// int office_id=Integer.parseInt(obj.setValue("Office_id", request));
				int office_id = Integer.parseInt(Office_id);

				int bentype = Integer.parseInt(obj.setValue("bentype", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);
				System.out.println("bentype is  " + bentype);
				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				int prvmonth = monthnew - 1;

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("prv_month", prvmonth);
				parameters.put("office_name", Office_name1);
				parameters.put("ben_type", bentype);
				System.out.println("Office_name1 apsk  " + Office_name1);

				System.out.println("Before apsk  " + Office_name1);

				path = getServletContext()
						.getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Ben_Type_COL_FromFAS96.jasper");

				System.out.println("After  apsk  " + Office_name1);

				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("DCB_REP97"))

			{
				option = "2";
				System.out.println("Office_name1 apsk1");
				int yearnew = Integer.parseInt(obj.setValue("year1", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month1", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));
				// int office_id=Integer.parseInt(obj.setValue("Office_id", request));
				int office_id = Integer.parseInt(Office_id);

				int bentype = Integer.parseInt(obj.setValue("bentype", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);
				System.out.println("bentype is  " + bentype);
				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				int prvmonth = monthnew - 1;

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("prv_month", prvmonth);
				parameters.put("office_name", Office_name1);
				parameters.put("ben_type", bentype);
				System.out.println("Office_name1 apsk  " + Office_name1);

				System.out.println("Before apsk  " + Office_name1);

				path = getServletContext()
						.getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Corporation_Coln_from_FAS97.jasper");

				System.out.println("After  apsk  " + Office_name1);

				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("DCB_REP98"))

			{
				option = "2";
				System.out.println("Office_name1 apsk1");
				int yearnew = Integer.parseInt(obj.setValue("year1", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month1", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));
				// int office_id=Integer.parseInt(obj.setValue("Office_id", request));
				int office_id = Integer.parseInt(Office_id);

				int bentype = Integer.parseInt(obj.setValue("bentype", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);
				System.out.println("bentype is  " + bentype);
				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				int prvmonth = monthnew - 1;

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("prv_month", prvmonth);
				parameters.put("office_name", Office_name1);
				parameters.put("ben_type", bentype);
				System.out.println("Office_name1 apsk  " + Office_name1);

				System.out.println("Before apsk  " + Office_name1);

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Ben_Coln_from_FAS8.jasper");

				System.out.println("After  apsk  " + Office_name1);

				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("DCB_REP99"))

			{
				option = "2";
				System.out.println("Office_name1 apsk1");
				int yearnew = Integer.parseInt(obj.setValue("year1", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month1", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));
				int repin = Integer.parseInt(obj.setValue("repin", request));
				// int office_id=Integer.parseInt(obj.setValue("Office_id", request));
				int office_id = Integer.parseInt(Office_id);

				int bentype = Integer.parseInt(obj.setValue("bentype", request));
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;

				ctxpath = request.getRequestURL().toString();

				System.out.println("yearnew is " + yearnew);
				System.out.println("monthnew is " + monthnew);
				System.out.println("repin is  " + repin);
				System.out.println("bentype is  " + bentype);
				System.out.println("office_id is  " + office_id);

				String Office_name1 = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				int prvmonth = monthnew - 1;

				parameters.put("year", yearnew);
				parameters.put("office_id", office_id);
				parameters.put("month", monthnew);
				parameters.put("prv_month", prvmonth);
				parameters.put("office_name", Office_name1);
				parameters.put("ben_type", bentype);
				System.out.println("Office_name1 apsk  " + Office_name1);

				System.out.println("Before apsk  " + Office_name1);

				path = getServletContext()
						.getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/DCB_Corpn_Coln_from_FAS9_99.jasper");

				System.out.println("After  apsk  " + Office_name1);

				response.setHeader("Content-Disposition", "attachment; filename=\"Fas_credit_advise.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					// reportPdf(response, jasperPrint2, outuputStream2);
					excelshow(response, jasperPrint2, outuputStream2);

				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			// DCB_rep91 Ovwer

			else if (command.trim().equalsIgnoreCase("Watercharge2")) {

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is 44 " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew44  " + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));

				int repin = Integer.parseInt(obj.setValue("repin", request));
				System.out.println(repin);
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;
				ctxpath = request.getRequestURL().toString();

				parameters.put("year", yearnew);
				parameters.put("divby", repin);
				parameters.put("month", monthnew);

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Quan.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Schemewise Quantity.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					// reportPdf(response, jasperPrint2, outuputStream2);
					excelshow(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("Watercharge1")) {

				int yearnew = Integer.parseInt(obj.setValue("year", request));
				System.out.println("yearnew is " + yearnew);
				int monthnew = Integer.parseInt(obj.setValue("month", request));
				System.out.println("monthnew" + monthnew);
				int rep = Integer.parseInt(obj.setValue("rep", request));

				int repin = Integer.parseInt(obj.setValue("repin", request));
				System.out.println(repin);
				if (repin == 1) {
					repin = 100000;
				} else
					repin = 1;
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				parameters.put("year", yearnew);
				parameters.put("divby", repin);
				parameters.put("month", monthnew);
				parameters.put("rep", rep);
				StringBuffer dynamic_query = new StringBuffer();

				dynamic_query.append(
						"select ob.district_code,ob.DISTRICT_NAME,coalesce(cor.cor,NULL,0,cor.cor)AS CORP,coalesce(mun.mun,NULL,0,mun.mun)AS MUN,coalesce(tp.tp,NULL,0,tp.tp)as TP,coalesce(vi.vi,NULL,0,vi.vi)as VP ");
				dynamic_query.append(
						" FROM ((SELECT distinct DISTRICT_CODE, DISTRICT_NAME from PMS_DCB_LEDGER_ACTUAL where month="
								+ monthnew + " and year=" + yearnew
								+ " )ob  LEFT OUTER JOIN (SELECT sum(BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES)) / "
								+ repin + " as cor,");
				dynamic_query.append(" DISTRICT_CODE from PMS_DCB_LEDGER_ACTUAL where month=" + monthnew + " and year="
						+ yearnew + " and BEN_TYPE_ID_SUB IN (1) ");
				dynamic_query.append(
						" GROUP BY DISTRICT_CODE )cor ON cor.DISTRICT_CODE       =ob.DISTRICT_CODE LEFT OUTER JOIN (SELECT sum(BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES) )/ "
								+ repin + " as mun,DISTRICT_CODE from PMS_DCB_LEDGER_ACTUAL where month=" + monthnew
								+ " and year=" + yearnew + " and BEN_TYPE_ID_SUB IN (2,3) ");
				dynamic_query.append(
						" GROUP BY DISTRICT_CODE )mun ON mun.DISTRICT_CODE       =ob.DISTRICT_CODE LEFT OUTER JOIN (SELECT sum(BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES) )/ "
								+ repin + " as tp,");
				dynamic_query.append(" DISTRICT_CODE from PMS_DCB_LEDGER_ACTUAL where month=" + monthnew + " and year="
						+ yearnew + " and BEN_TYPE_ID_SUB IN (4) GROUP BY DISTRICT_CODE )tp");
				dynamic_query.append(
						" ON tp.DISTRICT_CODE       =ob.DISTRICT_CODE LEFT OUTER JOIN  (SELECT sum(BALANCE+(OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC+ADD_DMD_UPTO)-(TOT_COLN_YES_YR_DMD+COLN_UPTO_PRV_MTH_CR_YR_DMD+COLN_INCLUDE_CHARGES) )/ "
								+ repin + " as vi, DISTRICT_CODE from PMS_DCB_LEDGER_ACTUAL where month=" + monthnew
								+ " and year=" + yearnew + " and BEN_TYPE_ID_SUB IN (6) ");
				dynamic_query.append(
						" GROUP BY DISTRICT_CODE )vi ON vi.DISTRICT_CODE       =ob.DISTRICT_CODE ) order by DISTRICT_NAME");

				parameters.put("query", dynamic_query.toString());
				System.out.println(dynamic_query.toString());

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Diswisewater.jasper");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"DISTRICTWISE WATER CHARGES DUE.pdf\"");
				outuputStream2 = response.getOutputStream();
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);

				if (rep == 1)
					reportPdf(response, jasperPrint2, outuputStream2);
				else if (rep == 2)
					excelshow(response, jasperPrint2, outuputStream2);

			}

			else if (command.trim().equalsIgnoreCase("meter_installed_status_summary")) {
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/new_report_summary.jasper");
				response.setHeader("Content-Disposition",
						"attachment; filename=\"meter_installed_status_summary.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("FIN_YEAR_REPORT")) {

				int year1 = Integer.parseInt(obj.setValue("pyear", request));
				System.out.println(year1);
				int month_end = Integer.parseInt(obj.setValue("pmonth", request));
				// if (month_end <=3) year1=year1-1;
				int year2 = (year1 + 1);
				;
				parameters.put("year1", Integer.toString(year1));
				parameters.put("year2", Integer.toString(year2));
				parameters.put("notmonth", month_end);
				month_value_display = obj.month_val(obj.setValue("pmonth", request)) + " " + year1;
				month_value_display += " To " + obj.month_val(obj.setValue("pmonth1", request)) + " " + year1;

				if (Office_id.equalsIgnoreCase("5000"))
					Office_id = obj.setValue("office_id", request);
				Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				obj.dis(Office_id);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", "Financial Year  " + year1 + "-" + year2);
				parameters.put("office_name", Office_name);
				int month1_end = Integer.parseInt(obj.setValue("pmonth", request));
				String month_L = obj.month_List(month1_end);
				String month_value = "";
				if (month1_end > 3 && month1_end <= 12)
					month_value = obj.month_val(obj.setValue("pmonth", request)) + " " + year1;
				else
					month_value = obj.month_val(obj.setValue("pmonth", request)) + " " + year2;
				parameters.put("mvalue1", month_value);
				StringBuffer dynamic_query = new StringBuffer();

//        		dynamic_query.append(" select cr.CASHBOOK_YEAR,cr.CASHBOOK_MONTH,decode(cramt,null,0,cramt) as cramt,decode(dramt,null,0,dramt) as dramt,");
//				dynamic_query.append(" decode(journal,null,0,journal) as journal,dcbdmd,decode(dcbcr,null,0,dcbcr) as dcbcr,");
//				dynamic_query.append(" decode(dcbdr,null,0,dcbdr) as dcbdr,decode(collection,null,0,collection) as collection,");
//				dynamic_query.append(" ( decode(dcbdmd,null,0,dcbdmd)-decode(journal,null,0,journal) ) as differ1,");
//				dynamic_query.append(" (decode(cramt,null,0,cramt)- decode(dramt,null,0,dramt))-decode(collection,null,0,collection) as differ2,");
//				dynamic_query.append(" CASE");
//				dynamic_query.append(" WHEN cr.CASHBOOK_MONTH =1 THEN 'January' WHEN cr.CASHBOOK_MONTH =2 THEN 'February' WHEN cr.CASHBOOK_MONTH =3  THEN 'March'");
//				dynamic_query.append(" WHEN cr.CASHBOOK_MONTH =4 THEN 'April'   WHEN cr.CASHBOOK_MONTH =5 THEN 'May'      WHEN cr.CASHBOOK_MONTH =6  THEN 'June'");
//				dynamic_query.append(" WHEN cr.CASHBOOK_MONTH =7 THEN 'July'    WHEN cr.CASHBOOK_MONTH =8 THEN 'August'   WHEN cr.CASHBOOK_MONTH =9  THEN 'September'");
//				dynamic_query.append(" WHEN cr.CASHBOOK_MONTH =10 THEN 'October' WHEN cr.CASHBOOK_MONTH =11  THEN 'November'  WHEN cr.CASHBOOK_MONTH =12   THEN 'December'");
//				dynamic_query.append(" END AS mval"); 
//				dynamic_query.append(" from");
//				dynamic_query.append(" (");
//				dynamic_query.append("  SELECT SUM(journal) AS journal ,CASHBOOK_MONTH, CASHBOOK_YEAR,  NEW_OFFICE_ID   AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
//				dynamic_query.append(" ( SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as journal,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_DR_VIEW");
//				dynamic_query.append(" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 780401 and 780407 )");
//				dynamic_query.append(" and sltypecode in (10,14)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy')>= to_date('1-4-"+year1+"','dd-mm-yyyy'))");
//				dynamic_query.append(" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy'))");
//				dynamic_query.append(" and CASHBOOK_MONTH  in ("+month_L+")");
//				dynamic_query.append(" group by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH order by CASHBOOK_YEAR,CASHBOOK_MONTH ");
//				dynamic_query.append(" )a");
//				dynamic_query.append(" JOIN ");
//				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID ="+Office_id);
//				dynamic_query.append(" )b");
//				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
//				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");
//				dynamic_query.append(" )journal");
//				dynamic_query.append(" right outer join");
//				dynamic_query.append(" (");
//				dynamic_query.append(" SELECT SUM(cramt) AS cramt ,CASHBOOK_MONTH, CASHBOOK_YEAR,NEW_OFFICE_ID    AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
//				dynamic_query.append(" ( SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as cramt,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_CR_VIEW");
//				dynamic_query.append(" WHERE achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where COLLECTION_TYPE in (6,7))");
//				dynamic_query.append(" and sltypecode in (10,14)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy'))");
//				dynamic_query.append(" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy'))");
//				dynamic_query.append("    and CASHBOOK_MONTH  in ("+month_L+")  group by ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR,CASHBOOK_MONTH  order by CASHBOOK_YEAR,CASHBOOK_MONTH");
//				dynamic_query.append(" )a");
//				dynamic_query.append(" JOIN ");     
//				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID ="+Office_id);
//				dynamic_query.append(" )b");
//				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
//				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");				
//				dynamic_query.append(" )cr");
//				dynamic_query.append(" on cr.CASHBOOK_YEAR=journal.CASHBOOK_YEAR and cr.CASHBOOK_MONTH=journal.CASHBOOK_MONTH");
//				dynamic_query.append(" left outer join");
//				dynamic_query.append(" (");
//				dynamic_query.append(" SELECT SUM(dramt) AS dramt ,CASHBOOK_MONTH, CASHBOOK_YEAR,NEW_OFFICE_ID    AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
//				dynamic_query.append(" (SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as dramt,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_DR_VIEW");
//				dynamic_query.append(" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 782401 and 782407 )"); 
//				dynamic_query.append("     AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy'))");
//				dynamic_query.append(" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy'))");
//				dynamic_query.append(" and CASHBOOK_MONTH  in ("+month_L+")   group by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH order by CASHBOOK_YEAR,CASHBOOK_MONTH");
//				dynamic_query.append(" )a");
//				dynamic_query.append(" JOIN ");
//				dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID ="+Office_id);
//				dynamic_query.append(" )b");
//				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
//				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");				
//
//				dynamic_query.append(" )dr");
//				dynamic_query.append(" on cr.CASHBOOK_YEAR=dr.CASHBOOK_YEAR and cr.CASHBOOK_MONTH=dr.CASHBOOK_MONTH");
//				dynamic_query.append(" left outer join ");
//				dynamic_query.append(" (");
//				dynamic_query.append("  select YEAR,MONTH,  sum(DMD_FOR_MTH_WC) as dcbdmd, sum(ADD_CHARGES_WC)  as dcbdr,sum(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)   as collection  ,sum(MINUS_CHARGES_WC) as dcbcr from PMS_DCB_LEDGER_ACTUAL");
//				dynamic_query.append(" where   (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy'))");
//				dynamic_query.append(" and office_id="+Office_id+" AND (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"+year2+"','dd-mm-yyyy'))");
//				dynamic_query.append(" and month  in ("+month_L+")  group by YEAR,MONTH  order by YEAR,MONTH");
//				dynamic_query.append(" )dcb on dcb.YEAR=journal.CASHBOOK_YEAR and dcb.MONTH=journal.CASHBOOK_MONTH order by cr.CASHBOOK_YEAR,cr.CASHBOOK_MONTH");

				dynamic_query.append(
						" select cr.CASHBOOK_YEAR,cr.CASHBOOK_MONTH,coalesce(cramt,null,0,cramt) as cramt,coalesce(dramt,null,0,dramt) as dramt,");
				dynamic_query.append(
						" coalesce(journal,null,0,journal) as journal,dcbdmd,coalesce(dcbcr,null,0,dcbcr) as dcbcr,");
				dynamic_query.append(
						" coalesce(dcbdr,null,0,dcbdr) as dcbdr,coalesce(collection,null,0,collection) as collection,");
				dynamic_query
						.append(" ( coalesce(dcbdmd,null,0,dcbdmd)-coalesce(journal,null,0,journal) ) as differ1,");
				dynamic_query.append(
						" (coalesce(cramt,null,0,cramt)- coalesce(dramt,null,0,dramt))-coalesce(collection,null,0,collection) as differ2,");
				dynamic_query.append(" CASE");
				dynamic_query.append(
						" WHEN cr.CASHBOOK_MONTH =1 THEN 'January' WHEN cr.CASHBOOK_MONTH =2 THEN 'February' WHEN cr.CASHBOOK_MONTH =3  THEN 'March'");
				dynamic_query.append(
						" WHEN cr.CASHBOOK_MONTH =4 THEN 'April'   WHEN cr.CASHBOOK_MONTH =5 THEN 'May'      WHEN cr.CASHBOOK_MONTH =6  THEN 'June'");
				dynamic_query.append(
						" WHEN cr.CASHBOOK_MONTH =7 THEN 'July'    WHEN cr.CASHBOOK_MONTH =8 THEN 'August'   WHEN cr.CASHBOOK_MONTH =9  THEN 'September'");
				dynamic_query.append(
						" WHEN cr.CASHBOOK_MONTH =10 THEN 'October' WHEN cr.CASHBOOK_MONTH =11  THEN 'November'  WHEN cr.CASHBOOK_MONTH =12   THEN 'December'");
				dynamic_query.append(" END AS mval");
				dynamic_query.append(" from");
				dynamic_query.append(" (");
				dynamic_query.append(
						"  SELECT SUM(journal) AS journal ,CASHBOOK_MONTH, CASHBOOK_YEAR,  NEW_OFFICE_ID   AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(
						" ( SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as journal,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_DR_VIEW");
				dynamic_query.append(
						" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 780401 and 780407 )");
				dynamic_query.append(
						" and sltypecode in (10,14)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy')>= to_date('1-4-"
								+ year1 + "','dd-mm-yyyy'))");
				dynamic_query.append(
						" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"
								+ year2 + "','dd-mm-yyyy'))");
				dynamic_query.append(" and CASHBOOK_MONTH  in (" + month_L + ")");
				dynamic_query.append(
						" group by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH order by CASHBOOK_YEAR,CASHBOOK_MONTH ");
				dynamic_query.append(" )a");
				dynamic_query.append(" JOIN ");
				// dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM
				// PMS_DCB_DEFUND_DIV where NEW_OFFICE_ID ="+Office_id);
				dynamic_query.append(
						" ( SELECT distinct(new_office_id) FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID =" + Office_id);
				dynamic_query.append(" )b");
				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");
				dynamic_query.append(" )journal");
				dynamic_query.append(" right outer join");
				dynamic_query.append(" (");
				dynamic_query.append(
						" SELECT SUM(cramt) AS cramt ,CASHBOOK_MONTH, CASHBOOK_YEAR,NEW_OFFICE_ID    AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(
						" ( SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as cramt,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_CR_VIEW");
				dynamic_query.append(
						" WHERE achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where COLLECTION_TYPE in (6,7))");
				dynamic_query.append(
						" and achcode not in (160502) and sltypecode in (10,14)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"
								+ year1 + "','dd-mm-yyyy'))");
//				dynamic_query.append(" and sltypecode in (10,14)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"+year1+"','dd-mm-yyyy'))");

				dynamic_query.append(
						" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"
								+ year2 + "','dd-mm-yyyy'))");
				dynamic_query.append("    and CASHBOOK_MONTH  in (" + month_L
						+ ")  group by ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR,CASHBOOK_MONTH  order by CASHBOOK_YEAR,CASHBOOK_MONTH");
				dynamic_query.append(" )a");
				dynamic_query.append(" JOIN ");

				dynamic_query.append(
						" ( SELECT distinct(new_office_id) FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID =" + Office_id);

				// dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM
				// PMS_DCB_DEFUND_DIV where NEW_OFFICE_ID ="+Office_id);
				dynamic_query.append(" )b");
				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");
				dynamic_query.append(" )cr");
				dynamic_query.append(
						" on cr.CASHBOOK_YEAR=journal.CASHBOOK_YEAR and cr.CASHBOOK_MONTH=journal.CASHBOOK_MONTH");
				dynamic_query.append(" left outer join");
				dynamic_query.append(" (");
				dynamic_query.append(
						" SELECT SUM(dramt) AS dramt ,CASHBOOK_MONTH, CASHBOOK_YEAR,NEW_OFFICE_ID    AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(
						" (SELECT CASHBOOK_YEAR,CASHBOOK_MONTH,SUM(amount1) as dramt,ACCOUNTING_FOR_OFFICE_ID FROM FAS_HEAD_SLTYPE_DR_VIEW");
				dynamic_query.append(
						" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 782401 and 782407 )");
				dynamic_query.append(
						"     AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"
								+ year1 + "','dd-mm-yyyy'))");
				dynamic_query.append(
						" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"
								+ year2 + "','dd-mm-yyyy'))");
				dynamic_query.append(" and CASHBOOK_MONTH  in (" + month_L
						+ ")   group by ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH order by CASHBOOK_YEAR,CASHBOOK_MONTH");
				dynamic_query.append(" )a");
				dynamic_query.append(" JOIN ");

				// dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM
				// PMS_DCB_DEFUND_DIV where NEW_OFFICE_ID ="+Office_id);

				dynamic_query.append(
						" ( SELECT distinct(new_office_id) FROM PMS_DCB_DEFUND_DIV  where NEW_OFFICE_ID =" + Office_id);
				dynamic_query.append(" )b");
				dynamic_query.append(" ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID");
				dynamic_query.append(" GROUP BY NEW_OFFICE_ID,CASHBOOK_MONTH, CASHBOOK_YEAR");

				dynamic_query.append(" )dr");
				dynamic_query.append(" on cr.CASHBOOK_YEAR=dr.CASHBOOK_YEAR and cr.CASHBOOK_MONTH=dr.CASHBOOK_MONTH");
				dynamic_query.append(" left outer join ");
				dynamic_query.append(" (");
				dynamic_query.append(
						"  select YEAR,MONTH,  sum(DMD_FOR_MTH_WC) as dcbdmd, sum(ADD_CHARGES_WC)  as dcbdr,sum(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)   as collection  ,sum(MINUS_CHARGES_WC) as dcbcr from PMS_DCB_LEDGER_ACTUAL");
				dynamic_query.append(" where   (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"
						+ year1 + "','dd-mm-yyyy'))");
				dynamic_query.append(" and office_id=" + Office_id
						+ " AND (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-" + year2
						+ "','dd-mm-yyyy'))");
				dynamic_query.append(" and month  in (" + month_L + ")  group by YEAR,MONTH  order by YEAR,MONTH");
				dynamic_query.append(
						" )dcb on dcb.YEAR=journal.CASHBOOK_YEAR and dcb.MONTH=journal.CASHBOOK_MONTH order by cr.CASHBOOK_YEAR,cr.CASHBOOK_MONTH");

				parameters.put("query", dynamic_query.toString());

				System.out.println(dynamic_query.toString());
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_checking.jasper");
				ctxpath = request.getRequestURL().toString();
				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_checking.jasper");
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"Fin_Year_Demand_Collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("FIN_YEAR_REPORT_HO")) {
				int year1 = Integer.parseInt(obj.setValue("pyear", request));
				System.out.println(obj.setValue("pyear", request));
				int month1_end = Integer.parseInt(obj.setValue("pmonth", request));
				// if (month1_end <=3) year1=year1-1;
				int year2 = (year1 + 1);
				;
				parameters.put("year1", Integer.toString(year1));
				parameters.put("year2", Integer.toString(year2));
				int month2_start = 0, month2_end = 0;
				parameters.put("month1_end", month1_end);
				parameters.put("month2_start", month2_start);
				parameters.put("month2_end", month2_end);
				parameters.put("notmonth", month1_end);
				String month_L = obj.month_List(month1_end);
				StringBuffer dynamic_query = new StringBuffer();
				dynamic_query.append("select off.office_id,office_name,coalesce(cramt,null,0,cramt) as cramt, ");
				dynamic_query.append(" coalesce(dramt,null,0,dramt) as dramt, ");
				dynamic_query.append(" coalesce(journal,null,0,journal) as journal,dcbdmd, ");
				dynamic_query.append(" coalesce(dcbcr,null,0,dcbcr) as dcbcr, ");
				dynamic_query.append(" coalesce(dcbdr,null,0,dcbdr) as dcbdr, ");
				dynamic_query.append("  coalesce(collection,null,0,collection) as collection ,  ");
				dynamic_query
						.append("  ( coalesce(dcbdmd,null,0,dcbdmd)-coalesce(journal,null,0,journal) ) as differ1,  ");
				dynamic_query.append(
						" (coalesce(cramt,null,0,cramt)- coalesce(dramt,null,0,dramt))-coalesce(collection,null,0,collection) as differ2  ");
				dynamic_query.append(" from (   ");
				dynamic_query.append(" select office_id ,office_name from com_mst_all_offices_view ");
				dynamic_query.append(" where   office_id in (select office_id from PMS_DCB_DIV_DIST_MAP ) ");
				dynamic_query.append(" )off  ");
				dynamic_query.append(" join  ");
				dynamic_query.append(
						" (   SELECT SUM(journal) as journal ,NEW_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" (");
				dynamic_query.append(
						" SELECT ACCOUNTING_FOR_OFFICE_ID,SUM(amount1) as journal FROM FAS_HEAD_SLTYPE_DR_VIEW  ");
				dynamic_query.append(
						" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 780401 and 780407 )  ");
				dynamic_query.append(
						" and sltypecode IN (10,14)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy')>= to_date('1-4-"
								+ year1
								+ "','dd-mm-yyyy'))   AND (to_date((01||'-'||CASHBOOK_MONTH||'-' ||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"
								+ year2 + "','dd-mm-yyyy')) ");
				dynamic_query.append(" and CASHBOOK_MONTH in (" + month_L + ") ");
				dynamic_query.append("  group by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" order by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" )a JOIN ");
				// dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM
				// PMS_DCB_DEFUND_DIV ");

				dynamic_query.append(" ( SELECT distinct(new_office_id) FROM PMS_DCB_DEFUND_DIV ");

				dynamic_query.append(" )b ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID  GROUP BY NEW_OFFICE_ID ");
				dynamic_query.append("   )journal ");
				dynamic_query.append(" on off.office_id=journal.ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" left outer join ");
				dynamic_query
						.append(" (  SELECT SUM(cramt) as cramt ,NEW_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" (");
				dynamic_query
						.append(" SELECT ACCOUNTING_FOR_OFFICE_ID,SUM(amount1) as cramt FROM FAS_HEAD_SLTYPE_CR_VIEW ");
				dynamic_query.append(
						" WHERE achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where COLLECTION_TYPE in (6,7) ) ");
				dynamic_query.append(
						" and achcode not in (160502) and sltypecode in (14,10)  AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"
								+ year1 + "','dd-mm-yyyy')) ");
				// dynamic_query.append(" and sltypecode in (14,10) AND
				// (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >=
				// to_date('1-4-"+year1+"','dd-mm-yyyy')) ");

				dynamic_query.append(
						" AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"
								+ year2 + "','dd-mm-yyyy')) ");
				dynamic_query.append(" and CASHBOOK_MONTH in (" + month_L + ")");
				dynamic_query.append(" group by ACCOUNTING_FOR_OFFICE_ID order by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" )a JOIN ");

				dynamic_query.append(" ( SELECT distinct(new_office_id) FROM PMS_DCB_DEFUND_DIV ");

				// dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM
				// PMS_DCB_DEFUND_DIV ");
				dynamic_query.append(" )b ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID  GROUP BY NEW_OFFICE_ID ");
				dynamic_query.append(" )cr ");
				dynamic_query.append(" on cr.ACCOUNTING_FOR_OFFICE_ID=off.office_id ");
				dynamic_query.append(" left outer join ");
				dynamic_query.append(" ( SELECT SUM(dramt) as dramt ,NEW_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID  FROM ");
				dynamic_query.append(" (");
				dynamic_query
						.append(" SELECT ACCOUNTING_FOR_OFFICE_ID,SUM(amount1) as dramt FROM FAS_HEAD_SLTYPE_DR_VIEW ");
				dynamic_query.append(
						" WHERE achcode IN ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where account_head_code between 782401 and 782407 ) ");
				dynamic_query.append(
						" 				   and   (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') >= to_date('1-4-"
								+ year1 + "','dd-mm-yyyy')) ");
				dynamic_query.append(
						"   AND (to_date((01||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR),'dd-MM-yyyy') <= to_date('31-3-"
								+ year2 + "','dd-mm-yyyy')) ");
				dynamic_query.append(" and CASHBOOK_MONTH in (" + month_L + ") ");
				dynamic_query.append(" group by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" order by ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" )a JOIN ");
				// dynamic_query.append(" ( SELECT OFFICE_ID,NEW_OFFICE_ID FROM
				// PMS_DCB_DEFUND_DIV ");

				dynamic_query.append(" ( SELECT distinct(new_office_id) FROM PMS_DCB_DEFUND_DIV ");

				dynamic_query.append(" )b ON b.NEW_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID  GROUP BY NEW_OFFICE_ID ");
				dynamic_query.append(" )dr ");
				dynamic_query.append(" on off.office_id=dr.ACCOUNTING_FOR_OFFICE_ID ");
				dynamic_query.append(" left outer join  ");
				dynamic_query.append(" ( ");
				dynamic_query.append(
						" select office_id, sum(DMD_FOR_MTH_WC) as dcbdmd, sum(ADD_CHARGES_WC)  as dcbdr,sum(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)  as collection  ,sum(MINUS_CHARGES_WC) as dcbcr from ");
				dynamic_query.append(" PMS_DCB_LEDGER_ACTUAL ");
				dynamic_query.append(" where    (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') >= to_date('1-4-"
						+ year1 + "','dd-mm-yyyy')) ");
				dynamic_query.append(
						" 					  and   (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-"
								+ year2 + "','dd-mm-yyyy')) ");
				dynamic_query.append(" and month in (" + month_L + ") ");
				dynamic_query.append(" group by office_id ");
				dynamic_query.append(" order by office_id ");
				dynamic_query.append(" )dcb on ");
				dynamic_query.append(" dcb.office_id=off.office_id  order by office_id");
				parameters.put("query", dynamic_query.toString());
				System.out.println("queryyyyyy for dcb" + dynamic_query.toString());
				month_value_display = obj.month_val(obj.setValue("pmonth", request)) + " " + year1;
				month_value_display += " To " + obj.month_val(obj.setValue("pmonth1", request)) + " " + year1;
				String month_value = "";
				if (month1_end > 3 && month1_end <= 12)
					month_value = obj.month_val(obj.setValue("pmonth", request)) + " " + year1;
				else
					month_value = obj.month_val(obj.setValue("pmonth", request)) + " " + year2;
				parameters.put("mvalue", "Financial Year  " + year1 + "-" + year2);
				parameters.put("mvalue1", month_value);

				// parameters.put("mvalue", " obj.month_val(obj.setValue("pmonth1",
				// request))-"+year2);
				parameters.put("office_name", Office_name);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_chk_HO.jasper");
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"DCB_FAS_dmd_collection_chk_HO.pdf\"");
				outuputStream2 = response.getOutputStream();

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_chk_HO.jasper");
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"DCB_FAS_dmd_collection_chk_HO.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("Private_PR")) {
				parameters.put("year1", obj.setValue("pyear", request));
				int year2 = Integer.parseInt(obj.setValue("pyear", request)) + 1;
				;
				parameters.put("year2", Integer.toString(year2));
				parameters.put("finyear", obj.setValue("pyear", request) + "-" + Integer.toString(year2));
				int month1_end = Integer.parseInt(obj.setValue("pmonth", request));
				parameters.put("notmonth", month1_end);
				if (month1_end <= 3)
					parameters.put("uptomonth", obj.month_val(Integer.toString(month1_end)) + "   " + year2);
				else
					parameters.put("uptomonth",
							obj.month_val(Integer.toString(month1_end)) + " " + obj.setValue("pyear", request));

				String cond_ = (Integer.parseInt(Office_id) == 5000) ? "" : " and office_id=" + Office_id;
				String off_name = (Integer.parseInt(Office_id) == 5000) ? "" : Office_name;
				parameters.put("off_name", off_name);
				String month_L = obj.month_List(month1_end);
				StringBuffer dynamic_query = new StringBuffer();
				dynamic_query.append(" select sum(qty) as qty,ddesc from	( ");
				dynamic_query.append(
						" select ben.BENEFICIARY_TYPE_ID_SUB,(select a.BEN_TYPE_DESC from PMS_DCB_BEN_TYPE a where a.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID_SUB) as ddesc, ");
				dynamic_query.append(
						" ben.beneficiary_sno,	ben.beneficiary_name,ben.district_code,(select a.district_name from com_mst_districts a where a.district_code=ben.district_code) as distdesc,");
				dynamic_query.append(" coalesce(pr.qty,null,0,pr.qty) as qty  from (SELECT BENEFICIARY_TYPE_ID_SUB,");
				dynamic_query
						.append(" beneficiary_sno, beneficiary_name,  district_code	FROM PMS_DCB_MST_BENEFICIARY ");
				dynamic_query.append(" WHERE status='L'	AND beneficiary_type_id_sub > 6	 " + cond_
						+ "  ORDER BY beneficiary_type_id_sub ");
				dynamic_query.append(" )ben left outer join	(SELECT beneficiary_sno, SUM(QTY_CONSUMED_NET) as qty ");
				dynamic_query.append(
						" FROM PMS_DCB_TRN_MONTHLY_PR where (to_date((01||'-'||MONTH||'-'|| YEAR),'dd-MM-yyyy') >= to_date('1-4-"
								+ obj.setValue("pyear", request) + "','dd-mm-yyyy')) ");
				dynamic_query.append(" AND (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-" + year2
						+ "','dd-mm-yyyy')) ");
				dynamic_query.append(" and month<>0 and year<>0 and year  >  2012 and  month in (" + month_L + ") "
						+ cond_ + " group by beneficiary_sno	)pr ");
				dynamic_query.append(" on pr.beneficiary_sno=ben.beneficiary_sno )	group by  ddesc ");
				parameters.put("query", dynamic_query.toString());
				System.out.println("queryyyyyyyyyyyyyyyyy" + dynamic_query.toString());
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/private_pumping_return_II.jasper");
				ctxpath = request.getRequestURL().toString();
				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/private_pumping_return_II.jasper");
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"Private_Ben_Pumping_Return.pdf\"");
				outuputStream2 = response.getOutputStream();
			}

			else if (command.trim().equalsIgnoreCase("localbody_PR1")) {
				parameters.put("year1", obj.setValue("pyear", request));
				int year2 = Integer.parseInt(obj.setValue("pyear", request)) + 1;
				;
				parameters.put("year2", Integer.toString(year2));
				parameters.put("finyear", obj.setValue("pyear", request) + "-" + Integer.toString(year2));
				int month1_end = Integer.parseInt(obj.setValue("pmonth", request));
				parameters.put("notmonth", month1_end);
				if (month1_end <= 3)
					parameters.put("uptomonth", obj.month_val(Integer.toString(month1_end)) + "   " + year2);
				else
					parameters.put("uptomonth",
							obj.month_val(Integer.toString(month1_end)) + " " + obj.setValue("pyear", request));

				String cond_ = (Integer.parseInt(Office_id) == 5000) ? "" : " and office_id=" + Office_id;
				String off_name = (Integer.parseInt(Office_id) == 5000) ? "" : Office_name;
				parameters.put("off_name", off_name);
				String month_L = obj.month_List(month1_end);
				StringBuffer dynamic_query = new StringBuffer();
				dynamic_query.append(" select SUM(qty)as qty,SUM(total)as total,ddesc from	( ");
				dynamic_query.append(
						" select ben.BENEFICIARY_TYPE_ID_SUB,(select a.BEN_TYPE_DESC from PMS_DCB_BEN_TYPE a where a.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID_SUB) as ddesc, ");
				dynamic_query.append(
						" ben.beneficiary_sno,	ben.beneficiary_name,ben.district_code,(select a.district_name from com_mst_districts a where a.district_code=ben.district_code) as distdesc,");
				dynamic_query.append(
						" coalesce(pr.qty,null,0,pr.qty) as qty, coalesce(pr.total,NULL,0,pr.total) AS total  from (SELECT BENEFICIARY_TYPE_ID_SUB,");
				dynamic_query
						.append(" beneficiary_sno, beneficiary_name,  district_code	FROM PMS_DCB_MST_BENEFICIARY ");
				dynamic_query.append(" WHERE status='L'	AND beneficiary_type_id_sub between 1 and 6	 " + cond_
						+ "  ORDER BY beneficiary_type_id_sub ");
				dynamic_query.append(
						" )ben left outer join	(SELECT beneficiary_sno, SUM(QTY_CONSUMED) AS qty,SUM(TOTAL_AMT) AS total ");
				dynamic_query.append(
						" FROM PMS_DCB_WC_BILLING where (to_date((01||'-'||MONTH||'-'|| YEAR),'dd-MM-yyyy') >= to_date('1-4-"
								+ obj.setValue("pyear", request) + "','dd-mm-yyyy')) ");
				dynamic_query.append(" AND (to_date((01||'-'||MONTH||'-'||YEAR),'dd-MM-yyyy') <= to_date('31-3-" + year2
						+ "','dd-mm-yyyy')) ");
				dynamic_query.append(" and month<>0 and year<>0 and year  >  2012 and  month in (" + month_L + ") "
						+ cond_ + " group by beneficiary_sno	)pr ");
				dynamic_query.append(" on pr.beneficiary_sno=ben.beneficiary_sno )	group by  ddesc");
				parameters.put("query", dynamic_query.toString());
				System.out.println("queryyyyyyyyyyyyyyyyy" + dynamic_query.toString());
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/private_pumping_return_II.jasper");
				ctxpath = request.getRequestURL().toString();
				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/private_pumping_return_II.jasper");
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"Private_Ben_Pumping_Return.pdf\"");
				outuputStream2 = response.getOutputStream();
			}

			else if (command.trim().equalsIgnoreCase("SCH_REPORT_HO")) {
				String ftype = obj.setValue("option", request);
				String year_fin = obj.setValue("year", request);
				String pmonth = obj.setValue("pmonth", request);

				if (Integer.parseInt(ftype) == 1) {
					parameters.put("divby", "10000000");
					parameters.put("label", "Rs.in Crores");
				} else if (Integer.parseInt(ftype) == 2) {
					parameters.put("divby", "100000");
					parameters.put("label", "Rs.in Lakhs");
				} else if (Integer.parseInt(ftype) == 3) {
					parameters.put("divby", "1");
					parameters.put("label", "");
				}

				parameters.put("month", pmonth);
				parameters.put("year", year_fin);

				parameters.put("mvalue", obj.month_val(obj.setValue("pmonth", request)));
				System.out.println("ftype is " + ftype);
				System.out.println("month is " + pmonth);
				System.out.println("year is" + year_fin);

				ctxpath = request.getRequestURL().toString();
				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Scheme_dem_coll.jasper");
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"Schemewise Demand and coll.pdf\"");
				outuputStream2 = response.getOutputStream();

			}

			else if (command.trim().equalsIgnoreCase("FIN_YEAR_REPORT_HO_MTH")) {
				parameters.put("year", obj.setValue("pyear", request));
				parameters.put("month", obj.setValue("pmonth", request));
				parameters.put("mvalue", obj.month_val(obj.setValue("pmonth", request)));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_Mth_HO.jasper");
				ctxpath = request.getRequestURL().toString();
				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off.jasper");
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_FAS_dmd_collection_Mth_HO.jasper");
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"DCB_FAS_dmd_collection_Mth_HO.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("Division_OpeningBalance")) {
				parameters.put("year", obj.setValue("pyear", request));
				parameters.put("month", obj.setValue("pmonth", request));
				parameters.put("mvalue", obj.month_val(obj.setValue("pmonth", request)));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Opening_Balance.jasper");
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"Division_Opening_Balance.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("Division_OpeningBalance_Details")) {
				parameters.put("year", obj.setValue("pyear", request));
				parameters.put("month", obj.setValue("pmonth", request));
				parameters.put("mvalue", obj.month_val(obj.setValue("pmonth", request)));
				Office_id = obj.setValue("office_id", request);
				Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" + Office_id);
				parameters.put("office_id", Office_id);
				parameters.put("office_name", Office_name);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Opening_Balance_Details.jasper");
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition",
						"attachment; filename=\"Division_Opening_Balance_Details.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("FIN_YEAR_DCB")) {
				parameters.put("year1", obj.setValue("pyear", request));
				int year2 = Integer.parseInt(obj.setValue("pyear", request)) + 1;
				;
				parameters.put("year2", Integer.toString(year2));
				parameters.put("mvalue", "Financial Year : " + obj.setValue("pyear", request) + "-" + year2);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Demand_Collection_Balance.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Demand_Collection_Balance.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("FIN_YEAR_REPORT_DCB")) {
				parameters.put("year1", obj.setValue("pyear", request));
				int year2 = Integer.parseInt(obj.setValue("pyear", request)) + 1;
				;
				parameters.put("year2", Integer.toString(year2));
				month_value_display = obj.month_val(obj.setValue("pmonth", request)) + " "
						+ obj.setValue("pyear", request);
				month_value_display += " To " + obj.month_val(obj.setValue("pmonth1", request)) + " "
						+ obj.setValue("pyear", request);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", "Financial Year : " + obj.setValue("pyear", request) + "-" + year2);
				parameters.put("office_name", Office_name);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/PMS_DCB_DMD_COLLECTION_off_DCB.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Collection Stmt.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("WCBenScheme")) {
				parameters.put("year1", obj.setValue("pyear", request));
				parameters.put("year2", obj.setValue("pyear1", request));
				parameters.put("month1", obj.setValue("pmonth", request));
				parameters.put("month2", obj.setValue("pmonth1", request));

				if (obj.setValue("pmonth", request).equalsIgnoreCase(obj.setValue("pmonth1", request))
						&& obj.setValue("pyear", request).equalsIgnoreCase(obj.setValue("pyear1", request))) {
					month_value_display = obj.month_val(obj.setValue("pmonth", request)) + "-"
							+ obj.setValue("pyear", request);

				} else if (obj.setValue("pmonth", request).equalsIgnoreCase(obj.setValue("pmonth1", request))
						&& !obj.setValue("pyear", request).equalsIgnoreCase(obj.setValue("pyear1", request))) {
					month_value_display = obj.month_val(obj.setValue("pmonth", request)) + " "
							+ obj.setValue("pyear", request);
					month_value_display += "-" + obj.month_val(obj.setValue("pmonth", request)) + " "
							+ obj.setValue("pyear1", request);
				} else {
					month_value_display = obj.month_val(obj.setValue("pmonth", request)) + " "
							+ obj.setValue("pyear", request);
					month_value_display += "-" + obj.month_val(obj.setValue("pmonth1", request)) + " "
							+ obj.setValue("pyear1", request);
				}
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", month_value_display);
				parameters.put("office_name", Office_name);
				String imagespath = getServletContext().getRealPath("/images/") + File.separator;
				parameters.put("imgpath", imagespath);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Sch_Wc_new.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Ben_Sch_Wc_Pr.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("BenSchemePR")) {

				String imagespath = getServletContext().getRealPath("/images/") + File.separator;
				parameters.put("imgpath", imagespath);
				month = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH", " where OFFICE_ID=" + Office_id), 1);
				year = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR", "where OFFICE_ID=" + Office_id), 1);
				parameters.put("year", year);
				parameters.put("month", month);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", obj.month_val(month));
				parameters.put("office_name", Office_name);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Sch_Wc_Pr.jasper");
				response.setHeader("Content-Disposition", "attachment; filename=\"Ben_Sch_Wc_new.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("supply_abstract")) {
				month = obj.setValue("month", request);
				year = obj.setValue("year", request);

				parameters.put("year", year);
				parameters.put("month", month);
				parameters.put("mvalue", obj.month_val(month));

				if (Office_id.equalsIgnoreCase("5000")) {
					parameters.put("office_name", "");

					String off_id = obj.setValue("off_id", request);
					System.out.println(off_id);
					parameters.put("office_id", off_id);
					if (off_id.equalsIgnoreCase("0")) {
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Supplied_Qty_review.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("Supplied_Qty_review.jasper"));
					} else {
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Supplied_Qty_review_div.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("Supplied_Qty_review_div.jasper"));
					}
				} else {
					parameters.put("office_id", Office_id);
					parameters.put("office_name", Office_name);
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Supplied_Qty_review_div.jasper");
					ctxpath = path.substring(0, path.lastIndexOf("Supplied_Qty_review_div.jasper"));
				}

				response.setHeader("Content-Disposition", "attachment; filename=\"Supplied_Qty_review.pdf\"");
				parameters.put("ctxpath", ctxpath);
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("supply_details")) {
				month = obj.setValue("month", request);
				year = obj.setValue("year", request);
				parameters.put("year", year);
				parameters.put("month", month);
				parameters.put("mvalue", obj.month_val(month));
				if (Office_id.equalsIgnoreCase("5000")) {
					parameters.put("office_name", "");
					String off_id = obj.setValue("off_id", request);
					parameters.put("office_id", off_id);
					if (off_id.equalsIgnoreCase("0")) {
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_private_indues.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("Ben_private_indues.jasper"));
					} else {
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_private_indues_div.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("Ben_private_indues_div.jasper"));
					}
				} else {
					parameters.put("office_id", Office_id);
					parameters.put("office_name", Office_name);
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_private_indues_div.jasper");
					ctxpath = path.substring(0, path.lastIndexOf("Ben_private_indues_div.jasper"));
				}
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"Supplied_Qty_review_Details.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("scheme_wise_demand_collection")) {
				month = obj.setValue("month", request);
				year = obj.setValue("year", request);
				parameters.put("year", year);
				parameters.put("office_name", Office_name);
				parameters.put("month", month);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", obj.month_val(month));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/scheme_wise_dmd_collection.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("April_opening_balance")) {
				month = obj.setValue("month", request);
				year = obj.setValue("year", request);
				parameters.put("year", year);
				parameters.put("office_name", Office_name);
				parameters.put("month", month);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", obj.month_val(month));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/ben_wise_april_ob.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("Date_Wise_Collection")) {
				parameters.put("date1", obj.setValue("date1", request));
				parameters.put("date2", obj.setValue("date2", request));

				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Date_Wise_Collection.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("Date_Wise_Journal")) {
				parameters.put("date1", obj.setValue("date1", request));
				parameters.put("date2", obj.setValue("date2", request));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Date_Wise_Journal.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			}

			else if (command.trim().equalsIgnoreCase("Head_Office_Data1")) {
				int april_year = 0;
				month = obj.setValue("month", request);
				year = obj.setValue("year", request);
				parameters.put("year", year);
				report_name = "DCB Data ";
				if (Integer.parseInt(month) <= 3) {
					april_year = Integer.parseInt(year) - 1;
				} else {
					april_year = Integer.parseInt(year);
				}
				parameters.put("office_name", Office_name);
				parameters.put("april_year", Integer.toString(april_year));
				parameters.put("month", month);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", obj.month_val(month));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Head_office_data_Report.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("Head_Office_Data_new")) {
				int april_year = 0;
				month = obj.setValue("month", request);
				year = obj.setValue("year", request);
				parameters.put("year", year);
				report_name = "DCB Data ";
				if (Integer.parseInt(month) <= 3) {
					april_year = Integer.parseInt(year) - 1;
				} else {
					april_year = Integer.parseInt(year);
				}
				parameters.put("office_name", Office_name);
				parameters.put("april_year", Integer.toString(april_year));
				parameters.put("month", month);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", obj.month_val(month));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Head_office_data_Report_new.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			}

			else if (command.trim().equalsIgnoreCase("Head_Office_DataTentative")) {
				int april_year = 0;
				month = obj.setValue("month", request);
				year = obj.setValue("year", request);
				parameters.put("year", year);
				report_name = "DCB Data ";
				if (Integer.parseInt(month) <= 3) {
					april_year = Integer.parseInt(year) - 1;
				} else {
					april_year = Integer.parseInt(year);
				}
				parameters.put("office_name", Office_name);
				parameters.put("april_year", Integer.toString(april_year));
				parameters.put("month", month);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", obj.month_val(month));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Head_office_data_Report_tentat.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			} else if (command.trim().equalsIgnoreCase("Head_Office_DataTentative2")) {
				int april_year = 0;
				month = obj.setValue("month", request);
				year = obj.setValue("year", request);
				parameters.put("year", year);
				report_name = "DCB Data ";
				if (Integer.parseInt(month) <= 3) {
					april_year = Integer.parseInt(year) - 1;
				} else {
					april_year = Integer.parseInt(year);
				}
				parameters.put("office_name", Office_name);
				parameters.put("april_year", Integer.toString(april_year));
				parameters.put("month", month);
				parameters.put("office_id", Office_id);
				parameters.put("mvalue", obj.month_val(month));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Div_wise_Report_tentat.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			}

			// apsk

			else if (command.trim().equalsIgnoreCase("Ledger_data_Report4")) {
				parameters.put("year", obj.setValue("pyear", request));
				parameters.put("month", obj.setValue("pmonth", request));
				parameters.put("mvalue", obj.month_val(obj.setValue("pmonth", request)));
				// path =
				// getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Opening_Balance.jasper");
				// JasperCompileManager.compileReport("/WEB-INF/ReportSrc/Ledger_data_report_29082022.jrxml");
				path = getServletContext()
						.getRealPath("/WEB-INF/ReportSrc/LEDGER_DATA/Ledger_data_report_29082022.jasper");
				// path =
				// getServletContext().getRealPath("/DCB_30_08_2022/twadphase2dcb/WebContent/WEB-INF/ReportSrc/test
				// Report1.jasper");
				ctxpath = request.getRequestURL().toString();
				parameters.put("ctxpath", ctxpath);
				response.setHeader("Content-Disposition", "attachment; filename=\"Division_Opening_Balance.pdf\"");
				outuputStream2 = response.getOutputStream();
			}
			// apsk --- Ledger_data_report_29082022
			// DCB_30_08_2022/twadphase2dcb/WebContent/WEB-INF/ReportSrc

			else if (command.trim().equalsIgnoreCase("receipt_verificaiton_HO")) {
				year = obj.setValue("year", request);
				parameters.put("year", year);
				parameters.put("office_name", Office_name);
				parameters.put("office_id", Office_id);
				parameters.put("year1", Integer.parseInt(year) + 1);
				parameters.put("mvalue", year + "-" + (Integer.parseInt(year) + 1));
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Receipt_Verification_Head_Office.jasper");
				// response.setHeader("Content-Disposition","attachment;
				// filename=\"scheme_wise_dmd_collection.pdf\"");
				outuputStream2 = response.getOutputStream();
			}

			if (flag == 0) {
				jasperPrint2 = JasperFillManager.fillReport(path, parameters, con);
				if (option.equalsIgnoreCase("0"))
					option = "1";
				if (Integer.parseInt(option) == 1)
					if(filename != null) {
						reportPdf(response, jasperPrint2, outuputStream2, filename);
					}else
						reportPdf(response, jasperPrint2, outuputStream2);
				else if (Integer.parseInt(option) == 2)
					excelshow(response, jasperPrint2, outuputStream2);
				else if (Integer.parseInt(option) == 3)
					htmlshow(response, jasperPrint2, outuputStream2);
			}

			con.close();

		} catch (Exception e) {
			response.sendRedirect("");
			e.printStackTrace();
		}
	}
	// =========================================

//	public void excelshow(HttpServletResponse response,
//			JasperPrint jasperPrint, OutputStream outuputStream) {
//		
//		System.out.println("Report Design Startapsk2"); 
//		try {
//			response.setContentType("application/vnd.ms-excel");
//			response.setHeader("Content-Disposition", "attachment;filename=\""+ report_name + ".xls\"");
//			JRXlsExporter exporterXLS = new JRXlsExporter();
//			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
//			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
//			exporterXLS.exportReport();
//			byte[] bytes;
//			bytes = xlsReport.toByteArray();
//			outuputStream.write(bytes, 0, bytes.length);
//			outuputStream.flush();
//			outuputStream.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	// ===========================
	protected void reportPdf(HttpServletResponse response, JasperPrint jasperPrint, OutputStream outuputStream2) {

		System.out.println("Report Design Startapsk1");
		JasperPrint jasperPrint2 = null;
		// =========PDF FORMAT=======================
		try {
			JRExporter exporter = null;
			response.setContentType("application/pdf");
			// response.setHeader("Content-Disposition", "attachment; filename=\""+
			// report_name + ".pdf" + "\"");
			response.setHeader("Content-Disposition", "attachment; filename=\"REPORT.pdf\"");
			exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outuputStream2);
			exporter.exportReport();
			outuputStream2.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("Report Design Startapsk2");

	}
	
	
	protected void reportPdf(HttpServletResponse response, JasperPrint jasperPrint, OutputStream outuputStream2, String filename) {

		System.out.println("Report Design Startapsk1");
		JasperPrint jasperPrint2 = null;
		// =========PDF FORMAT=======================
		try {
			JRExporter exporter = null;
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".pdf\"");

			exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outuputStream2);
			exporter.exportReport();
			outuputStream2.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("Report Design Startapsk2");

	}

	
	public void excelshow(HttpServletResponse response, JasperPrint jasperPrint, OutputStream outuputStream) {

		System.out.println("Report Design Startapsk2");
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + report_name + ".xls\"");
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
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

	public void htmlshow(HttpServletResponse response, JasperPrint jasperPrint, OutputStream outuputStream) {
		try {
			JRExporter exporter = new JRHtmlExporter();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=\"Report.html\"");
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "build/reports/BatchExportReport.html");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outuputStream);
			exporter.exportReport();
			outuputStream.close();
			outuputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void generateExcelFile(ResultSet resultSet, HttpServletResponse response, String filename, String reportTitle) {
	    try {
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet("Sheet 1");

	        HSSFCellStyle headerStyle = workbook.createCellStyle();
	        HSSFFont headerFont = workbook.createFont();
	        headerFont.setBoldweight((short) 10);
	        headerFont.setFontHeightInPoints((short) 14);
	        headerStyle.setFont(headerFont);
	        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	        // Create header title row
	        HSSFRow titleRow = sheet.createRow(0);
	        HSSFCell titleCell = titleRow.createCell((short) 0);
	        titleCell.setCellValue(reportTitle);
	        titleCell.setCellStyle(headerStyle);

	        ResultSetMetaData metaData = resultSet.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (columnCount - 1)));

	        HSSFCellStyle borderStyle = workbook.createCellStyle();
	        borderStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        borderStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        borderStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        borderStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

	        // Create column headers row
	        HSSFRow headerRow = sheet.createRow(1);
	        int[] columnWidths = new int[columnCount];

	        for (int i = 1; i <= columnCount; i++) {
	            HSSFCell cell = headerRow.createCell((short) (i - 1));
	            String columnName = metaData.getColumnName(i).toUpperCase();
	            cell.setCellValue(columnName);
	            cell.setCellStyle(borderStyle);



	            // Initialize column width with header length as minimum
	            columnWidths[i - 1] = columnName.length();
	        }

	        // First pass: Determine max width of each column
	        while (resultSet.next()) {
	            for (int i = 1; i <= columnCount; i++) {
	                String cellValue = resultSet.getString(i);
	                if (cellValue != null) {
	                    columnWidths[i - 1] = Math.max(columnWidths[i - 1], cellValue.length());
	                }
	            }
	        }

	        // Reset ResultSet cursor to beginning
	        resultSet.beforeFirst();

	        // Second pass: Write data to cells
	        int rowNum = 2;
	    	while (resultSet.next()) {
				HSSFRow row = sheet.createRow(rowNum++);
				for (int i = 1; i <= columnCount; i++) {
					HSSFCell cell = row.createCell((short) (i - 1));

					int columnType = metaData.getColumnType(i);

					// Handle different data types
					switch (columnType) {
					case 2:
						cell.setCellValue(resultSet.getLong(i));
						break;
					case java.sql.Types.DOUBLE:
						cell.setCellValue(resultSet.getDouble(i));
						break;
					default:
						// Default to treating other types as strings
						cell.setCellValue(resultSet.getString(i));
					}
				}

			}

	        // Set column widths based on max data length
	        for (int i = 0; i < columnCount; i++) {
	            int width = Math.min(columnWidths[i] * 256 + 200, 255 * 256); // Convert to units, max 255 characters
	            sheet.setColumnWidth((short)i, (short)width);
	        }

	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls");

	        workbook.write(response.getOutputStream());

	        System.out.println("Excel file generated successfully");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



	public static void generateExcelFile(ResultSet resultSet, HttpServletResponse response, String filename) {
		try {

			// Create a new workbook
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet 1");
			
			// Create header row
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			HSSFRow headerRow = sheet.createRow(0);
			for (int i = 1; i <= columnCount; i++) {
				HSSFCell cell = headerRow.createCell((short) (i - 1));
				cell.setCellValue(metaData.getColumnName(i).toUpperCase());
			}

			// Create data rows
			int rowNum = 1;
			while (resultSet.next()) {
				HSSFRow row = sheet.createRow(rowNum++);
				for (int i = 1; i <= columnCount; i++) {
					HSSFCell cell = row.createCell((short) (i - 1));

					int columnType = metaData.getColumnType(i);

					// Handle different data types
					switch (columnType) {
					case 2:
						cell.setCellValue(resultSet.getLong(i));
						break;
					case java.sql.Types.DOUBLE:
						cell.setCellValue(resultSet.getDouble(i));
						break;
					default:
						// Default to treating other types as strings
						cell.setCellValue(resultSet.getString(i));
					}
				}

			}

			// Auto-size columns for better readability
			for (int i = 0; i < columnCount; i++) {
				sheet.autoSizeColumn((short) i);
			}

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls");
			
			workbook.write(response.getOutputStream());

			System.out.println("Excel file generated successfully");
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
