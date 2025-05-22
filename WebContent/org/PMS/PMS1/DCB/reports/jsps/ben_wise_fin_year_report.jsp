<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB Financial Year Statement - Consolidated</title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script> 
<script type="text/javascript">
function rld1(a)
{  
	document.forms["myform"].submit();

}  
function rpt()  
{
	var year= document.getElementById("year").value;
	var DV= document.getElementById("DV").value;
	if (year==0 || DV==0)
	{  
		alert("Select Financial year and Division ");  
	}  
	else
	{
		
		var s= window.open("../../../../../../reg_menu_index?ref_sno=200&year="+year+"&DV="+DV,"","","");
	}   
}
</script>  
</head>     
<body>   
<form action="ben_wise_fin_year_report.jsp" method="get" name="myform">
<%  
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Connection con;
		String BEN_TYPE_ID="",Office_name="",userid="",Office_id="",combostr="";
		Controller obj=new Controller();
		String combo1="";
		try
		{
			
			con=obj.con();
			con=obj.con();
			obj.createStatement(con);
			 
			userid=(String)session.getAttribute("UserId");

			if(userid==null)  
			{
			 	//response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
	 		Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		//    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			Office_name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);

		    if (Office_id.equals("")) Office_id="0"; 
			BEN_TYPE_ID=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID"," where BEN_TYPE_ID in ( select BENEFICIARY_TYPE_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+")","BEN_TYPE_ID",""," style='width: 200;font-size: 0.7em; color: maroon;' onchange=sch_load(2,'')" );
			  
			String regdiv=obj.isNull(obj.setValue("regdiv",request),1);
			String disable="";
			if (Integer.parseInt(Office_id)!=5000)
				disable=" disabled=disabled";
			
			combo1=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='RN' order by OFFICE_ID ","regdiv",regdiv," class=select onchange='rld1(this.value)' "+disable);
			
			String rd="";
		 
			if (Integer.parseInt(Office_id)==5000) rd=""; else rd="where OFFICE_ID="+Office_id;
			
			if (Integer.parseInt(Office_id)!=5000)
				combostr=obj.combo_str("COM_MST_OFFICES","OFFICE_NAME","OFFICE_ID"," where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP "+rd+" )   ","DV",Office_id," class=select  "  );
			else
				combostr=obj.combo_str("COM_MST_ALL_OFFICES_VIEW","OFFICE_NAME","OFFICE_ID"," where OFFICE_LEVEL_ID='DN' and  REGION_OFFICE_ID="+regdiv+" and  OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP  )  ","DV",Office_id," class=select "  );
			
			//combostr=obj.combo_str("com_mst_all_offices_view","OFFICE_NAME","OFFICE_ID","where OFFICE_ID in (select OFFICE_ID from PMS_DCB_DIV_DIST_MAP ) and OFFICE_LEVEL_ID='DN'","DV","  style='width: 200;font-size: 0.7em; color: maroon;'");
			}catch(Exception e) {}
			int year_incr=0;
			if (month <4)
				year_incr=0;    
			else
			    year_incr=1; 
			int prvfinyear=obj.prvfinyear(year,month);
%>
<table align="center" width="50%" cellpadding="10" cellspacing="0" border=1 bordercolor="darkgray">
 <tr bgcolor="skyblue">
 		<td colspan="2" align="center"><%=Office_name%></td>
 </tr>
  <tr>
 		<td colspan="2" align="center">DCB Financial Year Statement</td>
 </tr>
 <tr>
		<td>Region</td>
		<td><%=combo1%></td>
	</tr>
	<tr>	<td class=tdText width="40%"   > <font color="#0000A0"> Division </font></td>
     	   <td   ><%=combostr%></td>
     </tr>
 <tr class="tdText">
     	<td   >
        	<label id="benname" >Financial Year </label> 
                            
           </td>
            <td   >
            	<select id="year"   tabindex="5" style="width:220pt"  class="select">
                	<option value="0" selected="selected">- - - Select - - -</option>
                	<%	for (int j=year-5;j<=year+year_incr;j++)
                	{
                	if (j-1==prvfinyear) {
      				 %>
       				 <option value="<%=j-1%>" selected><%=j-1%>-<%=j%></option>
       				 <% } else { %>
        			<option value="<%=j-1%>"><%=j-1%>-<%=j%></option>
       				<%}} %> 
                 </select>
             </td>
     </tr>
     <tr>
     		<td align="center" colspan="2"><input type="button" value="Submit" onclick="rpt()"> 
     				<input type="button" value="Exit" onclick="window.close()"> 
     			</td>
     </tr>
  </table>
  </form>
</body>
</html>