package Servlets.PMS.PMS1.DCB.servlets;
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
public class OB_Change_Accept extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public OB_Change_Accept() 
    {
        super();    
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			response.setContentType(CONTENT_TYPE);
			PrintWriter pr = response.getWriter();
			Controller obj = new Controller();
			String Office_id = "";
			String userid = "";
			HttpSession session = request.getSession(false);
			userid = (String) session.getAttribute("UserId");
			if (userid == null) 
			{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			try 
			{
				Connection con=obj.con();
				String year =obj.setValue("year", request);
				Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')");
				String qry = "select OB_FREEZE from pms_dcb_ob_freeze where OFFICE_ID=? and YEAR=?";
				PreparedStatement ps=con.prepareStatement(qry);				
				ps.setInt(1, Integer.parseInt(Office_id)); 
				ps.setInt(2, Integer.parseInt(year));
				ResultSet rs=ps.executeQuery();
				String xml="<result>";
				String fstatus="";
				if (rs.next())
				{
					fstatus=rs.getString("OB_FREEZE");	
				}
				xml+="<OB_FREEZE>"+fstatus+"</OB_FREEZE>";	
				xml += "</result>";
				pr.write(xml);
				pr.flush();
				pr.close();
				
			} catch (Exception e1) 
			{
				e1.printStackTrace();
			}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
