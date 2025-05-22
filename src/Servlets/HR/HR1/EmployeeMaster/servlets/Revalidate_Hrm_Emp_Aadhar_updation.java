package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import org.syntax.jedit.InputHandler.insert_break;


public class Revalidate_Hrm_Emp_Aadhar_updation extends HttpServlet {
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
        // Statement statement=null;
        String sql="";
        ResultSet result1 = null;
       
        PreparedStatement ps = null;
        


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
            System.out.println("Command:" + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(strCommand.equalsIgnoreCase("Update_aadher"))
        {
        	
        	
        	try
        	{
        		 xml = "<response><command>Update_aadher</command>";
        		 
             	int emp_id=Integer.parseInt(request.getParameter("emp_id"));
             	//int Aadher_no=Integer.parseInt(request.getParameter("Aadher_no"));
             	//int Aadher_no1=Integer.parseInt(request.getParameter("Aadher_no1"));
             	//int Aadher_no2=Integer.parseInt(request.getParameter("Aadher_no2"));
             	
            
             	String Aadher_no=request.getParameter("Aadher_no");
             	String Aadher_no1=request.getParameter("Aadher_no1");
             	String Aadher_no2=request.getParameter("Aadher_no2");
             	
             	String final_aadherno=Aadher_no.concat(Aadher_no1+Aadher_no2);
             	 
             	//Long fbint=Long.parseLong(final_aadherno);
             	
             	
             	System.out.println("final_aadherno--->"+final_aadherno);
             	
        		String ss="UPDATE HRM_MST_EMPLOYEES SET AADHAR_NO=? WHERE EMPLOYEE_ID="+emp_id+"";
        		ps=connection.prepareStatement(ss);
        		ps.setString(1, final_aadherno);
        		
        		 ps.executeUpdate();
        		 xml = xml + "<flag>success</flag>";
        	}
        	
        	catch(Exception e)
        	{
        		System.out.println("Error--->"+e);
        		 xml = xml + "<flag>failure</flag>";
        	}
        	  xml = xml + "</response>";
        }

        if (strCommand.equalsIgnoreCase("getemp_details")) {
           
            xml = "<response><command>getemp_details</command>";
            try {
            	int emp_id = Integer.parseInt(request.getParameter("emp_id"));
            	
                UserProfile up = null;
             
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
                		connection.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, emp_id);
                result1 = ps.executeQuery();
                if (!result1.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != emp_id) {
                    int OfficeId = 0;
                    sql =
 "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, emp_id);
                    result1 = ps.executeQuery();

                    if (result1.next()) {
                        OfficeId = result1.getInt("CONTROLLING_OFFICE_ID");
                    }

                    if (OfficeId != 0) {
                        sql =
 "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        result1 = ps.executeQuery();
                        if (result1.next()) {
                            int offid = result1.getInt("OFFICE_ID");
                            if (offid != OfficeId) {
                                //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
                                // xml=xml+"<flag>failure1</flag>";
                                if (offid!=5000)
                                {
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
                        // xml=xml+"<flag>failure3</flag>";
                        // flag=false;
                        if (session.getAttribute("Admin") == null ||
                            !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES")) {
                            xml = xml + "<flag>failure3</flag>";
                            flag = false;
                        }
                    }
                } else {

                    //xml=xml+"<flag>failure4</flag>";
                    //flag=false;
                }

                if (flag) {
                	
                	sql="SELECT EMPLOYEE_NAME, " +
"  DESIGNATION, " +
"  DATE_OF_BIRTH, " +
"  GPF_NO " +
"FROM " +
"  (SELECT EMPLOYEE_ID, " +
"    EMPLOYEE_NAME " +
"    ||DECODE(EMPLOYEE_INITIAL,NULL,' ','.' " +
"    ||EMPLOYEE_INITIAL) AS EMPLOYEE_NAME, " +
"    DATE_OF_BIRTH, " +
"    GPF_NO " +
"  FROM HRM_MST_EMPLOYEES " +
"  WHERE EMPLOYEE_ID="+emp_id+" " +
"  )A " +
"JOIN " +
"  (SELECT EMPLOYEE_ID, DESIGNATION FROM VIEW_EMPLOYEE2 " +
"  )b " +
"ON a.EMPLOYEE_ID=b.EMPLOYEE_ID";
                	
                  ps=connection.prepareStatement(sql);
                  
                  System.out.println("sql is--->"+sql);
                  
                    result1 = ps.executeQuery();

                    while (result1.next()) {

                        xml =xml + "<ename>" + result1.getString("EMPLOYEE_NAME") + "</ename>";
                        //xml =xml + "<AADHER_NO>" + result1.getString("AADHER_NO") + "</AADHER_NO>";
                        xml =xml + "<egpf>" + result1.getLong("GPF_NO") + "</egpf>";
                        xml =xml + "<DESIGNATION>" + result1.getString("DESIGNATION") + "</DESIGNATION>";         
                        if (result1.getDate("DATE_OF_BIRTH") != null) {
                            String[] sd =result1.getDate("DATE_OF_BIRTH").toString().split("-");
                            String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                            xml = xml + "<edob>" + od + "</edob>";
                        } 
                        else
                        {
                            xml = xml + "<edob>-</edob>";
                        }
                       
                      
                        xml = xml + "<flag>success</flag>";
                    }
                   
                } 
                else {
                    xml = xml + "<flag>failure</flag>";
                }


            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            } finally {


            }
            xml = xml + "</response>";

        }
        
        if(strCommand.equalsIgnoreCase("getaadher"))
        {
        	 xml = "<response><command>getaadher</command>";
        	try
        	{
        		int emp_id = Integer.parseInt(request.getParameter("emp_id"));
        		 sql="SELECT DECODE(AADHAR_NO,NULL,' ',AADHAR_NO) AS AADHAR_NO " +
        				"FROM hrm_mst_employees " +
        				"WHERE employee_id="+emp_id+"";
        		 ps=connection.prepareStatement(sql);
        		 result1=ps.executeQuery();
        		 while(result1.next())
        		 {
        			 xml =xml + "<count><AADHAR_NO>" + result1.getString("AADHAR_NO") + "</AADHAR_NO></count>";
        		 }
        		 xml = xml + "<flag>success</flag>";
        	}
        	catch(Exception es)
        	{
        		System.out.println("Error--->"+es);
        		xml = xml + "<flag>failure</flag>";
        	}
        	 xml = xml + "</response>";
        }
         
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
       


    }
    
}
