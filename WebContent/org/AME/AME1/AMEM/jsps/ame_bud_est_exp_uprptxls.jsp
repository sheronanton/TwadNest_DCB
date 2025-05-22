<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*" %>
<%@page import="java.sql.PreparedStatement"%> 
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.*"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Pms_Ame_Item_Breakup"%>
<html>
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
	response.setContentType("application/vnd.ms-excel");
	response.setHeader ("Content-Disposition", "attachment;filename=\" Budget Estimate,A.M. Estimate Entry, Expenditure Item Account Head Mapping.xls\"");
	String inp_year=obj.setValue("pyear",request);
	String upto_month=obj.setValue("upto_month",request);
	String upto_month_val=" Upto "+obj.month_val(upto_month);
	
	
	StringBuffer br=new StringBuffer();
	String Office_id = obj.setValue("Office_id",request);
	year=Integer.parseInt(inp_year);
	String fin_year=year+"-"+(year+1);
	String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	if (Office_id.equals("")) Office_id="0";

	String region_id= obj.setValue("region_id",request);
	String circle_id= obj.setValue("circle_id",request);
	br.append(" select project_id,sch_type_id,sch_.sch_sno,sch_name,am.am_est_amt as am_est_amt,BUDGET_EST_AMT from  ");
	br.append(" ( select office_id,a.sch_name ,a.sch_sno,a.project_id,sch_type_id FROM pms_sch_master a WHERE EXISTS ");
	br.append(" ( ");
	br.append("  SELECT distinct scheme_sno,office_id,meter_status,sch_type_id  FROM pms_dcb_mst_beneficiary_metre where   meter_status='L'  and scheme_sno=a.sch_sno ");
	br.append("  and  office_id ="+Office_id+"    AND meter_status='L'   ) ");
	br.append(" )sch_ "); 
	br.append(" left outer join ( ");
	br.append(" select FIN_YEAR,SCH_SNO,OFFICE_ID, sum(am_est_amt)  as am_est_amt from pms_ame_trn_abstract  ");
	br.append(" where FIN_YEAR='"+fin_year+"' and OFFICE_ID="+Office_id+" ");
	br.append(" group by FIN_YEAR,OFFICE_ID,OFFICE_ID,SCH_SNO ");
	br.append(" )am ");
	br.append(" on am.OFFICE_ID=sch_.OFFICE_ID and am.SCH_SNO=sch_.sch_sno ");
	br.append(" left outer join ( ");
	br.append(" select FIN_YEAR,OFFICE_ID,SCH_SNO,  sum(BUDGET_EST_AMT)  as BUDGET_EST_AMT from PMS_AME_TRN_BUDGET  ");
	br.append(" where FIN_YEAR='"+fin_year+"' and OFFICE_ID="+Office_id+" ");
	br.append(" group by FIN_YEAR,OFFICE_ID,SCH_SNO ");
	br.append(" )budget ");
	br.append(" on budget.OFFICE_ID=sch_.OFFICE_ID and budget.SCH_SNO=sch_.sch_sno and budget.FIN_YEAR=am.FIN_YEAR and budget.SCH_SNO=am.sch_sno order by sch_sno");
	
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
	//col_count=col_count+4;
	ResultSet rs_amt=ps.executeQuery();
	double []values=new double[head_size];
	double []values1=new double[head_size];
	double est_tot=0.0,bud_tot=0.0;
	double net_total=0.0,net_total2=0.0;
	String prv_msg="TWAD HO-->"+obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+region_id+ "  ")+"-->"+obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+circle_id+ "  ")+"-->"+Office_Name.trim(); 
	%>
	<table width="95%" align="center" border="1"> 
		<tr>
		 <th colspan="<%out.print(col_count+9);%>">&nbsp;</th>
		</tr>
		<tr> 
			<td colspan="<%out.print(col_count+9);%>" align="center">  <b> Estimate , Budget and Item Wise Expenditure 
			  for  <%=fin_year%></b> &nbsp;   <Br> <%=upto_month_val %>
			</td> 
		</tr> 
		<tr>
			<th colspan="<%out.print(col_count+9);%>" align="left"><%=prv_msg%></th>
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
			<th>Upto Total (Rs.lakhs)<br><%=sum_label%></th><th>Currnet Total (Rs.lakhs)<br><%=sum_label2%></th></tr>
			<%			
		 
			Double am_amt=0.0,bud_amt=0.0;
			String office_name="",sch_type="0",sch_sno="0",str_name=""; 
			int row=0;
			while(rs_amt.next())
			{
				row++;	
				str_name=obj1.isNull(rs_amt.getString("sch_name"),2);
				sch_type=obj1.isNull(rs_amt.getString("sch_type_id"),2);
				sch_sno=obj1.isNull(rs_amt.getString("sch_sno"),2);
				project_id=obj1.isNull(rs_amt.getString("project_id"),2);
				am_amt= Double.parseDouble(obj1.isNull(rs_amt.getString("am_est_amt"),1));
				bud_amt= Double.parseDouble(obj1.isNull(rs_amt.getString("BUDGET_EST_AMT"),1));
				item_obj.setSch_type(Integer.parseInt(sch_type));
				item_obj.setYear(year);
				item_obj.setOffice_id(Integer.parseInt(Office_id)); 
				item_obj.setSchsno(Integer.parseInt(sch_sno));
				est_tot+=am_amt;
				bud_tot+=bud_amt;
			%>
			<tr> 
				<td><%=row%></td> 
				<td><%=str_name+"("+sch_sno+","+project_id+")"%></td>								
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
						amt_res_up=item_obj.getAmt_net_regprv(Integer.parseInt(Office_id),4,year,prv_month,next_year,4);
					}
					double amt_res_cur=item_obj.getAmt_net_regprv(Integer.parseInt(Office_id),Integer.parseInt(upto_month),next_year,Integer.parseInt(upto_month),next_year,4);
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
</form>
</body>
</html> 