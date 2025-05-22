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

function hidden1()
{
	 var offlevel=document.getElementById("trofficeselection");
     offlevel.style.display="none";
     officevisible('none','none','none','none','none','none');
	document.getElementById("divallhier").style.display="none";
	document.getElementById("divdeputation").style.display="";
}


function selectoption2()
{
   
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    
    
    if(document.Hrm_Emp_Roles_Based_Rep.rad_off[0].checked)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
       
    }
    if(document.Hrm_Emp_Roles_Based_Rep.rad_off[6].checked)
    {
    	
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
       officevisible('none','none','none','none','none','none');
    }
  if(document.Hrm_Emp_Roles_Based_Rep.rad_off[1].checked)
  {
   // alert(document.Hrm_Emp_Roles_Based_Rep.rad_off[1].checked);
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none','none','none');
  }
  
  else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[2].checked)
  {
   // alert(document.Hrm_Emp_Roles_Based_Rep.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none','none','none');
  }
  
  else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[3].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','none','none');
  }
  else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[4].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','none','none','none','block','none');
     var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[5].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','none','none','none','block');
     var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  
  
  document.Hrm_Emp_Roles_Based_Rep.cmbcircle.disabled=true;
   // document.Hrm_Emp_Roles_Based_Rep.cmbofftype.disabled=true;
    document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=true;
    document.Hrm_Emp_Roles_Based_Rep.cmbaudit.disabled=true;
     document.Hrm_Emp_Roles_Based_Rep.cmblab.disabled=true;
    
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
                
        document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus();
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
                   
                  document.Hrm_Emp_Roles_Based_Rep.cmbcircle.disabled=false;
                  document.Hrm_Emp_Roles_Based_Rep.cmbaudit.disabled=false;
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
     //   var type=document.Hrm_Emp_Roles_Based_Rep.cmbolevel.options[document.Hrm_Emp_Roles_Based_Rep.cmbolevel.selectedIndex].value;
     
      
    
     // if(document.Hrm_Emp_Roles_Based_Rep.chkregion)
      //{
      
         // alert(document.Hrm_Emp_Roles_Based_Rep.chkregion);
           
               if(document.Hrm_Emp_Roles_Based_Rep.chkregion.length > 0)
              {
              
                // alert(document.Hrm_Emp_Roles_Based_Rep.chkregion.length);
             
                            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkregion.length;i++)
                            {
                              //  alert("document.Hrm_Emp_Roles_Based_Rep.chkregion[i].checked..."+document.Hrm_Emp_Roles_Based_Rep.chkregion[i].checked);
                                    if(document.Hrm_Emp_Roles_Based_Rep.chkregion[i].checked==true)
                                            region= region+document.Hrm_Emp_Roles_Based_Rep.chkregion[i].value +",";
                                            
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
                                           document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                           document.Hrm_Emp_Roles_Based_Rep.cmblab.disabled=false;
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                             //   alert(url);
                              // document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus();
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
                                            document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                             document.Hrm_Emp_Roles_Based_Rep.cmblab.disabled=false;
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
                                document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                document.Hrm_Emp_Roles_Based_Rep.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.Hrm_Emp_Roles_Based_Rep.chkregion.checked==true)
                             {
                                            region= document.Hrm_Emp_Roles_Based_Rep.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                                    document.Hrm_Emp_Roles_Based_Rep.cmblab.disabled=false;
                                                //return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                                        
                                      // document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus();
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
                                                    
                                                    document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                                     document.Hrm_Emp_Roles_Based_Rep.cmblab.disabled=false;
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
                                document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                document.Hrm_Emp_Roles_Based_Rep.cmbcircle.focus();
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
//      if(document.Hrm_Emp_Roles_Based_Rep.chkregion)
//      {
//         region=document.Hrm_Emp_Roles_Based_Rep.chkregion.value;
//         region+=",";
//         }   
         
         if(document.Hrm_Emp_Roles_Based_Rep.chkregion.length>0)
              {
                   for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkregion.length;i++)
                            {
                            //  alert(document.Hrm_Emp_Roles_Based_Rep.chkregion[i].value);
                                    if(document.Hrm_Emp_Roles_Based_Rep.chkregion[i].checked==true)
                                            region= region+document.Hrm_Emp_Roles_Based_Rep.chkregion[i].value +",";
                                          // alert('region...'+region);
                                         
                                    
                            }
                 }
                 else
                 {
                        region=document.Hrm_Emp_Roles_Based_Rep.chkregion.value;
                        region+=",";
                 }
     
       if(document.Hrm_Emp_Roles_Based_Rep.chkofftype)
       {
               
         if(document.Hrm_Emp_Roles_Based_Rep.chkofftype.length)
         {
                    
                  for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkofftype.length;i++)
                  {
                    if(document.Hrm_Emp_Roles_Based_Rep.chkofftype[i].checked==true)
                     offtype= offtype+"'"+document.Hrm_Emp_Roles_Based_Rep.chkofftype[i].value+"'"+",";            
                                      
                  }
               
         }
       }
       
      if(document.Hrm_Emp_Roles_Based_Rep.chkcircle)
      {
      //alert("hello");
            if(document.Hrm_Emp_Roles_Based_Rep.chkcircle.length)
            {
         //alert("hello1");   
                            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkcircle.length;i++)
                            {
                                    if(document.Hrm_Emp_Roles_Based_Rep.chkcircle[i].checked==true)
                                            circle= circle+document.Hrm_Emp_Roles_Based_Rep.chkcircle[i].value +",";
                                  
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
                                            
                                            //var type=document.Hrm_Emp_Roles_Based_Rep.cmbolevel.options[document.Hrm_Emp_Roles_Based_Rep.cmbolevel.selectedIndex].value;
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
                                      // document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus();
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
                                                    //var type=document.Hrm_Emp_Roles_Based_Rep.cmbolevel.options[document.Hrm_Emp_Roles_Based_Rep.cmbolevel.selectedIndex].value;
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
                                document.Hrm_Emp_Roles_Based_Rep.cmbdivision.focus();
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

   if(document.Hrm_Emp_Roles_Based_Rep.chkregion)
      {
      
            
            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkregion.length;i++)
            {
                    document.Hrm_Emp_Roles_Based_Rep.chkregion[i].checked=true;
                    
            }
           //  regiononchange();
        }
}


function offtypeselectAll()
{

   if(document.Hrm_Emp_Roles_Based_Rep.chkofftype)
      {
      
            
            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkofftype.length;i++)
            {
                    document.Hrm_Emp_Roles_Based_Rep.chkofftype[i].checked=true;
                    
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
    if(document.Hrm_Emp_Roles_Based_Rep.chkcircle)
      {
      
            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkcircle.length;i++)
            {
                    document.Hrm_Emp_Roles_Based_Rep.chkcircle[i].checked=true;
                    
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
    if(document.Hrm_Emp_Roles_Based_Rep.chkdivision)
      {
      
            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkdivision.length;i++)
            {
                    document.Hrm_Emp_Roles_Based_Rep.chkdivision[i].checked=true;
                    
            }
        }
}
function auditselectAll()
{
    if(document.Hrm_Emp_Roles_Based_Rep.chkaudit)
      {
      
            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkaudit.length;i++)
            {
                    document.Hrm_Emp_Roles_Based_Rep.chkaudit[i].checked=true;
                    
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
    if(document.Hrm_Emp_Roles_Based_Rep.chklab)
      {
      
            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chklab.length;i++)
            {
                    document.Hrm_Emp_Roles_Based_Rep.chklab[i].checked=true;
                    
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

     if(document.Hrm_Emp_Roles_Based_Rep.rad_off[3].checked)
     {
       getDivision('division');
     }
     /*
     var type=document.Hrm_Emp_Roles_Based_Rep.cmbolevel.options[document.Hrm_Emp_Roles_Based_Rep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }
   */
}




function frmsubmit()
{
  
   // alert('inside submit');
        var url="";
        
                             
                         if(document.Hrm_Emp_Roles_Based_Rep.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.Hrm_Emp_Roles_Based_Rep.chkregion)
                              {
                              
                                        if(document.Hrm_Emp_Roles_Based_Rep.chkregion.length)
                                        {
                                                  for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkregion.length;i++)
                                                    {
                                                            if(document.Hrm_Emp_Roles_Based_Rep.chkregion[i].checked==true)
                                                              region= region+document.Hrm_Emp_Roles_Based_Rep.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.Hrm_Emp_Roles_Based_Rep.officeselected.value=region;
                                                         //alert(document.Hrm_Emp_Roles_Based_Rep.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.Hrm_Emp_Roles_Based_Rep.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.Hrm_Emp_Roles_Based_Rep.chkregion.checked==true)
                                                {
                                                            region= document.Hrm_Emp_Roles_Based_Rep.chkregion.value ;
                                                            document.Hrm_Emp_Roles_Based_Rep.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.Hrm_Emp_Roles_Based_Rep.chkcircle)
                              {
                              
                                       if(document.Hrm_Emp_Roles_Based_Rep.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkcircle.length;i++)
                                                    {
                                                            if(document.Hrm_Emp_Roles_Based_Rep.chkcircle[i].checked==true)
                                                                    circle= circle+document.Hrm_Emp_Roles_Based_Rep.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.Hrm_Emp_Roles_Based_Rep.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Hrm_Emp_Roles_Based_Rep.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Hrm_Emp_Roles_Based_Rep.chkcircle.checked==true)
                                                {
                                                            circle= document.Hrm_Emp_Roles_Based_Rep.chkcircle.value ;
                                                            document.Hrm_Emp_Roles_Based_Rep.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.Hrm_Emp_Roles_Based_Rep.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.Hrm_Emp_Roles_Based_Rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.Hrm_Emp_Roles_Based_Rep.chkdivision)
                              {
                             //alert(document.Hrm_Emp_Roles_Based_Rep.chkregion.length);
                                     if(document.Hrm_Emp_Roles_Based_Rep.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkdivision.length;i++)
                                                {
                                                        if(document.Hrm_Emp_Roles_Based_Rep.chkdivision[i].checked==true)
                                                                division= division+document.Hrm_Emp_Roles_Based_Rep.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.Hrm_Emp_Roles_Based_Rep.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.Hrm_Emp_Roles_Based_Rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Hrm_Emp_Roles_Based_Rep.chkdivision.checked==true)
                                                {
                                                                division= document.Hrm_Emp_Roles_Based_Rep.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.Hrm_Emp_Roles_Based_Rep.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.Hrm_Emp_Roles_Based_Rep.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.Hrm_Emp_Roles_Based_Rep.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Hrm_Emp_Roles_Based_Rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                         else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[4].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var audit="";
                            if(document.Hrm_Emp_Roles_Based_Rep.chkaudit)
                              {
                              
                                       if(document.Hrm_Emp_Roles_Based_Rep.chkaudit.length )
                                        {
                                                
                                                    for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkaudit.length;i++)
                                                    {
                                                            if(document.Hrm_Emp_Roles_Based_Rep.chkaudit[i].checked==true)
                                                                    audit= audit+document.Hrm_Emp_Roles_Based_Rep.chkaudit[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(audit!="")
                                                    {
                                                        audit=audit.substring(0,audit.length-1);
                                                         url=url+"&officeselected="+audit;
                                                         document.Hrm_Emp_Roles_Based_Rep.officeselected.value=audit;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Hrm_Emp_Roles_Based_Rep.cmbaudit.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Hrm_Emp_Roles_Based_Rep.chkaudit.checked==true)
                                                {
                                                            circle= document.Hrm_Emp_Roles_Based_Rep.chkaudit.value ;
                                                            document.Hrm_Emp_Roles_Based_Rep.officeselected.value=audit;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Audit..');
                                                            document.Hrm_Emp_Roles_Based_Rep.cmbaudit.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Audit...');
                                       try{
                                        document.Hrm_Emp_Roles_Based_Rep.cmbaudit.focus(); 
                                        }
                                        catch(e){
                                        document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                         else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[5].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var lab="";
                            if(document.Hrm_Emp_Roles_Based_Rep.chklab)
                              {
                             //alert(document.Hrm_Emp_Roles_Based_Rep.chkregion.length);
                                     if(document.Hrm_Emp_Roles_Based_Rep.chklab.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chklab.length;i++)
                                                {
                                                        if(document.Hrm_Emp_Roles_Based_Rep.chklab[i].checked==true)
                                                                lab= lab+document.Hrm_Emp_Roles_Based_Rep.chklab[i].value +",";
                                                        
                                                }
                                                
                                                if(lab!="")
                                                {
                                                    lab=lab.substring(0,lab.length-1);
                                                     url=url+"&officeselected="+lab;
                                                     document.Hrm_Emp_Roles_Based_Rep.officeselected.value=lab;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the lab.');
                                                        document.Hrm_Emp_Roles_Based_Rep.cmblab.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Hrm_Emp_Roles_Based_Rep.chklab.checked==true)
                                                {
                                                                lab= document.Hrm_Emp_Roles_Based_Rep.chkdivision.value;
                                                                    //lab=lab.substring(0,lab.length-1);
                                                                 url=url+"&officeselected="+lab;
                                                                 document.Hrm_Emp_Roles_Based_Rep.officeselected.value=lab;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the lab..');
                                                        document.Hrm_Emp_Roles_Based_Rep.cmblab.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the lab...');
                                       try{
                                        document.Hrm_Emp_Roles_Based_Rep.cmblab.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Hrm_Emp_Roles_Based_Rep.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                         else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[0].checked) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&officeselected="+5000;
                      	  
                              }
                         else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[6].checked) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&officeselected=All";
                      	  
                              }
                       
                        var include="";
                          if(document.Hrm_Emp_Roles_Based_Rep.allhier.checked==true) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&allhier=include";
                      	  
                              }
                          else
                        	  {
                        	  url=url+"&allhier="+include;
                        	  }
                         
                         
                       
                     	
                     	
             
                     	if(document.Hrm_Emp_Roles_Based_Rep.rad_off[0].checked) 
                            // else  if(type=='DN')
                             {
                     	   url=url+"&offlevel=HO";
                     	  
                             }
                        else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[1].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=rg";
                     	  
                             }
                        else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[2].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=cr";
                     	  
                             }
                        else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[3].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=dv";
                     	  
                             }
                        else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[4].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=aw";
                     	  
                             }
                        else if(document.Hrm_Emp_Roles_Based_Rep.rad_off[5].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=lb";
                     	  
                             }
                     	
                         var role_id=0;
                         role_id=document.getElementById("role_id").value;
                         
                         
                         if(role_id>0)
                        	 {
                        	 url=url+"&role_id="+role_id;
                        	 }
                         else
                        	 {
                        	       alert('Please Select Roles Name...');
                               // document.Hrm_Emp_Roles_Based_Rep.cmblab.focus(); 
                                 return;
                        	 }
                         
                       // alert(url);
                     	
  document.Hrm_Emp_Roles_Based_Rep.action="Hrm_Emp_Roles_Based_Details_rep_New.jsp?"+url+"&role_id="+role_id;
//alert(url);
  document.Hrm_Emp_Roles_Based_Rep.submit();
                            
                    //}
                        
                    
               // }
       // }
       
      
     
   
    
   // document.Hrm_Emp_Roles_Based_Rep.action="../../../../../../EmployeeDetailReportServ_New_Interface";
    //document.Hrm_Emp_Roles_Based_Rep.submit();
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
       // var type=document.Hrm_Emp_Roles_Based_Rep.cmbolevel.options[document.Hrm_Emp_Roles_Based_Rep.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=offtype" ;
         //alert(url);
                
        document.Hrm_Emp_Roles_Based_Rep.cmbofftype.focus();
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
                  document.Hrm_Emp_Roles_Based_Rep.cmbcircle.disabled=false;
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
                if(document.Hrm_Emp_Roles_Based_Rep.chkregion.length > 0)
                {
                            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkregion.length;i++)
                            {
                                    if(document.Hrm_Emp_Roles_Based_Rep.chkregion[i].checked==true)
                                            region= region+document.Hrm_Emp_Roles_Based_Rep.chkregion[i].value +",";
                            }
                            
                            if(region!="")
                            {
                                 if(auditflag==true && aud=='null')
                                {
                                         var iframe=document.getElementById("diviframeaudit");
                                         iframe.style.visibility='visible';
                                         iframe.focus();
                                         document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                         document.Hrm_Emp_Roles_Based_Rep.cmbcircle.disabled=false; 
                                }
                                region=region.substring(0,region.length-1);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=audit&regions="+region;
                              // document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus();
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
                                            document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
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
                                document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                document.Hrm_Emp_Roles_Based_Rep.cmbaudit.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.Hrm_Emp_Roles_Based_Rep.chkregion.checked==true)
                             {
                                            region= document.Hrm_Emp_Roles_Based_Rep.chkregion.value ;
                                    
                                    
                                         if(auditflag==true && aud=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframeaudit");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
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
                                                    
                                                    document.Hrm_Emp_Roles_Based_Rep.cmbaudit.disabled=false;
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
                                document.Hrm_Emp_Roles_Based_Rep.cmbdivision.disabled=false;
                                document.Hrm_Emp_Roles_Based_Rep.cmbaudit.focus();
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

       
       
     // if(document.Hrm_Emp_Roles_Based_Rep.chkregion)
     // {
      
         
           
               if(document.Hrm_Emp_Roles_Based_Rep.chkregion.length)
              {
              
                
             
                            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkregion.length;i++)
                            {
                             
                                    if(document.Hrm_Emp_Roles_Based_Rep.chkregion[i].checked==true)
                                            region= region+document.Hrm_Emp_Roles_Based_Rep.chkregion[i].value +",";
                                            //alert('region...'+region);
                                         
                                    
                            }
                 }
       // }
       
     
       
      if(document.Hrm_Emp_Roles_Based_Rep.chkcircle)
      {
      
            if(document.Hrm_Emp_Roles_Based_Rep.chkcircle.length)
            {
            
                            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkcircle.length;i++)
                            {
                                    if(document.Hrm_Emp_Roles_Based_Rep.chkcircle[i].checked==true)
                                            circle= circle+document.Hrm_Emp_Roles_Based_Rep.chkcircle[i].value +",";
                                  
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
                              
                              // document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus();
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
                                            
                                            //var type=document.Hrm_Emp_Roles_Based_Rep.cmbolevel.options[document.Hrm_Emp_Roles_Based_Rep.cmbolevel.selectedIndex].value;
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
                                document.Hrm_Emp_Roles_Based_Rep.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.Hrm_Emp_Roles_Based_Rep.chkcircle.checked==true)
                             {
                                            circle= document.Hrm_Emp_Roles_Based_Rep.chkcircle.value ;
                                    
                           
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
                                      // document.Hrm_Emp_Roles_Based_Rep.cmbregion.focus();
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
                                                    //var type=document.Hrm_Emp_Roles_Based_Rep.cmbolevel.options[document.Hrm_Emp_Roles_Based_Rep.cmbolevel.selectedIndex].value;
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
                                document.Hrm_Emp_Roles_Based_Rep.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                
                }
            
      }
   
}
function  designa()
{

	 if(document.Hrm_Emp_Roles_Based_Rep.optselect.checked==true)
	    {
	 var id=document.getElementById("divdest");
     id.style.display='block';       
	    }
    
   
   

}
function getDesignation()
{
document.getElementById("desigblock").style.display='block';
//alert("getDesisg");
   var type=document.Hrm_Emp_Roles_Based_Rep.cmbsgroup.options[document.Hrm_Emp_Roles_Based_Rep.cmbsgroup.selectedIndex].value;
   //alert(type);
   if(type!=0)
    {
    //alert("inside");
           // var din=document.getElementById("divdest");
           // din.style.visibility="visible";
           // document.Desig_wise_summ_rep.cmbDesignation.style.visibility="visible";
            loadOfficesByType(type);
    }
   else
    {
             var des=document.getElementById("cmbDesignation");
             des.innerHTML='';
            // var din=document.getElementById("divdest");
           //  din.style.visibility="hidden";
           //  document.Desig_wise_summ_rep.cmbDesignation.style.visibility="hidden";
     }
}
function loadOfficesByType(type)
{
    //alert(type);
    var type=document.Hrm_Emp_Roles_Based_Rep.cmbsgroup.options[document.Hrm_Emp_Roles_Based_Rep.cmbsgroup.selectedIndex].value;
    //startwaiting(document.Desig_wise_summ_rep) ;
     //service=null;
  
   //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
   var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
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
      //alert(req.responseText);
        var divdes=document.getElementById("divdesignation");
        divdes.style.visibility='visible';
        divdes.focus();
        //divdes.style.display='block';
       // var des=document.getElementById("cmbDesignation");
        
        
      //  alert(des);
        
       if(navigator.appName.indexOf('Microsoft')!=-1)
            {divdes.innerHTML=req.responseText;
       divdes.innerText=req.responseText;}
        else
        	{
        	divdes.innerHTML=req.responseText;
            divdes.innerText=req.responseText;
        	}
        // alert("show");
        //document.getElementById("cmbRank").visibility='hidden';  
        //document.getElementById("cmbCadre").visibility='hidden';
        }
        }
         
         }
    req.send(null);
}
function designationselectAll()
{
    if(document.Hrm_Emp_Roles_Based_Rep.chkdesig)
      {
      
            for(i=0;i<document.Hrm_Emp_Roles_Based_Rep.chkdesig.length;i++)
            {
                    document.Hrm_Emp_Roles_Based_Rep.chkdesig[i].checked=true;
                    
            }
        }
}
function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
  
}