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

public class Create_VRS_Willingness extends HttpServlet {
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
        int tempid = 0,slno=0;
        String command = request.getParameter("command");
        System.out.println("command.." + command);

        int empid=0,offid=0;
        String willingdate="",vrs_reason="",proposed_date="";
        
        try {

        	  if (command.equalsIgnoreCase("Add")) {

        	        empid = Integer.parseInt(request.getParameter("empid"));
        	        willingdate = request.getParameter("willingdate");
        	        vrs_reason = request.getParameter("vrs_reason");
        	        proposed_date = request.getParameter("propose_date");
        	        System.out.println("vrs_reason is" + vrs_reason);
           		   xml = "<response><command>Add</command>";
        	        int cnts=0;
        	        
        	        String sqls=" SELECT COUNT(*) AS cnt " +
                                " FROM HRM_VRS_WILLINGNESS_MST " +
                                " WHERE EMPLOYEE_ID     = ? " +
                                " AND VRS_REQUEST_STATUS='Accepted' " +
                                " AND REQUEST_STATUS_ID ='FR'";
        	        ps=con.prepareStatement(sqls);
        	        ps.setInt(1, empid);
        	        rs= ps.executeQuery();
        	        if(rs.next())
        	        {
        	        	 cnts=rs.getInt("cnt");
        	        }
        	       // cnts=cnts+1;
        	       // System.out.println("count is-->"+cnts);
        	        
        	        if(cnts<=0)
        	        {
        	        	

                	//System.out.println("inside if");	         	        
               	       
                		  String sql="INSERT " +
                		   "INTO HRM_VRS_WILLINGNESS_MST " +
                		   "  ( " +
                		  
                		   "    EMPLOYEE_ID, " +
                		   "    VRS_REQUEST_DATE, " +
                		   "    VRS_REQUEST_REASON, " +
                		   "    VRS_REQUEST_STATUS, " +
                		  
                		   "    UPDATED_BY_USER_ID, " +
                		   "    UPDATED_DATE, " +
                		   "    PROCESS_FLOW_ID ,VRS_STATUS_DATE,PROPOSED_VRS_DATE,REQUEST_STATUS_ID" +
                		   "  ) " +
                		   "  VALUES " +
                		   "  ( " +
                		  
                		 
                		   "    ?, " +
                		   "   to_date( ?,'dd/mm/yyyy'), " +
                		   "    ?, " +
                		   "    ?, " +
                		   "    ?, " +
                		   "    ?, " +
                		   "    ?,to_date(?,'dd/MM/yyyy'),to_date(?,'dd/MM/yyyy'), ? )" ;
                		  ps = con.prepareStatement(sql);
                		  ps.setInt(1, empid);
                		  ps.setString(2, willingdate);
                		  ps.setString(3, vrs_reason);
                		  ps.setString(4, "Pending");
                		
                		  ps.setString(5, updatedby);
                		  ps.setTimestamp(6, ts);
                		  ps.setString(7,"CR");
                		  ps.setString(8,dateNow);
                		  ps.setString(9,proposed_date);
                		  ps.setString(10,"CR");
                         ps.executeUpdate();
                		  
                    String sql3 =
                        "SELECT REQUEST_SLNO FROM HRM_VRS_WILLINGNESS_MST  WHERE employee_id =? order by REQUEST_SLNO desc";

                    ps = con.prepareStatement(sql3);

               
                    ps.setInt(1, empid);
                    rs= ps.executeQuery();
                   
                    
                    if(rs.next())
                    {
                    	slno=rs.getInt("REQUEST_SLNO");
                    }
                    xml=xml+"<slno>"+slno+"</slno><flag>success</flag>";
                 
                   
                    con.commit();
                	        
                    		  
                    	  
        	        	
        	        	
        	        }
              	  else
              	  {
              		 // System.out.println("inside else ");
              		  
              		xml=xml+"<flag>Error</flag>";
              	  }
           
        }
        	  
        	  if (command.equalsIgnoreCase("Update")) {
        		   xml = "<response><command>Update</command>";
                  int cnts=0;
        		   empid = Integer.parseInt(request.getParameter("empid"));
       	        willingdate = request.getParameter("willingdate");
       	        vrs_reason = request.getParameter("vrs_reason");
       	        proposed_date = request.getParameter("propose_date");
        	        System.out.println("vrs_reason is" + vrs_reason);
        		   slno = Integer.parseInt(request.getParameter("slno")); 
        		   
        		   String sqls=" SELECT COUNT(*) AS cnt " +
                   " FROM HRM_VRS_WILLINGNESS_MST " +
                   " WHERE EMPLOYEE_ID     = ? " +
                   " AND VRS_REQUEST_STATUS='Accepted' " +
                   " AND REQUEST_STATUS_ID ='FR'";
       	        ps=con.prepareStatement(sqls);
       	        ps.setInt(1, empid);
       	        rs= ps.executeQuery();
       	        if(rs.next())
       	        {
       	        	 cnts=rs.getInt("cnt");
       	        }
       	        System.out.println("count is-->"+cnts);
       	        
       	        if(cnts<=2)
       	        {
       	        	
        		   
        		   
        		   
                  String sql3 = "  UPDATE HRM_VRS_WILLINGNESS_MST " +
								"  SET VRS_REQUEST_DATE =to_date(?,'dd/mm/yyyy'), " +
								"  VRS_REQUEST_REASON = ?, " +
								"  UPDATED_BY_USER_ID = ?, UPDATED_DATE=? ,PROCESS_FLOW_ID=?,proposed_vrs_date=to_date(?,'dd/mm/yyyy'), " +
								"  REQUEST_STATUS_ID = ? " +
								"  WHERE REQUEST_SLNO  = ? " +
								"  AND EMPLOYEE_ID     = ?";
                  
                  ps = con.prepareStatement(sql3);

             
                 
                  ps.setString(1, willingdate);
                  ps.setString(2, vrs_reason);
                  ps.setString(3, updatedby);
                  ps.setTimestamp(4, ts);
                  ps.setString(5, "MD");
                  ps.setString(6,proposed_date);
                  ps.setString(7,"MD");
                  ps.setInt(8, slno);
                  ps.setInt(9,empid);
                  ps.executeUpdate();
                  ps.close();

               
                  xml=xml+"<flag>success</flag>";
                  con.commit();
                  
       	     }
            	  else
            	  {
            		  System.out.println("inside else ");
            		  
            		xml=xml+"<flag>Error</flag>";
            	  }
                 
              }
        	  else if(command.equalsIgnoreCase("Validate"))
        	  {
               System.out.println("inside validate--->");
       		   xml = "<response><command>Validate</command>";
                 int cnts=0;
       		   empid = Integer.parseInt(request.getParameter("empid"));
      	        willingdate = request.getParameter("willingdate");
      	        vrs_reason = request.getParameter("vrs_reason");
      	        proposed_date = request.getParameter("propose_date");
       	        System.out.println("vrs_reason is" + vrs_reason);
       		   slno = Integer.parseInt(request.getParameter("slno"));  
       		   
       		 
       		String sqls=" SELECT COUNT(*) AS cnt " +
            " FROM HRM_VRS_WILLINGNESS_MST " +
            " WHERE EMPLOYEE_ID     = ? " +
            " AND VRS_REQUEST_STATUS='Accepted' " +
            " AND REQUEST_STATUS_ID ='FR'";
   	        ps=con.prepareStatement(sqls);
   	        ps.setInt(1, empid);
   	        rs= ps.executeQuery();
   	        if(rs.next())
   	        {
   	        	 cnts=rs.getInt("cnt");
   	        }
   	        System.out.println("count is-->"+cnts);
   	        
   	        if(cnts<=2)
   	        {
   	        	
    		   
       		   
                 String sql3 =" UPDATE HRM_VRS_WILLINGNESS_MST " +
								"  SET VRS_REQUEST_DATE=to_date(?,'dd/mm/yyyy'), " +
								"  VRS_REQUEST_REASON=?, " +
								"  UPDATED_BY_USER_ID=?, UPDATED_DATE=? ,PROCESS_FLOW_ID=?,proposed_vrs_date=to_date(?,'dd/mm/yyyy'), " +
								"  REQUEST_STATUS_ID = ? " +
								"  WHERE REQUEST_SLNO  = ? " +
								"  AND EMPLOYEE_ID     = ? ";

                 ps = con.prepareStatement(sql3);

            
                
                 ps.setString(1, willingdate);
                 ps.setString(2, vrs_reason);
                 ps.setString(3, updatedby);
                 ps.setTimestamp(4, ts);
                 ps.setString(5, "CR");
                 ps.setString(6,proposed_date);
                 ps.setString(7, "FR");
                 ps.setInt(8, slno);
                 ps.setInt(9,empid);
                 ps.executeUpdate();
                 ps.close();

              
                 xml=xml+"<flag>success</flag>";
                 con.commit();
   	     }
      	  else
      	  {
      		  System.out.println("inside else ");
      		  
      		xml=xml+"<flag>Error</flag>";
      	  }
             
        	  }
         	  if (command.equalsIgnoreCase("Delete")) {
       		   xml = "<response><command>Delete</command>";
       		   slno = Integer.parseInt(request.getParameter("slno"));  
       		   empid = Integer.parseInt(request.getParameter("empid"));
       		   System.out.println("------>"+slno+empid);
                 String sql3 ="DELETE  FROM HRM_VRS_WILLINGNESS_MST WHERE REQUEST_SLNO = ? AND EMPLOYEE_ID  = ?";

                 ps = con.prepareStatement(sql3);

                 
                 
                 ps.setInt(1, slno);
                 ps.setInt(2,empid);
                 ps.executeUpdate();
                 ps.close();

              
                 xml=xml+"<flag>success</flag>";
                 con.commit();
                
             }
         	 
         	  if (command.equalsIgnoreCase("Load")) {
          		   xml = "<response><command>Load</command>";
          		 offid = Integer.parseInt(request.getParameter("offid"));
     	       System.out.println("offid"+offid);
                    String sql3 ="SELECT REQUEST_SLNO, " +
						"  EMPLOYEE_ID, " +
						"  to_char(VRS_REQUEST_DATE,'dd/mm/yyyy') as VRS_REQUEST_DATE, to_char(PROPOSED_VRS_DATE,'dd/mm/yyyy') as proposed_vrs_date," +
						"  VRS_REQUEST_REASON, " +
						"  VRS_REQUEST_STATUS, " +
						"  VRS_STATUS_DATE, " +
						"  office_name,EMPLOYEE_STATUS_DESC, " +
						"  designation,employee_name ,process_flow_id,REQUEST_STATUS_ID" +
						" FROM " +
						"  (SELECT REQUEST_SLNO, " +
						"    EMPLOYEE_ID, " +
						"    VRS_REQUEST_DATE, " +
						"    VRS_REQUEST_REASON, " +
						"    VRS_REQUEST_STATUS, " +
						"    VRS_STATUS_DATE,process_flow_id,PROPOSED_VRS_DATE,REQUEST_STATUS_ID " +
						"  FROM HRM_VRS_WILLINGNESS_MST " +
						"  WHERE employee_id IN " +
						"    (SELECT employee_id FROM hrm_emp_controlling_office WHERE controlling_office_id=? " +
						"    ) " +
						"  )a " +
						" LEFT OUTER JOIN " +
						"  (SELECT office_id, " +
						"    designation_id, " +
						"    employee_id AS emp_id, " +
						"    employee_status_id " +
						"  FROM hrm_emp_current_posting " +
						"  )b " +
						" ON b.emp_id=a.employee_id " +
						" LEFT OUTER JOIN " +
						"  (SELECT office_id,office_name FROM com_mst_offices " +
						"  )c " +
						" ON c.office_id=b.office_id " +
						" LEFT OUTER JOIN " +
						"  (SELECT designation_id desigid, designation FROM hrm_mst_designations " +
						"  )d " +
						" ON d.desigid=b.designation_id " +
						" LEFT OUTER JOIN " +
						"  ( SELECT employee_id empid,employee_name FROM hrm_mst_employees " +
						"  )e " +
						" ON e.empid=a.employee_id " +
						" LEFT OUTER JOIN " +
						"  (SELECT EMPLOYEE_STATUS_ID status_id, " +
						"    EMPLOYEE_STATUS_DESC " +
						"  FROM HRM_MST_EMPLOYEE_STATUS " +
						"  )f " +
						" ON f.status_id=b.employee_status_id";
                    System.out.println("sql3"+sql3);
                    ps = con.prepareStatement(sql3); 
                    ps.setInt(1, offid);
                    rs=ps.executeQuery();
                    while(rs.next())
                    {
                    xml=xml+"<request>";
                    xml=xml+"<slno>"+rs.getString("REQUEST_SLNO")+"</slno>";
                    xml=xml+"<empid>"+rs.getInt("EMPLOYEE_ID")+"</empid>";
                    xml=xml+"<reqdate>"+rs.getString("VRS_REQUEST_DATE")+"</reqdate>";
                    xml=xml+"<prodate>"+rs.getString("proposed_vrs_date")+"</prodate>";
                    xml=xml+"<reason>"+rs.getString("VRS_REQUEST_REASON")+"</reason>";
                    xml=xml+"<offname>"+rs.getString("office_name")+"</offname>";
                    xml=xml+"<desig>"+rs.getString("designation")+"</desig>";
                    xml=xml+"<reqstatus>"+rs.getString("VRS_REQUEST_STATUS")+"</reqstatus>" ;
                    xml=xml+"<empname>"+rs.getString("employee_name")+"</empname>" ;
                    xml=xml+"<REQUEST_STATUS_ID>"+rs.getString("REQUEST_STATUS_ID")+"</REQUEST_STATUS_ID>" ;
                    xml=xml+"<flow_id>"+rs.getString("process_flow_id")+"</flow_id></request>";
                    }
                    xml=xml+"<flag>success</flag>";
                    
                    ps.close();
                    con.commit();
                   
                }
        	  xml=xml+"</response>";
        	  System.out.print("xml"+xml);
        	  out.println(xml);
        }

        catch (Exception e) {
        xml=xml+"<flag>fail</flag></response>";
            System.out.println("Exception in connection:" + e.getMessage());
            
        }

        finally {
        }


        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}
