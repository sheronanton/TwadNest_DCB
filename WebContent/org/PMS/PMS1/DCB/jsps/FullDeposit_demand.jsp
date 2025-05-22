<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">



	<link href="../../../../../css/txtbox.css" rel="stylesheet"  media="screen"/>
<title>Full Deposit Demand</title>
<%
	String new_cond=Controller.new_cond;
 String amt="",billsno="",inp_month="",inp_year="";
 String combo="";
	int row=0; 
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			String userid="0",Office_id="",Office_Name="";
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
				 //	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				String yr=obj.setValue("year",request);
				String mt=obj.setValue("month",request);
				String ben=obj.setValue("ben",request);
				inp_month=mt;
				inp_year=yr;
				Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				if (Office_id.equals("")) Office_id="0";
		 		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
				String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
				 
		 		String A_u_id=obj.getValue("FAS_MST_ACCT_UNITS","ACCOUNTING_UNIT_ID","where ACCOUNTING_UNIT_OFFICE_ID="+Office_id);
		 		String cb=obj.combo_str("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","BENEFICIARY_SNO"," where "+new_cond+" OFFICE_ID="+Office_id+" and BENEFICIARY_SNO in  ( SELECT SUB_LEDGER_CODE from FAS_JOURNAL_TRANSACTION where CASHBOOK_YEAR="+inp_year+" and CASHBOOK_MONTH="+inp_month+"  ) order by BENEFICIARY_TYPE_ID,BENEFICIARY_NAME","ben",ben,"onchange=rld() style='width:180pt;'   class=select");  
		 		String qry="SELECT "+
		 		" tr.ACCOUNTING_UNIT_ID, "+
		 		" tr.account_head_code, "+
		 		" tr.amount,tr.CR_DR_INDICATOR , tr.VOUCHER_NO,ac.ACCOUNT_HEAD_DESC,to_char(mas.VOUCHER_DATE,'DD/MM/YYYY') as VOUCHER_DATE,tr.PARTICULARS ,ben.BENEFICIARY_NAME,ben.BENEFICIARY_SNO "+
		 		" FROM "+
		 		" (SELECT * "+
		 				 "  FROM FAS_JOURNAL_TRANSACTION "+
		 		 " WHERE   ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and VOUCHER_NO not in (select VOUCHER_NO from PMS_DCB_OTHER_CHARGES where CASHBOOK_MONTH="+inp_month+" and  CASHBOOK_YEAR="+inp_year+" and  OFFICE_ID="+Office_id+" ) "+
		 		   " AND SUB_LEDGER_TYPE_CODE=14 "+
		 		  " AND SUB_LEDGER_CODE  in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" OFFICE_ID="+Office_id+" and BENEFICIARY_TYPE_ID=18)   " +
		 	 	" and CASHBOOK_MONTH= "+inp_month+
		  		" and CASHBOOK_YEAR=   "+inp_year+ 
		 		" and CR_DR_INDICATOR='DR')tr "+
		 		 " JOIN "+
		 		" ( SELECT * FROM FAS_JOURNAL_MASTER WHERE JOURNAL_TYPE_CODE in (73)  and  JOURNAL_STATUS='L' and CASHBOOK_MONTH= "+inp_month+" and CASHBOOK_YEAR=  "+inp_year+"  and ACCOUNTING_FOR_OFFICE_ID="+Office_id+
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
				  " ( select BENEFICIARY_NAME,BENEFICIARY_SNO from  PMS_DCB_MST_BENEFICIARY where "+new_cond+" OFFICE_ID="+Office_id+"   )ben on ben.BENEFICIARY_SNO=tr.SUB_LEDGER_CODE order by VOUCHER_NO" ;

		 		
		 	 
		 		ResultSet rs_local=null;
		 		 
		 			rs_local=obj.getRS(qry);
		 		 
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
		<form action="FullDeposit_demand.jsp" method="get" name="othercharges">
			<table align="center" width="95%" cellpadding="5" cellspacing="0" border=0 class="alerts2">
		 <tr  > <td  valign="middle" colspan="3" align="center"><font size='4'> Full Deposit Demand</font> </td></tr>
		 	<tr bgcolor="#e5eff8"><td colspan="3" class="tdText" align="right">ACCOUNTING UNIT OFFICE ID : <%=A_u_id%></td></tr>
			<tr>
	          <td  align="left" class="tdText" width="5%">&nbsp;&nbsp;Year </td>
			  <Td colspan="2"> <select class="select"  id="year" name="year"  style="width:180pt;" >
			   <option value="0">-Select Year-</option>
			  <%
			  
			  for (int i=2010;i<year+1;i++)
			  {
			    if (Integer.parseInt(inp_year)!=i) {
			   %>
			  <option value="<%=i%>"><%=i%></option>
			  <%}else{%> 
			  <option value="<%=i%>" selected><%=i%></option>
			  
			  <%} %>
			  <%} %>
			  </select> <label id="msg"></label> </tD>
			  </tr>
			 <Tr >
			   <td align="left" class="tdText">&nbsp;&nbsp;Month </td>	  	          
			 <Td  colspan="3"><select class="select" id="month" name="month"  style="width:180pt;"   onchange="rld()">
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
			 </select>   </Td>	        
			  
          	</tr>
           
          	
          	<tr> <td colspan="3"><table class="ttable" align="center" width="95%" border=1  cellpadding="0" cellspacing="0"  >
					 <tr bgcolor="skyblue" >
					  <td class="tdText" align="center"  width="5%">Sl.No</td>
					  <td class="tdText" align="center"  width="15%">Voucher No & Date</td>
					 <td class="tdText" align="center"   >Beneficiary Name </td>
					
	          <td class="tdText" align="center" width="50%">Account Head Code/Account Head Description </td>
	                <td class="tdText" align="center"  width="5%">CR/DR</td>
	           
	    
	            <td class="tdText" align="center"  width="10%">Amount</td>
 	          
          		</tr>
          	
          	<%
          			try
          			{
          			
          				String code="";
          				String PARTICULARS="";
          				String vno="";
          				String vdate="";
          				String drcr="";
          				String vamt="";
          				String bsno="";
          				while (rs_local.next())
          				{
          					code=rs_local.getString(2);
          					PARTICULARS=rs_local.getString(8);
          					row++;
          					vno=rs_local.getString(5);
          					vdate=rs_local.getString(7);
          					drcr=rs_local.getString(4);
          					vamt=rs_local.getString(3);
          					bsno=rs_local.getString(3);
          					%>
          					<tr> 
          					<td class="tdText" align="center" width="2%"><%=row%></td>
          					<td class="tdText" align="center" width="15%"><input type=hidden id="no<%=row%>" name="no<%=row%>"  class="tb0" value="<%=vno%>">    
          					  <input type=hidden id="vdate<%=row%>" name="vdate<%=row%>"  class="tb4" value="<%=vdate%>"><font size='2' color='	#810541'><%=vno%></font> &<font size='2' color='#15317E'> <%=vdate%></font></td> 
          					  <td class="tdText" align="left"><%=rs_local.getString("BENEFICIARY_NAME")%> <input type=hidden id="bsno<%=row%>" name="bsno<%=row%>"  class="tb4" value="<%=rs_local.getString("BENEFICIARY_SNO")%> "></td> 
          					 	
	         				 	<td  class="tdText" align="left"> <input type=hidden id="aco<%=row%>" name="aco<%=row%>"  class="tb2" value="<%=code%>"> <font size='2' color='#15317E'><%=code%></font>/<font size='2' color='	#810541'> <%=rs_local.getString(6) %></font> </td>
 	            				<td class="tdText" align="center" width="5%"> <input type=hidden id="crdr<%=row%>" name="crdr<%=row%>"  class="tb2" value="<%=drcr%>"><%=drcr%></td>
  	            				<td class="tdText" align="center">   <input type=hidden id="crdramt<%=row%>" name="crdramt<%=row%>"  class="tb2" value="<%=vamt%>"><%=vamt%>  </td>	          				 
	          				
	          					
          					</tr>
          					
          					<%          					   
          				}
          			
          			
          			%>    				
          					
          			<%}catch(Exception e) {}
          	 
          		
          	%></table></td> </tr>
          	<tr>
          		<td valign="middle" colspan="3" align="center"><input type="button" class="fb2" value="Exit" onclick="window.close()"  style="font-style: italic;color: red">&nbsp;&nbsp;  </td>
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
	
