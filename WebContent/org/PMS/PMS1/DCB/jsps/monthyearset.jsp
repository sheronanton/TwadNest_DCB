<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
 <%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Month & Year Setting | TWAD Nest - Phase II </title>
</head>
     <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>

    <script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">

function pr()
{
	window.open("../../../../../Bill_Demand?command=All_benList");
}

function rld()
{
	document.setting.submit();
}



</script>
<body >
<% try
{
  
%>
<form action="monthyearset.jsp" method="get" name="setting">
<%
String demand_command="",type="";
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH)+1 ;

int year = cal.get(Calendar.YEAR);
 
 if (month >=4)
	year=year;
else
	year=year;

int pumpingfalg=0;
/*

	flag from setting table
	pumpingfalg=1

*/

String Date_dis=day+"/"+(month+1)+"/"+year;
String userid="0",Office_id="",Office_Name="";
Controller obj=new Controller();
Connection con=null;
try
{ 
	con=obj.con();
	obj.createStatement(con);
	
	try
	{
	   userid=(String)session.getAttribute("UserId");
	}catch(Exception e) {userid="0";}
	if(userid==null)
	{
	 //	response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	if (Office_id.equals("")) Office_id="0";
	Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
	String  btype=obj.isNull(request.getParameter("type"),1);
	 demand_command=obj.getValue("PMS_DCB_BEN_TYPE","REMARKS","where BEN_TYPE_ID="+btype);
	
	  type=obj.combo_str("PMS_DCB_BEN_TYPE","BEN_TYPE_DESC","BEN_TYPE_ID","","type",btype,"class='select' onchange=rld()");
	 String  smonth=obj.isNull(request.getParameter("month"),1);
 	
	String syear=obj.isNull(request.getParameter("year"),1);;
 	String sflag=obj.isNull(request.getParameter("b1"),1);;
 	String button2=obj.isNull(request.getParameter("button2"),1);;
 	PreparedStatement ps=null;
	String ins_qry="";
	
	if (!button2.equalsIgnoreCase("0"))
	{
		String demand_command_local=obj.isNull(request.getParameter("rem"),1);
		
		String ben_type=obj.isNull(request.getParameter("type"),1);
	 	ps=null;
	 	ins_qry=" update PMS_DCB_BEN_TYPE set REMARKS='"+demand_command_local+"' where BEN_TYPE_ID="+ben_type;
		ps=con.prepareStatement(ins_qry);
		int i_s=ps.executeUpdate();
		demand_command=obj.getValue("PMS_DCB_BEN_TYPE","REMARKS","where BEN_TYPE_ID="+ben_type);
	}
	
	
	
	
	int ck=0;
	 
	if (!sflag.equalsIgnoreCase("0"))
	{
			if (month >= Integer.parseInt(smonth) &&  year == Integer.parseInt(syear) )
			 {
				ck=1;
			 }else if (month <  Integer.parseInt(smonth) &&  year > Integer.parseInt(syear) )
			 {
				 ck=1;
			 }
			 else{ck=0;}
	
	
	 
		if (ck==1)	
		{
		  if (sflag.equalsIgnoreCase("set"))
		   {
			    
			  try
			  {
				int rc=Integer.parseInt(obj.isNull(Integer.toString(obj.getCount("PMS_DCB_SETTING","where (1=1)")),1));
				if (rc!=0)
				{
						 ins_qry="update PMS_DCB_SETTING set YEAR=? , MONTH=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp() ";
						 ps=con.prepareStatement(ins_qry);				
						 ps.setInt(1,Integer.parseInt(syear));
						 ps.setInt(2,Integer.parseInt(smonth));
						 ps.setString(3,userid);				
						 int r_s=ps.executeUpdate();
						   if (r_s==1)
						   {
						 		%>
						 			<script type="text/javascript">
						 					alert("Setting Done")
						 			</script>
						 		<%
						 	}
				} 
				else
				{
						int r_s=0;
						ResultSet rs=obj.getRS("select distinct OFFICE_ID from PMS_DCB_DIV_DIST_MAP ");
						while (rs.next())
						{
							ins_qry="insert into  PMS_DCB_SETTING (YEAR,MONTH,OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,clock_timestamp())";
							ps=con.prepareStatement(ins_qry);
							ps.setInt(1,Integer.parseInt(syear));
							ps.setInt(2,Integer.parseInt(smonth));
							ps.setInt(3,Integer.parseInt(rs.getString(1)));
							ps.setInt(4,Integer.parseInt(rs.getString(1)));
							r_s+=ps.executeUpdate();
						}
								 
						if (r_s>0)
						{%>
							<script type="text/javascript">
							alert (" Month / Year Setting Done For All Division ..........")
							</script>  
						<%}
			     }
						
						
						
				}catch(Exception e) {out.println("TEST"+e);}
			} //sflag.equalsIgnoreCase("set")
		 }//if (ck==1)	
		 else
		 {
			 %><script type="text/javascript">alert(" Month and Year can't be Greater Than current month and year !")</script><%
		 }////if (ck==1) END
		 
	} //if (!sflag.equalsIgnoreCase("0")) end
	
	
}catch(Exception e) {userid="0";}
	
	
	  
	
 
 

String tb_month=obj.getValue("PMS_DCB_SETTING","MONTH","");
String tb_year=obj.getValue("PMS_DCB_SETTING","YEAR","");

String []cmonth ={"-select month-","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec"};
String []cmonthv ={"0","1","2","3","4","5","6","7","8","9","10","11","12"};
String []cyear ={"-select year-","2009","2010"};
String []cyearv ={"0","2009","2010"};	



String m_value="";

	if (tb_year.equalsIgnoreCase("0"))
	 {
    	m_value="<font color='red'>Month and Year Setting Not Done</font> ";
    	tb_year="";
     }
    else
    	m_value=cmonth[Integer.parseInt(tb_month)]+"-";
    	
obj.conClose(con);
   int cy=year+1;
 %>
 <!--  
<table class="fb2"   id="" width=50% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">

 <tr  class="tdH"> 
          <td align="center" height="20px"   colspan="2" height="10" >
          			 Setting  <font class="tdText" color="red"><label id="msg"></label></font>
          </td>
 </tr>
  <TR bgcolor="skyblue">
 	<td colspan="2">Month & Year</td>
 </TR>
<tr>
		<td width="50%" class="tdText"> Month</td>
		 <td> <select class="select" id="month" name="month"  style="width:100pt"  >
		   <%
		 	for (int i=0;i<cmonth.length;i++)
			{%>
			<option value="<%=cmonthv[i]%>"><%=cmonth[i]%></option>
		   <%}
		 
		  %>
		 
		  
		 </select></td>
</tr>
	       <tr>
		 <td width="50%" class="tdText">  Year  </td>
		 <td>  <select class="select" id="year" name="year"  style="width:100pt"  >
		 <%
		 	for (int i=2010;i<cy+1;i++)
			{%>
			<option value="<%=i%>"  <% if (year==i){ out.println(" selected ");%>  <% }%>  ><%=i%></option>
		   <%}
		   
		  %>  
		 
		 </select> </td>
</tr>     
<tr>
<td class="tdText">
		Current Setting 
</td>
<td class="tdText"><marquee  SCROLLDELAY=300 BEHAVIOR=ALTERNATE><%=m_value%> <%=tb_year%></marquee></td>
</tr>
  <tr>
		 <td  class="tdText" align="center" colspan="2"><input type="submit" class="fb2" value="Set" name="b1"/>&nbsp; <input type="button" class="fb2" value="Exit" onclick="window.close()"/>    </td>
		   
</tr>
</table><BR><BR><BR><BR><BR><BR><BR><BR><BR>
-->
<table class="fb2"  id="" width=50% align="center" border="1"   cellspacing="0" cellpadding="3" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
 <tr  class="tdH"> 
          <td align="center" height="20px"   colspan="2" height="10" >
          			Demand Notice Remarks Setting   
          </td>
 </tr>
 <TR bgcolor="skyblue">
 	<td colspan="2">Remarks [Optional]</td>
 </TR>
 <TR  >
 	<td class="tdText">Beneficiary Type</td>
 	<td><%=type%></td>
 </TR>
<tr>
	<td class>Remarks in Demand Notice (if any) : </td><td><textarea class="textarea" id="rem" name="rem"><%=demand_command%></textarea> </td>
</tr> 
  <TR>
 	<td colspan="2" align="center"><input type="submit" name="button2" id="button2" value="Submit" class="fb1"></td>
 </TR>
</table>
 
</form>

<% }catch(Exception e) {out.println(e);} %>
</body>
</html>

