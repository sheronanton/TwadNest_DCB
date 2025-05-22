
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page session="false" contentType="text/html;charset=windows-1252"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Office Transfer Routine</title>
<script type="text/javascript" src="../scripts/Office_Shift.js"></script>
	<script src="jquery-3.6.0.js" type="text/javascript"></script> 
	 <script type="text/javascript" src="../scripts/cellcreate.js"></script>
 <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
      <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>

<script type="text/javascript">
  function AllowNumbersOnly(e) {
    var code = (e.which) ? e.which : e.keyCode;
    if (code > 31 && (code < 48 || code > 57)) {
      e.preventDefault();
    }
  }
</script>
<script type="text/javascript">
$(document).ready(function () {
    //Disable cut copy paste
    $('body').bind('cut copy paste', function (e) {
        e.preventDefault();
    });
   
    //Disable mouse right click
//   $("body").on("contextmenu",function(e){
 //     return false;
 //  });
});
</script>

</head>
<body onload="loadgrid(),loadgrid1()">

<% 
HttpSession session = request.getSession();
String userid=(String)session.getAttribute("UserId");
	         	
%>
<br><br>
 
<table align="center"  cellpadding="2" border="2">
<tr bgcolor="skyblue" align="center">
	<td colspan="8" align="center" class="tdText"><b>Office Transfer Routine</b></td>
</tr>
<tr bgcolor="skyblue" align="center">
	<td colspan="8" align="center" class="tdText"><b>View Office Details (Closed) To Be Transferred</b></td>
</tr>
<tr class="tdText" align="center">
<td>Enter From Office &nbsp;&nbsp;
</td>

<td>
<input type="text" id="frm_off" name="frm_off" maxlength="4" size="4" onkeypress = "return AllowNumbersOnly(event)"  onblur="getdata(),loadgrid(),loadgrid1()"  >
</td>
<td>&nbsp;&nbsp;Office Name &nbsp;&nbsp;
</td>
<td>
<input type="text" id="off_name" name="off_name" size="45" readonly >
</td>
<td>&nbsp;&nbsp;Office Level &nbsp;&nbsp;
</td>
<td>
<input type="text" id="off_level" name="off_level" readonly size="5">
</td>
<td>&nbsp;&nbsp;Office Status &nbsp;&nbsp;
</td>
<td>
<input type="text" id="off_status" name="off_level" readonly size="15">
</td>
</tr>
</table>
<br>
<table id="igrid" align="center" width="75%" cellpadding="2" border="1">
<tr bgcolor="skyblue" align="center">
	<td colspan="8" align="center" class="tdText"><b>Based on Sub Division</b></td>
</tr>
 <tr class="tdText" align="center" bgcolor="skyblue">
 <th>Sub Division Id</th>
<th>Office Name</th>
<th>Office Level</th>
<th>Office Status</th>
<th>BENEFICIARY</th>

 </tr>
 <tbody id="tgrid" align="center"></tbody>
 </table>
<br>
  
<table id="igrid1" align="center" width="75%" cellpadding="2" border="1">
<tr bgcolor="skyblue" align="center">
	<td colspan="8" align="center" class="tdText"><b>Based on Scheme</b></td>
</tr>
 <tr class="tdText" align="center" bgcolor="skyblue">
<th>Scheme No</th>
<th>Scheme Name</th>
<th>BENEFICIARY</th>

 </tr>
 
 <tbody id="tgrid1" align="center" ></tbody>
 </table>
  <br>
  
  <center>
   <input type="button"  value="Click To View All Office Region Wise Details (Active)"  onclick="Help()" class="sa" >
  <input type="button" value="Proceed with Office Transfer ---->"  onclick="proceed()" class="sa">
  <input type="button"  value="Exit"  onclick="self.close()" class="sa" >
  
  
 </center>      
                 
</body>



</html>


