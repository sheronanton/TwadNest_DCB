<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
  <link href="../../../../../../css/AME.css" rel="stylesheet" media="screen"/>
  <link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" >
function getTransport()
{
 var req=false;
 try
 {
	 req=new ActiveXobject("Msxml2.XMLHTTP");
 }
 catch(e)
{
	 try
{
	 req=new ActiveXobject("Microsoft.XMLHTTP");
}catch(e2)
{
	req=false;
}
}
if(!req && typeof XMLHttpRequest !='undefined')
	{
	req=new XMLHttpRequest();
	}
return req;
}

function setvalue(frm_off,sche_id)
{
//alert(frm_off);
//	alert(sche_id);
 var frm_off=frm_off;
 var sche_id=sche_id;


 var url="../../../../../Office_Shift?command=loadvalue1&frm_off="+frm_off+"&sche_id="+sche_id;
//	alert(url);
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
		if(req.readyState==4)
			{
	//		alert(req.responseText);
			if(req.status==200)
				{
				
//				alert("wwww--->"+req.responseText);
				var response=req.responseXML.getElementsByTagName("response")[0];
				var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="loadvalue1")
					{
					if(flag=="success")
						{
		//			alert("inside success");
						var tgrid=document.getElementById("tgrid2");
				         try{
					    	  tgrid.innerHTML="";
					          }
					      catch(e)
					        {
					    	  tgrid.innerText="";
					        }
					     while (tgrid.childNodes.length > 0) {
					    	  tgrid.removeChild(tgrid.firstChild);
					          }    
		//			     alert("inside clean");
					     var len=response.getElementsByTagName("count").length;
					     
					        for(var i=0;i<len;i++)
					        {
					        var BENEFICIARY_SNO=response.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
					        var BENEFICIARY_NAME=response.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
	//				        var OFFICE_LEVEL_ID=response.getElementsByTagName("OFFICE_LEVEL_ID")[i].firstChild.nodeValue;
	//				        var OFFICE_STATUS_ID=response.getElementsByTagName("OFFICE_STATUS_ID")[i].firstChild.nodeValue;
//					        
//					        
		// edit button to pass the value to register
					        
//					        var tr=document.createElement("TR");
//							tr.id=seq;
//							var td=document.createElement("TD");
//							var anc=document.createElement("A");
//							var url="javascript:loadvalue('"+seq+"')";
//							anc.href=url;
//							var edit=document.createTextNode("Edit");
//							anc.appendChild(edit);
//							td.appendChild(anc);
//							tr.appendChild(td);   
//							
							
//							var tr=document.createElement("TR");
//							tr.id=seq;
//							var td=document.createElement("TD");
//							var name=document.createTextNode(name_element);
//							td.appendChild(name);
//							tr.appendChild(td);  
					        
					        
					        var tr=document.createElement("TR");
							
                  	        var td1=document.createElement("TD");
					        var BENEFICIARY_SNO=document.createTextNode(BENEFICIARY_SNO);
					        td1.appendChild(BENEFICIARY_SNO);
					        tr.appendChild(td1);
					       
		        	        var td2=document.createElement("TD");
					        var BENEFICIARY_NAME=document.createTextNode(BENEFICIARY_NAME);
					        td2.appendChild(BENEFICIARY_NAME);
					        tr.appendChild(td2);
					        
					        
									
					        
					        
					        tgrid.appendChild(tr); 
					      
					}
					} else
			        {
				         
//				        document.getElementById("ename").value="";
//				        document.getElementById("eadd").value="";
				       
				        alert("Failure in loading values");
				        }
				}
			}
	}}
	req.send(null);	
};
 



</script>

</head>
<body onload="setvalue('<%=request.getParameter("frm_off")%>','<%=request.getParameter("sche_id")%>')">
<br>
<table align="center"  cellpadding="2" border="2">

<tr >
	<td colspan="8" align="center" class="tdText" bgcolor="skyblue" ><b>Division :</b></td>
	<td colspan="8" align="center" class="tdText" ><b> <%=request.getParameter("off_name")%>  (<%=request.getParameter("frm_off")%>)</b></td>
</tr>
<tr >
	<td colspan="8" align="center" class="tdText" bgcolor="skyblue"><b>Scheme Name : </b></td>
		<td colspan="8" align="center" class="tdText" ><b>  <%=request.getParameter("sche_Name")%>  (<%=request.getParameter("sche_id")%>)</b></td>
	
</tr>
</table>
 <br>
<center><input type="button" value="Back"  onclick="window.history.back()" class="sa" ></center>
<br>
<table id="igrid2" align="center"  cellpadding="2" border="2">
<tr bgcolor="skyblue" align="center">
	<td colspan="8" align="center" class="tdText"><b>List of Beneficiaries</b></td>
</tr>
 <tr align="center" bgcolor="skyblue">
 <th class="tdText">BENEFICIARY_SNO</th>
<th class="tdText">BENEFICIARY_NAME</th>
 </tr>
 <tbody id="tgrid2" align="center"></tbody>
 </table>
 <br><br>
<center><input type="button" value="Back"  onclick="window.history.back()" class="sa" ></center>
</html>