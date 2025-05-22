<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.text.DecimalFormat"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand,Collection and Balance Statement </title>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.XmlDataGenerator"%>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>  
</head>
<body>    
 <%
 	String  Office_Name="",userid="",Office_id="",pmonth="0",pyear="0",schtype="0" ,BEN_TYPE_GROUP="0";
	DecimalFormat df = new DecimalFormat("0.00");
	Connection db_con;
	Controller obj_new=new Controller();
	Controller obj_new2=new Controller();
	try
	{
		
		db_con=obj_new.con();
		obj_new.createStatement(db_con);
		  pmonth=obj_new.setValue("pmonth",request);
		  pyear=obj_new.setValue("pyear",request);
		  schtype=obj_new.setValue("schtype",request);
		obj_new2.createStatement(db_con);
		  BEN_TYPE_GROUP=obj_new.setValue("BEN_TYPE_GROUP",request);
		String ben_group_name=obj_new2.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_GROUP_NAME","where BEN_TYPE_GROUP="+BEN_TYPE_GROUP);
		String stype_value=obj_new2.getValue("PMS_DCB_APPLICABLE_SCH_TYPE", "SCH_TYPE_SUB_DESC","where SCH_TYPE_ID_SUB=" + schtype);
		userid=(String)session.getAttribute("UserId");
		if(userid==null)  
		{
		  response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		Office_id=obj_new.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_Name = obj_new.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		XmlDataGenerator xml=new XmlDataGenerator(db_con,obj_new);  
		String off=Office_id.equals("5000")?"":" and Office_id="+Office_id;	 	
		Office_Name=Office_id.equals("5000")?"":""+Office_Name+"<br>";
		String mvalue=obj_new.month_val(pmonth);
		String add_cond="",add_cond2="";
		 String path=getServletContext().getRealPath("\\data")+"\\test.xml";
		 xml.fpath=path;
	 
		if (Integer.parseInt(schtype)==1) 
			add_cond=" ";// and BEN_TYPE_GROUP not in (8) ";  
		else
			add_cond=" ";
		if (Integer.parseInt(schtype)==1)   
			add_cond2=" BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"";  
		else
			add_cond2=" BEN_TYPE_ID_SUB in (6) ";  
		//response.sendRedirect("http://10.163.2.213:7070/project/server/fchart/mschart1.php?xmlfile="+sxml);
		 int prv_month=obj_new.prv_month(Integer.parseInt(pyear),Integer.parseInt(pmonth));
		 int prv_year=obj_new.prv_year(Integer.parseInt(pyear),Integer.parseInt(pmonth));
		 String prv_mvalue=obj_new.month_val(Integer.toString(prv_month));
 %> 
<table border=1  width="72%" cellpadding="5" cellspacing="0" style="border-collapse: collapse;"  align="center"> 
	<tr class="tdH">
		<td colspan=6 align="center"><%=Office_Name%>Demand,Collection and Balance Statement		
		<br> <%=ben_group_name%><br>
				<font size=2>(for the month of <%=mvalue%> <%=pyear%>)</font>   
				<br>
				<font size=2>(Rupees in Lakhs)</font>  
		</td>
	</tr> 
	<tr>
		<th width='5%' align='center'>Sl.No</th>
		<th width='45%' align='center'>District</th>
		<th width='10%' align='center'>Opening Balance<br> <%=prv_mvalue%>-<%=prv_year%></th>
		<th width='10%' align='center' >Demand<br>   <%=mvalue%>-<%=pyear%> </th>		
		<th width='10%' align='center'>Collection <br><%=mvalue%>-<%=pyear%></th>
		<th width='10%' align='center'>Balance</th>
	</tr>    
<%	
				String user_query=" SELECT ROUND( TRUNC((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13) ,4),3) AS ob,"+
				" ROUND(TRUNC(DMD_FOR_MTH_WC_10,4),3)AS dmd, ROUND(TRUNC((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),4),3)AS collection,"+
				" ROUND(TRUNC(BALANCE_18,4),3)AS balance,rpt.DISTRICT_NAME,rpt.DISTRICT_CODE "+
				" FROM "+  
				" (SELECT SUM( DECODE(ac.OPENING_BAL_WC_8 ,NULL,0.0, ac.OPENING_BAL_WC_8)) AS OPENING_BAL_WC_8, "+
				"  SUM( DECODE(ac.DMD_UPTO_PRV_MNTH_WC_9 ,NULL,0.0, ac.DMD_UPTO_PRV_MNTH_WC_9))  AS DMD_UPTO_PRV_MNTH_WC_9, "+
				"  SUM( DECODE( ac.DMD_FOR_MTH_WC_10,NULL,0.0,ac.DMD_FOR_MTH_WC_10)) AS DMD_FOR_MTH_WC_10, "+
				"  SUM( DECODE( ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12,NULL,0.0,ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12)) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
				"  SUM( DECODE( ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13,NULL,0.0,ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13)) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
				"  SUM( DECODE( ac.COLN_FOR_MTH_YES_YR_WC_14,NULL,0.0,ac.COLN_FOR_MTH_YES_YR_WC_14))     AS COLN_FOR_MTH_YES_YR_WC_14, "+
				"  SUM( DECODE( ac.COLN_FOR_MTH_WC_15,NULL,0.0,ac.COLN_FOR_MTH_WC_15))   AS COLN_FOR_MTH_WC_15, "+
				"  SUM( DECODE( ac.BALANCE_18 ,NULL,0.0,ac.BALANCE_18))  AS BALANCE_18, ac.MONTH AS MONTH, ac.YEAR AS YEAR , ac.DISTRICT_NAME,DISTRICT_CODE "+
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
				"   DISTRICT_CODE,DISTRICT_NAME, MONTH, YEAR FROM PMS_DCB_LEDGER_ACTUAL "+
				//"  WHERE YEAR="+pyear+"  AND MONTH="+pmonth+"  "+add_cond+" "+off+" and "+add_cond2+"     GROUP BY YEAR, MONTH, DISTRICT_CODE, DISTRICT_NAME ORDER BY DISTRICT_CODE,DISTRICT_NAME "+
				"  WHERE YEAR="+pyear+"  AND MONTH="+pmonth+" and md_group=3   "+add_cond+" "+off+" and "+add_cond2+"     GROUP BY YEAR, MONTH, DISTRICT_CODE, DISTRICT_NAME ORDER BY DISTRICT_CODE,DISTRICT_NAME "+
				"  )ac "+ 
				" 	GROUP BY YEAR, MONTH,   DISTRICT_CODE, DISTRICT_NAME ORDER BY DISTRICT_CODE,  DISTRICT_NAME "+
				" )rpt  ORDER BY DISTRICT_CODE,  DISTRICT_NAME ";   
				ResultSet rs_dis2=obj_new2.getRS(user_query);
				double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
				int row=0;
				while(rs_dis2.next())
				{
					row++;	
					String DISTRICT_NAME=rs_dis2.getString("DISTRICT_NAME");
					String DISTRICT_CODE=rs_dis2.getString("DISTRICT_CODE");
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
						out.println("<tr bgcolor='#CCFFFF'><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>");
					out.println("<td align='left' width='45%' ><a href=md_new_abstract_blockwise.jsp?pmonth="+pmonth+"&pyear="+pyear+"&schtype="+schtype+"&DISTRICT_CODE="+DISTRICT_CODE+">"+DISTRICT_NAME+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(ob))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(dmd))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(collection))+"&nbsp;</td>");
					out.println("<td align='right' width='11%'>"+df.format(Double.parseDouble(balance))+"&nbsp;</td></tr>"); 					  
				}  
				out.println("<tr bgcolor='#E8E8E8'><td align='right' width='5%'>&nbsp;</td><td align='right'width='45%' >Total  </td>");
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
<table border=1 width="72%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
<tr><td align="center"><input type=button value="Back" onclick="window.history.go(-1)">&nbsp;&nbsp;&nbsp;<a href="md_new_abstract_distwiserpt.jsp?BEN_TYPE_GROUP=<%=BEN_TYPE_GROUP%>&pmonth=<%=pmonth%>&pyear=<%=pyear%>&schtype=<%=schtype%>">Report Excel Download</a></td></tr></table></center>
</body> 
</html> 