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
import java.util.Hashtable;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Scheme_Change extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public Scheme_Change() 
	{
		super();
	}

	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String process_code = "", xml = "";
		Controller obj = new Controller();
		response.setContentType(CONTENT_TYPE);
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		out = response.getWriter();
		try {
			con = obj.con();
			stmt = con.createStatement();
		} catch (Exception e) {
		}
		HttpSession session = request.getSession(false);
		String userid = "0", Office_id = "0";
		try {
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) {
			userid = "0";
		}
		process_code = obj.setValue("process_code", request);
		if (userid == null) {
			userid = "0";
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		System.out.println("DCB->Scheme_Change-> ->process_code->"+ process_code);
		try {
			Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')"), 1));
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		if (process_code.equals("5")) 
		{
			int row = Integer.parseInt(obj.setValue("row", request));
			int ben_rec = 0;
			for (int i = 1; i <= row; i++) 
			{
				int ben = Integer.parseInt(obj.setValue("bensno" + i, request));
				int ben_count=0;
				
				try {
					ben_count=obj.getCount("PMS_DCB_MST_BENEFICIARY","where OLD_BENEFICIARY_SNO is null ");
				} catch (Exception e1) 
				{
					e1.printStackTrace();
				}
				
				int div = Integer.parseInt(obj.setValue("dv" + i + "1", request));
				CallableStatement proc_stmt1 = null;
				try {
 
					 
					proc_stmt1 = con.prepareCall("{call PMS_DCB_BEN_New_INS (?,?,?) }");
					proc_stmt1.setInt(1, ben);
					proc_stmt1.setInt(2, div);
					proc_stmt1.registerOutParameter(3, java.sql.Types.VARCHAR);
					proc_stmt1.execute();
					ben_rec++;
					String benreturn = obj.isNull(proc_stmt1.getString(3), 1);
					int totalmetrow = Integer.parseInt(obj.setValue("metrow"+ i, request));
					int count_ben = 0;
					try {
						count_ben = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE","where BENEFICIARY_SNO =" + ben+ " and OFFICE_ID=" + Office_id);
					} catch (Exception e) {
					}
					for (int j = 1; j <= totalmetrow; j++) 
					{
						int metsno = Integer.parseInt(obj.setValue("metsno" + i+ "" + j, request));
						int subdiv = Integer.parseInt(obj.setValue("subdiv" + i+ "" + j, request));
						int div_sch = Integer.parseInt(obj.setValue("div_sh"+ i + "" + j, request));
						int DV = Integer.parseInt(obj.setValue("dv" + i + ""+ j, request));
						int Sch_type = Integer.parseInt(obj.getValue("PMS_SCH_MASTER", "SCH_TYPE_ID"," where SCH_SNO=" + div_sch));
						int msno = metsno;
						CallableStatement proc_stmt2 = null;
						proc_stmt2 = con.prepareCall("{call PMS_DCB_METER_NEW_INS (?,?,?,?,?,?) }");
						proc_stmt2.setInt(1, msno);
						proc_stmt2.setInt(2, div);
						proc_stmt2.setInt(3, Integer.parseInt(benreturn));
						proc_stmt2.setInt(4, subdiv);
						proc_stmt2.setInt(5, div_sch);
						proc_stmt2.setInt(6, Sch_type);
						proc_stmt2.execute();
						Hashtable upd = new Hashtable();
						upd.put("METER_STATUS", "'C'");
						Hashtable condht = new Hashtable();
						condht.put("BENEFICIARY_SNO", ben);
						condht.put("METRE_SNO", msno);
						condht.put("OFFICE_ID", Office_id);
						upd.put("UPDATED_BY_USER_ID", "'" + userid + "'");
						upd.put("UPDATED_DATE", "clock_timestamp()");
						int srow = obj.recordSave(upd, condht,"PMS_DCB_MST_BENEFICIARY_METRE", con);
					}
					if (count_ben == totalmetrow) 
					{
						
						Hashtable upd = new Hashtable();
						upd.put("Active", "'N'");
						upd.put("INACTIVE_MTH", obj.setValue("effective_month", request));
						upd.put("INACTIVE_YEAR", obj.setValue("effective_year", request));
						upd.put("UPDATED_BY_USER_ID", "'" + userid + "'");
						upd.put("UPDATED_DATE", "clock_timestamp()");
						Hashtable condht = new Hashtable();
						condht.put("BENEFICIARY_SNO", ben);
						condht.put("OFFICE_ID", Office_id);
						int srow = obj.recordSave(upd, condht,"PMS_DCB_MST_BENEFICIARY", con);
						
					}else
					{  
						Hashtable upd = new Hashtable();
						upd.put("Active", "'Y'");
						Hashtable condht = new Hashtable();
						condht.put("BENEFICIARY_SNO", ben);
						condht.put("OFFICE_ID", Office_id);
						upd.put("UPDATED_BY_USER_ID", "'" + userid + "'");
						upd.put("UPDATED_DATE", "clock_timestamp()");
						int srow = obj.recordSave(upd, condht,"PMS_DCB_MST_BENEFICIARY", con);
					}
				} catch (SQLException e) 
				{
					System.out.println(e);
				} catch (NumberFormatException e) 
				{
					e.printStackTrace();
				} catch (Exception e) 
				{
					e.printStackTrace();
				} 
			}
			xml = "<result><ben_rec>" + ben_rec + "</ben_rec>";
			xml += "</result>";
		} else if (process_code.equals("3")) 
		{
			try {
				xml = obj.combo_Mod(Office_id,"OFFICE_ID","OFFICE_NAME",2, "--Select--");
			} catch (Exception e) 
			{
				e.printStackTrace();
			} // for division
		} else if (process_code.equals("4")) 
		{
			try {
				String div = obj.setValue("div", request);                // Modified
				xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME","com_mst_all_offices_view", "where DIVISION_OFFICE_ID="+ div + " and OFFICE_LEVEL_ID='SD'", 2,"--Select--"); // for division
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		} else if (process_code.equals("6")) 
		{
			try {
				String div = obj.setValue("div", request);
				xml = obj.combo_lkup("SCH_SNO","SCH_NAME","PMS_SCH_MASTER","where SCH_SNO in (Select SCHEME_SNO  from  PMS_DCB_MST_BENEFICIARY_METRE where   office_id= "+ div + ")", 2, "--Select--"); // for
			} catch (Exception e) 
			{
				e.printStackTrace();
			} 
		}
		if (process_code.equals("2"))   
		{
			String sch = obj.setValue("sch", request);
			String BEN_TYPE_ID = obj.setValue("BEN_TYPE_ID", request);

			String qry = " SELECT a.beneficiary_name,a.BENEFICIARY_SNO,b.metre_location,b.METRE_SNO,b.scheme_sno ";
			qry += " FROM pms_dcb_mst_beneficiary a,pms_dcb_mst_beneficiary_metre b ";
			qry += " WHERE a.beneficiary_sno = b.beneficiary_sno";
			qry += " and a.BENEFICIARY_TYPE_ID="
					+ BEN_TYPE_ID
					+ " and b.scheme_sno="
					+ sch
					+ " and a.office_id=" 
					+ Office_id
					+ "  and a.office_id=b.office_id and a.status='L' and b.meter_status='L'";
			qry += " order by  a.beneficiary_name ";
			System.out.println(qry); 
			try {
				xml = obj.resultXML(qry, con, obj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (process_code.equals("11")) 
		{
			String m = obj.setValue("m", request);
			String y = obj.setValue("y", request);
			String DV = obj.setValue("DV", request);
			String qry = " SELECT ben.beneficiary_sno,ben.beneficiary_name,ben.old_office_id,ben.old_beneficiary_sno, ob.*,met.scheme_sno,sch.SCH_NAME,apr.apr_CUR_YR_WC FROM "
					+ " ( SELECT beneficiary_sno,beneficiary_name,old_office_id,"
					+ "   old_beneficiary_sno FROM pms_dcb_mst_beneficiary"
					+ "   WHERE office_id = "+Office_id+" AND old_office_id="+DV+" ) ben"
					+ "  JOIN   (SELECT DISTINCT scheme_sno,      beneficiary_sno,office_id "
					+ "   FROM pms_dcb_mst_beneficiary_metre   ) "
					+ " met ON met.beneficiary_sno = ben.old_beneficiary_sno  AND "
					+ " met.office_id = ben.old_office_id "
					+ " JOIN "
					+ "  ( SELECT   beneficiary_sno,   fin_year,     MONTH,"
					+ "   office_id,    ob_maint_charges,    ob_cur_yr_wc,"
					+ "      ob_yester_yr_wc,      dmd_upto_prv_mth_wc, coln_upto_prv_mth_maint,"
					+ "      coln_upto_prv_mth_yester_yr, coln_upto_prv_mth_wc, ob_for_mth_maint_charges,"
					+ "      ob_for_mth_cur_yr_wc, ob_for_mth_yester_yr_wc,sch_sno,BENEFICIARY_OB_SNO"
					+ "    FROM"
					+ "    pms_dcb_ob_yearly   ) "
					+ "  ob  ON ob.beneficiary_sno = ben.old_beneficiary_sno"
					+ "  AND ob.office_id = ben.old_office_id  AND ob.sch_sno = met.scheme_sno AND ob.MONTH = "
					+ m
					+ " AND ob.fin_year = "
					+ y
					+ " join ( select "
					+ "    SCH_SNO,  SCH_NAME "
					+ "  from  PMS_SCH_MASTER"
					+ " ) sch "
					+ "on sch.SCH_SNO=ob.sch_sno "
					+ " left outer join ( SELECT  SUM(OB_CUR_YR_WC)+ SUM( OB_YESTER_YR_WC) as apr_CUR_YR_WC ,BENEFICIARY_SNO,   FIN_YEAR,    MONTH,  OFFICE_ID   FROM PMS_DCB_OB_YEARLY   WHERE BENEFICIARY_SNO IN  ( SELECT BENEFICIARY_SNO FROM PMS_DCB_MST_BENEFICIARY WHERE OFFICE_ID="
					+ DV+" )   AND FIN_YEAR="
					+ y+" AND MONTH   ="
					+ m+"   GROUP BY beneficiary_sno,    FIN_YEAR,    MONTH,OFFICE_ID ) apr  on apr.BENEFICIARY_SNO=ob.BENEFICIARY_SNO ";
			try {
				xml = obj.resultXML(qry, con, obj);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (process_code.equals("12")) 
		{
			int row = Integer.parseInt(obj.setValue("row", request));
			int ben_rec = 0;
			int ben_row = 0;
			for (int i = 1; i <= row; i++) 
			{
				int ben = Integer.parseInt(obj.setValue("ben" + i, request));
				int div = Integer.parseInt(Office_id);
				String m = obj.setValue("m", request);
				String y = obj.setValue("y", request);
				int sch_ = Integer.parseInt(obj.setValue("sch_" + i, request));
				int old_bensno = Integer.parseInt(obj.setValue("old_bensno" + i, request));
				int obsno = Integer.parseInt(obj.setValue("obsno" + i, request));
				CallableStatement proc_stmt1 = null;
				try {

					proc_stmt1 = con.prepareCall("{call PMS_DCB_BEN_OB_CHANGE (?,?,?,?,?,?,?,?) }");
					proc_stmt1.setInt(1, ben);
					proc_stmt1.setInt(2, obsno);
					proc_stmt1.setInt(3, div);
					proc_stmt1.setInt(4, sch_);
					proc_stmt1.setString(5, userid);
					proc_stmt1.setInt(6, Integer.parseInt(m));
					proc_stmt1.setInt(7, Integer.parseInt(y));
					proc_stmt1.setInt(8, old_bensno);
					proc_stmt1.execute();
					ben_row++;
				} catch (Exception e) {
					System.out.println(e);

				}
			}
			if (ben_row >= 1) 
			{
				xml = "<result><ben_row>" + ben_row + "</ben_row>";
				xml += "</result>";
			}
		}
		if (process_code.equals("14")) 
		{
			String DV = obj.setValue("DV", request);
			String qry = "select BENEFICIARY_NAME,BENEFICIARY_SNO,OFFICE_ID,TARIFF_MODE,OLD_OFFICE_ID,OLD_BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+ Office_id + " and Old_OFFICE_ID=" + DV;
			try {
				xml = obj.resultXML(qry, con, obj);
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		if (process_code.equals("15")) {
			int ben = Integer.parseInt(obj.setValue("BENEFICIARY_SNO", request));
			CallableStatement proc_stmt1 = null;
			try {
				proc_stmt1 = con.prepareCall("{call PMS_DCB_SLAB_RARE_TRANSPFER (?,?,?,?) }");
				proc_stmt1.setInt(1, ben);
				proc_stmt1.setInt(2, Integer.parseInt(Office_id));
				proc_stmt1.setString(3, userid);
				proc_stmt1.registerOutParameter(4, java.sql.Types.VARCHAR);
				proc_stmt1.execute();
				String benreturn = obj.isNull(proc_stmt1.getString(4), 1);
				xml = "<result><ben_row>" + benreturn + "</ben_row>";
				xml += "</result>";
			} catch (Exception e) 
			{
				System.out.println(e);
			}
		}

		out.println(xml);
		out.flush();

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
