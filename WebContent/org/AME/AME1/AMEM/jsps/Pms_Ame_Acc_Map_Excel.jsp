 <%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.*" %>
<%@ page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
<html>
<head>
   <link href="../../../../../css/AME.css" rel="stylesheet" media="screen" />
   <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
</head>
<body>
<%

	try 
	{  
		 
		Controller obj=new Controller(); 
		Connection con=obj.con();
		String mainitem=obj.setValue("mainitem",request); 
	 	String subitem=obj.setValue("subitem",request);
	 	String sch_type=obj.setValue("sch_type",request); 
	 	String ACC_GROUP_SNO=obj.setValue("ACC_GROUP_SNO",request); 
		String Cond_=" where (1=1) ";
		obj.createStatement(con); 
		if (!sch_type.equalsIgnoreCase("0"))
		{
			Cond_+=" and SCH_TYPE_ID="+sch_type;
		}
		if (!mainitem.equalsIgnoreCase("0"))
			Cond_+=" and MAIN_ITEM_SNO="+mainitem;
		
		if (!subitem.equalsIgnoreCase("0"))
			Cond_+=" and SUB_ITEM_SNO="+subitem;
		if (!ACC_GROUP_SNO.equalsIgnoreCase("0"))
			Cond_+=" and ACC_GROUP_SNO="+ACC_GROUP_SNO;
		String qry="	SELECT ac.*, "
			+ " mitem.MAIN_ITEM_DESC, "
			+ " sitem.SUB_ITEM_DESC, (select SCH_TYPE_DESC from   PMS_SCH_LKP_TYPE   where SCH_TYPE_ID=ac.SCH_TYPE_ID) as sch_type_desc "
			+ " ,(SELECT ACC_GROUP_DESC FROM PMS_AME_ACC_GROUP WHERE ACC_GROUP_SNO=ac.ACC_GROUP_SNO ) AS ACC_GROUP_DESC FROM "
			+ " (SELECT ACC_CODE_SNO,CODE_W_E_F, "
			+ " MAIN_ITEM_SNO," 
			+ " SUB_ITEM_SNO,"
			+ " ACCOUNT_HEAD_CODE,ACC_GROUP_SNO,"
			+ " ACC_DESC,SCH_TYPE_ID,UPDATED_DATE "
			+ " FROM PMS_AME_MST_ACC_HEAD_MAP  "+ Cond_
			+ "   "   
			+ " ) ac "    
			+ " LEFT JOIN "
			+ " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC FROM PMS_AME_MST_MAIN_ITEM "
			+ "  )mitem "
			+ " ON mitem.MAIN_ITEM_SNO=ac.MAIN_ITEM_SNO "
			+ " LEFT JOIN "
			+ " ( SELECT SUB_ITEM_SNO,SUB_ITEM_DESC FROM PMS_AME_MST_SUB_ITEM "
			+ "  )sitem "
			+ " ON sitem.SUB_ITEM_SNO=ac.SUB_ITEM_SNO order by SCH_TYPE_ID,ac.MAIN_ITEM_SNO,ac.SUB_ITEM_SNO  ";
			response.setContentType("application/vnd.ms-excel");
			response.setHeader ("Content-Disposition", "attachment;filename=\"Group wise Expenditure Item Account Head Mapping.xls\"");
%>    
		<table class="table" id="etable"  border="1"  align="center" width="100%" border="1" style="border-collapse: collapse;border-color: skyblue">  
		<tr>
			<th  class="tdH"  colspan="7">Group wise Expenditure Item Account Head Mapping </th>
		</tr>  
		<tr class="tdH" > 
			<th width="2%" align="center" >Sl.No</th>	
			<th width="6%" align="center">Scheme Type</th>
			<th width="10%" align="center">Account Group</th> 
			<th width="10%" align="center">Main Item</th>
			<th width="10%" align="center">Sub Item</th>
			<th width="2%" align="center">Account Head Code</th>
			<th width="20%" align="center">Account Head Description </th>			
		</tr>  
		<%
			int row=0;
			PreparedStatement ps=con.prepareStatement(qry);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{ row++;%>
				<tr>
				<td width="2%" align="center" ><%=row %></td>	
				<td width="6%" align="left"><%=obj.isNull(rs.getString("SCH_TYPE_DESC"),2)%></td>
				<td width="10%" align="left"><%=obj.isNull(rs.getString("ACC_GROUP_DESC"),2)%></td> 
				<td width="10%" align="left"><%=obj.isNull(rs.getString("MAIN_ITEM_DESC"),2)%></td>
				<td width="10%" align="left"><%=obj.isNull(rs.getString("SUB_ITEM_DESC"),2)%></td>
				<td width="2%" align="left"><%=obj.isNull(rs.getString("Account_head_code").toString(),2)%></td>
				<td width="20%" align="left"><%=obj.isNull(rs.getString("ACC_DESC"),2)%></td>			
			</tr>	
			<%}
		%>
      	</table>
 	<%  
	}catch(Exception e) 
	{
		out.println(e);		
	}
 	%>
 	</body>
</html>
