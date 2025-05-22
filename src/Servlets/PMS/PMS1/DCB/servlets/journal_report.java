/* 
  * Created on : dd-mm-yy 
  * Author     : Panneer Selvam.K
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description
  * 17/09/2011		Add the Beneficiary Status to 'L'  
  *---------|---------------|--------------------------------------------------- 
  */
package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class journal_report
 */
public class journal_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static Connection con = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public journal_report() {
		super();
		//   Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//   Auto-generated method stub
		  String new_cond=Controller.new_cond;
		PrintWriter pr = response.getWriter();
		response.setContentType(CONTENT_TYPE);
		Controller obj = new Controller();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null, rs2 = null;

		// Office id & user id Start
		String Office_id = "0";
		String userid = "0";
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
			//  Auto-generated catch block
			e1.printStackTrace();
		}
		if (Office_id.equals(""))
			Office_id = "0";

		// Office id & user id End

		String xml = "<result>";
		rs = null;
		String BENEFICIARY_SNO = "0", QTY = "0", TARIFF_RATE = "0", WC_SLAB_AMT = "0";
		String benname = "";
		String ben_sel = request.getParameter("ben_sel");
		String fyear1 = request.getParameter("fyear");
		String fmonth1 = request.getParameter("fmonth");
		String qry = "select BENEFICIARY_SNO,QTY,TARIFF_RATE,WC_SLAB_AMT from PMS_DCB_WC_SLAB where BENEFICIARY_SNO="
				+ ben_sel
				+ " and  MONTH="
				+ fmonth1
				+ " and   YEAR="
				+ fyear1
				+ " and  OFFICE_ID =" + Office_id;
		try {

			con = obj.con();
			stmt = con.createStatement();
			obj.createStatement(con);

			rs = obj.getRS(qry);

			while (rs.next()) 
			{
				BENEFICIARY_SNO = obj.isNull(rs.getString("BENEFICIARY_SNO"), 1);
				benname = obj.isNull(obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME", "where "+new_cond+" BENEFICIARY_SNO="+BENEFICIARY_SNO), 2);
				QTY = obj.isNull(rs.getString("QTY"), 1);
				TARIFF_RATE = obj.isNull(rs.getString("TARIFF_RATE"), 1);
				WC_SLAB_AMT = obj.isNull(rs.getString("WC_SLAB_AMT"), 1);
				xml += "<result>" + benname + "</result>";
				xml += "<QTY>" + QTY + "</QTY>";
				xml += "<TARIFF_RATE>" + TARIFF_RATE + "</TARIFF_RATE>";
				xml += "<WC_SLAB_AMT>" + WC_SLAB_AMT + "</WC_SLAB_AMT>";
			}
			xml += "</result>";
			pr.write(xml);
			pr.flush();
			pr.close();
			obj.conClose(con);
		} catch (SQLException e) {
			//  Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			//  Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//  Auto-generated method stub
	}

}
