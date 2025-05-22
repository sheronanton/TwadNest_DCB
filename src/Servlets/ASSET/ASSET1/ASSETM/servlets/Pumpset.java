/* 
 * Created on : dd-mm-yy 
 * Author     : Joanofark.E
 * Last Date  : 
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description			BY	
 * 07/03/2012	New   
 * 23/11/2012	New Code to Report 	Panneer Selvam.k
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

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
 
public class Pumpset extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    public Pumpset() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String XML="";
		Controller obj = new Controller();
		PrintWriter pr=null;
		Connection con = null;  
		String comment = request.getParameter("comment");
		String qry;
		int ofid = 0, proid = 0, comp = 0, subcomp = 0,schno=0;
		String sch_no="", src_comp="",sub_src_comp="";
		try
		{
		   con=obj.con();
		   obj.createStatement(con);
	       sch_no=obj.setValue("sno", request);
	       src_comp=obj.setValue("comp",request);
	       sub_src_comp=obj.setValue("sub_comp",request);
		   schno=Integer.parseInt(sch_no);
		   comp=Integer.parseInt(src_comp);
		   subcomp=Integer.parseInt(sub_src_comp);
		   HttpSession session = request.getSession(false);
		 String userid = (String) session.getAttribute("UserId");
 
		if (userid == null) 
		{   
		  response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		
		System.out.println("ASSET->Pumpset->command->" + comment);  
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
		{
		 response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		 ofid = Integer.parseInt(Office_id);
		   if (comment.equalsIgnoreCase("PDF"))
		    {
		    	String sno = request.getParameter("sno");
	 			response.setContentType("application/pdf");
				Map p = new HashMap();
				p.put("sch_sno", sno); 
			 	p.put("off_id", Office_id); 
			 	String sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+sno+ "  ");
			 	String off_namne=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+ofid+ "  ");
			 	p.put("scheme_name", sch_name); 
			 	p.put("office_name", off_namne); 
			 	
			 	System.out.println(sch_name);
			 	System.out.println(off_namne);
			 	
				String path = getServletContext().getRealPath("/WEB-INF/PDF/Pumpset_Single_Report_new.jasper");
				response.setHeader("Content-Disposition","attachment; filename=\"Pumpset_Single_Report.pdf\"");
				OutputStream outp = response.getOutputStream();
				JasperPrint jf;				 			
	 		    jf=JasperFillManager.fillReport(path,p,con);
	 			JRPdfExporter jrf;
	 			jrf=new JRPdfExporter();
	 	        jrf.setParameter(JRExporterParameter.JASPER_PRINT, jf);
	 			jrf.setParameter(JRExporterParameter.OUTPUT_STREAM,outp);
	 			jrf.exportReport();
	 		 
	 			outp.close();
		    	
		     
			}
		   else
		   {
			   response.setContentType("text/xml");  
			   pr=response.getWriter();  
			   if (comment.equalsIgnoreCase("add")) 
			   {
			try {
				int pump_type = Integer.parseInt(obj.setValue("pump_type",request));					
				int duty_type = Integer.parseInt(obj.setValue("duty_type",request));					
				int head = Integer.parseInt(obj.setValue("head",request));
				int qty = Integer.parseInt(obj.setValue("qty",request));			
				String remark = obj.setValue("remark",request);
				int htype = Integer.parseInt(obj.setValue("htype",request));
			       
				int id = obj.getMax("PMS_SCH_ASSET_HW_PUMPSET","PMS_ASSET_HW_PS_SNO", "");
						
				 System.out.println("id"+id);
			
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				qry = "insert into PMS_SCH_ASSET_HW_PUMPSET (PMS_ASSET_HW_PS_SNO,OFFICE_ID,PROJECT_ID,SCH_SNO,COMP_SNO,SUBCOMP_SNO,TYPE_OF_PUMPSET,DUTY,HEAD,QUANTITY,REMARKS,UPDATED_BY_USER_ID,UPDATED_TIME,HORSE_POWER)"
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(qry);
				ps.setInt(1, id);
				ps.setInt(2, ofid);
				ps.setInt(3, proid);
				ps.setInt(4, schno);
				ps.setInt(5, comp);
				ps.setInt(6, subcomp);
				ps.setInt(7,pump_type);
				ps.setInt(8, duty_type);
				ps.setInt(9, head);
				ps.setInt(10, qty);
				ps.setString(11, remark);
				ps.setString(12, userid);
				ps.setTimestamp(13, sqlTimestamp);
				ps.setInt(14, htype);
				int res = ps.executeUpdate();
				XML = "<response>";
				XML += "<row>"+res+"</row>";
				XML += "</response>";
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		} 
		 else if (comment.equalsIgnoreCase("view"))
			{
				try {
					  XML = "<response>";
					
					
					qry = "select * from PMS_SCH_ASSET_HW_PUMPSET ";
					PreparedStatement ps = con.prepareStatement(qry);
					ResultSet rs = ps.executeQuery();
					
					while (rs.next()) 
					{
						
						String type=rs.getString("TYPE_OF_PUMPSET");
						String val=obj.getValue("PMS_SCH_ASSET_PUMPSET_TYPE","TYPE_DESC", "where PUMPSET_TYPE_ID="+type);
						XML += "<PMS_ASSET_HW_PS_SNO>" +rs.getString("PMS_ASSET_HW_PS_SNO");
						XML += "</PMS_ASSET_HW_PS_SNO>";
						XML += "<OFFICE_ID>" + rs.getString("OFFICE_ID");
						XML += "</OFFICE_ID>";
						XML += "<PROJECT_ID>" + rs.getString("PROJECT_ID");
						XML += "</PROJECT_ID>";
						XML += "<SCH_SNO>" + rs.getString("SCH_SNO");
						XML += "</SCH_SNO>";
						XML += "<COMP_ID>" + rs.getString("COMP_SNO"); 
						XML += "</COMP_ID>";
						XML += "<SUBCOMP_ID>" + rs.getString("SUBCOMP_SNO");
						XML += "</SUBCOMP_ID>";
						XML += "<TYPE_OF_PUMPSET>"+val+"</TYPE_OF_PUMPSET>";     
						XML += "<DUTY>" + rs.getString("DUTY");
						XML += "</DUTY>";
						XML += "<HEAD>" + rs.getString("HEAD");
						XML += "</HEAD>";
						XML += "<QUANTITY>" + rs.getString("QUANTITY");     
						XML += "</QUANTITY>";
						XML += "<REMARKS>" + rs.getString("REMARKS");
						XML += "</REMARKS>";
						XML += "<UPDATED_BY_USER_ID>"
								+ rs.getString("UPDATED_BY_USER_ID");
						XML += "</UPDATED_BY_USER_ID>";
						XML += "<UPDATED_TIME>" + rs.getString("UPDATED_TIME");
						XML += "</UPDATED_TIME>";
						XML += "<HORSE_POWER>" + rs.getString("HORSE_POWER");
						XML += "</HORSE_POWER>";
					}

					XML += "</response>";
					 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			
			}
		 else if (comment.equalsIgnoreCase("modify")) {
				try {

					int pump_type = Integer.parseInt(obj.setValue("pump_type",request));					
					int duty_type = Integer.parseInt(obj.setValue("duty_type",request));					
					int head = Integer.parseInt(obj.setValue("head",request));
					int qty = Integer.parseInt(obj.setValue("qty",request));			
					String remark = obj.setValue("remark",request);
					int htype = Integer.parseInt(obj.setValue("htype",request));
					int sno = Integer.parseInt(request.getParameter("sno"));
					qry = "update  PMS_SCH_ASSET_HW_PUMPSET set TYPE_OF_PUMPSET=?,DUTY=?,HEAD=?,QUANTITY=?,HORSE_POWER=?,REMARKS=? where  PMS_ASSET_HW_PS_SNO=? ";
					PreparedStatement ps = con.prepareStatement(qry);
					ps.setInt(1,pump_type);
					ps.setInt(2, duty_type);
					ps.setInt(3, head);
					ps.setInt(4, qty);
					ps.setInt(5, htype);
					ps.setString(6, remark);					
					ps.setInt(7, sno);
					int res = ps.executeUpdate();
					XML = "<response>";
					XML += "<row>" + res;
					XML += "</row>";
					XML += "</response>";
					 
				} catch (Exception e)
				{
					e.printStackTrace();
				}
		    }else if (comment.equalsIgnoreCase("pop")) {
				try {
					String sno = request.getParameter("sno");
					System.out.println(sno+"sno");
					qry = "delete from PMS_SCH_ASSET_HW_PUMPSET where PMS_ASSET_HW_PS_SNO=?";
					PreparedStatement ps = con.prepareStatement(qry);
					ps.setString(1, sno);
					int res = ps.executeUpdate();
					 XML = "<response>";
					XML += "<row>" + res;
					XML += "</row>";
					XML += "</response>";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			}
				 System.out.println(XML);
				pr.write(XML);
		   }	 
				 
				con.close();
			}catch (Exception e) {
				
				System.out.println("Error" + e);  
			}	
	}
 
 
}
