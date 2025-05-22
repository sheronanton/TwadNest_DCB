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
 * Servlet implementation class GPF_Trans_Types
 */
public class GPF_Trans_Types extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CONTENT_TYPE = "application/xml; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Trans_Types() {
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
	        System.out.println("command"+command);
	         if(command.equalsIgnoreCase("Get")) {
	            xml="<response><command>get</command>";
	            try
	            {
	                System.out.println("try");
           	               	                   	                                      
	         ps=con.prepareStatement("select gpf_trans_type_id,gpf_trans_type_desc,cr_achead_code,db_achead_code,fund_nature from HRM_GPF_TRANS_TYPES");
	                   
	                    rs=ps.executeQuery();  
	                    xml=xml+"<flag>success</flag>";
	                    
	                    while(rs.next()){
	                        xml=xml+"<typeid>"+rs.getString(1)+"</typeid>";                
	                        xml=xml+"<typedesc>"+rs.getString(2)+"</typedesc>";
	                        xml=xml+"<crcode>"+rs.getInt(3)+"</crcode>";
	                       xml=xml+"<dbcode>"+rs.getInt(4)+"</dbcode>";
	                       
	                        xml=xml+"<fundnature>"+rs.getString(5)+"</fundnature>";
	                      
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
	        	
	            String typeId=request.getParameter("typeid");
	            String typeDesc=request.getParameter("typedesc");
        	            
	            int crCode=Integer.parseInt(request.getParameter("crcode").trim()); 
	            int dbCode=Integer.parseInt(request.getParameter("dbcode").trim());
	           String fundNature = request.getParameter("fundnature");         
	           
	          	          	           
	            xml="<response><command>Add</command>";
	            try{
	                ps=con.prepareStatement("insert into HRM_GPF_TRANS_TYPES(gpf_trans_type_id,gpf_trans_type_desc,cr_achead_code,db_achead_code,fund_nature,updated_by,updated_date) values(?,?,?,?,?,?,?)");
	                ps.setString(1,typeId);
	                ps.setString(2,typeDesc);
	                ps.setInt(3,crCode);
	                ps.setInt(4,dbCode);
	                ps.setString(5,fundNature);
	               ps.setString(6,updatedby);
	                ps.setTimestamp(7,ts);
	                ps.executeUpdate();
	                
	                ps=con.prepareStatement("select gpf_trans_type_id,gpf_trans_type_desc,cr_achead_code,db_achead_code,fund_nature from HRM_GPF_TRANS_TYPES");
	                   
                    rs=ps.executeQuery();  
                    xml=xml+"<flag>success</flag>";
                    
                    while(rs.next()){
                        xml=xml+"<typeid>"+rs.getString(1)+"</typeid>";                
                        xml=xml+"<typedesc>"+rs.getString(2)+"</typedesc>";
                        xml=xml+"<crcode>"+rs.getInt(3)+"</crcode>";
                       xml=xml+"<dbcode>"+rs.getInt(4)+"</dbcode>";
                       
                        xml=xml+"<fundnature>"+rs.getString(5)+"</fundnature>";
                      
                    }
                
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Update")) {
	        	
	        	String typeId=request.getParameter("typeid");
	            String typeDesc=request.getParameter("typedesc");
        	            
	            int crCode=Integer.parseInt(request.getParameter("crcode").trim()); 
	            int dbCode=Integer.parseInt(request.getParameter("dbcode").trim());
	           String fundNature = request.getParameter("fundnature");         
	           
	            xml="<response><command>Update</command>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_TRANS_TYPES set gpf_trans_type_desc=?,cr_achead_code=?,db_achead_code=?,fund_nature=?,updated_by=?,updated_date=? where gpf_trans_type_id=?");
                    ps.setString(1,typeDesc);   
                    ps.setInt(2,crCode);
                    ps.setInt(3,dbCode);
                    ps.setString(4,fundNature);
	                ps.setString(5,updatedby);
	                ps.setTimestamp(6,ts);
	                ps.setString(7,typeId);
	               
	                ps.executeUpdate();
	                
	               
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<typeid>"+typeId+"</typeid>";                
                    xml=xml+"<typedesc>"+typeDesc+"</typedesc>";
                    xml=xml+"<crcode>"+crCode+"</crcode>";
                   xml=xml+"<dbcode>"+dbCode+"</dbcode>";
                   
                    xml=xml+"<fundnature>"+fundNature+"</fundnature>";
                
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Delete")) {
	            System.out.println("delete");
	            
	            String typeId=request.getParameter("typeid");   
	            
	            xml="<response><command>Delete</command>";
	          try{
	                ps=con.prepareStatement("delete from HRM_GPF_TRANS_TYPES where gpf_trans_type_id=?");
	                ps.setString(1,typeId);
	              
	                ps.executeUpdate();
	            
	                ps=con.prepareStatement("select gpf_trans_type_id,gpf_trans_type_desc,cr_achead_code,db_achead_code,fund_nature from HRM_GPF_TRANS_TYPES");
	                   
                    rs=ps.executeQuery();  
                    xml=xml+"<flag>success</flag>";
                    
                    while(rs.next()){
                        xml=xml+"<typeid>"+rs.getString(1)+"</typeid>";                
                        xml=xml+"<typedesc>"+rs.getString(2)+"</typedesc>";
                        xml=xml+"<crcode>"+rs.getInt(3)+"</crcode>";
                       xml=xml+"<dbcode>"+rs.getInt(4)+"</dbcode>";
                       
                        xml=xml+"<fundnature>"+rs.getString(5)+"</fundnature>";
                      
                    }
                
	              
	          }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	       
	        
	         System.out.print(xml);
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
