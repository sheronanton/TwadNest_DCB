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




function selectoption2()
{
   
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    
    
    if(document.frmEmployeeorderby.rad_off[1].checked)
    {
       var offlevel=document.getElementById("trofficeselection");
       offlevel.style.display="none";
    }

  if(document.frmEmployeeorderby.rad_off[2].checked)
  {
   // alert(document.frmEmployeeorderby.rad_off[1].checked);
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
    
     officevisible('block','none','none','none');
  }
  
  else if(document.frmEmployeeorderby.rad_off[3].checked)
  {
   // alert(document.frmEmployeeorderby.rad_off[2].checked);
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
     officevisible('block','block','none','none');
  }
  
  else if(document.frmEmployeeorderby.rad_off[4].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('block','block','block','block','block');
  }
  
  else if(document.frmEmployeeorderby.rad_off[0].checked)
  {
     var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    //document.getElementById("divallhier").style.display='block';
  
    officevisible('none','none','none','none','none');
    document.getElementById("trofficeselection").style.display="none";
    document.getElementById("tithidden").style.display="none";
    
  }
  document.frmEmployeeorderby.cmbcircle.disabled=true;
   // document.frmEmployeeorderby.cmbofftype.disabled=true;
    document.frmEmployeeorderby.cmbdivision.disabled=true;
    
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
       // alert(regionflag);
              
        
        if(regionflag==true)
        {
               
                 var iframe=document.getElementById("diviframeregion");
                 iframe.style.visibility='visible';
                 iframe.focus();
               // return;
        }
       
    
         
         var url="../../../../../../EmpValidationDetailServ_New_Interface?OLevel=region" ;
         //alert(url);
        //        
        document.frmEmployeeorderby.cmbregion.focus();
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
                   
                  document.frmEmployeeorderby.cmbcircle.disabled=false;
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
     //   var type=document.frmEmployeeorderby.cmbolevel.options[document.frmEmployeeorderby.cmbolevel.selectedIndex].value;
     
      
    
    //  if(document.frmEmployeeorderby.chkregion)
      //{
      
          //alert(document.frmEmployeeorderby.chkregion);
           
               if(document.frmEmployeeorderby.chkregion.length > 0)
              {
              
                 //alert(document.frmEmployeeorderby.chkregion.length);
             
                            for(i=0;i<document.frmEmployeeorderby.chkregion.length;i++)
                            {
                              //  alert("document.frmEmployeeorderby.chkregion[i].checked..."+document.frmEmployeeorderby.chkregion[i].checked);
                                    if(document.frmEmployeeorderby.chkregion[i].checked==true)
                                            region= region+document.frmEmployeeorderby.chkregion[i].value +",";
                                            
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
                                           document.frmEmployeeorderby.cmbdivision.disabled=false;
                                      //  return;
                                }
                                region=region.substring(0,region.length-1);
                               // alert("region1..."+region);
                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                
                                var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ_New_Interface?OLevel=circle&regions="+region;
                                //alert(url);
                              // document.frmEmployeeorderby.cmbregion.focus();
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
                                            document.frmEmployeeorderby.cmbdivision.disabled=false;
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
                                document.frmEmployeeorderby.cmbdivision.disabled=false;
                                document.frmEmployeeorderby.cmbcircle.focus();
                                alert('Please Select a Region');
                            }
            }
            else
            {
            
                             if(document.frmEmployeeorderby.chkregion.checked==true)
                             {
                                            region= document.frmEmployeeorderby.chkregion.value ;
                                            //alert(region);
                                    
                                    
                                         if(circleflag==true && cir=='null')
                                        {
                                               
                                                 var iframe=document.getElementById("diviframecircle");
                                                    iframe.style.visibility='visible';
                                                  iframe.focus();
                                                   document.frmEmployeeorderby.cmbdivision.disabled=false;
                                               // return;
                                        }
                                       // region=region.substring(0,region.length-1);
                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
                                        
                                        var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ_New_Interface?OLevel=circle&regions="+region;
                                        //alert(url);
                                      // document.frmEmployeeorderby.cmbregion.focus();
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
                                                    
                                                    document.frmEmployeeorderby.cmbdivision.disabled=false;
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
                                document.frmEmployeeorderby.cmbdivision.disabled=false;
                                document.frmEmployeeorderby.cmbcircle.focus();
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
//      if(document.frmEmployeeorderby.chkregion)
//      {
//         region=document.frmEmployeeorderby.chkregion.value;
//         region+=",";
//         }   
         
         if(document.frmEmployeeorderby.chkregion.length>0)
              {
                   for(i=0;i<document.frmEmployeeorderby.chkregion.length;i++)
                            {
                            //  alert(document.frmEmployeeorderby.chkregion[i].value);
                                    if(document.frmEmployeeorderby.chkregion[i].checked==true)
                                            region= region+document.frmEmployeeorderby.chkregion[i].value +",";
                                          // alert('region...'+region);
                                         
                                    
                            }
                 }
                 else
                 {
                        region=document.frmEmployeeorderby.chkregion.value;
                        region+=",";
                 }
     
       if(document.frmEmployeeorderby.chkofftype)
       {
               
         if(document.frmEmployeeorderby.chkofftype.length)
         {
                    
                  for(i=0;i<document.frmEmployeeorderby.chkofftype.length;i++)
                  {
                    if(document.frmEmployeeorderby.chkofftype[i].checked==true)
                     offtype= offtype+"'"+document.frmEmployeeorderby.chkofftype[i].value+"'"+",";            
                                      
                  }
               
         }
       }
       
      if(document.frmEmployeeorderby.chkcircle)
      {
      //alert("hello");
            if(document.frmEmployeeorderby.chkcircle.length)
            {
         //alert("hello1");   
                            for(i=0;i<document.frmEmployeeorderby.chkcircle.length;i++)
                            {
                                    if(document.frmEmployeeorderby.chkcircle[i].checked==true)
                                            circle= circle+document.frmEmployeeorderby.chkcircle[i].value +",";
                                  
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
                            
          var url="../../../../../../EmpValidationSummaryServ_New_Interface?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
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
                                            
                                            //var type=document.frmEmployeeorderby.cmbolevel.options[document.frmEmployeeorderby.cmbolevel.selectedIndex].value;
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
                                      var url="../../../../../../EmpValidationSummaryServ_New_Interface?OLevel=division&regions="+region+"&offtype="+offtype+"&circles="+"nocircle";
                                       // alert(url);
                                      // document.frmEmployeeorderby.cmbregion.focus();
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
                                                    //var type=document.frmEmployeeorderby.cmbolevel.options[document.frmEmployeeorderby.cmbolevel.selectedIndex].value;
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
                                document.frmEmployeeorderby.cmbdivision.focus();
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

   if(document.frmEmployeeorderby.chkregion)
      {
      
            
            for(i=0;i<document.frmEmployeeorderby.chkregion.length;i++)
            {
                    document.frmEmployeeorderby.chkregion[i].checked=true;
                    
            }
           //  regiononchange();
        }
}


function offtypeselectAll()
{

   if(document.frmEmployeeorderby.chkofftype)
      {
      
            
            for(i=0;i<document.frmEmployeeorderby.chkofftype.length;i++)
            {
                    document.frmEmployeeorderby.chkofftype[i].checked=true;
                    
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
    if(document.frmEmployeeorderby.chkcircle)
      {
      
            for(i=0;i<document.frmEmployeeorderby.chkcircle.length;i++)
            {
                    document.frmEmployeeorderby.chkcircle[i].checked=true;
                    
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
    if(document.frmEmployeeorderby.chkdivision)
      {
      
            for(i=0;i<document.frmEmployeeorderby.chkdivision.length;i++)
            {
                    document.frmEmployeeorderby.chkdivision[i].checked=true;
                    
            }
        }
}

function oftypeonchange()
{

     if(document.frmEmployeeorderby.rad_off[3].checked)
     {
       getDivision('division');
     }
     /*
     var type=document.frmEmployeeorderby.cmbolevel.options[document.frmEmployeeorderby.cmbolevel.selectedIndex].value;
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
        var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ_New_Interface?OLevel=submit";
        
                          
        
                         if(document.frmEmployeeorderby.rad_off[1].checked)    
                          //  if(type=='RN')
                            {
                             // alert("inside region");
                            ////////
                            var region="";
                            if(document.frmEmployeeorderby.chkregion)
                              {
                              
                                        if(document.frmEmployeeorderby.chkregion.length)
                                        {
                                                  for(i=0;i<document.frmEmployeeorderby.chkregion.length;i++)
                                                    {
                                                            if(document.frmEmployeeorderby.chkregion[i].checked==true)
                                                              region= region+document.frmEmployeeorderby.chkregion[i].value +",";
                                                               
                                                            
                                                    }
                                                    if(region!="")
                                                    {
                                                        region=region.substring(0,region.length-1);
                                                         url=url+"&officeselected="+region;
                                                        // alert(url);
                                                         document.frmEmployeeorderby.officeselected.value=region;
                                                         //alert(document.frmEmployeeorderby.officeselected.value);
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Region.');
                                                            document.frmEmployeeorderby.cmbregion[0].focus(); 
                                                            return;
                                                    }
                                            
                                        }
                                        else{
                                           
                                                if(document.frmEmployeeorderby.chkregion.checked==true)
                                                {
                                                            region= document.frmEmployeeorderby.chkregion.value ;
                                                            document.frmEmployeeorderby.officeselected.value=region;
                                                }
                                                 else
                                                    {
                                                           alert('Select the Region..');
                                                            document.frmEmployeeorderby.cmbregion.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                 else
                                {
                                       alert('Select the Region...');
                                        document.frmEmployeeorderby.cmbregion.focus(); 
                                        return ;
                                }    
                            ////
                            
                            }
                            
                          else if(document.frmEmployeeorderby.rad_off[2].checked) 
                            
                            //else if(type=='CL')
                            {
                            
                            ////////
                            var circle="";
                            if(document.frmEmployeeorderby.chkcircle)
                              {
                              
                                       if(document.frmEmployeeorderby.chkcircle.length )
                                        {
                                                
                                                    for(i=0;i<document.frmEmployeeorderby.chkcircle.length;i++)
                                                    {
                                                            if(document.frmEmployeeorderby.chkcircle[i].checked==true)
                                                                    circle= circle+document.frmEmployeeorderby.chkcircle[i].value +",";
                                                                    // alert(circle);  
                                                    }
                                                    if(circle!="")
                                                    {
                                                        circle=circle.substring(0,circle.length-1);
                                                         url=url+"&officeselected="+circle;
                                                         document.frmEmployeeorderby.officeselected.value=circle;
                                                    }
                                                    else
                                                    {
                                                           alert('Select the Circle.');
                                                            document.frmEmployeeorderby.cmbcircle.focus(); 
                                                             return;
                                                    }
                                        }
                                        else{
                                          
                                                if(document.frmEmployeeorderby.chkcircle.checked==true)
                                                {
                                                            circle= document.frmEmployeeorderby.chkcircle.value ;
                                                            document.frmEmployeeorderby.officeselected.value=circle;
                                                           // alert(circle);
                                                }
                                                 else
                                                    {
                                                           alert('Select the Circle..');
                                                            document.frmEmployeeorderby.cmbcircle.focus(); 
                                                            return;
                                                    }
                                                     //alert(region);
                                                            
                                        }
                                }
                                else
                                {
                                       alert('Select the Circle...');
                                       try{
                                        document.frmEmployeeorderby.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmEmployeeorderby.cmbregion.focus(); 
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                            
                           else if(document.frmEmployeeorderby.rad_off[3].checked) 
                           // else  if(type=='DN')
                            {
                           
                            ////////
                            var division="";
                            if(document.frmEmployeeorderby.chkdivision)
                              {
                             //alert(document.frmEmployeeorderby.chkregion.length);
                                     if(document.frmEmployeeorderby.chkdivision.length )
                                        {
                                        
                                        
                                                for(i=0;i<document.frmEmployeeorderby.chkdivision.length;i++)
                                                {
                                                        if(document.frmEmployeeorderby.chkdivision[i].checked==true)
                                                                division= division+document.frmEmployeeorderby.chkdivision[i].value +",";
                                                        
                                                }
                                                
                                                if(division!="")
                                                {
                                                    division=division.substring(0,division.length-1);
                                                     url=url+"&officeselected="+division;
                                                     document.frmEmployeeorderby.officeselected.value=division;
                                                     
                                                }
                                                else
                                                {
                                                       alert('Select the Division.');
                                                        document.frmEmployeeorderby.cmbdivision.focus(); 
                                                         return;
                                                }
                                        }
                                        else
                                        {
                                                if(document.frmEmployeeorderby.chkdivision.checked==true)
                                                {
                                                                division= document.frmEmployeeorderby.chkdivision.value;
                                                                    //division=division.substring(0,division.length-1);
                                                                 url=url+"&officeselected="+division;
                                                                 document.frmEmployeeorderby.officeselected.value=division;
                                                                 
                                                }
                                                else
                                                {
                                                       alert('Select the Division..');
                                                        document.frmEmployeeorderby.cmbdivision.focus(); 
                                                         return;
                                                }
                                        
                                        
                                        }
                                }
                                 else
                                {
                                       alert('Select the Division...');
                                       try{
                                        document.frmEmployeeorderby.cmbdivision.focus(); 
                                        }catch(e)
                                        {
                                        try{
                                        document.frmEmployeeorderby.cmbcircle.focus(); 
                                        }
                                        catch(e){
                                        document.frmEmployeeorderby.cmbregion.focus(); 
                                        }
                                        }
                                         return;
                                }
                                    
                            ////
                            
                            }
                        
                    //}
                        
                    
               // }
       // }
       
      
        //select the output type
        if(document.frmEmployeeorderby.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.frmEmployeeorderby.outputtype.value='pdf';
        }
        else if(document.frmEmployeeorderby.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.frmEmployeeorderby.outputtype.value='excel';
        }
        else if(document.frmEmployeeorderby.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.frmEmployeeorderby.outputtype.value='html';
        }
        
   
    
    document.frmEmployeeorderby.action="../../../../../../ListofWorkingEmpSRCtlOfficeServ_New_Interface";
    document.frmEmployeeorderby.submit();
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
       // var type=document.frmEmployeeorderby.cmbolevel.options[document.frmEmployeeorderby.cmbolevel.selectedIndex].value;
    
         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
         var url="../../../../../../ListofWorkingEmpSRCtlOfficeServ_New_Interface?OLevel=offtype" ;
         //alert(url);
                
        document.frmEmployeeorderby.cmbofftype.focus();
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
                  document.frmEmployeeorderby.cmbcircle.disabled=false;
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
    


