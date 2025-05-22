<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<%@page import="java.text.DecimalFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB Statement II </title>
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
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<body>
<%
Connection db_con=null,db_con1=null;
Controller obj_new=new Controller();
Controller obj_new2=new Controller();
Controller obj_new3=new Controller();
DecimalFormat df = new DecimalFormat("0.00");
int prv_month=0,prv_year=0;
String month="",year="",monthval="";
String REGNAME="" ,OFFICE_NAME="",CRAMT ,dramt="0",DMDAMT ,yester_year_demand ,current_year_collection ,total_demand;
String current_year_collection1 ,current_year_demand ,collection_of_current_month ,collection_of_current_month2 ;
double avg3 ,avg2 ;
String REGION_ID="";
double total_CRAMT=0.0,total_dramt=0.0,total_DMDAMT=0.0,total_yester_year_demand=0.0,total_current_year_collection=0.0,total_total_demand=0.0;
double total_current_year_collection1=0.0,total_current_year_demand=0.0,total_collection_of_current_month=0.0,total_collection_of_current_month2=0.0;
double net_collection=0.0,netbalance=0.0;
String week1_sdate="",week1_edate="",current_year_edate_ap="";
String week2_sdate="",week2_edate="",current_year_sdate_ap="";
String week3_sdate="",week3_edate="",current_year_edate;
String week4_sdate="",week4_edate="",current_year_sdate;
int flag=0;
int  april_year=0;
try
{	 
	db_con=obj_new.con();
	db_con1=obj_new3.con();
	obj_new.createStatement(db_con);
	obj_new2.createStatement(db_con);
	obj_new3.createStatement(db_con1);
	month=obj_new.setValue("month",request);
	year=obj_new.setValue("year",request);
	REGION_ID=obj_new.setValue("REGION_ID",request);
	REGNAME=obj_new3.getValue("com_mst_all_offices_view","office_name"," where office_id="+REGION_ID);
	monthval=obj_new.month_val(month);
	String day1 = obj_new.month_val2(Integer.parseInt(month));        								 
	  current_year_sdate=1+"/5/"+april_year;       
	  current_year_edate=day1+"/"+Integer.parseInt(month)+"/"+year;  
	if (Integer.parseInt(month)==1)    
	{
		prv_month=12;
	 	prv_year=Integer.parseInt(year)-1;
	}  
	else    
	{
		prv_month=Integer.parseInt(month)-1;
	 	prv_year=Integer.parseInt(year);
	}
	if (Integer.parseInt(month)<=3)
		april_year=Integer.parseInt(year)-1;
	else  
		april_year=Integer.parseInt(year); 

	  current_year_sdate_ap=1+"/4/"+april_year;       
	  current_year_edate_ap="30/4/"+april_year;
	week1_sdate=obj_new.setValue("sdate1", request);week1_edate=obj_new.setValue("edate1", request);
	week2_sdate=obj_new.setValue("sdate2", request);week2_edate=obj_new.setValue("edate2", request);
	week3_sdate=obj_new.setValue("sdate3", request);week3_edate=obj_new.setValue("edate3", request);
	week4_sdate=obj_new.setValue("sdate4", request);week4_edate=obj_new.setValue("edate4", request);
	String select_count=obj_new.setValue("select_count", request); 
}catch(Exception r)

{
	out.println(r);	
}
try
{

	   
%>
	<table  class="ft" border=1 style="border-collapse: collapse;" width="100%" cellpadding="7">
		<tr class="tdH">   
			<td colspan="16" align="center">
				Statement II showing the status of maintenance of CWSS / Board owned Schemes for the month of  <%=monthval%> <%=year%>
				       
			</td>			
		</tr>
		<tr>   
			<td colspan="16" align="left">
				<%=REGNAME%>				       
			</td>			
		</tr>
		<tr>  
				<th align="center"> Sl.No</th>
				<th> Name of Office</th>
				<th width="5%">Current year <br> Demand from <br>4/<%=april_year%> upto<br><%=prv_month%>/<%=prv_year%></th>  
				<th width="5%">Collection During   4/<%=year%></th>
				<th width="5%">Collection for Current year from 5/<%=april_year%> upto <%=month%>/<%=year%></th>
				<th width="5%">% of collection</th>
				<th width="5%">Current Month demand (<%=month%>/<%=year%>)</th>
				<th width="5%">Current month collection (<%=week1_sdate%> to <%=week1_edate%>)</th>
				<th width="5%">Current month collection (<%=week2_sdate%> to <%=week2_edate%>)</th>
				<th width="5%">Total Collection (7+8)</th>
				<th width="5%">% of collection</th>
				<th width="5%">Arrear Demand Upto 31/3/<%=april_year%></th>
				<th width="5%">Arrear Collection During   4/<%=april_year%></th>
				<th width="5%">Total Demand from 4/<%=april_year%>-<%=month%>/<%=year%>(3+6)</th>
				<th width="5%">Total Collection from  5/<%=april_year%> upto (<%=week2_edate%> (4b+9)</th>
				<th width="5%">Net Balance (11a+12)-<Br>(11b+13)</th>
		</tr>
		<tr class="tdH"> 
				<td align="center">1</td>
				<td align="center">2</td>
				<td width="5%" align="center">3</td>  
				<td width="5%" align="center">4a</td>
				<td width="5%" align="center">4b</td>
				<td width="5%" align="center">5</td>
				<td width="5%" align="center">6</td>
				<td width="5%" align="center">7</td>
				<td width="5%" align="center">8</td>
				<td width="5%" align="center">9</td>
				<td width="5%" align="center">10</td>
				<td width="5%" align="center">11a</td>
				<td width="5%" align="center">11b</td>
				<td width="5%" align="center">12</td>
				<td width="5%" align="center">13</td>
				<td width="5%" align="center">14</td>
		</tr>
<%
		  String qry="select  OFFICE_ID ,sum(CRAMT) as CRAMT,sum(dramt) as dramt,sum(DMDAMT) as DMDAMT,sum(yester_year_demand) as yester_year_demand ,";
			     qry+=" sum(current_year_collection) as current_year_collection,sum(current_year_collection1) as current_year_collection1,sum(current_year_demand) as current_year_demand ,";
			     qry+="sum(collection_of_current_month) as collection_of_current_month,sum(collection_of_current_month2) as collection_of_current_month2, sum(total_demand) as total_demand  from PMS_DCB_DASH_BOARD_DATA ";
			     qry+=" where year="+year+" and month="+month +" and REGION_ID="+REGION_ID+"  group by OFFICE_ID  order by  OFFICE_ID";
 			     int row=0;
		  ResultSet rs=obj_new2.getRS(qry);
		  while(rs.next())
		  { 
			  row++;	
			  String OFFICE_ID =obj_new3.isNull(rs.getString("OFFICE_ID"),1);
			  OFFICE_NAME=obj_new3.getValue("com_mst_all_offices_view","office_name"," where office_id="+OFFICE_ID);
		      CRAMT =obj_new3.isNull(rs.getString("CRAMT"),1);
		      dramt =obj_new3.isNull(rs.getString("dramt"),1);  
		      DMDAMT =obj_new3.isNull(rs.getString("DMDAMT"),1);
		      yester_year_demand =obj_new3.isNull(rs.getString("yester_year_demand"),1);
		      current_year_collection =obj_new3.isNull(rs.getString("current_year_collection"),1);
		      current_year_collection1  =obj_new3.isNull(rs.getString("current_year_collection1"),1);
		      current_year_demand =obj_new3.isNull(rs.getString("current_year_demand"),1);
		      collection_of_current_month =obj_new3.isNull(rs.getString("collection_of_current_month"),1);
		      collection_of_current_month2 =obj_new3.isNull(rs.getString("collection_of_current_month2"),1);
		      total_demand =obj_new3.isNull(rs.getString("total_demand"),1);
		      double total_collection=Double.parseDouble(collection_of_current_month)+Double.parseDouble(collection_of_current_month2);
		      double balance=(Double.parseDouble(total_demand)+Double.parseDouble(yester_year_demand))-
		    	  (total_collection+Double.parseDouble(current_year_collection1)+Double.parseDouble(current_year_collection));
		      avg2 =(Double.parseDouble(current_year_collection) /Double.parseDouble(current_year_demand))*100;
		      avg3 =((Double.parseDouble(collection_of_current_month)+Double.parseDouble(collection_of_current_month2))/Double.parseDouble(DMDAMT))*100;
		      // total
		     try
		      {
		      total_DMDAMT+=Double.parseDouble(DMDAMT);
		      total_yester_year_demand+=Double.parseDouble(yester_year_demand);
		      total_current_year_collection+=Double.parseDouble(current_year_collection);
		      total_total_demand+=Double.parseDouble(total_demand);
		      total_current_year_collection1+=Double.parseDouble(current_year_collection1);;
		      total_current_year_demand+=Double.parseDouble(current_year_demand);;
		      total_collection_of_current_month+=Double.parseDouble(collection_of_current_month);;
		      total_collection_of_current_month2+=Double.parseDouble(collection_of_current_month2);;
		      net_collection+=total_collection;
		      netbalance+=balance;
		      }catch(Exception e) {}
		      String color="";
		      if (row % 2==1) color=" bgcolor=#99FFFF"; 
		  %>
		  	<!-- <a href="Scheme_Desi_Pump_Supply.jsp?office_id=<%=OFFICE_ID%>&REGION_ID=<%=REGION_ID%>&month=<%=month%>&year=<%=year%>"> -->
			  <tr  <%=color%>>
				<td align="center"><%=row%></td>
				<td><%=OFFICE_NAME%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(current_year_demand))%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(current_year_collection1))%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(current_year_collection))%></td>
				<td width="5%" align="center"><%=df.format(avg2)%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(DMDAMT))%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(collection_of_current_month))%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(collection_of_current_month2))%></td>
				<td width="5%" align="right"><%=df.format(total_collection)%></td>
				<td width="5%" align="center"><%=df.format(avg3)%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(yester_year_demand))%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(current_year_collection1))%></td>
				<td width="5%" align="right"><%=df.format(Double.parseDouble(total_demand))%></td>
				<td width="5%" align="right"><%=df.format((total_collection+Double.parseDouble(current_year_collection)))%></td>
				<td width="5%" align="right"><%=df.format(balance)%></td>				
		</tr><%}
		
		  double tot_avg=0.0,tot_avg2=0.0;
			try
			{
				tot_avg=(total_current_year_collection/total_current_year_demand)*100;
				tot_avg2=(total_collection_of_current_month/total_collection_of_current_month2)*100;
			}catch(Exception e) {}
		%>
		
		   <tr bgcolor='#CCFFFF' class="fnt">
				<td align="center" colspan="2">Total</td>				
				<td width="5%" align="right"><%=df.format(total_current_year_demand)%></td>
				<td width="5%" align="right"><%=df.format(total_current_year_collection1)%></td>
				<td width="5%" align="right"><%=df.format(total_current_year_collection)%></td>
				<td width="5%" align="center"><%=df.format(tot_avg)%> </td>
				<td width="5%" align="right"><%=df.format(total_DMDAMT)%></td>
				<td width="5%" align="right"><%=df.format(total_collection_of_current_month)%></td>
				<td width="5%" align="right"><%=df.format(total_collection_of_current_month2)%></td>
				<td width="5%" align="right"><%=df.format(net_collection)%></td>
					<td width="5%" align="center"><%=df.format(tot_avg2)%> </td>
				<td width="5%" align="right"><%=df.format(total_yester_year_demand)%></td>
				<td width="5%" align="right"><%=df.format(total_current_year_collection1)%></td>
				<td width="5%" align="right"><%=df.format(total_total_demand)%></td>
				<td width="5%" align="right"><%=df.format(net_collection+total_current_year_collection)%></td>
				<td width="5%" align="right"><%=df.format(netbalance)%></td>				
		</tr>  
	  	  <%
	  	 }catch(Exception r)  
		 {
			out.println(r);	
		 }

	%>
	</table>
	<center>
<table border=0 width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
<tr><td align="left"><input type=button value="Back" onclick="window.history.go(-1)"></td></tr></table></center>
</body>
</html>