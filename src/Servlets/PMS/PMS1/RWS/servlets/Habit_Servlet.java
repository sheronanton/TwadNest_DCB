package Servlets.PMS.PMS1.RWS.servlets; 

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Habit_Servlet extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection connection=null;
    private Statement statement=null;
    private ResultSet results=null;
    private ResultSet results1=null;
    private PreparedStatement ps=null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try
          { ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
        int strStateCode=0;
        int strDistCode = 0;
        int strBlockCode=0;
        int strPanCode=0;
        int strHabitCode=0;
        String strHabitName="";
        int strVACode=0;
        
        
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
          strStateCode= Integer.parseInt(request.getParameter("StateCode"));
          strDistCode = Integer.parseInt(request.getParameter("DistCode"));
            strBlockCode =Integer.parseInt(request.getParameter("BlockCode"));
            strPanCode =Integer.parseInt(request.getParameter("PanCode"));
            strHabitName = request.getParameter("HabitName");
            strVACode =Integer.parseInt(request.getParameter("VACode"));
            
        }
        catch(Exception e)
        { 
            System.out.println("in getting values **** "+ e);
        }
        try
        {
            strHabitCode =Integer.parseInt(request.getParameter("HabitCode"));
        }
        catch(Exception e)
        { 
            System.out.println("in getting values **** "+ e);
        }
        if(strCommand.equalsIgnoreCase("Delete"))
                {
                  xml="<response><command>Delete</command>";
                  
                  String sql="delete from COM_MST_HABITATIONS where STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                      ps.setInt(1,strStateCode);
                      ps.setInt(2,strDistCode);
                      ps.setInt(3,strBlockCode);
                      ps.setInt(4,strPanCode);
                      ps.setInt(5,strHabitCode);
                    ps.executeUpdate();        
                    xml=xml+"<flag>success</flag>";
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
                  String sql="update COM_MST_HABITATIONS  set HABITATION_NAME=?,CENSUS_VILLAGE_ALIAS_CODE=? where STATE_CODE=? AND DISTRICT_CODE=?";
                  sql=sql+ " AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                      ps.setInt(3,strStateCode);
                      ps.setInt(4,strDistCode);
                        ps.setInt(5,strBlockCode);
                      ps.setInt(6,strPanCode);
                      ps.setInt(7,strHabitCode);
                        ps.setString(1,strHabitName);
                      ps.setInt(2,strVACode);
                      
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
          String sxml="<response><command>Add</command>";
                   try
                             {
                              String sql="SELECT STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE,HABITATION_CODE FROM COM_MST_HABITATIONS WHERE  STATE_CODE=? AND";
                              sql=sql +" DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=?";
                                 ps=connection.prepareStatement(sql);
                                 ps.setInt(1,strStateCode);
                                 ps.setInt(2,strDistCode);
                                    ps.setInt(3,strBlockCode);
                                  ps.setInt(4,strPanCode);
                           ps.setInt(5,strHabitCode);
                                 results=ps.executeQuery();
                                 int i=0;
                                 while(results.next())
                                 {
                                 i++;
                                 }
                                 if(i==0)
                                 {

                                   sql="insert into COM_MST_HABITATIONS(STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE,HABITATION_CODE,HABITATION_NAME,CENSUS_VILLAGE_ALIAS_CODE) values(?,?,?,?,?,?,?)";
                                   try
                                   {
                                     ps=connection.prepareStatement(sql);
                                     ps.setInt(1,strStateCode);
                                     ps.setInt(2,strDistCode);
                                       ps.setInt(3,strBlockCode);
                                       ps.setInt(4,strPanCode);
                                       ps.setInt(5,strHabitCode);
                                       ps.setString(6,strHabitName);
                                       ps.setInt(7,strVACode);
                                       
                                     ps.executeUpdate(); 
                                       xml=sxml+"<flag>success</flag>";
                                       ps.close();
                                   }
                                   catch(Exception e)
                                   {        
                                      System.out.println("exception in the add"+ e);
                                     xml=xml+"<flag>failure</flag>";
                                   }
                                }
                               else 
                               {
                                   xml=sxml+"<flag>Record</flag>"; 
                               }
               
               
                       }
                       catch(Exception se){System.out.println("this is Habit code error" + se);}
         
          
          
          xml=xml+"</response>";
        }
        
        else if(strCommand.equals("check"))
              {
             String  sxml="<response><command>check</command>";
               try
              {
                String sql="SELECT * FROM COM_MST_HABITATIONS WHERE STATE_CODE=? AND DISTRICT_CODE=? AND  BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? AND HABITATION_NAME=? AND CENSUS_VILLAGE_ALIAS_CODE=? ";
                try
               {
                   ps=connection.prepareStatement(sql);
                   ps.setInt(1,strStateCode);
                   ps.setInt(2,strDistCode);
                     ps.setInt(3,strBlockCode);
                   ps.setInt(4,strPanCode);
                   ps.setInt(5,strHabitCode);
                     ps.setString(6,strHabitName);
                   ps.setInt(7,strVACode);
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
        
        
        // for load
        
         else if(strCommand.equals("Load"))
               {
             xml="<response><command>Load</command>";
                try
               {
                 String sql="SELECT COM_MST_STATE.STATE_CODE,COM_MST_STATE.STATE_NAME,COM_MST_DISTRICTS.DISTRICT_CODE,COM_MST_DISTRICTS.DISTRICT_NAME,COM_MST_BLOCKS.BLOCK_CODE,COM_MST_BLOCKS.BLOCK_NAME,";
                 sql=sql+ " COM_MST_PANCHAYATS.PANCHAYAT_CODE,COM_MST_PANCHAYATS.PANCHAYAT_NAME,COM_MST_CENSUS_VILLAGES.CV_ALIAS_CODE,COM_MST_CENSUS_VILLAGES.CENSUS_VILLAGE_NAME,";
                 sql=sql+ " COM_MST_HABITATIONS.HABITATION_CODE,COM_MST_HABITATIONS.HABITATION_NAME,COM_MST_HABITATIONS.CENSUS_VILLAGE_ALIAS_CODE ";
                 sql=sql+" FROM COM_MST_STATE,COM_MST_DISTRICTS,COM_MST_BLOCKS,COM_MST_PANCHAYATS,COM_MST_CENSUS_VILLAGES,COM_MST_HABITATIONS WHERE COM_MST_HABITATIONS.STATE_CODE=COM_MST_STATE.STATE_CODE AND COM_MST_HABITATIONS.DISTRICT_CODE=COM_MST_DISTRICTS.DISTRICT_CODE";
                 sql=sql+ " AND COM_MST_HABITATIONS.BLOCK_CODE=COM_MST_BLOCKS.BLOCK_CODE AND COM_MST_HABITATIONS.PANCHAYAT_CODE=COM_MST_PANCHAYATS.PANCHAYAT_CODE AND COM_MST_HABITATIONS.CENSUS_VILLAGE_ALIAS_CODE=COM_MST_CENSUS_VILLAGES.CV_ALIAS_CODE AND COM_MST_HABITATIONS.STATE_CODE=? AND";
                 sql=sql+" COM_MST_HABITATIONS.DISTRICT_CODE=? AND COM_MST_HABITATIONS.BLOCK_CODE=? AND COM_MST_HABITATIONS.PANCHAYAT_CODE=?  ORDER BY COM_MST_HABITATIONS.HABITATION_CODE";
                 try
                {
                    ps=connection.prepareStatement(sql);
                    ps.setInt(1,strStateCode);
                    ps.setInt(2,strDistCode);
                    ps.setInt(3,strBlockCode);
                    ps.setInt(4,strPanCode);
                    
                   results1=ps.executeQuery();
                    xml=xml+"<flag>success</flag>";
                    try
                    {System.out.println("Exception is:the Load");
                while(results1.next())
               { System.out.println("Exception is:the Load");
                   xml=xml+ "<StateCode>"+results1.getInt("STATE_CODE")+"</StateCode>";
                   xml=xml+ "<StateName>"+results1.getString("STATE_NAME")+"</StateName>";

                   xml=xml+ "<DistCode>"+results1.getInt("DISTRICT_CODE")+"</DistCode>";
                   xml=xml+ "<DistName>"+results1.getString("DISTRICT_NAME")+"</DistName>";

                   xml=xml+ "<BlockCode>"+results1.getInt("BLOCK_CODE")+"</BlockCode>";
                   xml=xml+ "<BlockName>"+results1.getString("BLOCK_NAME")+"</BlockName>";

                   xml=xml+ "<PanCode>"+results1.getInt("PANCHAYAT_CODE")+"</PanCode>";
                   xml=xml+ "<PanName>"+results1.getString("PANCHAYAT_NAME")+"</PanName>";

                   xml=xml+ "<HabitCode>"+results1.getInt("HABITATION_CODE")+"</HabitCode>";
                   xml=xml+ "<HabitName>"+results1.getString("HABITATION_NAME")+"</HabitName>";

                   xml=xml+ "<VACode>"+results1.getInt("CENSUS_VILLAGE_ALIAS_CODE")+"</VACode>";
                   xml=xml+ "<VAName>"+results1.getString("CENSUS_VILLAGE_NAME")+"</VAName>";
               }
                    }catch(Exception ee){System.out.println("Error in the xml of load" + ee);}
                }catch(Exception ae)
                {
                   System.out.println("Exception is:the Load" +ae);
                }
             }
             catch(Exception e)
             {        
                System.out.println("exce ****2 vv"+ e);
               xml=xml+"<flag>failure</flag>";
             }
             xml=xml+"</response>";
         }
         
        else if(strCommand.equals("Get"))
              {
             String  sxml="<response><command>Get</command>";
               try
              {
                String sql="SELECT * FROM COM_MST_HABITATIONS WHERE HABITATION_CODE=? ";
                try
               {
                   ps=connection.prepareStatement(sql);
                   
                   ps.setInt(1,strHabitCode);
                    
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
