<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="org.apache.log4j.Logger,java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Pumping Return | TWAD Nest - Phase II</title>
		<script type="text/javascript" src="../../../../../org/PMS/PMS1/CommonControls/scripts/Date_Check.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
		<title>Bill Demand</title>
			<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
		<script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
		<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/master.js"></script>
		<script type="text/javascript" src="../scripts/msg.js"></script>
		<script type="text/javascript" src="../scripts/counting.js"></script>
		<script type="text/javascript" src="../scripts/Ben_Report.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>

		<script language="javascript" type='text/javascript'>
		 var obPage = false;
		function wc_cal_prv_window(a,b,c)
		{  
			var mtype=document.getElementById("mtypestr"+c).innerHTML;
			var win=window.open("Rect.jsp?mtype="+mtype+"&ben_value="+a+"&selsno="+b+"&row="+c,'',"width=400,height=200,toolbar=false,location=false,directories=false,status=false,menubar=false,scrollbars=false,copyhistory=false,resizable=false");
		} 
		function ben_report_pump()
		{   
		 //	grid_show(3,'data','ben_data','ben_body' ,'bentype')
		}
		function show_prv(row)
		{
			 var ben_value=document.getElementById("BENEFICIARY_SNO").value;
			 var month_value =document.getElementById("month").value;
			 var year_value =document.getElementById("year").value;
			 var mv=document.getElementById("vmonth").value	 
			 var msno=document.getElementById("selsno"+row).value
			 v = window.open('prv_month_entry.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&msno="+msno,'windowname1','width=400, height=250,status=0')
			 setTimeout("v.location.href = 'javascript:self.close()'",10000);																																																  
		}
		function pump_report()
		{	
			var ben_value=document.getElementById("BENEFICIARY_SNO").value;
			var ben_type=document.getElementById("bentype").value;	
			var month_value =document.getElementById("month").value;
			var year_value =document.getElementById("year").value;
			var mv=document.getElementById("vmonth").value
			var yv=year_value;
			document.getElementById("selected_ben").value=ben_value; 
			window.open('pumping_report.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&mv="+mv+"&yv="+yv+"&ben_type="+ben_type,'windowname1','width=900, height=700,scrollbars=1,resizable=1')
		}
		function calcuate(row)
		{
		 	var METRE_INIT_READING =document.getElementById("METRE_INIT_READING"+row).value;
			if (METRE_INIT_READING=="") METRE_INIT_READING="0";
			var METRE_FIXED=document.getElementById("METRE_FIXED"+row).innerHTML;
		 	var METRE_WORKING=document.getElementById("METRE_WORKING"+row).value; 
		 	var METRE_TYPE=document.getElementById("METRE_TYPE"+row).value;  
			var read=document.getElementById("read"+row).value;
			if (read=="") read="0";	 
		  	if (METRE_FIXED=="Yes" && METRE_WORKING=="Y")
			{
				if (parseFloat(read) < parseFloat(METRE_INIT_READING))
				{
		 			msgload("Check the Closing Reading ! (row no "+row+")",1)
					document.getElementById("nounit"+row).value=0;
					document.getElementById("read"+row).value=0;
				}else
				{			 
				var nounit=roundNumber(parseFloat(read)-parseFloat(METRE_INIT_READING),3);
						if (parseFloat(METRE_TYPE)==2)
						{
						    document.getElementById("difference"+row).value=parseFloat(nounit)
							document.getElementById("nounit"+row).value=roundNumber(parseFloat(nounit)*1000,3);
							}
						else
						{	document.getElementById("difference"+row).value=parseFloat(nounit)
							document.getElementById("nounit"+row).value=parseFloat(nounit);
						}
				}
			}
		 	else
		 	{ 
		 		var s=0; 
		 	 }	
			 
		 	var rowcnt_meter=document.getElementById("rowcnt_meter").value; 	
		 	var tot_read=0;
		 	var PARENT_METRE=document.getElementById("PARENT_H_METRE"+row).value;	  
			 if (PARENT_METRE=="y")
			 {
				var net_qty=document.getElementById("nounit"+row).value;		
			 	var PARENT_METER_qty=document.getElementById("PARENT_METER"+row).value;
			 	if (net_qty=="") net_qty="0";
			 		 if (net_qty > 0 )
					 {
					     document.getElementById("nounit"+row).value=parseFloat(net_qty)-parseFloat(PARENT_METER_qty);
					  }
			 }		  
			for (i=1;i<=rowcnt_meter;i++)
			{			  	
				tot_read+=parseFloat(document.getElementById("nounit"+i).value);
			}	
				document.getElementById("netunit").value=tot_read;

				var ben_value=document.getElementById("ben_value").value;
				var selsno=document.getElementById("selsno"+row).value;
				wc_cal(parseFloat(document.getElementById("nounit"+row).value),ben_value,selsno,row);
				
				
		}  
		function ckset(r)
		{	  
			document.getElementById("netunit").value=0;  
			document.getElementById("net_wc_amt").value=0;
		 	var row=document.getElementById("rowcnt").value;
		    document.getElementById("BENEFICIARY_SNO").value=document.getElementById("ben_value").value
		 	var month =document.getElementById("month").value;
			var year=document.getElementById("year").value;
			var vtype=document.getElementById("bentype").value;
				if (vtype!=6)
				{
					 grid_show(4,'data','ben_meter_data','ben_meter_body','bentype')
					 document.getElementById('block').style.visibility = 'hidden';
					 document.getElementById('dis').style.visibility = 'hidden';	
				}
				else
				{
					grid_show(4,'data','ben_meter_data','ben_meter_body','bentype')
				}	 
		}
		function value_set(row)
		{
			document.getElementById("BENEFICIARY_SNO").value=document.getElementById("ben_value").value;
		}
		function sh()
		{
			document.getElementById('hlab').innerHTML=""
		}
		function mselection(row)
		{ 
			document.getElementById("vmonth").value=monthselect(row) ;
			 
		}
		function spl()
		{
			document.getElementById("ben_type_fltr").value=1;	 
			data_show_dcb('show',2,'bentype')
		}
		</script>  
		<%
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = 0;//cal.get(Calendar.MONTH)+1 ;
			int year =0;// cal.get(Calendar.YEAR);
			 if (month >=4)
				year=year;
			else
				year=year;
			int pumpingfalg=0;
			String Date_dis=day+"/"+(month+1)+"/"+year;
			String userid="0",Office_id="",Office_Name="",OFFICE_LEVEL_ID="0";
			Controller obj=new Controller();
			Connection con;
			try
			{
				con=obj.con();
				obj.createStatement(con);
				try
				{
				   userid=(String)session.getAttribute("UserId");
				}catch(Exception e) { }
				if(userid==null)
				{
					 response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
			//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			
				Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

				if (Office_id.equals("")) Office_id="0";
			
				Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
				
			//	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
				
				try
				{
				String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
				String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
				
				if (smonth.equalsIgnoreCase("0"))
				 month=month;
				else
				 month=Integer.parseInt(smonth);
				 
				 if (syear.equalsIgnoreCase("0"))
				  year= year;
				else
				 year=Integer.parseInt(syear);
				
				}catch(Exception e) { out.println(e); }
				 
				obj.conClose(con);
			}catch(Exception e) {
				 response.sendRedirect(request.getContextPath()+"/index.jsp");
			}	
			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		
			int month_set=0,year_set=0;
			
			
			if ((month) == 1) {
				month_set = 12;
				year_set = year - 1;
			} else {
				month_set = (month - 1);
				year_set =year;
			}
			String count1=obj.getValue("PMS_DCB_TRN_MONTHLY_PR"," count(distinct BENEFICIARY_SNO) ","c1","where OFFICE_ID="+Office_id+" and MONTH="+month_set+" and YEAR="+year_set);
			String count2=obj.getValue("PMS_DCB_TRN_BILL_DMD"," count(distinct BENEFICIARY_SNO) ","c1","where OFFICE_ID="+Office_id+" and BILL_MONTH="+month_set+" and BILL_YEAR="+year_set);
			String count3=obj.getValue("PMS_DCB_LEDGER_ACTUAL"," count(distinct BENEFICIARY_SNO) ","c1","where OFFICE_ID="+Office_id+" and MONTH="+month_set+" and YEAR="+year_set);
			//if (  Integer.parseInt(count1)!=Integer.parseInt(count3) ||  Integer.parseInt(count1)!= Integer.parseInt(count2))
			String set=obj.getValue("PMS_DCB_SETTING", "PR_ENABLE"," where OFFICE_ID="+Office_id);
			System.out.println(Office_id);
			if (!set.equalsIgnoreCase("y"))
			response.sendRedirect("warn.jsp");
			
			 
			
			if (month==0 ||  year ==0)
			{%><script type="text/javascript">
				alert("Please select month and year in Transaction Month Setting  !!!!");
				document.location.href="monthyearset_div.jsp";
				</script>
			<%
				
			}
			
			
//			select * from com_mst_all_offices_view  where   region_office_id=6777 and office_id in (select office_id from pms_dcb_div_dist_map);
			OFFICE_LEVEL_ID=obj.getValue("com_mst_offices", "OFFICE_LEVEL_ID","where OFFICE_ID="+Office_id+ "  ");
			String division_list="";
			try
			{
				 division_list=obj.combo_str("com_mst_offices","office_name","office_id","where   office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map)  ","div","class='select' onchange=focusloss()");

				// division_list=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where   circle_office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","Child_div","class='select' onchange=data_show_dcb('show',1,'subdiv')");
			}catch(Exception e) 
			{
				out.println(e);
			}
			
			
		%> 
		 <%  Logger logger = Logger.getLogger("Bill_Pumping.jsp"); %>
 </head>  
 <%
 		logger.info("Bill_Pumping");
 %>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
<link href="../../../../../css/label.css" rel="stylesheet" media="screen"/> 
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<body onload=" div_sh(),mselection('<%=month%>') , data_show_dcb('show',2,'bentype'),report('show',1,'dis_value',''),data_show_dcb('show',1,'subdiv'),frstatuscheck(),flash(),startclock()">
	<table    id="" width=100% align="center" border="0"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
	 <tr class="tdH">
	          <th colspan=4 height=30 align="center">
	         	   <font class="tdText" > Pumping Return -- <%=Office_Name%> </font> <font color="red"><label id="msg"></label></font>
	          </th>
	 </tr>
		<%
		if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
		{
		%> 
		<tr>
	 	 <td align=left valign="top" width=10% ><font class="tdText">  Division</font> </td>
	 	 <td>
	 	&nbsp;:&nbsp;<%=division_list%>
	 	</td>
	 	</tr>
	 	<%}%>
	 <tr> 
	         	 <td align=left valign="top" width=10% ><font class="tdText">Sub Division</font> </td>
	         	 <td width=15% align=left valign="top">&nbsp;:
				 <select class='select'  id="subdiv" name="subdiv" onchange="sub_div_wise_pr_total_req(1);spl()" > </select></td> 
	          	 <td width=10% align="left" valign="top"> <font class="tdText"> &nbsp;Month and Year:</font></td>
	          	 <td width=20%  ><input type=hidden id="month" name="month" value="<%=month%>" size=4 class="tb1">   <input type=text class="tb4" readonly id="vmonth" name="vmonth" value="<%=cmonth[month]%>" size=2 style="text-align: right;"  ><font class="tdText"> </font><input type=text readonly class="tb1"   id="year" name="year" value="<%=year%>" size=4>:<label id="time" class="tb4"></label> 
	            </td>
	</Tr>   
	<tr>
			 	 <td width=10% align="left" valign="top"><font class="tdText"> Beneficiary Type</font></td><td>&nbsp;:&nbsp;<select class="select" id="bentype" name="bentype" onchange="grid_show(3,'data','ben_data','ben_body' ,'bentype'),flash()"></select></tD>
				 <td   width=15% valign="top"  align=left><div id="dis"><font class="tdText">District and Block:</font></div> </td>
				 <td width=15%  valign="top" ><div id="block"><select id="dis_value"  class=select  name="dis_value" onchange="report('show',2,'block_value',this) " style="width:100pt"></select>&nbsp;&nbsp;<select id="block_value" class=select onchange="grid_show(3,'data','ben_data','ben_body' ,'bentype')" name="block_value"  style="width:100pt"></select></div> </td>
	</tr>
					<input type=hidden id="subdiv" name="subdiv"  style="width:160pt" value=0>
	<tr>
				<td valign="top">
					<font class="tdText">Beneficiary Name </font></td><td valign="top"> &nbsp;:&nbsp;<select id="ben_value" class="select" name="ben_value" onchange="ckset(1) " style="width:250pt"/>
				</td>
				<td valign="top">
					<font class="tdText"> Submission  Date</font>&nbsp; :&nbsp; </td><td valign="top"><input type="text" class="tb4" id="pumpdate" name="pumpdate"><img src="../../../../../images/calendr3.gif"  onclick="showCalendarControl(document.getElementById('pumpdate'),1);"  alt="Show Calendar"></img>  
				</td>
	</tr>
	</table>
	<table    width=100% align="center" border="0"  cellspacing="0" cellpadding="2"  >
				<tr>
				<td valign="top"><font class="tdText"><u>Reading Details</u></font> :-</td><td align="right"> <label id='data_show'></label> </td>
				</tr>
				<Tr valign="top"  >
					<td valign="top" width="100%" align="left" style="height: 400px; " colspan="2">
					<table  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"   id="ben_meter_data" width="100%" align="center" border="0"   cellspacing="0" cellpadding="2"  >
							<tr valign="top"   > 
										<th width="25%" valign="middle"  align="left"> <font class="tdText"> Location</font> </th>		 	
										<th width="25%" valign="middle"  align="left"> <font class="tdText"> Scheme </font> </th>
										<th width="5%" valign="middle"  align="center"> Rectification <br>Quantity / Amount</th>
							 			<th width="5%" valign="middle"  align="center"> <font class="tdText"> Meter<br> Available</font> </th>
							 			<th width="2%" valign="middle"  align="center"> <font class="tdText">Meter<br> Type</font> </th>
										<th width="5%" valign="middle"  align="center"> <font class="tdText">Meter<br> Working</font> </th>
										<th width="5%"  valign="middle" align="center"> <font class="tdText">Opening <br>Reading</font> </th>
				 					 	<th width="5%" valign="middle"  align="center"> <font class="tdText">Closing<br>  Reading</font> </th>
				 					 	<th width="5%" valign="middle"  align="center"> <font class="tdText">Child<br>Meter Qty</font><font size=2 color="blue"><b>[KL]</b></font> </th>
									 	<th width="5%" valign="middle"  align="center"> <font class="tdText">No of <br>Units</font><font color="red" size=2><b>[KL]</b></font> </th>
				 					 	<th width="10%" valign="middle"  align="center" >Check<br>Water Charges <bR></th>
				 					 	<th width="10%" valign="middle"  align="center" >Process </th>
						   </Tr>
						   	<tbody id="ben_meter_body" align="left" style="bgcolor: #E3E4FA"  ></tbody>
					</table>	
					</td>	 
				</Tr>
				</table> 
				<table  width=100% align="center" border=0   cellspacing="0" cellpadding="2"  > 
					<tr>
						<td width="10%" align=right>&nbsp; </td>
						<td align=right ><b><font size=2 color="blue">Total Units And Water Charges   </font></b>  <input class="tb5" type=text id="netunit" readonly="readonly" name="netunit" style="text-align: right" value="0"></input>
						  <input class="tb5" type=text id="net_wc_amt" readonly="readonly" name="net_wc_amt" style="text-align: right" value="0"></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  </td>
					</tr> 
					<Tr>
						<th colspan=5 align=center><input type=submit class="fb2"  value=Submit onclick="data_show_dcb('add',7,'')">&nbsp;<input type=button  class="fb2"  onclick="pump_report()" value=Report>&nbsp;<input type="button" class="fb2" value="Refresh" onclick="javascript:window.location.reload();">
					 		 <input class="fb2"   type=submit value=Exit onclick="javascript:window.close()"><img src="../../../../../images/help_images.jpg" height="18px" width="45px"  style="vertical-align: bottom;"  onClick="window.open('help1.jsp?fn=21','mywindow','width=600,height=400,scrollbars=yes')"> 
					 	</th>				 
					</Tr>
				</table>				 
				<input type=hidden name="BENEFICIARY_SNO" id="BENEFICIARY_SNO"></input>
				<input type=hidden name="selbentype" id="selbentype"></input>
				<input type=hidden name="rowcnt" id="rowcnt"></input>
				<input type=hidden name="rowcnt_meter" id="rowcnt_meter"></input>
				<input type=hidden name="OFFICE_ID" id="OFFICE_ID" value="5430"></input>
				<input type="hidden" id="selected_ben" name="selected_ben" value="0"></input>
				<input type=hidden id="pr_status" name="pr_status" value="0"> 
				<input type="hidden" id="ben_type_fltr" name="ben_type_fltr" value="0">  
				<input type="hidden" id="fyear" name="fyear" value="<%=year%>" ></input> 
				<input type="hidden" id="fmonth" name="fmonth" value='<%=month%>' ></input>
				<input type="hidden" id="officeid" name="officeid" value='<%=Office_id%>' ></input>
</body>
</html>