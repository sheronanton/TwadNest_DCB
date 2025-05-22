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

/**
 * Servlet implementation class GPF_Withdrawal_Status
 */
public class GPF_Withdrawal_Status extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Withdrawal_Status() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
	        try {
	                    
	                                 ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                                 String ConnectionString="";

	                                 String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                                 String strdsn=rs1.getString("Config.DSN");
	                                 String strhostname=rs1.getString("Config.HOST_NAME");
	                                 String strportno=rs1.getString("Config.PORT_NUMBER");
	                                 String strsid=rs1.getString("Config.SID");
	                                 String strdbusername=rs1.getString("Config.USER_NAME");
	                                 String strdbpassword=rs1.getString("Config.PASSWORD");
	                     //            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
	                                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
	                                 Class.forName(strDriver.trim());
	                                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
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
	                   
	                    ps=con.prepareStatement("select Withdrawal_status_id ,Withdrawal_Status_Desc from HRM_GPF_MST_WITHDRAWAL_STATUS");
	                    rs=ps.executeQuery();
	                    
	                    xml=xml+"<flag>success</flag>";
	                        
	                        while(rs.next()){
	                            xml=xml+"<w_id>"+rs.getInt("Withdrawal_status_id")+"</w_id>";                
	                            xml=xml+"<w_desc>"+rs.getString("Withdrawal_Status_Desc")+"</w_desc>";
	                           
	                        }
	                    
	                    }
	                 
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	          
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	            
	           
	            int w_id;
	            w_id =Integer.parseInt(request.getParameter("w_id"));
	          String  w_desc= request.getParameter("w_desc");
	            
	            xml="<response><command>Add</command>";
	            try{
	            System.out.println("this is add"+w_id+" == "+w_desc);
	                ps=con.prepareStatement("select Withdrawal_status_id,Withdrawal_Status_Desc from HRM_GPF_MST_WITHDRAWAL_STATUS where Withdrawal_status_id=? ");
	                ps.setInt(1, w_id);
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
	                        ps=con.prepareStatement("insert into HRM_GPF_MST_WITHDRAWAL_STATUS(Withdrawal_status_id,Withdrawal_Status_Desc,Updated_date,Updated_by_User_id) values(?,?,?,?)");
	                        ps.setInt(1,w_id);
	                        ps.setString(2,w_desc);
	                        ps.setTimestamp(3, ts);
	                        ps.setString(4,updatedby);
	                        ps.executeUpdate();
	                        xml=xml+"<flag>success</flag>";
	                        xml=xml+"<w_id>"+w_id+"</w_id>";  
	                        xml=xml+"<w_desc>"+w_desc+"</w_desc>";
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
	            String w_desc;
	           int w_id =Integer.parseInt(request.getParameter("w_id"));
	            w_desc= request.getParameter("w_desc");
	           
	            xml="<response><command>Update</command>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_MST_WITHDRAWAL_STATUS set Withdrawal_Status_Desc=?, Updated_date=?,Updated_by_User_id=?  where Withdrawal_status_id=?");
	                ps.setString(1,w_desc);
	                ps.setTimestamp(2, ts);
	                ps.setString(3, updatedby);
	                ps.setInt(4,w_id);
	                ps.executeUpdate();
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<w_id>"+w_id+"</w_id>";  
	                xml=xml+"<w_desc>"+w_desc+"</w_desc>";
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Delete")) {
	            System.out.println("delete");
	            String w_desc;
	           int w_id =Integer.parseInt(request.getParameter("w_id"));
	          
	            
	            xml="<response><command>Delete</command>";
	          try{
	                ps=con.prepareStatement("delete from HRM_GPF_MST_WITHDRAWAL_STATUS where Withdrawal_status_id=?");
	                ps.setInt(1,w_id);
	                       
	                ps.executeUpdate();
	                
	              ps=con.prepareStatement("select Withdrawal_status_id,Withdrawal_Status_Desc from HRM_GPF_MST_WITHDRAWAL_STATUS");
	              rs=ps.executeQuery();
	              
	              xml=xml+"<flag>success</flag>";
	                  
	                  while(rs.next()){
	                      xml=xml+"<w_id>"+rs.getString("Withdrawal_status_id")+"</w_id>";                
	                      xml=xml+"<w_desc>"+rs.getString("Withdrawal_Status_Desc")+"</w_desc>";
	                     
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
