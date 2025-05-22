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
public class Panel_Master_Updation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     public Panel_Master_Updation() {
        super();
      
    }
     Connection connection = null;
     PreparedStatement ps=null,ps1=null;
     ResultSet rs=null;
     HttpSession session;
	
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		
 	
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
	        int cadre=0,Pincode=0,Last_Office_id=0,Emp_Rel=0,Appl_Com=0,post_code=0,reason_id,appl_no=0;
	        String panel_ref_no,panel_date,ori_reg_no,remarks,Amendment,Emp_Ini,Desig_at_dth,Date_of_Rec,Appl_DOB,Edu_level,File_no,incom,Status_Rsn,Emp_Name,Date_of_dth,Last_Office_Name,Status_Id,checklist=null,reason[];
	        String updatedby = (String)session.getAttribute("UserId");
	        System.out.println("user id..." + updatedby);
	        String sql="";
	        int panel_no=0;
	        int panel_id=0;
	        long l = System.currentTimeMillis();
	        java.sql.Timestamp ts = new java.sql.Timestamp(l);
	        strCommand=request.getParameter("Command");
	        exsit=request.getParameter("exsit");
	        newss=request.getParameter("newss");
	        System.out.println("strCommand"+strCommand);
	        System.out.println("exsit------->"+exsit);
	        try
	        {
	        	
			         if (strCommand.equalsIgnoreCase("Add"))
			        {
			        	xml="<response><command>Add</command>";
			        	try
                        {
			        		cadre=Integer.parseInt(request.getParameter("cadre"));
			        	
			        		panel_ref_no=request.getParameter("panel_ref_no");
			        	
			        		panel_date=request.getParameter("panel_date");
			        	
			        	    //ori_reg_no=request.getParameter("ori_reg_no");
			        	
			        	    remarks=request.getParameter("remarks");
			        	
			        	    //Amendment=request.getParameter("Amendment");
			        	
			        	   sql="SELECT max(PANEL_ID) AS PANEL_ID FROM HRM_PANEL_MASTER_NEW";
			        	   ps=connection.prepareStatement(sql);
			        	   rs=ps.executeQuery();
			        	   while(rs.next())
			        	   {
			        		   panel_no=rs.getInt("PANEL_ID");
			        	   }
			        	   panel_no=panel_no+1;	
			        	   
			        	   sql="SELECT PANEL_ID, " +
			        			   "  PANEL_CADRE, " +
			        			   "  to_char(PANEL_DATE,'dd/mm/yyyy') as PANEL_DATE " +
			        			   " FROM HRM_PANEL_MASTER_NEW " +
			        			   "WHERE PANEL_CADRE="+cadre+" " +
			        			   "AND PANEL_DATE=to_date('"+panel_date+"','dd/mm/yyyy')";
			        	   System.out.println("sql ---------->"+sql);
			        	   ps=connection.prepareStatement(sql);
			        	   rs=ps.executeQuery();
			        	   if(rs.next())
			        	   {
			        		   System.out.println("Inside if---------->");
			        		   xml=xml+"<flag>Exsist</flag>";
			        	   }
			        	   else
			        	   {
			        	   	        	
			        	System.out.println("Seniooo---->"+panel_no);
			        	   sql="INSERT " +
			        			   "INTO HRM_PANEL_MASTER_NEW " +
			        			   "  ( " +
			        			   "    PANEL_ID, " +
			        			   "    PANEL_CADRE, " +
			        			   "    PANEL_REF_NO, " +
			        			   "    PANEL_DATE, " +
			        			   "    UPDATED_BY_USER_ID, " +
			        			   "    UPDATED_DATE, " +
			        			   "    PROCESS_FLOW_ID, " +
			        			   " REMARKS " +
			        			   "  ) " +
			        			   "  VALUES " +
			        			   "  ( " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    to_date(?,'dd/mm/yyyy'), " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    ? " +
			        			   "  )";

                                ps=connection.prepareStatement(sql);
                                ps.setInt(1, panel_no);
                                ps.setInt(2, cadre);
                                ps.setString(3, panel_ref_no);
                                ps.setString(4, panel_date);                               
                                ps.setString(5,updatedby);
                                ps.setTimestamp(6, ts);                            
                                ps.setString(7, "CR");
                                ps.setString(8, remarks);
                                ps.executeUpdate();
                                
			        	   
                        xml=xml+"<flag>success</flag>";
			        	   }
                        }catch(Exception e)
                        {
                        	System.out.println("exception "+e);
                        }
			        }   
			         else if(strCommand.equalsIgnoreCase("Update"))
			         {
			        	 xml="<response><command>Update</command>";
				        	try
	                        {
				        		cadre=Integer.parseInt(request.getParameter("cadre"));
				        		panel_id=Integer.parseInt(request.getParameter("panel_id"));
				        		panel_ref_no=request.getParameter("panel_ref_no");
				        	
				        		panel_date=request.getParameter("panel_date");
				        	
				        	    //ori_reg_no=request.getParameter("ori_reg_no");
				        	
				        	    remarks=request.getParameter("remarks");
				        	    
				        	    
				        	    
				        	    sql="UPDATE HRM_PANEL_MASTER_NEW " +
				        	    		"SET PANEL_CADRE      =?, " +
				        	    		"  PANEL_REF_NO       =?, " +
				        	    		"  PANEL_DATE         =to_date(?,'dd/mm/yyyy'), " +
				        	    		"  UPDATED_BY_USER_ID =?, " +
				        	    		"  UPDATED_DATE       =?, " +
				        	    		"  PROCESS_FLOW_ID    =?, " +
				        	    		"  REMARKS            =? " +
				        	    		"WHERE PANEL_ID       =?" ;

				        	    ps=connection.prepareStatement(sql);
				        	   
                                ps.setInt(1, cadre);
                                ps.setString(2, panel_ref_no);
                                ps.setString(3, panel_date);
                                ps.setString(4,updatedby);
                                ps.setTimestamp(5, ts);                            
                                ps.setString(6, "MD");
                                ps.setString(7, remarks);
                                ps.setInt(8, panel_id);
                                ps.executeUpdate();
                                
                                xml=xml+"<flag>success</flag>";
	                        }
				          catch(Exception es)
				          {
				        	  System.out.println("Error is---->"+es);
				          }
			         }
			         else if(strCommand.equalsIgnoreCase("validate"))
			         {
			        	 xml="<response><command>validate</command>";
				        	try
	                        {
				        		cadre=Integer.parseInt(request.getParameter("cadre"));
				        		panel_id=Integer.parseInt(request.getParameter("panel_id"));
				        		panel_ref_no=request.getParameter("panel_ref_no");
				        	
				        		panel_date=request.getParameter("panel_date");
				        	
				        	    //ori_reg_no=request.getParameter("ori_reg_no");
				        	
				        	    remarks=request.getParameter("remarks");
				        	    
				        	    
				        	    
				        	    sql="UPDATE HRM_PANEL_MASTER_NEW " +
				        	    		"SET PANEL_CADRE      =?, " +
				        	    		"  PANEL_REF_NO       =?, " +
				        	    		"  PANEL_DATE         =to_date(?,'dd/mm/yyyy'), " +
				        	    		"  UPDATED_BY_USER_ID =?, " +
				        	    		"  UPDATED_DATE       =?, " +
				        	    		"  PROCESS_FLOW_ID    =?, " +
				        	    		"  REMARKS            =? " +
				        	    		"WHERE PANEL_ID       =?" ;

				        	    ps=connection.prepareStatement(sql);
				        	   
                                ps.setInt(1, cadre);
                                ps.setString(2, panel_ref_no);
                                ps.setString(3, panel_date);
                                ps.setString(4,updatedby);
                                ps.setTimestamp(5, ts);                            
                                ps.setString(6, "FR");
                                ps.setString(7, remarks);
                                ps.setInt(8, panel_id);
                                ps.executeUpdate();
                                
                                xml=xml+"<flag>success</flag>";
	                        }
				          catch(Exception es)
				          {
				        	  System.out.println("Error is---->"+es);
				          }
			         }
			        else if(strCommand.equalsIgnoreCase("Delete"))
			         {
			        	 xml="<response><command>Delete</command>"; 
			        	 try
			        	 {
			        		 panel_id=Integer.parseInt(request.getParameter("panel_id"));
			        		 sql="DELETE FROM HRM_PANEL_MASTER_NEW WHERE PANEL_ID = '"+panel_id+"'";

			        		 ps=connection.prepareStatement(sql);
			        		 ps.executeQuery();
			        		 xml=xml+"<flag>success</flag>";
			        	 }
			        	 catch(Exception es)
			        	 {
			        		 System.out.println("Error is--->"+es);
			        	 }
			         }
			         else if(strCommand.equalsIgnoreCase("getdata"))			        	
			         {
			        	
			        	 cadre=Integer.parseInt(request.getParameter("cadre_id"));
			        	 xml="<response><command>getdata</command>"; 
			        	 try
			        	 {
							 sql="SELECT PANEL_ID, " +
								"  PANEL_CADRE, " +
								"  CADRE_NAME, " +
								"  PANEL_REF_NO, " +
								"  PANEL_DATE, " +
								"  PROCESS_FLOW_ID, " +
								"  REMARKS " +
								"FROM " +
								"  (SELECT PANEL_ID, " +
								"    PANEL_CADRE, " +
								"    PANEL_REF_NO, " +
								"    TO_CHAR(PANEL_DATE,'dd/mm/yyyy') AS PANEL_DATE, " +
								"    PROCESS_FLOW_ID, " +
								"    REMARKS " +
								"  FROM HRM_PANEL_MASTER_NEW WHERE PANEL_CADRE="+cadre+" " +
								"  ) mst " +
								"LEFT OUTER JOIN " +
								"  (SELECT CADRE_ID,CADRE_NAME FROM HRM_MST_CADRE " +
								"  )sub " +
								"ON sub.CADRE_ID=mst.PANEL_CADRE order by PANEL_DATE desc";

			        		 ps=connection.prepareStatement(sql);
			        		 rs=ps.executeQuery();
			        		 while(rs.next())
			        		 {
			        			 xml=xml+"<count><PANEL_ID>"+rs.getInt("PANEL_ID")+"</PANEL_ID>";
			        			 xml=xml+"<PANEL_CADRE>"+rs.getInt("PANEL_CADRE")+"</PANEL_CADRE>";
			        			 xml=xml+"<CADRE_NAME>"+rs.getString("CADRE_NAME")+"</CADRE_NAME>";
			        			 xml=xml+"<PANEL_REF_NO>"+rs.getString("PANEL_REF_NO")+"</PANEL_REF_NO>";
			        			 xml=xml+"<PANEL_DATE>"+rs.getString("PANEL_DATE")+"</PANEL_DATE>";		       
			        			 xml=xml+"<PROCESS_FLOW_ID>"+rs.getString("PROCESS_FLOW_ID")+"</PROCESS_FLOW_ID>";
			        			 xml=xml+"<REMARKS>"+rs.getString("REMARKS")+"</REMARKS></count>";
			        		 }
			        		 xml=xml+"<flag>success</flag>";
			        	 }
			        	 catch(Exception e)
			        	 {
			        		 System.out.println("Error is--->"+e);
			        	 }
			         }
			        else if(strCommand.equalsIgnoreCase("seniority"))
			        {}
			        
			        else if(strCommand.equalsIgnoreCase("load"))
			        {}
			        else if(strCommand.equalsIgnoreCase("getreason"))
			        {}
			        else if(strCommand.equalsIgnoreCase("Update"))
			        {}
			        if(strCommand.equalsIgnoreCase("getchecklist"))
			        {
			        	String chklist="";
			        	xml="<response><command>getchecklist</command>";
			        	try
			        	{
			        		chklist=request.getParameter("chklist");
			        		System.out.println("chklist--->"+chklist);
			        	 sql="SELECT REASON_ID, " +
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
	
	


}
