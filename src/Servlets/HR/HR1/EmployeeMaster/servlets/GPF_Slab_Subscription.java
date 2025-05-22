package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class GPF_Slab_Subscription
 */
public class GPF_Slab_Subscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Slab_Subscription() {
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
	                   
	                    ps=con.prepareStatement("select GPF_Slab_Id ,Salary_From,Salary_To,Minumum_Subscription,to_char(Date_effective_from,'dd/mm/yyyy') from HRM_GPF_MST_SLAB");
	                    rs=ps.executeQuery();
	                    
	                    xml=xml+"<flag>success</flag>";
	                        
	                        while(rs.next()){
	                            xml=xml+"<slabid>"+rs.getInt("GPF_Slab_Id")+"</slabid>";                
	                            xml=xml+"<salaryfrom>"+rs.getInt("Salary_From")+"</salaryfrom>";
	                            xml=xml+"<salaryto>"+rs.getInt("Salary_To")+"</salaryto>";
	                            xml=xml+"<subamount>"+rs.getInt("Minumum_Subscription")+"</subamount>";
	                            xml=xml+"<date>"+rs.getString(5)+"</date>";
	                        }
	                    
	                    }
	                 
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	          
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	            
	           
	          
	          int slabId =Integer.parseInt(request.getParameter("slabid"));
	          int  salaryFrom= Integer.parseInt(request.getParameter("salaryfrom"));
	          int  salaryTo= Integer.parseInt(request.getParameter("salaryto"));
	          int subAmount=Integer.parseInt(request.getParameter("subamount"));
	          String date=request.getParameter("date"); 
	            xml="<response><command>Add</command>";
	            try{
	            System.out.println("this is add"+slabId+" == "+salaryFrom);
	                ps=con.prepareStatement("select GPF_Slab_Id,Salary_From from HRM_GPF_MST_SLAB where GPF_Slab_Id=? ");
	                ps.setInt(1, slabId);
	                rs=ps.executeQuery();
	                int flag=0;
	               if(rs.next()) {
	                   // System.out.println("this is add"+rs.getString("type_with")+" == "+w_id);
	                   
	                        xml=xml+"<flag>same</flag>";
	                        flag=1;
	                       
	                }
	                System.out.println("flag"+flag);
	                if(flag==0)
	                {
	                        ps=con.prepareStatement("insert into HRM_GPF_MST_SLAB(GPF_Slab_Id,Salary_From,Salary_To,Minumum_Subscription,Date_effective_from,Process_Flow_Status_Id,Updated_date,Updated_by_User_id) values(?,?,?,?,?,?,?,?)");
	                        ps.setInt(1,slabId);
	                        ps.setInt(2,salaryFrom);
	                        ps.setInt(3, salaryTo);
	                        ps.setInt(4, subAmount);
	                        ps.setString(5, date);
	                        ps.setString(6, "CR");
	                        ps.setTimestamp(7, ts);
	                        ps.setString(8,updatedby);
	                        ps.executeUpdate();
	                        xml=xml+"<flag>success</flag>";
	                        xml=xml+"<slabid>"+slabId+"</slabid>";                
                            xml=xml+"<salaryfrom>"+salaryFrom+"</salaryfrom>";
                            xml=xml+"<salaryto>"+salaryTo+"</salaryto>";
                            xml=xml+"<subamount>"+subAmount+"</subamount>";
                            xml=xml+"<date>"+date+"</date>";
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
	            
		          int slabId =Integer.parseInt(request.getParameter("slabid"));
		          int  salaryFrom= Integer.parseInt(request.getParameter("salaryfrom"));
		          int  salaryTo= Integer.parseInt(request.getParameter("salaryto"));
		          int subAmount=Integer.parseInt(request.getParameter("subamount"));
		          String date=request.getParameter("date"); 
	           
	            xml="<response><command>Update</command>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_MST_SLAB set Salary_From=?,Salary_To=?,Minumum_Subscription=?,Date_effective_from=?,Process_Flow_Status_Id=?,Updated_date=?,Updated_by_User_id=?  where GPF_Slab_Id=?");
	                ps.setInt(1,salaryFrom);
	                ps.setInt(2,salaryTo);
	                ps.setInt(3,subAmount);
	                ps.setString(4, date);
	                ps.setString(5, "MD");
	                ps.setTimestamp(6, ts);
	                ps.setString(7,updatedby);
	                ps.setInt(8,slabId);
	                ps.executeUpdate();
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<slabid>"+slabId+"</slabid>";                
                    xml=xml+"<salaryfrom>"+salaryFrom+"</salaryfrom>";
                    xml=xml+"<salaryto>"+salaryTo+"</salaryto>";
                    xml=xml+"<subamount>"+subAmount+"</subamount>";
                    xml=xml+"<date>"+date+"</date>";
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Delete")) {
	            System.out.println("delete");
	            int slabId =Integer.parseInt(request.getParameter("slabid"));
	          
	            
	            xml="<response><command>Delete</command>";
	          try{
	                ps=con.prepareStatement("delete from HRM_GPF_MST_SLAB where GPF_Slab_Id=?");
	                ps.setInt(1,slabId);
	                       
	                ps.executeUpdate();
	                
	              ps=con.prepareStatement("select GPF_Slab_Id ,Salary_From,Salary_To,Minumum_Subscription,to_char(Date_effective_from,'dd/mm/yyyy') from HRM_GPF_MST_SLAB");
	              rs=ps.executeQuery();
	              
	              xml=xml+"<flag>success</flag>";
	                  
	                  while(rs.next()){
	                	  xml=xml+"<slabid>"+rs.getInt("GPF_Slab_Id")+"</slabid>";                
                          xml=xml+"<salaryfrom>"+rs.getInt("Salary_From")+"</salaryfrom>";
                          xml=xml+"<salaryto>"+rs.getInt("Salary_To")+"</salaryto>";
                          xml=xml+"<subamount>"+rs.getInt("Minumum_Subscription")+"</subamount>";
                          xml=xml+"<date>"+rs.getString(5)+"</date>";
	                  }
	              
	          }
	            catch(SQLException e) {
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
