<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<%@ page import="java.text .DecimalFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>Actual Item Wise Expenditure</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/transcat.js"></script>
<style type="text/css"> 
#msg_div {
    position:absolute;
    top: 50%;
    left:  50%;
    width:30em;
    height:12em;
    margin-top: -9em; /*set to a negative number 1/2 of your height*/
    margin-left: -15em; /*set to a negative number 1/2 of your width*/
    border: 1px solid #ccc;
    border-color=#434567; 
    background-color: #f3f3f3;
    overflow-x: scroll; 
    word-wrap: break-word;
}

</style>
<script type="text/javascript">
	function acc_view(i)
	{
		var MAIN_ITEM_DESC	=document.getElementById("MAIN_ITEM_DESC"+i).innerHTML;
		var acc=document.getElementById("ACCOUNT_HEAD_CODE"+i).value;
		document.getElementById("msg_div").style.visibility = "visible";
		document.getElementById("msg_div").innerHTML="<table align='center' width='100%'><tr><td width='15%'>Description of Items : </td><td width='85%'>"+MAIN_ITEM_DESC +" </td></tr><td  width='15%'> Account Heads : </td><td  width='85%'>" +acc+"</td></tr><tr><td colspan=2 align=left><input type=button value='X' onclick=cls()></table>";
	}
	function cls()
	{
		document.getElementById("msg_div").style.visibility = "hidden";
	}
	function verf_cls()
	{
		document.getElementById('verf').disabled = true;
	}
	function verf_cls1()
	{
		document.getElementById('verf').disabled = false;
	}
</script>
</head>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<body onload="flash();verf_cls()">  
<%
	try
	{	
		HttpSession session1 = request.getSession(false);
		String	userid = (String) session1.getAttribute("UserId");
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj=new Controller();
		
		if (userid == null) {

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String sch_sno=obj.setValue("sch_sno",request);
		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')");
		
		if (Office_id.equals("")) Office_id="0";
		String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where SCH_type_id in ( select SCH_type_id from PMS_DCB_APPLICABLE_SCH_TYPE ) and sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")  order by SCH_SNO","sch_sno","","class='select' style='width: 262' " );
		
		
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		String flag_c=obj.setValue("flag_c",request);
		int year_incr=0;
		  if (month <4)
			  year_incr=0;    
		  else  
			  year_incr=1;   
%>
			<table   align="center" width="100%" border="0" cellspacing="1" cellpadding="1" style="border-collapse: collapse;border-color: skyblue" >
     		<tr>
     			 <th colspan="2" align="center"  class="tdH">  Item Wise Expenditure </th></tr> 
			<tr>
				<td width="40%"><label class="fnt">Division Name :</label></td> 
				<td align="left"> <label class="fnt"><%=Office_Name%></label></td>
			</tr>
			<tr> 
				 <td width="40%"><label class="fnt">Scheme  Name : </label></td>
				 <td align="left"> <%=sch%></td></tr>
			<tr><td colspan="2"><table width="100%" border="0" >
			  		<tr>
			  			    <td width="20%"><label class="fnt">  Month </label></td>
			  			    <td width="20%"> <select class="select" id="pmonth" name="pmonth"  style="width:90pt;"  onchange="report_period_verify_2(this,document.getElementById('pyear'))">
			  				<option value="0">Select</option>
			  				<%for (int j=1;j<=12;j++) {%>
			    			<option value="<%=j%>"><%=monthArr[j]%></option>
			    			<%} %>  
			 				</select>   
			 	 			</td>   
			 	   			<td width="20%"><label class="fnt"> Year </label> </td><td width="20%"> 
			        			<select class="select"  id="pyear" name="pyear"  style="width:80pt;" onchange="report_period_verify_2(document.getElementById('pmonth'),this)">
			  				<option value="0">Select</option>
			  		 		<%for (int i=year-2;i<=year;i++) {%>
			  				<option value="<%=i%>"><%=i%></option>
			  				<%}%>
			  		</select> </td><td><input type=button value="GO" onclick="transaction(3,15)"> 
			  			 &nbsp;&nbsp;<input type="hidden" class="btn" value="Submit" onclick="transaction(2,10)"/>
						 &nbsp;&nbsp;<input type="button" class="btn" value="Verify" onclick="verify(1)" id='verf'/>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Exit"  class="btn" onclick="window.close()" />	</td> 
			  		 
			  	</tr> 
			  </table>
			  </td>
			  </tr>
			  <tr>
			  	<td colspan="2"><label id='msg'></label></td>
			  </tr>
			</table>
				<table id="etable"   align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
				<tr>
			 		<td colspan="2" align="right"><label style="font-size: 1.15em;color: blue" id='net_label'></label></td>
			 	</tr>
				<tr><td valign="top" align="center" colspan="2" style="height: 42em;">						
					<table id="etablesub"   border="1"   align="center" width="100%"  cellspacing="1" cellpadding="1">
						<tr style="height: 2em;">
							<th align="center" width="5%">Sl.No</th>
							<th align="center">Description of Items</th>
							<th align="center" width="15%">Account Head Code</th>
							<th align="center" width="15%">Amount <Br> (Rs.in lakhs)</th>
						</tr>
						<tbody id="edatasub" align="center" valign="top"/>
					</table> 
					</td>
			 	</tr>
			 	<tr>
			 		<td colspan="2" align="right"><label style="font-size: 1.15em;color: blue" id='net_label1'></label></td>
			 	</tr>
			 
				<tr>
					<td class="tdH" valign="top">
					<table>
						<tr>
							<td  rowspan=3><font color='red' size='3'><b>Note:</b> &nbsp;&nbsp;</font></td>
							<td >1.&nbsp; Sl.No in Red indicates Account Head Code mapping Not done for this item...Please Contact TWAD HO for mapping </td>
						</tr>
						<tr>
							<td>2.&nbsp;For any improper / zero figures in Amount Column against any item reason could be    
						 	Entry is not accounted for selected scheme for that Account Head Code</td>
						 </tr>
						  <tr>
							 <td>&nbsp;Contact TWAD, HO for further Help / Clarification </td>  
						  </tr>
						</table>	
					</td>
				</tr>				 
			</table>
			<input type="hidden" id="msg_hid" name="msg_hid" value="Expenditure figures are being fetched from FAS ...Please Wait">
			<input type="hidden" id="pr_status" name="pr_status" value="1"/>
			<input type="hidden" id="flag_c" name="flag_c" value="M"/>
			<input type="hidden" id="days" name="days" value="0"/> 
			<input type="hidden" id="pumped_dpr" name="pumped_dpr" value="0"/>
			<input type="hidden" id="dataflag" name="dataflag" value="0"/> 
			<input type="hidden" id="row_count" name="row_count" value="0"/>
			<input type="hidden" id="desg_hour" name="desg_hour" value="0"/>
			<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
			
			<div id='msg_div'></div> 
			<script type="text/javascript">
				document.getElementById("msg_div").style.visibility = "hidden";
			</script> 
</body>
	<%
	}catch(Exception e) 
	{
			out.println(e);
	}  
	%>
</html>