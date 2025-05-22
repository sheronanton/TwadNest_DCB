package Servlets.PMS.PMS1.DCB.reports;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class knowyourben extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	public knowyourben() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(  request,  response);
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		String xml="";
		try
		{
		Controller obj = new Controller();
		Connection con=obj.con();
		obj.createStatement(con);
		String command = request.getParameter("command");// Command
		String option = request.getParameter("option");// Command		
		String ben_type_id=obj.setValue("ben_type_id", request);
		String startname=obj.setValue("startname", request);
		System.out.println("PMS-->DCB-->knowyourben-->option-->"+option);
		int process_code=Integer.parseInt(option);
		String userid = "0", Office_id = "0"; 
		HttpSession session = request.getSession(false);
		try
		{
			userid = (String) session.getAttribute("UserId");
		}catch (Exception e) 
		{
		}  
		if (userid == null) 
		{
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING",	"OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')"), 1));
		if (Office_id.equals("0")) Office_id = "0";
		String qry="";
			Hashtable ht=new Hashtable();	 	
			if (process_code==1)  
			{
				qry="select BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where  office_id=?  and  BENEFICIARY_TYPE_ID=? ";			
				ht.put("1", ben_type_id);
				ht.put("2", Office_id);
				xml+=obj.resultXML(qry, ht,con, obj); 
			}else if(process_code==2)
			{ 
				qry="select BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where office_id="+Office_id+" and upper(BENEFICIARY_NAME) like (upper('%"+startname+"%')) order by BENEFICIARY_NAME ";					
				xml+=obj.resultXML(qry, con, obj); 
			}else if (process_code==3)   
			{
				String beneficiary_sno=obj.setValue("ben", request);
				qry="SELECT  a.BENEFICIARY_NAME,a.BILLING_ADDRESS1,a.BILLING_ADDRESS2,a.BILLING_ADDRESS3,a.BILLING_PIN_CODE,a.BILLING_ADDRESS3,a.BENEFICIARY_TYPE_ID,a.block_sno,(SELECT BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE "+
						 " WHERE BEN_TYPE_ID=a.BENEFICIARY_TYPE_ID ) AS BEN_TYPE_DESC , (select distinct district_name from com_mst_districts  where district_code=a.district_code) as dsname,"+
						  " (select block_name from com_mst_blocks where block_sno=a.block_sno) as block_name,a.STATUS FROM PMS_DCB_MST_BENEFICIARY a WHERE BENEFICIARY_SNO=?";
				ht.put("1", beneficiary_sno);		
				int c=obj.getCount("PMS_DCB_TRN_BILL_DMD"," where( bill_year =(select max(bill_year) from PMS_DCB_TRN_BILL_DMD) and bill_month=(select to_char(sysdate,'MM')-1 from dual) and BENEFICIARY_SNO=1 )  group by BENEFICIARY_SNO");
				 xml+="<response>"+obj.resultXML(qry, ht,con, obj);
				 xml+="<count>"+c+"</count>";
				qry="SELECT b.METRE_LOCATION,b.meter_status, b.scheme_sno,(SELECT sch_name FROM pms_sch_master WHERE sch_sno=b.scheme_sno ) AS sch_name,"+
					" (select SCH_TYPE_DESC from PMS_SCH_LKP_TYPE where SCH_TYPE_ID in (select SCH_TYPE_ID FROM pms_sch_master WHERE sch_sno=b.scheme_sno)) as type_desc,"+
  					" (select SCH_STATUS_DESC from PMS_SCH_LKP_STATUS where SCH_STATUS_ID in (select SCH_STATUS_ID FROM pms_sch_master WHERE sch_sno=b.scheme_sno)) as status_desc,"+
  					" b.sch_type_id, b.tariff_rate, b.tariff_flag FROM PMS_DCB_MST_BENEFICIARY_METRE b WHERE b.BENEFICIARY_SNO=?   ";
			 
				xml+=obj.resultXML(qry, ht,con, obj,1)+"</response>";  
			}else if (process_code==4)
			{
				String month=obj.setValue("month", request);
				String year=obj.setValue("year", request);
				String beneficiary_sno=obj.setValue("ben", request);
				qry="SELECT SUM(a.OB_CUR_YR_WC)+SUM(a.OB_YESTER_YR_WC) AS apr, "+
				" SUM(a.OB_FOR_MTH_CUR_YR_WC)                     AS curob, "+
				  " (SELECT sum(TOTAL_AMT)  FROM PMS_DCB_WC_BILLING  WHERE month="+month+" AND year="+year+"  AND beneficiary_sno="+beneficiary_sno+" group by beneficiary_sno ) AS wc, "+
				  " (SELECT COUNT(*)  FROM PMS_DCB_TRN_BILL_DMD  WHERE bill_month="+month+" AND bill_year="+year+" AND beneficiary_sno="+beneficiary_sno+" ) AS bill_ct, "+
				  " (SELECT MONTH_BILL_AMT  FROM PMS_DCB_TRN_BILL_DMD  WHERE bill_month="+month+"  AND bill_year="+year+"   AND beneficiary_sno="+beneficiary_sno+"  ) AS bill_amt, "+
				  " (select sum(COLN_CUR_YR_WC)+sum(COLN_YESTER_YR_WC)  FROM PMS_DCB_TRN_BILL_DMD  WHERE bill_month="+month+"  AND bill_year="+year+"  AND beneficiary_sno="+beneficiary_sno+"  group by beneficiary_sno ) as collection, "+
				  " (select sum(ADD_CHARGES_WC)  FROM PMS_DCB_TRN_BILL_DMD  WHERE bill_month="+month+"  AND bill_year="+year+"  AND beneficiary_sno="+beneficiary_sno+" group by beneficiary_sno ) as add_charges, "+
				  " (select sum(MINUS_CHARGES_WC) FROM PMS_DCB_TRN_BILL_DMD  WHERE bill_month="+month+"  AND bill_year="+year+"  AND beneficiary_sno="+beneficiary_sno+" group by beneficiary_sno ) as min_charges, "+
				  "   (SELECT COUNT(*)  FROM PMS_DCB_LEDGER_ACTUAL  WHERE month="+month+"  AND year="+year+"  AND beneficiary_sno="+beneficiary_sno+"  ) AS ledger_ct "+
				" FROM PMS_DCB_OB_YEARLY a WHERE a.month="+month+" AND a.fin_year="+year+"	AND a.beneficiary_sno="+beneficiary_sno+"	GROUP BY a.beneficiary_sno";			 
				xml+="<response>";
				xml+=obj.resultXML(qry,con, obj,1);
				xml+="<month>"+month+"</month><year>"+year+"</year></response>";
			}
			
		}catch(Exception e)  
		{      
			System.out.println(e); 
			xml+="<response><flag>1</flag></response>";
		}
		PrintWriter er = response.getWriter();
		er.write(xml);
		er.flush();  
	}

}  
