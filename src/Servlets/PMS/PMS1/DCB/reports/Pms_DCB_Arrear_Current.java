/*
 * 	Name :Panneer Selvam.K
 *  Date :19/07/2014
 *  Purpose : Arrear Report
 *  
 * */
package Servlets.PMS.PMS1.DCB.reports;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
public class Pms_DCB_Arrear_Current extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String option="";
    public Pms_DCB_Arrear_Current() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Map parameters = new HashMap();
		Controller obj = new Controller();
		OutputStream outuputStream_dcb=null;
		 
		JasperPrint jasperPrint_dcb=null ;
		String command = request.getParameter("command");// Command
		if (command == null || command.equals("")) 	command = "no command";
	
		Connection con = null;
		try {
			con = obj.con();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			Statement stmt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}  
		String month="0",year="0",option="0",reporttype="0";
		month=obj.setValue("month", request);
		year=obj.setValue("year", request);
		this.option=obj.setValue("option", request);
		reporttype=obj.setValue("reporttype", request);
		int prv_month=0,prv_year=0,arr_year=0,cur_year=0,enddate_prv=0,enddate_cur=0;
		String arr_set="",cur_set="";
		if (Integer.parseInt(month)<4)
		{
		     
			  
			 if (Integer.parseInt(month)==1)
			 {  
				 prv_month=12;
				 prv_year=Integer.parseInt(year)-1;
				 arr_year=Integer.parseInt(year)-1;
				 arr_set =Integer.parseInt("12")+"/"+(Integer.parseInt(year)-1);
				 cur_set =Integer.parseInt("1")+"/"+(Integer.parseInt(year));
				 cur_year=Integer.parseInt(year);
				
			 }
			 else  
			 {  
				 prv_month=Integer.parseInt(month)-1;
				 prv_year=Integer.parseInt(year);  
				 arr_year=Integer.parseInt(year)-1;
				 arr_set =(Integer.parseInt(month)-1)+"/"+(Integer.parseInt(year)-1);
				 cur_set =Integer.parseInt(month)+"/"+(Integer.parseInt(year));
				 cur_year=Integer.parseInt(year);
			 }
		}else
		{
   
			 prv_month=Integer.parseInt(month)-1;
			 prv_year=Integer.parseInt(year);
			 arr_year=Integer.parseInt(year);
			 arr_set =(Integer.parseInt(month)-1)+"/"+(Integer.parseInt(year));
			 cur_set =Integer.parseInt(month)+"/"+(Integer.parseInt(year));
			 cur_year=Integer.parseInt(year);
		}
		 enddate_prv=obj.month_val2(prv_month,prv_year) ;
		 enddate_cur=obj.month_val2(Integer.parseInt(month),Integer.parseInt(year)) ;
		System.out.println("DCB->Pms_DCB_Arrear_Current ->reporttype-->"+reporttype+"-->option->"+option+"->command->" +command);
		String path="";
		if (reporttype.equals("1"))
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_wise DCB_abs.jasper");
		}else if (reporttype.equals("2"))
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_wise DCB_new.jasper");
		}else if (reporttype.equals("3"))
		{
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_wise DCB_vb_dis_.jasper");
		}
		try
		{  
				
				parameters.put("year",year);
				parameters.put("month",month);
				parameters.put("prv_month",prv_month);
				parameters.put("prv_year",prv_year);  
				parameters.put("cur_year",cur_year);
				parameters.put("arr_year",arr_year);    
				parameters.put("arr_set",arr_set);  
				parameters.put("month_val", obj.month_val(month)+"-"+year);
				parameters.put("cur_set",cur_set);
				parameters.put("enddate_prv",enddate_prv);
				parameters.put("enddate_cur",enddate_cur);  
				jasperPrint_dcb = JasperFillManager.fillReport(path, parameters, con);
				outuputStream_dcb = response.getOutputStream();
		}catch(Exception e) {}
				pdfshow( response,  jasperPrint_dcb,  outuputStream_dcb);   
	}  

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
	public void pdfshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		if (option.equalsIgnoreCase("2"))  
		{   
			excelshow( response, jasperPrint, outuputStream);
		}else
		{
			try {
			  JRExporter exporter = new JRPdfExporter();
			  response.setContentType("application/pdf");
			  response.setHeader("Content-Disposition","attachment; filename=\"Report.pdf\"");
			  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
			  exporter.exportReport();
			  outuputStream.close();  
			  outuputStream.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}   
		}
	}
	public void excelshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		if (option.equalsIgnoreCase("1"))
		{
			pdfshow( response, jasperPrint, outuputStream);
		}else  
		{
			try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment;filename=\"Report.xls\"");
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
}
