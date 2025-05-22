<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
 
Connection con = null;
Controller obj = new Controller();
Controller obj2 = new Controller();
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
	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING",
			"OFFICE_ID",
			"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
					+ userid + "')");

	month = obj.setValue("smonth", request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
	year = obj.setValue("syear", request);
	
	String DCB_Freeze="N";
	DCB_Freeze=obj.getValue("PMS_DCB_FREEZE_RECEIPT","RECEIPT_FREEZE"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year).trim();	
	String DCB_Freeze_msg=DCB_Freeze.trim().equalsIgnoreCase("Y") ? " Receipt Verified Done ": "Not Verified";
	if (DCB_Freeze.trim().equalsIgnoreCase("Y"))
	{
		out.println("<center><a href='receipt_verification_form.jsp?smonth="+month+"&syear="+year+"'> DCB  Receipt Verification Already Completed.....(Click Here)</a></center>");
	}
	else
	{
			String aid= obj.setValue("aid", request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);
		 	int count=obj.getCount("PMS_DCB_FREEZE_RECEIPT","where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year);
		 	if (count==0)
		 	{
				//obj.delRecord("PMS_DCB_FREEZE_RECEIPT"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year,con);
				Hashtable ht=new Hashtable();
				ht.put("OFFICE_ID",Office_id);
				ht.put("CASHBOOK_MONTH",month);
				ht.put("CASHBOOK_YEAR",year);
				ht.put("RECEIPT_FREEZE","'Y'");
				ht.put("FREEZED_DATE","clock_timestamp()");
				ht.put("UPDATED_DATE","clock_timestamp()");
				ht.put("UPDATED_BY_USER_ID","'"+userid+"'");
				ht.put("ACCOUNTING_UNIT_ID",aid);
				int r=obj.recordSave(ht,"PMS_DCB_FREEZE_RECEIPT",con);
				if (r > 0) {%> 
				<%
				out.println("<center><a href='journal_report.jsp'>Receipt Verfied Ok.!(Click Here)</a><center>");			
				}
		 	}
		 	else
		 	{
		 		
		 		Hashtable ht = new Hashtable();
				Hashtable condht = new Hashtable();
				ht.put("RECEIPT_FREEZE","'Y'");
				ht.put("FREEZED_DATE","clock_timestamp()");
				condht.put("OFFICE_ID", Office_id);
				condht.put("CASHBOOK_MONTH", month);  
				condht.put("CASHBOOK_YEAR", year);		
				int r=obj.recordSave(ht,condht, "PMS_DCB_FREEZE_RECEIPT ",con);
		 	}
		}
	} catch (Exception e) {
	out.println("e"+e);
}

%>
 
</body>
</html>