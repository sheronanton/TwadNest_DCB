package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class Hrm_Emp_Retirement_Updation
 */
public class Hrm_Emp_Retirement_Updation extends HttpServlet {
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
           
            PreparedStatement ps = null,ps1=null;
            

            String updatedby = (String) session.getAttribute("UserId");
            System.out.println("updated by  - - - - - > "+updatedby);
            String strCommand = "";
            String xml = "";
            response.setContentType("text/xml");
            PrintWriter pw = response.getWriter();
            response.setHeader("Cache-Control", "no-cache");
            //HttpSession session =null ;
//            try
//            {
//             session = request.getSession(false);
//            }
//            catch(Exception es)
//            {
//            	
//            }
//            try {
//                if (session == null) {
//                    xml =
//     "<response><command>sessionout</command><flag>sessionout</flag></response>";
//                    pw.println(xml);
//                    System.out.println(xml);
//                    pw.close();
//                    return;
//
//                }
//                System.out.println(session);
//
//            } catch (Exception e) {
//                System.out.println("Redirect Error :" + e);
//            }

            try {
                strCommand = request.getParameter("command");
                System.out.println("Command:" + strCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(strCommand.equalsIgnoreCase("Validate_retireDate"))
            {

            	
            	
            	try
            	{
            		 xml = "<response><command>Validate_retireDate</command>";
            		 
                 	int emp_id=Integer.parseInt(request.getParameter("emp_id"));
                 	String remarks=request.getParameter("remarks");
                 	String retireDate=request.getParameter("retireDate");
                 	String dob=request.getParameter("dob");
                 	System.out.println("date of birth - "+dob);
                 	//int Aadher_no1=Integer.parseInt(request.getParameter("Aadher_no1"));
                 	//int Aadher_no2=Integer.parseInt(request.getParameter("Aadher_no2"));
                 	
                
                 	
                 	
                 	
                 	
                 	
                 	System.out.println("remarks -------->  "+remarks);
                 	

            		String ss="UPDATE HRM_EMP_RETIREMENT_DATA " +
            				"SET     REMARKS          = ?,"+
            				"  UPDATED_BY          = ?,"+
            				"  UPDATED_DATE          = clock_timestamp(),"+
            				"  process_status_id='FR' " +
            				"WHERE employee_id  = ?";
            		ps=connection.prepareStatement(ss);
            		//ps.setString(1, retireDate);
            		ps.setString(1, remarks);
            		ps.setString(2, updatedby);
            		ps.setInt(3, emp_id);
            	    ps.executeUpdate();
            	    
            		 ss="UPDATE hrm_mst_employees " +
            				"SET     DATE_OF_BIRTH          =  to_date(?,'dd-mm-yyyy'),"+
            				"  UPDATED_BY_user_id          = ?,"+
            				"  UPDATED_DATE          = clock_timestamp(),"+
            				"  process_flow_status_id='FR' " +
            				"WHERE employee_id  = ?";
            		ps=connection.prepareStatement(ss);
            		ps.setString(1, dob);
            		ps.setString(2, remarks);
            		ps.setString(3, updatedby);
            		ps.setInt(3, emp_id);
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
            if(strCommand.equalsIgnoreCase("Update_retireDate"))
            {
            	
            	
            	try
            	{
            		ResultSet rs=null;
            		 xml = "<response><command>Update_retireDate</command>";
            		 int count=0,c=0;
                 	int emp_id=Integer.parseInt(request.getParameter("emp_id"));
                 	String remarks=request.getParameter("remarks");
                 	String retireDate=request.getParameter("retireDate");
                 	String dob=request.getParameter("dob");
                 	System.out.println("date of birth - "+dob);
                 	String retire_tobe=request.getParameter("retire_tobe");
                 	//int Aadher_no1=Integer.parseInt(request.getParameter("Aadher_no1"));
                 	//int Aadher_no2=Integer.parseInt(request.getParameter("Aadher_no2"));
                 	
                String emp_check_sql=" select EMPLOYEE_ID from HRM_EMP_RETIREMENT_DATA_LOG where employee_id=? ";
           
                ps=connection.prepareStatement(emp_check_sql);
                ps.setInt(1, emp_id);
                rs=ps.executeQuery();
             	System.out.println(emp_check_sql);
                if(!rs.next())
                {
                	
                     
                 	System.out.println("-------------------->");
                 	
                 	String sql_query=" INSERT " +
         			" INTO HRM_EMP_RETIREMENT_DATA_LOG " +
         			"  ( " +
         			"    EMPLOYEE_ID, " +
         			"    DATE_OF_BIRTH, " +
         			"    RETIREDATE, " +
         			"    RETIRE_TO_BE, " +
         			"    REMARKS, " +
         			"    UPDATED_BY, " +
         			"    UPDATED_DATE, " +
         			"    PROCESS_STATUS_ID " +
         			"  ) " +
         			"  VALUES " +
         			"  ( " +
         			"    ?, " +
         			"    to_date(?,'dd-mm-yyyy'), " +
         			"    to_date(?,'dd-mm-yyyy'), " +
         			"    to_date(?,'dd-mm-yyyy'), " +
         			"    ?, " +
         			"    ?, " +
         			"    clock_timestamp() ,?" +
         			"  ) ";
         	System.out.println(sql_query);
         			ps1=connection.prepareStatement(sql_query);
                 	ps1.setInt(1, emp_id);
            		ps1.setString(2, dob);
            		ps1.setString(3, retire_tobe);
            		ps1.setString(4, retireDate);
            		ps1.setString(5, remarks);
            		ps1.setString(6, updatedby);
               		ps1.setString(7, "MD");
            		ps1.executeUpdate();

                }
                else
                {
                	System.out.println("+++++++++-------------------->");
                	String sql_query=" UPDATE HRM_EMP_RETIREMENT_DATA_LOG " +
					 " SET " +
			          "  DATE_OF_BIRTH     = to_date(?,'dd-mm-yyyy'), " +
			          "  RETIREDATE          = to_date(?,'dd-mm-yyyy'), " +
			          "  retire_to_be          = to_date(?,'dd-mm-yyyy'), " +
			          "  REMARKS             = ?, " +
			          "  UPDATED_BY          = ?, " +
			          "  UPDATED_DATE      = clock_timestamp(), " +
		            	" PROCESS_STATUS_ID = 'MD' " +
	            		" WHERE " +
			          " EMPLOYEE_ID = ?"
	            		;
	
	ps1=connection.prepareStatement(sql_query);
	ps1.setInt(6, emp_id);
	ps1.setString(1, dob);
	ps1.setString(3, retireDate);
	ps1.setString(2, retire_tobe);
	ps1.setString(4, remarks);
	ps1.setString(5, updatedby);
	ps1.executeUpdate();
                }
                 	
            		//ps.setString(2, dob);
            		
            		
            		 
                 	//Long fbint=Long.parseLong(final_aadherno);
                 	
                 	
                 	System.out.println("remarks -------->  "+remarks);
                 	
                 	 String empid_check_sql="select EMPLOYEE_ID from HRM_EMP_RETIREMENT_DATA where employee_id=?";
                     ps=connection.prepareStatement(empid_check_sql);
                     ps.setInt(1, emp_id);
                     rs=ps.executeQuery();
                     if(!rs.next())
                     {
                    	 String ss= "INSERT " +
            			 "INTO HRM_EMP_RETIREMENT_DATA " +
            			 "  ( " +
            			 "    EMPLOYEE_ID, " +
            			 "    DATE_OF_BIRTH, " +
            			 "    RETIREDATE, " +
            			 "    RETIRE_TO_BE, " +
            			 "    REMARKS, " +
            			 "    UPDATED_BY, " +
            			 "    UPDATED_DATE, " +
            			 "    PROCESS_STATUS_ID " +
            			 "  ) " +
            			 "  VALUES " +
            			 "  ( " +
            			 "    ?, " +
            			 "    to_date(?,'dd-mm-yyyy'), " +
            			 "    to_date(?,'dd-mm-yyyy'), " +
            			 "    to_date(?,'dd-mm-yyyy'), " +
            			 "    ?, " +
            			 "    ?, " +
            			 "    clock_timestamp(), " +
            			 "    'CR' " +
            			 "  )"
;
         		ps1=connection.prepareStatement(ss);
         		ps1.setString(4, retireDate);
         		ps1.setString(3, retire_tobe);
         		ps1.setString(2, dob);
         		ps1.setString(5, remarks);
         		ps1.setString(6, updatedby);
         		ps1.setInt(1, emp_id);
         		
         		 ps1.executeUpdate();
                     }
                     else
                     {
                    	 System.out.println("else------->");
                    	 String ss="UPDATE HRM_EMP_RETIREMENT_DATA " +
         				"SET retiredate     =to_date(?,'dd-mm-yyyy'), " +
         			"	RETIRE_TO_BE     =to_date(?,'dd-mm-yyyy'), " +
         				"  REMARKS          = ?,"+
         				"  UPDATED_BY          = ?,"+
         				"  UPDATED_DATE          = clock_timestamp(),"+
         				"  process_status_id='MD' " +
         				"WHERE employee_id  = ?";
         		ps1=connection.prepareStatement(ss);
         		ps1.setString(2, retireDate);
         		ps1.setString(1, retire_tobe);
         		ps1.setString(3, remarks);
         		ps1.setString(4, updatedby);
         		ps1.setInt(5, emp_id);
         		
         		 ps1.executeUpdate();
                     }
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
                    			"  dob, " +
                    			"  DECODE(dob_log,NULL, dob,dob_log) AS DATE_OF_BIRTH, " +
                    			"  GPF_NO, " +
                    			"  RETIREDATE, " +
                    			"  RETIRE_TO_BE, " +
                    			"  PROCESS_STATUS_ID, " +
                    			"  REMARKS, " +
                    			"  employee_status_id " +
                    			"FROM " +
                    			"  (SELECT EMPLOYEE_NAME, " +
                    			"    DESIGNATION, " +
                    			"    dob, " +
                    			"    DECODE(DATE_OF_BIRTH, NULL, dob_log,DATE_OF_BIRTH) AS dob_log, " +
                    			"    GPF_NO, " +
                    			"    DECODE( RETIREDATE,NULL,RETIREDATE_LOG,RETIREDATE) AS RETIREDATE, " +
                    			"    RETIRE_TO_BE_LOG                                   AS RETIRE_TO_BE, " +
                    			"    PROCESS_STATUS_ID, " +
                    			"    DECODE(REMARKS,NULL,REMARKS_LOG,REMARKS) AS REMARKS, " +
                    			"    employee_status_id " +
                    			"  FROM " +
                    			"    (SELECT EMPLOYEE_ID, " +
                    			"      EMPLOYEE_NAME " +
                    			"      ||DECODE(EMPLOYEE_INITIAL,NULL,' ','.' " +
                    			"      ||EMPLOYEE_INITIAL) AS EMPLOYEE_NAME, " +
                    			"      DATE_OF_BIRTH       AS dob, " +
                    			"      GPF_NO " +
                    			"    FROM HRM_MST_EMPLOYEES " +
                    			"    WHERE EMPLOYEE_ID=? " +
                    			"    )A " +
                    			"  JOIN " +
                    			"    (SELECT EMPLOYEE_ID, DESIGNATION FROM VIEW_EMPLOYEE2 " +
                    			"    )b " +
                    			"  ON a.EMPLOYEE_ID=b.EMPLOYEE_ID " +
                    			"  LEFT OUTER JOIN " +
                    			"    (SELECT to_date(RETIREDATE,'dd-mm-yyyy') AS RETIREDATE, " +
                    			"      DECODE(REMARKS,'null','',REMARKS)      AS REMARKS, " +
                    			"      DATE_OF_BIRTH, " +
                    			"      PROCESS_STATUS_ID, " +
                    			"      EMPLOYEE_ID " +
                    			"    FROM hrm_emp_retirement_data " +
                    			"    )c " +
                    			"  ON a.EMPLOYEE_ID=c.EMPLOYEE_ID " +
                    			"  LEFT OUTER JOIN " +
                    			"    (SELECT to_date(RETIREDATE,'dd-mm-yyyy') AS RETIREDATE_LOG, " +
                    			"      to_date(RETIRE_TO_BE,'dd-mm-yyyy')     AS RETIRE_TO_BE_LOG, " +
                    			"      DECODE(REMARKS,'null','',REMARKS)      AS REMARKS_LOG, " +
                    			"      DATE_OF_BIRTH                          AS dob_log, " +
                    			"      PROCESS_STATUS_ID, " +
                    			"      EMPLOYEE_ID " +
                    			"    FROM hrm_emp_retirement_data_log " +
                    			"    )c1 " +
                    			"  ON a.EMPLOYEE_ID=c1.EMPLOYEE_ID " +
                    			"  LEFT OUTER JOIN " +
                    			"    (SELECT employee_id,employee_status_id FROM hrm_emp_current_posting " +
                    			"    )d " +
                    			"  ON d.employee_id=a.employee_id " +
                    			"  )";
                    	
                    	System.out.println("SQL QUERY = "+sql);
                    	
//    				    sql="SELECT EMPLOYEE_NAME, " +
//    						"  DESIGNATION, " +
//    						"  DATE_OF_BIRTH, " +
//    						"  GPF_NO " +
//    						"FROM " +
//    						"  (SELECT EMPLOYEE_ID, " +
//    						"    EMPLOYEE_NAME " +
//    						"    ||DECODE(EMPLOYEE_INITIAL,NULL,' ','.' " +
//    						"    ||EMPLOYEE_INITIAL) AS EMPLOYEE_NAME, " +
//    						"    DATE_OF_BIRTH, " +
//    						"    GPF_NO " +
//    						"  FROM HRM_MST_EMPLOYEES " +
//    						"  WHERE EMPLOYEE_ID="+emp_id+" " +
//    						"  )A " +
//    						"JOIN " +
//    						"  (SELECT EMPLOYEE_ID, DESIGNATION FROM VIEW_EMPLOYEE2 " +
//    						"  )b " +
//    						"ON a.EMPLOYEE_ID=b.EMPLOYEE_ID";
    						                	
                      ps=connection.prepareStatement(sql);
                      ps.setInt(1,emp_id);
                     // System.out.println("sql is--->"+sql);
                      
                        result1 = ps.executeQuery();
                        /* (((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                        {
                                xml = xml + "<flags>Y</flag>";
                               // flag = true;
                            }
                        else
                        {
                        	 xml = xml + "<flags>N</flag>";
                        }*/
                        while (result1.next()) {

                            xml =xml + "<ename>" + result1.getString("EMPLOYEE_NAME") + "</ename>";
                            //xml =xml + "<AADHER_NO>" + result1.getString("AADHER_NO") + "</AADHER_NO>";
                            xml =xml + "<egpf>" + result1.getLong("GPF_NO") + "</egpf>";
                            xml =xml + "<DESIGNATION>" + result1.getString("DESIGNATION") + "</DESIGNATION>";   
                         
                            if (result1.getDate("DATE_OF_BIRTH") != null) {
                                String[] sd =result1.getDate("DATE_OF_BIRTH").toString().split("-");
                                String od = sd[2] + "/" + sd[1] + "/" + sd[0];
                                System.out.println("date of birth = "+od);
                                System.out.println(result1.getDate("DATE_OF_BIRTH"));
                                xml = xml + "<edob>" + od + "</edob>";
                            
                                
                            } 
                            else
                            {
                                xml = xml + "<edob>-</edob>";
                            }
                            if (result1.getDate("RETIREDATE") != null) {
                                String[] sd =result1.getDate("RETIREDATE").toString().split("-");
                                int year=Integer.parseInt(sd[0])+2000;
                                String od = sd[2] + "/" + sd[1] + "/" + year;
                                xml = xml + "<retire>" + od + "</retire>";
                                System.out.println(result1.getDate("RETIREDATE"));
                            } 
                            else
                            {
                                xml = xml + "<retire>-</retire>";
                            }
                          if (result1.getDate("RETIRE_TO_BE") != null) {
                                String[] sd =result1.getDate("RETIRE_TO_BE").toString().split("-");
                                int year=Integer.parseInt(sd[0])+2000;
                                String od = sd[2] + "/" + sd[1] + "/" + year;
                                xml = xml + "<retire_tobe>" + od + "</retire_tobe>";
                                System.out.println( od);
                            } 
                            else
                            {
                                xml = xml + "<retire_tobe>-</retire_tobe>";
                            }
                            xml=xml+"<remarks>"+result1.getString("REMARKS")+"</remarks>";
                            xml=xml+"<process_flow_id>"+result1.getString("PROCESS_STATUS_ID")+"</process_flow_id>";
                            xml=xml+"<employee_status_id>"+result1.getString("employee_status_id")+"</employee_status_id>";
                            
                            
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
            		 boolean flag1 = true;
            		 while(result1.next())
            		 {
            			 xml =xml + "<count><AADHAR_NO>" + result1.getString("AADHAR_NO") + "</AADHAR_NO></count>";
            		 }
            		 
            		try
            		{
            		
            		 if (((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                     {
                             xml = xml + "<flags>Y</flags>";
                            // flag = true;
                      }
            		 
                     else if(session.getAttribute("Admin") == null ||
                             !((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
                     {
                     	 xml = xml + "<flags>N</flags>";
                     	 flag1 = false;
                     }
                     else
                     {
                    	 xml = xml + "<flags>N</flags>";
                    	 flag1 = false;
                     }
            		}
            		catch(Exception e)
            		{
            			xml = xml + "<flags>N</flags>";
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
