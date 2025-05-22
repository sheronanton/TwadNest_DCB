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

          document.Hrm_TransJoinForm.docid.value=rcells.item(1).firstChild.nodeValue;
          document.Hrm_TransJoinForm.docdesc.value=rcells.item(2).firstChild.nodeValue;
          
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
                       
                    var len=response.getElementsByTagName("docid").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    var dodcid=response.getElementsByTagName("docid")[i].firstChild.nodeValue;
                     var docdesc=response.getElementsByTagName("docdesc")[i].firstChild.nodeValue;
                                                    
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
                    var wid=document.createTextNode(dodcid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(docdesc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                   
                 
                    tlist.appendChild(tr);             
                    seq++;
                    }
                }
                else
                    {
                     
                    document.getElementById("docid").value="";
                    document.getElementById("docdesc").value="";
                    alert("Failure in loading values");
                    }
                    
                   
            }
           else if(command=="Add")
            {
        	  
                if(flag=='success')
                {
                   
                    var tlist=document.getElementById("tlist");
                   var docid=response.getElementsByTagName("docid")[0].firstChild.nodeValue;
                     var docdesc=response.getElementsByTagName("docdesc")[0].firstChild.nodeValue;
                   
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
                    var wid=document.createTextNode(docid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(docdesc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                 
                    tlist.appendChild(tr);             
                    seq++;
                    
                   
                     document.Hrm_TransJoinForm.update.disabled=true;
                     document.Hrm_TransJoinForm.delete1.disabled=true;
                     alert("Added Successfully");
                      clear1();
                    
                }
                else if(flag=='same')
                      alert("Record already Exists");
                    else
                      alert("Record not Saved");
                    
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {  
                     var tlist=document.getElementById("tlist");
                   var docid=response.getElementsByTagName("docid")[0].firstChild.nodeValue;
                     var docdesc=response.getElementsByTagName("docdesc")[0].firstChild.nodeValue;
                   
                    var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                 
                    cells.item(1).firstChild.nodeValue=docid;
                     cells.item(2).firstChild.nodeValue=docdesc;
                              
                    
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
                    var len=response.getElementsByTagName("docid").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    var docid=response.getElementsByTagName("docid")[i].firstChild.nodeValue;
                     var docdesc=response.getElementsByTagName("docdesc")[i].firstChild.nodeValue;
                   
                   
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
                    var wid=document.createTextNode(docid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(docdesc);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                 
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
         document.Hrm_TransJoinForm.docid.value="";
        document.Hrm_TransJoinForm.docdesc.value="";
        document.Hrm_TransJoinForm.docid.focus();
        document.Hrm_TransJoinForm.add.disabled=false;
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;
         
      
}

function clearGPF()
{
          
        document.Hrm_TransJoinForm.docid.value="";
        document.Hrm_TransJoinForm.docdesc.value="";
       
        document.Hrm_TransJoinForm.docid.focus();
      
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
    
        
        var docid,docdesc;
        
        var url="";
        if(command=="Add")
        { 
                nullCheck();

                docid=document.getElementById("docid").value;
                docdesc=document.getElementById("docdesc").value;
               
           
          url="../../../../../GPF_Final_Doc_Types?command=Add&docid="+docid+"&docdesc="+docdesc;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);       
    }
    else if(command=="Update")
    { 
    	docid=document.getElementById("docid").value;
                             docdesc=document.getElementById("docdesc").value;

            
          url="../../../../../GPF_Final_Doc_Types?command=Update&docid="+docid+"&docdesc="+docdesc;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
    else if(command=="Delete")
    {                            docid=document.getElementById("docid").value;
    docdesc=document.getElementById("docdesc").value;

            
          url="../../../../../GPF_Final_Doc_Types?command=Delete&docid="+docid;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
   
    else if(command=="get")
    {   
            document.Hrm_TransJoinForm.docid.focus();
        url="../../../../../GPF_Final_Doc_Types?command=get";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
if((document.Hrm_TransJoinForm.docid.value==""))
{
alert("Enter the Document type Id ");
}

else if(document.Hrm_TransJoinForm.docdesc.value=="")
{
alert("Enter the Document type description");
}

}


