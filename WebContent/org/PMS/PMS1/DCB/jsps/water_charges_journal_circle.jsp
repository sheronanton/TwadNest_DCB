<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*,java.sql.*,Servlets.PMS.PMS1.DCB.servlets.Controller" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.text.DecimalFormat"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Water Charges Journal Data -  Circle | TWAD Nest - Phase II</title>
	<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
	
	<script type="text/javascript">
			function rld_frm()
			{
					var year=document.wc_div.pyear.value;
					var month=document.wc_div.pmonth.value;
					document.location.href="water_charges_journal_circle.jsp?month="+month+"&year="+year;

			}
			function submit_frm()
			{
					var year=document.wc_div.pyear.value;
					var month=document.wc_div.pmonth.value;
					document.location.href="water_charges_journal_circle.jsp?month="+month+"&year="+year;

			}
	</script>
</head>
<body>
<form name="wc_div" method="get">
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
  			
  			Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			if (Office_id.equals("")) Office_id="0";
			Office_name=obj.getValue("COM_MST_ALL_OFFICES_VIEW","Office_name","where OFFICE_ID="+Office_id);
			
			CallableStatement proc_stmt =null;
			proc_stmt=con.prepareCall("{call ben_select(?,?)}");
			proc_stmt.setInt(1, 2);
			proc_stmt.registerOutParameter(2,java.sql.Types.VARCHAR);
			proc_stmt.execute();
			String Name =obj.isNull(proc_stmt.getString(2), 1);
			String qry=" select distinct office_id from pms_dcb_div_dist_map where office_id "+
				       " in (select distinct office_id from com_mst_all_offices_view where region_office_id="+Office_id+" and office_level_id='DN') ";
			PreparedStatement ps=con.prepareStatement(qry); 
			ResultSet rs=ps.executeQuery();
			DecimalFormat df=new DecimalFormat("0.00");
		%>
		
		<table align="center" width="85%" border="1"  cellspacing="0" cellpadding="3">
			<tr> 
			<th  colspan="4" align="center">Water Charges Calculation Freeze - <%=Office_name%></th></tr> 
				  <tr>
				  		<td>  Month and Year</td>
				  		<td><select class="select" id="pmonth" name="pmonth"  style="width:80pt" >
		  					<option value="0">Select</option>
   							<%
			  					for (int j=1;j<=12;j++)
			  					{
			   				%>
			    			<option value="<%=j%>" 	<% if (j==Integer.parseInt(pmonth)) out.print("selected"); %>><%=cmonth[j]%></option>
			  				<%	} %>
						  </select>
						  
						  <select class="select"  id="pyear" name="pyear"  style="width:80pt"  >
   						  <option value="0">Select</option>
			  				<%
			  					for (int i=2009;i<=year;i++)
			  					{
			   				%>
			  					 <option value="<%=i%>"	<% if (i==Integer.parseInt(pyear)) out.print("selected"); %>><%=i%></option>
			  			    <%  } %>
			  			 </select>  &nbsp;&nbsp;<input type="button" value="View" onclick="rld_frm()">
			  			</td>
  		 </tr>
  		 
		</table>			
		<table align="center" width="85%" border="1"  cellspacing="0" cellpadding="3">
				<tr>
					<th>Sl.No</th>
					<th>Office Name</th>
					<th>Total CR Amount</th>
					<th>Total DR Amount</th>
				</tr>
			
			
			<%
				String off_id="0",off_name="";
				int row=0;
				double netcr=0.0,netdr=0.0;
				while (rs.next())
				{
					off_id=rs.getString(1);
					row++;
					CallableStatement cmd=null;
					cmd=con.prepareCall("{call PMS_DCB_WC_CHARGES_CIRCLE(?,?,?,?,?)}");
					cmd.setInt(1,Integer.parseInt(off_id));
					cmd.setInt(2,Integer.parseInt(pmonth));
					cmd.setInt(3,Integer.parseInt(pyear));
					cmd.registerOutParameter(4,java.sql.Types.DOUBLE);
					cmd.registerOutParameter(5,java.sql.Types.DOUBLE);
					cmd.execute();
					double dr_amt =cmd.getDouble(4);
					netdr+=dr_amt;
					double cr_amt =cmd.getDouble(5);
					netcr+=cr_amt;
					off_name=obj2.getValue("COM_MST_ALL_OFFICES_VIEW","Office_name","where OFFICE_ID="+off_id);
					%><tr>
					<td align="center"><%=row%></td>
					<td><a href="../reports/jsps/pms_dcb_division_wc_sch.jsp?year=<%=pyear%>&month=<%=pmonth%>&rptoff=<%=off_id%>"><%=off_name%></a></td>
					<td align="right"><%=df.format(cr_amt)%></td>
					<td align="right"><%=df.format(dr_amt)%></td>
					</tr>
				<%
				} 
			%>
				<tr bgcolor="#c0c0ff" style="font-weight: bold">  
					<td align="right" colspan="2">Total</td>
					<td align="right"><%=df.format(netcr)%></td>
					<td align="right"><%=df.format(netdr)%></td>
					</tr>
		<%
					
		
		 
		
		
		}catch(Exception e)
		{
			
			out.println(e);
			
		}
	%>
		<tr>
  		 	<td colspan="4" align="center"><input type="button" value="Freeze"></td> 
  		 </tr>
		</table>
</form>
</body>
</html>