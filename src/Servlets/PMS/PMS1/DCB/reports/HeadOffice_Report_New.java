                                                                                            package Servlets.PMS.PMS1.DCB.reports;
import java.io.File;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class HeadOffice_Report_New extends HttpServlet 
{
		private static final long serialVersionUID = 1L;
		public HeadOffice_Report_New() {
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
		 			String imagespath = getServletContext().getRealPath("/images/")+File.separator;
					p.put("imgpath", imagespath); 
		 			File reportFile=null;	 	 			
			 		if (process.equalsIgnoreCase("1"))
			 		{
			 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_ABC_NEW.jasper");
			 			response.setHeader("Content-Disposition","attachment;filename=\"HO_ABC_NEW.pdf\"");		 			
			 		}else if (process.equalsIgnoreCase("2"))  
			 		{  
			 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_VP.jasper");
			 			response.setHeader("Content-Disposition","attachment;filename=\"HO_VP.pdf\"");
			 		}else if (process.equalsIgnoreCase("3"))
			 		{
			 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_TownPan.jasper");
			 			response.setHeader("Content-Disposition","attachment;filename=\"HO_TownPan.pdf\"");     
			 		}else if (process.equalsIgnoreCase("4"))
			 		{
			 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_Mun.jasper");
			 		 	response.setHeader("Content-Disposition","attachment;filename=\"HO_Mun.pdf\"");
			 		}else if (process.equalsIgnoreCase("5"))
			 		{
			 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_Corporation.jasper");
			 			response.setHeader("Content-Disposition","attachment;filename=\"HO_Corporation.pdf\"");
			 		}
			 		reportFile = new File(path);
			 		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
			 		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, p, con);
			 		byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint); 
			 		response.setContentType("application/pdf");
			        response.setContentLength(buf.length);
			        OutputStream out = response.getOutputStream();
		            out.write(buf);
		    }catch(Exception e)
			{
				System.out.println(e);
			}
	}
		/*HO_ABC.jasper	HO_ABC_NEW.jrxml
HO_Collection_District.jasper	HO_VP.jrxml
HO_Collection_tp.jasper	HO_TownPan.jrxml
HO_Collection_MP.jasper	HO_Mun.jrxml
HO_Collection2.jasper	HO_Corporation.jrxml
*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
	 			File reportFile=null;	 	 			
		 		if (process.equalsIgnoreCase("1"))
		 		{
		 			path=getServletContext().getRealPath("WEB-INF\\ReportSrc\\HO_ABC.jasper");
		 			response.setHeader("Content-Disposition","attachment;filename=\"HO_ABSs.pdf\"");		 			
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
		 		reportFile = new File(path);
		 		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		 		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, p, con);
		 		byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint); 
		 		response.setContentType("application/pdf");
		        response.setContentLength(buf.length);
		        OutputStream out = response.getOutputStream();
	            out.write(buf);
	    }catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
