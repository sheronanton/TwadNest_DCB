package Servlets.PMS.PMS1.DCB.reports;
/* 
 * Created on : dd-mm-yy 
 * Author     : Panneer Selvam.K
 * Last Date  : 28/01/2013
 *----------------------------------------------------------------------------- 
 * Revision History (Release 1.0.0.0) 
 *-----------------------------------------------------------------------------
 * Date			Description								Done By 		  Type
 *-----------------------------------------------------------------------------
 * 28/01/2013	District wise Report in HO				Panneer Selvam.k	N
 *-----------------------------------------------------------------------------
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import Servlets.AME.AME1.AMEM.servlets.DataUploadAmeCollection;
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
public class PMS_DCB_HO_DIST_WISE extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	private static final String cr_label="Rs in Crores";  
	private static final String lk_label="Rs in Lakhs";
	private static final String ac_label="Rs";
	private static final int cr_fig=10000000;
	private static final int lk_fig=100000;
	private static final int ak_fig=1;
	public PMS_DCB_HO_DIST_WISE() {
		super();
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
         Connection connection = null;
         Controller obj=new Controller();
         String imagespath="";
 		 try 
 		  {
              connection =obj.con();
          } catch (Exception ex)
          {
              String connectMsg ="Could not create the connection" + ex.getMessage() + " " +ex.getLocalizedMessage();
          }
         try 
         {
          Map parameters = new HashMap();
          String path="";
          String   command=obj.setValue("command", request);
          String process=obj.setValue("ref_sno", request);
          HttpSession session = request.getSession(false);
          String userid = (String) session.getAttribute("UserId");
			if (userid == null){
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			 String office_=obj.getValue("COM_MST_OFFICES","OFFICE_NAME"," where OFFICE_ID="+Office_id);	  
 	       System.out.println("DCB->PMS_DCB_HO_DIST_WISE ->process_code->"+process+"->command->" +command);
		   if (Integer.parseInt(process)==135 || Integer.parseInt(process)==136 || Integer.parseInt(process)==137 ) 
		  {	
			 path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_F_Actual_HO_SPL_dist_type.jasper");
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
		  }else if (Integer.parseInt(process)==21 || Integer.parseInt(process)==31 || Integer.parseInt(process)==41 )
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
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_F_Actual_HO_SPL_dist_corp_type.jasper");
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
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_F_Actual_HO_SPL_dist_MUN_type.jasper");
		  } else if (Integer.parseInt(process)==23 || Integer.parseInt(process)==33 || Integer.parseInt(process)==43)
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
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_F_Actual_HO_SPL_dist_RTP_type.jasper");
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
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_F_Actual_HO_SPL_dist_UTP_type.jasper");
		  }	  else if (Integer.parseInt(process)==25 || Integer.parseInt(process)==35 || Integer.parseInt(process)==45 )
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
			  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_VP_Type_F_Actual_HO_SPL.jasper");
		  } else if (Integer.parseInt(process)==101  )
		  {
			  	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/ben_type_district_wise.jasper");				
				parameters.put("office_id", Office_id);   
		  }else if (Integer.parseInt(process)==102  )  
		  {     
			  	int set_year1=0,set_year2=0;
			    int month_new=Integer.parseInt(obj.setValue("month", request));
			    if (month_new >=3)
			    	{ 
			    	set_year1=Integer.parseInt(obj.setValue("year", request))-1;
			    	set_year2=Integer.parseInt(obj.setValue("year", request));
			    	}
			    else
			    	{
			    	set_year1=Integer.parseInt(obj.setValue("year", request));
			    	set_year2=Integer.parseInt(obj.setValue("year", request))+1;
			    	
			    	}
			  	path = getServletContext().getRealPath("/WEB-INF/ReportSrc/QtySuppliedReport.jasper");
			  	System.out.println(Office_id);
			  	System.out.println(set_year1);
			  	System.out.println(set_year2);  
				parameters.put("office_id", Office_id);  
				parameters.put("year1",set_year1 );  
				parameters.put("year2", set_year2);  
				parameters.put("fin_year",(Integer.parseInt(obj.setValue("year", request)))+"-"+( Integer.parseInt(obj.setValue("year", request))+1));
		  }
		  else if (Integer.parseInt(process)==70 )  
		  {
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_ACTUAL_EXP.jasper");				
				parameters.put("office_id", Office_id);
		  }
		  else if (Integer.parseInt(process)==80 )
		  {
				path = getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_SCHEME_FIN_YEAR.jasper");
				System.out.println(obj.setValue("fin_year", request));
				parameters.put("fin_year",obj.setValue("fin_year", request));
				parameters.put("office_id",Office_id);	
		  }  
		  imagespath = getServletContext().getRealPath("/images/")+File.separator;
		  parameters.put("imgpath", imagespath);
 	      parameters.put("year",obj.setValue("year", request));    
 	      parameters.put("month",obj.setValue("month", request));
		  parameters.put("monthval", obj.month_val(obj.setValue("month", request)));   
		  parameters.put("office_", office_);
	      OutputStream outuputStream = response.getOutputStream();
		  JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
		  String splflag="0";
		  splflag=obj.isNull(obj.setValue("splflag", request),1);
		  System.out.println(splflag);
		  try
		  {
			int r=Integer.parseInt(splflag);  
		  }catch(Exception e)
		  {
			  splflag="0";
		  }  
		  if (splflag.equalsIgnoreCase(null)) splflag="0";
		  System.out.println("splflag" +splflag);    
		  if ( splflag.equalsIgnoreCase("2"))
		  {
			  try    
				 {
				 //  path = getServletContext().getRealPath("/WEB-INF/ReportSrc/AME_ACTUAL_EXPxls.jasper");     
				   jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
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
			       } catch (JRException e) {   
			   		// TODO Auto-generated catch block
			   		e.printStackTrace();
			   	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		  }else if ( splflag.equalsIgnoreCase("1"))
		  {
				 response.setContentType(CONTENT_TYPE);
			//  pdfshow( response,  jasperPrint,  outuputStream); 
				 JRExporter exporter = new JRPdfExporter();
				      jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
				   response.setContentType("application/pdf");
				   response.setHeader("Content-Disposition","attachment; filename=\"Report1.pdf\"");
				   exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				   exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream);
				   exporter.exportReport();
				   outuputStream.close();
				   outuputStream.flush();
		  }  else
		  {
			  htmlshow(  response,  jasperPrint,  outuputStream);
		  }
 	      } catch (Exception e) 
 	      {
			e.printStackTrace();
 	      } 
	}
	public void pdfshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
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
	public void excelshow(HttpServletResponse response,JasperPrint jasperPrint,OutputStream outuputStream)
	{
	 try 
	 {
       response.setHeader ("Content-Disposition", "attachment;filename=\"Report_AME.xls\"");
	   JRExporter exporter = new JRPdfExporter();
	   exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
       ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
       exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport);
       System.out.println(xlsReport); 
       exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
       exporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
       exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
       exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
		exporter.exportReport();
       byte []bytes;
       bytes = xlsReport.toByteArray();
       ServletOutputStream ouputStream = response.getOutputStream();
       ouputStream.write(bytes, 0, bytes.length);
       ouputStream.flush();
       ouputStream.close();
       } catch (JRException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
	}
}
