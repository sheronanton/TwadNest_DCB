
		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle"%>
 			<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 			<%@page import="java.util.Calendar" %>
 			<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			 <link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
			<title>Scheme Performance </title>
			<script type="text/javascript">
			document.onmousedown=disableclick;
			var status="Right Click Disabled";
			function process()         
			{

				var month=document.getElementById('pmonth').value;
				var year=document.getElementById('pyear').value;
				if (month!=0 && year!=0)
				{  
					var res=report_period_verify_2(document.getElementById('pmonth'),document.getElementById('pyear'));		
					if (res!=1)
					{
						//transaction(2,18);
						transaction(2,11);
					}
				}   
			}
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
			 
			function divclr()
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
			}
			function rld()
			{
				var pmonth=0;
				var sch_sno=document.getElementById("sch_sno").value;
				var pyear=document.getElementById("pyear").value;
				try  
				{
				pmonth=document.getElementById("pmonth").value;
				}catch(e) {
					arg="&sch_sno=0&pyear=0&pmonth=0&bc=0";			
				} 

				if (sch_sno==0 || pyear==0 || pmonth==0)
				{
						alert(" Select Scheme/Year/Month ")
				}
				else
				{ 
				var arg="&sch_sno="+sch_sno+"&pyear="+pyear+"&pmonth="+pmonth;
				win=window.open("sch_sub1_ed.jsp?row=1+"+arg,"BankAccountNumber","status=1,height=900,width=1400,resizable=YES, scrollbars=yes");;
				win.focus();
				}
			}
			function exp()
			{
			
									
									var rowcount2 = document.getElementById("rowcount2").value;
									var table = document.getElementById("etablesub");
									var t=0;
									var sum=0;
									for(t=1;t<=rowcount2;t++)
									{
									  sum+=parseFloat(document.getElementById("eamt"+t).value)	
									}
									document.getElementById("famt3").value=sum;
									var famt2=parseFloat(document.getElementById("famt2").value);
									document.getElementById("famt4").value= roundNumber( (parseFloat(sum)*100000/(parseFloat(famt2)*1000)),2);
									var famt5=document.getElementById("famt5").value;
									document.getElementById("famt6").value=roundNumber(parseFloat(famt5)-parseFloat(sum),2);
									divclr();
			}
			function exp2()
			{
			
									
									 
									 
									var t=0;
									var sum=0;
									sum=document.getElementById("famt3").value;
									var famt2=parseFloat(document.getElementById("famt2").value);
									document.getElementById("famt4").value= roundNumber( (parseFloat(sum)*100000/(parseFloat(famt2)*1000)),2);
									var famt5=document.getElementById("famt5").value;
									document.getElementById("famt6").value=roundNumber(parseFloat(famt5)-parseFloat(sum),2);
									divclr();
			}
			</script>
			</head>
  			<script type="text/javascript" src="../scripts/transcat.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>
			<body onload="page_size(),transaction(3,6),flash(),toolout('dv'),toolout('dv1')">
			<%  
			
			try			
			{
			
			
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
			 
			
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
			String sch_sno=obj.setValue("sch_sno",request);
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String sch=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO", 
					 " where   sch_sno in (select distinct SCHEME_SNO from  PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID="+Office_id+" ) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by sch_sno","sch_sno","","class='select' style='width: 262'" );
			
			 
			//String sch= obj.getValue("PMS_SCH_MASTER", "SCH_NAME", " where SCH_SNO="+sch_sno);
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String flag_c=obj.setValue("flag_c",request);
			%>
			 
			<form action="" name="schperormance" >
			<table width="100%" class="table" align="center" border="1" cellpadding="5" cellspacing="0">
			 <tr> <th colspan="3" align="center"  class="tdH">Scheme Performance - Edit <label id='msg'></label></th></tr>
			 <tr> <td width="25%"><label class="lbl">Division Office Name :</label></td><td align="left" colspan="2"> <label class="lbl"><%=Office_Name%></label></td></tr>
			 <tr> <td width="25%"><label class="lbl">Scheme  Name : </label></td><td align="left" colspan="2"> <%=sch%></td></tr>
			 
			 <% if (flag_c.equalsIgnoreCase("Y")) { %>		 	
			 <tr>
	         <td><label class="lbl">Financial Year</label> </td>
			 <td colspan="3"><select class="select"  id="pyear" name="pyear"  style="width:75pt;" >
			 				 <option value="0">Select</option>
			  				<%
			  				  for (int i=year-2;i<=year+1;i++)
			  					{
			     		    %>
			  				 <option value="<%=i%>-<%=i+1%>"><%=i%>-<%=i+1%></option>
			  				<%} %>
			  				</select> 
			  </td>
			  </tr>
			 <%} else { %>	
			  <tr>
	          <td><label class="lbl"> Year </label> </td>
			  <td colspan="2"><select class="select"  id="pyear" name="pyear"  style="width:75pt;">
			  				  <option value="0">Select</option>
			  				 <%for (int i=year-2;i<=year;i++) {%>
			  				   <option value="<%=i%>"><%=i%></option>
			  				  <%}%>
			  				</select> 
			  </td>
			  </tr> 
			  
			  <tr>
			  	 <td><label class="lbl">  Month </label></td>	  	          
			 	 <td colspan="2" width="63%"><select class="select" id="pmonth" name="pmonth"  style="width:85pt;"   >
			  					 <option value="0">Select</option>
			  				  	<%for (int j=1;j<=12;j++){%>
			    				<option value="<%=j%>"><%=monthArr[j]%></option>
			    				<%} %>  
			 				</select>   <input type=button value="GO" onclick="process();">  
			 	 </td> 
			 	
			 </tr>
			 <% } %>  
			    
			 
			  </table>	
				<table id="etable" style="BORDER-COLLAPSE: collapse" border="1"  align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
					
					 
					<tr><td align="center">						
					<table id="etable" style="BORDER-COLLAPSE: collapse" border="1"   align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
						
		 				<tbody id="edata" align="center" valign="top"/>		 			 
		 				
			      	</table>
				</td></tr>
				<tr><td align="center">
					<input type="hidden" value="Save" class="btn" onclick="transaction(2,1)"/>					 
					<input type="hidden"" value="Close"  class="btn" onclick="window.close()"/> 
				</td></tr>
				</table>
				<table id="enttable"   border="1"  align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
		 				<tbody id="entdata" align="center" valign="top"/>		 			 
		 				
			</table>
				<table id="etable"   border="1"  align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
					<tr><td align="center" colspan="2">						
					<table id="etablesub"   border="0"   align="center" width="100%" border="1" cellspacing="0" cellpadding="1">
		 				<tbody id="edatasub" align="center" valign="top"/>		 			 
			      	</table>  
			      	  
				</td></tr>
				 <tr id='exp_id'>
				 	<th align="center">
						 <input type="hidden"  id="UPDATE"  value="Update" class="btn" onclick="transaction(2,1)"/>						 
						 <input type="hidden" id="itemexp" value="Iitem wise Expenditure" class="btn" onclick="rld()" />
						 <input type="button"  id="DELETE" value="Delete" class="btn" onclick="transaction(3,4)"/>
						 <input type="Reset" value="Clear" class="btn" />				   
						 <input type="button" value="Exit"  class="btn" onclick="window.close()"/>
					</th> 
				 	 </tr>
				</table>
				 
				</form>
				<input type="hidden" id="dataflag" name="dataflag" value="2"/> 
				<input type="hidden" id="row_count" name="row_count" value="0"/>
				<input type="hidden" id="rowcount2" name="rowcount2" value="0"/>
				<input type="hidden" id="flag_c" name="flag_c" value="<%=flag_c%>"/>
				<input type="hidden" id="sch_sno" name="sch_sno" value="<%=sch_sno%>"/>
				<%}catch(Exception e) {
				
			 
				response.sendRedirect("../../../../../index.jsp");
				
				} %>
				<input type="hidden" id="pr_status" name="pr_status" value="1"/>
				<div id='dv' style="height: 20;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" >Click Here for Expenditure Break up</div>
				<div id='dv1' style="height: 20;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" >Click Here Expenditure Bill Wise Break up</div>  
			</body>
		</html>  
