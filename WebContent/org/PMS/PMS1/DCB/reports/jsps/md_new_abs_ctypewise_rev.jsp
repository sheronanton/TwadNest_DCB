<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller,java.sql.*" %>
<%@page import="java.util.*"%> 
<%@page import="Servlets.PMS.PMS1.DCB.reports.MD_Abstract"%>
 <%@page import="java.text.DecimalFormat"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.XmlDataGenerator"%>
<%@page import="org.apache.axis.types.Year"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DCB-HOD Report</title>
<script src="../../scripts/libraries/RGraph.common.core.js" ></script>
<script src="../../scripts/libraries/RGraph.bar.js" ></script>
<link href="../../../../../../css/abstract_report.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function report(process_code)
{
	var pyear=document.getElementById("pyear").value;
	var pmonth=document.getElementById("pmonth").value;
	var res=month_year_check(pmonth,pyear);
	if (res!=1)
	{	
	window.open("../../../../../../PMS_DCB_REVIEW_REPORT?process_code="+process_code+"&pyear="+pyear+"&pmonth="+pmonth);
	} 
}
</script>
</head>
<script  src="../../scripts/FusionCharts.js"></script>
<body>  
 <%
	String pie_chart_xml="";
	String label="",path="",res="";
	String user_query="";
	String set1="";  
	String fn="",path_="";
	DecimalFormat df = new DecimalFormat("0.00");
	Connection db_con=null;
	Controller obj_new=new Controller();
	Controller obj_new2=new Controller();
	String userid="0",Office_id="",Office_Name="",pmonth="",pyear="";
	try
	{
		db_con=obj_new.con();
		obj_new.createStatement(db_con);
		  pmonth=obj_new.setValue("pmonth",request);
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
		obj_new.dis(Office_id);
		if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
		String off=Office_id.equals("5000")?"":" and Office_id="+Office_id;
		Office_Name=Office_id.equals("5000")?"":""+Office_Name+"<br>";
		if (Office_Name.trim()==("0").trim()) Office_Name="";
		String BEN_TYPE_GROUP=obj_new.setValue("BEN_TYPE_GROUP",request);
		String ben_group_name=obj_new2.getValue("PMS_DCB_BEN_TYPE","BEN_TYPE_GROUP_NAME","where BEN_TYPE_GROUP="+BEN_TYPE_GROUP);
		String mvalue=obj_new.month_val(pmonth);
		int prv_month=obj_new.prv_month(Integer.parseInt(pyear),Integer.parseInt(pmonth));
		int prv_year=obj_new.prv_year(Integer.parseInt(pyear),Integer.parseInt(pmonth));
		String prv_mvalue=obj_new.month_val(Integer.toString(prv_month));
		XmlDataGenerator xml=new XmlDataGenerator(db_con,obj_new);
		user_query=" select MD_GROUP,MD_GROUP_DESC,ob,dmd,collection,(ob+dmd)-collection as balance from ( "+
		" SELECT ROUND( TRUNC((OPENING_BAL_WC_8+DMD_UPTO_PRV_MNTH_WC_9)-(COLN_UPTO_PRV_MTH_YES_YR_WC_12+COLN_UPTO_PRV_MTH_CR_YR_DMD_13) ,3),2) AS ob, "+
			" ROUND(TRUNC(DMD_FOR_MTH_WC_10,3),2) AS dmd,  ROUND(TRUNC((COLN_FOR_MTH_YES_YR_WC_14+COLN_FOR_MTH_WC_15),3),2)  AS collection, "+
			  " ROUND(TRUNC(BALANCE_18,2),3) AS balance , rpt_prv.MD_GROUP,rpt1.MD_GROUP_DESC     "+
			  " FROM "+
			" (SELECT DECODE(SUM(OPENING_BAL_WC),NULL,0.0,SUM(OPENING_BAL_WC)) /100000 AS OPENING_BAL_WC_8, "+
					  " DECODE(SUM(DMD_UPTO_PRV_MNTH_WC),NULL,0.0,SUM(DMD_UPTO_PRV_MNTH_WC)) /100000 AS DMD_UPTO_PRV_MNTH_WC_9, "+
			    " DECODE(SUM(DMD_FOR_MTH_WC),NULL,0.0,SUM(DMD_FOR_MTH_WC)) /100000 AS DMD_FOR_MTH_WC_10, "+
			    " DECODE(SUM(COLN_UPTO_PRV_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_YES_YR_WC)) /100000 AS COLN_UPTO_PRV_MTH_YES_YR_WC_12, "+
			    " DECODE(SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD),NULL,0.0,SUM(COLN_UPTO_PRV_MTH_CR_YR_DMD)) /100000 AS COLN_UPTO_PRV_MTH_CR_YR_DMD_13,    "+ 
			    " DECODE(SUM(TOT_COLN_FOR_YR_DMD),NULL,0.0,SUM(TOT_COLN_FOR_YR_DMD))/100000 AS TOT_COLN_FOR_YR_DMD_17, "+
			    " DECODE(SUM(BALANCE_18),NULL,0.0,SUM(BALANCE_18))/100000 AS BALANCE_18,MD_GROUP, MONTH, YEAR FROM PMS_DCB_LEDGER_ACTUAL "+
			    " WHERE SCH_TYPE_ID_SUB IS NOT NULL AND YEAR="+prv_year+" AND MONTH="+prv_month+" GROUP BY YEAR,MONTH,MD_GROUP) rpt_prv "+
			  " join  ( "+
					  " SELECT  DECODE(SUM(COLN_FOR_MTH_YES_YR_WC),NULL,0.0,SUM(COLN_FOR_MTH_YES_YR_WC)) /100000 AS COLN_FOR_MTH_YES_YR_WC_14, "+
			  " DECODE(SUM(COLN_INCLUDE_CHARGES),NULL,0.0,SUM(COLN_INCLUDE_CHARGES)) /100000 AS COLN_FOR_MTH_WC_15, MD_GROUP ,MONTH,YEAR  FROM PMS_DCB_LEDGER_ACTUAL "+
			    " WHERE SCH_TYPE_ID_SUB IS NOT NULL AND YEAR ="+pyear+" AND MONTH="+pmonth+"  GROUP BY YEAR,MONTH, MD_GROUP)rpt_coll  on (rpt_prv.MD_GROUP=rpt_coll.MD_GROUP) "+
			  " JOIN "+
			  " ( SELECT DISTINCT MD_GROUP, MD_GROUP_DESC FROM PMS_DCB_BEN_TYPE "+
					  "  )rpt1 "+
			" ON rpt1.MD_GROUP=rpt_prv.MD_GROUP ORDER BY MD_GROUP )";
		  fn="";    
		 
		 path=getServletContext().getRealPath("\\data")+"\\"+fn+"Col3DLineDY.xml";
		 xml.fpath=path;
		 xml.setReport_query(user_query);
		 String head1="Demand for "+prv_mvalue+"-"+prv_year;
		 String head2="Collection during "+mvalue+"-"+pyear;
		 res=xml.bar_chart("MD_GROUP_DESC",head1,head2);            
		 pie_chart_xml="<graph showNames='1' formatNumberScale='2' showPercentValues='1'>";
 %>      
<table border=1  width="72%" cellpadding="2" cellspacing="0" style="border-collapse: collapse;"  align="center"> 
	<tr class="tdH">
		<td colspan=6 align="center"> <%=Office_Name%>Demand,Collection and Balance Statement 
		<br>
				<font size=2>(for the month of <%=mvalue%> <%=pyear%>)</font>   
				<br>
				<font size=2>(Rupees in Lakhs)</font>   
		</td>
	</tr> 
	<tr>
		<!--<th width='5%' align='center'> Sl.No</th>  -->
		<th width='25%' align='center'>Beneficiary Type</th>
		<th width='20%' align='center'>Opening Balance for <%=prv_mvalue%>-<%=prv_year%></th>
		<th width='20%' align='center' >Demand  for<%=prv_mvalue%>-<%=prv_year%> </th>		
		<th width='20%' align='center'>Collection during <%=mvalue%>-<%=pyear%></th>
		<th width='20%' align='center'>Balance</th>
	</tr> 
	<tr><th width='45%' align='center'>&nbsp;</th>
		<th width='10%' align='center'>A</th>
		<th width='10%' align='center'>B</th>		
		<th width='10%' align='center'>C</th>
		<th width='10%' align='center'>D=(A+B)-C</th>
	</tr>
<%	
				 String color1="#CCFFFF",color2="white";       
			 
				ResultSet rs_dis2=obj_new2.getRS(user_query);
				double tot1=0,tot2=0,tot3=0,tot4=0,tot5=0;
				int row=0;
				int row1=0;
				while(rs_dis2.next())
				{
					String MD_GROUP_DESC=rs_dis2.getString("MD_GROUP_DESC");
					if (row==0)
					label="'"+MD_GROUP_DESC+"'";
					else
						label+=",'"+MD_GROUP_DESC+"'";	
					
					row++;	
					String ob=rs_dis2.getString("ob");
					String dmd=rs_dis2.getString("dmd"); 
					String collection=rs_dis2.getString("collection");
					String balance=rs_dis2.getString("balance");
					String MD_GROUP=rs_dis2.getString("MD_GROUP");
					 
					if (row1==0)
					{
					set1="["+ Double.parseDouble(ob) +","+Math.round(Double.parseDouble(dmd) )+","+Math.round(Double.parseDouble(collection) )+","+Math.round(Double.parseDouble(balance) )+"]";
					}
					else
					{
						set1+=",["+Math.round(Double.parseDouble(ob) )+","+Math.round(Double.parseDouble(dmd) )+","+Math.round(Double.parseDouble(collection) )+","+Math.round(Double.parseDouble(balance) )+"]";
					}
					row1++;
					tot1+=Double.parseDouble(ob);
					tot2+=Double.parseDouble(dmd);
					tot3+=Double.parseDouble(collection);
					tot4+=Double.parseDouble(balance);  
					/*if (row1%2==0)
					out.println("<tr bgcolor='"+color1+"'><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>");
					else
					out.println("<tr bgcolor='"+color2+"'><td align='center' width='6%'>&nbsp;"+row+"&nbsp;</td>");
					*/
					pie_chart_xml+="<set name='"+MD_GROUP_DESC+"' value='"+Double.parseDouble(balance)+"'/>";
					out.println("<td align='left' width='45%' ><a href=md_new_abs_btypewise_rev.jsp?pmonth="+pmonth+"&pyear="+pyear+"&MD_GROUP="+MD_GROUP+">"+MD_GROUP_DESC+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(ob))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(dmd))+"&nbsp;</td>");
					out.println("<td align='right' width='10%'>"+df.format(Double.parseDouble(collection))+"&nbsp;</td>");
					out.println("<td align='right' width='11%'>"+df.format(Double.parseDouble(balance))+"&nbsp;</td></tr>"); 					  
				}   //<td align='right' width='5%'>&nbsp;</td>
				out.println("<tr bgcolor='#E8E8E8'><td align='right'width='45%' >Total&nbsp;&nbsp;</td>");
				out.println("<td align='right' width='10%'>"+df.format(tot1)+"&nbsp;</td>");
				out.println("<td align='right' width='10%'>"+df.format(tot2)+"&nbsp;</td>");  
				out.println("<td align='right' width='10%'>"+df.format(tot3)+"&nbsp;</td>");
				out.println("<td align='right' width='11%'>"+df.format(tot4)+"&nbsp;</td></tr>");
				out.println(" ");  
			
		}catch(Exception e) 
		{  
			e.printStackTrace(); 
			out.println(e);		} 
	 out.println(res);
	 pie_chart_xml+="</graph>";  
%>  
<tr><td align="center" colspan="6"><input type="button" value="Print" onclick="report(1)"><input type="button"  style="color: red;font-weight: bold"  value="Close" onclick="javascript:window.close()"></td></tr>
</table>
 <table width="72%" border="1" cellspacing="0" cellpadding="0" align="center" style="border-collapse: collapse;">
  <tr> 
    <td valign="top"  class="text" align="center"> <div id="chartdiv" align="center"></div>  
      <script type="text/javascript">
		   var chart = new FusionCharts("../Charts/FCF_MSColumn2D.swf", "ChartId", "400", "380","0","1");       
		   chart.setDataXML("<%=res%>");	    
		   chart.render("chartdiv");  
		</script> </td>  
		<td><center>Balance</center><div id="chartdiv1" align="center"></div> 
		 <script type="text/javascript">
		   var chart = new FusionCharts("../Charts/FCF_Pie3D.swf", "ChartId", "400", "350");
		   chart.setDataXML("<%=pie_chart_xml%>");		     
		   chart.render("chartdiv1");
		</script> 
		</td>
  </tr>
  </table>   
  <input type="hidden" name="pyear" id="pyear" value="<%=pyear%>">
  <input type="hidden" name="pmonth" id="pmonth" value="<%=pmonth%>">
  
 <a href="GIS_UI.jsp">.</a>
</body>
</html>
