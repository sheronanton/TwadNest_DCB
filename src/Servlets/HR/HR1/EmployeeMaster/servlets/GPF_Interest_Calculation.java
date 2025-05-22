package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

 

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

//import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * Servlet implementation class ViewBalanceReport
 */
public class GPF_Interest_Calculation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Interest_Calculation() {
        super();
      
    }
private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
{
	Connection con2=null;
    Connection con=null;
    Connection con1=null;
    ResultSet rs=null;
    ResultSet rs2=null;
    ResultSet rs3=null;
    ResultSet rs4=null;
    ResultSet rs5=null;
    ResultSet rs6=null;
    PreparedStatement ps1=null;
    PreparedStatement ps=null;
    PreparedStatement ps2=null;
	   int empid=0;
	   long l=System.currentTimeMillis();
       java.sql.Timestamp ts=new java.sql.Timestamp(l);
       String ConnectionString="";
       String strDriver="",strdsn="",strhostname="",strportno="",strsid="",strdbusername="",strdbpassword="";
	   String updatedby="";
       try {
                   
    	   LoadDriver driver=new LoadDriver();
           con=driver.getConnection();

                               // con1=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                               // con2=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());    
               }
               catch(Exception e)
                   {
                      System.out.println("Exception in openeing connection :"+e); 
                      //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");   
                      
                   }
                   
                       
                       
                       try
                               {
                                   HttpSession session=request.getSession(false);
                                   UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                           	      
                             	    System.out.println("user id::"+empProfile.getEmployeeId());
                             	    empid=empProfile.getEmployeeId(); 
                             	    updatedby=(String)session.getAttribute("UserId");
                                   System.out.println("user id..."+updatedby);
                                  
                                   
                             	    if(session==null)
                                   {
                                       System.out.println(request.getContextPath()+"/index.jsp");
                                       response.sendRedirect(request.getContextPath()+"/index.jsp");

                                   }
                                   System.out.println(session);

                               }catch(Exception e)
                               {
                               System.out.println("Redirect Error :"+e);
                               }
 // System.out.println("------------My Java called----------------");
 
 
	String fin_year="";
	int ac_month=0,ac_year=0,bal=0,sub_amt=0,ref_amt=0,with_amt=0,ob=0,ac_year1=0,ac_year2=0,flag=1,gpf_no,count=1,mul=12;
	double credit=0;
	double credit1=0;
	fin_year=request.getParameter("fin_year");
	
	String fin[]=fin_year.split("-");
	ac_year1=Integer.parseInt(fin[0]);
	ac_year2=Integer.parseInt(fin[1]);
	ac_month=4;
	ac_year=ac_year1;
	//System.out.println("fin_year----------------"+fin_year);
	//System.out.println("ac_year1----------------"+ac_year1);
	//System.out.println("ac_year2----------------"+ac_year2);
	//gpf_no=4081;
	try{
		
          	if(request.getParameter("type")!=null)   
          	{
          		if(request.getParameter("type").equalsIgnoreCase("group"))
          		{
          			
          			int fromgpf=Integer.parseInt(request.getParameter("fromGpf")),togpf=Integer.parseInt(request.getParameter("toGpf"));
          			String recalculate=request.getParameter("recalculate");
          			System.out.println("group"+fromgpf+"-"+togpf+recalculate+ac_year+fin_year);
          			CallableStatement call=con.prepareCall("{call GPF_REG_FULL_INTEREST_BYGROUP(?,?,?,?,?,?,?)}");
          	         
          	         
          			call.setInt(1,fromgpf);
          			call.setInt(2,togpf);
                    call.setInt(3,4);
                    call.setInt(4,ac_year);
                    call.setString(5,fin_year);
                    call.setString(6,updatedby);
                    call.setString(7,recalculate);
                    //call.setTimestamp(5, ts);
                   // System.out.println(new java.util.Date());
                    call.execute();
                   // System.out.println(new java.util.Date());
          		}
          		else if(request.getParameter("type").equalsIgnoreCase("validate")) 
          		{
          			
          			
          			//System.out.println("starts"+fromgpf);
          			try{
          				int fromgpf=Integer.parseInt(request.getParameter("fromGpf")),togpf=Integer.parseInt(request.getParameter("toGpf"));
          			System.out.println("starts"+fromgpf+"-"+togpf+"-"+fin_year);
          			System.out.println("update HRM_GPF_INT_CREDITED set PROCESS_FLOW_ID='FR' where FIN_YEAR=? and INTEREST_TYPE='Reg' and PARTIAL_FULL='F' and GPF_NO between ? and ?");
          			 ps=con.prepareStatement("update HRM_GPF_INT_CREDITED set PROCESS_FLOW_ID='FR' where FIN_YEAR=? and INTEREST_TYPE='Reg' and PARTIAL_FULL='F' and GPF_NO between ? and ?");
          			ps.setString(1, fin_year);
          			ps.setInt(2, fromgpf);
          			ps.setInt(3, togpf);
          			ps.executeUpdate();
          			System.out.println("ends");
          			}
          			catch (Exception e) {
						e.printStackTrace();
					}
          			//System.out.println("ends");
          		}
          		else if(request.getParameter("type").equalsIgnoreCase("check")) 
          		{
          			 String CONTENT_TYPE = 
          			    "text/xml; charset=windows-1252";
          			 response.setContentType(CONTENT_TYPE);
          			 String xml="";
          			int count1=0;
          			//System.out.println("starts"+fromgpf);
          			try{
          				int fromgpf=Integer.parseInt(request.getParameter("fromGpf")),togpf=Integer.parseInt(request.getParameter("toGpf"));
          			System.out.println("starts"+fromgpf+"-"+togpf+"-"+fin_year);
          			String query=" select count(*) from HRM_MST_EMPLOYEES e,hrm_emp_current_posting cb " +
          					" where e.EMPLOYEE_ID=cb.EMPLOYEE_ID  and e.IS_CONSOLIDATE='N' and e.gpf_no is not null " +
          					" and e.gpf_no!=0 and cb. employee_status_id is not null and cb.DATE_EFFECTIVE_FROM is not null " +
          					" and cb. employee_status_id not in('VRS','SAN','DTH') and e.gpf_no not in (select gpf_no " +
          					" from hrm_gpf_interest_not_opted where fin_year=?) and e.GPF_NO between  ?  and  ? order by e.GPF_NO";
          		System.out.println(query);
          			 ps=con.prepareStatement(query);
          			ps.setString(1, fin_year);
          			ps.setInt(2, fromgpf);
          			ps.setInt(3, togpf);
          			
          			ResultSet rs1=ps.executeQuery();
          			if(rs1.next())
          			{
          				count1=rs1.getInt(1);
          			}
          			System.out.println("ends");
          			}
          			catch (Exception e) {
						e.printStackTrace();
					}
          		
          			PrintWriter out=response.getWriter();
          			xml="<response><count>"+count1+"</count></response>";
          			System.out.println("xml"+xml);
          			out.write(xml);
          			return;
          		}
          		
          	}
          	else
          	{
          		//System.out.println("full");
          CallableStatement call=con.prepareCall("{call GPF_REGULAR_FULL_INTEREST(?,?,?,?)}");
         
         
          
          call.setInt(1,4);
          call.setInt(2,ac_year);
          call.setString(3,fin_year);
          call.setString(4,updatedby);
          //call.setTimestamp(5, ts);
          call.execute();
         
          	}
          
       
        con.close();
      
 
		
		
		
	}
	catch(SQLException e)
	{
		 System.out.println("----------------Error in gpf_no");
		e.printStackTrace();
	}
	//System.out.println("BYE BYE----------------C U-------------------------"); 
    
}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
			}

}
