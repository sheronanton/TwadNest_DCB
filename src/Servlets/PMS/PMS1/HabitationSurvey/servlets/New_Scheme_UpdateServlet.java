package Servlets.PMS.PMS1.HabitationSurvey.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;

public class New_Scheme_UpdateServlet extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection connection=null;
    private Statement statement=null;
    private PreparedStatement ps=null;
    private PreparedStatement ps1=null;
    private PreparedStatement ps2=null;
    private PreparedStatement ps3=null;
    private PreparedStatement ps4=null;
    private PreparedStatement ps5=null;
    private PreparedStatement ps6=null;
    private PreparedStatement ps7=null;

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
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

                      //    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

                           Class.forName(strDriver.trim());
                           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                         System.out.println("this is habitation servlet");
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
             System.out.println("Exception in openeing connection:"+e);
          }
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        System.out.println("servlet called");
                String strCommand = ""; 
                    String xml="";
                    
                    
         /* declaration of variables for the Scheme tab */
                int District_Code=0;
                int Block_Code=0;
                int Panchayat_Code=0;
                int Habitation_Code=0;
                
                int SchemeNumber=0;
                String SchemeTypeCode="";
                String ModeFinance="";
                String AgencyExe="";
                String SchemeCompDt="";
                int SchemeCost=0;
                String PresentWorkCond="";
                int TotalNoTWAD=0;
                int TotalNoPanchayat=0;
                String SchemeAdequate="";
                int HSTotalNo=0;
                int HSCvalue=0;
                int HSLpcd=0;
                
          /* Declaration of variables for Source tab     */
          
                int SOURCE_NUMBER=0;
                int DEPTH_IN_METRE=0;
                int DIAGONAL_IN_METRE=0;
                int SUMMER_YIELD_IN_LPM=0;
                int WINTER_YIELD_IN_LPM=0;
                int RIVER_DISTANCE=0;
                int OORANI_DISTANCE=0;
                int IRRIGATION_DISTANCE=0;
                int SUMMER_WATER_LEVEL_GL=0;
                int WINTER_WATER_LEVEL_GL=0;
                int DISTANCE_SOURCE_DEST=0;
                int ELEVATION_METRES=0;
        
                String WATER_SOURCE_TYPE_ID="";
                String WHETHER_POTABLE="";
                String sWHETHER_ADEQUATE="";
                String sPRESENT_IN_USE="";
                String SAFE_WATER_SOURCE_TYPE_ID="";
                String WHETER_OUTSIDE_HABIT="";

           /* Declaration of variabled for Pumpset tab */
           
                int PUMPSET_NO=0;
                int SOURCE_NO=0;
                int PUMPSET_TYPE_ID=0;
                int YEAR_INSTALLED=0;
                int MONTH_INSTALLED=0;
                int PS_HORSE_POWER=0;
                
                int DUTY_Q_IN_LPM=0;
                int DUTY_H_IN_LPM=0;
                int HOURS_WORKING_PER_DAY=0;
                int PUMPSET_STATUS_ID=0;
                
       /* Declaration for pumping main Tab */ 
        
                int PUMPMAIN_NO=0;
                int LENGTH_KM=0;
                int PUMPMAIN_PIPE_TYPE_ID=0;
                int PIPE_SIZE_IN_MM=0;
                int PUMPMAIN_STATUS_ID=0;
                
               
             /* Declaration for service reservoir tab */
             
                int SR_NO=0;
                int SERVICE_RESERVOIR_TYPE_ID=0;
                int SR_MATERIAL_TYPE_ID=0;
                int SR_CAPACITY_LITRES=0;
                String CAPACITY_ADEQUATE="";
                int YEAR_EXECUTED=0;   
                int MONTH_EXECUTED=0;
                String SR_LOCATION="";
                int SR_STATUS_ID=0;
       
             /* Declaration for distribution system Tab */
             
                 int DS_NO=0;
                 int DS_LENGTH_KM=0;
                 int DS_PIPE_TYPE_ID=0;   
                 int SIZE_IN_MM=0;
                 int DS_STATUS_ID=0;
                 String DS_WHETHER_ADEQUATE="";
                 
                    response.setContentType("text/xml");
                    PrintWriter pw=response.getWriter();    
                    response.setHeader("Cache-Control","no-cache");
                    try
                    {
                      strCommand = request.getParameter("command");      
                    }
                    catch(Exception e)
                    {
                      e.printStackTrace();
                    }
               
                                        //getting values from script
                    
                     try
                     {
                         District_Code = Integer.parseInt(request.getParameter("DistrictCode"));
                         Block_Code =Integer.parseInt(request.getParameter("BlockCode"));
                         Panchayat_Code =Integer.parseInt(request.getParameter("PanchayatCode"));
                         Habitation_Code =Integer.parseInt(request.getParameter("HabitationCode"));
                     }
                     catch(Exception e)
                     { 
                         System.out.println("in getting values in from the first tab**** "+ e);
                     }
                     
                // This is the update for the population first tab
                
                
          if(strCommand.equals("SchemeUpdate"))
          {
             xml="<response><command>SchemeUpdate</command>";
                        try
                       {
                           //getting population tab values from script
                           SchemeNumber =Integer.parseInt(request.getParameter("SchemeNumber"));
                           SchemeTypeCode =request.getParameter("SchemeTypeCode");
                           ModeFinance =request.getParameter("ModeFinance");
                           AgencyExe =request.getParameter("AgencyExe");
                           SchemeCost =Integer.parseInt(request.getParameter("SchemeCost"));
                           TotalNoTWAD =Integer.parseInt(request.getParameter("TotalNoTWAD"));
                           TotalNoPanchayat =Integer.parseInt(request.getParameter("TotalNoPanchayat"));
                           HSTotalNo =Integer.parseInt(request.getParameter("HSTotalNo"));
                           HSCvalue =Integer.parseInt(request.getParameter("HSCvalue"));
                           HSLpcd =Integer.parseInt(request.getParameter("HSLpcd"));
                           PresentWorkCond=request.getParameter("PresentWorkCond");
                           SchemeAdequate=request.getParameter("SchemeAdequate");
                           System.out.println("TotalNoPanchayat*************" + TotalNoPanchayat);
                           
                           String dateString1=request.getParameter("SchemeCompDt");
                           java.sql.Date date1=null;
                           SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                               try
                               {
                                 java.util.Date d1=dateFormat1.parse(dateString1);
                                   dateFormat1.applyPattern("yyyy-MM-dd");
                                 SchemeCompDt=dateFormat1.format(d1);

                               }catch(Exception e)
                             {
                               e.printStackTrace();
                             }
                              date1=Date.valueOf(SchemeCompDt);
                              System.out.println("date after formatting" +date1);
                                          try
                                                   { 
                         String sqlSchemes = "UPDATE HBS_MST_SCHEMES SET SCHEME_TYPE_ID=?,PROGRAMME_ID=?,EXEC_AGENCY_ID=?,SCHEME_COMPLETION_DATE=?,COST_IN_LAKHS=?,SCHEME_STATUS_ID=?,NO_FOUNTAINS_TWAD=?,NO_FOUNTAINS_ADDITIONAL=?,FOUNTAIN_WATER_ADEQUATE=?,HOUSE_CONNECTIONS=?,HOUSE_CONNECTION_POPULATION=?,LPCD_AFTER_COMPLETION=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SCHEME_NO=?";  
                         
                             
                                        ps=connection.prepareStatement(sqlSchemes);
                                         
                                          ps.setString(1,SchemeTypeCode);
                                          ps.setString(2,ModeFinance);
                                          ps.setString(3,AgencyExe);
                                          ps.setDate(4,date1);
                                          ps.setInt(5,SchemeCost);
                                          ps.setString(6,PresentWorkCond);
                                          ps.setInt(7,TotalNoTWAD);
                                          ps.setInt(8,TotalNoPanchayat);
                                          ps.setString(9,SchemeAdequate);
                                          ps.setInt(10,HSTotalNo);
                                          ps.setInt(11,HSCvalue);
                                          ps.setInt(12,HSLpcd);
                                          
                                          ps.setInt(13,District_Code);
                                          ps.setInt(14,Block_Code);
                                          ps.setInt(15,Panchayat_Code);
                                          ps.setInt(16,Habitation_Code);
                                          ps.setInt(17,SchemeNumber);
                                             ps.executeUpdate();        
                                        xml=xml+"<flag>success</flag>";
                                        ps.close();
                                      }
                                      catch(Exception e)
                                      {        
                                         System.out.println("exception in the update schemes SETTING VALUES PS"+ e);
                                        
                                      }
                         
                       }
                       catch(Exception ae)
                       {
                       System.out.println("error in the scheme tab update" + ae);
                       xml=xml+"<flag>failure</flag>";
                       }
              xml=xml+"</response>";
          }
          
          //This update is for the second Source tab
          
          else if(strCommand.equals("SourceUpdate"))
           {
              xml="<response><command>SourceUpdate</command>";
               try
               {
                  //getting source tab values from script
                  SchemeNumber =Integer.parseInt(request.getParameter("SchemeNumber"));
                  SOURCE_NUMBER =Integer.parseInt(request.getParameter("SOURCE_NUMBER"));
                  DEPTH_IN_METRE =Integer.parseInt(request.getParameter("DEPTH_IN_METRE"));
                  DIAGONAL_IN_METRE =Integer.parseInt(request.getParameter("DIAGONAL_IN_METRE"));
                  SUMMER_YIELD_IN_LPM =Integer.parseInt(request.getParameter("SUMMER_YIELD_IN_LPM"));
                  WINTER_YIELD_IN_LPM =Integer.parseInt(request.getParameter("WINTER_YIELD_IN_LPM"));
                  RIVER_DISTANCE =Integer.parseInt(request.getParameter("RIVER_DISTANCE"));
                  OORANI_DISTANCE =Integer.parseInt(request.getParameter("OORANI_DISTANCE"));
                  IRRIGATION_DISTANCE =Integer.parseInt(request.getParameter("IRRIGATION_DISTANCE"));
                  SUMMER_WATER_LEVEL_GL =Integer.parseInt(request.getParameter("SUMMER_WATER_LEVEL_GL"));
                   WINTER_WATER_LEVEL_GL =Integer.parseInt(request.getParameter("WINTER_WATER_LEVEL_GL"));
                   DISTANCE_SOURCE_DEST =Integer.parseInt(request.getParameter("DISTANCE_SOURCE_DEST"));
                   ELEVATION_METRES =Integer.parseInt(request.getParameter("ELEVATION_METRES"));

                  WATER_SOURCE_TYPE_ID=request.getParameter("WATER_SOURCE_TYPE_ID").trim();
                  WHETHER_POTABLE=request.getParameter("WHETHER_POTABLE");
                   sWHETHER_ADEQUATE=request.getParameter("WHETHER_ADEQUATE");
                   sPRESENT_IN_USE=request.getParameter("PRESENT_IN_USE");
                   SAFE_WATER_SOURCE_TYPE_ID=request.getParameter("SAFE_WATER_SOURCE_TYPE_ID");
                   WHETER_OUTSIDE_HABIT=request.getParameter("WHETER_OUTSIDE_HABIT");
        
                    System.out.println(WHETHER_POTABLE + "-" + sWHETHER_ADEQUATE + "-" + sPRESENT_IN_USE +"-" + WHETER_OUTSIDE_HABIT + " - " + WATER_SOURCE_TYPE_ID);
                  String sqlSource = "UPDATE HBS_MST_SCHEME_COMP_SOURCE SET DEPTH_IN_METRE=?,DIAGONAL_IN_METRE=?,SUMMER_YIELD_IN_LPM=?,WINTER_YIELD_IN_LPM=?,RIVER_DISTANCE=?,OORANI_DISTANCE=?,IRRIGATION_DISTANCE=?,SUMMER_WATER_LEVEL_GL=?,WINTER_WATER_LEVEL_GL=?,DISTANCE_SOURCE_DEST=?,ELEVATION_METRES=?,WHETHER_POTABLE=?,WHETHER_ADEQUATE=?,PRESENT_IN_USE=?,SAFE_WATER_SOURCE_TYPE_ID=?,WHETER_OUTSIDE_HABIT=?,WATER_SOURCE_TYPE_ID=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SCHEME_SLNO=? and SOURCE_NUMBER=?";  
                
                    try
                             {
                               ps1=connection.prepareStatement(sqlSource);
                                
                                 ps1.setInt(1,DEPTH_IN_METRE);
                                 ps1.setInt(2,DIAGONAL_IN_METRE);
                                 ps1.setInt(3,SUMMER_YIELD_IN_LPM);
                                 ps1.setInt(4,WINTER_YIELD_IN_LPM);
                                 ps1.setInt(5,RIVER_DISTANCE);

                                 ps1.setInt(6,OORANI_DISTANCE);
                                 ps1.setInt(7,IRRIGATION_DISTANCE);
                                 ps1.setInt(8,SUMMER_WATER_LEVEL_GL);
                                 ps1.setInt(9,WINTER_WATER_LEVEL_GL);
                                 ps1.setInt(10,DISTANCE_SOURCE_DEST);
                                 ps1.setInt(11,ELEVATION_METRES);
                                 
                                 ps1.setString(12,WHETHER_POTABLE);
                                 ps1.setString(13,sWHETHER_ADEQUATE);
                                 ps1.setString(14,sPRESENT_IN_USE);
                                 ps1.setString(15,SAFE_WATER_SOURCE_TYPE_ID);
                                 ps1.setString(16,WHETER_OUTSIDE_HABIT);
                                 System.out.println("id is:"+WATER_SOURCE_TYPE_ID);
                                 ps1.setString(17,WATER_SOURCE_TYPE_ID);
                                 
                                 ps1.setInt(18,District_Code);
                                 ps1.setInt(19,Block_Code);
                                 ps1.setInt(20,Panchayat_Code);
                                 ps1.setInt(21,Habitation_Code);
                                 ps1.setInt(22,SchemeNumber);
                                 ps1.setInt(23,SOURCE_NUMBER);
                                 ps1.executeUpdate();      
                                 
                               xml=xml+"<flag>success</flag>";
                               ps1.close();
                             }
                             catch(Exception e)
                             {        
                                System.out.println("exception in the update source SETTING VALUES PS"+ e);
                               
                             }
                
               }
               catch(Exception ae)
               {
               System.out.println("error in the source tab update" + ae);
               xml=xml+"<flag>failure</flag>";
               }

               xml=xml+"</response>";
           }
          
          //This is to update pump set details tab
          
          else if(strCommand.equals("PumpsetUpdate"))
           {
              xml="<response><command>PumpsetUpdate</command>";
                         try
                        {
                            //getting pump set tab values from script
                             SchemeNumber =Integer.parseInt(request.getParameter("SchemeNumber"));
                          PUMPSET_NO =Integer.parseInt(request.getParameter("PUMPSET_NO"));
                          SOURCE_NO =Integer.parseInt(request.getParameter("SOURCE_NO"));
                          PUMPSET_TYPE_ID =Integer.parseInt(request.getParameter("PUMPSET_TYPE_ID"));
                          YEAR_INSTALLED = Integer.parseInt(request.getParameter("YEAR_INSTALLED"));
                          MONTH_INSTALLED =Integer.parseInt(request.getParameter("MONTH_INSTALLED"));
                          PS_HORSE_POWER =Integer.parseInt(request.getParameter("PS_HORSE_POWER"));
                            
                            DUTY_Q_IN_LPM =Integer.parseInt(request.getParameter("DUTY_Q_IN_LPM"));
                            DUTY_H_IN_LPM =Integer.parseInt(request.getParameter("DUTY_H_IN_METER"));
                            HOURS_WORKING_PER_DAY =Integer.parseInt(request.getParameter("HOURS_WORKING_PER_DAY"));
                            PUMPSET_STATUS_ID = Integer.parseInt(request.getParameter("PUMPSET_STATUS_ID"));
                          String sqlpumpset = "UPDATE HBS_MST_SCHEME_COMP_PS SET SOURCE_NO=?,PUMPSET_TYPE_ID=?,YEAR_INSTALLED=?,MONTH_INSTALLED=?,PS_HORSE_POWER=?,DUTY_Q_IN_LPM=?,DUTY_H_IN_LPM=?,HOURS_WORKING_PER_DAY=?,PUMPSET_STATUS_ID=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SCHEME_SLNO=? and PUMPSET_NO=?";  
                          
                              try
                                       {
                                         ps2=connection.prepareStatement(sqlpumpset);
                                           ps2.setInt(1,SOURCE_NO);
                                           ps2.setInt(2,PUMPSET_TYPE_ID);
                                           ps2.setInt(3,YEAR_INSTALLED);
                                           ps2.setInt(4,MONTH_INSTALLED);
                                           ps2.setInt(5,PS_HORSE_POWER);
                                           ps2.setInt(6,DUTY_Q_IN_LPM);
                                           
                                           ps2.setInt(7,DUTY_H_IN_LPM);
                                           ps2.setInt(8,HOURS_WORKING_PER_DAY);
                                           ps2.setInt(9,PUMPSET_STATUS_ID);
                                          
                                           ps2.setInt(10,District_Code);
                                           ps2.setInt(11,Block_Code);
                                           ps2.setInt(12,Panchayat_Code);
                                           ps2.setInt(13,Habitation_Code);
                                           ps2.setInt(14,SchemeNumber);
                                           ps2.setInt(15,PUMPSET_NO);
                                           
                                          ps2.executeUpdate();        
                                         xml=xml+"<flag>success</flag>";
                                         ps2.close();
                                       }
                                       catch(Exception e)
                                       {        
                                          System.out.println("exception in the update pumpset GETTING VALUES PS1"+ e);
                                         
                                       }
                          
                        }
                        catch(Exception ae)
                        {
                        System.out.println("error in the pumpset tab update" + ae);
                        xml=xml+"<flag>failure</flag>";
                        }
               xml=xml+"</response>";
           }
          
          
           
            // To update Pumping Main Tab
            
             else if(strCommand.equals("PumpMainUpdate"))
             {
                xml="<response><command>PumpMainUpdate</command>";
                           try
                          {
                              //getting pumping main tab values from script
                               SchemeNumber =Integer.parseInt(request.getParameter("SchemeNumber"));
                            PUMPMAIN_NO =Integer.parseInt(request.getParameter("PUMPMAIN_NO"));
                            LENGTH_KM =Integer.parseInt(request.getParameter("LENGTH_KM"));
                            PUMPMAIN_PIPE_TYPE_ID =Integer.parseInt(request.getParameter("PUMPMAIN_PIPE_TYPE_ID"));
                            PIPE_SIZE_IN_MM = Integer.parseInt(request.getParameter("PIPE_SIZE_IN_MM"));
                            PUMPMAIN_STATUS_ID =Integer.parseInt(request.getParameter("PUMPMAIN_STATUS_ID"));

                            String sqlPumpMain = "UPDATE HBS_MST_SCHEME_COMP_PM SET LENGTH_KM=?,PUMPMAIN_PIPE_TYPE_ID=?,PIPE_SIZE_IN_MM=?,PUMPMAIN_STATUS_ID=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SCHEME_SLNO=? and PUMPMAIN_NO=?";  
                            
                                try
                                         {
                                             ps3=connection.prepareStatement(sqlPumpMain);
                                             ps3.setInt(1,LENGTH_KM);
                                             ps3.setInt(2,PUMPMAIN_PIPE_TYPE_ID);
                                             ps3.setInt(3,PIPE_SIZE_IN_MM);
                                             ps3.setInt(4,PUMPMAIN_STATUS_ID);

                                             ps3.setInt(5,District_Code);
                                             ps3.setInt(6,Block_Code);
                                             ps3.setInt(7,Panchayat_Code);
                                             ps3.setInt(8,Habitation_Code);
                                             ps3.setInt(9,SchemeNumber);
                                             ps3.setInt(10,PUMPMAIN_NO);
                                             
                                            ps3.executeUpdate();        
                                           xml=xml+"<flag>success</flag>";
                                           ps3.close();
                                         }
                                         catch(Exception e)
                                         {        
                                            System.out.println("exception in the update pumping main GETTING VALUES PS1"+ e);
                                           
                                         }
                            
                          }
                          catch(Exception ae)
                          {
                          System.out.println("error in the pumping main tab update" + ae);
                          xml=xml+"<flag>failure</flag>";
                          }
                 xml=xml+"</response>";
             }
             
             //This is for service reservoir Tab
              else if(strCommand.equals("ServiceResUpdate"))
               {
                  xml="<response><command>ServiceResUpdate</command>";
                             try
                            {
                                //getting Service reservoir tab values from script
                                 SchemeNumber =Integer.parseInt(request.getParameter("SchemeNumber"));
                              SR_NO =Integer.parseInt(request.getParameter("SR_NO"));
                              SERVICE_RESERVOIR_TYPE_ID =Integer.parseInt(request.getParameter("SERVICE_RESERVOIR_TYPE_ID"));
                              SR_MATERIAL_TYPE_ID =Integer.parseInt(request.getParameter("SR_MATERIAL_TYPE_ID"));
                              SR_CAPACITY_LITRES=Integer.parseInt(request.getParameter("SR_CAPACITY_LITRES"));
                                YEAR_EXECUTED=Integer.parseInt(request.getParameter("YEAR_EXECUTED"))  ;
                                MONTH_EXECUTED=Integer.parseInt(request.getParameter("MONTH_EXECUTED"));
                                SR_LOCATION=request.getParameter("SR_LOCATION");
                                SR_STATUS_ID=Integer.parseInt(request.getParameter("SR_STATUS_ID"));
                                CAPACITY_ADEQUATE=request.getParameter("CAPACITY_ADEQUATE");  
                                
                              String sqlSR = "UPDATE HBS_MST_SCHEME_COMP_SR SET SERVICE_RESERVOIR_TYPE_ID=?,SR_MATERIAL_TYPE_ID=?,SR_CAPACITY_LITRES=?,CAPACITY_ADEQUATE=?,YEAR_EXECUTED=?,MONTH_EXECUTED=?,SR_LOCATION=?,SR_STATUS_ID=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SCHEME_SLNO=? and SR_NO=?";  
                              
                                  try
                                           {
                                             ps4=connection.prepareStatement(sqlSR);
                                               ps4.setInt(1,SERVICE_RESERVOIR_TYPE_ID);
                                               ps4.setInt(2,SR_MATERIAL_TYPE_ID);
                                               ps4.setInt(3,SR_CAPACITY_LITRES);
                                               ps4.setString(4,CAPACITY_ADEQUATE);
                                               ps4.setInt(5,YEAR_EXECUTED);
                                               ps4.setInt(6,MONTH_EXECUTED);
                                               ps4.setString(7,SR_LOCATION);
                                               ps4.setInt(8,SR_STATUS_ID);
                                               
                                               ps4.setInt(9,District_Code);
                                               ps4.setInt(10,Block_Code);
                                               ps4.setInt(11,Panchayat_Code);
                                               ps4.setInt(12,Habitation_Code);
                                               ps4.setInt(13,SchemeNumber);
                                               ps4.setInt(14,SR_NO);
                                                  ps4.executeUpdate();        
                                             xml=xml+"<flag>success</flag>";
                                             ps4.close();
                                           }
                                           catch(Exception e)
                                           {        
                                              System.out.println("exception in the update SR GETTING VALUES PS1"+ e);
                                             
                                           }
                              
                            }
                            catch(Exception ae)
                            {
                            System.out.println("error in the SR tab update" + ae);
                            xml=xml+"<flag>failure</flag>";
                            }
                   xml=xml+"</response>";
               }
               
               //This is for Distribution Tab
                else if(strCommand.equals("DistributionSysUpdate"))
                 {
                    xml="<response><command>DistributionSysUpdate</command>";
                               try
                              {
                                  //getting distribution tab values from script
                                   SchemeNumber =Integer.parseInt(request.getParameter("SchemeNumber"));
                                  DS_NO =Integer.parseInt(request.getParameter("DS_NO"));
                                  DS_LENGTH_KM =Integer.parseInt(request.getParameter("LENGTH_KM"));
                                  DS_PIPE_TYPE_ID =Integer.parseInt(request.getParameter("DS_PIPE_TYPE_ID"));
                                  SIZE_IN_MM =Integer.parseInt(request.getParameter("SIZE_IN_MM"));
                                  DS_STATUS_ID =Integer.parseInt(request.getParameter("DS_STATUS_ID"));
                                  DS_WHETHER_ADEQUATE =request.getParameter("WHETHER_ADEQUATE");
            
                                    System.out.println("values for DS: " + SchemeNumber + "- " + DS_NO + "- " + DS_LENGTH_KM +"-" +DS_PIPE_TYPE_ID + "-" +SIZE_IN_MM+ "status id starting" +DS_STATUS_ID + "this is end of status id" +DS_WHETHER_ADEQUATE + "THATS ALL");
                                String sqlDS = "UPDATE HBS_MST_SCHEME_COMP_DS SET LENGTH_KM=?,DS_PIPE_TYPE_ID=?,SIZE_IN_MM=?,DS_STATUS_ID=?,WHETHER_ADEQUATE=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SCHEME_SLNO=? and DS_NO=?";  
                                
                                    try
                                             {
                                                 ps5=connection.prepareStatement(sqlDS);
                                                 ps5.setInt(1,DS_LENGTH_KM);
                                                 ps5.setInt(2,DS_PIPE_TYPE_ID);
                                                 ps5.setInt(3,SIZE_IN_MM);
                                                 ps5.setInt(4,DS_STATUS_ID);
                                                 ps5.setString(5,DS_WHETHER_ADEQUATE);

                                                 ps5.setInt(6,District_Code);
                                                 ps5.setInt(7,Block_Code);
                                                 ps5.setInt(8,Panchayat_Code);
                                                 ps5.setInt(9,Habitation_Code);
                                                 ps5.setInt(10,SchemeNumber);
                                                 ps5.setInt(11,DS_NO);
                                                 ps5.executeUpdate();        
                                               xml=xml+"<flag>success</flag>";
                                               ps5.close();
                                             }
                                             catch(Exception e)
                                             {        
                                                System.out.println("exception in the update DS GETTING VALUES PS1"+ e);
                                               
                                             }
                                
                              }
                              catch(Exception ae)
                              {
                              System.out.println("error in the Distribution system tab update" + ae);
                              xml=xml+"<flag>failure</flag>";
                              }
                     xml=xml+"</response>";
                 }
                 
      
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
           
           
    }
}