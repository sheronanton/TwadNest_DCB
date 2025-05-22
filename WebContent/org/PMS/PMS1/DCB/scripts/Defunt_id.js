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

function Setting() 
{
	var off=document.getElementById("off").value;
	
//		alert("Enter the Year ");
		
	var url="../../../../../Office_Shift_new?command=Setting&off="+off;
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
//		alert(req.responseText);
		if(req.status==200)
		{
			var response=req.responseXML.getElementsByTagName("response")[0];
			var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		
		if(command=="Setting")
			{
			if(flag=="success")
				{
					var mon;	
				var year=response.getElementsByTagName("year")[0].firstChild.nodeValue;
				var month=response.getElementsByTagName("month")[0].firstChild.nodeValue;
				if(month==1)
				{
				mon="January - ";
				}
				if(month==2)
				{
				mon="February - ";
				}
				if(month==3)
				{
				mon="March - ";
				}
				if(month==4)
					{
				mon="April - ";
					}
				if(month==5)
				{
				mon="May - ";
				}
				if(month==6)
					{
				mon="June - ";
					}
				if(month==7)
					{
				mon="July - ";
					}
				if(month==8)
					{
				mon="August - ";
					}
				if(month==9)
					{
				mon="September - ";
					}
				if(month==10)
					{
				mon="October - ";
					}
				if(month==11)
				{
				mon="November - ";
				}
				if(month==12){
				mon="December - ";	
				}		
				
				var res = mon.concat(year);
				var k= mon + year;			
				
				document.getElementById("pyear").innerHTML=k;
				
							
				
			}
				
				
		
				}}}};
	req.send(null);
	}


function SetOffice()
{
//	 alert("Start");
	var real=document.getElementById("real").value;
	var off=document.getElementById("off").value;

	
//	alert("real id is"+real);
//	alert("off id is"+off);
	
var url ="../../../../../Office_Shift_new?command=SetOffice&off="+off+"&real="+real;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		  
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	   if(command=="SetOffice")
		{
		if(flag=="success")
			{
			alert("Office Changed Sucessfully");
				location=location;
				}else
					{
					alert("Data failed");
					}
		}}}};
		
req.send(null);
}

















function getid()
{
	

var off_id=document.getElementById("off_id").value;
//var selmonth=document.getElementById("selmonth").value;
//var year=document.getElementById("year").value;
// alert("off_id"+off_id);
// alert("selmonth"+selmonth);
// alert("year"+year);
var old_office_id=new Array();
var option=new Array();
var office_name=new Array();

var url ="../../../../../../Office_Shift_new?command=getid&off_id="+off_id;
var req=getTransport();
req.open("GET",url,true);
req.onreadystatechange=function()
{
if(req.readyState==4)
	{
//	alert(req.responseText);
	if(req.status==200)
	{
		
		
//		  var select = document.getElementById("def_div");
//		    select.innerText="";
//		    select.innerHTML="";
//		    while (select.childNodes.length > 0) {
//		    	select.removeChild(tgrid4.firstChild);
//		   					          }   
		    
		    
		var response=req.responseXML.getElementsByTagName("response")[0];
		var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
		var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	if(command=="getid")
		{
		if(flag=="success")
			{
		//	   alert("inside success");

			var len=response.getElementsByTagName("count").length;
		//	  alert("inside len"+len);

			 for ( var i = 0; i <= len; i++)
			   {
					 
				 
			    old_office_id[i]=response.getElementsByTagName("old_office_id")[i].firstChild.nodeValue;
			    
			    office_name[i]=response.getElementsByTagName("office_name")[i].firstChild.nodeValue;
			 
			   	    
			     select = document.getElementById("def_div");
			     option[i] = document.createElement("option");
			     option[i].value=old_office_id[i];
			     option[i].innerHTML=office_name[i];
			     select.appendChild(option[i]);
			// document.getElementById("def_div").innerHTML="<option value="+old_office_id[i]+">"+old_office_id[i]+"</option>";
			    }
			
		
				}}}}};
req.send(null);
}
