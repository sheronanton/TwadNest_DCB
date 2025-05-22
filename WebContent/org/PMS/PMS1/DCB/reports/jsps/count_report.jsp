<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
	<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<script type="text/javascript"> 
			function callReport()
			{
				 var fyear=document.getElementById('fyear').value;
				 var fmonth=document.getElementById('fmonth').value;	 
			     window.open("../../../../../Bill_Demand?command=prmispdf&fmonth="+fmonth+"&fyear="+fyear);
			}
			</script> 
			<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
	</head>
<%
		String new_cond=Controller.new_cond;
		String regname="";
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con;
		String smonth="",syear="",html="";
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
		String reg=obj.setValue("reg",request);
		  regname=obj.setValue("regname",request);
		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		if (Office_id.equals("")) Office_id="0";
		Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " and OFFICE_LEVEL_ID='DN'");
		smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
		syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		String qry="SELECT " +
		" Off1.OFFICE_NAME as reg, " +
		"  off2.OFFICE_NAME as div, " +
		"   pr.prvcount, " +
		"   ben1.ben1count, " +
		"   ben2.ben2count, " +
		"   DECODE( ben1.ben1count,NULL,0, ben1.ben1count) AS  ben1count, " +
		"   DECODE( ben2.ben2count,NULL,0, ben2.ben2count) AS  ben2count, " +
		"   DECODE( met1.met1count,NULL,0, met1.met1count)+  " +
		"   DECODE( met2.met2count,NULL,0, met2.met2count) as totmet ,  " +	 	
		"   metfr.metfrcount, " +
		"   metnfr.metnfrcount, " +
		"   rate.ratecount,off2.OFFICE_ID, " +
		"   ratenot.ratenotcount,Aid.ACCOUNTING_UNIT_ID " +
		" FROM ( " + 
		"   (SELECT REGION_OFFICE_ID, " +
		"     OFFICE_LEVEL_ID, " +
			    "     OFFICE_ID, " +
			    "     OFFICE_NAME " +
			    "   FROM COM_MST_ALL_OFFICES_VIEW " +
			    "   WHERE OFFICE_LEVEL_ID='RN' and OFFICE_ID="+reg+
			    "   )Off1 " +
			    " JOIN " +
			    " (SELECT REGION_OFFICE_ID, " +
			    "    OFFICE_LEVEL_ID, " +
			    "    OFFICE_ID, " +
			    "    OFFICE_NAME " +
			    "  FROM COM_MST_ALL_OFFICES_VIEW " +
			    "  WHERE OFFICE_LEVEL_ID='DN' and OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP) " +
			    "   )off2 " +
			    " ON off2.REGION_OFFICE_ID=off1.OFFICE_ID " +
			    " left JOIN " +
			    "   ( SELECT OFFICE_ID ,COUNT(*) as prvcount  FROM COM_MST_PRIVATE GROUP BY OFFICE_ID " +
			    "   )pr " +
			    " ON pr.OFFICE_ID=off2.OFFICE_ID " +
			    " left JOIN " +
			    "   ( SELECT OFFICE_ID ,COUNT(*) as ben1count  FROM PMS_DCB_MST_BENEFICIARY  where "+new_cond+" BENEFICIARY_TYPE_ID <=6 GROUP BY OFFICE_ID " +
			    "   )ben1 " +
			    " ON ben1.OFFICE_ID=off2.OFFICE_ID " +
			    " left JOIN " +
			    "   ( SELECT OFFICE_ID ,COUNT(*) as ben2count  FROM PMS_DCB_MST_BENEFICIARY  where "+new_cond+" BENEFICIARY_TYPE_ID >6 GROUP BY OFFICE_ID " +
			    "   )ben2 " +
			    " ON ben2.OFFICE_ID=off2.OFFICE_ID " +
			    " left JOIN " +
			    "   ( SELECT OFFICE_ID ,COUNT(*) as met1count  FROM PMS_DCB_MST_BENEFICIARY_METRE  where BENEFICIARY_TYPE_ID <=6 GROUP BY OFFICE_ID " +
			    "   )met1 " +
			    " ON met1.OFFICE_ID=off2.OFFICE_ID " +
			    " left JOIN " +
			    "   ( SELECT OFFICE_ID ,COUNT(*) as met2count  FROM PMS_DCB_MST_BENEFICIARY_METRE  where BENEFICIARY_TYPE_ID >6 GROUP BY OFFICE_ID " +
			    "   )met2 " +
			    " ON met2.OFFICE_ID=off2.OFFICE_ID " +
			    " left JOIN " +
			    "   ( SELECT OFFICE_ID ,COUNT(*) as metfrcount  FROM PMS_DCB_MST_BENEFICIARY_METRE  where SETTING_FLAG ='FR' GROUP BY OFFICE_ID " +
			    "   )metfr " +
			    " ON metfr.OFFICE_ID=off2.OFFICE_ID " +
			    " left JOIN " +
			    "   ( SELECT OFFICE_ID ,COUNT(*) as metnfrcount  FROM PMS_DCB_MST_BENEFICIARY_METRE  where (SETTING_FLAG !='FR' or SETTING_FLAG is null) GROUP BY OFFICE_ID " +
					  "   )metnfr " +
					  " ON metnfr.OFFICE_ID=off2.OFFICE_ID " +
					  " left JOIN " +
					  "   ( SELECT OFFICE_ID ,COUNT(*) as ratecount  FROM PMS_DCB_MST_BENEFICIARY_METRE  where TARIFF_RATE is not null GROUP BY OFFICE_ID " +
					  "   )rate " +
					  " ON rate.OFFICE_ID=off2.OFFICE_ID " +
					  " left JOIN " +
					  "   ( SELECT OFFICE_ID ,COUNT(*) as ratenotcount  FROM PMS_DCB_MST_BENEFICIARY_METRE  where TARIFF_RATE is   null GROUP BY OFFICE_ID " +
					  "  )ratenot " +
			  " ON ratenot.OFFICE_ID=off2.OFFICE_ID " +		 
		        " "+ 
		        "      join ( "+ 
		        " select  "+
		        		" ACCOUNTING_UNIT_ID ,ACCOUNTING_UNIT_OFFICE_ID"+ 
		        			" from  "+
		        		" FAS_MST_ACCT_UNITS "+
		        			" 	)Aid "+
						" on Aid.ACCOUNTING_UNIT_OFFICE_ID=off2.OFFICE_ID "+
		 		")"; 
		 
		 
		  
		 
		 
 		table_column="div,OFFICE_ID,ACCOUNTING_UNIT_ID,prvcount,ben1count,ben2count,totmet,metfrcount,metnfrcount,ratecount,ratenotcount";
 		table_header="Division,ID,ACC.ID,Private Master,Local Body,Private Beneficiary,Meter,Tariff Setting Freeze,Tariff Setting Not Freeze,Rate Entered,Rate Not Entered  ";
		String table_td_set="";
 		table_td_set="width='20%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%',align=center width='5%'";
		table_heading="DCB Record Count Report Region Wise ";
		String tab="cellspacing='0' cellpadding='3' width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'";
		html=obj.genReport(qry,table_header,table_column,tab,table_heading,table_td_set); 
		
		con.close(); 
		}catch (Exception e) {out.println(e);
	 
			
		}
%> 

		<body> 
		<link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
		<title><%=table_heading%>  | TWAD Nest - Phase II </title>
		<Table class="fb2"    id="" width=100% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
				<tr>
					<td colspan=2><center><%=table_heading%></center></td>
				</tr>
				<tr>
					<td colspan=2><center><%=regname%></center></td>
				</tr>
				 
				 
				<Tr>
					<td colspan=2><%=html%></td>
				</Tr>
				<tr>
					<td colspan=2 align="center">  <input class="fb2" type="button" id="" value="Exit" onclick="javascript:window.close()" /></td>
				</tr> 
		</Table>
		 <input type="hidden" id="fyear" name="fyear" value="<%=syear%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=smonth%>' ></input> 
</body>
 

</html>