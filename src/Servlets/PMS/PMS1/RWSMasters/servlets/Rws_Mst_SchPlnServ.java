package Servlets.PMS.PMS1.RWSMasters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Rws_Mst_SchPlnServ extends HttpServlet {
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
        
        String strcode="";
        //String strname="";
        //String xml="";
        String strCommand="";
        
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);
            strcode=request.getParameter("txtPlnId");
            System.out.println("assign....."+strcode);
            strname=request.getParameter("txtPlnName");
            System.out.println("assign...."+strname);
        }
        
        catch(Exception e) {
            System.out.println("Exception in assigning..."+e);
        }
         if(strCommand.equalsIgnoreCase("Delete")) {
            xml="<response><command>Delete</command>";
            String sql="";
            try {
            
            sql="DELETE FROM RWS_MST_SCHEME_PLANS WHERE PLAN_ID=?";
            }
            catch(Exception ae){ System.out.println("in select"+ae);}
                //ps=con.prepareStatement("DELETE FROM RWS_MST_SCHEME_TYPES WHERE SCHEME_TYPE_ID=?");
                
                try
                {
                ps=con.prepareStatement(sql);
                ps.setString(1,strcode);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag><scd>"+strcode+"</scd>";
            }
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
        else if(strCommand.equalsIgnoreCase("Add")) {
            xml="<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            /*try {
                ps=con.prepareStatement("INSERT INTO RWS_MST_SCHEME_PLANS VALUES(?,?)");
                
                ps.setString(1,strcode);
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
             {
             try 
                 {
                     cs=con.prepareCall("{call RM_SCHEME_PLANS_proc(?,?,?,?)}") ; 
                     cs.setString(1,strcode);
                     cs.setString(2,strname);
                     cs.setString(4,"insert");
                     cs.registerOutParameter(3,java.sql.Types.NUMERIC);
                     cs.execute();
                                 
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
                     System.out.println("insert exception  :"+e);
                     xml=xml+"<duplicate>no</duplicate><flag>failure</flag>";
                     }
             }              
             else
             {
               xml=xml+"<duplicate>yes</duplicate><flag>failure</flag>";
             }
            xml=xml+"</response>";
        }
        
        
        else if(strCommand.equalsIgnoreCase("novali")) {
            String sxml="<response><command>novali</command>";
            try {
                ps=con.prepareStatement("SELECT * FROM RWS_MST_SCHEME_PLANS WHERE PLAN_ID=?");
                ps.setString(1,strcode);
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
        else if(strCommand.equalsIgnoreCase("Update")) {
            xml="<response><command>Update</command>";
            
           /* try {
                
                ps=con.prepareStatement("UPDATE RWS_MST_SCHEME_PLANS SET PLAN_DESC=? WHERE PLAN_ID=?");
                ps.setString(1,strname);
                ps.setString(2,strcode);
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
            {
            try 
                {
                    cs=con.prepareCall("{call RM_SCHEME_PLANS_proc(?,?,?,?)}") ; 
                    cs.setString(1,strcode);
                    cs.setString(2,strname);
                    cs.setString(4,"update");
                    cs.registerOutParameter(3,java.sql.Types.NUMERIC);
                    cs.execute();
                                
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
               ps=con.prepareStatement("select * from RWS_MST_SCHEME_PLANS where PLAN_DESC=?");
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
