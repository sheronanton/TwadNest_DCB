/* 
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 21/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 
 *-----------------------------------------------------------------------------
 */
package Servlets.PMS.PMS1.DCB.reports;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class HO_menu_index extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";   
    public HO_menu_index() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj = new Controller();
		Connection con = null;
		String process="";
		String command="";
		try
		{
			command=obj.setValue("command", request);
			process=obj.setValue("ref_sno", request);
			con = obj.con();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			String userid = "0", Office_id = "0";
			try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {
				userid = "0";
			}
			if (userid == null) {
				userid = "0";
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING",	"OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')"), 1));
			if (Office_id.equals("0"))
				Office_id = "0";
		    System.out.println("DCB->reg_menu_index->command->" + command 	+ "->process->" + process);			
			String oid=obj.setValue("oid", request);
			if (oid.equalsIgnoreCase("0")) Office_id=Office_id; else Office_id=oid;
			 String office_=obj.getValue("COM_MST_OFFICES","OFFICE_NAME"," where OFFICE_ID="+Office_id);	  
			String MONTH = obj.setValue("month", request);
			String YEAR = obj.setValue("year", request);
			Map parameters = new HashMap(); 
			parameters.put("office_id", Office_id );				 
			parameters.put("year",  YEAR );
			parameters.put("month",  MONTH );
		    parameters.put("monthval",obj.month_val(MONTH));
		    JRExporter exporter = new JRPdfExporter();
		    if (command.equalsIgnoreCase("Setting"))
			  {
		    	PrintWriter pw = response.getWriter();  
				response.setHeader("Cache-Control", "no-cache");
				String month=request.getParameter("pmonth");
				String year=request.getParameter("pyear");
				session.setAttribute("dcbmonth", month);
				session.setAttribute("dcbyear", year);
				session.setAttribute("dcbflag", "1");
				String xml = "<response><command>Setting</command><msg>Month and Year Setting Done</msg></response>";
				pw.write(xml);
				pw.flush();
				pw.close();
			  }
	try {
			OutputStream outuputStream =null;
			String path="";
			outuputStream = response.getOutputStream();
			String	imagespath = getServletContext().getRealPath("/images/")+File.separator;
			parameters.put("imgpath", imagespath);  
			if (Integer.parseInt(process)==1 || Integer.parseInt(process)==7) 
			{
				 if (Integer.parseInt(process)==7)
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F_Actual.jasper");
				 else
				    	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F.jasper");
				  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				  response.setContentType("application/pdf");
				  response.setHeader("Content-Disposition","attachment; filename=\"LedgerReport1.pdf\"");
				  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				  exporter.exportReport();
				  outuputStream.close();
				  outuputStream.flush();
		  }
		  else if (Integer.parseInt(process)==2 || Integer.parseInt(process)==8)					 
		  {
				 if (Integer.parseInt(process)==8)
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_F_Actual.jasper");
				 else
					 	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_F.jasper");					 
				JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition","attachment; filename=\"LedgerReport1.pdf\"");
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				exporter.exportReport();
				outuputStream.close();
				outuputStream.flush();
		 }else if (Integer.parseInt(process)==3 || Integer.parseInt(process)==9)
		 {
				  if (Integer.parseInt(process)==9)
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_F_Actual.jasper");
				  else	 
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_F.jasper");
				  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				  response.setContentType("application/pdf");
				  response.setHeader("Content-Disposition","attachment; filename=\"LedgerReport1.pdf\"");
				  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				  exporter.exportReport();
				  outuputStream.close();
				  outuputStream.flush();
		}else if (Integer.parseInt(process)==4 || Integer.parseInt(process)==10)
		{
				 if (Integer.parseInt(process)==10)
					   path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_F_Actual.jasper");
				 else
					   path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_F.jasper");
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				 response.setContentType("application/pdf");
				 response.setHeader("Content-Disposition","attachment; filename=\"LedgerReport1.pdf\"");
				 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				 exporter.exportReport();
				 outuputStream.close();
				 outuputStream.flush();
		} else if (Integer.parseInt(process)==5 || Integer.parseInt(process)==11 || Integer.parseInt(process)==13 || Integer.parseInt(process)==14)
	    {
				if (Integer.parseInt(process)==11)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_F_Actual.jasper");
				else  if (Integer.parseInt(process)==13)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_3TWAD_Actual.jasper");
				else if (Integer.parseInt(process)==14)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_3TWAD_F.jasper");
				else
				     path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_F.jasper");
				imagespath = getServletContext().getRealPath("/images/")+File.separator;
				parameters.put("imgpath", imagespath); 
			   JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			   response.setContentType("application/pdf");
			   response.setHeader("Content-Disposition","attachment; filename=\"LedgerReport1.pdf\"");
			   exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
			   exporter.exportReport();
			   outuputStream.close();
			   outuputStream.flush();
		}else if (Integer.parseInt(process)==6 || Integer.parseInt(process)==12)
		{ 
			  if (Integer.parseInt(process)==12)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Prv_F_Actual.jasper");
			 else	 
				 	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Prv_F.jasper");
			   JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
					 response.setContentType("application/pdf");
					 response.setHeader("Content-Disposition","attachment; filename=\"LedgerReport1.pdf\"");
					 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
					 exporter.exportReport();
					outuputStream.close();
					outuputStream.flush();
				 }
				else if (Integer.parseInt(process)==20)
				{
					response.setContentType("application/pdf"); 
					 Map parameters1 = new HashMap();
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Fin_Year_Report1.jasper");
					 parameters1.put("year",  YEAR );
					 parameters1.put("finyear",(Integer.parseInt(YEAR)-1)+"-"+Integer.parseInt(YEAR) );
					 parameters1.put("imgpath", imagespath);  
					 parameters1.put("office_id", Office_id );
					 parameters1.put("off_name", office_ );
					 parameters1.put("month","3" );
					 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
					 response.setHeader("Content-Disposition","attachment; filename=\"Fin_Year_Report1.pdf\"");
					 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
					 exporter.exportReport();
					 outuputStream.close();
					 outuputStream.flush();
				}
			} catch (JRException e) {
				//throw new ServletException(e);
				System.out.println(e);
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
