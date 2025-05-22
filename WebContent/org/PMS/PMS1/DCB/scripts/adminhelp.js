

function help(a)
{ 
	
	 var xmlobj=createObject();  
	 
	 var url="../../../../../adminhelp?command=help&tablename="+a.value;
	 
	 if (a.value!="")
	 { 
		 document.getElementById("tt").innerHTML="<font Color='red' size=2>" + a.value +" </font>";
		 xmlobj.open("GET",url,true);
	     xmlobj.onreadystatechange=function()
	     {
	    	 help_process(xmlobj);  
	     }
	     xmlobj.send(null);  
	 }
	 
}
function help_process(xmlobj)
{
	 if (xmlobj.readyState==4)
	   { 
		  
	   if (xmlobj.status==200)
	     {  
	         
	        	 help_result(xmlobj);               
	     }
	   }
}

function help_result(xmlobj)
{
	try {
		var bR = xmlobj.responseXML.getElementsByTagName("response")[0];
		document.getElementById("column").options.length = 0; 
		var len = bR.getElementsByTagName("columnname").length;
		for (i = 0; i < parseInt(len); i++) {
			 
			var name = bR.getElementsByTagName("columnname")[i].firstChild.nodeValue;

			addOption(document.getElementById("column"), name, name)
		}
	} catch (e) {
		 
	}
}