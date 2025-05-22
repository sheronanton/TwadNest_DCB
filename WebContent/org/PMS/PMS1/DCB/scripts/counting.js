

/* file : counting.js
 * ProcessCode 	Function 
 * 1			sub div wise pr total 
 * 
 * 
 */
function sub_div_wise_pr_total_req(process_code)
{
	var url="";
	var office_id="";
	var month="";
	var year="";
	var sub_div_id="";
	var process_code=process_code;
	var schsno="0";
	try
	{
		
		office_id=document.getElementById("officeid").value;
		month=document.getElementById("fmonth").value;
		year=document.getElementById("fyear").value;
		try { sub_div_id=document.getElementById("subdiv").value }catch(e) {};
		try { schsno=document.getElementById("sch_load").value }catch(e) {};
	 
		url="../../../../../Counting?office_id="+office_id+"&month="+month;
		url+="&year="+year+"&sub_div="+sub_div_id+"&process_code="+process_code+"&schsno="+schsno;
		var xmlobj = createObject();  
		xmlobj.open("GET", url, true); 
		xmlobj.onreadystatechange = function() {
			sub_div_wise_pr_total_req_process(xmlobj,process_code);  
		}

		xmlobj.send(null);
	}catch(e){
		
	}
}


function sub_div_wise_pr_total_req_process(xmlobj_,process_code)
{
	if (xmlobj_.readyState == 4) 
	{

		if (xmlobj_.status == 200) 
		{
			if (process_code=="1" || process_code=="2")
			{
				var m=(process_code=="1") ? "Sub Division" : "Scheme"; 
				var BR=xmlobj_.responseXML.getElementsByTagName("result")[0];
				var read=BR.getElementsByTagName("reading")[0].firstChild.nodeValue;;
				document.getElementById("data_show").innerHTML="Selected "+m+" PR Qty  : <font color='red'><b>"+read+" (KL)</b></font>";
			}
		}
		
	}
		 
}