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
 	color:#D6297C;
 	font-weight: bold;
 	
  }
 .bld2
 {
 	font-family:sans-serif;
 	font-size: 15px;
 	font-weight:bold;
 }
 </style>
<title>TWAD : Water Charges Calculation</title>
<script type="text/javascript" src="../scripts/OpeningBalanceReport.js"></script>
<script type="text/javascript" src="../../scripts/Basic.js"></script>
<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/msg.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>

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
			con=obj.con();
			obj.createStatement(con);
			userid=(String)session.getAttribute("UserId");
			if(userid==null)  
			{
				 	response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			if (Office_id.equals("")) Office_id="0";
			Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
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
 
<body>
<form name="wccal">
	<table align="center" width="70%"    cellpadding="5" cellspacing="0"  > 
 	 	<tr><th colspan="7" align="center"><%=Office_Name%></th></tr>
 		<tr><td colspan="7" align="center"> Water Charge Calculation</td></tr>
 		<tr></tr>
		<tr><td>Month  : <%=cmonth[Integer.parseInt(smonth)] %></td>	<td  class="tdText" colspan="6">  Year : <%=syear %></td></tr>
		<tr> 
			<th rowspan="2"  align="center">Type</th>		
			<th colspan="3" >Beneficiary</Th>
 			<th rowspan="2" width='15%'   align="center">Pumping Quantity[KL] </th>
 			<th rowspan="2" width='15%'  align="center">Pumping Return Status </th>
 			<th rowspan="2" width='15%'  align="center">Water Charges [Rs] </th>
 		</tr>
 				<tr>		 
 					<th align="center">Total</th> 					
 					<th align="center">Total PR Entered</th>
 					<th align="center">Total WC Calculated</th>
 				</tr>  				 				
 				<tr class='bld1' bgcolor="skyblue"></tr>
 				<%
		 		  String color_code1="",color_code2="",common_color="";
		 		  int total_pr_ben=0,total_added_ben=0,total_ben=0;
				  String qty="0",st,wcamt="0.0";    
		 		  double net_pr=0.0,net_amt=0.0;
		 		  int net_pr_ben=0,net_added_ben=0,net_total_ben=0;
		 		  String ac_id=obj.accountUID(Integer.parseInt(Office_id));
		 		  String reg_id=obj.regionID(Integer.parseInt(Office_id));
		 		  String str_cir=" ";
		 		  	if (!ac_id.equalsIgnoreCase("0"))
		 		  	str_cir=" and OFFICE_ID="+Office_id;
		 		  	else
		 		  	str_cir=" and ACTUAL_OFFICE_ID="+Office_id;
		 		  	
		 		  int rc = obj.getCount("PMS_DCB_JOURNAL_DETAILS","where CASHBOOK_MONTH="+smonth+"and CASHBOOK_YEAR="+syear+""+str_cir);//" and OFFICE_ID="+Office_id);
		 		  while (rs.next())       
		 		  {  
		 				 String BEN_TYPE_DESC=rs.getString("BEN_TYPE_DESC");
 						 BEN_TYPE_ID =rs.getString("BEN_TYPE_ID");
 						 qty=obj.getValue("PMS_DCB_TRN_MONTHLY_PR","sum(QTY_CONSUMED_NET)","qty"," where METRE_SNO in (select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+"  and METER_STATUS='L') and   BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and STATUS='L' and BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+") and  OFFICE_ID="+Office_id+" and year="+syear+" and month="+smonth+" group by year,month");
 						 st=obj.getValue("PMS_DCB_TRN_MONTHLY_PR"," ( case when PROCESS_FLAG='CR' then 'Create' "+
			 							" when PROCESS_FLAG='FR' then 'Freezed' "+
			 							" when PROCESS_FLAG='V' then '<font color=red>Not Freezed</font>' "+  
			 							" end)","status"," where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and STATUS='L' and BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+") and  OFFICE_ID="+Office_id+" and year="+syear+" and month="+smonth+" ");
 						 net_pr+=Double.parseDouble(qty);
						 total_pr_ben=obj.dist_getCount("PMS_DCB_TRN_MONTHLY_PR","where OFFICE_ID=" + Office_id+" and month="+smonth+" and year="+syear+" and BENEFICIARY_SNO in  (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and STATUS='L' and BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+")","BENEFICIARY_SNO");
 			 			 total_added_ben=obj.dist_getCount("PMS_DCB_WC_BILLING","where OFFICE_ID=" + Office_id+" and month="+smonth+" and year="+syear+" and BENEFICIARY_SNO in  (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and STATUS='L' and BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+")","BENEFICIARY_SNO");
 			 			 total_ben=obj.dist_getCount("PMS_DCB_MST_BENEFICIARY","where status='L' and OFFICE_ID=" + Office_id+" and BENEFICIARY_SNO in  (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" and METER_STATUS='L' ) and BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+"" ,"BENEFICIARY_SNO");
 			 			 net_pr_ben+=total_pr_ben;
 			 			 net_added_ben+=total_added_ben;
 			 			 net_total_ben+=total_ben;
 			 			 color_code1=(total_ben!=total_pr_ben)?"color=red size=3>**&nbsp;":"color='green'>";				
 			 			 color_code2=(total_ben!=total_added_ben)?"color=red size=3>**&nbsp;":"color='green'>";
 			 			 //common_color=( (total_ben!=total_pr_ben) || (total_ben!=total_added_ben))?"bgcolor=lightgray":"bgcolor='white'";
 			 					 if (rc>0)    
						 		{
						 				wcamt=obj.isNull(obj.getValue("PMS_DCB_WC_BILLING","sum(TOTAL_AMT)","calamt", "where BENEFICIARY_SNO in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and STATUS='L' and BENEFICIARY_TYPE_ID="+BEN_TYPE_ID+") and  OFFICE_ID="+Office_id+" and year="+syear+" and month="+smonth),1);						    		 
						    		 	net_amt+=Math.round(Double.parseDouble(wcamt));    		
						    	}else
						    	{
						    			wcamt="0.0";  
						    	}  
						    			out.println("<tr class='bld' "+common_color+"> <td  width='30%'><b><font class='bld'>"+BEN_TYPE_DESC+"</font></td><td width='10%' align='right'><b>"+total_ben+"</font></td><td width='10%' align='right'><b><font "+color_code1+""+total_pr_ben+"</td><td width='10%' align='right'><b><font "+color_code2+""+total_added_ben+"</td><td  width='15%' class='tdText' align=right ><b>"+qty+"&nbsp;&nbsp;&nbsp;</td><Td   align='center' width='15%'><b>"+st+"</td><Td align='right' width='15%'><b>"+Math.round(Double.parseDouble(wcamt))+"&nbsp;&nbsp;</td></tr>");
 						 
 						}    
		 		  
 				  int tt=obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where  OFFICE_ID="+Office_id+" and year="+syear+" and month="+smonth+" and beneficiary_sno in (select beneficiary_sno from PMS_DCB_MST_BENEFICIARY where status='L')");
 				  int frtt=obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where  OFFICE_ID="+Office_id+" and year="+syear+" and month="+smonth+" and PROCESS_FLAG='FR' and beneficiary_sno in (select beneficiary_sno from PMS_DCB_MST_BENEFICIARY where status='L') ");
 				  String status=obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS", "WC_FREEZED"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+smonth+" and CASHBOOK_YEAR="+syear ),1);
 				 out.println("<tr class='bld2' bgcolor='skyblue'> <td  align='right'>Total </td><td width='10%' align='right'>"+net_total_ben+"</td><td width='10%' align='right'>"+net_pr_ben+"</td><td width='10%' align='right'>"+net_added_ben+"</td><td align='right'>"+Math.round(net_pr)+"&nbsp;&nbsp; </td><td class='tdText' align=right colspan=3><b>"+Math.round(net_amt)+" </td></tr>");
 				 
 				  
 				 if (tt==frtt   )  
				  { 
 					 if (rc == 0  )
 					 {
		 					if (!status.trim().equalsIgnoreCase("Y") )    
		 					{
							 	out.println("<tr> <td class='tdText' colspan='7' align=center><input type='button' value='Calculate Water Charge' onclick='journal_start()'><input type='button' value='Refresh'  onclick='javascript:window.location.reload();'/><input type='button' value='Exit' onclick='window.close()'></td></tr>");
		 					}
		 					else
		 					{
		 						out.println("<tr> <td class='tdText' colspan='7' align=center><input type='button' value='Refresh'  onclick='javascript:window.location.reload();'/><input type='button' value='Exit' onclick='window.close()'></td></tr>");
		 					}
 					 }
 					 else
 					 {
 						out.println("<tr> <td class='tdText' colspan='7' align=center><input type='button' value='Refresh'  onclick='javascript:window.location.reload();'/><input type='button' value='Exit' onclick='window.close()'></td></tr>");
 					 }
				  }
				  else
				  {
					  out.println("<tr> <td class='tdText' colspan='7' align=center><input type='button' value='Refresh'  onclick='javascript:window.location.reload();'/><input type='button' value='Exit' onclick='window.close()'></td></tr>");
				  }
 				%> 
 				<tr>
 					<td colspan="8"  valign="top"> <table><tr>   
 					<td valign="top"><img src="../../../../../images/info.jpg" height="80" width="150"></img></td><td valign="top" colspan="7"  class='bld1'>
 					<div style="vertical-align: top;width:50em;" > 
 						<br><font color=red size=4>** &nbsp;</font>Beneficiary Mismatch ....Please UnFreeze PR and Freeze PR again<br><br> Pumping Return Should be Freezed for All Beneficiary Types<br>
 					<br>				Click Refresh Button to check calculated water charges <br>
 					</div> </td>
 					</tr></table></td>
 				</tr>
 				<tr><th colspan="7" align="center">&nbsp;</th></tr>
 		</table>    
 		  <input type="hidden" id="month" value="<%=smonth%>">
 		  <input type="hidden" id="year" value="<%=syear%>">  
 		  
	</form> 	 
</body>

<%
		    obj.conClose(con);
			}catch(Exception e) {
				out.println(e);
			//	response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
%>
</html>