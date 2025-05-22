<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1" import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.awt.image.RescaleOp"%>
<%@page import="java.sql.ResultSet"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Receipt SubLedger Code Correction</title>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
	function load(project_id)
	{
		document.getElementById("project_id").value=project_id;
	}
	function back()
	{
		
		window.close();
		
	}
</script>
 <script type="text/javascript" src="../scripts/troubleshoot.js"></script>
</head>  
<body>
<form action="Receipt_Trouble_shoot.jsp" method="get" id="recp">

<%
		String userid="",Office_id="",oid="",smonth="",syear="";
		
		Controller obj=null,obj2=null;;
		java.sql.Connection con;
		try
		{
				String new_cond=Controller.new_cond;
	  			obj = new Controller();
	  			obj2= new Controller();
				con = obj.con();
				obj2.createStatement(con);
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
					smonth=obj.setValue("smonth",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
					syear=obj.setValue("syear",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
					
					smonth=obj.setValue("smonth",request);
					syear=obj.setValue("syear",request);
					
					
					String process_month=smonth;
					String process_year=syear;
				  	obj.recp_month=smonth;				  
				  	obj.recp_year=process_year;
				  	obj.recp_off=Office_id;  
					//String qry=obj.rec_trouble();
					String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};

					%>
					<table width="100%" border=1 style="border-collapse: collapse" cellpadding="5" cellspacing="0">
<tr>
	<td colspan="3" align="center">
			<font size=4 color="darkgreen" ><b>Receipt SubLedger Code Correction</b></font>
	</td>
</tr>
<tr>
	<td colspan="3" align="right">
			<input type="button" onclick="back()" value="Close" style="color: darkred;font-weight: bold" >
	</td>
</tr> 
<tr style="background-color: ">
	<td align="center" >
		<font size=4> Receipt Details of <%=monthArr[Integer.parseInt(obj.recp_month)]%>&nbsp;<%=obj.recp_year %> </font>
	</td> 
	<td align="center" colspan="2"  >
		<font size=4> Beneficiary Scheme Details</font>
	</td>
</tr>
					
					<%
					//a.accounting_for_office_id=5075 and   
					  	out.println("recp_no"  );
					String qry=" select a.cashbook_month,a.cashbook_year,a.accounting_for_office_id,a.receipt_no as RECEIPT_NO,a.sub_ledger_code as BENEFICIARY_SNO   , b.amount,b.sub_ledger_type_code ,b.sub_ledger_code from  FAS_RECEIPT_TRANSACTION b, FAS_RECEIPT_MASTER a "+  
						" where    " + 
						" a.cashbook_month=5  and a.cashbook_year=2013 and a.receipt_status='L' and a.sub_ledger_type_code=14 and "+  
						" a.accounting_for_office_id=b.accounting_for_office_id and  "+
						" a.cashbook_month=b.cashbook_month and "+
						" a.cashbook_year=b.cashbook_year  and  "+
						" a.receipt_no=b.receipt_no    "+
						" and b.sub_ledger_code not  in (select project_id from PMS_MST_PROJECTS_VIEW where sch_sno in (Select scheme_sno from pms_dcb_mst_beneficiary_metre where office_id=a.accounting_for_office_id and beneficiary_sno=a.sub_ledger_code )) "+
						 " order by a.receipt_no "; 
 
						qry="SELECT SL_NO,CASHBOOK_YEAR,RECEIPT_NO,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,SUB_LEDGER_CODE,AMOUNT FROM FAS_RECEIPT_TRANSACTION where cashbook_month=5  and cashbook_year=2013 and accounting_for_office_id=5898";
					  
			String qry_res="";
			qry_res="<tr>";
			String bname="",ben_sno="",office_id="",recp_no="",amt="";
			int row=0;
				ResultSet rs=obj.getRS(qry);  
				while(rs.next())  
				{
					row++;
					obj.recp_month=obj.isNull(rs.getString("cashbook_month"),1);;				  
				  	obj.recp_year=obj.isNull(rs.getString("cashbook_year"),1);;
				  	Office_id=obj.isNull(rs.getString("accounting_for_office_id"),1);
				  	recp_no=obj.isNull(rs.getString("RECEIPT_NO"),1);	
				  	out.println("recp_no" + recp_no);
					ben_sno=obj.getValue("FAS_RECEIPT_MASTER","SUB_LEDGER_CODE"," where cashbook_month="+obj.recp_month+" and cashbook_year="+obj.recp_year+" and accounting_for_office_id="+Office_id+" and receipt_no="+recp_no);//obj.isNull(rs.getString("BENEFICIARY_SNO"),1);
					 
				  	obj.recp_off=Office_id;    		
									
					amt=obj.isNull(rs.getString("AMOUNT"),1);  
					
					qry_res+="<tr><td  align=left><table width='50%' style='border-collapse: collapse'><tr style='background-color:#CCCC99'><td width='40%'>Beneficiary Name</td><td>"+bname+"("+ben_sno+")"+"</td></tr>";  
					qry_res+="<tr><td width='40%'>Receipt No</td><td>"+recp_no+"</td></tr>";					
					qry_res+="<tr><td width='40%'>Amount</td><td>"+amt+"</td></tr></table><td><tr>";  
					qry_res+="<td   width='50%'  valign='top'><table border='1' cellpadding='5' width='100%' style='border-collapse: collapse'><tr><td width='5%'>Sl.No </td><td>Scheme Name </td> <td  width='15%'>  Project ID </td> <td width='10%'> Amount</td></tr>";
					String qry2="select  *  from (SELECT  SL_NO	,CASHBOOK_YEAR,RECEIPT_NO,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,SUB_LEDGER_CODE, AMOUNT FROM FAS_RECEIPT_TRANSACTION "+
							" WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and  ACCOUNT_HEAD_CODE in (SELECT ACCOUNT_HEAD_CODE  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE in (7,8,9) ) and  CASHBOOK_YEAR="+obj.recp_year+" AND CASHBOOK_MONTH="+obj.recp_month+" AND SUB_LEDGER_TYPE_CODE=10 and RECEIPT_NO="+recp_no+
							"   ) trans left JOIN ( SELECT SCH_SNO,PROJECT_NAME as SCH_NAME, PROJECT_ID FROM PMS_MST_PROJECTS_VIEW"+
							 " ) sch ON sch.SCH_SNO<>trans.SUB_LEDGER_CODE ";
					  
					 String ac="",cy="",cm="",recp="",slno="";
					ResultSet rs2=obj2.getRS(qry2);
					while(rs2.next()) 
					{  
						recp=obj2.isNull(rs2.getString("RECEIPT_NO"),1);
						ac=obj2.isNull(rs2.getString("ACCOUNTING_FOR_OFFICE_ID"),1);
						cy=obj2.isNull(rs2.getString("CASHBOOK_YEAR"),1);  
						cm=obj2.isNull(rs2.getString("CASHBOOK_MONTH"),1);
						slno=obj2.isNull(rs2.getString("SL_NO"),1);
						qry_res+="<tr><td>"+slno+"</td>";
						qry_res+=" <td>"+obj2.isNull(rs2.getString("SCH_NAME"),1)+"</td>";  
						qry_res+="<td>"+obj2.isNull(rs2.getString("PROJECT_ID"),1)+"</td>";					
						qry_res+="<td>"+obj2.isNull(rs2.getString("AMOUNT"),1)+"</td> ";
						qry_res+="<td width='5%'><input type='button' value='Submit' onclick=trouble_shoot("+ac+","+cy+","+cm+","+recp+","+slno+")></td></tr>";
						
					}	  
					qry_res+="</table></td><td width='50%' valign='top'><table width='100%' style='border-collapse: collapse' border=1 cellpadding='2'><tr style='background-color:#66669C'><td><font color=white>Check</font> </td><td><font color=white>Scheme Name</font> </td> <td> <font color=white>Project ID</font> </td>  </tr>";
					qry2="SELECT sch.SCH_NAME,sch.PROJECT_ID,met.SCHEME_SNO FROM (SELECT BENEFICIARY_SNO,OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY  WHERE status='L' "+
						  " and OFFICE_ID="+Office_id+"  AND BENEFICIARY_SNO="+ben_sno+" ) ben JOIN (SELECT distinct SCHEME_SNO,BENEFICIARY_SNO,OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY_METRE "+
						  " WHERE meter_status ='L' AND BENEFICIARY_SNO="+ben_sno+" ) met ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO AND met.OFFICE_ID=ben.OFFICE_ID "+
						  " JOIN  ( SELECT SCH_SNO,PROJECT_NAME as SCH_NAME, PROJECT_ID FROM PMS_MST_PROJECTS_VIEW   )sch ON sch.SCH_SNO=met.SCHEME_SNO ";
				   
					int r1=0;
					String project_id="";
					String SCHEME_SNO="";
					rs2=obj2.getRS(qry2);      
					while(rs2.next()) 
					{
						SCHEME_SNO=obj2.isNull(rs2.getString("SCHEME_SNO"),1);
						project_id=obj2.isNull(rs2.getString("PROJECT_ID"),1);
						r1++;
						qry_res+="<tr><td width='5%'><input type=checkbox id='ch"+r1+"' onchange=load("+project_id+")></td>";
						qry_res+="<td>"+obj2.isNull(rs2.getString("SCH_NAME"),1)+"("+SCHEME_SNO+")</td>";  
						qry_res+="<td width='10%'>"+project_id+"</td></tr>";
						 
					} 
					qry_res+="</table> </td>"; 					
				}	
				qry_res+="</tr></table>";
				out.println(qry_res);
		}catch(Exception e)  
		{
				out.println(e);
		}
%>

</table>
<input type="hidden" id="project_id" name="project_id" value="0">
</form>
</body>
</html>