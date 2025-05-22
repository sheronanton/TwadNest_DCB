<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="Servlets.AME.AME1.AMEM.servlets.*,java.util.*,java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>A.M. Estimate Report</title>
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
</head>
<body>
<%
try
{
	Calendar cal = Calendar.getInstance();
	String[] monthArrv = { "0","1", "2", "3", "4", "5", "6","7", "8", "9", "10", "11", "12" };
	String []monthArr ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	int day = cal.get(Calendar.DATE);
	int month = cal.get(Calendar.MONTH) + 1;
	int year = cal.get(Calendar.YEAR);
	 	Controller obj=new Controller();
		Connection con=obj.con();
		obj.createStatement(con);
		 String	userid = (String) session.getAttribute("UserId");
		 String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			if (Office_id.equals("")) Office_id="0";
			
		String selyear1=obj.setValue("year",request);
		int r=0;
		 //String qry="select MAIN_ITEM_SNO,MAIN_ITEM_DESC from PMS_AME_MST_MAIN_ITEM order by Group_sno,MAIN_ITEM_SNO";
		 
			String qry=" SELECT a.main_item_sno as main_item_sno, " +
			"  a.main_item_desc as main_item_desc, " +
			"  b.sub_item_sno as sub_item_sno, " +
			"  case when SUB_ITEM_SDESC='-' then b.sub_item_desc  else  b.SUB_ITEM_SDESC   end as sub_item_desc " +
			" FROM PMS_AME_MST_SUB_ITEM b, " +
			"  PMS_AME_MST_MAIN_ITEM a " +
			" WHERE a.main_item_sno=b.main_item_sno and  a.GROUP_SNO=1 " +
			" ORDER BY a.group_sno, " +
			"  a.main_item_sno,sub_item_sno";
		 PreparedStatement ps=con.prepareStatement(qry);
		 ResultSet rs=ps.executeQuery();
		int prvmonth=obj.prv_month(year,month);
		int prvyear=obj.prv_year(year,month);
	String sel="",sely="";
%>   
<form>
<table align="center" border="1" bordercolor="skyblue"   cellpadding="10" cellspacing="0" width="100%">
		<tr>
			<th colspan="2" align="center"> Head Wise Expenditure </th>
		</tr>
		<tr>
			<td>Month</td>
			<td>  
			<select class="select" id="smonth" name="smonth" style="width: 110pt;" onchange="report_period_verify(this,document.getElementById('year'))" >
					<option value="0">Select</option>
				 	<%
				 		for (int j = 1; j <=12  ; j++) 
				 		{
				 			if (prvmonth==j) sel="selected"; else sel="";
					%>
						<option value="<%=monthArrv[j]%>" <%=sel%> ><%=monthArr[j]%></option>
					<% }  %>
				</select>
			 </td>
		</tr>   
		<tr>
			<td> Year </td>
			<td> 	
					 <select class="select" id="year" name="year" style="width: 110pt;" onchange="report_period_verify(document.getElementById('smonth'),this)" >
				        <option value="0">Select</option>			 
					<% 	for (int i = year-1; i <=year; i++) 
					{ 
						if (prvyear==i) sely="selected";
					%>
						<option value="<%=i%>" <%=sely%> > <%=i%></option>
					<% 	} %>     
					</select>
			</td>
		</tr>
</table>
<br>
			<%
				ArrayList main_=new ArrayList();
				ArrayList sub_=new ArrayList();				
				String msno="0";
				String ssno="0";
				int c1=obj.getCount("PMS_AMT_ITEMS_COUNT","");
				int []gnso=new int[c1];  
				int []msnoc=new int[c1];
				int []ssnoc=new int[c1];
				String table1="<table width='100%' align='center' border='1' bordercolor='skyblue'  cellpadding='10' cellspacing='0'>";
				table1+="<tr><td colspan='2'>&nbsp;</td>";
				//String table2="<table align='center' border='1' bordercolor='skyblue'  cellpadding='10' cellspacing='0'>";
				String table2="<tr><td width='3%' valign='top'>Sl.No</td><td width='10%' valign='top'>Scheme Name</td>";
				int row=0;
				int cols=1;
				String prv_msno="0";
				String main_desc="";
				String sub_desc="";
		 		while (rs.next())
		 		{
		 			row++;		 			
		 			msno=rs.getString("MAIN_ITEM_SNO");
		 			ssno=rs.getString("sub_item_sno");
		 			main_desc=rs.getString("MAIN_ITEM_DESC");
		 			int c=obj.getCount("PMS_AME_MST_SUB_ITEM"," where main_item_sno="+msno);
		 			sub_desc=rs.getString("sub_item_desc");
		 			gnso[row-1]=Integer.parseInt("1");
		 			msnoc[row-1]=Integer.parseInt(msno);
		 			ssnoc[row-1]=Integer.parseInt(ssno);
		 			if (Integer.parseInt(msno)!=Integer.parseInt(prv_msno))
		 			{ 
		 				table1+="<td align='center' width='25%' id=r"+row+" colspan="+c+" valign='top'>"+main_desc+"</td>";
		 				table2+="<td width='7%' valign='top'>"+sub_desc+"</td>";
		 				cols=1;
		 			}else
		 			{
		 				cols++;
		 				table2+="<td width='7%' valign='top'>"+sub_desc+"</td>";
		 			}	 			
		 			 
		 			prv_msno=msno;
		 		}
		 		table1+="</tr> "+table2+"</tr> ";
		 	//	table1+=" </table>";  
		 		int newrow=0;
		 		String sch_sno="0";
		 		String temp_sno="0";
		 		String qry_1=" select sch_name,sch_sno from pms_sch_master where sch_sno in (SELECT distinct sch_sno FROM PMS_AME_TRN_ABSTRACT    )";
		 		PreparedStatement ps1=con.prepareStatement(qry_1);
			 	ResultSet rs1=ps1.executeQuery();
			 	int erow=0;
			 	while(rs1.next())
			 	{
			 		erow++;
			 		sch_sno=rs1.getString("sch_sno"); 
			 		table1+="<tr><td valign='top'>"+erow+"</td><td width='10%' valign='top'>"+rs1.getString("sch_name")+"</td>";
			 		for(int i=0;i<row;i++)
			 		{
			 			String amt=obj.getValue("PMS_AME_TRN_ABSTRACT","AM_EST_AMT"," where sch_sno="+sch_sno+" and  main_item_sno="+msnoc[i]+" and sub_item_sno="+ssnoc[i]+"  --and FIN_YEAR='2015-2016'");
			 			if (!temp_sno.equals(sch_sno))
				 		{
				 			table1+="<td width='7%' align='right' valign='top'>"+amt+"</td>";				 	
				 		}	 		
			 		}
			 		table1+="</tr>";
			 	} 
			 	table1+=" </table>";
		 	%>
		 	<%=table1%> 
		 	<table width='100%' align='center' border='1' bordercolor='skyblue'  cellpadding='10' cellspacing='0'>
			<tr>    
				<th colspan="2" align="center">
				<input type="submit" name="Report" value="Report">
				<input type="reset" value="Exit" onclick= "javascript:self.close()" />  </th>
			</tr> 
			</table>
			<a href="ame_new_reports_excel.jsp">excel</a>
		
<%
}catch(Exception e ) 
{
	out.println(e);
}
%>
</form>
</body>
</html>