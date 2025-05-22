package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class ServletDisplayIssue extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection connection=null;
    private PreparedStatement ps=null;
    private ResultSet res=null;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String xml="";
        
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

              //         ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                       ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 

                        Class.forName(strDriver.trim());
                        connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
          
        }
        catch(Exception e)
        {
        System.out.println("Exception in openeing connection:"+e);
        }  
        
        int issue=0;
        try
        {
        issue=Integer.parseInt(request.getParameter("IssueId"));
        System.out.println("issue id is"+issue);
        }
        catch(Exception e) {
            System.out.println("Number Format Exception"+e);
        }
        
        try 
        {
            String sql1="select a.ISSUE_REPORTED_DATE,a.ISSUE_PRIORITY,b.MAJOR_SYSTEM_ID,b.major_system_desc,c.MINOR_SYSTEM_ID,c.minor_system_desc,a.ISSUE_TITLE,a.ISSUE_DESC,a.ISSUE_STATUS,a.REPORTED_BY_USER_ID,a.issue_solution,a.office_id from HLP_ISSUE_REQUESTS a,sec_mst_major_systems b,sec_mst_minor_systems c where a.major_system_id=b.major_system_id and a.minor_system_id=c.minor_system_id and a.issue_id=?";
            ps=connection.prepareStatement(sql1);
            ps.setInt(1,issue);
            res=ps.executeQuery();
            System.out.println("resultset is"+res);
           
            if(res.next()) 
            {
                java.sql.Date DateOfFormation = res.getDate("ISSUE_REPORTED_DATE");  
                String DateToBeDisplayed="";
                if(DateOfFormation==null)
                {
                    DateToBeDisplayed="Not Specified";
                }
                else
                {
                    try
                    {
                        java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                        DateToBeDisplayed=sdf.format(DateOfFormation);
                    }
                    catch(Exception e)
                    {
                        System.out.println("error while formatting date : " + e);
                        DateToBeDisplayed="Not Specified";
                    }
                }                
              System.out.println("inner"); 
                response.setContentType("text/xml");
               // xml="<response><flag>success</flag><reportdate>"+DateToBeDisplayed+"</reportdate><issuepriority>"+res.getString("issue_priority")+"</issuepriority><majorsystem>"+res.getString("major_system_id")+"</majorsystem><majorsystemdesc>"+res.getString("major_system_desc")+"</majorsystemdesc><minorsystemid>"+res.getString("minor_system_id")+"</minorsystemid><minorsystem>"+res.getString("minor_system_desc")+"</minorsystem><issuetitle>"+res.getString("issue_title")+"</issuetitle><issuedesc>"+res.getString("issue_desc")+"</issuedesc><issuestatus>"+res.getString("issue_status")+"</issuestatus><userid>"+res.getString("REPORTED_BY_USER_ID")+"</userid><solution>"+res.getString("Issue_solution")+"</solution>";
                xml="<response><flag>success</flag><reportdate>"+DateToBeDisplayed+"</reportdate><issuepriority>"+res.getString("issue_priority")+"</issuepriority><majorsystem>"+res.getString("major_system_id")+"</majorsystem><majorsystemdesc>"+res.getString("major_system_desc")+"</majorsystemdesc><minorsystemid>"+res.getString("minor_system_id")+"</minorsystemid><minorsystem>"+res.getString("minor_system_desc")+"</minorsystem><issuetitle>"+res.getString("issue_title")+"</issuetitle><issuedesc>"+res.getString("issue_desc")+"</issuedesc><issuestatus>"+res.getString("issue_status")+"</issuestatus><userid>"+res.getString("REPORTED_BY_USER_ID")+"</userid><solution>"+res.getString("Issue_solution")+"</solution>";
                int officeid=res.getInt("office_id");
                System.out.println("office id:"+officeid);
                String userid=res.getString("REPORTED_BY_USER_ID");
                String officename="",officename1="",empname="",username="";
                int Emp_Id=0;
                String User="";
                try{
                          { ps=connection.prepareStatement("select employee_id from sec_mst_users where user_id=?");
                           ps.setString(1,userid);
                           res=ps.executeQuery();
                           if(res.next())
                           {
                               Emp_Id=res.getInt("employee_id");
                               System.out.println("employee_id:"+Emp_Id);
                           }
                          
                      // ps=connection.prepareStatement("select employee_name||decode(employee_initial,null,' ','.'||employee_initial) employee_name from hrm_mst_employees where employee_id=?");
                           ps=connection.prepareStatement("select "
                           		+ "case"
                           		+ "	when employee_initial is null then employee_name"
                           		+ "	when employee_initial is not null then employee_initial||'.'||employee_name"
                           		+ "	end"
                           		+ " employee_name from hrm_mst_employees where employee_id =?");
                           
                           ps.setInt(1,Emp_Id);
                       res=ps.executeQuery();
                       if(res.next())
                       {
                           empname=res.getString("employee_name");
                           System.out.println("emp name:"+empname);
                       }
                         /* 
                           ps=connection.prepareStatement("select a.office_name from com_mst_offices a,hrm_emp_current_posting b where a.office_id=b.office_id and  b.employee_id=?");
                           ps.setInt(1,Emp_Id);
                           res=ps.executeQuery();
                           if(res.next())
                           {
                               officename=res.getString("office_name");
                               System.out.println("office name:"+officename);
                           }
                           */
                          
                            ps=connection.prepareStatement("select a.office_name from com_mst_offices a where a.office_id=?");
                            ps.setInt(1,officeid);
                            res=ps.executeQuery();
                            if(res.next())
                            {
                                officename=res.getString("office_name");
                                System.out.println("office name:"+officename);
                            }
                          }
                          
                       ps=connection.prepareStatement("select user_id from SEC_MST_OTHER_USERS_PROFILE where user_id=?");
                       ps.setString(1,userid);
                       res=ps.executeQuery();
                       if(res.next())
                       {
                           User=res.getString("user_id");
                           System.out.println("user_id:"+User);
                       }
                       
                       ps=connection.prepareStatement("select user_name from SEC_MST_OTHER_USERS_PROFILE where user_id=?");
                       ps.setString(1,User);
                       res=ps.executeQuery();
                       if(res.next())
                       {
                       empname=res.getString("user_name");
                       System.out.println("User Name:"+empname);
                       }
                      
                       
                        ps=connection.prepareStatement("select a.office_name from SEC_MST_OTHER_USERS_PROFILE a where a.user_id=?");
                        ps.setString(1,User);
                        res=ps.executeQuery();
                        if(res.next())
                        {
                            officename=res.getString("office_name");
                            System.out.println("office name:"+officename);
                        }
                     /*  else
                       {ps=connection.prepareStatement("select user_name from SEC_MST_OTHER_USERS_PROFILE where user_id=?");
                           User=res.getString("user_name");
                           System.out.println("user_name:"+User);
                           
                          
                           
                       }*/
                   }
                   catch(Exception e){System.out.println("Error in Office Name:"+e);}
                   if(empname!=null)
                   {
                   
                xml=xml+"<empname>"+empname+"</empname><officename>"+officename+"</officename></response>";
                   }
                   else if(officeid==0) {
                       xml=xml+"<empname>"+username+"</empname><officename>"+officename+"</officename></response>";
                   }
                
            }
            
            else {
                response.setContentType("text/xml");
                xml="<response><flag>failure</flag></response>";
            }
            
        }catch(Exception e) {
            System.out.println("Exception in try "+e);    
        }
        out.write(xml);
        System.out.println("the xml is"+xml);
        out.close();
    }
}
