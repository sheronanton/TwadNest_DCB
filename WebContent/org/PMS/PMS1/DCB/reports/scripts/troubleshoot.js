function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)
    {
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function trouble_shoot(ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO,SL_NO)
{
 
	var c="";
	
	c=new Boolean(confirm("Confirm ? "));  
	if (c==true)
	{
		var project_id=document.getElementById("project_id").value;
		var params="?ACCOUNTING_FOR_OFFICE_ID="+ACCOUNTING_FOR_OFFICE_ID+"&CASHBOOK_YEAR="+CASHBOOK_YEAR+"&CASHBOOK_MONTH="+CASHBOOK_MONTH+"&RECEIPT_NO="+RECEIPT_NO+"&SL_NO="+SL_NO;
		var url = "../../../../../../PMS_DCB_RECEIPT_TROUBLESSHOOT"+params+"&project_id="+project_id;
		var xmlobj = GetXmlHttpObject();
		xmlobj.open("GET", url, true);
		xmlobj.onreadystatechange = function() 
		{
			trouble_shoot_process(xmlobj);
		}
		xmlobj.send(null);
	}
}
function trouble_shoot_process(xmlobj2)
{
	if (xmlobj2.readyState == 4) 
	{
		if (xmlobj2.status == 200) 
		{  
			var bR = xmlobj2.responseXML.getElementsByTagName("response")[0];
			var rows = bR.getElementsByTagName("rows")[0].firstChild.nodeValue;	
			if (rows > 0)
			{
				alert("SubLedger Code Succssfully Updated !!!!!");
				location.reload();
			}
		}
		
	}
}