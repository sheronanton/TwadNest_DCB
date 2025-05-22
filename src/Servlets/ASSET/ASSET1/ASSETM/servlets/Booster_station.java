/* 
 * Created on : dd-mm-yy 
 * Author     : Joanofark.E
 * Last Date  : 
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description
 * 07/03/2012	New   
 * 
 *---------|---------------|--------------------------------------------------- 
 */
package Servlets.ASSET.ASSET1.ASSETM.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Hashtable;

/**
 * Servlet implementation class Booster_station
 */
public class Booster_station extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Booster_station() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Controller obj = new Controller();
		PrintWriter pr = response.getWriter();
		Connection con = null;
		String comment = request.getParameter("comment");
		System.out.println("ASSET->Booster_station->command->" + comment);
		String qry;
		String res = "";
		int ofid = 0, proid = 0, comp = 0, subcomp = 0, schno = 0;
		String sch_no = "", src_comp = "", sub_src_comp = "";
		try {
			con = obj.con();
			obj.createStatement(con);
			sch_no = obj.setValue("sno", request);
			src_comp = obj.setValue("comp", request);
			sub_src_comp = obj.setValue("sub_comp", request);
		} catch (Exception e) {
		}
		schno = Integer.parseInt(sch_no);
		comp = Integer.parseInt(src_comp);
		subcomp = Integer.parseInt(sub_src_comp);
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null) {
			//userid ="twad10950";
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}

		String Office_id = "";
		String project_id="0";
		try {
			project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch_no);
			proid=Integer.parseInt(project_id);
		} catch (Exception e2) 
		{
			proid=Integer.parseInt("0");
			e2.printStackTrace();
		}
		
		try {
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (Office_id == null)
			//Office_id = "0";
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		ofid = Integer.parseInt(Office_id);

		if (comment.equalsIgnoreCase("add")) {
			try {

				con = obj.con();
				obj.createStatement(con);
				int id = obj.getMax("PMS_SCH_ASSET_BOOSTER_PUMPSET",
						"PMS_ASSET_BOOSTER_PS_SNO", "");

				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);

				Hashtable numbers = new Hashtable();
				numbers.put("PMS_ASSET_BOOSTER_PS_SNO", id);
				numbers.put("OFFICE_ID", ofid);
				numbers.put("PROJECT_ID", proid);
				numbers.put("SCH_SNO", schno);
				numbers.put("COMP_SNO", comp);
				numbers.put("SUBCOMP_SNO", subcomp);
				numbers.put("TYPE_OF_PUMPSET", obj.setValue("pump_type",request));
				numbers.put("DUTY", obj.setValue("duty_type", request));
				numbers.put("HEAD", obj.setValue("head", request));
				numbers.put("QUANTITY", obj.setValue("qty", request));
				numbers.put("REMARKS", "'" + obj.setValue("remark", request)+ "'");
				numbers.put("UPDATED_BY_USER_ID", "'" + userid + "'");
				numbers.put("UPDATED_TIME", "clock_timestamp()");
				numbers.put("HORSE_POWER", obj.setValue("hp", request));
				numbers.put("PUMPSET_NO", obj.setValue("no_pump", request));
				

				int r = obj.recordSave(numbers,
						"PMS_SCH_ASSET_BOOSTER_PUMPSET ", con);
				if (r > 0) {
					res = "<response><rows>" + r + "</rows></response>";
				}
				obj.resposeWr(response, res);
			}

			catch (Exception e) {
				System.out.println(e);
			} 
		} else if (comment.equalsIgnoreCase("view")) {
			try {
				con = obj.con();
				obj.createStatement(con);
			
				res = obj.resultXML(" SELECT a.*,  b.type_desc as type_desc  FROM pms_sch_asset_booster_pumpset   a,   pms_sch_asset_pumpset_type   b WHERE a.TYPE_OF_PUMPSET = b.PUMPSET_TYPE_ID  ", con,obj);
						
				obj.resposeWr(response, res);
			} catch (Exception e) {
				System.out.println(e);
			}

		} else if (comment.equalsIgnoreCase("pop")) {
			try {
				con = obj.con();
				obj.createStatement(con);
				res = obj.delRecord("PMS_SCH_ASSET_BOOSTER_PUMPSET",
						" where PMS_ASSET_BOOSTER_PS_SNO=" + schno, con);
				obj.resposeWr(response, res);
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (comment.equalsIgnoreCase("modify")) {
			try {
				con = obj.con();
				obj.createStatement(con);
				Hashtable numbers = new Hashtable();
				Hashtable ht = new Hashtable();
				numbers.put("TYPE_OF_PUMPSET", obj.setValue("pump_type",request));
				
				
				numbers.put("DUTY", obj.setValue("duty_type", request));
				numbers.put("HEAD", obj.setValue("head", request));
				numbers.put("QUANTITY", obj.setValue("qty", request));
				numbers.put("REMARKS", "'" + obj.setValue("remark", request)+ "'");
				numbers.put("UPDATED_BY_USER_ID", "'" + userid + "'");
				numbers.put("UPDATED_TIME", "clock_timestamp()");
				numbers.put("HORSE_POWER", obj.setValue("hp", request));
				//numbers.put("PUMPSET_NO", obj.setValue("no_pump", request));
				ht.put("PMS_ASSET_BOOSTER_PS_SNO", schno);
				int r = obj.recordSave(numbers, ht,"PMS_SCH_ASSET_BOOSTER_PUMPSET ", con);
				res = "<response><rows>" + r + "</rows></response>";
				obj.resposeWr(response, res);

			} catch (Exception e) {
				 
			}
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
