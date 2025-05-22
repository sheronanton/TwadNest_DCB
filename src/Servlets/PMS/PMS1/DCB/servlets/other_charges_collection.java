/* 
  * Created on : dd-mm-yy 
  * Author     : Panneer Selvam.K
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description
  * 17/09/2011		Add the Beneficiary Status to 'L'  
  * 20/09/2011		Add the Meter Status to 'L'
  *---------|---------------|--------------------------------------------------- 
  */
package Servlets.PMS.PMS1.DCB.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 * Servlet implementation class other_charges_collection
 */
public class other_charges_collection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public other_charges_collection() {
		super();
		 
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		Connection con;
		Controller obj = new Controller();
		try {
			con = obj.con();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			String userid = "0", Office_id = "0";
			try {
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {
				userid = "0";
			}
			if (userid == null) {
				userid = "0";
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
	
	 		  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	
			
	//		Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')"), 1));
								
			if (Office_id.equals("0"))
				Office_id = "0";
			String xml = "<chargeresponse>";
			
			String ACCOUNTING_UNIT_ID = obj.getValue("fas_mst_acct_unit_offices","ACCOUNTING_UNIT_ID", "where ACCOUNTING_FOR_OFFICE_ID="+ Office_id);

			
	//		String ACCOUNTING_UNIT_ID = obj.getValue("FAS_MST_ACCT_UNITS","ACCOUNTING_UNIT_ID", "where ACCOUNTING_UNIT_OFFICE_ID="+ Office_id);
			String CASHBOOK_MONTH = "", CASHBOOK_YEAR = "", BENEFICIARY_SNO = "", SCH_SNO = "", VOUCHER_NO = "";
			String VOUCHER_DATE = "", ACCOUNT_HEAD_CODE = "", CR_DR_INDICATOR = "", AMOUNT = "", CONFIRM_FLAG = "", PARTICULARS = "";
			String row_cnt = obj.setValue("row_cnt", request);
			CASHBOOK_MONTH =  obj.isNull(obj.getValue("PMS_DCB_SETTING","MONTH", " where OFFICE_ID=" + Office_id), 1);//obj.setValue("month", request);
		  	CASHBOOK_YEAR = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);//obj.setValue("year", request);			
			String Voucher_month= obj.setValue("month", request);
		 	String Voucher_year= obj.setValue("year", request);;
			Date txtCrea_date = null;
			String qry = "";
			int ins_row=0;
			for (int i = 1; i <= Integer.parseInt(row_cnt); i++) 
			{
				Calendar c;
				CONFIRM_FLAG = obj.setValue("CONFIRM_FLAG" + i, request);
				if (CONFIRM_FLAG.equalsIgnoreCase("true"))					
					CONFIRM_FLAG = "Y";
				else
					CONFIRM_FLAG = "N";
				if (CONFIRM_FLAG.equalsIgnoreCase("Y"))  
				
				{
						qry = "Insert into PMS_DCB_OTHER_CHARGES (TYPE_SNO,SCH_TYPE_ID,ACCOUNTING_UNIT_ID,OFFICE_ID, CASHBOOK_MONTH,CASHBOOK_YEAR,BENEFICIARY_SNO,VOUCHER_NO,VOUCHER_DATE,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,AMOUNT,CONFIRM_FLAG,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,VOUCHER_MONTH,VOUCHER_YEAR) ";
						BENEFICIARY_SNO = obj.setValue("BENEFICIARY_SNO" + i, request);
						VOUCHER_NO = obj.setValue("VOUCHER_NO" + i, request);
						VOUCHER_DATE = obj.setValue("VOUCHER_DATE" + i, request);
						String[] sd = VOUCHER_DATE.split("/");
						c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
						java.util.Date d = c.getTime();
						txtCrea_date = new Date(d.getTime());
						ACCOUNT_HEAD_CODE = obj.setValue("ACCOUNT_HEAD_CODE" + i,request);
						String SCH_TYPE_ID = obj.getValue("PMS_DCB_RECEIPT_ACCOUNT_MAP","SCH_TYPE_ID", "where ACCOUNT_HEAD_CODE="+ ACCOUNT_HEAD_CODE);
						CR_DR_INDICATOR = obj.setValue("CR_DR_INDICATOR" + i, request);
						AMOUNT = obj.setValue("AMOUNT" + i, request);						
						PARTICULARS = obj.setValue("PARTICULARS" + i, request);					 
						qry += "values ("
								+ obj.getMax("PMS_DCB_OTHER_CHARGES", "TYPE_SNO", "")
								+ "" + " ,"+SCH_TYPE_ID+", " + ACCOUNTING_UNIT_ID + ",'" + Office_id
								+ "'," + CASHBOOK_MONTH + "," + CASHBOOK_YEAR + ","
								+ BENEFICIARY_SNO + "," + VOUCHER_NO + ",'"+VOUCHER_DATE+"',"
								+ ACCOUNT_HEAD_CODE + ",'" + CR_DR_INDICATOR + "',"
								+ AMOUNT + ",'" + CONFIRM_FLAG + "','" + PARTICULARS
								+ "','" + userid + "',clock_timestamp(),"+Voucher_month+","+Voucher_year+")";
						int count=obj.getCount("PMS_DCB_OTHER_CHARGES", "where ACCOUNTING_UNIT_ID="+ACCOUNTING_UNIT_ID+" and OFFICE_ID::numeric= "+Office_id +" and CASHBOOK_MONTH="+CASHBOOK_MONTH+" and CASHBOOK_YEAR="+CASHBOOK_YEAR+" and VOUCHER_NO="+VOUCHER_NO+"");
						//if (count==0){					
									PreparedStatement st_r = con.prepareStatement(qry);
									st_r.executeUpdate();
									ins_row++;
									System.out.println("INNER ins_row"+ins_row); 
						//}
				}
			}
		 
			xml += "<insrow>" + ins_row + "</insrow></chargeresponse>";
			PrintWriter pr = response.getWriter();
			pr.write(xml);
				pr.flush();
			pr.close();
			System.out.println("xml"+xml); 
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {	}
}
