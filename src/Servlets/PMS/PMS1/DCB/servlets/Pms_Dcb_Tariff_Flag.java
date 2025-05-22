/* 
  * Created on : dd-mm-yy 
  * Author     : Sathya
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
 * Servlet implementation class Pms_Dcb_Tariff_Flag
 */

public class Pms_Dcb_Tariff_Flag extends HttpServlet
{
	String new_cond=Controller.new_cond;
	 
	
	private static final long serialVersionUID = 1L;
	String command_var="";
	String xmlvariable="";
	ResultSet res,result_new,res_max,rs_check,res_new8;
	Connection connection = null;
	Controller obj=new Controller();
    PreparedStatement ps,ps_oid,ps_max,ps_check,ps_new8;
    
    String meter_status=Controller.meter_status;
    String meter_status2=Controller.meter_status2;
    int SubDivision;
    int empid;
    int oid;
    int flagvariable;
    int flagname;
    int Beneficiary_Sno;
    int Beneficiary_type;
    int Beneficiary_Name;
    int updatedflag=0;
   int counttariff=0;
    String SCHEME_SNO_VAL[];
    String benesnum[];
    String flagmode[];
    String set_flag[];
    String [] temp_SCHEME_SNO_VAL=null;
	String [] temp_benesnum=null;
	String [] temp_flagmode=null;
	String [] temp_set_flag=null;
	String set_flagmode;
    int i;
    int schemeval,benesnumval;
    int sch_sno_row=0;
    int ben_sno_row=0;
    String flagmode_row;
    String setflag_row;
    String flagmodeval;
    public Pms_Dcb_Tariff_Flag()
    {
        super();
        
    }
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
    }
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
          //  ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" + strsid.trim();
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
        String general_flag="0";
        System.out.println("Session id is:"+userid);
        UserProfile empProfile = (UserProfile)session.getAttribute("UserProfile");
        System.out.println("emp id::" + empProfile.getEmployeeId());
        empid = empProfile.getEmployeeId();
       //int empid=1758;
        String oname = "";
        System.out.println("Empid"+empid);
        try
        {
        	System.out.println("select CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID from PMS_DCB_COM_OFFICE_SWITCH WHERE EMPLOYEE_ID ="+empid);
            ps_oid = connection.prepareStatement("select CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID from PMS_DCB_COM_OFFICE_SWITCH  where EMPLOYEE_ID=?");
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
        try
        {
            command_var=request.getParameter("command");
            //System.out.println(command_var);
        }
        catch (Exception e)
        {
        	System.out.println(e);
        }
       
        try
        {
            general_flag=request.getParameter("general_flag");
          
		        if (general_flag.equalsIgnoreCase("2"))
		        {
		           SCHEME_SNO_VAL=request.getParameterValues("SCHEME_SNO_VAL");
		        }
           System.out.println("SCHEME_SNO_VAL"+SCHEME_SNO_VAL.length);
           
          
       }
       catch (Exception e) 
       {
           System.out.println("Exception in openeing connection:" + e); 
       }
       try
       {
    	   flagmode=request.getParameterValues("flagmode");
          System.out.println("flagmode"+flagmode);
          
         
      }
      catch (Exception e) 
      {
          System.out.println("Exception in openeing connection:" + e); 
      }
      try
      {
   	   benesnum=request.getParameterValues("benesnum");
         System.out.println("benesnum"+benesnum);
         
        
     }
     catch (Exception e) 
     {
         System.out.println("Exception in openeing connection:" + e); 
     }
     try
     {
    	 sch_sno_row=Integer.parseInt(request.getParameter("sch_sno_row"));
    	 System.out.println("sch_sno_row"+sch_sno_row);
        
       
    }
    catch (Exception e) 
    {
        System.out.println("Exception in sch_sno_row:" + e); 
    }
    try
    {
    	ben_sno_row=Integer.parseInt(request.getParameter("ben_sno_row"));
   	 System.out.println("ben_sno_row"+ben_sno_row);
       
      
   }
   catch (Exception e) 
   {
       System.out.println("Exception in sch_sno_row:" + e); 
   }
   try
   {
   	sch_sno_row=Integer.parseInt(request.getParameter("sch_sno_row"));
  	 System.out.println("sch_sno_row"+sch_sno_row);
      
     
  }
  catch (Exception e) 
  {
      System.out.println("Exception in sch_sno_row:" + e); 
  }
    try
    {
   	 set_flag=request.getParameterValues("set_flag");
       
      
   }
   catch (Exception e) 
   {
       System.out.println("Exception in set_flag:" + e); 
   }
   try
   {
	   flagmode_row=request.getParameter("flagmode_row");
      
     
  }
  catch (Exception e) 
  {
      System.out.println("Exception in flagmode_row:" + e); 
  }
  try
  {
	  setflag_row=request.getParameter("setflag_row");
     
    
 }
 catch (Exception e) 
 {
     System.out.println("Exception in setflag_row:" + e); 
 }
        if (command_var.equalsIgnoreCase("loadbeneficiarytype")) 
        {
           
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiarytype</command>";
            try 
            {
                ps = connection.prepareStatement("select BEN_TYPE_ID,BEN_TYPE_SDESC,BEN_TYPE_DESC from PMS_DCB_BEN_TYPE order by BEN_TYPE_ID");
                
                res = ps.executeQuery();
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
        else if (command_var.equalsIgnoreCase("loadsubdivision")) 
        {
            xmlvariable = "<response>";
            xmlvariable += "<command>loadsubdivision</command>";
           
            try 
            {
                ps_new8 = connection.prepareStatement("select HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID AS EMPLOYEE_ID ,\n" + 
                "COM_MST_ALL_OFFICES_VIEW.SUBDIVISION_OFFICE_ID AS SUBDIVISION_OFFICE_ID ,\n" + 
                "COM_MST_ALL_OFFICES_VIEW.OFFICE_ID AS OFFICE_ID,\n" + 
                "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID AS DIVISION_OFFICE_ID,\n" + 
                "OFFICE_LEVEL_ID AS OFFICE_LEVEL_ID ,\n" + 
                "OFFICE_NAME AS OFFICE_NAME\n" + 
                "from HRM_EMP_CURRENT_POSTING\n" + 
                "JOIN\n" + 
                "COM_MST_ALL_OFFICES_VIEW\n" + 
                "ON\n" + 
                "COM_MST_ALL_OFFICES_VIEW.DIVISION_OFFICE_ID=HRM_EMP_CURRENT_POSTING.OFFICE_ID\n" + 
                "AND\n" + 
                "OFFICE_LEVEL_ID='SD'\n" + 
                "WHERE\n" + 
                "HRM_EMP_CURRENT_POSTING.EMPLOYEE_ID=?");
               ps_new8.setInt(1,empid);
                res_new8 = ps_new8.executeQuery();
                flagvariable=0;
                while(res_new8.next()) 
                {
                    flagvariable=1;
                    xmlvariable += "<SUBDIVISION_OFFICE_ID>" + res_new8.getInt("SUBDIVISION_OFFICE_ID") + "</SUBDIVISION_OFFICE_ID>";
                    xmlvariable += "<OFFICE_NAME>" + res_new8.getString("OFFICE_NAME") + "</OFFICE_NAME>";
                    xmlvariable += "<flag>success</flag>";
                                        
                    
                }
                if(flagvariable==0) 
                {
                    xmlvariable += "<SUBDIVISION_OFFICE_ID>-1</SUBDIVISION_OFFICE_ID>";
                    xmlvariable += "<OFFICE_NAME>No data</OFFICE_NAME>";
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
            xmlvariable = "<response>";
            xmlvariable += "<command>loadbeneficiaryname</command>";
            try
            {
            	 Beneficiary_type=Integer.parseInt(request.getParameter("Beneficiary_type"));
            }
            catch (Exception e)
            {
            	System.out.println(e);
            }    
            
           
            
           /* try
            {
            	SubDivision=Integer.parseInt(request.getParameter("SubDivision"));
                System.out.println("SubDivision"+SubDivision);
            }
            catch (Exception e)
            {
            	System.out.println(e);
            }   */    
            try 
            {
            	
            	ps=connection.prepareStatement("select BENEFICIARY_SNO,BENEFICIARY_NAME,BENEFICIARY_TYPE_ID,OTHERS_PRIVATE_SNO ,VILLAGE_PANCHAYAT_SNO,URBANLB_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE  where "+meter_status+" OFFICE_ID=? and BENEFICIARY_TYPE_ID=?) and OFFICE_ID=? order by BENEFICIARY_NAME ");
            	ps.setInt(1,oid);
            	ps.setInt(2,Beneficiary_type);
            	ps.setInt(3,oid);
                           
                res = ps.executeQuery();
                flagname=0;
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
        else if (command_var.equalsIgnoreCase("loadschemes")) 
        {
           
            xmlvariable = "<response>";
            xmlvariable += "<command>loadschemes</command>";
            try
            {
            Beneficiary_Name=Integer.parseInt(request.getParameter("Beneficiary_Name"));
            }
            catch (Exception e)
            {
            	System.out.println(e);
            }    
            try 
            {
                ps = connection.prepareStatement("select distinct PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO,PMS_DCB_MST_BENEFICIARY_METRE.BENEFICIARY_SNO AS BENEFICIARY_SNO ,PMS_SCH_MASTER.SCH_NAME AS SCH_NAME, PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO AS SCHEME_SNO,PMS_DCB_MST_BENEFICIARY_METRE.SETTING_FLAG AS SETTING_FLAG ,PMS_DCB_MST_BENEFICIARY_METRE.TARIFF_FLAG AS TARIFF_FLAG from PMS_DCB_MST_BENEFICIARY_METRE join PMS_SCH_MASTER on "+meter_status2+"  PMS_SCH_MASTER.SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO where BENEFICIARY_SNO=? and (TARIFF_FLAG='-' or TARIFF_FLAG IS NULL or TARIFF_FLAG IS NOT NULL ) and (SETTING_FLAG IS NOT NULL or SETTING_FLAG IS NULL)order by PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO");
              	ps.setInt(1,Beneficiary_Name);
              	res = ps.executeQuery();
                flagname=0;
               while(res.next())
               {
                    flagname=1;
                    
                    xmlvariable += "<recordfound>" + 1 + "</recordfound>";
                    xmlvariable += "<BENEFICIARY_SNO>" + res.getInt("BENEFICIARY_SNO") + "</BENEFICIARY_SNO>";
                    xmlvariable += "<SCHEME_SNO>" + res.getInt("SCHEME_SNO") + "</SCHEME_SNO>";
                    xmlvariable += "<SCH_NAME><![CDATA[" + res.getString("SCH_NAME") + "]]></SCH_NAME>";
                    xmlvariable += "<SETTING_FLAG>" + res.getString("SETTING_FLAG") + "</SETTING_FLAG>";
                    
                    if(res.getString("TARIFF_FLAG")==null)
                    {
                    	xmlvariable += "<TARIFF_FLAG>" + "-" + "</TARIFF_FLAG>";
                    }
                    else
                    {
                    	xmlvariable += "<TARIFF_FLAG>" + res.getString("TARIFF_FLAG") + "</TARIFF_FLAG>";
                    }
                    xmlvariable += "<flag>success</flag>";            
                     
                }
                if(flagname==0) 
                {     
                	xmlvariable += "<recordfound>" + 0 + "</recordfound>";
                    xmlvariable += "<BENEFICIARY_SNO>" + -1 + "</BENEFICIARY_SNO>";
                    xmlvariable += "<SCHEME_SNO>" + -1 + "</SCHEME_SNO>";
                    xmlvariable += "<SETTING_FLAG>" + -1 + "</SETTING_FLAG>";
                    xmlvariable += "<TARIFF_FLAG>" + -1+ "</TARIFF_FLAG>";
                   
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
        else if (command_var.equalsIgnoreCase("report"))
        {	 String  ben_sno1 =request.getParameter("ben_sno");
             String  Beneficiary_type =request.getParameter("Beneficiary_type");
             String cond="";
             if (Integer.parseInt(ben_sno1)==0)
            	 cond=" where "+new_cond+" BENEFICIARY_TYPE_ID="+Beneficiary_type+" and OFFICE_ID="+oid;
             else
            	 cond=" where "+new_cond+" BENEFICIARY_SNO="+ben_sno1;
          try {
        	  
        	 String sel_qry="SELECT ben.beneficiary_name,met.metre_location,met.tariff_flag,met.sch_short_desc AS sch_short_desc_new FROM((SELECT beneficiary_name,beneficiary_sno FROM pms_dcb_mst_beneficiary "+ cond +") ben JOIN (SELECT metre_location, beneficiary_sno,tariff_flag, metre_sno, scheme_sno, sch_name as sch_short_desc  FROM pms_dcb_mst_beneficiary_metre JOIN pms_sch_master ON "+meter_status2+" pms_sch_master.sch_sno = pms_dcb_mst_beneficiary_metre.scheme_sno ORDER BY beneficiary_sno,metre_sno) met ON met.beneficiary_sno = ben.beneficiary_sno)";
        	 obj.testQry(sel_qry);
        	 ps = connection.prepareStatement(sel_qry);
         	 
        	 ResultSet rs= ps.executeQuery();
        	 xmlvariable = "<response>";
        	 xmlvariable += "<command>Report</command>";
        	 while (rs.next())
        	 {
        		  
        		   
        		  xmlvariable += "<TARIFF_MODE>"+obj.isNull(rs.getString(3),2)+"</TARIFF_MODE>";
        		  xmlvariable += "<BENEFICIARY_NAME>"+obj.isNull(rs.getString(1),2)+"</BENEFICIARY_NAME>";
        		  xmlvariable += "<METRE_LOCATION>"+obj.isNull(rs.getString(2),2)+"</METRE_LOCATION>";
        		  xmlvariable += "<SCH_SHORT_DESC>"+obj.isNull(rs.getString(4),2)+"</SCH_SHORT_DESC>";
        		  
        	 }
        	 	   xmlvariable += "</response>";
          } catch (SQLException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			} 
        }
        else if (command_var.equalsIgnoreCase("insert")) 
        {
        	 xmlvariable = "<response>";
             xmlvariable += "<command>add</command>";
             String  ben_sno =request.getParameter("ben_sno");
             
           if (Integer.parseInt(general_flag)==1)
           {
        	 
        	  try {
        	    ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set TARIFF_FLAG='B' where BENEFICIARY_SNO=?");		
        	 	ps.setInt(1,Integer.parseInt(ben_sno));
        	    ps.executeUpdate();
        	    updatedflag=1;
        	    
        	    ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY set TARIFF_MODE='B' where BENEFICIARY_SNO=?");		
        	 	ps.setInt(1,Integer.parseInt(ben_sno));
        	    ps.executeUpdate();
        	    xmlvariable += "<flag>success</flag>";
        	   
        	    System.out.println("------------------------------------------------------------>Update  "+ben_sno);
			} catch (NumberFormatException e) {
				 xmlvariable += "<flag>faliure</flag>";
				//  Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			}
			
			 xmlvariable += "</response>";
           }
             
           if (Integer.parseInt(general_flag)==2)
           {
        	try {
				ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY set TARIFF_MODE='S' where BENEFICIARY_SNO=?");
				ps.setInt(1,Integer.parseInt(ben_sno));
	       	    ps.executeUpdate();
	       	    
	       	    
	       	   
			} catch (SQLException e1) {
				//  Auto-generated catch block
				
				e1.printStackTrace();
			}		
       	 	
        	   
            for(i=0;i<SCHEME_SNO_VAL.length;i++)
   			{
            	 temp_SCHEME_SNO_VAL=SCHEME_SNO_VAL[i].split(",");
            	 temp_benesnum=benesnum[i].split(",");
            	 temp_flagmode=flagmode[i].split(",");
            	 temp_set_flag=set_flag[i].split(",");
            	 for(i=0;i<temp_SCHEME_SNO_VAL.length;i++)
            	 {
            		try
            		{
            		 schemeval=Integer.parseInt(temp_SCHEME_SNO_VAL[i]);
            		 benesnumval=Integer.parseInt(temp_benesnum[i]);
            		}catch(Exception e) {}
            		 flagmodeval=temp_flagmode[i];
            		 set_flagmode=temp_set_flag[i];
                	 try
                   	 {
                   		 
                   		 ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set TARIFF_FLAG=?,SETTING_FLAG=? where BENEFICIARY_SNO=? and SCHEME_SNO=?");
                   		 ps.setString(1,flagmodeval);
                   		 ps.setString(2,set_flagmode);
                         ps.setInt(3,benesnumval);
                         ps.setInt(4,schemeval);
                        
                             ps.executeUpdate();
                             System.out.println("Updated");
                             updatedflag=1;
                   	   
                   	 }
                   	 catch(Exception e)
                   	 {
                   		 System.out.println("Error"+e);
                   		 xmlvariable += "<flag>faliure</flag>";
                   	 }
                   	 
                   	
            	 }// for end 
            	 
   			      }// Flag =2 Scheme or location wise
            
             	 if(updatedflag==1)
            	 {
            		 xmlvariable += "<flag>success</flag>";
            	 }
             	 xmlvariable += "</response>";
            	
   			}// II for
          
        }
        else if (command_var.equalsIgnoreCase("insert_rowwise")) 
        {
        	 xmlvariable = "<response>";
             xmlvariable += "<command>insert_rowwise</command>";
             
             try 
             {
 				ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY set TARIFF_MODE='S' where BENEFICIARY_SNO=?");
 				ps.setInt(1,ben_sno_row);
 	       	    ps.executeUpdate();
 	       	  
 	       	   
 			} catch (SQLException e1) 
 			{
 				 System.out.println("Error"+e1);
 			}
 			 try
           	 {
           		 
           		 ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set TARIFF_FLAG=?,SETTING_FLAG=? where BENEFICIARY_SNO=? and SCHEME_SNO=?");
           		 ps.setString(1,flagmode_row);
           		 ps.setString(2,setflag_row);
                 ps.setInt(3,ben_sno_row);
                 ps.setInt(4,sch_sno_row);
                
                     ps.executeUpdate();
                     System.out.println("Updated");
                     updatedflag=1;
           	   
                   
           	 }
           	 catch(Exception e)
           	 {
           		 xmlvariable += "<flag>faliure</flag>";
           	 }
           	if(updatedflag==1)
           	{
       		 xmlvariable += "<flag>success</flag>";
           	}
        	 xmlvariable += "</response>";
        }
        else if (command_var.equalsIgnoreCase("insert_rowwiseunfreeze")) 
        {
        	 xmlvariable = "<response>";
             xmlvariable += "<command>insert_rowwiseunfreeze</command>";
             try
  			{
            	 
            	 
            	 ps = connection.prepareStatement("select count(*) as counttariff from PMS_DCB_TARIFF_SLAB where BENEFICIARY_SNO=? and SCH_SNO=? and TARIFF_FLAG=? and active_status = 'A'");
            	 ps.setInt(1,ben_sno_row);
 				 ps.setInt(2,sch_sno_row);
 				ps.setString(3,flagmode_row);
  	       	    res=ps.executeQuery();
  	       	    if(res.next())
  	       	    {
  	       	    	
  	       	   counttariff=res.getInt("counttariff");
  	       	    }
  			}
  			catch(SQLException e1) 
  			{
  				System.out.println("Error in count"+e1);
  			}
  			if(counttariff<=0)
  			{
  				try 
  				{
	 				ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY set TARIFF_MODE='S' where BENEFICIARY_SNO=?");
	 				ps.setInt(1,ben_sno_row);
	 	       	    ps.executeUpdate();
 	       	  
 	       	   
  				} 
  				catch (SQLException e1) 
  				{
  					System.out.println("Error"+e1);
  				}
 			
 			
	 			 try
	           	 {
	           		 
	           		 ps = connection.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE set TARIFF_FLAG=?,SETTING_FLAG=? where BENEFICIARY_SNO=? and SCHEME_SNO=?");
	           		 ps.setString(1,flagmode_row);
	           		 ps.setString(2,setflag_row);
	                 ps.setInt(3,ben_sno_row);
	                 ps.setInt(4,sch_sno_row);
	                
	                     ps.executeUpdate();
	                     System.out.println("Updated");
	                     updatedflag=1;
	           	   
	                   
	           	 }
	           	 catch(Exception e)
	           	 {
	           		 System.out.println("Error"+e);
	           		xmlvariable +="<counttariff>0</counttariff>";
	           		 xmlvariable += "<flag>faliure</flag>";
	           	 }
  			
	           	if(updatedflag==1)
	           	{
	           		xmlvariable +="<counttariff>0</counttariff>";
	       		 xmlvariable += "<flag>success</flag>";
	           	}
  			}
  			else
  			{
  				xmlvariable +="<counttariff>1</counttariff>";
  	       		 xmlvariable += "<flag>success</flag>";
  			}
        	 xmlvariable += "</response>";
        }	
         out.println(xmlvariable);
        
	}

	

}
