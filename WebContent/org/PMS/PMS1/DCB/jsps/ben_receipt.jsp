

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>	
<html> 
<title> Beneficiary Receipt CheckList | TWAD Nest - Phase II</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
<script type="text/javascript">
	function rld()
	{
		  var v=document.getElementById('ben_sel').value;
		  document.forms["receipt"].submit();
		  document.getElementById('ben').value=v;
	}
	function callReport(pro)
	{
		  var option=document.getElementById('pr_type').value;
		  var fyear=document.getElementById('fyear').value;
		  var fmonth=document.getElementById('fmonth').value;
		  var ben_sel=document.getElementById('ben').value;
		  window.open("../../../../../Bill_Demand?command=recpdf&fmonth="+fmonth+"&fyear="+fyear+"&ben_sel="+ben_sel+"&option="+option+"&pro="+pro);
	}
	function troubleshoot()
	{  
		  var fyear=document.getElementById('fyear').value;
		  var fmonth=document.getElementById('fmonth').value;		   
		   //window.open("../reports/jsps/Receipt_Troubleshoot_1.jsp?syear="+fyear+"&smonth="+fmonth);
		   window.open(" ../../../../../Receipt_Trouble_shoot?syear="+fyear+"&smonth="+fmonth,"Receipt Verification","height=900,width=1400");
		  
	}    
	function troubleshoot1()
	{  
		  var fyear=document.getElementById('fyear').value;
		  var fmonth=document.getElementById('fmonth').value;		   
		  s=window.open("../reports/jsps/receipt_demand.jsp?syear="+fyear+"&smonth="+fmonth,"","","");  
	}  
</script>
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>  
</head>    
<%
		int month = 0;//cal.get(Calendar.MONTH) + 1;
		int year = 0;//cal.get(Calendar.YEAR);
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="",OFFICE_LEVEL_ID="0";;
		Connection con;
		String smonth="",syear="",html="",SUB_LEDGER_CODE="";
		Controller obj=null;
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		  month = cal.get(Calendar.MONTH) + 1;
		  year = cal.get(Calendar.YEAR);
		  int prv_month=0;
			if (month==1) prv_month=12;
			else
			prv_month=month-1;   
			int prv_year=year;
			if (prv_month==12)  prv_year=year-1;
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
		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");		 
		smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		SUB_LEDGER_CODE=obj.setValue("ben_sel",request);
		String qry="select "+
			" tr. AMOUNT, "+
		    " tr.RECEIPT_NO, "+
		    " ben.BENEFICIARY_NAME, "+
		    " BEN_TYPE_DESC, "+
		    " sch.SCH_NAME, "+
		    " amap.RECEIPT_TRN_DESC, "+
		    " to_char(ma.RECEIPT_DATE , 'DD/MM/YYYY') as RECEIPT_DATE,ma.RECEIPT_STATUS "+
		    " from  "+
			" ( select  "+
			" 		ACCOUNTING_UNIT_ID, "+
		      " 	ACCOUNTING_FOR_OFFICE_ID, "+
		      " 	RECEIPT_DATE, "+
		      " 	CASHBOOK_YEAR, "+
		      " 	CASHBOOK_MONTH, "+
		      " 	RECEIPT_NO, "+
		      " 	SUB_LEDGER_TYPE_CODE, "+ 
		      " 	SUB_LEDGER_CODE,RECEIPT_STATUS "+
		      " from  "+
			  " 	FAS_RECEIPT_MASTER "+   
		      " where SUB_LEDGER_TYPE_CODE = 14 "+
			  " and SUB_LEDGER_CODE=  "+SUB_LEDGER_CODE+
			  " and CASHBOOK_YEAR= "+syear+
			  " and CASHBOOK_MONTH="+smonth+
			" order by RECEIPT_NO"+
			" )ma "+
			" join  "+
		" ( "+
				" select "+ 
		" ACCOUNTING_UNIT_ID, "+
		      " ACCOUNTING_FOR_OFFICE_ID, "+
		      " CASHBOOK_YEAR, "+
		      " CASHBOOK_MONTH, "+
		      " RECEIPT_NO, "+
		      " SUB_LEDGER_TYPE_CODE, "+
		      " SUB_LEDGER_CODE, "+
		      " AMOUNT, "+
		      " ACCOUNT_HEAD_CODE "+
		      " from "+
		" FAS_RECEIPT_TRANSACTION "+
		      " order by RECEIPT_NO  )tr "+
		" ON tr.ACCOUNTING_UNIT_ID=ma.ACCOUNTING_UNIT_ID "+ 
		" and  "+
		   " tr.ACCOUNTING_FOR_OFFICE_ID=ma.ACCOUNTING_FOR_OFFICE_ID "+
		   " and  "+
		   " tr.CASHBOOK_YEAR=ma.CASHBOOK_YEAR "+
		   " and  "+
		   " tr.CASHBOOK_MONTH=ma.CASHBOOK_MONTH "+
		   " and  "+
		   " tr.RECEIPT_NO=ma.RECEIPT_NO "+
		   "  join " + 
		    " ( " +
		    "  select " + 
		    "  	PROJECT_ID, " +
		    "  	SCH_SNO " +
		   "   from PMS_MST_PROJECTS_VIEW " +
		   "  "  +		     
		      "   ) v "+ 
		       " on v.PROJECT_ID=tr.SUB_LEDGER_CODE "+		   		   
		   " join "+
		" (   select "+ 
				" SCH_NAME, "+
		        " SCH_SNO "+
		        " from "+
		   " PMS_SCH_MASTER  "+
		        " )sch "+
		" on sch.SCH_SNO=v.SCH_SNO "+
		" join  "+
		" (  "+
			  " select "+ 
		      " 	BENEFICIARY_NAME, "+
		      " 	BENEFICIARY_SNO, "+
		      " 	BEN_TYPE_DESC "+
		      " from  "+
		    " BEN_ "+
		    " )ben "+
		"  on ben.BENEFICIARY_SNO=ma.SUB_LEDGER_CODE "+
		" join "+
		" ( "+
				" select "+
		     " ACCOUNT_HEAD_CODE, "+ 
		           " RECEIPT_TRN_DESC "+
		           " from "+
		      " PMS_DCB_RECEIPT_ACCOUNT_MAP "+
		           " )amap "+
		" on amap.ACCOUNT_HEAD_CODE=tr.ACCOUNT_HEAD_CODE";
	 
	 
		
	   
		
		
		table_column="BENEFICIARY_NAME,BEN_TYPE_DESC,SCH_NAME,RECEIPT_TRN_DESC,AMOUNT,RECEIPT_NO,RECEIPT_DATE,RECEIPT_STATUS";
		table_header="Beneficiary,Beneficiary Type,Scheme Type,Receipt Description,Amount,Receipt<Br> No,Receipt<Br> Date,Receipt<Br> Status";
		String table_td_set="width='25%',width='10%',align=left width='25%',align=left width='25%',align=right width='5%',align=center width='5%',align=center width='5%',align=center width='5%' ";
		table_heading="<font color='darkgreen' size='4' ><b> Beneficiary Receipt CheckList</b> </font> ";
		String tab="cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
		html=obj.genReport(qry,table_header,table_column,tab,table_heading,table_td_set);
		con.close();  
		}catch (Exception e) {out.println(e);}
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		
		OFFICE_LEVEL_ID=obj.getValue("com_mst_all_offices_view", "OFFICE_LEVEL_ID","where OFFICE_ID="+Office_id+ "  ");
		String division_list="";
		try
		{
		division_list=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where   region_office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","div","class='select' onchange=select_ben('0','subdiv','5')");
		 
		}catch(Exception e) 
		{
		out.println(e);
		}			
%>   
		<title><%=table_heading%></title>
		<body onload="select_ben('0','bentype','3'),select_ben('0','subdiv','5'),flash()">
		<form name="receipt" action="ben_receipt.jsp">
	
	
		<Table      id="" width="75%" align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" >
		<label name="msg" id="msg"></label>
		<tr class="tdH">
			<td colspan="2" align="center">
				<%=Office_Name%>
			</td>
		</tr>
		
		<tr><a href="./knowyourben.jsp">.</a>
					<td colspan=2><center><%=table_heading%></center></td>
				</tr>  
					<tr>  
					<td class="tdText"> &nbsp;&nbsp;Year&nbsp;&nbsp;</td><td> 
					<select class="select"  id="fyear" name="fyear"  onchange="report_period_verify(document.getElementById('fmonth'),this)">
			   			<option value="0">-Select Year-</option>
			  			<%
	  					  for (int i=year-4;i<=year;i++)
						 {
			    			if (prv_year!=i) {
			   			%>
			  			<option value="<%=i%>"><%=i%></option>
			  				<%}else{%> 
			  			<option value="<%=i%>" selected><%=i%></option>
					  <%} %>
				  <%} %> 
			 	 </select>&nbsp;&nbsp;Month &nbsp;&nbsp; <select class="select"  id="fmonth" name="fmonth" onchange="report_period_verify(this,document.getElementById('fyear'))" >
					<option value="0">-Select Month-</option>
			  		<%
			  		for (int j=1;j<=12;j++)
			  		{ 
			  		   if (prv_month!=j) { 
			   		%>
			 	   <option value="<%=j%>"><%=monthArr[j]%></option>
			   		 <%} else { %>
			  	  <option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
			   		 <%} %>
			  <%} %>  
			 </select> 
					 
					     </td>
				</tr> 
				 <%
					if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
					{
					%> 
					<tr>
				 	 <td  ><font class="tdText">  Division</font> </td>
				 	 <td>
				 	 <%=division_list%>
				 	</td>
				 	</tr>
				 	<%}%>  
		<tr><td align=left valign="top" width=10% style="width: 382px"><font class="tdText">Sub Division</font> </td>
		<td width=5% align=left  >
				<select class='select'  id="subdiv" name="subdiv" onchange="data_show('show',15,0)" ></select></td>
		</tr>
        <tr class="">
		<td width=25%><font class="tdText"> Beneficiary Type</font> </td>
		<td   width=75%  align=left  > <select  id="bentype" name="bentype" onchange="select_ben(this.value,'ben_sel',4),data_show('show',15,0)" class="select">  </select></td></tr>
		<Tr><td width=10% ><font class="tdText">Beneficiary Name </td><td  ><select id="ben_sel"  name="ben_sel" onchange="rld()"  class="select"  >
		<option value="0">--Select Beneficiary-</option> 
		</select>&nbsp;&nbsp; &nbsp;&nbsp; <input class="fb2" type="button" id="" value="Print" onclick="callReport(1)" style="font-style: italic;color: blue"  /></td></Tr>
    	<Tr><td colspan=2><%=html%></td></Tr>
		
		<tr>
		<td colspan=2 align="center">		 
		 	 <input type="button" id="" value=" Water Charges  " style="font-style: italic;font-size:7;color:blue" onclick="callReport(3)">		 	 
		 	 &nbsp;<input type="button" id="" value=" Interest  " style="font-style: italic;font-size:7;color:blue" onclick="callReport(4)">
		 	&nbsp; <input type="button" id="" value=" Maintenance Arrears " style="font-style: italic;font-size:7;color:blue" onclick="callReport(5)">		 
		 	&nbsp; <input type="button" id="" value="Print All " style="font-style: italic;font-size:7;color:blue" onclick="callReport(2)">    
		 	&nbsp;<input type="button" id="" value="Exit" onclick="javascript:window.close()"  style="font-style: italic;color: red"/> 
		 </td>
		 </tr>
		<!--   
		  <tr>			 
			<td align="center" colspan="2">
				<input type="button" id="" value="Receipt Difference" style="font-style: italic;font-size:9;font-weight:bold;color :darkblue" onclick="troubleshoot1()">
				&nbsp;&nbsp;&nbsp;<input type="button" id="" value="Receipt Correction" style="font-style: italic;font-size:9;font-weight:bold;color :darkred" onclick="troubleshoot()">
			</td>
		</tr>
		  -->
		<tr>
		<td>
			
		</td>
		</tr>
		 <input type=hidden  id="pr_type" value=1>
		 		   
		</Table>
		<input type=hidden id="pr_status" name="pr_status" value="0">
		<input type=hidden id="ben" name="ben" value="<%=SUB_LEDGER_CODE%>">
		 
        	</form>  
</body>
</html>