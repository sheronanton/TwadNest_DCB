<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@ page isThreadSafe="true"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0">
<title>Twad Board Intranet Services</title>
<link type="text/css" href="fg.menu.css" media="screen" rel="stylesheet" />
<link type="text/css" href="themes/start/jquery-ui.css" media="screen" rel="stylesheet" />
<script language='javascript' src='jquery-3.6.0.js'></script>
<script type='text/javascript' src='org/Security/scripts/twad.js'></script>
<link href='css/index.css' rel='stylesheet' media='screen' />
<script type='text/javascript' src='org/Security/scripts/StatusBarScrollText.js'></script>
<script language='javascript' src='./jquery-3.6.0.js'></script>
 
<style type="text/css">
 
.web_dialog_overlay
{
   position: fixed;
   top: 0;
   right: 0;
   bottom: 0;
   left: 0;
   height: 100%;
   width: 100%;
   margin: 0;
   padding: 0;
   background: #000000;
   opacity: .15;
   filter: alpha(opacity=15);
   -moz-opacity: .15;
   z-index: 101;
   display: none;
}
.web_dialog
{
   display: none;
   position: fixed;
   width: 380px;
   height: 200px;
   top: 50%;
   left: 50%;
   margin-left: -190px;
   margin-top: -100px;
   background-color: #ffffff;
   border: 2px solid #336699;
   padding: 0px;
   z-index: 102;
   font-family: Verdana;
   font-size: 10pt;
}
.web_dialog_title
{
   border-bottom: solid 2px #336699;
   background-color: #336699;
   padding: 4px;
   color: White;
   font-weight:bold;
}
.web_dialog_title a
{
   color: White;
   text-decoration: none;
}
.align_right
{
   text-align: right;
}

</style>
 <script type="text/javascript">
 $(document).ready(function ()
	        {
				 $("#btnShowSimple").click(function (e)
	            {
	                ShowDialog(true);
	                e.preventDefault();
	            });
				 $("#btnClose").click(function (e)
					      {
					         HideDialog();
					         e.preventDefault();
					      });
	        });
 function ShowDialog(modal)
 {
     $("#overlay").show();
     $("#dialog").fadeIn(300);

     if (modal)
     {
         $("#overlay").unbind("click");
     }
     else
     {
         $("#overlay").click(function (e)
         {
             HideDialog();
         });
     }
 }
 
 function HideDialog()
 {
    $("#overlay").hide();
     $("#dialog").fadeOut(300);
 } 
 </script>
 
</head>
<%
	try {
		Controller obj=new Controller();
		Connection con=null;
		response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0
		response.setDateHeader("Expires", 0); //prevent caching at the proxy server
		int empid = 0;
		String status = "";
		String Remote_host = "";
		String sess_id = "";
		String sessionid ="";
		String userid="0",Office_id="",Office_Name="";
		boolean flag_chk = true;
		try {
			flag_chk = request.isRequestedSessionIdFromCookie();
			Remote_host = request.getRemoteHost();
			session = request.getSession(false);
			if (session != null) 
			{
				  sessionid = request.getParameter("session");
  				empid = Integer.parseInt(request.getParameter("empid"));
				status = request.getParameter("status");
				
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	
		try {
			if (session == null) 
			{
				response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
				return;
			}
			//session.setMaxInactiveInterval(60);
			if (!session.isNew()) 
			{
				if (session.getAttribute("UserProfile") == null) 
				{
					session.invalidate();
					session = null;
					response.sendRedirect(request.getContextPath()+ "/index.jsp?message=sessionout");
				}
			}
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath()+ "/index.jsp?message=sessionout");
		}
		String sub_=request.getParameter("but1");
		if (sub_==null) sub_="0";
		con=obj.con();
		obj.createStatement(con); 
		sess_id = session.getId();
		UserProfile up = null;
		up = (UserProfile) session.getAttribute("UserProfile");
		String profile = (String) session.getAttribute("profile");
		String sqlGetRoleId = "";
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ResultSet results = null;
		ResultSet cntt, m1 = null;
		ResultSet results1 = null;
		ResultSet results2 = null;
		ResultSet results3 = null;
		ResultSet results4 = null;
		try {

					ResourceBundle rb = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
					String ConnectionString = "";
					String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
					String strdsn = rb.getString("Config.DSN");
					String strhostname = rb.getString("Config.HOST_NAME");
					String strportno = rb.getString("Config.PORT_NUMBER");
					String strsid = rb.getString("Config.SID");
					String strdbusername = rb.getString("Config.USER_NAME");
					String strdbpassword = rb.getString("Config.PASSWORD");
					up = (UserProfile) session.getAttribute("UserProfile");
			//		ConnectionString = strdsn.trim() + "@" + strhostname.trim()+ ":" + strportno.trim() + ":" + strsid.trim();
			 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
					Class.forName(strDriver.trim());
					connection = DriverManager.getConnection(ConnectionString,strdbusername.trim(), strdbpassword.trim());
					} catch (Exception e) {
					System.out.println("Exception in connection...." + e);
					response.sendRedirect(request.getContextPath()+ "/index.jsp?message=dbnill");
					}
			userid=(String)session.getAttribute("UserId");
			Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			if (Office_id.equals("")) Office_id="0";
			Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String msg="";
			msg=obj.getValue("PMS_DCB_SETTING", "MESSAGE","where OFFICE_ID="+Office_id+ " and status='L' ");
			if (sub_.equalsIgnoreCase("Continue")) 
			{
				 String path="/menuchecknew.jsp?session="+sessionid+"&empid="+empid+"&status="+status;
                 //path="/menuchecknew.jsp?session="+session.getId()+"&empid="+empid+"&status="+status;
                 response.encodeRedirectURL(path);  
                 //response.sendRedirect(path);
                  try   
				  {
                  response.sendRedirect(request.getContextPath()+path);
                  }catch(Exception e) 
                  {
                  }
                
                 msg=obj.getValue("PMS_DCB_SETTING", "MESSAGE","where OFFICE_ID="+Office_id+ " and status='L' "); 
			}
%>   
<body> 
<form action="message_show.jsp" method="get">

<table style="width: 100%; border: 1px;" cellpadding="3" cellspacing="0" align="center" border="0">  
		<tr>
			<td align="center">  
				  <input border="0"  style="font-size:3ex;background-repeat:no-repeat;background-image: url('images/msg.jpg');border:0ex; height: 216px; width: 230px;text-align: center;vertical-align: bottom"  type="button" id="btnShowSimple" value="Click here!!!!!!" />
			</td>
		</tr>  
	</table> 
<div id="dialog" class="web_dialog"> 
	 
   <table style="width: 100%; border: 0px;" cellpadding="12" cellspacing="0" border="0">
      <tr>
         <td class="web_dialog_title">Message</td>
         <td class="web_dialog_title align_right">
            <a href="#" id="btnClose"><%=Office_Name%></a>
         </td>
      </tr>
      <tr>
      	<td align="center" colspan="2"> 
      		<font color="blue"><%=msg %></font>  
      	</td> 
      </tr>
     <tr>
      	<td align="center" colspan="2">
      		<input type=submit name="but1" value="Continue">
      	</td>
      </tr>
      </table>
</div>
<input type="hidden" name="status" value="<%=status%>">
<input type="hidden" name="empid" value="<%=empid%>">
<input type="hidden" name="session" value="<%=sessionid%>">
 
</form>
</body>
<%
	} catch (Exception e) 
	{
		response.sendRedirect("index.jsp");
	}
%>
</html>