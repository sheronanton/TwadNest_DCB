<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Water Charges UnFreeze | TWAD Nest - Phase II </title>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
	<style>
		.FR
		{
		scrollbar-face-color:#9898D0;
		scrollbar-arrow-color:indigo;
		scrollbar-track-color:#C0DCC0;
		scrollbar-shadow-color:'';
		scrollbar-highlight-color:'';
		scrollbar-3dlight-color:'';
		scrollbar-darkshadow-Color:'';
		}
	 
	</style>
	<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
	<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
	<script type="text/javascript" src="../scripts/Bill_Demand_Report.js"></script>
	<script type="text/javascript" src="../scripts/RIW.js"></script>
	<script type="text/javascript" src="../scripts/msg.js"></script>
	<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
</head>
 <%
 		String amt="",billsno="",inp_month="",inp_year="",smonth="",syear="",status="";
 		String wcfreeze = "", JOURNAL_POSTED = "", msg = "", color_ = "", msg2 = "",disabled="";
 		String billstatus="";
   		String combo="<select id='dmdlist' name='dmdlist' multiple class='select' >";
   		int count = 0, fr_flag = 0, pumping_val_count = 0,Bill_count=0;;
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String userid="0",Office_id="",Office_Name="";
			String Date_dis=day+"/"+month+"/"+year;
			   Controller obj=new Controller();
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
			//	  Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			
				Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

				if (Office_id.equals("")) Office_id="0";
		 		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		 		 
		 		try
				{
		 		  smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		 		  syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		 		  status=obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS", "WC_FREEZED"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+smonth+" and CASHBOOK_YEAR="+syear ),1);
		 	 	   Bill_count=obj.getCount("PMS_DCB_TRN_BILL_DMD","where OFFICE_ID="+Office_id+" and BILL_MONTH="+smonth+" and BILL_YEAR="+syear);

		 		  
		 		  if (status.trim().equals("CR")) status="Water Charges Calculation Finished.... Not Yet Freezed !!";
		 		  else if (status.trim().equalsIgnoreCase("Y")) status="Water Charges  Freezed !";
		 		  else  status="Water Charges Not calculated !";
		 		  
				}catch(Exception e)
				{
					smonth=Integer.toString(month);
					syear=Integer.toString(year);
					status="Data Not Found !";
				}
		 		 
				wcfreeze = obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS","WC_FREEZED", "where OFFICE_ID=" + Office_id
						+ " and CASHBOOK_YEAR="+syear+" and  CASHBOOK_MONTH=" + smonth), 1);
		try {
			
			JOURNAL_POSTED = obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS", "JOURNAL_POSTED","where OFFICE_ID=" + Office_id
							+ " and CASHBOOK_YEAR=" + syear
							+ " and  CASHBOOK_MONTH=" + smonth), 1);
			} catch (NullPointerException e1) {
			JOURNAL_POSTED = "0";

		}

		int wc = 0;
		pumping_val_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where    office_id=" + Office_id + " and month="
						+ smonth + " and year=" + syear+" and PROCESS_FLAG='V'");

		 
		 
		if (wcfreeze.trim().equalsIgnoreCase("Y")
				&& (JOURNAL_POSTED.trim().equalsIgnoreCase("Y") || JOURNAL_POSTED
						.trim().equalsIgnoreCase("y"))) {
			msg = " Journal Already Posted ! Water Charges Cannot be Freezed or UnFreezed ";
			count = 1;
			fr_flag=0;
		} else if (wcfreeze.trim().equalsIgnoreCase("Y")
				&& JOURNAL_POSTED.trim().equalsIgnoreCase("0")) {

			count = 0;
			msg = " Water charges Freezed ! ";
			fr_flag=1;  
		} else if (wcfreeze.trim().equalsIgnoreCase("CR")
				&& JOURNAL_POSTED.trim().equalsIgnoreCase("0")) {
			msg = " Pumping Return Freezed ! ";
			count = 0;
			fr_flag = 1;
		} else {
			msg = " Pumping Return Not Freezed ! ";
			count = 0;
		}

		if (fr_flag == 0) {
			msg2 = " Pumping Return Cannot be Freezed... &nbsp;&nbsp;&nbsp;<a href='data_not_submit.jsp'>Please Check Pumping Return Missing Report</a>";
		}
		if (pumping_val_count != 0) {
			count = 0;
			fr_flag = 1;
		}
 
		 		 
		 		 
		 		 
		 		
		 		
		 		
		 		
		 		
		 		
		 		
		 		
				obj.conClose(con);
				}catch(Exception e) {
					
					userid="0";
				 
				//	response.sendRedirect(request.getContextPath()+"/index.jsp");
				 
				}
				//onload="year('pyear','ob'),month('pmonth','ob')"
				
				String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
				
				if (count == 0 && fr_flag == 1)
				{
						if (Bill_count==0)
						{
							disabled="";
							billstatus=Bill_count + " bills found ";
						}
						else
						{
							billstatus=""+Bill_count + " bills found. To Unfreeze Water Charges first delete bills. <font color='red'>Refer HELP</font> ";
							disabled="disabled='disabled'";
						}
				}
					else
					{
						billstatus=Bill_count + " bills found ";
						disabled="disabled='disabled'";
					}
				
			 
				
%>
<body onload="blink()">
<form > 
<input type="hidden" id="subdiv" value="0"/>
<label id="msg"></label>
 <table   width="75%"  align="center"  cellpadding="7" cellspacing="0"    style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" >

<tr  class="tdH">
	          <td colspan="4" align="center" ><font color="	#408080"> Tamil Nadu Water Supply and Drainage Board</font></td>
			  
			  </tr>
  			 <tr  >
	          <td colspan="4" align="center"> <%=Office_Name%></td>
			  
			  </tr >
			  <tr class="tdH">
	          <td colspan="4" align="center">Water Charges Calculation UnFreeze</td>
			  
			  </tr>
	         <tr  >
	          <td width="25%"> <font color="#0000A0"> Year </font> </td>
			  <Td> <%=syear%>  </tD>
			  </tr>
			 <Tr >
			   <td><font color="#0000A0">  Month </font></td>	  	          
			 <Td> <%=monthArr[Integer.parseInt(smonth)]%>   </Td>	        
			 
          	</tr><!-- data_show('show',14,'dmdlist')  -->
		 <Tr >
			  <td><font color="#0000A0">  Water Charges Status </font></td>	  	          
			 <Td> <%=status%>   </Td>	        
			 
          	</tr>
          	<Tr >
			  <td><font color="#0000A0">  Bill Generation Status </font></td>	  	          
			 <Td> <%=billstatus%>   </Td>	        
			 
          	</tr>
		  <tr><td  colspan=2 align="center"><label id="mst"><font class="fnt" color='red'><%=msg%></font></label></td></tr>
		 
		 <tr class="tdH">
		 		<td colspan="2" align="center">
		 		 
		 		<input type=button value="Unfreeze" class="fb2" onclick="unFreeze('WCUFR',0,0)" <%=disabled%>>&nbsp;<input type="button" class="fb2" value="Refresh" onclick="javascript:window.location.reload();">&nbsp;
		 		 
		 		<input type=button value="Exit" class="fb2" onclick="javascript:window.close()"> <img src="../../../../../images/help_images.jpg" height="18px" width="45px"  style="vertical-align: bottom;"  onClick="window.open('help1.jsp?fn=26','mywindow','width=600,height=400,scrollbars=yes')"></td>
		 </tr>
		  
		</table>	
	<input type=hidden id="month" name="month" value="<%=smonth%>"> 	 
	<input type=hidden id="year" name="year" value="<%=syear%>"> 	  
	<input type=hidden id="pr_status" name="pr_status" value="0"> 
 
 
</form>
     

</body>
</html>