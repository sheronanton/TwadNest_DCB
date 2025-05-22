var process_code=0;
var sel_type=0;
var url="";
var flag=1;
function load(type,processcode)
{
	process_code=processcode;
	sel_type=type;
	var xmlobj=createObject();
	url="../../../../../../Sch_master_report";
	var params="";
	xmlobj.open("POST",url,true);	
	xmlobj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlobj.setRequestHeader("Content-length", params.length);
	xmlobj.setRequestHeader("Connection", "close");
	 
	if (sel_type=="RN")
	{
		//REQUEST for Region offices
		params="type="+type;
		flag=0;
	}
	else if(sel_type=="CL")
	{
		//REQUEST for circle offices
		var count = 0;
		var roQueryString = "";
		var elements = document.forms[0].elements;
		for(var x = 0; x < elements.length; x++ )
		{
			if(elements[x].type == "checkbox" && elements[x].name == "regionOffices")
			{
				try
				{
					if(elements[x].checked)
					{
						count++;
						if(count == 1)
						{
							roQueryString += elements[x].value+",";
						}
						else
						{
							roQueryString += elements[x].value+",";
						}
					}
				}
				catch(e){}
			}
		}
		if(count > 0)
		{
			params="type="+type+"&roQueryString="+roQueryString;
			flag=0;
		}
		else
		{
			alert("Please Select Some Region Offices");
		}
	}
	else if(sel_type=="DT")
	{
		//Request for Division Types
		params="type="+type;
		flag=0;
	}
	else if(sel_type=="DN")
	{
		//Request for Division Offices
		var count = 0;
		var co = 0;
		var coQueryString = "";
		var otQueryString = "";
		var elements = document.forms[0].elements;
		for(var x = 0; x < elements.length; x++ )
		{
			if(elements[x].type == "checkbox" && elements[x].name == "circleOffices")
			{
				try
				{
					if(elements[x].checked)
					{
						count++;
						co++;
						if(count == 1)
						{
							coQueryString += elements[x].value+",";
						}
						else
						{
							coQueryString += elements[x].value+",";
						}
					}
				}
				catch(e){}
			}
			else if(elements[x].type == "checkbox" && elements[x].name == "officeTypes")
			{
				try
				{
					if(elements[x].checked)
					{
						count++;
						if(count == 1)
						{
							otQueryString += "'"+elements[x].value+"'"+",";
						}
						else
						{
							otQueryString += "'"+elements[x].value+"'"+",";
						}
					}
				}
				catch(e){}
			}
		}
		if(co > 0)
		{
			params="type="+type+"&coQueryString="+coQueryString+"&otQueryString="+otQueryString;
			
			flag=0;
		}
		else
		{
			alert("Please Select Some Circle Offices and Division Types.");
		}
	}

	
	//sending the request
	if (parseInt(flag)==0)
	{
		xmlobj.onreadystatechange=function()
		{
			report_process(xmlobj);
		}
    	xmlobj.send(params);
	}  else
	{
		
		alert("Releated Input Field Missing !")
	}	
}

function report_process(xml_obj)
{
	 
	if (xml_obj.readyState==4)
	 {	   
		 
		if (xml_obj.status==200)
	    {
				report_process_result(xml_obj);
				flag=1;
	    }
	 }
}
function report_process_result(xml_obj)
{

	var bR=xml_obj.responseXML.getElementsByTagName("response")[0];
	
	if(sel_type=="RN")
	{
		var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		
		var table = document.getElementById("tblist1");
		for (var i=0;i<len;i++)
		{ 
			var new_row=cell("TR","","","row","",2,"","","","","","","");
			var OFFICE_ID=xmlValue(bR, "REGION_OFFICE_ID", i);
			var OFFICE_NAME=xmlValue(bR, "REGION_OFFICE_NAME", i);	
			var Chk_cell=cell("TD","input","checkbox","regionOffices",OFFICE_ID,"","","","","2%","","","");
			var off_nam_cell=cell("TD","label","","lbl"+(i+1),OFFICE_NAME,"","","","","","","","");
			
			new_row.appendChild(Chk_cell);
			new_row.appendChild(off_nam_cell);
			table.appendChild(new_row);
			
		}
		load('DT','');
	}
	else if(sel_type=="CL")
	{
		var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		var table = document.getElementById("tblist2");
		var t=0;
		document.getElementById("tblist2").innerHTML="";

		for (var i=0;i<len;i++)
		{ 
			var new_row=cell("TR","","","row","",2,"","","","","","","");
			var CIRCLE_OFFICE_ID=xmlValue(bR, "CIRCLE_OFFICE_ID", i);
			var CIRCLE_OFFICE_NAME=xmlValue(bR, "CIRCLE_OFFICE_NAME", i);	
			var Chk_cell=cell("TD","input","checkbox","circleOffices",CIRCLE_OFFICE_ID,"","","","","2%","","","");
			var off_nam_cell=cell("TD","label","","lbl"+(i+1),CIRCLE_OFFICE_NAME,"","","","","","","","");

			new_row.appendChild(Chk_cell);
			new_row.appendChild(off_nam_cell);
			table.appendChild(new_row);

		}
	}
	else if(sel_type=="DT")
	{

		var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		var table = document.getElementById("tblist3");
		var t=0;

		for (var i=0;i<len;i++)
		{ 
			var new_row=cell("TR","","","row","",2,"","","","","","","");
			var WORK_NATURE_ID=xmlValue(bR, "WORK_NATURE_ID", i);
			var WORK_NATURE_DESC=xmlValue(bR, "WORK_NATURE_DESC", i);	
			var Chk_cell=cell("TD","input","checkbox","officeTypes",WORK_NATURE_ID,"","","","","2%","","","");
			var off_nam_cell=cell("TD","label","","lbl"+(i+1),WORK_NATURE_DESC,"","","","","","","","");

			new_row.appendChild(Chk_cell);
			new_row.appendChild(off_nam_cell);
			table.appendChild(new_row);

		}
	}
	else if(sel_type=="DN")
	{

		var len=xml_obj.responseXML.getElementsByTagName("row_count")[0].firstChild.nodeValue;
		var table = document.getElementById("tblist4");
		var t=0;

		for (var i=0;i<len;i++)
		{ 
			var new_row=cell("TR","","","row","",2,"","","","","","","");
			var DIVISION_OFFICE_ID=xmlValue(bR, "DIVISION_OFFICE_ID", i);
			var DIVISION_OFFICE_NAME=xmlValue(bR, "DIVISION_OFFICE_NAME", i);	
			var Chk_cell=cell("TD","input","checkbox","divisionOffices",DIVISION_OFFICE_ID,"","","","","2%","","","");
			var off_nam_cell=cell("TD","label","","lbl"+(i+1),DIVISION_OFFICE_NAME,"","","","","","","","");

			new_row.appendChild(Chk_cell);
			new_row.appendChild(off_nam_cell);
			table.appendChild(new_row);

		}
	}
}
function Select(no)
{
	var chkName = "";
	if(no == 1 || no == 2)
	{
		chkName = "regionOffices";
	}
	else if(no == 3 || no == 4)
	{
		chkName = "circleOffices";
	}
	else if(no == 5 || no == 6)
	{
		chkName = "officeTypes";
	}
	else if(no == 7 || no == 8)
	{
		chkName = "divisionOffices";
	}
	
	if(no % 2 == 1)
	{
		selectAll(chkName);
	}
	else
	{
		selectNone(chkName);
	}
}
function selectAll(chkName)
{
	var elements = document.forms[0].elements;
	for(var x = 0; x < elements.length; x++ )
	{
		if(elements[x].type == "checkbox" && elements[x].name == chkName)
		{
			try
			{
				elements[x].checked = true;
			}
			catch(e){}
		}
	}
}

function selectNone(chkName)
{
	var elements = document.forms[0].elements;
	for(var x = 0; x < elements.length; x++ )
	{
		if(elements[x].type == "checkbox" && elements[x].name == chkName)
		{
			try
			{
				elements[x].checked = false;
			}
			catch(e){}
		}
	}
}
