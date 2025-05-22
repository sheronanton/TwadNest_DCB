package Servlets.HR.HR1.EmployeeMaster.servlets;
import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class EmpDetails_Current extends HttpServlet {
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

        System.out.println("inside servlet");

        Connection con = null;  
        PreparedStatement ps = null;
        ResultSet rs = null;
        PreparedStatement ps5 = null;
        ResultSet rs5 = null;
        try {

        	LoadDriver driver=new LoadDriver();
            con=driver.getConnection();

        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        HttpSession session = request.getSession(false);

        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String updatedby = (String)session.getAttribute("UserId");
        System.out.println("user id..." + updatedby);

        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);

        String command = request.getParameter("command");
        System.out.println("command.." + command);


        String xml = "";
        if (command.equalsIgnoreCase("loademp")) {
            int eid = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("employee id.." + eid);

            int offid = Integer.parseInt(request.getParameter("offid"));
            System.out.println("office id.." + offid);


            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            //int offid=0;
            //offid=Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>loademp</command>";
            try {

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                int flag = 0;
                int cur_off=0;
                    int OfficeId = 0;
                    
                    
                    
                    
                    
                    
                    
                    String sql=" SELECT EMPLOYEE_ID " +
                    " FROM HRM_EMP_CONTROLLING_OFFICE " +
                    " WHERE CONTROLLING_OFFICE_ID= ? " +
                    " AND EMPLOYEE_ID            = ?";
                  
                    ps=con.prepareStatement(sql);
                    ps.setInt(1, offid);
                    ps.setInt(2,eid);
                    rs = ps.executeQuery();
                    if(rs.next())
                    {
                    sql="select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? and employee_status_id not in ('VRS','SAN','DTH','CMR','MEV','RES','DIS')";
                    ps=con.prepareStatement(sql);
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                    	
                        xml = xml + "<flag>failure1</flag>";
                    }
                    else 
                    {

                        System.out.println("inside employee status id");

                        if (rs.getString("EMPLOYEE_STATUS_ID") != null) {

                            ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_VRS_WILLINGNESS_MST where  EMPLOYEE_ID=? and (PROCESS_FLOW_ID='CR' or PROCESS_FLOW_ID='MD')");
                            ps.setInt(1, eid);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<flag>failure2</flag>";
                            } else {
                            	

                                ps =
      con.prepareStatement("select EMPLOYEE_ID from HRM_VRS_WILLINGNESS_MST where  EMPLOYEE_ID=? and (PROCESS_FLOW_ID='FR' and VRS_REQUEST_STATUS='Accept')");
                                ps.setInt(1, eid);
                                rs = ps.executeQuery();
                            
                            if (rs.next()) {
                                    xml = xml + "<flag>finish</flag>";
                                }
                                else
                                ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION,f.office_name,c.designation_id from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,com_mst_offices f where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND c.office_id=f.office_id and e.EMPLOYEE_ID=? ");
                                ps.setInt(1, eid);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                if (rs.next()) {


                                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><curr_post>" +
   rs.getString("office_name") + "</curr_post><desig_id>" +
   rs.getInt("designation_id") + "</desig_id>";
                                    xml =
 xml + "<dob>" + (rs.getDate("date_of_birth")).getYear() + "</dob>";


                                    /***************  16-08-2007    ***********************updated on 07-01-2013/


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
                    
                  	
                    }
                    }
                    else
                    {
                    	 xml = xml + "<flag>failuretest</flag>";
                    }
                    
                    
                  
                    	
                  
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }


            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }


        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);

        PrintWriter out = response.getWriter();

        System.out.println("inside post");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String xml = "";
        try {

        	 LoadDriver driver=new LoadDriver();
        	  	con=driver.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

        HttpSession session = request.getSession(false);

        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String updatedby = (String)session.getAttribute("UserId");
        System.out.println("user id..." + updatedby);

        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);

        System.out.println("ts.." + ts);

        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter= 
        new SimpleDateFormat("dd/MM/yyyy");
        String dateNow = formatter.format(currentDate.getTime());
        System.out.println("Now the date is :=>  " + dateNow);
        
        String command = request.getParameter("command");
        System.out.println("command.." + command);

        int Emp_id=0;
       

        	  if (command.equalsIgnoreCase("getempDetails")) {

        		   try
        		   {
        			   Emp_id = Integer.parseInt(request.getParameter("Emp_id"));
        	        
           		   xml = "<response><command>getempDetails</command>";
        	        
              	 String sql= "SELECT * " 
              	+"FROM " 
              	+"  (SELECT Employee_Id, " 
              	+"    Employee_Name as empname, " 
              	+"    Office_Short_Name AS off_name, " 
              	+"    Designation " 
              	+"  FROM View_Employee1 " 
              	+"  WHERE Employee_id=? " 
              	+"  )a " 
              	+"LEFT OUTER JOIN " 
              	+"  (SELECT Employee_Id Empid ,gpf_no FROM Hrm_Mst_Employees " 
              	+"  )B " 
              	+"ON A.Employee_Id=B.Empid " 
              	+"LEFT OUTER JOIN " 
              	+"  (SELECT Employee_Id cemp_id,Cps_No FROM Hrm_Mst_Emp_Cps_Link " 
              	+"  )C " 
              	+"ON A.Employee_Id=Cemp_Id " 
              	+"LEFT OUTER JOIN " 
              	+"  (SELECT employee_id, " 
              	+"    DECODE(gpf_no,NULL,'N','Y') AS flag " 
              	+"  FROM HRM_MST_EMPLOYEES " 
              	+"  )Sub2 " 
              	+"ON sub2.employee_id=a.employee_id " 
              	+"LEFT OUTER JOIN " 
              	+"  (SELECT EMPLOYEE_ID, " 
              	+"    DECODE(CPS_NO,NULL,'N','Y') AS flag1 " 
              	+"  FROM hrm_mst_emp_cps_link " 
              	+"  )Sub3 " 
              	+"ON a.EMPLOYEE_ID=sub3.EMPLOYEE_ID";
              	 ps=con.prepareStatement(sql);
              	 ps.setInt(1, Emp_id);
              	 
              	 rs=ps.executeQuery();
              	 int i=0;
              	 while(rs.next())
              	 {
              		 i=i+1;
              		 xml=xml+"<gpf_no>"+rs.getInt("gpf_no")+"</gpf_no>";
              		 xml=xml+"<CPS_NO>"+rs.getInt("CPS_NO")+"</CPS_NO>";
                     xml=xml+"<empid>"+rs.getInt("EMPLOYEE_ID")+"</empid>";
                     xml=xml+"<empname>"+rs.getString("empname")+"</empname>";
                     xml=xml+"<desig>"+rs.getString("Designation")+"</desig>";
                     xml=xml+"<off_name>"+rs.getString("off_name")+"</off_name>";
                     xml=xml+"<flag1>"+rs.getString("flag1")+"</flag1>";
                     xml=xml+"<flags>"+rs.getString("flag")+"</flags>";
              	 }
              	 if(i>0)
              	 xml=xml+"<flag>success</flag>";
              	 else
              		xml=xml+"<flag>notexist</flag>"; 
        		   }catch(NumberFormatException es)
        		   {
        			   xml=xml+"<response><command>getempDetails</command><flag>NumFormat</flag>";
        		   } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		   xml=xml+"</response>";
             	  System.out.print("xml"+xml);
             	  out.println(xml);
        }
        	  out.close();
}
}