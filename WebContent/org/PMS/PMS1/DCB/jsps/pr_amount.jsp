<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="Servlets.Security.classes.UserProfile"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Beneficiary Water Charges | TWAD Nest - Phase II</title>
</head>
<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<body onload="list('pr_select',1,0),flash(),startclock()">
<%
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) ;

int year = cal.get(Calendar.YEAR);

 if (month >=4)
	year=year;
else
	year=year;

int pumpingfalg=0;
/*

	flag from setting table
	pumpingfalg=1

*/

String Date_dis=day+"/"+(month+1)+"/"+year,oid="";
String userid="0",Office_id="",Office_Name="";
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
	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	if (Office_id.equals("")) Office_id="0";
	
		  oid=obj.setValue("oid",request); 		
 		if (!oid.trim().equalsIgnoreCase("0"))
 			Office_id=oid;
 			
	
	 Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		String flag="0",smonth="0",syear="0";
 		  try
 		  {
 		 		flag=session.getAttribute("dcbflag").toString();
 		  } catch(Exception e) {
 		  	flag="0";
 		  }
 		  if (flag.equalsIgnoreCase("")) flag="0"; 
 		 String arg_month=obj.setValue("arg_month",request);
  		 String arg_year=obj.setValue("arg_year",request);
  		String pmonth="",pyear="";
  		if (!arg_month.equalsIgnoreCase("0"))
  		{
  			month=Integer.parseInt(arg_month);
  			year=Integer.parseInt(arg_year); 
  			smonth=arg_month;
 			syear=arg_year;
  		}
  		else
  		{ 
			 		  if (flag.equalsIgnoreCase("1"))
			 		  {
			 		   	String dcbmonth=session.getAttribute("dcbmonth").toString();
			 		  	String dcbyear=session.getAttribute("dcbyear").toString();		  	
			 			smonth=dcbmonth;
			 		  	syear=dcbyear;
			 		  }
			 		  else
			 		  {
			 		   		smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
			 		   		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
			 		     if (smonth.equalsIgnoreCase("0"))
							 month=month;
								else
							 month=Integer.parseInt(smonth);
				 
							 if (syear.equalsIgnoreCase("0"))
							  year= year;
							else
							 year=Integer.parseInt(syear);
			 		  }
				 			pmonth=obj.setValue("pmonth",request);
				 			pyear=obj.setValue("pyear",request);
				 			
				 			if (!pmonth.trim().equalsIgnoreCase("0")) 		month=Integer.parseInt(pmonth);   
							if (!pyear.trim().equalsIgnoreCase("0")) 		year=Integer.parseInt(pyear);
	
  		}
	
	obj.conClose(con);  
}catch(Exception e) {
	
	response.sendRedirect(request.getContextPath()+"/index.jsp");
}	

 		 String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	   
%>
 <input type="hidden" id="month" value="<%=month%>">
<input type="hidden" id="year" value="<%=year%>"> 
 <form> 
<table  class="fb2" style="BORDER-COLLAPSE: collapse;vertical-align: top;" border="1" borderColor="#92c2d8"  width="75%" cellpadding="5"  cellspacing="0" align="center">
<tr  class="tdText">
<td align="center" colspan="4"><font size="3">Beneficiary Water Charges - <%=Office_Name%></font> <label id="msg"></label> 
</td>
</tr>
 <tr style="height: 30pt"  > 
    <td colspan="4" align="left" class="tdText"><font size="2" > Month and Year : </font><font size="2" color='blue'> <%=cmonth[month]%>&nbsp; and &nbsp;<%=year%> </font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label id="time" class="tb4"></label></td>
</tr>
<tr>
</tr>
</table>
<table  class="fb2" width="75%"   style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" id="pr_ben_data"  border="1"  align="center"  cellspacing="0" cellpadding="3">
<tr>
	<td>
		<table  class="fb2" width="100%"   style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" id="pr_ben_data"  border="1"  align="center"  cellspacing="0" cellpadding="3">
				<Tr class="tdH" valign="top"  >
							<td align=center valign="top"  width="5%"><font class="tdText"  >Sl.No</font> </td>
 				 			<td align=center valign="top" ><font class="tdText"  > Beneficiary Name </font> </td>
							<td align=center valign="top" width=17%><font class="tdText"  > Beneficiary Type </font> </td>
							<td align=center valign="top" width="15%"><font class="tdText"  > Previous Net Consumption<font class="tdText"><bR></font>[KL]</font> </td>
							<td align=center valign="top" width="15%" nowrap="nowrap"><font class="tdText">Current<Br>Net Consumption<font class="tdText"><bR></font>[KL]</font> </td>
							<td align=center valign="top" width="16%" nowrap="nowrap"><font class="tdText"  >Net Consumption<bR> Charges (RS)</font> </td>
 				</tr> 
 		</table>
	</td>
</tr>
<tr>
<td >
<div id='scroll_clipper' style='position:relative; width:99%; border-height:1px; height: 450px; overflow:auto;white-space:nowrap;'>
<div id='scroll_text'  >  
<table  class="fb2" width="100%"   style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" id="pr_ben_data"  border="1"  align="center"  cellspacing="0" cellpadding="3">
				<tbody class="table" id="pr_ben_body" align="left"   ></tbody>
</table>  
</div>
</div>
</td>
</tr>
</table>
<table  class="fb2" width="75%"   style="BORDER-COLLAPSE: collapse" border="0" borderColor="#92c2d8" id="pr_ben_data"  border="1"  align="center"  cellspacing="0" cellpadding="3">
<Tr>
				<td colspan="2">
						<select id="pr_type" class="select">  
						<option value="1">   Select Print Format   </option>
						<option value="1">   PDF   </option>
						<option value="2">   EXCEL  </option>
						</select>
					</td>
					<td style="color: blue;font-weight: bold" align="right" width="15%">Total   </td>
					<td style="color: blue;font-weight: bold" align="right" width="15%"><label id='net_cons'></label></td>
					<td style="color: blue;font-weight: bold" align="right"  width="15%"><label id='total'></label></td>
					<td width="2%">&nbsp;</td>
					</Tr>
				<Tr class="tdH">
 				 			<td colspan="6" align="center" valign="top" style="height: 9px">  <input type="button" class="fb2" value="Back" onclick="history.go(-1)"> <input type="button" onclick="WC_pdf_show(document.getElementById('pr_type').value,'pdf')" class="fb2" value="Print"><input type="button" onclick="window.close()" class="fb2" value="Exit">&nbsp;<a  style="vertical-align: bottom" href="benschamount.jsp?pyear=<%=year%>&oid=<%=oid%>&pmonth=<%=month%>&BENEFICIARY_SNO=0&spl_flag=1"> All Beneficiary</a> <img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=49','mywindow','scrollbars=yes')"/></td>
				</tr>
</table>  
		   <input type="hidden" id="pr_status" name="pr_status" value="0">  
		   <input type="hidden" id="row_count" name="row_count" value="0">
		   <input type="hidden" id="freezs" name="freezs" value="1">
		   <input type="hidden" id="sp_flag" name="freezs" value="2">
		   <input type="hidden" id="subdiv" name="subdiv" value="1">
		   <input type="hidden" id="oid" name="oid" value="<%=oid%>">
</form>
</body>
</html>