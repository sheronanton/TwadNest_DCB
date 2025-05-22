<%@ page contentType="text/html;charset=windows-1252" session="false"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@ page import="java.sql.*,java.util.*"%>
<html>
<head>
<meta http-equiv="Content-Type"content="text/html; charset=windows-1252">
<link href="css/index.css" rel="stylesheet" media="screen" />
	<script type="text/javascript">
	function c1()
	{
		document.getElementById("pr").style.color="red";
		setTimeout ("c2()", 500 );
	}  
	function c2()
	{ 
		document.getElementById("pr").style.color="green";
		setTimeout ("c1()", 500 );
	}
	</script>
</head>
<body onload="c1()">
<%
	   Connection connection=null;
	   Statement statement=null;
	   PreparedStatement ps=null;
	   ResultSet results=null;   
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
		        }          
  		}catch(Exception e)
  		{
		}  
 %>
<% 
    try
    {       
       String Remote_host="";
       String sess_id="";
       int out_flag=0;
      // getting remote host address
       Remote_host=request.getRemoteHost();
                         // updating logout time on history
                            try
                            {
                              ps=connection.prepareStatement("update sec_mst_users_login_history set logged_out_time=(select sysdate from dual) where user_id=? and ip_address=? and session_id=?"); 
                              // getting user id from the session object
                              HttpSession session=request.getSession();
                              UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                              int Emp_Id=empProfile.getEmployeeId();
                              sess_id=session.getId();
                               ps.setInt(1,Emp_Id);
                               ps.setString(2,Remote_host);
                               ps.setString(3,sess_id);
                               out_flag=ps.executeUpdate(); 
                               connection.commit();
                               if(out_flag>0)
                               {
                               System.out.println("Logout time inserted successfully");
                               }
                                
                             }
                    catch(Exception e)
                           {
                                System.out.println("Problem in updating login history : " + e);
                           } 
					        HttpSession session=request.getSession(false);
					        session.removeAttribute("UserProfile"); 
					        session.invalidate();
					        session=null;
    }
    catch(Exception e)
    {     
    System.out.println(e);
    }
  %>
<br>
<br>
<br>
<br>
<br>
<br>
<form name="divlogout">
<div id=seperator1  ><hr width="500"></div>
<center>
<p id='pr'><b> You have logged out, successfully.<br>
To visit the home page, please follow this link&nbsp; <a
	href="index.jsp">Home</a>&nbsp; </b></p>
</center>
<div id=seperator2  ><hr width="500"></div>

<script type="text/javascript">
if( self != top ) { top.location.replace(self.location.href); }
</script>
</form>
</body>
</html>
