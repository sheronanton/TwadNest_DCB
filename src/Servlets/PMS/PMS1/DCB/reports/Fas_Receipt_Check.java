package Servlets.PMS.PMS1.DCB.reports;
 import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class Fas_Receipt_Check extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public Fas_Receipt_Check() {
		super();
	}  
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection prcon = null;
		Controller Obj = new Controller();
		String imagespath = "";
		System.out.println("sdfsf");  
		try {
			prcon = Obj.con();
			Obj.createStatement(prcon);
		} catch (Exception ex) {
			String connectMsg = "Could not create the connection"
					+ ex.getMessage() + " " + ex.getLocalizedMessage();
		}
		try {
			Map parameters = new HashMap();
			String path = "",MONTH="",year="";
			String command = Obj.setValue("command", request);
			String process = Obj.setValue("ref_sno", request);
			HttpSession session = request.getSession(false);
			String userid = (String) session.getAttribute("UserId");
			if (userid == null)
			{
			}
		//	String Office_id = Obj.getValue("HRM_EMP_CURRENT_POSTING",	"OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			String	Office_id=Obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			
			String office_ = Obj.getValue("COM_MST_OFFICES", "OFFICE_NAME",
					" where OFFICE_ID=" + Office_id);
			MONTH = Obj.isNull(Obj.getValue("PMS_DCB_SETTING","MONTH", " where OFFICE_ID=" + Office_id), 1);
			year = Obj.isNull(Obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
			String query = " SELECT b.ACCOUNTING_FOR_OFFICE_ID,b.receipt_no,b.amount,c.beneficiary_name, b.sub_ledger_code,b.sub_ledger_type_code, "
					+ " b.AMOUNT AS recp_amt,b.CR_DR_INDICATOR,b.ACCOUNT_HEAD_CODE,b.CASHBOOK_MONTH, b.CASHBOOK_YEAR "
					+ " FROM FAS_RECEIPT_TRANSACTION b ,FAS_RECEIPT_MASTER a , PMS_DCB_MST_BENEFICIARY c WHERE b.CASHBOOK_MONTH   = "+MONTH
					+ " AND   b.accounting_for_office_id ="+Office_id+" and  b.CASHBOOK_YEAR = "+year+" AND b.ACCOUNT_HEAD_CODE IN  (SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "
					+ " WHERE COLLECTION_TYPE IN (7)  ) AND ( b.sub_ledger_type_code   <> 10 or b.sub_ledger_code=0 )	AND b.ACCOUNTING_FOR_OFFICE_ID IN "
					+ " (SELECT office_id FROM PMS_DCB_DIV_DIST_MAP  )	AND b.CASHBOOK_MONTH =a.CASHBOOK_MONTH AND b.CASHBOOK_YEAR=a.CASHBOOK_YEAR"
					+ " AND b.receipt_no=a.receipt_no AND b.ACCOUNTING_FOR_OFFICE_ID=a.ACCOUNTING_FOR_OFFICE_ID AND a.SUB_LEDGER_TYPE_CODE   = 14"
					+ " AND a.RECEIPT_STATUS ='L' AND c.beneficiary_sno=a.sub_ledger_code AND c.office_id=a.ACCOUNTING_FOR_OFFICE_ID"
					+ " AND c.status='L' ORDER BY b.ACCOUNTING_FOR_OFFICE_ID ";
			String xml=Obj.resultXML(query, prcon, Obj); 			   
			PrintWriter pr = response.getWriter();
			pr.write(xml);  
				pr.flush();
			pr.close();   
		} catch (Exception e) {
				System.out.println(e);
		}
	}
}
