<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <%@page import="java.util.Calendar" %>
 <%@page import="java.util.*,java.sql.*"%>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Beneficiary Type - Scheme Type Wise  | TWAD Nest - Phase II </title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function dis(b,a)
{
	var a=document.getElementById('bentype').value;
	var b=document.getElementById('schtype').value;
	 
	
	var fyear=document.getElementById('year').value;
	var fmonth=document.getElementById('month').value;
	window.open("../../../../../Bill_Demand?command=Collection3&fmonth="+fmonth+"&fyear="+fyear+"&bentype="+a+"&schtype="+b);
}
function test()
{
	window.close();
	 window.location="mismenu.jsp";
	 
}

</script>
</head>
<body>
<%
		int month = 0;//cal.get(Calendar.MONTH) + 1;
		int year = 0;//cal.get(Calendar.YEAR);
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con;
		String smonth="",syear="",html="",combo1="",combo2="";
		Controller obj=null;
		try
		{
		  obj=new  Controller();
		  con=obj.con();
		  obj.createStatement(con);
		  
		  
		  combo1=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," order by BEN_TYPE_ID","bentype","class='select'");
		  combo2=obj.combo_str("PMS_SCH_LKP_TYPE","SCH_TYPE_DESC","SCH_TYPE_ID","order by SCH_TYPE_ID","schtype","class='select'");
		  
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
			smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
			syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		  
		  
		  con.close();  
		}catch (Exception e) {out.println(e);
		//x	response.sendRedirect(request.getContextPath()+"/index.jsp");
			
		}
		 
%> 
<Table class="fb2" valign=top  id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" class="fb2" >
		<label name="msg" id="msg"></label>
				<tr class='tdH'>
					<td colspan=2><center>Demand, Collection and Balance Statement</center></td>
				</tr>
		
				<tr class='tdH'>
					<td colspan=2><center>Scheme Type Wise-Beneficiary Type</center></td>
				</tr>
			    <tr class='tdH'> 
					<td class="tdText">Month&nbsp;&nbsp;:&nbsp;&nbsp;<%=obj.month_val(smonth)%></td>
					<td class="tdText" align="right">Year&nbsp;&nbsp;:&nbsp;&nbsp;<%=syear%></td>
				</tr> 
				  <tr>
					<td class="tdText">Scheme Type</td>
					<td><%=combo2%></td>
				</tr> 
				<tr> 
					<td class="tdText">Beneficiary Type </td>
					<td><%=combo1%></td>
					</tr>
				
				<tr>
					<td colspan=2 align="center"><input class="fb2" type="button" id="" value="Print" onclick="dis()"  /> <input class="fb2" type="button" id="" value="MIS Menu" onclick="test()"  /> <input class="fb2" type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr>
				<tr><td><a href="../reports/jsps/consalidated_report.jsp">Link</a></td></tr> 
</Table>
<input type="hidden" name="month" id="month" value="<%=smonth%>"/>
<input type="hidden" name="year" id="year" value="<%=syear%>"/> 
				
</body>
</html>