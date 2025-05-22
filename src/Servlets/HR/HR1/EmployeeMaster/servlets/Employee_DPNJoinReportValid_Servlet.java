package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class Employee_DPNJoinReportValid_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        Connection connection = null;
        try {
        	LoadDriver driver=new LoadDriver();
            connection=driver.getConnection();


        } catch (Exception e) {
            System.out.println("________Exception in opening connection:_______________" +
                               e);
        }
        //Statement statement=null;
        ResultSet results = null;
        ResultSet rs4 = null;
        ResultSet rs3 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        // PreparedStatement ps3=null;
        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                pw.println(xml);
                System.out.println(xml);
                pw.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";

            int strEmpName = 0;
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;

            try {
                try {
                    strEmpName =
                            Integer.parseInt(request.getParameter("txtEmpId"));
                    System.out.println(strEmpName);
                    OffId = Integer.parseInt(request.getParameter("txtOffId"));
                    System.out.println(OffId);
                    JYear = Integer.parseInt(request.getParameter("txtDOJ"));
                    System.out.println(JYear);
                    JoinId = Integer.parseInt(request.getParameter("JoinId"));
                    System.out.println(JoinId);

                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }

                String sql =
                    "delete from HRM_EMP_DPN_JOIN_REPORTS  where EMPLOYEE_ID=? and JOINING_REPORT_ID=? and OFFICE_ID=? and JOINING_YEAR=?";

                ps = connection.prepareStatement(sql);

                ps.setInt(1, strEmpName);
                ps.setInt(2, JoinId);
                ps.setInt(3, OffId);
                ps.setInt(4, JYear);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps.close();


            } catch (Exception e) {
                System.out.println("Exception of the e.getStackTrace" +
                                   e.getStackTrace());
                System.out.println("Exception of the e.getMessage()" +
                                   e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Load")) {

            String sxml = "<response><command>Load</command>";
            String strJoindt = "", strCompdt = "", empstatus =
                "", compsession = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            try {

                ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, Integer.parseInt(strEmpName));
                results = ps.executeQuery();
                if (!results.next()) {
                    xml = sxml + "<flag>failure</flag>";
                } else {
                    ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    results = ps.executeQuery();
                    if (!results.next()) {
                        xml = sxml + "<flag>failure1</flag>";
                    } else {
                        String sql =
                            "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                            " B.DATE_OF_JOINING, B.FN_OR_AN,B.OTHER_DEPT_ID,B.OTHER_DEPT_OFFICE_ID,B.SR_CONTROLLING_OFFICE_ID," +
                            " B.REMARKS,B.PROCESS_FLOW_STATUS_ID FROM HRM_MST_EMPLOYEES A " +
                            " INNER JOIN HRM_EMP_DPN_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                            " WHERE A.EMPLOYEE_ID=? and (B.PROCESS_FLOW_STATUS_ID='CR' or B.PROCESS_FLOW_STATUS_ID='MD') ";

                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(strEmpName));
                        ResultSet rs = ps.executeQuery();

                        int i = 0;
                        String strDob = "";
                        if (rs.next()) {
                            System.out.println("emp name1" + strEmpName);

                            String strEName = rs.getString("Employee_Name");
                            int strEmpGpf = rs.getInt("GPF_NO");

                            int JoinId = rs.getInt("JOINING_REPORT_ID");
                            String strNoon = rs.getString("FN_OR_AN");

                            // int DesigId=rs.getInt("DESIGNATION_ID");
                            //  int PostId=rs.getInt("POST_COUNTED_ID");

                            String strRemarks = rs.getString("REMARKS");
                            String strProcId =
                                rs.getString("PROCESS_FLOW_STATUS_ID");
                            System.out.println("proceess flow id is$$$$$" +
                                               strProcId);
                            i++;
                            Date strdt = rs.getDate("DATE_OF_BIRTH");
                            if (strdt == null) {
                                strDob = "0";
                            } else {
                                String[] sd;
                                sd =
  rs.getDate("DATE_OF_BIRTH").toString().split("-");
                                strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                            }

                            Date strjdt = rs.getDate("DATE_OF_JOINING");
                            if (strjdt == null) {
                                strJoindt = "0";
                            } else {
                                String[] sd1;
                                sd1 =
 rs.getDate("DATE_OF_JOINING").toString().split("-");
                                strJoindt =
                                        sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                            }


                            xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                            xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId>";
                            xml =
 xml + "<Noon>" + strNoon + "</Noon>"; //+"<DesigId>"+DesigId+"</DesigId>";
                            xml =
 xml + "<JDate>" + strJoindt + "</JDate>"; //<PostId>"+PostId+"</PostId>";
                            xml =
 xml + "<Remarks>" + strRemarks + "</Remarks><ProcId>" + strProcId +
   "</ProcId>";
                            // xml=xml+"<workingstatus>"+empstatus+"</workingstatus>";
                            String other_dept_id =
                                rs.getString("OTHER_DEPT_ID");
                            int other_office_id =
                                rs.getInt("OTHER_DEPT_OFFICE_ID");
                            xml =
 xml + "<other_dept_id>" + other_dept_id + "</other_dept_id><other_office_id>" +
   other_office_id + "</other_office_id>";
                            System.out.println("test3.5");
                            int sr_ctrl_office_id =
                                rs.getInt("SR_CONTROLLING_OFFICE_ID");
                            xml =
 xml + "<sr_ctrl_office_id>" + sr_ctrl_office_id + "</sr_ctrl_office_id>";
                            // System.out.println("dest" +DesigId);
                            try {
                                /*String sql3="select a.service_group_id from hrm_mst_service_group a inner join hrm_mst_designations b on b.service_group_id=a.service_group_id where b.designation_id=?";

                           ps1=connection.prepareStatement(sql3);
                           ps1.setInt(1,DesigId);
                           rs3=ps1.executeQuery();
                           while(rs3.next()) {
                           int servgrp = rs3.getInt("SERVICE_GROUP_ID");
                               System.out.println("serv" +servgrp);
                           xml=xml+"<ServGroup>"+servgrp+"</ServGroup>";
                           */
                                /***** 16-08-2006  **/
                                //ps=connection.prepareStatement("select max(date_to) as maxtodate from HRM_EMP_SERVICE_DATA where EMPLOYEE_ID=?");
                                ps =
  connection.prepareStatement("select DATE_EFFECTIVE_FROM as maxtodate from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                ps.setInt(1, Integer.parseInt(strEmpName));
                                rs = ps.executeQuery();
                                String maxtodate = "";
                                if (rs.next()) {
                                    if (rs.getDate("maxtodate") != null) {
                                        maxtodate =
                                                new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("maxtodate"));
                                        System.out.println("max to date :" +
                                                           rs.getDate("maxtodate"));
                                    } else {

                                        maxtodate = "empty";
                                    }
                                }
                                xml =
 xml + "<maxtodate>" + maxtodate + "</maxtodate>";


                                //ps=connection.prepareStatement("select d.DESIGNATION,c.DATE_EFFECTIVE_FROM ,f.OTHER_DEPT_OFFICE_NAME,g.OTHER_DEPT_NAME from HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,HRM_MST_OTHER_DEPT_OFFICES f,HRM_MST_OTHER_DEPTS g where g.OTHER_DEPT_ID=c.DEPARTMENT_ID and f.OTHER_DEPT_OFFICE_ID=c.OFFICE_ID and f.OTHER_DEPT_ID=c.DEPARTMENT_ID  and  c.DESIGNATION_ID=d.DESIGNATION_ID and      C.EMPLOYEE_ID=? ");
                                ps =
  connection.prepareStatement("select  OTHER_DEPT_OFFICE_NAME from  HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID  =? ");
                                ps.setString(1, other_dept_id);
                                ps.setInt(2, other_office_id);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                System.out.println("test6");
                                if (rs.next()) {

                                    String otherdeptoffice =
                                        "", otherdeptname = "";
                                    if (rs.getString("OTHER_DEPT_OFFICE_NAME") !=
                                        null) {
                                        otherdeptoffice =
                                                rs.getString("OTHER_DEPT_OFFICE_NAME");
                                    } else {
                                        otherdeptoffice = "null";
                                    }

                                    xml =
 xml + "<otherdeptoffice>" + otherdeptoffice + "</otherdeptoffice>";

                                }

                                ps =
  connection.prepareStatement("select f.OTHER_DEPT_OFFICE_ID,f.OTHER_DEPT_OFFICE_NAME " +
                              " from HRM_MST_OTHER_DEPT_OFFICES f where f.OTHER_DEPT_ID=?");

                                ps.setString(1, other_dept_id);
                                rs = ps.executeQuery();
                                while (rs.next()) {
                                    xml =
 xml + "<option><id>" + rs.getInt("OTHER_DEPT_OFFICE_ID") + "</id><name>" +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</name></option>";
                                }
                                /*******/


                                //}
                            } catch (Exception se) {
                                System.out.println("error in serv grp" + se);
                            }
                        }

                        if (i == 0) {
                            /*
                          try
                          {
                              String sql2="select * from hrm_mst_employees where employee_id=?";
                              ps2=connection.prepareStatement(sql2);
                              ps2.setInt(1,Integer.parseInt(strEmpName));

                               rs4=ps2.executeQuery();
                              int j=0;
                              while(rs4.next())
                              {
                                 j++;
                                  String strEmpPref=rs4.getString("Employee_Prefix");
                                  String strEmpInit=rs4.getString("Employee_Initial");
                                    strEmpName=rs4.getString("Employee_Name");
                                   int strEmpGpf=rs4.getInt("GPF_NO");
                                  Date strdt=rs4.getDate("DATE_OF_BIRTH");
                                  if(strdt==null) {
                                      strDob="0";
                                  }
                                  else
                                  {
                                  String[] sd;
                                  sd=rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                  strDob=sd[2]+"/"+sd[1]+"/"+sd[0];
                                  }
                                  xml=sxml+"<flag>NoRecord</flag>";
                                  xml=xml+"<EmpPref>"+strEmpPref+"</EmpPref><EmpInit>"+strEmpInit+"</EmpInit><EmpName>"+strEmpName+"</EmpName><EmpGpf>"+strEmpGpf+"</EmpGpf><Dtofbirth>"+strDob+"</Dtofbirth>";
                              }
                              if(j==0)
                              {
                                  xml=sxml+"<flag>NoValue</flag>";
                              }



                          }
                          catch(Exception ae){System.out.println("Error in the second query" + ae);}
                           */
                            xml = sxml + "<flag>failure2</flag>" + xml;
                        } else {
                            // System.out.println("EmpStatus in Deputation joining form:"+empstatus+"|");
                            // if(empstatus!=null && !empstatus.equalsIgnoreCase("DPT"))
                            //     xml=sxml+"<flag>failure3</flag>";
                            // else
                            xml = sxml + "<flag>success</flag>" + xml;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("Update")) {

            xml = "<response><command>Update</command>";

            int strEmpName = 0;
            int strDesigId = 0;
            int PostId = 0;
            String strRemarks = "", empstatus = "", compsession = "";
            int JYear = 0;
            int OffId = 0;
            int JoinId = 0;
            String strProceId = "";
            String radVal = "";
            Date dtjoin = null;
            java.util.Date d = null;
            java.sql.Date cdt = null;
            String txtDepOffId = "", txtDepId = "";
            String sr_cntrl_offid = "";

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            try {
                try {
                    System.out.println("join Validate");

                    strEmpName =
                            Integer.parseInt(request.getParameter("txtEmpId"));
                    OffId = Integer.parseInt(request.getParameter("txtOffId"));
                    //strDesigId =Integer.parseInt(request.getParameter("cmbdes"));
                    //PostId = Integer.parseInt(request.getParameter("comPostTow"));
                    JYear = Integer.parseInt(request.getParameter("txtDOJ"));
                    JoinId = Integer.parseInt(request.getParameter("JoinId"));
                    radVal = request.getParameter("radFNAN");
                    strRemarks = request.getParameter("txtRemarks");
                    strProceId = request.getParameter("comProcFlowId");

                    Calendar c;
                    String[] sd =
                        request.getParameter("txtjoindate").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    dtjoin = new Date(d.getTime());

                    empstatus = request.getParameter("empstatus");
                    System.out.println("empstatus..." + empstatus);

                    System.out.println("query:" + request.getQueryString());
                    txtDepId = request.getParameter("txtDepId");
                    System.out.println("txtDepId...." + txtDepId);

                    txtDepOffId = request.getParameter("txtDepOffId");
                    System.out.println("txtDepOffId...." + txtDepOffId);

                    sr_cntrl_offid = request.getParameter("sr_cntrl_offid");
                    System.out.println("sr_cntrl_offid...." + sr_cntrl_offid);

                    /*  if(empstatus!=null && !empstatus.equalsIgnoreCase("WKG")) {
                              String strDOC=request.getParameter("compdate");
                              SimpleDateFormat  dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                              d = dateFormat.parse(strDOC.trim());
                              dateFormat.applyPattern("yyyy-MM-dd");
                              String dateString = dateFormat.format(d);
                              cdt = java.sql.Date.valueOf(dateString);


                          }*/

                    // compsession=request.getParameter("compsession");
                    //  System.out.println("compsession..."+compsession);

                } catch (Exception e) {
                    System.out.println("exce **** " + e);
                }
                boolean flag = false;
                connection.setAutoCommit(false);
                System.out.println(strProceId);
                if (strProceId.equalsIgnoreCase("FR")) {
                    String sql =
                        "select EMPLOYEE_STATUS_ID  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, strEmpName);
                    results = ps.executeQuery();
                    if (!results.next()) {
                        xml =
 xml + "<flag>failure</flag><flag>This employee does not have any current post.So Cann't Freezed.</flag>";
                        flag = true;
                    } else {
                        String currentStatus =
                            results.getString("EMPLOYEE_STATUS_ID");
                        if (currentStatus.equalsIgnoreCase("WKG")) {
                            xml =
 xml + "<flag>failure</flag><flag>Given employee has been working status.Can not post aganin.</flag>";
                            flag = true;
                        }

                    }


                }
                if (!flag) {

                    int ooffid = 0, srctrloffid = 0;
                    try {
                        ooffid = Integer.parseInt(txtDepOffId);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    try {
                        srctrloffid = Integer.parseInt(sr_cntrl_offid);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    System.out.println("here is ok1");


                    // Delete the SR Controllling office record if exist
                    PreparedStatement psorg =
                        connection.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strEmpName);
                    psorg.executeUpdate();
                    System.out.println("here is ok2");
                    // insert SR Controlling Office Record
                    psorg =
connection.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                            " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1, strEmpName);
                    psorg.setInt(2, srctrloffid);
                    psorg.setString(3, "TWAD");
                    psorg.setString(4, "FR");
                    java.sql.Date dt =
                        new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5, dt);
                    psorg.setString(6, updatedby);
                    psorg.setTimestamp(7, ts);
                    psorg.executeUpdate();
                    System.out.println("here is ok3");


                    psorg =
connection.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?");
                    psorg.setInt(1, strEmpName);
                    psorg.executeUpdate();
                    System.out.println("here is ok4");

                    psorg =
connection.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE_TMP(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                            " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1, strEmpName);
                    psorg.setInt(2, srctrloffid);
                    psorg.setString(3, "TWAD");
                    psorg.setString(4, "FR");
                    dt =
  new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5, dt);

                    psorg.setString(6, updatedby);
                    psorg.setTimestamp(7, ts);
                    psorg.executeUpdate();
                    System.out.println("here is ok5");

                    CallableStatement cs = null;


                    System.out.println("proc started");
                    cs =
  connection.prepareCall("{call HRM_EMP_DPNJOIN_REPORT_UPT(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                    System.out.println("offid" + OffId);
                    System.out.println("JYear" + JYear);
                    System.out.println("JoinId" + JoinId);
                    System.out.println("strEmpName" + strEmpName);

                    System.out.println("dtjoin" + dtjoin);
                    System.out.println("radVal" + radVal);
                    System.out.println("strEmpName" + strEmpName);
                    System.out.println("strProceId" + strProceId);
                    System.out.println("remarks" + strRemarks);
                    System.out.println("department id" + txtDepId);
                    System.out.println("office_id" + ooffid);
                    System.out.println("controlling office" + srctrloffid);
                    System.out.println("updated by" + updatedby);
                    System.out.println("ts" + ts);

                    cs.setInt(1, OffId);
                    cs.setInt(2, JYear);
                    cs.setInt(3, JoinId);
                    cs.setInt(4, strEmpName);
                    cs.setDate(5, dtjoin);
                    cs.setString(6, radVal);
                    cs.setString(7, strRemarks);
                    cs.setString(8, strProceId);
                    cs.registerOutParameter(9, java.sql.Types.NUMERIC);

                    cs.setString(10, txtDepId);
                    cs.setInt(11, ooffid);
                    cs.setInt(12, srctrloffid);

                    cs.setString(13, updatedby);
                    cs.setTimestamp(14, ts);
                    //cs.setString(10,empstatus);
                    //cs.setInt(7,strDesigId);
                    //cs.setInt(8,PostId);
                    //  cs.setDate(12,cdt);
                    //  cs.setString(14,compsession);
                    //cs.setDate(11,dtjoin);
                    cs.execute();
                    System.out.println("proc ended");
                    int errcode = cs.getInt(9);
                    System.out.println("SQLCODE:::" + errcode);
                    if (errcode != 0) {
                        xml = xml + "<flag>failure</flag>";
                    } else {
                        xml = xml + "<flag>success</flag>";
                    }
                    ps.close();
                }
                connection.commit();


            } catch (Exception e) {
                System.out.println("exception in the insertion " + e);
                xml = xml + "<flag>failure</flag>";
                try {
                    connection.rollback();
                } catch (Exception e1) {
                    System.out.println(e);
                }
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Load1")) {

            String sxml = "<response><command>Load</command>";
            String strJoindt = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            try {

                String sql =
                    "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                    " B.DATE_OF_JOINING, B.FN_OR_AN,  " +
                    " B.REMARKS,B.PROCESS_FLOW_STATUS_ID FROM HRM_MST_EMPLOYEES A " +
                    " INNER JOIN HRM_EMP_DPN_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                    " WHERE A.EMPLOYEE_ID=? ";

                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(strEmpName));
                ResultSet rs = ps.executeQuery();

                int i = 0;
                String strDob = "";
                if (rs.next()) {
                    System.out.println("emp name" + strEmpName);

                    String strEName = rs.getString("Employee_Name");
                    int strEmpGpf = rs.getInt("GPF_NO");

                    int JoinId = rs.getInt("JOINING_REPORT_ID");
                    String strNoon = rs.getString("FN_OR_AN");

                    // int DesigId=rs.getInt("DESIGNATION_ID");
                    // int PostId=rs.getInt("POST_COUNTED_ID");

                    String strRemarks = rs.getString("REMARKS");
                    String strProcId = rs.getString("PROCESS_FLOW_STATUS_ID");
                    i++;
                    Date strdt = rs.getDate("DATE_OF_BIRTH");
                    if (strdt == null) {
                        strDob = "0";
                    } else {
                        String[] sd;
                        sd = rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                    }

                    Date strjdt = rs.getDate("DATE_OF_JOINING");
                    if (strjdt == null) {
                        strJoindt = "0";
                    } else {
                        String[] sd1;
                        sd1 =
 rs.getDate("DATE_OF_JOINING").toString().split("-");
                        strJoindt = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                    }

                    xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId>";
                    xml = xml + "<Noon>" + strNoon + "</Noon>";
                    xml =
 xml + "<JDate>" + strJoindt + "</JDate><Remarks>" + strRemarks +
   "</Remarks><ProcId>" + strProcId + "</ProcId>";
                    // System.out.println("dest" +DesigId);

                }

                if (i == 0) {

                    try {
                        String sql2 =
                            "select * from hrm_mst_employees where employee_id=?";
                        ps2 = connection.prepareStatement(sql2);
                        ps2.setInt(1, Integer.parseInt(strEmpName));

                        rs4 = ps2.executeQuery();
                        int j = 0;
                        while (rs4.next()) {
                            j++;
                            String strEmpPref =
                                rs4.getString("Employee_Prefix");
                            String strEmpInit =
                                rs4.getString("Employee_Initial");
                            strEmpName = rs4.getString("Employee_Name");
                            int strEmpGpf = rs4.getInt("GPF_NO");
                            Date strdt = rs4.getDate("DATE_OF_BIRTH");
                            if (strdt == null) {
                                strDob = "0";
                            } else {
                                String[] sd;
                                sd =
  rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                            }
                            xml = sxml + "<flag>NoRecord</flag>";
                            xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf><Dtofbirth>" + strDob + "</Dtofbirth>";
                        }
                        if (j == 0) {
                            xml = sxml + "<flag>NoValue</flag>";
                        }

                    } catch (Exception ae) {
                        System.out.println("Error in the second query" + ae);
                    }

                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }

        else if (strCommand.equalsIgnoreCase("LoadEmp")) {
            System.out.println("hello.");
            xml = "<response><command>LoadEmp</command>";
            // String strJoindt="";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            try {
                ps =
  connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
                ps.setInt(1, Integer.parseInt(strEmpName));
                results = ps.executeQuery();
                if (results.next()) {
                    xml = xml + "<flag>failure1</flag>";
                } else {
                    results.close();
                    ps.close();


                    String sql =
                        "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH FROM HRM_MST_EMPLOYEES A " +
                        " WHERE A.EMPLOYEE_ID=? ";

                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(strEmpName));
                    ResultSet rs = ps.executeQuery();

                    // int i=0;
                    String strDob = "";
                    if (rs.next()) {
                        xml = xml + "<flag>success</flag>";
                        System.out.println("emp name" + strEmpName);

                        String strEName = rs.getString("Employee_Name");
                        int strEmpGpf = rs.getInt("GPF_NO");

                        Date strdt = rs.getDate("DATE_OF_BIRTH");
                        if (strdt == null) {
                            strDob = "0";
                        } else {
                            String[] sd;
                            sd =
  rs.getDate("DATE_OF_BIRTH").toString().split("-");
                            strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                        }


                        xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                        xml = xml + "<Dtofbirth>" + strDob + "</Dtofbirth>";

                    } else {
                        xml =
 "<response><command>LoadEmp</command>" + "<flag>NoValue</flag>";
                    }
                }


            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = xml + "<flag>NoValue</flag>";
            }
            //}
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("LoadSer")) {
            System.out.println("load service ");
            String sxml = "<response><command>LoadSer</command>";
            String strJoindt = "";
            String strEmpName = request.getParameter("EName");
            System.out.println("emp name" + strEmpName);
            String strserid = request.getParameter("txtser");
            System.out.println("service id" + strserid);
            try {

                String sql =
                    "SELECT A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,A.GPF_NO,A.DATE_OF_BIRTH,B.JOINING_REPORT_ID, " +
                    " B.DATE_OF_JOINING, B.FN_OR_AN,  " +
                    " B.REMARKS,B.PROCESS_FLOW_STATUS_ID FROM HRM_MST_EMPLOYEES A " +
                    " INNER JOIN HRM_EMP_DPN_JOIN_REPORTS B ON B.EMPLOYEE_ID=A.EMPLOYEE_ID " +
                    " WHERE A.EMPLOYEE_ID=? and B.JOINING_REPORT_ID=? ";

                ps = connection.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(strEmpName));
                ps.setInt(2, Integer.parseInt(strserid));
                ResultSet rs = ps.executeQuery();

                int i = 0;
                String strDob = "";
                if (rs.next()) {
                    System.out.println("emp name" + strEmpName);

                    String strEName = rs.getString("Employee_Name");
                    int strEmpGpf = rs.getInt("GPF_NO");

                    int JoinId = rs.getInt("JOINING_REPORT_ID");
                    String strNoon = rs.getString("FN_OR_AN");

                    // int DesigId=rs.getInt("DESIGNATION_ID");
                    // int PostId=rs.getInt("POST_COUNTED_ID");

                    String strRemarks = rs.getString("REMARKS");
                    String strProcId = rs.getString("PROCESS_FLOW_STATUS_ID");
                    i++;
                    Date strdt = rs.getDate("DATE_OF_BIRTH");
                    if (strdt == null) {
                        strDob = "0";
                    } else {
                        String[] sd;
                        sd = rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                    }

                    Date strjdt = rs.getDate("DATE_OF_JOINING");
                    if (strjdt == null) {
                        strJoindt = "0";
                    } else {
                        String[] sd1;
                        sd1 =
 rs.getDate("DATE_OF_JOINING").toString().split("-");
                        strJoindt = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                    }

                    xml =
 xml + "<Emp_Id>" + strEmpName + "</Emp_Id>" + "<EmpName>" + strEName +
   "</EmpName><EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    xml =
 xml + "<Dtofbirth>" + strDob + "</Dtofbirth>" + "<JoinId>" + JoinId +
   "</JoinId>";
                    xml = xml + "<Noon>" + strNoon + "</Noon>";
                    xml =
 xml + "<JDate>" + strJoindt + "</JDate><Remarks>" + strRemarks +
   "</Remarks><ProcId>" + strProcId + "</ProcId>";
                    // System.out.println("dest" +DesigId);

                }

                if (i == 0) {

                    try {
                        String sql2 =
                            "select * from hrm_mst_employees where employee_id=?";
                        ps2 = connection.prepareStatement(sql2);
                        ps2.setInt(1, Integer.parseInt(strEmpName));

                        rs4 = ps2.executeQuery();
                        int j = 0;
                        while (rs4.next()) {
                            j++;
                            String strEmpPref =
                                rs4.getString("Employee_Prefix");
                            String strEmpInit =
                                rs4.getString("Employee_Initial");
                            strEmpName = rs4.getString("Employee_Name");
                            int strEmpGpf = rs4.getInt("GPF_NO");
                            Date strdt = rs4.getDate("DATE_OF_BIRTH");
                            if (strdt == null) {
                                strDob = "0";
                            } else {
                                String[] sd;
                                sd =
  rs4.getDate("DATE_OF_BIRTH").toString().split("-");
                                strDob = sd[2] + "/" + sd[1] + "/" + sd[0];
                            }
                            xml = sxml + "<flag>NoRecord</flag>";
                            xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf><Dtofbirth>" + strDob + "</Dtofbirth>";
                        }
                        if (j == 0) {
                            xml = sxml + "<flag>NoValue</flag>";
                        }

                    } catch (Exception ae) {
                        System.out.println("Error in the second query" + ae);
                    }

                } else {
                    xml = sxml + "<flag>success</flag>" + xml;
                }

            } catch (Exception e) {
                System.out.println("Exception " + e);
                xml = sxml + "<flag>failure</flag>";
            }
            //}
            xml = xml + "</response>";

        }


        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
