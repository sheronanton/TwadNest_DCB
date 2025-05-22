package Servlets.PMS.PMS1.HabitationSurvey.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class New_Habit_LoadServlet extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private Connection connection=null;
    private Statement statement=null;
    private ResultSet results=null;
    private ResultSet results1=null;
    private ResultSet results2=null;
    private ResultSet results3=null;
    private ResultSet results4=null;
    private ResultSet results5=null;
    private ResultSet results6=null;
    private ResultSet results7=null;
    
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

                    //      ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
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
             System.out.println("Exception in openeing connection:"+e);
          }
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
               System.out.println("servlet called");
                String strCommand = ""; 
                String xml="";
               
                /* declaration of variables for the first tab */
                int District_Code=0;
                int Block_Code=0;
                int Panchayat_Code=0;
                int Habitation_Code=0;
                int CensusYear=0;
              
                
                  
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
                         CensusYear =Integer.parseInt(request.getParameter("CensusYear"));
                         
                     }
                     catch(Exception e)
                     { 
                         System.out.println("in getting values in from the first tab**** "+ e);
                     }
                     
                     
                     //to fetch values from the table using load command
                     
                       if(strCommand.equals("HabitLoad"))
                            {
                          xml="<response><command>HabitLoad</command>";
                             
                             //This is for population tab load
                             try
                            {
                             
                               String LoadPopulationtab="SELECT * FROM HBS_MST_POPULATION WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? and CENSUS_YEAR=?";
                              try
                             {
                                 ps=connection.prepareStatement(LoadPopulationtab);
                                 ps.setInt(1,District_Code);
                                 ps.setInt(2,Block_Code);
                                 ps.setInt(3,Panchayat_Code);
                                 ps.setInt(4,Habitation_Code);
                                 ps.setInt(5,CensusYear);
                                 results=ps.executeQuery();
                                 xml=xml+"<flag>success</flag>";
                             while(results.next())
                            { 
                                System.out.println("in result set of population load");
                                xml=xml+ "<CENSUS_YEAR>"+results.getInt("CENSUS_YEAR")+"</CENSUS_YEAR>";
                                
                                xml=xml+ "<POPULATION_TOTAL>"+results.getInt("POPULATION_TOTAL")+"</POPULATION_TOTAL>";
                                xml=xml+ "<POPULATION_SC>"+results.getInt("POPULATION_SC")+"</POPULATION_SC>";
                                xml=xml+ "<POPULATION_ST>"+results.getInt("POPULATION_ST")+"</POPULATION_ST>";
                                
                                xml=xml+ "<HOUSES_TOTAL>"+results.getInt("HOUSES_TOTAL")+"</HOUSES_TOTAL>";
                                xml=xml+ "<HOUSES_PERMANENT>"+results.getInt("HOUSES_PERMANENT")+"</HOUSES_PERMANENT>";
                                xml=xml+ "<HOUSES_THATCHED>"+results.getInt("HOUSES_THATCHED")+"</HOUSES_THATCHED>";
                                
                            }
                            
                             }catch(Exception ae)
                             {
                                System.out.println("Exception is:the Load of Panchayat" +ae);
                             }
                             
                             //This  is for LPCD tab load
                             try {
                                 
                                 String LoadLpcd= " SELECT * FROM HBS_MST_LPCD WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? and SURVEY_YEAR=?";
                                 ps1=connection.prepareStatement(LoadLpcd);
                                 ps1.setInt(1,District_Code);
                                 ps1.setInt(2,Block_Code);
                                 ps1.setInt(3,Panchayat_Code);
                                 ps1.setInt(4,Habitation_Code);
                                 ps1.setInt(5,CensusYear);
                                 results1=ps1.executeQuery();
                                 
                                 while(results1.next())
                                 {
                                    System.out.println("in result set of lpcd load");

                                    xml=xml+ "<MAX_LPCD_OVERALL>"+results1.getInt("MAX_LPCD_OVERALL")+"</MAX_LPCD_OVERALL>";
                                    xml=xml+ "<MIN_LPCD_OVERALL>"+results1.getInt("MIN_LPCD_OVERALL")+"</MIN_LPCD_OVERALL>";
                                    xml=xml+ "<MAX_LPCD_HP>"+results1.getInt("MAX_LPCD_HP")+"</MAX_LPCD_HP>";
                                    xml=xml+ "<MIN_LPCD_HP>"+results1.getInt("MIN_LPCD_HP")+"</MIN_LPCD_HP>";
                                    xml=xml+ "<MAX_LPCD_PP>"+results1.getInt("MAX_LPCD_PP")+"</MAX_LPCD_PP>";
                                    xml=xml+ "<MIN_LPCD_PP>"+results1.getInt("MIN_LPCD_PP")+"</MIN_LPCD_PP>";
                                    
                                 }
                                 
                             }
                             catch(Exception ee){System.out.println("Load of LPCD" + ee);}
                             
                              //This  is for Office tab load
                              try {
                                  
                                  String LoadOffice= " SELECT * FROM HBS_MST_INSTITUTIONS WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? and SURVEY_YEAR=?";
                                  ps2=connection.prepareStatement(LoadOffice);
                                  ps2.setInt(1,District_Code);
                                  ps2.setInt(2,Block_Code);
                                  ps2.setInt(3,Panchayat_Code);
                                  ps2.setInt(4,Habitation_Code);
                                  ps2.setInt(5,CensusYear);
                                  results2=ps2.executeQuery();
                                  
                                  while(results2.next())
                                  {
                                     System.out.println("in result set of office load");

                                     xml=xml+ "<NO_NOON_MEAL_CENTRES>"+results2.getInt("NO_NOON_MEAL_CENTRES")+"</NO_NOON_MEAL_CENTRES>";
                                     xml=xml+ "<NO_NOON_MEAL_CENTERS_WS>"+results2.getInt("NO_NOON_MEAL_CENTERS_WS")+"</NO_NOON_MEAL_CENTERS_WS>";
                                     xml=xml+ "<NO_PANCHAYAT_OFFICE>"+results2.getInt("NO_PANCHAYAT_OFFICE")+"</NO_PANCHAYAT_OFFICE>";
                                     xml=xml+ "<NO_PANCHAYAT_OFFICE_WS>"+results2.getInt("NO_PANCHAYAT_OFFICE_WS")+"</NO_PANCHAYAT_OFFICE_WS>";
                                     xml=xml+ "<NO_MARKET_PLACES>"+results2.getInt("NO_MARKET_PLACES")+"</NO_MARKET_PLACES>";
                                     
                                      xml=xml+ "<NO_MARKET_PLACES_WS>"+results2.getInt("NO_MARKET_PLACES_WS")+"</NO_MARKET_PLACES_WS>";
                                      xml=xml+ "<NO_ANGANWADIS>"+results2.getInt("NO_ANGANWADIS")+"</NO_ANGANWADIS>";
                                      xml=xml+ "<NO_ANGANWADIS_WS>"+results2.getInt("NO_ANGANWADIS_WS")+"</NO_ANGANWADIS_WS>";
                                      xml=xml+ "<NO_POLICE_STATIONS>"+results2.getInt("NO_POLICE_STATIONS")+"</NO_POLICE_STATIONS>";
                                      xml=xml+ "<NO_POLICE_STATIONS_WS>"+results2.getInt("NO_POLICE_STATIONS_WS")+"</NO_POLICE_STATIONS_WS>";

                                      xml=xml+ "<NO_TALUK_OFFICE>"+results2.getInt("NO_TALUK_OFFICE")+"</NO_TALUK_OFFICE>";
                                      xml=xml+ "<NO_TALUK_OFFICE_WS>"+results2.getInt("NO_TALUK_OFFICE_WS")+"</NO_TALUK_OFFICE_WS>";
                                      xml=xml+ "<NO_EB_OFFICE>"+results2.getInt("NO_EB_OFFICE")+"</NO_EB_OFFICE>";
                                      xml=xml+ "<NO_EB_OFFICE_WS>"+results2.getInt("NO_EB_OFFICE_WS")+"</NO_EB_OFFICE_WS>";
                                      xml=xml+ "<NO_BANKS>"+results2.getInt("NO_BANKS")+"</NO_BANKS>";

                                      xml=xml+ "<NO_BANKS_WS>"+results2.getInt("NO_BANKS_WS")+"</NO_BANKS_WS>";
                                      xml=xml+ "<NO_VERTINARY_HOSPITALS>"+results2.getInt("NO_VERTINARY_HOSPITALS")+"</NO_VERTINARY_HOSPITALS>";
                                      xml=xml+ "<NO_VERTINARY_HOSPITALS_WS>"+results2.getInt("NO_VERTINARY_HOSPITALS_WS")+"</NO_VERTINARY_HOSPITALS_WS>";
                                      xml=xml+ "<NO_OTHER_OFFICES>"+results2.getInt("NO_OTHER_OFFICES")+"</NO_OTHER_OFFICES>";
                                      xml=xml+ "<NO_OTHER_OFFICES_WS>"+results2.getInt("NO_OTHER_OFFICES_WS")+"</NO_OTHER_OFFICES_WS>";

                                      xml=xml+ "<NO_HOSPITALS>"+results2.getInt("NO_HOSPITALS")+"</NO_HOSPITALS>";
                                      xml=xml+ "<NO_HOSPITALS_WS>"+results2.getInt("NO_HOSPITALS_WS")+"</NO_HOSPITALS_WS>";
                                      xml=xml+ "<NO_OFFICES>"+results2.getInt("NO_OFFICES")+"</NO_OFFICES>";
                                      xml=xml+ "<NO_OFFICES_WS>"+results2.getInt("NO_OFFICES_WS")+"</NO_OFFICES_WS>";
                                                                          
                                      xml=xml+ "<NO_SHOPS>"+results2.getInt("NO_SHOPS")+"</NO_SHOPS>";
                                      xml=xml+ "<NO_SHOPS_WS>"+results2.getInt("NO_SHOPS_WS")+"</NO_SHOPS_WS>";
                                      xml=xml+ "<VWSC_FORMED>"+results2.getString("VWSC_FORMED")+"</VWSC_FORMED>";
                                      xml=xml+ "<USER_GROUP_FORMED>"+results2.getString("USER_GROUP_FORMED")+"</USER_GROUP_FORMED>";
                                     
                                  }
                                  
                              }
                              catch(Exception ee){System.out.println("Load of office" + ee);}
                             
                              //This is for School tab
                                                           try {
                                                               
                                                               String LoadSchool= " SELECT * FROM HBS_MST_SCHOOLS WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? and SURVEY_YEAR=?";
                                                               ps3=connection.prepareStatement(LoadSchool);
                                                               ps3.setInt(1,District_Code);
                                                               ps3.setInt(2,Block_Code);
                                                               ps3.setInt(3,Panchayat_Code);
                                                               ps3.setInt(4,Habitation_Code);
                                                               ps3.setInt(5,CensusYear);
                                                               results3=ps3.executeQuery();
                                                               
                                                               while(results3.next())
                                                               {
                                                                  System.out.println("in result set of school load");

                                                                  xml=xml+ "<NO_GOVT_ELEMENTARY_SCHOOLS>"+results3.getInt("NO_GOVT_ELEMENTARY_SCHOOLS")+"</NO_GOVT_ELEMENTARY_SCHOOLS>";
                                                                  xml=xml+ "<NO_GOVT_ELEMENTARY_STRENGTH>"+results3.getInt("NO_GOVT_ELEMENTARY_STRENGTH")+"</NO_GOVT_ELEMENTARY_STRENGTH>";
                                                                  xml=xml+ "<NO_GOVT_ELEMENTARY_SCHOOLS_WS>"+results3.getInt("NO_GOVT_ELEMENTARY_SCHOOLS_WS")+"</NO_GOVT_ELEMENTARY_SCHOOLS_WS>";
                                                                  xml=xml+ "<NO_GOVT_MIDDLE_SCHOOLS>"+results3.getInt("NO_GOVT_MIDDLE_SCHOOLS")+"</NO_GOVT_MIDDLE_SCHOOLS>";
                                                                  xml=xml+ "<NO_GOVT_MIDDLE_STRENGTH>"+results3.getInt("NO_GOVT_MIDDLE_STRENGTH")+"</NO_GOVT_MIDDLE_STRENGTH>";
                                                                  
                                                                   xml=xml+ "<NO_GOVT_MIDDLE_SCHOOLS_WS>"+results3.getInt("NO_GOVT_MIDDLE_SCHOOLS_WS")+"</NO_GOVT_MIDDLE_SCHOOLS_WS>";
                                                                   xml=xml+ "<NO_GOVT_HIGH_SCHOOLS>"+results3.getInt("NO_GOVT_HIGH_SCHOOLS")+"</NO_GOVT_HIGH_SCHOOLS>";
                                                                   xml=xml+ "<NO_GOVT_HIGH_STRENGTH>"+results3.getInt("NO_GOVT_HIGH_STRENGTH")+"</NO_GOVT_HIGH_STRENGTH>";
                                                                   xml=xml+ "<NO_GOVT_HIGH_SCHOOLS_WS>"+results3.getInt("NO_GOVT_HIGH_SCHOOLS_WS")+"</NO_GOVT_HIGH_SCHOOLS_WS>";
                                                                   xml=xml+ "<NO_GOVT_HS_SCHOOLS>"+results3.getInt("NO_GOVT_HS_SCHOOLS")+"</NO_GOVT_HS_SCHOOLS>";

                                                                   xml=xml+ "<NO_GOVT_HS_STRENGTH>"+results3.getInt("NO_GOVT_HS_STRENGTH")+"</NO_GOVT_HS_STRENGTH>";
                                                                   xml=xml+ "<NO_GOVT_HS_SCHOOLS_WS>"+results3.getInt("NO_GOVT_HS_SCHOOLS_WS")+"</NO_GOVT_HS_SCHOOLS_WS>";
                                                                  
                                                                   xml=xml+ "<NO_PVT_ELEMENTARY_SCHOOLS>"+results3.getInt("NO_PVT_ELEMENTARY_SCHOOLS")+"</NO_PVT_ELEMENTARY_SCHOOLS>";
                                                                   xml=xml+ "<NO_PVT_ELEMENTARY_STRENGTH>"+results3.getInt("NO_PVT_ELEMENTARY_STRENGTH")+"</NO_PVT_ELEMENTARY_STRENGTH>";
                                                                   xml=xml+ "<NO_PVT_ELEMENTARY_SCHOOLS_WS>"+results3.getInt("NO_PVT_ELEMENTARY_SCHOOLS_WS")+"</NO_PVT_ELEMENTARY_SCHOOLS_WS>";
                                                                   xml=xml+ "<NO_PVT_MIDDLE_SCHOOLS>"+results3.getInt("NO_PVT_MIDDLE_SCHOOLS")+"</NO_PVT_MIDDLE_SCHOOLS>";
                                                                   xml=xml+ "<NO_PVT_MIDDLE_STRENGTH>"+results3.getInt("NO_PVT_MIDDLE_STRENGTH")+"</NO_PVT_MIDDLE_STRENGTH>";

                                                                   xml=xml+ "<NO_PVT_MIDDLE_SCHOOLS_WS>"+results3.getInt("NO_PVT_MIDDLE_SCHOOLS_WS")+"</NO_PVT_MIDDLE_SCHOOLS_WS>";
                                                                   xml=xml+ "<NO_PVT_HIGH_SCHOOLS>"+results3.getInt("NO_PVT_HIGH_SCHOOLS")+"</NO_PVT_HIGH_SCHOOLS>";
                                                                   xml=xml+ "<NO_PVT_HIGH_STRENGTH>"+results3.getInt("NO_PVT_HIGH_STRENGTH")+"</NO_PVT_HIGH_STRENGTH>";
                                                                   xml=xml+ "<NO_PVT_HIGH_SCHOOLS_WS>"+results3.getInt("NO_PVT_HIGH_SCHOOLS_WS")+"</NO_PVT_HIGH_SCHOOLS_WS>";
                                                                   xml=xml+ "<NO_PVT_HS_SCHOOLS>"+results3.getInt("NO_PVT_HS_SCHOOLS")+"</NO_PVT_HS_SCHOOLS>";

                                                                   xml=xml+ "<NO_PVT_HS_STRENGTH>"+results3.getInt("NO_PVT_HS_STRENGTH")+"</NO_PVT_HS_STRENGTH>";
                                                                   xml=xml+ "<NO_PVT_HS_SCHOOLS_WS>"+results3.getInt("NO_PVT_HS_SCHOOLS_WS")+"</NO_PVT_HS_SCHOOLS_WS>";
                                                                   
                                                                  
                                                               }
                                                               
                                                           }
                                                           catch(Exception ee){System.out.println("Load of school" + ee);}

                             //This is for Cattle tab
                              try {
                                  
                                  String LoadCattle= " SELECT * FROM HBS_MST_CATTLE_DATA WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? and SURVEY_YEAR=?";
                                  ps4=connection.prepareStatement(LoadCattle);
                                  ps4.setInt(1,District_Code);
                                  ps4.setInt(2,Block_Code);
                                  ps4.setInt(3,Panchayat_Code);
                                  ps4.setInt(4,Habitation_Code);
                                  ps4.setInt(5,CensusYear);
                                  results4=ps4.executeQuery();
                                  
                                  while(results4.next())
                                  {
                                     System.out.println("in result set of cattle load");

                                     xml=xml+ "<TOTAL_NO_CATTLE>"+results4.getInt("TOTAL_NO_CATTLE")+"</TOTAL_NO_CATTLE>";
                                     xml=xml+ "<DAILY_REQMT_WS>"+results4.getInt("DAILY_REQMT_WS")+"</DAILY_REQMT_WS>";
                                     xml=xml+ "<EXISTING_WS_KLD>"+results4.getInt("EXISTING_WS_KLD")+"</EXISTING_WS_KLD>";
                                  }
                                  
                              }
                              catch(Exception ee){System.out.println("Load of cattle" + ee);}
                             
                              //This is for Additional tab
                               try {
                                   
                                   String LoadAdditional= " SELECT * FROM HBS_MST_ADDITIONAL WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? and SURVEY_YEAR=?";
                                   ps5=connection.prepareStatement(LoadAdditional);
                                   ps5.setInt(1,District_Code);
                                   ps5.setInt(2,Block_Code);
                                   ps5.setInt(3,Panchayat_Code);
                                   ps5.setInt(4,Habitation_Code);
                                   ps5.setInt(5,CensusYear);
                                   results5=ps5.executeQuery();
                                   
                                   while(results5.next())
                                   {
                                      System.out.println("in result set of Additional load");

                                       xml=xml+ "<NO_SEPTIC_TANKS>"+results5.getInt("NO_SEPTIC_TANKS")+"</NO_SEPTIC_TANKS>";
                                       xml=xml+ "<NO_DRY_LATRINES>"+results5.getInt("NO_DRY_LATRINES")+"</NO_DRY_LATRINES>";
                                       xml=xml+ "<NO_LCS_LATRINES>"+results5.getInt("NO_LCS_LATRINES")+"</NO_LCS_LATRINES>";
                                       xml=xml+ "<NO_COMM_LATRINES_USE>"+results5.getInt("NO_COMM_LATRINES_USE")+"</NO_COMM_LATRINES_USE>";
                                       xml=xml+ "<NO_COMM_LATRINES_DEFECT>"+results5.getInt("NO_COMM_LATRINES_DEFECT")+"</NO_COMM_LATRINES_DEFECT>";
                                       xml=xml+ "<NO_SULLAGE_DRAINAGE>"+results5.getInt("NO_SULLAGE_DRAINAGE")+"</NO_SULLAGE_DRAINAGE>";
                                       xml=xml+ "<NO_HOMES_SULLAGE_DRAINAGE>"+results5.getInt("NO_HOMES_SULLAGE_DRAINAGE")+"</NO_HOMES_SULLAGE_DRAINAGE>";
                                   }
                                   
                               }
                               catch(Exception ee){System.out.println("Load of Additional" + ee);}
                             
                             
                              //This is for Habitation Type tab
                               try {
                                   
                                   String LoadHabit= " SELECT * FROM HBS_MST_HABITATION_TYPE WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? and SURVEY_YEAR=?";
                                   ps6=connection.prepareStatement(LoadHabit);
                                   ps6.setInt(1,District_Code);
                                   ps6.setInt(2,Block_Code);
                                   ps6.setInt(3,Panchayat_Code);
                                   ps6.setInt(4,Habitation_Code);
                                   ps6.setInt(5,CensusYear);
                                   results6=ps6.executeQuery();
                                   
                                   while(results6.next())
                                   {
                                      System.out.println("in result set of Habitation Type load");

                                       xml=xml+ "<NEIGHBOUR_HABIT_DISTANCE>"+results6.getInt("NEIGHBOUR_HABIT_DISTANCE")+"</NEIGHBOUR_HABIT_DISTANCE>";
                                       xml=xml+ "<COVERED_ADDL_VILLAGE>"+results6.getString("COVERED_ADDL_VILLAGE")+"</COVERED_ADDL_VILLAGE>";
                                       xml=xml+ "<NAME_ADDL_VILLAGE>"+results6.getString("NAME_ADDL_VILLAGE")+"</NAME_ADDL_VILLAGE>";
                                       xml=xml+ "<RIVER_BASIN_CODE>"+results6.getInt("RIVER_BASIN_CODE")+"</RIVER_BASIN_CODE>";
                                       xml=xml+ "<HABITATION_OLD_NEW>"+results6.getString("HABITATION_OLD_NEW")+"</HABITATION_OLD_NEW>";
                                       xml=xml+ "<TOP_SOIL_CODE>"+results6.getInt("TOP_SOIL_CODE")+"</TOP_SOIL_CODE>";
                                       xml=xml+ "<SUB_STRATA_SOIL_CODE>"+results6.getInt("SUB_STRATA_SOIL_CODE")+"</SUB_STRATA_SOIL_CODE>";
                                       xml=xml+ "<HABITATION_GROUP_CODE>"+results6.getInt("HABITATION_GROUP_CODE")+"</HABITATION_GROUP_CODE>";
                                   }
                                   
                               }
                               catch(Exception ee){System.out.println("Load of Habitation" + ee);}
                               
                               
                          
                      
                                //This is for Others Tab
                                       try {
                                           
                                           String LoadOthers= " SELECT * FROM HBS_MST_WATER_SOURCES WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=? and SURVEY_YEAR=?";
                                           ps7=connection.prepareStatement(LoadOthers);
                                           ps7.setInt(1,District_Code);
                                           ps7.setInt(2,Block_Code);
                                           ps7.setInt(3,Panchayat_Code);
                                           ps7.setInt(4,Habitation_Code);
                                           ps7.setInt(5,CensusYear);
                                           results7=ps7.executeQuery();
                                           
                                           while(results7.next())
                                           {
                                              System.out.println("in result set of Others load");

                                              xml=xml+ "<WATER_SOURCE_SINO>"+results7.getInt("WATER_SOURCE_SINO")+"</WATER_SOURCE_SINO>";
                                              xml=xml+ "<WATER_SOURCE_TYPE_ID>"+results7.getInt("WATER_SOURCE_TYPE_ID")+"</WATER_SOURCE_TYPE_ID>";
                                              xml=xml+ "<NO_OF_SOURCES>"+results7.getInt("NO_OF_SOURCES")+"</NO_OF_SOURCES>";
                                              xml=xml+ "<NO_OF_SOURCES_IN_USE>"+results7.getInt("NO_OF_SOURCES_IN_USE")+"</NO_OF_SOURCES_IN_USE>";
                                              xml=xml+ "<NO_OF_MONTHS_WATER_AVAILABLE>"+results7.getInt("NO_OF_MONTHS_WATER_AVAILABLE")+"</NO_OF_MONTHS_WATER_AVAILABLE>";
                                              
                                           }
                                           
                                       }
                                       catch(Exception ee){System.out.println("Load of Others" + ee);}
                                      
                                      
                                     }
                                     catch(Exception e)
                                     {        
                                        System.out.println("Exception in the office *********"+ e);
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