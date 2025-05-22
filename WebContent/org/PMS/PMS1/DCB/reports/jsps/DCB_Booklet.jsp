<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@page import="java.util.Calendar" %>
 <%@page import="java.util.*,java.sql.*"%>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*,javax.swing.JFrame;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB - Booklet </title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function d_dcb( ) 
{
	var fyear=document.getElementById('pyear').value;
	var fmonth=document.getElementById('pmonth').value;
	var rtype=document.getElementById('rtype').value;
	var option=document.getElementById('option_type').value;
	var schtype="";
	var command="new_DCB";
	var url="";
	var refno="0";
	var bentype="";
	switch(parseInt(rtype))
	{
		case 1: 
			rtype=1;
			break;			
		case 2:
			rtype=2;
			refno=1;
			break;	
		case 9:
			schtype="2";
			refno=2;
			rtype=9;
			break;		
		case 4:
			  schtype="2";			  
			  try {   
			  	rtype=document.getElementById('subrpttype').value;
				if (parseInt(rtype)==4)
				{
					refno=3.1;
					bentype="";
				}
				else if (parseInt(rtype)==5)
				{
					refno=3.2;
					bentype="1";
				}
				else if (parseInt(rtype)==66)
				{	refno=3.3;
					bentype="6";
				}
				else if (parseInt(rtype)==67)
				{
					refno=3.4;
					bentype="7";  
				}
			  }catch(e) {rtype=0;}			  
		break;
		case 6:
			  schtype="1";
			  try {   
				  	rtype=document.getElementById('subrpttype').value;
					if (parseInt(rtype)==4)
					{
						refno=4.1;
						bentype="1";
					}
					else if (parseInt(rtype)==5)
					{
						refno=4.2;
						bentype="1";
					}
					else if (parseInt(rtype)==62)
					{
						bentype="2";
						refno=4.3;
					}
					else if (parseInt(rtype)==63)
					{
							bentype="3";
							refno=4.4;
					}
					else if (parseInt(rtype)==64)
					{
						bentype="4";
						refno=4.5;
					}
					else if (parseInt(rtype)==65)
					{
						bentype="5";
						refno=4.6;
					}
					else if (parseInt(rtype)==66)
					{
						bentype="6";
						refno=4.7;
					}  
				  }catch(e) {rtype=0;}
		break; 
		case 8:
				schtype="1";
				bentype="7";
				rtype="5";
				refno="7";
				break;
		case 17:		 
				rtype=17;
				refno="7";
				break;	
		case 18:		 
				rtype=18;  
				refno="5";
				break;		   
	}
	url="../../../../../../dcb_statement_new1?refno="+refno+"&option="+option+"&bentype="+bentype+"&schtype="+schtype+"&process_code="+rtype+"&command="+command+"&fmonth="+fmonth+"&fyear="+fyear;
 	if (parseInt(rtype)!=0)   
	window.open(url);
	
}
function ld()
{
	//var s=window.open("DCB_Booklet_New.jsp","Index","width=300,height=200,resizable=yes,location=no,scrollbars=no");
var page = window.open("DCB_Booklet_index.jsp", "", 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=no, width=600, height=600, top=0, left=0');
}
function d_dcb1()  
{
	var fyear=document.getElementById('pyear').value;
	var fmonth=document.getElementById('pmonth').value;	
	var option=document.getElementById('option_type').value;
	var rtype=document.getElementById('subrpttype').value;
	var schtype="";
	alert(rtype);
	var command="new_DCB";
	var url="";
	switch(parseInt(rtype))
	{
		case 1:			
		case 2:
		case 9:		 
			  url="../../../../../../dcb_statement_new1?option="+option+"&schtype="+schtype+"&process_code="+rtype+"&command="+command+"&fmonth="+fmonth+"&fyear="+fyear;
		break;
	}
 
	window.open(url);
	
}
	function rld(a) 
	{ 
		
		switch(parseInt(a))
		{ 
			case 4:
					var cb1="<select id='subrpttype'><option value=0>Select</option>"
					cb1+="<option value=4> 3.1)&nbsp;&nbsp;Abstract</option>";
					cb1+="<option value=5> 3.2)&nbsp;&nbsp;Corporation</option>";
					cb1+="<option value=66>3.3)&nbsp;&nbsp;Village Panchayat</option>";
					cb1+="<option value=67>3.4)&nbsp;&nbsp;Private and Others</option>";  
					document.getElementById('subrpt').innerHTML=cb1;
					break;
			case 6: 
					var cb1="<select id='subrpttype'><option value=0>Select</option>";
					cb1+="<option value=4>  4.1)&nbsp;&nbsp; Abstract</option>";
					cb1+="<option value=5>	4.2)&nbsp;&nbsp; Corporation</option>";
					cb1+="<option value=62> 4.3)&nbsp;&nbsp; Municipality III Grade</option>";
					cb1+="<option value=63> 4.4)&nbsp;&nbsp; Municipality</option>";
					cb1+="<option value=64> 4.5)&nbsp;&nbsp; UTP</option>";
					cb1+="<option value=65> 4.6)&nbsp;&nbsp; RTP</option>";
					cb1+="<option value=66> 4.7)&nbsp;&nbsp; Village Panchayat</option>";
					document.getElementById('subrpt').innerHTML=cb1;
					break;
			default: 
					document.getElementById('subrpt').innerHTML="";
					break;
		}	
				 
	}	 
</script>
</head>
<body>
 
<%
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int prv_month=0;
		if (month==1) prv_month=12;
		else
		prv_month=month-1;
		
		int prv_year=year;
		if (prv_month==12)  prv_year=year-1;
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con;
		String smonth="",syear="",html="",combo1="",combo2="";
		Controller obj=null;
		try
		{
		  obj=new  Controller();
		  con=obj.con();
		  obj.createStatement(con);
		 // combo1=obj.combo_str("","distinct BEN_TYPE_GROUP_NAME ","  BEN_TYPE_GROUP_NAME","BEN_TYPE_GROUP",""," order by BEN_TYPE_ID","bentype","   ","class=select style='width:250px' ");
		  combo1=obj.combo_str("PMS_DCB_BEN_TYPE","distinct BEN_TYPE_GROUP_NAME ","BEN_TYPE_GROUP_NAME","BEN_TYPE_GROUP"," where BEN_TYPE_GROUP not in (8,9) order by BEN_TYPE_GROUP", "bentype","","class=select style='width:250px' ");
		
		  combo2=obj.combo_str("PMS_SCH_LKP_CATEGORY","CATEGORY_DESC","SCH_CATEGORY_ID","where SCH_CATEGORY_ID=4 order by SCH_CATEGORY_ID","schtype","   ");
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
			
		}

		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
	
	 
%>
<table   class="table" id="" width=50% align="center" border="1"   cellspacing="0" cellpadding="20" style="BORDER-COLLAPSE: collapse" border="1" >
<tr bgcolor="#408099"><td colspan="3" align="center""> <font color='white'>DCB Booklet</font> </td></tr>
 
 <tr>
	<td align="left" width="20%"> <font  class="fnt"> Year </font> </td>
	<td colspan="2">
		<select id="pyear" name="pyear" >
		<option value="0">-Select Year-</option>
		 <% for (int i=year-6;i<=year;i++) 
				   { 
				   if (i==prv_year) {	%>  
				   <option value="<%=i%>" selected><%=i%></option>
				   <%  } else {%>  
				  <option value="<%=i%>"><%=i%></option>
				  <%}
				   }
				   %>
		</select> 
	</td>
</tr>
<tr>
	<td align="left"><font class="fnt">  Month </font></td>	  	          
	<td colspan="2"><select  id="pmonth" name="pmonth"   > 
	<option value="0">-Select Month-</option>
		<% 	for (int j=1;j<=12;j++)	
				  	{
				  	if (j==prv_month) {	%>
				    <option value="<%=j%>" selected><%=monthArr[j]%></option>
				  	<%  } else {%>
				  	<option value="<%=j%>" ><%=monthArr[j]%></option>
				  	<%
				  	} 
				  	}%>
	</select>&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:ld()">View Index Page</a> 
	</td>	        
</tr>
<tr>	<td>Select Report</td>
		<td width="25%">
			<select id="rtype" multiple="multiple" onclick="rld(this.value)">
				<!-- <option value="1"> General  Abstract</option> -->
				<option value="2"> 1)&nbsp;&nbsp;  Consolidated  Abstract</option>
				<option value="9"> 2)&nbsp;&nbsp;  5 Board WSS - Scheme Abstract </option>
				<option value="4"> 3)&nbsp;&nbsp;  5Board WSS </option>
				<option value="6"> 4)&nbsp;&nbsp;  CWSS - Local Body</option>
				<option value="18">5)&nbsp;&nbsp;  Village Panchayat- District Abstract</option> 
				<option value="8"> 6)&nbsp;&nbsp;  CWSS - Private and others</option> 
				<option value="17">7)&nbsp;&nbsp;  Unit wise Abstract</option>
			</select>			
		</td><td valign="top">	<div id='subrpt'></div></td>
</tr> 
<tr>
	<td colspan="3" align="center"><input type="button"  style="color: green;font-weight: bold"  value="Submit" onclick="d_dcb()">&nbsp; &nbsp; 
	<input type="button"  style="color: red;font-weight: bold"  value="Close" onclick="javascript:window.close()"></td>
	
</tr>
<tr> 
	<td colspan="3">
			       	<select id="option_type" name="option_type" class='select'>
			       						<option value="0" selected>Type</option>
										<option value="2">  Excel </option>
										<option value="1" selected>  PDF </option>  
										<option value="3">  HTML </option>
					</select></td>     		
     			</tr>
     			 
</Table> 
</body>
</html>