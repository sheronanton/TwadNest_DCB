var obj=false;
function req_pro()
{
	try
	{
		var obj_new;
		if (window.XMLHttpRequest)
		{
			obj_new=new XMLHttpRequest();
		}
		if (window.ActveXObject)
		{
			obj_new=new ActiveXObject("Microsoft.XMLHTTP");
		}
	}catch(e)
	{
		obj_new= new ActiveXObject("Msxml2.XMLHTTP");
	}  
	var url="./../../../../../After_Ledger_Post_Verificiaton";
	obj_new.open("GET",url,true);
	obj_new.onreadystatechange=function()
	{
		ledger_post_verify(obj_new);
	}
	obj_new.send(null); 
}
function ledger_post_verify(obj_new)
{
	if (obj_new.readyState==4)
	{
		if (obj_new.status==200)
		{
			var br=obj_new.responseXML.getElementsByTagName("response")[0];
			var ben_count2=br.getElementsByTagName("ben_count2")[0].firstChild.nodeValue;
			var c_count1=br.getElementsByTagName("c_count1")[0].firstChild.nodeValue;
			if (ben_count2==c_count1)
				document.getElementById("after_posted").innerHTML="<font color='green'>DCB Data Posted  All Ben </font>";
			else
				document.getElementById("after_posted").innerHTML="<font color='red'>DCB Data Not Posted to All Ben Some one has missing please check Ben Ledger Omission Report <a href=''>Click Here</a> </font>";	
		}
	}
}
