		<html>
		 	<%@ page import="java.sql.*,java.util.ResourceBundle"  %>
 			<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 			<%@page import="java.util.Calendar"  %>
 			<%@page import="java.util.GregorianCalendar"  %>
 			<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller" %>
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<title>Scheme AME Items Master for Financial Year</title>
			<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/> 
  			<script type="text/javascript" src="../scripts/transcat.js"></script>
  			<script type="text/javascript" src="../scripts/reports.js"></script>
 			<script type="text/javascript" src="../scripts/cellcreate.js"></script>  
			<script type="text/javascript"> 			
			function page_size()
			{

				window.resizeTo( 700,800 )

				
			} 
			function chkall()
			{
				var rnt= document.getElementById("rowcount").value;
				for(var i=1;i<=rnt;i++)
				{
					var chk=document.getElementById("chk"+i).checked=true;
				}

			}
			function chkall2()
			{
				var rnt= document.getElementById("rowcount").value;
				for(var i=1;i<=rnt;i++)
				{
					var chk=document.getElementById("chk"+i).checked=false;
				}

			}
			    

			function ct()
			{
				var ct3=0;
				var rnt= document.getElementById("rowcount").value;
				ct3=0;
				for(var i=1;i<=rnt-2;i++)
				{
					try    
					{
						 
						if (document.getElementById('chk'+i).value=='y')      
						{       
						ct3++;  
						}						
					 }catch(e)   
					 {
						alert("MSG" +e);
						}
				}
				 
					if (ct3 > 0) 
					{
						alert(" Items already added for selected scheme and Financial Year ")
					}  
			}
			function rld2()
			{	
			 	 document.forms['esitem'].submit();
			}
			function ed()
			{
				var rnt= document.getElementById("rowcount").value;
				var TECH_REF_NO= document.getElementById("TECH_REF_NO").value;
				var sch_sno= document.getElementById("sch_sno").value;
				var pyear= document.getElementById("pyear").value;
			 
				for(var i=1;i<=rnt;i++)
				{
					var MAIN_ITEM_SNO=document.getElementById("MAIN_ITEM_SNO"+i).value;
					if (MAIN_ITEM_SNO=="" || isNaN(MAIN_ITEM_SNO)) 
						MAIN_ITEM_SNO=0; 
					var SUB_ITEM_SNO=document.getElementById("SUB_ITEM_SNO"+i).value;
					var GROUP_SNO=document.getElementById("GROUP_SNO"+i).value;
					var s=0;
					try
					{
						s=item_select(7,TECH_REF_NO,sch_sno,pyear,MAIN_ITEM_SNO,SUB_ITEM_SNO,GROUP_SNO,i);
					}catch(e) {}
				}    
			//	transaction(1,12)  ;  
			sleep(500);
			ct();  
			}  
			function sleep(milliseconds) 
			{
				  var start = new Date().getTime();
				  for (var i = 0; i < 1e7; i++) 
				  {
				    if ((new Date().getTime() - start) > milliseconds)
				    {
				      break;
				    }
				  }
			}
			</script>
			</head>
			<body onload="page_size(),transaction(1,7),toolout('dv')">
			<%  
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
			String pyear=obj.setValue("pyear",request);
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
			if (Office_id.equals("")) Office_id="0";
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ "  ");
			String sch= obj.getValue("PMS_SCH_MASTER", "SCH_NAME", " where SCH_SNO="+sch_sno);
			int rc=obj.getCount("PMS_AME_TRN_BUDGET"," where SCH_SNO="+sch_sno+" and OFFICE_ID="+Office_id+" and FIN_YEAR='"+pyear+"'");
			String cond="'O'";
			if (rc>1)
				cond="'R'";
			String ref_=obj.combo_str("PMS_AME_TRN_BUDGET","TECH_REF_NO","TECH_SANC_NO"," where SCH_SNO="+sch_sno+" and ORG_REV="+cond+" and OFFICE_ID="+Office_id+" and FIN_YEAR='"+pyear+"'","TECH_REF_NO","0","class='select' style='width: 262;' 'font-weight: bold;' onchange=ed()" );  
			String sch_combo=obj.combo_str("PMS_SCH_MASTER","SCH_NAME","SCH_SNO"," where   SCH_SNO  in (select scheme_sno from PMS_DCB_MST_BENEFICIARY_METRE) and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") order by SCH_NAME  ","sch_sno",sch_sno,"class='select' style='width: 262;'    font-weight: bold;  onchange= rld2()" );
			String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
			String rate=obj.getValue("PMS_AME_MST_CENTAGE","CENTAGE"," where ACTIVE_STATUS='A' and SCH_SNO="+sch_sno);
			int year_incr=0;
			  if (month <4)
				  year_incr=0;    
			  else
				  year_incr=1; 
			%>  
			<form action="amtran_master.jsp" method="get" name="esitem">
			<table width="100%"  style="border-collapse: collapse;border-color: skyblue"  align="center" border=1 cellpadding="5" cellspacing="1" >
			<tr> <th colspan="2" class=tdH align="center">Scheme AME Items Master for Financial Year</th></tr>
			<tr> <td width="25%"><label class="fnt">Division Office Name :</label></td><td align="left"> <label class="lbl"><%=Office_Name%></label></td></tr>
			<tr>
				<td>
					<label class="fnt"> Financial Year</label> 
				</td> 
	          		<td>
	          			<select class="select"  id="pyear" name="pyear"  style="width:120pt;  ">
			   				<option value="0">Select</option>
			  				<%
			  				
			  					String opyear="";
			  					for (int i = year-2; i <= year+year_incr; i++) 
					  			{
					  				opyear=i+"-"+(i+1);
					  				
					  				if (pyear.equalsIgnoreCase(opyear))
					  				{ 
					  				%>
					  				<option value="<%=i%>-<%=i+1%>" selected><%=i%>-<%=i+1%></option>
					  				<% } else {  %>
					  				<option value="<%=i%>-<%=i+1%>"><%=i%>-<%=i+1%></option>
					  				<% } %>  
					  		<%} %>
			  			</select> 
			  		</td>
			  
			  </tr>
			 <tr>
					<td width="35%"><label class="fnt">Scheme  Name   </label></td>
					<td align="left"> <%=sch_combo%> </td>
			  </tr>			 
			  <tr>
		  			<td>Maint.Estimate Sanction Ref.No </td>
		  			<td><%=ref_%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Check All"  class="btn" onclick="chkall()"/>&nbsp;  <input type="button" value="Deselect All"  class="btn" onclick="chkall2()"/></td>
			  </tr>
			   
			  </table>					 
				<table id="etable"   border="1"  style="border-collapse: collapse;border-color: skyblue"   align="center" width="100%" border="1" cellspacing="1" cellpadding="1">
					<tr><td align="center">						
					<table id="etable" style="border-collapse: collapse;border-color: skyblue"   border="1" align="center" width="100%"  cellspacing="1" cellpadding="1">
		 				<tbody id="edata" align="center" valign="top"/>		 			 
			      	</table>
					</td>
				</tr>   
				<tr class="tdH"><td align="center">
					<input type="button" value="Add" class="btn" id="add" onclick="transaction(1,11)" />  
				 	<input type="button" value="Update" class="btn" onclick="transaction(1,14)" />     
					<input type="button" value="Delete" class="btn" id="delete" onclick="del_item_map()"/>
					<input type="reset" value="Clear"  class="btn" />							
					<input type="button" value="Exit"  class="btn" onclick="window.close()"/>
					<input type="button" value="Help" onclick="javascript:window.open('./twad_note.html#a1','_blank','toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=yes, width=600, height=800');">
					
					</td>
				 </tr>
				</table>
				
				<table id="etable4"   style="border-collapse: collapse;border-color: skyblue"  border="1" align="center" width="100%"  cellspacing="1" cellpadding="1">
		 				<tbody id="edata3" align="center" valign="top"/>		 			 
			    </table>
			    
				<table class="table" width="100%" align="center" border="1" cellpadding="0" id="etablenew"  style="border-collapse: collapse;border-color: skyblue">
						<tbody id="edatanew" align="left" valign="top"></tbody>
				</table>
				<input type="hidden" id="pr_status" name="pr_status" value="0"/>
				<input type="hidden" id="rowcount" name="rowcount" value="0"/>
				<input type="hidden" id="rate" name="rowcount" value="<%=rate%>"/>
				<input type="hidden" id="sch_sno" name="sch_sno" value="<%=sch_sno%>"/>
				<div id='dv' style="height: 20;background-color: 	#254117;border-color:red;color:white; text-align: center;vertical-align: middle" >Click Here For Centage Calculation</div>
				</form>  
				  
				
			</body>
		</html>  
