var aid=1;
var temp=0;
var tempto=0;
var tempflagvar=0;
var temp_var_check=0;
var flaginsert=0;
var check_var=0;
var max_flag_val=0;
var flagmaxcheck;
var flagalloted=0;
var flaginside=0;
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
    
    
    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadbeneficiaryname&Beneficiary_type="+Beneficiary_type;

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
                   
                    for(var i=0;i<BENEFICIARY_SNO_len;i++)
                     {
                         var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                     
                         addOptionbeneficiaryname(document.PMS_Dcb_Tariff_Slab_Rates.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
                     }
                    loadtariff();
                     
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

function loadtariffmode()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
       url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadtariffmode&Beneficiary_Name="+Beneficiary_Name;
    
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
                    var TARIFF_MODE=baseres.getElementsByTagName("TARIFF_FLAG")[0].firstChild.nodeValue;
                   
                  
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
                    loadlocation();
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
	document.getElementById('cmdsubmit_addrow').disabled=false;
	document.getElementById('cmdsave').disabled=false;

	document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value="0.0";
	document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value="0.0";
	
		
	document.getElementById("valus_starts_to_1").style.backgroundColor = '#ececec';
	// sa document.getElementById("valus_starts_to_1").readOnly=true;
	document.getElementById("valus_starts_to_1").readOnly=false;
	document.getElementById('maxcheck').disabled=false;
	
	
	 tbody=document.getElementById("test");
		for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
		{
			{
		 r=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value;
			try
			{
			ri=r.rowIndex;
		    tbody.deleteRow(ri);
			}
			catch(e)
			{
			}
			}
		
		}
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
      var Schemes=document.getElementById("Schemes").value;
    
   url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadlocation&Beneficiary_Name="+Beneficiary_Name+"&Schemes="+Schemes;
  
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
                	var TARIFF_FLAG=baseres.getElementsByTagName("TARIFF_FLAG")[0].firstChild.nodeValue;
                	document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag_one.value=TARIFF_FLAG;
                	if(TARIFF_FLAG=='L')
                	{
                		document.getElementById("tariff_flag_label").innerHTML="Tariff slab rate - Location Based";
                		var l=document.getElementById("locationload");
                        l.style.display="block";
                		var METRE_SNO_len=baseres.getElementsByTagName("METRE_SNO").length;
                		for(var i=0;i<METRE_SNO_len;i++)
                        {
                            var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[i].firstChild.nodeValue;
                            var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
                            addOptionloadlocation(document.PMS_Dcb_Tariff_Slab_Rates.Metre_Location,METRE_LOCATION,METRE_SNO);
                        }
                	}
                	if(TARIFF_FLAG=='S')
                	{

                		document.getElementById("tariff_flag_label").innerHTML="Tariff slab rate - Scheme Based";
                		var l=document.getElementById("locationload");
                        l.style.display="none";
                        loadlocationgrid();
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
function addOptionloadlocation(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
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
    	try
    	{
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
    	}
    	catch(e)
    	{
    	}
        document.PMS_Dcb_Tariff_Slab_Rates.Schemes.length=1;
     
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
                         var TARIFF_FLAG=baseres.getElementsByTagName("TARIFF_FLAG")[i].firstChild.nodeValue;
                         if(TARIFF_FLAG=='S')
                         {
                        	 TARIFF_FLAG='Scheme';
                         }
                         if(TARIFF_FLAG=='L')
                         {
                        	 TARIFF_FLAG='Location';
                         }
                     
                         addOptionloadscheme(document.PMS_Dcb_Tariff_Slab_Rates.Schemes,SCH_NAME+'['+TARIFF_FLAG+']',SCHEME_SNO);
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
function addOptionloadscheme(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}

function changeIt()
{
	  
         tbodyvar=document.getElementById("test");
         mycurrent_row=document.createElement("TR");
         mycurrent_row.id=aid;
         rid=aid;//mycurrent_row.id;
          cellcheck=document.createElement("TD");
          cellcheck.setAttribute("align","center");
          cellcheck.setAttribute("width","13%");
          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
 		 {
         	 hiddencheck_maxcheck_value=document.createElement("<input type='hidden' name='maxcheck_value' id='maxcheck_value' value='N'>");
         	cellcheck.appendChild(hiddencheck_maxcheck_value);
         	 mycurrent_row.appendChild(cellcheck); 
 		 }
          else
          {
        	  hiddencheck_maxcheck_value=document.createElement("input");
        	  hiddencheck_maxcheck_value.type="hidden";
        	  hiddencheck_maxcheck_value.value="N";
        	  hiddencheck_maxcheck_value.size="10";
        	  hiddencheck_maxcheck_value.name="maxcheck_value";
        	  hiddencheck_maxcheck_value.id="maxcheck_value";
        	  cellcheck.appendChild(hiddencheck_maxcheck_value);
              mycurrent_row.appendChild(cellcheck); 
          }
          
          hiddencheck="";
         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
		 {
        	
        	hiddencheck=document.createElement("<input type='checkbox' name='maxcheck' id='maxcheck' value='"+rid+"' onclick='checkfun1(this.value)'>");
        	// alert("test"+hiddencheck.getAttribute("onclick"));
         	 cellcheck.appendChild(hiddencheck);
		 }     
         else
         {
         hiddencheck=document.createElement("input");
         hiddencheck.type="checkbox";
         hiddencheck.value=rid;
         hiddencheck.size="10";
         hiddencheck.name="maxcheck";
         hiddencheck.id="maxcheck";
         hiddencheck.setAttribute('onclick','checkfun1(this.value)');
          cellcheck.appendChild(hiddencheck);
         }
         mycurrent_row.appendChild(cellcheck);  
          cell1=document.createElement("TD");
          cell1.setAttribute("align","center");
          
          if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
   		 {
        	  pretoval=parseInt(rid)-1;
    		  if(rid==1)
    		  {
    				nextval=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
    		  }
    		  if(rid>1)
    		  {
    				nextval=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[pretoval].value;
    				document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[pretoval].readOnly=true;
    		  }
        	  hidden1=document.createElement("<input type='text'  name='valus_starts_from_1' id='valus_starts_from_1' size='10' style='text-align: left;background-color: #ececec;' value='"+nextval+"' readOnly='true'  class='tb4' onkeyPress='return numonly(event);'>");
   		 }
          else
          {
        	  cell1=document.createElement("TD");
        	  cell1.setAttribute("align","center");
              hidden1=document.createElement("input");
                hidden1.type="text";
                hidden1.size="10";
                hidden1.name="valus_starts_from_1";
                hidden1.id="valus_starts_from_1";
                hidden1.readOnly="true";
                hidden1.style.backgroundColor = '#ececec';
                hidden1.Class="tb4";
                pretoval=parseInt(rid)-1;
      		  if(rid==1)
      		  {
      				nextval=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
      		  }
      		  if(rid>1)
      		  {
      				nextval=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[pretoval].value;
      				document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[pretoval].readonly=true;
      		  }
      		    hidden1.value=nextval;  
                hidden1.setAttribute('onkeypress','return numberOnly(event,this)');
          }
          cell1.appendChild(hidden1);
          mycurrent_row.appendChild(cell1); 
           cell2=document.createElement("TD");
           cell2.setAttribute("align","center");
           if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
     		 {
        	   hidden2=document.createElement("<input type='text'  name='valus_starts_to_1' id='valus_starts_to_1' size='10' class='tb4' style='text-align: left'>");
        	   hidden2.onkeypress= function() 
           	{
           		return numonly(event);
       		}; 
       		hidden2.onblur= function() 
        	{
        		
       			fun_mini_flag_dynamic();
    		}; 
     		 }
           else
           {
        	   hidden2=document.createElement("input");
        	   hidden2.type="text";
        	   hidden2.size="10";
        	   hidden2.name="valus_starts_to_1";
        	   hidden2.id="valus_starts_to_1";
        	   hidden2.Class="tb4";
        	   hidden2.setAttribute('onkeypress','return numonly(event)');
        	   hidden2.setAttribute('onblur','fun_mini_flag_dynamic()');
           }
        	   cell2.appendChild(hidden2);
        	   mycurrent_row.appendChild(cell2); 
        cell3=document.createElement("TD");
        cell3.setAttribute("align","center");

        if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
		 {
        	
        	
        	hidden3=document.createElement("<input type='text'  name='tariff_rate_1' id='tariff_rate_1' size='10'  class='tb4' style='text-align: left' >");
        	hidden3.onkeypress= function() 
        	{
        		
        		return numonly(event);
    		}; 
		 }
        else
        {
        	hidden3=document.createElement("input");
        	hidden3.type="text";
        	hidden3.size="10";
        	hidden3.name="tariff_rate_1";
        	hidden3.id="tariff_rate_1";
        	hidden3.Class="tb4";
        	
            hidden3.setAttribute('onkeypress','return numonly(event)');
        }
          cell3.appendChild(hidden3);
          mycurrent_row.appendChild(cell3);
          tbodyvar.appendChild(mycurrent_row);
          var rowcnt=document.getElementById("rowcnt").value;
          document.getElementById("rowcnt").value=parseInt(rowcnt)+1;
          aid++;
}

function validation()
{
	//alert("validation");
	len=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length;
	//alert("len is "+len);
	var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	var Metre_Location=document.getElementById("Metre_Location").value;
	var Tariff_w_e_f_1=document.getElementById("Tariff_w_e_f_1").value;
	var valus_starts_from_1=new Array();
	var valus_starts_to_1=new Array();
	var tariff_rate_1=new Array();
	var maxcheck_value=new Array();
	p=0;
	m=0;
	
	var validcheckval=savevalid();
	if(validcheckval==true)
	{
		if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length > 0) 
		{
		
			for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
			{
			//	alert("first");
				valus_starts_from_1[m]=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value;
				valus_starts_to_1[m]=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value;
				tariff_rate_1[m]=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value;
				maxcheck_value[m]=document.PMS_Dcb_Tariff_Slab_Rates.maxcheck_value[k].value;
			//	alert("valus_starts_from_1[m]"+valus_starts_from_1[m]);
			//	alert("valus_starts_to_1[m]"+valus_starts_to_1[m]);
			//	alert("tariff_rate_1[m]"+tariff_rate_1[m]);
			//	alert("maxcheck_value[m]"+maxcheck_value[m]);
				
				m=m+1;
			}
			var xmlhttp=GetXmlHttpObject();
		//    if (xmlhttp==null) mmnbv 
		 //   {
		  //       alert ("Your browser does not support AJAX!");
		 //        return;
		  //  }
			var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
			var Tariff_w_e_f_1=document.getElementById("Tariff_w_e_f_1").value;
			Metre_Location=document.getElementById("Metre_Location").value;
			Schemes=document.getElementById("Schemes").value;
			min_qty=document.getElementById("min_qty").value;
			text_allot_flag=document.getElementById("allot_flag").value;
			tariff_flag=document.getElementById("tariff_flag").value;
			
			//alert("text_allot_flag[m]"+text_allot_flag);
			if(Metre_Location=="")
			{
				Metre_Location=0;
			}
			else
			{
				Metre_Location=document.getElementById("Metre_Location").value;
			}
			if(Schemes=="")
			{
				Schemes=0;
			}
			else
			{
				Schemes=document.getElementById("Schemes").value;
			}
			if(text_allot_flag=="")
			{
				
				text_allot_flag=0;
				
			}
			else
			{
				text_allot_flag=document.getElementById("allot_flag").value;
			}
		   
			if(min_qty=="")
			{
				text_min_qty=0;
				
			}
			else
			{
				text_min_qty=document.getElementById("min_qty").value;
			}
			
			
		    for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.mini_flag.length; i++ )
		    {
		        if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].checked)
		        {
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value=='N')
		        	{
		        		document.getElementById("min_qty").value="";
		        		document.getElementById("min_qty").style.backgroundColor = '#ececec';
		        		document.getElementById("min_qty").readOnly=true;
		        		mini_flag_value=document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value;
		        	}
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value=='Y')
		        	{
		        		document.getElementById("min_qty").style.backgroundColor = 'white';
		        		document.getElementById("min_qty").readOnly=false;
		        		mini_flag_value=document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value;
		        	}
		        }
		    }
		    for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag.length; i++ )
		    {
		        if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].checked)
		        {
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=='N')
		        	{
		        		document.getElementById("allot_flag").value="";
		        		document.getElementById("allot_flag").style.backgroundColor = '#ececec';
		        		document.getElementById("allot_flag").readOnly=true;
		        		allot_flag_value=document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value;
		        	}
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=='Y')
		        	{
		        		document.getElementById("allot_flag").style.backgroundColor = 'white';
		        		document.getElementById("allot_flag").readOnly=false;
		        		allot_flag_value=document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value;
		        	}
		        }
		    }
		 //   alert("tariff_flag"+tariff_flag);
		    if(tariff_flag=='B')
		    {
		    	url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+0+"&Schemes="+0+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&mini_flag_value="+mini_flag_value+"&allot_flag_value="+allot_flag_value+"&text_allot_flag="+text_allot_flag+"&text_min_qty="+text_min_qty+"&tariff_flag="+tariff_flag+"&maxcheck="+maxcheck_value;
		    	
		    	url=url+"&sid="+Math.random();
		    	xmlhttp.open("GET",url,true);
		    	xmlhttp.onreadystatechange= function()
		    	{
		    		stateChangedloadinsert(xmlhttp);
		    	}
		    	xmlhttp.send(null);
		    }
		    tariff_flag_var=document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag_one.value;
	    	if(tariff_flag_var=='S')
	    	{
	    	url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+0+"&Schemes="+Schemes+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&mini_flag_value="+mini_flag_value+"&allot_flag_value="+allot_flag_value+"&text_allot_flag="+text_allot_flag+"&text_min_qty="+text_min_qty+"&tariff_flag="+tariff_flag_var+"&maxcheck="+maxcheck_value;
	    	
	    	url=url+"&sid="+Math.random();
	    	xmlhttp.open("GET",url,true);
	    	xmlhttp.onreadystatechange= function()
	    	{
	    		stateChangedloadinsert(xmlhttp);
	    	}
	    	xmlhttp.send(null);
	    	}
	    	if(tariff_flag_var=='L')
	    	{
	    	url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+Metre_Location+"&Schemes="+Schemes+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&mini_flag_value="+mini_flag_value+"&allot_flag_value="+allot_flag_value+"&text_allot_flag="+text_allot_flag+"&text_min_qty="+text_min_qty+"&tariff_flag="+tariff_flag_var+"&maxcheck="+maxcheck_value;
	    	
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
			var xmlhttp=GetXmlHttpObject();
		    if (xmlhttp==null)
		    {
		         alert ("Your browser does not support AJAX!");
		         return;
		    }
			valus_starts_from_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value;
			valus_starts_to_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
			tariff_rate_1=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value;
			var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
			
			var Tariff_w_e_f_1=document.getElementById("Tariff_w_e_f_1").value;
			
			
			Metre_Location=document.getElementById("Metre_Location").value;
			Schemes=document.getElementById("Schemes").value;
			min_qty=document.getElementById("min_qty").value;
			text_allot_flag=document.getElementById("allot_flag").value;
			tariff_flag=document.getElementById("tariff_flag").value;
			if(Metre_Location=="")
			{
				Metre_Location=0;
			}
			else
			{
				Metre_Location=document.getElementById("Metre_Location").value;
			}
			if(Schemes=="")
			{
				Schemes=0;
			}
			else
			{
				Schemes=document.getElementById("Schemes").value;
			}
			if(text_allot_flag=="")
			{
				
				text_allot_flag=0;
				
			}
			else
			{
				text_allot_flag=document.getElementById("allot_flag").value;
			}
		   
			if(min_qty=="")
			{
				text_min_qty=0;
				
			}
			else
			{
				text_min_qty=document.getElementById("min_qty").value;
			}
			
			
		    for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.mini_flag.length; i++ )
		    {
		        if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].checked)
		        {
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value=='N')
		        	{
		        		document.getElementById("min_qty").value="";
		        		document.getElementById("min_qty").style.backgroundColor = '#ececec';
		        		document.getElementById("min_qty").readOnly=true;
		        		mini_flag_value=document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value;
		        	}
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value=='Y')
		        	{
		        		document.getElementById("min_qty").style.backgroundColor = 'white';
		        		document.getElementById("min_qty").readOnly=false;
		        		mini_flag_value=document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value;
		        	}
		        }
		    }
		    for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag.length; i++ )
		    {
		        if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].checked)
		        {
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=='N')
		        	{
		        		document.getElementById("allot_flag").value="";
		        		document.getElementById("allot_flag").style.backgroundColor = '#ececec';
		        		document.getElementById("allot_flag").readOnly=true;
		        		allot_flag_value=document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value;
		        	}
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=='Y')
		        	{
		        		document.getElementById("allot_flag").style.backgroundColor = 'white';
		        		document.getElementById("allot_flag").readOnly=false;
		        		allot_flag_value=document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value;
		        	}
		        }
		    }
		   
		    
		    if(document.getElementById('maxcheck').checked)
			{
		    	maxcheck = document.getElementById('maxcheck').value;
			}
		    else
		    {
		    	maxcheck ='N';
		    }
			
		    	if(tariff_flag=='B')
		    	{
		    	url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+0+"&Schemes="+0+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&mini_flag_value="+mini_flag_value+"&allot_flag_value="+allot_flag_value+"&text_allot_flag="+text_allot_flag+"&text_min_qty="+text_min_qty+"&tariff_flag="+tariff_flag+"&maxcheck="+maxcheck;
		    	
		    	url=url+"&sid="+Math.random();
		    	xmlhttp.open("GET",url,true);
		    	xmlhttp.onreadystatechange= function()
		    	{
		    		stateChangedloadinsert(xmlhttp);
		    	}
		    	xmlhttp.send(null);
		    	}
		    	
		    	
		    	tariff_flag_var=document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag_one.value;
		    	if(tariff_flag_var=='S')
		    	{
		    	url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+0+"&Schemes="+Schemes+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&mini_flag_value="+mini_flag_value+"&allot_flag_value="+allot_flag_value+"&text_allot_flag="+text_allot_flag+"&text_min_qty="+text_min_qty+"&tariff_flag="+tariff_flag_var+"&maxcheck="+maxcheck;
		    	
		    	url=url+"&sid="+Math.random();
		    	xmlhttp.open("GET",url,true);
		    	xmlhttp.onreadystatechange= function()
		    	{
		    		stateChangedloadinsert(xmlhttp);
		    	}
		    	xmlhttp.send(null);
		    	}
		    	if(tariff_flag_var=='L')
		    	{
		    	url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=insert&valus_starts_from_1="+valus_starts_from_1+"&valus_starts_to_1="+valus_starts_to_1+"&tariff_rate_1="+tariff_rate_1+"&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+Metre_Location+"&Schemes="+Schemes+"&Tariff_w_e_f_1="+Tariff_w_e_f_1+"&mini_flag_value="+mini_flag_value+"&allot_flag_value="+allot_flag_value+"&text_allot_flag="+text_allot_flag+"&text_min_qty="+text_min_qty+"&tariff_flag="+tariff_flag_var+"&maxcheck="+maxcheck;
		    	
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
                        alert("Successfully Added");
                        document.getElementById("valus_starts_from_1").style.backgroundColor = '#ececec';
                		document.getElementById("valus_starts_from_1").readOnly=true;
                		document.getElementById("valus_starts_from_1").value=0;
                		 document.getElementById("valus_starts_to_1").style.backgroundColor = '#ececec';
                 		document.getElementById("valus_starts_to_1").readOnly=true;
                 		document.getElementById("valus_starts_to_1").value=0;
                    	document.getElementById("min_qty").value="";
                    	document.getElementById("allot_flag").value="";
                    	document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[1].checked=true;
                    	document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[1].checked=true;
                    	 document.getElementById("min_qty").style.backgroundColor = '#ececec';
                    	 document.getElementById("min_qty").readOnly=true;
                    	 document.getElementById("allot_flag").style.backgroundColor = '#ececec';
                    	 document.getElementById("allot_flag").readOnly=true;
                    	document.PMS_Dcb_Tariff_Slab_Rates.cmdsubmit_addrow.disabled=true;
                    	document.PMS_Dcb_Tariff_Slab_Rates.maxcheck_value.value='N';
                    	
                    	if(document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.length>0)
                    	{
                    		for( i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.length; i++ )
                    		{     
                    			document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.checked=false;
                    		
                    			
                    		}
                    	}
                    	else
                    	{
                    		document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.checked=false;
                    		
                    	}
                    	if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length>0)
                    	{
                    		
                    		testvar=document.getElementById("test");
                    		try
                    		{
                    			testvar.innerHTML="";
                    			aid=1;
                    		}
                    		catch(e) 
                    		{
                    			testvar.innerText="";
                    			aid=1;
                    		}
                    	}
                    	document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.disabled=false;
                        flaginsert=1;
                      
                        loadlocationgrid();
                    }
                
                    else if(countinsert==1)
                     {
                    	 alert("Tariff Slab Rate Already Exists for this Beneficiary and Scheme ..\n If Tariff Slab Rate Is To Be Updated, Chooese Update Tariff Option");
                    	 document.getElementById('cmdsubmit_addrow').disabled=true;
         				document.getElementById('cmdsave').disabled=true;
                           
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

function   validcheck()
{
	 
	document.getElementById("cmdsave").disabled = false;
	var fg=new Boolean;
	fg=false;
	var b;
	flag=0;
	flagmaxcheck=0;
	len_var=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length;
	var s_len=document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.length
	try
	{
	
	if(len_var > 0) 
	{
		for(k=0; k<len_var ; k++)
		{
			valuestarts=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value;
			if(valuestarts=="")
			{		
				alert("Enter From ");
				flag=1;
				fg=false;
			}
			if((document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value=="") && document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].readOnly==false)
			{
					flag=1;
					alert("Enter To");
					fg=false;
			}
			if((document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value!="") && (document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value!=""))
			{
				if(flagmaxcheck==0)
				{
					
				if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value) > parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value))
				{
					flag=1;
					alert("To Qty should be greater than From Qty");
					fg=false;
				}
				}
			}
			if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value=="")
			{
				flag=1;
				alert("Enter Tariff Rate");
				fg=false;
			}
			if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value!="")
			{
				if(k>=1)
				{
					re=parseInt(k)-1;
					if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value) < parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[re].value))
					{
						flag=1;
						alert("Tariff Rate should be greater than previous Tariff rate");
						fg=false;
					}
				}
			}
			document.PMS_Dcb_Tariff_Slab_Rates.maxcheck[k].disabled=true;	
		}
		if(flag==0)
		{
			 changeIt();
			 fg=true;
		}

		
	}
		else
		{
			flag=0;			
			valuestarts=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value;
			valus_starts_to_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
			tariff_rate_1=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value;
			
			if((valuestarts!="") && (valus_starts_to_1!=""))
			{
				if(parseInt(valuestarts) > parseInt(valus_starts_to_1))
				{
					flag=1;
					alert("To Qty should be greater than From Qty");
					fg=false;
				}
					
			}
			 if((valuestarts=="")||(valus_starts_to_1=="")||(tariff_rate_1==""))
			{
				flag=1;
				alert("Enter neccessary values");
				fg=false;
			}
			 if(valus_starts_to_1==0)
			 {
				 	flag=1;
					alert("To Quantity cannot be zero");
					fg=false;
			 }
			 document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.disabled=true;
			 
			  
			if(flag==0)
			{
			    
				 changeIt();
				fg=true;
			}
			
		}
	}
	catch(e){}
	
	return fg;
}
function exitwindow()
{
    window.close();
}

function savevalid()
{
	
	var fg=new Boolean();
	flag=0;
	flagalloted=0;
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
	if(document.PMS_Dcb_Tariff_Slab_Rates.Schemes.value=="")
	{
		alert("Enter Schemes");
		flag=1;
		return false;
	}
	if(document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag_one.value=="L")
	{
		if(document.PMS_Dcb_Tariff_Slab_Rates.Metre_Location.value=="")
		{
			alert("Enter Location");
			flag=1;
			return false;
		}
	}

	if(document.PMS_Dcb_Tariff_Slab_Rates.Tariff_w_e_f_1.value=="")
	{
		alert("Enter Tariff W.e.f");
		flag=1;
		return false;
	}
	for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.mini_flag.length; i++ )
    {
        if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].checked)
        {
        	
        	if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value=="Y")
        	{
        		
        		if(document.PMS_Dcb_Tariff_Slab_Rates.min_qty.value=="")
        		{
        			flag=1;
        			alert("Enter Minimum Quantity");
        			return false;
        		}
        	}
        }
    }
	for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag.length; i++ )
    {
        if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].checked)
        {
        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=="Y")
        	{
        		if(document.PMS_Dcb_Tariff_Slab_Rates.allot_flag.value=="")
        		{
        			flag=1;
        			alert("Enter Allotted Quantity");
        			return false;
        		}
        	}
        }
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
				if(flagmaxcheck==0)
				{
				if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value) > parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value))
					{
					flag=1;
					alert("To Qty should be greater than From Qty");
					return false;
					}
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
					if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[k].value) < parseInt(document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1[re].value))
					{
					flag=1;
					alert("Tariff Rate should be greater than previous Tariff rate");
					return false;
					}
				}
			}
			for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag.length; i++ )
		    {
		        if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].checked)
		        {
		        	
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=="Y")
		        	{
		        		if(flaginside==0)
		        		{
		        		if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.allot_flag.value)==parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value))
		        		{
		        			
		        			flagalloted=1;
		        		}
		        		}
		        	}
		        }
		    }
			
		}
	}
		else
		{
			
			//flagalloted=0;
			valuestarts=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value;
			valus_starts_to_1=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value;
		
			tariff_rate_1=document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value;
			if((valuestarts!="") && (valus_starts_to_1!=""))
			{
				if(check_var==1)
				{
					if(parseInt(valuestarts) > parseInt(valus_starts_to_1))
					{
					flag=1;
					alert("Enter To Value Max than From");
					//fg=false;
					return false;
					}
				}	
			}
			if((valuestarts=="")||(valus_starts_to_1=="")||(tariff_rate_1==""))
			{
				flag=1;
				alert("Enter neccessary values");
				//fg=false;
				return false;
			}
			
			for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag.length; i++ )
		    {
		        if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].checked)
		        {
		        	
		        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=="Y")
		        	{
		        		//alert(document.PMS_Dcb_Tariff_Slab_Rates.allot_flag.value);
		        		//alert(valus_starts_to_1);
		        		if(parseInt(document.PMS_Dcb_Tariff_Slab_Rates.allot_flag.value) == parseInt(valus_starts_to_1))
		        		{
		        			//alert("flagalloted"+flagalloted);
		        			flagalloted=1;
		        			flaginside=1;
		        			//return false;
		        		}
		        	}
		        } 
		    }
				
			
		}
	for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag.length; i++ )
    {
		
		
        if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].checked)
        {
        	
        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=="Y")
        	{
        		if(flagalloted==0)
        		{
        			alert("Allotted qty must be present in Qty To Slab");
        			return false;
        		}
        		 
        	}
        }
    }
	if(flag==0)	
	{
		
		//changeIt();
		//fg=true;
		return true;
	}else
	{
		return false;
	}
		
	
}
function refresh()
{
	
	try
	{
		window.location.reload(true);
	}
	catch(e)
	{
		alert(e);
	}
	
	document.getElementById("Tariff_w_e_f_1").value="";
	document.getElementById("valus_starts_from_1").value=0;
	document.getElementById("valus_starts_to_1").value=0;
	document.getElementById("tariff_rate_1").value="";
	document.getElementById("tariff_flag").value="";
	document.getElementById("min_qty").value="";
	document.getElementById("allot_flag").value="";
	document.getElementById("maxcheck_value").value="";
	document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[1].checked=true;
	document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[1].checked=true;
	document.getElementById("min_qty").style.backgroundColor = '#ececec';
	document.getElementById("min_qty").readOnly=true;
	document.getElementById("allot_flag").style.backgroundColor = '#ececec';
	document.getElementById("allot_flag").readOnly=true;
	document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.disabled=false;
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
	    tariff_flag_value = document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag.value;
	    var Metre_Location=document.getElementById("Metre_Location").value;
	    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=get&Metre_Location="+Metre_Location+"&tariff_flag_value="+tariff_flag_value;
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
    if(xmlhttp.readyState==4)
    {
    	try
    	{
    	baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        var recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;
    	}
    	catch(e)
    	{
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
                                  rowvalue.appendChild(tabledata);
                                  var tabledata1=document.createElement("TD");
                                  var hiddentext2=document.createTextNode(TARIFF_SLAB_STARTS_TO);
                                  tabledata1.appendChild(hiddentext2);
                                  tabledata1.setAttribute("align","center");
                                  rowvalue.appendChild(tabledata1);
                                  var tabledata2=document.createElement("TD");
                                  var hiddentext3=document.createTextNode(TARIFF_RATE);
                                  tabledata2.appendChild(hiddentext3);
                                  tabledata2.setAttribute("align","center");
                                  rowvalue.appendChild(tabledata2);
                                  var ag=TARIFF_W_E_F.split("-");
                                  var datefor=ag[2]+"/"+ag[1]+"/"+ag[0]; 
                                  var tabledata3=document.createElement("TD");
                                  var hiddentext4=document.createTextNode(datefor);
                                  tabledata3.appendChild(hiddentext4);
                                  tabledata3.setAttribute("align","center");

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
function checkfun1(str)
{
	if(document.PMS_Dcb_Tariff_Slab_Rates.maxcheck[str].checked==true)
		
	{	
		flagmaxcheck=1;
		document.PMS_Dcb_Tariff_Slab_Rates.cmdsubmit_addrow.disabled=true;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].style.color='#ececec';
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].value=0;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].style.backgroundColor='#ececec';
		document.PMS_Dcb_Tariff_Slab_Rates.maxcheck_value[str].value="Y";
	}
	else
	{
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].readOnly=false;
		document.PMS_Dcb_Tariff_Slab_Rates.cmdsubmit_addrow.disabled=false;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].value="";
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].style.color='black';
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[str].style.backgroundColor='white';
		document.PMS_Dcb_Tariff_Slab_Rates.maxcheck_value[str].value="N";
	}
}

function checkfun_main(a)
{
	
	if(document.PMS_Dcb_Tariff_Slab_Rates.maxcheck.checked==true)
	{
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.readOnly=true;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.style.backgroundColor ='#ececec';
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value=0;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.readOnly=false;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value=0;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.style.backgroundColor ='#ececec';
		document.PMS_Dcb_Tariff_Slab_Rates.cmdsubmit_addrow.disabled=true;
		document.PMS_Dcb_Tariff_Slab_Rates.maxcheck_value.value='Y';
	}
	else
	{
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.readOnly=true;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.style.backgroundColor ='#ececec';
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value=0;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.readOnly=false;
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value="";
		document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.style.backgroundColor ='white';
		document.PMS_Dcb_Tariff_Slab_Rates.cmdsubmit_addrow.disabled=false;
		document.PMS_Dcb_Tariff_Slab_Rates.maxcheck_value.value='N';
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
	var splCharCheck = /[a-zA-Z!@#$%&*()+-=|_'"`~:;<>?,\/\\\^\\{\}\[\]]/;
	return (!splCharCheck.test(keychar));
}

function loadflag()
{
	
	var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	 var xmlhttp=GetXmlHttpObject();
	    if (xmlhttp==null)
	     {
	         alert ("Your browser does not support AJAX!");
	         return;
	     }
	    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadflag&Beneficiary_Name="+Beneficiary_Name
	    url=url+"&sid="+Math.random();
	    xmlhttp.open("GET",url,true);
	    xmlhttp.onreadystatechange= function()
	            {
	                 stateChangedloadflag(xmlhttp);
	            }
	    xmlhttp.send(null);
}
function stateChangedloadflag(xmlhttp)
{
    var baseres,commandres,flagres;
    document.getElementById("tariff_flag").value="";
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
        if(xmlhttp.status==200)
        {
             if((commandres=="loadflag"))
            {
                if(flagres=='success')
                {
                	var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[0].firstChild.nodeValue;
                    var TARIFF_MODE=baseres.getElementsByTagName("TARIFF_MODE")[0].firstChild.nodeValue;
                    if(TARIFF_MODE=='B')
                    {
                    	 var s=document.getElementById("schemesload");
                         s.style.display="none";
                         var l=document.getElementById("locationload");
                         l.style.display="none";
                         document.getElementById("tariff_flag_label").innerHTML="Tariff slab rate - Beneficiary based ";
                         document.getElementById("tariff_flag").value=TARIFF_MODE;
                         loadlocationgrid();
                         
                    }
                    if(TARIFF_MODE=='S')
                    {
                    	 var s=document.getElementById("schemesload");
                         s.style.display="block";
                         var l=document.getElementById("locationload");
                         l.style.display="none";
                         document.getElementById("tariff_flag_label").innerHTML="";
                         document.getElementById("tariff_flag").value=TARIFF_MODE;
                         tbody=document.getElementById("getvaluerows");
                     	try
                         {
                     		tbody.innerHTML="";
                     	}
                         catch(e) 
                         {
                         	tbody.innerText="";
                         }
                       
                         loadscheme();
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
function fun_mini_flag()
{
   
    for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.mini_flag.length; i++ )
    {
        if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].checked)
        {
        	if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value=='N')
        	{
        		document.getElementById("min_qty").value="";
        		document.getElementById("min_qty").style.backgroundColor = '#ececec';
        		document.getElementById("min_qty").readOnly=true;
        	}
        	if(document.PMS_Dcb_Tariff_Slab_Rates.mini_flag[i].value=='Y')
        	{
        		document.getElementById("min_qty").style.backgroundColor = 'white';
        		document.getElementById("min_qty").readOnly=false;
        	}
        }
    }
}
function fun_allotted_flag()
{
	 for(var i = 0; i < document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag.length; i++ )
	    {
	        if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].checked)
	        {
	        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=='N')
	        	{
	        		document.getElementById("allot_flag").value="";
	        		document.getElementById("allot_flag").style.backgroundColor = '#ececec';
	        		document.getElementById("allot_flag").readOnly=true;
	        	}
	        	if(document.PMS_Dcb_Tariff_Slab_Rates.allotted_flag[i].value=='Y')
	        	{
	        		document.getElementById("allot_flag").style.backgroundColor = 'white';
	        		document.getElementById("allot_flag").readOnly=false;
	        	}
	        }
	    }
}
function fun_alloted_gr()
{

	var v=0;
	var gr_flag=document.getElementById("allot_flag").value;
	var gr_allotted_flag=document.getElementById("allotted_flag").value;
	var valus_starts_to_1=document.getElementById("valus_starts_to_1").value;
	
	if(gr_allotted_flag=='Y')
	{
		
		if(parseInt(gr_flag)<parseInt(valus_starts_to_1))
		{
			alert("Please enter as  Two Tariff slabs \n 1. From  Qty  --  To  Qty   (limit upto Allotted Qty) \n 2. From  (Allotted Qty)  -- To Qty ");
			v=0;
		}else
		{
			v=1;
		}
	}
	 if (v==0)
		 return false;
	 else
		 return true; 
}
function fun_mini_flag_dynamic()
{
	var to_qty=0;
	var al_qty=0
	var e_flag=0;
	if(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length > 0) 
	{
		for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
		{
			try
			{
									to_qty=parseInt(document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value);
									 
			}
			catch(e) 
			{
									
									to_qty=0;
			}
			try
			{
									al_qty=parseInt(document.PMS_Dcb_Tariff_Slab_Rates.allot_flag.value);
			}
			catch(e) 
			{
									
									al_qty=0;
			}
			if (to_qty==al_qty && e_flag==0)
			{
									e_flag=1;
			}
			if (to_qty > al_qty && e_flag==0)
			{
									alert("Please enter as  Two Tariff slabs \n 1. From  Qty  --  To  Qty   (limit upto Allotted Qty) \n 2. From  (Allotted Qty)  -- To Qty ");
									document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].value="";
									document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1[k].focus();
									//document.PMS_Dcb_Tariff_Slab_Rates.cmdsubmit_addrow.disabled=true;
									//document.PMS_Dcb_Tariff_Slab_Rates.cmdsave.disabled=true;
																
									return false;
									
			}
								
		}
	}
}



function loadlocationgrid()
{
	document.getElementById('cmdsubmit_addrow').disabled=false;
	document.getElementById('cmdsave').disabled=false;
	var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	var Metre_Location=document.getElementById("Metre_Location").value;
	var Schemes=document.getElementById("Schemes").value;
	 tariff_flag=document.getElementById("tariff_flag").value;
	TARIFF_FLAG_VAR=document.PMS_Dcb_Tariff_Slab_Rates.tariff_flag_one.value;
	 var xmlhttp=GetXmlHttpObject();
	    if (xmlhttp==null)
	     {
	         alert ("Your browser does not support AJAX!");
	         return;
	     }
	  
	    if(tariff_flag=='B')
		   {   
		    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadlocationgrid&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+0+"&Schemes="+0+"&tariff_flag="+tariff_flag;
		    url=url+"&sid="+Math.random();
		    xmlhttp.open("GET",url,true);
		    xmlhttp.onreadystatechange= function()
		            {
		                 stateChangedloadlocationgrid(xmlhttp);
		            }
		    xmlhttp.send(null);
		   }
	    
	   if(TARIFF_FLAG_VAR=='L')
	   {   
	    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadlocationgrid&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+Metre_Location+"&Schemes="+Schemes+"&tariff_flag="+TARIFF_FLAG_VAR;
	    url=url+"&sid="+Math.random();
	    xmlhttp.open("GET",url,true);
	    xmlhttp.onreadystatechange= function()
	            {
	                 stateChangedloadlocationgrid(xmlhttp);
	            }
	    xmlhttp.send(null);
	   }
	   if(TARIFF_FLAG_VAR=='S')
	   {   
	    url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadlocationgrid&Beneficiary_Name="+Beneficiary_Name+"&Metre_Location="+0+"&Schemes="+Schemes+"&tariff_flag="+TARIFF_FLAG_VAR;
	    url=url+"&sid="+Math.random();
	    xmlhttp.open("GET",url,true);
	    xmlhttp.onreadystatechange= function()
	            {
	                 stateChangedloadlocationgrid(xmlhttp);
	            }
	    xmlhttp.send(null);
	   }
}
function stateChangedloadlocationgrid(xmlhttp)
{
	
	document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.value="0.0";
	document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_to_1.value="0.0";
	document.getElementById("valus_starts_to_1").style.backgroundColor = '#ececec';
	// document.getElementById("valus_starts_to_1").readOnly=true;
	document.getElementById("valus_starts_to_1").readOnly=false;
	 tbody=document.getElementById("test");
	for(k=0; k<document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1.length ; k++)
	{
		{
	 r=document.PMS_Dcb_Tariff_Slab_Rates.valus_starts_from_1[k].value;
		try
		{
		ri=r.rowIndex;
	    tbody.deleteRow(ri);
		}
		catch(e)
		{}
		}
	
	}
	document.getElementById('maxcheck').disabled=false;
    var baseres,commandres,flagres,recordres;
    if(xmlhttp.readyState==4)
    {
    	try
    	{
    	baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        var recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;
    	}
    	catch(e)
    	{
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
    	if(xmlhttp.status==200)
        {
             if(commandres=="loadlocationgrid")
            {
                if(flagres=='success')
                {
                		if(recordfound!=0)
                		{
                			if(flaginsert==0)
                			{
                				alert("Tariff Slab Rate Already Exists for this Beneficiary and Scheme ..\n If Tariff Slab Rate Is To Be Updated, Choose Update Tariff Slab Rate Option");
                				document.getElementById('cmdsubmit_addrow').disabled=true;
                				document.getElementById('cmdsave').disabled=true;
                				
                			}
                			tablebody=document.getElementById("getvaluerows");
                             var len=baseres.getElementsByTagName("QTY_FROM").length;
                             for(var i=0;i<len;i++)
                             {
                            	 var QTY_FROM=baseres.getElementsByTagName("QTY_FROM")[i].firstChild.nodeValue;
                                 var QTY_TO=baseres.getElementsByTagName("QTY_TO")[i].firstChild.nodeValue;
                                 var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[i].firstChild.nodeValue;
                                 var TARIFF_W_E_F=baseres.getElementsByTagName("TARIFF_W_E_F")[i].firstChild.nodeValue;
                                 rowvalue=document.createElement("TR");
                                 rowvalue.id=aid;
                                 
                           
                                 var tabledata=document.createElement("TD");
                                  var hiddentext1=document.createTextNode(QTY_FROM);
                                  tabledata.appendChild(hiddentext1);
                                  rowvalue.appendChild(tabledata);
                                  
                                  var tabledata1=document.createElement("TD");
                                  var hiddentext2=document.createTextNode(QTY_TO);
                                  tabledata1.appendChild(hiddentext2);
                                  rowvalue.appendChild(tabledata1);
                                  
                                  var tabledata2=document.createElement("TD");
                                  var hiddentext3=document.createTextNode(TARIFF_RATE);
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
                			//alert("Tariff Slab Rate is not present");
                			tbody=document.getElementById("getvaluerows");
                	        //alert("tbody"+tbody);
                	    	try
                	        {
                	    		tbody.innerHTML="";
                	    	}
                	        catch(e) 
                	        {
                	        	tbody.innerText="";
                	        }
                		}
                }
            }
        }
    }
}
function loadtariff()
{
	    var xmlhttp=GetXmlHttpObject();
	    if (xmlhttp==null)
	     {
	         alert ("Your browser does not support AJAX!");
	         return;
	     }
		var Beneficiary_type=document.getElementById("Beneficiary_type").value;
		url="../../../../../PMS_Dcb_Tariff_Slab_Rates?command=loadtariffvalue&Beneficiary_type="+Beneficiary_type;
	    url=url+"&sid="+Math.random();
	    xmlhttp.open("GET",url,true);
	    xmlhttp.onreadystatechange= function()
	            {
	                 stateChangedloadtariffvalue(xmlhttp);
	            }
	    xmlhttp.send(null);
}
function stateChangedloadtariffvalue(xmlhttp)
{
    if(xmlhttp.readyState==4)
    {
    	if(xmlhttp.status==200)
        {
    		try
    		{
    		var baseres,commandres,flagres,recordres;
    	    baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
    		commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
    		flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
    		document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value="";
    		}
    		catch(e)
    		{
    			
    		}
             if(commandres=="loadtariffvalue")
            {
                if(flagres=='success')
                {
    
                	try
                	{
                		
                		TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[0].firstChild.nodeValue;
                		TARIFF_WEF=baseres.getElementsByTagName("TARIFF_WEF")[0].firstChild.nodeValue;
                		TARIFF=TARIFF_WEF.split("-");
                		
                		temp=TARIFF[2]+"/"+TARIFF[1]+"/"+TARIFF[0];
                		
                		if(TARIFF_RATE!='-')
                		{
                			if(TARIFF=="null")
                    		{
                    			
                        		document.PMS_Dcb_Tariff_Slab_Rates.Tariff_w_e_f_1.value="";
                        		document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value=TARIFF_RATE;
                    		}
                			else
                			{
                				document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value=TARIFF_RATE;
                				document.PMS_Dcb_Tariff_Slab_Rates.Tariff_w_e_f_1.value=temp;
                			}
                		}
                		
                		else
                		{
                			document.PMS_Dcb_Tariff_Slab_Rates.tariff_rate_1.value="";
                    		document.PMS_Dcb_Tariff_Slab_Rates.Tariff_w_e_f_1.value="";
                		}
                		
                	}
                	catch(e)
                	{
                	}
                }
            }
        }
    }
} 
function numonly(e)
{
	var flag=true;
    var unicode=e.charCode? e.charCode : e.keyCode;
    if (unicode!=8)//backspace
    { 
        if (unicode<45||unicode>57||unicode==47||unicode==45) 
        	flag=false ;
    }
    return flag;
}  

function digit_control(s1,v)
{
	  var i;  
	  var fc=0;
	  var fcc=0;
	   var  s = document.getElementById(s1).value
	  
	  var ss="";
	  var pos=0;
      for (i = 0; i < s.length; i++)
      {
	       var c = s.charAt(i);		       
	       if (c=='.')
	       {
	    	   ss+=c;
	    	   fc=1;
	       }else
	       {
	    	   if (fcc>v)
		       {
		    	   alert(" Only "+v+" Decimal Allowed")
		    	   ss=ss;			    	   
		    	   document.getElementById(s1).value=ss;
		       }else
		    	{ ss+=c; }
	       }
		       if (fc==1)
		       {
		    	   fcc++;
		       }
      } 
	
}
