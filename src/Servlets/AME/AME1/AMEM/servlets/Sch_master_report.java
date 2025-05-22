package Servlets.AME.AME1.AMEM.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Sch_master_report
 */
public class Sch_master_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sch_master_report() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//type = office_level
		String office_level="", res="";
		 try {
			 	Controller obj = new Controller();
				Connection con = null;
				con = obj.con();
				obj.createStatement(con);
				office_level = obj.setValue("type", request);
				
				HttpSession session = request.getSession(false);
				String userid = (String) session.getAttribute("UserId");
				if (userid == null) {

					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				
				String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
				
				System.out.println("AME-------->REPORT------TYPE("+office_level+")");
				
				if (office_level.equalsIgnoreCase("RN")) 
				{
					res=obj.resultXML("SELECT REGION_OFFICE_ID, REGION_OFFICE_NAME FROM COM_MST_REGIONS_HVIEW", con, obj);
				}
				else if(office_level.equalsIgnoreCase("CL"))  
				{
					String  roQueryString=obj.setValue("roQueryString", request);
					String ro_cl = roQueryString.substring(0, roQueryString.length()-1);
					
					res += obj.resultXML("SELECT CIRCLE_OFFICE_ID,  CIRCLE_OFFICE_NAME FROM COM_MST_CIRCLES_HVIEW " 
							+"WHERE REGION_OFFICE_ID IN ( "+ro_cl+" )ORDER BY REGION_OFFICE_ID", con, obj);
				}
				else if (office_level.equalsIgnoreCase("DT")) 
				{
					res=obj.resultXML("SELECT WORK_NATURE_ID, WORK_NATURE_DESC FROM COM_MST_WORK_NATURE ORDER BY WORK_NATURE_DESC", con, obj);
				}
				else if (office_level.equalsIgnoreCase("DN")) 
				{
					String coQueryString=obj.setValue("coQueryString", request);
					String otQueryString=obj.setValue("otQueryString", request);
					String co_tmp = coQueryString.substring(0, coQueryString.length()-1);
					String ot_tmp = otQueryString.substring(0, otQueryString.length()-1);
					
					//System.out.println(coQueryString+"========"+co_tmp+">>>>>>>>"+otQueryString+"---------"+ot_tmp);
					
					res += obj.resultXML("SELECT A.DIVISION_OFFICE_ID,  A.DIVISION_OFFICE_NAME FROM COM_MST_DIVISIONS_HVIEW A " 
							+"INNER JOIN COM_MST_OFFICES B ON A.DIVISION_OFFICE_ID   = B.OFFICE_ID " 
							+"WHERE A.CIRCLE_OFFICE_ID IN ( "+ co_tmp +" ) " 
							+"AND B.PRIMARY_WORK_ID    IN ( "+ ot_tmp +") " 
							+"ORDER BY A.CIRCLE_OFFICE_ID, A.DIVISION_OFFICE_NAME" , con, obj);
					
				}
				obj.resposeWr(response, res);
	}
		 catch(Exception e)
		 {}

	}
}
