package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Emp_Ctrl_Officepopup extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {

			ResourceBundle rs = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs.getString("Config.DSN");
			String strhostname = rs.getString("Config.HOST_NAME");
			String strportno = rs.getString("Config.PORT_NUMBER");
			String strsid = rs.getString("Config.SID");
			String strdbusername = rs.getString("Config.USER_NAME");
			String strdbpassword = rs.getString("Config.PASSWORD");

		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
		//			+ strportno.trim() + ":" + strsid.trim();
			
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());

			System.out.println("connection success");
		} catch (Exception e) {
			System.out.println("Exception in connection..." + e);
		}
		ResultSet rs = null;
		PreparedStatement ps = null;
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				String xml = "<response><command>sessionout</command><flag>sessionout</flag></response>";
				out.println(xml);
				System.out.println(xml);
				out.close();
				return;

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

		String xml = "";
		String strCommand = "";
		int sgroup = 0;
		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign....." + strCommand);
			// sgroup=Integer.parseInt(request.getParameter("cmbsgroup"));
			// System.out.println("sgroup is"+sgroup);
		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		if (strCommand.equalsIgnoreCase("Emp")) {
			xml = "<response>";

			try {
				System.out.println("sgroup::" + sgroup);

				int count = 0;

				UserProfile empProfile = (UserProfile) session
						.getAttribute("UserProfile");

				System.out.println("user id::" + empProfile.getEmployeeId());
				int empid = empProfile.getEmployeeId();
				int oid = 0;

				ps = con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
				ps.setInt(1, empid);
				rs = ps.executeQuery();
				if (rs.next()) {
					oid = rs.getInt("OFFICE_ID");
				}
				rs.close();
				ps.close();

				String sql = "SELECT A.employee_id, " +
						"  empname, " +
						"  designation, " +
						"  gpf_no, " +
						"  designation_id " +
						"FROM " +
						"  (SELECT employee_id " +
						"  FROM hrm_emp_controlling_office " +
						"  WHERE controlling_office_id="+oid+" " +
						"  )A " +
						"JOIN " +
						"  (SELECT employee_id, " +
						"    employee_initial " +
						"    || '.' " +
						"    || employee_name AS empname, " +
						"    designation " +
						"  FROM view_employee2 " +
						"  )B " +
						"ON A.employee_id=b.employee_id " +
						"LEFT OUTER JOIN " +
						"  (SELECT employee_id,designation_id FROM hrm_emp_current_posting " +
						"  )C " +
						"ON a.employee_id=c.employee_id " +
						"LEFT OUTER JOIN " +
						"  (SELECT employee_id,gpf_no FROM hrm_mst_employees " +
						"  )d " +
						"ON a.employee_id=d.employee_id " +
						"ORDER BY designation_id";

				System.out.println(sql);
				ps = con.prepareStatement(sql);
				//ps.setInt(1, oid);
				rs = ps.executeQuery();

				while (rs.next()) {

					
					xml = xml + "<employee>";
					xml = xml + "<empid>" + rs.getInt("EMPLOYEE_ID")
							+ "</empid>";
					xml = xml + "<empname>" + rs.getString("empname")
							+ "</empname>";
			
				
					xml = xml + "<designation>" + rs.getString("DESIGNATION")
							+ "</designation>";
			
			        xml = xml + "<gpf>" + rs.getInt("gpf_no") + "</gpf>";
					xml = xml + "</employee>";
			
					count++;
				}

				// System.out.println("count::"+count);
				if (count == 0)
					xml = "<response><flag>failure</flag>";
				else
					xml = xml + "<flag>success</flag>";

			}

			catch (Exception e) {

				System.out.println("catch........" + e);
				// e.printStackTrace();

				xml = "<response><flag>failure</flag>";
			}

			xml = xml + "</response>";

		} else if (strCommand.equalsIgnoreCase("loadempedit")) {
			// txtEmployeeid
			xml = "<response>";
			int nn = Integer.parseInt(request.getParameter("txtEmployeeid"));

			try {

				// int count=0;

				String sql ="SELECT Employee_Name " 
				+"  ||' ' " 
				+"  ||Employee_Initial AS Employee_Name, " 
				+"  Designation, " 
				+"  T.Office_Id ,b.Designation_Id,curr_off_id " 
				+"FROM " 
				+"  (SELECT Employee_Id AS Empid, " 
				+"    Designation_Id , " 
				+"    Employee_Status_Id,office_id as curr_off_id, " 
				+"    TO_CHAR(Date_Effective_From,'dd/mm/yyyy') AS Date_Effective_From " 
				+"  FROM hrm_emp_current_posting " 
				+"  WHERE Employee_Id='"
										+ nn
										+ "' " 
				+"  )b " 
				+"LEFT OUTER JOIN " 
				+"  (SELECT Employee_Id, " 
				+"    Employee_Name, " 
				+"    Employee_Initial, " 
				+"    Gpf_No, " 
				+"    TO_CHAR(Date_Of_Birth,'dd/mm/yyyy') AS Dob " 
				+"  FROM Hrm_Mst_Employees " 
				+"  )A " 
				+"ON A.Employee_Id=B.Empid " 
				+"LEFT OUTER JOIN " 
				+"  (SELECT Designation_Id AS Desig,Designation FROM Hrm_Mst_Designations " 
				+"  )F " 
				+"ON F.Desig=b.Designation_Id " 
				+"LEFT OUTER JOIN " 
				+"  (SELECT employee_id, " 
				+"    CONTROLLING_OFFICE_ID AS Office_Id " 
				+"  FROM HRM_EMP_CONTROLLING_OFFICE " 
				+"  )T " 
				+"ON T.employee_id=b.Empid";

				System.out.println(sql);
				ps = con.prepareStatement(sql);
				// ps.setInt(1,oid);
				rs = ps.executeQuery();

				if (rs.next()) {

					// xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
					// xml=xml+"<employee>";
					int temp=rs.getInt("Designation_Id");
					if( temp==4 ||  temp ==6 || temp==7 || temp==8)										
					xml = xml + "<Office_Id>" + rs.getInt("curr_off_id")+ "</Office_Id>";
					else
						xml = xml + "<Office_Id>" + rs.getInt("Office_Id")+ "</Office_Id>";
					
					xml = xml + "<empn>" + rs.getString("Employee_Name")
							+ "</empn>";
					xml = xml + "<Designation>" + rs.getString("Designation")
							+ "</Designation><flag>Success</flag>";
					// xml=xml+"<designation>"
					// +rs.getString("DESIGNATION")+"</designation>";

					// xml=xml+"</employee>";

					// count++;
				} else {
					xml = xml + "<flag>failure</flag>";
				}

			}

			catch (Exception e) {

				System.out.println("catch........" + e);

				xml = "<response><flag>failure</flag>";
			}

			xml = xml + "</response>";
		}

		out.println(xml);
		System.out.println(xml);
	}

}
