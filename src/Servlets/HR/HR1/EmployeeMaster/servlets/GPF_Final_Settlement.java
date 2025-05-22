package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class GPF_Final_Settlement extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        GpfAuthMstModel.openConnection();
	 }
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	GpfAuthMstModel.closeConnection();
		super.destroy();		
    }
    
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {    	 	
	
    	
    	
    	Connection con = null,con1=null;
		try 
		{
			GpfAuthMstModel.openConnection();

			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
			//		+ strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
			con1 = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
		}
 
	 String xml="";
	 response.setContentType(CONTENT_TYPE);
	 PrintWriter out = response.getWriter();
	 response.setHeader("Cache-Control","no-cache");  
	 HttpSession session=request.getSession(false);
	 ResultSet rs = null, rss = null,rs1=null,rs2=null,rss2=null,rss3=null,rs11=null,rrs=null,rrss=null;
		PreparedStatement ps = null, pss = null,ps1=null,ps2=null,pss2=null,pss3=null,ps11=null,pps=null,ppss=null;
		
		CallableStatement callableStatement = null;		
		//ResultSet rs=null;
	//	PreparedStatement ps=null,ps1=null;
	    Statement st=null;
	    Connection connection=null;
	    ResultSet results=null;
	   // ResultSet rs1=null;
	    ResultSet results2=null;
		
		
		
	 try
	 {
	         if(session==null)
	         {
	                 xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
	                 out.println(xml);
	                 System.out.println(xml);
	                 out.close();
	                 return;
	          }
	 }catch(Exception e)
	 {
	        e.printStackTrace();
	 }
	 
	 String command="";
	 try {
	 	command = request.getParameter("command");
	 	System.out.println("COMMAND======="+command);
	} catch (Exception e) {
			// TODO: handle exception
	}
	 boolean gpfIntegerCheck=false;
	 boolean gpfValidity=false;
	 
	 session =request.getSession(false);
	 String updatedby=(String)session.getAttribute("UserId");
	 
	 long l=System.currentTimeMillis();
	 java.sql.Timestamp ts=new java.sql.Timestamp(l);
	 
	 if(command.equalsIgnoreCase("leg_details_Update"))
	 {
		 System.out.println("PIN CODE=====");
		 int dth_Legal_person_ID=Integer.parseInt(request.getParameter("dth_Legal_person_ID"));
		 int gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
		// int empid=Integer.parseInt(request.getParameter("empid"));
		 int pin,percent;
				 String emp_name=request.getParameter("emp_name");
		 String dth_Legal_person=request.getParameter("dth_Legal_person");
		 String dth_Relation=request.getParameter("dth_Relation");
		 System.out.println("dth_Relation === "+dth_Relation);
		 String dth_date2=request.getParameter("dth_date2");
		 String dth_add1=request.getParameter("dth_add1");
		 String dth_add2=request.getParameter("dth_add2");
		 String dth_add3=request.getParameter("dth_add3");
		 String copy_to = null;
		System.out.println("dth_add3===="+dth_add3);
	
		  pin=Integer.parseInt(request.getParameter("pin"));
		  if(pin==0)
		  {
			  String pinn="";
			  System.out.println("PIN CODE====="+pinn);
		  }
		  System.out.println("pin =="+pin);
		  percent=Integer.parseInt(request.getParameter("percent"));
		 if(dth_add3!="" && pin!=0)
		 {
			 copy_to=dth_Legal_person+","+dth_Relation+"/O, "+emp_name+","+dth_add1+","+dth_add2+","+dth_add3+","+pin+".";
			 System.out.println("All values copy_to === "+copy_to);
		 }
		if(dth_add3=="")
		 {
			 copy_to=dth_Legal_person+","+dth_Relation+"/O, "+emp_name+","+dth_add1+","+dth_add2+","+pin+".";
			 System.out.println("All values Except add three copy_to === "+copy_to);
		 }
	    if(pin==0)
		 {
			 copy_to=dth_Legal_person+","+dth_Relation+"/O, "+emp_name+","+dth_add1+","+dth_add2+","+dth_add3+".";
			 System.out.println("All values except pin values copy_to === "+copy_to);
		 }
		 
		 if(pin==0 && dth_add3=="")
		 {
			 copy_to=dth_Legal_person+","+dth_Relation+"/O, "+emp_name+","+dth_add1+","+dth_add2+".";
			 System.out.println("All values copy_to except add three and pin values.. === "+copy_to);
		 }
		
		 String sql="update HRM_GPF_LEGAL_DETAILS set " +
					//"    LEGAL_HIER_ID=?, " +
				 "    LEGAL_HEIR_NAME=?, " +
				 "    RELATIONSHIP=?, " +
				 "    LIC_DATE=to_date(?,'dd/mm/yyyy'), " +
				 "    LEGAL_HEIR_ADD1=?, " +
				 "    LEGAL_HEIR_ADD2=?, " +
				 "    LEGAL_HEIR_ADD3=?, " +
				 "    LEGAL_HEIR_PIN=?, " +
				 "    LEGAL_PERCENT=?, " +
				 "    COPY_TO=? " +
				 "WHERE GPF_NO=? and LEGAL_HIER_ID=?"
				
				;

		 xml="<response><command>leg_details_Update</command>";
		 xml+="<status>leg_details_Update</status>";
		 
		 try {
			ps=con.prepareStatement(sql);
			//ps.setInt(1, gpf_no);
			//ps.setInt(2, empid);
			//ps.setInt(1, dth_Legal_person_ID);
			ps.setString(1, dth_Legal_person);
			ps.setString(2, dth_Relation);
			ps.setString(3, dth_date2);
			ps.setString(4, dth_add1);
			ps.setString(5, dth_add2);
			ps.setString(6, dth_add3);
			ps.setInt(7, pin);
			ps.setInt(8, percent);
			ps.setString(9, copy_to);
			ps.setInt(10, gpf_no);
			ps.setInt(11, dth_Legal_person_ID);
			
			ps.executeUpdate();
			
			xml+="<flag>success</flag>";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure</flag>";
		}

		 xml+="</response>";
		 System.out.println("LEGAL ADDING ==="+xml);
	 }
	 
	 if(command.equalsIgnoreCase("leg_details_Delete"))
	 {
		 int dth_Legal_person_ID=Integer.parseInt(request.getParameter("dth_Legal_person_ID"));
		 int gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
		 int empid=Integer.parseInt(request.getParameter("empid"));
		 int pin;
				 
		 String dth_Legal_person=request.getParameter("dth_Legal_person");
		 String dth_Relation=request.getParameter("dth_Relation");
		 String dth_date2=request.getParameter("dth_date2");
		 String dth_add1=request.getParameter("dth_add1");
		 String dth_add2=request.getParameter("dth_add2");
		 String dth_add3=request.getParameter("dth_add3");
		 
		  pin=Integer.parseInt(request.getParameter("pin"));
		  
		 System.out.println("PIN CODE====="+pin);
		 String sql="DELETE FROM hrm_gpf_legal_details WHERE GPF_NO=? AND LEGAL_HIER_ID=?";

		 xml="<response><command>leg_details_Delete</command>";
		 xml+="<status>leg_details_Delete</status>";
		 
		 try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, gpf_no);		
			ps.setInt(2, dth_Legal_person_ID);
			ps.executeUpdate();
			
			xml+="<flag>success</flag>";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure</flag>";
		}

		 xml+="</response>";
		 System.out.println("LEGAL ADDING ==="+xml);
	 }
	 
	 if(command.equalsIgnoreCase("record_count"))
	 {
		 int gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
		 int count_rec=0;
		 String sql="select count(*) as TOTAL from hrm_gpf_legal_details where gpf_no=?";
		 xml="<response><command>record_count</command>";
		 xml+="<status>record_count</status>";
		 
		 try {
				ps=con.prepareStatement(sql);
				ps.setInt(1, gpf_no);		
				//ps.setInt(2, dth_Legal_person_ID);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml+="<TOTAL>"+rs.getInt("TOTAL")+"</TOTAL>";
				}
				
				
				xml+="<flag>success</flag>";
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				xml+="<flag>failure</flag>";
			}

			 xml+="</response>";
			 System.out.println("LEGAL ADDING ==="+xml);
		 
	 }
	 if(command.equalsIgnoreCase("loadings1")){

		 System.out.println("INside LODIMG=...");
		 int gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
		 System.out.println("GPF_NO==="+gpf_no);
		 String sql="SELECT GPF_NO, " +
				 "  EMPLOYEE_ID, " +
				 "  LEGAL_HIER_ID, " +
				 "  LEGAL_HEIR_NAME, " +
				 "  RELATIONSHIP, " +
				 "  TO_CHAR(LIC_DATE,'dd/mm/yyyy') as LIC_DATE, " +
				 "  LEGAL_HEIR_ADD1, " +
				 "  LEGAL_HEIR_ADD2, " +
				 "  LEGAL_HEIR_ADD3, " +
				 "  LEGAL_HEIR_PIN, " +
				 "  LEGAL_PERCENT " +
				 "FROM HRM_GPF_LEGAL_DETAILS " +
				 "WHERE gpf_no=? ";
		 
		 xml="<response><command>loads</command>";
		 xml+="<status>loads</status>";
		 
		 try {
			 int count=0;
			ps=con.prepareStatement(sql);
			 ps.setInt(1, gpf_no);
			 rs=ps.executeQuery();
			 while(rs.next())
			 {
				 count++;
				 
				 xml+="<LEGAL_HIER_ID>"+rs.getInt("LEGAL_HIER_ID")+"</LEGAL_HIER_ID>";	 				 			 
				 xml+="<LEGAL_HEIR_NAME>"+rs.getString("LEGAL_HEIR_NAME")+"</LEGAL_HEIR_NAME>";
				 xml+="<RELATIONSHIP>"+rs.getString("RELATIONSHIP")+"</RELATIONSHIP>";
				 xml+="<LIC_DATE>"+rs.getString("LIC_DATE")+"</LIC_DATE>";
				 xml+="<LEGAL_HEIR_ADD1>"+rs.getString("LEGAL_HEIR_ADD1")+"</LEGAL_HEIR_ADD1>";
				 xml+="<LEGAL_HEIR_ADD2>"+rs.getString("LEGAL_HEIR_ADD2")+"</LEGAL_HEIR_ADD2>";
				 xml+="<LEGAL_HEIR_ADD3>"+rs.getString("LEGAL_HEIR_ADD3")+"</LEGAL_HEIR_ADD3>";
				 xml+="<LEGAL_HEIR_PIN>"+rs.getInt("LEGAL_HEIR_PIN")+"</LEGAL_HEIR_PIN>";
				 xml+="<LEGAL_PERCENT>"+rs.getInt("LEGAL_PERCENT")+"</LEGAL_PERCENT>";
			 }
			 xml+="<count>"+count+"</count>";
			 
			 xml+="<flag>success</flag>";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure</flag>";
		}
		 xml+="</response>";
		 System.out.println("XML IS====="+xml);

	 }
	 
	 
	 if(command.equalsIgnoreCase("loading"))
	 {
		 System.out.println("INside LODIMG=...");
		 int gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
		 System.out.println("GPF_NO==="+gpf_no);
		 String sql="SELECT GPF_NO, " +
				 "  EMPLOYEE_ID, " +
				 "  LEGAL_HIER_ID, " +
				 "  LEGAL_HEIR_NAME, " +
				 "  RELATIONSHIP, " +
				 "  TO_CHAR(LIC_DATE,'dd/mm/yyyy') as LIC_DATE, " +
				 "  LEGAL_HEIR_ADD1, " +
				 "  LEGAL_HEIR_ADD2, " +
				 "  LEGAL_HEIR_ADD3, " +
				 "  LEGAL_HEIR_PIN, " +
				 "  LEGAL_PERCENT " +
				 "FROM HRM_GPF_LEGAL_DETAILS " +
				 "WHERE gpf_no=? ";
		 
		 xml="<response><command>load</command>";
		 xml+="<status>load</status>";
		 
		 try {
			 int count=0;
			ps=con.prepareStatement(sql);
			 ps.setInt(1, gpf_no);
			 rs=ps.executeQuery();
			 while(rs.next())
			 {
				 count++;
				 
				 xml+="<LEGAL_HIER_ID>"+rs.getInt("LEGAL_HIER_ID")+"</LEGAL_HIER_ID>";	 				 			 
				 xml+="<LEGAL_HEIR_NAME>"+rs.getString("LEGAL_HEIR_NAME")+"</LEGAL_HEIR_NAME>";
				 xml+="<RELATIONSHIP>"+rs.getString("RELATIONSHIP")+"</RELATIONSHIP>";
				 xml+="<LIC_DATE>"+rs.getString("LIC_DATE")+"</LIC_DATE>";
				 xml+="<LEGAL_HEIR_ADD1>"+rs.getString("LEGAL_HEIR_ADD1")+"</LEGAL_HEIR_ADD1>";
				 xml+="<LEGAL_HEIR_ADD2>"+rs.getString("LEGAL_HEIR_ADD2")+"</LEGAL_HEIR_ADD2>";
				 xml+="<LEGAL_HEIR_ADD3>"+rs.getString("LEGAL_HEIR_ADD3")+"</LEGAL_HEIR_ADD3>";
				 xml+="<LEGAL_HEIR_PIN>"+rs.getInt("LEGAL_HEIR_PIN")+"</LEGAL_HEIR_PIN>";
				 xml+="<LEGAL_PERCENT>"+rs.getInt("LEGAL_PERCENT")+"</LEGAL_PERCENT>";
			 }
			 xml+="<count>"+count+"</count>";
			 
			 xml+="<flag>success</flag>";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure</flag>";
		}
		 xml+="</response>";
		 System.out.println("XML IS====="+xml);

	 }
	 
	 if(command.equalsIgnoreCase("Check"))
	  {
	 

	  int gpfNo = Integer.parseInt(request.getParameter("gpf_no").trim());
	  String sql_qry="select * from hrm_emp_current_posting  where employee_id=?";
	  xml="<response><command>Check</command>";
		xml+="<status>Check</status>";
	  try {
		  pss=con.prepareStatement(sql_qry);
			pss.setInt(1,gpfNo);
			rss=pss.executeQuery();
				if(rss.next())
			{
	  
					/*    String sqls="select GPF_NO from HRM_GPF_FINAL_SETTLEMENT_PROC where gpf_no=?";
	     int count=0;
	    
	    
			pss=con.prepareStatement(sqls);
			pss.setInt(1,gpfNo);
			rss=pss.executeQuery();
			if(rss.next())
			{
				count++;
				xml+="<count>"+count+"</count>";
				if(gpfNo==rss.getInt("GPF_NO"))
				{
									
					
					xml+="<flag>exists</flag>";
				}
			}
			else
			{
				/*String sqls1="select gpf_no from hrm_gpf_int_auth_trn where int_calculated='Y'  and note_generated='Y' and proc_generated='Y'  and gpf_no=?";
				pss=con.prepareStatement(sqls1);
				pss.setInt(1,gpfNo);
				rss=pss.executeQuery();
				if(rss.next())
				{
					xml+="<flag>success</flag>";
					xml+="<GPF_NO>"+rss.getInt("gpf_no")+"</GPF_NO>";
				}
				else
				{
					xml+="<flag>no_partial</flag>";
				}
			}*/
//			if(count==0)
//			{
//			xml+="<count>"+count+"</count>";
//			xml+="<flag>failure</flag>";
//			xml+="<GPF_NO>"+gpfNo+"</GPF_NO>";
//			
//			}
					xml+="<flag>success</flag>";
	     }
			else
			
			{
				xml+="<flag>success</flag>";
			}
	    
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
		
			
		}
	     xml=xml+"</response>";
	     System.out.print("XML CHECKKKKING ==== "+xml);
	     
 }
	 
	 if(command.equalsIgnoreCase("copy_to"))
	 {
		 System.out.println("copy to of the loop....");
		 int counts=0;
		 int gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
		 System.out.println("copy to of the loop...."+gpf_no);
		 String sql="SELECT COPY_TO FROM hrm_gpf_legal_details WHERE gpf_no=?";
		 xml="<response><command>copy_to</command>";
		 xml+="<status>copy_to</status>";
		 try
		 {
			 	System.out.println("copy to of the loop...."+xml);
			 	ps=con.prepareStatement(sql);
				ps.setInt(1, gpf_no);
				rs=ps.executeQuery();
				while(rs.next())
				{
					counts++;
					
					xml+="<COPY_TO>"+rs.getString("COPY_TO")+"</COPY_TO>";
				}
				xml+="<counts>"+counts+"</counts>";
				xml+="<flag>success</flag>";
		 }catch(SQLException e)
		 {
			 e.printStackTrace();
				xml+="<flag>failure</flag>";
		 }

			 xml+="</response>";
			 System.out.println("COPY TO INFORMATIONS  ==="+xml);
	 }
	 
	 
	 
	 
	 
	 if(command.equalsIgnoreCase("legal_details"))
	 {
		 
		 int dth_Legal_person_ID=Integer.parseInt(request.getParameter("dth_Legal_person_ID"));
		 int gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
		 int empid=Integer.parseInt(request.getParameter("empid"));
		 int pin,percent;
				 
		 String dth_Legal_person=request.getParameter("dth_Legal_person");
		 String dth_Relation=request.getParameter("dth_Relation");
		 String dth_date2=request.getParameter("dth_date2");
		 String dth_add1=request.getParameter("dth_add1");
		 String dth_add2=request.getParameter("dth_add2");
		 String dth_add3=request.getParameter("dth_add3");
		 String copy_to=request.getParameter("copy_to");
		 
		  pin=Integer.parseInt(request.getParameter("pin"));
		  percent=Integer.parseInt(request.getParameter("percent"));
		  
		 System.out.println("PIN CODE====="+pin);
		 String sql="INSERT " +
				 "INTO HRM_GPF_LEGAL_DETAILS " +
				 "  ( " +
				 "    GPF_NO, " +
				 "    EMPLOYEE_ID, " +
				 "    LEGAL_HIER_ID, " +
				 "    LEGAL_HEIR_NAME, " +
				 "    RELATIONSHIP, " +
				 "    LIC_DATE, " +
				 "    LEGAL_HEIR_ADD1, " +
				 "    LEGAL_HEIR_ADD2, " +
				 "    LEGAL_HEIR_ADD3, " +
				 "    LEGAL_HEIR_PIN, " +
				 "    LEGAL_PERCENT, " +
				 "    COPY_TO " +
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
				 "    ?, " +
				 "    ?, " +
				 "    ?, " +
				 "    ? " +
				 "  )";

		 xml="<response><command>legal_details</command>";
		 xml+="<status>legal_details</status>";
		 
		 try {
			ps=con.prepareStatement(sql);
			ps.setInt(1, gpf_no);
			ps.setInt(2, empid);
			ps.setInt(3, dth_Legal_person_ID);
			ps.setString(4, dth_Legal_person);
			ps.setString(5, dth_Relation);
			ps.setString(6, dth_date2);
			ps.setString(7, dth_add1);
			ps.setString(8, dth_add2);
			ps.setString(9, dth_add3);
			ps.setInt(10, pin);
			ps.setInt(11, percent);
			ps.setString(12, copy_to);
			ps.executeUpdate();
			
			xml+="<flag>success</flag>";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure</flag>";
		}

		 xml+="</response>";
		 System.out.println("LEGAL ADDING ==="+xml);
		 
	 }
	 if(command.equalsIgnoreCase("other_details"))
	 {
		 System.out.println("With in Other Details...");
		 int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
		 int count=0;
		 String sql="SELECT * " +
				 "FROM( " +
				 "  (SELECT EMPLOYEE_ID, " +
				 "    DESIGNATION_ID, " +
				 "    EMPLOYEE_STATUS_ID, " +
				 "    DATE_EFFECTIVE_FROM " +
				 "  FROM hrm_emp_current_posting " +
				 "  WHERE employee_id=? " +
				 "  )a " +
				 "LEFT OUTER JOIN " +
				 "  (SELECT DESIGNATION_ID,DESIGNATION FROM HRM_MST_DESIGNATIONS " +
				 "  )b " +
				 "ON a.DESIGNATION_ID=b.DESIGNATION_ID " +
				 "LEFT OUTER JOIN " +
				 "  (SELECT EMPLOYEE_ID, " +
				 "    EMPLOYEE_NAME, " +
				 "    EMPLOYEE_INITIAL, " +
				 "    EMPLOYEE_PREFIX " +
				 "  FROM HRM_MST_EMPLOYEES " +
				 "  )c " +
				 "ON a.EMPLOYEE_ID=c.EMPLOYEE_ID)"
;
		 
		 int empid=0,desg_id=0;
		 String emp_status="",date_effective="",desg="",emp_name="";
		 String sub="GENERAL PROVIDENT FUND - Authorisation for the final payment of GPF (Regular) GPF (Impounded) Account in respect of";
		 String prefix="",initial="";
		// String pro_officer_name=request.getParameter("pro_officer_name").trim();
		// String suffix=request.getParameter("suffix");
		// String pro_desingnation=request.getParameter("pro_desingnation");
		 xml="<response><command>other_details</command>";
		 xml+="<status>other_details</status>";
		 try {
			ps=con.prepareStatement(sql);
			ps.setInt(1,gpfNo);
			rs=ps.executeQuery();
			while(rs.next())
			{
				count++;
				empid=rs.getInt("EMPLOYEE_ID");
				emp_name=rs.getString("EMPLOYEE_NAME");
				prefix=rs.getString("EMPLOYEE_PREFIX");
				initial=rs.getString("EMPLOYEE_INITIAL");
				desg_id=rs.getInt("DESIGNATION_ID");
				emp_status=rs.getString("EMPLOYEE_STATUS_ID");
				date_effective=rs.getString("DATE_EFFECTIVE_FROM");
				desg=rs.getString("DESIGNATION");
				
				//xml+="<EMPLOYEE_ID>"+rs.getInt("EMPLOYEE_ID")+"</EMPLOYEE_ID>";
				
				//xml+="<EMPLOYEE_STATUS_ID>"+rs.getString("EMPLOYEE_STATUS_ID")+"</EMPLOYEE_STATUS_ID>";
				//xml+="<DATE_EFFECTIVE_FROM>"+rs.getString("DATE_EFFECTIVE_FROM")+"</DATE_EFFECTIVE_FROM>";
				//xml+="<DESIGNATION_ID>"+rs.getInt("DESIGNATION_ID")+"</DESIGNATION_ID>";
				//xml+="<DESIGNATION>"+rs.getString("DESIGNATION")+"</DESIGNATION>";
				
			}
			
			if(emp_status.equalsIgnoreCase("DTH"))
			{
				sub=sub+" (Late) "+prefix+" "+initial+" "+emp_name+", "+desg ;
			}
			else
			{
				sub=sub+" "+prefix+" "+initial+" "+emp_name+", "+desg ;
			}
			sub=sub+" - GPF Account No. "+gpfNo+"/TWAD - ";
			
			if(emp_status.equalsIgnoreCase("DTH"))
			{
				sub=sub+" Died on "+date_effective+" - Final Payment Authorised - Reg";
			}
			
			if(emp_status.equalsIgnoreCase("VRS"))
			{
				sub=sub+" VRS on "+date_effective+" - Final Payment Authorised - Reg";
			}
			if(emp_status.equalsIgnoreCase("SAN"))
			{
				sub=sub+" retired on "+date_effective+" - Final Payment Authorised - Reg";
			}
			xml+="<SUBJECT>"+sub+"</SUBJECT>";
			xml+="<count>"+count+"</count>";
			
			
			
			
			
			
//			
//			  String s1="select max(LEGAL_HIER_ID) as LEGAL_HIER_ID_1 from HRM_GPF_LEGAL_DETAILS Where GPF_NO=?";
//	            System.out.println(s1);
//	          
//	           ps11=con.prepareStatement(s1);
//	           ps11.setInt(1, gpfNo);
//	           rs11=ps11.executeQuery();
//	           int c=0;
//	           while(rs11.next())
//	           {
//	        	  
//	        	   System.out.println(s1);
//	        	   
//	        	   c=rs11.getInt("LEGAL_HIER_ID_1");
//	        	   c++;
//	        	   //xml+="<LEGAL_HIER_ID>"+rs1.getInt("LEGAL_HIER_ID_1")+"</LEGAL_HIER_ID>";
//	        	   
//	           }
//	           System.out.println("Count========>"+c);
//	           xml+="<LEGAL_HIER_ID_1>"+c+"</LEGAL_HIER_ID_1>";
//	           System.out.println("Count========>"+xml);
			
			
			
			
			
			
			
			
			
			
			
			xml+="<flag>success1</flag>";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure1</flag>";
		}
		 
		 
		
		 
		 
		 xml+="</response>";
		 
		
	 }
	 
	 
	 
	 
	 if(command.equalsIgnoreCase("legal_ID"))
	 {
		 int gpf_no=Integer.parseInt(request.getParameter("gpf_no"));
		 String s1="select max(LEGAL_HIER_ID) as LEGAL_HIER_ID_1 from HRM_GPF_LEGAL_DETAILS Where GPF_NO=?";
         System.out.println(s1);
       
         xml="<response><command>legal_ID</command>";
		 xml+="<status>legal_ID</status>";
         
        try {
			ps11=con.prepareStatement(s1);
			  ps11.setInt(1, gpf_no);
		        rs11=ps11.executeQuery();
		        int c=0;
		        while(rs11.next())
		        {
		     	  
		     	   System.out.println(s1);
		     	   
		     	   c=rs11.getInt("LEGAL_HIER_ID_1");
		     	   c++;
		     	  
		     	  System.out.println("Count========>"+c);
		          xml+="<LEGAL_HIER_ID_1>"+c+"</LEGAL_HIER_ID_1>";
		          System.out.println("Count========>"+xml);
		          xml+="<flag>success1</flag>";
		          System.out.println("Count========>"+xml);
		          xml+="</response>";
		        }
		        
		       
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure1</flag>";
			xml+="</response>";
		}
        
        System.out.println("Count========>"+xml);
	 }
	 
	   
	  if(command.equalsIgnoreCase("Get")) {
	     int gpfNo=0;	 
	     String exist = null,notexist;
		 try {
		 	gpfNo = Integer.parseInt(request.getParameter("gpf_no").trim());
//		 	exist=request.getParameter("exist");
//		 	notexist=request.getParameter("notexist");
		 	
		 	gpfIntegerCheck=true;
		} catch (Exception e) {
			// TODO: handle exception
			gpfIntegerCheck=false;
		}
	     xml="<response><command>get</command>";
         //xml+="<flag1>success</flag1>";
	     String sqls = null,sqll=null;
	
	     
	    sqls = "SELECT e.EMPLOYEE_NAME, " +
	     "  TO_CHAR(e.DATE_OF_BIRTH,'dd/mm/yyyy') as DOB, " +
	     "  e.GPF_NO, " +
	     "  f.DESIGNATION, " +
	     "  e.EMPLOYEE_ID " +
	     "FROM HRM_MST_EMPLOYEES e, " +
	     "  HRM_EMP_CURRENT_POSTING d, " +
	     "  HRM_MST_DESIGNATIONS f " +
	     "WHERE e.EMPLOYEE_ID =d.EMPLOYEE_ID " +
	     "AND d.DESIGNATION_ID=f.DESIGNATION_ID " +
	     "AND e.GPF_NO        =?";
	     int count=0,c=0;
	     try {
			pss=con.prepareStatement(sqls);
			pss.setInt(1,gpfNo);
			rss=pss.executeQuery();
			while(rss.next())
			{
				count++;
				xml+="<count>"+count+"</count>";
				xml+="<GPF_NO>"+rss.getInt("GPF_NO")+"</GPF_NO>";
				xml+="<EMPLOYEE_NAME>"+rss.getString("EMPLOYEE_NAME")+"</EMPLOYEE_NAME>";
				xml+="<DOB>"+rss.getString("DOB")+"</DOB>";
				xml+="<DESIGNATION>"+rss.getString("DESIGNATION")+"</DESIGNATION>";
				xml+="<EMPLOYEE_ID>"+rss.getInt("GPF_NO")+"</EMPLOYEE_ID>";
			}
			rss.close();
			
			sqll=    "SELECT A.EMPLOYEE_STATUS_ID, " +
					"  TO_CHAR(B.DATE_OF_BIRTH,'dd/mm/yyyy')       AS DATE_OF_BIRTH, " +
					"  TO_CHAR(A.DATE_EFFECTIVE_FROM,'dd/mm/yyyy') AS DATE_EFFECTIVE_FROM " +
					"FROM HRM_EMP_CURRENT_POSTING A , " +
					"  hrm_mst_employees B " +
					"WHERE (A.EMPLOYEE_STATUS_ID='VRS' " +
					"OR A.EMPLOYEE_STATUS_ID    ='SAN' " +
					"OR A.EMPLOYEE_STATUS_ID    ='DTH' " +
					"OR A.EMPLOYEE_STATUS_ID    ='MEV' " +
					"OR A.EMPLOYEE_STATUS_ID    ='CMR') " +
					"AND B.GPF_NO               =? " +
					"AND B.employee_id          = A.employee_id"
					
	;				
			pss2=con.prepareStatement(sqll);
			pss2.setInt(1, gpfNo);
			//pss2.setInt(2, gpfNo);
			rss2=pss2.executeQuery();
		String date_reliev="";
			while(rss2.next())
			{
				c++;
				xml+="<count1>"+c+"</count1>";
				xml+="<EMPLOYEE_STATUS_ID>"+rss2.getString("EMPLOYEE_STATUS_ID")+"</EMPLOYEE_STATUS_ID>";
				xml+="<DATE_EFFECTIVE_FROM>"+rss2.getString("DATE_EFFECTIVE_FROM")+"</DATE_EFFECTIVE_FROM>";
				
				date_reliev=rss2.getString("DATE_EFFECTIVE_FROM");;
			}
			
			try
			{
				String sssql="select copy_to from hrm_gpf_legal_details where gpf_no=?";
				int cc=0;
				pps=con.prepareStatement(sssql);
				pps.setInt(1, gpfNo);
				rrs=pps.executeQuery();
				while(rrs.next())
				{
					cc++;
					xml+="<COPY_TO>"+rrs.getString("COPY_TO")+"</COPY_TO>";
				}
				xml+="<cccounts>"+cc+"</cccounts>";
			}
			
			catch(Exception e){
  				System.out.println("Error in "+e);
  			}
			
			try
			{
				String sssqls="select count(*) as TOTAL from hrm_gpf_legal_details where gpf_no=?";
				int cccc=0;
				ppss=con.prepareStatement(sssqls);
				ppss.setInt(1, gpfNo);
				rrss=ppss.executeQuery();
				while(rrss.next())
				{
					cccc++;
					xml+="<TOTAL>"+rrss.getString("TOTAL")+"</TOTAL>";
				}
				xml+="<cccount>"+cccc+"</cccount>";
			}catch(Exception e)
			{
				
			}
			
			int v1_excess=0,v2_excess=0,v3_excess=0,miss_amt_excess=0,sum_interest_excess=0;
			  int ImpReg=0,Imp2003=0,sum_interest=0,total=0,v1=0,v2=0,v3=0,emp_id=0,miss_amt=0,impreg_cr=0,impreg_db=0,imp2003_cr=0,imp2003_db=0,fimpreg=0,fimp2003=0;
			  int with_amount_DB=0;
			  int with_amount_CR=0;
			  String IMP2003_DB=null;
			  String IMP2003_CR=null;
			  String IMPREG_CR=null;
			  String IMPREG_DB=null;
			  String Ref_Amt=null;
			  String slip_fin_year="",gpf_no="",emp_name="",month_year="",type_trans_value="";
			  int close_bal=0,ac_month=0,ac_year=0,ref_amount=0,with_amount=0,arr_amount=0,sub_total=0,ref_total=0,with_total=0,with_total_CR=0,with_total_DB=0,with_total_null=0,with_amount_null=0;
			  String months[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
			  int[] sub_amount;
			  String[] relmonth_year;
			  int beagnYear = 0;
	          int endYear = 0;
	          int beagnYear1 = 0;
	          int endYear1 = 0;
			int beagnYear_fut=0;
			int endYear_fut =0;
			
			try {
				
				
				ps=con.prepareStatement("select SLIP_ISSUED_FIN_YEAR from HRM_GPF_LAST_SLIP_ISSUED" );
		          rs=ps.executeQuery();
		               if(rs.next()) 
		               {
		                  slip_fin_year=rs.getString(1);               
		               }
		          //rs.close();
		         // ps.close();
		         // System.out.println("Fin Year------------"+slip_fin_year);
		          
		               String[] splitYear = slip_fin_year.split("-");
		               beagnYear=Integer.parseInt(splitYear[0]);
		               beagnYear1=beagnYear+1;
		              
		               endYear=Integer.parseInt(splitYear[1]);
		               endYear1=endYear+1;
		               
		                beagnYear_fut = beagnYear1+1;
		               
		               endYear_fut = endYear1+1;
		     		  System.out.println("beagnYear1------------"+beagnYear1+" endYear1 "+endYear1);
		     		System.out.println("gpfNo------------"+gpfNo);
		     		  
		     		  String fin_year = beagnYear1+"-"+endYear1;
		     		  
		     		  String fin_year_fut = beagnYear_fut+"-"+endYear_fut;
		     		 xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
		      
		     		
		               System.out.println("fin_year_fut=========="+fin_year_fut);
		          ps=con.prepareStatement("SELECT Employee_Id,da_amount FROM Hrm_Employee_Da_Details  where process_flow_id='FR' and employee_id =?   ");
		          ps.setInt(1,gpfNo);
		         // ps.setString(2,slip_fin_year);
		          rs=ps.executeQuery();
		               if(rs.next()) 
		               {
		            	 
		            	   xml+="<DA_AMT>"+rs.getInt("da_amount")+" </DA_AMT>";
		            	 
		            	  
		               }
		               else
		            	   xml+="<DA_AMT>"+0+" </DA_AMT>"; 
		               
		          rs.close();
		          ps.close();
		         
		        /*  ps=con.prepareStatement("SELECT Gpf_No, " +
		        		  "  NVL(Imp2003_302cr-Imp2003_302db,0)                                                        AS Imp2003_Cr, " +
		        		  "  NVL(Imp2003_303db-Imp2003_303cr,0)                                                        AS Imp2003_Db, " +
		        		  "  NVL(Impreg_002cr -Impreg_002db,0)                                                         AS Impreg_Cr, " +
		        		  "  NVL(Impreg_003db -Impreg_003cr,0)                                                         AS impReg_db, " +
		        		  "  NVL(imp2003_ob,0)                                                                         AS imp2003_ob, " +
		        		  "  NVL(impReg_ob,0)                                                                          AS impReg_ob, " +
		        		  "  NVL (imp2003_ob,0)+ NVL(Imp2003_302cr-Imp2003_302db,0)-NVL(Imp2003_303db-Imp2003_303cr,0) AS imp2003_cb, " +
		        		  "  NVL(impReg_ob,0)  +NVL(Impreg_002cr-Impreg_002db,0)-NVL(Impreg_003db-Impreg_003cr,0)      AS impReg_cb, " +
		        		  "  'FR'                                                                                      AS flowId, " +
		        		  "  'system' upd_user " +
		        		  "FROM " +
		        		  "  ( SELECT DISTINCT gpf_no FROM HRM_GPF_IMPOUND_ob WHERE gpf_no = "+gpfNo+"  " +
		        		  "  ) a " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT Gpf_No AS Gpfno1, " +
		        		  "    CASE " +
		        		  "      WHEN (Type_Trans='CR') " +
		        		  "      THEN NVL(SUM(Impound_Amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS Imp2003_302cr, " +
		        		  "    CASE " +
		        		  "      WHEN (type_trans='DB') " +
		        		  "      THEN NVL(SUM(impound_amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS imp2003_302DB " +
		        		  "  FROM Hrm_Gpf_Impound_Disbnew " +
		        		  "  WHERE ( Gpf_No     = "+gpfNo+" ) " +
		        		  "  AND (AC_HEAD_CODE  =391302 " +
		        		  "  AND impound_type   ='Imp2003' ) " +
		        		  "  AND ( FIN_YEAR='"+slip_fin_year+"' or FIN_YEAR='"+slip_fin_year+"' )"+
		        		  "  GROUP BY Gpf_No, " +
		        		  "    type_trans " +
		        		  "  ) b " +
		        		  "ON a.gpf_no =b.gpfno1 " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT Gpf_No AS Gpfno2, " +
		        		  "    CASE " +
		        		  "      WHEN (Type_Trans='CR') " +
		        		  "      THEN NVL(SUM(Impound_Amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS Imp2003_303cr, " +
		        		  "    CASE " +
		        		  "      WHEN (type_trans='DB') " +
		        		  "      THEN NVL(SUM(impound_amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS imp2003_303DB " +
		        		  "  FROM Hrm_Gpf_Impound_Disbnew " +
		        		  "  WHERE ( Gpf_No     = "+gpfNo+" ) " +
		        		  "  AND (AC_HEAD_CODE  =391303 " +
		        		  "  AND impound_type   ='Imp2003' ) " +
		        		  "  AND process_flow_id='FR' " +
		        		  "  AND ( FIN_YEAR='"+fin_year+"' or FIN_YEAR='"+fin_year_fut+"' )"+
		        		  "  GROUP BY Gpf_No, " +
		        		  "    type_trans " +
		        		  "  ) c " +
		        		  "ON a.gpf_no = c.gpfno2 " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT Gpf_No AS Gpfno3, " +
		        		  "    CASE " +
		        		  "      WHEN (Type_Trans='CR') " +
		        		  "      THEN NVL(SUM(Impound_Amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS Impreg_002cr, " +
		        		  "    CASE " +
		        		  "      WHEN (type_trans='DB') " +
		        		  "      THEN NVL(SUM(impound_amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS impreg_002DB " +
		        		  "  FROM Hrm_Gpf_Impound_Disbnew " +
		        		  "  WHERE ( Gpf_No     = "+gpfNo+" ) " +
		        		  "  AND (Ac_Head_Code  =391002 " +
		        		  "  AND impound_type   ='ImpReg' ) " +
		        		  "  AND process_flow_id='FR' " +
		        		  "  AND ( FIN_YEAR='"+fin_year+"' or FIN_YEAR='"+fin_year_fut+"' )"+
		        		  "  GROUP BY Gpf_No, " +
		        		  "    type_trans " +
		        		  "  ) d " +
		        		  "ON a.gpf_no =d.gpfno3 " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT Gpf_No AS Gpfno4, " +
		        		  "    CASE " +
		        		  "      WHEN (Type_Trans='CR') " +
		        		  "      THEN NVL(SUM(Impound_Amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS Impreg_003cr, " +
		        		  "    CASE " +
		        		  "      WHEN (type_trans='DB') " +
		        		  "      THEN NVL(SUM(impound_amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS impreg_003DB " +
		        		  "  FROM Hrm_Gpf_Impound_Disbnew " +
		        		  "  WHERE ( Gpf_No     = "+gpfNo+" ) " +
		        		  "  AND (Ac_Head_Code  =391003 " +
		        		  "  AND impound_type   ='ImpReg' ) " +
		        		  "  AND ( FIN_YEAR='"+fin_year+"' or FIN_YEAR='"+fin_year_fut+"' )"+
		        		  "  GROUP BY Gpf_No, " +
		        		  "    type_trans " +
		        		  "  ) e " +
		        		  "ON A.Gpf_No = E.Gpfno4 " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT gpf_no    AS gpf, " +
		        		  "    CLOSING_BALANCE AS impReg_ob " +
		        		  "  FROM hrm_gpf_impound_cb " +
		        		  "  WHERE (Gpf_No      = "+gpfNo+") " +
		        		  "  AND Impound_Type_Id='ImpReg' " +
		        		  "  AND FINANCIAL_YEAR ='"+slip_fin_year+"' " +
		        		  "  ) f " +
		        		  "ON a.gpf_no=f.gpf " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT gpf_no    AS gpf1, " +
		        		  "    CLOSING_BALANCE AS imp2003_ob " +
		        		  "  FROM Hrm_Gpf_Impound_Cb " +
		        		  "  WHERE ( gpf_no     = "+gpfNo+") " +
		        		  "  AND impound_type_id='Imp2003' " +
		        		  "  AND FINANCIAL_YEAR ='"+slip_fin_year+"' " +
		        		  "  )G " +
		        		  "ON a.gpf_no=g.gpf1");  

		        		  String sql1="SELECT Gpf_No, " +
		        		  "  NVL(Imp2003_302cr-Imp2003_302db,0)                                                        AS Imp2003_Cr, " +
		        		  "  NVL(Imp2003_303db-Imp2003_303cr,0)                                                        AS Imp2003_Db, " +
		        		  "  NVL(Impreg_002cr -Impreg_002db,0)                                                         AS Impreg_Cr, " +
		        		  "  NVL(Impreg_003db -Impreg_003cr,0)                                                         AS impReg_db, " +
		        		  "  NVL(imp2003_ob,0)                                                                         AS imp2003_ob, " +
		        		  "  NVL(impReg_ob,0)                                                                          AS impReg_ob, " +
		        		  "  NVL (imp2003_ob,0)+ NVL(Imp2003_302cr-Imp2003_302db,0)-NVL(Imp2003_303db-Imp2003_303cr,0) AS imp2003_cb, " +
		        		  "  NVL(impReg_ob,0)  +NVL(Impreg_002cr-Impreg_002db,0)-NVL(Impreg_003db-Impreg_003cr,0)      AS impReg_cb, " +
		        		  "  'FR'                                                                                      AS flowId, " +
		        		  "  'system' upd_user " +
		        		  "FROM " +
		        		  "  ( SELECT DISTINCT gpf_no FROM HRM_GPF_IMPOUND_ob WHERE gpf_no = "+gpfNo+"  " +
		        		  "  ) a " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT Gpf_No AS Gpfno1, " +
		        		  "    CASE " +
		        		  "      WHEN (Type_Trans='CR') " +
		        		  "      THEN NVL(SUM(Impound_Amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS Imp2003_302cr, " +
		        		  "    CASE " +
		        		  "      WHEN (type_trans='DB') " +
		        		  "      THEN NVL(SUM(impound_amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS imp2003_302DB " +
		        		  "  FROM Hrm_Gpf_Impound_Disbnew " +
		        		  "  WHERE ( Gpf_No     = "+gpfNo+" ) " +
		        		  "  AND (AC_HEAD_CODE  =391302 " +
		        		  "  AND impound_type   ='Imp2003' ) " +
		        		  "  AND ( FIN_YEAR='"+fin_year+"' or FIN_YEAR='"+fin_year_fut+"' )"+
		        		  "  GROUP BY Gpf_No, " +
		        		  "    type_trans " +
		        		  "  ) b " +
		        		  "ON a.gpf_no =b.gpfno1 " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT Gpf_No AS Gpfno2, " +
		        		  "    CASE " +
		        		  "      WHEN (Type_Trans='CR') " +
		        		  "      THEN NVL(SUM(Impound_Amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS Imp2003_303cr, " +
		        		  "    CASE " +
		        		  "      WHEN (type_trans='DB') " +
		        		  "      THEN NVL(SUM(impound_amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS imp2003_303DB " +
		        		  "  FROM Hrm_Gpf_Impound_Disbnew " +
		        		  "  WHERE ( Gpf_No     = "+gpfNo+" ) " +
		        		  "  AND (AC_HEAD_CODE  =391303 " +
		        		  "  AND impound_type   ='Imp2003' ) " +
		        		  "  AND process_flow_id='FR' " +
		        		  "  AND ( FIN_YEAR='"+fin_year+"' or FIN_YEAR='"+fin_year_fut+"' )"+
		        		  "  GROUP BY Gpf_No, " +
		        		  "    type_trans " +
		        		  "  ) c " +
		        		  "ON a.gpf_no = c.gpfno2 " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT Gpf_No AS Gpfno3, " +
		        		  "    CASE " +
		        		  "      WHEN (Type_Trans='CR') " +
		        		  "      THEN NVL(SUM(Impound_Amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS Impreg_002cr, " +
		        		  "    CASE " +
		        		  "      WHEN (type_trans='DB') " +
		        		  "      THEN NVL(SUM(impound_amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS impreg_002DB " +
		        		  "  FROM Hrm_Gpf_Impound_Disbnew " +
		        		  "  WHERE ( Gpf_No     = "+gpfNo+" ) " +
		        		  "  AND (Ac_Head_Code  =391002 " +
		        		  "  AND impound_type   ='ImpReg' ) " +
		        		  "  AND process_flow_id='FR' " +
		        		  "  AND ( FIN_YEAR='"+fin_year+"' or FIN_YEAR='"+fin_year_fut+"' )"+
		        		  "  GROUP BY Gpf_No, " +
		        		  "    type_trans " +
		        		  "  ) d " +
		        		  "ON a.gpf_no =d.gpfno3 " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT Gpf_No AS Gpfno4, " +
		        		  "    CASE " +
		        		  "      WHEN (Type_Trans='CR') " +
		        		  "      THEN NVL(SUM(Impound_Amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS Impreg_003cr, " +
		        		  "    CASE " +
		        		  "      WHEN (type_trans='DB') " +
		        		  "      THEN NVL(SUM(impound_amount),0) " +
		        		  "      ELSE 0 " +
		        		  "    END AS impreg_003DB " +
		        		  "  FROM Hrm_Gpf_Impound_Disbnew " +
		        		  "  WHERE ( Gpf_No     = "+gpfNo+" ) " +
		        		  "  AND (Ac_Head_Code  =391003 " +
		        		  "  AND impound_type   ='ImpReg' ) " +
		        		  "  AND ( FIN_YEAR='"+fin_year+"' or FIN_YEAR='"+fin_year_fut+"' )"+
		        		  "  GROUP BY Gpf_No, " +
		        		  "    type_trans " +
		        		  "  ) e " +
		        		  "ON A.Gpf_No = E.Gpfno4 " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT gpf_no    AS gpf, " +
		        		  "    CLOSING_BALANCE AS impReg_ob " +
		        		  "  FROM hrm_gpf_impound_cb " +
		        		  "  WHERE (Gpf_No      = "+gpfNo+") " +
		        		  "  AND Impound_Type_Id='ImpReg' " +
		        		  "  AND FINANCIAL_YEAR ='"+slip_fin_year+"' " +
		        		  "  ) f " +
		        		  "ON a.gpf_no=f.gpf " +
		        		  "LEFT OUTER JOIN " +
		        		  "  (SELECT gpf_no    AS gpf1, " +
		        		  "    CLOSING_BALANCE AS imp2003_ob " +
		        		  "  FROM Hrm_Gpf_Impound_Cb " +
		        		  "  WHERE ( gpf_no     = "+gpfNo+") " +
		        		  "  AND impound_type_id='Imp2003' " +
		        		  "  AND FINANCIAL_YEAR ='"+slip_fin_year+"' " +
		        		  "  )G " +
		        		  "ON a.gpf_no=g.gpf1"; 

		        		  System.out.println("sql1................."+sql1);       
		        		      	//	ps.setString(1,gpf_no);
		        		      	//	ps.setString(2,fin_year);
		        		      		rs=ps.executeQuery();
		        		      		     if(rs.next()) 
		        		      		     {
		        		      		  	   ImpReg=rs.getInt("IMPREG_OB");
		        		      		       impreg_cr=rs.getInt("IMPREG_CR");
		        		      		       impreg_db=rs.getInt("IMPREG_db");
		        		      		       fimpreg=rs.getInt("IMPREG_CB");
		        		      		       Imp2003=rs.getInt("IMP2003_OB");
		        		      		       fimp2003=rs.getInt("IMP2003_CB");; 
		        		      		     }    		     
		        		      		rs.close();
		        		      		ps.close();
		          
		        		      		xml+="<fimp2003>"+fimp2003+" </fimp2003>";
		          gpf_no=gpfNo+"";
		          
		          
		          
		          String slip[]=slip_fin_year.split("-");
		      	ac_year=Integer.parseInt(slip[1]);
		      	ac_month=3;
		      	
		      	java.util.Date d=new java.util.Date();
		        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        int curr_month=d.getMonth()+1;
		        int curr_year=d.getYear()+1900;
		      	
		      	do
		      	{
		      			if(ac_month==12)
		      			{
		      				ac_month=1;
		      				ac_year=ac_year+1;
		      			}
		      			else
		      				ac_month++;		
		      			//st=con.createStatement(ResultSet.SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		      			ps=con.prepareStatement("select sum(sub_amount+arr_amount) as sub_amount,rel_year,rel_month FROM HRM_GPF_SUBSCRIPTIONNEW_temp where gpf_no=? and process_flow_id='FR' and AC_MONTH=? and AC_YEAR=? group by rel_year, rel_month order by rel_year,rel_month", ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
		      			ps.setString(1,gpf_no);
		      			ps.setInt(2,ac_month);
		      			ps.setInt(3,ac_year);
		      			rs=ps.executeQuery();
		      			int i=0,size=0;
		      			if(rs.last())
		      				size=rs.getRow();
		      			if(size==0)
		      				size=1;
		      			sub_amount=new int[size];
		      			relmonth_year=new String[size];
		      		//	System.out.println("count="+size);
		      			try{
		      				rs.beforeFirst();
		      			     while(rs.next()){
		      			    	int subamt=rs.getInt("sub_amount");
		      			  	   	sub_amount[i]=subamt;
		      			  	  sub_total=sub_total+sub_amount[i];
		      			  	  if((months[rs.getInt("rel_month")-1]+"-"+rs.getInt("rel_year")).length()<5)
		      			  	 {
		      			  			ps1=con.prepareStatement("select Rel_Month,Rel_Year FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=?" );
		      						ps1.setString(1,gpf_no);
		      						ps1.setInt(2,ac_month);
		      						ps1.setInt(3,ac_year);
		      						rs1=ps1.executeQuery();
		      			    		if(rs1.next()) 
		      			     		{
		      			     			if((months[rs1.getInt("rel_month")-1]+"-"+rs1.getInt("rel_year")).length()<5)
		      			  					relmonth_year[i]="";
		      			  				else
		      			  					relmonth_year[i]=months[rs1.getInt("rel_month")-1]+"-"+rs1.getInt("rel_year");
		      			     		}
		      			     		else
		      			  					relmonth_year[i]="";
		      			  		}
		      			  	 	else{
		      			  	 		relmonth_year[i]=months[rs.getInt("rel_month")-1]+"-"+rs.getInt("rel_year");
		      			  			}
		      			  	   i++;			     
		      			     }
		      			     if(i==0){
		      			     ps1=con.prepareStatement("select Rel_Month,Rel_Year FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=?" );
		      						ps1.setString(1,gpf_no);
		      						ps1.setInt(2,ac_month);
		      						ps1.setInt(3,ac_year);
		      						rs1=ps1.executeQuery();
		      			    		if(rs1.next()) 
		      			     		{
		      			     			if((months[rs1.getInt("rel_month")-1]+"-"+rs1.getInt("rel_year")).length()<5)
		      			  					relmonth_year[i]="";
		      			  				else
		      			  					relmonth_year[i]=months[rs1.getInt("rel_month")-1]+"-"+rs1.getInt("rel_year");
		      			     		}
		      			     		else
		      			  					relmonth_year[i]="";
		      			  			i++;
		      			     }
		      			}
		      			catch(Exception e){
		      				System.out.println("Error in "+e);
		      			}
		      			
		      			//rs.close();
		      			//ps.close();
		      			//System.out.println("ref_amount------------"+arr_amount);
		      			ps=con.prepareStatement("select nvl(SUM(REF_AMOUNT),0) FROM HRM_GPF_SUBSCRIPTIONNEW_temp where gpf_no=? and AC_MONTH=? and AC_YEAR=?   and process_flow_id='FR' " );
		      			ps.setString(1,gpf_no);
		      			ps.setInt(2,ac_month);
		      			ps.setInt(3,ac_year);
		      			rs=ps.executeQuery();
		      			     if(rs.next()) 
		      			     {
		      			  	   ref_amount=rs.getInt(1);
		      			     
		      			     }
		      			//rs.close();
		      			//ps.close();
		      			ps=con.prepareStatement("select CASE WHEN type_trans='CR' and withdrwal_amount<>0 THEN 'CR' when type_trans='DB' and withdrwal_amount<>0 then 'DB'  when type_trans is null and withdrwal_amount<>0  then 'DB'  when type_trans is null and withdrwal_amount=' '  then ' '  ELSE ' ' END type_trans FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=? " );
		      			ps.setString(1,gpf_no);
		      			ps.setInt(2,ac_month);
		      			ps.setInt(3,ac_year);
		      			rs=ps.executeQuery();
		      			     if(rs.next()) 
		      			     {
		      			  	   type_trans_value=rs.getString(1);
		      			     
		      			     }
		      			//rs.close();
		      			//ps.close();		
		      			
		      			
		      			ps=con.prepareStatement("select nvl(SUM(withdrwal_amount),0)FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=? " );
		      			ps.setString(1,gpf_no);
		      			ps.setInt(2,ac_month);
		      			ps.setInt(3,ac_year);
		      			rs=ps.executeQuery();
		      			     if(rs.next()) 
		      			     {
		      			  	   with_amount=rs.getInt(1);
		      			     
		      			     }
		      			//rs.close();
		      			//ps.close();		
		      			
		      			
		      			ps=con.prepareStatement("select nvl(SUM(withdrwal_amount),0) FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=? and (type_trans is null or type_trans='DB') " );
		      			ps.setString(1,gpf_no);
		      			ps.setInt(2,ac_month);
		      			ps.setInt(3,ac_year);
		      			rs=ps.executeQuery();
		      			     if(rs.next()) 
		      			     {
		      			  	   with_amount_DB=rs.getInt(1);
		      			     
		      			     }
		      			//rs.close();
		      			//ps.close();		
		      			
		      			
		      			
		      			
		      			
		      				
		      			
		      			ps=con.prepareStatement("select nvl(SUM(withdrwal_amount),0)FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=? and type_trans='CR'" );
		      			ps.setString(1,gpf_no);
		      			ps.setInt(2,ac_month);
		      			ps.setInt(3,ac_year);
		      			rs=ps.executeQuery();
		      			     if(rs.next()) 
		      			     {
		      			  	   with_amount_CR=rs.getInt(1);
		      			     
		      			     }
		      			//rs.close();
		      			//ps.close();		
		      			
		      		
		      			
		      			
		      			month_year=months[ac_month-1]+"-"+ac_year;		
		      			//sub_total=sub_total+sub_amount;
		      			ref_total=ref_total+ref_amount;
		      			type_trans_value=type_trans_value;
		      			
		      				with_total_CR=with_total_CR+with_amount_CR;
		      				with_total_DB=with_total_DB+with_amount_DB;
		      				with_total=with_total_DB-with_total_CR;
		      			
		      				
		      			
		      			
		      				
		      					
		                        	
		      	}while(!(ac_month==curr_month&&ac_year==curr_year));
		      	
		          
		          
		          
		          
		          
		          
		          
		          
		          
		          
//		          
	          int gpfNum=gpfNo;
//		          int beagnYear = 0;
//		          int beagnYear1 = 0;
//		          int endYear = 0;
//		          int endYear1 = 0;
//		          String[] splitYear = slip_fin_year.split("-");
		         
		          beagnYear=Integer.parseInt(splitYear[0]);
		          beagnYear1=beagnYear+1;
		         
		          endYear=Integer.parseInt(splitYear[1]);
		          endYear1=endYear+1;
				 
				  
				 // String fin_year = beagnYear1+"-"+endYear1;
		          System.out.println("fin_year==="+fin_year);
		          String sql = "";
		          sql = "{call HRM_GPF_IMPOUND_BAL_VIEW_NEW(?,?,?,?)}";
					callableStatement = con.prepareCall(sql);          
		          callableStatement.setInt(1,gpfNum);
		          callableStatement.setString(2,fin_year);
		          callableStatement.setInt(3,beagnYear1);
		          callableStatement.setInt(4,endYear1);
		          callableStatement.executeUpdate();
		          ps=con.prepareStatement("SELECT GPF_NO, " +
		        		  "  FINANCIAL_YEAR, " +        		  
		        		  "  IMP2003_CR, " +
		        		  "  IMP2003_DB, " +
		        		  "  IMPREG_CR, " +
		        		  "  IMPREG_DB, " +
		        		  "  IMP2003_OB, " +
		        		  "  IMPREG_OB, " +
		        		  "  IMP2003_CB, " +
		        		  "  IMPREG_CB " +
		        		  "FROM HRM_GPF_IMPOUND_BAL_TMP " +
		        		  "WHERE GPF_NO      =? " +
		        		  "AND FINANCIAL_YEAR=?"
						);          
		    		ps.setString(1,gpf_no);
		    		ps.setString(2,fin_year);
		    		rs=ps.executeQuery();
		    		     if(rs.next()) 
		    		     {
		    		  	   ImpReg=rs.getInt("IMPREG_OB");
		    		  	   System.out.println("ImpReg="+ImpReg);
		    		       impreg_cr=rs.getInt("IMPREG_CR");
		    		       impreg_db=rs.getInt("IMPREG_db");
		    		       fimpreg=rs.getInt("IMPREG_CB");
		    		       Imp2003=rs.getInt("IMP2003_OB");
		    		       fimp2003=rs.getInt("IMP2003_CB");
		    		       IMP2003_CR=rs.getString("IMP2003_CR");
		    		       IMP2003_DB=rs.getString("IMP2003_DB");
		    		     }    		     
		    		//rs.close();
		    		//ps.close();
		    		
		    		//xml+="<Impound_Regular>"+ImpReg+" </Impound_Regular>";	          
		          
		          
		          
		          
		          
		          
		          ArrayList<String> impound=new ArrayList<String>();
		        String impamt=null;
		        String Query="SELECT NVL(a.IMP2003_CR,0) AS IMP2003_CR, " +
		        "  NVL(a.IMPREG_CR,0)       AS IMPREG_CR, " +
		        "  NVL(a.IMPREG_DB,0)       AS IMPREG_DB, " +
		        "  NVL(a.IMP2003_DB,0)      AS IMP2003_DB, " +

		        "  b.ac_month  as ac_month," +
		        "  b.type_trans  as type_trans,"+
		        "  b.impound_type  as impound_type,"+
		        "  b.ac_year  as ac_year" +
		        "FROM HRM_GPF_IMPOUND_BAL_TMP a, " +
		        "  HRM_GPF_impound_disbnew b " +
		        "WHERE a.gpf_no      =b.gpf_no  and b.Process_Flow_Id='FR' " +
		        "AND a.gpf_no        =? " +
		        "AND a.financial_year=?";
		        //System.out.println("Query"+Query);
		        			ps=con.prepareStatement("SELECT NVL(a.IMP2003_CR,0) AS IMP2003_CR, " +
		        "  NVL(a.IMPREG_CR,0)       AS IMPREG_CR, " +
		        "  NVL(a.IMPREG_DB,0)       AS IMPREG_DB, " +
		        "  NVL(a.IMP2003_DB,0)      AS IMP2003_DB, " +

		        "  b.ac_month  as ac_month," +
		        "  b.type_trans  as type_trans,"+
		        "  b.impound_type  as impound_type,"+
		        "  b.ac_year  as ac_year" +
		        " FROM HRM_GPF_IMPOUND_BAL_TMP a, " +
		        "  HRM_GPF_impound_disbnew b " +
		        "WHERE a.gpf_no      =b.gpf_no " +
		        "AND a.gpf_no        =? " +
		        "AND a.financial_year=?" );
		        			ps.setString(1,gpf_no);
		        			ps.setString(2,fin_year);
		        			
		        			rs=ps.executeQuery();
		        			     while(rs.next()) 
		        			     {
		        			    	         impound.add(months[rs.getInt("ac_month")-1]+"-"+rs.getInt("ac_year"));
		        					    	 IMPREG_CR=rs.getString("IMPREG_CR");
		        					    	 IMPREG_DB=rs.getString("IMPREG_DB");
		        					    	 
		        					    	
		        					    	 IMP2003_DB=rs.getString("IMP2003_DB");
		        					    	 IMP2003_CR=rs.getString("IMP2003_CR");
		        					       	 if(rs.getString("impound_type").equalsIgnoreCase("ImpReg"))
		        					    	 {
		        					       		//System.out.println("Test impreg");
		        							    		if(rs.getString("type_trans").equalsIgnoreCase("CR"))
		        							    			
		        							    			impound.add(IMPREG_CR);
		        							    		else
		        							    		{
		        							    			impamt="-"+IMPREG_DB;
		        							    			impound.add(impamt);
		        							    		}
		        							    	//	System.out.println("impreg");
		        							    		 impound.add("0");
		        					    	 }
		        					    	 else
		        					    	 {
		        					    		 impound.add("0");
		        					    		 if(rs.getString("impound_type").equalsIgnoreCase("Imp2003"))
		        						    	 {
		        					    		 //System.out.println("imp2003");			 
		        					    			   		if(rs.getString("type_trans").equalsIgnoreCase("CR"))
		        								    			impound.add(IMP2003_CR);
		        								    		else
		        								    		{
		        								    			impamt="-"+IMP2003_DB;
		        								    			impound.add(impamt);
		        								    		}
		        					    			   		//impound.add("0");
		        						    	 
		        						    	 }					    		 
		        					    			  
		        					    	 }
		        					    	 
		        					     
		        			     }
		        			//rs.close();
		        			//ps.close();
		        			
		        				int cnt=impound.size();
		        	for(int i=0; i<cnt; i++){
		        		System.out.println("get impound "+i+" "+impound.get(i));
		        	}
		        	System.out.println("cnt len "+cnt);
		          
		          
		          
		          v1=(close_bal+sub_total+ref_total)-with_total;
		          v2=fimpreg;
		          System.out.println("fimpreg="+fimpreg);
		          v3=fimp2003;
		          System.out.println("v1="+v1);
		          try
		          {
		          ps=con.prepareStatement("SELECT * " +
		          		"FROM " +
		          		"  (SELECT a.ho_jrnl_mst_id AS HO_JRNL_MST_ID, " +
		          		"    JOURNAL_REF_NO, " +
		          		"    REL_MONTH, " +
		          		"    REL_YEAR, " +
		          		"    AMOUNT, " +
		          		"    TRANS_TYPE, " +
		          		"    CATEGORY, " +
		          		"    SERIAL_NO " +
		          		"  FROM " +
		          		"    (SELECT ho_jrnl_mst_id, " +
		          		"      REL_MONTH, " +
		          		"      REL_YEAR, " +
		          		"      AMOUNT, " +
		          		"      TRANS_TYPE, " +
		          		"      CATEGORY, " +
		          		"      SERIAL_NO " +
		          		"    FROM hrm_gpf_missing_cr_db_con " +
		          		"    WHERE gpf_no                  =? " +
		          		"    AND INTEREST_CALCULATED_STATUS='Y' " +
		          		"    )a " +
		          		"  LEFT OUTER JOIN " +
		          		"    (SELECT ho_jrnl_mst_id, " +
		          		"      JOURNAL_REF_NO " +
		          		"    FROM hrm_gpf_missing_cr_db_mst " +
		          		"    WHERE PROCESS_FLOW_STATUS_ID='FR' " +
		          		"    )b " +
		          		"  ON a.ho_jrnl_mst_id=b.ho_jrnl_mst_id " +
		          		"  )aa " +
		          		"INNER JOIN " +
		          		"  (SELECT SUM(INTEREST_VALUE) AS miss_interest, " +
		          		"    serial_no " +
		          		"  FROM HRM_GPF_MISSING_INTEREST " +
		          		"  WHERE PROCESS_FLOW_STATUS_ID='FR' " +
		          		"  GROUP BY serial_no " +
		          		"  )bb " +
		          		"ON bb.serial_no=aa.serial_no" );
		          	ps.setString(1,gpf_no);
		          	rs=ps.executeQuery();
		          	     while(rs.next()) 
		          	     {
		          	    	 
		          	    	       miss_amt=rs.getInt("AMOUNT");
		          	    	     	 sum_interest=rs.getInt("miss_interest");
		          	    	     	System.out.println("CATEGORY1="+rs.getString("CATEGORY"));
		  	          	    	  System.out.println("TRANS_TYPE1="+rs.getString("TRANS_TYPE"));
		          	    	     
		          	    	  System.out.println("adfdaf"+miss_amt); 
		          
		          if(rs.getString("CATEGORY").equals("Regular")&&(rs.getString("TRANS_TYPE").equals("CR")))
			  	   {
			  		   v1=v1+miss_amt+sum_interest;
			  	   }
			  	   else if(rs.getString("CATEGORY").equals("Regular")&&(rs.getString("TRANS_TYPE").equals("DB")))
			  	   {
			  		   v1=v1-miss_amt-sum_interest;
			  	   }
			  	 else if(rs.getString("CATEGORY").equals("ImpReg")&&(rs.getString("TRANS_TYPE").equals("CR")))
			  	   {
			  		   v2=v2+miss_amt+sum_interest;
			  	   }
			  	else if(rs.getString("CATEGORY").equals("ImpReg")&&(rs.getString("TRANS_TYPE").equals("DB")))
			  	   {
			  		   v2=v2-miss_amt-sum_interest;
			  	   }
			  	else if(rs.getString("CATEGORY").equals("Imp2003")&&(rs.getString("TRANS_TYPE").equals("CR")))
			  	   {
			  		   v3=v3+miss_amt+sum_interest;
			  	   }
			  	else if(rs.getString("CATEGORY").equals("Imp2003")&&(rs.getString("TRANS_TYPE").equals("DB")))
			  	   {
			  		   v3=v3-miss_amt-sum_interest;
			  	   }
			  	 System.out.println("v1 now="+v1);  
			     
		}
		}
		catch(Exception e)
		{
			 System.out.println("miss"+e); 
		}

		          
		          
		          
		          
		          v1_excess=v1;
		          v2_excess=v2;
		          v3_excess=v3;
		          System.out.println("v1_excess="+v1_excess);
		         // ps.close();
		        //  rs.close();

		          try
		          {
		          ps=con.prepareStatement("SELECT * " +
		          		"FROM " +
		          		"  (SELECT a.ho_jrnl_mst_id AS HO_JRNL_MST_ID, " +
		          		"    JOURNAL_REF_NO, " +
		          		"    REL_MONTH, " +
		          		"    REL_YEAR, " +
		          		"    AMOUNT, " +
		          		"    TRANS_TYPE, " +
		          		"    CATEGORY, " +
		          		"    SERIAL_NO " +
		          		"  FROM " +
		          		"    (SELECT ho_jrnl_mst_id, " +
		          		"      REL_MONTH, " +
		          		"      REL_YEAR, " +
		          		"      AMOUNT, " +
		          		"      TRANS_TYPE, " +
		          		"      CATEGORY, " +
		          		"      SERIAL_NO " +
		          		"    FROM HRM_GPF_EXCESS_CR_DB_CON " +
		          		"    WHERE gpf_no=? " +
		          		"    )a " +
		          		"  LEFT OUTER JOIN " +
		          		"    (SELECT ho_jrnl_mst_id, " +
		          		"      JOURNAL_REF_NO " +
		          		"    FROM HRM_GPF_EXCESS_CR_DB_MST " +
		          		"    WHERE PROCESS_FLOW_STATUS_ID='FR' " +
		          		"    )b " +
		          		"  ON a.ho_jrnl_mst_id=b.ho_jrnl_mst_id " +
		          		"  )aa " +
		          		"INNER JOIN " +
		          		"  (SELECT SUM(INTEREST_VALUE) as excess_int, " +
		          		"    SERIAL_NO " +
		          		"  FROM HRM_GPF_excess_INTEREST " +
		          		"  WHERE PROCESS_FLOW_STATUS_ID='FR' " +
		          		"  GROUP BY SERIAL_NO " +
		          		"  )bb " +
		          		"ON aa.SERIAL_NO=bb.SERIAL_NO" );
		          	ps.setString(1,gpf_no);
		          	rs=ps.executeQuery();
		          	     while(rs.next()) 
		          	     {	    	 
		          	    	
		          	    	     	 sum_interest_excess=rs.getInt("excess_int");
		          	    	      
		          	    	    miss_amt_excess=rs.getInt("AMOUNT");
		          	    	    System.out.println("miss_amt_excess"+miss_amt_excess);
		          	    	    
		          	    	    System.out.println("CATEGORY="+rs.getString("CATEGORY"));
		          	    	  System.out.println("TRANS_TYPE="+rs.getString("TRANS_TYPE"));
		          
		          System.out.println(rs.getString("CATEGORY")+":"+rs.getString("TRANS_TYPE"));
			  	   if(rs.getString("CATEGORY").equals("Regular")&&(rs.getString("TRANS_TYPE").equals("CR")))
			  	   {
			  		   v1_excess=v1_excess-miss_amt_excess-sum_interest_excess;
			  	   }
			  	   else if(rs.getString("CATEGORY").equals("Regular")&&(rs.getString("TRANS_TYPE").equals("DB")))
			  	   {
			  		   v1_excess=v1_excess+miss_amt_excess+sum_interest_excess;
			  	   }
			  	 else if(rs.getString("CATEGORY").equals("ImpReg")&&(rs.getString("TRANS_TYPE").equals("CR")))
			  	   {
			  		   v2_excess=v2_excess-miss_amt_excess-sum_interest_excess;
			  	   }
			  	else if(rs.getString("CATEGORY").equals("ImpReg")&&(rs.getString("TRANS_TYPE").equals("DB")))
			  	   {
			  		   v2_excess=v2_excess+miss_amt_excess+sum_interest_excess;
			  	   }
			  	else if(rs.getString("CATEGORY").equals("Imp2003")&&(rs.getString("TRANS_TYPE").equals("CR")))
			  	   {
			  		   v3_excess=v3_excess-miss_amt_excess-sum_interest_excess;
			  	   }
			  	else if(rs.getString("CATEGORY").equals("Imp2003")&&(rs.getString("TRANS_TYPE").equals("DB")))
			  	   {
			  		   v3_excess=v3_excess+miss_amt_excess+sum_interest_excess;
			  	   }
			  	 System.out.println("v1_excess now="+v1_excess);  
			     }
		}
		catch(Exception e)
		{
			 System.out.print("exceptyion in excess"+ e);
		}
			//rs.close();
			//ps.close();
		          
				  /*xml+="<Subscription_Credit>"+(close_bal+sub_total+ref_total)+" </Subscription_Credit>";
				  xml+="<Subscription_Debit>"+with_total+" </Subscription_Debit>";
		          xml+="<Balance_Has_On_Date>"+v1_excess+" </Balance_Has_On_Date>";
		          xml+="<Imp_Balance_Has_On_Date>"+v2_excess+" </Imp_Balance_Has_On_Date>";*/
			//xml+="<Subscription_Credit>"+(sub_total+ref_total)+" </Subscription_Credit>";
			//  xml+="<Subscription_Debit>"+with_total+" </Subscription_Debit>"; */  Commented as on 5 jul 2013 by veronica
		              
		      		xml=xml+"<listYear>"+GpfSettlementNoteModel.xmlLoadListYear(date_reliev)+"</listYear>";
		          
				
				
		          
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				try {
					if(rs!=null){
						//rs.close();
					}				
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {				
					if(ps!=null){
						//ps.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			System.out.println("xmlGetBasicData()="+xml);
			
		
			
			
			
			
			
			
			
			
			   try
				 {
					 String sql1="SELECT office_id " +
							 "FROM HRM_EMP_SERVICE_DATA " +
							 "WHERE employee_id=? " +
							 "AND DATE_TO      = " +
							 "  (SELECT MAX(DATE_TO) FROM HRM_EMP_SERVICE_DATA WHERE employee_id=? " +
							 "  ) " +
							 "AND office_id IS NOT NULL";
					 String last_work_place="",last_work_office="";
					 int offid = 0 ;
					int count1=0;
					 
					 pss=con1.prepareStatement(sql1);
					 pss.setInt(1,gpfNo);
					 pss.setInt(2,gpfNo);
					 System.out.println("COUNTING===="+gpfNo);
					 rss=pss.executeQuery();
					 while(rss.next())
					 {
						
						 offid=+rss.getInt("office_id");
						// String Off_ID = "<Off_ID>"+rss.getInt("office_id")+"</Off_ID>";
//						 xml+="<last_work_place>"+rs.getString("CITY_TOWN_NAME")+"</last_work_place>";
//						 xml+="<last_work_office>"+rs.getString("OFFICE_NAME")+"</last_work_office>";
					 }
					 
					 System.out.println("COUNTING===="+offid);
					 
					 
					 
					 String ss2="SELECT * " +
							 "FROM( " +
							 "  (SELECT office_id " +
							 "  FROM HRM_EMP_SERVICE_DATA " +
							 "  WHERE employee_id=? " +
							 "  AND DATE_TO      = " +
							 "    (SELECT MAX(DATE_TO) FROM HRM_EMP_SERVICE_DATA WHERE employee_id=? " +
							 "    ) " +
							 "  AND office_id IS NOT NULL " +
							 "  )a " +
							 "RIGHT OUTER JOIN " +
							 "  (SELECT office_id AS Off_ID, " +
							 "    office_name, " +
							 "    city_town_name " +
							 "  FROM COM_MST_OFFICES " +
							 "  WHERE Office_Id=? " +
							 "  )b " +
							 "ON a.office_id=b.Off_ID)";
					 
					 pss2=con.prepareStatement(ss2);
					 pss2.setInt(1,gpfNo);
					 pss2.setInt(2,gpfNo);
					 pss2.setInt(3,offid);
					 rss2=pss2.executeQuery();
					 while(rss2.next())
					 {
						 xml+="<city_town_name>"+rss2.getString("city_town_name")+"</city_town_name>";
						 xml+="<office_name>"+rss2.getString("office_name")+"</office_name>";
						 
					 }
					 
					// xml+="<flags>success1</flags>";
					 
				 }
				 catch(Exception e)
				 {
					 xml+="<flags>failure1</flags>";
				 }
			     
//			     try{
//			    	 
//			    	 String legal_details="SELECT * FROM hrm_gpf_legal_details WHERE gpf_no=?";
//			    	 pss3=con.prepareStatement(legal_details);
//			    	 pss3.setInt(1,gpfNo);
//			    	 rss3=pss3.executeQuery();
//			    	 while(rss3.next())
//			    	 {
//			    		 
//			    	 }
//			     }catch(Exception e)
//			     {
//			    	 
//			     }
			
			
			   
			     String s1="select max(LEGAL_HIER_ID) as LEGAL_HIER_ID_1 from HRM_GPF_LEGAL_DETAILS Where GPF_NO=?";
		         System.out.println(s1);
		       
		        try {
					ps11=con.prepareStatement(s1);
					 ps11.setInt(1, gpfNo);
				        rs11=ps11.executeQuery();
				        int c2=0;
				        while(rs11.next())
				        {
				     	  
				     	   System.out.println(s1);
				     	   
				     	   c2=rs11.getInt("LEGAL_HIER_ID_1");
				     	   c2++;
				     	   //xml+="<LEGAL_HIER_ID>"+rs1.getInt("LEGAL_HIER_ID_1")+"</LEGAL_HIER_ID>";
				     	   
				        }
				        System.out.println("Count========>"+c);
				        xml+="<LEGAL_HIER_ID_1>"+c2+"</LEGAL_HIER_ID_1>";
				        System.out.println("Count========>"+xml);
					     
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			xml+="<flag>success1</flag>";
	     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml+="<flag>failure</flag>";
		}
	     
	     
	     
	     
	    
	     
//	     GpfAuthMstBean gpfAuthMstBean=null;
//	     int settlementStatus=0;
//	     System.out.println("GPF===="+gpfNo);
//	   	     
//	     
//	     
//	     
//	     
//	     
//	     String tempStrCheck=GpfAuthMstModel.xmlEmployee(gpfNo);
//	     System.out.println("tempStrCheck............"+tempStrCheck);
//	    // System.out.println("tempStrCheck.length()==============>"+tempStrCheck.length());
//	     if(tempStrCheck.length()>10)
//	    	 gpfValidity=true;
//	     
//	     if(gpfIntegerCheck && gpfValidity){
//	     	xml=xml+"<flag>success</flag>";
//	     //	System.out.println("GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()=======>"+GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo());
//	     	if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()==0){
//	     		System.out.println("GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()=======>"+GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo());
//	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
//	     		xml+=GpfAuthMstModel.xmlFirstRelieveDetails(gpfNo);
//	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
//	     		xml=xml+"<status>GpfNotExist</status>";	     		
//	     	}
//	     	else if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("CR")
//	     			|| GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("ED") ){
//	     		gpfAuthMstBean=GpfAuthMstModel.getGpfAuthMstBean(gpfNo);
//	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
//	     		xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);	
//	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
//	     		xml=xml+"<status>GpfEdited</status>";
//	     	}
//	     	else if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("FR")){
//	     		gpfAuthMstBean=GpfAuthMstModel.getGpfAuthMstBean(gpfNo);
//	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
//	     		xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);	
//	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
//	     		xml=xml+"<status>GpfValidated</status>";
//	     	}
//	     	
//	     }else if(!gpfIntegerCheck){
//	     	xml=xml+"<status>inValidInteger</status>";
//	     	xml=xml+"<flag>success</flag>";
//	     }else if(!gpfValidity){
//		     	xml=xml+"<status>inValidGpf</status>";
//		     	xml=xml+"<flag>success</flag>";
//		 }
	     
	     
	     
 
	   
	     
	     
	     
	  
	     
	     xml=xml+"</response>";
	     System.out.println(xml);
	 }
	  
	 
	  
 if(command.equalsIgnoreCase("Add")) {
	 System.out.println("With in the Addd function...");
		int gpfNo=0,emp_id=0;	
		String prefix = null,pro_officer_name = null,suffix= null,pro_desingnation= null,letter_no= null,letter_date= null,jur_off= null,jur_add1= null,jur_add2= null,jur_add3= null;
		int jur_pincode = 0;
		
	 	String relieve_status="";
	 	String relieve_date="";
	 	String intrest_upto= null,intrest_upto1= null,subject= null,reference= null;
	 	int Regular_Balance = 0,regu_acc_unit_code= 0,txtImpoundBal= 0,imp_acc_unit_code= 0,txtImpound2003bal= 0,imp_2003= 0,DA_Amt= 0,da_acc_unit= 0;
	 	String dth_Legal_person= null,dth_add1= null,dth_add2= null,dth_add3= null;
	 	int pin= 0,percent=0;
	 	String any_comments= null,for_off1= null,for_design1= null,copy= null;
	 	String int_tobe_calc_month="";
	 	String int_tobe_calc_year="";
	 	String int_tobe_calc_date="";
	 	String dth_Relation = null,dth_date2 = null;
	 	int dth_Legal_person_ID = 0;
	 	int count=0;
	 	
	 	try {
	 		
	 		gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());	
	 		emp_id=Integer.parseInt(request.getParameter("emp_id").trim());	
	 		prefix=request.getParameter("prefix").trim();
	 		pro_officer_name=request.getParameter("pro_officer_name").trim();
	 		suffix=request.getParameter("suffix").trim();
	 		pro_desingnation=request.getParameter("pro_desingnation").trim();
	 		letter_no=request.getParameter("letter_no").trim();
	 		letter_date=request.getParameter("letter_date").trim();
	 		jur_off=request.getParameter("jur_off").trim();
	 		jur_add1=request.getParameter("jur_add1").trim();
	 		jur_add2=request.getParameter("jur_add2").trim();
	 		jur_add3=request.getParameter("jur_add3").trim();
	 		jur_pincode=Integer.parseInt(request.getParameter("jur_pincode").trim());	
	 		String dth_date22="10/10/2011";
	 		count=Integer.parseInt(request.getParameter("count"));
	 		System.out.println("counting values is-----"+count);
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_status=request.getParameter("relieve_status").trim();
	 		System.out.println("Rel Status==="+relieve_status);
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_date=request.getParameter("relieve_date").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		
	 		intrest_upto1=request.getParameter("intrest_upto").trim();
	 		String Arr_upto[]=intrest_upto1.split("-");
	 		int year=Integer.parseInt(Arr_upto[0]);
	 		int month=Integer.parseInt(Arr_upto[1]);
	 		int days=0;
	 		
	 		if(year%4==0)
	 		{
	 			if((month==1) || (month==3) || (month==5) || (month==7) || (month==8) || (month==10) || (month==12))
	 			{
	 				days=31;
	 			}
	 			else if(month==2)
	 			{
	 				days=29;
	 			}
	 			else
	 			{
	 				days=30;
	 			}
	 			
	 		}
	 		else
	 		{
	 			if((month==1) || (month==3) || (month==5) || (month==7) || (month==8) || (month==10) || (month==12))
	 			{
	 				days=31;
	 			}
	 			else if(month==2)
	 			{
	 				days=28;
	 			}
	 			else
	 			{
	 				days=30;
	 			}
	 		}
	 		
	 		 intrest_upto=days+"/"+month+"/"+year;
	 		System.out.println("intrest_upto............"+intrest_upto);
	 		subject=request.getParameter("subject").trim();
	 		reference=request.getParameter("reference").trim();
	 		System.out.println("reference............"+reference);
	 		copy=request.getParameter("copy");
	 		System.out.println("copy............"+copy);
	 		Regular_Balance=Integer.parseInt(request.getParameter("Regular_Balance").trim());
	 		regu_acc_unit_code=Integer.parseInt(request.getParameter("regu_acc_unit_code").trim());
	 		txtImpoundBal=Integer.parseInt(request.getParameter("txtImpoundBal").trim());
	 		imp_acc_unit_code=Integer.parseInt(request.getParameter("imp_acc_unit_code").trim());
	 		txtImpound2003bal=Integer.parseInt(request.getParameter("txtImpound2003bal").trim());
	 		imp_2003=Integer.parseInt(request.getParameter("imp_2003").trim());
	 		DA_Amt=Integer.parseInt(request.getParameter("DA_Amt").trim());
	 		da_acc_unit=Integer.parseInt(request.getParameter("da_acc_unit").trim());
	 		
	 		
	 		System.out.println("after reference............");
	 		System.out.println("Rel Status==="+relieve_status);
	 		
	 		System.out.println("after pin............");
	 		
	 		for_off1=request.getParameter("for_of");
	 		for_design1=request.getParameter("for_design1");
	 		copy=request.getParameter("copy").trim();
	 		any_comments=request.getParameter("any_comments").trim();
	 		
	 		System.out.println("any_comments==="+any_comments+"for_off1==="+for_off1+"for_design1==="+for_design1+"copy===="+copy);
	 		//any_comments=request.getParameter("any_comments").trim();
	 		//for_off1=request.getParameter("for_off1").trim();
	 		//for_design1=request.getParameter("for_design1").trim();
	 		
	 		
	 		if(relieve_status.equals("DTH"))
	 		{
	 			System.out.println("any_comments==="+any_comments);
	 			dth_Legal_person_ID=Integer.parseInt(request.getParameter("dth_Legal_person_ID"));
	 		dth_Legal_person=request.getParameter("dth_Legal_person").trim();
	 		//dth_Legal_person=null;
	 		dth_Relation=request.getParameter("dth_Relation").trim();
	 		//dth_Relation=null;
	 		dth_date2=request.getParameter("dth_date2").trim();
	 		//dth_date2=null;
	 		dth_add1=request.getParameter("dth_add1").trim();
	 		//dth_add1=null;
	 		dth_add2=request.getParameter("dth_add2").trim();
	 		System.out.println("after the ADDDDRESS 2..........."+dth_add2);
	 		percent=Integer.parseInt(request.getParameter("percent"));
	 		System.out.println("after the percentage............"+percent);
	 		copy=request.getParameter("copy");
	 		System.out.println("after the copy............"+copy);
	 		//dth_add2=null;
	 		dth_add3=request.getParameter("dth_add3").trim();	
	 		//dth_add3=null;
	 		pin=Integer.parseInt(request.getParameter("pin").trim());
	 		//System.out.println("any_comments pin pin==="+pin);
	 		//percent=Integer.parseInt(request.getParameter("percent"));
	 		//System.out.println("after the percentage............"+percent);
	 		//copy=request.getParameter("copy");
	 		
//System.out.println("after pin............"+pin);
//System.out.println("after the percentage............"+percent);
	 		
	 	}
	 		System.out.println("any_comments==="+any_comments+"for_off1==="+for_off1+"for_design1==="+for_design1+"copy===="+copy);
	 		
	 		int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	     
	 	int_tobe_calc_date="";
	     	
	     xml="<response><command>add</command>";
	     xml=xml+"<status>add</status>";
	     
	     try {
	    	
	    	System.out.println("inside try"); 
	
		 
		 
     if(relieve_status.equals("DTH"))
	 {	 
    	 System.out.println("DTH_COUNT===="+count);
	   if(count!=0)
	  	 {  	 
			intrest_upto1=request.getParameter("intrest_upto").trim();
	 		String Arr_upto[]=intrest_upto1.split("-");
	 		String s1=Arr_upto[0].trim();
	 		String s2=Arr_upto[1].trim();
	 		int year=Integer.parseInt(s1);
	 		int month=Integer.parseInt(s2);
	 		int days=0;
	 		
	 		if(year%4==0)
	 		{
	 			if((month==1) || (month==3) || (month==5) || (month==7) || (month==8) || (month==10) || (month==12))
	 			{
	 				days=31;
	 			}
	 			else if(month==2)
	 			{
	 				days=29;
	 			}
	 			else
	 			{
	 				days=30;
	 			}
	 			
	 		}
	 		else
	 		{
	 			if((month==1) || (month==3) || (month==5) || (month==7) || (month==8) || (month==10) || (month==12))
	 			{
	 				days=31;
	 			}
	 			else if(month==2)
	 			{
	 				days=28;
	 			}
	 			else
	 			{
	 				days=30;
	 			}
	 		}
	 		 intrest_upto=days+"/"+month+"/"+year;
	 		System.out.println("intrest_upto............"+intrest_upto);
	 		subject=request.getParameter("subject").trim();
	 		reference=request.getParameter("reference").trim();
	 		System.out.println("reference............"+reference);
	 		copy=request.getParameter("copy");
	 		System.out.println("copy............"+copy);
	 		Regular_Balance=Integer.parseInt(request.getParameter("Regular_Balance").trim());
	 		regu_acc_unit_code=Integer.parseInt(request.getParameter("regu_acc_unit_code").trim());
	 		txtImpoundBal=Integer.parseInt(request.getParameter("txtImpoundBal").trim());
	 		imp_acc_unit_code=Integer.parseInt(request.getParameter("imp_acc_unit_code").trim());
	 		txtImpound2003bal=Integer.parseInt(request.getParameter("txtImpound2003bal").trim());
	 		imp_2003=Integer.parseInt(request.getParameter("imp_2003").trim());
	 		DA_Amt=Integer.parseInt(request.getParameter("DA_Amt").trim());
	 		da_acc_unit=Integer.parseInt(request.getParameter("da_acc_unit").trim());	
			try{
	    		 ps=con.prepareStatement("INSERT " +
	    				 "INTO HRM_GPF_FINAL_SETTLEMENT_PROC " +
	    				 "  ( " +
	    				 "    GPF_NO, " +
	    				 "    EMPLOYEE_ID, " +
	    				 "    PRESID_OFFICER_PREFIX, " +
	    				 "    PRESID_OFFICER_NAME, " +
	    				 "    PRESID_OFFICER_SUFFIX, " +
	    				 "    PRESID_OFFICER_DESIG, " +
	    				 "    AUTH_LTR_NO, " +
	    				 "    AUTH_LTR_DATE, " +
	    				 "    JURISDICTION_OFFICER_DESIG, " +
	    				 "    JURISDICTION_OFFICE_ADD1, " +
	    				 "    JURISDICTION_OFFICE_ADD2, " +
	    				 "    JURISDICTION_OFFICE_ADD3, " +
	    				 "    JURISDICTION_OFFICE_PIN, " +
	    				 "    RELIEVAL_REASON_ID, " +
	    				 "    RELIEVAL_DATE, " +
	    				 "    INTEREST_UPTO, " +
	    				 "    SUBJECT, " +
	    				 "    REFERENCE, " +
	    				 "    GPF_REG_BAL_AMT, " +
	    				 "    GPF_REG_BAL_AC_HEAD, " +
	    				 "    GPF_IMP_BAL_AMT, " +
	    				 "    GPF_IMP_BAL_AC_HEAD, " +
	    				 "    GPF_IMP03_BAL_AMT, " +
	    				 "    GPF_IMP03_BAL_AC_HEAD, " +
	    				 "    GPF_1DA_BAL_AMT, " +
	    				 "    GPF_1DA_BAL_AC_HEAD, " +
	    				 "    LEGAL_HEIR_NAME, " +
	    				 "    LEGAL_HEIR_ADD1, " +
	    				 "    LEGAL_HEIR_ADD2, " +
	    				 "    LEGAL_HEIR_ADD3, " +
	    				 "    LEGAL_HEIR_PIN, " +
	    				 "    ADDL_CONDITIONS, " +
	    				 "    FOWARD_OFFICER_NAME, " +
	    				 "    FOWARD_OFFICER_DESIG, " +
	    				 "    COPY_TO, " +
	    				 "    UPDATED_BY_USER_ID, " +
	    				 "    UPDATED_DATE, " +
	    				 "    PROCESS_FLOW_STATUS_ID, " +
	    				 "    LIC_DATE, " +
	    				 "    RELATIONSHIP " +
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
	    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
	    				 "    ?, " +
	    				 "    ?, " +
	    				 "    ?, " +
	    				 "    ?, " +
	    				 "    ?, " +
	    				 "    ?, " +
	    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
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
	    				 "    ?, " +
	    				 "    clock_timestamp(), " +
	    				 "    'CR', " +
	    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
	    				 "    ? " +
	    				 "  )"
);
			ps.setInt(1,gpfNo);
			ps.setInt(2,emp_id);
			ps.setString(3, prefix);
			ps.setString(4, pro_officer_name);
			ps.setString(5, suffix);
			ps.setString(6, pro_desingnation);
			
			ps.setString(7,letter_no);
			ps.setString(8, letter_date);
			
			ps.setString(9, jur_off);
			ps.setString(10, jur_add1);
			ps.setString(11, jur_add2);
			ps.setString(12, jur_add3);
			ps.setInt(13, jur_pincode);
			
			ps.setString(14, relieve_status);
			ps.setString(15, relieve_date);
			ps.setString(16, intrest_upto);
			
			ps.setString(17, subject);
			ps.setString(18, reference);
			
			ps.setInt(19,Regular_Balance);		
			ps.setInt(20,regu_acc_unit_code);
			ps.setInt(21,txtImpoundBal);
			ps.setInt(22,imp_acc_unit_code);
			ps.setInt(23,txtImpound2003bal);
			ps.setInt(24,imp_2003);
			ps.setInt(25,DA_Amt);
			ps.setInt(26,da_acc_unit);
			
			
			ps.setString(27, dth_Legal_person);
			ps.setString(28, dth_add1);
			ps.setString(29, dth_add2);
			ps.setString(30, dth_add3);
			ps.setInt(31,pin);
			
			ps.setString(32, any_comments);
			ps.setString(33, for_off1);
			ps.setString(34, for_design1);
			ps.setString(35, copy);
			
			ps.setString(36, updatedby);
		//	ps.setTimestamp(37,"clock_timestamp()");	
			
			ps.setString(37,dth_date2);
			ps.setString(38,dth_Relation);
			
			ps.executeUpdate();
			count++;
			}catch(Exception e)
			{
				
				System.out.println(e);
			}
	   }
	   if(count==0)
	   {
		   intrest_upto1=request.getParameter("intrest_upto").trim();
	 		String Arr_upto[]=intrest_upto1.split("-");
	 		String s1=Arr_upto[0].trim();
	 		String s2=Arr_upto[1].trim();
	 		int year=Integer.parseInt(s1);
	 		int month=Integer.parseInt(s2);
	 		int days=0;
	 		
	 		if(year%4==0)
	 		{
	 			if((month==1) || (month==3) || (month==5) || (month==7) || (month==8) || (month==10) || (month==12))
	 			{
	 				days=31;
	 			}
	 			else if(month==2)
	 			{
	 				days=29;
	 			}
	 			else
	 			{
	 				days=30;
	 			}
	 			
	 		}
	 		else
	 		{
	 			if((month==1) || (month==3) || (month==5) || (month==7) || (month==8) || (month==10) || (month==12))
	 			{
	 				days=31;
	 			}
	 			else if(month==2)
	 			{
	 				days=28;
	 			}
	 			else
	 			{
	 				days=30;
	 			}
	 		}
	 		 intrest_upto=days+"/"+month+"/"+year;
	 		System.out.println("intrest_upto............"+intrest_upto);
	 		subject=request.getParameter("subject").trim();
	 		reference=request.getParameter("reference").trim();
	 		System.out.println("reference............"+reference);
	 		copy=request.getParameter("copy");
	 		System.out.println("copy............"+copy);
	 		Regular_Balance=Integer.parseInt(request.getParameter("Regular_Balance").trim());
	 		regu_acc_unit_code=Integer.parseInt(request.getParameter("regu_acc_unit_code").trim());
	 		txtImpoundBal=Integer.parseInt(request.getParameter("txtImpoundBal").trim());
	 		imp_acc_unit_code=Integer.parseInt(request.getParameter("imp_acc_unit_code").trim());
	 		txtImpound2003bal=Integer.parseInt(request.getParameter("txtImpound2003bal").trim());
	 		imp_2003=Integer.parseInt(request.getParameter("imp_2003").trim());
	 		DA_Amt=Integer.parseInt(request.getParameter("DA_Amt").trim());
	 		da_acc_unit=Integer.parseInt(request.getParameter("da_acc_unit").trim());
   		
					try{
			    		 ps=con.prepareStatement("INSERT " +
			    				 "INTO HRM_GPF_FINAL_SETTLEMENT_PROC " +
			    				 "  ( " +
			    				 "    GPF_NO, " +
			    				 "    EMPLOYEE_ID, " +
			    				 "    PRESID_OFFICER_PREFIX, " +
			    				 "    PRESID_OFFICER_NAME, " +
			    				 "    PRESID_OFFICER_SUFFIX, " +
			    				 "    PRESID_OFFICER_DESIG, " +
			    				 "    AUTH_LTR_NO, " +
			    				 "    AUTH_LTR_DATE, " +
			    				 "    JURISDICTION_OFFICER_DESIG, " +
			    				 "    JURISDICTION_OFFICE_ADD1, " +
			    				 "    JURISDICTION_OFFICE_ADD2, " +
			    				 "    JURISDICTION_OFFICE_ADD3, " +
			    				 "    JURISDICTION_OFFICE_PIN, " +
			    				 "    RELIEVAL_REASON_ID, " +
			    				 "    RELIEVAL_DATE, " +
			    				 "    INTEREST_UPTO, " +
			    				 "    SUBJECT, " +
			    				 "    REFERENCE, " +
			    				 "    GPF_REG_BAL_AMT, " +
			    				 "    GPF_REG_BAL_AC_HEAD, " +
			    				 "    GPF_IMP_BAL_AMT, " +
			    				 "    GPF_IMP_BAL_AC_HEAD, " +
			    				 "    GPF_IMP03_BAL_AMT, " +
			    				 "    GPF_IMP03_BAL_AC_HEAD, " +
			    				 "    GPF_1DA_BAL_AMT, " +
			    				 "    GPF_1DA_BAL_AC_HEAD, " +
			    				 "    LEGAL_HEIR_NAME, " +
			    				 "    LEGAL_HEIR_ADD1, " +
			    				 "    LEGAL_HEIR_ADD2, " +
			    				 "    LEGAL_HEIR_ADD3, " +
			    				 "    LEGAL_HEIR_PIN, " +
			    				 "    ADDL_CONDITIONS, " +
			    				 "    FOWARD_OFFICER_NAME, " +
			    				 "    FOWARD_OFFICER_DESIG, " +
			    				 "    COPY_TO, " +
			    				 "    UPDATED_BY_USER_ID, " +
			    				 "    UPDATED_DATE, " +
			    				 "    PROCESS_FLOW_STATUS_ID, " +
			    				 "    LIC_DATE, " +
			    				 "    RELATIONSHIP " +
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
			    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
			    				 "    ?, " +
			    				 "    ?, " +
			    				 "    ?, " +
			    				 "    ?, " +
			    				 "    ?, " +
			    				 "    ?, " +
			    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
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
			    				 "    ?, " +
			    				 "    clock_timestamp(), " +
			    				 "    'CR', " +
			    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
			    				 "    ? " +
			    				 "  )"
		);
					ps.setInt(1,gpfNo);
					ps.setInt(2,emp_id);
					ps.setString(3, prefix);
					ps.setString(4, pro_officer_name);
					ps.setString(5, suffix);
					ps.setString(6, pro_desingnation);
					
					ps.setString(7,letter_no);
					ps.setString(8, letter_date);
					
					ps.setString(9, jur_off);
					ps.setString(10, jur_add1);
					ps.setString(11, jur_add2);
					ps.setString(12, jur_add3);
					ps.setInt(13, jur_pincode);
					
					ps.setString(14, relieve_status);
					ps.setString(15, relieve_date);
					ps.setString(16, intrest_upto);
					
					ps.setString(17, subject);
					ps.setString(18, reference);
					
					ps.setInt(19,Regular_Balance);		
					ps.setInt(20,regu_acc_unit_code);
					ps.setInt(21,txtImpoundBal);
					ps.setInt(22,imp_acc_unit_code);
					ps.setInt(23,txtImpound2003bal);
					ps.setInt(24,imp_2003);
					ps.setInt(25,DA_Amt);
					ps.setInt(26,da_acc_unit);
					
					
					ps.setString(27, dth_Legal_person);
					ps.setString(28, dth_add1);
					ps.setString(29, dth_add2);
					ps.setString(30, dth_add3);
					ps.setInt(31,pin);
					
					ps.setString(32, any_comments);
					ps.setString(33, for_off1);
					ps.setString(34, for_design1);
					ps.setString(35, copy);
					
					ps.setString(36, updatedby);
				//	ps.setTimestamp(37,"clock_timestamp()");	
					
					ps.setString(37,dth_date2);
					ps.setString(38,dth_Relation);
					
					ps.executeUpdate();
					count++;
					}catch(Exception e)
					{
						
						System.out.println(e);
					}
					if(relieve_status.equals("DTH"))
					{
						System.out.println("any_comments==="+any_comments);
			 			dth_Legal_person_ID=Integer.parseInt(request.getParameter("dth_Legal_person_ID"));
			 		dth_Legal_person=request.getParameter("dth_Legal_person").trim();
			 		//dth_Legal_person=null;
			 		dth_Relation=request.getParameter("dth_Relation").trim();
			 		//dth_Relation=null;
			 		dth_date2=request.getParameter("dth_date2").trim();
			 		//dth_date2=null;
			 		dth_add1=request.getParameter("dth_add1").trim();
			 		//dth_add1=null;
			 		dth_add2=request.getParameter("dth_add2").trim();
			 		System.out.println("after the ADDDDRESS 2..........."+dth_add2);
			 		percent=Integer.parseInt(request.getParameter("percent"));
			 		System.out.println("after the percentage............"+percent);
			 		copy=request.getParameter("copy");
			 		System.out.println("after the copy............"+copy);
			 		//dth_add2=null;
			 		dth_add3=request.getParameter("dth_add3").trim();	
			 		//dth_add3=null;
			 		pin=Integer.parseInt(request.getParameter("pin").trim());
						   
						pss=con.prepareStatement("INSERT " +
								"INTO HRM_GPF_LEGAL_DETAILS " +
								"  ( " +
								"    GPF_NO, " +
								"    EMPLOYEE_ID, " +
								"    LEGAL_HIER_ID, " +
								"    LEGAL_HEIR_NAME, " +
								"    RELATIONSHIP, " +
								"    LIC_DATE, " +
								"    LEGAL_HEIR_ADD1, " +
								"    LEGAL_HEIR_ADD2, " +
								"    LEGAL_HEIR_ADD3, " +
								"    LEGAL_HEIR_PIN, " +
								"    LEGAL_PERCENT, " +
								"    COPY_TO " +
								"  ) " +
								"  VALUES " +
								"  ( " +
								"    ?, " +
								"    ?, " +
								"    ?, " +
								"    ?, " +
								"    ?, " +
								"    TO_DATE(?,'DD/MM/YYYY'), " +
								"    ?, " +
								"    ?, " +
								"    ?, " +
								"    ?, " +
								"    ?, " +
								"    ? " +
								"  )"
			);
						
						pss.setInt(1,gpfNo);
						pss.setInt(2,emp_id);
						pss.setInt(3, dth_Legal_person_ID);
						pss.setString(4, dth_Legal_person);
						pss.setString(5,dth_Relation);
						pss.setString(6,dth_date2);
						
						pss.setString(7, dth_add1);
						pss.setString(8, dth_add2);
						pss.setString(9, dth_add3);
						pss.setInt(10,pin);
						pss.setInt(11,percent);
						pss.setString(12,copy);
						
						
						System.out.println("GPF NO=="+gpfNo);
						System.out.println("emp_id=="+emp_id);
						System.out.println("dth_Legal_person_ID=="+dth_Legal_person_ID);
						System.out.println("dth_Legal_person=="+dth_Legal_person);
						System.out.println("dth_Relation=="+dth_Relation);
						System.out.println("dth_date2=="+dth_date2);
						System.out.println("dth_add1=="+dth_add1);
						System.out.println("dth_add2=="+dth_add2);
						System.out.println("dth_add3=="+dth_add3);
						System.out.println("pin======="+pin);
						
						
						pss.executeUpdate();
					}
	   }
	}
		 
		 if(relieve_status.equals("VRS") || relieve_status.equals("SAN") || relieve_status.equals("MEV") || relieve_status.equals("CMR"))
	 		{
			 intrest_upto1=request.getParameter("intrest_upto").trim();
			 
			 System.out.println("intrest_upto............"+intrest_upto1);
		 		String Arr_upto[]=intrest_upto1.split("-");
		 		
		 		String s1=Arr_upto[0].trim();
		 		String s2=Arr_upto[1].trim();
		 		int year=Integer.parseInt(s1);
		 		int month=Integer.parseInt(s2);
		 		int days=0;
		 		
		 		if(year%4==0)
		 		{
		 			if((month==1) || (month==3) || (month==5) || (month==7) || (month==8) || (month==10) || (month==12))
		 			{
		 				days=31;
		 			}
		 			else if(month==2)
		 			{
		 				days=29;
		 			}
		 			else
		 			{
		 				days=30;
		 			}
		 			
		 		}
		 		else
		 		{
		 			if((month==1) || (month==3) || (month==5) || (month==7) || (month==8) || (month==10) || (month==12))
		 			{
		 				days=31;
		 			}
		 			else if(month==2)
		 			{
		 				days=28;
		 			}
		 			else
		 			{
		 				days=30;
		 			}
		 		}
		 		
		 		 intrest_upto=days+"/"+month+"/"+year;
		 		System.out.println("intrest_upto............"+intrest_upto);
			 subject=request.getParameter("subject").trim();
		 		reference=request.getParameter("reference").trim();
		 		System.out.println("reference............"+reference);
		 		copy=request.getParameter("copy");
		 		System.out.println("copy............"+copy);
			 Regular_Balance=Integer.parseInt(request.getParameter("Regular_Balance").trim());
		 		regu_acc_unit_code=Integer.parseInt(request.getParameter("regu_acc_unit_code").trim());
		 		txtImpoundBal=Integer.parseInt(request.getParameter("txtImpoundBal").trim());
		 		imp_acc_unit_code=Integer.parseInt(request.getParameter("imp_acc_unit_code").trim());
		 		txtImpound2003bal=Integer.parseInt(request.getParameter("txtImpound2003bal").trim());
		 		imp_2003=Integer.parseInt(request.getParameter("imp_2003").trim());
		 		DA_Amt=Integer.parseInt(request.getParameter("DA_Amt").trim());
		 		da_acc_unit=Integer.parseInt(request.getParameter("da_acc_unit").trim());
			 System.out.println("VRS or SAN counts===="+count);
		  	 
			 System.out.println("Regular_Balance ............"+Regular_Balance);
		 		System.out.println("txtImpoundBal ............"+txtImpoundBal);
		 		System.out.println("txtImpound2003bal ............"+txtImpound2003bal);
				try{
		    		 ps=con.prepareStatement("INSERT " +
		    				 "INTO HRM_GPF_FINAL_SETTLEMENT_PROC " +
		    				 "  ( " +
		    				 "    GPF_NO, " +
		    				 "    EMPLOYEE_ID, " +
		    				 "    PRESID_OFFICER_PREFIX, " +
		    				 "    PRESID_OFFICER_NAME, " +
		    				 "    PRESID_OFFICER_SUFFIX, " +
		    				 "    PRESID_OFFICER_DESIG, " +
		    				 "    AUTH_LTR_NO, " +
		    				 "    AUTH_LTR_DATE, " +
		    				 "    JURISDICTION_OFFICER_DESIG, " +
		    				 "    JURISDICTION_OFFICE_ADD1, " +
		    				 "    JURISDICTION_OFFICE_ADD2, " +
		    				 "    JURISDICTION_OFFICE_ADD3, " +
		    				 "    JURISDICTION_OFFICE_PIN, " +
		    				 "    RELIEVAL_REASON_ID, " +
		    				 "    RELIEVAL_DATE, " +
		    				 "    INTEREST_UPTO, " +
		    				 "    SUBJECT, " +
		    				 "    REFERENCE, " +
		    				 "    GPF_REG_BAL_AMT, " +
		    				 "    GPF_REG_BAL_AC_HEAD, " +
		    				 "    GPF_IMP_BAL_AMT, " +
		    				 "    GPF_IMP_BAL_AC_HEAD, " +
		    				 "    GPF_IMP03_BAL_AMT, " +
		    				 "    GPF_IMP03_BAL_AC_HEAD, " +
		    				 "    GPF_1DA_BAL_AMT, " +
		    				 "    GPF_1DA_BAL_AC_HEAD, " +
		    				 "    LEGAL_HEIR_NAME, " +
		    				 "    LEGAL_HEIR_ADD1, " +
		    				 "    LEGAL_HEIR_ADD2, " +
		    				 "    LEGAL_HEIR_ADD3, " +
		    				 "    LEGAL_HEIR_PIN, " +
		    				 "    ADDL_CONDITIONS, " +
		    				 "    FOWARD_OFFICER_NAME, " +
		    				 "    FOWARD_OFFICER_DESIG, " +
		    				 "    COPY_TO, " +
		    				 "    UPDATED_BY_USER_ID, " +
		    				 "    UPDATED_DATE, " +
		    				 "    PROCESS_FLOW_STATUS_ID, " +
		    				 "    LIC_DATE, " +
		    				 "    RELATIONSHIP " +
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
		    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
		    				 "    ?, " +
		    				 "    ?, " +
		    				 "    ?, " +
		    				 "    ?, " +
		    				 "    ?, " +
		    				 "    ?, " +
		    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
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
		    				 "    ?, " +
		    				 "    clock_timestamp(), " +
		    				 "    'CR', " +
		    				 "    TO_DATE(?,'DD/MM/YYYY'), " +
		    				 "    ? " +
		    				 "  )"
	);
				ps.setInt(1,gpfNo);
				ps.setInt(2,emp_id);
				ps.setString(3, prefix);
				ps.setString(4, pro_officer_name);
				ps.setString(5, suffix);
				ps.setString(6, pro_desingnation);
				
				ps.setString(7,letter_no);
				ps.setString(8, letter_date);
				
				ps.setString(9, jur_off);
				ps.setString(10, jur_add1);
				ps.setString(11, jur_add2);
				ps.setString(12, jur_add3);
				ps.setInt(13, jur_pincode);
				
				ps.setString(14, relieve_status);
				ps.setString(15, relieve_date);
				ps.setString(16, intrest_upto);
				
				ps.setString(17, subject);
				ps.setString(18, reference);
				
				ps.setInt(19,Regular_Balance);		
				ps.setInt(20,regu_acc_unit_code);
				ps.setInt(21,txtImpoundBal);
				ps.setInt(22,imp_acc_unit_code);
				ps.setInt(23,txtImpound2003bal);
				ps.setInt(24,imp_2003);
				ps.setInt(25,DA_Amt);
				ps.setInt(26,da_acc_unit);
				
				
				ps.setString(27, dth_Legal_person);
				ps.setString(28, dth_add1);
				ps.setString(29, dth_add2);
				ps.setString(30, dth_add3);
				ps.setInt(31,pin);
				
				ps.setString(32, any_comments);
				ps.setString(33, for_off1);
				ps.setString(34, for_design1);
				ps.setString(35, copy);
				
				ps.setString(36, updatedby);
			//	ps.setTimestamp(37,"clock_timestamp()");	
				
				ps.setString(37,dth_date2);
				ps.setString(38,dth_Relation);
				
				ps.executeUpdate();
				count++;
				}catch(Exception e)
				{
					
					System.out.println(e);
				}
		   
	 		}
	   
//	   if(relieve_status.equals("DTH"))
//		{
//	   
//			pss=con.prepareStatement("INSERT " +
//					"INTO HRM_GPF_LEGAL_DETAILS " +
//					"  ( " +
//					"    GPF_NO, " +
//					"    EMPLOYEE_ID, " +
//					"    LEGAL_HIER_ID, " +
//					"    LEGAL_HEIR_NAME, " +
//					"    RELATIONSHIP, " +
//					"    LIC_DATE, " +
//					"    LEGAL_HEIR_ADD1, " +
//					"    LEGAL_HEIR_ADD2, " +
//					"    LEGAL_HEIR_ADD3, " +
//					"    LEGAL_HEIR_PIN, " +
//					"    LEGAL_PERCENT, " +
//					"    COPY_TO " +
//					"  ) " +
//					"  VALUES " +
//					"  ( " +
//					"    ?, " +
//					"    ?, " +
//					"    ?, " +
//					"    ?, " +
//					"    ?, " +
//					"    TO_DATE(?,'DD/MM/YYYY'), " +
//					"    ?, " +
//					"    ?, " +
//					"    ?, " +
//					"    ?, " +
//					"    ?, " +
//					"    ? " +
//					"  )"
//);
//			
//			pss.setInt(1,gpfNo);
//			pss.setInt(2,emp_id);
//			pss.setInt(3, dth_Legal_person_ID);
//			pss.setString(4, dth_Legal_person);
//			pss.setString(5,dth_Relation);
//			pss.setString(6,dth_date2);
//			
//			pss.setString(7, dth_add1);
//			pss.setString(8, dth_add2);
//			pss.setString(9, dth_add3);
//			pss.setInt(10,pin);
//			pss.setInt(11,percent);
//			pss.setString(12,copy);
//			
//			
//			System.out.println("GPF NO=="+gpfNo);
//			System.out.println("emp_id=="+emp_id);
//			System.out.println("dth_Legal_person_ID=="+dth_Legal_person_ID);
//			System.out.println("dth_Legal_person=="+dth_Legal_person);
//			System.out.println("dth_Relation=="+dth_Relation);
//			System.out.println("dth_date2=="+dth_date2);
//			System.out.println("dth_add1=="+dth_add1);
//			System.out.println("dth_add2=="+dth_add2);
//			System.out.println("dth_add3=="+dth_add3);
//			System.out.println("pin======="+pin);
//			
//			
//			pss.executeUpdate();
//		}
	   

	    	/* if(relieve_status.equals("VRS"))
		 		{
	    		 String date="10-10-2000";
	    		 String rel="NIL";
	    		 String dth_Legal="null",add1="null",add2="null",add3="null";
	    		 int pinco=0;
	    		 ps=con.prepareStatement("INSERT INTO HRM_GPF_FINAL_SETTLEMENT_PROC(GPF_NO,    EMPLOYEE_ID,    PRESID_OFFICER_PREFIX,    PRESID_OFFICER_NAME,    PRESID_OFFICER_SUFFIX,    PRESID_OFFICER_DESIG,    AUTH_LTR_NO,    AUTH_LTR_DATE,    JURISDICTION_OFFICER_DESIG,    JURISDICTION_OFFICE_ADD1,    JURISDICTION_OFFICE_ADD2,    JURISDICTION_OFFICE_ADD3,    JURISDICTION_OFFICE_PIN,    RELIEVAL_REASON_ID,    RELIEVAL_DATE,    INTEREST_UPTO,    SUBJECT,    REFERENCE,    GPF_REG_BAL_AMT,    GPF_REG_BAL_AC_HEAD,    GPF_IMP_BAL_AMT,    GPF_IMP_BAL_AC_HEAD,    GPF_IMP03_BAL_AMT,    GPF_IMP03_BAL_AC_HEAD,    GPF_1DA_BAL_AMT,    GPF_1DA_BAL_AC_HEAD,       ADDL_CONDITIONS,    FOWARD_OFFICER_NAME,    FOWARD_OFFICER_DESIG,    COPY_TO,    UPDATED_BY_USER_ID,    UPDATED_DATE,    PROCESS_FLOW_STATUS_ID )  VALUES  (    ?,    ?,    ?,    ?,    ?,    ?,    ?,    TO_DATE(?,'DD/MM/YYYY'),    ?,    ?,    ?,    ?,    ?,    ?,    TO_DATE(?,'DD/MM/YYYY'),    TO_DATE(?,'DD/MM/YYYY'),        ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,        ?,    ?,    ?,    ?,    ?,    clock_timestamp(),'CR')");
	    		 ps.setInt(1,gpfNo);
	 			ps.setInt(2,emp_id);
	 			ps.setString(3, prefix);
	 			ps.setString(4, pro_officer_name);
	 			ps.setString(5, suffix);
	 			ps.setString(6, pro_desingnation);
	 			ps.setString(7,letter_no);
	 			ps.setString(8, letter_date);
	 			ps.setString(9, jur_off);
	 			ps.setString(10, jur_add1);
	 			ps.setString(11, jur_add2);
	 			ps.setString(12, jur_add3);
	 			ps.setInt(13, jur_pincode);
	 			
	 			ps.setString(14, relieve_status);
	 			ps.setString(15, relieve_date);
	 			ps.setString(16, intrest_upto);
	 			ps.setString(17, subject);
	 			ps.setString(18, reference);
	 			ps.setInt(19,Regular_Balance);	 			
	 			ps.setInt(20,regu_acc_unit_code);
	 			ps.setInt(21,txtImpoundBal);
	 			ps.setInt(22,imp_acc_unit_code);
	 			ps.setInt(23,txtImpound2003bal);
	 			ps.setInt(24,imp_2003);
	 			ps.setInt(25,DA_Amt);
	 			ps.setInt(26,da_acc_unit);
	 			
	 			
	 			
	 			
	 			ps.setString(27, any_comments);
	 			ps.setString(28, for_off1);
	 			ps.setString(29, for_design1);
	 			ps.setString(30, copy);
	 			
	 			ps.setString(31, updatedby);
	 		//	ps.setTimestamp(37,"clock_timestamp()");	
	 			
	 			
	 			
	 			
//	 			ps.setString(27, dth_Legal_person);
//	 			ps.setString(28, dth_add1);
//	 			ps.setString(29, dth_add2);
//	 			ps.setString(30, dth_add3);
//	 			ps.setInt(31,pin);
	 			
//	 			ps.setString(27, any_comments);
//	 			ps.setString(28, for_off1);
//	 			ps.setString(29, for_design1);
//	 			ps.setString(30, copy);
//	 			
//	 			ps.setString(31, updatedby);
//	 		//	ps.setTimestamp(37,"clock_timestamp()");	
//	 			
//	 			ps.setString(32,date);
	 			//ps.setString(38,dth_Relation);
	 			
	 			ps.executeUpdate();
		 		}
	    	 if(relieve_status.equals("SAN"))
		 		{
	    		 String date="10-10-2000";
	    		 String rel="NIL";
	    		 String dth_Legal="null",add1="null",add2="null",add3="null";
	    		 int pinco=0;
	    		 ps=con.prepareStatement("INSERT INTO HRM_GPF_FINAL_SETTLEMENT_PROC(GPF_NO,    EMPLOYEE_ID,    PRESID_OFFICER_PREFIX,    PRESID_OFFICER_NAME,    PRESID_OFFICER_SUFFIX,    PRESID_OFFICER_DESIG,    AUTH_LTR_NO,    AUTH_LTR_DATE,    JURISDICTION_OFFICER_DESIG,    JURISDICTION_OFFICE_ADD1,    JURISDICTION_OFFICE_ADD2,    JURISDICTION_OFFICE_ADD3,    JURISDICTION_OFFICE_PIN,    RELIEVAL_REASON_ID,    RELIEVAL_DATE,    INTEREST_UPTO,    SUBJECT,    REFERENCE,    GPF_REG_BAL_AMT,    GPF_REG_BAL_AC_HEAD,    GPF_IMP_BAL_AMT,    GPF_IMP_BAL_AC_HEAD,    GPF_IMP03_BAL_AMT,    GPF_IMP03_BAL_AC_HEAD,    GPF_1DA_BAL_AMT,    GPF_1DA_BAL_AC_HEAD,       ADDL_CONDITIONS,    FOWARD_OFFICER_NAME,    FOWARD_OFFICER_DESIG,    COPY_TO,    UPDATED_BY_USER_ID,    UPDATED_DATE,    PROCESS_FLOW_STATUS_ID )  VALUES  (    ?,    ?,    ?,    ?,    ?,    ?,    ?,    TO_DATE(?,'DD/MM/YYYY'),    ?,    ?,    ?,    ?,    ?,    ?,    TO_DATE(?,'DD/MM/YYYY'),    TO_DATE(?,'DD/MM/YYYY'),        ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,    ?,        ?,    ?,    ?,    ?,    ?,    clock_timestamp(),'CR')");
	    		 ps.setInt(1,gpfNo);
	 			ps.setInt(2,emp_id);
	 			ps.setString(3, prefix);
	 			ps.setString(4, pro_officer_name);
	 			ps.setString(5, suffix);
	 			ps.setString(6, pro_desingnation);
	 			ps.setString(7,letter_no);
	 			ps.setString(8, letter_date);
	 			ps.setString(9, jur_off);
	 			ps.setString(10, jur_add1);
	 			ps.setString(11, jur_add2);
	 			ps.setString(12, jur_add3);
	 			ps.setInt(13, jur_pincode);
	 			
	 			ps.setString(14, relieve_status);
	 			ps.setString(15, relieve_date);
	 			ps.setString(16, intrest_upto);
	 			ps.setString(17, subject);
	 			ps.setString(18, reference);
	 			ps.setInt(19,Regular_Balance);	 			
	 			ps.setInt(20,regu_acc_unit_code);
	 			ps.setInt(21,txtImpoundBal);
	 			ps.setInt(22,imp_acc_unit_code);
	 			ps.setInt(23,txtImpound2003bal);
	 			ps.setInt(24,imp_2003);
	 			ps.setInt(25,DA_Amt);
	 			ps.setInt(26,da_acc_unit);
	 			
	 			
	 			
	 			
	 			ps.setString(27, any_comments);
	 			ps.setString(28, for_off1);
	 			ps.setString(29, for_design1);
	 			ps.setString(30, copy);
	 			
	 			ps.setString(31, updatedby);
	 		//	ps.setTimestamp(37,"clock_timestamp()");	
	 			
	 			
	 			
	 			
//	 			ps.setString(27, dth_Legal_person);
//	 			ps.setString(28, dth_add1);
//	 			ps.setString(29, dth_add2);
//	 			ps.setString(30, dth_add3);
//	 			ps.setInt(31,pin);
	 			
//	 			ps.setString(27, any_comments);
//	 			ps.setString(28, for_off1);
//	 			ps.setString(29, for_design1);
//	 			ps.setString(30, copy);
//	 			
//	 			ps.setString(31, updatedby);
//	 		//	ps.setTimestamp(37,"clock_timestamp()");	
//	 			
//	 			ps.setString(32,date);
	 			//ps.setString(38,dth_Relation);
	 			
	 			ps.executeUpdate();
		 		}*/
		}
	     
	     
	     catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xml=xml+"<flag>failure</flag>";
		}
	     
	     
	     xml=xml+"<flag>success</flag>";
	     
	   //  GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();	     
	  //   String statusid=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId();
	     
//	     if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()==0){	     	
//	     	int_tobe_calc_date=GpfAuthMstModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
//	     	
//	     	gpfAuthMstBean.setGpfNo(gpfNo);
//	     	gpfAuthMstBean.setSettlementReason(relieve_status);
//	     	gpfAuthMstBean.setRelievalDate(relieve_date);
//	     	gpfAuthMstBean.setIntToBeCalcDate(int_tobe_calc_date);	
//	     	gpfAuthMstBean.setStatusId("CR");
//	     	gpfAuthMstBean.setUpdatedDate(ts);
//	     	gpfAuthMstBean.setUpdateByUserId(updatedby);
//         	boolean result=GpfAuthMstModel.insertGpfAuthMstBean(gpfAuthMstBean);
//            if(result){
//            	xml=xml+"<flag>success</flag>";            	
//            	xml+=GpfAuthMstModel.xmlEmployee(gpfNo);
//             	xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);         		
//            }
//            else{
//            	xml=xml+"<flag>failure</flag>";            	
//            }
//	     }
//	     else {
//	     	xml=xml+"<flag>failure</flag>";
//	     }
	     xml=xml+"</response>";
	     
	     
	 }
	  if(command.equalsIgnoreCase("Update")) {
		 
	    int gpfNo=0;	     	
	 	String relieve_status="";
	 	String relieve_date="";
	 	String int_tobe_calc_month="";
	 	String int_tobe_calc_year="";
	 	String int_tobe_calc_date="";
	 	try {
	 		gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());	  
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_status=request.getParameter("relieve_status").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_date=request.getParameter("relieve_date").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}	     
	 	int_tobe_calc_date="";
	 	GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();	  
	 	String statusid=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId();
	    int_tobe_calc_date=GpfAuthMstModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
	     
	     xml="<response><command>update</command>";
	     xml=xml+"<status>update</status>";
	     if(statusid.equalsIgnoreCase("CR")||statusid.equalsIgnoreCase("ED")){	     	
    	 	gpfAuthMstBean.setGpfNo(gpfNo);
	     	gpfAuthMstBean.setSettlementReason(relieve_status);
	     	gpfAuthMstBean.setRelievalDate(relieve_date);
	     	gpfAuthMstBean.setIntToBeCalcDate(int_tobe_calc_date);	
	     	gpfAuthMstBean.setStatusId("ED");
	     	gpfAuthMstBean.setUpdatedDate(ts);
	     	gpfAuthMstBean.setUpdateByUserId(updatedby);
	 		boolean boolSt=false;
	 		boolSt=GpfAuthMstModel.setGpfAuthMstBean(gpfAuthMstBean);
	 		System.out.println("hi...1");	
     		if(boolSt){
     			xml=xml+"<flag>success</flag>";     			
	            xml+=GpfAuthMstModel.xmlEmployee(gpfNo);    	            	
	            System.out.println("hi...2");	
     		}else{
     			xml=xml+"<flag>failure</flag>";
     			System.out.println("hi...3");	
     		}
     	}
	        
	     else{
	     	xml=xml+"<flag>failure</flag>";
	     	System.out.println("hi...4");	
	     }
	     
	     
	     xml=xml+"</response>";
	 }
	     if(command.equalsIgnoreCase("Validate")) {
	    	 int gpfNo=0;	     	
	 	 	String relieve_status="";
	 	 	String relieve_date="";
	 	 	String int_tobe_calc_month="";
	 	 	String int_tobe_calc_year="";
	 	 	String int_tobe_calc_date="";
	 	 	try {
	 	 		gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());	  
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 	 	try {
	 	 		relieve_status=request.getParameter("relieve_status").trim();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 	 	try {
	 	 		relieve_date=request.getParameter("relieve_date").trim();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 	 	try {
	 	 		int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 	 	try {
	 	 		int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}	     
	 	 	int_tobe_calc_date="";
	 	 	GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();	  
	 	 	String statusid=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId();
	 	    int_tobe_calc_date=GpfAuthMstModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
	 	     
	 	     xml="<response><command>validate</command>";
	 	     xml=xml+"<status>validate</status>";
	 	     if(statusid.equalsIgnoreCase("CR")||statusid.equalsIgnoreCase("ED")){	     	
	     	 	gpfAuthMstBean.setGpfNo(gpfNo);
	 	     	gpfAuthMstBean.setSettlementReason(relieve_status);
	 	     	gpfAuthMstBean.setRelievalDate(relieve_date);
	 	     	gpfAuthMstBean.setIntToBeCalcDate(int_tobe_calc_date);	
	 	     	gpfAuthMstBean.setStatusId("FR");
	 	     	gpfAuthMstBean.setUpdatedDate(ts);
	 	     	gpfAuthMstBean.setUpdateByUserId(updatedby);
	 	 		boolean boolSt=false;
	 	 		System.out.println("relieve_status===="+relieve_status);
	 	 		boolSt=GpfAuthMstModel.setGpfAuthMstBean(gpfAuthMstBean);
	 	 		System.out.println("hi...1");	
	      		if(boolSt){
	      			xml=xml+"<flag>success</flag>";     			
	 	            xml+=GpfAuthMstModel.xmlEmployee(gpfNo);    	            	
	 	            System.out.println("hi...2");	
	      		}else{
	      			xml=xml+"<flag>failure</flag>";
	      			System.out.println("hi...3");	
	      		}
	      	}
	 	        
	 	     else{
	 	     	xml=xml+"<flag>failure</flag>";
	 	     	System.out.println("hi...4");	
	 	     }
	 	     
	 	     
	 	     xml=xml+"</response>";
	     }	            
	      if(command.equalsIgnoreCase("Delete")) {
	         System.out.println("Delete");
	         int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
	         xml="<response><command>delete</command>";
	         xml=xml+"<status>delete</status>";
	         String processStatus=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().trim();
	         
	         
	         	if(!processStatus.equals("FR")){
	         		boolean boolDS=GpfAuthMstModel.deleteGpfAuthMstBean(gpfNo);
	         		if (boolDS) {
	         			xml=xml+"<flag>success</flag>"; 
						} else {
							xml=xml+"<flag>failure</flag>";
						}
	             	
	         	}else{
	         		xml=xml+"<flag>failure</flag>";
	         	}
	         	
				
				xml=xml+"</response>";
	     }	     
	    if(command.equalsIgnoreCase("LoadYear")){
	     	
	     	xml="<response><command>LoadYear</command>";
	     	xml=xml+"<status>LoadYear</status>";
	     	xml=xml+"<flag>success</flag>"; 
	     	String relvDate="";
	     	try {
	     		relvDate=request.getParameter("relieve_date");
				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println("LoadYear:rel_date="+relvDate);
				
	     	
				xml=xml+"<listYear>"+GpfAuthMstModel.xmlLoadListYear(relvDate)+"</listYear>"; 
				
	     	xml=xml+"</response>";
	     	System.out.println("LoadYear xml="+xml);
	     }
	     if(command.equalsIgnoreCase("LoadMonth")){
	     	xml="<response><command>LoadMonth</command>";
	     	xml=xml+"<status>LoadMonth</status>";
	     	xml=xml+"<flag>success</flag>"; 
	     	String relvDate="";
	     	String listYear="";
	     	
	     	try {
	     		relvDate=request.getParameter("relieve_date");
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {						
					listYear=request.getParameter("listYear");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
	     	
				xml=xml+"<listMonth>"+GpfAuthMstModel.xmlLoadListMonth(relvDate, listYear)+"</listMonth>"; 
				
	     	xml=xml+"</response>";
	     }
	     
	     
	  	GpfAuthMstModel.closeConnection();
	  	System.out.println(xml);
		 out.println(xml);
		 out.close();	        
	
	    }
}
