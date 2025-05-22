 
 
 
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Journal Cancel </title>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
</head>
<body>
 <script type="text/javascript" src="../scripts/cellcreate.js"></script>
 <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<form action="journal_cancel.jsp" method="get">
<jsp:useBean id="bean" class="Servlets.PMS.PMS1.DCB.reports.count_bean" scope="session"></jsp:useBean>

<%
String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
Connection con;
String smonth="",syear="",html="",monval="",dis="",cancel_msg="";
int row=0;
String tbstatus="",res=""; 
int  A_unit_id=0;
	Controller obj=null,obj1=null;
	ResultSet rs_local=null,rs_local1=null;
	String sbut="";
	String code="";
		String PARTICULARS="";
		String vno="";
		String vdate="";
		String drcr="";
		String vamt="";
		String bsno="";
		String AUTHORIZED_TO="";
try
{
  obj=new  Controller();
  obj1=new  Controller();
  con=obj.con();
  
  obj.createStatement(con);
  obj1.createStatement(con);
  try
	{
	   userid=(String)session.getAttribute("UserId");
	}catch(Exception e) {userid="0";}
	if(userid==null)
	{ 
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	if (Office_id.equals("")) Office_id="0";
	
	  smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
	  syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	  monval=cmonth[Integer.parseInt(smonth)];
       
 	  bean.setMset(smonth);
	  bean.setYset(syear);
	
	bean.setOffid(Integer.parseInt(Office_id));
	A_unit_id=Integer.parseInt(obj.getValue("FAS_MST_ACCT_UNITS", "ACCOUNTING_UNIT_ID", " where ACCOUNTING_UNIT_OFFICE_ID="+Office_id));
	  
	  
	tbstatus=obj.getValue("FAS_TRIAL_BALANCE_STATUS", "TB_STATUS", " where ACCOUNTING_UNIT_ID = "+A_unit_id+"   and CASHBOOK_YEAR =" +syear+ " and CASHBOOK_MONTH="+smonth);
     
	if (tbstatus.trim().equalsIgnoreCase("y"))			
		res="<font class='fnt' color='red'>TRIAL BALANCE CLOSED</font>";
	else
		res="TRIAL BALANCE NOT CLOSED";
	
	sbut=obj.setValue("sbut",request);
	   
	
	String qry="SELECT "+
		" tr.ACCOUNTING_UNIT_ID, "+
		" tr.account_head_code, "+
		" tr.amount,tr.CR_DR_INDICATOR , tr.VOUCHER_NO,ac.ACCOUNT_HEAD_DESC,to_char(mas.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,tr.PARTICULARS   "+
		" FROM "+
		" (SELECT * "+
				 "  FROM FAS_JOURNAL_TRANSACTION "+
		 " WHERE   ACCOUNTING_FOR_OFFICE_ID="+Office_id+"::numeric and account_head_code IN "+
		 " ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "+
		    		"  ) "+
		   "  "+
		 // " AND SUB_LEDGER_CODE     = "+ben+
	 	" and CASHBOOK_MONTH= "+smonth+"::numeric"+
		" and CASHBOOK_YEAR=   "+syear+"::numeric"+
		" )tr "+
		 " JOIN "+
		" ( SELECT * FROM FAS_JOURNAL_MASTER WHERE JOURNAL_TYPE_CODE in (73 )  and  JOURNAL_STATUS='L' and CASHBOOK_MONTH= "+smonth+"::numeric and CASHBOOK_YEAR=  "+syear+"::numeric  and ACCOUNTING_FOR_OFFICE_ID="+Office_id+"::numeric"+
				 " )mas "+
		 " ON mas.ACCOUNTING_UNIT_ID       =tr.ACCOUNTING_UNIT_ID "+
		//" AND mas.ACCOUNTING_FOR_OFFICE_ID=tr.ACCOUNTING_FOR_OFFICE_ID "+
		" AND mas.CASHBOOK_YEAR           =tr.CASHBOOK_YEAR "+
		" AND mas.CASHBOOK_MONTH          =tr.CASHBOOK_MONTH  "+
		" and mas.VOUCHER_NO=tr.VOUCHER_NO "+ 
		" join  "+
		" ( "+
			" select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS  "+

			" )ac "+
		" on ac.ACCOUNT_HEAD_CODE=tr.account_head_code   " +
	 "   " + 
	  "   order by VOUCHER_NO" ;

		
	 
	int flag=0;
	 
	if (tbstatus.trim().equalsIgnoreCase("y"))
	{
		flag=1; 
		dis="disabled='disabled'";
		cancel_msg="<font class='fnt' color='red'>JOURNAL CANNOT BE CANCELLED.....</font> ";
	}else
	{
		dis="";
		
		int jcount=0,billcount=0,jc_count=0,jlcount=0;
		String wcfreeze="";
		jcount=obj.getCount("FAS_JOURNAL_MASTER","where ACCOUNTING_UNIT_ID="+A_unit_id+" and CASHBOOK_YEAR="+syear+" and CASHBOOK_MONTH="+smonth +"  and JOURNAL_TYPE_CODE=73");
	 
		if (jcount>0)
		{ 
			jlcount=obj.getCount("FAS_JOURNAL_MASTER","where ACCOUNTING_UNIT_ID="+A_unit_id+" and CASHBOOK_YEAR="+syear+" and CASHBOOK_MONTH="+smonth +" and JOURNAL_STATUS='L' and JOURNAL_TYPE_CODE=73");
			 
		  // if Journal avaiable or not 
		   if ( jlcount > 0) // if yes		
		   {
			   
			   billcount=obj.getCount("PMS_DCB_TRN_BILL_DMD","where OFFICE_ID="+Office_id+" and BILL_MONTH="+smonth+" and BILL_YEAR="+syear);
			   // if demand has 
			   if (billcount > 0)
			   {
				   cancel_msg=" Demand Notice(Bill) Already Generated For This Month and Year...<br> "+ 
				    " Delete Generated Bills <a href='Demand_Report_List.jsp'></a>";  
				   flag=1;
			   }else
			   {
				   wcfreeze=obj.isNull(obj.getValue("PMS_DCB_FREEZE_STATUS","WC_FREEZED","where OFFICE_ID="+Office_id+" and CASHBOOK_YEAR="+syear+" and  CASHBOOK_MONTH="+smonth),1);
				   
				   if (wcfreeze.trim().equalsIgnoreCase("y"))
				   {
					   cancel_msg=" Water Charges Should be Unfreezed after Cancelling Journal <a href='wc_unfreeze.jsp'>Click Here</a> and " 
					     +" Pumping Return should also unfreeze";
					   flag=1; 
				   }
				   else 
				   {
					   cancel_msg=" Pumping Return should be Unfreezed <a href='Pr_freeze.jsp'>Click Here</a>"  ;
					   flag=1;
				   }
			   }
			    
		   }
		   else
		   {
			   jc_count=obj.getCount("FAS_JOURNAL_MASTER","where ACCOUNTING_UNIT_ID="+A_unit_id+" and CASHBOOK_YEAR="+syear+" and CASHBOOK_MONTH="+smonth +" and JOURNAL_STATUS='C' and JOURNAL_TYPE_CODE=73");
			   cancel_msg=" Journal Already Cancelled for this Cash Book Month and Year ";
			   flag=1;
			   dis="disabled='disabled'"; 
		   }
		}
		else // There is no journal 
		{
			   cancel_msg=" Journal Not Yet Posted for this Cash Book Month and Year ";
			   flag=1;
			   dis="disabled='disabled'";
		}
		
	}
		
	
 
	 String qry1="SELECT "+
		" tr.ACCOUNTING_UNIT_ID, "+
		" tr.account_head_code, "+
		" tr.amount,tr.CR_DR_INDICATOR , tr.VOUCHER_NO,ac.ACCOUNT_HEAD_DESC,to_char(mas.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,tr.PARTICULARS   "+
		" FROM "+
		" (SELECT * "+
				 "  FROM FAS_JOURNAL_TRANSACTION "+
		 " WHERE   ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and account_head_code IN "+
		 " ( SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP "+
		    		"  ) "+
		   "  "+
		 // " AND SUB_LEDGER_CODE     = "+ben+
	 	" and CASHBOOK_MONTH= "+smonth+
		" and CASHBOOK_YEAR=   "+syear+ 
		" )tr "+
		 " JOIN "+
		" ( SELECT * FROM FAS_JOURNAL_MASTER WHERE JOURNAL_TYPE_CODE in (73 )  and  JOURNAL_STATUS='C' and CASHBOOK_MONTH= "+smonth+" and CASHBOOK_YEAR=  "+syear+"  and ACCOUNTING_FOR_OFFICE_ID="+Office_id+
				 " )mas "+
		 " ON mas.ACCOUNTING_UNIT_ID       =tr.ACCOUNTING_UNIT_ID "+
		//" AND mas.ACCOUNTING_FOR_OFFICE_ID=tr.ACCOUNTING_FOR_OFFICE_ID "+
		" AND mas.CASHBOOK_YEAR           =tr.CASHBOOK_YEAR "+
		" AND mas.CASHBOOK_MONTH          =tr.CASHBOOK_MONTH  "+
		" and mas.VOUCHER_NO=tr.VOUCHER_NO "+ 
		" join  "+
		" ( "+
			" select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC FROM COM_MST_ACCOUNT_HEADS  "+

			" )ac "+
		" on ac.ACCOUNT_HEAD_CODE=tr.account_head_code   " +
	 "   " + 
	  "   order by VOUCHER_NO" ;
	
	//if (tbstatus.trim().equalsIgnoreCase("y"))			 
		rs_local=obj.getRS(qry);
	PreparedStatement ps_=con.prepareStatement(qry1);
	rs_local1=ps_.executeQuery();
}catch(Exception e) {
	userid="0";
	}
%>
 <script type="text/javascript" src="../scripts/journal_cancel_.js"></script>

<table border="1" width="80%" align="center" cellpadding="5" cellspacing="0" class="table"> 
	<tr  bgcolor='skyblue'><td colspan="2" align="center"><b>Journal Cancel</b></td></tr>

	<tr>
		<td width="25%" class="tdText"><font class='fnt'> Office Name :</font></td><td  class="tdText"><b><jsp:getProperty name="bean" property="process"/></b></td>	
	</tr>
	<tr>
		<td width="25%" class="tdText"> <font class='fnt'>Trial Balance Status  : </font></td><td  class="tdText"><b><%=res%></b></td>	
	</tr>
	<tr>
		<td width="25%" class="tdText" ><font class='fnt'> Cash Book Month and Cash Book Year  : </font> </td><td  class="tdText"><b><%=monval%>  <%=syear%></b></td>	
	</tr>
	
	<tr>
		<td width="25%" class="tdText"> <font class='fnt'> Message  : </font></td><td  class="tdText"><b><%=cancel_msg%></b></td>	
	</tr>
	<tr>
		<td width="25%" class="tdText" colspan="2" align="center"><input type="submit"  value="View Journal Details" name="sbut"  onclick="charge_show('jshow',1,0)"  style="color: blue;font-weight: bold"> <input type="button" style="color: blue;font-weight: bold"value="Exit" onclick="javascript:window.close();">
		
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<pre><font size="4" color='red'><strong><b>Click View Journal Details to Cancel and View Water Charges Journal</b></strong></font></pre> 
		
		 </td>	
	</tr>
	<% 
	if (sbut.equalsIgnoreCase("View Journal Details")) {
	%> 
	<tr> <td colspan="2">
	<table class="table" align="center" width="100%" border=1  cellpadding="5" cellspacing="0" style="border-collapse: collapse;" bordercolor="skyblue" >
				 <tr bgcolor='skyblue'  >
				 	 <td  align="center" width="2%"><font class='fnt' >Sl.No</font></td>
					 <td align="center" width="10%"><font class='fnt' >Voucher No & Date</font></td>
 					 <td align="center" width="25%"><font class='fnt' >Account Head Code/Account Head Description</font></td>
	               	 <td align="center" width="5%"><font class='fnt' >CR/DR</font></td>
	           		 <td align="center" width="25%"><font class='fnt' >Particulars</font></td>
	    	         <td align="center" width="5%"><font class='fnt' >Amount</font></td>
          		</tr>
          	<tr><td colspan="5" align="left"><font class='fnt' color='green' ><b> Live Journal  </b></font> </td></tr>
          	<%
          	
          	
          	
          	
          	
          	
          	
          	
          			try
          			{
          			
          			
          				while (rs_local.next())
          				{
          					code=rs_local.getString(2);
          					PARTICULARS=rs_local.getString(8);
          					
          					vno=rs_local.getString(5);
          					vdate=rs_local.getString(7);
          					drcr=rs_local.getString(4);
          					vamt=rs_local.getString(3);
          					AUTHORIZED_TO=obj.getValue("FAS_CROSS_REFERENCE","AUTHORIZED_TO", " where ACCOUNTING_UNIT_ID="+A_unit_id+"  and  ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and CASHBOOK_YEAR="+syear+" and  CASHBOOK_MONTH="+smonth+" and  DOC_TYPE='DJV' and VOUCHER_NO="+vno);
          					AUTHORIZED_TO="C";
          					if (AUTHORIZED_TO.equalsIgnoreCase("C"))
          					{
          						row++;
          					%>
          					<tr>
          					 <td width="2%"><%=row%></td>
          					<td class="tdText" align="center" width="10%">
          						<input type=hidden id="no<%=row%>" name="no<%=row%>"  class="tb0" value="<%=vno%>">    
          					  	<input type=hidden id="vdate<%=row%>" name="vdate<%=row%>"  class="tb4" value="<%=vdate%>"><font size='2' color='	#810541'><%=vno%></font> &<font size='2' color='#15317E'> <%=vdate%></font></td> 
        					 	
	         				 	<td  class="tdText" align="left"> <input type=hidden id="aco<%=row%>" name="aco<%=row%>"  class="tb2" value="<%=code%>"> <font size='2' color='#15317E'><%=code%></font>/<font size='2' color='	#810541'> <%=rs_local.getString(6) %></font> </td>
 	            				<td class="tdText" align="center" width="5%"> <input type=hidden id="crdr<%=row%>" name="crdr<%=row%>"  class="tb2" value="<%=drcr%>"><%=drcr%></td>
 	            				<Td class="tdText" align="left" width="25%"><%=PARTICULARS%></Td><input type=hidden id="pr<%=row%>" name="pr<%=row%>"  class="tb2" value="<%=PARTICULARS%>">
 	            				<td class="tdText" align="center">   <input type=hidden id="crdramt<%=row%>" name="crdramt<%=row%>"  class="tb2" value="<%=vamt%>"><%=vamt%>  </td>	          				 
	          					 
	          					
          					</tr>
          					
          					<%
          					}
          					else
          					{
          						%>
          						<tr > 
          					    	<td class="tdText" align="center" width="10%"><%=vno%><%=vdate%> </td> 
           					 		<td  class="tdText" align="left"> <%=code%><%=rs_local.getString(6) %> </td>
 	            					<td class="tdText" align="center" width="5%">  <%=drcr%></td>
 	            					<Td class="tdText" align="left" width="25%"><%=PARTICULARS%></Td>
 	            					<td class="tdText" align="center"> <%=vamt%>  </td>	          				 
	          						<td class="tdText" align="center" ><font color='Red'> Not Authorized for Cancel</font>  </td>
	          					
          					</tr>
          						
          						<%
          						
          						
          					}
          				}
          				rs_local.close();
          			}catch(Exception e) { out.println(e);}
          				%>
          				</table>
          				</td>
          				</tr>
          				<tr>
          		<td valign="middle" colspan="2" align="center">
          		
         		 		<input type="button" class="fb2" value="Cancel Journal" onclick="jou_cancel_ins()"  <%=dis%>>
          		
          		&nbsp;&nbsp;  </td>
          	</tr>
          				
          				<tr><td colspan="5" align="left"><font class='fnt' color='red' > Cancelled Journal </font> </td></tr>
          				<Tr>
          				<td colspan="5">
          				<table class="ttable" align="center" width="100%" border=1  cellpadding="0" cellspacing="0"  >
          				
          				<% 
          				int row1=0;
          				System.out.println("TEST ");
          				while (rs_local1.next())
          				{
          					code=rs_local1.getString(2);
          					PARTICULARS=rs_local1.getString(8);
          					
          					vno=rs_local1.getString(5);
          					vdate=rs_local1.getString(7);
          					drcr=rs_local1.getString(4);
          					vamt=rs_local1.getString(3);
          					 
          						row1++;
          					%>
          					<tr> 
          					<td width="2%"><%=row1%></td>
          					<td class="tdText" align="center" width="10%">
          							<font size='2' color='	#810541'><%=vno%></font> &<font size='2' color='#15317E'> <%=vdate%></font>
          					</td> 
          					<td  class="tdText" align="left" width="25%"> 
          						<font size='2' color='#15317E'><%=code%></font>/<font size='2' color='	#810541'> <%=rs_local1.getString(6) %></font>
          					</td>
 	            			<td class="tdText" align="center" width="5%"> 
 	            				<%=drcr%>
 	            			</td>
 	            			<Td class="tdText" align="left" width="25%">
 	            				<%=PARTICULARS%>
 	            			</Td>
 	            			<td class="tdText" align="center" width="5%">   
 	            				<%=vamt%>  
 	            			</td>	          				 
	          					 
	          					
          					</tr>
          					
          					<%
          					 
          				
          				
          				}
          				
          				rs_local1.close();
          				
          				
          				
          				
          				
          				
          				
          				
          				
          				
          				
          				
          				
          				
          				
          			
          			%>    				
          					
          			<%
          	   
          		
          	%></table></td> </tr>
          	<%} %>
          		
	</table>
<input type=hidden value="<%=row%>" id="rowcnt"> 

</form>
</body>
</html>
 