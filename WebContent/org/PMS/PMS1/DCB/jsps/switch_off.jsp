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
<body  >
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
		String userid="0",Office_id="",Office_Name="",Office_Name1="",current_id="",defunt_id="",current_Name="";
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
			
			current_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
			Office_id =obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
			defunt_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			current_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+current_id+ "  ");
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
							 	ps.setString(4,office);	
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
 <br><br><br>
<table id="" width=50%  cellspacing="0" cellpadding="20" align="center">
 <tr> 
          <th align="center" height="20px"   colspan="2" height="10" >
          			 Switch between Defunct and Current Office <font class="tdText" color="red"><label id="msg"></label></font>
          </th>
 </tr> 
 <TR>  <td>
         <input type="hidden" id="real" name="real"  value="<%=defunt_id%>">
  			
 	 Office Name </td>  <td> <select class="select" id="off" name="off"  >
		 <%
		 	for (int i=0;i<k;i++)
			{%>
			<option  value="<%=arr[i]%>"><%=arrn[i]%></option>
		   <%}
		  %>
		 </select></td>
	</TR>
  
<tr>
<td class="tdText">
	Current Office id
</td>
<td class="tdText"><marquee  SCROLLDELAY=300 BEHAVIOR=ALTERNATE>  <%=current_Name%> </marquee></td>
</tr>

  <tr>
		 <th align="center" colspan="2"><input type="Button" class="fb2" value="Set" onclick="SetOffice()" id="b1" name="b1"/>&nbsp; <input type="button" class="fb2" value="Exit" onclick="window.close()"/>&nbsp; </th>
</tr>
</table>
<br> 
 
</form>  

<% }catch(Exception e) {out.println(e);} %>
</body>
</html>
