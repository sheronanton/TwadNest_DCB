//alert('detail');

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
    
    
    if(document.HRM_Contact_employee_details.rad_off[0].checked)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
    }
    if(document.HRM_Contact_employee_details.rad_off[6].checked)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
    }
  if(document.HRM_Contact_employee_details.rad_off[1].checked)
  {
   // alert(document.HRM_Contact_employee_details.rad_off[1].checked);
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none','none','none');
  }
  
  else if(document.HRM_Contact_employee_details.rad_off[2].checked)
  {
   // alert(document.HRM_Contact_employee_details.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none','none','none');
  }
  
  else if(document.HRM_Contact_employee_details.rad_off[3].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','none','none');
  }
  else if(document.HRM_Contact_employee_details.rad_off[4].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','none','none','none','block','none');
     var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  else if(document.HRM_Contact_employee_details.rad_off[5].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','none','none','none','block');
     var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  
  
  document.HRM_Contact_employee_details.cmbcircle.disabled=true;
   // document.HRM_Contact_employee_details.cmbofftype.disabled=true;
    document.HRM_Contact_employee_details.cmbdivision.disabled=true;
    document.HRM_Contact_employee_details.cmbaudit.disabled=true;
     document.HRM_Contact_employee_details.cmblab.disabled=true;
    
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
                
        document.HRM_Contact_employee_details.cmbregion.focus();
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            //requesthandle(req);
        //	alert(req);
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
                   
                 // alert(req.responseText);
                    if(navigator.appName.indexOf('Microsoft')!=-1)
                        iframe.innerHTML=req.responseText;
                    else
                        iframe.innerText=req.responseText;
                    iframe.innerHTML=req.responseText;
                   
                  document.HRM_Contact_employee_details.cmbcircle.disabled=false;
                  document.HRM_Contact_employee_details.cmbaudit.disabled=false;
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
     // alert("inside getcircle");
        
       var region="";
     //   var type=document.HRM_Contact_employee_details.cmbolevel.options[document.HRM_Contact_employee_details.cmbolevel.selectedIndex].value;
     
      
    
     // if(document.HRM_Contact_employee_details.chkregion)
      //{
      
         // alert(document.HRM_Contact_employee_details.chkregion);
           
               if(document.HRM_Contact_employee_details.chkregion.length > 0)
              {
              
                // alert(document.HRM_Contact_employee_details.chkregion.length);
             
                            for(i=0;i<document.HRM_Contact_employee_details.chkregion.length;i++)
                            {
                              //  alert("document.HRM_Contact_employee_details.chkregion[i].checked..."+document.HRM_Contact_employee_details.chkregion[i].checked);
                                    if(document.HRM_Contact_employee_details.chkregion[i].checked==true)
                                            region= region+document.HRM_Contact_employee_details.chkregion[i].value +",";
                                            
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
                                           document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                           document.HRM_Contact_employee_details.cmblab.disabled=false;
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                             //   alert(url);
                              // document.HRM_Contact_employee_details.cmbregion.focus();
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
                                            document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                             document.HRM_Contact_employee_details.cmblab.disabled=false;
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
                                document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                document.HRM_Contact_employee_details.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.HRM_Contact_employee_details.chkregion.checked==true)
                             {
                                            region= document.HRM_Contact_employee_details.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                                    document.HRM_Contact_employee_details.cmblab.disabled=false;
                                                //return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                                        
                                      // document.HRM_Contact_employee_details.cmbregion.focus();
                                        var req=getTransport();
                                        req.open("GET",url,true);        
                                        req.onreadystatechange=function()
                                        {
                                            //requesthandle(req);
                                            if(req.readyState==4)
                                            { 
                                                  if(req.status==200)
                                                  {  
                                                   var iframe=document.getElementById("diviframecircle");
                                                   if(navigator.appName.indexOf('Microsoft')!=-1)
                                                       iframe.innerHTML=req.responseText;
                                                         else
                                                       iframe.innerText=req.responseText;
                                                   iframe.innerHTML=req.responseText;
                                                    // if((type=='CL' || type=='DN') && cir=='null' )
                                                    if(cir=='null')
                                                    {
                                                    
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                                    
                                                    document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                                     document.HRM_Contact_employee_details.cmblab.disabled=false;
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
                                document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                document.HRM_Contact_employee_details.cmbcircle.focus();
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
//      if(document.HRM_Contact_employee_details.chkregion)
//      {
//         region=document.HRM_Contact_employee_details.chkregion.value;
//         region+=",";
//         }   
         
         if(document.HRM_Contact_employee_details.chkregion.length>0)
              {
                   for(i=0;i<document.HRM_Contact_employee_details.chkregion.length;i++)
                            {
                            //  alert(document.HRM_Contact_employee_details.chkregion[i].value);
                                    if(document.HRM_Contact_employee_details.chkregion[i].checked==true)
                                            region= region+document.HRM_Contact_employee_details.chkregion[i].value +",";
                                          // alert('region...'+region);
                                         
                                    
                            }
                 }
                 else
                 {
                        region=document.HRM_Contact_employee_details.chkregion.value;
                        region+=",";
                 }
     
       if(document.HRM_Contact_employee_details.chkofftype)
       {
               
         if(document.HRM_Contact_employee_details.chkofftype.length)
         {
                    
                  for(i=0;i<document.HRM_Contact_employee_details.chkofftype.length;i++)
                  {
                    if(document.HRM_Contact_employee_details.chkofftype[i].checked==true)
                     offtype= offtype+"'"+document.HRM_Contact_employee_details.chkofftype[i].value+"'"+",";            
                                      
                  }
               
         }
       }
       
      if(document.HRM_Contact_employee_details.chkcircle)
      {
      //alert("hello");
            if(document.HRM_Contact_employee_details.chkcircle.length)
            {
         //alert("hello1");   
                            for(i=0;i<document.HRM_Contact_employee_details.chkcircle.length;i++)
                            {
                                    if(document.HRM_Contact_employee_details.chkcircle[i].checked==true)
                                            circle= circle+document.HRM_Contact_employee_details.chkcircle[i].value +",";
                                  
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
                            
          var url="../../../../../../EmployeeDPReport_office?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
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
                                            
                                            //var type=document.HRM_Contact_employee_details.cmbolevel.options[document.HRM_Contact_employee_details.cmbolevel.selectedIndex].value;
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
                                      var url="../../../../../../EmployeeDPReport_office?OLevel=division&regions="+region+"&offtype="+offtype+"&circles="+"nocircle";
                                       // alert(url);
                                      // document.HRM_Contact_employee_details.cmbregion.focus();
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
                                                    //var type=document.HRM_Contact_employee_details.cmbolevel.options[document.HRM_Contact_employee_details.cmbolevel.selectedIndex].value;
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
                                document.HRM_Contact_employee_details.cmbdivision.focus();
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

   if(document.HRM_Contact_employee_details.chkregion)
      {
      
            
            for(i=0;i<document.HRM_Contact_employee_details.chkregion.length;i++)
            {
                    document.HRM_Contact_employee_details.chkregion[i].checked=true;
                    
            }
           //  regiononchange();
        }
}


function offtypeselectAll()
{

   if(document.HRM_Contact_employee_details.chkofftype)
      {
      
            
            for(i=0;i<document.HRM_Contact_employee_details.chkofftype.length;i++)
            {
                    document.HRM_Contact_employee_details.chkofftype[i].checked=true;
                    
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
    if(document.HRM_Contact_employee_details.chkcircle)
      {
      
            for(i=0;i<document.HRM_Contact_employee_details.chkcircle.length;i++)
            {
                    document.HRM_Contact_employee_details.chkcircle[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function divisionclose()
{
    
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
  
}

function divisionselectAll()
{
    if(document.HRM_Contact_employee_details.chkdivision)
      {
      
            for(i=0;i<document.HRM_Contact_employee_details.chkdivision.length;i++)
            {
                    document.HRM_Contact_employee_details.chkdivision[i].checked=true;
                    
            }
        }
}
function auditselectAll()
{
    if(document.HRM_Contact_employee_details.chkaudit)
      {
      
            for(i=0;i<document.HRM_Contact_employee_details.chkaudit.length;i++)
            {
                    document.HRM_Contact_employee_details.chkaudit[i].checked=true;
                    
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
    if(document.HRM_Contact_employee_details.chklab)
      {
      
            for(i=0;i<document.HRM_Contact_employee_details.chklab.length;i++)
            {
                    document.HRM_Contact_employee_details.chklab[i].checked=true;
                    
            }
           // circleonchange();
        }
}

function labclose()
{
    var iframe=document.getElementById("diviframelab");
    iframe.style.visibility='hidden';
}

function oftypeonchange()
{

     if(document.HRM_Contact_employee_details.rad_off[3].checked)
     {
       getDivision('division');
     }
     /*
     var type=document.HRM_Contact_employee_details.cmbolevel.options[document.HRM_Contact_employee_details.cmbolevel.selectedIndex].value;
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
        
                             
                         if(document.HRM_Contact_employee_details.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.HRM_Contact_employee_details.chkregion)
                              {
                              
                                        if(document.HRM_Contact_employee_details.chkregion.length)
                                        {
                                                  for(i=0;i<document.HRM_Contact_employee_details.chkregion.length;i++)
                                                    {
                                                            if(document.HRM_Contact_employee_details.chkregion[i].checked==true)
                                                              region= region+document.HRM_Contact_employee_details.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.HRM_Contact_employee_details.officeselected.value=region;
                                                         //alert(document.HRM_Contact_employee_details.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.HRM_Contact_employee_details.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.HRM_Contact_employee_details.chkregion.checked==true)
                                                {
                                                            region= document.HRM_Contact_employee_details.chkregion.value ;
                                                            document.HRM_Contact_employee_details.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.HRM_Contact_employee_details.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.HRM_Contact_employee_details.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.HRM_Contact_employee_details.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.HRM_Contact_employee_details.chkcircle)
                              {
                              
                                       if(document.HRM_Contact_employee_details.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.HRM_Contact_employee_details.chkcircle.length;i++)
                                                    {
                                                            if(document.HRM_Contact_employee_details.chkcircle[i].checked==true)
                                                                    circle= circle+document.HRM_Contact_employee_details.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.HRM_Contact_employee_details.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.HRM_Contact_employee_details.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.HRM_Contact_employee_details.chkcircle.checked==true)
                                                {
                                                            circle= document.HRM_Contact_employee_details.chkcircle.value ;
                                                            document.HRM_Contact_employee_details.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.HRM_Contact_employee_details.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.HRM_Contact_employee_details.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.HRM_Contact_employee_details.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.HRM_Contact_employee_details.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.HRM_Contact_employee_details.chkdivision)
                              {
                             //alert(document.HRM_Contact_employee_details.chkregion.length);
                                     if(document.HRM_Contact_employee_details.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.HRM_Contact_employee_details.chkdivision.length;i++)
                                                {
                                                        if(document.HRM_Contact_employee_details.chkdivision[i].checked==true)
                                                                division= division+document.HRM_Contact_employee_details.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.HRM_Contact_employee_details.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.HRM_Contact_employee_details.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.HRM_Contact_employee_details.chkdivision.checked==true)
                                                {
                                                                division= document.HRM_Contact_employee_details.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.HRM_Contact_employee_details.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.HRM_Contact_employee_details.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.HRM_Contact_employee_details.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.HRM_Contact_employee_details.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.HRM_Contact_employee_details.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                         else if(document.HRM_Contact_employee_details.rad_off[4].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var audit="";
                            if(document.HRM_Contact_employee_details.chkaudit)
                              {
                              
                                       if(document.HRM_Contact_employee_details.chkaudit.length )
                                        {
                                                
                                                    for(i=0;i<document.HRM_Contact_employee_details.chkaudit.length;i++)
                                                    {
                                                            if(document.HRM_Contact_employee_details.chkaudit[i].checked==true)
                                                                    audit= audit+document.HRM_Contact_employee_details.chkaudit[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(audit!="")
                                                    {
                                                        audit=audit.substring(0,audit.length-1);
                                                         url=url+"&officeselected="+audit;
                                                         document.HRM_Contact_employee_details.officeselected.value=audit;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.HRM_Contact_employee_details.cmbaudit.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.HRM_Contact_employee_details.chkaudit.checked==true)
                                                {
                                                            circle= document.HRM_Contact_employee_details.chkaudit.value ;
                                                            document.HRM_Contact_employee_details.officeselected.value=audit;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Audit..');
                                                            document.HRM_Contact_employee_details.cmbaudit.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Audit...');
                                       try{
                                        document.HRM_Contact_employee_details.cmbaudit.focus(); 
                                        }
                                        catch(e){
                                        document.HRM_Contact_employee_details.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                         else if(document.HRM_Contact_employee_details.rad_off[5].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var lab="";
                            if(document.HRM_Contact_employee_details.chklab)
                              {
                             //alert(document.HRM_Contact_employee_details.chkregion.length);
                                     if(document.HRM_Contact_employee_details.chklab.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.HRM_Contact_employee_details.chklab.length;i++)
                                                {
                                                        if(document.HRM_Contact_employee_details.chklab[i].checked==true)
                                                                lab= lab+document.HRM_Contact_employee_details.chklab[i].value +",";
                                                        
                                                }
                                                
                                                if(lab!="")
                                                {
                                                    lab=lab.substring(0,lab.length-1);
                                                     url=url+"&officeselected="+lab;
                                                     document.HRM_Contact_employee_details.officeselected.value=lab;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the lab.');
                                                        document.HRM_Contact_employee_details.cmblab.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.HRM_Contact_employee_details.chklab.checked==true)
                                                {
                                                                lab= document.HRM_Contact_employee_details.chkdivision.value;
                                                                    //lab=lab.substring(0,lab.length-1);
                                                                 url=url+"&officeselected="+lab;
                                                                 document.HRM_Contact_employee_details.officeselected.value=lab;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the lab..');
                                                        document.HRM_Contact_employee_details.cmblab.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the lab...');
                                       try{
                                        document.HRM_Contact_employee_details.cmblab.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.HRM_Contact_employee_details.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.HRM_Contact_employee_details.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                         else if(document.HRM_Contact_employee_details.rad_off[0].checked) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&officeselected="+5000;
                      	  
                              }
                         else if(document.HRM_Contact_employee_details.rad_off[6].checked) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&officeselected=All";
                      	  
                              }
                         
                        var include="";
                          if(document.HRM_Contact_employee_details.allhier.checked==true) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&allhier=include";
                      	  
                              }
                          else
                        	  {
                        	  url=url+"&allhier="+include;
                        	  }
                         
                         
                         var cadre_id="";
                     	if(document.HRM_Contact_employee_details.chkcadre)
                        {
                          if(document.HRM_Contact_employee_details.chkcadre.length )
                           {
                    	    for(i=0;i<document.HRM_Contact_employee_details.chkcadre.length;i++)
                              {
                              if(document.HRM_Contact_employee_details.chkcadre[i].checked==true)
                                    cadre_id= cadre_id+document.HRM_Contact_employee_details.chkcadre[i].value +",";                                                                                                       
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
                     	if(document.HRM_Contact_employee_details.optselect.checked==false)
                     		{
                     		alert("Please Select Cadre ");
                     		return false;
                     		}
                     	if(document.HRM_Contact_employee_details.optselect.checked==true)
                 		{
                 		if(document.HRM_Contact_employee_details.cmbsgroup2.selectedIndex==0)
                 			{
                 			alert("Please Select Service Group ");
                 			return false;
                 			}
                 		}
              var Address="";
                     	if(document.HRM_Contact_employee_details.Address[0].checked) 
                            // else  if(type=='DN')
                             {
                     		Address="WA";
                     	   url=url+"&Address="+Address;
                     	  
                             }
                        else if(document.HRM_Contact_employee_details.Address[1].checked) 
                            // else  if(type=='DN')
                             {
                        	Address="WOA";
                     	   url=url+"&Address="+Address;
                     	  
                             }
                     	if(document.HRM_Contact_employee_details.rad_off[0].checked) 
                            // else  if(type=='DN')
                             {
                     	   url=url+"&offlevel=ho";
                     	  
                             }
                        else if(document.HRM_Contact_employee_details.rad_off[1].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=rg";
                     	  
                             }
                        else if(document.HRM_Contact_employee_details.rad_off[2].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=cr";
                     	  
                             }
                        else if(document.HRM_Contact_employee_details.rad_off[3].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=dv";
                     	  
                             }
                        else if(document.HRM_Contact_employee_details.rad_off[4].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=aw";
                     	  
                             }
                        else if(document.HRM_Contact_employee_details.rad_off[5].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=lb";
                     	  
                             }
                     	                     	
                     	
  document.HRM_Contact_employee_details.action="Contact_employee_details.jsp?"+url+"&cadre_id="+cadre_id;
// alert(url);
  document.HRM_Contact_employee_details.submit();
                            
                    //}
                        
                    
               // }
       // }
       
      
     
   
    
   // document.HRM_Contact_employee_details.action="../../../../../../EmployeeDetailReportServ_New_Interface";
    //document.HRM_Contact_employee_details.submit();
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
       // var type=document.HRM_Contact_employee_details.cmbolevel.options[document.HRM_Contact_employee_details.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=offtype" ;
         //alert(url);
                
        document.HRM_Contact_employee_details.cmbofftype.focus();
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
                  document.HRM_Contact_employee_details.cmbcircle.disabled=false;
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
                if(document.HRM_Contact_employee_details.chkregion.length > 0)
                {
                            for(i=0;i<document.HRM_Contact_employee_details.chkregion.length;i++)
                            {
                                    if(document.HRM_Contact_employee_details.chkregion[i].checked==true)
                                            region= region+document.HRM_Contact_employee_details.chkregion[i].value +",";
                            }
                            
                            if(region!="")
                            {
                                 if(auditflag==true && aud=='null')
                                {
                                         var iframe=document.getElementById("diviframeaudit");
                                         iframe.style.visibility='visible';
                                         iframe.focus();
                                         document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                         document.HRM_Contact_employee_details.cmbcircle.disabled=false; 
                                }
                                region=region.substring(0,region.length-1);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=audit&regions="+region;
                              // document.HRM_Contact_employee_details.cmbregion.focus();
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
                                            var iframe=document.getElementById("diviframeaudit");
                                            iframe.style.visibility='visible';
                                            iframe.focus();
                                            
                                            if(navigator.appName.indexOf('Microsoft')!=-1)
                                              iframe.innerHTML=req.responseText;
                                              else
                                              iframe.innerText=req.responseText;
                                            iframe.innerHTML=req.responseText;
                                            document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                              var iframe=document.getElementById("diviframedivision");
                                             iframe.style.visibility='hidden';
                                            
                                            
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
                                document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                document.HRM_Contact_employee_details.cmbaudit.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.HRM_Contact_employee_details.chkregion.checked==true)
                             {
                                            region= document.HRM_Contact_employee_details.chkregion.value ;
                                    
                                    
                                         if(auditflag==true && aud=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframeaudit");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                        }
                                        var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=audit&regions="+region;
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
                                                    
                                                    document.HRM_Contact_employee_details.cmbaudit.disabled=false;
                                                     var iframe=document.getElementById("diviframeaudit");
                                                     iframe.style.visibility='hidden';
                                                    
                                                    
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
                                document.HRM_Contact_employee_details.cmbdivision.disabled=false;
                                document.HRM_Contact_employee_details.cmbaudit.focus();
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

       
       
     // if(document.HRM_Contact_employee_details.chkregion)
     // {
      
         
           
               if(document.HRM_Contact_employee_details.chkregion.length)
              {
              
                
             
                            for(i=0;i<document.HRM_Contact_employee_details.chkregion.length;i++)
                            {
                             
                                    if(document.HRM_Contact_employee_details.chkregion[i].checked==true)
                                            region= region+document.HRM_Contact_employee_details.chkregion[i].value +",";
                                            //alert('region...'+region);
                                         
                                    
                            }
                 }
       // }
       
     
       
      if(document.HRM_Contact_employee_details.chkcircle)
      {
      
            if(document.HRM_Contact_employee_details.chkcircle.length)
            {
            
                            for(i=0;i<document.HRM_Contact_employee_details.chkcircle.length;i++)
                            {
                                    if(document.HRM_Contact_employee_details.chkcircle[i].checked==true)
                                            circle= circle+document.HRM_Contact_employee_details.chkcircle[i].value +",";
                                  
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
                               // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                              
                              var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=lab&circles="+circle+"&regions="+region;
                             //alert(url);
                              
                              // document.HRM_Contact_employee_details.cmbregion.focus();
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
                                            
                                            //var type=document.HRM_Contact_employee_details.cmbolevel.options[document.HRM_Contact_employee_details.cmbolevel.selectedIndex].value;
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
                                document.HRM_Contact_employee_details.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.HRM_Contact_employee_details.chkcircle.checked==true)
                             {
                                            circle= document.HRM_Contact_employee_details.chkcircle.value ;
                                    
                           
                                         if(divisionflag==true && lb=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframelab");
                                                    iframe.style.visibility='visible';
                                                    iframe.focus();
                                              //  return;
                                        }
                                       // circle=circle.substring(0,circle.length-1);
                                      //  var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=division&circles="+circle;
                                      region=region.substring(0,region.length-1);
                                      //alert(region);
                                      var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=lab&circles="+circle;
                                      //alert(url);
                                      // document.HRM_Contact_employee_details.cmbregion.focus();
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
                                                    //var type=document.HRM_Contact_employee_details.cmbolevel.options[document.HRM_Contact_employee_details.cmbolevel.selectedIndex].value;
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
                                document.HRM_Contact_employee_details.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                
                }
            
      }
   
}
function  Caders()
{



    //if(document.HRM_Contact_employee_details.optselect[2].checked)
   
     if(document.HRM_Contact_employee_details.optselect.checked==true)
    {
      
        var id=document.getElementById("divcadre");
        id.style.display='block';       
    
    
    }

}
function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.HRM_Contact_employee_details.cmbsgroup2.options[document.HRM_Contact_employee_details.cmbsgroup2.selectedIndex].value;
       
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
    var type=document.HRM_Contact_employee_details.cmbsgroup2.options[document.HRM_Contact_employee_details.cmbsgroup2.selectedIndex].value;
    //startwaiting(document.HRM_Contact_employee_details) ;
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
    if(document.HRM_Contact_employee_details.chkcadre)
      {
      
            for(i=0;i<document.HRM_Contact_employee_details.chkcadre.length;i++)
            {
                    document.HRM_Contact_employee_details.chkcadre[i].checked=true;
                    
            }
        }
}
function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
  
}