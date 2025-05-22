package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class GPF_Final_Settlement_Uppdate extends HttpServlet{
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
    
    
    public void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
            {
    	
    	Connection con = null;
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
			
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
		}
		
		
		JasperPrint jasperPrint=null;
  	 	File reportFile=null;
 
	 String xml="";
	 response.setContentType(CONTENT_TYPE);
	 PrintWriter out = response.getWriter();
	 response.setHeader("Cache-Control","no-cache");  
	 HttpSession session=request.getSession(false);
	 ResultSet rs = null, rss = null,rss2=null,rr=null,rrss=null;
		PreparedStatement ps = null, pss = null,pss2=null,pp=null,ppss=null;
		
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
	 	
	 	System.out.println("COMMAND=============>"+command);
	} catch (Exception e) {
			// TODO: handle exception
	}
	 boolean gpfIntegerCheck=false;
	 boolean gpfValidity=false;
	 
	 session =request.getSession(false);
	 String updatedby=(String)session.getAttribute("UserId");
	 String employee_id=updatedby;
	 String emppp_id=employee_id.substring(4);
	 
	
	 
	 System.out.println("EMPLOYEEE________________ID======"+employee_id);
	 System.out.println("EMPLOYEEE________________ID======"+emppp_id);
	 
	 long l=System.currentTimeMillis();
	 java.sql.Timestamp ts=new java.sql.Timestamp(l);
	 
	 
	 if(command.equalsIgnoreCase("report"))
	 {
		 int count=0;
		 String off_name=null;
		 xml="<response><command>taken_office</command>";
	     xml=xml+"<status>taken_offices</status>";
	     String intrest_upto1=request.getParameter("intrest_upto").trim();
	     try {
				ps=con.prepareStatement("(SELECT * " +
						"FROM " +
						"  (SELECT office_id AS off_id FROM HRM_EMP_CURRENT_POSTING WHERE employee_id=? " +
						"  )a " +
						"LEFT OUTER JOIN " +
						"  (SELECT office_id,office_name FROM COM_MST_OFFICES " +
						"  ) b " +
						"ON a.off_id=b.office_id " +
						")" 
	);

				ps.setString(1,emppp_id);
				rs=ps.executeQuery();
				while(rs.next())
				{
					count++;
					System.out.println("count================="+count+" ******************");
					xml+="<COUNT>"+count+"</COUNT>";
					off_name=rs.getString("office_name");
					
					System.out.println("OFFICE-NAME======(((())))=====>"+off_name);
				}
				
		     
		     } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     
		   
	     
	     
	     
	     
	     
		 Map map=new HashMap();
		 
		 
		 reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/TWAD_PROJECT.jasper"));	
		 String rep=getServletContext().getRealPath("/WEB-INF/ReportSrc/");
	 		  rep=rep+"/";
	 		  
	 		 map.put("login_off",off_name);
   	 	     map.put("month_year",intrest_upto1);
   	 	     
   	 	 if (!reportFile.exists())
	      {
	          System.out.println("does not exsist");
	          throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	      }
	      try {
			System.out.println(JRLoader.loadObject(reportFile.getPath()));
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     JasperReport jasperReport = null;
		try {
			jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	 	      System.out.println("Report File:"+reportFile); 
 	  
 		 //  System.out.println("emp_id:"+emp_id);
 	 	     
 	 	  
 	 	    
 	 	    
 	 	      try {
				jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	 	      
 	 	    byte[] buf = null;
			try {
				buf = JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           response.setContentType("application/pdf");
	           response.setContentLength(buf.length);
	           response.setHeader ("Content-Disposition", "attachment;filename=\"Generate_Final_Settlement.pdf\"");
	          // OutputStream out1 = response.getOutputStream();
	        //   out.write(buf, 0, buf.length);
	           
	           out.close();
	           
	           if(count!=0)
			    {
		     			xml=xml+"<flag>success</flag>";     			
			               	            	
			            System.out.println("hi...2...success");	
		     		}else{
		     			xml=xml+"<flag>failure</flag>";
		     			System.out.println("hi...3...failure");	
		     			
		     		}
		 
	 }
	  
	  
	  xml=xml+"</response>";
	    
	  out.write(xml);
	  out.flush();
	  out.close();
	  System.out.println("XML===="+xml);
	    
	 }

            
            
    
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {    	 	
	
    	//GpfAuthMstModel.closeConnection();
    	//GpfAuthMstModel.openConnection();
    	
    	Connection con = null;
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
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
		}
		
		
		JasperPrint jasperPrint=null;
  	 	File reportFile=null;
 
	 String xml="";
	 response.setContentType(CONTENT_TYPE);
	 PrintWriter out = response.getWriter();
	 response.setHeader("Cache-Control","no-cache");  
	 HttpSession session=request.getSession(false);
	 ResultSet rs = null, rss = null,rss2=null;
		PreparedStatement ps = null, pss = null,pss2=null,pp=null;
		
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
	 	
	 	System.out.println("COMMAND=============>"+command);
	} catch (Exception e) {
			// TODO: handle exception
	}
	 boolean gpfIntegerCheck=false;
	 boolean gpfValidity=false;
	 
	 session =request.getSession(false);
	 String updatedby=(String)session.getAttribute("UserId");
	 String employee_id=updatedby;
	 String emppp_id=employee_id.substring(4);
	 
	
	 
	 System.out.println("EMPLOYEEE________________ID======"+employee_id);
	 System.out.println("EMPLOYEEE________________ID======"+emppp_id);
	 
	 long l=System.currentTimeMillis();
	 java.sql.Timestamp ts=new java.sql.Timestamp(l);
	 
	 
	 
	 

	 if(command.equalsIgnoreCase("Check"))
	  {

	  int gpfNo = Integer.parseInt(request.getParameter("gpf_no").trim());
	     String sqls="select GPF_NO from HRM_GPF_FINAL_SETTLEMENT_PROC where gpf_no=?";
	     int count=0;
	     xml="<response><command>Check</command>";
			xml+="<status>Check</status>";
	     try {
			pss=con.prepareStatement(sqls);
			pss.setInt(1,gpfNo);
			rss=pss.executeQuery();
			while(rss.next())
			{
				count++;
				xml+="<count>"+count+"</count>";
				if(gpfNo==rss.getInt("GPF_NO"))
				{
					xml+="<flag>success</flag>";
				}
			}
			if(count==0)
			{
			xml+="<count>"+count+"</count>";
			xml+="<flag>failure</flag>";
			
			}
	     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
			
		}
	     //xml=xml+"</response>";
	     System.out.print("XML CHECKKKKING ==== "+xml);
	     
}
	 
	 

	     
	  if(command.equalsIgnoreCase("Get")) {
	     int gpfNo=0;	 
		 try {
		 	gpfNo = Integer.parseInt(request.getParameter("gpf_no").trim());
		 	
		 	gpfIntegerCheck=true;
		} catch (Exception e) {
			// TODO: handle exception
			gpfIntegerCheck=false;
		}
	     xml="<response><command>get</command>";
	     
	     GpfAuthMstBean gpfAuthMstBean=null;
	     int settlementStatus=0;
	     String tempStrCheck=GpfAuthMstModel.xmlEmployee(gpfNo);
	     System.out.println("tempStrCheck.length()==============>"+tempStrCheck.length());
	     if(tempStrCheck.length()>10)
	    	 gpfValidity=true;
	     
	     if(gpfIntegerCheck && gpfValidity){
	     	xml=xml+"<flag>success</flag>";
	     	System.out.println("GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()=======>"+GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo());
	     	if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()==0){
	     		System.out.println("GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()=======>"+GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo());
	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
	     		xml+=GpfAuthMstModel.xmlFirstRelieveDetails(gpfNo);
	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
	     		xml=xml+"<status>GpfNotExist</status>";	     		
	     	}
	     	else if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("CR")
	     			|| GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("ED") ){
	     		gpfAuthMstBean=GpfAuthMstModel.getGpfAuthMstBean(gpfNo);
	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
	     		xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);	
	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
	     		xml=xml+"<status>GpfEdited</status>";
	     	}
	     	else if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("FR")){
	     		gpfAuthMstBean=GpfAuthMstModel.getGpfAuthMstBean(gpfNo);
	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
	     		xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);	
	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
	     		xml=xml+"<status>GpfValidated</status>";
	     	}
	     	
	     }else if(!gpfIntegerCheck){
	     	xml=xml+"<status>inValidInteger</status>";
	     	xml=xml+"<flag>success</flag>";
	     }else if(!gpfValidity){
		     	xml=xml+"<status>inValidGpf</status>";
		     	xml=xml+"<flag>success</flag>";
		 }
	     xml=xml+"</response>";
	     System.out.println(xml);
	 }
	 else if(command.equalsIgnoreCase("Update")) {
		int gpfNo=0,emp_id=0;	
		String prefix = null,pro_officer_name = null,suffix= null,pro_desingnation= null,letter_no= null,letter_date= null,jur_off= null,jur_add1= null,jur_add2= null,jur_add3= null;
		int jur_pincode = 0;
		
	 	String relieve_status="";
	 	String relieve_date="";
	 	String intrest_upto= null,intrest_upto1= null,subject= null,reference= null;
	 	int txtLastSlipClosingBal = 0,regu_acc_unit_code= 0,txtImpoundBal= 0,imp_acc_unit_code= 0,txtSubscriptionCredit= 0,imp_2003= 0,txtSubscriptionDebit= 0,da_acc_unit= 0;
	 	String dth_Legal_person= null,dth_add1= null,dth_add2= null,dth_add3= null;
	 	int pin= 0,percent=0;
	 	int dth_Legal_person_ID=0;
	 	String any_comments= null,for_off1= null,for_design1= null,copy= null;
	 	String int_tobe_calc_month="";
	 	String int_tobe_calc_year="";
	 	String int_tobe_calc_date="";
	 	String dth_Relation = null;
	 	String dth_date2 = null;
	 	try {
	 		gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());	
	 		emp_id=Integer.parseInt(request.getParameter("emp_id").trim());	
	 		prefix=request.getParameter("prefix").trim();
	 		pro_officer_name=request.getParameter("pro_officer_name");
	 		suffix=request.getParameter("suffix");
	 		pro_desingnation=request.getParameter("pro_desingnation");
	 		letter_no=request.getParameter("letter_no");
	 		letter_date=request.getParameter("letter_date");
	 		jur_off=request.getParameter("jur_off");
	 		
	 		jur_add1=request.getParameter("jur_add1");
	 		jur_add2=request.getParameter("jur_add2");
	 		jur_add3=request.getParameter("jur_add3");
	 		jur_pincode=Integer.parseInt(request.getParameter("jur_pincode"));	
	 		
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	
	 	try {
	 		relieve_date=request.getParameter("relieve_date");
	 		subject=request.getParameter("subject");
	 		reference=request.getParameter("reference");
	 		System.out.println("subject............"+subject+"reference...."+reference);
	 		txtLastSlipClosingBal=Integer.parseInt(request.getParameter("txtLastSlipClosingBal"));
	 		regu_acc_unit_code=Integer.parseInt(request.getParameter("regu_acc_unit_code"));
	 		txtImpoundBal=Integer.parseInt(request.getParameter("txtImpoundBal"));
	 		imp_acc_unit_code=Integer.parseInt(request.getParameter("imp_acc_unit_code"));
	 		txtSubscriptionCredit=Integer.parseInt(request.getParameter("txtSubscriptionCredit"));
	 		imp_2003=Integer.parseInt(request.getParameter("imp_2003"));
	 		txtSubscriptionDebit=Integer.parseInt(request.getParameter("txtSubscriptionDebit"));
	 		da_acc_unit=Integer.parseInt(request.getParameter("da_acc_unit"));
	 		
	 		
	 		
	 		any_comments=request.getParameter("any_comments");
	 		for_off1=request.getParameter("for_off1");
	 		for_design1=request.getParameter("for_design1");
	 		copy=request.getParameter("copy");
	 		
	 		
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		
	 		
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_year=request.getParameter("int_tobe_calc_year");
		} catch (Exception e) {
			// TODO: handle exception
		}
	     
	 	int_tobe_calc_date="";
	     	
	     xml="<response><command>Update</command>";
	     xml=xml+"<status>Update</status>";
	     System.out.println("XML==========="+xml);
	     try {
	    	 System.out.println("jur office===="+jur_off);
			ps=con.prepareStatement("UPDATE HRM_GPF_FINAL_SETTLEMENT_PROC " +
					"SET AUTH_LTR_NO              = ?," +
					"  AUTH_LTR_DATE              = to_date(?,'dd/mm/yyyy')," +
					"  PRESID_OFFICER_PREFIX      = ?," +
					"  PRESID_OFFICER_NAME        = ?," +
					"  PRESID_OFFICER_SUFFIX      = ?," +
					"  PRESID_OFFICER_DESIG       = ?," +
					"  SUBJECT                    = ?," +
					"  REFERENCE                  = ?," +
					"  COPY_TO                    = ?," +
					"  JURISDICTION_OFFICER_DESIG = ?," +
					"  JURISDICTION_OFFICE_ADD1   = ?," +
					"  JURISDICTION_OFFICE_ADD2   = ?," +
					"  JURISDICTION_OFFICE_ADD3   = ?," +
					"  JURISDICTION_OFFICE_PIN    = ?," +
					"  FOWARD_OFFICER_NAME        = ?," +
					"  FOWARD_OFFICER_DESIG       = ?," +
					"  ADDL_CONDITIONS            = ?," +
					"  UPDATED_BY_USER_ID         = ?," +
					"  UPDATED_DATE               = clock_timestamp()," +
					"  PROCESS_FLOW_STATUS_ID     = 'MD'" +
//					"  LEGAL_HEIR_NAME            = ?, " +
//					"  LEGAL_HEIR_ADD1            = ?, " +
//					"  LEGAL_HEIR_ADD2            = ?, " +
//					"  LEGAL_HEIR_ADD3            = ?, " +
//					"  LEGAL_HEIR_PIN             = ?, " +
//					
//					"  LIC_DATE                    = to_date(?,'dd/mm/yyyy'), " +
//					"  RELATIONSHIP                    = ? " +
					
					"WHERE GPF_NO                 = ?" );
			
			//ps.setInt(2,emp_id);
			
			
			 
			
			ps.setString(1,letter_no);
    		ps.setString(2, letter_date);
			ps.setString(3, prefix);
			ps.setString(4, pro_officer_name);
			ps.setString(5, suffix);
			ps.setString(6, pro_desingnation);
			ps.setString(7, subject);
			ps.setString(8, reference);
			ps.setString(9, copy);			
			ps.setString(10, jur_off);
			ps.setString(11, jur_add1);
			ps.setString(12, jur_add2);
			ps.setString(13, jur_add3);
			ps.setInt(14, jur_pincode);
			ps.setString(15, for_off1);
			ps.setString(16, for_design1);
			ps.setString(17, any_comments);
			ps.setString(18, updatedby);

			ps.setInt(19,gpfNo);
			System.out.println("XML==========="+xml);
			ps.executeUpdate();
			System.out.println("XML====XML=====XML=="+xml);
			relieve_status=request.getParameter("relieve_status");
	 	 		System.out.println("Relieval Status==="+relieve_status);
	 	 		//if(relieve_status.equalsIgnoreCase("DTH"))
//	 	 		{
//	 	 			System.out.print("how te read..mannnnnnn.");
//	 	 			dth_Legal_person_ID=Integer.parseInt(request.getParameter("dth_Legal_person_ID"));
//	 	 			//System.out.println("dth_Legal_person_ID"+dth_Legal_person_ID);
//	 	 		dth_Legal_person=request.getParameter("dth_Legal_person");
//	 	 		//System.out.println("dth_Legal_person..."+dth_Legal_person);
//	 	 		
//	 	 		dth_Relation=request.getParameter("dth_Relation");
//	 	 		//System.out.println("dth_Relation..."+dth_Relation);
//	 	 		dth_date2=request.getParameter("dth_date2");
//	 	 		//System.out.println("dth_date2..."+dth_date2);
//	 	 		
//	 	 		dth_add1=request.getParameter("dth_add1");
//	 	 		//System.out.println("how te read..."+dth_add1);
//	 	 		
//	 	 		dth_add2=request.getParameter("dth_add2");
//	 	 		//System.out.println("dth_add2..."+dth_add2);
//	 	 		
//	 	 		dth_add3=request.getParameter("dth_add3");	
//	 	 		//System.out.println("dth_add3..."+dth_add3);
//	 	 		
//	 	 		pin=Integer.parseInt(request.getParameter("pin"));
//	 	 		//System.out.println("how te read pin..."+pin);
//	 	 		percent=Integer.parseInt(request.getParameter("percent"));
//	 	 		
//	 	 		 System.out.println("====dth_Legal_person==="+dth_Legal_person);
//		    	 System.out.println("====dth_Relation==="+dth_Relation);
//		    	 System.out.println("====dth_date2==="+dth_date2);
//		    	 System.out.println("====dth_add1==="+dth_add1);
//		    	 System.out.println("====dth_add2==="+dth_add2);
//		    	 System.out.println("====dth_add3==="+dth_add3);
//		    	 System.out.println("====pin==="+pin);
//		    	 System.out.println("====gpfNo==="+gpfNo);
//		    	 
//		    	 String ss2="INSERT " +
//							"INTO HRM_GPF_LEGAL_DETAILS " +
//							"  ( " +
//							"    GPF_NO, " +
//							"    EMPLOYEE_ID, " +
//							"    LEGAL_HIER_ID, " +
//							"    LEGAL_HEIR_NAME, " +
//							"    RELATIONSHIP, " +
//							"    LIC_DATE, " +
//							"    LEGAL_HEIR_ADD1, " +
//							"    LEGAL_HEIR_ADD2, " +
//							"    LEGAL_HEIR_ADD3, " +
//							"    LEGAL_HEIR_PIN, " +
//							"    LEGAL_PERCENT " +
//							"  ) " +
//							"  VALUES " +
//							"  ( " +
//							"    ?, " +
//							"    ?, " +
//							"    ?, " +
//							"    ?, " +
//							"    ?, " +
//							"    TO_DATE(?,'DD/MM/YYYY'), " +
//							"    ?, " +
//							"    ?, " +
//							"    ?, " +
//							"    ?, " +
//							"    ? " +
//							"  )"
//							;
//		    	 pp=con.prepareStatement(ss2);
//		    	 
//		    	 pp.setInt(1,gpfNo);
//		    	 pp.setInt(2,emp_id);
//		    	 pp.setInt(3, dth_Legal_person_ID);
//		    	 pp.setString(4, dth_Legal_person);
//		    	 pp.setString(5,dth_Relation);
//		    	 pp.setString(6,dth_date2);
//					
//		    	 pp.setString(7, dth_add1);
//		    	 pp.setString(8, dth_add2);
//		    	 pp.setString(9, dth_add3);
//		    	 pp.setInt(10,pin);
//		    	 pp.setInt(11,percent);
//		    	 
//					/*pp.setString(1, dth_Legal_person);
//					pp.setString(2,dth_Relation);
//					pp.setString(3,dth_date2);
//					pp.setString(4, dth_add1);
//					pp.setString(5, dth_add2);
//					pp.setString(6, dth_add3);
//					pp.setInt(7,pin);
//					pp.setInt(8, gpfNo);
//					pp.setInt(9, dth_Legal_person_ID);*/
//					
//					pp.executeUpdate();
//					
//					
//	 	 			 	 		
//	 	 		}
	 		
	    	 
	    	
				
	    	 

	     }catch(Exception e)
	     {
	    	 System.out.println("SQLERROR death detailss  =====   " +e);
				xml=xml+"<flag>failure</flag>";
	     }
	     xml=xml+"<flag>success</flag>";
	     
	   //  GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();	     
	  //   String statusid=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId();
	     
//	     if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()==0){	     	
//	     	int_tobe_calc_date=GpfAuthMstModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
//	     	
//	     	gpfAuthMstBean.setGpfNo(gpfNo);
//	     	gpfAuthMstBean.setSettlementReason(relieve_status);
//	     	gpfAuthMstBean.setRelievalDate(relieve_date);
//	     	gpfAuthMstBean.setIntToBeCalcDate(int_tobe_calc_date);	
//	     	gpfAuthMstBean.setStatusId("CR");
//	     	gpfAuthMstBean.setUpdatedDate(ts);
//	     	gpfAuthMstBean.setUpdateByUserId(updatedby);
//         	boolean result=GpfAuthMstModel.insertGpfAuthMstBean(gpfAuthMstBean);
//            if(result){
//            	xml=xml+"<flag>success</flag>";            	
//            	xml+=GpfAuthMstModel.xmlEmployee(gpfNo);
//             	xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);         		
//            }
//            else{
//            	xml=xml+"<flag>failure</flag>";            	
//            }
//	     }
//	     else {
//	     	xml=xml+"<flag>failure</flag>";
//	     }
	     System.out.println("XML OF RESPONSE  =====   " +xml);
	 }
	 else if(command.equalsIgnoreCase("Draft")) {
		 
	    int gpfNo=0,count=0;	     	
	 	String relieve_status="";
	 	String relieve_date="";
	 	String int_tobe_calc_month="";
	 	String int_tobe_calc_year="";
	 	String int_tobe_calc_date="";
	 	try {
	 		gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());	  
	 		
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_status=request.getParameter("relieve_status").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_date=request.getParameter("relieve_date").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}	     
	 	int_tobe_calc_date="";
//	 	GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();	  
//	 	String statusid=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId();
//	    int_tobe_calc_date=GpfAuthMstModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
//	     
	     xml="<response><command>Draft</command>";
	     xml=xml+"<status>draft</status>";
	     
	     try {
			ps=con.prepareStatement("(SELECT * " +
					"FROM " +
					"  (SELECT GPF_NO, " +
					"    EMPLOYEE_ID AS EMP_ID, " +
					"    PRESID_OFFICER_PREFIX, " +
					"    PRESID_OFFICER_NAME, " +
					"    PRESID_OFFICER_SUFFIX, " +
					"    PRESID_OFFICER_DESIG, " +
					"    AUTH_LTR_NO, " +
					"    TO_CHAR(AUTH_LTR_DATE,'dd/mm/yyyy') AS AUTH_LTR_DATE, " +
					"    JURISDICTION_OFFICER_DESIG, " +
					"    JURISDICTION_OFFICE_ADD1, " +
					"    JURISDICTION_OFFICE_ADD2, " +
					"    JURISDICTION_OFFICE_ADD3, " +
					"    JURISDICTION_OFFICE_PIN, " +
					"    RELIEVAL_REASON_ID, " +
					"    TO_CHAR(RELIEVAL_DATE,'dd/mm/yyyy') AS RELIEVAL_DATE, " +
					"    INTEREST_UPTO, " +
					"    SUBJECT, " +
					"    REFERENCE, " +
					"    GPF_REG_BAL_AMT, " +
					"    GPF_REG_BAL_AC_HEAD, " +
					"    GPF_IMP_BAL_AMT, " +
					"    GPF_IMP_BAL_AC_HEAD, " +
					"    GPF_IMP03_BAL_AMT, " +
					"    GPF_IMP03_BAL_AC_HEAD, " +
					"    GPF_1DA_BAL_AMT, " +
					"    GPF_1DA_BAL_AC_HEAD, " +
					"    ADDL_CONDITIONS, " +
					"    FOWARD_OFFICER_NAME, " +
					"    FOWARD_OFFICER_DESIG, " +
					"    COPY_TO, " +
					"    PROCESS_FLOW_STATUS_ID " +
					"  FROM HRM_GPF_FINAL_SETTLEMENT_PROC " +
					"  WHERE GPF_NO               =? " +
					"  AND (PROCESS_FLOW_STATUS_ID='CR' " +
					"  OR PROCESS_FLOW_STATUS_ID  ='MD') " +
					"  ) a " +
					"LEFT OUTER JOIN " +
					"  (SELECT EMPLOYEE_NAME,to_char(DATE_OF_BIRTH,'dd/mm/yyyy') as DATE_OF_BIRTH,employee_id FROM HRM_MST_EMPLOYEES " +
					"  ) c " +
					"ON a.EMP_ID=c.employee_id " +
					"LEFT OUTER JOIN " +
					"  (SELECT DESIGNATION_ID,employee_id FROM HRM_EMP_CURRENT_POSTING " +
					"  ) b " +
					"ON c.employee_id=b.employee_id " +
					"LEFT OUTER JOIN " +
					"  (SELECT DESIGNATION_ID,designation AS desig FROM hrm_mst_designations " +
					"  ) d " +
					"ON d.DESIGNATION_ID=b.DESIGNATION_ID " +
					")"
);

			ps.setInt(1,gpfNo);
			rs=ps.executeQuery();
			while(rs.next())
			{
				count++;
				
				xml+="<COUNT>"+count+"</COUNT>";
				xml+="<GPF_NO>"+rs.getInt("GPF_NO")+"</GPF_NO>";
				xml+="<EMP_ID>"+rs.getInt("EMP_ID")+"</EMP_ID>";
				xml+="<EMPLOYEE_NAME>"+rs.getString("EMPLOYEE_NAME")+"</EMPLOYEE_NAME>";
				xml+="<desig>"+rs.getString("desig")+"</desig>";
				xml+="<DATE_OF_BIRTH>"+rs.getString("DATE_OF_BIRTH")+"</DATE_OF_BIRTH>";
				xml+="<RELIEVAL_REASON_ID>"+rs.getString("RELIEVAL_REASON_ID")+"</RELIEVAL_REASON_ID>";
				xml+="<RELIEVAL_DATE>"+rs.getString("RELIEVAL_DATE")+"</RELIEVAL_DATE>";
				xml+="<GPF_REG_BAL_AMT>"+rs.getInt("GPF_REG_BAL_AMT")+"</GPF_REG_BAL_AMT>";
				xml+="<GPF_REG_BAL_AC_HEAD>"+rs.getInt("GPF_REG_BAL_AC_HEAD")+"</GPF_REG_BAL_AC_HEAD>";
				xml+="<GPF_IMP_BAL_AMT>"+rs.getInt("GPF_IMP_BAL_AMT")+"</GPF_IMP_BAL_AMT>";
				xml+="<GPF_IMP_BAL_AC_HEAD>"+rs.getInt("GPF_IMP_BAL_AC_HEAD")+"</GPF_IMP_BAL_AC_HEAD>";
				xml+="<GPF_IMP03_BAL_AMT>"+rs.getInt("GPF_IMP03_BAL_AMT")+"</GPF_IMP03_BAL_AMT>";
				xml+="<GPF_IMP03_BAL_AC_HEAD>"+rs.getInt("GPF_IMP03_BAL_AC_HEAD")+"</GPF_IMP03_BAL_AC_HEAD>";
				xml+="<GPF_1DA_BAL_AMT>"+rs.getInt("GPF_1DA_BAL_AMT")+"</GPF_1DA_BAL_AMT>";
				xml+="<GPF_1DA_BAL_AC_HEAD>"+rs.getInt("GPF_1DA_BAL_AC_HEAD")+"</GPF_1DA_BAL_AC_HEAD>";
				xml+="<INTEREST_UPTO>"+rs.getString("INTEREST_UPTO")+"</INTEREST_UPTO>";
				
//				xml+="<LEGAL_HEIR_NAME>"+rs.getString("LEGAL_HEIR_NAME")+"</LEGAL_HEIR_NAME>";
//				xml+="<LIC_DATE>"+rs.getString("LIC_DATE")+"</LIC_DATE>";
//				xml+="<RELATIONSHIP>"+rs.getString("RELATIONSHIP")+"</RELATIONSHIP>";
//				xml+="<LEGAL_HEIR_ADD1>"+rs.getString("LEGAL_HEIR_ADD1")+"</LEGAL_HEIR_ADD1>";
//				xml+="<LEGAL_HEIR_ADD2>"+rs.getString("LEGAL_HEIR_ADD2")+"</LEGAL_HEIR_ADD2>";
//				xml+="<LEGAL_HEIR_ADD3>"+rs.getString("LEGAL_HEIR_ADD3")+"</LEGAL_HEIR_ADD3>";
//				xml+="<LEGAL_HEIR_PIN>"+rs.getInt("LEGAL_HEIR_PIN")+"</LEGAL_HEIR_PIN>";
				
				
				xml+="<AUTH_LTR_NO>"+rs.getString("AUTH_LTR_NO")+"</AUTH_LTR_NO>";
				xml+="<AUTH_LTR_DATE>"+rs.getString("AUTH_LTR_DATE")+"</AUTH_LTR_DATE>";
				
				xml+="<PRESID_OFFICER_PREFIX>"+rs.getString("PRESID_OFFICER_PREFIX")+"</PRESID_OFFICER_PREFIX>";
				xml+="<PRESID_OFFICER_NAME>"+rs.getString("PRESID_OFFICER_NAME")+"</PRESID_OFFICER_NAME>";
				xml+="<PRESID_OFFICER_SUFFIX>"+rs.getString("PRESID_OFFICER_SUFFIX")+"</PRESID_OFFICER_SUFFIX>";
				xml+="<PRESID_OFFICER_DESIG>"+rs.getString("PRESID_OFFICER_DESIG")+"</PRESID_OFFICER_DESIG>";
				
				
				xml+="<SUBJECT>"+rs.getString("SUBJECT")+"</SUBJECT>";
				xml+="<REFERENCE>"+rs.getString("REFERENCE")+"</REFERENCE>";
				xml+="<COPY_TO_DATA>"+rs.getString("COPY_TO")+"</COPY_TO_DATA>";
				
				
				xml+="<JURISDICTION_OFFICER_DESIG>"+rs.getString("JURISDICTION_OFFICER_DESIG")+"</JURISDICTION_OFFICER_DESIG>";
				xml+="<JURISDICTION_OFFICE_ADD1>"+rs.getString("JURISDICTION_OFFICE_ADD1")+"</JURISDICTION_OFFICE_ADD1>";
				xml+="<JURISDICTION_OFFICE_ADD2>"+rs.getString("JURISDICTION_OFFICE_ADD2")+"</JURISDICTION_OFFICE_ADD2>";
				xml+="<JURISDICTION_OFFICE_ADD3>"+rs.getString("JURISDICTION_OFFICE_ADD3")+"</JURISDICTION_OFFICE_ADD3>";
				xml+="<JURISDICTION_OFFICE_PIN>"+rs.getInt("JURISDICTION_OFFICE_PIN")+"</JURISDICTION_OFFICE_PIN>";
				
				
				xml+="<FOWARD_OFFICER_NAME>"+rs.getString("FOWARD_OFFICER_NAME")+"</FOWARD_OFFICER_NAME>";
				xml+="<FOWARD_OFFICER_DESIG>"+rs.getString("FOWARD_OFFICER_DESIG")+"</FOWARD_OFFICER_DESIG>";
				
				
				xml+="<ADDL_CONDITIONS>"+rs.getString("ADDL_CONDITIONS")+"</ADDL_CONDITIONS>";
				
				
				try
				{
					String rr=rs.getString("RELIEVAL_REASON_ID");
					if(rr.equalsIgnoreCase("DTH"))
					{
						String s1="SELECT GPF_NO, " +
								"  EMPLOYEE_ID, " +
								"  LEGAL_HIER_ID, " +
								"  LEGAL_HEIR_NAME, " +
								"  RELATIONSHIP, " +
								"  TO_CHAR(LIC_DATE,'dd/mm/yyyy') AS LIC_DATE, " +
								"  LEGAL_HEIR_ADD1, " +
								"  LEGAL_HEIR_ADD2, " +
								"  LEGAL_HEIR_ADD3, " +
								"  LEGAL_HEIR_PIN, " +
								"  LEGAL_PERCENT " +
								"FROM HRM_GPF_LEGAL_DETAILS " +
								"WHERE GPF_NO= ?"
;
						int gpf_no=0,e_no=0,legal_id=0,legal_pin=0;
						String legal_name="",legal_rel="",legal_add1="",legal_add2="",legal_add3="",legal_date="";
						
						pss2=con.prepareStatement(s1);
						pss2.setInt(1, gpfNo);
						rss2=pss2.executeQuery();
						while(rss2.next())
						{
							xml+="<gpf_no>"+rss2.getInt("GPF_NO")+"</gpf_no>";						
							xml+="<e_no>"+rss2.getInt("EMPLOYEE_ID")+"</e_no>";
							xml+="<legal_id>"+rss2.getInt("LEGAL_HIER_ID")+"</legal_id>";
							xml+="<legal_pin>"+rss2.getInt("LEGAL_HEIR_PIN")+"</legal_pin>";
							
							xml+="<legal_name>"+rss2.getString("LEGAL_HEIR_NAME")+"</legal_name>";
							
							
							xml+="<legal_rel>"+rss2.getString("RELATIONSHIP")+"</legal_rel>";
							xml+="<legal_add1>"+rss2.getString("LEGAL_HEIR_ADD1")+"</legal_add1>";
							xml+="<legal_add2>"+rss2.getString("LEGAL_HEIR_ADD2")+"</legal_add2>";
							xml+="<legal_add3>"+rss2.getString("LEGAL_HEIR_ADD3")+"</legal_add3>";
							xml+="<legal_date>"+rss2.getString("LIC_DATE")+"</legal_date>";
							xml+="<LEGAL_PERCENT>"+rss2.getString("LEGAL_PERCENT")+"</LEGAL_PERCENT>";
							
						}
						
						String sqqls="select copy_to from hrm_gpf_legal_details where gpf_no=?";
						try
						{
							int countss=0;
							PreparedStatement ppss = con.prepareStatement(sqqls);
							ppss.setInt(1, gpfNo);
							ResultSet rrss = ppss.executeQuery();
							while(rrss.next())
							{
								countss++;
								xml+="<copy_to>"+rrss.getString("copy_to")+"</copy_to>";
							}
							xml+="<countss>"+countss+"</countss>";
						}
						catch(Exception e)
						{
							
						}
						
						
						try
						{
							String sssqls="select count(*) as TOTAL from hrm_gpf_legal_details where gpf_no=?";
							int cccc=0;
							PreparedStatement ppsss = con.prepareStatement(sssqls);
							ppsss.setInt(1, gpfNo);
							ResultSet rrsss = ppsss.executeQuery();
							while(rrsss.next())
							{
								cccc++;
								xml+="<TOTAL>"+rrsss.getString("TOTAL")+"</TOTAL>";
							}
							xml+="<cccount>"+cccc+"</cccount>";
						}catch(Exception e)
						{
							
						}
						
					}
				}catch(Exception e)
				{
					
				}
				
			}
			
	     
	     } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     System.out.println("Count====="+count+ "      hi this");
	     
	    if(count!=0)
	    {
	    	System.out.println("With in Count====="+count);
     			xml=xml+"<flag>success</flag>";     			
	               	            	
	            System.out.println("hi...2");	
     		}else{
     			xml=xml+"<flag>failure</flag>";
     			System.out.println("hi...3");	
     			
     		}
     	}
	  
	  
	 else if(command.equalsIgnoreCase("report"))
	 {
		 int count=0;
		 String off_name=null;
		 xml="<response><command>taken_office</command>";
	     xml=xml+"<status>taken_offices</status>";
	     String intrest_upto1=request.getParameter("intrest_upto").trim();
	     try {
				ps=con.prepareStatement("(SELECT * " +
						"FROM " +
						"  (SELECT office_id AS off_id FROM HRM_EMP_CURRENT_POSTING WHERE employee_id=? " +
						"  )a " +
						"LEFT OUTER JOIN " +
						"  (SELECT office_id,office_name FROM COM_MST_OFFICES " +
						"  ) b " +
						"ON a.off_id=b.office_id " +
						")" 
	);

				ps.setString(1,emppp_id);
				rs=ps.executeQuery();
				while(rs.next())
				{
					count++;
					System.out.println("count================="+count+" ******************");
					xml+="<COUNT>"+count+"</COUNT>";
					off_name=rs.getString("office_name");
					
					
				}
				
		     
		     } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     
		   
	     
	     
	     
	     
	     
		 Map map=new HashMap();
		 
		 
		 reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/TWAD_PROJECT.jasper"));	
		 String rep=getServletContext().getRealPath("/WEB-INF/ReportSrc/");
	 		  rep=rep+"/";
	 		  
	 		 map.put("login_off",off_name);
   	 	     map.put("month_year",intrest_upto1);
   	 	     
   	 	 if (!reportFile.exists())
	      {
	          System.out.println("does not exsist");
	          throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	      }
	      try {
			System.out.println(JRLoader.loadObject(reportFile.getPath()));
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     JasperReport jasperReport = null;
		try {
			jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	 	      System.out.println("Report File:"+reportFile); 
 	  
 		 //  System.out.println("emp_id:"+emp_id);
 	 	     
 	 	  
 	 	    
 	 	    
 	 	      try {
				jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	 	      
 	 	    byte buf[] = null;
			try {
				buf = JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           response.setContentType("application/pdf");
	           response.setContentLength(buf.length);
	           response.setHeader ("Content-Disposition", "attachment;filename=\"leave_sanction_proceed_details.pdf\"");
	           OutputStream out1 = response.getOutputStream();
	           out1.write(buf, 0, buf.length);
	           out1.close();
	           
	           if(count!=0)
			    {
		     			xml=xml+"<flag>success</flag>";     			
			               	            	
			            System.out.println("hi...2...success");	
		     		}else{
		     			xml=xml+"<flag>failure</flag>";
		     			System.out.println("hi...3...failure");	
		     			
		     		}
		 
	 }
	  
	 else if(command.equalsIgnoreCase("Delete"))
	 {
		 xml="<response><command>delete</command>";
	     xml=xml+"<status>delete</status>";
		 int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
		 String sql="DELETE FROM HRM_GPF_FINAL_SETTLEMENT_PROC WHERE GPF_NO = ?";
		 String sql1="DELETE FROM HRM_GPF_LEGAL_DETAILS WHERE GPF_NO = ?";
		
		 try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, gpfNo);
			ps.executeUpdate();
			 boolean check=true;
			 if(check==true)
			 {
				 pss=con.prepareStatement(sql1);
				 pss.setInt(1, gpfNo);
				 pss.executeUpdate();
				 xml+="<flag>success</flag>";
			 }
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			xml+="<flag>failure</flag>";
			e.printStackTrace();
		}
		 
		 

	 }
	  
	  
	  xml=xml+"</response>";
	    
	  out.write(xml);
	  out.flush();
	  out.close();
	  System.out.println("XML===="+xml);
	    
	 }
	  
	     
	  	
}
