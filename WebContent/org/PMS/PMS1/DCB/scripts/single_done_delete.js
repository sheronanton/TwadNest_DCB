function delete_fun(flag)
{
	var dist1=0;
 	var btype=0;
 	var block=0;
 	var bensno=0;
 	var pmonth=0;
 	var pyear=0;
 	try
 	{
 		dist1=document.getElementById("District").value;
 	}catch(e){}
 	try
 	{
 		block=document.getElementById("block").value;
 	}catch(e){}
 	try
 	{
 		btype=document.getElementById("btype").value;
 	}catch(e){}
 	try
 	{
 		bensno=document.getElementById("ben_name").value;
 		pmonth=document.getElementById("fmonth").value;
 		pyear=document.getElementById("fyear").value;
 	}catch(e)
 	{
 	}
 	
 	if (flag!=5)
 	{
		var obj=createObject();  
		var url="../../../../../single_done_delete?pmonth="+pmonth+"&pyear="+pyear+"&bensno="+bensno+"&type="+flag+"&District="+dist1+"&block="+block+"&btype="+btype;
		obj.open("GET",url,true);
		obj.onreadystatechange=function ()
		{ 
			delete_process(obj,flag);
		}			
		obj.send(null);
 	}else
 	{
 		var res=report_period_verify_2(document.getElementById('fmonth'),document.getElementById('fyear'));
 		var res1=month_year_check(pmonth,pyear);
 		 
 			if (res1!=1)
 			{
 				if (res!=1)  
 				{
				var obj=createObject();  
				var url="../../../../../single_done_delete?pmonth="+pmonth+"&pyear="+pyear+"&bensno="+bensno+"&type="+flag+"&District="+dist1+"&block="+block+"&btype="+btype;
				obj.open("GET",url,true);
				obj.onreadystatechange=function ()
				{ 
					delete_process(obj,flag);
				}			
				obj.send(null);
 			}
 		}
 	}
}
function delete_process(xmlobj,type)
{
	 if (xmlobj.readyState==4)
	 {	   
		if (xmlobj.status==200)
	    {
			if (type==1)
			{
					var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
					document.getElementById('District').options.length = 0;
					var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
					addOption(document.getElementById('District'), "select",0);
					for (i = 0; i < len; i++)
					{
						var sno = bR.getElementsByTagName("DISTRICT_CODE")[i].firstChild.nodeValue;
						var name = bR.getElementsByTagName("DISTRICT_NAME")[i].firstChild.nodeValue;
						addOption(document.getElementById('District'), name, sno);
					}
			
			}if (type==2)
			{
				var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
				document.getElementById('block').options.length = 0;
				var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				addOption(document.getElementById('block'), "select",0);
				for (i = 0; i < len; i++)
				{
					var sno = bR.getElementsByTagName("BLOCK_SNO")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BLOCK_NAME")[i].firstChild.nodeValue;
					addOption(document.getElementById('block'), name, sno);
				}
			}if (type==3)
			{
				var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
				document.getElementById('btype').options.length = 0;
				var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				addOption(document.getElementById('btype'), "select",0);
				for (i = 0; i < len; i++)
				{
					var sno = bR.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
					addOption(document.getElementById('btype'), name, sno);  
				}
			}
			if (type==4)
			{ 
				var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
				document.getElementById('ben_name').options.length = 0;
				var len = bR.getElementsByTagName("row_count")[0].firstChild.nodeValue;
				addOption(document.getElementById('ben_name'), "select",0);
				for (i = 0; i < len; i++)
				{
					var sno = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
					addOption(document.getElementById('ben_name'), (i+1)+" ) "+name, sno);  
				}
			}
			if (type==5)
			{
				var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
				document.getElementById('data').innerHTML=bR.getElementsByTagName("data")[0].firstChild.nodeValue;
				document.getElementById('data2').innerHTML=bR.getElementsByTagName("met_data")[0].firstChild.nodeValue;
			}
			  
	    }  
	   
	 }
}
			 