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
 * Servlet implementation class Booster_Main
 */
public class Booster_Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Booster_Main() {
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
		Connection con = null;
		String comment = request.getParameter("comment");
		
		String qry;
		String res="";
		int ofid = 0, proid = 0, comp = 0, subcomp = 0,schno=0;
		String sch_no="", src_comp="",sub_src_comp="";
			try{
				con=obj.con();
			 
				obj.createStatement(con);
			
		
	       sch_no=obj.setValue("sno", request);
	       src_comp=obj.setValue("comp",request);
	     	sub_src_comp=obj.setValue("sub_comp",request);
	   	
			}catch (Exception e) {
			}
		schno=Integer.parseInt(sch_no);
		comp=Integer.parseInt(src_comp);
		subcomp=Integer.parseInt(sub_src_comp);
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null) 
		{   
			 
			 response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		
		System.out.println("ASSET->Booster_Main->command->" + comment);  
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
		if(Office_id == null)
		// Office_id = "0";
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		 ofid = Integer.parseInt(Office_id);
		 if (comment.equalsIgnoreCase("PDF")) 
			{
				try 
				{
					 con = obj.con();
					obj.createStatement(con);
					response.setContentType("application/pdf");
					String path = getServletContext().getRealPath("/WEB-INF/PDF/boos_main_report.jasper");
					response.setHeader("Content-Disposition","attachment;filename=\"boos_main_report.pdf\"");
					OutputStream outp = response.getOutputStream();
					Map p=new HashMap();
					p.put("sch_no", sch_no);System.out.println("sch_no"+sch_no);
					p.put("off_id", Office_id);System.out.println("Office_id"+Office_id);

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
				
			       con = obj.con();
				obj.createStatement(con);
				   int id = obj.getMax("PMS_SCH_ASSET_BMAIN","PMS_ASSET_BMAIN_SNO", "");
				  	
			
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				 Hashtable numbers = new Hashtable();                            
			     numbers.put("PMS_ASSET_BMAIN_SNO",id);
			     numbers.put("OFFICE_ID", ofid);
			     numbers.put("PROJECT_ID", proid);
			     numbers.put("SCH_SNO", schno);
			     numbers.put("COMP_SNO", comp); 
			     numbers.put("SUBCOMP_SNO", subcomp);
			      
			     numbers.put("TYPE_OF_PMAIN", obj.setValue("main_type", request) );			     
			     numbers.put("CLASS_OF_MAIN", obj.setValue("class_type", request) );
			     
			     
			     
			     
			     numbers.put("DIAMETER", obj.setValue("dim", request));
			     numbers.put("LENGTH_MAIN", obj.setValue("len_main", request) );
			     numbers.put("LOCATION_FROM","'"+ obj.setValue("from", request)+"'");	
			     numbers.put("LOCATION_TO", "'"+obj.setValue("to", request)+"'");	
			     numbers.put("UPDATED_BY_USER_ID", "'"+userid+"'");
			     numbers.put("UPDATED_TIME", "clock_timestamp()");  
			     numbers.put("BMAIN_NO",obj.setValue("boo_main", request));
			     int r=obj.recordSave(numbers, "PMS_SCH_ASSET_BMAIN ", con);
			     if (r > 0 )
			     {
			    	 res="<response><rows>"+r+"</rows></response>";
			     }	
			     obj.resposeWr(response, res) ;
			}
			
			
		catch (Exception e) {
		}
	}
		else if(comment.equalsIgnoreCase("view"))
		{
			try{
				con = obj.con();
				obj.createStatement(con);
				res="<response>"; 
				 qry=" SELECT bmain.*, "+  
					" ctype.type_desc as ctype,mtype.TYPE_DESC as mtype "+
				  " FROM "+  
				" (SELECT * "+
						  " FROM pms_sch_asset_bmain) "+
				" bmain JOIN "+
				" (SELECT * "+
						  " FROM pms_sch_asset_class_type) "+
				" ctype ON ctype.class_type_id = bmain.CLASS_OF_MAIN "+
				" JOIN  "+
				" (SELECT * "+
						  "    FROM PMS_SCH_ASSET_PMAIN_TYPE) "+
				" mtype ON mtype.PMAIN_TYPE_ID = bmain.TYPE_OF_PMAIN ";


				
				res+=obj.resultXML(qry, con, obj,1);  
				
				res+="</response>";
				obj.resposeWr(response, res) ;
			}catch (Exception e) {
				System.out.println(e);
			}
			
		}
		else if (comment.equalsIgnoreCase("pop")) {
			try {
				 con = obj.con();
				obj.createStatement(con);
				res=obj.delRecord("PMS_SCH_ASSET_BMAIN", " where PMS_ASSET_BMAIN_SNO="+schno,con);
				 obj.resposeWr(response, res) ;

			} catch (Exception e) {
			}
		}
		else if (comment.equalsIgnoreCase("modify")) {
			try {
				 con = obj.con();
				 obj.createStatement(con); 
				 Hashtable numbers = new Hashtable();
				 Hashtable ht = new Hashtable();  
				 numbers.put("TYPE_OF_PMAIN", obj.setValue("main_type", request) );			     
			     numbers.put("CLASS_OF_MAIN", obj.setValue("class_type", request) );
			     numbers.put("DIAMETER", obj.setValue("dim", request));
			     numbers.put("LENGTH_MAIN", obj.setValue("len_main", request) );
			     numbers.put("LOCATION_FROM","'"+ obj.setValue("from", request)+"'");	
			     numbers.put("LOCATION_TO", "'"+obj.setValue("to", request)+"'");	
			     numbers.put("UPDATED_BY_USER_ID", "'"+userid+"'");
			     numbers.put("UPDATED_TIME", "clock_timestamp()");  
			     ht.put("PMS_ASSET_BMAIN_SNO",schno);
			     int r=obj.recordSave(numbers,ht,"PMS_SCH_ASSET_BMAIN ", con);
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
