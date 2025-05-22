<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.text.DecimalFormat,java.text.NumberFormat"%>
<%@page import="Servlets.Security.classes.UserProfile"%>
<%@page import="java.io.PushbackInputStream"%>
<%@page import="java.util.*,java.sql.*"%>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript">
			function rld()
			{
			var r=document.getElementById("row").value;
			var pmonth=document.getElementById("pmonth").value;
			var pyear=document.getElementById("pyear").value;
			var sub_div_sno=document.getElementById("sub_div_sno").value;
			var res=month_year_check(pmonth,pyear);
				if (res!=1 )
				{
					if (sub_div_sno!=0) {
					document.location.href="sub_div_ben.jsp?pmonth="+pmonth+"&pyear="+pyear+"&sub_div_sno="+sub_div_sno+"&#"+r;
					document.location.submit();
					}else {alert("Select Sub Division");}
				}
			}
			function report()
			{
				var pmonth=document.getElementById("pmonth").value;
				var pyear=document.getElementById("pyear").value;
				var res=month_year_check(pmonth,pyear);
				if (res!=1)
				{
					var sub_div_sno=document.getElementById("sub_div_sno").value;
					if (sub_div_sno!=0) {
						a=window.open("../../../../../dcb_statement?process_code=15&fmonth="+pmonth+"&fyear="+pyear+"&sub_div_sno="+sub_div_sno)
					}else {alert("Select Sub Division");}
				}
			}   
			       
		</script>
		<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/> 
	</head>
<body>
<%
		NumberFormat  decfor=new  DecimalFormat("#0.000");
		NumberFormat  decfor1=new DecimalFormat("#0.00");	
		String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		Controller obj=new Controller();
		Controller obj2=new Controller();
		Connection con=obj.con();  
		obj.createStatement(con);
		obj2.createStatement(con);
		String  inp_month=obj.setValue("pmonth",request);
		String  inp_year=obj.setValue("pyear",request);
		int count=0;
		try
		{
			count=Integer.parseInt(obj.setValue("count",request));
		}catch(Exception e) {count=10;}
		if (count==0) count=10;
		String officeId="0",qry="",condition="",userid="",OFFICE_LEVEL_ID="0";
				try
				{
			  		 userid=(String)session.getAttribute("UserId");
				}catch(Exception e) {userid="0";}
				if(userid==null)
				{
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				
				
				officeId=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			//	officeId=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
				if (officeId.equals("")) officeId="0";
				String Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+officeId+ " ");
				
				OFFICE_LEVEL_ID=obj.getValue("com_mst_offices", "OFFICE_LEVEL_ID","where OFFICE_ID="+officeId+ "  ");
				
				String div=obj.setValue("div", request);
				if ("0".equals(div) || div==null) div=officeId;
				
				
				String division_list="";
				try
				{
			//	division_list=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where   region_office_id="+officeId+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","div",div,"class='select' onchange=javascript:document.change_record.submit();");
				 division_list=obj.combo_str("com_mst_offices","office_name","office_id","where   office_id="+officeId+" and office_id in (select office_id from pms_dcb_div_dist_map)  ","div","class='select' onchange=focusloss()");

				}catch(Exception e) 
				{
				out.println(e);
				}	
				
			
				
%>  <form action="sub_div_ben.jsp" method="post" name="change_record">
	<table align="center" width="100%" border=1  style="border-collapse: collapse;vertical-align: top;"  bordercolor="skyblue" > 
			<tr style="font-size: 17px;font-weight: bold;;font-weight: bold"> 
				<td   align="left" width="50%" colspan="2"> <%=Office_Name %> </td><td align="center" colspan="2"> Sub Division Wise Consolidated Checklist</td>
			</tr> 
	 		<Tr>
			<Td colspan="4">
				<table align="center" width="100%" border=1  style="border-collapse: collapse;"  cellpadding="6" bordercolor="skyblue">
				<tr  style="font-size: 14px">
	          		<td width="10%" align="left">Year<select style="width:64px;" class="select"  id="pyear" name="pyear" onchange="report_period_verify(document.getElementById('pmonth'),this)">
			   			<option value="0">-Select Year-</option>
			  			<%
			  			for (int i=year;i>=2015;i--)
	  	//				  for (int i=year-6;i<=year;i++)
						 {
			    			if (Integer.parseInt(inp_year)!=i) {  
			   			%> 
			  			<option value="<%=i%>"><%=i%></option>
			  				<%}else{%> 
			  			<option value="<%=i%>" selected><%=i%></option>
					  <%} %>
				  <%} %>
			  </select>  </td><td width="15%" > &nbsp; Month &nbsp;  <select style="width:84px;"  class="select" id="pmonth" name="pmonth" onchange="report_period_verify(this,document.getElementById('pyear'))">
			  <option value="0">-Select Month-</option>
			  <%
			  for (int j=1;j<=12;j++)
			  {
			  
			   if (Integer.parseInt(inp_month)!=j) {
			   %>
			    <option value="<%=j%>"><%=monthArr[j]%></option>
			    <%} else { %>
			    <option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
			    <%} %>
			  <%} %>  
			 </select>   </td> <%
					if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
					{
					%> 
					 
				 	 <td  ><font class="tdText">  Division</font>  
				 	 <%=division_list%>
				 	</td>
				 	 
				 	<%}%><td>  &nbsp;  Sub Division   &nbsp;  : 
	<select class="select" id="sub_div_sno" name="sub_div_sno" onchange="javascript:document.change_record.submit();"><option value="0">Select SubDivision </option>	
<%
//	ResultSet rs=obj.getRS("SELECT OFFICE_ID,OFFICE_NAME FROM  com_mst_offices where OFFICE_ID="+div+" ");

 ResultSet rs=obj.getRS("SELECT OFFICE_ID,OFFICE_NAME FROM  COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID="+div+" and OFFICE_LEVEL_ID='SD'");

	String div_name="",sub_div_sno="";
	sub_div_sno=obj.setValue("sub_div_sno",request);
	
	while (rs.next())
	{
		div_name=rs.getString("OFFICE_NAME");
		String sub_div_sno1=rs.getString("OFFICE_ID");
		%>
			<option value="<%=sub_div_sno1%>" <% if (sub_div_sno1.equalsIgnoreCase(sub_div_sno)) { %> selected <% }  %>><%=div_name%></option>
		<%
	}
	%>
	</select><br><input type="button"  style="color:blue;" value="Report" onclick="report()"><input type="button" style="color:blue;" value="Exit" onclick="javascript:window.close()">
	<%
		condition	=" and  a.SUB_DIV_ID="+sub_div_sno ;
		qry="SELECT  t1.*,wc.amt,wc.qty  from ( select a.BENEFICIARY_SNO,b.beneficiary_type_id,b.beneficiary_name,c.sch_name,a.sub_div_id,d.sch_type_shortdesc,c.sch_sno,count(a.METRE_LOCATION) as count,a.tariff_flag,t.ben_type_desc" +
		" from PMS_DCB_MST_BENEFICIARY_METRE a" +
		" join PMS_DCB_MST_BENEFICIARY b on b.status='L' and a.meter_status='L'  and  a.BENEFICIARY_SNO=b.BENEFICIARY_SNO" +
		" join pms_sch_master c on a.SCHEME_SNO=c.sch_sno" +
		" join pms_sch_lkp_type d on a.SCH_TYPE_ID=d.sch_type_id " +
		" JOIN PMS_DCB_BEN_TYPE t " +
		" on t.ben_type_id= b.beneficiary_type_id" +
		" where 0=0 and a.OFFICE_ID = "+officeId+ "  and b.OFFICE_ID= "+officeId + condition +"   group by a.BENEFICIARY_SNO,a.tariff_flag,b.beneficiary_name,c.sch_name,d.sch_type_shortdesc,c.sch_sno,a.sub_div_id" +
				",t.ben_type_desc ,b.beneficiary_type_id order by b.beneficiary_name ) t1 JOIN  (SELECT BENEFICIARY_SNO," +
		    	" OFFICE_ID, " +
		    	" MONTH," +
		    	" YEAR," +
		    	" SCHEME_SNO," +
		    	" SUM(QTY_CONSUMED) AS qty," +
		    	" SUM(TOTAL_AMT)    AS amt" +
		    	" FROM PMS_DCB_WC_BILLING" +
		    	" WHERE MONTH  ="+inp_month +
		    	" AND YEAR     =" +inp_year +
		    	" AND office_id=" +officeId +
		    	"  GROUP BY OFFICE_ID," +
		    	"  BENEFICIARY_SNO," +
		    	"  SCHEME_SNO," +
		    	"  MONTH," +
		    	"  YEAR" +
		    	"   " +
		    	" )wc" +
		    	" on wc.BENEFICIARY_SNO=t1.BENEFICIARY_SNO" +
		    	" and wc.SCHEME_SNO=t1.sch_Sno order by sub_div_id,beneficiary_type_id,beneficiary_name ";
	%>
	</td>
	</tr>
	</table>
	</Td>
	</Tr>
 
			<tr><td colspan="4">
			<table width="100%"  border=1 cellpadding="0" cellspacing="0" style="border-collapse: collapse;vertical-align: top">   
			<Tr   bgcolor="#408080"  style="font-size: 13px;color: white;font-weight: bold;;font-weight: bold;">
					<td align="center">Sl.No</td>
					<td align="center">Beneficiary Name</td>
					<td align="center" width="15%">Beneficiary Type</td>
					<td align="center" width="25%">Scheme Name<br><center><font color="">Click for Tariff Details</font></center></td>
					<td align="center" width="7%">Tariff Mode</td>
					<td align="center" width="7%">Meter Location</td>
					<td align="center" width="7%">Pumping Return Qty</td>
					<td align="center" width="7%">Total Water Charges(Rs.)</td>
			</Tr> 
			</table></td></tr>
			
			<tr>
			     			   
 			<Td colspan="15" align="center">
 				<div id='scroll_clipper' style='position:relative; width:100%; border-height:0px; height: 450px; overflow:auto;white-space:nowrap;'>
				<div id='scroll_text'  >
			 	<table width="100%" border=1 border=0 cellpadding="6" cellspacing="0" style="border-collapse: collapse;"  bordercolor="skyblue">  
  	 			 
	<%		
				String page_select="<select id='row' name='row' onchange='rld()'>";  
				String BeneficiaryName="",BeneficiarySno="",SchemeName="",MeterLocation="",TariffMode="",ben_type_desc="",SchemeType="",SchemeSno="";
				ResultSet rs2=obj2.getRS(qry);
				String qty="",amt="";
				int row=0;
				int srow=0;
				double tot_qty=0.0;
				double tot_amt=0.0;
				String temp="0";
				String namedis="",nodis="";
				while (rs2.next())
				{
					 
				 	 BeneficiaryName=rs2.getString("beneficiary_name");
				 	 BeneficiarySno=rs2.getString("BENEFICIARY_SNO");
				 	 
				 	 if (!temp.equalsIgnoreCase(BeneficiarySno))
				 	 {
				 		 row++;
				 	 nodis=Integer.toString(row);
				 	 namedis=BeneficiaryName;
				 	 }
				 	 else
				 	 {
				 	 namedis="";
				 	 nodis="";
				 	 }
				 		
		        	 SchemeName=rs2.getString("sch_name");
		        	 SchemeType=rs2.getString("sch_type_shortdesc");
		        	 SchemeSno=rs2.getString("sch_sno");
		        	 MeterLocation=rs2.getString("count");
		        	 TariffMode=rs2.getString("tariff_flag");
		        	 ben_type_desc=rs2.getString("ben_type_desc");
		        	 qty=rs2.getString("qty");
		        	 tot_qty+=Double.parseDouble(qty);
		        	 amt=rs2.getString("amt");
		        	 tot_amt+=Double.parseDouble(amt);
		        	
		        	 String lk="";
		        	 if (row==1)
		        	 {
		        		 	srow ++ ;
		        		 	lk="<a name="+srow+">";	
		        		 	page_select+="<option value="+srow+">"+srow+"</option>";
		        	 }
		        	 else
		        	 {
		        		 if (row % 15==0) {
		        			 srow ++ ;
		        		 	lk="<a name="+srow+">";
		        		 	page_select+="<option value="+srow+">"+srow+"</option>";
		        	 	}else
		        	 	{
		        			 lk="";
		        	 	}
		        		 
		        	 }
		        	 
		        	 temp=BeneficiarySno;
		        	 
				%>
					<tr style="font-size: 13px">
						<td align="right" width="7%"><%=nodis%></td>
						<td  align="left"  ><%=lk%><%=namedis%></td>
						<td  align="left" width="15%"><%=ben_type_desc%></td>
						<td align="left" width="25%"><a  href="../jsps/pmsDcbBeneficiaryMetreReport.jsp?BeneficiarySno=<%=BeneficiarySno%>&SchemeSno=<%=SchemeSno%>"><%=SchemeName%></a></td>
						<td align="center" width="7%"><%=TariffMode%></td>
						<td align="center" width="7%"> <%=MeterLocation%></td>
						<td align="right" width="7%"><%=qty%></td>
						<td align="right" width="7%"><%=amt%></td>
						
					</tr>
		  	 		
				<%}
				page_select+="</select>";
				%>
					</table></div></div>
				<%
	
	
%>			
			
  	




</Td>

</tr>
<tr   style="font-size: 14px;color: blue;font-weight: bold" >
		<td  width="20%" align="left" >Total Pumping Return Quantity: </td><td  width="20%" align="left">&nbsp; &nbsp; &nbsp; <%=decfor.format(tot_qty)%> &nbsp;&nbsp;&nbsp;&nbsp;</td><td  width="40%" align="right" > Net Water Charges :</td><td  width="20%" align="right">     <%=decfor1.format(tot_amt) %> </td>
</tr>
   
 </table>
 </form>
 </body>
 </html>
 
 