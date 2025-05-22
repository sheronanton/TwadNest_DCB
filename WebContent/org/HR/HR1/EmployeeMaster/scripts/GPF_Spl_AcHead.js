 var xmlhttp;
 
 function clearAll(){
	 document.getElementById("ac_head").value="";
	 document.getElementById("txtDescription").value="";
	 document.getElementById("add").disabled=false;
	 document.getElementById("update").disabled=true;
 }
function headcode(){
	
	var ac_head=document.getElementById("ac_head").value;
	var len=ac_head.length;
	if(isNaN(ac_head)){
		alert("AccountHeadcode Should be Number");
		document.getElementById("ac_head").value="";
		
	}
	else if(len>6){
		alert("Account Head Code should be within 6 digits");
	}
	else if(ac_head=="" || ac_head==null){
		alert("Account Head Code Should be enter");
	}
	
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

function call(command){
	xmlhttp=getxmlhttpObject();
	if(xmlhttp==null){
	    alert("Your browser doesnot support AJAX");
	    return;
	}   
    if(command=="Add"){ 
      
    	var ac_head=document.getElementById("ac_head").value;
    	var desc=document.getElementById("txtDescription").value;
        url="../../../../../GPF_Special_AChead?command=Add&ac_head="+ac_head+"&desc="+desc;
        alert(url);
        url=url+"&sid="+Math.random();
    	xmlhttp.open("GET",url,true);
    	xmlhttp.onreadystatechange=function()
    	{
    		stateChanged(xmlhttp);
    	}
    	xmlhttp.send(null);  
    }
    if(command=="get")
    { 
    	url="../../../../../GPF_Special_AChead?command=get";
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
        
    	var ac_head=document.getElementById("ac_head").value;
    	var old_ac_head=document.getElementById("old_ac_head").value;
    	var new_desc=document.getElementById("txtDescription").value;
        url="../../../../../GPF_Special_AChead?command=Update&ac_head="+ac_head+"&old_ac_head="+old_ac_head+"&new_desc="+new_desc;
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
	                    var len=response.getElementsByTagName("achead").length;
	                   // alert("len"+len);
	                   var seq1=0;
	                    for(var i=0;i<len;i++)
	                    {
	                    	//alert("************");
	                    	var serial_no=response.getElementsByTagName("serial_no")[i].firstChild.nodeValue;
		                    var achead=response.getElementsByTagName("achead")[i].firstChild.nodeValue;
		                    var desc=response.getElementsByTagName("desc")[i].firstChild.nodeValue;
		                  /*var updated_by=response.getElementsByTagName("updated_by")[i].firstChild.nodeValue;
		                    var updated_date=response.getElementsByTagName("updated_date")[i].firstChild.nodeValue;*/
	                    
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
	                     
	                    var tdg=document.createElement("TD");
	                    var serialno=document.createTextNode(serial_no);
	                    tdg.appendChild(serialno);
	                    tr.appendChild(tdg);
	                    
	                    var tdg=document.createElement("TD");
	                    var achead1=document.createTextNode(achead);
	                    tdg.appendChild(achead1);
	                    tr.appendChild(tdg); 
	                    
	                    var tdg=document.createElement("TD");
	                    var desc1=document.createTextNode(desc);
	                    tdg.appendChild(desc1);
	                    tr.appendChild(tdg); 
	
	                   /* var tdg=document.createElement("TD");
	                    var updated_by1=document.createTextNode(updated_by);
	                    tdg.appendChild(updated_by1);
	                    tr.appendChild(tdg); 
	
	                    var tdg=document.createElement("TD");
	                    var updated_date1=document.createTextNode(updated_date);
	                    tdg.appendChild(updated_date1);
	                    tr.appendChild(tdg); */
	                    
	 					tlist.appendChild(tr);                     
	                    seq1++;
	                    }
                	}
                	catch(e){ //alert("Error Here"+e);
                	}
            }
         }
    

       else if(command=="Update"){
           
    	   if(flag=='success')
           {
    		   call('get');
               alert("Update Successfully");
              
           }
       }
    }
  } 
}


function pick(i){
	  	var emp_id=document.getElementById(i); 
	  	var rcells=emp_id.cells;
   		var serial_no=rcells.item(1).firstChild.nodeValue;
   		var ac_head=rcells.item(2).firstChild.nodeValue;
   		var desc=rcells.item(3).firstChild.nodeValue;
    	/*var updated_by=rcells.item(2).lastChild.nodeValue.split("-");
    	var updated_date=rcells.item(3).lastChild.value;*/
	    document.getElementById("ac_head").value=ac_head;
	    document.getElementById("old_ac_head").value=ac_head;
	    document.getElementById("txtDescription").value=desc;
	    document.getElementById("add").disabled=true;
	    document.getElementById("update").disabled=false;
}