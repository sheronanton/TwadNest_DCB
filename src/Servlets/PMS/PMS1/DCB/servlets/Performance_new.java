package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Performance
 */
public class Performance_new extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Performance_new() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Controller obj = new Controller();
		response.setContentType(CONTENT_TYPE);
		PrintWriter pr = response.getWriter();
		String method = request.getParameter("method");
		int dlim = 0, dord = 0, dtype = 0;
		String desc = "",flag= "" ,unit= "",ronly= "";
		HttpSession session = request.getSession(false);
		String userid = "0", Office_id = "0",Formula="",astatus="";
		try {
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) {
			 
		}

		if (userid == null) {
		 
			 response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
					
		String qry,man="";
		if (method.equalsIgnoreCase("add"))
			try {
					 desc = request.getParameter("desc");
					 
					 desc=desc.replace('#', '%');
					 flag = request.getParameter("flag");
					 unit = request.getParameter("unit");
					 ronly = request.getParameter("ronly");
					 dlim = Integer.parseInt(request.getParameter("dlim"));
					 dord = Integer.parseInt(request.getParameter("dord"));
					 dtype = Integer.parseInt(request.getParameter("dtype"));
					 Formula=obj.setValue("Formula", request);
					 astatus=obj.setValue("astatus", request);
					 man=obj.setValue("man", request);
				Connection con = obj.con();
				obj.createStatement(con);
				int id = obj.getMax("PMS_AME_MST_DESC","PERFORM_DESC_SNO", "");
				userid = "";
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				qry = "insert into PMS_AME_MST_DESC (PERFORM_DESC_SNO,PERFORM_DESC,FYEAR_MTH_FLAG,UPDATED_BY_USER_ID,UPDATED_TIME,UNITS,READONLY,DLIMIT,DISPLAYORDER,DATATYPE,Formula,ACTIVE_STATUS,MANDATORY) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(qry);
				ps.setInt(1, id);
				ps.setString(2, desc);
				ps.setString(3, flag.toUpperCase());
				ps.setString(4, userid);
				ps.setTimestamp(5, sqlTimestamp);
				ps.setString(6, unit);
				ps.setString(7, ronly.toUpperCase());
				ps.setInt(8, dlim);
				ps.setInt(9, dord);
				ps.setInt(10, dtype);
				ps.setString(11, Formula);
				ps.setString(12, astatus);
				ps.setString(13, man);
				
				int res = ps.executeUpdate();

				String xml = "<response>"; 
				xml += "<row>" + res;
				xml += "</row>";
				xml += "</response>";
				pr.write(xml);
				pr.flush();
                pr.close();
				con.close();

			} catch (Exception e) {
				
				e.printStackTrace();
			}
			else if (method.equalsIgnoreCase("update"))
			try {
				 desc = obj.setValue("desc", request);
				 desc=desc.replace("*", "%");       
				 flag = obj.setValue("flag", request);    
				 unit = obj.setValue("unit", request);
				 ronly = obj.setValue("ronly", request);
				 dlim = Integer.parseInt(obj.setValue("dlim", request));
				 dord = Integer.parseInt(obj.setValue("dord", request));
				 dtype = Integer.parseInt(obj.setValue("dtype", request));
				 Formula=obj.setValue("Formula", request);
				 astatus=obj.setValue("astatus", request);
				 man=obj.setValue("man", request);
				Connection con = obj.con();  
				obj.createStatement(con);
				String sno1 =request.getParameter("ssno");
				userid = "";
				java.util.Date date = new java.util.Date();
				long t = date.getTime();
				java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
				qry = "update  PMS_AME_MST_DESC set PERFORM_DESC=?,FYEAR_MTH_FLAG=?,UPDATED_BY_USER_ID=?,UPDATED_TIME=?,UNITS=?,READONLY=?,DLIMIT=?,DISPLAYORDER=?,DATATYPE=? ,Formula=? ,ACTIVE_STATUS=?,MANDATORY=? where PERFORM_DESC_SNO=?";

				PreparedStatement ps = con.prepareStatement(qry);
			
				ps.setString(1, desc);
				ps.setString(2, flag.toUpperCase());
				ps.setString(3, userid);
				ps.setTimestamp(4, sqlTimestamp);
				ps.setString(5, unit);
				ps.setString(6, ronly.toUpperCase());
				ps.setInt(7, dlim);
				ps.setInt(8, dord);
				ps.setInt(9, dtype);
				ps.setString(10, Formula);
				ps.setString(11, astatus);
				ps.setString(12, man);
				ps.setString(13, sno1);
				int res = ps.executeUpdate();

				String xml = "<response>";
				xml += "<row>" + res;
				xml += "</row>";
				xml += "</response>";
				pr.write(xml);
				pr.flush();
				pr.close();
				con.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			else if (method.equalsIgnoreCase("view"))
			{
				try 
				{
						System.out.println("*****************");
					    Connection con=obj.con();
					    qry="select * from PMS_AME_MST_DESC  order by   DISPLAYORDER"; 
						PreparedStatement ps=con.prepareStatement(qry);
						ResultSet rs=ps.executeQuery();
						String XML="<response>";  
						while(rs.next())
						{
							XML+="<PERFORM_DESC_SNO>"+rs.getString("PERFORM_DESC_SNO")+"</PERFORM_DESC_SNO>";
							XML+="<PERFORM_DESC>"+rs.getString("PERFORM_DESC")+"</PERFORM_DESC>";
							XML+="<FYEAR_MTH_FLAG>"+rs.getString("FYEAR_MTH_FLAG")+"</FYEAR_MTH_FLAG>";
							XML+="<UPDATED_BY_USER_ID>"+rs.getString("UPDATED_BY_USER_ID")+"</UPDATED_BY_USER_ID>";				 
							XML+="<UNITS>"+rs.getString("UNITS")+"</UNITS>";
							XML+="<READONLY>"+rs.getString("READONLY")+"</READONLY>";
							XML+="<DLIMIT>"+rs.getString("DLIMIT")+"</DLIMIT>";
							XML+="<DISPLAYORDER>"+rs.getString("DISPLAYORDER")+"</DISPLAYORDER>";
							XML+="<DATATYPE>"+rs.getString("DATATYPE")+"</DATATYPE>";
							XML+="<FORMULA>"+rs.getString("FORMULA")+"</FORMULA>";
							XML+="<ACTIVE_STATUS>"+rs.getString("ACTIVE_STATUS")+"</ACTIVE_STATUS>";  
							XML+="<MANDATORY>"+rs.getString("MANDATORY")+"</MANDATORY>";  
						}
						XML+="</response>";
						pr.write(XML);
						con.close();
				} 
				catch (Exception e) 
				{
					System.out.println(e);
				}
			  }
				else if (method.equalsIgnoreCase("delete")) {
					try 
					{	Connection con=obj.con();
					obj.createStatement(con);
					 String ssno = request.getParameter("ssno");
					 qry="delete from PMS_AME_MST_DESC where PERFORM_DESC_SNO=?";
					PreparedStatement ps=con.prepareStatement(qry);
					ps.setString(1,ssno);
					int res=ps.executeUpdate();
					System.out.println(qry);
					String XML="<response>";
					XML+="<row>"+res;
					XML+="</row>";
					XML+="</response>";
					pr.write(XML);
					con.close();
					} 
						catch (Exception e) {
							// TODO Auto-generated catch block
							System.out.println(e);
						}	}
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
