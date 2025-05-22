package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HRM_GPF_Interest_NotOpted extends HttpServlet 
{  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    }

    

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	 Connection connection=null;
         Statement statement=null;    
       
         ResultSet results3=null;
         PreparedStatement ps=null;
         response.setContentType("text/xml");
         PrintWriter pw=response.getWriter();   
         String strCommand = "",year,dateofRequest,sql; 
         String xml="";
         response.setHeader("Cache-Control","no-cache");
         HttpSession session=request.getSession(false);
         try
         {            
             if(session==null)
             {
                 System.out.println(request.getContextPath()+"/index.jsp?message=sessionout");
                 response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
                return;
             }
             System.out.println(session);
                 
         }catch(Exception e)
         {
            System.out.println("Redirect Error :"+e);
         }
         try
         {
           strCommand = request.getParameter("command");      
         }
         catch(Exception e)
         {
           e.printStackTrace();
         }
       
    	try
        {
    		  LoadDriver driver = new LoadDriver();
  			  connection = driver.getConnection();
         } 
    	catch (Exception e) {
    		if(strCommand.equalsIgnoreCase("Get"))
    		{
    			xml="Database Service not Available";
    		}
    		else
    		{
    			 xml="<response><status>success</status><value>databaseError</value></response>";
    		}
    		pw.write(xml);
    		 pw.flush();
    	       pw.close();	
    	       System.out.println("databse connection error");
		return;
		}
    	
        /**
         * Variables Declaration 
         */
       
       
        int count=0;
        int strCadreId=0;
        String strCadreName="",gpfno;
        String strCadresName="";       
        String strCadreRemarks="";
        
        /**
         * Set Content Type
         */
       
        
        
        /**
         * Session Checking 
         */
        
        
        
        String userid=(String)session.getAttribute("UserId");
        System.out.println("session id is:"+userid);
        
        
        /**
         * Command Parameter 
         */
       
        String updatedby=(String)session.getAttribute("UserId");
       
        DateFormat dateFormat= new SimpleDateFormat("dd/MMM/yyyy");
        java.util.Date date= new java.util.Date();
        String dateString= dateFormat.format(date);
        java.sql.Timestamp ts=null;
		try {
			ts = new java.sql.Timestamp(dateFormat.parse(dateString).getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
        if(strCommand.equalsIgnoreCase("Add"))
        {
        	try{
        		int gpfcheck=0;
        		gpfno = request.getParameter("gpfno")!=null?request.getParameter("gpfno").trim():""; 
                gpfno=gpfno.replaceAll(" ", "");  
            	year=request.getParameter("finyear");
            	dateofRequest=request.getParameter("dateofreq");
            	System.out.println("-------------"+dateofRequest);
            	sql="select * from HRM_GPF_INTEREST_NOT_OPTED where GPF_NO=? and FIN_YEAR=?";
            	PreparedStatement st = connection.prepareStatement(sql);
        		st.setString(1, gpfno);
        		st.setString(2, year);
        		System.out.println("-------------");
        		results3=st.executeQuery();
        		while(results3.next())
        		{
        			xml="<response><status>success</status><value>Notadded</value></response>";	
        			gpfcheck++;
        		}
        		st.close();
        		if(gpfcheck==0){
        	sql="insert into HRM_GPF_INTEREST_NOT_OPTED (GPF_NO,FIN_YEAR,DATE_OF_REQUEST,UPDATED_BY,UPDATED_DATE)" +
        			"values(?,?,to_date(?,'dd-MM-yyyy'),?,?)";
        System.out.println("dd"+sql);
		 st = connection.prepareStatement(sql);
		st.setString(1, gpfno);
		st.setString(2, year);
		st.setString(3, dateofRequest);
		st.setString(4, updatedby);
		st.setTimestamp(5, ts);
		st.executeQuery();
		 xml="<response><status>success</status><value>added</value></response>";
		 st.close();
        		}
        	}
        	catch (Exception e) {
        		xml="<response><status>failure</status></response>";
        		e.printStackTrace();
			}
        	
        }
        if(strCommand.equalsIgnoreCase("Get"))
        {
        	try{
        		System.out.println("enter get");
        	int j=0;
        	Statement st = connection.createStatement();
        sql="select GPF_NO,FIN_YEAR,TO_CHAR(DATE_OF_REQUEST,'DD/MM/YYYY')as DATE_OF_REQUEST,UPDATED_BY,TO_CHAR(UPDATED_DATE,'DD/MM/YYYY')as UPDATED_DATE from HRM_GPF_INTEREST_NOT_OPTED";
		ResultSet result = st.executeQuery(sql);
		xml = xml
		+"<table id='Existing' cellspacing='2' cellpadding='3' border='1' width='75%' align='center'>" +
				"<tr class='tdH'><th>Select</th><th>GPF NO</th><th>Financial Year</th><th>Date of Request</th></tr>";
		while (result.next()) {
			
				j++;
				// html=html+"<tr ><td ><a HREF='javascript:void(0)' " +
				// "onclick='javascript:window.open('status_report_popup.jsp?&"+s1.replace("'","")+"')'>"+result.getString("designation")+"</a></td>";
				xml = xml
						+ "<tr><td align=left><a href='javascript:viewDetails("+j+ ");'>Edit"
						
						+ "</a><input type=hidden id=id"+j
						+ " name=name" + j + " value=gpfno=" + result.getString("GPF_NO")+"&dateofreq="
						+ result.getString("DATE_OF_REQUEST")+"&finyear="+result.getString("FIN_YEAR")+" /></td><td align=left>"+result.getString("GPF_NO") +"</td>";
				xml = xml + "<td align=left>"
						+ result.getString("FIN_YEAR") + "</td>" +
								"<td align=left>"
						+ result.getString("DATE_OF_REQUEST") + "</td></tr>";
			
			count++;
		}
		System.out.println("j value"+j +sql);

		if (count == 0) {
		
			xml = xml+"<tr><td colspan='4'>There is no Status</td></tr>";
		}
		xml = xml+"<tr><td class='tdH' colspan='4'><div align='center'><input type='button' name='CmdExit' " +
				"value='EXIT' id='CmdExit' onclick='Exit()' align='middle' /></div></td></tr></table>";
        	}
        	catch (Exception e) {
        		e.printStackTrace();
			}

        }
        if(strCommand.equalsIgnoreCase("update"))
        {
        	try{
        		  dateFormat= new SimpleDateFormat("dd-MMM-yyyy");
        		  gpfno = request.getParameter("gpfno")!=null?request.getParameter("gpfno").trim():""; 
        	        gpfno=gpfno.replaceAll(" ", "");   
        	year=request.getParameter("finyear")!=null?request.getParameter("finyear"):"";
        	dateofRequest=request.getParameter("dateofreq")!=null?request.getParameter("dateofreq"):"";
        	sql="update HRM_GPF_INTEREST_NOT_OPTED set FIN_YEAR=?,DATE_OF_REQUEST=to_date(?,'dd-MM-yyyy'),UPDATED_BY=?,UPDATED_DATE=? where GPF_NO=? and FIN_YEAR=?" ;
        System.out.println(sql);
        PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1, year);
		st.setString(2, dateofRequest);
		st.setString(3, updatedby);
		
		st.setTimestamp(4, ts);
		st.setString(5, gpfno);
		st.setString(6, year);
		st.executeUpdate();
		 xml="<response><status>success</status><value>update</value></response>";
        	}
        	catch (Exception e) {
        		xml="<response><status>failure</status></response>";
        		e.printStackTrace();
			}
        }
        if(strCommand.equalsIgnoreCase("delete"))
        {
        	try{
        		gpfno = request.getParameter("gpfno")!=null?request.getParameter("gpfno").trim():""; 
                gpfno=gpfno.replaceAll(" ", "");   
            	year=request.getParameter("finyear")!=null?request.getParameter("finyear"):"";
            	dateofRequest=request.getParameter("dateofreq")!=null?request.getParameter("dateofreq"):"";
        	sql="delete from HRM_GPF_INTEREST_NOT_OPTED where GPF_NO='"+gpfno+"' and FIN_YEAR='"+year+"'" ;
        System.out.println(sql);
		Statement st = connection.createStatement();
		st.execute(sql);
		 xml="<response><status>success</status><value>delete</value></response>";
        	}
        	catch (Exception e) {
        		xml="<response><status>failure</status></response>";
        		e.printStackTrace();
			}
        }
        if(strCommand.equalsIgnoreCase("edit"))
        {
        	try{
        		gpfno = request.getParameter("gpfno")!=null?request.getParameter("gpfno").trim():""; 
                gpfno=gpfno.replaceAll(" ", "");   
            	year=request.getParameter("finyear")!=null?request.getParameter("finyear"):"";
            	dateofRequest=request.getParameter("dateofreq")!=null?request.getParameter("dateofreq"):"";
        	sql="select GPF_NO,FIN_YEAR,TO_CHAR(DATE_OF_REQUEST,'DD/MM/YYYY')as DATE_OF_REQUEST from HRM_GPF_INTEREST_NOT_OPTED where GPF_NO='"+gpfno+"' and FIN_YEAR='"+year+"'"  ;
       // System.out.println(sql);
		Statement st = connection.createStatement();
		ResultSet result=st.executeQuery(sql);
		while(result.next())
		{
			 xml="<response><status>success</status><value>no</value><gpfno>"+result.getString("GPF_NO")+"</gpfno>" +
			 		"<FIN_YEAR>"+result.getString("FIN_YEAR")+"</FIN_YEAR>" +
			 				"<DATE_OF_REQUEST>"+result.getString("DATE_OF_REQUEST")+"</DATE_OF_REQUEST></response>";
		}
		
        	}
        	catch (Exception e) {
        		xml="<response><status>failure</status></response>";
        		e.printStackTrace();
			}
        }
        if(strCommand.equalsIgnoreCase("checkvalidGPF"))
        {
        	int count1=0;
        	try{
        	gpfno = request.getParameter("gpfno")!=null?request.getParameter("gpfno").trim():""; 
             gpfno=gpfno.replaceAll(" ", "");
        	sql="SELECT designation, " 
							+"  OFFICE_NAME, " 
							+"  e.employee_name " 
							+"  ||' ' " 
							+"  ||e.EMPLOYEE_INITIAL AS employee_name, " 
							+"  e.gpf_no, " 
							+"  DECODE(e.dob,NULL,'',e.dob) AS dob " 
							+"FROM " 
							+"  (SELECT OFFICE_ID, " 
							+"    designation, " 
							+"    employee_id " 
							+"  FROM " 
							+"    (SELECT DESIGNATION_ID, " 
							+"      OFFICE_ID, " 
							+"      employee_id " 
							+"    FROM HRM_EMP_CURRENT_POSTING " 
							+"    WHERE EMPLOYEE_ID           ='"+gpfno+"' " 
							+"    AND EMPLOYEE_STATUS_ID NOT IN ('SAN','DTH','VRS','DIS','MEV') " 
							+"    ) a " 
							+"  LEFT OUTER JOIN " 
							+"    ( SELECT DESIGNATION_ID AS id1, designation FROM hrm_mst_designations " 
							+"    ) b " 
							+"  ON a.DESIGNATION_ID=b.id1 " 
							+"  ) c " 
							+"LEFT OUTER JOIN " 
							+"  (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " 
							+"  ) d " 
							+"ON c.OFFICE_ID=d.OFFICE_ID " 
							+"LEFT OUTER JOIN " 
							+"  (SELECT employee_id AS empid, " 
							+"    employee_name, " 
							+"    employee_initial, " 
							+"    gpf_no, " 
							+"    TO_CHAR(DATE_OF_BIRTH,'dd/mm/yyyy') AS dob " 
							+"  FROM hrm_mst_employees " 
							+"  )e " 
							+"ON e.empid= c.employee_id" ;
            System.out.println("enter validgpf check"+sql);
    		Statement st = connection.createStatement();
    		ResultSet result=st.executeQuery(sql);
    		while(result.next())
    		{
    			 xml="<response><status>success</status><value>checkvalidGPF</value><gpfno>"+gpfno+"</gpfno>" +
    			 		"<designation>"+result.getString("designation")+"</designation>" +
    			 				"<OFFICE_NAME>"+result.getString("OFFICE_NAME")+"</OFFICE_NAME>" +"<employee_name>"+result.getString("employee_name")+"</employee_name>"+
    			 						"</response>";
    			 count1++;
    		}
    		if(count1==0)
    		{
    			xml="<response><status>success</status><value>checkvalidGPFFailure</value></response>";
    		}
            	}
            	catch (Exception e) {
            		xml="<response><status>failure</status></response>";
            		e.printStackTrace();
    			}
        }
        System.out.println(xml);
        pw.write(xml);
        pw.flush();
        pw.close();  
    }

}
