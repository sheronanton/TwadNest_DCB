<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Calendar" %>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%>
<%@page import="java.sql.Connection"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receipt Difference  </title>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
 
 
 <%
 
	String userid="",Office_id="",Office_Name="",smonth="",syear="",table_column="";
	String table_header="",table_heading="",html="";
	Controller obj=null;
	Connection con;
try {
	
	String new_cond=Controller.new_cond;
		  obj = new Controller();
		con = obj.con();
		obj.createStatement(con);
			  
		try {
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) {
			userid = "0";
		}
		if (userid == null) {
			response.sendRedirect(request.getContextPath()+ "/index.jsp");
		}
		Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID",
				"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
						+ userid + "')");
		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " 	");
		smonth=obj.setValue("smonth",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		syear=obj.setValue("syear",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
 String qry="SELECT BENEFICIARY_NAME ,btype , ben.BENEFICIARY_SNO AS BENEFICIARY_SNO, "+
 			" COLN_CUR_YR_WC,COLN_INT_WC,COLN_MAINT,COLN_INT_MAINT,COLN_YESTER_YR_WC,(COLN_CUR_YR_WC+COLN_INT_WC+COLN_MAINT+COLN_INT_MAINT+COLN_YESTER_YR_WC) as total_receipt, TOTAL_AMOUNT,(TOTAL_AMOUNT-(COLN_CUR_YR_WC+COLN_INT_WC+COLN_MAINT+COLN_INT_MAINT+COLN_YESTER_YR_WC)) AS dff "+
	  		" FROM "+
	  		" (SELECT BENEFICIARY_NAME ,BENEFICIARY_SNO,BENEFICIARY_TYPE_ID, "+
	    	" ( SELECT BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE WHERE BEN_TYPE_ID=BENEFICIARY_TYPE_ID ) AS btype, "+   
	      	" (SELECT BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE WHERE BEN_TYPE_ID=BENEFICIARY_TYPE_ID ) AS BEN_TYPE_DESC "+
	        " FROM PMS_DCB_MST_BENEFICIARY  WHERE office_id      ="+Office_id+" AND status           ='L' "+
	    	" AND BENEFICIARY_SNO IN "+
	    	" ( SELECT BENEFICIARY_SNO  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id ="+Office_id+"  AND METER_STATUS='L'    ) "+
	      	" ORDER BY BENEFICIARY_TYPE_ID "+
	    	" )ben "+
	    	" LEFT JOIN   ( "+
			" SELECT SUM(TOTAL_AMOUNT) AS TOTAL_AMOUNT ,SUB_LEDGER_TYPE_CODE ,SUB_LEDGER_CODE,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,CASHBOOK_YEAR "+
	    	"  FROM FAS_RECEIPT_MASTER "+
	    	" WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" "+
	    	" AND CASHBOOK_MONTH            ="+smonth+"  AND CASHBOOK_YEAR             ="+syear+
	    	" AND SUB_LEDGER_TYPE_CODE      =14 AND RECEIPT_STATUS            ='L' "+
	    	" GROUP BY ACCOUNTING_FOR_OFFICE_ID,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CASHBOOK_MONTH,CASHBOOK_YEAR "+
		    " )rec "+
		    " ON rec.SUB_LEDGER_CODE=ben.BENEFICIARY_SNO "+
	  		" LEFT JOIN "+
	  		" (SELECT OFFICE_ID,BENEFICIARY_SNO,BILL_MONTH, BILL_YEAR,decode(COLN_CUR_YR_WC,null,0,COLN_CUR_YR_WC) as COLN_CUR_YR_WC, decode(COLN_INT_WC,null,0,COLN_INT_WC) as COLN_INT_WC,  decode(COLN_MAINT,null,0,COLN_MAINT) as COLN_MAINT,  decode(COLN_INT_MAINT,null,0,COLN_INT_MAINT) as COLN_INT_MAINT,    decode(COLN_YESTER_YR_WC,null,0,COLN_YESTER_YR_WC) as COLN_YESTER_YR_WC  FROM PMS_DCB_TRN_BILL_DMD )dmd "+
	    	" ON dmd.OFFICE_ID       =rec.ACCOUNTING_FOR_OFFICE_ID "+
	  		" AND dmd.BENEFICIARY_SNO=rec.SUB_LEDGER_CODE AND dmd.BENEFICIARY_SNO=ben.BENEFICIARY_SNO "+
	  		"  AND dmd.BILL_MONTH     =rec.CASHBOOK_MONTH AND dmd.BILL_YEAR      =rec.CASHBOOK_YEAR "+
	 		" ORDER BY btype,BENEFICIARY_NAME asc ";
 		 
		 table_column = "BENEFICIARY_SNO,BENEFICIARY_NAME,btype,COLN_CUR_YR_WC,COLN_YESTER_YR_WC, COLN_INT_WC,COLN_MAINT,COLN_INT_MAINT,total_receipt,TOTAL_AMOUNT,dff" ;	   	   
		table_header = "Beneficiary Sl.No,Beneficiary Name,Beneficiary Type,Water Charge,Yester Year,Interst,Maintatance Charge,Maintatance Interest ,Total Collection Taken in DCB,Collection in FAS ,Amount Difference";
		String table_td_set = "width='15%',width='25%',width='15%',width='15%',width='15%',width='15%',width='15%',width='15%',width='5%',width='15%',width='15%',width='15%'";
		table_heading = "    - "+ Office_Name + "";
	String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
	html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set); 
	
	con.close();
	con=null;
}catch(Exception e) 
{
} 
 %>
 <table align="center" border=1 style="border-collapse: collapse;border-color: skyblue" >
 <tr>
 	<td align="center"><%=Office_Name%></td>
 </tr>
 <tr>
 	<td align="center"> Month and Year : <%=obj.month_val(smonth)%> <%=syear %></td>
 </tr>
 <tr>
 	<td align="center"> Receipt Difference </td>
 </tr>
 <tr>
					<td colspan=2 align="right"><input class="fb2" type="button" id="" value="Close"   onclick="window.close()"/> </td>
				</tr> 
 <tr>
 <td>
  <%=html%>
  </td>
  </tr>
  </table>
</body>
</html>