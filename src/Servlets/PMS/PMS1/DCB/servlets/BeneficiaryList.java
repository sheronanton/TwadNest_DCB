/* 
  * Created on : dd-mm-yy 
  * Author     : SINDU
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description
  * 17/09/2011		Add the Beneficiary Status to 'L'  
  * 20/09/2011		Add the Meter Status to 'L'
  *---------|---------------|--------------------------------------------------- 
  */ 
package Servlets.PMS.PMS1.DCB.servlets;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletConfig;
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
import Servlets.Security.classes.UserProfile;
public class BeneficiaryList extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Connection connection = null;
		try {
			String new_cond=Controller.new_cond;
			ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs.getString("Config.DSN");
			String strhostname = rs.getString("Config.HOST_NAME");
			String strportno = rs.getString("Config.PORT_NUMBER");
			String strsid = rs.getString("Config.SID");
			String strdbusername = rs.getString("Config.USER_NAME");
			String strdbpassword = rs.getString("Config.PASSWORD");
		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
		//			+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
		} catch (Exception ex) {
			String connectMsg = "Could not create the connection"
					+ ex.getMessage() + " " + ex.getLocalizedMessage();
		}
		System.out.println("DCB->BeneficiaryList-> ->"  );
		String action = null;  
		String type = null;
		String dis = null;
		String blk = null;
		try {
			action = request.getParameter("action");
		} catch (Exception e) {
		}
		try {
			type = request.getParameter("type");
			if ((type == null) || (type == "")) {
				type = "%";
			}
		} catch (Exception e) {
			System.out.println("Exception getting type parameter ==> " + e);
		}
		try {
			dis = request.getParameter("dis");
			if ((dis == null) || (dis == "")) {
				dis = "%";
			}
		} catch (Exception e) {
			System.out.println("Exception getting dis parameter ==> " + e);
		}
		try {
			blk = request.getParameter("blk");
			if ((blk == null) || (blk == "")) {
				blk = "%";
			}
		} catch (Exception e) {
			System.out.println("Exception getting blk parameter ==> " + e);
		}
		PreparedStatement ps = null;
		ResultSet result = null;
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			System.out.println(session);
		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		UserProfile empProfile = (UserProfile) session.getAttribute("UserProfile");
		int oid = 0;
		String oname = "";
		String userid="0";
		Controller obj=new Controller();
		Connection con=null;
		
		try
		{
		  con=obj.con();
		  obj.createStatement(con);
		  userid=(String)session.getAttribute("UserId");	
		
		  if(userid==null)
		  {
			 	response.sendRedirect(request.getContextPath()+"/index.jsp");
		  }
		}catch(Exception e)
		{
		//	out.println(e); 
			System.out.println(e);
		}
		
		try
     	{
		String	off=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	         oid=Integer. parseInt(off);
			
	//		oid=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

     		//oid=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID ='"+empId+"'","OFFICE_ID") ;

     		oname=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+oid+ "  ");

     	
     	}
     	catch (Exception e)
     	{
     		System.out.println(e);
     	}
     	
		if (action.equalsIgnoreCase("ListAll")) {
			Map parameters = new HashMap();
			parameters.put("Div", oname);
			parameters.put("Off", oid);
			parameters.put("Type", type);
			parameters.put("Dis", dis);
			parameters.put("Blk", blk);
			String imagespath = getServletContext().getRealPath("/images/")+File.separator;
			parameters.put("imgpath", imagespath);
			String path = "";
			try {
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/BeneficiaryList.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(path,parameters, connection);
				OutputStream outuputStream = response.getOutputStream();
				JRExporter exporter = null;
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition","attachment; filename=\"BeneficiaryList.pdf\"");
				exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				exporter.exportReport();
				outuputStream.close();
			} catch (JRException e) {
				throw new ServletException(e);
			}
		}
		if (action.equalsIgnoreCase("Office")) {
			Map parameters = new HashMap();
			parameters.put("Div", oname);
			String path = "";
			try {
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Office_id.jasper");
				JasperPrint jasperPrint = JasperFillManager.fillReport(path,parameters, connection);
				OutputStream outuputStream = response.getOutputStream();
				JRExporter exporter = null;
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition","attachment; filename=\"OfficeId.pdf\"");
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
}
