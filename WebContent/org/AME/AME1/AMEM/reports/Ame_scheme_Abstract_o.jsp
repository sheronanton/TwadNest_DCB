<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*,Servlets.AME.AME1.AMEM.servlets.Controller,Servlets.AME.AME1.AMEM.Bean.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.text.DecimalFormat"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> A.M.E Abstract for Financial Year </title>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript">

	function reload(region_office_id,circle_office_id,office_id,pyear)
	{
		 window.open("Ame_Scheme_Abstract_newHO.jsp?flag=C&circle_office_id="+circle_office_id+"&office_id="+office_id+"&region_office_id="+region_office_id+"&pyear="+pyear+"");
	  
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
		String region_office_id=obj.setValue("region_office_id",request);
		String Office_id =obj.setValue("office_id",request);// Ame_scheme_Item_Wise_new_o.jsp;;// obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
		String flag="R";
		try
		{
		if (Office_id.equals(""))
			Office_id = "0";
		String Office_Name = obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + obj.setValue("region_office_id",request) + "  ");
		Office_Name+="->"+obj.getValue("com_mst_all_offices_view","OFFICE_NAME", "where OFFICE_ID=" + obj.setValue("circle_office_id",request) + "  ");

		String query="select main_item_sno,sub_item_sno,group_sno,disdesc from PMS_AME_MASTER_VIEW order by main_item_sno,sub_item_sno,group_sno ";
		
		ResultSet rs=obj.getRS(query);
		
		
		  flag=obj.setValue("flag",request);
 
		  Pms_Ame_Masters data=null;		
		List <Pms_Ame_Masters> list=new ArrayList<Pms_Ame_Masters>();
		int innerrow=0;
		while (rs.next())
		{
				data=new Pms_Ame_Masters(); 
				data.setMainsno(rs.getInt("main_item_sno"));
				data.setSubsno(rs.getInt("sub_item_sno"));
				data.setGroupsno(rs.getInt("group_sno"));
				data.setDisplayinfo(obj.isNull(rs.getString("disdesc"),1));
				list.add(data); 
				innerrow++;
		}		  
		 
		rs=null;
		int row=0;
		%>
		<div id='dataid'>
			<table border="1">
			<tr>
				<td align="center" colspan="<%=(innerrow+2) %>"> A.M.E Abstract for Financial Year </td>
			</tr>			
			<tr>
				<td align="center" colspan="<%=(innerrow+2) %>"> &nbsp;&nbsp;&nbsp;  Financial Year  &nbsp;:&nbsp; <%=pyear %></td>
			</tr>
			<tr>
					<td align="left" colspan="<%=(innerrow+2) %>"><%=Office_Name%></td>							 
			</tr>
			<% out.println("</tr>"); %>
				<td>Sl.No</td>
				<td width='20%'>Office Name</td>
			<%
			region_office_id=obj.setValue("region_office_id",request);
			String circle_office_id=obj.setValue("circle_office_id",request);
			for(Pms_Ame_Masters data1 : list)
			{								
					out.println("<td align='center'  width='7%'>"+data1.getDisplayinfo()+"</td>"); 
			}
			out.println("</tr>");
			query=" select distinct office_id as office_id ,  office_name from pms_dcb_reg_cir_div_sch where region_office_id="+region_office_id+" and circle_office_id="+circle_office_id+" order by circle_office_id";
			rs=obj.getRS(query);
			while(rs.next())
			{
				String office_id=rs.getString("office_id");
											 
				out.println("<tr><td align='center' >"+(++row)+"</td><td align='left'><a href=javascript:reload("+region_office_id+","+circle_office_id+","+office_id+",'"+pyear+"')>"+obj.isNull(rs.getString("office_name"),1)+"</td>");				
				for(Pms_Ame_Masters data1 : list) 
				{				
					int msno=data1.getMainsno();
					int ssno=data1.getSubsno(); 
					int gsno=data1.getGroupsno();
					
					String cond=" where GROUP_SNO="+gsno+" AND MAIN_ITEM_SNO="+msno+" and SUB_ITEM_SNO="+ssno+" and FIN_YEAR='"+pyear+"' and office_id="+office_id+"   "  ;				
					String amount="0";				 
					amount=obj_data.getGSValue("PMS_AME_ABSTRACT_DATA","AM_EST_AMT",cond,"office_id");
						out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(obj.isNull(amount,1)))+"</td>");
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
	 
		
		}catch(Exception e)
		{
			out.println(e);
			
		} 
		
	%>
	 
	 
</form>
</body>
</html>