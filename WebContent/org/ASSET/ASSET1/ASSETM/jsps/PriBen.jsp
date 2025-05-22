<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.*"%>
<%@page import="java.util.*,java.sql.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Private Beneficiaries</title>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript">
function fun()
{
	
	window.close();
	

}

</script>
</head>

<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />
<body>
	<form action="">	
   <%

    String sch_name="";
	try 
	{
		Controller obj = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
		
		 sch_name="SELECT met.SCHEME_SNO,"
			 +" ben.BENEFICIARY_NAME as ben_name,"
			 +" btype.BEN_TYPE_DESC as ben_desc,"
			 +" sch.SCH_NAME"
			 +" FROM "
			 +" (SELECT BENEFICIARY_SNO,"
			 +"  BENEFICIARY_NAME,"
			 +" BENEFICIARY_TYPE_ID,"
			 +" OFFICE_ID"
			 +" FROM PMS_DCB_MST_BENEFICIARY"
			 +" WHERE STATUS            ='L'"
			 +" AND BENEFICIARY_TYPE_ID >6"
			 +" AND office_id           =5982 )ben"
			 +" JOIN"
			 +"( SELECT DISTINCT SCHEME_SNO,"
			 +" BENEFICIARY_SNO,"
			 +" office_id"
			 +"  FROM PMS_DCB_MST_BENEFICIARY_METRE"
			 +" WHERE METER_STATUS='L'"
			 +" AND office_id     = 5982 )met"
			 +" ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO"
			 +" AND met.office_id     =ben.office_id"
			 +" JOIN"
			 +" ( SELECT SCH_SNO,SCH_NAME FROM PMS_SCH_MASTER)sch"
			 +" ON sch.SCH_SNO=met.SCHEME_SNO"
			 +" JOIN"
			 +" (SELECT BEN_TYPE_ID, BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE)btype"
			 +" ON btype.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID"
			 +" ORDER BY BEN_TYPE_DESC,"
			 +" BENEFICIARY_NAME,"
			 +" SCH_NAME";
		 
		//out.println(sch_name);
		 PreparedStatement ps = con.prepareStatement(sch_name);
		ResultSet rs = ps.executeQuery();
		 System.out.println(sch_name);
			
	%>
			<table border="1" width="70%" cellpadding="7" cellspacing="0" align="left">
			<tr  class="tdH"><td colspan="6" align="center">Service Reservoirs(Private Beneficiaries)</td></tr>
			<tr><td style="font-size:large;height: 2cm;">S.no</td>
			<td style="font-size:large;">Name of Beneficiary:</td>
			<td style="font-size:large;">Name of Habitation/Location</td>
			<td style="font-size:large;">Capacity of OHT&nbsp;(LL)</td>
			<td style="font-size:large;">Quantity&nbsp;(nos)</td>
			<td width="20%"></td></tr>
			
	<%
	      
	int i=1;
			while(rs.next())
			{
				out.println("<tr><td>"+i+"</td><td rowspan=j>"+rs.getString("ben_name")+"&nbsp;/&nbsp;"+rs.getString("ben_desc")+"</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
						+"</td><td><input type=button value='Add Location' id=aid>"+"&nbsp;"+"<input type=button value=Cancel id=cid></td></tr>");
					i+=1;
								}
			
	  }
	catch(Exception e)
	  {
		out.println(e);
	}

	%>
	<tr><td colspan="6" align="center"><input type="button" name="exit" value="EXIT" onclick="fun()"></td></tr>
</table>	
		
</form>		
</body>
</html>