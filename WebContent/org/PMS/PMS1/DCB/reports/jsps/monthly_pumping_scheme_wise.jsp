<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="java.sql.Connection"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scheme Wise Pumping Return</title>
<link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
 function s()
 {
	 var selectedArray1 = new Array();
	 var selectedArray = new Array();
	 var selObj = document.getElementById('pyear');
	 var i;
	 var count = 0;
	 for (i=0; i<selObj.options.length; i++) {
	    if (selObj.options[i].selected) {
	      selectedArray[count] = "'"+selObj.options[i].value+"'";
	      selectedArray1[count] = selObj.options[i].value;
	      count++;
	    }
	  }
	  document.getElementById('fin_year').value=selectedArray;
	  document.getElementById('fin_year1').value=selectedArray1;
} 
 
function report1(process_code)
{
		var pyear=document.getElementById("finyear").value;
		alert( pyear)
		window.open("../../../../../../ame_report?process_code="+process_code+"&pyear="+pyear);	  
}
function report(process_code)
 {
	var pyear=document.getElementById("pyear").value;
	var pmonth=document.getElementById("pmonth").value;
	var pyear1=document.getElementById("pyear1").value;
	var pmonth1=document.getElementById("pmonth1").value;
	window.open("../../../../../../ame_report?process_code="+process_code+"&pyear="+pyear+"&pmonth="+pmonth+"&pyear1="+pyear1+"&pmonth1="+pmonth1);
	  
}
 function sub()
 {
	 document.forms["myform"].submit();
 }
 function page_size()
 {
	 window.resizeTo( 700,700 )
 }
 </script>
 <script type="text/javascript" src="../scripts/transcat.js"></script>
 <script type="text/javascript" src="../scripts/reports.js"></script>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>  
 </head>
  <body  onload="page_size() ">
<%
	Connection con=null;
	HttpSession session1 = request.getSession(false);
	String userid = (String) session1.getAttribute("UserId");
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj = new Controller();
	con=obj.con();
	if (userid == null) {

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	String Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

//	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID"," where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals(""))
		Office_id = "0";
	String Office_Name = obj.getValue("com_mst_offices","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");
	
	String sch_sno=obj.setValue("sch_sno",request);
	int m= Integer.parseInt(obj.setValue("pmonth",request));
	String mv=obj.month_val2(m);
	String sch = obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+ Office_id + ")", "sch_sno",sch_sno,"class='select' style='width: 262'");
//	String[] monthArr = { "Select","April", "May", "June", "July", "August", "September","October", "November", "December" ,"January", "February", "March"};
	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
	String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	String cyear="";
	if (m !=0 ) { 
	if ( m>=4 )
		cyear=obj.setValue("pyear",request).split("-")[0];
	else
		cyear=obj.setValue("pyear",request).split("-")[1];
	}
	String fin_year1=obj.prvfinyear(obj.setValue("pyear",request),2);  
	String fin_year=obj.prvfinyear(obj.setValue("pyear",request),2,1);  
	  
	String []sr=fin_year1.split("\\,");  
	 
	String td="";
	
	 
	for (int i=sr.length-1;i>=0;i--)
	{ 
		td+="<td align='center' width='7%' class='lbl'>During "+sr[i]+"</td>";	
		
	} 
	td+="<td align='center' width='5%' class='lbl'>During "+obj.setValue("pyear",request)+"<bR>"+mv+"-"+m	+"-"+cyear+"</td>";
	NumberFormat formatter = new DecimalFormat("#0.00");
	  
  String mstr="  and month in (";
  if ( m !=0)
  {
			  if (m<=3)
			  {
						  for (int p=4;p<=12;p++)
						  {
							  if (p==4)
								   mstr+=""+p;
							  else
								  mstr+=","+p;
							 
						  }  
						  for (int q=1;q<=m;q++) 
							  
						  {
							  
							  mstr+=","+q;
						  }
			  }
			  else
			  {
					  for (int p=4;p<=m;p++)
					  {
						  if (p==4)
							   mstr+=""+p;
						  else
							  mstr+=","+p;
					  }
			  }
  }
  mstr+=")";  
  int year_incr=0;
  if (month <4)
	  year_incr=0;    
  else
	  year_incr=1;     
%>  
<form name="myform" action="schemeperformance_r.jsp">
<table align="center" width="85%" border=2 class="table" cellpadding="5" cellspacing="0" style="vertical-align: top;">
		<tr>
			<td colspan="4" align="center" class="tdH">Scheme Wise Pumping Return - <%=Office_Name%></td></tr>
		 <tr>     
			<td width="10%">From Month :</td><td>
				<select class="select" id="pmonth" name="pmonth" onchange="report_period_verify(this,document.getElementById('pyear'))" style="width: 80pt;"  >
				<option value="0">-Month-</option>
					<%  for (int j = 1; j <=12  ; j++) {
						 if (Integer.parseInt(monthArrv[j])==m) {
						%>
						<option value="<%=monthArrv[j]%>" selected><%=monthArr[j]%></option>
						<% } else { %>
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
						<%} %>
					<% }  %>
				</select>
			</td>
			<td width="10%">From Year :</td><td>
					<select class="select" id="pyear" name="pyear" style="width: 80pt;" onchange="report_period_verify(document.getElementById('pmonth'),this)" >			 
					<% 	for (int i = year-6; i <= year; i++) { %>
						<option value="<%=i%>" selected><%=i%></option>
					<% 	} %>
					</select>  
			</td> 
	</tr>  
	<tr> 
			<td width="10%">To Month :</td><td width="10%">
				<select class="select" id="pmonth1" name="pmonth1" onchange="report_period_verify(this,document.getElementById('pyear1'))" style="width: 80pt;"  >
				<option value="0">-Month-</option>
					<%  for (int j = 1; j <=12  ; j++) {
						 if (Integer.parseInt(monthArrv[j])==m) {
						%>
						<option value="<%=monthArrv[j]%>" selected><%=monthArr[j]%></option>
						<% } else { %>
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
						<%} %>
					<% }  %>
				</select>  
			</td> 
			<td width="10%"> To Year :</td><td width="10%">
					<select class="select" id="pyear1" name="pyear1" style="width: 80pt;" onchange="report_period_verify(document.getElementById('pmonth1'),this)" >			 
					<% 	for (int i = year-6; i <= year; i++) { %>
						<option value="<%=i%>" selected><%=i%></option>
					<% 	} %>
					</select> 
			&nbsp;&nbsp;&nbsp;<input  type="button" value="REPORT" class="fb2"   onclick="report(9)"></td> 
	</tr>
	<Tr>  
			<td colspan="2"> 
				<font color=blue>Financial Year </font> </td> 
			 <td width="10%" width="10%" >
            	<select id="finyear"   tabindex="5" style="width:120pt"  class="select">
                	<option value="0" selected="selected">- - - Select - - -</option>
                	<%
                	for (int j=year-5;j<=year+year_incr;j++)
                	{
                	%>    
                	<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
                	<%} %>
                 </select>	
             </td>  
			<td>
				<input  type="button" value="Financial Year Wise" class="fb2"   onclick="report1(19)"/>  
			</td>
	</Tr> 
		<tr>
			<td align="center" colspan="4">
					<input type="button" value="EXIT" class="fb2"  onclick="window.close()"> 
			</td>
		</tr>
</table>
           <input type=hidden id="fin_year" name="fin_year"/>  
           <input type=hidden id="fin_year1" name="fin_year1"/>
</form>
</body>
</html>
 