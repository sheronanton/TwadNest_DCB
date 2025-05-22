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
 * Servlet implementation class Pumping_report1
 */
public class Dist_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dist_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
		Controller obj = new Controller();
		Connection con = null;
		String command=request.getParameter("comment");
		if(command.equalsIgnoreCase("PDF")){
			 
		try
		{
			con=obj.con();
		obj.createStatement(con);
	 HttpSession session = request.getSession(false);
	 String userid = (String) session.getAttribute("UserId");
	if (userid == null) {
		//userid ="twad10950";
		 response.sendRedirect(request.getContextPath() + "/index.jsp");
	}    
	String sch_no1=obj.setValue("sno", request);
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id == "0") {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	} 
	 			response.setContentType("application/pdf");
	 			Map p= new HashMap(); 
	 			String path = getServletContext().getRealPath("/WEB-INF/PDF/dist ribb_report_1.jasper");
				String ctxpath = path.substring(0, path.lastIndexOf("distribb_report_1.jasper"));	 
	 			p.put("sch_no", sch_no1);
				System.out.println("scno1" + sch_no1);
				p.put("off_id", Office_id);
				System.out.println("Office_id" + Office_id);
				p.put("ctxpath", ctxpath);
				System.out.println(path);
	 			response.setHeader("Content-Disposition","attachment;filename=\"distribb_report_1.pdf\"");
	 		    OutputStream outp=response.getOutputStream();
	 		    JasperPrint jf;				 			
	 		    jf=JasperFillManager.fillReport(path,p,con);
	 			JRPdfExporter jrf;
	 			jrf=new JRPdfExporter();
	 	        jrf.setParameter(JRExporterParameter.JASPER_PRINT, jf);
	 			jrf.setParameter(JRExporterParameter.OUTPUT_STREAM,outp);
	 			jrf.exportReport();
	 			outp.close();
	   }
	   catch (Exception e) 
	   { 		
		  System.out.println(e); 
	   }
}
}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
