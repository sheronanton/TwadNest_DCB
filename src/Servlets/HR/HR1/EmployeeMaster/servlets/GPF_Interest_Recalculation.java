package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
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
import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class GPF_Interest_Recalculation
 */
public class GPF_Interest_Recalculation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Interest_Recalculation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	Connection con2=null;
        Connection con=null;
        Connection con1=null;
        ResultSet rs=null;
        ResultSet rs2=null;
        ResultSet rs3=null;
        ResultSet rs4=null;
        ResultSet rs5=null;
        ResultSet rs6=null;
        PreparedStatement ps1=null;
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
    	   int empid=0;
    	   long l=System.currentTimeMillis();
           java.sql.Timestamp ts=new java.sql.Timestamp(l);
           String ConnectionString="";
           String strDriver="",strdsn="",strhostname="",strportno="",strsid="",strdbusername="",strdbpassword="";
    	   String updatedby="";
           try {
                       
        	   LoadDriver driver=new LoadDriver();
               con=driver.getConnection();

                                   // con1=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                                   // con2=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());    
                   }
                   catch(Exception e)
                       {
                          System.out.println("Exception in openeing connection :"+e); 
                          //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");   
                          
                       }
                       
                           
                           
                           try
                                   {
                                       HttpSession session=request.getSession(false);
                                       UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                               	      
                                 	    System.out.println("user id::"+empProfile.getEmployeeId());
                                 	    empid=empProfile.getEmployeeId(); 
                                 	    updatedby=(String)session.getAttribute("UserId");
                                       System.out.println("user id..."+updatedby);
                                      
                                       
                                 	    if(session==null)
                                       {
                                           System.out.println(request.getContextPath()+"/index.jsp");
                                           response.sendRedirect(request.getContextPath()+"/index.jsp");

                                       }
                                       System.out.println(session);

                                   }catch(Exception e)
                                   {
                                   System.out.println("Redirect Error :"+e);
                                   }
      System.out.println("------------My Java called----------------");
      
    	String fin_year="";
    	int ac_month=0,ac_year=0,bal=0,sub_amt=0,ref_amt=0,with_amt=0,ob=0,ac_year1=0,ac_year2=0,flag=1,gpf_no,count=1,mul=12;
    	double credit=0;
    	double credit1=0;
    	fin_year=request.getParameter("fin_year");
    	gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
    	String fin[]=fin_year.split("-");
    	ac_year1=Integer.parseInt(fin[0]);
    	ac_year2=Integer.parseInt(fin[1]);
    	ac_month=4;
    	ac_year=ac_year1;
    	System.out.println("fin_year----------------"+fin_year);
    	System.out.println("ac_year1----------------"+ac_year1);
    	System.out.println("ac_year2----------------"+ac_year2);
    	//gpf_no=4081;
    	try{
    		
              	   
              CallableStatement call=con.prepareCall("{call GPF_REGULAR_INDIVIDUAL_INT(?,?,?,?,?)}");
             
             
              call.setInt(1,gpf_no);
              call.setInt(2,4);
              call.setInt(3,ac_year);
              call.setString(4,fin_year);
              call.setString(5,updatedby);
              //call.setTimestamp(5, ts);
              call.execute();
             
              	
             
           
           
            con.close();
          
     
    		
    		
    		
    	}
    	catch(SQLException e)
    	{
    		 System.out.println("----------------Error in gpf_no");
    		e.printStackTrace();
    	}
    	System.out.println("BYE BYE----------------C U-------------------------"); 
        
    }
    	/**
    	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    	 */
    	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doProcess(request, response);
    		
    	}

    	/**
    	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    	 */
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		doProcess(request, response);
    			}

}
