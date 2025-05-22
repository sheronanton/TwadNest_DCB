package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ResetPassword_new extends HttpServlet {
    
     public void init(ServletConfig config) throws ServletException {
         super.init(config);
         
     }

     public void doGet(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, IOException 
     {
         Connection con=null;
         try {
             
             File f=new File("");
             f.deleteOnExit();
             
             
              ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                          String ConnectionString="";

                          String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                          String strdsn=rs.getString("Config.DSN");
                          String strhostname=rs.getString("Config.HOST_NAME");
                          String strportno=rs.getString("Config.PORT_NUMBER");
                          String strsid=rs.getString("Config.SID");
                          String strdbusername=rs.getString("Config.USER_NAME");
                          String strdbpassword=rs.getString("Config.PASSWORD");

             //             ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 

                           Class.forName(strDriver.trim());
                           con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
         }
         catch(Exception e) {
             System.out.println("Exception in connection..."+e);
         }
          ResultSet rs=null;
          PreparedStatement ps=null;
         PrintWriter out = response.getWriter();
         response.setContentType("text/xml");
         response.setHeader("Cache-Control","no-cache");
         String userid=null,newpass="",strCommand=null;
         String strPassword=null;
         int empid=0;
         try {
             strCommand=request.getParameter("Command");
             System.out.println("assign....."+strCommand);
             userid=request.getParameter("txtUserId");
             System.out.println("assign..... userid::"+userid);
             newpass=request.getParameter("txtnewpass");
             System.out.println("assign..... new pass::"+newpass);
             
                 
             
             
         }
         catch(Exception e) {
             System.out.println("Exception in assigning..."+e);
         }
         String xml=null;
         if(strCommand.equalsIgnoreCase("test")) {
             xml="<response><command>test</command>";
              try
             {
                 
                 byte []b=newpass.getBytes();
               
                                    
                                 
                                 try{
                                     MessageDigest algorithm = MessageDigest.getInstance("MD5");
                                     algorithm.reset();
                                     algorithm.update(b);
                                     byte messageDigest[] = algorithm.digest();
                                     System.out.println("actual encrypt::"+messageDigest);                            
                                     StringBuffer hexString = new StringBuffer();
                                     for (int i=0;i<messageDigest.length;i++) {
                                          hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                                     }
                                                                
                                         strPassword=new String(hexString);
                                     }
                                     catch(NoSuchAlgorithmException nsae){
                                           System.out.println("Second MD5::"+nsae);          
                                         }
                                         System.out.println("userid.equals(newpass)"+userid.equals(newpass));
                                  if(userid.equals(newpass))
                                  {
                                     ps=con.prepareStatement("update  SEC_MST_USERS set USER_PASSWORD=?,CHANGE_PASSWORD='1'  where USER_ID=?" );
                                     ps.setString(1,strPassword);
                                     ps.setString(2,userid);
                                     ps.executeUpdate();
                                     System.out.println("second check ok");
                                  }
                                  else
                                  {
                                      ps=con.prepareStatement("update  SEC_MST_USERS set USER_PASSWORD=?,CHANGE_PASSWORD='0'  where USER_ID=?" );
                                      ps.setString(1,strPassword);
                                      ps.setString(2,userid);
                                      ps.executeUpdate();
                                      System.out.println("second check ok2");
                                  }
                                  
                                 xml=xml+"<flag>success</flag>";
                            
                
             }
            
             catch(Exception e) {
             
                 System.out.println("catch........"+e);
                xml=xml+"<flag>failure</flag>";
             }
             
         }  
             
            else if(strCommand.equalsIgnoreCase("Login")) {
                 xml="<response><command>Login</command>";
            
                  try
                 {
                    
                                                 
                                     ps=con.prepareStatement("select USER_ID from    SEC_MST_USERS  where USER_ID=?" );
                                     ps.setString(1,userid);
                                     rs=ps.executeQuery();
                                     if(!rs.next())
                                     {
                                        xml=xml+"<flag>failure</flag>";
                                        
                                     }
                                     else
                                     {
                                    	 String sql=   "SELECT * " +
                                    	 "FROM " +
                                    	 "  (SELECT USER_ID " +
                                    	 "  FROM sec_mst_users " +
                                    	 "  WHERE USER_ID    =? " +
                                    	 "  AND Employee_Id IN " +
                                    	 "    (SELECT Employee_Id FROM Sec_Mst_User_Roles WHERE ROLE_ID =9 " +
                                    	 "    ) " +
                                    	 "  UNION " +
                                    	 "  SELECT USER_ID " +
                                    	 "  FROM Sec_Mst_Other_User_Roles " +
                                    	 "  WHERE USER_ID=? " +
                                    	 "  AND role_id  =9 " +
                                    	 "  )";
                                    	 ps=con.prepareStatement(sql);
                                    	 ps.setString(1,userid);
                                    	 ps.setString(2,userid);
                                         rs=ps.executeQuery();
                                         if(rs.next())
                                         {
                                        xml=xml+"<flag>failure2</flag>";
                                         }else
                                         {
                                        	 xml=xml+"<flag>success</flag>";
                                         }
                                        
                                        	 
                                     }
                                     
                                
                    
                 }
                
                 catch(Exception e) {
                 
                     System.out.println("catch........"+e);
                    xml=xml+"<flag>failure</flag>";
                 }
         }            
         xml=xml+"</response>";
         out.println(xml); 
         System.out.println(xml);   
         
        
         
         
         
         
     }
     }
