<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Region Wise Balance</title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script> 
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function report()
{
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;

	if (month==0 || year==0)
	{
		alert("Select Month and Year");  
	}else
	{
	var reg_no=document.getElementById("reg_no").value;

	  if (reg_no==0)
	  {
		  alert("Select Region.."); 
	  } else 
	  {
		var rtype=document.getElementById("rtype").value;
		var reg_name=document.getElementById("reg_no").options[document.getElementById("reg_no").selectedIndex].text; 
		var src="../../../../../../dcb_statement_new?option="+rtype+"&fmonth="+month+"&fyear="+year+"&process_code=20&region_id="+reg_no+"&region_name="+reg_name;	 
		window.open(src);
	 }
	}
}
  
</script> 
</head> 
<body>

<%
Controller obj=null;
Connection con;
try
{
	  obj=new  Controller(); 
	  con=obj.con();
	  obj.createStatement(con);

	  	Calendar cal = Calendar.getInstance();
	  	int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int prv_month=0;
		if (month==1) prv_month=12;
		else
		prv_month=month-1;
		
		int prv_year=year;
		if (prv_month==12)  
		{
			prv_year=year-1;
		}
	  String combo=obj.combo_str("com_mst_all_offices_view","office_name","region_office_id","where office_level_id='RN' and Region_office_id <> 6777 ","reg_no","   ");
%>	  
	<table width="50%" align="center" border="1" bordercolor="skyblue" cellpadding="10" cellspacing="0" style="border-collapse: collapse; ">
		<tr>
			<td colspan="2" align="center">
				&nbsp;Region Wise Balance  
			</td>
		</tr>  <tr><td>&nbsp;Year</td>
	            <td><select id="year" name="year"  tabindex="5" onchange="report_period_verify(document.getElementById('month'),this)">
                <option value="0" selected="selected">Select</option>
                <%
                for (int j=(year-6);j<=year;j++)
                {
                  if (j==prv_year) { %>
                  <option value="<%=j%>" selected><%=j%></option>
				   <%  } else {%>
                <option value="<%=j%>"><%=j%></option>
                <%}
                  }%>  
    </select> </td>
		</tr>
		<tr> 
			<td width="20%"  align="left">&nbsp;Month</td><td> <select name="month" id="month" onchange="report_period_verify(this,document.getElementById('year'))">
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++)
	         	{
	         	if (i==prv_month) {	%>
	         	<option value="<%=i%>"  selected="selected"><%=amonth[i]%></option>  
	         	<%  } else {%>
				  	<option value="<%=i%>" ><%=amonth[i]%></option>
				  	<%
				  	}  
	         	} %>
	          </select></td>
	       </tr>
	     
		<tr>
			<td width="50%">&nbsp;Region</td>
			<td><%=combo%></td> 
		</tr>
		<tr>
		<td colspan="2" align="left"><select id="rtype" name="rtype" class='select'>
						<option value="0">Select </option>							
							<option value="1" selected="selected">  PDF </option>
							<option value="2">  Excel </option>
							<option value="3">  HTML </option>    
				</select>
			</td>
			</tr>
		<tr>
			<td align="center" colspan="2">
				<input type=Button value="Report" onclick="report()">
				<input type=Button value="Exit" onclick="javascript:window.close()">
			</td>
		</tr>
	</table>
	 	
<%	  
}catch(Exception e)
{
	

}


%>
</body>
</html>
