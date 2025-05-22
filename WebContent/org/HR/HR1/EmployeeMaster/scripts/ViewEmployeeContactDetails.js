
var regionflag=false;
//var regionflag=true;
var circleflag=true;
var divisionflag=true;
var offtypeflag=true;
var auditflag=true;
var labflag=true;

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

/*
function showofficelevel()
{
    document.contactDetails.optofficelevel[1].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="block";
    
}
*/



 



/*
function showoffice()
{
   
    document.contactDetails.optoffice[1].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    //alert('hai');
    var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
    if(type=='RN') 
    {
        officevisible('block','none','none','none');
        
    }
    else if(type=='CL')     {
       officevisible('block','block','none','none');
    }

    else if(type=='DN')     {
       officevisible('block','block','block','block','block');
    }
    document.contactDetails.cmbcircle.disabled=true;
   // document.contactDetails.cmbofftype.disabled=true;
    document.contactDetails.cmbdivision.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    
    
    regionflag=false;
    circleflag=true;
    offtypeflag=true;
    divisionflag=true;
    
    
    
}
*/
function statusselectAll()
{

   if(document.contactDetails.chkstatus)
      {
      
            
            for(i=0;i<document.contactDetails.chkstatus.length;i++)
            {
                    document.contactDetails.chkstatus[i].checked=true;
                    
            }
           //  regiononchange();
        }
}
function statusclose()
{
	
	 var iframe=document.getElementById("diviframestatus");
    iframe.style.visibility='hidden';
}
function officevisible(r,c,d,o,aud,l)
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
    //alert(aud);
    var ad1=document.getElementById("divaudit");
    ad1.style.display=aud;
    //alert(document.getElementById("divaudit").style.display);
     var lb=document.getElementById("divlab");
    lb.style.display=l;
    
    var rn=document.getElementById("cmbregion");
    rn.style.display=r;
    var cl=document.getElementById("cmbcircle");
    cl.style.display=c;
    var dn=document.getElementById("cmbdivision");
    dn.style.display=d;
    var ot=document.getElementById("cmbofftype");
    ot.style.display=o;
    var ad=document.getElementById("cmbaudit");
    ad.style.display=aud;
     var lb=document.getElementById("cmblab");
    lb.style.display=l;
}




function selectoption2()
{
   
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    
    
    if(document.contactDetails.rad_off[0].checked)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
    }

  if(document.contactDetails.rad_off[1].checked)
  {
   // alert(document.contactDetails.rad_off[1].checked);
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none','none','none');
  }
  
  else if(document.contactDetails.rad_off[2].checked)
  {
   // alert(document.contactDetails.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none','none','none');
  }
  
  else if(document.contactDetails.rad_off[3].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','none','none');
  }
  else if(document.contactDetails.rad_off[4].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','none','none','none','block','none');
      var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  else if(document.contactDetails.rad_off[5].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','none','none','none','block');
      var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  
  
  document.contactDetails.cmbcircle.disabled=true;
   // document.contactDetails.cmbofftype.disabled=true;
    document.contactDetails.cmbdivision.disabled=true;
    document.contactDetails.cmbaudit.disabled=true;
     document.contactDetails.cmblab.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeaudit");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframelab");
    iframe.style.visibility='hidden';
    
    
    regionflag=true;
    circleflag=true;
    offtypeflag=true;
    divisionflag=true;
    auditflag=true;
    labflag=true;
   

}


var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;


/*
function getLevel()
    {
    //alert("show me");
        var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
        //alert('getoffice:'+type);
       officevisible('none','none','none','none'); 
       hideoffice()
      if(type=="")
      {
            var offlevel=document.getElementById("troffice");
            offlevel.style.display="none";
      }
      else
      {
            
             if(type!='HO') 
            {
                 var offlevel=document.getElementById("troffice");
                 offlevel.style.display="block";        
            }
            
             if(type=='DN')
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="block";
              }
              else
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="none";
                document.contactDetails.aggid.checked=false;
              }
           
      }
    }
    */
    
  
function getRegion()
    {
      //  alert(regionflag);
              
       // alert("hai");
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       
    
         
         var url="../../../../../EmployeeDesigAndOfficeLevel?OLevel=region" ;
      //  alert(url);
                
        document.contactDetails.cmbregion.focus();
        var req=getTransport();
        req.open("GET",url,true);          
      
        req.onreadystatechange=function()
        {
           
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
                        iframe.innerHTML=req.responseText;
                        else
                        iframe.innerText=req.responseText;
                        iframe.innerHTML=req.responseText;
                       
                   
                  document.contactDetails.cmbcircle.disabled=false;
                    document.contactDetails.cmbaudit.disabled=false;
                   var iframe=document.getElementById("diviframecircle");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframedivision");
                            iframe.style.visibility='hidden';
                   var iframe=document.getElementById("diviframeaudit");
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
     //   var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
     
      
    
    //  if(document.contactDetails.chkregion)
      //{
      
          //alert(document.contactDetails.chkregion);
           
               if(document.contactDetails.chkregion.length > 0)
              {
              
                 //alert(document.contactDetails.chkregion.length);
             
                            for(i=0;i<document.contactDetails.chkregion.length;i++)
                            {
                              //  alert("document.contactDetails.chkregion[i].checked..."+document.contactDetails.chkregion[i].checked);
                                    if(document.contactDetails.chkregion[i].checked==true)
                                            region= region+document.contactDetails.chkregion[i].value +",";
                                            
                                           // alert("region..."+region);
                                    
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
                                           document.contactDetails.cmbdivision.disabled=false;
                                           document.contactDetails.cmblab.disabled=false;
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../ListofWorkingEmpSRCtlOfficeServ_New_Interface?OLevel=circle&regions="+region;
                                //alert(url);
                              // document.contactDetails.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,true);   
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          {  
                              //             alert("success");
                                         //  var iframe=document.getElementById("diviframecircle");
                                            // if((type=='CL' || type=='DN') && cir=='null' )
                                            if(circleflag==true && cir=='null')
                                            {
                                         //   alert("see these");
                                            var iframe=document.getElementById("diviframecircle");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                                iframe.innerHTML=req.responseText;
                                                else
                                                iframe.innerText=req.responseText;
                                                iframe.innerHTML=req.responseText;
                                            
                                            document.contactDetails.cmbdivision.disabled=false;
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
                                document.contactDetails.cmbdivision.disabled=false;
                                document.contactDetails.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.contactDetails.chkregion.checked==true)
                             {
                                            region= document.contactDetails.chkregion.value ;
                                            //alert(region);
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.contactDetails.cmbdivision.disabled=false;
                                               // return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../ListofWorkingEmpSRCtlOfficeServ_New_Interface?OLevel=circle&regions="+region;
                                        //alert(url);
                                      // document.contactDetails.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,true);  
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  {  
                                                  //alert('success');
                                                   var iframe=document.getElementById("diviframecircle");
                                                    // if((type=='CL' || type=='DN') && cir=='null' )
                                                   if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                       else
                                                       iframe.innerText=req.responseText;
                                                       iframe.innerHTML=req.responseText;
                                                       
                                                    if(cir=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.contactDetails.cmbdivision.disabled=false;
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
                                document.contactDetails.cmbdivision.disabled=false;
                                document.contactDetails.cmbcircle.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      //}
      
   
}


function getDivision(div)
{
        
      //alert('division');
       var region="";
       var circle="";
       var offtype="";
//      if(document.contactDetails.chkregion)
//      {
//         region=document.contactDetails.chkregion.value;
//         region+=",";
//         }   
         
         if(document.contactDetails.chkregion.length>0)
              {
                   for(i=0;i<document.contactDetails.chkregion.length;i++)
                            {
                            //  alert(document.contactDetails.chkregion[i].value);
                                    if(document.contactDetails.chkregion[i].checked==true)
                                            region= region+document.contactDetails.chkregion[i].value +",";
                                          // alert('region...'+region);
                                         
                                    
                            }
                 }
                 else
                 {
                        region=document.contactDetails.chkregion.value;
                        region+=",";
                 }
     
       if(document.contactDetails.chkofftype)
       {
               
         if(document.contactDetails.chkofftype.length)
         {
                    
                  for(i=0;i<document.contactDetails.chkofftype.length;i++)
                  {
                    if(document.contactDetails.chkofftype[i].checked==true)
                     offtype= offtype+"'"+document.contactDetails.chkofftype[i].value+"'"+",";            
                                      
                  }
               
         }
       }
       
      if(document.contactDetails.chkcircle)
      {
      //alert("hello");
            if(document.contactDetails.chkcircle.length)
            {
         //alert("hello1");   
                            for(i=0;i<document.contactDetails.chkcircle.length;i++)
                            {
                                    if(document.contactDetails.chkcircle[i].checked==true)
                                            circle= circle+document.contactDetails.chkcircle[i].value +",";
                                  
                            }
                            
                            
              
         }                  
         if(divisionflag==true && div=='null')
        {
           
               
                 var iframe=document.getElementById("diviframedivision");
                    iframe.style.visibility='visible';
                    iframe.focus();
               // return;
        }
            if(circle!='');  
              circle=circle.substring(0,circle.length-1);
            
            offtype=offtype.substring(0,offtype.length-1);
            region=region.substring(0,region.length-1);
                            
          var url="../../../../../EmployeeDesigAndOfficeLevel?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
          //alert("url"+url);
                                var req=getTransport();
                                req.open("GET",url,true);      
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          { 
                                           // alert("success");
                                            var iframe=document.getElementById("diviframedivision");
                                            
                                            //var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
                                            if(div=='null')
                                            {
                                            //alert("visible");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            }
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                                iframe.innerHTML=req.responseText;
                                                else
                                                iframe.innerText=req.responseText;
                                                iframe.innerHTML=req.responseText;
                                            
                                             // alert(req.responseText);
                                            // iframe.innerHTML=req.responseText;
                                             
                                           divisionflag=true;  
                                          }
                                    }
                                }
                                req.send(null);
               }
                else
                {
                
                                 
                                      offtype=offtype.substring(0,offtype.length-1);
                                      region=region.substring(0,region.length-1);
                                      circle=null; 
                                      var url="../../../../../EmployeeDesigAndOfficeLevel?OLevel=division&regions="+region+"&offtype="+offtype+"&circles="+"nocircle";
                                       // alert(url);
                                      // document.contactDetails.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,true);  
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  { 
                                                    var iframe=document.getElementById("diviframedivision");
                                                    //var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
                                                    if(div=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    }
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                                        iframe.innerHTML=req.responseText;
                                                        else
                                                        iframe.innerText=req.responseText;
                                                        iframe.innerHTML=req.responseText;
                                                    
                                                     //iframe.innerHTML=req.responseText;
                                                     
                                                   divisionflag=true;  
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                           /* else
                            {
                                var iframe=document.getElementById("diviframedivision");
                                iframe.style.visibility='hidden';
                                document.contactDetails.cmbdivision.focus();
                                alert('Please Select a Circle');
                            }*/
                           // alert("circles:"+circle);
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


function regionselectAll()
{

   if(document.contactDetails.chkregion)
      {
      
            
            for(i=0;i<document.contactDetails.chkregion.length;i++)
            {
                    document.contactDetails.chkregion[i].checked=true;
                    
            }
           //  regiononchange();
        }
}


function offtypeselectAll()
{

   if(document.contactDetails.chkofftype)
      {
      
            
            for(i=0;i<document.contactDetails.chkofftype.length;i++)
            {
                    document.contactDetails.chkofftype[i].checked=true;
                    
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
    if(document.contactDetails.chkcircle)
      {
      
            for(i=0;i<document.contactDetails.chkcircle.length;i++)
            {
                    document.contactDetails.chkcircle[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function auditselectAll()
{
    if(document.contactDetails.chkaudit)
      {
      
            for(i=0;i<document.contactDetails.chkaudit.length;i++)
            {
                    document.contactDetails.chkaudit[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function auditclose()
{
    
    var iframe=document.getElementById("diviframeaudit");
    iframe.style.visibility='hidden';
  
}

function labselectAll()
{
    if(document.contactDetails.chklab)
      {
      
            for(i=0;i<document.contactDetails.chklab.length;i++)
            {
                    document.contactDetails.chklab[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function labclose()
{
    var iframe=document.getElementById("diviframelab");
    iframe.style.visibility='hidden';
}


function divisionclose()
{
    
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
  
}
function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
  
}
function rankclose()
{
    
   // var iframe=document.getElementById("divranknew");
  //  iframe.style.visibility='hidden';
  
}
function cadreclose()
{
    
  //  var iframe=document.getElementById("divcadrenew");
  //  iframe.style.visibility='hidden';
  
}

function divisionselectAll()
{
    if(document.contactDetails.chkdivision)
      {
      
            for(i=0;i<document.contactDetails.chkdivision.length;i++)
            {
                    document.contactDetails.chkdivision[i].checked=true;
                    
            }
        }
}

function designationselectAll()
{
    if(document.contactDetails.chkdesig)
      {
      
            for(i=0;i<document.contactDetails.chkdesig.length;i++)
            {
                    document.contactDetails.chkdesig[i].checked=true;
                    
            }
        }
}
function rankselectAll()
{
    if(document.contactDetails.chkrank)
      {
      
            for(i=0;i<document.contactDetails.chkrank.length;i++)
            {
                    document.contactDetails.chkrank[i].checked=true;
                    
            }
        }
}
function cadreselectAll()
{
    if(document.contactDetails.chkcadre)
      {
      
            for(i=0;i<document.contactDetails.chkcadre.length;i++)
            {
                    document.contactDetails.chkcadre[i].checked=true;
                    
            }
        }
}

/*
function regiononchange()
{
     var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN')
    {
    getDivision('division');
    }

}
*/
function oftypeonchange()
{

     if(document.contactDetails.rad_off[3].checked)
     {
       getDivision('division');
     }
     /*
     var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }
   */
}



function hidedisignation()
{
    document.contactDetails.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdest");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbDesignation");
    offlevel.style.visibility="hidden";
    document.contactDetails.cmbsgroup.value="0";
}

function showdesignation()
{
   document.contactDetails.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
{
document.getElementById("desigblock").style.display='block';
//alert("getDesisg");
   var type=document.contactDetails.cmbsgroup.options[document.contactDetails.cmbsgroup.selectedIndex].value;
   //alert(type);
   if(type!=0)
    {
    //alert("inside");
           // var din=document.getElementById("divdest");
           // din.style.visibility="visible";
           // document.contactDetails.cmbDesignation.style.visibility="visible";
            loadOfficesByType(type);
    }
   else
    {
             var des=document.getElementById("cmbDesignation");
             des.innerHTML='';
            // var din=document.getElementById("divdest");
           //  din.style.visibility="hidden";
           //  document.contactDetails.cmbDesignation.style.visibility="hidden";
     }
}
    

    
    
    function loadOfficesByType(type)
    {
        //alert(type);
       // var type=document.contactDetails.cmbsgroup.options[document.contactDetails.cmbsgroup.selectedIndex].value;
        //startwaiting(document.contactDetails) ;
         //service=null;
      
       //var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
       //alert(url);
       var req=getTransport();
       req.open("GET",url,true);          
     
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
             //alert("show");
            //document.getElementById("cmbRank").visibility='hidden';  
            //document.getElementById("cmbCadre").visibility='hidden';
            }
            }
             
             }
        req.send(null);
    }
    
function  loadDesignation(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
               
                var des=document.getElementById("cmbDesignation");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                // stopwaiting(document.contactDetails);
                if(flag=="failure")
                {
                    alert("No Designation exists under this level");
                }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Designation--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}



function frmsubmit()
{
  
    //alert('inside submit');
        var url="";
        
        var count=0;
    	var stdata="";
    	  
    	        for(i=0;i<document.contactDetails.chkstatus.length;i++)
    	        	
    	        {
    	        	
    	        	if(document.contactDetails.chkstatus[i].checked==true)
    	        	{
    	        		count++;
    	        		stdata+="'"+document.contactDetails.chkstatus[i].value+"',";
    	        	}
    	                
    	        }
    	       
    	   
    	if(count==0)
    	{
    		
    		alert("select status");
    		 return;
    	}        
    	
    	 url=url+"status="+stdata;
    	 if(document.contactDetails.rad_off[0].checked)
    	 {
    		 url=url+"&officeselected=5000";
    	 }
    	 else if(document.contactDetails.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.contactDetails.chkregion)
                              {
                              
                                        if(document.contactDetails.chkregion.length)
                                        {
                                                  for(i=0;i<document.contactDetails.chkregion.length;i++)
                                                    {
                                                            if(document.contactDetails.chkregion[i].checked==true)
                                                              region= region+document.contactDetails.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.contactDetails.officeselected.value=region;
                                                         //alert(document.contactDetails.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.contactDetails.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.contactDetails.chkregion.checked==true)
                                                {
                                                            region= document.contactDetails.chkregion.value ;
                                                            document.contactDetails.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.contactDetails.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.contactDetails.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.contactDetails.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.contactDetails.chkcircle)
                              {
                              
                                       if(document.contactDetails.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.contactDetails.chkcircle.length;i++)
                                                    {
                                                            if(document.contactDetails.chkcircle[i].checked==true)
                                                                    circle= circle+document.contactDetails.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.contactDetails.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.contactDetails.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.contactDetails.chkcircle.checked==true)
                                                {
                                                            circle= document.contactDetails.chkcircle.value ;
                                                            document.contactDetails.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.contactDetails.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.contactDetails.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.contactDetails.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.contactDetails.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.contactDetails.chkdivision)
                              {
                             //alert(document.contactDetails.chkregion.length);
                                     if(document.contactDetails.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.contactDetails.chkdivision.length;i++)
                                                {
                                                        if(document.contactDetails.chkdivision[i].checked==true)
                                                                division= division+document.contactDetails.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.contactDetails.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.contactDetails.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.contactDetails.chkdivision.checked==true)
                                                {
                                                                division= document.contactDetails.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.contactDetails.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.contactDetails.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.contactDetails.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.contactDetails.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.contactDetails.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                         else if(document.contactDetails.rad_off[4].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var audit="";
                            if(document.contactDetails.chkaudit)
                              {
                             
                                       if(document.contactDetails.chkaudit.length)
                                        {
                                                 //alert("audit......leng");
                                                    for(i=0;i<document.contactDetails.chkaudit.length;i++)
                                                    {
                                                            if(document.contactDetails.chkaudit[i].checked==true)
                                                                    audit= audit+document.contactDetails.chkaudit[i].value +",";
                                                                    //alert("audit");  
                                                    }
                                                    if(audit!="")
                                                    {
                                                   // alert(audit);
                                                        audit=audit.substring(0,audit.length-1);
                                                         url=url+"&officeselected="+audit;
                                                         document.contactDetails.officeselected.value=audit;
                                                         
                                                    }
                                                    else
                                                    {
                                                           alert('Select the audit wing');
                                                            document.contactDetails.cmbaudit.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                       
                                                if(document.contactDetails.chkaudit.checked==true)
                                                {
                                                            audit= document.contactDetails.chkaudit.value ;
                                                            document.contactDetails.officeselected.value=audit;
                                                              url=url+"&officeselected="+audit;
                                                         document.contactDetails.officeselected.value=audit;
                                                            
                                                }
                                                 else
                                                    {
                                                           alert('Select the Audit..');
                                                            document.contactDetails.cmbaudit.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Audit...');
                                       try{
                                        document.contactDetails.cmbaudit.focus(); 
                                        }
                                        catch(e){
                                        document.contactDetails.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                         else if(document.contactDetails.rad_off[5].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var lab="";
                            if(document.contactDetails.chklab)
                              {
                             //alert(document.contactDetails.chkregion.length);
                                     if(document.contactDetails.chklab.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.contactDetails.chklab.length;i++)
                                                {
                                                        if(document.contactDetails.chklab[i].checked==true)
                                                                lab= lab+document.contactDetails.chklab[i].value +",";
                                                        
                                                }
                                                
                                                if(lab!="")
                                                {
                                                    lab=lab.substring(0,lab.length-1);
                                                     url=url+"&officeselected="+lab;
                                                     document.contactDetails.officeselected.value=lab;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the lab.');
                                                        document.contactDetails.cmblab.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.contactDetails.chklab.checked==true)
                                                {
                                                                lab= document.contactDetails.chkdivision.value;
                                                                    //lab=lab.substring(0,lab.length-1);
                                                                 url=url+"&officeselected="+lab;
                                                                 document.contactDetails.officeselected.value=lab;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the lab..');
                                                        document.contactDetails.cmblab.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the lab...');
                                       try{
                                        document.contactDetails.cmblab.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.contactDetails.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.contactDetails.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                         else
                         {
                        	 alert("Select office ");
                        	 return;
                         }
                    //}
                        
                    
               // }
       // }
    	 if(document.contactDetails.optselect[0].checked)
    	 {
    		 url=url+"&optselect=Dest";
    		 if(document.contactDetails.cmbsgroup.value==0)
    		 {
    			 alert("Select Designation");
    			 return;
    		 }
    		 else
    		 { 
    			 var flag=true;
    			 var data="";
    			 if(document.contactDetails.chkdesig.length>0)
    			 {
    				 for(i=0;i<document.contactDetails.chkdesig.length;i++)
    				 {
    					 if(document.contactDetails.chkdesig[i].checked==true)
    					 {
    						 flag=false;
    						 data=data+document.contactDetails.chkdesig[i].value+",";
    					 }
    				 }
    			
    			 }
    			 if(flag)
    			 {
    				 alert("Select Designation");
        			 return false;
    			 }
    			 url=url+"&dsiid="+data;
    		 }
    			 
    	 }
    	 else if(document.contactDetails.optselect[1].checked)
    	 {
    		 url=url+"&optselect=Rank";
    		 if(document.contactDetails.cmbsgroup1.value==0)
    		 {
    			 alert("Select Rank");
    			 return;
    		 }
    		 else
    		 {
    			 
    			 var flag=true;
    			 var data="";
    			 if(document.contactDetails.chkrank.length>0)
    			 {
    				 for(i=0;i<document.contactDetails.chkrank.length;i++)
    				 {
    					 if(document.contactDetails.chkrank[i].checked==true)
    					 {
    						 flag=false;
    						 data=data+document.contactDetails.chkrank[i].value+",";
    					 }
    				 }
    			
    			 }
    			 if(flag)
    			 {
    				 alert("Select Rank");
        			 return false;
    			 }
    			 url=url+"&dsiid="+data;
    		 
    		 }
    	 }
    	 else if(document.contactDetails.optselect[2].checked)
    	 {
    		 url=url+"&optselect=Cadr";
    		 if(document.contactDetails.cmbsgroup2.value==0)
    		 {
    			 alert("Select Cadre");
    			 return;
    		 }
    		 else
    		 {
    			 var flag=true;
    			 var data="";
    			 if(document.contactDetails.chkcadre.length>0)
    			 {
    				 for(i=0;i<document.contactDetails.chkcadre.length;i++)
    				 {
    					 if(document.contactDetails.chkcadre[i].checked==true)
    					 {
    						 flag=false;
    						 data=data+document.contactDetails.chkcadre[i].value+",";
    					 }
    				 }
    			
    			 }
    			 if(flag)
    			 {
    				 alert("Select Cadre");
        			 return false;
    			 }
    			 url=url+"&dsiid="+data;
    			
    		 }
    	 }
    	 else
    	 {
    		 alert("Selection of Designation / Rank / Cadre");
    		 return;
    	 }
      
        //select the output type
        if(document.contactDetails.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.contactDetails.outputtype.value='pdf';
        }
        else if(document.contactDetails.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.contactDetails.outputtype.value='excel';
        }
        else if(document.contactDetails.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.contactDetails.outputtype.value='html';
        }
        var add='';
        if(document.contactDetails.address[0].checked)
        	add='yes';
        else
        	add='no';
   url=url+"&address="+add;
   // alert(url);
    document.contactDetails.action="../../../../../pay_view_emp_contact_details?"+url;
    document.contactDetails.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}






//////////////////////////// for next purpose ///////////////////////
///////////////////////  for designation,cadre,rank   ///////////////////////

function sellectall()
{
   
  //  if(document.contactDetails.optselect[1].checked)
  if(document.contactDetails.optselectgrp[1].checked)
    {
      //alert("1");
      //alert(document.contactDetails.optselect[1].checked);
      var id=document.getElementById("divselect");
      id.style.display='block';          
                
    }    
                      
    else
    {
      var id=document.getElementById("divselect");
      id.style.display='none';      
     
     }
        
}

function hidedesig()
{
//alert("here");
   //if(document.contactDetails.optselect[0].checked)
   if(document.contactDetails.optselectgrp[0].checked)
   {
  // alert('sec')
      var id1=document.getElementById("divselect");
      id1.style.display='none';
      var id2=document.getElementById("divdest");
      id2.style.display='none';
      var id3=document.getElementById("divrank");
      id3.style.display='none';
      var id4=document.getElementById("divcadre");
      id4.style.display='none';
    document.getElementById("desigblock").style.display='none';
      
   }
   
   else
   {
     var id1=document.getElementById("divselect");
      id1.style.display='block';
      var id2=document.getElementById("divdest");
      id2.style.display='block';
      var id3=document.getElementById("divrank");
      id3.style.display='block';
      var id4=document.getElementById("divcadre");
      id4.style.display='block';
      document.getElementById("desigblock").style.display='block';
   }

}

    
function  selectoption1()
{
//alert('test');


    
    if(document.contactDetails.optselect[0].checked)
    {
      
        var id=document.getElementById("divdest");
        id.style.display='block';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
        
        var id=document.getElementById("divcadre");
        id.style.display='none';
      
    }
    
   
    else if(document.contactDetails.optselect[1].checked)
    {
      
         var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='block';
         var id=document.getElementById("divcadre");
        id.style.display='none';     
       
    
    }
   
    else if(document.contactDetails.optselect[2].checked)
    {
       
        var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
       
        var id=document.getElementById("divcadre");
        id.style.display='block';       
    
    
    }

}
        
     
function checkGroup()
{
        
  var type=document.contactDetails.cmbsgroup.options[document.contactDetails.cmbsgroup.selectedIndex].value;
  if(type==0)
   {
     alert('Select Service Group');
     document.contactDetails.cmbsgroup.focus();
     return false;
   }
}    



function getDesignation1()
{
document.getElementById("desigblock").style.display='block';
  var type=document.contactDetails.cmbsgroup1.options[document.contactDetails.cmbsgroup1.selectedIndex].value;
  //alert(type)     
  if(type!=0)
  {
    loadOfficesByType1(type);
  }
  else
  {
     var des=document.getElementById("cmbRank");
     des.innerHTML='';
  }
}




 function loadOfficesByType1(type)
    {
        //alert(type);
        var type=document.contactDetails.cmbsgroup1.options[document.contactDetails.cmbsgroup1.selectedIndex].value;
        //startwaiting(document.contactDetails) ;
         //service=null;
      
       //var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../DesigCadRankServ_New?Command=Rank&cmbsgroup=" + type ;
       //alert(url);
       var req=getTransport();
       req.open("GET",url,true);          
     
       req.onreadystatechange=function()
       {
          
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
             //alert("show");
            //document.getElementById("cmbRank").visibility='hidden';  
            //document.getElementById("cmbCadre").visibility='hidden';
            }
            }
             
             }
        req.send(null);
    }

function  loadDesignation1(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbRank");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.contactDetails);
                if(flag=="failure")
                {
                    alert("No Rank exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Rank--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}

function checkGroup1()
{
        
        var type=document.contactDetails.cmbsgroup1.options[document.contactDetails.cmbsgroup1.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.contactDetails.cmbsgroup1.focus();
            return false;
        }
}

function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.contactDetails.cmbsgroup2.options[document.contactDetails.cmbsgroup2.selectedIndex].value;
       
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
        var type=document.contactDetails.cmbsgroup2.options[document.contactDetails.cmbsgroup2.selectedIndex].value;
        //startwaiting(document.contactDetails) ;
         //service=null;
      
       //var url="../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
       //alert(url);
       var req=getTransport();
       req.open("GET",url,true);          
     
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
             //alert("show");
            //document.getElementById("cmbRank").visibility='hidden';  
            //document.getElementById("cmbCadre").visibility='hidden';
            }
            }
             
             }
        req.send(null);
    }

function  loadDesignation2(req)
{
     if(req.readyState==4)
        {
          if(req.status==200)
          { 
                //alert(req);
                var des=document.getElementById("cmbCadre");
                var i=0;
                var response=req.responseXML.getElementsByTagName("response")[0];
                var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
                des.innerHTML="";
                
                 stopwaiting(document.contactDetails);
                if(flag=="failure")
                {
                    alert("No Rank exists under this level");
                }
                else if(flag=="sessionout")
            {
                alert('Session is closed');
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                self.close();
                return;
            }
                else
                {   
                
                    var value=response.getElementsByTagName("option");
                    var option=document.createElement("OPTION");
                    option.text="--Select Cadre--";
                    option.value="0";
                    try
                    {
                        des.add(option);
                    }catch(errorObject)
                    {
                        des.add(option,null);
                    }
                    for(var i=0;i<value.length;i++)
                    {
                        var tmpoption=value.item(i);
                        var id=tmpoption.getElementsByTagName("id")[0].firstChild.nodeValue;
                        var name=tmpoption.getElementsByTagName("name")[0].firstChild.nodeValue;
                        var option=document.createElement("OPTION");
                          option.text=name;
                          option.value=id;
                          //Making Browser Independent
                          try
                          {
                              des.add(option);
                          }
                          catch(errorObject)
                          {
                              des.add(option,null);
                          }
                    }
                
                }
        
        }
        
    }
    

}

function checkGroup2()
{
        
  var type=document.contactDetails.cmbsgroup2.options[document.contactDetails.cmbsgroup2.selectedIndex].value;
  if(type==0)
  {
     alert('Select Service Group');
     document.contactDetails.cmbsgroup2.focus();
     return false;
  }
}

function desigChange()
{
var desig="";
//alert('designationchange');
if(document.contactDetails.chkdesig)
      {
     // alert("1");
               if(document.contactDetails.chkdesig.length)
              {
              //alert("2");
             
                            for(i=0;i<document.contactDetails.chkdesig.length;i++)
                            {
                            
                                    if(document.contactDetails.chkdesig[i].checked==true)
                                        { 
                                           //alert("inside");
                                            desig=desig+document.contactDetails.chkdesig[i].value +",";
                                         }
                            }
                            
                            if(desig!="")
                            {
                            //alert("ids:"+desig);
                            desig=desig.substring(0,desig.length-1);
                            var url="../../../../../EmployeeDetailsReportServ_New_Interface?desigs="+desig;
                            }
               }
        }  
        
}

function rankChange()
{
var rank="";
//alert('rank');
if(document.contactDetails.chkdesig)
      {
      //alert("1");
               if(document.contactDetails.chkrank.length)
              {
        //      alert("2");
             
                            for(i=0;i<document.contactDetails.chkrank.length;i++)
                            {
                            
                                    if(document.contactDetails.chkrank[i].checked==true)
                                        { 
                                           //alert("inside");
                                            rank=rank+document.contactDetails.chkrank[i].value +",";
                                         }
                            }
                            
                            if(rank!="")
                            {
                            //alert("ids:"+rank);
                            rank=rank.substring(0,rank.length-1);
                            var url="../../../../../EmployeeDetailsReportServ_New_Interface?ranks="+rank;
                            }
               }
        }  
        
}
    
function cadreChange()
{
var cadre="";
//alert('cadre');
if(document.contactDetails.chkcadre)
      {
      //alert("1");
               if(document.contactDetails.chkcadre.length)
              {
             // alert("2");
             
                            for(i=0;i<document.contactDetails.chkcadre.length;i++)
                            {
                            
                                    if(document.contactDetails.chkcadre[i].checked==true)
                                        { 
                                           //alert("inside");
                                            cadre=cadre+document.contactDetails.chkcadre[i].value +",";
                                         }
                            }
                            
                            if(cadre!="")
                            {
                            //alert("ids:"+cadre);
                            cadre=cadre.substring(0,cadre.length-1);
                            var url="../../../../../EmployeeDetailsReportServ_New_Interface?cadres="+cadre;
                            }
               }
        }  
        
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
       // var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../EmployeeDetailsReportServ_New_Interface?OLevel=offtype" ;
         //alert(url);
                
        document.contactDetails.cmbofftype.focus();
        var req=getTransport();
        req.open("GET",url,true);     
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
                  document.contactDetails.cmbcircle.disabled=false;
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
    


function getAudit(aud)
{

       var region="";
                if(document.contactDetails.chkregion.length > 0)
                {
                
                            for(i=0;i<document.contactDetails.chkregion.length;i++)
                            {
                                    if(document.contactDetails.chkregion[i].checked==true)
                                            region= region+document.contactDetails.chkregion[i].value +",";
                            }
                            
                            if(region!="")
                            {
                                 if(auditflag==true && aud=='null')
                                {
                                         var iframe=document.getElementById("diviframeaudit");
                                         iframe.style.visibility='visible';
                                         iframe.focus();
                                         document.contactDetails.cmbdivision.disabled=false;
                                         document.contactDetails.cmbcircle.disabled=false; 
                                }
                                region=region.substring(0,region.length-1);
                                //var url="../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../EmployeeDesigAndOfficeLevel?OLevel=audit&regions="+region;
                              // document.contactDetails.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,true);         
                                req.onreadystatechange=function()
                                {
                              
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                   
                                          if(req.status==200)
                                          { 
                                         
                                            if(auditflag==true && aud=='null')
                                            {
                                           // alert("auditflag");
                                            var iframe=document.getElementById("diviframeaudit");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                                iframe.innerHTML=req.responseText;
                                                else
                                                iframe.innerText=req.responseText;
                                                iframe.innerHTML=req.responseText;
                                            
                                            document.contactDetails.cmbdivision.disabled=false;
                                              var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                            // alert(req.responseText);
                                            
                                            }
                                            
                                             iframe.innerHTML=req.responseText;
                                              circleflag=true;
                                            
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframeaudit");
                                iframe.style.visibility='hidden';
                                document.contactDetails.cmbdivision.disabled=false;
                                document.contactDetails.cmbaudit.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
           
                             if(document.contactDetails.chkregion.checked==true)
                             {
                                            region= document.contactDetails.chkregion.value ;
                                    
                                    
                                         if(auditflag==true && aud=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframeaudit");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.contactDetails.cmbdivision.disabled=false;
                                        }
                                        var url="../../../../../EmployeeDesigAndOfficeLevel?OLevel=audit&regions="+region;
                                        var req=getTransport();
                                        req.open("GET",url,true);      
                                        req.onreadystatechange=function()
                                        {
                                            if(req.readyState==4)
                                            { 
                                        
                                                  if(req.status==200)
                                                  {  
                                                
                                                   var iframe=document.getElementById("diviframeaudit");
                                                  
                                                   if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                       else
                                                       iframe.innerText=req.responseText;
                                                       iframe.innerHTML=req.responseText;
                                                     
                                                    if(aud=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                   /* document.contactDetails.cmbaudit.disabled=false;
                                                     var iframe=document.getElementById("diviframeaudit");
                                                     iframe.style.visibility='hidden';*/
                                                    
                                                    
                                                    }
                                                    auditflag=true;
                                                    
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                               
                            
                                var iframe=document.getElementById("diviframeaudit");
                                iframe.style.visibility='hidden';
                                document.contactDetails.cmbdivision.disabled=false;
                                document.contactDetails.cmbaudit.focus();
                                alert('Please Select a Region');
                            }

            
            
            
            
            }
            
      //}
      
   
}

function getLab(lb)
    {
        
       // alert('division');
       var region="";
       var circle="";

       
       
     // if(document.contactDetails.chkregion)
     // {
      
         
           
               if(document.contactDetails.chkregion.length)
              {
              
                
             
                            for(i=0;i<document.contactDetails.chkregion.length;i++)
                            {
                             
                                    if(document.contactDetails.chkregion[i].checked==true)
                                            region= region+document.contactDetails.chkregion[i].value +",";
                                            //alert('region...'+region);
                                         
                                    
                            }
                 }
       // }
       
     
       
      if(document.contactDetails.chkcircle)
      {
      
            if(document.contactDetails.chkcircle.length)
            {
            
                            for(i=0;i<document.contactDetails.chkcircle.length;i++)
                            {
                                    if(document.contactDetails.chkcircle[i].checked==true)
                                            circle= circle+document.contactDetails.chkcircle[i].value +",";
                                  
                            }
                            if(circle!="")
                            {
                               
                               
                                 if(divisionflag==true && lb=='null')
                                {
                                   
                                       
                                         var iframe=document.getElementById("diviframelab");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                           
                                       // return;
                                }
                                 
                                circle=circle.substring(0,circle.length-1);
                                region=region.substring(0,region.length-1);
                               // var url="../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                              
                              var url="../../../../../EmployeeDesigAndOfficeLevel?OLevel=lab&circles="+circle+"&regions="+region;
                             //alert(url);
                              
                              // document.contactDetails.cmbregion.focus();
                                var req=getTransport();
                                req.open("GET",url,true);       
                                req.onreadystatechange=function()
                                {
                                    //requesthandle(req);
                                    if(req.readyState==4)
                                    { 
                                          if(req.status==200)
                                          { 
                                           // alert("success");
                                            var iframe=document.getElementById("diviframelab");
                                            
                                            //var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
                                            if(lb=='null')
                                            {
                                            //alert("visible");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            }
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                                iframe.innerHTML=req.responseText;
                                                else
                                                iframe.innerText=req.responseText;
                                                iframe.innerHTML=req.responseText;
                                            
                                             // alert(req.responseText);
                                            // iframe.innerHTML=req.responseText;
                                             
                                           divisionflag=true;  
                                          }
                                    }
                                }
                                req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframelab");
                                iframe.style.visibility='hidden';
                                document.contactDetails.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.contactDetails.chkcircle.checked==true)
                             {
                                            circle= document.contactDetails.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && lb=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframelab");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                              //  return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                      //  var url="../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                                      region=region.substring(0,region.length-1);
                                      //alert(region);
                                      var url="../../../../../EmployeeDetailsReportServ_New_Interface?OLevel=lab&circles="+circle;
                                      //alert(url);
                                      // document.contactDetails.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,true);       
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  { 
                                                    var iframe=document.getElementById("diviframelab");
                                                    //var type=document.contactDetails.cmbolevel.options[document.contactDetails.cmbolevel.selectedIndex].value;
                                                    if(lb=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    }
                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
                                                        iframe.innerHTML=req.responseText;
                                                        else
                                                        iframe.innerText=req.responseText;
                                                        iframe.innerHTML=req.responseText;
                                                    
                                                     //iframe.innerHTML=req.responseText;
                                                     
                                                   divisionflag=true;  
                                                  }
                                            }
                                        }
                                        req.send(null);
                            }
                            else
                            {
                                var iframe=document.getElementById("diviframelab");
                                iframe.style.visibility='hidden';
                                document.contactDetails.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                
                }
            
      }
   
}

function getStatus()
{
	
        
    var url="../../../../../EmployeeDesigAndOfficeLevel?OLevel=Status";


    document.contactDetails.cmbstatus.focus();
  
    var req=getTransport();
   
    req.open("GET",url,true);        
    req.onreadystatechange=function()
    {
       // requesthandle(req);
    	//alert("enter");
        if(req.readyState==4)
        { 
            if(req.status==200)
            {  
               
                
                
            	var iframe=document.getElementById("diviframestatus");
               
                iframe.style.visibility='visible';
                iframe.focus();
                // alert(navigator.appName);
                // alert(navigator.appName.indexOf('Microsoft'));
                
                
                if(navigator.appName.indexOf('Microsoft')!=-1)
                iframe.innerHTML=req.responseText;
                else
                iframe.innerText=req.responseText;
                iframe.innerHTML=req.responseText;
                statusflag=true;
            }
        }
    }
    req.send(null);
}

