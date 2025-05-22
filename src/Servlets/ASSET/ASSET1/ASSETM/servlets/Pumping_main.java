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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class Pumping_main extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Pumping_main() {
        super();
    }
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		Controller obj = new Controller();
		PrintWriter pr = response.getWriter();
		String comment = request.getParameter("comment");
		String qry;
		Connection con = null;
		int ofid = 0, proid = 0, comp = 0,schno=0, subcomp = 0;
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		String XML="";
		try {
			con = obj.con();
			obj.createStatement(con);
		if (userid == null) 
		{    
			 
		}
		
		System.out.println("ASSET->Pumping_main->command->" + comment);  
		String Office_id = "";
		try {
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		 
		 ofid = Integer.parseInt(Office_id);
		  String sch_no=obj.setValue("sno", request);
		 String project_id="0";
			try {
				project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch_no);
				proid=Integer.parseInt(project_id);
			} catch (Exception e2) 
			{
				proid=Integer.parseInt("0");
				e2.printStackTrace();
			}
			
		if (comment.equalsIgnoreCase("add")) 
		{
			try {
				int main_type = Integer.parseInt(request.getParameter("main_type"));					
				int class_type = Integer.parseInt(request.getParameter("class_type"));						
				int dim = Integer.parseInt(request.getParameter("dim"));
				int len_main = Integer.parseInt(request.getParameter("len_main"));			
				String from = request.getParameter("from");	
				String to = request.getParameter("to"); 
				String sno= obj.setValue("sno", request);
				comp=Integer.parseInt(obj.setValue("comp", request));
				subcomp=Integer.parseInt(obj.setValue("sub_comp", request));
				int id = obj.getMax("PMS_SCH_ASSET_PMAIN","PMS_ASSET_PMAIN_SNO", "");
			
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				qry = "insert into PMS_SCH_ASSET_PMAIN (PMS_ASSET_PMAIN_SNO,OFFICE_ID,PROJECT_ID,SCH_SNO,COMP_SNO,SUBCOMP_SNO,TYPE_OF_PMAIN,CLASS_OF_MAIN,DIAMETER,LENGTH_MAIN,LOCATION_FROM,UPDATED_BY_USER_ID,UPDATED_TIME,LOCATION_TO)"
						+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(qry);
			
				ps.setInt(1, id);
				ps.setInt(2, ofid);
				ps.setInt(3, proid);
				ps.setString(4, sno);  
				ps.setInt(5, comp);  
				ps.setInt(6, subcomp);
				ps.setInt(7,main_type);
				ps.setInt(8, class_type);
				ps.setInt(9, dim);
				ps.setInt(10, len_main);
				ps.setString(11, from);
				ps.setString(12, userid);
				ps.setTimestamp(13, sqlTimestamp);
				ps.setString(14, to);
				int res = ps.executeUpdate();
				  XML = "<response>";
				XML += "<row>" + res;
				XML += "</row>";
				XML += "</response>";
				 
				 
				 

			} catch (Exception e) {

				e.printStackTrace();
			}
		} 
		 else if (comment.equalsIgnoreCase("view"))
			{
				try {
	            
					 
					qry = "select * from PMS_SCH_ASSET_PMAIN";
					PreparedStatement ps = con.prepareStatement(qry);
					ResultSet rs = ps.executeQuery();
					   XML = "<response>";
					while (rs.next())   
					{
						
						XML += "<PMS_ASSET_PMAIN_SNO>" +rs.getString("PMS_ASSET_PMAIN_SNO");
						XML += "</PMS_ASSET_PMAIN_SNO>";
						XML += "<OFFICE_ID>" + rs.getString("OFFICE_ID");
						XML += "</OFFICE_ID>";
						XML += "<PROJECT_ID>" + rs.getString("PROJECT_ID");	
						XML +="</PROJECT_ID>";
						XML += "<SCH_SNO>" + rs.getString("SCH_SNO");
						XML += "</SCH_SNO>";
						XML += "<COMP_ID>" + rs.getString("COMP_SNO");   
						XML += "</COMP_ID>";
						XML += "<SUBCOMP_ID>" + rs.getString("SUBCOMP_SNO");
						XML += "</SUBCOMP_ID>";
						String type=rs.getString("TYPE_OF_PMAIN");
						String val=obj.getValue("PMS_SCH_ASSET_PMAIN_TYPE","TYPE_DESC", "where PMAIN_TYPE_ID="+type);
						XML += "<TYPE_OF_PMAIN>"+val+"</TYPE_OF_PMAIN>";
						String ctype=rs.getString("CLASS_OF_MAIN");
						String cval=obj.getValue("PMS_SCH_ASSET_CLASS_TYPE","TYPE_DESC", "where CLASS_TYPE_ID="+ctype);
						XML += "<CLASS_OF_MAIN>"
							+cval;
				        XML += "</CLASS_OF_MAIN>";
						XML += "<DIAMETER>" + rs.getString("DIAMETER");                              					
						XML += "</DIAMETER>";
						XML += "<LENGTH_MAIN>" + rs.getString("LENGTH_MAIN");
						XML += "</LENGTH_MAIN>";
						XML += "<LOCATION_FROM>" + rs.getString("LOCATION_FROM");     
						XML += "</LOCATION_FROM>";
						XML += "<UPDATED_BY_USER_ID>"
								+ rs.getString("UPDATED_BY_USER_ID");
						XML += "</UPDATED_BY_USER_ID>";
						XML += "<UPDATED_TIME>" + rs.getString("UPDATED_TIME");
						XML += "</UPDATED_TIME>";
						XML += "<LOCATION_TO>" + rs.getString("LOCATION_TO");
						XML += "</LOCATION_TO>";
					}

					XML += "</response>";
					
					
					 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			
			}
		 else if (comment.equalsIgnoreCase("modify")) {
				try {

					int main_type = Integer.parseInt(request.getParameter("main_type"));					
					int class_type = Integer.parseInt(request.getParameter("class_type"));						
					int dim = Integer.parseInt(request.getParameter("dim"));
					int len_main = Integer.parseInt(request.getParameter("len_main"));			
					String from = request.getParameter("from");	
					String to = request.getParameter("to");
					 
					int sno = Integer.parseInt(request.getParameter("sno"));

					qry = "update  PMS_SCH_ASSET_PMAIN set TYPE_OF_PMAIN=?,CLASS_OF_MAIN=?,DIAMETER=?,LENGTH_MAIN=?,LOCATION_FROM=?,LOCATION_TO=? where  PMS_ASSET_PMAIN_SNO=? ";

					PreparedStatement ps = con.prepareStatement(qry);
					
					
					ps.setInt(1,main_type);
					ps.setInt(2, class_type);
					ps.setInt(3, dim);
					ps.setInt(4, len_main);
					ps.setString(5, from);					
					ps.setString(6, to);
					ps.setInt(7,sno);
					int res = ps.executeUpdate();
					XML = "<response>";
					XML += "<row>" + res;
					XML += "</row>";
					XML += "</response>";
					 
					con.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else if (comment.equalsIgnoreCase("pop")) {
				try {
					 
					String sno = request.getParameter("sno");
					 
					qry = "delete from PMS_SCH_ASSET_PMAIN where PMS_ASSET_PMAIN_SNO=?";
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
				 
					pr.write(XML);
					con.close(); 
		} catch (Exception e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}  	
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
