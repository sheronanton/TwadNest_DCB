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
    document.getElementById("divdeputation").style.display="none";
    
    if(document.Avg_age_emp_desig.rad_off[0].checked)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
       
    }
    if(document.Avg_age_emp_desig.rad_off[6].checked)
    {
    	
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
       officevisible('none','none','none','none','none','none');
    }
  if(document.Avg_age_emp_desig.rad_off[1].checked)
  {
   // alert(document.Avg_age_emp_desig.rad_off[1].checked);
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none','none','none');
  }
  
  else if(document.Avg_age_emp_desig.rad_off[2].checked)
  {
   // alert(document.Avg_age_emp_desig.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none','none','none');
  }
  
  else if(document.Avg_age_emp_desig.rad_off[3].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','none','none');
  }
  else if(document.Avg_age_emp_desig.rad_off[4].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','none','none','none','block','none');
     var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  else if(document.Avg_age_emp_desig.rad_off[5].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','none','none','none','block');
     var divallhier=document.getElementById("divallhier");
     divallhier.style.display="none";  
  }
  
  
  document.Avg_age_emp_desig.cmbcircle.disabled=true;
   // document.Avg_age_emp_desig.cmbofftype.disabled=true;
    document.Avg_age_emp_desig.cmbdivision.disabled=true;
    document.Avg_age_emp_desig.cmbaudit.disabled=true;
     document.Avg_age_emp_desig.cmblab.disabled=true;
    
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
                
        document.Avg_age_emp_desig.cmbregion.focus();
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
                   
                  document.Avg_age_emp_desig.cmbcircle.disabled=false;
                  document.Avg_age_emp_desig.cmbaudit.disabled=false;
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
     //   var type=document.Avg_age_emp_desig.cmbolevel.options[document.Avg_age_emp_desig.cmbolevel.selectedIndex].value;
     
      
    
     // if(document.Avg_age_emp_desig.chkregion)
      //{
      
         // alert(document.Avg_age_emp_desig.chkregion);
           
               if(document.Avg_age_emp_desig.chkregion.length > 0)
              {
              
                // alert(document.Avg_age_emp_desig.chkregion.length);
             
                            for(i=0;i<document.Avg_age_emp_desig.chkregion.length;i++)
                            {
                              //  alert("document.Avg_age_emp_desig.chkregion[i].checked..."+document.Avg_age_emp_desig.chkregion[i].checked);
                                    if(document.Avg_age_emp_desig.chkregion[i].checked==true)
                                            region= region+document.Avg_age_emp_desig.chkregion[i].value +",";
                                            
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
                                           document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                           document.Avg_age_emp_desig.cmblab.disabled=false;
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                             //   alert(url);
                              // document.Avg_age_emp_desig.cmbregion.focus();
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
                                            document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                             document.Avg_age_emp_desig.cmblab.disabled=false;
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
                                document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                document.Avg_age_emp_desig.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.Avg_age_emp_desig.chkregion.checked==true)
                             {
                                            region= document.Avg_age_emp_desig.chkregion.value ;
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                                    document.Avg_age_emp_desig.cmblab.disabled=false;
                                                //return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../EmployeeDPReport_office?OLevel=circle&regions="+region;
                                        
                                      // document.Avg_age_emp_desig.cmbregion.focus();
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
                                                    
                                                    document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                                     document.Avg_age_emp_desig.cmblab.disabled=false;
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
                                document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                document.Avg_age_emp_desig.cmbcircle.focus();
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
//      if(document.Avg_age_emp_desig.chkregion)
//      {
//         region=document.Avg_age_emp_desig.chkregion.value;
//         region+=",";
//         }   
         
         if(document.Avg_age_emp_desig.chkregion.length>0)
              {
                   for(i=0;i<document.Avg_age_emp_desig.chkregion.length;i++)
                            {
                            //  alert(document.Avg_age_emp_desig.chkregion[i].value);
                                    if(document.Avg_age_emp_desig.chkregion[i].checked==true)
                                            region= region+document.Avg_age_emp_desig.chkregion[i].value +",";
                                          // alert('region...'+region);
                                         
                                    
                            }
                 }
                 else
                 {
                        region=document.Avg_age_emp_desig.chkregion.value;
                        region+=",";
                 }
     
       if(document.Avg_age_emp_desig.chkofftype)
       {
               
         if(document.Avg_age_emp_desig.chkofftype.length)
         {
                    
                  for(i=0;i<document.Avg_age_emp_desig.chkofftype.length;i++)
                  {
                    if(document.Avg_age_emp_desig.chkofftype[i].checked==true)
                     offtype= offtype+"'"+document.Avg_age_emp_desig.chkofftype[i].value+"'"+",";            
                                      
                  }
               
         }
       }
       
      if(document.Avg_age_emp_desig.chkcircle)
      {
      //alert("hello");
            if(document.Avg_age_emp_desig.chkcircle.length)
            {
         //alert("hello1");   
                            for(i=0;i<document.Avg_age_emp_desig.chkcircle.length;i++)
                            {
                                    if(document.Avg_age_emp_desig.chkcircle[i].checked==true)
                                            circle= circle+document.Avg_age_emp_desig.chkcircle[i].value +",";
                                  
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
                                            
                                            //var type=document.Avg_age_emp_desig.cmbolevel.options[document.Avg_age_emp_desig.cmbolevel.selectedIndex].value;
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
                                      // document.Avg_age_emp_desig.cmbregion.focus();
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
                                                    //var type=document.Avg_age_emp_desig.cmbolevel.options[document.Avg_age_emp_desig.cmbolevel.selectedIndex].value;
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
                                document.Avg_age_emp_desig.cmbdivision.focus();
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

   if(document.Avg_age_emp_desig.chkregion)
      {
      
            
            for(i=0;i<document.Avg_age_emp_desig.chkregion.length;i++)
            {
                    document.Avg_age_emp_desig.chkregion[i].checked=true;
                    
            }
           //  regiononchange();
        }
}


function offtypeselectAll()
{

   if(document.Avg_age_emp_desig.chkofftype)
      {
      
            
            for(i=0;i<document.Avg_age_emp_desig.chkofftype.length;i++)
            {
                    document.Avg_age_emp_desig.chkofftype[i].checked=true;
                    
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
    if(document.Avg_age_emp_desig.chkcircle)
      {
      
            for(i=0;i<document.Avg_age_emp_desig.chkcircle.length;i++)
            {
                    document.Avg_age_emp_desig.chkcircle[i].checked=true;
                    
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
    if(document.Avg_age_emp_desig.chkdivision)
      {
      
            for(i=0;i<document.Avg_age_emp_desig.chkdivision.length;i++)
            {
                    document.Avg_age_emp_desig.chkdivision[i].checked=true;
                    
            }
        }
}
function auditselectAll()
{
    if(document.Avg_age_emp_desig.chkaudit)
      {
      
            for(i=0;i<document.Avg_age_emp_desig.chkaudit.length;i++)
            {
                    document.Avg_age_emp_desig.chkaudit[i].checked=true;
                    
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
    if(document.Avg_age_emp_desig.chklab)
      {
      
            for(i=0;i<document.Avg_age_emp_desig.chklab.length;i++)
            {
                    document.Avg_age_emp_desig.chklab[i].checked=true;
                    
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

     if(document.Avg_age_emp_desig.rad_off[3].checked)
     {
       getDivision('division');
     }
     /*
     var type=document.Avg_age_emp_desig.cmbolevel.options[document.Avg_age_emp_desig.cmbolevel.selectedIndex].value;
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
        
                             
                         if(document.Avg_age_emp_desig.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.Avg_age_emp_desig.chkregion)
                              {
                              
                                        if(document.Avg_age_emp_desig.chkregion.length)
                                        {
                                                  for(i=0;i<document.Avg_age_emp_desig.chkregion.length;i++)
                                                    {
                                                            if(document.Avg_age_emp_desig.chkregion[i].checked==true)
                                                              region= region+document.Avg_age_emp_desig.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.Avg_age_emp_desig.officeselected.value=region;
                                                         //alert(document.Avg_age_emp_desig.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.Avg_age_emp_desig.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.Avg_age_emp_desig.chkregion.checked==true)
                                                {
                                                            region= document.Avg_age_emp_desig.chkregion.value ;
                                                            document.Avg_age_emp_desig.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.Avg_age_emp_desig.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.Avg_age_emp_desig.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.Avg_age_emp_desig.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.Avg_age_emp_desig.chkcircle)
                              {
                              
                                       if(document.Avg_age_emp_desig.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.Avg_age_emp_desig.chkcircle.length;i++)
                                                    {
                                                            if(document.Avg_age_emp_desig.chkcircle[i].checked==true)
                                                                    circle= circle+document.Avg_age_emp_desig.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.Avg_age_emp_desig.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Avg_age_emp_desig.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Avg_age_emp_desig.chkcircle.checked==true)
                                                {
                                                            circle= document.Avg_age_emp_desig.chkcircle.value ;
                                                            document.Avg_age_emp_desig.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.Avg_age_emp_desig.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.Avg_age_emp_desig.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Avg_age_emp_desig.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.Avg_age_emp_desig.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.Avg_age_emp_desig.chkdivision)
                              {
                             //alert(document.Avg_age_emp_desig.chkregion.length);
                                     if(document.Avg_age_emp_desig.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Avg_age_emp_desig.chkdivision.length;i++)
                                                {
                                                        if(document.Avg_age_emp_desig.chkdivision[i].checked==true)
                                                                division= division+document.Avg_age_emp_desig.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.Avg_age_emp_desig.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.Avg_age_emp_desig.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Avg_age_emp_desig.chkdivision.checked==true)
                                                {
                                                                division= document.Avg_age_emp_desig.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.Avg_age_emp_desig.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.Avg_age_emp_desig.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.Avg_age_emp_desig.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Avg_age_emp_desig.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Avg_age_emp_desig.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                         else if(document.Avg_age_emp_desig.rad_off[4].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var audit="";
                            if(document.Avg_age_emp_desig.chkaudit)
                              {
                              
                                       if(document.Avg_age_emp_desig.chkaudit.length )
                                        {
                                                
                                                    for(i=0;i<document.Avg_age_emp_desig.chkaudit.length;i++)
                                                    {
                                                            if(document.Avg_age_emp_desig.chkaudit[i].checked==true)
                                                                    audit= audit+document.Avg_age_emp_desig.chkaudit[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(audit!="")
                                                    {
                                                        audit=audit.substring(0,audit.length-1);
                                                         url=url+"&officeselected="+audit;
                                                         document.Avg_age_emp_desig.officeselected.value=audit;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.Avg_age_emp_desig.cmbaudit.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.Avg_age_emp_desig.chkaudit.checked==true)
                                                {
                                                            circle= document.Avg_age_emp_desig.chkaudit.value ;
                                                            document.Avg_age_emp_desig.officeselected.value=audit;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Audit..');
                                                            document.Avg_age_emp_desig.cmbaudit.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Audit...');
                                       try{
                                        document.Avg_age_emp_desig.cmbaudit.focus(); 
                                        }
                                        catch(e){
                                        document.Avg_age_emp_desig.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                         else if(document.Avg_age_emp_desig.rad_off[5].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var lab="";
                            if(document.Avg_age_emp_desig.chklab)
                              {
                             //alert(document.Avg_age_emp_desig.chkregion.length);
                                     if(document.Avg_age_emp_desig.chklab.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.Avg_age_emp_desig.chklab.length;i++)
                                                {
                                                        if(document.Avg_age_emp_desig.chklab[i].checked==true)
                                                                lab= lab+document.Avg_age_emp_desig.chklab[i].value +",";
                                                        
                                                }
                                                
                                                if(lab!="")
                                                {
                                                    lab=lab.substring(0,lab.length-1);
                                                     url=url+"&officeselected="+lab;
                                                     document.Avg_age_emp_desig.officeselected.value=lab;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the lab.');
                                                        document.Avg_age_emp_desig.cmblab.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.Avg_age_emp_desig.chklab.checked==true)
                                                {
                                                                lab= document.Avg_age_emp_desig.chkdivision.value;
                                                                    //lab=lab.substring(0,lab.length-1);
                                                                 url=url+"&officeselected="+lab;
                                                                 document.Avg_age_emp_desig.officeselected.value=lab;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the lab..');
                                                        document.Avg_age_emp_desig.cmblab.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the lab...');
                                       try{
                                        document.Avg_age_emp_desig.cmblab.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.Avg_age_emp_desig.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.Avg_age_emp_desig.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                         else if(document.Avg_age_emp_desig.rad_off[0].checked) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&officeselected="+5000;
                      	  
                              }
                         else if(document.Avg_age_emp_desig.rad_off[6].checked) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&officeselected=All";
                      	  
                              }
                       
                        var include="";
                          if(document.Avg_age_emp_desig.allhier.checked==true) 
                             // else  if(type=='DN')
                              {
                      	   url=url+"&allhier=include";
                      	  
                              }
                          else
                        	  {
                        	  url=url+"&allhier="+include;
                        	  }
                         
                         
                         var desig_id="";
                     	if(document.Avg_age_emp_desig.chkdesig)
                        {
                          if(document.Avg_age_emp_desig.chkdesig.length )
                           {
                    	    for(i=0;i<document.Avg_age_emp_desig.chkdesig.length;i++)
                              {
                              if(document.Avg_age_emp_desig.chkdesig[i].checked==true)
                            	  desig_id= desig_id+document.Avg_age_emp_desig.chkdesig[i].value +",";                                                                                                       
                               }
                               if(desig_id!="")
                               {
                            	   desig_id= desig_id.substring(0, desig_id.length-1);
                               // alert("desig_id===>"+desig_id);
                                }
                                else
                                {
                                alert("Please Select Designation Name!");
                                return false;
                                }
                    	   }
                        }
                     	var selection="Spec";
                     	if(document.Avg_age_emp_desig.optselect[1].checked==true)
                     		{
                     		desig_id="";
                     		selection="All";
                     		
                     		}
                     	
                     	//if(document.Avg_age_emp_desig.optselect.checked==false)
                     		//{
                     		//alert("Please Select Designation ");
                     	//	return false;
                     	//	}
                     	if(document.Avg_age_emp_desig.optselect.checked==true)
                 		{
                 		if(document.Avg_age_emp_desig.cmbsgroup.selectedIndex==0)
                 			{
                 			alert("Please Select Service Group ");
                 			return false;
                 			}
                 		}
             
                     	if(document.Avg_age_emp_desig.rad_off[0].checked) 
                            // else  if(type=='DN')
                             {
                     	   url=url+"&offlevel=HO";
                     	  
                             }
                        else if(document.Avg_age_emp_desig.rad_off[1].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=rg";
                     	  
                             }
                        else if(document.Avg_age_emp_desig.rad_off[2].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=cr";
                     	  
                             }
                        else if(document.Avg_age_emp_desig.rad_off[3].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=dv";
                     	  
                             }
                        else if(document.Avg_age_emp_desig.rad_off[4].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=aw";
                     	  
                             }
                        else if(document.Avg_age_emp_desig.rad_off[5].checked) 
                            // else  if(type=='DN')
                             {
                        	 url=url+"&offlevel=lb";
                     	  
                             }
                     	 var inclue_dpt="";
                         if(document.Avg_age_emp_desig.alldept.checked==true) 
                            // else  if(type=='DN')
                             {
                     	   url=url+"&all_dpt=inclue_dpt";
                     	  
                             }
                         else
                       	  {
                       	  url=url+"&all_dpt="+inclue_dpt;
                       	  }              	
                     	
  document.Avg_age_emp_desig.action="Avg_age_employee_details_rep.jsp?"+url+"&desig_id="+desig_id+"&selection="+selection;

  document.Avg_age_emp_desig.submit();
                            
                    //}
                        
                    
               // }
       // }
       
      
     
   
    
   // document.Avg_age_emp_desig.action="../../../../../../EmployeeDetailReportServ_New_Interface";
    //document.Avg_age_emp_desig.submit();
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
       // var type=document.Avg_age_emp_desig.cmbolevel.options[document.Avg_age_emp_desig.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=offtype" ;
         //alert(url);
                
        document.Avg_age_emp_desig.cmbofftype.focus();
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
                  document.Avg_age_emp_desig.cmbcircle.disabled=false;
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
                if(document.Avg_age_emp_desig.chkregion.length > 0)
                {
                            for(i=0;i<document.Avg_age_emp_desig.chkregion.length;i++)
                            {
                                    if(document.Avg_age_emp_desig.chkregion[i].checked==true)
                                            region= region+document.Avg_age_emp_desig.chkregion[i].value +",";
                            }
                            
                            if(region!="")
                            {
                                 if(auditflag==true && aud=='null')
                                {
                                         var iframe=document.getElementById("diviframeaudit");
                                         iframe.style.visibility='visible';
                                         iframe.focus();
                                         document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                         document.Avg_age_emp_desig.cmbcircle.disabled=false; 
                                }
                                region=region.substring(0,region.length-1);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../EmployeeDetailReportServ_New_Interface?OLevel=audit&regions="+region;
                              // document.Avg_age_emp_desig.cmbregion.focus();
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
                                            document.Avg_age_emp_desig.cmbdivision.disabled=false;
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
                                document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                document.Avg_age_emp_desig.cmbaudit.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.Avg_age_emp_desig.chkregion.checked==true)
                             {
                                            region= document.Avg_age_emp_desig.chkregion.value ;
                                    
                                    
                                         if(auditflag==true && aud=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframeaudit");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.Avg_age_emp_desig.cmbdivision.disabled=false;
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
                                                    
                                                    document.Avg_age_emp_desig.cmbaudit.disabled=false;
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
                                document.Avg_age_emp_desig.cmbdivision.disabled=false;
                                document.Avg_age_emp_desig.cmbaudit.focus();
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

       
       
     // if(document.Avg_age_emp_desig.chkregion)
     // {
      
         
           
               if(document.Avg_age_emp_desig.chkregion.length)
              {
              
                
             
                            for(i=0;i<document.Avg_age_emp_desig.chkregion.length;i++)
                            {
                             
                                    if(document.Avg_age_emp_desig.chkregion[i].checked==true)
                                            region= region+document.Avg_age_emp_desig.chkregion[i].value +",";
                                            //alert('region...'+region);
                                         
                                    
                            }
                 }
       // }
       
     
       
      if(document.Avg_age_emp_desig.chkcircle)
      {
      
            if(document.Avg_age_emp_desig.chkcircle.length)
            {
            
                            for(i=0;i<document.Avg_age_emp_desig.chkcircle.length;i++)
                            {
                                    if(document.Avg_age_emp_desig.chkcircle[i].checked==true)
                                            circle= circle+document.Avg_age_emp_desig.chkcircle[i].value +",";
                                  
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
                              
                              // document.Avg_age_emp_desig.cmbregion.focus();
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
                                            
                                            //var type=document.Avg_age_emp_desig.cmbolevel.options[document.Avg_age_emp_desig.cmbolevel.selectedIndex].value;
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
                                document.Avg_age_emp_desig.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                }
                else
                {
                
                             if(document.Avg_age_emp_desig.chkcircle.checked==true)
                             {
                                            circle= document.Avg_age_emp_desig.chkcircle.value ;
                                    
                           
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
                                      // document.Avg_age_emp_desig.cmbregion.focus();
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
                                                    //var type=document.Avg_age_emp_desig.cmbolevel.options[document.Avg_age_emp_desig.cmbolevel.selectedIndex].value;
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
                                document.Avg_age_emp_desig.cmblab.focus();
                                alert('Please Select a Circle');
                            }
                           // alert("circles:"+circle);
                
                }
            
      }
   
}
function  designa()
{

	 if(document.Avg_age_emp_desig.optselect[0].checked==true)
	    {
	 var id=document.getElementById("divdest");
     id.style.display='block';       
	    }
    
   
   

}

function  designa1()
{

	 if(document.Avg_age_emp_desig.optselect[1].checked==true)
	    {
	 var id=document.getElementById("divdest");
     id.style.display='none';       
	    }
    
   
   

}
function getDesignation()
{
document.getElementById("desigblock").style.display='block';
//alert("getDesisg");
   var type=document.Avg_age_emp_desig.cmbsgroup.options[document.Avg_age_emp_desig.cmbsgroup.selectedIndex].value;
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
    var type=document.Avg_age_emp_desig.cmbsgroup.options[document.Avg_age_emp_desig.cmbsgroup.selectedIndex].value;
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
        	divdes.innerHTML=req.responseText;
            else
            	divdes.innerText=req.responseText;
                divdes.innerHTML=req.responseText;
        
//        if(navigator.appName.indexOf('Microsoft')!=-1)
//        	{
//	        	 divdes.innerHTML=req.responseText;
//	             divdes.innerText=req.responseText;
//        	}
//            else
//            {
//            	divdes.innerText=req.responseText;
//                divdes.innerHTML=req.responseText;
//            }
        
        
//       if(navigator.appName.indexOf('Microsoft')!=-1)
//            {
//	    	   divdes.innerHTML=req.responseText;
//	           divdes.innerText=req.responseText;
//       }
//        else
//        	{
//        	divdes.innerHTML=req.responseText;
//            divdes.innerText=req.responseText;
//        	}
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
    if(document.Avg_age_emp_desig.chkdesig)
      {
      
            for(i=0;i<document.Avg_age_emp_desig.chkdesig.length;i++)
            {
                    document.Avg_age_emp_desig.chkdesig[i].checked=true;
                    
            }
        }
}
function designationclose()
{
    
    var iframe=document.getElementById("divdesignation");
    iframe.style.visibility='hidden';
  
}