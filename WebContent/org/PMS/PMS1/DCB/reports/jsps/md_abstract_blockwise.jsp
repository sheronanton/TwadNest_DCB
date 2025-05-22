<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.DecimalFormat"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand,Collection and Balance Statement</title>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body >  
 <%
 String  Office_Name="",userid="",Office_id=""  ;
	DecimalFormat df = new DecimalFormat("0.00");  
	Connection db_con;
	Controller obj_new=new Controller();
	Controller obj_new2=new Controller();
	try
	{
		
		db_con=obj_new.con();
		obj_new.createStatement(db_con);
		String pmonth=obj_new.setValue("pmonth",request);
		String pyear=obj_new.setValue("pyear",request);
		String schtype=obj_new.setValue("schtype",request);
		String stype_value=obj_new2.getValue("PMS_DCB_APPLICABLE_SCH_TYPE", "SCH_TYPE_SUB_DESC","where SCH_TYPE_ID_SUB=" + schtype);
		String DISTRICT_CODE=obj_new.setValue("DISTRICT_CODE",request);
		String DISTRICT_CODE_value=obj_new2.getValue("COM_MST_DISTRICTS", "DISTRICT_NAME","where DISTRICT_CODE=" + DISTRICT_CODE);
		obj_new2.createStatement(db_con);
		String BEN_TYPE_GROUP="6";
		String ben_group_name=obj_new2.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_GROUP_NAME","where BEN_TYPE_GROUP="+BEN_TYPE_GROUP);
		String mvalue=obj_new.month_val(pmonth);
		String add_cond="",add_cond2="";
		if (Integer.parseInt(schtype)==1) 
			add_cond=" ";//and BEN_TYPE_GROUP not in (8) ";  
		else
			add_cond=" ";
		if (Integer.parseInt(schtype)==1) 
			add_cond2=" BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"";  
		else
			add_cond2=" BEN_TYPE_ID_SUB in (6) ";
		userid=(String)session.getAttribute("UserId");
		if(userid==null)  
		{
		  response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		Office_id=obj_new.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_Name = obj_new.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
		String off=Office_id.equals("5000")?"":" and Office_id="+Office_id;	 	
		Office_Name=Office_id.equals("5000")?"":""+Office_Name+"<br>";
		
 %> 
<table border=1  width="72%" cellpadding="5" cellspacing="0" style="border-collapse: collapse;"  align="center"> 
	<tr class='tdH'>
		<td colspan=6 align="center"><%=Office_Name%>Demand,Collection and Balance Statement 
		<br><%=stype_value%>-<%=ben_group_name%><br>
				<font size=2>(for the month of <%=mvalue%> <%=pyear%>)</font>   
				<br>
				<font size=2>(Rupees in Lakhs)</font>  
		</td>
	</tr> 
	<tr>
		<td colspan="6"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <b>District :</b> 
			 <%=DISTRICT_CODE_value%>	
		</td>
	</tr>
	 
	<tr>
		<th width='5%' align='center'>Sl.No</th>  
		<th width='45%' align='center'>Block </th>
		<th width='10%' align='center'>Opening Balance</th>
		<th width='10%' align='center' >Demand Current month</th>		
		<th width='10%' align='center'>Collection Current month</th>
		<th width='10%' align='center'>Balance</th>
	</tr>
	
<%	
	     
				
				String user_query=" SELECT ROUND( TRUNC((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13) ,3),2) AS ob,"+
				" ROUND(TRUNC(DMD_FOR_MTH_WC_10,3),2)AS dmd, ROUND(TRUNC((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),3),2)AS collection,"+
				" ROUND(TRUNC(BALANCE_18,3),2)AS balance, rpt.BLOCK_SNO,BLOCK_NAME "+
				" FROM "+  
				" (SELECT SUM( DECODE(ac.OPENING_BAL_WC_8 ,NULL,0.0, ac.OPENING_BAL_WC_8)) AS OPENING_BAL_WC_8, "+
				"  SUM( DECODE(ac.DMD_UPTO_PRV_MNTH_WC_9 ,NULL,0.0, ac.DMD_UPTO_PRV_MNTH_WC_9))  AS DMD_UPTO_PRV_MNTH_WC_9, "+
				"  SUM( DECODE( ac.DMD_FOR_MTH_WC_10,NULL,0.0,ac.DMD_FOR_MTH_WC_10)) AS DMD_FOR_MTH_WC_10, "+
				"  SUM( DECODE( ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12,NULL,0.0,ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12)) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
				"  SUM( DECODE( ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13,NULL,0.0,ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13)) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
				"  SUM( DECODE( ac.COLN_FOR_MTH_YES_YR_WC_14,NULL,0.0,ac.COLN_FOR_MTH_YES_YR_WC_14))     AS COLN_FOR_MTH_YES_YR_WC_14, "+
				"  SUM( DECODE( ac.COLN_FOR_MTH_WC_15,NULL,0.0,ac.COLN_FOR_MTH_WC_15))   AS COLN_FOR_MTH_WC_15, "+
				"  SUM( DECODE( ac.BALANCE_18 ,NULL,0.0,ac.BALANCE_18))  AS BALANCE_18, ac.MONTH AS MONTH, ac.YEAR AS YEAR , ac.BLOCK_SNO,  BLOCK_NAME  "+
				" FROM "+
				" (SELECT DECODE(SUM(OPENING_BAL_WC),NULL,0.0,SUM(OPENING_BAL_WC))     / 100000 AS OPENING_BAL_WC_8, "+
				"  DECODE(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC))         / 100000 AS DMD_UPTO_PRV_MNTH_WC_9, "+
				"  DECODE(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC))     / 100000 AS DMD_FOR_MTH_WC_10, "+
				"  DECODE(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) / 100000 AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
				"  DECODE(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) / 100000 AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
				"  DECODE(SUM(COLN_FOR_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_FOR_MTH_YES_YR_WC))     / 100000 AS COLN_FOR_MTH_YES_YR_WC_14, "+
				"  DECODE(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES))   / 100000 AS COLN_FOR_MTH_WC_15, "+
				"  DECODE(SUM(TOT_COLN_FOR_YR_DMD),NULL,0.0,SUM(TOT_COLN_FOR_YR_DMD))           / 100000 AS TOT_COLN_FOR_YR_DMD_17, "+
				"  DECODE(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))       / 100000 AS BALANCE_18, "+
				"  SCH_TYPE_ID_SUB, BLOCK_SNO,  BLOCK_NAME, MONTH, YEAR FROM PMS_DCB_LEDGER_ACTUAL "+
				"  WHERE YEAR="+pyear+"  AND MONTH="+pmonth+" "+add_cond+" and DISTRICT_CODE="+DISTRICT_CODE+" and  "+add_cond2+"  AND SCH_TYPE_ID_SUB="+schtype+"  GROUP BY YEAR, MONTH,SCH_TYPE_ID_SUB,BLOCK_SNO,  BLOCK_NAME ORDER BY  BLOCK_NAME "+
				"  )ac "+
				" 	GROUP BY YEAR, MONTH, ac.SCH_TYPE_ID_SUB , BLOCK_SNO,  BLOCK_NAME ORDER BY  BLOCK_NAME "+
				" )rpt  ORDER BY  BLOCK_NAME";     
				      
				    
				ResultSet rs_dis2=obj_new2.getRS(user_query);  
				double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
				int row=0;
				while(rs_dis2.next())
				{
					row++;	
					String BLOCK_NAME=rs_dis2.getString("BLOCK_NAME");
					String BLOCK_SNO=rs_dis2.getString("BLOCK_SNO");		
					String ob=rs_dis2.getString("ob");
					String dmd=rs_dis2.getString("dmd"); 
					String collection=rs_dis2.getString("collection");
					String balance=rs_dis2.getString("balance");
					tot1+=Double.parseDouble(ob);
					tot2+=Double.parseDouble(dmd);
					tot3+=Double.parseDouble(collection);
					tot4+=Double.parseDouble(balance);  
					 
					 if (row%2==1)
					 out.println("<tr><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>");
					 else
					 out.println("<tr  bgcolor='#CCFFFF'><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>");
						 
					
					
					out.println("<td align='left' width='45%' ><a href=md_abstract_benwise.jsp?DISTRICT_CODE="+DISTRICT_CODE+"&pmonth="+pmonth+"&pyear="+pyear+"&schtype="+schtype+"&BLOCK_SNO="+BLOCK_SNO+">"+BLOCK_NAME+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(ob))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(dmd))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(collection))+"&nbsp;</td>");
					out.println("<td align='right' width='11%'>"+df.format(Double.parseDouble(balance))+"&nbsp;</td></tr>"); 					  
				}
				 
				out.println("<tr bgcolor='#E8E8E8'><td align='right' width='5%'>&nbsp;</td><td align='right'width='45%' >Total &nbsp;</td>");
				out.println("<td align='right' width='10%'>"+df.format(tot1)+"&nbsp;</td>");
				out.println("<td align='right' width='10%'>"+df.format(tot2)+"&nbsp;</td>");  
				out.println("<td align='right' width='10%'>"+df.format(tot3)+"&nbsp;</td>");
				out.println("<td align='right' width='11%'>"+df.format(tot4)+"&nbsp;</td></tr>");
				out.println("</table></td></tr>");  
			 
			db_con.close(); 
		}catch(Exception e) 
		{  
			e.printStackTrace();  
		}
%>  
 
</table><center>
<table border=0 width="72%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
<tr><td align="left"><input type=button value="Back" onclick="window.history.go(-1)"></td></tr></table></center>
</body>
</html>