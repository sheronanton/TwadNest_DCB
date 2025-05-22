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
         
          //document.Hrm_TransJoinForm.sno.value=rcells.item(1).firstChild.nodeValue;
          
          document.Hrm_TransJoinForm.typeid.value=rcells.item(1).firstChild.nodeValue;
          
          document.Hrm_TransJoinForm.typedesc.value=rcells.item(2).firstChild.nodeValue;
          document.Hrm_TransJoinForm.crcode.value=rcells.item(3).firstChild.nodeValue;
          document.Hrm_TransJoinForm.dbcode.value=rcells.item(4).firstChild.nodeValue;
          var fundnature=rcells.item(5).firstChild.nodeValue;
          
         var leng= document.Hrm_TransJoinForm.fundnature.length;
        
        
        
          for(var i=0;i<leng;i++)
          {
        	  if(document.getElementById("fundnature").options[i].value==fundnature)
        	  {
        		  document.getElementById("fundnature").options[i].selected='selected';  
        	  }
          }
          
          document.Hrm_TransJoinForm.typeid.disabled=true;
         // document.Hrm_TransJoinForm.fundnature.disabled=true;   
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

                     
                   
                    var len=response.getElementsByTagName("typeid").length;
                  
                    for(var i=0;i<len;i++)
                    {
                    var typeid=response.getElementsByTagName("typeid")[i].firstChild.nodeValue;
                    
                    var typedesc=response.getElementsByTagName("typedesc")[i].firstChild.nodeValue;
                    
                    var crcode=response.getElementsByTagName("crcode")[i].firstChild.nodeValue;
                   
                    var dbcode=response.getElementsByTagName("dbcode")[i].firstChild.nodeValue;
                                     
                    
                   var fundnature=response.getElementsByTagName("fundnature")[i].firstChild.nodeValue;
                   
                   
                   

               	if(crcode==0)
               	{
               		crcode="";
               	}
               	
               	if(dbcode==0)
               	{
               		dbcode="";
               	}
                   
                   
                    
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
                    
                  /*  var td1=document.createElement("TD");
                    var sn=document.createTextNode(sno);
                    td1.appendChild(sn);
                    tr.appendChild(td1); */    
                    
                    var td2=document.createElement("TD");
                    var typei=document.createTextNode(typeid);
                    td2.appendChild(typei);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var typed=document.createTextNode(typedesc);
                    td3.appendChild(typed);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var crc=document.createTextNode(crcode);
                    td4.appendChild(crc);
                    tr.appendChild(td4);
               
                    var td5=document.createElement("TD");
                    var dbc=document.createTextNode(dbcode);
                    td5.appendChild(dbc);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var fund=document.createTextNode(fundnature);
                    td6.appendChild(fund);
                    tr.appendChild(td6);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                    
                  
                }
                else
                    {
                       
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
                	 while (tlist.childNodes.length > 0) {
                    	 tlist.removeChild(tlist.firstChild);
                     }

                     
                   
                	  var len=response.getElementsByTagName("typeid").length;
                      
                      for(var i=0;i<len;i++)
                      {
                      var typeid=response.getElementsByTagName("typeid")[i].firstChild.nodeValue;
                      
                      var typedesc=response.getElementsByTagName("typedesc")[i].firstChild.nodeValue;
                      
                      var crcode=response.getElementsByTagName("crcode")[i].firstChild.nodeValue;
                     
                      var dbcode=response.getElementsByTagName("dbcode")[i].firstChild.nodeValue;
                                       
                      
                     var fundnature=response.getElementsByTagName("fundnature")[i].firstChild.nodeValue;
                     
                     if(crcode==0)
                    	{
                    		crcode="";
                    	}
                    	
                    	if(dbcode==0)
                    	{
                    		dbcode="";
                    	}
                      
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
                      
                    /*  var td1=document.createElement("TD");
                      var sn=document.createTextNode(sno);
                      td1.appendChild(sn);
                      tr.appendChild(td1); */    
                      
                      var td2=document.createElement("TD");
                      var typei=document.createTextNode(typeid);
                      td2.appendChild(typei);
                      tr.appendChild(td2);
                      
                      var td3=document.createElement("TD");
                      var typed=document.createTextNode(typedesc);
                      td3.appendChild(typed);
                      tr.appendChild(td3);
                      
                      var td4=document.createElement("TD");
                      var crc=document.createTextNode(crcode);
                      td4.appendChild(crc);
                      tr.appendChild(td4);
                 
                      var td5=document.createElement("TD");
                      var dbc=document.createTextNode(dbcode);
                      td5.appendChild(dbc);
                      tr.appendChild(td5);
                      
                      var td6=document.createElement("TD");
                      var fund=document.createTextNode(fundnature);
                      td6.appendChild(fund);
                      tr.appendChild(td6);
                      
                      tlist.appendChild(tr);             
                      seq++;
                      }
                    
                      document.Hrm_TransJoinForm.add.disabled=false;
                     document.Hrm_TransJoinForm.update.disabled=true;
                     document.Hrm_TransJoinForm.delete1.disabled=true;
                     alert("Added Successfully");
                     clear1();
                }
                else
                    alert("User Id already Grant for this office ");
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {
                    var tlist=document.getElementById("tlist");
                    
                    var typeid=response.getElementsByTagName("typeid")[0].firstChild.nodeValue;
                    
                    var typedesc=response.getElementsByTagName("typedesc")[0].firstChild.nodeValue;
                    
                    var crcode=response.getElementsByTagName("crcode")[0].firstChild.nodeValue;
                   
                    var dbcode=response.getElementsByTagName("dbcode")[0].firstChild.nodeValue;
                                     
                    
                   var fundnature=response.getElementsByTagName("fundnature")[0].firstChild.nodeValue;
                
                   if(crcode==0)
                  	{
                  		crcode="";
                  	}
                  	
                  	if(dbcode==0)
                  	{
                  		dbcode="";
                  	}
                   
                  var readCell=document.getElementById(common);
                  
                    var cells=readCell.cells;
                   
                    cells.item(1).firstChild.nodeValue=typeid;
                    cells.item(2).firstChild.nodeValue=typedesc;
                    cells.item(3).firstChild.nodeValue=crcode;
                    cells.item(4).firstChild.nodeValue=dbcode;
                    cells.item(5).firstChild.nodeValue=fundnature;
                    alert("Updated Successfully");
                    
                     document.Hrm_TransJoinForm.add.disabled=false;
                    document.Hrm_TransJoinForm.update.disabled=true;
                    document.Hrm_TransJoinForm.delete1.disabled=true;
                    clear1();
                                       
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

                                                         
                    var len=response.getElementsByTagName("typeid").length;
                  
                    for(var i=0;i<len;i++)
                    {
                    var typeid=response.getElementsByTagName("typeid")[i].firstChild.nodeValue;
                    
                    var typedesc=response.getElementsByTagName("typedesc")[i].firstChild.nodeValue;
                    
                    var crcode=response.getElementsByTagName("crcode")[i].firstChild.nodeValue;
                   
                    var dbcode=response.getElementsByTagName("dbcode")[i].firstChild.nodeValue;
                                     
                    
                   var fundnature=response.getElementsByTagName("fundnature")[i].firstChild.nodeValue;
                   
                   
                   if(crcode==0)
                  	{
                  		crcode="";
                  	}
                  	
                  	if(dbcode==0)
                  	{
                  		dbcode="";
                  	}
                   
                    
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
                    
                  /*  var td1=document.createElement("TD");
                    var sn=document.createTextNode(sno);
                    td1.appendChild(sn);
                    tr.appendChild(td1); */    
                    
                    var td2=document.createElement("TD");
                    var typei=document.createTextNode(typeid);
                    td2.appendChild(typei);
                    tr.appendChild(td2);
                    
                    var td3=document.createElement("TD");
                    var typed=document.createTextNode(typedesc);
                    td3.appendChild(typed);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    var crc=document.createTextNode(crcode);
                    td4.appendChild(crc);
                    tr.appendChild(td4);
               
                    var td5=document.createElement("TD");
                    var dbc=document.createTextNode(dbcode);
                    td5.appendChild(dbc);
                    tr.appendChild(td5);
                    
                    var td6=document.createElement("TD");
                    var fund=document.createTextNode(fundnature);
                    td6.appendChild(fund);
                    tr.appendChild(td6);
                    
                    tlist.appendChild(tr);             
                    seq++;
                    }
                   
                
                    alert("Deleted Successfully");
                    document.Hrm_TransJoinForm.update.disabled=true;
                   
                    clear1();
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
	
	document.Hrm_TransJoinForm.fundnature.options[0].selected=true;
	document.Hrm_TransJoinForm.typeid.disabled=false;
	document.Hrm_TransJoinForm.typeid.value="";
	document.Hrm_TransJoinForm.typedesc.value="";
	document.Hrm_TransJoinForm.crcode.value="";
	document.Hrm_TransJoinForm.dbcode.value="";
	
	// document.Hrm_TransJoinForm.fundnature.disabled=false;
    
	
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
    
        
        var typeid;
        var typedesc;
        var crcode;
        var dbcode;
        var fundnature;
       
        
        var url="";
        if(command=="Add")
        { 
                if(nullCheck())
                {
                
                	typeid=document.getElementById("typeid").value;
                	typedesc=document.getElementById("typedesc").value;
                	crcode=document.getElementById("crcode").value;
                	
                	if(crcode==""||crcode.length<=0)
                	{
                		crcode=0;
                	}
                	dbcode=document.getElementById("dbcode").value;
                	if(dbcode==""||dbcode.length<=0)
                	{
                		dbcode=0;
                	}
                	
               
                	fundnature=document.getElementById("fundnature").value;
                	
        url="../../../../../GPF_Trans_Types?command=Add&typeid="+typeid+"&typedesc="+typedesc+"&crcode="+crcode+"&dbcode="+dbcode+"&fundnature="+fundnature;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);     
        }
    }
    else if(command=="Update")
    {
                if(nullCheck())
                {
                	typeid=document.getElementById("typeid").value;
                	typedesc=document.getElementById("typedesc").value;
                	crcode=document.getElementById("crcode").value;
                	dbcode=document.getElementById("dbcode").value;
                	fundnature=document.getElementById("fundnature").value;
                	

                	if(crcode==""||crcode.length<=0)
                	{
                		crcode=0;
                	}
                	dbcode=document.getElementById("dbcode").value;
                	if(dbcode==""||dbcode.length<=0)
                	{
                		dbcode=0;
                	}
                	
                    
        url="../../../../../GPF_Trans_Types?command=Update&typeid="+typeid+"&typedesc="+typedesc+"&crcode="+crcode+"&dbcode="+dbcode+"&fundnature="+fundnature;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);   
        }
    }
    else if(command=="Delete")
    {
    	
    	typeid=document.getElementById("typeid").value;
    	
        url="../../../../../GPF_Trans_Types?command=Delete&typeid="+typeid;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);      
    }
  
    else if(command=="get")
    {   
        
   
       
        url="../../../../../GPF_Trans_Types?command=get";
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
	var flag;
	var flag1;
        if((document.Hrm_TransJoinForm.typeid.value==""))
        {
        alert("Enter the Transaction Type Id");
        return 0;
        }
                     
        else if(document.Hrm_TransJoinForm.typedesc.value=="")
        {
        alert("Enter Type Description");
         return 0;
        }
        
        else if(document.Hrm_TransJoinForm.crcode.value=="" ||document.Hrm_TransJoinForm.crcode.value.length<1)
        {
        	
        	if(document.Hrm_TransJoinForm.dbcode.value==""||document.Hrm_TransJoinForm.dbcode.value.length<1)
            {
        		 alert("Enter any of the Account Head Code");
        		 return 0;
           
            }
        }
       
        else if((document.getElementById("fundnature").value=="--Select Fund Nature--"))
        {
        alert("Select Fund Nature");
         return 0;
        }
        
            return 1;

}

function filter_real(evt,item,n,pre)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
// allow "." for one time 
         if(charCode==46){
                        //    alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)    return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
        //            alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                        //    alert("precision count ="+item.value.split(".")[1].length);
                                if(item.value.split(".")[1].length<pre) return true;
                                else return false;
                        }
                        return false;
        }else{
                        return false;
        }
}

function filter_spl(evt,item)
{
         var charCode = (evt.which) ? evt.which : event.keyCode
//alert(charCode);
        if(charCode==37||charCode==38||charCode==60||charCode==62||charCode==35)	 
        {
        	return false;
        }
        else{
        	return true;
        }	 
}




