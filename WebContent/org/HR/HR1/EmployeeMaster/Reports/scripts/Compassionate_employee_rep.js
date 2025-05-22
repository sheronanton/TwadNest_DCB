
function getTransport()
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


function checkNull()
{
	
  if(document.Compassionate_details_report.com_repo[0].checked==false && document.Compassionate_details_report.com_repo[1].checked==false && document.Compassionate_details_report.com_repo[2].checked==false && document.Compassionate_details_report.com_repo[3].checked==false && document.Compassionate_details_report.com_repo[4].checked==false && document.Compassionate_details_report.com_repo[5].checked==false)
  {
	  alert("Please Select Compassionate Status");
	  return false;  
  }	  
  else if(document.Compassionate_details_report.com_repo_desig[0].checked==false && document.Compassionate_details_report.com_repo_desig[1].checked==false)
  {
	  alert("Please Select Designation");
	  return false;  
  }
  
  
  return true;
}
function show()
{
document.getElementById("divdesignation").style.display='';
loaddesig();
}
function hide()
{
document.getElementById("divdesignation").style.display='none';
}
    

function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
   iframe.style.visibility='hidden';
   
}

function rankselectAll()
{
	
	
	var i=0;
    if(document.Compassionate_details_report.chkdesig)
      {
      
            for(i=0;i<document.Compassionate_details_report.chkdesig.length;i++)
            {
                    document.Compassionate_details_report.chkdesig[i].checked=true;
                    
            }
        }
}


 function loaddesig()
    {
        //alert(type);
       
       var url="../../../../../../Compassionate_details_report?Command=Desig";
       //alert(url);
       var req=getTransport();
       req.open("GET",url,true);        
	    req.onreadystatechange=function()
	    {
	    	
	       // requesthandle(req);
	        if(req.readyState==4)
	        { 
	            if(req.status==200)
	            {  
            //alert(req.responseText);
            var divdes=document.getElementById("divdesignation");
            divdes.style.visibility='visible';
            divdes.focus();
            //divdes.style.display='block';
           // var des=document.getElementById("cmbDesignation");
            
            
          //  alert(des);
            
            if(navigator.appName.indexOf('Microsoft')!=-1)
            	divdes.innerHTML=req.responseText;
                else
                	divdes.innerText=req.responseText;
            divdes.innerHTML=req.responseText;
            //divdes.innerTEXt=req.responseText;
             //alert("show");
            //document.getElementById("cmbRank").visibility='hidden';  
            //document.getElementById("cmbCadre").visibility='hidden';
            }
            }
             
             }
        req.send(null);
    }
/* function gosubmit()
	{		
	var val=checkNull();
		if(val==true)
		{
		 	var desig="";
          if(document.Compassionate_details_report.com_repo_desig[1].checked==true)
          {
if(document.Compassionate_details_report.chkdesig)
 {
        if(document.Compassionate_details_report.chkdesig.length)
         {
      
      
                     for(i=0;i<document.Compassionate_details_report.chkdesig.length;i++)
                     {
                     
                             if(document.Compassionate_details_report.chkdesig[i].checked==true)
                                 { 
                                  
                                      desig=desig+document.Compassionate_details_report.chkdesig[i].value +",";
                                  }
                     }
                     
                     if(desig!="")
                     {
                    
                     desig=desig.substring(0,desig.length-1);
                   
                     }
                     else
                     {
                     alert("please select specific designation");
                     return false;
                     }
                   
                    
        }
 }  
 }
	
			
			if(document.Compassionate_details_report.com_repo[0].checked)
		   	 status='All';
		    else if(document.Compassionate_details_report.com_repo[1].checked)
		   	 status='Seniority';
		   	else if(document.Compassionate_details_report.com_repo[2].checked)
		   	 status='US';
		    else if(document.Compassionate_details_report.com_repo[3].checked)
		   	 status='Incomplete';
		   	else if(document.Compassionate_details_report.com_repo[4].checked)
		   	 status='RJ';
		   	else if(document.Compassionate_details_report.com_repo[5].checked)
		   	 status='Qualification';
		   	else
		   	 status='Designation';
		   	
		  
		   
		   	 var url="../../../../../../Compassionate_details_report?status="+status;
		   	 url=url+"&desig="+desig;
				//alert(url);
			document.Compassionate_details_report.submit();
		}
	}*/
 function frmsubmit()
 {
	 var val=checkNull();
		if(val==true)
		{
			 var url="../../../../../../Compassionate_employee_rep?OLevel=submit" ;
			// alert(url)

			 
			 if(document.Compassionate_details_report.com_repo[0].checked==true)
			   {
				 com_repo='All';
			   	url=url+"&com_repo="+com_repo;
			   	
			   }
		  else if(document.Compassionate_details_report.com_repo[1].checked==true)
			  {
			  com_repo='Seniority';
			   	url=url+"&com_repo="+com_repo;
			  }
	      else if(document.Compassionate_details_report.com_repo[2].checked==true)
	    	  {
	    	  com_repo='US';
			   	url=url+"&com_repo="+com_repo;
	    	  }
		  else if(document.Compassionate_details_report.com_repo[3].checked==true)
			  {
			  com_repo='Incomplete';
			   	url=url+"&com_repo="+com_repo;
			  }
		  else if(document.Compassionate_details_report.com_repo[4].checked==true)
			  {
			  com_repo='RJ';
			   	url=url+"&com_repo="+com_repo;
			  }
		 else if(document.Compassionate_details_report.com_repo[5].checked==true)
			 {
			 com_repo='Qualification';
			   	url=url+"&com_repo="+com_repo;
			 }
		 else
			  {
			 com_repo='Designation';
		        url=url+"&com_repo="+com_repo;
			  }
		   
			 
	 // alert(url)
	  
	   
	   if(document.Compassionate_details_report.com_repo_desig[0].checked==true)
	   {
		   com_repo_desig='AllDes';
	   	url=url+"&com_repo_desig="+com_repo_desig;
	   	
	   	//document.Compassionate_details_report.officeselected.value=com_repo_desig;
	   }
   if(document.Compassionate_details_report.com_repo_desig[1].checked==true)
	  {
	  com_repo_desig='Design';
	   	url=url+"&com_repo_desig="+com_repo_desig;
	   	
	    var desig="";
		   
		   if(document.Compassionate_details_report.chkdesig)
	       {
	       
	                 if(document.Compassionate_details_report.chkdesig.length)
	                 {
	                           for(i=0;i<document.Compassionate_details_report.chkdesig.length;i++)
	                             {
	                                     if(document.Compassionate_details_report.chkdesig[i].checked==true)
	                                    	 desig= desig+document.Compassionate_details_report.chkdesig[i].value +",";
	                                        
	                                     
	                             }
	                             if(desig!="")
	                             {
	                            	 desig=desig.substring(0,desig.length-1);
	                                  url=url+"&designation="+desig;
	                                 //alert(url);
	                                  document.Compassionate_details_report.officeselected.value=desig;
	                                  //alert(document.frmValidationSummaryRep.officeselected.value);
	                             }
	                             else
	                             {
	                                    alert('Please Select Specific Desigantion.');
	                                    // document.frmValidationSummaryRep.cmbregion[0].focus(); 
	                                     return false;
	                             }
	                     
	                 }
	  }
	   
	   
	  
 
	
	   
	  
   }
   
   
   document.Compassionate_details_report.action="../../../../../../Compassionate_employee_rep";
   document.Compassionate_details_report.method='POST';
    document.Compassionate_details_report.submit();   	
 }
 }