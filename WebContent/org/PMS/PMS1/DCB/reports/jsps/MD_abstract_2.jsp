<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*,Servlets.PMS.PMS1.DCB.reports.myf" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.XmlDataGenerator"%>
<html>   
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demand,Collection and Balance Statement </title>
<script src="../../scripts/libraries/RGraph.common.core.js" ></script>
<script src="../../scripts/libraries/RGraph.bar.js" ></script>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
</head>      
   <script  src="../../scripts/FusionCharts.js"></script>
<body>  
<form action="MD_abstract_2.jsp" method="post"> 
<%
String  Office_Name="",userid="",Office_id="",path=""  ;
String fn="";  
Controller obj=new Controller();
Connection con=null;
Calendar cal = Calendar.getInstance();
int day = cal.get(Calendar.DATE);
int month = cal.get(Calendar.MONTH) + 1;
int year = cal.get(Calendar.YEAR);
try
{
	con=obj.con();   
	obj.createStatement(con);
	userid=(String)session.getAttribute("UserId");
	if(userid==null)  
	{
	  response.sendRedirect(request.getContextPath()+"/index.jsp");
	}  
	String pmonth=obj.setValue("pmonth",request);
	String pyear=obj.setValue("pyear",request);
	String schtype=obj.setValue("schtype",request);
    Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
    Office_Name = obj.getValue("COM_MST_OFFICES", "OFFICE_NAME","where OFFICE_ID=" + Office_id);
	String stype_value=obj.getValue("PMS_DCB_APPLICABLE_SCH_TYPE", "SCH_TYPE_SUB_DESC","where SCH_TYPE_ID_SUB=" + schtype);
	String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	MD_Abstract obj_abs=new MD_Abstract();
	obj_abs.setMonth(Integer.parseInt(pmonth));
	obj_abs.setYear(Integer.parseInt(pyear));
	obj_abs.setSchtype(Integer.parseInt(schtype));   
	String off=Office_id.equals("5000")?"":" and Office_id="+Office_id;	 	
	Office_Name=Office_id.equals("5000")?"":""+Office_Name+"<br>";
	obj_abs.setOffice_id(off);
	ArrayList ar=obj_abs.getType_wise();
	obj_abs.setMonth(Integer.parseInt(pmonth));
	obj_abs.setYear(Integer.parseInt(pyear));
	String mvalue=obj.month_val(pmonth);
	String add_cond="";  
	if (Integer.parseInt(schtype)==1)
		add_cond=" ";//and BEN_TYPE_GROUP not in (8)";
	else
		add_cond="  ";
 
	  String qry=obj_abs.report_query;
	  XmlDataGenerator xml=new XmlDataGenerator(con,obj);
	  path=getServletContext().getRealPath("\\data")+"\\"+fn+"typeCol3DLineDY2.xml";
	  xml.fpath=path; 
	  xml.setReport_query(qry);    
	 String res=xml.bar_chart("BEN_TYPE_DESC_SUB");         
 	 path = request.getScheme() +"://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/data/"+fn+"typeCol3DLineDY2.xml";  
%>
	<table border=1 width="72%" cellpadding="6" cellspacing="0" style="border-collapse: collapse;"  align="center">
	<tr class="tdH">
		<td colspan="6" align="center"><%=Office_Name%>
				Demand,Collection and Balance Statement
				<br><%=stype_value%> - Abstract
				<br>
				(for the month of <%=mvalue%> <%=pyear%>)			  
				<br>
				(Rupees in Lakhs)  	
		</td>
	</tr> 
	<tr><th width='5%' align='center'>Sl.No</th>
		<th width='45%' align='center'>Beneficiary Type</th>
		<th width='10%' align='center'>Opening Balance</th>
		<th width='10%' align='center' >Demand Current month</th>		
		<th width='10%' align='center'>Collection Current month</th>
		<th width='10%' align='center'>Balance</th>
	</tr>				 
  		 <% 
  			Iterator as=ar.iterator();
  		     int i=0;
  		     int j=0;
  		     int k=0;
  		   double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
  			while (as.hasNext() )  
			{
  				String ds=as.next().toString();  				
  				out.println(ds);  								   
			}
  			String p=getServletContext().getRealPath("/xmls");
  			p="https://track.tn.nic.in/twadphase2dcb/xmls/NewFile2.xml";  
  			String xmlpath=p+"";
  		 %>  
  		   
<tr><td align="center" colspan="6"></td></tr>			 
<tr><td align="left" colspan="6"><input type=button value="Back" onclick="window.history.go(-1)">&nbsp;&nbsp;&nbsp;<input type="hidden"  style="color: red;font-weight: bold"  value="Close" onclick="javascript:window.close()"></td></tr>
</table>
  <table width="98%" border="0" cellspacing="0" cellpadding="3' align="center">
  <tr> 
    <td valign="top" class="text" align="center"> <div id="chartdiv" align="center"> 
        FusionCharts. </div>
      <script type="text/javascript"> 
		   var chart = new FusionCharts("../Charts/FCF_MSColumn2D.swf", "ChartId", "400", "380");
		   chart.setDataXML("<%=res%>");	 
		   chart.render("chartdiv");  
		</script> </td>
  </tr>
  </table>  
<%
}catch(Exception e) 
{
	out.println(e);	
}
%>
<%!
		Thread d=new Thread();

%>

</form>
</body>
</html>