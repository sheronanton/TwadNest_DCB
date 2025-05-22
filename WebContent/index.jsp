<%@ page contentType="text/html;charset=iso-8859-1" session="false"%>
<%@ page import="java.util.*" %>


 
<%@page import="Servlets.PMS.PMS1.DCB.servlets.app"%><html>
  <head>
   <meta  http-equiv="Content-Type" content="text/html; charset=iso-8859-1 ; "></meta>
  	<title>TWAD Nest - Phase II welcomes you...</title>
     <style type="text/css"></style>
     <script type="text/javascript">
     	function focusget()
     	{
         var val=document.getElementById("captchaval").value;
         if (val=="")
        	 document.getElementById("captchaval").value="Enter Displayed Code";
        }
     	function focusloss()
     	{
     		document.getElementById("captchaval").value="";
        }
     </script>
     <link href="images/style.css" rel="stylesheet" type="text/css" /> 
    <script type="text/javascript"  src="index.js">     </script>
    <script type="text/javascript"  src="org/Security/scripts/twad.js">     </script>
     
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<link href="css/newstyle.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    
<!--  
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.bg {
    background-image: url(images/index_bg.jpg);
    background-repeat: no-repeat;
    background-position: center top;
}
-->   
</style></head>
  
 <body onload="focusget()">
 <%  
 
 %>
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr>
    <td height="599" align="center" valign="top" class="bg"><br />
        <br /> 
        <br />
        <br />   
        <br />
       
                   
      <img src="images/title.png" alt="" width="420" height="139" /> <br />
        <br />&nbsp; 
        <table width="291" border="0" cellspacing="0" cellpadding="0" bordercolor="skyblue">
          	<tr><td align="center"><font color='#DA18F4' size='5'><b><strong>
          	<%
          		String v=request.getRequestURI();
          		if (v.lastIndexOf("ame")>0) 
          		{
          	 %> <img src="./AME.png" width="70" height="30"> 
          	  <% } else  {%>
          	   <img src="./DCB.png" width="70" height="30">
          	  <%} %>	
          	</strong></b></font></td></tr>
          </table>   <br /> 
          <table width="296" border="0" cellspacing="0" cellpadding="0" bordercolor="skyblue">
      <tr>
        <td height="145" align="center" valign="middle" background="images/login_bg.jpg">
        <form name="frmindex" id="frmindex" ><br>
        <table width="95%" border="0" cellspacing="0" cellpadding="4" align="center" style="height: 7em;"> 
          <tr>   
                            <td width="35%" height="25" align="left" valign="middle"> 
                            	 <b><font color="white" size="2">&nbsp;User Name</font></b>
                            </td>
                            <td width="15%" align="left" valign="middle">
                            <input type="text" name="txtID" id="txtID"" tabindex="1" style="color: #4B4B4B; font-size: 12;width: 113px;" class="textbox"   title="Enter UserName" maxlength="12" size="12"  ></input>
                            </td> 
                          </tr> 
                          <tr> 
                            <td height="25"  width="35%" align="left" valign="middle" >
                            <b><font color="white" size="2">&nbsp;Password</font></b></td>
                            <td align="left" valign="middle">
                            <input type="password" name="txtPassword" id="txtPassword" style="color: #4B4B4B; font-size: 12;width: 113px;" tabindex="2"   class="textbox"    maxlength="12" size="12" onblur="killChars(this);stripQuotes(this);"  title="Enter Password"></input>
                            </td>
                          </tr> 
                          
                         
          <tr >
				<td width="45%" height="30%"  valign="bottom" align="left"  ><img src="captcha.jsp" style="height: 21px; width: 100px;" />
					&nbsp;&nbsp;<a href="index.jsp" onclick="Reload()"><img src="images/refresh_capcha.png" style="height: 16px; width: 15px; border: 0; color: white;font-size-adjust: none"
 title="Change Code" /></a></td>
				<td>
					<input type="text" style="color: #4B4B4B; font-size: 11;width: 113px;"  name="captchaval" id="captchaval" tabindex="3" onblur="focusget()"  onclick="focusloss()"  onKeyPress="return buttonsubmit(event,this)" id="captchaval" class="textbox"  size="12"  maxlength="7" title="Enter Code"  >
				</td>
		  </tr>   
		  
         
		    <tr>
                             
                            <td align="center" valign="bottom" colspan="2">
                           <a id="loginid" href="#" onKeyPress="return buttonsubmit(event)"  tabindex="4" >                            
                            <img src="images/button.gif"  onClick="return notNull()"  name="butSubmit" alt="Login" width="60" 
                                          height="18" border="0" /></a>
       						</td>   </tr>
        </table>
        </form></td>
      </tr>
    </table></td>
  </tr> 
  <tr>
    <td height="30" align="center" valign="bottom" bgcolor="#176B9A"><table width="714" border="0" cellspacing="0" cellpadding="0">
      
       <td height="30" background="images1/footerbg.jpg" align="center" valign="middle" style="background-repeat: no-repeat;">
<span class="arialwhitebold">
Designed &amp; Developed by
<a class="arialwhitebold" target="_blank" href="http://www.tn.nic.in">National Informatics Centre, Chennai</a>
</span>
</td>
    </table></td>
  </tr>
</table>
<input type=hidden value="1" id="f" name="f">
</body>
</html>
<%
System.out.println("test");
try
{

   String message=request.getParameter("message"); 
   System.out.println("Message:"+message);
   request.setAttribute("message","");
   if(message!=null)
   {
           if(message.equals("yes"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Invalid User name or Password</label>");    
        out.println("<script language='javascript'>alert('Invalid User name or Password.');</script>");
           }
           else if(message.equals("noprofile"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert('Your profile has to be updated.\\n Please contact System Administrator.');</script>");
           }
           else if(message.equals("retired"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert('Login is disabled based on Employee Retirement data.');</script>");
           }
           else if(message.equals("dbnill"))
           {            
        //out.println("<label style='color:rgb(255,0,0);'>Your profile has to\nbe updated. Please contact \nSystem Administrator.</label>");    
        out.println("<script language='javascript'>alert(\"Database Service is not available.\\n Please Contact System Administrator.\");</script>");
           }
            else if(message.equals("logindisabled"))
           {            
                out.println("<script language='javascript'>alert(\"Login Disabled.\");</script>");
           }
    }
}catch(Exception e)
{
                // while loading first
}
%>