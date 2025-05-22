package Servlets.AME.AME1.AMEM.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter; 
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class report
 */
public class ame_report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			doGet(request,response);
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Controller obj_rs = null;
		Controller obj = null;
		String userid = "", Office_id = "",Office_name="", AC_UNIT_ID = "";
		response.setContentType(CONTENT_TYPE);
		Connection con = null;
		obj = new Controller();
		obj_rs = new Controller();
		HttpSession session = request.getSession(false);
		
		try {
			con = obj.con();
		} catch (Exception e3) {

			e3.printStackTrace();
		}
		try { userid = (String) session.getAttribute("UserId"); } catch(Exception e) {userid="0";} 
		if (userid == null) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		try {
			
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		AC_UNIT_ID = obj.getValue("FAS_MST_ACCT_UNITS","ACCOUNTING_UNIT_ID", "where ACCOUNTING_UNIT_OFFICE_ID="+ Office_id);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (Office_id.equals("5000"))
			Office_id = obj.setValue("office_id", request);  
		
		String process_code = obj.setValue("process_code", request);   
		Map parameters = new HashMap();
		System.out.println("AME-------->ame_report---------->process_code("+process_code+")");
		File reportFile=null;
		String path ="";
		if (Integer.parseInt(process_code) == 2) 
		{
			path = getServletContext().getRealPath("/WEB-INF/PDF/estimate_2.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/estimate_2.jasper"));
		    parameters.put("sch_sno", obj.setValue("sch_sno", request));
			parameters.put("office_id", Office_id);			   
			parameters.put("year", obj.setValue("pyear", request));
			parameters.put("month", obj.setValue("pmonth", request));
			parameters.put("year2", obj.setValue("pyear", request));
		}else if(Integer.parseInt(process_code) == 3) {
			parameters.put("Office_name", Office_name);
			parameters.put("fin_yr", obj.setValue("pyear", request));
			parameters.put("office_id", Office_id);
			 path = getServletContext().getRealPath("/WEB-INF/PDF/bud_est_trans.jasper");
		     reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/bud_est_trans.jasper"));
		}
		else if (Integer.parseInt(process_code) ==1)
		{
			
			parameters.put("fin_year", obj.setValue("pyear", request));
			parameters.put("office_id", Office_id);	
		    path = getServletContext().getRealPath("/WEB-INF/PDF/budest.jasper");		 
		    reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/budest.jasper"));
		    
		}else if(Integer.parseInt(process_code) == 4) { 
			parameters.put("office_name", Office_name);
			parameters.put("fin_year", obj.setValue("pyear", request));
			path = getServletContext().getRealPath("/WEB-INF/PDF/AME_Master_1.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/AME_Master_1.jasper"));
		  	
			  
		}else if(Integer.parseInt(process_code) == 5) { 
			parameters.put("office_name", Office_name);
			path = getServletContext().getRealPath("/WEB-INF/PDF/AME_Master_1.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/AME_Master_1.jasper"));
		  	
			 
		}
		else  if(Integer.parseInt(process_code) == 6)  
		{
			
			parameters.put("fin_year", obj.setValue("pyear", request));
			parameters.put("sch_sno", obj.setValue("sch_sno", request));
			parameters.put("office_id", Office_id);	
		    path = getServletContext().getRealPath("/WEB-INF/PDF/estimate.jasper");
		    reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/estimate.jasper"));
		}
		else if (Integer.parseInt(process_code) == 7) 
		{
			String cyear="";
			int m= Integer.parseInt(obj.setValue("pmonth",request));
			if (m !=0 ) { 
				if ( m>=4 )
					cyear=obj.setValue("pyear",request).split("-")[0];
				else
					cyear=obj.setValue("pyear",request).split("-")[1];
				}
			String mvalue=obj.month_val(Integer.toString(m));
			
			  String pmonth= "";
			  if ( m !=0)
			  {
				  if (m<=3)
				  {
					  for(int p=4;p<=12;p++)
					  {
						  if (p==4)
							  pmonth+=""+p;
						  else
							  pmonth+=","+p;
					  }  
					  for (int q=1;q<=m;q++) 
					  {
						  pmonth+=","+q;
					  }
				  }
				  else
				  {
					  for (int p=4;p<=m;p++)
					  {
						  if (p==4)
							  pmonth+=""+p;
						  else
							  pmonth+=","+p;
					  }
				  }
			  }
			  
			String []fin_year1=obj.prvfinyear(obj.setValue("pyear",request),2).split(",");  
			  
			parameters.put("sch_sno", obj.setValue("sch_sno", request));
			parameters.put("Office_id", Office_id);
			parameters.put("Office_name", Office_name);
			parameters.put("year", obj.setValue("pyear", request));
			parameters.put("mvalue", mvalue+" "+cyear);
			parameters.put("pmonth", pmonth);
			parameters.put("cm", m);
			parameters.put("fin_year1", fin_year1[1]);
			parameters.put("fin_year2", fin_year1[0]);  
			path = getServletContext().getRealPath("/WEB-INF/PDF/SchemePerformace.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/SchemePerformace.jasper"));

		}
		else if (Integer.parseInt(process_code) == 8) 
		{
			parameters.put("sch_sno", obj.setValue("sch_sno", request));
			parameters.put("Office_id", Office_id);
			parameters.put("Office_name", Office_name);
			parameters.put("year", obj.setValue("pyear", request));
			
			String sname="";
			try
			{
			sname=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+ obj.setValue("sch_sno", request));
			}catch (Exception e) {}
			parameters.put("sch_name",sname);
			path = getServletContext().getRealPath("/WEB-INF/PDF/SchemePerformace_current.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/SchemePerformace_current.jasper"));
		} 
		else if (Integer.parseInt(process_code) == 9) 
		{   
			String mvalue="";
			String pmonth=obj.setValue("pmonth", request);
			String pmonth1=obj.setValue("pmonth1", request);
			
			String pyear= obj.setValue("pyear", request);
			String pyear1= obj.setValue("pyear1", request);
			
			if (pmonth.equalsIgnoreCase(pmonth1) && pyear.equalsIgnoreCase(pyear1))
			{
				mvalue=obj.month_val(pmonth)+"' "+obj.setValue("pyear", request);
			}
			else  
			{
				mvalue=obj.month_val(pmonth)+"' "+pyear+"-"+obj.month_val(pmonth1)+"' "+pyear1;
			}
		
			parameters.put("office_id", Office_id);
 			parameters.put("year",pyear);
 			parameters.put("year1",pyear1 );
			parameters.put("month", pmonth);		 
			parameters.put("month1", pmonth1); 	   
			parameters.put("monthvalue", mvalue);
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Total_pump_qty.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Total_pump_qty.jasper"));
		}else if (Integer.parseInt(process_code) == 29) 
		{   
			String mvalue="";
			String pmonth=obj.setValue("pmonth", request);
			String pyear= obj.setValue("pyear", request);
			parameters.put("office_id", Office_id);
 			parameters.put("year",pyear);  
 			int days=Integer.parseInt(obj.month_val2(Integer.parseInt(pmonth), Integer.parseInt(pyear)) );
 			parameters.put("days",days);
			parameters.put("month", pmonth);		           
			parameters.put("monthvalue", obj.month_val(obj.setValue("pmonth", request)));
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Total_pump_qty_with_Design.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Total_pump_qty_with_Design.jasper"));
		}
		else if (Integer.parseInt(process_code) == 19) 
		{    
			String mvalue="";
			String pyear= obj.setValue("pyear", request);
			int year2= Integer.parseInt(obj.setValue("pyear", request))+1;;
			
			mvalue=obj.month_val("4")+" "+obj.setValue("pyear", request);
			mvalue+="-"+obj.month_val("3")+" "+year2;
			parameters.put("mvalue", mvalue);  
			parameters.put("year1", obj.setValue("pyear", request));		 		
			parameters.put("year2", Integer.toString(year2));
			parameters.put("office_name", Office_name);
			parameters.put("office_id", Office_id);
 			parameters.put("year",pyear);  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/fin_year_billed_qty.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/fin_year_billed_qty.jasper"));
		} else if (Integer.parseInt(process_code) == 99) 
		{ 
			String mvalue="";
			String pmonth=obj.setValue("pmonth", request);
			String pmonth1=pmonth;
			
			String pyear= obj.setValue("pyear", request);
			String pyear1=pyear;
			
			if (pmonth.equalsIgnoreCase(pmonth1) && pyear.equalsIgnoreCase(pyear1))
			{
				mvalue=obj.month_val(pmonth)+" "+obj.setValue("pyear", request);
			}
			else    
			{
				mvalue=obj.month_val(pmonth)+" "+pyear+"-"+obj.month_val(pmonth1)+" "+pyear1;
			}
		
			parameters.put("office_id", Office_id);
 			parameters.put("year",pyear);
 			parameters.put("year1",pyear1 );
			parameters.put("month", pmonth);  
			parameters.put("month1", pmonth1); 	   
			parameters.put("monthvalue", mvalue);
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Total_pump_qty.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Total_pump_qty.jasper"));
		} 
		else if (Integer.parseInt(process_code) == 10) 
		{
		 	parameters.put("office_id", Office_id); 
		 	parameters.put("office_name", Office_name);
			path = getServletContext().getRealPath("/WEB-INF/PDF/Ben_Sche.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/Ben_Sche.jasper"));
		} 
		else if (Integer.parseInt(process_code) == 14)
        {
            path = getServletContext().getRealPath("/WEB-INF/PDF/Exp_Mth_Div.jasper");
            reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/Exp_Mth_Div.jasper"));
        }
        else if (Integer.parseInt(process_code) == 11)
        {
            path = getServletContext().getRealPath("/WEB-INF/PDF/Exp_Mth_Sch.jasper");
            reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/Exp_Mth_Sch.jasper"));
        }
        else if (Integer.parseInt(process_code) == 12)
        {  
            path = getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth.jasper");
            reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth.jasper"));
        }
        else if (Integer.parseInt(process_code) == 13)
        {
            path = getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth_upto.jasper");
            reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth_upto.jasper"));
        } 
        else if (Integer.parseInt(process_code) == 15)
        {
            path = getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth_upto.jasper");
            reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth_upto_wss.jasper"));
        } 
        else if (Integer.parseInt(process_code) == 16)
        {
            path = getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth_upto.jasper");
            reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth_wss.jasper"));
        } 
        else if (Integer.parseInt(process_code) == 17)
        {
            path = getServletContext().getRealPath("/WEB-INF/PDF/Demand_mth_upto.jasper");
            reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/Exp_fund_receipt.jasper"));
        }

        else if (Integer.parseInt(process_code) == 18)
        {
        	String pmonth=obj.setValue("pmonth", request);
			String mvalue=obj.month_val(pmonth);
  			parameters.put("year", obj.setValue("pyear", request));
			parameters.put("month", pmonth);
			parameters.put("mvalue", mvalue);
            path = getServletContext().getRealPath("/WEB-INF/ReportSrc/pumping_scheme_wise.jasper");
            reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/pumping_scheme_wise.jasper"));
        }else if(Integer.parseInt(process_code) == 19) 
        { 
			path = getServletContext().getRealPath("/WEB-INF/PDF/twad_sch_master.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/PDF/twad_sch_master.jasper"));
		}else if(Integer.parseInt(process_code) == 20) 
        { 
			String ctxpath =request.getRequestURL().toString();			
			String reg_id=obj.setValue("reg_id", request);			 
			String pmonth=obj.setValue("pmonth", request);
			String fin_year_label="";
			int year2= (Integer.parseInt(obj.setValue("fin_year", request)));
			fin_year_label=(year2-1)+"-"+year2;
			String monthvalue=obj.month_val(pmonth);
			
		   if (Integer.parseInt(pmonth)>=3)  
		   {  
				// year2=year2;     
			   fin_year_label=(year2)+"-"+(year2+1);	 
		   }
		   else
		   {
				// year2=year2+1;
			   fin_year_label=(year2-1)+"-"+year2;
			}
			parameters.put("year",year2);  
			parameters.put("month", pmonth);  
			parameters.put("fin_year",fin_year_label );      
			parameters.put("monthvalue", monthvalue);
			parameters.put("ctxpath", ctxpath);
			parameters.put("reg_id", reg_id);     
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_Report_Fin_Year.jasper");  
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_Report_Fin_Year.jasper"));
        }else if(Integer.parseInt(process_code) == 201) 
        { 
        	String ctxpath =request.getRequestURL().toString();
			String pmonth=obj.setValue("pmonth", request);
			 int year2= (Integer.parseInt(obj.setValue("fin_year", request)));	   
			String monthvalue=obj.month_val(pmonth);
		   if (Integer.parseInt(pmonth)>3)  
				 year2=year2;
			 else
				 year2=year2+1;  
			parameters.put("year",year2);  
			parameters.put("month", pmonth);  
			parameters.put("fin_year", (year2)+"-"+(year2+1));    
			parameters.put("monthvalue", monthvalue);  
			parameters.put("ctxpath", ctxpath);  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_Report_Fin_Year_reg.jasper");  
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_Report_Fin_Year_reg.jasper"));
        }
		else if(Integer.parseInt(process_code) == 21) 
        { 			
		   String pmonth=obj.setValue("pmonth", request);
		   int year1= (Integer.parseInt( obj.setValue("fin_year", request)));	 
		   String monthvalue=obj.month_val(pmonth);
		   if (Integer.parseInt(pmonth)==4)
			   monthvalue=" AS ON " + monthvalue;
		   else
			   monthvalue=" UP TO " + monthvalue;		   
		   int year2=0;  
		   
		   if (Integer.parseInt(pmonth)>3)
				 year2=year1;
			 else
				 year2=year1+1;  
		   
			parameters.put("year",year2);  
			parameters.put("year1",year1);  
			parameters.put("year2",year2);
			parameters.put("month", pmonth);			    
			parameters.put("monthvalue", monthvalue);			 
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/MD_Review_DCB_New.jasper");  
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/MD_Review_DCB_New.jasper"));
        }else if(Integer.parseInt(process_code) == 22) 
        { 
			String ctxpath =request.getRequestURL().toString();			
			String year2=  obj.setValue("fin_year", request);  
			String off_id= obj.setValue("off_id", request);  
			parameters.put("fin_year", year2);     
			parameters.put("office_id", off_id);  
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_Report_Fin_Year_Sub1.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_Report_Fin_Year_Sub1.jasper"));
        }
        else if(Integer.parseInt(process_code) == 111)   
        { 
			String ctxpath =request.getRequestURL().toString();			
			String year2=  obj.setValue("fin_year", request);  
			String off_id= obj.setValue("off_id", request);			 
			parameters.put("fin_year", year2);     
			parameters.put("office_id", off_id);   
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Budget_Estimate_Entry_Report.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Budget_Estimate_Entry_Report.jasper"));
        } else if(Integer.parseInt(process_code) == 112)   
        { 
			String ctxpath =request.getRequestURL().toString();			
			String year2=  obj.setValue("fin_year", request);  
			String off_id= obj.setValue("off_id", request);			 
			parameters.put("fin_year", year2);     
			parameters.put("office_id", off_id);   
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/ExpenditureItem.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/ExpenditureItem.jasper"));
        }else if(Integer.parseInt(process_code) == 123) 
        { 
			String ctxpath =request.getRequestURL().toString();			
			int year1= (Integer.parseInt( obj.setValue("fin_year", request)));	 
			int year2=year1+1;  
			
			String sch_sno=obj.setValue("sch_sno", request);
			System.out.println("Schme_no"+sch_sno);
			parameters.put("office_id", Office_id);			
			parameters.put("sch_sno", Integer.parseInt(sch_sno));
			parameters.put("start_year",year1);  
			parameters.put("end_year",year2);
			System.out.println("start_year"+year1);
			System.out.println("end_year"+year2);
			parameters.put("head", (year1)+"-"+(year2));     
			String sname="";
			try
			{ 
			sname=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+ obj.setValue("sch_sno", request));
			}catch (Exception e) {}
			parameters.put("sch_name",sname);
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/itemwiseReport.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/itemwiseReport.jasper"));
        }else if(Integer.parseInt(process_code) == 124) 
        { 
			String ctxpath =request.getRequestURL().toString();	
			
			String fin_year=obj.setValue("fin_year", request);	 
			String sch_sno=obj.setValue("sch_sno", request);
			System.out.println("Schme_no"+sch_sno);
			System.out.println("fin_year"+fin_year);
			System.out.println("fin_year"+fin_year);
			parameters.put("fin_year", fin_year); 
			parameters.put("head", fin_year);
			parameters.put("office_id", Office_id);			
			parameters.put("sch_sno", Integer.parseInt(sch_sno));
			String sname="";
			try  
			{ 
			sname=obj.getValue("PMS_SCH_MASTER", "SCH_NAME","where SCH_SNO="+sch_sno);
			
			}catch (Exception e) {
				System.out.println(e);
			}
			System.out.println(sname);
			parameters.put("sch_name",sname);
			path = getServletContext().getRealPath("/WEB-INF/ReportSrc/itemwiseReport_fin.jasper");
			reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/itemwiseReport_fin.jasper"));
        }
		try
		{
			JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);			
			String option=obj.setValue("option",request);
			if (option.equalsIgnoreCase("2"))
			{
				try        
				 {
				   response.setContentType("application/vnd.ms-excel");
			       response.setHeader ("Content-Disposition", "attachment;filename=\"Report_AME.xls\"");
				   JRExporter exporter = new JRPdfExporter();
				   JRXlsExporter exporterXLS = new JRXlsExporter(); 
                   exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
                   ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                   exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
                   exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
                   exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
                   exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
                   exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
                   exporterXLS.exportReport(); 
                   byte []bytes;
                   bytes = xlsReport.toByteArray();
                   ServletOutputStream ouputStream = response.getOutputStream();
                   ouputStream.write(bytes, 0, bytes.length);
                   ouputStream.flush();
                   ouputStream.close();
			       } catch (JRException e)
			       {			   	
			   		e.printStackTrace();
			   	   } catch (IOException e) 
			   	   {
					e.printStackTrace();
				   }				
			}    
			else
			{			
				OutputStream outuputStream = response.getOutputStream();
				JRExporter exporter = null;
				response.setContentType("application/pdf");     
				response.setHeader("Content-Disposition","attachment; filename=\"REPORT.pdf\"");  
				exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter( JRExporterParameter.OUTPUT_STREAM,outuputStream);
				exporter.exportReport();
				outuputStream.close();
			}			
		}catch(Exception e)	
		{
			System.out.println(e   );  
		}		
	}	
}