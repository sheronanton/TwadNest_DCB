<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.sql.*,java.util.ResourceBundle"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.text.DecimalFormat"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheme Wise Pumped Quantity</title>
<script type="text/javascript" src="../scripts/pumped_qty.js"> </script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">
function reload()  
{
	var res=report_period_verify_2(document.getElementById('month'),document.getElementById('year'));
	if (document.getElementById('month').value!=0 && document.getElementById('year').value!=0)
	{
		if (res!=1)
		document.getElementById("pump").submit();
	}
	  
}
function check(a)
{  
	if (a.value > 5)
	{
	alert("Check : Average Monthly Pumped Quantity Entered is in MLD");
	}

}
</script>
</head>
<body>
<form id="pump" name="pump" action="pumped_qty.jsp" method="post">
<%
	try {
		HttpSession session1 = request.getSession(false);
		String userid = (String) session1.getAttribute("UserId");
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj = new Controller();
		Controller obj1 = new Controller();
		Controller obj2 = new Controller();
		
		if (userid == null)
		{
			response.sendRedirect(request.getContextPath()+ "/index.jsp");
		}
	//	String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",	"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"		+ userid + "')");
	
		String	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

	
//		String	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

		if (Office_id.equals("")) Office_id="0";
		String Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
		String qry=" select	SCH_NAME,SCH_SNO from 	PMS_SCH_MASTER  where  sch_sno in (select scheme_sno from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' "+ 
				" and office_id="+Office_id+") order by SCH_sno asc";		
		Connection con=obj.con();
		DecimalFormat df=new DecimalFormat("0.000");      
		obj.createStatement(con);
		obj1.createStatement(con);
		obj2.createStatement(con);
		
		ResultSet rs=obj.getRS(qry);
		String monthv=obj.setValue("month",request);
		String yearv=obj.setValue("year",request);
		
		
		
		int prvmonth=obj.prv_month(year,month);
		int prvyear=obj.prv_month(year,month);
		
		
		String month_combo=obj.month_combo("month","onchange=reload()",monthv);
		String year_combo=obj.year_combo("year","onchange=reload()",yearv);
		String month_value=obj.month_val(monthv);
		
		if (month_value=="0") month_value="";
		String html="<table cellspacing='1' cellpadding='1' style='border-collapse: collapse;border-color: skyblue'  width='75%' border=1 align=center>"+
		"<tr><th colspan=3 align=center>"+Office_Name+"</th></tr>"+  
		"<tr><th colspan=3 align=center>Scheme wise Monthly Average Pumped and Design Quantity</th></tr>"+
		"<tr><td colspan=3 align=center>&nbsp;</td></tr>"+
		"<tr><td align=left colspan=1>Month and Year</td><td colspan=2 >"+month_combo+"&nbsp;&nbsp;"+year_combo+"</td></tr>"+
		"<tr><td colspan=3 align=center>&nbsp;</td></tr>"+
		"<tr><td colspan=3>(Refer TWAD,HO Daily P.R module URL&nbsp;&nbsp;&nbsp;<font color='red' size=2><b>http://218.248.23.4/maint-projects</b></font>&nbsp;&nbsp;&nbsp;<br>Report --> Pumping Returns --> Month wise Average Qty )</td></tr></table>";
		String html1="<table cellspacing='1' cellpadding='0' style='border-collapse: collapse;border-color: skyblue'  width='75%' border=1 align=center>";	 
		  html1+="<tr  ><th align=center>Sl.No</th><th align=center>Scheme Name</th><th align=center>Average Eamarked Quantity <font color='red' size=2>(MLD)</font> <br>One Time Entry</th><th align=center>Average Pumped Qty <br> for the Selected Month <br><font color='red' size=2>(in MLD)</font></th> </tr>";
			   String name="",sno="";
			   int row=0;
		while(rs.next())
		{
			row++;    
			name=obj.isNull(rs.getString("SCH_NAME"),1);  
			sno=obj.isNull(rs.getString("SCH_SNO"),1); 
			
			String value="";//obj.getValue("PMS_AME_TRN_SCH_DP_QTY","PUMPING_QTY","where SCH_SNO="+sno+"	and  MONTH="+monthv+" and YEAR="+yearv+"  and OFFICE_ID="+Office_id,1);
			String design_value="";//obj.getValue("PMS_AME_MST_SCH_DETAILS","QTY_DESIGN","where SCH_SNO="+sno+"	     and OFFICE_ID="+Office_id,1);
			
			ResultSet rs1=obj1.getRS("select PUMPING_QTY from PMS_AME_TRN_SCH_DP_QTY where SCH_SNO="+sno+"	and  MONTH="+monthv+" and YEAR="+yearv+"  and OFFICE_ID="+Office_id);
			if (rs1.next())
			{
				value=obj.isNull(rs1.getString("PUMPING_QTY"),1);
			}
			ResultSet rs2=obj2.getRS("select QTY_DESIGN from PMS_AME_MST_SCH_DETAILS where SCH_SNO="+sno+"	  and OFFICE_ID="+Office_id);
			if (rs2.next())
			{  
				design_value=obj.isNull(rs2.getString("QTY_DESIGN"),1);
			}
			if (value.trim().equalsIgnoreCase("") || design_value.trim().equalsIgnoreCase(""))  
			{
				if (!design_value.trim().equalsIgnoreCase(""))
					html1+="<tr style='color: red' id=row"+row+"><td align=center>"+row+"</td><td align=left><input type=hidden  id=schsno"+row+" value="+sno+">"+name+"</td><td align=center><input type=text size=8 maxlength=8 style='text-align: right;' id=dqty"+row+" name=dqty"+row+" value='"+df.format(Double.parseDouble(design_value))+"'></td><td><input type=text size=8 maxlength=8 style='text-align: right;' id=qty"+row+" name=qty"+row+" value='"+value+"' onblur=check(this)></td></tr>";
				else
					html1+="<tr style='color: red' id=row"+row+"><td align=center>"+row+"</td><td align=left><input type=hidden  id=schsno"+row+" value="+sno+">"+name+"</td><td align=center><input type=text size=8 maxlength=8 style='text-align: right;' id=dqty"+row+" name=dqty"+row+" value='"+design_value+"'></td><td><input type=text size=8 maxlength=8 style='text-align: right;' id=qty"+row+" name=qty"+row+" value='"+value+"' onblur=check(this)></td></tr>";
			}
			else
			{
				html1+="<tr id=row"+row+"><th align=center>"+row+"</td><td align=left><input type=hidden  id=schsno"+row+" value="+sno+">"+name+"</td><td align=center><input type=text size=8 maxlength=8 id=dqty"+row+" name=dqty"+row+" value='"+df.format(Double.parseDouble(design_value))+"' style='text-align: right;'></td><td align=center><input type=text size=8 maxlength=8 id=qty"+row+" name=qty"+row+" value='"+df.format(Double.parseDouble(value))+"' style='text-align: right;' onblur=check(this)></th></tr>";
			}
		} 
		html1+="<tr><th colspan=4 align=center><input type=hidden id=rowcount value="+row+"><input type=button value=Submit id=store name=store onclick='pumped_process(1)'><input type=button value=Update id=store name=store onclick='pumped_process(2)'><input type=button value='Exit' onclick='window.close()'></th></tr></table>";
		
		out.println(html); 
		out.println(html1); 
		String html2="<table cellspacing='1' cellpadding='1' style='border-collapse: collapse;border-color: skyblue'  width='75%' border=0 align=center>";
		 	  html2+="<tr style='color: red'><td width='5%'><font color=green size=4><b>Note:<b/></font>&nbsp;&nbsp;&nbsp;&nbsp;</td><td colspan=2align=left>Please Enter Missing Average Eamarked Quantity &nbsp;/&nbsp;Pumped Quantity.&nbsp;&nbsp;  </td></tr> ";
			   html2+="<tr style='color: red'><td width='5%'> &nbsp;&nbsp;&nbsp;&nbsp;</td><td colspan=2 align=left>Enter Zero and Submit if Data Not Available</td></tr></table>";
			   out.println(html2);   
	} catch (Exception e) {
		  
		out.println(e);
	}
%>
 
</form>
</body>
</html>