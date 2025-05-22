package Servlets.PMS.PMS1.RWS.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;

public class schemeser extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    String xml="";
    Connection con=null;
    Statement st=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    Statement smt=null;
    int c=0;
    try 
     {
       Class.forName("oracle.jdbc.OracleDriver");
       con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","password");
       System.out.println("connected inside");
     }
     catch (ClassNotFoundException e) 
      {
       System.out.println("Connection err");
      }
      catch (SQLException e)
       {
         System.out.println("Sql err");
       }
     String cmd=request.getParameter("command");
     System.out.println(cmd);
     out.println("<html>");
     out.println("<head><title>schemeser</title></head>");
     out.println("<body>");
     if(cmd.equalsIgnoreCase("load")) 
     {
       xml="<response1><command>load</command>";
       try
       {
        String cid=request.getParameter("cmdcid").trim();
        System.out.println("Serial no is"+cid);
        smt=con.createStatement();
        rs=smt.executeQuery("select scheme_proposal_category,scheme_category_description from RWS_MST_SCHEME_PROP_CATEGORY where scheme_proposal_category='"+cid+"'");
        while(rs.next())
        {
         c++;
         xml=xml+"<CAT_ID>"+rs.getString("scheme_proposal_category").trim()+"</CAT_ID>";
         xml=xml+"<CAT_DES>"+rs.getString("scheme_category_description").trim()+"</CAT_DES>";
        }
       if(c>0)
        {
          xml=xml+"<flag1>Success</flag1>";
        }
        else 
        {
         xml=xml+"<flag1>failure</flag1>";
         }
       } 
      catch(Exception e) 
      {
        System.out.println(c);
        xml=xml+"<flag1>failure</flag1>";
        System.out.println("Err");
      } 
       System.out.println(c);
       xml=xml+"</response1>";
       out.println(xml);
       System.out.println(xml);
     }
        
     else if(cmd.equalsIgnoreCase("Add"))
     {
       String cid=request.getParameter("cmdcid").trim();
       String cdes=request.getParameter("cmddesc").trim();
       System.out.println(cid);
       System.out.println(cdes);
       xml="<response1><command>Add</command>";
       try{
            ps = con.prepareStatement("Insert into RWS_MST_SCHEME_PROP_CATEGORY(Scheme_Proposal_Category,Scheme_Category_Description) values(?,?)");
            ps.setString(1,cid);
            ps.setString(2,cdes);
            ps.executeUpdate();
            xml=xml+"<flag1>Success</flag1>";
            xml=xml+"<CAT_ID>"+cid+"</CAT_ID><CAT_DES>"+cdes+"</CAT_DES>";
            con.commit();
           }
      catch (SQLException e) 
      {
        xml=xml+"<flag1>failure</flag1>";
        System.out.println("err in add");
      } 
      xml=xml+"</response1>";
      out.println(xml);
      System.out.println(xml);
     }
                         
     else if(cmd.equalsIgnoreCase("Delete"))
     {
       String cid=request.getParameter("cmdcid").trim();
       System.out.println("Serial no is"+cid);
       String cdes=request.getParameter("cmddesc").trim();
       System.out.println(cdes);
       System.out.println("Command is"+cmd);
       xml="<response1><command>Delete</command>";
       try
        {
          smt=con.createStatement();
          smt.executeUpdate("Delete from RWS_MST_SCHEME_PROP_CATEGORY where Scheme_Proposal_Category='"+cid+"'");
          xml=xml+"<flag1>Success</flag1><CAT_ID>"+cid+"</CAT_ID><CAT_DES>"+cdes+"</CAT_DES>";
          con.commit();         
        }
      catch (SQLException e)
      {
        xml=xml+"<flag1>failure</flag1>";
        System.out.println("err in delete");
      }
      catch (Exception e) 
       {
         System.out.println("sql exception");
       }
      xml=xml+"</response1>";
      out.println(xml);
      System.out.println(xml);
    }

     else if(cmd.equalsIgnoreCase("Update"))
      {
        System.out.println("Command is"+cmd);
         xml="<response1><command>Update</command>";
         try{    
              String cid=request.getParameter("cmdcid").trim();
              String cdes=request.getParameter("cmddesc").trim();
              System.out.println(cid);
              System.out.println(cdes);               
              smt=con.createStatement();
              int d= smt.executeUpdate("update RWS_MST_SCHEME_PROP_CATEGORY set Scheme_Category_Description='"+cdes+"'"+" where Scheme_Proposal_Category='"+cid+"'");
              System.out.println("After updating");
              if(d>=1)      {
                 xml=xml+"<flag1>Success</flag1>";
                 con.commit();
                }
             }
         catch (SQLException e)
         {
           xml=xml+"<flag1>failure</flag1>";
           System.out.println("err in add");
          } 
        xml=xml+"</response1>";
        out.println(xml);
        System.out.println(xml);
      }
 out.println("<p>The servlet has received a GET. This is the reply.</p>");
 out.println("</body></html>");
 out.close();
   }
}

        
      