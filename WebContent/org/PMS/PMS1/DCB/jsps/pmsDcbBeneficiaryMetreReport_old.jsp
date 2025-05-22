<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%!	
	String beneficiarySno="";
	String SchemeSno="";
%>
<% 

    
    String beneficiarySno=request.getParameter("BeneficiarySno");
	String SchemeSno=request.getParameter("SchemeSno");
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Beneficiary Scheme Report | TWAD Nest - Phase II</title>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
    <script type="text/javascript" src="../scripts/pmsDcbBeneficiaryMetreReport.js"></script>
    <style type="text/css">
					
					table.border1
					{
					color:#000000;
					padding:0px;
					border-top: 1px solid #dddddd;
					border-left: 1px solid #dddddd;
					border-bottom: 0px solid #dddddd;
					border-right: 0px solid #dddddd;
					font-size: 14  px;
					
					}
					
					table.border1 th, table.border1 td 
					{
					padding:5px;
					padding-bottom:2px;
					border-top: 0px solid #dddddd;
					border-left: 0px solid #dddddd;
					border-bottom: 1px solid #dddddd;
					border-right: 1px solid #dddddd;
					}
										
				</style>
  </head>
  				        <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
  
  <body onload="divisionname();getSchBenName();loadReport();">
  <form name="beneficaryMeterReport" action="">
    <table  class="fb2" border="1" width="100%" align="center" cellpadding="0" cellspacing="0"  style="BORDER-COLLAPSE: collapse"  borderColor="#92c2d8">
    	<tr  class="tdH" align="center" >
			<td colspan="6">
				<div align="center">
					<font size=3>  Beneficiary Meter Report </font>
					<label id="divisionname"></label>
				</div>
			</td>
      	</tr>
        <tr>
            <td  class="tdText">
                 Beneficiary Name:
           </td>
           <td style="padding-left:50px;" class="tdText">
           		<label id="ben"></label> 
           		<input type="hidden" name="beneficiarySno" id="beneficiarySno" value=<%=beneficiarySno%>>
           </td>
        </tr>
        <tr>
	        <td  class="tdText">  
	              Scheme Name:
	        </td>
	        <td style="padding-left:50px;" class="tdText">
	               <label id="sch"></label>
	               <input type="hidden" name="schemeSno" id="schemeSno" value=<%=SchemeSno%>>
	        </td>
        </tr>
        
    </table>
    <br>
    
    <table  class="fb2" id="existing" border="1" width="100%" align="center"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
    	<tr  class="tdH">
        	<th class="tdText">Sno</th>	
        	<th class="tdText">Meter Location</th>
            <th class="tdText">Whether Meter<br>fixed?</th>
            <th class="tdText">Whether Meter<br>Working?</th>
            <th class="tdText">Meter Type</th>
            <th class="tdText">Multiplying<br>factor</th>
            <th class="tdText">Meter initial<br> reading</th>
            <th class="tdText">Parent Meter</th>	
            <th class="tdText">Minimum<br>Billing<br>Yes/No<br>(Qty in KL)</th>
            <th class="tdText">Allotted<br>Quantity<br>Yes/No<br>(Qty in KL)</th>
            <th class="tdText">Tariff Rate Details</th>
        </tr>
        <tbody id="getvaluerows" class="tdText">
        </tbody>
   </table>
       		<br>
            <table align="center"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
            <tr>
           		<td>
             		<input type="button" name="cmdexit" value="Back" id="cmdlist" onclick="javascript:history.go(-1)" class="fb2"/><input type="button" name="cmdexit" value="Exit" id="cmdlist" onclick="exitwindow();" class="fb2"/>
            	<td>
             </tr>
        </table>
 </form>
 </body>
</html>