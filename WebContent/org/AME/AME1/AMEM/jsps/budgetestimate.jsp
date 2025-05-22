<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@page import="java.util.Calendar"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Budget Estimate Amount</title>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen" />
<!--<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen" />-->
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/transcat.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script src="../scripts/reports.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/CalendarCtrl.js"></script>
<script type="text/javascript" src="../scripts/reports.js"></script>
<script type="text/javascript"> 
function load ()
{
	 
	 document.forms['bud'].submit();	
}

function page_size()
{
	window.resizeTo( 700,700 );
}

function cls()
{
	document.bud.ORG_REV[0].disabled=false;   
	document.bud.ORG_REV[1].disabled=false;
	document.getElementById("save").disabled = false;
}
function fun_val(ORG_REV)
{
	var fin_val;	
 	for (i=0; i < ORG_REV.length; i++) 
 	{
   		if (ORG_REV[i].checked==true) 
   		{
  			fin_val=ORG_REV[i].value;
   		}
 	} 
 	return fin_val;
}
function store()
{
	var val=fun_val(document.bud.ORG_REV); 
	var ct=document.getElementById("orgcount").value;
	if ( val=="O")
	{
		if (ct==0)
		{
			transaction(5,1);
		 	//transaction(5,6);   
		}else
		{
			alert("Already Record is There in Original Status");
		}
	}
	else
	{	
		if (ct==0)
		{
			alert("Plase Set to stage as Orignal");
		}
		else
		{
			transaction(5,1);
			//transaction(5,6);
		}
	}		
}
	function check_verify()
	{
		var sch_sno=document.getElementById("sch_sno").value; 
		var rowcount=document.getElementById("rowcount").value;
		for(var i=1;i<=rowcount;i++)
		{
			var SCH_SNO_r=document.getElementById("SCH_SNO"+i).value;
			if (parseInt(SCH_SNO_r)==parseInt(sch_sno))
			{
				var status=document.getElementById("status"+i).innerHTML;
				alert(status);
				if (status=="Orignal")
				{
					alert("Already Record is There in Original Status");
					document.getElementById("save").disabled = true;
					break;
				}
				else
				{
					document.getElementById("save").disabled = false;
				}
			}
			else
			{
				document.getElementById("save").disabled = false;
			}
				
		}		



	}

</script>
</head>
<body onload="page_size();transaction(5,6)">
<form action="budgetestimate.jsp" name="bud" method="get">
<%
	HttpSession session1 = request.getSession(false);
	String userid = (String) session1.getAttribute("UserId");
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj = new Controller();
	if (userid == null) 
	{
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	String sch="0";
	String sch_sno = obj.setValue("sch_sno", request);
	String pyear= obj.setValue("pyear", request);
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals(""))  	Office_id = "0";
	String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + "  ");
	//String sch = obj.getValue("PMS_SCH_MASTER", "SCH_NAME"," where SCH_SNO=" + sch_sno);
	String ad_no=obj.getValue("PMS_SCH_ADMIN_SANCTION", "SANCTION_REFNO"," where SCH_SNO=" + sch_sno);
	String ad_dt= obj.getDate("PMS_SCH_ADMIN_SANCTION", "SANCTION_DATE"," where SCH_SNO=" + sch_sno);
	
	String msg=" ";
	if (ad_no.equals("0"))
	{ 
		msg=" ";
		ad_dt="";
		ad_no="";
	}
	else
	{
		msg="";
	}

	sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where   sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by sch_sno","sch_sno",sch_sno,"class='select' style='width: 262' onchange=check_verify()" );
	String[] monthArr = { "Select", "January", "February", "March","April", "May", "June", "July", "August", "September","October", "November", "December" };
	int year_incr=0;
	  if (month <4)  
		  year_incr=0;     
	  else
		  year_incr=1;   
%>

<table width="100%" class="table" align="center" border=1
	cellpadding="5"   
	style="border-collapse: collapse; border-color: skyblue">
	<tr>
		<th colspan="2" align="center" class="tdH"><b>Budget Estimate Entry</b></th>
	</tr>
	<tr>
		<td width="25%"><label class="fnt">Division Office Name :</label></td>
		<td align="left" colspan="1"><label class="fnt"><%=Office_Name%></label></td>
	</tr>
	<tr>
		<td><label class="fnt">Financial Year</label></td>
		<td colspan="1">
			<select class="select" id="pyear" name="pyear" style="width: 80pt;">
			<option value="0">Select</option>
			<%
				for (int i = year-2; i <= year+year_incr; i++) 
				{
					String inp_year=(i-1)+"-"+i;
					if (pyear.equalsIgnoreCase(inp_year.trim()))  
					{
			%><option value="<%=i-1%>-<%=i%>" selected><%=i-1%>-<%=i%></option>
			<%
					}
					else
					{
					%>
					<option value="<%=i-1%>-<%=i%>"><%=i-1%>-<%=i%></option>
					<%
					}
				}
			%>
			</select> &nbsp;&nbsp;<input type=button value="GO" onclick="transaction(5,6)">
		</td>
	</tr>
	<tr>
		<td width="50%"><label class="fnt">Status : </label></td>
		<td colspan="2" align="left">
				 <input type="radio" id="ORG_REV" name="ORG_REV"	value="O" checked="checked">Original &nbsp;&nbsp;&nbsp;
				<input type="radio" id="ORG_REV" name="ORG_REV" value="R">Revised
		</td>
	</tr>
	<tr>
		<td width="50%"><label class="fnt">Scheme Name : </label></td>
		<td align="left"><%=sch%>
				 </td>
	</tr>
	<input type=hidden class="tb4" id='adminref'  name='adminref' value="0"/>&nbsp;<%=msg%>
	<input  size=10  type=hidden id='adminrefdate' name='adminrefdate' readonly="readonly" value="<%=day+"/"+month+"/"+year%>"/>
	<!--<tr>
		<td class="fnt">Admin Sanction Ref.</td>
		<td></td>
	</tr>
	<tr>
		<td class="fnt">Admin Sanction Date</td> 
		<td>--><!--
			  <img src="../../../../../images/calendr3.gif" align="middle"
				onclick="showCalendarControl(document.bud.adminrefdate,1);"
				alt="Show Calendar">  
		</td>
	</tr>-->
	<tr>
		<td class="fnt">Maint.Estimate Sanction Ref.</td>
		<td><input type=text  class="tb4" id='ref' name='ref'></td>
	</tr>
	<tr>
		<td class="fnt">Maint.Estimate Sanction Date</td>
		<td><input size=10 class="tb4_1"  type=text id='refdate' name='refdate' readonly="readonly" >
		 	  <img src="../../../../../images/calendr3.gif" align="middle" onclick="showCalendarControl(document.bud.refdate,1);" alt="Show Calendar"> 
		</td>
	</tr>
	<tr>
		<td class="fnt" width="30%">Budget Estimate Amount <font size=2 color=red><b>(Rs.
		in lakhs)</b></font></td>
			<td>
				<input type=text   class="tb4_2" id='budamt' size=10 name='budamt' value=""	maxlength="8"
				onkeyup="isInteger(this,9,event,'budamt'),digit_control('budamt',2)">
			</td>
	</tr>
	<!--   
	<tr>  
			<td class="fnt" width="30%">Revised Estimate Amount</td>
			<td><input   type=text id='revbudamt' size=10  name='revbudamt'  value="" onkeyup="isInteger(this,9,event,'revbudamt'),digit_control('revbudamt',2)"><font size=2>(Rs. in lakhs)</font></td>
		</tr>
	<tr>
		<td class="fnt" width="30%">Annual Maintenance Estimate</td>
		<td><input   type=text id='mainestamt' size=10  name='mainestamt'  readonly="readonly" value=""><font size=2>(Rs. in lakhs)</font></td>
	</tr>
	  -->
	<tr class="tdH">
		<td align="center" colspan="2">
			<input type="hidden" value="" id="EST_SNO" name="EST_SNO">
			<input type="button" value="Add" id='save' onclick="store()"/>
			<input type="button" value="Update" id="update" class="btn" onclick="transaction(5,2);"/>			
			<input type="button" value="Report" onclick="report(3)" /> 
			<input type="reset"  value="Reset" onclick="cls()" />
			<input type="button" value="Exit" onclick="window.close()" />
		 	<input type="hidden" name="key_value" id="key_value" value="">
		</td>
	</tr>
</table>
<table class="table" width="100%" align="center" border="1"
	cellpadding="5" id="etable"
	style="border-collapse: collapse; border-color: skyblue">
	<tr class="tdH">
		<!--<th class="tdText">Select</th>  -->
		<th class="fnt" width="10%">EDIT</th>
		<th class="fnt">Scheme Name</th>
		<!--<th class="fnt" width="10%">Admin Sanction No.</th>
		<th class="fnt" width="10%">Admin Sanction Date</th>
		--><th class="fnt" width="10%">Maint.Estimate Sanction No.</th>
		<th class="fnt" width="10%">Maint.Estimate Sanction Date</th>
		<th class="fnt" width="10%">Estimate Amount <Br>
		(Rs. in lakhs)</th>
		<th class="fnt" width="10%">Status</th>
		<!-- <th class="fnt"  width="10%">Revised Estimate Amount</th> -->
		<!--  <th class="fnt" width="10%">Annual Maintenance Estimate</th>  -->
	</tr>

	<tbody id="edata" align="left" valign="top" />
</table> 
	<input type="hidden" id="rowcount">
	<input type="hidden" id="sch_sno" name="sch_sno" value="<%=sch_sno%>" /> 
	<input type="hidden" id="orgcount" name="orgcount" value="0" />
</form>
</body>
</html>
