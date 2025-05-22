package Servlets.PMS.PMS1.DCB.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;  
public class json_servlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public json_servlet() 
	{
		super();
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
 		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String xml="";
		Controller obj=new Controller();
		try {
			con=obj.con();  

			stmt=con.createStatement();
			obj.createStatement(con);
			HttpSession session=request.getSession(false);
			String process = request.getParameter("pr");// Command
			String sub_div = request.getParameter("value");// Command
			String userid="0",Office_id="0";
			try
			{
			  userid=(String)session.getAttribute("UserId");
			}catch(Exception e) {userid="0";}

			if(userid==null)
	        {
				userid="0";
		        response.sendRedirect(request.getContextPath()+"/index.jsp");
	        }
			
			Office_id=(obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')"),1));
			
			 if (Office_id.equals("0") ) Office_id="0";
			 if (process.equalsIgnoreCase("1"))
			xml=obj.combo_lkup("OFFICE_ID", "OFFICE_NAME", "com_mst_all_offices_view", "where DIVISION_OFFICE_ID="+Office_id+" and OFFICE_LEVEL_ID='SD'",2,"--Select--"); // for division
			 else if (process.equalsIgnoreCase("2"))
					xml=obj.combo_lkup("BEN_TYPE_ID", "BEN_TYPE_DESC", "PMS_DCB_BEN_TYPE", "where BEN_TYPE_ID in (select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where meter_status='L'  ) order by BEN_TYPE_ID",2,"--Select Beneficiary Type--");
			 
			 
			PrintWriter pr=response.getWriter();
			pr.write(xml);
	        pr.flush();
	        pr.close();
	        obj.conClose(con);
		}catch (Exception e) {
			 
		}
			
		 
		
		 
		
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
	}

}
