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

          document.Hrm_TransJoinForm.reasonid.value=rcells.item(1).firstChild.nodeValue;
          document.Hrm_TransJoinForm.loanreason.value=rcells.item(2).firstChild.nodeValue;
          document.Hrm_TransJoinForm.rule.value=rcells.item(3).firstChild.nodeValue;
          
          var types=rcells.item(4).firstChild.nodeValue.split(",");;
          var i=0,j=0;
          
          for(i=0;i<types.length;i++){
          for(j=0;j<document.Hrm_TransJoinForm.withdrawtype.length;j++)
          {
        	if(document.Hrm_TransJoinForm.withdrawtype[j].value==types[i])
        	{
        		document.Hrm_TransJoinForm.withdrawtype[j].checked=true;
        	}
          }
          }
          
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
                    
                       
                    var len=response.getElementsByTagName("reasonid").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    	 var reasonid=response.getElementsByTagName("reasonid")[i].firstChild.nodeValue;
                         var reason=response.getElementsByTagName("loanreason")[i].firstChild.nodeValue;
                         var rule=response.getElementsByTagName("rule")[i].firstChild.nodeValue;
                         
                         var withdrawtype=response.getElementsByTagName("withdrawtype")[i].firstChild.nodeValue;
                                                    
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
                    var wid=document.createTextNode(reasonid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(reason);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var wdesc=document.createTextNode(rule);
                    td3.appendChild(wdesc);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var wtype=document.createTextNode(withdrawtype);
                    td4.appendChild(wtype);
                    tr.appendChild(td4);
                 
                    tlist.appendChild(tr);             
                    seq++;
                    }
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.delete1.disabled=true;   
                }
                else
                    {
                     
                    document.getElementById("reasonid").value="";
                    document.getElementById("loanreason").value="";
                    alert("Failure in loading values");
                    }
               
                   
            }
           else if(command=="Add")
            {
                if(flag=='success')
                {
                   
                    var tlist=document.getElementById("tlist");
                    var reasonid=response.getElementsByTagName("reasonid")[0].firstChild.nodeValue;
                    var reason=response.getElementsByTagName("loanreason")[0].firstChild.nodeValue;
                    var rule=response.getElementsByTagName("rule")[0].firstChild.nodeValue;
                    var withdrawtype=response.getElementsByTagName("withdrawtype")[0].firstChild.nodeValue;
                    
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
                    var wid=document.createTextNode(reasonid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(reason);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var wdesc=document.createTextNode(rule);
                    td3.appendChild(wdesc);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var wtype=document.createTextNode(withdrawtype);
                    td4.appendChild(wtype);
                    tr.appendChild(td4);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    
                   
                     document.Hrm_TransJoinForm.update.disabled=true;
                     document.Hrm_TransJoinForm.delete1.disabled=true;
                     alert("Added Successfully");
                      clear1();
                    
                }
                else if(flag=='same')
                        alert("Record Already Exists");
                    else
                      alert("Record not Saved");
                    
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {  
                     var tlist=document.getElementById("tlist");
                   var reasonid=response.getElementsByTagName("reasonid")[0].firstChild.nodeValue;
                     var reason=response.getElementsByTagName("loanreason")[0].firstChild.nodeValue;
                     var rule=response.getElementsByTagName("rule")[0].firstChild.nodeValue;
                     var withdrawtype=response.getElementsByTagName("withdrawtype")[0].firstChild.nodeValue;
                     var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                 
                    cells.item(1).firstChild.nodeValue=reasonid;
                     cells.item(2).firstChild.nodeValue=reason;
                     cells.item(3).firstChild.nodeValue=rule;        
                     cells.item(4).firstChild.nodeValue=withdrawtype;    
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
                     try{
                          tist.innerHTML="";
                        }
                     catch(e)
                          {
                           tlist.innerText="";
                         }  
                     while (tlist.childNodes.length > 0) {
                    	 tlist.removeChild(tlist.firstChild);
                     }
                     
                    var len=response.getElementsByTagName("reasonid").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    	var reasonid=response.getElementsByTagName("reasonid")[i].firstChild.nodeValue;
                        var reason=response.getElementsByTagName("loanreason")[i].firstChild.nodeValue;
                        var rule=response.getElementsByTagName("rule")[i].firstChild.nodeValue;
                        
                        var withdrawtype=response.getElementsByTagName("withdrawtype")[i].firstChild.nodeValue;
                   
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
                    var wid=document.createTextNode(reasonid);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(reason);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var wdesc=document.createTextNode(rule);
                    td3.appendChild(wdesc);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var wtype=document.createTextNode(withdrawtype);
                    td4.appendChild(wtype);
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
	var i;
	for (i = 0; i < document.Hrm_TransJoinForm.withdrawtype.length; i++){
		document.Hrm_TransJoinForm.withdrawtype[i].checked = false ;
	}

         document.Hrm_TransJoinForm.reasonid.value="";
        document.Hrm_TransJoinForm.loanreason.value="";
        document.Hrm_TransJoinForm.rule.value="";
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;   
         document.Hrm_TransJoinForm.add.disabled=false; 
        document.Hrm_TransJoinForm.reasonid.focus();
         
      
}

function clearGPF()
{
          
        document.Hrm_TransJoinForm.w_id.value="";
        document.Hrm_TransJoinForm.w_desc.value="";
       
        document.Hrm_TransJoinForm.w_id.focus();
      
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
    
        
        var reasonid,loanreason,rule;
        
        var url="";
        if(command=="Add")
        { 
        	
                if(nullCheck())
                {
                reasonid=document.getElementById("reasonid").value;
                loanreason=document.getElementById("loanreason").value;
                rule=document.getElementById("rule").value;
                
                
              
               
                               
                var i;  
                var withdrawtype= "";  
                for ( i = 0; i < document.Hrm_TransJoinForm.withdrawtype.length; i ++)  
                {  
                	if( document.Hrm_TransJoinForm.withdrawtype[i].checked==true)
                	{
                		if(withdrawtype==""){
                			withdrawtype=document.Hrm_TransJoinForm.withdrawtype[i].value;
                		}else{
                		withdrawtype += ","+document.Hrm_TransJoinForm.withdrawtype[i].value;
                		}
                	}
               }  
                
                
           // alert(withdrawtype);    
                
                
          
          url="../../../../../GPF_Loan_Reason?command=Add&reasonid="+reasonid+"&withdrawtype="+withdrawtype+"&loanreason="+loanreason+"&rule="+rule;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
                }
    }
    else if(command=="Update")
    { 
    	 reasonid=document.getElementById("reasonid").value;
         loanreason=document.getElementById("loanreason").value;
         rule=document.getElementById("rule").value;

         var i;  
         var withdrawtype= "";  
         for ( i = 0; i < document.Hrm_TransJoinForm.withdrawtype.length; i ++)  
         {  
         	if( document.Hrm_TransJoinForm.withdrawtype[i].checked==true)
         	{
         		if(withdrawtype==""){
         			withdrawtype=document.Hrm_TransJoinForm.withdrawtype[i].value;
         		}else{
         		withdrawtype += ","+document.Hrm_TransJoinForm.withdrawtype[i].value;
         		}
         	}
        }  
            
          url="../../../../../GPF_Loan_Reason?command=Update&reasonid="+reasonid+"&withdrawtype="+withdrawtype+"&loanreason="+loanreason+"&rule="+rule;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
    else if(command=="Delete")
    {                           
    	
    	reasonid=document.getElementById("reasonid").value;
    
            
          url="../../../../../GPF_Loan_Reason?command=Delete&reasonid="+reasonid;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
   
    else if(command=="get")
    {   
          // document.Hrm_TransJoinForm.reasonid.focus();
        url="../../../../../GPF_Loan_Reason?command=get";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
if((document.Hrm_TransJoinForm.reasonid.value==""))
{
alert("Enter the Withdrawl Reason Id ");
return 0;
}

else if(document.Hrm_TransJoinForm.loanreason.value=="")
{
alert("Enter the withdrawl description");
return 0;
}

else if(document.Hrm_TransJoinForm.rule.value=="")
{
alert("Enter the Applicable Rule");
return 0;
}

var i;
var flag=0;
for (i = 0; i < document.Hrm_TransJoinForm.withdrawtype.length; i++){
	if(document.Hrm_TransJoinForm.withdrawtype[i].checked ==true)
	{
	flag=1;	
	}
}

if(flag==0)
{
alert("Please choose Withdrawal Types");
return 0;
}


return true;

}


