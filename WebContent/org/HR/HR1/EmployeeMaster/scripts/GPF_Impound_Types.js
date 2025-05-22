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

          document.Hrm_TransJoinForm.impoundid.value=rcells.item(1).firstChild.nodeValue;
          document.Hrm_TransJoinForm.impounddesc.value=rcells.item(2).firstChild.nodeValue;
         // document.Hrm_TransJoinForm.interest.value=rcells.item(3).firstChild.nodeValue;
          document.Hrm_TransJoinForm.date.value=rcells.item(3).firstChild.nodeValue;
          
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
                     
                  while (tlist.childNodes.length > 0) {
                 	 tlist.removeChild(tlist.firstChild);
                  }    
                       
                    var len=response.getElementsByTagName("impoundid").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    var impoundid=response.getElementsByTagName("impoundid")[i].firstChild.nodeValue;
                     var impounddesc=response.getElementsByTagName("impounddesc")[i].firstChild.nodeValue;
                   
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
                    var wid=document.createTextNode(impoundid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(impounddesc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                   
	                   /* var td3=document.createElement("TD");
	                    var inte=document.createTextNode(interest);
	                    td3.appendChild(inte);
	                    tr.appendChild(td3);*/
                 
	                    var td4=document.createElement("TD");
	                    var dates=document.createTextNode(date);
	                    td4.appendChild(dates);
	                    tr.appendChild(td4);
	                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                   
                }
                else
                    {
                     
                    document.getElementById("impoundid").value="";
                    document.getElementById("impounddesc").value="";
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
                    
                    var impoundid=response.getElementsByTagName("impoundid")[0].firstChild.nodeValue;
                    var impounddesc=response.getElementsByTagName("impounddesc")[0].firstChild.nodeValue;
                  
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
                    var wid=document.createTextNode(impoundid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(impounddesc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                 
                   /* var td3=document.createElement("TD");
                    var inte=document.createTextNode(interest);
                    td3.appendChild(inte);
                    tr.appendChild(td3);*/
             
                    var td4=document.createElement("TD");
                    var dates=document.createTextNode(date);
                    td4.appendChild(dates);
                    tr.appendChild(td4);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    
                   
                     document.Hrm_TransJoinForm.update.disabled=true;
                     document.Hrm_TransJoinForm.delete1.disabled=true;
                     alert("Added Successfully");
                      clear1();
                    
                }
                else if(flag=='same')
                      alert("Record has already Exists");
                    else
                      alert("Record not Saved");
                    
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {  
                     var tlist=document.getElementById("tlist");
                     
                     var impoundid=response.getElementsByTagName("impoundid")[0].firstChild.nodeValue;
                     var impounddesc=response.getElementsByTagName("impounddesc")[0].firstChild.nodeValue;
                 
                     var date=response.getElementsByTagName("date")[0].firstChild.nodeValue;
                   
                    var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                 
                    cells.item(1).firstChild.nodeValue=impoundid;
                     cells.item(2).firstChild.nodeValue=impounddesc;
                    // cells.item(3).firstChild.nodeValue=interest;
                     cells.item(3).firstChild.nodeValue=date;
                    
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
                    var len=response.getElementsByTagName("impoundid").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    	
                    	 var impoundid=response.getElementsByTagName("impoundid")[i].firstChild.nodeValue;
                         var impounddesc=response.getElementsByTagName("impounddesc")[i].firstChild.nodeValue;
                     
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
                    var wid=document.createTextNode(impoundid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(impounddesc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                 
                   /* var td3=document.createElement("TD");
                    var inte=document.createTextNode(interest);
                    td3.appendChild(inte);
                    tr.appendChild(td3);*/
             
                    var td4=document.createElement("TD");
                    var dates=document.createTextNode(date);
                    td4.appendChild(dates);
                    tr.appendChild(td4);
                    
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
	 
         document.Hrm_TransJoinForm.impoundid.value="";
        document.Hrm_TransJoinForm.impounddesc.value="";
        document.Hrm_TransJoinForm.date.value="";
        document.Hrm_TransJoinForm.add.disabled=false; 
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;   
        document.Hrm_TransJoinForm.impoundid.focus();
       
      
}

function clearGPF()
{
         
        document.Hrm_TransJoinForm.impoundid.value="";
        document.Hrm_TransJoinForm.impounddesc.value="";
        document.Hrm_TransJoinForm.date.value="";
        document.Hrm_TransJoinForm.impoundid.focus();
        document.Hrm_TransJoinForm.add.disabled=false;   
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
    
        
        var impoundid,impounddesc,interest,date;
        
        var url="";
        if(command=="Add")
        { 
                if(nullCheck())
                {
                	
                	/*myOption = -1;
                	for (i=document.Hrm_TransJoinForm.interest.length-1; i > -1; i--) {
                	if (document.Hrm_TransJoinForm.interest[i].checked) {
                	myOption = i; i = -1;
                	}
                	}
                	if (myOption == -1) {
                	alert("You must select a radio button");
                	return false;
                	}

                	
                interest= document.Hrm_TransJoinForm.interest[myOption].value;

                	*/
                	
                	
                	
                impoundid=document.getElementById("impoundid").value;
                impounddesc=document.getElementById("impounddesc").value;
               // interest=document.getElementById("interest").value;
                date=document.getElementById("date").value;
           
          url="../../../../../GPF_Impound_Types?command=Add&impoundid="+impoundid+"&impounddesc="+impounddesc+"&date="+date;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
                }
    }
    else if(command=="Update")
    { 
    	
    	/*myOption = -1;
    	for (i=document.Hrm_TransJoinForm.interest.length-1; i > -1; i--) {
    	if (document.Hrm_TransJoinForm.interest[i].checked) {
    	myOption = i; i = -1;
    	}
    	}
    	if (myOption == -1) {
    	alert("You must select a radio button");
    	return false;
    	}

    	
    interest= document.Hrm_TransJoinForm.interest[myOption].value;
    	*/
    	
    	 impoundid=document.getElementById("impoundid").value;
         impounddesc=document.getElementById("impounddesc").value;
         //interest=document.getElementById("interest").value;
         date=document.getElementById("date").value;
            
          url="../../../../../GPF_Impound_Types?command=Update&impoundid="+impoundid+"&impounddesc="+impounddesc+"&date="+date;  
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
    else if(command=="Delete")
    {                           impoundid=document.getElementById("impoundid").value;
               
          url="../../../../../GPF_Impound_Types?command=Delete&impoundid="+impoundid;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
   
    else if(command=="get")
    {   
            document.Hrm_TransJoinForm.impoundid.focus();
        url="../../../../../GPF_Impound_Types?command=get";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
if((document.Hrm_TransJoinForm.impoundid.value==""))
{
alert("Enter the Impound type Id ");
return 0;
}

else if(document.Hrm_TransJoinForm.impounddesc.value=="")
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


