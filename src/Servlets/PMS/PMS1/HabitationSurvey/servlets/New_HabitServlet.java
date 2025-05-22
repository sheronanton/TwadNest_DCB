package Servlets.PMS.PMS1.HabitationSurvey.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class New_HabitServlet extends HttpServlet
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

                     //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

                           Class.forName(strDriver.trim());
                           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                         System.out.println("this is habitation servlet");
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
               
               //this is function to load panchayat combo     
          if(strCommand.equals("pan"))
            {
                    try
                    {
                     
                        int DistCode=Integer.parseInt(request.getParameter("dist"));
                       int BlockCode=Integer.parseInt(request.getParameter("block"));
                     
                      String sql="select PANCHAYAT_CODE,PANCHAYAT_NAME from COM_MST_PANCHAYATS where  DISTRICT_CODE=? AND BLOCK_CODE=?";
                    ps=connection.prepareStatement(sql);
                   
                        ps.setInt(1,DistCode);
                        ps.setInt(2,BlockCode);
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
            
            
            //this is the function to load habitation combo
            
             else if(strCommand.equals("Habit"))
               {
                       try
                       {
                        
                           int DistCode=Integer.parseInt(request.getParameter("dist"));
                          int BlockCode=Integer.parseInt(request.getParameter("block"));
                           int PanchayatCode=Integer.parseInt(request.getParameter("Panchayat"));
                        
                         String sql="select HABITATION_CODE,HABITATION_NAME from COM_MST_HABITATIONS where  DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=?";
                       ps=connection.prepareStatement(sql);
                      
                           ps.setInt(1,DistCode);
                           ps.setInt(2,BlockCode);
                           ps.setInt(3,PanchayatCode);
                           
                       results=ps.executeQuery();
                          
                       while(results.next())
                       {
                        String temp=results.getString("HABITATION_CODE");
                         
                         xml=xml+"<option><desc>"+results.getString("HABITATION_NAME")+"</desc><id>"+temp+"</id></option>";
                           
                       }
                           xml="<select>"+xml+"</select>";                   
                       } 
                       
                       catch(Exception e)
                       {
                       System.out.println(e);
                       }
               }
               
               
               // This function is to load census year in first tab
               
               
                else if(strCommand.equals("CensusYr"))
                  {
                          try
                          {
                           
                              int DistCode=Integer.parseInt(request.getParameter("dist"));
                             int BlockCode=Integer.parseInt(request.getParameter("block"));
                              int PanchayatCode=Integer.parseInt(request.getParameter("Panchayat"));
                              int HabitationCode=Integer.parseInt(request.getParameter("Habitation"));
                           
                            String sql="SELECT * FROM HBS_MST_POPULATION WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=?";
                          ps=connection.prepareStatement(sql);
                         
                              ps.setInt(1,DistCode);
                              ps.setInt(2,BlockCode);
                              ps.setInt(3,PanchayatCode);
                              ps.setInt(4,HabitationCode);
                              
                          results=ps.executeQuery();
                             
                          while(results.next())
                          {
                           String temp=results.getString("CENSUS_YEAR");
                            
                            xml=xml+"<option><desc>"+results.getString("CENSUS_YEAR")+"</desc><id>"+temp+"</id></option>";
                              
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