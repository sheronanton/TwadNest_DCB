<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Twad Board Intranet Services</title>




<script type='text/javascript' src='org/Security/scripts/twad.js'></script>
<link href='css/index.css' rel='stylesheet' media='screen'/>
<link href='css/doubletab2.css' rel='stylesheet' media='screen'/>
<script type='text/javascript' src='org/Security/scripts/StatusBarScrollText.js'></script>

<script type="text/javascript" src="jquery-3.6.0.js"></script>
<script type="text/javascript" src="index.js"></script>
<script type="text/javascript" src="ddaccordion.js">

/***********************************************
* Accordion Content script- (c) Dynamic Drive DHTML code library (www.dynamicdrive.com)
* Visit http://www.dynamicDrive.com for hundreds of DHTML scripts
* This notice must stay intact for legal use
***********************************************/

</script>


<script type="text/javascript">


ddaccordion.init({
	headerclass: "expandable", //Shared CSS class name of headers group that are expandable
	contentclass: "categoryitems", //Shared CSS class name of contents group
	revealtype: "click", //Reveal content when user clicks or onmouseover the header? Valid value: "click", "clickgo", or "mouseover"
	mouseoverdelay: 200, //if revealtype="mouseover", set delay in milliseconds before header expands onMouseover
	collapseprev: true, //Collapse previous content (so only one open at any time)? true/false 
	defaultexpanded: [0], //index of content(s) open by default [index1, index2, etc]. [] denotes no content
	onemustopen: false, //Specify whether at least one header should be open always (so never all headers closed)
	animatedefault: false, //Should contents open by default be animated into view?
	persiststate: true, //persist state of opened contents within browser session?
	toggleclass: ["", "openheader"], //Two CSS classes to be applied to the header when it's collapsed and expanded, respectively ["class1", "class2"]
	togglehtml: ["prefix", "", ""], //Additional HTML added to the header when it's collapsed and expanded, respectively  ["position", "html1", "html2"] (see docs)
	animatespeed: "medium", //speed of animation: integer in milliseconds (ie: 200), or keywords "fast", "normal", or "slow"
	oninit:function(headers, expandedindices){ //custom code to run when headers have initalized
		//do nothing
	},
	onopenclose:function(header, index, state, isuseractivated){ //custom code to run whenever a header is opened or closed
		//do nothing
	}
})


</script>

<style type="text/css">
			
			.arrowlistmenu{
			width: 280px; /*width of accordion menu*/
			align: center; 
			}
			
			.arrowlistmenu .menuheader{ /*CSS class for menu headers in general (expanding or not!)*/
			font: bold 14px Arial;
			color: white;
			background: #6C9FCC url(images/bluebg1.jpg) 50% bottom repeat-x;
			  -moz-border-radius: .3em .3em .3em .3em;
			margin-bottom: 10px; /*bottom spacing between header and rest of content*/
			text-transform: uppercase;
			padding: 1px 0 4px 10px; /*header text is indented 10px*/
			cursor: hand;
			cursor: pointer;
			}
			
			.arrowlistmenu .openheader{ /*CSS class to apply to expandable header when it's expanded*/
			background-image: url(titlebar-active.png);
			}
			
			.arrowlistmenu ul{ /*CSS for UL of each sub menu*/
			list-style-type: none;
			margin: 0;
			padding: 0;
			margin-bottom: 8px; /*bottom spacing between each UL and rest of content*/
			}
			
			.arrowlistmenu ul li{
			padding-bottom: 2px; /*bottom spacing between menu items*/
			}
			
			.arrowlistmenu ul li a{
			color: blue;
			background: url(arrowbullet.png) no-repeat center left; /*custom bullet list image*/
			display: block;
			padding: 2px 0;
			padding-left: 19px; /*link text is indented 19px*/
			text-decoration: none;
			font-weight: bold;
			border-bottom: 1px solid #dadada;
			font-size: 90%;
			}
			
			.arrowlistmenu ul li a:visited{
			color: #194F04;
			}
			.arrowlistmenu ul li a:active{
			color: white;
			background-color: #DB90DE;
			}
			.arrowlistmenu ul li a:hover{ /*hover state CSS*/
			color: white;
			background-color: #9909DB;
			}

</style>
</head>
<body onload="framesclear();">
<div class="arrowlistmenu">

<%
		int empid=0;
		String status="";
		String Remote_host="";
		String sess_id="";
		String strhostname="";
		boolean flag_chk=true;


		try
		{
		  flag_chk=request.isRequestedSessionIdFromCookie();
		  Remote_host=request.getRemoteHost();
		   session=request.getSession(false);
		   session.setMaxInactiveInterval(36000);
			   if(session!=null) 
			   {
			            String sessionid=request.getParameter("session");
			           empid=Integer.parseInt(request.getParameter("empid"));
			           status=request.getParameter("status");
			   }
		}
		catch(Exception e)
		{
			System.out.println("testsubmenu----156");
		  //e.printStackTrace();
		}

session=request.getSession(false);

	try	
	{
		if(session ==null) {
        		response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
        		return;
    		}
    	if(!session.isNew()) 
    	{
       		if(session.getAttribute("UserProfile")==null) 
       		{
             session.invalidate(); 
             session=null;
             response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
         	}
     	}
     }
    catch(Exception e){
            response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
    }
    sess_id=session.getId();
    
		    UserProfile up=null;
		    up=(UserProfile)session.getAttribute("UserProfile");
		    String profile=(String)session.getAttribute("profile");
		    String sqlGetRoleId="";

			Connection connection=null;
			ResultSet rs=null;
			PreparedStatement ps=null;
			ResultSet results=null;
			ResultSet cntt,m1=null;
			ResultSet results1=null;
			ResultSet results2=null;
			ResultSet results3=null;
			ResultSet results4=null;
		try
		{ 
		   	     	
		   ResourceBundle  rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
		     
		       String ConnectionString="";
		        String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
		        String strdsn=rb.getString("Config.DSN");
		          strhostname=rb.getString("Config.HOST_NAME");
		        String strportno=rb.getString("Config.PORT_NUMBER");
		        String strsid=rb.getString("Config.SID");
		        String strdbusername=rb.getString("Config.USER_NAME");
		        String strdbpassword=rb.getString("Config.PASSWORD");
		
		  //      ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
		   ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
		
		         Class.forName(strDriver.trim());
		         Controller obj=new Controller(); 
		         connection=obj.con();
		         //connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		}
		catch(Exception e)
		{
			System.out.println("Exception in connection...."+e);
			response.sendRedirect(request.getContextPath()+ "/index.jsp?message=dbnill");
		}
try{
		String  m11="";
		String temp="";
		int flag=0;
		int i=1;
		String compareMenu="menu";
		try
		{
			if(profile.equalsIgnoreCase("twad")) {
			    sqlGetRoleId="select Role_Id from SEC_MST_USER_ROLES where Employee_Id=? order by List_Seq_No";
			   
			    ps=connection.prepareStatement(sqlGetRoleId);
			    ps.setInt(1,up.getEmployeeId());
			}
			else{
			    String userid=(String)session.getAttribute("UserId");
		            System.out.println("userid::"+userid);
			    sqlGetRoleId="select Role_Id from SEC_MST_OTHER_USER_ROLES where USER_ID=? order by List_Seq_No";
			    
			    ps=connection.prepareStatement(sqlGetRoleId);
			    ps.setString(1,userid);
			}
		 
					results=ps.executeQuery();
					while(results.next())
					{
							if(temp==""){
								temp=new Integer(results.getInt("Role_Id")).toString();
							}else{
								temp+=","+new Integer(results.getInt("Role_Id")).toString();
							}
					}
				}
			catch(Exception e){
			 
			}		
			String cnt="select count(*) as cnt1 from(select distinct(Major_System_Id) from SEC_MST_ROLE_MENUS where Role_Id in ("+temp+")	) ";
			try
			{
					ps=connection.prepareStatement(cnt);
					cntt=ps.executeQuery();
					cntt.next();
			}
			catch(Exception e)
			{
			}
			String major_id="";
			StringBuffer menucategoryCheck=new StringBuffer();
			StringBuffer menusubCheck=new StringBuffer();
			try
				{
					major_id=request.getParameter("majorid");
				}
				catch(Exception e)
				{
				}
			String minor="select * from SEC_MST_MINOR_SYSTEMS where  Minor_System_Id in (select distinct(Minor_System_Id) from SEC_MST_ROLE_MENUS where Role_Id in ("+temp+") and Major_System_Id='"+major_id+"' )";
 		    ps=connection.prepareStatement(minor); 
            results1=ps.executeQuery();
		   	System.out.println("strhostname" + strhostname);
		//   	String serverstatus=strhostname.equalsIgnoreCase("10.163.31.20") ? "" : "<h6 style='text-align: right;color: red;height: 0.6em;'><font size='2'>TEST SITE</font></h3>";
 		  //m11+="<div class='arrowlistmenu'>";
 		//  			m11+=serverstatus;     
                     while(results1.next())
                     {
                        m11+="<h3 class='menuheader expandable'>"+results1.getString("Minor_System_Short_Desc")+"</h3><ul class='categoryitems'>"; 
 						String subsystem="select * from SEC_MST_SUBSYSTEMS where sub_system_id in(select distinct(sub_system_id) from SEC_MST_ROLE_MENUS where Role_Id in("+temp+") and Major_System_Id='"+results1.getString("Major_System_Id")+"' and minor_system_id='"+results1.getString("Minor_System_Id")+"') and Major_System_Id='"+results1.getString("Major_System_Id")+"' and minor_system_id='"+results1.getString("Minor_System_Id")+"' order by LIST_SEQ_NO";
 						ps=connection.prepareStatement(subsystem); 
 						results2=ps.executeQuery();
                                while(results2.next())
                                { 
                                    m11+="<li><a href='testmenufile.jsp?major_id="+major_id+"&subid="+results2.getString("sub_system_id")+"&minor_id="+results1.getString("Minor_System_Id")+"' target='menu'>"+results2.getString("Sub_System_Short_Desc")+"</a></li>";
                                 }
                                   m11+="</ul>";  
                       }
	 // m11+="</div>";  
	out.print(m11);
}catch(Exception e){ 
response.sendRedirect("index.jsp?message=dbnill");
}
%>
</div>
</body>
</html>