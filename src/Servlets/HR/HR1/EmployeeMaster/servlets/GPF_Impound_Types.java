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
 * Servlet implementation class GPF_Impound_Types
 */
public class GPF_Impound_Types extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Impound_Types() {
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
	                   
	                    ps=con.prepareStatement("select Impound_Type_Id ,Impound_Type_Desc,to_char(Date_effective_from,'dd/mm/yyyy') from HRM_GPF_MST_IMPOUND_TYPES");
	                    rs=ps.executeQuery();
	                    
	                    xml=xml+"<flag>success</flag>";
	                        
	                        while(rs.next()){
	                            xml=xml+"<impoundid>"+rs.getString("Impound_Type_Id")+"</impoundid>";                
	                            xml=xml+"<impounddesc>"+rs.getString("Impound_Type_Desc")+"</impounddesc>";
	                           // xml=xml+"<interest>"+rs.getString("Interest_Applicable")+"</interest>";
	                            xml=xml+"<date>"+rs.getString(3)+"</date>";
	                        }
	                    
	                    }
	                 
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	          
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	            
	           
	           String impoundId;
	           impoundId =request.getParameter("impoundid");
	          String  impoundDesc= request.getParameter("impounddesc");
	         // String interest=request.getParameter("interest");
	          String date=request.getParameter("date"); 
	            xml="<response><command>Add</command>";
	            try{
	            System.out.println("this is add"+impoundId+" == "+impoundDesc);
	                ps=con.prepareStatement("select Impound_Type_Id,Impound_Type_Desc from HRM_GPF_MST_IMPOUND_TYPES where Impound_Type_Id=? ");
	                ps.setString(1, impoundId);
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
	                        ps=con.prepareStatement("insert into HRM_GPF_MST_IMPOUND_TYPES(Impound_Type_Id,Impound_Type_Desc,Date_effective_from,Process_Flow_Status_Id,Updated_date,Updated_by_User_id) values(?,?,to_date(?,'dd-mm-yyyy'),?,?,?)");
	                        ps.setString(1,impoundId);
	                        ps.setString(2,impoundDesc);
	                        //ps.setString(3, interest);
	                        ps.setString(3, date);
	                        ps.setString(4, "CR");
	                        ps.setTimestamp(5, ts);
	                        ps.setString(6,updatedby);
	                        ps.executeUpdate();
	                        xml=xml+"<flag>success</flag>";
	                        xml=xml+"<impoundid>"+impoundId+"</impoundid>";  
	                        xml=xml+"<impounddesc>"+impoundDesc+"</impounddesc>";
	                       // xml=xml+"<interest>"+interest+"</interest>";
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
	            String impoundId;
		           impoundId =request.getParameter("impoundid");
		          String  impoundDesc= request.getParameter("impounddesc");
		          //String interest=request.getParameter("interest");
		          String date=request.getParameter("date"); 
	           
	            xml="<response><command>Update</command>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_MST_IMPOUND_TYPES set Impound_Type_Desc=?,Date_effective_from=to_date(?,'dd-mm-yyyy'),Process_Flow_Status_Id=?,Updated_date=?,Updated_by_User_id=?  where Impound_Type_Id=?");
	                ps.setString(1,impoundDesc);
	                //ps.setString(2,interest);
	                ps.setString(2, date);
	                ps.setString(3, "MD");
	                ps.setTimestamp(4, ts);
	                ps.setString(5,updatedby);
	                ps.setString(6,impoundId);
	                ps.executeUpdate();
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<impoundid>"+impoundId+"</impoundid>";  
                    xml=xml+"<impounddesc>"+impoundDesc+"</impounddesc>";
                  //  xml=xml+"<interest>"+interest+"</interest>";
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
	            String docDesc;
	        String impoundId =request.getParameter("impoundid");
	          
	            
	            xml="<response><command>Delete</command>";
	          try{
	                ps=con.prepareStatement("delete from HRM_GPF_MST_IMPOUND_TYPES where Impound_Type_Id=?");
	                ps.setString(1,impoundId);
	                       
	                ps.executeUpdate();
	                
	              ps=con.prepareStatement("select Impound_Type_Id,Impound_Type_Desc,to_char(Date_effective_from,'dd/mm/yyyy')  from HRM_GPF_MST_IMPOUND_TYPES");
	              rs=ps.executeQuery();
	              
	              xml=xml+"<flag>success</flag>";
	                  
	                  while(rs.next()){
	                      xml=xml+"<impoundid>"+rs.getString("Impound_Type_Id")+"</impoundid>";                
	                      xml=xml+"<impounddesc>"+rs.getString("Impound_Type_Desc")+"</impounddesc>";
	                    //  xml=xml+"<interest>"+rs.getString("Interest_Applicable")+"</interest>";
                          xml=xml+"<date>"+rs.getString(3)+"</date>";
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
