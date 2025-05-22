<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1" import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>
<body>
<%
		String userid="",Office_id="",oid="",smonth="",syear="";
		Controller obj=null;
		java.sql.Connection con;
		try
		{
				String new_cond=Controller.new_cond;
	  			obj = new Controller();
				con = obj.con();
				obj.createStatement(con);  
				 try
					{
					   userid=(String)session.getAttribute("UserId");
					}catch(Exception e) {userid="0";}
					if(userid==null)
					{ 
						 response.sendRedirect(request.getContextPath()+"/index.jsp");
					}
					Office_id=obj.setValue("office_id",request);//obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
					smonth=obj.setValue("fmonth",request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
					syear=obj.setValue("fyear",request);;//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
					String process_month=smonth;
					String process_year=syear;
				    
				  	obj.recp_month="4";				  
				  	obj.recp_year=process_year;
				  	obj.recp_off=Office_id;  
					String qry=obj.rec_trouble();
					ResultSet rs=obj.getRS(qry);
					
			String qry_res="";
			qry_res="<tr>";
			String bname="",ben_sno="",office_id="",recp_no="";
			int row=0;
				while(rs.next())
				{
					row++;
					bname=obj.isNull(rs.getString("BENEFICIARY_NAME"),1);
				 
					ben_sno=obj.isNull(rs.getString("SUB_LEDGER_CODE"),1);
					office_id=obj.isNull(rs.getString("ACCOUNTING_FOR_OFFICE_ID"),1);
					recp_no=obj.isNull(rs.getString("RECEIPT_NO"),1);
					qry_res+="<tr><td>"+bname+"</td>";  
					qry_res+="<td>"+rs.getString("OFFICE_NAME")+"</td>";
					qry_res+="<td>"+recp_no+"</td>";					
					qry_res+="<td><input type=button value='Next' name=b1  onclick=load('"+recp_no+"')></td></tr>";
				}	
			 
				
				out.println(qry_res);
				  
				  
				
				  
				
				
				
		}catch(Exception e)
		{
				out.println(e);
		}


%>
<table>
		<tr>
		<td>
		<table>
		<tr>
			<td> Year </td>
		</tr>
		<tr>
			<td> Month </td>
		</tr>
		<tr>
			<td> Receipt No </td>
		</tr>
		</table>
		</td>
		</tr>
		<tr>
		<td>
		<table>		
		<tr>
			<td> Scheme Name </td><td> Project Id </td><td>Check</td>
		</tr>
		</table>		
</table>







</body>
</html>