package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletHelp extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, 
                                                           IOException {
        response.setHeader("cache-control","no-cache");                                                           
        try
        {
            HttpSession session=request.getSession(false);
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
               /* response.setContentType("text/xml");
                response.setHeader("Cache-Control","no-cache"); 
               String xml="<response><command>session</command><flag>failure</flag><flag>Session already closed.</flag></response>";
               System.out.println(xml);
                out.println(xml); 
                out.close();
                return;*/
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        PrintWriter out = response.getWriter();
        System.out.println("servlet called help");
        boolean ErrorOccured=false;
        String ErrorMessage="";
        String major="";
        String xml="";
        
        int IntID=0;
        major=request.getParameter("MajorId");
        
        if(!ErrorOccured) {
        
            Connection connection=null;
            PreparedStatement ps=null;
            ResultSet results=null;   
            
            try
            {         
                
                ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                String ConnectionString="";

                String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                String strdsn=rs.getString("Config.DSN");
                String strhostname=rs.getString("Config.HOST_NAME");
                String strportno=rs.getString("Config.PORT_NUMBER");
                String strsid=rs.getString("Config.SID");
                String strdbusername=rs.getString("Config.USER_NAME");
                String strdbpassword=rs.getString("Config.PASSWORD");

         //       ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 

                Class.forName(strDriver.trim());
                connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                
                try
                {                     
                     connection.clearWarnings();
                     xml="<response><flag>success</flag>";
                     String sql="select Minor_system_id,minor_system_desc from sec_mst_minor_systems where major_system_Id=?";
                     ps=connection.prepareStatement(sql);
                     ps.setString(1,major);
                     results=ps.executeQuery();
                     while(results.next())
                     {
                                      
                         xml=xml+"<options><minorid>" +results.getString("minor_system_id")  + "</minorid><minordesc>"+results.getString("minor_system_desc").trim()+"</minordesc></options>";
                    
                     }
                     xml=xml+"</response>";
                     
                }
                catch(SQLException e)
                {
                    System.out.println("Exception in creating statement:"+e);
                    ErrorOccured=true;
                    ErrorMessage=e.getMessage();
                }  
           
             
                
            }
            catch(Exception e)
            {
                System.out.println("Exception in openeing connection:"+e);
                ErrorOccured=true;
                ErrorMessage=e.getMessage();
            }
            
        }
        response.setContentType("text/xml");
        out.write(xml);
        System.out.println("xml is"+xml);
        out.close();
    }
}
