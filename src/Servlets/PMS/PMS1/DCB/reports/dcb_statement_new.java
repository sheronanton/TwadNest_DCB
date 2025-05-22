package Servlets.PMS.PMS1.DCB.reports;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Servlets.PMS.PMS1.DCB.servlets.Controller;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
/**
 * Servlet implementation class dcb_statement
 */
public class dcb_statement_new extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public String option="",report_ref_no="0";
    public dcb_statement_new() 
    {
        super();  
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map parameters = new HashMap();
		Controller obj = new Controller();
		String xml = "", qry = "", mvalue="",Office_id = "0",path = null, Office_name = "", userid = "0", ACCOUNTING_UNIT_ID = "";
		OutputStream outuputStream_dcb;
		JasperPrint jasperPrint_dcb ;
		String oid=obj.setValue("oid",request);
		String	imagespath = getServletContext().getRealPath("/images/")+File.separator;
		parameters.put("imgpath", imagespath);  
		option=obj.setValue("option", request);  
		String ref_no=obj.setValue("refno", request);    
		report_ref_no=ref_no;    
		if (oid.equalsIgnoreCase("0")) Office_id=Office_id; else Office_id=oid;					
		//#M4 END
		String command = request.getParameter("command");// Command
		// ->view,insert etc
		if (command == null || command.equals("")) 	command = "no command";
		Connection con = null;
		try {
			con = obj.con();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HttpSession session = request.getSession(false);
		userid = (String) session.getAttribute("UserId");
		if (userid == null) 
		{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		try {
			
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String input_value = request.getParameter("input_value"); // input value
		if (input_value == null || input_value.equals(""))	input_value = "0";
		String process_code = request.getParameter("process_code");// process
		System.out.println("DCB->dcb_statement_new->command->" + command 	+ "->process_code->" + process_code);
		int month_set = 0;   
		int year_set = 0;
		int flag = 0;
		if (process_code == null || process_code.equals("")) process_code = "0";
		try {
			String sname= request.getParameter("sname");
			mvalue = obj.month_val(obj.setValue("fmonth",request));
			if (process_code.equalsIgnoreCase("1"))
			{   
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
			}if (process_code.equalsIgnoreCase("9"))
			{   
				String sty=obj.setValue("schtype",request);
				  month_set = 0;   
				  year_set = 0;
				if ((Integer.parseInt(obj.setValue("fmonth",request))) == 1) {
					month_set = 12;
					year_set = Integer.parseInt(obj.setValue("fyear", request)) - 1;
				} else {
					month_set = (Integer.parseInt(obj.setValue("fmonth",request)) - 1);
					year_set = Integer.parseInt(obj.setValue("fyear", request));
				}
				if (Office_id.trim().equalsIgnoreCase("5000"))
				{  
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_sch_abs.jasper");
				}else  
				{
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_sch_abs_div.jasper");					 
					parameters.put("office_id", Office_id);
					parameters.put("office_name", Office_name);
				}   
				parameters.put("pmonth", month_set);  
				parameters.put("pyear", year_set); 
				parameters.put("sch_type_id_sub", obj.setValue("schtype",request));  
			}
			if (process_code.equalsIgnoreCase("2"))
			{
				if (Office_id.trim().equalsIgnoreCase("5000"))
				{
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidate_abs_new.jasper");
				}else
				{
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Consolidate_abs_div_new.jasper");					 
					parameters.put("office_id", Office_id);
					parameters.put("office_name", Office_name);   
				}
				parameters.put("schtype", obj.setValue("schtype",request));  
			}
			if (process_code.equalsIgnoreCase("4"))  
			{ 
				String sty=obj.setValue("schtype",request);
				if (sty.equalsIgnoreCase("1"))
				{
					if (Office_id.trim().equalsIgnoreCase("5000"))  
					{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_abs_new.jasper");
					}else
					{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_abs_div_new.jasper");					 
						parameters.put("office_id", Office_id);
						parameters.put("office_name", Office_name);   
					}
				}
				else    
				{
					if (Office_id.trim().equalsIgnoreCase("5000"))  
					{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_abs_new2.jasper");
					}else
					{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_abs_div_new.jasper");					 
						parameters.put("office_id", Office_id);
						parameters.put("office_name", Office_name);   
					}
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
			if (process_code.equalsIgnoreCase("5") || process_code.equalsIgnoreCase("66") || process_code.equalsIgnoreCase("67") ||  process_code.equalsIgnoreCase("62")
					 || process_code.equalsIgnoreCase("63") ||  process_code.equalsIgnoreCase("64") ||  process_code.equalsIgnoreCase("65") )
			  {    
				if (obj.setValue("bentype",request).equalsIgnoreCase("6"))
				{  
					if (Office_id.trim().equalsIgnoreCase("5000"))  
					{  
						if (obj.setValue("schtype",request).equalsIgnoreCase("1"))
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_bty_new_vp.jasper");
						}else
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_bty_new_vp_2.jasper");
						}
					}else
					{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_bty_div_new_vp.jasper");
						parameters.put("office_id", Office_id);
						parameters.put("office_name", Office_name);  
					}
				}  
				else  
				{
						if (Office_id.trim().equalsIgnoreCase("5000"))
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_bty_new.jasper");
						}else  
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sch_type_bty_div_new.jasper");
							parameters.put("office_id", Office_id);
							parameters.put("office_name", Office_name);   
						}
				}
				parameters.put("ben_type", obj.setValue("bentype",request));
				parameters.put("schtype", obj.setValue("schtype",request));
				String schtypevalue = obj.getValue("PMS_DCB_APPLICABLE_SCH_TYPE", "SCH_TYPE_SUB_DESC","where SCH_TYPE_ID_SUB=" + obj.setValue("schtype",request));
				parameters.put("schtypedesc", schtypevalue);
			}else if (process_code.equalsIgnoreCase("15"))
			{
				parameters.put("office_id", Office_id);
				parameters.put("sub_office_id", obj.setValue("sub_div_sno",request));
				parameters.put("office_name", Office_name);
				String sub_div_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + obj.setValue("sub_div_sno",request));
				parameters.put("sub_office_name", sub_div_name);
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/sub_div_wise_water_charges.jasper");
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
			else   if (process_code.equalsIgnoreCase("7"))
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
			}else if (process_code.equalsIgnoreCase("17"))
			{
					path = getServletContext().getRealPath("/WEB-INF/ReportSrc/unitwise_abstract.jasper");
			}else if (process_code.equalsIgnoreCase("18"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/dcb_booklet_vb_districtwise.jasper");
			}	
			if (process_code.equalsIgnoreCase("20"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Region_Wise_Balance.jasper");
				parameters.put("region_name",obj.setValue("region_name", request));
				parameters.put("region_id",obj.setValue("region_id", request));
			}
			else if (process_code.equalsIgnoreCase("21"))
			{	String ctxpath =request.getRequestURL().toString();
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Scheme_DCB.jasper");
				String month=obj.setValue("fmonth", request);
				String year=obj.setValue("fyear", request);
				if ( Integer.parseInt(month) >=4)  
				{
					parameters.put("apryear",Integer.toString((Integer.parseInt(year))));    
				}
				else
				{  
					parameters.put("apryear",Integer.toString((Integer.parseInt(year)-1)));
				}
				if ((Integer.parseInt(obj.setValue("fmonth",request))) == 1) {
					month_set = 12;
					year_set = Integer.parseInt(obj.setValue("fyear", request)) - 1;
				} else {
					month_set = (Integer.parseInt(obj.setValue("fmonth",request)) - 1);
					year_set = Integer.parseInt(obj.setValue("fyear", request));
				}  
				parameters.put("office_id", Office_id);
				parameters.put("ctxpath", ctxpath);  
				parameters.put("office_name", Office_name);
				parameters.put("prv_month", month_set);  
				parameters.put("prv_year", year_set);   
			
				String opt = request.getParameter("option");
				
				System.out.println("opt ======= "+opt);
				
				if(opt.equalsIgnoreCase("2")) {
					String qry1 = " select "
					   		+ "    SCH_sno  as scheme_code, "
					   		+ "    SCH_NAME as scheme_name ,OPENING_BAL_WC,"
					   		+ "    DMD_UPTO_PRV_MNTH_WC_9 as DEMAND_UPTO_PRV_MTH_WC,"
					   		+ "    total AS TOTAL,"
					   		+ "    collection_upto COLLECTION_UPTO,balance,DMD_FOR_MTH_WC_10 as dmdformonth,netdue"
					   		+ " from"
					   		+ " ("
					   		+ " select"
					   		+ "     trim(SCH_NAME) as SCH_NAME,OPENING_BAL_WC,DMD_UPTO_PRV_MNTH_WC_9,"
					   		+ "    OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC_9 as total,"
					   		+ "    COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13 +COLN_FOR_MTH_WC_15 as collection_upto ,"
					   		+ "    (OPENING_BAL_WC+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13 +COLN_FOR_MTH_WC_15) as balance ,DMD_FOR_MTH_WC_10, "
					   		+ "    BALANCE_18 as netdue,yr.SCH_sno as SCH_sno "
					   		+ " from"
					   		+ " ("
					   		+ " SELECT      "
					   		+ "      coalesce(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC))  AS DMD_UPTO_PRV_MNTH_WC_9,"
					   		+ "      coalesce(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC))              AS DMD_FOR_MTH_WC_10,"
					   		+ "      coalesce(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12,"
					   		+ "      coalesce(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "
					   		+ "      coalesce(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES))  AS COLN_FOR_MTH_WC_15, "
					   		+ "      coalesce(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))                      AS BALANCE_18,"
					   		+ "      SCH_NAME,MONTH,YEAR,SCH_sno"
					   		+ "    FROM PMS_DCB_LEDGER_ACTUAL WHERE YEAR="+year_set+"::int AND MONTH ="+month_set+"::int and office_id = "+Office_id+""
					   		+ " GROUP BY YEAR,MONTH,SCH_NAME,SCH_sno"
					   		+ "  )act"
					   		+ "  join"
					   		+ "  ("
					   		+ "  select sum(OB_CUR_YR_WC)+sum(OB_YESTER_YR_WC) as OPENING_BAL_WC,SCH_sno from PMS_DCB_OB_YEARLY WHERE fin_YEAR="+year_set+"::int AND MONTH ="+month_set+"::int   GROUP BY fin_YEAR,MONTH,SCH_sno"
					   		+ "  )yr"
					   		+ "  on yr.SCH_sno=act.SCH_sno  "
					   		+ " ) as opt1"
					   		+ " order by SCH_NAME";
				
				PreparedStatement ps = con.prepareStatement(qry1);
				ResultSet rs = ps.executeQuery();
				
				generateExcelFile1(rs, response, "Schewise_DCB");
				flag=1;
				}
			   
			
			}else if (process_code.equalsIgnoreCase("22"))
			{
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Div_Sch_Ben_DCB.jasper");
				String SCH_NAME=obj.getValue("PMS_SCH_MASTER", "SCH_NAME"," where SCH_SNO="+obj.setValue("SCH_SNO",request));
				parameters.put("office_id", Office_id);
				parameters.put("office_name", Office_name);
				parameters.put("SCH_NAME", SCH_NAME);  
				parameters.put("sch_sno", obj.setValue("SCH_SNO", request));
				String month=obj.setValue("fmonth", request);
				String year=obj.setValue("fyear", request);
				if ( Integer.parseInt(month) >=4)  
				{ 
					parameters.put("apryear",Integer.toString((Integer.parseInt(year))));    
				}
				else
				{  
					parameters.put("apryear",Integer.toString((Integer.parseInt(year)-1)));
				}
			}		     
				parameters.put("year", obj.setValue("fyear", request));
				parameters.put("month", obj.setValue("fmonth", request));
				parameters.put("mvalue", mvalue);  
				parameters.put("sname", sname);
				parameters.put("report_ref_no", report_ref_no); 
			int p = 1;
			String cond = "";
			jasperPrint_dcb = JasperFillManager.fillReport(path, parameters, con);
			outuputStream_dcb = response.getOutputStream();
			/*JRExporter exporter = null;
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition","attachment; filename=\"DCBSTMT.pdf\"");
			exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint_dcb);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_dcb);
			exporter.exportReport();
			outuputStream_dcb.close();
			*/
			if (flag != 1) {
				pdfshow( response,  jasperPrint_dcb,  outuputStream_dcb);      
				excelshow1( response,  jasperPrint_dcb,  outuputStream_dcb);
			}
			
		} catch (JRException e) 
		{
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xml = "<result>Report";
		xml += "</result>";
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	public void htmlshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		try {    
			  JRExporter exporter = new JRHtmlExporter();  
			  response.setContentType("application/pdf");      
			  response.setHeader("Content-Disposition","attachment; filename=\"Report.html\"");
			  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			  exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "build/reports/BatchExportReport.html");
			  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outuputStream);
 			  exporter.exportReport();
			  outuputStream.close();  
			  outuputStream.flush();  
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public void pdfshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
	 
		if (option.equalsIgnoreCase("2"))  
		{
			excelshow( response, jasperPrint, outuputStream);
		}else if (option.equalsIgnoreCase("3"))  
		{
			htmlshow( response, jasperPrint, outuputStream);  
		}else
		{
			try {
				JRPdfExporter exporter = new JRPdfExporter();
			  response.setContentType("application/pdf");      
			  response.setHeader("Content-Disposition","attachment; filename=\"Report.pdf\"");
			  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
			  exporter.exportReport();
			  outuputStream.close();  
			  outuputStream.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void excelshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		if (option.equalsIgnoreCase("1"))
		{
			pdfshow( response, jasperPrint, outuputStream);
		}else if (option.equalsIgnoreCase("3"))  
		{  
			htmlshow( response, jasperPrint, outuputStream);  
		}else if (option.equalsIgnoreCase("2"))  
		{  
			try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","inline; filename=\"Report.xls\"");
			response.addHeader("Content-Disposition", "attachment");
			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS,Boolean.FALSE);
			exporterXLS.exportReport();
			System.out.println(exporterXLS);
			byte[] bytes;
			bytes = xlsReport.toByteArray(); 
			outuputStream.write(bytes, 0, bytes.length);
			outuputStream.flush();
			outuputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void generateExcelFile1(ResultSet resultSet, HttpServletResponse response, String filename) {
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
			
			System.out.println("Excel file generated successfully1!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excelshow1(HttpServletResponse response, JasperPrint jasperPrint, OutputStream outuputStream) {
	    try {
	        if (option.equalsIgnoreCase("1")) {
	            // Assuming pdfshow method is correctly implemented
	            pdfshow(response, jasperPrint, outuputStream);
	        } else if (option.equalsIgnoreCase("3")) {
	            // Assuming htmlshow method is correctly implemented
	            htmlshow(response, jasperPrint, outuputStream);
	        } else if (option.equalsIgnoreCase("2")) {
	            try {
	                System.out.println("Exporting to Excel...");

	                response.setContentType("application/vnd.ms-excel");
	                response.setHeader("Content-Disposition", "inline; filename=\"Report.xls\"");
	                response.addHeader("Content-Disposition", "attachment");

	                JRXlsExporter exporterXLS = new JRXlsExporter();
	                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);

	                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);

	                exporterXLS.exportReport();

	                System.out.println("Export to Excel completed.");

	                byte[] bytes = xlsReport.toByteArray();
	                outuputStream.write(bytes, 0, bytes.length);
	                outuputStream.flush();
	                outuputStream.close();
	            } catch (Exception e) {
	                System.err.println("Error exporting to Excel: " + e.getMessage());
	                e.printStackTrace();
	            }
	        } else {
	            System.err.println("Invalid option: " + option);
	        }
	    } catch (Exception e) {
	        System.err.println("Error in excelshow method: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	}

	
	

