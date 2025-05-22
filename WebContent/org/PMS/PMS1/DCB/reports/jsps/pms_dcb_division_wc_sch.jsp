<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*,java.sql.*,Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.text.DecimalFormat"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Water Charges Journal Data -  Circle | TWAD Nest - Phase II</title>
<link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
</head>
<body>
	<% 
	
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Connection con=null;
		Controller obj=null,obj2=null;
		String userid="",Office_id="0",Office_name="";
		String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
		try
		{
		
			try
			{ 
   				userid=(String)session.getAttribute("UserId");
			}catch(Exception e) {userid="0";}
			
			if(userid==null)
			{ 
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			obj=new  Controller();
  			obj2=new  Controller();
  			con=obj.con();
  			obj.createStatement(con);
  			obj2.createStatement(con);
  			
  			String pyear=obj.setValue("year",request);
  			String pmonth=obj.setValue("month",request);
  			
  			Office_id=obj.setValue("rptoff",request);
			if (Office_id.equals("")) Office_id="0";
			Office_name=obj.getValue("COM_MST_ALL_OFFICES_VIEW","Office_name","where OFFICE_ID="+Office_id);
			StringBuffer SQL=new StringBuffer();			
			SQL.append("SELECT m.amount, \n");
			SQL.append("  CR_DR_INDICATOR, \n");
			SQL.append("  m.sub_ledger_code, \n");
			SQL.append("  case when m.SCH_SNO is not null then (SELECT SCH_NAME FROM PMS_SCH_MASTER WHERE SCH_SNO=m.SCH_SNO \n");
			SQL.append("  ) else '-' end  AS sch_desc , \n");
			SQL.append("  m.sub_ledger_type_code, \n");
			SQL.append("  (select sub_ledger_type_desc from com_mst_sl_types where sub_ledger_type_code=m.sub_ledger_type_code) as sub_ledger_type_desc ,");
			SQL.append("  m.scheme_type_id, \n");
			SQL.append("  case when m.SCH_SNO is not null then m.sch_sno else 0 end as SCH_SNO, \n");
			SQL.append("  m.account_head_code , \n");
			SQL.append("  (SELECT ACCOUNT_HEAD_DESC \n");
			SQL.append("  FROM COM_MST_ACCOUNT_HEADS \n");
			SQL.append("  WHERE ACCOUNT_HEAD_CODE=m.account_head_code \n");
			SQL.append("  ) AS ACCOUNT_HEAD_DESC,Remarks \n");
			SQL.append(" FROM PMS_DCB_JOURNAL_DETAILS m \n");
			SQL.append(" WHERE m.cashbook_year="+pyear);
			SQL.append(" AND m.cashbook_month ="+pmonth);
			SQL.append(" AND m.office_id      ="+Office_id); 
			PreparedStatement ps=con.prepareStatement(SQL.toString());
			ResultSet rs=ps.executeQuery();
			DecimalFormat df=new DecimalFormat("0.00");
		%>
		
		<table align="center" width="85%" border="1"  cellspacing="0" cellpadding="3">
			<tr> 
			<th  colspan="4" align="center">Water Charges Calculation Freeze - <%=Office_name%></th></tr>   
				  <tr>
				  		<td>  Month and Year</td>
				  		 
			  			 
  		 </tr>
		</table>			
		<table align="center" width="85%" border="1"  cellspacing="0" cellpadding="3">
				<Tr bgcolor="#C6DEFF" class="tdText" valign="top"  >
						<td  align="center" > Sl.No</td>
						<td align="center">A/c Head Code and  Description</td>
						<td align="center">SL Type Code (Project ID) </td>
						<td align="center">Sub Legder Code </td>
						<td align="center">CR</td>
						<td align="center">DR</td>
						<td align="center">Particulars</td>
				</Tr>
			
			<%
				String off_id="0",off_name="",crdr="";
				int row=0;
				double netcr=0.0,netdr=0.0;
				String temp="0",temp1="0.0";
				double _amt=0.0;
				while (rs.next())
				{
					off_id=rs.getString(1);
					row++;
					crdr=rs.getString("CR_DR_INDICATOR");
					if (crdr.equalsIgnoreCase("DR"))
					{
						temp=rs.getString("amount");
						temp1="";
						_amt =Double.parseDouble(temp);
						netdr+=_amt;
					}else
					{
						temp1=rs.getString("amount");
						temp="";
						_amt =Double.parseDouble(temp1);
						netcr+=_amt;
					}
					%><tr>
					<td align="center"><%=row%></td>
					<td align="left"><%=rs.getString("account_head_code")%>-<%=rs.getString("ACCOUNT_HEAD_DESC")%></td>
					<td align="left"><%=rs.getString("sub_ledger_type_desc")+"("+rs.getString("sub_ledger_type_code")+")"%></td> 
					<td align="left"><%=rs.getString("sch_desc")+"("+rs.getString("sch_sno")+")"%></td>
					<td align="right"><%=temp%></td>
					<td align="right"><%=temp1%></td>
						<td align="left"><%=rs.getString("Remarks")%></td> 
					</tr>
				<%  
				}  
			%>
				<tr bgcolor="#c0c0ff" style="font-weight: bold">  
					<td align="right" colspan="4">Total</td>
					<td align="right"><%=df.format(netcr)%></td>
					<td align="right"><%=df.format(netdr)%></td>
					<td>&nbsp;</td> 
				</tr>
				<tr>
					<td colspan=7 align="center"><a href="javascript:window.history.go(-1)">Back </a></td>
				</tr>
		<%
		}catch(Exception e)
		{
			
			out.println(e);
			
		}
	%>
		</table>
</body>
</html>