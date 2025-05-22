//alert('detail');

var regionflag=false;
var circleflag=true;
var divisionflag=true;

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

var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;

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



function designationselectAll()
{
    if(document.hrm_financial_projection_for_year.chkdesig)
      {
      
            for(i=0;i<document.hrm_financial_projection_for_year.chkdesig.length;i++)
            {
                    document.hrm_financial_projection_for_year.chkdesig[i].checked=true;
                    
            }
        }
}
function rankselectAll()
{
    if(document.hrm_financial_projection_for_year.chkrank)
      {
      
            for(i=0;i<document.hrm_financial_projection_for_year.chkrank.length;i++)
            {
                    document.hrm_financial_projection_for_year.chkrank[i].checked=true;
                    
            }
        }
}
function cadreselectAll()
{
    if(document.hrm_financial_projection_for_year.chkcadre)
      {
      
            for(i=0;i<document.hrm_financial_projection_for_year.chkcadre.length;i++)
            {
                    document.hrm_financial_projection_for_year.chkcadre[i].checked=true;
                    
            }
        }
}








function hidedisignation()
{
    document.Emp_Retire_Befini_Rep.optdesignation[0].checked=true;
   var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="none";
    
      var offlevel=document.getElementById("divdest");
    offlevel.style.visibility="hidden";
     var offlevel=document.getElementById("cmbDesignation");
    offlevel.style.visibility="hidden";
    document.Emp_Retire_Befini_Rep.cmbsgroup.value="0";
}

function showdesignation()
{
   document.Emp_Retire_Befini_Rep.optdesignation[1].checked=true;
    var offlevel=document.getElementById("trsergroup");
    offlevel.style.display="block";
}

function getDesignation()
{
document.getElementById("desigblock").style.display='block';
//alert("getDesisg");
   var type=document.hrm_financial_projection_for_year.cmbsgroup.options[document.hrm_financial_projection_for_year.cmbsgroup.selectedIndex].value;
   //alert(type);
   if(type!=0)
    {
    //alert("inside");
           // var din=document.getElementById("divdest");
           // din.style.visibility="visible";
           // document.Emp_Retire_Befini_Rep.cmbDesignation.style.visibility="visible";
            loadOfficesByType(type);
    }
   else
    {
             var des=document.getElementById("cmbDesignation");
             des.innerHTML='';
            // var din=document.getElementById("divdest");
           //  din.style.visibility="hidden";
           //  document.Emp_Retire_Befini_Rep.cmbDesignation.style.visibility="hidden";
     }
}
    
/*function loadOfficesByType(type)
    {
        //alert(type);
        var type=document.Emp_Retire_Befini_Rep.cmbsgroup.options[document.Emp_Retire_Befini_Rep.cmbsgroup.selectedIndex].value;
        //startwaiting(document.Emp_Retire_Befini_Rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
        req.onreadystatechange=function()
        {
            
             loadDesignation(req);
        }
        req.send(null);
    }*/
    
    
    function loadOfficesByType(type)
    {
       
        var type=document.hrm_financial_projection_for_year.cmbsgroup.options[document.hrm_financial_projection_for_year.cmbsgroup.selectedIndex].value;
        //startwaiting(document.Emp_Retire_Befini_Rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Desig&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
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
                
                // stopwaiting(document.Emp_Retire_Befini_Rep);
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
  
	var des_id="";
    var val=nullcheck();
    if(val ==true)
    	{
    	
    	var des_id="";
    	if(document.hrm_financial_projection_for_year.chkdesig)
        {
          if(document.hrm_financial_projection_for_year.chkdesig.length )
           {
    	    for(i=0;i<document.hrm_financial_projection_for_year.chkdesig.length;i++)
              {
              if(document.hrm_financial_projection_for_year.chkdesig[i].checked==true)
                    des_id= des_id+document.hrm_financial_projection_for_year.chkdesig[i].value +",";                                                                                                       
               }
               if(des_id!="")
               {
              des_id= des_id.substring(0, des_id.length-1);
              }
              else
              {
               alert("Please Select Designation!");
               return false;
              }
    	   }
        }
    	var rand_id="";
    	if(document.hrm_financial_projection_for_year.chkrank)
	    {
	      if(document.hrm_financial_projection_for_year.chkrank.length )
	       {
		    for(i=0;i<document.hrm_financial_projection_for_year.chkrank.length;i++)
	          {
	          if(document.hrm_financial_projection_for_year.chkrank[i].checked==true)
	                rand_id= rand_id+document.hrm_financial_projection_for_year.chkrank[i].value +",";                                                                                                       
	           }
	           
	          if(rand_id!="")
	          {
	          rand_id= rand_id.substring(0, rand_id.length-1);
	          }
	          else
	          {
	           alert("Please Select Rank! ");
	           return false;
	          }
		   }
		   
		     
	    }
    	
    	var cadre_id="";
    	if(document.hrm_financial_projection_for_year.chkcadre)
        {
          if(document.hrm_financial_projection_for_year.chkcadre.length )
           {
    	    for(i=0;i<document.hrm_financial_projection_for_year.chkcadre.length;i++)
              {
              if(document.hrm_financial_projection_for_year.chkcadre[i].checked==true)
                    cadre_id= cadre_id+document.hrm_financial_projection_for_year.chkcadre[i].value +",";                                                                                                       
               }
               if(cadre_id!="")
               {
                cadre_id= cadre_id.substring(0, cadre_id.length-1);
                }
                else
                {
                alert("Please Select Cadre!");
                return false;
                }
    	   }
        }
        
var fin_year=document.getElementById("fin_year").value;

var des="";
var desall="";

if(document.hrm_financial_projection_for_year.optselect[0].checked==true)
	{
	des="DESIG";
	}
else if(document.hrm_financial_projection_for_year.optselect[1].checked==true)
	{
	des="RANK";
	}
else if(document.hrm_financial_projection_for_year.optselect[2].checked==true)
	{
	des="CADRE";
	}

    	var url="&des_id="+des_id+"&rand_id="+rand_id+"&cadre_id="+cadre_id+"&fin_year="+fin_year+"&des="+des;
    	
        document.hrm_financial_projection_for_year.action="hrm_financial_projection_for_year_details.jsp?"+url;
     	document.hrm_financial_projection_for_year.method="POST";
        document.hrm_financial_projection_for_year.submit();
    	}
   /* var req=getTransport();
    req.open("POST",url,false);  
    req.send(null);*/

}



//////////////////////////// for next purpose ///////////////////////
///////////////////////  for designation,cadre,rank   ///////////////////////

function sellectall()
{
      var id=document.getElementById("divselect");
      id.style.display='block';          
        
}

function hidedesig()
{
//alert("here");
   //if(document.Emp_Retire_Befini_Rep.optselect[0].checked)
   if(document.hrm_financial_projection_for_year.optselectgrp[0].checked)
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



    
    if(document.hrm_financial_projection_for_year.optselect[0].checked)
    {
      
        var id=document.getElementById("divdest");
        id.style.display='block';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
        
        var id=document.getElementById("divcadre");
        id.style.display='none';
        if(document.hrm_financial_projection_for_year.cmbsgroup)
        {
        	document.hrm_financial_projection_for_year.cmbsgroup.value="0";
        }
        if(document.hrm_financial_projection_for_year.chkdesig)
        {
    
                 if(document.hrm_financial_projection_for_year.chkdesig.length)
                {
                //alert("2");
               
                              for(i=0;i<document.hrm_financial_projection_for_year.chkdesig.length;i++)
                              {
                              
                                      document.hrm_financial_projection_for_year.chkdesig[i].checked=false;
                                         
                              }
                              
                              
                 }
          } 
    }
    
   
    else if(document.hrm_financial_projection_for_year.optselect[1].checked)
    {
      
         var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='block';
         var id=document.getElementById("divcadre");
        id.style.display='none';     
        if(document.hrm_financial_projection_for_year.cmbsgroup1)
        {
        	document.hrm_financial_projection_for_year.cmbsgroup1.value="0";
        }
        if(document.hrm_financial_projection_for_year.chkrank)
        {
       // alert("1");
                 if(document.hrm_financial_projection_for_year.chkrank.length)
                {
            //  alert("2");
               
                              for(i=0;i<document.hrm_financial_projection_for_year.chkrank.length;i++)
                              {
                              
                                      document.hrm_financial_projection_for_year.chkrank[i].checked=false;
                                          
                              }
                              
                              
                             
                 }
          }  
    }
   
    else if(document.hrm_financial_projection_for_year.optselect[2].checked)
    {
       
        var id=document.getElementById("divdest");
        id.style.display='none';
        var divdesigid=document.getElementById("divdesignation");
        divdesigid.style.visibility='hidden';
        var id=document.getElementById("divrank");
        id.style.display='none';
       
        var id=document.getElementById("divcadre");
        id.style.display='block';    
        if(document.Emp_Retire_Befini_Rep.cmbsgroup2)
        {
        	document.hrm_financial_projection_for_year.cmbsgroup2.value="0";
        }
        if(document.hrm_financial_projection_for_year.chkcadre)
        {
        //alert("1");
                 if(document.hrm_financial_projection_for_year.chkcadre.length)
                {
            //   alert("2");
               
                              for(i=0;i<document.hrm_financial_projection_for_year.chkcadre.length;i++)
                              {
                              
                                      document.hrm_financial_projection_for_year.chkcadre[i].checked=false;
                                          
                              }
                              
                             
                 }
          }  
    
    }

}    
    
function checkGroup()
{
        
  var type=document.Emp_Retire_Befini_Rep.cmbsgroup.options[document.Emp_Retire_Befini_Rep.cmbsgroup.selectedIndex].value;
  if(type==0)
   {
     alert('Select Service Group');
     document.Emp_Retire_Befini_Rep.cmbsgroup.focus();
     return false;
   }
}    



function getDesignation1()
{
document.getElementById("desigblock").style.display='block';
  var type=document.hrm_financial_projection_for_year.cmbsgroup1.options[document.hrm_financial_projection_for_year.cmbsgroup1.selectedIndex].value;
 // alert(type);    
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
        var type=document.hrm_financial_projection_for_year.cmbsgroup1.options[document.hrm_financial_projection_for_year.cmbsgroup1.selectedIndex].value;
        //startwaiting(document.Emp_Retire_Befini_Rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Rank&cmbsgroup=" + type ;
       //alert(url);
       var req=getTransport();
       req.open("GET",url,true);        
	    req.onreadystatechange=function()
	    {
	    	
	        //requesthandle(req);
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
                
                 stopwaiting(document.Emp_Retire_Befini_Rep);
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
        
        var type=document.hrm_financial_projection_for_year.cmbsgroup1.options[document.hrm_financial_projection_for_year.cmbsgroup1.selectedIndex].value;
        if(type==0)
        {
            alert('Select Service Group');
            document.Emp_Retire_Befini_Rep.cmbsgroup1.focus();
            return false;
        }
}

function getDesignation2()
{
document.getElementById("desigblock").style.display='block';
  var type=document.hrm_financial_projection_for_year.cmbsgroup2.options[document.hrm_financial_projection_for_year.cmbsgroup2.selectedIndex].value;
       
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
        var type=document.hrm_financial_projection_for_year.cmbsgroup2.options[document.hrm_financial_projection_for_year.cmbsgroup2.selectedIndex].value;
        //startwaiting(document.Emp_Retire_Befini_Rep) ;
         //service=null;
      
       //var url="../../../../../../EmpServicePopupServ.view?Command=SGroup&cmbsgroup=" + type ;
       var url="../../../../../../DesigCadRankServ_New?Command=Cadre&cmbsgroup=" + type ;
       //alert(url);
        var req=getTransport();
        req.open("GET",url,true);        
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
                
                 stopwaiting(document.Emp_Retire_Befini_Rep);
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
        
  var type=document.hrm_financial_projection_for_year.cmbsgroup2.options[document.hrm_financial_projection_for_year.cmbsgroup2.selectedIndex].value;
  if(type==0)
  {
     alert('Select Service Group');
     document.Emp_Retire_Befini_Rep.cmbsgroup2.focus();
     return false;
  }
}

function desigChange()
{
var desig="";
//alert('designationchange');
if(document.hrm_financial_projection_for_year.chkdesig)
      {
     // alert("1");
               if(document.hrm_financial_projection_for_year.chkdesig.length)
              {
              //alert("2");
             
                            for(i=0;i<document.hrm_financial_projection_for_year.chkdesig.length;i++)
                            {
                            
                                    if(document.hrm_financial_projection_for_year.chkdesig[i].checked==true)
                                        { 
                                           //alert("inside");
                                            desig=desig+document.hrm_financial_projection_for_year.chkdesig[i].value +",";
                                         }
                            }
                            
                            if(desig!="")
                            {
                            //alert("ids:"+desig);
                            desig=desig.substring(0,desig.length-1);
                            var url="../../../../../../RetirementList1_Serv1_all?desigs="+desig;
                            }
               }
        }  
        
}

function rankChange()
{
var rank="";
//alert('rank');
if(document.hrm_financial_projection_for_year.chkdesig)
      {
      //alert("1");
               if(document.hrm_financial_projection_for_year.chkrank.length)
              {
        //      alert("2");
             
                            for(i=0;i<document.hrm_financial_projection_for_year.chkrank.length;i++)
                            {
                            
                                    if(document.hrm_financial_projection_for_year.chkrank[i].checked==true)
                                        { 
                                           //alert("inside");
                                            rank=rank+document.hrm_financial_projection_for_year.chkrank[i].value +",";
                                         }
                            }
                            
                            if(rank!="")
                            {
                            //alert("ids:"+rank);
                            rank=rank.substring(0,rank.length-1);
                            var url="../../../../../../RetirementList1_Serv1_all?ranks="+rank;
                            }
               }
        }  
        
}
    
function cadreChange()
{
var cadre="";
//alert('cadre');
if(document.hrm_financial_projection_for_year.chkcadre)
      {
      //alert("1");
               if(document.hrm_financial_projection_for_year.chkcadre.length)
              {
             // alert("2");
             
                            for(i=0;i<document.hrm_financial_projection_for_year.chkcadre.length;i++)
                            {
                            
                                    if(document.hrm_financial_projection_for_year.chkcadre[i].checked==true)
                                        { 
                                           //alert("inside");
                                            cadre=cadre+document.hrm_financial_projection_for_year.chkcadre[i].value +",";
                                         }
                            }
                            
                            if(cadre!="")
                            {
                            //alert("ids:"+cadre);
                            cadre=cadre.substring(0,cadre.length-1);
                            var url="../../../../../../RetirementList1_Serv1_all?cadres="+cadre;
                            }
               }
        }  
        
}

/*function enableHierarhy()
{
alert('show');
alert(document.getElementById("divallhier"));
document.getElementById("divallhier").style.display='block';
}*/


function checkdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){}
        //exception  start
        
         t.value=c;
       /*     var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<1940)
            {
            
                   // alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    //t.value="";
                   // t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        //t.value="";
                        //t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                               // alert('Entered date should be less than current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                //t.value="";
                                //t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    //t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<1940)
            {
            
                    //alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    //t.value="";
                    //t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        // alert('Entered date should be less than current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        //t.value="";
                        //t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than current date and \n year should be greater than or equal to '+1940);
                               // t.value="";
                               // t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                   // t.value="";
                    return false;
                }
        }*/
            
    }
    else
    {
            alert('Date format  should be (dd/mm/yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    
}

function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}



//second calendar
/*

function checkcurdt(t)
{
  
    if(t.value.length==0)
        return false;
    if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
    {
      
       
        // var c=t.value.replace(/-/g,'/');
         var c=t.value;
        try{
        var f=DateFormat(t,c,event,true,'3');
        }catch(e){
        //exception  start
        
         t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                        alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+ _Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
             if(err!=0)
                {
                    t.value="";
                    return false;
                }
            return true;
        
        
        //exception end
        
        }
        if( f==true)
        {
            //alert(f);
            //t.value=c.replace(/\//g,'-');
            t.value=c;
            var sc=t.value.split('/');
            var currenDay =sc[0];
            var currentMonth=sc[1];
            var currentYear=sc[2];
            //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
         
            if(currentYear > getCurrentYear()  || currentYear<_Service_Period_Beg_Year)
            {
            
                    alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                    t.value="";
                    t.focus();
                    return false;
           } 
           else if(currentYear == getCurrentYear())
            {
                    if( currentMonth > getCurrentMonth())
                    {
                         alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                        t.value="";
                        t.focus();
                        return false;
                    }
                    else if( currentMonth == getCurrentMonth())
                    {
                        if(currenDay > getCurrentDay() )
                        {
                                alert('Entered date should be less than or equal to current date and \n year should be greater than or equal to '+_Service_Period_Beg_Year);
                                t.value="";
                                t.focus();
                                return false;
                        }
                    }
                    
            }
            
            t.value=c;
           
            return true;
            
        }
        else
        {
                if(err!=0)
                {
                    t.value="";
                    return false;
                }
        }
            
    }
    else
    {
            alert('Date format  should be (dd-mm-yyyy)');
            t.value="";
            //t.focus();
            return false
    }
    return true;
    
}

function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }  
  

function checkdt1(t)
{

   if(t.value.length==0)
       return false;
   if(t.value.length==10  && t.value.indexOf('/',0)==2 && t.value.indexOf('/',3)==5)
   {


       // var c=t.value.replace(/-/g,'/');
        var c=t.value;
       try{
       var f=DateFormat(t,c,event,true,'3');
       }catch(e){
       //exception  start

        t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());
           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
          }
         

           t.value=c;
            if(err!=0)
               {
                   t.value="";
                   return false;
               }
           return true;


       //exception end

       }
       if( f==true)
       {
           //alert(f);
           //t.value=c.replace(/\//g,'-');
           t.value=c;
           var sc=t.value.split('/');
           var currenDay =sc[0];
           var currentMonth=sc[1];
           var currentYear=sc[2];
           //alert(currentYear == getCurrentYear()  && currentMonth == getCurrentMonth() && currenDay > getCurrentDay());

           if(currentYear<1970)
           {

                   alert('Entered date should be greater than or equal to 1970');
                   t.value="";
                   t.focus();
                   return false;
          }
         

           t.value=c;

           return true;

       }
       else
       {
               if(err!=0)
               {
                   t.value="";
                   return false;
               }
       }

   }
   else
   {
           alert('Date format  should be (dd/mm/yyyy)');
           t.value="";
           //t.focus();
           return false
   }

}
  
  */
  
  
function checkdat()
{
     var dt=document.Emp_Retire_Befini_Rep.txtfromdate.value;
     //alert('dt...'+dt);
     
         var dateParts = dt.split("/");
        selectedDay = dateParts[0];
        selectedMonth = dateParts[1];
        selectedYear = dateParts[2];
        //alert('selectedYear...'+selectedYear);
        
        var year = new Date().getYear();
        var mon = new Date().getMonth();
        var d=new Date().getDate();
        
        //alert(mon);
        //alert('year..'+year);
        //alert('selectedMonth'+selectedMonth);
        
        if(selectedYear<year)
        {
           alert('Year should be greater than current Year');
           document.Emp_Retire_Befini_Rep.txtfromdate.value="";
           document.Emp_Retire_Befini_Rep.txtfromdate.focus();
       }
       
       else if(selectedYear==year)
       {
       if(selectedMonth<(mon+1))
       {
         alert('Month should be greater than current Month');
           document.Emp_Retire_Befini_Rep.txtfromdate.value="";
           document.Emp_Retire_Befini_Rep.txtfromdate.focus();
       }
       
       if(selectedMonth==(mon+1))
        {
          //alert('equal');
           if(selectedDay<d)
           {
           alert('Date should be greater than current Date');
           document.Emp_Retire_Befini_Rep.txtfromdate.value="";
           document.Emp_Retire_Befini_Rep.txtfromdate.focus();
           }
       }
       }
       
       return true;

}

function checkdat1()
{
     var dt=document.Emp_Retire_Befini_Rep.txttodate.value;
     //alert('dt...'+dt);
     
         var dateParts = dt.split("/");
        selectedDay = dateParts[0];
        selectedMonth = dateParts[1];
        selectedYear = dateParts[2];
                
        var year = new Date().getYear();
        var mon = new Date().getMonth();
        var d=new Date().getDate();
        
        
        
        
        if(selectedYear<year)
        {
           alert('Year should be greater than current Year');
           document.Emp_Retire_Befini_Rep.txttodate.value="";
           document.Emp_Retire_Befini_Rep.txttodate.focus();
       }
       
       else if(selectedYear==year)
       {
       if(selectedMonth<(mon+1))
        {
           alert('Month should be greater than current Month');
           document.Emp_Retire_Befini_Rep.txttodate.value="";
           document.Emp_Retire_Befini_Rep.txttodate.focus();
       }
       
       if(selectedMonth==(mon+1))
        {
          //alert('equal');
           if(selectedDay < d)
           {
           alert('Date should be greater than current Date');
           document.Emp_Retire_Befini_Rep.txttodate.value="";
           document.Emp_Retire_Befini_Rep.txttodate.focus();
           
           }
       }
       }
       
       return true;

}

function nullcheck()
 {

	var des_id = "";

	try {

		if ((document.hrm_financial_projection_for_year.optselect[0].checked == false)
				&& (document.hrm_financial_projection_for_year.optselect[1].checked == false)
				&& (document.hrm_financial_projection_for_year.optselect[2].checked == false))

		{
			alert("Please select any Desigantion / Rank / Cadre category ");
			return false;
		}

		else
		///alert("sdgfsg");

		if (document.hrm_financial_projection_for_year.optselect[0].checked == true) {
			if (document.hrm_financial_projection_for_year.cmbsgroup.value == 0) {
				alert("Please select the service group");
				return false;
			} else {
				if (document.hrm_financial_projection_for_year.chkdesig) {
					if (document.hrm_financial_projection_for_year.chkdesig.length) {
						for (i = 0; i < document.hrm_financial_projection_for_year.chkdesig.length; i++) {
							if (document.hrm_financial_projection_for_year.chkdesig[i].checked == true)
								des_id = des_id
										+ document.hrm_financial_projection_for_year.chkdesig[i].value
										+ ",";
						}
						if (des_id != "") {
							des_id = des_id.substring(0, des_id.length - 1);
						} else {
							alert("Please Select Designation!");
							return false;
						}
					}
				}
			}
		}

	} catch (e) {
		// TODO: handle exception
	}

	var rand_id = "";
	try {

		if (document.hrm_financial_projection_for_year.optselect[1].checked == true) {
			if (document.hrm_financial_projection_for_year.cmbsgroup1.value == 0) {
				alert("Please select the service group");
				return false;
			} else {
				if (document.hrm_financial_projection_for_year.chkrank) {
					if (document.hrm_financial_projection_for_year.chkrank.length) {
						for (i = 0; i < document.hrm_financial_projection_for_year.chkrank.length; i++) {
							if (document.hrm_financial_projection_for_year.chkrank[i].checked == true)
								rand_id = rand_id
										+ document.hrm_financial_projection_for_year.chkrank[i].value
										+ ",";
						}

						if (rand_id != "") {
							rand_id = rand_id.substring(0, rand_id.length - 1);
						} else {
							alert("Please Select Rank! ");
							return false;
						}
					}

				}
			}
		}

	} catch (e) {
	}
	var cadre_id = "";
	try {

		if (document.hrm_financial_projection_for_year.optselect[2].checked == true) {
			if (document.hrm_financial_projection_for_year.cmbsgroup2.value == 0) {
				alert("Please select the service group");
				return false;
			} else {
				if (document.hrm_financial_projection_for_year.chkcadre) {
					if (document.hrm_financial_projection_for_year.chkcadre.length) {
						for (i = 0; i < document.hrm_financial_projection_for_year.chkcadre.length; i++) {
							if (document.hrm_financial_projection_for_year.chkcadre[i].checked == true)
								cadre_id = cadre_id
										+ document.hrm_financial_projection_for_year.chkcadre[i].value
										+ ",";
						}
						if (cadre_id != "") {
							cadre_id = cadre_id.substring(0,
									cadre_id.length - 1);
						} else {
							alert("Please Select Cadre!");
							return false;
						}
					}
				}
			}
		}

	} catch (e) {
		// TODO: handle exception
	}

	 if(document.hrm_financial_projection_for_year.fin_year.value.length==0 || document.hrm_financial_projection_for_year.fin_year.selectedIndex=="0")
	    {
	       alert('Please Select Financiyal Year');
	       document.hrm_financial_projection_for_year.fin_year.focus();
	       return false;
	    }
	    
	
	
	return true;

}


  
