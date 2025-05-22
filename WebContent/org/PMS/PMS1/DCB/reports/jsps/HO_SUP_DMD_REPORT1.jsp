<!DOCTYPE html>    
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
 <%@page import="java.text.DecimalFormat"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.XmlDataGenerator"%><html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">      
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB-HOD Report</title>
<script src="../../scripts/libraries/RGraph.common.core.js" ></script>
<script src="../../scripts/libraries/RGraph.bar.js" ></script>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
 <style type="text/css">
	.ft
	{
		font-size: small;      
	}
</style>
</head>  
<script  src="../../scripts/FusionCharts.js"></script>
<script type="text/javascript">
function ld_1(a)
{ 
	var Lyear=0,pyear=0;
	try
	{    
		Lyear=document.getElementById("Lyear").value; 
	  pyear=document.getElementById("pyear").value;
	}catch(e) {}  
   
	parent.frames[3].location.href="HO_SUP_DMD_REPORT2.jsp?Lyear="+Lyear+"&pyear="+pyear+"&MD_GROUP="+a;
	parent.frames[4].location.href="about:blank"; 
	parent.frames[2].location.href="about:blank"; 
//	parent.frames[2].location.href ="md_new_abs_btypewise_mir.jsp?pmonth="+pmonth+"&pyear="+pyear+"&MD_GROUP="+a+"&MD_GROUP_DESC="+b+"";
   
}
</script>  
<body>  
 <%
 String label="",path="",res="",Lyear="",pyear="";
 String user_query="";
 String set1="";  
 String fn="",path_="";
	DecimalFormat df = new DecimalFormat("0.00");
	Connection db_con=null;
	Controller obj_new=new Controller();
	Controller obj_new2=new Controller();
	String userid="0",Office_id="",Office_Name="";
	try
	{  
		db_con=obj_new.con();
		obj_new.createStatement(db_con);
	      Lyear=obj_new.setValue("Lyear",request);
		  pyear=obj_new.setValue("pyear",request);
		obj_new2.createStatement(db_con);
		try
		  {
			   userid=(String)session.getAttribute("UserId");
		  }catch(Exception e) {userid="0";}
		  if(userid==null)
		  { 
				  response.sendRedirect(request.getContextPath()+"/index.jsp");
		  }
		Office_id=obj_new.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;		
		Office_Name=obj_new.getValue("COM_MST_OFFICES", "OFFICE_NAME", " where OFFICE_ID="+Office_id);
		try
		{  
			fn= Office_Name.substring(0,10)+userid;
		}catch(Exception e)
		{
			
			Office_Name="";
		}
		if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
		String off=Office_id.equals("5000")?"":" and Office_id="+Office_id;
		Office_Name=Office_id.equals("5000")?"":""+Office_Name+"<br>";
	    
		String BEN_TYPE_GROUP=obj_new.setValue("BEN_TYPE_GROUP",request);
		String ben_group_name=obj_new2.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_GROUP_NAME","where BEN_TYPE_GROUP="+BEN_TYPE_GROUP);
	//	String mvalue=obj_new.month_val(pmonth);
		XmlDataGenerator xml=new XmlDataGenerator(db_con,obj_new);
		user_query="SELECT k.MD_GROUP1 AS bentype, " +
				"  k.MD_GROUP_DESC1 AS bendesc, " +
				"  SUM(a.supplied)  AS supplied, " +
				"  SUM(a.Demand)    AS Demand " +
				"FROM " +
				"  (SELECT BENEFICIARY_SNO, " +
				"    SUM(QTY_CONSUMED) AS supplied, " +
				"    SUM(TOTAL_AMT)    AS Demand " +
				"  FROM PMS_DCB_WC_BILLING " +
				"  WHERE To_Date((MONTH " +
				"    ||'-' " +
				"    || YEAR),'mm-yyyy') BETWEEN To_Date(4 " +
				"    ||'-' " +
				"    ||"+pyear+",'mm-yyyy') " +
				"  AND to_date(3 " +
				"    ||'-' " +
				"    ||"+Lyear+",'mm-yyyy') " +
				"  GROUP BY BENEFICIARY_SNO " +
				"  )a " +
				"LEFT OUTER JOIN " +
				"  (SELECT BENEFICIARY_SNO, " +
				"    BENEFICIARY_TYPE_ID_sub, " +
				"    BENEFICIARY_NAME " +
				"  FROM PMS_DCB_MST_BENEFICIARY " +
				"  )b " +
				"ON a.BENEFICIARY_SNO=b.BENEFICIARY_SNO " +
				"LEFT OUTER JOIN " +
				"  ( SELECT BEN_TYPE_ID,MD_GROUP1,MD_GROUP_DESC1 FROM PMS_DCB_BEN_TYPE " +
				"  )k " +
				"ON k.BEN_TYPE_ID=b.BENEFICIARY_TYPE_ID_sub " +
				"GROUP BY k.MD_GROUP1, " +
				"  k.MD_GROUP_DESC1 " +
				"ORDER BY k.MD_GROUP1";
		  fn="";      
		 path=getServletContext().getRealPath("\\data")+"\\"+fn+"Col3DLineDY.xml";
		  xml.fpath=path;
		  xml.setReport_query(user_query);       
		 res=xml.bar_chart("MD_GROUP_DESC");           
 %>         
<table border=1 class="ft" width="100%" cellpadding="6" cellspacing="0" style="border-collapse: collapse;"  align="left"> 
	<tr class="tdH">
		<td colspan=6 align="center" > <%=Office_Name%>Pumped Qty and Demand Statement 
		<br>
				<font>(for the Financial year <%=pyear%> - <%=Lyear%>)</font>   
				<br>
				<font>(Rupees in Actual)</font>  
		</td>
	</tr>	 
	<tr>  
		<!--<th width='5%' align='center'> Sl.No</th>  -->
	
		<th width='15%' align='center'>Beneficiary Type</th>
		<th width='15%' align='center' >DCB Qty (KL) </th>		
		<th width='15%' align='center'>DCB Amount (Rs.)</th>
		</tr> 
<%	
				 String color1="#CCFFFF",color2="white";       
			 
				ResultSet rs_dis2=obj_new2.getRS(user_query);
				double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
				int row=0;
				int row1=0;
				while(rs_dis2.next())
				{
					String MD_GROUP_DESC=rs_dis2.getString("bendesc");
					if (row==0)
					label="'"+MD_GROUP_DESC+"'";
					else
						label+=",'"+MD_GROUP_DESC+"'";	
					
					row++;		  
					  
					
					String bentype=rs_dis2.getString("bentype");
					String bendesc=rs_dis2.getString("bendesc"); 
					String supplied=rs_dis2.getString("supplied");
					String Demand=rs_dis2.getString("Demand");
					//String MD_GROUP=rs_dis2.getString("MD_GROUP");
					 
					if (row1==0)
					{
					set1="["+ Double.parseDouble(supplied) +","+Math.round(Double.parseDouble(Demand) )+"]";
					}
					else
					{
						set1+=",["+Math.round(Double.parseDouble(supplied) )+","+Math.round(Double.parseDouble(Demand) )+"]";
					}
					row1++;  
					tot1+=Double.parseDouble(supplied);
					tot2+=Double.parseDouble(Demand);
					//tot3+=Double.parseDouble(collection);
				//	tot4+=Double.parseDouble(balance);  
					/*if (row1%2==0)
					out.println("<tr bgcolor='"+color1+"'><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>");
					else
					out.println("<tr bgcolor='"+color2+"'><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>");
					*/   
					out.println("<td align='left' width='15%' ><a href=javascript:ld_1("+bentype+")>"+MD_GROUP_DESC+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(supplied))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(Demand))+"&nbsp;</td></tr>");
				//	out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(collection))+"&nbsp;</td>");
				//	out.println("<td align='right' width='11%'>"+df.format(Double.parseDouble(balance))+"&nbsp;</td></tr>"); 	  				  
				}   
				//<td align='right' width='5%'>&nbsp;</td>
				out.println("<tr bgcolor='#E8E8E8'><td align='right'width='15%' >Total&nbsp;&nbsp;</td>");
				out.println("<td align='right' width='15%'>"+df.format(tot1)+"&nbsp;</td>");
				out.println("<td align='right' width='15%'>"+df.format(tot2)+"&nbsp;</td></tr>");  
			//	out.println("<td align='right' width='10%'>"+df.format(tot3)+"&nbsp;</td>");
			//	out.println("<td align='right' width='11%'>"+df.format(tot4)+"&nbsp;</td></tr>");   
				out.println(" ");  
			
		}catch(Exception e) 
		{  
			e.printStackTrace();       
		}
		    
	 out.println(res);
%>  
</table>
 <input type=hidden id="Lyear" value="<%=Lyear%>">
 <input type=hidden id="pyear" value="<%=pyear%>">
 <input type=hidden id="gr" value="<graph showNames='1'  decimalPrecision='0'  >  <set name='USA' value='20' />   <set name='France' value='7'  />    <set name='India' value='12' />    <set name='England' value='11' />    <set name='Italy' value='8' />    <set ame='Canada' value='19'/>    <set name='Germany' value='15'/></graph>">
 </body> 
</html>  
