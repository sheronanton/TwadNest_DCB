<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="./jquery-3.6.0.js"></script>


<script>
  
function toolinin(e,dv)    
{
	var x=0;
	var y=0;		
	if(window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
    { 
		x=e.screenX;
		y=e.screenY;
    }else
    {
    	x=e.pageX;  
		y=e.pageY;
    }		    
	
	document.getElementById(dv).style.visibility="visible";		
	document.getElementById(dv).style.top=y-80;
	document.getElementById(dv).style.left="20%"; 
	document.getElementById(dv).style.position = "absolute";
	document.getElementById(dv).style.marginleft="200px"; 
} 
function tooloutin(dv)
{
	
	 document.getElementById(dv).style.visibility="hidden"; 

	
}

</script>

<style>
 
</style>

</head>
<body onload="toolinin(event,'dv')">
 
 
   <div id='dv'  style="border:1px solid red"  > 
   	<Br> Click ITEM WISE EXPENDITURE Button  for Item Wise Expenditure
   	<bR>  
   	<input type="button" onclick="tooloutin('dv')"></div>
</body>
</html>