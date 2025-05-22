package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

public class ViewAllResponseServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("show me");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String xml = "";
        try {

        	LoadDriver driver=new LoadDriver();
        	connection=driver.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }
        
        xml = "<response>";
        
        
        String command=request.getParameter("command");
        System.out.println("COMMAND((((((()))))))====="+command);
        if(command.equalsIgnoreCase("major"))
        {
        	int count=0;
        	xml+="<command>get</command>";
        	String major_value=request.getParameter("major_value");
        	
        	String cond = "";
        	if(major_value.equalsIgnoreCase("ALL")) {
        		cond = " or 1=1";
        	}
        	
        	System.out.println("MAJOR_VALUE========"+major_value);
        	xml+="<MINOR_SYSTEM_ID>"+"ALL"+"</MINOR_SYSTEM_ID><MINOR_SYSTEM_DESC>"+"ALL CATEGORY"+"</MINOR_SYSTEM_DESC>";
        	String sql="SELECT MINOR_SYSTEM_ID, " +
        			"  MINOR_SYSTEM_DESC " +
        			"FROM SEC_MST_MINOR_SYSTEMS " +
        			"WHERE MAJOR_SYSTEM_ID=? "+ cond + " order by  MINOR_SYSTEM_DESC " ;
        	try {
				ps=connection.prepareStatement(sql);
				ps.setString(1,major_value);
				res=ps.executeQuery();
				while(res.next())
				{
					count++;
					
					xml+="<MINOR_SYSTEM_ID>"+res.getString("MINOR_SYSTEM_ID")+"</MINOR_SYSTEM_ID>";
					xml+="<MINOR_SYSTEM_DESC>"+res.getString("MINOR_SYSTEM_DESC")+"</MINOR_SYSTEM_DESC>";
					
				}
				res.close();
				ps.close();
				System.out.println("after xml========"+xml);
				
				xml+="<count>"+count+"</count>";
				xml+="<flag>success</flag>";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				xml+="<flag>failure</flag>";
				e.printStackTrace();
			}
        	
        }
        
       
        
        if(command.equalsIgnoreCase("submit"))
        {
        try {
        	xml+="<command>load</command>";
            connection.clearWarnings();


            String fromdate = request.getParameter("txtfromdate");
            String todate = request.getParameter("txttodate");
            String status = request.getParameter("cmbstatus");
            String major = request.getParameter("cmbmajor");
            String cmbMinor=request.getParameter("cmbMinor");
            System.out.println("MINOR============="+cmbMinor);
            String office=request.getParameter("office");
            System.out.println("office============="+office);
            java.sql.Date dateOfAttachment = null;
            System.out.println("before converting date");
            String dateString = fromdate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d;
            d = dateFormat.parse(fromdate.trim());
            System.out.println("util date is:" + d);
            dateFormat.applyPattern("yyyy-MM-dd");
            dateString = dateFormat.format(d);
            dateOfAttachment = java.sql.Date.valueOf(dateString);

            java.sql.Date dateto = null;
            System.out.println("before converting date");
            String dateString1 = todate;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date d1;
            d1 = dateFormat1.parse(todate.trim());
            dateFormat1.applyPattern("yyyy-MM-dd");
            dateString1 = dateFormat1.format(d1);
            dateto = java.sql.Date.valueOf(dateString1);

            System.out.println("fromdate" + dateOfAttachment);
            System.out.println("todate" + dateto);
            String s1 = "", s2 = "";
            if (status.equalsIgnoreCase("A")) {
                s1 = "O";
                s2 = "C";
            } else {
                s1 = status;
                s2 = status;
            }

            System.out.println("Status:" + status);
            System.out.println("s1" + s1);
            System.out.println("s2" + s2);
            System.out.println("s=" + s1 + "  " + s2);
            /* String sql="select a.issue_id,a.ISSUE_REPORTED_DATE,a.ISSUE_PRIORITY,a.ISSUE_SOLUTION,b.MAJOR_SYSTEM_ID,b.major_system_desc,c.MINOR_SYSTEM_ID,c.minor_system_desc,a.ISSUE_TITLE,a.ISSUE_DESC,a.ISSUE_STATUS,a.REPORTED_BY_USER_ID from HLP_ISSUE_REQUESTS a,sec_mst_major_systems b,sec_mst_minor_systems c where a.major_system_id=b.major_system_id and a.minor_system_id=c.minor_system_id " +
            " and (a.ISSUE_REPORTED_DATE between ? and ?) and (a.ISSUE_STATUS=? or a.ISSUE_STATUS=?)   "+
            " order by a.issue_id";
            */
            String sql="";
            if(cmbMinor.equalsIgnoreCase("ALL") && office.equalsIgnoreCase("ALL"))
            {
            		String cond = "";
            		if(major.equalsIgnoreCase("ALL")) {
            			 cond = " or 1=1";
            		}
            	  
            	  sql =
              		"SELECT a.issue_id, " +
              				"  a.ISSUE_REPORTED_DATE, " +
              				"  a.ISSUE_PRIORITY, " +
              				"  a.ISSUE_SOLUTION, " +
              				"  b.MAJOR_SYSTEM_ID, " +
              				"  b.major_system_desc, " +
              				"  c.MINOR_SYSTEM_ID, " +
              				"  c.minor_system_desc, " +
              				"  a.ISSUE_TITLE, " +
              				"  a.ISSUE_DESC, " +
              				"  a.ISSUE_STATUS, " +
              				"  a.REPORTED_BY_USER_ID, " +
              				"  case "+
              				 "	when d.employee_initial is NULL then d.employee_name||' '"+
              				 "	when d.employee_initial is NOT NULL then d.employee_name||'.'||d.employee_initial"+
              				 "	 end as employee_name,"+
              				"  d.employee_id, " +
              				"  f.office_name " +
              				"FROM HLP_ISSUE_REQUESTS a " +
              				"INNER JOIN sec_mst_major_systems b " +
              				"ON a.major_system_id=b.major_system_id " +
              				"INNER JOIN sec_mst_minor_systems c " +
              				"ON a.minor_system_id=c.minor_system_id " +
              				"INNER JOIN sec_mst_users g " +
              				"ON g.user_id=a.REPORTED_BY_USER_ID " +
              				"INNER JOIN hrm_mst_employees d " +
              				"ON g.employee_id=d.employee_id " +
              				"LEFT OUTER JOIN hrm_emp_current_posting e " +
              				"ON e.employee_id=d.employee_id " +
              				"LEFT OUTER JOIN com_mst_offices f " +
              				"ON f.office_id=a.office_id " +
              				"WHERE (a.ISSUE_REPORTED_DATE BETWEEN ? AND ?) " +
              				"AND (a.ISSUE_STATUS  =? " +
              				"OR a.ISSUE_STATUS    =?) " +
              				"AND (a.MAJOR_SYSTEM_ID=? "+cond+"  )   " +              				
              				"ORDER BY a.issue_id";
            	  ps = connection.prepareStatement(sql);
                  ps.setDate(1, dateOfAttachment);
                  ps.setDate(2, dateto);
                  ps.setString(3, s1);
                  ps.setString(4, s2);
                  ps.setString(5, major);
                //  ps.setString(6, cmbMinor);
                  
            }
            else if(!cmbMinor.equals("ALL") && office.equalsIgnoreCase("ALL"))
            {
            	System.out.println(dateOfAttachment);
            	System.out.println(dateto);
            	System.out.println(s1);
            	System.out.println(s2);
            	System.out.println(major);
            	System.out.println(cmbMinor);
             sql =
            		"SELECT a.issue_id, " +
            				"  a.ISSUE_REPORTED_DATE, " +
            				"  a.ISSUE_PRIORITY, " +
            				"  a.ISSUE_SOLUTION, " +
            				"  b.MAJOR_SYSTEM_ID, " +
            				"  b.major_system_desc, " +
            				"  c.MINOR_SYSTEM_ID, " +
            				"  c.minor_system_desc, " +
            				"  a.ISSUE_TITLE, " +
            				"  a.ISSUE_DESC, " +
            				"  a.ISSUE_STATUS, " +
            				"  a.REPORTED_BY_USER_ID, " +
            				"  case "+
             				 "	when d.employee_initial is NULL then d.employee_name||' '"+
             				 "	when d.employee_initial is NOT NULL then d.employee_name||'.'||d.employee_initial"+
             				 "	 end as employee_name,"+
            				"  d.employee_id, " +
            				"  f.office_name " +
            				"FROM HLP_ISSUE_REQUESTS a " +
            				"INNER JOIN sec_mst_major_systems b " +
            				"ON a.major_system_id=b.major_system_id " +
            				"INNER JOIN sec_mst_minor_systems c " +
            				"ON a.minor_system_id=c.minor_system_id " +
            				"INNER JOIN sec_mst_users g " +
            				"ON g.user_id=a.REPORTED_BY_USER_ID " +
            				"INNER JOIN hrm_mst_employees d " +
            				"ON g.employee_id=d.employee_id " +
            				"LEFT OUTER JOIN hrm_emp_current_posting e " +
            				"ON e.employee_id=d.employee_id " +
            				"LEFT OUTER JOIN com_mst_offices f " +
            				"ON f.office_id=a.office_id " +
            				"WHERE (a.ISSUE_REPORTED_DATE BETWEEN ? AND ?) " +
            				"AND (a.ISSUE_STATUS  =? " +
            				"OR a.ISSUE_STATUS    =?) " +
            				"AND a.MAJOR_SYSTEM_ID=? " +
            				"AND c.minor_system_id=? " +
            				"ORDER BY a.issue_id";
             ps = connection.prepareStatement(sql);
             ps.setDate(1, dateOfAttachment);
             ps.setDate(2, dateto);
             ps.setString(3, s1);
             ps.setString(4, s2);
             ps.setString(5, major);
             ps.setString(6, cmbMinor);
            }
            else if(cmbMinor.equalsIgnoreCase("ALL") && !office.equals("ALL"))
            {
            	String cond = "";
        		if(major.equalsIgnoreCase("ALL")) {
        			 cond = " or 1=1";
        		}
            	sql =
                		"SELECT a.issue_id, " +
                				"  a.ISSUE_REPORTED_DATE, " +
                				"  a.ISSUE_PRIORITY, " +
                				"  a.ISSUE_SOLUTION, " +
                				"  b.MAJOR_SYSTEM_ID, " +
                				"  b.major_system_desc, " +
                				"  c.MINOR_SYSTEM_ID, " +
                				"  c.minor_system_desc, " +
                				"  a.ISSUE_TITLE, " +
                				"  a.ISSUE_DESC, " +
                				"  a.ISSUE_STATUS, " +
                				"  a.REPORTED_BY_USER_ID, " +
                				"  case "+
                 				 "	when d.employee_initial is NULL then d.employee_name||' '"+
                 				 "	when d.employee_initial is NOT NULL then d.employee_name||'.'||d.employee_initial"+
                 				 "	 end as employee_name,"+
                				"  d.employee_id, " +               				
                				"  f.office_name " +
                				"FROM HLP_ISSUE_REQUESTS a " +
                				"INNER JOIN sec_mst_major_systems b " +
                				"ON a.major_system_id=b.major_system_id " +
                				"INNER JOIN sec_mst_minor_systems c " +
                				"ON a.minor_system_id=c.minor_system_id " +
                				"INNER JOIN sec_mst_users g " +
                				"ON g.user_id=a.REPORTED_BY_USER_ID " +
                				"INNER JOIN hrm_mst_employees d " +
                				"ON g.employee_id=d.employee_id " +
                				"LEFT OUTER JOIN hrm_emp_current_posting e " +
                				"ON e.employee_id=d.employee_id " +
                				"LEFT OUTER JOIN com_mst_offices f " +
                				"ON f.office_id=a.office_id " +
                				"WHERE (a.ISSUE_REPORTED_DATE BETWEEN ? AND ?) " +
                				"AND (a.ISSUE_STATUS  =? " +
                				"OR a.ISSUE_STATUS    =?) " +
                				"AND (a.MAJOR_SYSTEM_ID=? "+cond+"  )   " +  
                				"AND a.OFFICE_ID=?::numeric " +                				
                				"ORDER BY a.issue_id";
                 ps = connection.prepareStatement(sql);
                 ps.setDate(1, dateOfAttachment);
                 ps.setDate(2, dateto);
                 ps.setString(3, s1);
                 ps.setString(4, s2);
                 ps.setString(5, major);
                 ps.setString(6, office);
                
            }
            
            else
            {
            	sql="SELECT a.issue_id, " +
        				"  a.ISSUE_REPORTED_DATE, " +
        				"  a.ISSUE_PRIORITY, " +
        				"  a.ISSUE_SOLUTION, " +
        				"  b.MAJOR_SYSTEM_ID, " +
        				"  b.major_system_desc, " +
        				"  c.MINOR_SYSTEM_ID, " +
        				"  c.minor_system_desc, " +
        				"  a.ISSUE_TITLE, " +
        				"  a.ISSUE_DESC, " +
        				"  a.ISSUE_STATUS, " +
        				"  a.REPORTED_BY_USER_ID, " +
        				"  case "+
         				 "	when d.employee_initial is NULL then d.employee_name||' '"+
         				 "	when d.employee_initial is NOT NULL then d.employee_name||'.'||d.employee_initial"+
         				 "	 end as employee_name,"+
        				"  d.employee_id, " +
        				"  f.office_name " +
        				"FROM HLP_ISSUE_REQUESTS a " +
        				"INNER JOIN sec_mst_major_systems b " +
        				"ON a.major_system_id=b.major_system_id " +
        				"INNER JOIN sec_mst_minor_systems c " +
        				"ON a.minor_system_id=c.minor_system_id " +
        				"INNER JOIN sec_mst_users g " +
        				"ON g.user_id=a.REPORTED_BY_USER_ID " +
        				"INNER JOIN hrm_mst_employees d " +
        				"ON g.employee_id=d.employee_id " +
        				"LEFT OUTER JOIN hrm_emp_current_posting e " +
        				"ON e.employee_id=d.employee_id " +
        				"LEFT OUTER JOIN com_mst_offices f " +
        				"ON f.office_id=a.office_id " +
        				"WHERE (a.ISSUE_REPORTED_DATE BETWEEN ? AND ?) " +
        				"AND (a.ISSUE_STATUS  =? " +
        				"OR a.ISSUE_STATUS    =?) " +
        				"AND a.MAJOR_SYSTEM_ID=? " +
        				"AND a.OFFICE_ID=?::numeric " +
        				"AND c.minor_system_id=? " +
        				"ORDER BY a.issue_id";
            	
				         ps = connection.prepareStatement(sql);
				         ps.setDate(1, dateOfAttachment);
				         ps.setDate(2, dateto);
				         ps.setString(3, s1);
				         ps.setString(4, s2);
				         ps.setString(5, major);
				         ps.setString(6, office);
				         ps.setString(7, cmbMinor);
            }

            //String sql="select a.issue_id,a.ISSUE_REPORTED_DATE,a.ISSUE_PRIORITY,a.ISSUE_SOLUTION,b.MAJOR_SYSTEM_ID,b.major_system_desc,c.MINOR_SYSTEM_ID,c.minor_system_desc,a.ISSUE_TITLE,a.ISSUE_DESC,a.ISSUE_STATUS,a.REPORTED_BY_USER_ID from HLP_ISSUE_REQUESTS a,sec_mst_major_systems b,sec_mst_minor_systems c where a.major_system_id=b.major_system_id and a.minor_system_id=c.minor_system_id order by a.issue_id";
            //ps=connection.prepareStatement(sql);
            res = ps.executeQuery();

            int i = 0;
            while (res.next()) {
                java.sql.Date DateOfFormation =
                    res.getDate("ISSUE_REPORTED_DATE");
                String DateToBeDisplayed = "";
                if (DateOfFormation == null) {
                    DateToBeDisplayed = "Not Specified";
                } else {
                    try {
                        java.text.SimpleDateFormat sdf =
                            new java.text.SimpleDateFormat("dd/MM/yyyy");
                        DateToBeDisplayed = sdf.format(DateOfFormation);
                    } catch (Exception e) {
                        System.out.println("error while formatting date : " +
                                           e);
                        DateToBeDisplayed = "Not Specified";
                    }
                }
                //response.setContentType("text/xml");
                xml =
 xml + "<options><issueid>" + res.getString("issue_id") + "</issueid><majorsystemdesc>" +
   res.getString("major_system_desc").trim() + "</majorsystemdesc>" +
   		"<MINOR_SYSTEM_DESC>" +
   res.getString("MINOR_SYSTEM_DESC").trim() + "</MINOR_SYSTEM_DESC><subject>" +
   res.getString("Issue_title").trim() + "</subject><reportdate>" +
   DateToBeDisplayed + "</reportdate><status>" +
   res.getString("issue_status") + "</status><solution>" +
   res.getString("issue_solution") + "</solution><desc>" +
   res.getString("issue_desc") + "</desc><empid>" + res.getInt("employee_id") +
   "</empid><empname>" + res.getString("employee_name") +
   "</empname><officename>" + res.getString("office_name") +
   "</officename></options>";
                i++;
            }
            res.close();
            ps.close();
            if (i > 0) {
                xml = xml + "<flag>success</flag>";
            } else {
                xml = xml + "<flag>failure</flag>";
            }


        } catch (Exception e) {
            System.out.println("Exception in try :" + e);
        }
     }
        xml = xml + "</response>";
        out.write(xml);
        System.out.println("xml is:" + xml);
        System.out.println("lastline");
        out.close();
        try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
