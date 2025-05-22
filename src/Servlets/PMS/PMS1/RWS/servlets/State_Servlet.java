package Servlets.PMS.PMS1.RWS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class State_Servlet extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection connection=null;
    private Statement statement=null;
    private ResultSet results=null;
    private PreparedStatement ps=null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("within init config");

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
                   
              //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

                  Class.forName(strDriver.trim());
                  connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                  System.out.println("creating statement:" +connection);
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


        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
        String strStateCode="";
        String strStateName = "";
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
        try
        {
          strStateCode= request.getParameter("StateCode");
          strStateName = request.getParameter("StateName");
        }
        catch(Exception e)
        { 
            System.out.println("in getting values **** "+ e);
        }
        
        if(strCommand.equalsIgnoreCase("Delete"))
                {
                  xml="<response><command>Delete</command>";
                  String sql="delete from COM_MST_STATE where STATE_CODE=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                    ps.setString(1,strStateCode);
                    ps.executeUpdate();        
                    xml=xml+"<flag>success</flag><StateCode>"+strStateCode+"</StateCode>";
                    ps.close();
                  }
                  catch(Exception e)
                  {        
                     System.out.println("exception in the delete"+ e);
                    xml=xml+"<flag>failure</flag>";
                  }
                  xml=xml+"</response>";
                }
                
                else if(strCommand.equalsIgnoreCase("Update"))
                {
                  xml="<response><command>Update</command>";
                  String sql="update COM_MST_STATE set STATE_NAME=? where STATE_CODE=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                    ps.setString(2,strStateCode);
                    ps.setString(1,strStateName);
                    ps.executeUpdate();        
                    xml=xml+"<flag>success</flag>";
                    ps.close();
                  }
                  catch(Exception e)
                  {        
                     System.out.println("exception in the update"+ e);
                    xml=xml+"<flag>failure</flag>";
                  }
                  xml=xml+"</response>";
                }
                
        else if(strCommand.equalsIgnoreCase("Load"))
        {
          String sxml="<response><command>Load</command>";
          
            try
            {
             String sql="SELECT STATE_CODE FROM COM_MST_STATE WHERE STATE_CODE=?";
                try
                    {
                       ps=connection.prepareStatement(sql);
                       ps.setString(1,strStateCode);
                      results=ps.executeQuery();
                      int i=0;
                            while(results.next())
                            {
                              i++;
                            }
                            if(i==0)
                            {
                                xml=sxml+"<flag>failure</flag>"; 
                            }
                            else {
                               
                                xml=sxml+"<flag>success</flag>";
                            }
                            
                }catch(Exception ae){System.out.println("Exception is:the check" +ae);
                   
                }
                }
                catch(Exception e)
                {
                System.out.println("exce ****2 vv"+ e);
                xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
        }    
                
        else if(strCommand.equalsIgnoreCase("Add"))
               {
                 String sxml="<response><command>Add</command>";
                 
                   try
                   {
                    String sql="SELECT STATE_CODE FROM COM_MST_STATE WHERE STATE_CODE=?";
                    try
                   {
                       ps=connection.prepareStatement(sql);
                       ps.setString(1,strStateCode);
                      results=ps.executeQuery();
                      int i=0;
                   while(results.next())
                   {
                      i++;
                   }
                   if(i==0)
                   {
                       sql="insert into COM_MST_STATE(STATE_CODE,STATE_NAME) values(?,?)";
                                 try
                                 {
                                   ps=connection.prepareStatement(sql);
                                   ps.setString(1,strStateCode);
                                   ps.setString(2,strStateName);
                                   ps.executeUpdate(); 
                                     xml=sxml+"<flag>success</flag>";
                                     ps.close();
                                 }
                                 catch(Exception e)
                                 {        
                                    System.out.println("exception in the add"+ e);
                                   xml=sxml+"<flag>failure</flag>";
                                 }  
                   }
                   else
                   {
                      xml=sxml+"<flag>Record</flag>"; 
                   }
                   }catch(Exception ae){System.out.println("Exception is: in the insert" +ae);
                      
                   }
                   }
                   catch(Exception e)
                   {
                   System.out.println("Exception in the select --- insert"+ e);
                   }
                 xml=xml+"</response>";
               }

        
        else if(strCommand.equals("check"))
              {
             String  sxml="<response><command>check</command>";
               try
              {
                String sql="SELECT * FROM COM_MST_STATE WHERE STATE_CODE=? AND STATE_NAME=?";
                try
               {
                   ps=connection.prepareStatement(sql);
                   ps.setString(1,strStateCode);
                   ps.setString(2,strStateName);
                  results=ps.executeQuery();
                  int i=0;
               while(results.next())
              { 
                  i++;
              }
              if(i==0)
              {
                  xml=sxml+"<flag>failure</flag>";
              }
              else 
              {
                  xml=sxml+"<flag>success</flag>"+xml; 
              }
               }catch(Exception ae){System.out.println("Exception is:the check" +ae);
                  
               }
            }
            catch(Exception e)
            {        
               System.out.println("exce ****2 vv"+ e);
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
                System.out.println("xml is : " + xml);
                pw.write(xml);
                pw.flush();
                pw.close();
        
    }
}
