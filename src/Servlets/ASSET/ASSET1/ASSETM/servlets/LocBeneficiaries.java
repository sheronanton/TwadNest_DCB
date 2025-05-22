package Servlets.ASSET.ASSET1.ASSETM.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Hashtable;

public class LocBeneficiaries extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public LocBeneficiaries() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		Controller obj = new Controller();
		PrintWriter pr=response.getWriter();
		Connection con = null;
		String comment = request.getParameter("comment");
		response.setContentType(CONTENT_TYPE);
		String qry;
		String res="",res1="";
		int ofid = 0, proid = 0, comp = 0, subcomp = 0,schno=0;
		String sch_no="", src_comp="",sub_src_comp="",beneficiary_sno="";
			try{
					con=obj.con();
					obj.createStatement(con);
					sch_no=obj.setValue("sno", request);
					src_comp=obj.setValue("comp",request);
					sub_src_comp=obj.setValue("sub_comp",request);
			}catch (Exception e) {  }
		schno=Integer.parseInt(sch_no);
		comp=Integer.parseInt(src_comp);
		subcomp=Integer.parseInt(sub_src_comp);
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null){ 
			 response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		System.out.println("ASSET->LocBeneficiaries->command->" + comment);  
		String Office_id = "",beneficiary_sno_name="";
		String project_id="0";
		try {
			project_id = obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID","where SCH_SNO=" + schno);
			proid = Integer.parseInt(project_id);
		} catch (Exception e2) {
			proid = Integer.parseInt("0");
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
		{
			Office_id = "0";
		}
		ofid = Integer.parseInt(Office_id);
		if (comment.equalsIgnoreCase("add")) {
			try {
				con = obj.con();
				obj.createStatement(con);
				int id = 0;
				int id1 = 0;
				String loc = "", capacity = "", qty = "";
				String ben_sno = "";
				loc = obj.setValue("location", request);
				id = obj.getMax("PMS_SCH_ASSET_SR", "PMS_ASSET_SR_SNO", "");
				capacity = obj.setValue("capacity", request);
				qty = obj.setValue("quantity", request);
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				Hashtable numbers = new Hashtable();
				numbers.put("PMS_ASSET_SR_SNO", id);
				numbers.put("OFFICE_ID", ofid);
				numbers.put("PROJECT_ID", proid);
				numbers.put("SCH_SNO", schno);
				numbers.put("COMP_SNO", comp);
				numbers.put("SUBCOMP_SNO", subcomp);
				numbers.put("LOCATION", "'" + loc + "'");
				numbers.put("CAPACITY", capacity);
				numbers.put("QTY", qty);
				numbers.put("UPDATED_BY_USER_ID", "'" + userid + "'");
				numbers.put("UPDATED_TIME", "clock_timestamp()");
				int r = obj.recordSave(numbers, "PMS_SCH_ASSET_SR ", con);
				if (r > 0) {
					res = "<response><msg>" + r + "</msg></response>";
				}
				con.close();
				}
		catch (Exception e) {
           System.out.println(e);
		     }
		    obj.resposeWr(response, res) ;
		    }
			else if(comment.equalsIgnoreCase("view"))
			{
				try{
					con = obj.con();
				obj.createStatement(con);
				res = obj.resultXML(" SELECT * from PMS_SCH_ASSET_SR where SCH_SNO="+ schno, con, obj);
				obj.resposeWr(response, res);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (comment.equalsIgnoreCase("pop")) {
			try {
				con = obj.con();
				obj.createStatement(con);
				String no = obj.setValue("sno", request);
				res = obj.delRecord("PMS_SCH_ASSET_SR", " where PMS_ASSET_SR_SNO=" + no, con);
				obj.resposeWr(response, res);
				con.close();
			}catch (Exception e) {
				System.out.println(e);
			}
		} else if (comment.equalsIgnoreCase("modify")) {
			try {
				con = obj.con();
				obj.createStatement(con);

				String loc = "", capacity = "", qty = "";
				String sno = obj.setValue("sno", request);
				loc = obj.setValue("loc", request);
				capacity = obj.setValue("cap", request);
				qty = obj.setValue("qty", request);
				Hashtable numbers = new Hashtable();
				numbers.put("CAPACITY", capacity);
				numbers.put("QTY", qty);
				numbers.put("LOCATION", "'" + loc + "'");
				Hashtable ht = new Hashtable();
				ht.put("PMS_ASSET_SR_SNO", sno);
				int r = obj.recordSave(numbers, ht, "PMS_SCH_ASSET_SR ", con);
				res = "<response><msg>" + r + "</msg></response>";
				obj.resposeWr(response, res);
				con.close();
				}
				catch (Exception e) {
				}
	}else if (comment.equalsIgnoreCase("add_ben")) {
		try {
			con = obj.con();
				obj.createStatement(con);
				int id = 0, sr_sno1 = 0, loc_count = 0, sr_sno_count = 0;
				String loc = "", beneficiary_val = "";
				String ben_sno = "";
				String sr_sno = "";
				loc = obj.setValue("location", request);
				sr_sno = obj.setValue("sr_sno", request);
				sr_sno1 = Integer.parseInt(sr_sno);
				id = obj.getMax("PMS_SCH_ASSET_SR_LOC_BEN","PMS_ASSET_SR_BEN_SNO", "");
				beneficiary_val = obj.setValue("beneficiary_val", request);
				beneficiary_sno = obj.setValue("beneficiary_sno", request);
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				Hashtable numbers = new Hashtable();
				numbers.put("PMS_ASSET_SR_BEN_SNO", id);
				numbers.put("OFFICE_ID", ofid);
				numbers.put("PROJECT_ID", proid);
				numbers.put("SCH_SNO", schno);
				numbers.put("BENEFICIARY_TYPE_ID", beneficiary_val);
				numbers.put("BENEFICIARY_SNO", beneficiary_sno);
				numbers.put("PMS_ASSET_SR_SNO", sr_sno);
				int ct=obj.getCount("PMS_SCH_ASSET_SR_LOC_BEN"," where BENEFICIARY_SNO=" + beneficiary_sno +" and SCH_SNO="+sch_no +" and PMS_ASSET_SR_SNO='" + sr_sno+ "'");
				int r=0;
				if (ct==0)  
				{  
					r= obj.recordSave(numbers, "PMS_SCH_ASSET_SR_LOC_BEN ",con);
					ct=obj.getCount("PMS_SCH_ASSET_SR_LOC_BEN"," where BENEFICIARY_SNO=" + beneficiary_sno +" and SCH_SNO="+sch_no +" and PMS_ASSET_SR_SNO='" + sr_sno+ "'");
					if (r > 0) 
					{
						res = "<response><msg>" + r + "</msg><ct>"+ct+"</ct></response>";
					}
				}
				else
				{
					ct=obj.getCount("PMS_SCH_ASSET_SR_LOC_BEN"," where BENEFICIARY_SNO=" + beneficiary_sno +" and SCH_SNO="+sch_no +" and PMS_ASSET_SR_SNO='" + sr_sno + "'");
					res = "<response><msg>" + r + "</msg><ct>"+ct+"</ct></response>";
				}
				obj.resposeWr(response, res);
				con.close();
		}
		catch (Exception e) {
			   System.out.println(e);
				}
	}else if (comment.equalsIgnoreCase("modify_ben")) {
				try {
					 	con = obj.con();
				obj.createStatement(con);
				String ben_id = "", ben_sno = "";
				String sno = obj.setValue("sno", request);
				ben_id = obj.setValue("ben_id", request);
				ben_sno = obj.setValue("ben_sno", request);
				Hashtable numbers = new Hashtable();
				numbers.put("BENEFICIARY_TYPE_ID", ben_id);
				numbers.put("BENEFICIARY_SNO", ben_sno);
				Hashtable ht = new Hashtable();
				ht.put("PMS_ASSET_SR_BEN_SNO", sno);
				int r = obj.recordSave(numbers, ht,
						"PMS_SCH_ASSET_SR_LOC_BEN ", con);
				res = "<response><msg>" + r + "</msg></response>";
				obj.resposeWr(response, res);
				con.close();
				}
				catch (Exception e) {
				}
	}
	
	else if (comment.equalsIgnoreCase("pop_ben")) {
		try {
			int loc_count = 0, ct = 0;
				con = obj.con();
				obj.createStatement(con);
				String no = obj.setValue("sno", request);
				String sr_sno = obj.setValue("sr_sno", request);
				res = "<response>";
				res += "<rows> "
						+ obj.delRecord("PMS_SCH_ASSET_SR_LOC_BEN",
								" where PMS_ASSET_SR_BEN_SNO=" + no, con, 1);
				res += "</rows>";
			    ct=obj.getCount("PMS_SCH_ASSET_SR_LOC_BEN"," where SCH_SNO="+sch_no +" and PMS_ASSET_SR_SNO='" + sr_sno + "'");
			    res += "<ct>"+ct+"</ct></response>";
				obj.resposeWr(response, res);
				loc_count = obj.getCount("PMS_SCH_ASSET_SR_LOC_BEN",
						" where PMS_ASSET_SR_SNO=" + sr_sno);
				con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	else if (comment.equalsIgnoreCase("combo_ben")) {
		try{
			con = obj.con();
				obj.createStatement(con);
				String sno = obj.setValue("combo_sno", request);
				String ben_name = obj.setValue("ben_name", request);
				res = obj
						.resultXML(
								"SELECT BENEFICIARY_NAME, BENEFICIARY_SNO FROM PMS_DCB_MST_BENEFICIARY WHERE BENEFICIARY_SNO IN(SELECT BENEFICIARY_SNO FROM pms_dcb_mst_beneficiary_metre WHERE SCHEME_SNO="
										+ sno
										+ " )AND BENEFICIARY_TYPE_ID="
										+ ben_name
										+ " ORDER BY BENEFICIARY_SNO", con, obj);
				obj.resposeWr(response, res);
				con.close();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	else if(comment.equalsIgnoreCase("view_ben"))
	{
			try {
				con = obj.con();
				obj.createStatement(con);
				String sr_sno = obj.setValue("sr_sno", request);
				String beneficiary_val= obj.setValue("beneficiary_val", request); 
				sch_no = obj.setValue("sch_sno", request);
				String cond="";
				if (beneficiary_val.equalsIgnoreCase("0"))
					cond=""; 
				else
					cond=" and BENEFICIARY_TYPE_ID="+beneficiary_val;
				  qry="SELECT a.*,"
					+ "s.BEN_TYPE_DESC,"
					+ "loc.LOCATION,"
					+ "b.BENEFICIARY_NAME,s.BEN_TYPE_SDESC,s.BEN_TYPE_ID"
					+ " FROM"
					+ "(SELECT * FROM PMS_SCH_ASSET_SR_LOC_BEN where SCH_SNO="
					+ sch_no
					+ " and PMS_ASSET_SR_SNO="
					+ sr_sno+" "+cond
					+ " )a"
					+ " JOIN"
					+ " (SELECT * FROM PMS_DCB_MST_BENEFICIARY"
					+ ")b"
					+ " ON a.BENEFICIARY_SNO=b.BENEFICIARY_SNO"
					+ " JOIN"
					+ " (SELECT * FROM PMS_DCB_BEN_TYPE"
					+ " )s"
					+ " ON a.BENEFICIARY_TYPE_ID=s.BEN_TYPE_ID"
					+ " JOIN"
					+ "(SELECT * FROM PMS_SCH_ASSET_SR"
					+ " )loc" + " ON "
					+ " a.PMS_ASSET_SR_SNO=loc.PMS_ASSET_SR_SNO";
				 int loc_count = obj.getCount("PMS_SCH_ASSET_SR_LOC_BEN"," where PMS_ASSET_SR_SNO=" +sr_sno+" "+cond);
					String  ben_combo ="";
				ben_combo="";
				res="<response>";
				res+= obj.resultXML(qry,con, obj,1);
				res+="<ct>"+loc_count+"</ct>";
				res+="</response>";
				obj.resposeWr(response, res);  
				con.close();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
