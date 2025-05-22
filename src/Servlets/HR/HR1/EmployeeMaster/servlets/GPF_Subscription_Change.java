package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

public class GPF_Subscription_Change extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;
    Connection connection = null;
	Statement statement = null;
	ResultSet results3 = null;
	ResultSet results4 = null;
	PreparedStatement ps = null, lstatement, Pstatement, empstmt;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, IOException {
    	 Connection con=null;
	        try {
	                    
	        	LoadDriver driver=new LoadDriver();
	            connection=driver.getConnection();

	                }
	                catch(Exception e)
	                    {
	                       System.out.println("Exception in openeing connection :"+e); 
	                       //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");   
	                       
	                    }
	        ResultSet rs=null,rs1=null,rs2=null;
	        CallableStatement cs=null;
	        PreparedStatement ps=null,ps1=null,ps2=null;
	        String xml="";
	        DecimalFormat df=new DecimalFormat("#0.00");
	        
	        int auto_no=0;
	        
	        
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        response.setHeader("Cache-Control","no-cache");  
	        HttpSession session=request.getSession(false);
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
	                    //System.out.println(session);

	        }catch(Exception e)
	        {
	                //System.out.println("Redirect Error :"+e);
	        }
	        System.out.println("java");
	        String command;
	        command = request.getParameter("command");
	        
	        session =request.getSession(false);
	        String updatedby=(String)session.getAttribute("UserId");
	        long l=System.currentTimeMillis();
	        java.sql.Timestamp ts=new java.sql.Timestamp(l);
	            System.out.println("got");
	         if(command.equalsIgnoreCase("Get")) {
	            int gpf_no;
	            int design=0;
	            gpf_no = Integer.parseInt(request.getParameter("gpf_no"));
	            xml="<response><command>get</command>";
	            xml=xml+"<status>get</status>";
	            try{
	                   
	                    ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE GPF_NO=?");
	                    ps.setInt(1,gpf_no);
	                    rs=ps.executeQuery();
	                    
	                    if(!rs.next()) 
	                    {
	                        xml=xml+"<flag>failure</flag>";
	                    }
	                    else {
	                        
	                        ps=con.prepareStatement("SELECT e.EMPLOYEE_NAME,to_char(e.DATE_OF_BIRTH,'dd/mm/yyyy'),e.GPF_NO,f.DESIGNATION,e.EMPLOYEE_ID FROM HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING d,HRM_MST_DESIGNATIONS f WHERE e.EMPLOYEE_ID=d.EMPLOYEE_ID AND d.DESIGNATION_ID=f.DESIGNATION_ID AND e.GPF_NO=?");
	                        ps.setInt(1,gpf_no);
	                        rs=ps.executeQuery();
	                        if(rs.next()) {
	                          
	                            xml=xml+"<emp_name>"+rs.getString(1)+" </emp_name>";
	                            xml=xml+"<date_of_birth>"+rs.getString(2)+" </date_of_birth>";
	                            xml=xml+"<gpf_no>"+rs.getInt(3)+" </gpf_no>";
	                            xml=xml+"<designation>"+rs.getString(4)+" </designation>";
	                            xml=xml+"<emp_id>"+rs.getInt(5)+" </emp_id>";
	                            }
	                        xml=xml+"<flag>success</flag>";
	                        ps=con.prepareStatement("select process_flow_status_id from hrm_gpf_subscription_change where gpf_no=?");
	                        ps.setInt(1,gpf_no);
	                        rs=ps.executeQuery();
	                        if(rs.next())
                                {
	                           
                                    ps=con.prepareStatement("select request_sino,to_char(request_date,'dd/mm/yyyy'),subscription_amt,effective_year,effective_month,process_flow_status_id,remarks from HRM_GPF_SUBSCRIPTION_CHANGE where GPF_NO=? order by request_date desc");
                                    ps.setInt(1,gpf_no);
                                    rs=ps.executeQuery();
	                       
	                        
	                        while(rs.next()){
	                            xml=xml+"<sino>"+rs.getInt(1)+"</sino>";                
	                            xml=xml+"<date>"+rs.getString(2)+"</date>";
	                            xml=xml+"<amount>"+df.format(rs.getDouble(3))+"</amount>";
	                            xml=xml+"<eff_year>"+rs.getInt(4)+"</eff_year>";
	                            xml=xml+"<eff_month>"+rs.getInt(5)+"</eff_month>";
	                            xml=xml+"<status_id>"+rs.getString(6)+"</status_id>";
	                            xml=xml+"<remarks>"+rs.getString(7)+"</remarks>";
	                            
	                        }
                                }
	                    }
	                    }
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	           // System.out.println(xml);
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	            
	        
	            int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
	            String date_trans=request.getParameter("date_trans");
	            double amount=Double.parseDouble(request.getParameter("amount"));
	            int eff_month=Integer.parseInt(request.getParameter("eff_month"));  
	            int eff_year=Integer.parseInt(request.getParameter("eff_year"));  
	            String fin_year=request.getParameter("fin_year");
	            int Acc_unit_code=Integer.parseInt(request.getParameter("Acc_unit_code"));  
	            
	            
	            String status_id="CR";
	            String remarks;
	            remarks = request.getParameter("remarks");
	          System.out.println("month----------------"+eff_month);
	              
	           
	            xml="<response><command>load</command>";
	            xml=xml+"<status>add</status>";
	            try{
	            System.out.println("this is add");
	            
	            ps=con.prepareStatement("select max(request_sino) from HRM_GPF_SUBSCRIPTION_CHANGE ");
                 rs=ps.executeQuery();
                 
                 while (rs.next()) {

         		 auto_no = (rs.getInt(1)) + 1;

         		}
         			
	            
	                ps=con.prepareStatement("insert into HRM_GPF_SUBSCRIPTION_CHANGE(gpf_no,request_date,subscription_amt,effective_year,effective_month,process_flow_status_id,updated_by,updated_date,remarks,FIN_YEAR,ACCOUNTING_UNIT_ID,request_sino) values(?,to_date(?,'dd-MM-yyyy'),?,?,?,?,?,?,?,?,?,?)");
	                ps.setInt(1,gpf_no);
	                ps.setString(2,date_trans);
	                ps.setDouble(3,amount);
	                ps.setInt(4,eff_year);
	                ps.setInt(5,eff_month);
	                ps.setString(6,status_id);
	                ps.setString(7,updatedby);
	                ps.setTimestamp(8,ts);
	                ps.setString(9,remarks);
	                ps.setString(10,fin_year);
	                ps.setInt(11,Acc_unit_code);
	               ps.setInt(12,auto_no);
	                ps.executeUpdate();
	                System.out.println("came here");
                        ps.close();
	               
	                xml=xml+"<flag>success</flag>";
	                
	              
	              
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Update")) {
	            System.out.println("update");
	            
	                int sino=Integer.parseInt(request.getParameter("no").trim());    
	         
	                int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
	           
	                String date_trans=request.getParameter("date_trans");
	        
	                double amount=Double.parseDouble(request.getParameter("amount"));
	         
	                int eff_month=Integer.parseInt(request.getParameter("eff_month"));  
	          
	                int eff_year=Integer.parseInt(request.getParameter("eff_year"));  
	                String status_id="ED";
	                String remarks;
	                remarks = request.getParameter("remarks");
	                
	                String fin_year=request.getParameter("fin_year");
		            int Acc_unit_code=Integer.parseInt(request.getParameter("Acc_unit_code"));  
	            System.out.println("update1");
	           
	            xml="<response><command>load</command>";
	            xml=xml+"<status>update</status>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_SUBSCRIPTION_CHANGE set request_date=to_date(?,'dd-MM-yyyy'),subscription_amt=?,effective_year=?,effective_month=?,process_flow_status_id=?,remarks=?,updated_by=?,updated_date=? where gpf_no=? and request_sino=?");
	                                 
	                ps.setString(1,date_trans);
	                ps.setDouble(2,amount);
	                ps.setInt(3,eff_year);
	                ps.setInt(4,eff_month);
	                ps.setString(5,status_id);
	                ps.setString(6,remarks);
	                ps.setString(7,updatedby);
	                ps.setTimestamp(8,ts);
	               ps.setInt(9,gpf_no);
	                ps.setInt(10,sino);
	                ps.setString(11,fin_year);
	                ps.setInt(12,Acc_unit_code);
	                ps.executeUpdate();
	                ps.close();    
	                System.out.println("came here");  
	                ps=con.prepareStatement("select request_sino,to_char(request_date,'dd/mm/yyyy'),subscription_amt,effective_year,effective_month,process_flow_status_id,remarks from HRM_GPF_SUBSCRIPTION_CHANGE where GPF_NO=?");
	                ps.setInt(1,gpf_no);
	                rs=ps.executeQuery();
	                xml=xml+"<flag>success</flag>";
	                
	                while(rs.next()){
	                 xml=xml+"<sino>"+rs.getInt(1)+"</sino>";                
	                 xml=xml+"<date>"+rs.getString(2)+"</date>";
	                 xml=xml+"<amount>"+df.format(rs.getDouble(3))+"</amount>";
	                 xml=xml+"<eff_year>"+rs.getInt(4)+"</eff_year>";
	                 xml=xml+"<eff_month>"+rs.getInt(5)+"</eff_month>";
	                 xml=xml+"<status_id>"+rs.getString(6)+"</status_id>";
	                 xml=xml+"<remarks>"+rs.getString(7)+"</remarks>";
	                 xml=xml+"<fin_year>"+rs.getString(8)+"</fin_year>";
	                 xml=xml+"<Acc_unit_code>"+rs.getString(9)+"</Acc_unit_code>";
	                         }
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	            else if(command.equalsIgnoreCase("Validate")) {
	                System.out.println("validate");
	                
	                    int sino=Integer.parseInt(request.getParameter("no").trim());            
	                    int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
	                    String date_trans=request.getParameter("date_trans");
	                    double amount=Double.parseDouble(request.getParameter("amount"));
	                    int eff_month=Integer.parseInt(request.getParameter("eff_month"));  
	                    int eff_year=Integer.parseInt(request.getParameter("eff_year"));  
	                    String status_id="FR";
	                    String remarks;
	                    remarks = request.getParameter("remarks");
	                    
	                    String fin_year=request.getParameter("fin_year");
	    	            int Acc_unit_code=Integer.parseInt(request.getParameter("Acc_unit_code"));  
	                System.out.println("validate1");
	               
	                xml="<response><command>load</command>";
	                xml=xml+"<status>validate</status>";
	                try{
	                    ps=con.prepareStatement("update HRM_GPF_SUBSCRIPTION_CHANGE set request_date=to_date(?,'dd-MM-yyyy'),subscription_amt=?,effective_year=?,effective_month=?,process_flow_status_id=?,remarks=?,updated_by=?,updated_date=? where gpf_no=? and request_sino=?");
	                                     
	                    ps.setString(1,date_trans);
	                    ps.setDouble(2,amount);
	                    ps.setInt(3,eff_year);
	                    ps.setInt(4,eff_month);
	                    ps.setString(5,status_id);
	                    ps.setString(6,remarks);
	                    ps.setString(7,updatedby);
	                    ps.setTimestamp(8,ts);
	                    ps.setInt(9,gpf_no);
	                    ps.setInt(10,sino);
	                    ps.setString(11,fin_year);
		                ps.setInt(12,Acc_unit_code);
	                    ps.executeUpdate();
	                    ps.close();    
                            
	                    ps=con.prepareStatement("select request_sino,to_char(request_date,'dd/mm/yyyy'),subscription_amt,effective_year,effective_month,process_flow_status_id,remarks from HRM_GPF_SUBSCRIPTION_CHANGE where GPF_NO=?");
	                    ps.setInt(1,gpf_no);
	                    rs=ps.executeQuery();
	                    xml=xml+"<flag>success</flag>";
	                    
	                    while(rs.next()){
	                     xml=xml+"<sino>"+rs.getInt(1)+"</sino>";                
	                     xml=xml+"<date>"+rs.getString(2)+"</date>";
	                     xml=xml+"<amount>"+df.format(rs.getDouble(3))+"</amount>";
	                     xml=xml+"<eff_year>"+rs.getInt(4)+"</eff_year>";
	                     xml=xml+"<eff_month>"+rs.getInt(5)+"</eff_month>";
	                     xml=xml+"<status_id>"+rs.getString(6)+"</status_id>";
	                     xml=xml+"<remarks>"+rs.getString(7)+"</remarks>";
	                     
	                     xml=xml+"<fin_year>"+rs.getString(8)+"</fin_year>";
		                 xml=xml+"<Acc_unit_code>"+rs.getString(9)+"</Acc_unit_code>";
	                             }
	                }
	                catch(SQLException e) {
	                    xml=xml+"<flag>failure</flag>";
	                    e.printStackTrace();
	                }
	                xml=xml+"</response>";
	            }
	        else if(command.equalsIgnoreCase("unit")) {
	            String unit_name;
	            System.out.println("unit");
	              int office_id=0;
	            unit_name = request.getParameter("unit_name");
	        xml="<response><command>unit</command>";
	            try{
	                
	                ps=con.prepareStatement("select accounting_unit_id from fas_mst_acct_units where accounting_unit_name=?" );
	                ps.setString(1,unit_name);
	                rs=ps.executeQuery();
	                xml=xml+"<flag>success</flag>";
	                if(rs.next()) 
	                     {
	                          xml=xml+"<unit_id>"+ rs.getInt(1) +"</unit_id>";
	                     
	                      }
	               
	                
	                
	            }
	            
	            
	            catch(SQLException e)
	            {
	                xml=xml+"<flag>failure</flag>";
	           e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	       
	         
	         //////////////////////////////////////////////////
	        
	         
	         
	       
	 	
	         System.out.println(xml);
	 		out.write(xml);
	        

        }

        
        }
