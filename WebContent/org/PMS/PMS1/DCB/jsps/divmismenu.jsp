<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MIS MENU</title>
<style type="text/css">
/* menu styles */
#jsddm
{	margin: 0;
	padding: 0}

	#jsddm li
	{	float: left;
		list-style: none;
		font: 12px Tahoma, Arial}

	#jsddm li a
	{	display: block;
		background: #324143;
		padding: 5px 12px;
		text-decoration: none;
		border-right: 1px solid white;
		width: 70px;
		color: #EAFFED;
		white-space: nowrap}

	#jsddm li a:hover
	{	background: #24313C}
		
		#jsddm li ul
		{	margin: 0;
			padding: 0;
			position: absolute;
			visibility: hidden;
			border-top: 1px solid white}
		
			#jsddm li ul li
			{	float: none;
				display: inline}
			
			#jsddm li ul li a
			{	width: auto;
				background: #A9C251;
				color: #24313C}
			
			#jsddm li ul li a:hover
			{	background: #8EA344}
</style>
<style type="text/css"> 
	
	/* common page styles */
	body
	{	background: #6595A3;
		padding: 0 20px}
 
	.clear
	{	clear: both;
		overflow: hidden;
		height: 0}
 
	#all
	{	width: 80%;
		min-width: 650px;
		margin: 40px auto 0 auto;
		background: #FCFFED;
		padding: 20px 35px}
 
	h1
	{	font: 26px tahoma, arial;
		color: #324143}
 
	p
	{	font: 12px tahoma, arial;
		color: #171F26;
		margin-bottom: 25px}
 
	a
	{	color: #324143}
 
	#copyright
	{	width: 80%;
		min-width: 650px;
		margin: 0 auto;
		padding: 20px 35px;
		background: #B6C28B;
		font: 12px tahoma, arial;
		color: #324143}
 
	#copyright a
	{	color: #324143}
 
	#copyright a:hover
	{	color: #171F26}
	</style> 
	<script src="jquery-3.6.0.js" type="text/javascript"></script> 
<script type="text/javascript"> 
var timeout         = 500;
var closetimer		= 0;
var ddmenuitem      = 0;
 
function jsddm_open()
{	jsddm_canceltimer();
	jsddm_close();
	ddmenuitem = $(this).find('ul').eq(0).css('visibility', 'visible');}
 
function jsddm_close()
{	if(ddmenuitem) ddmenuitem.css('visibility', 'hidden');}
 
function jsddm_timer()
{	closetimer = window.setTimeout(jsddm_close, timeout);}
 
function jsddm_canceltimer()
{	if(closetimer)
	{	window.clearTimeout(closetimer);
		closetimer = null;}}
 
$(document).ready(function()
{	$('#jsddm > li').bind('mouseover', jsddm_open);
	$('#jsddm > li').bind('mouseout',  jsddm_timer);});
 
document.onclick = jsddm_close;
</script>
<script type="text/javascript">
function fun(s,a)
{
	if (s==1)
	 document.getElementById('f').src = "mis_input.jsp";
	else if (s==2)
		 document.getElementById('f').src = "div_mis.jsp";
	else if (s==3)
		 document.getElementById('f').src = "ben_receipt.jsp";	
	else if (s==4)
		 document.getElementById('f').src = "data_not_submit.jsp";
	else if (s==5)
	{
		 var fyear=document.getElementById('year').value;
	 	var fmonth=document.getElementById('month').value;
	 
      window.open("../../../../../Bill_Demand?command=Collection2&fmonth="+fmonth+"&fyear="+fyear+"&bentype="+a);
	}else if (s==7) {
		 	 var fyear=document.getElementById('year').value;
	 	var fmonth=document.getElementById('month').value;
	 
      window.open("../../../../../Bill_Demand?command=UnitwiseAbstrct&fmonth="+fmonth+"&fyear="+fyear+"&bentype="+a);
	}	 
	else if (s==6)
	{
	
	if (a==0)
	{
			window.open("schbentype_mis.jsp");
	}else
	{
		var fyear=document.getElementById('year').value;
	 	var fmonth=document.getElementById('month').value;
	 
      window.open("../../../../../Bill_Demand?command=Collection3&fmonth="+fmonth+"&fyear="+fyear+"&bentype="+a);
	}
	}
}
</script> 
 </head>
 <%
 String userid="0",Office_id="",Office_Name="",smonth="",syear="";
 Controller obj=new Controller();
 Connection con;
 try
{
	con=obj.con();
	obj.createStatement(con);
	try
	{
	   userid=(String)session.getAttribute("UserId");
	}catch(Exception e) {userid="0";}
	if(userid==null)
	{
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	if (Office_id.equals("")) Office_id="0";
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
	try
	{
	  smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
	  syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
	}catch(Exception e) {
	out.println(e);
	 //response.sendRedirect(request.getContextPath()+"/index.jsp");
}
	
	obj.conClose(con);
}catch(Exception e) {
	out.println(e);
	// response.sendRedirect(request.getContextPath()+"/index.jsp");
}		
  %>
  <table>
 <tr>
 		<td >
 			<img src="../../../../../images/twadnest.png" />	
 		</td>

 <td>
 <ul id="jsddm">
 	<!--<li><a href="#">Setting</a>
 		<ul>
 		  <li><a href="javascript:fun(1)" >Month Setting</a></li>
 		</ul>
 	</li>
 	  <li><a href="#">Basic Reports</a>
 		<ul>
 		  <li><a href="javascript:fun(3)" >Receipts</a></li>
 		  <li><a href="javascript:fun(4)" >Pumping Missing</a></li>
 		</ul>
 	</li> -->     
    <li><a href="#" style="width: 2000"><center>DCB</center></a> 
    
        <ul>
        
             
            <li><a href="javascript:fun(2,0)">Water Charges Due :: Unit Wise</a></li>
            <li><a href="javascript:fun(5,6)">Water Charges Due from Village Panchayats</a></li>
            <li><a href="javascript:fun(5,1)">Water Charges Due from Corporations</a></li>
            <li><a href="javascript:fun(5,3)">Water Charges Due from Municipalities</a></li>
            <li><a href="javascript:fun(6,0)">Scheme Type - Beneficiary Type </a></li>
            <li><a href="javascript:fun(7,0)">Unitwise Abstract</a></li>
            <li><a href="../reports/jsps/allben_with_met.jsp">Region Wise Report </a></li>
            <li><a href="./pun_.jsp">Urban Master Status </a></li>
           
              
            
        </ul>
    </li>    
    &nbsp;
    <li><center><a href="Book3.jsp">Help</a></center></li>
    <li class="tdText"><center><a href="javascript:window.close()">Exit</a></center></li>
</ul>    
</td>
     </tr>

</table>
 <table width="100%" border="1" height="100%">
 <tr>
  
<td style="height: 860px; width: 753px">
<iframe id='f' frameborder="1" width="100%" height="100%">
 
 </iframe>
 </td>
 </tr>
 </table> 
<div class="clear"> </div>
 
 
 <input type=hidden name="month" id="month" value="<%=smonth%>"></input>
<input type=hidden name="year" id="year" value="<%=syear %>"></input>
</html>