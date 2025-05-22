package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class Servlet_Load_Minor
 */
public class Servlet_Load_Minor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	//  private static final String CONTENT_TYPE =
		   //     "text/xml; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet_Load_Minor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 Connection connection = null;
	        PreparedStatement ps = null,ps1=null;
	        ResultSet res = null,rs1=null;
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        String xml = "";

	        try {

	        	LoadDriver driver=new LoadDriver();
	        	connection=driver.getConnection();

	        } catch (Exception e) {
	            System.out.println("Exception in openeing connection:" + e);
	        }
	        
	       
	        
	        String command=request.getParameter("cammand");
	        System.out.println("command------->"+command);
	        if(command.equalsIgnoreCase("load_Minor"))
	        {
	        	int c=0,count=0;
	        	xml+="<response><command>load_Minor</command>";
	        	int issue_id=Integer.parseInt(request.getParameter("issue_id"));
	        	  String sql1 =
	                      "select a.ISSUE_REPORTED_DATE,a.last_Updated_date,a.ISSUE_PRIORITY,a.ISSUE_SOLUTION,b.MAJOR_SYSTEM_ID,b.major_system_desc,c.MINOR_SYSTEM_ID,c.minor_system_desc,a.ISSUE_TITLE,a.ISSUE_DESC,a.ISSUE_STATUS,a.REPORTED_BY_USER_ID,a.office_id from HLP_ISSUE_REQUESTS a,sec_mst_major_systems b,sec_mst_minor_systems c where a.major_system_id=b.major_system_id and a.minor_system_id=c.minor_system_id and a.issue_id=?";
	        	  try {
						ps1=connection.prepareStatement(sql1);
						ps1.setInt(1, issue_id);
						rs1=ps1.executeQuery();
						while(rs1.next())
						{
							c++;
							xml+="<MINOR_SYSTEM_ID_1>"+rs1.getString("MINOR_SYSTEM_ID")+"</MINOR_SYSTEM_ID_1>";
							xml+="<MINOR_SYSTEM_DESC_1>"+rs1.getString("minor_system_desc")+"</MINOR_SYSTEM_DESC_1>";
						}
						xml+="<count_1>"+c+"</count_1>";
						if(c!=0)
						{
							xml+="<flag1>success1</flag1>";
						}
						else
						{
							xml+="<flag1>failure1</flag1>";
						}
	        	  }
	        	  catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	
	        	
	        	String major_id=request.getParameter("major_id");
	        	String sql="SELECT MINOR_SYSTEM_ID, " +
	        			"  MINOR_SYSTEM_DESC " +
	        			"FROM sec_mst_minor_systems " +
	        			"WHERE MAJOR_SYSTEM_ID=?";
	        	try {
					ps=connection.prepareStatement(sql);
					ps.setString(1,major_id );
					res=ps.executeQuery();
					while(res.next())
					{
						count++;
						xml+="<MINOR_SYSTEM_ID>"+res.getString("MINOR_SYSTEM_ID")+"</MINOR_SYSTEM_ID>";
						xml+="<MINOR_SYSTEM_DESC>"+res.getString("MINOR_SYSTEM_DESC")+"</MINOR_SYSTEM_DESC>";
					}
					xml+="<count>"+count+"</count>";
					if(count!=0)
					{
						xml+="<flag>success</flag></response>";
					}
					else
					{
						xml+="<flag>failure</flag></response>";
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        	//xml+="</response>";
	        	 out.write(xml);
	             System.out.println("the xml is" + xml);
	             out.close();
	        	System.out.println("XML === "+xml);
	        	
	        }
	        if(command.equalsIgnoreCase("Loadchangereq"))
	        {
	        	int c=0,count=0;
	        	xml="<response><command>Load</command>";
	        	int issue_id=Integer.parseInt(request.getParameter("req_slno"));
	        	  String sql1 ="SELECT a.CHANGE_REQUEST_ID, " +
	        	  "  to_char(a.REQUEST_DATE,'dd/mm/yyyy') as REQUEST_DATE, " +
	        	  "  a.OFFICE_ID, " +
	        	  "  a.EMPLOYEE_ID, " +
	        	  "  a.REQUEST_TYPE, " +
	        	  "  a.MAJOR_SYSTEM_ID, " +
	        	  "  a.MINOR_SYSTEM_ID, " +
	        	  "  a.SUB_SYSTEM_ID, " +
	        	  "  a.MENU_ID, " +
	        	  "  a.CHANGE_REQUEST_DESC, " +
	        	  "  a.ASSIGNED_TO, " +
	        	  "  a.PHONE_NO, " +
	        	  "  b.ATTACH_SLNO, " +
	        	  "  b. FILE_CONTENT, " +
	        	  "  b. FILE_NAME, " +
	        	  "  office_name, " +
	        	  "  employee_name " +
	        	  "FROM " +
	        	  "  (SELECT CHANGE_REQUEST_ID, " +
	        	  "    REQUEST_DATE, " +
	        	  "    OFFICE_ID, " +
	        	  "    EMPLOYEE_ID, " +
	        	  "    REQUEST_TYPE, " +
	        	  "    MAJOR_SYSTEM_ID, " +
	        	  "    MINOR_SYSTEM_ID, " +
	        	  "    SUB_SYSTEM_ID, " +
	        	  "    MENU_ID, " +
	        	  "    CHANGE_REQUEST_DESC, " +
	        	  "    ASSIGNED_TO, " +
	        	  "    DATE_INITIATED, " +
	        	  "    DATE_CLOSED, " +
	        	  "    CONTACT_PERSON, " +
	        	  "    PHONE_NO " +
	        	  "  FROM HLP_CHANGE_REQUEST_MST " +
	        	  "  WHERE CHANGE_REQUEST_ID=? " +
	        	  "  )a " +
	        	  "LEFT OUTER JOIN " +
	        	  "  (SELECT CHANGE_REQUEST_ID, " +
	        	  "    ATTACH_SLNO, " +
	        	  "    UPDATED_DATE, " +
	        	  "    UPDATEDBY_USER_ID, " +
	        	  "    PROCESS_FLOW_STATUS_ID, " +
	        	  "    FILE_CONTENT, " +
	        	  "    FILE_NAME " +
	        	  "  FROM HLP_CHANGE_REQUEST_TRN " +
	        	  "  )b " +
	        	  "ON a.CHANGE_REQUEST_ID=b.CHANGE_REQUEST_ID " +
	        	  "LEFT OUTER JOIN " +
	        	  "  ( SELECT office_id,office_name FROM com_mst_offices " +
	        	  "  )c " +
	        	  "ON c.office_id=a.office_id " +
	        	  "LEFT OUTER JOIN " +
	        	  "  (SELECT employee_id, " +
	        	  "    EMPLOYEE_INITIAL " +
	        	  "    ||'.' " +
	        	  "    ||employee_name AS employee_name " +
	        	  "  FROM hrm_mst_employees " +
	        	  "  )d " +
	        	  "ON d.employee_id=a.employee_id " +
	        	  "LEFT OUTER JOIN " +
	        	  "  ( SELECT office_id,office_name FROM com_mst_offices " +
	        	  "  )e " +
	        	  "ON e.office_id=a.office_id";

	        	  try {
						ps1=connection.prepareStatement(sql1);
						ps1.setInt(1, issue_id);
						rs1=ps1.executeQuery();
						while(rs1.next())
						{
							c=1;
							
							xml+="<REQUEST_DATE>"+rs1.getString("REQUEST_DATE")+"</REQUEST_DATE>";
							xml+="<OFFICE_ID>"+rs1.getString("OFFICE_ID")+"</OFFICE_ID>";
							xml+="<EMPLOYEE_ID>"+rs1.getString("EMPLOYEE_ID")+"</EMPLOYEE_ID>";
							xml+="<REQUEST_TYPE>"+rs1.getString("REQUEST_TYPE")+"</REQUEST_TYPE>";
							xml+="<MAJOR_SYSTEM_ID>"+rs1.getString("MAJOR_SYSTEM_ID")+"</MAJOR_SYSTEM_ID>";
							xml+="<MINOR_SYSTEM_ID>"+rs1.getString("MINOR_SYSTEM_ID")+"</MINOR_SYSTEM_ID>";
							xml+="<SUB_SYSTEM_ID>"+rs1.getString("SUB_SYSTEM_ID")+"</SUB_SYSTEM_ID>";
							xml+="<MENU_ID>"+rs1.getString("MENU_ID")+"</MENU_ID>";
							xml+="<CHANGE_REQUEST_DESC>"+rs1.getString("CHANGE_REQUEST_DESC")+"</CHANGE_REQUEST_DESC>";
							xml+="<ASSIGNED_TO>"+rs1.getString("ASSIGNED_TO")+"</ASSIGNED_TO>";
							xml+="<PHONE_NO>"+rs1.getString("PHONE_NO")+"</PHONE_NO>";
							xml+="<ATTACH_SLNO>"+rs1.getString("ATTACH_SLNO")+"</ATTACH_SLNO>";
							xml+="<FILE_NAME>"+rs1.getString("FILE_NAME")+"</FILE_NAME>";
							xml+="<office_name>"+rs1.getString("office_name")+"</office_name>";
							xml+="<employee_name>"+rs1.getString("employee_name")+"</employee_name>";
						}
					     System.out.println("xml------->"+xml);
						if(c!=0)
						{
							xml+="<flag1>success</flag1></response>";
						}
						else
						{
							xml+="<flag1>failure</flag1></response>";
						}
	        	  }
	        	  catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	
	        	
	        
	        	//xml+="</response>";
	        	 out.write(xml);
	             System.out.println("the xml is" + xml);
	             out.close();
	        	System.out.println("XML === "+xml);
	        	
	        }
	        
	        if(command.equalsIgnoreCase("update"))
	        {
	        	int issue_id=Integer.parseInt(request.getParameter("issue_id"));
	        	String major_id=request.getParameter("major_id");
	        	String minor_id=request.getParameter("minor_id");
	        	String sql="UPDATE hlp_issue_requests " +
	        			"SET major_system_id=?, " +
	        			"  MINOR_SYSTEM_ID  =? " +
	        			"WHERE ISSUE_ID     =?" ;
	        	
	        	int count=0;
	        	xml+="<response><command>update</command>";
	        	
	        	try {
					ps=connection.prepareStatement(sql);
					ps.setString(1, major_id);
					ps.setString(2, minor_id);
					ps.setInt(3, issue_id);
					ps.executeUpdate();
					xml+="<flag>success</flag></response>";
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					xml+="<flag>failure</flag></response>";
					e.printStackTrace();
				}
	        	 out.write(xml);
	             System.out.println("the xml is" + xml);
	             out.close();
	        	System.out.println("XML === "+xml);
	        }
	        
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
