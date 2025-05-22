<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<%@page import="java.text.DecimalFormat"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statement I showing the status of maintenance of CWSS / Board owned Schemes for the month of </title>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
		Connection db_con=null,db_con1=null;
		Controller obj_new=new Controller();
		Controller obj_new2=new Controller();
		Controller obj_new3=new Controller();
		DecimalFormat df = new DecimalFormat("0.000");
		DecimalFormat df1 = new DecimalFormat("0.00");
		int prv_month=0,prv_year=0;
		String month="",year="",monthval="";
		String REGNAME ,CRAMT ,dramt,DMDAMT ,yester_year_demand ,current_year_collection ,total_demand;
		String current_year_collection1 ,current_year_demand ,collection_of_current_month ,collection_of_current_month2 ;
		double avg3 ,avg2 ;
		double total_CRAMT=0.0,total_dramt=0.0,total_DMDAMT=0.0,total_yester_year_demand=0.0,total_current_year_collection=0.0,total_total_demand=0.0;
		double total_current_year_collection1=0.0,total_current_year_demand=0.0,total_collection_of_current_month=0.0,total_collection_of_current_month2=0.0;
		double net_collection=0.0,netbalance=0.0;
		String parameter="",pie_chart_xml="";
		String week1_sdate="",week1_edate="" ;
		String week2_sdate="",week2_edate="" ;		 
try
		{	
			db_con=obj_new.con();
			db_con1=obj_new3.con();
			obj_new.createStatement(db_con);
			obj_new2.createStatement(db_con);
			obj_new3.createStatement(db_con1);
			month=obj_new.setValue("month",request);  
			year=obj_new.setValue("year",request);
			monthval=obj_new.month_val(month);
			String day1 = obj_new.month_val2(Integer.parseInt(month));        								 
			int month1 = Integer.parseInt(month)+1 ;// 12
			if (Integer.parseInt(month)==1)    
			{
				prv_month=12;
			 	prv_year=Integer.parseInt(year);
			}  
			else    
			{
				prv_month=Integer.parseInt(month)-1;
			 	prv_year=Integer.parseInt(year);
			}
			        
				int year1=0;
				if (Integer.parseInt(month)==12)
				{  
					year1=Integer.parseInt(year)+1;
					month1=1;
				}else  
				{
					year1=Integer.parseInt(year);  
				} 
				String sdate=1+"/"+month1+"/"+year1;
				String edate=obj_new.month_val2(month1)+"/"+month1+"/"+year1;
				week1_sdate=sdate;
				week1_edate=edate;    
 		//	week1_sdate=obj_new.setValue("sdate1", request);week1_edate=obj_new.setValue("edate1", request);
		//	week2_sdate=obj_new.setValue("sdate2", request);week2_edate=obj_new.setValue("edate2", request);
		
		
			 
			parameter="&prv_month="+prv_month;
			parameter+="&prv_year="+prv_year;
			parameter+="&year="+year;
			parameter+="&month="+month;	
			 
			parameter+="&sdate1="+week1_sdate;parameter+="&edate1="+week1_edate;
			parameter+="&sdate2="+week2_sdate;parameter+="&edate2="+week1_edate;  
	
			String select_count=obj_new.setValue("select_count", request);
			PreparedStatement  proc_stmt=null;
	 		try
			{ 
	 		 
					proc_stmt = db_con.prepareCall("{call PMS_DCB_DASH_BOARD_STMT (?,?,?,?,?) }");		        		  		 
					proc_stmt.setInt(1, Integer.parseInt(year));
					proc_stmt.setInt(2, Integer.parseInt(month));  					 
				 	proc_stmt.setString(3, week1_sdate);
					proc_stmt.setString(4, week1_edate);					
					proc_stmt.setString(5,day1);
					          
					proc_stmt.execute();									
			}catch(Exception e){ out.println(e);}
		}catch(Exception r) {out.println(r);}
%>
<form>
<table  class="ft" border=1 style="border-collapse: collapse;" width="100%" cellpadding="7">
			<tr class="tdH">   
				<td colspan="16" align="center">Statement I showing the status of maintenance of CWSS / Board owned Schemes for the month of  <%=monthval%> <%=year%></td>
			</tr>
			<tr>    
				<th width="5%" align="center"> Sl.No</th>
				<th>Region</th>
				<th width="5%">Designed Quantity (ML)</th>  
				<th width="5%">Pumped Quantity (ML)</th>
				<th width="5%">Supplied/ Billed Quantity (ML)</th>
				<th width="5%">Demand Raised (Rs. in lakhs)</th>
			<!-- 	<th width="5%">Maintenance Expenditure (Rs. In Lakhs)</th>-->	
			<!-- 	<th width="5%">Profit / Loss (Rs. in lakhs)</th> -->	
			<!-- <th width="5%">% of Profit / Loss</th> -->	
			</tr>
			
	
	<%
	String QTY_DESIGN="0",PUMPING_QTY="0",SUPPLIED_QTY="0",DRAMT="0",   EXP="0", PRFVALUE="0",COLLECTION_OF_CURRENT_MONTH="0";
	double total_QTY_DESIGN=0.0,total_PUMPING_QTY=0.0, total_SUPPLIED_QTY=0.0,total_DRAMT=0.0,total_EXP=0.0,total_PRFVALUE=0.0,total_COLLECTION_OF_CURRENT_MONTH=0.0;

	  String qry="SELECT REGION_ID,sum(QTY_DESIGN) as QTY_DESIGN,sum(PUMPING_QTY) as PUMPING_QTY,sum(SUPPLIED_QTY) as SUPPLIED_QTY,  "+
		  	" sum(DRAMT) as DRAMT,sum(CRAMT) as CRAMT,sum(EXP) as EXP,sum(DMDAMT) as DMDAMT,sum(PRFVALUE) as PRFVALUE,  "+
		  	" sum(COLLECTION_OF_CURRENT_MONTH) as COLLECTION_OF_CURRENT_MONTH  "+
		 	" FROM PMS_DCB_DASH_BOARD_STMT1 where YEAR="+year+" and MONTH="+month+" group by REGION_ID  order by REGION_ID"; 
	     	int row=0;
	 pie_chart_xml="<graph showNames='1'  decimalPrecision='2'>";    
	 ResultSet rs=obj_new2.getRS(qry);
		while(rs.next())
		{
			row++;
			String REGION_ID =obj_new3.isNull(rs.getString("REGION_ID"),1);
		  	REGNAME=obj_new3.getValue("com_mst_all_offices_view","office_name"," where office_id="+REGION_ID);
			QTY_DESIGN=obj_new3.isNull(rs.getString("QTY_DESIGN"),1);
			PUMPING_QTY=obj_new3.isNull(rs.getString("PUMPING_QTY"),1);
			SUPPLIED_QTY=obj_new3.isNull(rs.getString("SUPPLIED_QTY"),1);
			DMDAMT=obj_new3.isNull(rs.getString("DMDAMT"),1);
			CRAMT=obj_new3.isNull(rs.getString("CRAMT"),1);
			DRAMT=obj_new3.isNull(rs.getString("DRAMT"),1);
			EXP=obj_new3.isNull(rs.getString("EXP"),1);
			PRFVALUE=obj_new3.isNull(rs.getString("PRFVALUE"),1);
			COLLECTION_OF_CURRENT_MONTH=obj_new3.isNull(rs.getString("COLLECTION_OF_CURRENT_MONTH"),2);
			
			try
			{
				total_QTY_DESIGN+=Double.parseDouble(QTY_DESIGN);
				total_PUMPING_QTY+=Double.parseDouble(PUMPING_QTY);
				total_DMDAMT+=Double.parseDouble(DMDAMT);
				total_SUPPLIED_QTY+=Double.parseDouble(SUPPLIED_QTY);
				total_DRAMT+=Double.parseDouble(DRAMT);
				total_EXP+=Double.parseDouble(EXP);
				total_PRFVALUE+=Double.parseDouble(PRFVALUE);
				
			}catch(Exception e) {				
				
			}
			String clr="";
			 if (row%2==1)
		    	  clr="bgcolor='#CCFFFF'";
	  %>
			  <tr <%=clr%>>
				<td align="center"><%=row%></td>
				<td><a href="Dash_board_stmt1_reg.jsp?REGION_ID=<%=REGION_ID%><%=parameter%>"><%=REGNAME%></td>
				<td width="10%" align="right"><%=df.format(Double.parseDouble(QTY_DESIGN))%></td>
				<td width="10%" align="right"><%=df.format(Double.parseDouble(PUMPING_QTY))%></td>
				<td width="10%" align="right"><%=df.format(Double.parseDouble(SUPPLIED_QTY))%></td>			 
				<td width="10%" align="right"><%=df1.format(Double.parseDouble(DMDAMT))%></td>
			<!--	<td width="10%" align="right"><%=df1.format(Double.parseDouble(EXP))%></td>-->	
		<!--  <td width="10%" align="right"><%=df1.format(Double.parseDouble(PRFVALUE))%></td> -->	
 			  </tr>
		<%
		}
		%>
		 <tr bgcolor='#CCFFFF'> 
				<th colspan="2"  align="right">Total</th>
				<th width="10%" align="right"><%=df.format(total_QTY_DESIGN)%></th>
				<th width="10%" align="right"><%=df.format(total_PUMPING_QTY)%></th>
				<th width="10%" align="right"><%=df.format(total_SUPPLIED_QTY)%></th>			 
				<th width="10%" align="right"><%=df1.format(total_DMDAMT)%></th>				 
				<!-- <th width="10%" align="right"><%=df1.format(total_EXP)%></th>-->	
				<!-- <th width="10%" align="right"><%=df1.format(total_PRFVALUE)%></th> -->
 			  </tr>
</table><center>
		<table border=0 width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse;"  align="center">
		<tr><td align="left"><input type=button value="Exit" onclick="window.close()"></td></tr></table></center>
</form>
</body>
</html>