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
public class Assetreport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Assetreport() {
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
		String xml = "", qry = "", Office_id = "0", Office_name = "", userid = "0", ACCOUNTING_UNIT_ID = "";
		String path="",ctxpath="";
		System.out.println("ASSET->Assetreport->command->" + command);  
		try {
			con = obj.con();
			stmt = con.createStatement();
			obj.createStatement(con);
		 	con= obj.con();
			HttpSession session = request.getSession(false);
			userid = (String) session.getAttribute("UserId");
			if (userid == null) 
			{
				  
					response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
			if (Office_name.equals("")) Office_name = "";
			if (command.trim().equalsIgnoreCase("pdf1"))
			{
			    parameters.put("sch_no", obj.setValue("schsno", request));  
				parameters.put("off_id", Office_id);
				parameters.put("office_name",Office_name );
				
				
				System.out.println(obj.setValue("schsno", request));
				System.out.println(Office_id);
				System.out.println(Office_name);
				
				
				path = getServletContext().getRealPath("/WEB-INF/PDF/Assetsch_report_1.jasper");
				ctxpath = path.substring(0, path.lastIndexOf("Assetsch_report_1.jasper"));	 
				parameters.put("ctxpath", ctxpath);
				System.out.println(ctxpath);
				response.setHeader("Content-Disposition","attachment; filename=\"Assetsch_report_1.pdf\"");
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
		}catch(Exception e) {}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
