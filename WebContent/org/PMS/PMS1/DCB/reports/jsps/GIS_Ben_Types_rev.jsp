<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="pms.dcb.webservices.GIS"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%><html>
<head>
		  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		  <title>GIS Report </title>
          <link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
			<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
	String pmonth="",pyear="";
	try
	{
			Controller obj_con=new Controller();
			DecimalFormat df=new DecimalFormat("0.00");
			String month=request.getParameter("pmonth");
			String year=request.getParameter("pyear");
			int prv_month=obj_con.prv_month(Integer.parseInt(year),Integer.parseInt(month));
			int prv_year=obj_con.prv_year(Integer.parseInt(year),Integer.parseInt(month));
			String prv_mvalue=obj_con.month_val(Integer.toString(prv_month));
			String monthv=obj_con.month_val(month);
			String qry="SELECT BEN_TYPE_ID_SUB,BEN_TYPE_DESC_SUB,ROUND( TRUNC((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13) ,3),2) AS ob,"+
					  " ROUND(TRUNC(DMD_FOR_MTH_WC_10,3),2)   AS dmd,ROUND(TRUNC((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),3),2) AS collection,"+ 
					  " ROUND(TRUNC(BALANCE_18,3),2) AS balance "+
					  " FROM "+
					  " (SELECT SUM( DECODE(ac.OPENING_BAL_WC_8 ,NULL,0.0, ac.OPENING_BAL_WC_8))AS OPENING_BAL_WC_8, "+
					    " SUM( DECODE(ac.DMD_UPTO_PRV_MNTH_WC_9 ,NULL,0.0, ac.DMD_UPTO_PRV_MNTH_WC_9))     AS DMD_UPTO_PRV_MNTH_WC_9, "+
					    " SUM( DECODE( ac.DMD_FOR_MTH_WC_10,NULL,0.0,ac.DMD_FOR_MTH_WC_10))     AS DMD_FOR_MTH_WC_10, "+
					    " SUM( DECODE( ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12,NULL,0.0,ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12)) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
					    " SUM( DECODE( ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13,NULL,0.0,ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13)) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
					    " SUM( DECODE( ac.COLN_FOR_MTH_YES_YR_WC_14,NULL,0.0,ac.COLN_FOR_MTH_YES_YR_WC_14))AS COLN_FOR_MTH_YES_YR_WC_14, "+
					    " SUM( DECODE( ac.COLN_FOR_MTH_WC_15,NULL,0.0,ac.COLN_FOR_MTH_WC_15))   AS COLN_FOR_MTH_WC_15, "+
					    " SUM( DECODE( ac.BALANCE_18 ,NULL,0.0,ac.BALANCE_18))       AS BALANCE_18,ac.MONTH ,ac.YEAR ,ac.BEN_TYPE_ID_SUB ,ac.BEN_TYPE_DESC_SUB "+
					  " FROM "+
					    " (SELECT DECODE(SUM(OPENING_BAL_WC),NULL,0.0,SUM(OPENING_BAL_WC))          / 100000 AS OPENING_BAL_WC_8, "+
					      " DECODE(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC))    / 100000 AS DMD_UPTO_PRV_MNTH_WC_9, "+
					      " DECODE(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC))     / 100000 AS DMD_FOR_MTH_WC_10, "+
					      " DECODE(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) / 100000 AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
					      " DECODE(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) / 100000 AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
					      " DECODE(SUM(COLN_FOR_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_FOR_MTH_YES_YR_WC))/ 100000 AS COLN_FOR_MTH_YES_YR_WC_14, "+
					      " DECODE(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES))    / 100000 AS COLN_FOR_MTH_WC_15, "+
					      " DECODE(SUM(TOT_COLN_FOR_YR_DMD),NULL,0.0,SUM(TOT_COLN_FOR_YR_DMD))      / 100000 AS TOT_COLN_FOR_YR_DMD_17, "+
					      " DECODE(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))  / 100000 AS BALANCE_18,BEN_TYPE_ID_SUB,BEN_TYPE_DESC_SUB,MONTH,YEAR "+
					    " FROM PMS_DCB_LEDGER_ACTUAL WHERE YEAR="+year+" AND MONTH="+month+" and BEN_TYPE_ID_SUB <=6 GROUP BY YEAR,MONTH,BEN_TYPE_ID_SUB,BEN_TYPE_DESC_SUB "+
					    " )ac GROUP BY YEAR,MONTH,BEN_TYPE_ID_SUB,BEN_TYPE_DESC_SUB "+
					  " ) rpt ORDER BY BEN_TYPE_ID_SUB ";
			  qry="SELECT rpt_prv.BEN_TYPE_ID_SUB,BEN_TYPE_DESC_SUB,ob,dmd,collection,((ob+dmd)-collection)    AS balance "+
				" from ( SELECT sum(DMD_FOR_MTH_WC)  / 100000 as dmd,"+ 
			        "   (((sum(OPENING_BAL_WC)+sum(DMD_UPTO_PRV_MNTH_WC))-(sum(COLN_UPTO_PRV_MTH_YES_YR_WC)+sum(COLN_UPTO_PRV_MTH_CR_YR_DMD))))   / 100000 as ob,"+
			        " BEN_TYPE_ID_SUB,MONTH,YEAR  FROM PMS_DCB_LEDGER_ACTUAL "+
			        " WHERE  MONTH=11 AND YEAR=2013   AND BEN_TYPE_ID_SUB <=6"+
			    " GROUP BY YEAR,MONTH,BEN_TYPE_ID_SUB   ORDER BY BEN_TYPE_ID_SUB"+
			    " ) rpt_prv    "+
			   " join ( SELECT (SUM(COLN_FOR_MTH_YES_YR_WC) + SUM(COLN_INCLUDE_CHARGES))  / 100000 as  collection,BEN_TYPE_ID_SUB,BEN_TYPE_DESC_SUB from PMS_DCB_LEDGER_ACTUAL"+ 
			    " WHERE MONTH =12 and year=2013 AND BEN_TYPE_ID_SUB <=6  "+
			    " GROUP BY YEAR,MONTH,BEN_TYPE_ID_SUB,BEN_TYPE_DESC_SUB "+
			    " )rpt_coll   ON rpt_prv.BEN_TYPE_ID_SUB=rpt_coll.BEN_TYPE_ID_SUB "+
			    " ORDER BY BEN_TYPE_ID_SUB ";
		 
			GIS obj=new GIS();  
			ArrayList ar=obj.ben_type(qry);			
			Iterator val=ar.iterator();
			out.println("<table style='border-collapse: collapse;' align=center width='60%' border='1' cellpadding='5' cellspacing='0'>"+
			"<tr class='tdH'><th width='5%' colspan=6 align='center'>Abstract Report - Beneficiary Type Wise</th></tr>	"+
			"<tr><td width='5%' align='center'>&nbsp;</td><td colspan=4 align='center'>"+monthv+"-"+year+"</td><td colspan=6 align='right'>Rs. In Lakhs</th></tr>"+
			"<tr><th width='5%' align='center'>Sl.No</th><th   align='center'>Beneficiary Type</th>"+
			"<th width='15%' align='center'>Opening Balance</th>"+
			"<th width='15%' align='center'>Demand Current month </th>"+
			"<th width='15%' align='center'>Collection Current month</th><th align='center' width='15%' >Balance</th></tr>");
			int i=0;
			int j=0;
			int row=0;
			String type="",column_value="";
			double ob_sum=0.0,dmd_sum=0.0,collection=0.0,balance=0.0;
			String color1="#CCFFFF";
			String Rvalue="";
			while(val.hasNext())
			{  				 
				if (i%6==0) 
				{
					row++;
					if (row%2==0)
					{
					out.println("<tr bgcolor='#CCFFFF'><td>"+row+"</td>");
					}else
					{
						out.println("<tr><td><font size='3'>"+row+"</font></td>");
					}
					//out.println("<tr><td>"+val.next().toString()+"</td>");
					type=val.next().toString();
					j++;
				}else
				{
					if (j%7!=1)
					{
						column_value=val.next().toString(); 
						if (i%6==2) { ob_sum=ob_sum+Double.parseDouble(column_value);}
						else if (i%6==3) { dmd_sum=dmd_sum+Double.parseDouble(column_value);}		 
						else if (i%6==4) { collection=collection+Double.parseDouble(column_value);}
						else if (i%6==5) { balance=balance+Double.parseDouble(column_value);}						
						out.println("<td  align='right'><font size='3'>"+df.format(Double.parseDouble(column_value))+"</font></td>");						
					}
					else   
					{
						Rvalue=val.next().toString();
						out.println("<td> <font size='3'> <a href='GIS_Dist_Wise.jsp?type="+type+"&month="+month+"&year="+year+"&desc="+Rvalue+"'>"+Rvalue+"</font></a></td>");
						j=0;
					}					  
				}
				i++;
			} 
			out.println("</tr><tr bgcolor='#E8E8E8'><td colspan=2 align='right'>Total&nbsp;&nbsp;&nbsp;</td>"+
					"<td  align='right'>"+df.format(ob_sum)+"</td>"+
					"<td align='right'>"+df.format(dmd_sum)+"</td>"+
					"<td align='right'>"+df.format(collection)+"</td><td align='right'>"+df.format(balance)+"</td></tr>"+
					"<tr><td colspan='6' align='center'><input type='button'  style='color: red;font-weight: bold'  value='Exit' onclick='javascript:window.close()'></td></tr></table>");
	}catch(Exception e)
	{
		out.println(e);		  
	}
%> 
</body>
</html>