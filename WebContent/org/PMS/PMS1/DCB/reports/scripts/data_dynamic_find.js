//var xmlobj_new=createObject();
var k=0;
function divisions(office_id,month,year)  
{
	document.getElementById("msg").innerHTML="<font color='red'>Process.....</font>";
	var url="";
	url = "../../../../../../data_dynamic_find?month="+month+"&year="+year+"&process_code=1&office_id="+office_id;
	var xmlobj_new=createObject();
	xmlobj_new.open("GET", url, true);  
	xmlobj_new.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	xmlobj_new.send(null);
	xmlobj_new.onreadystatechange = function() 
	{
		division_show(xmlobj_new,office_id);
	}
}
function start()
{
	document.getElementById("msg").innerHTML="<font color='red'>Process.......</font>";
	load();
	
}
function load()
{
	var i=1;    
	var total_div=document.getElementById("totalrow").value;  
	var month=document.getElementById("month").value;  
	var year=document.getElementById("year").value;  
	for(;i<=total_div;i++)
	{
		var office_id=document.getElementById("oid"+i).value;
		divisions(office_id,month,year);
	}  
	document.getElementById("msg").innerHTML=" "; 
	//setTimeout(start(),1000);
}
function division_show(xmlobj_new,office_id)
{
	try 
	{  
		if (xmlobj_new.readyState == 4) 
		{
			if (xmlobj_new.status == 200) 
			{
				try 
				{
				   
					var bR = xmlobj_new.responseXML.getElementsByTagName("result")[0];
					var pumping_qty_CR=xmlobj_new.responseXML.getElementsByTagName("pumping_qty_CR")[0].firstChild.nodeValue;
					var pumping_qty_V=xmlobj_new.responseXML.getElementsByTagName("pumping_qty_V")[0].firstChild.nodeValue;
					var pumping_qty_FR=xmlobj_new.responseXML.getElementsByTagName("pumping_qty_FR")[0].firstChild.nodeValue;
					var jrpost=xmlobj_new.responseXML.getElementsByTagName("jrpost")[0].firstChild.nodeValue;
					var ltcount=xmlobj_new.responseXML.getElementsByTagName("ltcount")[0].firstChild.nodeValue;
					var dmdpost=xmlobj_new.responseXML.getElementsByTagName("dmdpost")[0].firstChild.nodeValue;
					var DCB_Freeze_msg=xmlobj_new.responseXML.getElementsByTagName("DCB_Freeze_msg")[0].firstChild.nodeValue;
					var old_data_cr=document.getElementById("cr"+office_id).innerHTML;
					var old_data_vl=document.getElementById("vl"+office_id).innerHTML;
					var old_data_fr=document.getElementById("fr"+office_id).innerHTML;
					var old_data_bd=document.getElementById("bd"+office_id).innerHTML;
					var font_color_cr="#fcffcc";
					var font_color_vl='#fcffcc';
					var font_color_fr='#fcffcc';
					var rowcolor='#ffffcc'; 
					if (old_data_cr==pumping_qty_CR && old_data_fr==pumping_qty_FR && old_data_bd==dmdpost && old_data_vl==pumping_qty_V )
					{
				 
						rowcolor="white";
					}else
					{ 
						rowcolor="#ffffcc";
					}
					font_color_cr="white";  
				 
					document.getElementById("cr"+office_id).innerHTML=pumping_qty_CR;
					document.getElementById("vl"+office_id).innerHTML=pumping_qty_V;
					document.getElementById("fr"+office_id).innerHTML=pumping_qty_FR;
					document.getElementById("jr"+office_id).innerHTML=jrpost;
					document.getElementById("bd"+office_id).innerHTML=dmdpost;
					document.getElementById("lg"+office_id).innerHTML=ltcount;
					document.getElementById("recfr"+office_id).innerHTML=DCB_Freeze_msg;
				//	document.getElementById("cr"+office_id).style.backgroundColor=font_color_cr;
				//	document.getElementById("vl"+office_id).style.backgroundColor=font_color_vl;
				//	document.getElementById("fr"+office_id).style.backgroundColor=font_color_fr;
					document.getElementById("row"+office_id).style.backgroundColor=rowcolor;  
					var t = 0;  
				}catch(e)
				{
					 
				}
			}
		}
	}catch(e) {
		 
	}
	
	
}