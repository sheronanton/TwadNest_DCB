function getTransport()
{
 var req = false;
 try 
 {
       req= new ActiveXObject("Msxml2.XMLHTTP");
 }
 catch (e) 
 {
       try 
       {
            req = new ActiveXObject("Microsoft.XMLHTTP");
       }
       catch (e2) 
       {
            req = false;
       }
 }
 if (!req && typeof XMLHttpRequest != 'undefined') 
 {
       req = new XMLHttpRequest();
 }   
 return req;
}

function load_Minor()
{
	//alert("Hai.....");
	 var url="";
	var major_id=document.getElementById("major").value;
	var issue_id=document.getElementById("cmbissue_id").value;
	url="../../../Servlet_Load_Minor?cammand=load_Minor&major_id="+major_id+"&issue_id="+issue_id;
    //alert("URL ==="+url);
    var req=getTransport();
    req.open("Get",url,true);
    req.onreadystatechange=function()
    {
    	process_response(req);
    
    };
    req.send(null);
	
	
}

function process_response(req)
{
	//alert("kkkkkkkkkkkkkkk");
	 if(req.readyState==4)
	    {
	        if(req.status==200)
	        {
	        	 var response=req.responseXML.getElementsByTagName("response")[0];
	        	// alert(req.responseText);
	        	// alert(response);
	        	 var command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	        	// alert("Command ---- "+command);
	        	 
	        	 
	        	 if(command=="update")
        		 {
	        		 var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
	        		 if(flag=="success")
     		 		{
	        			 alert("Record is Updated");
	        			 clearing_values();
     		 		}
	        		 else
	        			 {
	        			 	alert("Record is Not Updated");
	        			 }
        		 }
	        	 
	        	 
		        	 if(command=="load_Minor")
		        		 {
		        		 	var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
		        		 	var flag1=response.getElementsByTagName("flag1")[0].firstChild.nodeValue;
		        		 	//alert(flag+"   "+flag1);
		        		 	if(flag=="success")
		        		 		{
		        		 		var MINOR_SYSTEM_ID_1;
	        		 			var MINOR_SYSTEM_DESC_1;
		        		 		if(flag1=="success1")
		        		 			{
		        		 				MINOR_SYSTEM_ID_1=response.getElementsByTagName("MINOR_SYSTEM_ID_1")[0].firstChild.nodeValue;
		        		 				MINOR_SYSTEM_DESC_1=response.getElementsByTagName("MINOR_SYSTEM_DESC_1")[0].firstChild.nodeValue;
		        		 				//alert(MINOR_SYSTEM_ID_1);
		        		 				//alert(MINOR_SYSTEM_DESC_1);
		        		 			}
		        		 			
		        		 		
		        		 		var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
		        		 		count=count+1;
		        		 		//alert("count --- "+count);
		        		 		 var length=document.getElementById("minor").options.length;
		         					var optionss=document.getElementById("minor");
		                 	 	 	for(var i=length-1;i>=0;i--)
		                 	 	 		{
		                 	 	 			optionss.remove(i);
		                 	 	 		}
		                 	 	 	var listOpt=document.createElement("option");
		         					optionss.length=0;
		         					optionss.appendChild(listOpt);
		         					listOpt.text="--Minor Id--";
		         					listOpt.value="0";
		        		 		for(var i=0;i<=count;i++)
		        		 			{
		        		 			
		        		 			
			        		 			//var minor=document.getElementById("minor");
			        		 			var MINOR_SYSTEM_ID=response.getElementsByTagName("MINOR_SYSTEM_ID")[i].firstChild.nodeValue;
			        		 			var MINOR_SYSTEM_DESC=response.getElementsByTagName("MINOR_SYSTEM_DESC")[i].firstChild.nodeValue;
			        		 			
			        		 			
			        		 			if(MINOR_SYSTEM_ID_1==MINOR_SYSTEM_ID)
			                			{
			                				var optionss=document.getElementById("minor");
			                				optionss.length=i+1;
			     							var listOpt=document.createElement("option");
			     							optionss.appendChild(listOpt);
			     							listOpt.text=MINOR_SYSTEM_DESC;
			     							listOpt.value=MINOR_SYSTEM_ID;
			     							listOpt.selected="selected";
			     							//load_Minor();
			     					
			                			}
			                		else
			                			{
				                			var optionss=document.getElementById("minor");
				            				optionss.length=i+1;
				 							var listOpt=document.createElement("option");
				 							optionss.appendChild(listOpt);
				 							listOpt.text=MINOR_SYSTEM_DESC;
				 							listOpt.value=MINOR_SYSTEM_ID;
				 							//load_Minor();
			                			}
			         					
			         					
			         					
//			         					optionss.length=i;
//			     							var listOpt=document.createElement("option");
//			     							optionss.appendChild(listOpt);
//			     							listOpt.text=MINOR_SYSTEM_DESC;
//			     							listOpt.value=MINOR_SYSTEM_ID;
			     					
			         					
			         					//document.getElementById("minor").value=listOpt;
		        		 			}
		        		 		}
		        		 }
	             
	        }
	    }   
}

function updating_function()
{
	var issue_id=document.getElementById("cmbissue_id").value;
	if(issue_id!="0")
		{
			var url="";
			var major_id=document.getElementById("major").value;
			var minor_id=document.getElementById("minor").value;
			if((major_id!='0'))
				{
				
				if((minor_id!='0'))
				{
					url="../../../Servlet_Load_Minor?cammand=update&major_id="+major_id+"&minor_id="+minor_id+"&issue_id="+issue_id;
		    //alert("URL ==="+url);
					 var req=getTransport();
					    req.open("Get",url,true);
					    req.onreadystatechange=function()
					    {
					    	process_response(req);
					    
					    };
					    req.send(null);
				}
				else
				{
					alert("Please Select Minor Id");
				}	
				
				}
			
			
			else
				{
					alert("Please Select Major Id");
				}
		   
			
		}
	else
	{
		alert("Please Select Issue Id");
	}
}


function clearing_values()
{
	
	  document.frmHelp.cmbissue_id.value="0";
	  document.frmHelp.txtofficename.value="";
	  document.frmHelp.txtUserName.value="";
	  document.frmHelp.major.value="0";
	  document.frmHelp.minor.value="0";
	  document.getElementById('minor').length=1;
	  document.frmHelp.status.value="";
	
	  document.frmHelp.txtpriority.value="";
	  document.frmHelp.txtdate.value="";
	  document.frmHelp.txtSubject.value="";
	  document.frmHelp.txtdesc.value="";
	  
	  document.frmHelp.txtHUserName.value="";
	
	  
	  
	  
	  document.frmHelp.txtsolution.value="";
	  
	  
}