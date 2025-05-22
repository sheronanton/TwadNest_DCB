<html>
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@page import="java.util.Calendar,java.util.Hashtable"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>TWAD - PMS Scheme Mapping </title>
</head>    
<!--<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen" />
--><link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
<script language='javascript' src='../../../../../jquery-3.6.0.js'></script>
<script type="text/javascript" src="../scripts/transcat.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">   
	$(document).ready(function()
	{
	$('#twadsch_sno').change(function () 
	{  
	    //// $(this).css("width","170px");
		 var val=document.getElementById("twadsch_sno").options[document.getElementById("twadsch_sno").selectedIndex].text;
		 document.getElementById("selsch").innerHTML=val;
	});});

	function edit_load(a,b,c)
	{
		var id=document.getElementById("sch_sno");
		document.getElementById("SCH_MAPPING_SNO").value=c;   	
		var len=id.length;	 
		for (var i=0;i<len;i++)
		{ 
			var tt=document.getElementById("sch_sno").options[i].value;		 
			if (a==tt) 
			{
				document.getElementById("sch_sno").options[i].selected=true;			 
			}
			else
			{
				document.getElementById("sch_sno").options[i].selected=false;
			}
	 	}
		 var id1=document.getElementById("fassch");
		var len1=id1.length;	 
		for (var i=0;i<len1;i++)
		{ 
			var tt=document.getElementById("fassch").options[i].value;		 
			if (b==tt) 
			{     
				document.getElementById("fassch").options[i].selected=true;			 
			}
			else
			{
				document.getElementById("fassch").options[i].selected=false;
			}
	 	}  
}
  
</script>
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
Connection con=obj.con();
obj.createStatement(con);
if (userid == null) {

	response.sendRedirect(request.getContextPath() + "/index.jsp");
}
 

String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
if (Office_id.equals("")) Office_id="0";
String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
String twadsch=obj.combo_str("PMS_AME_TWAD_DPR_SCH_MASTER","PROJECT_DESC","TWAD_PROJECT_ID","TWAD_PROJECT_ID"," order by PROJECT_DESC","twadsch_sno","a"," style=' width: 562' " );
String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO","SCH_SNO", 
		 " where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") and SCH_SNO in ( select SCHEME_SNO from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" and METER_STATUS='L') and sch_status_id in (4,9) and SCH_CATEGORY_ID=4","sch_sno","g","  style='width: 462'" );
String sch1=obj.combo_str("PMS_MST_PROJECTS_VIEW","PROJECT_NAME","PROJECT_ID","PROJECT_ID", 
		 " where     OFFICE_ID="+Office_id+" and status='L' and sch_type_id in (select SCH_TYPE_ID from PMS_DCB_APPLICABLE_SCH_TYPE  )  order by PROJECT_NAME ","fassch","","class='select' style='width: 262'   onKeyDown=key_pass(event,'sch_sno','yearcomm',1)" );
	String map=obj.setValue("map",request);
	String twadsch_sno=obj.setValue("twadsch_sno",request);
	String sch_sno=obj.setValue("sch_sno",request);
	String office_id=obj.setValue("office_id",request); 
	String fassch=obj.setValue("fassch",request);
	String project_id=obj.getValue("PMS_SCH_MASTER","PROJECT_ID"," where sch_sno="+sch_sno);
	 
	 if (map.equalsIgnoreCase("Submit")  )
	{
		 Hashtable ht=new Hashtable();  
		 int max=0;
		 
		 
		 if (map.equalsIgnoreCase("0")   || sch_sno.equalsIgnoreCase("0")
				 || office_id.equalsIgnoreCase("0") || project_id.equalsIgnoreCase("0") || fassch.equalsIgnoreCase("0"))
		 {
			 %>
			 <script type="text/javascript">
			 	alert("Please select scheme  ");
			 	window.location.href="twadsch_map.jsp";
			 </script>
			 <%
			  
		 }
		 else
		 {
			 try
			 {
				 max=obj.getMax("PMS_AME_MST_SCH_MAPPING","SCH_MAPPING_SNO","");
			 }catch(Exception e)
			 {
				 max=obj.getMax("PMS_AME_MST_SCH_MAPPING","SCH_MAPPING_SNO","");
			 }
			 ht.put("SCH_MAPPING_SNO" ,max);	
			 ht.put("SCH_SNO" ,sch_sno);	
			 ht.put("OFFICE_ID" ,Office_id);  
			 ht.put("UPDATED_BY_USER_ID" , "'"+userid+"'");
			 ht.put("UPDATED_TIME" ,"clock_timestamp()");
			 ht.put("TWAD_PROJECT_ID" ,twadsch_sno);
			 ht.put("FAS_PROJECT_ID" ,fassch);
			 int row=obj.recordSave(ht,"PMS_AME_MST_SCH_MAPPING",con);  
		 }
	}	
	 if (map.equalsIgnoreCase("Update")  )
		{
		 
		 if (map.equalsIgnoreCase("0")  || sch_sno.equalsIgnoreCase("0")
				 || office_id.equalsIgnoreCase("0") || project_id.equalsIgnoreCase("0") || fassch.equalsIgnoreCase("0"))
		 {
			 %>
			 <script type="text/javascript">
					alert("Please select scheme  ");
					window.location.href="twadsch_map.jsp";
			 </script>
			 <%
			
		 }
		 else
		 {
			 Hashtable ht=new Hashtable();
			 Hashtable htcond=new Hashtable();
				String SCH_MAPPING_SNO=obj.setValue("SCH_MAPPING_SNO",request);
			 ht.put("SCH_SNO" ,sch_sno);	
			 ht.put("OFFICE_ID" ,Office_id);  
			 ht.put("UPDATED_BY_USER_ID" , "'"+userid+"'");
			 ht.put("UPDATED_TIME" ,"clock_timestamp()");
			 ht.put("TWAD_PROJECT_ID" ,twadsch_sno);
			 ht.put("FAS_PROJECT_ID" ,fassch);
			 htcond.put("SCH_MAPPING_SNO" ,SCH_MAPPING_SNO);	
			 int row=obj.recordSave(ht,htcond,"PMS_AME_MST_SCH_MAPPING",con);
		 }
	}
	 else if (map.equalsIgnoreCase("Delete")  )
	 {
			String SCH_MAPPING_SNO=obj.setValue("SCH_MAPPING_SNO",request);
		 String row=obj.delRecord("PMS_AME_MST_SCH_MAPPING"," where SCH_MAPPING_SNO="+SCH_MAPPING_SNO +" and OFFICE_ID="+Office_id,con);
	 }
	   
%>      
<body>
<form action="twadsch_map.jsp" method="post">

			<table align="center" width="85%" border="1"  style="border-collapse: collapse;border-color: skyblue"  cellpadding="5" cellspacing="0" bordercolor="darkgray">
							<tr class="tdH"> <th align="center" colspan="2">PMS - FAS Scheme Mapping  </th></tr> 
 		    				<tr  class='bld1'><td colspan="4" align="center"><%=Office_Name%></td></tr>
 		    				<tr> <td> PMS Scheme Name</td><td><%=sch%></td></tr>
 		    				 <tr> <td> TWAD Scheme Name</td><td><%=twadsch%></td></tr>  
 		    				<tr> <td> FAS Scheme Name</td><td><%=sch1%></td></tr>
 		    				<!--<tr><td width="25%"> Selected Scheme</td><td id='selsch'></td></tr> -->
 			
 			<tr>
 				<td> 
	 				<input type="submit" name="map" value="Submit">  
	 				<input type="submit" name="map" value="Update">
	 				<input type="submit" name="map" value="Delete">
	 				<input type="button" value="Exit" onclick="window.close()">
 				</td>
 				<Td><a href="javascript:window.open('excelUpload.jsp','','')">..</a><a href="javascript:window.open('fin_year_excelUpload.jsp','','')">..</a></Td>
 			</tr>
 			</table>
 			<table align="center" width="85%" border="1"  style="border-collapse: collapse;border-color: skyblue"  cellpadding="5" cellspacing="0" bordercolor="darkgray"> 
 			<tr class="tdH">
 			<td>&nbsp;</td>
 			<td  width="5%">&nbsp;</td>
 			<td align="center" width="30%"  >  PMS Scheme </td>
 			<td align="center" width="30%">FAS Scheme Name </td>
 				 <td align="center"  width="30%"> TWAD DPR Scheme </td> 
 			</tr>
 			
  			<%
  			    
  			try
  			{
  				 ResultSet rs=obj.getRS(" select SCH_MAPPING_SNO,SCH_SNO,TWAD_PROJECT_ID,FAS_PROJECT_ID from PMS_AME_MST_SCH_MAPPING where OFFICE_ID="+Office_id+"");
  				String sch_name="",pr_name="",fasschdesc="";
  				String prsno="",fasschsno="",SCH_MAPPING_SNO="";
  				String schsno="";  int row=0;
  				while (rs.next())
  				{
  						
  					   row++;
  	  				    prsno=obj.isNull(rs.getString("TWAD_PROJECT_ID"),1);
  	  					fasschsno=obj.isNull(rs.getString("FAS_PROJECT_ID"),1);
  	  					SCH_MAPPING_SNO=rs.getString("SCH_MAPPING_SNO");
  	  					schsno=rs.getString("SCH_SNO");
  	  				    sch_name=obj.getValue("PMS_SCH_MASTER", "SCH_NAME"," where SCH_SNO="+schsno);
  	  					fasschdesc=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_NAME"," where PROJECT_ID="+fasschsno);
  	  					 
  	  					
  	  				  if (prsno.equalsIgnoreCase("0"))
  	  					pr_name="";
  	  					else
  	  					pr_name=obj.getValue("PMS_AME_TWAD_DPR_SCH_MASTER", "PROJECT_DESC"," where TWAD_PROJECT_ID="+prsno);
  	  					
  				%>  
  				<tr>	<td width="5%"><%=row%></td>
  						<td><a href="javascript:edit_load(<%=schsno%>,<%=fasschsno%>,<%=SCH_MAPPING_SNO%>)">Edit</a></td>
 						<td><%=sch_name%></td>
 						<td><%=fasschdesc%></td>
 						 <td><%=pr_name%></td> 
 						  
 						
 						
 				</tr>
  				<%	
  				}   
  				con=null;  
  			}catch(Exception e) { out.println(e);}
  			%>
  			
 				<input type="hidden" name="office_id"  id="office_id" value="<%=Office_id%>">
 			<input type="hidden" name="SCH_MAPPING_SNO"  id="SCH_MAPPING_SNO" value="0">
 		<%
 		  
	}catch(Exception e)
	{
		out.println(e);		
	}
 		%>  
 		</table>	
</form>

</body>
</html>