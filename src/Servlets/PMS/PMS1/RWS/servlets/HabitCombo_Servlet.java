package Servlets.PMS.PMS1.RWS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HabitCombo_Servlet extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection connection=null;
    private Statement statement=null;
    private ResultSet results=null;
    private ResultSet results1=null;
    private PreparedStatement ps=null;
    private PreparedStatement ps1=null;

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
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

                      //    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

                           Class.forName(strDriver.trim());
                           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

              try
              {
                statement=connection.createStatement();
                connection.clearWarnings();
              }
              catch(SQLException e)
              {
                  System.out.println("Exception in creating statement:"+e);
              }          
           }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
    
        try
                {
                    HttpSession session=request.getSession(false);
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


        System.out.println("servlet called");
                String strCommand = ""; 
                    String xml="";
                    response.setContentType("text/xml");
                    PrintWriter pw=response.getWriter();    
                    response.setHeader("Cache-Control","no-cache");
                    try
                    {
                      strCommand = request.getParameter("command");      
                    }
                    catch(Exception e)
                    {
                      e.printStackTrace();
                    }
                    
        if(strCommand.equals("major"))
            {
                    try
                    {
                      String strstate=request.getParameter("First");
                      String sql="select DISTRICT_CODE,DISTRICT_NAME from COM_MST_DISTRICTS where STATE_CODE=?";
                    ps=connection.prepareStatement(sql);
                    ps.setString(1,strstate);
                    results=ps.executeQuery();
                       
                    while(results.next())
                    {
                      String temp=results.getString("DISTRICT_CODE");
                      
                      xml=xml+"<option><desc>"+results.getString("DISTRICT_NAME")+"</desc><id>"+temp+"</id></option>";
                        
                    }
                        xml="<select>"+xml+"</select>";                   
                    } 
                    
                    catch(Exception e)
                    {
                    System.out.println(e);
                    }
            }
    
    
        else if(strCommand.equals("block"))
            {
                    try
                    {
                      String strstate=request.getParameter("Second");
                        String strstate1=request.getParameter("dist");
                      String sql="select BLOCK_CODE,BLOCK_NAME from COM_MST_BLOCKS where STATE_CODE=? AND DISTRICT_CODE=?";
                    ps=connection.prepareStatement(sql);
                    ps.setString(1,strstate);
                        ps.setString(2,strstate1);
                    results=ps.executeQuery();
                       
                    while(results.next())
                    {
                      String temp=results.getString("BLOCK_CODE");
                      
                      xml=xml+"<option><desc>"+results.getString("BLOCK_NAME")+"</desc><id>"+temp+"</id></option>";
                        
                    }
                        xml="<select>"+xml+"</select>";                   
                    } 
                    
                    catch(Exception e)
                    {
                    System.out.println(e);
                    }
            }
    
        else if(strCommand.equals("pan"))
            {
                    try
                    {
                      String strstate=request.getParameter("Second");
                        String strstate1=request.getParameter("dist");
                       String strstate2=request.getParameter("block");
                     
                      String sql="select PANCHAYAT_CODE,PANCHAYAT_NAME from COM_MST_PANCHAYATS where STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=?";
                    ps=connection.prepareStatement(sql);
                    ps.setString(1,strstate);
                        ps.setString(2,strstate1);
                        ps.setString(3,strstate2);
                    results=ps.executeQuery();
                       
                    while(results.next())
                    {
                     String temp=results.getString("PANCHAYAT_CODE");
                      
                      xml=xml+"<option><desc>"+results.getString("PANCHAYAT_NAME")+"</desc><id>"+temp+"</id></option>";
                        
                    }
                        xml="<select>"+xml+"</select>";                   
                    } 
                    
                    catch(Exception e)
                    {
                    System.out.println(e);
                    }
            }
      
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
           
           
    }
}
