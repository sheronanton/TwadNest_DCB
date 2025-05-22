/* 
  * Created on : dd-mm-yy 
  * Author     : sathya
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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class Pms_Dcb_Ben_Sch_Allot
 */
public class Pms_Dcb_Ben_Sch_Allot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String command_var = "";
	String xmlvariable = "";
	ResultSet res, result_new, res_max, rs_check;
	Connection connection = null;
	PreparedStatement ps, ps_oid, ps_max, ps_check;
	String new_cond=Controller.new_cond; 
	 String meter_status=Controller.meter_status;
	int Beneficiary_type = 0;
	int empid = 0;
	int oid = 0;
	int flagname = 0;
	int Beneficiary_Name = 0;
	int Metre_Location = 0;
	int countsno = 0;
	String alloted_flag = "";
	int ALLOT_SNO = 0;
	int schemename = 0;
	double ALLOTED_QTY_val, MIN_BILL_QTY_val, EXCESS_TARIFF_RATE_val;

	public Pms_Dcb_Ben_Sch_Allot() {
		super();

	}

	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		double valus_starts_from_1 = 0;
		double valus_starts_to_1 = 0;
		double tariff_rate_1 = 0;
		int TARIFF_SLAB_SNO = 0;
		String Tariff_w_e_f_1 = "";
		double Allotted_Qty = 0;
		double Min_bill_Qty = 0;
		double Excess_Tariff_Rate = 0;
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

		System.out.println("emp id::" + empProfile.getEmployeeId());

		empid = empProfile.getEmployeeId();

		String oname = "";
		System.out.println("Empid" + empid);
		try {

			System.out
					.println("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID="
							+ empid);
			ps_oid = connection
					.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
			ps_oid.setInt(1, empid);
			result_new = ps_oid.executeQuery();
			System.out.println("Testing value");
			if (result_new.next()) {
				System.out.println("inside condition");
				oid = result_new.getInt("OFFICE_ID");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			ResourceBundle rs = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strdsn = rs.getString("Config.DSN");// jdbc:oracle:thin:
			String strhostname = rs.getString("Config.HOST_NAME");// 10.163.0.58
			String strportno = rs.getString("Config.PORT_NUMBER");// 1521
			String strsid = rs.getString("Config.SID");// twadnest
			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");// oracle.jdbc.OracleDriver
			String strdbusername = rs.getString("Config.USER_NAME");// twadpms
			String strdbpassword = rs.getString("Config.PASSWORD");// twadpms123
	//		ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
	//				+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			System.out.println("Paid by master");
			try {
				connection.clearWarnings();
			} catch (SQLException e) {
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			command_var = request.getParameter("command");
			System.out.println("command_var" + command_var);

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			valus_starts_from_1 = Double.parseDouble(request
					.getParameter("valus_starts_from_1"));
			System.out.println("valus_starts_from_1" + valus_starts_from_1);

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			valus_starts_to_1 = Double.parseDouble(request
					.getParameter("valus_starts_to_1"));
			System.out.println("valus_starts_to_1" + valus_starts_to_1);

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			tariff_rate_1 = Double.parseDouble(request
					.getParameter("tariff_rate_1"));
			System.out.println("tariff_rate_1" + tariff_rate_1);

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));
			System.out.println("Beneficiary_Name" + Beneficiary_Name);

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			Metre_Location = Integer.parseInt(request
					.getParameter("Metre_Location"));
			System.out.println("Metre_Location" + Metre_Location);

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			Tariff_w_e_f_1 = request.getParameter("Tariff_w_e_f_1");
			System.out.println("Tariff_w_e_f_1" + Tariff_w_e_f_1);

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			alloted_flag = request.getParameter("Alloted_flag_value");
			System.out.println("alloted_flag" + alloted_flag);

		} catch (Exception e) {
			System.out.println("Exception in alloted_flag:" + e);
		}
		try {
			Allotted_Qty = Double.parseDouble(request
					.getParameter("Allotted_Qty"));
		} catch (Exception e) {
			System.out.println("Allotted_Qty" + e);
		}
		try {
			Min_bill_Qty = Double.parseDouble(request
					.getParameter("Min_bill_Qty"));
		} catch (Exception e) {
			System.out.println("Min_bill_Qty" + e);
		}
		try {
			Excess_Tariff_Rate = Double.parseDouble(request
					.getParameter("Excess_Tariff_Rate"));
		} catch (Exception e) {
			System.out.println("Excess_Tariff_Rate" + e);
		}
		try {
			schemename = Integer.parseInt(request.getParameter("schemename"));
		} catch (Exception e) {
			System.out.println("schemename" + e);
		}

		if (command_var.equalsIgnoreCase("loadbeneficiarytype")) {

			xmlvariable = "<response>";
			xmlvariable += "<command>loadbeneficiarytype</command>";
			try {
				ps = connection
						.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");

				res = ps.executeQuery();
				System.out.println("loadbeneficiarytype");
				while (res.next()) {

					xmlvariable += "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID")
							+ "</BEN_TYPE_ID>";
					xmlvariable += "<BEN_TYPE_DESC>"
							+ res.getString("BEN_TYPE_DESC")
							+ "</BEN_TYPE_DESC>";
					xmlvariable += "<BEN_TYPE_SDESC>"
							+ res.getString("BEN_TYPE_SDESC")
							+ "</BEN_TYPE_SDESC>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadbeneficiaryname")) {
			System.out.println("loadbeneficiaryname");
			// System.out.println("alloted_flag"+alloted_flag);
			xmlvariable = "<response>";
			xmlvariable += "<command>loadbeneficiaryname</command>";
			Beneficiary_type = Integer.parseInt(request
					.getParameter("Beneficiary_type"));

			try {
				ps = connection
						.prepareStatement("select BENEFICIARY_SNO,\n"
								+ "BENEFICIARY_TYPE_ID,\n"
								+ "BENEFICIARY_NAME,\n"
								+ "OTHERS_PRIVATE_SNO,\n"
								+ "VILLAGE_PANCHAYAT_SNO,\n"
								+ "ALLOTED_FLG,\n"
								+ "URBANLB_SNO,\n"
								+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID\n"
								+ "from PMS_DCB_MST_BENEFICIARY\n"
								+ "where \n "+new_cond
								+ " BENEFICIARY_TYPE_ID=?\n"
								+ "and\n"
								+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=? AND (ALLOTED_FLG='B'OR ALLOTED_FLG='S')");

				ps.setInt(1, Beneficiary_type);
				ps.setInt(2, oid);
				// ps.setString(3,alloted_flag);

				res = ps.executeQuery();
				flagname = 0;
				while (res.next()) {
					flagname = 1;
					System.out.println(res.getInt("BENEFICIARY_SNO"));
					System.out.println(res.getString("BENEFICIARY_NAME"));

					xmlvariable += "<BENEFICIARY_SNO>"
							+ res.getInt("BENEFICIARY_SNO")
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<BENEFICIARY_NAME>"
							+ res.getString("BENEFICIARY_NAME")
							+ "</BENEFICIARY_NAME>";
					xmlvariable += "<BENEFICIARY_TYPE_ID>"
							+ res.getInt("BENEFICIARY_TYPE_ID")
							+ "</BENEFICIARY_TYPE_ID>";
					xmlvariable += "<OTHERS_PRIVATE_SNO>"
							+ res.getInt("OTHERS_PRIVATE_SNO")
							+ "</OTHERS_PRIVATE_SNO>";
					xmlvariable += "<VILLAGE_PANCHAYAT_SNO>"
							+ res.getInt("VILLAGE_PANCHAYAT_SNO")
							+ "</VILLAGE_PANCHAYAT_SNO>";
					xmlvariable += "<URBANLB_SNO>" + res.getInt("URBANLB_SNO")
							+ "</URBANLB_SNO>";
					xmlvariable += "<ALLOTED_FLG>"
							+ res.getString("ALLOTED_FLG") + "</ALLOTED_FLG>";
					xmlvariable += "<flag>success</flag>";

				}
				if (flagname == 0) {
					xmlvariable += "<BENEFICIARY_SNO>" + -1
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<BENEFICIARY_NAME>" + "No Data"
							+ "</BENEFICIARY_NAME>";
					xmlvariable += "<BENEFICIARY_TYPE_ID>" + -1
							+ "</BENEFICIARY_TYPE_ID>";
					xmlvariable += "<OTHERS_PRIVATE_SNO>" + -1
							+ "</OTHERS_PRIVATE_SNO>";
					xmlvariable += "<VILLAGE_PANCHAYAT_SNO>" + -1
							+ "</VILLAGE_PANCHAYAT_SNO>";
					xmlvariable += "<URBANLB_SNO>" + -1 + "</URBANLB_SNO>";
					xmlvariable += "<ALLOTED_FLG>" + -1 + "</ALLOTED_FLG>";

					xmlvariable += "<flag>success</flag>";

				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadallotedflag")) {
			System.out.println("loadallotedflag");

			xmlvariable = "<response>";
			xmlvariable += "<command>loadallotedflag</command>";
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));

			try {
				ps = connection.prepareStatement("select BENEFICIARY_SNO,\n"
						+ "ALLOTED_FLG,\n"
						+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID\n"
						+ "from PMS_DCB_MST_BENEFICIARY\n" + "where \n "+new_cond
						+ "BENEFICIARY_SNO=?\n" + "and\n"
						+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?");

				ps.setInt(1, Beneficiary_Name);
				ps.setInt(2, oid);
				// ps.setString(3,alloted_flag);

				res = ps.executeQuery();
				flagname = 0;
				while (res.next()) {
					flagname = 1;
					System.out.println(res.getInt("BENEFICIARY_SNO"));

					xmlvariable += "<BENEFICIARY_SNO>"
							+ res.getInt("BENEFICIARY_SNO")
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<ALLOTED_FLG>"
							+ res.getString("ALLOTED_FLG") + "</ALLOTED_FLG>";
					xmlvariable += "<flag>success</flag>";
					xmlvariable += "<recordfound>" + 1 + "</recordfound>";
				}
				if (flagname == 0) {
					xmlvariable += "<BENEFICIARY_SNO>" + -1
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<ALLOTED_FLG>"
							+ res.getString("ALLOTED_FLG") + "</ALLOTED_FLG>";
					xmlvariable += "<flag>success</flag>";
					xmlvariable += "<recordfound>" + 0 + "</recordfound>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadschemename")) {
			System.out.println("loadschemename");

			xmlvariable = "<response>";
			xmlvariable += "<command>loadschemename</command>";
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));

			try {
				ps = connection
						.prepareStatement("select distinct PMS_DCB_MST_BENEFICIARY_METRE.SCH_TYPE_ID,BENEFICIARY_SNO AS BENEFICIARY_SNO,SCHEME_SNO AS SCHEME_SNO,PMS_SCH_MASTER.SCH_NAME AS SCH_NAME from PMS_DCB_MST_BENEFICIARY_METRE join PMS_SCH_MASTER on PMS_DCB_MST_BENEFICIARY_METRE.METER_STATUS='L' and PMS_SCH_MASTER.SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO where   BENEFICIARY_SNO=?");
				ps.setInt(1, Beneficiary_Name);

				res = ps.executeQuery();
				flagname = 0;
				while (res.next()) {
					flagname = 1;
					System.out.println(res.getInt("SCHEME_SNO"));
					System.out.println(res.getString("SCH_NAME"));

					xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO")
							+ "</SCHEME_SNO>";
					xmlvariable += "<SCH_NAME><![CDATA[" + res.getString("SCH_NAME")
							+ "]]></SCH_NAME>";

					xmlvariable += "<flag>success</flag>";

				}
				if (flagname == 0) {
					xmlvariable += "<SCHEME_SNO>" + -1 + "</SCHEME_SNO>";
					xmlvariable += "<SCH_NAME>" + "No Data" + "</SCH_NAME>";

					xmlvariable += "<flag>success</flag>";

				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("insertben")) {
			System.out.println("insertben");

			xmlvariable = "<response>";
			xmlvariable += "<command>insertben</command>";
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));
			try {
				ps_check = connection
						.prepareStatement("select ALLOTED_QTY,MIN_BILL_QTY,EXCESS_TARIFF_RATE from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO = ? ");
				ps_check.setInt(1, Beneficiary_Name);
				rs_check = ps_check.executeQuery();
				if (rs_check.next()) {
					ALLOTED_QTY_val = rs_check.getDouble("ALLOTED_QTY");
					MIN_BILL_QTY_val = rs_check.getDouble("MIN_BILL_QTY");
					EXCESS_TARIFF_RATE_val = rs_check
							.getDouble("EXCESS_TARIFF_RATE");
					// countsno= rs_check.getInt("countsno") ;
					System.out.println("ALLOTED_QTY" + ALLOTED_QTY_val);
					System.out.println("MIN_BILL_QTY" + MIN_BILL_QTY_val);
					System.out.println("EXCESS_TARIFF_RATE"
							+ EXCESS_TARIFF_RATE_val);
					System.out.println("Beneficiary_Name" + Beneficiary_Name);
				}
			} catch (Exception e) {
				System.out.println("Error reterival");
			}
			// if(ALLOTED_QTY_val)
			try {
				String sqlAdd_flag = "update PMS_DCB_MST_BENEFICIARY set ALLOTED_QTY =?,MIN_BILL_QTY=?,EXCESS_TARIFF_RATE=? where "+new_cond+" BENEFICIARY_SNO =?";
				PreparedStatement pstmt = connection
						.prepareStatement(sqlAdd_flag);
				pstmt.setDouble(1, Allotted_Qty);
				pstmt.setDouble(2, Min_bill_Qty);
				pstmt.setDouble(3, Excess_Tariff_Rate);
				pstmt.setInt(4, Beneficiary_Name);
				pstmt.executeUpdate();

				System.out.println("inserted");

				xmlvariable += "<Beneficiary_Name>" + Beneficiary_Name
						+ "</Beneficiary_Name>";
				xmlvariable += "<Allotted_Qty>" + Allotted_Qty
						+ "</Allotted_Qty>";
				xmlvariable += "<Min_bill_Qty>" + Min_bill_Qty
						+ "</Min_bill_Qty>";
				xmlvariable += "<Excess_Tariff_Rate>" + Excess_Tariff_Rate
						+ "</Excess_Tariff_Rate>";
				xmlvariable += "<flag>success</flag>";

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("insertsch")) {
			xmlvariable = "<response>";
			xmlvariable += "<command>insertsch</command>";
			try {
				ps_max = connection
						.prepareStatement("select max(ALLOT_SNO) AS ALLOT_SNO from PMS_DCB_SCH_ALLOT");
				res_max = ps_max.executeQuery();
				if (res_max.next()) {

					ALLOT_SNO = res_max.getInt("ALLOT_SNO");

				}
			} catch (Exception e) {
				System.out.println("Erroe");
			}
			if (ALLOT_SNO > 0) {
				ALLOT_SNO = ALLOT_SNO + 1;
				System.out.println("ALLOT_SNO value is" + ALLOT_SNO);
			} else {
				ALLOT_SNO = 1;
				System.out.println("ALLOT_SNO value is" + ALLOT_SNO);
			}
			try {
				ps_check = connection
						.prepareStatement("select count(*) As countsno from PMS_DCB_SCH_ALLOT where BENEFICIARY_SNO = ? and SCHEME_SNO=?");
				ps_check.setInt(1, Beneficiary_Name);
				ps_check.setInt(2, schemename);
				rs_check = ps_check.executeQuery();
				if (rs_check.next()) {
					countsno = rs_check.getInt("countsno");
					System.out.println("countsnoinsert" + countsno);
				}
			} catch (Exception e) {
				System.out.println("Error reterival");
			}
			if (countsno == 0) {
				try {

					ps = connection
							.prepareStatement("insert into PMS_DCB_SCH_ALLOT (ALLOT_SNO,BENEFICIARY_SNO,SCHEME_SNO,ALLOTED_QTY,MIN_BILL_QTY,EXCESS_TARIFF_RATE,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,clock_timestamp())");
					ps.setInt(1, ALLOT_SNO);
					ps.setInt(2, Beneficiary_Name);
					ps.setInt(3, schemename);
					ps.setDouble(4, Allotted_Qty);
					ps.setDouble(5, Min_bill_Qty);
					ps.setDouble(6, Excess_Tariff_Rate);
					ps.setInt(7, oid);
					ps.executeUpdate();

					System.out.println("inserted");
					xmlvariable += "<Beneficiary_Name>" + Beneficiary_Name
							+ "</Beneficiary_Name>";
					xmlvariable += "<Allotted_Qty>" + Allotted_Qty
							+ "</Allotted_Qty>";
					xmlvariable += "<Min_bill_Qty>" + Min_bill_Qty
							+ "</Min_bill_Qty>";
					xmlvariable += "<Excess_Tariff_Rate>" + Excess_Tariff_Rate
							+ "</Excess_Tariff_Rate>";
					xmlvariable += "<countinsert>" + 0 + "</countinsert>";
					xmlvariable += "<flag>success</flag>";
				} catch (Exception e) {
					System.out.println("Erroe");
				}
			} else {
				xmlvariable += "<countinsert>" + 1 + "</countinsert>";
				xmlvariable += "<flag>success</flag>";
			}

			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("valuesgrid")) {
			System.out.println("valuesgrid");

			xmlvariable = "<response>";
			xmlvariable += "<command>valuesgrid</command>";
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));
			alloted_flag = request.getParameter("Alloted_flag_value");

			if (alloted_flag.equals("B")) {
				try {
					String sqlAdd_flag = "select BENEFICIARY_NAME,BENEFICIARY_SNO,ALLOTED_QTY,MIN_BILL_QTY,EXCESS_TARIFF_RATE from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO=?";
					PreparedStatement pstmt = connection
							.prepareStatement(sqlAdd_flag);
					pstmt.setInt(1, Beneficiary_Name);
					res = pstmt.executeQuery();
					flagname = 0;
					while (res.next()) {
						flagname = 1;

						xmlvariable += "<recordfound>" + 1 + "</recordfound>";
						xmlvariable += "<BENEFICIARY_SNO>"
								+ res.getInt("BENEFICIARY_SNO")
								+ "</BENEFICIARY_SNO>";
						xmlvariable += "<BENEFICIARY_NAME>"
								+ res.getString("BENEFICIARY_NAME")
								+ "</BENEFICIARY_NAME>";
						xmlvariable += "<ALLOTED_QTY>"
								+ res.getInt("ALLOTED_QTY") + "</ALLOTED_QTY>";
						xmlvariable += "<MIN_BILL_QTY>"
								+ res.getInt("MIN_BILL_QTY")
								+ "</MIN_BILL_QTY>";
						xmlvariable += "<EXCESS_TARIFF_RATE>"
								+ res.getInt("EXCESS_TARIFF_RATE")
								+ "</EXCESS_TARIFF_RATE>";
						xmlvariable += "<flag>success</flag>";
					}
					if (flagname == 0) {
						xmlvariable += "<recordfound>" + 0 + "</recordfound>";
						xmlvariable += "<BENEFICIARY_SNO>" + -1
								+ "</BENEFICIARY_SNO>";
						xmlvariable += "<BENEFICIARY_NAME>" + "No Data"
								+ "</BENEFICIARY_NAME>";
						xmlvariable += "<ALLOTED_QTY>" + -1 + "</ALLOTED_QTY>";
						xmlvariable += "<MIN_BILL_QTY>" + -1
								+ "</MIN_BILL_QTY>";
						xmlvariable += "<EXCESS_TARIFF_RATE>" + -1
								+ "</EXCESS_TARIFF_RATE>";
						xmlvariable += "<flag>success</flag>";

					}
				} catch (Exception e) {
					xmlvariable += "<flag>failure</flag>";
					System.out.println(e + "not reterived!");
				}
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadgrid")) {
			System.out.println("loadgrid");

			xmlvariable = "<response>";
			xmlvariable += "<command>loadgrid</command>";
			schemename = Integer.parseInt(request.getParameter("schemename"));
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));

			try {
				String sqlAdd_flag = "select ALLOT_SNO,"
						+ "BENEFICIARY_SNO,"
						+ "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_NAME AS BENEFICIARY_NAME,"
						+ "SCHEME_SNO,"
						+ "PMS_SCH_MASTER.SCH_NAME as SCH_NAME ,"
						+ "PMS_DCB_SCH_ALLOT.ALLOTED_QTY AS ALLOTED_QTY,"
						+ "PMS_DCB_SCH_ALLOT.MIN_BILL_QTY AS MIN_BILL_QTY ,"
						+ "PMS_DCB_SCH_ALLOT.EXCESS_TARIFF_RATE AS EXCESS_TARIFF_RATE "
						+ "from PMS_DCB_SCH_ALLOT "
						+ "join "
						+ "PMS_SCH_MASTER "
						+ "on "
						+ "PMS_SCH_MASTER.SCH_SNO=PMS_DCB_SCH_ALLOT.SCHEME_SNO "
						+ "JOIN "
						+ "PMS_DCB_MST_BENEFICIARY "
						+ "ON "
						+ "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=PMS_DCB_SCH_ALLOT.BENEFICIARY_SNO "
						+ "WHERE " + "PMS_DCB_SCH_ALLOT.SCHEME_SNO=? " + "AND "
						+ "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=? and PMS_DCB_MST_BENEFICIARY.STATUS='L'";
				PreparedStatement pstmt = connection
						.prepareStatement(sqlAdd_flag);
				pstmt.setInt(1, schemename);
				pstmt.setInt(2, Beneficiary_Name);
				res = pstmt.executeQuery();
				flagname = 0;
				while (res.next()) {
					flagname = 1;

					xmlvariable += "<recordfound>" + 1 + "</recordfound>";
					xmlvariable += "<BENEFICIARY_NAME>"
							+ res.getString("BENEFICIARY_NAME")
							+ "</BENEFICIARY_NAME>";
					xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO")
							+ "</SCHEME_SNO>";
					xmlvariable += "<ALLOT_SNO>" + res.getInt("ALLOT_SNO")
							+ "</ALLOT_SNO>";
					xmlvariable += "<ALLOTED_QTY>"
							+ res.getDouble("ALLOTED_QTY") + "</ALLOTED_QTY>";
					xmlvariable += "<MIN_BILL_QTY>"
							+ res.getDouble("MIN_BILL_QTY") + "</MIN_BILL_QTY>";
					xmlvariable += "<EXCESS_TARIFF_RATE>"
							+ res.getDouble("EXCESS_TARIFF_RATE")
							+ "</EXCESS_TARIFF_RATE>";
					xmlvariable += "<flag>success</flag>";
				}
				if (flagname == 0) {
					xmlvariable += "<recordfound>" + 0 + "</recordfound>";
					xmlvariable += "<BENEFICIARY_NAME><![CDATA["
							+ res.getString("BENEFICIARY_NAME")
							+ "]]></BENEFICIARY_NAME>";
					xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO")
							+ "</SCHEME_SNO>";
					xmlvariable += "<ALLOT_SNO>" + -1 + "</ALLOT_SNO>";
					xmlvariable += "<ALLOTED_QTY>" + -1 + "</ALLOTED_QTY>";
					xmlvariable += "<MIN_BILL_QTY>" + -1 + "</MIN_BILL_QTY>";
					xmlvariable += "<EXCESS_TARIFF_RATE>" + -1
							+ "</EXCESS_TARIFF_RATE>";
					xmlvariable += "<flag>success</flag>";

				}
			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}

			xmlvariable += "</response>";
		}
		out.println(xmlvariable);
		System.out.println(xmlvariable);
	}

}
