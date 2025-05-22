<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <%@page import="java.util.Calendar,java.net.*"%>
 
<%@page import="java.net.DatagramPacket"%><html> 
 	<head>
 	<style type="text/css">
 			.bld
 			{
 				font-family:sans-serif;
 				font-size: 12px;
 			}
 			.bld1
 			{
 				font-family:sans-serif;
 				font-size: 14px;
 			}
 			.bld2
 			{
 				font-family:sans-serif;
 				font-size: 15px;
 				font-weight:bold;
 			}
 	</style>
 		<title>TWAD : Total Demand Bill Beneficiary Type Wise</title>
 		<script type="text/javascript" src="../scripts/OpeningBalanceReport.js"></script>
 		<script type="text/javascript" src="../../scripts/Basic.js"></script>
 		<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
		<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
		<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/msg.js"></script>
			 <%
		 try
			{
		 	String userid="112",Office_id="",Office_Name="";
			String smonth="",syear="",html="";
			String process="0";
			Controller obj=new Controller();
			Connection con; 
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR),ben_count=0;
			
			String meter_status=Controller.meter_status;
				 
				con=obj.con();
				obj.createStatement(con);
				 
				userid=(String)session.getAttribute("UserId");
			
				if(userid==null)  
				{
				 	//response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				
				Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			
			 //   Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			    if (Office_id.equals("")) Office_id="0";
				Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
				smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		   		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1); 
			    
			      ben_count=obj.getCount("PMS_DCB_MST_BENEFICIARY"," where office_id="+Office_id+" and status='L'");
			    process=obj.setValue("process",request);
			
			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
			String BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")","BEN_TYPE_ID","","class='select' style='width: 262'" );
			
			
			ResultSet rs=obj.getRS("select BEN_TYPE_DESC,BEN_TYPE_ID from PMS_DCB_BEN_TYPE where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")");
		 %> 
 	<script type="text/javascript"> 
 			function rpt()
 			{
 	 			var year=document.getElementById("pyear").value;
 	 	 		var month=document.getElementById("smonth").value;
 	 	 		var BEN_TYPE_ID=document.getElementById("BEN_TYPE_ID").value;
 	 	 		var nots=0;
 	 	 		try
 	 	 		{
 	 	 			nots=document.getElementById("nots").value;
 	 	 		}catch(e) {nots=0;}	
 	 	 		var flag=document.getElementById("flag").value;
				window.open("../../../../../../Bill_Demand_Report?month="+month+"&nots="+nots+"&BEN_TYPE_ID="+BEN_TYPE_ID+"&flag="+flag+"&year="+year+"&command=pdf&process_code=10","","");

 	 		}
 	</script>
 
 	</head>
 	    <link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 	
	<body onload="clickIE4()">
 		<table align="center" width="50%" border="1" style="border-collapse: collapse;" cellpadding="5" cellspacing="0" bordercolor="darkgray"> 
 		   
 				<tr  class='bld1'><td colspan="4" align="center"><%=Office_Name%></td></tr>
 				<tr  class='bld1'><td colspan="4" align="center"> Demand Bill Check List</td></tr>
 				<tr class='bld1'> </tr>
 				<tr class='bld1'> <td  class="tdText"> Month  : <%=cmonth[Integer.parseInt(smonth)] %></td>	<td  class="tdText" colspan="3">  Year : <%=syear %></td></tr>	
 				<tr>
 				<td colspan="5">
 				<table align="center" width="100%" border="1" style="border-collapse: collapse;" cellpadding="5" cellspacing="0" bordercolor="darkgray"> 			
 				<tr class='bld1' bgcolor="skyblue"> 
 					<td class="tdText" align="center" width='25%'> Beneficiary Type </td>
 					<td class="tdText" width='10%'  align="center">Total Beneficiaries </td>
 					<td width='10%' class="tdText"  align="center">Total Generated Bill </td>
 					<td width='10%' class="tdText"  align="center">Status </td>
 					<td  class="tdText"  align="center">Action </td>
 				</tr>
 				<%
 					String met_query =" select beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where " +meter_status+"   OFFICE_ID="+Office_id;
					int netben=0,netbill=0;
 					while (rs.next())    
 					{
 						 String BEN_TYPE_DESC=rs.getString("BEN_TYPE_DESC");
 						 BEN_TYPE_ID =rs.getString("BEN_TYPE_ID");
 						 int rc = obj.getCount("PMS_DCB_JOURNAL_DETAILS","where CASHBOOK_MONTH="+smonth+"and CASHBOOK_YEAR="+syear+" and OFFICE_ID="+Office_id);
						 int type_tot_ben=obj.getCount("pms_dcb_mst_beneficiary", "where status ='L' and OFFICE_ID="+Office_id+" and beneficiary_sno in ("+met_query+")  and  BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+" and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from pms_dcb_trn_monthly_pr where month="+smonth+" and year="+syear+" and office_id="+Office_id+")"   );
						 int already_demand=obj.getCount("PMS_DCB_TRN_BILL_DMD", "where OFFICE_ID="+Office_id+" and BILL_MONTH="+smonth+" and BILL_YEAR="+syear+" and BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+" and OFFICE_ID="+Office_id+" and status='L')");
						 netben+=type_tot_ben;
						 netbill+=already_demand;
						 String img=(already_demand!=type_tot_ben)?" ../../../../../images/tick2.jpg":" ../../../../../images/tick_3.jpg";
						 String action=(already_demand!=type_tot_ben)?"<font color='red' size='2'>Re-generate Bill for this type</font>":"<font color='green' size='2'>Ok</font>";
						 if (type_tot_ben!=0) 
						 out.println("<tr class='bld'> <td ><font class='bld'>"+BEN_TYPE_DESC+"</font></td><td  class='tdText' align=center>"+type_tot_ben+"&nbsp;&nbsp;&nbsp;</td> <Td align='center'>"+already_demand+"&nbsp;&nbsp;&nbsp;</td><td align='center'><img src='"+img+"' width='20' height='20'></td><td align='center'>"+action+"</td></tr>");
 					}  
 					 out.println("<tr class='bld'  bgcolor='skyblue'> <td align='right'><font class='bld'> Total </font></td><td  class='tdText' align=center>"+netben+"&nbsp;&nbsp;&nbsp;</td> <Td align='center'>"+netbill+"&nbsp;&nbsp;&nbsp;</td><Td>&nbsp;</td><Td>&nbsp;</td></tr>");
 				%>     
 				 
 				 <tr>
 				 	<td colspan="5" align="center"><input class="fb2" type="button" name="exit" value="EXIT" id="exit" onclick="self.close()" align="middle"/></td>
 				 </tr>
 			 	</table>
 			 	</td>
 			 	</tr>
 		</table>    
 		  <input type="hidden" id="month" value="<%=smonth%>">
 		  <input type="hidden" id="year" value="<%=syear%>">  
 		  
		  <a href="journal_verificaiton.jsp"></a>
</body> 
<%
		    obj.conClose(con);
			}catch(Exception e) {
				out.println(e);
			//	response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
%>
</html>