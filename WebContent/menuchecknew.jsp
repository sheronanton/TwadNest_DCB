<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
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
 
<script type="text/javascript">
	function msg1() 
	{
		var t1 = document.getElementById("t1").value;
		var t2 = document.getElementById("t2").value;
		var t3 = document.getElementById("t3").value;
		var msg = "";
		//\n 1. Check and Freeze Water Charges for Automatic Water Charges Journal Creation through FAS.";
		//msg+="\n\ 2. Opening Balance Entry for all  Beneficiaries should be complete for successfull bill generation.";
		//msg+="\n\ 3. Upload DCB Ledger Data every month for Ledger Statements.";
		//msg+="\n 1. Pumping Return(PR) Freeze and Water Charge Calculation process \n  has been seperated w.e.f Feb 2012 for performance tuning  ";
		//msg+="\n\n 2. Goto Water Charge(WC) Calculation Option for  WC Calculation. Refer Information   ";
		//msg+="\n\n 3. Pumping Return Freeze has been modified to Beneficary Type wise Freeze ";
		//msg+="\n\n Please go through FAQ for any doubts....";
		//msg+="\n\n New Reports Hosted under DCB Ledger   ";
		// msg+="\n Update OB as on 01/04/2012.. Refer FAQ.";
		//msg+="\n Generate Demand every Month for next month Automatic OB.";
		//msg+="\n Upload Ledger Data every Month for DCB Ledger Statements.";  
		//msg+="\n\n \t \t\t New Reports \n\n 1. Scheme Wise Water Charges Journal...Refer Water Charges Journal under Reports.";
		//msg+="\n\n 2. Bill Generation Check List under Demand Notice Generation.\n  ";          
		//msg+="\n\n 2. Pumping Return NOT Received Beneficiary List";
		//msg+="\n \..............................................................................................................................\n \t\t\t Please send your valuable feedback";
		// msg+="\n\n Refer FAQ for Instructions and doubt clarification....";
		// msg+="\n\n Checking Water Charges feature during Pumping Return Entry has been provided";
		//	 msg+="\n\n Give feedback. "
		//  msg+="\n\n Check OB report for the bill month and year before Bill Generation process";
		msg + "\n Welcome " + t1 + t2 + t3;
		msg += "\n \n View Check List before Uploading DCB Monthly Ledger Data \n \n";
		alert("DCB Message : \n***************" + msg);
	}
   
	function cls()
	{
		alert("test");
		document.getElementById("LeftHtmlContainer").style.visibility="hidden";
		document.getElementById("LeftHtmlContainer"),innerHTML="";
	}
 
					 
	 
</script>
</head>  
<form>
<%

	try {
		response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0
		response.setDateHeader("Expires", 0); //prevent caching at the proxy server
		int empid = 0;
		String status = "";
		String Remote_host = "";
		String sess_id = "";
		boolean flag_chk = true;
		try {
			flag_chk = request.isRequestedSessionIdFromCookie();
			Remote_host = request.getRemoteHost();
			session = request.getSession(false);
			if (session != null) 
			{
				String sessionid = request.getParameter("session");
				empid = Integer.parseInt(request.getParameter("empid"));
				status = request.getParameter("status");
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		session = request.getSession(false);
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
		//	ConnectionString = strdsn.trim() + "@" + strhostname.trim()+ ":" + strportno.trim() + ":" + strsid.trim();
		 	ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,strdbusername.trim(), strdbpassword.trim());
			} catch (Exception e) {
			System.out.println("Exception in connection...." + e);
			response.sendRedirect(request.getContextPath()+ "/index.jsp?message=dbnill");
			}
%>  
<frameset rows="185,*" name="framehead" id="framehead" frameborder="0" border="0" framespacing="0" >
	<frame name="top_header" id="top_header" src="testheader.jsp"></frame>
	<frameset cols="300,*" frameborder="0" border="0" framespacing="0">
		<frame name="content" id="content" src="testsubmenu.jsp" marginheight="0" marginwidth="0" horizoantalscrolling="auto"  noresize="noresize">
		<frame name="menu" src="testmenufile.jsp" marginheight="0" marginwidth="0" noresize="noresize">
	</frameset>
</frameset>
 
 
<%
	} catch (Exception e) 
	{
		response.sendRedirect("index.jsp");
	}
%>
</form>
</html>