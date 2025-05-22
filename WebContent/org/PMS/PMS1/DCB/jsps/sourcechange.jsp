<%@page import="java.util.*,java.sql.*"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<script type="text/javascript" src="../scripts/jquery-3.6.0.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen" />
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>	
<script type="text/javascript">
	function tometer()
	{
		var input_value="frommeter";
		process_code=12;								 
		var div=document.getElementById("div").value;
		var ben=document.getElementById("toben").value;
		var url="../../../../../SourceChange?process_code="+process_code+"&div="+div+"&ben="+ben;					 
		obj.open("GET",url,true);	
		obj.onreadystatechange = function()
		{
			load(obj,input_value,process_code);  	
		}
		obj.send(null);
				 	   
	}

	function meter()
	{
			var obj=createObject();
			var ben=document.getElementById("toben").value;
			var sdiv=document.getElementById("sdiv").value;
			var div=document.getElementById("div").value;
			var ss="";
			var frommeter=document.getElementById("frommeter");
			while (frommeter.selectedIndex != -1) 
			{ 
				if (frommeter.selectedIndex != 0) 
				ss+=frommeter.options[frommeter.selectedIndex].value+","; 
				frommeter.options[frommeter.selectedIndex].selected = false;
			}													 	 
			var process_code=9;			 
			if (sdiv!=0 && div!=0 && ben!=0 && ss!="")
			{	
				var url="../../../../../SourceChange?process_code="+process_code+"&ben="+ben+"&sdiv="+sdiv+"&div="+div+"&met_list="+ss;			 		 
				obj.open("GET",url,true);	
				obj.onreadystatechange = function()
				 {
					 load(obj,input_value,process_code);  	
				 }
				obj.send(null);  
			}else
			{
				alert("Values Missing !")
			}
	}
   function rld_3()
   {
		window.open("../../../../../Bill_Demand?process_code=555");	   
   }
   function tomaster ()
   {
	   var obj=createObject();
	   process_code=6;
	   var input_value="toben";										 
	   var div=document.getElementById("div").value;
	   var url="../../../../../SourceChange?process_code="+process_code+"&div="+div;					 
	   obj.open("GET",url,true);	
	   obj.onreadystatechange = function()
		{
		load(obj,input_value,process_code);  	
		}
		obj.send(null);				 	   
	}
   </script>
<script type="text/javascript" language="javascript">
	var obj=createObject();
	var process_code=0;
	$(function () {
		     
					 $('#fr').ready(
							 		function()
									 {
										var input_value="sch";
										process_code=1;
										// process 1 for load scheme of login diviision
										var url="../../../../../SourceChange?process_code="+process_code;					 
										obj.open("GET",url,true);	
										obj.onreadystatechange = function()
										 {
											 load(obj,input_value,process_code);  	
										 }
										obj.send(null);
									 }
									);

					 
					$('#sch').change(
							function (){
								var input_value="ben";
								process_code=2;
								  
								var sch=0;
								try {
								sch=document.getElementById("sch").value;
								}catch(e) {}
								// process 1 for load scheme of login diviision
								var url="../../../../../SourceChange?process_code="+process_code+"&sch="+sch;					 
								obj.open("GET",url,true);	
								obj.onreadystatechange = function()
								 {
									 load(obj,input_value,process_code);  	
								 }
								obj.send(null);

								}

							); 
					
					$('#commit').click(
							function (){
								 
								var process_code=11;
								var input_value="";
								var url="../../../../../SourceChange?process_code="+process_code;					 
								obj.open("GET",url,true);	
								obj.onreadystatechange = function()
								 {
									 load(obj,input_value,process_code);  	
								 }
								obj.send(null);

								}

							); 
					$('#rollback').click(
							function (){
								 
								var process_code=10;
								var input_value="";
								var url="../../../../../SourceChange?process_code="+process_code;					 
								obj.open("GET",url,true);	
								obj.onreadystatechange = function()
								 {
									 load(obj,input_value,process_code);  	
								 }
								obj.send(null);

								}

							); 
					$('#to').click(
							function (){
								var input_value="div";
								process_code=3;
								 
								// process 1 for load scheme of login diviision
								var url="../../../../../SourceChange?process_code="+process_code;					 
								obj.open("GET",url,true);	
								obj.onreadystatechange = function()
								 {
									 load(obj,input_value,process_code);  	
								 }
								obj.send(null);

								}

							);

					$('#master').click(
							function (){
								var input_value="div";
								process_code=5;
								var ben_leng=document.getElementById("ben");
								var div=document.getElementById("div").value;
								document.getElementById("tempben").value=ben_leng.value;
								var pmonth=document.getElementById("pmonth").value;
								var pyear=document.getElementById("pyear").value;
								var ben2=document.getElementById("ben2");
							
								
								
											if (div!=0)
											{
												var ss="";
												
												 var id=ben_leng.selectedIndex;
												 	  while (id != -1) 
													  { 
													  alert(id);
													  		if (id!= 0)
													  		{ 
													  			ss+=ben_leng.options[ben_leng.selectedIndex].value+",";

													  			var op=document.createElement("option");
													  			op.text=ben_leng.options[ben_leng.selectedIndex].text;
													  			op.value=ben_leng.options[ben_leng.selectedIndex].value;;
													  			 
													  			try
																{
													  				ben2.add(op,null);
																}catch(E)
																{
																	ben2.add(op);
																}

																			
												  			 
													 	 	  	ben_leng.options[ben_leng.selectedIndex].selected = false;
													  		}	  	
													   } 
												 	 
												 	 var url="../../../../../SourceChange?process_code="+process_code+"&ben_list="+ss+"&div="+div+"&pmonth="+pmonth+"&pyear="+pyear;
												 	 alert(url)  					 
													//	obj.open("GET",url,true);	
													//	obj.onreadystatechange = function()
													//	 {
													//		 load(obj,input_value,process_code);  	
													//	 }
													//	obj.send(null);
											}	   
								}

							); 

					
				 

					$('#mastermeter').click(
							function (){
								var input_value="frommeter";
								process_code=7;								 
								var div=document.getElementById("div").value;
								var ben=document.getElementById("ben2").value;
											 
			 
												 	 var url="../../../../../SourceChange?process_code="+process_code+"&div="+div+"&ben="+ben;					 
														obj.open("GET",url,true);	
														obj.onreadystatechange = function()
														 {
															 load(obj,input_value,process_code);  	
														 }
														obj.send(null);
										 	   
								}

							);

					
					$('#div').change(
							function (){
								var input_value="sdiv";
								process_code=4;
								var div=0;
								try {
									div=document.getElementById("div").value;
								}catch(e) {}
								// process 1 for load scheme of login diviision
								var url="../../../../../SourceChange?process_code="+process_code+"&div="+div;					 
								obj.open("GET",url,true);	
								obj.onreadystatechange = function()
								 {
									 load(obj,input_value,process_code);  	
								 }
								obj.send(null);

								}

							);
		}
 
	);

	function  load(obj_,input_value,process_code)
	{

  		if(obj_.readyState==4)  
		{  
   			 if(obj_.status==200)
    	 	{  
   				
   				if (process_code==9)
    	    	 	{
   							tometer()
        	    	}
    	    	 	if (process_code==5)
    	    	 	{
    	    	 		tomaster();
        	    	}
   					if (process_code==10  || process_code==11 )
   					{
   						var bR = obj_.responseXML.getElementsByTagName("result")[0];
   						var msg = bR.getElementsByTagName("msg")[0].firstChild.nodeValue;
   						 
   	   				}
   					else if (process_code==1 || process_code==2 || process_code==3 || process_code==4 || process_code==6 || process_code==7 )
    	    	 	{
   						var bR = obj_.responseXML.getElementsByTagName("result")[0];
		   				var len = bR.getElementsByTagName("sno").length;
						document.getElementById(input_value).options.length = 0;
		   				if (len > 1) 
		   				{
				   					var status = bR.getElementsByTagName("name")[0].firstChild.nodeValue;
				   				//if (len==0) 			alert(status+"\n-------------------------------")
				   				
				   					addOption(document.getElementById(input_value), "Select", "0")
				   				var temp=0;
				   				for (i = 0; i < len; i++) 
					   				{ 
					   					
				   						var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;

				   						
				   						var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
				   						
				   						if (process_code!=7)
				   						{
				   							if (temp!=sno) 
					   						{
				   									addOption(document.getElementById(input_value), name, sno)
				   							}
				   						}else
				   						{
				   								addOption(document.getElementById(input_value), name, sno)
					   					}
				   						temp=sno;
				   					}

		   					}
    	    	 	}
    	 	}	}       	
	}
  
</script>

</head>
<body id="fr" name="fr">

<% 
Calendar cal = Calendar.getInstance();
			int day = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
try
{	
		 Controller obj=new Controller();
	  	ResultSet rs2;
	  	String qry="";  
		String cmd=obj.setValue("master",request);
		String div=obj.setValue("div",request);
		String[] parameterValues = request.getParameterValues("ben");
		
		Connection con=obj.con();
		PreparedStatement ps;
		
		for (int i=1;i<=parameterValues.length;i++)
		{  
			out.println(parameterValues[i-1]);
			out.println(parameterValues.length);
			out.println("cmd" + cmd );
			out.println("div" + div ); 
			CallableStatement proc_stmt = null;
			if (cmd.equalsIgnoreCase("Change"))
			{
				out.println("START");
				proc_stmt = con.prepareCall("{call PMS_DCB_BEN_INS (?,?) }");
				proc_stmt.setInt(1, Integer.parseInt(parameterValues[i-1]));
				proc_stmt.setInt(2, Integer.parseInt(div));
				proc_stmt.execute();
				out.println("STOP"); 
			}  
			 
			
		}

		}catch(Exception e ) { out.println(e);}
						String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
		
%>
<form><a href="Scheme_Change.jsp">Scheme_Change </a>
<table border=1 class="newtable" width="75%" align="center" >
    <tr > <td colspan="3" align="center"><font size='4'>Scheme Change</font> </td></tr>
	<tr valign="top">
		<td width="25%">
		 
		<table width="100%" border=1 class="newtable">
			<tr>
				<td>Scheme Name</td>
				<td><select id="sch" name="sch" class="select"
					style="width: 200">
					<option value="0">Select</option>
				</select></td>
				<td>
				 <input type="button" id="to" name="to" value="To"></td>

			</tr>
		 <tr  >
	          <td> <font color="#0000A0"> Month And Year </font> </td>
			  <Td   ><select class="select" id="pmonth" name="pmonth"  style="width:90pt;"  onchange="rld()">
			  <option value="0">-Select Month-</option>
			  <%  int inp_year=0,inp_month=0;;
			  for (int j=1;j<=12;j++)
			  {
			  
			   if ( inp_month !=j) {
			   %>
			    <option value="<%=j%>"><%=monthArr[j]%></option>
			    <%} else { %>
			    <option value="<%=j%>" selected="selected"><%=monthArr[j]%></option>
			    <%} %>
			  <%} %>  
			 </select> <select class="select"  id="pyear" name="pyear"  style="width:80pt;" onchange="rld()" >
			   <option value="0">-Select Year-</option>
			  <%
			 
			  for (int i=2010;i<=year+1;i++)
			  {
			    if ( inp_year  !=i) {
			   %>
			  <option value="<%=i%>"><%=i%></option>
			  <%}else{%> 
			  <option value="<%=i%>" selected><%=i%></option>
			  
			  <%} %>
			  <%} %>
			  </select></td><td><input type="button" value="Print All"   onclick="demand_show('pdf',2,0)"> </tD>
			  </tr>
			<tr>
				<td>Beneficiary List</td>
				<td>
				
				
				
				<select id="ben" name="ben" multiple="multiple"
					style="height: 150; width: 200" class="select">
					<option value="0">Select</option>
				</select></td><td> <input type="button" id="mastermeter" name="mastermeter" value="Meter"><br>
				</td>
			</tr>
			
		</table>
		</td>

		<td width="25%">  
		<table width="100%" border=1 class="newtable">
			<tr><td  width="25%" >Division To :</td>
				<td  >
				  <select id="div" name="div"  class="select" style="width: 200;">
					<option value="0">Select</option>  
				</select>
				</td> <td> <input type="button" id="master" name="master" value="Change"></td>
			</tr>
			
			<tr>  
				 
				<td  align="center" colspan="3"  style="height: 105px">&nbsp;<select id="toben" name="toben" multiple="multiple"
					class="select" style="height: 150; width: 200">
					<option value="0">Select</option>  
				</select></td>
				 
			</tr>
			<tr>
			   <tr><td  width="30%" > Sub Division To :</td>
				<td colspan="2"> <select id="sdiv" name="sdiv" style=" width: 200"
					class="select">
					<option value="0">Select</option>
				</select>
				 
				</td>
			</tr>
			<tr>
				<td width="10%" align="center"><input type="button" id="MeterChange" name="MeterChange"  value="MeterChange" onclick="meter()"></td>
					<td colspan="2"><select id="frommeter" name="frommeter" class="select" multiple="multiple" style="height: 150; width: 200"></select></td>
				
				</tr>
					 
		</table>
		</td>
		 

		   
	</tr>

<tr>
		<td> <table>
			<tr>  
				<td> Converted List </td>
				<td><select id="ben2" name="ben2" class="select" multiple="multiple" style="height: 150; width: 200">
				 </select> <bR>
				</td>
			</tr>
			<tr>
				<td>From Ben Meter Location</td>
				<td><select id="frommeter" name="frommeter" class="select" multiple="multiple" style="height: 150; width: 200">
				 </select> <bR>
				</td>
			</tr>
			</table></td></tr>
						
</table>  

<input type="thidden" id="tempben" name="tempben">
   

</body>
</html>
