package Servlets.PMS.PMS1.RWSMasters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RM_RIVER_BASINS extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;
    private Connection conn=null;
    private PreparedStatement ps=null;
    private CallableStatement cs=null;
    private ResultSet rs=null;

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
        try {
            
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
                         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

                          Class.forName(strDriver.trim());
                          conn=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e) {
            System.out.println("Exception in connection..."+e);
        }

    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control","no-cache");
        String svtid="";
        String svtname="";
        String action=request.getParameter("action");
        System.out.println("action");
        if(action.equalsIgnoreCase("adding"))
        { 
            System.out.println("Insertion");
            svtid=request.getParameter("sid");
            svtname=request.getParameter("sname");
            String sql="";
            String xml="<response>";
            /*sql="insert into RWS_MST_RIVER_BASINS values(?,?)";
            try
            {
                System.out.println(xml);
                ps=conn.prepareStatement(sql);
                ps.setString(1,svtid);
                ps.setString(2,svtname);
                int y=ps.executeUpdate();
                System.out.println(y);
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) 
            {
                System.out.println("insert exception  :"+e); 
                xml=xml+"<flag>failure</flag>";
                
            }*/
             try 
             {
                 cs=conn.prepareCall("{call RM_RIVER_BASINS(?,?,?,?)}") ; 
                 cs.setString(1,svtid);
                 cs.setString(2,svtname);
                 cs.setString(4,"insert");
                 cs.registerOutParameter(3,java.sql.Types.NUMERIC);
                              cs.execute();
                             
                              int errcode=cs.getInt(3);
                              System.out.println("SQLCODE:::"+errcode);
                              if(errcode!=0)
                             {
                                     xml=xml+"<flag>failure</flag>";
                             }
                             else
                                 xml=xml+"<flag>success</flag>";
             }
             catch(Exception e) 
             {
                 System.out.println("insert exception  :"+e); 
                 xml=xml+"<flag>failure</flag>";  
             }
        xml=xml+"</response>";
        System.out.println("xml is:"+xml);
        out.write(xml);
        out.flush();
        out.close();
        }
        else if(action.equalsIgnoreCase("dup"))
        {
                System.out.println("DUPLICATION");
                svtid=request.getParameter("sid");
                String sql="";                
                String xml="<response>";
                sql="select RIVER_BASIN_ID from RWS_MST_RIVER_BASINS where RIVER_BASIN_ID=?";
                
                try
                {
                    System.out.println(sql);
                    ps=conn.prepareStatement(sql);
                    System.out.println(sql);
                    ps.setString(1,svtid);                                         
                    rs=ps.executeQuery();
                    if(rs.next())
                    {  
                       System.out.println("Duplication occur");
                       xml=xml+"<flag>success</flag>";}
                    else
                     {
                       System.out.println("no dup there");
                       xml=xml+"<flag>failure</flag>";
                     } 
                }
                catch(Exception e) 
                {
                    System.out.println("duplication exc:"+e); 
                    xml=xml+"<flag>failure</flag>";
                }
            xml=xml+"</response>";
            System.out.println("xml is:"+xml);
            out.write(xml);
            out.flush();
            out.close();
                  
        }
        else if(action.equalsIgnoreCase("dup2"))
        {
                System.out.println("DUPLICATION");
                svtname=request.getParameter("sid");
                String sql="";                
                String xml="<response>";
                sql="select RIVER_BASIN_NAME from RWS_MST_RIVER_BASINS where RIVER_BASIN_NAME=?";
                
                try
                {
                    System.out.println(sql);
                    ps=conn.prepareStatement(sql);
                    System.out.println(sql);
                    ps.setString(1,svtname);                                         
                    rs=ps.executeQuery();
                    if(rs.next())
                    {  
                       System.out.println("Duplication occur");
                       xml=xml+"<flag>success</flag>";}
                    else
                     {
                       System.out.println("no dup there");
                       xml=xml+"<flag>failure</flag>";
                     } 
                }
                catch(Exception e) 
                {
                    System.out.println("duplication exc:"+e); 
                    xml=xml+"<flag>failure</flag>";
                }
            xml=xml+"</response>";
            System.out.println("xml is:"+xml);
            out.write(xml);
            out.flush();
            out.close();
                  
        }
        else if(action.equalsIgnoreCase("deleting"))
        {
                System.out.println("Deletion");
                svtid=request.getParameter("sid");
                String sql="";
                String xml="<response>";
                sql="delete from RWS_MST_RIVER_BASINS where RIVER_BASIN_ID=?";
                try
                {
                    System.out.println(svtid);
                    ps=conn.prepareStatement(sql);
                    ps.setString(1,svtid);                    
                    int t=ps.executeUpdate();
                    System.out.println(t);
                    xml=xml+"<flag>success</flag>";
                }
                catch(Exception e) 
                {
                    System.out.println("delete exp:"+e); 
                    xml=xml+"<flag>failure</flag>";
                    
                }
            xml=xml+"</response>";
            System.out.println("xml is:"+xml);
            out.write(xml);
            out.flush();
            out.close();
                  
        }
        else if(action.equalsIgnoreCase("updating"))
        {
                System.out.println("Update");
                svtid=request.getParameter("sid");
                svtname=request.getParameter("sname");
                System.out.println(request.getParameter("sname"));
                String sql="";
                String xml="<response>";
              /*sql="update RWS_MST_RIVER_BASINS set RIVER_BASIN_NAME=? where RIVER_BASIN_ID=?";
                try
                {
                    System.out.println(svtid + "  " + svtname);
                    ps=conn.prepareStatement(sql);
                    ps.setString(1,svtname);
                    ps.setString(2,svtid);
                    int s=ps.executeUpdate();
                    System.out.println(s);
                    xml=xml+"<flag>success</flag>";
                }
                catch(Exception e) 
                {
                    System.out.println("up exc:"+e); 
                    xml=xml+"<flag>failure</flag>";
                    
                }*/
               try 
               {
                   cs=conn.prepareCall("{call RM_RIVER_BASINS(?,?,?,?)}") ; 
                   cs.setString(1,svtid);
                   cs.setString(2,svtname);
                   cs.setString(4,"update");
                   cs.registerOutParameter(3,java.sql.Types.NUMERIC);
                                cs.execute();
                               
                                int errcode=cs.getInt(3);
                                System.out.println("SQLCODE:::"+errcode);
                                if(errcode!=0)
                               {
                                       xml=xml+"<flag>failure</flag>";
                               }
                               else
                                   xml=xml+"<flag>success</flag>";
               }
            catch(Exception e) 
            {
                System.out.println("up exc:"+e); 
                xml=xml+"<flag>failure</flag>";
                
            }

            xml=xml+"</response>";
            System.out.println("xml is:"+xml);
            out.write(xml);
            out.flush();
            out.close();                  
        }
        else
        System.out.println("No action performed");
        
    }    
}
