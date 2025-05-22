

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>	
<html> 
<title> DCB Receipt Verificaition | TWAD Nest - Phase II</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
	function rld()
	{
		  var v=document.getElementById('ben_sel').value;
		  document.forms["receipt"].submit();
		  document.getElementById('ben').value=v;
	}
	function callReport(pro)
	{
		  var option=document.getElementById('pr_type').value;
		  var fyear=document.getElementById('fyear').value;
		  var fmonth=document.getElementById('fmonth').value;
		  var ben_sel=document.getElementById('ben').value;
		  window.open("../../../../../Bill_Demand?command=recpdf&fmonth="+fmonth+"&fyear="+fyear+"&ben_sel="+ben_sel+"&option="+option+"&pro="+pro);
	}
	function troubleshoot()
	{  
		  var fyear=document.getElementById('fyear').value;
		  var fmonth=document.getElementById('fmonth').value;		   
		   //window.open("../reports/jsps/Receipt_Troubleshoot_1.jsp?syear="+fyear+"&smonth="+fmonth);
		 var res=month_year_check(fmonth,fyear);
		 if (res!=1)
		 {  
			 var url=" ./receipt_verification_form.jsp?syear="+fyear+"&smonth="+fmonth; 
			 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
			 { 
				// var  s=window.open(url);   
				 window.open(url,'','toolbar=0,titlebar=0,fullscreen=1,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 82,top = 54','');       	 
			 }else
			 {
				 var  s=window.open(url,'Receipt Verification','height=900,width=1400');
		     }  
		 }
	}       
	function troubleshoot1()
	{  
		  var fyear=document.getElementById('fyear').value;
		  var fmonth=document.getElementById('fmonth').value;		   
		  s=window.open("../reports/jsps/receipt_demand.jsp?syear="+fyear+"&smonth="+fmonth,"","","");
		    
	}  
</script>  
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
   
<%
		int month = 0;//cal.get(Calendar.MONTH) + 1;
		int year = 0;//cal.get(Calendar.YEAR);
		String userid="0",Office_id="",Office_Name="",table_heading="DCB Receipt Verificaition",table_column="",table_header="";;
		Connection con;
		String smonth="",syear="",html="",SUB_LEDGER_CODE="";
		Controller obj=null;
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		  month = cal.get(Calendar.MONTH) + 1;
		  year = cal.get(Calendar.YEAR);
		try
		{
		  obj=new  Controller();
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

//		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");		 
		smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		SUB_LEDGER_CODE=obj.setValue("ben_sel",request);
		 
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		

%>  	<title><%=table_heading%></title>
</head> 
		<body>
		<form name="receipt" action="ben_receipt.jsp">
		<label ="msg" id="msg"></label>
		<Table id="" width="75%" align="center" cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" >
		
		<tr class="tdH">
			<th colspan="2" align="center">
			 <%=Office_Name%> 
			</th>
		</tr>
		<tr>
					<td colspan=2><center><%=table_heading%></center></td>
				</tr>  
					<tr>  
					<td class="tdText">Year&nbsp;</td><td> 
						&nbsp;<select class="select"  id="fyear" name="fyear"  onchange="report_period_verify(document.getElementById('fmonth'),this)">
			   			<option value="0">-Select Year-</option>
			  			<%
	  					  for (int i=year-6;i<=year;i++)
						 {
			    			if (Integer.parseInt(syear)!=i) {
			   			%>
			  			<option value="<%=i%>"><%=i%></option>
			  				<%}else{%> 
			  			<option value="<%=i%>" selected><%=i%></option>
					  <%}
			    		}%>
				
			 	 </select>&nbsp;&nbsp; Month &nbsp; &nbsp;  <select class="select"  id="fmonth" name="fmonth" onchange="report_period_verify(this,document.getElementById('fyear'))">
					<option value="0">-Select Month-</option>
			  		<%
			  		for (int j=1;j<=12;j++)
			  		{
			  		   if (Integer.parseInt(smonth)!=j) {
			   		%>
			 	   <option value="<%=j%>"><%=monthArr[j]%></option>
			   		 <%} else { %>
			  	  <option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
			   		 <%} %>
			  <%} %>  
			 </select> 
				
					     </td>
				</tr> 
		 
	   <%}catch(Exception e)  { }%> 
		  
		  <tr>			 
			<td align="center" colspan="2">
				<input type="button" id="" value="Verify Receipt" style="font-style: italic;font-size:9;font-weight:bold;color :darkred" onclick="troubleshoot()">
				&nbsp;&nbsp;&nbsp;<input type=button value='Exit' onclick='window.close()'>
				 
			</td>
		</tr>
		
		 <tr>
		 <td colspan="2"  align="left" >
		 	<table   width=100%  border="0" style="border-collapse: collapse;border-color: skyblue;" cellpadding="7" cellspacing="0">
		 		<tr><td colspan="2">  
		 				Note:
		 		</td></tr>  
		 		<tr><td width="5%">&nbsp;</td><td>
		 				<pre>It may take few minutes  after clicking  'Verify Receipt' .....Please Wait</pre>
		 		</td></tr>
		 	</table>
		 </td>
		 </tr>
		 <input type=hidden id="pr_status" name="pr_status" value="1">
 		 		   
		</Table>
  		 
        	</form>  
</body>
</html>