package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Servlets.PMS.PMS1.DCB.Impl.Common_Impl;
public class Counting extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public Counting() { super(); }
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException   
	{
		 response.setContentType(CONTENT_TYPE);
		 PrintWriter pr = response.getWriter();
		 String xml = "";
		 String process_code = request.getParameter("process_code");// process		 
		 System.out.println("DCB->Counting->process_code-->"+process_code);
		 Controller obj=new Controller();
		 Connection con = null;
			try {
				con = obj.con();
				con.createStatement();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
			 int month=Integer.parseInt(obj.setValue("month", request));
			 int year=Integer.parseInt(obj.setValue("year", request));
			 int office_id=Integer.parseInt(obj.setValue("office_id", request));
		 if (process_code.equalsIgnoreCase("1"))
		 {
			
				
			 		xml = "<result>";
				 int sub_div=Integer.parseInt(obj.setValue("sub_div", request));
				 int schsno=Integer.parseInt(obj.setValue("schsno", request));
				 Common_Impl obj_impl=new  Common_Impl();
				 Double read=0.0;    
				 xml+="<reading>";
				 try {
					  if (process_code.equalsIgnoreCase("1")) 
					  read=obj_impl.sub_div_wise_pr(office_id, sub_div, month, year, obj);
					  if (process_code.equalsIgnoreCase("2")) 
						  read=obj_impl.schemewise_pr(office_id, schsno, month, year, obj);
			 	} catch (Exception e) 
				{ 
					obj.testQry(e.getMessage());
				}
			 	 xml+=read+"</reading>";
			 	 xml+= "</result>";
				 
				 
		 } else if (process_code.equalsIgnoreCase("2"))
		 {
			    xml = "<result>";
			    int already_demand=0;
			 	String bentype=obj.setValue("ben_type", request);
				try {
					  already_demand=obj.getCount("PMS_DCB_TRN_BILL_DMD", "where OFFICE_ID="+office_id+" and BILL_MONTH="+month+
							      " and BILL_YEAR="+year+" and BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY" +
							      " where BENEFICIARY_TYPE_ID="+bentype+" and OFFICE_ID="+office_id+")");
				} catch (Exception e) 
				{  
						e.printStackTrace();
				}
				 xml+="</already_demand>"+already_demand+"</already_demand>";
				 xml+= "</result>";
		 }else if (process_code.equalsIgnoreCase("3"))
		 {
		 		try {    
		 			String bentype=obj.setValue("bentype", request);             
					String  ben=obj.combo_lkup("BENEFICIARY_SNO","BENEFICIARY_NAME","PMS_DCB_MST_BENEFICIARY"," where office_id="+office_id+" and status='L' and BENEFICIARY_TYPE_ID="+bentype+" order by BENEFICIARY_NAME ",2,"Select Beneficiary");
					xml=ben;     
				} catch (Exception e) {				
					e.printStackTrace();
				}

		 }      
		 
		
		 pr.write(xml);
		 pr.flush();
		 pr.close();
		 obj.conClose(con)	;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException 
	{
	
	}

}
