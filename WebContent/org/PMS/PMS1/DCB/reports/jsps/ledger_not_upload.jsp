<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ledger Data Omission</title>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
	String userid = "", Office_id = "", Office_Name = "", head = "", table_header = "", table_heading = "",table_column="";
	String html="",qry="",smonth="",syear="";
	Controller obj = null;
	Connection con;
	try 
	{
    	String new_cond = Controller.new_cond;
		obj = new Controller();
		con = obj.con();
		obj.createStatement(con);
		head=" Ledger Data Omission  ";
		try {
			userid = (String) session.getAttribute("UserId");
		} catch (Exception e) 
		{
			userid = "0";
		}
		if (userid == null) 
		{
			response.sendRedirect(request.getContextPath()+ "/index.jsp");
		}
		Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID from SEC_MST_USERS where USER_ID='"+userid+"')");
	    Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	"); 		
 		smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID=" + Office_id), 1); 		
 		syear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);	
		qry=" select "+ 
			" mastertab.BENEFICIARY_NAME || '('  || mastertab.beneficiary_sno || ')' as BENEFICIARY_NAME,btype.BEN_TYPE_DESC as BEN_TYPE_DESC "+
			" from  "+ 
			" ( "+ 
			" SELECT BENEFICIARY_NAME,beneficiary_sno ,BENEFICIARY_TYPE_ID FROM pms_dcb_mst_beneficiary  WHERE office_id = "+Office_id+"  AND status = 'L' AND beneficiary_sno IN "+
			" ( "+
		    "     SELECT beneficiary_sno FROM pms_dcb_monthly_pr  WHERE YEAR="+syear+"  AND MONTH ="+smonth+"  AND office_id =  "+Office_id+
		    " ) AND beneficiary_sno not IN (SELECT beneficiary_sno  FROM pms_dcb_ledger_actual  WHERE YEAR="+syear+"  AND MONTH = "+smonth+" AND office_id = "+Office_id+") "+
			" ) mastertab "+
			" join  "+
			" ( "+
			" 	select * from PMS_DCB_BEN_TYPE "+ 
			" )btype "+
			" on btype.BEN_TYPE_ID=mastertab.BENEFICIARY_TYPE_ID ";
		 
		table_column = "BENEFICIARY_NAME,BEN_TYPE_DESC";	   	   
 		table_header = "Beneficiary,Beneficiary Type";
 		String table_td_set = "width='25%',width='15%'";
 		table_heading = "   "+head+" - "+ Office_Name + "";
 		String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);
	} catch (Exception e) {
	}
%>
	<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%> | TWAD Nest - Phase II </title>
		<Table      id="" width=75% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan="2"><center><b><%=table_heading%></b> </center></td>
				</tr> 
				<tr>
					<td colspan="2" align="right"><input type="button" class="fb2" value="Back" onclick="history.go(-1)">  <input class="fb2"   type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
				<Tr>
					<td colspan="2"><%=html%></td>  
				</Tr>
				<tr>
					<td colspan="2" align="center"><input type="button" class="fb2" value="Back" onclick="history.go(-1)">  <input class="fb2"   type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
		</Table>
		 <input type="hidden" id="fyear"  name="fyear"  value="<%=syear%>"></input> 
       	 <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>'></input> 
</body>
</html>