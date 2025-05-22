<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*,Servlets.AME.AME1.AMEM.servlets.Controller,Servlets.AME.AME1.AMEM.Bean.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.text.DecimalFormat"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Monthly Scheme Expenditure Summary</title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
 <script type="text/javascript">

	function reload(region_office_id,circle_office_id,office_id,pmonth,pyear)
	{
		 window.open("Ame_scheme_Performance_new.jsp?flag=C&office_id="+office_id+"&region_office_id="+region_office_id+"&circle_office_id="+circle_office_id+"&pmonth="+pmonth+"&pyear="+pyear);
	}

</script>
</head>
<body>
 

<form name="Performance">
<% 
		HttpSession session1 = request.getSession(false);
		String userid = (String) session1.getAttribute("UserId");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int day = cal.get(java.util.Calendar.DATE);
		int month = cal.get(java.util.Calendar.MONTH) + 1;
		int year = cal.get(java.util.Calendar.YEAR);
		String html="",datahtml="";	
		Controller obj = new Controller();
		Controller obj_data = new Controller();
		
		
		String pmonth=obj.setValue("pmonth",request);
		String pyear=obj.setValue("pyear",request);
 		
		Connection con=obj.con();
		DecimalFormat df=new DecimalFormat("0.00");
		
		obj.createStatement(con);
		obj_data.createStatement(con);
		if (userid == null) {
		
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		
		String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		String flag="R";
		try
		{
		if (Office_id.equals(""))
			Office_id = "0";
		String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + obj.setValue("region_office_id",request) + "  ");
		Office_Name+="->"+obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + obj.setValue("circle_office_id",request) + "  ");
		String query="SELECT PERFORM_DESC_SNO,PERFORM_DESC ,DISPLAYORDER,TO_BE_PRINTED,UNITS,datatype FROM PMS_AME_MST_DESC " +
		  			" WHERE ACTIVE_STATUS='A'  AND to_be_printed  ='Y' and PERFORM_DESC_SNO in (select perform_desc_sno from pms_ame_mst_desc where datatype in (1,2)) order by  DISPLAYORDER";
		
		ResultSet rs=obj.getRS(query);
		
		
		  flag=obj.setValue("flag",request);
 
		Pms_Ame_Mst_Desc data=null;		
		List <Pms_Ame_Mst_Desc> list=new ArrayList<Pms_Ame_Mst_Desc>();
		int innerrow=0;
		while (rs.next())
		{
				data=new Pms_Ame_Mst_Desc();
				data.setPerformdescsno(obj.isNull(rs.getString("PERFORM_DESC_SNO"),1));
				data.setPerformdesc(obj.isNull(rs.getString("PERFORM_DESC"),1));
				data.setUnits(obj.isNull(rs.getString("UNITS"),1));
				data.setDatatype(obj.isNull(rs.getString("datatype"),1));
				list.add(data); 
				innerrow++;
		}		  
		if ("C".equalsIgnoreCase(flag))
		{
			rs=null;
			int row=0;
			String region_office_id=obj.setValue("region_office_id",request);
			%>
			<div id='dataid'>
				<table border="1">
				<tr>
					<td align="center" colspan="<%=(innerrow+2) %>">Scheme Performance</td>
				</tr>
				<tr>
					<td align="center" colspan="<%=(innerrow+2) %>">Month &nbsp;:&nbsp; <%=obj.month_val(pmonth) %> &nbsp;&nbsp;&nbsp;&nbsp; Year &nbsp;:&nbsp; <%=year %></td>
				</tr>				
				<tr>
					<td align="left" colspan="<%=(innerrow+2) %>"><b><%=Office_Name%></b></td>							 
				</tr>
				<tr>
					<td align="left" colspan="<%=(innerrow+2) %>"><%=Office_Name%></td>							 
				</tr>
				<% out.println("</tr>"); %>
					<td>Sl.No</td>
					<td width='20%'>Office Name</td>
				<% 
				
				for(Pms_Ame_Mst_Desc data1 : list)
				{								
						out.println("<td align='center'>"+data1.getPerformdesc()+data1.getUnits()+"</td>"); 
				}
				out.println("</tr>"); 
				  region_office_id=obj.setValue("region_office_id",request);
				String circle_office_id=obj.setValue("circle_office_id",request);
				query=" select distinct office_id as office_id ,  office_name from pms_dcb_reg_cir_div_sch where region_office_id="+region_office_id+" and circle_office_id="+circle_office_id+" order by circle_office_id";
				rs=obj.getRS(query);
				while(rs.next()) 
				{
									
					String office_id=rs.getString("office_id");
					out.println("<tr><td align='center'>"+(++row)+"</td><td align='left'><a href='javascript:reload("+region_office_id+","+circle_office_id+","+office_id+","+pmonth+","+pyear+")'>"+obj.isNull(rs.getString("office_name"),1)+"</td>");
					for(Pms_Ame_Mst_Desc data1 : list) 
					{				
						String performancesno=data1.getPerformdescsno();				
						String cond=" where  PERFORM_DESC_SNO="+performancesno+" AND office_id="+office_id+" and MONTH ="+pmonth+" AND YEAR="+pyear ;
						String amount="0";				 
						if ("1".equalsIgnoreCase(data1.getDatatype()) ||  "2".equalsIgnoreCase(data1.getDatatype()))
						{  
							amount=obj_data.getValue("pms_ame_div_sch_sum","QTY_OR_AMOUNT",cond);
							out.println("<td align='right'>"+df.format(Double.parseDouble(obj.isNull(amount,1)))+"</td>");
						} 				 
					} 
					out.println("</tr>");
				 }
				 %>
				    <tr>  
			 <td colspan=<%=(innerrow+2)%> align="center">  
			 	<div class="img"><a href="javascript:document.title = 'Budget Estimate'; window.print();" >Print	</a></div>
			 	</td>
			 </tr>
			 </table>
			 </div>
			<%
		}
		
		
		}catch(Exception e)
		{
			out.println(e);
			
		} 
		
	%>
	
	<input type="hidden" value="<%=flag%>" id='flag'>
	<input type="hidden" value="0" id='region_office_id'>
		
</form>
</body>
</html>