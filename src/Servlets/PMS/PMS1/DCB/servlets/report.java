/* 
 * Copyright 2006 (C) NIC  
 *  
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 20/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description
 * 17/09/2011		Add the Beneficiary Status to 'L'  if
 * 20/09/2011		Add the Meter Status to 'L' if 
 *---------|---------------|--------------------------------------------------- 
 */
  
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

public class report extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	String meter_status = Controller.meter_status;

	public report() 
	{
		super();
	}

	String new_cond = Controller.new_cond;
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		Controller obj = new Controller();
		String command = request.getParameter("command");// Command
														// ->view,insert etc
		if (command == null || command.equals(""))
			command = "no command";

		String input_value = request.getParameter("input_value"); // input value
		if (input_value == null || input_value.equals(""))
			input_value = "0";

		String process_code = request.getParameter("process_code");// process
																	// code 1->
																	// Ben
																	// Select
																	// ,2->Meter
																	// Select
		if (process_code == null || process_code.equals(""))
			process_code = "0";

		obj.testQry("DCB->report->command->" + command + "->process_code->"+ process_code);
		String xml = " ";
		try {
			con = obj.con();
			stmt = con.createStatement();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			String userid = "0", Office_id = "0";
			try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {
				userid = "0";
			}

			if (userid == null) 
			{
				userid = "0";
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		//	Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')"), 1));
			//Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING","OLD_OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')"), 1));
			
			
			if (Office_id.equals("0"))
				Office_id = "0";
			/**************************************************************************************************/

			String Child_Div=obj.setValue("Child_div", request);
			
			if (process_code.equals("1"))
				xml = obj.combo_lkup("DISTRICT_CODE","DISTRICT_NAME","COM_MST_DISTRICTS","WHERE DISTRICT_CODE IN (SELECT DISTRICT_CODE FROM PMS_DCB_DIV_DIST_MAP WHERE OFFICE_ID="+ Office_id
										+ ") order by DISTRICT_NAME", 2,"--Select--");
			if (process_code.equals("2"))
				xml = obj.combo_lkup("block_sno", "block_name","com_mst_blocks", "where district_code =" + input_value
								+ " order by block_name", 2, "--Select--");

			if (process_code.equals("3"))
				xml = obj.combo_lkup("BEN_TYPE_ID", "BEN_TYPE_DESC","PMS_DCB_BEN_TYPE", "order by BEN_TYPE_ID", 2,"--Select Beneficiary Type--");

			if (process_code.equals("4"))
			{
				if ("0".equalsIgnoreCase(Child_Div))
				{	xml = obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME","PMS_DCB_MST_BENEFICIARY", "where " + new_cond+ " BENEFICIARY_TYPE_ID =" + input_value+ " and OFFICE_ID=" + Office_id+ " order by BENEFICIARY_NAME", 2,"--Select Beneficiary--"); }
				else
				{	xml = obj.combo_lkup("BENEFICIARY_SNO", "BENEFICIARY_NAME","PMS_DCB_MST_BENEFICIARY", "where " + new_cond+ " BENEFICIARY_TYPE_ID =" + input_value+ " and OFFICE_ID_BEN="+ Child_Div+" order by BENEFICIARY_NAME", 2,"--Select Beneficiary--"); }
				 
			}
			if (process_code.equals("5"))
			{
		//		xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME","com_mst_all_offices_view", "where DIVISION_OFFICE_ID="+ Office_id + " and OFFICE_LEVEL_ID='SD'", 2,"--Select--"); // for division
			//	xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME","com_mst_all_offices_view", "where OFFICE_ID   IN( SELECT DISTINCT SUB_DIV_ID FROM PMS_DCB_MST_BENEFICIARY_METRE where "+ meter_status+ " office_id ="+ Office_id+" ) and OFFICE_LEVEL_ID='SD'", 2,"--Select--"); // for division
				//String div=obj.setValue("div", request);
				String div=Office_id;
				if ("0".equals(div) || div==null) div=Office_id;
				xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME","com_mst_all_offices_view","where DIVISION_OFFICE_ID=" + div+ " and OFFICE_LEVEL_ID='SD'", 2,"--Select--");
			//	xml = obj.combo_lkup("OFFICE_ID", "OFFICE_NAME","com_mst_offices","where OFFICE_ID=" + div  , 2,"--Select--");

			} 	
			if (process_code.equals("6"))
				xml = obj.combo_lkup("SCH_SNO","SCH_NAME","pms_sch_master"," WHERE sch_sno IN( SELECT DISTINCT SCHEME_SNO FROM PMS_DCB_MST_BENEFICIARY_METRE "
										+ "WHERE "+ meter_status+ " office_id ="+ Office_id
										+ " AND BENEFICIARY_SNO IN( SELECT BENEFICIARY_SNO FROM PMS_DCB_MST_BENEFICIARY where "
										+ new_cond+" office_id ="+ Office_id
										+ ")" + ") order by sch_sno", 2,"--Select--"); // for scheme
  
			PrintWriter pr = response.getWriter();
			pr.write(xml);
			pr.flush();
			pr.close();
			obj.conClose(con);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
