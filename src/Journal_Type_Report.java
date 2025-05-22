import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.PMS.PMS1.DCB.servlets.Controller;

public class Journal_Type_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Journal_Type_Report() {
		super();
	}

	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache");
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
		System.out.println("DCB--->Journal_Type_Report-->");  
		String Office_id = "", month = "", year = "";
		
		// String qry="select JOURNAL_TYPE_CODE,JOURNAL_TYPE_DESC,UPDATED_BY_USER_ID,UPDATED_DATE , ";
			//   qry+=" CATEGORY,DISPLAY_RESTRICTED,CONDITION,CONDITION_DESC from PMS_DCB_APPLICABLE_JOU_TYPE order by JOURNAL_TYPE_CODE";
		String qry="SELECT a.BENEFICIARY_SNO,SUM(a.COLN_CUR_YR_WC) AS COLN_CUR_YR_WC,b.beneficiary_name FROM PMS_DCB_TRN_BILL_DMD a, PMS_DCB_MST_BENEFICIARY b "+
				" WHERE a.office_id    =5982 AND a.beneficiary_sno=b.beneficiary_sno GROUP BY a.BENEFICIARY_SNO,b.beneficiary_name ORDER BY a.BENEFICIARY_SNO";
			   ResultSet rs;
			   out.println("<html>");  
               out.println("<head><title>Hello</title></title>");  
               out.println("<body><table  align='center' width='75%'><tr><td>Journal Type Code</td><td>Journal Type Description</td>");
			   String xmlvariable = "<response>"; 
			try {
				rs = obj.getRS(qry);
				String BENEFICIARY_SNO,BENEFICIARY_NAME ,COLN_CUR_YR_WC;
	            xmlvariable += "<command>add</command>";
				while (rs.next())
				{
					BENEFICIARY_SNO = obj.isNull(rs.getString("BENEFICIARY_SNO"), 2);
					BENEFICIARY_NAME = obj.isNull(rs.getString("BENEFICIARY_NAME"), 2);  
					COLN_CUR_YR_WC = obj.isNull(rs.getString("COLN_CUR_YR_WC"), 2);
					 out.println("<tr><td>"+BENEFICIARY_SNO+"</td>");
					 out.println("<td>"+BENEFICIARY_NAME+"</td><td>"+COLN_CUR_YR_WC+"</td></tr>");
				}  
				xmlvariable+="</response>";
				// out.println(xmlvariable);   
				
               
                out.println("</table></body></html>");
			} catch (Exception e) { 
			 
				e.printStackTrace();
			}
	}

}
