<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="Servlets.PMS.PMS1.DCB.reports.Scheme_Desi_Pump_Supp"%><html>
<head>
<style type="text/css">
	.ft
	{
		font-size: small;
	}
	.fnt
	{
		font-weight: bold;
	}
</style>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>  
<title>DCB DashBoard - Scheme wise Design,Pumped and Supplied</title>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
		String month="",year="",office_id="";
		Connection db_con;
		DecimalFormat df = new DecimalFormat("0.000");
		Controller obj_new=new Controller();
		Controller obj_new2=new Controller();
		String html="",REGNAME="",REGION_ID="" ,OFFICE_NAME="";
		try
		{
			db_con=obj_new.con();
			obj_new.createStatement(db_con);
			obj_new2.createStatement(db_con);
			month=obj_new.setValue("month",request);
			year=obj_new.setValue("year",request);
			office_id=obj_new.setValue("office_id",request);
			String days=obj_new.month_val2(Integer.parseInt(month));
			Scheme_Desi_Pump_Supp obj1=new Scheme_Desi_Pump_Supp();
			REGION_ID=obj_new.setValue("REGION_ID",request);
			REGNAME=obj_new.getValue("com_mst_all_offices_view","office_name"," where office_id="+REGION_ID);
			String Office_Name=obj_new2.getValue("com_mst_all_offices_view","office_name"," where office_id="+office_id);
			obj1.setPromonth(Integer.parseInt(month));  
			obj1.setProyear(Integer.parseInt(year));
			obj1.setProoffice_id(Integer.parseInt(office_id));
			obj1.setDays(Integer.parseInt(days));
			ArrayList res=obj1.Result(obj_new2);
			String mvalue=obj_new.month_val(month);
			int i=0;
			int  row=0;
			Iterator er=res.iterator();%>
			<table border=1 class="ft"  width="72%" cellpadding="5" cellspacing="0" style="border-collapse: collapse;"  align="center"> 
			<tr class="tdH">
			<td colspan=6 align="center">SchemeWise Quantity -- Design,Pumped,Supply  	
				<font size=2>(for the month of <%=mvalue%> <%=year%>)</font> 
			</td>
			</tr> 
			<tr> <td colspan="6"><table width="100%"><tr>  
			<td colspan="3" align="left">
				<b>Region:</b> <%=REGNAME%>				       
			</td>
			<td colspan="3" align="right">
				<b>Division :</b> <%=Office_Name%>				       
			</td>						
			</tr></table></td></tr>
			<tr>
				<th width='5%' align='center'>Sl.No</th>
				<th width='45%' align='center'>Scheme Name</th>
				<th width='10%' align='center'>Eamarked/Design Qty (ML)</th>
				<th width='10%' align='center' >Pumped Qty(ML)</th>		
				<th width='10%' align='center'>Supplied Qty(ML)</th>				 
			</tr>
			
			<%  html="";
			while (er.hasNext())
			{
				i++;
				
				Object b=er.next();				
				if (i%4==1)
				{
					row++;
					if (row %2==1)
					html+="<tr><td align='center'>"+row+"</td><td>"+b.toString()+"</td>";
					else
					html+="<tr  bgcolor='#CCFFFF'><td  align='center'>"+row+"</td><td>"+b.toString()+"</td>";
				}
				else if (i%4==0)
				{	
					if (i%4==2)
					{
						html+="<td align=left>"+df.format(Double.parseDouble(b.toString()))+"</td></tr>";
					}
					else
					{
						html+="<td align=right>"+df.format(Double.parseDouble(b.toString()))+"</td></tr>";
					}
				}
				else
					html+="<td  align=right>"+df.format(Double.parseDouble(b.toString()))+"</td>";
				
				
			}
		}catch(Exception e) 
		{  
			e.printStackTrace();  
		}

			out.println(html);
%>
	</table><center>
<table border=0 width="72%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
<tr><td align="left"><input type=button value="Back" onclick="window.history.go(-1)"></td></tr></table></center>
</body>
</html>