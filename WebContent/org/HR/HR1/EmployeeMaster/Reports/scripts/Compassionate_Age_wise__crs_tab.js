
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

function hide()
{
document.getElementById("divdesignation").style.display='none';
}
    

function statusclose()
{
    
    var iframe=document.getElementById("divdesignation");
   iframe.style.visibility='hidden';
   
}

function statusselectAll()
{
	
	
	var i=0;
    if(document.Compassionate_details_report.chkstatus)
      {
      
            for(i=0;i<document.Compassionate_details_report.chkstatus.length;i++)
            {
                    document.Compassionate_details_report.chkstatus[i].checked=true;
                    
            }
        }
}


 function LoadStatus()
    {
        
       
       var url="../../../../../../Age_wise_Compassionate_Report?Command=LoadStatus";
      // alert(url);
       var req=getTransport();
       req.open("POST",url,true);        
	    req.onreadystatechange=function()
	    {
	    	
	       
	        if(req.readyState==4)
	        { 
	        
	            if(req.status==200)
	            {  
           // alert(req.responseText);
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
	 
		
			 var url="../../../../../../Age_wise_Compassionate_Report?OLevel=submit" ;
			 

			 
			
			 
	  
	  
	   
	  
   
	    var status="";
		   
		   if(document.Compassionate_details_report.chkstatus)
	       {
	       
	                 if(document.Compassionate_details_report.chkstatus.length)
	                 {
	                           for(i=0;i<document.Compassionate_details_report.chkstatus.length;i++)
	                             {
	                        	   
	                                     if(document.Compassionate_details_report.chkstatus[i].checked==true)
	                                
	                                    	 status= status+"'"+document.Compassionate_details_report.chkstatus[i].value +"'"+",";
	                                     
	                                     
	                             }
	                             if(status!="")
	                             {
	                            	 status=status.substring(0,status.length-1);
	                                  url=url+"&statusdesc="+status;
	                                 //alert(url);
	                                  document.Compassionate_details_report.statuselected.value=status;
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
	   
	   
	  
 
	
	   
	  

   
   
   document.Compassionate_details_report.action="../../../../../../Age_wise_Compassionate_Report";
   document.Compassionate_details_report.method='GET';
    document.Compassionate_details_report.submit();   	

 }