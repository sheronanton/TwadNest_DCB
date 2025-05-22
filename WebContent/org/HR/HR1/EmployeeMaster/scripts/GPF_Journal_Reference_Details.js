
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
            	
                if(flag=='success')
                {
                	try{
                  	  var tlist=document.getElementById("tbody");
                      while (tlist.childNodes.length > 0) {
                     	 tlist.removeChild(tlist.firstChild);
                      }
                      var len=response.getElementsByTagName("voucharno").length;
                      
                      var unit=response.getElementsByTagName("unit")[0].firstChild.nodeValue;
                      var officeid=response.getElementsByTagName("officeid")[0].firstChild.nodeValue;
                      var cashyear=response.getElementsByTagName("cashyear")[0].firstChild.nodeValue;
                      var cashmonth=response.getElementsByTagName("cashmonth")[0].firstChild.nodeValue;
                      var acheadcode=response.getElementsByTagName("acheadcode")[0].firstChild.nodeValue;
                      
                      seq=0;
                      
                      var primaryk=unit+","+officeid+","+cashyear+","+cashmonth+","+acheadcode;
                      for(var i=0;i<len;i++)
                      {
                    	  flag1=1;
                      var voucharno=response.getElementsByTagName("voucharno")[i].firstChild.nodeValue;
                     
                      var vdate=response.getElementsByTagName("vdate")[i].firstChild.nodeValue;
                      var remarks=response.getElementsByTagName("remarks")[i].firstChild.nodeValue;
                      
                      var totamount=response.getElementsByTagName("totalamount")[i].firstChild.nodeValue;
                      
                      var trnamount=response.getElementsByTagName("trnamount")[i].firstChild.nodeValue;
                      
                      var checklistvalue=primaryk+","+voucharno+","+vdate+","+trnamount;
                      
                      var tr=document.createElement("TR");
                      tr.id=seq;
                      
                      var td=document.createElement("TD");
                      td.align='CENTER';
                      var check1=document.createElement("input");
                    // check1.setAttribute("type","checkbox");
                  	//check1.setAttribute("name","gpfchecking");
                  	//check1.setAttribute("value",checklistvalue);
                      check1.type="checkbox";
                      check1.name="gpfchecking[]";
                      check1.value=checklistvalue;
                      check1.id="a"+seq;
                      
                  	 td.appendChild(check1);
                  	tr.appendChild(td);
                  	
                  	var td1=document.createElement("TD");
                  	td1.align='CENTER';
                    var vouc=document.createTextNode(voucharno);
                    td1.appendChild(vouc);
                    tr.appendChild(td1);
                  	
                    var td2=document.createElement("TD");
                    td2.align='CENTER';
                    var date=document.createTextNode(vdate);
                    td2.appendChild(date);
                    tr.appendChild(td2);
                    
                    
                    var td3=document.createElement("TD");
                    var rem=document.createTextNode(remarks);
                    td3.appendChild(rem);
                    tr.appendChild(td3);
                    
                    var td4=document.createElement("TD");
                    td4.align='RIGHT';
                    var amt=document.createTextNode(trnamount);
                    td4.appendChild(amt);
                    tr.appendChild(td4);
                    
                    var td5=document.createElement("TD");
                    td5.align='CENTER';
                    var anc=document.createElement("A");
                    var url="javascript:pick('"+unit+"','"+officeid+"','"+cashyear+"','"+cashmonth+"','"+voucharno+"')";
                    anc.href=url;
                    var edit=document.createTextNode("Details");
                	   anc.appendChild(edit);
                    td5.appendChild(anc);
                    tr.appendChild(td5);
                    
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
                    }
                    
              if(flag1==0)
              {
            	  alert('Data Not Found'); 
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
var acc_unit=document.getElementById("cmbAcc_UnitCode").value;
var office_id=document.getElementById("cmbOffice_code").value;
var cashmonth=document.getElementById("txtCB_Month").value;
var cashyear=document.getElementById("txtCB_Year").value;
var acheadcode=document.getElementById("acheadcode").value;


var url="../../../../../GPF_Journal_Reference_Details?command=get&acc_unit="+acc_unit+"&office_id="+office_id+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&acheadcode="+acheadcode+"";
url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,true);
xmlhttp.onreadystatechange=stateChanged;
xmlhttp.send(null);  
	}


}


var gpf_journal_list;

function pick(unitcode,offid,yr,mon,recNo)
{
    if (gpf_journal_list && gpf_journal_list.open && !gpf_journal_list.closed) 
    {
    	gpf_journal_list.resizeTo(500,500);
    	gpf_journal_list.moveTo(250,250); 
    	gpf_journal_list.focus();
    }
    else
    {
    	gpf_journal_list=null;
    }
    gpf_journal_list= window.open("../../../../../org/FAS/FAS1/JournalSystem/jsps/GPF_Journal_List.jsp?cmbAcc_UnitCode="+unitcode+"&cmbOffice_code="+offid+"&yr="+yr+"&mon="+mon+"&recNo="+recNo,"ReceiptDateSearch","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
    gpf_journal_list.moveTo(250,250);  
    gpf_journal_list.focus();
    
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
	   
	    

	    var url="../../../../../GPF_Journal_Reference_Details?command=update&unitid="+unitid+"&officeid="+officeid+"&cashmonth="+cashmonth+"&cashyear="+cashyear+"&acheadcode="+acheadcode+"&vno="+vno+"&vdate="+vdate+"&trnamount="+trnamount+"";
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


function clear()
{
	document.gpfupdateform.txtCB_Month[0].selected='selected';
	document.gpfupdateform.acheadcode[0].selected='selected';
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










