function msgload(m,f) {
	newWindow("","alert_msg.jsp?msg="+m+"&flag="+f,310,150,1)
}

function newWindow(windowName, URL, width, height, scrolling) {
	width = width || 410;
	height = height || 360;
	scrolling = scrolling || 0;
	
	var topX = (window.screen.width / 2) - ( width / 2);
	var topY = (window.screen.height / 2) - ( height / 2);
	 
	window.open(URL, windowName, 'width=' + width + ',height=' + height + ',toolbar=no,directories=no,copyhistory=no, location=no,menubar=no,status =no,resizable=no|1,scrollbars=no,screenX=' + topX + ',screenY=' + topY,true);
}

