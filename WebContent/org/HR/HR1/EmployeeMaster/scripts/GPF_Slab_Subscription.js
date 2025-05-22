var xmlhttp;var seq=0;var common="",mn,yr,v;
function Exit()
{
    window.close();
}




 function loadvalue(empid)
    {      
          common=empid;
          var emp_id=document.getElementById(empid); 
          var rcells=emp_id.cells;

          document.Hrm_TransJoinForm.slabid.value=rcells.item(1).firstChild.nodeValue;
          document.Hrm_TransJoinForm.salaryfrom.value=rcells.item(2).firstChild.nodeValue;
          document.Hrm_TransJoinForm.salaryto.value=rcells.item(3).firstChild.nodeValue;
          document.Hrm_TransJoinForm.subamount.value=rcells.item(4).firstChild.nodeValue;
          document.Hrm_TransJoinForm.date.value=rcells.item(5).firstChild.nodeValue;
          
          document.Hrm_TransJoinForm.add.disabled=true;
        document.Hrm_TransJoinForm.update.disabled=false;
        document.Hrm_TransJoinForm.delete1.disabled=false;
        
          
      
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
function stateChanged()
{
    var flag,command,response;
   
    if(xmlhttp.readyState==4)
    {
       if(xmlhttp.status==200)
       {
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="get")
            {
                if(flag=='success')
                {
                    var tlist=document.getElementById("tlist");
                     try{
                //tlist.innerText="";
                tlist.innerHTML="";
            
                      }
                  catch(e)
                  {
                tlist.innerText="";
                //tlist.innerHTML="";
               
                  }
                  while (tlist.childNodes.length > 0) {
                 	 tlist.removeChild(tlist.firstChild);
                  }    
                       
                    var len=response.getElementsByTagName("slabid").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    var slabid=response.getElementsByTagName("slabid")[i].firstChild.nodeValue;
                     var salaryfrom=response.getElementsByTagName("salaryfrom")[i].firstChild.nodeValue;
                     var salaryto=response.getElementsByTagName("salaryto")[i].firstChild.nodeValue;
                     var subamount=response.getElementsByTagName("subamount")[i].firstChild.nodeValue;
                     var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                     
                   var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);                    
         
                    var td1=document.createElement("TD");
                    var wid=document.createTextNode(slabid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(salaryfrom);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                   
	                    var td3=document.createElement("TD");
	                    var inte=document.createTextNode(salaryto);
	                    td3.appendChild(inte);
	                    tr.appendChild(td3);
	                    
	                    var td4=document.createElement("TD");
	                    var sub=document.createTextNode(subamount);
	                    td4.appendChild(sub);
	                    tr.appendChild(td4);
                 
	                    var td5=document.createElement("TD");
	                    var dates=document.createTextNode(date);
	                    td5.appendChild(dates);
	                    tr.appendChild(td5);
	                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                }
                else
                    {
                     
                    document.getElementById("slabid").value="";
                    document.getElementById("salaryfrom").value="";
                    document.getElementById("salaryto").value="";
                    document.getElementById("subamount").value="";
                    document.getElementById("date").value="";
                    alert("Failure in loading values");
                    }
                document.Hrm_TransJoinForm.add.disabled=false;
                document.Hrm_TransJoinForm.update.disabled=true;
               
                document.Hrm_TransJoinForm.delete1.disabled=true;         
                   
            }
           else if(command=="Add")
            {
                if(flag=='success')
                {
                   
                    var tlist=document.getElementById("tlist");
                    
                    var slabid=response.getElementsByTagName("slabid")[0].firstChild.nodeValue;
                    var salaryfrom=response.getElementsByTagName("salaryfrom")[0].firstChild.nodeValue;
                    var salaryto=response.getElementsByTagName("salaryto")[0].firstChild.nodeValue;
                    var subamount=response.getElementsByTagName("subamount")[0].firstChild.nodeValue;
                    var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                    
                   
                   var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);                    
         
                    var td1=document.createElement("TD");
                    var wid=document.createTextNode(slabid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(salaryfrom);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                 
                    var td3=document.createElement("TD");
                    var inte=document.createTextNode(salaryto);
                    td3.appendChild(inte);
                    tr.appendChild(td3);
             
                    var td4=document.createElement("TD");
                    var sub=document.createTextNode(subamount);
                    td4.appendChild(sub);
                    tr.appendChild(td4);
             
                    var td5=document.createElement("TD");
                    var dates=document.createTextNode(date);
                    td5.appendChild(dates);
                    tr.appendChild(td5);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    
                   
                     document.Hrm_TransJoinForm.update.disabled=true;
                     document.Hrm_TransJoinForm.delete1.disabled=true;
                     alert("Added Successfully");
                      clear1();
                    
                }
                else if(flag=='same')
                      alert("Same data wont be allowed");
                    else
                      alert("Record not Saved");
                    
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {  
                     var tlist=document.getElementById("tlist");
                     
                     var slabid=response.getElementsByTagName("slabid")[0].firstChild.nodeValue;
                     var salaryfrom=response.getElementsByTagName("salaryfrom")[0].firstChild.nodeValue;
                     var salaryto=response.getElementsByTagName("salaryto")[0].firstChild.nodeValue;
                     var subamount=response.getElementsByTagName("subamount")[0].firstChild.nodeValue;
                     var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                   
                    var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                 
                    cells.item(1).firstChild.nodeValue=slabid;
                     cells.item(2).firstChild.nodeValue=salaryfrom;
                     cells.item(3).firstChild.nodeValue=salaryto;
                     cells.item(4).firstChild.nodeValue=subamount;
                     cells.item(5).firstChild.nodeValue=date;
                    
                    alert("Updated Successfully");
                     clear1();
                    
                     document.Hrm_TransJoinForm.add.disabled=false;
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.delete1.disabled=true;
                   
                                       
                }
                else
                    alert("Failure in Update");
            }
          else if(command=="Delete")
            {
                if(flag=='success')
                {   
                
                
                  
                    var tlist=document.getElementById("tlist");
                    while (tlist.childNodes.length > 0) {
                   	 tlist.removeChild(tlist.firstChild);
                    }  
                    var len=response.getElementsByTagName("slabid").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    	
                    	  var slabid=response.getElementsByTagName("slabid")[i].firstChild.nodeValue;
                          var salaryfrom=response.getElementsByTagName("salaryfrom")[i].firstChild.nodeValue;
                          var salaryto=response.getElementsByTagName("salaryto")[i].firstChild.nodeValue;
                          var subamount=response.getElementsByTagName("subamount")[i].firstChild.nodeValue;
                          var date=response.getElementsByTagName("date")[i].firstChild.nodeValue;
                          
                   
                    var tr=document.createElement("TR");
                    tr.id=seq;
                    var td=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvalue('"+seq+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    td.appendChild(anc);
                    tr.appendChild(td);                    
         
                    var td1=document.createElement("TD");
                    var wid=document.createTextNode(slabid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(salaryfrom);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                 
                    var td3=document.createElement("TD");
                    var inte=document.createTextNode(salaryto);
                    td3.appendChild(inte);
                    tr.appendChild(td3);
             
                    var td4=document.createElement("TD");
                    var sub=document.createTextNode(subamount);
                    td4.appendChild(sub);
                    tr.appendChild(td4);
             
                    var td5=document.createElement("TD");
                    var dates=document.createTextNode(date);
                    td5.appendChild(dates);
                    tr.appendChild(td5);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                    
                    
                  /*  var tlist=document.getElementById("tlist");
                   
                    alert(common);
                    if(common==0)
                    {
                    var field=document.getElementById(common);
                    }
                    else
                    {
                    var field=document.getElementById(common-1);
                    }
                    var index=field.rowIndex;
                    tlist.deleteRow(index);         */
                    alert("Deleted Successfully");
                     clear1();
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.delete1.disabled=true;   
                     document.Hrm_TransJoinForm.add.disabled=false;   
                    
                }
                else
                {
                    alert("Not success in Delete");
                    }
            
        }
       
        }
    }
           
}
function clear1()
{
         document.Hrm_TransJoinForm.slabid.value="";
        document.Hrm_TransJoinForm.salaryfrom.value="";
        document.Hrm_TransJoinForm.salaryto.value="";
        document.Hrm_TransJoinForm.subamount.value="";
        document.Hrm_TransJoinForm.date.value="";
        document.Hrm_TransJoinForm.add.disabled=false; 
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;   
        document.Hrm_TransJoinForm.slabid.focus();
         
      
}

function clearGPF()
{
          
	document.Hrm_TransJoinForm.slabid.value="";
    document.Hrm_TransJoinForm.salaryfrom.value="";
    document.Hrm_TransJoinForm.salaryto.value="";
    document.Hrm_TransJoinForm.subamount.value="";
    document.Hrm_TransJoinForm.date.value="";
    document.Hrm_TransJoinForm.slabid.focus();
      
    /*    var tlist=document.getElementById("tlist");
        try{
                //tlist.innerText="";
                tlist.innerHTML="";
            
       }
    catch(e)
       {
                tlist.innerText="";
                //tlist.innerHTML="";
               
       }*/
       
        document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
        
}
function call(command)
{
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   
    
        
        var slabid,salaryfrom,salaryto,subamount,date;
        
        var url="";
        if(command=="Add")
        { 
                if(nullCheck())
                {
                	
             	       	                	                	                	
                	slabid=document.getElementById("slabid").value;
                	salaryfrom=document.getElementById("salaryfrom").value;
                	salaryto=document.getElementById("salaryto").value;
                	subamount=document.getElementById("subamount").value;
                date=document.getElementById("date").value;
           
          url="../../../../../GPF_Slab_Subscription?command=Add&slabid="+slabid+"&salaryfrom="+salaryfrom+"&salaryto="+salaryto+"&subamount="+subamount+"&date="+date;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
                }
    }
    else if(command=="Update")
    { 
    	   	       	    	
    slabid=document.getElementById("slabid").value;
	salaryfrom=document.getElementById("salaryfrom").value;
	salaryto=document.getElementById("salaryto").value;
	subamount=document.getElementById("subamount").value;
date=document.getElementById("date").value;
            
         url="../../../../../GPF_Slab_Subscription?command=update&slabid="+slabid+"&salaryfrom="+salaryfrom+"&salaryto="+salaryto+"&subamount="+subamount+"&date="+date;  
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
    else if(command=="Delete")
    {                           
    	slabid=document.getElementById("slabid").value;
               
          url="../../../../../GPF_Slab_Subscription?command=Delete&slabid="+slabid;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
   
    else if(command=="get")
    {   
            document.Hrm_TransJoinForm.slabid.focus();
        url="../../../../../GPF_Slab_Subscription?command=get";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
if((document.Hrm_TransJoinForm.slabid.value==""))
{
alert("Enter the Impound type Id ");
return 0;
}

else if(document.Hrm_TransJoinForm.salaryfrom.value=="")
{
alert("Enter the Impound type description");
return 0;
}

else if(document.Hrm_TransJoinForm.date.value=="")
{
alert("Enter the Effective Date");
return 0;
}

return true;

}
function numbersonly1(e,t)
{
   var unicode=e.charCode? e.charCode : e.keyCode;
   //alert(unicode);
   if(unicode==13)
    {
      try{t.blur();}catch(e){}
      return true;
    
    }
    if ( unicode!=8 && unicode !=9  )
    {
        if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
            return false 
    }
   
 }

function subamount1()
{
	var salamount=parseInt(document.getElementById("salaryto").value);
	var subamount=parseInt(document.getElementById("subamount").value);
	
	if(salamount<subamount)
	{
		alert('Min.Subscription amount should less than Salary To amount');
		document.getElementById("subamount").value="";
		document.getElementById("subamount").focus();
	}
			
}
