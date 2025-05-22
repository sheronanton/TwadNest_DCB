<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.*,Servlets.Security.classes.UserProfile" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Reviews</title>
  <link href="../../../../../css/common.css" rel="stylesheet" media="screen"/>
  <script type="text/javascript">
  var flag="0";
  function check()
  {
  	var res=0;
  	var v=document.getElementById("desc").value;
  	 
  	 if (typeof(flag) == "undefined")
  	 {
  	 	flag=0;
  	 }
  	 
  	 if (flag==0)
  	 alert("select any one option"); 
  	  
  	if ((v!="") && (parseInt(flag)==1) )
  	{
  		res=true;
  		document.getElementById("Add").disabled=false;
  	}
  	else
  	{
  		res=false;  
  	}    
  	 
  	return res;     
  }
  function show()
  {
  	document.getElementById("rcorners52").innerHTML=" For issues entry as per procedure Go To Help Desk....";
    document.getElementById("Add").disabled=true;   
  }
  function view(a)
  {
  	flag=1;
    if (a==1)
    { 
    	document.getElementById("rcorners52").innerHTML="DCB Module is useful to your division. Could you please specify one or more reason ?  Example : Manual workload reduction, ledger report, review report generation, online bills to benficiaries, maintaining DCB records, collect arrears etc. Positve comments will be encouraging and appreciable.";
    }
    if (a==2) 
    {
    	document.getElementById("rcorners52").innerHTML=" If any Practical Suggestion you have about the DCB module please specify <br> Practical Suggestion useful to all divisions will be appreciable.";
    }
    if (a==3) 
    {
    	document.getElementById("rcorners52").innerHTML=" Practical Difficulties Your Division will face without Online DCB in completing DCB Work";
    }
  	document.getElementById("Add").disabled=false;   
  }
  </script>
</head>
<body>

<%
	 
	String userid="0",Office_id="",Office_Name="";
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Connection con=null;
	java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
	String pmonth="0",pyear="0";
	try
	{
	  con=obj.con();
	  obj.createStatement(con);
	  userid=(String)session.getAttribute("UserId");			
	  if(userid==null)
	  {
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
	  }
		UserProfile up = null;
		up = (UserProfile) session.getAttribute("UserProfile");
	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
	String but=""; 		
	but=obj.setValue("Add",request);
 	String b1=obj.setValue("b1",request);
 	int row=0;
	if (but.equals("Save"))
	 		{
	  						String desc=obj.setValue("desc",request);
	 
							java.util.Hashtable ht = new Hashtable();
												
							if (Integer.parseInt(b1)==1)					
							ht.put("SUGGESTION",    "'"+desc + "'");
							else if (Integer.parseInt(b1)==2)
							ht.put("ISSUE",    "'"+desc + "'");
							else if (Integer.parseInt(b1)==3)
							ht.put("USEFUL_MESSAGE",   "'"+desc + "'");
							else if (Integer.parseInt(b1)==4)
								ht.put("USEFUL_MESSAGE",   "'"+desc + "'");
							ht.put("OFFICE_ID", Office_id);
							
							ht.put("CONTACT_NAME",   "'" + up.getEmployeeName() + "'");
							ht.put("CONTACT_NO", "0");
							ht.put("UPDATED_BY_USER_ID", "'" + userid + "'");
							ht.put("UPDATED_DATE", "clock_timestamp()");
							
							// New code 
							String client=request.getRemoteAddr();
							
							//ht.put(table_column,value);
							ht.put("clientip", "'"+client+"'");
							
							row=obj.recordSave(ht ,"PMS_DCB_HELP_REPORT ", con);
							
							if (row > 0)
							{%>
							<script type="text/javascript">
								alert("Thanks !!");
							</script>
							<%
							}	
	 		}
%>
<form action="user_review.jsp" method="post" onsubmit="return check()" name="frm" >
<br> <center><div id='rcorners51'></div></center><br><br> 
<table class="tab1" align="center" width="65%" cellpadding="0" cellspacing="7" border="0"> 
		  
	<tr>
		<td align="left" style="width:25% ">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../../../../images/FEEDBACK.jpg" width="90" height="90">
		</td>		
		<td align="center"><font color='blue'>FeedBack&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>
	</tr>	
	<tr> 
		<td colspan="2" align="center" ><blockquote style="width: 690px;text-align: left;">Please share your valuable thought about DCB Module <Br><Br>
		Choose the option below as you wish and key in few comments about   Module usefulness</blockquote> 
		</td>
	</tr>
	<tr>
		<td  colspan="2"  align="center" style="color:#8904B1;font-style: italic;font-size: 1.4em;">     
			<input type="radio" name="b1" id="b1" value="1" onclick="view(1)">&nbsp;<font color=''>Usefulness</font>
			<input type="radio" name="b1" id="b1" value="2" onclick="view(2)" >&nbsp;Practical Suggestion 			
			<input type="radio" name="b1" id="b1" value="4" onclick="view(3)" >&nbsp;Opinion Without Online DCB
			<input type="radio" name="b1" id="b1" value="3" onclick="show()" >&nbsp;Issues
			
		</td>		 
	</tr>
	<tr> 
		<td colspan="2"> 
			 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Your Comments
		</td>
	</tr>
	<tr>
		<td  colspan="2" align="center"><textarea name="desc" id='desc' name="desc" onclick="document.getElementById('rcorners52').innerHTML=''"></textarea></td>
	</tr>
	<tr>
		<td colspan="2"  valign="top" align='center'><table><tr><td><input type="submit" value="Save" name="Add" id='Add' ></td><td><img src="../../../../../images/viewcomt.png" onclick="window.open('user_review_v.jsp')" width='105' height="25"></td><td><input type="button" value="Exit" onclick="javascript:window.close()"/></td></tr></table></td>

	</tr>
</table><br> 
 <center><div id='rcorners52'></div></center> 
   
 
</form>

<%
	}catch(Exception e)
	{
		out.println(e);  
	}

 %>

</body>
</html>