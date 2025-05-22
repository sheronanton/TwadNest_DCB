function validateDecimal(id){
	
	var field = id;
	var valo = new String();
	var numere = "0123456789.";
	var chars = field.value.split("");
	for (i = 0; i < chars.length; i++){
		if (numere.indexOf(chars[i]) != -1) 
			valo += chars[i];
		else{
			alert("Only Numbers And '.' !");
		}
	}
	if (field.value != valo) field.value = valo; 
}


function validateNumber(id){
	var field = id;
	var valo = new String();
	var numere = "0123456789";
	var chars = field.value.split("");
	for (i = 0; i < chars.length; i++){
		if (numere.indexOf(chars[i]) != -1) 
			valo += chars[i];
		else{
			alert("Numbers Only Allowed !");
		}
	}
	if (field.value != valo) field.value = valo; 
}
function enabledtb(id)
{
	if(document.getElementById(id).disabled=true){
	document.getElementById(id).disabled=false;}
}
function enabled1(id)
{
	
	document.getElementById(id).disabled="false";
}
function disabled1(id)
{
	
	document.getElementById(id).disabled="true";
}




function checkDecimal(id){
	var n=new RegExp("/^\d{4}+(.\d{0,2})?$/");
			if(id.value.match(n))
			{
				return true;
			}
			else
			{
				alert("enter decimal value");
				return false;
			}
}	

//function test(str)
// {
	// str=alltrim(str);
// / return /^\$?[1-9][0-9]{0,2}(,[0-9]{3})*(\.[0-9]{2})?$/.test(str);
	
// }
function check(id) {
	var val=
	id.value=Math.round(val*100)/100;
	if(!(val<10000)){
	alert("enter 4 digit value only");
	}
}

  

function Decimalvalidation(control)
{
//alert('regular expression');
var rgexp=("/^\d{4}(.\d{0,2})?$/");


var input=document.getElementById(control).value;
if(input.match(rgexp))
{
alert("ok");}
else{
alert("no");}
}
//******************************************************************
function val(tb,len,dec)
{
	var v=tb.value;
	var regex='^[0-9]{1,'+len+'}[.][0-9]{1,'+dec+'}$';
	if(v.search(regex)==-1)
	{
		     
		return false;
	}
	return true;
	
	
}
//******************************************************************

function val1(tb,len,dec)
{
	var v=tb.value;
	var regex='^[0-9]{1,'+len+'}[.][0-9]{1,'+dec+'}$';
	if(v.search(regex)==-1)
	{
		 
		return false;
	}
	return true;
	
	
}

function val_check(id,msg)
{
	var field=document.getElementById(id).value; 
	
	if (field == null || field == "") 
	{
		alert(msg);
		document.getElementById(id).focus;
		return 1;
	}else
	{ 
		return 0;
		
	}
}
function change_clr()
{
	document.getElementById('urban_val').value=="";
}

function check_val_zero(val,id)
{
	if(val==0)
	{	
		document.getElementById(id).value="";
	}
		else
	{
		document.getElementById(id).value=val;
	}
}
