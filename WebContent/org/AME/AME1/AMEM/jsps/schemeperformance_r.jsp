<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Scheme Performance Comparison Yearwise. </title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript">
 function s()
 {


	 var selectedArray1 = new Array();
	  var selectedArray = new Array();
	  var selObj = document.getElementById('pyear');
	  var i;
	  var count = 0;
	  try
	  {
		  for (i=0; i<selObj.options.length; i++) {
		    if (selObj.options[i].selected) {
		      selectedArray[count] = "'"+selObj.options[i].value+"'";
		      selectedArray1[count] = selObj.options[i].value;
		      count++;
		    }
		  }
	  }catch(e){}
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
 <script type="text/javascript" src="../scripts/cellcreate.js"></script>   
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
	String UNITS="";
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID"," where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals(""))
		Office_id = "0";
	String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");
	
	String sch_sno=obj.setValue("sch_sno",request);
	int m= Integer.parseInt(obj.setValue("pmonth",request));
	String mv=obj.month_val2(m);
	String sch = obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+ Office_id + ") order by sch_sno", "sch_sno",sch_sno,"class='select' style='width: 262'");
	//String[] monthArr = { "Select","April", "May", "June", "July", "August", "September","October", "November", "December" ,"January", "February", "March"};
	String[] monthArrv = { "0","1", "2", "3","4", "5", "6", "7", "8", "9","10", "11", "12"};
	String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
	
	String cyear="";
	if (m !=0 )
	{
		try
		{
					if ( m>=4 )
						cyear=obj.setValue("pyear",request).split("-")[0];
					else
						cyear=obj.setValue("pyear",request).split("-")[1];
		}catch(Exception e) {
			
			out.println(e);
		}
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
  int prvfinyear=obj.prvfinyear(year,month);
  int prvmonth=obj.prv_month(year,month);
%>
<form name="myform" action="schemeperformance_r.jsp">

<table align="center" width="100%" border=1 class="table" cellpadding="2" cellspacing="0">
		<tr>
			<th colspan="4" align="center" class="tdH">Scheme Performance Comparison Yearwise</th></tr>
		<tr>
			<td width="25%"><label class="lbl">Scheme Name : </label></td>
			<td align="left"><%=sch%></td>
		</tr>
		 <tr>  
			<td><label class="lbl">Financial Year</label></td>
			<td colspan="2">
					<select class="select" id="pyear" name="pyear" style="width: 110pt;"  >			 
					<% for (int j=year-1;j<=year+year_incr;j++) 
					{
						if ( prvfinyear==j-1)
						{
					%>    
                		<option value="<%=j-1%>-<%=j%>" selected><%=j-1%>-<%=j%></option>
                	<%
						}else
						{
						%>    
	                		<option value="<%=j-1%>-<%=j%>"><%=j-1%>-<%=j%></option>
	                	<%		
						}
                		} %>
					</select>
			</td>       
		</tr>
		<tr>
			<td><label class="lbl">Upto Month</label></td>
			<td colspan="2">
				<select class="select" id="pmonth" name="pmonth" onchange="s();sub()" style="width: 110pt;"  >
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
<% if (!fin_year.equalsIgnoreCase("0")) { %>
	<table align="center" width="100%" border=1 class="table" cellpadding="5" cellspacing="0" class="tdH">
	<tr>
			<td class="lbl"  align="center" width=2%>Sl.No</td>
			<td class="lbl" align="center"  width=10%>Description</td>
			<td class="lbl"  align="center" width=5%>&nbsp;</td>
			<%
			 int sch_sno1=0;
			 PreparedStatement p1=con.prepareStatement("SELECT * FROM PMS_SCH_MASTER where SCH_SNO="+sch_sno);
			 ResultSet rs1=p1.executeQuery();
			 int r=0;
			 while (rs1.next())
			 {
			   r++;
			   sch_sno1=rs1.getInt("SCH_SNO");					 
			%>
			<td class="lbl" bgcolor='#fffff1' align="center" colspan="<%=sr.length+1%>"><%=rs1.getString("SCH_NAME")%></td>
			<%}%>
	</tr>
	<tr>
		    <td></td><td></td><td></td>
			<%
				for (int i=1;i<=r;i++)
				{
					
					out.println(td);	
				}
			%>
			 
		
		</Tr>
			<%
			  PreparedStatement p2=con.prepareStatement("SELECT * FROM PMS_AME_MST_DESC where active_status='A'  order by DISPLAYORDER");
			  ResultSet rs2=p2.executeQuery();
			   int r1=0;
			   int psno=0;
			  while (rs2.next())
			  {
				  psno=rs2.getInt("PERFORM_DESC_SNO");
				  String dtype=obj.isNull(rs2.getString("DATATYPE"),1);
				  UNITS=obj.isNull(rs2.getString("UNITS"),2);
			  %>
				<tr>
					<td class="lbl" width="2%"><%=++r1%></td>
					<td class="lbl" width="40%"><%=obj.isNull(rs2.getString("PERFORM_DESC"),2)%></td>	
					<td><%=UNITS%></td>				
					<%
				 		for (int j=1;j<=r;j++)  
						{
							for (int k=sr.length-1;k>=0;k--)
							{
								   String amt="&nbsp;";
								 if (!dtype.equalsIgnoreCase("4"))
								 {
								  amt=obj.getValue("PMS_AME_TRN_SCH_PERFORM_YEAR","sum(QTY_OR_AMOUNT)" ," where FIN_YEAR='"+sr[k]+"' and  SCH_SNO="+sch_sno1+" and  PERFORM_DESC_SNO="+psno+" and PERFORM_DESC_SNO  not in ( select PERFORM_DESC_SNO from  PMS_AME_MST_DESC where DATATYPE=4) and  OFFICE_ID="+Office_id);
								   amt=formatter.format(Double.parseDouble(amt));
								 }
								 else
								 {
									 amt=obj.getValue("PMS_AME_TRN_SCH_PERFORM_YEAR","REMARKS" ," where FIN_YEAR='"+sr[k]+"' and  SCH_SNO="+sch_sno1+" and  PERFORM_DESC_SNO="+psno+"  and PERFORM_DESC_SNO  in ( select PERFORM_DESC_SNO from  PMS_AME_MST_DESC where DATATYPE=4) and  OFFICE_ID="+Office_id);
									 if (amt.equalsIgnoreCase("0")) amt="&nbsp;";
								 }
								    out.println("<TD align=right  width='15%'>"+amt+"</td>");
								     amt="";
								    
									
								    
							}
							  String camt="&nbsp;";
								 if (!dtype.equalsIgnoreCase("4")) {
								   camt=obj.getValue("PMS_AME_TRN_SCH_PERFORM_MTH","sum(QTY_OR_AMOUNT)" ," where FIN_YEAR='"+obj.setValue("pyear",request)+"' and  SCH_SNO="+sch_sno1+"     "+mstr+"  and PERFORM_DESC_SNO="+psno+" and PERFORM_DESC_SNO  not in ( select PERFORM_DESC_SNO from  PMS_AME_MST_DESC where DATATYPE=4) and  OFFICE_ID="+Office_id); 
								   camt=formatter.format(Double.parseDouble(camt));
								 }
								 else
								 {
									 camt=obj.getValue("PMS_AME_TRN_SCH_PERFORM_MTH","REMARKS" ," where FIN_YEAR='"+obj.setValue("pyear",request)+"' and  SCH_SNO="+sch_sno1+" and (  PERFORM_DESC_SNO="+psno+"  and PERFORM_DESC_SNO    in ( select PERFORM_DESC_SNO from  PMS_AME_MST_DESC where DATATYPE=4)  )      "+mstr+" and  OFFICE_ID="+Office_id);
									 if (camt.equalsIgnoreCase("0")) camt="&nbsp;";
								 }
								    out.println("<TD align=right width='15%'>"+camt+"</td>");
								    camt="";
						}
					%>					
				</tr>  
			  <%}  
			 %>
		<tr>
			
		</tr>

</table>

<% } %>
<table align="center" width="100%" border=1 class="tab" cellpadding="4" cellspacing="0">
		<tr>
			<th align="center" >
			<input type="button" value="Print" class="btn"  onclick="report(7)"/>
			<input type="button" value="Exit" class="btn"  onclick="window.close()"/> </th>
		</tr>
		
</table>
           <input type=hidden id="fin_year" name="fin_year">
           <input type=hidden id="fin_year1" name="fin_year1">
</form>
</body>
</html>
 