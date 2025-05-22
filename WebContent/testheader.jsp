<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0">
<title>Twad Board Intranet Services</title>
<script language='javascript' src='jquery-3.6.0.js'></script>
<script type='text/javascript' src='org/Security/scripts/twad.js'></script>
<link href='css/index.css' rel='stylesheet' media='screen' />
<link href='css/doubletab2.css' rel='stylesheet' media='screen' />
<script type='text/javascript' src='org/Security/scripts/StatusBarScrollText.js'></script>
<script type="text/javascript">
function hide()
{
    var theDiv = document.getElementById('msg');
    theDiv.style.visibility = "hidden";  
}
function hide2()
{
 
    var theDiv = document.getElementById('msg2');
    theDiv.style.visibility = "hidden"; 
}
function show2()
{
	 try
	 {
	    var theDiv = document.getElementById('msg2');
	    theDiv.style.display="display";
	    var divobj = document.getElementById("msg2"); 
	    divobj.style.visibility = "visible"; 
	    if (navigator.appName=="Microsoft Internet Explorer") 
	    computedStyle = divobj.currentStyle; 
	    else computedStyle = document.defaultView.getComputedStyle(divobj, null); 
	    var divWidth = computedStyle.width.replace('px', ''); 
	    var divHeight = computedStyle.height.replace('px', ''); 
	    var pageWidth = window.innerWidth; 
	    var pageHeight = window.innerHeight;   
	    var divLeft = (pageWidth - divWidth) / 2; 
	    var divTop = (pageHeight - divHeight) / 2;
	    divobj.style.left = (divLeft+450) + "px";       
	    divobj.style.top =(divTop+30) + "px";   
		var div=$("#msg2");    
	     $("#msg2").css("color","green").slideUp(0).slideDown(0);
		 //div.animate({left:'825px'},"slow"); 
	    //Set Left and top coordinates for the div tag 
	 }catch(e){}		   
}
function showHide(divId){
    var theDiv = document.getElementById('msg');
    theDiv.style.display="display";
    var  theDiv2 = document.getElementById('msg2');
    theDiv2.style.visibility = "hidden";
    var divobj = document.getElementById("msg"); 
    divobj.style.visibility = "visible"; 
    if (navigator.appName=="Microsoft Internet Explorer") 
    computedStyle = divobj.currentStyle; 
    else computedStyle = document.defaultView.getComputedStyle(divobj, null); 
    //Get Div width and height from StyleSheet 
    var divWidth = computedStyle.width.replace('px', ''); 
    var divHeight = computedStyle.height.replace('px', ''); 
    var pageWidth = window.innerWidth; 
    var pageHeight = window.innerHeight;   
    var divLeft = (pageWidth - divWidth) / 2; 
    var divTop = (pageHeight - divHeight) / 2; 
    //Set Left and top coordinates for the div tag 
    divobj.style.left = divLeft + "px"; 
    divobj.style.top = divTop + "px"; 
}
</script>
<style type="text/css">
.tab {
	overflow: hidden;
	border: 1px solid #d3d3d3;
	background: #fefefe;
	width: 75%; 
	margin: 5% auto 0;
	-moz-border-radius: 5px; /* FF1+ */
	-webkit-border-radius: 5px; /* Saf3-4 */
	border-radius: 5px;
	-moz-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
	-webkit-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
	position: absolute;
	margin-top: -5px;
	margin-left: 200px;
	font-family: "Trebuchet MS", sans-serif;
	font-size: 13px;
	font-weight: bold;
	line-height: 1.0em;
	font-style: normal;
	border-collapse: separate;
	border-color: aqua;
}

.sss {
	position: absolute;
	top: 20%;
	left: 30%;
	width: 400px;
	margin-top: -10px;
	margin-left: 900px;
	height: 100px;
	z-index: 0;
}

.sss1 {
	position: absolute;
	top: 50%;
	left: 30%;
	width: 1300px;
	height: 10px;
	margin-top: -100px;
	margin-left: -500px;
		-moz-border-radius: 5px; /* FF1+ */
	-webkit-border-radius: 5px; /* Saf3-4 */
	-moz-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
	-webkit-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
}

​
table.stats {
	text-align: center;
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
	font-weight: normal;
	font-size: 11px;
	color: #fff;
	width: 880px;
	background-color: #666;
	border: 0px;
	border-collapse: collapse;
	border-spacing: 0px;
}

table.stats td {
	background-color: #c9c9c9;
	color: #000;
	padding: 4px;
	border: 1px #fff solid;
}

table.stats td.hed {
	background-color: white;
	color: block;
	padding: 4px;
	border-bottom: 2px #fff solid;
	font-size: 10px;
	font-weight: bold;
	height: 2.1em;
}

#tabs {
	font-size: 80%;
	margin-top: -12px;
}

#tabs ul {
	float: left;
	background: #6C9FCC url(images/bluebg1.jpg) 50% bottom repeat-x;
	-moz-border-radius: .5em .5em .5em .5em;
	width: 97%;
	padding-top: 4px;
}

#tabs li {
	margin-left: 0px;
	list-style: none;
}

* html #tabs li {
	display: inline; /* ie6 double float margin bug */
}

#tabs li,#tabs li a {
	float: left;
}

#tabs ul li a {
	text-decoration: none;
	padding: 8px;
	color: #FFFFFC;
	font-weight: bold;
}

#tabs ul li.active {
	background: #CEE1EF url(img/nav-right.gif) no-repeat right top;
	border: 2px solid #6C9FCC;
	border-bottom-width: 0px;
	border-color: #6C9FCC #6C9FCC #6C9FCC #6C9FCC;
	-moz-border-radius: .75em .75em 0em 0em;
	border-radius-topleft: .75em;
	border-radius-topright: .75em;
}

#tabs ul li.active a {
	background: url(img/nav-left.gif) no-repeat left top;
	color: #333333;
}
</style>

<script type="text/javascript">
		$(document).ready(function()
		{
		$('#tabs ul li:first').addClass('active'); // Set the class of the first link to active
		$('#tabs ul li a').click(function(){ //When any link is clicked
		$('#tabs ul li').removeClass('active'); // Remove active class from all links
		$(this).parent().addClass('active'); //Set clicked link class to active
		});
		});   
		$(document).ready(function() {	  
		$('#tabs ul li a').click(function(e) {
		
		});
		});   
		$(document).ready(function(){
		//open popup
		$("#pop").ready(function(){
		  $("#overlay_form").fadeIn(100);  
		  positionPopup();
		});

		//close popup
		$("#close").click(function(){
			$("#overlay_form").fadeOut(500);
		});
		});

		//position the popup at the center of the page
		function positionPopup(){
		  if(!$("#overlay_form").is(':visible')){
		    return;
		  } 
		  $("#overlay_form").css({
		   //   left: ($(window).width() - $('#overlay_form').width()) / 2,
		     // top: ($(window).width() - $('#overlay_form').width()) / 5,    
		       left:0,
		      top: 0,
		      position:'relative'
		  });
		}

		//maintain the popup at center of the page when browser resized
		$(window).bind('resize',positionPopup);


		
</script>
</head>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevent caching at the proxy server
%>

<%
	int empid = 0;
	String status = "";
	String Remote_host = "";
	String sess_id = "";
	String serverstatus = "";
	boolean flag_chk = true;
	try {
		flag_chk = request.isRequestedSessionIdFromCookie();
		Remote_host = request.getRemoteHost();
		session = request.getSession(false);
		session.setMaxInactiveInterval(36000);

		if (session != null) {
			String sessionid = request.getParameter("session");
			empid = Integer.parseInt(request.getParameter("empid"));
			status = request.getParameter("status");
		}
	} catch (Exception e) {
		System.out.println("testheader.jsp----124");
	}

	session = request.getSession(false);
	try {
		if (session == null) {
			response.sendRedirect(request.getContextPath()
					+ "/index.jsp?message=sessionout");
			return;
		}

		if (!session.isNew()) {
			if (session.getAttribute("UserProfile") == null) {
				session.invalidate();
				session = null;
				response.sendRedirect(request.getContextPath()
						+ "/index.jsp?message=sessionout");
			}
		}
	} catch (Exception e) {
		response.sendRedirect(request.getContextPath()
				+ "/index.jsp?message=sessionout");
	}
	sess_id = session.getId();
	UserProfile up = null;
	up = (UserProfile) session.getAttribute("UserProfile");
	String profile = (String) session.getAttribute("profile");
	String sqlGetRoleId = "";
	Connection connection = null;
	Controller obj = new Controller();
	ResultSet rs = null;
	PreparedStatement ps = null;
	ResultSet results = null;
	ResultSet cntt, m1 = null;
	ResultSet results1 = null;
	ResultSet results2 = null;
	ResultSet results3 = null;
	ResultSet results4 = null;
	try {
		ResourceBundle rb = ResourceBundle
				.getBundle("Servlets.Security.servlets.Config");
		String ConnectionString = "";
		String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
		String strdsn = rb.getString("Config.DSN");
		String strhostname = rb.getString("Config.HOST_NAME");
		String strportno = rb.getString("Config.PORT_NUMBER");
		String strsid = rb.getString("Config.SID");
		String strdbusername = rb.getString("Config.USER_NAME");
		String strdbpassword = rb.getString("Config.PASSWORD");
	//	ConnectionString = strdsn.trim() + "@" + strhostname.trim()
	//			+ ":" + strportno.trim() + ":" + strsid.trim();
	 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
		Class.forName(strDriver.trim());
		connection = DriverManager.getConnection(ConnectionString,
				strdbusername.trim(), strdbpassword.trim());
		obj.createStatement(connection);
	} catch (Exception e) {
		response.sendRedirect(request.getContextPath()
				+ "/index.jsp?message=dbnill");
	}
	String userid = "";
	try {
		String m11 = "";
		String temp = "";
		int flag = 0;
		int i = 1;
		String compareMenu = "menu";

		try {
			if (profile.equalsIgnoreCase("twad")) {
				sqlGetRoleId = "select Role_Id from SEC_MST_USER_ROLES where Employee_Id=? order by List_Seq_No";
				ps = connection.prepareStatement(sqlGetRoleId);
				ps.setInt(1, up.getEmployeeId());
			} else {
				userid = (String) session.getAttribute("UserId");
				sqlGetRoleId = "select Role_Id from SEC_MST_OTHER_USER_ROLES where USER_ID=? order by List_Seq_No";
				ps = connection.prepareStatement(sqlGetRoleId);
				ps.setString(1, userid);
			}
			results = ps.executeQuery();
			//ArrayList roles = new ArrayList();
			while (results.next()) // Role exsist
			{
				Integer ii = new Integer(results.getInt("Role_Id"));
				if (results.getInt("Role_Id") == 9)
					session.setAttribute("Admin", "YES");
				if (results.getInt("Role_Id") == 7)
					session.setAttribute("FAS_SU", "YES");
				if (results.getInt("Role_Id") == 98)
					session.setAttribute("FAS_SU_REGION", "YES");
				if (results.getInt("Role_Id") == 99)
					session.setAttribute("FAS_SU_CIRCLE", "YES");
				if (results.getInt("Role_Id") == 97)
					session.setAttribute("FAS_SU_ALL", "YES");
				if (results.getInt("Role_Id") == 96)
					session.setAttribute("FAS_CAO", "YES");
			}
			String pr = request.getContextPath();
			boolean r = pr.endsWith("ame");
			//showHide('msg'),hide2()
			
			ResourceBundle rs1 = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			String strhostname = rs1.getString("Config.HOST_NAME");
			System.out.println("strhostname" + strhostname);
			serverstatus=strhostname.equalsIgnoreCase("10.163.31.20") ? "<font size='3' color='white' ><b> Live </b> </font> " : " <font size='3' color='white' ><b> Test Site </b></font>";
%>

<body id="pop" onload="show2()">
<table cellspacing="0" border="0" width="100%"  
	style="margin-top: -10px; height: 0.21em;" cellpadding="0">
	<tr height='89' style='background: images1/ subpageBannerNew.jpg'>
		<td height='89' background='images1/subpageBannerNew.jpg;'
			STYLE="background-repeat: no-repeat;" colspan=3 align='right'>
		<div>
		<table border="0" cellspacing="0" border="0"  width="20%"  >
			<tr>
				<td><%=serverstatus %></td><td>
				<div id='divpre'
					onclick="loadPageInNewWindow('<%=request.getContextPath()%>/org/HR/HR1/EmployeeMaster/jsps/EmployeeOptionJSP.jsp')"
					align='right'
					onmouseover="this.style.cursor='pointer';this.style.color='#000000';this.style.background='#cfdee1';"
					onmouseout="this.style.cursor='default';this.style.color='#ffffff'; this.style.background='#006D8A'">
				<a>Preferences</a></div>
				</td>
				<td>&nbsp;</td>
				<td>
				<div id='divlogout' onclick="loadPage('Logout.jsp')" align='right'
					onmouseover="this.style.cursor='pointer';this.style.color='#000000';divlogout.style.background='#cfdee1';"
					onmouseout="this.style.cursor='default';this.style.color='#ffffff';this.style.background='#006D8A'"><a>Logout</a>
				</div>
				</td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div align='left'><b> Name
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</b><%=up.getEmployeePrefix() + " "
							+ up.getEmpInitial() + "." + up.getEmployeeName()%></div>
		</td>
		<td align='right'>
		<div align='right'><b>Office Level :</b></div>
		</td>
		<td align='left'>
		<div align='left'><%=up.getOfficeLevel()%></div>
		</td>
	</tr>
	<tr class='ProfileBand'>
		<td>
		<div align='left'><b>Designation :</b> <%=up.getDesignation()%></div>
		</td>
		<td align='right'>
		<div align='right'><b>Office Name :</b></div>
		</td>
		<td align='left'>
		<div align='left'><%=up.getOfficeShortName()%></div>
		</td>
	</tr>
	<tr>
		<td colspan="3" align="left">
		<%
					results = ps.executeQuery();
					while (results.next()) 
					{
						if (temp == "") {
							temp = new Integer(results.getInt("Role_Id")).toString();
						} else {
							temp += ","+new Integer(results.getInt("Role_Id")).toString();
						}
					}
				} catch (Exception e) {
				}
				String cnt = "select count(*) as cnt1 from(select distinct(Major_System_Id) from SEC_MST_ROLE_MENUS where Role_Id in ("+temp+")	) ";
				try {
					ps = connection.prepareStatement(cnt);
					cntt = ps.executeQuery();
					cntt.next();
				} catch (Exception e) 
				{
				}
				StringBuffer menucategoryCheck = new StringBuffer();
				StringBuffer menusubCheck = new StringBuffer();
				
				
				String MajorSystemDesc="select Major_System_Short_Desc,Major_System_Id from SEC_MST_MAJOR_SYSTEMS where Major_System_Id in ('ASA','MNT','HLD') and  Major_System_Id in( select distinct(Major_System_Id) from SEC_MST_ROLE_MENUS where Role_Id in("+temp+"))";
				
			//	String MajorSystemDesc = "select Major_System_Short_Desc,Major_System_Id from SEC_MST_MAJOR_SYSTEMS where Major_System_Id in( select distinct(Major_System_Id) from SEC_MST_ROLE_MENUS where Role_Id in("+temp+"))    ";    
				ps = connection.prepareStatement(MajorSystemDesc);
				//ps.setInt(1,results.getInt("Major_System_Id"));
				m1 = ps.executeQuery();
				m11 += "<div id='tabs'><ul>"; 
				while (m1.next()) 
				{
					m11 += "<li><a href=testsubmenu.jsp?majorid="+m1.getString("Major_System_Id")+" target='content'>"
							+ m1.getString("Major_System_Short_Desc")+ " </a></li>\n";
				}
				m11 += "</ul><div>";
				String minor = "select * from SEC_MST_MINOR_SYSTEMS where  Minor_System_Id in (select distinct(Minor_System_Id) from SEC_MST_ROLE_MENUS where Role_Id in ("+temp+") and Major_System_Id='HRS')";
				ps = connection.prepareStatement(minor);
				results1 = ps.executeQuery();
				out.print(m11);
			} catch (Exception e) 
			{
				out.println("Errior" + e);
				response.sendRedirect("index.jsp?message=dbnill");
			}
			String msg = "", Office_id = "";
			userid = (String) session.getAttribute("UserId");
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID"," where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')");
			msg = obj.getValue("PMS_DCB_SETTING", "MESSAGE", "where OFFICE_ID="
				 + Office_id + "  ")+"<bR>"+obj.isNull(obj.getValue("PMS_DCB_SETTING", "MESSAGE_POSTING", "where OFFICE_ID="
				 + Office_id + "  "),5)+obj.isNull(obj.getValue("PMS_DCB_SETTING", "OB_MESSAGE", "where OFFICE_ID="
									+ Office_id + "  "),5);
			if (msg.equalsIgnoreCase("0"))
				msg = "<b>No Message</b>";
		%>
		</td>
	</tr>
</table>

				<%
				String v=request.getRequestURI();
          		if (v.lastIndexOf("ame")>0) 
          		{
          		}
          		else
          		{
          		%> 
          		<div class="sss1" id='msg2' style="vertical-align: baseline;border-radius :15px; "> 
          		<table  align="center" width="85%" border="1"  cellspacing="0" cellpadding="2">
				<tr bgcolor="white">
					<td align="center"> 
							<!-- <img src="images/msg.jpg" width="75" height="40" border="0"> -->
							<font color='black' size='3'><% if (Integer.parseInt(Office_id)!=5000) %>Important HO Instruction</font>
					</Td>
					<td align="left" width="5%"><a href="javascript:hide2()"><img src="images/cross.jpg" width="30" height="30" border="0" /></a></td>
				</tr>
				
				<tr bgcolor="#A52A2A" >
					<td colspan="2"><b><%=msg%></b></td>
				</tr>
				</table>
				</div>
				<% } %>
</body>
</html>
