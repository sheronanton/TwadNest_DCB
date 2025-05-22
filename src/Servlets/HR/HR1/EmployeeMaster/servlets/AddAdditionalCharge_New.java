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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class AddAdditionalCharge_New extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        //response.setContentType(CONTENT_TYPE);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        Connection con = null;
        try {

        	LoadDriver driver=new LoadDriver();
            con=driver.getConnection();

        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null,rs2=null,rss=null,rss1=null;
        PreparedStatement ps = null,ps1=null,pss=null,pss1=null;
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

            


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        
        
        if(strCommand.equalsIgnoreCase("load_grid1"))
        {
        	System.out.println("load grid function");
        	try{
        		 xml+= "<response><command>load_grid</command>";
        		 String strDef1=null;

                 String sql1="SELECT employee_id, " +
                		 "  designation, " +
                		 "  office_name, " +
                		 "  date_effective_from, " +
                		 "  slno, " +
                		 "  addl_charge_type, " +
                		 "  ADDL_CHARGE_STATUS, " +
                		 "  process_flow_status_id " +
                		 "FROM " +
                		 "  (SELECT employee_id, " +
                		 "    office_id, " +
                		 "    designation_id, " +
                		 "    date_effective_from, " +
                		 "    slno, " +
                		 "    addl_charge_type, " +
                		 "    ADDL_CHARGE_STATUS, " +
                		 "    process_flow_status_id " +
                		 "  FROM HRM_EMP_ADDL_CHARGE " +
                		 "  WHERE(ADDL_CHARGE_STATUS NOT IN 'CL' " +
                		 "  OR ADDL_CHARGE_STATUS        IS NULL) " +
                		 "  ) a " +
                		 "LEFT OUTER JOIN " +
                		 "  ( SELECT office_id AS off_id,office_name FROM com_mst_offices " +
                		 "  ) b " +
                		 "ON a.office_id=b.off_id " +
                		 "LEFT OUTER JOIN " +
                		 "  ( SELECT designation_id AS desig_id,designation FROM hrm_mst_designations " +
                		 "  ) c " +
                		 "ON a.designation_id=c.desig_id " +
                		 "ORDER BY slno";
                                         
                         ps1=con.prepareStatement(sql1); 
                         
                         rs2=ps1.executeQuery();
                         int count=0;
                         while(rs2.next())
                         {
                        	 int employee_id=rs2.getInt("employee_id");
                             xml = xml + "<employee_id>" + rs2.getInt("employee_id") + "</employee_id>";
                             System.out.println("sl no..."+employee_id);
                             
                             int slnos=rs2.getInt("SLNO");
                             xml = xml + "<SLNO>" + rs2.getInt("SLNO") + "</SLNO>";
                             System.out.println("sl no..."+slnos);
                             
                             String desig=rs2.getString("designation");
                             xml = xml + "<designations>" + rs2.getString("designation") + "</designations>";
                             System.out.println("designatuion...."+desig);
                             
                             String office=rs2.getString("office_name");
                             xml = xml + "<office_names>" + rs2.getString("office_name") + "</office_names>";
                             System.out.println("office..."+office);
                             
                             java.util.Date dt=rs2.getDate("date_effective_from");
                             
                           //  System.out.println("date_effective_from ....."+date_effective_from);
                            String[] sd1=dt.toString().split("-");
                            strDef1=sd1[2]+"/"+sd1[1]+"/"+sd1[0];
                            
                            /* if(rs2.getDate("date_effective_from")!=null)
                             {
                            String[] sd1=rs.getDate("date_effective_from").toString().split("-");
                            strDef1=sd1[2]+"/"+sd1[1]+"/"+sd1[0];
                             }*/
                             System.out.println("date..."+strDef1);
                             xml = xml + "<date_effective_froms>" + strDef1 + "</date_effective_froms>";
                             
                             System.out.println("date..."+strDef1);
                             xml = xml + "<process_flow_status_id>" + rs2.getString("process_flow_status_id") + "</process_flow_status_id>";
                             
                             
                             
                             xml = xml + "<ADDL_CHARGE_STATUS>" + rs2.getString("ADDL_CHARGE_STATUS") + "</ADDL_CHARGE_STATUS>";
                             count++;
                             
                           
                         }
                         xml = xml + "<countss>" + count + "</countss>";
                         xml = xml + "<flag>success</flag>";
        		 
        	}catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            
            System.out.println("XML ==== "+xml);
            try {
				rs2.close();       ps1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
          
        }
        

        if (strCommand.equalsIgnoreCase("loademp")) {
            xml = "<response><command>loademp</command>";
            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);
            try {
                HttpSession session = request.getSession(false);
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
                }

                if (flag) {

                    //con.setAutoCommit(false);
                    ps =
  con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID!='DL'");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();

                    if (rs.next()) {

                        String status = rs.getString("PROCESS_FLOW_STATUS_ID");
                        if (status.equalsIgnoreCase("CR") ||
                            status.equalsIgnoreCase("MD")) {
                            xml = xml + "<flag>exists</flag>";
                        } else if (status.equalsIgnoreCase("FR")) {
                            xml = xml + "<flag>freezed</flag>";
                        }

                    } else {
                        ps =
  con.prepareStatement("select EMPLOYEE_NAME ||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();

                        if (rs.next()) {

                            xml =
 xml + "<ename>" + rs.getString("EMPLOYEE_NAME") + "</ename>";
                            xml =
 xml + "<egpf>" + rs.getLong("GPF_NO") + "</egpf>";
                            rs.close();
                            ps.close();
                            xml = xml + "<flag>success</flag>";
                        } else {
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }


        if (strCommand.equalsIgnoreCase("loadempedit")) {
            xml = "<response><command>loadempedit</command>";
            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);
            try {

                HttpSession session = request.getSession(false);
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
                }
                rs.close();
                ps.close();
                /*  else  if(up.getEmployeeId()!=strcode)
                 {
                               int OfficeId=0;
                               String sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                               ps=con.prepareStatement(sql);
                               ps.setInt(1,strcode);
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
                                           int offid=rs.getInt("OFFICE_ID");
                                           if(offid!=OfficeId)
                                           {
                                               System.out.println("Admin Session:"+session.getAttribute("Admin"));
                                                       if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                                                       {//response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                                        xml=xml+"<flag>failure1</flag>";
                                                        flag=false;
                                                       }
                                           }
                                       }
                                       else
                                       {
                                              // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                                               xml=xml+"<flag>failure2</flag>";
                                           flag=false;
                                       }

                               }
                               else{
                                   //if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                                   {
                                   xml=xml+"<flag>failure3</flag>";
                                   flag=false;
                                   }
                               }
                 }
                 else {

                   //  xml=xml+"<flag>failure4</flag>";
                    //flag=false;
                 }
                       */
                if (flag) {
                    //con.setAutoCommit(false);
                    System.out.println("ok1");
                    //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=?"); // and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                    //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR'" );

                    ps =
  con.prepareStatement("select a.EMPLOYEE_ID, b.EMPLOYEE_NAME||decode(b.EMPLOYEE_INITIAL,null,' ','.'||b.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME , " +
                       " b.GPF_NO, a.OFFICE_ID,a.DESIGNATION_ID,a.DATE_EFFECTIVE_FROM, " +
                       " a.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,a.PROCESS_FLOW_STATUS_ID, " +
                       " a.EMPLOYEE_STATUS_ID,a.LEAVE_TYPE_CODE,a.DATE_EFFECTIVE_FROM_SESSION,c.SERVICE_GROUP_ID,a.OFFICE_WING_SINO " +
                       " from HRM_EMP_CURRENT_POSTING a " +
                       " inner join HRM_MST_EMPLOYEES b on b.employee_id=a.employee_id  " +
                       " left outer join HRM_MST_DESIGNATIONS c on c.DESIGNATION_ID=a.DESIGNATION_ID " +
                       " where a.EMPLOYEE_ID=?");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    boolean oldrec = false;
                    String status = null;
                    if (!rs.next()) {
                        //  data in temporary table is not available
                        //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=? and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                        ps =
  con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, HRM_EMP_CURRENT_POSTING.OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,HRM_EMP_CURRENT_POSTING.DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID,w.OFFICE_WING_SINO from HRM_EMP_CURRENT_WING w,HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR' and HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=w.EMPLOYEE_ID(+)");
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            oldrec = true; // main table is available
                        } else {
                            oldrec = false; // main table is not available
                        }
                    } else {
                        oldrec = true; //  data in temporary table is available
                    }
                    if (oldrec == true) {
                        System.out.println("ok2");
                        //if(rs.next())
                        {
                            System.out.println("here 1");

                            xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("EMPLOYEE_ID") + "</EMPLOYEE_ID>";
                            xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                            xml =
 xml + "<GPF_NO>" + rs.getInt("GPF_NO") + "</GPF_NO>";
                            xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                            xml =
 xml + "<DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";
                            System.out.println("here 2");
                            if (rs.getDate("DATE_EFFECTIVE_FROM") != null) {
                                String[] sd1 =
                                    rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                                String od =
                                    sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + od + "</DATE_EFFECTIVE_FROM>";
                            } else {
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + rs.getDate("DATE_EFFECTIVE_FROM") +
   "</DATE_EFFECTIVE_FROM>";

                            }
                            System.out.println("here 3");
                            xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                            xml =
 xml + "<OFFICE_GRADE>" + rs.getString("OFFICE_GRADE") + "</OFFICE_GRADE>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                            xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                            xml =
 xml + "<LEAVE_TYPE>" + rs.getString("LEAVE_TYPE_CODE") + "</LEAVE_TYPE>";
                            xml =
 xml + "<DATE_EFFECTIVE_FROM_SESSION>" + rs.getString("DATE_EFFECTIVE_FROM_SESSION") +
   "</DATE_EFFECTIVE_FROM_SESSION>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<SERVICE_GROUP_ID>" + rs.getInt("SERVICE_GROUP_ID") +
   "</SERVICE_GROUP_ID>";

                            status = rs.getString("PROCESS_FLOW_STATUS_ID");

                            int wing = 0;
                            System.out.println("here 4");
                            if (rs.getString("OFFICE_WING_SINO") != null) {
                                wing =
Integer.parseInt(rs.getString("OFFICE_WING_SINO"));
                            }
                            xml = xml + "<WING>" + wing + "</WING>";
                            System.out.println("here 5");
                            /* if(status!=null && status.equalsIgnoreCase("FR"))
                                         {
                                                    xml=xml+"<flag>freezed</flag>";
                                         }
                                         else {
                                             {
                                                        xml=xml+"<flag>success</flag>";
                                             }
                                         }*/
                            System.out.println("here is ok1");

                            System.out.println("here is ok2");

                            xml = xml + "<flag>success</flag>";
                            System.out.println("test");
                            if (session.getAttribute("Admin") != null &&
                                ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                                xml = xml + "<admin>YES</admin>";
                            } else {
                                xml = xml + "<admin>NO</admin>";
                            }


                        }

                    } else { // there is no old record is available so we have to create a new record

                        //  checking for freezed record
                        /* ps=con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'" );
                             ps.setInt(1,strcode);
                             rs=ps.executeQuery();

                             if(rs.next()) {

                                    //String status=rs.getString("PROCESS_FLOW_STATUS_ID");
                                     xml=xml+"<flag>freezed</flag>";

                             }
                             else */
                        {
                            //xml=xml+"<flag>failure</flag>";
                            // load the employee basic information to crate a new record
                            ps =
  con.prepareStatement("select EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO from HRM_MST_EMPLOYEES  where EMPLOYEE_ID=?");
                            ps.setInt(1, strcode);
                            rs = ps.executeQuery();

                            if (rs.next()) {

                                xml =
 xml + "<ename>" + rs.getString("EMPLOYEE_NAME") + "</ename>";
                                xml =
 xml + "<egpf>" + rs.getLong("GPF_NO") + "</egpf>";
                                rs.close();
                                ps.close();
                                xml = xml + "<flag>success1</flag>";
                            } else {
                                xml = xml + "<flag>failure</flag>";
                            }

                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }


        if (strCommand.equalsIgnoreCase("loadempvalid")) {
            xml = "<response><command>loadempvalid</command>";
            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);
            try {

                HttpSession session = request.getSession(false);
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
                                System.out.println("Admin Session:" +
                                                   session.getAttribute("Admin"));
                                if (session.getAttribute("Admin") == null ||
                                    !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) { //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                    xml = xml + "<flag>failure1</flag>";
                                    flag = false;
                                }
                            }
                        } else {
                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");

                            xml = xml + "<flag>failure2</flag>";
                            flag = false;

                        }

                    } else {
                        // if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                        {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;
                        }
                    }
                } else {

                    //  xml=xml+"<flag>failure4</flag>";
                    //flag=false;
                }

                if (flag) {
                    //con.setAutoCommit(false);
                    System.out.println("ok1");
                    ps =
  con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID,OFFICE_WING_SINO from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=?"); // and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                    //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR'" );
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    boolean oldrec = false;
                    if (!rs.next()) {
                        //  data in temporary table is not available
                        //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=? and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                        //  checking for freezed record
                        /*  ps=con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'" );
                              ps.setInt(1,strcode);
                              rs=ps.executeQuery();

                              if(rs.next()) {

                                     String status=rs.getString("PROCESS_FLOW_STATUS_ID");
                                      xml=xml+"<flag>freezed</flag>";

                              }
                              else */
                        {
                            xml = xml + "<flag>norecord</flag>";
                        }
                    } else {
                        oldrec = true; //  data in temporary table is available
                    }
                    if (oldrec == true) {
                        System.out.println("ok2");
                        //if(rs.next())
                        {


                            xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("EMPLOYEE_ID") + "</EMPLOYEE_ID>";
                            xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                            xml =
 xml + "<GPF_NO>" + rs.getInt("GPF_NO") + "</GPF_NO>";
                            xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                            xml =
 xml + "<DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";

                            if (rs.getDate("DATE_EFFECTIVE_FROM") != null) {
                                String[] sd1 =
                                    rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                                String od =
                                    sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + od + "</DATE_EFFECTIVE_FROM>";
                            } else {
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + rs.getDate("DATE_EFFECTIVE_FROM") +
   "</DATE_EFFECTIVE_FROM>";

                            }

                            xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                            xml =
 xml + "<OFFICE_GRADE>" + rs.getString("OFFICE_GRADE") + "</OFFICE_GRADE>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                            xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                            xml =
 xml + "<LEAVE_TYPE>" + rs.getString("LEAVE_TYPE_CODE") + "</LEAVE_TYPE>";
                            xml =
 xml + "<DATE_EFFECTIVE_FROM_SESSION>" + rs.getString("DATE_EFFECTIVE_FROM_SESSION") +
   "</DATE_EFFECTIVE_FROM_SESSION>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<SERVICE_GROUP_ID>" + rs.getInt("SERVICE_GROUP_ID") +
   "</SERVICE_GROUP_ID>";

                            // rs.close();
                            //  ps.close();

                            int wing = 0;

                            if (rs.getString("OFFICE_WING_SINO") != null) {
                                wing =
Integer.parseInt(rs.getString("OFFICE_WING_SINO"));
                            }
                            xml = xml + "<WING>" + wing + "</WING>";

                            System.out.println("here is ok1");
                            if (rs.getString("EMPLOYEE_STATUS_ID") != null &&
                                rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("STU")) {

                                /*   sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                                              ps=con.prepareStatement(sql);
                                              ps.setInt(1,strcode);
                                              rs=ps.executeQuery();
                                              int OfficeId=0;
                                              if(rs.next()) {
                                                  OfficeId=rs.getInt("CONTROLLING_OFFICE_ID");
                                              }*/

                                System.out.println("ok123");
                                ps =
  con.prepareStatement("select  max(JOINING_REPORT_ID)  rep_id,INSTITUTION_NAME,INSTITUTION_LOCATION,COURSE_NAME from HRM_EMP_STU_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD' or PROCESS_FLOW_STATUS_ID='FR') group by INSTITUTION_NAME,INSTITUTION_LOCATION,COURSE_NAME");
                                ps.setInt(1, strcode);
                                //ps.setInt(2,OfficeId);
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    xml =
 xml + "<INSTITUTION_NAME>" + rs.getString("INSTITUTION_NAME") +
   "</INSTITUTION_NAME>";
                                    xml =
 xml + "<INSTITUTION_LOCATION>" + rs.getString("INSTITUTION_LOCATION") +
   "</INSTITUTION_LOCATION>";
                                    xml =
 xml + "<COURSE_NAME>" + rs.getString("COURSE_NAME") + "</COURSE_NAME>";
                                }
                            }
                            System.out.println("here is ok2");


                            xml = xml + "<flag>success</flag>";

                            System.out.println("test");
                            if (session.getAttribute("Admin") != null &&
                                ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                                xml = xml + "<admin>YES</admin>";
                            } else {
                                xml = xml + "<admin>NO</admin>";
                            }

                        }
                        /*   else
                                    {
                                        ps=con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'" );
                                        ps.setInt(1,strcode);
                                        rs=ps.executeQuery();

                                        if(rs.next()) {

                                               String status=rs.getString("PROCESS_FLOW_STATUS_ID");
                                                xml=xml+"<flag>freezed</flag>";

                                        }
                                        else
                                        {
                                                xml=xml+"<flag>failure</flag>";
                                        }
                                    }
                                    */
                    }

                }

            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }


        if (strCommand.equalsIgnoreCase("loadempview")) {
        	strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);
            xml = "<response><command>loadempview</command>";
            try {

                HttpSession session = request.getSession(false);
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
                                System.out.println("Admin Session:" +
                                                   session.getAttribute("Admin"));
                                if (session.getAttribute("Admin") == null ||
                                    !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) { //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                    xml = xml + "<flag>failure1</flag>";
                                    flag = false;
                                }
                            }
                        } else {
                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");

                            xml = xml + "<flag>failure2</flag>";
                            flag = false;

                        }

                    } else {
                        // if(session.getAttribute("Admin")==null || !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                        {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;
                        }
                    }
                } else {

                    //  xml=xml+"<flag>failure4</flag>";
                    //flag=false;
                }

                if (flag) {
                    //con.setAutoCommit(false);
                    System.out.println("ok1");
                    ps =
  con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID='FR'"); // and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')" );
                    //ps=con.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ',EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,HRM_EMP_CURRENT_POSTING.DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS where HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=? and  HRM_EMP_CURRENT_POSTING.PROCESS_FLOW_STATUS_ID!='FR'" );
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    boolean oldrec = false;
                    if (!rs.next()) {
                        xml = xml + "<flag>norecord</flag>";

                    } else {
                        oldrec = true; //  data in temporary table is available
                    }
                    if (oldrec == true) {
                        System.out.println("ok2");
                        //if(rs.next())
                        {


                            xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("EMPLOYEE_ID") + "</EMPLOYEE_ID>";
                            xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                            xml =
 xml + "<GPF_NO>" + rs.getInt("GPF_NO") + "</GPF_NO>";
                            xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                            xml =
 xml + "<DESIGNATION_ID>" + rs.getInt("DESIGNATION_ID") + "</DESIGNATION_ID>";

                            if (rs.getDate("DATE_EFFECTIVE_FROM") != null) {
                                String[] sd1 =
                                    rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                                String od =
                                    sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + od + "</DATE_EFFECTIVE_FROM>";
                            } else {
                                xml =
 xml + "<DATE_EFFECTIVE_FROM>" + rs.getDate("DATE_EFFECTIVE_FROM") +
   "</DATE_EFFECTIVE_FROM>";

                            }

                            xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                            xml =
 xml + "<OFFICE_GRADE>" + rs.getString("OFFICE_GRADE") + "</OFFICE_GRADE>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                            xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                            xml =
 xml + "<LEAVE_TYPE>" + rs.getString("LEAVE_TYPE_CODE") + "</LEAVE_TYPE>";
                            xml =
 xml + "<DATE_EFFECTIVE_FROM_SESSION>" + rs.getString("DATE_EFFECTIVE_FROM_SESSION") +
   "</DATE_EFFECTIVE_FROM_SESSION>";
                            xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                            xml =
 xml + "<SERVICE_GROUP_ID>" + rs.getInt("SERVICE_GROUP_ID") +
   "</SERVICE_GROUP_ID>";

                            rs.close();
                            ps.close();

                            /*    wing  */


                            int wing = 0;
                            ps =
  con.prepareStatement("select OFFICE_WING_SINO from HRM_EMP_CURRENT_WING where employee_id=?");
                            ps.setInt(1, strcode);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                if (rs.getString("OFFICE_WING_SINO") != null) {
                                    wing =
Integer.parseInt(rs.getString("OFFICE_WING_SINO"));
                                }
                            }
                            xml = xml + "<WING>" + wing + "</WING>";

                            /* wing  */


                            xml = xml + "<flag>success</flag>";
                        }

                    }

                }
                
                try{
                	String sqls="SELECT process_flow_status_id FROM HRM_EMP_ADDL_CHARGE WHERE employee_id=?";
                	pss=con.prepareStatement(sqls);
                	pss.setInt(1,strcode);
                	rss=pss.executeQuery();
                	int counts=0;
                	String pro_status_id="";
                	while(rss.next())
                	{
                		counts++;
                		pro_status_id=rss.getString("process_flow_status_id");
                		System.out.println("pro_status_id === "+pro_status_id);
                		if(pro_status_id.equalsIgnoreCase("FR")){
                			
                			String strDef1=null;
                            
                            try
                            {
                               String sql1="SELECT employee_id, " +
                            		   "  designation, " +
                            		   "  office_name, " +
                            		   "  date_effective_from, " +
                            		   "  slno, " +
                            		   "  addl_charge_type, " +
                            		   "  process_flow_status_id " +
                            		   "FROM " +
                            		   "  (SELECT employee_id, " +
                            		   "    office_id, " +
                            		   "    designation_id, " +
                            		   "    date_effective_from, " +
                            		   "    slno, " +
                            		   "    addl_charge_type, " +
                            		   "    process_flow_status_id " +
                            		   "  FROM HRM_EMP_ADDL_CHARGE " +
                            		   "  WHERE employee_id            ='"+strcode+"' " +
                            		   "  AND (ADDL_CHARGE_STATUS NOT IN 'CL' " +
                            		   "  OR ADDL_CHARGE_STATUS       IS NULL) " +
                            		   "  AND process_flow_status_id   ='FR' " +
                            		   "  ) a " +
                            		   "LEFT OUTER JOIN " +
                            		   "  ( SELECT office_id AS off_id,office_name FROM com_mst_offices " +
                            		   "  ) b " +
                            		   "ON a.office_id=b.off_id " +
                            		   "LEFT OUTER JOIN " +
                            		   "  ( SELECT designation_id AS desig_id,designation FROM hrm_mst_designations " +
                            		   "  ) c " +
                            		   "ON a.designation_id=c.desig_id " +
                            		   "ORDER BY slno";
                                                       
                                       ps1=con.prepareStatement(sql1); 
                                       
                                       rs2=ps1.executeQuery();
                                       int count=0;
                                       while(rs2.next())
                                       {
                                           int slnos=rs2.getInt("SLNO");
                                           xml = xml + "<SLNO>" + rs2.getInt("SLNO") + "</SLNO>";
                                           System.out.println("sl no..."+slno);
                                           
                                           String desig=rs2.getString("designation");
                                           xml = xml + "<designations>" + rs2.getString("designation") + "</designations>";
                                           System.out.println("designatuion...."+desig);
                                           
                                           String office=rs2.getString("office_name");
                                           xml = xml + "<office_names>" + rs2.getString("office_name") + "</office_names>";
                                           System.out.println("office..."+office);
                                           
                                           java.util.Date dt=rs2.getDate("date_effective_from");
                                           
                                         //  System.out.println("date_effective_from ....."+date_effective_from);
                                          String[] sd1=dt.toString().split("-");
                                          strDef1=sd1[2]+"/"+sd1[1]+"/"+sd1[0];
                                          
                                          /* if(rs2.getDate("date_effective_from")!=null)
                                           {
                                          String[] sd1=rs.getDate("date_effective_from").toString().split("-");
                                          strDef1=sd1[2]+"/"+sd1[1]+"/"+sd1[0];
                                           }*/
                                           System.out.println("date..."+strDef1);
                                           xml = xml + "<date_effective_froms>" + strDef1 + "</date_effective_froms>";
                                           count++;
                                           
                                         
                                       }
                                       xml = xml + "<countss>" + count + "</countss>";
                            }
                            catch(Exception e)
                            {
                              System.out.println("Exception e..."+e.getMessage());
                            }
                		}
                	}
                	
                }catch(Exception e)
                {
                	
                }
                
                
                String sss="SELECT * " +
                		"FROM hrm_emp_addl_charge " +
                		"WHERE employee_id          =? " +
                		"AND (process_flow_status_id='CR' " +
                		"OR process_flow_status_id  ='MD')";
                pss1 = con.prepareStatement(sss);
                pss1.setInt(1, strcode);
                 rss1 = pss1.executeQuery();
                int c1=0;
                while(rss1.next())
                {
                	c1++;
                }
if(c1==0)
{
	xml = xml + "<Exist>Not Existed</Exist>";
}
else
{
	xml = xml + "<Exist>Existed</Exist>";
}
                
                

            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        if (strCommand.equalsIgnoreCase("loadempdelete")) {
        	strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);
            xml = "<response><command>loadempdelete</command>";
            try {

                HttpSession session = request.getSession(false);
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

                    xml = xml + "<flag>failure4</flag>";
                    flag = false;
                }

                if (flag) {
                    //con.setAutoCommit(false);
                    System.out.println("ok1");
                    ps =
  con.prepareStatement("select HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID, EMPLOYEE_NAME||decode(EMPLOYEE_INITIAL,null,' ','.'||EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,GPF_NO, OFFICE_ID,DESIGNATION as DESIGNATION_ID,DATE_EFFECTIVE_FROM, HRM_EMP_CURRENT_POSTING_TMP.REMARKS,OFFICE_GRADE,DEPARTMENT_ID,HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID,CUR_POST_REASON_DESC, EMPLOYEE_STATUS_ID,LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,SERVICE_GROUP_ID from HRM_EMP_CURRENT_POSTING_TMP,HRM_MST_EMPLOYEES,HRM_MST_DESIGNATIONS,HRM_MST_CUR_POST_STATUS where HRM_MST_CUR_POST_STATUS.CUR_POST_REASON_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_STATUS_ID and  HRM_MST_DESIGNATIONS.DESIGNATION_ID=HRM_EMP_CURRENT_POSTING_TMP.DESIGNATION_ID and HRM_MST_EMPLOYEES.EMPLOYEE_ID=HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID and  HRM_EMP_CURRENT_POSTING_TMP.EMPLOYEE_ID=? and  (HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='CR' or HRM_EMP_CURRENT_POSTING_TMP.PROCESS_FLOW_STATUS_ID='MD')");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    System.out.println("ok2");
                    if (rs.next()) {


                        xml =
 xml + "<EMPLOYEE_ID>" + rs.getInt("EMPLOYEE_ID") + "</EMPLOYEE_ID>";
                        xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                        xml =
 xml + "<GPF_NO>" + rs.getInt("GPF_NO") + "</GPF_NO>";
                        xml =
 xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID") + "</OFFICE_ID>";
                        xml =
 xml + "<DESIGNATION_ID>" + rs.getString("DESIGNATION_ID") +
   "</DESIGNATION_ID>";

                        if (rs.getDate("DATE_EFFECTIVE_FROM") != null) {
                            String[] sd1 =
                                rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                            String od = sd1[2] + "/" + sd1[1] + "/" + sd1[0];
                            xml =
 xml + "<DATE_EFFECTIVE_FROM>" + od + "</DATE_EFFECTIVE_FROM>";
                        } else {
                            xml =
 xml + "<DATE_EFFECTIVE_FROM>" + rs.getDate("DATE_EFFECTIVE_FROM") +
   "</DATE_EFFECTIVE_FROM>";

                        }

                        xml =
 xml + "<REMARKS>" + rs.getString("REMARKS") + "</REMARKS>";
                        xml =
 xml + "<OFFICE_GRADE>" + rs.getString("OFFICE_GRADE") + "</OFFICE_GRADE>";
                        xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                        xml =
 xml + "<PROCESS_FLOW_STATUS_ID>" + rs.getString("PROCESS_FLOW_STATUS_ID") +
   "</PROCESS_FLOW_STATUS_ID>";
                        xml =
 xml + "<EMPLOYEE_STATUS_ID>" + rs.getString("EMPLOYEE_STATUS_ID") +
   "</EMPLOYEE_STATUS_ID>";
                        xml =
 xml + "<CUR_POST_REASON_DESC>" + rs.getString("CUR_POST_REASON_DESC") +
   "</CUR_POST_REASON_DESC>";
                        xml =
 xml + "<LEAVE_TYPE>" + rs.getString("LEAVE_TYPE_CODE") + "</LEAVE_TYPE>";
                        xml =
 xml + "<DATE_EFFECTIVE_FROM_SESSION>" + rs.getString("DATE_EFFECTIVE_FROM_SESSION") +
   "</DATE_EFFECTIVE_FROM_SESSION>";
                        xml =
 xml + "<DEPARTMENT_ID>" + rs.getString("DEPARTMENT_ID") + "</DEPARTMENT_ID>";
                        xml =
 xml + "<SERVICE_GROUP_ID>" + rs.getInt("SERVICE_GROUP_ID") +
   "</SERVICE_GROUP_ID>";

                        rs.close();
                        ps.close();
                        xml = xml + "<flag>success</flag>";
                    } else {
                        ps =
  con.prepareStatement("select PROCESS_FLOW_STATUS_ID from HRM_EMP_CURRENT_POSTING_TMP  where EMPLOYEE_ID=? and PROCESS_FLOW_STATUS_ID='FR'");
                        ps.setInt(1, strcode);
                        rs = ps.executeQuery();

                        if (rs.next()) {

                            String status =
                                rs.getString("PROCESS_FLOW_STATUS_ID");
                            xml = xml + "<flag>freezed</flag>";

                        } else {
                            xml = xml + "<flag>failure</flag>";
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        if (strCommand.equalsIgnoreCase("SerGroup")) {
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

        ///////////////////////////////////////////////
        out.println(xml);
        System.out.println(xml);
        out.close();
        
        try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
        try {

        	LoadDriver driver=new LoadDriver();
            con=driver.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null,rss=null;
        PreparedStatement ps = null,pss=null;
        Statement st = null;
        boolean flag1 = true;
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

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);

            /*    if(!strCommand.equalsIgnoreCase("Delete")) {
            officegrade=request.getParameter("Office_Grade");
            System.out.println("Office_Grade:"+officegrade);

            statusid=request.getParameter("cmbstatus");
            System.out.println("cmbstatus:"+statusid);

            designation=Integer.parseInt(request.getParameter("cmbDesignation"));
            System.out.println("cmbDesignation:"+designation);

              }
              else {*/
            statusid = request.getParameter("txtstatus");
            System.out.println("cmbstatus:" + statusid);
            //}


        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        PreparedStatement psnew = null;
        ResultSet rsnew = null;
        int max = 0;
        try {
            psnew =
con.prepareStatement("select max(slno) as maxSlNo from HRM_EMP_ADDL_CHARGE where employee_id=?");
            psnew.setInt(1, strcode);
            rsnew = psnew.executeQuery();
            if (rsnew.next()) {
                max = rsnew.getInt("maxSlNo");
                System.out.println("@@@@@@@@@@@@@@@@@@@:::" + max);
                max = max + 1;
                System.out.println(max);
            } else {
                max = max + 1;
                System.out.println("max value in else part is sssssss" + max);
            }
        } catch (Exception e) {
            System.out.println("error in max slno: " + e + slno);
        }
        System.out.println("max sl no is :" + max);

        if (strCommand.equalsIgnoreCase("AddPost")) {


            HttpSession session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            System.out.println("Command :" + strCommand + "");
            strcode = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("assign..... Code::" + strcode);

            officegrade = request.getParameter("Office_Grade1");
            System.out.println("Office_Grade:" + officegrade);

            statusid = request.getParameter("cmbstatus");
            System.out.println("cmbstatus:" + statusid);

            designation =
                    Integer.parseInt(request.getParameter("cmbDesignation1"));
            System.out.println("cmbDesignation:" + designation);


            try {
                java.util.Date dt =
                    new java.util.Date(System.currentTimeMillis());
                java.sql.Timestamp sqldt =
                    new java.sql.Timestamp(dt.getTime());
                System.out.println("Time Stamp:" + sqldt);


                //  if(statusid.equalsIgnoreCase("WKG"))           {

                String addlchargetype = request.getParameter("AddlCharge");
                System.out.println("ADDL charge tyep is ........" +
                                   addlchargetype);

                deptid = request.getParameter("txtDept_Id_work1");
                System.out.println("txtDept_Id_work:" + deptid);

                officeid =
                        Integer.parseInt(request.getParameter("txtOffice_Id1"));
                System.out.println("txtOffice_Id:" + officeid);


                String[] sd1 =
                    request.getParameter("txtWorkDateFrom1").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) -
                         1, Integer.parseInt(sd1[0]));
                java.util.Date d = c.getTime();
                dtfrom = new Date(d.getTime());

                dtfromsession = request.getParameter("optWorkDateFrom1");

                System.out.println("From Date:" + dtfrom + " " +
                                   dtfromsession);

                remarks = request.getParameter("txtworkremark1");
                System.out.println("txtworkremark:" + remarks);
                
               try
               {
            	   
            	   String sssql="SELECT employee_id, " +
            			   "  office_id,DESIGNATION_ID, " +
            			   "  process_flow_status_id, " +
            			   "  to_char(date_effective_from, 'dd/mm/yyyy') as date_effective_from, " +
            			   "  date_effective_from_session, " +
            			   "  addl_charge_status " +
            			   "FROM hrm_emp_addl_charge " +
            			   "WHERE employee_id=?";
               	pss=con.prepareStatement(sssql);
               	pss.setInt(1,strcode);
               	rss=pss.executeQuery();
               	while(rss.next())
               	{
               		int office_id=rss.getInt("office_id");
               		int employee_id=rss.getInt("employee_id");
               		int designid=rss.getInt("DESIGNATION_ID");
               		String date_effective_from=rss.getString("date_effective_from"); 
               		String addl_charge_status=rss.getString("addl_charge_status");
               		String process_flow_status_id=rss.getString("process_flow_status_id");
               		System.out.println("addl_charge_status ==== "+addl_charge_status);
               		System.out.println("date_effective_from ==== "+date_effective_from);
               			if(addl_charge_status.equalsIgnoreCase("CL"))
               			{
               				if(officeid==office_id && employee_id==strcode)
                   			{
               					
                   				int form_year=Integer.parseInt(sd1[2]);
                   				int form_month=Integer.parseInt(sd1[1]);
                   				int form_day=Integer.parseInt(sd1[0]);
                   				
                   				String[] db_array=new String[10];
                   				db_array=date_effective_from.split("/");
                   				System.out.println("date_effective_from ==== "+date_effective_from);
                   				int db_year=Integer.parseInt(db_array[2]);
                   				System.out.println("db_year ==== "+db_year);
                   				int db_month=Integer.parseInt(db_array[1]);
                   				System.out.println("db_month ==== "+db_month);
                   				int db_day=Integer.parseInt(db_array[0]);
                   				System.out.println("db_day ==== "+db_day);
                   				System.out.println("*******()()()()*******()()()*");
                   				System.out.println(form_year+"=="+db_year);
                   				System.out.println(form_month+"=="+db_month);
                   				System.out.println(form_day+"=="+db_day);
                   				
                   				if(form_year==db_year && form_month==db_month && form_day==db_day)
                   				{
                   					 flag1=false;
                   					sendMessage(response,
                                            "Already Exist the Additional Charge details in this date : "+request.getParameter("txtWorkDateFrom1")+" ", "ok");
                   					 break;
                   				}
                   				else
                   				{
                   					flag1=true;
                   				}
                   			}
               			
               			
               		}
               			if(addl_charge_status.equalsIgnoreCase("CR"))
               			{
               				if(officeid==office_id && employee_id==strcode && designation==designid)
                   			{
                   			
               					flag1=false;
                   				
              					 sendMessage(response,
                                           "Already Entered The Same office id, designation to this Employee. Please Change the Designation.", "ok");
              					 break;
                   			}
               				else
               				{
               					flag1=true;
               				}
               			}
//               			else 
//               			{
//               			 
//               				flag1=false;
//               				
//       					 sendMessage(response,
//                                    "Already This Employee has assigned for this office,\nPlease use Revoke Form", "ok");
//               			}
               		
               	}
               	
               	
               
                if(flag1==true)
                {
                	
                	int eid = 0,offid = 0,desnid = 0;
                	 String sss="SELECT EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID " +
                        		"FROM hrm_emp_addl_charge " +
                        		"WHERE employee_id         =? " +
                        		"AND process_flow_status_id='CR'";
                        PreparedStatement pss1 = con.prepareStatement(sss);
                        pss1.setInt(1, strcode);
                        ResultSet rss1 = pss1.executeQuery();
                        int c1=0;
                        while(rss1.next())
                        {
                        	c1++;
                        	eid=rss1.getInt("EMPLOYEE_ID");
                        	offid=rss1.getInt("OFFICE_ID");
                        	desnid=rss1.getInt("DESIGNATION_ID");
                        }
         if(eid==strcode && offid==officeid && desnid==designation)
        {

        	 
        	 sendMessage(response,
                     "Already Entered The Same office id, designation to this Employee.Please Change the Designation.", "ok");
        	 
               }

         else
         {
        	    ps =
        	          con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE" + "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
        	                               "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID," +
        	                               "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,EMPLOYEE_STATUS_ID,updated_by_user_id,ADDL_CHARGE_TYPE,ADDL_CHARGE_STATUS)" +
        	                               " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	                        ps.setInt(1, strcode);
        	                        ps.setInt(2, officeid);
        	                        ps.setInt(3, designation);
        	                        ps.setDate(4, dtfrom);
        	                        ps.setString(5, remarks);
        	                        ps.setString(6, officegrade);
        	                        ps.setString(7, deptid);
        	                        ps.setString(8, "CR");
        	                        // ps.setString(9,statusid);
        	                        // ps.setString(9,null);
        	                        ps.setString(9, dtfromsession);
        	                        ps.setTimestamp(10, sqldt);
        	                       // ps.setInt(11, max);
        	                        ps.setString(11, "WKG");
        	                        ps.setString(12, updatedby);
        	                        ps.setString(13, addlchargetype);
        	                        ps.setString(14, "CR");
        	                        ps.executeUpdate();

        	                        sendMessage(response,
        	                                    "The new Addl. Charge Serial Number has been created successfully", "ok");
        	                   
        	                        

         }
                }
                
                	  
   
               	
               	
               }catch(Exception e)
               {
            	   System.out.println("exception === "+e.getMessage());
               }
                
               
       
                
             
                
            } catch (Exception e) {
                String err = "Record is not Saved. " + e.getMessage();
                sendMessage(response, err, "ok");

            }
        }


        if (strCommand.equalsIgnoreCase("checkPost")) {
            int empid = Integer.parseInt(request.getParameter("empid"));
            int offid = Integer.parseInt(request.getParameter("offid"));
            int desigid = Integer.parseInt(request.getParameter("cmbdesig"));
            xml = "<response>";
            try {

                boolean flag = true;
                ps =
  con.prepareStatement("SELECT * FROM HRM_EMP_CURRENT_POSTING WHERE office_id=? and designation_id=? ");
                //ps.setInt(1,empid);
                ps.setInt(1, offid);
                ps.setInt(2, desigid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    //flag=false;
                } else {
                    xml = xml + "<flag>success</flag>";
                }

            } catch (Exception e) {
                System.out.println("catch........" + e);
                //xml=xml+"<flag></flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        if (strCommand.equalsIgnoreCase("Add1_test")) {

            int newoffice_id =
                Integer.parseInt(request.getParameter("txtOffice_Id1"));
            int newdesignation =
                Integer.parseInt(request.getParameter("cmbDesignation1"));

            String newofficegrade = request.getParameter("Office_Grade1");
            System.out.println("cmbDesignation:" + designation);
            System.out.println("Command :" + strCommand);
            try {
                java.util.Date dt =
                    new java.util.Date(System.currentTimeMillis());
                java.sql.Timestamp sqldt =
                    new java.sql.Timestamp(dt.getTime());
                System.out.println("Time Stamp:" + sqldt);


                if (statusid.equalsIgnoreCase("WKG")) {

                    deptid = request.getParameter("txtDept_Id_work1");

                    System.out.println("txtDept_Id_work:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOffice_Id1"));
                    System.out.println("txtOffice_Id:" + officeid);


                    String[] sd =
                        request.getParameter("txtWorkDateFrom1").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optWorkDateFrom1");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtworkremark1");
                    System.out.println("txtworkremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,SLNO)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, newoffice_id);
                    ps.setInt(3, newdesignation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, newofficegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "CR");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setInt(13, max);


                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("STU")) {

                    String[] sd =
                        request.getParameter("txtStudyDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optStudyDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtstudyremark");
                    System.out.println("txtstudyremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,SLNO)" +
                       " values(?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setDate(2, dtfrom);
                    ps.setString(3, remarks);
                    ps.setString(4, officegrade);
                    ps.setString(5, "CR");
                    ps.setString(6, statusid);
                    ps.setString(7, dtfromsession);
                    ps.setTimestamp(8, sqldt);
                    ps.setInt(9, max);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                }


                else if (statusid.equalsIgnoreCase("DPN")) {

                    deptid = request.getParameter("txtDept_Id");
                    System.out.println("txtDept_Id:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOthOffice_Id"));
                    System.out.println("txtOthOffice_Id:" + officeid);

                    String[] sd =
                        request.getParameter("txtDepDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optDepDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtDepremark");
                    System.out.println("txtDepremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,SLNO)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "CR");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setInt(13, max);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("LLV")) {

                    leavetype = request.getParameter("cmbleavetype");
                    System.out.println("cmbleavetype:" + leavetype);

                    String[] sd =
                        request.getParameter("txtDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtleaveremark");
                    System.out.println("txtleaveremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,SLNO)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, leavetype);
                    ps.setString(9, dtfromsession);
                    ps.setTimestamp(10, sqldt);
                    ps.setInt(11, max);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("SUS")) {
                    String[] sd =
                        request.getParameter("txtSusFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optSusFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtsusreson");
                    System.out.println("txtsusreson:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,SLNO)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setInt(10, max);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");
                }

                /*TRANSIT  */
                else if (statusid.equalsIgnoreCase("TRT")) {
                    String[] sd =
                        request.getParameter("txtTrtFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optTrtFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtTrtreson");
                    System.out.println("txtTrtreson:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,SLNO)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setInt(10, max);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");
                }

                /* UNAUTHERIZED LEAVE   */
                else if (statusid.equalsIgnoreCase("UAL")) {
                    String[] sd =
                        request.getParameter("txtUalFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optUalFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtUalreson");
                    System.out.println("txtUalreson:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setInt(10, max);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");
                }

                /*  COMPULSARY LEAVE */
                else if (statusid.equalsIgnoreCase("CMW")) {
                    String[] sd =
                        request.getParameter("txtCmwFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optCmwFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtCmwreson");
                    System.out.println("txtCmwreson:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,SLNO)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setInt(10, max);

                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");
                }


                else if (statusid.equalsIgnoreCase("ABS")) {
                    String[] sd =
                        request.getParameter("txtAbsFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optAbsFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtabsremark");
                    System.out.println("txtabsremark:" + remarks);

                    ps =
  con.prepareStatement("insert into HRM_EMP_ADDL_CHARGE_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,SLNO)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "CR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setInt(10, max);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Saved Successfully",
                                "ok");

                }

            } catch (Exception e) {
                String err = "Record is not Saved. " + e.getMessage();
                sendMessage(response, err, "ok");

            }

        }
        ////////////////
        if (strCommand.equalsIgnoreCase("Update")) {
            System.out.println("Command :" + strCommand);

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
                if (statusid.equalsIgnoreCase("WKG")) {

                    deptid = request.getParameter("txtDept_Id_work");
                    System.out.println("txtDept_Id_work:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOffice_Id"));
                    System.out.println("txtOffice_Id:" + officeid);

                    int wing = 0;
                    if (request.getParameter("cmbWing") != null) {

                        wing =
Integer.parseInt(request.getParameter("cmbWing"));

                    }

                    String[] sd =
                        request.getParameter("txtWorkDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optWorkDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtworkremark");
                    System.out.println("txtworkremark:" + remarks);

                    con.setAutoCommit(false);


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and ( PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();


                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID,OFFICE_WING_SINO)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "MD");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setString(13, updatedby);
                    ps.setInt(14, wing);
                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                }

                else if (statusid.equalsIgnoreCase("STU")) {

                    String[] sd =
                        request.getParameter("txtStudyDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optStudyDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtstudyremark");
                    System.out.println("txtstudyremark:" + remarks);

                    con.setAutoCommit(false);


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID,DESIGNATION_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setDate(2, dtfrom);
                    ps.setString(3, remarks);
                    ps.setString(4, officegrade);
                    ps.setString(5, "MD");
                    ps.setString(6, statusid);
                    ps.setString(7, dtfromsession);
                    ps.setTimestamp(8, sqldt);
                    ps.setString(9, updatedby);

                    ps.setInt(10, designation);
                    ps.executeUpdate();

                    System.out.println("designation:" + designation);

                    System.out.println("current posting is ok");
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    int OfficeId = 0;
                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }
                    System.out.println("Office Id:" + OfficeId);
                    System.out.println("date :" +
                                       request.getParameter("txtStudyDateFrom"));
                    sd = request.getParameter("txtStudyDateFrom").split("/");
                    int Year1 = Integer.parseInt(sd[2]);
                    ps =
  con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_STU_JOIN_REPORTS where OFFICE_ID=? and JOINING_YEAR=?");
                    ps.setInt(1, OfficeId);
                    ps.setInt(2, Year1);
                    rs = ps.executeQuery();
                    int b = 0;
                    if (rs.next()) {
                        b = rs.getInt("b") + 1;
                        System.out.println("b is " + b);

                    }

                    ps =
  con.prepareStatement("select max(JOINING_REPORT_ID) repid,employee_id,JOINING_YEAR from HRM_EMP_STU_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') group by employee_id,JOINING_YEAR");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    if (!rs.next()) {


                        ps =
  con.prepareStatement("INSERT INTO HRM_EMP_STU_JOIN_REPORTS" +
                       "(OFFICE_ID,JOINING_YEAR,JOINING_REPORT_ID,EMPLOYEE_ID,STUDIES_ACTUAL_JOIN_DATE,SESSION_AN_FN," +
                       "STUDIES_JOIN_REMARKS,PROCESS_FLOW_STATUS_ID,INSTITUTION_NAME,INSTITUTION_LOCATION, COURSE_NAME ,SR_CONTROLLING_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE)" +
                       "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1, OfficeId);
                        ps.setInt(2, Year1);
                        ps.setInt(3, b);
                        ps.setInt(4, strcode);
                        ps.setDate(5, dtfrom);
                        ps.setString(6, dtfromsession);
                        ps.setString(7, remarks);
                        ps.setString(8, "MD");

                        String txtInstName =
                            request.getParameter("txtInst_Name");
                        String txtInstLocation =
                            request.getParameter("txtInst_Location");
                        String txtCourseName =
                            request.getParameter("txtCourse_Name");


                        ps.setString(9, txtInstName);
                        ps.setString(10, txtInstLocation);
                        ps.setString(11, txtCourseName);
                        ps.setInt(12, OfficeId);

                        ps.setString(13, updatedby);
                        ps.setTimestamp(14, ts);

                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                    } else {
                        // xml=xml+"<flag>failure1</flag>";
                        int repid = rs.getInt("repid");
                        int year2 = rs.getInt("JOINING_YEAR");

                        ps =
  con.prepareStatement("update HRM_EMP_STU_JOIN_REPORTS " +
                       "set OFFICE_ID=?,JOINING_YEAR=?,JOINING_REPORT_ID=?,EMPLOYEE_ID=?,STUDIES_ACTUAL_JOIN_DATE=?," +
                       " SESSION_AN_FN=?,STUDIES_JOIN_REMARKS=?,PROCESS_FLOW_STATUS_ID=?,INSTITUTION_NAME=?,INSTITUTION_LOCATION=?," +
                       " COURSE_NAME=? ,SR_CONTROLLING_OFFICE_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? " +
                       " where employee_id=? and OFFICE_ID=? and JOINING_REPORT_ID=? and JOINING_YEAR=?");
                        ps.setInt(1, OfficeId);
                        ps.setInt(2, Year1);
                        ps.setInt(3, repid);
                        ps.setInt(4, strcode);
                        ps.setDate(5, dtfrom);
                        ps.setString(6, dtfromsession);
                        ps.setString(7, remarks);
                        ps.setString(8, "MD");

                        String txtInstName =
                            request.getParameter("txtInst_Name");
                        String txtInstLocation =
                            request.getParameter("txtInst_Location");
                        String txtCourseName =
                            request.getParameter("txtCourse_Name");

                        ps.setString(9, txtInstName);
                        ps.setString(10, txtInstLocation);
                        ps.setString(11, txtCourseName);
                        ps.setInt(12, OfficeId);

                        ps.setString(13, updatedby);
                        ps.setTimestamp(14, ts);

                        ps.setInt(15, strcode);
                        ps.setInt(16, OfficeId);
                        ps.setInt(17, repid);
                        ps.setInt(18, year2);

                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";


                    }


                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                }


                else if (statusid.equalsIgnoreCase("DPN")) {

                    deptid = request.getParameter("txtDept_Id");
                    System.out.println("txtDept_Id:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOthOffice_Id"));
                    System.out.println("txtOthOffice_Id:" + officeid);

                    String[] sd =
                        request.getParameter("txtDepDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optDepDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtDepremark");
                    System.out.println("txtDepremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "MD");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setString(13, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("LLV")) {

                    leavetype = request.getParameter("cmbleavetype");
                    System.out.println("cmbleavetype:" + leavetype);

                    String[] sd =
                        request.getParameter("txtDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtleaveremark");
                    System.out.println("txtleaveremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, leavetype);
                    ps.setString(9, dtfromsession);
                    ps.setTimestamp(10, sqldt);
                    ps.setString(11, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("SUS")) {
                    String[] sd =
                        request.getParameter("txtSusFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optSusFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtsusreson");
                    System.out.println("txtsusreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* TRANSIT */
                else if (statusid.equalsIgnoreCase("TRT")) {
                    String[] sd =
                        request.getParameter("txtTrtFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optTrtFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtTrtreson");
                    System.out.println("txtTrtreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* UNAUTHERIZED LEAVE  */
                else if (statusid.equalsIgnoreCase("UAL")) {
                    String[] sd =
                        request.getParameter("txtUalFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optUalFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtUalreson");
                    System.out.println("txtUalreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }

                /* COMPULSARY WAIT */
                else if (statusid.equalsIgnoreCase("CMW")) {
                    String[] sd =
                        request.getParameter("txtCmwFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optCmwFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtCmwreson");
                    System.out.println("txtCmwreson:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");
                }


                else if (statusid.equalsIgnoreCase("ABS")) {
                    String[] sd =
                        request.getParameter("txtAbsFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optAbsFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtabsremark");
                    System.out.println("txtabsremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "MD");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();

                    con.commit();


                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                }

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


        if (strCommand.equalsIgnoreCase("Delete")) {
            System.out.println("Command :" + strCommand);

            try {

                if (statusid.equalsIgnoreCase("WKG")) {


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Deleted Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("DPN")) {

                    System.out.println("del1");
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    System.out.println("del2");

                    sendMessage(response, "Record is Deleted Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("LLV")) {


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Deleted Successfully",
                                "ok");

                } else if (statusid.equalsIgnoreCase("SUS")) {

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Deleted Successfully",
                                "ok");
                } else if (statusid.equalsIgnoreCase("TRT")) {

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Deleted Successfully",
                                "ok");
                } else if (statusid.equalsIgnoreCase("UAL")) {

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Deleted Successfully",
                                "ok");
                } else if (statusid.equalsIgnoreCase("CMW")) {

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Deleted Successfully",
                                "ok");
                } else if (statusid.equalsIgnoreCase("ABS")) {

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    sendMessage(response, "Record is Deleted Successfully",
                                "ok");

                }

            } catch (Exception e) {
                String err = "Record is not Deleted. " + e.getMessage();

                sendMessage(response, err, "ok");

            }


        }
        //////////////          VALIDATE    //////////////////////////////
        if (strCommand.equalsIgnoreCase("Validate")) {
            System.out.println("Command :" + strCommand);

            try {
                java.util.Date dt =
                    new java.util.Date(System.currentTimeMillis());
                java.sql.Timestamp sqldt =
                    new java.sql.Timestamp(dt.getTime());
                System.out.println("Time Stamp:" + sqldt);

                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);


                if (statusid.equalsIgnoreCase("WKG")) {

                    deptid = request.getParameter("txtDept_Id_work");
                    System.out.println("txtDept_Id_work:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOffice_Id"));
                    System.out.println("txtOffice_Id:" + officeid);

                    String[] sd =
                        request.getParameter("txtWorkDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optWorkDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtworkremark");
                    System.out.println("txtworkremark:" + remarks);


                    int wing = 0;
                    if (request.getParameter("cmbWing") != null) {

                        wing =
Integer.parseInt(request.getParameter("cmbWing"));

                    }
                    con.setAutoCommit(false);

                    int srofficeid = officeid;
                    /*from here  */PreparedStatement psorg =
                        con.prepareStatement("select DESIGNATION_SHORT_NAME from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                    psorg.setInt(1, designation);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        String desc =
                            rsorg.getString("DESIGNATION_SHORT_NAME");
                        System.out.println("Office Id::" + officeid);
                        System.out.println("Designation Desc::" + desc);
                        if (desc != null &&
                            (desc.equalsIgnoreCase("EE") || desc.equalsIgnoreCase("CE") ||
                             desc.equalsIgnoreCase("SE"))) {
                            if (desc.equalsIgnoreCase("EE")) {

                                PreparedStatement psl =
                                    con.prepareStatement("select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_ID=?");
                                psl.setInt(1, officeid);
                                ResultSet rsl = psl.executeQuery();
                                System.out.println("check1");
                                if (rsl.next()) {
                                    String level =
                                        rsl.getString("OFFICE_LEVEL_ID");
                                    System.out.println("Office Level:" +
                                                       level);
                                    if (level.equalsIgnoreCase("HO")) {
                                        srofficeid = officeid;
                                    } else if (level.equalsIgnoreCase("RN")) {
                                        srofficeid = officeid;
                                    } else if (level.equalsIgnoreCase("CL")) {
                                        PreparedStatement psc =
                                            con.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                                        psc.setInt(1, officeid);
                                        ResultSet rsc = psc.executeQuery();
                                        if (rsc.next()) {
                                            srofficeid =
                                                    rsc.getInt("CONTROLLING_OFFICE_ID");
                                        }
                                    } else if (level.equalsIgnoreCase("DN")) {
                                        PreparedStatement psd =
                                            con.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                                        psd.setInt(1, officeid);
                                        ResultSet rsd = psd.executeQuery();
                                        if (rsd.next()) {
                                            int officecl =
                                                rsd.getInt("CONTROLLING_OFFICE_ID");
                                            PreparedStatement psc =
                                                con.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                                            psc.setInt(1, officecl);
                                            ResultSet rsc = psc.executeQuery();
                                            if (rsc.next()) {
                                                srofficeid =
                                                        rsc.getInt("CONTROLLING_OFFICE_ID");
                                            }

                                        }
                                    }
                                }

                            } //EE
                            else if (desc.equalsIgnoreCase("CE") ||
                                     desc.equalsIgnoreCase("SE")) {
                                //officeid=5000;
                                PreparedStatement psd =
                                    con.prepareStatement("select OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID='HO'");
                                ResultSet rsd = psd.executeQuery();
                                if (rsd.next()) {
                                    srofficeid = rsd.getInt("OFFICE_ID");
                                }
                            } // CE  ||  SE
                            else {
                                srofficeid = officeid;

                            }
                        } else {
                            PreparedStatement psl =
                                con.prepareStatement("select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_ID=?");
                            psl.setInt(1, officeid);
                            ResultSet rsl = psl.executeQuery();
                            System.out.println("else part check SD");
                            if (rsl.next()) {
                                if (rsl.getString("OFFICE_LEVEL_ID").equalsIgnoreCase("SD")) {
                                    PreparedStatement psc =
                                        con.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                                    psc.setInt(1, officeid);
                                    ResultSet rsc = psc.executeQuery();
                                    if (rsc.next()) {
                                        srofficeid =
                                                rsc.getInt("CONTROLLING_OFFICE_ID");
                                    }
                                } else {
                                    srofficeid = officeid;
                                }
                            }
                        }
                    }

                    System.out.println("SR Ctrl Office :" + srofficeid);
                    // Delete the SR Controllling office record if exist
                    psorg =
con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("here is ok2");
                    // insert SR Controlling Office Record
                    psorg =
con.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                     " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1, strcode);
                    psorg.setInt(2, srofficeid);
                    psorg.setString(3, "TWAD");
                    psorg.setString(4, "FR");
                    java.sql.Date dt2 =
                        new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5, dt2);
                    psorg.setString(6, updatedby);
                    psorg.setTimestamp(7, ts);

                    psorg.executeUpdate();
                    System.out.println("here is ok3");

                    psorg =
con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("here is ok4");

                    psorg =
con.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE_TMP(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                     " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1, strcode);
                    psorg.setInt(2, srofficeid);
                    psorg.setString(3, "TWAD");
                    psorg.setString(4, "FR");
                    dt2 =
 new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5, dt2);
                    psorg.setString(6, updatedby);
                    psorg.setTimestamp(7, ts);
                    psorg.executeUpdate();
                    System.out.println("here is ok5");

                    /* psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='FR'   where EMPLOYEE_ID=?");
                    psorg.setInt(1,strcode);
                    psorg.executeUpdate();
                     */ //here
                    PreparedStatement psorg1 =
                        con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='1' where EMPLOYEE_ID=?  ");
                    psorg1.setInt(1, strcode);
                    psorg1.executeUpdate();
                    System.out.println("step 8");


                    System.out.println("test1");
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    System.out.println("test2");
                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID,OFFICE_WING_SINO)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "FR");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setString(13, updatedby);
                    ps.setInt(14, wing);
                    ps.executeUpdate();
                    System.out.println("test3");

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING  where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    System.out.println("test4");

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "FR");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setString(13, updatedby);

                    ps.executeUpdate();
                    System.out.println("test5");


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    System.out.println("test6");

                    java.sql.Date dt1 =
                        new java.sql.Date(System.currentTimeMillis());
                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_WING(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)");

                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, wing);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, updatedby);
                    ps.setTimestamp(6, sqldt);
                    ps.executeUpdate();
                    System.out.println("test7");


                    /*   ps=con.prepareStatement("update hrm_emp_controlling_office set controlling_office_id=5887 where employee_id=?");
                    ps.setInt(1,strcode);
                    ps.executeUpdate();*/


                    con.commit();
                    sendMessage(response, "Record is Validated Successfully",
                                "ok1");

                }


                else if (statusid.equalsIgnoreCase("STU")) {

                    String[] sd =
                        request.getParameter("txtStudyDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optStudyDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtstudyremark");
                    System.out.println("txtstudyremark:" + remarks);

                    con.setAutoCommit(false);


                    PreparedStatement psorg =
                        con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='1' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID,DESIGNATION_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setDate(2, dtfrom);
                    ps.setString(3, remarks);
                    ps.setString(4, officegrade);
                    ps.setString(5, "FR");
                    ps.setString(6, statusid);
                    ps.setString(7, dtfromsession);
                    ps.setTimestamp(8, sqldt);
                    ps.setString(9, updatedby);

                    ps.setInt(10, designation);
                    ps.executeUpdate();

                    System.out.println("designation:" + designation);


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING " +
                       " where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID,DESIGNATION_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setDate(2, dtfrom);
                    ps.setString(3, remarks);
                    ps.setString(4, officegrade);
                    ps.setString(5, "FR");
                    ps.setString(6, statusid);
                    ps.setString(7, dtfromsession);
                    ps.setTimestamp(8, sqldt);
                    ps.setString(9, updatedby);

                    ps.setInt(10, designation);
                    ps.executeUpdate();


                    System.out.println("current posting is ok");
                    String sql =
                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    int OfficeId = 0;
                    if (rs.next()) {
                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                    }
                    System.out.println("Office Id:" + OfficeId);
                    System.out.println("date :" +
                                       request.getParameter("txtStudyDateFrom"));
                    sd = request.getParameter("txtStudyDateFrom").split("/");
                    int Year1 = Integer.parseInt(sd[2]);
                    ps =
  con.prepareStatement("SELECT MAX(JOINING_REPORT_ID) AS b FROM HRM_EMP_STU_JOIN_REPORTS where OFFICE_ID=? and JOINING_YEAR=?");
                    ps.setInt(1, OfficeId);
                    ps.setInt(2, Year1);
                    rs = ps.executeQuery();
                    int b = 0;
                    if (rs.next()) {
                        b = rs.getInt("b") + 1;
                        System.out.println("b is " + b);

                    }

                    ps =
  con.prepareStatement("select max(JOINING_REPORT_ID) repid,employee_id,JOINING_YEAR from HRM_EMP_STU_JOIN_REPORTS where EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') group by employee_id,JOINING_YEAR");
                    ps.setInt(1, strcode);
                    rs = ps.executeQuery();
                    if (!rs.next()) {


                        ps =
  con.prepareStatement("INSERT INTO HRM_EMP_STU_JOIN_REPORTS" +
                       "(OFFICE_ID,JOINING_YEAR,JOINING_REPORT_ID,EMPLOYEE_ID,STUDIES_ACTUAL_JOIN_DATE,SESSION_AN_FN," +
                       "STUDIES_JOIN_REMARKS,PROCESS_FLOW_STATUS_ID,INSTITUTION_NAME,INSTITUTION_LOCATION, COURSE_NAME ,SR_CONTROLLING_OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE)" +
                       "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1, OfficeId);
                        ps.setInt(2, Year1);
                        ps.setInt(3, b);
                        ps.setInt(4, strcode);
                        ps.setDate(5, dtfrom);
                        ps.setString(6, dtfromsession);
                        ps.setString(7, remarks);
                        ps.setString(8, "FR");

                        String txtInstName =
                            request.getParameter("txtInst_Name");
                        String txtInstLocation =
                            request.getParameter("txtInst_Location");
                        String txtCourseName =
                            request.getParameter("txtCourse_Name");


                        ps.setString(9, txtInstName);
                        ps.setString(10, txtInstLocation);
                        ps.setString(11, txtCourseName);
                        ps.setInt(12, OfficeId);

                        ps.setString(13, updatedby);
                        ps.setTimestamp(14, ts);

                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";
                    } else {
                        // xml=xml+"<flag>failure1</flag>";
                        int repid = rs.getInt("repid");
                        int year2 = rs.getInt("JOINING_YEAR");

                        ps =
  con.prepareStatement("update HRM_EMP_STU_JOIN_REPORTS " +
                       "set OFFICE_ID=?,JOINING_YEAR=?,JOINING_REPORT_ID=?,EMPLOYEE_ID=?,STUDIES_ACTUAL_JOIN_DATE=?," +
                       " SESSION_AN_FN=?,STUDIES_JOIN_REMARKS=?,PROCESS_FLOW_STATUS_ID=?,INSTITUTION_NAME=?,INSTITUTION_LOCATION=?," +
                       " COURSE_NAME=? ,SR_CONTROLLING_OFFICE_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? " +
                       " where employee_id=? and OFFICE_ID=? and JOINING_REPORT_ID=? and JOINING_YEAR=?");
                        ps.setInt(1, OfficeId);
                        ps.setInt(2, Year1);
                        ps.setInt(3, repid);
                        ps.setInt(4, strcode);
                        ps.setDate(5, dtfrom);
                        ps.setString(6, dtfromsession);
                        ps.setString(7, remarks);
                        ps.setString(8, "FR");

                        String txtInstName =
                            request.getParameter("txtInst_Name");
                        String txtInstLocation =
                            request.getParameter("txtInst_Location");
                        String txtCourseName =
                            request.getParameter("txtCourse_Name");

                        ps.setString(9, txtInstName);
                        ps.setString(10, txtInstLocation);
                        ps.setString(11, txtCourseName);
                        ps.setInt(12, OfficeId);

                        ps.setString(13, updatedby);
                        ps.setTimestamp(14, ts);

                        ps.setInt(15, strcode);
                        ps.setInt(16, OfficeId);
                        ps.setInt(17, repid);
                        ps.setInt(18, year2);

                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";


                    }


                    con.commit();
                    sendMessage(response, "Record is Updated Successfully",
                                "ok");

                }


                else if (statusid.equalsIgnoreCase("DPN")) {

                    deptid = request.getParameter("txtDept_Id");
                    System.out.println("txtDept_Id:" + deptid);

                    officeid =
                            Integer.parseInt(request.getParameter("txtOthOffice_Id"));
                    System.out.println("txtOthOffice_Id:" + officeid);

                    session = request.getSession(false);
                    UserProfile up = null;
                    up = (UserProfile)session.getAttribute("UserProfile");
                    String sql =
                        "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, up.getEmployeeId());
                    int offid = 0;
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        offid = rs.getInt("OFFICE_ID");
                    }

                    System.out.println("login office id:" + offid);
                    // officeid=Integer.parseInt(request.getParameter("txtOffice_Id"));
                    // System.out.println("txtOffice_Id:"+officeid);

                    String[] sd =
                        request.getParameter("txtDepDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optDepDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtDepremark");
                    System.out.println("txtDepremark:" + remarks);

                    con.setAutoCommit(false);


                    /*   remove the SR Controlling office and moved it to log table  */
                    /*  System.out.println("step 1");
                    PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                    "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                    "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1,strcode);
                    ResultSet rsorg=psorg.executeQuery();
                    if(rsorg.next()) {
                       System.out.println("step 2");
                       PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                       psins.setInt(1,strcode);
                       ResultSet rsins=psins.executeQuery();
                       if(rsins.next()){
                           int sid=rsins.getInt("sid");
                           if(sid>0) {
                               sid+=1;
                           }
                           else{
                               sid=1;
                           }
                           System.out.println("step 3");
                           psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                           "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                           psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                           psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                           psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                           psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                           psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                           psins.setInt(6,sid);
                           psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                           psins.setDate(8,rsorg.getDate("UPDATED_DATE"));
                           psins.executeUpdate();
                           System.out.println("step 4");
                       }
                    }

                       System.out.println("step 5");
                       psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                       psorg.setInt(1,strcode);
                       psorg.executeUpdate();
                       System.out.println("step 6");


                    psorg=con.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                    " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1,strcode);
                    psorg.setInt(2,offid);
                    psorg.setString(3,"TWAD");
                    psorg.setString(4,"FR");
                    java.sql.Date dt1=new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5,dt1);
                    psorg.setString(6,updatedby);
                    psorg.setTimestamp(7,ts);
                    psorg.executeUpdate();
                      System.out.println("here is ok7");


                    psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE_TMP where EMPLOYEE_ID=?");
                    psorg.setInt(1,strcode);
                    psorg.executeUpdate();
                    System.out.println("here is ok8");

                    psorg=con.prepareStatement("insert into  HRM_EMP_CONTROLLING_OFFICE_TMP(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                    " values (?,?,?,?,?,?,?)");
                    psorg.setInt(1,strcode);
                    psorg.setInt(2,offid);
                    psorg.setString(3,"TWAD");
                    psorg.setString(4,"FR");
                    dt1=new java.sql.Date(new java.util.Date(System.currentTimeMillis()).getTime());
                    psorg.setDate(5,dt1);
                    psorg.setString(6,updatedby);
                    psorg.setTimestamp(7,ts);
                    psorg.executeUpdate();
                    System.out.println("here is ok9");

                       /*
                       psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD'   where EMPLOYEE_ID=? ");
                       psorg.setInt(1,strcode);
                       psorg.executeUpdate();
                       System.out.println("step 7");*/

                    PreparedStatement psorg =
                        con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 10");
                    /* End of the comment:   remove the SR Controlling office and moved it to log table  */


                    System.out.println("step1");
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    System.out.println("step2");
                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "FR");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setString(13, updatedby);

                    ps.executeUpdate();
                    System.out.println("step3");
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING  where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,DEPARTMENT_ID,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, officeid);
                    ps.setInt(3, designation);
                    ps.setDate(4, dtfrom);
                    ps.setString(5, remarks);
                    ps.setString(6, officegrade);
                    ps.setString(7, deptid);
                    ps.setString(8, "FR");
                    ps.setString(9, statusid);
                    ps.setString(10, null);
                    ps.setString(11, dtfromsession);
                    ps.setTimestamp(12, sqldt);
                    ps.setString(13, updatedby);

                    ps.executeUpdate();
                    System.out.println("step4");
                    con.commit();
                    sendMessage(response, "Record is Validated Successfully",
                                "ok1");

                } else if (statusid.equalsIgnoreCase("LLV")) {

                    leavetype = request.getParameter("cmbleavetype");
                    System.out.println("cmbleavetype:" + leavetype);

                    String[] sd =
                        request.getParameter("txtDateFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optDateFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtleaveremark");
                    System.out.println("txtleaveremark:" + remarks);

                    con.setAutoCommit(false);

                    /*   remove the SR Controlling office and moved it to log table  */
                    System.out.println("step 1");
                    PreparedStatement psorg =
                        con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                             "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                             "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strcode);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        System.out.println("step 2");
                        PreparedStatement psins =
                            con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                        psins.setInt(1, strcode);
                        ResultSet rsins = psins.executeQuery();
                        if (rsins.next()) {
                            int sid = rsins.getInt("sid");
                            if (sid > 0) {
                                sid += 1;
                            } else {
                                sid = 1;
                            }
                            System.out.println("step 3");
                            psins =
con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                            psins.setInt(1, rsorg.getInt("EMPLOYEE_ID"));
                            psins.setInt(2,
                                         rsorg.getInt("CONTROLLING_OFFICE_ID"));
                            psins.setString(3,
                                            rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                            psins.setString(4,
                                            rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                            psins.setDate(5,
                                          rsorg.getDate("CONTROLLING_DATE_FROM"));
                            psins.setInt(6, sid);
                            psins.setString(7,
                                            rsorg.getString("UPDATED_BY_USER_ID"));
                            psins.setDate(8, rsorg.getDate("UPDATED_DATE"));
                            psins.executeUpdate();
                            System.out.println("step 4");
                        }
                    }

                    System.out.println("step 5");
                    psorg =
con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 6");

                    psorg =
con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD'   where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 7");

                    psorg =
con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 8");
                    /* End of the comment:   remove the SR Controlling office and moved it to log table  */


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, leavetype);
                    ps.setString(9, dtfromsession);
                    ps.setTimestamp(10, sqldt);
                    ps.setString(11, updatedby);

                    ps.executeUpdate();

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING  where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "LEAVE_TYPE_CODE,DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, leavetype);
                    ps.setString(9, dtfromsession);
                    ps.setTimestamp(10, sqldt);
                    ps.setString(11, updatedby);
                    ps.executeUpdate();


                    con.commit();
                    sendMessage(response, "Record is Validated Successfully",
                                "ok1");

                } else if (statusid.equalsIgnoreCase("SUS")) {
                    String[] sd =
                        request.getParameter("txtSusFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optSusFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtsusreson");
                    System.out.println("txtsusreson:" + remarks);

                    con.setAutoCommit(false);

                    /*   remove the SR Controlling office and moved it to log table  */
                    System.out.println("step 1");
                    PreparedStatement psorg =
                        con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                             "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                             "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strcode);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        System.out.println("step 2");
                        PreparedStatement psins =
                            con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                        psins.setInt(1, strcode);
                        ResultSet rsins = psins.executeQuery();
                        if (rsins.next()) {
                            int sid = rsins.getInt("sid");
                            if (sid > 0) {
                                sid += 1;
                            } else {
                                sid = 1;
                            }


                            System.out.println("step 3");
                            psins =
con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                            psins.setInt(1, rsorg.getInt("EMPLOYEE_ID"));
                            psins.setInt(2,
                                         rsorg.getInt("CONTROLLING_OFFICE_ID"));
                            psins.setString(3,
                                            rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                            psins.setString(4,
                                            rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                            psins.setDate(5,
                                          rsorg.getDate("CONTROLLING_DATE_FROM"));
                            psins.setInt(6, sid);
                            psins.setString(7,
                                            rsorg.getString("UPDATED_BY_USER_ID"));
                            psins.setDate(8, rsorg.getDate("UPDATED_DATE"));
                            psins.executeUpdate();
                            System.out.println("step 4");
                        }
                    }

                    System.out.println("step 5");
                    psorg =
con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 6");

                    psorg =
con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD'   where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 7");

                    psorg =
con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 8");
                    /* End of the comment:   remove the SR Controlling office and moved it to log table  */


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING  where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Validated Successfully",
                                "ok1");
                }

                /*  TRANSIT   */

                else if (statusid.equalsIgnoreCase("TRT")) {
                    String[] sd =
                        request.getParameter("txtTrtFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optTrtFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtTrtreson");
                    System.out.println("txtTrtreson:" + remarks);

                    con.setAutoCommit(false);

                    /*   remove the SR Controlling office and moved it to log table  */
                    System.out.println("step 1");
                    PreparedStatement psorg =
                        con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                             "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                             "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strcode);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        System.out.println("step 2");
                        PreparedStatement psins =
                            con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                        psins.setInt(1, strcode);
                        ResultSet rsins = psins.executeQuery();
                        if (rsins.next()) {
                            int sid = rsins.getInt("sid");
                            if (sid > 0) {
                                sid += 1;
                            } else {
                                sid = 1;
                            }
                            System.out.println("step 3");
                            psins =
con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                            psins.setInt(1, rsorg.getInt("EMPLOYEE_ID"));
                            psins.setInt(2,
                                         rsorg.getInt("CONTROLLING_OFFICE_ID"));
                            psins.setString(3,
                                            rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                            psins.setString(4,
                                            rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                            psins.setDate(5,
                                          rsorg.getDate("CONTROLLING_DATE_FROM"));
                            psins.setInt(6, sid);

                            psins.setString(7,
                                            rsorg.getString("UPDATED_BY_USER_ID"));
                            psins.setDate(8, rsorg.getDate("UPDATED_DATE"));

                            psins.executeUpdate();
                            System.out.println("step 4");
                        }
                    }

                    System.out.println("step 5");
                    psorg =
con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 6");

                    psorg =
con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD'   where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 7");

                    psorg =
con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 8");
                    /* End of the comment:   remove the SR Controlling office and moved it to log table  */


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING  where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Validated Successfully",
                                "ok1");
                }


                /* UNAUTHERIZED LEAVE */

                else if (statusid.equalsIgnoreCase("UAL")) {
                    String[] sd =
                        request.getParameter("txtUalFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optUalFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtUalreson");
                    System.out.println("txtUalreson:" + remarks);

                    con.setAutoCommit(false);

                    /*   remove the SR Controlling office and moved it to log table  */
                    System.out.println("step 1");
                    PreparedStatement psorg =
                        con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                             "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                             "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strcode);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        System.out.println("step 2");
                        PreparedStatement psins =
                            con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                        psins.setInt(1, strcode);
                        ResultSet rsins = psins.executeQuery();
                        if (rsins.next()) {
                            int sid = rsins.getInt("sid");
                            if (sid > 0) {
                                sid += 1;
                            } else {
                                sid = 1;
                            }
                            System.out.println("step 3");
                            psins =
con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                            psins.setInt(1, rsorg.getInt("EMPLOYEE_ID"));
                            psins.setInt(2,
                                         rsorg.getInt("CONTROLLING_OFFICE_ID"));
                            psins.setString(3,
                                            rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                            psins.setString(4,
                                            rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                            psins.setDate(5,
                                          rsorg.getDate("CONTROLLING_DATE_FROM"));
                            psins.setInt(6, sid);
                            psins.setString(7,
                                            rsorg.getString("UPDATED_BY_USER_ID"));
                            psins.setDate(8, rsorg.getDate("UPDATED_DATE"));
                            psins.executeUpdate();
                            System.out.println("step 4");
                        }
                    }

                    System.out.println("step 5");
                    psorg =
con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 6");

                    psorg =
con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD'   where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 7");

                    psorg =
con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 8");
                    /* End of the comment:   remove the SR Controlling office and moved it to log table  */


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING  where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();

                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Validated Successfully",
                                "ok1");
                }


                /*  COMPULSARY leave */
                else if (statusid.equalsIgnoreCase("CMW")) {
                    String[] sd =
                        request.getParameter("txtCmwFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optCmwFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtCmwreson");
                    System.out.println("txtCmwreson:" + remarks);

                    con.setAutoCommit(false);

                    /*   remove the SR Controlling office and moved it to log table  */
                    System.out.println("step 1");
                    PreparedStatement psorg =
                        con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                             "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                             "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                    psorg.setInt(1, strcode);
                    ResultSet rsorg = psorg.executeQuery();
                    if (rsorg.next()) {
                        System.out.println("step 2");
                        PreparedStatement psins =
                            con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                        psins.setInt(1, strcode);
                        ResultSet rsins = psins.executeQuery();
                        if (rsins.next()) {
                            int sid = rsins.getInt("sid");
                            if (sid > 0) {
                                sid += 1;
                            } else {
                                sid = 1;
                            }
                            System.out.println("step 3");
                            psins =
con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?)");
                            psins.setInt(1, rsorg.getInt("EMPLOYEE_ID"));
                            psins.setInt(2,
                                         rsorg.getInt("CONTROLLING_OFFICE_ID"));
                            psins.setString(3,
                                            rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                            psins.setString(4,
                                            rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                            psins.setDate(5,
                                          rsorg.getDate("CONTROLLING_DATE_FROM"));
                            psins.setInt(6, sid);
                            psins.setString(7,
                                            rsorg.getString("UPDATED_BY_USER_ID"));
                            psins.setDate(8, rsorg.getDate("UPDATED_DATE"));
                            psins.executeUpdate();
                            psins.close();
                            System.out.println("step 4");
                        }
                        rsins.close();
                    }

                    System.out.println("step 5");
                    psorg =
con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 6");
                    psorg.close();
                    psorg =
con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD'   where EMPLOYEE_ID=? ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    System.out.println("step 7");
                    psorg.close();
                    psorg =
con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                    psorg.setInt(1, strcode);
                    psorg.executeUpdate();
                    psorg.close();
                    System.out.println("step 8");
                    /* End of the comment:   remove the SR Controlling office and moved it to log table  */


                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    ps.close();
                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    ps.close();

                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING  where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    ps.close();
                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    con.commit();
                    sendMessage(response, "Record is Validated Successfully",
                                "ok1");
                    rsorg.close();
                    ps.close();
                }


                else if (statusid.equalsIgnoreCase("ABS")) {
                    String[] sd =
                        request.getParameter("txtAbsFrom").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    dtfrom = new Date(d.getTime());

                    dtfromsession = request.getParameter("optAbsFrom");

                    System.out.println("From Date:" + dtfrom + " " +
                                       dtfromsession);

                    remarks = request.getParameter("txtabsremark");
                    System.out.println("txtabsremark:" + remarks);

                    con.setAutoCommit(false);
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING_TMP " +
                       " where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID!='DL') ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    ps.close();
                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING_TMP" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();
                    ps.close();
                    ps =
  con.prepareStatement("delete from HRM_EMP_CURRENT_POSTING  where  EMPLOYEE_ID=? ");
                    ps.setInt(1, strcode);
                    ps.executeUpdate();
                    ps.close();
                    ps =
  con.prepareStatement("insert into HRM_EMP_CURRENT_POSTING" +
                       "(EMPLOYEE_ID,DESIGNATION_ID,DATE_EFFECTIVE_FROM,REMARKS," +
                       "OFFICE_GRADE,PROCESS_FLOW_STATUS_ID,EMPLOYEE_STATUS_ID," +
                       "DATE_EFFECTIVE_FROM_SESSION,UPDATED_DATE,UPDATED_BY_USER_ID)" +
                       " values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1, strcode);
                    ps.setInt(2, designation);
                    ps.setDate(3, dtfrom);
                    ps.setString(4, remarks);
                    ps.setString(5, officegrade);
                    ps.setString(6, "FR");
                    ps.setString(7, statusid);
                    ps.setString(8, dtfromsession);
                    ps.setTimestamp(9, sqldt);
                    ps.setString(10, updatedby);

                    ps.executeUpdate();

                    con.commit();


                    sendMessage(response, "Record is Validated Successfully",
                                "ok1");
                    ps.close();
                }

            } catch (Exception e) {
                String err = "Record is not Validated. " + e.getMessage();
                try {
                    con.rollback();
                } catch (Exception e1) {
                    System.out.println(e1);
                }
                sendMessage(response, err, "ok1");

            } finally {
                try {
                    con.setAutoCommit(true);
                } catch (Exception e1) {
                    System.out.println(e1);
                }
            }

        }
try {
	con.close();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
        ///////////////
    }


    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger_addlcharge.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}


