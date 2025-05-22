// CREATE EMPLOYEE RELIEVAL
package Servlets.HR.HR1.EmployeeMaster.servlets;


import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class Create_Emp_servlet_new extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
        try {

        	LoadDriver driver=new LoadDriver();
            con=driver.getConnection();

        } catch (Exception e) {
            System.out.println("Exception in openeing connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        ResultSet rs = null, rs1 = null, rs2 = null, rs3 = null;
        CallableStatement cs = null;
        PreparedStatement psnew1 = null;
        ResultSet rsnew1 = null;
        PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null;
        String xml = "";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                xml =
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


        PreparedStatement psnew = null;
        ResultSet rsnew = null;

        String strCommand = "";
        Calendar c;
        String ename = "", desig = "";
        String relreasonid = "";
        int eid = 0;
        try {
            eid = Integer.parseInt(request.getParameter("txtEmployeeid"));
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(eid);
        strCommand = request.getParameter("Command");
        System.out.println(strCommand);

        if (strCommand.equalsIgnoreCase("loademp")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int offid = 0;
            offid = Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>loademp</command>";
            try {

                //   UserProfile up=null;
                //   up=(UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                /*  ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1,eid);
                rs=ps.executeQuery();
                if(!rs.next())
                {
                   xml=xml+"<flag>failure</flag>";
                   flag=false;
                }
                else  if(up.getEmployeeId()!=eid)
                 {
                               int OfficeId=0;
                               String sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                               ps=con.prepareStatement(sql);
                               ps.setInt(1,eid);
                               rs=ps.executeQuery();

                              if(rs.next()) {
                                   OfficeId=rs.getInt("CONTROLLING_OFFICE_ID");
                               }

                               if(OfficeId!=0)
                               {
                                      sql="select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                                       ps=con.prepareStatement(sql);
                                       ps.setInt(1,up.getEmployeeId());
                                       rs=ps.executeQuery();
                                        if(rs.next()) {
                                           int offid1=rs.getInt("OFFICE_ID");
                                           if(offid1!=OfficeId)
                                           {
                                                   //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                                    xml=xml+"<flag>failurea</flag>";
                                               flag=false;
                                           }
                                       }
                                       else
                                       {
                                              // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                                               xml=xml+"<flag>failureb</flag>";
                                           flag=false;
                                       }

                               }
                               else{
                                   xml=xml+"<flag>failurec</flag>";
                                   flag=false;
                               }
                 }
                 else {

                    // xml=xml+"<flag>failured</flag>";
                    // flag=false;
                 }*/

                if (flag) {
                    ps =
  con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        xml = xml + "<flag>failure1</flag>";
                    } else {
                        System.out.println("inside employee status id");

                        if (rs.getString("EMPLOYEE_STATUS_ID") !=
                            null) // && rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG"))
                        {

                            ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                            ps.setInt(1, eid);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<flag>failure2</flag>";
                            } else {

                                ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ");
                                ps.setInt(1, eid);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                if (rs.next()) {


                                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig>";
                                    xml =
 xml + "<dob>" + (rs.getDate("date_of_birth")).getYear() + "</dob>";


                                    /***************  16-08-2007    ***********************/


                                    ps =
  con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                    ps.setInt(1, eid);

                                    rs = ps.executeQuery();
                                    String maxfromdate = "";
                                    String maxsession = "";
                                    if (rs.next()) {
                                        if (rs.getDate("DATE_EFFECTIVE_FROM") !=
                                            null) {
                                            maxfromdate =
                                                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_EFFECTIVE_FROM"));
                                            System.out.println("max from date :" +
                                                               rs.getDate("DATE_EFFECTIVE_FROM"));
                                        } else {

                                            maxfromdate = "empty";
                                        }
                                        if (rs.getString("DATE_EFFECTIVE_FROM_SESSION") !=
                                            null) {
                                            maxsession =
                                                    rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                        } else {
                                            maxsession = "FN";
                                        }
                                    }
                                    xml =
 xml + "<maxfromdate>" + maxfromdate + "</maxfromdate><maxsession>" +
   maxsession + "</maxsession>";


                                    /********************************************************/

                                } else
                                    xml = xml + "<flag>failure</flag>";

                            }
                        }
                        /*      else if(rs.getString("EMPLOYEE_STATUS_ID")!=null && !(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG")))
                                {
                                System.out.println("comes here checking........");
                                       try{
                                        psnew=con.prepareStatement("select EMPLOYEE_ID,relieval_reason_id from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=?");
                                        psnew.setInt(1,eid);
                                        rsnew=psnew.executeQuery();
                                        if(rsnew.next()) {

                                                relreasonid=rsnew.getString("relieval_reason_id");
                                             System.out.println("Relieval Reason Id is "+relreasonid);
                                         }
                                       }
                                       catch(Exception e) {
                                           System.out.println("error in getting reason id");
                                       }
                                         if(relreasonid.equalsIgnoreCase("SUS"))
                                            {
                                              //ok, proceed
                                               try{
                                               ps3=con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ");
                                               ps3.setInt(1,eid);
                                               //ps.setInt(2,offid);
                                               rs3=ps3.executeQuery();
                                               if(rs3.next())
                                               {


                                               xml=xml+"<flag>success</flag><eid>"+eid+"</eid><ename>"+rs3.getString("EMPLOYEE_NAME")+"</ename><desig>"+rs3.getString("DESIGNATION")+"</desig>";
                                               xml=xml+"<dob>"+rs3.getDate("date_of_birth")+"<dob>";
                                               }

                                                   psnew=con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                                   psnew.setInt(1,eid);

                                                   rsnew=psnew.executeQuery();
                                                   String maxfromdate="";
                                                      String maxsession="";
                                                   if(rsnew.next())
                                                   {
                                                       if(rsnew.getDate("DATE_EFFECTIVE_FROM")!=null)
                                                       {
                                                           maxfromdate=new SimpleDateFormat("dd/MM/yyyy").format(rsnew.getDate("DATE_EFFECTIVE_FROM"));
                                                           System.out.println("max from date :"+rsnew.getDate("DATE_EFFECTIVE_FROM"));
                                                       }
                                                       else {

                                                           maxfromdate="empty";
                                                       }
                                                       if(rsnew.getString("DATE_EFFECTIVE_FROM_SESSION")!=null) {
                                                           maxsession=rsnew.getString("DATE_EFFECTIVE_FROM_SESSION");
                                                       }
                                                       else {
                                                           maxsession="FN";
                                                       }
                                                   }
                                                      xml=xml+"<maxfromdate>"+maxfromdate+"</maxfromdate><maxsession>"+maxsession+"</maxsession>";




                                               }catch(Exception e) {
                                                  xml+="<flag>failure</flag>" ;
                                               }


                                            }



                                    }
                                else {
                                      xml=xml+"<flag>failure</flag>";
                                      System.out.println("Failure new ");
                                      sendMessage(response,"Reilval is not possible","ok");
                                  }*/


                    }
                } else {
                    xml = xml + "<flag>failure3</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }


            xml = xml + "</response>";
            out.println(xml);

        } else if (strCommand.equalsIgnoreCase("Suspension")) {
            System.out.println("comes here checking........");
            // try{  eid=Integer.parseInt(request.getParameter("txtEmployeeid"));}catch(Exception e){System.out.println(e);}
            System.out.println(eid);
            xml = "<response><command>Suspension</command>";
            String reason = request.getParameter("reasonid");
            System.out.println(reason);
            System.out.println("employeeid " + eid);

            //whether working or death
            try {
                psnew1 =
                        con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
                psnew1.setInt(1, eid);
                rsnew1 = psnew1.executeQuery();
                if (rsnew1.next()) {
                    String rsnewid = rsnew1.getString("EMPLOYEE_STATUS_ID");
                    System.out.println("id" + rsnewid);
                    System.out.println("before that ");
                    if (rsnew1.getString("EMPLOYEE_STATUS_ID") != null &&
                        !(rsnew1.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG")))
                    // if(rsnewid.equalsIgnoreCase("SUS"))// && (rsnew1.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SUS")))
                    {
                        System.out.println("comes here");


                        try {
                            psnew =
con.prepareStatement("select EMPLOYEE_ID,relieval_reason_id from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=?");
                            psnew.setInt(1, eid);

                            // psnew=con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
                            //   psnew.setInt(1,eid);
                            System.out.println("aaaaaaaa");
                            rsnew = psnew.executeQuery();
                            if (rsnew.next()) {

                                relreasonid =
                                        rsnew.getString("relieval_reason_id");
                                // relreasonid=rsnew.getString("EMPLOYEE_STATUS_ID");
                                System.out.println("Employee Status Id is " +
                                                   relreasonid);

                            }
                            if (reason.equalsIgnoreCase("DTH")) {

                                xml = xml + "<flag>success</flag>";
                                //ok, proceed
                                /*   try{
                             ps3=con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ");
                             ps3.setInt(1,eid);
                             //ps.setInt(2,offid);
                             rs3=ps3.executeQuery();
                             if(rs3.next())
                             {


                             xml=xml+"<flag>success</flag><eid>"+eid+"</eid><ename>"+rs3.getString("EMPLOYEE_NAME")+"</ename><desig>"+rs3.getString("DESIGNATION")+"</desig>";
                             xml=xml+"<dob>"+rs3.getDate("date_of_birth")+"<dob>";
                             }

                                 psnew=con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                 psnew.setInt(1,eid);

                                 rsnew=psnew.executeQuery();
                                 String maxfromdate="";
                                    String maxsession="";
                                 if(rsnew.next())
                                 {
                                     if(rsnew.getDate("DATE_EFFECTIVE_FROM")!=null)
                                     {
                                         maxfromdate=new SimpleDateFormat("dd/MM/yyyy").format(rsnew.getDate("DATE_EFFECTIVE_FROM"));
                                         System.out.println("max from date :"+rsnew.getDate("DATE_EFFECTIVE_FROM"));
                                     }
                                     else {

                                         maxfromdate="empty";
                                     }
                                     if(rsnew.getString("DATE_EFFECTIVE_FROM_SESSION")!=null) {
                                         maxsession=rsnew.getString("DATE_EFFECTIVE_FROM_SESSION");
                                     }
                                     else {
                                         maxsession="FN";
                                     }
                                 }
                                    xml=xml+"<maxfromdate>"+maxfromdate+"</maxfromdate><maxsession>"+maxsession+"</maxsession>";




                             }catch(Exception e) {
                                xml+="<flag>failure</flag>" ;
                             }*/
                            } else {
                                xml += "<flag>failurenew</flag>";
                                // sendMessage(response,"Relieval is not possible","ok");
                            }
                        } catch (Exception e) {

                            System.out.println("error in getting reason id");
                        }
                        // if((relreasonid.equalsIgnoreCase("DTH")))


                    }
                }
            } catch (Exception e) {
                System.out.println("error");
            }
            //    else {

            //    }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);


        }

        else if (strCommand.equalsIgnoreCase("loademp1")) {
            xml = "<response><command>loademp1</command>";
            try {
                eid = Integer.parseInt(request.getParameter("eid"));
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                ps3 =
 con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ");
                ps3.setInt(1, eid);
                //ps.setInt(2,offid);
                rs3 = ps3.executeQuery();
                if (rs3.next()) {


                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs3.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs3.getString("DESIGNATION") + "</desig>";
                    xml =
 xml + "<dob>" + rs3.getDate("date_of_birth") + "<dob>";
                }
            } catch (Exception e) {
                xml += "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("office")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>office</command>";
            try {
                int oid = 0;
                String oname = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps2.setInt(1, oid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><oid>" + oid + "</oid><oname>" +
   rs2.getString("OFFICE_NAME") + "</oname>";
                else
                    xml = xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                ps2.close();
                rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("desig")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>desig</command>";
            try {
                int desigid = 0;
                String designame = "";
                try {
                    desigid =
                            Integer.parseInt(request.getParameter("desigid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                ps2 =
 con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                ps2.setInt(1, desigid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><designame>" + rs2.getString("DESIGNATION") +
   "</designame>";
                else
                    xml = xml + "<flag>failure</flag>";
                ps2.close();
                rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";

            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("dept")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>dept</command>";
            try {
                int oid = 0;
                String oname = "", deptid = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                deptid = request.getParameter("deptid");
                ps2 =
 con.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?");
                ps2.setString(1, deptid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><dname>" + rs2.getString("OTHER_DEPT_NAME") +
   "</dname>";
                else
                    xml = xml + "<flag>failure</flag><err>did</err>";
                ps2.close();
                rs2.close();
                ps2 =
 con.prepareStatement("select OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?");
                ps2.setString(1, deptid);
                ps2.setInt(2, oid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<oname>" + rs2.getString("OTHER_DEPT_OFFICE_NAME") + "</oname>";
                else
                    xml = xml + "<flag>failure</flag><err1>ofid</err1>";
                ps2.close();
                rs2.close();

            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            int txtOffId = 0, txtEmployeeid = 0, txtRel_SLNO = 0;
            Date txtDORelieval = null;
            String rad_DORelieval = "", cmbReason = "", txtRemarks =
                "", Proc_Status = "";
            System.out.println(xml);
            Proc_Status = request.getParameter("cmbStatus");
            System.out.println(Proc_Status);
            // strCommand=request.getParameter("from here");
            txtOffId = Integer.parseInt(request.getParameter("txtOffId"));
            txtEmployeeid =
                    Integer.parseInt(request.getParameter("txtEmployeeid"));

            String[] sd = request.getParameter("txtDORelieval").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtDORelieval = new Date(d.getTime());

            rad_DORelieval = request.getParameter("rad_DORelieval");

            cmbReason = request.getParameter("cmbReason");
            txtRemarks = request.getParameter("txtRemarks");
            System.out.println(txtOffId);
            System.out.println(txtEmployeeid);
            System.out.println(txtRel_SLNO);
            System.out.println(txtDORelieval);
            System.out.println(cmbReason);
            System.out.println("from " +
                               request.getParameter("txtDORelieval"));
            try {
                ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                ps.setInt(1, txtEmployeeid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>failure2</flag>";
                    System.out.println("Failure 2");
                    sendMessage(response,
                                "This Employee already has an unfrezeed Record. \n Inserting a new Reilval is not possible",
                                "ok");
                } else {
                    if (cmbReason.equalsIgnoreCase("TRN")) {
                        System.out.println("TRN");
                        int i = 0;
                        int txtT_OffId = 0;
                        try {
                            txtT_OffId =
                                    Integer.parseInt(request.getParameter("txtT_OffId"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        String radT_Repost = null;
                        radT_Repost = request.getParameter("radT_Repost");
                        Date txtT_proc_Date = null;
                        String s = null;
                        s = request.getParameter("txtT_proc_Date");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdTd[] =
                                request.getParameter("txtT_proc_Date").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdTd[2]),
                         Integer.parseInt(sdTd[1]) - 1,
                         Integer.parseInt(sdTd[0]));
                            d = c.getTime();
                            txtT_proc_Date = new Date(d.getTime());
                        }
                        String txtT_proc_No =
                            request.getParameter("txtT_proc_No");
                        try {
                            System.out.println("inside query" + s);


                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_TRN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);
                            cs.setInt(8, txtT_OffId);
                            cs.setString(9, radT_Repost);
                            cs.setDate(10, txtT_proc_Date);
                            cs.setString(11, txtT_proc_No);
                            cs.setString(13, "insert");
                            cs.setString(14, Proc_Status);
                            cs.setInt(15, 0);
                            cs.setInt(16, 0);

                            cs.setString(17, updatedby);
                            cs.setTimestamp(18, ts);

                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(12,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(12);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";

                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number  " +
                                            txtRel_SLNO +
                                            "  has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else if (cmbReason.equalsIgnoreCase("DPN")) {
                        System.out.println(cmbReason);
                        String txtD_ODep_Id = "";
                        int i = 0;
                        try {
                            txtD_ODep_Id =
                                    request.getParameter("txtD_ODep_Id");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        int txtD_ODep_OffId = 0;
                        try {
                            txtD_ODep_OffId =
                                    Integer.parseInt(request.getParameter("txtD_ODep_OffId"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        String txtD_Period =
                            request.getParameter("txtD_Period");
                        Date txtD_Date = null;
                        String s = null;
                        s = request.getParameter("txtD_Date");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtD_Date").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtD_Date = new Date(d.getTime());
                        }

                        try {
                            System.out.println("inside query" + s);


                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_DPN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            cs.setString(8, txtD_ODep_Id);
                            cs.setInt(9, txtD_ODep_OffId);
                            cs.setString(10, txtD_Period);
                            cs.setDate(11, txtD_Date);
                            cs.setString(13, "insert");
                            cs.setString(14, Proc_Status);
                            cs.setInt(15, 0);
                            cs.setInt(16, 0);

                            cs.setString(17, updatedby);
                            cs.setTimestamp(18, ts);
                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(12,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            System.out.println("heloo");
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(12);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    }


                    /*  study leave sk 14/10/2006   */
                    else if (cmbReason.equalsIgnoreCase("STU")) {
                        System.out.println(cmbReason);
                        String txtD_ODep_Id = "";
                        int i = 0;
                        String strInst_Name =
                            request.getParameter("txtInst_Name");
                        String strInst_Location =
                            request.getParameter("txtInst_Location");
                        String strInst_Course =
                            request.getParameter("txtCourse_Name");
                        Date txtSDate_From = null;
                        String s = null;
                        s = request.getParameter("txtSDate_From");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtSDate_From").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtSDate_From = new Date(d.getTime());
                        }

                        Date txtSDate_To = null;
                        s = null;
                        s = request.getParameter("txtSDate_To");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtSDate_To").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtSDate_To = new Date(d.getTime());
                        }

                        try {
                            System.out.println("inside query" + s);

                            System.out.println("here is ok1");
                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_SDU_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);

                            cs.setString(8, strInst_Name);
                            cs.setString(9, strInst_Location);
                            cs.setString(10, strInst_Course);
                            cs.setDate(11, txtSDate_From);
                            cs.setDate(12, txtSDate_To);

                            cs.setString(14, "insert");
                            cs.setString(15, Proc_Status);
                            cs.setInt(16, 0);
                            cs.setInt(17, 0);

                            cs.setString(18, updatedby);
                            cs.setTimestamp(19, ts);

                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(13,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            System.out.println("heloo");
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(13);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    }

                    /*  end of study leave   */
                    else if (cmbReason.equalsIgnoreCase("PRO")) {
                        System.out.println(cmbReason);
                        int txtP_OffId = 0, i = 0;
                        try {
                            txtP_OffId =
                                    Integer.parseInt(request.getParameter("txtP_OffId"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        int txtP_desigId = 0;
                        try {
                            txtP_desigId =
                                    Integer.parseInt(request.getParameter("txtP_desigId"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        String radP_Repost = null;
                        radP_Repost = request.getParameter("radP_Repost");
                        Date txtP_proc_Date = null;
                        String s = null;
                        s = request.getParameter("txtP_proc_Date");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdTd[] =
                                request.getParameter("txtP_proc_Date").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdTd[2]),
                         Integer.parseInt(sdTd[1]) - 1,
                         Integer.parseInt(sdTd[0]));
                            d = c.getTime();
                            txtP_proc_Date = new Date(d.getTime());
                        }
                        String txtP_proc_No =
                            request.getParameter("txtP_proc_No");

                        String dep_id = request.getParameter("txtP_depid");
                        System.out.println("department id..." + dep_id);

                        try {
                            System.out.println("inside query");


                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_PRO_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);
                            cs.setInt(8, txtP_OffId);
                            cs.setInt(9, txtP_desigId);

                            cs.setString(10, radP_Repost);
                            cs.setDate(11, txtP_proc_Date);
                            cs.setString(12, txtP_proc_No);

                            cs.setString(14, "insert");
                            cs.setString(15, Proc_Status);
                            cs.setInt(16, 0);
                            cs.setInt(17, 0);

                            cs.setString(18, updatedby);
                            cs.setTimestamp(19, ts);
                            cs.setString(20, dep_id);

                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(13,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(13);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else if (cmbReason.equalsIgnoreCase("LLV")) {
                        System.out.println(cmbReason);
                        String cmbLL_TypeId = null;
                        int i = 0;
                        cmbLL_TypeId = (request.getParameter("cmbLL_TypeId"));
                        String txtLL_purpose = null;
                        txtLL_purpose =
                                (request.getParameter("txtLL_purpose"));
                        Date txtL_Period_From = null;
                        String s = null;
                        s = request.getParameter("txtL_Period_From");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtL_Period_From").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtL_Period_From = new Date(d.getTime());
                        }
                        Date txtL_Period_To = null;
                        s = null;
                        s = request.getParameter("txtL_Period_To");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtL_Period_To").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtL_Period_To = new Date(d.getTime());
                        }

                        try {
                            System.out.println("inside query" + s);

                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_LLV_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);
                            cs.setString(8, cmbLL_TypeId);
                            cs.setString(9, txtLL_purpose);
                            cs.setDate(10, txtL_Period_From);
                            cs.setDate(11, txtL_Period_To);
                            cs.setString(13, "insert");
                            cs.setString(14, Proc_Status);
                            cs.setInt(15, 0);
                            cs.setInt(16, 0);

                            cs.setString(17, updatedby);
                            cs.setTimestamp(18, ts);

                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(12,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(12);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number  " +
                                            txtRel_SLNO +
                                            "  has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else if (cmbReason.equalsIgnoreCase("DVN")) {
                        System.out.println("DVN" + cmbReason);
                        int txtDv_OffId = 0, i = 0;
                        try {
                            txtDv_OffId =
                                    Integer.parseInt(request.getParameter("txtDv_OffId"));
                        } catch (Exception e) {
                        }
                        Date txtDv_Date = null;
                        String s = null;
                        s = request.getParameter("txtDv_Date");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtDv_Date").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtDv_Date = new Date(d.getTime());
                        }
                        try {
                            System.out.println("inside query" + s);

                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_DVN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);
                            cs.setInt(8, txtDv_OffId);
                            cs.setDate(9, txtDv_Date);
                            cs.setString(11, "insert");
                            cs.setString(12, Proc_Status);
                            //cs.setInt(13,0);
                            //cs.setInt(14,0);

                            cs.setString(13, updatedby);
                            cs.setTimestamp(14, ts);

                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(10,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(10);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number  " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else if (cmbReason.equalsIgnoreCase("SUS") ||
                               cmbReason.equalsIgnoreCase("VRS") ||
                               cmbReason.equalsIgnoreCase("DIS") ||
                               cmbReason.equalsIgnoreCase("SAN") ||
                               cmbReason.equalsIgnoreCase("DTH") ||
                               cmbReason.equalsIgnoreCase("ABR")) {
                        int i = 0;

                        try {
                            System.out.println("inside query in sus");

                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_OTH_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);

                            cs.setString(9, "insert");
                            cs.setString(10, Proc_Status);
                            cs.setInt(11, 0);
                            cs.setInt(12, 0);

                            cs.setString(13, updatedby);
                            cs.setTimestamp(14, ts);

                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(8, java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(8);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }


                    }
                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Error :" + e);
            }
            xml = xml + "</response>";

        }
        System.out.println(xml);
        //out.println(xml);
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
