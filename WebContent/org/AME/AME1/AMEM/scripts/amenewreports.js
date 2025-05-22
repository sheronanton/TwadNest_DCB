function am_est_fun(a)
{
	var params = "";
	var process_code=a;
	var xmlobj_new = createObject();  
	url = "../../../../../AME_NewReports?process_code="+process_code;
	xmlobj_new.open("GET", url, true);
	xmlobj_new.onreadystatechange = function()  
	{
		am_est_fun_process(xmlobj_new,process_code);		 
	}
	xmlobj_new.send(null);
}

function am_est_fun_process(xmlobj,process_code)
{
	if (xmlobj.readyState == 4) 
	{
		if (xmlobj.status == 200) 
		{
			try    
			{
				var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
				 
				if (parseInt(process_code)==1)
				{
					
				}
			}catch(e)	 {}
				
		}  
	}
}
