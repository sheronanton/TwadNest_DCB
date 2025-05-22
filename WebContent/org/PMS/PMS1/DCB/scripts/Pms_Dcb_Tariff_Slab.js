
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

function rateshow(command)
{
	var x=GetXmlHttpObject();
	var ben=document.getElementById("Beneficiary_Name").value;
	var rate=document.getElementById("rate").value;
	var url="./Int_Show.jsp?command="+command+"&ben="+ben+"&rate="+rate;
    x.open("GET",url,true);
    x.onreadystatechange= function()
            {
    	rateshow_result(x,command);
            }
    x.send(null);
	
	
}
function rateshow_result(xmlobj,command)
{
	
if (xmlobj.readyState==4)
 {
	 if(xmlobj.status==200)
     {
		 
		 var results=xmlobj.responseText.split("|");
			var res = results[1];
			document.getElementById("rate").value=res;
		 if (command=="update")
		 {
			   alert(" Interest Rate Successfully Updated...");
		 }
     }
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
    var url="../../../../../Pms_Dcb_Tariff_Flag?command=loadbeneficiarytype";
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
                         addOptionBeneficiary_type(document.tariff_flag.Beneficiary_type,BEN_TYPE_DESC,BEN_TYPE_ID);
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
function loadsubdivision()
{
    
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
    var url="../../../../../Pms_Dcb_Tariff_Flag?command=loadsubdivision";
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
                    for(var i=0;i<SUBDIVISION_OFFICE_ID_len;i++)
                     {
                         var SUBDIVISION_OFFICE_ID=baseres.getElementsByTagName("SUBDIVISION_OFFICE_ID")[i].firstChild.nodeValue;
                         
                         var OFFICE_NAME=baseres.getElementsByTagName("OFFICE_NAME")[i].firstChild.nodeValue;
                         addOptiondesc(document.tariff_flag.SubDivision,OFFICE_NAME,SUBDIVISION_OFFICE_ID);
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
   
    var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_type=document.getElementById("Beneficiary_type").value;
    //  var SubDivision=document.getElementById("SubDivision").value;
    // alert(Beneficiary_Name);
   // url="../../../../../Pms_Dcb_Tariff_Flag?command=loadbeneficiaryname&Beneficiary_type="+Beneficiary_type+"&SubDivision="+SubDivision;
      
    url="../../../../../Pms_Dcb_Tariff_Flag?command=loadbeneficiaryname&Beneficiary_type="+Beneficiary_type;
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
        document.tariff_flag.Beneficiary_Name.length=1;
        if(xmlhttp.status==200)
        {
             if((commandres=="loadbeneficiaryname")||(commandres=="loadbenname"))
            {
                if(flagres=='success')
                {
                 //   alert("success");
                    
                    
                    var BENEFICIARY_SNO_len=baseres.getElementsByTagName("BENEFICIARY_SNO").length;
                    //alert(beneficiary_sno_len);
                    for(var i=0;i<BENEFICIARY_SNO_len;i++)
                     {
                         var BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
                         var BENEFICIARY_NAME=baseres.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
                     
                         addOptionbeneficiaryname(document.tariff_flag.Beneficiary_Name,BENEFICIARY_NAME,BENEFICIARY_SNO);
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
function loadschemes()
{
	var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
     {
         alert ("Your browser does not support AJAX!");
         return;
     }
      var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
      document.getElementById("pr_status").value=0;
     
      
   if (Beneficiary_Name!="0")
   {
	   
    url="../../../../../Pms_Dcb_Tariff_Flag?command=loadschemes&Beneficiary_Name="+Beneficiary_Name;
    url=url+"&sid="+Math.random();
   
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                stateChangedloadschemes(xmlhttp);
            }
    xmlhttp.send(null);
   }else
   {
	   alert(" Select Beneficiary Name ! ! ")
	   document.getElementById("pr_status").value=1;
   }
}
function stateChangedloadschemes(xmlhttp)
{
   
    if(xmlhttp.readyState==4)
    {
    	if(xmlhttp.status==200)
		{
    		var rid=0;
    	    var baseres,commandres,flagres,recordfound;
    	    baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
    		commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
    		flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
    		recordfound=baseres.getElementsByTagName("recordfound")[0].firstChild.nodeValue;
    		tbodyclear=document.getElementById("valuerows");
    		ben_loc_flag_body_clear=document.getElementById("ben_loc_flag");
    		try
    	    {
    			
    			
    			tbodyclear.innerHTML="";
    		}
    	    catch(e) 
    	    {
    	    	tbodyclear.innerText="";
    	    }
    	    try
    	    {
    			
    			
    	    	ben_loc_flag_body_clear.innerHTML="";
    		}
    	    catch(e) 
    	    {
    	    	ben_loc_flag_body_clear.innerText="";
    	    }
    	if(commandres=="loadschemes")
        {
    		if(flagres=='success')
            {
    			if(recordfound==1)
    			{
    			document.getElementById("pr_status").value=1;;
    			
    			
    			 var BENEFICIARY_SNO_len=baseres.getElementsByTagName("BENEFICIARY_SNO").length;
    			
    			
    				
	    				tbody=document.getElementById("valuerows");
	    				
	    				// 		mycurrent_row.id=aid;
	    				// 	rid=mycurrent_row.id;
	    				for(var i=0;i<BENEFICIARY_SNO_len;i++)
	    				{
	    					
	    					BENEFICIARY_SNO=baseres.getElementsByTagName("BENEFICIARY_SNO")[i].firstChild.nodeValue;
	    					SCH_NAME=baseres.getElementsByTagName("SCH_NAME")[i].firstChild.nodeValue;
	    					SCHEME_SNO=baseres.getElementsByTagName("SCHEME_SNO")[i].firstChild.nodeValue;
	    					SETTING_FLAG=baseres.getElementsByTagName("SETTING_FLAG")[i].firstChild.nodeValue;
	    					
	    					TARIFF_FLAG=baseres.getElementsByTagName("TARIFF_FLAG")[i].firstChild.nodeValue;
	    					
	    					if (SCHEME_SNO > 0)
	    					{ 
	    					
	    					
	    					mycurrent_row=document.createElement("TR");
	    					
	    					mycurrent_row.id=rid;
		    				var aid=mycurrent_row.id;
		    				
		    				mycurrent_row.setAttribute("class","tdText");
	    					cellschsno=document.createElement("TD");
	    					
	    					 if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {
	    						 hiddenSCHEME_SNO=document.createElement("<input type='hidden' name='SCHEME_SNO_VAL' id='SCHEME_SNO_VAL' value='"+SCHEME_SNO+"'>");
	    						 hiddenSCHEME_SNO_fr=document.createElement("<input type='hidden' name='hiddenSCHEME_SNO_fre"+i+"' id='hiddenSCHEME_SNO_fre"+i+"' value='"+SCHEME_SNO+"'>");
	    					 }
	    					 else
	    					 {
	    					 var hiddenSCHEME_SNO=document.createElement("input");
    	                     hiddenSCHEME_SNO.type="hidden";
    	                     hiddenSCHEME_SNO.name="SCHEME_SNO_VAL";
    	                     hiddenSCHEME_SNO.id="SCHEME_SNO_VAL";
    	                     hiddenSCHEME_SNO.value=SCHEME_SNO;
    	                     hiddenSCHEME_SNO_fr=document.createElement("input");
    	                     hiddenSCHEME_SNO_fr.type="hidden";
    	                     hiddenSCHEME_SNO_fr.name="hiddenSCHEME_SNO_fre"+i;
    	                     hiddenSCHEME_SNO_fr.id="hiddenSCHEME_SNO_fre"+i;
    	                     hiddenSCHEME_SNO_fr.value=SCHEME_SNO;
	    					 }
	    					 cellschsno.setAttribute("class","tdText");
    	                     cellschsno.appendChild(hiddenSCHEME_SNO_fr);
	    					 
    	                     cellschsno.setAttribute("class","tdText");
    	                     cellschsno.appendChild(hiddenSCHEME_SNO);
    	                     
    	                    
    	                    
    	                     
    	                     
	    				
    	                     if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {	 
 	    						 schsnum=document.createElement("<label id='schsno'  name='schsno'>");
	    						 schsnum.innerHTML=""+SCH_NAME+"</font>";            		 
	    						 schsnum.setAttribute("class", "tdText");
	    	            		 
	    						  
	    					 }
    	                     else
	    					 {
	    						 schsnum=document.createTextNode(SCH_NAME);
	    						 schsnum.name="schsno";
	    	                     schsnum.id="schsno";    
	    	                    
	    					 }
    	                   
    	                     cellschsno.appendChild(schsnum);
    	                    
    	                     if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {
    	                    	 bensno=document.createElement("<input type='hidden' name='benesnum' id='benesnum' value='"+BENEFICIARY_SNO+"'>");
	    					 }
    	                     else
    	                     {
    	                     
	    					  var bensno=document.createElement("input");
	    					  bensno.type="hidden";
	    					  bensno.name="benesnum";
	    					  bensno.id="benesnum";
	    					 
	    					  bensno.value=BENEFICIARY_SNO;
    	                     } 					
	    					cellschsno.appendChild(bensno);
	    					
	    					
	    					if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {
	    			        	
	    						tariffmode=document.createElement("<input type='hidden' name='flagmode" +i+ "' id='flagmode" +i+ "' value='-'>");
	    						if(TARIFF_FLAG!="")
		    					  {
		    						  tariffmode.value=TARIFF_FLAG;
		    					  }
		    					  else
		    					  {
		    					  tariffmode.value="-";
		    					  }
	    					 }
	    					else
	    					{
	    					  var tariffmode=document.createElement("input");
	    					  tariffmode.type="hidden";
	    					  tariffmode.name="flagmode"+i;
	    					  tariffmode.id="flagmode"+i;
	    					  tariffmode.value="-";
	    					  if(TARIFF_FLAG!="")
	    					  {
	    						  tariffmode.value=TARIFF_FLAG;
	    					  }
	    					  else
	    					  {
	    					  tariffmode.value="-";
	    					  }
	    					  if(SETTING_FLAG=='FR')
	    					  {
	    						  tariffmode.value=TARIFF_FLAG;
	    					  }
	    					}  					
	    					cellschsno.appendChild(tariffmode);
	    					
	    					mycurrent_row.appendChild(cellschsno);
	    					
	    				
	    					
	    					
	    					cellcheck=document.createElement("TD");
	    					if((SETTING_FLAG=='CR')||(SETTING_FLAG=="null"))
	    					{
	    					
	    			         
	    				    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {
	    				    	if(TARIFF_FLAG=='S')
	    				    	{
	    				    		hiddencheck_sch=document.createElement("<input type='radio' name='mode_flag" +i+ "' id='mode_flag" +i+ "' value='S' checked='true' onclick='checkfun1(this.value,"+aid+")'>");
	    				    	}
	    				    	else
		    			         {
	    				    		hiddencheck_sch=document.createElement("<input type='radio' name='mode_flag" +i+ "' id='mode_flag" +i+ "' value='S' onclick='checkfun1(this.value,"+aid+")'>");
		    			         }
		    			        
		    			      
	    					 }
	    			         else
	    			         {
	    			         hiddencheck_sch=document.createElement("input");
	    			         hiddencheck_sch.type="radio";
	    			         hiddencheck_sch.name="mode_flag"+i;
	    			         hiddencheck_sch.id="mode_flag"+i;
	    			         hiddencheck_sch.value="S";
	    			         if(TARIFF_FLAG=='S')
	    			         {
	    			        	 hiddencheck_sch.checked=true;
	    			         }
	    			         hiddencheck_sch.setAttribute('onclick','checkfun1(this.value,'+aid+')');
	    			         
	    			         }
	    			         cellcheck.appendChild(hiddencheck_sch);
	    			         
	    			         
	    			        
	    			         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {	 
	    			        	 Scheme=document.createElement("<label id='Schemeval'  name='Schemeval'>");
	    			        	 Scheme.innerHTML="Scheme"; 
	    			        	  
	    					 }else
	    					 {
	    						 Scheme=document.createTextNode("Scheme");
	 	    			         Scheme.name="Schemeval";
	 	    			         Scheme.id="Schemeval";
	 	    			      
	    					 }
	    			         
	    			         cellcheck.appendChild(Scheme);
	    			         
	    			         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {
	    			        	 if(TARIFF_FLAG=='L')
	    			        	 {
	    			        	 hiddencheck_loc=document.createElement("<input type='radio' name='mode_flag" +i+ "' id='mode_flag" +i+ "' value='L' checked='true' onclick='checkfun1(this.value,"+aid+")'>");
	    			        	 }
	    			        	 else
	    			        	 {
	    			        	 hiddencheck_loc=document.createElement("<input type='radio' name='mode_flag" +i+ "' id='mode_flag" +i+ "' value='L' onclick='checkfun1(this.value,"+aid+")'>");
	    			        	 }
	    					 }
	    			        else
	    			        {
	    			        	
	    			        hiddencheck_loc=document.createElement("input");
	    			        hiddencheck_loc.type="radio";
	    			        hiddencheck_loc.name="mode_flag"+i;
	    			        hiddencheck_loc.id="mode_flag"+i;
	    			        hiddencheck_loc.value="L";
	    			        if(TARIFF_FLAG=='L')
	    			         {
	    			        	hiddencheck_loc.checked=true;
	    			         }
	    			        hiddencheck_loc.setAttribute('onclick','checkfun1(this.value,'+aid+')');
	    			        }
	    			        cellcheck.appendChild(hiddencheck_loc);
	    			        
	    			         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {	 
	    			        	 Location=document.createElement("<label id='Locationval'  name='Locationval'>");
	    			        	 Location.innerHTML="Location"; 
	    			        	 
	    					 }else
	    					 {
	    						 Location=document.createTextNode("Location");
	    						
		    			         Location.name="Locationval";
		    			         Location.id="Locationval";
		    			         //Location_add=document.createTextNode("nbsp;nbsp;nbsp;nbsp;nbsp;nbsp;");
		    			         //Location.appendChild(Location_add);
	    					 }
	    			         cellcheck.appendChild(Location);
	    			         mycurrent_row.appendChild(cellcheck);
	    					}
	    					else 
	    						if(SETTING_FLAG=='FR')
	    						{
	    						  if(TARIFF_FLAG=='S')
	    						  {	  
	    							  if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    		    					 {	 
	    		    			        	 Location=document.createElement("<label id='Locationval'  name='Locationval'>");
	    		    			        	 Location.innerHTML="SCHEME"; 
	    		    			        	 
	    		    					 }
	    							  else
	    							  {
	    							  Location=document.createTextNode("SCHEME");
	    							  Location.name="Locationval";
	    							  Location.id="Locationval";
	    							  }
	    						  }
	    						  else if(TARIFF_FLAG=='L')
	    						  {	 
	    							  if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    		    					 {	 
	    		    			        	 Location=document.createElement("<label id='Locationval'  name='Locationval'>");
	    		    			        	 Location.innerHTML="LOCATION"; 
	    		    			        	 
	    		    					 }
	    							  else
	    							  {
	    							  Location=document.createTextNode("LOCATION");
	    							 // Location=document.createTextNode('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
	    							  Location.name="Locationval";
	    							  Location.id="Locationval";
	    							  }
	    						  }
		    			         cellcheck.appendChild(Location);
		    			         mycurrent_row.appendChild(cellcheck);
	    						}
	    					
	    					if((SETTING_FLAG=='CR')||(SETTING_FLAG=="null"))
	    					{
	    			         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {
	    			        	 butt_freez=document.createElement("<input type='button' name='but_freze" +i+ "' id='but_freze" +i+ "' value='Freeze'  onclick='funsetfreeze("+aid+")' disabled='true' class='fb2'>");
	    			        	 if(TARIFF_FLAG!='-')
		    			        	{
		    			        		butt_freez.disabled=false;
		    			        	}
	    			        	
	    					 }
	    			        else
	    			        {
	    			        	
	    			        	butt_freez=document.createElement("input");
	    			        	butt_freez.type="button";
	    			        	butt_freez.name="but_freze"+i;
	    			        	butt_freez.id="but_freze"+i;
	    			        	
	    			        	butt_freez.value="Freeze";
	    			        	butt_freez.disabled="true";
	    			        	butt_freez.setAttribute('class','fb2');
	    			        	
	    			        	if(TARIFF_FLAG!='-')
	    			        	{
	    			        		butt_freez.disabled=false;
	    			        	}
	    			        	butt_freez.setAttribute('onclick','funsetfreeze('+aid+')');
	    			        }
	    			        
	    			         cellcheck.appendChild(butt_freez);
	    			         mycurrent_row.appendChild(cellcheck);
	    					}
	    			         if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
	    					 {
	    			        	 set_flag_text=document.createElement("<input type='hidden' name='set_flag" +i+ "' id='set_flag" +i+ "' value='CR'>");
	    			        	 if(SETTING_FLAG=='FR')
		    			        	{
		    			        		set_flag_text.value=SETTING_FLAG;
		    			        	}
	    			        	
	    					 }
	    			        else
	    			        {
	    			        	
	    			        	set_flag_text=document.createElement("input");
	    			        	set_flag_text.type="hidden";
	    			        	set_flag_text.name="set_flag"+i;
	    			        	set_flag_text.value="CR";
	    			        	set_flag_text.id="set_flag"+i;
	    			        	if(SETTING_FLAG=='FR')
	    			        	{
	    			        		set_flag_text.value=SETTING_FLAG;
	    			        	}
	    			        	
	    			        	
	    			        }
	    			        
	    			         cellcheck.appendChild(set_flag_text);
	    			         mycurrent_row.appendChild(cellcheck);
	    			        
	    			         if(SETTING_FLAG=='FR')
		    					{
		    						
		    						if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
			    					 {
			    			        	 butt_unfreez=document.createElement("<input type='button' name='butt_unfreez" +i+ "' id='butt_unfreez" +i+ "' value='UnFreeze' onclick='funsetunfreeze("+aid+")' class='fb2' >");
			    			        	 
			    			        	
			    					 }
		    						else
		    						{
		    							butt_unfreez=document.createElement("input");
		    							butt_unfreez.type="button";
		    							butt_unfreez.name="butt_unfreez"+i;
		    							butt_unfreez.id="butt_unfreez"+i;
			    			        	
		    							butt_unfreez.value="UnFreeze";
		    							//butt_unfreez.disabled="true";
			    			        	
		    							butt_unfreez.setAttribute('class','fb2');
			    			        	butt_unfreez.setAttribute('onclick','funsetunfreeze('+aid+')');
		    						}
		    						 cellcheck.appendChild(butt_unfreez);
		    						 mycurrent_row.appendChild(cellcheck);
		    					}
	    			        
	    			         
	    			         tbody.appendChild(mycurrent_row);
	    			         rid++;
	    					}
    			
                    }
    			}
    			else
    			{
    				alert("Tariff Rate Settings Already Done");
    			}
    			}
            }
        }     
    }
}

function checkfun1(str,rrid)
{
	
	document.getElementById("flagmode"+rrid).value=str;
	document.getElementById("but_freze"+rrid).disabled=false;
	
}
function funsetfreeze(flagid)
{
	
	ben_sno =document.tariff_flag.Beneficiary_Name.value;
	
	document.getElementById("set_flag"+flagid).value="FR";
	
	document.getElementById("but_freze"+flagid).disabled=true;
	
 	hiddenSCHEME_SNO_fre=document.getElementById("hiddenSCHEME_SNO_fre"+flagid).value;
 	
	set_flag=document.getElementById("set_flag"+flagid).value;
	flagmode=document.getElementById("flagmode"+flagid).value;	
	
	
	
	var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
    {
    	alert ("Your browser does not support AJAX!");
		         return;
    }
    url="../../../../../Pms_Dcb_Tariff_Flag?command=insert_rowwise&ben_sno_row="+ben_sno+"&sch_sno_row="+hiddenSCHEME_SNO_fre+"&ben_sno_row="+ben_sno+"&flagmode_row="+flagmode+"&setflag_row="+set_flag;
   
    
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
   
    
    xmlhttp.onreadystatechange= function()
    {
    
       loadfreeze(xmlhttp);
    }
    xmlhttp.send(null);
   /* tbodyclear=document.getElementById("valuerows");
	ben_loc_flag_body_clear=document.getElementById("ben_loc_flag");
	try
    {
		
		
		tbodyclear.innerHTML="";
	}
    catch(e) 
    {
    	tbodyclear.innerText="";
    }
    try
    {
		
		
    	ben_loc_flag_body_clear.innerHTML="";
	}
    catch(e) 
    {
    	ben_loc_flag_body_clear.innerText="";
    }
   
	*/
   



}
function funsetunfreeze(unfreezeflagid)
{
//	alert("unfreezeflagid"+unfreezeflagid);
	 
		 document.getElementById("set_flag"+unfreezeflagid).value='CR';
		 ben_sno =document.tariff_flag.Beneficiary_Name.value;
			
			
			
		 	hiddenSCHEME_SNO_fre=document.getElementById("hiddenSCHEME_SNO_fre"+unfreezeflagid).value;
		 	
			set_flag=document.getElementById("set_flag"+unfreezeflagid).value;
			flagmode=document.getElementById("flagmode"+unfreezeflagid).value;	
			
			
			
			var xmlhttp=GetXmlHttpObject();
		    if (xmlhttp==null)
		    {
		    	alert ("Your browser does not support AJAX!");
				         return;
		    }
		    url="../../../../../Pms_Dcb_Tariff_Flag?command=insert_rowwiseunfreeze&ben_sno_row="+ben_sno+"&sch_sno_row="+hiddenSCHEME_SNO_fre+"&ben_sno_row="+ben_sno+"&flagmode_row="+flagmode+"&setflag_row="+set_flag;
		   // alert("url"+url);
		    
		    url=url+"&sid="+Math.random();
		    xmlhttp.open("GET",url,true);
		    
		    xmlhttp.onreadystatechange= function()
            {
                loadunfreeze(xmlhttp);
            }
			 
		    xmlhttp.send(null);
		  /*  try
		    {
				
				
				tbodyclear.innerHTML="";
			}
		    catch(e) 
		    {
		    	tbodyclear.innerText="";
		    }
		    try
		    {
				
				
		    	ben_loc_flag_body_clear.innerHTML="";
			}
		    catch(e) 
		    {
		    	ben_loc_flag_body_clear.innerText="";
		    }*/
		 
		
}
function loadunfreeze(xmlhttp)
{

	  if(xmlhttp.readyState==4)
	   {
		   if(xmlhttp.status==200)
	       {
			   var baseres,commandres,flagres,recordfound;
	    	    baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
	    		commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
	    		flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
	    		counttariff=baseres.getElementsByTagName("counttariff")[0].firstChild.nodeValue;
	    		if(counttariff==0)
	    		{
			   		   loadschemes();
	    		}
	    		else
	    		{
	    			 alert("Tariff Slab Rate Exists for this Beneficiary and Scheme ..");
	    			return false;
	    		}
	       }
	   }
}
function loadfreeze(xmlhttp)
{

	  if(xmlhttp.readyState==4)
	   {
		   if(xmlhttp.status==200)
	       {
			   loadschemes();
	       }
	   }
}
function funsave()
{
	var SCHEME_SNO_VAL=new Array();
	var benesnum=new Array();
	var flagmode=new Array();
	var set_flag=new Array();
	var general_flag=2;
	var general_flag_c=document.tariff_flag.op.length;
    var ben_sno =document.tariff_flag.Beneficiary_Name.value;
    
	/*for (i=0;i<general_flag_c;i++)
	{
		
		if (document.tariff_flag.op[i].checked==true)
		{
			general_flag=document.tariff_flag.op[i].value;
			
		}
		
	}*/

	m=0;
 
	if (general_flag==2)
	{
		
		if(document.tariff_flag.SCHEME_SNO_VAL.length>0)
		{
			
			for(k=0; k<document.tariff_flag.SCHEME_SNO_VAL.length ; k++)
			{
			
				SCHEME_SNO_VAL[m]=document.tariff_flag.SCHEME_SNO_VAL[k].value;
				benesnum[m]=document.tariff_flag.benesnum[k].value;				
				flagmode[m]=document.getElementById("flagmode"+k).value;	
				set_flag[m]=document.getElementById("set_flag"+k).value;	
				
				m=m+1;
			}	
		}
		else
		{
			SCHEME_SNO_VAL[0]=document.tariff_flag.SCHEME_SNO_VAL.value;
			benesnum[0]=document.tariff_flag.benesnum.value;				
			flagmode[0]=document.getElementById("flagmode"+0).value;
			set_flag[0]=document.getElementById("set_flag"+0).value;
		}
	}
	var xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
    {
    	alert ("Your browser does not support AJAX!");
		         return;
    }
    url="../../../../../Pms_Dcb_Tariff_Flag?command=insert&general_flag="+general_flag+"&ben_sno="+ben_sno+"&SCHEME_SNO_VAL="+SCHEME_SNO_VAL+"&benesnum="+benesnum+"&flagmode="+flagmode+"&set_flag="+set_flag;
    
    
    url=url+"&sid="+Math.random();
    xmlhttp.open("GET",url,true);
    xmlhttp.onreadystatechange= function()
            {
                stateChangedloadinsert(xmlhttp);
            }
    xmlhttp.send(null);
	/*	for(i=0;i<rid;i++)
		{
			var mode = "";
			for(var j=0;j<3;j++)
			{
				var opt = document.getElementsByName("mode_flag"+i);
				if(opt[j].checked)
				{
					mode = opt[j].value;
					alert("mode: "+mode);
					document.tariff_flag.flagmode[i].value=mode;
					break;
				}
			}
		}*/
	
	
	
}



function stateChangedloadinsert(xmlhttp)
{
    
  
   if(xmlhttp.readyState==4)
   {
	   if(xmlhttp.status==200)
       {
    try
    {
    	 
    	 var baseres,commandres,flagres;
    	 baseres=xmlhttp.responseXML.getElementsByTagName("response")[0];
    	 commandres=baseres.getElementsByTagName("command")[0].firstChild.nodeValue;
    	
    	 flagres=baseres.getElementsByTagName("flag")[0].firstChild.nodeValue;
    }
    catch(e)
    {
    }
  
   
             if(commandres=="add")
            {
            	
            	 if(flagres=='success')
                {
                    
                        alert("Settings Successfully Completed...");
                        loadschemes();
                 }
                
                 else
                {
                    alert("Not Loaded");
                }
            }
        }
    }
}
function exitwindow()
{
    window.close();
}






function report_()
{
	 var xmlhttp=GetXmlHttpObject();
	 var Beneficiary_Name=document.getElementById("Beneficiary_Name").value;
	 var Beneficiary_type=document.getElementById("Beneficiary_type").value;
	 var sflag=0;
	 
	 if (Beneficiary_Name==0)
	 {
		 var  c=confirm("List Beneficiary Setting");
		 if (c==true)
			 sflag=1;
		 else
			 sflag=0;
		 
	 }else
	 {
		 sflag=1; 
	 }
	 
	if (sflag==1)
	{
	 var url="../../../../../Pms_Dcb_Tariff_Flag?command=report&ben_sno="+Beneficiary_Name+"&Beneficiary_type="+Beneficiary_type;
	 xmlhttp.open("GET",url,true);
	 xmlhttp.onreadystatechange= function()
	 {
	    			report_result(xmlhttp);
	 }
	 xmlhttp.send(null);
	}
}
function report_result(xmlobj)
{
if (xmlobj.readyState==4)
 {
	 if(xmlobj.status==200)
     {
		  var bR=xmlobj.responseXML.getElementsByTagName("response")[0];
		  var tbody = document.getElementById("ben_loc_flag");
		  var table = document.getElementById("ben_loc_data");    
		  
		  var len=bR.getElementsByTagName("METRE_LOCATION").length;
		  for(t=tbody.rows.length-1;t>=0;t--){tbody.deleteRow(0);}
		  
		  
				  var new_row1=cell("TR","","","","","","tdH","","","","","","");
				  var TARIFF_MODE_label=cell("TD","label","","","Sl.No",7,"tdText","font-size:2","left","5%","","","");
				  var BENEFICIARY_NAME_label=cell("TD","label","","","Beneficiary Name",7,"tdText","font-size:2","","","center","","");
				  var SCHEME_NAME_label=cell("TD","label","","","Scheme Name",7,"tdText","font-size:2","","","center","","");
		          var METRE_LOCATION_label=cell("TD","label","","","Meter Location",7,"tdText","font-size:2","","","center","","");
		          var Tariff=cell("TD","label","","","Tariff",7,"tdText","font-size:2","left","10%","","","");
		          new_row1.appendChild(TARIFF_MODE_label);
		          new_row1.appendChild(BENEFICIARY_NAME_label);
		          new_row1.appendChild(SCHEME_NAME_label);
		          new_row1.appendChild(METRE_LOCATION_label);
		          new_row1.appendChild(Tariff);
		          tbody.appendChild(new_row1);
          
		  for (i=0;i<len;i++)
		 	{  
			  var BENEFICIARY_NAME=bR.getElementsByTagName("BENEFICIARY_NAME")[i].firstChild.nodeValue;
			  var SCH_SHORT_DESC=bR.getElementsByTagName("SCH_SHORT_DESC")[i].firstChild.nodeValue;
			  var TARIFF_MODE_one=bR.getElementsByTagName("TARIFF_MODE")[i].firstChild.nodeValue;
			  
			  if(TARIFF_MODE_one=='S')
			  {
				  TARIFF_MODE_one='Scheme';
			  }
			  else if(TARIFF_MODE_one=='L')
			  {
				  TARIFF_MODE_one='Location';
			  }
			  else
			  {
				  TARIFF_MODE_one='-';
			  }  
			  //alert("TARIFF_MODE_one"+TARIFF_MODE_one);
			  var METRE_LOCATION=bR.getElementsByTagName("METRE_LOCATION")[i].firstChild.nodeValue;
			  var new_row=cell("TR","","","","","","tdText","","","","","","");
              var TARIFF_MODE_cell=cell("TD","label","","",TARIFF_MODE_one,7,"tdText","font-size:2","left","5%","","","");
              var BENEFICIARY_NAME_cell=cell("TD","label","","",BENEFICIARY_NAME,7,"tdText","font-size:2","","","left","","");
              var SCH_SHORT_DESC_cell=cell("TD","label","","",SCH_SHORT_DESC,7,"tdText","font-size:2","","","left","","");
              var METRE_LOCATION_cell=cell("TD","label","","",METRE_LOCATION,7,"tdText","font-size:2","","","left","","");
              var sno_LOCATION_cell=cell("TD","label","","",(i+1),7,"","font-size:2","left","10%","","","");
              new_row.appendChild(sno_LOCATION_cell);
              new_row.appendChild(BENEFICIARY_NAME_cell);
              new_row.appendChild(SCH_SHORT_DESC_cell);
              new_row.appendChild(METRE_LOCATION_cell);
              new_row.appendChild(TARIFF_MODE_cell);
              tbody.appendChild(new_row);
		 	}
		
     }
     	
 }
}
function refreshval()
{
	document.tariff_flag.Beneficiary_type.value=0;
	document.tariff_flag.Beneficiary_Name.value=0;
	tbodyclear=document.getElementById("valuerows");
	ben_loc_flag_body_clear=document.getElementById("ben_loc_flag");
	  var t=tbodyclear.rows.length  ;
	    for(var i=t-1;i>=0;i--)
	    {
	    	tbodyclear.deleteRow(i);
	    } 
	  
}














