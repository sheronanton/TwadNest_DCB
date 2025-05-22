/* 
 * Created on : dd-mm-yy 
 * Author     : SINDU
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
public class Pms_Dcb_District_Division_Mapping extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	public Pms_Dcb_District_Division_Mapping() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
         doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
        Connection connection=null;
        Controller obj=new Controller();
		try {  
            connection =obj.con();  
			} catch (Exception ex)
			{
				String connectMsg ="Could not create the connection" + ex.getMessage()+""+ex.getLocalizedMessage();
			}
        obj.testQry("DCB------->Pms_Dcb_District_Division_Mapping---->");
        Map parameters = new HashMap();
        String path = "";
        try {
            path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_division_district_report.jasper");
            String imagespath = getServletContext().getRealPath("/images/")+File.separator;
			parameters.put("imgpath", imagespath);    
            JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
            OutputStream outuputStream = response.getOutputStream();
            JRExporter exporter = null;
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition","attachment; filename=\"pms_division_district_report.pdf\"");
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
            exporter.exportReport();
            outuputStream.close();
            connection.close(); 
        } catch (Exception e) {
            throw new ServletException(e);
        }
	}
}
