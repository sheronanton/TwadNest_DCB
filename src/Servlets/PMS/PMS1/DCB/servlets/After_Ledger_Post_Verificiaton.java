package Servlets.PMS.PMS1.DCB.servlets;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class After_Ledger_Post_Verificiaton extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public After_Ledger_Post_Verificiaton() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/xml");
		PrintWriter pw=response.getWriter();
		Controller obj=new Controller();
		Connection con=null;
		String month="",year="";
		try
		{ 
			con=obj.con();
			HttpSession session = request.getSession(false);
			String userid = (String) session.getAttribute("UserId");
			if (userid == null) 
			{
					response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			//String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	String		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
			String Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
			month=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
			year=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
			int ben_count2=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY" ,"count(distinct BENEFICIARY_SNO) ","c", "where status='L' and  OFFICE_ID="+Office_id+"  and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_TRN_MONTHLY_PR where office_id="+Office_id+"  and month="+month+" and year="+year+")"));
			String c_count1=obj.getValue("PMS_DCB_LEDGER_ACTUAL","count(distinct BENEFICIARY_SNO)","c"," where OFFICE_ID="+Office_id+" and YEAR="+year+" and MONTH="+month);
			if (Office_name.equals("")) Office_name = "";
			String res="<response><ben_count2>"+ben_count2+"</ben_count2><c_count1>"+c_count1+"</c_count1></response>";
			System.out.println(res);
			pw.write(res);
		}catch(Exception e) 
		{
			System.out.println(e);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
