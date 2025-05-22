<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%> 
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Monthly Pumping Return Freeze | TWAD Nest - Phase II</title>
</head>
<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/msg.js"></script>
  <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<body onload="list('pr_select',1,0),flash(),startclock()">
<%
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) ;
String new_cond=Controller.new_cond;
String meter_status=Controller.meter_status;
int year = cal.get(Calendar.YEAR);
 if (month >=4)
	year=year;
else
	year=year;
int pumpingfalg=0;
String BEN_TYPE_ID="0";
int count=0,pumping_val_count=0;
int fr_flag=0,Bill_count=0;
String wcfreeze="",JOURNAL_POSTED="",msg="",color_="",msg2="",oid="";
String Date_dis=day+"/"+(month+1)+"/"+year;
String userid="0",Office_id="",Office_Name="";
Controller obj=new Controller();
 int total_meter_location=0,pumping_count=0;
Connection con=null;
try 
{
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
	//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	if (Office_id.equals("")) Office_id="0";
	
	oid=obj.setValue("oid",request); 		
 		if (!oid.trim().equalsIgnoreCase("0"))
 			Office_id=oid;
	Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
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
	 String add_cond=" and METRE_SNO in ( select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where meter_status='L' and office_id="+Office_id+")";
	 total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where "+meter_status+" BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id +" " );
	 //total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where    office_id="+Office_id);	
 	 pumping_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where  "+Controller.meter_ben_same+" ) and  BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and    office_id="+Office_id+" and month="+smonth+" and year="+syear+ " " +add_cond);//+" and PROCESS_FLAG='FR'" );
 	 pumping_val_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where "+Controller.meter_ben_same+" ) and BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and      office_id="+Office_id+" and month="+smonth+" and year="+syear+" and PROCESS_FLAG='V'"+ " " +add_cond );
	 color_="";  
		  color_=(total_meter_location==pumping_count) ? "green":"red";
	  //   fr_flag=(total_meter_location==pumping_count)?1:0;//1 -> Count is equal
	  wcfreeze=obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS","WC_FREEZED","where OFFICE_ID="+Office_id+" and CASHBOOK_YEAR="+syear+" and  CASHBOOK_MONTH="+smonth),1);
	  try
	  { 
	  JOURNAL_POSTED=obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS","JOURNAL_POSTED","where OFFICE_ID="+Office_id+" and CASHBOOK_YEAR="+syear+" and  CASHBOOK_MONTH="+smonth),1);
	  }catch(NullPointerException  e1){
	    JOURNAL_POSTED="0";
	   
	   }  
	   int wc=0; 
	 
	  if (wcfreeze.trim().equalsIgnoreCase("Y") && (JOURNAL_POSTED.trim().equalsIgnoreCase("Y")|| JOURNAL_POSTED.trim().equalsIgnoreCase("y")))
	  {
	    msg=" Journal Already Posted ! ";
	    count=1;
	  }else if (wcfreeze.trim().equalsIgnoreCase("Y") && JOURNAL_POSTED.trim().equalsIgnoreCase("0"))
	  {
	   
	    count=1;
	    msg=" Water charges Freezed ! ";
	  }else if (wcfreeze.trim().equalsIgnoreCase("CR")&& JOURNAL_POSTED.trim().equalsIgnoreCase("0"))
	 {
	    msg="Pumping Return Freezed";
	    count=0;
	 } 
	 else 
	 {
	   msg=" Pumping Return Not Freezed ! ";
	  count=0;
	 }
	 // if Total Meter Count and Pumping count not equal -> 0
	 // if Total Meter Count and Pumping count equal -> 1
     if (fr_flag==0)
	 {     
	   //	msg2=" Pumping Return Cannot be Freezed... &nbsp;&nbsp;&nbsp;<a href='data_not_submit.jsp'>Please Check Pumping Return Missing Report</a>";
	 }
 	 if (pumping_val_count !=0 )
 	 {
 	  count=0;
 	  fr_flag=1;  
 	 }
 	   BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")","bentype","","class='select' style='width: 262'" );
	obj.conClose(con);
}catch(Exception e) {
	out.println(e);
	// response.sendRedirect(request.getContextPath()+"/index.jsp");
}	  
String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
%>
 <input type="hidden" id="month" value="<%=month%>">
<input type="hidden" id="year" value="<%=year%>"> 
 <form>
<table  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"  width="85%" cellpadding="5"  cellspacing="0" align="center" class="fb2">
<tr  class="tdText">
<td align="center" colspan="4">  
Monthly Pumping Return Freeze  - <%=Office_Name%>  
</td>
</tr>  

 <tr style="height: 30pt"  > 

    <td colspan=4 align="left" class="tdText"> <font>Month And Year : </font> &nbsp;&nbsp;&nbsp;   <%=cmonth[month]%>&nbsp;/&nbsp;<%=year%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="time" class="tb4"></label>&nbsp;&nbsp;&nbsp;&nbsp; <font color=blue>Beneficiary Type</font> &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<%=BEN_TYPE_ID%><img src="../../../../../images/new_.jpg" width="30" height="20" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="msg"></label></td> 
     
    
 <% String all_div=obj.combo_str("com_mst_all_offices_view","OFFICE_NAME", "OFFICE_ID","","",""); %>
</tr>
<tr>  
   
 
   
</tr>
</table>
<table width="85%"   style="BORDER-COLLAPSE: collapse"   id="pr_ben_data"  border="1"  align="center"  cellspacing="0" cellpadding="3" class="fb2">
		<tr><td colspan="5"><table width="100%" border="0"   style="BORDER-COLLAPSE: collapse">
				 <tr>
    				 <td width="40%"  ><label class="lbl">Total Meter Location &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;<font color='green'><%=total_meter_location%></font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Pumping Return Entered &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;<font color="<%=color_%>"><%=pumping_count%></font> </label></td>
    				 <td  align="center"> <label class="lbl"> <font color="<%=color_%>"><%=msg2%> </font></label>  <font color=blue> <%=msg%> </font></td>
     			</tr>
     			</table></td></tr> 
     				<tr>
	     				<td colspan="5">
		     			<table width="100%" align="center" border="1">
		     			 <Tr class="tdH" valign="top"   >
									<td align=center valign=top  width=5%><font class="tdText"  >Sl.No</font> </td>
		 				 			<td align=center valign=top width=33%><font class="tdText"  > Beneficiary Name </font> </td>
									<td align=center valign=top width=10%><font class="tdText"  > Beneficiary Type </font> </td>
									<td align=left valign=top width=12%><font class="tdText"  > Previous Net<bR> Consumption [KL]</font> </td>
									<td align=left valign=top width=10% nowrap="nowrap"><font class="tdText"  > Current Net<bR> Consumption [KL] </font> </td>
		 				</tr>
	     				</table>
     					</td>
     				</tr>
     				<tr><td colspan="5">
     				<table width="100%" > 
		     			<tr>     			 
		     			<Td colspan="5" align="center">
			     			<div id='scroll_clipper' style='position:relative; width:100%; border-height:1px; height: 320px; overflow:auto;white-space:nowrap;'>
							<div id='scroll_text'  >
			     					 <table width="100%"   style="BORDER-COLLAPSE: collapse"   id="pr_ben_data"  border="1"  align="center"  cellspacing="0" cellpadding="0"  bgcolor='white'>
										<tbody class="table" id="pr_ben_body" align="left"   ></tbody>
									 </table>
							</div>
							</div>
						</Td>
						</tr>
						<tr bgcolor="#aafcfa">
							<td><input type="button" onclick="window.open('sub_div_ben.jsp')" style="color: blue" value="Sub Division Wise Report"></td>
							<td colspan="3" align="right"><font color='#454545'><b>Total Consumption</b></font>&nbsp;</td>
							<td align="right" id="net_cons" bgcolor="#cdcfff"></td>
						</tr>
					</table>
					</td></tr></table>
				<table width="85%" border="0" align="center">
				<tr>
					<td  width="30%" >Water Charges Calculation Status</td>
					<td colspan="2" bgcolor="skyblue" align="center"><font size="2">**<%=msg%>**</font> 
					</td>				
				</tr>   
				 </table>
				 <table width="85%" border="0" align="center">
				 <tr>
					<td colspan="5" align="right"><font color='red' size='2'>Click Refresh button after Pumping Return Freeze completed for all beneficiary types.  </font> </td>
				</tr>
				<Tr>
 				 			<th colspan="5" align="center" valign="top" style="height: 9px"><% if (count==0 && fr_flag==1)
 				 			 { 
 				 						 				  
 				 			   %><input type="button" class="fb2" value="Pumping Return Freeze" id="FR_BT" onclick="FR_('FR',1,0)">&nbsp;<%} %>&nbsp;
 				 		      <input type="button" class="fb2" value="Refresh" onclick="javascript:window.location.reload();"> 
							
				 
 				 			<% if (count==0)
 				 					{ 
 				 			 			 %><input type="button"   class="fb2" value="Pumping Return Un Freeze" onclick="unFreeze('UFR',1,0)"><% 
 				 			 		 } %> 
 				 						 <input type="button" onclick="self.close()" class="fb2" value="Exit">&nbsp;<input type="button" value="HELP" onclick="window.open('help1.jsp?fn=24','mywindow','scrollbars=yes')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
 				 			</th>
							
				</tr>
				
</table>
		   <input type="hidden" id="pr_status" name="pr_status" value="0"> 
		   <input type="hidden" id="row_count" name="row_count" value="0">
		   <input type="hidden" id="freezs" name="freezs" value="1">
		   <input type="hidden" id="subdiv" name="subdiv" value="1">
		   <input type="hidden" id="sp_flag" name="freezs" value="1">
		    <input type="hidden" id="oid" name="oid" value="<%=oid%>">
<%
	//String tot_entry=obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE","count(*)"," where OFFICE_ID="+Office_id+" and  tariff_rate is NULL ");
//if (Integer.parseInt(tot_entry) > 0) 
//{
	  %>
	<script type="text/javascript">
//	 alert("Tariff Rate Not Entered for all Beneficiaries...\n Water Charge will be calculated after all Tariff Rate entry is completed" );
	//		document.getElementById("FR_BT").disabled = true;
	</script>  
	<%  
//}
%>
<% obj.conClose(con); %>
 </form>
</body>
</html>