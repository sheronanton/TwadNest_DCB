/*   Free Script provided by HIOXINDIA            */
/*   visit us at http://www.hscripts.com     */
/*   This is a copyright product of hioxindia.com */


var heading = "#ffaaff";
var bgcolor1 = "#ffdddd";
var bgcolor2 = "#eeffcc";
var font1 = "blue";
var font2 = "#000000";
var height = "180";
var width = "250";


function DaysInMonth(Y, M) {
    with (new Date(Y, M, 1, 12)) {
        setDate(0);
        return getDate();
    }
}

function setcal(mon,yea)
{
	mon=mon+1;
	var days = DaysInMonth(yea,mon);
	var D = new Date(mon+"/01/"+yea);
	var day = D.getDay();
		
	var ar = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
	var df = ar[mon-1];
	df = df+", "+D.getFullYear();
	document.xxx.ddd.value = df;

	var ddf = document.calen.length;
	
	for(var xx=0; xx<ddf; xx++)
		document.calen[xx].value = "";

	var ss = 1;
	for(var xx=day; xx<day+days; xx++){
		document.calen[xx].value = ss++;
	}
}

var exd = new Date();
var monthe = exd.getMonth();
var yeare = exd.getFullYear();

function prev()
{
	monthe = monthe-1;
	if(monthe < 0)
	{
		yeare = yeare-1;	
		monthe = 11;
	}
	setcal(monthe, yeare);
	return false;
}

function next()
{
	monthe = monthe+1;
	if(monthe > 11)
	{
		yeare = yeare+1;	
		monthe = 0;
	}
	setcal(monthe, yeare);
	return false;
}

function thismon()
{
	//yeare = yeare+1900;
	setcal(monthe, yeare);
}

document.write("<table width="+width+" height="+height+" cellpadding=1 cellspacing=0 \
		style=\"font-family: arial, verdana; color: "+font1+"; font-size: 12px;\" bgcolor="+bgcolor1+" border=1>\
			<tr bgcolor="+heading+" align=center><td colspan=7>\
				<table width=100% align=center style=\"color: "+font2+"; font-size: 12px;\" align=center>\
				<tr align=center><td><a style=\"cursor: pointer;\" onclick=\"return prev()\">Prev</a></td>\
				<td><form name=xxx style=\"margin: 0px; padding: 0px;\"><input size=12 style=\"font-size: 12px; \
				font-weight: bold; text-align: center; font-family: san-serif, verdana, arial;\" type=text name=ddd></form></td>\
				<td><a style=\"cursor: pointer;\" onclick=\"return next()\">Next</a></td></tr></table>\
			</td></tr>\
			<tr bgcolor="+bgcolor2+" align=center><td>Sun</td><td>Mon</td><td>Tue</td><td>Wed</td><td>Thur</td><td>Fri</td><td>Sat</td></tr>");
document.write("<form name=calen style=\"border: 0px; padding:0px;\">");
for(var xx=0; xx<6; xx++)
{
	document.write("<tr>");
	for(var cc=0; cc<7; cc++){
		var dd = xx*7+cc;
		if(dd == 41)
			document.write("<td align=center><a style=\"font-size: 9px; color: 888888; text-decoration: none;\" href=http://www.hscripts.com>H</a></td>");
		else
			document.write("<td align=center><input style=\"background-color: "+bgcolor1+"; color: "+font2+"; border: 0px; \" name=x"+dd+" readonly size=1></td>");
	}
	document.write("</tr>");
}

document.write("</form></table>");

thismon();

/*   Free Script provided by HIOXINDIA            */
/*   visit us at http://www.hscripts.com     */
/*   This is a copyright product of hioxindia.com */

