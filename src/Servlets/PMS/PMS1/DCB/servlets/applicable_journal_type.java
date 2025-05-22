package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

public class applicable_journal_type extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public applicable_journal_type() {
		super();
	}
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost( request,response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj = new Controller();
		response.setContentType(CONTENT_TYPE);
		PrintWriter pr = response.getWriter();
		Connection con = null;
		Statement stmt = null;
		try {
			con = obj.con();
			stmt = con.createStatement();		
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null) 
		{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
				
		String xml = "<result>";		
		String flag=obj.setValue("flag", request);
		String type=obj.setValue("jtype", request); 
 
		if (Integer.parseInt(flag)==1)
		{
			xml+=obj.resultXML("select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC from FAS_MST_JOURNAL_TYPE where JOURNAL_TYPE_CODE not in (select JOURNAL_TYPE_CODE from PMS_DCB_APPLICABLE_JOU_TYPE ) ",con,obj);
		} 
		 if (Integer.parseInt(flag)==2)
		{
			String CATEGORY=obj.getValue("FAS_MST_JOURNAL_TYPE", "CATEGORY", "where JOURNAL_TYPE_CODE="+type);
			String DISPLAY_RESTRICTED=obj.getValue("FAS_MST_JOURNAL_TYPE", "DISPLAY_RESTRICTED", "where JOURNAL_TYPE_CODE="+type);
			String JOURNAL_TYPE_DESC=obj.getValue("FAS_MST_JOURNAL_TYPE", "JOURNAL_TYPE_DESC", "where JOURNAL_TYPE_CODE="+type);
		
			System.out.println( "CATEGORY"+CATEGORY);
			System.out.println( "DISPLAY_RESTRICTED"+DISPLAY_RESTRICTED);
			System.out.println( "JOURNAL_TYPE_DESC"+JOURNAL_TYPE_DESC);
			System.out.println( "flag"+flag);
			System.out.println( "type"+type);

			
			
			String qry="insert into PMS_DCB_APPLICABLE_JOU_TYPE (JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC,DISPLAY_RESTRICTED,CATEGORY,CONDITION,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,'"+userid+"',clock_timestamp())";
			PreparedStatement ps=con.prepareStatement(qry);
			ps.setInt(1,Integer.parseInt(type));
			ps.setString(2, JOURNAL_TYPE_DESC); 
			ps.setString(3, DISPLAY_RESTRICTED);			
			ps.setString(4, CATEGORY);
			ps.setInt(5, 1);
			int row_insert=ps.executeUpdate();
			ps=con.prepareStatement(qry);
			ps.setInt(1,Integer.parseInt(type));
			ps.setString(2, JOURNAL_TYPE_DESC); 
			ps.setString(3, DISPLAY_RESTRICTED);			
			ps.setString(4, CATEGORY);
			ps.setInt(5, 2);
			    
			row_insert=ps.executeUpdate();			
			xml+="<row_count>"+row_insert+"</row_count>";			
		}if (Integer.parseInt(flag)==7)
		{
			String qry="delete from PMS_DCB_APPLICABLE_JOU_TYPE  where JOURNAL_TYPE_CODE=?";
			PreparedStatement ps=con.prepareStatement(qry);
			ps.setInt(1,Integer.parseInt(type));
			int row_insert=ps.executeUpdate();			
			xml+="<row_count>"+row_insert+"</row_count>";	
		} 
		if (Integer.parseInt(flag)==5)
		{
			String qry="select distinct a.JOURNAL_TYPE_CODE,a.JOURNAL_TYPE_DESC,b.display_restricted,b.category from FAS_MST_JOURNAL_TYPE  a , PMS_DCB_APPLICABLE_JOU_TYPE b " 
				 +" where a.journal_type_code=b.journal_type_code order by a.JOURNAL_TYPE_CODE";
			System.out.println(qry); 
			xml+=obj.resultXML(qry,con,obj);
		}	    

		xml += "</result>";	 
		pr.write(xml);
		pr.flush(); 
		System.out.println( xml);
		pr.close();
		obj.conClose(con);
		}catch (Exception e)  
		{
			System.out.println( e);
		}
	}

}
