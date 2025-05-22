package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Scheme_Acc_mapping extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	private Connection con = null;
	public Scheme_Acc_mapping() 
	{
		super();
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		try {
			response.setContentType(CONTENT_TYPE);
			Connection con;
			Controller obj = new Controller();
			String Office_id = "", userid = "", Office_name = "";
			HttpSession session = request.getSession(false);
			userid = (String) session.getAttribute("UserId");
			PrintWriter out = response.getWriter();
			con = obj.con();
			obj.createStatement(con);
			if (userid == null) 
			{
				 response.sendRedirect(request.getContextPath()+"/index.jsp");
			}

			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
			String cmd = obj.setValue("store", request);
			String cmd1 = obj.setValue("update", request);
			String FIN_TRAN_ID = obj.setValue("finid", request);
			String ACC_HD_DR1 = obj.setValue("drac1", request);
			String ACC_HD_CR1 = obj.setValue("crac1", request);
			//System.out.println("credit"+ACC_HD_CR);
			//System.out.println("debit"+ACC_HD_DR1);
			String ACC_HD_DR=(String) ACC_HD_DR1.substring(0,6);
			String ACC_HD_CR=(String) ACC_HD_CR1.substring(0,6);
			System.out.println(ACC_HD_DR);
			String SCH_ACC_MAP_SNO = "";
			String SCH_STATUS_ID = obj.setValue("schmestatusid", request);
			String SCH_SNO = obj.setValue("sch_sel", request);
			Hashtable ht = new Hashtable();
			Hashtable ht1 = new Hashtable();

			int c = obj.getCount("PMS_SCH_ACCOUNT_MAPPING", "where SCH_SNO="+ SCH_SNO);
			String msg = "";
			if (c == 0) 
			{
				if (cmd.equalsIgnoreCase("Submit")) 
				{
					SCH_ACC_MAP_SNO = Integer.toString(obj.getMax("PMS_SCH_ACCOUNT_MAPPING", "SCH_ACC_MAP_SNO", ""));
					ht.put("SCH_ACC_MAP_SNO", SCH_ACC_MAP_SNO);
					ht.put("SCH_SNO	", SCH_SNO);
					ht.put("FIN_TRAN_ID", FIN_TRAN_ID);
					ht.put("ACC_HD_DR", ACC_HD_DR);
					ht.put("ACC_HD_CR", ACC_HD_CR);
					ht.put("UPDATED_BY_USER_ID", "'" + userid + "'");
					ht.put("SCH_STATUS_ID", SCH_STATUS_ID);
					ht.put("UPDATED_TIME", "clock_timestamp()");
System.out.println("htttttttttttttttttttttttttttttttttt"+ht);
					int row = obj.recordSave(ht, "PMS_SCH_ACCOUNT_MAPPING", con);
					if (row > 0)
						msg = "Scheme Successfully Mapped";
					else
						msg = "Scheme Not Mapped";
				}

			} else {
				msg = "Scheme Already Mapped";
			}
			if (cmd1.equalsIgnoreCase("Update")) {
				SCH_ACC_MAP_SNO = obj.setValue("msno", request);
				ht1.put("SCH_ACC_MAP_SNO", SCH_ACC_MAP_SNO);
				ht.put("SCH_SNO	", SCH_SNO);
				ht.put("FIN_TRAN_ID", FIN_TRAN_ID);
				ht.put("ACC_HD_DR", ACC_HD_DR);
				ht.put("ACC_HD_CR", ACC_HD_CR);
				ht.put("UPDATED_BY_USER_ID", "'" + userid + "'");
				ht.put("SCH_STATUS_ID", SCH_STATUS_ID);
				ht.put("UPDATED_TIME", "clock_timestamp()");

				int row = obj.recordSave(ht, ht1, "PMS_SCH_ACCOUNT_MAPPING",con);
				if (row > 0)
					msg = " Successfully Updated";
				else
					msg = " Scheme Not Updated";
			}

			response.sendRedirect("org/PMS/PMS1/DCB/jsps/sch_acc_map.jsp?msg="+ msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
