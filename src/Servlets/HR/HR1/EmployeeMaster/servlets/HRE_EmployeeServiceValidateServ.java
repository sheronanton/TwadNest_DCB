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

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class HRE_EmployeeServiceValidateServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

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

        PrintWriter out = response.getWriter();
        String tablename = "";

        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                String xml =
                    "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        //HttpSession session=request.getSession(false);
        // UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");

        //  System.out.println("user id::"+empProfile.getEmployeeId());
        //int empid=empProfile.getEmployeeId();
        // System.out.println("session identifier:"+session.hashCode());
        int l = session.getId().length();
        l = l - 10;

        tablename = "TEMPSR_" + session.getId().substring(l);
        //tablename="TEMPSR"+empid;
        System.out.println("Temp table Name:::" + tablename);
        int strcode = 0, slno = 0, designation = 0, officeid = 0;
        String statusid = "";
        Date dtfrom = null, dtto = null;
        String dtfromsession = "", dttosession = "", statusdetail =
            "", remarks = "", deptid = "";
        //String strname="";
        
        
        boolean flag22 = false;
        String xml = "";
        String strCommand = "";
        Calendar c;

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");


        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            if (strCommand.equalsIgnoreCase("loademp") ||
                strCommand.equalsIgnoreCase("test") ||
                strCommand.equalsIgnoreCase("LastDate")) {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Code::" + strcode);
            } else if (strCommand.equalsIgnoreCase("Add") ||
                       strCommand.equalsIgnoreCase("Update")) {

                if (strCommand.equalsIgnoreCase("Update")) {
                    slno = Integer.parseInt(request.getParameter("txtSNo"));

                }

                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                dtfromsession = request.getParameter("optDateFrom");
                dttosession = request.getParameter("optDateTo");
                String[] sd = request.getParameter("txtDateFrom").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                dtfrom = new Date(d.getTime());
                sd = request.getParameter("txtDateTo").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                d = c.getTime();
                dtto = new Date(d.getTime());


                designation =
                        Integer.parseInt(request.getParameter("cmbDesignation"));
                officeid = Integer.parseInt(request.getParameter("cmbPlace"));
                deptid = request.getParameter("txtdeptid");
                deptid = (deptid == null) ? "TWAD" : deptid;
                statusid = request.getParameter("cmbStatus");
                statusdetail = request.getParameter("txtDetail");
                remarks = request.getParameter("txtRemark");


                System.out.println("assign..... dt from session::" +
                                   dtfromsession);
                System.out.println("assign..... dt to session::" +
                                   dttosession);
                System.out.println("assign..... dt from::" + dtfrom);
                System.out.println("assign..... dt to::" + dtto);


            } else if (strCommand.equalsIgnoreCase("Delete")) {
                slno = Integer.parseInt(request.getParameter("txtSNo"));
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
            }


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("loademp")) {
            xml = "<response><command>loademp</command>";
            try {
                //con.setAutoCommit(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != strcode) {
                    int OfficeId = 0;
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            int offid = rs.getInt("OFFICE_ID");
                            if (offid != OfficeId) {
                                //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                xml = xml + "<flag>failure1</flag>";
                                flag = false;
                            }
                        } else {
                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                            xml = xml + "<flag>failure2</flag>";
                            flag = false;
                        }

                    } else {
                        xml = xml + "<flag>failure3</flag>";
                        flag = false;
                    }
                } else {

                    //xml=xml+"<flag>failure4</flag>";
                    //flag=false;
                }

                if (flag) {
                    ps =
  con.prepareStatement("select EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME,DATE_OF_BIRTH,GPF_NO from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {

                        xml =
 xml + "<ename>" + rs.getString("EMPLOYEE_NAME") + "</ename>";
                        if (rs.getDate("DATE_OF_BIRTH") != null) {
                            String[] sd =
                                rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<edob>" + od + "</edob>";
                        } else {
                            xml = xml + "<edob>-</edob>";
                        }
                        xml =
 xml + "<egpf>" + rs.getLong("GPF_NO") + "</egpf>";
                        rs.close();
                        ps.close();

                        String sql = "";
                        if (request.getParameter("param") == null) {
                            try {
                                sql = "drop table " + tablename;
                                ps = con.prepareStatement(sql);
                                ps.executeUpdate();
                                System.out.println("table droped");
                            } catch (Exception e) {
                                System.out.println("drop table error:" + e);
                            }
                            sql =
 "create  TABLE  " + tablename + " as (select * from  HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=" +
   strcode + " )";
                            System.out.println(sql);
                            System.out.println("Temporary Table Name1:" +
                                               tablename);
                            st = con.createStatement();
                            //ps.setInt(1,strcode);
                            st.execute(sql);
                            System.out.println("temporary table created");
                        }

                        /*  sql="select * from ( " +
                     " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID,a.OFFICE_ID, " +
                     " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
                     " HRM_MST_DESIGNATIONS.DESIGNATION,COM_MST_OFFICES.OFFICE_NAME,HRM_MST_EMPLOYEE_STATUS.EMPLOYEE_STATUS_DESC, " +
                     " a.OFFICE_DEPT_ID from "+tablename +"  a,HRM_MST_DESIGNATIONS,COM_MST_OFFICES,HRM_MST_EMPLOYEE_STATUS    where a.EMPLOYEE_ID=? and COM_MST_OFFICES.OFFICE_ID=a.OFFICE_ID and HRM_MST_EMPLOYEE_STATUS.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   and   HRM_MST_DESIGNATIONS.DESIGNATION_ID=a.DESIGNATION_ID  and a.OFFICE_DEPT_ID ='TWAD' " +
                     " union all " +
                     " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION, " +
                     " a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID, " +
                     " a.OFFICE_ID, a.EMPLOYEE_STATUS_ID, a.STATUS_DETAIL,a.REMARKS, HRM_MST_DESIGNATIONS.DESIGNATION, HRM_MST_OTHER_DEPT_OFFICES.OTHER_DEPT_OFFICE_NAME as OFFICE_NAME, HRM_MST_EMPLOYEE_STATUS.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID  " +
                     " from "+tablename +"  a,HRM_MST_DESIGNATIONS,HRM_MST_OTHER_DEPT_OFFICES,HRM_MST_EMPLOYEE_STATUS    where a.EMPLOYEE_ID=? and HRM_MST_OTHER_DEPT_OFFICES.OTHER_DEPT_OFFICE_ID=a.OFFICE_ID and HRM_MST_EMPLOYEE_STATUS.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   and   HRM_MST_DESIGNATIONS.DESIGNATION_ID=a.DESIGNATION_ID  and a.OFFICE_DEPT_ID <> 'TWAD' " +
                     ") order by DATE_FROM";*/


                        sql =
 "select * from ( " + " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID,a.OFFICE_ID, " +
   " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
   " b.DESIGNATION,c.OFFICE_NAME,d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID,a.PROCESS_FLOW_STATUS_ID " +
   " from " + tablename + "  a" +
   " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID  " +
   " left outer join COM_MST_OFFICES c on c.OFFICE_ID=a.OFFICE_ID " +
   " left outer join HRM_MST_EMPLOYEE_STATUS d on   d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   " +
   " where a.EMPLOYEE_ID=? and a.OFFICE_DEPT_ID ='TWAD' " + " union all " +
   " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION, " +
   " a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID, " +
   " a.OFFICE_ID, a.EMPLOYEE_STATUS_ID, a.STATUS_DETAIL,a.REMARKS, b.DESIGNATION, c.OTHER_DEPT_OFFICE_NAME as OFFICE_NAME, d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID,a.PROCESS_FLOW_STATUS_ID  " +
   " from " + tablename + "  a " +
   " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID " +
   " left outer join HRM_MST_OTHER_DEPT_OFFICES c on c.OTHER_DEPT_OFFICE_ID=a.OFFICE_ID and c.OTHER_DEPT_ID=a.OFFICE_DEPT_ID " +
   " left outer join HRM_MST_EMPLOYEE_STATUS d on  d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID " +
   "where a.EMPLOYEE_ID=?  and a.OFFICE_DEPT_ID <> 'TWAD' " + " union all " +
   " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID,a.OFFICE_ID, " +
   " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
   " b.DESIGNATION,c.OFFICE_NAME,d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID,a.PROCESS_FLOW_STATUS_ID " +
   " from " + tablename + "  a" +
   " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID  " +
   " left outer join COM_MST_OFFICES c on c.OFFICE_ID=a.OFFICE_ID " +
   " left outer join HRM_MST_EMPLOYEE_STATUS d on   d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   " +
   " where a.EMPLOYEE_ID=? and a.OFFICE_DEPT_ID is null " +
   ") order by DATE_FROM,DATE_FROM_SESSION desc";
                        //") order by SERVICE_LIST_SLNO";

                        System.out.println(sql);
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, strcode);
                        ps.setInt(2, strcode);
                        ps.setInt(3, strcode);
                        rs = ps.executeQuery();
                        int serno = 0;
                        while (rs.next()) {
                            System.out.println(rs.getInt("DESIGNATION_ID") +
                                               "->" +
                                               rs.getString("DESIGNATION") +
                                               ";" + rs.getInt("OFFICE_ID") +
                                               "->" +
                                               rs.getString("OFFICE_NAME"));
                            xml = xml + "<servicedata>";
                            xml = xml + "<SLNO>" + (++serno) + "</SLNO>";
                            xml =
 xml + "<SERVICE_LIST_SLNO>" + rs.getInt("SERVICE_LIST_SLNO") +
   "</SERVICE_LIST_SLNO>";
                            if (rs.getDate("DATE_FROM") != null) {
                                String[] sd =
                                    rs.getDate("DATE_FROM").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                xml =
 xml + "<DATE_FROM>" + od + "</DATE_FROM>";
                            } else {
                                xml =
 xml + "<DATE_FROM>" + rs.getDate("DATE_FROM") + "</DATE_FROM>";

                            }
                            xml =
 xml + "<DATE_FROM_SESSION>" + rs.getString("DATE_FROM_SESSION") +
   "</DATE_FROM_SESSION>";
                            if (rs.getDate("DATE_TO") != null) {
                                String[] sd =
                                    rs.getDate("DATE_TO").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                xml = xml + "<DATE_TO>" + od + "</DATE_TO>";
                            } else {
                                xml =
 xml + "<DATE_TO>" + rs.getDate("DATE_TO") + "</DATE_TO>";
                            }
                            xml =
 xml + "<DATE_TO_SESSION>" + rs.getString("DATE_TO_SESSION") +
   "</DATE_TO_SESSION>";
                            xml =
 xml + " <DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";
                            xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                            xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                            xml =
 xml + "<STATUS_DETAIL>" + rs.getString("STATUS_DETAIL") + "</STATUS_DETAIL>";
                            xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";

                            xml =
 xml + "<DESIGNATION>" + rs.getString("DESIGNATION") + "</DESIGNATION>";
                            xml =
 xml + "<OFFICE_NAME>" + rs.getString("OFFICE_NAME") + "</OFFICE_NAME>";
                            xml =
 xml + "<EMPLOYEE_STATUS_DESC>" + rs.getString("EMPLOYEE_STATUS_DESC") +
   "</EMPLOYEE_STATUS_DESC>";
                            xml =
 xml + "<OFFICE_DEPT_ID>" + rs.getString("OFFICE_DEPT_ID") +
   "</OFFICE_DEPT_ID>";
                            xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                            xml = xml + "</servicedata>";


                        }
                        rs.close();
                        ps.close();

                        xml = xml + "<flag>success</flag>";
                    }

                } else {
                    xml = xml + "<flag>failure</flag>";
                }


            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            } finally {


            }
            xml = xml + "</response>";


        } else if (strCommand.equalsIgnoreCase("test_test")) {
            xml = "<response><command>test</command>";
            try {


                ps =
  con.prepareStatement("select * from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
                ps.setInt(1, strcode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("SerGroup")) {
            xml = "<response><command>SerGroup</command>";
            try {
                String strdes = request.getParameter("cmbdes");
                int des = Integer.parseInt(strdes);

                ps =
  con.prepareStatement("select SERVICE_GROUP_ID from HRM_MST_DESIGNATIONS  where DESIGNATION_ID=?");
                ps.setInt(1, des);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><sid>" + rs.getInt(1) + "</sid>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            boolean flag = true;
            try {

                /*
                  CallableStatement cs=con.prepareCall("{call HRM_EMP_SERVICEDATA (?,?,?,?,?,?,?,?,?,?,?,?,?)}" );

                  cs.registerOutParameter(1,java.sql.Types.NUMERIC);
                  cs.setString(2,"update");
                  cs.setInt(3,strcode);
                  //cs.registerOutParameter(4,java.sql.Types.NUMERIC);
                  cs.setInt(4,slno);
                  cs.setDate(5,dtfrom);
                  cs.setString(6,dtfromsession);
                  cs.setDate(7,dtto);
                  cs.setString(8,dttosession);

                  cs.setInt(9,designation);
                  cs.setInt(10,officeid);
                  cs.setInt(11,statusid);
                  cs.setString(12,statusdetail);
                  cs.setString(13,remarks);

                  cs.execute();
                  int errcode =cs.getInt(1);
                  System.out.println("errcode:"+errcode);
                  if(errcode==0){

                      xml=xml+"<flag>success</flag>";

                  }
                  else {
                      xml=xml+"<flag>failure</flag>";

                  }

                  cs.close();
               */

                String fromses = request.getParameter("optDateFrom");
                if (fromses.equalsIgnoreCase("FN"))
                    fromses = "AM";
                else
                    fromses = "PM";
                String strdtfrom =
                    request.getParameter("txtDateFrom") + " " + fromses;
                String sql =
                    "select EMPLOYEE_ID from " + tablename + " where to_date('" +
                    strdtfrom + "','DD/MM/YYYY AM')  >=" +
                    "  to_date(to_char(DATE_FROM,'DD/MM/YYYY')||' '||decode(DATE_FROM_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM') " +
                    " and to_date('" + strdtfrom +
                    "','DD/MM/YYYY AM') < to_date(to_char(DATE_TO,'DD/MM/YYYY')||' '||decode(DATE_To_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM') and EMPLOYEE_ID=?  and SERVICE_LIST_SLNO !=?  and DATE_TO is not null";

                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setInt(2, slno);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>failure1</flag>";
                    flag = false;

                } else {

                    String toses = request.getParameter("optDateTo");
                    if (toses.equalsIgnoreCase("FN"))
                        toses = "AM";
                    else
                        toses = "PM";
                    String strdtto =
                        request.getParameter("txtDateTo") + " " + toses;
                    sql =
 "select EMPLOYEE_ID from " + tablename + " where to_date('" + strdtto +
   "','DD/MM/YYYY AM') > " +
   "  to_date(to_char(DATE_FROM,'DD/MM/YYYY')||' '||decode(DATE_FROM_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM') " +
   " and to_date('" + strdtto +
   "','DD/MM/YYYY AM') <= to_date(to_char(DATE_TO,'DD/MM/YYYY')||' '||decode(DATE_To_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM') and EMPLOYEE_ID=?  and SERVICE_LIST_SLNO !=? and DATE_TO is not null";

                    System.out.println("to date query1:" + sql);
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    ps.setInt(2, slno);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        xml = xml + "<flag>failure2</flag>";
                        flag = false;

                    } else {
                        fromses = request.getParameter("optDateFrom");
                        if (fromses.equalsIgnoreCase("FN"))
                            fromses = "AM";
                        else
                            fromses = "PM";
                        strdtfrom =
                                request.getParameter("txtDateFrom") + " " + fromses;

                        toses = request.getParameter("optDateTo");
                        if (toses.equalsIgnoreCase("FN"))
                            toses = "AM";
                        else
                            toses = "PM";
                        strdtto =
                                request.getParameter("txtDateTo") + " " + toses;

                        sql =
 " select EMPLOYEE_ID from " + tablename + " where " + " ((  to_date('" +
   strdtfrom +
   "','DD/MM/YYYY AM') < to_date(to_char(DATE_FROM,'DD/MM/YYYY')||' '||decode(DATE_FROM_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM')  " +
   "  and " + " to_date('" + strdtto +
   "','DD/MM/YYYY AM') > to_date(to_char(DATE_FROM,'DD/MM/YYYY')||' '||decode(DATE_FROM_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM')" +
   " )or ( to_date('" + strdtfrom +
   "','DD/MM/YYYY AM') < to_date(to_char(DATE_TO,'DD/MM/YYYY')||' '||decode(DATE_To_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM')  " +
   "   and " + " to_date('" + strdtto +
   "','DD/MM/YYYY AM') >  to_date(to_char(DATE_TO,'DD/MM/YYYY')||' '||decode(DATE_To_SESSION,'FN','AM','AN','PM'),'DD/MM/YYYY AM')  " +
   " )) and EMPLOYEE_ID=? and SERVICE_LIST_SLNO !=?  and DATE_TO is not null";
                        System.out.println("to date query2:" + sql);
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, strcode);
                        ps.setInt(2, slno);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;

                        }

                    }
                }
                if (flag == true) {

                    PreparedStatement cs =
                        con.prepareCall("update " + tablename +
                                        " set DATE_FROM=?,DATE_FROM_SESSION=?,DATE_TO=?,DATE_TO_SESSION=?," +
                                        "            DESIGNATION_ID=?, OFFICE_ID=?, EMPLOYEE_STATUS_ID=?, STATUS_DETAIL=?, REMARKS=?" +
                                        "                        where EMPLOYEE_ID=? and SERVICE_LIST_SLNO=?" +
                                        "            ");

                    cs.setInt(10, strcode);
                    cs.setInt(11, slno);

                    cs.setDate(1, dtfrom);
                    cs.setString(2, dtfromsession);
                    cs.setDate(3, dtto);
                    cs.setString(4, dttosession);
                    cs.setInt(5, designation);
                    cs.setInt(6, officeid);
                    cs.setString(7, statusid);
                    cs.setString(8, statusdetail);
                    cs.setString(9, remarks);

                    cs.execute();
                    xml = xml + "<flag>success</flag>";
                    cs.close();

                }
            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        else if (strCommand.equalsIgnoreCase("Interval")) {
            xml = "<response><command>Interval</command>";
            //String strinterval="";
            System.out.println("Interval is executed");
            boolean flag = false,flag1=false;
            try {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Emp id Code::" + strcode);
                System.out.println("Temporary Table Name2:" + tablename);
                PreparedStatement cs =
                    con.prepareStatement("select SERVICE_LIST_SLNO,DATE_FROM,DATE_FROM_SESSION,DATE_TO,DATE_TO_SESSION," +
                                         "DESIGNATION_ID,OFFICE_ID,EMPLOYEE_STATUS_ID  from " +
                                         tablename +
                                         " where   EMPLOYEE_ID=? ");
                cs.setInt(1, strcode);
                rs = cs.executeQuery();
                String valstr = null;
                String valstr_new = null;
                // valstr="<table cellspacing=\"2\" cellpadding=\"3\" border=\"1\" width=\"80%\" align=\"center\" class=\"tdH\">";
                valstr =
                        "<tr><td colspan='3' class='tdH'  align='center'>SR Validation Result for Employee Id - " +
                        strcode + "</td></tr>";
                
                
                valstr_new =
                    "<tr><td colspan='3' class='tdH'  align='center'>SR Validation Result for Employee Id - " +
                    strcode + "</td></tr>";
                
                String missing = "";
                String overlap = "";
                while (rs.next()) {
                    String rec = "";
                    int i = 0;
                    if (rs.getDate("DATE_FROM") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td align='right'>" + (++i) +
 ")</td><td>From Date is Empty</td></tr>";
                    }
                    if (rs.getString("DATE_FROM_SESSION") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>From Date Session Empty</td></tr>";
                    }
                    if (rs.getDate("DATE_TO") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>To Date Empty</td></tr>";
                    }
                    if (rs.getString("DATE_TO_SESSION") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>To Date Session Empty</td></tr>";
                    }


                    if (!(rs.getString("EMPLOYEE_STATUS_ID") != null &&
                          (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRT") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("LLV") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TCL") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("UAL") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("ABS") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SLV") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DIS") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("CMW") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("LOP") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SAN") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRA") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SUS") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPN")))) {
                        if (rs.getInt("DESIGNATION_ID") == 0) {
                            rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>Designation Entry Empty</td></tr>";
                        }
                    }
                    if (!(rs.getString("EMPLOYEE_STATUS_ID") != null &&
                          (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRT") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("LLV") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TCL") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("UAL") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("ABS") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DIS") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SLV") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("CMW") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("LOP") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SAN") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRA") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SUS") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STU") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STT") ||
                           rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("ABR")))) {
                        if (rs.getInt("OFFICE_ID") == 0) {
                            rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>Office Entry Empty</td></tr>";
                        }
                    }

                    /*

                      if(!(rs.getString("EMPLOYEE_STATUS_ID")!=null && (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRT") || rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT")|| rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("LLV")|| rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TCL") || rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPN")  )) )
                      {
                              if(rs.getInt("DESIGNATION_ID")==0) {
                                  rec+="<tr><td>&nbsp;</td><td  align='right'>"+ (++i) + ")</td><td>Designation Entry Empty</td></tr>";
                              }
                      }
                      if(!(rs.getString("EMPLOYEE_STATUS_ID")!=null && (rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TRT") || rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("DPT")|| rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("LLV")|| rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("TCL"))) )
                      {
                              if(rs.getInt("OFFICE_ID")==0) {
                                  rec+="<tr><td>&nbsp;</td><td  align='right'>"+ (++i) + ")</td><td>Office Entry Empty</td></tr>";
                              }
                      }
                      */
                    if (rs.getString("EMPLOYEE_STATUS_ID") == null) {
                        rec +=
"<tr><td>&nbsp;</td><td  align='right'>" + (++i) +
 ")</td><td>Employee Status Empty</td></tr>";
                    }

                    if (!rec.equals("")) {

                        missing +=
                                "<tr><td colspan=3 class='table'><b>Service Record " +
                                rs.getInt("SERVICE_LIST_SLNO") +
                                "</b></td></tr>";
                        missing += rec;
                        
                        
                        overlap +=
                            "<tr><td colspan=3 class='table'><b>Service Record " +
                            rs.getInt("SERVICE_LIST_SLNO") +
                            "</b></td></tr>";
                        overlap += rec;
                    }


                }

                if (!missing.equals("")) {
                    flag = true;
                    valstr +=
                            "<tr><td colspan=3 class='tdH'  align='center'>SR Missing Entries</td></tr>";
                    valstr += missing;
                }
                
                
                if (!overlap.equals("")) {
                    flag1 = true;
                    valstr_new +=
                            "<tr><td colspan=3 class='tdH'  align='center'>SR Overlapping Entries</td></tr>";
                    valstr_new += overlap;
                }


                {

                    //cs=con.prepareStatement("select SERVICE_LIST_SLNO,DATE_FROM,DATE_FROM_SESSION, DATE_TO,DATE_TO_SESSION from "+tablename+" where EMPLOYEE_ID=? and DATE_TO is not null order by SERVICE_LIST_SLNO" );
//                    cs =
//  con.prepareStatement(" SELECT SERVICE_LIST_SLNO, " +
//"  DATE_FROM, " +
//"  DATE_TO " +
//" FROM " + tablename +
//" WHERE EMPLOYEE_ID = ? " +
//" AND DATE_TO      IS NOT NULL " +
//" UNION " +
//" SELECT EMPLOYEE_ID, " +
//"  DATE_EFFECTIVE_FROM, " +
//"  Sysdate AS C " +
//" FROM HRM_EMP_CURRENT_POSTING " +
//" WHERE employee_id = ? " +
//" ORDER BY date_from,date_to ");
                	
                    cs =
                    	  con.prepareStatement(" SELECT SERVICE_LIST_SLNO, " +
                    	"  DATE_FROM, " +
                    	"  DATE_TO, " +
                    	"  date_from_session, "+
                    	"  date_to_session "+
                    	" FROM " + tablename +
                    	" WHERE EMPLOYEE_ID = ? " +
                    	" AND DATE_TO      IS NOT NULL " +
                    	" UNION " +
                    	" SELECT EMPLOYEE_ID, " +
                    	"  DATE_EFFECTIVE_FROM, " +
                    	"  Sysdate AS C, " +
                    	"  date_effective_from_session, "+
                    	"  'FN' as date_effective_to_session "+
                    	" FROM HRM_EMP_CURRENT_POSTING " +
                    	" WHERE employee_id = ? " +
                    	" ORDER BY DATE_FROM,DATE_FROM_SESSION desc ");
                	
                    cs.setInt(1, strcode);
                    cs.setInt(2, strcode);
                    rs = cs.executeQuery();
                    int count = 0,count1=0;
                    java.util.Date fdate = null;
                    java.util.Date tdate = null;
                    String fses="";
                    String tses="";
                    missing = "";
                    int srl1 = 0, srl2 = 0;
                    if (rs.next()) {
                         fdate = rs.getDate("DATE_FROM");
                         fses=rs.getString("DATE_FROM_SESSION");
                         tdate = rs.getDate("DATE_TO");
                         srl1 = rs.getInt("SERVICE_LIST_SLNO");
                         tses=rs.getString("DATE_TO_SESSION");

                    }
                    while (rs.next()) {
                        java.util.Date cfdate = rs.getDate("DATE_FROM");
                        // String fses=rs.getString("DATE_FROM_SESSION");
                        java.util.Date ctdate = rs.getDate("DATE_TO");
                        
                        String cfsession=rs.getString("date_from_session");
                        String ctsession=rs.getString("date_to_session");
                        System.out.println("cfdate:" + cfdate+"ctdate:" + ctdate+"cfsession:" + cfsession+"cfsession:" + cfsession);
                        srl2 = rs.getInt("SERVICE_LIST_SLNO");
                        System.out.println("srl2:" + srl2);
                        //  String tses=rs.getString("DATE_TO_SESSION");

                        //System.out.println("Date : From :"+fdate+"    To:"+tdate);

                        long diff = cfdate.getTime() - tdate.getTime();
                        
                        System.out.println("diff--->"+diff);
                        
                        System.out.println("Day Difference:"+(diff / (1000L*60L*60L*24L)) + " days.");
                        
                        long fullday = (1000L * 60L * 60L * 24L);
                        
                        System.out.println("fullday--->"+fullday);
                        
                        long day = (diff / fullday);
                        
                        
                        
                        System.out.println("DAY ===  "+day);
                        if (day > 1) {
                            long from = tdate.getTime() + fullday;
                            String strfrom =
                                new SimpleDateFormat("dd/MM/yy").format(new java.util.Date(from));
                            long to = cfdate.getTime() - fullday;
                            String strto =
                                new SimpleDateFormat("dd/MM/yy").format(new java.util.Date(to));
                            System.out.println("Interval :" + strfrom + "   " +
                                               strto);
//                            if(tses.equals("AN"))
//                            	tses="FN";
//                            if(cfsession.equals("AN"))
//                            	cfsession="AN";

                            
                            missing +=
                                    "<tr><td>" + srl1 + " - " + srl2 + "</td><td>" +
                                    strfrom + "</td><td>" + strto +
                                    "</td></tr>";
                            
//                            missing +=
//                                "<tr><td>" + srl1 + " - " + srl2 + "</td><td>" +
//                                strfrom+" "+tses + "</td><td>" + strto +" "+cfsession+   ///  This code is with sessions....
//                                "</td></tr>";
                            
                            //xml=xml+"<interval>"+strfrom+"   "+strto+"</interval>";
                            //strinterval=strinterval+strfrom+" "+strto+" ";

                            count++;
                        }
                        
                       //else if(day==0 && (tses.equals(cfsession)) )
                        else if(day<0 )
                        {

                    		long from = tdate.getTime();
                            String strfrom =
                                new SimpleDateFormat("dd/MM/yy").format(new java.util.Date(from));
                            long to = cfdate.getTime();
                            String strto =
                                new SimpleDateFormat("dd/MM/yy").format(new java.util.Date(to));
                            System.out.println("Interval :" + strfrom + "   " +
                                               strto);
                            overlap +=
                                    "<tr><td>" + srl1+ " - " + srl2 + "</td><td>" +
                                    strfrom +" "+tses + "</td><td>" + strto+" "+cfsession +
                                    "</td></tr>";
                            //xml=xml+"<interval>"+strfrom+"   "+strto+"</interval>";
                            //strinterval=strinterval+strfrom+" "+strto+" ";

                            count1++;
                    	
                        }
                        else if(day==0 || day <1)
                        {
                        	
                        	if(tses.equals("AN") && cfsession.equals("FN"))
                        	{
                        		long from = tdate.getTime();
                                String strfrom =
                                    new SimpleDateFormat("dd/MM/yy").format(new java.util.Date(from));
                                long to = cfdate.getTime();
                                String strto =
                                    new SimpleDateFormat("dd/MM/yy").format(new java.util.Date(to));
                                System.out.println("Interval :" + strfrom + "   " +
                                                   strto);
                                
                               // strfrom=strfrom+1900;
                                System.out.println("from date === "+strfrom);
                                overlap +=
                                        "<tr><td>" + srl1+ " - " + srl2 + "</td><td>" +
                                        strfrom +" "+tses + "</td><td>" + strto+" "+cfsession +
                                        "</td></tr>";
                                //xml=xml+"<interval>"+strfrom+"   "+strto+"</interval>";
                                //strinterval=strinterval+strfrom+" "+strto+" ";

                                count1++;
                        	}
                        	
                        }

                        fdate = cfdate;
                        tdate = ctdate;
                        fses  = cfsession;
                        tses  = ctsession;
                        srl1 = rs.getInt("SERVICE_LIST_SLNO");
                        System.out.println("srl1:" + srl1);

                    }
                    if (count > 0) {
                        // xml=xml+"<flag>success</flag>";
                        //System.out.println("Interval:|"+strinterval+"|");
                        //xml=xml+"<interval>"+strinterval+"</interval>";
                        flag = true;
                        valstr +=
                                "<tr><td colspan=\"3\" class='tdH' align='center'>SR Interval</td></tr>";
                        valstr +=
                                "<tr><td class='tdH'>S.No Between</td><td class='tdH'>From</td><td class='tdH'>To</td></tr>";
                        valstr += missing;
                        
                        

                    }
                    
                     if(count1>0)
                    {
                    	flag22 = true;
                        valstr_new +=
                            "<tr><td colspan=\"3\" class='tdH' align='center'>SR Overlap Interval</td></tr>";
                        valstr_new +=
                            "<tr><td class='tdH'>S.No Between</td><td class='tdH'>From</td><td class='tdH'>To</td></tr>";
                        valstr_new += overlap;

                    }
                     
                     System.out.println("flag  == "+flag+"  flag22  ==  "+flag22);
                    
                    if (flag == true && flag22==true) {
                    	
                        xml = xml + "<flag>failure</flag>";
                        //valstr+="</table>";
                        session.setAttribute("interval", valstr);
                        session.setAttribute("interval_new", valstr_new);
                    } 
                    
                    else if(flag==true)
                    {
                    	 xml = xml + "<flag>failure</flag>";
                         //valstr+="</table>";
                         //session.setAttribute("interval", valstr);
                         session.setAttribute("interval", valstr);

                    }
                    else if(flag22==true)
                    {
                    	 xml = xml + "<flag>failure</flag>";
                         //valstr+="</table>";
                         //session.setAttribute("interval", valstr);
                         session.setAttribute("interval_new", valstr_new);

                    }
                    else {

                        System.out.println("step1");
                        con.setAutoCommit(false);
                        cs =
  con.prepareStatement("delete from HRM_EMP_SERVICE_DATA  where EMPLOYEE_ID=?");
                        cs.setInt(1, strcode);
                        cs.execute();
                        cs.close();
                        System.out.println("step2");
                        System.out.println(tablename);
                        //cs=con.prepareStatement("select * from "+tablename+" order by DATE_FROM");
                        String sql =
                            "select * from " + tablename + " order by SERVICE_LIST_SLNO";
                        st = con.createStatement();
                        //cs.setString(1,tablename);
                        System.out.println(sql);
                        rs = st.executeQuery(sql);
                        System.out.println("temp table selected");
                        int seq = 0;
                        String updatedby =
                            (String)session.getAttribute("UserId");
                        long ln = System.currentTimeMillis();
                        Timestamp ts = new Timestamp(ln);

                        while (rs.next()) {
                            PreparedStatement ps1 =
                                con.prepareStatement("insert into  HRM_EMP_SERVICE_DATA(EMPLOYEE_ID, SERVICE_LIST_SLNO, DATE_FROM, DATE_FROM_SESSION," +
                                                     "DATE_TO, DATE_TO_SESSION, DESIGNATION_ID, OFFICE_ID, EMPLOYEE_STATUS_ID, STATUS_DETAIL, REMARKS, OFFICE_DEPT_ID,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID,UPDATED_DATE   ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                            ps1.setInt(1, rs.getInt("EMPLOYEE_ID"));
                            seq = seq + 1;
                            ps1.setInt(2, seq);
                            ps1.setDate(3, rs.getDate("DATE_FROM"));
                            ps1.setString(4,
                                          rs.getString("DATE_FROM_SESSION"));
                            ps1.setDate(5, rs.getDate("DATE_TO"));
                            ps1.setString(6, rs.getString("DATE_TO_SESSION"));
                            ps1.setInt(7, rs.getInt("DESIGNATION_ID"));
                            ps1.setInt(8, rs.getInt("OFFICE_ID"));
                            ps1.setString(9,
                                          rs.getString("EMPLOYEE_STATUS_ID"));
                            ps1.setString(10,
                                          rs.getString("STATUS_DETAIL") == null ?
                                          "" : rs.getString("STATUS_DETAIL"));
                            ps1.setString(11,
                                          rs.getString("REMARKS") == null ? "" :
                                          rs.getString("REMARKS"));
                            ps1.setString(12, rs.getString("OFFICE_DEPT_ID"));


                            if (rs.getString("PROCESS_FLOW_STATUS_ID") ==
                                null ||
                                !rs.getString("PROCESS_FLOW_STATUS_ID").equalsIgnoreCase("FR")) {
                                ps1.setString(13, "FR");
                                ps1.setString(14, updatedby);
                                ps1.setTimestamp(15, ts);
                            } else {
                                ps1.setString(13,
                                              rs.getString("PROCESS_FLOW_STATUS_ID"));
                                ps1.setString(14,
                                              rs.getString("UPDATED_BY_USER_ID"));
                                ps1.setTimestamp(15,
                                                 rs.getTimestamp("UPDATED_DATE"));
                            }


                            System.out.println("service no:" +
                                               rs.getInt("SERVICE_LIST_SLNO"));
                            System.out.println("emp id::" +
                                               rs.getInt("EMPLOYEE_ID"));
                            System.out.println("from date::" +
                                               rs.getDate("DATE_FROM"));
                            ps1.executeUpdate();
                            ps1.close();


                        }
                        System.out.println("seq:" + seq);

                        rs.close();
                        st.close();

                        cs = con.prepareStatement("drop table " + tablename);
                        //cs.setString(1,tablename);
                        cs.execute();
                        cs.close();

                        con.commit();
                        xml = xml + "<flag>success</flag>";
                        valstr +=
                                "<tr><td colspan=\"3\" class='table'>There is no Interval/Empty field</td></tr>";
                        valstr += "</table>";
                    }
                    rs.close();
                    cs.close();
                }
            }


            catch (Exception e) {
                System.out.println("catch........" + e);
                try {
                    con.rollback();
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                xml = xml + "<flag>failure1</flag>";
            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }
            xml = xml + "</response>";


        }

        else if (strCommand.equalsIgnoreCase("reload")) {
            xml = "<response><command>reload</command>";
            try {
                strcode =
                        Integer.parseInt(request.getParameter("txtEmployeeid"));
                System.out.println("assign..... Emp id Code::" + strcode);

                String sql =
                    "select * from ( " + " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION,a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID,a.OFFICE_ID, " +
                    " a.EMPLOYEE_STATUS_ID,a.STATUS_DETAIL,a.REMARKS, " +
                    " b.DESIGNATION,c.OFFICE_NAME,d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID " +
                    " from " + tablename + "  a" +
                    " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID  " +
                    " left outer join COM_MST_OFFICES c on c.OFFICE_ID=a.OFFICE_ID " +
                    " left outer join HRM_MST_EMPLOYEE_STATUS d on   d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID   " +
                    " where a.EMPLOYEE_ID=? and a.OFFICE_DEPT_ID ='TWAD' " +
                    " union all " +
                    " select a.SERVICE_LIST_SLNO,a.DATE_FROM,a.DATE_FROM_SESSION, " +
                    " a.DATE_TO,a.DATE_TO_SESSION,a.DESIGNATION_ID, " +
                    " a.OFFICE_ID, a.EMPLOYEE_STATUS_ID, a.STATUS_DETAIL,a.REMARKS, b.DESIGNATION, c.OTHER_DEPT_OFFICE_NAME as OFFICE_NAME, d.EMPLOYEE_STATUS_DESC, a.OFFICE_DEPT_ID  " +
                    " from " + tablename + "  a " +
                    " left outer join HRM_MST_DESIGNATIONS b on b.DESIGNATION_ID=a.DESIGNATION_ID " +
                    " left outer join HRM_MST_OTHER_DEPT_OFFICES c on c.OTHER_DEPT_OFFICE_ID=a.OFFICE_ID " +
                    " left outer join HRM_MST_EMPLOYEE_STATUS d on  d.EMPLOYEE_STATUS_ID=a.EMPLOYEE_STATUS_ID " +
                    "where a.EMPLOYEE_ID=?  and a.OFFICE_DEPT_ID <> 'TWAD' " +
                    ") order by DATE_FROM,DATE_FROM_SESSION,DATE_TO,DATE_TO_SESSION";

                ps = con.prepareStatement(sql);
                ps.setInt(1, strcode);
                ps.setInt(2, strcode);
                rs = ps.executeQuery();
                int serno = 0;
                while (rs.next()) {
                    System.out.println(rs.getInt("DESIGNATION_ID") + "->" +
                                       rs.getString("DESIGNATION") + ";" +
                                       rs.getInt("OFFICE_ID") + "->" +
                                       rs.getString("OFFICE_NAME"));
                    xml = xml + "<servicedata>";
                    xml = xml + "<SLNO>" + (++serno) + "</SLNO>";
                    xml =
 xml + "<SERVICE_LIST_SLNO>" + rs.getInt("SERVICE_LIST_SLNO") +
   "</SERVICE_LIST_SLNO>";
                    if (rs.getDate("DATE_FROM") != null) {
                        String[] sd =
                            rs.getDate("DATE_FROM").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<DATE_FROM>" + od + "</DATE_FROM>";
                    } else {
                        xml =
 xml + "<DATE_FROM>" + rs.getDate("DATE_FROM") + "</DATE_FROM>";

                    }
                    xml =
 xml + "<DATE_FROM_SESSION>" + rs.getString("DATE_FROM_SESSION") +
   "</DATE_FROM_SESSION>";
                    if (rs.getDate("DATE_TO") != null) {
                        String[] sd =
                            rs.getDate("DATE_TO").toString().split("-");
                        String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                        xml = xml + "<DATE_TO>" + od + "</DATE_TO>";
                    } else {
                        xml =
 xml + "<DATE_TO>" + rs.getDate("DATE_TO") + "</DATE_TO>";
                    }
                    xml =
 xml + "<DATE_TO_SESSION>" + rs.getString("DATE_TO_SESSION") +
   "</DATE_TO_SESSION>";
                    xml =
 xml + "<DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";
                    xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                    xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                    xml =
 xml + "<STATUS_DETAIL>" + rs.getString("STATUS_DETAIL") + "</STATUS_DETAIL>";
                    xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";

                    xml =
 xml + "<DESIGNATION>" + rs.getString("DESIGNATION") + "</DESIGNATION>";
                    xml =
 xml + "<OFFICE_NAME>" + rs.getString("OFFICE_NAME") + "</OFFICE_NAME>";
                    xml =
 xml + "<EMPLOYEE_STATUS_DESC>" + rs.getString("EMPLOYEE_STATUS_DESC") +
   "</EMPLOYEE_STATUS_DESC>";
                    xml =
 xml + "<OFFICE_DEPT_ID>" + rs.getString("OFFICE_DEPT_ID") +
   "</OFFICE_DEPT_ID>";

                    xml = xml + "</servicedata>";


                }
                rs.close();
                ps.close();

                xml = xml + "<flag>success</flag>";

            }


            catch (Exception e) {
                System.out.println("reload catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        out.println(xml);
        System.out.println(xml);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        doGet(request, response);
    }
}


