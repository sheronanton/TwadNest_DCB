/* 
  * Created on : dd-mm-yy 
  * Author     : sathya
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description							Done By 
  * 17/09/2011		Add the Beneficiary Status to 'L'  
  * 20/09/2011		Add the Meter Status to 'L'
  * 26/09/2011		Change the Delete Qry 					PS
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
import java.sql.Statement;

import java.sql.Types;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
import Servlets.Security.classes.UserProfile;

public class Pms_Dcb_Beneficiary_metre extends HttpServlet {
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
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		String new_cond=Controller.new_cond; 
		String meter_status3=Controller.meter_status3;
		String meter_status2=Controller.meter_status2;
		String meter_status=Controller.meter_status;
		String command_var = "";
		String xmlvariable = "";
		int Beneficiary_Sno, metre_sno;
		double Metre_init_reading;
		int msno = 0;
		int countvalue = 0;
		int flagvariable = 0;
		int Schemesid = 0;
		int flagname = 0;
		int countsno = 0;
		metre_sno = 0;
		int tempvar = 0;
		int delvalue = 0;
		int Beneficiary_Name = 0;
		Beneficiary_Sno = 0;
		int subdivisionid = 0;
		Metre_init_reading = 0;
		double Multiply_factor;
		Multiply_factor = 1;
		String TARIFF_FLAG = "";
		String Metre_Location = "";
		String meterfixed = "";
		String meterworking = "";
		String Init_Reading_Record_date = "";
		int Beneficiary_type = 0;
		String Service_Connection_date = "";
		int Habitation_Name = 0;
		int Consumption_Category = 0;
		int Metre_Type = 0;
		String Service_Connection = "";
		String officename = "";
		int Schemes = 0;
		int SubDivision = 0;
		int district_Name = 0;
		Connection connection = null;
		PreparedStatement ps, ps1, ps_new, ps_new8, ps_oid, ps_off, ps_ben_name, ps_vill_pan, psdel, ps_check, ps_insert, ps_max, ps_update;
		ResultSet res, res_new, res_new8, result_new, res_off, res_ben_name, rs_vill_pan, resdel, rs_check, res_insert, res_max;
		res_off = null;
		res = null;
		ps = null;
		ps1 = null;
		resdel = null;
		rs_check = null;
		res_insert = null;
		int countinsert = 0;
		int dis=0;
		int empid=0;;
		int oid = 0;
		int hablist = 0;
		int tariff_id = 0;
		int BENEFICIARY_TYPE_ID = 0;
		int SCH_TYPE_ID = 0;
		int OTHERS_PRIVATE_SNO = 0;
		int VILLAGE_PANCHAYAT_SNO = 0;
		int URBANLB_SNO = 0;
		int block_Name = 0;
		String Applicableval = "";
		String parentmetre_value = "";
		String SETTING_FLAG = "";
		String OFFICE_LEVEL_ID="";
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
			
			
			
			try {
				connection.clearWarnings();
			} catch (SQLException e) {
				//System.out.println("Exception in creating statement:" + e);
			}
		} catch (Exception e) {
			//System.out.println("Exception in openeing connection:" + e);
		}
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			

		} catch (Exception e) {
			//System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		

		UserProfile empProfile = (UserProfile) session
				.getAttribute("UserProfile");

		
		Controller obj=new Controller();
	   
		// int empid=1758;

		String oname = "";
		  
		try {
			empid = empProfile.getEmployeeId();
		
			ps_oid = connection.prepareStatement("select CASE WHEN OLD_OFFICE_ID IS NULL AND DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN  SWITCH_ID ELSE  OFFICE_ID	END	AS OFFICE_ID FROM PMS_DCB_COM_OFFICE_SWITCH WHERE EMPLOYEE_ID=?");

			
		//	ps_oid = connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
			ps_oid.setInt(1, empid);
			result_new = ps_oid.executeQuery();		
			if (result_new.next()) {		
					oid = result_new.getInt("OFFICE_ID");
			}
			obj.createStatement(connection);
			OFFICE_LEVEL_ID=obj.getValue("com_mst_offices", "OFFICE_LEVEL_ID","where OFFICE_ID="+oid+ "  ");
			
		} catch (Exception e) {
			//System.out.println(e);
		}
		
		try {
			command_var = request.getParameter("command");
		 
			// Metre_Code=request.getParameter("Metre_Code");
			try {
				hablist = Integer.parseInt(request.getParameter("hablist"));
			} catch (Exception e) {
				//System.out.println("hablist" + e);
			}
			try {
				Beneficiary_Sno = Integer.parseInt(request
						.getParameter("Beneficiary_type"));
			} catch (Exception e) {
				//System.out.println("Beneficiary_Sno" + e);
			}
			try {
				Habitation_Name = Integer.parseInt(request
						.getParameter("Habitation_Name"));
			} catch (Exception e) {
				//System.out.println("Habitation_Name" + e);
			}

			
			try {
				dis = Integer.parseInt(request
						.getParameter("dis"));
			} catch (Exception e) {
				//System.out.println("Beneficiary_Name" + e);
				dis=0;
			}
			try {
				Beneficiary_Name = Integer.parseInt(request
						.getParameter("Beneficiary_Name"));
			} catch (Exception e) {
				//System.out.println("Beneficiary_Name" + e);
			}
			try {
				Consumption_Category = Integer.parseInt(request
						.getParameter("Consumption_Category"));
			} catch (Exception e) {
				//System.out.println("Consumption_Category" + e);
			}
			try {
			} catch (Exception e) {
				//System.out.println("Multi_WS_Category" + e);
			}
			
			 System.out.println("DCB->Pms_Dcb_Beneficiary_metre->command->" + command_var);
			 

			try {
				SubDivision = Integer.parseInt(request
						.getParameter("SubDivision"));
			} catch (Exception e) {
				//System.out.println("SubDivision" + e);
			}
			try {
				Schemes = Integer.parseInt(request.getParameter("Schemes"));
			} catch (Exception e) {
				//System.out.println("Schemes" + e);
			}
			try {
				Metre_Location = request.getParameter("Metre_Location");
				Metre_Location = Metre_Location.toUpperCase();
			} catch (Exception e) {
				//System.out.println("Metre_Location" + e);
			}
			try {
				meterfixed = request.getParameter("meterfixed");
			} catch (Exception e) {
				//System.out.println("meterfixed" + e);
			}
			try {
				meterworking = request.getParameter("meterworking");
			} catch (Exception e) {
				//System.out.println("meterworking" + e);
			}
			try {
				Metre_Type = Integer.parseInt(request
						.getParameter("Metre_Type"));
			} catch (Exception e) {
				//System.out.println("Metre_Type" + e);
			}
			try {
				Metre_init_reading = Double.parseDouble(request
						.getParameter("Metre_init_reading"));
			} catch (Exception e) {
				//System.out.println("Metre_init_reading" + e);
			}
			try {
				Multiply_factor = Double.parseDouble(request
						.getParameter("Multiply_factor"));
			} catch (Exception e) {
				//System.out.println("Multiply_factor" + e);
			}
			try {
				Init_Reading_Record_date = request
						.getParameter("Init_Reading_Record_date");
			} catch (Exception e) {
				//System.out.println("Init_Reading_Record_date" + e);
			}
			 
			try {
				Service_Connection = request.getParameter("Service_Connection");
			} catch (Exception e) {
				//System.out.println("serviceno" + e);
			}
			try {
				Service_Connection_date = request
						.getParameter("Service_Connection_date");
			} catch (Exception e) {
				//System.out.println("Connectiondate" + e);
			}
			try {
				BENEFICIARY_TYPE_ID = Integer.parseInt(request
						.getParameter("BENEFICIARY_TYPE_ID"));
			} catch (Exception e) {
				//System.out.println("BENEFICIARY_TYPE_ID" + e);
			}
			try {
				SCH_TYPE_ID = Integer.parseInt(request
						.getParameter("SCH_TYPE_ID"));
			} catch (Exception e) {
				//System.out.println("SCH_TYPE_ID" + e);
			}
			try {
				OTHERS_PRIVATE_SNO = Integer.parseInt(request
						.getParameter("OTHERS_PRIVATE_SNO"));
			} catch (Exception e) {
				//System.out.println("OTHERS_PRIVATE_SNO" + e);
			}
			try {
				VILLAGE_PANCHAYAT_SNO = Integer.parseInt(request
						.getParameter("VILLAGE_PANCHAYAT_SNO"));
			} catch (Exception e) {
				//System.out.println("VILLAGE_PANCHAYAT_SNO" + e);
			}
			try {
				URBANLB_SNO = Integer.parseInt(request
						.getParameter("URBANLB_SNO"));
			} catch (Exception e) {
				//System.out.println("URBANLB_SNO" + e);
			}
			try {
				district_Name = Integer.parseInt(request
						.getParameter("district_Name"));
			} catch (Exception e) {
				//System.out.println("district_Name" + e);
			}
			try {
				block_Name = Integer.parseInt(request
						.getParameter("block_Name"));
			} catch (Exception e) {
				//System.out.println("block_Name" + e);
			}
			try {
				block_Name = Integer.parseInt(request
						.getParameter("block_Name"));
			} catch (Exception e) {
				//System.out.println("block_Name" + e);
			}
			 
			try {
				parentmetre_value = request.getParameter("parentmetre_value");
			} catch (Exception e) {
				//System.out.println("parentmetre_value" + e);
			}
		 
			 
			 
  
		} catch (Exception e) {
			//System.out.println("Error in reterival:" + e);
		}
		String Bill_month="",Bill_year="";
		
		
		try {
			 Bill_month=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+oid),1);
			 Bill_year=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+oid),1);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		if (command_var.equalsIgnoreCase("add")) {

			xmlvariable = "<response>";
			xmlvariable += "<command>add</command>";
			try {
				ps_max = connection
						.prepareStatement("select max(METRE_SNO) AS metre_sno from PMS_DCB_MST_BENEFICIARY_METRE   " );
				res_max = ps_max.executeQuery();
				if (res_max.next())
				{
					metre_sno = res_max.getInt("metre_sno");
				}
			} catch (Exception e) {  
			}
			if (metre_sno > 0) {
				metre_sno = metre_sno + 1;
			} else {
				metre_sno = 1;
			}
			try {
				 
				 
				if (BENEFICIARY_TYPE_ID == 6) {
					if ((Consumption_Category == 0)	|| (Consumption_Category == 1)) {
						ps_insert = connection
								.prepareStatement("select count(*)as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE  \n"
										+ "where   \n" +meter_status
										+ " BENEFICIARY_SNO=?  \n"
										+ " and  \n"
										+ " SUB_DIV_ID=?  \n"
										+ " and  \n"
										+ " scheme_sno=?  \n"
										+ "and  \n"
										+ " HABITATION_SNO=? ");
						
						
						
						
						ps_insert.setInt(1, Beneficiary_Name);
						ps_insert.setInt(2, SubDivision);
						ps_insert.setInt(3, Schemes);
						ps_insert.setInt(4, Habitation_Name);
						res_insert = ps_insert.executeQuery();
						
						
						if (res_insert.next()) {
							countinsert = res_insert.getInt("countinsert");
						}
					}
				}
				if ((BENEFICIARY_TYPE_ID == 1) || (BENEFICIARY_TYPE_ID == 2)
						|| (BENEFICIARY_TYPE_ID == 3)
						|| (BENEFICIARY_TYPE_ID == 4)
						|| (BENEFICIARY_TYPE_ID == 5)) {
					if (Consumption_Category == 0) {
						ps_insert = connection
								.prepareStatement("select count(*) as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE\n"
										+ "where \n" +meter_status
										+ " BENEFICIARY_SNO=?\n"
										+ "and\n"
										+ "SUB_DIV_ID=?\n"
										+ "and\n"
										+ "SCHEME_SNO=?");
						ps_insert.setInt(1, Beneficiary_Name);
						ps_insert.setInt(2, SubDivision);
						ps_insert.setInt(3, Schemes);
						res_insert = ps_insert.executeQuery();
						if (res_insert.next()) {
							countinsert = res_insert.getInt("countinsert");
						}

					}
				}

				if ((Consumption_Category == 0) || (BENEFICIARY_TYPE_ID > 6)) {
					ps_insert = connection
							.prepareStatement("select count(*) as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE\n"
									+ "where \n "+meter_status
									+ "BENEFICIARY_SNO=?\n"
									+ "and\n"
									+ "SUB_DIV_ID=?\n"
									+ "and\n" 
									+ "scheme_sno=?\n"
									+ "and\n"
									+ "METRE_LOCATION=?");
					 
					ps_insert.setInt(1, Beneficiary_Name);
					ps_insert.setInt(2, SubDivision);
					ps_insert.setInt(3, Schemes);
					ps_insert.setString(4, Metre_Location);
					res_insert = ps_insert.executeQuery();
					if (res_insert.next()) {

						countinsert = res_insert.getInt("countinsert");
					}
				}
			 
				if (countinsert == 0) {

					ps = connection
							.prepareStatement("insert into PMS_DCB_MST_BENEFICIARY_METRE(BENEFICIARY_SNO,\n"
									+ "METRE_LOCATION,\n"
									+ "METRE_FIXED,\n"									
									+ "METRE_INIT_READING,\n"
									+ "INIT_READING_RECORD_DT,\n"
									+ "MULTIPLY_FACTOR,\n"
									+ "SCHEME_SNO,\n"
									+ "HABITATION_SNO,\n"
									+ "METRE_WORKING,\n"
									+ "UPDATED_BY_USER_ID,\n"
									+ "UPDATED_DATE,\n"
									+ "METRE_TYPE,\n"
									+ "SERVICE_CON_NO,\n"
									+ "SEVICE_CONN_DATE,\n"
									+ "OFFICE_ID,\n"
									+ "BENEFICIARY_TYPE_ID,\n"
									+ "SCH_TYPE_ID,\n"
									+ "OTHERS_PRIVATE_SNO,\n"
									+ "VILLAGE_PANCHAYAT_SNO,\n"
									+ "URBANLB_SNO,\n"
									+ "BULKWS_CATEGORY,\n"
									+ "SUB_DIV_ID,\n"
									//+ "METRE_SNO,PARENT_METRE,meter_status) values(?,?,?,?,to_date(?,'DD/MM/YYYY'),?,?,?,?,?,clock_timestamp(),?,?,to_date(?,'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,'L')");
									+ "METRE_SNO,PARENT_METRE,meter_status) values(?,?,?,?,to_date(?::VARCHAR,'DD/MM/YYYY'),?,?,?,?,?,clock_timestamp(),?,?,to_date(?::VARCHAR,'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,'L')");

									
					ps.setInt(1, Beneficiary_Name);
					ps.setString(2, Metre_Location);
					ps.setString(3, meterfixed);
					ps.setDouble(4, Metre_init_reading);
					if (Init_Reading_Record_date.equals("")) {
						ps.setNull(5, Types.DATE);
					} else {
						ps.setString(5, Init_Reading_Record_date);
					}
					ps.setDouble(6, Multiply_factor);
					ps.setInt(7, Schemes);
					ps.setInt(8, Habitation_Name);
					ps.setString(9, meterworking);
					ps.setString(10, userid);
					ps.setInt(11, Metre_Type);
					ps.setString(12, Service_Connection);
					if (Service_Connection_date.equals(""))
					{
						ps.setNull(13, Types.DATE);
					} else {
						ps.setString(13, Service_Connection_date);
					}
					ps.setInt(14, oid);
					ps.setInt(15, BENEFICIARY_TYPE_ID);
					ps.setInt(16, SCH_TYPE_ID);
					ps.setInt(17, OTHERS_PRIVATE_SNO);
					ps.setInt(18, VILLAGE_PANCHAYAT_SNO);
					ps.setInt(19, URBANLB_SNO);
					ps.setInt(20, Consumption_Category);
					ps.setInt(21, SubDivision);
					ps.setInt(22, metre_sno);
					ps.setString(23, parentmetre_value);

					ps.executeUpdate();
					ps1 = connection
							.prepareStatement("select max(METRE_SNO) as METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L'  " );
					res = ps1.executeQuery();
					if (res.next()) 
					{
						metre_sno = res.getInt("METRE_SNO");
						xmlvariable += "<metre_sno>" + res.getInt("METRE_SNO")+ "</metre_sno>";
					}

					ps_off = connection
							.prepareStatement("select office_name from  COM_MST_ALL_OFFICES_VIEW  where COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=?");
					ps_off.setInt(1, SubDivision);
					res_off = ps_off.executeQuery();
					if (res_off.next()) 
					{
 						xmlvariable += "<office_name>"+ res_off.getString("office_name")+ "</office_name>";

					}
					ps_off = connection
							.prepareStatement("select SCH_NAME from PMS_SCH_MASTER where SCH_SNO=?");
					ps_off.setInt(1, Schemes);
					res_off = ps_off.executeQuery();
					if (res_off.next()) 
					{
						xmlvariable += "<SCH_NAME><![CDATA["+ res_off.getString("SCH_NAME") + "]]></SCH_NAME>";

					}
					ps_off = connection.prepareStatement("select BEN_TYPE_DESC  from PMS_DCB_BEN_TYPE where BEN_TYPE_ID=?");
					ps_off.setInt(1, BENEFICIARY_TYPE_ID);
					res_off = ps_off.executeQuery();
					if (res_off.next()) 
					{
						xmlvariable += "<BEN_TYPE_DESC>"+ res_off.getString("BEN_TYPE_DESC")+ "</BEN_TYPE_DESC>";
					}
					ps_off = connection.prepareStatement("select BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO=?");
					ps_off.setInt(1, Beneficiary_Name);
					res_off = ps_off.executeQuery();
					if (res_off.next())
					{
						xmlvariable += "<BENEFICIARY_NAME>"+ res_off.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
					}

					xmlvariable += "<Metre_Location>" + Metre_Location+ "</Metre_Location>";
					xmlvariable += "<meterfixed>" + meterfixed+ "</meterfixed>";
					xmlvariable += "<meterworking>" + meterworking+ "</meterworking>";
					xmlvariable += "<Consumption_Category>"+ Consumption_Category + "</Consumption_Category>";
					xmlvariable += "<BENEFICIARY_TYPE_ID>"+ BENEFICIARY_TYPE_ID + "</BENEFICIARY_TYPE_ID>";
					xmlvariable += "<countinsert>" + 0 + "</countinsert>";
					xmlvariable += "<flag>success</flag>";
				}

				else {
					xmlvariable += "<countinsert>" + 1 + "</countinsert>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//System.out.println(e + "not inserted!");
			}
			xmlvariable += "</response>";

			try {

				ps = connection
						.prepareStatement("select count (*) as countvalue from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO=? and  SCHEME_SNO=? and SETTING_FLAG is not NULL");
				ps.setInt(1, Beneficiary_Name);
				ps.setInt(2, Schemes);

				res = ps.executeQuery();
				if (res.next()) {

					countvalue = res.getInt("countvalue");
 
				}

			} catch (Exception e) {
				//System.out.println("Error1" + e);
				xmlvariable += "<flag>faliure</flag>";
			}
			if (countvalue >= 1) {
				try {

					ps = connection
							.prepareStatement("select distinct TARIFF_FLAG,SETTING_FLAG from PMS_DCB_MST_BENEFICIARY_METRE where  "+meter_status+" BENEFICIARY_SNO=? and  SCHEME_SNO=? and SETTING_FLAG is not NULL");
					ps.setInt(1, Beneficiary_Name);
					ps.setInt(2, Schemes);
					res = ps.executeQuery();
					if (res.next()) {

						if (res.getString("SETTING_FLAG") == null) {
							SETTING_FLAG = "-";
						} else {
							SETTING_FLAG = res.getString("SETTING_FLAG");
						}
						TARIFF_FLAG = res.getString("TARIFF_FLAG");

					}
				} catch (Exception e) {
					//System.out.println("Error2" + e);
					xmlvariable += "<flag>faliure</flag>";
				}
				try {
					ps_update = connection
							.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set TARIFF_FLAG=?,SETTING_FLAG=? where BENEFICIARY_SNO=? and SCHEME_SNO=?");
					ps_update.setString(1, TARIFF_FLAG);
					ps_update.setString(2, SETTING_FLAG);
					ps_update.setInt(3, Beneficiary_Name);
					ps_update.setInt(4, Schemes);

					//System.out.println("TARIFF_FLAG" + TARIFF_FLAG);
					//System.out.println("Beneficiary_Name" + Beneficiary_Name);
					//System.out.println("Schemes" + Schemes);

					ps_update.executeUpdate();
				} catch (Exception e) {
					//System.out.println("Error2" + e);
					xmlvariable += "<flag>faliure</flag>";
				}
			}

		} else if (command_var.equalsIgnoreCase("get")) {
			 
			xmlvariable = "<response>";
			xmlvariable += "<command>get</command>";

			try {
				//System.out.println("select count(*) As countsno from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO =?");
				ps_check = connection
						.prepareStatement("select count(*) As countsno from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO =?");
				ps_check.setInt(1, Beneficiary_Name);
				//System.out.println("Beneficiary_Name" + Beneficiary_Name);
				rs_check = ps_check.executeQuery();
				
				if (rs_check.next()) {
					countsno = rs_check.getInt("countsno");
					//System.out.println("countsno" + countsno);
				}
			} catch (Exception e) {
				//System.out.println("Error reterival");

			}
			
			
			//System.out.println("countsno ----->" + countsno);
			if (countsno > 0) {
				try {

					 String qry=" select \n"
						+ "PMS_DCB_MST_BENEFICIARY_METRE.METRE_SNO AS METRE_SNO,\n"
						+ "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO AS Beneficiary_Sno,\n"
						+ "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_NAME AS BENEFICIARY_NAME,\n"
						+ "COM_MST_ALL_OFFICES_VIEW.OFFICE_NAME As officename,\n"
						+ "PMS_SCH_MASTER.SCH_NAME as SCH_NAME,\n"
						+ "PMS_DCB_BEN_TYPE.BEN_TYPE_ID AS BEN_TYPE_ID,\n"
						+ "PMS_DCB_BEN_TYPE.BEN_TYPE_DESC,\n"
						+ "PMS_DCB_MST_BENEFICIARY_METRE.METRE_LOCATION AS METRE_LOCATION,\n"
						+ "PMS_DCB_MST_BENEFICIARY_METRE.METRE_WORKING AS METRE_WORKING,\n"
						+ "PMS_DCB_MST_BENEFICIARY_METRE.BULKWS_CATEGORY AS BULKWS_CATEGORY,\n"
						+ "PMS_DCB_MST_BENEFICIARY_METRE.METRE_WORKING AS METRE_WORKING,\n"
						+ "PMS_DCB_MST_BENEFICIARY_METRE.METRE_FIXED AS METRE_FIXED \n"
						+ "from PMS_DCB_MST_BENEFICIARY_METRE\n"
						+ "\n"
						+ "join\n"
						+ "PMS_DCB_MST_BENEFICIARY \n"
						+ "on \n" +  meter_status2
						+ " PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO\n"
						+ "join\n"
						+ "COM_MST_ALL_OFFICES_VIEW\n"
						+ "on\n"
						+ "PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID=COM_MST_ALL_OFFICES_VIEW.OFFICE_ID\n"
						+ "join\n"
						+ "PMS_SCH_MASTER\n"
						+ "on  " +meter_status2
						+ " PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO=PMS_SCH_MASTER.SCH_SNO "
						+ "JOIN "
						+ "PMS_DCB_BEN_TYPE "
						+ "ON "
						+ "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=PMS_DCB_BEN_TYPE.BEN_TYPE_ID "
						+ "where PMS_DCB_MST_BENEFICIARY.STATUS='L' and    "
						+ "PMS_DCB_MST_BENEFICIARY_METRE.office_id=?  "
						+ "and   "
						+ "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID=?   "
						+ "and   "
						+ "PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO=?   "
						+ "ORDER BY PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_TYPE_ID";
					 
					 System.out.println(qry);
					ps = connection.prepareStatement(qry);
					ps.setInt(1, oid);
					ps.setInt(2, Beneficiary_Sno);
					ps.setInt(3, Beneficiary_Name); 
					res = ps.executeQuery();
					while (res.next()) 
					{
						String m_location= res.getString("METRE_LOCATION");
						if (m_location==null || m_location=="")
							m_location="-";
						
						xmlvariable += "<recordfound>1</recordfound>";
						xmlvariable += "<METRE_SNO>"+res.getInt("METRE_SNO")+"</METRE_SNO>";
						xmlvariable += "<BENEFICIARY_NAME>"+res.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
						xmlvariable += "<METRE_LOCATION>"+m_location.replace('&', '*')+"</METRE_LOCATION>";
						xmlvariable += "<METRE_FIXED>"+res.getString("METRE_FIXED")+"</METRE_FIXED>";
						xmlvariable += "<BULKWS_CATEGORY>"+res.getInt("BULKWS_CATEGORY")+"</BULKWS_CATEGORY>";
						xmlvariable += "<meterworking>"+res.getString("METRE_WORKING")+"</meterworking>";
						xmlvariable += "<SCH_NAME><![CDATA["+res.getString("SCH_NAME")+"]]></SCH_NAME>";
						xmlvariable += "<BEN_TYPE_DESC>"+res.getString("BEN_TYPE_DESC")+"</BEN_TYPE_DESC>";
						xmlvariable += "<BEN_TYPE_ID>"+res.getInt("BEN_TYPE_ID")+"</BEN_TYPE_ID>";
						xmlvariable += "<officename>"+res.getString("officename")+"</officename>";
						xmlvariable += "<countsno>"+countsno+"</countsno>";
						xmlvariable += "<flag>success</flag>";

					}

				} catch (Exception e) {
					xmlvariable += "<flag>failure</flag>";
					//System.out.println(e + "not reterived!");
				}

			} else {
				xmlvariable += "<flag>success</flag>";
				xmlvariable += "<recordfound>0</recordfound>";
			}
			xmlvariable += "</response>"; 
			//System.out.println("Load Location Details " + xmlvariable);
		} else if (command_var.equalsIgnoreCase("update")) {

		 
			xmlvariable = "<response>";
			xmlvariable += "<command>update</command>";
			 
			try {
				metre_sno = Integer.parseInt(request.getParameter("Meter_Sno"));
				ps = connection
						.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set "
								+ "SCH_TYPE_ID=?,"
								+ "BENEFICIARY_SNO=?,"
								+ "METRE_LOCATION=?,"
								+ "METRE_FIXED=?,"
								+ "METRE_INIT_READING=?,"
								+ "INIT_READING_RECORD_DT=to_date(?,'DD/MM/YYYY'),"
								+ "MULTIPLY_FACTOR=?,"
								+ "OFFICE_ID=?,"
								+ "SCHEME_SNO=?,"
								+ "HABITATION_SNO=?,"
								+ "METRE_WORKING=?,"
								+ "UPDATED_BY_USER_ID=?,"
								+ "UPDATED_DATE=clock_timestamp(),"
								+ "BULKWS_CATEGORY=?,"
								+ "METRE_TYPE=?,"
								+ "SERVICE_CON_NO=?,"
								+ "SEVICE_CONN_DATE=to_date(?,'DD/MM/YYYY'),"
								+ "SUB_DIV_ID=?,"
								+ "OTHERS_PRIVATE_SNO=?,"
								+ "VILLAGE_PANCHAYAT_SNO=?,"
								+ "URBANLB_SNO=?,"
								+ "BENEFICIARY_TYPE_ID=?, "
								+ "PARENT_METRE=? "
								+ "where METRE_SNO =?");

				ps.setInt(1, SCH_TYPE_ID);
				ps.setInt(2, Beneficiary_Name);
				ps.setString(3, Metre_Location);
				ps.setString(4, meterfixed);
				ps.setDouble(5, Metre_init_reading);
				ps.setString(6, Init_Reading_Record_date);
				ps.setDouble(7, Multiply_factor);
				ps.setInt(8, oid);
				ps.setInt(9, Schemes);
				ps.setInt(10, Habitation_Name);
				ps.setString(11, meterworking);
				ps.setString(12, userid);
				ps.setInt(13, Consumption_Category);
				ps.setInt(14, Metre_Type);
				ps.setString(15, Service_Connection);
				ps.setString(16, Service_Connection_date);
				ps.setInt(17, SubDivision);
				ps.setInt(18, OTHERS_PRIVATE_SNO);
				ps.setInt(19, VILLAGE_PANCHAYAT_SNO);
				ps.setInt(20, URBANLB_SNO);
				ps.setInt(21, BENEFICIARY_TYPE_ID);
				ps.setString(22, parentmetre_value);
				ps.setInt(23, metre_sno);

				ps.executeUpdate();
				ps_off = connection.prepareStatement("select office_name from  COM_MST_ALL_OFFICES_VIEW  where COM_MST_ALL_OFFICES_VIEW.OFFICE_ID=?");
				ps_off.setInt(1, SubDivision);
				res_off = ps_off.executeQuery();
				if (res_off.next())
				{
					xmlvariable += "<office_name>"+ res_off.getString("office_name")+ "</office_name>";
				}
				
				ps_off = connection.prepareStatement("select SCH_NAME from PMS_SCH_MASTER where SCH_SNO=?");
				ps_off.setInt(1, Schemes);
				res_off = ps_off.executeQuery();
				if (res_off.next())
				{
					xmlvariable += "<SCH_NAME><![CDATA[" + res_off.getString("SCH_NAME")+ "]]></SCH_NAME>";
				}
				
				ps_off = connection.prepareStatement("select BEN_TYPE_DESC  from PMS_DCB_BEN_TYPE where BEN_TYPE_ID=?");
				ps_off.setInt(1, BENEFICIARY_TYPE_ID);
				res_off = ps_off.executeQuery();
				if (res_off.next())
				{
					xmlvariable += "<BEN_TYPE_DESC>"+ res_off.getString("BEN_TYPE_DESC")+ "</BEN_TYPE_DESC>";
				}
				
				ps_off = connection.prepareStatement("select BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO=?");
				ps_off.setInt(1, Beneficiary_Name);
				res_off = ps_off.executeQuery();
				if (res_off.next()) 
				{
					xmlvariable += "<BENEFICIARY_NAME>"+ res_off.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
				}

				xmlvariable += "<metre_sno>" + metre_sno + "</metre_sno>";
				xmlvariable += "<Metre_Location>" + Metre_Location+ "</Metre_Location>";
				xmlvariable += "<meterfixed>" + meterfixed + "</meterfixed>";
				xmlvariable += "<meterworking>" + meterworking+ "</meterworking>";
				xmlvariable += "<Consumption_Category>" + Consumption_Category+ "</Consumption_Category>";
				xmlvariable += "<BENEFICIARY_TYPE_ID>" + BENEFICIARY_TYPE_ID+ "</BENEFICIARY_TYPE_ID>";
				xmlvariable += "<flag>success</flag>";
				
			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//System.out.println(e + "not updated!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("delete")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>delete</command>";
			
			try {
				metre_sno = Integer.parseInt(request.getParameter("Meter_Sno"));
				int year= Integer.parseInt(request.getParameter("cyear"));
				int month= Integer.parseInt(request.getParameter("cmonth"));
				psdel = connection.prepareStatement("select count(*) as countvalue from  PMS_DCB_TRN_MONTHLY_PR   join PMS_DCB_MST_BENEFICIARY_METRE on PMS_DCB_TRN_MONTHLY_PR.METRE_SNO=? and PMS_DCB_TRN_MONTHLY_PR.MONTH=? and PMS_DCB_TRN_MONTHLY_PR.YEAR=? and QTY_CONSUMED_NET <> 0");
				psdel.setInt(1, metre_sno);
				psdel.setInt(2, Integer.parseInt(Bill_month));
				psdel.setInt(3, Integer.parseInt(Bill_year));  
				resdel = psdel.executeQuery();
				if (resdel.next()) { delvalue = resdel.getInt("countvalue"); }
				
				  
				// Date : 19/09/2011 
				// There is  no check at cancel the metre 
				
				System.out.println("delvalue" + delvalue);  
				  
				if (delvalue < 1)   
				{
					
					//Checking whether the beneficiary have balance 
					
//					 String qry = "Select * from ("
//							 	+ "select beneficiary_sno , bill_year, bill_month , balance, row_number() over(partition by beneficiary_sno  order by beneficiary_sno , bill_year desc , bill_month desc "
//							 	+ ") as rn   from full_view_new"
//								+ ") as fin where rn = 1 and beneficiary_sno = "++"";
					
					
					
					
					
					ps = connection
							.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE  set UPDATED_BY_USER_ID='"+userid+"',UPDATED_DATE=clock_timestamp(),CANCEL_MONTH="+month+",CANCEL_YEAR="+year+", meter_status='C'  where METRE_SNO=?");
					ps.setInt(1, metre_sno);  
					ps.executeUpdate();
					xmlvariable += "<checkcons>0</checkcons>";
					xmlvariable += "<metre_sno>" + metre_sno + "</metre_sno>";
					 
					xmlvariable += "<flag>success</flag>";
					Statement stmt=null;
					Connection con=null;
					Controller obj1=new Controller();
					 try {
							con=obj.con();
							   
							stmt=con.createStatement();
							obj.createStatement(con);
							 
					 }catch(Exception e) {
						 
						 System.out.println("Meter---Delete"+e);  
						 
					 }
					Hashtable upd=new Hashtable();
					upd.put("ACTIVE_STATUS","'D'");
				 
					Hashtable condht = new Hashtable();
					condht.put("METRE_SNO",  metre_sno );
					condht.put("TARIFF_FLAG",  "'L'" );  
					   
					int srow  = obj.recordSave(upd, condht,"PMS_DCB_TARIFF_SLAB", con);
					
					ps = connection.prepareStatement("insert into PMS_DCB_MST_METRE_HIST   select METRE_SNO,BENEFICIARY_SNO,BENEFICIARY_TYPE_ID,OFFICE_ID,SUB_DIV_ID,OTHERS_PRIVATE_SNO,VILLAGE_PANCHAYAT_SNO,"+
					"HABITATION_SNO,URBANLB_SNO,SCHEME_SNO,SCH_TYPE_ID,METRE_LOCATION,METRE_FIXED,METRE_WORKING,METRE_INIT_READING,INIT_READING_RECORD_DT,MULTIPLY_FACTOR,PARENT_METRE,METRE_TYPE,BULKWS_CATEGORY,"+
					"SERVICE_CON_NO,SEVICE_CONN_DATE,METRE_W_WEF,METRE_NW_WEF,TARIFF_FLAG,UPDATED_BY_USER_ID,UPDATED_DATE from PMS_DCB_MST_BENEFICIARY_METRE where metre_sno="+metre_sno);
					 ps.executeUpdate(); 
				} else {
					xmlvariable += "<metre_sno>" + metre_sno + "</metre_sno>";
					xmlvariable += "<checkcons>1</checkcons>";
					xmlvariable += "<flag>success</flag>";
				}
			} catch (Exception e) 
			{
				 System.out.println("Meter---Delete"+e);  
				xmlvariable += "<flag>failure</flag>";
				
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadbeneficiarytype")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loadbeneficiarytype</command>";
			try {
				ps = connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");
				res = ps.executeQuery();
				while (res.next()) 
				{
					xmlvariable += "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID")+ "</BEN_TYPE_ID>";
					xmlvariable += "<BEN_TYPE_DESC>"+ res.getString("BEN_TYPE_DESC")+ "</BEN_TYPE_DESC>";
					xmlvariable += "<BEN_TYPE_SDESC>"+ res.getString("BEN_TYPE_SDESC")+ "</BEN_TYPE_SDESC>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) 
			{
				xmlvariable += "<flag>failure</flag>";
			}
			xmlvariable += "</response>";
			
		} else if (command_var.equalsIgnoreCase("loadhabitations")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loadhabitations</command>";
			Beneficiary_type = Integer.parseInt(request.getParameter("Beneficiary_type"));
			try {
				ps = connection.prepareStatement("SELECT HAB_SNO, "
								+ "HAB_NAME FROM PMS_DCB_MST_BENEFICIARY "
								+ "JOIN "
								+ "COM_MST_HABITATIONS "
								+ "ON "
								+ "COM_MST_HABITATIONS.PANCH_SNO=PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO "
								+ "WHERE "
								+ "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_TYPE_ID=? "
								+ "AND  " 
								+ " PMS_DCB_MST_BENEFICIARY.STATUS='L' and PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?     order by HAB_NAME ");
				ps.setInt(1, Beneficiary_type);
				ps.setInt(2, oid);
				res = ps.executeQuery();
				
				while (res.next())
				{
					xmlvariable += "<HAB_CODE>" + res.getInt("HAB_SNO")+ "</HAB_CODE>";
					xmlvariable += "<HNAME>" + res.getString("HAB_NAME")+ "</HNAME>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) 
			{
				xmlvariable += "<flag>failure</flag>";
			}
			xmlvariable += "</response>";
			
		} else if (command_var.equalsIgnoreCase("loadbeneficiaryname")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loadbeneficiaryname</command>";
			String child_div="0";
			try
			{
				child_div=obj.setValue("child_div", request);
			}catch (Exception e) 
			{
				child_div="0";
			}
			
			if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
			{
				oid=Integer.parseInt(child_div);
			}
			
			Beneficiary_type = Integer.parseInt(request.getParameter("Beneficiary_type"));
			try {
				
				String qry="select BENEFICIARY_SNO, "
					+ "BENEFICIARY_TYPE_ID, " + "BENEFICIARY_NAME, "
					+ "OTHERS_PRIVATE_SNO, " + "VILLAGE_PANCHAYAT_SNO, "
					+ "URBANLB_SNO, "
					+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID "
					+ "from PMS_DCB_MST_BENEFICIARY " + " where  "
					+ " PMS_DCB_MST_BENEFICIARY.STATUS='L' and BENEFICIARY_TYPE_ID=?  " + "and  ";				 
					if ("DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
					{
					qry+=" PMS_DCB_MST_BENEFICIARY.OFFICE_ID=? " ;
					}else
					{
						qry+=" PMS_DCB_MST_BENEFICIARY.office_id_ben=? " ;
					}
					qry+="  order by BENEFICIARY_NAME ";
				
				ps = connection.prepareStatement(qry);
				ps.setInt(1, Beneficiary_type); 
				ps.setInt(2, oid);
				res = ps.executeQuery();
				flagname = 0;
				while (res.next()) 
				{
					flagname = 1;
					xmlvariable += "<BENEFICIARY_SNO>"+ res.getInt("BENEFICIARY_SNO")+ "</BENEFICIARY_SNO>";
					xmlvariable += "<BENEFICIARY_NAME>"+ res.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
					xmlvariable += "<BENEFICIARY_TYPE_ID>"+ res.getInt("BENEFICIARY_TYPE_ID")+ "</BENEFICIARY_TYPE_ID>";
					xmlvariable += "<OTHERS_PRIVATE_SNO>"+ res.getInt("OTHERS_PRIVATE_SNO")+ "</OTHERS_PRIVATE_SNO>";
					xmlvariable += "<VILLAGE_PANCHAYAT_SNO>"+ res.getInt("VILLAGE_PANCHAYAT_SNO")+ "</VILLAGE_PANCHAYAT_SNO>";
					xmlvariable += "<URBANLB_SNO>" + res.getInt("URBANLB_SNO")+ "</URBANLB_SNO>";
					xmlvariable += "<flag>success</flag>";
				}
				if (flagname == 0)
				{
					xmlvariable += "<BENEFICIARY_SNO>" + -1+ "</BENEFICIARY_SNO>";
					xmlvariable += "<BENEFICIARY_NAME>" + "No Data"+ "</BENEFICIARY_NAME>";
					xmlvariable += "<BENEFICIARY_TYPE_ID>" + -1+ "</BENEFICIARY_TYPE_ID>";
					xmlvariable += "<OTHERS_PRIVATE_SNO>" + -1+ "</OTHERS_PRIVATE_SNO>";
					xmlvariable += "<VILLAGE_PANCHAYAT_SNO>" + -1+ "</VILLAGE_PANCHAYAT_SNO>";
					xmlvariable += "<URBANLB_SNO>" + -1 + "</URBANLB_SNO>";
					xmlvariable += "<flag>success</flag>";
				}
			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
			}
			xmlvariable += "</response>";
		}

		else if (command_var.equalsIgnoreCase("loadcategory")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loadcategory</command>";
			Beneficiary_Name = Integer.parseInt(request.getParameter("Beneficiary_Name"));
			try {
				ps = connection.prepareStatement("select BENEFICIARY_SNO,BENEFICIARY_NAME,BEN_CONS_CATEGORY,BENEFICIARY_TYPE_ID,OTHERS_PRIVATE_SNO,VILLAGE_PANCHAYAT_SNO, "
								+ "URBANLB_SNO from PMS_DCB_MST_BENEFICIARY  "
								+ " WHERE status='L' and  BENEFICIARY_SNO=?");
				ps.setInt(1, Beneficiary_Name);
				res = ps.executeQuery();
				if (res.next()) 
				{
					tempvar = res.getInt("BENEFICIARY_TYPE_ID");
					xmlvariable += "<BEN_CONS_CATEGORY>"+ res.getInt("BEN_CONS_CATEGORY")+ "</BEN_CONS_CATEGORY>";
					xmlvariable += "<BENEFICIARY_NAME>"+ res.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
					xmlvariable += "<BENEFICIARY_TYPE_ID>"+ res.getInt("BENEFICIARY_TYPE_ID")+ "</BENEFICIARY_TYPE_ID>";
					xmlvariable += "<OTHERS_PRIVATE_SNO>"+ res.getInt("OTHERS_PRIVATE_SNO")+ "</OTHERS_PRIVATE_SNO>";
					xmlvariable += "<VILLAGE_PANCHAYAT_SNO>"+ res.getInt("VILLAGE_PANCHAYAT_SNO")+ "</VILLAGE_PANCHAYAT_SNO>";
					xmlvariable += "<URBANLB_SNO>" + res.getInt("URBANLB_SNO")+ "</URBANLB_SNO>";
					flagvariable = 0;
					ps_new = connection.prepareStatement("select BENEFICIARY_TYPE from  PMS_DCB_MST_TARIFF where BENEFICIARY_TYPE=? and   ACTIVE_STATUS='A' ");
					ps_new.setInt(1, tempvar);
					res_new = ps_new.executeQuery();
					if (res_new.next()) 
					{    
						flagvariable = 1;
					}
					if (flagvariable == 1) 
					{
						xmlvariable += "<BENEFICIARY_TYPE>"+ res_new.getInt("BENEFICIARY_TYPE")+ "</BENEFICIARY_TYPE>";
						xmlvariable += "<flag>success</flag>";
					} else {
						xmlvariable += "<BENEFICIARY_TYPE>" + 0+ "</BENEFICIARY_TYPE>";
						xmlvariable += "<flag>success</flag>";
					}
				}
			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadsubdivision")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loadsubdivision</command>";
			
			String div=obj.setValue("div", request);
			System.out.println("div is "+div);
			if ("0".equals(div) || div==null) div=Integer.toString(oid);
			try 
			{
				/*ps_new8 = connection.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID AS EMPLOYEE_ID , "
								+ "COM_MST_ALL_OFFICES_VIEW.SUBDIVISION_OFFICE_ID AS SUBDIVISION_OFFICE_ID , "
								+ "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID AS OFFICE_ID, "
								+ "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID AS DIVISION_OFFICE_ID, "
								+ "OFFICE_LEVEL_ID AS OFFICE_LEVEL_ID , "
								+ "OFFICE_NAME AS OFFICE_NAME "
								+ "from HRM_EMP_CURRENT_POSTING "
								+ "JOIN "
								+ "COM_MST_ALL_OFFICES_VIEW "
								+ "ON "
								+ "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID=HRM_EMP_CURRENT_POSTING.OFFICE_ID "
								+ "AND "
								+ "OFFICE_LEVEL_ID='SD' "
								+ "WHERE "
								+ "HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=?");*/
				String Query="select SUBDIVISION_OFFICE_ID, OFFICE_ID,OFFICE_NAME from com_mst_all_offices_view where DIVISION_OFFICE_ID="+div+" and OFFICE_LEVEL_ID='SD'";
				System.out.println(Query);
				ps_new8=connection.prepareStatement(Query);
				// empid=2513 
			 
				res_new8 = ps_new8.executeQuery();
				flagvariable = 0;
				while (res_new8.next()) 
				{
					flagvariable = 1;
					xmlvariable += "<SUBDIVISION_OFFICE_ID>"+ res_new8.getInt("SUBDIVISION_OFFICE_ID")+ "</SUBDIVISION_OFFICE_ID>";
					xmlvariable += "<OFFICE_NAME>"+ res_new8.getString("OFFICE_NAME")+ "</OFFICE_NAME>";
					xmlvariable += "<flag>success</flag>";
				}
				
				
				
				if (flagvariable == 0) 
				{
					xmlvariable += "<SUBDIVISION_OFFICE_ID>-1</SUBDIVISION_OFFICE_ID>";
					xmlvariable += "<OFFICE_NAME>No data</OFFICE_NAME>";
					xmlvariable += "<flag>success</flag>";
				} 
			} catch (Exception e) {  
				xmlvariable += "<flag>failure</flag>";				
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadschemes")) 
		{ 
			xmlvariable = "<response>";
			xmlvariable += "<command>loadschemes</command>"; 
			String SCH_NAME="";
			try {
				String qry="SELECT PMS_SCH_MASTER.SCH_SNO AS SCH_SNO,PMS_SCH_MASTER.SCH_NAME     AS SCH_NAME,PMS_SCH_MASTER.SCH_TYPE_ID  AS SCH_TYPE_ID FROM PMS_DCB_DIV_SCHEME_MAP JOIN PMS_SCH_MASTER ON PMS_SCH_MASTER.SCH_SNO =PMS_DCB_DIV_SCHEME_MAP.SCH_SNO   JOIN PMS_SCH_MST_CONFIRM ON PMS_SCH_MST_CONFIRM.SCH_SNO =PMS_SCH_MASTER.SCH_SNO WHERE PMS_DCB_DIV_SCHEME_MAP.OFFICE_ID="+oid+" and pms_sch_mst_confirm.existing_or_other='Y' ";
				System.out.println("pssssssssssssssssssssssssssssss####"+qry);
				ps = connection.prepareStatement(qry);
				
				//ps.setInt(1, oid);
				res = ps.executeQuery();
				flagvariable = 0;
				while (res.next()) 
				{
					flagvariable = 1;
					xmlvariable += "<SCHEME_ID>" + res.getInt("SCH_SNO")+ "</SCHEME_ID>";
					SCH_NAME=res.getString("SCH_NAME"); 
					SCH_NAME= SCH_NAME.replace('&', '*');
					xmlvariable += "<SCHEME_NAME><![CDATA[" +SCH_NAME+ "]]></SCHEME_NAME>";
					xmlvariable += "<SCH_TYPE_ID>" + res.getInt("SCH_TYPE_ID")+ "</SCH_TYPE_ID>";
					xmlvariable += "<flag>success</flag>";
				}
				if (flagvariable == 0) 
				{
					xmlvariable += "<SCHEME_ID>-1</SCHEME_ID>";
					xmlvariable += "<SCHEME_NAME>No data</SCHEME_NAME>";
					xmlvariable += "<flag>success</flag>";
				}
			} catch (Exception e) {
				 xmlvariable += "<flag>failure</flag>";
				 System.out.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
			System.out.println(xmlvariable);
		} else if (command_var.equalsIgnoreCase("subdivisiondis")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>subdivisiondis</command>";
			subdivisionid = Integer.parseInt(request.getParameter("subdivisionid"));
			try {
				ps_new8 = connection.prepareStatement("select OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where office_id=?");
				ps_new8.setInt(1, subdivisionid);
				res_new8 = ps_new8.executeQuery();
				flagvariable = 0;
				if (res_new8.next()) 
				{
					xmlvariable += "<OFFICE_ID>" + res_new8.getInt("OFFICE_ID")+ "</OFFICE_ID>";
					xmlvariable += "<OFFICE_NAME>"+ res_new8.getString("OFFICE_NAME")+ "</OFFICE_NAME>";
					xmlvariable += "<flag>success</flag>";
				}
			} catch (Exception e) 
			{
				xmlvariable += "<flag>failure</flag>";			
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("Schemesval")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>Schemesval</command>";
			Schemesid = Integer.parseInt(request.getParameter("Schemesid"));

			try {
				ps_new8 = connection.prepareStatement("select SCH_SNO,SCH_NAME,OFFICE_ID,SCH_TYPE_ID from PMS_SCH_MASTER where "
								+ "SCH_SNO=?");
				ps_new8.setInt(1, Schemesid);
				res_new8 = ps_new8.executeQuery();
				flagvariable = 0;
				if (res_new8.next())
				{
					xmlvariable += "<SCH_SNO>" + res_new8.getInt("SCH_SNO")+"</SCH_SNO>";
					xmlvariable += "<SCH_NAME><![CDATA["+res_new8.getString("SCH_NAME") + "]]></SCH_NAME>";
					xmlvariable += "<SCH_TYPE_ID>"+ res_new8.getInt("SCH_TYPE_ID") + "</SCH_TYPE_ID>";
					xmlvariable += "<flag>success</flag>";
				}
			} catch (Exception e) 
			{
				xmlvariable += "<flag>failure</flag>";			
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadhabitationlist")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loadhabitationlist</command>";
			hablist = Integer.parseInt(request.getParameter("hablist"));
			try {
				ps_new8 = connection.prepareStatement("select PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO, "
								+ "PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO, "
								+ "COM_MST_HABITATIONS.HAB_SNO As HAB_SNO , "
								+ "COM_MST_HABITATIONS.HAB_NAME As HAB_NAME "
								+ "from PMS_DCB_MST_BENEFICIARY "
								+ "join "
								+ "COM_MST_HABITATIONS "
								+ "on "
								+ "PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO=COM_MST_HABITATIONS.PANCH_SNO "
								+ "where PMS_DCB_MST_BENEFICIARY.STATUS='L'   and "
								+ "PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=?       order by HAB_NAME ");			 
				ps_new8.setInt(1, hablist);
				res_new8 = ps_new8.executeQuery();
				flagvariable = 0;
				String  HAB_NAME="";				
				while (res_new8.next()) 
				{ 
					HAB_NAME=res_new8.getString("HAB_NAME");
					HAB_NAME= HAB_NAME.replace('&', '*');
					flagvariable = 1;
					xmlvariable += "<HAB_SNO>"+ res_new8.getInt("HAB_SNO")+ "</HAB_SNO>";
					xmlvariable += "<HAB_NAME>"+ HAB_NAME + "</HAB_NAME>";
					xmlvariable += "<flag>success</flag>";
				}
				if (flagvariable == 0) 
				{
					xmlvariable += "<HAB_SNO>-1</HAB_SNO>";
					xmlvariable += "<HAB_NAME>No data</HAB_NAME>";
					xmlvariable += "<flag>success</flag>";
				}
			} catch (Exception e) 
			{
				xmlvariable += "<flag>failure</flag>";
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("getgrid")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>getgrid</command>";
			msno = Integer.parseInt(request.getParameter("msno"));
			try {
				ps = connection.prepareStatement("select SCH_TYPE_ID,BENEFICIARY_SNO,METRE_LOCATION,METRE_FIXED,METRE_INIT_READING,INIT_READING_RECORD_DT,MULTIPLY_FACTOR,OFFICE_ID,SCHEME_SNO,HABITATION_SNO,METRE_WORKING,UPDATED_BY_USER_ID,UPDATED_DATE,BULKWS_CATEGORY,METRE_TYPE,SERVICE_CON_NO,SEVICE_CONN_DATE,SUB_DIV_ID,METRE_W_WEF,METRE_NW_WEF,OTHERS_PRIVATE_SNO,VILLAGE_PANCHAYAT_SNO,URBANLB_SNO,BENEFICIARY_TYPE_ID,METRE_SNO,PARENT_METRE from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" METRE_SNO=? ");
				ps.setInt(1, msno);
				res = ps.executeQuery();
				while (res.next()) 
				{
							int temptypeid = res.getInt("BENEFICIARY_TYPE_ID");
							ps_ben_name = connection.prepareStatement("select BENEFICIARY_SNO, "
											+ "BENEFICIARY_TYPE_ID,  "
											+ "BENEFICIARY_NAME, "
											+ "OTHERS_PRIVATE_SNO, "
											+ "VILLAGE_PANCHAYAT_SNO, "
											+ "URBANLB_SNO, "
											+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID "
											+ "from PMS_DCB_MST_BENEFICIARY "
											+ "where  " +new_cond+ " BENEFICIARY_TYPE_ID=? "
											+ "and "
											+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?");
							ps_ben_name.setInt(1, temptypeid);
							ps_ben_name.setInt(2, oid);
		
							res_ben_name = ps_ben_name.executeQuery();
							while (res_ben_name.next()) 
							{
								xmlvariable += "<BENEFICIARY_SNO>"+ res_ben_name.getInt("BENEFICIARY_SNO")+ "</BENEFICIARY_SNO>";
								xmlvariable += "<BENEFICIARY_NAME>"+ res_ben_name.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
							}
							int bensno = res.getInt("BENEFICIARY_SNO");
							ps_ben_name = connection
									.prepareStatement("select BENEFICIARY_SNO, "
											+ "BENEFICIARY_TYPE_ID,  "
											+ "BENEFICIARY_NAME, "
											+ "OTHERS_PRIVATE_SNO, "
											+ "VILLAGE_PANCHAYAT_SNO, "
											+ "URBANLB_SNO, "
											+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID "
											+ "from PMS_DCB_MST_BENEFICIARY "
											+ "where " +new_cond+" BENEFICIARY_TYPE_ID=? "
											+ " and "
											+ "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=?"
											+ " and BENEFICIARY_SNO=?");
							ps_ben_name.setInt(1, temptypeid);
							ps_ben_name.setInt(2, oid);
							ps_ben_name.setInt(3, bensno);
							res_ben_name = ps_ben_name.executeQuery();
							while (res_ben_name.next()) 
							{
								// xmlvariable += "<TARIFF_MODE>"
								// +res_ben_name.getString("TARIFF_MODE") +
								// "</TARIFF_MODE>";
							}
							if (temptypeid == 6) 
							{
								int tempsno = res.getInt("BENEFICIARY_SNO");
								xmlvariable += "<HAB_var>1 </HAB_var>";
								ps_vill_pan = connection.prepareStatement("select PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO, "
												+ "PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO, "
												+ "COM_MST_HABITATIONS.HAB_SNO As HAB_SNO,  "
												+ "COM_MST_HABITATIONS.HAB_NAME As HAB_NAME "
												+ "from PMS_DCB_MST_BENEFICIARY "
												+ "join "
												+ "COM_MST_HABITATIONS "
												+ "on "
												+ "PMS_DCB_MST_BENEFICIARY.VILLAGE_PANCHAYAT_SNO=COM_MST_HABITATIONS.PANCH_SNO "
												+ "where  "
												+ " PMS_DCB_MST_BENEFICIARY.STATUS='L' and  PMS_DCB_MST_BENEFICIARY.BENEFICIARY_SNO=?");
								ps_vill_pan.setInt(1, tempsno);
								rs_vill_pan = ps_vill_pan.executeQuery();
								String HAB_NAME="";
								while (rs_vill_pan.next()) 
								{
									HAB_NAME=rs_vill_pan.getString("HAB_NAME");
									HAB_NAME= HAB_NAME.replace('&', '*');
									xmlvariable += "<HAB_SNO>"+ rs_vill_pan.getInt("HAB_SNO")+ "</HAB_SNO>";
									xmlvariable += "<HAB_NAME>"+ HAB_NAME+ "</HAB_NAME>";
								}
		
							}
							
					String m_location_=res.getString("METRE_LOCATION");
					
					if (m_location_==null || m_location_=="")  m_location_="-";
					
					xmlvariable += "<HAB_var>0 </HAB_var>";
					xmlvariable += "<BEN_SNO>" + res.getInt("BENEFICIARY_SNO")+ "</BEN_SNO>";
					xmlvariable += "<METRE_SNO>" + res.getInt("METRE_SNO")+ "</METRE_SNO>";
					xmlvariable += "<SCH_TYPE_ID>" + res.getInt("SCH_TYPE_ID")+ "</SCH_TYPE_ID>";
					xmlvariable += "<METRE_LOCATION>"+ m_location_.replace('&', '*')+ "</METRE_LOCATION>";
					xmlvariable += "<METRE_FIXED>"+ res.getString("METRE_FIXED") + "</METRE_FIXED>";
					xmlvariable += "<METRE_INIT_READING>"+ res.getDouble("METRE_INIT_READING")+ "</METRE_INIT_READING>";
					
					if (res.getDate("INIT_READING_RECORD_DT") == null)
						xmlvariable += "<INIT_READING_RECORD_DT>-</INIT_READING_RECORD_DT>";
					else
						xmlvariable += "<INIT_READING_RECORD_DT>"+ res.getDate("INIT_READING_RECORD_DT")+ "</INIT_READING_RECORD_DT>";
					
					xmlvariable += "<MULTIPLY_FACTOR>"+ res.getDouble("MULTIPLY_FACTOR")+ "</MULTIPLY_FACTOR>";
					xmlvariable += "<OFFICE_ID>" + res.getInt("OFFICE_ID")+ "</OFFICE_ID>";
					xmlvariable += "<SCHEME_SNO>" + res.getString("SCHEME_SNO")+ "</SCHEME_SNO>";
					xmlvariable += "<HABITATION_SNO>"+ res.getString("HABITATION_SNO")+ "</HABITATION_SNO>";
					xmlvariable += "<METRE_WORKING>"+ res.getString("METRE_WORKING")+ "</METRE_WORKING>";

					xmlvariable += "<BULKWS_CATEGORY>"+ res.getInt("BULKWS_CATEGORY")+ "</BULKWS_CATEGORY>";
					xmlvariable += "<METRE_TYPE>" + res.getInt("METRE_TYPE")+ "</METRE_TYPE>";

					if (res.getString("SERVICE_CON_NO") == null)
						xmlvariable += "<SERVICE_CON_NO>-</SERVICE_CON_NO>";
					else
						xmlvariable += "<SERVICE_CON_NO>"+ res.getString("SERVICE_CON_NO")+ "</SERVICE_CON_NO>";
					
					if (res.getDate("SEVICE_CONN_DATE") == null)
						xmlvariable += "<SEVICE_CONN_DATE>-</SEVICE_CONN_DATE>";
					else
						xmlvariable += "<SEVICE_CONN_DATE>"+ res.getDate("SEVICE_CONN_DATE")+ "</SEVICE_CONN_DATE>";
					
					xmlvariable += "<SUB_DIV_ID>" + res.getInt("SUB_DIV_ID")+ "</SUB_DIV_ID>";
					xmlvariable += "<OTHERS_PRIVATE_SNO>"+ res.getInt("OTHERS_PRIVATE_SNO")+ "</OTHERS_PRIVATE_SNO>";
					xmlvariable += "<VILLAGE_PANCHAYAT_SNO>"+ res.getInt("VILLAGE_PANCHAYAT_SNO")+ "</VILLAGE_PANCHAYAT_SNO>";
					xmlvariable += "<URBANLB_SNO>" + res.getInt("URBANLB_SNO")+ "</URBANLB_SNO>";
					xmlvariable += "<BENEFICIARY_TYPE_ID>"+ res.getInt("BENEFICIARY_TYPE_ID")+ "</BENEFICIARY_TYPE_ID>";
					xmlvariable += "<METRE_SNO>" + res.getInt("METRE_SNO")+ "</METRE_SNO>";
					xmlvariable += "<PARENT_METRE>"+ res.getString("PARENT_METRE") + "</PARENT_METRE>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}

			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadhabname")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loadhabname</command>";
			int bentypeval = Integer.parseInt(request.getParameter("bentypeval"));

			try {
				ps = connection
						.prepareStatement("select HAB_SNO,HAB_NAME from COM_MST_HABITATIONS where HAB_SNO=?");
				ps.setInt(1, bentypeval);
				res = ps.executeQuery();
				flagvariable = 0;
				String HAB_NAME1="";
				while (res.next()) 
				{
					flagvariable = 1;
					HAB_NAME1=res.getString("HAB_NAME");
					HAB_NAME1= HAB_NAME1.replace('&', '*');
					xmlvariable += "<HAB_SNO>" + res.getInt("HAB_SNO")+ "</HAB_SNO>";
					xmlvariable += "<HAB_NAME>" + HAB_NAME1+ "</HAB_NAME>";
					xmlvariable += "<flag>success</flag>";

				}
				if (flagvariable == 0) 
				{
					xmlvariable += "<HAB_SNO>-1</HAB_SNO>";
					xmlvariable += "<HAB_NAME>No data</HAB_NAME>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadschname")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loadschname</command>";
			int schvalue = Integer.parseInt(request.getParameter("schvalue"));
			try {
				String SCH_NAME="";
				ps = connection
						.prepareStatement("select SCH_SNO,SCH_NAME,SCH_TYPE_ID from PMS_SCH_MASTER where SCH_SNO=?");
				ps.setInt(1, schvalue);
				res = ps.executeQuery();
				flagvariable = 0;
				while (res.next()) 
				{
					flagvariable = 1;
					xmlvariable += "<SCH_SNO>" + res.getInt("SCH_SNO")+ "</SCH_SNO>";
					SCH_NAME=res.getString("SCH_NAME");
					SCH_NAME= SCH_NAME.replace('&', '*');
					xmlvariable += "<SCH_NAME><![CDATA[" +SCH_NAME+ "]]></SCH_NAME>";
					xmlvariable += "<SCH_TYPE_ID>" + res.getInt("SCH_TYPE_ID")+ "</SCH_TYPE_ID>";
					xmlvariable += "<flag>success</flag>";
				}
				
				if (flagvariable == 0) 
				{
					xmlvariable += "<SCH_SNO>-1</SCH_SNO>";
					xmlvariable += "<SCH_NAME>No data</SCH_NAME>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("divisionname")) {
			xmlvariable = "<response>";
			xmlvariable += "<command>divisionname</command>";
			try {

				ps_oid = connection
						.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
				ps_oid.setInt(1, oid);
				result_new = ps_oid.executeQuery();
				if (result_new.next()) {
					officename = result_new.getString("OFFICE_NAME");
					xmlvariable += "<officename>" + officename+ "</officename>";
					xmlvariable += "<flag>success</flag>";
				} else {
					xmlvariable += "<officename>0</officename>";
					xmlvariable += "<flag>success</flag>";

				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("habcheckdup")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>habcheckdup</command>";
			try {
				ps = connection
						.prepareStatement("select count(*)as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE     "
								+ "where   "+meter_status+"   "
								+ "BENEFICIARY_SNO=?     "
								+ "and     "
								+ "SUB_DIV_ID=?     "
								+ "and     "
								+ "scheme_sno=?     "
								+ "and     " + "HABITATION_SNO=?");
				ps.setInt(1, Beneficiary_Name);
				ps.setInt(2, SubDivision);
				ps.setInt(3, Schemes);
				ps.setInt(4, Habitation_Name);
				res = ps.executeQuery();

				if (res.next()) 
				{
					countinsert = res.getInt("countinsert");
					xmlvariable += "<countinsert>" + countinsert+ "</countinsert>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("schemecheck")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>schemecheck</command>";
			try {
				ps = connection
						.prepareStatement("select count(*) as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE   "
								+ "where  "+meter_status+"  "
								+ "BENEFICIARY_SNO=?   "
								+ "and   "
								+ "SUB_DIV_ID=?   "
								+ "and   "
								+ "SCHEME_SNO=?");
				ps.setInt(1, Beneficiary_Name);
				ps.setInt(2, SubDivision);
				ps.setInt(3, Schemes);
				res = ps.executeQuery();
				if (res.next()) 
				{
					countinsert = res.getInt("countinsert");
					xmlvariable += "<countinsert>" + countinsert+ "</countinsert>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("metercheck")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>metercheck</command>";
			try {
				ps = connection
						.prepareStatement("select count(*)as  countinsert from PMS_DCB_MST_BENEFICIARY_METRE     "
								+ "where "+meter_status+"     "
								+ "BENEFICIARY_SNO=?     "
								+ "and     "
								+ "SUB_DIV_ID=?     "
								+ "and     "
								+ "scheme_sno=?     "
								+ "and     " + "METRE_LOCATION=?");
				ps.setInt(1, Beneficiary_Name);
				ps.setInt(2, SubDivision);
				ps.setInt(3, Schemes);
				ps.setString(4, Metre_Location);

				res = ps.executeQuery();

				if (res.next())  
				{
					countinsert = res.getInt("countinsert");
					xmlvariable += "<countinsert>" + countinsert+ "</countinsert>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) 
			{
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loaddistrict")) 
		{
			xmlvariable = "<response>";
			xmlvariable += "<command>loaddistrict</command>";
			try {
				ps = connection
						.prepareStatement("SELECT COM_MST_DISTRICTS.DISTRICT_CODE DISTRICT_CODE, "
								+ "COM_MST_DISTRICTS.DISTRICT_NAME As DISTRICT_NAME "
								+ "FROM "
								+ "PMS_DCB_DIV_DIST_MAP "
								+ "JOIN "
								+ "COM_MST_DISTRICTS "
								+ "ON "
								+ "COM_MST_DISTRICTS.DISTRICT_CODE=PMS_DCB_DIV_DIST_MAP.DISTRICT_CODE "
								+ "WHERE "
								+ "OFFICE_ID=? order by COM_MST_DISTRICTS.DISTRICT_NAME");
				ps.setInt(1, oid);
				res = ps.executeQuery();
				while (res.next()) 
				{
					xmlvariable += "<DISTRICT_CODE>"+ res.getInt("DISTRICT_CODE") + "</DISTRICT_CODE>";
					xmlvariable += "<DISTRICT_NAME>"+ res.getString("DISTRICT_NAME")+ "</DISTRICT_NAME>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadblocks")) {
			 

			xmlvariable = "<response>";
			xmlvariable += "<command>loadblocks</command>";
			try {
				ps = connection.prepareStatement("SELECT BLOCK_SNO, "+ "BLOCK_NAME FROM " + "COM_MST_BLOCKS " + "WHERE "+ "DISTRICT_CODE=?" + "order by BLOCK_NAME");
				ps.setInt(1, district_Name);
				res = ps.executeQuery();
				while (res.next()) 
				{
					xmlvariable += "<BLOCK_SNO>" + res.getInt("BLOCK_SNO")+ "</BLOCK_SNO>";
					xmlvariable += "<BLOCK_NAME>" + res.getString("BLOCK_NAME")+ "</BLOCK_NAME>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				//.println(e + "not reterived!");
			}
			xmlvariable += "</response>";
		} else if (command_var.equalsIgnoreCase("loadbenname")) {
			 

			xmlvariable = "<response>";
			xmlvariable += "<command>loadbenname</command>";
			flagname = 0;
			
			 
			try {
				ps = connection.prepareStatement("select BENEFICIARY_NAME, "
						+ "BENEFICIARY_SNO " + "FROM "
						+ "PMS_DCB_MST_BENEFICIARY " + "WHERE  " + new_cond
						+ "BLOCK_SNO=? and DISTRICT_CODE="+dis + " and office_id="+oid+" order by BENEFICIARY_NAME");
				
				ps.setInt(1, block_Name);

				res = ps.executeQuery(); 

				while (res.next()) {

					flagname = 1;
					xmlvariable += "<BENEFICIARY_SNO>"+ res.getInt("BENEFICIARY_SNO")+ "</BENEFICIARY_SNO>";
					xmlvariable += "<BENEFICIARY_NAME>"+ res.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
					xmlvariable += "<flag>success</flag>";
				}
				if (flagname == 0) {
					xmlvariable += "<BENEFICIARY_SNO>-1</BENEFICIARY_SNO>";
					xmlvariable += "<BENEFICIARY_NAME>" + "No Data"+ "</BENEFICIARY_NAME>";
					xmlvariable += "<flag>success</flag>";
				}

			} catch (Exception e) {
				xmlvariable += "<flag>failure</flag>";
				 
			}
			xmlvariable += "</response>";
		}
		out.println(xmlvariable);
		out.flush();
		out.close();
		

	}
}

 
