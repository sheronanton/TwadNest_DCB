<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*" %>
<%@page import="java.sql.PreparedStatement"%> 
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.*"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Pms_Ame_Item_Breakup"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<script type="text/javascript">
	function report_open()
	{
		var pyear,Office_id;
		try { Office_id=document.pms_ame_reports.Office_id.value;} catch(e) {};
		try { pyear=document.pms_ame_reports.inp_year.value;} catch(e) {};
		params = "?pyear=" +pyear +"&Office_id=" +Office_id;
		window.open('ame_bud_est_exp_reg_rpt_xls.jsp'+params,'Abstract ','width=400,height=200')
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
	String sum_label="";
	int col_count=0;
	int head_size=0;
	String ag_sno_="0",project_id="0";
	DecimalFormat df=new DecimalFormat("0.00");
	TreeSet hs=new TreeSet();  
	TreeSet column_sum=null;
	PreparedStatement ps=con.prepareStatement(br.toString());	
	StringBuffer headhtml=new StringBuffer("");
	
	ResultSet rs=obj.getRS("select ACC_GROUP_SNO,ACC_GROUP_DESC from PMS_AME_ACC_GROUP order by ACC_GROUP_SNO");	
	while(rs.next())
	{
		col_count++;
		head_size++;
		ag_sno_=obj.isNull(rs.getString(1),1); 
		headhtml.append("<th>"+obj.isNull(rs.getString(2),1)+"("+ag_sno_+")<br>(Rs.lakhs)<br>("+chrs[col_count-1]+")</th>");
		if(col_count==1)
		sum_label+=chrs[col_count-1];
		else
		sum_label+="+"+chrs[col_count-1];
		hs.add(ag_sno_);
		
	}	 
	//col_count=col_count+4;
	ResultSet rs_amt=ps.executeQuery();
	double []values=new double[head_size];
	double est_tot=0.0,bud_tot=0.0;
	double net_total=0.0;
 	String prv_msg="TWAD,HO"; 
	%>
	<table width="95%" align="center"> 
		<tr>
		 <th colspan="<%out.print(col_count+5);%>">&nbsp;</th>
		</tr>
		<tr> 
			<td colspan="<%out.print(col_count+5);%>" align="center">  <b> Estimate , Budget and Item Wise Expenditure 
			  for  <%=fin_year%></b> &nbsp; 
			</td>
		</tr> 
		<tr>
			<th colspan="<%out.print(col_count+5);%>" align="left">TWAD,HO</th>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
			<td colspan="4" align="center" style="border-left-color: black;" ><b>Expenditure incurred</b> </td> 
		</tr>		
		<tr> 
			<th>Sl.No</th>
			<th>Region Name</th>
			<th>Estimate Amount (Rs.lakhs)</th>
			<th>Budget Amount (Rs.lakhs)</th>
			<%
				out.println(headhtml.toString()); 
			%>
			<th>Total (Rs.lakhs)<br><%=sum_label%></th></tr>
			<%
			
			Pms_Ame_Item_Breakup  item_obj=new Pms_Ame_Item_Breakup();
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
				<td><a href="ame_bud_est_exp_cir_rpt.jsp?pyear=<%=inp_year%>&Office_id=<%=Office_id%>"><%=office_name%></a></td>
				
				<td align="right"><%=df.format(am_amt)%></td>
				<td align="right"><%=df.format(bud_amt)%></td>
				<%
				double row_total=0.0;
				int i=0;
				Object[] ir=hs.toArray();
				
				while (i < ir.length)
				{
					String ag_sno=ir[i].toString();
					item_obj.setAcgroup(Integer.parseInt(ag_sno));
					double amt_res=item_obj.getAmt_net_reg(Integer.parseInt(Office_id)); 
					out.println("<td align=right>"+df.format(amt_res/100000)+"</td>");
					i++;			
					values[i-1]+=(amt_res/100000);
					row_total+=(amt_res/100000);
				}  
				%><td align="right"><%=df.format(row_total)%></td> 
			</tr>	  
		<%
				net_total+=row_total;
			}
	%>
	<tr><th colspan="2"  align="right">Total</th>
		<td align="right"><%=df.format(est_tot)%></td>
		<td align="right"><%=df.format(bud_tot)%></td>
		<%
			int j=0;
			while(j<col_count) 
			{%>
			<td align="right"><%=df.format(values[j])%></td>
			<%
			j++;
			}
		%>
		<td align="right"><%=df.format(net_total)%></td>
	</tr>
	 <tr>
			<td colspan="<%out.print(col_count+5);%>" align="center">
				<input type="button" value="Back" onclick="window.history.go(-1)">&nbsp;<input type="button" name="Report" value="Excel Report" onclick="report_open()">
			</td>
			</tr>
	</table> 
			<input type="hidden" name="inp_year" value="<%=inp_year%>"/>
			<input type="hidden" name="Office_id" value="<%=Office_id%>"/>
	<%	
		}catch(Exception e)
		{
			out.println(e); 
		}
	%>
</form>
</body>
</html> 