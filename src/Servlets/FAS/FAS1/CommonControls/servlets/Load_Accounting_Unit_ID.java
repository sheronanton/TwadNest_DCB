package Servlets.FAS.FAS1.CommonControls.servlets;

import java.sql.SQLException;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Load_Accounting_Unit_ID extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException 
   {
                                              
                                               /*
                                                   LoadAccountingUnitID('BOTH_UNITS_AND_OFFICES')
                                                   LoadAccountingUnitID('ONLY_UNITS')
                                                   
                                                   LoadAccountingUnitID('LIST_ALL_UNITS')
                                                   LoadAccountingUnitID('LIST_ALL_UNITS_ONLY')
                                                   
                                                   LoadAccountingUnitID('FOR_LIST_0')      // 0 -- Accounting Units Only 
                                                   LoadAccountingUnitID('FOR_LIST_1')      // 1 -- Both Units and Offices 
                                                   
                                               */
    
       response.setContentType(CONTENT_TYPE);
       PrintWriter out = response.getWriter();
              
//---------------------------------------------------------------------------------------------------------------                  
              
              /**
               * Variables Declaration
               */
              Connection con=null;
              
              ResultSet results=null;
              
              ResultSet rs=null;              
              ResultSet rs2=null;
              ResultSet rs3=null;   
              ResultSet rs4=null;              
              ResultSet rs5=null;
              ResultSet rs6=null;
              ResultSet rs7=null;
        
              PreparedStatement ps=null;
              PreparedStatement ps1=null;
              PreparedStatement ps2=null;
              PreparedStatement ps3=null;           
              PreparedStatement ps5=null;
              PreparedStatement ps6=null;
              PreparedStatement ps7=null;
        
              int  office_id=0;
              String office_name="";
              String COMMAND="";                            
              
//---------------------------------------------------------------------------------------------------------------                  
              
              /**
               * Database Connection
               */              
               try
              {
              
                        ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                        String ConnectionString="";
            
                        String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                        String strdsn=rs1.getString("Config.DSN");
                        String strhostname=rs1.getString("Config.HOST_NAME");
                        String strportno=rs1.getString("Config.PORT_NUMBER");
                        String strsid=rs1.getString("Config.SID");
                        String strdbusername=rs1.getString("Config.USER_NAME");
                        String strdbpassword=rs1.getString("Config.PASSWORD");
            
                  //      ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
            
                         Class.forName(strDriver.trim());
                         con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              }
              catch(Exception e)
              {
                System.out.println("Exception in connection...."+e);
              }
              
        
//---------------------------------------------------------------------------------------------------------------                         
        
        
             /**
              * Session Checking
              */
                HttpSession session=request.getSession(false);
                try
                {
            
                if(session==null)
                {
                    System.out.println(request.getContextPath()+"/index.jsp");
                    response.sendRedirect(request.getContextPath()+"/index.jsp");
                    return;
                }
                    System.out.println(session);
         
                }catch(Exception e)
                {
                    System.out.println("Redirect Error :"+e);
                }
        
                
               /**
                * GET Command Parameter 
                */
                
                COMMAND=request.getParameter("COMMAND");
      
                 System.out.println("FAS_SU-------------->"+session.getAttribute("FAS_SU"));              
                 System.out.println("FAS_SU_ALL---------->"+session.getAttribute("FAS_SU_ALL"));
                 System.out.println("FAS_SU_REGION------->"+session.getAttribute("FAS_SU_REGION"));
                 System.out.println("FAS_SU_CIRCLE------->"+session.getAttribute("FAS_SU_CIRCLE"));
                 System.out.println("FAS_CAO------->"+session.getAttribute("FAS_CAO"));
        
                 System.out.println("COMMAND ------------>"+COMMAND);
                                  
                String FAS_SU="";                 
                if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                    FAS_SU="YES";
                else
                    FAS_SU="NO";       
                    
               String FAS_CAO="";                 
               if(session.getAttribute("FAS_CAO")!=null && ((String)session.getAttribute("FAS_CAO")).equalsIgnoreCase("YES"))
                  FAS_CAO="YES";
               else
                  FAS_CAO="NO";       
            
               String FAS_SU_ALL="";                 
               if(session.getAttribute("FAS_SU_ALL")!=null && ((String)session.getAttribute("FAS_SU_ALL")).equalsIgnoreCase("YES"))
                    FAS_SU_ALL="YES";
                else
                    FAS_SU_ALL="NO";                   
                
                String FAS_SU_REGION="";                 
                if(session.getAttribute("FAS_SU_REGION")!=null && ((String)session.getAttribute("FAS_SU_REGION")).equalsIgnoreCase("YES"))
                    FAS_SU_REGION="YES";
                else
                    FAS_SU_REGION="NO";
            
                String FAS_SU_CIRCLE="";                 
                if(session.getAttribute("FAS_SU_CIRCLE")!=null && ((String)session.getAttribute("FAS_SU_CIRCLE")).equalsIgnoreCase("YES"))
                    FAS_SU_CIRCLE="YES";
                else
                    FAS_SU_CIRCLE="NO";
        
        
//---------------------------------------------------------------------------------------------------------------                         
        
                /**
                 * Get Employee ID from Session 
                 */
             //   HttpSession session=request.getSession(false);
                UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                  
                System.out.println("user id::"+empProfile.getEmployeeId());
                int employee_id=empProfile.getEmployeeId();                
               
                


//---------------------------------------------------------------------------------------------------------------         
        
        /**
         * Find Office ID and Office Name from Employee ID 
         */
        
        try
           {
                   /** Get Employee Office ID */
                   ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
                   ps.setInt(1,employee_id);
                   results=ps.executeQuery();
                        if(results.next()) 
                        {
                           office_id=results.getInt("OFFICE_ID");
                        }
                   results.close();
                   ps.close();
                   
                   /** Get Office Name */
                   ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
                   ps.setInt(1,office_id);
                   results=ps.executeQuery();
                        if(results.next()) 
                         {
                           office_name=results.getString("OFFICE_NAME");
                         }
                   results.close();
                   ps.close();
           }
           catch(Exception e)
           {
               System.out.println(e);
           }
        
        
       
//---------------------------------------------------------------------------------------------------------------  
       
       
        
                  /**
                   * Find Accounting Unit ID and its Name 
                   */
                   
                   
                            String xml="<response><command>Load_Accounting_Unit_ID</command>"; 
                           
                            /**
                             *    1. Employee has FAS_SUPER_USER Role 
                             *    2. List All Accounting Units 
                             */
                            
                            /**
                             * List All Units 
                             */
                             if(FAS_SU_ALL.equalsIgnoreCase("YES") && (COMMAND.equalsIgnoreCase("FOR_LIST_0") || COMMAND.equalsIgnoreCase("FOR_LIST_1")  ))
                             {    
                               System.out.println("1-----><"); 
                               String ALL_SQL="select accounting_unit_id, accounting_unit_name from fas_mst_acct_units order by accounting_unit_id"; 
                               try {
                                   ps5=con.prepareStatement(ALL_SQL);
                                   rs5=ps5.executeQuery();
                                   while(rs5.next())
                                       {
                                             xml=xml+"<option>"; 
                                             xml=xml+"<accounting_unit_id>"+rs5.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                             xml=xml+"<accounting_unit_name>"+rs5.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                                                                                            
                                             xml=xml+"</option>";                                                                                             
                                       }
                                   xml=xml+"<flag>success</flag>";       
                                   
                               }
                               catch(Exception e) {
                                   System.out.println("Failed to Fetch Record for Displaying All the Units -->"+e);
                               }
                             } 
                          
                            /**
                             * FAS CAO Role 
                             */
                            else if(FAS_CAO.equalsIgnoreCase("YES") && (COMMAND.equalsIgnoreCase("FOR_LIST_0") || COMMAND.equalsIgnoreCase("FOR_LIST_1") || COMMAND.equalsIgnoreCase("BOTH_UNITS_AND_OFFICES") || COMMAND.equalsIgnoreCase("ONLY_UNITS") || COMMAND.equalsIgnoreCase("LIST_ALL_UNITS") || COMMAND.equalsIgnoreCase("LIST_ALL_UNITS_ONLY")  ))
                            {    
                                System.out.println("1-----><"); 
                                String ALL_SQL="select accounting_unit_id, accounting_unit_name from fas_mst_acct_units order by accounting_unit_id"; 
                              try {
                                ps5=con.prepareStatement(ALL_SQL);
                                rs5=ps5.executeQuery();
                                while(rs5.next())
                                {
                                    xml=xml+"<option>"; 
                                    xml=xml+"<accounting_unit_id>"+rs5.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                    xml=xml+"<accounting_unit_name>"+rs5.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                                                                                            
                                    xml=xml+"</option>";                                                                                             
                                }
                                xml=xml+"<flag>success</flag>";       
              
                            }
                            catch(Exception e) {
                            System.out.println("Failed to Fetch Record for Displaying All the Units -->"+e);
                            }
                           } 
                             
                             
                             
                            /**
                             * List Units which come under Particular Region
                             */
                            else if(FAS_SU_REGION.equalsIgnoreCase("YES") && (COMMAND.equalsIgnoreCase("FOR_LIST_0") || COMMAND.equalsIgnoreCase("FOR_LIST_1")  ))
                            {    
                                System.out.println("2-----><");  
                                String REGION_SQL="" +
                                "   select                           \n" + 
                                "       accounting_unit_id,          \n" + 
                                "       accounting_unit_name         \n" + 
                                "   from                             \n" + 
                                "        fas_mst_acct_units          \n" + 
                                "   where                            \n" + 
                                "        accounting_unit_office_id in   \n" + 
                                "    (                               \n" + 
                                "       select                       \n" + 
                                "          office_id                 \n" + 
                                "       from                         \n" + 
                                "          com_mst_all_offices_view  \n" + 
                                "       where region_office_id= ?    \n" + 
                                "    )                               \n" + 
                                "   order by accounting_unit_id      \n" + 
                                "                                    \n";
                               
                                try {                                
                                    ps6=con.prepareStatement(REGION_SQL);                                    
                                    ps6.setInt(1,office_id);
                                    rs6=ps6.executeQuery();
                                    while(rs6.next())
                                        {
                                              xml=xml+"<option>"; 
                                              xml=xml+"<accounting_unit_id>"+rs6.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                              xml=xml+"<accounting_unit_name>"+rs6.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                                                                                            
                                              xml=xml+"</option>";                                                                                             
                                        }
                                    xml=xml+"<flag>success</flag>";                                                                        
                                }
                                catch (SQLException e) {
                                    System.out.println("Failed to Fetch Record for Displaying the Units Which come under particular Region -->"+e);
                                }
                             
                            } 
                            /**
                             * List Units Which come under Particular Circle 
                             */
                            else if(FAS_SU_CIRCLE.equalsIgnoreCase("YES") && (COMMAND.equalsIgnoreCase("FOR_LIST_0") || COMMAND.equalsIgnoreCase("FOR_LIST_1")  ))
                            {        
                                System.out.println("3-----><"); 
                                
                                String CIRCLE_SQL="" +
                                "   select                           \n" + 
                                "       accounting_unit_id,          \n" + 
                                "       accounting_unit_name         \n" + 
                                "   from                             \n" + 
                                "        fas_mst_acct_units          \n" + 
                                "   where                            \n" + 
                                "        accounting_unit_office_id in   \n" + 
                                "    (                               \n" + 
                                "       select                       \n" + 
                                "          office_id                 \n" + 
                                "       from                         \n" + 
                                "          com_mst_all_offices_view  \n" + 
                                "       where circle_office_id= ?    \n" + 
                                "    )                               \n" + 
                                "   order by accounting_unit_id      \n" + 
                                "                                    \n";
                                
                                try {                                
                                    ps7=con.prepareStatement(CIRCLE_SQL);                                    
                                    ps7.setInt(1,office_id);
                                    rs7=ps7.executeQuery();
                                    while(rs7.next())
                                        {
                                              xml=xml+"<option>"; 
                                              xml=xml+"<accounting_unit_id>"+rs7.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                              xml=xml+"<accounting_unit_name>"+rs7.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                                                                                            
                                              xml=xml+"</option>";                                                                                             
                                        }
                                    xml=xml+"<flag>success</flag>";                                                                        
                                }
                                catch (SQLException e) {
                                    System.out.println("Failed to Fetch Record for Displaying the Units Which come under particular Region -->"+e);
                                }        
        
                            }                            
                           /**
                            * The following are the Regular Units displays 
                            */
                           else  if(FAS_SU.equalsIgnoreCase("YES") && (COMMAND.equalsIgnoreCase("LIST_ALL_UNITS") || COMMAND.equalsIgnoreCase("LIST_ALL_UNITS_ONLY") || COMMAND.equalsIgnoreCase("FOR_LIST_0") || COMMAND.equalsIgnoreCase("FOR_LIST_1")  ))
                            {          
                                System.out.println("4-----><"); 
                                      if (office_id==5000) {
                                          
                                               try {      
                                                        int unitid=0;
                                                        String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                                                        ps=con.prepareStatement(getWing);
                                                        ps.setInt(1,office_id);
                                                        ps.setInt(2,employee_id);
                                                        ps.setInt(3,office_id);
                                                        rs=ps.executeQuery();
                                                        if(rs.next())
                                                            {
                                                                  xml=xml+"<option>"; 
                                                                  xml=xml+"<accounting_unit_id>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                                                  xml=xml+"<accounting_unit_name>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                                                                                            
                                                                  xml=xml+"</option>";          
                                                              
                                                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");                                                                
                                                                  
                                                                  if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
                                                                      { 
                                                                            String su="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID!=? order by ACCOUNTING_UNIT_NAME";
                                                                            ps=con.prepareStatement(su);
                                                                            ps.setInt(1,unitid);
                                                                            rs=ps.executeQuery();
                                                                            while(rs.next())
                                                                                {
                                                                                     xml=xml+"<option>"; 
                                                                                     xml=xml+"<accounting_unit_id>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                                                                     xml=xml+"<accounting_unit_name>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                         
                                                                                     xml=xml+"</option>";          
                                                                                }
                                                                       }
                                                              xml=xml+"<flag>success</flag>";   
                                                               
                                                            }
                                           
                                                    } 
                                                    catch(Exception e) {
                                                        System.out.println(e);
                                                    }
                                          
                                          }
                                       else {
                                   
                                   
                                   
                                   
                                           }                               
                                       
                            }
                            else 
                            {
                                System.out.println("5-----><"); 
                                           
                              /** Current Posting = Head Office */  
                              if(office_id==5000)
                              {
                              
                                 try
                                 {
                                                          
                                  /** Fetch Current Posting Details */                                    
                                  String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                                  ps=con.prepareStatement(getWing);
                                  ps.setInt(1,office_id);
                                  ps.setInt(2,employee_id);
                                  ps.setInt(3,office_id);
                                  rs=ps.executeQuery();
                                       
                                       while(rs.next())
                                        {   
                                           System.out.println("1--------------->");
                                           xml=xml+"<option>"; 
                                           xml=xml+"<accounting_unit_id>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                             
                                           xml=xml+"<accounting_unit_name>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";       
                                           xml=xml+"</option>"; 
                                           
                                        }
                                    
                                    
                                    
                                 /** Fetch Additional Posting Details */                                    
                                 String getWing_add="select office_id as off_id  from hrm_emp_addl_charge  where employee_id= ? ";
                                 ps1=con.prepareStatement(getWing_add);
                                 ps1.setInt(1,employee_id);
                                 rs2=ps1.executeQuery();                                   
                                 if(rs2.next())  
                                 {                    
                              
                                             int add_office_id=rs2.getInt("off_id");  
                              
                                             /** Current Posting = Head Office   and    Additional Charge = Head Office */
                                                if(add_office_id==5000)
                                                {
                              
                                                   
                                                           String sql="  select  ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_NAME,  OFFICE_WING_SINO                                                                                                          \n" + 
                                                                      "  from  FAS_MST_ACCT_UNITS                                                                                                                                                                \n" + 
                                                                      "  where ACCOUNTING_UNIT_OFFICE_ID in (  select office_id from hrm_emp_addl_charge  where employee_id= ? )                                                                                 \n" + 
                                                                      "  and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id= ? and office_id in (  select office_id from hrm_emp_addl_charge  where employee_id= ? ))  \n" + 
                                                                      "                                                                                                                                                                                          \n";
                                                 
                                                            ps2=con.prepareStatement(sql);
                                                            ps2.setInt(1,employee_id);
                                                            ps2.setInt(2,employee_id);  
                                                            ps2.setInt(3,employee_id);
                              
                                                            rs3=ps2.executeQuery();
                              
                                                            while(rs3.next())
                                                            {        
                                                                    System.out.println("2--------------->");
                                                                    xml=xml+"<option>"; 
                                                                    xml=xml+"<accounting_unit_id>"+rs3.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                                                    xml=xml+"<accounting_unit_name>"+rs3.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                            
                                                                    xml=xml+"</option>"; 
                                                        
                                                             }                             
                                          
                                               } 
                                     
                                               /** Current Posting = Head Office   and    Additional Charge = Other Offices */
                                               else
                                               {           System.out.println("J");
                                                           String sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)";
                                                          
                                                           ps3=con.prepareStatement(sql);
                                                          
                                                           ps3.setInt(1,add_office_id);                                                           
                                                           rs4=ps3.executeQuery();                                                           
                                                           while(rs4.next())
                                                           {
                                                                System.out.println("3--------------->");
                                                                xml=xml+"<option>";  
                                                                xml=xml+"<accounting_unit_id>"+rs4.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                                                xml=xml+"<accounting_unit_name>"+rs4.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                                                                              
                                                                xml=xml+"</option>";    
                                                           }                                          
                                          
                                               }          
                                      
                                     
                                     
                                 }   // End of Result Set                       
                                    
                                                                        
                                xml=xml+"<flag>success</flag>";
                                
                                ps.close();
                                rs.close();
                                
                               }
                               catch(Exception e)
                               {
                                  System.out.println(e);
                               }
                                
                             }
                                
                                
                               //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                       
                                
                                /** Current Posting  =  other Offices  */
                                else
                                    {
                                       System.out.println("6-----><"); 
                                      try
                                      {
                                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                        ps.setInt(1,office_id);
                                        rs=ps.executeQuery();
                                        if(rs.next())
                                        {
                                           System.out.println("4--------------->");
                                           xml=xml+"<option>"; 
                                           xml=xml+"<accounting_unit_id>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                           xml=xml+"<accounting_unit_name>"+rs.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                                      
                                           xml=xml+"</option>"; 
                                        }                               
                                        
                                        
                                          /** Fetch Additional Posting Details */                                    
                                          String getWing_add="select office_id as off_id from hrm_emp_addl_charge  where employee_id= ? ";
                                          ps1=con.prepareStatement(getWing_add);
                                          ps1.setInt(1,employee_id);
                                          rs2=ps1.executeQuery();                                   
                                          if(rs2.next())  
                                          {                                     
                                                      int add_office_id=rs2.getInt("off_id");  
                                              
                                                      /** Current Posting = Head Office   and    Additional Charge = Head Office */
                                                         if(add_office_id==5000)
                                                         {
                                                         
                                                                    String sql="  select  ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_NAME,  OFFICE_WING_SINO                                                                                                          \n" + 
                                                                               "  from  FAS_MST_ACCT_UNITS                                                                                                                                                                \n" + 
                                                                               "  where ACCOUNTING_UNIT_OFFICE_ID in (  select office_id from hrm_emp_addl_charge  where employee_id= ? )                                                                                 \n" + 
                                                                               "  and OFFICE_WING_SINO = ( select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id= ? and office_id in (  select office_id from hrm_emp_addl_charge  where employee_id= ? ))  \n" + 
                                                                               "                                                                                                                                                                                          \n";
                                                          
                                                                     ps2=con.prepareStatement(sql);
                                                                     ps2.setInt(1,employee_id);
                                                                     ps2.setInt(2,employee_id);  
                                                                     ps2.setInt(3,employee_id);
                                                                     rs3=ps2.executeQuery();
                                                                     while(rs3.next())
                                                                     {
                                                                             System.out.println("5--------------->");
                                                                             xml=xml+"<option>"; 
                                                                             xml=xml+"<accounting_unit_id>"+rs3.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                                                             xml=xml+"<accounting_unit_name>"+rs3.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                            
                                                                             xml=xml+"</option>"; 
                                                                 
                                                                      }                             
                                                   
                                                        } 
                                              
                                                        /** Current Posting = Head Office   and    Additional Charge = Other Offices */
                                                        else
                                                        {
                                                                    String sql="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)";
                                                                    ps3=con.prepareStatement(sql);
                                                                    ps3.setInt(1,add_office_id);
                                                                    rs4 = ps3.executeQuery();
                                                                    while(rs4.next())
                                                                    {
                                                                         System.out.println("6--------------->");
                                                                         xml=xml+"<option>"; 
                                                                         xml=xml+"<accounting_unit_id>"+rs4.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";                                      
                                                                         xml=xml+"<accounting_unit_name>"+rs4.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";                                                                                                                                                                                                                              
                                                                         xml=xml+"</option>"; 
                                                                    }                                          
                                                   
                                                        }          
                                               
                                              
                                              
                                          }   // End of Result Set                                                              
                                        
                                           xml=xml+"<flag>success</flag>";
                                           ps.close();
                                           rs.close();
                                           
                                      }
                                      catch(Exception e) {
                                          System.out.println(e);
                                      }
                                      
                                      
                                   }
                            }   
                                  
                          
        
        xml=xml+"</response>";     
        out.println(xml);
        System.out.println(xml);
        
        
        
        out.close();
    }    
    
//---------------------------------------------------------------------------------------------------------------                     

}
