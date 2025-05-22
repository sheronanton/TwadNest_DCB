<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.*,java.sql.*" %>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../css/txtbox.css" rel="stylesheet"  media="screen"/>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/Office_Shift_new.js"></script>

  <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript">
function checkAll(formname, checktoggle)
{
	var row_cnt=document.getElementById("row_cnt").value;
	for(var i=1;i<=row_cnt;i++)
	{
		document.getElementById("ck"+i).checked=true; 
	}
}
function disabl()
{
	document.getElementById("But1").disabled = true; 
	}

</script>
<title>Insert title here</title>
<%
/* 
	if  Journal Master --  JOURNAL_TYPE_CODE  56,57,53,75,87,66,54
			SUB_LEDGER_TYPE_CODE=14
*/

 String new_cond=Controller.new_cond;
 String amt="",billsno="",inp_month="",inp_year="",combo="",Bill_month="" ,Bill_year="";
 int row=0; 
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String userid="0",Office_id="",com_id="",Office_Name="";
			String Date_dis=day+"/"+month+"/"+year;
			   Controller obj=new Controller();
				Connection con;
				try
				{
				con=obj.con();
				obj.createStatement(con);
				userid=(String)session.getAttribute("UserId");			
				if(userid==null)
				{
				 	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				String yr=obj.setValue("year",request);
				String mt=obj.setValue("month",request);
				String ben=obj.setValue("ben",request);

				inp_month=mt;
				inp_year=yr;
				
			//	  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
	  Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

				
	//			Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			   	Bill_month=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
				Bill_year=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);

				int Cash_mon=Integer.parseInt(Bill_month);
				int Cash_ye=Integer.parseInt(Bill_year);
				System.out.println("Bill_month is"+Cash_mon);
				System.out.println("Bill_year is"+Cash_ye);

				
				
				String def_id=obj.setValue("def_id",request);
               if(def_id.equals("0"))
                    {
            	   def_id=Office_id;
                     }
				
				if (Office_id.equals("")) Office_id="0";
		 		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
				String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
				com_id=obj.com("def_id",Office_id,Office_Name,def_id) ;

			String A_u_id=obj.getValue("fas_mst_acct_unit_offices","ACCOUNTING_UNIT_ID","where ACCOUNTING_FOR_OFFICE_ID="+def_id);

				
	//			String A_u_id=obj.getValue("FAS_MST_ACCT_UNITS","ACCOUNTING_UNIT_ID","where ACCOUNTING_UNIT_OFFICE_ID="+def_id);
		 		String cb=obj.combo_str("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","BENEFICIARY_SNO"," where "+new_cond+" OFFICE_ID="+def_id+" and BENEFICIARY_SNO in  ( SELECT SUB_LEDGER_CODE from FAS_JOURNAL_TRANSACTION where CASHBOOK_YEAR="+inp_year+" and CASHBOOK_MONTH="+inp_month+"  ) order by BENEFICIARY_TYPE_ID,BENEFICIARY_NAME","ben",ben,"onchange=rld() style='width:180pt;'   class=select");  
		 		String qry="SELECT "+
		 		" tr.ACCOUNTING_UNIT_ID, "+
		 		" tr.account_head_code, "+
		 		" tr.amount,tr.CR_DR_INDICATOR , tr.VOUCHER_NO,ac.ACCOUNT_HEAD_DESC,to_char(mas.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,tr.PARTICULARS ,ben.BENEFICIARY_NAME,ben.BENEFICIARY_SNO "+
		 		" FROM "+
		 		" (SELECT * "+
		 				 "  FROM FAS_JOURNAL_TRANSACTION "+
		 		 " WHERE   ACCOUNTING_FOR_OFFICE_ID="+def_id+" and account_head_code IN "+
		 		 " ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "+
		 		    		"  ) "+ 
		 		    		//"and VOUCHER_NO not in (select VOUCHER_NO from PMS_DCB_OTHER_CHARGES where CASHBOOK_MONTH="+inp_month+" and  CASHBOOK_YEAR="+inp_year+" and  OFFICE_ID="+Office_id+" ) "+
		 		   " AND SUB_LEDGER_TYPE_CODE=14 "+
		 		 // " AND SUB_LEDGER_CODE     = "+ben+
		 	 	" and CASHBOOK_MONTH= "+inp_month+  
		  		" and CASHBOOK_YEAR=   "+inp_year+  
		 		" )tr "+
		 		 " JOIN "+ // Journal type considered for  others charges during demand generation 
		 		 // journal type code 73,56,57,53,75 from FAS_MST_JOURNAL_TYPE Table  /// (66,53,75,87)
		 		 //56,57,53,75,87,65,66,54,104,102
		 		" ( SELECT * FROM FAS_JOURNAL_MASTER WHERE JOURNAL_TYPE_CODE in (select JOURNAL_TYPE_CODE from  PMS_DCB_APPLICABLE_JOU_TYPE where CONDITION=1)  and  JOURNAL_STATUS='L' and CASHBOOK_MONTH= "+inp_month+" and CASHBOOK_YEAR=  "+inp_year+"  and ACCOUNTING_FOR_OFFICE_ID="+def_id+
		 				 " )mas "+ 
		 		 " ON mas.ACCOUNTING_UNIT_ID       =tr.ACCOUNTING_UNIT_ID "+
		 		" AND mas.ACCOUNTING_FOR_OFFICE_ID=tr.ACCOUNTING_FOR_OFFICE_ID "+
		 		" AND mas.CASHBOOK_YEAR           =tr.CASHBOOK_YEAR "+
		 		" AND mas.CASHBOOK_MONTH          =tr.CASHBOOK_MONTH  "+
		 		" and mas.VOUCHER_NO=tr.VOUCHER_NO "+   
		 		" join  "+
		 		" ( "+
						" select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS  "+

						" )ac "+
					" on ac.ACCOUNT_HEAD_CODE=tr.account_head_code   " +
				 " join " + 
				  " ( select BENEFICIARY_NAME,BENEFICIARY_SNO from  PMS_DCB_MST_BENEFICIARY where "+new_cond+" OFFICE_ID="+def_id+"   )ben on ben.BENEFICIARY_SNO=tr.SUB_LEDGER_CODE order by VOUCHER_NO ,BENEFICIARY_SNO" ;
		 		ResultSet rs_local=null;
		 				 
	//----------------------for defunct office id code included old office id query2 is for old _office id --------------------------------------//
	
	
		 		String qry2="SELECT "+
				 		" tr.ACCOUNTING_UNIT_ID, "+
				 		" tr.account_head_code, "+
				 		" tr.amount,tr.CR_DR_INDICATOR , tr.VOUCHER_NO,ac.ACCOUNT_HEAD_DESC,to_char(mas.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,tr.PARTICULARS ,ben.BENEFICIARY_NAME,ben.BENEFICIARY_SNO "+
				 		" FROM "+
				 		" (SELECT * "+
				 				 "  FROM FAS_JOURNAL_TRANSACTION "+
				 		 " WHERE   ACCOUNTING_FOR_OFFICE_ID="+def_id+" and account_head_code IN "+
				 		 " ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "+
				 		    		"  ) "+ 
				 		    		//"and VOUCHER_NO not in (select VOUCHER_NO from PMS_DCB_OTHER_CHARGES where CASHBOOK_MONTH="+inp_month+" and  CASHBOOK_YEAR="+inp_year+" and  OFFICE_ID="+Office_id+" ) "+
				 		   " AND SUB_LEDGER_TYPE_CODE=14 "+
				 		 // " AND SUB_LEDGER_CODE     = "+ben+
				 	 	" and CASHBOOK_MONTH= "+inp_month+  
				  		" and CASHBOOK_YEAR=   "+inp_year+ 
				 		" )tr "+
				 		 " JOIN "+ // Journal type considered for  others charges during demand generation 
				 		 // journal type code 73,56,57,53,75 from FAS_MST_JOURNAL_TYPE Table  /// (66,53,75,87)
				 		 //56,57,53,75,87,65,66,54,104,102
				 		" ( SELECT * FROM FAS_JOURNAL_MASTER WHERE JOURNAL_TYPE_CODE in (select JOURNAL_TYPE_CODE from  PMS_DCB_APPLICABLE_JOU_TYPE where CONDITION=1)  and  JOURNAL_STATUS='L' and CASHBOOK_MONTH= "+inp_month+" and CASHBOOK_YEAR=  "+inp_year+"  and ACCOUNTING_FOR_OFFICE_ID="+def_id+
				 				 " )mas "+ 
				 		 " ON mas.ACCOUNTING_UNIT_ID       =tr.ACCOUNTING_UNIT_ID "+
				 		" AND mas.ACCOUNTING_FOR_OFFICE_ID=tr.ACCOUNTING_FOR_OFFICE_ID "+
				 		" AND mas.CASHBOOK_YEAR           =tr.CASHBOOK_YEAR "+
				 		" AND mas.CASHBOOK_MONTH          =tr.CASHBOOK_MONTH  "+
				 		" and mas.VOUCHER_NO=tr.VOUCHER_NO "+   
				 		" join  "+
				 		" ( "+
								" select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS  "+

								" )ac "+
							" on ac.ACCOUNT_HEAD_CODE=tr.account_head_code   " +
						 " join " + 
						  " ( select BENEFICIARY_NAME,BENEFICIARY_SNO from  PMS_DCB_MST_BENEFICIARY where "+new_cond+"  old_OFFICE_ID="+def_id+"   )ben on ben.BENEFICIARY_SNO=tr.SUB_LEDGER_CODE order by VOUCHER_NO ,BENEFICIARY_SNO" ;
		 
		 				 
		 				 
		 				 
 			
		
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
					 		"    WHERE JOURNAL_TYPE_CODE IN (select JOURNAL_TYPE_CODE from  PMS_DCB_APPLICABLE_JOU_TYPE where CONDITION=2) "+
					 		"    AND JOURNAL_STATUS          ='L' "+
					 		"    AND CASHBOOK_MONTH          = "+inp_month+
					 		"    AND CASHBOOK_YEAR           = "+inp_year+
					 		"    AND ACCOUNTING_FOR_OFFICE_ID="+def_id+
					 		"    )mas "+
					 		"  JOIN "+
					 		"    (SELECT * "+
					 		"    FROM FAS_JOURNAL_TRANSACTION "+
					 		 "    WHERE ACCOUNTING_FOR_OFFICE_ID="+def_id+
					 		"    AND CASHBOOK_MONTH            = "+inp_month+
					 		"    AND CASHBOOK_YEAR             = "+inp_year+"  and  CR_DR_INDICATOR ='DR' "+
						//  "    AND VOUCHER_NO NOT           IN "+
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
					 		"      WHERE JOURNAL_TYPE_CODE IN (select JOURNAL_TYPE_CODE from  PMS_DCB_APPLICABLE_JOU_TYPE where CONDITION=2) "+
					 		"      AND JOURNAL_STATUS          ='L' "+
					 		"     AND CASHBOOK_MONTH          =  "+inp_month+
					 		"     AND CASHBOOK_YEAR           = "+inp_year+
					 		"      AND ACCOUNTING_FOR_OFFICE_ID="+def_id+  
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
					 			 "  AND OFFICE_ID="+def_id+
					 		 "  )ben "+
					 		 "  ON ben.BENEFICIARY_SNO=mas.SUB_LEDGER_CODE "+
					 		"  JOIN "+
					 		"  ( SELECT ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS "+
					 				 "  )ac "+
					 	 	  "  ON ac.ACCOUNT_HEAD_CODE=tr.account_head_code  order by VOUCHER_NO,BENEFICIARY_SNO";
					 	
					 		
			   if(Office_id.equals(def_id))
			   {
					System.out.println("def_id"+def_id);
					System.out.println("Office_id"+Office_id);
					System.out.println("inside 1");
		 			rs_local=obj.getRS(qry);
			   }else
			   {
				System.out.println("def_id"+def_id);
				System.out.println("Office_id"+Office_id);
				System.out.println("inside 2");
		 		rs_local=obj.getRS(qry2);
			   }
		 				
	 			
		%>
		<script type="text/javascript">  
		function rld()
		{
			document.forms["othercharges"].submit();
		}
		</script>
</head>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/other_charges_collection.js"></script>
		<body onload="flash()">
		<form action="other_charges_collection.jsp" method="get" name="othercharges">
		<table align="center" width="100%" cellpadding="5" cellspacing="0" border=0  >
		<tr class="tdH"> <td  valign="middle" colspan="2" align="center"><font size='4'> Other Charges (Journal Adjustment) </font> </td></tr>
		<tr bgcolor="#e5eff8"></tr>
		<tr>
		<td colspan="2">
		<table align="left" width="95%" cellpadding="0" cellspacing="0" borderz=0  bordercolor="skyblue" >
		<tr >    
				
			 <td align="left"   valign="top">Select  Division </td>
             <Td align="left"   valign="top"> 
             <%=com_id%>
             </td>
             
	          <td   align="left" class="tdText"  valign="top"><label class="lbl">Cash Book Year&nbsp; &nbsp;</label> </td>
			  <Td  align="left"  valign="top"> <select class="select"  id="year" name="year"  >
			   <option value="0">-Select Year-</option>
			  <%
			   	for (int i=year;i>=2015;i--)
		//	    for (int i=2010;i<year+1;i++)
			  {
			    if (Integer.parseInt(inp_year)!=i) {
			   %>
			  <option value="<%=i%>"><%=i%></option>
			  <%}else{%> 
			  <option value="<%=i%>" selected><%=i%></option>
			  
			  <%} %>
			  <%} %>
			  </select> <label id="msg"></label> </tD>
		 
			 <td align="left" class="tdText"   valign="top"><label class="lbl">Cash Book Month</label></td>	  	          
			 <Td align="left"  valign="top"><select class="select" id="month" name="month" onchange="rld()">
			 <option value="0">-Select Month-</option>
			  <% 
			  for (int j=1;j<=12;j++)    
			  {
			   if (Integer.parseInt(inp_month)!=j) {
			  %>
			  <option value="<%=j%>"><%=monthArr[j]%></option>
			  <%} else { %>
			  <option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
			  <%} %>
			  <%} %>  
			 </select> </Td>
			 
						 
			 <td class="tdText" align="right"  valign="top"><label class="lbl">ACCOUNTING UNIT OFFICE ID : <%=A_u_id%></label>
				 <input type=button value="Check All" onclick="checkAll('othercharges',checked)"></td>	        
     		</tr>
          </table></td>
       </tr>
       <tr><td colspan="10"><font color="red" size="3" ><center>Adjust only 10 Sl.No  at a time if Total Sl.No is more than 10</center></font></td></tr>
          	    
       <tr><td colspan="2" valign="top">
       	   <table class="ttable" align="center" width="100%" border=0  cellpadding="0" cellspacing="0"  >
		   <tr bgcolor="skyblue" > 
			  <th class="tdText" align="center"  width="2%">Sl.No</th>  
			  <th class="tdText" align="center"  width="5%">Voucher No & Date</th>
			  <th class="tdText" align="center"   >Beneficiary Name </th>
	          <th class="tdText" align="center" width="25%">Account Head Code/Account Head Description </th>
	          <th class="tdText" align="center"  width="5%">CR/DR</th>
	          <th class="tdText" align="center" width="25%">Particulars</th>
	          <th class="tdText" align="center"  width="5%">Amount</th>
	          <th class="tdText" align="center"  width=" 5%">Check to Include in Bill</th>
          	</tr>
          	<%
          			int c=0;
          			String code="",PARTICULARS="",vno="",vdate="",drcr="",vamt="",bsno="",Bensno="0";
          			String btype,typedesc="";
          			try
          			{
          				while (rs_local.next())
          				{
          					code=rs_local.getString(2);
          					PARTICULARS=obj.isNull(rs_local.getString(8),2);
          					vno=rs_local.getString(5);
          					vdate=rs_local.getString(7);
          					drcr=rs_local.getString(4);
          					vamt=rs_local.getString(3);
          					bsno=rs_local.getString(10);
          					Bensno=rs_local.getString("BENEFICIARY_SNO");
          					c=0;
          					c=obj.getCount("PMS_DCB_OTHER_CHARGES","where  CASHBOOK_MONTH  ="+inp_month+ " and  CASHBOOK_YEAR     ="+inp_year+" and  ACCOUNTING_UNIT_ID="+A_u_id+" and BENEFICIARY_SNO="+bsno+" and VOUCHER_NO="+vno);
          					btype= obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID","where BENEFICIARY_SNO=" + Bensno);
          					typedesc= obj.getValue("PMS_DCB_BEN_TYPE", "BEN_TYPE_SDESC","where BEN_TYPE_ID=" + btype);
          					typedesc=typedesc.replace("", " A");
          					
         					if (c==0)
         					{
         						row++;
          					%>
          					 <%--
          					<tr> 
	          					<td width="2%"><%=row%></td>
	          					<td class="tdText" align="center" width="10%"><input type=hidden id="no<%=row%>" name="no<%=row%>"  class="tb0" value="<%=vno%>">    
	          					<input type=hidden id="vdate<%=row%>" name="vdate<%=row%>"  class="tb4" value="<%=vdate%>"><font size='2' color='	#810541'><%=vno%></font> &<font size='2' color='#15317E'> <%=vdate%></font></td> 
	          					<td class="tdText" align="left">&nbsp;&nbsp;<font color=green><b><%=rs_local.getString("BENEFICIARY_NAME")%><font color='#810541'><b>[<%=Bensno%>]</b></font>-<font color=green><b><%=typedesc.replace("", "-")%></b></font> <input type=hidden id="bsno<%=row%>" name="bsno<%=row%>"  class="tb4" value="<%=Bensno%> "></td>          					 	
		         				<td  class="tdText" align="left"> <input type=hidden id="aco<%=row%>" name="aco<%=row%>"  class="tb2" value="<%=code%>"> <font size='2' color='#15317E'><%=code%></font>/<font size='2' color='	#810541'> <%=rs_local.getString(6) %></font> </td>
	 	            			<td class="tdText" align="center" width="5%"> <input type=hidden id="crdr<%=row%>" name="crdr<%=row%>"  class="tb2" value="<%=drcr%>"><%=drcr%></td>
	 	            			<td class="tdText" align="left" width="25%"><%=PARTICULARS%></td><td><input type=hidden id="pr<%=row%>" name="pr<%=row%>"  class="tb2" value="<%=PARTICULARS%>"></td>
	 	            			<td class="tdText" align="center">   <input type=hidden id="crdramt<%=row%>" name="crdramt<%=row%>"  class="tb2" value="<%=vamt%>"><%=vamt%>  </td>	          				 
		          				<td class="tdText" align="center">  <input type="checkbox"  id="ck<%=row%>" name="ck<%=row%>"> </td>	          					
          					</tr>     
          					 --%>    
          					 <tr>
						          <td width="2%"><%=row%></td>
						          <td class="tdText" align="center" width="10%"><input type=hidden id="no<%=row%>" name="no<%=row%>"  class="tb0" value="<%=vno%>">    
						          <input type=hidden id="vdate<%=row%>" name="vdate<%=row%>"  class="tb4" value="<%=vdate%>"><font size='2' color=' #810541'><%=vno%></font> &<font size='2' color='#15317E'> <%=vdate%></font></td>
						          <td class="tdText" align="left">&nbsp;&nbsp;<font color=green><b><%=rs_local.getString("BENEFICIARY_NAME")%><font color='#810541'><b>[<%=Bensno%>]</b></font>-<font color=green><b><%=typedesc%></b></font> <input type=hidden id="bsno<%=row%>" name="bsno<%=row%>"  class="tb4" value="<%=Bensno%> "></td>          
						        <td  class="tdText" align="left"> <input type=hidden id="aco<%=row%>" name="aco<%=row%>"  class="tb2" value="<%=code%>"> <font size='2' color='#15317E'><%=code%></font>/<font size='2' color=' #810541'> <%=rs_local.getString(6) %></font> </td>
						            <td class="tdText" align="center" width="5%"> <input type=hidden id="crdr<%=row%>" name="crdr<%=row%>"  class="tb2" value="<%=drcr%>"><%=drcr%></td>
						            <Td class="tdText" align="left" width="25%"><%=PARTICULARS%></Td><input type=hidden id="pr<%=row%>" name="pr<%=row%>"  class="tb2" value="<%=PARTICULARS%>">
						            <td class="tdText" align="center">   <input type=hidden id="crdramt<%=row%>" name="crdramt<%=row%>"  class="tb2" value="<%=vamt%>"><%=vamt%>  </td>          
						          <td class="tdText" align="center">  <input type="checkbox"  id="ck<%=row%>" name="ck<%=row%>"> </td>          
          					</tr>        
          					   	
          					 
          					 				
          					<%
         					}
          				}          			
          			 }catch(Exception e) {} 
          			try
          			{
          				int c2=0;
          			 
          				rs_local=obj.getRS(qry1);
          				code="";PARTICULARS="";vno="";vdate="";drcr="";vamt="";bsno="";
          				while (rs_local.next())
          				{
              				System.out.println("inside while");

          					code=rs_local.getString(2);
          					PARTICULARS=rs_local.getString(8);
          					c2=0;
          					vno=rs_local.getString(5);
          					vdate=rs_local.getString(7);
          					drcr=rs_local.getString(4);
          					vamt=rs_local.getString(3);    
          					bsno=rs_local.getString(10);
          					c2=obj.getCount("PMS_DCB_OTHER_CHARGES","where  CASHBOOK_MONTH  ="+inp_month+ " and  CASHBOOK_YEAR     ="+inp_year+" and  ACCOUNTING_UNIT_ID="+A_u_id+" and BENEFICIARY_SNO="+bsno+" and VOUCHER_NO="+vno);
          					 btype= obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID","where BENEFICIARY_SNO=" + bsno);
          				 
          					 typedesc= obj.getValue("PMS_DCB_BEN_TYPE", "BEN_TYPE_DESC","where BEN_TYPE_ID=" + btype);
          					
         					if (c2==0)
         					{
         						row++;
         						
          					%>  
          					<tr>
          					 <td><%=row%></td>    
          					<td class="tdText" align="center" width="10%"><input type=hidden id="no<%=row%>" name="no<%=row%>"  class="tb0" value="<%=vno%>">    
          					  <input type=hidden id="vdate<%=row%>" name="vdate<%=row%>"  class="tb4" value="<%=vdate%>"><font size='2' color='	#810541'><%=vno%></font> &<font size='2' color='#15317E'> <%=vdate%></font></td> 
          					  <td class="tdText" align="left"><font color=green><b><%=rs_local.getString("BENEFICIARY_NAME")%><font color='#810541'><b>[<%=Bensno%>]</b></font>-<%=typedesc%></b></font><input type=hidden id="bsno<%=row%>" name="bsno<%=row%>"  class="tb4" value="<%=rs_local.getString("BENEFICIARY_SNO")%> "></td> 
	         				 	<td  class="tdText" align="left"> <input type=hidden id="aco<%=row%>" name="aco<%=row%>"  class="tb2" value="<%=code%>"> <font size='2' color='#15317E'><%=code%></font>/<font size='2' color='	#810541'> <%=rs_local.getString(6) %></font> </td>
 	            				<td class="tdText" align="center" width="5%"> <input type=hidden id="crdr<%=row%>" name="crdr<%=row%>"  class="tb2" value="<%=drcr%>"><%=drcr%></td>
 	            				<Td class="tdText" align="left" width="25%"><%=PARTICULARS%></Td><input type=hidden id="pr<%=row%>" name="pr<%=row%>"  class="tb2" value="<%=PARTICULARS%>">
 	            				<td class="tdText" align="center">   <input type=hidden id="crdramt<%=row%>" name="crdramt<%=row%>"  class="tb2" value="<%=vamt%>"><%=vamt%>  </td>	          				 
	          					<td class="tdText" align="center">  <input type="checkbox"  id="ck<%=row%>" name="ck<%=row%>"> </td>
          					</tr>
          					<%     
         					}    					   
          				}
          			%>    				
          			<%}catch(Exception e) {}
          	%></table></td> </tr>
          	<tr>
          		<td valign="middle" colspan="2" align="center"><input type="button" class="blprint" value="Submit" id="But1" onclick="disabl(),charges()">&nbsp;&nbsp; 
          		<input type="button" class="fb2" value="Refresh" onclick="javascript:window.location.reload();"> </td>
          	</tr>
			</table><input type="hidden" id="row_cnt" name="row_cnt" value="<%=row%>" /><input type="hidden" id="pr_status" name="pr_status" value="1" />
		</form>  
		</body>
</html>
<%
				}catch(Exception e)
				{
					out.println(e);
				}
%>
