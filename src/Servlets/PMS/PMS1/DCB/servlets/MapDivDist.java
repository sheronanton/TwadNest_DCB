
/* 
  * Created on : dd-mm-yy 
  * Author     : 
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

public class MapDivDist extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		Controller obj=new Controller();
		try {
			 
			connection = obj.con();
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
		int sno = 0;
		String divi = null;
		String dis = null;
		int page = 1;
		int total = 2;
		int records = 15;
		int start = 1;
		int limit = 10;
		int end = 10;

		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			
		} catch (Exception e) {
		}
		
		
		String userid = (String) session.getAttribute("UserId");
 
		UserProfile empProfile = (UserProfile) session.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		int oid = 0;
		try {
			PreparedStatement ps = connection
					.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
			ps.setInt(1, empid);
			result = ps.executeQuery();
			if (result.next()) {
				oid = result.getInt("OFFICE_ID");
			}
			result.close();
			ps.close();
		} catch (Exception e) {
		}

		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		
		// ///////////////////// Command Parameters //////////////////////////
		try {
			strCommand = request.getParameter("command");
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("DCB->MapDivDist->strCommand->"+strCommand);
		// /////////////////////////////////////////////////////////////////////

		// ///////////////////// Pagination Parameters
		// //////////////////////////
		try {
			page = Integer.parseInt(request.getParameter("page"));
			 
		} catch (Exception e) {
			System.out.println("Exception getting 'page' parameter ==> " + e);
		}

		try {
			limit = Integer.parseInt(request.getParameter("limit"));
			 
		} catch (Exception e) {
			System.out.println("Exception getting 'limit' parameter ==> " + e);
		}
		// /////////////////////////////////////////////////////////////////////

		// ///////////////////// Other Parameters //////////////////////////
		try {
			sno = Integer.parseInt(request.getParameter("sno"));
			 
		} catch (Exception e) {
			System.out.println("Exception fetching sno ===> " + e);
		}

		try {
			divi = request.getParameter("divi");
			if ((divi == null) || ("".equals(divi))) {
				divi = "%";
			}
			 
		} catch (Exception e) {
		}

		try {
			dis = request.getParameter("dis");
			if ((dis == null) || ("".equals(dis))) {
				dis = "%";
			}
			 
		} catch (Exception e) {
		}
		/*******************************************************
		 * DELETE
		 *******************************************************/
		if (strCommand.equalsIgnoreCase("Delete")) {
			   
			xml = "<response><command>Delete</command>";
			try {

				String sql = "DELETE FROM PMS_DCB_DIV_DIST_MAP "
						+ "WHERE SNO = ?";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, sno);
				ps.executeUpdate();

				xml = xml + "<flag>success</flag>";
				xml = xml + "<sno>" + sno + "</sno>";
			} catch (Exception e1) {
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}

		/*******************************************************
		 * UPDATE
		 *******************************************************/

		else if (strCommand.equalsIgnoreCase("Update")) {
			xml = "<response><command>Update</command>";
			// /////////////////////// DUPLICATION CHECK
			// ///////////////////////////////////////////////////////
			int dup = 0;
			try {
				result = statement.executeQuery(" SELECT COUNT(*) AS dup "
						+ "  FROM PMS_DCB_DIV_DIST_MAP "
						+ "  WHERE OFFICE_ID = " + divi
						+ "    AND DISTRICT_CODE = " + dis + "    AND SNO != "
						+ sno);
				try {
					if (result.next()) {
						dup = result.getInt("dup");
					}
				} catch (Exception e) {
				}
				result.close();
			} catch (Exception e1) {
			}
			// /////////////////////////////////////////////////////////////////////////////////////////////////
			if (dup == 0) {
				try {
					String sql = " UPDATE PMS_DCB_DIV_DIST_MAP "
							+ " SET OFFICE_ID = ?, "
							+ "     DISTRICT_CODE = ? " + " WHERE SNO = ?";
					PreparedStatement ps = connection.prepareStatement(sql);

					ps.setInt(1, Integer.parseInt(divi));
					ps.setInt(2, Integer.parseInt(dis));
					ps.setInt(3, sno);

					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
				} catch (Exception e1) {
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";
			} else {
				xml = xml + "<flag>duplicate</flag></response>";
				System.out.println("Duplicate Entry Found - Failed to Update");
			}
		}

		/*******************************************************
		 * ADD
		 *******************************************************/

		else if (strCommand.equalsIgnoreCase("Add")) {
			 
			xml = "<response><command>Add</command>";

			// /////////////////////// DUPLICATION CHECK
			// ///////////////////////////////////////////////////////
			int dup = 0;
			try {
				result = statement.executeQuery(" SELECT COUNT(*) AS dup "
						+ "  FROM PMS_DCB_DIV_DIST_MAP "
						+ "  WHERE OFFICE_ID = " + divi
						+ "    AND DISTRICT_CODE = " + dis + "    AND SNO != "
						+ sno);
				try {
					if (result.next()) {
						dup = result.getInt("dup");
					}
				} catch (Exception e) {
				}
				result.close();
			} catch (Exception e1) {
				System.out.println("Exception is in dup" + e1);
			}
			if (dup == 0) {

				try {
					result = statement
							.executeQuery("SELECT "
									+ "(CASE WHEN MAX(SNO) IS NULL THEN 1 ELSE MAX(SNO)+1 END) AS maxsno "
									+ "FROM PMS_DCB_DIV_DIST_MAP");
					try {
						if (result.next()) {
							sno = result.getInt("maxsno");
						}
					} catch (Exception e) {
					}
					result.close();
				} catch (Exception e1) {
					System.out.println("Exception is in maxsno" + e1);
				}
				try {
					String sqlAdd = "INSERT INTO PMS_DCB_DIV_DIST_MAP " + "( "
							+ "  SNO, " + "  OFFICE_ID, " + "  DISTRICT_CODE "
							+ ") " + "VALUES(?,?,?)";

					PreparedStatement ps = connection.prepareStatement(sqlAdd);
					ps.setInt(1, sno);
					ps.setInt(2, Integer.parseInt(divi));
					ps.setInt(3, Integer.parseInt(dis));

					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
				} catch (Exception e1) {
					System.out.println("Exception in Adding record ===> " + e1);
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";

			} else {
				xml = xml + "<flag>duplicate</flag></response>";
				System.out.println("Duplicate Entry Found");
			}
		} else if (strCommand.equals("Get")) {
			xml = "<response><command>Get</command>";
			try {
				String qry=" SELECT COUNT(*) AS rec "
						+ " FROM PMS_DCB_DIV_DIST_MAP "
						+ " WHERE OFFICE_ID LIKE '" + divi + "' "
						+ "   AND DISTRICT_CODE LIKE '" + dis + "' ";
				result = statement.executeQuery(qry);
				if (result.next()) {
					records = result.getInt("rec");
				}
			} catch (Exception e1) {
			}

			start = (page - 1) * limit + 1;
			end = start + limit - 1;
			total = (int) Math.ceil((float) records / limit);
			try {
				String	qry="SELECT * FROM " + " ("
						+ "   SELECT " + "      ROWNUM AS ID, " + "      SNO, "
						+ "     a.OFFICE_ID, " + "     OFFICE_NAME, "
						+ "     a.DISTRICT_CODE, " + "     DISTRICT_NAME "
						+ "   FROM " + "     ( " + "       SELECT "
						+ "         SNO,  " + "         OFFICE_ID, "
						+ "         DISTRICT_CODE  "
						+ "       FROM PMS_DCB_DIV_DIST_MAP "
						+ "   	 WHERE OFFICE_ID="
						+ divi
						+ " "
						+ "	      AND DISTRICT_CODE LIKE '"
						+ dis
						+ "' "
						+ "     )a "
						+

						"     JOIN "
						+

						"     ( "
						+ "       SELECT "
						+ "         OFFICE_ID, "
						+ "         OFFICE_NAME "
						+ "       FROM COM_MST_ALL_OFFICES_VIEW "
						+ "	    WHERE OFFICE_ID LIKE '"
						+ divi
						+ "' "
						+ "     )b "
						+ "     ON a.OFFICE_ID = b.OFFICE_ID "
						+
						"     JOIN "
						+
						"     ( "
						+ "       SELECT "
						+ "         DISTRICT_CODE, "
						+ "         DISTRICT_NAME "
						+ "       FROM COM_MST_DISTRICTS "
						+ "   	 WHERE DISTRICT_CODE LIKE '"
						+ dis
						+ "' "
						+ "     )c "
						+ "     ON a.DISTRICT_CODE = c.DISTRICT_CODE   ORDER BY SNO"
						+ " )"
						+ " WHERE ID BETWEEN "
						+ start
						+ " AND " + end;
				result = statement.executeQuery(qry);

				try {
					xml = xml + "<flag>success</flag>";
					xml = xml + "<page>" + page + "</page>";
					xml = xml + "<total>" + total + "</total>";
					xml = xml + "<records>" + records + "</records>";

					while (result.next()) 
					{
						xml += "<row>";
						xml += "<sno>" + result.getInt("SNO") + "</sno>";
						xml += "<divi>" + result.getInt("OFFICE_ID")+ "</divi>";
						xml += "<division>" + result.getString("OFFICE_NAME")+ "</division>";
						xml += "<dis>" + result.getInt("DISTRICT_CODE")+ "</dis>";
						xml += "<district>" + result.getString("DISTRICT_NAME")+ "</district>";
						xml += "</row>";
					}
				} catch (Exception e) {
				}
				result.close();
			} catch (Exception e1) {
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		else if (strCommand.equals("LoadCmb")) 
		{
			xml = "<response><command>LoadCmb</command>";
			try {

				ResultSet rsdivi = statement.executeQuery(" SELECT "
						+ "    OFFICE_ID, " + "    OFFICE_NAME "
						+ " FROM COM_MST_ALL_OFFICES_VIEW " + " ORDER BY OFFICE_NAME");
				try {
					xml = xml + "<flag>success</flag>";
					while (rsdivi.next())
					{
						xml += "<divi>" + rsdivi.getInt("OFFICE_ID")+ "</divi>";
						xml += "<division>" + rsdivi.getString("OFFICE_NAME")+ "</division>";
					}
				} catch (Exception e)   
				{
				}
				rsdivi.close();
				ResultSet rsdis = statement.executeQuery(" SELECT "
						+ "    DISTRICT_CODE, " + "    DISTRICT_NAME "
						+ " FROM COM_MST_DISTRICTS "
						+ " ORDER BY DISTRICT_NAME");
				try {
					while (rsdis.next()) {
						xml += "<dis>" + rsdis.getInt("DISTRICT_CODE")
								+ "</dis>";
						xml += "<district>" + rsdis.getString("DISTRICT_NAME")
								+ "</district>";
					}
				} catch (Exception e) {
				}
				rsdis.close();
			} catch (Exception e1) {
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		pw.write(xml);
		pw.flush();
		pw.close();
		
			try 
			{
				connection.close();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
	}
}
