<%@ page language="java"  contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Beneficiary Transfer Master</title>
	<script type="text/javascript" src="../scripts/jquery-3.6.0.js"></script>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript" src="../scripts/Scheme_Change.js"></script>
	<script type="text/javascript" src="../scripts/dcbvalidation.js"></script> 				
	<script type="text/javascript"></script>
	<script type="text/javascript">
	function hide(flag)
	{
		document.getElementById('but1').disabled = true;
	}
	</script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
</head>
<% 
		Connection con;
		String BEN_TYPE_ID="",Office_name="";
		Controller obj=new Controller();
		String userid="112",Office_id="",Office_Name="";
		String combostr="";
		Calendar cal=Calendar.getInstance();	
		int day=cal.get(Calendar.DATE);
		int month=cal.get(Calendar.MONTH)+1;
		int year=cal.get(Calendar.YEAR);
		int prv_month=0;
		if (month==1) prv_month=12;
		else
		prv_month=month-1;   
		int prv_year=year;
		if (prv_month==12)  prv_year=year-1; 
	try
	{
			con=obj.con();
			con=obj.con();
			obj.createStatement(con);
			userid=(String)session.getAttribute("UserId");
			if(userid==null)  
			{
			 	 response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
		    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		    if (Office_id.equals("")) Office_id="0"; 
			BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")","BEN_TYPE_ID","","  onchange=sch_load(2,'')" );
			combostr=obj.combo_str("com_mst_all_offices_view","OFFICE_NAME","OFFICE_ID","where   OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP  ) and   OFFICE_LEVEL_ID='DN'","DV#@","onchange=div_sch(6,document.getElementById('DV#@'),#@) onblur=sub_div_load(4,#@)   ");
	}catch(Exception e) {}
	String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

%>  
<body onload="sch_load(1,'sch'),hide(1)"> 
 	<table border=0  width="100%" align="center"  cellpadding='5' cellspacing='0' >
 		<tr>
 		<td colspan="6"> 
		 		  <table border=0 width="100%" align="center"  cellpadding='5' cellspacing='0' >
					 	<tr> <th colspan="8" align="center"><%=Office_name%>   </th></tr>  
					 	
					 	<tr>    
							<td>Scheme Name </td><td>
									<select id="sch" name="sch" style="width: 120pt" onchange="sch_load(2,'')">
										<option value="0">Select</option>
									</select> 
							</td> 
							<td>Beneficiary Type</td>  
							<td align="left"> <%=BEN_TYPE_ID%></td>
							<td align="left" colspan="2" id="mtd"><font color='red' size=3><b>*</b></font>&nbsp;&nbsp;Effective Month and Year  &nbsp;&nbsp;&nbsp;&nbsp;
									<select class="select" id="pmonth" name="pmonth">
									<option value="0">Select</option> 
									<%
							for (int j=1;j<=12;j++) {%><option value="<%=j%>"><%=monthArr[j]%></option><%}%>
					 		</select>
					 		
							&nbsp;
					  		<select class="select"  id="pyear" name="pyear">
					  		<option value="0">Select</option>
					  		<%
						  	for (int i=year-2;i<=year;i++) { %> <option value="<%=i%>"><%=i%></option><% } %>
					   		</select>  
					  <font color='red' size=3><b>*</b></font>&nbsp;&nbsp;Order Number &nbsp;&nbsp;&nbsp;&nbsp;<input type=text id="orderno" name="orderno"></td>
				 
					 		
				</tr>  
				</table>
		</td>
		</tr>
		<tr>         
			<td colspan="4" valign="top" width="50%">
			<div id='scroll_clipper' style='position:relative; width:100%; border-height:1px; height: 500px; overflow:auto;white-space:nowrap;'>
			<div id='scroll_text'  style="vertical-align: top;" >
				<table width="100%" border="0" cellpadding="0" cellspacing="0"  >
				<tr>  
					<th>Beneficiary Name</th>
					<th width="1%">Select</th>
					<th>Meter Location</th>
					<th width="1%">Select</th>
				</tr>
				<tbody id="data_tbody" name="data_tbody"></tbody>
				</table>
			</td>
					<td width="5%">
						<Font size=3> <img src="../../../../../images/newclickme.jpg" onclick="move()" width="50" height="50"> 	</Font>
					</td>	
			<td valign="top" width="50%">
				<table width="100%" border="0" cellpadding="5" cellspacing="0">
				<tr>
					<td colspan="4" width="50%">
						<div id='movetodiv'></div>
					</td>
				</tr>
				<tr>
					<td align="center"><input type="button" value="Submit" id='but1' onclick="change(5)" style="background-color: skyblue">
					<input type="button" value="Exit" style="background-color: skyblue"onclick="window.close()"></td>
				</tr>
				</table>
				 
			</td>
		</tr>
	</table>
 <input type="hidden" id="sdv" name="sdv" value="<%=combostr%>">
 <input type="hidden" id="totallocal" name="totallocal" />
 <input type="hidden" id="totalben" name="totalben" />
 
</body>
</html>