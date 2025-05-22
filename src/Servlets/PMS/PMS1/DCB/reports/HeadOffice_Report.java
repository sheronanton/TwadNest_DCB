package Servlets.PMS.PMS1.DCB.reports;
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
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class HeadOffice_Report extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public HeadOffice_Report() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String userid="0",Office_id="";
		Controller obj=new Controller();
		response.setContentType("application/pdf");
		Connection con;
		try 
		{
				con=obj.con();
				obj.createStatement(con);
		 		HttpSession session=request.getSession(true);
		 		userid=(String)session.getAttribute("UserId");
		 		if(userid==null)
				{
				 	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				String process =obj.setValue("process", request);
		 		  System.out.println("HeadOffice_Report-->Process-->"+process);
		 		Map p=new HashMap();
		 		p.put("month", obj.setValue("fmonth", request));
	 			p.put("year", obj.setValue("fyear", request));
	 			p.put("mvalue",obj.month_val(obj.setValue("fmonth", request))); 
	 			String path="";  
	 			String report_title="";  
		 		if (process.equalsIgnoreCase("1"))
		 		{
		 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_ABC.jasper");
		 			response.setHeader("Content-Disposition","attachment;filename=HO_ABSs.pdf");  
		 		}else if (process.equalsIgnoreCase("2"))  
		 		{  
		 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_Collection_District.jasper");
		 			 response.setHeader("Content-Disposition","attachment;filename=\"HO_Village_Panchayats.pdf\"");
		 		}else if (process.equalsIgnoreCase("3"))
		 		{
		 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_Collection_tp.jasper");
		 			 response.setHeader("Content-Disposition","attachment;filename=\"Townpancharyat.pdf\"");     
		 		}else if (process.equalsIgnoreCase("4"))
		 		{
		 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_Collection_MP.jasper");
		 		 	response.setHeader("Content-Disposition","attachment;filename=\"Municipalities.pdf\"");
		 		}else if (process.equalsIgnoreCase("5"))
		 		{
		 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_Collection2.jasper");
		 			 response.setHeader("Content-Disposition","attachment;filename=\"Corporations.pdf\"");
		 		}
	 			OutputStream outp=response.getOutputStream();
	 			JasperPrint jf;				 			
	 			jf=JasperFillManager.fillReport(path,p,con);  
	 			JRExporter jrf;
	 			jrf=new JRPdfExporter();
	 			jrf.setParameter(JRExporterParameter.JASPER_PRINT, jf);
	 			jrf.setParameter(JRExporterParameter.OUTPUT_STREAM,outp);
	 			jrf.exportReport();
	 			outp.close();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
