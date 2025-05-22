package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class UnfreezeEmployeeSRServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        try {

        	 LoadDriver driver=new LoadDriver();
         	con=driver.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement st = null;

        int strcode = 0, slno = 0, designation = 0, officeid = 0, statusid = 0;
        Date dtfrom = null, dtto = null;
        String dtfromsession = "", dttosession = "", statusdetail =
            "", remarks = "", deptid = "";
        //String strname="";
        String xml = "";
        String strCommand = "";
        Calendar c;

        PrintWriter out = response.getWriter();
        /*   HttpSession session=request.getSession(false);
        try
        {

            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
            System.out.println(session);

        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }*/

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("loadempedit")) {
            System.out.println("Inside employee edit details");
            xml = "<response><command>loadempedit</command>";
            try {

                //  HttpSession session=request.getSession(false);
                //   UserProfile up=null;
                //   up=(UserProfile)session.getAttribute("UserProfile");
                //   boolean flag=true;
                System.out.println("inside try");

                ps =
  con.prepareStatement("SELECT employee_id, " +
		  "  gpf_no, " +
		  "  empname, " +
		  "  Office_Grade, " +
		  "  Employee_Status_Id, " +
		  "  Office_Id, " +
		  "  Office_Name, " +
		  "  CASE " +
		  "    WHEN c.Office_Name IS NULL " +
		  "    THEN D.Other_Dept_Office_Name " +
		  "    ELSE c.Office_Name " +
		  "  END AS Office_name_final " +
		  "FROM " +
		  "  (SELECT Employee_Id, " +
		  "    Gpf_No, " +
		  "    (Employee_Name " +
		  "    ||DECODE(Employee_Initial,NULL,' ','.' " +
		  "    ||Employee_Initial)) AS Empname " +
		  "  FROM Hrm_Mst_Employees " +
		  "  WHERE employee_id=? " +
		  "  )a " +
		  "LEFT OUTER JOIN " +
		  "  (SELECT Employee_Id AS Employeeid, " +
		  "    Office_Grade, " +
		  "    Office_Id, " +
		  "    employee_status_id, " +
		  "    DEPARTMENT_ID " +
		  "  FROM hrm_emp_current_posting " +
		  "  )B " +
		  "ON a.employee_id=b.employeeid " +
		  "LEFT OUTER JOIN " +
		  "  (SELECT office_id AS offid,office_name FROM com_mst_offices " +
		  "  )c " +
		  "ON b.office_id=c.offid " +
		  "LEFT OUTER JOIN " +
		  "  ( SELECT DISTINCT OTHER_DEPT_ID, " +
		  "    Other_Dept_Office_Id , " +
		  "    Other_Dept_Office_Name " +
		  "  FROM Hrm_Mst_Other_Dept_Offices " +
		  "  ) D " +
		  "ON B.Office_Id      = D.Other_Dept_Office_Id " +
		  "AND b.DEPARTMENT_ID =d.OTHER_DEPT_ID");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";

                } else {
                    xml =
 xml + "<estatus>" + rs.getString("employee_status_id") + "</estatus>";
                    xml =
 xml + "<ename>" + rs.getString("empname") + "</ename>";
                    xml = xml + "<egpf>" + rs.getString("gpf_no") + "</egpf>";
                    xml =
 xml + "<officegrade>" + rs.getString("office_grade") + "</officegrade><offname>"+rs.getString("Office_name_final") +"</offname>";
                    
                    xml = xml + "<flag>success1</flag>";
                    
                    

                    //flag=false;
                }


            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        ///////////////////////////////////////////////
        out.println(xml);
        System.out.println(xml);
        out.close();

    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        try {

            ResourceBundle rsb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rsb.getString("Config.DSN");
            String strhostname = rsb.getString("Config.HOST_NAME");
            String strportno = rsb.getString("Config.PORT_NUMBER");
            String strsid = rsb.getString("Config.SID");
            String strdbusername = rsb.getString("Config.USER_NAME");
            String strdbpassword = rsb.getString("Config.PASSWORD");

       //     ConnectionString =
       //             strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
        //            ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        PreparedStatement ps1 = null;
        Statement st = null;

        int strcode = 0, slno = 0, designation = 0, officeid = 0;
        String statusid = "", leavetype = "";
        Date dtfrom = null, dtto = null;
        String dtfromsession = "", dttosession = "", statusdetail =
            "", remarks = "", deptid = "", officegrade = "";
        //String strname="";
        String xml = "";
        String strCommand = "";
        Calendar c;

        PrintWriter out = response.getWriter();
        /*   HttpSession session=request.getSession(false);
        try
        {

            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
            System.out.println(session);

        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }*/

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        String empstatus = "";
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);
            PreparedStatement psnew =
                con.prepareStatement("select employee_status_id from hrm_emp_current_posting where employee_id=?");
            psnew.setInt(1, strcode);
            ResultSet rsnew = psnew.executeQuery();
            while (rsnew.next()) {
                empstatus = rsnew.getString("employee_status_id");
                System.out.println("emp staus is" + empstatus);
            }

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        ////////////////
        if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("Command :" + strCommand);
            String offgrade = request.getParameter("officegrade");
            System.out.println("office grade is" + offgrade);
            try {
                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                java.util.Date dt =
                    new java.util.Date(System.currentTimeMillis());
                java.sql.Timestamp sqldt =
                    new java.sql.Timestamp(dt.getTime());
                System.out.println("Time Stamp:" + sqldt);
                int y = 0;

                st = con.createStatement();
                rs =
  st.executeQuery("select employee_id from hrm_emp_service_data where employee_id=" +
                  strcode);
                if (rs.next()) {
                    ps1 =
 con.prepareStatement("update hrm_emp_service_data set process_flow_status_id='MD' where employee_id=?");
                    ps1.setInt(1, strcode);
                    y = ps1.executeUpdate();
                    if (y > 0)
                        sendMessage(response,
                                    "Record is Unfreezed Successfully", "ok");
                    else
                        sendMessage(response, "Record is not Unfreezed", "ok");
                } else
                    sendMessage(response,
                                "Service Record not available for specified employee",
                                "ok");

            } catch (Exception e) {
                String err = "Record is not Updated. " + e.getMessage();
                try {
                    con.rollback();
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                sendMessage(response, err, "ok");

            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }

        }


        ///////////////
    }


    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerJSP_UnfreezeSR.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


