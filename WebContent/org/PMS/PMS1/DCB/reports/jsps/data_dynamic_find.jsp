<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.*" %>
<html>
<head>
<script type="text/javascript" src="../../../DCB/scripts/cellcreate.js"></script>  
<script type="text/javascript" src="../scripts/data_dynamic_find.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<title>Data Monitoring</title>
<style type="text/css"> 
	.fnt
	{
	font-size: 0.85em;
	font-size-adjust:inherit;
	font-style:inherit;
	font-variant:inherit; 	
	border-collapse: collapse;
	}
</style>  
<script type="text/javascript">
	function rld()
	{
		document.forms["rpt"].submit();
	}
</script>  
</head>
<%
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	String pmonth=obj.setValue("month",request);
	String pyear=obj.setValue("year",request);
%>
<body>
<form name="rpt" action="data_dynamic_find.jsp">
<table  class="fnt"  align="center" border=1 width="50%"  bordercolor="#00FFFF"  >
	<tr> 
		<td colspan="2" align="center">  
			Data Monitoring 
		</td>
		</tr> 
		<tr>
  		<td colspan="5"  align="left"> Month: <select name="month" id="month" onchange="rld()" >
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++) {
	         	%>
	         	<option value="<%=i%>" <% if (Integer.parseInt(pmonth)==i) {%>selected<%} else { }%>><%=amonth[i]%></option>
	         	<%} %>
	          </select> Year:<select id="year" name="year"  tabindex="5" onchange="rld()">
                <option value="0" selected="selected">Select</option>
                <%
                for (int j=2010;j<=year;j++)
                {
                %>
                <option value="<%=j%>" <% if (Integer.parseInt(pyear)==j) {%>selected<%} else { }%>><%=j%></option>
                <%} %>  
    </select> </td>
    </tr>
	<tr> 
	
		<td width="5%" align="right" valign="top">  
			<input type=button onclick="start()" value="Refresh"><br><label id='msg'></label>
		</td>
		<td width="50%">
			<table class="fnt" bordercolor="#00FFFF"  id="entred_data" width="100%" align="center" border="1"  >
			<tr>
				<th align="center">Sl.No</th>
				<th align="center">Division</th>
				<th align="center" colspan="3">Pumping Return
					<table width="100%">
					<tr>
						<td align="center">Create</td>
						<td align="center">Validate</td>
						<td align="center">Freeze</td>
					</tr>
					</table> 
				</th>
				 <th align="center">Receipt Verification</th> 
				 <th align="center">DCB Journal</th>
				 <th align="center">Demand Bill</th>
				 <th align="center">Ledger Posted</th>				
			</tr>
		<%
		Connection con; 
		Controller obj1 = new Controller();
		try {
			con = obj.con();
			obj.createStatement(con);
			obj1.createStatement(con);
			}catch(Exception e) {}
		String html="";	
		String userquery=" SELECT a1.office_name as offname,a1.office_id as off_id FROM com_mst_all_offices_view a1 ";
		userquery+=" WHERE a1.office_level_id='DN'  AND EXISTS (SELECT office_id FROM pms_dcb_div_dist_map c WHERE c.office_id=a1.office_id ) order by off_id";
		String off_id="0",pumping_qty="0";
		ResultSet rs;
		int row=0;
		rs=obj.getRS(userquery);
		while (rs.next())     
		{
			row++;
			off_id=rs.getString("off_id");
			html+="<tr id='row"+off_id+"'><td width='5%' align='center'>"+row+"</td><input type=hidden id='oid"+row+"' value='"+off_id+"'>";			 
			html+="<td width='35%'>"+rs.getString("offname")+"</td>";
			html+="<td width='5%' align='center' id='cr"+off_id+"'></td>";
			html+="<td width='5%' align='center' id='vl"+off_id+"'></td>";
			html+="<td width='5%' align='center' id='fr"+off_id+"'></td>";
			html+="<td width='5%' align='center' id='recfr"+off_id+"'></td>";
			html+="<td width='5%' align='center' id='jr"+off_id+"'></td>";				
			html+="<td width='5%' align='center' id='bd"+off_id+"'></td>";
			html+="<td width='5%' align='center' id='lg"+off_id+"'></td>";
		}
			html+="<input type=hidden id='totalrow' value='"+row+"'>";
			html+="<script>setTimeout(function () {  load()  }, 3); </script>";
			out.println(html);
		%>
	</table>
	</td>
</tr>
</table></form>
</body>
</html>
   