package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SourceChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	String meter_status = Controller.meter_status;
	PrintWriter out;
	String new_cond = Controller.new_cond;
	String meter_status2 = Controller.meter_status2;
	public SourceChange() {
		super();
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String process_code = "", xml = "";
		response.setContentType(CONTENT_TYPE);
		Connection con = null;
		Connection con1 = null;
		Statement stmt = null;
		ResultSet rs = null;
		out = response.getWriter();
		Controller obj = new Controller();
		Controller obj1 = new Controller();
		try {
			con = obj.con();
			con1 = obj.con();
			stmt = con.createStatement();
			obj.createStatement(con);
			obj1.createStatement(con1);
			HttpSession session = request.getSession(false);
			String userid = "0", Office_id = "0";
			try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {
				userid = "0";
			}

			if (userid == null) {
				userid = "0";
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')"), 1));
			if (Office_id.equals("0"))
				Office_id = "0";
			process_code = obj.setValue("process_code", request);
			String cond = "";
			cond = "";
			if (process_code.equals("2")) 
			{
				String sch = obj.setValue("sch", request);
				String qry = " SELECT a.BENEFICIARY_SNO as  BENEFICIARY_SNO , "
						+ " a.BENEFICIARY_NAME as BENEFICIARY_NAME "
						+ " FROM PMS_DCB_MST_BENEFICIARY a,PMS_DCB_MST_BENEFICIARY_METRE b "
						+ " WHERE a.status ='L' and b.meter_status='L' and a.beneficiary_sno=b.beneficiary_sno "
						+ " AND a.OFFICE_ID=" + Office_id
						+ " and a.office_id=b.office_id and b.scheme_sno="
						+ sch + " ORDER BY a.BENEFICIARY_NAME ";
				ResultSet rs1 = obj.getRS(qry);
					xml = "<result>";
					xml += "<sno>0</sno>";
					xml += "<name>--Select--</name>";
				while (rs1.next()) 
				{
					xml += "<sno>" + rs1.getInt("BENEFICIARY_SNO") + "</sno>";
					xml += "<name>" + rs1.getString("BENEFICIARY_NAME")+ "</name>";
				}
					xml += "</result>";
			}
			if (process_code.equals("7")) 
			{
				String ben = obj.setValue("ben", request);
				String qry = " SELECT a.BENEFICIARY_SNO as  BENEFICIARY_SNO , "
						+ " a.BENEFICIARY_NAME as BENEFICIARY_NAME,b.METRE_LOCATION as METRE_LOCATION,b.METRE_SNO as METRE_SNO"
						+ " FROM PMS_DCB_MST_BENEFICIARY a,PMS_DCB_MST_BENEFICIARY_METRE b "
						+ " WHERE  b.meter_status='L' and a.beneficiary_sno=b.beneficiary_sno "
						+ " AND a.OFFICE_ID=" + Office_id
						+ " and a.office_id=b.office_id and a.beneficiary_sno="
						+ ben + " ORDER BY a.BENEFICIARY_NAME ";
				ResultSet rs1 = obj.getRS(qry);
					xml = "<result>";
					xml += "<sno>0</sno>";
					xml += "<name>--Select--</name>";
				while (rs1.next()) {

					xml += "<sno>" + rs1.getInt("METRE_SNO") + "</sno>";
					xml += "<name>" + rs1.getString("METRE_LOCATION")+ "</name>";
				}
					xml += "</result>";
			}
			if (process_code.equals("6")) 
			{
				String div = obj.setValue("div", request);
				xml = obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME","PMS_DCB_MST_BENEFICIARY", "where " + new_cond+ "   OFFICE_ID=" + div+ " order by BENEFICIARY_NAME", 2,"--Select Beneficiary--");
			}
			if (process_code.equals("1")) 
			{
				xml = obj.combo_lkup("SCH_SNO","SCH_NAME","pms_sch_master","WHERE sch_sno IN( SELECT DISTINCT SCHEME_SNO FROM PMS_DCB_MST_BENEFICIARY_METRE "+ "WHERE "+ meter_status+ " office_id      ="
										+ Office_id
										+ " AND BENEFICIARY_SNO IN( SELECT BENEFICIARY_SNO FROM PMS_DCB_MST_BENEFICIARY where "
										+ new_cond
										+ " office_id ="
										+ Office_id
										+ ")" + ") order by sch_sno", 2,
								"--Select--"); // for scheme
			} else if (process_code.equals("3")) 
			{
				xml = obj.combo_lkup("OFFICE_ID","OFFICE_NAME","com_mst_all_offices_view","where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP ) and OFFICE_LEVEL_ID='DN'",2, "--Select--"); // for division
			} else if (process_code.equals("4")) 
			{
				String div = obj.setValue("div", request);
				xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME","com_mst_all_offices_view", "where DIVISION_OFFICE_ID="+ div + " and OFFICE_LEVEL_ID='SD'", 2,"--Select--"); // for division
			} else if (process_code.equals("5")) 
			{
				String ben_list = obj.setValue("ben_list", request);
				String WEFMONTH = obj.setValue("pmonth", request);
				String WEFYEAR = obj.setValue("pyear", request);
				String NEWOFFICEID = obj.setValue("div", request);
				List<String> ls = Arrays.asList(ben_list.split(","));
				Object[] parameterValues = ls.toArray();
				int srow = 0;
				PreparedStatement ps;
				String div = obj.setValue("div", request);
				for (int i = 0; i < parameterValues.length; i++) 
				{
					String no = parameterValues[i].toString();
					CallableStatement proc_stmt = null;
					proc_stmt = con.prepareCall("{call PMS_DCB_BEN_INS (?,?) }");
					proc_stmt.setInt(1, Integer.parseInt(no));
					proc_stmt.setInt(2, Integer.parseInt(div));
					proc_stmt.execute();
					Hashtable upd1 = new Hashtable();
					upd1.put("WEFMONTH", WEFMONTH);
					upd1.put("WEFYEAR", WEFYEAR);
					upd1.put("NEWOFFICEID", NEWOFFICEID);
					Hashtable condht1 = new Hashtable();
					condht1.put("BENEFICIARY_SNO", no);
					srow += obj.recordSave(upd1, condht1,"PMS_DCB_MST_BENEFICIARY_HIST", con);
					Hashtable upd = new Hashtable();
					upd.put("OFFICEID", div);
					Hashtable condht = new Hashtable();
					condht.put("BENEFICIARY_SNO", no);
					srow += obj.recordSave(upd, condht,"PMS_DCB_MST_BENEFICIARY", con);
				}
			} else if (process_code.equals("9")) 
			{
				String div = obj.setValue("div", request);
				String ben = obj.setValue("ben", request);
				String sdiv = obj.setValue("ben", request);
				CallableStatement proc_stmt = null;
				String ben_list = obj.setValue("met_list", request);
				List<String> ls = Arrays.asList(ben_list.split(","));
				Object[] parameterValues = ls.toArray();
				for (int i = 0; i < parameterValues.length; i++) 
				{
					String msno = parameterValues[i].toString();
					proc_stmt = con.prepareCall("{call PMS_DCB_METER_INS (?,?,?,?) }");
					proc_stmt.setInt(1, Integer.parseInt(msno));
					proc_stmt.setInt(2, Integer.parseInt(div));
					proc_stmt.setInt(3, Integer.parseInt(ben));
					proc_stmt.setInt(4, Integer.parseInt(sdiv));
					proc_stmt.execute();
				}
			} else if (process_code.equals("10")) 
			{
				xml = "<result>";
				try {
					con.rollback();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
				xml = "<msg>Rollback Complted </msg></result>";
			} else if (process_code.equals("11")) {
				xml = "<result>";
				try {
					con.commit();
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
				xml = "<msg>Commit Complted </msg></result>";
			} else if (process_code.equals("12")) {

				String ben = obj.setValue("ben", request);
				String div = obj.setValue("div", request);
				String qry = " SELECT a.BENEFICIARY_SNO as  BENEFICIARY_SNO , "
						+ " a.BENEFICIARY_NAME as BENEFICIARY_NAME,b.METRE_LOCATION as METRE_LOCATION,b.METRE_SNO as METRE_SNO"
						+ " FROM PMS_DCB_MST_BENEFICIARY a,PMS_DCB_MST_BENEFICIARY_METRE b "
						+ " WHERE  b.meter_status='L' and a.beneficiary_sno=b.beneficiary_sno "
						+ " AND a.OFFICE_ID=" + div
						+ " and a.office_id=b.office_id and a.beneficiary_sno="
						+ ben + " ORDER BY a.BENEFICIARY_NAME ";

				ResultSet rs1 = obj.getRS(qry);
					xml = "<result>";
					xml += "<sno>0</sno>";
					xml += "<name>--Select--</name>";
				while (rs1.next()) {

					xml += "<sno>" + rs1.getInt("METRE_SNO") + "</sno>";
					xml += "<name>" + rs1.getString("METRE_LOCATION")+ "</name>";
				}
					xml += "</result>";
			}
			// /////////////// NEW PROCESS ////////////////////////////////
			else if (process_code.equals("32")) 
			{
				String div = obj.setValue("div", request);
				xml = obj.combo_lkup("OFFICE_ID","OFFICE_NAME","COM_MST_OFFICES","where  OFFICE_ID in ( select OFFICE_ID from  COM_OFFICE_CONTROL where CONTROLLING_OFFICE_ID="
										+ Office_id
										+ " ) and ( OFFICE_STATUS_ID='RD' or  OFFICE_STATUS_ID='CR' or OFFICE_STATUS_ID='CL'  )   ",
								2, "--Select--"); // for division
			} else if (process_code.equals("31")) 
			{
				String div = obj.setValue("div", request);
				xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME",
						"com_mst_all_offices_view", "where DIVISION_OFFICE_ID="
								+ Office_id + " and OFFICE_LEVEL_ID='SD'", 2,
						"--Select--"); // for division
			} else if (process_code.equals("33")) {
				String div = obj.setValue("div", request);
				String dqry = " SELECT c.sch_name,a.BENEFICIARY_SNO as  BENEFICIARY_SNO , "
						+ " a.BENEFICIARY_NAME as BENEFICIARY_NAME,b.METRE_LOCATION as METRE_LOCATION,b.METRE_SNO as METRE_SNO,c.sch_name, "
						+ " decode(b.METRE_WORKING,'y','Yes','Y','Yes','n','No','N','No') AS METRE_WORKING, decode(b.METRE_FIXED,'y','Yes','Y','Yes','n','No','N','No') AS METRE_FIXED "
						+ " FROM PMS_DCB_MST_BENEFICIARY a,PMS_DCB_MST_BENEFICIARY_METRE b,PMS_SCH_MASTER c "
						+ " WHERE  b.meter_status='L' and a.beneficiary_sno=b.beneficiary_sno "
						+ " AND b.SUB_DIV_ID="
						+ div
						+ " and a.office_id=b.office_id  and c.sch_sno=b.scheme_sno  "
						+ " ORDER BY a.BENEFICIARY_NAME ";

				xml = "<result>";

				PreparedStatement ps3 = con.prepareStatement(dqry);
				ResultSet rs2 = ps3.executeQuery();
				while (rs2.next()) 
				{
					xml += "<BENEFICIARY_SNO>"+ rs2.getString("BENEFICIARY_SNO")+ "</BENEFICIARY_SNO>";
					xml += "<BENEFICIARY_NAME>"+ rs2.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
					xml += "<METRE_WORKING>" + rs2.getString("METRE_WORKING")+ "</METRE_WORKING>";
					xml += "<METRE_FIXED>" + rs2.getString("METRE_FIXED")+ "</METRE_FIXED>";
					xml += "<sch_name>" + rs2.getString("sch_name")+ "</sch_name>";
					xml += "<METRE_SNO>" + rs2.getString("METRE_SNO")+ "</METRE_SNO>";
					xml += "<METRE_LOCATION>" + rs2.getString("METRE_LOCATION")+ "</METRE_LOCATION>";
				}
				xml += "</result>";
			} else if (process_code.equals("34")) 
			{
				String sdiv = obj.setValue("sdiv", request);
				CallableStatement proc_stmt = null;
				String ben_list = obj.setValue("met_list", request);
				List<String> ls = Arrays.asList(ben_list.split(","));
				Object[] parameterValues = ls.toArray();
				int i = 0;
				for (i = 0; i < parameterValues.length; i++) 
				{
					String msno = parameterValues[i].toString();
					proc_stmt = con.prepareCall("{call PMS_DCB_METER_INS_1 (?,?) }");
					proc_stmt.setInt(1, Integer.parseInt(msno));
					proc_stmt.setInt(2, Integer.parseInt(sdiv));
					proc_stmt.execute();
					Hashtable upd = new Hashtable();
					upd.put("METER_STATUS", "'C'");
					Hashtable condht = new Hashtable();
					condht.put("METRE_SNO", msno);
					int srow = obj.recordSave(upd, condht,"PMS_DCB_MST_BENEFICIARY_METRE", con);
				}
				con.setAutoCommit(true);
				xml = "<result><row>" + i + "</row>";
				xml += "</result>";
			} else if (process_code.equals("35")) 
			{
				String sdiv = obj.setValue("div", request);
				String dqry = " SELECT c.sch_name,a.BENEFICIARY_SNO as  BENEFICIARY_SNO , "
						+ " a.BENEFICIARY_NAME as BENEFICIARY_NAME,b.METRE_LOCATION as METRE_LOCATION,b.METRE_SNO as METRE_SNO,c.sch_name, "
						+ " decode(b.METRE_WORKING,'y','Yes','Y','Yes','n','No','N','No') AS METRE_WORKING, decode(b.METRE_FIXED,'y','Yes','Y','Yes','n','No','N','No') AS METRE_FIXED "
						+ " FROM PMS_DCB_MST_BENEFICIARY a,PMS_DCB_MST_BENEFICIARY_METRE b,PMS_SCH_MASTER c "
						+ " WHERE  b.meter_status='L' and a.beneficiary_sno=b.beneficiary_sno "
						+ " AND b.SUB_DIV_ID="
						+ sdiv
						+ " and a.office_id=b.office_id  and c.sch_sno=b.scheme_sno  "
						+ " ORDER BY a.BENEFICIARY_NAME ";
					xml = "<result>";
				PreparedStatement ps3 = con.prepareStatement(dqry);
				ResultSet rs2 = ps3.executeQuery();
				while (rs2.next()) 
				{
					xml += "<BENEFICIARY_SNO>"+ rs2.getString("BENEFICIARY_SNO")+ "</BENEFICIARY_SNO>";
					xml += "<BENEFICIARY_NAME>"+ rs2.getString("BENEFICIARY_NAME")+ "</BENEFICIARY_NAME>";
					xml += "<METRE_WORKING>" + rs2.getString("METRE_WORKING")+ "</METRE_WORKING>";
					xml += "<METRE_FIXED>" + rs2.getString("METRE_FIXED")+ "</METRE_FIXED>";
					xml += "<sch_name>" + rs2.getString("sch_name")+ "</sch_name>";
					xml += "<METRE_SNO>" + rs2.getString("METRE_SNO")+ "</METRE_SNO>";
					xml += "<METRE_LOCATION>" + rs2.getString("METRE_LOCATION")+ "</METRE_LOCATION>";
				}
					xml += "</result>";
			}
			// //////////////////////////////////END //////////////////////
			else if (process_code.equals("41")) 
			{
				String qry = " SELECT a.BENEFICIARY_SNO as  BENEFICIARY_SNO , "
						+ " a.BENEFICIARY_NAME as BENEFICIARY_NAME,b.METRE_LOCATION as METRE_LOCATION,b.METRE_SNO as METRE_SNO"
						+ " FROM PMS_DCB_MST_BENEFICIARY a,PMS_DCB_MST_BENEFICIARY_METRE b "
						+ " WHERE  b.meter_status='L' and a.beneficiary_sno=b.beneficiary_sno "
						+ " AND a.OFFICE_ID=" + Office_id
						+ " and a.office_id=b.office_id "
						+ " ORDER BY a.BENEFICIARY_NAME ";

				ResultSet rs1 = obj.getRS(qry);
					xml = "<result>";
					xml += "<sno>0</sno>";
					xml += "<name>--Select--</name>";
				while (rs1.next()) 
				{
					xml += "<sno>" + rs1.getInt("BENEFICIARY_SNO") + "</sno>";
					xml += "<name>" + rs1.getString("BENEFICIARY_NAME")+ "</name>";
				}
					xml += "</result>";
			}
			out.println(xml);
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}
}
