<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB JOURNAL DETAILS | TWAD Nest - Phase II</title>
</head>
<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
 <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<body onload="journal_report(2,document.getElementById('val').value),flash()">
<form>
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
 
String Date_dis=day+"/"+(month+1)+"/"+year;
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
	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	if (Office_id.equals("")) Office_id="0";
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	
	
	 String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
	 String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
	
	if (smonth.equalsIgnoreCase("0"))
	 month=month;
	else
	 month=Integer.parseInt(smonth);
	 
	 if (syear.equalsIgnoreCase("0"))
	  year= year;
	else
	 year=Integer.parseInt(syear);
	obj.conClose(con);


String val=request.getParameter("val");
	 
	String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
%>
 <input type="hidden" id="month" value="<%=month%>">
<input type="hidden" id="year" value="<%=year%>">

<table style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" width="100%"   id="journ_ben_data1"  border="1"  align="center"  cellspacing="0" cellpadding="3">
<tr bgcolor="#C6DEFF"  class="tdText">
	<td colspan="7" align="center">DCB  JOURNAL DETAILS - <%=Office_Name%></td>
</tr>
 <tr style="height: 30pt"  > 

    <td colspan="2"  align="left" class="tdText"><font> Month/Year :</font></td><td  colspan="5" class="tdText"> <%=cmonth[month]%>&nbsp;/&nbsp;<%=year%></td>
    
	 
</tr>
<tr>
	<td colspan="7" align="right"><label id="msg"></label> </td>
</tr>
<Tr bgcolor="#C6DEFF" class="tdText" valign="top"  >
<td  align="center" > Sl.No</td>
<td align="center">A/c Head Code and  Description</td>
<td align="center">SL Type Code (Project ID) </td>
<td align="center">Sub Legder Code </td>
<td align="center">CR</td>
<td align="center">DR</td>
<td align="center">Particulars</td>
 

</Tr>
<tbody   id="journ_ben_body1" align="left"   ></tbody>
<tr>
	<td colspan=7 align="center"><a href="journal_report.jsp">Back </a>&nbsp;<a href="javascript:window.close()">Exit </a>   </td>
</tr>

</table> <input type="hidden" id="val" name="val" value="<%=val%>">
 			<input type="hidden" id="pr_status" name="pr_status" value="0"> 
		   <input type="hidden" id="row_count" name="row_count" value="0">
		   <input type="hidden" id="freezs" name="freezs" value="1">
		   <input type="hidden" id="subdiv" name="subdiv" value="1">
		   
		</form>
</body>
<%}catch(Exception e) {
	
	response.sendRedirect(request.getContextPath()+"/index.jsp");
}	 %>
</html>
