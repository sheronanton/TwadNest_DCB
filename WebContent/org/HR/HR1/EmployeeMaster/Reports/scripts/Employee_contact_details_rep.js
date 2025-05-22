//alert('detail');

var regionflag=false;
//var regionflag=true;
var circleflag=true;
var divisionflag=true;
var offtypeflag=true;

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


function officevisible(r,c,d,o)
{
   //alert("inside officevisible");
    
    var rn=document.getElementById("divregion");
    rn.style.display=r;
    var cl=document.getElementById("divcircle");
    cl.style.display=c;
    var dn=document.getElementById("divdivision");
    dn.style.display=d;
    var ot=document.getElementById("divofftype");
    ot.style.display=o;
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
    var ot=document.getElementById("cmbofftype");
    ot.style.display=o;
}


function hideService(cmd)
{
	document.getElementById("orderdivabs").style.display='none';
	document.getElementById("service").style.display='none';
	if(cmd=="A")
		{
			document.getElementById("orderdivabs").style.display='';
			if(document.getElementsByName("rank")[0].checked==false)
			document.getElementById("otype").style.display='';
			else
			document.getElementById("otype").style.display='none';
		}
	else
		{
			document.getElementById("service").style.display='';
		}
}

function selectoption2()
{
   
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    document.getElementById("selectoff").style.display="";
    
    if(document.Employee_contact_details_rep.rad_off[0].checked)
    {
       
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
       
    }

  if(document.Employee_contact_details_rep.rad_off[1].checked)
  {
   // alert(document.Employee_contact_details_rep.rad_off[1].checked);
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none');
  }
  
  else if(document.Employee_contact_details_rep.rad_off[2].checked)
  {
   // alert(document.Employee_contact_details_rep.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none');
  }
  
  else if(document.Employee_contact_details_rep.rad_off[3].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','block');
  }
  if(document.Employee_contact_details_rep.rad_off[4].checked)
  {
     var offlevel=document.getElementById("selectoff");
     offlevel.style.display="none";
   
     
  }
  
  document.Employee_contact_details_rep.cmbcircle.disabled=true;
   // document.Employee_contact_details_rep.cmbofftype.disabled=true;
    document.Employee_contact_details_rep.cmbdivision.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    
    
    regionflag=true;
    circleflag=true;
    offtypeflag=true;
    divisionflag=true;
   

}


var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;

  
function getRegion()
    {
       
              
        
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       
    
         
         var url="../../../../../../EmployeeDPReport_office?OLevel=region" ;
        //alert(url);
                
        document.Employee_contact_details_rep.cmbregion.focus();
        var req=getTransport();
        req.open("GET",url,false);        
        req.onreadystatechange=function()
        {
            //requesthandle(req);
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                   // alert(req.responseText);
                 
                 
                  
                    var iframe=document.getElementById("diviframeregion");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                    	{
                        iframe.innerHTML=req.responseText;
                    iframe.innerText=req.responseText;
                    	}
                    else
                    	{
                        iframe.innerText=req.responseText;
                    iframe.innerHTML=req.responseText;
                    	}
                  document.Employee_contact_details_rep.cmbcircle.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   
                   regionflag=true;
                  }
            }
        }
        req.send(null);
    }
    
    
function getCircle(cir)
{
      //alert("inside getcircle");
        
       var region="";
     //   var type=document.Employee_contact_details_rep.cmbolevel.options[document.Employee_contact_details_rep.cmbolevel.selectedIndex].value;
     
      
    
     
       if(document.Employee_contact_details_rep.chkregion)
      {
      
         // alert(document.Employee_contact_details_rep.chkregion);
           
               if(document.Employee_contact_details_rep.chkregion.length > 0)
              {
              
                 alert(document.Employee_contact_details_rep.chkregion.length);
             
                            for(i=0;i<document.Employee_contact_details_rep.chkregion.length;i++)
                            {
                                alert("document.Employee_contact_details_rep.chkregion[i].checked..."+document.Employee_contact_details_rep.chkregion[i].checked);
                                    if(document.Employee_contact_details_rep.chkregion[i].checked==true)
                                            region= region+document.Employee_contact_details_rep.chkregion[i].value +",";
                                            
                                         //   alert("region..."+region);
                                    
                            }
                            
                            if(region!="")
                            {
                              // alert("inside 1");
                                 if(circleflag==true && cir=='null')
                                {
                                  //     alert("inside 2");
                                         var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                          iframe.focus();
                                           document.Employee_contact_details_rep.cmbdivision.disabled=false;
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                               //alert(url);
                              // document.Employee_contact_details_rep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
                                req.onreadystatechange=function()
                                {
                                    requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          {  
                                          alert("success");
                                         //  var iframe=document.getElementById("diviframecircle");
                                            // if((type=='CL' || type=='DN') && cir=='null' )
                                            if(circleflag==true && cir=='null')
                                            {
                                         //   alert("see these");
                                            var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                            	{
                                            	 iframe.innerText=req.responseText;
                                              iframe.innerHTML=req.responseText;
                                            	}
                                              else
                                            	  {
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                            	  }
                                            
                                            document.Employee_contact_details_rep.cmbdivision.disabled=false;
                                              var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                            
                                            
                                            }
                                             iframe.innerHTML=req.responseText;
                                             
                                         //    alert(iframe.innerHTML);
                                             /*
                                             if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                              */
                                             circleflag=true;
                                            
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframecircle");
                                iframe.style.visibility='hidden';
                                document.Employee_contact_details_rep.cmbdivision.disabled=false;
                                document.Employee_contact_details_rep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.Employee_contact_details_rep.chkregion.checked==true)
                             {
                                            region= document.Employee_contact_details_rep.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.Employee_contact_details_rep.cmbdivision.disabled=false;
                                               // return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                                       // alert(url)
                                      // document.Employee_contact_details_rep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
                                        req.onreadystatechange=function()
                                        {
                                        	
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                            	
                                                  if(req.status==200)
                                                  {  
                                                   var iframe=document.getElementById("diviframecircle");
                                                   if(navigator.appName.indexOf('Microsoft')!=-1)
                                                	   {
                                                	   iframe.innerText=req.responseText;
                                                       iframe.innerHTML=req.responseText;
                                                	   }
                                                         else
                                                        	 {
                                                       iframe.innerText=req.responseText;
                                                   iframe.innerHTML=req.responseText;
                                                        	 }
                                                    // if((type=='CL' || type=='DN') && cir=='null' )
                                                    if(cir=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.Employee_contact_details_rep.cmbdivision.disabled=false;
                                                      var iframe=document.getElementById("diviframedivision");
                                                     iframe.style.visibility='hidden';
                                                    
                                                    
                                                    }
                                                     iframe.innerHTML=req.responseText;
                                                     
                                            //         alert(iframe.innerHTML);
                                                     /*
                                                     if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                       */
                                                     circleflag=true;
                                                    
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                               
                            
                                var iframe=document.getElementById("diviframecircle");
                                iframe.style.visibility='hidden';
                                document.Employee_contact_details_rep.cmbdivision.disabled=false;
                                document.Employee_contact_details_rep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
     }
      
   
}


function getDivision(div)
    {
        
       // alert('division');
       var region="";
       var circle="";
       var offtype="";
       
       //if(document.Employee_contact_details_rep.chkregion)
      //{
      
         
           
               if(document.Employee_contact_details_rep.chkregion.length)
              {
              
                
             
                            for(i=0;i<document.Employee_contact_details_rep.chkregion.length;i++)
                            {
                             
                                    if(document.Employee_contact_details_rep.chkregion[i].checked==true)
                                            region= region+document.Employee_contact_details_rep.chkregion[i].value +",";
                                            
                                         
                                    
                            }
                 }
        //}
       
       if(document.Employee_contact_details_rep.chkofftype)
       {
               
         if(document.Employee_contact_details_rep.chkofftype.length)
         {
                    
          for(i=0;i<document.Employee_contact_details_rep.chkofftype.length;i++)
          {
            if(document.Employee_contact_details_rep.chkofftype[i].checked==true)
             offtype= offtype+"'"+document.Employee_contact_details_rep.chkofftype[i].value+"'"+",";
             
                                  
          }
           
                      
         
         }
       }
       
      if(document.Employee_contact_details_rep.chkcircle)
      {
      
            if(document.Employee_contact_details_rep.chkcircle.length)
            {
            
                            for(i=0;i<document.Employee_contact_details_rep.chkcircle.length;i++)
                            {
                                    if(document.Employee_contact_details_rep.chkcircle[i].checked==true)
                                            circle= circle+document.Employee_contact_details_rep.chkcircle[i].value +",";
                                  
                            }
                            if(circle!="")
                            {
                               
                               
                                 if(divisionflag==true && div=='null')
                                {
                                   
                                       
                                         var iframe=document.getElementById("diviframedivision");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                       // return;
                                }
                                circle=circle.substring(0,circle.length-1);
                                offtype=offtype.substring(0,offtype.length-1);
                                region=region.substring(0,region.length-1);
                               // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                              
                              var url="../../../../../../EmployeeDPReport_office?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
                             //alert(url);
                              
                              // document.Employee_contact_details_rep.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,false);        
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          { 
                                           // alert("success");
                                            var iframe=document.getElementById("diviframedivision");
                                            
                                            //var type=document.Employee_contact_details_rep.cmbolevel.options[document.Employee_contact_details_rep.cmbolevel.selectedIndex].value;
                                            if(div=='null')
                                            {
                                            //alert("visible");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            }
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                              {iframe.innerHTML=req.responseText;
                                            iframe.innerText=req.responseText;}
                                              else
                                            	  {
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                             
                                            	  }
                                             
                                           divisionflag=true;  
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.Employee_contact_details_rep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.Employee_contact_details_rep.chkcircle.checked==true)
                             {
                                            circle= document.Employee_contact_details_rep.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && div=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframedivision");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                              //  return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                      //  var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                                     offtype=offtype.substring(0,offtype.length-1); 
                                      var url="../../../../../../EmployeeDPReport_office?OLevel=division&circles="+circle+"&offtype="+offtype;
                                      
                                      // document.Employee_contact_details_rep.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,false);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  { 
                                                    var iframe=document.getElementById("diviframedivision");
                                                    //var type=document.Employee_contact_details_rep.cmbolevel.options[document.Employee_contact_details_rep.cmbolevel.selectedIndex].value;
                                                    if(div=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    }
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                                    	{
                                              iframe.innerHTML=req.responseText;
                                                    iframe.innerText=req.responseText;
                                                    	}
                                              else
                                            	  {
                                              iframe.innerText=req.responseText;
                                                    iframe.innerHTML=req.responseText; }   
                                                     //iframe.innerHTML=req.responseText;
                                                     
                                                   divisionflag=true;  
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.Employee_contact_details_rep.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                
                }
            
      }
   
}

function regionclose()
{
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
  
}

function offtypeclose()
{
    
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
  
}


/*function regionselectAll()
{

   if(document.Employee_contact_details_rep.chkregion)
      {
     
            
            for(i=0;i<document.Employee_contact_details_rep.chkregion.length;i++)
            {
            	
                    document.Employee_contact_details_rep.chkregion[i].checked=true;
                    
            }
             //regiononchange();
        }
}*/
function regionselectAll()
{
    if(document.Employee_contact_details_rep.chkregion)
      {
      
            for(i=0;i<document.Employee_contact_details_rep.chkregion.length;i++)
            {
                    document.Employee_contact_details_rep.chkregion[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function offtypeselectAll()
{

   if(document.Employee_contact_details_rep.chkofftype)
      {
      
            
            for(i=0;i<document.Employee_contact_details_rep.chkofftype.length;i++)
            {
                    document.Employee_contact_details_rep.chkofftype[i].checked=true;
                    
            }
             oftypeonchange();
        }
}


function circleclose()
{
    
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
  
}


function circleselectAll()
{
    if(document.Employee_contact_details_rep.chkcircle)
      {
      
            for(i=0;i<document.Employee_contact_details_rep.chkcircle.length;i++)
            {
                    document.Employee_contact_details_rep.chkcircle[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function divisionclose()
{
    
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
  
}

function oftypeonchange()
{

     if(document.Employee_contact_details_rep.rad_off[3].checked)
     {
       getDivision('division');
     }
     /*
     var type=document.Employee_contact_details_rep.cmbolevel.options[document.Employee_contact_details_rep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }
   */
}


function frmsubmit()
{
  
    //alert('inside submit');
        var url="";
        
                             
                         if(document.Employee_contact_details_rep.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.Employee_contact_details_rep.chkregion)
                              {
                              
                                        if(document.Employee_contact_details_rep.chkregion.length)
                                        {
                                                  for(i=0;i<document.Employee_contact_details_rep.chkregion.length;i++)
                                                    {
                                                            if(document.Employee_contact_details_rep.chkregion[i].checked==true)
                                                              region= region+document.Employee_contact_details_rep.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.Employee_contact_details_rep.officeselected.value=region;
                                                         //alert(document.Employee_contact_details_rep.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.Employee_contact_details_rep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.Employee_contact_details_rep.chkregion.checked==true)
                                                {
                                                            region= document.Employee_contact_details_rep.chkregion.value ;
                                                            document.Employee_contact_details_rep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.Employee_contact_details_rep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.Employee_contact_details_rep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.Employee_contact_details_rep.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.Employee_contact_details_rep.chkcircle)
                              {
                              
                                       if(document.Employee_contact_details_rep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.Employee_contact_details_rep.chkcircle.length;i++)
                                                    {
                                                            if(document.Employee_contact_details_rep.chkcircle[i].checked==true)
                                                                    circle= circle+document.Employee_contact_details_rep.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.Employee_contact_details_rep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Employee_contact_details_rep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Employee_contact_details_rep.chkcircle.checked==true)
                                                {
                                                            circle= document.Employee_contact_details_rep.chkcircle.value ;
                                                            document.Employee_contact_details_rep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.Employee_contact_details_rep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.Employee_contact_details_rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Employee_contact_details_rep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.Employee_contact_details_rep.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.Employee_contact_details_rep.chkdivision)
                              {
                             //alert(document.Employee_contact_details_rep.chkregion.length);
                                     if(document.Employee_contact_details_rep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Employee_contact_details_rep.chkdivision.length;i++)
                                                {
                                                        if(document.Employee_contact_details_rep.chkdivision[i].checked==true)
                                                                division= division+document.Employee_contact_details_rep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.Employee_contact_details_rep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.Employee_contact_details_rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Employee_contact_details_rep.chkdivision.checked==true)
                                                {
                                                                division= document.Employee_contact_details_rep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.Employee_contact_details_rep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.Employee_contact_details_rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.Employee_contact_details_rep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Employee_contact_details_rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Employee_contact_details_rep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                           else if(document.Employee_contact_details_rep.rad_off[0].checked)
                    	   {
                    	   url=url+"&officeselected=HO";
                    	   document.Employee_contact_details_rep.officeselected.value="HO";
                    	   }
                           else
                           {
                        	   url=url+"&officeselected=all";
                        	   document.Employee_contact_details_rep.officeselected.value="all";
                           }
                         var cadre_id="";
                      	if(document.Employee_contact_details_rep.chkcadre)
                         {
                           if(document.Employee_contact_details_rep.chkcadre.length )
                            {
                     	    for(i=0;i<document.Employee_contact_details_rep.chkcadre.length;i++)
                               {
                               if(document.Employee_contact_details_rep.chkcadre[i].checked==true)
                                     cadre_id= cadre_id+document.Employee_contact_details_rep.chkcadre[i].value +",";                                                                                                       
                                }
                                if(cadre_id!="")
                                {
                                 cadre_id= cadre_id.substring(0, cadre_id.length-1);
                                // alert("cadre_id===>"+cadre_id);
                                 }
                                 else
                                 {
                                 alert("Please Select Cadre Name!");
                                 return false;
                                 }
                     	   }
                         }
                      	if(document.Employee_contact_details_rep.optselect.checked==false)
                      		{
                      		alert("Please Select Cadre ");
                      		return false;
                      		}
                      	if(document.Employee_contact_details_rep.optselect.checked==true)
                  		{
                  		if(document.Employee_contact_details_rep.cmbsgroup2.selectedIndex==0)
                  			{
                  			alert("Please Select Service Group ");
                  			return false;
                  			}
                  		}
                    //}
                        
                    
               // }
       // }
                        
   // alert(url+cadre_id);
    
    document.Employee_contact_details_rep.action="Emp_cont_det_rep.jsp?"+url+"&cadre_id="+cadre_id;
    document.Employee_contact_details_rep.submit();
    	   
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}
function frmnewsubmit(){

	  
    alert('inside submit');
        var url="";
        
                             
                         if(document.Employee_contact_details_rep.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.Employee_contact_details_rep.chkregion)
                              {
                              
                                        if(document.Employee_contact_details_rep.chkregion.length)
                                        {
                                                  for(i=0;i<document.Employee_contact_details_rep.chkregion.length;i++)
                                                    {
                                                            if(document.Employee_contact_details_rep.chkregion[i].checked==true)
                                                              region= region+document.Employee_contact_details_rep.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.Employee_contact_details_rep.officeselected.value=region;
                                                         //alert(document.Employee_contact_details_rep.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.Employee_contact_details_rep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.Employee_contact_details_rep.chkregion.checked==true)
                                                {
                                                            region= document.Employee_contact_details_rep.chkregion.value ;
                                                            document.Employee_contact_details_rep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.Employee_contact_details_rep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.Employee_contact_details_rep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.Employee_contact_details_rep.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.Employee_contact_details_rep.chkcircle)
                              {
                              
                                       if(document.Employee_contact_details_rep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.Employee_contact_details_rep.chkcircle.length;i++)
                                                    {
                                                            if(document.Employee_contact_details_rep.chkcircle[i].checked==true)
                                                                    circle= circle+document.Employee_contact_details_rep.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.Employee_contact_details_rep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Employee_contact_details_rep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Employee_contact_details_rep.chkcircle.checked==true)
                                                {
                                                            circle= document.Employee_contact_details_rep.chkcircle.value ;
                                                            document.Employee_contact_details_rep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.Employee_contact_details_rep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.Employee_contact_details_rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Employee_contact_details_rep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.Employee_contact_details_rep.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.Employee_contact_details_rep.chkdivision)
                              {
                             //alert(document.Employee_contact_details_rep.chkregion.length);
                                     if(document.Employee_contact_details_rep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Employee_contact_details_rep.chkdivision.length;i++)
                                                {
                                                        if(document.Employee_contact_details_rep.chkdivision[i].checked==true)
                                                                division= division+document.Employee_contact_details_rep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.Employee_contact_details_rep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.Employee_contact_details_rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Employee_contact_details_rep.chkdivision.checked==true)
                                                {
                                                                division= document.Employee_contact_details_rep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.Employee_contact_details_rep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.Employee_contact_details_rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.Employee_contact_details_rep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Employee_contact_details_rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Employee_contact_details_rep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            alert("url----->"+url);
                            
                            }
                           else if(document.Employee_contact_details_rep.rad_off[0].checked)
                    	   {
                    	   url=url+"&officeselected=HO";
                    	   document.Employee_contact_details_rep.officeselected.value="HO";
                    	   }
                           else
                           {
                        	   url=url+"&officeselected=all";
                        	   document.Employee_contact_details_rep.officeselected.value="all";
                           }
                      
                    //}
                        
                    
               // }
       // }
                        
    alert("../../../../../../Emp_bday_spec_month?bmonth="+bmonth+url);
				var bmonth=document.frmValidationSummaryRep.month.value;
				document.Employee_contact_details_rep.method="POST"
				document.Employee_contact_details_rep.action="../../../../../../Emp_bday_spec_month?bmonth="+bmonth+url;
				document.Employee_contact_details_rep.submit();
    	   
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/


}
function getofftype()
    {
      //  alert(regionflag);
           //alert("inside office type");   
        
        if(offtypeflag==true)
        {
               
                 var iframe=document.getElementById("diviframeofftype");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       // var type=document.Employee_contact_details_rep.cmbolevel.options[document.Employee_contact_details_rep.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../EmployeeServiceReport_Interface_New?OLevel=offtype" ;
         //alert(url);
                
        document.Employee_contact_details_rep.cmbofftype.focus();
        var req=getTransport();
        req.open("GET",url,false);        
        req.onreadystatechange=function()
        {
            //requesthandle(req);
            if(req.readyState==4)
            { 
                  if(req.status==200)
                  {  
                   // alert(req.responseText);
                 
                 // document.frames("iframregion").document.body.innerHTML=req.responseText;
                  //(document.frames("iframregion").document.body.innerText);
                    var iframe=document.getElementById("diviframeofftype");
                    iframe.style.visibility='visible';
                    iframe.focus();
                   // alert(navigator.appName);
                   // alert(navigator.appName.indexOf('Microsoft'));
                   
                  
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                    iframe.innerHTML=req.responseText;
                       
                   /*
                  document.Employee_contact_details_rep.cmbcircle.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   */
                   offtypeflag=true;
                  }
            }
        }
        req.send(null);
    }
    

function divisionselectAll()
{

   if(document.Employee_contact_details_rep.chkdivision)
      {
      
            
            for(i=0;i<document.Employee_contact_details_rep.chkdivision.length;i++)
            {
                    document.Employee_contact_details_rep.chkdivision[i].checked=true;
                    
            }
             //regiononchange();
        }
}


function callService()
{	
	if(document.Employee_contact_details_rep.order[1].checked==true)
	{
//		document.Employee_contact_details_rep.service.style.display="block";
		document.getElementById("repabtstract").checked=true;
		hideService('A');
	}
	else
		{
		
		document.getElementById("orderdivabs").style.display='none';
		document.getElementById("service").style.display='none';
		
		}
	
	
	
}

function numbersonly(e,t)
{
     var unicode=e.charCode? e.charCode : e.keyCode;
     if(unicode==13)
      {
        try{t.blur();}catch(e){}
        return true;
      
      }
      if (unicode!=8 && unicode !=9  )
      {
          if (unicode<48||unicode>57 ) 
              return false; 
      }
}

function checkType()
{
	if(document.getElementsByName("yeartype")[0].checked==true)
		document.getElementById("nyear").style.display='none';
	else
		document.getElementById("nyear").style.display='';
	
	document.getElementById("nyear").value="";
}

function selectAll()
{
	var len=document.getElementsByName("service").length;
	for(var i=0;i<len;i++)
		document.getElementsByName("service")[i].checked=true;
	
}
function clearAll()
{
	var len=document.getElementsByName("service").length;
	for(var i=0;i<len;i++)
		document.getElementsByName("service")[i].checked=false;
	
}
function checkValue()
{
		if(!document.getElementById("nyear").value=="")
		{
			var val=document.getElementById("nyear").value;
			val=parseInt(val);
			if(val>35)
				{
					alert("Please Enter year of Service less or equal to 35 ");
					document.getElementById("nyear").value="";
				}
		}
	
}

function download(output)
{
	
	document.getElementById("output").value=output;
	document.frmTypeOfService.submit();
}
function  Caders()
{



    //if(document.Employee_contact_details_rep.optselect[2].checked)
   
     if(document.Employee_contact_details_rep.optselect.checked==true)
    {
      
        var id=document.getElementById("divcadre");
        id.style.display='block';       
    
    
    }

}
function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.Employee_contact_details_rep.cmbsgroup2.options[document.Employee_contact_details_rep.cmbsgroup2.selectedIndex].value;
       
  if(type!=0)
  {
    loadOfficesByType2(type);
  }
  else
  {
     var des=document.getElementById("cmbCadre");
     des.innerHTML='';
  }
}
function loadOfficesByType2(type)
{
    //alert(type);
    var type=document.Employee_contact_details_rep.cmbsgroup2.options[document.Employee_contact_details_rep.cmbsgroup2.selectedIndex].value;
    //startwaiting(document.Employee_contact_details_rep) ;
     //service=null;
  
   //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
   var url="../../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
   //alert(url);
    var req=getTransport();
    req.open("GET",url,false);        
    req.onreadystatechange=function()
    {
    //alert(req.readystate);
        if(req.readyState==4)
        {
        //alert(req.status);
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
    	   {
            divdes.innerHTML=req.responseText;
       divdes.innerText=req.responseText;
    	   }
        else
        	{
        	divdes.innerText=req.responseText;
        	 divdes.innerHTML=req.responseText;
        	}
            
         //alert("show");
        //document.getElementById("cmbRank").visibility='hidden';  
        //document.getElementById("cmbCadre").visibility='hidden';
        }
        }
         
         }
    req.send(null);
}
function cadreselectAll()
{
    if(document.Employee_contact_details_rep.chkcadre)
      {
      
            for(i=0;i<document.Employee_contact_details_rep.chkcadre.length;i++)
            {
                    document.Employee_contact_details_rep.chkcadre[i].checked=true;
                    
            }
        }
}
function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
  
}