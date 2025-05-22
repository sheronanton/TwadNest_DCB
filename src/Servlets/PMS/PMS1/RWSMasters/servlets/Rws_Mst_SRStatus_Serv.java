package Servlets.PMS.PMS1.RWSMasters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Rws_Mst_SRStatus_Serv extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection con=null;
    private ResultSet rs=null;
    private CallableStatement cs=null;
    private PreparedStatement ps=null;
    private int valid_2nd_field=0;
    private String xml="";
    private String strname="";
    public void init(ServletConfig config) throws ServletException {
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

                    //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

                          Class.forName(strDriver.trim());
                          con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e) {
            System.out.println("Exception in connection..."+e);
        }
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        
        int strcode=0;
       // String strname="";
        //String xml="";
        String strCommand="";
        
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);
           // strcode=Integer.parseInt(request.getParameter("txtSRStatusid"));
            System.out.println("assign....."+strcode);
            strname=request.getParameter("txtSRStatus");
            System.out.println("assign...."+strname);
        }
        
        catch(Exception e) {
            System.out.println("Exception in assigning..."+e);
        }
        
        if(strCommand.equalsIgnoreCase("Add")) {
            xml="<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            /*try {
                ps=con.prepareStatement("insert into RWS_MST_SR_STATUS values(?,?)");
                System.out.println(ps);
                ps.setInt(1,strcode);
                ps.setString(2,strname);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
            
                 System.out.println("catch........"+e);
                xml=xml+"<flag>failure</flag>";
            }*/
             valid_2nd_field=0;
             validate_2ndfield();
             if(valid_2nd_field==1)
             {       int id=0;
             try 
                 {
                     cs=con.prepareCall("{call RM_SR_STATUS_PROC(?,?,?,?)}") ; 
                     cs.setInt(1,strcode);
                     cs.setString(2,strname);
                     cs.setString(4,"insert");
                         cs.registerOutParameter(3,java.sql.Types.NUMERIC);
                         cs.registerOutParameter(1,java.sql.Types.NUMERIC);            
                     cs.execute();
                     
                     int errcode=cs.getInt(3);
                     id=cs.getInt(1);
                     System.out.println(id);
                     System.out.println("SQLCODE:::"+errcode);
                     if(errcode!=0)
                     {                   
                       xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>failure</flag>";                          
                     }
                     else
                       xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>success</flag>";
                     }
                     catch(Exception e)
                     {
                     System.out.println("insert exception fg :"+e);
                     xml=xml+"<num>"+id+"</num><duplicate>no</duplicate><flag>failure</flag>";
                     }
             }              
             else
             {
               xml=xml+"<duplicate>yes</duplicate><flag>failure</flag>";
             }
            xml=xml+"</response>";
        }
        
        
        else if(strCommand.equalsIgnoreCase("Delete")) {
            xml="<response><command>Delete</command>";
            strcode=Integer.parseInt(request.getParameter("txtSRStatusid"));
            try {
                ps=con.prepareStatement("delete from RWS_MST_SR_STATUS where SR_RESERVOIR_STATUS_ID=?");
                ps.setInt(1,strcode);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag><scd>"+strcode+"</scd>";
            }
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
        else if(strCommand.equalsIgnoreCase("Update")) {
            xml="<response><command>Update</command>";
            
            /*try {
                
                ps=con.prepareStatement("update RWS_MST_SR_STATUS set SR_RESERVOIR_STATUS=? where SR_RESERVOIR_STATUS_ID=?");
                ps.setString(1,strname);
                ps.setInt(2,strcode);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }*/
             valid_2nd_field=0;
             validate_2ndfield();
             if(valid_2nd_field==1)
             {      strcode=Integer.parseInt(request.getParameter("txtSRStatusid"));
             try 
                 {
                     cs=con.prepareCall("{call RM_SR_STATUS_PROC(?,?,?,?)}") ; 
                     cs.setInt(1,strcode);
                     cs.setString(2,strname);
                     cs.setString(4,"update");                     
                    
                     cs.registerOutParameter(3,java.sql.Types.NUMERIC);
                     cs.registerOutParameter(1,java.sql.Types.NUMERIC);
                         cs.execute();
                     int id=cs.getInt(1);    
                     int errcode=cs.getInt(3);
                     System.out.println("SQLCODE:::"+errcode);
                     if(errcode!=0)
                     {
                       xml=xml+"<duplicate>no</duplicate><flag>failure</flag>";                          
                     }
                     else
                       xml=xml+"<duplicate>no</duplicate><flag>success</flag>";
                     }
                     catch(Exception e)
                     {
                     System.out.println("update exception  :"+e);
                     xml=xml+"<duplicate>no</duplicate><flag>failure</flag>";
                     }
             }              
             else
             {
               xml=xml+"<duplicate>yes</duplicate><flag>failure</flag>";
             }
            xml=xml+"</response>";
        }
        
        System.out.println("xml is:"+xml);
        out.write(xml);
        out.flush();
        out.close();
    }
private void validate_2ndfield() 
    {
               try
               {
                   ps=con.prepareStatement("select * from RWS_MST_SR_STATUS where SR_RESERVOIR_STATUS=?");
                   ps.setString(1,strname);
                   rs=ps.executeQuery();
                   if(!rs.next())
                   {
                   valid_2nd_field=1;
                   }
               }
               catch(Exception e)
               {
                   System.out.println("catch...."+e);
               }
    }
}
