/*
 *  This servlet is mainly used for validating the logged-in user details and the necessary details are added in the session
 *  attributes.
 * */


package Servlets.Security.servlets;

import Servlets.Security.classes.UserProfile;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Date;
import java.util.ResourceBundle;
import javax.servlet.*;
import javax.servlet.http.*;

public class UserValidation_DCB extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection=null;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
         
        System.out.println("::::::::::::USER LOGIN CONNECTION INITIALING");
    }
    
   
    public void doGet(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
                           response.sendRedirect(request.getContextPath()+"/index.jsp");
                       }
    
 
    public void doPost(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
       
        /*
         * The following code is for creating the Connection object.
         * 
         * In config file, the parameters such as (HostName, Port number, driver, dsn, username, password, etc..)
         * required for creating connection object is declared.
         * 
         * These Parameters are get through ResourceBundle object and creating the connection object.
         * 
         * If any error in creating connection object, then the page is redirected to index page.
         * 
         * */
       
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

      //      ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" + strsid.trim() ;
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
            System.out.println(" connection string : " + ConnectionString);
            
            Class.forName(strDriver.trim());
            connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e){System.out.println("Connection e"+e);
        //RequestDispatcher rd=config.getServletContext().getNamedDispatcher("/index.jsp?message=yes");
         response.sendRedirect("index.jsp?message=dbnill");
         
         return;
        }
       
        boolean loginflag=false;
         ResultSet results=null;
         PreparedStatement statement=null;
         HttpSession session=null;
         int empid=0;
         String strID = "";
         String strPassword = "";
         String status="";
         String Remote_host="";
         ResultSet resultset=null;
         java.sql.Date retiredate=null;
         java.sql.Date currentdate=null;
       System.out.println("user validateion");
       
       /*
        * 
        * The following code is for getting the Username and Password from index.jsp for authentication.
        * 
        * the session object is made null and the password is typecasted to byte
        * and the byte is stored in byte array.
        * 
        * For security purpose, we are using MD5 Algorithm.
        * After creating the MessgeDigest object, it is resetted and updating the password in the form of byte array into
        * MessageDigest object.         
        * 
        * Using digest method of MD5 algorithm, the byte is hashed and the hash value stored in the array of byte datatype.
        * 
        * The hash value is stored in String Buffer and with this value a bit wise AND operation is done using 0XFF(255).
        * so that from the hash value original string cannot be detected. 
        * 
        * 
        * */
       
        try
        {
          strID = request.getParameter("txtID");
          strPassword = request.getParameter("txtPassword");
                // System.out.println("user Name:"+strID);
        // System.out.println("user password:"+strPassword);
          //Getting  Remote host IP
          Remote_host=request.getRemoteHost();
          System.out.println("Remote Host is :"+Remote_host);
            
           
           session=request.getSession(false);
           System.out.println("::Session:: "+session);
           if(session==null) 
           {
           System.out.println("Session parameter is null ");
          
                  if(strID!=null && strPassword!=null )
                  {
                       byte []b=strPassword.getBytes();
                       try{
                           MessageDigest algorithm = MessageDigest.getInstance("MD5");
                           algorithm.reset();
                           algorithm.update(b);
                           byte messageDigest[] = algorithm.digest();
                           System.out.println("actual encrypt::"+messageDigest);                            
                           StringBuffer hexString = new StringBuffer();
                           for (int i=0;i<messageDigest.length;i++) {
                                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                               
                           }
                               System.out.println("After appending :"+hexString);
                           strPassword=new String(hexString);
                           //System.out.println(strPassword);
                           }catch(NoSuchAlgorithmException nsae){
                                System.out.println(nsae); 
                       }
                           
                              try
                             {
                             
                             
                             /*
                              * 
                              * The following code describe that after getting username and hash value of password,
                              * it is validated in the database.
                              * 
                              * If the username and password is found to be correct then value of login enabled is checked and
                              * if the value is set to '0' then the page is redirected to index page and if the value is '1' it is proceeding
                              * forward.
                              * 
                              * Next, the value of user category id is ckecked. If the value is '1' then the user is from twad board and if the
                              * value is other than '1' then the user is from other department. 
                              * 
                              * If the user is from 'TWAD' board then validating the employee id is valid id or not and next validating the
                              * employee id is retired or not.
                              * 
                              * After validating, the details about logeed in employee such as (Employee Id, name, designation and current posting) are 
                              * added in the session attribute and details are saved in the User profile file.
                              * 
                              * if any database data is mismatched with the validated data then the page is redirected to index page.
                              * 
                              * 
                              * */
                            
                            String sql=null; 
                             
                           /* if (strID.equalsIgnoreCase("twad10099"))
                            {
                              sql="select * from SEC_MST_USERS where USER_ID=?";
                              statement=connection.prepareStatement(sql);
                              statement.setString(1,strID);
                            }
                            else   */
                            {
                                sql="select * from SEC_MST_USERS where USER_ID=? and USER_PASSWORD=?";
                                statement=connection.prepareStatement(sql);
                                statement.setString(1,strID);
                                statement.setString(2,strPassword);
                            }
                            
                            
                            
                               results=statement.executeQuery();
                               
                                 if(results.next())    // login was successful
                                 {
                                 System.out.println("login success");
                                   
                                   
                                     String active=results.getString("LOGIN_ENABLED");
                                     if(active==null || active.equalsIgnoreCase("0")) {
                                         response.sendRedirect("index.jsp?message=logindisabled");
                                         return;
                                     }
                                   
                                    loginflag=true;
                                   empid=results.getInt("Employee_Id");
                                   status=results.getString("CHANGE_PASSWORD"); 
                                   //System.out.println("status of pass change:"+status);
                                   int categoryid=1;
                                   try{
                                       categoryid=results.getInt("USER_CATEGORY_ID");
                                   }
                                   catch(Exception e){
                                       System.out.println("Error in category id:"+e);
                                   }
                                    
                                   results.close();
                                   statement.close();
                                     
                                     System.out.println("Login part is ok");
                                   
                                     
                                     UserProfile up=null;
                                     ResultSet profile=null;
                                     
                                     if(categoryid==1)
                                     {
                                               sql="select * from VIEW_EMPLOYEE1 where Employee_Id=?";
                                                 statement=connection.prepareStatement(sql);
                                                statement.setInt(1,empid);
                                                System.out.print("employee id : " + empid);
                                                
                                                try
                                                {
                    
                                                    profile=statement.executeQuery();
                                                    // creating user profile object
                                                    if(!profile.next()) {
                                                        response.sendRedirect("index.jsp?message=noprofile");
                                                        return;
                                                    }
                                                    
                                                   
                                                   //System.out.println("empid:"+profile.getInt("Employee_Id"));
                                                    up=new UserProfile(profile.getInt("Employee_Id"),(profile.getString("Employee_Initial")==null)?"":profile.getString("Employee_Initial"),profile.getString("Employee_Name"),profile.getString("Employee_Prefix"),profile.getString("Designation"),profile.getString("Office_Level"),profile.getString("Office_Short_Name"),profile.getString("Office_Address"));
                                                    System.out.println("Employee sucessfully loaded");
                                                    //System.out.println("Employee Name : " + up.getEmployeeName());
                    
                                                    profile.close();
                                                    statement.close();
                                                    
                                                    session=request.getSession(true);
                                                    // session.setMaxInactiveInterval(5);
                                                    // System.out.println("New session id:"+session.getId());
                                                     
                                                     sql="select max(ACCESS_SEQ_NUM)  acc_seq from SEC_MST_USERS_LOGIN_HISTORY";
                                                     PreparedStatement ps=connection.prepareStatement(sql);
                                                     ResultSet rset=ps.executeQuery();
                                                     rset.next();
                                                     int accno=rset.getInt("acc_seq");
                                                     if(accno>0) {
                                                            accno+=1;
                                                     }
                                                     else {
                                                         accno=1;
                                                     }
                                                     
                                                     System.out.println("Acc No:"+accno);
                                                     sql="insert into sec_mst_users_login_history(USER_ID,LOGGED_IN_TIME,IP_ADDRESS,SESSION_ID,ACCESS_SEQ_NUM) values(?,?,?,?,?)";
                                                     ps=connection.prepareStatement(sql);
                                                     ps.setString(1,strID);
                                                     java.sql.Date dt=new java.sql.Date(System.currentTimeMillis());
                                                     Timestamp tms=new Timestamp(System.currentTimeMillis());
                                                     ps.setTimestamp(2,tms);
                                                     ps.setString(3,Remote_host);
                                                     ps.setString(4,session.getId());
                                                     ps.setInt(5,accno);
                                                     ps.executeUpdate();
                                                     
                                                     session.setAttribute("accno",String.valueOf(accno));
                                                    
                                                    session.setAttribute("UserProfile",up);
                                                    session.setAttribute("UserId",strID);
                                                    
                                                    System.out.println("profile from VIEW TWAD");
                                                    session.setAttribute("profile","twad");
                                                    
                                                }
                                                catch(Exception e) {
                                                    System.out.println(e);
                                                    response.sendRedirect("index.jsp?message=noprofile");
                                                    return;
                                                }
                                                
                                                try {
                                                
                                                    strID = request.getParameter("txtID");
                                                    System.out.println("User id is.............."+strID);
                                                    String strempid=strID.substring(4);
                                                    int intempid=Integer.parseInt(strempid);
                                                    System.out.println("sub string of user id is------------------"+strempid);
                                                    sql="select retiredate,(select sysdate from dual) as currentdate from allretirementview where employee_id=?";
                                                    statement=connection.prepareStatement(sql);
                                                    statement.setInt(1,intempid);
                                                    resultset=statement.executeQuery();
                                                    while(resultset.next()) {
                                                          retiredate=resultset.getDate("retiredate");
                                                          currentdate=resultset.getDate("currentdate");
                                                          if(currentdate.compareTo(retiredate)>0) {
                                                                  //do not allow to login the retired employee
                                                                   response.sendRedirect("index.jsp?message=retired");
                                                                   return;
                                                              }
                                                          else {
                                                                System.out.println("Not a retired employee");  
                                                              }
                                                          
                                                      }
                                                }
                                                catch(Exception e) {
                                                      System.out.println("Exception in retirement date verifications ");
                                                  }
                                     }
                                     else{
                                        
                                        // sql="select SEC_MST_OTHER_USERS_PROFILE.* from SEC_MST_OTHER_USERS_PROFILE, where USER_ID=?";
                                         sql="select SEC_MST_OTHER_USERS_PROFILE.*,SEC_MST_USER_CATEGORY.USER_CATEGORY_DESC" + 
                                         " from SEC_MST_OTHER_USERS_PROFILE,SEC_MST_USER_CATEGORY " + 
                                         " where SEC_MST_OTHER_USERS_PROFILE.USER_ID=? " + 
                                         " and SEC_MST_USER_CATEGORY.USER_CATEGORY_ID =?";
                                           statement=connection.prepareStatement(sql);
                                          statement.setString(1,strID);
                                          statement.setInt(2,categoryid);
                                          System.out.print("employee id : " + empid); 
                                          
                                          try
                                          {
                                         
                                              profile=statement.executeQuery();
                                              // creating user profile object
                                              if(!profile.next()) {
                                                  response.sendRedirect("index.jsp?message=noprofile");
                                                  return;
                                              }
                                              
                                             
                                             //System.out.println("empid:"+profile.getInt("Employee_Id"));
                                              up=new UserProfile(0,(profile.getString("USER_INITIAL")==null)?"":profile.getString("USER_INITIAL"),profile.getString("USER_NAME"),profile.getString("USER_PREFIX"),profile.getString("DESIGNATION"),profile.getString("USER_CATEGORY_DESC"),profile.getString("OFFICE_NAME"),profile.getString("OFFICE_ADDRESS"));
                                              System.out.println("Employee sucessfully loaded");
                                              //System.out.println("Employee Name : " + up.getEmployeeName());
                                         
                                              profile.close();
                                              statement.close();
                                              
                                              session=request.getSession(true);
                                              // session.setMaxInactiveInterval(5);
                                              // System.out.println("New session id:"+session.getId());
                                               
                                               sql="select max(ACCESS_SEQ_NUM)  acc_seq from SEC_MST_USERS_LOGIN_HISTORY";
                                               PreparedStatement ps=connection.prepareStatement(sql);
                                               ResultSet rset=ps.executeQuery();
                                               rset.next();
                                               int accno=rset.getInt("acc_seq");
                                               if(accno>0) {
                                                      accno+=1;
                                               }
                                               else {
                                                   accno=1;
                                               }
                                               
                                               System.out.println("Acc No:"+accno);
                                               sql="insert into sec_mst_users_login_history(USER_ID,LOGGED_IN_TIME,IP_ADDRESS,SESSION_ID,ACCESS_SEQ_NUM) values(?,?,?,?,?)";
                                               ps=connection.prepareStatement(sql);
                                               ps.setString(1,strID);
                                               java.sql.Date dt=new java.sql.Date(System.currentTimeMillis());
                                               Timestamp tms=new Timestamp(System.currentTimeMillis());
                                               ps.setTimestamp(2,tms);
                                               ps.setString(3,Remote_host);
                                               ps.setString(4,session.getId());
                                               ps.setInt(5,accno);
                                               ps.executeUpdate();
                                               
                                               session.setAttribute("accno",String.valueOf(accno));
                                              
                                              session.setAttribute("UserProfile",up);
                                              session.setAttribute("UserId",strID);
                                              
                                              System.out.println("profile from SEC_MST_OTHER_USERS_PROFILE");
                                              session.setAttribute("profile","other");
                                              
                                          }
                                          catch(Exception e) {
                                              System.out.println(e);
                                              response.sendRedirect("index.jsp?message=noprofile");
                                              return;
                                          }

                                         
                                     }
                                     
                                     /*
                                      * 
                                      * After validating and session atrributes are added then the details such as employee id,session id
                                      * and his status are passed to servletlogin.
                                      * */
                                     
                                                                
                                     
                                     
                                       String path="/ServletLogin.con?session="+session.getId()+"&empid="+empid+"&status="+status;
                                       path=response.encodeRedirectURL(path);
                                     //response.sendRedirect(request.getContextPath()+"/"+path);
                                     try
                                     {
                                        System.out.println("path:"+path);
                                      response.sendRedirect(request.getContextPath()+path);
                                      //return;
                                       System.out.println("After redirect");
                                      // response.sendRedirect(request.getContextPath()+"/index.jsp?message=yes");
                                     }catch(Exception e) {System.out.println("Redirect Error:"+e);}
                                     return;
                                     
                                     
                                   
                                 }
                                 else {
                                     System.out.println("Login failure");
                                     response.sendRedirect(request.getContextPath()+"/index.jsp?message=yes");
                                     //System.out.println("ok");
                                      System.out.println("After Dispatching");
                                      return;
                                 }
                             }
                             catch(Exception e) {
                                 System.out.println("authentication error:"+e);
                                 response.sendRedirect(request.getContextPath()+"/index.jsp?message=dbnill");
                                 return;
                             }
                  }
                  else {
                      response.sendRedirect(request.getContextPath()+"/index.jsp?message=yes");
                      return;
                      
                  }
           } 
           else {
               session.invalidate();
               System.out.println("session is invalidated in uservalidation form");
               response.sendRedirect(request.getContextPath()+"/index.jsp");
               return;
           }
           
        }
        catch(Exception e)
        {
          e.printStackTrace();
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }
    }
}
