package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RevokeAdditionalCharge extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection con = null;
        ResultSet resultset = null,rs=null,rss=null,rs2=null,rs3=null,rs4=null,rs5=null,rs6=null;
        PreparedStatement ps = null,pss=null,ps3=null,ps4=null,ps5=null,ps6=null;
        ResultSet resultset1 = null;
        PreparedStatement ps1 = null;
        ResultSet rsnew = null;
        PreparedStatement psnew = null;
        java.sql.CallableStatement cs = null;
        try {
        	 LoadDriver driver=new LoadDriver();
         	con=driver.getConnection();
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Exception in connection...").append(e).toString());
        }
        int cnt = 0;
        String command = request.getParameter("Command");
        
        int strcode=0,slno=0;
        
        System.out.println("emp id is  === "+strcode);
        System.out.println("emp id is  === "+command);
        //String proNo = request.getParameter("prono");
        int offid = 0;
       // System.out.println((new StringBuilder()).append("proceeding no is").append(proNo).toString());
      //  System.out.println((new StringBuilder()).append("Office id is").append(offid).toString());
        String xml = "<response>";
       
        
        if(command.equalsIgnoreCase("load_grid"))
        {
        	System.out.println("load grid function");
        	try{
        		 xml+= "<command>load_grid</command>";
        		 String strDef1=null;

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
           out.println(xml);
        }
        
        
        if (command.equalsIgnoreCase("loadempview"))         
        {
        	System.out.println("empviewsssssss");
        	strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
        	slno=Integer.parseInt(request.getParameter("slno"));
            xml+= "<command>loadempview</command>";
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
                             offid = rs.getInt("OFFICE_ID");
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
                	String sqls="SELECT process_flow_status_id FROM HRM_EMP_ADDL_CHARGE WHERE employee_id=? and slno=?";
                	pss=con.prepareStatement(sqls);
                	pss.setInt(1,strcode);
                	pss.setInt(2,slno);
                	rss=pss.executeQuery();
                	int counts=0;
                	String pro_status_id="";
                	while(rss.next())
                	{
                		counts++;
                		pro_status_id=rss.getString("process_flow_status_id");
                		System.out.println("pro_status_id === "+pro_status_id);
                		
                			
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
                                 		  "  AND slno                     =? " +
                                 		   "  AND (ADDL_CHARGE_STATUS NOT IN 'CL' " +
                                 		   "  OR ADDL_CHARGE_STATUS       IS NULL) " +
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
                                            ps1.setInt(1, slno);
                                            rs2=ps1.executeQuery();
                                            int count=0;
                                            while(rs2.next())
                                            {
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
                                                count++;
                                                
                                              
                                            }
                                            xml = xml + "<countss>" + count + "</countss>";
                                 }
                                 catch(Exception e)
                                 {
                                   System.out.println("Exception e..."+e.getMessage());
                                 }
                			
                            
                           
                		
                	}
                	
                }catch(Exception e)
                {
                	
                }
                
                System.out.println("**********");
               
                	int designation_id = 0,service_id=0,office_id=0;
                	String sql1s="SELECT designation_id, " +
                			"  office_id " +
                			"FROM hrm_emp_addl_charge " +
                			"WHERE employee_id          =? and slno=? " +
                			"AND process_flow_status_id='FR' ";
                	ps4=con.prepareStatement(sql1s);
                	ps4.setInt(1,strcode);
                	ps4.setInt(2, slno);
                	rs4=ps4.executeQuery();
                	while(rs4.next())
                	{
                		designation_id=rs4.getInt("designation_id");
                		office_id=rs4.getInt("office_id");
                	}
                	System.out.println("design === "+designation_id);
                	System.out.println("office_id === "+office_id);
                	String sqqll="select service_group_id from hrm_mst_designations where designation_id=?";
                	ps5=con.prepareStatement(sqqll);
                	ps5.setInt(1, designation_id);
                	rs5=ps5.executeQuery();
                	
                	while(rs5.next())
                	{
                		service_id=rs5.getInt("service_group_id");
                	}
                	
                	System.out.println("service_id === "+service_id);
                	
                	String ssql="SELECT slno, " +
                			"  SERVICE_GROUP_ID, " +
                			"  SERVICE_GROUP_NAME, " +
                			"  a.DESIGNATION_ID, " +
                			"  DESIGNATION, " +
                			"  OFFICE_GRADE, " +
                			"  EMPLOYEE_ID, " +
                			"  OFFICE_ID, " +
                			"  d.OFFICE_NAME, " +
                			"  OFFICE_ADDRESS1, " +
                			"  OFFICE_ADDRESS2, " +
                			"  CITY_TOWN_NAME, " +
                			"  TO_CHAR(DATE_EFFECTIVE_FROM,'dd/mm/yyyy') AS DATE_EFFECTIVE_FROM, " +
                			"  DATE_EFFECTIVE_FROM_SESSION, " +
                			"  DATE_EFFECTIVE_TO_SESSION, " +
                			"  ADDL_CHARGE_TYPE, " +
                			"  a.REMARKS, " +
                			"  a.PROCESS_FLOW_STATUS_ID " +
                			"FROM ( (( " +
                			"  (SELECT * " +
                			"  FROM hrm_emp_addl_charge " +
                			"  WHERE employee_id           =? and slno=? " +
                			
                			"  )a " +
                			"LEFT OUTER JOIN " +
                			"  (SELECT * FROM hrm_mst_designations WHERE designation_id=? " +
                			"  )b " +
                			"ON a.DESIGNATION_ID=b.DESIGNATION_ID) " +
                			"LEFT OUTER JOIN " +
                			"  (SELECT * FROM hrm_mst_service_group WHERE service_group_id=? " +
                			"  )c " +
                			"ON b.SERVICE_GROUP_ID=c.SERVICE_GROUP_ID) " +
                			"LEFT OUTER JOIN " +
                			"  (SELECT * FROM com_mst_offices WHERE office_id=? " +
                			"  )d " +
                			"ON a.OFFICE_ID=d.OFFICE_ID)"
;
                	String pro_flow_id="";
                	ps3=con.prepareStatement(ssql);
                	ps3.setInt(1,strcode);
                	ps3.setInt(2, slno);
                	ps3.setInt(3, designation_id);
                	ps3.setInt(4, service_id);
                	ps3.setInt(5,office_id);
                	rs3=ps3.executeQuery();
                	while(rs3.next())
                	{
                		pro_flow_id=rs3.getString("PROCESS_FLOW_STATUS_ID");
                		if(!pro_flow_id.equalsIgnoreCase("FR"))
                		{
                			xml = xml + "<Freezed>Not Freezed</Freezed>";
                		
                		}
                		else
                		{
                			xml = xml + "<SLNOSS>" + rs3.getInt("SLNO") + "</SLNOSS>";
                    		
                    		xml = xml + "<SERVICE_GROUP_IDSS>" + rs3.getInt("SERVICE_GROUP_ID") + "</SERVICE_GROUP_IDSS>";
                    		xml = xml + "<SERVICE_GROUP_NAMESS>" + rs3.getString("SERVICE_GROUP_NAME") + "</SERVICE_GROUP_NAMESS>";
                    		xml = xml + "<DESIGNATION_IDSS>" + rs3.getInt("DESIGNATION_ID") + "</DESIGNATION_IDSS>";
                    		xml = xml + "<DESIGNATIONSS>" + rs3.getString("DESIGNATION") + "</DESIGNATIONSS>";
                    		xml = xml + "<OFFICE_GRADESS>" + rs3.getString("OFFICE_GRADE") + "</OFFICE_GRADESS>";
                    		xml = xml + "<EMPLOYEE_IDSS>" + rs3.getInt("EMPLOYEE_ID") + "</EMPLOYEE_IDSS>";
                    		xml = xml + "<OFFICE_IDSS>" + rs3.getInt("OFFICE_ID") + "</OFFICE_IDSS>";
                    		
                    		xml = xml + "<OFFICE_NAMESS>" + rs3.getString("OFFICE_NAME") + "</OFFICE_NAMESS>";
                    		xml = xml + "<OFFICE_ADDRESS1SS>" + rs3.getString("OFFICE_ADDRESS1") + "</OFFICE_ADDRESS1SS>";
                    		xml = xml + "<OFFICE_ADDRESS2SS>" + rs3.getString("OFFICE_ADDRESS2") + "</OFFICE_ADDRESS2SS>";
                    		xml = xml + "<CITY_TOWN_NAMESS>" + rs3.getString("CITY_TOWN_NAME") + "</CITY_TOWN_NAMESS>";
                    		xml = xml + "<DATE_EFFECTIVE_FROMSS>" + rs3.getString("DATE_EFFECTIVE_FROM") + "</DATE_EFFECTIVE_FROMSS>";
                    		xml = xml + "<DATE_EFFECTIVE_FROM_SESSIONSS>" + rs3.getString("DATE_EFFECTIVE_FROM_SESSION") + "</DATE_EFFECTIVE_FROM_SESSIONSS>";
                    		xml = xml + "<DATE_EFFECTIVE_TO_SESSIONSS>" + rs3.getString("DATE_EFFECTIVE_TO_SESSION") + "</DATE_EFFECTIVE_TO_SESSIONSS>";
                    		xml = xml + "<ADDL_CHARGE_TYPESS>" + rs3.getString("ADDL_CHARGE_TYPE") + "</ADDL_CHARGE_TYPESS>";
                    		xml = xml + "<REMARKSSS>" + rs3.getString("REMARKS") + "</REMARKSSS>";
                    		xml = xml + "<PROCESS_FLOW_SID>" + rs3.getString("PROCESS_FLOW_STATUS_ID") + "</PROCESS_FLOW_SID>";
                    		xml = xml + "<Freezed>Freezed</Freezed>";
                		}
                	}
                	
                	
                	
               
                

            } catch (Exception e) {
                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            
            System.out.println("XML ==== "+xml);
           out.println(xml);
        }
        
        
        
        if (command.equalsIgnoreCase("loadEmployee")) {
        	strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
            xml =
 (new StringBuilder()).append(xml).append("<command>loadEmployee</command>").toString();
            try {
                System.out.println("before query");
                ps =
  con.prepareStatement("select distinct(employee_id),empname,designation,relieval_reason_desc, Proceeding_no,proceeding_date,subject,sub_reference,copyto,addl_para_one,addl_para_2,presiding_officer, presiding_officer_desig,PROCESS_FLOW_STATUS_ID from ( select employee_id,office_id,Relieval_Reason_id,PROCESS_FLOW_STATUS_ID,  Proceeding_no from hrm_emp_relieval_details where proceeding_no=? and office_id=? and process_flow_status_id!='FR' )a left outer join ( select Proceeding_no as prono,office_id as offid,Proceeding_Date,subject,sub_reference,copyto,addl_para_one,addl_para_2, presiding_officer,presiding_officer_desig from hrm_emp_relieval_proceed_new where proceeding_no=? and office_id=? )b on a.Proceeding_no=b.prono  and a.office_id=b.offid left outer join ( select employee_id as empid1,(employee_prefix||'.'||employee_name||'.'||employee_initial) as empname from hrm_mst_employees )c on a.employee_id=c.empid1 left outer join  ( select relieval_reason_id as reasonid,relieval_reason_desc from hrm_mst_relieval_reasons )d on a.relieval_reason_id=d.reasonid left outer join ( select employee_id as empid2,designation_id from hrm_emp_current_posting  )e on a.employee_id=e.empid2 left outer join ( select designation_id as desigid,designation from hrm_mst_designations )f on e.designation_id=f.desigid");
              //  ps.setString(1, proNo);
                ps.setInt(2, offid);
            //    ps.setString(3, proNo);
                ps.setInt(4, offid);
                System.out.println("before executing query");
                resultset = ps.executeQuery();
                System.out.println("After executing query");
                while (resultset.next()) {
                    xml =
 (new StringBuilder()).append(xml).append("<pro_no>").append(resultset.getString("PROCEEDING_NO")).append("</pro_no><ref>").append(resultset.getString("SUB_REFERENCE")).append("</ref><sub>").append(resultset.getString("SUBJECT")).append("</sub><copyto>").append(resultset.getString("COPYTO")).append("</copyto>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<pr_off>").append(resultset.getString("PRESIDING_OFFICER")).append("</pr_off><pr_desig>").append(resultset.getString("PRESIDING_OFFICER_DESIG")).append("</pr_desig><addlparaone>").append(resultset.getString("ADDL_PARA_ONE")).append("</addlparaone><addlparatwo>").append(resultset.getString("ADDL_PARA_2")).append("</addlparatwo>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<pr_date>").append(resultset.getDate("proceeding_date")).append("</pr_date>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<details>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<empid>").append(resultset.getString("EMPLOYEE_ID")).append("</empid>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<empname>").append(resultset.getString("empname")).append("</empname>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<empdesig>").append(resultset.getString("designation")).append("</empdesig>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<emprelstatus>").append(resultset.getString("relieval_reason_desc")).append("</emprelstatus>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<processflow>").append(resultset.getString("PROCESS_FLOW_STATUS_ID")).append("</processflow>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("</details>").toString();
                    cnt++;
                }
                if (cnt != 0)
                    xml =
 (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                else
                    xml =
 (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            } catch (Exception e) {
                System.out.println((new StringBuilder()).append("Error in getting the employee details ").append(e).toString());
            }
            xml =
 (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println((new StringBuilder()).append("xml is :").append(xml).toString());
            out.println(xml);
        } else if (command.equalsIgnoreCase("save")) {
        	strcode=Integer.parseInt(request.getParameter("txtEmployeeid"));
            String sub = null;
            String ref = null;
            String addlParaOne = null;
            String addlParaTwo = null;
            String preno = null;
            String predateparam = null;
            String preoff = null;
            String predesig = null;
            String copyto = null;
            java.util.Date predate = null;
            preno = request.getParameter("txtPNo");
            predateparam = request.getParameter("txtPDat");
            preoff = request.getParameter("txtPO");
            predesig = request.getParameter("txtPODesig");
            copyto = request.getParameter("txtCopy");
            sub = request.getParameter("txtSubject");
            ref = request.getParameter("txtRef");
            addlParaOne = request.getParameter("txtPara1");
            addlParaTwo = request.getParameter("txtPara2");
            try {
                ps =
  con.prepareStatement("insert into hrm_emp_relieval_proceed_new(proceeding_no,proceeding_date,subject,sub_reference,addl_para_one,addl_para_2,copyto, presiding_officer,presiding_officer_desig) values(?,?,?,?,?,?,?,?,?)");
                ps.setString(1, preno);
                ps.setString(2, predateparam);
                ps.setString(2, sub);
                ps.setString(3, ref);
                ps.setString(4, addlParaOne);
                ps.setString(5, addlParaTwo);
                ps.setString(6, copyto);
                ps.setString(7, preoff);
                ps.setString(8, predesig);
                int x = ps.executeUpdate();
                System.out.println("Records are inserterd Successfully...");
            } catch (Exception e) {
                System.out.println((new StringBuilder()).append("Error in updating data ").append(e).toString());
            }
        }
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("Report Generation");
        Connection con = null;
        String Proc_Status = "";
        PreparedStatement psnew = null;
        ResultSet rsnew = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        PreparedStatement pst1 = null;
        ResultSet rst1 = null;
        int eid = 0;
        int txtRel_SLNO = 0;
        int txtOffId = 0;
        String txtEmployeeid = "";
        String predateparam = null;
        String sub = null;
        String ref = null;
        String addlParaOne = null;
        String addlParaTwo = null;
        String preno = null;
        String preoff = null;
        String predesig = null;
        String copyto = null;
        java.util.Date predate = null;
        try {
        	LoadDriver driver=new LoadDriver();
            con=driver.getConnection();

        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Exception in openeing connection :").append(e).toString());
        }
        String prono = request.getParameter("cmbProceed");
        int offid = Integer.parseInt(request.getParameter("txtOffId"));
        String empidsnew = request.getParameter("txtseloff");
        String empids = "";
        try {
            psnew =
con.prepareStatement("select employee_id,PROCESS_FLOW_STATUS_ID from HRM_EMP_RELIEVAL_DETAILS where proceeding_no=? and office_id=?");
            System.out.println((new StringBuilder()).append("select employee_id,PROCESS_FLOW_STATUS_ID from HRM_EMP_RELIEVAL_DETAILS where proceeding_no=").append(prono).append("and office_id=").append(offid).toString());
            psnew.setString(1, prono);
            psnew.setInt(2, offid);
            for (rsnew = psnew.executeQuery(); rsnew.next();
                 System.out.println(txtEmployeeid)) {
                Proc_Status = rsnew.getString("PROCESS_FLOW_STATUS_ID");
                txtEmployeeid =
                        (new StringBuilder()).append(txtEmployeeid).append(rsnew.getInt("Employee_id")).append(",").toString();
            }

            System.out.println((new StringBuilder()).append("empid is -----------").append(txtEmployeeid).toString());
            empids = txtEmployeeid.substring(0, txtEmployeeid.length() - 1);
            System.out.println((new StringBuilder()).append("empids are   :::::::").append(empids).toString());
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Error in retrieving process flow status id").append(e).toString());
        }
        int cnt = 0;
        boolean flag = false;
        preno = request.getParameter("cmbProceed");
        predateparam = request.getParameter("txtPDat");
        java.sql.Date dateto = null;
        System.out.println("before converting date");
        String dateString1 = predateparam;
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d1 = null;
        try {
            d1 = dateFormat1.parse(predateparam.trim());
        } catch (ParseException e) {
            d1 = null;
            System.out.println("error in parsing date");
        }
        dateFormat1.applyPattern("yyyy-MM-dd");
        dateString1 = dateFormat1.format(d1);
        dateto = java.sql.Date.valueOf(dateString1);
        System.out.println((new StringBuilder()).append("Date value is :").append(dateto).toString());
        preoff = request.getParameter("txtPO");
        predesig = request.getParameter("txtPODesig");
        copyto = request.getParameter("txtCopy");
        sub = request.getParameter("txtSubject");
        ref = request.getParameter("txtRef");
        addlParaOne = request.getParameter("txtPara1");
        addlParaTwo = request.getParameter("txtPara2");
        try {
            pst1 =
con.prepareStatement("select * from hrm_emp_relieval_proceed_new where proceeding_no=? and office_id=?");
            pst1.setString(1, preno);
            pst1.setInt(2, offid);
            rst1 = pst1.executeQuery();
            if (rst1.next())
                flag = true;
            else
                flag = false;
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("throwing an err").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("flag value is         ::").append(flag).toString());
        if (flag)
            try {
                pst =
 con.prepareStatement("update hrm_emp_relieval_proceed_new set proceeding_date=?, presiding_officer=?,presiding_officer_desig=?,subject=?,sub_reference=?,copyto=?,addl_para_one=?, addl_para_2=? where proceeding_no=? and office_id=?");
                pst.setDate(1, dateto);
                pst.setString(2, preoff);
                pst.setString(3, predesig);
                pst.setString(4, sub);
                pst.setString(5, ref);
                pst.setString(6, copyto);
                pst.setString(7, addlParaOne);
                pst.setString(8, addlParaTwo);
                pst.setString(9, preno);
                pst.setInt(10, offid);
                int y = pst.executeUpdate();
            } catch (Exception e) {
                System.out.println((new StringBuilder()).append("Error while updating ").append(e).toString());
            }
        else
            try {
                pst =
 con.prepareStatement("insert into hrm_emp_relieval_proceed_new(proceeding_date, presiding_officer,presiding_officer_desig,subject,sub_reference,copyto,addl_para_one, addl_para_2,proceeding_no,office_id) values(?,?,?,?,?,?,?,?,?,?)");
                pst.setDate(1, dateto);
                pst.setString(2, preoff);
                pst.setString(3, predesig);
                pst.setString(4, sub);
                pst.setString(5, ref);
                pst.setString(6, copyto);
                pst.setString(7, addlParaOne);
                pst.setString(8, addlParaTwo);
                pst.setString(9, preno);
                pst.setInt(10, offid);
                int y = pst.executeUpdate();
            } catch (Exception e) {
                System.out.println((new StringBuilder()).append("Error while inserting ").append(e).toString());
            }
        File reportFile = null;
        try {
            System.out.println("calling servlet");
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Relieval_Proceeding_Report_New.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("empid", empidsnew);
            map.put("subject", sub);
            map.put("refer", ref);
            map.put("paraone", addlParaOne);
            map.put("paratwo", addlParaTwo);
            map.put("preno", preno);
            map.put("predate", predateparam);
            map.put("predesig", predesig);
            map.put("preoff", preoff);
            map.put("copyto", copyto);
            net.sf.jasperreports.engine.JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, con);
            String rtype = request.getParameter("RType");
            rtype = "PDF";
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                      Boolean.valueOf(false));
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                exporter.exportReport();
                out.flush();
                out.close();
            } else if (rtype.equalsIgnoreCase("PDF")) {
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.pdf\"");
                OutputStream outs = response.getOutputStream();
                outs.write(buf, 0, buf.length);
                outs.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);
                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                         xlsReport);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                         Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                         Boolean.TRUE);
                exporterXLS.exportReport();
                byte bytes[] = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
            } else if (rtype.equalsIgnoreCase("TXT")) {
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.txt\"");
                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      txtReport);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                      new Integer(200));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                      new Integer(50));
                exporter.exportReport();
                byte bytes[] = txtReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception ex) {
            String connectMsg =
                (new StringBuilder()).append("Could not create the report ").append(ex.getMessage()).append(" ").append(ex.getLocalizedMessage()).toString();
            System.out.println(connectMsg);
        }
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                (new StringBuilder()).append("org/Library/jsps/Messenger.jsp?message=").append(msg).append("&button=").append(bType).toString();
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

}
