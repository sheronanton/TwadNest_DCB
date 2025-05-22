


/* 
	* Created on : dd-mm-yy 
* Author     : Panneer Selvam.K
* Last Date  : 21/09/2011
*----------------------------------------------------------------------------- 
* Revision History (Release 1.0.0.0) 
*-----------------------------------------------------------------------------
* Date			Description								Done By			Mode 
*-----------------------------------------------------------------------------
* 
* 26/08/2013	added code for generate statment II		Panneer			M		 
*              Report 
* 27,28,29,30/08/2013	added code for generate statment II		Panneer			M
* 
* 02/09/13		Tariff Revised Report Start 
*             
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
	     public class Collection_amount_new extends HttpServlet {
	     	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	     	private static final long serialVersionUID = 1L;
	     	public Collection_amount_new() 
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
	            	     
	            			  
	            		     String pr=obj.setValue("pr", request);
	            		     String rin=obj.setValue("rin", request);
	            		     String month=obj.setValue("month", request);
	            		     String year=obj.setValue("year", request);
	            		 
	            		     String office_id1=obj.setValue("div", request);
	       
	            		     String ten_word="";
	            		     int tbcount=0;
	            		     int divcount=0;
	            		     String title="Report for Fund Receipt Payment ...";
	            			 String UnitName="";
	            				File reportFile = null;
	            				String strcommand=null;
	            				

	                 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Collection_amount_fas.jasper");
	                 String imagespath = getServletContext().getRealPath("/images/")+File.separator;
	     			parameters.put("imgpath", imagespath); 
	     			parameters.put("cb_month", month); 
	     			parameters.put("cb_year", year); 
	     			parameters.put("office_id", office_id1); 
	                 JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
	                 OutputStream outuputStream = response.getOutputStream();
	                 JRExporter exporter = null;
	                 response.setContentType("application/pdf");
	                 response.setHeader("Content-Disposition","attachment; filename=\"pms_water_charge.pdf\"");
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
