<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<title> Beneficiary Receipt Collection | TWAD Nest - Phase II</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
<script type="text/javascript">
function rld()
{
  
  document.forms["receipt"].submit();
  
}
function callReport(pro)
{
  var option=document.getElementById('pr_type').value;
  var fyear=document.getElementById('fyear').value;
  var fmonth=document.getElementById('fmonth').value;
   window.open("../../../../../Bill_Demand?command=recpdf&fmonth="+fmonth+"&fyear="+fyear+"&option="+option+"&pro="+pro);
}
</script>
</head>
<%
		String new_cond=Controller.new_cond;
		
		Calendar cal = Calendar.getInstance();
		String inp_month="",inp_year="";
		int month = 0;//cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con;
		String smonth="",syear="",html="",SUB_LEDGER_CODE="";
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
		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");		 
		 
	 
		String yr=obj.setValue("year",request);
		String mt=obj.setValue("month",request);
 		inp_month=mt;
		inp_year=yr;
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
			  " AND SUB_LEDGER_CODE  in (select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY where "+new_cond+" OFFICE_ID="+Office_id+" and BENEFICIARY_TYPE_ID=18) " +
			  " and CASHBOOK_YEAR= "+inp_year+
			  " and CASHBOOK_MONTH="+inp_month+
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
		table_heading=" Beneficiary Full Deposit Receipt Collection - "+Office_Name+ "";
		String tab="cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
		html=obj.genReport(qry,table_header,table_column,tab,table_heading,table_td_set);
		con.close();  
		}catch (Exception e) {out.println(e);}
		
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		 

%>   	<link href="../../../../../css/txtbox.css" rel="stylesheet"  media="screen"/>
		<body onload=" select_ben('0','bentype','3'),select_ben('0','subdiv','5')">
		<form name="receipt" action="Full_D_receipt.jsp">
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%></title>
		<Table       id="" width="95%" align="center" border="1"   class="alerts2"  cellspacing="0" cellpadding="3" >
		<label name="msg" id="msg"></label>
		<tr>
					<td colspan=2><center><font size=2><%=table_heading%></font></center></td>
				</tr>  
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
			 <Td  colspan="1"><select class="select" id="month" name="month"  style="width:180pt;"   onchange="rld()">
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
		 
    	<Tr><td colspan=2><%=html%></td></Tr>
		 
		<tr><td colspan=2 align="center"> &nbsp;<input class="fb2" type="button" id="" value="Exit" onclick="javascript:window.close()" style="font-style: italic;color: red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td></tr>
		</Table>
		<input type=hidden id="pr_status" name="pr_status" value="0">
		<input type=hidden id="ben" name="ben" value="<%=SUB_LEDGER_CODE%>">
		<input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	<input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input>
       	</form>  
</body>
</html>