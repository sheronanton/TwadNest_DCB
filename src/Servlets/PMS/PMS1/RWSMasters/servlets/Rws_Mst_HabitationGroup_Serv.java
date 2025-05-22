package Servlets.PMS.PMS1.RWSMasters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Rws_Mst_HabitationGroup_Serv extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection con=null;
    private ResultSet rs=null;
    private PreparedStatement ps=null;
    

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

                      //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
        int strcode=0;
        String strname="";
        String xml="";
        String strCommand="";
        
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);
            strname=request.getParameter("txtHabitationGroup");
            System.out.println("assign Name....::"+strname);
            strcode=Integer.parseInt(request.getParameter("txtHabitationGroupid"));
            System.out.println("assign..... Code::"+strcode);
            
        }
        
        catch(Exception e) {
            System.out.println("Exception in assigning..."+e);
        }
        
        if(strCommand.equalsIgnoreCase("Add")) {
            xml="<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            try
            {
                System.out.println("adddddddd::"+strcode+" "+strname);
             CallableStatement cs=con.prepareCall("{call RWS_MST_HABITATIONGP_GENID (?,?,?)}" );
            //cs.setInt(1,strcode);
             cs.setString(2,strname);
             cs.registerOutParameter(1,java.sql.Types.NUMERIC);
             cs.registerOutParameter(3,java.sql.Types.NUMERIC);
             cs.execute();
             int no=cs.getInt(1);
             int errcode=cs.getInt(3);
             System.out.println("SQLCODE:::"+errcode);
             cs.close();
             System.out.println("Gen Id::"+no);
             if(errcode!=0)
            {
                    xml=xml+"<flag>failure</flag>";
            }
             else if(no>0)
             {
             
             xml=xml+"<flag>success</flag><genid>"+no+"</genid>";
             }
             else {
                 xml=xml+"<flag>success</flag><genid>1</genid>";
             }
            
            if(errcode!=0)
            {
                xml=xml+"<flag>failure</flag>";
            }
            
          /*  try {
                ps=con.prepareStatement("insert into RWS_MST_TREATMENTPLANT_TYPES values(?,?)");
                System.out.println(ps);
                ps.setInt(1,strcode);
                ps.setString(2,strname);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";*/
            }
            catch(Exception e) {
            
                 System.out.println("catch........"+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
        
        else if(strCommand.equalsIgnoreCase("novali")) {
            String sxml="<response><command>novali</command>";
            try {
                ps=con.prepareStatement("select * from RWS_MST_HABITATION_GROUP where HABITATION_GROUP_ID=?");
                ps.setInt(1,strcode);
                rs=ps.executeQuery();
                int i=0;
                while(rs.next()) {
                    i++;
                }
                if(i==0)
                {
                xml=sxml+"<flag>success</flag>";
                }
                else {
                    xml=sxml+"<flag>failure</flag>";
                }
            }
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            }
            
            
        else if(strCommand.equalsIgnoreCase("novalitype")) {
            String sxml="<response><command>novalitype</command>";
            try {
               System.out.println("novali name:::"+strname);
                ps=con.prepareStatement("select * from RWS_MST_HABITATION_GROUP where HABITATION_GROUP=?");
                ps.setString(1,strname);
                rs=ps.executeQuery();
                int i=0;
              while(rs.next()) {
                    i++;
                    System.out.println("\nget no"+rs.getInt(1));
                }
                if(i==0)
                {
                xml=sxml+"<flag>success</flag>";
                }
                else {
                    xml=sxml+"<flag>failure</flag>";
                }
            }
            catch(Exception e) {
                System.out.println("type duplicate :catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            }
            
        
        
        else if(strCommand.equalsIgnoreCase("Delete")) {
            xml="<response><command>Delete</command>";
            try {
                ps=con.prepareStatement("delete from RWS_MST_HABITATION_GROUP where HABITATION_GROUP_ID=?");
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
            
            try {
                
                ps=con.prepareStatement("update RWS_MST_HABITATION_GROUP set HABITATION_GROUP=? where HABITATION_GROUP_ID=?");
                ps.setString(1,strname);
                ps.setInt(2,strcode);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
       
        
        System.out.println("xml is:"+xml);
        out.write(xml);
        out.flush();
        out.close();
    }
    }
