package Servlets.AME.AME1.AMEM.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Pms_Ame_Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public Pms_Ame_Verification() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String res="<response>";
		int count=0;
		Controller obj = new Controller();
		try
		{
		
			response.setContentType(CONTENT_TYPE);
			Connection con =obj.con();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			String userid = (String) session.getAttribute("UserId");
			if (userid == null) 
			{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			
			Hashtable ht = new Hashtable();
			Hashtable cnd = new Hashtable();
			
			int process_code=Integer.parseInt(obj.setValue("process_code", request));
			int p_month=Integer.parseInt(obj.setValue("p_month", request));
			int p_year=Integer.parseInt(obj.setValue("p_year", request));
			int p_sch_sno=Integer.parseInt(obj.setValue("p_sch_sno", request));
			int ins_row = 0;
			int cnt=obj.getCount("PMS_AME_TRN_APPROVAL","where OFFICE_ID="+Office_id+" and month="+p_month+" and year="+p_year+" and sch_sno="+p_sch_sno);
			if (cnt==0)
			{	
					ht.put("OFFICE_ID", Office_id);
					ht.put("SCH_SNO", p_sch_sno); 
					ht.put("VERIFY_EXP", "'Y'");
					ht.put("MONTH", p_month);
					ht.put("YEAR", p_year); 
					ht.put("UPDATED_BY_USER_ID", "'" + userid + "'");
					ht.put("UPDATED_TIME", "clock_timestamp()");
		
					
				//	ins_row= obj.delRecord("PMS_AME_TRN_APPROVAL", "where OFFICE_ID="+Office_id+" and month="+p_month+" and year="+p_year+" and sch_sno="+p_sch_sno, con,1);
					ins_row = obj.recordSave(ht,"PMS_AME_TRN_APPROVAL", con);
					res+= " <ins_row>" + ins_row+ "</ins_row> ";
					count=ins_row;
			}
			else
			{
				ht.put("VERIFY_EXP", "'Y'");
				ht.put("UPDATED_BY_USER_ID", "'" + userid + "'");
				ht.put("UPDATED_TIME", "clock_timestamp()");				
				ht.put("VERIFY_EXP", "'Y'");
				cnd.put("OFFICE_ID", Office_id);
				cnd.put("SCH_SNO", p_sch_sno); 
				cnd.put("MONTH", p_month);
				cnd.put("YEAR", p_year);
				ins_row = obj.recordSave(ht,cnd,"PMS_AME_TRN_APPROVAL", con);
				res+= " <ins_row>" + ins_row+ "</ins_row> ";
				count=ins_row;  
			}
			
			 
		}catch(Exception e)
		{
			System.out.println(e);
			//response.sendRedirect(request.getContextPath() + "/index.jsp");
			 
		}
		res+="<count>"+count+"</count></response>";
		obj.resposeWr(response, res); 
		
	}

}
