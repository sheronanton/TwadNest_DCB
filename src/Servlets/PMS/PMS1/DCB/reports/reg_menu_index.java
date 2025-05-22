/* 
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 21/09/2011
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 
 *-----------------------------------------------------------------------------
 */
package Servlets.PMS.PMS1.DCB.reports;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
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
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class reg_menu_index extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	private static final String cr_label="Rs in Crores";  
	private static final String lk_label="Rs in Lakhs";
	private static final String ac_label="Rs";
	private static final int cr_fig=10000000;
	private static final int lk_fig=100000;
	private static final int ak_fig=1;
	public String option="";
    public reg_menu_index() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj = new Controller();
		Connection con = null;
		String process="";
		String command="";
		String ben_name="";
		try
		{
			command=obj.setValue("command", request);
			process=obj.setValue("ref_sno", request);
			ben_name=obj.setValue("ben_name", request);
			con = obj.con();
			obj.createStatement(con);
			HttpSession session = request.getSession(false);
			String userid = "0", Office_id = "0";
			try
			{
				userid = (String) session.getAttribute("UserId");
			} catch (Exception e) 
			{
				userid = "0";
			}
			if (userid == null) 
			{
				userid = "0";
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
		    OutputStream outuputStream = response.getOutputStream();
		
		    
	 	//  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
    
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		    
		//	Office_id = (obj.isNull(obj.getValue("HRM_EMP_CURRENT_POSTING",	"OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')"), 1));
			if (Office_id.equals("0"))	Office_id = "0";
			String oid=obj.setValue("oid", request);			  
			if (oid.equalsIgnoreCase("0")) Office_id=Office_id; else Office_id=oid;
			if (Office_id.equalsIgnoreCase("0")) Office_id="5000";    
			String office_ = obj.getValue("COM_MST_OFFICES","OFFICE_NAME"," where OFFICE_ID="+Office_id);	  
			String MONTH=obj.setValue("month", request);
			String Frm_month=obj.setValue("Frm_month", request);
			String YEAR=obj.setValue("year", request);
			
			String to_year=obj.setValue("to_year", request);
			
			option=obj.setValue("option", request); 
			if (Integer.parseInt(option)==0) option="1";
			Map parameters=new HashMap(); 
			parameters.put("office_id", Office_id );				   
			parameters.put("year",  YEAR );
			parameters.put("month",  MONTH );
		    parameters.put("monthval",obj.month_val(MONTH));
		    int reporttype=Integer.parseInt(obj.setValue("reporttype", request));
		    System.out.println("DCB->reg_menu_index->command->"+command+"->option->"+option+"-->process->" + process+" -->reporttype-->"+reporttype);  
		    JRExporter exporter = new JRPdfExporter();
			String path="";
			
			
			
			if (command.equalsIgnoreCase("defunt1"))
			{
				if (option.equalsIgnoreCase("1"))
			{     
				 response.setContentType("application/pdf"); 
				 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Defunt_report_Ben.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("to_year",to_year );
				 parameters1.put("office_id", Office_id );
				 parameters1.put("month",MONTH );
				 parameters1.put("Frm_month",Frm_month );
				 parameters1.put("ben_name",ben_name );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
				 response.setHeader("Content-Disposition","attachment; filename=\"Defunt_Report1.pdf\"");
				 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				 exporter.exportReport();
				 outuputStream.close();  
				 outuputStream.flush();
	    	}else
	    	{ 
	    		
	    		 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Defunt_report_Ben.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("to_year",to_year );
				 parameters1.put("office_id", Office_id );
				 parameters1.put("month",MONTH );
				 parameters1.put("Frm_month",Frm_month );
				 parameters1.put("ben_name",ben_name );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
			  	 response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"Report.xls\"");
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
   		
	    	}
			
			}
			
			if (command.equalsIgnoreCase("defunt_Total"))
			{
				if (option.equalsIgnoreCase("1"))
			{     
				 response.setContentType("application/pdf"); 
				 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Defunt_ben_Total.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("to_year",to_year );
				 parameters1.put("office_id", Office_id );
				 parameters1.put("month",MONTH );
				 parameters1.put("Frm_month",Frm_month );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
				 response.setHeader("Content-Disposition","attachment; filename=\"Defunt_ben_report.pdf\"");
				 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				 exporter.exportReport();
				 outuputStream.close();  
				 outuputStream.flush();
	    	}else
	    	{ 
	    		
	    		 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Defunt_ben_Total.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("to_year",to_year );
				 parameters1.put("office_id", Office_id );
				 parameters1.put("month",MONTH );
				 parameters1.put("Frm_month",Frm_month );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
			  	 response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"Report.xls\"");
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
   		
	    	}
			
			}
			
			if (command.equalsIgnoreCase("defunt_Benwise"))
			{
				if (option.equalsIgnoreCase("1"))
			{     
				 response.setContentType("application/pdf"); 
				 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Defunt_ben_report.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("to_year",to_year );
				 parameters1.put("office_id", Office_id );
				 parameters1.put("month",MONTH );
				 parameters1.put("Frm_month",Frm_month );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
				 response.setHeader("Content-Disposition","attachment; filename=\"Defunt_ben_report.pdf\"");
				 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				 exporter.exportReport();
				 outuputStream.close();  
				 outuputStream.flush();
	    	}else
	    	{ 
	    		
	    		 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Defunt_ben_report.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("to_year",to_year );
				 parameters1.put("office_id", Office_id );
				 parameters1.put("month",MONTH );
				 parameters1.put("Frm_month",Frm_month );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
			  	 response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"Report.xls\"");
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
   		
	    	}
			
			}
			
			
			if (command.equalsIgnoreCase("defunt"))
			{
				if (option.equalsIgnoreCase("1"))
			{     
				 response.setContentType("application/pdf"); 
				 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Defunt_report.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("to_year",to_year );
				 parameters1.put("office_id", Office_id );
				 parameters1.put("month",MONTH );
				 parameters1.put("Frm_month",Frm_month );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
				 response.setHeader("Content-Disposition","attachment; filename=\"Defunt_Report1.pdf\"");
				 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				 exporter.exportReport();
				 outuputStream.close();  
				 outuputStream.flush();
	    	}else
	    	{ 
	    		
	    		 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Defunt_report.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("to_year",to_year );
				 parameters1.put("office_id", Office_id );
				 parameters1.put("month",MONTH );
				 parameters1.put("Frm_month",Frm_month );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
			  	 response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"Report.xls\"");
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
   		
	    	}
			
			}
		      if (command.equalsIgnoreCase("Setting"))   
			  {
		    	PrintWriter pw = response.getWriter();  
				response.setHeader("Cache-Control", "no-cache");
				response.setContentType("text/xml");
				String month=request.getParameter("pmonth");
				String year=request.getParameter("pyear");
				session.setAttribute("dcbmonth", month);
				session.setAttribute("dcbyear", year);
				session.setAttribute("dcbflag", "1");
				String xml = "<response><command>Setting</command><msg>Month and Year Setting Done</msg></response>";
				pw.write(xml);
				pw.flush();
				pw.close();
			  }else
			  {  
				  			
		    	try {
						
						String	imagespath = getServletContext().getRealPath("/images/")+File.separator;
						parameters.put("imgpath", imagespath);
						// Summary of Ledger (All Division Data )
						if (Integer.parseInt(process)==140 )			
						{								   
							  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/new_dcb_ledger_abstract.jasper");   
							  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
							  pdfshow( response,  jasperPrint,  outuputStream);
						} 
						if (Integer.parseInt(process)==141 )			
						{								          
							  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/new_dcb_ledger.jasper");   
							  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
							  pdfshow( response,  jasperPrint,  outuputStream);
						}          
						 
			if (Integer.parseInt(process)==51 || Integer.parseInt(process)==61  || Integer.parseInt(process)==71 )			
			{
				  switch(Integer.parseInt(process))
				  {
				  case 61  :
					  	parameters.put("label", cr_label );	  
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 51:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 71:
					  	parameters.put("label", ac_label);	
					  	parameters.put("divby", ak_fig);
					  	break;  
				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Grouped_district__corp.jasper");
				  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				  pdfshow( response,  jasperPrint,  outuputStream);
			}	else if (Integer.parseInt(process)==52 || Integer.parseInt(process)==62  || Integer.parseInt(process)==72  )			
			{
				  switch(Integer.parseInt(process))
				  {
				  case 62  :
					  	parameters.put("label", cr_label );	
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 52:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 72:
					  	parameters.put("label", ac_label);	
					  	parameters.put("divby", ak_fig);
					  	break;
				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Grouped_district_mun.jasper");
				  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				  pdfshow( response,  jasperPrint,  outuputStream);
			}	else if (Integer.parseInt(process)==53 || Integer.parseInt(process)==63 || Integer.parseInt(process)==73)			
			{
				  switch(Integer.parseInt(process))
				  {
				  case 63  :
					  	parameters.put("label", cr_label );	
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 53:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 73:
					  	parameters.put("label", ac_label);	
					  	parameters.put("divby", ak_fig);
					  	break;
				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Grouped_district_rtp.jasper");
				  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				  pdfshow( response,  jasperPrint,  outuputStream);
			}	else if (Integer.parseInt(process)==54 || Integer.parseInt(process)==64 || Integer.parseInt(process)==74)			
			{
				  switch(Integer.parseInt(process))
				  {
				  case 64  :
					  	parameters.put("label", cr_label );	
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 54:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 74:
					  	parameters.put("label", ac_label);	  
					  	parameters.put("divby", ak_fig);
					  	break;
				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Grouped_district_utp.jasper");
				  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				  pdfshow( response,  jasperPrint,  outuputStream);
			}	else if (Integer.parseInt(process)==55 || Integer.parseInt(process)==65 || Integer.parseInt(process)==75)			
			{
				  switch(Integer.parseInt(process))
				  {
				  case 65  :
					  	parameters.put("label", cr_label );	
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 55:    
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 75:
					  	parameters.put("label", ac_label);	
					  	parameters.put("divby", ak_fig);
					  	break;
				  }
				  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Grouped_district_vp.jasper");
				  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				  pdfshow( response,  jasperPrint,  outuputStream);
			}		
			else if (Integer.parseInt(process)==135 || Integer.parseInt(process)==136 || Integer.parseInt(process)==137 ) 
		   {
			   switch(Integer.parseInt(process))
				  {
				  case 135  :
					  	parameters.put("label", cr_label );	
					  	parameters.put("divby", cr_fig);
					  	break;
				  case 136:
					  	parameters.put("label", lk_label );	
					  	parameters.put("divby", lk_fig);
					  	break;				  			
				  case 137:
					  	parameters.put("label", ac_label);	
					  	parameters.put("divby", ak_fig);
					  	break;
				  }
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F_Actual_HO_SPL.jasper");
			 	JasperPrint  jasperPrint1 = JasperFillManager.fillReport(path, parameters, con);
			 	//pdfshow( response,  jasperPrint,  outuputStream);
			 	 if (Integer.parseInt(option)==1)     
	              {  
	           	   	response.setContentType("application/pdf");
	           	   	response.setHeader("Content-Disposition", "attachment; filename=\"Report.pdf\"");
	           	   	exporter = new JRPdfExporter();
	           	   	exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint1);  
	           	   	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
	           	   	exporter.exportReport();
	           	   	outuputStream.close();
	              }
	              else  if (Integer.parseInt(option)==2)   
	              {  
	           	   	  response.setContentType("application/vnd.ms-excel");
	                  response.setHeader ("Content-Disposition", "attachment;filename=\"Report.xls\"");
	                  JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                  exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint1); 
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
	              }
		   }
			if (Integer.parseInt(process)==1 || Integer.parseInt(process)==7) 
			{
				 if (reporttype==1)
				 {
					 if (Integer.parseInt(process)==7)
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F_Actual.jasper");
					 else
					    path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F.jasper");
					 	JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
					 	pdfshow( response,  jasperPrint,  outuputStream);
				 }
				 else 
				 {
					 if (Integer.parseInt(process)==7)
							//path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F_Actual_xls.jasper");
						 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F_Actual.jasper");						 
					 else
					    //	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F_xls.jasper");
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_BTYP_F.jasper");  
					 	JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);		 	
					 	excelshow( response,  jasperPrint,  outuputStream);
				 }
		  }
		  else if (Integer.parseInt(process)==21 || Integer.parseInt(process)==31 || Integer.parseInt(process)==41 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 21  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 31:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 41:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Cor_F_Actual_HO_SPL.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }
		  else if (Integer.parseInt(process)==22  || Integer.parseInt(process)==32 || Integer.parseInt(process)==42)
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 22  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 32:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig); 
				  	break;				  			
			  case 42:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_F_Actual_HO_SPL.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }		  else if (Integer.parseInt(process)==23 || Integer.parseInt(process)==33 || Integer.parseInt(process)==43)
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 23  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 33:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 43:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_F_Actual_HO_SPL.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }
		  else if (Integer.parseInt(process)==24 || Integer.parseInt(process)==34 || Integer.parseInt(process)==44 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 24  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 34:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 44:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_F_Actual_HO_SPL.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  } else if ( Integer.parseInt(process)==160 )
		  {
			  parameters.put("label", lk_label );	
			  parameters.put("divby", lk_fig); 
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Private_Actual_HO_SPL.jasper"); 
			  JasperPrint  jasperPrint1 = JasperFillManager.fillReport(path, parameters, con);
			  //pdfshow( response,  jasperPrint,  outuputStream);
			  if (Integer.parseInt(option)==1)     
              {  
				  
				  
           	   	response.setContentType("application/pdf");
           	   	response.setHeader("Content-Disposition", "attachment; filename=\"Report.pdf\"");
           	   	exporter = new JRPdfExporter();
           	   	exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint1);  
           	   	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
           	   	exporter.exportReport();
           	   	outuputStream.close();
              }
              else  if (Integer.parseInt(option)==2)   
              {  
           	   	  response.setContentType("application/vnd.ms-excel");
                  response.setHeader ("Content-Disposition", "attachment;filename=\"Report.xls\"");
                  JRXlsExporter exporterXLS = new JRXlsExporter(); 
                  exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint1); 
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
              }
		  }
		  else if (Integer.parseInt(process)==25 || Integer.parseInt(process)==35 || Integer.parseInt(process)==45 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 25  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 35:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 45: 
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;    
			  }																		 
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_SPL_dist_VP_type.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }else if (Integer.parseInt(process)==221 || Integer.parseInt(process)==321 || Integer.parseInt(process)==421 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 221  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 321:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 421:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Cor_bal_due_details.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }	else if (Integer.parseInt(process)==222 || Integer.parseInt(process)==322 || Integer.parseInt(process)==422 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 222  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 322:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 422:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_bal_due_details.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }	else if (Integer.parseInt(process)==223 || Integer.parseInt(process)==323 || Integer.parseInt(process)==423 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 223  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 323:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 423:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_MunIII_bal_due_det.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }	else if (Integer.parseInt(process)==224 || Integer.parseInt(process)==324 || Integer.parseInt(process)==424 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 224  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 324:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 424:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_bal_due_details.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }	else if (Integer.parseInt(process)==225 || Integer.parseInt(process)==325 || Integer.parseInt(process)==425 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 225  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 325:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 425:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_bal_due_details.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }	
		  else if (Integer.parseInt(process)==226 || Integer.parseInt(process)==326 || Integer.parseInt(process)==426 )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 226  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 326:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 426:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_bal_due_det_blk.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }	else if (Integer.parseInt(process)==227 || Integer.parseInt(process)==327 || Integer.parseInt(process)==427 )
		  {
			  switch(Integer.parseInt(process))  
			  {
			  case 227  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 327:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 427:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;  
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_bal_due_details_vb.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }	
		  else if (Integer.parseInt(process)==206  || Integer.parseInt(process)==106  || Integer.parseInt(process)==306  )
		  {
			  switch(Integer.parseInt(process))
			  {
			  case 106  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 206:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig); 
				  	break;				  			  
			  case 306:
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;				  	
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_HO_SPL_dist_VP_type_B.jasper");
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }
		  else if (  Integer.parseInt(process)==46 || Integer.parseInt(process)==47 || Integer.parseInt(process)==48)
		  {  switch(Integer.parseInt(process))
			  {
			  case 46  :
				  	parameters.put("label", cr_label );	
				  	parameters.put("divby", cr_fig);
				  	break;
			  case 47:
				  	parameters.put("label", lk_label );	
				  	parameters.put("divby", lk_fig);
				  	break;				  			
			  case 48: 
				  	parameters.put("label", ac_label);	
				  	parameters.put("divby", ak_fig);
				  	break;  
			  }
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_HO_New_JAO.jasper");   
			  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			  pdfshow( response,  jasperPrint,  outuputStream);
		  }
		  else if (Integer.parseInt(process)==2 || Integer.parseInt(process)==8)					 
		  {
			  if (reporttype==1)
			  {
				 if (Integer.parseInt(process)==8)
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_F_Actual.jasper");
				 else
					 	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_F.jasper");					 
				JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				pdfshow( response,  jasperPrint,  outuputStream);
			  }
			  else
			  {
				  if (Integer.parseInt(process)==8)
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_F_Actual.jasper");
				 else
				    	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Mun_F.jasper");
				 	JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);		 	
				 	excelshow( response,  jasperPrint,  outuputStream);
			  }  
		 }else if (Integer.parseInt(process)==3 || Integer.parseInt(process)==9)
		 {
				  if (Integer.parseInt(process)==9)
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_F_Actual.jasper");
				  else	 
						path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_UTP_F.jasper");
				  JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				  pdfshow(response, jasperPrint, outuputStream);       
				  
		}else if (Integer.parseInt(process)==4 || Integer.parseInt(process)==10)
		{
				 if (Integer.parseInt(process)==10)
				 {
					 System.out.println("This path is selected 10");
					 
					   path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_F_Actual.jasper");
				 }
				 else
					   path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_F.jasper");
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
				 pdfshow(response, jasperPrint, outuputStream);       
		} else if (Integer.parseInt(process)==5 || Integer.parseInt(process)==11 || Integer.parseInt(process)==13 || Integer.parseInt(process)==14)
	    {
				if (Integer.parseInt(process)==11)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_F_Actual.jasper");
				else  if (Integer.parseInt(process)==13)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_3TWAD_Actual.jasper");
				else if (Integer.parseInt(process)==14)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_3TWAD_F.jasper");
				else
				     path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RTP_F.jasper");
				   imagespath = getServletContext().getRealPath("/images/")+File.separator;
			    	 parameters.put("imgpath", imagespath); 
			 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			
			    	pdfshow(response, jasperPrint, outuputStream);       
		}else if (Integer.parseInt(process)==6 || Integer.parseInt(process)==12)
		{ 
			  if (Integer.parseInt(process)==12)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Prv_F_Actual.jasper");
			 else	 
				 	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Prv_F.jasper");
			   		JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			   		pdfshow(response, jasperPrint, outuputStream);       
					option=Integer.toString(reporttype);
		}else if (Integer.parseInt(process)==15 || Integer.parseInt(process)==16)
		{ 
			  if (Integer.parseInt(process)==16)
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Cor_F_Actual.jasper");
			 else	 
				 	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_Cor_F.jasper");
			   		JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters, con);
			   		pdfshow(response, jasperPrint, outuputStream);       
					option=Integer.toString(reporttype);
		}else if (Integer.parseInt(process)==20)
		{
					 response.setContentType("application/pdf"); 
					 Map parameters1 = new HashMap();  
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Fin_Year_Report1.jasper");
					 parameters1.put("year",YEAR );
					 parameters1.put("year1", Integer.parseInt(YEAR)+1);  
					 parameters1.put("finyear",(Integer.parseInt(YEAR))+"-"+(Integer.parseInt(YEAR)+1) );
					 parameters1.put("imgpath", imagespath);  
					 System.out.println("parameters1"+parameters1);
					 if (Office_id.equalsIgnoreCase("5000")) 
							Office_id=obj.setValue("DV", request);
					 office_=obj.getValue("COM_MST_OFFICES","OFFICE_NAME"," where OFFICE_ID="+Office_id);	
					 parameters1.put("office_id", Office_id );
					 parameters1.put("off_name", office_ );
					 parameters1.put("month","3" );
					 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
					 response.setHeader("Content-Disposition","attachment; filename=\"Fin_Year_Report1.pdf\"");
					 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
					 exporter.exportReport();
					 outuputStream.close();  
					 outuputStream.flush();
			}else if (Integer.parseInt(process)==210)
			{     
				 response.setContentType("application/pdf"); 
				 Map parameters1 = new HashMap();  
				 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Fin_Year_Report1_New.jasper");
				 parameters1.put("year",YEAR );
				 parameters1.put("year1", Integer.parseInt(YEAR)+1);  
				 parameters1.put("finyear",(Integer.parseInt(YEAR))+"-"+(Integer.parseInt(YEAR)+1) );
				 parameters1.put("imgpath", imagespath);  
				 if (Office_id.equalsIgnoreCase("5000")) 
						Office_id=obj.setValue("DV", request);
				 office_=obj.getValue("COM_MST_OFFICES","OFFICE_NAME"," where OFFICE_ID="+Office_id);	
				 parameters1.put("office_id", Office_id );
				 parameters1.put("off_name", office_ );
				 parameters1.put("month","3" );
				 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
				 response.setHeader("Content-Disposition","attachment; filename=\"Fin_Year_Report1.pdf\"");
				 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				 exporter.exportReport();
				 outuputStream.close();  
				 outuputStream.flush();
		}
		else if (Integer.parseInt(process)==200)
		{ 
					 response.setContentType("application/pdf");   
					 Map parameters1 = new HashMap();
					 path="";  
					 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Wise_Fin_year_Report.jasper");
					 if (Office_id.equalsIgnoreCase("5000"))  
							Office_id=obj.setValue("DV", request);
					 int year2= (Integer.parseInt(YEAR)+1);
					 imagespath = getServletContext().getRealPath("/images/")+File.separator;  
						obj.createStatement(con);    
					 office_=obj.getValue("COM_MST_OFFICES","OFFICE_NAME"," where OFFICE_ID="+Office_id);
					 parameters1.put("office_name",office_);
					 parameters1.put("office_id",Office_id);
					 parameters1.put("year1",YEAR);
					 parameters1.put("finyear",(Integer.parseInt(YEAR))+"-"+( Integer.parseInt(YEAR)+1));
					 parameters1.put("imgpath",imagespath);				 	
					 parameters1.put("year2",Integer.toString(year2));    
					 JasperPrint  jasperPrint = JasperFillManager.fillReport(path, parameters1, con);
					 response.setHeader("Content-Disposition","attachment; filename=\"Fin_Year_Report1.pdf\"");
					 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
					 exporter.exportReport();
					 outuputStream.close();
					 outuputStream.flush();  
			}
			
			con.close();
			con=null;	
			  
	} catch (JRException e) 
	{
				System.out.println(e);
	}
		}
	}catch (Exception e) 
	{
			System.out.println(e);
	}
	
}
	public void pdfshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		System.out.println(option);
		if (option.equalsIgnoreCase("2"))  
		{
			excelshow( response, jasperPrint, outuputStream);
		}else   if (option.equalsIgnoreCase("1"))		 
		{
			try {
			  JRExporter exporter = new JRPdfExporter();
			  response.setContentType("application/pdf");
			  response.setHeader("Content-Disposition","attachment; filename=\"Report.pdf\"");
			  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
			  exporter.exportReport();
			  outuputStream.close();  
			  outuputStream.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else 
		{
			htmlshow( response, jasperPrint, outuputStream); 
		}
	}
	public void excelshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		if (option.equalsIgnoreCase("1"))
		{
			pdfshow( response, jasperPrint, outuputStream);
		}else if (option.equalsIgnoreCase("2"))
		{
			
			try {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition","attachment; filename=\"Report.xls\"");
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS,Boolean.FALSE);
			    exporterXLS.exportReport();
				byte[] bytes;
				bytes = xlsReport.toByteArray(); 
				outuputStream.write(bytes, 0, bytes.length);
				outuputStream.flush();
				outuputStream.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
//			try {
//			response.setContentType("application/vnd.ms-excel");
//			response.setHeader("Content-Disposition","inline; filename=\"Report.xls\"");
//			response.addHeader("Content-Disposition", "attachment");
//			//response.setHeader("Content-Disposition","attachment;filename=\"Report.xls\"");
//			JRXlsExporter exporterXLS = new JRXlsExporter();
//			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//			ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,Boolean.TRUE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
//			exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS,Boolean.FALSE);
//				exporterXLS.exportReport();
//			byte[] bytes;
//			bytes = xlsReport.toByteArray(); 
//			outuputStream.write(bytes, 0, bytes.length);
//			outuputStream.flush();
//			outuputStream.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();  
//			}    
		} else  
		{
			htmlshow( response, jasperPrint, outuputStream);  
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}
	public void htmlshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
		try {    
			  JRExporter exporter = new JRHtmlExporter();  
			  response.setContentType("application/pdf");      
			  response.setHeader("Content-Disposition","attachment; filename=\"Report.html\"");
			  exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			  exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, "build/reports/BatchExportReport.html");
			  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outuputStream);
 			  exporter.exportReport();
			  outuputStream.close();  
			  outuputStream.flush();  
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
