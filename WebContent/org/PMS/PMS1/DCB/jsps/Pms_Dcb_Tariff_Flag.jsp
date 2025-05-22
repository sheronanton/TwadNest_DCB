<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.Security.classes.UserProfile,java.sql.*,Servlets.PMS.PMS1.DCB.servlets.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tariff Rate Settings | TWAD Nest - Phase II </title>
<link href='../../../../../css/Sample3.css' rel='stylesheet'  media='screen'/>
<script type="text/javascript"  src="../scripts/Pms_Dcb_Tariff_Slab.js"></script>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<style type="text/css">
					table.border1
					{
					color:#000000;
					padding:0px;
					border-top: 1px solid #dddddd;
					border-left: 1px solid #dddddd;
					border-bottom: 0px solid #dddddd;
					border-right: 0px solid #dddddd;
					font-size: 14  px;
					
					}
					
					table.border1 th, table.border1 td 
					{
					padding:5px;
					padding-bottom:2px;
					border-top: 0px solid #dddddd;
					border-left: 0px solid #dddddd;
					border-bottom: 1px solid #dddddd;
					border-right: 1px solid #dddddd;
					}
										
				</style>
</head>
<body onload="loadbeneficiarytype() ,flash()">
<%
	String userid="112",Office_id="",Office_Name="";
	Servlets.PMS.PMS1.DCB.servlets.Controller obj=new Servlets.PMS.PMS1.DCB.servlets.Controller();
	java.sql.Connection con=null;
	try
	{     
		con=obj.con();
		obj.createStatement(con);
		userid=(String)session.getAttribute("UserId");
		
		if(userid==null)
		{
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID  from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	}catch(Exception e) 
	{
		
	}
	
%>


<form name="tariff_flag">
<table  class="fb2" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  width="90%" align="center" cellpadding="5" cellspacing="0"  >
<tr   class="tdH" style="color:black">
	<td class="tdText" colspan="4" align="center">
		<b>Tariff Type Settings - <%=Office_Name%></b>
	</td>                      
</tr>
<tr class="tdText"  >
	<td style="padding-left:10px;"    width="20%">Beneficiary Type<font color="#ff2121">*</font></td>
    <td style="padding-left:10px;" colspan="2">
		<select id="Beneficiary_type"  onChange="loadbeneficiaryname();" class="select">
			<option value="0" selected="selected">- - - Select - - -</option>
		</select><input type="hidden" name="Exit" value="View Tariff Settings " class="fb2" id="report" onclick="report_();"><label id="msg"></label>
     </td>
</tr>
<tr class="tdText" >
	<td style="padding-left:10px;"  width="20%"	>Beneficiary Name<font color="#ff2121">*</font></td>
	<td style="padding-left:10px;">
		<select id="Beneficiary_Name"  name="Beneficiary_Name" class="select" onchange="loadschemes();rateshow('show');">
     		<option value="0" selected="selected">- - - Select - - -</option>
	     </select> 
	      <input type="hidden" name="op" id="op" value="2"> 
	</td> 
</tr>
<tr  class="tdText" >
		<td> 
			
			&nbsp;Active Interest Rate 
		</td>
		<td>
			<!--Done by Sheron on November-11-2024 to prevent users from updating the interest rate>
			<!-- &nbsp;<input type="text" id="rate" name="rate" class="tb1"> <input type="button" value="Update " class="fb2" onclick="rateshow('update')">-->
		</td>
</tr>

<!-- 
<tr class="tdText" >
	<td style="padding-left:10px;"  width="20%">Mode of Tariff <font color="#ff2121">*</font></td>
	<td style="padding-left:10px;">
		   <input type="radio" name="op" id="op" checked="checked" value="1"> Beneficiary   <input type="radio"  id="op"  value=2 name="op" onclick="loadschemes();">  Scheme/Location
	</td>
</tr>
-->
 	 
</table>
<br></br>
 <input type=hidden id="pr_status" name="pr_status" value="1"> 
 <table  class="fb2" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  border="0" width="90%" align="center" cellpadding="0" cellspacing="0"   id="my_div">

 	<tr  class="tdH">
    	<th class="tdText"  width="60%">Scheme</th>
        <th  class="tdText">Tariff mode</th>
      
   </tr>
   
   <tbody id="valuerows" ></tbody>
   	
  
  </table>
  <Br><br>
  <table style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"   width="90%" align="center" cellpadding="0" cellspacing="0"   id="my_div">
   <tr>
   	<td align="center">
   	  	<input type="button" name="Save" value="Submit" id="cmdsave" onclick="funsave()";  class="fb2" >
   	  	 <input type="button" name="Exit" value="Exit" id="exit" onclick="exitwindow();"  class="fb2" />
   	  	  <input type="button" name="Exit" value="View Location Settings" id="report" onclick="report_();"  class="fb2" />
   	  	  <input type="button" name="Refresh" value="Refresh" id="Refresh" onclick="refreshval();"  class="fb2" /><img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=8','mywindow','scrollbars=yes')"> 
   	</td>
   </tr>
  </table>
  
    <table style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"   id="ben_loc_data" width="75%" align="center" border="1"   cellspacing="0" cellpadding="2"  >
  	<tbody id="ben_loc_flag" align="center" style="bgcolor: #E3E4FA"  ></tbody>
    </table>
  
  
</form>
</body>
</html>