<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="Servlets.Security.classes.UserProfile"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <%@page import="java.util.Calendar" %>
 <%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %>
 <%@page import="java.sql.*,java.util.ResourceBundle"%>
 <%
 		String userid="0",Office_id="",Office_Name="",OFFICE_LEVEL_ID="0";
 		Calendar cal = Calendar.getInstance();
 		int day = cal.get(Calendar.DATE);
 		int month = cal.get(Calendar.MONTH) + 1;
 		int year = cal.get(Calendar.YEAR);
 		int yearmonthenable=2;
 		Controller obj=new Controller();
  		Connection con;
		try
		{
				con=obj.con();
				obj.createStatement(con);	 
				userid=(String)session.getAttribute("UserId");
				if(userid==null)
				{
	 				response.sendRedirect(request.getContextPath()+"/index.jsp");
				}

		 	//	  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;
		 			Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;


		//		Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
    			if (Office_id.equals("")) Office_id="0";
				Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
				obj.conClose(con);
		}catch(Exception e) 
		{	 
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}  
	int prv_month=obj.prv_month(year,month);
	int	prv_year=obj.prv_year(year,month);
	OFFICE_LEVEL_ID=obj.getValue("com_mst_all_offices_view", "OFFICE_LEVEL_ID","where OFFICE_ID="+Office_id+ "  ");
	String division_list="";
	try
	{
	//division_list=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where   region_office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","Child_div","class='select' onchange=focusloss()");
		 division_list=obj.combo_str("com_mst_offices","office_name","office_id","where   office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map)  ","div","class='select' onchange=focusloss()");

	}catch(Exception e) 
	{
	out.println(e);
	}				 
%>
	<script language="javascript" type='text/javascript'>
   	 var obPage = true;

	
		
		function focusloss()
		{	 document.getElementById("bentype").selectedIndex = "0";
		} 
		function vd()
		{
			window.open("../../../../../video_tut?file=opening_balance_edit","100","100")  
		}
		function setEnable()
		{
							var set = document.getElementById("monthyearenable").value;
							if (set=="1")
							{
							document.getElementById("month").disabled = true;
							document.getElementById("year").disabled = true;
							}
							else
							{
							document.getElementById("month").disabled = false;
							document.getElementById("year").disabled = false;
							}
		}
		function print(a)
		{
						if (a=='dcb')	
							{
								 var month=document.getElementById('month').value;
								 var year=document.getElementById('year').value;
								 var ben=document.getElementById('ben_value').value;
								 url="../../../../../OpeningBalanceReport?command=Report&flag=DCB&option=1&month="+month+"&year="+year+"&ben="+ben;
//alert(url);
								     var winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
							    // winemp.moveTo(250,250); 
							     //winemp.focus();
							 }
							 else  
							 {
								 var month=document.getElementById('month').value;
								 var year=document.getElementById('year').value;
								 var ben=document.getElementById('ben_value').value;
								 url="../../../../../OpeningBalanceReport?command=Report&option=1&flag=Int&month="+month+"&year="+year+"&ben="+ben;
								 winemp= window.open(url,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
								// winemp.moveTo(250,250); 
								 //winemp.focus();
							 } 
				}
				 
				function mthch(a)
				{

					
					
					var amonth =new Array("-select month-","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec");	
					var month=a.value;
					if (month!=4)
					{
						if (parseInt(month)!=0)
						{	
							
									var year=document.getElementById("year").value;
									var pm=0;
									var yr=0;
									yr=year;
				
									
									if (parseInt(month)==1)
									{
									pm=12;
									yr=yr-1;
									}
									else
									{
									pm=parseInt(month)-1;
									}
				
									 
									try
									{ 
											document.getElementById("cm").innerHTML=amonth[month]+" "+year;
									}catch(e) {}
									try
									{ 
											document.getElementById("pmnt").innerHTML=amonth[pm]+" "+yr;
									}catch(e) {}
									try
									{ 
								 			document.getElementById("pmnt1").innerHTML=amonth[pm]+" "+yr;
									}catch(e) {} 
								 	try
									{ 
								 				document.getElementById("pmnt2").innerHTML=amonth[pm]+" "+yr;
									}catch(e) {}
	
						}
					} 
				}
				
				function ckset(r)
				{    
					vs();// enable the dcb button
					try 
					{
						dcbshow(document.getElementById('selprocess').value);
					}catch(e) {}
					var label=document.getElementById("ben_value").value;	 
				 	var row=document.getElementById("rowcnt").value; 			 
				}
				function value_set(row)
				{
					document.getElementById("BENEFICIARY_SNO").value=document.getElementById("select"+row).value;
				}
				function sh()
				{
					document.getElementById('ent_div').style.visibility = 'visible';
				}
				
				function dcbshow(process)
				{
					 
					document.getElementById('ent_div').style.visibility = 'visible';
					 
					if (process=="1")
					{
					//Additions if any upto previous month for the current fin year
					var dcb="<table   id='ben_entred_data' width='100%' align='center'   cellspacing='0' cellpadding='1'  style='BORDER-COLLAPSE: collapse' border='0' >";
					dcb+="<tbody id='ben_entred_body' align='left'>";
					dcb+="<tr class='tdH' ><td  align='center' width='50%'   colspan='3'><font size=3 color='blue'>DCB</font>&nbsp;(<font size=2> Value in Rupees )</td>"; 	
				  	//dcb+="<tr><td width='10%' align='left'  ><font size=2  color=red ><b>Opening Balance  as on 1st April 2012</b> </font></td></tr>";
				  	dcb+="<tr><td width='10%' align='left'  ><font size=2  color=red ><b>Opening Balance</b> </font></td></tr>";  
				  	dcb+="<tr><td><font  class=tdText >&nbsp;&nbsp;Yester year Water Charge <font size=3  color=blue > ( as on 01-04-2004 )</font></font></td><td><input type=text class='tb5' id='OB_YESTER_YR_WC' name='OB_YESTER_YR_WC' onKeyUp=isInteger(this,9,event)></input></td> </tr>";
					dcb+="<tr><td><font class=tdText  >&nbsp;&nbsp;Water Charges Current Year <font size=3  color=red >( as on 01-04-<span name='cyr'></span> )</font> </font></td><td><input type=text id='OB_CUR_YR_WC' name='OB_CUR_YR_WC' class='tb5'  onKeyUp=isInteger(this,9,event)></input></td> </tr>";
					dcb+="<tr><td  ><font class=tdText  >&nbsp;&nbsp;Maintenance Charges &nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;<font color='red'>*&nbsp;</font>Water Charges Prior to 1997 </font></td>" 
					   + "<td  ><input class='tb5' type=text id='OB_MAINT_CHARGES' name='OB_MAINT_CHARGES' onKeyUp=isInteger(this,9,event)></input></td> </tr> "
					   + "<tr><td class=tdText ><font color='red'>*&nbsp;</font> Applicable only for 3TWAD Beneficiary</td></tr> ";
					dcb+="<input type=hidden class='tb5' id='OB_FOR_MTH_YESTER_YR_WC' name='OB_FOR_MTH_YESTER_YR_WC' onKeyUp=isInteger(this,9,event) value=0></input>"
					dcb+="<input type=hidden id='OB_FOR_MTH_CUR_YR_WC' name='OB_FOR_MTH_CUR_YR_WC' class='tb5'  onKeyUp=isInteger(this,9,event)  value=0></input>"
					dcb+=" <input class='tb5' type=hidden id='OB_FOR_MTH_MAINT_CHARGES' name='OB_FOR_MTH_MAINT_CHARGES' onKeyUp=isInteger(this,9,event)  value=0></input>"
				  	dcb+="<tr><td colspan='2'><br/></td></tr>";  
				  	dcb+="<tr><td width='50%' align='left' colspan=2><font size=2   ><b>Demand Upto the previous month(  <span name='process_year' id='process_year'></span>   <label id='pmnt'></label>) </b></font></td></tr>";
				   	dcb+="<tr><td><font  class=tdText >&nbsp;&nbsp;Water Charges Current Year Demand     </font></td><td><input class='tb5' type=text id='DMD_UPTO_PRV_MTH_WC' name='DMD_UPTO_PRV_MTH_WC' onKeyUp=isInteger(this,9,event)></input></td></tr>";
				  	dcb+="<tr><td colspan='2'><br/></td></tr>";  
					dcb+="<tr><td width='50%' align='left' colspan=2><font size=2   ><b>Collection Upto the previous month (  <span name='process_year1' id='process_year1'></span>  <label id='pmnt1'></label>) </b></font></td></tr>";
				 	dcb+="<tr><td <font  class=tdText >&nbsp;&nbsp;On Yester Year WC Demand   </td><td><input type=text id='COLN_UPTO_PRV_MTH_YESTER_YR' name='COLN_UPTO_PRV_MTH_YESTER_YR' class='tb5'  onKeyUp=isInteger(this,9,event)></input></td></tr>";
					dcb+="<tr><td><font  class=tdText >&nbsp;&nbsp; On Current Year WC Demand   </font></td><td><input type=text id='COLN_UPTO_PRV_MTH_WC' name='COLN_UPTO_PRV_MTH_WC' class='tb5'  onKeyUp=isInteger(this,9,event)></input></td></tr>";
					dcb+="<tr><td width='50%'><font  class=tdText >&nbsp;&nbsp;Maintenance Charges</font>  </td><td><input class='tb5' type=text id='COLN_UPTO_PRV_MTH_MAINT' name='COLN_UPTO_PRV_MTH_MAINT' onKeyUp=isInteger(this,9,event)></input></td></tr>";
					dcb+="<tr><td colspan='2'><br/></td></tr>";
					dcb+= "<tr><td align='center' colspan=2><font size=2 ><input type=button value=Submit id='button1'  class='fb2'  onclick=data_show_dcb('add',4,'0');></input><input  class='fb2' type=button value=Update  disabled='disabled' onclick=data_show_dcb('add',10,'0') id='button2'  ></input><!--input class='fb2' type=button value=Interest onclick=select(CUR_SNO); id='butInt' disabled=true></input--><input type=button value='Print Opening Balance' id='button1'  class='fb2'  onclick=print('dcb')></font><input type='button' class='fb2' value='Refresh' onclick='javascript:window.location.reload();'><input class='fb2' type='button' value='Exit' onclick='self.close()'></input></td></tr>";
				 	document.getElementById('ent_div').innerHTML=dcb;								 								
					}	
					else
					{  
					
					  var amonth =new Array("-select month-","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec");	
					  var year=document.getElementById("year").value;
					  var month=document.getElementById("month").value;
					  var pm=0;  
					  var cm="";
					  var pmnt=0;
					  var pmnt1=0;
					  var pmnt2=0;
									var yr=0;
									yr=year;
									if (parseInt(month)==1)
									{
									pm=12;
									yr=yr-1;
									}
									else
									{
									pm=parseInt(month)-1;
									
									}
									try
									{ 
									cm=amonth[month]+" "+year;
									}catch(e) {}
									try
									{ 
									pmnt=amonth[pm]+" "+yr;
									}catch(e) {}
									try
									{ 
									 pmnt1=amonth[pm]+" "+yr;
									}catch(e) {} 
									 try
									{ 
									 pmnt2=amonth[pm]+" "+yr;
									}catch(e) {} 
									try
									{ 
										cm=amonth[month]+" "+year;
									}catch(e) {}
									
									if (parseInt(month)==4)
										pmnt2="";
									
						document.getElementById('selprocess').value=2;
						var intr="<table   id='ben_entred_data' width='100%' align='center' border='0' cellspacing='0' cellpadding='1'  style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>";
						intr+="<tbody id='ben_entred_body' align='left' >";
						intr+="<tr class='tdH'><td   align='center' colspan=2  ><font size=3 color='blue'>INTEREST</font>&nbsp;( <font size=2>Value in Rupees ) </font></td></tr>";
				 		intr+="<tr><td   align='left'   ><font size=2 ><b><font color=red>Interest as on 1st April <span name='cyr'>of the current year </font> </span></b> </font></td></tr>";
						intr+="<tr><td><font  class=tdText >&nbsp;&nbsp; Maintenance Charges Principle  <input type=hidden name='pyr'>  </font></td><td><input  class='tb5' type=text id='OB_INT_PRV_YR_MAINT' name='OB_INT_PRV_YR_MAINT' onKeyUp=isInteger(this,9,event)></input></td></tr>";
						intr+="<tr><td><font  class=tdText >&nbsp;&nbsp; Maintenance Charges Interest (01-04-<span name='pyr'>Previous Year</span> to 31-03-<span name='cyr'>Current Year</span>)</font></td><td><input class='tb5' type=text id='OB_INT_CUR_YR_MAINT' name='OB_INT_CUR_YR_MAINT' onKeyUp=isInteger(this,9,event)></input></td></tr>";
						intr+="<tr><td><font  class=tdText >&nbsp;&nbsp; Water Charges Interest</font></td><td><input  class='tb5' type=text name='OB_INT_AMT_WC' id='OB_INT_AMT_WC' onKeyUp=isInteger(this,9,event)></input></td></tr>";				
						intr+="<input class='tb5' value='0' type=hidden name='DMD_INT_UPTO_PRV_MTH_WC' id='DMD_INT_UPTO_PRV_MTH_WC' onKeyUp=isInteger(this,9,event)></input>";					  
				 		intr+="<tr><td   align='left'   ><font size=2 ><b>Water Charges Interest Upto Previous Month( <span name='process_year3' id='process_year3'></span>   "+pmnt2+") </font></td></tr>";	
				 		intr+="<tr><td><font  class=tdText >&nbsp;&nbsp; Water Charges Interest Upto Previous Month</font></td><td><input  class='tb5' type=text name='OB_FOR_MTH_INT_AMT_WC' id='OB_FOR_MTH_INT_AMT_WC' onKeyUp=isInteger(this,9,event)></input></td></tr>";
						intr+="<tr><td width='50%' align='left' colspan='3'><font size=2  ><b>Interest Collection Upto Previous month (  <span name='process_year4' id='process_year4'></span>   "+pmnt2+")</label>  </b></font></td></tr>";
						intr+="<tr><td> <font  class=tdText >&nbsp;&nbsp; Water charges Current Year Interest upto Previous Month </font></td><td><input  class='tb5' type=text name='COLN_INT_UPTO_PRV_MTH_WC' id='COLN_INT_UPTO_PRV_MTH_WC' onKeyUp=isInteger(this,9,event)></input></td></tr>";
						intr+="<tr><td width='50%'><font size=2  class=tdText >&nbsp;&nbsp; Maintenance Charges Interest upto <span name='pmnt'>Previous Month</span> </font>  </td><td><input  class='tb5' type=text name='COLN_INT_UPTO_PRV_MTH_MAINT' id='COLN_INT_UPTO_PRV_MTH_MAINT' onKeyUp=isInteger(this,9,event)></input></td></tr>";
						intr+="<tr><td colspan='2'><br/></td></tr>";
						intr+="<tr><td align='center' colspan=2><font size=2 ><input type=button value=Submit class='fb2' onclick=data_show_dcb('add',6,'0') id='button1'></input><input  class='fb2' type=button id='button2'  disabled='disabled' value='Update' onclick=data_show_dcb('add',11,'0') ></input></font><!--input type=button value='DCB' id='dcbbutton' onclick='dcbshow(1)' class='fb2'/--><input type=button value='Print Interest' id='button1'  class='fb2'  onclick=print('int')><input type='button' class='fb2' value='Refresh' onclick='javascript:window.location.reload();'><input class='fb2' type='button' value='Exit' onclick='self.close()'></input></td></tr>";
				 		document.getElementById('ent_div').innerHTML=intr;
						}
					    document.getElementById('ent_div').style.visibility = 'visible';
					    document.getElementById("button2").disabled = true;  
					    document.getElementById("button1").disabled = false;
					    onMonthChange();
					    onYearChange();
					   // month_check();
				}
				
				function vs()
				{
				 	//document.getElementById("dcbbutton").disabled =false;
				}
				function ben_filter()
				{
					     grid_show(3,'data','ben_data','ben_body' ,'bentype');
					     var select_black=document.getElementById("block_value").value;
					     document.getElementById("selblk").value=select_black;
				}
				 
				function c1()
				{
					document.getElementById("intid").style.color="red";
					setTimeout ("c2()", 1000 );
				}
				  
				function c2()
				{ 
					document.getElementById("intid").style.color="green";
					setTimeout ("c1()", 1000 );
				}
				function rld()
				{ 
					var mon=document.getElementById("year").value; 
					var smonth=document.getElementById("month").value;
					var res=month_year_check(smonth,mon);
					 month_check();
					if (res!=1)
					{  
						var res1=report_period_verify_2(document.getElementById("month"),document.getElementById("year"));
						if (res1!=1)
							onYearChange();
						
					}
					
				}
				function rld2()
				{ 
					var mon=document.getElementById("year").value; 
					var smonth=document.getElementById("month").value;
					var res=month_year_check(smonth,mon);
					month_check();
					if (res!=1)
					{  
						var res1=report_period_verify_2(document.getElementById("month"),document.getElementById("year"));
						
						if (res1!=1)
						{
							//alert("Inside");
							onMonthChange();
							//alert("Inside2");
							onYearChange();
						    mthch(document.getElementById("month"));
						  ob_freeze_status() ;
						}  
					}
					
				}    
									
</script>
	 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	 <title>Opening Balance - DCB    <%=Office_Name%> | TWAD Nest - Phase II </title>
     <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
     <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
     <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
     <script type="text/javascript" src="../scripts/msg.js"></script>    
     <script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
     <script type="text/javascript" src="../scripts/cellcreate.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>
	 <script type="text/javascript" src="../scripts/master.js"></script>
     <script type="text/javascript" src="../scripts/Ben_Report.js"></script>
     <script type="text/javascript" src="../scripts/iframerequest.js"></script>
      <script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
  </head>
<body onload="c1(), dcbshow(1),div_sh(), month('month','ob'),setEnable(),sh() ,data_show_dcb('show',2,'bentype'),report('show',1,'dis_value','');onMonthChange();onYearChange();month_check();rld2()">
<form>
	<table class="tab2" id="data_table" width="85%" align="center" border="0"   style="BORDER-COLLAPSE: collapse" border="1" borderColor="skyblue"  >
 	<tr class="tdH">
       <th align="center" height="20px" colspan="4" height="10" >Opening Balance -   <%=Office_Name%> <font class="tdText" color="red"><label id="msg"></label></font>
       </th>
 	</tr> 
 	<tr>
 		<input type="hidden" name="monthyearenable" id="monthyearenable" value="<%=yearmonthenable%>"/>  
		<input type="hidden" id="subdiv" name="subdiv" value="0"/>
		<input type="hidden" name="obsno" id="obsno"/>
		<input type="hidden" name="mobsno" id="mobsno"/>
		</tr>
				 <%
					if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
					{
					%> 
					<tr>
				 	 <td  ><font class="tdText">  Division</font> </td>
				 	 <td>
				 	 <%=division_list%>
				 	</td>
				 	 
				 	<%}%>
	 
		<td width="10%" class="tdText">Month and Year</td>
        <td width="10%" align="left" colspan="2">
      		    <select class="select" id="month" name="month"  style="width:80pt" onchange="rld2()"></select>
	    		<select class="select" id="year" name="year"  style="width:80pt" onchange="rld2()">
	    		
	    		<option value="0">-Select Year-</option>
		  	<%
				for (int i=year-4;i<=year;i++)
			  	{
		   			if (i==prv_year) 
		   			{
		   				System.out.println(prv_year);
		   	%>
		   	<option value="<%=i%>" selected="selected"><%=i%></option><% } else {	%>
		    <option value="<%=i%>"><%=i%></option><%
		    		}
			 	}
		  	%>
	    		</select> 
        </tD>
       
        <td valign="center"  align="center" ><input type="hidden" value="Interest" onclick="dcbshow(2)"></input>&nbsp;</td>
        </tr> 
		<tr >
			<td width="10%" class="tdText"> Beneficiary Type</td>
			<td width="10%" align="left"> <select class="select" id="bentype" name="bentype" onchange="vs(),grid_show(3,'data','ben_data','ben_body' ,'bentype'),flash()">  </select></td>
			<td width="30%" class="tdText"><div id="dis"> <b>District</b> &nbsp;:&nbsp; <select id="dis_value" class="select"   name="dis_value" onchange="ben_report_show('show',3,this),report('show',2,'block_value',this) " style="width:100pt"></select></div></td>
			<td align="left" width="25%" class="tdText"> <div id="block"> <b>Block </b>&nbsp;:&nbsp;<select class="select" id="block_value" class="selectbox" onchange="ben_filter()" name="block_value"  style="width:100pt"></select> </div></td>  
		</tr>
		<Tr><td valign="top" class="tdText">  Beneficiary Name  </td> 
		<td colspan="3" >
			<select id="ben_value" class="select" name="ben_value" onchange=" scheme_Select('show',5);" style="width:250pt">&nbsp;<input type="button" value="Search" onclick="grid_show(7,'report','','bentype','subdiv')"> <img src="../../../../../images/new_.jpg" width="30" height="20"/></select>		 
		</td>
		</Tr>
		<Tr>
			<td class="tdText" width="10%">  Select Scheme Name  </td>
			<td colspan="2" width="10%"><select id="Scheme" class="select" style="width:250pt"  name="Scheme" onchange=" single_vlaue('show',5);"  /></select></td>
			<td align="center"><img  src="../../../../../images/newhelp.jpg" height="34px" width="65px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=14','mywindow','scrollbars=yes')">
				<input type="button" onclick="vd()" value="Video Help">
			</td>
        </tr>		  
</table> 
		<table  style="BORDER-COLLAPSE: collapse" border="0"  borderColor="skyblue"  width="85%" align="center"   cellspacing="0" cellpadding="0" height="300">
			<tr>
		   		<td valign="top" align="left">
					<div id="ent_div">					
					</div>
				</td>
			</tr>
		</table> 
        <table class="table" bordercolor="#00FFFF"   width="85%" align="center" border="0"   cellspacing="0" cellpadding="0">
        <tr> 
	        <th align="center" width="2%"><font size="1"><a href="#A">A</a></font></th><th align="center"  width="2%"><font size="1"><a href="#B">B</a></font></th><th  align="center" width="2%"><font size="1"><a href="#C">C</a></font></th><th   align="center" width="2%"><font size="1"><a href="#D">D</a></font></th><th  align="center"  width="2%"><font size="1"><a href="#E">E</a></font></th><th align="center"   width="2%"><font size="1"><a href="#F">F</a></font></th><th  align="center"  width="2%"><font size="1"><a href="#G">G</a></font></th><th  align="center"  width="2%"><font size="1"><a href="#H">H</a></font></th>
    	    <th align="center" width="2%"><font size="1"><a href="#I">I</a></font></th><th align="center"  width="2%"><font size="1"><a href="#R">J</a></font></th><th  align="center" width="2%"><font size="1"><a href="#K">K</a></font></th><th   align="center" width="2%"><font size="1"><a href="#L">L</a></font></th><th  align="center"  width="2%"><font size="1"><a href="#M">M</a></font></th><th align="center"   width="2%"><font size="1"><a href="#N">N</a></font></th><th align="center"   width="2%"><font size="1"><a href="#O">O</a></font></th><th  align="center"  width="2%"><font size="1"><a href="#P">P</a></font></th>
        	<th align="center" width="2%"><font size="1"><a href="#Q">Q</a></font></th><th align="center" width="2%"><font size="1"><a href="#R">R</a></font></th><th align="center" width="2%"><font size="1"><a href="#S">S</a></font></th><th align="center"   width="2%"><font size="1"><a href="#T">T</a></font></th><th align="center"   width="2%"><font size="1"><a href="#U">U</a></font></th><th align="center"  width="2%"><font size="1"><a href="#V">V</a></font></th><th align="center"   width="2%"><font size="1"><a href="#W">W</a></font></th><th align="center"   width="2%"><font size="1"><a href="#Y">Y</a></font></th>
        	<th align="center"  width="2%"><font size="1"><a href="#X">X</a></font></th><th align="center"  width="2%"><font size="1"><a href="#Y">Y</a></font></th><th  align="center" width="2%"><font size="1"><a href="#Z">Z</a></font></th>
        </tr>
		</table>	
		<table bordercolor="#00FFFF"   width="85%" align="center" border="1"   cellspacing="0" cellpadding="0">
			<tr>
				<td align="left" width="15%" class="tdText"><font size="2"  >Process</font> </td>				
				<td align="left" width="5%" class="tdText"><font size="2"  >&nbsp;&nbsp;&nbsp;Select</font> </td>				
				<td align="left" width="20%" class="tdText"><font size="2" >&nbsp;&nbsp;&nbsp;Beneficiary </font> </td>
				<td align="left" width="40%" class="tdText"><font size="2" >&nbsp;&nbsp;&nbsp;Scheme Name</font> </td>			
				<td id="intid" align="center"  width="10%"  class="tdText"><font size="2" >Interest Entry&nbsp;&nbsp;&nbsp;</font> </td>
			</tr>
		</table>		 
 		<center> 
		<div id='scroll_clipper'  style=' width: 85%; border-height:1px; height: 196px; overflow:auto;white-space:nowrap;'  >
		<div id='scroll_text'  > 
 		<table class="fb2" bordercolor="#00FFFF"  id="entred_data" width="100%" align="center" border="0"   cellspacing="0" cellpadding="2">
		 <tbody id="entred_body" align="center" ></tbody>
		</table>
		<table class="table" bordercolor="#00FFFF"  id="entred_data" width="100%" align="center" border="0"   cellspacing="0" cellpadding="2">		<tr>
		<th align="center"><input class='fb2'   type="button" value="Exit" onclick="self.close()"></input><input type="button" class="fb2" value="Refresh" onclick="javascript:window.location.reload();"></th>
		</tr>
		</table>
</div>		
</div>
</center>
<input type="hidden" name="ob_free_status" id="ob_free_status"></input>
<input type="hidden" name="SELBENEFICIARY_SNO" id="SELBENEFICIARY_SNO"></input>
<input type="hidden" name="BENEFICIARY_SNO" id="BENEFICIARY_SNO"></input>
<input type="hidden" name="bentype" id="bentype"></input>
<input type="hidden" name="rowcnt" id="rowcnt"></input>
<input type="hidden" name="en_rowcnt" id="en_rowcnt"></input>
<input type="hidden" name="OFFICE_ID" id="OFFICE_ID" style="" value="5430"></input>
<input type="hidden" name="selsno" id="selsno"></input>
<input type="hidden" name="selbentype" id="selbentype"></input>
<input type="hidden" name="selprocess" id="selprocess" value="1"></input>
<input type="hidden" id="pr_status" name="pr_status" value="0"/>
<input type="hidden" name="apr_OB_MAINT_CHARGES" id="apr_OB_MAINT_CHARGES" value="0"></input>
<input type="hidden" name="apr_OB_CUR_YR_WC" id="apr_OB_CUR_YR_WC" value="0"></input>
<input type="hidden" name="apr_OB_YESTER_YR_WC" id="apr_OB_YESTER_YR_WC" value="0"></input>
<input type="hidden" name="apr_OB_INT_PRV_YR_MAINT" id="apr_OB_INT_PRV_YR_MAINT" value="0"></input>
<input type="hidden" name="apr_OB_INT_CUR_YR_MAINT" id="apr_OB_INT_CUR_YR_MAINT" value="0"></input>
<input type="hidden" name="apr_OB_INT_AMT_WC" id="apr_OB_INT_AMT_WC" value="0"></input>
<input type="hidden" name="mobsno_int" id="mobsno_int" value="0"></input>
<input type="hidden" name="mSCH_SNO" id="mSCH_SNO" value="0"></input>
<input type="hidden" name="selblk" id="selblk" value="0"></input>
</form>

</body>
</html>