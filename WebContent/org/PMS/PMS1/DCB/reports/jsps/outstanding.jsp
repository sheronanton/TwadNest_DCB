<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <%@page import="java.util.Calendar,java.net.*"%>
 
<%@page import="java.net.DatagramPacket"%><html> 
 	<head>
 		<title>TWAD : Division Report</title>
 		<script type="text/javascript" src="../scripts/OpeningBalanceReport.js"></script>
 		<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 		<script type="text/javascript" src="../../scripts/Basic.js"></script>
	 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 		<script type="text/javascript">
 		function rld()
 		{
 	 		var mon=document.getElementById("pyear").value; 
 			var smonth=document.getElementById("smonth").value;
 			var res=month_year_check(smonth,mon);  
 		  	if (res!=1)
 			{  
				document.location.href="outstanding.jsp";
				document.forms["set"].submit();
 			}
 	 	}
 		
 		</script>
		 <%
		 	String userid="112",Office_id="",Office_Name="";
			String smonth="",syear="",html="";
			String process="0",ben_type="0";
			Controller obj=new Controller();
			Connection con; 
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR),ben_count=0,ben_count1=0;
			try
			{
			
				
				con=obj.con();
				obj.createStatement(con);
				 
				userid=(String)session.getAttribute("UserId");
			
				if(userid==null)
				{
				 	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		//	    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			    if (Office_id.equals("")) Office_id="0";
				Office_Name=obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
				
				//smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);				
		   		//syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		   		smonth=obj.setValue("smonth",request);
		   		syear=obj.setValue("pyear",request);
		   	    ben_type=obj.setValue("BEN_TYPE_ID",request);
			    obj.conClose(con);
			      ben_count=obj.getCount("PMS_DCB_MST_BENEFICIARY"," where office_id="+Office_id+" and status='L'");
			  
			 	
			      ben_count1=obj.getCount("PMS_DCB_MST_BENEFICIARY"," where office_id="+Office_id+" and status='L' and BENEFICIARY_TYPE_ID="+ben_type);
			  
			    process=obj.setValue("process",request);
			}catch(Exception e) {
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}  
			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
			String BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")","BEN_TYPE_ID",ben_type,"class='select' style='width: 262' " );
		
			%>
 	<script type="text/javascript"> 
 	
 			function rpt()
 			{
 	 			var year=document.getElementById("pyear").value;
 	 	 		var month=document.getElementById("smonth").value;
 	 	 		var BEN_TYPE_ID=document.getElementById("BEN_TYPE_ID").value;
 	 	 		//alert(BEN_TYPE_ID);
 	 	 		var nots=0;
 	 	 		try
 	 	 		{
 	 	 			nots=document.getElementById("nots").value;
 	 	 		}catch(e) {nots=0;}	
 	 	 		var flag=document.getElementById("flag").value;
				window.open("../../../../../../Bill_Demand_Report?month="+month+"&nots="+nots+"&BEN_TYPE_ID="+BEN_TYPE_ID+"&flag="+flag+"&year="+year+"&command=pdf&process_code=10","","");

 	 		}
 	 		
 			function fst(){
 	 			var BEN_TYPE_ID= document.getElementById("BEN_TYPE_ID").value;
 	 			var flag= document.getElementById("flag").value;
 	 			document.location.href="outstanding1.jsp?BEN_TYPE_ID="+BEN_TYPE_ID+"&flag="+flag;
 	 			
 	 		}
 	 		
 			
 	</script>
 
 	</head>
 	    <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 	
<body>
<form name="set">
<%
	String flag=obj.setValue("flag",request);

	 

	String Head="";  
	
	if (flag.equalsIgnoreCase("1")) Head="Beneficiary  Outstanding List";
	if (flag.equalsIgnoreCase("2")) Head="Beneficiary  Pumping Return Zero List";

%>
<input type="hidden" id="flag" name="flag" value="<%=flag%>">
 <table cellpadding="10" class="table" id="data_table" width="65%" align="center" border="1" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  >
		 <tr class="tdH">
		          <td align="center" height="20px" colspan="4" height="10">
		          			<%=Head%> - <jsp:expression>Office_Name</jsp:expression> <font class="tdText" color="red"><label id="msg"></label></font>
		          </td>
		 </tr>
		    
				<tr class="tdText"> 
					<td width="20%" class="tdText"><b> Year &nbsp;&nbsp;&nbsp;</b>  
					   <select class="select" id="pyear" name="pyear" style="width: 110pt;" onchange="report_period_verify(document.getElementById('smonth'),this)">
					<option value="0">- Year -</option>			 
					<% 	for (int i = year-1; i <= year; i++) { %>
						<option value="<%=i%>"> <%=i%></option>
					<% 	} %>
					</select></td>
			        <td width="50%" align="left">
			        
					 Month &nbsp;&nbsp;&nbsp;
		 <select class="select" id="smonth" name="smonth"  style="width:100pt" onchange="report_period_verify(this,document.getElementById('pyear'))" >
			        <option value="0">- Month -</option>		
		   			<%
		 				for (int i=1;i<cmonth.length;i++)
					{%>
						<option value="<%=i%>" ><%=cmonth[i]%></option>
		  		   <%}
		 		 %>  
				 </select>
			        </td>
		        </tr> 
				<Tr>
					<td>Beneficiary Type</td><td><%=BEN_TYPE_ID%></td>
				</Tr>
			<%
			if (flag.equalsIgnoreCase("1"))
			{
			%>	
				<tr>
					<td>Number of Beneficiaries to be Listed</td>
					<td>
							<input type="text" class="tb1" id="nots" name="nots" value=""/>&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;<%=ben_count1%>
						</td><td align="right">	<font size=3>Division Total Beneficiaries  : <%=ben_count%></font></td>
						
				</tr>
			<% } %>	
				<tr>
					<td colspan="3">
						<center>  
							
							<input type="button" class="fb2" value="View PDF" onclick="rpt();"/>
							<input type="button" class="fb2" value="Exit" onclick="self.close();"/>
						</center>
					</td>
				</tr>
				
				<tr class="tdH">
		          <td align="center" height="20px" colspan="4" height="10">
		          </td>
		         
		         <input type="hidden" id="flag" value="<%=flag%>">
 		 </tr> 
		</table>
</form>
</body>
</html>