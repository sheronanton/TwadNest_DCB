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

public class applicable_scheme_type extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public applicable_scheme_type() {
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
		String type=obj.setValue("stype", request);  
		obj.createStatement(con);
		if (Integer.parseInt(flag)==1)
		{
			xml+=obj.resultXML("select sch_type_id, TRANSLATE(sch_type_desc,'&','and')  as sch_type_desc from pms_sch_lkp_type where SCH_TYPE_ID not in (select SCH_TYPE_ID from PMS_DCB_APPLICABLE_SCH_TYPE ) and   SCH_CATEGORY_ID=4 ",con,obj);
		}  
		 if (Integer.parseInt(flag)==2)
		{
			String SCH_TYPE_DESC=obj.getValue("PMS_SCH_LKP_TYPE", "SCH_TYPE_DESC", "where SCH_TYPE_ID="+type);
			String qry="insert into PMS_DCB_APPLICABLE_SCH_TYPE (SCH_TYPE_ID,SCH_TYPE_DESC,SCH_TYPE_SNO  ) values (?,?,?)";
			PreparedStatement ps=con.prepareStatement(qry);
			ps.setInt(1,Integer.parseInt(type));
			ps.setString(2, SCH_TYPE_DESC); 
			ps.setInt(3, obj.getMax("PMS_DCB_APPLICABLE_SCH_TYPE", "SCH_TYPE_SNO", ""));			
			int row_insert=ps.executeUpdate();
			xml+="<row_count>"+row_insert+"</row_count>";			
		}if (Integer.parseInt(flag)==7)
		{
			String qry="delete from PMS_DCB_APPLICABLE_SCH_TYPE  where SCH_TYPE_ID=?";
			PreparedStatement ps=con.prepareStatement(qry);
			ps.setInt(1,Integer.parseInt(type)); 
			int row_insert=ps.executeUpdate();			
			xml+="<row_count>"+row_insert+"</row_count>";	
		} 
		if (Integer.parseInt(flag)==5)
		{
			String qry=" select SCH_TYPE_ID ,TRANSLATE(sch_type_desc,'&','and') as SCH_TYPE_DESC from PMS_DCB_APPLICABLE_SCH_TYPE";
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
