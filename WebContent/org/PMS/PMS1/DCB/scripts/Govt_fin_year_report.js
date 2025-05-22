function rld1(a)
{
	document.forms["myform"].submit();
}     
function rpt()  
{
	var option=document.getElementById("rtype").value;  
	var year= document.getElementById("year").value;
	var DV= document.getElementById("DV").value;
	var year= document.getElementById("year").value;
	if (year=="0" || DV=="0")
	{
		alert("Select Financial Year / Division ! ");
	}
	else
	{
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?splflag=0&option="+option+"&pr=4&option="+option+"&year="+year+"&month=0&div="+DV);
	}
}             
function rpt1()    
{	var option=document.getElementById("rtype").value;  
	var year= document.getElementById("year").value;
	if (year=="0")
	{
		alert("Select Financial Year! ");
	}
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=1&option="+option+"&pr=4&year="+year+"&month=0&div="+DV);
	}
}
function rpt3()    
{
	var process_year= document.getElementById("process_year").value;
	var process_month= document.getElementById("process_month").value;
	var option=document.getElementById("rtype").value;  
	if (process_year=="0" || process_month=="0")
	{
		alert("Select Month and Year! ");
	}  
	else
	{
		var DV= document.getElementById("DV").value;
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=1&pr=6&year="+process_year+"&month="+process_month+"&div="+DV);
	}
}
function rpt4()        
{
	var option=document.getElementById("rtype").value;  
	var process_year= document.getElementById("process_year").value;
	var process_month= document.getElementById("process_month").value;
	var DV= document.getElementById("DV").value;
	if (process_year=="0" || process_month=="0" || DV=="0")
	{
		alert("Select Month ,Year and  Division Name! ");
	}
	else
	{
	 		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?option="+option+"&splflag=0&pr=6&year="+process_year+"&month="+process_month+"&div="+DV);
	}
}