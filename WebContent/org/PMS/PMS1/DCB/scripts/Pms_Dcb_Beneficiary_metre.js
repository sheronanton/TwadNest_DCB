var flag=0,flagvar=0,flagmetre=0;var seq=0;
var flagApplicable=0;
var parentmetre_val=0;
var Applicable_slab_value=0;
var cancel_month=0;
var cancel_year=0;
function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {      
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)
    {     
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}
function divisionname()
{
		    xmlhttp=createObject();
		    if (xmlhttp==null)
		    {
		         alert ("Your browser does not support AJAX!");
		         return;
		    }
		    
		    url="../../../../../Pms_Dcb_Beneficiary_metre?command=divisionname";     
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange= function()
            {
                 stateChangeddivisionname(xmlhttp);
            }
            xmlhttp.send(null);
}
function cbenable(id,set)
{
		var x=document.getElementById(id);
	    x.disabled=set;	
}
function stateChangeddivisionname(xmlhttp)
{
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
    	try
    	{
	        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
	        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
	        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;        
	        if(xmlhttp.status==200)
	        {  
	             if(commandres=="divisionname")
	            {
	                if(flagres=='success')
	                {
	                    officename=baseres.getElementsByTagName("officename")[0].firstChild.nodeValue;
	                    if(officename!=0)
	                    {
	                        document.getElementById("divisionname").innerHTML=' - '+officename;                        
	                    }
	                    else
	                    {
	                         alert("Division name is not loaded")
	                    }
	                }
	            }
	        }
    	}catch(e) {}
    }
}

function meterdisplay()
{
   
    for(var i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
    {
        if(document.beneficary_meter.meterfixed[i].checked)
        {
            flag=1;
            meterfixed = document.beneficary_meter.meterfixed[i].value;
            var BENEFICIARY_TYPE=document.getElementById("Beneficiary_type").value;
            if(meterfixed=="y")
            {
                var d=document.getElementById("prevref");
                d.style.display="block";
            }
            else 
            {
                meterfixed = document.beneficary_meter.meterfixed[i].value;
                meterworking="n";
                document.beneficary_meter.Metre_init_reading.value="";
                
                var d=document.getElementById("prevref");
                d.style.display="none";                               
            }
        }      
        
    }
}
function parentmetrefun()
{
	
	for( i = 0; i < document.beneficary_meter.parentmetre.length; i++ )
    {
        if(document.beneficary_meter.parentmetre[i].checked)
        {
        	parentmetre_val=1;
            parentmetre_value = document.beneficary_meter.parentmetre[i].value;          
       }       
   }
}
    function chmeterworking()
    {
    for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
         {
             if(document.beneficary_meter.meterworking[i].checked)
             {
                 flagvar=1;
                 meterworking = document.beneficary_meter.meterworking[i].value;
            }
        }
    }
     function MetreType()
    {
    for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
         {
             if(document.beneficary_meter.Metre_Type[i].checked)
             {
                 flagmetre=1;
                 Metre_Type = document.beneficary_meter.Metre_Type[i].value;
            }
        }
    }
    if(flagmetre==0)
    {
        Metre_Type=0;
    }
   
function doFunction(actionval)
{
   xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }

     var Beneficiary_type=document.getElementById("Beneficiary_type").value;   
     var Habitation_Name=document.getElementById("Habitation_Name").value;
     
     if(Habitation_Name!="")
     {
        var Habitation_Name_val=Habitation_Name;
     }
     else
     {
        var Habitation_Name_val='NULL';
     }
     
     
     
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Consumption_Category=document.getElementById("Consumption_Categoryid").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Metre_Location=document.getElementById("Metre_Location").value; 
    var Multiply_factor=document.getElementById("Multiply_factor").value;
    var Metre_init_reading=document.getElementById("Metre_init_reading").value;
    var Init_Reading_Record_date=document.getElementById("Init_Reading_Record_date").value;
    var Service_Connection=document.getElementById("Service_Connection").value;
    var Service_Connection_date=document.getElementById("Service_Connection_date").value;
    var SCH_TYPE_ID=document.getElementById("SCH_TYPE_ID").value;
    var BENEFICIARY_TYPE_ID=document.getElementById("BENEFICIARY_TYPE_ID").value;
    var OTHERS_PRIVATE_SNO=document.getElementById("OTHERS_PRIVATE_SNO").value;
    var VILLAGE_PANCHAYAT_SNO=document.getElementById("VILLAGE_PANCHAYAT_SNO").value;
    var URBANLB_SNO=document.getElementById("URBANLB_SNO").value;
    
    if(Multiply_factor!="")
    {
        var Multiply_factor_val=Multiply_factor;
    }
    else
    {
         var Multiply_factor_val=0;
    }  
    if(Metre_init_reading!="")
    {
        var Metre_init_reading_val=Metre_init_reading;
    }
    else
    {
         var Metre_init_reading_val=0;
    }      
    
    if(actionval=="Add")
    {
            var valvar=validate();
            if(valvar==true)
           {
       
                url="../../../../../Pms_Dcb_Beneficiary_metre?command=add&Beneficiary_type="+Beneficiary_type+"&Habitation_Name="+Habitation_Name_val+"&Beneficiary_Name="+Beneficiary_Name+"&Consumption_Category="+Consumption_Category+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Metre_Location="+Metre_Location+"&meterfixed="+meterfixed+"&meterworking="+meterworking+"&Metre_Type="+Metre_Type+"&Multiply_factor="+Multiply_factor_val+"&Metre_init_reading="+Metre_init_reading_val+"&Init_Reading_Record_date="+Init_Reading_Record_date+"&Service_Connection="+Service_Connection+"&Service_Connection_date="+Service_Connection_date+"&SCH_TYPE_ID="+SCH_TYPE_ID+"&BENEFICIARY_TYPE_ID="+BENEFICIARY_TYPE_ID+"&OTHERS_PRIVATE_SNO="+OTHERS_PRIVATE_SNO+"&VILLAGE_PANCHAYAT_SNO="+VILLAGE_PANCHAYAT_SNO+"&URBANLB_SNO="+URBANLB_SNO+"&parentmetre_value="+parentmetre_value;
                url=url+"&sid="+Math.random();
                xmlhttp.open("GET",url,true);
                xmlhttp.onreadystatechange= stateChanged;
                xmlhttp.send(null);
            }
    }   
    else if(actionval=="Update")
    {
        
        var Meter_Sno=document.getElementById("Meter_Sno").value;
        for(var i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
        {
            if(document.beneficary_meter.meterfixed[i].checked)
            {
                    meterfixed = document.beneficary_meter.meterfixed[i].value;
            }
        }
        
        for(var i = 0; i < document.beneficary_meter.meterworking.length; i++ )
        {
            if(document.beneficary_meter.meterworking[i].checked)
            {
                    meterworking = document.beneficary_meter.meterworking[i].value;
            }
        }
    
         for(var i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
        {
            if(document.beneficary_meter.Metre_Type[i].checked)
            {
                    Metre_Type = document.beneficary_meter.Metre_Type[i].value;
            }
        }
 
         for( i = 0; i < document.beneficary_meter.parentmetre.length; i++ )
         {
             if(document.beneficary_meter.parentmetre[i].checked)
             {
                 parentmetre_value = document.beneficary_meter.parentmetre[i].value;
            }
        }
        
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=update&Meter_Sno="+Meter_Sno+"&Beneficiary_type="+Beneficiary_type+"&Habitation_Name="+Habitation_Name_val+"&Beneficiary_Name="+Beneficiary_Name+"&Consumption_Category="+Consumption_Category+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Metre_Location="+Metre_Location+"&meterfixed="+meterfixed+"&meterworking="+meterworking+"&Metre_Type="+Metre_Type+"&Multiply_factor="+Multiply_factor_val+"&Metre_init_reading="+Metre_init_reading_val+"&Init_Reading_Record_date="+Init_Reading_Record_date+"&Service_Connection="+Service_Connection+"&Service_Connection_date="+Service_Connection_date+"&SCH_TYPE_ID="+SCH_TYPE_ID+"&BENEFICIARY_TYPE_ID="+BENEFICIARY_TYPE_ID+"&OTHERS_PRIVATE_SNO="+OTHERS_PRIVATE_SNO+"&VILLAGE_PANCHAYAT_SNO="+VILLAGE_PANCHAYAT_SNO+"&URBANLB_SNO="+URBANLB_SNO+"&parentmetre_value="+parentmetre_value;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=stateChanged;
        xmlhttp.send(null);
       
    }
    else if(actionval=="Delete")
    {
    	
    	var cdate=new String(document.getElementById("cdate").value).split('/');    
    	if (cdate=="")
    	{
    		alert("Please Select Cancel (w.e.f)  ! ");
    	}
    	else
    	{
    	
		    	var month=cdate[1];
		    	var year=cdate[2];
		    	  cancel_month=month;
		    	  cancel_year=year;
		    	var	 f=confirm(" Are you sure want to cancel ?");
		    	if (f==true)
		    	{  
		    		
		    		var Meter_Sno=document.getElementById("Meter_Sno").value;
		       		var url="../../../../../Pms_Dcb_Beneficiary_metre?cyear="+year+"&cmonth="+month+"&command=delete&Meter_Sno="+Meter_Sno;
		       		url=url+"&sid="+Math.random();
		       		xmlhttp.open("GET",url,true);
		       		xmlhttp.onreadystatechange= function()
		       		{
		       			stateChanged(xmlhttp);
		       		}
		       		xmlhttp.send(null);
		    	}
    	}   	   
    	
   }  
}
function loadbeneficiarytype()
{
    document.getElementById("prevref").style.display='block';
    document.getElementById("Habitation").style.display='none';
    document.getElementById("district").style.display='none';
    document.getElementById("Metre_Location_div").style.display='none';
    document.getElementById("cmdupdate").style.display='none';
    document.getElementById("cmddelete").style.display='none';
    var xmlhttp=GetXmlHttpObject();
		    if (xmlhttp==null)
		     {
		         alert ("Your browser does not support AJAX!");
		         return;
		     }
		    url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadbeneficiarytype";
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
                         addOptionBeneficiary_type(document.beneficary_meter.Beneficiary_type,BEN_TYPE_DESC,BEN_TYPE_ID);
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
function loadhabitations()
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
   
   var Beneficiary_type=document.getElementById("Beneficiary_type").value;
   if(Beneficiary_type==6)
   {
        document.getElementById("Habitation").style.display='block';
        document.getElementById("benname").innerHTML="Panchayat Name";
        document.getElementById("location").innerHTML="Meter Location";
   }
    else
    {
        document.getElementById("benname").innerHTML="Beneficiary Name";
         document.getElementById("location").innerHTML="Metre Location";
        document.getElementById("Habitation").style.display='none';
    }
 
   if((Beneficiary_type==6)||(Beneficiary_type==2)||(Beneficiary_type==3)||(Beneficiary_type==4)||(Beneficiary_type==5)||(Beneficiary_type==1))
   {
        document.getElementById("Metre_Location").value="";
       document.getElementById("Metre_Location").style.backgroundColor = 'white';
       document.getElementById("Metre_Location").readOnly=false;
   }
    else
    {
        document.getElementById("Metre_Location").value="";
        document.getElementById("Metre_Location").style.backgroundColor = 'white';
        document.getElementById("Metre_Location").readOnly=false;
    }
   if(Beneficiary_type==6)
   {
        document.getElementById("meterlabel").style.display='block';
        document.getElementById("meterlabel").innerHTML='Habitation name';
   }
   else
   {
        document.getElementById("meterlabel").style.display='block';
        document.getElementById("meterlabel").innerHTML='Meter Location';
   }
}
function stateChangedloadhabitations(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadhabitations")
            {
                if(flagres=='success')
                {
                    var HAB_CODE_len=baseres.getElementsByTagName("HAB_CODE").length;
                    for(var i=0;i<HAB_CODE_len;i++)
                     {
                         var HAB_CODE=baseres.getElementsByTagName("HAB_CODE")[i].firstChild.nodeValue;
                         var HNAME=baseres.getElementsByTagName("HNAME")[i].firstChild.nodeValue;
                         addOptionhabitations(document.beneficary_meter.Habitation_Name,HNAME,HAB_CODE);
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
function addOptionhabitations(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function loadbeneficiaryname()
{
    var child_div=0;
    try
    {
    	 child_div=document.getElementById("div").value
    }catch(e){}
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_type=document.getElementById("Beneficiary_type").value;
      	url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadbeneficiaryname&child_div="+child_div+"&Beneficiary_type="+Beneficiary_type;
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
        document.beneficary_meter.Beneficiary_Name.length=1;
        if(xmlhttp.status==200)
        {
             if((commandres=="loadbeneficiaryname")||(commandres=="loadbenname"))
            {
                if(flagres=='success')
                {
                    var BENEFICIARY_SNO_len=baseres.getElementsByTagName("BENEFICIARY_SNO").length;
                    for(var i=0;i<BENEFICIARY_SNO_len;i++)
                     {
                         var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                         addOptionbeneficiaryname(document.beneficary_meter.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
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
function loadcategory()
{
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     
      var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    if(Beneficiary_Name!=-1)
    {
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadcategory&Beneficiary_Name="+Beneficiary_Name;
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadcategory(xmlhttp);
            }
        xmlhttp.send(null);
    }
}
function stateChangedloadcategory(xmlhttp)
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
        if(xmlhttp.status==200)
        {
             if(commandres=="loadcategory")
            {
                if(flagres=='success')
                {
                    
                   
                 
                         var BEN_CONS_CATEGORY=baseres.getElementsByTagName("BEN_CONS_CATEGORY")[0].firstChild.nodeValue;
                         var BENEFICIARY_TYPE=baseres.getElementsByTagName("BENEFICIARY_TYPE")[0].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                         var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                         var OTHERS_PRIVATE_SNO=baseres.getElementsByTagName("OTHERS_PRIVATE_SNO")[0].firstChild.nodeValue;
                         var VILLAGE_PANCHAYAT_SNO=baseres.getElementsByTagName("VILLAGE_PANCHAYAT_SNO")[0].firstChild.nodeValue;
                         var URBANLB_SNO=baseres.getElementsByTagName("URBANLB_SNO")[0].firstChild.nodeValue;
                        document.getElementById("BENEFICIARY_TYPE_ID").value= BENEFICIARY_TYPE_ID;
                        document.getElementById("OTHERS_PRIVATE_SNO").value= OTHERS_PRIVATE_SNO;
                        document.getElementById("VILLAGE_PANCHAYAT_SNO").value= VILLAGE_PANCHAYAT_SNO;
                        document.getElementById("URBANLB_SNO").value= URBANLB_SNO;
                     
                         
                         if(BEN_CONS_CATEGORY==0)
                         {
                            document.getElementById("Consumption_Category").value='Single Meter';
                            document.getElementById("Consumption_Categoryid").value=BEN_CONS_CATEGORY;
                            document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                         }
                         if((BEN_CONS_CATEGORY==0))
                         {
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BEN_CONS_CATEGORY;
                            document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                         }
                         if((BEN_CONS_CATEGORY==0)&&(BENEFICIARY_TYPE==6))
                         {
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BEN_CONS_CATEGORY;
                            document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                         }
                         if((BEN_CONS_CATEGORY==0)&&(BENEFICIARY_TYPE>6))
                         {
                       
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BEN_CONS_CATEGORY;
                            document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                         }
                        if(BENEFICIARY_TYPE_ID>6)
                        {
                        	
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
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
function loadsubdivision()
{
	
	
	var div=0;
	try
	{
		div=document.getElementById("div1").value;
	//	alert ("div is :"+div);
	}catch(e){}
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadsubdivision&div="+div;
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadsubdivision(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadsubdivision(xmlhttp)
{
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadsubdivision")
            {
                if(flagres=='success')
                {
                    
                    var SUBDIVISION_OFFICE_ID_len=baseres.getElementsByTagName("SUBDIVISION_OFFICE_ID").length;
                    document.beneficary_meter.SubDivision.length=1; 
                    for(var i=0;i<SUBDIVISION_OFFICE_ID_len;i++)
                     {
                         var SUBDIVISION_OFFICE_ID=baseres.getElementsByTagName("SUBDIVISION_OFFICE_ID")[i].firstChild.nodeValue;
                         var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
                         addOptiondesc(document.beneficary_meter.SubDivision,OFFICE_NAME,SUBDIVISION_OFFICE_ID);
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
 

function loadschemes()
{
    
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadschemes";
  //  alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadschemes(xmlhttp);
            }
    xmlhttp.send(null);
}
function stateChangedloadschemes(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4) 
    {
    	 
    	try
    	{
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {  
             if(commandres=="loadschemes")
            {
                if(flagres=='success')
                {
                    
                    var SCHEME_ID_len=baseres.getElementsByTagName("SCHEME_ID").length;
                   
                    for(var i=0;i<SCHEME_ID_len;i++)
                     {
                         var SCHEME_ID=baseres.getElementsByTagName("SCHEME_ID")[i].firstChild.nodeValue;
                         var SCHEME_NAME=baseres.getElementsByTagName("SCHEME_NAME")[i].firstChild.nodeValue;
                       
                         SCHEME_NAME=SCHEME_NAME.replace ('*','&');    
                         addOptiondesc(document.beneficary_meter.Schemes,SCHEME_NAME,SCHEME_ID);
                     }
                }
                 else
                {
                    alert("Not Loaded schemes");  
                }
            }
        }
    	}catch(e) {}	
    }
}
function addOptiondesc(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}

function stateChanged()
{
    var baseres,commandres,flagres,recordres;
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        {
           
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
         
            if(commandres=="add")
            {
                if(flagres=='success')
                {
                   countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
                   if(countinsert==0) //countinsert
                    { 
                    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
                   var agree=confirm("Data Saved Sucessfully ...  Add another Meter Reading For This Beneficiary");
                   if (agree)
                   {
                        var BENEFICIARY_TYPE=document.getElementById("Beneficiary_type").value;
                       for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
                        {
                            document.beneficary_meter.meterworking[i].checked=false;
                        }
                         for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
                        {
                            document.beneficary_meter.meterfixed[i].checked=false;
                        }   
                         for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
                        {
                            document.beneficary_meter.Metre_Type[i].checked=false;
                        }    
                       document.getElementById("meterworking").checked=false;
                        document.getElementById("Habitation_Name").value="";
                       document.getElementById("Multiply_factor").value=1;
                       document.getElementById("Metre_init_reading").value="";
                       document.getElementById("Init_Reading_Record_date").value="";
                       document.getElementById("Metre_Location").value="";
                       if(BENEFICIARY_TYPE>5)
                       {
                       document.getElementById("Metre_Location").value="";
                       }
                       document.beneficary_meter.parentmetre[1].checked=true;
                       document.getElementById("Service_Connection").value="";
                       document.getElementById("Service_Connection_date").value="";
                       
                        tablebody=document.getElementById("getvaluerows");
	                    var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
	                    var office_name=baseres.getElementsByTagName("office_name")[0].firstChild.nodeValue;
	                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
	                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
	                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
	                    var Metre_Location=baseres.getElementsByTagName("Metre_Location")[0].firstChild.nodeValue;
	                    var meterfixed=baseres.getElementsByTagName("meterfixed")[0].firstChild.nodeValue;
	                    var meterworking=baseres.getElementsByTagName("meterworking")[0].firstChild.nodeValue;
	                    var Consumption_Category=baseres.getElementsByTagName("Consumption_Category")[0].firstChild.nodeValue;
	                    var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
		                    var rowvalue=document.createElement("TR");
		                    rowvalue.id=metre_sno;
		                    var tabledata=document.createElement("TD");
		                    var anc=document.createElement("A");
		                    var url="javascript:loadvaluesfromtable('"+metre_sno+"')";
		                    var nameval=document.createTextNode("Edit");
		                    anc.href=url;
		                    anc.appendChild(nameval);
		                    tabledata.appendChild(anc);
                   
		                     var hiddentext1=document.createElement("input");
		                     hiddentext1.type="hidden";
		                     hiddentext1.name="qual1";
		                     hiddentext1.id="qual1";
		                     hiddentext1.text="qual1";
		                     hiddentext1.value=metre_sno;
                    
		                     tabledata.appendChild(hiddentext1);
                   
		                     rowvalue.appendChild(tabledata);
		                     seq++;
                    
		                     var sno_value=document.createElement("TD");
		                     var sno=document.createTextNode(seq);
		                     sno_value.appendChild(sno);
		                     rowvalue.appendChild(sno_value);
		                     
	                        var METRE_LOCATION_VAL=document.createElement("TD");
	                        var METRE_LOCATION=document.createTextNode(Metre_Location);
	                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
	                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
		                    var tabledata0=document.createElement("TD");
		                    var office_name=document.createTextNode(office_name);
		                    tabledata0.appendChild(office_name);
		                    rowvalue.appendChild(tabledata0);
                    
                   
		                    var tabledata1=document.createElement("TD");
		                    var SCH_NAME=document.createTextNode(SCH_NAME);
		                    tabledata1.appendChild(SCH_NAME);
		                    rowvalue.appendChild(tabledata1);
                    
		                     var meterfixed_val=document.createElement("TD");
		                    if((meterfixed=='y')||(meterfixed=='Y'))
		                    {
		                        meterfixed='Yes';
		                        var meterfixed=document.createTextNode(meterfixed);
		                    }
		                    
		                    else
		                     {
		                        meterfixed='No';
		                        var meterfixed=document.createTextNode(meterfixed);
		                    }
		                    meterfixed_val.appendChild(meterfixed);
		                    rowvalue.appendChild(meterfixed_val);
                    
		                    var meterworking_val=document.createElement("TD");
		                    if((meterworking=='y')||(meterworking=='Y'))
		                    {
		                        meterworking='Yes';
		                        var meterworking=document.createTextNode(meterworking);
		                    }
		                    else
		                     {
		                        meterworking='No';
		                        var meterworking=document.createTextNode(meterworking);
		                    }
		                    meterworking_val.appendChild(meterworking);
		                    rowvalue.appendChild(meterworking_val);
                        
		                    tablebody.appendChild(rowvalue);
		                    return true ;
                   }
                        
                    else
                    {
                        for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
                        {
                            document.beneficary_meter.meterworking[i].checked=false;
                        }
                         for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
                        {
                            document.beneficary_meter.meterfixed[i].checked=false;
                        }    
                         for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
                        {
                            document.beneficary_meter.Metre_Type[i].checked=false;
                        }  
                        var BENEFICIARY_TYPE=document.getElementById("Beneficiary_type").value;
                        document.getElementById("SubDivision").value="";
                        
                        if(BENEFICIARY_TYPE>5)
                        {
                        document.getElementById("Metre_Location").value="";
                        }
                       document.getElementById("Schemes").value="";
                       document.getElementById("Beneficiary_type").value="";
                       document.getElementById("district_Name").value="";
                       document.getElementById("block_Name").value="";
                       document.getElementById("Beneficiary_Name").value="";
                        document.getElementById("Consumption_Category").value="";
                       document.getElementById("Habitation_Name").value="";
                       document.getElementById("Multiply_factor").value=1;
                       document.getElementById("Metre_init_reading").value="";
                       document.getElementById("Init_Reading_Record_date").value="";
                       document.beneficary_meter.parentmetre[1].checked=true;
                       document.getElementById("Service_Connection").value="";
                       document.getElementById("Service_Connection_date").value="";
                        document.getElementById("SCH_TYPE_ID").value="";
                        document.getElementById("BENEFICIARY_TYPE_ID").value="";
                        document.getElementById("OTHERS_PRIVATE_SNO").value="";
                        document.getElementById("VILLAGE_PANCHAYAT_SNO").value="";
                        document.getElementById("URBANLB_SNO").value="";
                        document.getElementById("Metre_Location").value="";
                        
                        tablebody=document.getElementById("getvaluerows");
                    var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
               
                        
                        tablebody=document.getElementById("getvaluerows");
                    var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                    
                    var office_name=baseres.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                    var Metre_Location=baseres.getElementsByTagName("Metre_Location")[0].firstChild.nodeValue;
                    var meterfixed=baseres.getElementsByTagName("meterfixed")[0].firstChild.nodeValue;
                    var meterworking=baseres.getElementsByTagName("meterworking")[0].firstChild.nodeValue;
                    var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    tbody=document.getElementById("getvaluerows");
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=metre_sno;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+metre_sno+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                   
                     var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=metre_sno;
                    
                    tabledata.appendChild(hiddentext1);
                    rowvalue.appendChild(tabledata);
                   
                    seq++;
                    
                     var sno_value=document.createElement("TD");
                    var sno=document.createTextNode(seq);
                    sno_value.appendChild(sno);
                    rowvalue.appendChild(sno_value);
                    
                    
                   
                  //   document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(Metre_Location);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    var tabledata0=document.createElement("TD");
                    var office_name=document.createTextNode(office_name);
                    tabledata0.appendChild(office_name);
                    rowvalue.appendChild(tabledata0);
                    
                   
                var tabledata1=document.createElement("TD");
                    var SCH_NAME=document.createTextNode(SCH_NAME);
                    tabledata1.appendChild(SCH_NAME);
                    rowvalue.appendChild(tabledata1);
                    
                    
                  
                    
                    
                    
                    
                     var meterfixed_val=document.createElement("TD");
                    if((meterfixed=='y')||(meterfixed=='Y'))
                    {
                        meterfixed='Yes';
                        var meterfixed=document.createTextNode(meterfixed);
                    }
                    else
                     {
                        meterfixed='No';
                        var meterfixed=document.createTextNode(meterfixed);
                    }
                    meterfixed_val.appendChild(meterfixed);
                    rowvalue.appendChild(meterfixed_val);
                    
                    
                      var meterworking_val=document.createElement("TD");
                    if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    else
                     {
                        meterworking='No';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    meterworking_val.appendChild(meterworking);
                    rowvalue.appendChild(meterworking_val);
                    
                
                      
                        
                    tablebody.appendChild(rowvalue);
                        
                       return true ;
                    }
                  }
                  else if(countinsert==1)
                  {
                        alert("Record is already present");
                        
                  }
                }
                 else
                {
                    alert("Not success");
                }
            }
          else if(commandres=="get")
            {
                 if(flagres=='success')
                {
                    tablebody=document.getElementById("getvaluerows");
                    var len=baseres.getElementsByTagName("METRE_SNO").length;
                    for(var i=0;i<len;i++)
                    {
                     var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[i].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                //    var SUB_DIV_ID=baseres.getElementsByTagName("SUB_DIV_ID")[i].firstChild.nodeValue;
                    var officename=baseres.getElementsByTagName("officename")[i].firstChild.nodeValue;
                  //  var SCHEME_SNO=baseres.getElementsByTagName("SCHEME_SNO")[i].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[i].firstChild.nodeValue;
                 //   var HABITATION_SNO=baseres.getElementsByTagName("HABITATION_SNO")[i].firstChild.nodeValue;
                    var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[i].firstChild.nodeValue;
                 //   var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
                    var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[i].firstChild.nodeValue;
                    var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
                     var meterworking=baseres.getElementsByTagName("meterworking")[i].firstChild.nodeValue;
                     var BEN_TYPE_ID=baseres.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
                   
                  
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=METRE_SNO;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+METRE_SNO+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    
                    
                  var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=METRE_SNO;
                     tabledata.appendChild(hiddentext1);
                     
                    rowvalue.appendChild(tabledata);
                     
                    var tabledata8=document.createElement("TD");
                    var BEN_TYPE_DESC=document.createTextNode(BEN_TYPE_DESC);
                    tabledata8.appendChild(BEN_TYPE_DESC);
                    rowvalue.appendChild(tabledata8);
                    
                    var tabledata2=document.createElement("TD");
                    var BENEFICIARY_NAME=document.createTextNode(BENEFICIARY_NAME);
                    tabledata2.appendChild(BENEFICIARY_NAME);
                    rowvalue.appendChild(tabledata2);
                   
                     
                    var tabledata3=document.createElement("TD");
                    var officename=document.createTextNode(officename);
                    tabledata3.appendChild(officename);
                    rowvalue.appendChild(tabledata3);
                    
                     var tabledata4=document.createElement("TD");
                    var SCH_NAME=document.createTextNode(SCH_NAME);
                    tabledata4.appendChild(SCH_NAME);
                    rowvalue.appendChild(tabledata4);
                    
                  
                    var tabledata7=document.createElement("TD");
                    var TARIFF_RATE=document.createTextNode(TARIFF_RATE);
                    tabledata7.appendChild(TARIFF_RATE);
                    rowvalue.appendChild(tabledata7);
                    
                     var tabledata5=document.createElement("TD");
                   
                    if((METRE_FIXED=='y')||(METRE_FIXED=='Y'))
                    {
                        METRE_FIXED='Yes';
                        var METRE_FIXED=document.createTextNode(METRE_FIXED);
                    }
                    else
                     {
                        METRE_FIXED='No';
                        var METRE_FIXED=document.createTextNode(METRE_FIXED);
                    }
                    tabledata5.appendChild(METRE_FIXED);
                    
                      var meterworking_val=document.createElement("TD");
                    if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    else
                     {
                        meterworking='No';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    meterworking_val.appendChild(meterworking);
                    rowvalue.appendChild(meterworking_val);
                    
                    
                    
                    rowvalue.appendChild(tabledata5);
                    
                   
                    
                   
                    
                  
                     
                      
                      
                      tablebody.appendChild(rowvalue);
                      
                    }
                   
                } 
                else
                {
                   alert("Not success");
                }
            }
             else if(commandres=="update")
            {
                if(flagres=='success')
                {
                   
                    alert( 'Successfully Updated');
                    var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                    
                    var office_name=baseres.getElementsByTagName("office_name")[0].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[0].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[0].firstChild.nodeValue;
                    var Metre_Location=baseres.getElementsByTagName("Metre_Location")[0].firstChild.nodeValue;
                    //var Tariff_Id=baseres.getElementsByTagName("Tariff_Id")[0].firstChild.nodeValue;
                    var meterfixed=baseres.getElementsByTagName("meterfixed")[0].firstChild.nodeValue;
                     var meterworking=baseres.getElementsByTagName("meterworking")[0].firstChild.nodeValue;
                     var Consumption_Category=baseres.getElementsByTagName("Consumption_Category")[0].firstChild.nodeValue;
                     var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    // var TARIFF_MODE=baseres.getElementsByTagName("TARIFF_MODE")[0].firstChild.nodeValue;
                    var rvar=document.getElementById(metre_sno);
                    var rcells=rvar.cells;
                    
                   if(Consumption_Category==1)
                    {
                         document.getElementById("meterlabel").style.display='block';
                       rcells.item(2).firstChild.nodeValue=Metre_Location;
                        
                    }
                   
                     if(BENEFICIARY_TYPE_ID==6)
                    {
                         document.getElementById("meterlabel").style.display='block';
                       rcells.item(2).firstChild.nodeValue=Metre_Location;
                        
                    }
                    
                    rcells.item(3).firstChild.nodeValue=office_name;
                    rcells.item(4).firstChild.nodeValue=SCH_NAME;
                   
                    
                    
                    
                    if((meterfixed=='y')||(meterfixed=='Y'))
                    {
                        meterfixed='Yes';
                        var METRE_FIXED=document.createTextNode(METRE_FIXED);
                    }
                    else
                     {
                        meterfixed='No';
                        var METRE_FIXED=document.createTextNode(METRE_FIXED);
                    }
                    rcells.item(5).firstChild.nodeValue=meterfixed;
                 
                     if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                      
                    }
                    else
                     {
                        meterworking='No';
                        
                    }
                   rcells.item(6).firstChild.nodeValue=meterworking;
                  
                     document.getElementById("Meter_Sno").value="";
    document.getElementById("Beneficiary_type").value="";
    document.getElementById("Habitation_Name").value="";
    document.getElementById("Beneficiary_Name").value="";
    
    document.getElementById("Consumption_Category").value="";
  
    document.getElementById("SubDivision").value="";
    document.getElementById("Schemes").value="";
    document.getElementById("Metre_Location").value="";
   
   document.getElementById("Metre_Location").value="";
   for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
    {
        document.beneficary_meter.meterworking[i].checked=false;
    }
 for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
 {
      document.beneficary_meter.meterfixed[i].checked=false;
 }    
  //  document.getElementById("Metre_Type").value="";
   for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
 {
      document.beneficary_meter.Metre_Type[i].checked=false;
 }  
    document.getElementById("Multiply_factor").value=1;
    document.getElementById("Metre_init_reading").value="";
    document.getElementById("Init_Reading_Record_date").value="";
    
    document.beneficary_meter.parentmetre[1].checked=true;
    document.getElementById("Service_Connection").value="";
    document.getElementById("Service_Connection_date").value="";
    
    //document.getElementById("Tariff_Id_val").value="";
    document.getElementById("SCH_TYPE_ID").value="";
    document.getElementById("BENEFICIARY_TYPE_ID").value="";
    document.getElementById("OTHERS_PRIVATE_SNO").value="";
    document.getElementById("VILLAGE_PANCHAYAT_SNO").value="";
    document.getElementById("URBANLB_SNO").value="";
    
                   }
                 else
                {
                    alert("Not updated");
                }
            }
            else if(commandres=="delete")
            {
                if(flagres=='success')
                {
                    
                     var metre_sno=baseres.getElementsByTagName("metre_sno")[0].firstChild.nodeValue;
                     var checkcons=baseres.getElementsByTagName("checkcons")[0].firstChild.nodeValue;
                     if(checkcons==1)
                     {
                    	var mth=Array("","Jan","Feb","Mar","Apr","May","June","July","Aug","Sep","Oct","Nov","Dec");
                    	
                    	var msg_met="Meter details For This Beneficiary Cannot Be Deleted ... \n Pumping Return has been entered for "+mth[cancel_month]+" - "+cancel_year;
                    		msg_met+="Pumping Return should be zero(0) for deletion";
                    	alert(msg_met);
                     }
                     else
                     {
                        
                        var tbody=document.getElementById("existing");
                        var r=document.getElementById(metre_sno);
                        
                        var ri=r.rowIndex;
                        tbody.deleteRow(ri);
                        alert('Successfully Deleted');
                        document.getElementById("Meter_Sno").value="";
					    document.getElementById("Beneficiary_type").value="";
					    document.getElementById("Habitation_Name").value="";
					    document.getElementById("Beneficiary_Name").value=0;
					    document.getElementById("Consumption_Category").value="";
					    //document.getElementById("Multi_WS_Category").value="";
					    //document.getElementById("Tariff_Id").value="";
					    document.getElementById("SubDivision").value="";
					    document.getElementById("Schemes").value="";
					    document.getElementById("Metre_Location").value="";
					    //document.getElementById("meterfixed").value="y";
					    //document.getElementById("meterworking").value="";
					    document.getElementById("Metre_Location").value="";
					    
					    for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
					    {
					    	document.beneficary_meter.meterworking[i].checked=false;
					    }
					    for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
					    {
					    	document.beneficary_meter.meterfixed[i].checked=false;
 }    
  //  document.getElementById("Metre_Type").value="";
   for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
 {
      document.beneficary_meter.Metre_Type[i].checked=false;
 }  
    document.getElementById("Multiply_factor").value=1;
    document.getElementById("Metre_init_reading").value="";
    document.getElementById("Init_Reading_Record_date").value="";
    //document.getElementById("Allotted_Qty").value="";
  //  document.getElementById("Min_bill_Qty").value="";
   // document.getElementById("Excess_Tariff_Rate").value="";
    document.getElementById("Service_Connection").value="";
    document.getElementById("Service_Connection_date").value="";
    
    //document.getElementById("Tariff_Id_val").value="";
    document.getElementById("SCH_TYPE_ID").value="";
    document.getElementById("BENEFICIARY_TYPE_ID").value="";
    document.getElementById("OTHERS_PRIVATE_SNO").value="";
    document.getElementById("VILLAGE_PANCHAYAT_SNO").value="";
    document.getElementById("URBANLB_SNO").value="";
    
                    }
                    
                }
                 else
                {
                    alert("Not deleted");
                }
            }
        }
    }
}
function loadvaluesfromtable(r)
{
	
	
   //cbenable('SubDivision',true);
   cbenable( 'Schemes',true);
	
   document.getElementById("cmdadd").style.display='none';
   document.getElementById("cmdupdate").style.display='block';
   document.getElementById("cmddelete").style.display='block';
   document.getElementById("cmdupdate").style.display='inline';
   document.getElementById("cmddelete").style.display='inline';
    
     /*document.getElementById("cmdupdate").style.display='none';
     document.getElementById("cmddelete").style.display='none';*/
     
     
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    //var d=document.getElementById("prevref");
  //  d.style.display="block";
 // document.getElementById('cmdadd').disabled=true;
 //   document.getElementById('cmdupdate').disabled=false;
 //   document.getElementById('cmddelete').disabled=false;
    var rvar=document.getElementById(r);
    var rcells=rvar.cells;
    var rvalue=rcells.item(0).lastChild.value;
    
    
    
    
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=getgrid&msno="+rvalue;
       //alert("getgrid value");
        url=url+"&sid="+Math.random();
      //  alert(url);
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange=function()
            {
                 stateChangedgetgrid(xmlhttp);
            }
        
        
        xmlhttp.send(null);
        
  
}
function stateChangedgetgrid(xmlhttp)
{
         var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="getgrid")
            {
                if(flagres=='success')
                {
                
                    document.beneficary_meter.Beneficiary_Name.length=1;
                    document.beneficary_meter.Habitation_Name.length=1;
                    var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[0].firstChild.nodeValue;
                    var SUB_DIV_ID=baseres.getElementsByTagName("SUB_DIV_ID")[0].firstChild.nodeValue;
                     var SCHEME_SNO=baseres.getElementsByTagName("SCHEME_SNO")[0].firstChild.nodeValue;
                     var SCH_TYPE_ID=baseres.getElementsByTagName("SCH_TYPE_ID")[0].firstChild.nodeValue;
                    var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    var HAB_var=baseres.getElementsByTagName("HAB_var")[0].firstChild.nodeValue;
                      HAB_var=HAB_var.replace ('*','&');
                   // var TARIFF_MODE=baseres.getElementsByTagName("TARIFF_MODE")[0].firstChild.nodeValue;
                 //  alert("TARIFF_MODE"+TARIFF_MODE);
                   // var ALLOTED_FLG=baseres.getElementsByTagName("ALLOTED_FLG")[0].firstChild.nodeValue;
                    var PARENT_METRE=baseres.getElementsByTagName("PARENT_METRE")[0].firstChild.nodeValue;
                   // var TARIFF_SLAB_FLG=baseres.getElementsByTagName("TARIFF_SLAB_FLG")[0].firstChild.nodeValue;
                    //alert("dsdsd");
                    //alert(PARENT_METRE);
                     if(BENEFICIARY_TYPE_ID==6)
                    {
                      
                            document.getElementById("Habitation").style.display='block';
                            document.getElementById("benname").innerHTML="Panchayat Name";
                            document.getElementById("location").innerHTML="Habitation Name";
        
                    }
                    else
                    {
                            document.getElementById("benname").innerHTML="Beneficiary Name";
                            document.getElementById("location").innerHTML="Metre Location";
                            document.getElementById("Habitation").style.display='none';
       
                    }
                   if((BENEFICIARY_TYPE_ID==6)||(BENEFICIARY_TYPE_ID==2)||(BENEFICIARY_TYPE_ID==3)||(BENEFICIARY_TYPE_ID==4)||(BENEFICIARY_TYPE_ID==5)||(BENEFICIARY_TYPE_ID==1))
                    {
                        //document.getElementById("fixed").style.display='none';
                    }
                     else
                    {
                       // document.getElementById("fixed").style.display='block';
                    }
                    var BULKWS_CATEGORY=baseres.getElementsByTagName("BULKWS_CATEGORY")[0].firstChild.nodeValue;
                    var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[0].firstChild.nodeValue;
                    METRE_LOCATION=METRE_LOCATION.replace('*','&');
                   
                    var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[0].firstChild.nodeValue;
                    var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[0].firstChild.nodeValue;
                    var MULTIPLY_FACTOR=baseres.getElementsByTagName("MULTIPLY_FACTOR")[0].firstChild.nodeValue;
                     var METRE_TYPE=baseres.getElementsByTagName("METRE_TYPE")[0].firstChild.nodeValue;
                      var METRE_INIT_READING=baseres.getElementsByTagName("METRE_INIT_READING")[0].firstChild.nodeValue;
                    var INIT_READING_RECORD_DT=baseres.getElementsByTagName("INIT_READING_RECORD_DT")[0].firstChild.nodeValue;
                     if(INIT_READING_RECORD_DT=="-")
                    {
                        INIT_READING_RECORD_DT="";
                    }
                    else
                    {                    
                        var temp=INIT_READING_RECORD_DT.split("-");
                        var INIT_READING_RECORD_DT=temp[2]+"/"+temp[1]+"/"+temp[0]; 
                    }
                   
                    
                  
                   var HABITATION_SNO=baseres.getElementsByTagName("HABITATION_SNO")[0].firstChild.nodeValue;
                   var BENEFICIARY_TYPE_ID=baseres.getElementsByTagName("BENEFICIARY_TYPE_ID")[0].firstChild.nodeValue;
                    var OTHERS_PRIVATE_SNO=baseres.getElementsByTagName("OTHERS_PRIVATE_SNO")[0].firstChild.nodeValue;
                    var VILLAGE_PANCHAYAT_SNO=baseres.getElementsByTagName("VILLAGE_PANCHAYAT_SNO")[0].firstChild.nodeValue;
                    var URBANLB_SNO=baseres.getElementsByTagName("URBANLB_SNO")[0].firstChild.nodeValue;
                    
                    
                    var BULKWS_CATEGORY=baseres.getElementsByTagName("BULKWS_CATEGORY")[0].firstChild.nodeValue;
                    
                    
                    if(BULKWS_CATEGORY==0)
                         {
                           // document.getElementById("bulk").style.display='none';
                          
                            document.getElementById("Consumption_Category").value='Single Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value=METRE_LOCATION;
                           // document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                             document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                            //document.getElementById("Metre_Location_div_name").style.display='none';
                         }
                         if(BULKWS_CATEGORY==0)
                         {
                           // document.getElementById("bulk").style.display='none';
                          
                            document.getElementById("Consumption_Category").value='Single Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value=METRE_LOCATION;
                            //document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                             document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                            //document.getElementById("Metre_Location_div_name").style.display='none';
                         }
                         if ((BULKWS_CATEGORY==0)&&(BENEFICIARY_TYPE_ID==6))
                         {
                         //   alert(BULKWS_CATEGORY);
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                            document.getElementById('Metre_Location').value=METRE_LOCATION;
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                         }
                         if ((BULKWS_CATEGORY==0)&&(BENEFICIARY_TYPE_ID==6))
                         {
                         //   alert(BULKWS_CATEGORY);
                            document.getElementById("Consumption_Category").value='Single Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                            document.getElementById('Metre_Location').value=METRE_LOCATION;
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                         }
                    if ((BULKWS_CATEGORY==0)&&(BENEFICIARY_TYPE_ID>6))
                         {
                         //   alert(BULKWS_CATEGORY);
                            document.getElementById("Consumption_Category").value='Multi Meter';
                            document.getElementById("Consumption_Categoryid").value=BULKWS_CATEGORY;
                             document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                            document.getElementById("Metre_Location_div").style.display='block';
                            document.getElementById('Metre_Location').value=METRE_LOCATION;
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                         }
                        if(BENEFICIARY_TYPE_ID>6)
                        {
                            document.getElementById("Metre_Location").style.backgroundColor = 'white';
                            document.getElementById("Metre_Location").readOnly=false;
                         //   document.getElementById("Metre_Location").value="";
                            document.getElementById("Metre_Location_div").style.display='block';
                          //  document.getElementById("Metre_Location_div_name").style.display='block';
                          document.getElementById('Metre_Location').value=METRE_LOCATION;
                        }

                    
                    if(baseres.getElementsByTagName("SERVICE_CON_NO")[0].firstChild == null){
						var SERVICE_CON_NO = "";
					}else{
						var SERVICE_CON_NO=baseres.getElementsByTagName("SERVICE_CON_NO")[0].firstChild.nodeValue;
					}
                    
                    /*
                    if(SERVICE_CON_NO=="-")
                    {
                        SERVICE_CON_NO="";
                    }
                    else
                    {                    
                         SERVICE_CON_NO=SERVICE_CON_NO;
                    }
                    */
                    var SEVICE_CONN_DATE=baseres.getElementsByTagName("SEVICE_CONN_DATE")[0].firstChild.nodeValue;
                    if(SEVICE_CONN_DATE=="-")
                    {
                        SEVICE_CONN_DATE="";
                    }
                    else
                    {                    
                        var temp=SEVICE_CONN_DATE.split("-");
                        var SEVICE_CONN_DATE=temp[2]+"/"+temp[1]+"/"+temp[0]; 
                    }
                    var BULKWS_CATEGORY=baseres.getElementsByTagName("BULKWS_CATEGORY")[0].firstChild.nodeValue;
                  //  var EXCESS_TARIFF_RATE=baseres.getElementsByTagName("EXCESS_TARIFF_RATE")[0].firstChild.nodeValue;
                    var BEN_SNO=baseres.getElementsByTagName("BEN_SNO")[0].firstChild.nodeValue;
                   
                     
                    var BENEFICIARY_SNO_len=baseres.getElementsByTagName("BENEFICIARY_SNO").length; 
                    
                    for(var i=0;i<BENEFICIARY_SNO_len;i++)
                     {
                         var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                         
                         addOptionbeneficiaryname(document.beneficary_meter.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
                       //  alert(BEN_SNO);
                           document.getElementById('Beneficiary_Name').value=BEN_SNO;
                     }
                     
                    
                  
                    if(HAB_var==1)
                    {
                        HAB_SNO_len=baseres.getElementsByTagName("HAB_SNO").length; 
                        for(var i=0;i<HAB_SNO_len;i++)
                     {
                         var HAB_SNO=baseres.getElementsByTagName("HAB_SNO")[i].firstChild.nodeValue;
                         var HAB_NAME	=baseres.getElementsByTagName("HAB_NAME")[i].firstChild.nodeValue;
                        
                        addOptionloadhabitationlist(document.beneficary_meter.Habitation_Name,HAB_NAME,HAB_SNO);
                        document.getElementById('Habitation_Name').value=HABITATION_SNO;
                     }
                    }
                    document.getElementById('Meter_Sno').value=METRE_SNO;
                    document.getElementById('SubDivision').value=SUB_DIV_ID;
                     document.getElementById('Schemes').value=SCHEME_SNO;
                     document.getElementById('SCH_TYPE_ID').value=SCH_TYPE_ID;
                      document.getElementById('Beneficiary_type').value=BENEFICIARY_TYPE_ID;
                 
                   if((BENEFICIARY_TYPE_ID!=6)&&(BENEFICIARY_TYPE_ID!=2)&&(BENEFICIARY_TYPE_ID!=3)&&(BENEFICIARY_TYPE_ID!=4)&&(BENEFICIARY_TYPE_ID!=5)&&(BENEFICIARY_TYPE_ID!=1))
                         {
                          
                           
                          //   document.getElementById("Tariff_Id").style.backgroundColor = 'white';
                          //  document.getElementById("Tariff_Id").readOnly=false;
                               
                         }
                         else
                         {
                           
                          //  document.getElementById("Tariff_Id").style.backgroundColor = '#ececec';
                        	// document.getElementById("Tariff_Id").style.backgroundColor = 'white';
                           // document.getElementById("Tariff_Id").readOnly=false;
                           
                         }
                    
                    if((METRE_FIXED=='y')||(METRE_FIXED=='Y'))
                    {
                        document.beneficary_meter.meterfixed[0].checked=true;
                         var d=document.getElementById("prevref");
                        d.style.display="block";
                        if((BENEFICIARY_TYPE_ID==6)||(BENEFICIARY_TYPE_ID==2)||(BENEFICIARY_TYPE_ID==3)||(BENEFICIARY_TYPE_ID==4)||(BENEFICIARY_TYPE_ID==5)||(BENEFICIARY_TYPE_ID==1))
                        {
                         //    var f=document.getElementById("fixed");
                         //    f.style.display="none";
                        }
                        else
                        {
                           // var f=document.getElementById("fixed");
                          //  f.style.display="block";
                        }
                        
                    }
                    else
                    {
                        document.beneficary_meter.meterfixed[1].checked=true;
                        for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
                        {              
                            document.beneficary_meter.meterworking[i].checked=false;
                         }
                        
                        meterworking=0;
                       
                        var d=document.getElementById("prevref");
                        
                        d.style.display="none";
                        if((BENEFICIARY_TYPE_ID==6)||(BENEFICIARY_TYPE_ID==2)||(BENEFICIARY_TYPE_ID==3)||(BENEFICIARY_TYPE_ID==4)||(BENEFICIARY_TYPE_ID==5)||(BENEFICIARY_TYPE_ID==1))
                        {
                            // var f=document.getElementById("fixed");
                       //      f.style.display="none";
                        }
                    }
                    if((METRE_WORKING=='y')||(METRE_WORKING=='Y'))
                    {
                        document.beneficary_meter.meterworking[0].checked=true;
                    }
                    else
                    {
                        document.beneficary_meter.meterworking[1].checked=true;
                    
                    }
                 
                    document.getElementById('BENEFICIARY_TYPE_ID').value=BENEFICIARY_TYPE_ID;
                    document.getElementById('OTHERS_PRIVATE_SNO').value=OTHERS_PRIVATE_SNO;
                    document.getElementById('VILLAGE_PANCHAYAT_SNO').value=VILLAGE_PANCHAYAT_SNO;
                    document.getElementById('URBANLB_SNO').value=URBANLB_SNO;
                //    document.getElementById('Metre_Type').value=METRE_TYPE;
                     if(METRE_TYPE=='1')
                    {
                        document.beneficary_meter.Metre_Type[0].checked=true;
                    }
                    else
                    {
                        document.beneficary_meter.Metre_Type[1].checked=true;
                    
                    }
                    document.getElementById('Multiply_factor').value=MULTIPLY_FACTOR;
                    document.getElementById('Metre_init_reading').value=METRE_INIT_READING;
                    document.getElementById('Init_Reading_Record_date').value=INIT_READING_RECORD_DT;
                   // document.getElementById('Allotted_Qty').value=ALLOTED_QTY;
                 //   document.getElementById('Min_bill_Qty').value=MIN_BILL_QTY;
                  //  document.getElementById('Excess_Tariff_Rate').value=EXCESS_TARIFF_RATE;
                    document.getElementById('Service_Connection').value=SERVICE_CON_NO;
                    document.getElementById('Service_Connection_date').value=SEVICE_CONN_DATE;
                   
                  
                  /*  if((BENEFICIARY_TYPE_ID==6)||(BENEFICIARY_TYPE_ID==2)||(BENEFICIARY_TYPE_ID==3)||(BENEFICIARY_TYPE_ID==4)||(BENEFICIARY_TYPE_ID==5)||(BENEFICIARY_TYPE_ID==1))
                    {	
	                    if((ALLOTED_FLG=="n")||(ALLOTED_FLG=="N"))
	                    {
	                        document.beneficary_meter.Applicableval[1].checked=true;
	                  //      var d=document.getElementById("fixed");
	                   //     d.style.display="none";
	                      
	                    }
                    }*/
                    if(PARENT_METRE=='y')
                    {
                        document.beneficary_meter.parentmetre[0].checked=true;
                    }
                    else if(PARENT_METRE=='n')
                    {
                        document.beneficary_meter.parentmetre[1].checked=true;
                    
                    }
                    else
                    {
                    	document.beneficary_meter.parentmetre[0].checked=false;
                    	document.beneficary_meter.parentmetre[1].checked=false;
                    }
                   /* if(TARIFF_SLAB_FLG=='y')
                    {
                        document.beneficary_meter.Applicableslabval[0].checked=true;
                    }
                    else if(TARIFF_SLAB_FLG=='n')
                    {
                        document.beneficary_meter.Applicableslabval[1].checked=true;
                    
                    }
                    else
                    {
                    	document.beneficary_meter.Applicableslabval[0].checked=false;
                    	document.beneficary_meter.Applicableslabval[1].checked=false;
                    }*/
                  //  alert("TARIFF_MODE"+TARIFF_MODE)
                   /* if(TARIFF_MODE=='L')
                    {
                    	document.getElementById("mode_of_tariff").innerHTML= "Location Slabwise";
                    }
                    else if(TARIFF_MODE=='S')
                    {
                    	document.getElementById("mode_of_tariff").innerHTML= "Scheme Slabwise";
                    }
                    else if(TARIFF_MODE=='U')
                    {
                    	document.getElementById("mode_of_tariff").innerHTML= "Uniform";
                    }*/
                     
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
           
        }
    }
}
function refresh()
{
    document.getElementById("cmdadd").style.display='block';
    document.getElementById("cmdupdate").style.display='block';
    document.getElementById("cmddelete").style.display='block';
    document.getElementById("cmdadd").style.display='inline';
    document.getElementById("cmdupdate").style.display='inline';
    document.getElementById("cmddelete").style.display='inline';
    document.getElementById("Meter_Sno").value="";
    document.getElementById("Beneficiary_type").value="";
    document.getElementById("Beneficiary_Name").value="";
   // document.getElementById("mode_of_tariff").innerHTML="";
    document.getElementById("Habitation_Name").value="";
    document.getElementById("Beneficiary_Name").value=0;
    document.getElementById("Consumption_Category").value="";
 //   document.getElementById("Multi_WS_Category").value="";
   // document.getElementById("Tariff_Id").value=1;
    document.getElementById("SubDivision").value="";
    document.getElementById("Schemes").value="";
    document.getElementById("Metre_Location").value="";
    //document.getElementById("meterfixed").value="y";
   // document.getElementById("meterworking").value="";
   document.getElementById("Metre_Location").value="";
   document.getElementById("district_Name").value="";
   document.getElementById("block_Name").value="";
   for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
    {
        document.beneficary_meter.meterworking[i].checked=false;
    }
 for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
 {
      document.beneficary_meter.meterfixed[i].checked=false;
 }    
  //  document.getElementById("Metre_Type").value="";
   for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
 {
      document.beneficary_meter.Metre_Type[i].checked=false;
 }  
    document.getElementById("Multiply_factor").value=1;
    document.getElementById("Metre_init_reading").value="";
    document.getElementById("Init_Reading_Record_date").value="";
    //document.getElementById("Allotted_Qty").value="";
  //  document.getElementById("Min_bill_Qty").value="";
 //   document.getElementById("Excess_Tariff_Rate").value=1;
    document.beneficary_meter.parentmetre[1].checked=true;
    document.getElementById("Service_Connection").value="";
    document.getElementById("Service_Connection_date").value="";
    
   // document.getElementById("Tariff_Id_val").value="";
    document.getElementById("SCH_TYPE_ID").value="";
    document.getElementById("BENEFICIARY_TYPE_ID").value="";
    document.getElementById("OTHERS_PRIVATE_SNO").value="";
    document.getElementById("VILLAGE_PANCHAYAT_SNO").value="";
    document.getElementById("URBANLB_SNO").value="";
    
    var tb=document.getElementById("getvaluerows");
    var t=tb.rows.length  
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
    
    
  
}
function exitwindow()
{
    window.close();
}

/*function meterdisplay(checkval)
{
    if(checkval=="yes")
    {
      var d=document.getElementById("prevref");
      d.style.display="block";
    }
    else
    {
        var d=document.getElementById("prevref");
        d.style.display="none";
    }
    
}*/


/*function clearval()
{
   
     document.beneficary_meter.Beneficiary_Name.length=1;
}*/
function subdivision()
{
  //  var Multi_WS_Category=document.getElementById('Multi_WS_Category').value; 
    var subdivisionid=document.getElementById('SubDivision').value;
     
   
        var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=subdivisiondis&subdivisionid="+subdivisionid;
      //  alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedsubdivisiondis(xmlhttp);
            }
    xmlhttp.send(null);
    
  
}

function stateChangedsubdivisiondis(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="subdivisiondis")
            {
                if(flagres=='success')
                {
                    
                   
                         var OFFICE_ID=baseres.getElementsByTagName("OFFICE_ID")[0].firstChild.nodeValue;
                         var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;
                         document.getElementById('Metre_Location').value=OFFICE_NAME;
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
function Schemesval()
{
   // var Multi_WS_Category=document.getElementById('Multi_WS_Category').value; 
    var Schemesid=document.getElementById('Schemes').value;
   //alert(Multi_WS_Category);
  // alert(Schemes);
      
   
        var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=Schemesval&Schemesid="+Schemesid;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedschemedis(xmlhttp);
            }
        xmlhttp.send(null);
    
    
       
   
}
function stateChangedschemedis(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        if(xmlhttp.status==200)
        {
             if(commandres=="Schemesval")
            {
                if(flagres=='success')
                {
                    
                   
                         var SCH_SNO=baseres.getElementsByTagName("SCH_SNO")[0].firstChild.nodeValue;
                         var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                         var SCH_TYPE_ID=baseres.getElementsByTagName("SCH_TYPE_ID")[0].firstChild.nodeValue;
                         document.getElementById('Metre_Location').value=SCH_NAME;
                         document.getElementById('SCH_TYPE_ID').value=SCH_TYPE_ID;
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
    
        document.getElementById("Metre_Location").value="";
        document.getElementById("Metre_Location").style.backgroundColor = 'white';
        document.getElementById("Metre_Location").readOnly=false;
    
   /* else
    {
        document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
        document.getElementById("Metre_Location").readOnly=true;
    }*/
    
}   

function validate()
{
	
    
    
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
  //  var Tariff_Id=document.getElementById("Tariff_Id").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Metre_Location=document.getElementById("Metre_Location").value;
    var meterfixed=document.getElementById("meterfixed").value;
	var meterworking=document.getElementById("meterworking").value;
    var Metre_init_reading=document.getElementById("Metre_init_reading").value;
 //   var Excess_Tariff_Rate=document.getElementById("Excess_Tariff_Rate").value;
  //  alert(Applicable_slab_value);
   // var Allotted_Qty=document.getElementById("Allotted_Qty").value;
    var Metre_Type=document.getElementById("Metre_Type").value;
	 flagvar=0;
	for( i = 0; i < document.beneficary_meter.meterworking.length; i++ )
         {
             if(document.beneficary_meter.meterworking[i].checked)
             {
                 flagvar=1;
                 meterworking = document.beneficary_meter.meterworking[i].value;
                 //alert(meterworking);
                 
   
            }
            
        }
		
		flag=0;
	for( i = 0; i < document.beneficary_meter.meterfixed.length; i++ )
         {
             if(document.beneficary_meter.meterfixed[i].checked)
             {
                 flag=1;
                 meterfixed = document.beneficary_meter.meterfixed[i].value;
   
            }
            
        }
	for( i = 0; i < document.beneficary_meter.Metre_Type.length; i++ )
         {
             if(document.beneficary_meter.Metre_Type[i].checked)
             {
            	 flagmetre=1;
                 Metre_Type = document.beneficary_meter.Metre_Type[i].value;
               // alert(Metre_Type);
                // alert(flagmetre);
   
            }
            
        }	
	for( i = 0; i < document.beneficary_meter.parentmetre.length; i++ )
    {
        if(document.beneficary_meter.parentmetre[i].checked)
        {
        	parentmetre_val=1;
            parentmetre_value = document.beneficary_meter.parentmetre[i].value;
          //  alert(parentmetre_value);
            

       }
       
   }
/*	for( i = 0; i < document.beneficary_meter.Applicableval.length; i++ )
    {
        if(document.beneficary_meter.Applicableval[i].checked)
        {
        	flagApplicable=1;
        	Applicableval = document.beneficary_meter.Applicableval[i].value;
       //  alert(Applicableval);
            

       }
       
   }*/
	
	   /* for( i = 0; i < document.beneficary_meter.Applicableslabval.length; i++ )
	         {
	             if(document.beneficary_meter.Applicableslabval[i].checked)
	             {
	            	 Applicable_slab_value=1;
	                 Applicableslab_value = document.beneficary_meter.Applicableslabval[i].value;
	              //  alert("asas"+Applicableslab_value);
	                 
	   
	            }
	            
	        }*/
    if(Beneficiary_type=="")
    {
        alert("Enter Beneficiary Type");
        return false;
    }	
     if(Beneficiary_Name=="")
    {
        alert("Enter Beneficiary Name");
        return false;
    }
     if(SubDivision=="")
    {
        alert("Select Sub Division");
        return false;
    }
    if(Schemes=="")
    {
        alert("Select Schemes");
        return false;
    }
   
    
    
   
  if(Metre_Location=="")
    {
        alert("Enter Location");
        return false;
    }
    /*if(Tariff_Id=="")
    {
        alert("Enter Tariff Rate");
        return false;
    }*/
	
   if(flag==0)
   {
        alert("Select Metre Fixed");
        return false;
    }
   if(meterfixed=="y")
    {
        if(flagvar==0)
        {
            alert("Select Metre Working");
            return false;
        }
        if(flagmetre==0)
        {	
     	   alert("Enter Meter Type");
            return false;
        }
      } 
   	if(parseInt(document.beneficary_meter.Multiply_factor.value)==0)
	{
		alert("Multiplying Factor cannot be zero");
		 return false;
	}
    if((meterfixed=="y")&&(meterworking=="y"))
    {   
           
            if(Metre_init_reading=="")
            {
                  alert("Enter Meter Initial Reading");
                  return false;
            }
           
      }
    /* if(parseInt(document.beneficary_meter.Metre_init_reading.value)==0)
	{
		alert("Enter Meter initial reading from 1 to...");
		 return false;
	} */
       if(parentmetre_val==0)
       {
            alert("Select Parent Metre");
            return false;
        }
       
      /* if(Applicableval=="y")
       {
    	   if(Allotted_Qty=="")
           {
                 alert("Enter Allotted_Qty");
                 return false;
           }
    	 
        }*/
     /*  if(Applicable_slab_value==0)
       {	
    	   alert("Select Tariff Slab rate");
           return false;
    	   
       }*/
       
 /*if((Beneficiary_type!=6)&&(Beneficiary_type!=2)&&(Beneficiary_type!=3)&&(Beneficiary_type!=4)&&(Beneficiary_type!=5)&&(Beneficiary_type!=1))
    {
       
       if(Excess_Tariff_Rate=="")
        {
           // alert("Enter Excess_Tariff_Rate");
            //return false;
            return true;
        }
        else
        {
            return true;
        }
        
    }*/
    else
    {
        return true;
    }
}
function loadhabitationlist()
{
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    if(Beneficiary_type==6)
    {
    var hablist=document.getElementById('Beneficiary_Name').value; 
  
    var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadhabitationlist&hablist="+hablist;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadhabitationlist(xmlhttp);
            }
        xmlhttp.send(null);
        }
         
}
function stateChangedloadhabitationlist(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.beneficary_meter.Habitation_Name.length=1;
        HAB_SNO_len=baseres.getElementsByTagName("HAB_SNO").length;
        if(xmlhttp.status==200)
        {
        	 
             if(commandres=="loadhabitationlist")
            {
                if(flagres=='success')
                {
                     
                    for(var i=0;i<HAB_SNO_len;i++)
                     {
                       
                   
                         var HAB_SNO=baseres.getElementsByTagName("HAB_SNO")[i].firstChild.nodeValue;
                         var HAB_NAME=baseres.getElementsByTagName("HAB_NAME")[i].firstChild.nodeValue;
                         var var_HAB_NAME=HAB_NAME.replace ("*","&");                         
                         addOptionloadhabitationlist(document.beneficary_meter.Habitation_Name,var_HAB_NAME,HAB_SNO);
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
function addOptionloadhabitationlist(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}


function loadhabname()
{
    var habnameval=document.getElementById("Beneficiary_type").value;
    if(habnameval==6)
    {
        var bentypeval=document.getElementById("Habitation_Name").value;
     //   alert(bentypeval);
        var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadhabname&bentypeval="+bentypeval;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadhabname(xmlhttp);
            }
        xmlhttp.send(null);
    }
    
}
function stateChangedloadhabname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
        if(xmlhttp.status==200)
        {
             if(commandres=="loadhabname")
            {
                if(flagres=='success')
                {
                    
                   
                         var HAB_SNO=baseres.getElementsByTagName("HAB_SNO")[0].firstChild.nodeValue;
                         var HAB_NAME=baseres.getElementsByTagName("HAB_NAME")[0].firstChild.nodeValue;
                         HAB_NAME=HAB_NAME.replace ("*","&");        
                         document.getElementById("Metre_Location_div").style.display='block';
                      //  document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                        document.getElementById("Metre_Location").style.backgroundColor = 'white';
                        document.getElementById("Metre_Location").readOnly=false;
                        document.getElementById("Metre_Location").value=HAB_NAME;
                }
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
/* function loadschname()
{
    var BENEFICIARY_TYPE=document.getElementById('Beneficiary_type').value;
    if((BENEFICIARY_TYPE==5)||(BENEFICIARY_TYPE==4)||(BENEFICIARY_TYPE==3)||(BENEFICIARY_TYPE==2)||(BENEFICIARY_TYPE==1))
    {
    schvalue=document.getElementById('Schemes').value;
   // alert(schvalue);
    // alert(BENEFICIARY_TYPE);
     var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadschname&schvalue="+schvalue;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadschname(xmlhttp);
            }
        xmlhttp.send(null);
    }
   
    else
    
    {
     
        document.getElementById("Metre_Location").value="";
        document.getElementById("Metre_Location").style.backgroundColor = 'white';
        document.getElementById("Metre_Location").readOnly=false;
    }
     
    }


function stateChangedloadschname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
        if(xmlhttp.status==200)
        {  
             if(commandres=="loadschname")
            {
                if(flagres=='success')
                {
                    
                   
                        var SCH_SNO=baseres.getElementsByTagName("SCH_SNO")[0].firstChild.nodeValue;
                        var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                          SCH_NAME=SCH_NAME.replace ('*','&');  
                        var SCH_TYPE_ID=baseres.getElementsByTagName("SCH_TYPE_ID")[0].firstChild.nodeValue;
                        document.getElementById("Metre_Location").style.backgroundColor = '#ececec';
                        document.getElementById("Metre_Location").readOnly=false;   
                        document.getElementById("Metre_Location").value=SCH_NAME;
                        //alert(SCH_TYPE_ID);
                        document.getElementById("SCH_TYPE_ID").value=SCH_TYPE_ID;
                }  
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
*/
function loadschname()
{
    var BENEFICIARY_TYPE=document.getElementById('Beneficiary_type').value;
   /* if((BENEFICIARY_TYPE==5)||(BENEFICIARY_TYPE==4)||(BENEFICIARY_TYPE==3)||(BENEFICIARY_TYPE==2)||(BENEFICIARY_TYPE==1))
    {*/
    schvalue=document.getElementById('Schemes').value;
   // alert(schvalue);
    // alert(BENEFICIARY_TYPE);
     var xmlhttp=GetXmlHttpObject();
        if (xmlhttp==null)
        {
             alert ("Your browser does not support AJAX!");
            return;
        }
        url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadschname&schvalue="+schvalue;
       // alert(url);
        url=url+"&sid="+Math.random();
        xmlhttp.open("GET",url,true);
        xmlhttp.onreadystatechange= function()
            {
                 stateChangedloadschname(xmlhttp);
            }
        xmlhttp.send(null);
  // }
   
  
     
    }


function stateChangedloadschname(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        
        if(xmlhttp.status==200)
        {
             if(commandres=="loadschname")
            {
                if(flagres=='success')
                {  
                    
                   
                        var SCH_SNO=baseres.getElementsByTagName("SCH_SNO")[0].firstChild.nodeValue;
                        var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[0].firstChild.nodeValue;
                        var SCH_TYPE_ID=baseres.getElementsByTagName("SCH_TYPE_ID")[0].firstChild.nodeValue;
                        
                        document.getElementById("SCH_TYPE_ID").value=SCH_TYPE_ID;
                        var Categoryid=document.getElementById("Consumption_Categoryid").value;
                        var bentypeid=document.getElementById("Beneficiary_type").value;
                        var schemeid=document.getElementById("Schemes").value;
                        var SubDivisionid=document.getElementById("SubDivision").value;
                     /*   if((Categoryid==0)&&(bentypeid<6))
                        {
                              // alert(SCH_NAME);
                                document.getElementById("Metre_Location").value=SCH_NAME;
                        }*/
                      /*  else
                        {
                              document.getElementById("Metre_Location").value="";
                        }*/
                        
                }
                 else
                {
                    alert("Not Loaded");
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
            if (unicode<45||unicode>57||unicode==47||unicode==45) 
                return false ;
        }
    }

function getvaluegrid()
{
     var xmlhttp=createObject();
   
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    url="../../../../../Pms_Dcb_Beneficiary_metre?command=get&Beneficiary_type="+Beneficiary_type+"&Beneficiary_Name="+Beneficiary_Name;
     
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    var tb=document.getElementById("getvaluerows");
    var t=tb.rows.length;
    for(var i=t-1;i>=0;i--)
    {
          tb.deleteRow(i);
    } 
    xmlhttp.onreadystatechange=function()  
            {    
                 stateChangedgrid(xmlhttp);
            }
    xmlhttp.send(null);
}

function stateChangedgrid(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    
     
    if(xmlhttp.readyState==4)
    {
    	 
        if(xmlhttp.status==200)
        {   
        	  
        	try
        	{
        	  try
        	  {
        		  baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        	  }catch(e1) {}
        	  
        		 
        		commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        		 
        		recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;
        		 
        		flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        		 
        	}
        	catch(e)
        	{
        	 
        	}
            seq=0;
            if(commandres=="get")
            {
            	
                 if(flagres=='success')
                {
                    if(recordfound==1)
                    {
                    tablebody=document.getElementById("getvaluerows");
                    var len=baseres.getElementsByTagName("METRE_SNO").length;
                    countsno=baseres.getElementsByTagName("countsno")[0].firstChild.nodeValue;
                
                    
                    msgload(countsno+" Meter Locations Data Already Exists", 1)
                  
                    for(j=0;j<len;j++)
                    {
                       // alert(j);
                     var METRE_SNO=baseres.getElementsByTagName("METRE_SNO")[j].firstChild.nodeValue;
                    var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[j].firstChild.nodeValue;
                
                    var officename=baseres.getElementsByTagName("officename")[j].firstChild.nodeValue;
                var BULKWS_CATEGORY=baseres.getElementsByTagName("BULKWS_CATEGORY")[j].firstChild.nodeValue;
                    var SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[j].firstChild.nodeValue;
                    SCH_NAME=SCH_NAME.replace ('*','&');  
                 //   var HABITATION_SNO=baseres.getElementsByTagName("HABITATION_SNO")[i].firstChild.nodeValue;
                    //var TARIFF_RATE=baseres.getElementsByTagName("TARIFF_RATE")[j].firstChild.nodeValue;
                 //   var METRE_WORKING=baseres.getElementsByTagName("METRE_WORKING")[i].firstChild.nodeValue;
                    var METRE_FIXED=baseres.getElementsByTagName("METRE_FIXED")[j].firstChild.nodeValue;
                    var METRE_LOCATION=baseres.getElementsByTagName("METRE_LOCATION")[j].firstChild.nodeValue;
                    METRE_LOCATION=METRE_LOCATION.replace ("*","&"); 
                    var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[j].firstChild.nodeValue;
                     var meterworking=baseres.getElementsByTagName("meterworking")[j].firstChild.nodeValue;
                    var BEN_TYPE_ID=baseres.getElementsByTagName("BEN_TYPE_ID")[j].firstChild.nodeValue;
                   // var TARIFF_MODE=baseres.getElementsByTagName("TARIFF_MODE")[j].firstChild.nodeValue;
                    var rowvalue=document.createElement("TR");
                    rowvalue.id=METRE_SNO;
                    var tabledata=document.createElement("TD");
                    var anc=document.createElement("A");
                    var url="javascript:loadvaluesfromtable('"+METRE_SNO+"')";
                    var nameval=document.createTextNode("Edit");
                    anc.href=url;
                    anc.appendChild(nameval);
                    tabledata.appendChild(anc);
                    
                    
                  var hiddentext1=document.createElement("input");
                     hiddentext1.type="hidden";
                     hiddentext1.name="qual1";
                     hiddentext1.id="qual1";
                     hiddentext1.text="qual1";
                     hiddentext1.value=METRE_SNO;
                     tabledata.appendChild(hiddentext1);
                     
                    rowvalue.appendChild(tabledata);
                    seq++;
                    var sno=document.createElement("TD");
                    var i=document.createTextNode(seq);
                    sno.appendChild(i);
                    rowvalue.appendChild(sno);
                     
               /*     var tabledata8=document.createElement("TD");
                    var BEN_TYPE_DESC=document.createTextNode(BEN_TYPE_DESC);
                    tabledata8.appendChild(BEN_TYPE_DESC);
                    rowvalue.appendChild(tabledata8);
                    
                    var tabledata2=document.createElement("TD");
                    var BENEFICIARY_NAME=document.createTextNode(BENEFICIARY_NAME);
                    tabledata2.appendChild(BENEFICIARY_NAME);
                    rowvalue.appendChild(tabledata2);*/
                /*   if(BULKWS_CATEGORY==1)
                    {
                        document.getElementById("meterlabel").style.display='block';
                         document.getElementById("meterlabel").innerHTML='Meter Location';
                         
                    }
                    
                    if(BEN_TYPE_ID==6)
                    {
                        document.getElementById("meterlabel").style.display='block';
                         document.getElementById("meterlabel").innerHTML='Habitation name';
                         
                    }*/
                    
                    
                 //   document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                     
                    var tabledata3=document.createElement("TD");
                    var officename=document.createTextNode(officename);
                    tabledata3.appendChild(officename);
                    rowvalue.appendChild(tabledata3);
                    
                     var tabledata4=document.createElement("TD");
                    var SCH_NAME=document.createTextNode(SCH_NAME);
                    tabledata4.appendChild(SCH_NAME);
                    rowvalue.appendChild(tabledata4);
                    
                    
                   /* if(TARIFF_MODE=='U')
                    {
                  
                    var tabledata7=document.createElement("TD");
                    var TARIFF_RATE=document.createTextNode(TARIFF_RATE);
                    tabledata7.appendChild(TARIFF_RATE);
                    rowvalue.appendChild(tabledata7);
                    }*/
                     var tabledata5=document.createElement("TD");
                   
                    if((METRE_FIXED=='y')||(METRE_FIXED=='Y'))
                    {
                        METRE_FIXED='Yes';
                        var METRE_FIXED=document.createTextNode(METRE_FIXED);
                    }
                    else
                     {
                        METRE_FIXED='No';
                        var METRE_FIXED=document.createTextNode(METRE_FIXED);
                    }
                    tabledata5.appendChild(METRE_FIXED);
                    rowvalue.appendChild(tabledata5);
                      var meterworking_val=document.createElement("TD");
                    if((meterworking=='y')||(meterworking=='Y'))
                    {
                        meterworking='Yes';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    else
                     {
                        meterworking='No';
                        var meterworking=document.createTextNode(meterworking);
                    }
                    meterworking_val.appendChild(meterworking);
                    rowvalue.appendChild(meterworking_val);
                    
               
                    
                /*    if(BULKWS_CATEGORY==1)
                    {
                         document.getElementById("meterlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    }
                     else
                    {
                        document.getElementById("meterlabel").style.display='none';
                    }
                     if(BEN_TYPE_ID==6)
                    {
                         document.getElementById("Habitationlabel").style.display='block';
                        var METRE_LOCATION_VAL=document.createElement("TD");
                        var METRE_LOCATION=document.createTextNode(METRE_LOCATION);
                        METRE_LOCATION_VAL.appendChild(METRE_LOCATION);
                        rowvalue.appendChild(METRE_LOCATION_VAL);
                        
                    }
                    else
                    {
                        document.getElementById("Habitationlabel").style.display='none';
                    }
                    */
                  //  alert(BULKWS_CATEGORY);
                     
                      tablebody.appendChild(rowvalue);
                      //alert(len);
                     // alert(j);
                    }
                   }
                   else
                   {
                       //alert("No metre found");
                    }
                } 
                else
                {
                   alert("Not success");
                }
            }           
        }
    }
}

function habcheckdup()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Habitation_Name=document.getElementById("Habitation_Name").value;
    var Metre_Location=document.getElementById("Metre_Location").value;
    var Consumption_Category=document.getElementById("Consumption_Categoryid").value;
    if(Beneficiary_type==6)
    {
        if((Consumption_Category==0)||(Consumption_Category==1))
        {
            url="../../../../../Pms_Dcb_Beneficiary_metre?command=habcheckdup&Beneficiary_type="+Beneficiary_type+"&Consumption_Categoryid="+Consumption_Category+"&Beneficiary_Name="+Beneficiary_Name+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Habitation_Name="+Habitation_Name;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedhabcheckdup(xmlhttp);
            }
            xmlhttp.send(null);
        }
    }
        
}
function stateChangedhabcheckdup(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        { 
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="habcheckdup")
            {
                 if(flagres=='success')
                {
                    if(countinsert>0)
                    {
                       //alert("This Habitation already exists for this Scheme and Sub division");
                       msgload("This Habitation already exists for this Scheme and Sub division", 1)
                    }
                }
            }
          }  
     }
}
function schemecheck()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Consumption_Category=document.getElementById("Consumption_Categoryid").value;
   if((Beneficiary_type==1)||(Beneficiary_type==2)||(Beneficiary_type==3)||(Beneficiary_type==4)||(Beneficiary_type==5))
   {
        if(Consumption_Category==0)
        {
            url="../../../../../Pms_Dcb_Beneficiary_metre?command=schemecheck&Beneficiary_type="+Beneficiary_type+"&Consumption_Categoryid="+Consumption_Category+"&Beneficiary_Name="+Beneficiary_Name+"&SubDivision="+SubDivision+"&Schemes="+Schemes;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedschemecheck(xmlhttp);
            }
            xmlhttp.send(null);
        }
   }
        
}              
function stateChangedschemecheck(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        { //alert('sathiya');
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="schemecheck")
            {
                 if(flagres=='success')
                {
                    if(countinsert>0)
                    {
                    	 msgload("This Metre Location already exists for this Scheme and Sub division",1);
                       //alert("This Metre Location already exists for this Scheme and Sub division");
                    }
                }
            }
          }  
     }
}            
        
function metercheck()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
    var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    var SubDivision=document.getElementById("SubDivision").value;
    var Schemes=document.getElementById("Schemes").value;
    var Consumption_Category=document.getElementById("Consumption_Categoryid").value;
    var Metre_Location=document.getElementById("Metre_Location").value;
   
        if((Consumption_Category==1)||(Beneficiary_type>6))
        {
            url="../../../../../Pms_Dcb_Beneficiary_metre?command=metercheck&Beneficiary_type="+Beneficiary_type+"&Consumption_Categoryid="+Consumption_Category+"&Beneficiary_Name="+Beneficiary_Name+"&SubDivision="+SubDivision+"&Schemes="+Schemes+"&Metre_Location="+Metre_Location;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedmetercheck(xmlhttp);
            }
            xmlhttp.send(null);
        }
  
        
}             

function stateChangedmetercheck(xmlhttp)
{
    var baseres,commandres,flagres,recordres;
    
    if(xmlhttp.readyState==4)
    {
        if(xmlhttp.status==200)
        { //alert('sathiya');
            baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            countinsert=baseres.getElementsByTagName("countinsert")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(commandres=="metercheck")
            {
                 if(flagres=='success')
                {
                    if(countinsert>0)
                    {
                    	msgload("This Metre Location already exists for this Scheme and Sub division",1)
                    	//alert("This Metre Location already exists for this Scheme and Sub division");
                    }
                }
            }
          }  
     }
}   
function popup()
{
		winemp= window.open("Pms_Dcb_Beneficiary_Metre_Report.jsp","list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
		
		
        winemp.moveTo(250,250); 
        winemp.focus();
}
function districload()
{
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     var Beneficiary_type=document.getElementById("Beneficiary_type").value;
     if(Beneficiary_type==6)
     {
        document.getElementById("district").style.display="block";
         url="../../../../../Pms_Dcb_Beneficiary_metre?command=loaddistrict&Beneficiary_type="+Beneficiary_type;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedloaddistrict(xmlhttp);
            }
            xmlhttp.send(null);
     
     }
     else
     {
         document.getElementById("district").style.display="none";
     }
     
}
function stateChangedloaddistrict(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.beneficary_meter.district_Name.length=1;
        DISTRICT_CODE_len=baseres.getElementsByTagName("DISTRICT_CODE").length;
        if(xmlhttp.status==200)
        {
             if(commandres=="loaddistrict")
            {
                if(flagres=='success')
                {
                    
                    for(var i=0;i<DISTRICT_CODE_len;i++)
                     {
                       
                   
                         var DISTRICT_CODE=baseres.getElementsByTagName("DISTRICT_CODE")[i].firstChild.nodeValue;
                         var DISTRICT_NAME=baseres.getElementsByTagName("DISTRICT_NAME")[i].firstChild.nodeValue;
                         addOptiondistrictlist(document.beneficary_meter.district_Name,DISTRICT_NAME,DISTRICT_CODE);
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
function addOptiondistrictlist(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}

function loadblocks()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     var Beneficiary_type=document.getElementById("Beneficiary_type").value;
     var district_Name=document.getElementById("district_Name").value;
     if((Beneficiary_type==6)&&(district_Name!=""))
     {
        document.getElementById("district").style.display="block";
         url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadblocks&district_Name="+district_Name;
          //  alert(url);
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedloadblocks(xmlhttp);
            }
            xmlhttp.send(null);
     
     }
   

}
function stateChangedloadblocks(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
        baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
        
        
        if(baseres.getElementsByTagName("command")[0] == undefined){
			commandres = "";
		}else{
			commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
		}
		
        
        
        flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        document.beneficary_meter.block_Name.length=1;
        BLOCK_CODE_len=baseres.getElementsByTagName("BLOCK_SNO").length;
        if(xmlhttp.status==200)
        {
             if(commandres=="loadblocks")
            {
                if(flagres=='success')
                {
                    
                    for(var i=0;i<BLOCK_CODE_len;i++)
                     {
                       
                   
                         var BLOCK_SNO=baseres.getElementsByTagName("BLOCK_SNO")[i].firstChild.nodeValue;
                         var BLOCK_NAME=baseres.getElementsByTagName("BLOCK_NAME")[i].firstChild.nodeValue;
                         addOptionblocklist(document.beneficary_meter.block_Name,BLOCK_NAME,BLOCK_SNO);
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
function addOptionblocklist(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function loadbenname()
{
     var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     var Beneficiary_type=document.getElementById("Beneficiary_type").value;
     var district_Name=document.getElementById("district_Name").value;
     var block_Name=document.getElementById("block_Name").value;
     
     if((Beneficiary_type==6)&&(district_Name!="")&&(block_Name!=""))
     {
        document.getElementById("district").style.display="block";
         url="../../../../../Pms_Dcb_Beneficiary_metre?command=loadbenname&block_Name="+block_Name+"&dis="+document.getElementById("district_Name").value;
          //alert(url); 
            url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
            xmlhttp.onreadystatechange=function()
            {
                 stateChangedloadbeneficiaryname(xmlhttp);
            }
            xmlhttp.send(null);
     
     }
}

/* function fn_cmdtariff_slab()
{
	var Tariff_Id_value=document.getElementById("Tariff_Id").value;
	var Allotted_Qty_val=document.getElementById("Allotted_Qty").value;
	winemp= window.open('Pms_Dcb_Tariff_slab_rate.jsp?Tariff_Id_value='+Tariff_Id_value+'&Allotted_Qty_val='+Allotted_Qty_val,"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
		//	winemp= window.open('../../../../../BeneficiaryMetreReportPdf?command=cmdreport&reportvalue='+reportvalue+'&Beneficiary_type='+Beneficiary_type_report+'&SubDivision='+SubDivision_report+'&Beneficiary_Name='+Beneficiary_Name_report+'&divisionname='+divisionname_report+'',"list","status=1,height=500,width=600,resizable=YES, scrollbars=yes");
    winemp.moveTo(250,250); 
    winemp.focus();
}*/
function checkzero()
{
	if(parseInt(document.beneficary_meter.Multiply_factor.value)==0)
	{
		//alert("Multiplying Factor cannot be zero");
		 msgload("Multiplying Factor cannot be zero", 1);
	}

}
/*function checkzero_factor()
{
	if(parseInt(document.beneficary_meter.Metre_init_reading.value)==0)
	{
		alert("Enter Meter initial reading from 1 to...");
		 
	}
}*/
