<html>
	<%@ page import="java.sql.*,java.util.ResourceBundle"%>
 	<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 	<%@page import="java.util.Calendar" %>
 	<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Scheme Performance </title>
	<script type="text/javascript" src="../scripts/reports.js"></script>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
	<script type="text/javascript">
	document.onmousedown=disableclick;
	var status="Right Click Disabled";
	function disableclick(e)
	{
		var browser=window.navigator.appName.toLowerCase().indexOf("netscape");  
		if (browser==0)	  
		{
		  if(e.button==2) { alert(status); return false; }		   
		}
		else
		{
			if(event.button==2)
			   {
			     alert(status);
			     return false;    
			  }
		}   
	 }
	function page_size()
	{
		window.resizeTo( 800,700 )  
	}  
	function process()        
	{

		var month=document.getElementById('pmonth').value;
		var year=document.getElementById('pyear').value;
		if (month!=0 && year!=0)
		{  
			var res=report_period_verify_2(document.getElementById('pmonth'),document.getElementById('pyear'));		
			if (res!=1)
			{
				
				 transaction(2,18);
			}
		}       
	} 

	
	function rld()
	{
		var pmonth=0;
		var sch_sno=document.getElementById("sch_sno").value;
		var pyear=document.getElementById("pyear").value;
		try  
		{
			pmonth=document.getElementById("pmonth").value;
		}catch(e) 
		{
			arg="&sch_sno=0&pyear=0&pmonth=0&bc=0";			
		} 
		if (sch_sno==0 || pyear==0 || pmonth==0)
		{
			alert(" Select Scheme/Year/Month ")
		}
				else
				{ 
					var arg="&sch_sno="+sch_sno+"&pyear="+pyear+"&pmonth="+pmonth;
					win=window.open("sch_sub1.jsp?row=1+"+arg,"BankAccountNumber","status=1,height=900,width=1400,resizable=YES, scrollbars=yes");;
					win.focus();
				}
			}
			function divclr()
			{
					try
					{
					var tbody = document.getElementById("edatasub");
					var table = document.getElementById("etablesub");
					var t=0;
					for(t=tbody.rows.length-1;t>=0;t--)
					{
						tbody.deleteRow(0);
					}
					var row = document.getElementById("exp_id");
					if (row.style.display == '') row.style.display = 'none';
					else row.style.display = '';
					}catch(e) {}
			}
			
			function cls()
			{
						var row = document.getElementById("exp_id");
								 row.style.display = 'none';
			}
			</script>
			</head><!--
 			 <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet" media="screen"/>
  			--><script type="text/javascript" src="../scripts/transcat.js"></script>
 			<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
			<body onload="page_size(),flash(),cls(),transaction(2,5);toolout('dv1')">
			<%  
			try			  
			{
				//,toolout('dv'),toolout('dv1');
			HttpSession session1 = request.getSession(false);
			String	userid = (String) session1.getAttribute("UserId");
			Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			Controller obj=new Controller();
			if (userid == null) {

				response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			String sch_sno=obj.setValue("sch_sno",request);
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
			
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			
			//String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO", " where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")","sch_sno","","class='select' style='width: 262'" );
			//String sch= obj.getValue("PMS_SCH_MASTER", "SCH_NAME", " where SCH_SNO="+sch_sno);
					String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where SCH_type_id in ( select SCH_type_id from PMS_DCB_APPLICABLE_SCH_TYPE ) and sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by sch_sno","sch_sno","","class='select' style='width: 262' " );

			//String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")","sch_sno","","class='select' style='width: 262' onchange='transaction(2,5)'" );
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String flag_c=obj.setValue("flag_c",request);
			int year_incr=0;
			  if (month <4)
				  year_incr=0;    
			  else
				  year_incr=1;  
			%>     
			<form action="" name="schperormance" >
			<table   align="center" width="100%" border="1" cellspacing="1" cellpadding="1"> 
     		<tr> <th colspan="3" align="center"  class="tdH">Scheme Performance Month Wise </th></tr>
			<tr> <td width="25%"><label class="fnt">Division Office Name :</label></td><td align="left" colspan="2"> <label class="fnt"><%=Office_Name%></label></td></tr>
			<tr> <td width="25%"><label class="fnt">Scheme  Name : </label></td><td align="left" colspan="2"> <%=sch%></td></tr>
			 <% if (flag_c.equalsIgnoreCase("Y")) { %>		 	
			 <tr>
	         <td><label class="fnt">Financial Year</label> </td>
			 <td colspan="3"><select class="select"  id="pyear" name="pyear"  style="width:75pt;" >
			 				 <option value="0">Select</option>
			  					<%
								for (int i = year-2; i <= year+year_incr; i++) 
								{
								%>
								<option value="<%=i-1%>-<%=i%>"><%=i-1%>-<%=i%></option>
								<%
								}
								%>
			  				</select> 
			  </td>
			  </tr>
			 <%} else { %>	
			  <tr>
			  	 <td><label class="fnt">  Month </label></td><!--	  	          
			  	 onchange="process()" 
			 	 --><td colspan="2" width="63%"><select class="select" id="pmonth" name="pmonth"  style="width:90pt;"  >
			  					 <option value="0">Select</option>
			  				  	<%for (int j=1;j<=12;j++){%>
			    				<option value="<%=j%>"><%=monthArr[j]%></option>
			    				<%} %>  
			 				</select>   
			 	 </td>   
			 </tr>
			  <tr>
	          <td><label class="fnt"> Year </label> </td>
			  <td colspan="2"><select class="select"  id="pyear" name="pyear"  style="width:80pt;" >
			  				  <option value="0">Select</option>
			  				 <%for (int i=year-2;i<=year;i++) {%>
			  				   <option value="<%=i%>"><%=i%></option>
			  				  <%}%>
			  				</select> <input type=button value="GO" onclick="process();"> 
			  </td> 
			  </tr> 
			  <tr> <td colspan="2" width="50%"> &nbsp;&nbsp;<font size='5'><label id='msg'></label></font></td>
					<td  align="right"><label style="color:red">*</label> Indicates Mandatory input</td>
				</tr>
			 <% } %>     
			    
			 
			  </table>	
				<table id="etable"  style="border-collapse: collapse;border-color: skyblue" align="center" width="100%" border="0" cellspacing="0" cellpadding="1">
					<tr >
					<td align="center">						 
					<table id="etable"   border="0"  style="border-collapse: collapse;border-color: skyblue" align="center" width="100%"  cellspacing="0" cellpadding="1">
		 				<tbody id="edata" align="center" valign="top"/>		 			 
			      	</table>    
				</td></tr>
				<tr>
					<td align="right"> <label style="color:red">*</label> Indicates Mandatory input</td>
				</tr>
				<tr class="tdH"><th align="center">  
					<input type="button" id="save" name="save"  value="Add" class="btn" onclick="transaction(2,1)"/>
					<input type="button" id="itemwise" name="itemwise" value="ITEM WISE EXPENDITURE" class="btn" onclick="rld()" />	
					<input type="Reset" value="Clear" class="btn" />
					<input type="button" value="Delete" class="btn" id="delete" onclick="transaction(3,4)" />									   
					 <input type="button" value="Report" onclick="report(2)" class="btn" >					
					<input type="button" value="HELP" onclick="javascript:window.open('./twad_note.html#t4','_blank','toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=yes, width=600, height=800');">
					<input type="button" value="Exit"  class="btn" onclick="window.close()"/>
				</th></tr> 
				</table> 
				 
			<table   border="0"  align="center" width="100%"   cellspacing="1" cellpadding="1">
			<tr><th>
				<table id="etable"     align="center" width="100%" border="0" cellspacing="1" cellpadding="1">
				 <tr id='exp_id'><th>
				 <input  type=button value="Close"  class="btn" onclick="divclr()" />  </th>
				 <th align="right"><input  type=button class="btn" id="save" value="Submit" onclick="transaction(2,10)"/>  </th></tr>
				</table> 
				</th>   
				</tr>  
				</table>
				<!--<input  type="hidden" id="sch_sno" name="sch_sno" value="<%=sch_sno%>"/>-->
				 <input type="hidden" id="days" name="days" value="0"/> 
				 <input type="hidden" id="pumped_dpr" name="pumped_dpr" value="0"/>
					 <input type="hidden" id="dataflag" name="dataflag" value="0"/> 
					<input type="hidden" id="row_count" name="row_count" value="0"/>
					<input type="hidden" id="desg_hour" name="desg_hour" value="0"/>
					<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
				<input type="hidden" id="flag_c" name="flag_c" value="M"/>
				<%}catch(Exception e) 
				{
					response.sendRedirect("../../../../../index.jsp");
				} %>        
				 
				<input type="hidden" id="pr_status" name="pr_status" value="1"/>
				<!--  <div id='dv1' style="position:absolute;top:-10;height: 100;background-color:#B1EAF0;" > <table border=1 style="border-collapse: collapse;" cellpadding="5" cellspacing="5">
				
				 <tr>
				 	<td colspan="2">Enter Scheme Monthly Average Pumped Qty in MLD   <input type=text id="dpr" maxlength=5 size=5 value=0 ></td>
				 </tr>
				 <tr><td>(Refer URL http://218.248.23.4/maint-projects)  TWAD,HO Daily P.R module 
				 </td><td valign="top"><input type=button value="Close" onclick="set_value()"></td></tr>
				 </table>
				 
				</div> -->
				<div id='dv1' style="height: 20;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" >Click Here Expenditure Bill Wise Break up</div>
				 
			</form>  
			</body>
		</html>  
