<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scheme Wise Pumping Return </title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">
 function s()
 {
	 var selectedArray1 = new Array();
	 var selectedArray = new Array();
	 var selObj = document.getElementById('pyear');
	 var i;
	 var count = 0;
	 for (i=0; i<selObj.options.length; i++) 
	 {
	    if (selObj.options[i].selected) {
	      selectedArray[count] = "'"+selObj.options[i].value+"'";
	      selectedArray1[count] = selObj.options[i].value;
	      count++;
	    }
	  }
	 
	  document.getElementById('fin_year').value=selectedArray;
	  document.getElementById('fin_year1').value=selectedArray1;

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

	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID"," where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals(""))
		Office_id = "0"; 
	out.println(Office_id);
	String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");
	out.println(Office_Name);
	String sch_sno=obj.setValue("sch_sno",request);  
	int m= Integer.parseInt(obj.setValue("pmonth",request));
	String mv=obj.month_val2(m);
	String sch = obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+ Office_id + ")", "sch_sno",sch_sno,"class='select' style='width: 262'");
	
	/*String[] monthArr = { "Select","April", "May", "June", "July", "August", "September","October", "November", "December" ,"January", "February", "March"};
	String[] monthArrv = { "0","4", "5", "6", "7", "8", "9","10", "11", "12", "1", "2", "3" };
	*/ 
		String[] monthArrv = { "0","1", "2", "3","4", "5", "6", "7", "8", "9","10", "11", "12"};
	String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
	
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
    int prvmonth=obj.prv_month(year,month);
	int prvyear=obj.prv_year(year,month);
	
%>
<form name="myform" action="schemeperformance_r.jsp">
<table align="center" width="100%" border=1 class="table" cellpadding="2" cellspacing="0">
		<tr> 
			<th colspan="3" align="center" class="tdH">Scheme Wise Pumping Return - <%=Office_Name %></th></tr>
		 <tr>   
			<td>Year</td> 
			<td colspan="2">
					<select class="select" id="pyear" name="pyear" style="width: 110pt;"  onchange="report_period_verify(document.getElementById('pmonth'),this)">			 
					<% 	for (int i = year-1; i <= year; i++) { 
					    if (i==prvyear) {%>
						<option value="<%=i%>" selected><%=i%></option>
					<% }else{%>
					<option value="<%=i%>"  ><%=i%></option>
						<%
						}
					    } %>
					</select>
			</td>    
		</tr>           
		<tr>  
			<td>Month</td>
			<td colspan="2">
				<select class="select" id="pmonth" name="pmonth"  style="width: 110pt;" onchange="report_period_verify(this,document.getElementById('pyear'))">
				<option value="0">Select</option>
					<%  for (int j = 1; j <=12  ; j++) {
						 if (prvmonth==j) {
						%>  
						<option value="<%=monthArrv[j]%>" selected><%=monthArr[j]%></option>
						<% } else { %>   
						<option value="<%=monthArrv[j]%>"><%=monthArr[j]%></option>
						<%} %>
					<% }  %>   
				</select>
			</td>
	</tr>
</table>
 
<table align="center" width="100%" border=1 class="tab" cellpadding="4" cellspacing="0">
		<tr>
			<th align="center" >
				<input type="button" value="Report" class="btn"   onclick="report(99)"/>
				<input type="button" value="Exit" class="btn"  onclick="window.close()"/> </th>
		</tr>
</table>
           <input type=hidden id="fin_year" name="fin_year"/>
           <input type=hidden id="fin_year1" name="fin_year1"/>
</form>
</body>
</html>
 