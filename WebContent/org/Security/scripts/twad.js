var wind=new Array();
var i=0;
function loadPage(url)
{    
		document.location=url;
}
function loadPageInNewWindow(url)
{  
		var dt=new Date();
		var st="";
		st=dt.getHours()+"_"+dt.getMinutes()+"_"+dt.getSeconds();
		wind[i]=window.open(url,"winName"+st,"status=no,location=no,menubar=no,resizable=1,scrollbars=yes,titlebar=yes,toolbar=no");  
		wind[i].focus();
		i=i+1;
}

function handleOver(url)
{
		if(url=='Dashboard')
		{
			if (document.getElementById) { // DOM3 = IE5, NS6
				document.getElementById('popp').style.visibility = 'visible';
				}
				else {
				if (document.layers) { // Netscape 4
				document.popp.visibility = 'visible';
				}
				else { // IE 4
				document.all.popp.style.visibility = 'visible';
				}
				} 
		}
}

function handleout(url)
{
		if(url=='Dashboard')
		{
			setTimeout("Func1()", 4000);
		}
}

function Func1()
{
		if (document.getElementById) { // DOM3 = IE5, NS6
		document.getElementById('popp').style.visibility = 'hidden';
		}
		else {
		if (document.layers) { // Netscape 4
		document.popp.visibility = 'hidden';
		}
		else { // IE 4
		document.all.popp.style.visibility = 'hidden';
		}
		} 
}
function loadPageInWindow(url)
{  
	  var dt=new Date();
	  var st="";
	  st=dt.getHours()+"_"+dt.getMinutes()+"_"+dt.getSeconds();
	  wind[i]=window.open(url,"winName"+st,"status=no,location=no,menubar=no,resizable=1,scrollbars=yes,titlebar=yes,toolbar=no");  
	  wind[i].focus();
	  i=i+1;
 
}
 
function myclose()
{
    var j=0;
    for(j=0;j<i;j++)
    {
        if (wind[j] && wind[j].open && !wind[j].closed) wind[j].close();
    }
}