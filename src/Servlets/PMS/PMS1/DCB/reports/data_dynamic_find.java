package Servlets.PMS.PMS1.DCB.reports;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class data_dynamic_find extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public data_dynamic_find() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		Connection con;
		Connection con1;
		Controller obj = new Controller();
		Controller obj1 = new Controller();
		Controller obj2 = new Controller();
		Controller obj3 = new Controller();
		String xml="<result>";
		try {
			con = obj.con();
			con1 = obj3.con();
			obj.createStatement(con);
			obj1.createStatement(con);
			obj2.createStatement(con);
			obj3.createStatement(con1);  
			String process_code = request.getParameter("process_code");// process
			String off_id = request.getParameter("office_id");// process
			String year = obj.setValue("year", request);
			if (year == null || year.equals("")) year = "0";
			String month = obj.setValue("month", request);
			if (year == null || year.equals(""))  month = "0";
			String  pumping_qty_CR="0",pumping_qty_V="0",pumping_qty_FR="0";
			if (process_code.equals("1"))
			{
						pumping_qty_CR=obj1.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)","qty"," where office_id="+off_id+" and year="+year+" and month="+month+" and PROCESS_FLAG='CR'");
						pumping_qty_V=obj1.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)","qty"," where office_id="+off_id+" and year="+year+" and month="+month+" and PROCESS_FLAG='V'");
						pumping_qty_FR=obj1.getValue("PMS_DCB_TRN_MONTHLY_PR","count(*)","qty"," where office_id="+off_id+" and year="+year+" and month="+month+" and PROCESS_FLAG='FR'");
						xml+="<pumping_qty_CR>"+pumping_qty_CR+"</pumping_qty_CR>";
						xml+="<pumping_qty_V>"+pumping_qty_V+"</pumping_qty_V>";  
						xml+="<pumping_qty_FR>"+pumping_qty_FR+"</pumping_qty_FR>";
						String qry="SELECT OFFICE_ID,case when JOURNAL_POSTED  is null then 'No' else 'Yes' end   AS st  FROM PMS_DCB_FREEZE_STATUS  where office_id="+off_id+" and  CASHBOOK_YEAR="+year+" and CASHBOOK_MONTH="+month+"";
						ResultSet rs1=obj2.getRS(qry);
						if (rs1.next())
							xml+="<jrpost>"+rs1.getString("st")+"</jrpost>";
						else
							xml+="<jrpost>-</jrpost>";
						rs1=null;
						qry="select count (*) as dmdc  from PMS_DCB_TRN_BILL_DMD where office_id="+off_id+" and  beneficiary_sno IN (SELECT beneficiary_sno FROM PMS_DCB_MST_BENEFICIARY WHERE status='L'  ) and BILL_YEAR="+year+" and BILL_MONTH="+month+"  group by office_id, BILL_YEAR, BILL_MONTH   ";
						rs1=obj2.getRS(qry);
						if (rs1.next())
							xml+="<dmdpost>"+rs1.getString("dmdc")+"</dmdpost>";
						else
							xml+="<dmdpost>-</dmdpost>";
						rs1=null; 
						qry="select   count(DISTINCT beneficiary_sno) as ltcount     from    PMS_DCB_LEDGER_ACTUAL   where office_id="+off_id+" and  YEAR="+year+" AND MONTH="+month+"";
						rs1=obj2.getRS(qry);
						if (rs1.next())
							xml+="<ltcount>"+rs1.getString("ltcount")+"</ltcount>";
						else
							xml+="<ltcount>-</ltcount>";
						rs1=null;
						String DCB_Freeze_msg="",DCB_Freeze="";
						qry="select RECEIPT_FREEZE from  PMS_DCB_FREEZE_RECEIPT   where office_id="+off_id+" and  CASHBOOK_YEAR="+year+" AND CASHBOOK_MONTH="+month+"";
						rs1=obj2.getRS(qry);
						if (rs1.next())
							DCB_Freeze=rs1.getString("RECEIPT_FREEZE");			
						else
							DCB_Freeze="N";
						  if (DCB_Freeze.trim().equalsIgnoreCase("Y")) DCB_Freeze_msg="Verified"; else DCB_Freeze_msg="Not Verified";
						  xml+="<DCB_Freeze_msg>"+DCB_Freeze_msg+"</DCB_Freeze_msg>";
						  rs1=null;
			}
			con.close();
			} catch (Exception e) 
			{
				System.out.println(e);
			}
			xml+="</result>";
			PrintWriter er = response.getWriter();
			er.write(xml);
			er.flush(); 
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
