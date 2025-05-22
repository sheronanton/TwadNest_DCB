<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page
	import="java.util.*,Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,Servlets.PMS.PMS1.DCB.reports.myf"%>
<%@page import="java.text.DecimalFormat"%><html>
<head>
<script type="text/javascript" src="../../../DCB/scripts/cellcreate.js"></script>
<script type="text/javascript"
	src="../scripts/actual_data_verificaiton.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../../css/AME.css" rel="stylesheet"
	media="screen">
<title>Demand and Collection Monthly Transaction - Checklist</title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript">
	function rld() {
		var res = report_period_verify_2(document.getElementById('month'),
				document.getElementById('year'));
		document.forms["rpt"].submit();
	}
</script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body onload="div_verfiy()">
<form name="rpt" action="actual_data_verificaiton.jsp">
<%
	String userid = "0", Office_id = "", Office_Name = "";
	Connection db_con = null, db_con1 = null;
	Controller obj_new = new Controller();
	try {
		db_con = obj_new.con();
		obj_new.createStatement(db_con);
	} catch (Exception e) {
	}
	DecimalFormat df = new DecimalFormat("0.00");
	try {
		userid = (String) session.getAttribute("UserId");
	} catch (Exception e) {
		userid = "0";
	}
	if (userid == null) {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

//	  Office_id=obj_new.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	Office_id=obj_new.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	

//	Office_id = obj_new.getValue("HRM_EMP_CURRENT_POSTING",	"OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (Office_id.equals(""))
		Office_id = "0";
	Office_Name = obj_new.getValue("com_mst_offices",
			"OFFICE_NAME", "where OFFICE_ID='" + Office_id + "'  ");
	String msg = "", msg2 = "";
	String pmonth = "", pyear = "";
	pmonth = obj_new.isNull(obj_new.setValue("month", request), 1);
	pyear = obj_new.isNull(obj_new.setValue("year", request), 1);

	String yr = obj_new.isNull(obj_new.getValue("PMS_DCB_SETTING",
			"YEAR", " where OFFICE_ID='" + Office_id+"'"), 1);;//obj.setValue("year",request);
	String mt = obj_new.isNull(obj_new.getValue("PMS_DCB_SETTING",
			"MONTH", " where OFFICE_ID='" + Office_id+"'"), 1);;//obj.setValue("month",request);
	if (pmonth.equalsIgnoreCase("0")) {
		pmonth = mt;
		pyear = yr;
	}
	String tot_collection = "";
	String actdmd = "", arrdmd="",wcdmd2 = "", actual_coll = "", fas_collection = "", extcoll_cr = "", extcoll_dr = "", journal_adj = "";

	String cond = Office_id.equalsIgnoreCase("5000")
			? ""
			: " and office_id='" + Office_id+"'";
	String cond1 = Office_id.equalsIgnoreCase("5000")
			? ""
			: " and accounting_for_office_id='" + Office_id+"'";
	if (!pyear.equalsIgnoreCase("0") && !pmonth.equalsIgnoreCase("0")) {
		String qry = " SELECT actdmd,wcdmd2,actual_coll,fas_collection,  extcoll_cr,extcoll_dr,arrdmd  FROM "
				+ " ( SELECT SUM(a.DMD_FOR_MTH_WC) AS actdmd   FROM PMS_DCB_LEDGER_ACTUAL a  WHERE a.MONTH ="
				+ pmonth
				+ "  AND a.YEAR="
				+ pyear
				+ " "
				+ cond
				+ " ) a "
				+ " left OUTER JOIN "
				+ " (SELECT SUM(b.total_amt) AS wcdmd2  FROM PMS_DCB_WC_BILLING b WHERE b.month="
				+ pmonth
				+ " AND b.year="
				+ pyear
				+ "  "
				+ cond
				+ " )b "
				+ " ON (1=1) left OUTER JOIN "
				+ " (select (SUM(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)) AS actual_coll from PMS_DCB_LEDGER_ACTUAL   WHERE MONTH="
				+ pmonth
				+ " "
				+ cond
				+ "  AND YEAR="
				+ pyear
				+ ")c "
				+ " ON (1=1) left OUTER JOIN "
				+ " (select SUM(amount) AS fas_collection  from PMS_DCB_FAS_RECEIPT_VIEW   WHERE cashbook_month="
				+ pmonth
				+ " and  account_head_code <> 120601 "
				+ cond1
				+ "  AND cashbook_year="
				+ pyear
				+ ")d "
				+ " ON (1=1) left OUTER JOIN "
				+ " (select SUM(amount) AS extcoll_cr  from PMS_DCB_OTHER_CHARGES   WHERE cashbook_month="
				+ pmonth
				+ "  AND cashbook_year="
				+ pyear
				+ " "
				+ cond
				+ "   and CR_DR_INDICATOR='CR')e "
				+ " ON (1=1) left OUTER JOIN "
				+ " (select SUM(amount) AS extcoll_dr from PMS_DCB_OTHER_CHARGES   WHERE cashbook_month="
				+ pmonth
				+ "  AND cashbook_year="
				+ pyear
				+ " "
				+ cond
				+ " and CR_DR_INDICATOR='DR')f ON (1=1) LEFT OUTER JOIN (SELECT SUM(amount) AS arrdmd  FROM PMS_DCB_OTHER_CHARGES"
				+"	WHERE cashbook_month="  
	        	+ pmonth
		        + "  AND cashbook_year="
		        + pyear
		        + " "
		        + cond
		        + " AND CR_DR_INDICATOR ='DR' and ACCOUNT_HEAD_CODE  in (780406,780401,780402,780403,780404,780405,780407) )q ON (1=1)";

		//out.println("query"+qry);
		ResultSet rs = obj_new.getRS(qry);
		if (rs.next()) {
			actdmd = obj_new.isNull(rs.getString("actdmd"), 1);
			wcdmd2 = obj_new.isNull(rs.getString("wcdmd2"), 1);
			arrdmd = obj_new.isNull(rs.getString("arrdmd"), 1);

			actual_coll = obj_new
					.isNull(rs.getString("actual_coll"), 1);
			fas_collection = obj_new.isNull(rs
					.getString("fas_collection"), 1);
			extcoll_cr = obj_new.isNull(rs.getString("extcoll_cr"), 1);
			extcoll_dr = obj_new.isNull(rs.getString("extcoll_dr"), 1);
			msg = "<font color='darkgreen' size='2'>(All Figures in Actuals)</font>";
			msg2 = Office_id.equalsIgnoreCase("5000")
					? "All Division"
					: Office_Name;
		}
		journal_adj = Double.toString(Double.parseDouble(extcoll_cr)
				- Double.parseDouble(extcoll_dr));
		tot_collection = Double.toString(((Double
				.parseDouble(fas_collection) + Double
				.parseDouble(extcoll_cr)) - Double
				.parseDouble(extcoll_dr)));
		tot_collection = df.format(Double.parseDouble(tot_collection));
		journal_adj = df.format(Double.parseDouble(journal_adj));
	}
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
%> <input type="hidden" id="pmonth" name="pmonth" value="<%=pmonth%>"><input
	type="hidden" id="pyear" name="pyear" value="<%=pyear%>">

<table width="100%" border="1" style="border-collapse: collapse;">
	<tr>
		<th align="center" colspan="4"><b>Demand and Collection
		Monthly Transaction - Checklist <Br><%=Office_Name%></b></th>
	</tr>
	<tr>
		<td colspan="2" align="left">Year: <select id="year" name="year"
			tabindex="5" onchange="rld()">
			<option value="0" selected="selected">Select</option>
			<%
				for (int j = year - 6; j <= year; j++) {
			%>
			<option value="<%=j%>" <%if (j == Integer.parseInt(pyear)) {%>
				selected <%}%>><%=j%></option>
			<%
				}
			%>
		</select> Month: <select name="month" id="month" onchange="rld()">
			<option value="0" selected="selected">Select</option>
			<%
			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
				for (int i = 1; i <= 12; i++) {
			%>
			<option value="<%=i%>" <%if (i == Integer.parseInt(pmonth)) {%>
				selected <%}%>><%=cmonth[i]%></option>
			<%
				}
			%>
		</select> <%
 	if (!pyear.equalsIgnoreCase("0") && !pmonth.equalsIgnoreCase("0")) {
 %>
		<input type="button" value="Click Details" onclick="div_verfiy()">
		<%
			}
		%> &nbsp;<label><%=msg%></label><a href="data_dynamic_find.jsp">.</a>
		<br>
		<br>
		<b><label id='div_name'></label></b></td>


		<%
			if (Office_id.equalsIgnoreCase("5000")) {

				if (!pyear.equalsIgnoreCase("0")
						&& !pmonth.equalsIgnoreCase("0")) {
		%>
		<td colspan="2" width="50%">
		<div align="right" id='scroll_clipper'>
		<div>
		<table width="100%" align="left" style="border-collapse: collapse;"
			cellspacing="0" cellpadding="2">
			<tr>
				<th width="60%" align="center" colspan="2">TWAD BOARD</th>
				<th align="center">Actual Demand</th>
				<th align="center">DCB Posted Demand</th>
				<th align="center">FAS Total Collection(1)</th>
				<th align="center">Journal Adjustment(2)</th>
				<th align="center">Total Collection(1+2)</th>
				<th align="center">DCB Posted Collection</th>
				
				<th align="center">Arrear Demand</th>
				
			</tr>
			<tr>
				<td width="60%" align="center" colspan="2"><%=msg2%></td>
				<td align="center"><%=wcdmd2%></td>
				<td align="center"><%=actdmd%></td>
				<td align="center"><%=fas_collection%></td>
				<td align="center"><%=journal_adj%></td>
				<td align="center"><%=tot_collection%></td>
				<td align="center"><%=actual_coll%></td>
				
			 <td align="center"><%=arrdmd%></td>
				
				
			</tr>
		</table>
		</div>
		</div>
		</td>
		<%
			}
			}
		%>
	</tr>
</table>
<table>
	<tr>
		<td style="vertical-align: top;">
		<div align="right" id='scroll_clipper'>
		<div>
		<table class="cls" bordercolor="#00FFFF" id="entred_data1" width="50%"
			align="left" style="border-collapse: collapse;" cellspacing="0"
			cellpadding="2">
			<tbody id="entred_body1" align="center"></tbody>
		</table>
		</div>
		</div>
		</td>
		<td style="vertical-align: top;">
		<div align="right" id='scroll_clipper'
			style='width: 750px; vertical-align: top; border-height: 1px; height: 556px; overflow: auto'>
		<div align="right" id='scroll_text'>
		<table class="cls" bordercolor="#00FFFF"
			style="border-collapse: collapse;" id="entred_data2" width="90%"
			align="left" border="1" cellspacing="0" cellpadding="2">
			<tbody id="entred_body2" align="center"></tbody>
		</table>
		</div>
		</div>
		</td>
	</tr>
	<tr>
		<td><input type="button" value="Exit" onclick="javascript: window.close();"></td>
	</tr>
</table>

</form>
</body>
</html>
