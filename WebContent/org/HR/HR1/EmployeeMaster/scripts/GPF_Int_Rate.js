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
         
          
          
          
          var sel=rcells.item(1).firstChild.nodeValue;
         // alert(sel);
          var size=document.Hrm_TransJoinForm.year.length;
          for(var i=0;i<size;i++)
          {
                 // alert(sel.trim()+"   "+document.Hrm_TransJoinForm.year.options[i].value);
        	  if(document.Hrm_TransJoinForm.year.options[i].value==sel)
        	  {
        		  document.Hrm_TransJoinForm.year.options[i].selected='selected'; 
        		  document.Hrm_TransJoinForm.year.disabled=true;
        	  }
          }
          
          
          
          
          document.Hrm_TransJoinForm.rateofinterest.value=rcells.item(2).firstChild.nodeValue;
         var caltype=rcells.item(3).firstChild.nodeValue;
         
          document.Hrm_TransJoinForm.edate.value=rcells.item(4).firstChild.nodeValue;
          document.Hrm_TransJoinForm.prodate.value=rcells.item(5).firstChild.nodeValue;
          document.Hrm_TransJoinForm.prono.value=rcells.item(6).firstChild.nodeValue;
       // alert(rcells.item(6).lastChild.value);
          
          
          var index=rcells.item(6).lastChild.value.indexOf('null');
          if(index==0)
             document.Hrm_TransJoinForm.remarks.value='';
          else
          document.Hrm_TransJoinForm.remarks.value=rcells.item(6).lastChild.value;
          
         //alert(caltype);
         caltype=caltype;
          if(caltype=="Annual")
          {
          
          document.Hrm_TransJoinForm.caltype[0].checked='checked';
          }
          else
          { 
          document.Hrm_TransJoinForm.caltype[1].checked='checked';
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
                 
                    
                    var year=response.getElementsByTagName("year")[0].firstChild.nodeValue;
                   
                     var caltype=response.getElementsByTagName("caltype")[0].firstChild.nodeValue;
                     var edate=response.getElementsByTagName("edate")[0].firstChild.nodeValue;
                     var prodate=response.getElementsByTagName("prodate")[0].firstChild.nodeValue;
                     var prono=response.getElementsByTagName("prono")[0].firstChild.nodeValue;
                     var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                     alert("caltype------>"+caltype);
                     if(prodate==null||prodate=='null')
                    	 prodate='';
                     if(prono==null||prono=='null')
                    	 prono='';
                     if(edate==null||edate=='null')
                    	 edate='';
                     var apr=response.getElementsByTagName("apr")[0].firstChild.nodeValue;
                     var may=response.getElementsByTagName("may")[0].firstChild.nodeValue;
                     var jun=response.getElementsByTagName("jun")[0].firstChild.nodeValue;
                     var jul=response.getElementsByTagName("jul")[0].firstChild.nodeValue;
                     var aug=response.getElementsByTagName("aug")[0].firstChild.nodeValue;
                     var sep=response.getElementsByTagName("sep")[0].firstChild.nodeValue;
                     var oct=response.getElementsByTagName("oct")[0].firstChild.nodeValue;                 
                     var nov=response.getElementsByTagName("nov")[0].firstChild.nodeValue;
                     var dec=response.getElementsByTagName("dec")[0].firstChild.nodeValue;
                     var jan=response.getElementsByTagName("jan")[0].firstChild.nodeValue;
                     var feb=response.getElementsByTagName("feb")[0].firstChild.nodeValue;
                     var mar=response.getElementsByTagName("mar")[0].firstChild.nodeValue;
                     
                     document.getElementById("year").value=year;
                     document.getElementById("edate").value=edate;
                     document.getElementById("prodate").value=prodate;
                     document.getElementById("prono").value=prono;                                 
                     document.getElementById("remarks").value=remarks;
                     
                     document.getElementById("apr").value=apr;
                     document.getElementById("may").value=may;
                     document.getElementById("jun").value=jun;
                     document.getElementById("jul").value=jul;                                 
                     document.getElementById("aug").value=aug;
                     
                     document.getElementById("sep").value=sep;
                     document.getElementById("oct").value=oct;
                     document.getElementById("nov").value=nov;
                     document.getElementById("dec").value=dec;
                     document.getElementById("jan").value=jan;                                 
                     document.getElementById("feb").value=feb;
                     document.getElementById("mar").value=mar;
                     if(caltype=="Annual")
                    	 {
                    	 alert("Annual");
                    	 document.getElementById("caltype")[0].checked=true;
                    	 document.getElementById("caltype")[1].checked=false;
                    	 }
                     else
                    	 {
                    	 alert("monthly");
                    	 document.getElementById("caltype")[1].checked=true;
                    	 document.getElementById("caltype")[0].checked=false;
                    	 }
                     
                   //  alert(remarks);
              /*     var tr=document.createElement("TR");
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
                    var wid=document.createTextNode(year);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(rateofinterest);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                   
	                    var td3=document.createElement("TD");
	                    var inte=document.createTextNode(caltype);
	                    td3.appendChild(inte);
	                    tr.appendChild(td3);
	                    
	                    var td4=document.createElement("TD");
	                    var sub=document.createTextNode(edate);
	                    td4.appendChild(sub);
	                    tr.appendChild(td4);
                 
	                    var td5=document.createElement("TD");
	                    var dates=document.createTextNode(prodate);
	                    td5.appendChild(dates);
	                    tr.appendChild(td5);
	                    
	                    var td6=document.createElement("TD");
	                    var pro=document.createTextNode(prono);
	                    td6.appendChild(pro);
	                    var h1=document.createElement("TEXT");
	                    h1.type="hidden";
	                    h1.name="remarks";
	                    h1.value=remarks;
	                    td6.appendChild(h1);
	                    tr.appendChild(td6);
	                   
	                  
	                    
                    tlist.appendChild(tr);             
                    seq++;*/
                    
                }
                else
                    {
                     
                    document.getElementById("rateofinterest").value="";
                    document.getElementById("edate").value="";
                    document.getElementById("prodate").value="";
                  
                    document.getElementById("remarks").value="";
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
                    
                    var year=response.getElementsByTagName("year")[0].firstChild.nodeValue;
                    var rateofinterest=response.getElementsByTagName("rateofinterest")[0].firstChild.nodeValue;
                    var caltype=response.getElementsByTagName("caltype")[0].firstChild.nodeValue;
                    var edate=response.getElementsByTagName("edate")[0].firstChild.nodeValue;
                    var prodate=response.getElementsByTagName("prodate")[0].firstChild.nodeValue;
                    var prono=response.getElementsByTagName("prono")[0].firstChild.nodeValue;
                    var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                     if(remarks==null||remarks=='null')
                        remarks='';
                     
                   
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
                    var wid=document.createTextNode(year);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(rateofinterest);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                   
	                    var td3=document.createElement("TD");
	                    var inte=document.createTextNode(caltype);
	                    td3.appendChild(inte);
	                    tr.appendChild(td3);
	                    
	                    var td4=document.createElement("TD");
	                    var sub=document.createTextNode(edate);
	                    td4.appendChild(sub);
	                    tr.appendChild(td4);
                 
	                    var td5=document.createElement("TD");
	                    var dates=document.createTextNode(prodate);
	                    td5.appendChild(dates);
	                    tr.appendChild(td5);
	                    
	                    var td6=document.createElement("TD");
	                    var pro=document.createTextNode(prono);
	                    td6.appendChild(pro);
	                    var h1=document.createElement("TEXT");
	                    h1.type="hidden";
	                    h1.name="remarks";
	                    h1.value=remarks;
	                    td6.appendChild(h1);
	                    tr.appendChild(td6);
                    
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
                      alert("Record has already Exists 1");
                    
            }
          else  if(command=="Update")
            {
                if(flag=='success')
                {  
                     var tlist=document.getElementById("tlist");
                     
                     var year=response.getElementsByTagName("year")[0].firstChild.nodeValue;
                     var rateofinterest=response.getElementsByTagName("rateofinterest")[0].firstChild.nodeValue;
                     var caltype=response.getElementsByTagName("caltype")[0].firstChild.nodeValue;
                     var edate=response.getElementsByTagName("edate")[0].firstChild.nodeValue;
                     var prodate=response.getElementsByTagName("prodate")[0].firstChild.nodeValue;
                     var prono=response.getElementsByTagName("prono")[0].firstChild.nodeValue;
                     var remarks=response.getElementsByTagName("remarks")[0].firstChild.nodeValue;
                   
                    var readCell=document.getElementById(common);
                    var cells=readCell.cells;
                 
                    cells.item(1).firstChild.nodeValue=year;
                     cells.item(2).firstChild.nodeValue=rateofinterest;
                     cells.item(3).firstChild.nodeValue=caltype;
                     cells.item(4).firstChild.nodeValue=edate;
                     cells.item(5).firstChild.nodeValue=prodate;
                     cells.item(6).firstChild.nodeValue=prono;
                     cells.item(6).lastChild.Value=remarks;
                    
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
                    var len=response.getElementsByTagName("year").length;
                   
                    for(var i=0;i<len;i++)
                    {
                    	
                    	 var year=response.getElementsByTagName("year")[i].firstChild.nodeValue;
                         var rateofinterest=response.getElementsByTagName("rateofinterest")[i].firstChild.nodeValue;
                         var caltype=response.getElementsByTagName("caltype")[i].firstChild.nodeValue;
                         var edate=response.getElementsByTagName("edate")[i].firstChild.nodeValue;
                         var prodate=response.getElementsByTagName("prodate")[i].firstChild.nodeValue;
                         var prono=response.getElementsByTagName("prono")[i].firstChild.nodeValue;
                         var remarks=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                          
                   
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
                    var wid=document.createTextNode(year);
                    td1.appendChild(wid);
                    tr.appendChild(td1);
                    
                    var td2=document.createElement("TD");
                    var wdesc=document.createTextNode(rateofinterest);
                    td2.appendChild(wdesc);
                    tr.appendChild(td2);
                   
	                    var td3=document.createElement("TD");
	                    var inte=document.createTextNode(caltype);
	                    td3.appendChild(inte);
	                    tr.appendChild(td3);
	                    
	                    var td4=document.createElement("TD");
	                    var sub=document.createTextNode(edate);
	                    td4.appendChild(sub);
	                    tr.appendChild(td4);
                 
	                    var td5=document.createElement("TD");
	                    var dates=document.createTextNode(prodate);
	                    td5.appendChild(dates);
	                    tr.appendChild(td5);
	                    
	                    var td6=document.createElement("TD");
	                    var pro=document.createTextNode(prono);
	                    td6.appendChild(pro);
	                    var h1=document.createElement("TEXT");
	                    h1.type="hidden";
	                    h1.name="remarks";
	                    h1.value=remarks;
	                    td6.appendChild(h1);
	                    tr.appendChild(td6);
                    
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
	document.Hrm_TransJoinForm.year.disabled=false;
         document.Hrm_TransJoinForm.rateofinterest.value="";
        document.Hrm_TransJoinForm.edate.value="";
        document.Hrm_TransJoinForm.prodate.value="";
        document.Hrm_TransJoinForm.prono.value="";
        document.Hrm_TransJoinForm.remarks.value="";
        document.Hrm_TransJoinForm.caltype[0].checked='checked'; 
        document.Hrm_TransJoinForm.add.disabled=false; 
        document.Hrm_TransJoinForm.update.disabled=true;
        document.Hrm_TransJoinForm.delete1.disabled=true;   
       
         
      
}

function clearGPF()
{
          
	 document.Hrm_TransJoinForm.rateofinterest.value="";
     document.Hrm_TransJoinForm.edate.value="";
     document.Hrm_TransJoinForm.prodate.value="";
     document.Hrm_TransJoinForm.prono.value="";
     document.Hrm_TransJoinForm.remarks.value="";
   
      
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
     document.Hrm_TransJoinForm.caltype[0].checked='checked'; 
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
    
        
        var year,rateofinterest,caltype,edate,prodate,prono,remarks;
        
        var url="";
        if(command=="Add")
        { 
                if(nullCheck())
                {
                	
             	       	                	                	                	
                	year=document.getElementById("year").value;
                	rateofinterest=document.getElementById("rateofinterest").value;
                	//caltype=document.getElementById("caltype").value;
                	edate=document.getElementById("edate").value;
                	prodate=document.getElementById("prodate").value;
                	prono=document.getElementById("prono").value;
                	remarks=document.getElementById("remarks").value;
                	
                	 if (document.Hrm_TransJoinForm.caltype[0].checked)
                     {
                		 caltype=document.Hrm_TransJoinForm.caltype[0].value;
                     }
                     else
                      {
                    	 caltype=document.Hrm_TransJoinForm.caltype[1].value;
                      }
                	
          if(remarks=='')      	
             remarks='null';
           
          url="../../../../../GPF_Int_Rate?command=Add&year="+year+"&rateofinterest="+rateofinterest+"&caltype="+caltype+"&edate="+edate+"&prono="+prono+"&remarks="+remarks+"&prodate="+prodate;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
                }
    }
    else if(command=="Update")
    { 
    	   	       	    	
    	year=document.getElementById("year").value;
    	rateofinterest=document.getElementById("rateofinterest").value;
    	//caltype=document.getElementById("caltype").value;
    	edate=document.getElementById("edate").value;
    	prodate=document.getElementById("prodate").value;
    	prono=document.getElementById("prono").value;
    	remarks=document.getElementById("remarks").value;
    	 if(remarks=='')      	
             remarks='null';
           
    	
    	 if (document.Hrm_TransJoinForm.caltype[0].checked)
         {
    		 caltype=document.Hrm_TransJoinForm.caltype[0].value;
         }
         else
          {
        	 caltype=document.Hrm_TransJoinForm.caltype[1].value;
          }
            
         url="../../../../../GPF_Int_Rate?command=update&year="+year+"&rateofinterest="+rateofinterest+"&caltype="+caltype+"&edate="+edate+"&prono="+prono+"&remarks="+remarks+"&prodate="+prodate;  
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
    else if(command=="Delete")
    {                           
    	year=document.getElementById("year").value;
               
          url="../../../../../GPF_Int_Rate?command=Delete&year="+year;    
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
   
    else if(command=="get")
    {   
    	year=document.getElementById("year").value;  
        url="../../../../../GPF_Int_Rate_Monthly?command=get&year="+year;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
       xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null); 
    }
}
function nullCheck()
{
	
if((document.Hrm_TransJoinForm.rateofinterest.value==""))
{
alert("Enter the Rate of Interest");
return 0;
}

else if(document.Hrm_TransJoinForm.edate.value=="")
{
alert("Enter the Effective date");
return 0;
}

else if(document.Hrm_TransJoinForm.prodate.value=="")
{
alert("Enter the Proceedings Date");
return 0;
}
else if(document.Hrm_TransJoinForm.prono.value=="")
{
alert("Enter the Proceedings No");
return 0;
}
else if(document.Hrm_TransJoinForm.prono.value.length>40)
{
alert("Only 40 characters are allowed in Proceedings No");
return 0;
}
/*else if(document.Hrm_TransJoinForm.remarks.value=="")
  {
alert("Enter the remarks");
return 0;
}*/
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

function filter_real(evt,item,n,pre)
{//alert(pre);
         var charCode = (evt.which) ? evt.which : event.keyCode
// allow "." for one time 
         if(charCode==46){
                       //   alert("Position of . "+item.value.indexOf("."));
                                if(item.value.indexOf(".")<0)    return (item.value.length>0)?true:false;
                                else return false;
          }
         if (!(charCode > 31 && (charCode < 48 || charCode > 57))){
                // to avoid over flow
                        if(item.value.indexOf(".")<0){
                   //alert("Length without . ="+item.value.length);
                                return (item.value.length<n)?true:false;
                        }
                // dont allow more than 2 precision no's after the point
                        if(item.value.indexOf(".")>0){
                            //alert("precision count ="+item.value.split(".")[1].length);
                            //alert(item.value.split(".")[1].length<pre);
                                if(item.value.split(".")[1].length<pre)
                                {
                                	return true;
                                }
                                else
                                	{
                                	//alert("precision");
                                	return false;
                                	}
                        }
                        return false;
        }else{
                        return false;
        }
}



function splchar()
{
	var data=document.Hrm_TransJoinForm.rateofinterest.value;
	
	   var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\":<>?~_"; 
	   for (var i = 0; i < data.length; i++) {
	  	if (iChars.indexOf(data.charAt(i)) != -1) {
	  	  alert ("Rate Of Interest  has special characters. \nThese are not allowed.");
	  	document.Hrm_TransJoinForm.rateofinterest.value="";
	  	return false;
	  	}
	  }
}

function lengthcheck()
{
	 if(document.Hrm_TransJoinForm.prono.value.length>40)
	{
	alert("Only 40 characters are allowed in Proceedings No");
	return 0;
	}	
}

