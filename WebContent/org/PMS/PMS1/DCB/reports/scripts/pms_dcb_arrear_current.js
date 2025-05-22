function report_show()
{
	var month=document.getElementById("selmonth").value;
	var year=document.getElementById("year").value;
	var reporttype=document.getElementById("reporttype").value;
	var option=document.getElementById("option").value;
	var v=window.open("../../../../../../Pms_DCB_Arrear_Current?option="+option+"&reporttype="+reporttype+"&year="+year+"&month="+month);
	
	
}