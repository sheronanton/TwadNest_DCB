package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import java.io.*;

import java.sql.*;
import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import oracle.jdbc.driver.*;
import oracle.sql.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import Servlets.Security.classes.UserProfile;

public class View_Balance_Report extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
    }
	 public void doPost(HttpServletRequest request, 
             HttpServletResponse response) throws ServletException, IOException {
System.out.println("hello");
Connection connection=null;

try {
	 LoadDriver driver=new LoadDriver();
 	connection=driver.getConnection();
}
catch(Exception e) {
   System.out.println("Exception in connection..."+e);
}   
        try
       {
           HttpSession session=request.getSession(false);
           if(session==null)
           {
            //   System.out.println(request.getContextPath()+"/index.jsp");
               response.sendRedirect(request.getContextPath()+"/index.jsp");
           }
          // System.out.println(session);
       }catch(Exception e)
       {
       System.out.println("Redirect Error :"+e);
       }    
       int w_amt=0;
       int su_amt=0;
       int Rf_amt=0;
       String fin_year="";
       String fin_year_fut="";
       String gpf_no="",SLIP_ISSUED_FIN_YEAR="",CLOSING_BALANCE_REGULAR="",IMPREG_OB="",IMP2003_OB="",
       Regular_Rs="",Impound_Rs="",Impound_2003_Rs="",state="",reg_Par="",imp_Par="";                
       try {
    	   gpf_no=request.getParameter("gpf_no");
    	   SLIP_ISSUED_FIN_YEAR=request.getParameter("SLIP_ISSUED_FIN_YEAR");
    	   CLOSING_BALANCE_REGULAR=request.getParameter("CLOSING_BALANCE_REGULAR");
    	   IMPREG_OB=request.getParameter("IMPREG_OB");
    	   IMP2003_OB=request.getParameter("IMP2003_OB");
    	   Regular_Rs=request.getParameter("Regular_Rs");
    	   Impound_Rs=request.getParameter("Impound_Rs");
    	   Impound_2003_Rs=request.getParameter("Impound_2003_Rs");
    	   state=request.getParameter("state");
    	   w_amt=Integer.parseInt(request.getParameter("w_amt"));
    	   su_amt=Integer.parseInt(request.getParameter("su_amt"));
    	   Rf_amt=Integer.parseInt(request.getParameter("Rf_amt"));
    	   System.out.println("w_amt..."+w_amt+"Rf_amt....."+Rf_amt+"su_amt...."+su_amt);
    	   
    	   int beagnYear = 0;
           int endYear = 0;
           
           int beagnYear1 = 0;
           int endYear1 = 0;
           
           int beagnYear_fut = 0;
           int endYear_fut = 0;
           String[] splitYear = SLIP_ISSUED_FIN_YEAR.split("-");
           beagnYear=Integer.parseInt(splitYear[0]);
           beagnYear1=beagnYear+1;
          
           endYear=Integer.parseInt(splitYear[1]);
           endYear1=endYear+1;
           
           
           beagnYear_fut=beagnYear1+1;
           endYear_fut=endYear1+1;
 		  System.out.println("beagnYear1------------"+beagnYear1+" endYear1 "+endYear1 +"beagnYear_fut..."+beagnYear_fut+"endYear_fut..."+endYear_fut);
 		
 		  
 		   fin_year = beagnYear1+"-"+endYear1;
 		  fin_year_fut=beagnYear_fut+"-"+endYear_fut;
       }                
       catch(Exception e) {
           System.out.println("Exception in assigning..."+e);
       }
       HttpSession session=request.getSession(false);
       UserProfile up=null;
       up=(UserProfile)session.getAttribute("UserProfile");  
       int empId=up.getEmployeeId();
       String UserName=up.getEmpInitial()+"."+up.getEmployeeName();
       String UsOff=up.getDesignation(),Fin="";


     File reportFile=null;
    String optbase=request.getParameter("cmbReportType");
//     System.out.println("Option Selected:"+optbase);
     try {
     //    System.out.println("calling servlet");
    	 Map map=new HashMap();
    	 JasperReport jasperReport=null;
    	 if(state.equalsIgnoreCase("working")){
         reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/View_Balance.jasper"));
    //     System.out.println(reportFile.getAbsolutePath());
         String path = getServletContext().getRealPath("/WEB-INF/ReportSrc/")+File.separator;
//    System.out.println("path="+path);
      if (!reportFile.exists())
     throw new JRRuntimeException("File J not found. The report design must be compiled first.");      
     jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
     
    map.put("gpf_no",gpf_no);
    map.put("SLIP_ISSUED_FIN_YEAR",SLIP_ISSUED_FIN_YEAR);
    map.put("CLOSING_BALANCE_REGULAR",CLOSING_BALANCE_REGULAR);
    map.put("IMPREG_OB",IMPREG_OB);
    map.put("IMP2003_OB",IMP2003_OB);
    map.put("Regular_Rs",Regular_Rs);
    map.put("Impound_Rs",Impound_Rs);
    map.put("Impound_2003_Rs",Impound_2003_Rs);
    map.put("SUBREPORT_DIR",path);
    map.put("w_amt",w_amt);
    map.put("Rf_amt",Rf_amt);
    map.put("su_amt",su_amt);
    map.put("fin_year", fin_year);
    map.put("fin_year_fut", fin_year_fut);
    	 }
    	 else{
    		 reg_Par=request.getParameter("reg_Par");
    		 imp_Par=request.getParameter("imp_Par");
    		 
    		 reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/View_Balance_Retired.jasper"));
    		    //     System.out.println(reportFile.getAbsolutePath());
    		         String path = getServletContext().getRealPath("/WEB-INF/ReportSrc/")+File.separator;
//    		    System.out.println("path="+path);
    		      if (!reportFile.exists())
    		     throw new JRRuntimeException("File J not found. The report design must be compiled first.");      
    		     jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
    		     
    		    map.put("gpf_no",gpf_no);
    		    map.put("SLIP_ISSUED_FIN_YEAR",SLIP_ISSUED_FIN_YEAR);
    		    map.put("CLOSING_BALANCE_REGULAR",CLOSING_BALANCE_REGULAR);
    		    map.put("IMPREG_OB",IMPREG_OB);
    		    map.put("IMP2003_OB",IMP2003_OB);
    		    map.put("Regular_Rs",Regular_Rs);
    		    map.put("Impound_Rs",Impound_Rs);
    		    map.put("reg_Par",reg_Par);
    		    map.put("imp_Par",imp_Par);
    		    map.put("Impound_2003_Rs",Impound_2003_Rs);
    		    map.put("SUBREPORT_DIR",path);
    		    map.put("w_amt",w_amt);
    		    map.put("Rf_amt",Rf_amt);
    		    map.put("su_amt",su_amt);
    		       
    		    map.put("fin_year", fin_year);
    		    map.put("fin_year_fut", fin_year_fut);
    	 }
    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
     
     String rtype= request.getParameter("cmbReportType");
     if (rtype.equalsIgnoreCase("HTML"))   
     {
                 response.setContentType("text/html");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"View_Current_Balance.html\"");
                 PrintWriter out = response.getWriter();
                 JRHtmlExporter exporter = new JRHtmlExporter();
                 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                 exporter.exportReport();
                  out.flush();
                 out.close();
     }
     else if (rtype.equalsIgnoreCase("PDF"))   
     {
                 byte buf[] = 
                   JasperExportManager.exportReportToPdf(jasperPrint);
                 response.setContentType("application/pdf");
                 response.setContentLength(buf.length);                     
                 response.setHeader ("Content-Disposition", "attachment;filename=\"View_Current_Balance.pdf\"");
                 OutputStream out = response.getOutputStream();
                 out.write(buf, 0, buf.length);
                 out.close();
     }
     else if (rtype.equalsIgnoreCase("EXCEL"))   
     {
     
             response.setContentType("application/vnd.ms-excel");
              response.setHeader ("Content-Disposition", "attachment;filename=\"View_Current_Balance.xls\"");
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
     else if (rtype.equalsIgnoreCase("TXT"))   
     {
     
             response.setContentType("text/plain");
              response.setHeader ("Content-Disposition", "attachment;filename=\"View_Current_Balance.txt\"");
              
         JRTextExporter exporter = new JRTextExporter();
         exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
         ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
         exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
         exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
         exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
         exporter.exportReport(); 
         
              byte []bytes;
             bytes = txtReport.toByteArray();
             ServletOutputStream ouputStream = response.getOutputStream();
             ouputStream.write(bytes, 0, bytes.length);
             ouputStream.flush();
             ouputStream.close();

     }
                   
     } catch (Exception ex) {
     String connectMsg = 
         "Could not create the report " + ex.getMessage() + " " + 
         ex.getLocalizedMessage();
     System.out.println(connectMsg);
     }
       
}
}
