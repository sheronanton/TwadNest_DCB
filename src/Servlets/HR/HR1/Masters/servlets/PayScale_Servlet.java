package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class PayScale_Servlet extends HttpServlet 
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
                                  
               //                 ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 

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
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
        String strPayScaleId="";
        String strPayScale = "";
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
          strPayScaleId= request.getParameter("PayScaleId");
          strPayScale = request.getParameter("PayScale");
        }
        catch(Exception e)
        { 
            System.out.println("in getting values **** "+ e);
        }
        
        if(strCommand.equalsIgnoreCase("Delete"))
                {
                  xml="<response><command>Delete</command>";
                  String sql="delete from HRM_MST_PAYSCALES where PAY_SCALE_ID=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                    ps.setString(1,strPayScaleId);
                    ps.executeUpdate();        
                    xml=xml+"<flag>success</flag><PayScaleId>"+strPayScaleId+"</PayScaleId>";
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
                  String sql="update HRM_MST_PAYSCALES set PAY_SCALE=? where PAY_SCALE_ID=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                    ps.setString(2,strPayScaleId);
                    ps.setString(1,strPayScale);
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
                
        else if(strCommand.equalsIgnoreCase("Add"))
        {
          xml="<response><command>Add</command>";
          String sql="insert into HRM_MST_PAYSCALES(PAY_SCALE_ID,PAY_SCALE) values(?,?)";
          try
          {
            ps=connection.prepareStatement(sql);
            ps.setString(1,strPayScaleId);
            ps.setString(2,strPayScale);
            ps.executeUpdate(); 
              xml=xml+"<flag>success</flag>";
              ps.close();
          }
          catch(Exception e)
          {        
             System.out.println("exception in the add"+ e);
            xml=xml+"<flag>failure</flag>";
          }
          
          xml=xml+"</response>";
        }
        
        else if(strCommand.equals("check"))
              {
             String  sxml="<response><command>check</command>";
               try
              {
                String sql="SELECT * FROM HRM_MST_PAYSCALES WHERE PAY_SCALE_ID=? AND PAY_SCALE=?";
                try
               {
                   ps=connection.prepareStatement(sql);
                   ps.setString(1,strPayScaleId);
                   ps.setString(2,strPayScale);
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
