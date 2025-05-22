package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.http.*;
import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;


public class Emp_Ret_Test extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
  //  private static final String DOC_TYPE = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
  
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		Connection con=null;
    	 	//System.out.println("welcome to subsciption details");
    	 //	FileRead_Bean fileObj=new FileRead_Bean();
	        try{
	        	LoadDriver driver=new LoadDriver();
	        	con=driver.getConnection();
	        }
	        catch(Exception e){
	        	//System.out.println("Exception in opening connection :"+e); 
	        }
	        ResultSet rs=null;
	        //CallableStatement cs=null;
	        PreparedStatement ps=null;
	        String xml="",sql="";
	       
	        int emp_id=0;
	        
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        response.setHeader("Cache-Control","no-cache");  
	        HttpSession session=request.getSession(false);
	        
	      //  UserProfile up=null;
	      //  up=(UserProfile)session.getAttribute("UserProfile");   
	       // int empId=Integer.parseInt(up.getEmployeeId()+"");
	        
	        try{
	                if(session==null){
	                        xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
	                        out.println(xml);
	                        out.close();
	                        return;
	                }
	        }catch(Exception e){
	                System.out.println("Redirect Error :"+e);
	        }
	        String command;
	        boolean bb=false;
	        boolean bb1=false;
	        boolean bb2=false;
	        command = request.getParameter("command");	        
	        session =request.getSession(false);
	       // String updatedby=(String)session.getAttribute("UserId");
	        //long l=System.currentTimeMillis();
	       // java.sql.Timestamp ts=new java.sql.Timestamp(l);
	        if(command.equalsIgnoreCase("loademp")) {
	        	
	        	int txtEmployeeid = Integer.parseInt(request.getParameter("txtEmployeeid"));
		        xml="<response><command>loademp</command>";
		        try
		        {
		        	 sql="SELECT mst.EMPLOYEE_ID, " +
		        	 "  mst.OFFICE_ID, " +
		        	 "  empname, " +
		        	 "  DESIGNATION, " +
		        	 "  mst.DESIGNATION_ID, " +
		        	 "  POST_RANK_ID " +
		        	 " FROM " +
		        	 "  (SELECT EMPLOYEE_ID, " +
		        	 "    OFFICE_ID, " +
		        	 "    DESIGNATION_ID, " +
		        	 "    POST_RANK_ID " +
		        	 "  FROM HRM_EMP_CURRENT_POSTING " +
		        	 "  WHERE EMPLOYEE_ID       =? " +
		        	 "  AND employee_status_id IN('SAN','VRS','DIS','DTH','RES','CMR') " +
		        	 "  )mst " +
		        	 " LEFT OUTER JOIN " +
		        	 "  (SELECT DESIGNATION_ID,DESIGNATION FROM HRM_MST_DESIGNATIONS " +
		        	 "  )sub " +
		        	 " ON sub.DESIGNATION_ID=mst.DESIGNATION_ID " +
		        	 " LEFT OUTER JOIN " +
		        	 "  (SELECT EMPLOYEE_ID, " +
		        	 "    EMPLOYEE_INITIAL " +
		        	 "    || '.' " +
		        	 "    || EMPLOYEE_NAME AS empname " +
		        	 "  FROM VIEW_EMPLOYEE2 " +
		        	 "  )sub1 " +
		        	 " ON sub1.EMPLOYEE_ID=mst.EMPLOYEE_ID";

                   ps=con.prepareStatement(sql);
                   ps.setInt(1, txtEmployeeid);
                   rs=ps.executeQuery();
                   if(!rs.next())
                   {
                	   xml=xml+"<flag>failure</flag>";
                	   
                   }
                   else
                   {
                	   xml=xml+"<desig>"+rs.getString("DESIGNATION")+"</desig>"; 
                	   xml=xml+"<empname>"+rs.getString("empname")+"</empname>"; 
                	   xml=xml+"<flag>success</flag>";
                   }
                   
		        }
	           
	            catch(Exception e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            
	            xml=xml+"</response>";

	        }
	         if(command.equalsIgnoreCase("SGroup1"))
	         { int sgroup = 0;
	        	 sgroup = Integer.parseInt(request.getParameter("cmbsgroup"));
	             xml = "<response>";
	             try {
	                 ps =
	   con.prepareStatement("select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS  where SERVICE_GROUP_ID=? order by DESIGNATION");
	                 ps.setInt(1, sgroup);
	                 rs = ps.executeQuery();
	                 int count = 0;
	                 xml = xml + "<flag>success</flag>";
	                 while (rs.next()) {

	                     xml =
	  xml + "<option><id>" + rs.getInt("DESIGNATION_ID") + "</id><name>" +
	    rs.getString("DESIGNATION") + "</name></option>";
	                     count++;
	                 }
	                 System.out.println("count::" + count);
	                 if (count == 0)
	                     xml = "<response><flag>failure</flag>";
	                 else
	                     xml = xml + "<flag>success</flag>";

	             }

	             catch (Exception e) {

	                 System.out.println("catch........" + e);
	                 xml = "<response><flag>failure</flag>";
	             }

	             xml = xml + "</response>";


	         
	         }
	         if(command.equalsIgnoreCase("Updation"))
	         {
	        	 int post_id=0;
	        	 try
	        	 {
	        	  emp_id = Integer.parseInt(request.getParameter("emp_id"));
	        
	        	  int ss= Integer.toString(emp_id).length();
	        			if(ss<=5)
	        			{
	        				bb=true;
	        			}
	        			else
	        			{
	        				bb=false;
	        			}
	        	 // String service_grp =request.getParameter("service_grp");
	        	 
	        	  int service_desig = Integer.parseInt(request.getParameter("service_desig"));
			        xml="<response><command>Updation</command>";
			        try
			        { 
			          sql="SELECT EMPLOYEE_ID, " +
			         "  mst.DESIGNATION_ID, " +
			         "  POST_RANK_ID " +
			         " FROM " +
			         "  (SELECT EMPLOYEE_ID, " +
			         "    DESIGNATION_ID " +
			         "  FROM HRM_EMP_CURRENT_POSTING " +
			         "  WHERE EMPLOYEE_ID = '" + emp_id + "' " +
			         "  )mst " +
			         " JOIN " +
			         "  ( SELECT DESIGNATION_ID, POST_RANK_ID FROM HRM_MST_DESIGNATIONS " +
			         "  )sub " +
			         "ON sub.DESIGNATION_ID=mst.DESIGNATION_ID";
                     ps=con.prepareStatement(sql);
                    
                    rs= ps.executeQuery();
                     if(rs.next())
                     {
                    	   post_id=rs.getInt("POST_RANK_ID");
                    	   
                     }
                    
                     int sd= Integer.toString(post_id).length();
                     
                     if(sd<=3)
	        			{
	        				bb1=true;
	        			}
	        			else
	        			{
	        				bb1=false;
	        			}
                     int sf= Integer.toString(service_desig).length();
                     
                     if(sf<=3)
	        			{
	        				bb2=true;
	        			}
	        			else
	        			{
	        				bb2=false;
	        			}
                     
                     if(bb==true && bb1==true && bb2==true)
                     {
                     
                     sql="UPDATE HRM_EMP_CURRENT_POSTING " +
                     " SET POST_RANK_ID  = ?, " +
                     " DESIGNATION_ID = ? " +
                     " WHERE EMPLOYEE_ID = ? " ;
                    
                     ps=con.prepareStatement(sql);
                     ps.setInt(1, post_id);
                     ps.setInt(2, service_desig);
                     ps.setInt(3, emp_id);
                   
                     ps.executeUpdate();
                     xml = xml + "<flag>success</flag>";
			        }
                     else
                     {
                    	 xml=xml+"<flag>RecordLength</flag>";
                     }
			        }
			        
			        catch(Exception e)
			        {
			        	System.out.println("Error is---->"+e);
			        	 xml=xml+"<flag>failure</flag>";
			        }
	        	 }
	        	 catch(NumberFormatException es)
	        	 {
	        		 xml=xml+"<response><command>Updation</command><flag>Numformat</flag>"; 
	        	 }
			        xml = xml + "</response>";
	         }
	        
	         out.println(xml);
	         System.out.println("adfdas"+xml);
	        out.close();
	        

        }
}
