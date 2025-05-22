package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

import java.sql.*;

import java.util.ResourceBundle;

public class Leave_typeserv1 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        response.setHeader("cache-control", "no-cache");
        out.println("<html>");
        out.println("<head><title>LoadEmpCodeServlet</title></head>");
        out.println("<body>");
        String xml = "<response1><command>LoadSN</command>";
        try {

        	LoadDriver driver=new LoadDriver();
        	con=driver.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }
        try {

            st = con.createStatement();
            rs =
  st.executeQuery("SELECT LEAVE_TYPE_ID FROM HRM_LEAVE_TYPES GROUP BY LEAVE_TYPE_ID HAVING LEAVE_TYPE_ID=(select max(LEAVE_TYPE_ID) from HRM_LEAVE_TYPES)");
            System.out.print("rs");
            while (rs.next()) {
                int no = 0;
                no = rs.getInt(1);
                System.out.println(no);
                System.out.println("ok");

                xml = xml + "<flag1>Success</flag1>";
                no = no + 1;
                xml = xml + "<code>" + no + "</code>";

            }
            rs.close();
            response.setHeader("cache-control", "no-cache");
        } catch (Exception e) {
            System.out.println("ERR" + e);
            xml = xml + "<flag1>Failure</flag1>";
        }
        xml = xml + "</response1>";
        out.println(xml);
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        System.out.println(xml);
        out.close();
    }
}
