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

  var flag=0;
  function pumped_process(val)  
{	  
	  
	  flag=val;
	var month=document.getElementById("month").value;
	var year=document.getElementById("year").value;
	
	if (month ==0 || year==0)
	{
		alert("Select Month and Year");  
	}else
	{
		
		var rowcount=document.getElementById("rowcount").value;
		var url="../../../../../sch_transaction?type=2&YEAR="+year+"&MONTH="+month+"&rowcount="+rowcount;
		
		for(var i=1;i<=rowcount;i++)
		{
			var sch_sno=document.getElementById("schsno"+i).value;
			var qty=document.getElementById("qty"+i).value;
			var dqty=document.getElementById("dqty"+i).value;
			var pump_qty=0;
			var desg_qty=0;
			if (qty=="")
				pump_qty=9999;    
			else
				pump_qty=qty;  
				          
			if (dqty=="")
				desg_qty=9999;    
			else
				desg_qty=dqty;  
			url+="&SCH_SNO"+i+"="+sch_sno;
			url+="&PUMPING_QTY"+i+"="+pump_qty;      
			url+="&DESIGN_QTY"+i+"="+desg_qty;     
		}      
		var xmlobj = GetXmlHttpObject();
		xmlobj.open("GET", url, true);
		xmlobj.onreadystatechange = function() 
		{
			pumped_process_result(xmlobj);
		}
		xmlobj.send(null);
	}
}
function pumped_process_result(xmlobj2)  
{
	if (xmlobj2.readyState == 4)   
	{
		if (xmlobj2.status == 200) 
		{ 
			if (flag==1)
			alert("Saved Successfully");
			else if (flag==2)
				alert("Updated Successfully");
			document.getElementById("pump").submit();  
		}
	}
}