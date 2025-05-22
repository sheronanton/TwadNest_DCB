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
	function load_new( )
	{
		 window.open("../../../../../../Receipt_Trouble_shoot");
	}
</script>
 <script type="text/javascript" src="../scripts/troubleshoot.js"></script>
</head>  
<body>
<form action="Receipt_Trouble_shoot.jsp" method="get" id="recp">
<div id='scroll_clipper' style='position:relative; width:95%; border-height:1px; height: 620px; overflow:auto;white-space:nowrap;'>
<div id='scroll_text'  >
<%
		String userid="",Office_id="",oid="",smonth="",syear="",Office_name="";
		
		Controller obj=null,obj2=null,obj3=null;
		java.sql.Connection con;
		try
		{
				String new_cond=Controller.new_cond;
	  			obj = new Controller();
	  			obj2= new Controller();
	  			obj3= new Controller();
				con = obj.con();
				obj2.createStatement(con);
				obj.createStatement(con);
				obj3.createStatement(con);
				 try
					{
					   userid=(String)session.getAttribute("UserId");
					}catch(Exception e) {userid="0";}
					if(userid==null)
					{ 
						 response.sendRedirect(request.getContextPath()+"/index.jsp");
					}
					Office_id=obj.setValue("office_id",request);//obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
					smonth=obj.setValue("fmonth",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
					syear=obj.setValue("fyear",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
					Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
					
					 
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
	<td>
		<%=Office_name%>
	</td>
	<td colspan="2" align="right">
			<input type="button" onclick="back()" value="Close" style="color: darkred;font-weight: bold" >
	</td>
</tr>

<tr style="background-color: skyblue ">
	<td align="center" >
		<font size=4> Receipt Details of <%=monthArr[Integer.parseInt(obj.recp_month)]%>&nbsp;<%=obj.recp_year %> </font>
	</td> 
	<td align="center" colspan="2"  >
		<font size=4> Beneficiary Scheme Details</font>
	</td>
	
	</tr>
					
					<%
					
					String qry=" SELECT ben.BENEFICIARY_SNO,ben.OFFICE_ID, mas.TOTAL_AMOUNT,  trans.AMOUNT,  mas.RECEIPT_NO,  ben.BENEFICIARY_NAME "+ 
					" FROM "+
						" (SELECT ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT "+
								  " FROM FAS_RECEIPT_TRANSACTION WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and  ACCOUNT_HEAD_CODE in (SELECT ACCOUNT_HEAD_CODE  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE in (7,8,9) ) and CASHBOOK_YEAR="+obj.recp_year+" AND CASHBOOK_MONTH="+obj.recp_month+" AND SUB_LEDGER_TYPE_CODE=10 "+
						  " and (SUB_LEDGER_CODE=0 or SUB_LEDGER_CODE is null  )     "+
						  " )trans "+
						  " JOIN "+    
						" (SELECT ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE ,TOTAL_AMOUNT "+
								  " FROM FAS_RECEIPT_MASTER WHERE CASHBOOK_YEAR="+obj.recp_year+" AND CASHBOOK_MONTH="+obj.recp_month+" AND SUB_LEDGER_TYPE_CODE=14 AND receipt_status='L'      "+
							   " ) mas "+
						  " ON mas.ACCOUNTING_FOR_OFFICE_ID=trans.ACCOUNTING_FOR_OFFICE_ID AND mas.CASHBOOK_YEAR= trans.CASHBOOK_YEAR "+
						" AND mas.CASHBOOK_MONTH=trans.CASHBOOK_MONTH AND mas.RECEIPT_NO=trans.RECEIPT_NO "+
						" JOIN "+
						" (SELECT BENEFICIARY_SNO,BENEFICIARY_NAME,OFFICE_ID "+
								  " FROM PMS_DCB_MST_BENEFICIARY WHERE status='L'   "+
							   " ) ben "+
						  " ON ben.BENEFICIARY_SNO=mas.SUB_LEDGER_CODE AND mas.ACCOUNTING_FOR_OFFICE_ID  =ben.OFFICE_ID "+
						" AND trans.ACCOUNTING_FOR_OFFICE_ID=ben.OFFICE_ID  "+
						" ORDER BY mas.ACCOUNTING_FOR_OFFICE_ID,RECEIPT_NO";
						
				 
			String qry_res="";
			qry_res="<tr>";
			String bname="",ben_sno="",office_id="",recp_no="",amt="";
			int row=0;
				ResultSet rs=obj.getRS(qry);
				while(rs.next())  
				{
					row++;
					ben_sno=obj.isNull(rs.getString("BENEFICIARY_SNO"),1);
					bname=obj.isNull(rs.getString("BENEFICIARY_NAME"),1);				
					recp_no=obj.isNull(rs.getString("RECEIPT_NO"),1);					
					amt=obj.isNull(rs.getString("AMOUNT"),1);  
					
					qry_res+="<tr><td  align=left><table width='50%' style='border-collapse: collapse'><tr style='background-color:#CCCC99'><td width='40%'>Beneficiary Name</td><td>"+bname+"("+ben_sno+")"+"</td></tr>";  
					qry_res+="<tr><td width='40%'>Receipt No</td><td>"+recp_no+"</td></tr>";					
					qry_res+="<tr><td width='40%'>Amount</td><td>"+amt+"</td></tr></table></td></tr><tr>";     
					qry_res+="<td   width='50%'  valign='top'><table border='1' cellpadding='5' width='100%' style='border-collapse: collapse'><tr><td width='5%'>Sl.No </td><td>Scheme Name </td> <td  width='15%'>  Project ID </td> <td width='10%'> Amount</td></tr>";
					String qry2="select  *  from (SELECT  SL_NO,CASHBOOK_YEAR,RECEIPT_NO,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,SUB_LEDGER_CODE, AMOUNT FROM FAS_RECEIPT_TRANSACTION "+
							" WHERE ACCOUNTING_FOR_OFFICE_ID="+Office_id+" and  ACCOUNT_HEAD_CODE in (SELECT ACCOUNT_HEAD_CODE  FROM PMS_DCB_RECEIPT_ACCOUNT_MAP where COLLECTION_TYPE in (7,8,9) ) and  CASHBOOK_YEAR="+obj.recp_year+" AND CASHBOOK_MONTH="+obj.recp_month+" AND SUB_LEDGER_TYPE_CODE=10 and RECEIPT_NO="+recp_no+
							" AND (SUB_LEDGER_CODE=0 OR SUB_LEDGER_CODE IS NULL)  ) trans left JOIN ( SELECT SCH_SNO,SCH_NAME, PROJECT_ID FROM PMS_SCH_MASTER"+
							 " ) sch ON sch.SCH_SNO=trans.SUB_LEDGER_CODE ";
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
					qry_res+="</table></td><td width='50%' valign='top'><table width='100%' style='border-collapse: collapse' border=1 cellpadding='2'><tr style='background-color:#66669C'><td><font color=white>Check</font> </td><td><font color=white>Correct Scheme Name</font> </td> <td> <font color=white>Project ID</font> </td>  </tr>";
					qry2="SELECT sch.SCH_NAME,sch.PROJECT_ID,met.SCHEME_SNO FROM (SELECT BENEFICIARY_SNO,OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY  WHERE status='L' "+
						  " and OFFICE_ID="+Office_id+"  AND BENEFICIARY_SNO="+ben_sno+" ) ben JOIN (SELECT distinct SCHEME_SNO,BENEFICIARY_SNO,OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY_METRE "+
						  " WHERE meter_status ='L' AND BENEFICIARY_SNO="+ben_sno+" ) met ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO AND met.OFFICE_ID=ben.OFFICE_ID "+
						  " JOIN  ( SELECT SCH_SNO,SCH_NAME, PROJECT_ID FROM PMS_SCH_MASTER   )sch ON sch.SCH_SNO=met.SCHEME_SNO ";
				 
					int r1=0;
					String project_id="";
					String SCHEME_SNO="";
					rs2=obj3.getRS(qry2);    
					while(rs2.next()) 
					{
						SCHEME_SNO=obj3.isNull(rs2.getString("SCHEME_SNO"),1);
						project_id=obj3.isNull(rs2.getString("PROJECT_ID"),1);
						r1++;
						qry_res+="<tr><td width='5%'><input type=checkbox id='ch"+r1+"' onchange=load("+project_id+")></td>";
						qry_res+="<td>"+obj3.isNull(rs2.getString("SCH_NAME"),1)+"("+SCHEME_SNO+")</td>";  
						qry_res+="<td width='10%'>"+project_id+"</td></tr>";
						 
					} 
					qry_res+="</table> </td>"; 					
				}	
				qry_res+="</tr>";
				 
					 
			 	out.println(qry_res);   
		}catch(Exception e)    
		{
				out.println(e);
		}   
%>
   
</table></div></div>

<input type="hidden" id="project_id" name="project_id" value="0">
</form>
</body>
</html>