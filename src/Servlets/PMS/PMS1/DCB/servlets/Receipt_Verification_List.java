package Servlets.PMS.PMS1.DCB.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class Receipt_Verification_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Receipt_Verification_List() {
        super();
        // TODO Auto-generated constructor stu  b
    }

    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		   response.setHeader("Cache-Control","no-cache");
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
		Connection con = null;
		Controller obj = new Controller();
		Controller obj2 = new Controller();
		try {
			con = obj.con();
			obj.createStatement(con);
			obj2.createStatement(con);
		} catch (Exception e) {
		}
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		System.out.println("DCB--->Receipt_Verification_List-->");
		String Office_id = "", month = "", year = ""; 
		try {
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING",
					"OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");

			month = obj.setValue("smonth", request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
			year = obj.setValue("syear", request);
			;//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);
			//Error,RDATE,AMOUNT,BENEFICIARY_NAME,ACCOUNT_HEAD_CODE,sch_name,SUB_LEDGER_CODE,SUB_LEDGER_TYPE_CODE,RECEIPT_TYPE,RECEIPT_NO,BENEFICIARY_SNO
			String qry="SELECT CASE WHEN receipt_type='B' THEN 'Bank' " +
						" ELSE 'Cash' END AS receipt_type , RECEIPT_NO, TO_CHAR(RDATE,'DD-MM-YYYY') AS RDATE, " +
						" AMOUNT,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,SCH_SNO," +
						" (SELECT sch_name FROM pms_sch_master WHERE sch_sno=a.sch_sno) AS sch_name, BENEFICIARY_NAME, " +
						" BENEFICIARY_SNO,BENEFICIARY_TYPE_ID_SUB,(SELECT BEN_TYPE_DESCFROM PMS_DCB_BEN_TYPE " +
						"  WHERE BEN_TYPE_ID=a.BENEFICIARY_TYPE_ID_SUB ) AS ben_type_dec,CASE  WHEN ACCOUNT_HEAD_CODE NOT IN " +
						" (SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP ) THEN 'Wrong Receipt Acc.Head Code' " +
						"  WHEN (SUB_LEDGER_CODE IS NULL  OR SUB_LEDGER_CODE     =0) THEN 'SL Code Missing ' WHEN SUB_LEDGER_TYPE_CODE IS NULL " +
						"   THEN 'SL Type Code Missing '  WHEN a.sch_sno NOT IN  (SELECT DISTINCT scheme_sno  FROM pms_dcb_mst_beneficiary_metre WHERE beneficiary_sno=a.BENEFICIARY_SNO " +
						"  )  THEN 'SL Code Mis-Match ' END AS Error FROM PMS_DCB_FAS_RECEIPT_VIEW a " +
						" WHERE   cashbook_month="+month+"  AND cashbook_year ="+year+" ORDER BY Error"; 
			
			String sch_sno = "0", type = "0", receipt_no = "0", receipt_dt = "", ben_sno = "", amt = "", oben_sno = "0", ben_name = "", ACCOUNT_HEAD_CODE = "", desc = "";
			String innerHTML = "";
			String qry_res = "",RECEIPT_TYPE="";
			ResultSet rs = obj.getRS(qry);
			int i = 0;
			int c = 0;
			String xmlvariable = "<response>";
            xmlvariable += "<command>add</command>";
			while (rs.next())
			{
				i++; 
				desc = obj.isNull(rs.getString("Error"), 2);
				receipt_dt = obj.isNull(rs.getString("RDATE"), 1);
				amt = obj.isNull(rs.getString("AMOUNT"), 1);
				ben_name = obj.isNull(rs.getString("BENEFICIARY_NAME"), 1);
				ACCOUNT_HEAD_CODE = obj.isNull(rs.getString("ACCOUNT_HEAD_CODE"), 1);
				sch_sno = obj.isNull(rs.getString("sch_name"), 3) + "&nbsp;&nbsp;("+ obj.isNull(rs.getString("SUB_LEDGER_CODE"), 3)+ ")";
				type = obj.isNull(rs.getString("SUB_LEDGER_TYPE_CODE"), 1);
				type = obj.getValue("com_mst_sl_types","SUB_LEDGER_TYPE_DESC"," where SUB_LEDGER_TYPE_CODE=" + type);
				RECEIPT_TYPE= obj.isNull(rs.getString("RECEIPT_TYPE"), 1);
				receipt_no = obj.isNull(rs.getString("RECEIPT_NO"), 1);
				ben_sno = obj.isNull(rs.getString("BENEFICIARY_SNO"), 1);
				String desc_new = "";
			
				xmlvariable+=obj.generateXML("Error", desc, 1, obj);
				xmlvariable+=obj.generateXML("RDATE", receipt_dt, 1, obj);
				xmlvariable+=obj.generateXML("AMOUNT", desc, 1, obj);
				xmlvariable+=obj.generateXML("BENEFICIARY_NAME", ben_name, 1, obj);
				xmlvariable+=obj.generateXML("ACCOUNT_HEAD_CODE", ACCOUNT_HEAD_CODE, 1, obj);
				xmlvariable+=obj.generateXML("sch_name", sch_sno, 1, obj);
				xmlvariable+=obj.generateXML("SUB_LEDGER_TYPE_CODE", type, 1, obj);
				xmlvariable+=obj.generateXML("type", type, 1, obj);
				xmlvariable+=obj.generateXML("RECEIPT_TYPE", RECEIPT_TYPE, 1, obj);
				xmlvariable+=obj.generateXML("RECEIPT_NO", receipt_no, 1, obj);
				xmlvariable+=obj.generateXML("BENEFICIARY_SNO", ben_sno, 1, obj);
				 
				  
				
			}
		} catch (Exception e) {
		}
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
