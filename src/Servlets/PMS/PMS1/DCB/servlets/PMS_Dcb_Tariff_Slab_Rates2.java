/* 
  * Created on : dd-mm-yy 
  * Author     : Sathya
  * Last Date  : 20/09/2011
  *----------------------------------------------------------------------------- 
  * Revision History (Release 1.0.0.0) 
  *-----------------------------------------------------------------------------
  * Date			Description							Done By
  * 17/09/2011		Add the Beneficiary Status to 'L'  	PS
  * 21/09/2011		Add the Meter Status to 'L'			PS
  *---------|---------------|--------------------------------------------------- 
  */
package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class PMS_Dcb_Tariff_Slab_Rates
 */
public class PMS_Dcb_Tariff_Slab_Rates2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	String meter_status2=Controller.meter_status2;
	String meter_status=Controller.meter_status;
	String new_cond=Controller.new_cond; 
	String command_var="";
	String xmlvariable="";
	ResultSet res,result_new,res_max,rs_check;
	Connection connection = null;
    PreparedStatement ps,ps_oid,ps_max,ps_check;
    int Beneficiary_type=0;
    int empid=0;
    int oid=0;
    int flagname=0;
    int Beneficiary_Name=0;
    int Metre_Location=0;
    int Schemes=0;
    int countsno=0;
    double text_allot_flag;
    double text_min_qty;
    String mini_flag_value;
    String allot_flag_value;
    String tariff_flag_value="";
    String valus_starts_from_1[];
	String valus_starts_to_1[];
	String tariff_rate_1[];
	String tariff_flag;
	int tarifflagname=0;
    int TARIFF_SLAB_SNO=0;
    String Tariff_w_e_f_1="";
    double valus_starts;
	double valus_to;
	 double tariff_rate;
	 int ALLOT_SNO;
	 int i;
	 int lengthtemp;
	String check_value="";
	
		String [] temp_valus_starts_from=null;
		String [] temp_valus_to=null;
		String [] temp_tariff_rate=null;
		 String [] maxcheck=null;
		 String [] temp_check_value=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PMS_Dcb_Tariff_Slab_Rates2() {
        super();
        // Auto-generated constructor stub
    }
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("testing");
		Controller obj=null;
		try 
        {
            ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            obj=new Controller();
            
            String ConnectionString = "";
            String strdsn = rs.getString("Config.DSN");//jdbc:oracle:thin:
            String strhostname = rs.getString("Config.HOST_NAME");//10.163.0.58
            String strportno = rs.getString("Config.PORT_NUMBER");//1521
            String strsid = rs.getString("Config.SID");//twadnest
            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");//oracle.jdbc.OracleDriver
            String strdbusername = rs.getString("Config.USER_NAME");//twadpms
            String strdbpassword = rs.getString("Config.PASSWORD");//twadpms123
         //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
            Class.forName(strDriver.trim());  
            connection = DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
           
            try 
            {
                connection.clearWarnings();
                obj.createStatement(connection);
            } 
            catch (SQLException e)  
            {
                System.out.println("Exception in creating statement:" + e);
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Exception in openeing connection:" + e); 
        }
        
        
        
        
        try
        {
            command_var=request.getParameter("command");
            System.out.println("command_var"+command_var);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in openeing connection:" + e); 
        }
        try
        {
        	 valus_starts_from_1=request.getParameterValues("valus_starts_from_1");
            System.out.println("valus_starts_from_1"+valus_starts_from_1);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in openeing connection:" + e); 
        }
        try
        {
        	
        	//String values[]=req.getParameterValues("t1");
        	 valus_starts_to_1=request.getParameterValues("valus_starts_to_1");
        	//valus_starts_to_1=Double.parseDouble(request.getParameter("valus_starts_to_1"));
            System.out.println("valus_starts_to_1"+valus_starts_to_1);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in openeing connection:" + e); 
        }
        try
        {
        	 tariff_rate_1=request.getParameterValues("tariff_rate_1");
            System.out.println("tariff_rate_1"+tariff_rate_1);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in openeing connection:" + e); 
        }
        try
        {
        	Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            System.out.println("Beneficiary_Name"+Beneficiary_Name);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in openeing connection:" + e); 
        }
        try
        {
        	Metre_Location=Integer.parseInt(request.getParameter("Metre_Location"));
            System.out.println("Metre_Location"+Metre_Location);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in openeing connection:" + e); 
        }
        try
        {
        	Tariff_w_e_f_1=request.getParameter("Tariff_w_e_f_1");
            System.out.println("Tariff_w_e_f_1"+Tariff_w_e_f_1);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in openeing connection:" + e); 
        }
        
        try
        {
        	Schemes=Integer.parseInt(request.getParameter("Schemes"));
            System.out.println("Schemes"+Schemes);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in Schemes:" + e); 
        }
        try
        {
        	mini_flag_value=request.getParameter("mini_flag_value");
            System.out.println("mini_flag_value"+mini_flag_value);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in mini_flag_value:" + e); 
        }
        try
        {
        	allot_flag_value=request.getParameter("allot_flag_value");
            System.out.println("allot_flag_value"+allot_flag_value);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in allot_flag_value;:" + e); 
        }
        try
        {
        	text_allot_flag=Double.parseDouble(request.getParameter("text_allot_flag"));
            System.out.println("text_allot_flag"+text_allot_flag);
            
           
        }
        
        catch (Exception e) 
        {
            System.out.println("Exception in text_allot_flag:" + e); 
        }
        try
        {
        	tariff_flag=request.getParameter("tariff_flag");
            System.out.println("tariff_flag"+tariff_flag);
            
           
        }
        
        catch (Exception e) 
        {
            System.out.println("Exception in tariff_flag:" + e); 
        }
        
        try
        {
        	text_min_qty=Double.parseDouble(request.getParameter("text_min_qty"));
            System.out.println("text_min_qty"+text_min_qty);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in text_min_qty:" + e); 
        }
        try
        {
        	maxcheck=request.getParameterValues("maxcheck");
            System.out.println("tariff_flag"+maxcheck);
            
           
        }
        
        catch (Exception e) 
        {
            System.out.println("Exception in maxcheck:" + e); 
        }
        HttpSession session=request.getSession(false);
        try
        {
              if(session==null)
              {
                     System.out.println(request.getContextPath()+"/index.jsp");
                     response.sendRedirect(request.getContextPath()+"/index.jsp");
               }
                System.out.println(session);

        }
        catch(Exception e)
        {
           System.out.println("Redirect Error :"+e);
        }
        String userid=(String)session.getAttribute("UserId");
        
        
        UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
        System.out.println("emp id::" + empProfile.getEmployeeId());
        empid = empProfile.getEmployeeId();
        String oname = "";
        
        try
        {
            System.out.println("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID="+empid);
            ps_oid = connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
            ps_oid.setInt(1,empid);
            result_new = ps_oid.executeQuery();
            System.out.println("Testing value");
            if(result_new.next())
            {
                System.out.println("inside condition");
                oid = result_new.getInt("OFFICE_ID");
            }
                
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        System.out.println("command_var"+command_var);
       if (command_var.equalsIgnoreCase("insert")) 
        {  xmlvariable="";
    	   xmlvariable = "<response>";
           xmlvariable += "<command>add</command>";
          System.out.println("RATE ---------------->  Insert inside ");
             try
        	   {
        		   ps_check = connection.prepareStatement("select count(*) As countsno from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO =? and METRE_SNO = ? and SCH_SNO=? and TARIFF_W_E_F=to_date(?,'DD/MM/YYYY') and ACTIVE_STATUS='A'");
        		   ps_check.setInt(1,Beneficiary_Name);
        		   ps_check.setInt(2,Metre_Location);
        		   ps_check.setInt(3,Schemes);
        		   ps_check.setString(4,Tariff_w_e_f_1);
        		  
        		   rs_check = ps_check.executeQuery();
        		   if(rs_check.next()) 
        		   {
        			   countsno= rs_check.getInt("countsno") ;
        			 
        		   }
        	   }
        	   catch (Exception e) 
        	   {
        		   System.out.println("Error reterival");
        	   }
        	   if (countsno==0)
        	   {
        	   for(i=0;i<valus_starts_from_1.length;i++)
       			{
        		  
        		   temp_valus_starts_from=valus_starts_from_1[i].split(",");
        		   temp_valus_to=valus_starts_to_1[i].split(",");
        		   temp_tariff_rate=tariff_rate_1[i].split(",");
        		   temp_check_value=maxcheck[i].split(",");
        		   
        		   String rate_str="";
        		   String rate_val="";
        		   for( i=0;i<temp_valus_starts_from.length;i++)
          			{
        			  
        			   valus_starts=Double.parseDouble(temp_valus_starts_from[i]);
        			   valus_to=Double.parseDouble(temp_valus_to[i]);
        			   rate_str+=Double.toString(valus_starts)+"-"+Double.toString(valus_to)+",";
        			   tariff_rate=Double.parseDouble(temp_tariff_rate[i]);
        			   rate_val+=tariff_rate+",";
        			   check_value=temp_check_value[i];
        			   try
                	   {
                		   ps_max = connection.prepareStatement("select max(TARIFF_SLAB_SNO) AS TARIFF_SLAB_SNO from PMS_DCB_TARIFF_SLAB");
                		   res_max = ps_max.executeQuery();
                		   if(res_max.next())
                		   {
                        	
                			   TARIFF_SLAB_SNO= res_max.getInt("TARIFF_SLAB_SNO");
                			   
                        	
                		   }
                	   }
                	   catch(Exception e)
                	   {
                		   System.out.println("Erroe");
                	   }
                	   if (TARIFF_SLAB_SNO>0)
                	   {
                		   TARIFF_SLAB_SNO=TARIFF_SLAB_SNO+1;
                       }
                	   else
                	   {
                		   TARIFF_SLAB_SNO=1;
                    	
                	   }
                	
        			   try
                       {	
        				   
        			ps = connection.prepareStatement("insert into PMS_DCB_TARIFF_SLAB(TARIFF_SLAB_SNO,\n" + 
        		                    "BENEFICIARY_SNO,\n" + 
        		                    "SCH_SNO,\n" + 
        		                    "METRE_SNO,\n" + 
        		                    "TARIFF_FLAG,\n" +
        		                    "QTY_FROM,\n" + 
        		                    "QTY_TO,\n" + 
        		                    "TARIFF_RATE,\n" + 
        		                    "TARIFF_W_E_F,\n"+
        		                    "UPDATED_BY_USER_ID,\n"+
        		                    "UPDATED_DATE,\n"+
        		                    "ACTIVE_STATUS,MAX_FLAG,OFFICE_ID) values(?,?,?,?,?,?,?,?,to_date(?,'DD/MM/YYYY'),?,clock_timestamp(),'A',?,?)");
        		           
        		                      ps.setInt(1,TARIFF_SLAB_SNO);
        		                      ps.setInt(2,Beneficiary_Name);
        		                      ps.setInt(3,Schemes);
        		                      ps.setInt(4,Metre_Location);
        		                      ps.setString(5,tariff_flag);
        		                      ps.setDouble(6,valus_starts);
        		                      ps.setDouble(7, valus_to);
        		                      ps.setDouble(8,tariff_rate);
        		                      ps.setString(9,Tariff_w_e_f_1);
        		                      ps.setString(10,userid);
        		                      ps.setString(11,check_value);
        		                      ps.setInt(12,oid);
        		                      
        		                      ps.executeUpdate();
        		                     
        		                      xmlvariable += "<METRE_SNO>" + Metre_Location + "</METRE_SNO>";
        		                      xmlvariable += "<TARIFF_SLAB_STARTS_FROM>" + valus_starts + "</TARIFF_SLAB_STARTS_FROM>";
        		                      xmlvariable += "<TARIFF_SLAB_STARTS_TO>" + valus_to + "</TARIFF_SLAB_STARTS_TO>";
        		                      xmlvariable += "<TARIFF_RATE>" + tariff_rate + "</TARIFF_RATE>";
        		                      xmlvariable += "<countinsert>" + 0 + "</countinsert>";
        		                      xmlvariable += "<flag>success</flag>";
        		                      
                       }
                	   
        			   catch(Exception e)
                  	 	{
                  		 	System.out.println("Error"+e);
                  		 	xmlvariable += "<flag>faliure</flag>";
                  	 	}
        			   
        			   try {
        				   
        				     if (Metre_Location==0)
        				   		ps=connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set TARIFF_RATE='"+rate_val+"',QTY_SLAB='"+rate_str+"' where METRE_SNO in ( select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO="+Beneficiary_Name+" and  SCHEME_SNO="+Schemes+")");
        				      else
        				  		ps=connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set TARIFF_RATE='"+rate_val+"',QTY_SLAB='"+rate_str+"' where METRE_SNO="+Metre_Location);
        				    	  
        					   
        				    ps.executeUpdate();
        				   	
					} catch (SQLException e) {
						// Auto-generated catch block
						e.printStackTrace();
					}
        			   
        			   
                	   }
                	  
          			}
        	   try
        	   {
        		   ps_max = connection.prepareStatement("select max(ALLOT_SNO) AS ALLOT_SNO from PMS_DCB_ALLOTTED" );
        		   res_max = ps_max.executeQuery();
        		   if(res_max.next())
        		   {
                	
        			   ALLOT_SNO= res_max.getInt("ALLOT_SNO");
        			   
                	
        		   }
        	   }
        	   catch(Exception e)
        	   {
        		   System.out.println("Erroe");
        	   }
        	   if (ALLOT_SNO>0)
        	   {
        		   ALLOT_SNO=ALLOT_SNO+1;
               }
        	   else
        	   {
        		   ALLOT_SNO=1;
            	
        	   }
        	   System.out.println("ALLOT_SNO"+ALLOT_SNO);
        	   try
               {	
				   
			ps = connection.prepareStatement("insert into PMS_DCB_ALLOTTED(ALLOT_SNO,\n" + 
		                    "BENEFICIARY_SNO,\n" + 
		                    "MIN_FLAG,\n" + 
		                    "ALLOT_FLAG,\n" + 
		                    "METRE_SNO,\n" + 
		                    "SCH_SNO,\n"+
		                    "ALLOT_QTY,\n"+
		                    "MIN_QTY,\n"+ 
		                    "UPDATED_DATE,UPDATED_BY_USER_ID,ACTIVE_STATUS,OFFICE_ID) values(?,?,?,?,?,?,?,?,clock_timestamp(),?,'A',?)");
		         //   to_date(?,'DD/MM/YYYY')
		           
		                      ps.setInt(1,ALLOT_SNO);
		                      ps.setInt(2,Beneficiary_Name);
		                      ps.setString(3,mini_flag_value);
		                      ps.setString(4,allot_flag_value);
		                      ps.setInt(5,Metre_Location);
		                      ps.setInt(6,Schemes);
		                      ps.setDouble(7,text_allot_flag);
		                      ps.setDouble(8, text_min_qty);
		                      ps.setString(9,userid);
		                      ps.setInt(10,oid);
		                      ps.executeUpdate();
		                      
		                      System.out.println("Inserted PMS_DCB_ALLOTTED");
		                   
		                      
               }
        	   
			   catch(Exception e)
          	 	{
          		 	System.out.println("Error"+e);
          		 	xmlvariable += "<flag>faliure</flag>";
          	 	}
       			
        	   }
        	   else
        	   {
                   xmlvariable += "<countinsert>" + 1 + "</countinsert>";
                   xmlvariable += "<flag>success</flag>";
               }
             xmlvariable += "</response>";    
        	   //dcballoted_starts
        	   
          
           
        }
       
       
         if (command_var.equalsIgnoreCase("loadbeneficiarytype")) 
        {
        	 xmlvariable="";
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiarytype</command>";
            try 
            {
                ps = connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");
                
                res = ps.executeQuery();
                System.out.println("loadbeneficiarytype");
                while(res.next())
                {
                    
                    
                    xmlvariable += "<BEN_TYPE_ID>" + res.getInt("BEN_TYPE_ID") + "</BEN_TYPE_ID>";
                    xmlvariable += "<BEN_TYPE_DESC>" + res.getString("BEN_TYPE_DESC") + "</BEN_TYPE_DESC>";
                    xmlvariable += "<BEN_TYPE_SDESC>" + res.getString("BEN_TYPE_SDESC") + "</BEN_TYPE_SDESC>";
                    xmlvariable += "<flag>success</flag>";
                }
                
                
            } 
            catch (Exception e) 
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
       
        
        
		 if (command_var.equalsIgnoreCase("loadbeneficiaryname")) 
        {
			 xmlvariable="";
            System.out.println("loadbeneficiaryname==============================================");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiaryname</command>";
            Beneficiary_type=Integer.parseInt(request.getParameter("Beneficiary_type"));
            
            try 
            {
             
            	ps=connection.prepareStatement("select BENEFICIARY_SNO,BENEFICIARY_NAME,BENEFICIARY_TYPE_ID,OTHERS_PRIVATE_SNO ,VILLAGE_PANCHAYAT_SNO,URBANLB_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE  where "+meter_status+" OFFICE_ID=? and BENEFICIARY_TYPE_ID=?) and OFFICE_ID=? order by BENEFICIARY_NAME ");
            	
            	ps.setInt(1,oid);
                ps.setInt(2,Beneficiary_type);
                ps.setInt(3,oid);
                res = ps.executeQuery();
                flagname=0;
                System.out.println("oid"+"select BENEFICIARY_SNO,BENEFICIARY_NAME,BENEFICIARY_TYPE_ID,OTHERS_PRIVATE_SNO ,VILLAGE_PANCHAYAT_SNO,URBANLB_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE  where "+meter_status+" OFFICE_ID=? and BENEFICIARY_TYPE_ID=?) order by BENEFICIARY_NAME ");
                		 System.out.println("Beneficiary_type"+Beneficiary_type);
               while(res.next())
               {
                    flagname=1;
                   
                    
                    xmlvariable += "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") + "</BENEFICIARY_SNO>";
                    xmlvariable += "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") + "</BENEFICIARY_NAME>";
               
                   
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                if(flagname==0) 
                {                    
                    xmlvariable += "<BENEFICIARY_SNO>" + -1 + "</BENEFICIARY_SNO>";
                    xmlvariable += "<BENEFICIARY_NAME>" + "No Data" + "</BENEFICIARY_NAME>";
                    //xmlvariable += "<BENEFICIARY_TYPE_ID>" + -1 + "</BENEFICIARY_TYPE_ID>";
                   // xmlvariable += "<OTHERS_PRIVATE_SNO>" + -1 + "</OTHERS_PRIVATE_SNO>";
                   // xmlvariable += "<VILLAGE_PANCHAYAT_SNO>" + -1 + "</VILLAGE_PANCHAYAT_SNO>";
                   // xmlvariable += "<URBANLB_SNO>" + -1+ "</URBANLB_SNO>";
                    
                    
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                
                 
            } 
            catch (Exception e) 
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
		
		 
		  if (command_var.equalsIgnoreCase("loadtariffmode")) 
        {
			  xmlvariable="";
            xmlvariable = "<response>";
            xmlvariable += "<command>loadtariffmode</command>";
            Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            try 
            {
                ps = connection.prepareStatement("select BENEFICIARY_SNO, TARIFF_FLAG from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO=?");
               
                ps.setInt(1,Beneficiary_Name);
                          
                res = ps.executeQuery();
                flagname=0;
               while(res.next())
               {
                    flagname=1;
                    
                   
                    
                    xmlvariable += "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") + "</BENEFICIARY_SNO>";
                    xmlvariable += "<TARIFF_FLAG>" + res.getString("TARIFF_FLAG") + "</TARIFF_FLAG>";
                    
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                if(flagname==0) 
                {                    
                    xmlvariable += "<BENEFICIARY_SNO>" + -1 + "</BENEFICIARY_SNO>";
                    xmlvariable += "<TARIFF_FLAG>" + "No Data" + "</TARIFF_FLAG>";
                    
                    
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                
                 
            } 
            catch (Exception e) 
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
		 
		  
		  if (command_var.equalsIgnoreCase("loadlocation")) 
        {xmlvariable="";  
            System.out.println("loadlocation");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadlocation</command>";
            Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            try 
            {
                ps = connection.prepareStatement("select BENEFICIARY_SNO,METRE_SNO,METRE_LOCATION,SCHEME_SNO,TARIFF_FLAG from PMS_DCB_MST_BENEFICIARY_METRE where "+meter_status+" BENEFICIARY_SNO=? and SCHEME_SNO=?");
               
                ps.setInt(1,Beneficiary_Name);
                ps.setInt(2,Schemes);
                          
                res = ps.executeQuery();
                flagname=0;
               while(res.next())
               {
                    flagname=1;
                    
                  
                    
                    xmlvariable += "<METRE_SNO>" + res.getInt("METRE_SNO") + "</METRE_SNO>";
                    xmlvariable += "<METRE_LOCATION>" + res.getString("METRE_LOCATION") + "</METRE_LOCATION>";
                    xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO") + "</SCHEME_SNO>";
                    xmlvariable += "<TARIFF_FLAG>" + res.getString("TARIFF_FLAG") + "</TARIFF_FLAG>";
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                if(flagname==0) 
                {                    
                    xmlvariable += "<METRE_SNO>" + -1 + "</METRE_SNO>";
                    xmlvariable += "<METRE_LOCATION>" + "No Data" + "</METRE_LOCATION>";
                    
                    
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                
                 
            } 
            catch (Exception e) 
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
		 
		  
		  if (command_var.equalsIgnoreCase("loadscheme")) 
        { 
			  xmlvariable="";
            System.out.println("loadscheme");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadscheme</command>";
            Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            try 
            {
                ps = connection.prepareStatement("select distinct SCHEME_SNO AS SCHEME_SNO,BENEFICIARY_SNO AS BENEFICIARY_SNO,PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,PMS_DCB_MST_BENEFICIARY_METRE.TARIFF_FLAG as TARIFF_FLAG  from PMS_DCB_MST_BENEFICIARY_METRE join PMS_SCH_MASTER on "+meter_status2+" PMS_SCH_MASTER.SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO where BENEFICIARY_SNO=? and TARIFF_FLAG<>'-' AND TARIFF_FLAG IS NOT NULL AND SETTING_FLAG='FR'");
                 
                System.out.println("select distinct SCHEME_SNO AS SCHEME_SNO,BENEFICIARY_SNO AS BENEFICIARY_SNO,PMS_SCH_MASTER.SCH_NAME AS SCH_NAME,PMS_DCB_MST_BENEFICIARY_METRE.TARIFF_FLAG as TARIFF_FLAG  from PMS_DCB_MST_BENEFICIARY_METRE join PMS_SCH_MASTER on "+meter_status2+" PMS_SCH_MASTER.SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO where BENEFICIARY_SNO=? and TARIFF_FLAG<>'-' AND TARIFF_FLAG IS NOT NULL AND SETTING_FLAG='FR'");
                
                ps.setInt(1,Beneficiary_Name);
                res=null;
                res = ps.executeQuery();
                flagname=0;
                
               while(res.next())
               {
                    flagname=1;
                                              
                    xmlvariable += "<SCH_NAME><![CDATA[" + res.getString("SCH_NAME") + "]]></SCH_NAME>";
                    xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO") + "</SCHEME_SNO>";
                    xmlvariable += "<TARIFF_FLAG>" +obj.isNull(res.getString("TARIFF_FLAG"),1) + "</TARIFF_FLAG>";
                      
                    xmlvariable += "<flag>success</flag>";   
                    
                     
                } 
                if(flagname==0) 
                {                    
                    xmlvariable += "<SCHEME_SNO>" + -1 + "</SCHEME_SNO>";
                    xmlvariable += "<SCH_NAME>" + "No Data" + "</SCH_NAME>";
                    
                    
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                
                 
            } 
            catch (Exception e) 
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
		  
		  if (command_var.equalsIgnoreCase("get")) 
        {xmlvariable="";
           System.out.println("getting value");
           System.out.println("Metre_Location"+Metre_Location);
           
            xmlvariable = "<response>";
            xmlvariable += "<command>get</command>";
            if(tariff_flag_value.equals("Location"))
            { 
            try
            {
                ps_check = connection.prepareStatement("select count(*) As countsno from PMS_DCB_TARIFF_SLAB_LOCATION where METRE_SNO =?");
                ps_check.setInt(1,Metre_Location);
                rs_check = ps_check.executeQuery();
                if(rs_check.next()) 
                {
                    countsno= rs_check.getInt("countsno") ;
                    System.out.println("countsno"+countsno);
                }
            }
            catch (Exception e) 
            {
                System.out.println("Error reterival");
                
            }
            if(countsno>0)
           {
         	
            	
            try 
            {
            	ps=connection.prepareStatement("select TARIFF_SLAB_SNO,BENEFICIARY_SNO,METRE_SNO,TARIFF_SLAB_STARTS_FROM,TARIFF_SLAB_STARTS_TO,TARIFF_RATE,TARIFF_W_E_F from PMS_DCB_TARIFF_SLAB_LOCATION where METRE_SNO =? order by TARIFF_SLAB_STARTS_FROM");
            	ps.setInt(1,Metre_Location);
                res = ps.executeQuery();
                System.out.println("Metre_Location"+Metre_Location);
                System.out.println("Asass");
                while (res.next())
                {
                   try
                   {
                     System.out.println(res.getInt("TARIFF_SLAB_SNO"));
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Exception");
                   }
                   try
                   {
                    System.out.println(res.getInt("BENEFICIARY_SNO"));
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Exception");
                   }
                   try
                   {
                    System.out.println(res.getInt("METRE_SNO"));
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Exception");
                   }
                   try
                   {
                    System.out.println(res.getDouble("TARIFF_SLAB_STARTS_FROM"));
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Exception");
                   }
                   try
                   {
                    System.out.println(res.getDouble("TARIFF_SLAB_STARTS_TO"));
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Exception");
                   }
                   try
                   {
                    System.out.println(res.getDouble("TARIFF_RATE"));
                   }
                   catch(Exception e)
                   {
                    System.out.println(res.getDate("TARIFF_W_E_F"));
                   }
                   
                    
                  xmlvariable += "<recordfound> 1 </recordfound>";
                    xmlvariable += "<TARIFF_SLAB_STARTS_FROM>" + res.getDouble("TARIFF_SLAB_STARTS_FROM") + "</TARIFF_SLAB_STARTS_FROM>";
                    xmlvariable += "<TARIFF_SLAB_STARTS_TO>" +res.getDouble("TARIFF_SLAB_STARTS_TO") + "</TARIFF_SLAB_STARTS_TO>";
                    xmlvariable += "<TARIFF_RATE>" +res.getDouble("TARIFF_RATE") + "</TARIFF_RATE>";
                    
                    xmlvariable += "<TARIFF_W_E_F>" + res.getDate("TARIFF_W_E_F") + "</TARIFF_W_E_F>";
                   
                    xmlvariable += "<flag>success</flag>";
                    
                }
              
               
            } 
            catch (Exception e) 
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            
           }
            else 
            {
                xmlvariable += "<flag>success</flag>";
                xmlvariable += "<recordfound> 0 </recordfound>";
            }
            xmlvariable += "</response>";
        }
             
            if(tariff_flag_value.equals("Scheme"))
            {
            	try
                {
                    ps_check = connection.prepareStatement("select count(*) As countsno from PMS_DCB_TARIFF_SLAB_SCHEME where SCH_SNO =?");
                    ps_check.setInt(1,Metre_Location);
                    rs_check = ps_check.executeQuery();
                    if(rs_check.next()) 
                    {
                        countsno= rs_check.getInt("countsno") ;
                        System.out.println("countsno"+countsno);
                    }
                }
                catch (Exception e) 
                {
                    System.out.println("Error reterival");
                    
                }
                
                
                
                
                
                
                if(countsno>0)
                {
              	
                 	
                 try 
                 {
                 	ps=connection.prepareStatement("select TARIFF_SLAB_SNO,BENEFICIARY_SNO,SCH_SNO,TARIFF_SLAB_STARTS_FROM,TARIFF_SLAB_STARTS_TO,TARIFF_RATE,TARIFF_W_E_F from PMS_DCB_TARIFF_SLAB_SCHEME where SCH_SNO =? order by TARIFF_SLAB_STARTS_FROM");
                 	ps.setInt(1,Metre_Location);
                     res = ps.executeQuery();
                    
                     while (res.next())
                     {
                        try
                        {
                          System.out.println(res.getInt("TARIFF_SLAB_SNO"));
                        }
                        catch(Exception e)
                        {
                     	   System.out.println("Exception");
                        }
                        try
                        {
                         System.out.println(res.getInt("BENEFICIARY_SNO"));
                        }
                        catch(Exception e)
                        {
                     	   System.out.println("Exception");
                        }
                        try
                        {
                         System.out.println(res.getInt("SCH_SNO"));
                        }
                        catch(Exception e)
                        {
                     	   System.out.println("Exception");
                        }
                        try
                        {
                         System.out.println(res.getDouble("TARIFF_SLAB_STARTS_FROM"));
                        }
                        catch(Exception e)
                        {
                     	   System.out.println("Exception");
                        }
                        try
                        {
                         System.out.println(res.getDouble("TARIFF_SLAB_STARTS_TO"));
                        }
                        catch(Exception e)
                        {
                     	   System.out.println("Exception");
                        }
                        try
                        {
                         System.out.println(res.getDouble("TARIFF_RATE"));
                        }
                        catch(Exception e)
                        {
                         System.out.println(res.getDate("TARIFF_W_E_F"));
                        }
                        
                         
                       xmlvariable += "<recordfound> 1 </recordfound>";
                         xmlvariable += "<TARIFF_SLAB_STARTS_FROM>" + res.getDouble("TARIFF_SLAB_STARTS_FROM") + "</TARIFF_SLAB_STARTS_FROM>";
                         xmlvariable += "<TARIFF_SLAB_STARTS_TO>" +res.getDouble("TARIFF_SLAB_STARTS_TO") + "</TARIFF_SLAB_STARTS_TO>";
                         xmlvariable += "<TARIFF_RATE>" +res.getDouble("TARIFF_RATE") + "</TARIFF_RATE>";
                         
                         xmlvariable += "<TARIFF_W_E_F>" + res.getDate("TARIFF_W_E_F") + "</TARIFF_W_E_F>";
                        
                         xmlvariable += "<flag>success</flag>";
                         
                     }
                   
                    
                 } 
                 catch (Exception e) 
                 {
                     xmlvariable += "<flag>failure</flag>";
                     System.out.println(e + "not reterived!");
                 }
                 
                }
                 else 
                 {
                     xmlvariable += "<flag>success</flag>";
                     xmlvariable += "<recordfound> 0 </recordfound>";
                 }
                 xmlvariable += "</response>";
            }
        
        }
		  if (command_var.equalsIgnoreCase("loadflag")) 
        {
			 xmlvariable="";
            System.out.println("loadflag");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadflag</command>";
            Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            try 
            {
                ps = connection.prepareStatement("select BENEFICIARY_SNO,TARIFF_MODE FROM PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO=?");
               
                ps.setInt(1,Beneficiary_Name);
                          
                res = ps.executeQuery();
                flagname=0;
               while(res.next())
               {
                    flagname=1;
                                              
                    xmlvariable += "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") + "</BENEFICIARY_SNO>";
                    xmlvariable += "<TARIFF_MODE>" + res.getString("TARIFF_MODE") + "</TARIFF_MODE>";
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                if(flagname==0) 
                {                    
                    xmlvariable += "<BENEFICIARY_SNO>" + -1 + "</BENEFICIARY_SNO>";
                    xmlvariable += "<TARIFF_MODE>" + "No Data" + "</TARIFF_MODE>";
                    
                    
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                
                 
            } 
            catch (Exception e) 
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
            xmlvariable += "</response>";
        }
		  if (command_var.equalsIgnoreCase("loadlocationgrid"))
		{
			 xmlvariable="";
			xmlvariable = "<response>";
			xmlvariable += "<command>loadlocationgrid</command>";
			//forlocationgrid starts
			if(tariff_flag.equals("L"))
			{
				try
				{
					ps_check = connection.prepareStatement("select count(*) as countsno from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=? and TARIFF_FLAG=? and ACTIVE_STATUS='A'");
					ps_check.setInt(1,Beneficiary_Name);
				    ps_check.setInt(2,Metre_Location);
				    ps_check.setInt(3,Schemes);
				    ps_check.setString(4,tariff_flag);
				    rs_check = ps_check.executeQuery();
				    if(rs_check.next()) 
				    {
				    	countsno= rs_check.getInt("countsno") ;
				        System.out.println("countsno"+countsno);
				    }
				 }
				 catch (Exception e) 
				 {
					 System.out.println("Error_reterival_for_grid");
				 }
				 if(countsno>0)
				 {
				try 
				{
					ps_check=connection.prepareStatement("select METRE_SNO,TARIFF_FLAG,QTY_FROM,QTY_TO,TARIFF_RATE,TARIFF_W_E_F,ACTIVE_STATUS from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and METRE_SNO=? and SCH_SNO=? and TARIFF_FLAG=? AND ACTIVE_STATUS='A' ORDER BY QTY_FROM");
					ps_check.setInt(1,Beneficiary_Name);
				    ps_check.setInt(2,Metre_Location);
				    ps_check.setInt(3,Schemes);
				    ps_check.setString(4,tariff_flag);
				    
			        res = ps_check.executeQuery();
			       
			        while (res.next())
			        {
			        	xmlvariable += "<recordfound> 1 </recordfound>";
					    xmlvariable += "<QTY_FROM>" + res.getDouble("QTY_FROM") + "</QTY_FROM>";
						xmlvariable += "<QTY_TO>" +res.getDouble("QTY_TO") + "</QTY_TO>";
				        xmlvariable += "<TARIFF_RATE>" +res.getDouble("TARIFF_RATE") + "</TARIFF_RATE>";
						xmlvariable += "<TARIFF_W_E_F>" + res.getDate("TARIFF_W_E_F") + "</TARIFF_W_E_F>";
				        xmlvariable += "<flag>success</flag>";
				   	}
				}
				catch (Exception e) 
				{
					xmlvariable += "<flag>failure</flag>";
				    System.out.println(e + "not reterived!");
				}
			 }
			else 
			{
				xmlvariable += "<flag>success</flag>";
				xmlvariable += "<recordfound> 0 </recordfound>";
			}
			    xmlvariable += "</response>";
			}
			//for locationgrid ends
			//for schemegrid starts
			if(tariff_flag.equals("S"))
			{
				try
				{
					ps_check = connection.prepareStatement("select count(*) as countsno from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and SCH_SNO=? and TARIFF_FLAG=? AND ACTIVE_STATUS='A'");
					ps_check.setInt(1,Beneficiary_Name);
					ps_check.setInt(2,Schemes);
				    ps_check.setString(3,tariff_flag);
				    
				    rs_check = ps_check.executeQuery();
				    if(rs_check.next()) 
				    {
				    	countsno= rs_check.getInt("countsno") ;
				        System.out.println("countsno"+countsno);
				    }
				 }
				 catch (Exception e) 
				 {
					 System.out.println("Error_reterival_for_grid");
				 }
				 if(countsno>0)
				 {
				try 
				{
					ps_check=connection.prepareStatement("select METRE_SNO,TARIFF_FLAG,QTY_FROM,QTY_TO,TARIFF_RATE,TARIFF_W_E_F,ACTIVE_STATUS from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and SCH_SNO=? and TARIFF_FLAG=? AND ACTIVE_STATUS='A' ORDER BY QTY_FROM");
					ps_check.setInt(1,Beneficiary_Name);
					ps_check.setInt(2,Schemes);
				    ps_check.setString(3,tariff_flag);
			        res = ps_check.executeQuery();
			       
			        while (res.next())
			        {
			        	xmlvariable += "<recordfound> 1 </recordfound>";
					    xmlvariable += "<QTY_FROM>" + res.getDouble("QTY_FROM") + "</QTY_FROM>";
						xmlvariable += "<QTY_TO>" +res.getDouble("QTY_TO") + "</QTY_TO>";
				        xmlvariable += "<TARIFF_RATE>" +res.getDouble("TARIFF_RATE") + "</TARIFF_RATE>";
						xmlvariable += "<TARIFF_W_E_F>" + res.getDate("TARIFF_W_E_F") + "</TARIFF_W_E_F>";
				        xmlvariable += "<flag>success</flag>";
				   	}
				}
				catch (Exception e) 
				{
					xmlvariable += "<flag>failure</flag>";
				    System.out.println(e + "not reterived!");
				}
			 }
			else 
			{
				xmlvariable += "<flag>success</flag>";
				xmlvariable += "<recordfound> 0 </recordfound>";
			}
			    xmlvariable += "</response>";
			}
			//for schemegrid ends
			if(tariff_flag.equals("B"))
			{
				try
				{
					ps_check = connection.prepareStatement("select count(*) as countsno from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and TARIFF_FLAG=? AND ACTIVE_STATUS='A'");
					ps_check.setInt(1,Beneficiary_Name);
					ps_check.setString(2,tariff_flag);
				    
				    rs_check = ps_check.executeQuery();
				    if(rs_check.next()) 
				    {
				    	countsno= rs_check.getInt("countsno") ;
				        System.out.println("countsno"+countsno);
				    }
				 }
				 catch (Exception e) 
				 {
					 System.out.println("Error_reterival_for_grid");
				 }
				 if(countsno>0)
				 {
				try 
				{
					ps_check=connection.prepareStatement("select METRE_SNO,TARIFF_FLAG,QTY_FROM,QTY_TO,TARIFF_RATE,TARIFF_W_E_F,ACTIVE_STATUS from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and TARIFF_FLAG=? ACTIVE_STATUS='A' ORDER BY QTY_FROM");
					ps_check.setInt(1,Beneficiary_Name);
				    ps_check.setString(2,tariff_flag);
			        res = ps_check.executeQuery();
			       
			        while (res.next())
			        {
			        	xmlvariable += "<recordfound> 1 </recordfound>";
					    xmlvariable += "<QTY_FROM>" + res.getDouble("QTY_FROM") + "</QTY_FROM>";
						xmlvariable += "<QTY_TO>" +res.getDouble("QTY_TO") + "</QTY_TO>";
				        xmlvariable += "<TARIFF_RATE>" +res.getDouble("TARIFF_RATE") + "</TARIFF_RATE>";
						xmlvariable += "<TARIFF_W_E_F>" + res.getDate("TARIFF_W_E_F") + "</TARIFF_W_E_F>";
				        xmlvariable += "<flag>success</flag>";
				   	}
				}
				catch (Exception e) 
				{
					xmlvariable += "<flag>failure</flag>";
				    System.out.println(e + "not reterived!");
				}
			 }
			else 
			{
				xmlvariable += "<flag>success</flag>";
				xmlvariable += "<recordfound> 0 </recordfound>";
			}
			    xmlvariable += "</response>";
			}
		}
		else if (command_var.equalsIgnoreCase("loadtariffvalue")) 
        {
			 xmlvariable="";
            System.out.println("loadtariffvalue");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadtariffvalue</command>";
            Beneficiary_type=Integer.parseInt(request.getParameter("Beneficiary_type"));
            System.out.println("Beneficiary_type"+Beneficiary_type);
            
            try
			{
				ps_check = connection.prepareStatement("select count(*) AS countsno from PMS_DCB_MST_TARIFF WHERE BENEFICIARY_TYPE =? and ACTIVE_STATUS='A'");
				ps_check.setInt(1,Beneficiary_type);
				
			    
			    rs_check = ps_check.executeQuery();
			    if(rs_check.next())  
			    {
			    	countsno= rs_check.getInt("countsno") ;
			        System.out.println("countsno"+countsno);
			    }
			 }
			 catch (Exception e) 
			 {
				 System.out.println("Error_reterival_for_grid");
			 }
			 if(countsno>0)
			 {
				 try 
				 {
                ps = connection.prepareStatement("select TARIFF_ID,TARIFF_RATE,TARIFF_WEF from PMS_DCB_MST_TARIFF WHERE BENEFICIARY_TYPE =? and ACTIVE_STATUS='A'");
               
                ps.setInt(1,Beneficiary_type);
               
                          
                res = ps.executeQuery();
                
               if(res.next())
               {
                    tarifflagname=1;
                    
                    System.out.println(res.getString("TARIFF_RATE"));
                    
                    xmlvariable += "<TARIFF_RATE>" + res.getDouble("TARIFF_RATE") + "</TARIFF_RATE>";
                    
                    xmlvariable += "<TARIFF_WEF>" + res.getDate("TARIFF_WEF") + "</TARIFF_WEF>";
                 
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                
                
                 
            } 
            catch (Exception e) 
            {
                xmlvariable += "<flag>failure</flag>";
                System.out.println(e + "not reterived!");
            }
			}
			 else
			 {
              
                xmlvariable += "<TARIFF_RATE>" + "-" + "</TARIFF_RATE>";
                xmlvariable += "<TARIFF_WEF>" + "-" + "</TARIFF_WEF>";
                
                xmlvariable += "<flag>success</flag>";            
                 
            }
            xmlvariable += "</response>";
        }
		   System.out.println(xmlvariable);
		out.println(xmlvariable);
	 
	}
	 
}
