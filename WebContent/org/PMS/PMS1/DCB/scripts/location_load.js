function createObject2()
{	  
	try
	{
	var xmlHttp = false;
	
	
	 
	   try {
		   xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")   // For Microsoft IE 6.0+
		  
	    }
	   catch (e) {
		 
			     try {
			    	 xmlHttp = new ActiveXObject("Microsoft.XMLHTTP") 
			     	}
			     	catch (e2){	    	   	 
			     		xmlHttp = false;   // No Browser accepts the XMLHTTP Object then false
	       
	       try {
	    	   xmlHttp= new ActiveXObject("MSXML2.XMLHTTP.3.0");
	        }
	        catch(ex) {
	        	 

	        }

	       
	     }
	   }
	   if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
	     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
	   }
	
	   
	   
	  
	   return xmlHttp;  // Mandatory Statement returning the ajax object created

	}catch(e)
	{
		 try { return new ActiveXObject("Msxml2.XMLHTTP.6.0"); }catch (e) {
			 alert("XML Object Not Created\n--------------------------")
		}

		
	}
	
}


function loc2()	
	{
		var obj3=createObject2();
		
		process_code=35;
		var  div=document.getElementById("asdiv2").value;
		
		var url="../../../../../SourceChange?process_code="+process_code+"&div="+div;
		 
			
			 try
			 {
				obj3.open("GET",url,true);
				 
			
			
			
			obj3.onreadystatechange=function() { loction2(obj3);};	
			obj3.send(null);
			 }catch(e) {alert("TEST" + e);}
	}
	
	function loction2(obj_)
	{
		 
			if(obj_.readyState==4)  
			{  
				 
			 	if(obj_.status==200)
			   	 {
			 		 
			   	   
			   		var tbody = document.getElementById("entred_body2");
			   		var table = document.getElementById("entred_data2");
			   		var t=0;
			   		for(t=tbody.rows.length-1;t>=0;t--)
			   		 {
			   			tbody.deleteRow(0);
			   		 }
		   			var bR = obj_.responseXML.getElementsByTagName("result")[0];
					var len = bR.getElementsByTagName("METRE_SNO").length;

					 
				 
					for (var i=0;i<len;i++)
			   		{	
						var new_row = cell("TR", "", "", "mrow" + (i + 1), (i + 1), 2, "","", "", "", "", "");
			   			var METRE_SNO=xmlValue(bR, "METRE_SNO", i);
			   			var sch_name=xmlValue(bR, "sch_name", i);
			   			var BENEFICIARY_NAME=xmlValue(bR, "BENEFICIARY_NAME", i);
			   			var METRE_FIXED=xmlValue(bR, "METRE_FIXED", i);
			   			var METRE_WORKING=xmlValue(bR, "METRE_WORKING", i);
			   			var METRE_LOCATION=xmlValue(bR, "METRE_LOCATION", i);
			   			
			   			var sno_cell=cell("TD","label","","",(i+1),5,"","","","3%","center","","");
			   		    var BENEFICIARY_NAME_cell=cell("TD","label","","BENEFICIARY_NAME"+(i+1),BENEFICIARY_NAME,5,"ttd2","","","15%","left","","");
			   			var METRE_LOCATION_cell=cell("TD","label","","METRE_LOCATION"+(i+1),METRE_LOCATION,5,"ttd2","","","10%","left","","");
			   			var sch_name_cell=cell("TD","label","","sch_name"+(i+1),sch_name,5,"ttd2","","","10%","left","","");
			   			var METRE_WORKING_cell=cell("TD","label","","METRE_WORKING"+(i+1),METRE_WORKING,5,"ttd2","","","5%","left","","");
			   			var METRE_FIXED_cell=cell("TD","label","","METRE_FIXED"+(i+1),METRE_FIXED,5,"ttd2","","","5%","left","","");
			   			var METRE_SNO_cell = cell("TD", "input", "hidden", "METRE_SNO" + (i + 1), METRE_SNO,7, "ttd2", "", "", "2%", "", "onclick", "");
			   			var DCB_cell = cell("TD", "input", "checkbox", "rchdcb" + (i + 1), 0,7, "ttd2", "", "", "5%", "center", "onclick", "");
			   			
			   			
			   			new_row.appendChild(sno_cell);
			   			 
			   			new_row.appendChild(BENEFICIARY_NAME_cell);
			   			new_row.appendChild(METRE_LOCATION_cell);
			   			new_row.appendChild(sch_name_cell);
			   			new_row.appendChild(METRE_WORKING_cell);
			   			new_row.appendChild(METRE_FIXED_cell);
			   			new_row.appendChild(METRE_SNO_cell);
			   			
			   			
			   			tbody.appendChild(new_row);  
			   		}  
				} 
			}
}
