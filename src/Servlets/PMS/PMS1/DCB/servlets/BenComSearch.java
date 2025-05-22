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
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Servlets.Security.classes.UserProfile;
public class BenComSearch extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String new_cond=Controller.new_cond;
		try {
			ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs.getString("Config.DSN");
			String strhostname = rs.getString("Config.HOST_NAME");
			String strportno = rs.getString("Config.PORT_NUMBER");
			String strsid = rs.getString("Config.SID");
			String strdbusername = rs.getString("Config.USER_NAME");
			String strdbpassword = rs.getString("Config.PASSWORD");
	//		ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
		//			+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
			}
		} catch (Exception e) {
		}
		response.setContentType(CONTENT_TYPE);
		String strCommand = "";
		String xml = "";
		int page = 1;
		int total = 2;
		int records = 15;
		int start = 1;
		int limit = 10;
		int end = 10;
		String dis = "%";
		String blk = "%";
		String pan = "%";
		String ULBtype = "%";
		String ULBgrade = "%";
		String area = "%";
		String ulb = "%";
		String priv = "%";
		int ctype = 0;
		String cname = "";
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
		} catch (Exception e) {
		}
		String userid = (String) session.getAttribute("UserId");
		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		UserProfile empProfile = (UserProfile) session.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		try {
			strCommand = request.getParameter("command");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
		}
		try {
			limit = Integer.parseInt(request.getParameter("limit"));
		} catch (Exception e) {
		}
		try {
			dis = request.getParameter("dis");
			if ((dis == "") || ((dis == null))) {
				dis = " SELECT DISTRICT_CODE "
						+ " FROM HRM_EMP_CURRENT_POSTING a JOIN PMS_DCB_DIV_DIST_MAP b "
						+ " ON a.OFFICE_ID=b.OFFICE_ID "
						+ " WHERE EMPLOYEE_ID= " + empid;
			}
		} catch (Exception e) {
			dis = "%";
		}
		try {
			ctype = Integer.parseInt(request.getParameter("ctype"));
		} catch (Exception e) {
		}
		try {
			cname = request.getParameter("cname");
		} catch (Exception e) {
		}
		// ///////////////////// Town Panchayat //////////////////////////
		try {
			blk = request.getParameter("blk");
			if ((blk == "") || (blk == null)) {
				blk = "%";
				System.out.println("blk =======> %");
			}
		} catch (Exception e) {
			blk = "%";
		}
		try {
			pan = request.getParameter("pan");
			if ((pan == "") || (pan == null)) {
				pan = "%";
			}
		} catch (Exception e) {
			pan = "%";
		}
		// /////////////////////////////////////////////////////////////////////
		// ///////////////////// Urban LB //////////////////////////
		try {
			ULBtype = request.getParameter("ULBtype");
			if ((ULBtype == "") || (ULBtype == null)) {
				ULBtype = "%";
			}
		} catch (Exception e) {
		}
		try {
			ULBgrade = request.getParameter("ULBgrade");
			if ((ULBgrade == "") || (ULBgrade == null)) {
				ULBgrade = "%";
			}
		} catch (Exception e) {
		}
		try {
			area = request.getParameter("area");
			if ((area == "") || (area == null)) {
				area = "%";
			}
		} catch (Exception e) {
		}
		try {
			ulb = request.getParameter("ulb");
			if ((ulb == "") || (ulb == null)) {
				ulb = "%";
				System.out.println("ulb =======> %");
			}
		} catch (Exception e) {
			System.out.println("Exception fetching ulb ===> " + e);
		}
		// /////////////////////////////////////////////////////////////////////
		// ///////////////////// Private & Others //////////////////////////
		try {
			priv = request.getParameter("priv");
			if ((priv == "") || (priv == null)) {
				priv = "%";
			}
		} catch (Exception e) {
			System.out.println("Exception fetching priv ===> " + e);
		}
		/******************************************************************/
		if (strCommand.equals("TypeCategory")) {
			xml = "<response><command>TypeCategory</command>";
			try {
				result = statement.executeQuery("SELECT " + "  BEN_TYPE_DESC, "
						+ "  BEN_TYPE_CATEGORY " + "FROM PMS_DCB_BEN_TYPE "
						+ "WHERE BEN_TYPE_ID=" + ctype);
				try {
					xml = xml + "<flag>success</flag>";
					if (result.next()) {
						xml += "<row>";
						xml += "<type>" + result.getString("BEN_TYPE_DESC")
								+ "</type>";
						xml += "<categ>"
								+ result.getString("BEN_TYPE_CATEGORY")
								+ "</categ>";
						xml += "</row>";
					}
				} catch (Exception e) {
					System.out
							.println("Exception in the getting values from TypeCategory query: "
									+ e);
				}
				result.close();
			} catch (Exception e1) {
				System.out.println("Exception in TypeCategory query" + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		else if (strCommand.equals("District")) {
			xml = "<response><command>District</command>";
			try {
				result = statement
						.executeQuery("SELECT DISTRICT_CODE,DISTRICT_NAME "
								+ "FROM COM_MST_DISTRICTS  "
								+ "WHERE DISTRICT_CODE "
								+ "      IN  "
								+ "      ( "
								+ "        SELECT DISTRICT_CODE "
								+ "        FROM HRM_EMP_CURRENT_POSTING a JOIN PMS_DCB_DIV_DIST_MAP b "
								+ "        ON a.OFFICE_ID=b.OFFICE_ID "
								+ "        WHERE EMPLOYEE_ID= " + empid
								+ "      )");
				try {
					xml = xml + "<flag>success</flag>";
					while (result.next()) 
					{
						xml += "<row>";
						xml += "<dis>" + result.getInt("DISTRICT_CODE")+ "</dis>";
						xml += "<district>" + result.getString("DISTRICT_NAME")+ "</district>";
						xml += "</row>";
					}
				} catch (Exception e) 
				{
					System.out.println("Exception in the getting values from District query: "+ e);
				}
				result.close();
			} catch (Exception e1) 
			{
				System.out.println("Exception in District query" + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		else if (strCommand.equals("Block")) 
		{
			xml = "<response><command>Block</command>";
			try {
				result = statement.executeQuery("SELECT " + "district_code, "
						+ "block_sno, " + "block_name "
						+ "FROM com_mst_blocks " + "WHERE district_code=" + dis
						+ " ORDER BY block_name");
				try {
					xml = xml + "<flag>success</flag>";
					while (result.next()) 
					{
						xml += "<row>";
						xml += "<dis>" + result.getInt("district_code")+ "</dis>";
						xml += "<blk>" + result.getInt("block_sno") + "</blk>";
						xml += "<block>" + result.getString("block_name")+ "</block>";
						xml += "</row>";
					}
				} catch (Exception e) 
				{
					System.out.println("Exception in the getting values from Block query: "+ e);
				}
				result.close();
			} catch (Exception e1) {
				System.out.println("Exception in Block query" + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		else if (strCommand.equals("Get")) {
			xml = "<response><command>Get</command>";
			try {
				System.out.println("SELECT COUNT(*) AS rec " + "FROM " + "  ( "
						+ "    SELECT * " + "    FROM PMS_DCB_MST_BENEFICIARY "
						+ "    WHERE "+new_cond+" DISTRICT_CODE IN ("
						+ dis
						+ ") "
						+ "    AND BLOCK_SNO LIKE '"
						+ blk
						+ "' "
						+ "    AND OTHERS_PRIVATE_SNO LIKE '"
						+ priv
						+ "' "
						+ "    AND BENEFICIARY_TYPE_ID LIKE '"
						+ ctype
						+ "' "
						+ "    AND LOWER(BENEFICIARY_NAME) LIKE '"
						+ cname
						+ "%' "
						+ "    AND OFFICE_ID = "
						+ "      ( SELECT OFFICE_ID "
						+ "		 FROM HRM_EMP_CURRENT_POSTING "
						+ "		 WHERE EMPLOYEE_ID = "
						+ empid
						+ "      ) "
						+ "  )BEN "
						+
						"LEFT OUTER JOIN "
						+
						"  (  "
						+ "    SELECT "
						+ "      URBANLB_SNO, "
						+ "      URBANLB_GRADE_ID, "
						+ "      AREA_TYPE_ID  "
						+ "    FROM COM_MST_URBAN_LB "
						+ "  )ULB "
						+ "ON BEN.URBANLB_SNO=ULB.URBANLB_SNO "
						+ "WHERE (URBANLB_GRADE_ID LIKE '"
						+ ULBgrade
						+ "' "
						+ "OR URBANLB_GRADE_ID IS NULL) "
						+ "AND (AREA_TYPE_ID LIKE '"
						+ area
						+ "' "
						+ "OR AREA_TYPE_ID IS NULL)");
				result = statement.executeQuery("SELECT COUNT(*) AS rec "
						+ "FROM " + "  ( " + "    SELECT * "
						+ "    FROM PMS_DCB_MST_BENEFICIARY "
						+ "    WHERE "+new_cond+" DISTRICT_CODE IN ("
						+ dis
						+ ") "
						+ "    AND BLOCK_SNO LIKE '"
						+ blk
						+ "' "
						+ "    AND OTHERS_PRIVATE_SNO LIKE '"
						+ priv
						+ "' "
						+ "    AND BENEFICIARY_TYPE_ID LIKE '"
						+ ctype
						+ "' "
						+ "    AND LOWER(BENEFICIARY_NAME) LIKE '"
						+ cname
						+ "%' "
						+ "    AND OFFICE_ID = "
						+ "      ( SELECT OFFICE_ID "
						+ "		 FROM HRM_EMP_CURRENT_POSTING "
						+ "		 WHERE EMPLOYEE_ID = "
						+ empid
						+ "      ) "
						+ "  )BEN "
						+
						"LEFT OUTER JOIN "
						+
						"  (  "
						+ "    SELECT "
						+ "      URBANLB_SNO, "
						+ "      URBANLB_GRADE_ID, "
						+ "      AREA_TYPE_ID  "
						+ "    FROM COM_MST_URBAN_LB "
						+ "  )ULB "
						+ "ON BEN.URBANLB_SNO=ULB.URBANLB_SNO "
						+ "WHERE (URBANLB_GRADE_ID LIKE '"
						+ ULBgrade
						+ "' "
						+ "OR URBANLB_GRADE_ID IS NULL) "
						+ "AND (AREA_TYPE_ID LIKE '"
						+ area
						+ "' "
						+ "OR AREA_TYPE_ID IS NULL)");
				if (result.next()) {
					records = result.getInt("rec");
					System.out.println("Total records : " + records);
				}
			} catch (Exception e1) {
				System.out.println("Exception in Counting Records query (Get) ==> "+ e1);
			}
			start = (page - 1) * limit + 1;
			end = start + limit - 1;
			total = (int) Math.ceil((float) records / limit);
			try {
				result = statement
						.executeQuery("SELECT " + "	BENEFICIARY_SNO, "
								+ "   BENEFICIARY_NAME, "
								+ "   DISTRICT_NAME, "
								+ "   CASE WHEN BLOCK_NAME IS NULL THEN '-' "
								+ "        ELSE BLOCK_NAME "
								+ "   END AS BLOCK_NAME, " + "   CASE "
								+ "     WHEN URBANLB_GRADE_ID IS NULL "
								+ "     THEN '-' "
								+ "     ELSE URBANLB_GRADE_ID "
								+ "   END AS URBANLB_GRADE_ID, " + "   CASE "
								+ "     WHEN AREA_TYPE_ID IS NULL "
								+ "     THEN '-' " + "     ELSE AREA_TYPE_ID "
								+ "   END AS AREA_TYPE_ID " + " FROM " + " ( "
								+ "   SELECT " + "	  ROWNUM AS ID,"
								+ "     BENEFICIARY_SNO, "
								+ "     DISTRICT_CODE, " + "     BLOCK_SNO, "
								+ "     BENEFICIARY_NAME, "
								+ " 	  URBANLB_SNO "
								+ "   FROM PMS_DCB_MST_BENEFICIARY "
								+ "   WHERE "+new_cond+" DISTRICT_CODE IN ("
								+ dis
								+ ") "
								+ "     AND BLOCK_SNO LIKE '"
								+ blk
								+ "' "
								+ "     AND OTHERS_PRIVATE_SNO LIKE '"
								+ priv
								+ "' "
								+ "     AND BENEFICIARY_TYPE_ID LIKE '"
								+ ctype
								+ "' "
								+ "     AND LOWER(BENEFICIARY_NAME) LIKE '"
								+ cname
								+ "%' "
								+ "     AND OFFICE_ID = ( SELECT OFFICE_ID "
								+ "                       FROM HRM_EMP_CURRENT_POSTING "
								+ "						WHERE EMPLOYEE_ID= "
								+ empid
								+ " ) "
								+ " )BEN "
								+
								" LEFT OUTER JOIN "
								+
								" ( "
								+ "   SELECT "
								+ "     DISTRICT_CODE, "
								+ "     DISTRICT_NAME "
								+ "   FROM COM_MST_DISTRICTS "
								+ " )DIS "
								+ " ON DIS.DISTRICT_CODE=BEN.DISTRICT_CODE "
								+
								" LEFT OUTER JOIN "
								+
								" ( "
								+ "   SELECT "
								+ "     BLOCK_SNO, "
								+ "     BLOCK_NAME "
								+ "   FROM COM_MST_BLOCKS "
								+ " )BLK "
								+ " ON BEN.BLOCK_SNO=BLK.BLOCK_SNO "
								+
								" LEFT OUTER JOIN "
								+
								" ( "
								+ "   SELECT "
								+ "     URBANLB_SNO, "
								+ "     URBANLB_GRADE_ID, "
								+ "     AREA_TYPE_ID "
								+ "   FROM COM_MST_URBAN_LB "
								+ " )ULB "
								+ " ON BEN.URBANLB_SNO=ULB.URBANLB_SNO "
								+ " WHERE (URBANLB_GRADE_ID LIKE '"
								+ ULBgrade
								+ "' "
								+ "	  OR URBANLB_GRADE_ID IS NULL) "
								+ "	  AND (AREA_TYPE_ID LIKE '"
								+ area
								+ "' "
								+ "	  OR AREA_TYPE_ID IS NULL) "
								+ "	  AND id BETWEEN "
								+ start
								+ " AND "
								+ end
								+ " " + "	ORDER BY BENEFICIARY_NAME ");
				try {
					xml = xml + "<flag>success</flag>";
					xml = xml + "<page>" + page + "</page>";
					xml = xml + "<total>" + total + "</total>";
					xml = xml + "<records>" + records + "</records>";
					while (result.next()) 
					{
						xml += "<row>";
						xml += "<sno>" + result.getInt("BENEFICIARY_SNO")+ "</sno>";
						xml += "<cname>" + result.getString("BENEFICIARY_NAME")+ "</cname>";
						xml += "<district>" + result.getString("DISTRICT_NAME")+ "</district>";
						xml += "<block>" + result.getString("BLOCK_NAME")+ "</block>";
						xml += "</row>";
					}
				} catch (Exception e) 
				{
					System.out.println("Exception fetching values from 'Get' resultset ==> "+ e);
				}
				result.close();
			} catch (Exception e1) {
				System.out.println("Exception in 'Get' query ==> " + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		else if (strCommand.equals("ULBgrade")) 
		{
			xml = "<response><command>ULBgrade</command>";
			try {
				result = statement.executeQuery("SELECT URBANLB_GRADE_ID, "
						+ "  URBANLB_GRADE_DESC "
						+ "FROM COM_MST_URBAN_LB_GRADE");
				try 
				{
					xml = xml + "<flag>success</flag>";
					while (result.next()) 
					{
						xml += "<row>";
						xml += "<gid>" + result.getInt("URBANLB_GRADE_ID")+ "</gid>";
						xml += "<gdesc>"+ result.getString("URBANLB_GRADE_DESC")+ "</gdesc>";
						xml += "</row>";
					}
				} catch (Exception e) 
				{
					System.out.println("Exception in the getting values from ULBgrade query: "+ e);
				}
				result.close();
			} catch (Exception e1) {
				System.out.println("Exception in ULBgrade query" + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		pw.write(xml);
		pw.flush();
	}
}
