package Servlets.PMS.PMS1.DCB.servlets;

/* 
 * Created on : dd-mm-yy 
 * Author     : Sathya
 * Last Date  : 20/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description
 * 17/09/2011		Add the Beneficiary Status to 'L'  
 *---------|---------------|--------------------------------------------------- 
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Scheme_Mapping_List extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public Scheme_Mapping_List() 
	{
		super();
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		Connection con = null;
		try 
		{
			ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");

		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
		} catch (Exception e) 
		{
			System.out.println("Exception in connection...." + e);
		}
		ResultSet rs = null, rs1 = null, rs2 = null, rs4 = null;
		CallableStatement cs = null;
		PreparedStatement ps = null, ps1 = null, ps2 = null;
		String xml = "";

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				xml = "<response><command>sessionout</command><flag>sessionout</flag></response>";
				out.println(xml);
				out.close();
				return;
			}

		} catch (Exception e) {
		}
		String command;
		command = request.getParameter("command");
		session = request.getSession(false);
		String updatedby = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(l);
		String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);
		if (command.equalsIgnoreCase("loadproject")) 
		{
			xml = "<response><command>loadproject</command>";
			int cmbOffice_code = 0, y = 0;
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			} catch (Exception e) 
			{
				System.out.println("error get office id");
			}

			try {

				String qry_ = "select a.*,c.sch_type_desc,d.sch_status_desc from ( \n"
						+ "   SELECT PROJECT_ID,PROJECT_NAME,COMPONENT_NAME,decode(SCH_STATUS_ID,null,0,SCH_STATUS_ID) as SCH_STATUS_ID,decode(sch_type_id,null,0,sch_type_id) as sch_type_id ,decode(sch_sno,null,0,sch_sno) as sch_sno,sch_name,decode(comp_sno,null,0,comp_sno) as comp_sno,COMP_DESC,proj_or_comp \n"
						+ "  FROM PMS_MST_PROJECTS_VIEW WHERE OFFICE_ID=? and status='L') a \n"
						+ "   \n"
						+ "   left outer join pms_sch_lkp_type c on c.sch_type_id=a.sch_type_id \n"
						+ "  left outer join pms_sch_lkp_status d on d.sch_status_id=a.sch_status_id  order by a.PROJECT_ID";

				ps = con.prepareStatement(qry_);
				ps.setInt(1, cmbOffice_code);
				rs = ps.executeQuery();
				while (rs.next()) 
				{
					xml = xml + "<projectid>" + rs.getInt("PROJECT_ID")+ "</projectid>";
					xml = xml + "<projectname>" + rs.getString("PROJECT_NAME")+ "</projectname>";
					if (rs.getString("COMPONENT_NAME") != null)
						xml = xml + "<component>"+ rs.getString("COMPONENT_NAME")+ "</component>";
					else
						xml = xml + "<component>--</component>";

					xml = xml + "<areacode>0</areacode>";
					xml = xml + "<areadesc>0</areadesc>";

					if (rs.getInt("SCH_STATUS_ID") != 0) 
					{
						xml = xml + "<statuscode>" + rs.getInt("SCH_STATUS_ID")+ "</statuscode>";
						xml = xml + "<statusdesc>"+ rs.getString("sch_status_desc")+ "</statusdesc>";
					} else {
						xml = xml + "<statuscode>0</statuscode>";
						xml = xml + "<statusdesc>0</statusdesc>";
					}
					if (rs.getInt("sch_type_id") != 0) {
						xml = xml + "<typecode>" + rs.getInt("sch_type_id")+ "</typecode>";
						xml = xml + "<typedesc>"+ rs.getString("sch_type_desc") + "</typedesc>";
					} else {
						xml = xml + "<typecode>0</typecode>";
						xml = xml + "<typedesc>0</typedesc>";
					}

					if (rs.getInt("sch_sno") != 0)
						xml = xml + "<sch_sno>" + rs.getInt("sch_sno")+ "</sch_sno>";
					else
						xml = xml + "<sch_sno>0</sch_sno>";

					if (rs.getString("sch_name") != null)
						xml = xml + "<schemename>" + rs.getString("sch_name")+ "</schemename>";
					else
						xml = xml + "<schemename>--</schemename>";
					
					if (rs.getInt("comp_sno") != 0)
						xml = xml + "<compo_sno>" + rs.getInt("comp_sno")+ "</compo_sno>";
					else
						xml = xml + "<compo_sno>0</compo_sno>";

					if (rs.getString("COMP_DESC") != null)
						xml = xml + "<compo_desc>" + rs.getString("COMP_DESC")+ "</compo_desc>";
					else
						xml = xml + "<compo_desc>0</compo_desc>";

					if (rs.getString("proj_or_comp") != null)
						xml = xml + "<pro_comp>" + rs.getString("proj_or_comp")+ "</pro_comp>";
					else
						xml = xml + "<pro_comp>--</pro_comp>";
					y++;
				}

				if (y != 0) 
				{
					xml = xml + "<flag>success</flag>";
				} else
					xml = xml + "<flag>failure</flag>";

			} catch (SQLException e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			out.println(xml);
		}
	}

	 
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;

		try {

			ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";
			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");

		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"+ strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in connection...." + e);
		}
		ResultSet rs = null, rs1 = null, rs2 = null, rs4 = null;
		CallableStatement cs = null;
		PreparedStatement ps = null, ps1 = null, ps2 = null;
		String xml = "";
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		String command;
		command = request.getParameter("command");
		session = request.getSession(false);
		String updatedby = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(l);

		if (command.equalsIgnoreCase("Add")) 
		{
			int cmbOffice_code = 0;
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}

			String project_No[] = request.getParameterValues("projectno");
			String project_Name[] = request.getParameterValues("projectname");
			String component_Name[] = request.getParameterValues("componame");
			String state_id[] = request.getParameterValues("officestate");
			String status_id[] = request.getParameterValues("schemestatus");
			String scheme_type[] = request.getParameterValues("schemetype");
			String scheme_no[] = request.getParameterValues("schemeno");
			String scheme_name[] = request.getParameterValues("schemname");
			String compo_no[] = request.getParameterValues("compono");
			String compo_name2[] = request.getParameterValues("componame2");
			String pro_comp[] = request.getParameterValues("procomp");
			int projectId = 0, stateId = 0, sch_Sno = 0, comp_Sno = 0, schemeTypeId = 0, schemeStatusId = 0, componentNo = 0;
			String projectName = "", componentName = "", schemeName = "", componentName2 = "", procomp = "";
			for (int i = 0; i < state_id.length; i++) {

				try {

					schemeStatusId = Integer.parseInt(status_id[i]);
					try {
						projectId = Integer.parseInt(project_No[i]);
					} catch (NumberFormatException e) {
						System.out.println("exception" + e);
					}
					try {
						sch_Sno = Integer.parseInt(scheme_no[i]);
					} catch (NumberFormatException e) {
						System.out.println("exception" + e);
					}

					try {
						schemeTypeId = Integer.parseInt(scheme_type[i]);
					} catch (NumberFormatException e) {
						System.out.println("exception" + e);
					}
					try {
						componentNo = Integer.parseInt(compo_no[i]);
					} catch (NumberFormatException e) {
						System.out.println("exception" + e);
					}
					projectName = project_Name[i];
					componentName = component_Name[i];
					procomp = pro_comp[i];
					schemeName = scheme_name[i];
					componentName2 = compo_name2[i];

					try {
						ps = con.prepareStatement("update PMS_MST_PROJECTS_VIEW set  SCH_STATUS_ID=?,SCH_TYPE_ID=?,SCH_SNO=?,SCH_NAME=?,COMP_SNO=?,COMP_DESC=?,PROJ_OR_COMP=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where OFFICE_ID=? and PROJECT_ID=?");
						ps.setInt(1, schemeStatusId);
						ps.setInt(2, schemeTypeId);
						ps.setInt(3, sch_Sno);
						ps.setString(4, schemeName);
						ps.setInt(5, componentNo);
						ps.setString(6, componentName2);
						ps.setString(7, procomp);
						ps.setString(8, updatedby);
						ps.setTimestamp(9, ts);
						ps.setInt(10, cmbOffice_code);
						ps.setInt(11, projectId);
						ps.executeUpdate();
					} catch (Exception e) 
					{
						System.out.println(e);
					}
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
			}
			sendMessage(response, "Scheme Mapping Upated ", "ok");
		}
	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
		}
	}
}
