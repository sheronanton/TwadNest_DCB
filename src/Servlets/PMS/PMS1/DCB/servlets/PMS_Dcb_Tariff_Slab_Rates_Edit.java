/* 
  * Created on : dd-mm-yy 
  * Author     : Sathya
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description							Done By
  * 17/09/2011		Add the Beneficiary Status to 'L'  	PS
  * 21/09/2011		Add the Meter Status to 'L'			PS
  *---------|---------------|--------------------------------------------------- 
  */
package Servlets.PMS.PMS1.DCB.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class PMS_Dcb_Tariff_Slab_Rates_Edit
 */
public class PMS_Dcb_Tariff_Slab_Rates_Edit extends HttpServlet
{
	String new_cond=Controller.new_cond;
	String meter_status2=Controller.meter_status2;
	String meter_status=Controller.meter_status;
	private static final long serialVersionUID = 1L;
	String command_var = "";
	String xmlvariable = "";
	ResultSet res, result_new, res_max, rs_check;
	Connection connection = null;
	PreparedStatement ps, ps_oid, ps_max, ps_check, ps_update;
	int Beneficiary_type = 0;
	int empid = 0;
	int oid = 0;
	int flagname = 0;
	int Beneficiary_Name = 0;
	int Metre_Location = 0;
	int Schemes = 0;
	int countsno = 0;
	double text_allot_flag = 0;
	double text_min_qty = 0;
	String mini_flag_value;
	String allot_flag_value;
	String tariff_flag_value = "";
	String valus_starts_from_1[];
	String valus_starts_to_1[];
	String tariff_rate_1[];
	String tariffslab[];
	String temp_tariffslab[];
	String WC_FREEZED_VALUE = "";
	String tariff_flag;
	int tarifflagname = 0;
	int TARIFF_SLAB_SNO = 0;
	String Tariff_w_e_f_1 = "";
	int valus_starts;
	int valus_to;  
	double tariff_rate;
	int ALLOT_SNO;
	int i;
	int lengthtemp;
	String check_value = "";
	int tariffslab_value = 0;
	Controller obj=new Controller();
	String[] temp_valus_starts_from = null;
	String[] temp_valus_to = null;
	String[] temp_tariff_rate = null;
	String[] maxcheck = null;
	String[] temp_check_value = null;
	public PMS_Dcb_Tariff_Slab_Rates_Edit() {super();}
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		Controller obj=new Controller();
		try 
		{
			Connection con;
			connection =  obj.con();
			try 
			{
				connection.clearWarnings();
			} catch (SQLException e) 
			{
				System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) 
		{
			System.out.println("Exception in openeing connection:" + e);
		}
		try 
		{
			command_var =obj.isNull(request.getParameter("command"),1);
		} catch (Exception e) 
		{
			System.out.println("Exception in openeing connection:" + e);
		}
		try 
		{
			valus_starts_from_1 = request.getParameterValues("valus_starts_from_1");
		} catch (Exception e) 
		{
			System.out.println("Exception in openeing connection:" + e);
		}
		try
		{
			valus_starts_to_1 = request.getParameterValues("valus_starts_to_1");
		} catch (Exception e) 
		{
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			tariff_rate_1 = request.getParameterValues("tariff_rate_1");

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			Metre_Location = Integer.parseInt(request
					.getParameter("Metre_Location"));

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		try {
			Tariff_w_e_f_1 = request.getParameter("Tariff_w_e_f_1");

		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}

		try {
			Schemes = Integer.parseInt(request.getParameter("Schemes"));

		} catch (Exception e) {
			System.out.println("Exception in Schemes:" + e);
		}
		try {
			mini_flag_value = request.getParameter("mini_flag_value");

		} catch (Exception e) {
			System.out.println("Exception in mini_flag_value:" + e);
		}
		try {
			allot_flag_value = request.getParameter("allot_flag_value");

		} catch (Exception e) {
			System.out.println("Exception in allot_flag_value;:" + e);
		}
		try {
			text_allot_flag = Double.parseDouble(request
					.getParameter("text_allot_flag"));

		}

		catch (Exception e) {
			System.out.println("Exception in text_allot_flag:" + e);
		}
		try {
			tariff_flag = obj.isNull(request.getParameter("tariff_flag"),1);

		}

		catch (Exception e) {
			System.out.println("Exception in tariff_flag:" + e);
		}

		try {
			text_min_qty = Double.parseDouble(obj.isNull(request.getParameter("text_min_qty"),1));
    
		} catch (Exception e) {
			System.out.println("Exception in text_min_qty:" + e);
		}
		try {
			
			maxcheck =  request.getParameterValues("maxcheck");
			
		}

		catch (Exception e) {
			System.out.println("Exception in maxcheck:" + e);
		}
		try {
			tariffslab = request.getParameterValues("tariffslab");

		}

		catch (Exception e) {
			System.out.println("Exception in tariffslab:" + e);
		}
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

		System.out.println("userid --- " + userid);
		 
		UserProfile empProfile = (UserProfile) session
				.getAttribute("UserProfile");
		// System.out.println("emp id::" + empProfile.getEmployeeId());
		empid = empProfile.getEmployeeId();
		// empid=2513;
		String oname = "";

		try {
            ps_oid = connection.prepareStatement("select CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID from PMS_DCB_COM_OFFICE_SWITCH  where EMPLOYEE_ID=?");

		//	ps_oid = connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
			ps_oid.setInt(1, empid);
			result_new = ps_oid.executeQuery();		 
			if (result_new.next()) 
			{
				oid = result_new.getInt("OFFICE_ID");
			}
		} catch (Exception e) {
			System.out.println(e);
		}  
	 
		if (command_var.equalsIgnoreCase("insert")) {
			xmlvariable = "";
			xmlvariable = "<response>";
			xmlvariable += "<command>add</command>";
			// System.out.println("Insert inside");
			try {
				ps_check = connection.prepareStatement("select count(*) As countsno from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO =? and METRE_SNO = ? and SCH_SNO=? and TARIFF_W_E_F=to_date(?,'DD/MM/YYYY')");
				ps_check.setInt(1, Beneficiary_Name);
				ps_check.setInt(2, Metre_Location);
				ps_check.setInt(3, Schemes);
				ps_check.setString(4, Tariff_w_e_f_1);

				rs_check = ps_check.executeQuery();
				if (rs_check.next()) {
					countsno = rs_check.getInt("countsno");

				}
			} catch (Exception e) {
				System.out.println("Error reterival");
			}
			if (countsno == 0) {
				for (i = 0; i < valus_starts_from_1.length; i++) {

					temp_valus_starts_from = valus_starts_from_1[i].split(",");
					temp_valus_to = valus_starts_to_1[i].split(",");
					temp_tariff_rate = tariff_rate_1[i].split(",");
					temp_check_value = maxcheck[i].split(",");
					for (i = 0; i < temp_valus_starts_from.length; i++) {

						valus_starts = Integer
								.parseInt(temp_valus_starts_from[i]);
						valus_to = Integer.parseInt(temp_valus_to[i]);
						tariff_rate = Double.parseDouble(temp_tariff_rate[i]);
						check_value = temp_check_value[i];
						try {
							ps_max = connection
									.prepareStatement("select max(TARIFF_SLAB_SNO) AS TARIFF_SLAB_SNO from PMS_DCB_TARIFF_SLAB");
							res_max = ps_max.executeQuery();
							if (res_max.next()) {

								TARIFF_SLAB_SNO = res_max
										.getInt("TARIFF_SLAB_SNO");

							}
						} catch (Exception e) {
							System.out.println("Erroe");
						}
						if (TARIFF_SLAB_SNO > 0) {
							TARIFF_SLAB_SNO = TARIFF_SLAB_SNO + 1;
						} else {
							TARIFF_SLAB_SNO = 1;

						}

						// select
						// TARIFF_SLAB_SNO,BENEFICIARY_SNO,SCH_SNO,METRE_SNO,TARIFF_FLAG,QTY_FROM,QTY_TO,TARIFF_RATE,TARIFF_W_E_F,UPDATED_BY_USER_ID,UPDATED_DATE,ACTIVE_STATUS
						// from TWADPHASE2.PMS_DCB_TARIFF_SLAB;
						try {

							ps = connection
									.prepareStatement("insert into PMS_DCB_TARIFF_SLAB(TARIFF_SLAB_SNO,\n"
											+ "BENEFICIARY_SNO,\n"
											+ "SCH_SNO,\n"
											+ "METRE_SNO,\n"
											+ "TARIFF_FLAG,\n"
											+ "QTY_FROM,\n"
											+ "QTY_TO,\n"
											+ "TARIFF_RATE,\n"
											+ "TARIFF_W_E_F,\n"
											+ "UPDATED_BY_USER_ID,\n"
											+ "UPDATED_DATE,\n"
											+ "ACTIVE_STATUS,MAX_FLAG) values(?,?,?,?,?,?,?,?,to_date(?,'DD/MM/YYYY'),?,clock_timestamp(),'A',?)");
							// to_date(?,'DD/MM/YYYY')

							ps.setInt(1, TARIFF_SLAB_SNO);
							ps.setInt(2, Beneficiary_Name);
							ps.setInt(3, Schemes);
							ps.setInt(4, Metre_Location);
							ps.setString(5, tariff_flag);
							ps.setInt(6, valus_starts);
							ps.setInt(7, valus_to);
							ps.setDouble(8, tariff_rate);
							ps.setString(9, Tariff_w_e_f_1);
							ps.setString(10, userid);
							ps.setString(11, check_value);
							ps.executeUpdate();
						 
							 
							xmlvariable += "<METRE_SNO>" + Metre_Location
									+ "</METRE_SNO>";
							xmlvariable += "<TARIFF_SLAB_STARTS_FROM>"
									+ valus_starts
									+ "</TARIFF_SLAB_STARTS_FROM>";
							xmlvariable += "<TARIFF_SLAB_STARTS_TO>" + valus_to
									+ "</TARIFF_SLAB_STARTS_TO>";
							xmlvariable += "<TARIFF_RATE>" + tariff_rate
									+ "</TARIFF_RATE>";
							xmlvariable += "<countinsert>" + 0
									+ "</countinsert>";
							xmlvariable += "<flag>success</flag>";

						}

						catch (Exception e) {
							System.out.println("Error -- insert " + e);
							xmlvariable += "<flag>faliure</flag>";
						}
					}

				}
			} else {
				xmlvariable += "<countinsert>" + 1 + "</countinsert>";
				xmlvariable += "<flag>success</flag>";
			}
			xmlvariable += "</response>";
			// dcballoted_starts
			try {
				ps_max = connection
						.prepareStatement("select max(ALLOT_SNO) AS ALLOT_SNO from PMS_DCB_ALLOTTED");
				res_max = ps_max.executeQuery();
				if (res_max.next()) {

					ALLOT_SNO = res_max.getInt("ALLOT_SNO");

				}
			} catch (Exception e) {
				System.out.println("Erroe");
			}
			if (ALLOT_SNO > 0) {
				ALLOT_SNO = ALLOT_SNO + 1;
			} else {
				ALLOT_SNO = 1;

			}
			 
			try {

				ps = connection
						.prepareStatement("insert into PMS_DCB_ALLOTTED(ALLOT_SNO,\n"
								+ "BENEFICIARY_SNO,\n"
								+ "MIN_FLAG,\n"
								+ "ALLOT_FLAG,\n"
								+ "METRE_SNO,\n"
								+ "SCH_SNO,\n"
								+ "ALLOT_QTY,\n"
								+ "MIN_QTY,\n"
								+ "UPDATED_DATE,UPDATED_BY_USER_ID) values(?,?,?,?,?,?,?,?,clock_timestamp(),?)");
				// to_date(?,'DD/MM/YYYY')

				ps.setInt(1, ALLOT_SNO);
				ps.setInt(2, Beneficiary_Name);
				ps.setString(3, mini_flag_value);
				ps.setString(4, allot_flag_value);
				ps.setInt(5, Metre_Location);
				ps.setInt(6, Schemes);
				ps.setDouble(7, text_allot_flag);
				ps.setDouble(8, text_min_qty);
				ps.setString(9, userid);

				ps.executeUpdate();

				/*
				 * xmlvariable += "<METRE_SNO>" + Metre_Location +
				 * "</METRE_SNO>"; xmlvariable += "<TARIFF_SLAB_STARTS_FROM>" +
				 * valus_starts + "</TARIFF_SLAB_STARTS_FROM>"; xmlvariable +=
				 * "<TARIFF_SLAB_STARTS_TO>" + valus_to +
				 * "</TARIFF_SLAB_STARTS_TO>"; xmlvariable += "<TARIFF_RATE>" +
				 * tariff_rate + "</TARIFF_RATE>"; xmlvariable +=
				 * "<countinsert>" + 0 + "</countinsert>"; xmlvariable +=
				 * "<flag>success</flag>";
				 */

			}

			catch (Exception e) {
				System.out.println("Error  : insert" + e);
				xmlvariable += "<flag>faliure</flag>";
			}

		}

		if (command_var.equalsIgnoreCase("loadbeneficiarytype")) {
			xmlvariable = "";
			xmlvariable = "<response>";
			xmlvariable += "<command>loadbeneficiarytype</command>";
			try {
				ps = connection
						.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");

				res = ps.executeQuery();

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
		}

		if (command_var.equalsIgnoreCase("loadbeneficiaryname")) {
			xmlvariable = "";
			System.out.println("loadbeneficiaryname");
			xmlvariable = "<response>";
			xmlvariable += "<command>loadbeneficiaryname</command>";
			Beneficiary_type = Integer.parseInt(request
					.getParameter("Beneficiary_type"));

			try {
				 
				ps = connection
						.prepareStatement("select BENEFICIARY_SNO,BENEFICIARY_NAME,BENEFICIARY_TYPE_ID,OTHERS_PRIVATE_SNO ,VILLAGE_PANCHAYAT_SNO,URBANLB_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE  where "+meter_status+" OFFICE_ID=? and BENEFICIARY_TYPE_ID=?) and OFFICE_ID=? order by BENEFICIARY_NAME ");
				ps.setInt(1, oid);
				ps.setInt(2, Beneficiary_type);
				ps.setInt(3, oid);
				res = ps.executeQuery();
				flagname = 0;
				while (res.next()) {
					flagname = 1;

					xmlvariable += "<BENEFICIARY_SNO>"
							+ res.getInt("BENEFICIARY_SNO")
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<BENEFICIARY_NAME>"
							+ res.getString("BENEFICIARY_NAME")
							+ "</BENEFICIARY_NAME>";
					 

					xmlvariable += "<flag>success</flag>";

				}
				if (flagname == 0) {
					xmlvariable += "<BENEFICIARY_SNO>" + -1
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<BENEFICIARY_NAME>" + "No Data"
							+ "</BENEFICIARY_NAME>";
					  

					xmlvariable += "<flag>success</flag>";

				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		}

		if (command_var.equalsIgnoreCase("loadtariffmode")) {
			xmlvariable = "";
			xmlvariable = "<response>";
			xmlvariable += "<command>loadtariffmode</command>";
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));
			try {
				ps = connection
						.prepareStatement("select BENEFICIARY_SNO, TARIFF_FLAG from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO=?");

				ps.setInt(1, Beneficiary_Name);

				res = ps.executeQuery();
				flagname = 0;
				while (res.next()) {
					flagname = 1;

					xmlvariable += "<BENEFICIARY_SNO>"
							+ res.getInt("BENEFICIARY_SNO")
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<TARIFF_FLAG>"
							+ res.getString("TARIFF_FLAG") + "</TARIFF_FLAG>";

					xmlvariable += "<flag>success</flag>";

				}
				if (flagname == 0) {
					xmlvariable += "<BENEFICIARY_SNO>" + -1
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<TARIFF_FLAG>" + "No Data"
							+ "</TARIFF_FLAG>";

					xmlvariable += "<flag>success</flag>";

				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		}

		if (command_var.equalsIgnoreCase("loadlocation")) {
			xmlvariable = "";

			xmlvariable = "<response>";
			xmlvariable += "<command>loadlocation</command>";
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));
			try {
				ps = connection
						.prepareStatement("select BENEFICIARY_SNO,METRE_SNO,METRE_LOCATION,SCHEME_SNO,TARIFF_FLAG from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO=? and SCHEME_SNO=?");

				ps.setInt(1, Beneficiary_Name);
				ps.setInt(2, Schemes);

				res = ps.executeQuery();
				flagname = 0;
				while (res.next()) {
					flagname = 1;

					xmlvariable += "<METRE_SNO>" + res.getInt("METRE_SNO")
							+ "</METRE_SNO>";
					xmlvariable += "<METRE_LOCATION>"
							+ res.getString("METRE_LOCATION")
							+ "</METRE_LOCATION>";
					xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO")
							+ "</SCHEME_SNO>";
					xmlvariable += "<TARIFF_FLAG>"
							+ res.getString("TARIFF_FLAG") + "</TARIFF_FLAG>";
					xmlvariable += "<flag>success</flag>";

				}
				if (flagname == 0) {
					xmlvariable += "<METRE_SNO>" + -1 + "</METRE_SNO>";
					xmlvariable += "<METRE_LOCATION>" + "No Data"
							+ "</METRE_LOCATION>";

					xmlvariable += "<flag>success</flag>";

				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		}

		if (command_var.equalsIgnoreCase("loadscheme")) {
			xmlvariable = "";
			 
			xmlvariable = "<response>";
			xmlvariable += "<command>loadscheme</command>";
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));
			try {
				ps = connection
						.prepareStatement("select distinct PMS_DCB_MST_BENEFICIARY_METRE.SCH_TYPE_ID,BENEFICIARY_SNO AS BENEFICIARY_SNO,SCHEME_SNO AS SCHEME_SNO,PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,PMS_DCB_MST_BENEFICIARY_METRE.TARIFF_FLAG as TARIFF_FLAG  from PMS_DCB_MST_BENEFICIARY_METRE join PMS_SCH_MASTER on "+meter_status2+" PMS_SCH_MASTER.SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO where BENEFICIARY_SNO=? and TARIFF_FLAG<>'-' AND TARIFF_FLAG IS NOT NULL AND SETTING_FLAG='FR'");

				ps.setInt(1, Beneficiary_Name);
				System.out.println("Beneficiary_Name" + Beneficiary_Name);
				res = null;
				res = ps.executeQuery();
				flagname = 0;
				System.out.println("First");
				while (res.next()) {
					flagname = 1;

					xmlvariable += "<SCH_NAME><![CDATA[" + res.getString("SCH_NAME")+ "]]></SCH_NAME>";
					xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO")+ "</SCHEME_SNO>";
					xmlvariable += "<TARIFF_FLAG>"
							+ res.getString("TARIFF_FLAG") + "</TARIFF_FLAG>";

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
		}

		if (command_var.equalsIgnoreCase("get")) {
			xmlvariable = "";

			xmlvariable = "<response>";
			xmlvariable += "<command>get</command>";
			if (tariff_flag_value.equals("Location")) {
				try {
					ps_check = connection
							.prepareStatement("select count(*) As countsno from PMS_DCB_TARIFF_SLAB_LOCATION where METRE_SNO =?");
					ps_check.setInt(1, Metre_Location);
					rs_check = ps_check.executeQuery();
					if (rs_check.next()) {
						countsno = rs_check.getInt("countsno");
						 
					}
				} catch (Exception e) {
					System.out.println("Error reterival");

				}
				if (countsno > 0) {

					try {
						ps = connection
								.prepareStatement("select TARIFF_SLAB_SNO,BENEFICIARY_SNO,METRE_SNO,TARIFF_SLAB_STARTS_FROM,TARIFF_SLAB_STARTS_TO,TARIFF_RATE,TARIFF_W_E_F from PMS_DCB_TARIFF_SLAB_LOCATION where METRE_SNO =? order by TARIFF_SLAB_STARTS_FROM");
						ps.setInt(1, Metre_Location);
						res = ps.executeQuery();
						 
						while (res.next()) {
							try {
								System.out.println(res
										.getInt("TARIFF_SLAB_SNO"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out.println(res
										.getInt("BENEFICIARY_SNO"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out.println(res.getInt("METRE_SNO"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out.println(res
										.getDouble("TARIFF_SLAB_STARTS_FROM"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out.println(res
										.getDouble("TARIFF_SLAB_STARTS_TO"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out
										.println(res.getDouble("TARIFF_RATE"));
							} catch (Exception e) {
								System.out.println(res.getDate("TARIFF_W_E_F"));
							}

							xmlvariable += "<recordfound> 1 </recordfound>";
							xmlvariable += "<TARIFF_SLAB_STARTS_FROM>"
									+ res.getDouble("TARIFF_SLAB_STARTS_FROM")
									+ "</TARIFF_SLAB_STARTS_FROM>";
							xmlvariable += "<TARIFF_SLAB_STARTS_TO>"
									+ res.getDouble("TARIFF_SLAB_STARTS_TO")
									+ "</TARIFF_SLAB_STARTS_TO>";
							xmlvariable += "<TARIFF_RATE>"
									+ res.getDouble("TARIFF_RATE")
									+ "</TARIFF_RATE>";

							xmlvariable += "<TARIFF_W_E_F>"
									+ res.getDate("TARIFF_W_E_F")
									+ "</TARIFF_W_E_F>";

							xmlvariable += "<flag>success</flag>";

						}

					} catch (Exception e) {
						xmlvariable += "<flag>failure</flag>";
						System.out.println(e + "not reterived!");
					}

				} else {
					xmlvariable += "<flag>success</flag>";
					xmlvariable += "<recordfound> 0 </recordfound>";
				}
				xmlvariable += "</response>";
			}

			if (tariff_flag_value.equals("Scheme")) {
				try {
					ps_check = connection
							.prepareStatement("select count(*) As countsno from PMS_DCB_TARIFF_SLAB_SCHEME where SCH_SNO =?");
					ps_check.setInt(1, Metre_Location);
					rs_check = ps_check.executeQuery();
					if (rs_check.next()) {
						countsno = rs_check.getInt("countsno");
						 
					}
				} catch (Exception e) {
					System.out.println("Error reterival");

				}

				if (countsno > 0) {

					try {
						ps = connection
								.prepareStatement("select TARIFF_SLAB_SNO,BENEFICIARY_SNO,SCH_SNO,TARIFF_SLAB_STARTS_FROM,TARIFF_SLAB_STARTS_TO,TARIFF_RATE,TARIFF_W_E_F from PMS_DCB_TARIFF_SLAB_SCHEME where SCH_SNO =? order by TARIFF_SLAB_STARTS_FROM");
						ps.setInt(1, Metre_Location);
						res = ps.executeQuery();

						while (res.next()) {
							try {
								System.out.println(res
										.getInt("TARIFF_SLAB_SNO"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out.println(res
										.getInt("BENEFICIARY_SNO"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out.println(res.getInt("SCH_SNO"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out.println(res
										.getDouble("TARIFF_SLAB_STARTS_FROM"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out.println(res
										.getDouble("TARIFF_SLAB_STARTS_TO"));
							} catch (Exception e) {
								System.out.println("Exception");
							}
							try {
								System.out
										.println(res.getDouble("TARIFF_RATE"));
							} catch (Exception e) {
								System.out.println(res.getDate("TARIFF_W_E_F"));
							}

							xmlvariable += "<recordfound> 1 </recordfound>";
							xmlvariable += "<TARIFF_SLAB_STARTS_FROM>"
									+ res.getDouble("TARIFF_SLAB_STARTS_FROM")
									+ "</TARIFF_SLAB_STARTS_FROM>";
							xmlvariable += "<TARIFF_SLAB_STARTS_TO>"
									+ res.getDouble("TARIFF_SLAB_STARTS_TO")
									+ "</TARIFF_SLAB_STARTS_TO>";
							xmlvariable += "<TARIFF_RATE>"
									+ res.getDouble("TARIFF_RATE")
									+ "</TARIFF_RATE>";

							xmlvariable += "<TARIFF_W_E_F>"
									+ res.getDate("TARIFF_W_E_F")
									+ "</TARIFF_W_E_F>";

							xmlvariable += "<flag>success</flag>";

						}

					} catch (Exception e) {
						xmlvariable += "<flag>failure</flag>";
						System.out.println(e + "not reterived!");
					}

				} else {
					xmlvariable += "<flag>success</flag>";
					xmlvariable += "<recordfound> 0 </recordfound>";
				}
				xmlvariable += "</response>";
			}

		}
		if (command_var.equalsIgnoreCase("loadflag")) {
			xmlvariable = "";

			xmlvariable = "<response>";
			xmlvariable += "<command>loadflag</command>";
			Beneficiary_Name = Integer.parseInt(request
					.getParameter("Beneficiary_Name"));
			try {
				ps = connection
						.prepareStatement("select BENEFICIARY_SNO,TARIFF_MODE FROM PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO=?");

				ps.setInt(1, Beneficiary_Name);

				res = ps.executeQuery();
				flagname = 0;
				while (res.next()) {
					flagname = 1;

					xmlvariable += "<BENEFICIARY_SNO>"
							+ res.getInt("BENEFICIARY_SNO")
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<TARIFF_MODE>"
							+ res.getString("TARIFF_MODE") + "</TARIFF_MODE>";
					xmlvariable += "<flag>success</flag>";

				}
				if (flagname == 0) {
					xmlvariable += "<BENEFICIARY_SNO>" + -1
							+ "</BENEFICIARY_SNO>";
					xmlvariable += "<TARIFF_MODE>" + "No Data"
							+ "</TARIFF_MODE>";

					xmlvariable += "<flag>success</flag>";

				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		}
		if (command_var.equalsIgnoreCase("loadlocationgrid")) {
			xmlvariable = "";
			xmlvariable = "<response>";
			xmlvariable += "<command>loadlocationgrid</command>";
			String cond = "";
			// forlocationgrid starts
			if (tariff_flag.equals("L")) {
				// cond=where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=?
				try {
					ps_check = connection
							.prepareStatement("select count(*) as countsno from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=? and TARIFF_FLAG=? and ACTIVE_STATUS='A'");
					ps_check.setInt(1, Beneficiary_Name);
					ps_check.setInt(2, Metre_Location);
					ps_check.setInt(3, Schemes);
					ps_check.setString(4, tariff_flag);
					rs_check = ps_check.executeQuery();
					if (rs_check.next()) {
						countsno = rs_check.getInt("countsno");
						System.out.println("countsno" + countsno);
					}
				} catch (Exception e) {
					System.out.println("Error_reterival_for_grid");
				}
				
				if (countsno > 0) {
					try {

						// ps_check=connection.prepareStatement("select DISTINCT PMS_DCB_TARIFF_SLAB.METRE_SNO AS METRE_SNO,PMS_DCB_TARIFF_SLAB.TARIFF_FLAG AS TARIFF_FLAG,PMS_DCB_TARIFF_SLAB.QTY_FROM AS QTY_FROM,PMS_DCB_TARIFF_SLAB.QTY_TO AS QTY_TO,PMS_DCB_TARIFF_SLAB.TARIFF_RATE AS TARIFF_RATE,PMS_DCB_TARIFF_SLAB.TARIFF_W_E_F AS TARIFF_W_E_F,PMS_DCB_TARIFF_SLAB.ACTIVE_STATUS AS ACTIVE_STATUS,PMS_DCB_TARIFF_SLAB.TARIFF_FLAG AS TARIFF_FLAG,PMS_DCB_TARIFF_SLAB.MAX_FLAG AS MAX_FLAG,PMS_DCB_ALLOTTED.MIN_FLAG AS MIN_FLAG,PMS_DCB_ALLOTTED.ALLOT_FLAG AS ALLOT_FLAG,PMS_DCB_ALLOTTED.ALLOT_QTY AS ALLOT_QTY,PMS_DCB_ALLOTTED.MIN_QTY AS MIN_QTY from PMS_DCB_TARIFF_SLAB JOIN PMS_DCB_ALLOTTED ON PMS_DCB_ALLOTTED.BENEFICIARY_SNO=PMS_DCB_TARIFF_SLAB.BENEFICIARY_SNO where PMS_DCB_TARIFF_SLAB.BENEFICIARY_SNO=? and PMS_DCB_TARIFF_SLAB.METRE_SNO=? and PMS_DCB_TARIFF_SLAB.SCH_SNO=? and PMS_DCB_TARIFF_SLAB.TARIFF_FLAG=? ORDER BY PMS_DCB_TARIFF_SLAB.QTY_FROM");
						ps_check = connection
								.prepareStatement("select TARIFF_SLAB_SNO,MAX_FLAG,METRE_SNO,TARIFF_FLAG,QTY_FROM,QTY_TO,TARIFF_RATE,TARIFF_W_E_F,ACTIVE_STATUS from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=? and TARIFF_FLAG=? and ACTIVE_STATUS='A' ORDER BY QTY_FROM");
						ps_check.setInt(1, Beneficiary_Name);
						
						ps_check.setInt(2, Metre_Location);
						ps_check.setInt(3, Schemes);
						ps_check.setString(4, tariff_flag);
						// ps_check.setInt(4,Beneficiary_Name);
						// ps_check.setInt(5,Metre_Location);
						// ps_check.setInt(6,Schemes);
						// ps_check.setString(7,tariff_flag);
						res = ps_check.executeQuery();
					 
						while (res.next()) {
						
							xmlvariable += "<recordfound> 1 </recordfound>";
							xmlvariable += "<QTY_FROM>"
									+ res.getDouble("QTY_FROM") + "</QTY_FROM>";
							xmlvariable += "<QTY_TO>" + res.getDouble("QTY_TO")
									+ "</QTY_TO>";
							xmlvariable += "<TARIFF_RATE>"
									+ res.getDouble("TARIFF_RATE")
									+ "</TARIFF_RATE>";
							xmlvariable += "<TARIFF_W_E_F>"
									+ res.getDate("TARIFF_W_E_F")
									+ "</TARIFF_W_E_F>";
							// xmlvariable += "<MIN_FLAG>" +
							// res.getString("MIN_FLAG") + "</MIN_FLAG>";
							// xmlvariable += "<ALLOT_FLAG>" +
							// res.getString("ALLOT_FLAG") + "</ALLOT_FLAG>";
							// xmlvariable += "<ALLOT_QTY>" +
							// res.getInt("ALLOT_QTY") + "</ALLOT_QTY>";
							// xmlvariable += "<MIN_QTY>" +
							// res.getInt("MIN_QTY") + "</MIN_QTY>";
							xmlvariable += "<MAX_FLAG>"
									+ res.getString("MAX_FLAG") + "</MAX_FLAG>";
							xmlvariable += "<ACTIVE_STATUS>"
									+ res.getString("ACTIVE_STATUS")
									+ "</ACTIVE_STATUS>";
							xmlvariable += "<METRE_SNO>"
									+ res.getInt("METRE_SNO") + "</METRE_SNO>";
							xmlvariable += "<TARIFF_SLAB_SNO>"
									+ res.getInt("TARIFF_SLAB_SNO")
									+ "</TARIFF_SLAB_SNO>";

							
						}
						 
						ps_check = connection
								.prepareStatement("select BENEFICIARY_SNO,MIN_FLAG,ALLOT_FLAG,ALLOT_QTY,MIN_QTY from PMS_DCB_ALLOTTED where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=? and ACTIVE_STATUS='A'");
						ps_check.setInt(1, Beneficiary_Name);
						ps_check.setInt(2, Metre_Location);
						ps_check.setInt(3, Schemes);
						res = ps_check.executeQuery();

						while (res.next()) {
							System.out.println(res.getString("ALLOT_QTY"));

							xmlvariable += "<MIN_FLAG>"
									+ res.getString("MIN_FLAG") + "</MIN_FLAG>";
							xmlvariable += "<ALLOT_FLAG>"
									+ res.getString("ALLOT_FLAG")
									+ "</ALLOT_FLAG>";
							xmlvariable += "<ALLOT_QTY> "
									+ res.getString("ALLOT_QTY")
									+ "</ALLOT_QTY>";
							xmlvariable += "<MIN_QTY>"
									+ res.getString("MIN_QTY") + "</MIN_QTY>";
							xmlvariable += "<flag>success</flag>";
						}

					} catch (Exception e) {
						xmlvariable += "<flag>failure</flag>";
						System.out.println(e + "not reterived!");
					}
				} else {
					xmlvariable += "<flag>success</flag>";
					xmlvariable += "<recordfound> 0 </recordfound>";
				}
				xmlvariable += "</response>";
			}
			// for locationgrid ends
			// for schemegrid starts
			if (tariff_flag.equals("S")) {
				try {
					ps_check = connection
							.prepareStatement("select count(*) as countsno from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and SCH_SNO=? and TARIFF_FLAG=? and ACTIVE_STATUS='A'");
					ps_check.setInt(1, Beneficiary_Name);
					ps_check.setInt(2, Schemes);

					ps_check.setString(3, tariff_flag);

					rs_check = ps_check.executeQuery();
					if (rs_check.next()) {
						countsno = rs_check.getInt("countsno");
						System.out.println("countsno" + countsno);
					}
				} catch (Exception e) {
					System.out.println("Error_reterival_for_grid");
				}
				if (countsno > 0) {
					try {
						ps_check = connection
								.prepareStatement("select TARIFF_SLAB_SNO,MAX_FLAG,METRE_SNO,TARIFF_FLAG,QTY_FROM,QTY_TO,TARIFF_RATE,TARIFF_W_E_F,ACTIVE_STATUS from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and SCH_SNO=? and TARIFF_FLAG=? and ACTIVE_STATUS='A' ORDER BY QTY_FROM");
						ps_check.setInt(1, Beneficiary_Name);
						ps_check.setInt(2, Schemes);
						ps_check.setString(3, tariff_flag);
						System.out.println("Beneficiary_Name" + Beneficiary_Name);
						System.out.println("Schemes" + Schemes);
						System.out.println("tariff_flag" + tariff_flag);
					 
						  
						res = ps_check.executeQuery();

						while (res.next()) {
							xmlvariable += "<recordfound> 1 </recordfound>";
							xmlvariable += "<QTY_FROM>"
									+ res.getDouble("QTY_FROM") + "</QTY_FROM>";
							xmlvariable += "<QTY_TO>" + res.getDouble("QTY_TO")
									+ "</QTY_TO>";
							xmlvariable += "<TARIFF_RATE>"
									+ res.getDouble("TARIFF_RATE")
									+ "</TARIFF_RATE>";
							xmlvariable += "<TARIFF_W_E_F>"
									+ res.getDate("TARIFF_W_E_F")
									+ "</TARIFF_W_E_F>";
							xmlvariable += "<TARIFF_SLAB_SNO>"
									+ res.getInt("TARIFF_SLAB_SNO")
									+ "</TARIFF_SLAB_SNO>";
							// xmlvariable += "<MIN_FLAG>" +
							// res.getString("MIN_FLAG") + "</MIN_FLAG>";
							// xmlvariable += "<ALLOT_FLAG>" +
							// res.getString("ALLOT_FLAG") + "</ALLOT_FLAG>";
							// xmlvariable += "<ALLOT_QTY>" +
							// res.getInt("ALLOT_QTY") + "</ALLOT_QTY>";
							// xmlvariable += "<MIN_QTY>" +
							// res.getInt("MIN_QTY") + "</MIN_QTY>";
							xmlvariable += "<MAX_FLAG>"
									+ res.getString("MAX_FLAG") + "</MAX_FLAG>";
							xmlvariable += "<ACTIVE_STATUS>"
									+ res.getString("ACTIVE_STATUS")
									+ "</ACTIVE_STATUS>";
							xmlvariable += "<METRE_SNO>"
									+ res.getInt("METRE_SNO") + "</METRE_SNO>";
							// xmlvariable += "<TARIFF_SLAB_SNO>" +
							// res.getInt("TARIFF_SLAB_SNO") +
							// "</TARIFF_SLAB_SNO>";
							xmlvariable += "<flag>success</flag>";
							System.out.println(xmlvariable);
						}  

						ps_check = connection
								.prepareStatement("select BENEFICIARY_SNO,MIN_FLAG,ALLOT_FLAG,ALLOT_QTY,MIN_QTY from PMS_DCB_ALLOTTED where BENEFICIARY_SNO=? and SCH_SNO=?");
						ps_check.setInt(1, Beneficiary_Name);
						ps_check.setInt(2, Schemes);
						res = ps_check.executeQuery();
						while (res.next()) {

							xmlvariable += "<MIN_FLAG>"
									+ res.getString("MIN_FLAG") + "</MIN_FLAG>";
							xmlvariable += "<ALLOT_FLAG>"
									+ res.getString("ALLOT_FLAG")
									+ "</ALLOT_FLAG>";
							xmlvariable += "<ALLOT_QTY>"
									+ res.getString("ALLOT_QTY")
									+ "</ALLOT_QTY>";
							xmlvariable += "<MIN_QTY>"
									+ res.getString("MIN_QTY") + "</MIN_QTY>";
							xmlvariable += "<flag>success</flag>";
						}

					} catch (Exception e) {
						xmlvariable += "<flag>failure</flag>";
						System.out.println(e + "not reterived!");
					}
				} else {
					xmlvariable += "<flag>success</flag>";
					xmlvariable += "<recordfound> 0 </recordfound>";
				}
				xmlvariable += "</response>";
			}
			// for schemegrid ends
			if (tariff_flag.equals("B")) {
				try {
					ps_check = connection
							.prepareStatement("select count(*) as countsno from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and TARIFF_FLAG=? and ACTIVE_STATUS='A'");
					ps_check.setInt(1, Beneficiary_Name);
					ps_check.setString(2, tariff_flag);

					rs_check = ps_check.executeQuery();
					if (rs_check.next()) {
						countsno = rs_check.getInt("countsno");
						System.out.println("countsno" + countsno);
					}
				} catch (Exception e) {
					System.out.println("Error_reterival_for_grid");
				}
				if (countsno > 0) {
					try {
						ps_check = connection
								.prepareStatement("select PMS_DCB_TARIFF_SLAB.TARIFF_SLAB_SNO AS TARIFF_SLAB_SNO ,PMS_DCB_TARIFF_SLAB.METRE_SNO AS METRE_SNO,PMS_DCB_TARIFF_SLAB.TARIFF_FLAG AS TARIFF_FLAG,PMS_DCB_TARIFF_SLAB.QTY_FROM AS QTY_FROM,PMS_DCB_TARIFF_SLAB.QTY_TO AS QTY_TO,PMS_DCB_TARIFF_SLAB.TARIFF_RATE AS TARIFF_RATE,PMS_DCB_TARIFF_SLAB.TARIFF_W_E_F AS TARIFF_W_E_F,PMS_DCB_TARIFF_SLAB.ACTIVE_STATUS AS ACTIVE_STATUS,PMS_DCB_TARIFF_SLAB.MAX_FLAG AS MAX_FLAG,innquer.MIN_FLAG AS MIN_FLAG,innquer.ALLOT_FLAG AS ALLOT_FLAG,innquer.ALLOT_QTY AS ALLOT_QTY,innquer.MIN_QTY AS MIN_QTY from (select * from PMS_DCB_ALLOTTED where BENEFICIARY_SNO=?)innquer join PMS_DCB_TARIFF_SLAB on innquer.BENEFICIARY_SNO=PMS_DCB_TARIFF_SLAB.BENEFICIARY_SNO where PMS_DCB_TARIFF_SLAB.BENEFICIARY_SNO=? and PMS_DCB_TARIFF_SLAB.tariff_flag=? order by PMS_DCB_TARIFF_SLAB.QTY_FROM");
						ps_check.setInt(1, Beneficiary_Name);
						ps_check.setInt(2, Beneficiary_Name);
						ps_check.setString(3, tariff_flag);
						res = ps_check.executeQuery();

						while (res.next()) {
							xmlvariable += "<recordfound> 1 </recordfound>";
							xmlvariable += "<QTY_FROM>"
									+ res.getDouble("QTY_FROM") + "</QTY_FROM>";
							xmlvariable += "<QTY_TO>" + res.getDouble("QTY_TO")
									+ "</QTY_TO>";
							xmlvariable += "<TARIFF_RATE>"
									+ res.getDouble("TARIFF_RATE")
									+ "</TARIFF_RATE>";
							xmlvariable += "<TARIFF_W_E_F>"
									+ res.getDate("TARIFF_W_E_F")
									+ "</TARIFF_W_E_F>";
							xmlvariable += "<MIN_FLAG>"
									+ res.getString("MIN_FLAG") + "</MIN_FLAG>";
							xmlvariable += "<ALLOT_FLAG>"
									+ res.getString("ALLOT_FLAG")
									+ "</ALLOT_FLAG>";
							xmlvariable += "<ALLOT_QTY>"
									+ res.getString("ALLOT_QTY")
									+ "</ALLOT_QTY>";
							xmlvariable += "<MIN_QTY>"
									+ res.getString("MIN_QTY") + "</MIN_QTY>";
							xmlvariable += "<MAX_FLAG>"
									+ res.getString("MAX_FLAG") + "</MAX_FLAG>";
							xmlvariable += "<ACTIVE_STATUS>"
									+ res.getString("ACTIVE_STATUS")
									+ "</ACTIVE_STATUS>";
							xmlvariable += "<METRE_SNO>"
									+ res.getInt("METRE_SNO") + "</METRE_SNO>";
							xmlvariable += "<TARIFF_SLAB_SNO>"
									+ res.getInt("TARIFF_SLAB_SNO")
									+ "</TARIFF_SLAB_SNO>";
							xmlvariable += "<flag>success</flag>";
						}
					} catch (Exception e) {
						xmlvariable += "<flag>failure</flag>";
						System.out.println(e + "not reterived!");
					}
				} else {
					xmlvariable += "<flag>success</flag>";
					xmlvariable += "<recordfound> 0 </recordfound>";
				}
				xmlvariable += "</response>";
			}
		} else if (command_var.equalsIgnoreCase("loadtariffvalue")) {
			xmlvariable = "";
			System.out.println("loadtariffvalue");
			xmlvariable = "<response>";
			xmlvariable += "<command>loadtariffvalue</command>";
			Beneficiary_type = Integer.parseInt(request
					.getParameter("Beneficiary_type"));

			try {
				ps_check = connection
						.prepareStatement("select count(*) AS countsno from PMS_DCB_MST_TARIFF WHERE BENEFICIARY_TYPE =? and ACTIVE_STATUS='A'");
				ps_check.setInt(1, Beneficiary_type);

				rs_check = ps_check.executeQuery();
				if (rs_check.next()) {
					countsno = rs_check.getInt("countsno");
					System.out.println("countsno" + countsno);
				}
			} catch (Exception e) {
				System.out.println("Error_reterival_for_grid");
			}
			if (countsno > 0) 
			{
				try 
				{
					ps = connection.prepareStatement("select TARIFF_ID,TARIFF_RATE,TARIFF_WEF from PMS_DCB_MST_TARIFF WHERE BENEFICIARY_TYPE =? and ACTIVE_STATUS='A'");
					ps.setInt(1, Beneficiary_type);
					res = ps.executeQuery();
					if (res.next()) 
					{
						tarifflagname = 1;
						xmlvariable +="<TARIFF_RATE>"+res.getDouble("TARIFF_RATE")+"</TARIFF_RATE>";
						xmlvariable +="<TARIFF_WEF>"+res.getDate("TARIFF_WEF") + "</TARIFF_WEF>";
						xmlvariable +="<flag>success</flag>";
					}
				} catch (Exception e) 
				{
					xmlvariable += "<flag>failure</flag>";
					System.out.println(e + "not reterived!");
				}
			} else {

				xmlvariable += "<TARIFF_RATE>" + "-" + "</TARIFF_RATE>";
				xmlvariable += "<TARIFF_WEF>" + "-" + "</TARIFF_WEF>";

				xmlvariable += "<flag>success</flag>";

			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("minupdate")) {
			xmlvariable = "";

			xmlvariable = "<response>";
			xmlvariable += "<command>minupdate</command>";
			try {
				ps_check = connection
						.prepareStatement("select PMS_DCB_SETTING.OFFICE_ID,PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,PMS_DCB_SETTING.MONTH,PMS_DCB_SETTING.YEAR,PMS_DCB_FREEZE_STATUS.WC_FREEZED AS WC_FREEZED from PMS_DCB_MST_BENEFICIARY join PMS_DCB_SETTING on  PMS_DCB_MST_BENEFICIARY.STATUS='L' and PMS_DCB_MST_BENEFICIARY.OFFICE_ID=PMS_DCB_SETTING.OFFICE_ID join PMS_DCB_FREEZE_STATUS on PMS_DCB_SETTING.OFFICE_ID=PMS_DCB_FREEZE_STATUS.OFFICE_ID and PMS_DCB_SETTING.MONTH=PMS_DCB_FREEZE_STATUS.CASHBOOK_MONTH and PMS_DCB_SETTING.YEAR=PMS_DCB_FREEZE_STATUS.CASHBOOK_YEAR where PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=?");
				ps_check.setInt(1, Beneficiary_Name);

				rs_check = ps_check.executeQuery();
				if (rs_check.next()) {
					WC_FREEZED_VALUE = rs_check.getString("WC_FREEZED");

				}
			} catch (Exception e) {
				System.out.println(e + "not reterived!");
			}
		 
			 WC_FREEZED_VALUE=obj.isNull(WC_FREEZED_VALUE, 1);
			if (WC_FREEZED_VALUE.equals("Y") || WC_FREEZED_VALUE.equals("y")) {
				xmlvariable += "<updateflag>1</updateflag>";
				xmlvariable += "<flag>success</flag>";
			} else {
				try {
					ps = connection
							.prepareStatement("update PMS_DCB_ALLOTTED set MIN_FLAG=? ,MIN_QTY=? where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=?");

					ps.setString(1, mini_flag_value);
					ps.setDouble(2, text_min_qty);
					ps.setInt(3, Beneficiary_Name);
					ps.setInt(4, Metre_Location);
					ps.setInt(5, Schemes);

					ps.executeUpdate();

					xmlvariable += "<updateflag>0</updateflag>";
					xmlvariable += "<flag>success</flag>";

				}

				catch (Exception e) {
					xmlvariable += "<updateflag>-1</updateflag>";
					xmlvariable += "<flag>failure</flag>";
					System.out.println(e + "not reterived!");
				}
			}

			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("allotupdate")) {
			xmlvariable = "";
			System.out.println("allotupdate");
			xmlvariable = "<response>";
			xmlvariable += "<command>allotupdate</command>";
			try {
				ps_check = connection
						.prepareStatement("select PMS_DCB_SETTING.OFFICE_ID,PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,PMS_DCB_SETTING.MONTH,PMS_DCB_SETTING.YEAR,PMS_DCB_FREEZE_STATUS.WC_FREEZED AS WC_FREEZED from PMS_DCB_MST_BENEFICIARY join PMS_DCB_SETTING on PMS_DCB_MST_BENEFICIARY.STATUS='L' and  PMS_DCB_MST_BENEFICIARY.OFFICE_ID=PMS_DCB_SETTING.OFFICE_ID join PMS_DCB_FREEZE_STATUS on PMS_DCB_SETTING.OFFICE_ID=PMS_DCB_FREEZE_STATUS.OFFICE_ID and PMS_DCB_SETTING.MONTH=PMS_DCB_FREEZE_STATUS.CASHBOOK_MONTH and PMS_DCB_SETTING.YEAR=PMS_DCB_FREEZE_STATUS.CASHBOOK_YEAR where PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=?");
				ps_check.setInt(1, Beneficiary_Name);

				rs_check = ps_check.executeQuery();
				if (rs_check.next()) {
					WC_FREEZED_VALUE = obj.isNull(rs_check.getString("WC_FREEZED"),1);

				}
			} catch (Exception e) {
				System.out.println(e + "not reterived!");
			}
			if ((WC_FREEZED_VALUE.trim()).equals("Y")
					|| (WC_FREEZED_VALUE.trim()).equals("y")) {
				xmlvariable += "<updateflag>1</updateflag>";
				xmlvariable += "<flag>success</flag>";

			} else {
				try {
					ps = connection
							.prepareStatement("update PMS_DCB_ALLOTTED set ALLOT_FLAG=? ,ALLOT_QTY=? where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=?");

					ps.setString(1, allot_flag_value);
					ps.setDouble(2, text_allot_flag);
					ps.setInt(3, Beneficiary_Name);
					ps.setInt(4, Metre_Location);
					ps.setInt(5, Schemes);

					ps.executeUpdate();

					xmlvariable += "<updateflag>0</updateflag>";
					xmlvariable += "<flag>success</flag>";

				}

				catch (Exception e) {
					xmlvariable += "<updateflag>-1</updateflag>";
					xmlvariable += "<flag>failure</flag>";
					System.out.println(e + "not reterived!");
				}
			}

			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadupdate")) {
			xmlvariable = "";
			xmlvariable = "<response>";
			xmlvariable += "<command>loadupdate</command>";
			try {

				ps_check = connection.prepareStatement("select PMS_DCB_SETTING.OFFICE_ID,PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,PMS_DCB_SETTING.MONTH,PMS_DCB_SETTING.YEAR,PMS_DCB_FREEZE_STATUS.WC_FREEZED AS WC_FREEZED from PMS_DCB_MST_BENEFICIARY join PMS_DCB_SETTING on PMS_DCB_MST_BENEFICIARY.STATUS='L' and PMS_DCB_MST_BENEFICIARY.OFFICE_ID=PMS_DCB_SETTING.OFFICE_ID join PMS_DCB_FREEZE_STATUS on PMS_DCB_SETTING.OFFICE_ID=PMS_DCB_FREEZE_STATUS.OFFICE_ID and PMS_DCB_SETTING.MONTH=PMS_DCB_FREEZE_STATUS.CASHBOOK_MONTH and PMS_DCB_SETTING.YEAR=PMS_DCB_FREEZE_STATUS.CASHBOOK_YEAR where PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=?");
				ps_check.setInt(1, Beneficiary_Name);
				rs_check = ps_check.executeQuery();

				if (rs_check.next()) 
				{
					WC_FREEZED_VALUE = obj.isNull(rs_check.getString("WC_FREEZED"),1);
				}
			} catch (Exception e) {
				System.out.println("Error_reterival_for_WC_FREEZED_VALUE");
			}

			if (WC_FREEZED_VALUE == null || WC_FREEZED_VALUE == "'null'")
				WC_FREEZED_VALUE = "N";

			if ((WC_FREEZED_VALUE.trim()).equals("Y") || (WC_FREEZED_VALUE.trim()).equals("y")) 
			{
				xmlvariable += "<updateflag>1</updateflag>";
				xmlvariable += "<flag>success</flag>";
			} else {
				try {
					ps = connection.prepareStatement("update PMS_DCB_TARIFF_SLAB set ACTIVE_STATUS='D',UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp() where BENEFICIARY_SNO=? and  METRE_SNO=? and SCH_SNO=? and TARIFF_FLAG=?");
					ps.setString(1, userid);
					ps.setInt(2, Beneficiary_Name);
					ps.setInt(3, Metre_Location);
					ps.setInt(4, Schemes);
					ps.setString(5, tariff_flag);
					ps.executeUpdate();
					ps_update = connection.prepareStatement("update PMS_DCB_ALLOTTED set ACTIVE_STATUS='D',UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp() where BENEFICIARY_SNO=? and  METRE_SNO=? and SCH_SNO=?");
					ps_update.setString(1, userid);
					ps_update.setInt(2, Beneficiary_Name);
					ps_update.setInt(3, Metre_Location);
					ps_update.setInt(4, Schemes);
					ps_update.executeUpdate();
					xmlvariable += "<updateflag>0</updateflag>";
					xmlvariable += "<flag>success</flag>";

				}

				catch (Exception e) {
					xmlvariable += "<flag>failure</flag>";
					xmlvariable += "<updateflag>-1</updateflag>";
					System.out.println(e + "not reterived!");
				}
			}

			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("commandupdatetariff")) {
			xmlvariable = "";
			WC_FREEZED_VALUE="N"; 
			xmlvariable = "<response>";
			xmlvariable += "<command>commandupdatetariff</command>";
			try {
				ps_check = connection.prepareStatement("select PMS_DCB_SETTING.OFFICE_ID,PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO,PMS_DCB_SETTING.MONTH,PMS_DCB_SETTING.YEAR,PMS_DCB_FREEZE_STATUS.WC_FREEZED AS WC_FREEZED from PMS_DCB_MST_BENEFICIARY join PMS_DCB_SETTING on PMS_DCB_MST_BENEFICIARY.STATUS='L' and PMS_DCB_MST_BENEFICIARY.OFFICE_ID=PMS_DCB_SETTING.OFFICE_ID join PMS_DCB_FREEZE_STATUS on PMS_DCB_SETTING.OFFICE_ID=PMS_DCB_FREEZE_STATUS.OFFICE_ID and PMS_DCB_SETTING.MONTH=PMS_DCB_FREEZE_STATUS.CASHBOOK_MONTH and PMS_DCB_SETTING.YEAR=PMS_DCB_FREEZE_STATUS.CASHBOOK_YEAR where PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=?");
				ps_check.setInt(1, Beneficiary_Name);
				rs_check = ps_check.executeQuery();
				if (rs_check.next()) 
				{
					WC_FREEZED_VALUE = obj.isNull(rs_check.getString("WC_FREEZED"),1);
				}
			} catch (Exception e) 
			{
				System.out.println("Error_reterival_for_grid");
				WC_FREEZED_VALUE = "N";
			}
			 
			System.out.println("WC_FREEZED_VALUE" + WC_FREEZED_VALUE);
			
			if (WC_FREEZED_VALUE == null || WC_FREEZED_VALUE == "'null'")
				WC_FREEZED_VALUE = "N";

			

			if ((WC_FREEZED_VALUE.trim()).equals("Y") || (WC_FREEZED_VALUE.trim()).equals("y")) 
			{
				xmlvariable += "<updateflag>1</updateflag>";
				xmlvariable += "<flag>success</flag>";
			} else 
			{
				try {
					
					System.out.println("inside try");
					
					for (i = 0; i < tariff_rate_1.length; i++) 
					{  

						temp_tariff_rate = tariff_rate_1[i].split(",");
						temp_tariffslab = tariffslab[i].split(",");
						
						String rate_val = "";

						for (i = 0; i < temp_tariff_rate.length; i++) 
						{

							tariff_rate = Double.parseDouble(temp_tariff_rate[i]);
							tariffslab_value = Integer.parseInt(temp_tariffslab[i]);
							rate_val += tariff_rate + ",";
							System.out.println("tariff_rate is-->"+tariff_rate);
							System.out.println("tariffslab_value is-->"+tariffslab_value);
							
							try {
								ps = connection.prepareStatement("update PMS_DCB_TARIFF_SLAB set TARIFF_RATE=?,UPDATED_BY_USER_ID='"+userid+"',UPDATED_DATE=clock_timestamp() where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=? and TARIFF_SLAB_SNO=?");
								ps.setDouble(1, tariff_rate);
								ps.setInt(2, Beneficiary_Name);
								ps.setInt(3, Metre_Location);
								ps.setInt(4, Schemes);
								ps.setInt(5, tariffslab_value);
								ps.executeUpdate();
								
								

								if (Metre_Location == 0)   
									ps = connection
											.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set UPDATED_BY_USER_ID='"+userid+"',UPDATED_DATE=clock_timestamp(), TARIFF_RATE='"
													+ rate_val
													+ "'  where METRE_SNO in ( select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO="
													+ Beneficiary_Name
													+ " and  SCHEME_SNO="
													+ Schemes + ")");
								else
									ps = connection
											.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set TARIFF_RATE='"
													+ rate_val
													+ "'  where METRE_SNO="
													+ Metre_Location);

								ps.executeUpdate();
							} catch (Exception e) {
								System.out.println("Error UPDATE 2 " + e);
							}
							xmlvariable += "<updateflag>0</updateflag>";
							xmlvariable += "<flag>success</flag>";
						}
					}

				}

				catch (Exception e) {
					xmlvariable += "<updateflag>-1</updateflag>";
					xmlvariable += "<flag>failure</flag>";
					System.out.println(e + "not reterived!");
				}
			}

			xmlvariable += "</response>";
		}

		out.println(xmlvariable);

	}

}
