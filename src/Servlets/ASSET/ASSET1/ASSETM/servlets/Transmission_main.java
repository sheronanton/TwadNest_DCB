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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import java.util.Hashtable;

/**
 * Servlet implementation class Booster_station
 */
public class Transmission_main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Transmission_main() {
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
		Connection con = null;
		String comment = request.getParameter("comment");
		System.out.println("ASSET->Transmission Main ->command->" + comment);
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
		 if (comment.equalsIgnoreCase("PDF"))
		 {
		 try 
			{System.out.println("welcome pdf");
				 con = obj.con();
				obj.createStatement(con);
				response.setContentType("application/pdf");
				String path = getServletContext().getRealPath("/WEB-INF/PDF/trans_report.jasper");
				response.setHeader("Content-Disposition","attachment;filename=\"trans_report.pdf\"");
				OutputStream outp = response.getOutputStream();
				Map p=new HashMap();
				p.put("sch_no", sch_no);System.out.println(sch_no+"nnn");
				p.put("off_id", Office_id);
				JasperPrint jf;
				jf = JasperFillManager.fillReport(path, p, con);
				JRPdfExporter jrf;
				jrf = new JRPdfExporter();
				jrf.setParameter(JRExporterParameter.JASPER_PRINT, jf);
				jrf.setParameter(JRExporterParameter.OUTPUT_STREAM, outp);
				jrf.exportReport();
				outp.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} 
	else {
			PrintWriter pr = response.getWriter();
		if (comment.equalsIgnoreCase("add")) {
			try {

				con = obj.con();
				obj.createStatement(con);
				int id = obj.getMax("PMS_SCH_ASSET_TMAIN",
						"PMS_ASSET_TMAIN_SNO", "");

				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);

				Hashtable numbers = new Hashtable();
				numbers.put("PMS_ASSET_TMAIN_SNO", id);
				numbers.put("OFFICE_ID", ofid);
				numbers.put("PROJECT_ID", proid);
				numbers.put("SCH_SNO", schno);
				numbers.put("COMP_SNO", comp);
				numbers.put("SUBCOMP_SNO", subcomp);
				numbers.put("TYPE_OF_PIPE", obj.setValue("pipe_type",request));
				numbers.put("SPECIFICATION", "'"+obj.setValue("specification", request)+"'");
				numbers.put("LOCATION", "'"+obj.setValue("location", request)+"'");
				numbers.put("REACH_NO", obj.setValue("no_reach", request));
				numbers.put("LENGTH", obj.setValue("length", request));
				numbers.put("UPDATED_BY_USER_ID", "'" + userid + "'");
				numbers.put("UPDATED_TIME", "clock_timestamp()");
				int r = obj.recordSave(numbers,
						"PMS_SCH_ASSET_TMAIN ", con);
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
			
				res = obj.resultXML(" SELECT a.*,  b.type_desc as type_desc  FROM PMS_SCH_ASSET_TMAIN   a,   PMS_SCH_ASSET_PIPE_TYPE   b WHERE a.TYPE_OF_PIPE = b.PIPE_TYPE_ID  ", con,obj);
						
				obj.resposeWr(response, res);
			} catch (Exception e) {
				System.out.println(e);
			}

		} else if (comment.equalsIgnoreCase("pop")) {
			try {
				con = obj.con();
				obj.createStatement(con);
				res = obj.delRecord("PMS_SCH_ASSET_TMAIN",
						" where PMS_ASSET_TMAIN_SNO=" + schno, con);
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
				numbers.put("TYPE_OF_PIPE", obj.setValue("pipe_type",request));
				numbers.put("SPECIFICATION", "'"+obj.setValue("specification", request)+"'");
				numbers.put("LOCATION", "'"+obj.setValue("location", request)+"'");
				//numbers.put("REACH_NO", obj.setValue("no_reach", request));
				numbers.put("LENGTH", obj.setValue("length", request));
				numbers.put("UPDATED_BY_USER_ID", "'" + userid + "'");
				numbers.put("UPDATED_TIME", "clock_timestamp()");
				ht.put("PMS_ASSET_TMAIN_SNO", schno);
				int r = obj.recordSave(numbers, ht,"PMS_SCH_ASSET_TMAIN ", con);
				res = "<response><rows>" + r + "</rows></response>";
				obj.resposeWr(response, res);

			} catch (Exception e) {
				 
			}
		}

	}
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
