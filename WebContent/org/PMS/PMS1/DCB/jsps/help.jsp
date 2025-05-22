<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"> 
			function page_size()
			{
				window.resizeTo( 800,700 )
			}
			
			</script>
 <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TWAD NEST || NIC HELP</title>
<style type="text/css"> 
ol li
{
	margin-left: 58px;
	padding-left: 20px;
}

UL.check
 {
  list-style-image: url(/LI-markers/checkmark.gif) 
  }


</style>
</head>
<body onload="page_size()">
<Br><BR><Br> 
<a name="ben"></a> 
 <pre><font  color="#800000" size="6">Beneficiary Master</font> <img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></pre>
   <ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
		<li>Beneficiary Type should be defined</li><br> 
		<li>Beneficiary Name * for local bodies from PMS Master</li><br>
		<li>Beneficiary Name  for Private to be added from DCB application</li><br>
		<li>Address 2 appearing in the demand notice should be entered   in Beneficiary type for local bodies</li><br>
		<li>address 2 for private to be entered while creating private Beneficiary</li><br>  
		<li>office address same as the billing address (can be changed)</li><br>
		<li>for each local body beneficiary type tariff rate as for board order is maintained </li><br>
		<li>if tariff rate  has Revised  the existing rate  status had to be  made  defunct through the edit option</li><br>
</ul>
<Br><BR><Br><BR>

<a name="benmet"></a> 
 <pre><font  color="#800000" size="6">Beneficiary Metre</font> <img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></pre>
   <ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
		<li>District and Block Combo  have used for filtration  </li><br> 
		<li>By Default Metre Location  for Village Panchayat is the Habitation Name.</li><br> 
		<li>The user cannot add the metre details for the same habitation or metre location<Br> , Scheme and Sub division</li><br> 
		<li>Multiplying factor cannot be zero and does not allow negative value,default value is one</li><br> 
		<li>Initial reading cannot be negative</li><br> 
		<li>"If the column,Whether Metre fixed is no,The following columns are disabled</li><br> 
 		<li>Whether Meter Working</li><br> 
 		<li>Meter Type</li><br> 
 		<li>Multiplying factor</li><br> 
 		<li>Meter initial reading </li><br> 
  		<li>If the columns,Whether Metre fixed and Whether Meter Working is yes,the<br>  user should enter the metre initial reading 
			The metre details cannot be deleted if metre_sno has been referred  for Pumping  Return  The metre details< <br>   
			Before saving the data ,it shows the alert message ""Add another metre reading for this Beneficiary <br> 
			with ok and cancel button If we press ok button we can add the details for same  <br> 
		<li>Beneficiary with different metre location and cancel button allows to enter new metre location details</li><br> 
</ul>

<Br><BR><Br><BR>
<a name="ob"></a>
  	 <pre> <font  color="#800000" size="6">Opening Balance</font>  <img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></pre>
  	    <ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
 	 		<li> Meter Must for scheme selection of Beneficiary</li><bR>
			<li> Entered Scheme of Beneficiary will show under the form</li><bR>
			<li> Interest entry for the scheme of Beneficiary after only DCB finish</li><bR>
			</ul>
			
<Br><BR><Br><BR>
<a name="prenry"></a> 
  	 <pre> <font  color="#800000" size="6">Pumping Return </font>  <img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></pre>
  	    <ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
 	 	<li>  1.Month and year  must be set in the month setting from.</li><br>
 	 	<li>  2.Beneficiary Type show based on who one had meter	</li><br>
 	 	<li>  3.Beneficiary Name List also based on  .Beneficiary Type</li><br>
		<li>  4.No of Units[KL] direct entry  when Meter Working  & Meter Available both No</li><br>
		<li>  5.No of Units[KL] direct entry  when Meter Working No & Meter Available yes</li><br>
 		<li>  6.child meter qty only possible for who had setting Is parent  ? Setting in meter</li><br>
		<li>  7.if pumping return freeze for the current month we can't allowed for entry</li><br>
		<li>  8.if meter not working or not available direct consumption   quantity   entered should be KL only </li><br>
		<li>  9.if parent meter reading applicable is "yes" through the meter   form.the child meter <Br> quantity[KL] will be enable which subtract from no of units </li><br>
		<li>  10.Opening Meter reading non Editable</li><br>
		<li>  11.Previous month closing reading will this month opening reading</li><br>
	</ul>
	
<Br><BR><Br><BR>
<a name="prenryed"></a>
  	 <pre><font  color="#800000" size="6">Pumping Return Edit</font> <img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></pre>
  			Pumping Return Edit is Possible in one of Following Cases
  		<ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
	  			<li>Journal Not Posted </li>
				<li>Water Charges Not Freezed</li>
				<li>Pumping Return Not Freezed</li>
				<li>Validation Status Should be in Created Status </li><Br>			 
		</ul>
		Edit Procedure
		  <ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
	   			<li> Go To Pumping Return  Menu Item  </li><br>
	   			<li> Go To Pumping Return  Validation Form </li><br>
	   			<li> Select Beneficiary   </li><br>
	   			<li> Meter Locations for the Selected Beneficiary Will be displayed </li><br>
	   			<li> Go To The meter Location which is required to be edited   <br>
	   			 if Validation Status is 'Validated' Press Revoke Button  <BR>
	   			 'Revoke status' will change to 'Created status'  
	   			 Now the meter location is editable for Selected Beneficiary. 		
 	 		  </li><br>	
 	 	</ul>
 	 	
<Br><BR><Br><BR> 
<a name="prval"></a>
	  <pre><font  color="#800000" size="6">Pumping Return Report - Validation</font><img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></pre> 
	 
	<ul type="square"  style="list-style-image:url('../../../../../images/help.png')"> 
		<li>Three type Filtration for Ben.Record <Br>
		 1. Sub Division 2,Beneficiary Type ,3 Select Beneficiary Name</li><br>
		<li>Validation Status ->current state of PR,</li><br>
		<li>If status CR->to be validate,if status -> validate  you can be revoke</li><br>
		<li>if status Freeze  process area blank with out any button</li><br>
		<li>you can delete the record when Validation Status is CR otherwise we cannot delete</li><br>
		</ul>
<Br><BR><Br><BR>		
<a name="fr"></a>
<pre><font  color="#800000" size="6">Monthly Pumping Return Freeze</font> <img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></pre>
<ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
		<li>Pumping Return Should be Freeze for a division only.</li> <BR> 
		<li>Pumping Return Should be Freeze if all Pumping Return entries of All beneficiaries is validated</li><br> 
		<li>Water charge calculation will be finished automatically when Pumping Return Freeze.</li>
</ul>

<Br><BR><Br><BR>
<a name="DCBSTMT7"></a>
  	 <pre><font  color="#800000" size="6">DCB STATEMENT</font> <img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></pre>
   <ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
   <pre>Input:</pre>
  	 	<li>Select the Report  Year</li>
		<li>Select the Report  Month</li>
		<li>Select the Report Type (Scheme Category-Beneficiary Type-Union Wise)</li>
		<li>Select the Scheme Category </li>
		<li>Select the Beneficiary Type</li>
	 
	<pre> Report Type And Description </pre>
	 <ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
		<li>General Abstract -> General Abstrct of All Division and All Scheme Category</li>
		<li>Scheme Abstract</li>
		<li>Consolidated Abstract</li>
		<li>Scheme-Beneficiary Type Abstract</li>
		<li>Scheme-Beneficiary Type</li>
		<li>District-Beneficiary Type</li>
		<li>Scheme Category-Beneficiary Type-Union Wise</li>
	 </ul> 
 </ul>
 	 
 <Br><BR><Br><BR>
  
<a name="DMDINS"></a>
  	 <pre><font  color="#800000" size="6">Bill Demand <img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></font></pre>
	<ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
		<li>Select the Bill Period </li>
	</ul>
	
<Br><BR><Br><BR>	
<a name="wcfr"></a>
  	 <pre><font  color="#800000" size="6">Water Charge Calculation Freeze<img  style="vertical-align: bottom;" src="../../../../../images/help2.jpg"  height="50px"/></font></pre>
	<ul type="square"  style="list-style-image:url('../../../../../images/help.png')">
		<li>Scheme Type wise / Scheme wise water charge calculated <Br> can be view and freeze for a division </li><BR>
	</ul>
	

 
</body>
</html>