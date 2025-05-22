package Servlets.PMS.PMS1.DCB.reports;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
public class Pms_Dcb_District_Scheme_Mapping extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    public Pms_Dcb_District_Scheme_Mapping() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  Auto-generated method stub
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        Controller obj=new Controller();
        try {
            connection =obj.con();
        	} catch (Exception ex)
        	{
        		String connectMsg ="Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
        	}
        	Map parameters = new HashMap();
        	String path = "";
        	File reportFile=null;
        	try 
        	{
	        	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_division_scheme_reportview.jasper");
	            String imagespath = getServletContext().getRealPath("/images/")+File.separator;
				parameters.put("imgpath", imagespath);    
	            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
	            OutputStream outuputStream = response.getOutputStream();
	            JRExporter exporter = null;
	            response.setContentType("application/pdf");
	            response.setHeader("Content-Disposition","attachment; filename=\"DivisionSchemeMapping.pdf\"");
	            exporter = new JRPdfExporter();
	            exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
	            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
	            exporter.exportReport();
	            outuputStream.close();
        } catch (JRException e) {
            throw new ServletException(e);
        }
	}
}
