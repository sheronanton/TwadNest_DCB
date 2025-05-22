function tariff_revised_rpt()  
{
	var option=document.getElementById("rtype").value;  
	var year= document.getElementById("year").value;
	 
	
	if (year=="0" )
	{
		alert("Select Financial Year / Division ! ");
	}
	else  
	{
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?splflag=0&option="+option+"&pr=17&option="+option+"&year="+year);
	}
}        
function tariff_revised_type_wiserpt()  
{
	var rtype=document.getElementById("type").value;      
	var splflag=document.getElementById("rtype").value;      
		var s=window.open("../../../../../../Pms_Dcb_Ledger_Report?splflag="+rtype+"&pr=18"+"&option="+splflag);  
}  