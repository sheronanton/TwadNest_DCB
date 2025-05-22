

function numbercheck(value)
{
	var res=1;
	var input=value;
	try
	{
		input=parseInt(value);
		res=0;
	}catch(e) 
	{
		res=1;
		alert("given values not correct......"); 
	}
	return res;
} 

 
//Disable right mouse click Script
//By Maximus (maximus@nsimail.com) w/ mods by DynamicDrive
//For full source code, visit http://www.dynamicdrive.com

var message="\n ---------------------------\n  Right Click Disabled !!!\n---------------------------\n";

///////////////////////////////////
function clickIE4()
{	var val=true;
	 
	if (event.button==2)
	{
		alert(message);
		val= false;  
	}else
	{
		val= true;
	}  
	return val;
}

function clickNS4(e)
{
	var val=true;
	if (document.layers||document.getElementById&&!document.all)
	{
			if (e.which==2||e.which==3)
			{
				alert(message);
				val=false;
			} 
	}
	return val;
}

if (document.layers){
document.captureEvents(Event.MOUSEDOWN);
document.onmousedown=clickNS4;
}
else if (document.all&&!document.getElementById){
document.onmousedown=clickIE4;
}

document.oncontextmenu=new Function("alert(message);return false")
 