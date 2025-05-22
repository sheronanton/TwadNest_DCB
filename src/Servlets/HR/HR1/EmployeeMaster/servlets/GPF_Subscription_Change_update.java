package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

public class GPF_Subscription_Change_update extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;
    Connection connection = null;
	Statement statement = null;
	ResultSet results3 = null;
	ResultSet results4 = null;
	PreparedStatement ps = null, lstatement, Pstatement, empstmt;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        try {
        	LoadDriver driver=new LoadDriver();
            connection=driver.getConnection();


		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
    }
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("java");
		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		String strCommand = "", sql;

		String xml = "";
		int found = 0;
		response.setHeader("Cache-Control", "no-cache");
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				System.out.println(request.getContextPath()
						+ "/index.jsp?message=sessionout");
				response.sendRedirect(request.getContextPath()
						+ "/index.jsp?message=sessionout");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			strCommand = request.getParameter("command");
		} catch (Exception e) {
			e.printStackTrace();
		}

		int count = 0;
		String offid = "";
		int txtOffice_Id = 0;
		int txtOffice_Id1 = 0;
		int auto_no = 0;
		int ujt = 0;
		int ajt = 0;
		int ejt = 0;
		String ton = "", eid1 = "", tod = "", ron = "", rod = "", jd = "", app_name = "", app_desg = "";
		String userid = (String) session.getAttribute("UserId");
		System.out.println("session id is:" + userid);

		String updatedby = (String) session.getAttribute("UserId");

		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		java.util.Date date1 = new java.util.Date();
		String dateString = dateFormat.format(date1);
		java.sql.Timestamp ts = null;
		try {
			ts = new java.sql.Timestamp(dateFormat.parse(dateString).getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(strCommand);

		if (strCommand.equalsIgnoreCase("Update")) {
			try {
				
				int no = Integer.parseInt(request.getParameter("no"));
				String txtGpfNo = request.getParameter("txtGpfNo");
				int amount = Integer.parseInt(request.getParameter("amount"));
				String fin_year = request.getParameter("fin_year");
				
				String eff_month = request.getParameter("eff_month");
			
				String eff_year = request.getParameter("eff_year");

				String remarks = request.getParameter("remarks");
			
				String date = request.getParameter("date");

				
			
				java.sql.Date f1 = null;
				System.out.println("before converting date");
				String dateString1 = date;
				SimpleDateFormat dateFormat1 = new SimpleDateFormat(
						"dd/MM/yyyy");

				java.util.Date d1;
				d1 = dateFormat1.parse(date.trim());
				dateFormat1.applyPattern("yyyy-MM-dd");
				dateString1 = dateFormat1.format(d1);
				f1 = java.sql.Date.valueOf(dateString1);
				 System.out.println(f1);

				
				

				System.out.println("inside Update");

				
				

				sql ="UPDATE  HRM_GPF_SUBSCRIPTION_CHANGE  SET  REQUEST_SINO= ?,GPF_NO = ? ,REQUEST_DATE  = ?,SUBSCRIPTION_AMT= ? , EFFECTIVE_YEAR = ?,EFFECTIVE_MONTH = ?, PROCESS_FLOW_STATUS_ID = ?, UPDATED_DATE =?,UPDATED_BY =?, REMARKS =?, FIN_YEAR = ?  where REQUEST_SINO='"+no+"' " ;

				PreparedStatement pss = connection.prepareStatement(sql);
				pss.setInt(1, no);
				pss.setString(2, txtGpfNo);
				pss.setDate(3, f1);
				pss.setInt(4, amount);
				pss.setString(5, eff_year);
				pss.setString(6, eff_month);
				pss.setString(7, "CR");
				pss.setTimestamp(8, ts);
				pss.setString(9, updatedby);
				pss.setString(10,remarks);
				pss.setString(11,fin_year);
				pss.executeUpdate();

				xml = "<response> <status>success</status> <command>Update</command> </response>";

			} catch (Exception e) {
				xml = "<response><status>Failed</status></response>";
				e.printStackTrace();
			}

		}

		
		

		if (strCommand.equalsIgnoreCase("Delete")) {
			try {
				//eid1 = request.getParameter("eid1");
				
				int no = Integer.parseInt(request.getParameter("no"));
				String txtGpfNo = request.getParameter("txtGpfNo");
				
				System.out.println(no);
				System.out.println(txtGpfNo);
				System.out.println("inside Delete ");

				sql = "delete from HRM_GPF_SUBSCRIPTION_CHANGE  where REQUEST_SINO='"+ no+"' and GPF_NO='"+txtGpfNo+"'";					

			Statement stt = connection.createStatement();
			stt.execute(sql);	
				

				

				xml = "<response> <status>success</status> <command>Delete</command> </response>";

			} catch (Exception e) {
				xml = "<response><status>Failed</status></response>";
				e.printStackTrace();
			}

		}

		
		
		
		
		
		
	
		
		
		if (strCommand.equalsIgnoreCase("Validate")) {
			try {
				
				int no = Integer.parseInt(request.getParameter("no"));
				String txtGpfNo = request.getParameter("txtGpfNo");
				int amount = Integer.parseInt(request.getParameter("amount"));
				String fin_year = request.getParameter("fin_year");
				
				String eff_month = request.getParameter("eff_month");
			
				String eff_year = request.getParameter("eff_year");

				String remarks = request.getParameter("remarks");
			
				String date = request.getParameter("date");

				
			
				java.sql.Date f1 = null;
				System.out.println("before converting date");
				String dateString1 = date;
				SimpleDateFormat dateFormat1 = new SimpleDateFormat(
						"dd/MM/yyyy");

				java.util.Date d1;
				d1 = dateFormat1.parse(date.trim());
				dateFormat1.applyPattern("yyyy-MM-dd");
				dateString1 = dateFormat1.format(d1);
				f1 = java.sql.Date.valueOf(dateString1);
				 System.out.println(f1);

				
				

				

				System.out.println("inside Validate");

				
				System.out.println(auto_no);

				sql ="UPDATE  HRM_GPF_SUBSCRIPTION_CHANGE  SET  REQUEST_SINO= ?,GPF_NO = ? ,REQUEST_DATE  = ?,SUBSCRIPTION_AMT= ? , EFFECTIVE_YEAR = ?,EFFECTIVE_MONTH = ?, PROCESS_FLOW_STATUS_ID = ?, UPDATED_DATE =?,UPDATED_BY =?, REMARKS =?, FIN_YEAR = ?  where REQUEST_SINO='"+no+"' " ;

				PreparedStatement pss = connection.prepareStatement(sql);
				pss.setInt(1, no);
				pss.setString(2, txtGpfNo);
				pss.setDate(3, f1);
				pss.setInt(4, amount);
				pss.setString(5, eff_year);
				pss.setString(6, eff_month);
				pss.setString(7, "FR");
				pss.setTimestamp(8, ts);
				pss.setString(9, updatedby);
				pss.setString(10,remarks);
				pss.setString(11,fin_year);
				pss.executeUpdate();


				xml = "<response> <status>success</status> <command>Validate</command> </response>";

			} catch (Exception e) {
				xml = "<response><status>Failed</status></response>";
				e.printStackTrace();
			}

		}
		
		
		if (strCommand.equalsIgnoreCase("LoadLoanID")) {
			System.out.println("Test load loan Id");
			xml = "<response>";

			try {

				count = 0;

				UserProfile empProfile = (UserProfile) session
						.getAttribute("UserProfile");

				System.out.println("user id::" + empProfile.getEmployeeId());
				int empid = empProfile.getEmployeeId();
				
				System.out.println(empid);
				
				int oid = 0;
				try {
					ps = connection
							.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
					ps.setInt(1, empid);
					results3 = ps.executeQuery();
					if (results3.next()) {
						oid = results3.getInt("OFFICE_ID");
					}

				} catch (Exception e) {
					System.out.println("catch" + e);
					// e.printStackTrace();
					 
					 
				}
				System.out.println(oid);
			
				
				
			String sql3 = "SELECT f.request_sino, " +
			"  f.gpf_no, " +
			"  f.request_date, " +
			"  f.subscription_amt, " +
			"  f.EFFECTIVE_YEAR, " +
			"  f.effective_month, " +
			"  f.PROCESS_FLOW_STATUS_ID, " +
			"  f.updated_date, " +
			"  f.updated_by, " +
			"  f.REMARKS, " +
			"  f.fin_year, " +
			"  f.accounting_unit_id, " +
			"  b.employee_id, " +
			"  b.EMPLOYEE_name, " +
			"  cc.designation " +
			"FROM " +
			"  (SELECT * FROM hrm_gpf_subscription_change " +
			"  )f " +
			"LEFT OUTER JOIN " +
			"  (SELECT * FROM hrm_mst_employees " +
			"  )b " +
			"ON f.GPF_NO=b.GPF_NO " +
			"LEFT OUTER JOIN " +
			"  (SELECT * FROM hrm_emp_current_posting " +
			"  )bb " +
			"ON b.employee_id=bb.employee_id " +
			"LEFT OUTER JOIN " +
			"  (SELECT * FROM hrm_mst_designations " +
			"  )cc " +
			"ON bb.designation_id=cc.designation_id" ;
				
				
				//String sql3="select * from  HRM_LEAVE_UNAVAIL_JT_EL_TRN";
				
				System.out.println(sql3);
				
			
				
			//	Statement stt2 = connection.createStatement();
				//stt2.executeQuery(sql3);
			PreparedStatement st3 = connection.prepareStatement(sql3);
			//st3.setInt(1, oid);
				ResultSet results5 = st3.executeQuery();

				while (results5.next()) {
					// xml=xml+"<option><id>"+rs.getInt("DESIGNATION_ID")+"</id><name>"+rs.getString("DESIGNATION")+"</name></option>";
					
					xml = xml + "<employee>";
					xml = xml + "<empid>" + results5.getString("Employee_Id")
							+ "</empid>";
					xml = xml + "<empname>"
							+ results5.getString("Employee_Name")
							+ "</empname>";
					// xml=xml+"<initial>"
					// +results3.getString("EMPLOYEE_INITIAL")+"</initial>";
					xml = xml + "<designation>"
							+ results5.getString("REQUEST_SINO")
							+ "</designation>";
					// xml=xml+"<dob>" +results3.getString("dob")+"</dob>";
					/*
					 * if( rs.getDate("DATE_OF_BIRTH")!=null) { String[]
					 * sd=rs.getDate("DATE_OF_BIRTH").toString().split("-");
					 * String od=sd[2]+"/"+sd[1]+"/"+sd[0]; xml=xml+"<dob>"
					 * +od+"</dob>"; } else { xml=xml+"<dob>"
					 * +rs.getDate("DATE_OF_BIRTH")+"</dob>"; }
					 */
					// xml=xml+"<gpf>" +results3.getString("GPF_NO")+"</gpf>";
					xml = xml + "<eff_year>"
						+ results5.getString("REQUEST_SINO")
						+ "</eff_year>";
				 xml = xml + "<amount>"
				 + results5.getString("DESIGNATION")
				 + "</amount>";
				 xml = xml + "<gpf_no>"
				 + results5.getString("GPF_NO")
				 + "</gpf_no>";
				 
				
				 
				xml = xml + "</employee>";
					// System.out.println("ok");
					count++;
					
					String des=results5.getString("DESIGNATION");
					System.out.println(des);
					
				}
				// System.out.println("count::"+count);
				
				
				if (count == 0)
					xml = "<response><flag>failure</flag>";
				else
					xml = xml + "<flag>success</flag>";
				System.out.println(xml);
				results5.close();
				st3.close();
				
			
			}

			catch (Exception e) {
				System.out.println(xml);
				System.out.println("catch........" + e);
				e.printStackTrace();

				xml = "<response><flag>failure</flag>";
			}

			xml = xml + "</response>";

		}

	
		
		if (strCommand.equalsIgnoreCase("Get")) {
			try {

				
				int no = Integer.parseInt(request.getParameter("no"));
				String txtGpfNo = request.getParameter("txtGpfNo");
				
				 System.out.println(no);
				xml += "<response>";

				sql = "Select *  from HRM_GPF_SUBSCRIPTION_CHANGE  where REQUEST_SINO='"+ no+"'";		

				
				Statement stt2 = connection.createStatement();
				stt2.executeQuery(sql);
				ResultSet emprs = stt2.executeQuery(sql);
				if (emprs.next()) {

					
					
					
					java.sql.Date DateOfFormation4 = emprs.getDate("REQUEST_DATE");
					java.text.SimpleDateFormat sdf4 = new java.text.SimpleDateFormat(
							"dd/MM/yyyy");
					String date14 = sdf4.format(DateOfFormation4);
										

					no = emprs.getInt("REQUEST_SINO");

					xml += "<no>" + no + "</no> ";
				//	xml += "<eid1>" + emprs.getInt("EMPLOYEE_ID")
						//	+ "</eid1> ";
					//xml += "<date>" +date14 + "</date> ";

					xml += "<fin_year>" + emprs.getString("FIN_YEAR")
							+ "</fin_year> ";
					xml += "<date>"
							+date14
							+ "</date> ";

					xml += "<eff_month>" + emprs.getString("EFFECTIVE_MONTH")
							+ "</eff_month> ";

					

					xml += "<remarks>"
							+ emprs.getString("REMARKS")
							+ "</remarks> ";
					

					
					xml += "<eff_year>" + emprs.getString("EFFECTIVE_YEAR")
							+ "</eff_year> ";
					xml += "<amount>" + emprs.getString("SUBSCRIPTION_AMT")
							+ "</amount> ";

					xml += "<txtGpfNo>" + emprs.getString("GPF_NO")
							+ "</txtGpfNo> ";
					
					xml += "<PROCESS_FLOW_STATUS_ID>"
							+ emprs.getString("PROCESS_FLOW_STATUS_ID")
							+ "</PROCESS_FLOW_STATUS_ID> ";
					xml += "<status>success</status> ";
					 
				}
				
				
				
				xml+="</response> ";

				
			} catch (Exception e) {
				xml = "<response><status>failure</status></response>";
				e.printStackTrace();
			}
		}

		System.out.println(xml);
		pw.write(xml);
        }

        
        }
