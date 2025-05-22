<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
      <link href='../../../../../css/CalendarControl.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
	<script src="jquery-3.6.0.js" type="text/javascript"></script> 
	
<script>
$(document).ready(function(){
    $("#d1").hide();
    $("#b1").click(function(){
        $("#d1").slideToggle();
    });
});
</script>
<script>
$(document).ready(function(){
    $("#d2").hide();
    $("#b2").click(function(){
        $("#d2").slideToggle();
    });
});
</script>
<script>
$(document).ready(function(){
    $("#d3").hide();
    $("#b3").click(function(){
        $("#d3").slideToggle();
    });
});
</script>
<script>
$(document).ready(function(){
    $("#d4").hide();
    $("#b4").click(function(){
        $("#d4").slideToggle();
    });
});
</script>
<script>
$(document).ready(function(){
    $("#d5").hide();
    $("#b5").click(function(){
        $("#d5").slideToggle();
    });
});
</script>

<script>
$(document).ready(function(){
    $("#d6").hide();
    $("#b6").click(function(){
        $("#d6").slideToggle();
    });
});
</script>

<script>
$(document).ready(function(){
    $("#d7").hide();
    $("#b7").click(function(){
        $("#d7").slideToggle();
    });
});
</script>

<script type="text/javascript">
$(document).ready(function () {
    //Disable cut copy paste
    $('body').bind('cut copy paste', function (e) {
        e.preventDefault();
    });
   
    //Disable mouse right click
 //  $("body").on("contextmenu",function(e){
 //     return false;
 //  });
});
</script>


<title>Shifting of Closed Office along with Beneficiaries to New Office</title>
<script type="text/javascript" src="../scripts/Office_Shift_new.js">
</script>
</head>
<body onload="setvalue('<%=request.getParameter("frm_off")%>','<%=request.getParameter("off_name")%>','<%=request.getParameter("off_status")%>'),loadgrid1(),loadgrid(),loadgrid5(),loadgrid4(),loadgrid6()">


<% 
HttpSession session = request.getSession();
String userid=(String)session.getAttribute("UserId");
	         	
%>
<br>
 <input type="text" id="user_id"  name="user_id" size="4" readonly  hidden value="<%=(String)session.getAttribute("UserId") %>"  >
 <input type="text" id="Da_te"  name="Da_te" size="4" readonly hidden   >
 <input type="text" id="getmonth1" size="14" readonly  hidden="true" >


<table align="center"  cellpadding="2" border="2" width="90%">
<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Shifting of Closed Office along with Beneficiaries to New Office</b></td>
</tr>
<tr class="tdText" align="center">
<td align="center" class="tdText"> From Office Id : </td>
<td align="center" class="tdText"><%=request.getParameter("frm_off")%></td>
<td align="center" class="tdText"> Office Name  : </td>
<td align="center" class="tdText"><%=request.getParameter("off_name")%></td>
<td align="center" class="tdText"> Office Status : </td>
<td align="center" class="tdText"><%=request.getParameter("off_status")%></td>
</tr>
<tr bgcolor="skyblue" align="center">
	<td bgcolor="#b3ecff" colspan="6" align="center" class="tdText"><b><font color="#7610ba"> ** CR - Created ,  CL - Closed ,  CN / NC -  Converted  , AT  -  Attached  ,  RD  - Redeployed </font></b></td>
</tr>
</table>
 <br>
 
 <table align="center"  cellpadding="2" border="2" width="90%">
<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Shifting of Beneficiaries to New Office w.e.f month and year</b></td>
</tr>
<tr class="tdText" align="center">
<td colspan="2" align="center" class="tdText"> Enter the Month  : </td>
<td  align="center" class="tdText"><input type="text" id="month" name="month" size="2" maxlength="2" onblur="check_month()" onkeypress = "return AllowNumbersOnly(event)"  ></td>
<td colspan="2" align="center" class="tdText"> Enter the Year  : </td>
<td  align="center" class="tdText"><input type="text" id="Year" name="Year" size="4"  maxlength="4"  onkeypress = "return AllowNumbersOnly(event)"   onblur="getmonth(),getmon(),getBen5(),yearValidation(),getBen1(),getBen2(),getBen3(),getBen4(),getBen6(),getBen7()"></td>
<td colspan="2" align="center" class="tdText"> Entered Month and Year  : </td>
<td  align="center" class="tdText"> <b id="mon"></b></td>
</tr>
<tr bgcolor="#b3ecff">
<td colspan="10" align="center" class="tdText">
<font color="#7610ba">
Pls. Note:<br></font>
<font color="#7610ba">1. Demand Notice,Ledger Posting should have been completed <br>
2.Soft Copies of Ledger Detail Report, OB Report, Beneficiary Master and Master Report should be maintained by division.
 <br>
3. Closing Balance of all beneficiaries should be available as on <input type="text" id="getmonth" size="14" readonly class="da" hidden="true" > before  shifting beneficiaries to New Office<br>
4. Schemes  shifting to New Office should have been completed <br>
5. Division - District Mapping should have been completed by Head office
</font>
</td>
</tr>
<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Verification Statistics of Beneficiary</b></td>
</tr>
<tr class="tdText" align="center">
<td align="center" class="tdText"> <b><font color="blue">Master Details :</font></b></td>
<td align="center" class="tdText"> Total Beneficiaries : </td>
<td align="center" class="tdText"> <b id="ben1"></b></td>
<td colspan="2" align="center" class="tdText"> Total Beneficiaries with Meter Details </td>
<td align="center" class="tdText"> <b id="ben6"></b> </td>
<td colspan="2" align="center" class="tdText"> Total Beneficiaries with Tariff Rate Entry </td>
<td align="center" class="tdText"> <b id="ben7"></b> </td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText"><b><font color="blue"> Transaction Entries as on <b id="get"></b></font></b> </td>
<td align="center" class="tdText"> Bills Generated : </td>
<td align="center" class="tdText"> <b id="Ben2"></b> </td>
<td align="center" class="tdText"> CB Generated : </td>
<td align="center" class="tdText"> <b id="ben3"></b> </td>
<td align="center" class="tdText"> OB Generated : </td>
<td align="center" class="tdText"> <b id="ben4"></b> </td>
<td align="center" class="tdText"> Ledger Posted : </td>
<td align="center" class="tdText"> <b id="Ben5"></b> </td>
</tr>
<tr class="tdText" align="center">
<td colspan="10" align="center" class="tdText">
<font color="#7610ba" >Click Suitable Option ( 1 , 2 , 3 , 4 , 5 , 6 , 7 )</font>
</td>
</tr>
<tr class="tdText" align="center">
<td colspan="10" align="center" class="tdText">
<button id="b4" onclick="check()" class="sa">1. Merge Sub Division Within Same Division</button>&nbsp;&nbsp;
<button id="b6" onclick="check()" class="sa">2. Merge Scheme Within Same Division</button>&nbsp;&nbsp;
<button id="b1" onclick="check()" class="sa">3. Merge Sub Division with New Division</button>&nbsp;&nbsp;
<button id="b2" onclick="check()" class="sa">4. Merge Scheme with New Division</button>&nbsp;&nbsp;
<button id="b5" onclick="check()" class="sa">5. Transfer To New Division</button>
<button id="b3" onclick="check()" class="sa">6. Based on Both Sub Division and Scheme</button>
<button id="b7" onclick="check()" class="sa">7. Merge Beneficiary After Office Transfer </button>
 <button onclick="goBack()" class="sa"><font color="blue" >Go Back</button>
  <button onclick="window.close();" class="sa"><font color="blue" >Exit</button><br>
 <p id="demo2"></p>
 
</td>
</tr>
</table>
<input type="text" id="off_name"  name="off_name" hidden  size="4" value="<%=request.getParameter("off_name")%>" > 
<input type="text" id="frm_off"  name="frm_off" size="4" hidden value="<%=request.getParameter("frm_off")%>"  >
<input type="text" id="Server_Year" name="Server_Year" size="4"  hidden>
<input type="text" id="Server_month" name="Server_month" size="4" hidden>
<input type="text" id="ben5" name="ben5" size="4" hidden >
<input type="text" id="ben2" name="ben2" size="4" hidden >

<input type="text" id="b1_hidden" name="b1_hidden" hidden >
<input type="text" id="b2_hidden" name="b2_hidden" hidden >


<div id="d4">

<table align="center"  cellpadding="2" border="2" width="90%">

<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Option 1 : Shift Beneficiaries Based on Sub Division</b>
	<button  onclick="location.reload();" class="sa">Exit</button>
	</td>
</tr>
<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b> <font color="#7610ba"> Enter New sub Division only if applicable</font></b></td>
</tr>
 
  
<br>
<table id="igrid5" align="center"  cellpadding="2" border="2" width="90%">
 <tr>
 <th>Closed Sub <br>Div office 
 </th>
 <th>Sub Div Office Name
 </th>
<th>Office <br> Status
</th>
<th>Enter New <br> Sub Div office
</th>
<th>New Sub Div Office_Name
</th>
<th>Office <br> Status
</th>
<th> Change Closed <br> Sub Div To <br> New Sub Div.
</th>
<th>Update <br> Status
</th>
</tr>
 <tbody id="tgrid5"></tbody>
 </table>
 
 </table>
 
  </div>


<div id="d6">

<table align="center"  cellpadding="2" border="2" width="90%">

<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Option 2 : Shift Beneficiaries Based on Scheme</b>
	<button  onclick="location.reload();" class="sa">Exit</button>
	</td>
</tr>
<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b><font color="#7610ba">Pls Note : Division - District Mapping should have been completed by Head office as Per Office Shifted Based on Scheme</font></b></td>
</tr>
 
  
<br>
<table id="igrid6" align="center"  cellpadding="2" border="2" width="90%" >
 <tr>
 <th>Scheme <br> SNo 
 </th>
 <th>Scheme Name
 </th>
<th>BENEFICIARY
</th>
<th>New Scheme <br> SNo
</th>
<th>New Scheme Name
</th>
<th>Shift <br> Beneficiaries
</th>
<th>Update <br> Status
</th>
</tr>
 <tbody id="tgrid6"></tbody>
 </table>
 <br>
 
 
 
 </table>
 
  </div>



 <div id="d1">
  <br>
 <table align="center"  cellpadding="2" border="2" width="90%">
 <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Option 3 : Shift Beneficiaries Based on Sub Division</b>
	<button  onclick="location.reload();" class="sa">Exit</button></td>
</tr>
 <tr bgcolor="skyblue"  align="center">
	<td colspan="10" align="center" class="tdText"><b> <font color="#7610ba"> Enter New sub Division only if applicable</font></b></td>
</tr>
   <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b><font color="#7610ba">NOTE : Please Finish Step1 And Go To Step2</font></b></td>
</tr>
 <tr bgcolor="skyblue" align="left">
	<td colspan="10" align="left" class="tdText"><b><font color="#7610ba">Step 1 :</font></b></td>
</tr>  
  <table align="center"  cellpadding="2" border="2" width="90%">
<tr>
<td><font color="blue">Enter Changed Office Id :  &nbsp;&nbsp;
 </font>
</td>
<td>
<input type="text" id="New_off_id" maxlength="4" name="New_off_id" size="4" onblur="getdata()" onkeypress = "return AllowNumbersOnly(event)" >
</td>
<td><font color="blue"> &nbsp; Office Name : &nbsp; </font>
</td>
<td>
<input type="text" id="New_off_name" name="New_off_name" readonly size="45">
</td>
<td><font color="blue"> &nbsp; Office Status : &nbsp; </font>
</td>
<td>
<input type="text" id="New_off_status" name="New_off_status" size="4" readonly size="45">
</td>

</tr>
</table>
<br>
<table id="igrid" align="center"  cellpadding="2" border="2" width="90%">
 <tr>
 <th>Closed Sub <br>Div office 
 </th>
 <th>Sub Div Office Name
 </th>
<th>Office <br> Status
</th>
<th>Enter Changed  <br> Sub Div Office <br>(New)
</th>
<th> Sub Div Name
</th>
<th>Office <br> Status
</th>
<th> Change Closed <br> Sub Div To <br> New Sub Div.
</th>
<th>Update <br> Status
</th>
</tr>
 <tbody id="tgrid"></tbody>
 </table>
 <table align="center"  cellpadding="2" border="2" width="90%">
 <tr bgcolor="skyblue" align="left">
	<td colspan="10" align="left" class="tdText"><b><font color="#7610ba">Step 2 :</font></b></td>
</tr>
   <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><button class="sa" onclick="proceed()">Proceed With Office Updation</button><b id="demo"></b></td>
	
</tr> 
</table>
 
 <br>
 <br>
 
 </table>
 
  </div>
 
  <div id="d2">
  <br>
  <table  align="center"  cellpadding="2" border="2" width="90%">
  

 <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Option 4 : Shift Beneficiaries Based on Scheme</b>
	<button  onclick="location.reload();" class="sa">Exit</button></td>
</tr>
  <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b><font color="#7610ba">Pls Note : Division - District Mapping should have been completed by Head office as Per Office Shifted Based on Scheme</font></b></td>
</tr>
 
  <tr class="tdText" align="center">
<td align="center" class="tdText"> From Office Id : </td>
<td align="center" class="tdText"><%=request.getParameter("frm_off")%></td>
<td align="center" class="tdText"> Office Name  : </td>
<td align="center" class="tdText"><%=request.getParameter("off_name")%></td>
<td align="center" class="tdText"> Office Status : </td>
<td align="center" class="tdText"><%=request.getParameter("off_status")%></td>
</tr>
 
<table id="igrid1" align="center"  cellpadding="2" border="2" width="90%" >
 <tr>
 <th>Scheme <br> SNo 
 </th>
 <th>Scheme Name
 </th>
<th>BENEFICIARY
</th>
<th>New office id
</th>
<th>New Office_Name
</th>
<th>Shift <br> Beneficiaries
</th>
<th>Update <br> Status
</th>
</tr>
 <tbody id="tgrid1"></tbody>
 </table>
 <br>
 
 </table>
 
  </div>
  
 <div id="d3">
 <br>
 
 <table align="center"  cellpadding="2" border="2" width="90%">
 <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Option 6 : Shift Beneficiaries Based on Both Sub Division and Scheme</b>
	<button  onclick="location.reload();" class="sa">Exit</button></td>
</tr>
  <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b><font color="#7610ba"> Procedure : 1. Use Option 3 to change Sub Division. <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
2. Use Option 4 to change Scheme.</font></font></b></td>
</tr>
  


</table>
 </div>
 
 
 <div id="d5">
 <br>
 <table align="center"  cellpadding="2" border="2" width="90%">
 <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Option 5 : Transfer To New Division</b>
	<button  onclick="location.reload();" class="sa">Exit</button></td>
</tr>
  <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b><font color="#7610ba">NOTE : Please Finish Step1 And Go To Step2</font></b></td>
</tr>
 <tr bgcolor="skyblue" align="left">
	<td colspan="10" align="left" class="tdText"><b><font color="#7610ba">Step 1 :</font></b></td>
</tr>  
   <tr>
 
  
  <table  align="center"  cellpadding="2" border="2" width="90%" >
<tr>
<td><font color="blue">Enter New Office Id :  &nbsp;&nbsp;
 </font>
</td>
<td>
<input type="text" id="New_off_id4" maxlength="4" name="New_off_id4" size="4" onblur="getdata4()" onkeypress = "return AllowNumbersOnly(event)" >
</td>
<td><font color="blue"> &nbsp; Office Name : &nbsp; </font>
</td>
<td>
<input type="text" id="New_off_name4" name="New_off_name4" readonly size="45">
</td>
<td><font color="blue"> &nbsp; Office Status : &nbsp; </font>
</td>
<td>
<input type="text" id="New_off_status4" name="New_off_status4" size="4" readonly size="45">
</td>
</tr>
</table>
  <br>
<table id="igrid4" align="center"  cellpadding="2" border="2" width="90%">
 <tr>
 <th>Scheme <br> SNo 
 </th>
 <th>Scheme Name
 </th>
<th>BENEFICIARY
</th>
<th>Enter Changed  <br> Sub Div Office <br>(New)
</th>
<th>Sub Div Name
</th>
<th>Office Status
</th>
<th>Shift <br> Beneficiaries
</th>
<th>Update <br> Status
</th>
</tr>
 <tbody id="tgrid4"></tbody>
 </table>
  
 </tr>
 <table align="center"  cellpadding="2" border="2" width="90%">
 <tr bgcolor="skyblue" align="left">
	<td colspan="10" align="left" class="tdText"><b><font color="#7610ba">Step 2 :</font></b></td>
</tr>
   <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><button class="sa" onclick="proceed1()">Proceed With Office Updation</button><b id="demo5"></b></td>
	
</tr> 
</table>


</table>

  </div>
 
  
 <div id="d7">
 <br>
 
 <table align="center"  cellpadding="2" border="2" width="90%">
 <tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><b>Option 7 : Merge Beneficiary After Office Transfer</b>
	<button  onclick="location.reload();" class="sa">Exit</button></td>
</tr>
 <tr class="tdText" align="center">
<td align="center" class="tdText" > Enter Beneficiary code (B1) : </td>
<td align="center" class="tdText" > <input type="text" size="4" maxlength="5" id="bencode1" name="bencode1" onblur="bencode1(),bensch1()"> </td>
<td align="center" class="tdText" > Enter Beneficiary code (B2) : </td>
<td align="center" class="tdText" > <input type="text" size="4"  maxlength="5" id="bencode2" name="bencode2" onblur="bencode2(),bensch2()" > </td>
</tr>
 <tr bgcolor="skyblue" align="center">
<td colspan="10" align="center" class="tdText"><b>Details of Beneficiary code <b id="code1"></b> (B1) :</b>
</td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText" colspan="2"><b><font color="blue"> Beneficiary Name (B1) :</font></b> </td>
<td align="center" class="tdText" colspan="2"><b id="ben_name1"></b>  </td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText"><b><font color="blue"> Current Office Name :</font></b> </td>
<td align="center" class="tdText"><b id="b1_office"></b>  </td>
<td  class="tdText"><b><font color="blue"> Previous Office Name :</font></b> </td>
<td align="center" class="tdText"><font color="red"> <b id="b1_old"></b> ( <b id="b1_old111"></b> ) </font> </td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText"><b><font color="blue"> Beneficiary Type :</font></b> </td>
<td align="center" class="tdText"><b id="b1_ben"></b> </td>
<td  class="tdText"><b><font color="blue"> Reference No :</font></b> </td>
<td align="center" class="tdText"><b id="b1_ref"> </b> </td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText" colspan="2"><b><font color="blue"> Scheme :</font></b> </td>
<td align="center" class="tdText" colspan="2"><b id="b1_scheme"></b>  </td>
</tr>

 <tr bgcolor="skyblue" align="center">
<td colspan="10" align="center" class="tdText"><b>Details of Beneficiary code <b id="code2"></b> (B2) :</b>
</td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText" colspan="2"><b><font color="blue"> Beneficiary Name (B2) :</font></b> </td>
<td align="center" class="tdText" colspan="2"><b id="ben_name2"></b>  </td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText"><b><font color="blue"> Current Office Name :</font></b> </td>
<td align="center" class="tdText"><b id="b2_office"></b>  </td>
<td  class="tdText"><b><font color="blue"> Previous Office Name :</font></b> </td>
<td align="center" class="tdText"><font color="red"><b id="b2_old"></b> ( <b id="b2_old111"></b> ) </font> </td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText"><b><font color="blue"> Beneficiary Type :</font></b> </td>
<td align="center" class="tdText"><b id="b2_ben"></b> </td>
<td  class="tdText"><b><font color="blue"> Reference No :</font></b> </td>
<td align="center" class="tdText"><b id="b2_ref"> </b>  </td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText" colspan="2"><b><font color="blue"> Scheme :</font></b> </td>
<td align="center" class="tdText" colspan="2"><b id="b2_scheme"></b>  </td>
</tr>
</table>
<table align="center"  cellpadding="2" border="2" width="90%">
<tr bgcolor="skyblue" align="center">
<td colspan="10" align="center" class="tdText"><b>Merge :</b>
</td>
</tr>
<tr class="tdText" align="center">
<td  class="tdText" ><b>Merge B1 with B2</b> </td>
<td  class="tdText" ><input type="radio" id="r1" name="Merge" value="B1" onclick="sel_b1()"></td>
<td  class="tdText" ><b><font color="red">(OR)</font></b> </td>
<td  class="tdText" ><b>Merge B2 with B1</b> </td>
<td  class="tdText" ><input type="radio" id="r2" name="Merge" value="B2" onclick="sel_b2()"></td>
</tr>


<tr bgcolor="#b3ecff">
<td colspan="10" align="center" class="tdText">
<font color="#7610ba">
Please Note:<br></font>
<font color="#7610ba">
<b id="dis"></b> Will Be Marked Cancelled and will not be in use in Future 

</font>
</td>
</tr>
<tr bgcolor="skyblue" align="center">
	<td colspan="10" align="center" class="tdText"><button class="sa" onclick="Ben_Merge()">Proceed With Merge</button></td>
	
</tr> 

</table>
 </div>
 
 
</body>
</html>
