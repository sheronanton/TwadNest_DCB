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

import javax.servlet.*;
import javax.servlet.http.*;

public class Consumer extends HttpServlet 
{
   
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	Controller obj=new Controller();
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        String new_cond=Controller.new_cond;
        try
        {
        			             
        				connection=obj.con();      
        				statement=connection.createStatement();
        				connection.clearWarnings();
                        
        }
        catch(Exception e)
        {
             System.out.println("Exception in openeing connection:"+e);
         }
        String thisServer= getServletConfig().getServletContext().getServerInfo();
        System.out.println("SERVELET VERSION IS " + thisServer);
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";

        //PAGINATION VARIABLES//
        int page = 1;
        int total = 2;
        int records = 15;
        int start = 1;
        int limit = 10;
        int end = 10;
        ////////////////////////
        
		String dis = "%";
		String blk = "%";
		String pan = "";
		
		String ULBtype = "%";
		String ULBgrade = "%";
		String area = "%";
		String ulb = "";

    	int cid = 0;
    	int maxben=0;
		int priv = 0;
		int ctype = 0;
		String cname = null; 
		
		String adr1off = null;
		String adr2off = null;
		String adr3off = null;
		String pinoff = null;
		String llnooff = null;
		String celloff = null;
		String emailoff = null;
		
		String adr1 = null;
		String adr2 = null;
		String adr3 = null;
		String pin = null;
		String llno = null;
		String cell = null;
		String email = null;

		String group = null;
		String ccode = null;
		int consumption = 0;
        
		int oid = 0;
		
        HttpSession session=request.getSession(false);
        try
        {
            if(session==null)
            {
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            }
        }catch(Exception e)
        {
        	System.out.println("Redirect Error :"+e);
        }
        String userid=(String)session.getAttribute("UserId");
        
        response.setContentType("text/xml");
        
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        
        try
        {
        	strCommand = request.getParameter("command");     
        	//System.out.println("strCommand : " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        /*******************************************************************
         *                 Pagination parameters
         *******************************************************************/
        
        try {
        page = Integer.parseInt(request.getParameter("page"));
        }
        catch (Exception e) {
        }
        
        
        try {
        limit = Integer.parseInt(request.getParameter("limit"));
        }
        catch (Exception e) {

        }
        
        /******************************************************************/


        System.out.println("DCB-->Consumer-->Command("+strCommand+")-->Office("+oid+")-->Ben ("+cid+")");
       
		try
        {
			oid= Integer.parseInt(request.getParameter("oid"));
        	 
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching oid ===> " + e);
        }        
        
        
		try
        {
			cid= Integer.parseInt(request.getParameter("cid"));
        	 
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching cid ===> " + e);
        }        
        
        
        try
        {
        	dis = request.getParameter("dis");
            if((dis=="")||((dis==null)))
            {
            	dis = "%";
            	 
            }
         
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching dis ===> " + e);
            dis = "%";
        }

      
        /////////////////////// Town Panchayat //////////////////////////
        try
        {
        	blk = request.getParameter("blk");
            if((blk=="")||(blk==null))
            {
            	blk = "%";
            	 
            }
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching blk ===> " + e);
            blk = "%";
        }

        
        try
        {
        	pan = request.getParameter("pan");
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching pan ===> " + e);
        }
        ///////////////////////////////////////////////////////////////////////
        
        
        
        
        try
        {
        	ulb = request.getParameter("ulb");
             
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching ulb ===> " + e);
        }
        

        try
        {
        	priv = Integer.parseInt(request.getParameter("priv"));
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching priv ===> " + e);
        }

      
        try
        {
        	ctype = Integer.parseInt(request.getParameter("ctype"));
        	
        	   System.out.println("ctype"+ctype);
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching ctype ===> " + e);
        }

        
        try
        {
        	cname = request.getParameter("cname");
           
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching cname ===> " + e);
        }

      
        try
        {
        	adr1off = request.getParameter("adr1off");
             
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching adr1off ===> " + e);
        }


        try
        {
        	adr2off = request.getParameter("adr2off");
           
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching adr2off ===> " + e);
        }

        
        try
        {
        	adr3off = request.getParameter("adr3off");
           
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching adr3off ===> " + e);
        }

        
        try
        {
        	pinoff = request.getParameter("pinoff");
             
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching pinoff ===> " + e);
        }

      
        try
        {
        	llnooff = request.getParameter("llnooff");
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching dis ===> " + e);
        }

        
        try
        {
        	celloff = request.getParameter("celloff");
             
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching celloff ===> " + e);
        }
      
        
        try
        {
        	emailoff = request.getParameter("emailoff");
           
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching emailoff ===> " + e);
        }

        
        try
        {
        	adr1 = request.getParameter("adr1");
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching adr1 ===> " + e);
        }

        
        try
        {
        	adr2 = request.getParameter("adr2");
             
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching adr2 ===> " + e);
        }
      
        
        try
        {
        	adr3 = request.getParameter("adr3");
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching adr3 ===> " + e);
        }

        
        try
        {
        	pin = request.getParameter("pin");
             
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching pin ===> " + e);
        }

        
        try
        {
        	llno = request.getParameter("llno");
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching llno ===> " + e);
        }

        
        try
        {
        	cell = request.getParameter("cell");
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching cell ===> " + e);
        }

      
        try
        {
        	email = request.getParameter("email");
            
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching email ===> " + e);
        }

        
        try
        {
        	group = request.getParameter("group");
             
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching group ===> " + e);
        }
        
        
        try
        {
        	ccode = request.getParameter("ccode");
          
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching ccode ===> " + e);
        }
       
        
        try
        {
        	consumption = Integer.parseInt(request.getParameter("consumption"));
             
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching consumption ===> " + e);
        }
      //cid dis blk pan priv ctype cname adr1off adr2off adr3off pinoff llnooff celloff emailoff adr1 adr2 adr3 pin llno cell email group ccode consumption
  

        
        
        
        
        /*
         * Execute the task adhered to the requested command
         */
        
       
        if(strCommand.equalsIgnoreCase("Division"))
        {
        	 
            xml="<response><command>Division</command>";
	        try 
	        {
	         result = statement.executeQuery(	"SELECT OFFICE_NAME " +
												"FROM COM_MST_OFFICES " +
												"WHERE OFFICE_ID = " +
												"        ( " +
												"          SELECT OFFICE_ID " +
												"          FROM HRM_EMP_CURRENT_POSTING " +
												"          WHERE EMPLOYEE_ID IN " + 
												"                        ( " +
												"                          SELECT EMPLOYEE_ID " +
												"                          FROM SEC_MST_USERS " +
												"                          WHERE USER_ID= '" + userid + "' " +
												"                        ) " +
												"        )");
	             try
	             {
	                 if(result.next())
	                 {
	                	 xml+= "<division>" + result.getString("OFFICE_NAME") + "</division>";
	                 }
	                 xml=xml+"<flag>success</flag>";
	             }catch(Exception e)
	             {
	            	 System.out.println("Exception in reading Division resultset: " + e);
	            	 xml=xml+"<flag>failure</flag>";
	             }
	             result.close();
	        }
	        catch(Exception e1)
	        {
	        	System.out.println("Exception in Division query == "+e1);
	        	xml=xml+"<flag>failure</flag>";
	        }
	        xml=xml+"</response>";
        }

        
        else if(strCommand.equalsIgnoreCase("Delete"))
        {
         
            xml="<response><command>Delete</command>";
            try 
            {
            String month=obj.setValue("month", request);
            String year=obj.setValue("year", request);	
            	
            	
             String sqlDelete = "Update PMS_DCB_MST_BENEFICIARY  set status='C' " +
            	 				" , CANCEL_MONTH=?,CANCEL_YEAR=? WHERE BENEFICIARY_SNO = ? and BENEFICIARY_SNO not in (Select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where meter_status='L' )";
		   	 PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setInt(1, Integer.parseInt(month));
		   	 ps.setInt(2, Integer.parseInt(year));
		   	 ps.setInt(3, cid);
		   	 int row = ps.executeUpdate();
		   	 System.out.println("Consumer----------->"+row);
		   	 if (row>=1)
		   	 {
		   	 	xml=xml+"<flag>success</flag>";
		   	 	xml=xml+"<cid>" + cid + "</cid>";
		   	 }else
		   	 {
		   		xml=xml+"<flag>failure</flag>";
		   	 	xml=xml+"<cid>" + cid + "</cid>";
		   	 }
		   	 
		   	 	  
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Deleting record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
                
        else if(strCommand.equalsIgnoreCase("Update"))
        {
        	System.out.println("\n*************\nUpdate\n**************\n");
            xml="<response><command>Update</command>";
            
            
 
            
            
            int dup = 0;
            try 
            {
             result = statement.executeQuery("SELECT COUNT(*) AS dup " +
            		 						 "FROM PMS_DCB_MST_BENEFICIARY " +
             								 "WHERE "+new_cond+" OFFICE_ID = " + oid +
             								 "  AND OTHERS_PRIVATE_SNO = " + priv +
             								 "  AND VILLAGE_PANCHAYAT_SNO = " + pan +
											 "  AND URBANLB_SNO = " + ulb +
											 "  AND BENEFICIARY_SNO != " + cid);
	             try
	             {
	                 if(result.next())
	                 {
	                	 dup=result.getInt("dup");
	                 }
	             }catch(Exception e)
	             {
	            	 System.out.println("Exception in the getting values OF dup: " + e);
	             }
	             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in dup"+e1);
            }
         
            
            if(dup==0)
            {
	            try 
	            {
	             String sqlUpdate = "UPDATE PMS_DCB_MST_BENEFICIARY " +
									"SET DISTRICT_CODE       = ?, " +
									"  BLOCK_SNO             = ?, " +
									"  OTHERS_PRIVATE_SNO    = ?, " +
									"  VILLAGE_PANCHAYAT_SNO = ?, " +
									"  URBANLB_SNO           = ?, " +
									"  BENEFICIARY_TYPE_ID   = ?, " +
									"  BENEFICIARY_NAME      = ?, " +
									"  OFFICE_ADDRESS1       = ?, " +
									"  OFFICE_ADDRESS2       = ?, " +
									"  OFFICE_ADDRESS3       = ?, " +
									"  OFFICE_PIN_CODE       = ?, " +
									"  OFFICE_LANDLINE_NO    = ?, " +
									"  OFFICE_MOBILE_NO      = ?, " +
									"  OFFICE_EMAIL          = ?, " +
									"  BILLING_ADDRESS1      = ?, " +
									"  BILLING_ADDRESS2      = ?, " +
									"  BILLING_ADDRESS3      = ?, " +
									"  BILLING_PIN_CODE      = ?, " +
									"  BILLING_LANDLINE_NO   = ?, " +
									"  BILLING_MOBILE_NO     = ?, " +
									"  BILLING_EMAIL         = ?, " +
									"  BENEFICIARY_GROUP_ID  = ?, " +
									"  BENEFICIARY_USER_CODE = ?, " +
									"  UPDATED_BY_USER_ID    = ?, " +
									"  UPDATED_DATE          = clock_timestamp() ," +
									"  BEN_CONS_CATEGORY     = ? " +
								//	"  OFFICE_ID,            = ?," +
								//	"  BEN_DIV_SNO           = ?" +
									"WHERE BENEFICIARY_SNO   = ? ";
			   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
					
					ps.setInt(1,Integer.parseInt(dis));
					ps.setInt(2,Integer.parseInt(blk));
					ps.setInt(3,priv);
					ps.setInt(4,Integer.parseInt(pan));
					ps.setInt(5,Integer.parseInt(ulb));
					ps.setInt(6,ctype);
					ps.setString(7,cname);
	
					ps.setString(8,adr1off);
					ps.setString(9,adr2off);
					ps.setString(10,adr3off);
					ps.setString(11,pinoff);
					ps.setString(12,llnooff);
					ps.setString(13,celloff);
					ps.setString(14,emailoff);
					
					ps.setString(15,adr1);
					ps.setString(16,adr2);
					ps.setString(17,adr3);
					ps.setString(18,pin);
					ps.setString(19,llno);
					ps.setString(20,cell);
					ps.setString(21,email);
	
					ps.setString(22,group);
					ps.setString(23,ccode);
					ps.setString(24,userid);
					
					ps.setInt(25,consumption);
					
					ps.setInt(26,cid);
					
			   	 	ps.executeUpdate();
			   	 	 
			   	 	xml=xml+"<flag>success</flag>";
			   	 	
			   	 	PreparedStatement proc_stmt=null;
			  		try 
			  		{
						proc_stmt = connection.prepareCall("call PMS_DCB_NEW_BEN_ID_UPDATE (?) ");
						proc_stmt.setInt(1, oid);
				  		proc_stmt.execute();   		  			   // P Month         month_process       
					} catch (SQLException e) 
					{
						e.printStackTrace();
					}
			   	// cid dis blk pan priv ctype cname adr1off adr2off adr3off pinoff llnooff celloff emailoff adr1 adr2 adr3 pin llno cell email group ccode
	/*		   	 	
					xml+= "<cid>" + cid + "</cid>";
					xml+= "<dis>" + dis + "</dis>";
					xml+= "<blk>" + blk + "</blk>";
					xml+= "<pan>" + pan + "</pan>";
					xml+= "<priv>" + priv + "</priv>";
	
					xml+= "<ctype>" + ctype + "</ctype>";
					xml+= "<cname>" + cname + "</cname>";
	
					xml+= "<adr1off>" + adr1off + "</adr1off>";
					xml+= "<adr2off>" + adr2off + "</adr2off>";
					xml+= "<adr3off>" + adr3off + "</adr3off>";
					xml+= "<pinoff>" + pinoff + "</pinoff>";
					xml+= "<llnooff>" + llnooff + "</llnooff>";
					xml+= "<celloff>" + celloff + "</celloff>";
					xml+= "<emailoff>" + emailoff + "</emailoff>";
					
					xml+= "<adr1>" + adr1 + "</adr1>";
					xml+= "<adr2>" + adr2 + "</adr2>";
					xml+= "<adr3>" + adr3 + "</adr3>";
					xml+= "<pin>" + pin + "</pin>";
					xml+= "<llno>" + llno + "</llno>";
					xml+= "<cell>" + cell + "</cell>";
					xml+= "<email>" + email + "</email>";
	
					xml+= "<consumption>" + consumption + "</consumption>";
					
					xml+= "<group>" + group + "</group>";
					xml+= "<ccode>" + ccode + "</ccode>";
	*/
	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception in Updating record ===> "+e1);
	            	xml=xml+"<flag>failure</flag>";
	            }
            }
            else
            {
            	xml=xml+"<flag>duplicate</flag>";
            	System.out.println("Duplicate Beneficiary Found - Failed to Update");
            }
            xml=xml+"</response>";
        }
                
        else if(strCommand.equalsIgnoreCase("Add"))
        {
        	System.out.println("\n*************\nAdd\n**************\n");
            xml="<response><command>Add</command>";

  
            
            
            int dup = 0;
            try 
            {
             result = statement.executeQuery("SELECT COUNT(*) AS dup " +
            		 						 "FROM PMS_DCB_MST_BENEFICIARY " +
             								 "WHERE "+new_cond+" OFFICE_ID = " + oid +
             								 "  AND OTHERS_PRIVATE_SNO = " + priv +
             								 "  AND VILLAGE_PANCHAYAT_SNO = " + pan +
											 "  AND URBANLB_SNO = " + ulb);
	             try
	             {
	                 if(result.next())
	                 {
	                	 dup=result.getInt("dup");
	                 }
	             }catch(Exception e)
	             {
	            	 System.out.println("Exception in the getting values OF dup: " + e);
	             }
	             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in dup"+e1);
            }
            if(dup==0)
            {
	            
	         
	            try 
	            {
	             result = statement.executeQuery("SELECT " +
	             								 "(CASE WHEN MAX(BENEFICIARY_SNO) IS NULL THEN 1 ELSE MAX(BENEFICIARY_SNO)+1 END) AS MAXCID " +
	            		 						 "FROM PMS_DCB_MST_BENEFICIARY   ");
		             try
		             {
		                 if(result.next())
		                 {
			     			 cid=result.getInt("MAXCID");
		                 }
		             }catch(Exception e)
		             {
		            	 System.out.println("Exception in the getting values OF cid: " + e);
		             }
		             result.close();
		             //response.setHeader("cache-control","no-cache");
	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in MAX cid"+e1);
	            }
		         
	            try 
	            {
	             result = statement.executeQuery("SELECT " +
	             								 "(CASE WHEN MAX(BEN_DIV_SNO) IS NULL THEN 1 ELSE MAX(BEN_DIV_SNO)+1 END) AS MAXBEN " +
	            		 						 "FROM PMS_DCB_MST_BENEFICIARY " +
	             								 "WHERE  "+new_cond+" OFFICE_ID="+oid);
		             try
		             {
		                 if(result.next())
		                 {
			     			 maxben=result.getInt("MAXBEN");
		                 }
		             }catch(Exception e)
		             {
		            	 System.out.println("Exception in the getting values OF maxben: " + e);
		             }
		             result.close();
		             //response.setHeader("cache-control","no-cache");
	            }
	            catch(Exception e1)
	            {
	            	System.out.println("Exception is in maxben"+e1);
	            }
	            try 
	            {
	            	
	            	String div=obj.setValue("div", request);
	    			if ("0".equals(div) || div==null) div=Integer.toString(oid);	
	            	
	             String sqlAdd = "INSERT INTO PMS_DCB_MST_BENEFICIARY " +
								"  ( DISTRICT_CODE, " +
								"    BLOCK_SNO, " +
								"    OTHERS_PRIVATE_SNO, " +
								"    VILLAGE_PANCHAYAT_SNO, " +
								"    URBANLB_SNO, " +
								"    BENEFICIARY_TYPE_ID, " +
								"    BENEFICIARY_NAME, " +
								"    OFFICE_ADDRESS1, " +
								"    OFFICE_ADDRESS2, " +
								"    OFFICE_ADDRESS3, " +
								"    OFFICE_PIN_CODE, " +
								"    OFFICE_LANDLINE_NO, " +
								"    OFFICE_MOBILE_NO, " +
								"    OFFICE_EMAIL, " +
								"    BILLING_ADDRESS1, " +
								"    BILLING_ADDRESS2, " +
								"    BILLING_ADDRESS3, " +
								"    BILLING_PIN_CODE, " +
								"    BILLING_LANDLINE_NO, " +
								"    BILLING_MOBILE_NO, " +
								"    BILLING_EMAIL, " +
								"    BENEFICIARY_GROUP_ID, " +
								"    BENEFICIARY_USER_CODE, " +
								"    UPDATED_BY_USER_ID, " +
								"    UPDATED_DATE, " +
								"    BENEFICIARY_SNO," +
								"	 BEN_CONS_CATEGORY," +
								"	 OFFICE_ID," +
								"	 BEN_DIV_SNO,STATUS,ADDED_TO_BEN_NAME,OFFICE_ID_BEN) " +
								" VALUES " +
								"  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,clock_timestamp(),?,?,?,?,'L',?,?::int)";
	
	             	PreparedStatement ps = connection.prepareStatement(sqlAdd);
					ps.setInt(1,Integer.parseInt(dis));
					ps.setInt(2,Integer.parseInt(blk));
					ps.setInt(3,priv);
					ps.setInt(4,Integer.parseInt(pan));
					ps.setInt(5,Integer.parseInt(ulb));
					ps.setInt(6,ctype);
					ps.setString(7,cname);
	
					ps.setString(8,adr1off);
					ps.setString(9,adr2off);
					ps.setString(10,adr3off);
					ps.setString(11,pinoff);
					ps.setString(12,llnooff);
					ps.setString(13,celloff);
					ps.setString(14,emailoff);
					
					ps.setString(15,adr1);
					ps.setString(16,adr2);
					ps.setString(17,adr3);
					ps.setString(18,pin);
					ps.setString(19,llno);
					ps.setString(20,cell);
					ps.setString(21,email);
	
					ps.setString(22,group);
					ps.setString(23,ccode); 
					ps.setString(24,userid);
					
					ps.setInt(25,cid);
					ps.setInt(26,consumption);
					ps.setInt(27,oid);
					ps.setInt(28,maxben);
					ps.setString(29,cname);
					ps.setString(30,div); 
			   	 	ps.executeUpdate();  
			   	 	 
			   	 	xml=xml+"<flag>success</flag>";
			   	 	
			   	 	double interestRate;
			   	 	
			   	 	if(ctype <=6) {
			   	 		interestRate = 0.5;
			   	 	}else {
				   	 	interestRate = 2;

			   	 	}
			   	 	String benIntRateUpdate = "update pms_dcb_mst_beneficiary set int_rate = "+interestRate+" where beneficiary_sno = "+cid+"";
			   	 	PreparedStatement ps1 = connection.prepareStatement(benIntRateUpdate);
			   	 	int queryResult = ps1.executeUpdate();
			   	 	
			   	 	
			   	 	
			   	 	PreparedStatement proc_stmt=null;
			  		try 
			  		{
						proc_stmt = connection.prepareCall("call PMS_DCB_NEW_BEN_ID_UPDATE (?) ");
						proc_stmt.setInt(1, oid);
				  		proc_stmt.execute();   		  			   // P Month         month_process       
					} catch (SQLException e) 
					{
						e.printStackTrace();
					}
	
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
            String benTyp = ""+ctype;
            int benTyp1 = ctype;
           // String benTyp = Integer.toString(ctype);
            System.out.println("ctype=="+ctype);
            System.out.println("benTyp=="+benTyp);
            
            
     //       System.out.println("ctype=="+ctype.getClass().getSimpleName());
           
            if(ctype == 0)
            {
            	benTyp = "%";
            }
            String child_div="0";
            String OFFICE_LEVEL_ID="0";
            try
			{
				child_div=obj.setValue("child_div", request);
				System.out.println("child_div=="+child_div);
				System.out.println("new_cond==="+new_cond);
				System.out.println("benTyp=="+benTyp);
				OFFICE_LEVEL_ID=obj.getValue("com_mst_offices", "OFFICE_LEVEL_ID","where OFFICE_ID="+oid+ "  ");
			}catch (Exception e) 
			{
				child_div="0";
			}
			String div_str="";
			if ("DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
			{
				div_str=" OFFICE_ID = " + oid;
			}else 
			{
				div_str=" OFFICE_ID_BEN = " + child_div;
				//div_str=" c = " + oid;

			}
             
            
            
            try 
            {
            	System.out.println("benTyp=="+benTyp+benTyp.getClass().getSimpleName());
	             result = statement.executeQuery("SELECT COUNT(*) AS rec " +
												"  FROM PMS_DCB_MST_BENEFICIARY " +
												"  WHERE  "+new_cond+div_str+//" OFFICE_ID = " + oid +
														"	AND	BENEFICIARY_TYPE_ID::varchar LIKE '" + benTyp + "'");
									
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
            System.out.println("start------->"+start);
            end   = start + limit - 1;
            System.out.println("end------->"+end);

            total = (int)Math.ceil((float)records/limit);
            try 
            {
            	
            	 
            String sql=	"SELECT * FROM " +
	            	"( " +
		           " 	SELECT " + 
		//	       "     	ROWNUM AS ID,   " +
	//	           " row_num() OVER() ROWNUM, " +
	               "   row_number() OVER () ROWNUM,"+
			       "     	BENEFICIARY_SNO,  " +
			       "     	DISTRICT_CODE,   " +
			       "     	BLOCK_SNO,   " +
			       "     	OTHERS_PRIVATE_SNO, " +  
			       "     	VILLAGE_PANCHAYAT_SNO,  " + 
			       "     	URBANLB_SNO,   " +
			       "     	BENEFICIARY_TYPE_ID, " +  
			       "     	BEN_TYPE_DESC,   " +
			       "     	BENEFICIARY_NAME,   " +
			       "     	OFFICE_ADDRESS1,   " +
			       "     	OFFICE_ADDRESS2,   " +
			       "     	OFFICE_ADDRESS3,   " +
			       "     	OFFICE_PIN_CODE,   " +
			       "     	OFFICE_LANDLINE_NO,   " +
			       "     	OFFICE_MOBILE_NO,   " +
			       "     	OFFICE_EMAIL,   " +
			       "     	BILLING_ADDRESS1,   " +
			       "     	BILLING_ADDRESS2,   " +
			       "     	BILLING_ADDRESS3,   " +
			       "     	BILLING_PIN_CODE,   " +
			       "     	BILLING_LANDLINE_NO,   " +
			       "     	BILLING_MOBILE_NO,   " +
			       "     	BILLING_EMAIL,   " +
			       "     	BENEFICIARY_GROUP_ID, " +  
			       "     	BENEFICIARY_USER_CODE,   " +
			       "     	BEN_CONS_CATEGORY,   " +
			       "     	OFFICE_ID,   " +
			       "     	BEN_DIV_SNO   " +
		           " 	FROM   " +
		           " 	(  " +
			        "    	SELECT " +
				    "        	BENEFICIARY_SNO, " + 
				    "        	DISTRICT_CODE,   " +
				    "        	BLOCK_SNO,   " +
				    "        	OTHERS_PRIVATE_SNO, " +  
				    "        	VILLAGE_PANCHAYAT_SNO,  " + 
				    "         	URBANLB_SNO,   " +
				    "        	BENEFICIARY_TYPE_ID, " +  
				    "        	BEN_TYPE_DESC,   " +
				    "        	BENEFICIARY_NAME,   " +
				    "        	OFFICE_ADDRESS1,   " +
				    "        	OFFICE_ADDRESS2,   " +
				    "        	OFFICE_ADDRESS3,   " +
				    "        	OFFICE_PIN_CODE,   " +
				    "        	OFFICE_LANDLINE_NO,   " +
				    "        	OFFICE_MOBILE_NO,   " +
				    "        	OFFICE_EMAIL,   " +
				    "        	BILLING_ADDRESS1,   " +
				    "        	BILLING_ADDRESS2,   " +
				    "        	BILLING_ADDRESS3,   " +
				    "        	BILLING_PIN_CODE,   " +
				    "        	BILLING_LANDLINE_NO,   " +
				    "        	BILLING_MOBILE_NO,   " +
				    "        	BILLING_EMAIL,   " +
				    "        	BENEFICIARY_GROUP_ID, " +  
				    "        	BENEFICIARY_USER_CODE,   " +
				    "        	BEN_CONS_CATEGORY,   " +
				    "        	OFFICE_ID,   " +
				    "        	BEN_DIV_SNO   " +
			        "    	FROM PMS_DCB_MST_BENEFICIARY " +  
			        "    	JOIN PMS_DCB_BEN_TYPE   " +
			        "    	ON BENEFICIARY_TYPE_ID = BEN_TYPE_ID " +  
			        "    	WHERE  "+new_cond+" "+div_str+
			      "    		AND	BENEFICIARY_TYPE_ID::varchar LIKE '%" + benTyp + "%'" + 
			        //"    		AND	BENEFICIARY_TYPE_ID ="+benTyp1+  
			        "    	ORDER BY BENEFICIARY_TYPE_ID, BENEFICIARY_NAME " +  
		            "	) as opt1 " +
	            	") as opt2 " +
	      //      	"WHERE ID BETWEEN " + start + " AND " + end;
          	   	"WHERE ROWNUM BETWEEN " + start + " AND " + end;

            	System.out.println("SQL====9=>"+sql);
             result = statement.executeQuery(sql);
             						 
             try
             	
             {
            	 xml=xml+"<flag>success</flag>";
            	 
                 xml = xml + "<page>"+page+"</page>";
                 xml = xml + "<total>"+total+"</total>";
                 xml = xml + "<records>"+records+"</records>";
                 int ben_sno=0;
                 while(result.next())
                 { 
         		 	xml+= "<row>";
         		 		ben_sno=result.getInt("BENEFICIARY_SNO");
	     				xml+= "<cid>" + ben_sno + "</cid>";
	     				xml+= "<dis>" + result.getInt("DISTRICT_CODE") + "</dis>";
	     				xml+= "<blk>" + result.getInt("BLOCK_SNO") + "</blk>";
	     				xml+= "<pan>" + result.getInt("VILLAGE_PANCHAYAT_SNO") + "</pan>";
	     				xml+= "<priv>" + result.getInt("OTHERS_PRIVATE_SNO") + "</priv>";
	     				xml+= "<ulb>" + result.getInt("URBANLB_SNO") + "</ulb>";
	
	     				xml+= "<ctype>" + result.getInt("BENEFICIARY_TYPE_ID") + "</ctype>";
	     			//	xml+= "<ctype>" + result.getString("BENEFICIARY_TYPE_ID") + "</ctype>";
	     				xml+= "<ctypedesc>" + result.getString("BEN_TYPE_DESC") + "</ctypedesc>";
	     				xml+= "<cname>" + result.getString("BENEFICIARY_NAME") + " ( "+ben_sno+" ) </cname>";
	
	     				xml+= "<adr1off>" + result.getString("OFFICE_ADDRESS1") + "</adr1off>";
	     				xml+= "<adr2off>" + result.getString("OFFICE_ADDRESS2") + "</adr2off>";
	     				xml+= "<adr3off>" + result.getString("OFFICE_ADDRESS3") + "</adr3off>";
	     				xml+= "<pinoff>" + result.getString("OFFICE_PIN_CODE") + "</pinoff>";
	     				xml+= "<llnooff>" + result.getString("OFFICE_LANDLINE_NO") + "</llnooff>";
	     				xml+= "<celloff>" + result.getString("OFFICE_MOBILE_NO") + "</celloff>";
	     				xml+= "<emailoff>" + result.getString("OFFICE_EMAIL") + "</emailoff>";
	     				
	     				xml+= "<adr1>" + obj.isNull(result.getString("BILLING_ADDRESS1"),2) + "</adr1>";
	     				xml+= "<adr2>" + obj.isNull(result.getString("BILLING_ADDRESS2"),2) + "</adr2>";
	     				xml+= "<adr3>" + obj.isNull(result.getString("BILLING_ADDRESS3") ,2)+ "</adr3>";
	     				xml+= "<pin>" + obj.isNull(result.getString("BILLING_PIN_CODE"),4) + "</pin>";
	     				xml+= "<llno>" + obj.isNull(result.getString("BILLING_LANDLINE_NO"),2) + "</llno>";
	     				xml+= "<cell>" + obj.isNull(result.getString("BILLING_MOBILE_NO"),2) + "</cell>";
	     				xml+= "<email>" + obj.isNull(result.getString("BILLING_EMAIL") ,2)+ "</email>";
	
	     				xml+= "<group>" + result.getString("BENEFICIARY_GROUP_ID") + "</group>";
	     				xml+= "<consumption>" + result.getInt("BEN_CONS_CATEGORY") + "</consumption>";

	     			xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in fetching values from Resultset - GET: " + e);
             }
             result.close();
             //response.setHeader("cache-control","no-cache");
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        
        else if(strCommand.equals("Group"))
        { 
        	System.out.println("\n*************\nGroup\n**************\n");
            xml="<response><command>Group</command>";
            try 
            {
             result = statement.executeQuery("SELECT BENEFICIARY_GROUP_ID, " +
												"  BENEFICIARY_GROUP_DESC " +
												"FROM PMS_DCB_MST_GROUP");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 {
                	xml+= "<row>";
	     				xml+= "<grp>" + result.getString("BENEFICIARY_GROUP_ID") + "</grp>";
	     				xml+= "<group>" + result.getString("BENEFICIARY_GROUP_DESC") + "</group>";
	     			xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values OF Group: " + e);
             }
             result.close();
             //response.setHeader("cache-control","no-cache");
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Group"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  

    
        
        else if(strCommand.equals("Type"))
        { 
        	System.out.println("\n*************\nType\n**************\n");
            xml="<response><command>Type</command>";
            try 
            {
             result = statement.executeQuery("SELECT BEN_TYPE_ID, " +
											"  BEN_TYPE_DESC " +
											"FROM PMS_DCB_BEN_TYPE " +
											"ORDER BY BEN_TYPE_ID");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 {
                	xml+= "<row>";
	     				xml+= "<typ>" + result.getString("BEN_TYPE_ID") + "</typ>";
	     				xml+= "<type>" + result.getString("BEN_TYPE_DESC") + "</type>";
	     			xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values OF Type: " + e);
             }
             result.close();
             //response.setHeader("cache-control","no-cache");
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Type"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  

    
        
        
        
        else if(strCommand.equals("ABC"))
        { 
        	System.out.println("\n*************\nGENERATE CODE - temp code!!\n**************\n");
            xml="<response><command>ABC</command>";
            
            
            
            response.setContentType("text/html");
            
            try 
            {
            	int xy = 0;
            	result = statement.executeQuery("SELECT HABITATION_CODE, " +
												"  STATE_CODE, " +
												"  DISTRICT_CODE, " +
												"  block_sno, " +
												"  PANCH_CODE, " +
												"  VILLAGE_CODE, " +
												"  HAB_CODE, " +
												"  HNAME, " +
												"  UPDATED_BY_USER_ID, " +
												"  UPDATED_DATE, " +
												"  PROCESS_FLOW_STATUS_ID " +
												"FROM COM_MST_HABITATIONS_NO_SNO " +
												"ORDER BY DISTRICT_CODE, block_sno, PANCH_CODE");
            	while(result.next())
            	{
            		try {
							String qry = "INSERT INTO COM_MST_HABITATIONS values(" +
							++xy + "," +
							"'" + result.getString("HABITATION_CODE") + "'," +
							result.getInt("STATE_CODE") + "," +
							result.getInt("DISTRICT_CODE") + "," +
							result.getInt("block_sno") + "," +
							result.getInt("PANCH_CODE") + "," +
							result.getInt("VILLAGE_CODE") + "," +
							result.getInt("HAB_CODE") + "," +
							"'" + result.getString("HNAME") + "'," +
							"'" + result.getString("UPDATED_BY_USER_ID") + "'," +
							"'" + result.getString("UPDATED_DATE") + "'," +
							"'" + result.getString("PROCESS_FLOW_STATUS_ID") + "'" +
							")";
							PreparedStatement ps = connection.prepareStatement(qry);
							ps.executeUpdate();
							xml=xml+"<flag>success</flag>";
							ps.close();
						} catch (Exception e) {
							System.out.println("Exception in ABC INSERT");
						e.printStackTrace();
					}
            	}
            	
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in ABC SELECT ==> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        else if(strCommand.equals("District"))
        { 
        	System.out.println("\n*************\nDistrict\n**************\n");
            xml="<response><command>District</command>";
            try 
            {
             result = statement.executeQuery("SELECT district_code, district_name " +
												"FROM com_mst_districts");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
         		 	xml+= "<row>";

         		 		xml+= "<dis>" + result.getInt("district_code") + "</dis>";
	     				xml+= "<district>" + result.getString("district_name") + "</district>";

     				xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values from District query: " + e);
             }
             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in District query"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        
        
        else if(strCommand.equals("Block"))
        { 
        	System.out.println("\n*************\nblock\n**************\n");
            xml="<response><command>Block</command>";
            try 
            {
             result = statement.executeQuery("SELECT block_sno, block_name " +
												"FROM com_mst_blocks " +
												"WHERE district_code=" + dis);
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
         		 	xml+= "<row>";
	                	 
	     				xml+= "<dis>" + dis + "</dis>";
	     				xml+= "<blk>" + result.getInt("block_sno") + "</blk>";
	     				xml+= "<block>" + result.getString("block_name") + "</block>";

     				xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values from Block query: " + e);
             }
             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Block query"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        
        
        else if(strCommand.equals("Panch"))
        { 
        	System.out.println("\n*************\nPanch\n**************\n");
            xml="<response><command>Panch</command>";
            try 
            {
                result = statement.executeQuery("SELECT a.district_code AS district_code," + 
						                        "      a.block_sno AS block_sno," + 
						                        "      a.panch_sno AS panch_sno," + 
						                        "      district_name," + 
						                        "      block_name," + 
						                        "      a.panch_name AS panch_name" + 
						                        "FROM" + 
						                        "(" + 
						                        "  (" + 
						                        "    SELECT district_code," + 
						                        "      block_sno," + 
						                        "      panch_sno," + 
						                        "      panch_name" + 
						                        "    FROM com_mst_panchayats" + 
						                        "    WHERE district_code LIKE '" + dis + "'" + 
						                        "    AND block_sno LIKE '" + blk + "'" + 
						                        "    AND LOWER(panch_name) LIKE '" + pan + "%'" + 
						                        "    ORDER BY district_code, block_sno, panch_code" + 
						                        "  )a " + 
						                        "  " + 
						                        "  JOIN" + 
						                        "  " + 
						                        "  (" + 
						                        "    SELECT district_code," + 
						                        "      district_name" + 
						                        "    FROM com_mst_districts" + 
						                        "  )b" + 
						                        "  ON a.district_code=b.district_code" + 
						                        "  " + 
						                        "  JOIN" + 
						                        "  " + 
						                        "  (" + 
						                        "    SELECT block_sno," + 
						                        "      block_name" + 
						                        "    FROM com_mst_blocks" + 
						                        "  )c" + 
						                        "  ON a.block_sno=c.block_sno" + 
						                        "  " + 
						                        ")");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
         		 	xml+= "<row>";
	                	 
	     				xml+= "<dis>" + result.getInt("district_code") + "</dis>";
	     				xml+= "<blk>" + result.getInt("block_sno") + "</blk>";
	     				xml+= "<pan>" + result.getInt("panch_code") + "</pan>";
	     				xml+= "<district>" + result.getString("district_name") + "</district>";
	     				xml+= "<block>" + result.getString("block_name") + "</block>";
	     				xml+= "<panch>" + result.getString("panch_name") + "</panch>";

     				xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values from Panch query: " + e);
             }
             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Panch query"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        
        
        
        
        else if(strCommand.equals("ULB"))
        { 
        	System.out.println("\n*************\nULB\n**************\n");
            xml="<response><command>ULB</command>";
            try 
            {
                result = statement.executeQuery("SELECT URBANLB_SNO, " +
												"    URBANLB_NAME " +
												"  FROM COM_MST_URBAN_LB " +
												"  where DISTRICT_CODE like '" + dis + "'" +
												"  and URBANLB_TYPE_ID like '" + ULBtype + "'" +
												"  and URBANLB_GRADE_ID like '" + ULBgrade + "'" +
												"  and AREA_TYPE_ID like '" + area + "'" +
												"  and LOWER(URBANLB_NAME) like '" + ulb + "%'" +
												"ORDER BY URBANLB_SNO");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
         		 	xml+= "<row>";
	                	 
	  				 	xml+= "<dis>" + dis + "</dis>";
	  				 	xml+= "<ULBtype>" + ULBtype + "</ULBtype>";
	     				xml+= "<ULBgrade>" + ULBgrade + "</ULBgrade>";
	     				xml+= "<area>" + area + "</area>";
	     				xml+= "<ulb>" + result.getInt("URBANLB_SNO") + "</ulb>";
	     				xml+= "<urbanlb>" + result.getString("URBANLB_NAME") + "</urbanlb>";

     				xml+= "</row>";
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values from ULB query: " + e);
             }
             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in ULB query"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
        else if(strCommand.equals("Category"))
        { 
        	System.out.println("\n*************\nCategory\n**************\n");
            xml="<response><command>Category</command>";
            try 
            {
                result = statement.executeQuery("SELECT BEN_TYPE_CATEGORY " +
												"  FROM PMS_DCB_BEN_TYPE " +
												"  where BEN_TYPE_ID = " + ctype);
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 if(result.next())
                 { 
  				 	xml+= "<ctype>" + ctype + "</ctype>";
  				 	xml+= "<prvlb>" + result.getString("BEN_TYPE_CATEGORY") + "</prvlb>";
     		     }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values from Category query: " + e);
             }
             result.close();
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Category query"+e1);
            	xml=xml+"<flag>failure</flag>";
            
            }
            xml=xml+"</response>";
        }  
        pw.write(xml);
        pw.flush();
        pw.close();
    
    }
   
    }




