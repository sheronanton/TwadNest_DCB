<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*" %>
<%@page import="java.sql.PreparedStatement"%> 
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.*"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Pms_Ame_Item_Breakup;"%><html>
<head>
  <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
</head>
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
	response.setContentType("application/vnd.ms-excel");
	response.setHeader ("Content-Disposition", "attachment;filename=\" Budget Estimate,A.M. Estimate Entry, Expenditure Item Account Head Mapping.xls\"");
	String inp_year=obj.setValue("pyear",request);
	StringBuffer br=new StringBuffer();
	String Office_id = obj.setValue("Office_id",request);
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
	
	  
		br.append(" SELECT sch_.circle_office_id, sch_.office_name, am.am_est_amt AS am_est_amt, BUDGET_EST_AMT ");
		br.append(" FROM ");
		br.append("   (SELECT a.office_id as circle_office_id,  a.office_name  FROM com_mst_all_offices_view a  WHERE a.office_level_id='CL' and region_office_id="+Office_id+" ");  
		br.append(" 	  AND EXISTS    (SELECT DISTINCT office_id    FROM pms_dcb_mst_beneficiary_metre    WHERE meter_status='L' ");
		br.append("   AND meter_status  ='L'   AND office_id   in (select office_id from com_mst_all_offices_view where office_level_id='DN' and circle_office_id=a.office_id)   ) ");
		br.append("   )sch_ ");
		br.append(" LEFT OUTER JOIN ");
		br.append("   ( select FIN_YEAR,sum(am_est_amt) as am_est_amt ,circle_office_id ");
		br.append("     from (SELECT a.FIN_YEAR,office_id  ,(select circle_office_id from com_mst_all_offices_view where office_id=a.office_id) as ");
		br.append("     circle_office_id, SUM(a.am_est_amt) AS am_est_amt FROM pms_ame_trn_abstract a   WHERE FIN_YEAR='"+fin_year+"' "); 
		br.append("     GROUP BY FIN_YEAR,office_id  )group by FIN_YEAR,circle_office_id "); 
		br.append("   )am ON am.circle_office_id =sch_.circle_office_id ");
		br.append(" LEFT OUTER JOIN ");
		br.append("     ( select FIN_YEAR,sum(BUDGET_EST_AMT) as BUDGET_EST_AMT ,circle_office_id ");
		br.append("       from (SELECT a.FIN_YEAR,  a.OFFICE_ID,(select circle_office_id from com_mst_all_offices_view where office_id=a.office_id) as ");
		br.append("       circle_office_id, SUM(BUDGET_EST_AMT) AS BUDGET_EST_AMT    ");
		br.append("       FROM PMS_AME_TRN_BUDGET a  WHERE FIN_YEAR='"+fin_year+"'  GROUP BY FIN_YEAR, OFFICE_ID ");
		br.append("      )group by FIN_YEAR,circle_office_id  ");
		br.append("   )budget ON budget.circle_office_id=sch_.circle_office_id ");
		br.append(" AND budget.FIN_YEAR=am.FIN_YEAR ORDER BY circle_office_id  ");
		int col_count=0;	
		int head_size=0; 
		char []chrs={'A','B','C','D','E','F'};
		String sum_label="";
	String ag_sno_="0",project_id="0";
	DecimalFormat df=new DecimalFormat("0.00"); 
	TreeSet hs=new TreeSet();  
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
	ResultSet rs_amt=ps.executeQuery();
	double []values=new double[head_size];
	double est_tot=0.0,bud_tot=0.0;
	double net_total=0.0;
	%>
		<table width="95%" align="center" border=1>
		<tr>
		 <th colspan="<%=col_count%>">&nbsp;</th>
		</tr>
		<tr> 
			<td colspan="<%out.print(col_count+5);%>" align="center">  <b> Estimate , Budget and Item Wise Expenditure 
			  for  <%=fin_year%></b><br> &nbsp; 
			</td>
		</tr>
		<tr>
			<th colspan="<%out.print(col_count+5);%>" align="left"><%=Office_Name%></th>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
			<td colspan="4" align="center" style="border-left-color: black;" ><b>Expenditure incurred</b> </td> 
		</tr>
		<tr> 
			<th>Sl.No</th>
			<th>Circle Name</th>
			<th>Estimate Amount (Rs.lakhs)</th>
			<th>Budget Amount (Rs.lakhs)</th>
			<%
				out.println(headhtml.toString()); 
			%><th>Total (Rs.lakhs)<br><%=sum_label%></th></tr>
			<%
			Pms_Ame_Item_Breakup  item_obj=new Pms_Ame_Item_Breakup();
			Double am_amt=0.0,bud_amt=0.0;
			String office_name="",sch_type="0",sch_sno="0"; 
			int row=0;
			while(rs_amt.next())
			{
				row++;	
				office_name=obj1.isNull(rs_amt.getString("office_name"),2);
				Office_id=obj1.isNull(rs_amt.getString("circle_office_id"),2);
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
				int i=0;
				Object[] ir=hs.toArray();
				double row_total=0.0;
				while (i < ir.length)
				{
					String ag_sno=ir[i].toString();
					item_obj.setAcgroup(Integer.parseInt(ag_sno));
					double amt_res=item_obj.getAmt_net_cir(Integer.parseInt(Office_id)); 
					out.println("<td align=right>"+df.format(amt_res/100000)+"</td>");
					i++;    
					values[i-1]+=(amt_res/100000);
					row_total+=(amt_res/100000);
				}  
				%><td align="right"><%=df.format(row_total)%></td></tr><%
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
	 
	</table> 
			 
	<%
		}catch(Exception e)
		{
			out.println(e); 
		}
	%>
</form>
</body>
</html>