package Servlets.PMS.PMS1.RWS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Block_Servlet extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection connection=null;
    private Statement statement=null;
    private ResultSet results=null;
    private PreparedStatement ps=null;

    public void init(ServletConfig config) throws ServletException {
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

                     //      ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
        String strBlockName="";
        String strBlockTCode="";
        int strBlockACode=0;
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        try
        {System.out.println("Exception is:the check***********");
          strCommand = request.getParameter("command");   
            System.out.println("Exception is:the check" + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        try
        {
          strStateCode= Integer.parseInt(request.getParameter("StateCode"));
          strDistCode = Integer.parseInt(request.getParameter("DistCode"));
            strBlockName = request.getParameter("BlockName");
            strBlockTCode =request.getParameter("BlockTCode");
            strBlockACode =Integer.parseInt(request.getParameter("BlockACode"));
            
        }
        catch(Exception e)
        { 
            System.out.println("in getting values in block code **** "+ e);
        }
        try
        {
            strBlockCode =Integer.parseInt(request.getParameter("BlockCode"));
        }
        catch(Exception e)
        { 
            System.out.println("in getting values in block code **** "+ e);
        }
        if(strCommand.equalsIgnoreCase("Delete"))
                {
                  xml="<response><command>Delete</command>";
                  
                  String sql="delete from COM_MST_BLOCKS where STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                      ps.setInt(1,strStateCode);
                      ps.setInt(2,strDistCode);
                      ps.setInt(3,strBlockCode);
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
                  String sql="update COM_MST_BLOCKS set BLOCK_NAME=?,BLOCK_TERRAIN_CODE=?,BLOCK_ALIAS_CODE=? where STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                      ps.setInt(4,strStateCode);
                      ps.setInt(5,strDistCode);
                        ps.setInt(6,strBlockCode);
                        ps.setString(1,strBlockName);
                      ps.setString(2,strBlockTCode);
                      ps.setInt(3,strBlockACode);
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
        else if(strCommand.equalsIgnoreCase("Get"))
        {
          String sxml="<response><command>Get</command>";
            try
            {
                    String sql="SELECT * FROM COM_MST_BLOCKS WHERE   BLOCK_CODE=?";
                       try
                       {
                       ps=connection.prepareStatement(sql);
                      
                          ps.setInt(1,strBlockCode);
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
                                         String sql="SELECT STATE_CODE,DISTRICT_CODE,BLOCK_CODE FROM COM_MST_BLOCKS WHERE  STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=?";
                                            ps=connection.prepareStatement(sql);
                                            ps.setInt(1,strStateCode);
                                            ps.setInt(2,strDistCode);
                                               ps.setInt(3,strBlockCode);
                                            results=ps.executeQuery();
                                            int i=0;
                                            while(results.next())
                                            {
                                            i++;
                                            }
                                            if(i==0)
                                            {

                                              sql="insert into COM_MST_BLOCKS(STATE_CODE,DISTRICT_CODE,BLOCK_CODE,BLOCK_NAME,BLOCK_TERRAIN_CODE,BLOCK_ALIAS_CODE) values(?,?,?,?,?,?)";
                                              try
                                              {
                                                ps=connection.prepareStatement(sql);
                                                ps.setInt(1,strStateCode);
                                                ps.setInt(2,strDistCode);
                                                  ps.setInt(3,strBlockCode);
                                                  ps.setString(4,strBlockName);
                                                  ps.setString(5,strBlockTCode);
                                                  ps.setInt(6,strBlockACode);
                                                  
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
                          
                          
                                  }
                                  catch(Exception se){System.out.println("this is taluk code error" + se);}
                    
                     
                     
                     xml=xml+"</response>";
                   }

        
        else if(strCommand.equals("check"))
              {
             String  sxml="<response><command>check</command>";
               try
              {
                String sql="SELECT * FROM COM_MST_BLOCKS WHERE STATE_CODE=? AND DISTRICT_CODE=? AND  BLOCK_CODE=? AND BLOCK_NAME=? AND BLOCK_TERRAIN_CODE=? AND BLOCK_ALIAS_CODE=? ";
                try
               {
                   ps=connection.prepareStatement(sql);
                   ps.setInt(1,strStateCode);
                   ps.setInt(2,strDistCode);
                     ps.setInt(3,strBlockCode);
                     ps.setString(4,strBlockName);
                   ps.setString(5,strBlockTCode);
                   ps.setInt(6,strBlockACode);
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
                 String sql="SELECT COM_MST_STATE.STATE_CODE,COM_MST_STATE.STATE_NAME,COM_MST_DISTRICTS.DISTRICT_CODE,COM_MST_DISTRICTS.DISTRICT_NAME,HBS_MST_TERRAINS.TERRAIN_CODE,HBS_MST_TERRAINS.TERRAIN_DESC,COM_MST_BLOCKS.BLOCK_CODE,COM_MST_BLOCKS.BLOCK_NAME,COM_MST_BLOCKS.BLOCK_TERRAIN_CODE,COM_MST_BLOCKS.BLOCK_ALIAS_CODE";
                 sql=sql+" FROM COM_MST_STATE,COM_MST_DISTRICTS,HBS_MST_TERRAINS,COM_MST_BLOCKS WHERE COM_MST_BLOCKS.STATE_CODE=COM_MST_STATE.STATE_CODE AND COM_MST_BLOCKS.DISTRICT_CODE=COM_MST_DISTRICTS.DISTRICT_CODE AND COM_MST_BLOCKS.BLOCK_TERRAIN_CODE=HBS_MST_TERRAINS.TERRAIN_CODE AND COM_MST_BLOCKS.STATE_CODE=? AND COM_MST_BLOCKS.DISTRICT_CODE=? ORDER BY COM_MST_BLOCKS.BLOCK_CODE";
                 try
                {
                    ps=connection.prepareStatement(sql);
                    ps.setInt(1,strStateCode);
                    ps.setInt(2,strDistCode);
                   results=ps.executeQuery();
                    xml=xml+"<flag>success</flag>";
                while(results.next())
               { 
                   
                   xml=xml+ "<StateCode>"+results.getInt("STATE_CODE")+"</StateCode>";
                   xml=xml+ "<StateName>"+results.getString("STATE_NAME")+"</StateName>";
                   xml=xml+ "<DistCode>"+results.getInt("DISTRICT_CODE")+"</DistCode>";
                   xml=xml+ "<DistName>"+results.getString("DISTRICT_NAME")+"</DistName>";
                   xml=xml+ "<BlockCode>"+results.getInt("BLOCK_CODE")+"</BlockCode>";
                   xml=xml+ "<BlockName>"+results.getString("BLOCK_NAME")+"</BlockName>";
                   xml=xml+ "<BlockTCode>"+results.getString("BLOCK_TERRAIN_CODE").trim()+"</BlockTCode>";
                   xml=xml+ "<BlockTtype>"+results.getString("TERRAIN_DESC")+"</BlockTtype>";
                   xml=xml+ "<BlockACode>"+results.getInt("BLOCK_ALIAS_CODE")+"</BlockACode>";
               }
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
                
                System.out.println("xml is : " + xml);
                pw.write(xml);
                pw.flush();
                pw.close();
        
    }
}
