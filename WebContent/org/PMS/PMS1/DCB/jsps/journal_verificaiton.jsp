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
<title>Insert title here</title>
<%
/* 
	if  Journal Master --  JOURNAL_TYPE_CODE  56,57,53,75,87,66,54
			SUB_LEDGER_TYPE_CODE=14
*/

 String new_cond=Controller.new_cond;
 String amt="",billsno="",inp_month="",inp_year="",combo="";
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
				 	response.sendRedirect(request.getContextPath()+"/index.jsp");
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
		 	ResultSet rs_local=null;
		 	String qry1=" SELECT a.* FROM FAS_JOURNAL_TRANSACTION a WHERE account_head_code IN (SELECT distinct ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where   acc_type='C' "+
		 		        " )and CASHBOOK_MONTH="+mt+" and CASHBOOK_YEAR in ( "+yr+") "+
		 		        " and exists ( SELECT b.voucher_no  FROM FAS_JOURNAL_MASTER b where b.accounting_for_office_id=a.accounting_for_office_id "+
						" and b.cashbook_month=a.cashbook_month and b.cashbook_year=a.cashbook_year and b.voucher_no=a.voucher_no and b.journal_status='L')and b.JOURNAL_STATUS='L' AND ACCOUNTING_FOR_OFFICE_ID="+Office_id+" ";
	 		rs_local=obj.getRS(qry1);
	 		  
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
		<form action="journal_verificaiton.jsp" method="get" name="othercharges">
		<table align="center" width="100%" cellpadding="5" cellspacing="0" border=0  bordercolor="skyblue" >
		<tr class="tdH"> <td  valign="middle" colspan="2" align="center"><font size='4'> Other Charges (Journal Adjustment) </font> </td></tr>
		<tr bgcolor="#e5eff8"></tr>
		<tr>
		<td colspan="2">
		<table align="left" width="90%" cellpadding="0" cellspacing="0" border="0"  bordercolor="skyblue" >
		<tr >    
	          <td   align="left" class="tdText" width="10%" valign="top"><label class="lbl">Cash Book Year&nbsp; &nbsp;</label> </td>
			  <td  width="20%" align="left"  valign="top"> <select class="select"  id="year" name="year"  >
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
			  </select> <label id="msg"></label> </td>		 
			 <td align="left" class="tdText" width="12%"  valign="top"><label class="lbl">Cash Book Month</label></td>	  	          
			 <td align="left" width="20%"  valign="top"><select class="select" id="month" name="month"    onchange="rld()">
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
			 </select> </Td><td class="tdText" align="right"  valign="top"><label class="lbl">ACCOUNTING UNIT OFFICE ID : <%=A_u_id%></label></td>	        
     </tr>
     </table>
     </td></tr>          	    
     <tr><td colspan="2" valign="top">
       	   <table  align="center" width="100%" border=1  cellpadding="0" cellspacing="0"  >
		   <tr> 
		      <td align="center" width="2%">Sl.No</td>
			  <td align="center" width="5%">Voucher No  </td>
			  <td align="center" width="15%">Sub Ledger Type Code  </td>
	          <td align="center" width="15%">Sub Ledger Code( Beneficiary Name )</td>
	          <td align="center" width="5%">Account Head Code</td>			  
	          <td align="center" width="5%">Amount</td>
	          <td align="center" width="5%">CR / DR </td> 
	          <td align="center" width="25%">Particulars</td>     
          	</tr>
          	<%
          			int c=0;
          			String code="",PARTICULARS="",vno="",vdate="",drcr="",vamt="",bsno="",Bensno="0";
          			String btype,typedesc="";
          			 
          			try
          			{
          				int c2=0;
          				rs_local=obj.getRS(qry1);
          				code="";PARTICULARS="";vno="";vdate="";drcr="";vamt="";bsno="";
          				while (rs_local.next())
          				{
          					
          					
          					vno=rs_local.getString("VOUCHER_NO");          					
          					drcr=rs_local.getString("CR_DR_INDICATOR");
          					vamt=rs_local.getString("AMOUNT");    
          					bsno=rs_local.getString("SUB_LEDGER_CODE");
          					code=rs_local.getString("SUB_LEDGER_TYPE_CODE");
          					row++;
          					PARTICULARS=rs_local.getString("PARTICULARS");
          					c2=0;
          					
          					%>  
          					<tr>
          					 <td><%=row%></td>    
          					 <td><%=vno%></td>
          					  <%
          					  	if (Integer.parseInt(code)==10) out.println("<td>Scheme ( "+code+" )</td>");
          					  	else if (Integer.parseInt(code)==14) out.println("<td>Beneficiary ( "+code+" )</td>");
          					    else out.println("<td>-</td>");
          					  %>
          					   <%
          					  	if (Integer.parseInt(code)==10) 
          					  		out.println("<td>"+obj.getValue("PMS_SCH_MASTER","SCH_NAME"," where project_id="+bsno)+"( "+bsno+" )</td>");
          					  	else if (Integer.parseInt(code)==14) out.println("<td>"+obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME"," where BENEFICIARY_SNO="+bsno)+"( "+bsno+" )</td>");
          					  	else out.println("<td>-</td>");
          					  %>
          					   <td><%=rs_local.getString("ACCOUNT_HEAD_CODE")%></td>
          					    <td><%=vamt%></td>
          					    <td><%=drcr%></td>
          					  <td><%=PARTICULARS%></td>
          					</tr>
          					<%     
         					}    					   
          				 
          			%>    				
          			<%}catch(Exception e) {
          				out.println(e);
          				
          			}
          	%></table></td> </tr>
          	<tr>
          		<td valign="middle" colspan="2" align="center">
          		<input type="button" class="fb2" value="Exit" onclick="javascript:window.close();"> 
          	</td>
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
