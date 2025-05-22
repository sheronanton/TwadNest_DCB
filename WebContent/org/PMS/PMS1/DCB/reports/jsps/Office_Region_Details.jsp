<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Office Region Wise Details</title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript"> 
	$( function()
	{ 
	$("#pyear").change( 
	function () {
		var pyear=document.getElementById("pyear").value;
	    var pmonth=document.getElementById("pmonth").value;
	    var xmlobj=createObject();  
		var url="../../../../../../reg_menu_index?command=Setting&pyear="+pyear+"&pmonth="+pmonth;
		xmlobj.open("GET", url, true); 
		xmlobj.onreadystatechange = function() 
		{
			set_m(xmlobj );  
		}
  		xmlobj.send(null);
		}
		);
		});     
		function set_m(xmlobj)
      {
     		if(xmlobj.readyState==4)
   		{ 
      			 if(xmlobj.status==200)
       	 	{
      				var baseResponse=xmlobj.responseXML.getElementsByTagName("response")[0];
      				var tagcommand=baseResponse.getElementsByTagName("msg")[0].firstChild.nodeValue;;
      				alert(tagcommand)
       	 	}
   		}
      }
	
	</script>
					
</head>
	<body>
		<%	Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con=null;
		ResultSet rs1=null,rs2=null;
		Controller obj=null,obj2=null;
		String Region_Filter_cond="",
			   Region_Filter_cond1="",
			   Region_Title_Head="Region Wise ";
		String new_cond=Controller.new_cond;
		int bencount=0;
		try
		{
  			obj=new  Controller();
  			obj2=new  Controller();
  			con=obj.con();
  			obj.createStatement(con);
  			obj2.createStatement(con);
  			  try
				{ 
	   				userid=(String)session.getAttribute("UserId");
				}catch(Exception e) {userid="0";}
				
				if(userid==null)
				{ 
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				 
				 Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				 
	  			
				if (Office_id.equals("")) Office_id="0";
			
				String Region_Id=obj.getValue("COM_MST_ALL_OFFICES_VIEW","REGION_OFFICE_ID","where OFFICE_ID="+Office_id);
				
				 
				
				
				if (Region_Id.equalsIgnoreCase("0"))
				{
					Region_Filter_cond="   ";
					Region_Filter_cond1="";
					Region_Title_Head="";
				}
				else
					{
						Region_Filter_cond =" and  REGION_OFFICE_ID="+Region_Id;
						Region_Filter_cond1=" and OFFICE_ID in (select OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where REGION_OFFICE_ID="+Region_Id+" and OFFICE_LEVEL_ID='DN' )";
					}
				
				
				PreparedStatement ps=con.prepareStatement(" SELECT REGION_OFFICE_ID,OFFICE_LEVEL_ID,OFFICE_ID,OFFICE_NAME FROM COM_MST_ALL_OFFICES_VIEW WHERE OFFICE_LEVEL_ID='RN'  " + Region_Filter_cond);
				rs1=ps.executeQuery();
				
				
			
				
				
				
				
			}catch(Exception e) {userid="0";}

			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};

	

 

%>	<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
	<table align="center" width="85%" border="1"  cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse"   borderColor="#92c2d8">
			<tr bgcolor="#408080"> <td  colspan="4" align="center"><font color='white'> <%=Region_Title_Head%> Office Region Wise Details  </font></td></tr>
		  <tr><td> Report Month and Year</td><Td><select class="select" id="pmonth" name="pmonth"  style="width:80pt" >
		  <option value="0">Select</option>
   			<%
			  for (int j=1;j<=12;j++)
			  {
			   %>
			    <option value="<%=j%>"><%=cmonth[j]%></option>
			  <%} %>
			 </select> 
			 <select class="select"  id="pyear" name="pyear"  style="width:80pt"  >
   				<option value="0">Select</option>
			  <%
			  for (int i=2009;i<=year;i++)
			  {
			   %>
			  <option value="<%=i%>"><%=i%></option>
			  <%} %>
			  </select> </tD>
  		 </tr>
			
			<tr>
				<td class="fnt" width="20%">&nbsp;</td>
				<td class="fnt" align="center" >Division</td>  
				<td class="fnt"  align="center" width="20%">Total Beneficiaries</td>
				<td class="fnt"  align="center" width="20%"> Transaction Status</td>
			</tr>
		<%
		String rid="",oid="";
		while (rs1.next()) 
		{ 
			rid=rs1.getString("REGION_OFFICE_ID");
			
		%>
			<tr>
				<td class="fnt"  colspan="3">Region : <%=rs1.getString("OFFICE_NAME") %></td>
				 
			</tr>
			<tr>
				<td colspan="4">
				<%
					rs2=obj.getRS("SELECT REGION_OFFICE_ID, OFFICE_LEVEL_ID,OFFICE_ID, OFFICE_NAME FROM COM_MST_ALL_OFFICES_VIEW WHERE REGION_OFFICE_ID="+rid+" and OFFICE_LEVEL_ID='DN' and OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP)");
				%>
				<table align="center" width="100%" border="1" style="BORDER-COLLAPSE: collapse"   borderColor="#92c2d8">
				<%
				
					while (rs2.next())
					{  
					oid	=rs2.getString("OFFICE_ID");
				
					bencount=obj.getCount("PMS_DCB_MST_BENEFICIARY ", " where  "+new_cond+" office_id="+oid);
					 
				%>
				<tr>
				
					<td align="left" ><font size=2>&nbsp;&nbsp;<%=rs2.getString("OFFICE_ID") %></font></td>
					<td align="left" ><font size=2>&nbsp;&nbsp;<%=rs2.getString("OFFICE_NAME") %></font></td>
	          		<td  width="20%"  align="center"><font size=2><a href="common_ben.jsp?oid=<%=oid%>&fun=1"><%=bencount%></a></font></td>
					<td  width="20%"  align="center"><font size=2><a href="count_region.jsp?oid=<%=oid%>">Click</a></font></td>
					
				</tr>  
				<%  } %>
				</table>
				</td>
				
			</tr>	
					
		<%}
		int netbencount=obj.getCount("PMS_DCB_MST_BENEFICIARY ", "where  "+new_cond+" OFFICE_ID !=0 and OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP)  " + Region_Filter_cond1);
		%>
		<tr >
					<td colspan=2 align="right" > Grand Total :</td><td colspan=1 align="center" bgcolor="#408080"> <font color="white"><%=netbencount%></font></td><td></td>
		</tr> 
		<tr>
					<td colspan=4 align="center">   <input class="fb2"   type="button" id="" value="Back" onclick="javascript:window.history.back()" /></td>
		</tr> 
	</table>	
 
	</body> 
</html>
 