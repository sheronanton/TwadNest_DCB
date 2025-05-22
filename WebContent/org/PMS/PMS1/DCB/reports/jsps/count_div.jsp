<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
 <%@ page isThreadSafe="true"%>
<link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
 <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<jsp:useBean id="bean" class="Servlets.PMS.PMS1.DCB.reports.count_bean" scope="page"></jsp:useBean>
<title>Statistical Report </title>
<script >
	function rld()
	{
		window.open("../../../../../../count_report_serv?command=pdf")
	}   
</script>
<script type="text/javascript" src="../../../../../../jquery-3.6.0.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
var i=0,j=0,k=0,l=0,m=0,n=0;
 $(document).ready(function(){
	$("#panel").slideUp();
	});
 
$(document).ready(function(){
	$("#panel2").slideUp();
});
$(document).ready(function(){
	$("#panel3").slideUp();  
});
$(document).ready(function(){
	$("#panel4").slideUp();
});
$(document).ready(function(){
	$("#panel5").slideUp();
});
$(document).ready(function(){
	  $("#flip").click(function(){
	    $("#panel").slideToggle("slow");
		i++;
		if (i%2==1)
	    $("#img1").attr("src","../../../../../../images/minus.jpg");
		else
		$("#img1").attr("src","../../../../../../images/plus.png");
	  });
	});
$(document).ready(function(){
	  $("#flip2").click(function(){
	    $("#panel2").slideToggle("slow");
		j++;
		if (j%2==1)
	    $("#img2").attr("src","../../../../../../images/minus.jpg");
		else
		$("#img2").attr("src","../../../../../../images/plus.png");
	  });
	});
$(document).ready(function(){
	  $("#flip3").click(function(){
	    $("#panel3").slideToggle("slow");
		k++;
		if (k%2==1)
	    $("#img3").attr("src","../../../../../../images/minus.jpg");
		else
		$("#img3").attr("src","../../../../../../images/plus.png");
	  });
	});
$(document).ready(function(){
	  $("#flip4").click(function(){
	    $("#panel4").slideToggle("slow");
		l++;
		if (l%2==1)
	    $("#img4").attr("src","../../../../../../images/minus.jpg");
		else
		$("#img4").attr("src","../../../../../../images/plus.png");
	  });
	});
$(document).ready(function(){
	  $("#flip5").click(function(){
	    $("#panel5").slideToggle("slow");
		m++;
		if (m%2==1)
	    $("#img5").attr("src","../../../../../../images/minus.jpg");
		else
		$("#img5").attr("src","../../../../../../images/plus.png");
	  });
	});

</script>
</head>
<body>
<%
String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
Connection con=null;
String smonth="",syear="",html="",oid="",JOURNAL_POSTED="",msg="";
Controller obj=null;
try
{
  obj=new  Controller();
  con=obj.con();
  obj.createStatement(con);  
  try
	{
	   userid=(String)session.getAttribute("UserId");
	}catch(Exception e) {userid="0";}
	if(userid==null)
	{ 
		 response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	//Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	
	oid=obj.setValue("oid",request);
 	if (!oid.trim().equalsIgnoreCase("0"))
 	Office_id=oid;
 	
	smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
	syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
	String flag="0";
	try
	{
		flag=session.getAttribute("dcbflag").toString();
	} catch(Exception e) 
	{
	  	flag="0";
	}
	 if (flag.equalsIgnoreCase("")) flag="0"; 
	 if (flag.equalsIgnoreCase("1"))
	 {
	   	String dcbmonth=session.getAttribute("dcbmonth").toString();
	  	String dcbyear=session.getAttribute("dcbyear").toString();
	  	bean.setSet_month(dcbmonth);
	  	bean.setSet_year(dcbyear);
		smonth=dcbmonth;
	  	syear=dcbyear;
	  }
	  else
	  {
	      smonth=smonth;
	  	  syear=syear;
	  }
	if (Office_id.equals("")) Office_id="0";
	bean.setOffid(Integer.parseInt(Office_id));
	
 		try 
		    {
				JOURNAL_POSTED = obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS", "JOURNAL_POSTED","where OFFICE_ID=" + Office_id
						+ " and CASHBOOK_YEAR=" + syear
						+ " and  CASHBOOK_MONTH=" + smonth), 1);
		    } catch (NullPointerException e1) {
				JOURNAL_POSTED = "0";
			}
			 if ( (JOURNAL_POSTED.trim().equalsIgnoreCase("Y") || JOURNAL_POSTED.trim().equalsIgnoreCase("y"))) 
			  {
				msg = " DCB Journal Posted !";   				 
			  }else
			  {
				msg = " DCB Journal yet to be  Posted ! ";
			  }
 

}catch(Exception e) {userid="0";}
%>
<table border="1" width="85%" align="center" cellpadding="5" cellspacing="0" class="table"> 
	<tr  bgcolor="#408080"><th colspan="2" align="center"><b><font color="black">Statistical Report</font></b></th></tr>
	<tr><td  width="20%" > Office Name :</td><td  width="80%" ><b><jsp:getProperty name="bean" property="process"/></b>   </td></tr>
	<tr>
	<td colspan="2" width="40%">
	<div id="flip" > <img id="img1" src="../../../../../../images/plus.png" width="12"  height="12"> <b><font color="black">Master</font></div>
	<div id="panel">      
	<table border="1" width="100%" cellpadding="0" cellspacing="0" class="table">   
	<tr>	
	 <td width="50%"> Beneficiary </td><td >	
		<table border="0" width="100%" align="left" cellpadding="0" cellspacing="0"    >			
			<tr><td > Local Body  </td><td    align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=2"><jsp:getProperty name="bean" property="local"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
			<tr><td > Private Beneficiary  </td><td width="50%"   align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=3"><jsp:getProperty name="bean" property="prvben"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
			<tr  bgcolor='#D0D0D0'><td ><font color="black"><b> Total Beneficiary</b></font>  </td><td width="50%"  align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=1"><jsp:getProperty name="bean" property="totben"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
			</table> 
		</td>
	</tr> 
	<tr><td  width="20%" > Meter </td><td >
			<table border="1" width="100%" align="left" cellpadding="0" cellspacing="0"    >		
				<tr>
					<td width="50%" >Tariff   Setting  -   Location Based </td><td width="50%" align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=6"><jsp:getProperty name="bean" property="locationmeter"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td width="50%" >Tariff   Setting - Scheme Based</td><td width="50%"  align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=7"><jsp:getProperty name="bean" property="schmeter"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td width="50%" >Tariff   Setting Not Set</td><td width="50%"  align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=9"><jsp:getProperty name="bean" property="total_meter_net_set"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
					<tr  bgcolor='#D0D0D0'><td width="50%" ><font color="black"><b>Total</b></font></td><td width="50%" align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=5"><jsp:getProperty name="bean" property="metercount"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
			</table>
	</td></tr>
	<tr><td width="20%" > Tariff Setting </td><td>
	<table border="1" width="100%" align="left" cellpadding="0" cellspacing="0" class="table"  >
				<tr>
					<td width="50%" >Created Status </td><td width="50%" align="right"  ><a href="common_ben.jsp?oid=<%=oid%>&fun=14"><jsp:getProperty name="bean" property="tariffcr"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td width="50%" >Freeze Status </td><td width="50%" align="right"  ><a href="common_ben.jsp?oid=<%=oid%>&fun=15"><jsp:getProperty name="bean" property="tariffset"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td width="50%" >Tariff Not Set </td><td width="50%" align="right"  ><a href="common_ben.jsp?oid=<%=oid%>&fun=9"><jsp:getProperty name="bean" property="tariffsetnotfr"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
	</table>
	</td>
	</tr>
	<tr><td width="20%" > Rate Entered <font color='blue' size='2'><b>[Locations]</b></font></td><td  align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=10"><jsp:getProperty name="bean" property="rateenter"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
	<tr><td  > Rate Not Entered<font color='blue' size='2'><b>[Locations]</b></font></td><td  align="right" ><a href="common_ben.jsp?oid=<%=oid%>&fun=11"><jsp:getProperty name="bean" property="ratenotenter"/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
	</table>
	</div>
	</td>
	</tr>
	<tr>  
	<td colspan="2" width="30%"><div id="flip2" > <img id="img2" src="../../../../../../images/plus.png" width="12"  height="12"> <b><font color="black">Monthly Transaction: <jsp:getProperty name="bean" property="monthval"/> <jsp:getProperty name="bean" property="year"/> </font></b>    </div>
	<div id="panel2">
	<table border="1" width="100%" cellpadding="0" cellspacing="0" class="table">	
			<tr><td > Pumping Return  </td><td >
			<table border="1" width="100%" align="left" cellpadding="0" cellspacing="0" class="table">
						<tr> <td colspan="3" align="left"><font size="3"> Meter Locations </font> </td></tr>
						<tr>
							<td width="50%" >Created Status </td><td width="50%" align="right"   ><jsp:getProperty name="bean" property="cr_rec"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
							<tr>
							<td width="50%" >Validated Status </td><td width="50%" align="right"  ><jsp:getProperty name="bean" property="val_rec"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
							<tr>
								<td width="50%" >Freeze Status </td><td width="50%" align="right" ><jsp:getProperty name="bean" property="fr_rec"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
								
						<tr  bgcolor='#D0D0D0'>
							<td width="50%" ><font color="black"><b>Total</b> </td><td width="50%" align="right"  ><jsp:getProperty name="bean" property="pr_rec"/>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
			</table>
			</td></tr>
		<tr>
				<td width="50%" ><font  size='2'><b>Beneficiary</b></font> Pumping Return Omission </td><td width="50%" align="right" ><a href="../../jsps/data_not_submit_ben.jsp?oid=<%=oid%>"><jsp:getProperty name="bean" property="pmissing"/></a>&nbsp;&nbsp;&nbsp;&nbsp; </td>
		</tr>
		<tr>
			<td width="20%"  >Total Water Charges  (Demand)</td><td  align="right"  ><a href="../../jsps/pr_amount.jsp??pyear=<%=syear%>&oid=<%=oid%>&pmonth=<%=smonth%>"><jsp:getProperty name="bean" property="wc2"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
		<tr><td width="20%"   >Water Charge Freeze Status   </td><td  align="right"  ><jsp:getProperty name="bean" property="wcstatus"/></td></tr>
		<tr><td width="20%"   >DCB Journal Posted Status  </td><td  align="right"  >&nbsp;<%=msg%></td></tr>

		</table></div></td></tr>
	<tr>
	<td colspan="2" width="20%"><div id="flip3" > <img id="img3" src="../../../../../../images/plus.png" width="12"  height="12"> <b><font color="black">  Journal </font></b></div>
	<div id="panel3">
	<table border="0" width="100%">	
	<tr><td  width="40%"> DCB Journal Details</td><td >
	<table  width="100%" align="left" cellpadding="5" cellspacing="0"  border="1" class="table">
	<tr>
		<td  align="center"><b>Voucher No</b></td>
		<td  align="center"><b>Voucher Date</b></td>
		<td  align="center"><b>A/C Code</b></td>
		<td  align="center"><b>Amount</b></td>
		<td  align="center"><b>Status</b></td>
	</tr>
	<%
	Connection con1=obj.con();
	ResultSet rs_div=null;
	String qry="select JOURNAL_STATUS,VOUCHER_NO,to_char(VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE from FAS_JOURNAL_MASTER  where ACCOUNTING_UNIT_ID="+bean.getAcc_unit_id() +" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+smonth+" and  JOURNAL_TYPE_CODE =73 and CASHBOOK_YEAR="+syear;
	 
	PreparedStatement ps_div=con1.prepareStatement(qry);
	String amt="";
	String VOUCHER_NO="0",ACCOUNT_HEAD_CODE="";
	try
	{
	rs_div=ps_div.executeQuery();
	
	while (rs_div.next())
	{
		VOUCHER_NO=rs_div.getString("VOUCHER_NO");
		ACCOUNT_HEAD_CODE=obj.getValue("FAS_JOURNAL_TRANSACTION","ACCOUNT_HEAD_CODE" ,"where ACCOUNTING_UNIT_ID="+bean.getAcc_unit_id() +" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+smonth+" and  VOUCHER_NO ="+VOUCHER_NO+" and CR_DR_INDICATOR='CR' and  CASHBOOK_YEAR="+syear);
		amt=obj.getValue("FAS_JOURNAL_TRANSACTION","AMOUNT" ,"where ACCOUNTING_UNIT_ID="+bean.getAcc_unit_id() +" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+smonth+" and  VOUCHER_NO ="+VOUCHER_NO+" and CR_DR_INDICATOR='CR' and  CASHBOOK_YEAR="+syear);
	%>
	
	<Tr>
		<td   align="center"><%=rs_div.getString("VOUCHER_NO") %></td>
	    <td   align="center"><%=rs_div.getString("VOUCHER_DATE") %></td>
	    <td   align="center"><%=ACCOUNT_HEAD_CODE%></td>
	    <td   align="center"> <%=amt%></td>
		<td   align="center"> <%=rs_div.getString("JOURNAL_STATUS") %></td>
	</Tr>
	<%	
	}
	}catch(Exception e) {}
	%>
	</table>
	</td>
	</tr>
	
	</table></div></td></tr>
	<tr>
	<td colspan="2" width="30%"><div id="flip4" > <img id="img4" src="../../../../../../images/plus.png" width="12"  height="12"> <b><font color="black">Collection </font></b></div>
	<div id="panel4">
	<table border="1" width="100%" cellpadding="0" cellspacing="0" class="table">	
	<tr>
		<td width="40%">Collection  through DCB Receipt  </td> 
		<td >  
			<table width="100%" border="1" cellpadding="5"  cellspacing="0" class="table">
			<tr>
				<td align="center"><b>Account Head Code</b></td> 
				<td align="center"><b>Description</b></td>
				<td align="center"><b>Amount</b></td>
			</tr>
		<%		// Read Receipt from fas transaction if the accountcode must be in PMS_DCB_RECEIPT_ACCOUNT_MAP table
							 /* qry=" SELECT SUM(amount),account_head_code "
															+ " FROM FAS_RECEIPT_TRANSACTION "
															+ " WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id
															+ " AND cashbook_month="+smonth
															+ " AND cashbook_year="+syear
															+ " AND receipt_no IN " 
															  + " (  "
															  + " SELECT receipt_no "
															  + " FROM FAS_RECEIPT_MASTER "
															  + " WHERE SUB_LEDGER_TYPE_CODE  = 14 "
															  + " AND ACCOUNTING_FOR_OFFICE_ID="+Office_id
															  + " AND cashbook_month="+smonth
															  + " AND cashbook_year="+syear
															  + " AND RECEIPT_STATUS='L' "
															  + " ) and account_head_code in (select DISTINCT account_head_code from PMS_DCB_RECEIPT_ACCOUNT_MAP where ACTIVE_STATUS='L' and  COLLECTION_TYPE  in (7,9)  ) "
															  //+ " ) and account_head_code in (782401,782402 ,782406,782404,120601 ) "
															  + " GROUP by account_head_code ";
															  */
					qry=" SELECT SUM(a.amount),a.account_head_code FROM FAS_RECEIPT_TRANSACTION a WHERE a.ACCOUNTING_FOR_OFFICE_ID="+Office_id+" AND a.cashbook_month ="+smonth+
						" AND a.cashbook_year="+syear+" AND exists (SELECT receipt_no FROM FAS_RECEIPT_MASTER b WHERE b.SUB_LEDGER_TYPE_CODE=14 AND b.ACCOUNTING_FOR_OFFICE_ID="+Office_id+
						" AND b.cashbook_month="+smonth+"  AND b.cashbook_year="+syear+"  AND b.RECEIPT_STATUS='L'  and b.receipt_no=a.receipt_no  ) "+
						" AND exists  (SELECT DISTINCT c.account_head_code  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP c  WHERE c.ACTIVE_STATUS  ='L'  AND c.COLLECTION_TYPE IN (7,9) "+
						" and c.account_head_code=a.account_head_code ) GROUP BY a.account_head_code  ";				 			  
					ResultSet rs_col=obj.getRS(qry)	;					
					while (rs_col.next())
					{%>
						 <tr>
						 	<td  ><%=rs_col.getString(2)%></td>
						 	<td  ><%=obj.getValue("COM_MST_ACCOUNT_HEADS","ACCOUNT_HEAD_DESC","where ACCOUNT_HEAD_CODE="+rs_col.getString(2))%></td>
						 	<td   align="right"><%=rs_col.getString(1)%></td>					 	
						 </tr>
				  	<%
					}					
		 %>
		</table>
		</td>	
	</tr>
	<tr><td width="20%" > Total Receipt (Rs)</td><td  align="right"  ><jsp:getProperty name="bean" property="collection2"/>  &nbsp;</td></tr>
	<tr>
		<td>Other Journal Adjustment<br>(Including Interest collection)</td>
		<td align="right">Total Debit :&nbsp;&nbsp;&nbsp;(&nbsp;-&nbsp;)&nbsp;&nbsp;&nbsp;&nbsp; <jsp:getProperty name="bean" property="othercharges"/>&nbsp;&nbsp;</td>
	</tr>
	<tr><td>&nbsp;</td>
		<td align="right">Total Credit : &nbsp;(&nbsp;+&nbsp;)&nbsp;  <jsp:getProperty name="bean" property="otherchargescr"/>&nbsp;&nbsp;</td>
	</tr>
	
	<tr>
		<td>Net Collection (as per FAS Posting)</td>
		<td align="right"><jsp:getProperty name="bean" property="netcollection"/> &nbsp;</td>
	</tr>
	</table></div></td></tr>
	<tr>
	<td colspan="2" width="30%"><div id="flip5" > <img id="img5" src="../../../../../../images/plus.png" width="12"  height="12"> <b><font color="black">Verification </font></b></div>
	<div id="panel5">
	<table border="1" width="100%" cellpadding="0" cellspacing="0" class="table">	
	<tr> 
			<td width="20%" >Verification Status</td>
			<td width="20%"   align="right"><a href="../../jsps/Process_Status_Report.jsp?pyear=<%=syear%>&oid=<%=oid%>&pmonth=<%=smonth%>">Click</a>&nbsp;&nbsp;</td>
			
	</tr>
	
	<tr> 
				<td width="20%" > Total Demand Bill Generated  </td>
				<td  align="right" ><a href="../../jsps/Demand_Report_List.jsp?pyear=<%=syear%>&oid=<%=oid%>&pmonth=<%=smonth%>&pr_status=0"><jsp:getProperty name="bean" property="demandedbillcount"/></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>  
	<tr> 
				<td width="20%" > Demand Bill Omission  </td>
				<td  align="right" ><a href="./bill_not_generate.jsp">Click</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>  
	
 	<tr> 
				<td width="20%" > Ledger Data Omission </td>
				<td  align="right" ><a href="./ledger_not_upload.jsp">Click</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>  
	<Tr  >
 	<td colspan="2" align="right"><a href="'../../../../../../../../Bill_Demand?command=record_pdf&process_code=3&fmonth=<%=smonth%>&fyear=<%=syear%>"><b><font color=#972137 size='2' >DCB Statistics</font></b></a> 
 	 </td>
 	 </Tr>
 	 </table></div></td></tr>
 	 <tr><th  colspan="2"  align="center"><input type="button" class="fb2" value="Back" onclick="history.go(-1)"><input type="button" class="fb2" value="Close" onclick="window.close()" >
 	 </th></tr> 
</table>
 
</body>
</html>
 