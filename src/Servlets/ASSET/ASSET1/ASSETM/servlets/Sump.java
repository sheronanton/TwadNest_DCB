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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import java.util.Hashtable;

/**
 * Servlet implementation class Sump
 */
public class Sump extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sump() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Controller obj = new Controller();
		//PrintWriter pr=response.getWriter();
		Map p=new HashMap();
		Connection con = null;
	 
		
		
		String qry;
		String res="";
		int ofid = 0, proid = 0, comp = 0, subcomp = 0,schno=0;
		String sch_no="", src_comp="",sub_src_comp="";
			try{
				con=obj.con();
				System.out.println(con);
				obj.createStatement(con);
	       sch_no=obj.setValue("sno", request);
	       src_comp=obj.setValue("comp",request);
	       sub_src_comp=obj.setValue("sub_comp",request);
			}catch (Exception e) {
			}
		String comment = obj.setValue("comment",request); 
		schno=Integer.parseInt(sch_no);
		comp=Integer.parseInt(src_comp);
		subcomp=Integer.parseInt(sub_src_comp);
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null) 
		{  //userid="twad10950"; 
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		System.out.println("ASSET->Sump->command->" + comment);  
		String Office_id = "";
		String project_id="0";
		try {
			project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+schno);
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
		if(Office_id == null)
		Office_id = "0";
		 ofid = Integer.parseInt(Office_id);
 
			if (comment.equalsIgnoreCase("PDF")) 
			{
				try 
				{
					 con = obj.con();
					obj.createStatement(con);
					 //String path = getServletContext().getRealPath("/WEB-INF/PDF/BOOSTER_station1.jasper");
				    // String ctxpath = path.substring(0, path.lastIndexOf("BOOSTER_station1.jasper"));

					String path = getServletContext().getRealPath("/WEB-INF/PDF/Sump.jasper");
				    String ctxpath = path.substring(0, path.lastIndexOf("Sump.jasper"));
					p.put("sch_sno", Integer.toString(schno));
					p.put("off_id", Integer.toString(ofid));
				   
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, p, con);
					OutputStream outuputStream1 = response.getOutputStream();
					JRExporter exporter = null;
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"Sump.pdf\"");
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream1);
					exporter.exportReport();					
					outuputStream1.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
			else {
		PrintWriter pr=response.getWriter();
		if (comment.equalsIgnoreCase("add")) {
			try {
				
			       con = obj.con();
				obj.createStatement(con);
				   int id = obj.getMax("PMS_SCH_ASSET_BOOSTER_SUMP","PMS_ASSET_BOOSTER_SUMP_SNO", "");
				    String loc = obj.setValue("loc",request);				
				    float capacity = Float.parseFloat(obj.setValue("capacity",request));					
				    float dim = Float.parseFloat(obj.setValue("dim",request));
				    float depth = Float.parseFloat(obj.setValue("depth",request));							
					int detention = Integer.parseInt(obj.setValue("detention",request));	
			
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				 Hashtable numbers = new Hashtable();
			     numbers.put("PMS_ASSET_BOOSTER_SUMP_SNO",id);
			     numbers.put("OFFICE_ID", ofid);
			     numbers.put("PROJECT_ID", proid);
			     numbers.put("SCH_SNO", schno);
			     numbers.put("COMP_SNO", comp);
			     numbers.put("SUBCOMP_SNO", subcomp);
			     numbers.put("LOCATION_FROM", "'"+obj.setValue("loc", request)+"'" );
			     numbers.put("CAPACITY", obj.setValue("capacity", request));
			     numbers.put("DIAMETER", obj.setValue("dim", request) );
			     numbers.put("DEPTH", obj.setValue("depth", request));	
			     numbers.put("DETENTION", obj.setValue("detention", request));	
			     numbers.put("UPDATED_BY_USER_ID", "'"+userid+"'");
			     numbers.put("UPDATED_TIME", "clock_timestamp()");     
			     int r=obj.recordSave(numbers, "PMS_SCH_ASSET_BOOSTER_SUMP ", con);
			     if (r > 0 )
			     {
			    	 res="<response><rows>"+r+"</rows></response>";
			     }	
			     obj.resposeWr(response, res) ;
			}
		catch (Exception e) {
       System.out.println(e);
		}
		}
			else if(comment.equalsIgnoreCase("view"))
			{
				try{
					con = obj.con();
					obj.createStatement(con);
					
				  
					res=obj.resultXML(" SELECT * from PMS_SCH_ASSET_BOOSTER_SUMP ", con, obj);
					
				
					 obj.resposeWr(response, res) ;
				}catch (Exception e) {
					System.out.println(e);
				}
				
			}
				
			else if (comment.equalsIgnoreCase("pop")) {
				try {
					 con = obj.con();
					obj.createStatement(con);
					res=obj.delRecord("PMS_SCH_ASSET_BOOSTER_SUMP", " where PMS_ASSET_BOOSTER_SUMP_SNO="+schno,con);
					 obj.resposeWr(response, res) ;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			}
		else if (comment.equalsIgnoreCase("modify")) {
				try {
					 con = obj.con();
					 obj.createStatement(con); 
					 Hashtable numbers = new Hashtable();
					 Hashtable ht = new Hashtable();
				     numbers.put("LOCATION_FROM", "'"+obj.setValue("loc", request)+"'" );
				     numbers.put("CAPACITY", obj.setValue("capacity", request));
				     numbers.put("DIAMETER", obj.setValue("dim", request) );
				     numbers.put("DEPTH", obj.setValue("depth", request));	
				     numbers.put("DETENTION", obj.setValue("detention", request));	
				     ht.put("PMS_ASSET_BOOSTER_SUMP_SNO",schno);
				     int r=obj.recordSave(numbers,ht,"PMS_SCH_ASSET_BOOSTER_SUMP ", con);
				     res="<response><rows>"+r+"</rows></response>";	
				     obj.resposeWr(response, res) ;
				  
				}
				catch (Exception e) {
				 
				}
	}
		 }		
	}			
		           
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
