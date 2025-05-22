<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="java.sql.Connection,java.sql.ResultSet,java.sql.PreparedStatement,java.sql.Statement;"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  <script type="text/javascript" src="../scripts/Office_Shift_new.js">  </script>
  <script type="text/javascript">

  function callReport()
{
	 
	 
	 var fyear=document.getElementById('fyear').value;
	 var fmonth=document.getElementById('fmonth').value;
	 
     window.open("../../../../../Bill_Demand?command=prmispdf&fmonth="+fmonth+"&fyear="+fyear);
}
  
  </script> 
</head>

 
<%
		String new_cond=Controller.new_cond;
		String meter_status=Controller.meter_status;
		
		
		int total_meter_location=0,pumping_count=0;
 	 	String userid = "0", Office_id = "", Office_Name = "", table_heading = "", table_column = "", table_header = "";
 		String smonth = "0", syear = "0", html = "";
 		Controller obj = null;
 		String metstatus="";
 	try {
 	
 		Connection con;
 		obj = new Controller();
 		con = obj.con();
 		obj.createStatement(con);
 		PreparedStatement ps = null;

 		//metre count in PMS_DCB_TRN_MONTHLY_PR table
 		String monthly_count = "", metre_count = "";
 		
 		//con = Obj.con();
 		

 		try {
 			userid = (String) session.getAttribute("UserId");
 		} catch (Exception e) {
 			userid = "0";
 		}
 		if (userid == null) {
 			response.sendRedirect(request.getContextPath()+ "/index.jsp");
 		}
 		
 		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

 	//	Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
 		if (Office_id.equals(""))
 			Office_id = "0";
 		Office_Name = obj.getValue("com_mst_offices",
 				"OFFICE_NAME", "where OFFICE_ID=" + Office_id + " 	");
 		smonth = obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID=" + Office_id), 1);
 		syear = obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID=" + Office_id), 1);
 		
 		  
 		  total_meter_location = obj.getCount("PMS_DCB_MST_BENEFICIARY_METRE"," where "+meter_status+" BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id);	
 		  pumping_count = obj.getCount("PMS_DCB_TRN_MONTHLY_PR"," where   BENEFICIARY_SNO in ( select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" office_id="+Office_id+" ) and   office_id="+Office_id+" and month="+smonth+" and year="+syear);
 		//metre count in PMS_DCB_MST_BENEFICIARY_METRE table
 		 
int meter_count=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE" ,"count(distinct BENEFICIARY_SNO) ","c", "where "+meter_status+" OFFICE_ID="+Office_id ));
int ben_count=Integer.parseInt(obj.getValue("PMS_DCB_MST_BENEFICIARY" ,"count(distinct BENEFICIARY_SNO) ","c", "where "+new_cond+" OFFICE_ID="+Office_id ));
	
  
 		
 	
	if (meter_count==ben_count)
	{
		
	}
	else
	{
		metstatus="<font color='red'>Check Meter Missing Status </font>";
	}
	
	
			String qry="SELECT tr.ACCOUNTING_UNIT_ID, " 
			 		+"  tr.account_head_code                   AS account_head_code, " 
			 		+"  tr.amount                              AS amount, " 
			 		+"  tr.CR_DR_INDICATOR                     AS CR_DR_INDICATOR , " 
			 		+"  tr.VOUCHER_NO                          AS VOUCHER_NO, " 
			 		+"  ac.ACCOUNT_HEAD_DESC                   AS ACCOUNT_HEAD_DESC, " 
			 		+"  TO_CHAR(mas.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, " 
			 		+"  tr.PARTICULARS                         AS PARTICULARS, " 
			 		+"  ben.BENEFICIARY_NAME                   AS BENEFICIARY_NAME, " 
			 		+"  ben.BENEFICIARY_SNO " 
			 		+"FROM " 
			 		+"  ( SELECT * " 
			 		+"  FROM FAS_JOURNAL_TRANSACTION " 
			 		+"  WHERE ACCOUNTING_FOR_OFFICE_ID= "+Office_id 
			 		+"  AND account_head_code        IN " 
			 		+"    ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP " 
			 		+"    ) " 		 	 	 
			 		+"  AND SUB_LEDGER_TYPE_CODE=14 " 
			 		 //+ " AND SUB_LEDGER_CODE     = "+ben+
			 		+"  AND CASHBOOK_MONTH      = "+smonth 
			 		+"  AND CASHBOOK_YEAR       =  "+syear 
			 		+"  )tr " 
			 		// Journal type considered for  others charges during demand generation 
			 		// journal type code 73,56,57,53,75 from FAS_MST_JOURNAL_TYPE Table
			 		+"JOIN " 
			 		+"  (SELECT * " 
			 		+"  FROM FAS_JOURNAL_MASTER " 
			 		+"  WHERE JOURNAL_TYPE_CODE    IN (73,56,57,53,75,87) " 
			 		+"  AND JOURNAL_STATUS          ='L' " 
			 		+"  AND CASHBOOK_MONTH          =  "+smonth 
			 		+"  AND CASHBOOK_YEAR           =  "+syear 
			 		+"  AND ACCOUNTING_FOR_OFFICE_ID= " +Office_id+" and VOUCHER_NO in (select VOUCHER_NO from PMS_DCB_OTHER_CHARGES where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+smonth+" and CASHBOOK_YEAR="+syear+" )"
			 		+"  )mas " 
			 		+"ON mas.ACCOUNTING_UNIT_ID       =tr.ACCOUNTING_UNIT_ID " 
			 		+"AND mas.ACCOUNTING_FOR_OFFICE_ID=tr.ACCOUNTING_FOR_OFFICE_ID " 
			 		+"AND mas.CASHBOOK_YEAR           =tr.CASHBOOK_YEAR " 
			 		+"AND mas.CASHBOOK_MONTH          =tr.CASHBOOK_MONTH " 
			 		+"AND mas.VOUCHER_NO              =tr.VOUCHER_NO " 
			 		+"JOIN " 
			 		+"  ( SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS " 
			 		+"  )ac " 
			 		+"ON ac.ACCOUNT_HEAD_CODE=tr.account_head_code " 
			 		+"JOIN " 
			 		+"  (SELECT BENEFICIARY_NAME, " 
			 		+"    BENEFICIARY_SNO " 
			 		+"  FROM PMS_DCB_MST_BENEFICIARY " 
			 		+"  WHERE "+new_cond+" OFFICE_ID="+ Office_id
			 		+"  )ben " 
			 		+"ON ben.BENEFICIARY_SNO=tr.SUB_LEDGER_CODE " 
			 		+"ORDER BY VOUCHER_NO" ;
 		
 		 
 		table_column = "VOUCHER_NO,VOUCHER_DATE,BENEFICIARY_NAME,account_head_code,ACCOUNT_HEAD_DESC,CR_DR_INDICATOR,PARTICULARS,amount";
 		table_header = "Voucher No,Voucher Date, Beneficiary Name,Account Head Code,Account Head Description,CR/DR,Particulars,Amount";
 		String table_td_set = ",,align=left width='25%',align=center width='5%',align=left width='25%',align=center width='2%',align=center width='15%',align=center width='5%'";
 		table_heading = "Other Charges From Journal - "+ Office_Name + "";
 		String tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		//html = obj.genReport(qry, table_header, table_column, tab,table_heading, table_td_set);

 		
 		String qry1=" SELECT tr.ACCOUNTING_UNIT_ID, "+
 		"  tr.account_head_code, "+
 		"  tr.amount, "+
 		"   tr.CR_DR_INDICATOR , "+
 		"   tr.VOUCHER_NO, "+
 		"    ac.ACCOUNT_HEAD_DESC, "+
 		"   TO_CHAR(mas.VOUCHER_DATE,'DD/MM/YYYY') AS VOUCHER_DATE, "+
 		"   tr.PARTICULARS , "+
 		"   ben.BENEFICIARY_NAME, "+
 		"   ben.BENEFICIARY_SNO "+
 		"  FROM "+
 		"    (SELECT * "+
 		"    FROM FAS_JOURNAL_MASTER "+
 		"    WHERE JOURNAL_TYPE_CODE    IN (56,57,53,66) "+
 		"    AND JOURNAL_STATUS          ='L' "+
 		"    AND CASHBOOK_MONTH          = "+smonth+
 		"    AND CASHBOOK_YEAR           = "+syear+
 		"    AND ACCOUNTING_FOR_OFFICE_ID="+Office_id+
 		"    )mas "+
 		"  JOIN "+
 		"    (SELECT * "+
 		"    FROM FAS_JOURNAL_TRANSACTION "+
 		 "    WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+
 		"    AND CASHBOOK_MONTH            = "+smonth+
 		"    AND CASHBOOK_YEAR             = "+syear+"  and  CR_DR_INDICATOR ='DR' "+
//	 		"    AND VOUCHER_NO NOT           IN "+
// 		"      (SELECT VOUCHER_NO "+
 //		"      FROM PMS_DCB_OTHER_CHARGES "+ 
 	//	"      WHERE CASHBOOK_MONTH  ="+inp_month+
 		//"      AND CASHBOOK_YEAR     ="+inp_year+
 		//"     AND ACCOUNTING_UNIT_ID="+A_u_id+
 		"	and account_head_code in (SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP ) "+	 
 		"   "+
 		   "   AND VOUCHER_NO IN "+
 		  "     (SELECT VOUCHER_NO "+
 		 "     FROM FAS_JOURNAL_MASTER "+
 		"      WHERE JOURNAL_TYPE_CODE    IN (56,57,53) "+
 		"      AND JOURNAL_STATUS          ='L' "+
 		"     AND CASHBOOK_MONTH          =  "+smonth+
 		"     AND CASHBOOK_YEAR           = "+syear+
 		"      AND ACCOUNTING_FOR_OFFICE_ID="+Office_id+  
 		"      ) "+
 		"    )tr "+
 		 "  ON tr.ACCOUNTING_UNIT_ID       =mas.ACCOUNTING_UNIT_ID "+
 		"  AND tr.ACCOUNTING_FOR_OFFICE_ID=mas.ACCOUNTING_FOR_OFFICE_ID "+
 		"  AND tr.CASHBOOK_YEAR           =mas.CASHBOOK_YEAR "+
 		"  AND tr.CASHBOOK_MONTH          =mas.CASHBOOK_MONTH "+
 		"  AND tr.VOUCHER_NO              =mas.VOUCHER_NO "+
 		"  JOIN "+
 		"  (SELECT BENEFICIARY_NAME, "+
 				 "    BENEFICIARY_SNO "+
 		   "   FROM PMS_DCB_MST_BENEFICIARY "+
 		 "   WHERE STATUS ='L' "+
 			 "  AND OFFICE_ID="+Office_id+
 		 "  )ben "+
 		 "  ON ben.BENEFICIARY_SNO=mas.SUB_LEDGER_CODE "+
 		"  JOIN "+
 		"  ( SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS "+
 				 "  )ac "+
 	 	  "  ON ac.ACCOUNT_HEAD_CODE=tr.account_head_code  order by VOUCHER_NO";	
 		
 		
 		
 		qry1=" SELECT VOUCHER_NO, VOUCHER_DATE,(SELECT BENEFICIARY_NAME  FROM PMS_DCB_MST_BENEFICIARY WHERE BENEFICIARY_SNO= a.BENEFICIARY_SNO AND status='L' ) AS BENEFICIARY_NAME, "+
		   " account_head_code ,(SELECT ACCOUNT_HEAD_DESC  FROM COM_MST_ACCOUNT_HEADS  WHERE ACCOUNT_HEAD_CODE= a.ACCOUNT_HEAD_CODE  ) AS ACCOUNT_HEAD_DESC,  CR_DR_INDICATOR, "+
		   " PARTICULARS,  AMOUNT , (SELECT JOURNAL_TYPE_DESC FROM FAS_MST_JOURNAL_TYPE WHERE JOURNAL_TYPE_CODE IN (SELECT JOURNAL_TYPE_CODE FROM FAS_JOURNAL_MASTER "+
		    " WHERE JOURNAL_STATUS   ='L' AND accounting_unit_id = a.accounting_unit_id  AND cashbook_month     = a.cashbook_month  AND cashbook_year      = a.cashbook_year    AND voucher_no         = a.voucher_no "+
		    ")  ) AS code   FROM PMS_DCB_OTHER_CHARGES a WHERE a.office_id   ="+Office_id+" AND a.CASHBOOK_MONTH="+smonth+" AND a.CASHBOOK_YEAR ="+syear+" order by VOUCHER_NO,BENEFICIARY_NAME";
 		    
 		System.out.println("qry1 is "+qry1);  
 		
 		table_column = "VOUCHER_NO,VOUCHER_DATE,BENEFICIARY_NAME,account_head_code,ACCOUNT_HEAD_DESC,CR_DR_INDICATOR,PARTICULARS,amount,code";
 		table_header = "Voucher No,Voucher Date, Beneficiary Name,Account Head Code,Account Head Description,CR/DR,Particulars,Amount,Type";
 		  table_td_set = ",,align=left width='25%',align=center width='5%',align=left width='25%',align=center width='2%',align=left width='15%',align=center width='5%',align=left width='15%'";
 		table_heading = " Journal Adjustments - "+ Office_Name + "";
 		  tab = "cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
 		html+= obj.genReport(qry1, table_header, table_column, tab,table_heading, table_td_set);


 		
 		con.close();    
 		

 	} catch (Exception e) {
 		out.println(e);
 		//x	response.sendRedirect(request.getContextPath()+"/index.jsp");

 	}
 	
 %> 
 
		<body onload="blink()"> 
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table class="fb2"    id="" width=95% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan=2 class=fnt><b><center><font size='3'><%=table_heading%></font></center></b></td>
				</tr> 
				 
				<tr>
					<td colspan=1 class="tdText"><b>Month and Year : <%=obj.month_val(smonth)%>-<%=syear%></b></td>
					
					
				<td align="right"> <button class="sa" onclick="Delete_Demand()" >Delete All Journal Adjustments For <%=obj.month_val(smonth)%>-<%=syear%> </button></td>
					
					
				</tr>
				    
				<Tr> 
					<td colspan=2><%=html%></td>  
				</Tr>
				<tr>
					<td colspan=2 align="center"> <input class="fb2" style="font-style: italic;font-size:7;color:red;font-weight: bold"  type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
			 	
		</Table>  
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 
       	   <input type="hidden" id="Office_id" name="Office_id" value="<%=Office_id%>" ></input> 
       	  
</body>

 
</html>