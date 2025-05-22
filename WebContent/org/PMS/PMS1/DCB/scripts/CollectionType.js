

var CONS = 'Get';
var selSno = 0;

/////////////////////////////////////////////   XML req  /////////////////////////////////////////////////////
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


function loadCombo(value,caption,baseResponse)
{
	var cmb = document.getElementById(value);
    var len = baseResponse.getElementsByTagName(value).length;
    for(var i=0; i<len; i++)
    {
        var val = baseResponse.getElementsByTagName(value)[i].firstChild.nodeValue;
        var cap = baseResponse.getElementsByTagName(caption)[i].firstChild.nodeValue;
        
        var opt = document.createElement('option');
        opt.value = val;
        opt.innerHTML = cap;
        
        cmb.appendChild(opt);
    }
}







/*********************************************************

// Taken from Pagination.js
function unloadCombo(eleName)
{
	var cmb = document.getElementById(eleName);
	var len = cmb.length; 
	for(var i=len; i>1; i--)
	{
		cmb.removeChild(cmb.lastChild);
	}
}

//Taken from Pagination.js
function unloadChildren(eleName)
{
	var prnt = document.getElementById(eleName);
	var len = prnt.childNodes.length;
	for(var i=len; i>0; i--)
	{
		prnt.removeChild(prnt.lastChild);
	}
}

*********************************************************/






function nullCheck(inx)
{
	var index = document.getElementById(inx);
	if((index == null)||(index == '')||(index == ' '))
		return false;
	else
		return true;
}



function clearForm()
{
	selSno=0;
	
	document.forms[0].reset();
	
	document.getElementById('Add').disabled = false;
	document.getElementById('Update').disabled = true;
	document.getElementById('Delete').disabled = true;

	callServer('Get');
}


function callServer(command)
 {
       var sno=selSno;
       var ctyp=document.getElementById('ctyp').value;

       var url="";
       if(command=="Add")
        {              
    	   		  var flag = ( nullCheck('ctyp') );
                    if(flag==true)
                    {
	                    url="../../../../../CollectionType?command=Add&ctyp="+ctyp;
	                    var req=getTransport();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }
	                    req.send(null);
                    }
                    else
                    {
                    	alert("Please type the name of the Collection Type");
                    }
        }
        else if(command=="Update")
        {
        		  	var flag = ( (sno!=0) && nullCheck('ctyp') );
                    if(flag==true)
                    {
                    	url="../../../../../CollectionType?command=Update&sno="+sno+"&ctyp="+ctyp;
	                    var req=getTransport();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }
	                    req.send(null);
                    }
                    else
                    {
                    	alert("Please click edit and then make changes");
                    }
        }
        else if(command=="Delete")
        {  
        			var flag = (sno!=0);
        			if(flag==true)
        			{
	        			url="../../../../../CollectionType?command=Delete&sno="+sno;
	        			var req=getTransport();
	                    req.open("GET",url,true);        
	                    req.onreadystatechange=function()
	                    {
	                       processResponse(req);
	                    }
	                    req.send(null);
        			}
        }
        else if(command=="Get")
        {           
		    	    document.getElementById('divcmbpage').style.display = "inline";
		    	    document.getElementById('divpage').style.display = "inline";
		    	
		    	    var page = document.getElementById('cmbpage').value;
		    	    var limit = document.getElementById('cmbpagination').value;
		    	    	
        			url="../../../../../CollectionType?command=Get&page="+page+"&limit="+limit;
		            var req=getTransport();
		            req.open("GET",url,true); 
		            req.onreadystatechange=function()
		            {
		               processResponse(req);
		            }
		            req.send(null);
        }
}  


//********************************* CallServer Response Coding ***************************************//

		
	function processResponse(req)
	{
            if(req.readyState==4)
            {
                if(req.status==200)
                {
                    var baseResponse = req.responseXML.getElementsByTagName('response')[0];
                    var cmd = baseResponse.getElementsByTagName('command')[0].firstChild.nodeValue;
                    var flag = baseResponse.getElementsByTagName('flag')[0].firstChild.nodeValue;
                    
                    if(cmd == 'Add')
                    {
                            if(flag=='success')
                            {
                                    alert("Saved Successfully");
                                    callServer('Get');
                                    clearForm();
                            }
                            else if(flag=='duplicate')
                            {
                            		alert("Already Exists");
                            }
                            else
                            {
                                    alert("Failed to Save");
                            }
                    }
                    
                    
                    else if(cmd == 'Update')
                    {
                            if(flag=='success')
                            {
                                    alert("Changes Saved Successfully");
                                    callServer('Get');
                                    clearForm();
                            }
                            else if(flag=='duplicate')
                            {
                            		alert("Already Exists");
                            }
                            else
                            {
                                    alert("Failed to Save Changes");
                            }
                    }
                    
                    
                    else if(cmd == 'Delete')
                    {
                            if(flag=='success')
                            {
                                    alert("Deleted Successfully");
                                    callServer('Get');
                                    clearForm();
                            }
                            else
                            {
                            		alert("Failed to Delete");
                            }
                    }
                    
                    
                    else if(cmd == 'Get')
                    {
                    	    var tbody = document.getElementById('tbody');
                    	    unloadChildren('tbody');
                    	    
                            var row = baseResponse.getElementsByTagName('row');
                            var len = row.length;
                            
                            var page = document.getElementById('cmbpage').value;
                            var limit = document.getElementById('cmbpagination').value;

                            paginate(baseResponse);
                            
                            for(var i=0; i<len; i++)
                            {
                            		var sno = row[i].getElementsByTagName('sno')[0].firstChild.nodeValue;
                            		var ctyp = row[i].getElementsByTagName('ctyp')[0].firstChild.nodeValue;
                            		
                            		var tr = document.createElement('tr');
                                    tr.id = sno;
             
                                    var td;
                                    
                                    td = document.createElement('td');
                                    var anc = document.createElement('a');
                                    anc.href = 'javascript:edit(' + sno + ')';
                                    anc.innerHTML = 'Edit';
                                    td.appendChild(anc);
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = sno;
                                    td.style.display = 'none';
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = (page-1) * limit + (i+1);
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = ctyp;
                                    tr.appendChild(td);
                                    
                                    
                                    tbody.appendChild(tr);
                            }
                    }



                
                }
            }
		
	}


	
	function edit(rid)
	{
		var r = document.getElementById(rid);
		rcells = r.cells;

		selSno = rcells.item(1).firstChild.nodeValue;
		document.getElementById('ctyp').value = rcells.item(3).firstChild.nodeValue;

		
		document.getElementById('Add').disabled = true;
		document.getElementById('Update').disabled = false;
		document.getElementById('Delete').disabled = false;
	}
	


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

