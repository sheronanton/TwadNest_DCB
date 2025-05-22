 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="java.util.Calendar,java.sql.Connection" %> 
 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <%@ page isThreadSafe="true"%>
 <html> 
 	<head> 
 <title>Opening Balance Report</title>
 <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
 <script type="text/javascript" src="../scripts/OpeningBalanceReport.js"></script>
 <script type="text/javascript" src="../../scripts/Basic.js"></script>
 <script type="text/javascript" src="../../scripts/cellcreate.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 <script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
 <script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
 <script type="text/javascript" src="../scripts/json_js1.js"></script>
 <script src="jquery-3.6.0.js" type="text/javascript"></script>
 


 <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
 
 		<script type="text/javascript">
 		function apsk94()
 		
 		{
 			winSearchConsumer=null;
 			winSearchConsumer= window.open("../../../../../../org/PMS/PMS1/DCB/jsps/DCB_Ben_Type_Rep94.jsp"); 
 				    winSearchConsumer.moveTo(250,250);  
 				    winSearchConsumer.focus();	
 		}
function apsk95()
 		
 		{
 			winSearchConsumer=null;
 			winSearchConsumer= window.open("../../../../../../org/PMS/PMS1/DCB/jsps/DCB_Ben_Type_Rep95.jsp"); 
 				    winSearchConsumer.moveTo(250,250);  
 				    winSearchConsumer.focus();	
 		}
 		
function apsk96()
	
	{
		winSearchConsumer=null;
		winSearchConsumer= window.open("../../../../../../org/PMS/PMS1/DCB/jsps/DCB_Ben_Type_Rep96.jsp"); 
			    winSearchConsumer.moveTo(250,250);  
			    winSearchConsumer.focus();	
	}
	
function apsk97()

{
	winSearchConsumer=null;
	winSearchConsumer= window.open("../../../../../../org/PMS/PMS1/DCB/jsps/DCB_Ben_Type_Rep97.jsp"); 
		    winSearchConsumer.moveTo(250,250);  
		    winSearchConsumer.focus();	
}
function apsk98()

{
	winSearchConsumer=null;
	winSearchConsumer= window.open("../../../../../../org/PMS/PMS1/DCB/jsps/DCB_Ben_Type_Rep98.jsp"); 
		    winSearchConsumer.moveTo(250,250);  
		    winSearchConsumer.focus();	
}

function apsk99()

{
	winSearchConsumer=null;
	winSearchConsumer= window.open("../../../../../../org/PMS/PMS1/DCB/jsps/DCB_Ben_Type_Rep99.jsp"); 
		    winSearchConsumer.moveTo(250,250);  
		    winSearchConsumer.focus();	
}

 		
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

 		
 		
 		
 		
 		function rld1(a)
 		{
 			document.forms["myform"].submit();
 		}
 		</script>
		 <%
		 	String userid="112",Office_id="",Office_Name="";
			String smonth="",syear="",html="";  
			String process="0";
			Controller obj=new Controller();
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
			String combo1="",office_="";
			Connection con; 
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

			 //   Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			    if (Office_id.equals("")) Office_id="0";
				Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
				smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		   		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
			    obj.conClose(con);
			    if (Office_id.equals("")) Office_id="0";
				String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
				String disable="";
				 if (Integer.parseInt(Office_id)!=5000)
				 disable=" disabled=disabled";
			    String rd="";    
				combo1=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID ","regdiv",regdiv," class=select onchange='rld1(this.value)' "+disable);
				if (Office_Name.equals("0")) Office_Name="";
				if (Integer.parseInt(Office_id)==5000) rd=""; else rd="where OFFICE_ID="+Office_id;
				 
				if (Integer.parseInt(Office_id)!=5000)
				office_=obj.combo_str("COM_MST_OFFICES","OFFICE_NAME","OFFICE_ID"," where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP "+rd+" )   ","div",Office_id," class=select  "  );
				else
				office_=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='DN' and  REGION_OFFICE_ID="+regdiv+" and  OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP  )  ","div",Office_id," class=select "  );
			    process=obj.setValue("process",request);
			}catch(Exception e) {
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
		 %>
 	</head>
 	    <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
	
	 <% 
 	  
		con=obj.con();
		obj.createStatement(con);
	//	String userid="0";

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
	
<!-- 	<body> -->
	
	 <body onload="json(2,'bentype',2)"> 
 	 
	 <form action="" name="myform">
		<table cellpadding="10" class="table" id="data_table" width="65%" align="center" border="1" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  >
		 <tr class="tdH">
		    <td align="center" height="20px" colspan="4" height="10">
		    Opening Balance Report   <jsp:expression>Office_Name</jsp:expression> <font class="tdText" color="red"><label id="msg"></label></font>
		    </td>
		 </tr>
		 <tr>
			<td><font color="#0000A0">Region</font></td>
			<td><%=combo1%></td>
		</tr> 
	<tr>	
			<td class=tdText width="40%"><font color="#0000A0">Division </font></td>
     		<td><%=office_%></td>
    </tr>
	<tr class="tdText"> 
		<td width="20%" class="tdText"><b> Billed Month And Year</b>  </td>
		<%
	    String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };	 
		%>
		<td width="40%" align="left">
		<select class="select" id="smonth" name="smonth" onchange="report_period_verify(this,document.getElementById('year'))">
			<option value="">-Month-</option>
			<%
			for (int i=1;i<=12;i++)
			{
				if (i==prv_month) 
				{   
			%><option value="<%=i%>" selected="selected"><%=amonth[i]%></option><%} else { %>
			  <option value="<%=i%>"><%=amonth[i]%></option><%} %>
					         <%} %>
		</select>
		<select class="select" id="year" name="year" onchange="report_period_verify(document.getElementById('smonth'),this)">
			<option value="">-Year-</option>
			<%
            for (int j=year-6;j<=year;j++)
            {
            	if (j==prv_year)
                {
            %><option value="<%=j%>" selected="selected"><%=j%></option><%} else { %>
            <option value="<%=j%>"><%=j%></option><%} %>
            <%} %>
		</select></td>
		 </tr> 
	 
	  
	  
				
				 <tr>
		<td>Report Type</td>
		<td>
			<select id="reporttype" name="reporttype">
				<option value="1">PDF</option>
				<option value="2">Excel</option>
				<option value="3">HTML</option>
				
			</select>
		</td> 
	</tr>
		 <tr class="tdH">
		 			<td colspan="3" align="center"><%
		 		if (Integer.parseInt(Office_id)==5000)
		 		{%>
		 			<%-- <input type="button" class="fb2" value="Division Wise" onclick="callServer2('DivisionWise');"/>	
		 			<input type="button" class="fb2" name="dash1" id="dash1"  value="Dashboard Report" onclick="callServer2('dashboard');">	 	
		 			<input type="button" class="fb2" name="watercharges" id="watercharges"  value="Water Charges Report" onclick="callServer2('watercharges');">	
		 			--%> 			
		 		<%}
		 		%>
		 		
		 		
		 
		 	<input type="button" class="fb2" name="Ldata2" id="Ldata2"  value="BenficiaryWise Report" onclick="callServer2('Ldata');"> 
		 
		          
		          <input type="hidden" id="ttmonth" name="ttmonth" value='<%=smonth%>'> 
		          <input type="hidden" id="process" name="process" value='<%=process%>'>
		          
		          
		 </tr> 
		 
		 <tr>
		 
		 	 			 	
	
		
			<td colspan="3" align="center">
			
			
		 			 	
		</td>   
		 
		 </tr>
		 
		 
		 
		 
		
	<tr><td colspan="3"><center><input type="button" class="fb2" value="Exit" onclick="self.close();"/></center></td></tr>
	<tr>
		<td colspan="3"><input type="hidden" class="fb2" name="dcbb2" id="dcbb2" value="Print DCB(SCH)" onclick="callServer3('DCB');"></td>
	 </tr>
		</table>
	</form></body>
</html>