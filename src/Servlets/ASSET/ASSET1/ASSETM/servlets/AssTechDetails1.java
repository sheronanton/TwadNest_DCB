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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class AssTechDetails1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	public AssTechDetails1() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Map p = new HashMap();
		Controller obj = new Controller();
		//PrintWriter pr = response.getWriter();
		Connection con = null;
		try {
			con = obj.con();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		try {
			obj.createStatement(con);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		System.out.println(con);
		String comment = request.getParameter("comment");
		String qry;
		int ofid = 0, proid = 0, comp = 0, subcomp = 0 ;
		String sch_no = "", src_comp = "", sub_src_comp = "",Office_id="";
		String project_id="";
		try {
			con = obj.con();
			obj.createStatement(con);

			sch_no = obj.setValue("sno", request);
			src_comp = obj.setValue("comp", request);
			sub_src_comp = obj.setValue("sub_comp", request);
		} catch (Exception e) {
		}
		comp = Integer.parseInt(src_comp);
		subcomp = Integer.parseInt(sub_src_comp);
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");

		if (userid == null) {
				response.sendRedirect(request.getContextPath() +"/index.jsp");
		}
		try {
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid +"')");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch_no);
			proid=Integer.parseInt(project_id);
		} catch (Exception e2) 
		{
			e2.printStackTrace();
		}
		if (Office_id == null){
			//Office_id = "5982";
			response.sendRedirect(request.getContextPath() +"/index.jsp");}

		ofid = Integer.parseInt(Office_id);
		
		System.out.println("ASSET->AssTechDetails1->command->" +comment);
		
		if (comment.equalsIgnoreCase("PDF")) 
		{
			try 
			{
				 con = obj.con();
				obj.createStatement(con);
				response.setContentType("application/pdf");
				String path = getServletContext().getRealPath("/WEB-INF/PDF/head_source_1.jasper");
				response.setHeader("Content-Disposition","attachment;filename=\"head_source_1.pdf\"");
				OutputStream outp = response.getOutputStream();
				p.put("sch_sno", sch_no);
				p.put("office_id", ofid);
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
		} else {
			PrintWriter pr = response.getWriter();
		if (comment.equalsIgnoreCase("add")) {
			try {
				int type_src = Integer.parseInt(obj.setValue("type_src",request));
				String diam =  obj.setValue("diam", request);
				String depth =  obj.setValue("depth", request);
				String hou =  obj.setValue("hou", request);
				 String length =  obj.setValue("length", request) ;
				 String redial = obj.setValue("redial", request) ;
				 String qty =  obj.setValue("qty", request);
				String location = obj.setValue("location", request);
				String remark = obj.setValue("remark", request);
				int id = obj.getMax("PMS_SCH_ASSET_HW_SOURCE","PMS_ASSET_HW_SNO", "");
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				qry = "insert into PMS_SCH_ASSET_HW_SOURCE (PMS_ASSET_HW_SNO,OFFICE_ID,PROJECT_ID,SCH_SNO,COMP_SNO,SUBCOMP_SNO,TYPE_OF_SOURCE,DIAMETER,DEPTH_WELL,DIA_PUMPHOUSE,LENGTH_PUMPHOUSE,DIA_RADIAL,DRAW_QTY,SOURCE_LOCATION,REMARKS,UPDATED_BY_USER_ID,UPDATED_TIME) "
						+"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				System.out.println(qry);
				PreparedStatement ps = con.prepareStatement(qry);
				ps.setInt(1, id);
				ps.setInt(2, ofid);
				ps.setInt(3, proid);
				ps.setString(4, sch_no);
				ps.setInt(5, comp);
				ps.setInt(6, subcomp);
				ps.setInt(7, type_src);
				ps.setString(8, diam);
				ps.setString(9, depth);
				ps.setString(10, hou);  
				ps.setString(11, length);
				ps. setString(12, redial);
				ps.setString(13, qty);
				ps.setString(14, location);
				ps.setString(15, remark);
				ps.setString(16, userid);
				ps.setTimestamp(17, sqlTimestamp);
				int res = ps.executeUpdate();
				String XML = "<response>";  
				XML+="<row>"+res+"</row>";
				XML += "</response>";
				pr.write(XML);
				pr.flush();
				pr.close();
				con.close();
			} catch (Exception e) {
				System.out.println(" Error " +e);
			}
		} else if (comment.equalsIgnoreCase("view")) {
			try {
				qry = "select * from PMS_SCH_ASSET_HW_SOURCE ";
				PreparedStatement ps = con.prepareStatement(qry);
				ResultSet rs = ps.executeQuery();
				String XML = "<response>";
				while (rs.next()) {
					System.out.println("ss" +comment);
					XML += "<PMS_ASSET_HW_SNO>"+rs.getString("PMS_ASSET_HW_SNO")+"</PMS_ASSET_HW_SNO>";
					XML += "<OFFICE_ID>" +rs.getString("OFFICE_ID")+"</OFFICE_ID>";
					XML += "<PROJECT_ID>" +rs.getString("PROJECT_ID")+"</PROJECT_ID>";
					XML += "<SCH_SNO>" +rs.getString("SCH_SNO")+"</SCH_SNO>";
					XML += "<COMP_ID>" +rs.getString("COMP_SNO")+"</COMP_ID>";
					XML += "<SUBCOMP_ID>" +rs.getString("SUBCOMP_SNO")+"</SUBCOMP_ID>";
					String type=rs.getString("TYPE_OF_SOURCE");
					String val=obj.getValue("PMS_SCH_ASSET_SOURCE_TYPE","TYPE_DESC", "where SOURCE_TYPE_ID="+type);
					XML += "<TYPE_OF_SOURCE>" +val+"</TYPE_OF_SOURCE>";
					XML += "<type>" +type+"</type>";  
					XML += "<DIAMETER>" +rs.getString("DIAMETER")+"</DIAMETER>";
					XML += "<DEPTH_WELL>" +rs.getString("DEPTH_WELL")+"</DEPTH_WELL>";
					XML += "<DIA_PUMPHOUSE>" +rs.getString("DIA_PUMPHOUSE")+"</DIA_PUMPHOUSE>";
					XML += "<LENGTH_PUMPHOUSE>"+rs.getString("LENGTH_PUMPHOUSE")+"</LENGTH_PUMPHOUSE>";
					XML += "<DIA_RADIAL>" +rs.getString("DIA_RADIAL")+"</DIA_RADIAL>";
					XML += "<DRAW_QTY>" +rs.getString("DRAW_QTY")+"</DRAW_QTY>";
					XML += "<SOURCE_LOCATION>"+rs.getString("SOURCE_LOCATION")+"</SOURCE_LOCATION>";
					XML += "<REMARKS>" +rs.getString("REMARKS")+"</REMARKS>";
					XML += "<UPDATED_BY_USER_ID>"+rs.getString("UPDATED_BY_USER_ID")+"</UPDATED_BY_USER_ID>";
					XML += "<UPDATED_TIME>" +rs.getString("UPDATED_TIME")+"</UPDATED_TIME>";
  
				}
				  
				XML += "</response>";
				 
				pr.write(XML);
				pr.flush();
				pr.close();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (comment.equalsIgnoreCase("modify")) {
			try {

				// int sid = Integer.parseInt(obj.setValue("sid", request));
				int type_src = Integer.parseInt(obj.setValue("type_src",
						request));
				float diam = Float.parseFloat(obj.setValue("diam", request));
				int depth = Integer.parseInt(obj.setValue("depth", request));
				float hou = Float.parseFloat(obj.setValue("hou", request));
				int length = Integer.parseInt(obj.setValue("length", request));
				int redial = Integer.parseInt(obj.setValue("redial", request));
				float qty = Float.parseFloat(obj.setValue("qty", request));
				String location = obj.setValue("location",request);
						
				String remark = obj.setValue("remark", request);
				con = obj.con();
				obj.createStatement(con);
				int sno = Integer.parseInt(request.getParameter("sno"));

				qry = "update PMS_SCH_ASSET_HW_SOURCE set TYPE_OF_SOURCE=?,DIAMETER=?,DEPTH_WELL=?,DIA_PUMPHOUSE=?,LENGTH_PUMPHOUSE=?,DIA_RADIAL=?,DRAW_QTY=?,SOURCE_LOCATION=?,REMARKS=? where  PMS_ASSET_HW_SNO=? ";

				PreparedStatement ps = con.prepareStatement(qry);
				ps.setInt(1, type_src);
				ps.setFloat(2, diam);
				ps.setInt(3, depth);
				ps.setFloat(4, hou);
				ps.setInt(5, length);
				ps.setInt(6, redial);
				ps.setFloat(7, qty);
				ps.setString(8, location);
				ps.setString(9, remark);
				ps.setInt(10, sno);
				int res = ps.executeUpdate();
				String xml = "<response>";
				xml += "<row>" +res;
				xml += "</row>";
				xml += "</response>";
				pr.write(xml);
				pr.flush();
				pr.close();
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (comment.equalsIgnoreCase("pop")) {
			try {
				con = obj.con();
				obj.createStatement(con);
				String sno = request.getParameter("sno");
				System.out.println(sno +"sno");
				qry = "delete from PMS_SCH_ASSET_HW_SOURCE where PMS_ASSET_HW_SNO=?";
				PreparedStatement ps = con.prepareStatement(qry);
				ps.setString(1, sno);
				int res = ps.executeUpdate();
				String XML = "<response>";
				XML += "<row>" +res;
				XML += "</row>";
				XML += "</response>";
				pr.write(XML);
				pr.flush();
				pr.close();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
