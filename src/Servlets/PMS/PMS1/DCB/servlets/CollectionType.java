/* 
  * Created on : dd-mm-yy 
  * Author     : SINDU
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description
  * 17/09/2011		Add the Beneficiary Status to 'L'  
  * 20/09/2011		Add the Meter Status to 'L'
  *---------|---------------|--------------------------------------------------- 
  */
package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Servlets.Security.classes.UserProfile;

public class CollectionType extends HttpServlet 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        

    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
       Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        try
                  {
                       ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                         String ConnectionString="";
                        
                         String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                         String strdsn=rs.getString("Config.DSN");
                         String strhostname=rs.getString("Config.HOST_NAME");
                         String strportno=rs.getString("Config.PORT_NUMBER");
                         String strsid=rs.getString("Config.SID");
                         String strdbusername=rs.getString("Config.USER_NAME");
                         String strdbpassword=rs.getString("Config.PASSWORD");
                           
                 //        ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 

                          Class.forName(strDriver.trim());
                          connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              try
              {
                statement=connection.createStatement();
                connection.clearWarnings();
              }
              catch(SQLException e)
              {
                  System.out.println("Exception in creating statement:"+e);
              }          
           }
          catch(Exception e)
          {
             System.out.println("Exception in opening connection:"+e);
          }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";

    	int sno = 0;
	String ctyp = null;
	
		
        int page = 1;
        int total = 2;
        int records = 15;
        int start = 1;
        int limit = 10;
        int end = 10;
     
        HttpSession session=request.getSession(false);
        try
        {
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
        String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:"+userid);
        
        
        
        
     	UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
     	int empid = empProfile.getEmployeeId();
     	int oid = 0;
     	try 
     	{
     		PreparedStatement ps = connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
     		ps.setInt(1, empid);
     		result = ps.executeQuery();
     		if (result.next()) 
     		{
     			oid = result.getInt("OFFICE_ID");
     		}
     		result.close();
     		ps.close();
     	} 
     	catch (Exception e) 
     	{
     		System.out.println(e);
     	}
     
        
        
        
        
        
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        
        
        
        
        
        
        /////////////////////// Command Parameters //////////////////////////
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        ///////////////////////////////////////////////////////////////////////
        
        
	
       
        
        
        /////////////////////// Pagination Parameters //////////////////////////
        try {
        page = Integer.parseInt(request.getParameter("page"));
        System.out.println("page : " + page);
        }
        catch (Exception e) {
        System.out.println("Exception getting 'page' parameter ==> " + e);
        }
        
        
        try {
        limit = Integer.parseInt(request.getParameter("limit"));
        System.out.println("limit : " + limit);
        }
        catch (Exception e) {
        System.out.println("Exception getting 'limit' parameter ==> " + e);
        }
        ///////////////////////////////////////////////////////////////////////


       
       
        
       
        /////////////////////// Other Parameters //////////////////////////
		try
        {
		sno= Integer.parseInt(request.getParameter("sno"));
        	System.out.println("sno : " + sno);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching sno ===> " + e);
        }        
        
        
        try
        {
        	ctyp = request.getParameter("ctyp");
        	System.out.println("ctyp  : "+ ctyp );
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching ctyp  ===> " + e);
        }

 
        ///////////////////////////////////////////////////////////////////////
        
        
        

        
        
        
        /*******************************************************
         * 						DELETE
         *******************************************************/
        if(strCommand.equalsIgnoreCase("Delete"))
        {
        	System.out.println("\n*************\nDelete\n**************\n");
            xml="<response><command>Delete</command>";
            try 
            {

            	String sql = "DELETE " +
							"FROM PMS_DCB_RECEIPT_TYPE " +
							"WHERE COLLECTION_TYPE_SNO = ?";
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setInt(1, sno);
				ps.executeUpdate();

				xml=xml+"<flag>success</flag>";
	            xml=xml+"<sno>" + sno + "</sno>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Deleting record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        

        
        /*******************************************************
         * 						UPDATE
         *******************************************************/
       
        else if(strCommand.equalsIgnoreCase("Update"))
        {
        	System.out.println("\n*************\nUpdate\n**************\n");
            xml="<response><command>Update</command>";
            
         
           
            
            ///////////////////////// DUPLICATION CHECK ///////////////////////////////////////////////////////
            int dup = 0;
            try 
            {
             result = statement.executeQuery(" SELECT COUNT(*) AS dup " +
											"  FROM PMS_DCB_RECEIPT_TYPE " +
											"  WHERE COLLECTION_TYPE_DESC = '" + ctyp + "' " +
											"    AND COLLECTION_TYPE_SNO != " + sno);
	             try
	             {
	                 if(result.next())
	                 {
	                	 dup=result.getInt("dup");
	                 }
	             }catch(Exception e)
	             {
	            	 System.out.println("Exception in the getting values of dup: " + e);
	             }
	             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in dup == "+e1);
            }
            System.out.println("Duplicate - "+dup);
            /////////////////////////////////////////////////////////////////////////////////////////////////// 
            
 
            
            
            
            if(dup==0)
            {
	            try 
	            {
	            	String sql = " UPDATE PMS_DCB_RECEIPT_TYPE " +
								"SET COLLECTION_TYPE_DESC = ? " +
								"WHERE COLLECTION_TYPE_SNO = ?";
			   	 	PreparedStatement ps = connection.prepareStatement(sql);

				   	 ps.setString(1,ctyp );
				   	 ps.setInt(2,sno);
						
				   	 ps.executeUpdate();
				   	 	
				   	 xml=xml+"<flag>success</flag>";
	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception in Updating record ===> "+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
            }
            else
            {
            	xml=xml+"<flag>duplicate</flag></response>";
            	System.out.println("Duplicate Entry Found - Failed to Update");
            }
        }
                
        
        
        
        
        /*******************************************************
         * 						ADD
         *******************************************************/
       
        else if(strCommand.equalsIgnoreCase("Add"))
        {
        	System.out.println("\n*************\nAdd\n**************\n");
            xml="<response><command>Add</command>";

  
            
            
            ///////////////////////// DUPLICATION CHECK ///////////////////////////////////////////////////////
            int dup = 0;
            try 
            {
            	System.out.println(" SELECT COUNT(*) AS dup " +
												"  FROM PMS_DCB_RECEIPT_TYPE " +
												"  WHERE COLLECTION_TYPE_DESC = '" + ctyp + "'");
            	
                result = statement.executeQuery(" SELECT COUNT(*) AS dup " +
												"  FROM PMS_DCB_RECEIPT_TYPE " +
												"  WHERE COLLECTION_TYPE_DESC = '" + ctyp + "'");
	             try
	             {
	                 if(result.next())
	                 {
	                	 dup=result.getInt("dup");
	                 }
	             }catch(Exception e)
	             {
	            	 System.out.println("Exception in the getting values of dup: " + e);
	             }
	             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in dup"+e1);
            }
            System.out.println("Duplicate - "+dup);
            /////////////////////////////////////////////////////////////////////////////////////////////////// 
            
 
            
            
            if(dup==0)
            {
            	
            	
                try 
                {
                 result = statement.executeQuery("SELECT " +
												"	(CASE WHEN MAX(COLLECTION_TYPE_SNO) IS NULL THEN 1 " +
												"		ELSE MAX(COLLECTION_TYPE_SNO)+1 " +
												"	END) AS maxsno " +
												"FROM PMS_DCB_RECEIPT_TYPE ");
    	             try
    	             {
    	                 if(result.next())
    	                 {
    	                	 sno=result.getInt("maxsno");
    	                 }
    	             }catch(Exception e)
    	             {
    	            	 System.out.println("Exception in the getting values of maxsno: " + e);
    	             }
    	             result.close();
                }
                catch(Exception e1)
                {
                	System.out.println("Exception is in maxsno"+e1);
                }
                System.out.println("maxsno - "+sno);

            	
	            try 
	            {
		             String sqlAdd = "INSERT INTO PMS_DCB_RECEIPT_TYPE " +
									"( " +
									"  COLLECTION_TYPE_SNO, " + 
									"  COLLECTION_TYPE_DESC " +
									")VALUES(?,?)";
					
					  	PreparedStatement ps = connection.prepareStatement(sqlAdd);
					  	ps.setInt(1,sno);
					  	ps.setString(2,ctyp );
						
					 	ps.executeUpdate();

	 	
			   	 	xml=xml+"<flag>success</flag>";
	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception in Adding record ===> "+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
	            xml=xml+"</response>";
	            
            }
            else
            {
            	xml=xml+"<flag>duplicate</flag></response>";
            	System.out.println("Duplicate Entry Found");
            }
        }
        else if(strCommand.equals("Get"))
        { 
        	System.out.println("\n*************\nGet\n**************\n");
            xml="<response><command>Get</command>";
      
            try 
            {
	             result = statement.executeQuery(" SELECT COUNT(*) AS rec " +
												 " FROM PMS_DCB_RECEIPT_TYPE");
	        	 if(result.next())
	        	 {
	        		 records = result.getInt("rec");
	        	 }
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Counting Records query ==> "+e1);
            }
	            	 
	            	 
            start = (page-1)*limit + 1;
            end   = start + limit - 1;
            total = (int)Math.ceil((float)records/limit);
            
            
            try 
            {
            	
             result = statement.executeQuery("SELECT * FROM " +
											"( " +
											"	SELECT  " +
											"  		ROWNUM AS ID, " +
											"  		COLLECTION_TYPE_SNO,  " +
											" 		COLLECTION_TYPE_DESC  " +
											"	FROM" +
											"	( " +
											"		SELECT  " +
											"			COLLECTION_TYPE_SNO,  " +
											"  			COLLECTION_TYPE_DESC  " +
											"		FROM PMS_DCB_RECEIPT_TYPE " +
											"		ORDER BY COLLECTION_TYPE_SNO " +
											"	) " +
											             
										") " +
											"WHERE ID BETWEEN " + start + " AND " + end);

             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 
                 xml = xml + "<page>"+page+"</page>";
                 xml = xml + "<total>"+total+"</total>";
                 xml = xml + "<records>"+records+"</records>";

                 while(result.next())
                 { 
         		 	xml+= "<row>";
         	
						xml+= "<sno>" + result.getInt("COLLECTION_TYPE_SNO") + "</sno>";
						xml+= "<ctyp >" + result.getString("COLLECTION_TYPE_DESC") + "</ctyp >";
						
	     			xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in fetching values from Resultset - GET: " + e);
             }
             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Get == "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        
        

    
        
        
        System.out.println("\nCOLLECTION TYPE MASTER XML RESPONSE:");
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
    
    }
   
}

