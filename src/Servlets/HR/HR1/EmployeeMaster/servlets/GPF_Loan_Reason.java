package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

/**
 * Servlet implementation class GPF_Loan_Reason
 */
public class GPF_Loan_Reason extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	    private static final String DOC_TYPE = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Loan_Reason() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
	        try {
	                    
	        	LoadDriver driver=new LoadDriver();
	            con=driver.getConnection();

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
	            
	            xml="<response><command>get</command>";
	            try{
	                   
	                    ps=con.prepareStatement("select Withdrawal_Reason_Id,trim(Withdrawal_Reason_Desc) as reason,trim(Applicable_Rule) as rule,Withdrawal_types from HRM_GPF_MST_WITHDRAWAL_REASONS");
	                    rs=ps.executeQuery();
	                    
	                    xml=xml+"<flag>success</flag>";
	                        
	                        while(rs.next()){
	                            xml=xml+"<reasonid>"+rs.getString("Withdrawal_Reason_Id")+"</reasonid>";                
	                            xml=xml+"<loanreason>"+rs.getString("reason")+"</loanreason>";
	                            xml=xml+"<rule>"+rs.getString("rule")+"</rule>";
	                            xml=xml+"<withdrawtype>"+rs.getString("Withdrawal_types")+"</withdrawtype>";
	                        }
	                    
	                    }
	                 
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	          
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	            
	           
	            String reasonId,reason,rule;
	            reasonId = request.getParameter("reasonid");
	            reason= request.getParameter("loanreason");
	            rule=request.getParameter("rule");
	            
	            String withdrawType=request.getParameter("withdrawtype");
	            
	           // types[]=withdrawType.split(",");
	            
	            
	            xml="<response><command>Add</command>";
	            try{
	            System.out.println("this is add"+reasonId+" == "+reason);
	                ps=con.prepareStatement("select * from HRM_GPF_MST_WITHDRAWAL_REASONS where Withdrawal_Reason_Id=?");
	                ps.setString(1,reasonId);
	                rs=ps.executeQuery();
	                int flag=0;
	               if(rs.next()) {
	                  //  System.out.println("this is add"+rs.getString("type_with")+" == "+reasonId);
	                   
	                        xml=xml+"<flag>same</flag>";
	                        flag=1;
	                   
	                }
	                System.out.println("flag"+flag);
	                if(flag==0)
	                {
	                        ps=con.prepareStatement("insert into HRM_GPF_MST_WITHDRAWAL_REASONS(Withdrawal_Reason_Id,Withdrawal_Reason_Desc,Applicable_Rule,Process_Flow_Status_Id,Updated_by_User_id,Updated_Date,Withdrawal_types) values(?,?,?,?,?,?,?)");
	                        ps.setString(1,reasonId);
	                        ps.setString(2,reason);
	                        ps.setString(3,rule);
	                        ps.setString(4,"CR");
	                        ps.setString(5,updatedby);
	                        ps.setTimestamp(6,ts);
	                        ps.setString(7,withdrawType);
	                        ps.executeUpdate();
	                        xml=xml+"<flag>success</flag>";
	                        xml=xml+"<reasonid>"+reasonId+"</reasonid>";                
                            xml=xml+"<loanreason>"+reason+"</loanreason>";
                            xml=xml+"<rule>"+rule+"</rule>";
                            xml=xml+"<withdrawtype>"+withdrawType+"</withdrawtype>";
	                }
	                             
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Update")) {
	            System.out.println("update");
	            
	            String reasonId,reason,rule;
	            reasonId = request.getParameter("reasonid");
	            reason= request.getParameter("loanreason");
	            rule=request.getParameter("rule");
	            String withdrawType=request.getParameter("withdrawtype");
	            xml="<response><command>Update</command>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_MST_WITHDRAWAL_REASONS set Withdrawal_Reason_Desc=?,Applicable_Rule=?,Process_Flow_Status_Id=?,Updated_by_User_id=?,Updated_Date=?,Withdrawal_types=? where Withdrawal_Reason_Id=?");
	                ps.setString(1,reason);
	                ps.setString(2,rule);
	                ps.setString(3,"MD");
	                ps.setString(4,updatedby);
	                ps.setTimestamp(5,ts);
	                ps.setString(6,withdrawType); 
	                ps.setString(7,reasonId);
	                ps.executeUpdate();
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<reasonid>"+reasonId+"</reasonid>";                
                    xml=xml+"<loanreason>"+reason+"</loanreason>";
                    xml=xml+"<rule>"+rule+"</rule>";
                    xml=xml+"<withdrawtype>"+withdrawType+"</withdrawtype>";
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Delete")) {
	            System.out.println("delete");
	            String reasonId,reason,rule;
	            reasonId = request.getParameter("reasonid");
	            
	            
	              xml="<response><command>Delete</command>";
	          try{
	                ps=con.prepareStatement("delete from HRM_GPF_MST_WITHDRAWAL_REASONS where Withdrawal_Reason_Id=?");
	                ps.setString(1,reasonId);
	                       
	                ps.executeUpdate();
	                
	              ps=con.prepareStatement("select Withdrawal_Reason_Id,Withdrawal_Reason_Desc,Applicable_Rule,Withdrawal_types from HRM_GPF_MST_WITHDRAWAL_REASONS");
	              rs=ps.executeQuery();
	              
	              xml=xml+"<flag>success</flag>";
	                  
	                  while(rs.next()){
	                	  xml=xml+"<reasonid>"+rs.getString("Withdrawal_Reason_Id")+"</reasonid>";                
                          xml=xml+"<loanreason>"+rs.getString("Withdrawal_Reason_Desc")+"</loanreason>";
                          xml=xml+"<rule>"+rs.getString("Applicable_Rule")+"</rule>";
                          xml=xml+"<withdrawtype>"+rs.getString("Withdrawal_types")+"</withdrawtype>";
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
	        System.out.println(xml);
	        out.println(xml);
	        out.close();
	        

	        }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
