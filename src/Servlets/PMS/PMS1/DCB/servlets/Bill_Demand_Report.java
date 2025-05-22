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
  *  				Demand Generate Type Wise && Sub Div wise
  * 24/11/2011		Demand delete Type Wise
  *---------|---------------|--------------------------------------------------- 
  */ 
package Servlets.PMS.PMS1.DCB.servlets;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Servlets.PMS.PMS1.DCB.reports.count_bean;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
public class Bill_Demand_Report extends HttpServlet 
{
private static final long serialVersionUID = 1L;
private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
private static Connection con = null;
public Bill_Demand_Report() 
{
	super();
}
protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
{
		PreparedStatement ps=null;
		response.setContentType(CONTENT_TYPE);
		Controller obj = new Controller();
		Connection con = null;
		ResultSet rs = null;		 
		String MULTIPLY_FACTOR = "", QTY_CONSUMED = "",command="",input_value="",process_code="";
		DecimalFormat decForRate = new DecimalFormat("0.00");	
		double net_consumption = 0;
		double net_consumption_value = 0;
		String qry = "", xml = "<result>", ben_type = "",billsno="";
		int row_up=0;
		String path = "",userid="",Office_id="",Office_name="";
		PrintWriter pr_new = null;//response.getWriter();
		String new_cond=Controller.new_cond;
		try 
		{			
		con = obj.con();
		obj.createStatement(con);
		HttpSession session = request.getSession(false);
		userid = (String) session.getAttribute("UserId");
		if (userid == null) 
		{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		
		
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
		
	//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		command = request.getParameter("command");// Command ->view,insert etc
		if (command == null || command.equals(""))
			command = "no command";
		input_value = request.getParameter("input_value"); // input value
		if (input_value == null || input_value.equals(""))
			input_value = "0";
		process_code = request.getParameter("process_code");// process // Ben // Select ,2->Meter ,Select
		if (process_code == null || process_code.equals(""))
			process_code = "0";
		billsno = input_value;     
		obj.testQry("DCB->Bill_Demand_Report->command->"+command+"->process_code->"+process_code+"-->input_value");		
		if (command.equals("dmdfalsh"))
		{
			count_bean ct=new count_bean();
			ct.setMset(obj.setValue("billmonth", request));
			ct.setYset(obj.setValue("billyear", request));
			ct.setOffid(Integer.parseInt(Office_id));
			int ct_v=ct.getDemandedbillcount();
			xml += "<ct_v>"+ct_v+"</ct_v>";
		}
		else if (command.equals("wordupdate")) 
		{
			pr_new =response.getWriter();
			String processvalue = obj.setValue("processvalue", request);			
			if (processvalue.equalsIgnoreCase("0"))
			{
				processvalue="Zero";
			}
			String uqry = "update PMS_DCB_TRN_BILL_DMD set AMT_WORDS='"+ processvalue + "' where BILL_SNO=" + input_value;
			try 
			{
				ps=con.prepareStatement(uqry);
				row_up=ps.executeUpdate();
				xml += "<row_up>"+row_up+"</row_up> ";
			} catch (SQLException e) 
			{
				System.out.println("Error---->"+e);
				xml += "<row_up>0</row_up>";
			}
			xml += "</result>";
		 	pr_new.write(xml);
			pr_new.flush();
			pr_new.close(); 
		} 
		
		else if (command.equals("delete"))
		{ 
			pr_new =response.getWriter();
			String del_billyear = obj.setValue("billyear", request);				 
			String del_billmonth = obj.setValue("billmonth", request);  
			String New_cond ="";
			process_code = obj.setValue("process_code", request);	
			if (process_code.equalsIgnoreCase("14"))
			{
				String reqbillsno = obj.setValue("dmdlist", request);  
				String bensno = obj.getValue("PMS_DCB_TRN_BILL_DMD", "BENEFICIARY_SNO","where BILL_SNO=" + reqbillsno);
				New_cond=" and  BENEFICIARY_SNO in ("+bensno+")";		
			}
			try 
			{
			int cl_row=0;
			int ma_row=0;
			int del_client_rc=obj.getCount("PMS_DCB_TRN_BILL_DMD", "where OFFICE_ID="+Office_id+"  "+New_cond+" and BILL_MONTH="+del_billmonth+" and BILL_YEAR="+del_billyear);
			if (del_client_rc!=0)
			{
				try
				{  
				int nmonth_set=0,nyear_set=0;
				  if (Integer.parseInt(del_billmonth)==12)
				  {
					nmonth_set=1;
					nyear_set=(Integer.parseInt(del_billyear)+1);
				  }else							
				  {
					nyear_set=(Integer.parseInt(del_billyear));
					nmonth_set=(Integer.parseInt(del_billmonth)+1);
				  }
				String del_qry="";
				PreparedStatement ps_del=null;
				try
				{
				  del_qry="";
				  del_qry="delete   from PMS_DCB_TRN_BILL_DMD_SCH where office_id="+Office_id+"  and BILL_SNO in ( select BILL_SNO from PMS_DCB_TRN_BILL_DMD where office_id="+Office_id+"  "+New_cond+"  and BILL_MONTH="+del_billmonth+" and BILL_YEAR="+del_billyear+"  "+New_cond+")";
				  ps_del = con.prepareStatement(del_qry);
				  cl_row=ps_del.executeUpdate();
				}catch(SQLException e ) {}						
				ps_del=null;
				try
				{
				  del_qry="";
				  del_qry="delete from PMS_DCB_TRN_BILL_DMD where office_id="+Office_id+"  "+New_cond+" and BILL_MONTH="+del_billmonth+" and BILL_YEAR="+del_billyear+"";
				  ps_del = con.prepareStatement(del_qry);
				  ma_row=ps_del.executeUpdate();
				}catch(SQLException e ) {}
							ps_del=null;
							try
							{
								del_qry="";
								del_qry="delete    from PMS_DCB_OB_YEARLY where office_id="+Office_id+" "+New_cond+"  and month="+nmonth_set+" and fin_year="+nyear_set;						 
								ps_del = con.prepareStatement(del_qry);
								ps_del.executeUpdate();
							}catch(SQLException e ) {}
							try
							{
								del_qry="";
								ps_del=null;
								del_qry="delete from PMS_DCB_TRN_CB_MONTHLY where office_id="+Office_id+"  "+New_cond+" and month="+(Integer.parseInt(del_billmonth))+" and fin_year="+del_billyear;							 
								ps_del = con.prepareStatement(del_qry);
								ps_del.executeUpdate();
							}catch(SQLException e ) {}
							try
							{
								del_qry="";
								ps_del=null;
								del_qry="delete    from PMS_DCB_OB_YEARLY_TOTAL where office_id="+Office_id+"  "+New_cond+" and month="+nmonth_set+" and fin_year="+nyear_set;							 
								ps_del = con.prepareStatement(del_qry);
								ps_del.executeUpdate();
							}catch(SQLException e ) {}
							
							
												  
							  
							  
							
							  
					}catch (Exception e)
					{
						System.out.println(e);
					}
					xml += "<cl_row>"+cl_row+"</cl_row>";
					xml += "<ma_row>"+ma_row+"</ma_row>";
				}
				else
				{
					xml += "<cl_row>0</cl_row>";
					xml += "<ma_row>0</ma_row>";	
				}
		} catch (Exception e) {
				//   Auto-generated catch block
				e.printStackTrace();
				xml += "<cl_row>0</cl_row>";
				xml += "<ma_row>0</ma_row>";			
		}
				xml += "</result>";
		 		pr_new.write(xml);
		 		pr_new.flush();
		 		pr_new.close();
		}
		
		
		
		
		
		else if (command.equals("del"))
		{ 
			pr_new =response.getWriter();
			String del_billyear = obj.setValue("billyear", request);				 
			String del_billmonth = obj.setValue("billmonth", request);  
			String New_cond ="";
			process_code = obj.setValue("process_code", request);	
			if (process_code.equalsIgnoreCase("3"))
			{
				String reqbillsno = obj.setValue("dmdlist", request);  
				String bensno = obj.getValue("PMS_DCB_TRN_BILL_DMD", "BENEFICIARY_SNO","where BILL_SNO=" + reqbillsno);
				New_cond=" and  BENEFICIARY_SNO in ("+bensno+")";		
			}else
			if (process_code.equalsIgnoreCase("2"))
			{
				String bentype = obj.setValue("bentype", request);
				New_cond=" and  BENEFICIARY_SNO in ( select BENEFICIARY_SNO from  PMS_DCB_MST_BENEFICIARY  where BENEFICIARY_TYPE_ID="+bentype+" and  office_id="+Office_id+" and status='L')";
			}else 
			{ 
				New_cond ="";
			}
			
			
			try 
			{
			int cl_row=0;
			int ma_row=0;
			int del_client_rc=obj.getCount("PMS_DCB_TRN_BILL_DMD", "where OFFICE_ID="+Office_id+"  "+New_cond+" and BILL_MONTH="+del_billmonth+" and BILL_YEAR="+del_billyear);
			if (del_client_rc!=0)
			{
				try
				{  
				int nmonth_set=0,nyear_set=0;
				  if (Integer.parseInt(del_billmonth)==12)
				  {
					nmonth_set=1;
					nyear_set=(Integer.parseInt(del_billyear)+1);
				  }else							
				  {
					nyear_set=(Integer.parseInt(del_billyear));
					nmonth_set=(Integer.parseInt(del_billmonth)+1);
				  }
				String del_qry="";
				PreparedStatement ps_del=null;
				try
				{
				  del_qry="";
				  del_qry="delete   from PMS_DCB_TRN_BILL_DMD_SCH where office_id="+Office_id+"  and BILL_SNO in ( select BILL_SNO from PMS_DCB_TRN_BILL_DMD where office_id="+Office_id+"  "+New_cond+"  and BILL_MONTH="+del_billmonth+" and BILL_YEAR="+del_billyear+"  "+New_cond+")";
				  ps_del = con.prepareStatement(del_qry);
				  cl_row=ps_del.executeUpdate();
				}catch(SQLException e ) {}						
				ps_del=null;
				try
				{
				  del_qry="";
				  del_qry="delete from PMS_DCB_TRN_BILL_DMD where office_id="+Office_id+"  "+New_cond+" and BILL_MONTH="+del_billmonth+" and BILL_YEAR="+del_billyear+"";
				  ps_del = con.prepareStatement(del_qry);
				  ma_row=ps_del.executeUpdate();
				}catch(SQLException e ) {}
							ps_del=null;
							try
							{
								del_qry="";
								del_qry="delete    from PMS_DCB_OB_YEARLY where office_id="+Office_id+" "+New_cond+"  and month="+nmonth_set+" and fin_year="+nyear_set;						 
								ps_del = con.prepareStatement(del_qry);
								ps_del.executeUpdate();
							}catch(SQLException e ) {}
							try
							{
								del_qry="";
								ps_del=null;
								del_qry="delete from PMS_DCB_TRN_CB_MONTHLY where office_id="+Office_id+"  "+New_cond+" and month="+(Integer.parseInt(del_billmonth))+" and fin_year="+del_billyear;							 
								ps_del = con.prepareStatement(del_qry);
								ps_del.executeUpdate();
							}catch(SQLException e ) {}
							try
							{
								del_qry="";
								ps_del=null;
								del_qry="delete    from PMS_DCB_OB_YEARLY_TOTAL where office_id="+Office_id+"  "+New_cond+" and month="+nmonth_set+" and fin_year="+nyear_set;							 
								ps_del = con.prepareStatement(del_qry);
								ps_del.executeUpdate();
							}catch(SQLException e ) {}
							
							try
							{
								del_qry="";
								ps_del=null;
								del_qry="delete    from PMS_DCB_OTHER_CHARGES where office_id="+Office_id+"  "+New_cond+" and CASHBOOK_MONTH="+(Integer.parseInt(del_billmonth))+" and CASHBOOK_YEAR="+del_billyear;
								ps_del = con.prepareStatement(del_qry);
								ps_del.executeUpdate();
							}catch(SQLException e ) {}
							
							try
							{
								del_qry="";
								ps_del=null;
								del_qry="delete    from PMS_DCB_LEDGER_ACTUAL where office_id="+Office_id+"  and MONTH="+(Integer.parseInt(del_billmonth))+" and YEAR="+del_billyear;
								ps_del = con.prepareStatement(del_qry);
								ps_del.executeUpdate();
							}catch(SQLException e ) {}
							  
							  
							  
							
							  
					}catch (Exception e)
					{
						System.out.println(e);
					}
					xml += "<cl_row>"+cl_row+"</cl_row>";
					xml += "<ma_row>"+ma_row+"</ma_row>";
				}
				else
				{
					xml += "<cl_row>0</cl_row>";
					xml += "<ma_row>0</ma_row>";	
				}
		} catch (Exception e) {
				//   Auto-generated catch block
				e.printStackTrace();
				xml += "<cl_row>0</cl_row>";
				xml += "<ma_row>0</ma_row>";			
		}
				xml += "</result>";
		 		pr_new.write(xml);
		 		pr_new.flush();
		 		pr_new.close();
		}else if (command.equals("pdf")) 
		{
				try 
				{
					Map parameters = new HashMap();
					String ctxpath = null;
					String imagespath=null;
					
					System.out.println("process_code" + process_code);  
					if (process_code.equals("1")) 
					{
						if(Office_id.equals("0"))
						{
						parameters.put("billsno", billsno);
						String BENEFICIARY_SNO = obj.getValue("PMS_DCB_TRN_BILL_DMD", "BENEFICIARY_SNO","where BILL_SNO=" + billsno);
						String BENEFICIARY_TYPE_SNO = obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID","where "+new_cond+" BENEFICIARY_SNO=" + BENEFICIARY_SNO);
						if (BENEFICIARY_TYPE_SNO.equalsIgnoreCase("")) BENEFICIARY_TYPE_SNO = "0";
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/bill_demand_vellore.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("bill_demand_vellore.jasper"));
						parameters.put("ctxpath", ctxpath);
						imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("imgpath", imagespath);  
						System.out.println("parameters"+parameters);
						}else
						{
							parameters.put("billsno", billsno);
							String BENEFICIARY_SNO = obj.getValue("PMS_DCB_TRN_BILL_DMD", "BENEFICIARY_SNO","where BILL_SNO=" + billsno);
							String BENEFICIARY_TYPE_SNO = obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID","where "+new_cond+" BENEFICIARY_SNO=" + BENEFICIARY_SNO);
							if (BENEFICIARY_TYPE_SNO.equalsIgnoreCase("")) BENEFICIARY_TYPE_SNO = "0";
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/bill_demand.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("bill_demand.jasper"));
							parameters.put("ctxpath", ctxpath);
							imagespath = getServletContext().getRealPath("/images/")+File.separator;
							parameters.put("imgpath", imagespath);  
							System.out.println("parameters"+parameters);
							
						}
					}else if (process_code.equals("6")) 
					{
						parameters.put("billsno", billsno);
						String BENEFICIARY_SNO = obj.getValue("PMS_DCB_TRN_BILL_DMD", "BENEFICIARY_SNO","where BILL_SNO=" + billsno);
						String BENEFICIARY_TYPE_SNO = obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID","where "+new_cond+" BENEFICIARY_SNO=" + BENEFICIARY_SNO);
						if (BENEFICIARY_TYPE_SNO.equalsIgnoreCase("")) BENEFICIARY_TYPE_SNO = "0";
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/bill_demand_old.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("bill_demand_old.jasper"));
						parameters.put("ctxpath", ctxpath);
						imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("imgpath", imagespath);  
						System.out.println("parameters"+parameters);
					}else if (process_code.equals("5")) 
					{
						parameters.put("billsno", billsno);
						String BENEFICIARY_SNO = obj.getValue("PMS_DCB_TRN_BILL_DMD", "BENEFICIARY_SNO","where BILL_SNO=" + billsno);
						String BENEFICIARY_TYPE_SNO = obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID","where  "+new_cond+" BENEFICIARY_SNO=" + BENEFICIARY_SNO);
						if (BENEFICIARY_TYPE_SNO.equalsIgnoreCase("")) BENEFICIARY_TYPE_SNO = "0";
						String month=obj.setValue("billmonth", request); 
						String year=obj.setValue("billyear", request);
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/full_all_bill_demand.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("full_all_bill_demand.jasper"));
						imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("ctxpath", ctxpath);
						parameters.put("imgpath", imagespath);
						parameters.put("office_id", Office_id);
						parameters.put("month", month);
						parameters.put("year", year);
				  }
					else if (process_code.equals("4")) 
					{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/BlockWisediv_bill_demand.jasper");
						String month=obj.setValue("month", request); 
						String year=obj.setValue("year", request);				  
						ctxpath = path.substring(0, path.lastIndexOf("BlockWisediv_bill_demand.jasper"));						
						imagespath = getServletContext().getRealPath("/images/")+File.separator;
						String blk=obj.setValue("blk", request);
						parameters.put("ctxpath", ctxpath);
						parameters.put("imgpath", imagespath);
						parameters.put("office_id", Office_id);
						parameters.put("month", month);
						parameters.put("year", year);
						parameters.put("blk", blk);
					}else if (process_code.equals("3")) 
					{
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/subdiv_bill_demand.jasper");
						String month=obj.setValue("month", request); 
						String year=obj.setValue("year", request);				  
						ctxpath = path.substring(0, path.lastIndexOf("subdiv_bill_demand.jasper"));						
						imagespath = getServletContext().getRealPath("/images/")+File.separator;  
						String bentype=obj.setValue("bentype", request);
						parameters.put("ctxpath", ctxpath);
						parameters.put("imgpath", imagespath);
						parameters.put("office_id", Office_id);
						parameters.put("month", month);
						parameters.put("year", year);
						parameters.put("bentype", bentype);
					}else if (process_code.equals("10")) 
					{
						String flag=obj.setValue("flag", request);
						System.out.println("flag --> " + flag);  
						String month=obj.setValue("month", request);
						String mvalue = obj.month_val(obj.setValue("month", request));
						String BEN_TYPE_ID= obj.setValue("BEN_TYPE_ID", request)  ;  
						String year=obj.setValue("year", request);
						parameters.put("office_id", Office_id);
						parameters.put("month", month); 
						parameters.put("mvalue", mvalue);
						parameters.put("year", year);  
						parameters.put("office_name", Office_name);  
						if (flag.equalsIgnoreCase("1"))
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/outstanding.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("outstanding.jasper"));						
							parameters.put("ben_type", BEN_TYPE_ID);  
							parameters.put("totalnumber", obj.setValue("nots", request));
							imagespath = getServletContext().getRealPath("/images/")+File.separator;
							parameters.put("imgpath", imagespath);  
						}  
						if (flag.equalsIgnoreCase("2"))  
						{
							path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pumpingzero.jasper");
							ctxpath = path.substring(0, path.lastIndexOf("pumpingzero.jasper"));
							imagespath = getServletContext().getRealPath("/images/")+File.separator;
							parameters.put("imgpath", imagespath);  
						}  
					}
					else    
					{
						String month=obj.setValue("month", request);
						String year=obj.setValue("year", request);
						 String qry_test=" SELECT DMD.BILL_SNO, "+
						"   DMD.OFFICE_ID, "+
						"   DMD.BENEFICIARY_SNO, "+
						  "   TO_CHAR(DMD.BILL_PERIOD_FROM,'dd/mm/yyyy') AS BILL_PERIOD_FROM, "+
						  "   TO_CHAR(DMD.BILL_PERIOD_TO,'dd/mm/yyyy')   AS BILL_PERIOD_TO, "+
						  "  CASE WHEN LENGTH(DMD.BILL_MONTH::varchar)<2 "+
						      "   THEN CONCAT('0',DMD.BILL_MONTH) "+
						      "      ELSE CONCAT('',DMD.BILL_MONTH) END "+
						            "  AS BILL_MONTH, "+
						            "  DMD.BILL_YEAR, "+
						  "  CASE WHEN LENGTH(DMD.DIV_BILL_NO::varchar)<2 "+
						      "    THEN CONCAT('0',DMD.DIV_BILL_NO) "+
						"    ELSE CONCAT('',DMD.DIV_BILL_NO) END "+
						            "    AS DIV_BILL_NO, "+
						            "  DMD.NET_CONSUMPTION, "+
						  " TO_CHAR(DMD.BILLING_DT,'dd/mm/yyyy') AS BILLING_DT, "+
						  "  BEN.BENEFICIARY_NAME, "+
						  "  BEN.BENEFICIARY_TYPE_ID, "+
						  "  COALESCE(BEN.BILLING_ADDRESS1,NULL,' ',BEN.BILLING_ADDRESS1) AS BILLING_ADDRESS1, "+
						  "  COALESCE(BEN.BILLING_ADDRESS2,NULL,' ',BEN.BILLING_ADDRESS2) AS BILLING_ADDRESS2, "+
						  "  COALESCE(BEN.BILLING_ADDRESS3,NULL,' ',BEN.BILLING_ADDRESS3) AS BILLING_ADDRESS3, "+
						  " COALESCE(BEN.BILLING_PIN_CODE,NULL,' ',CONCAT('Pincode: ',BEN.BILLING_PIN_CODE)) AS BILLING_PIN_CODE, "+
						  "   DIV.OFFICE_NAME, 	 "+
						  "   COALESCE(DMD.COLN_INT_WC,NULL,'0',DMD.COLN_INT_WC) AS COLN_INT_WC, "+
						  "  COALESCE(DMD.DMD_FOR_MTH_MC,NULL,'0',DMD.DMD_FOR_MTH_MC) AS DMD_FOR_MTH_MC, "+
						 "   COALESCE(DMD.OB_YESTER_YR_WC,NULL,'0', DMD.OB_YESTER_YR_WC) AS  OB_YESTER_YR_WC , "+
						  "  COALESCE(DMD.ob_cur_yr_wc,NULL,'0',DMD.ob_cur_yr_wc) AS ob_cur_yr_wc, "+
						  " COALESCE(DMD.COLN_YESTER_YR_WC,NULL,'0', DMD.COLN_YESTER_YR_WC) AS  COLN_YESTER_YR_WC, "+
						  " COALESCE(DMD.coln_cur_yr_wc,NULL,'0',DMD.coln_cur_yr_wc) AS coln_cur_yr_wc, "+
						  " COALESCE(DMD.totob,NULL,'0', DMD.totob) AS  totob, "+
						  "  COALESCE(DMD.INT_CALC_WC,NULL,'0', DMD.INT_CALC_WC) AS INT_CALC_WC, "+
						  "  COALESCE(DMD.coln_maint,NULL,'0', DMD.coln_maint) AS coln_maint,  "+
						  "  COALESCE(DMD.OB_MAINT_CHARGES,NULL,'0', DMD.OB_MAINT_CHARGES) AS OB_MAINT_CHARGES, "+ 
						  "  COALESCE(INT.INT_RATE,NULL,'0',INT.INT_RATE) AS INT_RATE,  "+
						  "  COALESCE(DMD.DMD_INT_FOR_MTH_WC ,NULL,'0',DMD.DMD_INT_FOR_MTH_WC ) AS DMD_INT_FOR_MTH_WC , "+ 
						  "  COALESCE(DMD.CB_INT_AMT_WC ,NULL,'0',DMD.CB_INT_AMT_WC ) AS CB_INT_AMT_WC ,  "+
						  "  COALESCE(DMD.COLN_INT_WC ,NULL,'0',DMD.COLN_INT_WC ) AS COLN_INT_WC ,  "+
						  " COALESCE(DMD.OB_INT_AMT_WC ,NULL,'0',DMD.OB_INT_AMT_WC ) AS OB_INT_AMT_WC , "+   
						  " COALESCE(DMD.ADD_CHARGES_WC ,NULL,'0',DMD.ADD_CHARGES_WC ) AS ADD_CHARGES_WC ,   "+  
						"   COALESCE(DMD.MINUS_CHARGES_WC ,NULL,'0',DMD.MINUS_CHARGES_WC ) AS MINUS_CHARGES_WC , "+  
						  "   DMD.WC_MTH_TOTAL,DMD.CB_CUR_YR_WC, "+
						  "   DMD.MONTH_BILL_AMT, "+
						  "   COALESCE(DMD.AMT_WORDS,NULL,' ',CONCAT('Bill Value is Rs.',DMD.AMT_WORDS)) AS AMT_WORDS, "+
						  "   BTP.ADDRESS_TO  ,BTP.REMARKS "+
						  " FROM ( "+
								" (SELECT BILL_SNO,ADDRESS_TO, "+
								  "   OFFICE_ID, "+
						    "  BENEFICIARY_SNO, "+
						    "  BILL_PERIOD_FROM, "+
						    " BILL_PERIOD_TO, "+
						    "  BILL_MONTH, "+
						    "  BILL_YEAR, "+
						    "  DIV_BILL_NO, "+
						    "  NET_CONSUMPTION, "+
						    " BILLING_DT , "+
						    "   COLN_INT_WC, "+
						    " COLN_YESTER_YR_WC, "+
						    "   ob_cur_yr_wc, "+
						    "   OB_YESTER_YR_WC, "+
						    "   (ob_cur_yr_wc+OB_YESTER_YR_WC) as totob, "+
						    "   coln_cur_yr_wc,CB_CUR_YR_WC, "+
						    "    ob_maint_charges, "+
						    "   INT_CALC_WC, "+
						    "   coln_maint, "+
						    "   WC_MTH_TOTAL, "+
						    "   MONTH_BILL_AMT, "+
						    "  AMT_WORDS,DMD_INT_FOR_MTH_WC, "+
						    "    CB_INT_AMT_WC , "+
						    "    OB_INT_AMT_WC,DMD_FOR_MTH_MC  , ADD_CHARGES_WC,MINUS_CHARGES_WC "+   
						    "   FROM PMS_DCB_TRN_BILL_DMD "+
						  "   WHERE BILL_SNO in (select BILL_SNO from PMS_DCB_TRN_BILL_DMD where    OFFICE_ID="+Office_id+" and BILL_MONTH="+month+" and BILL_YEAR ="+year+"   ) "+
						  "   ) DMD "+
						  " JOIN "+
						" (SELECT BENEFICIARY_SNO, "+
								  "  upper(BENEFICIARY_NAME) as BENEFICIARY_NAME , "+
						    "  BENEFICIARY_TYPE_ID, "+
						    "   DISTRICT_CODE, "+
						    "   BLOCK_SNO, "+
						    "   BILLING_ADDRESS1, "+
						    "   BILLING_ADDRESS2, "+
						    "   BILLING_ADDRESS3, "+
						    "   BILLING_PIN_CODE, "+
						    "   OTHERS_PRIVATE_SNO "+
						    "  FROM PMS_DCB_MST_BENEFICIARY where status='L' "+
							  "  ) BEN "+
						  " ON BEN.BENEFICIARY_SNO=DMD.BENEFICIARY_SNO "+
						" JOIN "+
						"  (SELECT OFFICE_NAME, OFFICE_ID FROM com_mst_offices "+
								  "  )div "+
						  " ON div.OFFICE_ID=DMD.OFFICE_ID "+
						" JOIN "+
						"   ( SELECT BENEFICIARY_TYPE,INT_RATE FROM PMS_DCB_MST_INT  where ACTIVE_STATUS='A' "+
							  "   ) INT "+
						  " ON INT.BENEFICIARY_TYPE=BEN.BENEFICIARY_TYPE_ID "+
						" JOIN "+
						" ( "+
								  " SELECT BEN_TYPE_ID,ADDRESS_TO,REMARKS FROM PMS_DCB_BEN_TYPE "+
						      " ) BTP "+
						  " 	ON BEN.BENEFICIARY_TYPE_ID=BEN_TYPE_ID "+
					 " ) ";
						 
						 System.out.println("DemandQuery => "+qry_test );
						 
						 if(Office_id.equals("7179"))
						 {						 
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/all_bill_demand_Vellore.jasper");
						ctxpath = path.substring(0, path.lastIndexOf("all_bill_demand_Vellore.jasper"));	
						
						 }
						 else
						 {
							 System.out.println("Query is "+qry_test);
						//	 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/all_bill_demand.jasper");
						//	 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/all_bill_demand1.jasper");

						//	 ctxpath = path.substring(0, path.lastIndexOf("all_bill_demand.jasper"));	
								path = getServletContext().getRealPath("/WEB-INF/ReportSrc/all_bill_demand_Vellore.jasper");
								ctxpath = path.substring(0, path.lastIndexOf("all_bill_demand_Vellore.jasper"));	
							
						 }
											
						imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("imgpath", imagespath);
						parameters.put("ctxpath", ctxpath);
						parameters.put("office_id", Office_id);
						parameters.put("month", month);
						parameters.put("year", year);					
					}
					JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
					OutputStream outuputStream1 = response.getOutputStream();
					JRExporter exporter = null;
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition","attachment; filename=\"REPORT.pdf\"");
					exporter = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream1);
					exporter.exportReport();					
					outuputStream1.close();
				} catch (JRException e) {
					throw new ServletException(e);
				} catch (Exception e) {
					//   Auto-generated catch block
					e.printStackTrace();
				}
		} else {			
			pr_new =response.getWriter();
			if (command.equals("show")) {
				qry = "select "
						+ "DMD.BILL_SNO,"
						+ "DMD.OFFICE_ID,"
						+ "DMD.BENEFICIARY_SNO,"
						+ "to_char(DMD.BILL_PERIOD_FROM,'dd/mm/yyyy') as  BILL_PERIOD_FROM,"
						+ "to_char(DMD.BILL_PERIOD_TO,'dd/mm/yyyy') as  BILL_PERIOD_TO,"
						+ "DMD.BILL_MONTH," + "DMD.BILL_YEAR,"
						+ "DMD.DIV_BILL_NO," + "DMD.NET_CONSUMPTION,"
						+ "to_char(DMD.BILLING_DT,'dd/mm/yyyy') as BILLING_DT,"
						+ "BEN.BENEFICIARY_NAME," + "BEN.BENEFICIARY_TYPE_ID,"
						+ "BEN.BILLING_ADDRESS1," + "BEN.BILLING_ADDRESS2,"
						+ "BEN.BILLING_ADDRESS3," + "BEN.BILLING_PIN_CODE,"
						+ "DIV.OFFICE_NAME ," + "METRE.METRE_LOCATION "
						+ ",PR.METRE_INITIAL_READING"
						+ ",PR.METRE_CLOSING_READING " + ",PR.QTY_CONSUMED"
						+ ",METRE.MULTIPLY_FACTOR" + ",METRE.MIN_BILL_QTY,"
						+ "METRE.ALLOTED_QTY" + ",INT.INT_RATE,"
						+ "DMD.WC_INT_COLN," + " DMD.WC_OB," + "DMD.INT_CALC,"
						+ "SCH.SCH_NAME," + "STYP.SCH_TYPE_DESC,"
						+ "DMD.MAINT_OB," + "DMD.INT_CALC," + "DMD.WC_COLN,"
						+ "DMD.MAINT_COLN," + "WC.TARIFF_RATE,"
						+ "WC.EXCESS_RATE ," + "WC.EXCESS_QTY ,WC.EXCESS_AMT "
						+ " from " + "(" + "(" + "select BILL_SNO,"
						+ "OFFICE_ID," + "BENEFICIARY_SNO,"
						+ "BILL_PERIOD_FROM," + "BILL_PERIOD_TO,"
						+ "BILL_MONTH," + "BILL_YEAR," + "DIV_BILL_NO,"
						+ "NET_CONSUMPTION," + "BILLING_DT ," + "WC_INT_COLN,"
						+ "WC_OB," + "WC_COLN," + "MAINT_OB," + "INT_CALC,"
						+ "MAINT_COLN " + " from "
						+ " PMS_DCB_TRN_BILLING_DMD   where BILL_SNO="
						+ billsno
						+ ") DMD "
						+ "join("
						+ "select"
						+ "   BENEFICIARY_SNO,"
						+ "    BENEFICIARY_NAME,"
						+ "     BENEFICIARY_TYPE_ID,"
						+ "      DISTRICT_CODE,"
						+ " BLOCK_SNO,"
						+ "  BILLING_ADDRESS1,"
						+ "   BILLING_ADDRESS2,"
						+ "    BILLING_ADDRESS3,"
						+ "     BILLING_PIN_CODE "
						+ " from "
						+ "       PMS_DCB_MST_BENEFICIARY  where STATUS='L' "
						+ ") BEN"
						+ " on BEN.BENEFICIARY_SNO=DMD.BENEFICIARY_SNO "
						+ " join("
						+ "select"
						+ "   OFFICE_NAME,"
						+ "    OFFICE_ID "
						+ " from  "
						+ "     com_mst_offices "
						+ ")div"
						+ " on div.OFFICE_ID=DMD.OFFICE_ID "
						+ " join("
						+ " select "
						+ " BILL_SNO, "
						+ "  PR_SNO"
						+ "  ,TARIFF_RATE,"
						+ "  EXCESS_RATE,"
						+ "  EXCESS_QTY,"
						+ "  EXCESS_AMT, "
						+ "  METRE_SNO "
						+ " from "
						+ "  PMS_DCB_WC_BILLING "
						+ " where "
						+ "  BILL_SNO="
						+ billsno
						+ " )WC "
						+ " on WC.BILL_SNO=DMD.BILL_SNO "
						+ " join "
						+ " ( "
						+ " select  "
						+ "  PR_SNO,"
						+ "   "
						+ " METRE_INITIAL_READING, "
						+ " METRE_CLOSING_READING, "
						+ " QTY_CONSUMED "
						+ " from  "
						+ " PMS_DCB_TRN_MONTHLY_PR "
						+ " ) PR "
						+ " on PR.PR_SNO=WC.PR_SNO "
						+ "       join "
						+ " ( "
						+ " select "
						+ " BENEFICIARY_SNO, "
						+ " METRE_SNO,"
						+ "MULTIPLY_FACTOR, "
						+ "MIN_BILL_QTY"
						+ ",METRE_LOCATION ,"
						+ "TARIFF_RATE,"
						+ "EXCESS_TARIFF_RATE,"
						+ "SCH_TYPE_ID,"
						+ "SCHEME_SNO,"
						+ "ALLOTED_QTY "
						+ " from  "
						+ " PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' "
						+ " )METRE "
						+ " on WC.METRE_SNO=METRE.METRE_SNO AND METRE.BENEFICIARY_SNO=DMD.BENEFICIARY_SNO"
						+ " join ( "
						+ "select "
						+ "BENEFICIARY_TYPE,"
						+ "INT_RATE"
						+ " from "
						+ "PMS_DCB_MST_INT ) INT "
						+ "  on INT.BENEFICIARY_TYPE=BEN.BENEFICIARY_TYPE_ID "
						+ "JOIN ("
						+ "SELECT "
						+ " SCH_TYPE_ID,"
						+ "SCH_TYPE_DESC"
						+ "  FROM "
						+ "PMS_SCH_LKP_TYPE"
						+ "  )STYP "
						+ "  ON METRE.SCH_TYPE_ID=STYP.SCH_TYPE_ID "
						+ " join   (  "
						+ " SELECT "
						+ "  SCH_SNO, "
						+ "  SCH_NAME "
						+ "  FROM PMS_SCH_MASTER "
						+ "   )SCH "
						+ "   ON METRE.SCHEME_SNO=SCH.SCH_SNO" + " " + ")";
				try {
					rs = obj.getRS(qry);
					double total_vlaue = 0.0;
					while (rs.next()) 
					{
						xml += "<bill_sno>"+obj.isNull(rs.getString("BILL_SNO"), 2)+"</bill_sno>";
						xml += "<OFFICE_NAME>"+obj.isNull(rs.getString("OFFICE_NAME"), 2)+"</OFFICE_NAME>";
						xml += "<BENEFICIARY_SNO>"+obj.isNull(rs.getString("BENEFICIARY_SNO"),2) + "</BENEFICIARY_SNO>";
						xml += "<BILL_PERIOD_FROM>"+obj.isNull(rs.getString("BILL_PERIOD_FROM"),2) + "</BILL_PERIOD_FROM>";
						xml += "<BILL_PERIOD_TO>"+obj.isNull(rs.getString("BILL_PERIOD_TO"), 2)+"</BILL_PERIOD_TO>";
						xml += "<BILL_MONTH>"+obj.isNull(rs.getString("BILL_MONTH"), 2)+"</BILL_MONTH>";
						xml += "<BILL_YEAR>"+obj.isNull(rs.getString("BILL_YEAR"), 2)+"</BILL_YEAR>";
						xml += "<NET_CONSUMPTION>"+obj.isNull(rs.getString("NET_CONSUMPTION"),2) + "</NET_CONSUMPTION>";
						xml += "<DIV_BILL_NO>"+obj.isNull(rs.getString("DIV_BILL_NO"), 2)+"</DIV_BILL_NO>";
						xml += "<BENEFICIARY_TYPE_ID>"+obj.isNull(rs.getString("BENEFICIARY_TYPE_ID"), 2)+"</BENEFICIARY_TYPE_ID>";
						MULTIPLY_FACTOR = obj.isNull(rs.getString("MULTIPLY_FACTOR"), 2);
						QTY_CONSUMED = obj.isNull(rs.getString("QTY_CONSUMED"),2);
						xml += "<BILLING_DT>"+ obj.isNull(rs.getString("BILLING_DT"), 2)+"</BILLING_DT>";
						xml += "<BENEFICIARY_NAME>"+obj.isNull(rs.getString("BENEFICIARY_NAME"),2)+"</BENEFICIARY_NAME>";
						xml += "<BILLING_ADDRESS1>"+obj.isNull(rs.getString("BILLING_ADDRESS1"),2)+"</BILLING_ADDRESS1>";
						xml += "<BILLING_ADDRESS2>"+obj.isNull(rs.getString("BILLING_ADDRESS2"),2)+"</BILLING_ADDRESS2>";
						xml += "<BILLING_ADDRESS3>"+obj.isNull(rs.getString("BILLING_ADDRESS3"),2)+"</BILLING_ADDRESS3>";
						xml += "<BILLING_PIN_CODE>"+obj.isNull(rs.getString("BILLING_PIN_CODE"),2)+"</BILLING_PIN_CODE>";
						xml += "<METRE_LOCATION>"+obj.isNull(rs.getString("METRE_LOCATION"), 2).trim()+"</METRE_LOCATION>";
						xml += "<METRE_CLOSING_READING>"+obj.isNull(rs.getString("METRE_CLOSING_READING"), 2)+"</METRE_CLOSING_READING>";
						xml += "<METRE_INITIAL_READING>"+obj.isNull(rs.getString("METRE_INITIAL_READING"), 2)+"</METRE_INITIAL_READING>";
						xml += "<MULTIPLY_FACTOR>"+decForRate.format(Double.parseDouble(MULTIPLY_FACTOR))+"</MULTIPLY_FACTOR>";
						xml += "<TARIFF_RATE>"+obj.isNull(rs.getString("TARIFF_RATE"), 2)+"</TARIFF_RATE>";
						xml += "<EXCESS_TARIFF_RATE>"+obj.isNull(rs.getString("EXCESS_RATE"), 2)+"</EXCESS_TARIFF_RATE>";
						xml += "<QTY_CONSUMED>"+ QTY_CONSUMED+ "</QTY_CONSUMED>";
						xml += "<INT_RATE>"+obj.isNull(rs.getString("INT_RATE"), 2)+"</INT_RATE>";
						xml += "<WC_OB>" + obj.isNull(rs.getString("WC_OB"), 2)+"</WC_OB>";
						xml += "<WC_INT_COLN>"+obj.isNull(rs.getString("WC_INT_COLN"), 2)+"</WC_INT_COLN>";
						xml += "<MAINT_OB>"+obj.isNull(rs.getString("MAINT_OB"), 2)+"</MAINT_OB>";
						xml += "<WC_COLN>"+obj.isNull(rs.getString("WC_COLN"), 2)+"</WC_COLN>";
						xml += "<MAINT_COLN>"+obj.isNull(rs.getString("MAINT_COLN"), 2)+"</MAINT_COLN>";
						xml += "<MIN_BILL_QTY>"+obj.isNull(rs.getString("MIN_BILL_QTY"), 2)+"</MIN_BILL_QTY>";
						xml += "<ALLOTED_QTY>"+obj.isNull(rs.getString("ALLOTED_QTY"), 2)+"</ALLOTED_QTY>";
						xml += "<EXCESS_QTY>"+obj.isNull(rs.getString("EXCESS_QTY"), 2)+"</EXCESS_QTY>";
						xml += "<EXCESS_AMT>"+obj.isNull(rs.getString("EXCESS_AMT"), 2)+"</EXCESS_AMT>";
						xml += "<INT_CALC>"+obj.isNull(rs.getString("INT_CALC"), 2)+"</INT_CALC>";
						xml += "<SCH_TYPE_DESC>"+obj.isNull(rs.getString("SCH_TYPE_DESC"), 2)+"</SCH_TYPE_DESC>";
						xml += "<SCH_NAME><![CDATA["+obj.isNull(rs.getString("SCH_NAME"), 2)+"]]>";
						ben_type = obj.isNull(rs.getString("BENEFICIARY_TYPE_ID"), 2);
						// ************************************ Rs Calculated
						double cal = Double.parseDouble(MULTIPLY_FACTOR)* Double.parseDouble(QTY_CONSUMED);
						double MIN_BILL_QTY = Double.parseDouble(obj.isNull(rs.getString("MIN_BILL_QTY"), 1));
						if (Integer.parseInt(ben_type) > 6) 
						{
							obj.testQry(Double.toString(MIN_BILL_QTY));
							if (MIN_BILL_QTY > cal) 
							{
								total_vlaue = MIN_BILL_QTY* Double.parseDouble(obj.isNull(rs.getString("TARIFF_RATE"), 1));
								xml += "<total_consumption>"+decForRate.format(MIN_BILL_QTY)+"</total_consumption>";
								xml += "<total_vlaue>"+decForRate.format(total_vlaue)+"</total_vlaue>";
								net_consumption += MIN_BILL_QTY;
								net_consumption_value += total_vlaue;
							} else {
								total_vlaue = cal* Double.parseDouble(obj.isNull(rs.getString("TARIFF_RATE"), 1));
								xml += "<total_consumption>"+decForRate.format(cal)+"</total_consumption>";
								xml += "<total_vlaue>"+decForRate.format(total_vlaue)+"</total_vlaue>";
								net_consumption += cal;
								net_consumption_value += total_vlaue;
							}
						} else {
							total_vlaue = cal* Double.parseDouble(obj.isNull(rs.getString("TARIFF_RATE"), 1));
							xml += "<total_consumption>"+decForRate.format(cal)+"</total_consumption>";
							xml += "<total_vlaue>"+decForRate.format(total_vlaue)+"</total_vlaue>";
							net_consumption += cal;
							net_consumption_value += total_vlaue;
						}
						// Rs_Value=obj.getValue("dual",
						// "to_char(to_date('"+(int)((net_consumption_value+Double.parseDouble(obj.isNull(rs.getString("INT_CALC"),
						// 2))))+"','J'), 'JSP')","");
					}
					qry = "";
					qry = "  select \n" + "	SCH.SCH_NAME,\n"
							+ "	WCB.SCHEME_SNO,\n" + "	WCB.SCH_TYPE_ID,\n"
							+ "	WCB.QTY ," + "   STYP.SCH_TYPE_DESC" + "   \n"
							+ " from \n" + " (\n" + " (\n" + " select\n"
							+ "	sch_type_id,\n" + "	scheme_sno,"
							+ "	SUM(QTY_CONSUMED+EXCESS_QTY) AS QTY\n"
							+ " from \n" + "	PMS_DCB_WC_BILLING " + " where "
							+ "	pms_dcb_wc_billing.bill_sno= "
							+ billsno
							+ " GROUP BY "
							+ "	sch_type_id ,"
							+ "	scheme_sno	\n"
							+ " )WCB \n"
							+ " join\n"
							+ " ( \n"
							+ " select\n"
							+ " 	sch_sno,\n"
							+ "	SCH_NAME \n"
							+ " from "
							+ "	PMS_SCH_MASTER\n"
							+ ")SCH\n"
							+ " ON SCH.sch_sno=WCB.scheme_sno\n"
							+ ")\n"
							+ " JOIN ("
							+ "SELECT "
							+ " SCH_TYPE_ID,"
							+ "SCH_TYPE_DESC"
							+ "  FROM "
							+ "PMS_SCH_LKP_TYPE"
							+ "  )STYP "
							+ "  ON WCB.sch_type_id=STYP.SCH_TYPE_ID ";
					rs = obj.getRS(qry);
					int sch_row = 0;
					while (rs.next()) {
						sch_row++;
						xml += "<L_SCH_NAME><![CDATA["+obj.isNull(rs.getString("SCH_NAME"), 2)+"]]></L_SCH_NAME>";
						xml += "<L_SCH_TYPE_DESC>"+obj.isNull(rs.getString("SCH_TYPE_DESC"), 2)+"</L_SCH_TYPE_DESC>";
						xml += "<L_QTY>" + obj.isNull(rs.getString("QTY"), 2)+"</L_QTY>";
					}
					xml += "<sch_row>" + sch_row + "</sch_row>";
					xml += "<net_consumption>" + net_consumption+"</net_consumption>";
					xml += "<net_consumption_value>"+decForRate.format(net_consumption_value)+"</net_consumption_value>";
				} catch (Exception e)
				{
					System.out.println("" + e);
				}
			}
			xml += "</result>";
			pr_new.write(xml);
			pr_new.flush();
			pr_new.close();
		}
		} catch (Exception e) {
			 System.out.println("Error"+e);
		}
		obj.conClose(con);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//   Auto-generated method stub
	}
}
