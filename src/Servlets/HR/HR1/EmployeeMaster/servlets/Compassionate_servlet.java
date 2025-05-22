package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
public class Compassionate_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     public Compassionate_servlet() {
        super();
      
    }
     Connection connection = null;
     PreparedStatement ps=null,ps1=null;
     ResultSet rs=null;
     HttpSession session;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			LoadDriver driver=new LoadDriver();
        	connection=driver.getConnection();
        	
		}
		catch(Exception e)
		{
			System.out.println("Exception in connection..." + e);
		}
		 try {
	             session = request.getSession(false);
	            if (session == null) {
	                System.out.println(request.getContextPath() + "/index.jsp");
	                response.sendRedirect(request.getContextPath() + "/index.jsp");

	            }
	            System.out.println(session);

	        } catch (Exception e) {
	            System.out.println("Redirect Error :" + e);
	        }
	        PrintWriter out = response.getWriter();
	        response.setContentType("text/xml");
	        response.setHeader("Cache-Control", "no-cache");
	        String xml = "";
	        String strCommand = "";
	        String newss="";
	        String exsit="";
	        int EmpId=0,Pincode=0,Last_Office_id=0,Emp_Rel=0,Appl_Com=0,post_code=0,reason_id,appl_no=0;
	        String Appl_Name,Appl_Ini,Appl_addr1,Appl_addr2,Appl_addr3,Emp_Ini,Desig_at_dth,Date_of_Rec,Appl_DOB,Edu_level,File_no,incom,Status_Rsn,remarks,Emp_Name,Date_of_dth,Last_Office_Name,Status_Id,checklist=null,reason[];
	        String updatedby = (String)session.getAttribute("UserId");
	        System.out.println("user id..." + updatedby);
            
	        long l = System.currentTimeMillis();
	        java.sql.Timestamp ts = new java.sql.Timestamp(l);
	        strCommand=request.getParameter("Command");
	        exsit=request.getParameter("exsit");
	        newss=request.getParameter("newss");
	        System.out.println("strCommand"+strCommand);
	        System.out.println("exsit------->"+exsit);
	        try
	        {
	        	
			        if(strCommand.equalsIgnoreCase("dispEmp"))
			        {
			        	
			        	xml="<response><command>dispEmp</command>";
			        	EmpId=Integer.parseInt(request.getParameter("Emp_id"));
					        	String sql="Select Employee_Id, Employee_Name From Hrm_Mst_Employees WHERE Employee_Id='"+EmpId+"'";
			        	ps=connection.prepareStatement(sql);
			        	
			        	rs=ps.executeQuery();
			        	if(rs.next())
			        	{
			        		sql="Select Employee_Id From hrm_emp_current_posting WHERE Employee_Id='"+EmpId+"' and  employee_status_id  in('DTH')";
			        		ps=connection.prepareStatement(sql);
			        		rs=ps.executeQuery();
			        		
			        		if(rs.next())
			        		{
			        		System.out.println("inside if side===>");
			        		ps=connection.prepareStatement("SELECT Employee_Id, " 
														+"  Employee_Name, " 
														+"  EMPLOYEE_INITIAL, " 
														+"  to_char(DATE_OF_BIRTH,'dd/mm/yyyy') as DATE_OF_BIRTH, " 
														+"  Designation_Id, " 
														+"  Office_Id , " 
														+"  Designation,date_of_dth " 
														+" FROM " 
														+"  (SELECT Employee_Id, " 
														+"    Employee_Name, " 
														+"    EMPLOYEE_INITIAL, " 
														+"    DATE_OF_BIRTH " 
														+"  FROM Hrm_Mst_Employees " 
														+"  WHERE Is_Consolidate='N' " 
														+"  AND employee_id     =? " 
														+"  )A " 
														+"LEFT OUTER JOIN " 
														+"  (SELECT Employee_Id Empid, " 
														+"    Designation_Id, " 
														+"    Office_Id ,to_char(DATE_EFFECTIVE_FROM,'dd/mm/yyyy') as date_of_dth" 
														+"  FROM Hrm_Emp_Current_Posting " 
														+"  WHERE Employee_Status_Id='DTH' " 
														+"  )B " 
														+"ON B.Empid=A.Employee_Id " 
														+"LEFT OUTER JOIN " 
														+"  (SELECT Designation_Id Des_Id,Designation FROM Hrm_Mst_Designations " 
														+"  )C " 
														+"ON des_id=b.designation_id");
				              	ps.setInt(1, EmpId);
				              	rs=ps.executeQuery();
				              	if(rs.next())
				              	{
				              		
				              		xml=xml+"<flag>success</flag>";
				              		xml=xml+"<name>"+rs.getString("Employee_Name")+"</name>";
				              		xml=xml+"<initial>"+rs.getString("EMPLOYEE_INITIAL")+"</initial>";
				              		xml=xml+"<dth>"+rs.getString("date_of_dth")+"</dth>";
				              		xml=xml+"<des>"+rs.getString("Designation")+"</des>";
				              				              		
				              	}
				              	else
				              		System.out.println("inside first  selse ide===>");
				              		xml=xml+"<flag>nostatus</flag>";
			        		}
			        		else 
			        		{
			        			System.out.println("inside first  second ide===>");
			        			xml=xml+"<flag>status</flag>";
			        		}
			        	}
			        	else
			        		xml=xml+"<flag>notexist</flag>";
			         }
			        else if (strCommand.equalsIgnoreCase("Add"))
			        {
			        	xml="<response><command>Add</command>";
			        	try
                        {
			        	EmpId=Integer.parseInt(request.getParameter("Emp_id"));
			        	System.out.println("EmpId"+EmpId);
			        	post_code=Integer.parseInt(request.getParameter("post_code"));
			        	System.out.println("post_code"+post_code);
			        	Pincode=Integer.parseInt(request.getParameter("Pincode"));
			        	System.out.println("Pincode"+Pincode);
			        	Last_Office_id=Integer.parseInt(request.getParameter("Last_Office_id"));
			        	System.out.println("Last_Office_id"+Last_Office_id);
			        	Emp_Rel=Integer.parseInt(request.getParameter("Emp_Rel"));
			        	System.out.println("Emp_Rel"+Emp_Rel);
			        	Appl_Com=Integer.parseInt(request.getParameter("Appl_Com"));
			        	System.out.println("Appl_Com"+Appl_Com);
			        	Appl_Name=request.getParameter("Appl_Name");
			        	System.out.println("Appl_Name"+Appl_Name);
			        	Appl_addr2=request.getParameter("Appl_addr2");
			        	System.out.println("Appl_addr2"+Appl_addr2);
			        	Appl_Ini=request.getParameter("Appl_Ini");
			        	Emp_Ini=request.getParameter("Emp_Ini");
			        	System.out.println("Appl_Ini"+Appl_Ini);
			        	Appl_addr1=request.getParameter("Appl_addr1");
			        	System.out.println("Appl_addr1"+Appl_addr1);
			        	Appl_addr3=request.getParameter("Appl_addr3");
			        	System.out.println("Appl_addr3"+Appl_addr3);
			        	Desig_at_dth=request.getParameter("Desig_at_dth");
			        	System.out.println("Desig_at_dth"+Desig_at_dth);
			        	Date_of_Rec=request.getParameter("Date_of_Rec");
			        	System.out.println("Date_of_Rec"+Date_of_Rec);
			        	Appl_DOB=request.getParameter("Appl_DOB");
			        	System.out.println("Appl_DOB"+Appl_DOB);
			        	Edu_level=request.getParameter("Edu_level");
			        	System.out.println("Edu_level"+Edu_level);
			        	File_no=request.getParameter("File_no");
			        	System.out.println("File_no"+File_no);
			        	incom=request.getParameter("incom");
			        	System.out.println("incom"+incom);
			        	//Status_Rsn=request.getParameter("Status_Rsn");
			        	//System.out.println("Status_Rsn"+Status_Rsn);
			        	remarks=request.getParameter("remarks");
			        	System.out.println("remarks"+remarks);
			        	Emp_Name=request.getParameter("Emp_Name");
			        	System.out.println("Emp_Name"+Emp_Name);
			        	Date_of_dth=request.getParameter("Date_of_dth");
			        	System.out.println("Date_of_dth"+Date_of_dth);
			        	Last_Office_Name=request.getParameter("Last_Office_Name");
			        	System.out.println("Last_Office_Name"+Last_Office_Name);
			        	Status_Id=request.getParameter("Status_Id");
			        	System.out.println("Status_Id"+Status_Id);
			        	
			        	
			        	
			        	ps=connection.prepareStatement("INSERT " +
						"INTO HRM_COMPASSION_REQ_TRN " +
						"  ( " +
						
						"    APPLICANT_NAME, " +
						"    APPLICANT_ADDRESS1, " +
						"    APPLICANT_ADDRESS2, " +
						"    APPLICANT_ADDRESS3, " +
						"    APPLICANT_PINCODE, " +
						"    EMPLOYEE_ID, " +
						"    EMPLOYEE_NAME, " +
						"    DESIGNATION_AT_DTH, " +
						"    DATE_OF_RECEIPT, " +
						"    DOB_OF_APPLICANT, " +
						"    COMMUNITY_CODE, " +
						"    EDU_LEVEL, " +
						"    ELIGIBLE_POST_CODE, " +
						"    FILE_REF_NO, " +
						"    REMARKS, " +
						"    UPDATED_BY, " +
						"    UPDATED_DATE, " +
						"    PROCESS_FLOW_ID, " +
						"    LAST_OFFICE_ID, " +
						"    EMP_RELATION_ID, " +
						"    DATE_OF_DEATH, " +
						"    LAST_OFFICE_NAME, " +
						"    IS_INCOMPLETE, " +
						"    APPL_STATUS_ID, " +
						
						"    APPLICANT_INITIAL, " +
						"    EMPLOYEE_INITIAL " +
						"  ) " +
						"  VALUES " +
						"  ( " +
						
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"  to_date( ?,'dd-mm-yy') ," +
						"   to_date( ?,'dd-mm-yy'), " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    to_date( ?,'dd-mm-yy'), " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    ?,? " +
						
						
						"  )");

                        ps.setString(1,Appl_Name );
                        ps.setString(2, Appl_addr1);
                        ps.setString(3, Appl_addr2);
                        ps.setString(4, Appl_addr3); 
                        ps.setInt(5, Pincode);
                        ps.setInt(6, EmpId);
                        ps.setString(7, Emp_Name);
                        ps.setString(8, Desig_at_dth); 
                        ps.setString(9, Date_of_Rec);
                        ps.setString(10, Appl_DOB); 
                        ps.setInt(11, Appl_Com);
                        ps.setString(12, Edu_level);
                        ps.setInt(13, post_code); 
                        ps.setString(14, File_no);
                        ps.setString(15,remarks);
                        ps.setString(16,updatedby );
                        ps.setTimestamp(17,ts); 
                        ps.setString(18, "CR");
                        ps.setInt(19, Last_Office_id); 
                        ps.setInt(20, Emp_Rel);
                        ps.setString(21, Date_of_dth); 
                        ps.setString(22, Last_Office_Name);
                        ps.setString(23, incom);
                        ps.setString(24, Status_Id);
                       
                        ps.setString(25, Appl_Ini);
                        ps.setString(26, Emp_Ini);
                        ps.executeUpdate();
                        
                        checklist=request.getParameter("checklist");
                        if(checklist.length()>0)
                        {
                        if(checklist.equals(null)||checklist.equals("null"))
                        	System.out.println("checklist empty");
                        else
                        {
                        ps=	connection.prepareStatement("select max(APPLICATION_ID) from HRM_COMPASSION_REQ_TRN ");
                        rs=ps.executeQuery();
                        if(rs.next())
                        {
                        	appl_no=rs.getInt(1);
                        }
                      //  appl_no=1;
                        checklist=checklist.substring(0, checklist.length()-1);
                        reason=checklist.split(",");
                        System.out.println(reason.length);
                        for(int i=0;i<reason.length;i++)
                        {
                        ps=connection.prepareStatement("INSERT " +
						"INTO HRM_COMPASS_APPL_CHECKLIST " +
						"  ( " +
						"    APPLICATION_ID, " +
						"    REASON_ID, " +
						"    PROCESS_FLOW_ID, " +
						"    UPDATED_DATE, " +
						"    UPDATED_BY " +
						"  ) " +
						"  VALUES " +
						"  ( " +
						"    ?, " +
						"    ?, " +
						"    ?, " +
						"    clock_timestamp(), " +
						"    ? " +
						"  )");
                        ps.setInt(1,appl_no );
                        ps.setInt(2, Integer.parseInt(reason[i]));
                        ps.setString(3, "CR");
                       
                        ps.setString(4,updatedby );
                        ps.executeUpdate();
                        }
                        }
                        }
                        xml=xml+"<flag>success</flag>";
                        }catch(Exception e)
                        {
                        	System.out.println("exception "+e);
                        }
			        }   
			        else if(strCommand.equalsIgnoreCase("seniority"))
			        {
			        	System.out.println("inside seniority");
			        	xml="<response><command>seniority</command>";
			        	//new order
			        	if(newss.equalsIgnoreCase("New"))
			        	{
			        		System.out.println("inside nw");
			        	ps=connection.prepareStatement("select APPLICATION_ID, DATE_OF_DEATH  from HRM_COMPASSION_REQ_TRN  where APPL_STATUS_ID  in('AV','US') and DATE_OF_DEATH>(SELECT to_date(APPOINTED_DATE,'dd-mm-yyy') FROM HRM_COMPASS_APPOINTED_DATE) order by DATE_OF_DEATH");
			        	rs=ps.executeQuery();
			        	int i=0;
			        	while(rs.next())
			        	{
			        		i=i+1;
			        		ps=connection.prepareStatement("update HRM_COMPASSION_REQ_TRN set SENIORITY_NO=?  where APPLICATION_ID=?");
			        		ps.setInt(1,i);
			        		ps.setInt(2, rs.getInt("APPLICATION_ID"));
			        		ps.executeUpdate();
			        	}
			        	}
			        	else if(exsit.equalsIgnoreCase("Exsisting"))
			        		
			        	{
			        		System.out.println("inside exsisting");
			        	// continue with old seniority
			        	int max_seni=0;
			        	ps=connection.prepareStatement("select nvl(max(SENIORITY_NO),0) as mx_seni  from HRM_COMPASSION_REQ_TRN");
			        	rs=ps.executeQuery();
			        	if(rs.next())
			        		max_seni=rs.getInt("mx_seni");
			        	System.out.println("inside  --->"+max_seni);  
			        	// max_seni=max_seni+1;
			        	    
			        	    ps1=connection.prepareStatement("select APPLICATION_ID, DATE_OF_DEATH  from HRM_COMPASSION_REQ_TRN  where APPL_STATUS_ID  in('AV','US') and DATE_OF_DEATH>(SELECT to_date(APPOINTED_DATE,'dd-mm-yyy') FROM HRM_COMPASS_APPOINTED_DATE) and seniority_no is null order by DATE_OF_DEATH");
				        	rs=ps1.executeQuery();
				        	while(rs.next())
				        	{
				        		System.out.println("inside while loop");
				        		max_seni=max_seni+1;
				        		ps=connection.prepareStatement("update HRM_COMPASSION_REQ_TRN set SENIORITY_NO=?  where APPLICATION_ID=?");
				        		ps.setInt(1,max_seni);
				        		ps.setInt(2, rs.getInt("APPLICATION_ID"));
				        		ps.executeUpdate();
				        	}
			        	}
			        	 xml=xml+"<flag>success</flag>";
			        }
			        
			        else if(strCommand.equalsIgnoreCase("load"))
			        {
			        	int i=0;
			        	xml="<response><command>load</command>";
			        	appl_no=Integer.parseInt(request.getParameter("Appl_id"));
			        	String sql="SELECT APPLICATION_ID, " +
			        			"  APPLICANT_NAME, " +
			        			"  APPLICANT_ADDRESS1, " +
			        			"  APPLICANT_ADDRESS2, " +
			        			"  APPLICANT_ADDRESS3, " +
			        			"  APPLICANT_PINCODE, " +
			        			"  EMPLOYEE_ID, " +
			        			"  EMPLOYEE_NAME, " +
			        			"  Designation_At_Dth, " +
			        			"  Date_Of_Receipt, " +
			        			"  DOB_OF_APPLICANT, " +
			        			"  mst.COMMUNITY_CODE AS COMMUNITY_CODE, " +
			        			"  EDU_LEVEL, " +
			        			"  ELIGIBLE_POST_CODE, " +
			        			"  FILE_REF_NO, " +
			        			"  REMARKS, " +
			        			"  LAST_OFFICE_ID, " +
			        			"  Emp_Relation_Id, " +
			        			"  DATE_OF_DEATH , " +
			        			"  LAST_OFFICE_NAME, " +
			        			"  IS_INCOMPLETE, " +
			        			"  mst.APPL_STATUS_ID AS APPL_STATUS_ID, " +
			        			"  STATUS_REASON, " +
			        			"  SENIORITY_NO, " +
			        			"  EMPLOYEE_INITIAL, " +
			        			"  Applicant_Initial, " +
			        			"  COMMUNITY_NAME, " +
			        			"  APPL_STATUS_DESC, " +
			        			"  ELIGIBLE_DESIG_NAME, " +
			        			"  FAMILY_RELATIONSHIP_DESC " +
			        			"FROM " +
			        			"  (SELECT APPLICATION_ID, " +
			        			"    APPLICANT_NAME, " +
			        			"    APPLICANT_ADDRESS1, " +
			        			"    APPLICANT_ADDRESS2, " +
			        			"    APPLICANT_ADDRESS3, " +
			        			"    APPLICANT_PINCODE, " +
			        			"    EMPLOYEE_ID, " +
			        			"    EMPLOYEE_NAME, " +
			        			"    Designation_At_Dth, " +
			        			"    TO_CHAR(Date_Of_Receipt,'dd/mm/yyyy')  AS Date_Of_Receipt, " +
			        			"    TO_CHAR(DOB_OF_APPLICANT,'dd/mm/yyyy') AS DOB_OF_APPLICANT, " +
			        			"    COMMUNITY_CODE, " +
			        			"    EDU_LEVEL, " +
			        			"    ELIGIBLE_POST_CODE, " +
			        			"    FILE_REF_NO, " +
			        			"    REMARKS, " +
			        			"    LAST_OFFICE_ID, " +
			        			"    Emp_Relation_Id, " +
			        			"    TO_CHAR( DATE_OF_DEATH,'dd/mm/yyyy') AS DATE_OF_DEATH , " +
			        			"    LAST_OFFICE_NAME, " +
			        			"    IS_INCOMPLETE, " +
			        			"    APPL_STATUS_ID, " +
			        			"    STATUS_REASON, " +
			        			"    SENIORITY_NO, " +
			        			"    EMPLOYEE_INITIAL, " +
			        			"    Applicant_Initial " +
			        			"  FROM Hrm_Compassion_Req_Trn " +
			        			"  WHERE APPLICATION_ID='"+appl_no+"' " +
			        			"  )mst " +
			        			"LEFT OUTER JOIN " +
			        			"  (SELECT COMMUNITY_CODE,COMMUNITY_NAME FROM HRM_MST_COMMUNITY " +
			        			"  )sub " +
			        			"ON sub.COMMUNITY_CODE=mst.COMMUNITY_CODE " +
			        			"LEFT OUTER JOIN " +
			        			"  (SELECT APPL_STATUS_ID,APPL_STATUS_DESC FROM HRM_COMPASS_STATUS_MST " +
			        			"  )sub1 " +
			        			"ON sub1.APPL_STATUS_ID=mst.APPL_STATUS_ID " +
			        			"LEFT OUTER JOIN " +
			        			"  (SELECT ELIGIBLE_DESIG_ID,ELIGIBLE_DESIG_NAME FROM HRM_COMPASS_ELIGIBLE_DESIG " +
			        			"  )sub2 " +
			        			"ON sub2.ELIGIBLE_DESIG_ID=mst.ELIGIBLE_POST_CODE " +
			        			"LEFT OUTER JOIN " +
			        			"  (SELECT FAMILY_RELATIONSHIP_ID, " +
			        			"    FAMILY_RELATIONSHIP_DESC " +
			        			"  FROM HRM_MST_FAMILY_RELATIONS " +
			        			"  )sub3 " +
			        			"ON sub3.FAMILY_RELATIONSHIP_ID=mst.Emp_Relation_Id";
			        	ps=connection.prepareStatement(sql);
			        	//ps.setInt(1, appl_no);
			        	rs=ps.executeQuery();
			        	System.out.println("after query");
			        	if(rs.next())
			        	{
			        		
			        		xml=xml+"<APPLICANT_NAME>"+rs.getString("APPLICANT_NAME")+"</APPLICANT_NAME>";
			        		xml=xml+"<APPLICANT_ADDRESS1>"+rs.getString("APPLICANT_ADDRESS1")+"</APPLICANT_ADDRESS1>";
			        		xml=xml+"<APPLICANT_ADDRESS2>"+rs.getString("APPLICANT_ADDRESS2")+"</APPLICANT_ADDRESS2>";
			        		xml=xml+"<APPLICANT_ADDRESS3>"+rs.getString("APPLICANT_ADDRESS3")+"</APPLICANT_ADDRESS3>";
			        		System.out.println("inside if");
			        		xml=xml+"<APPLICANT_PINCODE>"+rs.getInt("APPLICANT_PINCODE")+"</APPLICANT_PINCODE>";
			        		xml=xml+"<EMPLOYEE_ID>"+rs.getInt("EMPLOYEE_ID")+"</EMPLOYEE_ID>";
			        		xml=xml+"<EMPLOYEE_NAME>"+rs.getString("EMPLOYEE_NAME")+"</EMPLOYEE_NAME>";
			        		xml=xml+"<Designation_At_Dth>"+rs.getString("Designation_At_Dth")+"</Designation_At_Dth>";
			        		xml=xml+"<Date_Of_Receipt>"+rs.getString("Date_Of_Receipt")+"</Date_Of_Receipt>";
			        		xml=xml+"<DOB_OF_APPLICANT>"+rs.getString("DOB_OF_APPLICANT")+"</DOB_OF_APPLICANT>";
			        		System.out.println("DOB_OF_APPLICANT");
			        		xml=xml+"<COMMUNITY_CODE>"+rs.getInt("COMMUNITY_CODE")+"</COMMUNITY_CODE>";
			        		xml=xml+"<EDU_LEVEL>"+rs.getString("EDU_LEVEL")+"</EDU_LEVEL>";
			        		xml=xml+"<ELIGIBLE_POST_CODE>"+rs.getInt("ELIGIBLE_POST_CODE")+"</ELIGIBLE_POST_CODE>";
			        		xml=xml+"<FILE_REF_NO>"+rs.getString("FILE_REF_NO")+"</FILE_REF_NO>";
			        		xml=xml+"<REMARKS>"+rs.getString("REMARKS")+"</REMARKS>";
			        		xml=xml+"<LAST_OFFICE_ID>"+rs.getString("LAST_OFFICE_ID")+"</LAST_OFFICE_ID>";
			        		xml=xml+"<Emp_Relation_Id>"+rs.getString("Emp_Relation_Id")+"</Emp_Relation_Id>";
			        		xml=xml+"<DATE_OF_DEATH>"+rs.getString("DATE_OF_DEATH")+"</DATE_OF_DEATH>";
			        		xml=xml+"<LAST_OFFICE_NAME>"+rs.getString("LAST_OFFICE_NAME")+"</LAST_OFFICE_NAME>";
			        		System.out.println("LAST_OFFICE_NAME");
			        		xml=xml+"<IS_INCOMPLETE>"+rs.getString("IS_INCOMPLETE")+"</IS_INCOMPLETE>";
			        		xml=xml+"<APPL_STATUS_ID>"+rs.getString("APPL_STATUS_ID")+"</APPL_STATUS_ID>";
			        		xml=xml+"<STATUS_REASON>"+rs.getString("STATUS_REASON")+"</STATUS_REASON>";
			        		xml=xml+"<SENIORITY_NO>"+rs.getString("SENIORITY_NO")+"</SENIORITY_NO>";
			        		xml=xml+"<EMPLOYEE_INITIAL>"+rs.getString("EMPLOYEE_INITIAL")+"</EMPLOYEE_INITIAL>";
			        		xml=xml+"<COMMUNITY_NAME>"+rs.getString("COMMUNITY_NAME")+"</COMMUNITY_NAME>";
			        		xml=xml+"<APPL_STATUS_DESC>"+rs.getString("APPL_STATUS_DESC")+"</APPL_STATUS_DESC>";
			        		xml=xml+"<ELIGIBLE_DESIG_NAME>"+rs.getString("ELIGIBLE_DESIG_NAME")+"</ELIGIBLE_DESIG_NAME>";
			        		xml=xml+"<FAMILY_RELATIONSHIP_DESC>"+rs.getString("FAMILY_RELATIONSHIP_DESC")+"</FAMILY_RELATIONSHIP_DESC>";
			        		xml=xml+"<Applicant_Initial>"+rs.getString("Applicant_Initial")+"</Applicant_Initial>";
			        		System.out.println("LAST");
			        		i++;
			        	}
			        	if(i>0)
			        	{
			        	 xml=xml+"<flag>success</flag>";
			        	}
			        	else
			        	{
			        		 xml=xml+"<flag>Norecord</flag>";
			        	}
			        }
			        else if(strCommand.equalsIgnoreCase("getreason"))
			        {
			        	xml="<response><command>getreason</command>";
			        	appl_no=Integer.parseInt(request.getParameter("Appl_id"));
			        	try
			        	{
			        		String sql="SELECT mst.APPLICATION_ID AS APPLICATION_ID, " +
			        				"  APPLICANT_NAME, " +
			        				"  APPLICANT_ADDRESS1, " +
			        				"  APPLICANT_ADDRESS2, " +
			        				"  APPLICANT_ADDRESS3, " +
			        				"  APPLICANT_PINCODE, " +
			        				"  EMPLOYEE_ID, " +
			        				"  EMPLOYEE_NAME, " +
			        				"  Designation_At_Dth, " +
			        				"  REASON_ID, " +
			        				"  REASON_DESC, " +
			        				"  Date_Of_Receipt, " +
			        				"  DOB_OF_APPLICANT, " +
			        				"  COMMUNITY_CODE, " +
			        				"  EDU_LEVEL, " +
			        				"  ELIGIBLE_POST_CODE, " +
			        				"  FILE_REF_NO, " +
			        				"  REMARKS, " +
			        				"  LAST_OFFICE_ID, " +
			        				"  Emp_Relation_Id, " +
			        				"  DATE_OF_DEATH, " +
			        				"  LAST_OFFICE_NAME, " +
			        				"  IS_INCOMPLETE, " +
			        				"  APPL_STATUS_ID, " +
			        				"  STATUS_REASON, " +
			        				"  SENIORITY_NO, " +
			        				"  EMPLOYEE_INITIAL, " +
			        				"  Applicant_Initial " +
			        				"FROM " +
			        				"  (SELECT APPLICATION_ID, " +
			        				"    APPLICANT_NAME, " +
			        				"    APPLICANT_ADDRESS1, " +
			        				"    APPLICANT_ADDRESS2, " +
			        				"    APPLICANT_ADDRESS3, " +
			        				"    APPLICANT_PINCODE, " +
			        				"    EMPLOYEE_ID, " +
			        				"    EMPLOYEE_NAME, " +
			        				"    Designation_At_Dth, " +
			        				"    TO_CHAR(Date_Of_Receipt,'dd/mm/yyyy')  AS Date_Of_Receipt, " +
			        				"    TO_CHAR(DOB_OF_APPLICANT,'dd/mm/yyyy') AS DOB_OF_APPLICANT, " +
			        				"    COMMUNITY_CODE, " +
			        				"    EDU_LEVEL, " +
			        				"    ELIGIBLE_POST_CODE, " +
			        				"    FILE_REF_NO, " +
			        				"    REMARKS, " +
			        				"    LAST_OFFICE_ID, " +
			        				"    Emp_Relation_Id, " +
			        				"    TO_CHAR( DATE_OF_DEATH,'dd/mm/yyyy') AS DATE_OF_DEATH , " +
			        				"    LAST_OFFICE_NAME, " +
			        				"    IS_INCOMPLETE, " +
			        				"    APPL_STATUS_ID, " +
			        				"    STATUS_REASON, " +
			        				"    SENIORITY_NO, " +
			        				"    EMPLOYEE_INITIAL, " +
			        				"    Applicant_Initial " +
			        				"  FROM Hrm_Compassion_Req_Trn " +
			        				"  WHERE APPLICATION_ID='"+appl_no+"' " +
			        				"  )mst " +
			        				"LEFT OUTER JOIN " +
			        				"  (SELECT APPLICATION_ID,REASON_ID FROM HRM_COMPASS_APPL_CHECKLIST " +
			        				"  )sub " +
			        				"ON sub.APPLICATION_ID=mst.APPLICATION_ID " +
			        				"LEFT OUTER JOIN " +
			        				"  (SELECT REASON_ID,REASON_DESC FROM HRM_COMPASS_INCOMP_REASON_LIST " +
			        				"  )sub1 " +
			        				"ON sub1.REASON_ID=sub.REASON_ID";
			        		ps=connection.prepareStatement(sql);
			        		rs=ps.executeQuery();
			        		while(rs.next())
			        		{
			        			xml=xml+"<tot><REASON_ID>"+rs.getInt("REASON_ID")+"</REASON_ID>";
				        		xml=xml+"<REASON_DESC>"+rs.getString("REASON_DESC")+"</REASON_DESC></tot>";
			        		}
			        		xml=xml+"<flag>success</flag>";
			        	}
			        	catch(Exception es)
			        	{
			        		System.out.println("Error is-->"+es);
			        	}
			        }
			        else if(strCommand.equalsIgnoreCase("Update"))
			        {
			        	xml="<response><command>update</command>";
			        	try
			        	{
			        	EmpId=Integer.parseInt(request.getParameter("Emp_id"));
			        	System.out.println("EmpId"+EmpId);
			        	post_code=Integer.parseInt(request.getParameter("post_code"));
			        	System.out.println("post_code"+post_code);
			        	Pincode=Integer.parseInt(request.getParameter("Pincode"));
			        	System.out.println("Pincode"+Pincode);
			        	Last_Office_id=Integer.parseInt(request.getParameter("Last_Office_id"));
			        	System.out.println("Last_Office_id"+Last_Office_id);
			        	Emp_Rel=Integer.parseInt(request.getParameter("Emp_Rel"));
			        	System.out.println("Emp_Rel"+Emp_Rel);
			        	Appl_Com=Integer.parseInt(request.getParameter("Appl_Com"));
			        	System.out.println("Appl_Com"+Appl_Com);
			        	Appl_Name=request.getParameter("Appl_Name");
			        	System.out.println("Appl_Name"+Appl_Name);
			        	Appl_addr2=request.getParameter("Appl_addr2");
			        	System.out.println("Appl_addr2"+Appl_addr2);
			        	Appl_Ini=request.getParameter("Appl_Ini");
			        	Emp_Ini=request.getParameter("Emp_Ini");
			        	System.out.println("Appl_Ini"+Appl_Ini);
			        	Appl_addr1=request.getParameter("Appl_addr1");
			        	System.out.println("Appl_addr1"+Appl_addr1);
			        	Appl_addr3=request.getParameter("Appl_addr3");
			        	System.out.println("Appl_addr3"+Appl_addr3);
			        	Desig_at_dth=request.getParameter("Desig_at_dth");
			        	System.out.println("Desig_at_dth"+Desig_at_dth);
			        	Date_of_Rec=request.getParameter("Date_of_Rec");
			        	System.out.println("Date_of_Rec"+Date_of_Rec);
			        	Appl_DOB=request.getParameter("Appl_DOB");
			        	System.out.println("Appl_DOB"+Appl_DOB);
			        	Edu_level=request.getParameter("Edu_level");
			        	System.out.println("Edu_level"+Edu_level);
			        	File_no=request.getParameter("File_no");
			        	System.out.println("File_no"+File_no);
			        	incom=request.getParameter("incom");
			        	System.out.println("incom"+incom);
			        	Status_Rsn=request.getParameter("Status_Rsn");
			        	System.out.println("Status_Rsn"+Status_Rsn);
			        	remarks=request.getParameter("remarks");
			        	System.out.println("remarks"+remarks);
			        	Emp_Name=request.getParameter("Emp_Name");
			        	System.out.println("Emp_Name"+Emp_Name);
			        	Date_of_dth=request.getParameter("Date_of_dth");
			        	System.out.println("Date_of_dth"+Date_of_dth);
			        	Last_Office_Name=request.getParameter("Last_Office_Name");
			        	System.out.println("Last_Office_Name"+Last_Office_Name);
			        	Status_Id=request.getParameter("Status_Id");
			        	System.out.println("Status_Id"+Status_Id);
			        	int Appl_no=Integer.parseInt(request.getParameter("Appl_no"));
			        	ps=connection.prepareStatement("UPDATE HRM_COMPASSION_REQ_TRN " +
					"SET  Applicant_Name     = ? , " +
					"  APPLICANT_ADDRESS1 = ? , " +
					"  APPLICANT_ADDRESS2 = ? , " +
					"  APPLICANT_ADDRESS3 = ? , " +
					"  APPLICANT_PINCODE  = ? , " +
					"  EMPLOYEE_ID        = ? , " +
					"  EMPLOYEE_NAME      = ? , " +
					"  DESIGNATION_AT_DTH = ? , " +
					"  Date_Of_Receipt    = to_date(?,'dd-mm-yyyy') , " +
					"  DOB_OF_APPLICANT   = to_date(?,'dd-mm-yyyy') , " +
					"  COMMUNITY_CODE     = ? , " +
					"  EDU_LEVEL          = ? , " +
					"  ELIGIBLE_POST_CODE = ? , " +
					"  FILE_REF_NO        = ? , " +
					"  REMARKS            = ? , " +
					"  Updated_By         = ? , " +
					"  Updated_Date       = clock_timestamp() , " +
					"  PROCESS_FLOW_ID    = 'MD' , " +
					"  LAST_OFFICE_ID     = ? , " +
					"  EMP_RELATION_ID    = ? , " +
					"  DATE_OF_DEATH      = to_date(?,'dd-mm-yyyy') , " +
					"  LAST_OFFICE_NAME   = ? , " +
					"  IS_INCOMPLETE      = ? , " +
					"  APPL_STATUS_ID     = ? , " +
					"  STATUS_REASON      = ? , " +
					
					"  EMPLOYEE_INITIAL   = ? , " +
					"  Applicant_Initial  = ? " +
					"WHERE Application_Id = ?"
					);
			        //ps.executeUpdate();
			        ps.setString(1,Appl_Name );
                    ps.setString(2, Appl_addr1);
                    ps.setString(3, Appl_addr2);
                    ps.setString(4, Appl_addr3); 
                    ps.setInt(5, Pincode);
                    ps.setInt(6, EmpId);
                    ps.setString(7, Emp_Name);
                    ps.setString(8, Desig_at_dth); 
                    ps.setString(9, Date_of_Rec);
                    ps.setString(10, Appl_DOB); 
                    ps.setInt(11, Appl_Com);
                    ps.setString(12, Edu_level);
                    ps.setInt(13, post_code); 
                    ps.setString(14, File_no);
                    ps.setString(15,remarks);
                    ps.setString(16,updatedby );
                    ps.setInt(17, Last_Office_id); 
                    ps.setInt(18, Emp_Rel);
                    ps.setString(19, Date_of_dth); 
                    ps.setString(20, Last_Office_Name);
                    ps.setString(21, incom);
                    ps.setString(22, Status_Id);
                    ps.setString(23, Status_Rsn);
                    ps.setString(24, Emp_Ini);
                    ps.setString(25, Appl_Ini);
                    ps.setInt(26, Appl_no);
                    ps.executeUpdate();
			        
                    checklist=request.getParameter("checklist");
                    if((checklist.length()>1)&&(incom.equalsIgnoreCase("N")))
                    {
                    ps=connection.prepareStatement("delete from HRM_COMPASS_APPL_CHECKLIST where APPLICATION_ID=?");
                    ps.setInt(1,Appl_no);
                    ps.executeUpdate();
                    
                    checklist=checklist.substring(0, checklist.length()-1);
                    reason=checklist.split(",");
                    System.out.println(reason.length);
                    for(int i=0;i<reason.length;i++)
                    {
                    ps=connection.prepareStatement("INSERT " +
					"INTO HRM_COMPASS_APPL_CHECKLIST " +
					"  ( " +
					"    APPLICATION_ID, " +
					"    REASON_ID, " +
					"    PROCESS_FLOW_ID, " +
					"    UPDATED_DATE, " +
					"    UPDATED_BY " +
					"  ) " +
					"  VALUES " +
					"  ( " +
					"    ?, " +
					"    ?, " +
					"    ?, " +
					"    clock_timestamp(), " +
					"    ? " +
					"  )");
                    ps.setInt(1,Appl_no );
                    ps.setInt(2, Integer.parseInt(reason[i]));
                    ps.setString(3, "CR");
                   
                    ps.setString(4,updatedby );
                    ps.executeUpdate();
                    }
                    }
                    
			        	 xml=xml+"<flag>success</flag>";
			        	}
			        	catch(Exception es)
			        	{
			        		System.out.println("Error is--->"+es);
			        	}
			        }
			        if(strCommand.equalsIgnoreCase("getchecklist"))
			        {
			        	String chklist="";
			        	xml="<response><command>getchecklist</command>";
			        	try
			        	{
			        		chklist=request.getParameter("chklist");
			        		System.out.println("chklist--->"+chklist);
			        	String sql="SELECT REASON_ID, " +
			        			"  REASON_DESC " +
			        			"FROM HRM_COMPASS_INCOMP_REASON_LIST " +
			        			"WHERE REASON_ID IN("+chklist+")";
			        	ps=connection.prepareStatement(sql);
			        	rs=ps.executeQuery();
			        	while(rs.next())
			        	{

		        			xml=xml+"<tot><REASON_ID>"+rs.getInt("REASON_ID")+"</REASON_ID>";
			        		xml=xml+"<REASON_DESC>"+rs.getString("REASON_DESC")+"</REASON_DESC></tot>";
		        		
			        	}
			        	 xml=xml+"<flag>success</flag>";
			        	}
			        	catch(Exception e)
			        	{
			        		System.out.println("Error is"+e);
			        	}
			        }
			        
			        
	        	System.out.println(xml);
	        }
	        catch(Exception e)
	        {
	          xml=xml+"<flag>fail</flag>";
	        	System.out.println("Exception :"+e);
	        }
	        xml=xml+"</response>";
	        System.out.println("xml"+xml);
	        out.println(xml);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
