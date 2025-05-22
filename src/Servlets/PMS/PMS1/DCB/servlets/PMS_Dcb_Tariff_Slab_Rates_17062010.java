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
public class PMS_Dcb_Tariff_Slab_Rates_17062010 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 

	
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
    int countsno=0;
    String tariff_flag_value="";
    String valus_starts_from_1[];
	String valus_starts_to_1[];
	String tariff_rate_1[];
    int TARIFF_SLAB_SNO=0;
    String Tariff_w_e_f_1="";
    int valus_starts;
	int valus_to;
	 double tariff_rate;
	 int i;
	 int lengthtemp;
		String [] temp_valus_starts_from=null;
		String [] temp_valus_to=null;
		String [] temp_tariff_rate=null;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PMS_Dcb_Tariff_Slab_Rates_17062010() {
        super();
        //   Auto-generated constructor stub
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
		
		try 
        {
            ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strdsn = rs.getString("Config.DSN");//jdbc:oracle:thin:
            String strhostname = rs.getString("Config.HOST_NAME");//10.163.0.58
            String strportno = rs.getString("Config.PORT_NUMBER");//1521
            String strsid = rs.getString("Config.SID");//twadnest
            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");//oracle.jdbc.OracleDriver
            String strdbusername = rs.getString("Config.USER_NAME");//twadpms
            String strdbpassword = rs.getString("Config.PASSWORD");//twadpms123
        //    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
            Class.forName(strDriver.trim());  
            connection = DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
            System.out.println("Paid by master");
            try 
            {
                connection.clearWarnings();
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
        	tariff_flag_value=request.getParameter("tariff_flag_value");
            System.out.println("tariff_flag_value"+tariff_flag_value);
            
           
        }
        catch (Exception e) 
        {
            System.out.println("Exception in tariff_flag_value:" + e); 
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
       if (command_var.equalsIgnoreCase("insert")) 
        {
    	   xmlvariable = "<response>";
           xmlvariable += "<command>add</command>";
           //System.out.println("Insert inside");
           if(tariff_flag_value.equals("Location"))
           {   
        	   try
        	   {
        		   ps_check = connection.prepareStatement("select count(*) As countsno from PMS_DCB_TARIFF_SLAB_LOCATION where METRE_SNO = ? and TARIFF_W_E_F=to_date(?,'DD/MM/YYYY')");
        		   ps_check.setInt(1,Metre_Location);
        		   ps_check.setString(2,Tariff_w_e_f_1);
        		   rs_check = ps_check.executeQuery();
        		   if(rs_check.next()) 
        		   {
        			   countsno= rs_check.getInt("countsno") ;
        			   System.out.println("countsnoinsert"+countsno);
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
        		   System.out.println("valus_starts_from_1[i]"+valus_starts_from_1[i]);
        		   temp_valus_starts_from=valus_starts_from_1[i].split(",");
        		   temp_valus_to=valus_starts_to_1[i].split(",");
        		   temp_tariff_rate=tariff_rate_1[i].split(",");
        		   for( i=0;i<temp_valus_starts_from.length;i++)
          			{
        			  System.out.println("temp_valus_starts_from"+temp_valus_starts_from[i]);
        			
        			 System.out.println("temp_valus_to"+temp_valus_to[i]);
        			 System.out.println("temp_tariff_rate"+temp_tariff_rate[i]);
        			   valus_starts=Integer.parseInt(temp_valus_starts_from[i]);
        			   valus_to=Integer.parseInt(temp_valus_to[i]);
        			   tariff_rate=Double.parseDouble(temp_tariff_rate[i]);
        			   
        			   try
                	   {
                		   ps_max = connection.prepareStatement("select max(TARIFF_SLAB_SNO) AS TARIFF_SLAB_SNO from PMS_DCB_TARIFF_SLAB_LOCATION");
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
        				   
        				   			ps = connection.prepareStatement("insert into PMS_DCB_TARIFF_SLAB_LOCATION(TARIFF_SLAB_SNO,\n" + 
        		                    "BENEFICIARY_SNO,\n" + 
        		                    "METRE_SNO,\n" + 
        		                    "TARIFF_SLAB_STARTS_FROM,\n" + 
        		                    "TARIFF_SLAB_STARTS_TO,\n" + 
        		                    "TARIFF_RATE,\n" + 
        		                    "TARIFF_W_E_F) values(?,?,?,?,?,?,to_date(?,'DD/MM/YYYY'))");
        		         //   to_date(?,'DD/MM/YYYY')
        		           
        		                      ps.setInt(1,TARIFF_SLAB_SNO);
        		                      ps.setInt(2,Beneficiary_Name);
        		                      ps.setInt(3,Metre_Location);
        		                      ps.setInt(4,valus_starts);
        		                      ps.setInt(5, valus_to);
        		                      ps.setDouble(6,tariff_rate);
        		                      ps.setString(7,Tariff_w_e_f_1);
        		                      
        		                      ps.executeUpdate();
        		                      System.out.println("i"+i);
        		                      System.out.println("Inserted");
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
                	   }
                	  
          			}
       			}
        	   else
        	   {
                   xmlvariable += "<countinsert>" + 1 + "</countinsert>";
                   xmlvariable += "<flag>success</flag>";
               }
             xmlvariable += "</response>";    
           }
           else if(tariff_flag_value.equals("Scheme"))
           {   
        	   try
               {
                   ps_check = connection.prepareStatement("select count(*) As countsno from PMS_DCB_TARIFF_SLAB_SCHEME where SCH_SNO =?  and TARIFF_W_E_F=to_date(?,'DD/MM/YYYY')");
                   ps_check.setInt(1,Metre_Location);
                   ps_check.setString(2,Tariff_w_e_f_1);
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
     
               if (countsno==0)
               {
        	   for(i=0;i<valus_starts_from_1.length;i++)
           	   {
     		  
        		   temp_valus_starts_from=valus_starts_from_1[i].split(",");
        		   temp_valus_to=valus_starts_to_1[i].split(",");
        		   temp_tariff_rate=tariff_rate_1[i].split(",");
        		   for( i=0;i<temp_valus_starts_from.length;i++)
       				{
        			   valus_starts=Integer.parseInt(temp_valus_starts_from[i]);
        			  // System.out.println("  temp_valus_starts_from[i]"+  temp_valus_starts_from[i]);
        			 
        			   valus_to=Integer.parseInt(temp_valus_to[i]);
        			   tariff_rate=Double.parseDouble(temp_tariff_rate[i]);
        			  // System.out.println("  temp_valus_starts_from[i]"+  temp_valus_starts_from[i]);
        			 //  System.out.println("valus_starts"+valus_starts);
        			 //  System.out.println("valus_to"+valus_to);
        			 //  System.out.println("  temp_tariff_rate[i]"+  temp_tariff_rate[i]);
        			 //  System.out.println("tariff_rate"+tariff_rate);
        			   
		               try
		               {
		            	   ps_max = connection.prepareStatement("select max(TARIFF_SLAB_SNO) AS TARIFF_SLAB_SNO from PMS_DCB_TARIFF_SLAB_SCHEME");
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
               		 
               		 ps = connection.prepareStatement("insert into PMS_DCB_TARIFF_SLAB_SCHEME(TARIFF_SLAB_SNO,\n" + 
                       "BENEFICIARY_SNO,\n" + 
                       "SCH_SNO,\n" + 
                       "TARIFF_SLAB_STARTS_FROM,\n" + 
                       "TARIFF_SLAB_STARTS_TO,\n" + 
                       "TARIFF_RATE,\n" + 
                       "TARIFF_W_E_F) values(?,?,?,?,?,?,to_date(?,'DD/MM/YYYY'))");
            //   to_date(?,'DD/MM/YYYY')
              
                         ps.setInt(1,TARIFF_SLAB_SNO);
                         ps.setInt(2,Beneficiary_Name);
                         ps.setInt(3,Metre_Location);
                         ps.setInt(4,valus_starts);
                         ps.setInt(5, valus_to);
                         ps.setDouble(6,tariff_rate);
                         ps.setString(7,Tariff_w_e_f_1);
                         
                         ps.executeUpdate();
                         System.out.println("Inserted");
                         
               	   
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
	            }
		       
       		}
         }
               else
         	   {
                    xmlvariable += "<countinsert>" + 1 + "</countinsert>";
                    xmlvariable += "<flag>success</flag>";
                }
        	   xmlvariable += "</response>";           
         }
           
        }
       else if (command_var.equalsIgnoreCase("loadbeneficiarytype")) 
        {
           
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
		else if (command_var.equalsIgnoreCase("loadbeneficiaryname")) 
        {
            System.out.println("loadbeneficiaryname");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiaryname</command>";
            Beneficiary_type=Integer.parseInt(request.getParameter("Beneficiary_type"));
            
            try 
            {
                ps = connection.prepareStatement("select BENEFICIARY_SNO,\n" + 
                "BENEFICIARY_TYPE_ID,\n" + 
                "BENEFICIARY_NAME,\n" + 
                "OTHERS_PRIVATE_SNO,\n" + 
                "VILLAGE_PANCHAYAT_SNO,\n" + 
                "URBANLB_SNO,\n" + 
                "PMS_DCB_MST_BENEFICIARY.OFFICE_ID\n" + 
                "from PMS_DCB_MST_BENEFICIARY\n" + 
                "where\n" + 
                "BENEFICIARY_TYPE_ID=?\n" + 
                "and\n" + 
                "PMS_DCB_MST_BENEFICIARY.OFFICE_ID=? and (TARIFF_MODE='S'or TARIFF_MODE='L')");
               
                ps.setInt(1,Beneficiary_type);
                ps.setInt(2,oid);
                
              
                res = ps.executeQuery();
                flagname=0;
               while(res.next())
               {
                    flagname=1;
                    System.out.println(res.getInt("BENEFICIARY_SNO"));
                    System.out.println(res.getString("BENEFICIARY_NAME"));
                    
                    xmlvariable += "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") + "</BENEFICIARY_SNO>";
                    xmlvariable += "<BENEFICIARY_NAME>" + res.getString("BENEFICIARY_NAME") + "</BENEFICIARY_NAME>";
                    xmlvariable += "<BENEFICIARY_TYPE_ID>" + res.getInt("BENEFICIARY_TYPE_ID") + "</BENEFICIARY_TYPE_ID>";
                    xmlvariable += "<OTHERS_PRIVATE_SNO>" + res.getInt("OTHERS_PRIVATE_SNO") + "</OTHERS_PRIVATE_SNO>";
                    xmlvariable += "<VILLAGE_PANCHAYAT_SNO>" + res.getInt("VILLAGE_PANCHAYAT_SNO") + "</VILLAGE_PANCHAYAT_SNO>";
                    xmlvariable += "<URBANLB_SNO>" + res.getInt("URBANLB_SNO") + "</URBANLB_SNO>";
                   
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                if(flagname==0) 
                {                    
                    xmlvariable += "<BENEFICIARY_SNO>" + -1 + "</BENEFICIARY_SNO>";
                    xmlvariable += "<BENEFICIARY_NAME>" + "No Data" + "</BENEFICIARY_NAME>";
                    xmlvariable += "<BENEFICIARY_TYPE_ID>" + -1 + "</BENEFICIARY_TYPE_ID>";
                    xmlvariable += "<OTHERS_PRIVATE_SNO>" + -1 + "</OTHERS_PRIVATE_SNO>";
                    xmlvariable += "<VILLAGE_PANCHAYAT_SNO>" + -1 + "</VILLAGE_PANCHAYAT_SNO>";
                    xmlvariable += "<URBANLB_SNO>" + -1+ "</URBANLB_SNO>";
                    
                    
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
		else if (command_var.equalsIgnoreCase("loadtariffmode")) 
        {
            
            xmlvariable = "<response>";
            xmlvariable += "<command>loadtariffmode</command>";
            Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            try 
            {
               // ps = connection.prepareStatement("select BENEFICIARY_SNO, TARIFF_MODE from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_SNO=?");
               
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
		else if (command_var.equalsIgnoreCase("loadlocation")) 
        {
            System.out.println("loadlocation");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadlocation</command>";
            Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            try 
            {
                ps = connection.prepareStatement("select METRE_SNO,METRE_LOCATION from PMS_DCB_MST_BENEFICIARY_METRE where BENEFICIARY_SNO=?");
               
                ps.setInt(1,Beneficiary_Name);
                          
                res = ps.executeQuery();
                flagname=0;
               while(res.next())
               {
                    flagname=1;
                    
                    System.out.println(res.getString("METRE_LOCATION"));
                    
                   
                    xmlvariable += "<METRE_LOCATION>" + res.getString("METRE_LOCATION") + "</METRE_LOCATION>";
                    xmlvariable += "<METRE_SNO>" + res.getInt("METRE_SNO") + "</METRE_SNO>";
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
		else if (command_var.equalsIgnoreCase("loadscheme")) 
        {
            System.out.println("loadscheme");
            xmlvariable = "<response>";
            xmlvariable += "<command>loadscheme</command>";
            Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            try 
            {
                ps = connection.prepareStatement("select distinct PMS_DCB_MST_BENEFICIARY_METRE.SCH_TYPE_ID,BENEFICIARY_SNO AS BENEFICIARY_SNO,SCHEME_SNO AS SCHEME_SNO,PMS_SCH_MASTER.SCH_NAME AS SCH_NAME from PMS_DCB_MST_BENEFICIARY_METRE join PMS_SCH_MASTER on PMS_SCH_MASTER.SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO where BENEFICIARY_SNO=?");
               
                ps.setInt(1,Beneficiary_Name);
                          
                res = ps.executeQuery();
                flagname=0;
               while(res.next())
               {
                    flagname=1;
                                              
                    xmlvariable += "<SCH_NAME>" + res.getString("SCH_NAME") + "</SCH_NAME>";
                    xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO") + "</SCHEME_SNO>";
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
		else if (command_var.equalsIgnoreCase("get")) 
        {
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
            else if(tariff_flag_value.equals("Scheme"))
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
		out.println(xmlvariable);
	     System.out.println(xmlvariable);
	}
	 
}
