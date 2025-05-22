<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB Data Freeze</title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/Office_Shift_new.js">
<script type="text/javascript">

function view()
{
	  var month=document.getElementById("month").value;
      var year=document.getElementById("year").value;
	  var v=window.open("../reports/jsps/actual_data_verificaiton.jsp?year="+year+"&month="+month);
}
function rld4()
{
	var reporttype =1;
	var pyear=document.getElementById("finyear").value;	 
	var pmonth=document.getElementById("month").value;	 
	window.open("../../../../../Water_Charges_Report?pmonth="+pmonth+"&command=FIN_YEAR_REPORT&pyear="+pyear+"&option=1");	 
}
</script>
</head>
<body>
<%
	String userid="0",Office_id="",Office_Name="";
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Connection con=null;
	java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
	String pmonth="0",pyear="0";
	try
	{
	  con=obj.con();
	  obj.createStatement(con);
	  userid=(String)session.getAttribute("UserId");			
	  if(userid==null)
	  {
		 	response.sendRedirect(request.getContextPath()+"/index.jsp");
	  }
	}catch(Exception e)
	{
		out.println(e);  
	}
	
 // Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	
//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
	String yr=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR"," where OFFICE_ID="+Office_id),1);;//obj.setValue("year",request);
	String mt=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);;//obj.setValue("month",request);
	if (pmonth.equalsIgnoreCase("0"))
	{
		pmonth=mt;
		pyear=yr; 
	}
	int count_2=obj.getCount("PMS_DCB_DATA_FREEZE","where DCB_FREEZE='Y' and OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+pmonth+" and CASHBOOK_YEAR="+pyear);
	
	String sub=obj.setValue("store",request);
	String acc_unit_id=obj.getValue("PMS_DCB_COM_OFFICE","ACCOUNTING_UNIT_ID"," where office_id='"+Office_id+"'");
	if (Office_id.equals("")) Office_id="0";
	  if(sub.equalsIgnoreCase("Freeze DCB"))
		{
		   
		   if (count_2==0)
		   {
			String demand=obj.setValue("demand",request);
			String collecton=obj.setValue("collecton",request);
			Hashtable ht=new Hashtable();
			ht.put("OFFICE_ID",Office_id);
			ht.put("CASHBOOK_MONTH",pmonth);
			ht.put("CASHBOOK_YEAR",pyear);
			ht.put("ACCOUNTING_UNIT_ID",acc_unit_id);
			ht.put("ACCOUNTING_FOR_OFFICE_ID",Office_id);
			ht.put("TOTAL_DMD",demand);
			ht.put("DCB_FREEZE","'Y'");
			ht.put("TOTAL_COLL",collecton);
			ht.put("UPDATED_DATE","clock_timestamp()");  
			ht.put("UPDATED_BY_USER_ID","'"+userid+"'");
			int r=obj.recordSave(ht,"PMS_DCB_DATA_FREEZE",con); 
			if (r > 0)
			{
				count_2=r;
				sub="";
				%>
				<script> 
					alert (" Freezed Successfully");
					window.close();
				</script>		
				<%
			  }
		   }
		   else 
			{
				%>
				<script>
					alert (" DCB Data Aleady Freezed ");
					window.close();
				</script>		
				<%				
			}
		}	  
	String journal=obj.getValue("FAS_HEAD_SLTYPE_DR_VIEW"," SUM(amount1)","journal","where  achcode IN   (SELECT ACCOUNT_HEAD_CODE  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  WHERE account_head_code BETWEEN 780401 AND 780407  ) and CASHBOOK_MONTH="+pmonth+" and CASHBOOK_YEAR="+pyear+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and sltypecode = 10");  
	String posted_total_deamand=obj.getValue("PMS_DCB_LEDGER_ACTUAL"," SUM(DMD_FOR_MTH_WC)","demand","where month="+pmonth+" and year="+pyear+" and office_id="+Office_id);	
	String posted_total_collection=obj.getValue("PMS_DCB_LEDGER_ACTUAL"," sum(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC)","collection"," where month="+pmonth+" and year="+pyear+" and office_id="+Office_id);
	String actual_total_deamand=obj.getValue("PMS_DCB_WC_BILLING "," SUM(total_amt)","actualdemand","where month="+pmonth+" and year="+pyear+" and office_id="+Office_id);
 	String dr_coll=obj.getValue("FAS_HEAD_SLTYPE_DR_VIEW"," SUM(amount1)","journal","where achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where COLLECTION_TYPE in (6,7) and acc_type='C' ) and CASHBOOK_MONTH="+pmonth+" and CASHBOOK_YEAR="+pyear+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id);	
	String cr_coll=obj.getValue("FAS_HEAD_SLTYPE_CR_VIEW"," SUM(amount1)","journal","where achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where COLLECTION_TYPE in (6,7) and acc_type='C' ) and CASHBOOK_MONTH="+pmonth+" and CASHBOOK_YEAR="+pyear+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id);
 	
	//for tda ajustment done checking 780406 for credit = 

	//String cr_coll=obj.getValue("FAS_HEAD_SLTYPE_CR_VIEW"," SUM(amount1)","journal","where achcode IN   ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  where COLLECTION_TYPE in (6,7) or (account_head_code = 780406 and acc_type='D')  ) and CASHBOOK_MONTH="+pmonth+" and CASHBOOK_YEAR="+pyear+" and ACCOUNTING_FOR_OFFICE_ID="+Office_id);


			
			
   	String bill_col=obj.getData(pmonth,pyear,Office_id,"coln_cur_yr_wc","coln_cur_yr_wc");
   	String bill_dmd=obj.getData(pmonth,pyear,Office_id,"WC_MTH_TOTAL","WC_MTH_TOTAL");
   	String bill_maint_ob=obj.getData(pmonth,pyear,Office_id,"ob_maint_charges","ob_maint_charges");
   	String bill_maint_col=obj.getData(pmonth,pyear,Office_id,"coln_maint","coln_maint");
   	String bill_int_out=obj.getData(pmonth,pyear,Office_id,"ob_int_amt_wc","ob_int_amt_wc");
   	String bill_int_col=obj.getData(pmonth,pyear,Office_id,"COLN_INT_WC","COLN_INT_WC");
  	String bill_int_dmd=obj.getData(pmonth,pyear,Office_id,"DMD_INT_FOR_MTH_WC");
  	String Cr_Advice_fas=obj.getValue("PMS_DCB_FAS_CREDIT_ADVISE","FAS"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+pmonth+" and CASHBOOK_YEAR="+pyear).trim();
	int Cr_Advice_fas1=Integer.parseInt(Cr_Advice_fas);	 
	String Cr_Advice_dcb=obj.getValue("PMS_DCB_FAS_CREDIT_ADVISE","DCB"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+pmonth+" and CASHBOOK_YEAR="+pyear).trim();
	int Cr_Advice_dcb1=Integer.parseInt(Cr_Advice_dcb);	 
   	
   	double bill_coll=Double.parseDouble(bill_col);
   	double bill_dmdd=Double.parseDouble(bill_dmd);
   	double bill_maint_ob_coll=Double.parseDouble(bill_maint_ob)+Double.parseDouble(bill_maint_col);
	double bill_int_out_coll=Double.parseDouble(bill_int_out)+Double.parseDouble(bill_int_col);
   	double bill_int_dmdd=Double.parseDouble(bill_int_dmd);
   	
   	double differ3=( Double.parseDouble(Cr_Advice_fas)-Double.parseDouble(Cr_Advice_dcb) );
   	
    

    String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
	int count_freeze=obj.getCount("PMS_DCB_LEDGER_ACTUAL","where month="+pmonth+" and year="+pyear+" and office_id="+Office_id);
	int count_freeze_=obj.getCount("PMS_DCB_DATA_FREEZE","where CASHBOOK_MONTH="+pmonth+" and CASHBOOK_YEAR="+pyear+" and office_id="+Office_id);
	double differ1=Double.parseDouble(posted_total_deamand)-Double.parseDouble(journal);
	System.out.println("the demand is 1 "+journal); 
	System.out.println("the demand is 2 "+posted_total_deamand); 
	
	double differ2=( Double.parseDouble(cr_coll)-Double.parseDouble(dr_coll) )-Double.parseDouble(posted_total_collection);
	System.out.println("the coll1 is"+Double.parseDouble(cr_coll)); 
	System.out.println("the coll2 is"+Double.parseDouble(dr_coll)); 
	System.out.println("the  coll3 is"+Double.parseDouble(posted_total_collection)); 
	
	System.out.println("the differ1 is"+differ1); 
	System.out.println("the differ2 is"+differ2); 
	System.out.println("the bill_coll is"+bill_coll); 
%> 
<form action="Division_DCB_Freeeze.jsp" method="get" name="freeze" id="myForm">
<table   align="center" border=1 width="70%"  bordercolor="#00FFFF" cellpadding="5" style="border-collapse: collapse" cellspacing="0" > 
	<tr bgcolor="#11297C" style="color: white;"> 
		<td colspan="2" align="center">  
			DCB Data Freeze
		</td>
		</tr> 
		<tr>
		<td colspan="2" align="center">
			<%=Office_Name%> (<%= acc_unit_id%>)
		</td>
		</tr>
		<tr>
  		<td align="left"> Month and Year   </td><td> <%=amonth[Integer.parseInt(pmonth)]%>   <%=pyear%> 
  		<input type="hidden" id="year" name="year"  value="<%=pyear%>">
  		   <input type="hidden" name="month" id="month" value="<%=pmonth%>">  		
  		   <input type="hidden" name="Office_id" id="Office_id" value="<%=Office_id%>">  	
	     
        </td>
    </tr> 
   <tr>
  
    	<td> Status : </td>
    	<td align="left">
    		<% 
    			if (count_2==0)
    				out.println("<font color='red'>DCB Data Not Yet Freezed</font>");
    			else
    				out.println("<font color='green'>DCB Data Freezed</font>");
    		%>
    	</td>
    </tr>
    <tr>
    	<td>
    		Difference in Demand 
    	</td>
    	<td>
    		<%=df.format(differ1)%> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Check Beneficiary"  onclick="rldcheck_dmd_current_month()" />
    	</td>
    </tr>
    <tr>
    	<td>
    		Difference in Collection 
    	</td>
    	<td>
    		<%=df.format(differ2)%> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Check Beneficiary"  onclick="rldcheck_coll_current_month()" />
    	</td>
    </tr>
    
     <tr>
    	<td>
    		Difference in Collection for present month (bill)
    	</td>
    	<td>
    		<%=df.format(bill_coll)%>  &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Check Beneficiary"  onclick="rldcheck_coll()" />
    	</td>
    </tr>
     <tr>
      	<td>
    		Difference in Demand for present month (bill)
    	</td>
    	<td>
    		<%=df.format(bill_dmdd)%> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Check Beneficiary"  onclick="rldcheck_dmd()" />
    	</td>
    </tr>
     <tr>
    	<td>
    		Difference in Maint Ob & Coll for present month (bill)
    	</td>
    	<td>
    		<%=df.format(bill_maint_ob_coll)%> &nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Check Beneficiary"  onclick="rldcheck_main_ob_coll()" />
    	</td>
    </tr>
     <tr>
    	<td>
    		Difference in interest Outstanding & Coll for present month (bill)
    	</td>
    	<td>
    		<%=df.format(bill_int_out_coll)%>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Check Beneficiary"  onclick="rldcheck_int_out_coll()" />
    	</td>
    </tr>
     <tr>
    	<td>
    		Difference in interest Demand for present month (bill)
    	</td>
    	<td>
    		<%=df.format(bill_int_dmdd)%>&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Check Beneficiary"  onclick="rldcheck_int_dmd()" />
    	</td>
    </tr>   
     <tr>
    	<td>
    		Difference in Credit Advise for present month (bill)
    	</td>
    	<td>
    		<%=df.format(differ3)%> &nbsp;&nbsp;&nbsp;&nbsp;  Credit Advise  by HO (FAS) is : <%=df.format(Cr_Advice_fas1)%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Credit Advise Adjusted by DCB is : <%=df.format(Cr_Advice_dcb1)%> 
    	</td>
    </tr> 
    <tr>
    	<td>&nbsp;</td>     	
    	<td align="center">
    		<input type="button" value=" View Report for Financial Year "  style="color: green;font-weight: bold"  onclick="rld4()">
    		&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:view()"><font size='3' style="color: green;font-weight: bold" > View Demand and Collection Monthly Transaction - CheckList</font></a>
    	</td>
    </tr> 
    <tr> 
    <td>&nbsp;</td>  
    <td align="center">
    	<%
    	String disable="";
 //   	if (count_freeze!=0 && count_freeze_==0 && differ1==0 && differ2==0  ) 
	    if (count_freeze!=0 && count_freeze_==0 && differ1==0 && differ2==0 && bill_coll==0 && bill_dmdd==0 && bill_maint_ob_coll==0 && bill_int_out_coll==0 && bill_int_dmdd==0 && differ3==0 ) 
    	{
    		disable="";
    	}else
    	{
    		disable="disabled='disabled'";
    	}
     
    	%><input type="submit" name="store" id="store" value="Freeze DCB" <%=disable%>>
        	&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="fb2" value="Exit" onclick="javascript:window.close();"> 
        	
        
    </td>
    </tr>    
    </table>  
    <%
    	int fin_year=0;
    	if (month<=3)
    		fin_year=year-1;
    	else
    		fin_year=year;
    %>
    <input type="hidden" id="finyear" value="<%=fin_year%>"> 
     <input type="hidden" id="count" value="<%=count_2%>"> 
     <input type="hidden" id="unit" value="<%=acc_unit_id%>"> 
     <input type="hidden" id="id" value="<%=userid%>"> 
     
     
</form>
</body>
</html>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br><p><center><font color="red">Note:</font>Difference in Demand and Difference in Collection should be Zero only then DCB can be Freezed<a href="javascript:rld()">.</a></center></p>
