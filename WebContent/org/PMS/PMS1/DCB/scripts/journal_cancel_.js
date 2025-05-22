var xmlobj=createObject();
function jou_cancel_ins()
{
	var rowcnt=document.getElementById("rowcnt").value;
	url="../../../../../journal_cancel_serv?rowcnt="+rowcnt ;
			 
	for (i=1;i<=rowcnt;i++)
	{
		url+="&vno"+i+"="+document.getElementById("no"+i).value;
		
	}
	 
	
	 xmlobj.open("GET",url,true);
	    
		xmlobj.onreadystatechange=function()
	    {	 
			jou_cancel_ins_process(xmlobj);
	    } 
		
		xmlobj.send(null);
}
function jou_cancel_ins_process(xmlobj)
{
	if (xmlobj.readyState==4)
	 {	   
	 	 
		if (xmlobj.status==200)
	    {
			var bR=xmlobj.responseXML.getElementsByTagName("result")[0];
			var row=bR.getElementsByTagName("row_up")[0].firstChild.nodeValue;;
			var tbstatus=bR.getElementsByTagName("tbstatus")[0].firstChild.nodeValue;;
			 
			if (tbstatus<=0)
			{
					if (row >0)
					{
						
						alert("DCB Journal Cancel  Successfully Completed")
						
						
					}
			}
			else
			{
				alert("DCB Journal Cancel Not Completed")
				
			}
	    }
		
	 }
	
	
}