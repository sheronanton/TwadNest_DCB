package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.*;


public class InsertEmployee1 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection connection = null;
        try {
        	LoadDriver driver=new LoadDriver();
        	connection=driver.getConnection();

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        Statement statement = null;
        ResultSet results = null;
        //Statement statement1=null;
        ResultSet results1 = null;
        PreparedStatement ps = null;
        System.out.println("SERVLET CALELD");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

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
        //    System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        //Declare The Variables
        String strEmpPref = "";
        String strEmpInit = "";
        String strEmpName = "";
        int strEmpGpf = 0;
        String StrCommand = "";
        int strEmpId = 0;
        int hstrEmpId = 0;
        String consolidate = null;
        String xml = "";
        String test="",strempid="";
        int flag=1,flag1=0;
        try {
            StrCommand = request.getParameter("command");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The Exception is req paraam is:" + e);
        }
        response.setHeader("cache-control", "no-cache");
        if (StrCommand.equalsIgnoreCase("Existg")) {
          
            try {
            	
            	strEmpId = Integer.parseInt(request.getParameter("EmpId"));
         
            xml = "<response><command>Existg</command>";
            
                statement = connection.createStatement();
                results =
                        statement.executeQuery("select Employee_Initial,Employee_Prefix,Employee_Name,GPF_NO from HRM_MST_EMPLOYEES_BASIC_TMP where   Employee_Id=" +
                                               strEmpId);
                if (results.next()) {
                    statement = connection.createStatement();
                    results =
                            statement.executeQuery("select Employee_Initial,Employee_Prefix,Employee_Name,GPF_NO,is_Consolidate from HRM_MST_EMPLOYEES_BASIC_TMP where (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD') and  Employee_Id=" +
                                                   strEmpId);
                    try {

                        int i = 0;
                        while (results.next()) {
                            i++;
                            strEmpPref = results.getString("Employee_Prefix");
                            if (strEmpPref == null)
                                strEmpPref = "null";
                            else
                                strEmpInit =
                                        results.getString("Employee_Initial");
                            strEmpName = results.getString("Employee_Name");
                            strEmpGpf = results.getInt("GPF_NO");
                            consolidate = results.getString("is_consolidate");

                        }
                        if (i == 0) {
                            statement = connection.createStatement();
                            results =
                                    statement.executeQuery("select Employee_Initial,Employee_Prefix,Employee_Name,GPF_NO,is_consolidate from HRM_MST_EMPLOYEES_BASIC_TMP where PROCESS_FLOW_STATUS_ID='FR'  and  Employee_Id=" +
                                                           strEmpId);
                            if (results.next()) {
                                xml = xml + "<flag>frezeed</flag>";
                            } else {
                                xml = xml + "<flag>delete</flag>";
                            }

                        } else {

                            xml = xml + "<flag>success</flag>";
                            xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>" + "<consolid>" + consolidate + "</consolid>";
                        }

                    } catch (Exception aee) {
                        System.out.println("Exception in the getting values IN get : " +
                                           aee);

                    }
                    results.close();
                    response.setHeader("cache-control", "no-cache");
                } else {

                    xml = xml + "<flag>failure</flag>";
                }
            }
            catch(NumberFormatException e)
            {
            	  xml="<response><command>error</command>";
                  e.printStackTrace();
            }
            catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        } else if (StrCommand.equalsIgnoreCase("Existgview")) {
            
           
            try {
            	strEmpId = Integer.parseInt(request.getParameter("EmpId"));
             
                statement = connection.createStatement();
                results =
                        statement.executeQuery("select Employee_Initial,Employee_Prefix,Employee_Name,GPF_NO,is_consolidate from HRM_MST_EMPLOYEES where Employee_Id=" +
                                               strEmpId);
                try {
                	 xml = "<response><command>Existg</command>";
                    int i = 0;
                    while (results.next()) {
                       
                        i++;
                        strEmpPref = results.getString("Employee_Prefix");
                        if (strEmpPref == null)
                            strEmpPref = "null";
                        else
                            strEmpInit = results.getString("Employee_Initial");
                        strEmpName = results.getString("Employee_Name");
                        strEmpGpf = results.getInt("GPF_NO");
                        consolidate = results.getString("is_consolidate");
                    }
                    if (i == 0) {
                        xml = xml + "<flag>NoValue</flag>";
                    } else {

                        xml = xml + "<flag>success</flag>";
                        xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf>" + "<consolid>" + consolidate + "</consolid>";
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values IN get : " +
                                       aee);

                }
                results.close();

            }
            catch(NumberFormatException e)
            {
            	 xml="<response><command>error</command>";		
            }
            catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (StrCommand.equalsIgnoreCase("ExistgviewSR")) {
            strEmpId = Integer.parseInt(request.getParameter("EmpId"));
          
            xml = "<response><command>Existg</command>";
            try {
                System.out.println("Employee id verify for SR");
                session = request.getSession(false);
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");

                String sql =
                    "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, up.getEmployeeId());
                results = ps.executeQuery();
                int offid = 0;
                if (results.next()) {
                    offid = results.getInt("OFFICE_ID");
                }
                statement = connection.createStatement();
                results =
                        statement.executeQuery("select a.Employee_Initial,a.Employee_Prefix,a.Employee_Name,a.GPF_NO,a.is_consolidate from HRM_MST_EMPLOYEES a " +
                                               " inner join hrm_emp_controlling_office b on b.employee_id =a.employee_id and b.controlling_office_id=" +
                                               offid +
                                               " where a.Employee_Id=" +
                                               strEmpId);
                try {

                    int i = 0;
                    while (results.next()) {
                       // System.out.println("ok");
                        i++;
                        strEmpPref = results.getString("Employee_Prefix");
                        if (strEmpPref == null)
                            strEmpPref = "null";
                        else
                            strEmpInit = results.getString("Employee_Initial");
                        strEmpName = results.getString("Employee_Name");
                        strEmpGpf = results.getInt("GPF_NO");
                        consolidate = results.getString("is_consolidate");
                    }
                    if (i == 0) {
                        xml = xml + "<flag>NoValue</flag>";
                    } else {

                        xml = xml + "<flag>success</flag>";
                        xml =
 xml + "<EmpPref>" + strEmpPref + "</EmpPref><EmpInit>" + strEmpInit +
   "</EmpInit><EmpName>" + strEmpName + "</EmpName><EmpGpf>" + strEmpGpf +
   "</EmpGpf><consolid>" + consolidate + "</consolid>";
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values IN get : " +
                                       aee);

                }
                results.close();

            }
            catch(NumberFormatException e)
            {
            	  xml="<response><command>error</command>";
                  e.printStackTrace();	
            }
            catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        }

        else if (StrCommand.equalsIgnoreCase("Add")) {
            int empid = 0;
           
           
            System.out.println("inseide add");
            try {


                strEmpPref = request.getParameter("EmpPref");
                strEmpInit = request.getParameter("EmpInit");
                strEmpName = request.getParameter("EmpName");
                String strEmpGpfno = request.getParameter("EmpGpf");
                consolidate = request.getParameter("consolidate");
               
               
                try {
                	 test=strEmpGpfno;
                    if(isNumeric(test))
                         { strEmpGpf =Integer.parseInt(request.getParameter("EmpGpf"));
                           xml = "<response><command>Add</command>";
                           flag=1;
                         }
                     else
                           xml="<response><command>error</command>";	 
                } catch (Exception e) {
                    System.out.println(e);
                }
                if(flag==1)
                {
             
                String sql =
                    "select GPF_NO from hrm_mst_employees_basic_tmp where GPF_NO=? and GPF_NO!=0 and PROCESS_FLOW_STATUS_ID!='DL'";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strEmpGpf);
              
                results1 = ps.executeQuery();
                int i = 0;
                try {

                    while (results1.next()) {
                        i = i + 1;
                    }
                    if (i == 0) {

                        sql =
                    "select GPF_NO from hrm_mst_employees where GPF_NO=? and GPF_NO!=0";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, strEmpGpf);
                       
                        results1 = ps.executeQuery();
                        i = 0;
                        while (results1.next()) {
                            i = i + 1;
                        }
                        if (i != 0) {
                            xml = xml + "<flag>ExistRec</flag>";
                            xml = xml + "<EmpGpf>" + strEmpGpf + "</EmpGpf>";
                        } else {
                            session = request.getSession(false);
                            String updatedby =
                                (String)session.getAttribute("UserId");
                            long l = System.currentTimeMillis();
                            Timestamp ts = new Timestamp(l);
                            java.sql.Date updateddate =
                                new java.sql.Date(System.currentTimeMillis());


                            CallableStatement pstmt =
                                connection.prepareCall("{call INSERTEMPID(?,?,?,?,?,?,?,?,?,?)}");

                            pstmt.setString(2, strEmpName);
                            pstmt.setString(3, strEmpInit);
                            pstmt.setString(4, strEmpPref);
                            pstmt.setInt(5, strEmpGpf);
                            pstmt.setTimestamp(6, ts);
                            pstmt.setString(7, "CR");
                            pstmt.registerOutParameter(1, Types.INTEGER);
                            pstmt.registerOutParameter(8, Types.INTEGER);

                            pstmt.setString(9, updatedby);
                            pstmt.setString(10, consolidate);

                            pstmt.executeUpdate();
                           


                            int err = 0;
                            err = pstmt.getInt(8);
                            System.out.println("Error code:" + err);
                            empid = pstmt.getInt(1);

                           
                            if (err != 0) {
                                xml = xml + "<flag>failuer</flag>";
                            } else {
                                xml = xml + "<flag>success</flag>";

                                xml = xml + "<EmpId>" + empid + "</EmpId>";
                            }
                            
                        
                        }
                    } else {
                        xml = xml + "<flag>ExistRec</flag>";
                        xml = xml + "<EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    }

                } catch (Exception ee) {
                    System.out.println("to check GPF" + ee);
                }
                }
            } catch (SQLException e) {
                System.out.println("Exception in connection:" + e);
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        }


        else if (StrCommand.equalsIgnoreCase("Update")) {

            try {
            	strempid=request.getParameter("EmpId");
            	
                
                strEmpPref = request.getParameter("EmpPref");
                strEmpInit = request.getParameter("EmpInit");
                strEmpName = request.getParameter("EmpName");
                String strEmpGpfno = request.getParameter("EmpGpf");
                consolidate = request.getParameter("consolidate");

               
                	 
              strEmpGpf =Integer.parseInt(request.getParameter("EmpGpf"));
             
              hstrEmpId=Integer.parseInt(request.getParameter("EmpId"));
                 
                    	
              
                /*  if(strEmpGpfno!=null)
              strEmpGpf=Integer.parseInt(request.getParameter("EmpGpf"));
              else
                strEmpGpf=Integer.parseInt(strEmpGpfno);*/
               

            } catch (NumberFormatException e) {
            	flag=0;
                e.printStackTrace();
                xml="<response><command>error</command>";
                System.out.println("The Exception is req paraam is II :" + e);
            }

           if(flag==1)
           {
            try {


            	xml = "<response><command>Update</command>";

                int i = 0;
                try {
                    String sql1 =
                        "select GPF_NO from hrm_mst_employees_basic_tmp where GPF_NO=? and GPF_NO!=0 and Employee_Id not in(?) and PROCESS_FLOW_STATUS_ID!='DL'";
                    ps = connection.prepareStatement(sql1);
                    ps.setInt(1, strEmpGpf);
                    ps.setInt(2, hstrEmpId);
                    results1 = ps.executeQuery();
                    while (results1.next()) {
                        i = i + 1;
                    }
                    if (i == 0) {

                        String sql =
                            "select GPF_NO from hrm_mst_employees where GPF_NO=? and GPF_NO!=0";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, strEmpGpf);
                        System.out.println(":::Gpf No::" + strEmpGpf);
                        results1 = ps.executeQuery();
                        i = 0;
                        while (results1.next()) {
                            i = i + 1;
                        }
                        if (i != 0) {
                            xml = xml + "<flag>ExistRec</flag>";
                            xml = xml + "<EmpGpf>" + strEmpGpf + "</EmpGpf>";
                        } else

                        {
                            String updatedby =
                                (String)session.getAttribute("UserId");
                            long l = System.currentTimeMillis();
                            Timestamp ts = new Timestamp(l);


                            sql =
 "update HRM_MST_EMPLOYEES_BASIC_TMP set Employee_Name=?,Employee_Initial=?,Employee_Prefix=?,GPF_NO=?,PROCESS_FLOW_STATUS_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,is_consolidate=? where Employee_Id=?";
                            ps = connection.prepareStatement(sql);
                            ps.setString(1, strEmpName);
                            ps.setString(2, strEmpInit);
                            ps.setString(3, strEmpPref);
                            ps.setInt(4, strEmpGpf);
                            ps.setString(5, "MD");

                            ps.setString(6, updatedby);
                            ps.setTimestamp(7, ts);
                            ps.setInt(9, hstrEmpId);
                            ps.setString(8, consolidate);

                            ps.executeUpdate();
                            xml = xml + "<flag>success</flag>";
                            //response.setHeader("cache-control","no-cache");
                            //response.sendRedirect("index.jsp?hai");
                        }
                    } else {
                        xml = xml + "<flag>ExistRec</flag>";
                        xml = xml + "<EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    }
                } catch (Exception ee) {
                    System.out.println("to check GPF" + ee);
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception ee) {

                System.out.println("Error in the update is:" + ee);
                xml = xml + "<flag>failure</flag>";
            }
           }
            xml = xml + "</response>";
        }


        else if (StrCommand.equalsIgnoreCase("Verify")) {
System.out.println(request.getParameter("EmpId"));
            try {
                hstrEmpId = Integer.parseInt(request.getParameter("EmpId"));
                strEmpPref = request.getParameter("EmpPref");
                strEmpInit = request.getParameter("EmpInit");
                strEmpName = request.getParameter("EmpName");
                String strEmpGpfno = request.getParameter("EmpGpf");
                consolidate = request.getParameter("consolidate");
                /* if(strEmpGpfno!=null)
                strEmpGpf=Integer.parseInt(request.getParameter("EmpGpf"));
                else
                  strEmpGpf=Integer.parseInt(strEmpGpfno);*/
           if(request.getParameter("EmpGpf")==""||request.getParameter("EmpGpf")==null)
        	  
        	   {System.out.println("gpf is empty");
              strEmpGpf=0; }
           else
                        strEmpGpf =Integer.parseInt(request.getParameter("EmpGpf"));
              
             

           

            try {


                xml = "<response><command>Verify</command>";

                int i = 0;
                try {
                    String sql1 =
                        "select GPF_NO from hrm_mst_employees_basic_tmp where GPF_NO=?  and GPF_NO!=0  and Employee_Id not in(?)  and PROCESS_FLOW_STATUS_ID!='DL'";
                    ps = connection.prepareStatement(sql1);
                    ps.setInt(1, strEmpGpf);
                    ps.setInt(2, hstrEmpId);
                    results1 = ps.executeQuery();
                    while (results1.next()) {
                        i = i + 1;
                    }
                    if (i == 0) {

                        String sql =
                            "select GPF_NO from hrm_mst_employees where GPF_NO=? and GPF_NO!=0";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, strEmpGpf);
                        System.out.println(":::Gpf No::" + strEmpGpf);
                        results1 = ps.executeQuery();
                        i = 0;
                        while (results1.next()) {
                            i = i + 1;
                        }
                        if (i != 0) {
                            xml = xml + "<flag>ExistRec</flag>";
                            xml = xml + "<EmpGpf>" + strEmpGpf + "</EmpGpf>";
                        } else {
                            connection.setAutoCommit(false);
                            String updatedby =
                                (String)session.getAttribute("UserId");
                            long l = System.currentTimeMillis();
                            Timestamp ts = new Timestamp(l);

                            sql =
 "update HRM_MST_EMPLOYEES_BASIC_TMP set Employee_Name=?,Employee_Initial=?,Employee_Prefix=?,GPF_NO=?,PROCESS_FLOW_STATUS_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,is_consolidate=? where Employee_Id=?";
                            ps = connection.prepareStatement(sql);
                            ps.setString(1, strEmpName);
                            ps.setString(2, strEmpInit);
                            ps.setString(3, strEmpPref);
                            ps.setInt(4, strEmpGpf);
                            ps.setString(5, "FR");

                            ps.setString(6, updatedby);
                            ps.setTimestamp(7, ts);

                            ps.setInt(9, hstrEmpId);
                            ps.setString(8, consolidate);
                            ps.executeUpdate();

                            System.out.println("test1");
                            ;

                            sql =
 "select max(EMPLOYEE_ID) as eid from  HRM_MST_EMPLOYEES where is_consolidate='N'";
                            System.out.println("consolidate----->" +
                                               consolidate);
                            ps = connection.prepareStatement(sql);
                          //  ps.setString(1, consolidate);
                            results = ps.executeQuery();
                            int eid = 0;
                            if (results.next()) {
                                eid = results.getInt("eid");
                                eid = eid + 1;
                            }
                          

                            sql =
 "INSERT INTO HRM_MST_EMPLOYEES(EMPLOYEE_ID ,EMPLOYEE_NAME ,EMPLOYEE_INITIAL ," +
   "EMPLOYEE_PREFIX ,GPF_NO ,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,UPDATED_BY_USER_ID,is_consolidate) VALUES(?,?,?,?,?,?,?,?,?)";
                            ps = connection.prepareStatement(sql);
                            ps.setInt(1, eid);
                            ps.setString(2, strEmpName);
                            ps.setString(3, strEmpInit);
                            ps.setString(4, strEmpPref);
                            ps.setInt(5, strEmpGpf);

                            ps.setTimestamp(6, ts);
                            ps.setString(7, "CR");

                            ps.setString(8, updatedby);
                            ps.setString(9, consolidate);

                            ps.executeUpdate();


                            connection.commit();

                            xml = xml + "<flag>success</flag>";
                            xml = xml + "<EmpId>" + eid + "</EmpId>";
                        }
                    } else {
                        xml = xml + "<flag>ExistRec</flag>";
                        xml = xml + "<EmpGpf>" + strEmpGpf + "</EmpGpf>";
                    }
                } catch (Exception ee) {
                    connection.rollback();
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("to check GPF" + ee);
                } finally {
                    connection.setAutoCommit(true);
                }
            } catch (Exception ee) {

                System.out.println("Error in the update is:" + ee);
                xml = xml + "<flag>failure</flag>";
            }
          
            } catch (NumberFormatException e) {
            	  xml="<response><command>error</command>";
                e.printStackTrace();
                System.out.println("The Exception is req paraam is II :" + e);
            }
            catch (Exception ee) {

                System.out.println("Error in the update is:" + ee);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (StrCommand.equalsIgnoreCase("Delete")) {

            try {
                hstrEmpId = Integer.parseInt(request.getParameter("EmpId"));
               
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                xml = "<response><command>Delete</command>";
                String sql =
                    "update HRM_MST_EMPLOYEES_BASIC_TMP set PROCESS_FLOW_STATUS_ID='DL',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where Employee_Id=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, updatedby);
                ps.setTimestamp(2, ts);
                ps.setInt(3, hstrEmpId);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";

            } catch(NumberFormatException e)
            {
            	  xml="<response><command>error</command>";
                  e.printStackTrace();
            }
            catch (Exception ee) {

                System.out.println("Error in the DELETE is:" + ee);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        System.out.println("xml is : " + xml);
        out.write(xml);
        out.flush();
        out.close();

    }

  public  boolean isNumeric(String s) {  
	     return java.util.regex.Pattern.matches("\\d+", s);  
	 }
}
