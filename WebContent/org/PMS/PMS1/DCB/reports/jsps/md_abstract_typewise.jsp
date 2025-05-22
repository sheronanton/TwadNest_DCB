<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<%@page import="java.text.DecimalFormat"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand,Collection and Balance Statement </title>
<script  src="../../scripts/FusionCharts.js"></script>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<style type="text/css">
#scroll_clipper 
{
    height: 500px;
    margin-left: 100px; 
    overflow: scroll;
    overflow-x: hidden;
    top: 0;     
    width: 85%;
  }
#scroll_clipper2 {
    height: 46px;
    margin-left: 100px;
    overflow: scroll;
    overflow-x: hidden;
    overflow-y: hidden;
    top: 0;    
    width: 84%;
}    
#scroll_clipper3 {
    height:70px;
    margin-left: 100px;
    overflow: scroll;
    overflow-x: hidden;
    overflow-y: hidden;
    top: 0;    
    width: 84%;
} 
</style>
</head>
<body>  
<center>
 <%
 	String xml="",xml2=""; 
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
		obj_new2.createStatement(db_con);
		String BEN_TYPE_GROUP=obj_new.setValue("BEN_TYPE_GROUP",request);
		String schtype=obj_new.setValue("schtype",request);    
		if (BEN_TYPE_GROUP.equalsIgnoreCase("6"))
		{
			response.sendRedirect("md_abstract_distwise.jsp?pmonth="+pmonth+"&pyear="+pyear+"&BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"&schtype="+schtype);
		}
		else
		{
		String ben_group_name=obj_new2.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_GROUP_NAME","where BEN_TYPE_GROUP="+BEN_TYPE_GROUP);
		String mvalue=obj_new.month_val(pmonth);
		
		String stype_value=obj_new2.getValue("PMS_DCB_APPLICABLE_SCH_TYPE", "SCH_TYPE_SUB_DESC","where SCH_TYPE_ID_SUB=" + schtype);
		String add_cond="",add_cond2="";
		userid=(String)session.getAttribute("UserId");
		if(userid==null)  
		{
		  response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		Office_id=obj_new.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		Office_Name = obj_new.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
		String off=Office_id.equals("5000")?"":" and Office_id="+Office_id;	 	
		Office_Name=Office_id.equals("5000")?"":""+Office_Name+"<br>";
		if (Integer.parseInt(schtype)==1) 
			add_cond=" ";// ";  
		else
			add_cond=" ";
		if (Integer.parseInt(schtype)==1) 
			add_cond2=" BEN_TYPE_GROUP="+BEN_TYPE_GROUP+"";  
		else
		{
			if (Integer.parseInt(BEN_TYPE_GROUP)==7)
			add_cond2=" BEN_TYPE_ID_SUB in ("+BEN_TYPE_GROUP+",8,9,10,11,12,13,14,15,16,17,18) ";
			else
			{
				if (Office_id.equalsIgnoreCase("5000"))
				{
					add_cond2=" BEN_TYPE_ID_SUB in ("+BEN_TYPE_GROUP+") ";
				}else
				{
					add_cond2=" BEN_TYPE_GROUP in ("+BEN_TYPE_GROUP+") ";
				}
			}
		}
	 
 %>  
<table  width="90%"   align="center" cellpadding="0" cellspacing="0"   border="0">  
<tr>
	<td width="80%" colspan="2">
		<table border=0 width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
		<tr>
			<td width="100%" valign="top">	
			<div id="scroll_clipper3">
			<table border=0 width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
			<tr class="tdH">
				<td colspan=6 align="center"><%=Office_Name%>Demand,Collection and Balance Statement 
				<br><%=stype_value%>-<%=ben_group_name%>
				<br><font size=2>(for the month of <%=mvalue%> <%=pyear%>)</font><br><font size=2>(Rupees in Lakhs)</font>  
				</td>
				</tr>			  
			</table>
			</div>
			<div id="scroll_clipper2"> 
			<table  border=0 width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
			<tr>
				<th width='5%' align='center'> Sl.No</th>
				<th width='50%' align='center'>Beneficiary Name</th> 
				<th width='10%' align='center'>Opening Balance</th>
				<th width='10%' align='center' >Demand Current month</th>		
				<th width='10%' align='center'>Collection Current month</th>
				<th width='10%' align='center'>Balance</th>
			</tr>  
			</table>
			</div>    
</td>
</tr> 
</table>  
	 	 <div id='scroll_clipper' > 
		 <table border=0 width="98%" cellpadding="4" cellspacing="0" style="border-collapse: collapse;border-bottom: 1em;"  align="center">
		<%
				xml="<graph showNames='1' decimalPrecision='0'>";
				xml2="<graph showNames='1' decimalPrecision='0'>";
				int row1=0; 
				double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
				String dis_code="0",dis_name="",user_query="";
				double ntot1=0,ntot2=0,ntot3=0,ntot4=0,ntot5=0;
				String dis_query="SELECT DISTRICT_CODE,DISTRICT_NAME FROM COM_MST_DISTRICTS WHERE DISTRICT_CODE IN (SELECT DISTRICT_CODE "+
								 " FROM PMS_DCB_LEDGER_ACTUAL  WHERE YEAR="+pyear+"   "+add_cond+" "+off+" AND MONTH="+pmonth+" AND SCH_TYPE_ID_SUB="+schtype+" and "+ add_cond2+" )"+
								 " order by DISTRICT_CODE";
				ResultSet rs_dis=obj_new.getRS(dis_query);
				while(rs_dis.next())  
				{
					  user_query="";	
					  dis_code=rs_dis.getString("DISTRICT_CODE");
					  dis_name=rs_dis.getString("DISTRICT_NAME");
					  user_query="select BENEFICIARY_NAME ,BENEFICIARY_SNO, round(  trunc((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13)  ,3),2) as ob, "+
							" round(trunc(DMD_FOR_MTH_WC_10,3),2) as dmd, round(trunc((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),3),2) as collection, round(trunc(BALANCE_18,3),2) as balance "+  
						" from (  SELECT    sum( DECODE(ac.OPENING_BAL_WC_8 ,NULL,0.0, ac.OPENING_BAL_WC_8))  AS OPENING_BAL_WC_8, "+
						" sum( DECODE(ac.DMD_UPTO_PRV_MNTH_WC_9 ,NULL,0.0, ac.DMD_UPTO_PRV_MNTH_WC_9))    AS DMD_UPTO_PRV_MNTH_WC_9, "+
						" sum( DECODE( ac.DMD_FOR_MTH_WC_10,NULL,0.0,ac.DMD_FOR_MTH_WC_10)) AS DMD_FOR_MTH_WC_10,  "+ 
						" sum( DECODE( ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12,NULL,0.0,ac.COLN_UPTO_PRV_MTH_YES_YR_WC_12)) AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
						" sum( DECODE( ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13,NULL,0.0,ac.COLN_UPTO_PRV_MTH_CR_YR_DMD_13)) AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
						" sum( DECODE( ac.COLN_FOR_MTH_YES_YR_WC_14,NULL,0.0,ac.COLN_FOR_MTH_YES_YR_WC_14))     AS COLN_FOR_MTH_YES_YR_WC_14, "+
						" sum( DECODE( ac.COLN_FOR_MTH_WC_15,NULL,0.0,ac.COLN_FOR_MTH_WC_15))  AS COLN_FOR_MTH_WC_15, "+
						" sum( DECODE( ac.BALANCE_18 ,NULL,0.0,ac.BALANCE_18))  AS BALANCE_18, ac.MONTH  ,ac.YEAR  ,  ac.BENEFICIARY_NAME ,ac.BENEFICIARY_SNO"+ 
						" FROM   (   SELECT  DECODE(SUM(OPENING_BAL_WC),NULL,0.0,SUM(OPENING_BAL_WC)) / 100000 AS OPENING_BAL_WC_8, "+
						" DECODE(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC))   / 100000 AS DMD_UPTO_PRV_MNTH_WC_9, "+
						" DECODE(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC)) / 100000 AS DMD_FOR_MTH_WC_10, "+
						" DECODE(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) / 100000 AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
						" DECODE(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) / 100000 AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13, "+
						" DECODE(SUM(COLN_FOR_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_FOR_MTH_YES_YR_WC))     / 100000 AS COLN_FOR_MTH_YES_YR_WC_14, "+
						" DECODE(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES))  / 100000 AS COLN_FOR_MTH_WC_15, "+
						" DECODE(SUM(TOT_COLN_FOR_YR_DMD),NULL,0.0,SUM(TOT_COLN_FOR_YR_DMD))  / 100000 AS TOT_COLN_FOR_YR_DMD_17, "+
						" DECODE(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))   / 100000 AS BALANCE_18,BENEFICIARY_NAME, BENEFICIARY_SNO,MONTH, YEAR "+
						" FROM PMS_DCB_LEDGER_ACTUAL  WHERE  YEAR="+pyear+" "+add_cond+" "+off+" AND MONTH ="+pmonth+" and SCH_TYPE_ID_SUB="+schtype+" and "+add_cond2+"  and DISTRICT_CODE="+dis_code+" GROUP BY YEAR,MONTH, BENEFICIARY_NAME,BENEFICIARY_SNO " +
						" )ac group by YEAR,MONTH,BENEFICIARY_NAME,BENEFICIARY_SNO ) rpt     order by BENEFICIARY_NAME,BENEFICIARY_SNO ";
					 
						tot1=0;tot2=0;tot3=0;tot4=0;tot5=0;
						  
						if (Integer.parseInt(BEN_TYPE_GROUP)>1) 
				 		out.println("<tr><td  colspan=6 align=left >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;District :&nbsp;&nbsp;"+dis_name+"  </td></tr>");						
					 	out.println("<tr><td colspan=6><table border=1 width='100%' cellpadding='2' cellspacing='0' style='border-collapse: collapse;'  align='center'>");
						ResultSet rs_dis2=obj_new2.getRS(user_query);						
						int row=0; 
						while(rs_dis2.next()) 
						{   
							row++;	
							String BENEFICIARY_NAME=rs_dis2.getString("BENEFICIARY_NAME");
							String ob=rs_dis2.getString("ob");
							String dmd=rs_dis2.getString("dmd"); 
							String collection=rs_dis2.getString("collection");
							String balance=rs_dis2.getString("balance");
							tot1+=Double.parseDouble(ob);
							tot2+=Double.parseDouble(dmd);
							tot3+=Double.parseDouble(collection);
							tot4+=Double.parseDouble(balance);
							ntot1+=Double.parseDouble(ob);
							ntot2+=Double.parseDouble(dmd);
							ntot3+=Double.parseDouble(collection);
							ntot4+=Double.parseDouble(balance);
							row1++;
							if (row1%2==1)
							out.println("<tr>");
							else
							out.println("<tr bgcolor='#CCFFFF'>");	
							
							if (Integer.parseInt(BEN_TYPE_GROUP)>1)
							{
							out.println(" <td align='center' width='5%'>&nbsp;"+row+"&nbsp;</td>");
							}else
							{
							out.println(" <td align='center' width='5%'>&nbsp;"+row1+"&nbsp;</td>");
							}  
						//	xml+="<set name='"+BENEFICIARY_NAME+"' value='"+df.format(Double.parseDouble(dmd))+"'/>";
						//	xml2+="<set name='"+BENEFICIARY_NAME+"' value='"+df.format(Double.parseDouble(collection))+"'/>";
						 
							out.println("<td align='left' width='51%' >"+BENEFICIARY_NAME+"&nbsp;</td>");
							out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(ob))+"&nbsp;</td>");
							out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(dmd))+"&nbsp;</td>");
							out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(collection))+"&nbsp;</td>");
							out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(balance))+"&nbsp;</td></tr>"); 					  
						}
						
						if (Integer.parseInt(BEN_TYPE_GROUP)>1)
						{     
						out.println("<tr><td align='right' width='5%'>&nbsp;</td><td align='right'width='48%' >Sum &nbsp;</td>");
						out.println("<td align='right' width='10%'>"+df.format(tot1)+"&nbsp;</td>");
						out.println("<td align='right' width='10%'>"+df.format(tot2)+"&nbsp;</td>");  
						out.println("<td align='right' width='10%'>"+df.format(tot3)+"&nbsp;</td>");
						out.println("<td align='right' width='10%'>"+df.format(tot4)+"&nbsp;</td></tr>");				
						}
						out.println("</table></td></tr>");
					}  
				//	xml+="</graph>";
					//xml2+="</graph>";
					//if (Integer.parseInt(BEN_TYPE_GROUP)<=5)
					//{
					out.println("<tr bgcolor='#E8E8E8'><td align='right' width='5%'>&nbsp;</td><td align='right'width='51%' > Total &nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(ntot1)+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(ntot2)+"&nbsp;</td>");  
					out.println("<td align='right' width='10%'>&nbsp;"+df.format(ntot3)+"</td>");
					out.println("<td align='right' width='10%'>"+df.format(ntot4)+"&nbsp;</td></tr>");
					//}   
					db_con.close(); 
		}
	}catch(Exception e) 
	{  
	e.printStackTrace();  
	}  
	
		%>  
		</table></div> </td></tr><tr> 
		<td  width="7%" valign="bottom" align="right"> </td><td align="left">
		<input type=button value="Back" onclick="window.history.go(-1)" class="paginationTbl">
	</td></tr></table><br></center></body></html>