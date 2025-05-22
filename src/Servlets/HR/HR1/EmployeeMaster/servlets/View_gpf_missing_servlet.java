package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class View_gpf_missing_servlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
System.out.println("enter servlet--**");
response.setHeader("cache-control", "no-cache");
try {
	HttpSession session = request.getSession(false);
	if (session == null) {
		System.out.println(request.getContextPath() + "/index.jsp");
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	
	} else {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet results = null;
		 PrintWriter out = response.getWriter();
		try {

			 LoadDriver driver=new LoadDriver();
		     connection=driver.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("********************"+request.getQueryString());
		if(request.getParameter("jid")!=null )
		{ 
			System.out.println("view part");
			request.setAttribute("jid", request.getParameter("jid"));
			RequestDispatcher dis=request.getRequestDispatcher("org/HR/HR1/EmployeeMaster/jsps/View_Missing_Interest_Balance_GPF.jsp?jrnl="+request.getParameter("jid"));
			dis.forward(request, response);
			return;
		}
		else
		{
		try {
			String html="";
			int count=0,var=1,counter=0;
			
			ResultSet rs;
			ResultSet rs1;
			String monthNames[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
			String type = request.getParameter("type") != null ? request
					.getParameter("type")
					: "";
					html=html+"<table cellspacing=2 cellpadding=3 border=1 width=90% align=center>";
					html=html+"<tr class='tdB'><td>Select</td><td>HO JRNL MST ID</td><td>GPF NO</td><td>Employee Name</td><td>Relating Month</td>";
					html=html+"<td>Relating Year</td><td>Transaction Type</td><td>Category</td><td>Amount</td><td>Serial No</td><td>Validate Status</td></tr>";
					if(type.equalsIgnoreCase("all"))
					{
					
					 ps=connection.prepareStatement("select a.SERIAL_NO,a.GPF_NO,empname,a.REL_MONTH,a.REL_YEAR,AMOUNT,a.TRANS_TYPE,a.INTEREST_CALCULATED_STATUS"+
",a.HO_JRNL_MST_ID,a.CATEGORY,decode(b.PROCESS_FLOW_STATUS_ID,'FR','YES','NO') as status "+
"from (select * from HRM_GPF_MISSING_CR_DB_CON where interest_calculated_status='Y')a  "+
"left outer join (select distinct SERIAL_NO,PROCESS_FLOW_STATUS_ID from HRM_GPF_MISSING_INTEREST)b "+
"on a.SERIAL_NO=b.SERIAL_NO left outer join (select GPF_NO,employee_name || '.' || employee_initial as empname from  hrm_mst_employees)C on A.GPF_NO=C.GPF_NO order by SERIAL_NO" );
					 rs=ps.executeQuery();
					}
					else
					{
					String date = request.getParameter("date") != null ? request
							.getParameter("date")
							: "";
							System.out.println("------------date"+date);
					 ps=connection.prepareStatement("select a.SERIAL_NO,a.GPF_NO,empname,a.REL_MONTH,a.REL_YEAR,AMOUNT,a.TRANS_TYPE,a.INTEREST_CALCULATED_STATUS"+
",a.HO_JRNL_MST_ID,a.CATEGORY,decode(b.PROCESS_FLOW_STATUS_ID,'FR','YES','NO') as status "+
"from (select * from HRM_GPF_MISSING_CR_DB_CON where interest_calculated_status='Y' and HO_JRNL_MST_ID in "+
"(select HO_JRNL_MST_ID from HRM_GPF_MISSING_CR_DB_MST where JOURNAL_REF_DATE=to_date(?,'dd-mm-yyyy')))a  "+
"left outer join (select distinct SERIAL_NO,PROCESS_FLOW_STATUS_ID from HRM_GPF_MISSING_INTEREST)b "+
"on a.SERIAL_NO=b.SERIAL_NO left outer join (select GPF_NO,employee_name || '.' || employee_initial as empname from  hrm_mst_employees)C on A.GPF_NO=C.GPF_NO order by SERIAL_NO" );
						ps.setString(1, date);
					    rs=ps.executeQuery();
					}
					int entrycount=0;
					String month_name="";
				             while(rs.next()) 
				             {
				            	
				            	// System.out.println("inside the loop------------");
				            	 System.out.println("jrnl id="+rs.getString("HO_JRNL_MST_ID"));
				            	 ps=connection.prepareStatement("select count(*) from HRM_GPF_MISSING_CR_DB_CON where HO_JRNL_MST_ID="+rs.getString("HO_JRNL_MST_ID"));
				         	    rs1=ps.executeQuery();
				                      if(rs1.next()) 
				                      {
				                    	  count=rs1.getInt(1);
				                      }
				                      
				                      System.out.println("***********************"+rs.getString("HO_JRNL_MST_ID")+count);
				            	 counter++;
				            	 if(var==1)
				            	 {
				            		 int smonth=rs.getInt("REL_MONTH");
				            		 month_name=monthNames[smonth-1];
				            		 entrycount++;
				            		 System.out.println("#####################");
				            		 html=html+"<div style='display:none'><input type=radio name=inter value='0'/>" +
				            		 		"<input type=hidden name=status value=''/></div>";
				            		 html=html+"<tr class=table>"; 
				            		 html=html+"<td rowspan="+count+"><input type=radio name=inter value='"+rs.getString("HO_JRNL_MST_ID") +"'  onclick='checkrecalculate();'/> </td>";  
				            		 html=html+"<td>"+rs.getString("HO_JRNL_MST_ID")+" </td><td>"+rs.getInt("gpf_no")+ "</td><td>"+rs.getString("empname")+ "</td> ";           
				            		 html=html+"<td>"+month_name +"</td>";
				            		 html=html+"<td>"+rs.getInt("rel_year") +" </td>";
				            		 html=html+"<td>"+rs.getString("TRANS_TYPE") +"</td>";
				            		 html=html+"<td>"+rs.getString("CATEGORY")+"</td>";
				            		 html=html+"<td>"+rs.getInt("amount") +" </td>";
				            		 html=html+"<td>"+rs.getInt("serial_no")+" </td>  "; 
				            		 html=html+" <td>"+rs.getString("status") +"<input type=hidden name=status value='"+rs.getString("status") +"'/> </td></tr>";
				              
				              
				              
				              var++;
				            	 }
				            	 else
				            	 {
				            		
				            		 html=html+"<tr class=table><td>"+rs.getString("HO_JRNL_MST_ID") +" </td>";
				            		          
				            		 html=html+"<td>"+rs.getInt("gpf_no")+" </td>";  
				            		 html=html+"<td>"+rs.getString("empname")+" </td>";  
				            		 
				            		 html=html+" <td>"+month_name+" </td>";
				            		 html=html+"<td>"+rs.getInt("rel_year") +" </td>";
				            		 html=html+" <td>"+rs.getString("TRANS_TYPE") +" </td>";
				            		 html=html+"   <td>"+rs.getString("CATEGORY") +" </td>";
				            		 html=html+" <td>"+rs.getInt("amount") +" </td>";
				            		 html=html+"  <td>"+rs.getInt("serial_no")+" </td>  ";
				            		 html=html+"    <td>"+rs.getString("status") +" </td> </tr>";
				            		              
				            		      
				            		     
				            		              
				            	 }
				            	 if(counter==count)
				            	 {
				            		 counter=0;
				            		 var=1;
				            	 }
				             
				             }
				        rs.close();
				        ps.close();
					html=html+" <tr class=tdH><td colspan=3><center><input type=button id='interest' value='View GPF Missing Interest' onclick='LoadData()'/></center></td>";
					html=html+"<td colspan=3><center><input type=button id='seperate' value='Working Sheet For Selected Record' onclick='report2()'/></center></td>";
					html=html+"<td colspan=2><center><input type=button id='report' value='Working Sheet For All' onclick='javascript:report3()'/></center></td>";
					html=html+"<td colspan=3><center><input type=button id='report1' name='recalculate' value='Recalculate Missing Interest' onclick='javascript:recalculate1()'/></center></td></tr></table>";
					System.out.println(html);
					out.print(html);
					
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
}
	catch (Exception e) {
		e.printStackTrace();
	}
	}
}
