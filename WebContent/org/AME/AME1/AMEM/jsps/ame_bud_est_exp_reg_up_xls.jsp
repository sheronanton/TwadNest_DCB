<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*" %>
<%@page import="java.sql.PreparedStatement"%> 
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.*"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Pms_Ame_Item_Breakup"%>
<html>
 
<head>
<script type="text/javascript">
function report_open()
{
	var pyear,Office_id,upto_month;
	try { Office_id=document.pms_ame_reports.Office_id.value;} catch(e) {};
	try { pyear=document.pms_ame_reports.inp_year.value;} catch(e) {};
	try { upto_month=document.pms_ame_reports.upto_month.value;} catch(e) {};
	params = "?pyear=" +pyear +"&Office_id=" +Office_id+"&upto_month="+upto_month; 
	window.open('ame_bud_est_exp_off_uprptxls.jsp'+params,'Abstract ','width=400,height=200')
} 
</script> 
</head>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>	
<body>
<form name="pms_ame_reports">
<% 
try 
{  
	HttpSession session1 = request.getSession(false);
	String	userid = (String) session1.getAttribute("UserId");
	Calendar cal = Calendar.getInstance();
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	Controller obj=new Controller();
	Controller obj1=new Controller();
	Connection con=null;
	con=obj.con();
	obj.createStatement(con);
	if (userid == null) {

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	
	String inp_year=obj.setValue("pyear",request);
	String upto_month=obj.setValue("upto_month",request);
	String upto_month_val=" Upto "+obj.month_val(upto_month);
	
	
	StringBuffer br=new StringBuffer();
	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
			"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
					+ userid + "')");
	year=Integer.parseInt(inp_year);
	String fin_year=year+"-"+(year+1);
	String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	if (Office_id.equals("")) Office_id="0";
	
	String region_office_id =obj.setValue("region_office_id",request);
	String circle_office_id =obj.setValue("circle_office_id",request);// obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
	if (region_office_id.equals("")) region_office_id="5000";
	String office_cond="";
		if (Integer.parseInt(region_office_id)==5000)
			office_cond=" (1=1) and  " ;  
		else   
			office_cond=" office_id in (select office_id from com_mst_all_offices_view where office_level_id='DN' and region_office_id="+region_office_id+")  and ";
	
		response.setContentType("application/vnd.ms-excel");
		response.setHeader ("Content-Disposition", "attachment;filename=\" Budget Estimate,A.M. Estimate Entry, Expenditure Item Account Head Mapping.xls\"");
		br.append(" SELECT sch_.region_office_id,sch_.office_name,am.am_est_amt AS am_est_amt,BUDGET_EST_AMT  ");
		br.append(" FROM (SELECT a.office_id AS region_office_id,a.office_name FROM com_mst_all_offices_view a  ");
		br.append(" WHERE a.office_level_id='RN' AND EXISTS ( SELECT DISTINCT office_id  ");
		br.append(" FROM pms_dcb_mst_beneficiary_metre  WHERE meter_status='L'  AND meter_status  ='L'  AND office_id    IN      (SELECT office_id  ");
		br.append(" FROM com_mst_all_offices_view   WHERE office_level_id='DN' AND region_office_id =a.office_id  )  ");
		br.append(" )   )sch_  LEFT OUTER JOIN  ");
		br.append(" (SELECT FIN_YEAR, SUM(am_est_amt) AS am_est_amt , region_office_id  ");
		br.append(" FROM (SELECT a.FIN_YEAR,office_id , (SELECT region_office_id  FROM com_mst_all_offices_view  WHERE office_id=a.office_id   ) AS region_office_id,  ");
		br.append(" SUM(a.am_est_amt) AS am_est_amt FROM pms_ame_trn_abstract a WHERE FIN_YEAR='"+fin_year+"' GROUP BY FIN_YEAR, office_id  )  GROUP BY FIN_YEAR,region_office_id  ");
		br.append(" )am ON am.region_office_id =sch_.region_office_id  ");
		br.append(" LEFT OUTER JOIN   (SELECT FIN_YEAR,  SUM(BUDGET_EST_AMT) AS BUDGET_EST_AMT , region_office_id FROM  ");
		br.append(" (SELECT a.FIN_YEAR, a.OFFICE_ID, (SELECT region_office_id  FROM com_mst_all_offices_view   WHERE office_id=a.office_id   )  AS region_office_id,  ");
		br.append("  SUM(BUDGET_EST_AMT) AS BUDGET_EST_AMT FROM PMS_AME_TRN_BUDGET a WHERE FIN_YEAR='"+fin_year+"' GROUP BY FIN_YEAR, OFFICE_ID  ");
		br.append("  ) GROUP BY FIN_YEAR, region_office_id  )budget  ");
		br.append(" ON budget.region_office_id=sch_.region_office_id AND budget.FIN_YEAR       =am.FIN_YEAR ORDER BY region_office_id  ");
	char []chrs={'A','B','C','D','E','F'};
	String sum_label="",sum_label2="";
	int col_count=0;
	int head_size=0;
	String ag_sno_="0",project_id="0";
	DecimalFormat df=new DecimalFormat("0.00");
	TreeSet hs=new TreeSet();  
	TreeSet column_sum=null;
	PreparedStatement ps=con.prepareStatement(br.toString());	
	StringBuffer headhtml=new StringBuffer("");	
	int next_year=obj.next_year(Integer.parseInt(inp_year),Integer.parseInt(upto_month));
	int prv_month=obj.prv_month(Integer.parseInt(inp_year),Integer.parseInt(upto_month));
	int cur_month_end_date=obj.prv_month(Integer.parseInt(inp_year),Integer.parseInt(upto_month));
	int cur_month_year=Integer.parseInt(inp_year); 
	int april_year=obj.april_year(Integer.parseInt(inp_year),Integer.parseInt(upto_month));
	 
	
	double est_tot=0.0,bud_tot=0.0;
	double net_total=0.0,net_total2=0.0;
 	String prv_msg="TWAD,HO"; 
 	Pms_Ame_Item_Breakup  item_obj=new Pms_Ame_Item_Breakup();    
 	String date_Display1=item_obj.date_Display(4,year,prv_month,next_year);
 	String date_Display2=item_obj.date_Display(Integer.parseInt(upto_month),next_year,Integer.parseInt(upto_month),next_year);
 	
	
	
	ResultSet rs=obj.getRS("select ACC_GROUP_SNO,ACC_GROUP_DESC from PMS_AME_ACC_GROUP order by ACC_GROUP_SNO");	
	while(rs.next())
	{
		col_count++;
		head_size=head_size+2;
		ag_sno_=obj.isNull(rs.getString(1),1); 
		headhtml.append("<th>"+obj.isNull(rs.getString(2),1)+"<br> Upto "+date_Display1+" (Rs.lakhs)<br>("+chrs[col_count-1]+") </th>");
		headhtml.append("<th>Currnet "+obj.isNull(rs.getString(2),1)+"<br>"+date_Display2+"(Rs.lakhs)<br>("+chrs[col_count+2]+")</th>");
		if(col_count==1)
		sum_label+=chrs[col_count-1];
		else
		sum_label+="+"+chrs[col_count-1]; 
		
		if(col_count==1)  
			sum_label2+=chrs[col_count+2];
			else
				sum_label2+="+"+chrs[col_count+2];
		
		
		hs.add(ag_sno_);
		
	}	 
	double []values=new double[head_size];
	double []values1=new double[head_size];
	//col_count=col_count+4;
	ResultSet rs_amt=ps.executeQuery();
	
	%>
	<table width="95%" align="center" border="1"> 
		<tr>
		 <th colspan="<%out.print(col_count+9);%>">&nbsp;</th>
		</tr>
		<tr> 
			<td colspan="<%out.print(col_count+9);%>" align="center">  <b> Estimate , Budget and Item Wise Expenditure 
			  for  <%=fin_year%></b> &nbsp; <Br> <%=upto_month_val %>
			  
			</td>
		</tr>  
		<tr><th colspan="<%out.print(col_count+9);%>" align="left">TWAD,HO</th></tr>
		<tr>
		    <td colspan="4">&nbsp;</td> 
			<td colspan="8" align="center" style="border-left-color: black;" ><b>Expenditure incurred</b> </td> 
		</tr>		
		<tr> 
			<th>Sl.No</th>
			<th>Region Name</th>
			<th>Estimate Amount (Rs.lakhs)</th>
			<th>Budget Amount (Rs.lakhs)</th>
			<%
				out.println(headhtml.toString()); 
			%>
			<th>Upto Total (Rs.lakhs)<br><%=sum_label%></th><th>Currnet Total (Rs.lakhs)<br><%=sum_label2%></th></tr>
			<%			
			Double am_amt=0.0,bud_amt=0.0;
			String office_name="",sch_type="0",sch_sno="0"; 
			int row=0;
			while(rs_amt.next())
			{
				row++;	
				office_name=obj1.isNull(rs_amt.getString("office_name"),2);
				Office_id=obj1.isNull(rs_amt.getString("region_office_id"),2);
				am_amt= Double.parseDouble(obj1.isNull(rs_amt.getString("am_est_amt"),1));
				bud_amt= Double.parseDouble(obj1.isNull(rs_amt.getString("BUDGET_EST_AMT"),1));
 				item_obj.setYear(year);
				item_obj.setOffice_id(Integer.parseInt(Office_id)); 
				est_tot+=am_amt;
				bud_tot+=bud_amt;
			%>
			<tr>  
				<td><%=row%></td> 
				<td><%=office_name%></td>				
				<td align="right"><%=df.format(am_amt)%></td>
				<td align="right"><%=df.format(bud_amt)%></td>
				<%
				double row_total=0.0,row_total2=0.0;
				int i=0;
				Object[] ir=hs.toArray();	
				while (i < ir.length)
				{ 
					String ag_sno=ir[i].toString();
					item_obj.setAcgroup(Integer.parseInt(ag_sno));					
					double amt_res_up=0.0;					
					if (Integer.parseInt(upto_month)==4)
					{
						amt_res_up=0.0;	
					}else
					{
						amt_res_up=item_obj.getAmt_net_regprv(Integer.parseInt(Office_id),4,year,prv_month,next_year,1);
					}
					double amt_res_cur=item_obj.getAmt_net_regprv(Integer.parseInt(Office_id),Integer.parseInt(upto_month),next_year,Integer.parseInt(upto_month),next_year,1);
					out.println("<td align=right>"+df.format(amt_res_up/100000)+"</td><td align=right>"+df.format(amt_res_cur/100000)+"</td>");
					i++;			
					values[i-1]+=(amt_res_up/100000);
					values1[i-1]+=(amt_res_cur/100000);					
					row_total+=(amt_res_up/100000);
					row_total2+=(amt_res_cur/100000);
				}  
				%><td align="right"><%=df.format(row_total)%></td> <td align="right"><%=df.format(row_total2)%></td> 
			</tr>	  
		<%
				net_total+=row_total;
				net_total2+=row_total2;
			}
	%>
	<tr><th colspan="2"  align="right">Total</th>
		<td align="right"><%=df.format(est_tot)%></td>
		<td align="right"><%=df.format(bud_tot)%></td>
		<% 
			int j=0; 
			while(j<col_count) 
			{%>
			<td align="right"><%=df.format(values[j])%></td><td align="right"><%=df.format(values1[j])%></td>
			<%
			j++;
			}
		%>
		<td align="right"><%=df.format(net_total)%></td><td align="right"><%=df.format(net_total2)%></td>
	</tr>
	</table> 
	<%	
		}catch(Exception e)
		{
			out.println(e); 
		}
	%> 
</form></body></html> 