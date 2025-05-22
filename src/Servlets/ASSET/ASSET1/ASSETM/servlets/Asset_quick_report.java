package Servlets.ASSET.ASSET1.ASSETM.servlets;

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

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import Servlets.PMS.PMS1.DCB.servlets.Controller;

/**
 * Servlet implementation class Asset_quick_report
 */
public class Asset_quick_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Asset_quick_report() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Controller obj = new Controller();
		Connection con = null;
		String command = request.getParameter("comment");
		if (command.equalsIgnoreCase("PDF1")) {
			try {
				con = obj.con();
				obj.createStatement(con);
				HttpSession session = request.getSession(false);
				String userid = (String) session.getAttribute("UserId");
				if (userid == null) {
					response.sendRedirect(request.getContextPath()+ "/index.jsp");
				}
				String sch_no1 = obj.setValue("sno", request);
				String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING",
						"OFFICE_ID",
						"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
								+ userid + "')");
				if (Office_id == "0") 
				{
					response.sendRedirect(request.getContextPath()+ "/index.jsp");
				}
				response.setContentType("application/pdf");
				Map p = new HashMap();
				String path = getServletContext().getRealPath("/WEB-INF/PDF/ref_pumpmain_1.jasper");
				p.put("sch_no", sch_no1);
				p.put("off_id", Office_id);
				response.setHeader("Content-Disposition","attachment;filename=\"ref_pumpmain_1.pdf\"");
				OutputStream outp = response.getOutputStream();
				JasperPrint jf;
				jf = JasperFillManager.fillReport(path, p, con);
				JRPdfExporter jrf;
				jrf = new JRPdfExporter();
				jrf.setParameter(JRExporterParameter.JASPER_PRINT, jf);
				jrf.setParameter(JRExporterParameter.OUTPUT_STREAM, outp);
				jrf.exportReport();
				outp.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// TODO Auto-generated method stub

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
