
<%@page import="java.sql.*"%>
<%@page import="java.sql.PreparedStatement"%><html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/location_load.js"></script>
<link href="../../../../../css/txtbox.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript">
function meter_change()
{
		var obj=createObject();		
		var sdiv=document.getElementById("asdiv").value;		
		var ss="";
		var process_code=34;30-10-2017
		var input_value="";
		var  len=document.getElementById("row").value;	

		for (i=1;i<=len;i++)
		{
			if (document.getElementById("chdcb"+i).checked==true)
			{
				ss+=document.getElementById("METRE_SNO"+i).value+",";
			}
		}

		if (sdiv!=0)
		{
				
				var url="../../../../../SourceChange?process_code="+process_code+"&sdiv="+sdiv+"&met_list="+ss;
			 
				obj.open("GET",url,true);	
				obj.onreadystatechange = function()
				 {
					 load_change(obj);  	
				 }
				obj.send(null);
		}
		else
		{
		alert("Select Sub Division ")
		}  

}
function ref()
{
	self.location="sourcechange_subdiv_chage.jsp";

}



function load_change(obj_)
{
		if(obj_.readyState==4)  
		{  
		 	if(obj_.status==200)
		   	 {
		 			var bR = obj_.responseXML.getElementsByTagName("result")[0];
					var row = bR.getElementsByTagName("row")[0].firstChild.nodeValue;
					
					if (row > 0 )
					{
							    alert (" Sub Division Changed Sucessfully !")
						 try {		
						 		window.reload();
						 }catch(e) { ref();}
						 loc2()	;
						 
					}
					else
						{
						alert ("Sub Division Change Not Sucessfull")
						}
		   	 }
		}
}


	function checkall(a)
	{
		var  len=document.getElementById("row").value;	

		for (i=1;i<=len;i++)
		{
			if (a==1)
			document.getElementById("chdcb"+i).checked=true;
			else
			document.getElementById("chdcb"+i).checked=false;
		}
	} 
	function loc()	
	{
		var obj3=createObject();
		process_code=33;
		var  div=document.getElementById("rdiv").value;
		
		var url="../../../../../SourceChange?process_code="+process_code+"&div="+div;
					 
			obj3.open("GET",url,true);
			
			obj3.onreadystatechange=function() { loction(obj3);};	
			obj3.send(null);
		  
	}
	
	function loction(obj_)
	{
		 
			if(obj_.readyState==4)  
			{  
				 
			 	if(obj_.status==200)
			   	 {
			 		 
			   	 
			   		var tbody = document.getElementById("entred_body");
			   		var table = document.getElementById("entred_data");
			   		var t=0;
			   		for(t=tbody.rows.length-1;t>=0;t--)
			   		 {
			   			tbody.deleteRow(0);
			   		 }
		   			var bR = obj_.responseXML.getElementsByTagName("result")[0];
					var len = bR.getElementsByTagName("METRE_SNO").length;

					 
					document.getElementById("row").value=len;
					for (var i=0;i<len;i++)
			   		{	
						var new_row = cell("TR", "", "", "mrow" + (i + 1), (i + 1), 2, "","", "", "", "", "");
			   			var METRE_SNO=xmlValue(bR, "METRE_SNO", i);
			   			var sch_name=xmlValue(bR, "sch_name", i);
			   			var BENEFICIARY_NAME=xmlValue(bR, "BENEFICIARY_NAME", i);
			   			var METRE_FIXED=xmlValue(bR, "METRE_FIXED", i);
			   			var METRE_WORKING=xmlValue(bR, "METRE_WORKING", i);
			   			var METRE_LOCATION=xmlValue(bR, "METRE_LOCATION", i);
			   			
			   			var sno_cell=cell("TD","label","","",(i+1),5,"","","","3%","center","","");
			   		    var BENEFICIARY_NAME_cell=cell("TD","label","","BENEFICIARY_NAME"+(i+1),BENEFICIARY_NAME,5,"ttd2","","","15%","left","","");
			   			var METRE_LOCATION_cell=cell("TD","label","","METRE_LOCATION"+(i+1),METRE_LOCATION,5,"ttd2","","","10%","left","","");
			   			var sch_name_cell=cell("TD","label","","sch_name"+(i+1),sch_name,5,"ttd2","","","10%","left","","");
			   			var METRE_WORKING_cell=cell("TD","label","","METRE_WORKING"+(i+1),METRE_WORKING,5,"ttd2","","","5%","left","","");
			   			var METRE_FIXED_cell=cell("TD","label","","METRE_FIXED"+(i+1),METRE_FIXED,5,"ttd2","","","5%","left","","");
			   			var METRE_SNO_cell = cell("TD", "input", "hidden", "METRE_SNO" + (i + 1), METRE_SNO,7, "ttd2", "", "", "2%", "", "onclick", "");
			   			var DCB_cell = cell("TD", "input", "checkbox", "chdcb" + (i + 1), 0,7, "ttd2", "", "", "5%", "center", "onclick", "");
			   			
			   			
			   			new_row.appendChild(sno_cell);
			   			new_row.appendChild(DCB_cell);
			   			new_row.appendChild(BENEFICIARY_NAME_cell);
			   			new_row.appendChild(METRE_LOCATION_cell);
			   			new_row.appendChild(sch_name_cell);
			   			new_row.appendChild(METRE_WORKING_cell);
			   			new_row.appendChild(METRE_FIXED_cell);
			   			new_row.appendChild(METRE_SNO_cell);
			   			
			   			
			   			tbody.appendChild(new_row);  
			   		}  
				} 
			}
}

				   								
										 		function div()
												 {  
										 			var obj=createObject();
													var input_value="rdiv";
													process_code=32;
													 
													var url="../../../../../SourceChange?process_code="+process_code;					 
													obj.open("GET",url,true);	
													obj.onreadystatechange = function()
													 {
														 load(obj,input_value,process_code);  	
													 }
													obj.send(null);
												 }
												  

										 		
										 		function adiv()
												 {  
										 			var obj=createObject();
													var input_value="asdiv";
													process_code=31;
													 
													var url="../../../../../SourceChange?process_code="+process_code;					 
													obj.open("GET",url,true);	
													obj.onreadystatechange = function()
													 {
														load(obj,input_value,process_code);
														
													 }
													obj.send(null);
													
												 }
												 
  
										
			 		 
 
			 function  load(obj_,input_value,process_code)
				{

			  		if(obj_.readyState==4)  
					{  
			   			 if(obj_.status==200)
			    	 	{  
			   				


				   				 
			   					if ( process_code==31 || process_code==32)
			   					{

				   					
				   				  try
				   				  {
			   						var bR = obj_.responseXML.getElementsByTagName("result")[0];
			   						var len = bR.getElementsByTagName("sno").length;
									document.getElementById(input_value).options.length = 0;
			   						if (len > 1) 
					   				{
			   								var bR = obj_.responseXML.getElementsByTagName("result")[0];
			   								var status = bR.getElementsByTagName("name")[0].firstChild.nodeValue;
							   				//if (len==0) 			alert(status+"\n-------------------------------")
							   				
							   				var temp=0;
							   				for (i = 0; i < len; i++) 
								   				{
							   						var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
							   						var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
							   							addOption(document.getElementById(input_value), name, sno)
							   							addOption(document.getElementById("asdiv2"), name, sno)
							   							
							   					}
							   					
					   				}
			   					
				   				  }catch(e) {}

				   				
			   	   				}





					   					 
					     }
					}       	
				}
			  
 </script>

</head>
<body id="dfr" name="dfr" onload="adiv()">



<% 
String userid="0",Office_id="",Office_Name="";
String Combo="";
try
{	
		 Controller obj=new Controller();
	  	ResultSet rs2;
	  	String qry="";  
		String cmd=obj.setValue("master",request);
		String div=obj.setValue("div",request);
		try
	   	{
	   		 
	   	   userid=(String)session.getAttribute("UserId");
	   	}catch(Exception e) {userid="0";}
	   	if(userid==null)
	   	{
	   		//response.sendRedirect(request.getContextPath()+"/index.jsp");
	   	}
	   	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;
	   	if (Office_id.equals("")) Office_id="0";
		  Combo=obj.combo_str("COM_MST_OFFICES","OFFICE_NAME","OFFICE_ID","where  OFFICE_ID in ( select OFFICE_ID from  COM_OFFICE_CONTROL where CONTROLLING_OFFICE_ID="+Office_id+" ) and ( OFFICE_STATUS_ID='RD' or  OFFICE_STATUS_ID='NC' or OFFICE_STATUS_ID='CL'  )","rdiv","onchange='loc()'");
		  //xml=obj.combo_lkup("OFFICE_ID", "OFFICE_NAME", "com_mst_all_offices_view", "where DIVISION_OFFICE_ID="+Office_id+" and OFFICE_LEVEL_ID='SD'",2,"--Select--"); // for division
		 
 
		}catch(Exception e ) { out.println(e);}
%>
<table width="98%"><tr> <td width="15%" align="center">Sub Division Change  </td></tr></table>
<table width="98%"  border="0">
			<tr  bgcolor="#408080">
				<td width="18%"><font color="white">  Beneficiary Current Sub Division </font> </td>
				<td>  <%=Combo%></td> <a href="Scheme_Change.jsp">.....</a>
	
	<td width="25%"><font color="white">Sub Division to be Changed</font></td>
			 
				<td><select id="asdiv" name="asdiv" style="width: 250"
					class="select"  >
					<option value="0">Select</option>
				</select></td>
		  
	</tr>
</table>
<table width="98%"   style="BORDER-COLLAPSE: collapse;border-color: skyblue"    border="1"  align="left"  cellspacing="0" cellpadding="0"   >
<tr>
<Td colspan="6" align="left">
<table width="98%"     style="BORDER-COLLAPSE: collapse;border-color: skyblue"     id="entred_data"  border="1"  align="left"  cellspacing="0" cellpadding="3"  >
   
<tr >
 	<td width="15%"><input type="button" value="Check All" onclick="checkall(1)"><input type="button" value="Remove All" onclick="checkall(2)"></td>
	<td width="28%"> Beneficiary Name</td>
	<td>Meter Location</td>
	<td width="18%">Scheme Name</td>  
	<td width="10%">Meter Fixed</td>
	<td width="10%">Meter Working</td>
</tr>  
</table> 
</Td>

</tr>  
     			<tr style="vertical-align: top;height: 150px;">     			 
     			<Td colspan="6" align="left">
     			<div style="clear: both; float: left; width: 1300px; height: 230px;   overflow-x; scroll; overflow-y: scroll;; padding: 0;">
				<div id='scroll_text'>
     					 <table width="100%"    id="entred_data"  border="1"  align="center"  cellspacing="0" cellpadding="3">
							<tbody   id="entred_body" align="left" style="border-color: skyblue;"   ></tbody>
						 </table>
				</div>  
				</div> 
				</Td>
				</tr>
			   
			 
   
<tr  bgcolor="#408080">
<td colspan="6">
		<table width="100%" border=0>
			<tr> 
				
				
				<td    align="center"> <input type="button" value="Submit" onclick="meter_change()"><input type="button" value="Refresh" onclick="ref()"> <input type="button" value="Exit" onclick="window.close()"></td>
			</tr>  
		</table>
		</td>
</tr> 
<tr  bgcolor="#408080">
	<td width="15%"><font color="white">View Sub Division Changes :</font>  </td>
	<td width="25%"><font color="white">Select Sub Division</font></td>
	
	<td><select id="asdiv2" name="asdiv2" style="width: 250"
					class="select" onchange="loc2()">
				 
				</select></td>
</tr>
	<tr style="vertical-align: top;height: 150px;">     			 
     			<Td colspan="6" align="left">
     			<div style="clear: both; float: left; width: 1300px; height: 230px;   overflow-x; scroll; overflow-y: scroll;; padding: 0;">
				<div id='scroll_text'>
     					 <table width="100%"    id="entred_data2"  border="1"  align="center"  cellspacing="0" cellpadding="3">
							<tbody   id="entred_body2" align="left" style="border-color: skyblue;"   ></tbody>
						 </table>
				</div>  
				</div> 
				</Td>
				</tr>
</table>
<input type="hidden" id="row" name=row >


  
</body>
</html>
