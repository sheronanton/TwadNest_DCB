package Servlets.PMS.PMS1.HabitationSurvey.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class New_Habit_UpdateServlet extends HttpServlet
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

                  //        ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
                    
                    
         /* declaration of variables for the Population tab */
                int District_Code=0;
                int Block_Code=0;
                int Panchayat_Code=0;
                int Habitation_Code=0;
                int CensusYear=0;
                int TotalPopulation=0;
                int TotalPopulationSC=0;
                int TotalPopulationST=0;
                int TotalHouse=0;
                int PermanentHouse=0;
                int ThatchedHouse=0;
                
          /* Declaration of variables for LPCD tab     */
          
                int MaxOverall=0;
                int MinOverall=0;
                int MaxHP=0;
                int MinHP=0;
                int MaxPP=0;
                int MinPP=0;
            
           /* Declaration of variabled for Office tab */
           
                int NOON_MEAL_CENTRES=0;
                int NOON_MEAL_CENTERS_WS=0;
                int PANCHAYAT_OFFICE=0;
                int PANCHAYAT_OFFICE_WS=0;
                int MARKET_PLACES=0;
                int MARKET_PLACES_WS=0;
                
                int ANGANWADIS=0;
                int ANGANWADIS_WS=0;
                int POLICE_STATIONS=0;
                int POLICE_STATIONS_WS=0;
                int TALUK_OFFICE=0;
                int TALUK_OFFICE_WS=0;
                
                int EB_OFFICE=0;
                int EB_OFFICE_WS=0;
                int BANKS=0;
                int BANKS_WS=0;
                int VERTINARY_HOSPITALS=0;
                int VERTINARY_HOSPITALS_WS=0;
                
                int OTHER_OFFICES=0;
                int OTHER_OFFICES_WS=0;
                int HOSPITALS=0;
                int HOSPITALS_WS=0;
                int OFFICES=0;
                int OFFICES_WS=0;
                
                int SHOPS=0;
                int SHOPS_WS=0;
                
       /* Declaration for School Tab */ 
        
                int GOVT_ELEMENTARY_SCHOOLS=0;
                int GOVT_ELEMENTARY_STRENGTH=0;
                int GOVT_ELEMENTARY_SCHOOLS_WS=0;
                
                int GOVT_MIDDLE_SCHOOLS=0;
                int GOVT_MIDDLE_STRENGTH=0;
                int GOVT_MIDDLE_SCHOOLS_WS=0;
                
                int GOVT_HIGH_SCHOOLS=0;
                int GOVT_HIGH_STRENGTH=0;
                int GOVT_HIGH_SCHOOLS_WS=0;
                
                int GOVT_HS_SCHOOLS=0;
                int GOVT_HS_STRENGTH=0;
                int GOVT_HS_SCHOOLS_WS=0;
                
                int PVT_ELEMENTARY_SCHOOLS=0;
                int PVT_ELEMENTARY_STRENGTH=0;
                int PVT_ELEMENTARY_SCHOOLS_WS=0;
                
                int PVT_MIDDLE_SCHOOLS=0;
                int PVT_MIDDLE_STRENGTH=0;
                int PVT_MIDDLE_SCHOOLS_WS=0;
                
                int PVT_HIGH_SCHOOLS=0;
                int PVT_HIGH_STRENGTH=0;
                int PVT_HIGH_SCHOOLS_WS=0;
            
                int PVT_HS_SCHOOLS=0;
                int PVT_HS_STRENGTH=0;
                int PVT_HS_SCHOOLS_WS=0;
                
                String VWSC_FORMED="";
                String USER_GROUP_FORMED="";
                
             /* Declaration for Cattle tab */
             
                int TOTAL_NO_CATTLE=0;
                int DAILY_REQMT_WS=0;
                int EXISTING_WS_KLD=0;
                
             /* Declaration for Additional particulars Tab */
             
                 int NO_SEPTIC_TANKS=0;
                 int NO_DRY_LATRINES=0;
                 int NO_LCS_LATRINES=0;   
                 int NO_COMM_LATRINES_USE=0;
                 int NO_COMM_LATRINES_DEFECT=0;
                 int NO_SULLAGE_DRAINAGE=0;
                 int NO_HOMES_SULLAGE_DRAINAGE=0;
                 
        /* Declaration for Habitation Tab */
        
                    int NEIGHBOUR_HABIT_DISTANCE=0;
                    String COVERED_ADDL_VILLAGE="";
                    String NAME_ADDL_VILLAGE="";   
                    int RIVER_BASIN_CODE=0;
                    String HABITATION_OLD_NEW="";
                    int TOP_SOIL_CODE=0;
                    int SUB_STRATA_SOIL_CODE=0;
                    int HABITATION_GROUP_CODE=0;
                    
        /* Declaration of variables for others tab     */
               
                     int WATER_SOURCE_SINO=0;
                     int WATER_SOURCE_TYPE_ID=0;
                     int NO_OF_SOURCES=0;
                     int NO_OF_SOURCES_IN_USE=0;
                     int NO_OF_MONTHS_WATER_AVAILABLE=0;

                 
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
                         CensusYear = Integer.parseInt(request.getParameter("CensusYear"));
                     }
                     catch(Exception e)
                     { 
                         System.out.println("in getting values in from the first tab**** "+ e);
                     }
                     
                // This is the update for the population first tab
                
                
          if(strCommand.equals("PopulationUpdate"))
          {
             xml="<response><command>PopulationUpdate</command>";
                        try
                       {
                           //getting population tab values from script
                         
                         TotalPopulation =Integer.parseInt(request.getParameter("TotalPopulation"));
                         TotalPopulationSC =Integer.parseInt(request.getParameter("TotalPopulationSC"));
                         TotalPopulationST =Integer.parseInt(request.getParameter("TotalPopulationST"));
                         
                         TotalHouse = Integer.parseInt(request.getParameter("TotalHouse"));
                         PermanentHouse =Integer.parseInt(request.getParameter("PermanentHouse"));
                         ThatchedHouse =Integer.parseInt(request.getParameter("ThatchedHouse"));
                           
                         String sqlPopulation = "UPDATE HBS_MST_POPULATION SET POPULATION_TOTAL=?,POPULATION_SC=?,POPULATION_ST=?,HOUSES_TOTAL=?,HOUSES_PERMANENT=?,HOUSES_THATCHED=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and CENSUS_YEAR=?";  
                         
                             try
                                      {
                                        ps=connection.prepareStatement(sqlPopulation);
                                          ps.setInt(1,TotalPopulation);
                                          ps.setInt(2,TotalPopulationSC);
                                          ps.setInt(3,TotalPopulationST);
                                          ps.setInt(4,TotalHouse);
                                          ps.setInt(5,PermanentHouse);
                                          ps.setInt(6,ThatchedHouse);

                                          ps.setInt(7,District_Code);
                                          ps.setInt(8,Block_Code);
                                          ps.setInt(9,Panchayat_Code);
                                          ps.setInt(10,Habitation_Code);
                                          ps.setInt(11,CensusYear);
                                             ps.executeUpdate();        
                                        xml=xml+"<flag>success</flag>";
                                        ps.close();
                                      }
                                      catch(Exception e)
                                      {        
                                         System.out.println("exception in the update POPULATION SETTING VALUES PS"+ e);
                                        
                                      }
                         
                       }
                       catch(Exception ae)
                       {
                       System.out.println("error in the population tab update" + ae);
                       xml=xml+"<flag>failure</flag>";
                       }
              xml=xml+"</response>";
          }
          
          //This update is for the second LPCD tab
          
          else if(strCommand.equals("LPCDUpdate"))
           {
              xml="<response><command>LPCDUpdate</command>";
                         try
                        {
                            //getting population tab values from script
                          MaxOverall =Integer.parseInt(request.getParameter("MaxOverall"));
                          MinOverall =Integer.parseInt(request.getParameter("MinOverall"));
                          MaxHP =Integer.parseInt(request.getParameter("MaxHP"));
                          
                          MinHP = Integer.parseInt(request.getParameter("MinHP"));
                          MaxPP =Integer.parseInt(request.getParameter("MaxPP"));
                          MinPP =Integer.parseInt(request.getParameter("MinPP"));
                            
                          String sqlLPCD = "UPDATE HBS_MST_LPCD SET MAX_LPCD_OVERALL=?,MIN_LPCD_OVERALL=?,MAX_LPCD_HP=?,MIN_LPCD_HP=?,MAX_LPCD_PP=?,MIN_LPCD_PP=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SURVEY_YEAR=?";  
                          
                              try
                                       {
                                         ps1=connection.prepareStatement(sqlLPCD);
                                           ps1.setInt(1,MaxOverall);
                                           ps1.setInt(2,MinOverall);
                                           ps1.setInt(3,MaxHP);
                                           ps1.setInt(4,MinHP);
                                           ps1.setInt(5,MaxPP);
                                           ps1.setInt(6,MinPP);

                                           ps1.setInt(7,District_Code);
                                           ps1.setInt(8,Block_Code);
                                           ps1.setInt(9,Panchayat_Code);
                                           ps1.setInt(10,Habitation_Code);
                                           ps1.setInt(11,CensusYear);
                                              ps1.executeUpdate();        
                                         xml=xml+"<flag>success</flag>";
                                         ps1.close();
                                       }
                                       catch(Exception e)
                                       {        
                                          System.out.println("exception in the update LPCD GETTING VALUES PS1"+ e);
                                         
                                       }
                          
                        }
                        catch(Exception ae)
                        {
                        System.out.println("error in the LPCD tab update" + ae);
                        xml=xml+"<flag>failure</flag>";
                        }
               xml=xml+"</response>";
           }
          
          //This is to update office details tab
          
          else if(strCommand.equals("OfficeUpdate"))
           {
              xml="<response><command>OfficeUpdate</command>";
                         try
                        {
                            //getting population tab values from script
                          NOON_MEAL_CENTRES =Integer.parseInt(request.getParameter("NOON_MEAL_CENTRES"));
                          NOON_MEAL_CENTERS_WS =Integer.parseInt(request.getParameter("NOON_MEAL_CENTERS_WS"));
                          PANCHAYAT_OFFICE =Integer.parseInt(request.getParameter("PANCHAYAT_OFFICE"));
                          PANCHAYAT_OFFICE_WS = Integer.parseInt(request.getParameter("PANCHAYAT_OFFICE_WS"));
                          MARKET_PLACES =Integer.parseInt(request.getParameter("MARKET_PLACES"));
                          MARKET_PLACES_WS =Integer.parseInt(request.getParameter("MARKET_PLACES_WS"));
                            
                            ANGANWADIS =Integer.parseInt(request.getParameter("ANGANWADIS"));
                            ANGANWADIS_WS =Integer.parseInt(request.getParameter("ANGANWADIS_WS"));
                            POLICE_STATIONS =Integer.parseInt(request.getParameter("POLICE_STATIONS"));
                            POLICE_STATIONS_WS = Integer.parseInt(request.getParameter("POLICE_STATIONS_WS"));
                            TALUK_OFFICE =Integer.parseInt(request.getParameter("TALUK_OFFICE"));
                            TALUK_OFFICE_WS =Integer.parseInt(request.getParameter("TALUK_OFFICE_WS"));

                            EB_OFFICE =Integer.parseInt(request.getParameter("EB_OFFICE"));
                            EB_OFFICE_WS =Integer.parseInt(request.getParameter("EB_OFFICE_WS"));
                            BANKS =Integer.parseInt(request.getParameter("BANKS"));
                            BANKS_WS = Integer.parseInt(request.getParameter("BANKS_WS"));
                            VERTINARY_HOSPITALS =Integer.parseInt(request.getParameter("VERTINARY_HOSPITALS"));
                            VERTINARY_HOSPITALS_WS =Integer.parseInt(request.getParameter("VERTINARY_HOSPITALS_WS"));

                            OTHER_OFFICES =Integer.parseInt(request.getParameter("OTHER_OFFICES"));
                            OTHER_OFFICES_WS =Integer.parseInt(request.getParameter("OTHER_OFFICES_WS"));
                            HOSPITALS =Integer.parseInt(request.getParameter("HOSPITALS"));
                            HOSPITALS_WS = Integer.parseInt(request.getParameter("HOSPITALS_WS"));
                            OFFICES =Integer.parseInt(request.getParameter("OFFICES"));
                            OFFICES_WS =Integer.parseInt(request.getParameter("OFFICES_WS"));

                            SHOPS =Integer.parseInt(request.getParameter("SHOPS"));
                            SHOPS_WS =Integer.parseInt(request.getParameter("SHOPS_WS"));
                            VWSC_FORMED =request.getParameter("VWSC_FORMED");
                            USER_GROUP_FORMED = request.getParameter("USER_GROUP_FORMED");
                          
  
                          String sqlOffice = "UPDATE HBS_MST_INSTITUTIONS SET NO_NOON_MEAL_CENTRES=?,NO_NOON_MEAL_CENTERS_WS=?,NO_PANCHAYAT_OFFICE=?,NO_PANCHAYAT_OFFICE_WS=?,NO_MARKET_PLACES=?,NO_MARKET_PLACES_WS=?,NO_ANGANWADIS=?,NO_ANGANWADIS_WS=?,NO_POLICE_STATIONS=?,NO_POLICE_STATIONS_WS=?,NO_TALUK_OFFICE=?,NO_TALUK_OFFICE_WS=?,NO_EB_OFFICE=?,NO_EB_OFFICE_WS=?,NO_BANKS=?,NO_BANKS_WS=?,NO_VERTINARY_HOSPITALS=?,NO_VERTINARY_HOSPITALS_WS=?,NO_OTHER_OFFICES=?,NO_OTHER_OFFICES_WS=?,NO_HOSPITALS=?,NO_HOSPITALS_WS=?,NO_OFFICES=?,NO_OFFICES_WS=?,NO_SHOPS=?,NO_SHOPS_WS=?,VWSC_FORMED=?,USER_GROUP_FORMED=?  WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SURVEY_YEAR=?";  
                          
                              try
                                       {
                                         ps2=connection.prepareStatement(sqlOffice);
                                           ps2.setInt(1,NOON_MEAL_CENTRES);
                                           ps2.setInt(2,NOON_MEAL_CENTERS_WS);
                                           ps2.setInt(3,PANCHAYAT_OFFICE);
                                           ps2.setInt(4,PANCHAYAT_OFFICE_WS);
                                           ps2.setInt(5,MARKET_PLACES);
                                           ps2.setInt(6,MARKET_PLACES_WS);
                                           
                                           ps2.setInt(7,ANGANWADIS);
                                           ps2.setInt(8,ANGANWADIS_WS);
                                           ps2.setInt(9,POLICE_STATIONS);
                                           ps2.setInt(10,POLICE_STATIONS_WS);
                                           ps2.setInt(11,TALUK_OFFICE);
                                           ps2.setInt(12,TALUK_OFFICE_WS);
                                           
                                           ps2.setInt(13,EB_OFFICE);
                                           ps2.setInt(14,EB_OFFICE_WS);
                                           ps2.setInt(15,BANKS);
                                           ps2.setInt(16,BANKS_WS);
                                           ps2.setInt(17,VERTINARY_HOSPITALS);
                                           ps2.setInt(18,VERTINARY_HOSPITALS_WS);
                                           
                                           ps2.setInt(19,OTHER_OFFICES);
                                           ps2.setInt(20,OTHER_OFFICES_WS);
                                           ps2.setInt(21,HOSPITALS);
                                           ps2.setInt(22,HOSPITALS_WS);
                                           ps2.setInt(23,OFFICES);
                                           ps2.setInt(24,OFFICES_WS);
                                           
                                           ps2.setInt(25,SHOPS);
                                           ps2.setInt(26,SHOPS_WS);
                                           ps2.setString(27,VWSC_FORMED);
                                           ps2.setString(28,USER_GROUP_FORMED);
                                          
                                           ps2.setInt(29,District_Code);
                                           ps2.setInt(30,Block_Code);
                                           ps2.setInt(31,Panchayat_Code);
                                           ps2.setInt(32,Habitation_Code);
                                           ps2.setInt(33,CensusYear);
                                           
                                          ps2.executeUpdate();        
                                         xml=xml+"<flag>success</flag>";
                                         ps2.close();
                                       }
                                       catch(Exception e)
                                       {        
                                          System.out.println("exception in the update Office GETTING VALUES PS1"+ e);
                                         
                                       }
                          
                        }
                        catch(Exception ae)
                        {
                        System.out.println("error in the Office tab update" + ae);
                        xml=xml+"<flag>failure</flag>";
                        }
               xml=xml+"</response>";
           }
          
          
           
            // To update School Tab
            
             else if(strCommand.equals("SchoolUpdate"))
             {
                xml="<response><command>SchoolUpdate</command>";
                           try
                          {
                              //getting population tab values from script
                            GOVT_ELEMENTARY_SCHOOLS =Integer.parseInt(request.getParameter("GOVT_ELEMENTARY_SCHOOLS"));
                            GOVT_ELEMENTARY_STRENGTH =Integer.parseInt(request.getParameter("GOVT_ELEMENTARY_STRENGTH"));
                            GOVT_ELEMENTARY_SCHOOLS_WS =Integer.parseInt(request.getParameter("GOVT_ELEMENTARY_SCHOOLS_WS"));
                           
                            GOVT_MIDDLE_SCHOOLS = Integer.parseInt(request.getParameter("GOVT_MIDDLE_SCHOOLS"));
                            GOVT_MIDDLE_STRENGTH =Integer.parseInt(request.getParameter("GOVT_MIDDLE_STRENGTH"));
                            GOVT_MIDDLE_SCHOOLS_WS =Integer.parseInt(request.getParameter("GOVT_MIDDLE_SCHOOLS_WS"));
                              
                              GOVT_HIGH_SCHOOLS =Integer.parseInt(request.getParameter("GOVT_HIGH_SCHOOLS"));
                              GOVT_HIGH_STRENGTH =Integer.parseInt(request.getParameter("GOVT_HIGH_STRENGTH"));
                              GOVT_HIGH_SCHOOLS_WS =Integer.parseInt(request.getParameter("GOVT_HIGH_SCHOOLS_WS"));
                              
                              GOVT_HS_SCHOOLS = Integer.parseInt(request.getParameter("GOVT_HS_SCHOOLS"));
                              GOVT_HS_STRENGTH =Integer.parseInt(request.getParameter("GOVT_HS_STRENGTH"));
                              GOVT_HS_SCHOOLS_WS =Integer.parseInt(request.getParameter("GOVT_HS_SCHOOLS_WS"));

                              PVT_ELEMENTARY_SCHOOLS =Integer.parseInt(request.getParameter("PVT_ELEMENTARY_SCHOOLS"));
                              PVT_ELEMENTARY_STRENGTH =Integer.parseInt(request.getParameter("PVT_ELEMENTARY_STRENGTH"));
                              PVT_ELEMENTARY_SCHOOLS_WS =Integer.parseInt(request.getParameter("PVT_ELEMENTARY_SCHOOLS_WS"));
                           
                              PVT_MIDDLE_SCHOOLS =Integer.parseInt(request.getParameter("PVT_MIDDLE_SCHOOLS"));
                              PVT_MIDDLE_STRENGTH =Integer.parseInt(request.getParameter("PVT_MIDDLE_STRENGTH"));
                              PVT_MIDDLE_SCHOOLS_WS =Integer.parseInt(request.getParameter("PVT_MIDDLE_SCHOOLS_WS"));

                              PVT_HIGH_SCHOOLS = Integer.parseInt(request.getParameter("PVT_HIGH_SCHOOLS"));
                              PVT_HIGH_STRENGTH =Integer.parseInt(request.getParameter("PVT_HIGH_STRENGTH"));
                              PVT_HIGH_SCHOOLS_WS =Integer.parseInt(request.getParameter("PVT_HIGH_SCHOOLS_WS"));
                              
                              
                              PVT_HS_SCHOOLS = Integer.parseInt(request.getParameter("PVT_HS_SCHOOLS"));
                              PVT_HS_STRENGTH =Integer.parseInt(request.getParameter("PVT_HS_STRENGTH"));
                              PVT_HS_SCHOOLS_WS =Integer.parseInt(request.getParameter("PVT_HS_SCHOOLS_WS"));

                            
                            String sqlSchool = "UPDATE HBS_MST_SCHOOLS SET NO_GOVT_ELEMENTARY_SCHOOLS=?,NO_GOVT_ELEMENTARY_STRENGTH=?,NO_GOVT_ELEMENTARY_SCHOOLS_WS=?,NO_GOVT_MIDDLE_SCHOOLS=?,NO_GOVT_MIDDLE_STRENGTH=?,NO_GOVT_MIDDLE_SCHOOLS_WS=?,NO_GOVT_HIGH_SCHOOLS=?,NO_GOVT_HIGH_STRENGTH=?,NO_GOVT_HIGH_SCHOOLS_WS=?,NO_GOVT_HS_SCHOOLS=?,NO_GOVT_HS_STRENGTH=?,NO_GOVT_HS_SCHOOLS_WS=?,NO_PVT_ELEMENTARY_SCHOOLS=?,NO_PVT_ELEMENTARY_STRENGTH=?,NO_PVT_ELEMENTARY_SCHOOLS_WS=?,NO_PVT_MIDDLE_SCHOOLS=?,NO_PVT_MIDDLE_STRENGTH=?,NO_PVT_MIDDLE_SCHOOLS_WS=?,NO_PVT_HIGH_SCHOOLS=?,NO_PVT_HIGH_STRENGTH=?,NO_PVT_HIGH_SCHOOLS_WS=?,NO_PVT_HS_SCHOOLS=?,NO_PVT_HS_STRENGTH=?,NO_PVT_HS_SCHOOLS_WS=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SURVEY_YEAR=?";  
                            
                                try
                                         {
                                             ps3=connection.prepareStatement(sqlSchool);
                                             ps3.setInt(1,GOVT_ELEMENTARY_SCHOOLS);
                                             ps3.setInt(2,GOVT_ELEMENTARY_STRENGTH);
                                             ps3.setInt(3,GOVT_ELEMENTARY_SCHOOLS_WS);
                                            
                                             ps3.setInt(4,GOVT_MIDDLE_SCHOOLS);
                                             ps3.setInt(5,GOVT_MIDDLE_STRENGTH);
                                             ps3.setInt(6,GOVT_MIDDLE_SCHOOLS_WS);
                                             
                                             ps3.setInt(7,GOVT_HIGH_SCHOOLS);
                                             ps3.setInt(8,GOVT_HIGH_STRENGTH);
                                             ps3.setInt(9,GOVT_HIGH_SCHOOLS_WS);
                                            
                                             ps3.setInt(10,GOVT_HS_SCHOOLS);
                                             ps3.setInt(11,GOVT_HS_STRENGTH);
                                             ps3.setInt(12,GOVT_HS_SCHOOLS_WS);
                                             
                                             ps3.setInt(13,PVT_ELEMENTARY_SCHOOLS);
                                             ps3.setInt(14,PVT_ELEMENTARY_STRENGTH);
                                             ps3.setInt(15,PVT_ELEMENTARY_SCHOOLS_WS);
                                            
                                             ps3.setInt(16,PVT_MIDDLE_SCHOOLS);
                                             ps3.setInt(17,PVT_MIDDLE_STRENGTH);
                                             ps3.setInt(18,PVT_MIDDLE_SCHOOLS_WS);
                                             
                                             ps3.setInt(19,PVT_HIGH_SCHOOLS);
                                             ps3.setInt(20,PVT_HIGH_STRENGTH);
                                             ps3.setInt(21,PVT_HIGH_SCHOOLS_WS);
                                            
                                             ps3.setInt(22,PVT_HS_SCHOOLS);
                                             ps3.setInt(23,PVT_HS_STRENGTH);
                                             ps3.setInt(24,PVT_HS_SCHOOLS_WS);
                                           
                                             ps3.setInt(25,District_Code);
                                             ps3.setInt(26,Block_Code);
                                             ps3.setInt(27,Panchayat_Code);
                                             ps3.setInt(28,Habitation_Code);
                                             ps3.setInt(29,CensusYear);
                                             
                                            ps3.executeUpdate();        
                                           xml=xml+"<flag>success</flag>";
                                           ps3.close();
                                         }
                                         catch(Exception e)
                                         {        
                                            System.out.println("exception in the update school GETTING VALUES PS1"+ e);
                                           
                                         }
                            
                          }
                          catch(Exception ae)
                          {
                          System.out.println("error in the schoole tab update" + ae);
                          xml=xml+"<flag>failure</flag>";
                          }
                 xml=xml+"</response>";
             }
             
             //This is for Cattle Tab
              else if(strCommand.equals("CattleUpdate"))
               {
                  xml="<response><command>CattleUpdate</command>";
                             try
                            {
                                //getting population tab values from script
                              TOTAL_NO_CATTLE =Integer.parseInt(request.getParameter("TOTAL_NO_CATTLE"));
                              DAILY_REQMT_WS =Integer.parseInt(request.getParameter("DAILY_REQMT_WS"));
                              EXISTING_WS_KLD =Integer.parseInt(request.getParameter("EXISTING_WS_KLD"));
                                
                              String sqlCattle = "UPDATE HBS_MST_CATTLE_DATA SET TOTAL_NO_CATTLE=?,DAILY_REQMT_WS=?,EXISTING_WS_KLD=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SURVEY_YEAR=?";  
                              
                                  try
                                           {
                                             ps4=connection.prepareStatement(sqlCattle);
                                               ps4.setInt(1,TOTAL_NO_CATTLE);
                                               ps4.setInt(2,DAILY_REQMT_WS);
                                               ps4.setInt(3,EXISTING_WS_KLD);

                                               ps4.setInt(4,District_Code);
                                               ps4.setInt(5,Block_Code);
                                               ps4.setInt(6,Panchayat_Code);
                                               ps4.setInt(7,Habitation_Code);
                                               ps4.setInt(8,CensusYear);
                                                  ps4.executeUpdate();        
                                             xml=xml+"<flag>success</flag>";
                                             ps4.close();
                                           }
                                           catch(Exception e)
                                           {        
                                              System.out.println("exception in the update cattle GETTING VALUES PS1"+ e);
                                             
                                           }
                              
                            }
                            catch(Exception ae)
                            {
                            System.out.println("error in the cattle tab update" + ae);
                            xml=xml+"<flag>failure</flag>";
                            }
                   xml=xml+"</response>";
               }
               
               //This is for Additional Tab
                else if(strCommand.equals("AdditionalUpdate"))
                 {
                    xml="<response><command>AdditionalUpdate</command>";
                               try
                              {
                                  //getting population tab values from script
                                  NO_SEPTIC_TANKS =Integer.parseInt(request.getParameter("NO_SEPTIC_TANKS"));
                                  NO_DRY_LATRINES =Integer.parseInt(request.getParameter("NO_DRY_LATRINES"));
                                  NO_LCS_LATRINES =Integer.parseInt(request.getParameter("NO_LCS_LATRINES"));
                                  NO_COMM_LATRINES_USE =Integer.parseInt(request.getParameter("NO_COMM_LATRINES_USE"));
                                  NO_COMM_LATRINES_DEFECT =Integer.parseInt(request.getParameter("NO_COMM_LATRINES_DEFECT"));
                                  NO_SULLAGE_DRAINAGE =Integer.parseInt(request.getParameter("NO_SULLAGE_DRAINAGE"));
                                  NO_HOMES_SULLAGE_DRAINAGE =Integer.parseInt(request.getParameter("NO_HOMES_SULLAGE_DRAINAGE"));
                                  
                                String sqlCattle = "UPDATE HBS_MST_ADDITIONAL SET NO_SEPTIC_TANKS=?,NO_DRY_LATRINES=?,NO_LCS_LATRINES=?,NO_COMM_LATRINES_USE=?,NO_COMM_LATRINES_DEFECT=?,NO_SULLAGE_DRAINAGE=?,NO_HOMES_SULLAGE_DRAINAGE=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SURVEY_YEAR=?";  
                                
                                    try
                                             {
                                                 ps5=connection.prepareStatement(sqlCattle);
                                                 ps5.setInt(1,NO_SEPTIC_TANKS);
                                                 ps5.setInt(2,NO_DRY_LATRINES);
                                                 ps5.setInt(3,NO_LCS_LATRINES);
                                                 ps5.setInt(4,NO_COMM_LATRINES_USE);
                                                 ps5.setInt(5,NO_COMM_LATRINES_DEFECT);
                                                 ps5.setInt(6,NO_SULLAGE_DRAINAGE);
                                                 ps5.setInt(7,NO_HOMES_SULLAGE_DRAINAGE);
                                                 
                                                 ps5.setInt(8,District_Code);
                                                 ps5.setInt(9,Block_Code);
                                                 ps5.setInt(10,Panchayat_Code);
                                                 ps5.setInt(11,Habitation_Code);
                                                 ps5.setInt(12,CensusYear);
                                                 ps5.executeUpdate();        
                                               xml=xml+"<flag>success</flag>";
                                               ps5.close();
                                             }
                                             catch(Exception e)
                                             {        
                                                System.out.println("exception in the update additional GETTING VALUES PS1"+ e);
                                               
                                             }
                                
                              }
                              catch(Exception ae)
                              {
                              System.out.println("error in the additional tab update" + ae);
                              xml=xml+"<flag>failure</flag>";
                              }
                     xml=xml+"</response>";
                 }
                 
                 
        //This is for Habitation Tab
         else if(strCommand.equals("HabitationUpdate"))
          {
             xml="<response><command>HabitationUpdate</command>";
                        try
                       {
                           //getting population tab values from script
                           NEIGHBOUR_HABIT_DISTANCE =Integer.parseInt(request.getParameter("NEIGHBOUR_HABIT_DISTANCE"));
                           COVERED_ADDL_VILLAGE =request.getParameter("COVERED_ADDL_VILLAGE");
                           NAME_ADDL_VILLAGE =request.getParameter("NAME_ADDL_VILLAGE");
                           RIVER_BASIN_CODE =Integer.parseInt(request.getParameter("RIVER_BASIN_CODE"));
                           HABITATION_OLD_NEW =request.getParameter("HABITATION_OLD_NEW");
                           TOP_SOIL_CODE =Integer.parseInt(request.getParameter("TOP_SOIL_CODE"));
                           SUB_STRATA_SOIL_CODE =Integer.parseInt(request.getParameter("SUB_STRATA_SOIL_CODE"));
                           HABITATION_GROUP_CODE =Integer.parseInt(request.getParameter("HABITATION_GROUP_CODE"));
                           
                         String sqlCattle = "UPDATE HBS_MST_HABITATION_TYPE SET NEIGHBOUR_HABIT_DISTANCE=?,COVERED_ADDL_VILLAGE=?,NAME_ADDL_VILLAGE=?,RIVER_BASIN_CODE=?,HABITATION_OLD_NEW=?,TOP_SOIL_CODE=?,SUB_STRATA_SOIL_CODE=?,HABITATION_GROUP_CODE=? WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SURVEY_YEAR=?";  
                         
                             try
                                      {
                                          ps5=connection.prepareStatement(sqlCattle);
                                          ps5.setInt(1,NEIGHBOUR_HABIT_DISTANCE);
                                          ps5.setString(2,COVERED_ADDL_VILLAGE);
                                          ps5.setString(3,NAME_ADDL_VILLAGE);
                                          ps5.setInt(4,RIVER_BASIN_CODE);
                                          ps5.setString(5,HABITATION_OLD_NEW);
                                          ps5.setInt(6,TOP_SOIL_CODE);
                                          ps5.setInt(7,SUB_STRATA_SOIL_CODE);
                                          ps5.setInt(8,HABITATION_GROUP_CODE);
                                          
                                          ps5.setInt(9,District_Code);
                                          ps5.setInt(10,Block_Code);
                                          ps5.setInt(11,Panchayat_Code);
                                          ps5.setInt(12,Habitation_Code);
                                          ps5.setInt(13,CensusYear);
                                          ps5.executeUpdate();        
                                        xml=xml+"<flag>success</flag>";
                                        ps5.close();
                                      }
                                      catch(Exception e)
                                      {        
                                         System.out.println("exception in the update Habitation GETTING VALUES PS1"+ e);
                                        
                                      }
                         
                       }
                       catch(Exception ae)
                       {
                       System.out.println("error in the Habitation tab update" + ae);
                       xml=xml+"<flag>failure</flag>";
                       }
              xml=xml+"</response>";
          }
          
        //This update is for the Others tab
                
                else if(strCommand.equals("OthersUpdate"))
                 {
                    xml="<response><command>OthersUpdate</command>";
                               try
                              {
                                  //getting population tab values from script
                                WATER_SOURCE_SINO =Integer.parseInt(request.getParameter("WATER_SOURCE_SINO"));
                                WATER_SOURCE_TYPE_ID =Integer.parseInt(request.getParameter("WATER_SOURCE_TYPE_ID"));
                                NO_OF_SOURCES =Integer.parseInt(request.getParameter("NO_OF_SOURCES"));
                                
                                NO_OF_SOURCES_IN_USE = Integer.parseInt(request.getParameter("NO_OF_SOURCES_IN_USE"));
                                NO_OF_MONTHS_WATER_AVAILABLE =Integer.parseInt(request.getParameter("NO_OF_MONTHS_WATER_AVAILABLE"));
                                  
                                String sqlOthers = "UPDATE HBS_MST_WATER_SOURCES SET WATER_SOURCE_SINO=?,WATER_SOURCE_TYPE_ID=?,NO_OF_SOURCES=?,NO_OF_SOURCES_IN_USE=?,NO_OF_MONTHS_WATER_AVAILABLE=?  WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SURVEY_YEAR=?";  
                                
                                    try
                                             {
                                               ps6=connection.prepareStatement(sqlOthers);
                                                 ps6.setInt(1,WATER_SOURCE_SINO);
                                                 ps6.setInt(2,WATER_SOURCE_TYPE_ID);
                                                 ps6.setInt(3,NO_OF_SOURCES);
                                                 ps6.setInt(4,NO_OF_SOURCES_IN_USE);
                                                 ps6.setInt(5,NO_OF_MONTHS_WATER_AVAILABLE);

                                                 ps6.setInt(6,District_Code);
                                                 ps6.setInt(7,Block_Code);
                                                 ps6.setInt(8,Panchayat_Code);
                                                 ps6.setInt(9,Habitation_Code);
                                                 ps6.setInt(10,CensusYear);
                                                    ps6.executeUpdate();        
                                               xml=xml+"<flag>success</flag>";
                                               ps6.close();
                                             }
                                             catch(Exception e)
                                             {        
                                                System.out.println("exception in the update Others GETTING VALUES PS1"+ e);
                                               
                                             }
                                
                              }
                              catch(Exception ae)
                              {
                              System.out.println("error in the Others tab update" + ae);
                              xml=xml+"<flag>failure</flag>";
                              }
                     xml=xml+"</response>";
                 }

        //This is to delete from the Others tab
                
                else if(strCommand.equals("OthersDelete"))
                 {
                    xml="<response><command>OthersDelete</command>";
                               try
                              {
                                  //getting population tab values from script
                                WATER_SOURCE_SINO =Integer.parseInt(request.getParameter("WATER_SOURCE_SINO"));

                                String sqlOthersDel = "Delete from HBS_MST_WATER_SOURCES WHERE DISTRICT_CODE=? and BLOCK_CODE=? and PANCHAYAT_CODE=? and HABITATION_CODE=? and SURVEY_YEAR=? and WATER_SOURCE_SINO=?";  
                                
                                    try
                                             {
                                                ps7=connection.prepareStatement(sqlOthersDel);
                                                
                                                 ps7.setInt(1,District_Code);
                                                 ps7.setInt(2,Block_Code);
                                                 ps7.setInt(3,Panchayat_Code);
                                                 ps7.setInt(4,Habitation_Code);
                                                 ps7.setInt(5,CensusYear);
                                                 ps7.setInt(6,WATER_SOURCE_SINO);
                                                 ps7.executeUpdate();        
                                               xml=xml+"<flag>success</flag><WATER_SOURCE_SINO>" + WATER_SOURCE_SINO + "</WATER_SOURCE_SINO>";
                                               ps7.close();
                                             }
                                             catch(Exception e)
                                             {        
                                                System.out.println("exception in the update Others GETTING VALUES PS1"+ e);
                                               
                                             }
                                
                              }
                              catch(Exception ae)
                              {
                              System.out.println("error in the Others tab update" + ae);
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