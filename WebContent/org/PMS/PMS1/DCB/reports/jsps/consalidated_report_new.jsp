 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <%@page import="java.util.Calendar" %>
 <%@page import="java.util.*,java.sql.*"%>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller,Servlets.PMS.PMS1.DCB.Impl.Common_Impl"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>	
<head>
<link href="../../../../../../css/txtbox.css" rel="stylesheet"	media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>D C B (Demand Collection Balance) </title>
<script type="text/javascript">
function d_dcb()
{	 
	var a=document.getElementById('bentype').value;  
	var b=document.getElementById('schtype').value;
	 var rtype=document.getElementById('rtype').value;
	 var option=document.getElementById('option_type').value;
	 
	 var oid=0;
	 try
	 {
	 oid=document.getElementById('div').value;
	 }catch(e) {}
	var fyear=document.getElementById('pyear').value;
	var fmonth=document.getElementById('pmonth').value;
	var command="new_DCB";
	process_code=rtype;
	var sname="";
	try
	{
		if (fmonth!=0 && fyear!=0 ) 
		{
			if (rtype!=3 && rtype!=6 && rtype!=7 &&  rtype!=1 &&  rtype!=9)
		{
			   sname=document.getElementById('schtype').value;
			    var url="../../../../../../dcb_statement_new?oid="+oid+"&option="+option+"&process_code="+process_code+"&command="+command+"&fmonth="+fmonth+"&fyear="+fyear+"&bentype="+a+"&schtype="+b+"&sname="+sname+"";			    
	  			window.open(url);	  			 
	  	}  
	  	else  
	  	{ 
	  		 sname=document.getElementById('schtype').value;
	  	    var  url="../../../../../../dcb_statement_new?oid="+oid+"&option="+option+"&process_code="+process_code+"&command="+command+"&fmonth="+fmonth+"&fyear="+fyear+"&bentype="+a+"&schtype="+b+"&sname="+sname+"";
	  	    
	 	 	window.open(url);
	  	}
	  	}else  	{	  		alert("Select Year / Month / Scheme Category ")	  	}  
	}catch(e) {	sname="";	} 
}
function test()
{  
	window.close();
	window.location="mismenu.jsp";	 
}
</script>
</head>
<body>
<%
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
	 	String Region_id="0",combo="";		
		String userid="0",Office_id="",Office_Name="",table_heading="",table_column="",table_header="";;
		Connection con;
		String smonth="",syear="",html="",combo1="",combo2="";
		Controller obj=null;
		try
		{
		  obj=new  Controller();
		  con=obj.con();
		  obj.createStatement(con);
		 // combo1=obj.combo_str("","distinct BEN_TYPE_GROUP_NAME ","  BEN_TYPE_GROUP_NAME","BEN_TYPE_GROUP",""," order by BEN_TYPE_ID","bentype","   ","class=select style='width:250px' ");
		  combo1=obj.combo_str("PMS_DCB_BEN_TYPE","distinct BEN_TYPE_GROUP_NAME ","BEN_TYPE_GROUP_NAME","BEN_TYPE_GROUP"," where BEN_TYPE_GROUP not in (8,9) order by BEN_TYPE_GROUP", "bentype","","class=select style='width:250px' ");
 
		  combo2=obj.combo_str("PMS_SCH_LKP_CATEGORY","CATEGORY_DESC","SCH_CATEGORY_ID","where SCH_CATEGORY_ID=4 order by SCH_CATEGORY_ID","schtype","   ");
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
			Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
			syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
		  con.close();  
		}catch (Exception e) {out.println(e);
			
		}
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		 Common_Impl impl_obj=new Common_Impl();		  
		 Region_id=impl_obj.regionId(userid,obj);		 
		 combo=impl_obj.Div_RegionWise(Region_id,"div");
		String cb=obj.combo_str("PMS_DCB_APPLICABLE_SCH_TYPE","distinct SCH_TYPE_SUB_DESC ","SCH_TYPE_SUB_DESC","SCH_TYPE_ID_SUB","   ","schtype","","class=select style='width:250px' ");

%> 
<Table   class="table" id="" width=70% align="center" border="1"   cellspacing="0" cellpadding="6" style="BORDER-COLLAPSE: collapse" border="1" >
		<label name="msg" id="msg"></label>
				<tr class="tdH">
				<td colspan="2" class="fnt"><center><b> Demand, Collection and Balance Statement Booklet</b> </center></td>				</tr>
				 <% if (! Region_id.equalsIgnoreCase("0")) { %>
				<tr>	
				<td class="tdText" width="15%">Division Name </td><td width="50%"><%=combo%></td>
				</tr>
				<%} %> 
			    <tr  >
	          <td align="left" width="50%"> <font  class="fnt"> Year </font> </td>
			  <Td colspan="2"><select   id="pyear" name="pyear" >
			   <option value="0">-Select Year-</option>
			  <% 
			  for (int i=2011;i<=year;i++)
			  {
			   %>
			  <option value="<%=i%>"><%=i%></option>
			  <%} %>
			  </select> </tD>
			  </tr>
			 <tr>
			 <td align="left"><font class="fnt">  Month </font></td>	  	          
			 <td  colspan="1"><select  id="pmonth" name="pmonth"   >
			  <option value="0">-Select Month-</option>
			  <%
			  for (int j=1;j<=12;j++)
			  {
			  %>
			  <option value="<%=j%>"><%=monthArr[j]%></option>
			  <%} %>  
			 </select>
			 </Td>	        
          	</tr>
				<tr> 
					<td class="tdText" align="left"> <font  class="fnt">Report Type</font></td>
					<td>
						<select id="rtype">  
							<option value="0">Select</option>
							<option value="1">General Abstract</option>
							<option value="2">Consolidated Abstract</option>						 
							<option value="9">Scheme Type Abstract</option>
							<option value="4">Scheme Type Abstract (Beneficiary Type)</option>							
							<option value="5">Scheme Type-Beneficiary Type</option>
							<option value="6">District-Beneficiary Type</option>
							<option value="7">Scheme Category-Beneficiary Type</option>
						   <option value="6">District-Beneficiary Type</option>
						</select>
					</td>
					</tr> 
				  
				 <tr>
					<td class="tdText" align="left"> <font  class="fnt">Scheme Type</font></td>
					<td><%=cb%></td>						
				 </tr>
				 
				<tr> 
					<td class="tdText" align="left"> <font class="fnt">Beneficiary Type</font> </td>
					<td><%=combo1%></td>
				</tr> 
				<tr>
					<td colspan=2 align="center"><input class="fb2" type="button" id="" value="Print" onclick="d_dcb()"   />  <input class="fb2" type="button" id="" value="Exit" onclick="javascript:window.close()" />
					<img src="../../../../../../images/help_images.jpg" height="20px" width="45px"  style="vertical-align: bottom;" onclick="javascript:window.open('../../../DCB/jsps/help1.jsp?fn=57','mywindow','width=600,height=400,scrollbars=yes')" > </td>
				</tr> 
				<tr> 
			     	<td colspan="2">
			       	<select id="option_type" name="option_type" class='select'>
			       						<option value="0" selected>Type</option>
										<option value="2">  Excel </option>
										<option value="1" selected>  PDF </option>  
										<option value="3">  HTML </option>
					</select></td>     		
     			</tr>
</Table>
<input type="hidden" name="month" id="month" value="<%=smonth%>"/>  
<input type="hidden" name="year" id="year" value="<%=syear%>"/> 
				
</body>
</html>