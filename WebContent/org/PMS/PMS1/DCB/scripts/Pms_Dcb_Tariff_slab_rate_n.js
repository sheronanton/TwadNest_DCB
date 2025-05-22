var aid=1;
var temp=0;
var tempto=0;
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
    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadbeneficiarytype";
  
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
              
                         
                        
                        
                         addOptionBeneficiary_type(document.PMS_Dcb_Tariff_Slab_Rates.Beneficiary_type,BEN_TYPE_DESC,BEN_TYPE_ID);
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
function loadbeneficiaryname()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    //  
    
    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadbeneficiaryname&Beneficiary_type="+Beneficiary_type;
  //  alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadbeneficiaryname(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadbeneficiaryname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.PMS_Dcb_Tariff_Slab_Rates.Beneficiary_Name.length=1;
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
                     
                         addOptionbeneficiaryname(document.PMS_Dcb_Tariff_Slab_Rates.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
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
/*function funtariff_flag()
{
	
	for( i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag.length; i++ )
    {
        if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag[i].checked)
        {
        	tariff_flag_val=1;
        	tariff_flag_value = document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag[i].value;
        	
        	
        	if(tariff_flag_value=='L')
        	{
        		document.getElementById("metreloc").innerHTML="Metre Location";
                
        	}
        	else if(tariff_flag_value=='S')
        	{
        		document.getElementById("metreloc").innerHTML="Scheme";
        	}
           var xmlhttp=GetXmlHttpObject();
           if (xmlhttp==null)
            {
                alert ("Your browser does not support AJAX!");
                return;
            }
           if(tariff_flag_value=='L')
           {
        	   var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
        	   url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadlocation&Beneficiary_Name="+Beneficiary_Name;
        	   url=url+"&sid="+Math.random();
        	   xmlhttp.open("GET",url,true);
        	   //document.PMS_Dcb_Tariff_Slab_Rates.getvaluerows.innerHTML="";
        	   xmlhttp.onreadystatechange= function()
               {
        		   stateChangedloadlocation(xmlhttp);
               }
        	   xmlhttp.send(null);
           }
           else if(tariff_flag_value=='S')
           {
        	   var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
        	   url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadscheme&Beneficiary_Name="+Beneficiary_Name;
        	   url=url+"&sid="+Math.random();
        	   xmlhttp.open("GET",url,true);
        	 //  document.PMS_Dcb_Tariff_Slab_Rates.getvaluerows.innerHTML="";
        	   xmlhttp.onreadystatechange= function()
               {
        		  stateChangedloadscheme(xmlhttp);
               }
        	   xmlhttp.send(null);
           }
       }
       
   }
}*/
function loadtariffmode()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    //  
    
    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadtariffmode&Beneficiary_Name="+Beneficiary_Name;
    //alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadtariffmode(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadtariffmode(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
    	
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
    
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
    
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
      
       
     
        if(xmlhttp.status==200)
        {
        	
             if((commandres=="loadtariffmode"))
            {
            	 
                if(flagres=='success')
                {
              
                    
                	document.getElementById("tariff_flag").value='';
                    var TARIFF_MODE=baseres.getElementsByTagName("TARIFF_MODE")[0].firstChild.nodeValue;
                   
                  // alert("TARIFF_MODE"+TARIFF_MODE);
                   if(TARIFF_MODE=='S')
                   {   
                	   document.getElementById("tariff_flag_label").innerHTML='Schemewise';
                	   document.getElementById("metreloc").innerHTML='Scheme';
                	   document.getElementById("tariff_flag").value='Scheme';
                	  
                	   loadscheme();
                   }   
                   if(TARIFF_MODE=='L')
                   {   
                	   document.getElementById("tariff_flag_label").innerHTML='Locationwise';
                	   document.getElementById("metreloc").innerHTML='Metre Location';
                	   document.getElementById("tariff_flag").value='Location';
                	   loadlocation();
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
function loadlocation()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    //  
    
    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadlocation&Beneficiary_Name="+Beneficiary_Name;
    //alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadlocation(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadlocation(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
    	try
    	{
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
    
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
    
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	}
    	catch(e)
    	{
    	}
      
        document.PMS_Dcb_Tariff_Slab_Rates.Metre_Location.length=1;
     
        if(xmlhttp.status==200)
        {
             if((commandres=="loadlocation"))
            {
                if(flagres=='success')
                {
              
                    
                    
                    var METRE_SNO_len=baseres.getElementsByTagName("METRE_SNO").length;
                   
                    for(var i=0;i<METRE_SNO_len;i++)
                     {
                         var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[i].firstChild.nodeValue;
                    
                         var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
                   
                     
                         addOptionbeneficiaryname(document.PMS_Dcb_Tariff_Slab_Rates.Metre_Location,METRE_LOCATION,METRE_SNO);
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
function loadscheme()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadscheme&Beneficiary_Name="+Beneficiary_Name;
url=url+"&sid="+Math.random();
xmlhttp.open("GET",url,true);
//  document.PMS_Dcb_Tariff_Slab_Rates.getvaluerows.innerHTML="";
xmlhttp.onreadystatechange= function()
{
	  stateChangedloadscheme(xmlhttp);
}
xmlhttp.send(null);
}
function stateChangedloadscheme(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
   
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.PMS_Dcb_Tariff_Slab_Rates.Metre_Location.length=1;
     
        if(xmlhttp.status==200)
        {
             if((commandres=="loadscheme"))
            {
                if(flagres=='success')
                {
              
                	
                    var SCHEME_SNO_len=baseres.getElementsByTagName("SCHEME_SNO").length;
   
                    for(var i=0;i<SCHEME_SNO_len;i++)
                     {
                         var SCHEME_SNO=baseres.getElementsByTagName("SCHEME_SNO")[i].firstChild.nodeValue;
   
                         var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[i].firstChild.nodeValue;
   
                     
                         addOptionbeneficiaryname(document.PMS_Dcb_Tariff_Slab_Rates.Metre_Location,SCH_NAME,SCHEME_SNO);
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
/*function changeIt()
{
	alert('hai');
	if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length > 0) 
	{
		for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
		{
			a="<tr><td><input type='text' name='valus_starts_from_1' id='valus_starts_from_1' value= document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value'></input></td>"+
			"<td><input type='text' name='valus_starts_to_1' id='valus_starts_to_1' value='+ document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value+'></input></td>"+
			"<td><input type='text' name='tariff_rate_1' id='tariff_rate_1' value='+ document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value+'></input></td>";
			document.getElementById("my_div").innerHTML+=a;
		}
	}
	else
	{
		document.getElementById("my_div").innerHTML=document.getElementById("my_div").innerHTML+"<tr><td><input type='text' name='valus_starts_from_1' id='valus_starts_from_1'></input></td>"+
			"<td><input type='text' name='valus_starts_to_1' id='valus_starts_to_1'></input></td>"+
			"<td><input type='text' name='tariff_rate_1' id='tariff_rate_1'></input></td>"
	}
//var k = 1;

a +="<tr><td><input type='text' name='valus_starts_from_1' id='valus_starts_from_1' ></input> </td>"+
"<td><input type='text' name='valus_starts_to_1' id='valus_starts_to_1'></input></td>"+
"<td><input type='text' name='tariff_rate_1' id='tariff_rate_1'></input></td></tr>"
}
*/
function changeIt()
{
		//alert('asas');
         tbody=document.getElementById("my_div");
         mycurrent_row=document.createElement("TR");
         mycurrent_row.id=aid;
         rid=mycurrent_row.id;
   
         
         cellcheck=document.createElement("TD");
         cellcheck.setAttribute("width","13%");
         cellcheck.setAttribute("align","center");
          hiddencheck=document.createElement("input");
         hiddencheck.type="checkbox";
         hiddencheck.value=rid;
         hiddencheck.size="10";
        hiddencheck.name="maxcheck";
         hiddencheck.id="maxcheck";
         hiddencheck.setAttribute('onclick','checkfun1(this.value)');
         hiddencheck.setAttribute('onMouseOver','checkmax()');
         
         cellcheck.appendChild(hiddencheck);
           mycurrent_row.appendChild(cellcheck); 
         
           nameval1=document.createTextNode("           ");
            cellcheck.appendChild(nameval1);
           mycurrent_row.appendChild(cellcheck);
           
           nameval=document.createTextNode(">");
           font = document.createElement("font");
           font.size = "3 px bold";
           font.appendChild(nameval);
           cellcheck.appendChild(font);
           mycurrent_row.appendChild(cellcheck);
           
          cell1=document.createElement("TD");
        hidden1=document.createElement("input");
        cell1.setAttribute("align","center");
        hidden1.setAttribute("class","tb4");
          hidden1.type="text";
          hidden1.size="10";
          hidden1.name="valus_starts_from_1";
          hidden1.id="valus_starts_from_1";
          hidden1.readOnly="true";
          pretoval=parseInt(rid)-1;
		  if(rid==1)
		  {
			  	//alert(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value);
				nextval=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
			//	document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.readOnly=true;
				
		  }
		  if(rid>1)
		  {
			  	//alert(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[rid].value);
				nextval=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[pretoval].value;
				document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[pretoval].readOnly=true;
				
		  }
		  //alert("");
		  hidden1.value=parseInt(nextval);
          hidden1.setAttribute('onkeypress','return numberOnly(event,this)');
          cell1.appendChild(hidden1);
          mycurrent_row.appendChild(cell1); 
          
           cell2=document.createElement("TD");
           hidden2=document.createElement("input");
           cell2.setAttribute("align","center");
           hidden2.setAttribute("class","tb4");
          hidden2.type="text";
          hidden2.size="10";
          hidden2.name="valus_starts_to_1";
          hidden2.id="valus_starts_to_1";
          hidden2.setAttribute('onkeypress','return numberOnly(event,this)');
          cell2.appendChild(hidden2);
          mycurrent_row.appendChild(cell2); 
          
        cell3=document.createElement("TD");
       hidden3=document.createElement("input");
       cell3.setAttribute("align","center");
       hidden3.setAttribute("class","tb4");
          hidden3.type="text";
          hidden3.size="10";
          hidden3.name="tariff_rate_1";
          hidden3.id="tariff_rate_1";
          hidden3.setAttribute('onkeypress','return numberOnly(event,this)');
          cell3.appendChild(hidden3);
          mycurrent_row.appendChild(cell3);
          
         
          
          tbody.appendChild(mycurrent_row);
          aid++;
}
function validation()
{
	len=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length;
	var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	var Metre_Location=document.getElementById("Metre_Location").value;
	var Tariff_w_e_f_1=document.getElementById("Tariff_w_e_f_1").value;
	var valus_starts_from_1=new Array();
	var valus_starts_to_1=new Array();
	var tariff_rate_1=new Array();
	p=0;
	m=0;
	
	var validcheckval=savevalid();
	if(validcheckval==true)
	{
		if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length > 0) 
		{
		
			for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
			{
				
				valus_starts_from_1[m]=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value;
				valus_starts_to_1[m]=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value;
				tariff_rate_1[m]=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value;
				m=m+1;
			}
		/*	for( i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag.length; i++ )
		    {
		        if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag[i].checked)
		        {
		        	tariff_flag_val=1;
		        	tariff_flag_value = document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag[i].value;
		        	
		        }
		    }*/
			tariff_flag_value = document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag.value;
			if(tariff_flag_value=='Location')
			{
		     	var xmlhttp=GetXmlHttpObject();
		        if (xmlhttp==null)
		        {
		        	alert ("Your browser does not support AJAX!");
		    		         return;
		        }
		        url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+Metre_Location+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&tariff_flag_value="+tariff_flag_value;
		   //   alert(url);
		    	url=url+"&sid="+Math.random();
		    	xmlhttp.open("GET",url,true);
		    	xmlhttp.onreadystatechange= function()
		    	{
		    		stateChangedloadinsert(xmlhttp);
		    	}
		    	xmlhttp.send(null);
			}
		    else if(tariff_flag_value=='Scheme')
		    {
		    	var xmlhttp=GetXmlHttpObject();
		        if (xmlhttp==null)
		        {
		    		 alert ("Your browser does not support AJAX!");
		    		      return;
		    	}
		   		url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+Metre_Location+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&tariff_flag_value="+tariff_flag_value;
		 //	alert(url);
		  		url=url+"&sid="+Math.random();
		  		xmlhttp.open("GET",url,true);
		  		xmlhttp.onreadystatechange= function()
		    	{
		  			stateChangedloadinsert(xmlhttp);
		    	}
		    	 xmlhttp.send(null);
		    }
		}
		
		else
		{
			valus_starts_from_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value;
			valus_starts_to_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
			tariff_rate_1=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value;
			var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
			var Metre_Location=document.getElementById("Metre_Location").value;
			var Tariff_w_e_f_1=document.getElementById("Tariff_w_e_f_1").value;
			var xmlhttp=GetXmlHttpObject();
		    if (xmlhttp==null)
		    {
		         alert ("Your browser does not support AJAX!");
		         return;
		    }
		   
		    /*for( i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag.length; i++ )
		    {
		        if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag[i].checked)
		        {
		        	tariff_flag_val=1;
		        	tariff_flag_value = document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag[i].value;
		        	
		        }
		    }*/
		    tariff_flag_value = document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag.value;
		    if(tariff_flag_value=='Location')
		    {
		    	url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+Metre_Location+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&tariff_flag_value="+tariff_flag_value;
		    	url=url+"&sid="+Math.random();
		    	xmlhttp.open("GET",url,true);
		    	xmlhttp.onreadystatechange= function()
		    	{
		    		stateChangedloadinsert(xmlhttp);
		    	}
		    	xmlhttp.send(null);
		                
		     }
		    else if(tariff_flag_value=='Scheme')
		    {
		    	url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+Metre_Location+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&tariff_flag_value="+tariff_flag_value;
		    	//alert(url);
		    	url=url+"&sid="+Math.random();
		    	xmlhttp.open("GET",url,true);
		    	xmlhttp.onreadystatechange= function()
		    	{
		    		 stateChangedloadinsert(xmlhttp);
		    	}
		    	xmlhttp.send(null);
		    }
		}
	}
	
   }
   

function stateChangedloadinsert(xmlhttp)
{
    
    var baseres,commandres,flagres;
    try
    {
    
    baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
    commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
    flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
    }
    catch(e)
    {
    }
   // alert("xmlhttp.readyState"+xmlhttp.readyState);
    if(xmlhttp.readyState==4)
    {
        
        if(xmlhttp.status==200)
        {
             if(commandres=="add")
            {
                if(flagres=='success')
                {
                    
                 countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
                   if(countinsert==0) 
                    { 
                        alert("Succefully Added");
                        
                     
                    }
                
                    else if(countinsert==1)
                     {
                           alert("Record is already present");
                           
                     }
                     
               }
                 else
                {
                    alert("Not Loaded");
                }
                tbody=document.getElementById("getvaluerows");
            	try
                {
            		tbody.innerHTML="";
            	}
                catch(e) 
                {
                	tbody.innerText="";
                }
               loadtarffic();
            }
        }
    }
    
}
/*function checkmax()
{
	alert("Check if Maximum Consumption Quantity");
}*/
function validcheck()
{
flag=0;
alert("TEST")	
	if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length > 0) 
	{
		for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
		{
			/*if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value!="")
			{
				if(k>=1)
				{
					re=parseInt(k)-1;
					alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value);
					alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[re].value);
					if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value) < parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[re].value))
					{
					flag=1;
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k-1].value);
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value);
					//alert("Enter Max Tariff Rate");
					return false;
					}
				}
				
			}*/
			
			if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value=="")
			{		
				valuestarts=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value;
				
				
					alert("Enter From ");
					flag=1;
					return false;
				
				
			}
			if((document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value=="") && document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].readOnly==false)
			{
				if(tempto==0)
				{
					flag=1;
					alert("Enter To");
					return false;
				}
			}
			if((document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value!="") && (document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value!=""))
			{
					if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value) > parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value))
					{
					flag=1;
					alert("To Qty should be greater than From Qty");
					return false;
					}
					
			}
			if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value=="")
			{
				
					flag=1;
					alert("Enter Tariff Rate");
					return false;
				
			}
			if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value!="")
			{
				if(k>=1)
				{
					re=parseInt(k)-1;
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value);
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[re].value);
					if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value) < parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[re].value))
					{
					flag=1;
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k-1].value);
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value);
					alert("Tariff Rate should be greater than previous Tariff rate");
					return false;
					}
				}
				
				
			}
			/*else if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value)
			{
				
					flag=1;
					alert("The value should be greate than from");
					return false;
				
			}*/
			/*else 
			{
				valuestarts=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value;
			valus_starts_to_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value;
			tariff_rate_1=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value;
			if((document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value!="")&&(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value!="")&&(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value!=""))
			{
				alert(valuestarts);
				alert(valus_starts_to_1);
				alert(tariff_rate_1	);
				//alert("sdsdsdsd");
				//changeIt();
				//return true;
				flag=1;
				
			}
			}*/
		}
		
		if(flag==0)
		{
			
			changeIt();
			return true;
		}

		
	}
		else
		{
			
			
			valuestarts=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value;
			valus_starts_to_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
			tariff_rate_1=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value;
			
			if((valuestarts!="") && (valus_starts_to_1!=""))
			{
					if(parseInt(valuestarts) > parseInt(valus_starts_to_1))
					{
					flag=1;
					alert("To Qty should be greater than From Qty");
					return false;
					}
					
			}
			 if((valuestarts=="")||(valus_starts_to_1=="")||(tariff_rate_1==""))
			{
				flag=1;
				alert("Enter neccessary values");
				return false;
			}
			

				if(flag==0)
				{
					
					changeIt();
					return true;
				}
				
			
		}
		
}
function exitwindow()
{
    window.close();
}

function savevalid()
{
	flag=0;
	var Beneficiary_type=document.getElementById("Beneficiary_type").value;
	var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	var Metre_Location=document.getElementById("Metre_Location").value;
	var Tariff_w_e_f_1=document.getElementById("Tariff_w_e_f_1").value;
	if(document.PMS_Dcb_Tariff_Slab_Rates.Beneficiary_type.value=="")
	{
		alert("Enter Beneficiary Type");
		flag=1;
		return false;
	}
	
	if(document.PMS_Dcb_Tariff_Slab_Rates.Beneficiary_Name.value=="")
	{
		alert("Enter Beneficiary Name");
		flag=1;
		return false;
	}
	if(document.PMS_Dcb_Tariff_Slab_Rates.Metre_Location.value=="")
	{
		alert("Enter Metre_Location");
		flag=1;
		return false;
	}
	if(document.PMS_Dcb_Tariff_Slab_Rates.Tariff_w_e_f_1.value=="")
	{
		alert("Enter Tariff W.e.f");
		flag=1;
		return false;
	}
	if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length > 0) 
	{
		for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
		{
			if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value=="")
			{
				valuestarts=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value;
				
				
					alert("Enter From ");
					flag=1;
					return false;
				
				
			}
			if((document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value=="") && document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].readOnly==false)
			{
				if(tempto==0)
				{
					flag=1;
					alert("Enter To");
					return false;
				}
			}
			if((document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value!="") && (document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value!=""))
			{
					if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value) > parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value))
					{
					flag=1;
					alert("To Qty should be greater than From Qty");
					return false;
					}
					
			}
			if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value=="")
			{
				
					flag=1;
					alert("Enter Tariff Rate");
					return false;
				
			}
			if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value!="")
			{
				if(k>=1)
				{
					re=parseInt(k)-1;
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value);
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[re].value);
					if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value) < parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[re].value))
					{
					flag=1;
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k-1].value);
					//alert(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value);
					alert("Tariff Rate should be greater than previous Tariff rate");
					return false;
					}
				}
				
				
			}
		
		}
		
		

		
	}
		else
		{
			
			
			valuestarts=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value;
			valus_starts_to_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
		
			tariff_rate_1=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value;
			if((valuestarts!="") && (valus_starts_to_1!=""))
			{
					if(parseInt(valuestarts) > parseInt(valus_starts_to_1))
					{
					flag=1;
					alert("Enter To Value Max than From");
					return false;
					}
					
			}
			if((valuestarts=="")||(valus_starts_to_1=="")||(tariff_rate_1==""))
			{
				flag=1;
				alert("Enter neccessary values");
				return false;
			}
			
				
			
		}
	if(flag==0)
	{
		
		//changeIt();
		return true;
	}
		
}
function refresh()
{
	document.getElementById("Tariff_w_e_f_1").value="";
	document.getElementById("valus_starts_from_1").value="";
	document.getElementById("valus_starts_to_1").value="";
	document.getElementById("tariff_rate_1").value="";
	
	
	window.location.reload();
	/*document.getElementById("Beneficiary_type").value="";
    document.getElementById("Beneficiary_Name").value="";
    document.getElementById("Metre_Location").value="";
    document.getElementById("Tariff_w_e_f_1").value="";
     tbody=document.getElementById("my_div");
   /* if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length > 0) 
	{
    	alert("rtrtrtrt");
    	for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
		{
    		{
    	 r=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value
    		alert("r"+r);
    		ri=r.rowIndex;
    	    tbody.deleteRow(ri);
			}
		}
	}
    else
	{
    	alert("Ssdsd");
    	r=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value;	
    	alert("r"+r);
         ri=r.rowIndex;
        tbody.deleteRow(ri);
		
	}
    */
/*  if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length > 0) 
 	{
    	 for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
 		{
    		 	alert('hsi'	);
    		 	mycurrent_row.removeChild(cell1[k]);
    		 	mycurrent_row.removeChild(cell2[k]); 
    		 	mycurrent_row.removeChild(cell3[k]);
 		}
 	}   */
    /* alert("rid"+rid);*/
   /*  var tbl = document.getElementById('mycurrent_row');
     var tbl=document.PMS_Dcb_Tariff_Slab_Rates.mycurrent_row.length;
     var lastRow = tbl.rows.length;
     alert("Sdsd"+lastRow);
     for(i=0;i<lastRow;i++)
     {
    	 kids = lastRow[i].childNodes;
     for (j=0; j < kids.length; j++) 
     {
    	 tbl[j].removeChild(kids[j]);
     }
     

     }
     */
    
    
}
function loadtarffic()
{
	{
	    xmlhttp=GetXmlHttpObject();
	    if (xmlhttp==null)
	    {
	         alert ("Your browser does not support AJAX!");
	         return;
	    }
	  /*  for( i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag.length; i++ )
	    {
	        if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag[i].checked)
	        {
	        	tariff_flag_val=1;	
	        	tariff_flag_value = document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag[i].value;
	        	
	        }
	    }*/
	    tariff_flag_value = document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag.value;
	    var Metre_Location=document.getElementById("Metre_Location").value;
	    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=get&Metre_Location="+Metre_Location+"&tariff_flag_value="+tariff_flag_value;
	     url=url+"&sid="+Math.random();
	    xmlhttp.open("GET",url,true);
	    xmlhttp.onreadystatechange= function()
	    {
	        stateChangedloadtarffic(xmlhttp);
	    }
	    xmlhttp.send(null);
	}
}
function stateChangedloadtarffic()
{
    var baseres,commandres,flagres,recordres;
    //alert(xmlhttp.readyState);
    if(xmlhttp.readyState==4)
    {
    	baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        var recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;

        tbody=document.getElementById("getvaluerows");
    	try
        {
    		tbody.innerHTML="";
    	}
        catch(e) 
        {
        	tbody.innerText="";
        }
    	if(xmlhttp.status==200)
        {
             if(commandres=="get")
            {
                if(flagres=='success')
                {
                		if(recordfound!=0)
                		{
                             tablebody=document.getElementById("getvaluerows");
                             var len=baseres.getElementsByTagName("TARIFF_SLAB_STARTS_FROM").length;
                             for(var i=0;i<len;i++)
                             {
                            	 var TARIFF_SLAB_STARTS_FROM=baseres.getElementsByTagName("TARIFF_SLAB_STARTS_FROM")[i].firstChild.nodeValue;
                                 var TARIFF_SLAB_STARTS_TO=baseres.getElementsByTagName("TARIFF_SLAB_STARTS_TO")[i].firstChild.nodeValue;
                                 var TARIFF_W_E_F=baseres.getElementsByTagName("TARIFF_W_E_F")[i].firstChild.nodeValue;
                                 var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[i].firstChild.nodeValue;
                                 var TARIFF_W_E_F=baseres.getElementsByTagName("TARIFF_W_E_F")[i].firstChild.nodeValue;
                                 rowvalue=document.createElement("TR");
                                 rowvalue.id=aid;
                                 
                                 
                        
                                 var tabledata=document.createElement("TD");
                                  var hiddentext1=document.createTextNode(TARIFF_SLAB_STARTS_FROM);
                                 
                                  tabledata.appendChild(hiddentext1);
                                  tabledata.setAttribute("align","center");
                                  rowvalue.appendChild(tabledata);
                                  
                                  var tabledata1=document.createElement("TD");
                                  var hiddentext2=document.createTextNode(TARIFF_SLAB_STARTS_TO);
                                  tabledata1.setAttribute("align","center");
                                  tabledata1.appendChild(hiddentext2);
                                  rowvalue.appendChild(tabledata1);
                                  
                                  var tabledata2=document.createElement("TD");
                                  var hiddentext3=document.createTextNode(TARIFF_RATE);
                                  tabledata2.setAttribute("align","center");
                                  tabledata2.appendChild(hiddentext3);
                                  rowvalue.appendChild(tabledata2);
                                  
                                  
                                  var ag=TARIFF_W_E_F.split("-");
                                  var datefor=ag[2]+"/"+ag[1]+"/"+ag[0]; 
                                  
                                  var tabledata3=document.createElement("TD");
                                  var hiddentext4=document.createTextNode(datefor);
                                  tabledata3.appendChild(hiddentext4);
                                  rowvalue.appendChild(tabledata3);
                                  
                                 tablebody.appendChild(rowvalue);
                             }
                		}
                		else
                		{
                			alert("Tariff Slab Rate is not present");
                		}
                }
            }
        }
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

function greateronly()
{
	/*var valus_starts_to_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
	var valus_starts_from_1 = document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value;
	if(valus_starts_to_1<valus_starts_from_1)
	{
		alert("The value should be greater than from");
	}*/
}





function checkfun1(str)
{
	//alert(document.PMS_Dcb_Tariff_Slab_Rates.maxcheck[str].value);
	if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].readOnly==false)
	{
		if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[str].value!="")
		{
		//	val=parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[str].value);
		//	val=val*20;
		//	document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].value=val;
		}
		
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].readOnly=true;
		document.PMS_Dcb_Tariff_Slab_Rates.cmdsubmit_addrow.disabled=true;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].value=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[str].value*20;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].style.color='#ececec';
	}
	else
	{
		
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].readOnly=false;
		document.PMS_Dcb_Tariff_Slab_Rates.cmdsubmit_addrow.disabled=false;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].value="";
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].style.color='black';
	}
}





function numberOnly(e,oBj)
{
	var keynum;
	var keychar;
	var numcheck;
	
	if(window.event) //IE
	{
		keynum=e.keyCode;
	}
	if(e.which) //Netscape/Firefox/Opera
	{
		keynum=e.which;
	}
	keychar=String.fromCharCode(keynum);
	var splCharCheck = /[a-zA-Z!@#$%&*()+=|_'"`~:;<>?,\/\\\^\\{\}\[\]]/;
	
	return (!splCharCheck.test(keychar));
}
function echeck(str) {

		var at="@";
		var dot=".";
		var lat=str.indexOf(at)
		var lstr=str.length
		var ldot=str.indexOf(dot)
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID");
		    return false;
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }
		
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

 		 return true;					
	}
