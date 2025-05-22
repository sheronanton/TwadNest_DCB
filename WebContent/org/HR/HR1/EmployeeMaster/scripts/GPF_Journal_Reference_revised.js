
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
            	var flag1=0;
            /*	if(flag=='Exist')
            	  alert("Records  already Freezed-Cant update journal");
            	else*/
            		
            		if(flag=='success')
                {
                	try{
                  	  var tlist=document.getElementById("tbody");
                      while (tlist.childNodes.length > 0) {
                     	 tlist.removeChild(tlist.firstChild);
                      }
                      var len=response.getElementsByTagName("vno").length;
                      
                    /*  var unit=response.getElementsByTagName("unit")[0].firstChild.nodeValue;
                      var officeid=response.getElementsByTagName("officeid")[0].firstChild.nodeValue;
                      var cashyear=response.getElementsByTagName("cashyear")[0].firstChild.nodeValue;
                      var cashmonth=response.getElementsByTagName("cashmonth")[0].firstChild.nodeValue;
                      var acheadcode=response.getElementsByTagName("acheadcode")[0].firstChild.nodeValue;*/
                      
                      seq=0;
                      
                     
                      for(var i=0;i<len;i++)
                      {
                    	  flag1=1;
                      var voucharno=response.getElementsByTagName("vno")[i].firstChild.nodeValue;
                     
                      var vdate=response.getElementsByTagName("vdate")[i].firstChild.nodeValue;
                      var headcode=response.getElementsByTagName("headcode")[i].firstChild.nodeValue;
                      
                      var sno=response.getElementsByTagName("sno")[i].firstChild.nodeValue;
                      
                      var crdr=response.getElementsByTagName("crdr")[i].firstChild.nodeValue;
                      
                      var amount=response.getElementsByTagName("amount")[i].firstChild.nodeValue;
                      
                      
                      var adjyear=response.getElementsByTagName("acyear")[i].firstChild.nodeValue;
                      var adjmonth=response.getElementsByTagName("acmonth")[i].firstChild.nodeValue;
                      
                      var headdesc=response.getElementsByTagName("headdesc")[i].firstChild.nodeValue;
                      
                      if(adjyear==0)
                      {
                    	  adjyear="";
                      }
                      if(adjmonth==0)
                      {
                    	  adjmonth="";
                      }
                      
                      
                      var tr=document.createElement("TR");
                      tr.id=seq;
                      
                      
                      
                      cell2=document.createElement("TD");
                      cell2.setAttribute('align','left');
                      var sch_id=document.createElement("input");
                      sch_id.type="hidden";
                      sch_id.name="vocherno";
                      sch_id.value=voucharno;
                      
                      cell2.appendChild(sch_id);
                      var currentText=document.createTextNode(voucharno);
                      cell2.appendChild(currentText);
                      tr.appendChild(cell2);
                      
                      
                      cell2=document.createElement("TD");
                      cell2.setAttribute('align','left');
                      var sch_id=document.createElement("input");
                      sch_id.type="hidden";
                      sch_id.name="vdate";
                      sch_id.value=vdate;
                      cell2.appendChild(sch_id);
                      var currentText=document.createTextNode(vdate);
                      cell2.appendChild(currentText);
                      tr.appendChild(cell2);
                      
                      cell2=document.createElement("TD");
                      cell2.setAttribute('align','left');
                      var sch_id=document.createElement("input");
                      sch_id.type="hidden";
                      sch_id.name="headcode";
                      sch_id.value=headcode;
                      cell2.appendChild(sch_id);
                      var currentText=document.createTextNode(headcode+"-"+headdesc);
                      cell2.appendChild(currentText);
                      tr.appendChild(cell2);
                      
                      cell2=document.createElement("TD");
                      cell2.setAttribute('align','left');
                      var sch_id=document.createElement("input");
                      sch_id.type="hidden";
                      sch_id.name="serialno";
                      sch_id.value=sno;
                      cell2.appendChild(sch_id);
                      var currentText=document.createTextNode(sno);
                      cell2.appendChild(currentText);
                      tr.appendChild(cell2);
                      
                      
                      cell2=document.createElement("TD");
                      cell2.setAttribute('align','left');
                      var sch_id=document.createElement("input");
                      sch_id.type="hidden";
                      sch_id.name="crdr";
                      sch_id.value=crdr;
                      cell2.appendChild(sch_id);
                      var currentText=document.createTextNode(crdr);
                      cell2.appendChild(currentText);
                      tr.appendChild(cell2); 
                      
                      cell2=document.createElement("TD");
                      cell2.setAttribute('align','right');
                      var sch_id=document.createElement("input");
                      sch_id.type="hidden";
                      sch_id.name="amount";
                      sch_id.value=amount;
                      cell2.appendChild(sch_id);
                      var currentText=document.createTextNode(amount);
                      cell2.appendChild(currentText);
                      tr.appendChild(cell2); 
                      
                      cell2=document.createElement("TD");
                      cell2.setAttribute('align','center');
                      var sch_id=document.createElement("input");
                      sch_id.type="text";
                      sch_id.name="adjyear";
                      sch_id.value=adjyear;
                      sch_id.size=4;
                      sch_id.maxlength=4;
                     // sch_id.onkeypress="return filter_real(event,this,4,0)";
                      cell2.appendChild(sch_id);
                      var currentText=document.createTextNode("");
                      cell2.appendChild(currentText);
                      tr.appendChild(cell2); 
                      
                      
                      cell2=document.createElement("TD");
                      cell2.setAttribute('align','center');
                      var sch_id=document.createElement("input");
                      sch_id.type="text";
                      sch_id.name="adjmonth";
                      sch_id.value=adjmonth;
                      sch_id.size=2;
                      sch_id.maxlength=2;
                      cell2.appendChild(sch_id);
                      var currentText=document.createTextNode("");
                      cell2.appendChild(currentText);
                      tr.appendChild(cell2); 
                      
                 
                    
                    tlist.appendChild(tr);             
                    seq++;
                      }
                     var d1=document.getElementById("submit1");
                    	       	  d1.style.display="block";
                    
                	}catch(e){alert(e);}
                	
                }
                else
                    {
                    alert('Data Not Found');
                    var d1=document.getElementById("submit1");
      	       	  d1.style.display="none";
      	       	var tbody=document.getElementById("tbody");
      	      var t=0;
      	      for(t=tbody.rows.length-1;t>=0;t--)
      	      {
      	         tbody.deleteRow(0);
      	      }
                    }
                    
             
            } 
          
       }
    }
}






function call()
{
	
xmlhttp=getxmlhttpObject();
if(xmlhttp==null)
{
    alert("Your borwser doesnot support AJAX");
    return;
    }   
  var flag=0;
  if(document.getElementById("Acc_unit_code").value=="")
	{
		alert('Please Select Accounting Unit Id');
		
		return false;
	}
  if(document.getElementById("txtCB_Month").value=="")
	{
		alert('Please Select Cash Book Month');
		
		return false;
	}

  
	if(document.getElementById("txtCB_Year").value=="")
	{
		alert('Please Enter Cash Book Year');
		flag=1;
		return false;
	}
	if(document.getElementById("txtCB_Year").value!="")
	{
		
		 v=new Date();
		 mn=v.getMonth();
		 mn=parseInt(mn)+1;
		 yr=v.getFullYear();
		
	    	var cashyear=document.getElementById("txtCB_Year").value;	
	    	var cashmonth=document.getElementById("txtCB_Month").value;
	    	
	    	
	    	if(cashyear.length<4)
	    	{
	    		 alert("Give Correct format(YYYY) of Year");
	    		 flag=1;
	 		    return false;
	    	}
	    		    	
	    	if(parseInt(cashyear)>parseInt(yr))
	    	{
	    		 alert("Year should not be greater than current year");
	    		 flag=1;
	    		    return false;
	    	}
	    	 if(parseInt(cashyear)==parseInt(yr))
	    	 {
	    		 if(parseInt(cashmonth)>parseInt(mn))
	    		 {
	    			 alert("Month should not be greater than current month");
	    			 flag=1;
	     		    return false; 
	    		 }
	    	 }
	    }
	
	
	
	
	
	if(flag==0){
var acc_unit=document.getElementById("unit_name").value;
//var office_id=document.getElementById("cmbOffice_code").value;
var cashmonth=document.getElementById("txtCB_Month").value;
var cashyear=document.getElementById("txtCB_Year").value;
//var acheadcode=document.getElementById("acheadcode").value;
var acchead=document.getElementById("acchead").value;

var url="../../../../../GPF_Journal_Reference_revised?command=get&acc_unit="+acc_unit+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&acchead="+acchead;
url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,true);
xmlhttp.onreadystatechange=stateChanged;
xmlhttp.send(null);  
	}


}


function updategpftable()
{
		
	
	var c_value = "";
	
	var currRow = document.getElementById("tbody").rows.length;
	var kk=0;
	for (var i=0; i <currRow; i++)
	   {
		var ii="a"+i;
	   if (document.getElementById(ii).checked)
	      {
	      c_value =document.getElementById(ii).value;
	      
	    kk=1;
	      
	    allvalues=c_value.split(",");
	    var unitid=allvalues[0];
	    var officeid=allvalues[1];
	    var cashyear=allvalues[2];
	    var cashmonth=allvalues[3];
	    var acheadcode=allvalues[4];
	    var vno=allvalues[5];
	    var vdate=allvalues[6]; 
	    var trnamount=allvalues[7]; 
	   
	    

	    var url="../../../../../GPF_Journal_Reference_revised_Details?command=update&unitid="+unitid+"&officeid="+officeid+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&acheadcode="+acheadcode+"&vno="+vno+"&vdate="+vdate+"&trnamount="+trnamount+"";
	    url=url+"&sid="+Math.random();
	    xmlhttp.open("GET",url,false);
	   // xmlhttp.onreadystatechange=stateChanged;
	    xmlhttp.send(null);
	    
	    
	    
	     
	      }
	   }
	if(kk==1)
	alert('Successfully updated');
	clear();
}


function clear1()
{
	//alert('d');
	document.gpfupdateform.txtCB_Month[0].selected='selected';
	//document.gpfupdateform.acheadcode[0].selected='selected';
	document.getElementById("txtCB_Year").value="";
	var tbody=document.getElementById("tbody");
    var t=0;
    for(t=tbody.rows.length-1;t>=0;t--)
    {
       tbody.deleteRow(0);
    }
    
    var d1=document.getElementById("submit1");
 	  d1.style.display="none";
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


function validatevalues()
{
	 v=new Date();
	 mn=v.getMonth();
	 mn=parseInt(mn)+1;
	 yr=v.getFullYear();
	 var vocherno = new Array();
	 var vdate = new Array();
	 var headcode = new Array();
	 var serialno = new Array();
	 var crdr = new Array();
	 var amount = new Array();
	 var adjyear = new Array();
	 var adjmonth = new Array();
	 
	var currRow = document.getElementById("tbody").rows.length;
	var kk=0;
	var flag=0;
	for (var i=0; i <currRow; i++)
	   {
		var sno=i;
		sno=sno+1;
		var r = document.getElementById(i);
		var rcells = r.cells;
		cashyear=rcells.item(6).firstChild.value;
		cashmonth=rcells.item(7).firstChild.value;
		 
		//
		vocherno[i]=rcells.item(0).firstChild.value;
		vdate[i]=rcells.item(1).firstChild.value;
		headcode[i]=rcells.item(2).firstChild.value;
		serialno[i]=rcells.item(3).firstChild.value;
		crdr[i]=rcells.item(4).firstChild.value;
		amount[i]=rcells.item(5).firstChild.value;
		adjyear[i]=rcells.item(6).firstChild.value;
		adjmonth[i]=rcells.item(7).firstChild.value;
		//
		
		if(cashyear!="" && cashmonth!="")
		{
		 if(cashyear.length<4 || cashyear.length>4 )
	    	{
	    		 alert("Give Correct format(YYYY) of Year in ROW "+sno);
	    		
	 		    return false;
	    	}
	    		    	
	    	if(parseInt(cashyear)>parseInt(yr))
	    	{
	    		 alert("Year should not be greater than current year in ROW "+sno);
	    		
	    		    return false;
	    	} 
	    	if(cashmonth.length>2 )
	    	{
	    		 alert("Give Correct format(MM) of Month in ROW "+sno);
	    		
	 		    return false;
	    	}
	    	
	    	if(parseInt(cashmonth)>12 || parseInt(cashmonth)<1)
	    	{
	    		 alert("Month Should be 1 to 12 in ROW "+sno);
	    		
	 		    return false;
	    	}
	    	
	    	
	    	if(parseInt(cashyear)==parseInt(yr))
	    	 {
	    		 if(parseInt(cashmonth)>parseInt(mn))
	    		 {
	    			 alert("Month should not be greater than current month in ROW "+sno);
	    			
	     		    return false; 
	    		 }
	    	 }
	    	flag=1;
		}
		 
		
	   }
	
	/*if(flag==0)
	{
		alert('Please Enter atleast one ADJ.Year and ADJ.Month');
		return false;
	}*/
	var unit_name=document.gpfupdateform.unit_name.value;
	var acchead=document.gpfupdateform.acchead.value;
	var txtCB_Year=document.gpfupdateform.txtCB_Year.value;
	var txtCB_Month=document.gpfupdateform.txtCB_Month.value;
	var url="../../../../../GPF_Journal_Reference_revised?Command=Add&vocherno="+vocherno+"&vdate="+vdate+"&headcode="+headcode+"&serialno="+serialno+"&crdr="+crdr+"&amount="+amount+"&adjyear="+adjyear+"&"+
	"adjmonth="+adjmonth+"&unit_name="+unit_name+"&acchead="+acchead+"&txtCB_Year="+txtCB_Year+"&txtCB_Month="+txtCB_Month;
	
		// alert(url);
    xmlhttp.open("POST",url,true);
    xmlhttp.onreadystatechange=function()
    {       
    	//alert(xmlhttp);
        if(xmlhttp.readyState==4)
        { 
        //	alert("1"+xmlhttp);
            if(xmlhttp.status==200)
            {  
            	  
            var baseResponse=xmlhttp.responseXML.getElementsByTagName("response")[0];
            	 //  alert(baseResponse);
            	   var stat=baseResponse.getElementsByTagName("status")[0].firstChild.nodeValue;
            		
            		if(stat=="Insert")
            		{
            			var count=baseResponse.getElementsByTagName("count")[0].firstChild.nodeValue;
            			alert(count+" Records Inserted Into Database Successfully");
            		}
            		else
            		{
            			alert("Data freezed for "+stat);
            		}
            }
        }
    }
    xmlhttp.send(null);
	//return true;
	
	
}


function getunit()
{	
	
       var unit_id=document.getElementById("unit_name").value;
        //alert("unit_id"+unit_id);
       document.getElementById("Acc_unit_code").value=unit_id;

}      


