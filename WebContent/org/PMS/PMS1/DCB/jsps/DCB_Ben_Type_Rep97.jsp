<html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<title>Bill Demand | TWAD Nest - Phase II </title>
 <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script src="jquery-3.6.0.js" type="text/javascript"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
 <script type="text/javascript" src="../scripts/OpeningBalanceReport.js"></script>
<script type="text/javascript" src="../scripts/json_js1.js"></script>
<script type="text/javascript">
function rld(process,ben_value,ben_type) 
{
	var month_value =document.getElementById("month").value;
	var year_value =document.getElementById("year").value;
 	var s="";
	if (process==1)
		s=window.open('Bill_Pumping_ed.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&ben_type="+ben_type,'windowname1','width=900, height=700,scrollbars=1')
	if (process==2)
	{
		document.getElementById("selected_ben").value=ben_value;
		s=window.open('Bill_Demand.jsp?month_value='+month_value+'&year_value='+year_value+"&ben_type="+ben_type,'windowname1','resizable=yes,width=900, height=800,scrollbars=1')
	}	
	if (process==3)
	{
		document.getElementById("selected_ben").value=ben_value;
		var mv=document.getElementById("month").options[document.getElementById("month").selectedIndex].text;
		var yv=document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
		var year_value =document.getElementById("year").value;
		s=window.open('pumping_report.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&mv="+mv+"&yv="+yv+"&ben_type="+ben_type,'windowname1','width=900, height=700,scrollbars=1')
	}
	if (process==4)
	{
		document.getElementById("selected_ben").value=ben_value;
		s=window.open('pumping_val.jsp?mv='+month_value+'&yv='+year_value+"&ben_type="+ben_type,'windowname1','resizable=yes,width=900, height=800,scrollbars=1')
	}		
}
</script>

</head>
 	   <% 
 	    Controller obj=new Controller();
		Connection con;
		con=obj.con();
		obj.createStatement(con);
		String userid="0",Office_id="",Office_Name="";
		String  prflag=obj.setValue("prflag",request);
		try
		{
		 userid=(String)session.getAttribute("UserId");
		}catch(Exception e) 
		{
			 response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
	//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	
		if (Office_id.equals("")) Office_id="0";
 		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		obj.conClose(con);
		%>
		
	
	 <body  onload="month('month','0'),year('year','0'),data_show_dcb('show',1,'subdiv'),data_show_dcb('show',2,'bentype'),flash()">
	 <form name="list_demnad">
 	 	<table  align=center   border="0"   width="95%"  cellspacing="0" cellpadding="2">
			<tr class="tdH">
	 	   	        <th align="center" colspan=8 class="tdText"> <font    size="2"><b>Pumping Return -- <%=Office_Name%> </b></font>  </th>
			</tr>
 			<tr class="tdHeader">
				<td valign="middle" align=left width=10% height=0> Year </td>
       			<td valign="middle" align=left width=10% height=0> <select class='select'  id="year" name="year"  style="width:90pt"></select></tD>			
        		<td  valign="middle" width=5% height=0>Month</td>
        	<!-- 	<td valign="middle" width=5%> <select class='select' id="month" name="month" style="width:90pt" onchange="list('show',1,this) "></select></tD> -->
        		<td valign="middle" width=5%> <select class='select' id="month" name="month" style="width:90pt" ></select></tD>
        	
        		<td valign="middle" width=20% align=right>Sub Division</td>
        	 	<td valign="middle" width=10% > <select disabled class='select'  id="subdiv" name="subdiv" onchange="list('show',2,this)"> </select></tD> 
         		<td valign="middle"  width=20% align=right>Beneficiary Type</td>
        		<td valign="middle"  align=right > <select class='select'  id="bentype" name="bentype" onchange="list('show',7,this)" style="width:100pt"></select> </td> 
           
       </tr> 
        		      
        	<tr class="tdH"><td colspan=10 align=right  height=4>
        	<b><font  > <label id="msg"> </label></font></b><input type="button" class="fb2" value="Refresh" onclick="list('show',4,this)"/></td></tr>
 			</table>
 			
 			<table class="fb2" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align=center width="95%" border="0"  cellspacing="0" cellpadding="0"  > 			 
       <!--    	<Tr valign="top" height=470>
				<td colspan="5" valign="top">
					<div id='scroll_clipper' style='position:relative; width:100%; border-height:1px; height: 500px; overflow:auto;white-space:nowrap;'>
						<div id='scroll_text'  style="vertical-align: top;" >
				 	<table id="entred_data" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" align=center width="100%" border=1 cellspacing=0 cellpadding=1> 
						 
						<tbody id="entred_body" align="left" valign="top"  ></tbody> 
			       	</table>  
						</div>
					</div>
				</td>
			</Tr>-->
			<Tr>
				<th colspan="5" align="right"> 
					 	<select id="pr_type" class="select">  
						<option value="1">   Select   </option>
						<option value="1">   PDF   </option>
						<option value="2">   EXCEL  </option>
						</select>
						 
					</th>
					</Tr>
			<tr class="tdH"> 
			<td colspan=5 align=center>
					<input type="button" class="fb2" value="Refresh" onclick="javascript:window.location.reload();">
 		
  
					<input type="button" class="fb2" value="Refresh" onclick="javascript:window.location.reload();"><input type="button" class="fb2" value="Print" onclick="pumping_pdf_show('2','pdf')"/>
					<% if (Integer.parseInt(prflag)==2){ %>
					<input type="hidden" class="fb2" value="Demand" onclick="rld(2,0,0)"> 
					
					<% } %>             
					    
					<input type=button class="fb2" value=Exit onclick="javascript:window.close()"></input><img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=22','mywindow','width=700,height=300,scrollbars=yes')">
			</td>
			</tr>  
 </table>
		<input type="hidden" id="selected_ben" name="selected_ben" value="0"></input>
		   <input type=hidden id="pr_status" name="pr_status" value="0"> 
			<input type="hidden" id="prflag" name="prflag" value=<%=prflag%>>
		</form>
</body>
</html>