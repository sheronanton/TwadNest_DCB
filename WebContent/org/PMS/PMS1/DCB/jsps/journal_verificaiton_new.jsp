<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.text.DecimalFormat"%><html>
<head>
<%@page import="java.util.*,java.sql.*" %>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../../../../../css/AME.css" rel="stylesheet"  media="screen"/>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<title> DCB Journal Adjustments Verification</title>
<%
/* 
	if  Journal Master --  JOURNAL_TYPE_CODE  56,57,53,75,87,66,54
			SUB_LEDGER_TYPE_CODE=14
*/

 String new_cond=Controller.new_cond;
 String amt="",billsno="",inp_month="",inp_year="",combo="";
 int row=0; 
 Controller obj=null;
	Connection con;
	obj=new Controller();
	con=obj.con();
 	obj.createStatement(con);
 	
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			 int prvmonth=obj.prv_month(year,month);
			 int prvyear=obj.prv_year(year,month);
			String userid="0",Office_id="",Office_Name="";
			String Date_dis=day+"/"+month+"/"+year;
		//	Controller obj=new Controller();
		//	Connection con;
			try
			{
			  con=obj.con();
			  obj.createStatement(con);
			  userid=(String)session.getAttribute("UserId");			
			  if(userid==null)
			  {
				 	response.sendRedirect(request.getContextPath()+"/index.jsp");
			  }
			
	
			  String ben=obj.setValue("ben",request);
		//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		
			if (Office_id.equals("")) Office_id="0";
			String off_details="";
			String yr="0";
			String mt="0";
			try{
				
		 mt= obj.setValue("mt",request);
		 yr= obj.setValue("yr",request);

			//	 yr=request.getParameter("yr");
			//	 mt=request.getParameter("mt");		
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				 yr=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR"," where OFFICE_ID="+Office_id),1);;
				 mt=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);;

			}
			

				
			
		//	String yr=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR"," where OFFICE_ID="+Office_id),1);;
		//      String yr=obj.setValue("year",request);
		//	String mt=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);;
		//     String mt= obj.setValue("month",request);
			if (Office_id.equalsIgnoreCase("0")) off_details="";
			else
				off_details=" and b.ACCOUNTING_FOR_OFFICE_ID="+Office_id;
				if (yr.equalsIgnoreCase("0"))  
				{ 
					mt=Integer.toString(month);
			     	yr=Integer.toString(year);
				}
			inp_month=mt;  
			inp_year=yr;

			int inp_month1= Integer.parseInt(mt);  
			int inp_year1=Integer.parseInt(yr);  
		
			Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};				 
		 	String A_u_id=obj.getValue("FAS_MST_ACCT_UNITS","ACCOUNTING_UNIT_ID","where ACCOUNTING_UNIT_OFFICE_ID="+Office_id);
		 	String cb=obj.combo_str("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME","BENEFICIARY_SNO"," where "+new_cond+" OFFICE_ID="+Office_id+" and BENEFICIARY_SNO in  ( SELECT SUB_LEDGER_CODE from FAS_JOURNAL_TRANSACTION where CASHBOOK_YEAR="+inp_year+" and CASHBOOK_MONTH="+inp_month+"  ) order by BENEFICIARY_TYPE_ID,BENEFICIARY_NAME","ben",ben,"onchange=rld() style='width:180pt;'   class=select");		 		 
		 	ResultSet rs_local=null;
		 	String qry1="SELECT a.PARTICULARS,a.CR_DR_INDICATOR,a.ACCOUNTING_FOR_OFFICE_ID,a.CASHBOOK_YEAR,a.CASHBOOK_MONTH, a.ACCOUNT_HEAD_CODE, a.AMOUNT, a.SUB_LEDGER_TYPE_CODE AS tran_subledgertypecode, "
		 		 		+" a.SUB_LEDGER_CODE AS tran_sub_ledger,b.VOUCHER_NO,to_char(b.VOUCHER_DATE,'dd/MM/YYYY') as VOUCHER_DATE,b.JOURNAL_TYPE_CODE,b.SUB_LEDGER_CODE AS Master_Subledgercode,b.REMARKS "
		 		 		+" FROM FAS_JOURNAL_MASTER b,FAS_JOURNAL_TRANSACTION a  "
		 		 		+" WHERE b.cashbook_month=a.cashbook_month AND b.cashbook_year=a.cashbook_year AND b.voucher_no=a.voucher_no "
		 		 		+" AND b.accounting_for_office_id=a.accounting_for_office_id AND b.journal_status='L' AND (a.account_head_code IN "
		 		 		+" (SELECT DISTINCT ACCOUNT_HEAD_CODE  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP  WHERE acc_type in ('C','D')  ))   and b.cashbook_month="+mt+" and b.cashbook_year="+yr +" "+off_details+ " and  a.SUB_LEDGER_TYPE_CODE <> 10  order by SUB_LEDGER_TYPE_CODE";
	 		rs_local=obj.getRS(qry1);
	 		String sub=obj.setValue("store",request);
	 		int count_2=0;
	 		count_2=obj.getCount("PMS_DCB_FREEZE_RECEIPT","where JOURNAL_FREEZE='Y' and OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+inp_month+" and CASHBOOK_YEAR="+inp_year);
	 		if(sub.equalsIgnoreCase("Ok") || sub.equalsIgnoreCase("Verify Journal"))
	 		{
			 	
	 			count_2=obj.getCount("PMS_DCB_FREEZE_RECEIPT","where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+inp_month+" and CASHBOOK_YEAR="+inp_year);
	 			if (count_2==0)
			 	{
					//obj.delRecord("PMS_DCB_FREEZE_RECEIPT"," where OFFICE_ID="+Office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year,con);
					Hashtable ht=new Hashtable();
					ht.put("OFFICE_ID",Office_id);
					ht.put("CASHBOOK_MONTH",month);
					ht.put("CASHBOOK_YEAR",year);
					ht.put("JOURNAL_FREEZE","'Y'");
					ht.put("JOU_UPDATED_DATE","clock_timestamp()");
					ht.put("UPDATED_DATE","clock_timestamp()");
					ht.put("JOU_UPDATED_BY_USER_ID","'"+userid+"'");
					int r=obj.recordSave(ht,"PMS_DCB_FREEZE_RECEIPT",con);
						if (r >0 )
						{
			  			%>
			  			<script type="text/javascript">
			  			alert('Sucessfully Verified..')   
			  			</script>
			  			<%
						}  
			 	}else
			 	{
	 				Hashtable ht = new Hashtable();
					Hashtable condht = new Hashtable();
					ht.put("JOURNAL_FREEZE", "'Y'");
					ht.put("JOU_UPDATED_DATE","clock_timestamp()");
					ht.put("JOU_UPDATED_BY_USER_ID","'"+userid+"'");
					condht.put("OFFICE_ID", Office_id);
					condht.put("CASHBOOK_MONTH", inp_month);
					condht.put("CASHBOOK_YEAR", inp_year);		
					int r=obj.recordSave(ht,condht, "PMS_DCB_FREEZE_RECEIPT ",con);
					if (r >0 )
					{
		  			%>
		  			<script type="text/javascript">
		  			alert('Sucessfully Verified..')
		  			</script>
		  			<%
		  				
					}
			 	}
	 		}	
			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};

		%>  
		<script type="text/javascript">
		function rld()
		{
			document.forms["othercharges"].submit();
		}  
		function troubleshoot()
		{  
			  var fyear=document.getElementById('year').value;
			  var fmonth=document.getElementById('month').value;		   
			  s=window.open("../reports/jsps/journal_demand.jsp?syear="+fyear+"&smonth="+fmonth,"","","");
			    
		}  
		</script>
</head>  
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/other_charges_collection.js"></script>
		<body onload="flash()">
		<form action="journal_verificaiton_new.jsp" method="get" name="othercharges">
		<table align="center" width="85%" cellpadding="5" cellspacing="0"  > 
		<tr class="tdH"> <th  valign="middle" colspan="2" align="center"> DCB Journal Adjustments Verification-<%=Office_Name%>  </th></tr>
		<tr  ></tr>
		<tr> 
		<td colspan="2">
		<table align="left" width="90%" cellpadding="0" cellspacing="0" border="0" >
		<tr>    
	          <td   align="left" class="tdText" width="10%" valign="top"><label class="lbl">Cash Book Year&nbsp; &nbsp;:</label> </td>
			  <td  width="20%" align="left"  valign="top">
			  
<select id="yr" name="yr" tabindex="5"  class="select" >
 	<option value="0" selected="selected">- - - Select - - -</option>
 	<%
 	   	for (int j=year;j>=2015;j--)
 	 	
 	{
 	%>
 	<option value="<%=j%>"  <% if (j==inp_year1) {%>selected<%} %>><%=j%></option>
 	<%} %>
			  
					 <label id="msg"></label> </td>		 
			 <td align="left" class="tdText" width="12%"  valign="top"><label class="lbl">Cash Book Month:</label></td>	  	          
			 <td align="left" width="20%"  valign="top">
			
 <select id="mt" name="mt"  class="select" onchange="rld()">

	 <%
	 	String[] amonth = { "-select month-", "January", "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December" };
	 	for (int i=0;i<=12;i++) 
	 	{
	 	%>
	 	<option value="<%=i%>" <% if (i==inp_month1) {%>selected<%} %> ><%=amonth[i]%></option>
 	
	 	<%} %>
	 </select>

			 </td>
			 </tr>
			 
     </table>
     </td></tr>      
         	<input type="hidden" id="pr_status" name="pr_status" value="1" />    
     <tr><td colspan="2" valign="top">
       	   <table  align="center" width="100%"   cellpadding="0" cellspacing="0"   >
		    <Tr>
		      <th align="center" width="2%">Sl.No</th>
			  <th align="center" width="10%">Voucher No and Date</th>
			  <th align="center" width="5%">Journal Type</th>
			  <th align="center" width="5%">Master <Br>SL Type Code  </th>
			  <th align="center" width="15%">Transaction <Br>SL Type Code  </th>
	          <th align="center" width="15%">SL Code</th>
	          <th align="center" width="5%">Account <bR>Head Code</th>			  
	          <th align="center" width="5%">Amount</th>
	          <th align="center" width="5%">CR / DR </th>
	          <th align="center">Status </th> 
	          <th align="center">Action </th>    
          	</tr>
          	<%
          			int c=0;
          			String code="",PARTICULARS="",vno="",vdate="",drcr="",vamt="",bsno="",Bensno="0";
          			String btype,typedesc="",JOURNAL_TYPE_CODE="",master_sub_ledger="";
          			double net_amt=0.0f;
          			String error_msg="",error_msg1="",action_msg="";
          			DecimalFormat df=new DecimalFormat("0.00");
          			int count=0;
          			try
          			{
          				int c2=0;
          				rs_local=obj.getRS(qry1);
          				code="";PARTICULARS="";vno="";vdate="";drcr="";vamt="";bsno=""; 
          				error_msg="";
          				String ben_name="";
      					String sch_name="";
      					String sch_type="";

      					String color="";
      					String status="";
          				while (rs_local.next())
          				{
          					
          					
          					vno="<b>"+rs_local.getString("VOUCHER_NO")+"</b>"+" &nbsp;Dt."+rs_local.getString("VOUCHER_DATE");          					
          					JOURNAL_TYPE_CODE=rs_local.getString("JOURNAL_TYPE_CODE");
          					drcr=rs_local.getString("CR_DR_INDICATOR");
          					vamt=rs_local.getString("AMOUNT");
          					net_amt+=Double.parseDouble(vamt);
          					master_sub_ledger=rs_local.getString("Master_Subledgercode");
          					bsno=rs_local.getString("tran_sub_ledger");
  					  		status=obj.getValue("PMS_DCB_MST_BENEFICIARY","status"," where BENEFICIARY_SNO="+bsno);
						 if (status.equals("L"))
						 {
          					code=rs_local.getString("tran_subledgertypecode");
          					row++;
          					PARTICULARS=rs_local.getString("PARTICULARS");
          					c2=0;
          					ben_name="";
          					sch_name="";
          					sch_type="";
          					error_msg="";
          					action_msg="";
          					%>  
          					<tr bgcolor="">
          					 <td align="center"><%=row%></td>    
          					 <td align="center"><%=vno%></td>
          					 <td align="center"><%=JOURNAL_TYPE_CODE%></td>
          					 <td>	
          					 	<%=master_sub_ledger%>
          					 </td>
          					  <%
          					  	if (Integer.parseInt(code)==10) 
          					  	{
          					  		out.println("<td>Scheme ( "+code+" )</td>");
          					  		///sch_name=obj.getValue("PMS_SCH_MASTER","SCH_NAME"," where project_id="+bsno);  		
          					  	sch_name=obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_NAME"," where project_id="+bsno);
          					  	}else if (Integer.parseInt(code)==0) 
          					  	{
          					  		out.println("<td>-</td>");
          					  		error_msg="<font color='red'>SL Type Code Missing</font>";
          					  		sch_name=obj.getValue("PMS_SCH_MASTER","SCH_NAME"," where project_id="+bsno); 
      								action_msg="<font color='blue'>Cancel this Voucher,Re-enter  </font>";
      								count++;
          					  	}
          					  else if (Integer.parseInt(code)==16) 
        					  	{
        					  		out.println("<td>Scheme type ( "+code+" )</td>");
        					  		sch_type = obj.getValue("pms_dcb_applicable_sch_type","sch_type_desc"," where sch_type_id="+bsno);
    								action_msg="<font color='blue'>Cancel this Voucher,Re-enter  </font>";
        					  	}
          					  	
          					  	
          					  	else if (Integer.parseInt(code)==14)
          					  	{ 
          					  		
          					  		out.println("<td>Beneficiary ( "+code+" )</td>");
          					  		ben_name=obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_NAME"," where BENEFICIARY_SNO="+bsno);
          					  	}
          					    else
          					    {
          					    	out.println("<td>-</td>");
          					    	sch_name="0";
          					    	ben_name="0";  
          					    }
          						if (sch_name.equalsIgnoreCase("0") || ben_name.equalsIgnoreCase("0"))
          						{
          							error_msg1="&nbsp;&nbsp;<font color='red'>SL Code Missing</font>";
          							action_msg="&nbsp;&nbsp;<font color='blue'>Cancel this Voucher,Re-enter  </font>";
          							count++;
          						}else
          						{
          							error_msg1="&nbsp;&nbsp;<font color=green>OK</font>";
          							action_msg="";
          						}
          					  %>
          					   <%
          					  	if (Integer.parseInt(code)==10) 
          					  		out.println("<td>"+sch_name+"( "+bsno+" )</td>");
          					  	else if (Integer.parseInt(code)==14) {out.println("<td>"+ben_name+"( "+bsno+" )</td>");}
          					  	else if (Integer.parseInt(code)==16) out.println("<td>"+sch_type+"( "+bsno+" )</td>");
          					  	else out.println("<td>-</td>");
          					  %>
          					   <td align="center"><%=rs_local.getString("ACCOUNT_HEAD_CODE")%></td>
          					    <td align="center"><%=vamt%></td>
          					    <td align="center"><%=drcr%></td>
          					    <td><%=error_msg%>,<%=error_msg1%></td>
          					    <td><%=action_msg%></td>
          					</tr>
          					<%     
         					} 
          				}
          				String label="Submit";
          				if (row==0)
          				{
          					out.println("<tr><td colspan=11 align='center'><font color='blue'>No Journal has been adjusted for the selected month and year</font> </td></td>");
          					label="OK";
          				}else
          				{
          					label="Verify Journal";
          				} 
          				String disable="";
          				 if (count_2!=0)
          					disable="disabled=disabled";
          				 else
          					disable="";
          			  
          	%></table></td> </tr>
          	 <Tr>
          	 	<td align="right">Total Amount</td>
          	 	<td align="right"><%=df.format(net_amt) %></td>
          	 </Tr>
          	<tr>
          		<th align="center" colspan="2"> 
 
         		<%
          		if (count==0) {%>
          		<input type="submit" id="" name="store" <%=disable%> value="<%=label%>"  >
          		
          		<% }else 
          			{
          			
        			out.println("<font color=red size=4> Errors Found in Journal are shown in Red.... Please Cancel those Journal and Re-Enter Journal");
          			}%>
          			
          			 <input type="submit" id="" name="store" <%=disable%> value="<%=label%>"  >
          				
          		<input type=button style="color: blue;" value="View DCB Journal Types" onclick="javascript:window.open('../reports/jsps/applicable_journal_types.jsp')"> 
          		<input type="button" class="fb2" value="Exit" onclick="javascript:window.close();"> 
          	</th>
          	</tr> 
			</table><input type="hidden" id="row_cnt" name="row_cnt" value="<%=row%>" />
			<a href="Head_Office_Trace.jsp">..</a>
			
			
		</form>  
		</body> 
</html>
<%  
con.close();
con=null;
	 
%>
    				
	<%}catch(Exception e) {
		out.println(e);
		}
				}catch(Exception e)
				{
					out.println(e);
				}
%>
