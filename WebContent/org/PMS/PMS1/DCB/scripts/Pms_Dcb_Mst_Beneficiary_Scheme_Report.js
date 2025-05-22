/*function GetXmlHttpObject()
{
    if(window.XMLHttpRequest)
    {
         // code for IE7+, Firefox, Chrome, Opera, Safari
        return new XMLHttpRequest();
    }
    if(window.ActiveXObject)0
    {
        // code for IE6, IE5
         return new ActiveXObject("Microsoft.XMLHTTP");
    }
}*/

function divisionname()
{
    var xmlhttp=createObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     url="../../../../../Pms_Dcb_Mst_Beneficiary_Scheme_Report?command=divisionname";
     url=url+"&sid="+Math.random();
     document.getElementById("pr_status").value=0;
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange= function()
     {
    	 stateChangeddivisionname(xmlhttp);
     }
     xmlhttp.send(null);
}
function stateChangeddivisionname(xmlhttp)
{
	var baseres,commandres,flagres;
	
    if(xmlhttp.readyState==4)
    {
    	
    	   	   	
        if(xmlhttp.status==200)
        {
        	if(window.ActiveXObject){ // If IE Windows
        		baseres = new ActiveXObject("Microsoft.XMLDOM");
            	baseres.loadXML(xmlhttp.responseText);
        		} else {
                	baseres = xmlhttp.responseXML;
        		}
        	
        	baseres=baseres.getElementsByTagName("response")[0];
        	commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        	flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        	
        	try
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
                        alert("Division name is not loaded");
                    }
                }
            }
        	}
        	catch(e)
        	{
        		alert("not getting")
        	}
        }
    }
}
function loadbeneficiarytype()
{
    var xmlhttp=createObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
     url="../../../../../Pms_Dcb_Mst_Beneficiary_Scheme_Report?command=loadbeneficiarytype";
     url=url+"&sid="+Math.random();
     
     xmlhttp.open("GET",url,true);
     xmlhttp.onreadystatechange= function()
     {
          stateChangedBeneficiaryType(xmlhttp);
     }
     xmlhttp.send(null);
}
function stateChangedBeneficiaryType(xmlhttp)
{
	
    var baseres,commandres,flagres, beneficiaryId;
    if(xmlhttp.readyState==4)
    {
    	
        
        if(xmlhttp.status==200)
        {
        	if(window.ActiveXObject){ // If IE Windows
        		baseres = new ActiveXObject("Microsoft.XMLDOM");
            	baseres.loadXML(xmlhttp.responseText);
        		} else {
                	baseres = xmlhttp.responseXML;
        		}
        	baseres=baseres.getElementsByTagName("response")[0];
        	commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
        	flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        	
        	
             if(commandres=="loadbeneficiarytype")
            {
                if(flagres=='success')
                {
                	beneficiaryId=baseres.getElementsByTagName("BEN_TYPE_ID")[0].firstChild.nodeValue;
                	recordsLength=baseres.getElementsByTagName("BEN_TYPE_ID").length;
                	
                    if(beneficiaryId!=0)
                    {
                       for(i=0;i<recordsLength;i++)
                       {
                    	   var BEN_TYPE_ID=baseres.getElementsByTagName("BEN_TYPE_ID")[i].firstChild.nodeValue;
                           var BEN_TYPE_DESC=baseres.getElementsByTagName("BEN_TYPE_DESC")[i].firstChild.nodeValue;
                           var BEN_TYPE_SDESC=baseres.getElementsByTagName("BEN_TYPE_SDESC")[i].firstChild.nodeValue;
                                               
                           addOptionBeneficiary_type(document.beneficary_meter_report.Beneficiary_type,BEN_TYPE_DESC,BEN_TYPE_ID);
                       }	   
                    }
                    else
                    {
                        alert("Not loaded");
                    }
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
function loadsubdivision()
{
    
    var xmlhttp=createObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    url="../../../../../Pms_Dcb_Mst_Beneficiary_Scheme_Report?command=loadsubdivision";
  //  alert(url);
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
    {
                 stateChangedloadsubdivision(xmlhttp);
    };
    xmlhttp.send(null);
}
function stateChangedloadsubdivision(xmlhttp)
{
    
    var baseres,commandres,flagres;
    if(xmlhttp.readyState==4)
    {
    	
        if(xmlhttp.status==200)
        {
        	
        	if(window.ActiveXObject){ // If IE Windows
        		baseres = new ActiveXObject("Microsoft.XMLDOM");
            	baseres.loadXML(xmlhttp.responseText);
        		} else {
                	baseres = xmlhttp.responseXML;
        		}
        	
        	baseres=baseres.getElementsByTagName("response")[0];
            commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        	
        	if(commandres=="loadsubdivision")
            {
                if(flagres=='success')
                {
                    
                    var SUBDIVISION_OFFICE_ID_len=baseres.getElementsByTagName("SUBDIVISION_OFFICE_ID").length;
                    for(var i=0;i<SUBDIVISION_OFFICE_ID_len;i++)
                     {
                         var SUBDIVISION_OFFICE_ID=baseres.getElementsByTagName("SUBDIVISION_OFFICE_ID")[i].firstChild.nodeValue;
                         var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
                         
                         addOptiondesc(document.beneficary_meter_report.SubDivision,OFFICE_NAME,SUBDIVISION_OFFICE_ID);
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
function addOptiondesc(selectbox,text,value)
{
var optn = document.createElement("OPTION");
optn.text = text;
optn.value = value;
selectbox.options.add(optn);
}
function loadbeneficiaryname()
{
   
    var xmlhttp=createObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiarytype=document.getElementById("Beneficiary_type").value;
    //  

    url="../../../../../Pms_Dcb_Mst_Beneficiary_Scheme_Report?command=loadbeneficiaryname&beneficiaryType="+Beneficiarytype;
  //  alert(url);
 
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
    {
         stateChangedloadbeneficiaryname(xmlhttp);
    };
    xmlhttp.send(null);
}
function stateChangedloadbeneficiaryname(xmlhttp)
{
	 var baseres,commandres,flagres;
	    if(xmlhttp.readyState==4)
	    {
	    	
	        if(xmlhttp.status==200)
	        {
	        	
	        	if(window.ActiveXObject){ // If IE Windows
	        		baseres = new ActiveXObject("Microsoft.XMLDOM");
	            	baseres.loadXML(xmlhttp.responseText);
	        		} else {
	                	baseres = xmlhttp.responseXML;
	        		}
	        	
	        	baseres=baseres.getElementsByTagName("response")[0];
	        	//alert("baseres111"+baseres)
	        	commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
	        //	alert("baseres22222"+baseres)
	            flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
	        //	alert("baseres33333"+baseres)
	                       
	            
      document.beneficary_meter_report.Beneficiary_Name.length=1;
      
        	
             if(commandres=="loadbeneficiaryname")
            {
            	// alert("loadbeneficiaryname")
                if(flagres=='success')
                {
              //      alert("success");
                    
                    
                    var BENEFICIARY_SNO_len=baseres.getElementsByTagName("BENEFICIARY_SNO").length;
                  //  alert(beneficiary_sno_len);
                    for(var i=0;i<BENEFICIARY_SNO_len;i++)
                     {
                         var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                        
                         addOptionbeneficiaryname(document.beneficary_meter_report.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
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

function loadReport()
{
	var xmlhttp=createObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var Beneficiarytype=document.getElementById("Beneficiary_type").value;
    var subDivision= document.getElementById("SubDivision").value;
    var beneficiaryName= document.getElementById("Beneficiary_Name").value;
    //alert("subdivision:"+subDivision);
    
    url="../../../../../Pms_Dcb_Mst_Beneficiary_Scheme_Report?command=loadReport" +
    		"&beneficiaryType="+Beneficiarytype+
    		"&subDivision="+subDivision+"&beneficiaryName="+beneficiaryName;
    
    
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
    {
    	stateChangedLoadReport(xmlhttp);
    };
    xmlhttp.send(null);

}

function stateChangedLoadReport(xmlhttp)
{
    var baseres,commandres,flagres="success";
    if(xmlhttp.readyState==4)
    {
    	//alert(xmlhttp.responseText);
    	
        var tbody=document.getElementById("getvaluerows");
        var t=0;	
   	   for(t=tbody.rows.length-1;t>=0;t--)
   	   {
   		   tbody.deleteRow(0);
   	   }
  	   if(xmlhttp.status==200)
       {
  		 if(window.ActiveXObject){ // If IE Windows
     		baseres = new ActiveXObject("Microsoft.XMLDOM");
         	baseres.loadXML(xmlhttp.responseText);
     		} else {
             	baseres = xmlhttp.responseXML;
     		}
  		document.getElementById("pr_status").value=1;   
  		baseres=baseres.getElementsByTagName("response")[0];
        commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
       // flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
        //document.beneficary_meter_report.Beneficiary_Name.length=1;
  		 
             if(commandres=="loadReport")
            { 
                if(flagres=='success')
                {
              
                 var BENEFICIARY_SNO_len=baseres.getElementsByTagName("SchemeSno").length;
                 //alert(beneficiary_sno_len);
                 var temp=0;
                 var c=0;
                 for(var i=0;i<BENEFICIARY_SNO_len;i++)
                 {
                	 var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                	 var new_row=cell("TR","","","row",1,2,"","","","","","","");
                	 var BeneficiaryName=baseres.getElementsByTagName("BeneficiaryName")[i].firstChild.nodeValue;
                	 var SchemeName=baseres.getElementsByTagName("SchemeName")[i].firstChild.nodeValue;
                	 var SchemeType=baseres.getElementsByTagName("SchemeType")[i].firstChild.nodeValue;
                	 var MeterLocationCount=baseres.getElementsByTagName("MeterLocation")[i].firstChild.nodeValue;
                	 var SchemeSno=baseres.getElementsByTagName("SchemeSno")[i].firstChild.nodeValue;
                	 var ben_type=baseres.getElementsByTagName("ben_type_desc")[i].firstChild.nodeValue;
                	 var sub_div=baseres.getElementsByTagName("sub_div")[i].firstChild.nodeValue;
                	 var postUrl="../jsps/pmsDcbBeneficiaryMetreReport.jsp?BeneficiarySno="+BENEFICIARY_SNO;
                	 	 // postUrl+="&BeneficiaryName="+BeneficiaryName+"&SchemeName="+SchemeName;	
                	 	 postUrl+="&SchemeSno="+SchemeSno;       
                	 	 
                	 var cell2=cell("TD","label","","",BeneficiaryName,2,"","","","","","","");
                	 var cell_ben_type=cell("TD","label","","",ben_type,2,"","","","","","","");
                	 var cell_sub_div=cell("TD","label","","",sub_div,2,"","","","","","","");
                	 
                	 var cell3=cell("TD","A","Edit","Edit",SchemeName,4,"",postUrl,"","","","","");
                     
                     var cell4=cell("TD","label","","",SchemeType,2,"","","","","","","");
                     
                     //var cell5=cell("TD","a","","",MeterLocationCount,7,"","","","","","","");
                     var cell5=cell("TD","label","","",MeterLocationCount,2,"","","","","right","","");
                     
                     var TariffMode=baseres.getElementsByTagName("TariffMode")[i].firstChild.nodeValue;
                     if(TariffMode !="-")
                     {	 
                    	 TariffMode=(TariffMode=="L")?"Location":"Scheme";
                    	 //tariffColor=(TariffMode=="Location")?"blue":"black";
                     } 	 
                     var cell6=cell("TD","label","","",TariffMode,3,"","","","","","","");
                     
                     
                     var empty=cell("TD","label","","","",3,"","","","","","","");
                  
                     
                     
                     if (parseInt(temp)!=parseInt(BENEFICIARY_SNO))
                     {
                    	 c=c+1;
                    	 var cell1=cell("TD","label","","",c,3,"","","","","right","","");
                    	 new_row.appendChild(cell1);
                    	 new_row.appendChild(cell2);
                     }
                     else
                     {
                    	 c=c+1;
                    	 var cell1=cell("TD","label","","",c,3,"","","","","","","");
                    	 new_row.appendChild(cell1);
                    	 new_row.appendChild(empty);
                     }
                     
                     new_row.appendChild(cell_ben_type);
                     new_row.appendChild(cell_sub_div);
                     new_row.appendChild(cell3);
                     new_row.appendChild(cell4);
                     new_row.appendChild(cell5);
                     new_row.appendChild(cell6);
                     tbody.appendChild(new_row);
                     temp=BENEFICIARY_SNO;
                 }
                 
                 
                 
                }
                 else
                {
                    
                }
            }
           
        }
    }
}


function reportPdf()
{
	 	var Beneficiarytype=document.getElementById("Beneficiary_type").value;
	    var subDivision= document.getElementById("SubDivision").value;
	    var beneficiaryName= document.getElementById("Beneficiary_Name").value;
	    window.open("../../../../../Pms_Dcb_Mst_Beneficiary_Scheme_Report?command=pdf&beneficiaryType="+
	    		Beneficiarytype+"&subDivision="+subDivision+"&beneficiaryName="+beneficiaryName);
}

function exitwindow()
{
	window.close();
}