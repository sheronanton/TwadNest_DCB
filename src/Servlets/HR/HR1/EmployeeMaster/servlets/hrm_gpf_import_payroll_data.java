package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class hrm_gpf_import_payroll_data extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection = null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * Variables Declaration
	GPF_Subscription_New_new.jsp	 */
		String strCommand = "", sql, type, pre_off_name, presid_suffix, prceed_No = "", preceed_date = "", txtSubject = "", txtRef = "", page1 = "", page2 = "", auth_name = "", auth_Desig = "";
		String xml = "";
		int count = 0, empId, leaveReq_id, year, txtOffId;
int sl=0;
String sanc_type="",process_id="";
		/**
		 * Set Content Type
		 */
		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");

		/**
		 * Session Checking
		 */
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			LoadDriver driver = new LoadDriver();
			connection = driver.getConnection();

		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
		}

		String userid = (String) session.getAttribute("UserId");
		System.out.println("session id is:" + userid);

		/**
		 * Command Parameter
		 */
		try {
			strCommand = request.getParameter("command");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String updatedby = (String) session.getAttribute("UserId");

		long l = System.currentTimeMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(l);

		// System.out.println("Enter servlet");
		
 
	if (strCommand.equalsIgnoreCase("getPayrollData")) {

			
			 System.out.println("getPayrollData............");
			try {
				
				xml = xml + "<response>";

				xml = xml + "<command>getPayrollData</command>";
				boolean flag = true;
			int	 OffId=0;
            int    accId=Integer.parseInt(request.getParameter("unit_name"));
            int    Ac_mnth=Integer.parseInt(request.getParameter("ac_month"));
              int  Ac_year=Integer.parseInt(request.getParameter("ac_year"));
              System.out.println("OffId....."+OffId+"accId..."+accId+"Ac_mnth...."+Ac_mnth+"Ac_year...."+Ac_year);
                
              String sqll="select ACCOUNTING_FOR_OFFICE_ID from  HRM_PAY_REG_BILL_PROCESS_MST  where accounting_unit_id="+accId+" and pay_process_month    ="+Ac_mnth+"  and pay_process_year     ="+Ac_year+" ";
				 System.out.println(sqll);
				Statement st1 = connection.createStatement();
				ResultSet rs1 = st1.executeQuery(sqll);
				

				while (rs1.next()) {
					
					OffId=rs1.getInt("ACCOUNTING_FOR_OFFICE_ID");

					
				}
				 System.out.println("OffId......."+OffId);
              
               	 String sql1="select * from  HRM_GPF_SUBSCRIPTIONNEW_TEMP  where accounting_unit_id="+accId+"  and ac_month="+Ac_mnth+"  and ac_year="+Ac_year+" and PROCESS_FLOW_ID='FR' ";
				 System.out.println(sql1);
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sql1);
			
				while (rs.next()) {
					if (flag) {
						xml = xml + "<flag>freezed</flag>";
						flag = false;
					}
					

					
				}
				
				 String sql11="select * from  HRM_GPF_SUBSCRIPTIONNEW_TEMP  where accounting_unit_id="+accId+"  and ac_month="+Ac_mnth+"  and ac_year="+Ac_year+" and GPF_FILE_TYPE='D' ";
				 System.out.println(sql11);
				Statement st11 = connection.createStatement();
				ResultSet rs11 = st11.executeQuery(sql11);
				

				while (rs11.next()) {
					if (flag) {
						xml = xml + "<flag>exists</flag>";
						flag = false;
					}
					

					
				}
				
				
				

				if (flag) {
					

						 String sql0="SELECT * " +
						 "FROM Hrm_Pay_Reg_Bill_Process_Mst " +
						 "WHERE Accounting_Unit_Id ="+accId+" " +
						 "AND pay_process_month    ="+Ac_mnth+" " +
						 "AND pay_process_year     ="+Ac_year+" " +
						 "AND PAY_PROCESS_STATUS_ID=1 " +
						 "AND bill_type_id         ='REG'";
					//	 System.out.println("sql2..........."+sql0);
						 PreparedStatement ps0=connection.prepareStatement(sql0);
						 ResultSet rs0=ps0.executeQuery();
						 if(rs0.next())
						 {
							 xml = xml + "<flag>nodata</flag>";
						 }
						 else
						 {
					
					 String sql2="SELECT * " +
"FROM Hrm_Pay_Reg_Bill_Process_Mst " +
"WHERE Accounting_Unit_Id ="+accId+" " +
"AND pay_process_month    ="+Ac_mnth+" " +
"AND pay_process_year     ="+Ac_year+" " +
"AND PAY_PROCESS_STATUS_ID=7 " +
"AND bill_type_id         ='REG'";
					 System.out.println("sql2..........."+sql2);
				 st = connection.createStatement();
				 rs = st.executeQuery(sql2);
				
				 if(rs.next())
				 {
					 
				 
					
					 CallableStatement callableStatement = null;
			          	String		sql3 = "{call HRM_GPF_IMPORT_PAYROLL_DATA (?,?,?,?)}";
			          			callableStatement = connection.prepareCall(sql3);          
			                    callableStatement.setInt(1,OffId);
			                   callableStatement.setInt(2,accId);
			                    callableStatement.setInt(3,Ac_year);
			                   callableStatement.setInt(4,Ac_mnth);
			                     callableStatement.execute();
			                    System.out.println("after execution..........");
					xml = xml + "<flag>updated</flag>";
				}
				 else
				 {
					 xml = xml + "<flag>nodata</flag>";
				 }
						 }
				 
				 
			}
				xml = xml + "</response>";

			} catch (Exception e) {
				xml = xml + "<response><status>failure</status></response>";
				e.printStackTrace();
			}

		}
		
	
	
	
	if (strCommand.equalsIgnoreCase("getPayrollData_withdet")) {

		
		 System.out.println("getPayrollData...for withdrawal.........");
		try {
			boolean flag = true;
		int	 OffId=0;
       int    accId=Integer.parseInt(request.getParameter("unit_name"));
       int    Ac_mnth=Integer.parseInt(request.getParameter("ac_month"));
         int  Ac_year=Integer.parseInt(request.getParameter("ac_year"));
         System.out.println("OffId....."+OffId+"accId..."+accId+"Ac_mnth...."+Ac_mnth+"Ac_year...."+Ac_year);
           
         String sqll="select ACCOUNTING_FOR_OFFICE_ID from  HRM_PAY_REG_BILL_PROCESS_MST  where accounting_unit_id="+accId+" and pay_process_month    ="+Ac_mnth+"  and pay_process_year     ="+Ac_year+" ";
			 System.out.println(sqll);
			Statement st1 = connection.createStatement();
			ResultSet rs1 = st1.executeQuery(sqll);
			

			while (rs1.next()) {
				
				OffId=rs1.getInt("ACCOUNTING_FOR_OFFICE_ID");

				
			}
			 System.out.println("OffId......."+OffId);
         
          	 String sql1="select * from  HRM_GPF_WITHDRAWALNEW_TEMP  where ACC_UNIT_ID="+accId+"  and ac_month="+Ac_mnth+"  and ac_year="+Ac_year+" and GPF_FILE_TYPE='D' ";
			 System.out.println(sql1);
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql1);
			xml = xml + "<response>";

			xml = xml + "<command>getPayrollData_withdet</command>";

			while (rs.next()) {
				if (flag) {
					xml = xml + "<flag>exists</flag>";
					flag = false;
				}
				

				
			}

			if (flag) {
				 String sql2="SELECT * " +
"FROM Hrm_Pay_Reg_Bill_Process_Mst " +
"WHERE Accounting_Unit_Id ="+accId+" " +
"AND pay_process_month    ="+Ac_mnth+" " +
"AND pay_process_year     ="+Ac_year+" " +
"AND PAY_PROCESS_STATUS_ID=7 " +
"AND bill_type_id         ='REG'";
				 System.out.println("sql2..........."+sql2);
			 st = connection.createStatement();
			 rs = st.executeQuery(sql2);
			
			 if(rs.next())
			 {
				 
			 
				
				 CallableStatement callableStatement = null;
		          	String		sql3 = "{call HRM_GPF_IMPORT_WITHDRAW_DATA (?,?,?,?)}";
		          			callableStatement = connection.prepareCall(sql3);          
		                    callableStatement.setInt(1,OffId);
		                   callableStatement.setInt(2,accId);
		                    callableStatement.setInt(3,Ac_year);
		                   callableStatement.setInt(4,Ac_mnth);
		                     callableStatement.execute();
		                    System.out.println("after execution..........");
				xml = xml + "<flag>updated</flag>";
			}
			 else
			 {
				 xml = xml + "<flag>nodata</flag>";
			 }
		}
			xml = xml + "</response>";

		} catch (Exception e) {
			xml = xml + "<response><status>failure</status></response>";
			e.printStackTrace();
		}

	}
		
		System.out.println(xml);
		pw.write(xml);
		pw.flush();
		pw.close();
	}
	 public void doPost(HttpServletRequest request, 
	            HttpServletResponse response) throws ServletException, IOException 
	            {
		 
	  	     
	}
}
