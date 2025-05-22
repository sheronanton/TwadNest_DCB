 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <%@ page session="false" contentType="text/html;charset=windows-1252"%>
 <html>
  <head>
     <script type="text/javascript" src="../scripts/msg.js"></script>
     
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pumping Return Report | TWAD Nest - Phase II   </title>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
       <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
   <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
   <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
    </head>
   <%
   String month_text=request.getParameter("mv");
   String Year_text=request.getParameter("yv");
   String Ben_type=request.getParameter("ben_type");
   String ben_value=request.getParameter("ben_value");
   int col=0;
   %>
    <body  onload="data_show('show',4,<%=ben_value%>),flash()"  >
     <form name="billdemand" >
      <table class="fb2"  style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" id="data_table" width=100% align=center border=1  cellspacing="0" cellpadding="5">
        <tr >
          <td align="center" class="tdH" colspan="3">
             
              <b>Pumping Return Report </b>   
             
          </td>
        </tr>
        
       <tr class="tdHeader">
       <td width=25%> <font size="2"><b>Beneficiary Name</b></font></td>
       <td colspan="2">
       <label id="ben_name"  class="tdText"></label>
       </td>
       </tr>
        <tr class="tdHeader">
       <td width=25%><font size="2"><b> Beneficiary Type</b></font></td>
       <td colspan="2">
       <label id="bentypevalue"  class="tdText"></label>
       </td>
       </tr>
      <!--
      
        <tr>
       <td class="tdHeader"> Alloated qty</b></font></td>
       <td>
      <label  id="Alloated"  class="tdText"></label></font>
       </td>
       </tr>
        <tr class="tdHeader">
       <td>Tariff Mode</b></font></td>
       <td>
        <label  id="traiff" class="tdText"></label> 
       </td>
       </tr>
       -->
        <tr class="tdHeader">
       <td > <font size="2"><b> Month&Year</b></font></td>
       <td>
       <%=month_text%> /<%=Year_text%>   <font size="2" color="red">&nbsp;<label  id="msg"></label></font>
       </td>
       </tr>
      <tr><td colspan="4"><b><u>Pumping Details </u></b></td></tr>
      </table>
      
      <table class="fb2"  id="data_table" width=100% align=center border=1  cellspacing="0" cellpadding="3">
      		<tr class="tdHeader">
      		    <td align="center">Sl.No</td>
       			<td align="center"> Meter Location</td>
       			<td align="center"> Scheme Name</td>
       			<td align="center">Meter Fixed</td>
       			<td align="center">Meter Working</td>
     			<td align="center">Opening
Reading</td>
     			
     			
     			<td align="center">Closing
Reading</td>
      			
      			<td align="center">Child  Meter Qty(KL)</td>
      			<td align="center">No of Units(KL)</td>
      			<td align="center">Multi Factor</td>
      			<% if (Integer.parseInt(Ben_type)>6) {col=14;			
      			%>      			
      			
      			<%} else{
      					col=7; 
      					}
      			 %>
      			<td align="center">Total Consumption(KL)</td>
      			     			
      			
      			
      		</tr>
      	 	<tbody id="data_tbody"  ></tbody>
    		  
       <tr  class="tdH" >
       <td colspan=15 align=center><input class="fb2" type=button value="Print" onclick="pumping_pdf_show('1','pdf')"></input><input type=button value="Exit" onclick="window.close()" class="fb2" ></input></td>
       	</tr>
      </table>
           <input type="hidden" id="year" value="<%=request.getParameter("year_value")%>" ></input> 
       	   <input type="hidden" id="month" value="<%=request.getParameter("month_value")%>"  ></input> 
       	   <input type="hidden" id="bensno" value="<%=request.getParameter("ben_value")%>" ></input> 
       	  
       	  <input type="hidden" id="fyear" name="fyear" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" ></input> 
      
    <input type=hidden id="pr_status" name="pr_status" value="0"> 
             <input type="hidden" id="formflag" value="1"/>      
           
 	  <input type="hidden" id="t1" name="t1" ></input> 
 	   <input type="hidden" id="spl" name="spl" value=1></input> 
 	  </form>
 	  		  </body>
 	  		 
 	  		  </html>
      