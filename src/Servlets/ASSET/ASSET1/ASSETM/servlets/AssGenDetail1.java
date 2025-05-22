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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
public class AssGenDetail1 extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public AssGenDetail1() {  super();  }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/xml");
		Controller obj = new Controller();
		Connection con=null;
		String comment = request.getParameter("comment");
		Map p = new HashMap();
		String qry,schno="";  int ofid = 0, proid = 0, chk_count=0;
		String project_id="0",sanno="";
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null)   
		{
		 response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String Office_id ="";
		try {
			con=obj.con();
			obj.createStatement(con);
			 schno = obj.setValue("sno",request);
			 sanno = obj.setValue("sanno",request);
			 chk_count=obj.getCount("PMS_SCH_ASSET_GENERAL"," where SCH_SNO="+schno);
			 Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')");
			 project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+schno);
				
		} catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		ofid = Integer.parseInt(Office_id);
		proid=Integer.parseInt(project_id);
		System.out.println("ASSET->AssGenDetails1->command->" + comment);
		if (comment.equalsIgnoreCase("PDF")) 
		{
			try 
			{
				 con = obj.con();
				obj.createStatement(con);
				response.setContentType("application/pdf");
				String path = getServletContext().getRealPath("/WEB-INF/PDF/GEneral _det_report.jasper");
				response.setHeader("Content-Disposition","attachment;filename=\"GEneral _det_report.pdf\"");
				OutputStream outp = response.getOutputStream();
				p.put("sch_no", schno);
				System.out.println(schno);
				p.put("off_id", Office_id);	System.out.println(Office_id);
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
			
		    if (comment.equalsIgnoreCase("add")) 
		    {
		    	if(chk_count==0)
				{
		    try
		      {
					    String year = obj.setValue("year", request);
					    String prepop = obj.setValue("prepop",request);
					    String prepopyr = obj.setValue("prepopyr",request);
					    String intpop = obj.setValue("intpop",request);	
					    String intpopyr = obj.setValue("intpopyr",request);
					    String ultpop = obj.setValue("ultpop",request);
					    String ultpopyr = obj.setValue("ultpopyr",request);
					    String hrs = obj.setValue("hrs",request);
					    String tp = obj.setValue("tp",request);
					    String hab = obj.setValue("hab",request);
					    String actsup = obj.setValue("actsup",request);
					    String intdly = obj.setValue("intdly",request);
					    String ultdly = obj.setValue("ultdly",request);
					    String prerte =  obj.setValue("prerte",request);
					    String intrte =  obj.setValue("intrte",request);
					    String ultrte =  obj.setValue("ultrte",request);
					    String corp=obj.setValue("corp",request);
					    String muni=obj.setValue("muni",request);
						String remark = obj.setValue("remark",request);
						String hr_inter = obj.setValue("int_hrs",request);
						String hr_ultimate = obj.setValue("ult_hrs",request);
							int id = obj.getMax("PMS_SCH_ASSET_GENERAL","PMS_ASSET_GEN_SNO", "");
							java.util.Date date = new java.util.Date();
							long t = date.getTime();
							java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
							qry = "insert into PMS_SCH_ASSET_GENERAL (PMS_ASSET_GEN_SNO,OFFICE_ID,PROJECT_ID,SCH_SNO,ADMIN_SANCTION_SNO,YEAR_COMMISION,POP_PRESENT,POP_INTER,POP_ULTIMATE,SUPPLY_TP,SUPPLY_HAB,DAILY_REQ_INTER,"
								+"DAILY_REQ_ULTIMATE,RATE_PUMP_INTER,RATE_PUMP_ULTIMATE,REMARKS,UPDATED_BY_USER_ID,UPDATED_TIME,DAILY_REQ_PRESENT,PRESENT_YR,INTER_YR,ULTIMATE_YR,HRS_PUMPING,RATE_PUMP_PRESENT,SUPPLY_CORP,SUPPLY_MUN,HRS_PUMPING_INTER,HRS_PUMPING_ULTIMATE) values "
								+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps = con.prepareStatement(qry);
							ps.setInt(1, id);
							ps.setInt(2, ofid);
							ps.setInt(3, proid);  
							ps.setString(4, schno);  
							ps.setString(5, sanno); 
							ps.setString(6, year);
							ps.setString(7, prepop);
							ps.setString(8, intpop);
							ps.setString(9, ultpop);
							ps.setString(10, tp);
							ps.setString(11, hab);
							ps.setString(12, intdly);
							ps.setString(13, ultdly);
							ps.setString(14, intrte);
							ps.setString(15, ultrte);
							ps.setString(16, remark);
							ps.setString(17, userid);
							ps.setTimestamp(18, sqlTimestamp);
							ps.setString(19, actsup);
							ps.setString(20, prepopyr);
							ps.setString(21, intpopyr);
							ps.setString(22, ultpopyr);
							ps.setString(23, hrs);
							ps.setString(24, prerte);
							ps.setString(25,corp);
							ps.setString(26, muni);
							ps.setString(27,hr_inter);
							ps.setString(28, hr_ultimate);
					int res = ps.executeUpdate();
					String XML = "<response>";
					XML += "<row>" + res;   
					XML += "</row>";
					XML += "</response>";
					pr.write(XML);
					pr.flush();
					pr.close();
					con.close();
		    } 
			catch (Exception e) 
			    {
					System.out.println(e);
			    }
			}
		   else{ 
	    	}
		    }
			else if (comment.equalsIgnoreCase("update")) {
				try {
					int year = Integer.parseInt(obj.setValue("year", request));
					int prepop = Integer.parseInt(obj.setValue("prepop",request));
					int prepopyr = Integer.parseInt(obj.setValue("prepopyr",request));
					int intpop = Integer.parseInt(obj.setValue("intpop",request));	
					int intpopyr = Integer.parseInt(obj.setValue("intpopyr",request));
					int ultpop = Integer.parseInt(obj.setValue("ultpop",request));
					int ultpopyr = Integer.parseInt(obj.setValue("ultpopyr",request));
					int hrs = Integer.parseInt(obj.setValue("hrs",request));
					int tp = Integer.parseInt(obj.setValue("tp",request));
					int hab = Integer.parseInt(obj.setValue("hab",request));
					int actsup = Integer.parseInt(obj.setValue("actsup",request));
					int intdly = Integer.parseInt(obj.setValue("intdly",request));
					int ultdly = Integer.parseInt(obj.setValue("ultdly",request));
					float prerte = Float.parseFloat(obj.setValue("prerte",request));
					float intrte = Float.parseFloat(obj.setValue("intrte",request));
					float ultrte = Float.parseFloat(obj.setValue("ultrte",request));
					int corp=Integer.parseInt(obj.setValue("corp",request));
					int muni=Integer.parseInt(obj.setValue("muni",request));
					String remark = obj.setValue("remark",request);
					String hr_inter = obj.setValue("int_hrs",request);
					String hr_ultimate = obj.setValue("ult_hrs",request);
				 con = obj.con();
					obj.createStatement(con);
					String sno = request.getParameter("sno");
				qry="update PMS_SCH_ASSET_GENERAL set YEAR_COMMISION=?,POP_PRESENT=?,POP_INTER=?,POP_ULTIMATE=?,SUPPLY_TP=?,SUPPLY_HAB=?,DAILY_REQ_INTER=?,DAILY_REQ_ULTIMATE=?,RATE_PUMP_INTER=?,RATE_PUMP_ULTIMATE=?,"
					+ "REMARKS=?,DAILY_REQ_PRESENT=?,PRESENT_YR=?,INTER_YR=?,ULTIMATE_YR=?,HRS_PUMPING=?,RATE_PUMP_PRESENT=?,SUPPLY_CORP=?,SUPPLY_MUN=?,HRS_PUMPING_INTER=?,HRS_PUMPING_ULTIMATE=? where PMS_ASSET_GEN_SNO=?";
					PreparedStatement ps = con.prepareStatement(qry);
					ps.setInt(1, year);
					ps.setInt(2, prepop);
					ps.setInt(3, intpop);
					ps.setInt(4, ultpop);
					ps.setInt(5, tp);
					ps.setInt(6, hab);
					ps.setInt(7, intdly);
					ps.setInt(8, ultdly);
					ps.setFloat(9, intrte);
					ps.setFloat(10, ultrte);
					ps.setString(11, remark);
					ps.setInt(12, actsup);
					ps.setInt(13, prepopyr);
					ps.setInt(14, intpopyr);
					ps.setInt(15, ultpopyr);
					ps.setInt(16, hrs);
					ps.setFloat(17, prerte);
					ps.setInt(18, corp);
					ps.setInt(19,muni);
					ps.setString(20,hr_inter);
					ps.setString(21, hr_ultimate);
					ps.setString(22, sno);
					int res = ps.executeUpdate();
					String xml = "<response>";
					xml += "<row>" + res;
					xml += "</row>";
					xml += "</response>";
					pr.write(xml);
					pr.flush();
					pr.close();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (comment.equalsIgnoreCase("view")) {
				try {
                 //System.out.println("show data");
				 con = obj.con();
				 obj.createStatement(con);
					qry = "select * from PMS_SCH_ASSET_GENERAL where SCH_SNO='"+schno+"' and office_id="+ofid+" " ;
					PreparedStatement ps = con.prepareStatement(qry);
					ResultSet rs = ps.executeQuery();
					String XML = "<response>";
					while (rs.next()) 
					{
					XML += "<PMS_ASSET_GEN_SNO>"+ rs.getString("PMS_ASSET_GEN_SNO")+"</PMS_ASSET_GEN_SNO>";
					XML += "<OFFICE_ID>" + rs.getString("OFFICE_ID")+"</OFFICE_ID>";
					XML += "<PROJECT_ID>" + rs.getString("PROJECT_ID")+"</PROJECT_ID>";
					XML += "<SCH_SNO>" + rs.getString("SCH_SNO")+"</SCH_SNO>";
					XML += "<ADMIN_SANCTION_SNO>"+ rs.getString("ADMIN_SANCTION_SNO")+ "</ADMIN_SANCTION_SNO>";
					XML += "<YEAR_COMMISION>" + rs.getString("YEAR_COMMISION")+"</YEAR_COMMISION>";
					XML += "<PRESENT_YR>" + rs.getString("PRESENT_YR")+"</PRESENT_YR>";
					XML += "<POP_PRESENT>" + rs.getString("POP_PRESENT")+"</POP_PRESENT>";
					XML += "<POP_INTER>" + rs.getString("POP_INTER")+"</POP_INTER>";
					XML += "<POP_ULTIMATE>" + rs.getString("POP_ULTIMATE")+"</POP_ULTIMATE>";
					XML += "<SUPPLY_TP>" + rs.getString("SUPPLY_TP")+"</SUPPLY_TP>";
					XML += "<SUPPLY_HAB>" + rs.getString("SUPPLY_HAB")+"</SUPPLY_HAB>";
					XML += "<DAILY_REQ_INTER>"+ rs.getString("DAILY_REQ_INTER")+"</DAILY_REQ_INTER>";
					XML += "<DAILY_REQ_ULTIMATE>"+ rs.getString("DAILY_REQ_ULTIMATE")+ "</DAILY_REQ_ULTIMATE>";
					XML += "<RATE_PUMP_INTER>"+ rs.getString("RATE_PUMP_INTER")+"</RATE_PUMP_INTER>";
					XML += "<RATE_PUMP_ULTIMATE>"+ rs.getString("RATE_PUMP_ULTIMATE")+ "</RATE_PUMP_ULTIMATE>";
					XML += "<REMARKS>" + rs.getString("REMARKS")+"</REMARKS>";
					XML += "<UPDATED_BY_USER_ID>"+ rs.getString("UPDATED_BY_USER_ID")+"</UPDATED_BY_USER_ID>";
					XML += "<UPDATED_TIME>" + rs.getString("UPDATED_TIME")+"</UPDATED_TIME>";
					XML += "<SUPPLY_ACTUAL>" + rs.getString("DAILY_REQ_PRESENT")+ "</SUPPLY_ACTUAL>";
					XML += "<PRESENT_YR>" + rs.getString("PRESENT_YR")+"</PRESENT_YR>";
					XML += "<INTER_YR>" + rs.getString("INTER_YR")+"</INTER_YR>";
					XML += "<ULTIMATE_YR>" + rs.getString("ULTIMATE_YR")+"</ULTIMATE_YR>";
					XML += "<HRS_PUMPING>" + rs.getString("HRS_PUMPING")+"</HRS_PUMPING>";
					XML += "<RATE_PUMP_PRESENT>"+ rs.getString("RATE_PUMP_PRESENT")+"</RATE_PUMP_PRESENT>";
				    XML += "<SUPPLY_CORP>"+ rs.getString("SUPPLY_CORP")+"</SUPPLY_CORP>";
				    XML += "<SUPPLY_MUN>"+ rs.getString("SUPPLY_MUN")+"</SUPPLY_MUN>";
				    XML+="<HRS_PUMPING_INTER>"+rs.getString("HRS_PUMPING_INTER")+"</HRS_PUMPING_INTER>";
				    XML+="<HRS_PUMPING_ULTIMATE>"+rs.getString("HRS_PUMPING_ULTIMATE")+"</HRS_PUMPING_ULTIMATE>";
					}
					XML += "</response>";
					pr.write(XML);
					pr.flush();
					pr.close();
					con.close();

				} catch (Exception e) {
					 System.out.println(e);
				}

			} else if (comment.equalsIgnoreCase("delete")) {
				try {
				 con = obj.con();
					obj.createStatement(con);
					String sno1 = request.getParameter("sno1");
					//System.out.println(sno1);
					qry = "delete from PMS_SCH_ASSET_GENERAL where PMS_ASSET_GEN_SNO=?";
					PreparedStatement ps = con.prepareStatement(qry);
					ps.setString(1, sno1);
					int res = ps.executeUpdate();
					String XML = "<response>";
					XML += "<row>" + res;
					XML += "</row>";
					XML += "</response>";
					//System.out.println(XML);
					pr.write(XML);
					pr.flush();
					pr.close();
					con.close();
					chk_count=obj.getCount("PMS_SCH_ASSET_GENERAL"," where SCH_SNO="+schno);
					 //System.out.println("chk_countv delete value"+chk_count);
						String XML1 = "<response1>";
						XML1 += "<row1>" + chk_count;
						XML1 += "</row1>";
						XML1 += "</response1>";
						//System.out.println("XML1 value"+XML1);
						pr.write(XML1);
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
