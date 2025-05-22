<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.PMS.PMS1.DCB.servlets.Controller"%>
<%@ page import="java.sql.Connection,java.sql.ResultSet,java.sql.PreparedStatement,java.sql.Statement"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<%!String beneficiarySno = "";
	String SchemeSno = "";%>
<%
	String beneficiarySno = request.getParameter("BeneficiarySno");
	String SchemeSno = request.getParameter("SchemeSno");
	String new_cond=Controller.new_cond;
	Controller Obj = new Controller();
	Connection con = null;
	PreparedStatement ps = null;

	String ben_name = "", sch_name = "";

	try {
		con = Obj.con();
		ps = con.prepareStatement("select b.beneficiary_name as BeneficiaryName,c.sch_name as SchemeName from PMS_DCB_MST_BENEFICIARY_METRE a"
				+ " join PMS_DCB_MST_BENEFICIARY b on b.status='L' and a.meter_status='L' and a.BENEFICIARY_SNO=b.BENEFICIARY_SNO"
				+ " join pms_sch_master c on a.SCHEME_SNO=c.sch_sno where b.BENEFICIARY_SNO='"
				+ beneficiarySno
				+ "' and "
				+ " c.sch_sno='"
				+ SchemeSno + "'"); 
		
 
		ResultSet res = ps.executeQuery();
		if (res.next()) {
			//System.out.println("inside loop");
			ben_name = res.getString("BeneficiaryName");
			sch_name = res.getString("SchemeName");
		}
	} catch (Exception e) {
		System.out.println(e + "not reterived!");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<title>Beneficiary Scheme Report | TWAD Nest - Phase II</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<!--  <script type="text/javascript" src="../scripts/pmsDcbBeneficiaryMetreReport.js"></script> -->
<style type="text/css">
table.border1 {
	color: #000000;
	padding: 0px;
	border-top: 1px solid #dddddd;
	border-left: 1px solid #dddddd;
	border-bottom: 0px solid #dddddd;
	border-right: 0px solid #dddddd;
	font-size: 14 px;
}

table.border1 th,table.border1 td {
	padding: 5px;
	padding-bottom: 2px;
	border-top: 0px solid #dddddd;
	border-left: 0px solid #dddddd;
	border-bottom: 1px solid #dddddd;
	border-right: 1px solid #dddddd;
}
</style>
</head>
<link href="../../../../../css/txtbox.css" rel="stylesheet"
	media="screen" />

<body>
	<form name="beneficaryMeterReport" action="">
		<table class="fb2" border="1" width="100%" align="center"
			cellpadding="0" cellspacing="0" style="BORDER-COLLAPSE: collapse"
			borderColor="#92c2d8">
			<tr class="tdH" align="center">
				<td colspan="6">
					<div align="center">
						<font size=3> Beneficiary Meter Report </font> <label
							id="divisionname"></label>
					</div></td>
			</tr>
			<tr>
				<td class="tdText">Beneficiary Name</td>
				<td style="padding-left: 50px;" class="tdText"><label id="ben"><%=ben_name%></label>
					<input type="hidden" name="beneficiarySno" id="beneficiarySno"
					value=<%=beneficiarySno%>></td>
			</tr>
			<tr>
				<td class="tdText">Scheme Name</td>
				<td style="padding-left: 50px;" class="tdText"><label id="sch"><%=sch_name%></label>
					<input type="hidden" name="schemeSno" id="schemeSno"
					value=<%=SchemeSno%>></td>
			</tr>

		</table>
		<br>

		<table class="fb2" id="existing" border="1" width="100%"
			align="center" style="BORDER-COLLAPSE: collapse" border="1"
			borderColor="#92c2d8">
			<tr class="tdH">
				<th class="tdText">Sno</th>
				<th class="tdText">Meter Location</th>
				<th class="tdText">Whether Meter<br>fixed?</th>
				<th class="tdText">Whether Meter<br>Working?</th>
				<th class="tdText">Meter Type</th>
				<th class="tdText">Multiplying<br>factor</th>
				<th class="tdText">Meter initial<br> reading</th>
				<th class="tdText">Parent Meter</th>
				<th class="tdText">Minimum<br>Billing<br>Yes/No<br>(Qty	in KL)</th>
				<th class="tdText">Allotted<br>Quantity<br>Yes/No<br>(Qty in KL)</th>
				<th class="tdText">Tariff Rate Details</th>
			</tr>
			<!-- <tbody id="getvaluerows" class="tdText">
        </tbody>  -->
			<%
				
				System.out.println("inside Report");
				String METRE_SNO="",TARIFF_FLAG="";
				try {
					String qry = " SELECT a.TARIFF_FLAG,a.METRE_LOCATION,"
							+ "  a.METRE_FIXED, "
							+ "  a.METRE_WORKING, "
							+ "  a.METRE_TYPE,"
							+ "  a.MULTIPLY_FACTOR,"
							+ "  a.METRE_INIT_READING, "
							+ "  a.PARENT_METRE, "
							+ "  a.BENEFICIARY_SNO,"
							+ "  a.METRE_SNO,"
							+ "  a.SCHEME_SNO, d.ALLOT_QTY, d.MIN_QTY,"
							+ "  d.MIN_FLAG, "
							+ "  d.ALLOT_FLAG  from pms_dcb_mst_beneficiary_metre a"
							+ "  left outer join PMS_DCB_ALLOTTED d on"
							+ "  a.metre_sno=d.metre_sno  WHERE " +

							"  a.beneficiary_sno ='" + beneficiarySno
							+ "' AND a.meter_status='L' and  a.scheme_sno ='" + SchemeSno
							+ "'  order by a.BENEFICIARY_SNO";
	
					 
					ps = con.prepareStatement(qry);

					ResultSet res = ps.executeQuery();

					int count = 1;
					String ben_sno = "", sch_sno = "";
			
				while (res.next()) {
					METRE_SNO=res.getString("METRE_SNO");
					TARIFF_FLAG=res.getString("TARIFF_FLAG");
			%>
					<tr>
					<td class="tdText"><%=count++%></td>
			
					<td class="tdText"><%=res.getString("METRE_LOCATION")%>(<%=METRE_SNO%>)</td>
			
					<td class="tdText"><%if(res.getString("METRE_FIXED")=="y")
										{
										out.println("Yes");
										}
										else
										{
										out.println("No");
										}
										%></td>
			
					<td class="tdText"><%if(res.getString("METRE_WORKING")=="Y")
										{
										out.println("Yes");
										}
										else
										{
										out.println("No");
										}
										%></td>  
			
					<td class="tdText"><%=res.getString("METRE_TYPE")%></td>
			
					<td class="tdText"><%=res.getString("MULTIPLY_FACTOR")%></td>
				
					<td class="tdText"><%=res.getString("METRE_INIT_READING")%></td>
			
					<td class="tdText"><%if(res.getString("PARENT_METRE")=="y")
										{
										out.println("Yes");
										}
										else
										{
										out.println("No");
										}
										%></td>
					<td class="tdText"><%if(res.getString("MIN_FLAG")=="Y")
										{
										out.println("Yes");
										}
										else
										{
										out.println("No");
										}
										%></td>
			
					<td class="tdText"><%if(res.getString("ALLOT_FLAG")=="Y")
										{
										out.println("Yes");
										}
										else
										{
										out.println("No");
										}
										%></td>

					<td class="tdText"><%
					//ben_sno= res.getString("BENEFICIARY_SNO"); 
					//sch_sno=res.getString("SCHEME_SNO"); 
					//out.println("ben_sno====================="+ben_sno+"sch_sno====================="+sch_sno);
							ResultSet rs=null;
							try
							{
								Connection conn=Obj.con();
								Statement stmt=conn.createStatement();
								
								String cond="";
								
								if (TARIFF_FLAG.equalsIgnoreCase("L"))
									cond="   METRE_SNO="+METRE_SNO+" and ";
								else
									cond=" ";
								
								String qury="SELECT BENEFICIARY_SNO," +
									"  SCH_SNO, QTY_FROM," +
									"  QTY_TO, TARIFF_RATE, TARIFF_FLAG," +
									"  METRE_SNO" +
									"  FROM PMS_DCB_TARIFF_SLAB  WHERE active_status='A' and "+cond+
									"  BENEFICIARY_SNO="+res.getString("BENEFICIARY_SNO")+" AND SCH_SNO="+res.getString("SCHEME_SNO") +" order by BENEFICIARY_SNO";  // and metre_sno="+ metre;
									     
								Obj.testQry(qury);
								rs=stmt.executeQuery(qury);	
					     	        out.println("<table valign=top width='100%' align='center' border='1'  cellspacing='0' cellpadding='0' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>");
					     	        out.println("<tr class='tdH'><td class='tdText' align='center'>Quantity From</td><td class='tdText' align='center'>Quantity To</td><td class='tdText' align='center'>Tariff Rate</td></tr><tr>");
									while(rs.next())
									{ 
									%>
									<tr>
										<td class="tdText"><%=rs.getString("QTY_FROM")%></td>
										<td class="tdText"><%=rs.getString("QTY_TO")%></td>
										<td class="tdText"><%=rs.getString("TARIFF_RATE")%></td>
									</tr>	
									<%
									}	
							out.println("</table>");
							}
							catch (Exception e) {
							System.out.println(e + "not reterived!");
						}
										
					%> 

					 
					
					<!-- xmlResponse +=generateXML("METRE_SNO",metre_sno,1,Obj);
				        	
				        	//xmlResponse+=generateXML("QTY_FROM",res.getString("QTY_FROM"),1,Obj);
				        	//xmlResponse+=generateXML("QTY_TO",res.getString("QTY_TO"),1,Obj);
				        	//xmlResponse+=generateXML("TARIFF_RATE",res.getString("TARIFF_RATE"),1,Obj); 

							ben_sno= res.getString("BENEFICIARY_SNO"); 
					sch_sno=res.getString("SCHEME_SNO"); 		   -->
			<%
			} }
				catch (Exception e) {
					System.out.println(e + "not reterived!");
				}
			%>
			
		</table>
		<br>
		<table align="center" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
			<tr>
				<td><input type="button" name="cmdexit" value="Back" id="cmdlist" onclick="javascript:history.go(-1)" class="fb2" /><input type="button" name="cmdexit" value="Exit" id="cmdlist"onclick="self.close();" class="fb2" />
				<td>
			</tr>
		</table>
	</form>
</body>
</html>
