package Servlets.PMS.PMS1.RWS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Panchayat_Servlet extends HttpServlet 
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
        String strPanName="";
        int strTalukCode=0;
        int strASegCode=0;
        
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
            System.out.println("in getting values in block code **** "+ strDistCode);
            strBlockCode =Integer.parseInt(request.getParameter("BlockCode"));
            
            strPanName = request.getParameter("PanName");
            strTalukCode =Integer.parseInt(request.getParameter("TalukCode"));
            strASegCode =Integer.parseInt(request.getParameter("ASegCode"));
            
        }
        catch(Exception e)
        { 
            System.out.println("in getting values in panchayat**** "+ e);
        }
        try {
            strPanCode =Integer.parseInt(request.getParameter("PanCode"));
        }
        catch(Exception e)
        { 
            System.out.println("in getting values in panchayat code only**** "+ e);
        }
        if(strCommand.equalsIgnoreCase("Delete"))
                {
                  xml="<response><command>Delete</command>";
                  
                  String sql="delete from COM_MST_PANCHAYATS where STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                      ps.setInt(1,strStateCode);
                      ps.setInt(2,strDistCode);
                      ps.setInt(3,strBlockCode);
                      ps.setInt(4,strPanCode);
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
                  String sql="update COM_MST_PANCHAYATS set PANCHAYAT_NAME=?,TALUK_CODE=?,ASSEMBLY_SEGMENT_CODE=? where STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=?";
                  try
                  {
                    ps=connection.prepareStatement(sql);
                      ps.setInt(4,strStateCode);
                      ps.setInt(5,strDistCode);
                        ps.setInt(6,strBlockCode);
                      ps.setInt(7,strPanCode);
                        ps.setString(1,strPanName);
                      ps.setInt(2,strTalukCode);
                      ps.setInt(3,strASegCode);
                         ps.executeUpdate();        
                    xml=xml+"<flag>success</flag>";
                    ps.close();
                  }
                  catch(Exception e)
                  {        
                     System.out.println("exception in the update in panchayat"+ e);
                    xml=xml+"<flag>failure</flag>";
                  }
                  xml=xml+"</response>";
                }
                
        else if(strCommand.equalsIgnoreCase("Add"))
        {
          String sxml="<response><command>Add</command>";
                   try
                             {
                              String sql="SELECT STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE FROM COM_MST_PANCHAYATS WHERE  STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=?";
                                 ps=connection.prepareStatement(sql);
                                System.out.println("first");
                                 ps.setInt(1,strStateCode);
                           System.out.println("first" + strStateCode);
                                 ps.setInt(2,strDistCode);
                           System.out.println("sec" + strDistCode);
                                    ps.setInt(3,strBlockCode);
                           System.out.println("thr" + strBlockCode);
                           ps.setInt(4,strPanCode);
                           System.out.println("fr" + strPanCode);
                                 results=ps.executeQuery();
                                 int i=0;
                                 while(results.next())
                                 {
                                 i++;
                                 }
                                 if(i==0)
                                 {

                                   sql="insert into COM_MST_PANCHAYATS(STATE_CODE,DISTRICT_CODE,BLOCK_CODE,PANCHAYAT_CODE,PANCHAYAT_NAME,TALUK_CODE,ASSEMBLY_SEGMENT_CODE) values(?,?,?,?,?,?,?)";
                                   try
                                   {
                                     ps=connection.prepareStatement(sql);
                                     ps.setInt(1,strStateCode);
                                     ps.setInt(2,strDistCode);
                                       ps.setInt(3,strBlockCode);
                                       ps.setInt(4,strPanCode);
                                       ps.setString(5,strPanName);
                                       ps.setInt(6,strTalukCode);
                                       ps.setInt(7,strASegCode);
                                       
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
                String sql="SELECT * FROM COM_MST_PANCHAYATS WHERE STATE_CODE=? AND DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND PANCHAYAT_NAME=? AND TALUK_CODE=? AND ASSEMBLY_SEGMENT_CODE=? ";
                try
               {
                   ps=connection.prepareStatement(sql);
                   ps.setInt(1,strStateCode);
                   ps.setInt(2,strDistCode);
                     ps.setInt(3,strBlockCode);
                   ps.setInt(4,strPanCode);
                     ps.setString(5,strPanName);
                   ps.setInt(6,strTalukCode);
                   ps.setInt(7,strASegCode);
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
                  xml=sxml+"<flag>success</flag>"; 
              }
               }catch(Exception ae){System.out.println("Exception is:the check" +ae);
                  
               }
            }
            catch(Exception e)
            {        
               System.out.println("Exception in the check before insert"+ e);
              xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
        else if(strCommand.equals("Get"))
              {
             String  sxml="<response><command>Get</command>";
               try
              {
                String sql="SELECT * FROM COM_MST_PANCHAYATS WHERE PANCHAYAT_CODE=?";
                try
               {
                   ps=connection.prepareStatement(sql);
                   ps.setInt(1,strPanCode);
                    
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
                  xml=sxml+"<flag>success</flag>"; 
              }
               }catch(Exception ae){System.out.println("Exception is:the GET" +ae);
                  
               }
            }
            catch(Exception e)
            {        
               System.out.println("Exception in the check before insert"+ e);
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
                 sql=sql+ " COM_MST_TALUKS.TALUK_CODE,COM_MST_TALUKS.TALUK_NAME,HBS_MST_ASSEMBLY.ASSEMBLY_SEGMENT_CODE,HBS_MST_ASSEMBLY.ASSEMBLY_SEGMENT_DESC,";
                 sql=sql+ " COM_MST_PANCHAYATS.PANCHAYAT_CODE,COM_MST_PANCHAYATS.PANCHAYAT_NAME,COM_MST_PANCHAYATS.TALUK_CODE,COM_MST_PANCHAYATS.ASSEMBLY_SEGMENT_CODE ";
                 sql=sql+" FROM COM_MST_STATE,COM_MST_DISTRICTS,COM_MST_BLOCKS,COM_MST_TALUKS,HBS_MST_ASSEMBLY,COM_MST_PANCHAYATS WHERE COM_MST_PANCHAYATS.STATE_CODE=COM_MST_STATE.STATE_CODE AND COM_MST_PANCHAYATS.DISTRICT_CODE=COM_MST_DISTRICTS.DISTRICT_CODE";
                 sql=sql+ " AND COM_MST_PANCHAYATS.BLOCK_CODE=COM_MST_BLOCKS.BLOCK_CODE AND COM_MST_PANCHAYATS.TALUK_CODE=COM_MST_TALUKS.TALUK_CODE AND COM_MST_PANCHAYATS.ASSEMBLY_SEGMENT_CODE=HBS_MST_ASSEMBLY.ASSEMBLY_SEGMENT_CODE AND ";
                 sql=sql+" COM_MST_PANCHAYATS.STATE_CODE=? AND COM_MST_PANCHAYATS.DISTRICT_CODE=? AND COM_MST_PANCHAYATS.BLOCK_CODE=? ORDER BY COM_MST_PANCHAYATS.PANCHAYAT_CODE";
                 try
                {
                    ps=connection.prepareStatement(sql);
                    ps.setInt(1,strStateCode);
                    ps.setInt(2,strDistCode);
                    ps.setInt(3,strBlockCode);
                    
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
                   
                   xml=xml+ "<PanCode>"+results.getInt("PANCHAYAT_CODE")+"</PanCode>";
                   xml=xml+ "<PanName>"+results.getString("PANCHAYAT_NAME")+"</PanName>";
                   
                   xml=xml+ "<TalukCode>"+results.getInt("TALUK_CODE")+"</TalukCode>";
                   xml=xml+ "<TalukName>"+results.getString("TALUK_NAME")+"</TalukName>";
                   
                   xml=xml+ "<ASegCode>"+results.getInt("ASSEMBLY_SEGMENT_CODE")+"</ASegCode>";
                   xml=xml+ "<ASegName>"+results.getString("ASSEMBLY_SEGMENT_DESC")+"</ASegName>";
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
