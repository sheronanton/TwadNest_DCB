package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PMS_DCB_RECEIPT_TROUBLESSHOOT extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public PMS_DCB_RECEIPT_TROUBLESSHOOT() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		PreparedStatement ps = null;
		Connection con=null;
		Controller obj = new Controller();
		HttpSession session = request.getSession(false);
		String userid = "0", Office_id = "0";
		try 
		{
			con = obj.con();
			obj.createStatement(con);
		}catch (Exception e) { }
		try 
		{
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) {	}
		
		String ACCOUNTING_FOR_OFFICE_ID="",CASHBOOK_YEAR="",CASHBOOK_MONTH="",RECEIPT_NO="",SL_NO="",project_id="";
		
		ACCOUNTING_FOR_OFFICE_ID=obj.setValue("ACCOUNTING_FOR_OFFICE_ID", request);
		CASHBOOK_YEAR=obj.setValue("CASHBOOK_YEAR", request);
		CASHBOOK_MONTH=obj.setValue("CASHBOOK_MONTH", request);
		RECEIPT_NO=obj.setValue("RECEIPT_NO", request);
		SL_NO=obj.setValue("SL_NO", request);
		project_id=obj.setValue("project_id", request);
		Hashtable upd = new Hashtable();
		upd.put("SUB_LEDGER_CODE", project_id);     
		upd.put("SUB_LEDGER_TYPE_CODE", "10");  
		Hashtable cond = new Hashtable();
		cond.put("ACCOUNTING_FOR_OFFICE_ID", ACCOUNTING_FOR_OFFICE_ID);
		cond.put("CASHBOOK_YEAR", CASHBOOK_YEAR);
		cond.put("CASHBOOK_MONTH", CASHBOOK_MONTH);
		cond.put("RECEIPT_NO", RECEIPT_NO);
		cond.put("SL_NO", SL_NO);
		
		String res="";
		try
		{  
			int sr= obj.recordSave(upd, cond,"FAS_RECEIPT_TRANSACTION", con);
			if (sr > 0) {
				res = "<response><rows>"+sr+"</rows></response>";
			}	else
			{
				res = "<response><rows>"+sr+"</rows></response>";
			}
		}catch(Exception e)
		{
			System.out.println(e);  
		}

		PrintWriter er = response.getWriter();
		er.write(res);  
		er.flush();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
