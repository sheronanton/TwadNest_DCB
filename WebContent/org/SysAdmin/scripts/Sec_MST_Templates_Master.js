 var xmlhttp;
 
 function nullCheck()
 {
	         if(document.Sec_MST_Templates.role_template_name.value==null ||document.Sec_MST_Templates.role_template_name.value=="")
             {
             alert("Must Type Template Name Value ");
                       
             return 0;
             }
     return 1;

 }
 
function getxmlhttpObject()
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

function clearAll(){
	 document.getElementById("role_template_name").value="";
	 document.getElementById("role_template_id").value="";
}

function call(command){
	
	xmlhttp=getxmlhttpObject();
	if(xmlhttp==null){
	    alert("Your browser doesnot support AJAX");
	    return;
	}   
    if(command=="Add"){ 
    	if(nullCheck()){
    	var element_name=document.getElementById("role_template_name").value;
    	url="../../../Sec_MST_Templates_Master?command=Add&template_name="+element_name;
        url=url+"&sid="+Math.random();
    	xmlhttp.open("GET",url,true);
    	xmlhttp.onreadystatechange=function()
    	{
    		stateChanged(xmlhttp);
    	}
    	xmlhttp.send(null); 
    	}
    }
    if(command=="get")
    { 
    	
    	document.getElementById("add").disabled=false;
    	document.getElementById("update").disabled=true;
    	clearAll();    	
    	url="../../../Sec_MST_Templates_Master?command=get";
        url=url+"&sid="+Math.random();
    	xmlhttp.open("GET",url,true);
    	xmlhttp.onreadystatechange=function()
    	{
    		stateChanged(xmlhttp);
    	}
    	xmlhttp.send(null);  
    }

    if(command=="Update")
    {      
    	var template_id=document.getElementById("role_template_id").value;
    	var template_name=document.getElementById("role_template_name").value;
    	url="../../../Sec_MST_Templates_Master?command=Update&template_id="+template_id+"&template_name="+template_name;
        url=url+"&sid="+Math.random();
    	xmlhttp.open("GET",url,true);
    	xmlhttp.onreadystatechange=function()
    	{
    		stateChanged(xmlhttp);
    	}
    	xmlhttp.send(null);  
    }
    
    if(command=="Validate")
    {      
    	var template_id=document.getElementById("role_template_id").value;
    	var template_name=document.getElementById("role_template_name").value;
    	url="../../../Sec_MST_Templates_Master?command=Validate&template_id="+template_id+"&template_name="+template_name;
        url=url+"&sid="+Math.random();
    	xmlhttp.open("GET",url,true);
    	xmlhttp.onreadystatechange=function()
    	{
    		stateChanged(xmlhttp);
    	}
    	xmlhttp.send(null);  
    }
    
    if(command=="Delete")
    { 
    	var template_id=document.getElementById("role_template_id").value;
    	 url="../../../Sec_MST_Templates_Master?command=Delete&template_id="+template_id;
    	 url=url+"&sid="+Math.random();
     	xmlhttp.open("GET",url,true);
     	xmlhttp.onreadystatechange=function()
     	{
     		stateChanged(xmlhttp);
     	}
     	xmlhttp.send(null);  
    }
}

function stateChanged(xmlhttp)
{
    var flag,command,response;
  
   
    if(xmlhttp.readyState==4)
    {
       if(xmlhttp.status==200)
       {
    	    response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
            if(command=="Add")
            {
                if(flag=='success')
                {
                	call('get');
                    alert("Added Successfully");                   
                }
            }
            else if(command=="get"){
            	if(flag=='success')
                {
            		try{
                		var tlist=document.getElementById("tlist");
	                    while (tlist.childNodes.length > 0) {
	                   	 tlist.removeChild(tlist.firstChild);
	                    }
	                    var len=response.getElementsByTagName("template_id").length;
	                   
	                    var seq1=0;
	                    for(var i=0;i<len;i++)
	                    {
	                    	var template_idd=response.getElementsByTagName("template_id")[i].firstChild.nodeValue;
		                    var template_namee=response.getElementsByTagName("template_name")[i].firstChild.nodeValue;
		                    var tr=document.createElement("TR");
		    	 			tr.id=seq1;
	          
	                    var td=document.createElement("TD");
	                    var anc=document.createElement("A");
	                    var url="javascript:pick('"+seq1+"')";
	                    anc.href=url;
	                   
		                var edit=document.createTextNode("Edit");
		                anc.appendChild(edit);
		                td.appendChild(anc);
	                    tr.appendChild(td);
	               
	                    var td1=document.createElement("TD");
	                    var template_id=document.createTextNode(template_idd);
	                    td1.appendChild(template_id);
	                    tr.appendChild(td1); 

	                 /*   var hab=document.createElement("TEXT");
	                    hab.type="hidden";
	                    hab.name="old_element_id";
	                    hab.value=template_idd;
	                    td1.appendChild(hab);*/
	                    
	                    var td2=document.createElement("TD");
	                    var template_name=document.createTextNode(template_namee);
	                    td2.appendChild(template_name);
	                    tr.appendChild(td2); 
	                    
	                    tlist.appendChild(tr);                     
	                    seq1++;
	                    }
                	}
                	catch(e){ //alert("Error Here"+e);
                	}
            }
         }
    

       else if(command=="update"){           
    	   if(flag=='success')
           {
    		   alert("Update Successfully");
    		   call('get');              
           }
       }
       else if(command=="validate"){           
    	   if(flag=='success')
           {
    		   alert("Successfully Validate");
    		   call('get');              
           }
       }
       else if(command=="delete"){
    	   if(flag=='success')
           {
    		   alert("Deleted Successfully");
    		   call('get');
           }
       }
    }
  } 
}
function pick(i){
	  	var element_id=document.getElementById(i); 
	  	var rcells=element_id.cells;
   		var template_id=rcells.item(1).firstChild.nodeValue;
   		var template_name=rcells.item(2).firstChild.nodeValue;   		
   		document.getElementById("role_template_id").value=template_id;
   		document.getElementById("role_template_name").value=template_name;
	    document.getElementById("add").disabled=true;
	    document.getElementById("update").disabled=false;
	    document.getElementById("delete").disabled=false;
	   
}