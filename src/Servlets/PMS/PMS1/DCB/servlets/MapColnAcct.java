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

public class MapColnAcct extends HttpServlet {
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
		try {
			ResourceBundle rs = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in opening connection:" + e);
		}
		response.setContentType(CONTENT_TYPE);
		String strCommand = "";
		String xml = "";

		int rid = 0;
		int achead = 0;
		String sch = null;
		String rdesc = null;
		String ctyp = null;
		String wef = null;
		String status = null,fstatus="";

		int page = 1;
		int total = 2;
		int records = 15;
		int start = 1;
		int limit = 10;
		int end = 10;

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
		String userid = (String) session.getAttribute("UserId");
		System.out.println("Session id is:" + userid);

		UserProfile empProfile = (UserProfile) session
				.getAttribute("UserProfile");
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
			System.out.println(e);
		}

		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");

		// ///////////////////// Command Parameters //////////////////////////
		try {
			strCommand = request.getParameter("command");
			System.out.println("strCommand : " + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// /////////////////////////////////////////////////////////////////////

		// ///////////////////// Pagination Parameters
		// //////////////////////////
		try {
			page = Integer.parseInt(request.getParameter("page"));
			System.out.println("page : " + page);
		} catch (Exception e) {
			System.out.println("Exception getting 'page' parameter ==> " + e);
		}

		try {
			limit = Integer.parseInt(request.getParameter("limit"));
			System.out.println("limit : " + limit);
		} catch (Exception e) {
			System.out.println("Exception getting 'limit' parameter ==> " + e);
		}
		// /////////////////////////////////////////////////////////////////////

		// ///////////////////// Other Parameters //////////////////////////
		try {
			rid = Integer.parseInt(request.getParameter("rid"));
			System.out.println("rid : " + rid);
		} catch (Exception e) {
			System.out.println("Exception fetching rid ===> " + e);
		}

		try {
			sch = request.getParameter("sch");
			if ((sch == null) || ("".equals(sch))) {
				sch = "%";
			}
			System.out.println("sch : " + sch);
		} catch (Exception e) {
			System.out.println("Exception fetching sch ===> " + e);
		}

		try {
			achead = Integer.parseInt(request.getParameter("achead"));
			System.out.println("achead : " + achead);
		} catch (Exception e) {
			System.out.println("Exception fetching achead ===> " + e);
		}

		try {
			ctyp = request.getParameter("ctyp");
			if ((ctyp == null) || ("".equals(ctyp))) {
				ctyp = "%";
			}
			System.out.println("ctyp : " + ctyp);
		} catch (Exception e) {
			System.out.println("Exception fetching ctyp ===> " + e);
		}

		try {
			rdesc = request.getParameter("rdesc");
			System.out.println("rdesc : " + rdesc);
		} catch (Exception e) {
			System.out.println("Exception fetching rdesc ===> " + e);
		}

		try {
			wef = request.getParameter("wef");
			System.out.println("wef : " + wef);
		} catch (Exception e) {
			System.out.println("Exception fetching wef ===> " + e);
		}

		try {
			status = request.getParameter("status");
			fstatus= request.getParameter("fstatus");
			System.out.println("status : " + status+"fstatus"+fstatus);
		} catch (Exception e) {
			System.out.println("Exception fetching status ===> " + e);
		}
		// /////////////////////////////////////////////////////////////////////

		/*******************************************************
		 * ACCOUNT HEAD DESC
		 *******************************************************/

		if (strCommand.equalsIgnoreCase("AcctHead")) {

			String headcode = request.getParameter("achead");
			int headcodeno = Integer.parseInt(headcode);
			xml = "<response><command>AcctHead</command>";
			try {
				PreparedStatement ps = connection
						.prepareStatement("select account_head_desc from com_mst_account_heads where account_head_code=? and USAGE_STATUS ='Y' ");
				ps.setInt(1, headcodeno);
				ResultSet res = ps.executeQuery();
				if (res.next()) {
					xml = xml + "<flag>success</flag><accthead>"
							+ res.getString("account_head_desc")
							+ "</accthead>";
				} else {
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";
			} catch (Exception e) {
				System.out.println("Exception in AcctHead:" + e);
			}

		}

		/*******************************************************
		 * DELETE
		 *******************************************************/
		else if (strCommand.equalsIgnoreCase("Delete")) {
			System.out.println("\n*************\nDelete\n**************\n");
			xml = "<response><command>Delete</command>";
			try {

				String sql = " DELETE " + " FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "
						+ " WHERE RECEIPT_TRN_ID=? ";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, rid);
				ps.executeUpdate();

				xml = xml + "<flag>success</flag>";
				xml = xml + "<rid>" + rid + "</rid>";
			} catch (Exception e1) {
				System.out.println("Exception in Deleting record ===> " + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}

		/*******************************************************
		 * UPDATE
		 *******************************************************/

		else if (strCommand.equalsIgnoreCase("Update")) {
			System.out.println("\n*************\nUpdate\n**************\n");
			xml = "<response><command>Update</command>";

			// /////////////////////// DUPLICATION CHECK
			// ///////////////////////////////////////////////////////
			int dup = 0;
			try {

				result = statement.executeQuery(" SELECT COUNT(*) AS dup "
						+ "  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "
						+ "  WHERE SCH_TYPE_ID= " + sch
						+ "  	AND COLLECTION_TYPE= " + ctyp
						+ "  	AND ACCOUNT_HEAD_CODE= " + achead
						+ "	AND ACTIVE_STATUS='L'" + "	AND RECEIPT_TRN_ID!="
						+ rid);
				try {
					if (result.next()) {
						dup = result.getInt("dup");
					}
				} catch (Exception e) {
					System.out
							.println("Exception in the getting values of dup: "
									+ e);
				}
				result.close();
			} catch (Exception e1) {
				System.out.println("Exception is in dup == " + e1);
			}
			System.out.println("Duplicate - " + dup);
			// /////////////////////////////////////////////////////////////////////////////////////////////////

			if (dup == 0) {
				try {
					result = statement.executeQuery(" SELECT "
							+ "	ACCOUNT_HEAD_DESC "
							+ "	FROM COM_MST_ACCOUNT_HEADS "
							+ "	WHERE ACCOUNT_HEAD_CODE =  " + achead +" and USAGE_STATUS='Y'");
					try {
						if (result.next()) {
							rdesc = result.getString("ACCOUNT_HEAD_DESC");
						}
					} catch (Exception e) {
						System.out
								.println("Exception in the getting values of rdesc (ACCOUNT_HEAD_DESC) from ResultSet : "
										+ e);
					}
					result.close();
				} catch (Exception e1) {
					System.out.println("Exception in rdesc == " + e1);
				}

				try {
					System.out.println("UPDATE PMS_DCB_RECEIPT_ACCOUNT_MAP "
							+ "SET " + "  SCH_TYPE_ID=?, "
							+ "  ACCOUNT_HEAD_CODE=?, "
							+ "  RECEIPT_TRN_DESC=?, "
							+ "  COLLECTION_TYPE=?, "
							+ "  ACC_CODE_WEF=to_date(?,'mm/dd/yyyy'), "
							+ "  ACTIVE_STATUS=?, "
							+ "  UPDATED_BY_USER_ID=?, "
							+ "  UPDATED_DATE=clock_timestamp() "
							+ "WHERE RECEIPT_TRN_ID=?");

					String sql = "UPDATE PMS_DCB_RECEIPT_ACCOUNT_MAP " + "SET "
							+ "  SCH_TYPE_ID=?, " + "  ACCOUNT_HEAD_CODE=?, "
							+ "  RECEIPT_TRN_DESC=?, "
							+ "  COLLECTION_TYPE=?, "
							+ "  ACC_CODE_WEF=to_date(?,'DD/MM/YYYY'), "
							+ "  ACTIVE_STATUS=?, "
							+ "  UPDATED_BY_USER_ID=?, "
							+ "  UPDATED_DATE=clock_timestamp() ,FAS_DISABLE=?"
							+ "WHERE RECEIPT_TRN_ID=?";
					PreparedStatement ps = connection.prepareStatement(sql);

					ps.setInt(1, Integer.parseInt(sch));
					ps.setInt(2, achead);
					ps.setString(3, rdesc);
					ps.setInt(4, Integer.parseInt(ctyp));
					ps.setString(5, wef);
					ps.setString(6, status);
					ps.setString(7, userid);
					ps.setString(8, fstatus);
					ps.setInt(9, rid);

					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
				} catch (Exception e1) {
					System.out.println("Exception in Updating record ===> "
							+ e1);
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
			System.out.println("\n*************\nAdd\n**************\n");
			xml = "<response><command>Add</command>";

			// /////////////////////// DUPLICATION CHECK
			// ///////////////////////////////////////////////////////
			int dup = 0;
			try {

				result = statement.executeQuery(" SELECT COUNT(*) AS dup "
						+ "  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "
						+ "  WHERE SCH_TYPE_ID= " + sch
						+ "  	AND COLLECTION_TYPE= " + ctyp
						+ "  	AND ACCOUNT_HEAD_CODE= " + achead
						+ "	AND ACTIVE_STATUS='L'" + "	AND RECEIPT_TRN_ID!="
						+ rid);
				try {
					if (result.next()) {
						dup = result.getInt("dup");
					}
				} catch (Exception e) {
					System.out
							.println("Exception in the getting values of dup: "
									+ e);
				}
				result.close();
			} catch (Exception e1) {
				System.out.println("Exception is in dup == " + e1);
			}
			System.out.println("Duplicate - " + dup);
			// /////////////////////////////////////////////////////////////////////////////////////////////////

			if (dup == 0) {
				try {
					result = statement.executeQuery(" SELECT "
							+ "	ACCOUNT_HEAD_DESC "
							+ "	FROM COM_MST_ACCOUNT_HEADS "
							+ "	WHERE ACCOUNT_HEAD_CODE =  " + achead +" and USAGE_STATUS='Y'");
					try {
						if (result.next()) {
							rdesc = result.getString("ACCOUNT_HEAD_DESC");
						}
					} catch (Exception e) {
						System.out
								.println("Exception in the getting values of rdesc (ACCOUNT_HEAD_DESC) from ResultSet : "
										+ e);
					}
					result.close();
				} catch (Exception e1) {
					System.out.println("Exception in rdesc == " + e1);
				}

				try {
					result = statement.executeQuery("SELECT "
							+ "	(CASE WHEN MAX(RECEIPT_TRN_ID) IS NULL "
							+ "		THEN 1 ELSE MAX(RECEIPT_TRN_ID)+1 "
							+ "	END) AS maxrid "
							+ "FROM PMS_DCB_RECEIPT_ACCOUNT_MAP ");
					try {
						if (result.next()) {
							rid = result.getInt("maxrid");
						}
					} catch (Exception e) {
						System.out
								.println("Exception in the getting values of maxrid: "
										+ e);
					}
					result.close();
				} catch (Exception e1) {
					System.out.println("Exception is in maxrid" + e1);
				}
				System.out.println("maxrid - " + rid);

				try {
					String sqlAdd = "INSERT INTO PMS_DCB_RECEIPT_ACCOUNT_MAP  "
							+ "(   "
							+ "  RECEIPT_TRN_ID, "
							+ "  SCH_TYPE_ID,   "
							+ "  ACCOUNT_HEAD_CODE, "
							+ "  RECEIPT_TRN_DESC,   "
							+ "  COLLECTION_TYPE,   "
							+ "  ACC_CODE_WEF,   "
							+ "  ACTIVE_STATUS,   "
							+ "  UPDATED_BY_USER_ID,  "
							+ "  UPDATED_DATE ,FAS_DISABLE  "
							+ ")   "
							+ "VALUES(?,?,?,?,?,to_date(?,'DD/MM/YYYY'),?,?,clock_timestamp(),?)";

					PreparedStatement ps = connection.prepareStatement(sqlAdd);
					System.out.println("fstatus"+fstatus);
					ps.setInt(1, rid);
					ps.setInt(2, Integer.parseInt(sch));
					ps.setInt(3, achead);
					ps.setString(4, rdesc);
					ps.setInt(5, Integer.parseInt(ctyp));
					ps.setString(6, wef);
					ps.setString(7, status);
					ps.setString(8, userid);
					ps.setString(9, fstatus);
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
			System.out.println("\n*************\nGet\n**************\n");
			xml = "<response><command>Get</command>";

			try {
				result = statement.executeQuery(" SELECT COUNT(*) AS rec "
						+ " FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "
						+ " WHERE ACTIVE_STATUS='" + status + "'");
				if (result.next()) {
					records = result.getInt("rec");
				}
			} catch (Exception e1) {
				System.out.println("Exception in Counting Records query ==> "
						+ e1);
			}

			start = (page - 1) * limit + 1;
			end = start + limit - 1;
			total = (int) Math.ceil((float) records / limit);

			try {

				result = statement
						.executeQuery(" SELECT * "
								+ "FROM "
								+ "( "
								+ "  SELECT  "
								+ "    ROWNUM AS ID, "
								+ "    RECEIPT_TRN_ID, "
								+ "	 SCH_TYPE_ID, "
								+ "    SCH_TYPE_DESC, "
								+ "    ACCOUNT_HEAD_CODE, "
								+ "    RECEIPT_TRN_DESC, "
								+ "	 COLLECTION_TYPE_SNO, "
								+ "    COLLECTION_TYPE_DESC, "
								+ "    ACC_CODE_WEF, "
								+ "    ACTIVE_STATUS,FAS_DISABLE "
								+ "  FROM "
								+ "    ( "
								+ "      SELECT RECEIPT_TRN_ID, "
								+ "        SCH_TYPE_ID, "
								+ "        ACCOUNT_HEAD_CODE, "
								+ "        RECEIPT_TRN_DESC, "
								+ "        COLLECTION_TYPE, "
								+ "        to_char(ACC_CODE_WEF,'dd/mm/yyyy') AS ACC_CODE_WEF, "
								+ "        ACTIVE_STATUS,FAS_DISABLE, "
								+ "        UPDATED_BY_USER_ID, "
								+ "        UPDATED_DATE "
								+ "      FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "
								+ "	   WHERE ACTIVE_STATUS='"
								+ status
								+ "' "
								+ "      ORDER BY RECEIPT_TRN_ID "
								+ "    )a "
								+

								"    JOIN "
								+

								"    ( "
								+ "      SELECT  "
								+ "        COLLECTION_TYPE_SNO,  "
								+ "        COLLECTION_TYPE_DESC  "
								+ "      FROM PMS_DCB_RECEIPT_TYPE "
								+ "    )b "
								+ "    ON a.COLLECTION_TYPE=b.COLLECTION_TYPE_SNO "
								+

								"    JOIN "
								+

								"    ( "
								+ "      SELECT  "
								+ "  	   	 SCH_TYPE_ID, "
								+ "	   	 SCH_TYPE_DESC "
								+ "	   FROM PMS_SCH_LKP_TYPE  "
								+ "    )c "
								+ "    ON a.SCH_TYPE_ID=c.SCH_TYPE_ID "
								+ ") "
								+ "WHERE ID BETWEEN " + start + " AND " + end);

				try {
					xml = xml + "<flag>success</flag>";

					xml = xml + "<page>" + page + "</page>";
					xml = xml + "<total>" + total + "</total>";
					xml = xml + "<records>" + records + "</records>";

					while (result.next()) {
						xml += "<row>";

						xml += "<rid>" + result.getInt("RECEIPT_TRN_ID")
								+ "</rid>";
						xml += "<sch>" + result.getInt("SCH_TYPE_ID")
								+ "</sch>";
						xml += "<scheme>" + result.getString("SCH_TYPE_DESC")
								+ "</scheme>";
						xml += "<achead>" + result.getInt("ACCOUNT_HEAD_CODE")
								+ "</achead>";
						xml += "<rdesc>" + result.getString("RECEIPT_TRN_DESC")
								+ "</rdesc>";
						xml += "<ctyp>" + result.getInt("COLLECTION_TYPE_SNO")
								+ "</ctyp>";
						xml += "<coltype>"
								+ result.getString("COLLECTION_TYPE_DESC")
								+ "</coltype>";
						xml += "<wef>" + result.getString("ACC_CODE_WEF")
								+ "</wef>";
						xml += "<status>" + result.getString("ACTIVE_STATUS")
								+ "</status>";
						xml += "<fstatus>" + result.getString("FAS_DISABLE")
						+ "</fstatus>";
						
						xml += "</row>";
					}
				} catch (Exception e) {
					System.out
							.println("Exception in fetching values from Resultset - GET: "
									+ e);
				}
				result.close();
			} catch (Exception e1) {
				System.out.println("Exception in Get" + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}

		else if (strCommand.equals("LoadCmb")) {
			System.out.println("\n*************\nLoadCmb\n**************\n");
			xml = "<response><command>LoadCmb</command>";

			try {

				ResultSet rs = statement.executeQuery("SELECT  "
						+ "  SCH_TYPE_ID, " + "  SCH_TYPE_DESC "
						+ "FROM PMS_SCH_LKP_TYPE "
						+ "WHERE SCH_TYPE_ID IN (select SCH_TYPE_ID from PMS_DCB_APPLICABLE_SCH_TYPE)"
						+ "ORDER BY SCH_TYPE_ID");
				try {
					xml = xml + "<flag>success</flag>";

					while (rs.next()) {
						xml += "<sch>" + rs.getInt("SCH_TYPE_ID") + "</sch>";
						xml += "<scheme>" + rs.getString("SCH_TYPE_DESC")
								+ "</scheme>";
					}
				} catch (Exception e) {
					System.out
							.println("Exception in fetching values from Resultset - GET: "
									+ e);
				}
				rs.close();

				rs = statement.executeQuery(" SELECT "
						+ "   COLLECTION_TYPE_SNO,  "
						+ "   COLLECTION_TYPE_DESC  "
						+ " FROM PMS_DCB_RECEIPT_TYPE ");
				try {
					while (rs.next()) {
						xml += "<ctyp>" + rs.getInt("COLLECTION_TYPE_SNO")
								+ "</ctyp>";
						xml += "<coltype>"
								+ rs.getString("COLLECTION_TYPE_DESC")
								+ "</coltype>";
					}
				} catch (Exception e) {
					System.out
							.println("Exception in fetching values from Resultset - GET: "
									+ e);
				}
				rs.close();

			} catch (Exception e1) {
				System.out.println("Exception in Get" + e1);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}

		System.out
				.println("\nCOLLECTION/RECEIPT ACCOUNT MAPPING XML RESPONSE:");
		System.out.println("xml is : " + xml);
		pw.write(xml);
		pw.flush();
		pw.close();

	}

}
