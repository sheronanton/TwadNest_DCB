package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;


/**
 * Servlet implementation class GPF_Journal_Reference_Details
 */
public class GPF_Journal_Reference_Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Journal_Reference_Details() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
  	  System.out.println("welcome to GPF Journal Details Update ");
  	  
	        try{
	        	LoadDriver driver=new LoadDriver();
	            con=driver.getConnection();

	        	
	        }
	        catch(Exception e){
	        	System.out.println("Exception in opening connection :"+e); 
	        }
	        ResultSet rs=null,rs1=null,rs2=null;
	        CallableStatement cs=null;
	        PreparedStatement ps=null,ps1=null,ps2=null;
	        String xml="",sql="";
	        DecimalFormat df=new DecimalFormat("#0.00");
	      
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
	                    //    System.out.println(xml);
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
		                   
		          
		            xml="<response><command>get</command>";
		            try{
		                   
		            	int unitId=Integer.parseInt(request.getParameter("acc_unit").trim());
		            	int officeId=Integer.parseInt(request.getParameter("office_id").trim());
		            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
		            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
		            	int acHeadCode=Integer.parseInt(request.getParameter("acheadcode").trim());
		            	
		            	
		                    ps=con.prepareStatement("SELECT VOUCHER_NO,TO_CHAR(VOUCHER_DATE,'DD/MM/YYYY') as vouchardate,REMARKS  FROM FAS_JOURNAL_MASTER WHERE ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and JOURNAL_TYPE_CODE=53 and " +
		                    		"VOUCHER_NO in (select VOUCHER_NO from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_HEAD_CODE=?)");
		                    ps.setInt(1,unitId);
		                    ps.setInt(2,officeId);
		                    ps.setInt(3,cashYear);
		                    ps.setInt(4,cashMonth);
		                    ps.setInt(5,unitId);
		                    ps.setInt(6,officeId);
		                    ps.setInt(7,cashYear);
		                    ps.setInt(8,cashMonth);
		                    ps.setInt(9,acHeadCode);
                 		               		                   		                    
		                    rs=ps.executeQuery();
		                    xml=xml+"<flag>success</flag>";
		                    xml=xml+"<unit>"+unitId+"</unit>";
		                    xml=xml+"<officeid>"+officeId+"</officeid>";
		                    xml=xml+"<cashyear>"+cashYear+"</cashyear>";
		                    xml=xml+"<cashmonth>"+cashMonth+"</cashmonth>";
		                    xml=xml+"<acheadcode>"+acHeadCode+"</acheadcode>";
	                        while(rs.next()){
	                        	
	                        	int voucharNo=rs.getInt("VOUCHER_NO");
	                        	
	                            xml=xml+"<voucharno>"+voucharNo+"</voucharno>";  
	                           
	                            xml=xml+"<vdate>"+rs.getString("vouchardate")+"</vdate>";
	                          
	                            xml=xml+"<remarks>"+rs.getString("REMARKS")+"</remarks>";
	                           
	                          
	                           	                    
			                    
			                    ps1=con.prepareStatement("select sum(amount) as amt from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?"); 
	                            
	                            ps1.setInt(1,unitId);
			                    ps1.setInt(2,officeId);
			                    ps1.setInt(3,cashYear);
			                    ps1.setInt(4,cashMonth);
			                    ps1.setInt(5,voucharNo);
			                    rs1=ps1.executeQuery();
			                    if(rs1.next())
			                    {
			                    xml=xml+"<totalamount>"+df.format(rs1.getInt("amt"))+"</totalamount>"; 
			                    }else{
			                    xml=xml+"<totalamount>0.00</totalamount>"; 
			                    } 
			                    
			                    ps2=con.prepareStatement("select sum(amount) as amount from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACCOUNT_HEAD_CODE=?"); 
	                            
	                            ps2.setInt(1,unitId);
			                    ps2.setInt(2,officeId);
			                    ps2.setInt(3,cashYear);
			                    ps2.setInt(4,cashMonth);
			                    ps2.setInt(5,voucharNo);
			                    ps2.setInt(6,acHeadCode);
			                    rs2=ps2.executeQuery();
			                    if(rs2.next())
			                    {
			                    	
			                    xml=xml+"<trnamount>"+df.format(rs2.getInt("amount"))+"</trnamount>"; 
			                    
			                    }else{
			                    xml=xml+"<trnamount>0.00</trnamount>"; 
			                    } 
			                    
			                    
			                    
	                        }
                    
	                    
	                    }
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	            System.out.println(xml);
	        }
	            
	            if(command.equalsIgnoreCase("update")) {
	                   
			          
		            xml="<response><command>update</command>";
		            try{
		                   
		            	int unitId=Integer.parseInt(request.getParameter("unitid").trim());
		            	int officeId=Integer.parseInt(request.getParameter("officeid").trim());
		            	int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
		            	int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
		            	int acHeadCode=Integer.parseInt(request.getParameter("acheadcode").trim());
		            	int voucharNo=Integer.parseInt(request.getParameter("vno").trim());	
		            	
		            String voucharDate=request.getParameter("vdate");
		           float amount=Float.parseFloat(request.getParameter("trnamount").trim());	
		            	
		                    ps=con.prepareStatement("update HRM_GPF_JRNL_ADJ_TRN set VOUCHER_NO=?,VOUCHER_DATE=to_date(?,'dd-mm-yyyy') where ACCOUNTING_UNIT_ID=? and JRNL_TOBE_MONTH=? and JRNL_TOBE_YEAR=? and AC_HEAD_CODE=? and ACCOUNTING_FOR_OFFICE_ID=? and DIFFERENCE_VALUE=?");
		                    ps.setInt(1,voucharNo);
		                    ps.setString(2,voucharDate);
		                   
		                    ps.setInt(3,unitId);
		                    ps.setInt(4,cashMonth);
		                    ps.setInt(5,cashYear);
		                    ps.setInt(6,acHeadCode);
		                    ps.setInt(7,officeId);    
		                    ps.setFloat(8,amount);    
		                    rs=ps.executeQuery();
		                    
		                    xml=xml+"<flag>success</flag>";   
		            }catch(Exception e){
		            	System.out.println(e);
		            }
		            xml=xml+"</response>";
	            }
		                    
	            
	            
	            
	            
	            out.print(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
