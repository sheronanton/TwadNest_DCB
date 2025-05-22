<html>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet,java.text.NumberFormat,java.text.DecimalFormat"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection,java.math.BigDecimal"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Month wise Breakup Scheme Expenditure for Fin. Year</title>
<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 1000,700 )
			}
			function fls()
			{
				 var first=document.getElementById("pr_status").value;
				 if (first==0)
					{
					document.getElementById("lbl").innerHTML="<font size=2 color='red'><b>Processing...Pls Wait</b></font>";
					
					}
					else
					{    try { 
						     document.getElementById("lbl").innerHTML="";
							 }catch(e) {}
					}
			}
			function setf()
			{

				 document.getElementById("pr_status").value=0;
			}
			</script>
</head>
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<body onload="page_size(),fls() ">
<form name="myform" action="monthwise_rpt.jsp" onsubmit="setf(),fls()">
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
		String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");
		
		
		
		
		obj.createStatement(con);
		ResultSet rs=obj.getRS("select * from PMS_AME_MST_DESC where  active_status='A' and PERFORM_DESC_SNO  NOT IN  ( SELECT PERFORM_DESC_SNO FROM PMS_AME_MST_DESC WHERE datatype =4    ) order by DISPLAYORDER ");
		String sch_sno=obj.setValue("sch_sno",request);
		String pyear=obj.setValue("pyear",request);
		String sch = obj
		.combo_str(
				"PMS_SCH_MASTER",
				"SCH_NAME",
				"SCH_SNO",
				" where   sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="
						+ Office_id + ") order by sch_sno", "sch_sno", sch_sno,
				"class='select' style='width: 380'");
		String[] monthArr = { "Select","Apr", "May", "Jun", "Jul", "Aug", "Sep",
				"Oct", "Nov", "Dec","Jan", "Feb", "Mar" };
		String[] monthArrv = { "0", "4", "5", "6",
				"7", "8", "9", "10", "11", "12",
				"1", "2", "3" };
		NumberFormat formatter = new DecimalFormat("#0.00");
		String sel_sch=obj.getValue("PMS_SCH_MASTER","SCH_NAME"," where SCH_SNO="+sch_sno);
		int prvfinyear=obj.prvfinyear(year,month);
		String year_set="";
%>
<table width="100%" class="table" align="center" border="1" cellpadding="5">
	<tr>
		<th colspan="2" align="center" class="tdH">Month wise Breakup Scheme Expenditure for Financial Year </th>
	</tr>
	<tr>
		<td width="25%"><label class="lbl">Division Office Name :</label></td>
		<td align="left"><label class="lbl"><%=Office_Name%></label></td>
	</tr>
	<tr>
		<td width="25%"><label class="lbl">Scheme Name : </label></td>
		<td align="left"><%=sch%></td>
	</tr>
	<tr>
		<td><label class="lbl">Financial Year</label></td>

 
		<Td colspan="2"><select class="select" id="pyear" name="pyear" style="width: 80pt;" >
			<option value="0">Select</option>
			<%
				for (int i = year-2; i <= year ; i++) 
				{ 
					year_set=(i+"-"+(i+1));
					if (pyear.equals(year_set.trim())) { %>					
					<option value="<%=i%>-<%=i + 1%>" selected><%=i%>-<%=i+1%></option>
				<% } else {  %>
					<option value="<%=i%>-<%=i + 1%>" ><%=i%>-<%=i + 1%></option>
				<%} %>

 
			<%
				}
			%>
		</select></tD>
		  
	</tr>
	 
	<tr><td colspan="2" align="center"> <input type="submit" value="Show"  class="btn" ><input type="button" value="Close"  class="btn" onclick="window.close()"/> </td></tr>
	<tR><td colspan="2" align="right"><label id="lbl"></label></td></tR>    
</table>
		  
			
			      
<%
		if (!pyear.equalsIgnoreCase("0"))
		{
%>	<table align="center" width="100%" cellpadding="4" cellspacing="0" border="1"  class="tab" >
		<tr bgcolor="skyblue"><td colspan="12" align="left">&nbsp;&nbsp;&nbsp;<label class="lbl"><font color='blue'>Financial Year <%=pyear%></font> </label></td><td align="right"><label class="lbl"><font color='blue'><%=sel_sch%></font></label>&nbsp;&nbsp;&nbsp;</td></tr>
		</table>
		<table align="center" width="100%" cellpadding="4" cellspacing="0" border="1"  class="tab" >
				<tr>
					<td></td>
					<td>Units</td>
					<% for (int i=1;i<=monthArr.length-1;i++){
						out.println("<td align='center' width='5%'><label class='lbl'>"+monthArr[i]+"</label></td>");
					}%>
				<td style="background-color: skyblue" align='center' width='5%'><label class='lbl' style="color: black;">Total</label></td>	
				</tr>   
				
				<%
				int psno=0;
				String units="";
				while (rs.next()) 
				{  
					String nsm=rs.getString("PERFORM_DESC");
					 psno=rs.getInt("PERFORM_DESC_SNO");
					 units=obj.isNull(rs.getString("UNITS"),3);
					%>
				<tr>
					<td  width='25%'><label class='lbl'><%=nsm%></label></td>
					<td> <%=units%></td>
					<%
					float tot_=0.0f;
					for (int i=1;i<=monthArr.length-1;i++)
					{
						String amt=obj.getValue("PMS_AME_TRN_SCH_PERFORM_MTH","sum(QTY_OR_AMOUNT)"," where  SCH_SNO="+sch_sno+" and   PERFORM_DESC_SNO="+rs.getString("PERFORM_DESC_SNO")+" and PERFORM_DESC_SNO NOT IN  ( SELECT PERFORM_DESC_SNO FROM PMS_AME_MST_DESC WHERE datatype  = 4    ) and OFFICE_ID="+Office_id+" and FIN_YEAR='"+pyear+"' and month="+monthArrv[i]);
					/*	if (psno!=7)  
						{
							 if (!amt.equalsIgnoreCase("0"))
							   amt=formatter.format(Double.parseDouble(amt));
							 else
							   amt=""; 
						}
						else
						{
							   amt=obj.getValue("PMS_AME_TRN_SCH_PERFORM_MTH","REMARKS"," where  SCH_SNO="+sch_sno+" and   PERFORM_DESC_SNO="+rs.getString("PERFORM_DESC_SNO")+" and PERFORM_DESC_SNO   IN  ( SELECT PERFORM_DESC_SNO FROM PMS_AME_MST_DESC WHERE datatype <>4    ) and OFFICE_ID="+Office_id+" and FIN_YEAR='"+pyear+"' and month="+monthArrv[i]);
							   if (amt.equalsIgnoreCase("0"))    
							   amt=""; 
						}
					*/
						DecimalFormat d=new DecimalFormat("###.###");						
						tot_=tot_+Float.parseFloat(amt);										
						out.println("<td align='right' width='6%'><label class='lbl'>"+d.format(Double.parseDouble(amt))+"</td>");	
					}
					DecimalFormat d1=new DecimalFormat("###.###");		
						if (psno==4)
						{  
						  
						%>		
						<td align='right' width='6%'><label class='lbl'><%=Math.round((tot_/12)*100)/100 %></label></td>
						<% }  else { %>
						<td align='right' width='6%'><label class='lbl'><%=d1.format(tot_)%></label></td>
						<% } %>
				</tr>
				<% } %> 
				
		</table>
		<% } %>
				<input type=hidden id="pr_status" value="1">
				<script type="text/javascript">
					 document.getElementById("pr_status").value=1; 
				</script>
</form>

</body>  


 


</html>
