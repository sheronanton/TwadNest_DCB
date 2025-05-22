var CONS = 'Get';
var selRid = 0;
// /////////////////////////////////////////// XML req
// /////////////////////////////////////////////////////
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
//  Taken from Pagination.js
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

function doParentAccHead(code)
{
   document.forms[0].txtaccountheadcode.value=code;
   callServer('AcctHead');
   return true;
}

function callServer(command)
 {
       var rid=selRid;
	   var sch=document.getElementById('sch').value;
	   var ctyp=document.getElementById('ctyp').value;
	   var achead=document.forms[0].txtaccountheadcode.value;
	   var wef=document.getElementById('wef').value;
	   if(wef=='-')
	   {
		   wef="";
	   }
	   var status=document.getElementById('status').value;
	   var fstatus=document.getElementById('fstatus').value;

       var url="";
       
       if(command=="AcctHead")
       {
           url="../../../../../MapColnAcct?command=AcctHead&achead="+achead;
           var req=getTransport();
           req.open("GET",url,true);        
           req.onreadystatechange=function()
           {
              processResponse(req);
           }
           req.send(null);
       }
    	   
       else if(command=="Add")
        {              
    	   			var flag = ( nullCheck('sch') && nullCheck('ctyp')  && achead!=""  && nullCheck('wef'));
                    if(flag==true)
                    {
	                    url="../../../../../MapColnAcct?command=Add&sch="+sch+"&ctyp="+ctyp+"&achead="+achead+"&wef="+wef+"&status="+status+"&fstatus="+fstatus;
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
                    	alert("Please enter all details");
                    }
        }
        else if(command=="Update")
        {
		   			var flag = ( (rid!=0) && nullCheck('sch') && nullCheck('ctyp')  && achead!="" && nullCheck('wef') && nullCheck('status'));
		   			
		            if(flag==true)
		            {
		                url="../../../../../MapColnAcct?command=Update&sch="+sch+"&ctyp="+ctyp+"&achead="+achead+"&wef="+wef+"&status="+status+"&rid="+rid+"&fstatus="+fstatus;
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
		            	alert("Please enter all details");
		            }
        }
        else if(command=="Delete")
        {  
		            if(rid!=0)
		            {
		                url="../../../../../MapColnAcct?command=Delete&rid="+rid;
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
		            	alert("Please enter all details");
		            }
        }
        else if(command=="Get")
        {           
		    	    document.getElementById('divcmbpage').style.display = "inline";
		    	    document.getElementById('divpage').style.display = "inline";
		    	
		    	    var page = document.getElementById('cmbpage').value;
		    	    var limit = document.getElementById('cmbpagination').value;
		    	    	
		    	    url="../../../../../MapColnAcct?command=Get&status="+status+"&page="+page+"&limit="+limit;
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
        			url="../../../../../MapColnAcct?command=LoadCmb";
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
                    
                    if(cmd=='AcctHead')
                    {
                        if(flag=='success')
                        {
                        	document.forms[0].txtaccountheadname.value=baseResponse.getElementsByTagName('accthead')[0].firstChild.nodeValue;
                        }
                        else
                        {
                                alert("Failed to load Account Head Description");
                        }

                    }
                    	
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
                            
                            var page = document.getElementById('cmbpage').value;
                            var limit = document.getElementById('cmbpagination').value;

                            paginate(baseResponse);
                            
                            for(var i=0; i<len; i++)
                            {
                        		
                            		var rid = row[i].getElementsByTagName('rid')[0].firstChild.nodeValue;
                            		var sch = row[i].getElementsByTagName('sch')[0].firstChild.nodeValue;
                            		var scheme = row[i].getElementsByTagName('scheme')[0].firstChild.nodeValue;
                            		var achead = row[i].getElementsByTagName('achead')[0].firstChild.nodeValue;
                            		var rdesc = row[i].getElementsByTagName('rdesc')[0].firstChild.nodeValue;
                            		var ctyp = row[i].getElementsByTagName('ctyp')[0].firstChild.nodeValue;
                            		var coltype = row[i].getElementsByTagName('coltype')[0].firstChild.nodeValue;
                            		var wef = row[i].getElementsByTagName('wef')[0].firstChild.nodeValue;
                            		var status = row[i].getElementsByTagName('status')[0].firstChild.nodeValue;
                            		var fstatus_Db = row[i].getElementsByTagName('fstatus')[0].firstChild.nodeValue;
                            		 
                            		var fstatus="";
                            		if (fstatus_Db=='Y')
                            			fstatus="Yes";
                            		else if (fstatus_Db=='N')
                            			fstatus="No";
                            		else
                            			fstatus="-";
                            		
                            		 
                            				
                            		var tr = document.createElement('tr');
                                    tr.id = rid;
             
                                    var td;
                                    
                                    td = document.createElement('td');
                                    var anc = document.createElement('a');
                                    anc.href = 'javascript:edit(' + rid + ')';
                                    anc.innerHTML = 'Edit';
                                    td.appendChild(anc);
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = (page-1) * limit + (i+1);
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(rid);
                                    td.style.display = 'none';
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(sch);
                                    td.style.display = 'none';
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(ctyp);
                                    td.style.display = 'none';
                                    tr.appendChild(td);

                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(scheme);
                                    tr.appendChild(td);

                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(coltype);
                                    tr.appendChild(td);

                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(rdesc);
                                    tr.appendChild(td);

                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(achead);
                                    tr.appendChild(td);

                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(wef);
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(status);
                                    tr.appendChild(td);
                                    
                                    td = document.createElement('td');
                                    td.innerHTML = removeNull(fstatus);
                                    tr.appendChild(td);
                                    
                                    tbody.appendChild(tr);
                            }
                    }
                    else if(cmd == 'LoadCmb')
                    {
                            if(flag=='success')
                            {
                            		unloadCombo('sch');
                            		loadCombo('sch','scheme',baseResponse);
                            		
                            		unloadCombo('ctyp');
                            		loadCombo('ctyp','coltype',baseResponse);
 
                            }
                            else
                            {
                                    alert("Failed to load Divisions and Districts");
                            }
                    }
                
                }
            }
		
	}


	
	function edit(rid)
	{
		
			var r = document.getElementById(rid);
			rcells = r.cells;
			selRid = rcells.item(2).firstChild.nodeValue;
			document.getElementById('sch').value = removeNull(rcells.item(3).firstChild.nodeValue);
			document.getElementById('ctyp').value = removeNull(rcells.item(4).firstChild.nodeValue);
			document.forms[0].txtaccountheadcode.value = removeNull(rcells.item(8).firstChild.nodeValue);
			callServer('AcctHead');
		    document.getElementById('wef').value = removeNull(rcells.item(9).firstChild.nodeValue);
			document.getElementById('status').value = removeNull(rcells.item(10).firstChild.nodeValue);
			var va_= removeNull(rcells.item(11).firstChild.nodeValue);
		   
			if (va_=='Yes')
				document.getElementById('fstatus').options[1].selected=true;
			else if (va_=='No')	
				document.getElementById('fstatus').options[2].selected=true;
			else 
				document.getElementById('fstatus').options[0].selected=true;
			
			document.getElementById('Add').disabled = true;
			document.getElementById('Update').disabled = false;
			document.getElementById('Delete').disabled = false;
	}
	
	
	function removeNull(val)
	{
		if((val==null)||(val=="")||(val=='null'))
		{
			return "-";
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
	var winAccHeadCode;
	var winListAllBudget;
	function AccHeadpopup()
	{
		if (winAccHeadCode && winAccHeadCode.open && !winAccHeadCode.closed) 
		{
			winAccHeadCode.resizeTo(500,500);
			winAccHeadCode.moveTo(250,250); 
			winAccHeadCode.focus();
		}
		else
		{
		winAccHeadCode=null
		}
	
		winAccHeadCode= window.open("../../../../../org/FAS/FAS1/AccountHeadDirectory/jsps/Acc_Head_Dir_List_InUse.jsp","AccountHeadSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
		winAccHeadCode.moveTo(250,250);  
		winAccHeadCode.focus();
	
	}
