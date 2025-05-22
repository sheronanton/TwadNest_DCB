 <%@ page import="java.sql.*,java.util.ResourceBundle"%>
 <%@ page session="false" contentType="text/html;charset=windows-1252"%>
  <%@page import="java.util.Calendar" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@ page import="Servlets.Security.classes.UserProfile"%>
 <html>
  <head>
     <%
     	/* 
     	Coe 	Date 	    Change by       Recommand		Description 
     	#M1	  16/11/2011  PS				Dhavamani       Sub div total of PR 
     	*/ 
      %>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pumping Return Validation   | TWAD Nest - Phase II  </title>
     	<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
      <script type="text/javascript" src="../scripts/cellcreate.js"></script>
      <script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
      <script type="text/javascript" src="../scripts/counting.js"></script>
      <script type="text/javascript" src="../scripts/msg.js"></script>
       <script type="text/javascript" src="../scripts/dcbvalidation.js"></script> 
         
   <script type="text/javascript">
   function rld1(process,ben_value,ben_type)
   {

   } 
   </script>
    </head>
     <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
          
   <%
    String Year_text="",month_text="";
    Calendar cal = Calendar.getInstance();
   int day = cal.get(Calendar.DATE);
   int month = 0;//cal.get(Calendar.MONTH) + 1;
   int year = 0;//cal.get(Calendar.YEAR);

   
   String userid="0",Office_id="",Office_Name="",OFFICE_LEVEL_ID="0";
   Controller obj=new Controller();
   Connection con;
   try
   {
   	con=obj.con();
   	obj.createStatement(con);
   	
   	try
   	{
   		HttpSession session=request.getSession(true);
   	   userid=(String)session.getAttribute("UserId");
   	}catch(Exception e) {userid="0";}
   	if(userid==null)
   	{
   		//response.sendRedirect(request.getContextPath()+"/index.jsp");
   	}
	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

//   	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
   	if (Office_id.equals("")) Office_id="0";
   	Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
   	
   	
   	 String smonth=obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id),1);
	 String syear=obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id),1);
	
	if (smonth.equalsIgnoreCase("0"))
	 month=month;
	else
	 month=Integer.parseInt(smonth);
	 
	 if (syear.equalsIgnoreCase("0"))
	  year= year;
	else
	 year=Integer.parseInt(syear);
	 String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
	 
    	  month_text=cmonth[month];
      Year_text=Integer.toString(year);
   	
   	obj.conClose(con);
   }catch(Exception e) {
   	
    
   }	
   
   int col=0;
   
    OFFICE_LEVEL_ID=obj.getValue("com_mst_offices", "OFFICE_LEVEL_ID","where OFFICE_ID="+Office_id+ "  ");
	String division_list="";
	try
	{
		
	//	 division_list=obj.combo_str("com_mst_offices","office_name","office_id","where   office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map)  ","div","class='select' onchange=focusloss()");

		 division_list=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where   region_office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","div","class='select' onchange=select_ben('0','subdiv','5')");
		 
	}catch(Exception e) 
	{
		out.println(e);
	}
   
   %>
   <script type="text/javascript">
   
   
   function deselect(a)
   {
   	document.getElementById(a).options[0].selected = true;
   }
   </script>
    <body  onload="select_ben('0','bentype','3'),select_ben('0','subdiv','5'),flash(),startclock(),select_ben('0','sch_load','6')" >
     <form name="billdemand" >
      <table  style="BORDER-COLLAPSE: collapse" border="0" borderColor="#92c2d8" id="data_table" width=100% align=center    cellspacing="0" cellpadding="5">
       	<tr>
          <th align="center"   colspan="4">  
              <b>Pumping Return Validation - <%=Office_Name%> </b>   
          </th>
        </tr>
      	<%
		if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
		{
		%> 
		<tr>
	 	 <td align=left valign="top" width=10% ><font class="tdText">  Division</font> </td>
	 	 <td>
	 	&nbsp;:&nbsp;<%=division_list%>
	 	</td>
	 	</tr>
	 	<%}%>
      
       
         <tr><td align=left valign="top" width=20% >Sub Division Name </td> 
			 <td width=25% align=left     >:&nbsp;<select class='select'  id="subdiv" name="subdiv" style="width: 50ex;" onchange="sub_div_wise_pr_total_req(1);deselect('sch_load');" ></select><input type="button" onclick="data_show('show',15,0);" value="Click"></td>
		 	 <td align=left valign="top" width=10%   > <font color =red><B>[OR]</B></font>&nbsp;&nbsp;Scheme Name </td>
			 <td width=15% align=left>
			 <select class='select'  style="width: 50ex;"  id="sch_load" name="sch_load" onchange="sub_div_wise_pr_total_req(2);deselect('subdiv');data_show('show',15,0)" ></select></td>
		</tr>  
       <tr class="">
			<td width=15%><font class="tdText"> Beneficiary Type</font> </td>
			<td  width=15% align=left colspan="2">: <select  id="bentype" name="bentype" onchange="select_ben(this.value,'ben_sel',4),data_show('show',15,0)" class="select">  </select></td><td    ><label id='data_show'></label></td>
			 
       </tr>
       
       <Tr><td width=10% ><font class="tdText">Beneficiary Name </td><td colspan=2>: <select id="ben_sel"  name="ben_sel" onchange="data_show('show',15,this.value)"  class="select"  ><option value="0">--Select Beneficiary--</option> </select></td></Tr>
       
       <input type=hidden id="subdiv"> 
      
       
          
        <tr>
       
         
       <td><font class="tdText"><b>Pumping Return Month and Year </b></font></td>
       <td colspan=5>
       <%=month_text%> /<%=Year_text%>:<label id="time" class="tb4"></label>   <font size="2" color="red">&nbsp;<label  id="msg"></label></font>
       </td>
       
       </tr>
       <tr>
     
      </table>
      
      <table  class="fb2" id="data_table" style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8" width="100%" align=center border=1  cellspacing="0" cellpadding="3">
      		<tr  >
      		    <th width=2% align="center" valign="top"><font size="1">Sl<br>No</font></th>
      		    <th align="center" valign="top" width="15%"> <font class="tdText"><b>Beneficiary Name</b></font></th> 
       			<th align="center"  valign="top" width="15%"> <font class="tdText"><b>Meter Location</b></font></th>
       			<th align="center" valign="top" width="15%" > <font class="tdText"><b>Scheme Name</b></font></th>
       			<th align="center" valign="top" width="5%"><font class="tdText"><b>Meter Fixed</b></font></th>
       			<th align="center" valign="top"  width="5%"><font class="tdText"><b>Meter Working</b></font></th>
       			<th align="center" valign="top"  width="5%"><font class="tdText"><b>Opening Reading<br> </b></font></th>    
     			<th align="center" width=5% valign="top"> <font class="tdText"><b>Closing Reading<br> </b></font></th>
     		<!-- 	<td align="center" valign="top"><font class="tdText"><b>Difference<br>[KL] </b></font></td> -->  
     			     				 	
     			<th align="center" valign="top"  width="5%"><font class="tdText"><b>Child Meter Qty[KL]<br></b></font></th>
     			<th align="center" valign="top" width="5%"><font class="tdText"><b>No of<Br>Units<br>[KL]<br></b></font></th>			 
     			<th align="center" valign="top"  width="5%"><font class="tdText"><b>Multi Factor<br></b></font></th>
      			<th align="center" valign="top"  width="5%"><font class="tdText"><b>Total Consumption<br>(KL)<br> </b></font></th>
      			<th align="center" valign="top"  width="5%"><font class="tdText"><b>Validation <br>Status</b></font></th>
      			<th align="center" valign="top"  width="5%"><font class="tdText"><b>Process </b></font></th>
      			<th align="center" valign="top"  width="5%"><font class="tdText"><b>&nbsp;</b></font></th>
      		</tr>
      		<tr  bgcolor="#C6DEFF">
      		    <td width=2% align="center" valign="top" colspan="6"><font class="tdText"><b></b></font></td>
      		     
       			
       			<td align="center" valign="top" > <font class="tdText"><b>A</b></font></td>
       			<td align="center" valign="top"><font class="tdText"><b>B</b></font></td>
       			<td align="center" valign="top"><font class="tdText"><b>C</b></font></td>
       			<td align="center" valign="top"><font class="tdText"><font class="tdText"><b>D= B-A</b></font></font></td>    
     			<td align="center" width=5% valign="top"> <font class="tdText"><b>E</b></font></td>
     			<td align="center" valign="top"><font class="tdText"><b><font class="tdText"><b>F=D*E</b></font></b></font></td>  
     			     				 	
     			<td align="center" valign="top"><font class="tdText"><font class="tdText"><b></b></font></font></td>
     			<td align="center" valign="top"> </td>			 
     			<td align="center" valign="top" class="tdText"> </td>
     			<td align="center" valign="top" class="tdText">&nbsp; </td>
          		</tr>
          		<tr>     			   
     			<Td colspan="15" align="center">
     			<div id='scroll_clipper' style='position:relative; width:100%; border-height:1px; height: 350px; overflow:auto;white-space:nowrap;'>
				<div id='scroll_text'  >
				 <table width="100%" border=1 style="border-collapse: collapse;" cellpadding="0" cellspacing="0" bordercolor="skyblue">
      	 			<tbody id="data_tbody"  ></tbody>
      	 		</table>
      	 		</div>
      	 		</div>
      	 		</Td>
      	 		</tr>  	 		
      	 		
    		 
       <tr>
       <th colspan=15 align=center>&nbsp;&nbsp;<input type=button value="Validate All " onclick="data_show('schemewisevalidate',0,0)" class="fb2"></input>&nbsp;&nbsp;<input type="button" value="Revoke All " onclick="data_show('schemewiserevoke',0,0)" class="fb2"/>&nbsp;&nbsp;<input type=button class="fb2" value="Refresh" onclick="javascript:window.location.reload();"> <img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=23','mywindow','scrollbars=yes')"><input type="button" value="Exit" onclick="window.close()" class="fb2"></th>
       	</tr>
      </table>   
          
          <input type="hidden" id="useform" name="useform" value=4 ></input>
       	  <input type="hidden" id="fyear" name="fyear" value="<%=year%>" ></input> 
       	  <input type="hidden" id="fmonth" name="fmonth" value='<%=month%>' ></input>
       	   <input type="hidden" id="officeid" name="officeid" value='<%=Office_id%>' ></input>  
      
     <input type=hidden id="pr_status" name="pr_status" value="0"> 
     <input type="hidden" id="formflag" value="2"/>      
 	  <input type="hidden" id="t1" name="t1" ></input>  
 	  </form>
 	  		  </body>
 	  		 
 	  		  </html>
      