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
public class Seniority_Proceeding_master extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     public Seniority_Proceeding_master() {
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
	        String pro_no,pro_date,ori_reg_no,remarks,Amendment,Emp_Ini,Desig_at_dth,Date_of_Rec,Appl_DOB,Edu_level,File_no,incom,Status_Rsn,Emp_Name,Date_of_dth,Last_Office_Name,Status_Id,checklist=null,reason[];
	        String updatedby = (String)session.getAttribute("UserId");
	        System.out.println("user id..." + updatedby);
	        String sql="";
	        int seniority_no=0;
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
			        	
			        		pro_no=request.getParameter("pro_no");
			        	
			        	    pro_date=request.getParameter("pro_date");
			        	
			        	    ori_reg_no=request.getParameter("ori_reg_no");
			        	
			        	    remarks=request.getParameter("remarks");
			        	
			        	    Amendment=request.getParameter("Amendment");
			        	
			        	   sql="SELECT max(SENIORITY_LIST_ID) AS SENIORITY_LIST_ID FROM HRM_SENIORITY_LIST_MST";
			        	   ps=connection.prepareStatement(sql);
			        	   rs=ps.executeQuery();
			        	   while(rs.next())
			        	   {
			        		   seniority_no=rs.getInt("SENIORITY_LIST_ID");
			        	   }
			        	   
			        	   seniority_no=seniority_no+1;		        	
			        	System.out.println("Seniooo---->"+seniority_no);
			        	   sql="INSERT " +
			        			   "INTO HRM_SENIORITY_LIST_MST " +
			        			   "  ( " +
			        			   "    SENIORITY_LIST_ID, " +
			        			   "    CADRE_ID, " +
			        			   "    PROCEEDINGS_DATE, " +
			        			   "    PROCEEDINGS_REF_NO, " +
			        			   "    NEW_OR_AMENDMENT_FLAG, " +
			        			   "    ORIGINAL_ORDER_REF_NO, " +
			        			   "    PROCESS_FLOW_ID, " +
			        			   "    UPDATED_BY_USER_ID, " +
			        			   "    UPDATED_DATE, " +
			        			   "    REMARKS " +
			        			   "  ) " +
			        			   "  VALUES " +
			        			   "  ( " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "   to_date(?,'dd-mm-yyyy'), " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    ?, " +
			        			   "    ? " +
			        			   "  )";
                                ps=connection.prepareStatement(sql);
                                ps.setInt(1, seniority_no);
                                ps.setInt(2, cadre);
                                ps.setString(3, pro_date);
                                ps.setString(4, pro_no);
                                ps.setString(5, Amendment);
                                ps.setString(6, ori_reg_no);
                                ps.setString(7, "CR");
                                ps.setString(8,updatedby);
                                ps.setTimestamp(9, ts);
                                ps.setString(10, remarks);
                                ps.executeUpdate();
                                
			        	   
                        xml=xml+"<flag>success</flag>";
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
				        		System.out.println("cadre -->"+cadre);
				        		pro_no=request.getParameter("pro_no");
				        		System.out.println("pro_no -->"+pro_no);
				        	    pro_date=request.getParameter("pro_date");
				        	    System.out.println("pro_date -->"+pro_date);
				        	    ori_reg_no=request.getParameter("ori_reg_no");
				        	    System.out.println("ori_reg_no -->"+ori_reg_no);
				        	    remarks=request.getParameter("remarks");
				        	    System.out.println("remarks -->"+remarks);
				        	    Amendment=request.getParameter("Amendment");
				        	    System.out.println("Amendment -->"+Amendment);
				        	    seniority_no=Integer.parseInt(request.getParameter("seniority_id"));
				        	    
				        	    System.out.println("seniortiy -->"+seniority_no);
				        	    
				        	    sql="UPDATE HRM_SENIORITY_LIST_MST " +
				        	    		" SET CADRE_ID            =?, " +
				        	    		"  PROCEEDINGS_DATE      =to_date(?,'dd-mm-yyyy'), " +
				        	    		"  PROCEEDINGS_REF_NO    =?, " +
				        	    		"  NEW_OR_AMENDMENT_FLAG =?, " +
				        	    		"  ORIGINAL_ORDER_REF_NO =?, " +
				        	    		"  PROCESS_FLOW_ID       =?, " +
				        	    		"  UPDATED_BY_USER_ID    =?, " +
				        	    		"  UPDATED_DATE          =?, " +
				        	    		"  REMARKS               =? " +
				        	    		" WHERE SENIORITY_LIST_ID = ?";
				        	    ps=connection.prepareStatement(sql);
				        	   
                                ps.setInt(1, cadre);
                                ps.setString(2, pro_date);
                                ps.setString(3, pro_no);
                                ps.setString(4, Amendment);
                                ps.setString(5, ori_reg_no);
                                ps.setString(6, "MD");
                                ps.setString(7,updatedby);
                                ps.setTimestamp(8, ts);
                                ps.setString(9, remarks);
                                ps.setInt(10, seniority_no);
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
				        		
				        		pro_no=request.getParameter("pro_no");
				        		
				        	    pro_date=request.getParameter("pro_date");
				        	   
				        	    ori_reg_no=request.getParameter("ori_reg_no");
				        	   
				        	    remarks=request.getParameter("remarks");
				        	    
				        	    Amendment=request.getParameter("Amendment");
				        	   
				        	    seniority_no=Integer.parseInt(request.getParameter("seniority_id"));
				        	    
				        	    
				        	    
				        	    sql="UPDATE HRM_SENIORITY_LIST_MST " +
				        	    		" SET CADRE_ID            =?, " +
				        	    		"  PROCEEDINGS_DATE      =to_date(?,'dd-mm-yyyy'), " +
				        	    		"  PROCEEDINGS_REF_NO    =?, " +
				        	    		"  NEW_OR_AMENDMENT_FLAG =?, " +
				        	    		"  ORIGINAL_ORDER_REF_NO =?, " +
				        	    		"  PROCESS_FLOW_ID       =?, " +
				        	    		"  UPDATED_BY_USER_ID    =?, " +
				        	    		"  UPDATED_DATE          =?, " +
				        	    		"  REMARKS               =? " +
				        	    		" WHERE SENIORITY_LIST_ID = ?";
				        	    ps=connection.prepareStatement(sql);
				        	   
                                ps.setInt(1, cadre);
                                ps.setString(2, pro_date);
                                ps.setString(3, pro_no);
                                ps.setString(4, Amendment);
                                ps.setString(5, ori_reg_no);
                                ps.setString(6, "FR");
                                ps.setString(7,updatedby);
                                ps.setTimestamp(8, ts);
                                ps.setString(9, remarks);
                                ps.setInt(10, seniority_no);
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
			        		 seniority_no=Integer.parseInt(request.getParameter("seniority_id"));
			        		 sql="DELETE FROM HRM_SENIORITY_LIST_MST WHERE SENIORITY_LIST_ID = '"+seniority_no+"'";

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
			        	 int cadre_id=Integer.parseInt(request.getParameter("cadre_id"));
			        	 
			        	 xml="<response><command>getdata</command>"; 
			        	 try
			        	 {
			        		 sql="SELECT SENIORITY_LIST_ID,mst.CADRE_ID as CADRE_ID, " +
			        				 "  CADRE_NAME, " +
			        				 "  PROCEEDINGS_DATE, " +
			        				 "  PROCEEDINGS_REF_NO, " +
			        				 "  NEW_OR_AMENDMENT_FLAG, " +
			        				 "  ORIGINAL_ORDER_REF_NO , " +
			        				 "  PROCESS_FLOW_ID, " +
			        				 "  REMARKS " +
			        				 "FROM " +
			        				 "  (SELECT SENIORITY_LIST_ID, " +
			        				 "    CADRE_ID, " +
			        				 "    to_char(PROCEEDINGS_DATE,'dd/mm/yyyy') as PROCEEDINGS_DATE, " +
			        				 "    PROCEEDINGS_REF_NO, " +
			        				 "    NEW_OR_AMENDMENT_FLAG, " +
			        				 "    ORIGINAL_ORDER_REF_NO, " +
			        				 "    PROCESS_FLOW_ID, " +
			        				 "    REMARKS " +
			        				 "  FROM HRM_SENIORITY_LIST_MST where CADRE_ID="+cadre_id+" " +
			        				 "  )mst " +
			        				 "LEFT OUTER JOIN " +
			        				 "  (SELECT CADRE_ID,CADRE_NAME FROM HRM_MST_CADRE " +
			        				 "  )sub " +
			        				 "ON sub.CADRE_ID=mst.CADRE_ID order by SENIORITY_LIST_ID desc ";
			        		 ps=connection.prepareStatement(sql);
			        		 rs=ps.executeQuery();
			        		 while(rs.next())
			        		 {
			        			 xml=xml+"<count><SENIORITY_LIST_ID>"+rs.getInt("SENIORITY_LIST_ID")+"</SENIORITY_LIST_ID>";
			        			 xml=xml+"<CADRE_ID>"+rs.getInt("CADRE_ID")+"</CADRE_ID>";
			        			 xml=xml+"<CADRE_NAME>"+rs.getString("CADRE_NAME")+"</CADRE_NAME>";
			        			 xml=xml+"<PROCEEDINGS_DATE>"+rs.getString("PROCEEDINGS_DATE")+"</PROCEEDINGS_DATE>";
			        			 xml=xml+"<PROCEEDINGS_REF_NO>"+rs.getString("PROCEEDINGS_REF_NO")+"</PROCEEDINGS_REF_NO>";
			        			 xml=xml+"<NEW_OR_AMENDMENT_FLAG>"+rs.getString("NEW_OR_AMENDMENT_FLAG")+"</NEW_OR_AMENDMENT_FLAG>";
			        			 xml=xml+"<ORIGINAL_ORDER_REF_NO>"+rs.getString("ORIGINAL_ORDER_REF_NO")+"</ORIGINAL_ORDER_REF_NO>";
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
