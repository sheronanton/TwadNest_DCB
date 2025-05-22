<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>

<head>
<style type="text/css">
form.cmxform { width: 30em; }
form.cmxform label.error {
	display: block;
	margin-left: 1em;
	width: auto;
}

</style>
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
 
 <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>

<script language="javascript" type="text/javascript">

function ben_report_pump()
{   
	grid_show(3,'data','ben_data','ben_body' ,'bentype')
}
function pump_report()
{	
	var ben_value=document.getElementById("BENEFICIARY_SNO").value;
	var month_value =document.getElementById("month").value;
	 var year_value =document.getElementById("year").value;
	 var mv=document.getElementById("vmonth").value
	 var yv=year_value;
	//var mv=document.getElementById("month").options[document.getElementById("month").selectedIndex].text;
	//var yv=document.getElementById("year").options[document.getElementById("year").selectedIndex].text;
	
	
	
	document.getElementById("selected_ben").value=ben_value;
 
s=window.open('pumping_report.jsp?ben_value='+ben_value+'&month_value='+month_value+'&year_value='+year_value+"&mv="+mv+"&yv="+yv,'windowname1','width=900, height=700')
}
function calcuate(row)
{
	 
 	var METRE_INIT_READING =document.getElementById("METRE_INIT_READING"+row).value;
	if (METRE_INIT_READING=="") METRE_INIT_READING="0";
	var METRE_FIXED=document.getElementById("METRE_FIXED"+row).innerHTML;
 	var METRE_WORKING=document.getElementById("METRE_WORKING"+row).innerHTML; 
 	var METRE_TYPE=document.getElementById("METRE_TYPE"+row).value; 
 	
	var read=document.getElementById("read"+row).value;
	if (read=="") read="0";
	 
  	if (METRE_FIXED=="Yes" && (METRE_WORKING=="Y" || METRE_WORKING=="Yes" ))
	{
		if (parseFloat(read) < parseFloat(METRE_INIT_READING))
		{
 			alert("check the reading! (row no "+row+") \n------------------------------------")
			document.getElementById("nounit"+row).value=0;
			document.getElementById("read"+row).value=0;
			//document.getElementById("nounit"+row).focus();
		}else

		{
			 
		var nounit=roundNumber(parseFloat(read)-parseFloat(METRE_INIT_READING),3);
		
		if (parseInt(METRE_TYPE)==2)
			document.getElementById("nounit"+row).value=roundNumber(parseFloat(nounit)*1000,3);
		else
			document.getElementById("nounit"+row).value=roundNumber(parseFloat(nounit),3);
		} 
	}
 	else
 	{ 
 		var s=0; 
 		//document.getElementById("nounit"+row).value=document.getElementById("read"+row).value;
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
			     document.getElementById("nounit"+row).value=roundNumber(parseFloat(net_qty)-parseFloat(PARENT_METER_qty),3);
			  }
	 }	
 	
	  
	for (i=1;i<=rowcnt_meter;i++)
	{
			  	 
		tot_read+=parseFloat(document.getElementById("nounit"+i).value);
	}

	
		document.getElementById("netunit").value=roundNumber(tot_read,3);
} 
 
function ckset(r)
{
	  
	var label=document.getElementById("disvalue"+r).innerHTML;
	document.getElementById("bname").innerHTML="";
	 
 	var row=document.getElementById("rowcnt").value;
  
 	for (i=1;i<=row;i++)
	{
		 
		 
		if (i!=r)
		{
		document.getElementById("netunit").value=0;
		document.getElementById("ch"+i).checked=false;
		document.getElementById("row"+i).style.backgroundColor="";
		} 
		if (i==r)
		{
			document.getElementById("netunit").value=0;
			document.getElementById("row"+i).style.backgroundColor="#dddfdd";
			document.getElementById("BENEFICIARY_SNO").value=document.getElementById("select"+i).value
			document.getElementById("bentype").value=document.getElementById("bentype").value
		}

		
		
		document.getElementById("bname").innerHTML=label;
		
	}
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
	document.getElementById("BENEFICIARY_SNO").value=document.getElementById("select"+row).value;
}
 

function sh()
{
	document.getElementById('hlab').innerHTML=""
}
function mselection(row)
{ 
	document.getElementById("vmonth").value=monthselect(row) ;
	 
}
</script>
<%
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) ;

int year = cal.get(Calendar.YEAR);

 if (month >=4)
	year=year;
else
	year=year;

int pumpingfalg=0;
/*

	flag from setting table
	pumpingfalg=1

*/
String wcfreeze = "", JOURNAL_POSTED = "", msg = "", color_ = "", msg2 = "";
String Date_dis=day+"/"+month+"/"+year;
String userid="0",Office_id="",Office_Name="";
Controller obj=new Controller();
Connection con;
int count = 0, fr_flag = 0, pumping_val_count = 0;
try
{
	con=obj.con();
	obj.createStatement(con);
	
	try
	{
	     userid=(String)session.getAttribute("UserId");
	}catch(Exception e)
	{
		  response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	if (Office_id.equals("")) Office_id="0";
	Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
	obj.conClose(con);
}catch(Exception e) {
	
	response.sendRedirect(request.getContextPath()+"/index.jsp");
}	

String ben_value="";
ben_value=request.getParameter("ben_value");
String smonth=obj.setValue("month_value",request);
String syear=obj.setValue("year_value",request);
if (ben_value==null) ben_value="0";
	
		int wc = 0;
		pumping_val_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where    office_id=" + Office_id + " and month="
						+ smonth + " and year=" + syear+" and PROCESS_FLAG='V'");
		wcfreeze = obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS","WC_FREEZED", "where OFFICE_ID=" + Office_id
						+ " and CASHBOOK_YEAR="+syear+" and  CASHBOOK_MONTH=" + smonth), 1);
		try {
			
			JOURNAL_POSTED = obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS", "JOURNAL_POSTED","where OFFICE_ID=" + Office_id
							+ " and CASHBOOK_YEAR=" + syear
							+ " and  CASHBOOK_MONTH=" + smonth), 1);
			} catch (NullPointerException e1) {
			JOURNAL_POSTED = "0";

		}
		 
		if (wcfreeze.trim().equalsIgnoreCase("Y")
				&& (JOURNAL_POSTED.trim().equalsIgnoreCase("Y") || JOURNAL_POSTED
						.trim().equalsIgnoreCase("y"))) {
			msg = " Journal Already Posted ! Pumping Return Cannot be Edited ";
			count = 1;
		} else if (wcfreeze.trim().equalsIgnoreCase("Y")
				&& JOURNAL_POSTED.trim().equalsIgnoreCase("0")) {

			count = 1;
			msg = " Water charges Freezed ! ";
		} else if (wcfreeze.trim().equalsIgnoreCase("CR")
				&& JOURNAL_POSTED.trim().equalsIgnoreCase("0")) {
			msg = " Pumping Return Freezed ! Cannot be Edited ";
			count = 0;
			fr_flag = 0;
		} else {
			msg = " Pumping Return Not Freezed ! ";
			count = 0;
			fr_flag = 1;
		}

		if (fr_flag == 0) {
			msg2 = " Pumping Return Cannot be Freezed... &nbsp;&nbsp;&nbsp;<a href='data_not_submit.jsp'>Please Check Pumping Return Missing Report</a>";
		}
		if (pumping_val_count != 0) {
			count = 0;
			fr_flag = 1;
		}
	
	
	
%>
 

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pumping Return Edit | TWAD Nest - Phase II</title>
 
       <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
         <link href="../../../../../css/label.css" rel="stylesheet" media="screen"/> 
  
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pumping Return  | TWAD Nest - Phase II</title>
          <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
    	<script type="text/javascript" src="../scripts/master.js"></script>
        <script type="text/javascript" src="../scripts/Ben_Report.js"></script>
    
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
    </head>
<body 	onload="data_show('show',11,<%=ben_value%>)">
<form  >

<table class="fb2"   class="table" id="" width=100% align=center border="0"   cellspacing="0" cellpadding="5">

 <tr >
      <td  class="tdH" colspan=2  height=30>
       <Center>Pumping Return Edit-- <%=Office_Name%></Center>
                
           
          </td>
        </tr>
         
        <tr>         
         <td  width="50%"   class="tdText" align="left"><font size=2  >&nbsp;Pumping  Return  Month : <input type="text" id="vmonth" name="vmonth" value="" class="tb4" size="2"></font></td><td> <input type=hidden id="month" name="month" value="<%=month%>" size=4 ><font size="2"> Year:</font> <input type=text id="year" name="year" value="" size=4 class="tb1"></input> </td>
         </tr>
        
		<tr  class="tdText">
			<td  width="30%" align=left ><font size=2  >&nbsp;Beneficiary Type&nbsp;</font> </td>
			<td   align=left id="bentype" name="bentype"  ></td>
		</tr>
		<tr class="tdText">	
			<td>
					<font size="2" >&nbsp;Beneficiary Name&nbsp; :&nbsp;</font></td>
					<td  > <label id="ben_name"></label></td>
 		</tr>
		
			 <tr><td  colspan=2 align="center"><label id="mst"><font class="fnt" color='red'><%=msg%></font></label></td></tr> 
</table>
 
<table class="fb2"  width=100% align=center border=0  cellspacing="0" cellpadding="2"   >
<Tr>
 
	<td colspan=2  > 
	
	<table style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" id="ben_meter_data_ed" width=100% align=center border="0" cellspacing="0" cellpadding="2"  >
			<tR   class="tdH"     valign="top" > 
						
						<td class="tdText"   valign=top  align=left>Location</td>	
						<td class="tdText" width=5% valign=top  align=center>Status</td>	 	
						<td class="tdText"   valign=top  align=left>Scheme</td>
						
						 
						<td class="tdText"  width=5% valign=top  align=center>Meter<br> Available </td>
						<td class="tdText" width="5%" valign=top  align=center>Meter<br> Working</td>
 						<td class="tdText"  width=10%  valign=top align=center>Opening <br>Reading</td>
 					 	<td class="tdText"   width=10% valign=top  align=center>Closing<br>Reading</td>
 					 	<td class="tdText"   width=10% valign=top  align=center>Child Meter<br>Reading[KL]</td>
					 	<td class="tdText" width=10% valign=top  align=center>No of Units[KL]</td>
 		   </Tr>
 		    
		   	<tbody id="ben_meter_body_ed" align="left"   ></tbody>
		   	 
	</table>
	</td>
	
</Tr>
 
<tr >
<td align=right colspan=2> Total Units<input class="tb5" type=text id="netunit"  name="netunit" style="text-align: right" size="7"></input></td>

</tr>
</table>
 
				<table width=95% align=center border=0   cellspacing="0" cellpadding="2"  > 
			
				<Tr  class="tdH" > 
					 				 	<td colspan=5 align=center>&nbsp;
					 				 	   
					 				 	<%if (count == 0 && fr_flag == 1) { System.out.println("fr_flag==>"+fr_flag);%> 
					 				 	<input type=submit value=Submit class="fb2" onclick="pr_edit_start('add',12)"> <%} %>&nbsp;
					 				 	<input type=button class="fb2" onclick="pump_report()" value=Report> 
					 				 	<input type=button class="fb2" value=Exit onclick="javascript:window.close()">&nbsp;<img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=22','mywindow','width=700,height=300,scrollbars=yes')"> </td>
				 
				</Tr> 
				
				</table>
<!-- <div align="center" style="position:absolute;top:10; left:20; width:100%; height:175;">
		<table   id="entred_data" width=90% align=center border=0   cellspacing="0" cellpadding="2">
				<tr >
				<td align=center width=5%><font size=2  >Select</font> </td>				
  				<td align=left width=15%><font size=2 >Beneficiary Type</font> </td>
				<td align=left width=70%><font size=2 >Beneficiary Name</font> </td>
				<td align=center width=5%><font size=2  >Select</font> </td>
				</tr>
				 <tbody id="entred_body" align="left"   >
				 
	      
				 </tbody>
		 
		</table>
		
</div>
 -->				 
 
<input type=hidden name="BENEFICIARY_SNO" id="BENEFICIARY_SNO"></input>
<input type=hidden name="selbentype" id="selbentype"></input>
<input type=hidden name="rowcnt" id="rowcnt"></input>
<input type=hidden name="rowcnt_meter" id="rowcnt_meter"></input>
<input type=hidden name="OFFICE_ID" id="OFFICE_ID" value="5430"></input>
<input type="hidden" id="selected_ben" name="selected_ben" value="0"></input>
<input type="hidden" id="MONPR_SNO" name="MONPR_SNO" value="0"></input>
   <input type=hidden id="pr_status" name="pr_status" value="0"> 

</form>
</body>
</html>