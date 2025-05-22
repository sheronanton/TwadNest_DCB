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


function report_show()
{
	var ftype=document.getElementById("ftype").value;
	var month=document.getElementById("selmonth").value;
	var year=document.getElementById("year").value;
	var year1=document.getElementById("year").value;
	var month1=month-1;
	if(month1==0)
		{
		month1=12;
		year1=year-1;
		}
	var today = new Date();
	var date = today.getDate();	
	 var prmonth = today.getMonth(); 
	 prmonth++;
// alert("year1 is"+year1);
	var year=document.getElementById("year").value;
	var reporttype=document.getElementById("reporttype").value;
//	alert(reporttype);
	var option=document.getElementById("option").value;
//	alert(option);
	var option_new=document.getElementById("option_new").value;
//	var option_new1=document.getElementById("option_new1").value;

//	alert(option_new);
	var v=window.open("../../../../../../Water_Charges_Pending2?com=Dis&ftype="+ftype+"&option="+option+"&option_new="+option_new+"&reporttype="+reporttype+"&year="+year+"&month="+month+"&month1="+month1+"&date="+date+"&prmonth="+prmonth+"&year1="+year1);
	
	
}

function check()
{
//	alert("HI");
	var url ="../../../../../../Add_Demand?command=check";
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
		if(command=="check")
			{
			if(flag=="success")
				{
				
				alert("Updated Sucessfully");
					}}}}};
	req.send(null);
	}


