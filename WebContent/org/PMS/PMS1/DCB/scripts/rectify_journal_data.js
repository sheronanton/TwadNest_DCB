function process(flag)
{
	 	var type=0;
	 	var ben2_sno=0;
	 	var month=0;
	 	var year=0;
	 	var ben_sno=0;
	 	try
	 	{ 
	 		 
	 		type=document.getElementById("type").value;
	 		month=document.getElementById("pmonth").value;
	 		year=document.getElementById("pyear").value;
	 		ben_sno=document.getElementById("ben_name").value;
	 	}catch(e) {} 
		var obj=createObject();   
		var url="../../../../../rectify_journal_data?flag="+flag+"&type="+type+"&ben_sno="+ben_sno+"&year="+year+"&month="+month;
		obj.open("GET",url,true);
		obj.onreadystatechange=function ()
		{ 
			process_result(obj,flag);
		}			  
		obj.send(null);
		 
} 

function process_result(obj,flag)
{
	 if (obj.readyState==4)
	 {	   
		if (obj.status==200)
	    {
			if (flag==1)
			{
				var bR = obj.responseXML.getElementsByTagName("result")[0];
				document.getElementById('ben_name').options.length = 0;
				var len = bR.getElementsByTagName("BENEFICIARY_NAME").length;
				addOption(document.getElementById('ben_name'), "select",0);
				 
				for (i = 0; i < len; i++) {
					var sno = bR.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
					var name = bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
					addOption(document.getElementById('ben_name'), name, sno);
				}
			}if (flag==2)
			{
				var bR = obj.responseXML.getElementsByTagName("result")[0];
				var len = bR.getElementsByTagName("sch_name").length;
				var tbody = document.getElementById("tbody");
				var table = document.getElementById("ttable");
				var t=0;
				for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
				for (i = 0; i < len; i++) 
				{
					var new_row=cell("TR","",i,"");
					var sch_name = bR.getElementsByTagName("sch_name")[i].firstChild.nodeValue;
					var tot_read = bR.getElementsByTagName("tot_read")[i].firstChild.nodeValue;
					var metre_location= bR.getElementsByTagName("metre_location")[i].firstChild.nodeValue;
					var beneficiary_name= bR.getElementsByTagName("beneficiary_name")[i].firstChild.nodeValue;
					var old_amount=bR.getElementsByTagName("old_amount")[i].firstChild.nodeValue;
					var new_amount=bR.getElementsByTagName("new_amount")[i].firstChild.nodeValue;
					var beneficiary_cell=cell("TD","label","","",beneficiary_name,7,"tdText","font-size:2","left","25%","","","");
					var metre_location_cell=cell("TD","label","","",metre_location,7,"tdText","font-size:2","left","25%","","","");
		            var name_cell=cell("TD","label","","",sch_name,7,"tdText","font-size:2","left","25%","","","");
		            var tot_read_cell=cell("TD","label","","",tot_read,7,"tdText","font-size:2","right","25%","","","");
		            var old_amount_cell=cell("TD","label","","",old_amount,7,"tdText","font-size:2","right","25%","","","");
		            var new_amount_cell=cell("TD","label","","",new_amount,7,"tdText","font-size:2","right","25%","","","");
		            
		            var difference_amount_cell=cell("TD","label","","",(parseFloat(new_amount)-parseFloat(old_amount)),7,"tdText","font-size:2","right","25%","","","");
		            new_row.appendChild(beneficiary_cell);
		            new_row.appendChild(metre_location_cell);
		            new_row.appendChild(name_cell);
		            new_row.appendChild(tot_read_cell);
		            new_row.appendChild(old_amount_cell);
		            new_row.appendChild(new_amount_cell);
		            new_row.appendChild(difference_amount_cell);
		            tbody.appendChild(new_row);
				}
			}
	    }
	 }
}