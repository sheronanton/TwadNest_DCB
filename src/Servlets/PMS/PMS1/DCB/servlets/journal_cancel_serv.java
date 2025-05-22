/* 
  * Created on : dd-mm-yy 
  * Author     : Panneer Selvam.K
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description
  *---------|---------------|--------------------------------------------------- 
  */
package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class journal_cancel_serv
 */
public class journal_cancel_serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public journal_cancel_serv() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
		PreparedStatement ps=null;
		response.setContentType(CONTENT_TYPE);
		Controller obj = new Controller();
		Connection con = null;
		String userid="",Office_id="";
		String  xml = "<result>";
		try {
			
			con = obj.con();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			userid = (String) session.getAttribute("UserId");
			if (userid == null) {

				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			int A_unit_id=Integer.parseInt(obj.getValue("FAS_MST_ACCT_UNITS", "ACCOUNTING_UNIT_ID", " where ACCOUNTING_UNIT_OFFICE_ID="+Office_id));
			String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
			String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
			String rowcnt=obj.setValue("rowcnt", request);
			int row_up=0;
			
			String tbstatus=obj.getValue("FAS_TRIAL_BALANCE_STATUS", "count(*)", " where ACCOUNTING_UNIT_ID = "+A_unit_id+"  and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and CASHBOOK_YEAR =" +syear+ " and CASHBOOK_MONTH="+smonth);
			if (Integer.parseInt(tbstatus)==0)
			{
			for (int i=1;i<=Integer.parseInt(rowcnt);i++)
			{
				
				String vno=obj.setValue("vno"+i, request);
				String AUTHORIZED_TO=obj.getValue("FAS_CROSS_REFERENCE","AUTHORIZED_TO", " where ACCOUNTING_UNIT_ID="+A_unit_id+"  and  ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and CASHBOOK_YEAR="+syear+" and  CASHBOOK_MONTH="+smonth+" and  DOC_TYPE='DJV' and VOUCHER_NO="+vno); 
				
				//if (AUTHORIZED_TO.equalsIgnoreCase("C")) {
				String qry="update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C' where ACCOUNTING_UNIT_ID="+A_unit_id+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and VOUCHER_NO="+vno+" and CASHBOOK_YEAR="+syear+" and CASHBOOK_MONTH="+smonth;
				PreparedStatement ps1=con.prepareStatement(qry);
				row_up=ps1.executeUpdate();
				
				
				qry="update PMS_DCB_JOURNAL_DETAILS set VOUCHER_NO=null,VOUCHER_DATE=null where ACCOUNTING_UNIT_ID="+A_unit_id+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+"   and CASHBOOK_YEAR="+syear+" and CASHBOOK_MONTH="+smonth;
				
				ps1=con.prepareStatement(qry);
				row_up=ps1.executeUpdate();
				
				qry="update PMS_DCB_FREEZE_STATUS set JOURNAL_POSTED=null,JOURNAL_POSTED_DATE=null where     OFFICE_ID="+Office_id+"   and CASHBOOK_YEAR="+syear+" and CASHBOOK_MONTH="+smonth;
				
				ps1=con.prepareStatement(qry);
				row_up=ps1.executeUpdate();
				 
				//}
				
			}
			xml += "<row_up>"+row_up+"</row_up> <tbstatus>"+tbstatus+"</tbstatus>";
			}
			else
			{
				xml += "<row_up>0</row_up><tbstatus>"+tbstatus+"</tbstatus>";
				
			}
			
			
			
			xml += "</result>";
			
		}catch (Exception e) {
			 System.out.print("Exception occured "+ e);
		}
		PrintWriter pr_new = null;
		pr_new =response.getWriter();
		pr_new.write(xml);
		pr_new.flush();
		pr_new.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
	}

}
