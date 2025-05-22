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
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class GPF_Final_Settlement_master extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        GpfAuthMstModel.openConnection();
	 }
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	GpfAuthMstModel.closeConnection();
		super.destroy();		
    }
    
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {    	 	
	
    	//GpfAuthMstModel.closeConnection();
    	//GpfAuthMstModel.openConnection();
    	
    	Connection con = null,con1=null;
		try 
		{

			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
		//			+ strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
			con1 = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
		}
 
	 String xml="";
	 response.setContentType(CONTENT_TYPE);
	 PrintWriter out = response.getWriter();
	 response.setHeader("Cache-Control","no-cache");  
	 HttpSession session=request.getSession(false);
	 ResultSet rs = null, rss = null,rs1=null,rs2=null,rss2=null,rss3=null,rs11=null,rrs=null,rrss=null;
		PreparedStatement ps = null, pss = null,ps1=null,ps2=null,pss2=null,pss3=null,ps11=null,pps=null,ppss=null;
		
		CallableStatement callableStatement = null;		
		//ResultSet rs=null;
	//	PreparedStatement ps=null,ps1=null;
	    Statement st=null;
	    Connection connection=null;
	    ResultSet results=null;
	   // ResultSet rs1=null;
	    ResultSet results2=null;
		
		
		
	 try
	 {
	         if(session==null)
	         {
	                 xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
	                 out.println(xml);
	                 System.out.println(xml);
	                 out.close();
	                 return;
	          }
	 }catch(Exception e)
	 {
	        e.printStackTrace();
	 }
	 
	 String command="";
	 try {
	 	command = request.getParameter("command");
	 	System.out.println("COMMAND======="+command);
	} catch (Exception e) {
			// TODO: handle exception
	}
	 boolean gpfIntegerCheck=false;
	 boolean gpfValidity=false;
	 
	 session =request.getSession(false);
	 String updatedby=(String)session.getAttribute("UserId");
	 
	 long l=System.currentTimeMillis();
	 java.sql.Timestamp ts=new java.sql.Timestamp(l);
	 
	 
	
	 
	 
	 if(command.equalsIgnoreCase("Get"))
	 {
		 System.out.println("In Get function");
		 
		 String sql="SELECT EmpId, " +
		 "  Employee_Name, " +
		 "  Designation, " +
		 "  DA_AMOUNT,PROCESS_FLOW_ID " +
		 "FROM " +
		 "  (SELECT Employee_Id AS EmpId, " +
		 "    Office_Id, " +
		 "    DA_AMOUNT,PROCESS_FLOW_ID " +
		 "  FROM Hrm_Employee_Da_Details " +
		 "  ) a " +
		 "LEFT OUTER JOIN " +
		 "  (SELECT Employee_Id, " +
		 "    Designation_Id, " +
		 "    EMPLOYEE_STATUS_ID " +
		 "  FROM Hrm_Emp_Current_Posting " +
		 "  )B " +
		 "ON a.EmpId=b.Employee_Id " +
		 "LEFT OUTER JOIN " +
		 "  (SELECT employee_id,EMPLOYEE_NAME ||'.' ||EMPLOYEE_INITIAL AS Employee_Name " +
		
		 "  FROM hrm_mst_employees " +
		 "  )c " +
		 "ON a.EmpId=c.employee_id " +
		 "LEFT OUTER JOIN " +
		 "  (SELECT Designation_Id,Designation FROM Hrm_Mst_Designations " +
		 "  )D " +
		 "ON b.Designation_Id=d.Designation_Id " +
		 "ORDER BY Empid";
		 
		 xml="<response><command>Get</command>";
		 xml+="<status>load</status>";
		 
		 try {
			 int count=0;
			ps=con.prepareStatement(sql);
			 
			 rs=ps.executeQuery();
			 while(rs.next())
			 {
				 count++;
				 
				 xml+="<EmpId>"+rs.getInt("EmpId")+"</EmpId>";	 				 			 
				 xml+="<Employee_Name>"+rs.getString("Employee_Name")+"</Employee_Name>";
				 xml+="<Designation>"+rs.getString("Designation")+"</Designation>";
				 xml+="<DA_AMOUNT>"+rs.getInt("DA_AMOUNT")+"</DA_AMOUNT>";
				 xml+="<process_flow_id>"+rs.getString("PROCESS_FLOW_ID")+"</process_flow_id>";
			 }
			
			 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<status>failure</status>";
		}
		 xml+="</response>";
		 System.out.println("XML IS====="+xml);

	 }
	 
	 if(command.equalsIgnoreCase("Check"))
	  {
	 

	  int txtEmployeeid = Integer.parseInt(request.getParameter("txtEmployeeid").trim());
	     String sqls="select employee_id from  HRM_EMPLOYEE_DA_DETAILS  where employee_id=? order by employee_id";
	     int count=0;
	     xml="<response><command>Check</command>";
			
	     try {
			pss=con.prepareStatement(sqls);
			pss.setInt(1,txtEmployeeid);
			rss=pss.executeQuery();
			while(rss.next())
			{
				count++;
				xml+="<count>"+count+"</count>";
				if(txtEmployeeid==rss.getInt("employee_id"))
				{
					xml+="<status>success</status>";
				}
			}
			if(count==0)
			{
			xml+="<count>"+count+"</count>";
			xml+="<status>failure</status>";
			
			
			}
	     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception..."+e);
			
		}
	     xml=xml+"</response>";
	     System.out.print("XML CHECKKKKING ==== "+xml);
	     
 }
	 

	  
 if(command.equalsIgnoreCase("Add")) {
	 
	 System.out.println("In add function");	
	 
	 int txtEmployee_id=0;
	 int off_id=0;
	 int daAmount=0;
	 txtEmployee_id=Integer.parseInt(request.getParameter("txtEmployeeid").trim());	 
	 off_id=Integer.parseInt(request.getParameter("txtOffId").trim());	 
	 daAmount=Integer.parseInt(request.getParameter("da_Amount").trim());	 
	 
	 try
	 {
		 xml="<response><command>added</command>";
		 
		 
		 
		 String ss2="insert into HRM_EMPLOYEE_DA_DETAILS (EMPLOYEE_ID,OFFICE_ID,DA_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_ID) values (?,?,?,?,clock_timestamp(),?)";
		 
		 pss2=con.prepareStatement(ss2);
		 pss2.setInt(1,txtEmployee_id);
		 pss2.setInt(2,off_id);
		 pss2.setInt(3,daAmount);
		 pss2.setString(4,updatedby);
		 pss2.setString(5,"CR");
		 
		 pss2.executeUpdate();
		 
		 
		 xml+="<status>success</status>";
		 
	 }
	 catch(Exception e)
	 {
		 System.out.println("Exception..."+e);
		 xml+="<status>failure</status>";
	 }
	 xml+="</response>";
	 System.out.println("xml in Add........."+xml);	
	 
 }
 
 
	  if(command.equalsIgnoreCase("updation")) {
		 
		  System.out.println("In updation function");	
			 
			 int txtEmployee_id=0;
			 int off_id=0;
			 int daAmount=0;
			 txtEmployee_id=Integer.parseInt(request.getParameter("txtEmployeeid").trim());	 
			 off_id=Integer.parseInt(request.getParameter("txtOffId").trim());	 
			 daAmount=Integer.parseInt(request.getParameter("da_Amount").trim());	 
			 
			 try
			 {
				 xml="<response><command>updation</command>";
				 
				 
				 
				 String ss2="update  HRM_EMPLOYEE_DA_DETAILS set OFFICE_ID=?,DA_AMOUNT=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),PROCESS_FLOW_ID=?  where EMPLOYEE_ID=? ";
				 
				 pss2=con.prepareStatement(ss2);
				 
				 pss2.setInt(1,off_id);
				 pss2.setInt(2,daAmount);
				 pss2.setString(3,updatedby);
				 pss2.setString(4,"MD");
				 pss2.setInt(5,txtEmployee_id);
				 
				 pss2.executeUpdate();
				 
				 
				 xml+="<status>success</status>";
				 
			 }
			 catch(Exception e)
			 {
				 System.out.println("Exception..."+e);
				 xml+="<status>failure</status>";
			 }
			 xml+="</response>";
			 System.out.println("xml in updation........."+xml);	
	 }
	    
	  if(command.equalsIgnoreCase("validate")) {
			 
		  System.out.println("In validate function");	
			 
			 int txtEmployee_id=0;
			 int off_id=0;
			 int daAmount=0;
			 txtEmployee_id=Integer.parseInt(request.getParameter("txtEmployeeid").trim());	 
			 off_id=Integer.parseInt(request.getParameter("txtOffId").trim());	 
			 daAmount=Integer.parseInt(request.getParameter("da_Amount").trim());	 
			 
			 try
			 {
				 xml="<response><command>validate</command>";
				 
				 
				 
				 String ss2="update  HRM_EMPLOYEE_DA_DETAILS set OFFICE_ID=?,DA_AMOUNT=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),PROCESS_FLOW_ID=?  where EMPLOYEE_ID=? ";
				 
				 pss2=con.prepareStatement(ss2);
				 
				 pss2.setInt(1,off_id);
				 pss2.setInt(2,daAmount);
				 pss2.setString(3,updatedby);
				 pss2.setString(4,"FR");
				 pss2.setInt(5,txtEmployee_id);
				 
				 pss2.executeUpdate();
				 
				 
				 xml+="<status>success</status>";
				 
			 }
			 catch(Exception e)
			 {
				 System.out.println("Exception..."+e);
				 xml+="<status>failure</status>";
			 }
			 xml+="</response>";
			 System.out.println("xml in Validation........."+xml);	
	 }
	      if(command.equalsIgnoreCase("Deletion")) {
	    	  System.out.println("In Deletion function");	
				 
				 int txtEmployee_id=0;
				 int off_id=0;
				 int daAmount=0;
				 txtEmployee_id=Integer.parseInt(request.getParameter("txtEmployeeid").trim());	 
				 
				 
				 try
				 {
					 xml="<response><command>Deletion</command>";
					 
					 
					 
					 String ss2="Delete from  HRM_EMPLOYEE_DA_DETAILS   where EMPLOYEE_ID=? ";
					 
					 pss2=con.prepareStatement(ss2);
					 
					 pss2.setInt(1,txtEmployee_id);
					 
					 pss2.executeUpdate();
					 
					 
					 xml+="<status>success</status>";
					 
				 }
				 catch(Exception e)
				 {
					 System.out.println("Exception..."+e);
					 xml+="<status>failure</status>";
				 }
				 xml+="</response>";
				 System.out.println("xml in Deletion........."+xml);	
	     }	     
	  
	     
	  	
	  	
		 out.println(xml);
		 out.close();	        
	
	    }
    
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	 Connection connection=null;
         try {
             ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
             String ConnectionString="";
             String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
             String strdsn=rs.getString("Config.DSN");
             String strhostname=rs.getString("Config.HOST_NAME");
             String strportno=rs.getString("Config.PORT_NUMBER");
             String strsid=rs.getString("Config.SID");
             String strdbusername=rs.getString("Config.USER_NAME");
             String strdbpassword=rs.getString("Config.PASSWORD");
          //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());                          
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
                   //  System.out.println(session);
                 }catch(Exception e)
                 {
                 System.out.println("Redirect Error :"+e);
                 }                
                 String output="";                
                 try {
                     output=request.getParameter("output");
                 }                
                 catch(Exception e) {
                     System.out.println("Exception in assigning..."+e);
                 }
                 try{
             		Map parameters = new HashMap();
             		String s = "",MyQry="";
             		MyQry="SELECT EmpId, " +
             		"  Employee_Name, " +
             		"  Designation, " +
             		"  DA_AMOUNT " +
             		"FROM " +
             		"  (SELECT Employee_Id AS EmpId, " +
             		"    Office_Id, " +
             		"    DA_AMOUNT, " +
             		"    PROCESS_FLOW_ID " +
             		"  FROM Hrm_Employee_Da_Details  where process_flow_id='FR' " +
             		"  ) a " +
             		"LEFT OUTER JOIN " +
             		"  (SELECT Employee_Id, " +
             		"    Designation_Id, " +
             		"    EMPLOYEE_STATUS_ID " +
             		"  FROM Hrm_Emp_Current_Posting " +
             		"  )B " +
             		"ON a.EmpId=b.Employee_Id " +
             		"LEFT OUTER JOIN " +
             		"  (SELECT employee_id, " +
             		"    EMPLOYEE_PREFIX " +
             		"    ||'.' " +
             		"    ||EMPLOYEE_INITIAL " +
             		"    ||'.' " +
             		"    ||EMPLOYEE_NAME AS Employee_Name " +
             		"  FROM hrm_mst_employees " +
             		"  )c " +
             		"ON a.EmpId=c.employee_id " +
             		"LEFT OUTER JOIN " +
             		"  (SELECT Designation_Id,Designation FROM Hrm_Mst_Designations " +
             		"  )D " +
             		"ON b.Designation_Id=d.Designation_Id " +
             		"ORDER BY Empid";
             		
             		s=request.getRealPath("/WEB-INF/ReportSrc/Employee_DA_Details.jrxml");
             		output=request.getParameter("output");
             	JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                System.out.println(MyQry);
             	JRDesignQuery query=new JRDesignQuery();
             	query.setText(MyQry);
             	jasperDesign.setQuery(query);
             	JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
             	JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, null, connection);
                ByteArrayOutputStream bout=new ByteArrayOutputStream();
             	if(output.equalsIgnoreCase("pdf"))
             	{
             	OutputStream os=response.getOutputStream();
             	response.setContentType("application/pdf");
             	response.setHeader ("Content-Disposition", "attachment;filename=\"Employee_DA_Details.pdf\"");
             	os.write(JasperManager.printReportToPdf(jasperPrint));
             	os.close();
             	}
             	else if(output.equalsIgnoreCase("excel"))
             	{
             		response.setContentType("application/vnd.ms-excel");
             		response.setHeader ("Content-Disposition", "attachment;filename=\"Employee_DA_Details.xls\"");
             		OutputStream os=response.getOutputStream();
             			JRXlsExporter exporterXLS = new JRXlsExporter(); 
             			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
             			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, bout);
             			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
             			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
             			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
             			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
             			exporterXLS.exportReport();
             		    byte[] buf=bout.toByteArray();
             		    os.write(buf);
             			os.close();
             		}
             	else if(output.equalsIgnoreCase("html"))
             	{
             		/*response.setContentType("text/html");
             		response.setHeader ("Content-Disposition", "attachment;filename=\"ViewBalance.html\"");
                     JRHtmlExporter exporter = new JRHtmlExporter();
                     
                     exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                     exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, bout);
                     exporter.exportReport();
             		    byte[] buf=bout.toByteArray();
             		    os.write(buf);
                   //  byte[] buf=bout.getBytes();
             	//	os.write(buf);
             		//os.write(JasperManager.p(jasperPrint)); */
             		 response.setContentType("text/html");
             		 response.setHeader ("Content-Disposition", "attachment;filename=\"Employee_DA_Details.html\"");
             		 PrintWriter out = response.getWriter();
             		 JRHtmlExporter exporter = new JRHtmlExporter();
             		 // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
             		 //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
             		 //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
             		 //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
             		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
             		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
             		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
             		 exporter.exportReport();
             		  out.flush();
             		 out.close();
             		}
             	}
             	catch(Exception e)
             	{
             		e.printStackTrace();
             	}
    }
}
