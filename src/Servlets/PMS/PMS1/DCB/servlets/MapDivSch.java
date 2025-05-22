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

public class MapDivSch extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try 
		{
			ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs.getString("Config.DSN");
			String strhostname = rs.getString("Config.HOST_NAME");
			String strportno = rs.getString("Config.PORT_NUMBER");
			String strsid = rs.getString("Config.SID");
			String strdbusername = rs.getString("Config.USER_NAME");
			String strdbpassword = rs.getString("Config.PASSWORD");
	//		ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"+strportno.trim() + ":" + strsid.trim();
			
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,strdbusername.trim(), strdbpassword.trim());
			try {
				statement = connection.createStatement();
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) 
		{
			System.out.println("Exception in opening connection:" + e);
		}
		response.setContentType(CONTENT_TYPE);
		String strCommand = "";
		String xml = "";
		int sno = 0;
		String divi = null;
		String sch = null;
		int page = 1;
		int total = 2;
		int records = 15;
		int start = 1;
		int limit = 10;
		int end = 10;
		HttpSession session = request.getSession(false);
		try {
			if (session == null) 
			{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
		} catch (Exception e) 
		{
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");		
		UserProfile empProfile = (UserProfile) session.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		int oid = 0;
		try 
		{
			PreparedStatement ps = connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
			ps.setInt(1, empid);
			result = ps.executeQuery();
			if (result.next()) 
			{
				oid = result.getInt("OFFICE_ID");
			}
			result.close();
			ps.close();
		} catch (Exception e) 
		{
			System.out.println(e);
		}

		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		
		try
		{
			strCommand = request.getParameter("command");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		System.out.println("DCB->MapDivSch->strCommand->"+strCommand);
		try 
		{
			page = Integer.parseInt(request.getParameter("page"));
			 
		} catch (Exception e) 
		{
		}

		try 
		{
			limit = Integer.parseInt(request.getParameter("limit"));
		} catch (Exception e) {
		}
		try 
		{
			sno = Integer.parseInt(request.getParameter("sno"));
		} catch (Exception e) 
		{
		}

		try 
		{
			divi = request.getParameter("divi");
			if ((divi == null) || ("".equals(divi))) 
			{
				divi = "%";
			}
		} catch (Exception e) 
		{
		}

		try 
		{
			sch = request.getParameter("sch");
			if ((sch == null) || ("".equals(sch))) 
			{
				sch = "%";
			}
		} catch (Exception e) 
		{
		}
		/*******************************************************
		 * DELETE
		 *******************************************************/
		if (strCommand.equalsIgnoreCase("Delete")) {
		 
			xml = "<response><command>Delete</command>";
			try {
				String sql = "DELETE FROM PMS_DCB_DIV_SCHEME_MAP "+"WHERE SNO = ?";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, sno);
				ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
				xml = xml + "<sno>" + sno + "</sno>";
			} catch (Exception e1) 
			{
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}

		/*******************************************************
		 * UPDATE
		 *******************************************************/
		else if (strCommand.equalsIgnoreCase("Update")) 
		{
			xml = "<response><command>Update</command>";
			// /////////////////////// DUPLICATION CHECK
			// ///////////////////////////////////////////////////////
			int dup = 0;
			try {
				result = statement.executeQuery(" SELECT COUNT(*) AS dup "
						+ "  FROM PMS_DCB_DIV_SCHEME_MAP "
						+ "  WHERE OFFICE_ID = " + divi + "    AND SCH_SNO = "
						+ sch + "    AND SNO != " + sno);
				try {
					if (result.next()) {
						dup = result.getInt("dup");
					}
				} catch (Exception e) {
					System.out.println("Exception in the getting values of dup: "+ e);
				}
				result.close();
			} catch (Exception e1) {
			}
			
			if (dup == 0) 
			{
				try 
				{
					String sql = " UPDATE PMS_DCB_DIV_SCHEME_MAP "
							+ " SET OFFICE_ID = ?, " + "     SCH_SNO = ? "
							+ " WHERE SNO = ?";
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(divi));
					ps.setInt(2, Integer.parseInt(sch));
					ps.setInt(3, sno);
					ps.executeUpdate();
					xml = xml + "<flag>success</flag>";
				} catch (Exception e1) 
				{
					System.out.println("Exception in Updating record ===> "+e1);
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";
			} else 
			{
				xml = xml + "<flag>duplicate</flag></response>";
			}
		}

		/*******************************************************
		 * ADD
		 *******************************************************/
		else if (strCommand.equalsIgnoreCase("Add")) 
		{
			xml = "<response><command>Add</command>";
			// /////////////////////// DUPLICATION CHECK
			int dup = 0;
			try 
			{
				result = statement.executeQuery(" SELECT COUNT(*) AS dup "
						+ "  FROM PMS_DCB_DIV_SCHEME_MAP "
						+ "  WHERE OFFICE_ID = " + divi + "    AND SCH_SNO = "
						+ sch + "    AND SNO != " + sno);
				 
				
				try {
					if (result.next()) 
					{
						dup = result.getInt("dup");
					}
				} catch (Exception e) {
				}
				result.close();
			} catch (Exception e1) {
			}
			
			if (dup == 0) 
			{
				try {
					result = statement
							.executeQuery("SELECT "
									+ "(CASE WHEN MAX(SNO) IS NULL THEN 1 ELSE MAX(SNO)+1 END) AS maxsno "
									+ "FROM PMS_DCB_DIV_SCHEME_MAP");
					try {
						if (result.next()) {
							sno = result.getInt("maxsno");
						}
					} catch (Exception e) {
					}
					result.close();
				} catch (Exception e1) {
				}
				
				try {
					String sqlAdd = "INSERT INTO PMS_DCB_DIV_SCHEME_MAP "
							+ "( " + "  SNO, " + "  OFFICE_ID, " + "  SCH_SNO "
							+ ") " + "VALUES(?,?,?)";

					PreparedStatement ps = connection.prepareStatement(sqlAdd);
					ps.setInt(1, sno);
					ps.setInt(2, Integer.parseInt(divi));
					ps.setInt(3, Integer.parseInt(sch));
					ps.executeUpdate();
					xml = xml + "<flag>success</flag>";
				} catch (Exception e1)
				{
					System.out.println(e1);
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";

			} else {
				xml = xml + "<flag>duplicate</flag></response>";
			}
		} else if (strCommand.equals("Get")) {
			 
			xml = "<response><command>Get</command>";

			try {
				result = statement.executeQuery(" SELECT COUNT(*) AS rec "
						+ " FROM PMS_DCB_DIV_SCHEME_MAP "
						+ " WHERE OFFICE_ID LIKE '" + divi + "' "
						+ "   AND SCH_SNO LIKE '" + sch + "' ");
				if (result.next()) {
					records = result.getInt("rec");
				}
			} catch (Exception e1) {
			}

			start = (page - 1) * limit + 1;
			end = start + limit - 1;
			total = (int) Math.ceil((float) records / limit);

			try {

				/*String 	qry="SELECT * FROM " + " ("
						+ "   SELECT " + "      ROWNUM AS ID, " + "      SNO, "
						+ "     a.OFFICE_ID, " + "     OFFICE_NAME, "
						+ "     a.SCH_SNO, " + "     SCH_NAME " + "   FROM "
						+ "     ( " + "       SELECT " + "         SNO,  "
						+ "         OFFICE_ID, " + "         SCH_SNO  "
						+ "       FROM PMS_DCB_DIV_SCHEME_MAP "
						+ "   	 WHERE OFFICE_ID LIKE '"
						+ divi
						+ "' "
						+ "	      AND SCH_SNO LIKE '"
						+ sch
						+ "' "
						+ "     )a "
						+

						"     JOIN "
						+

						"     ( "
						+ "       SELECT "
						+ "         OFFICE_ID, "
						+ "         OFFICE_NAME "
						+ "       FROM COM_MST_OFFICES "
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
						+ "         SCH_SNO, "
						+ "         SCH_NAME "
						+ "       FROM PMS_SCH_MASTER "
						+ "   	 WHERE sch_sno not in ( select SCH_SNO from  PMS_DCB_DIV_SCHEME_MAP) and  SCH_CATEGORY_ID=4 and SCH_SNO LIKE '"
						+ sch
						+ "' "
						+ "     )c "
						+ "     ON a.SCH_SNO = c.SCH_SNO "
						+

						"   ORDER BY SCH_SNO DESC"  
						+ " )"
						+ " WHERE ID BETWEEN "
						+ start
						+ " AND " + end  ;*/
				String 	qry="SELECT * FROM " + " ("
				+ "   SELECT " + "      ROWNUM AS ID, " + "      SNO, "
				+ "     a.OFFICE_ID, " + "     OFFICE_NAME, "
				+ "     a.SCH_SNO, " + "     SCH_NAME " + "   FROM "
				+ "     ( " + "       SELECT " + "         SNO,  "
				+ "         OFFICE_ID, " + "         SCH_SNO  "
				+ "       FROM PMS_DCB_DIV_SCHEME_MAP "
				+ "   	   )a "
				+

				"     JOIN "
				+

				"     ( "
				+ "       SELECT "
				+ "         OFFICE_ID, "
				+ "         OFFICE_NAME "
				+ "       FROM COM_MST_ALL_OFFICES_VIEW "
				+ "	       )b "
				+ "     ON a.OFFICE_ID = b.OFFICE_ID "
				+

				"     JOIN " 
				+
  
				"     ( "
				+ "       SELECT "
				+ "         SCH_SNO, "
				+ "         SCH_NAME "
				+ "       FROM PMS_SCH_MASTER "
				+ "   	 WHERE sch_sno   in ( select SCH_SNO from  PMS_DCB_DIV_SCHEME_MAP) and  SCH_CATEGORY_ID=4    )c "
				+ "     ON a.SCH_SNO = c.SCH_SNO "
				+

				"   ORDER BY SCH_NAME DESC"    
				+ " )"       
				+ " WHERE ID BETWEEN "
				+ start
				+ " AND " + end  ;
				
				
				System.out.println(qry);
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
						xml += "<sch>" + result.getInt("SCH_SNO") + "</sch>";
						xml += "<scheme><![CDATA[" + result.getString("SCH_NAME")+ "]]></scheme>";
						xml += "</row>";
					}
				} catch (Exception e) {
				}
				result.close();
			} catch (Exception e1) {
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}else if (strCommand.equals("LoadCmb")) 
		{
			xml = "<response><command>LoadCmb</command>";
			String division = "";
			try {

				ResultSet rsdivi = statement.executeQuery("SELECT "
						+ "  a.OFFICE_ID, " + "  OFFICE_NAME " + "FROM "
						+ "  ( " + "    SELECT "
						+ "        DISTINCT OFFICE_ID "
						+ "    FROM PMS_DCB_DIV_DIST_MAP " + "  )a "
						+ "  JOIN " + "  ( " + "    SELECT "
						+ "        OFFICE_ID, " + "        OFFICE_NAME   "
						+ "    FROM COM_MST_ALL_OFFICES_VIEW " + "  )b "
						+ "  ON a.OFFICE_ID=b.OFFICE_ID "
						+ "ORDER BY OFFICE_NAME");
				
				
				
				try {
					xml = xml + "<flag>success</flag>";

					while (rsdivi.next()) {
						int div = rsdivi.getInt("OFFICE_ID");
						xml += "<divi>" + div + "</divi>";
						xml += "<division>" + rsdivi.getString("OFFICE_NAME")+"</division>";
						division += (div + ",");
					}
					division = division.substring(0, division.lastIndexOf(","));
				} catch (Exception e) {
				}
				rsdivi.close();
				ResultSet rssch = statement.executeQuery(" SELECT  SCH_SNO,  SCH_NAME "
								+ " FROM PMS_SCH_MASTER "   
								+ " WHERE " +
										//"OFFICE_ID IN ("
								//+ division 
								//+ ") and " + // CATEGORY ID 4 for maintancne 
										"   SCH_CATEGORY_ID=4 and SCH_STATUS_ID in (4,9) and SCH_SNO not in  (select SCH_SNO from  PMS_DCB_DIV_SCHEME_MAP ) "
								+ " ORDER BY SCH_SNO DESC");
				 
				try {
					while (rssch.next())    
					{
						String ssname= rssch.getString("SCH_NAME");
						ssname=ssname.replace('&', '#');
						xml += "<sch>" + rssch.getInt("SCH_SNO") + "</sch>";
						xml += "<scheme><![CDATA[" +ssname+ "]]></scheme>";
					}
				} catch (Exception e) {  
				}  
				rssch.close();
			} catch (Exception e1) {
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
		}
		pw.write(xml);  
		pw.flush();
		pw.close();
	}
}
