<html>
	<%@page import="java.sql.*"%>
	<%@page import="java.sql.PreparedStatement"%>
	<%@ page import="Servlets.Security.classes.UserProfile"%>
	<%@page import="Servlets.PMS.PMS1.DCB.servlets.*"%>
 
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Insert title here</title>
		<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen" />
 		<script type="text/javascript" src="../scripts/jquery-3.6.0.js"></script>
		<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
 		<script type="text/javascript">
		function ben() 
		{
			var obj = createObject();
			var input_value = "ben";
			process_code = 41;
			var url = "../../../../../SourceChange?process_code=" + process_code;
			obj.open("GET", url, true);
			obj.onreadystatechange = function() {
			load_details(obj, input_value, process_code);
			}
			obj.send(null);
		}	 
		$(function () {
		$('#div').change(
				function (){
					var input_value="sdiv";
					process_code=4;
					var div=0;
					try {
						div=document.getElementById("div").value;
					}catch(e) {}
					var obj=createObject();
					// process 1 for load scheme of login diviision
					var url="../../../../../SourceChange?process_code="+process_code+"&div="+div;
					 		 
					obj.open("GET",url,true);	
					obj.onreadystatechange = function()
					 {
						load_details(obj,input_value,process_code);  	
					 }
					obj.send(null);

					}

				);
		
	});
	   
			
			function master (){
				 
				var input_value="div";
				process_code=5;
				var ben_leng=document.getElementById("ben");
				var div=document.getElementById("div").value;		 
			 
				var obj=createObject();
				
				 
							if (div!=0)
							{
								var ss="";
									alert(ben_leng.selectedIndex)
								 	  while (ben_leng.selectedIndex != -1) 
									  { 
									  		if (ben_leng.selectedIndex != 0)
									  		{ 
									  			ss+=ben_leng.options[ben_leng.selectedIndex].value+",";

									  			var op=document.createElement("option");
									  			op.text=ben_leng.options[ben_leng.selectedIndex].text;
									  			op.value=ben_leng.options[ben_leng.selectedIndex].value;;
									 	 	   
									  		}	  
									  		ben_leng.options[ben_leng.selectedIndex].selected = false;  	
									   } 
								 	 
								 	 var url="../../../../../SourceChange?process_code="+process_code+"&ben_list="+ss+"&div="+div;
								 	 					 
										obj.open("GET",url,true);	
										obj.onreadystatechange = function()
										 {
											load_details(obj,input_value,process_code);  	
										 }
										obj.send(null);
							}	   
				}
 
	








	

			

			function load_details(obj_, input_value, process_code) {

				if (obj_.readyState == 4) {
					if (obj_.status == 200) {

						if (process_code == 5)
						{

							
							
						}
						else if (process_code == 41 || process_code == 4) {

							try {  
								var bR = obj_.responseXML
										.getElementsByTagName("result")[0];
								var len = bR.getElementsByTagName("sno").length;
								document.getElementById(input_value).options.length = 0;
								if (len > 1) {
									var bR = obj_.responseXML
											.getElementsByTagName("result")[0];
									var status = bR.getElementsByTagName("name")[0].firstChild.nodeValue;
									//if (len==0) 			alert(status+"\n-------------------------------")

									var temp = 0;
									for (i = 0; i < len; i++) {
										var sno = bR.getElementsByTagName("sno")[i].firstChild.nodeValue;
										var name = bR.getElementsByTagName("name")[i].firstChild.nodeValue;
										addOption(document.getElementById(input_value),name, sno)

									}

								}

							} catch (e) {
							}

						}

					}
				}
			}
	
</script>

<body id="fr" name="fr" onload="ben()">

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
		String[] parameterValues = request.getParameterValues("ben");
		
		Connection con=obj.con();
		PreparedStatement ps;
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
		  Combo=obj.combo_str("com_mst_all_offices_view","OFFICE_NAME","OFFICE_ID","where  OFFICE_ID   in ( select OFFICE_ID from  COM_MST_OFFICES where OFFICE_STATUS_ID='CR') and OFFICE_LEVEL_ID='DN' order by OFFICE_NAME ","div"," ");

		 
		 
		
		 
			
		 
 
}catch(Exception e ) { out.println(e);}
%>


<table>
	<tr>
		<td>Beneficiary Name</td>
		<td>
			<select id="ben" name="ben" style="width: 250" class="select"  multiple="multiple">
			<option value="0">Select</option>
		</select></td>
	</tr>
	<tr>
	<td colspan="2">  
		<table width="100%" border=1 class="tab">
			<tr><td  width="25%" >Division To :</td>
				<td  >
				 <%=Combo%>
				</td> <td> <input type="button" id="master" name="master" value="Change" onclick="master()"></td>
			</tr><tr>
			   <tr><td  width="30%" > Sub Division To :</td>
				<td colspan="2"> <select id="sdiv" name="sdiv" style=" width: 200"
					class="select">
					<option value="0">Select</option>
				</select>
				 
				</td>
			</tr>
			</table>
		</td>
	</tr>

</table>

</body>
</html>
