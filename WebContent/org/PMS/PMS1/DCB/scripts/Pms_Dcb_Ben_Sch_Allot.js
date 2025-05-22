var Alloted_flag_val=0; 
function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {
         // code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)
    {
        // code for IE6, IE5
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}
function loadbeneficiarytype()
{
    
    
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=loadbeneficiarytype";
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbeneficiarytype(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadbeneficiarytype(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadbeneficiarytype")
            {
                if(flagres=='success')
                {
                      
                    var BEN_TYPE_ID_len=baseres.getElementsByTagName("BEN_TYPE_ID").length;
                    for(var i=0;i<BEN_TYPE_ID_len;i++)
                     {
                         var BEN_TYPE_ID=baseres.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
                         
                         var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
                         
                         var BEN_TYPE_SDESC=baseres.getElementsByTagName("BEN_TYPE_SDESC")[i].firstChild.nodeValue;
              
                         
                        
                        
                         addOptionBeneficiary_type(document.Pms_Dcb_Ben_Sch_Allot.Beneficiary_type,BEN_TYPE_DESC,BEN_TYPE_ID);
                     }
                   
                     
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
           
        }
    }
}
function addOptionBeneficiary_type(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
/*function loadbeneficiaryname()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    //  Alloted_flag_value
    
    url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=loadbeneficiaryname&Beneficiary_type="+Beneficiary_type;
    alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbeneficiaryname(xmlhttp);
            }
    xmlhttp.send(null);
}*/
function stateChangedloadbeneficiaryname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.Pms_Dcb_Ben_Sch_Allot.Beneficiary_Name.length=1;
        if(xmlhttp.status==200)
        {
             if((commandres=="loadbeneficiaryname"))
            {
                if(flagres=='success')
                {
              
                    
                    
                    var BENEFICIARY_SNO_len=baseres.getElementsByTagName("BENEFICIARY_SNO").length;
                    //alert(beneficiary_sno_len);
                    for(var i=0;i<BENEFICIARY_SNO_len;i++)
                     {
                         var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                         //var ALLOTED_FLG=baseres.getElementsByTagName("ALLOTED_FLG")[i].firstChild.nodeValue;
                        // alert(ALLOTED_FLG);
                        // document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.value=ALLOTED_FLG;
                         addOptionbeneficiaryname(document.Pms_Dcb_Ben_Sch_Allot.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
                     }
                   
                     
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
           
        }
    }
}
function addOptionbeneficiaryname(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function fun_Alloted_flag()
{
	
/*	for( i = 0; i < document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.length; i++ )
    {
        if(document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].checked)
        {
        	Alloted_flag_val=1;
        	Alloted_flag_value = document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].value;
          // alert(Alloted_flag_value);
        	
        	if(Alloted_flag_value=='B')
        	{
        		var d=document.getElementById("schemeload");
                d.style.display="none";
        	}
        	else
        	{
        		var d=document.getElementById("schemeload");
                d.style.display="block";
        	}*/
           var xmlhttp=GetXmlHttpObject();
           if (xmlhttp==null)
            {
                alert ("Your browser does not support AJAX!");
                return;
            }
             var Beneficiary_type=document.getElementById("Beneficiary_type").value;
           //  Alloted_flag_value
           
           url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=loadbeneficiaryname&Beneficiary_type="+Beneficiary_type;
         //  alert(url);
           url=url+"&sid="+Math.random();
           xmlhttp.open("GET",url,true);
           xmlhttp.onreadystatechange= function()
                   {
                       stateChangedloadbeneficiaryname(xmlhttp);
                   }
           xmlhttp.send(null);
            

     //  }
       
   //}
}
function loadschemes()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
     /* for( i = 0; i < document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.length; i++ )
      {
          if(document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].checked)
          {
          	Alloted_flag_val=1;
          	Alloted_flag_value = document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].value;
          }
      }*/
    /*if(Alloted_flag_value=='S')
    {
    url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=loadschemename&Beneficiary_Name="+Beneficiary_Name;
    //alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadschemename(xmlhttp);
            }
    xmlhttp.send(null);
    }*/
}
function stateChangedloadschemename(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.Pms_Dcb_Ben_Sch_Allot.schemename.length=1;
        if(xmlhttp.status==200)
        {
             if((commandres=="loadschemename"))
            {
                if(flagres=='success')
                {
              
                    
                    
                    var SCHEME_SNO_len=baseres.getElementsByTagName("SCHEME_SNO").length;
                    //alert(beneficiary_sno_len);
                    for(var i=0;i<SCHEME_SNO_len;i++)
                     {
                         var SCHEME_SNO=baseres.getElementsByTagName("SCHEME_SNO")[i].firstChild.nodeValue;
                         var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[i].firstChild.nodeValue;
                     
                         addOptionschemename(document.Pms_Dcb_Ben_Sch_Allot.schemename,SCH_NAME,SCHEME_SNO);
                     }
                   
                     
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
           
        }
    }
}
function addOptionschemename(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function exitwindow()
{
    window.close();
}
function funsave()
{
	var allotflag;
	var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	var Allotted_Qty=document.getElementById("Allotted_Qty").value;
	var Min_bill_Qty=document.getElementById("Min_bill_Qty").value;
	var Excess_Tariff_Rate=document.getElementById("Excess_Tariff_Rate").value;
	var validcheckval=validation();
	if(validcheckval==true)
	{
	//for( i = 0; i < document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.length; i++ )
   // {
       // if(document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].checked)
       // {
        	
       	allotflag = document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.value;
        	if(allotflag=='B')
        	{
        		var xmlhttp=GetXmlHttpObject();
        	    if (xmlhttp==null)
        	     {
        	         alert ("Your browser does not support AJAX!");
        	         return;
        	     }
        	    url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=insertben&Beneficiary_Name="+Beneficiary_Name+"&Allotted_Qty="+Allotted_Qty+"&Min_bill_Qty="+Min_bill_Qty+"&Excess_Tariff_Rate="+Excess_Tariff_Rate;
        	 // alert(url);
        	    url=url+"&sid="+Math.random();
        	    xmlhttp.open("GET",url,true);
        	    xmlhttp.onreadystatechange= function()
        	            {
        	                 stateChangedloadinsertben(xmlhttp);
        	            }
        	    xmlhttp.send(null); 	
        	    
        	}
        	else if (allotflag=='S')
        	{
        		var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
        		var Allotted_Qty=document.getElementById("Allotted_Qty").value;
        		var Min_bill_Qty=document.getElementById("Min_bill_Qty").value;
        		var Excess_Tariff_Rate=document.getElementById("Excess_Tariff_Rate").value;
        		var schemename=document.getElementById("schemename").value;
        		var xmlhttp=GetXmlHttpObject();
        	    if (xmlhttp==null)
        	     {
        	         alert ("Your browser does not support AJAX!");
        	         return;
        	     }
        	    url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=insertsch&Beneficiary_Name="+Beneficiary_Name+"&Allotted_Qty="+Allotted_Qty+"&Min_bill_Qty="+Min_bill_Qty+"&Excess_Tariff_Rate="+Excess_Tariff_Rate+"&schemename="+schemename;
        	  //  alert(url);
        	    url=url+"&sid="+Math.random();
        	    xmlhttp.open("GET",url,true);
        	    xmlhttp.onreadystatechange= function()
        	            {
        	                stateChangedloadinsertsch(xmlhttp);
        	            }
        	    xmlhttp.send(null); 	
        	}
        	
        }
    //}
	//}
}
function stateChangedloadinsertben(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(xmlhttp.status==200)
        {
             if((commandres=="insertben"))
            {
                if(flagres=='success')
                {
                	//countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
                 //   if(countinsert==0) 
                     { 
                    	alert('Sucessfully Added');
                     }
                 //   else if(countinsert==1)
                 //   {
                         //alert("Record is already present");
                 //         
                //s    }
                 
                }
               // loadvaluesgrid();
                
                loadallotedflag();
            }
        }
    }
}
function stateChangedloadinsertsch(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        if(xmlhttp.status==200)
        {
             if((commandres=="insertsch"))
            {
                if(flagres=='success')
                {
                	countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
                    if(countinsert==0) 
                     { 
                    	alert('Sucessfully Added');
                     }
                    else if(countinsert==1)
                    {
                          alert("Record is already present");
                          
                    }
                }
                	loadgrid();
                	
                }
            }
        }
    }

function validation()
{
	flag=0;
	var Beneficiary_type=document.getElementById("Beneficiary_type").value;
	
	var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	var Alloted_flag = document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.value;
	var schemename=document.getElementById("schemename").value;
	var Allotted_Qty=document.getElementById("Allotted_Qty").value;
	var Min_bill_Qty=document.getElementById("Min_bill_Qty").value;
	var Excess_Tariff_Rate=document.getElementById("Excess_Tariff_Rate").value;
	if(Beneficiary_type=="")
	{
		alert("Select Beneficiary type");
		flag=1;
		return false;
	}
	
	
		if(Beneficiary_Name=="")
		{
			alert("Select Beneficiary Name ");
			flag=1;
			return false;
		}
	
	
	if(Alloted_flag=='S')
	{
		if((Beneficiary_Name!="")&&(schemename==""))
		{
			alert("Select Scheme Name ");
			flag=1;
			return false;
		}
	}
	if(Allotted_Qty=="")
	{
		alert("Enter Allotted Quantity");
		flag=1;
		return false;
	}
	if(Min_bill_Qty=="")
	{
		alert("Enter Minimum Billing Quantity");
		flag=1;
		return false;
	}
	if(Excess_Tariff_Rate=="")
	{
		alert("Enter Excess Tariff Rate");
		flag=1;
		return false;
	}
	if(flag==0)
	{
		return true;
	}
}
function numonly(e)
{
    
    var unicode=e.charCode? e.charCode : e.keyCode
  //  alert(unicode);
    if (unicode!=8)//backspace
    { 
        if (unicode<45||unicode>57||unicode==47) 
            return false ;
    }
}  
function refresh()
{
	document.getElementById("Allotted_Qty").value="";
	document.getElementById("Min_bill_Qty").value="";
	document.getElementById("Excess_Tariff_Rate").value="";
	window.location.reload();
}
function loadallotedflag()
{
	var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=loadallotedflag&Beneficiary_Name="+Beneficiary_Name;
	   // alert("url"+url);
	    url=url+"&sid="+Math.random();
	    xmlhttp.open("GET",url,true);
	    xmlhttp.onreadystatechange= function()
	    {
	       stateChangedloadallotedflag(xmlhttp);
	    }
	    xmlhttp.send(null);
}
function stateChangedloadallotedflag(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
   
    if(xmlhttp.readyState==4)
    {
    	try
    	{
    	document.getElementById("Alloted_flag").value="";
    	baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        var recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;
       var ALLOTED_FLG=baseres.getElementsByTagName("ALLOTED_FLG")[0].firstChild.nodeValue;
    	}
    	catch(e)
    	{
    		
    	}
    	if(xmlhttp.status==200)
        {
             if(commandres=="loadallotedflag")
            {
                if(flagres=='success')
                {
                	
                	if(recordfound!=0)
                	{
                		document.getElementById("Alloted_flag").value=ALLOTED_FLG;
                		temp=document.getElementById("Alloted_flag").value;
                	//	alert("temp"+temp);
                			if(temp=='B')
                			{	
                				//alert("ssdsd");
                				var d=document.getElementById("schemeload");
                                d.style.display="none";
                                var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
                				url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=valuesgrid&Beneficiary_Name="+Beneficiary_Name+"&Alloted_flag_value="+temp;
                				//    alert("url"+url);
                				    url=url+"&sid="+Math.random();
                				    xmlhttp.open("GET",url,true);
                				    xmlhttp.onreadystatechange= function()
                				    {
                				       stateChangedloadvaluesgrid(xmlhttp);
                				    }
                				    xmlhttp.send(null);
                			}
                			if(temp=='S')
                		    {
                				var d=document.getElementById("schemeload");
                                d.style.display="block";
                                var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
                                //var schemename=document.getElementById("schemename").value;
                		    url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=loadschemename&Beneficiary_Name="+Beneficiary_Name;
                		 //   alert(url);
                		    url=url+"&sid="+Math.random();
                		    xmlhttp.open("GET",url,true);
                		    xmlhttp.onreadystatechange= function()
                		            {
                		                 stateChangedloadschemename(xmlhttp);
                		            }
                		    xmlhttp.send(null);
                		    }
                	}
                	else
                	{
                	         			alert("Record is not present");
                	}
                }
            }
        }
    }
}
function loadvaluesgrid()
{
	//alert("Sdsd");
	var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
/*	for( i = 0; i < document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.length; i++ )
    {
        if(document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].checked)
        {
        	Alloted_flag_val=1;
        	Alloted_flag_value = document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].value;
        }
    }*/
    Alloted_flag_value = document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.value;
	var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	var Allotted_Qty=document.getElementById("Allotted_Qty").value;
	var Min_bill_Qty=document.getElementById("Min_bill_Qty").value;
	var Excess_Tariff_Rate=document.getElementById("Excess_Tariff_Rate").value;
	//url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=valuesgrid&Beneficiary_Name="+Beneficiary_Name+"&Alloted_flag_value="+Alloted_flag_value;
	if(Alloted_flag_value=='B')
	{
	url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=valuesgrid&Beneficiary_Name="+Beneficiary_Name+"&Alloted_flag_value="+Alloted_flag_value;
	
	   // alert("url"+url);
	    url=url+"&sid="+Math.random();
	    xmlhttp.open("GET",url,true);
	    xmlhttp.onreadystatechange= function()
	    {
	       stateChangedloadvaluesgrid(xmlhttp);
	    }
	    xmlhttp.send(null);
	}
} 
function stateChangedloadvaluesgrid(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
   
    if(xmlhttp.readyState==4)
    {
    	tbody=document.getElementById("getvaluerows");
    	try
        {
    		tbody.innerHTML="";
    	}
        catch(e) 
        {
        	tbody.innerText="";
        }
      
    	baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        var recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;
       
    	if(xmlhttp.status==200)
        {
             if(commandres=="valuesgrid")
            {
                if(flagres=='success')
                {
                	
                	if(recordfound!=0)
                		{
                           
                			tablebody=document.getElementById("getvaluerows");
                             var len=baseres.getElementsByTagName("BENEFICIARY_SNO").length;
                          
                             for(var i=0;i<len;i++)
                             {
                            	
                            	 var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                            	
                                 var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                                 
                                 var ALLOTED_QTY=baseres.getElementsByTagName("ALLOTED_QTY")[i].firstChild.nodeValue;
                               
                                 var MIN_BILL_QTY=baseres.getElementsByTagName("MIN_BILL_QTY")[i].firstChild.nodeValue;
                                
                                 var EXCESS_TARIFF_RATE=baseres.getElementsByTagName("EXCESS_TARIFF_RATE")[i].firstChild.nodeValue;
                                 
                                 rowvalue=document.createElement("TR");
                                 rowvalue.id=BENEFICIARY_SNO;
                                 
                               
                                 var tabledata=document.createElement("TD");
                                 var BENEFICIARY_NAME_val=document.createTextNode(BENEFICIARY_NAME);
                                 tabledata.appendChild(BENEFICIARY_NAME_val);
                                 rowvalue.appendChild(tabledata);
                                 
                                  
                                 
                                 var tabledata1=document.createElement("TD");
                                  var ALLOTED_QTY_val=document.createTextNode(ALLOTED_QTY);
                                  tabledata1.appendChild(ALLOTED_QTY_val);
                                  rowvalue.appendChild(tabledata1);
                                  
                                  var tabledata2=document.createElement("TD");
                                  var MIN_BILL_QTY_val=document.createTextNode(MIN_BILL_QTY);
                                  tabledata2.appendChild(MIN_BILL_QTY_val);
                                  rowvalue.appendChild(tabledata2);
                                  
                                  
                                  var tabledata3=document.createElement("TD");
                                  var EXCESS_TARIFF_RATE_val=document.createTextNode(EXCESS_TARIFF_RATE);
                                  tabledata3.appendChild(EXCESS_TARIFF_RATE_val);
                                  rowvalue.appendChild(tabledata3);
                               
                                  
                                  
                                 tablebody.appendChild(rowvalue);
                             }
                		}
                		else
                		{
                			tbody=document.getElementById("getvaluerows");
                	    	try
                	        {
                	    		tbody.innerHTML="";
                	    	}
                	        catch(e) 
                	        {
                	        	tbody.innerText="";
                	        }
                			alert("Record is not present");
                		}
                }
            }
        }
    }
}

function loadgrid()
{
			
			var xmlhttp=GetXmlHttpObject();
		    if (xmlhttp==null)
		     {
		         alert ("Your browser does not support AJAX!");
		         return;
		     }
			/*for( i = 0; i < document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.length; i++ )
		    {
		        if(document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].checked)
		        {
		        	Alloted_flag_val=1;
		        	Alloted_flag_value = document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag[i].value;
		        }
		    }*/
		    Alloted_flag_value = document.Pms_Dcb_Ben_Sch_Allot.Alloted_flag.value;
			var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
			var Allotted_Qty=document.getElementById("Allotted_Qty").value;
			var Min_bill_Qty=document.getElementById("Min_bill_Qty").value;
			var Excess_Tariff_Rate=document.getElementById("Excess_Tariff_Rate").value;
			var schemename=document.getElementById("schemename").value;
			if(Alloted_flag_value=='S')
			{
				url="../../../../../Pms_Dcb_Ben_Sch_Allot?command=loadgrid&Beneficiary_Name="+Beneficiary_Name+"&Alloted_flag_value="+Alloted_flag_value+"&schemename="+schemename;
			    url=url+"&sid="+Math.random();
			    xmlhttp.open("GET",url,true);
			    xmlhttp.onreadystatechange= function()
			    {
			       stateChangedloadgrid(xmlhttp);
			    }
			    xmlhttp.send(null);
			}
}  
function stateChangedloadgrid(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
   
    if(xmlhttp.readyState==4)
    {
    	tbody=document.getElementById("getvaluerows");
    	try
        {
    		tbody.innerHTML="";
    	}
        catch(e) 
        {
        	tbody.innerText="";
        }
    	baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
       
        var recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;
       
    	if(xmlhttp.status==200)
        {
             if(commandres=="loadgrid")
            {
                if(flagres=='success')
                {
                	
                	if(recordfound!=0)
                		{
                           
                			tablebody=document.getElementById("getvaluerows");
                             var len=baseres.getElementsByTagName("ALLOT_SNO").length;
                          
                             for(var i=0;i<len;i++)
                             {
                            	
                            	 var ALLOT_SNO=baseres.getElementsByTagName("ALLOT_SNO")[i].firstChild.nodeValue;
                            	 var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                                 
                                 
                                 var ALLOTED_QTY=baseres.getElementsByTagName("ALLOTED_QTY")[i].firstChild.nodeValue;
                               
                                 var MIN_BILL_QTY=baseres.getElementsByTagName("MIN_BILL_QTY")[i].firstChild.nodeValue;
                                
                                 var EXCESS_TARIFF_RATE=baseres.getElementsByTagName("EXCESS_TARIFF_RATE")[i].firstChild.nodeValue;
                                 
                                 rowvalue=document.createElement("TR");
                                 rowvalue.id=ALLOT_SNO;
                                 
                               
                                 var tabledata=document.createElement("TD");
                                 var BENEFICIARY_NAME_val=document.createTextNode(BENEFICIARY_NAME);
                                 tabledata.appendChild(BENEFICIARY_NAME_val);
                                 rowvalue.appendChild(tabledata);
                                  
                                 
                                 var tabledata1=document.createElement("TD");
                                  var ALLOTED_QTY_val=document.createTextNode(ALLOTED_QTY);
                                  tabledata1.appendChild(ALLOTED_QTY_val);
                                  rowvalue.appendChild(tabledata1);
                                  
                                  var tabledata2=document.createElement("TD");
                                  var MIN_BILL_QTY_val=document.createTextNode(MIN_BILL_QTY);
                                  tabledata2.appendChild(MIN_BILL_QTY_val);
                                  rowvalue.appendChild(tabledata2);
                                  
                                  
                                  var tabledata3=document.createElement("TD");
                                  var EXCESS_TARIFF_RATE_val=document.createTextNode(EXCESS_TARIFF_RATE);
                                  tabledata3.appendChild(EXCESS_TARIFF_RATE_val);
                                  rowvalue.appendChild(tabledata3);
                               
                                  
                                  
                                 tablebody.appendChild(rowvalue);
                             }
                		}
                		else
                		{
                			tbody=document.getElementById("getvaluerows");
                	    	try
                	        {
                	    		tbody.innerHTML="";
                	    	}
                	        catch(e) 
                	        {
                	        	tbody.innerText="";
                	        }
                			alert("Record is not present");
                		}
                }
            }
        }
    }
}