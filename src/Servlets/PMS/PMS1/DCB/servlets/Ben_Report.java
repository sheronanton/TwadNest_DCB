/* 
  * Created on : dd-mm-yy 
  * Author     : Panneer Selvam.K
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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class Ben_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static Connection con = null;
	public Ben_Report() {
		super();	 
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			response.setContentType(CONTENT_TYPE);
			PrintWriter pr = response.getWriter();
			Controller obj = new Controller();
			String new_cond=Controller.new_cond;
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			String xml = "<result>";
			int prow = 0;
			// input area
			String command = request.getParameter("command");// Command view,insert etc
			if (command == null || command.equals(""))
				command = "no command";
			String input_value = request.getParameter("input_value"); // input value
			if (input_value == null || input_value.equals(""))
				input_value = "0";
			String process_code = request.getParameter("process_code");
			if (process_code == null || process_code.equals(""))
			process_code = "0";
			try {
				con = obj.con();
				stmt = con.createStatement();
				obj.createStatement(con);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// Office id & user id Start
		String Office_id = "112";
		String userid = "112";
		HttpSession session = request.getSession(false);
		userid = (String) session.getAttribute("UserId");
		if (userid == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		try {
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (Office_id.equals(""))			Office_id = "0";
		String cond = "",DISTRICT_CODE = "", BLOCK_SNO = "";
		if (process_code.equals("1"))
			cond = new_cond+"   OFFICE_ID=" + Office_id;
		else if (process_code.equals("2"))
			cond =new_cond+ "   OFFICE_ID=" + Office_id + " and BENEFICIARY_TYPE_ID = "+ input_value;
		else if (process_code.equals("3")) 
		{
			DISTRICT_CODE = obj.setValue("DISTRICT_CODE", request);
			cond =new_cond+"   OFFICE_ID=" + Office_id + " and BENEFICIARY_TYPE_ID = "
					+ input_value + " and DISTRICT_CODE=" + DISTRICT_CODE;
		} else if (process_code.equals("4")) {
			DISTRICT_CODE = obj.setValue("DISTRICT_CODE", request);
			BLOCK_SNO = obj.setValue("BLOCK_SNO", request);
			cond = new_cond+"   OFFICE_ID=" + Office_id + " and BENEFICIARY_TYPE_ID = "
					+ input_value + " and DISTRICT_CODE="+DISTRICT_CODE
					+ " and BLOCK_SNO=" + BLOCK_SNO;
		}
		if (process_code.equals("1") || process_code.equals("2")
				|| process_code.equals("3") || process_code.equals("4")) {
			if (command.equals("show")) {
				String qry = "select " + "BEN.BENEFICIARY_NAME,"
						+ "BEN.BENEFICIARY_TYPE_ID," + "BENTYPE.BEN_TYPE_DESC,"
						+ "BEN.OFFICE_ADDRESS1," + "BEN.OFFICE_ADDRESS2,"
						+ "BEN.OFFICE_ADDRESS3," + "BEN.OFFICE_PIN_CODE,"
						+ "BEN.OFFICE_LANDLINE_NO," + "BEN.OFFICE_MOBILE_NO,"
						+ "DIST.DISTRICT_NAME," + "BLK.block_name" + " from  "
						+ "(" + "(" + " select" + " BENEFICIARY_NAME,"
						+ "BENEFICIARY_TYPE_ID," + "DISTRICT_CODE,"
						+ "BLOCK_SNO," + "OFFICE_ADDRESS1,"
						+ "OFFICE_ADDRESS2," + "OFFICE_ADDRESS3,"
						+ "OFFICE_PIN_CODE," + "OFFICE_LANDLINE_NO,"
						+ "OFFICE_MOBILE_NO " + " from "
						+ " PMS_DCB_MST_BENEFICIARY where  "
						+ cond
						+ ""
						+ " order by "
						+ " BENEFICIARY_NAME    "
						+ ")BEN"
						+ " join"
						+ "("
						+ " select"
						+ " BEN_TYPE_ID,"
						+ "BEN_TYPE_DESC "
						+ " from "
						+ " PMS_DCB_BEN_TYPE"
						+ ")BENTYPE "
						+ " ON BENTYPE.BEN_TYPE_ID = BEN.BENEFICIARY_TYPE_ID "
						+ " left join( "
						+ " select DISTRICT_NAME, "
						+ "  DISTRICT_CODE "
						+ " from   COM_MST_DISTRICTS "
						+ " )DIST "
						+ " ON DIST.DISTRICT_CODE = BEN.DISTRICT_CODE "
						+ " left join( "
						+ " select block_name, "
						+ " block_sno "
						+ " from   com_mst_blocks "
						+ " )BLK "
						+ " ON BLK.block_sno = BEN.BLOCK_SNO   "
						+ ")" + "";
				try {
					rs = obj.getRS(qry);
					while (rs.next()) 
					{
						prow++;
						xml += "<BENEFICIARY_NAME>"+ obj.isNull(rs.getString("BENEFICIARY_NAME"),2) + "</BENEFICIARY_NAME>";
						xml += "<BEN_TYPE_SDESC>"+ obj.isNull(rs.getString("BEN_TYPE_DESC"), 2)+ "</BEN_TYPE_SDESC>";
						xml += "<DISTRICT_NAME>"+ obj.isNull(rs.getString("DISTRICT_NAME"), 2)+ "</DISTRICT_NAME>";
						xml += "<block_name>"+ obj.isNull(rs.getString("block_name"), 2)+ "</block_name>";
						xml += "<OFFICE_ADDRESS3>"+obj.isNull(rs.getString("OFFICE_ADDRESS3"),2) + "</OFFICE_ADDRESS3>";
					}
				} catch (Exception e) {
					System.out.println("" + e);
				}
			}
		}
		xml += "<prow>" + prow + "</prow></result>";
		pr.write(xml);
		pr.flush();
		pr.close();
		obj.conClose(con);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	} 
}