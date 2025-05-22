

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
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import Servlets.PMS.PMS1.DCB.servlets.Controller;

/**
 * Servlet implementation class Bill_Ledger_Posting_Statistics
 */
public class Bill_Ledger_Posting_Statistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String option="0";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bill_Ledger_Posting_Statistics() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/pdf"); 
		Controller obj = new Controller();
		Connection con = null;
		String process="";
		OutputStream outuputStream =null;
		outuputStream = response.getOutputStream();
		String command="",path="",option="0",YEAR="0",MONTH="0";
		try
		{
			command=obj.setValue("command", request);
			con = obj.con();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			String userid = "0", Office_id = "0";
			try
			{
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) 
			{
				userid = "0";
			}
			if (userid == null) 
			{
				userid = "0";
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			  MONTH=obj.setValue("month", request);
			  YEAR=obj.setValue("year", request);
			  option=obj.setValue("option", request); 
			if (Integer.parseInt(option)==0) option="1";
		}catch(Exception e) 
		{
			   
			              
		}
		  System.out.println("DCB->Bill_Ledger_Posting_Statistics->command->"+command+"->option->"+option);
		 	if (command.equalsIgnoreCase("divisionnames"))
		    {
		     	 
				 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Division_Names.jasper");
				 System.out.println(path);  
				 JasperPrint jasperPrint = null;
				try {
					jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
				} catch (JRException e) {
					 
					e.printStackTrace();
				}   
				  pdfshow( response,  jasperPrint,  outuputStream);
		    }else if (command.equalsIgnoreCase("Statistics"))
		    {      
		    	 response.setContentType("application/pdf");   
				 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/BillLedgerStatistics.jrxml");
				 parameters1.put("year1",YEAR);             
				 parameters1.put("year2", Integer.toString(Integer.parseInt(YEAR)+1));       
				 parameters1.put("year_value","  Bill Generation and Ledger Posting Statistics for financial year " + (Integer.parseInt(YEAR))+"-"+(Integer.parseInt(YEAR)+1)+" " );
				 JasperPrint  jasperPrint = null;
				 try {     
				  JasperDesign jasperDesign;
                  jasperDesign = JasperManager.loadXmlDesign(path);
                  JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                  jasperPrint = JasperManager.fillReport(jasperReport, parameters1, con);  
				 
				  
					  // jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
				} catch (JRException e) {
					 
					e.printStackTrace();
				}
				 pdfshow( response,  jasperPrint,  outuputStream);  
				 System.out.println("End");   
		    }
		    	   
	}
    
	 
	 
	public void pdfshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{  
			System.out.println("PDF -- WRITER ");  
		 
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
