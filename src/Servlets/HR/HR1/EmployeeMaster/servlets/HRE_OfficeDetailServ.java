package Servlets.HR.HR1.EmployeeMaster.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class HRE_OfficeDetailServ extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
PreparedStatement pss=null;
ResultSet rss=null;

        try {
        	LoadDriver driver=new LoadDriver();
        	connection=driver.getConnection();

        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
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
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        //String strCriteria = "";
        int Office_Id = 0,empid=0;
        Office_Id = Integer.parseInt(request.getParameter("OfficeId"));
        int found = 0;
        String xml = "";

        try {
xml+="<response>";

            String deptid = "";
            deptid = request.getParameter("txtDept_Id");
            System.out.println("My dept id::" +
                               request.getParameter("txtDept_Id"));
            if (deptid.equals("TWAD")) {
                System.out.println("TWAD---->");
                String sql =
                    "select a.office_status_id,a.office_id,a.office_name,a.Office_address1,a.office_address2,a.CITY_TOWN_NAME,a.DISTRICT_CODE,a.OFFICE_PHONE_NO,a.ADDL_PHONE_NOS,a.OFFICE_EMAIL_ID,a.ADDL_EMAIL_IDS,a.OFFICE_FAX_NO,a.ADDL_FAX_NOS,a.OFFICE_STD_CODE,d.DISTRICT_NAME,a.office_level_id from com_mst_offices a left outer join com_mst_districts d on d.DISTRICT_CODE=a.DISTRICT_CODE  where a.Office_Id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Office_Id);
                connection.clearWarnings();
                try {
                    ResultSet results = statement.executeQuery();
                    try {
                        xml =xml+ "<flag>success</flag>";
                        found = 0;
                        while (results.next()) {
                            //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                            xml =
 xml + "<options><id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><District>" +
   results.getInt("DISTRICT_CODE") + "</District><district_name>" +
   results.getString("DISTRICT_NAME") + "</district_name><PhoneNo>" +
   results.getString("OFFICE_PHONE_NO") + "</PhoneNo><AddPhone>" +
   results.getString("ADDL_PHONE_NOS") + "</AddPhone><EmailId>" +
   results.getString("OFFICE_EMAIL_ID") + "</EmailId><AddEmail>" +
   results.getString("ADDL_EMAIL_IDS") + "</AddEmail><FaxNo>" +
   results.getString("OFFICE_FAX_NO") + "</FaxNo><AddFaxNo>" +
   results.getString("ADDL_FAX_NOS") + "</AddFaxNo><StdCode>" +
   results.getString("OFFICE_STD_CODE") + "</StdCode><OfficeStatusId>" +
   results.getString("office_status_id") + "</OfficeStatusId></options>";


                            if (results.getString("office_level_id") != null &&
                                results.getString("office_level_id").equalsIgnoreCase("HO")) {
                                xml = xml + "<WING>yes</WING>";

                                String sql1 =
                                    "select OFFICE_WING_SINO,WING_NAME from COM_OFFICE_WINGS  where Office_Id=?";
                                PreparedStatement statement1 =
                                    connection.prepareStatement(sql1);
                                statement1.setInt(1, Office_Id);
                                ResultSet results1 = statement1.executeQuery();
                                while (results1.next()) {
                                    xml =
 xml + "<wingoptions><wingid>" + results1.getInt("OFFICE_WING_SINO") +
   "</wingid><wingname>" + results1.getString("WING_NAME") +
   "</wingname></wingoptions>";

                                }

                            } else {
                                xml = xml + "<WING>no</WING>";
                            }


                            try {
                                int desigid =
                                    Integer.parseInt(request.getParameter("disigid"));
                                System.out.println("desigantion id issssssss:" +
                                                   desigid);
                                boolean flag = true;
                                PreparedStatement ps =
                                    connection.prepareStatement("SELECT * FROM HRM_EMP_CURRENT_POSTING WHERE office_id=? and designation_id=? ");
                                //ps.setInt(1,empid);
                                ps.setInt(1, Office_Id);
                                ps.setInt(2, desigid);
                                ResultSet rs = ps.executeQuery();
                                if (rs.next()) {
                                    xml = xml + "<xists>yes</xists>";
                                    //flag=false;
                                } else {
                                    xml = xml + "<xists>no</xists>";
                                }

                            } catch (Exception e) {
                                System.out.println("catch........" + e);
                                //xml=xml+"<flag></flag>";
                            }


                            found++;
                        }
                        if (found == 0)
                            xml=xml+ "<flag>failure</flag>";

                       
                    } catch (SQLException e) {
                        System.out.println(e);
                    } finally {
                        results.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in statement:" + e);
                } finally {
                    statement.close();
                }
            }

            else {
                System.out.println("else---->");
                System.out.println("Office_Id---->"+Office_Id);
                System.out.println("deptid---->"+deptid);
                String sql =
                    "select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=? and OTHER_DEPT_ID=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Office_Id);
                statement.setString(2, deptid);
                connection.clearWarnings();
                try {
                    ResultSet results = statement.executeQuery();
                    try {
                        xml =xml+ "<flag>success</flag>";
                        found = 0;
                        while (results.next()) {
                            //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                            xml =
 xml + "<options><id>" + results.getInt("OTHER_DEPT_OFFICE_ID") +
   "</id><name>" + results.getString("OTHER_DEPT_OFFICE_NAME") +
   "</name><officeAddress1>" + results.getString("ADDRESS1") +
   "</officeAddress1><officeAddress2>" + results.getString("ADDRESS2") +
   "</officeAddress2><officeAddress3>" + results.getString("CITY_TOWN") +
   "</officeAddress3>" +
   "<OTHER_DEPT_ID>" + results.getString("OTHER_DEPT_ID") +
   "</OTHER_DEPT_ID></options>";
                            found++;
                        }
                        if (found == 0)
                            xml =xml+ "<flag>failure</flag>";

                       
                    } catch (SQLException e) {
                        System.out.println(e);
                    } finally {
                        results.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in statement:" + e);
                } finally {
                    statement.close();
                }
            }
            
            
            try{
            	int counstss=0;
            	 empid = Integer.parseInt(request.getParameter("empid"));
            	String sqlss="SELECT employee_id, " +
            			"  office_id, " +
            			"  TO_CHAR(date_effective_from,'dd/mm/yyyy') AS date_effective_from, " +
            			"  date_effective_from_session, " +
            			"  addl_charge_status " +
            			"FROM hrm_emp_addl_charge " +
            			"WHERE employee_id=? " +
            			"AND office_id    =?" ;
            	pss=connection.prepareStatement(sqlss);
            	pss.setInt(1,empid);
            	pss.setInt(2, Office_Id);
            	rss=pss.executeQuery();
            	while(rss.next())
            	{
            		counstss++;
            		xml+="<employee_idss>"+rss.getInt("employee_id")+"</employee_idss>";
            		xml+="<office_idss>"+rss.getInt("office_id")+"</office_idss>";
            		xml+="<date_effective_fromss>"+rss.getString("date_effective_from")+"</date_effective_fromss>";
            		xml+="<date_effective_from_sessionss>"+rss.getString("date_effective_from_session")+"</date_effective_from_sessionss>";
            		xml+="<addl_charge_statusss>"+rss.getString("addl_charge_status")+"</addl_charge_statusss>";
            	}
            	xml+="<counstss>"+counstss+"</counstss>";
            	
            	if(counstss==0)
            		xml+="<flagss>failures</flagss>";
            	else
            		xml+="<flagss>successs</flagss>";
            	
            }catch(Exception e)
            {
            	System.out.println("Exceptin e ==== "+e.getMessage());
            	xml+="<flagss>successs</flagss>";
            	
            }
            

            xml = xml + "</response>";
        } catch (SQLException e) {
            System.out.println("Exception in connection:" + e);
        }


        System.out.println("Xml is : " + xml);
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");


        out.println(xml);
        out.close();
        try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
