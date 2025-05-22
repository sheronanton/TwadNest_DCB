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
 * Servlet implementation class GPF_Final_Doc_Types
 */
public class GPF_Final_Doc_Types extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Final_Doc_Types() {
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
	                   
	                    ps=con.prepareStatement("select Attach_Doc_Type_Id ,Attach_Doc_Desc from HRM_GPF_MST_Attach_DOC_TYPES");
	                    rs=ps.executeQuery();
	                    
	                    xml=xml+"<flag>success</flag>";
	                        
	                        while(rs.next()){
	                            xml=xml+"<docid>"+rs.getInt("Attach_Doc_Type_Id")+"</docid>";                
	                            xml=xml+"<docdesc>"+rs.getString("Attach_Doc_Desc")+"</docdesc>";
	                           
	                        }
	                    
	                    }
	                 
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	          
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	            
	           
	            int docId;
	            docId =Integer.parseInt(request.getParameter("docid"));
	          String  docDesc= request.getParameter("docdesc");
	            
	            xml="<response><command>Add</command>";
	            try{
	            System.out.println("this is add"+docId+" == "+docDesc);
	                ps=con.prepareStatement("select Attach_Doc_Type_Id,Attach_Doc_Desc from HRM_GPF_MST_Attach_DOC_TYPES where Attach_Doc_Type_Id=? ");
	                ps.setInt(1, docId);
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
	                        ps=con.prepareStatement("insert into HRM_GPF_MST_Attach_DOC_TYPES(Attach_Doc_Type_Id,Attach_Doc_Desc,Process_Flow_Status_Id,Updated_date,Updated_by_User_id) values(?,?,?,?,?)");
	                        ps.setInt(1,docId);
	                        ps.setString(2,docDesc);
	                        ps.setString(3, "CR");
	                        ps.setTimestamp(4, ts);
	                        ps.setString(5,updatedby);
	                        ps.executeUpdate();
	                        xml=xml+"<flag>success</flag>";
	                        xml=xml+"<docid>"+docId+"</docid>";  
	                        xml=xml+"<docdesc>"+docDesc+"</docdesc>";
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
	            String docDesc;
	           int docId =Integer.parseInt(request.getParameter("docid"));
	            docDesc= request.getParameter("docdesc");
	           
	            xml="<response><command>Update</command>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_MST_Attach_DOC_TYPES set Attach_Doc_Desc=?,Process_Flow_Status_Id=?,Updated_date=?,Updated_by_User_id=?  where Attach_Doc_Type_Id=?");
	                ps.setString(1,docDesc);
	                ps.setString(2, "MD");
	                ps.setTimestamp(3, ts);
	                ps.setString(4,updatedby);
	                ps.setInt(5,docId);
	                ps.executeUpdate();
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<docid>"+docId+"</docid>";  
	                xml=xml+"<docdesc>"+docDesc+"</docdesc>";
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Delete")) {
	            System.out.println("delete");
	            String docDesc;
	           int docId =Integer.parseInt(request.getParameter("docid"));
	          
	            
	            xml="<response><command>Delete</command>";
	          try{
	                ps=con.prepareStatement("delete from HRM_GPF_MST_Attach_DOC_TYPES where Attach_Doc_Type_Id=?");
	                ps.setInt(1,docId);
	                       
	                ps.executeUpdate();
	                
	              ps=con.prepareStatement("select Attach_Doc_Type_Id,Attach_Doc_Desc from HRM_GPF_MST_Attach_DOC_TYPES");
	              rs=ps.executeQuery();
	              
	              xml=xml+"<flag>success</flag>";
	                  
	                  while(rs.next()){
	                      xml=xml+"<docid>"+rs.getString("Attach_Doc_Type_Id")+"</docid>";                
	                      xml=xml+"<docdesc>"+rs.getString("Attach_Doc_Desc")+"</docdesc>";
	                     
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
