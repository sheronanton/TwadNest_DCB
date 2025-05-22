package Servlets.PMS.PMS1.HabitationSurvey.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class New_Scheme_LoadServlet extends HttpServlet
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
    
    private PreparedStatement ps=null;
    private PreparedStatement ps1=null;
    private PreparedStatement ps2=null;
    private PreparedStatement ps3=null;
    private PreparedStatement ps4=null;
    private PreparedStatement ps5=null;
    private PreparedStatement ps6=null;

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

                     //     ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
                           Class.forName(strDriver.trim());
                           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                        System.out.println("This is the scheme load servlet called");
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
                     
                     
                     //to fetch values from the table using load command
                     
                       if(strCommand.equals("SchemeHomeLoad"))
                            {
                          xml="<response><command>SchemeHomeLoad</command>";
                             
                             //This is for Home tab load
                            
                             String LoadHome="SELECT a.SCHEME_TYPE_ID,a.SCHEME_TYPE_NAME, " + 
                             "b.PROGRAMME_ID,b.PROGRAMME_DESC," + 
                             "c.EXEC_AGENCY_ID,c.EXEC_AGENCY_NAME," + 
                             "d.SCHEME_STATUS_ID,d.SCHEME_STATUS_DESC," + 
                             "e.SCHEME_NO," + 
                             "e.SCHEME_COMPLETION_DATE," + 
                             "e.COST_IN_LAKHS," + 
                             "e.NO_FOUNTAINS_TWAD," + 
                             "e.NO_FOUNTAINS_ADDITIONAL," + 
                             "e.FOUNTAIN_WATER_ADEQUATE," + 
                             "e.HOUSE_CONNECTIONS," + 
                             "e.HOUSE_CONNECTION_POPULATION," + 
                             "e.LPCD_AFTER_COMPLETION " + 
                             "from RWS_MST_SCHEME_TYPES a,RWS_MST_SCHEME_PROGRAMMES b," + 
                             "RWS_MST_EXEC_AGENCIES c, " + 
                             "RWS_MST_SCHEME_STATUS d, " + 
                             "HBS_MST_SCHEMES e " + 
                             "where a.SCHEME_TYPE_ID=e.SCHEME_TYPE_ID and " + 
                             "b.PROGRAMME_ID=e.PROGRAMME_ID and " + 
                             "c.EXEC_AGENCY_ID=e.EXEC_AGENCY_ID and " + 
                             "d.SCHEME_STATUS_ID=e.SCHEME_STATUS_ID and " + 
                             "DISTRICT_CODE=? and " + 
                             "BLOCK_CODE=? and " + 
                             "PANCHAYAT_CODE=? and " + 
                             "HABITATION_CODE=? order by e.SCHEME_NO";
                               //String LoadHome="SELECT * FROM HBS_MST_SCHEMES WHERE DISTRICT_CODE=? AND BLOCK_CODE=? AND PANCHAYAT_CODE=? AND HABITATION_CODE=?";
                              try
                             {
                                 ps=connection.prepareStatement(LoadHome);
                                 ps.setInt(1,District_Code);
                                 ps.setInt(2,Block_Code);
                                 ps.setInt(3,Panchayat_Code);
                                 ps.setInt(4,Habitation_Code);
                                 results=ps.executeQuery();
                                 
                              String CompDate="";
                              try {
                                  System.out.println("in result set of Home Tab load");
                                  while(results.next())
                                  {
                                     
                                     System.out.println("in result set of Home Tab load");
                                      xml=xml+"<flag>success</flag>";
                                     xml=xml+ "<SCHEME_NO>"+results.getInt("SCHEME_NO")+"</SCHEME_NO>";
                                     xml=xml+ "<SCHEME_TYPE_ID>"+results.getString("SCHEME_TYPE_ID")+"</SCHEME_TYPE_ID>";
                                      xml=xml+ "<SCHEME_TYPE_NAME>"+results.getString("SCHEME_TYPE_NAME")+"</SCHEME_TYPE_NAME>";
                                     xml=xml+ "<PROGRAMME_ID>"+results.getString("PROGRAMME_ID")+"</PROGRAMME_ID>";
                                      xml=xml+ "<PROGRAMME_DESC>"+results.getString("PROGRAMME_DESC")+"</PROGRAMME_DESC>";
                                     xml=xml+ "<EXEC_AGENCY_ID>"+results.getString("EXEC_AGENCY_ID")+"</EXEC_AGENCY_ID>";
                                      xml=xml+ "<EXEC_AGENCY_NAME>"+results.getString("EXEC_AGENCY_NAME")+"</EXEC_AGENCY_NAME>";
                                       
                                      if(results.getDate("SCHEME_COMPLETION_DATE")==null) {
                                          CompDate="0";
                                      }
                                      else
                                      {
                                       String[] sd;
                                       sd=results.getDate("SCHEME_COMPLETION_DATE").toString().split("-");
                                       CompDate=sd[2]+"/"+sd[1]+"/"+sd[0];
                                      }
                                       
                                     xml=xml+ "<SCHEME_COMPLETION_DATE>"+CompDate+"</SCHEME_COMPLETION_DATE>";
                                     
                                      xml=xml+ "<Cost_in_Lakhs>"+results.getInt("COST_IN_LAKHS")+"</Cost_in_Lakhs>";
                                      xml=xml+ "<SCHEME_STATUS_ID>"+results.getString("SCHEME_STATUS_ID")+"</SCHEME_STATUS_ID>";
                                      xml=xml+ "<SCHEME_STATUS_DESC>"+results.getString("SCHEME_STATUS_DESC")+"</SCHEME_STATUS_DESC>";
                                      xml=xml+ "<NO_FOUNTAINS_TWAD>"+results.getInt("NO_FOUNTAINS_TWAD")+"</NO_FOUNTAINS_TWAD>";
                                      xml=xml+ "<NO_FOUNTAINS_ADDITIONAL>"+results.getInt("NO_FOUNTAINS_ADDITIONAL")+"</NO_FOUNTAINS_ADDITIONAL>";
                                      xml=xml+ "<FOUNTAIN_WATER_ADEQUATE>"+results.getString("FOUNTAIN_WATER_ADEQUATE")+"</FOUNTAIN_WATER_ADEQUATE>";

                                      xml=xml+ "<HOUSE_CONNECTIONS>"+results.getInt("HOUSE_CONNECTIONS")+"</HOUSE_CONNECTIONS>";
                                      xml=xml+ "<HOUSE_CONNECTION_POPULATION>"+results.getInt("HOUSE_CONNECTION_POPULATION")+"</HOUSE_CONNECTION_POPULATION>";
                                      xml=xml+ "<LPCD_AFTER_COMPLETION>"+results.getInt("LPCD_AFTER_COMPLETION")+"</LPCD_AFTER_COMPLETION>";
                                     
                                  }
                                  
                              }
                              catch(Exception ee){System.out.println("Load of scheme details" + ee);}
                            
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