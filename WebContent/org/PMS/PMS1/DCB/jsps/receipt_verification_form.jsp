<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.DecimalFormat"%><html>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="dcb.reports.Fas_Master_Transaction"%>
<%@page import="dcb.reports.Fas_Master_Transaction_DAO"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
</title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
</head>
<body>
<form action="receipt_verificaiton_a.jsp" method="get">
<%
	Connection con = null;
	Controller obj = new Controller();
	Controller obj2 = new Controller();
	DecimalFormat df=new DecimalFormat("0");
	try {
		con = obj.con();
		obj.createStatement(con);
		obj2.createStatement(con);
	} catch (Exception e) {
	}
	session = request.getSession(false);
	String userid = (String) session.getAttribute("UserId");
	if (userid == null) {
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	String Office_id = "", month = "", year = "";
	try {
	//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING",	"OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		month = obj.setValue("smonth", request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
		year = obj.setValue("syear", request);
		;//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);

	} catch (Exception e) {
	}
	String new_cond = Controller.new_cond;
	String meter_status = Controller.meter_status;
	try {
		String monval = obj.month_val(month);
		String Office_Name = obj.getValue("com_mst_offices",
				"OFFICE_NAME", "where OFFICE_ID=" + Office_id + " ");
		String auid = obj.getValue("FAS_MST_ACCT_UNIT_OFFICES",
				"ACCOUNTING_UNIT_ID", "where ACCOUNTING_FOR_OFFICE_ID="
						+ Office_id + " ");
		//	String qry="select CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,ACCOUNT_HEAD_CODE from FAS_RECEIPT_TRANSACTION where (accounting_for_office_id,CASHBOOK_MONTH,CASHBOOK_YEAR) =(select "+Office_id+
		//			","+month+" ,"+year+" from dual) and  exists (select receipt_no from FAS_RECEIPT_MASTER  where accounting_for_office_id="+Office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year+" and sub_ledger_type_code=14   and receipt_no=FAS_RECEIPT_TRANSACTION.receipt_no" +
		//						" ) and not exists (  select 'a' from PMS_MST_PROJECTS_VIEW a where a.office_id="+Office_id+" and a.project_id=FAS_RECEIPT_TRANSACTION.SUB_LEDGER_CODE)" ;
		//"   not exists ( select 'a' from PMS_DCB_RECEIPT_ACCOUNT_MAP a where COLLECTION_TYPE in (7,8,9) and a.ACCOUNT_HEAD_CODE=FAS_RECEIPT_TRANSACTION.ACCOUNT_HEAD_CODE )";
		String qry = " SELECT   to_char(UPDATED_DATE,'dd/mm/yyyy') as updatedate, case when  receipt_type='B' then 'Bank'  else 'Cash'   end as receipt_type ,RECEIPT_NO, TO_CHAR(RDATE,'DD-MM-YYYY') as RDATE, AMOUNT, ACCOUNT_HEAD_CODE, TRAN_SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, CR_DR_INDICATOR, SCH_SNO,(select sch_name from pms_sch_master where sch_sno=a.sch_sno) as sch_name, "
				+ " BENEFICIARY_NAME,BENEFICIARY_SNO, BENEFICIARY_TYPE_ID_SUB,(select BEN_TYPE_DESC from PMS_DCB_BEN_TYPE where BEN_TYPE_ID=a.BENEFICIARY_TYPE_ID_SUB) as ben_type_dec,	  case  "
				+ " when ACCOUNT_HEAD_CODE not in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP) then    '<font color=red>Wrong Receipt Acc.Head Code</font>' "
				+ " when (SUB_LEDGER_CODE is null  or  SUB_LEDGER_CODE=0)  then "
				+ " '<font color=red>SL Code Missing</font> '  " 
				+ " when (BENEFICIARY_SNO is null  or  BENEFICIARY_SNO=0 or MASTER_SUB_LEDGER_TYPE_CODE <> 14 or MASTER_SUB_LEDGER_TYPE_CODE is null )  then "
				+ " '<font color=red>Beneficiary Code Missing / Master SL Type Code Missing</font> '  "
				+ " when (TRAN_SUB_LEDGER_TYPE_CODE is null  or TRAN_SUB_LEDGER_TYPE_CODE=0) then   '<font color=red>SL Type Code Missing</fond> ' "
				+ " when (MASTER_SUB_LEDGER_TYPE_CODE is null  or MASTER_SUB_LEDGER_TYPE_CODE=0) then   '<font color=red>Master SL Type Code Missing</fond> ' "
				+ " WHEN (TRAN_SUB_LEDGER_TYPE_CODE =14 or TRAN_SUB_LEDGER_TYPE_CODE =0 or TRAN_SUB_LEDGER_TYPE_CODE is null)  THEN 'SL Type  Code must be Scheme code 'when a.sch_sno not in (select distinct scheme_sno from pms_dcb_mst_beneficiary_metre where beneficiary_sno=a.BENEFICIARY_SNO) then "
				+ " '<font color=red>SL Code Mis-Match</font> '   end as Error 	FROM PMS_DCB_FAS_RECEIPT_VIEW_ALL a		WHERE  cashbook_month="
				+ month+" AND cashbook_year=" + year + " and 	accounting_for_office_id="+ Office_id
				+ "  	order by Error ,RECEIPT_NO,BENEFICIARY_NAME";
				
			 
 			%> 
 			
 			
 			<%
		String html = "<table width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c298'>"
				+ "<tr ><th colspan=10 align=center>"
				+ Office_Name
				+ " ("
				+ auid
				+ ")</th></tr>"
				+ "<tr ><th colspan=10  align=center>DCB Receipt Verificaition for "
				+ monval
				+ "-"
				+ year
				+ "</th> </tr>";%> 
				
		<% 
			 
				Fas_Master_Transaction_DAO obj_new=new Fas_Master_Transaction_DAO();
				Fas_Master_Transaction dao=obj_new.actualValue(Integer.parseInt(month),Integer.parseInt(year),Integer.parseInt(Office_id));
			 
			 
		html+= "<tr ><td colspan=10>"; 
		html += "<div id='scroll_clipper' style='position:relative; width:1020px; border-height:1px; height: 480px; overflow:auto;white-space:nowrap;'>";
		html += "<div id='scroll_text'  ><table  cellspacing='0' cellpadding='3' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'   border=2><tr><td width='3%'>Sl.No</td><td>Beneficiary Name</td><td width='5%'>Receipt No/<Br> Date </td><td width='5%'>Receipt Type</td><td width='5%'>Sub ledger <br> Type Code</td><td  width='25%'>Sub ledger Code&nbsp;&nbsp;(Project Id)</td><td align=center>&nbsp;&nbsp;&nbsp;</td><td width='5%'>Account Head Code</td><td align=center width='7%'>Amount</td><td align=center>Correct SL Description <br>( As Per Beneficiary Meter Record) </td></tr>";
		String sch_sno = "0", type = "0", receipt_no = "0", receipt_dt = "", ben_sno = "", amt = "", oben_sno = "0", ben_name = "", ACCOUNT_HEAD_CODE = "", desc = "";
		String innerHTML = "";
		String qry_res = "",RECEIPT_TYPE="";
		double net_amt=0.0;
		 
		ResultSet rs = obj.getRS(qry); 
		int i = 0;
		int c = 0;
		while (rs.next()) {
			i++; 
			desc = obj.isNull(rs.getString("Error"), 2);
			receipt_dt = obj.isNull(rs.getString("RDATE"), 1);
			amt = obj.isNull(rs.getString("AMOUNT"), 1);
			net_amt+=Double.parseDouble(amt);
			ben_name = obj.isNull(rs.getString("BENEFICIARY_NAME"), 1);
			ACCOUNT_HEAD_CODE = obj.isNull(rs.getString("ACCOUNT_HEAD_CODE"), 1);
			sch_sno = obj.isNull(rs.getString("sch_name"), 3) + "&nbsp;&nbsp;("+ obj.isNull(rs.getString("SUB_LEDGER_CODE"), 3)+ ")";
			type = obj.isNull(rs.getString("TRAN_SUB_LEDGER_TYPE_CODE"), 1);
			type = obj.getValue("com_mst_sl_types","SUB_LEDGER_TYPE_DESC"," where SUB_LEDGER_TYPE_CODE=" + type);
			RECEIPT_TYPE= obj.isNull(rs.getString("RECEIPT_TYPE"), 1);
			receipt_no = obj.isNull(rs.getString("RECEIPT_NO"), 1);
			ben_sno = obj.isNull(rs.getString("BENEFICIARY_SNO"), 1);
			String desc_new = "";
			if (!desc.equalsIgnoreCase("-")) {
				html += "<tr style='font-size: 0.91em; color:red '><td   align='center' width='5%'>";
				desc_new = desc +" "+obj.isNull(rs.getString("updatedate"), 2);;
			} else {
				html += "<tr style='font-size: 0.91em;'><td   align='center' width='5%'>";
				desc_new = "<font color='green' size=3><center>O.K.</center></font>";
			}
			
			html += i+"</td><td>"+ben_name+"(&nbsp;"+ben_sno+"&nbsp;) </td><td  align='center' width='5%'>"
					+ receipt_no+"&nbsp;&nbsp;<font color='blue' size=3><b>/</b></font>&nbsp;&nbsp;"+receipt_dt
					+ "</td><td  align='center' width='5%'>"+ RECEIPT_TYPE+"</td><td  align='center' width='5%'>"+type+"</td><td  align='left'  width='25%'>"
					+ sch_sno.trim()+ "</td><td align=left>" + desc_new + "</td><td  align='center' width='5%'>"+ACCOUNT_HEAD_CODE+"</td><td  align='right' width='5%'>"  
					+ amt+ "</td>";
			if (!desc.equalsIgnoreCase("-")) 
			{
				qry_res += "<td><table width='100%' cellspacing='0' cellpadding='3' style='BORDER-COLLAPSE: collapse' border='1' borderColor='darkblue'   border=2>";
				String qry2 = "SELECT sch.SCH_NAME,sch.PROJECT_ID,met.SCHEME_SNO FROM (SELECT BENEFICIARY_SNO,OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY  WHERE status='L' "
						+" and OFFICE_ID="+Office_id+"  AND BENEFICIARY_SNO="+ben_sno
						+ " ) ben JOIN (SELECT distinct SCHEME_SNO,BENEFICIARY_SNO,OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY_METRE "
						+ " WHERE meter_status ='L' AND BENEFICIARY_SNO="
						+ ben_sno
						+ " ) met ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO AND met.OFFICE_ID=ben.OFFICE_ID "
						+ " JOIN  ( SELECT SCH_SNO,SCH_NAME, PROJECT_ID FROM PMS_SCH_MASTER   )sch ON sch.SCH_SNO=met.SCHEME_SNO    ";
				c++;
				int r1 = 0;
				String project_id = "";
				String SCHEME_SNO = "";
				ResultSet rs2 = obj2.getRS(qry2);
				while (rs2.next()) 
				{
					SCHEME_SNO = obj2.isNull(rs2.getString("SCHEME_SNO"), 1);
					project_id = obj2.isNull(rs2.getString("PROJECT_ID"), 1);
					r1++;
					qry_res += "<tr style='font-size: 0.91em;'>";
					qry_res += "<td><font color=green>"+ obj2.isNull(rs2.getString("SCH_NAME"), 1)
							+ "&nbsp;(&nbsp; " + project_id
							+ " &nbsp; )&nbsp;</font></td>";
					qry_res += " </tr>";
				}
				qry_res += "</table> ";  
			} else {
				qry_res += "<td>&nbsp;</td>";
			}
    
			html += qry_res;
			html += "</td></div></div></tr>";
			qry_res = "";

		}
		String html2 = "";
		
		html2 += "<tr ><td><font color=blue size=2> <b> Total DCB Receipt(Beneficiary Wise)&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp; "+dao.getMaster_amount()+"</font></td><td> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; <font color=blue size=2> <b> Total DCB Receipt(Project)&nbsp;&nbsp;  : &nbsp;&nbsp; "+dao.getTransaction_amount()+" </font> </td></tr>";
		if (i==0)
		{
			html2 += "<tr><td align=center colspan=2 > <font color='green' size='3'><b>No Receipts found ....... </font>  <input type='submit' value='Confirm'>&nbsp;&nbsp;&nbsp;<input type=button value='EXIT'  onclick='window.close()'></td></tr></table>";
		}   
		else   
		{
			if (c == 0) { 
				html2 += "<tr bgcolor=green><td align=center colspan=2 ><font color=white size=3>  No Errors in Sub Ledger Type Code and  Subledger Code .... Verify Amount.... If Tallied,  Click  RECEIPT VERIFIED  below  <b>.</font></td></tr>";
				html2 += "<tr ><td  colspan=2  align=center><input type=submit style='font-weight: bold;font-size:14;font-weight:bold;color :darkGreen' value='RECEIPT VERIFIED'>&nbsp;&nbsp;&nbsp;<input type=button value='EXIT'  onclick='window.close()'></td></tr></table>";
				
			} else {
				html2 += "<tr><td align=center  colspan=2><font color=red size=2> Errors Found in Receipt are shown in Red.... Please Cancel those Receipts and Re-Enter DCB Receipt</font></td></tr>";
				html2 += "<tr><td align=center  colspan=2><font  size=2> Water Charges can be Freezed only if DCB Receipt is Verfied.. If any clarificaiton please contact TWAD,HO</font></td></tr>";
				html2 += "<tr   colspan=2><th   align=center><input type=button value='EXIT' onclick='window.close()'></th></tr></table>";
			}
		}
		html += "</table></td></tr>" + html2 + "</table><input type=hidden name='aid' value="+auid+">";
		out.println(html + " ");
		html = "";
	} catch (Exception e) {
		System.out.println(e);  
	}  
%>

<input type="hidden" name="smonth" value="<%=month%>"/> 
<input type="hidden" name="syear" value="<%=year%>"/>
</form>
</body>
 
</html>