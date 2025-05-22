package Servlets.FAS.FAS1.CommonControls.servlets;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Common_Bank_Account_Number_Loading extends HttpServlet {
  
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	/**
         * Set Content Type 
         */
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        
        
        
        /**
         * Session Checking 
         */
        HttpSession session=request.getSession(false);
        try
         {
             
             if(session==null)
             {
                 System.out.println(request.getContextPath()+"/index.jsp");
                 response.sendRedirect(request.getContextPath()+"/index.jsp");
                 return;
             }
             System.out.println(session);
                 
         }catch(Exception e)
         {
         System.out.println("Redirect Error :"+e);
         }
        
        
         
         
        /**
         * Variables Declaration 
         */
        
        Connection con=null;
        PreparedStatement ps2=null;        
        ResultSet rs2=null;
        int year = 0;
        int month = 0;
        int day = 0;
        
        
        
        /**
         * Database Connection 
         */
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
                     //          ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
              {
                  System.out.println("Exception in opening connection :"+e);
              }
              
           int cmbAcc_UnitCode=0;
           String xml=null;   
              
              /** Get Accouting UNit ID */
          	try
          	{
          		cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          	}
          	catch(Exception e)
          	{
          		System.out.println(e);
          	}
              
                
              String sql =
            	  	"       select *             													\n"+   						
            	  	"		from 																	\n"+	
            	  	"		(																		\n"+	
            	  	"			select																\n"+
            	  	"				bank_id,														\n"+
            	  	"				BRANCH_ID,														\n"+
            	  	"				bank_ac_no, 													\n"+
            	  	"				AC_OPERATIONAL_MODE_ID,                                         \n"+
            	  	"				AC_OPERATIONAL_MODE_ID ||'-'||  bank_ac_no as acc_no			\n"+  
            	  	"			from																\n"+
            	  	"				fas_bank_balance												\n"+
            	  	"			where																\n"+		
            	  	"				accounting_unit_id = ?   										\n"+
            	  	"		)X																		\n"+			
            	  	"		left outer join															\n"+
            	  	"		(																		\n"+		 
            	  	"				select bank_id as y_bank_id , bank_name as y_bank_name from fas_bank_list							\n"+	
            	  	"		)Y																		\n"+
            	  	"    on 																		\n"+
            	  	"      X.bank_id  = Y.y_bank_id													\n"+
            	  	"    left outer join 															\n"+
            	  	"    (																			\n"+
            	  	"      select  BANK_ID as z_bank_id, BRANCH_ID as z_BRANCH_ID , BRANCH_NAME as z_BRANCH_NAME from fas_bank_branch	\n"+                   
            	  	"    )Z                                    										\n"+
            	  	"	 on  																		\n"+
            	  	"      X.bank_id  = Z.z_bank_id  and											\n"+ 
            	  	"      X.BRANCH_ID = Z.z_branch_id												\n"+
            	  	" 																			      ";
              
              
              try
            {
              ps2=con.prepareStatement(sql);
              ps2.setInt(1,cmbAcc_UnitCode);
              rs2=ps2.executeQuery();
              int count=0;
              
              xml="<response><command>LoadBankAccountNumber</command>";
              
              /** Count How many Records are available  */
              while (rs2.next()) 
              {
                 xml=xml+ "<acc_no>"+ rs2.getString("bank_ac_no") +"</acc_no>";	 
                 xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
                 xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
                 xml=xml+ "<opr_mode>"+ rs2.getString("AC_OPERATIONAL_MODE_ID") +"</opr_mode>";                 
                 xml=xml+ "<acc_desc>"+ rs2.getString("acc_no") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
                 xml=xml+ "<bank_name>"+ rs2.getString("y_bank_name") +"</bank_name>";                 
                 xml=xml+ "<branch_name>"+ rs2.getString("z_BRANCH_NAME") +"</branch_name>";
                 count++;
              }
              
              if(count==0) {
                  xml=xml+"<flag>NoData</flag></response>";
              }
              else{                
                  xml=xml+"<flag>success</flag></response>";
              }              
          }
          catch(Exception e) 
          {
              System.out.println("Exception in assigning..."+e);
              xml=xml+"<flag>failure</flag></response>";
          }
          
         System.out.println(xml);
         out.println(xml);
         out.close();
    }
}
