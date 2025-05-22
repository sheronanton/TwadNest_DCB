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
public class Source_type extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public Source_type() 
	{
		super();
	}
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		Controller obj = new Controller();
		PrintWriter pr = response.getWriter();
		String method=request.getParameter("method");
		String desc = request.getParameter("desc");
        if(method.equalsIgnoreCase("add"))
		try {
			Connection con = obj.con();
			obj.createStatement(con);
			int id=obj.getMax("SOURCE_TYPE", "ID", "");
			String qry = "insert into SOURCE_TYPE (ID,DESCRIPTION) values (" + id+ ",'" + desc + "')";
			PreparedStatement ps = con.prepareStatement(qry);
			int res = ps.executeUpdate();
			String xml = "<response>";
			xml += "<row>" + res;
			xml += "</row>";
			xml += "</response>";
			pr.write(xml);
			pr.flush();
			pr.close();
			con.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		else if(method.equalsIgnoreCase("update"))
		{
			try
			{
			Connection con = obj.con();
			obj.createStatement(con);
			 String id = request.getParameter("id");
			String qry1="update SOURCE_TYPE set DESCRIPTION=? where ID=?";
			PreparedStatement ps=con.prepareStatement(qry1);
			ps.setString(1, desc);
			ps.setString(2, id);
			int res=ps.executeUpdate();
			String XML="<response>";
			XML+="<row>"+res;
			XML+="</row>";
			XML+="</response>";
			pr.write(XML);
			pr.flush();
			pr.close();
			con.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
			else if (method.equalsIgnoreCase("delete")) 
			{
				try {
					Connection con=obj.con();
					obj.createStatement(con);
					String id = request.getParameter("id");
					String qry="delete from SOURCE_TYPE where ID=?";
					PreparedStatement ps=con.prepareStatement(qry);
					ps.setString(1,id);
					int res=ps.executeUpdate();
					String XML="<response>";
					XML+="<row>"+res;
					XML+="</row>";
					XML+="</response>";
					pr.write(XML);
					pr.flush();
					pr.close();
					con.close();
				}catch (Exception e) 
				{
					e.printStackTrace();
				}
				
			}
			else if (method.equalsIgnoreCase("view")) {
				try 
				{
				Connection con=obj.con();
				String qry="select ID,DESCRIPTION from SOURCE_TYPE";
				PreparedStatement ps=con.prepareStatement(qry);
				ResultSet rs=ps.executeQuery();
				String XML="<response>";
				while(rs.next())
				{
					XML+="<stid>"+rs.getString("ID");
					XML+="</stid>";
					XML+="<DESCRIPTION>"+rs.getString("DESCRIPTION");
					XML+="</DESCRIPTION>";
				}
					XML+="</response>";
					pr.write(XML);
					pr.flush();
					pr.close();
					con.close();
				}catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
