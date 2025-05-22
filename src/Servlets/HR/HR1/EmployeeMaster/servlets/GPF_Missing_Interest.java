package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

 

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

//import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * Servlet implementation class ViewBalanceReport
 */
public class GPF_Missing_Interest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Missing_Interest() {
        super();
      
    }
private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	Connection con2=null;
    Connection con=null;
    Connection con1=null;
    ResultSet rs=null;
    ResultSet rs3=null;
    ResultSet rs2=null;
    ResultSet rs4=null;
    ResultSet rs5=null;
    ResultSet rs6=null;
    PreparedStatement ps1=null;
    PreparedStatement ps=null;
    PreparedStatement ps2=null;
	   int empid=0;
	   long l=System.currentTimeMillis();
       java.sql.Timestamp ts=new java.sql.Timestamp(l);
       String ConnectionString="";
       String strDriver="",strdsn="",strhostname="",strportno="",strsid="",strdbusername="",strdbpassword="";
	   String updatedby="";
       try {
                   
    	   LoadDriver driver=new LoadDriver();
           con=driver.getConnection();

                               // con1=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                               // con2=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());    
               }
               catch(Exception e)
                   {
                      System.out.println("Exception in openeing connection :"+e); 
                      //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");   
                      
                   }
                   
                       
                       
                       try
                               {
                                   HttpSession session=request.getSession(false);
                                   UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                           	      
                             	    System.out.println("user id::"+empProfile.getEmployeeId());
                             	    empid=empProfile.getEmployeeId(); 
                             	    updatedby=(String)session.getAttribute("UserId");
                                   System.out.println("user id..."+updatedby);
                                  
                                   
                             	    if(session==null)
                                   {
                                       System.out.println(request.getContextPath()+"/index.jsp");
                                       response.sendRedirect(request.getContextPath()+"/index.jsp");

                                   }
                                   System.out.println(session);

                               }catch(Exception e)
                               {
                               System.out.println("Redirect Error :"+e);
                               }
  
  

	//gpf_no=4081;
	try{
		
        String s1=request.getParameter("command");
        
        if(s1.equals("report1"))
        {
        	try{
        		
        
        	Map parameters = new HashMap();
        	
        	//InputStream reportStream = this.class.getResourceAsStream("/the-cat-report.xml");
        	String s=	request.getRealPath("/Reports/GPF_Missing_CR_DB_Interest.jrxml");
        	System.out.println("The Servlet Path---> "+s);
        	System.out.println("Before");
        	JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
        	System.out.println("After");

        	
        	JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
        	
        	//
        	JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, null, con);
        	OutputStream os=response.getOutputStream();
                ByteArrayOutputStream bout=new ByteArrayOutputStream();
        	//ByteOutputStream bout=new ByteOutputStream();
        	//response.setContentType("application/vnd.ms-excel");
        	        	response.setContentType("application/pdf");
        	response.setHeader ("Content-Disposition", "attachment;filename=\"Missing Interest.pdf\"");
        	os.write(JasperManager.printReportToPdf(jasperPrint));
        	
        	os.close();
        	
        	System.out.println("Written print");
        	} 
        	catch (Exception e) {
        		e.printStackTrace();
			}
        	
        	
        	
        }
        else if(s1.equals("report2"))
        {
        	try{
        		String jrnl=request.getParameter("serial_no");
        		System.out.println("jrnl="+jrnl);
        		String serial="";
        		int count=0;
        		
        	Map parameters = new HashMap();
        	parameters.put("serial", jrnl);
        	//InputStream reportStream = this.class.getResourceAsStream("/the-cat-report.xml");
        	String s=	request.getRealPath("/Reports/GPF_Missing_CR_DB_Interest2.jrxml");
        	System.out.println("The Servlet Path---> "+s);
        	System.out.println("Before");
        	JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
        	System.out.println("After");

        	
        	JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
        	
        	//
        	JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, parameters, con);
        	OutputStream os=response.getOutputStream();
                ByteArrayOutputStream bout=new ByteArrayOutputStream();
        	//ByteOutputStream bout=new ByteOutputStream();
        	//response.setContentType("application/vnd.ms-excel");
        	        	response.setContentType("application/pdf");
        	response.setHeader ("Content-Disposition", "attachment;filename=\"Missing Interest.pdf\"");
        	os.write(JasperManager.printReportToPdf(jasperPrint));
        	
        	os.close();
        	
        	System.out.println("Written print");
        	} 
        	catch (Exception e) {
        		e.printStackTrace();
			}
        	
        	
        	
        }
        
        else if(s1.equals("calculate"))
        {
        	response.setContentType("text/xml");
    		PrintWriter pw = response.getWriter();
            String xml="success";
		   System.out.println("calucated---->");
		   try
		   {
        
          System.out.println("calucated---->1");
      	ps=con.prepareStatement("select SERIAL_NO from HRM_GPF_missing_CR_DB_CON where INTEREST_CALCULATED_STATUS='N'");
 	    rs=ps.executeQuery();
              while(rs.next()) 
              {
          CallableStatement call1=con.prepareCall("{call GPF_CALC_MISS_INT_JRNLNO(?,?)}");
       
          call1.setInt(1,rs.getInt("SERIAL_NO"));
          call1.setString(2,updatedby);
          call1.execute();
              }
		   }
		   catch(Exception e)
		   {
		   System.out.println("Exception e"+ e);
		   }
      	System.out.println("calucated----> executed 2");
      	pw.write(xml);
		pw.flush();
		pw.close();
        }
        else if(s1.equals("recalculate"))
        {
        	
        	
        	int jrnl=Integer.parseInt(request.getParameter("serial_no"));
        	int count=0;
    		System.out.println("recalculate part----jrnl="+jrnl);    
    		ps=con.prepareStatement("select SERIAL_NO from HRM_GPF_MISSING_CR_DB_CON where HO_JRNL_MST_ID="+jrnl);
     	    rs=ps.executeQuery();
                  while(rs.next()) 
                  {
                	  ps=con.prepareStatement("delete from HRM_GPF_MISSING_INTEREST where SERIAL_NO="+rs.getInt(1));
                	  rs3=ps.executeQuery();
                	  
                	  ps=con.prepareStatement("delete from HRM_GPF_MISSING_INT_MNTH_WISE where SERIAL_NO="+rs.getInt(1));
                	  rs2=ps.executeQuery();
                  }
                  
                  
          CallableStatement call=con.prepareCall("{call GPF_CALC_MISS_INT_JRNLNO(?,?)}");
          call.setInt(1,jrnl);
          call.setString(2,updatedby);
          call.execute();
        /*  response.setContentType("text/xml");
  		PrintWriter pw = response.getWriter();
          String xml="success";
      	pw.write(xml);
		pw.flush();
		pw.close();*/
        }
        con.close();
      
 
		
		
		
	}
	catch(SQLException e)
	{
		 System.out.println("----------------Error in gpf_no");
		e.printStackTrace();
	}
	
    
}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
			}

}
