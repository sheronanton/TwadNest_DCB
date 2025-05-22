

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
	document.forms[0].reset();
	
	document.getElementById('Add').disabled = false;
	document.getElementById('Update').disabled = true;
	document.getElementById('Delete').disabled = true;
	
	callServer('Get');
}


function callServer(command)
 {
       var sno=selSno;
	   var divi=document.getElementById('divi').value;
       var sch=document.getElementById('sch').value;

       var url="";
       if(command=="Add")
        {              
    	   			var flag = ( nullCheck('divi') && nullCheck('sch') );
                    if(flag==true)
                    {
	                    url="../../../../../MapDivSch?command=Add&divi="+divi+"&sch="+sch;
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
                    	alert("Please enter both the fields and then click Add");
                    }
        }
        else if(command=="Update")
        {
        			var flag = ( (sno!=0) && nullCheck('divi') && nullCheck('sch') );
                    if(flag==true)
                    {
                    	url="../../../../../MapDivSch?command=Update&sno="+sno+"&divi="+divi+"&sch="+sch;
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
                    	alert("Please enter both the fields and then click Update");
                    }
        }
        else if(command=="Delete")
        {  
        			var flag = (sno!=0);
        			if(flag==true)
        			{
	        			url="../../../../../MapDivSch?command=Delete&sno="+sno;
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
		    	    	
        			url="../../../../../MapDivSch?command=Get&divi="+divi+"&sch="+sch+"&page="+page+"&limit="+limit;
		            var req=getTransport();
		            req.open("GET",url,true); 
		            req.onreadystatechange=function()
		            {
		               processResponse(req);
		            }
		            req.send(null);
        }
        else if(command=="LoadCmb")
        {               
        			url="../../../../../MapDivSch?command=LoadCmb";
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
                            
                            paginate(baseResponse);
                            
                            for(var i=0; i<len; i++)
                            {
                            		var sno = row[i].getElementsByTagName('sno')[0].firstChild.nodeValue;
                            		var divi = row[i].getElementsByTagName('divi')[0].firstChild.nodeValue;
                            		var division = row[i].getElementsByTagName('division')[0].firstChild.nodeValue;
                            		var sch = row[i].getElementsByTagName('sch')[0].firstChild.nodeValue;
                            		var scheme = row[i].getElementsByTagName('scheme')[0].firstChild.nodeValue;
                            		
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
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = divi;
                                    td.style.display = 'none';
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = division;
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = sch;
                                    td.style.display = 'none';
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = scheme;
                                    tr.appendChild(td);
                                    
                                    
                                    tbody.appendChild(tr);
                            }
                    }

                    
                    else if(cmd == 'LoadCmb')
                    {
                            if(flag=='success')
                            {
                            		unloadCombo('divi');
                            		loadCombo('divi','division',baseResponse);
                            		
                            		unloadCombo('sch');
                            		loadCombo('sch','scheme',baseResponse);

                            }
                            else
                            {
                                    alert("Failed to load Divisions and Schemes");
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
		document.getElementById('divi').value = rcells.item(2).firstChild.nodeValue;
		document.getElementById('sch').value = rcells.item(4).firstChild.nodeValue;

		
		document.getElementById('Add').disabled = true;
		document.getElementById('Update').disabled = false;
		document.getElementById('Delete').disabled = false;
	}
	
	
	function removeNull(val)
	{
		if((val==null)||(val=="")||(val=='null'))
		{
			return "";
		}
		return val;
	}
	

	function openWindow(url,lbl)
	{
		var winSearchConsumer;
		if (winSearchConsumer && winSearchConsumer.open && !winSearchConsumer.closed) 
	    {
	       winSearchConsumer.resizeTo(500,500);
	       winSearchConsumer.moveTo(250,250); 
	       winSearchConsumer.focus();
	    }
	    else
	    {
	       winSearchConsumer=null
	    }        
	    winSearchConsumer= window.open(url,lbl,"status=1,height=500,width=500,resizable=YES,scrollbars=yes"); 
	    winSearchConsumer.moveTo(250,250);  
	    winSearchConsumer.focus();
	}
	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

