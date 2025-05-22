package Servlets.HR.HR1.EmployeeMaster.Reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class Individual_User
 */
public class Role_template_to_user extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Role_template_to_user() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection con = null, con1 = null;

		try {
			// System.out.println("New role assinging----------------------->");
			LoadDriver driver=new LoadDriver();
            con=driver.getConnection();

		} catch (Exception e) {
			System.out.println("Exception in connection..." + e);
		}
		ResultSet rs = null, rs1 = null, rss = null;
		PreparedStatement ps = null, pss = null, psss = null;
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		String updatedby = (String) session.getAttribute("UserId");
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

		String xml = "";

		String strCommand = "";
		int Desig_id = 0;
		int sgroup = 0;
		try {
			strCommand = request.getParameter("Command");

		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		if (strCommand.equalsIgnoreCase("submit")) {
			// System.out.println("inside submit");
			String desigval = null, roleval = null, sql = null, sql1 = null;
			// String[] desig = null;
			// String[] role = null;
			// int flag = 0;
			int i, j;
			// System.out.println("hello");
			// int desig=Integer.parseInt(request.getParameter("desigval"));
			int from = Integer.parseInt(request.getParameter("from"));
			int to = Integer.parseInt(request.getParameter("to"));
			int template_id = Integer.parseInt(request
					.getParameter("template_id"));
			sql = "select su.user_id,su.employee_id from SEC_MST_USERS su  where employee_id >= ? and employee_id <= ? and su.login_enabled=1 and user_id not in  (select USER_ID from SEC_MST_TEMPLATE_USERS where ROLE_TEMPLATE_ID=? ) order by su.employee_id";
			try {

				try {
					ps = con.prepareStatement(sql);
					ps.setInt(1, from);
					ps.setInt(2, to);
					ps.setInt(3, template_id);
					rs = ps.executeQuery();

					while (rs.next()) {
						String userid = rs.getString("user_id");
						pss = con
								.prepareStatement("insert into sec_mst_template_users values('"
										+ template_id
										+ "','"
										+ userid
										+ "','"
										+ updatedby + "',clock_timestamp())");
						pss.executeUpdate();
						userid = "";

					}
					xml = "<response><flag>success</flag></response>";
					rs.close();
					con.commit();
					// delete from sec_mst_user_roles where role_id=(select
					// role_id
					// from sec_mst_template_roles where role_template_id=2)

					try {
						sql1 = "select su.user_id,su.employee_id from SEC_MST_USERS su  where employee_id >= ? and employee_id <= ? and su.login_enabled=1 and employee_id in(select employee_id from hrm_emp_current_posting where employee_status_id='WKG') order by su.employee_id";
						psss = con.prepareStatement(sql1);
						psss.setInt(1, from);
						psss.setInt(2, to);

						rss = psss.executeQuery();
						while (rss.next()) {
							int employee_id = rss.getInt("employee_id");
							CallableStatement pstmt = con
									.prepareCall("{call hrm_assign_roles_temp(?,?)}");
							pstmt.setInt(1, employee_id);
							pstmt.setInt(2, template_id);
							pstmt.executeUpdate();
						}
						xml = "<response><flag>success</flag></response>";
						rss.close();
						con.commit();

					} catch (SQLException e) {
						System.out.println("Error  in procedure call" + e);
						xml = "<response><flag>fail</flag></response>";
						con.rollback();
					}
					con.commit();
				} catch (SQLException e) {
					System.out.println("Error in select user ID  :" + e);
					xml = "<response><flag>fail</flag></response>";
					con.rollback();
				}
				con.commit();
			} catch (Exception e) {
				xml = "<response><flag>fail</flag></response>";
				
			}

			System.out.println(xml);
			out.println(xml);
		}

		else if (strCommand.equalsIgnoreCase("remove")) {
			// System.out.println("inside remove");
			String desigval = null, roleval = null, sql = null, sql1 = null;
			// String[] desig = null;
			// String[] role = null;
			int flag = 0;
			int i, j;
			int from = Integer.parseInt(request.getParameter("from"));
			int to = Integer.parseInt(request.getParameter("to"));
			int template_id = Integer.parseInt(request
					.getParameter("template_id"));

			try {

				sql = "DELETE FROM sec_mst_template_users WHERE user_id IN   (SELECT su.user_id   FROM SEC_MST_USERS su WHERE (employee_id  >= '"
						+ from
						+ "') AND (employee_id <= '"
						+ to
						+ "') AND (su.login_enabled=1) ) AND  role_template_id='"
						+ template_id + "'";
				try {
					ps = con.prepareStatement(sql);

					ps.executeUpdate();
					System.out.print("delete successfully");
				} catch (SQLException e) {
					System.out
							.println("error in sec_mst_template_users table deletion"
									+ e);
					xml = "<response><flag>fail</flag></response>";
				}
				sql1 = "select su.user_id,su.employee_id from SEC_MST_USERS su  where employee_id >= ? and employee_id <= ? and su.login_enabled=1 order by su.employee_id";

				try {
					psss = con.prepareStatement(sql1);
					psss.setInt(1, from);
					psss.setInt(2, to);

					rss = psss.executeQuery();
					while (rss.next()) {
						int employee_id = rss.getInt("employee_id");
						CallableStatement pstmt = con
								.prepareCall("{call hrm_revoke_roles_temp(?,?)}");
						pstmt.setInt(1, employee_id);
						pstmt.setInt(2, template_id);
						pstmt.executeUpdate();
					}
					rss.close();
					xml = "<response><flag>success</flag></response>";
				} catch (SQLException e) {
					System.out.println("error in call procedure" + e);
					xml = "<response><flag>fail</flag></response>";
				}

			}

			catch (Exception e) {
				System.out.println(e);
				xml = "<response><flag>fail</flag></response>";
			}
			// System.out.println(xml);
			out.println(xml);
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
