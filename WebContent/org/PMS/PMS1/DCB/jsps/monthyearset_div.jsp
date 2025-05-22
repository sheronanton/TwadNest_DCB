<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction Month Setting | TWAD Nest - Phase II </title>
</head>
     
     <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
    <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
       <script type="text/javascript" src="../scripts/Defunt_id.js"></script>
	<script type="text/javascript">
	
			function pr()
			{
				window.open("../../../../../Bill_Demand?command=All_benList");
			}
			function list()
			{
				var id=document.getElementById("off").value;
				alert("id is :"+id);
			}
	
			function rld()
			{
				var month=document.getElementById("month").value;
				if (parseInt(month)==0)
				{	
					alert("please select month !!! ");
					return false;
				} 
				else
				{
					return true;
				}  
				
			}

			function form_sub()
			{
				var year=document.setting.year.value;
				var month=document.setting.month.value;
			}
	
	</script>
<body onload="Setting()" >
<% try
{
  
%>
<form action="monthyearset_div.jsp" method="post" name="setting" onsubmit="rld()">
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
		String msg="",mmsg="";
		String Date_dis=day+"/"+(month+1)+"/"+year;
		String userid="0",Office_id="",Office_Name="",Office_Name1="",defunt_id="";
		String[] arr = new String[10]; 
		String[] arrn = new String[10]; 
       int k=0;
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
			
			Office_id =obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
			defunt_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
		    	if(defunt_id.equals(Office_id))
		    	{
		    		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		    		arr[0]  = Office_id;
		    		arrn[0]  = Office_Name;
		    		k=1;
		    	}  
		    	else
		    	{
		    		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		    		arr[0]  = Office_id;
		    		arrn[0]  = Office_Name;
		    		Office_Name1=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+defunt_id+ "  ");
		    		arr[1]  = defunt_id;
		    		arrn[1]  = Office_Name1;
		    		k=2;
		    		
		    	}
    	
    	
    	  	
    	
		
    	if (Office_id.equals("")) Office_id="0";
	//		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String  smonth=obj.isNull(request.getParameter("month"),1);
			String syear=obj.isNull(request.getParameter("year"),1);;
		 	String sflag=obj.isNull(request.getParameter("b1"),1);;
		 	
		 	String office=request.getParameter("off");
		 	
		 	PreparedStatement ps=null;
			String ins_qry="";
			
			int monthflag=0;
			 
					if (!sflag.equalsIgnoreCase("0"))
					{
						// 9 < 4 
					if ( month < Integer.parseInt(smonth) && year > Integer.parseInt(syear) )
					 {
						monthflag=1;
					 
					 }else if( month > Integer.parseInt(smonth) && year >= Integer.parseInt(syear) )
					 {
						 monthflag=1;
						  
					 }else if ( month == Integer.parseInt(smonth) && year >= Integer.parseInt(syear))
					 {
						 monthflag=1;
					 } 
					else
					{
						 monthflag=0;	
					}
			mmsg="";
				if (monthflag==1)
				{
				    if (sflag.equalsIgnoreCase("set") && !smonth.equalsIgnoreCase("0"))
				    {
							try
							{
						//	int rc=Integer.parseInt(obj.isNull(Integer.toString(obj.getCount("PMS_DCB_SETTING","where (1=1) and OFFICE_ID="+Office_id)),1));
							int rc=Integer.parseInt(obj.isNull(Integer.toString(obj.getCount("PMS_DCB_SETTING","where (1=1) and OFFICE_ID="+office)),1));
							if (rc!=0)
							{
								 ins_qry="update PMS_DCB_SETTING set YEAR=? , MONTH=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp() where   OFFICE_ID=?";

							//	 ins_qry="update PMS_DCB_SETTING set YEAR=? , MONTH=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp() where   OFFICE_ID="+Office_id;
								 ps=con.prepareStatement(ins_qry);				
								 ps.setInt(1,Integer.parseInt(syear));
							 	 ps.setInt(2,Integer.parseInt(smonth));
							 	 ps.setString(3,userid);	
							 	ps.setInt(4,Integer.parseInt(office));	
									 int r_s=ps.executeUpdate();
									 if (r_s==1)
									 {
										 mmsg="<font  ><b>Setting Done</b></font>";
							  		}  
							}   
							else  
							{
							 int r_s=0;
							 	 ins_qry="insert into  PMS_DCB_SETTING (YEAR,MONTH,OFFICE_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,clock_timestamp())";
							  	 ps=con.prepareStatement(ins_qry);
							  	 ps.setInt(1,Integer.parseInt(syear));
							  	 ps.setInt(2,Integer.parseInt(smonth));
							  	 ps.setInt(3,Integer.parseInt(Office_id));
							  	 ps.setString(4,userid);				
							   	 r_s+=ps.executeUpdate();
									 if (r_s>0)
									 {%>
										 <script type="text/javascript">
							 			//	 alert (" Month / Year Setting Done  ..........")				 
							 			</script>  
							 		<%}
				 		   }
						}catch(Exception e) {out.println("TEST"+e);}
				}
		}
		 else
		 {
			 mmsg="<font color=red>Setting Not Done Please Refer Help! </font>";
		 %>
				 <script type="text/javascript">
				// alert(" Month Cannot Be Greater Than Current Month!!!")
				 </script>
				 <%
		 }
	}
}catch(Exception e) {
	out.println(e);
//	response.sendRedirect(request.getContextPath()+"/index.jsp");
}	
  
		String tb_month=obj.getValue("PMS_DCB_SETTING","MONTH","where office_id="+Office_id);
		String tb_year=obj.getValue("PMS_DCB_SETTING","YEAR","where office_id="+Office_id);
		String []cmonth ={"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		String []cmonthv ={"0","1","2","3","4","5","6","7","8","9","10","11","12"};
		String []cyear ={"Select","2009","2010"};
		String []cyearv ={"0","2009","2010"};	
		String m_value="";
		if (tb_year.equalsIgnoreCase("0"))
		{
    		m_value="<font color='red'>Month and Year Setting Not Done</font> ";
    		tb_year="0";
     	}
    	else
    		m_value=cmonth[Integer.parseInt(tb_month)]+"-";
    	obj.conClose(con);
   		int cy=year+1;
 %>
 
<table id="" width=70%  cellspacing="0" cellpadding="20" align="center">
 <tr> 
          <th align="center" height="20px"   colspan="2" height="10" >
          			 Transaction Month Setting  - <%=Office_Name %> <font class="tdText" color="red"><label id="msg"></label></font>
          </th>
 </tr> 
 <TR>  
 	<td  > Office Name </td>  <td> <select class="select" id="off" name="off" onchange="Setting()"   >
		 <%
		 	for (int i=0;i<k;i++)
			{%>
			<option  value="<%=arr[i]%>"><%=arrn[i]%></option>
		   <%}
		  %>
		 </select></td>
		 
		 
 </TR>
  <TR>  
 	<td  >  Current Month and Year</td><td width="50%" class="tdText"> <%=cmonth[month]%>  <%=year%></td>
 </TR>
  <TR bgcolor="skyblue">
 	<td colspan="2">&nbsp;</td>
 </TR>  
<tr>
		<td width="50%" class="tdText"> Setting Month</td>
		 <td> <select class="select" id="month" name="month"  style="width:100pt"  >
		 <%
		 	for (int i=0;i<cmonth.length;i++)
			{%>
			<option  value="<%=cmonthv[i]%>"><%=cmonth[i]%></option>
		   <%}
		  %>
		 </select></td>
</tr>
	       <tr>
		 <td width="50%" class="tdText">  Setting Year  </td>
		 <td>  <select class="select" id="year" name="year"  style="width:100pt"  >
		 <%
		 	for (int i=2009;i<=year;i++)
			{%>
			<option <% if (Integer.parseInt(tb_year)==i) %>selected   value="<%=i%>"><%=i%></option>
		   <%}
		  %>
		 </select> </td>
</tr>     
<tr>
<td class="tdText">
	Current Setting Status 
</td>
<td class="tdText"><marquee  SCROLLDELAY=300 BEHAVIOR=ALTERNATE> <p id="pyear"></marquee></td>
</tr>
<tr >
	<td colspan="3" align="center"><%=mmsg%></td>   
</tr>  
  <tr>
		 <th align="center" colspan="2"><input type="Submit" class="fb2" value="Set" id="b1" name="b1"/>&nbsp; <input type="button" class="fb2" value="Exit" onclick="window.close()"/>&nbsp;<input type="button" value="HELP" onclick="window.open('help1.jsp?fn=11','mywindow','width=600,height=400,scrollbars=yes')"/> </th>
</tr>
</table>
<br> 
<table id="" width=70%  cellspacing="0" cellpadding="20" align="center"> 
	<tr><td align="left"><img src="../../../../../images/pls note.jpg" height="110"></td> 
 			<Td><font size='2' color='#113434'> &nbsp;<strong>Please visit Feedback menu and enter your positive feedback and suggestions about DCB Module</strong></font>  
 		</td>
 	</tr>
 	 	<tr> 
 		<td colspan="2" align="center">
 			<font size='1' color='#dd3434'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Refer: Maintenance -> DCB -> Feedback (below DCB Flow ,FAQ) ->Post Your Feedback </strong></font> 
 		</td>
 	</tr>
 </table>
 
</form>  

<% }catch(Exception e) {out.println(e);} %>
</body>
</html>
