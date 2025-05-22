package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class Emp_Details
 */
public class Emp_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Emp_Details() {
        super();
        // TODO Auto-generated constructor stub
    }
    PreparedStatement ps,ps2;
    ResultSet rs = null,rs2=null;
    Connection con = null,con1=null;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("WELCOME");
		try 
		{

			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
		//			+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
			con1 = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
		}
 System.out.println("**********************************");
 System.out.println("**********************************");
 System.out.println("**********************************");
	 String xml="";
	 PrintWriter pw=response.getWriter();
	 response.setContentType("text/xml");
	// PrintWriter out = response.getWriter();
	 response.setHeader("Cache-Control","no-cache");  
	 HttpSession session=request.getSession(false);
	 
	 
	 try
	 {
	         if(session==null)
	         {
	                 xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
	                 pw.println(xml);
	                 System.out.println(xml);
	                 pw.close();
	                 return;
	          }
	 }catch(Exception e)
	 {
	        e.printStackTrace();
	 }
	 session =request.getSession(false);
	 String updatedby=(String)session.getAttribute("UserId");
	 
	 long l=System.currentTimeMillis();
	 java.sql.Timestamp ts=new java.sql.Timestamp(l);
	 int c=0,c1=0;
	 
	 String command=request.getParameter("command");
	 String empid=request.getParameter("empid");
	 System.out.println(command);
	 if(command.equalsIgnoreCase("insert"))
	 {
	 
		 xml="<response><command>Get_details</command>";
		
	 String sql="SELECT employee_id,EMPLOYEE_PREFIX, " +
			 "  EMPLOYEE_INITIAL, " +
			 "  EMPLOYEE_NAME, " +
			 "  GPF_NO " +
			 "FROM hrm_mst_employees " +
			 "WHERE employee_id=?"   ;
	 try {
		ps=con.prepareStatement(sql);
		 ps.setString(1, empid);
			rs= ps.executeQuery();
				if(rs.next())
				{
					 xml+="<status>Get_details</status>";
					c++;
					xml+="<EMPLOYEE_PREFIX>"+rs.getString("EMPLOYEE_PREFIX")+"</EMPLOYEE_PREFIX>";
					xml+="<EMPLOYEE_INITIAL>"+rs.getString("EMPLOYEE_INITIAL")+"</EMPLOYEE_INITIAL>";
					xml+="<EMPLOYEE_NAME>"+rs.getString("EMPLOYEE_NAME")+"</EMPLOYEE_NAME>";
					xml+="<GPF_NO>"+rs.getInt("GPF_NO")+"</GPF_NO>";
				}
				else
				{
					 xml+="<status>failure_empid</status>";
				}
				if(c!=0)
				{
					String sqls="SELECT a.employee_id, " +
							"  b.PROCESS_FLOW_STATUS_ID, " +
							"  b.EMPLOYEE_ID " +
							"FROM hrm_mst_employees a " +
							"INNER JOIN hrm_emp_addl_photo_new_tmp b " +
							"ON a.employee_id =b.employee_id " +
							"AND b.employee_id=?"  ;
					ps2=con.prepareStatement(sqls);
					ps2.setString(1, empid);
					rs2=ps2.executeQuery();
					while(rs2.next())
					{
						c1++;
						xml+="<PROCESS_FLOW_STATUS_ID>"+rs2.getString("PROCESS_FLOW_STATUS_ID")+"</PROCESS_FLOW_STATUS_ID>";
					}
					
				}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		xml+="<count>"+c+"</count>";
		xml+="<counting>"+c1+"</counting>";
		xml+="</response>";
		System.out.println("XML ==== "+xml);
		//pw.println(xml);
		pw.write(xml);
		
		pw.close();
	 }
	 
	 if(command.equalsIgnoreCase("delete"))
	 {
		 System.out.println("DELETE");
		 xml="<response><command>delete</command>";
		 xml+="<status>delete</status>";
		 String sql="delete from hrm_emp_addl_photo_new_tmp where EMPLOYEE_ID=?";
		 try {
			ps=con.prepareStatement(sql);
			ps.setString(1,empid);
			ps.executeUpdate();
			xml+="<flag>success</flag>";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure</flag>";
		}
		 xml+="</response>";
		 System.out.println("XML ==== "+xml);
		 pw.write(xml);
			
			pw.close();
	 }
		 
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
