<html>
<head>     
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
<%@page import="java.util.*,java.sql.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="../../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<title></title>
<script type="text/javascript" src="../../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../../scripts/dcbvalidation.js"></script>
<script type="text/javascript">
function Process()
{
	if (document.getElementById("month").value == 0 || document.getElementById("month").value == ""  )
	{
		alert("Enter the Month ");
		return false;
	}
	if (document.getElementById("year").value == 0 || document.getElementById("year").value == "")
	{
		alert("Enter the Year ");
		return false;
	}
	
var New_off_id=document.getElementById("New_off_id").value;
var url="Office_Shift_new?command=getdata&New_off_id="+New_off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
	alert(req.responseText);
	if(req.status==200)
	{
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getdata")
		{
		if(flag=="success")
			{
			var New_off_name=response.getElementsByTagName("New_off_name")[0].firstChild.nodeValue;
			var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[0].firstChild.nodeValue;

			document.getElementById("New_off_name").value=New_off_name;
			document.getElementById("New_off_status").value=OFFICE_STATUS_ID;

			}}}}};
req.send(null);
}


</script>
</head>
<body>
 <table border="1" width="50%" align="center" cellpadding="7" cellspacing="0" >
	 <tr>
	     <td><font color="#0000A0">Enter the month </font></td>
		 <td colspan="2">
		 <input type="text" id="month" maxlength="2" name="month"  size="5">
		 		     </td>
		     			  </tr>
		     			  <tr>
	     <td><font color="#0000A0">Enter the Year </font></td>
		 <td colspan="2">
		 <input type="text" id="year" maxlength="4" name="year"  size="5">
		 		     </td>
		     			  </tr>
		     			  <tr><td colspan="2" align="center">
<input type="button" class="fb2"  value="Submit" onclick="Process()">
<input type="button" class="fb2"  value="Close" onclick="exitwindow();">

</td>
</tr>
	</table>
	
</body>
</html>
 