<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> Water Charges Calculation Freeze | TWAD Nest - Phase II </title>
</head>
<script type="text/javascript">
function journalpdf(val)
{
	a=window.open("../../../../../Pumping_Return_List?command=waterchargejournalpdf&val=1" );
}
</script>
<script type="text/javascript" src="../scripts/Bill_Pumping_List.js"></script>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/msg.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>  
<body onload="journal_report(1,0),flash(),blink(),receipt_deails()">
<form>  
<%
		String new_cond=Controller.new_cond;
		String meter_status=Controller.meter_status;
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		if (month >= 4)
			year = year;
		else
			year = year;
		int pumpingfalg = 0;
/*
		 flag from setting table
		 pumpingfalg=1
 */
		
		String smonth ="";
		String syear ="";
		String wcfreeze = "", JOURNAL_POSTED = "", msg = "", color_ = "", msg2 = "",pumping_count_str="",msg_pr_miss="";
		String Date_dis = day + "/" + (month + 1) + "/" + year;
		String userid = "0", Office_id = "", Office_Name = "",disabled="";
		Controller obj = new Controller();
		int count = 0, fr_flag = 0, pumping_val_count = 0,total_meter_location = 0,pumping_count = 0;
		Connection con;
		try {

			con = obj.con();
			obj.createStatement(con);
			try {
					userid = (String) session.getAttribute("UserId");
			} catch (Exception e) {		userid = "0";  }	
			
			if (userid == null) {
					response.sendRedirect(request.getContextPath()+ "/index.jsp");
				}
		//   Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid + "')");
		
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		   if (Office_id.equals("")) Office_id = "0";
			  
		    Office_Name = obj.getValue("com_mst_offices","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ") +" ("+Office_id+")";
		    smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING","MONTH", " where OFFICE_ID=" + Office_id), 1);
		    syear = obj.isNull(obj.getValue("PMS_DCB_SETTING","YEAR", "where OFFICE_ID=" + Office_id), 1);
		    if (smonth.equalsIgnoreCase("0"))	
		    	month = month;
		    else
				month = Integer.parseInt(smonth);
		    
		    if (syear.equalsIgnoreCase("0"))
				year = year;  
		    else
				year = Integer.parseInt(syear);
		    
		    wcfreeze = obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS","WC_FREEZED", "where OFFICE_ID=" + Office_id
						+ " and CASHBOOK_YEAR="+syear+" and  CASHBOOK_MONTH=" + smonth), 1);
		    try 
		    {
				JOURNAL_POSTED = obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS", "JOURNAL_POSTED","where OFFICE_ID=" + Office_id
						+ " and CASHBOOK_YEAR=" + syear
						+ " and  CASHBOOK_MONTH=" + smonth), 1);
		    } catch (NullPointerException e1) {
				JOURNAL_POSTED = "0";
			}
		    int wc = 0;
		    pumping_val_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where    office_id=" + Office_id + " and month="
			         	+ smonth + " and year=" + syear+" and PROCESS_FLAG='V'");
		    total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where "+meter_status+" BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id);
		    // total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where  BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id);
		    pumping_count  = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and    METRE_SNO in (select METRE_SNO from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and    office_id="+Office_id+" ) and    office_id="+Office_id+" and month="+smonth+" and year="+syear+" and PROCESS_FLAG='FR'" );
		    if (pumping_count==total_meter_location)
		    {
					pumping_count_str="<font color='green'><b>"+pumping_count+"</b></font>";
					msg_pr_miss="";
		    }
		    else 
		    {
					pumping_count_str="<font color='red'><b>"+pumping_count+"</b></font>";
					msg_pr_miss="<font color='red'><b>Pumping Return Entry Omission Found.....View PR Omission Report</b></font>";
		    }
		    fr_flag=(total_meter_location==pumping_count)?1:0;//1 -> Count is equal
		    /* Total Location and Freezed Location of this month is same -> return 1 in fr_flag */
			  if ( (JOURNAL_POSTED.trim().equalsIgnoreCase("Y") || JOURNAL_POSTED.trim().equalsIgnoreCase("y"))) 
			  {
				msg = " Journal Already Posted ! Water Charges Cannot be Freezed or UnFreezed ";
				count = 1;
			  } 
			 
			/*else if (wcfreeze.trim().equalsIgnoreCase("Y")&& JOURNAL_POSTED.trim().equalsIgnoreCase("0")) 
			{
				count = 1;
				msg = " Water charges Freezed ! ";
			} else if (wcfreeze.trim().equalsIgnoreCase("CR")
					&& JOURNAL_POSTED.trim().equalsIgnoreCase("0")) {
				msg = " Pumping Return Freezed ! ";
				count = 0;
			//	fr_flag = 1;
			} else {
				msg = " Pumping Return Not Freezed ! ";
				count = 0;
			}
			*/
			if (fr_flag == 0) {
				msg2 = " Pumping Return Cannot be Freezed... &nbsp;&nbsp;&nbsp;<a href='data_not_submit.jsp'>Please Check Pumping Return Missing Report</a>";
			}
			if (pumping_val_count != 0) {
				count = 0;
			//	fr_flag = 1;
			}

			obj.conClose(con);
		} catch (Exception e) {

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}

		 //if (fr_flag==1 || count==1)
		 if (count==1)
			disabled="disabled='disabled'";
		else
			disabled="";
		String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		 
		String sch_pr_count=obj.getValue("PMS_DCB_TRN_MONTHLY_PR","count(distinct SCH_SNO)" ,"ct","where office_id="+Office_id+" and month="+month+" and year="+year+" having sum(qty_consumed_net)>0");
		
		String sch_pumped_count=obj.getValue("PMS_AME_TRN_SCH_DP_QTY","count(distinct SCH_SNO)" ,"ct","where   office_id="+Office_id+" and month="+month+" and year="+year);
		int dpr_cnt=obj.getCount("PMS_DCB_DIV_SCHEME_DPR","where office_id="+Office_id);
		
		System.out.print("sch_pr_count: "+ sch_pr_count);
		System.out.print("sch_pumped_count: "+ sch_pumped_count);
		if (dpr_cnt!=0)
		{
			sch_pumped_count=sch_pr_count;
		}
		int ct=obj.getCount("PMS_DCB_DIV_SCHEME_DPR"," where OFFICE_ID="+Office_id);
		
		
		
		
		 
	%>
<input type="hidden" id="month" value="<%=month%>">
<input type="hidden" id="year" value="<%=year%>">

<table width="65%"  class="table" id="journ_ben_data"  border="1"  align="center"  cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"> 
<tr class="tdH">
	<th colspan=7 align="center" class="tdText"><font class="fnt"><b>Water Charges Calculation Freeze - <%=Office_Name%></b></font></th>
</tr>
<tr style="height: 30pt"  >
    <td colspan=2 align="left" class="fnt" > <font>Month/Year :</font> <%=cmonth[month]%>&nbsp;/&nbsp;<%=year%></td>
	<td colspan=4 align="right"><label id="msg"></label> </td>
</tr>
<tr> <td colspan=7 align="left" class="fnt"> Accounting Unit ID : <label id="off"></label></td></tr>
 <tr> <td colspan=7><table border=0 width=100%><tr> 
    				 <td class="fnt" colspan=3 align="left"> Total Meter Location : <%=total_meter_location%></td><td>&nbsp;<%=msg_pr_miss %></td> <td class="fnt" colspan=2 align="right">   Total Pumping Return Freezed :  <%=pumping_count_str%>  </td>
    				 </tr></table></td></tr>
						<Tr class="tdH" valign="top"  >
						<th align="center" class="fnt" >Sno</th>
						<th align="center" class="fnt" >Particulars</th>
						<th align="center" class="fnt" >Total CR</th>
						<th align="center"  class="fnt">Total DR</th>
						<th align="center" class="fnt"  width="5%">Tally</th>
						<th align="center" class="fnt" colspan="2">Details</th>						  
						</Tr>
		<tbody class="table" id="journ_ben_body" align="left"   ></tbody>
		 <tr><td  colspan=8 align="center"><label id="mst"><font class="fnt" color='red'><%=msg%></font></label></td></tr>
		<tr>
			<th colspan=8 align="center">
			<%	
				out.println("<font color='skyblue'>"+count+" -- " +fr_flag);
				int new_flag=0;//its for disable or enable the button
				if (count == 0 && fr_flag == 1)  
				 { 			
				   if (sch_pr_count.equalsIgnoreCase(sch_pumped_count))
					{		
						String DCB_Freeze=obj.getValue("PMS_DCB_FREEZE_RECEIPT","RECEIPT_FREEZE"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year);
						DCB_Freeze=DCB_Freeze.trim();
				
						if (!DCB_Freeze.trim().equalsIgnoreCase("Y"))
						{  
							new_flag=1;
						%>
							<script type="text/javascript">
								alert(" Please Goto --> DCB Receipt Verification. \n If Receipt Verfied O.K  then Only Water Charges can be Freezed. ");
							</script>
						<%	
						}else
						{   
							new_flag=2;
							disabled="";
						%>
							
				 	  <%
						}
					}
					else if (!sch_pr_count.equalsIgnoreCase(sch_pumped_count))
					{
						disabled="disabled";
						%> 
						<script type="text/javascript">
							alert(" Water Charges cannot be Freezed. Please Enter Scheme wise Pumped Quantity in AME Module.. \n Refer AME --> AME Transaction-->AME Monthly Transaction--> Scheme wise Monthly Pumped Quantity for the Month of <%=obj.month_val(smonth)%>  <%=syear%> \n  (For further clarification please contact TWAD, HO).");
						</script> <%    
					}else if (sch_pumped_count.equalsIgnoreCase("0"))
					{
						disabled="disabled";
					%>
						<script type="text/javascript">
						alert(" Water Charges cannot be Freezed. Please Enter Scheme wise Pumped Quantity in AME Module.. \n Refer AME --> AME Transaction-->AME Monthly Transaction--> Scheme wise Monthly Pumped Quantity for the Month of <%=obj.month_val(smonth)%>  <%=syear%>.");
						</script>				
					<%}
				}  else
				{
					disabled="disabled";
				}
				  
			%>&nbsp;<input type="button" value="Freeze"  id='FR_BUT'  class="fb2" onclick="journal_report(3,0)" <%=disabled%> >&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="fb2" id='print_button' value="Print" onclick="journalpdf(1)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Exit" class="fb2"  onclick="window.close()"> <input type="button" class="fb2" value="HELP" onclick="window.open('help1.jsp?fn=25','mywindow','scrollbars=yes')"/>	 
			</th>  
		</tr>
		</table>
		<table width="65%"  class="table" id="receipt_table"  border="1"  align="center"  cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8"> 
		<tbody class="table" id="receipt_check" align="left"   >  
		</tbody>
</table>
 		   <input type="hidden" id="pr_status" name="pr_status" value="0"> 
		   <input type="hidden" id="row_count" name="row_count" value="0">
		   <input type="hidden" id="freezs" name="freezs" value="1">
		   <input type="hidden" id="subdiv" name="subdiv" value="1">
		  
</form>
</body>
</html>