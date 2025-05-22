package Servlets.PMS.PMS1.DCB.reports;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class Other_Head_Office_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	String report_name;
	public Other_Head_Office_Report() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType(CONTENT_TYPE);
		Map parameters = new HashMap();
		Controller obj = new Controller();
		Connection con=null;
		String month1="0",month2="0",year1="0",year2="0",path="",option="0";
		OutputStream outuputStream2=null;
		JasperPrint jasperPrint2 =null;
		try 
		{
			con = obj.con();
			obj.createStatement(con);
			month1= obj.setValue("pmonth", request);
			year1 = obj.setValue("pyear", request);
			month2= obj.setValue("pmonth2", request);
			year2 = obj.setValue("pyear2", request);
			report_name	="Ben_Type_Count_pumping"	;  	
			option= obj.setValue("reporttype", request);
			String Date1="1-"+month1+"-"+year1;
			String Date2=obj.month_val2(Integer.parseInt(month2))+"-"+month2+"-"+year2;
	 		 System.out.println("DCB->Other_Head_Office_Report->option->" + option 	+ " " );
			parameters.put("year1", year1);
			parameters.put("month1", obj.month_val(month1));
			parameters.put("year2", year2);  
			parameters.put("month2", obj.month_val(month2));
			parameters.put("date1", Date1);
			parameters.put("date2", Date2); 
			obj.testQry(Date1);  
			obj.testQry(Date2);
			if (Integer.parseInt(year1)==Integer.parseInt(year2) &&  Integer.parseInt(month1)==Integer.parseInt(month2))
				parameters.put("label", obj.month_val(month1)+" " +Integer.parseInt(year1) );
			else
				parameters.put("label", "From " + obj.month_val(month1)+" " +Integer.parseInt(year1) +" to "+ obj.month_val(month2)+" " +Integer.parseInt(year2));
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Count_Type_wise.jasper");  
			outuputStream2 = response.getOutputStream();
			jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);
			if (Integer.parseInt(option)==1)
				reportPdf(response, jasperPrint2, outuputStream2);
			else if(Integer.parseInt(option)==2)
				excelshow(response, jasperPrint2, outuputStream2);
			else  if(Integer.parseInt(option)==3)
				htmlshow(response, jasperPrint2, outuputStream2); 
			
			   
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
	protected void reportPdf(HttpServletResponse response,JasperPrint jasperPrint, OutputStream outuputStream2) 
	{
		
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
