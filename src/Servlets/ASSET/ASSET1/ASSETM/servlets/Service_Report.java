package Servlets.ASSET.ASSET1.ASSETM.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Statement;
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

/**
 * Servlet implementation class Service_Report
 */
public class Service_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Service_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/pdf");  
		Map parameters = new HashMap();
		Controller obj = new Controller();
		Connection con = null;
		String command = request.getParameter("command");// Command
		OutputStream outuputStream2 =null;
		JasperPrint jasperPrint2 =null;
		if (command == null || command.equals("")) 	command = "no command";
		Statement stmt = null;
		String xml = "", qry = "", Office_id = "0", Office_name = "", userid = "0",schno="";
		String path="";
		System.out.println("ASSET->Service_report->command->" + command);  
		try {
			con = obj.con();
			stmt = con.createStatement();
			obj.createStatement(con);
		 	con= obj.con();
		 	schno=obj.setValue("sno", request);
			HttpSession session = request.getSession(false);
			userid = (String) session.getAttribute("UserId");
			if (userid == null) 
			{
				//userid ="twad10950";
					response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
			if (Office_name.equals("")) Office_name = "";
			if (command.trim().equalsIgnoreCase("PDF"))
			{
			    parameters.put("sch_no",schno ); System.out.println(schno); 
				parameters.put("off_id", Office_id);
				path = getServletContext().getRealPath("/WEB-INF/PDF/Ser_Reservoir_report.jasper");
				response.setHeader("Content-Disposition","attachment; filename=\"Ser_Reservoir_report.pdf\"");
				outuputStream2 = response.getOutputStream();  
				jasperPrint2 = JasperFillManager.fillReport(path,parameters, con);
				JRExporter exporter = null;
				try {
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint2);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream2);
					exporter.exportReport();
					outuputStream2.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		}// TODO Auto-generated method stub
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
