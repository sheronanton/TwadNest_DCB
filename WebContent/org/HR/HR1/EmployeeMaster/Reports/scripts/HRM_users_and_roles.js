

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





//function officevisible(r,c,d,o)
//{
//   //alert("inside officevisible");
//    
//    var rn=document.getElementById("divregion");
//    rn.style.display=r;
//    var cl=document.getElementById("divcircle");
//    cl.style.display=c;
//    var dn=document.getElementById("divdivision");
//    dn.style.display=d;
//    var ot=document.getElementById("divofftype");
//    ot.style.display=o;
//    
//    var rn=document.getElementById("cmbregion");
//    rn.style.display=r;
//    var cl=document.getElementById("cmbcircle");
//    cl.style.display=c;
//    var dn=document.getElementById("cmbdivision");
//    dn.style.display=d;
//    var ot=document.getElementById("cmbofftype");
//    ot.style.display=o;
//}
//
//
//
//
//function selectoption2()
//{
//   
//    var offlevel=document.getElementById("trofficeselection");
//    offlevel.style.display="block";
//    document.getElementById("divallhier").style.display='block';
//    
//    
//    if(document.hrm_users_and_roles.rad_off[0].checked)
//    {
//       var offlevel=document.getElementById("trofficeselection");
//       offlevel.style.display="none";
//    }
//
//  if(document.hrm_users_and_roles.rad_off[1].checked)
//  {
//   // alert(document.hrm_users_and_roles.rad_off[1].checked);
//    var offlevel=document.getElementById("trofficeselection");
//    offlevel.style.display="block";
//    //document.getElementById("divallhier").style.display='block';
//    
//     officevisible('block','none','none','none');
//  }
//  
//  else if(document.hrm_users_and_roles.rad_off[2].checked)
//  {
//   // alert(document.hrm_users_and_roles.rad_off[2].checked);
//     var offlevel=document.getElementById("trofficeselection");
//    offlevel.style.display="block";
//    //document.getElementById("divallhier").style.display='block';
//  
//     officevisible('block','block','none','none');
//  }
//  
//  else if(document.hrm_users_and_roles.rad_off[3].checked)
//  {
//     var offlevel=document.getElementById("trofficeselection");
//    offlevel.style.display="block";
//    //document.getElementById("divallhier").style.display='block';
//  
//    officevisible('block','block','block','block','block');
//  }
//  
//  
//  document.hrm_users_and_roles.cmbcircle.disabled=true;
//   // document.hrm_users_and_roles.cmbofftype.disabled=true;
//    document.hrm_users_and_roles.cmbdivision.disabled=true;
//    
//    var iframe=document.getElementById("diviframeregion");
//    iframe.style.visibility='hidden';
//    var iframe=document.getElementById("diviframecircle");
//    iframe.style.visibility='hidden';
//    var iframe=document.getElementById("diviframeofftype");
//    iframe.style.visibility='hidden';
//    var iframe=document.getElementById("diviframedivision");
//    iframe.style.visibility='hidden';
//    
//    
//    regionflag=true;
//    circleflag=true;
//    offtypeflag=true;
//    divisionflag=true;
//   
//
//}
//
//
//var s=0;
//var hier=true;
//var level=true;
//var city=true;
//var  other=true;
//
//  
//function getRegion()
//    {
//   
//              
//        
//        if(regionflag==true)
//        {
//               
//                 var iframe=document.getElementById("diviframeregion");
//                 iframe.style.visibility='visible';
//                 iframe.focus();
//               // return;
//        }
//       
//    
//         
//         var url="../../../../../../EmpValidationDetailServ_New_Interface?OLevel=region" ;
//        //alert(url);
//                
//        document.hrm_users_and_roles.cmbregion.focus();
//        var req=getTransport();
//        req.open("GET",url,false);        
//        req.onreadystatechange=function()
//        {
//            //requesthandle(req);
//            if(req.readyState==4)
//            { 
//                  if(req.status==200)
//                  {  
//                   // alert(req.responseText);
//                 
//                 
//                  
//                    var iframe=document.getElementById("diviframeregion");
//                    iframe.style.visibility='visible';
//                    iframe.focus();
//                   // alert(navigator.appName);
//                   // alert(navigator.appName.indexOf('Microsoft'));
//                   
//                  
//                    if(navigator.appName.indexOf('Microsoft')!=-1)
//                        iframe.innerHTML=req.responseText;
//                    else
//                        iframe.innerText=req.responseText;
//                    iframe.innerHTML=req.responseText;
//                   
//                  document.hrm_users_and_roles.cmbcircle.disabled=false;
//                   var iframe=document.getElementById("diviframecircle");
//                            iframe.style.visibility='hidden';
//                   var iframe=document.getElementById("diviframedivision");
//                            iframe.style.visibility='hidden';
//                   
//                   regionflag=true;
//                  }
//            }
//        }
//        req.send(null);
//    }
//    
//    
//function getCircle(cir)
//{
//      //alert("inside getcircle");
//        
//       var region="";
//     //   var type=document.hrm_users_and_roles.cmbolevel.options[document.hrm_users_and_roles.cmbolevel.selectedIndex].value;
//     
//      
//    
//    //  if(document.hrm_users_and_roles.chkregion)
//      //{
//      
//          //alert(document.hrm_users_and_roles.chkregion);
//           
//               if(document.hrm_users_and_roles.chkregion.length > 0)
//              {
//              
//                 //alert(document.hrm_users_and_roles.chkregion.length);
//             
//                            for(i=0;i<document.hrm_users_and_roles.chkregion.length;i++)
//                            {
//                              //  alert("document.hrm_users_and_roles.chkregion[i].checked..."+document.hrm_users_and_roles.chkregion[i].checked);
//                                    if(document.hrm_users_and_roles.chkregion[i].checked==true)
//                                            region= region+document.hrm_users_and_roles.chkregion[i].value +",";
//                                            
//                                           // alert("region..."+region);
//                                    
//                            }
//                            
//                            if(region!="")
//                            {
//                              // alert("inside 1");
//                                 if(circleflag==true && cir=='null')
//                                {
//                                  //     alert("inside 2");
//                                         var iframe=document.getElementById("diviframecircle");
//                                            iframe.style.visibility='visible';
//                                          iframe.focus();
//                                           document.hrm_users_and_roles.cmbdivision.disabled=false;
//                                      //  return;
//                                }
//                                region=region.substring(0,region.length-1);
//                               // alert("region1..."+region);
//                                //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
//                                
//                                var url="../../../../../../HRM_Employee_Details_checklist?OLevel=circle&regions="+region;
//                                //alert(url);
//                              // document.hrm_users_and_roles.cmbregion.focus();
//                                var req=getTransport();
//                                req.open("GET",url,false);        
//                                req.onreadystatechange=function()
//                                {
//                                    //requesthandle(req);
//                                    if(req.readyState==4)
//                                    { 
//                                          if(req.status==200)
//                                          {  
//                              //             alert("success");
//                                         //  var iframe=document.getElementById("diviframecircle");
//                                            // if((type=='CL' || type=='DN') && cir=='null' )
//                                            if(circleflag==true && cir=='null')
//                                            {
//                                         //   alert("see these");
//                                            var iframe=document.getElementById("diviframecircle");
//                                            iframe.style.visibility='visible';
//                                            iframe.focus();
//                                            
//                                            if(navigator.appName.indexOf('Microsoft')!=-1)
//                                              iframe.innerHTML=req.responseText;
//                                              else
//                                              iframe.innerText=req.responseText;
//                                            iframe.innerHTML=req.responseText;
//                                            document.hrm_users_and_roles.cmbdivision.disabled=false;
//                                              var iframe=document.getElementById("diviframedivision");
//                                             iframe.style.visibility='hidden';
//                                            
//                                            
//                                            }
//                                             iframe.innerHTML=req.responseText;
//                                             
//                                         //    alert(iframe.innerHTML);
//                                             /*
//                                             if(navigator.appName.indexOf('Microsoft')!=-1)
//                                              iframe.innerHTML=req.responseText;
//                                              else
//                                              iframe.innerText=req.responseText;
//                                              */
//                                             circleflag=true;
//                                            
//                                          }
//                                    }
//                                }
//                                req.send(null);
//                            }
//                            else
//                            {
//                                var iframe=document.getElementById("diviframecircle");
//                                iframe.style.visibility='hidden';
//                                document.hrm_users_and_roles.cmbdivision.disabled=false;
//                                document.hrm_users_and_roles.cmbcircle.focus();
//                                alert('Please Select a Region');
//                            }
//            }
//            else
//            {
//            
//                             if(document.hrm_users_and_roles.chkregion.checked==true)
//                             {
//                                            region= document.hrm_users_and_roles.chkregion.value ;
//                                            //alert(region);
//                                    
//                                    
//                                         if(circleflag==true && cir=='null')
//                                        {
//                                               
//                                                 var iframe=document.getElementById("diviframecircle");
//                                                    iframe.style.visibility='visible';
//                                                  iframe.focus();
//                                                   document.hrm_users_and_roles.cmbdivision.disabled=false;
//                                               // return;
//                                        }
//                                       // region=region.substring(0,region.length-1);
//                                       // var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=circle&regions="+region;
//                                        
//                                        var url="../../../../../../HRM_Employee_Details_checklist?OLevel=circle&regions="+region;
//                                        //alert(url);
//                                      // document.hrm_users_and_roles.cmbregion.focus();
//                                        var req=getTransport();
//                                        req.open("GET",url,false);        
//                                        req.onreadystatechange=function()
//                                        {
//                                            //requesthandle(req);
//                                            if(req.readyState==4)
//                                            { 
//                                                  if(req.status==200)
//                                                  {  
//                                                  //alert('success');
//                                                   var iframe=document.getElementById("diviframecircle");
//                                                    // if((type=='CL' || type=='DN') && cir=='null' )
//                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
//                                                       iframe.innerHTML=req.responseText;
//                                                         else
//                                                       iframe.innerText=req.responseText;
//                                                    iframe.innerHTML=req.responseText;
//                                                    if(cir=='null')
//                                                    {
//                                                    
//                                                    iframe.style.visibility='visible';
//                                                    iframe.focus();
//                                                    
//                                                    document.hrm_users_and_roles.cmbdivision.disabled=false;
//                                                      var iframe=document.getElementById("diviframedivision");
//                                                     iframe.style.visibility='hidden';
//                                                    
//                                                    
//                                                    }
//                                                     iframe.innerHTML=req.responseText;
//                                                     
//                                            //         alert(iframe.innerHTML);
//                                                     /*
//                                                     if(navigator.appName.indexOf('Microsoft')!=-1)
//                                                       iframe.innerHTML=req.responseText;
//                                                         else
//                                                       iframe.innerText=req.responseText;
//                                                       */
//                                                     circleflag=true;
//                                                    
//                                                  }
//                                            }
//                                        }
//                                        req.send(null);
//                            }
//                            else
//                            {
//                               
//                            
//                                var iframe=document.getElementById("diviframecircle");
//                                iframe.style.visibility='hidden';
//                                document.hrm_users_and_roles.cmbdivision.disabled=false;
//                                document.hrm_users_and_roles.cmbcircle.focus();
//                                alert('Please Select a Region');
//                            }
//
//            
//            
//            
//            
//            }
//            
//      //}
//      
//   
//}
//
//
//function getDivision(div)
//{
//        
//      //alert('division');
//       var region="";
//       var circle="";
//       var offtype="";
////      if(document.hrm_users_and_roles.chkregion)
////      {
////         region=document.hrm_users_and_roles.chkregion.value;
////         region+=",";
////         }   
//         
//         if(document.hrm_users_and_roles.chkregion.length>0)
//              {
//                   for(i=0;i<document.hrm_users_and_roles.chkregion.length;i++)
//                            {
//                            //  alert(document.hrm_users_and_roles.chkregion[i].value);
//                                    if(document.hrm_users_and_roles.chkregion[i].checked==true)
//                                            region= region+document.hrm_users_and_roles.chkregion[i].value +",";
//                                          // alert('region...'+region);
//                                         
//                                    
//                            }
//                 }
//                 else
//                 {
//                        region=document.hrm_users_and_roles.chkregion.value;
//                        region+=",";
//                 }
//     
//       if(document.hrm_users_and_roles.chkofftype)
//       {
//               
//         if(document.hrm_users_and_roles.chkofftype.length)
//         {
//                    
//                  for(i=0;i<document.hrm_users_and_roles.chkofftype.length;i++)
//                  {
//                    if(document.hrm_users_and_roles.chkofftype[i].checked==true)
//                     offtype= offtype+"'"+document.hrm_users_and_roles.chkofftype[i].value+"'"+",";            
//                                      
//                  }
//               
//         }
//       }
//       
//      if(document.hrm_users_and_roles.chkcircle)
//      {
//      //alert("hello");
//            if(document.hrm_users_and_roles.chkcircle.length)
//            {
//         //alert("hello1");   
//                            for(i=0;i<document.hrm_users_and_roles.chkcircle.length;i++)
//                            {
//                                    if(document.hrm_users_and_roles.chkcircle[i].checked==true)
//                                            circle= circle+document.hrm_users_and_roles.chkcircle[i].value +",";
//                                  
//                            }
//                            
//                            
//              
//         }                  
//         if(divisionflag==true && div=='null')
//        {
//           
//               
//                 var iframe=document.getElementById("diviframedivision");
//                    iframe.style.visibility='visible';
//                    iframe.focus();
//               // return;
//        }
//            if(circle!='');  
//              circle=circle.substring(0,circle.length-1);
//            
//            offtype=offtype.substring(0,offtype.length-1);
//            region=region.substring(0,region.length-1);
//                            
//          var url="../../../../../../EmpValidationSummaryServ_New_Interface?OLevel=division&circles="+circle+"&offtype="+offtype+"&regions="+region;
//          //alert("url"+url);
//                                var req=getTransport();
//                                req.open("GET",url,false);        
//                                req.onreadystatechange=function()
//                                {
//                                    //requesthandle(req);
//                                    if(req.readyState==4)
//                                    { 
//                                          if(req.status==200)
//                                          { 
//                                           // alert("success");
//                                            var iframe=document.getElementById("diviframedivision");
//                                            
//                                            //var type=document.hrm_users_and_roles.cmbolevel.options[document.hrm_users_and_roles.cmbolevel.selectedIndex].value;
//                                            if(div=='null')
//                                            {
//                                            //alert("visible");
//                                            iframe.style.visibility='visible';
//                                            iframe.focus();
//                                            }
//                                            if(navigator.appName.indexOf('Microsoft')!=-1)
//                                              iframe.innerHTML=req.responseText;
//                                              else
//                                              iframe.innerText=req.responseText;
//                                            iframe.innerHTML=req.responseText;
//                                             // alert(req.responseText);
//                                            // iframe.innerHTML=req.responseText;
//                                             
//                                           divisionflag=true;  
//                                          }
//                                    }
//                                }
//                                req.send(null);
//               }
//                else
//                {
//                
//                                 
//                                      offtype=offtype.substring(0,offtype.length-1);
//                                      region=region.substring(0,region.length-1);
//                                      circle=null; 
//                                      var url="../../../../../../EmpValidationSummaryServ_New_Interface?OLevel=division&regions="+region+"&offtype="+offtype+"&circles="+"nocircle";
//                                       // alert(url);
//                                      // document.hrm_users_and_roles.cmbregion.focus();
//                                        var req=getTransport();
//                                        req.open("GET",url,false);        
//                                        req.onreadystatechange=function()
//                                        {
//                                            //requesthandle(req);
//                                            if(req.readyState==4)
//                                            { 
//                                                  if(req.status==200)
//                                                  { 
//                                                    var iframe=document.getElementById("diviframedivision");
//                                                    //var type=document.hrm_users_and_roles.cmbolevel.options[document.hrm_users_and_roles.cmbolevel.selectedIndex].value;
//                                                    if(div=='null')
//                                                    {
//                                                    
//                                                    iframe.style.visibility='visible';
//                                                    iframe.focus();
//                                                    }
//                                                    if(navigator.appName.indexOf('Microsoft')!=-1)
//                                              iframe.innerHTML=req.responseText;
//                                              else
//                                              iframe.innerText=req.responseText;
//                                                    iframe.innerHTML=req.responseText;      
//                                                     //iframe.innerHTML=req.responseText;
//                                                     
//                                                   divisionflag=true;  
//                                                  }
//                                            }
//                                        }
//                                        req.send(null);
//                            }
//                           /* else
//                            {
//                                var iframe=document.getElementById("diviframedivision");
//                                iframe.style.visibility='hidden';
//                                document.hrm_users_and_roles.cmbdivision.focus();
//                                alert('Please Select a Circle');
//                            }*/
//                           // alert("circles:"+circle);
//}
//
//function regionclose()
//{
//    
//    var iframe=document.getElementById("diviframeregion");
//    iframe.style.visibility='hidden';
//  
//}
//
//function offtypeclose()
//{
//    
//    var iframe=document.getElementById("diviframeofftype");
//    iframe.style.visibility='hidden';
//  
//}
//
//
//function regionselectAll()
//{
//
//   if(document.hrm_users_and_roles.chkregion)
//      {
//      
//            
//            for(i=0;i<document.hrm_users_and_roles.chkregion.length;i++)
//            {
//                    document.hrm_users_and_roles.chkregion[i].checked=true;
//                    
//            }
//           //  regiononchange();
//        }
//}
//
//
//function offtypeselectAll()
//{
//
//   if(document.hrm_users_and_roles.chkofftype)
//      {
//      
//            
//            for(i=0;i<document.hrm_users_and_roles.chkofftype.length;i++)
//            {
//                    document.hrm_users_and_roles.chkofftype[i].checked=true;
//                    
//            }
//             oftypeonchange();
//        }
//}
//
//
//function circleclose()
//{
//    
//    var iframe=document.getElementById("diviframecircle");
//    iframe.style.visibility='hidden';
//  
//}
//
//
//function circleselectAll()
//{
//    if(document.hrm_users_and_roles.chkcircle)
//      {
//      
//            for(i=0;i<document.hrm_users_and_roles.chkcircle.length;i++)
//            {
//                    document.hrm_users_and_roles.chkcircle[i].checked=true;
//                    
//            }
//           // circleonchange();
//        }
//}
//
//function divisionclose()
//{
//    
//    var iframe=document.getElementById("diviframedivision");
//    iframe.style.visibility='hidden';
//  
//}
//
//function divisionselectAll()
//{
//    if(document.hrm_users_and_roles.chkdivision)
//      {
//      
//            for(i=0;i<document.hrm_users_and_roles.chkdivision.length;i++)
//            {
//                    document.hrm_users_and_roles.chkdivision[i].checked=true;
//                    
//            }
//        }
//}
//
//function oftypeonchange()
//{
//
//     if(document.hrm_users_and_roles.rad_off[3].checked)
//     {
//       getDivision('division');
//     }
//     /*
//     var type=document.hrm_users_and_roles.cmbolevel.options[document.hrm_users_and_roles.cmbolevel.selectedIndex].value;
//   circlefalg=false;
//    if(type=='CL' || type=='DN')
//    {
//        getCircle('circle');
//    }
//   */
//}



function frmsubmit()
{
  
    //alert('inside submit');
        var url="../../../../../../HRM_users_and_roles_report?OLevel=submit";
        
        if((document.hrm_users_and_roles.role_id.selectedIndex==0))
     		  
        {
     	   alert("Please select any Role ");
     	   return
        }                    
                       
        if(document.hrm_users_and_roles.optoutputtype[0].checked==true)
        {
                url=url+"&outputtype=pdf";
                document.hrm_users_and_roles.optoutputtype.value='pdf';
        }
        else if(document.hrm_users_and_roles.optoutputtype[1].checked==true)
        {
                url=url+"&outputtype=excel";
                document.hrm_users_and_roles.optoutputtype.value='excel';
        }
        else if(document.hrm_users_and_roles.optoutputtype[2].checked==true)
        {
                url=url+"&outputtype=html";
                 document.hrm_users_and_roles.optoutputtype.value='html';
        }
        
        var role_id=document.hrm_users_and_roles.role_id.value;
       
   url=url+"&role_id="+role_id;
  
   
    document.hrm_users_and_roles.action="../../../../../../HRM_users_and_roles_report";
    document.hrm_users_and_roles.submit();
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}



//function getofftype()
//    {
//      //  alert(regionflag);
//           //alert("inside office type");   
//        
//        if(offtypeflag==true)
//        {
//               
//                 var iframe=document.getElementById("diviframeofftype");
//                 iframe.style.visibility='visible';
//                 iframe.focus();
//               // return;
//        }
//       // var type=document.hrm_users_and_roles.cmbolevel.options[document.hrm_users_and_roles.cmbolevel.selectedIndex].value;
//    
//         //var url="../../../../../../ListofEmpValidationSummaryServ.rep?OLevel=region" ;
//         var url="../../../../../../HRM_Employee_Details_checklist?OLevel=offtype" ;
//         //alert(url);
//                
//        document.hrm_users_and_roles.cmbofftype.focus();
//        var req=getTransport();
//        req.open("GET",url,false);        
//        req.onreadystatechange=function()
//        {
//            //requesthandle(req);
//            if(req.readyState==4)
//            { 
//                  if(req.status==200)
//                  {  
//                   // alert(req.responseText);
//                 
//                 // document.frames("iframregion").document.body.innerHTML=req.responseText;
//                  //(document.frames("iframregion").document.body.innerText);
//                    var iframe=document.getElementById("diviframeofftype");
//                    iframe.style.visibility='visible';
//                    iframe.focus();
//                   // alert(navigator.appName);
//                   // alert(navigator.appName.indexOf('Microsoft'));
//                   
//                  
//                    if(navigator.appName.indexOf('Microsoft')!=-1)
//                        iframe.innerHTML=req.responseText;
//                    else
//                        iframe.innerText=req.responseText;
//                    iframe.innerHTML=req.responseText;
//                   /*
//                  document.hrm_users_and_roles.cmbcircle.disabled=false;
//                   var iframe=document.getElementById("diviframecircle");
//                            iframe.style.visibility='hidden';
//                   var iframe=document.getElementById("diviframedivision");
//                            iframe.style.visibility='hidden';
//                   */
//                   offtypeflag=true;
//                  }
//            }
//        }
//        req.send(null);
//    }
//    
//

