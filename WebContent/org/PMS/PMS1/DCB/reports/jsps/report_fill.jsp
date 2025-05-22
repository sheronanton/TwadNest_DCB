<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script  src="../../scripts/FusionCharts.js"></script>
 <script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
  <tr> 
    <td valign="top" class="text" align="center"> <div id="chartdiv" align="center"> 
        FusionCharts. </div>
      <script type="text/javascript">
		   var chart = new FusionCharts("../Charts/FCF_MSColumn3D.swf", "ChartId", "600", "350");
		   chart.setDataURL("../data/Col3DLineDY.xml");    
		   chart.render("chartdiv");
		</script> </td>
  </tr>
  </table>
</body>
</html>