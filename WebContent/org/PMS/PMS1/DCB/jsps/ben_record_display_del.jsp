<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller,java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="dcb.reports.Ben_Details"%>
<%@page import="dcb.reports.Ben_Cancel"%>
<%@page import="java.util.Iterator"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Consumer.js"></script>
<script type="text/javascript">

		function del()
		{
							var month=0;
							var year=0;
							var cid=document.getElementById("ben_sno").value;
							try
							{
								month=document.getElementById("month").value;
								year=document.getElementById("year").value;
							}catch(e) {}  
		        			var url="../../../../../Consumer?command=Delete&cid=" + cid+"&month="+month+"&year="+year;
		        			var req=getTransport();
		                    req.open("GET",url,true);        
		                    req.onreadystatechange=function()
		                    {
		                       processResponse(req);
		                    }   
		                    req.send(null); 
		}
		function processResponse(req)
		{
			 if(req.readyState==4)
             {
                 if(req.status==200)
                 {
                     var baseResponse = req.responseXML.getElementsByTagName('response')[0];
                     var flag = baseResponse.getElementsByTagName('flag')[0].firstChild.nodeValue;
                     if(flag=='success')
                     {		
                     	alert("Deleted Successfully")
                            // alert("Deleted Successfully");     
                             refresh();
                     }
                     else
                     {
                     	alert("This Beneficiary cannot be deleted now. \n  Please. First Delete All Meter Location For this Beneficiary");
                             //alert("This Beneficiary cannot be deleted now. It has been referred by some module.");
                     }
                 }
             }
		}

		function rld() 
		{
			var res=report_period_verify_2(document.getElementById('month'),document.getElementById('year'));
			var res1=month_year_check(document.getElementById('month').value,document.getElementById('year').value);
			if (res1!=1)
			{
				if (res!=1)  
				{
					document.forms["frm"].submit();
				} 
			}
		 
		}
</script>
    
</head>
<body`>
<% 
		String msg="";
		String style="",userid="",Office_id="0";
		Controller obj=new Controller();
		Connection con=null;
		String cls_wc_charges="0";
		String cls_maint_charges="0";
		String cls_int_charges="0";
		String cls_yester_year="0";
		String ben="0",ben_name="",ben_type="";
		try
		{
			con=obj.con();
			  ben=obj.setValue("ben_sno",request);
			
			Calendar cr=Calendar.getInstance();
			int year=cr.get(Calendar.YEAR);
			int month=cr.get(Calendar.MONTH);
			int day=cr.get(Calendar.DAY_OF_MONTH);
			
			String pmonth=obj.setValue("month",request);
			String pyear=obj.setValue("year",request);
			try
			{
			   userid=(String)session.getAttribute("UserId");
			}catch(Exception e) {userid="0";}
			if(userid==null)
			{
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			
			}
			
			Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
			ben_name=obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_NAME", "where BENEFICIARY_SNO="+ben) ;
			ben_type=obj.getValue("PMS_DCB_MST_BENEFICIARY", "BENEFICIARY_TYPE_ID", "where BENEFICIARY_SNO="+ben) ;
			if (Office_id.equals("")) Office_id="0";
				String oid=obj.setValue("oid",request);		
				if (!oid.trim().equalsIgnoreCase("0"))
					Office_id=oid;
			 
%>
		<form name="frm" action="ben_record_display_del.jsp" method="post">
		<table align="center" width="70%"  cellspacing='0' cellpadding='5' style='border-collapse: collapse;'>
		<tr class="tdH"><th colspan="2" align="center">Beneficiary Details</th></tr>
		<tr class="tdH">
			<td> Beneficiary Name  :<%=ben_name %></td>
	    <!--	<td> Beneficiary Type  :</td>   -->
		</tr>
		
		<tr>
			<td colspan="2" align="left"><b>With Effect From</b></td>
		</tr> 
		<tr>
  		<td colspan="5"  align="left"> Month: <select name="month" id="month" onchange="rld()" >
	         <option value="0" selected="selected" >Select</option>
	         	<%
	         	String[] amonth = { "-select month-", "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December" };	         	
	         	for (int i=1;i<=12;i++) {
	         	%>
	         	<option value="<%=i%>" <% if (Integer.parseInt(pmonth)==i) {%>selected<%} else { }%>><%=amonth[i]%></option>
	         	<%} %>
	          </select> Year:<select id="year" name="year"  tabindex="5" onchange="rld()">
                <option value="0" selected="selected">Select</option>
                <%
                for (int j=2010;j<=year;j++)
                {
                %>
                <option value="<%=j%>" <% if (Integer.parseInt(pyear)==j) {%>selected<%} else { }%>><%=j%></option>
                <%} %>  
    </select> </td> 
    </tr>
		
		<tr> 
		<Td colspan="2" align="center">
		<%
			String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
			if (!pmonth.equalsIgnoreCase("0") && !pyear.equalsIgnoreCase("0"))
			{
				Ben_Cancel b=new Ben_Cancel();
				ArrayList ar_list=b.execute(ben,pyear,pmonth);   			
				Iterator er=ar_list.iterator();
				out.println("<table  width=100% align='center' cellpadding='5' style='BORDER-COLLAPSE: collapse'  borderColor='#92c2d8'>");
				if (er.hasNext()) 
				{
					Ben_Details ben_res=(Ben_Details)er.next();
					cls_wc_charges=obj.isNull(ben_res.getCls_wc_charges(),1);
					cls_maint_charges=obj.isNull(ben_res.getCls_maint_charges(),1);
					cls_int_charges=obj.isNull(ben_res.getCls_int_charges(),2);
					cls_yester_year=obj.isNull(ben_res.getCls_yester_year(),2); 
				//	out.println("<tr class='tdLText'><td> Beneficiary Name </td><td colspan='3'>"+ben_res.getBen_name()+"</td><tr>");
					out.println("<tr  class='tdLText'><td colspan='4'><b><u>Closing Balance(in Rs.)</u></td></tr>");
					out.println("<tr  class='tdLText'> <td> Water Charge   </td><td> Maintenance Charges   </td><td> Yester year Water Charge</td><td> Water Charges Interest </td><tr>");
					out.println("<tr> <td align='right'>"+cls_wc_charges+"</td><td align='right'>"+cls_maint_charges+"</td><td align='right'>"+cls_yester_year+"</td><td align='right'>"+cls_int_charges+"</td><tr>");
				 
				}
				out.println("<tr  class='tdLText'><td colspan='4'><b><u>Pumping Return Details(in KL.)</u></td></tr>");
				
				int prv_month=obj.prv_month(pmonth,pyear);
				int prv_year=obj.prv_year(pmonth,pyear);
				
				int prv_month_2=obj.prv_month(Integer.toString(prv_month),Integer.toString(prv_year));
				int prv_year_2=obj.prv_year(Integer.toString(prv_month),Integer.toString(prv_year));
				
				double pumping_return1=0.0,pumping_return2=0.0;
				String cond="where BENEFICIARY_SNO="+ben+" and OFFICE_ID="+Office_id+" and MONTH="+prv_month+" and YEAR="+prv_year;
				String cond2="where BENEFICIARY_SNO="+ben+" and OFFICE_ID="+Office_id+" and MONTH="+prv_month_2+" and YEAR="+prv_year_2;
				try
				{
					pumping_return1=Double.parseDouble(obj.getValue("PMS_DCB_TRN_MONTHLY_PR","sum(QTY_CONSUMED_NET)","qty",cond));
					pumping_return2=Double.parseDouble(obj.getValue("PMS_DCB_TRN_MONTHLY_PR","sum(QTY_CONSUMED_NET)","qty",cond2));
				}catch(Exception e) 
				{  
				out.println(e); 	  
				} 
				out.println("<tr> <td colspan='2' align='center'>"+cmonth[prv_month_2]+"&nbsp;"+prv_year_2+"</td><td colspan='2'  align='center'>"+cmonth[prv_month]+"&nbsp;"+prv_year+"</td></tr>");
				out.println("<tr> <td colspan='2' align='center'>"+pumping_return2+"</td><td colspan='2'  align='center'>"+pumping_return1+"</td></tr>");
				out.println("</table>"); 
			//	if (cls_wc_charges.equalsIgnoreCase("0") && cls_maint_charges.equalsIgnoreCase("0") && cls_int_charges.equalsIgnoreCase("0") && cls_yester_year.equalsIgnoreCase("0"))
				if (String.valueOf((int) Double.parseDouble(cls_wc_charges)).equalsIgnoreCase("0") && String.valueOf((int) Double.parseDouble(cls_maint_charges)).equalsIgnoreCase("0") && String.valueOf((int) Double.parseDouble(cls_int_charges)).equalsIgnoreCase("0") 
						&& String.valueOf((int) Double.parseDouble(cls_yester_year)).equalsIgnoreCase("0"))

				{
					style=" style='display:display'";
				}else
				{
					style=" style='display:none'";
					msg="<font color='red' size='3'>This Beneficiary  cannot be deleted as closing balance is not zero.....Please Contact Head Office</font> ";
				}
			}else
			{
				style=" style='display:none'";
			}
			
		
		}catch(Exception e)
		{ 
			  response.sendRedirect(request.getContextPath()+"/index.jsp");
		} 
%>                  
		</Td></tr>
	   
		<tr> 
			<td align="center"  colspan="4"><%=msg%></td>
		</tr>
				<tr>
			<td align="center" colspan="2">			
				<input type="button" name="Delete" value="DELETE" <%=style%>  id="Delete" onclick="del() " align="middle"/>
				<input type="button" value="Exit" onclick="javascript:window.close()">
			</td>
		</tr>	
		 <tr class="tdH"><th colspan="2" align="center">&nbsp;</th></tr>
		</table>
		<input type="hidden" id='ben_sno' name='ben_sno' value='<%=ben%>'> 
		
		</form>
		 <script type="text/javascript">
    		var OID = <%=Office_id%>
    	   </script>
</body>
</html>