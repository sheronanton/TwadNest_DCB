package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class GPF_Unfreezedata_servlet
 */
public class GPF_Unfreezedata_servlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
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
	     
	        CallableStatement cs=null;
	        PreparedStatement ps=null,ps_record_check;
	        CallableStatement CallStatement=null;
	        ResultSet rs=null;
	        HttpSession session=request.getSession(false);
	        String xml="";
	        String unfreezedby=(String)session.getAttribute("UserId"); 
	        long l=System.currentTimeMillis();
	        java.sql.Timestamp ts=new java.sql.Timestamp(l);
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        response.setHeader("Cache-Control","no-cache");  
	       // HttpSession session=request.getSession(false);
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
	        session =request.getSession(false);
			String command = request.getParameter("command");
		    System.out.println(command);
		   if(command.equalsIgnoreCase("unfreeze")) 
		   {
	
	    int Acc_unit_id =Integer.parseInt(request.getParameter("unit_name"));
	    int ac_month=Integer.parseInt(request.getParameter("ac_month"));
	    int ac_year=Integer.parseInt(request.getParameter("ac_year"));
	    String type=request.getParameter("type");
		String query=null;
	    try {
	    	if(type.equals("subscription"))
	    	{
	    			query="select * from hrm_gpf_subscriptionnew_temp where ACCOUNTING_UNIT_ID=? and AC_YEAR=? and AC_MONTH=? and process_flow_id='FR' ";
	    	}
	    	else if(type.equals("withdrawl"))
	    		   query="select * from HRM_GPF_WITHDRAWALNEW where ACC_UNIT_ID=? and AC_YEAR=? and AC_MONTH=? and process_flow_id='FR'";
	    	else
	    		query="select * from HRM_GPF_IMPOUND_DISBNEW where ACCOUNTING_UNIT_ID=? and AC_YEAR=? and AC_MONTH=? and process_flow_id='FR'";
	    ps_record_check=con.prepareStatement(query);
	    ps_record_check.setInt(1, Acc_unit_id);
	    ps_record_check.setInt(2, ac_year);
	    ps_record_check.setInt(3, ac_month);
	    rs=ps_record_check.executeQuery();
	    xml="<response><command>unfreeze</command>";
	    int a=0;
	    if(rs.next()){
	    	if(type.equals("subscription"))
                CallStatement=(CallableStatement) con.prepareCall ( "{call HRM_GPF_SUBSCRIPTION_UNFREEZE(?,?,?)}" );
        	else if(type.equals("withdrawl"))
        		CallStatement=(CallableStatement) con.prepareCall ( "{call HRM_GPF_WITHDRAWAL_UNFREEZE(?,?,?)}" );
        	else
        		CallStatement=(CallableStatement) con.prepareCall ( "{call HRM_GPF_impound_unfreeze(?,?,?)}" );
            CallStatement.setInt(1, Acc_unit_id);
            CallStatement.setInt(2, ac_year);
            CallStatement.setInt(3, ac_month);            
            a= CallStatement.executeUpdate();
            xml=xml+"<record>available</record>";
	    }  
	    else
	    	xml=xml+"<record>NotAvailable</record>";
	    
	    if(a>0)
	    {
	    	 xml=xml+"<flag>success</flag>";
	    	 ps=con.prepareStatement("insert into HRM_GPF_UNFREEZE_LOG(ACCOUNTING_UNIT_ID,ac_month,ac_year,data,UNFREEZED_BY,UNFREEZED_DATE)values(?,?,?,?,?,?)");
             ps.setInt(1,Acc_unit_id);
             ps.setInt(2,ac_month);
             ps.setInt(3,ac_year);
             ps.setString(4,type);
             ps.setString(5, unfreezedby);
             ps.setTimestamp(6, ts);
             ps.executeUpdate();
        
	    }          

	    else
	    	 xml=xml+"<flag>failure</flag>";
	
				
	}
            catch(Exception e) {
            xml=xml+"<flag>failure</flag>";
            e.printStackTrace();
    }
             xml=xml+"</response>";
             System.out.print(xml);
             out.println(xml);

}
}
}
