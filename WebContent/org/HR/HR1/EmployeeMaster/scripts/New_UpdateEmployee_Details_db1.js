//alert('comes inside');
var imgpath;
var OfficeId;
var my_window;
//alert("offid "  + OfficeId);
var DefaultImage="../../../../../images/sample_emp.bmp";

function call()
    {
    
    
    var empid=document.frmEmployee.employee_id.value;
    document.getElementById("EmpImage").src="Show_Image.jsp?empid="+empid;
    //return ;
    }

//alert(imgpath);

function doParentEmp(emp)
{
document.frmEmployee.employee_id.value=emp;
Get_details();
//call();
//callFunction('ExistgBasic','null');
}

window.onunload=function()
{
if (winjob && winjob.open && !winjob.closed) winjob.close();
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

///////////////////////////////////////////////////////////////////////////////////


function Get_details()
{
	var employee_id=document.getElementById("employee_id").value;
	var length=employee_id.length.value;
	//alert(length);
	//document.getElementById("txtEmpId1").value=employee_id;
	if(employee_id=="" || employee_id=="0" || length=="0")
		{
			alert("Enter Employee ID....");
			return false;
		}
	var url="../../../../../Emp_Details?command=insert&empid="+employee_id;
	//alert(url);
	var req1=getTransport1();
	req1.open("GET",url,true);        
    req1.onreadystatechange=function()
    {
    	stateChangedssa(req1);
    };
	
	//req1.open("GET",url,true);
	//req1.onreadystatechange=stateChangedssa;
	req1.send(null);
call();
}


function Delete_Photo()
{
	var employee_id=document.getElementById("employee_id").value;
	var length=employee_id.length.value;
	//alert(length);
	//document.getElementById("txtEmpId1").value=employee_id;
	if(employee_id=="" || employee_id=="0" || length=="0")
		{
			alert("Enter Employee ID....");
			return false;
		}
	var url="../../../../../Emp_Details?command=delete&empid="+employee_id;
	//alert(url);
	var req1=getTransport1();
	req1.open("GET",url,true);        
    req1.onreadystatechange=function()
    {
    	stateChangedssa(req1);
    };
	
	//req1.open("GET",url,true);
	//req1.onreadystatechange=stateChangedssa;
	req1.send(null);
}


function notnull()
{
	//alert(document.getElementById('userImage').value=="");
	
	if(document.getElementById("employee_id").value=="")
		{
			alert("Enter EMployee Id");
			return false;
		}
	//var field=document.getElementById("userImage").value;
var field=document.frmEmployee.userImage.value;
if(field=="")
{
	alert(" Please select image file");
	return false;
	
}else
{
	


	var ext = document.frmEmployee.userImage.value;
    var dot=ext.lastIndexOf('.');
    
    ext1 = ext.substring(ext.length-3,ext.length);
    ext2 = ext1.toLowerCase();
    

    if((ext2 != 'gif')&&(ext2!='GIF')&&(ext2!='JPEG') && (ext2!='jpeg') &&  (ext2!='JPG') && (ext2!='jpg')) 
     {
            alert('You have selected a .'+ext2+' file; Please select a jpg /gif file');
            return false; 
     }
}  
    
    return true;

	
	
}

function load_EmpId()
{
	var eid=document.getElementById("employee_id").value;
	if(eid=="" || eid=="0")
		{
			return false;
		}
	else 
		{
			return true;
		}
	
	
}


function stateChangedssa(req1)
{
	//alert("STATE CHANGED...");
	var command,response,status;
	 if(req1.readyState==4)
	    {
	       if(req1.status==200)
	       {
	    	  // alert("@@@@@@@@STATE CHANGED...");
	    	   try {
	    		   response=req1.responseXML.getElementsByTagName("response")[0];
	    		//alert("RESPONSE========="+req1.responseText);
	    		 command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
	    		 status=response.getElementsByTagName("status")[0].firstChild.nodeValue;
	    		
					//alert(command);
	    		 if(command == "Get_details")
	    			 {
	    			
	    			 if(status == "Get_details")
		    		
	    			 {	
	    				 //alert("**********");
		    			 var count=response.getElementsByTagName("count")[0].firstChild.nodeValue;
							//alert(count);
							if(count!=0)
								{
									var Employee_Prefix=response.getElementsByTagName("EMPLOYEE_PREFIX")[0].firstChild.nodeValue;
									var Employee_Initial=response.getElementsByTagName("EMPLOYEE_INITIAL")[0].firstChild.nodeValue;
									var Employee_Name=response.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
									var gpf_no=response.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
									
									if(Employee_Prefix=="null" || Employee_Prefix==null || Employee_Prefix==0)
										{
										Employee_Prefix="";
										}
									
									document.getElementById("Employee_Prefix").value=Employee_Prefix;
									document.getElementById("Employee_Initial").value=Employee_Initial;
									document.getElementById("Employee_Name").value=Employee_Name;
									//alert(gpf_no);
									document.getElementById("Gpf_Number").value=gpf_no;
									document.getElementById("upload").disabled=false;
								}
		    			 
							
							 var PROCESS_FLOW_STATUS_ID=response.getElementsByTagName("PROCESS_FLOW_STATUS_ID")[0].firstChild.nodeValue;
				    		 if( PROCESS_FLOW_STATUS_ID == "FR")
				    			 {
				    			 	alert("Record is Already Freezed");
				    			 	var Employee_Prefix=response.getElementsByTagName("EMPLOYEE_PREFIX")[0].firstChild.nodeValue;
									var Employee_Initial=response.getElementsByTagName("EMPLOYEE_INITIAL")[0].firstChild.nodeValue;
									var Employee_Name=response.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
									var gpf_no=response.getElementsByTagName("GPF_NO")[0].firstChild.nodeValue;
									document.getElementById("Employee_Prefix").value=Employee_Prefix;
									document.getElementById("Employee_Initial").value=Employee_Initial;
									document.getElementById("Employee_Name").value=Employee_Name;
									//alert(gpf_no);
									document.getElementById("Gpf_Number").value=gpf_no;
				    			 	document.getElementById("upload").disabled=true;
				    			 	document.getElementById("del_button").disabled=true;
				    			 }
				    		 
	    			 
				    		 else
				    		 {
				    			 document.getElementById("upload").disabled=true;
				    		 }
						
	    			 }
	    			 else if(status == "failure_empid")
	    			 {
	    				 alert("Please enter a valid Employee Id");
	    			 }
	    		 
	    			 }
					if(command=="delete")
						{
							var flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
							if(flag=="success")
								{
									alert("Record is Deleted");
								}
							else 
								{
									alert("Record is not Deleted");
								}
							
							if (window.location.reload)
								window.location.reload( true );
								else if (window.location.replace)
								window.location.replace(unescape(location.href));
								else
								window.location.href=unescape(location.href);
						}
	    		   
				} catch (e) {
					// TODO: handle exception
				}
				
				
	       }

	    }

}

function getTransport()
    {
         var req = false;
         try 
         {
               req= new ActiveXObject("Msxml2.XMLHTTP");
               //alert("1")
         }
         catch (e) 
         {
               try 
               {
                    req = new ActiveXObject("Microsoft.XMLHTTP");
                //   alert("2") 
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

function getTransport1()
{
     var req1 = false;
     try 
     {
           req1= new ActiveXObject("Msxml2.XMLHTTP");
           //alert("1")
     }
     catch (e) 
     {
           try 
           {
                req1 = new ActiveXObject("Microsoft.XMLHTTP");
            //   alert("2") 
           }
           catch (e2) 
           {
                req1 = false;
           }
     }
     if (!req1 && typeof XMLHttpRequest != 'undefined') 
     {
           req1 = new XMLHttpRequest();
     }   
     return req1;
}


var s=0;



function callFunction(command,param)
{
    if(command=="ExistgBasic")
       {
      // alert("hello");
         var strEmpId=document.frmEmployee.txtEmpId1.value;
         //startwaiting(document.frmEmployee) ; 
          url="../../../../../InsertEmployee_db1.con?command=ExistgBasic&EmpId=" + strEmpId ;
                     var req=getTransport();
                     
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
           // alert("show");
               processHandleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
       }
     
                    
        }

function processHandleResponse(req)
    {   
   // alert(req);
      if(req.readyState==4)
        {
          if(req.status==200)
          {            
          stopwaiting(document.frmEmployee) ;
          
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
               //alert("show me");
               if(command=="ExistgBasic")
              {
                  existBasicRow(baseResponse);                 
              }
              else if(command=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                //self.close();
                return;
            }
              
        }
        }
  }
   
function existBasicRow(baseResponse)
{
var strEmpId=document.frmEmployee.txtEmpId1.value;
//alert(strEmpId);
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
  //alert(flag);
            if(flag=="EmpDet")
                    {
                       var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                   if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                 
                  document.frmEmployee.Employee_Initial.value=einit;
                  
                   
                  
                  
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                  
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(egpf==0)
                     egpf=0;
                  else   
                     document.frmEmployee.Gpf_Number.value=egpf;
                  var epho=baseResponse.getElementsByTagName("Photo")[0].firstChild.nodeValue; 
                // alert(epho);
                 if(epho=='null')
                 {
               //  alert('comes')
                     document.getElementById("upload").disabled=false; 
                     document.getElementById("delete").disabled=true; 
                //     alert('here')
                 }     
                     document.frmimageupload.EmpBrowse.disabled = false;
        /*         var epho=baseResponse.getElementsByTagName("EmpPhoto")[0].firstChild.nodeValue; 
                
                //alert(epho);
                 
                                    
                    //if((epho.substr(epho.lastIndexOf(".jpg"))==".jpg")||(epho.substr(epho.lastIndexOf(".gif"))==".gif"))
                     var exist=(epho.substring(epho.length-3,epho.length)).toLowerCase();
                     
                   // alert(exist);
                    if((exist == "jpg") || (exist=="gif"))
                    {
                    document.getElementById("EmpImage").src=imgpath+epho;
                    alert(imgpath);
                    }
                    else
                    {
                               // alert("Only .jpg and .gif will be allowed");
                               
                            //    epho="twad.jpg";
                                document.getElementById("EmpImage").src="sample_emp.bmp";
                                //document.getElementById("Validate").disabled=true;
                                document.getElementById("delete").disabled=true;
                                    
                    }*/
                 
                    }
                    else if (flag=="exists")
                        {
                        //alert("Given Employee Id already exist.");
                        
                      //  if(confirm("Do you want to update?"))
                      //       {
                            document.getElementById("delete").disabled=false;
                            var flag1=baseResponse.getElementsByTagName("flag")[1].firstChild.nodeValue; 
                            if(flag1=="EmpDet")
                            {
                                var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                                 if(epref==null)
                                    epref='Mr';
                                else   
                                    document.frmEmployee.Employee_Prefix.value=epref;
                  
                                var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                                if(einit==null)
                                     einit="";
                                else if(einit=='null')
                                    einit="";
                 
                                    document.frmEmployee.Employee_Initial.value=einit;
                  
                   
                  
                  
                                var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                                if(ename==null)
                                     ename="Not Specified";
                                else
                                        document.frmEmployee.Employee_Name.value=ename;
                  
                                    var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                                if(egpf==0)
                                    egpf=0;
                                else   
                                    document.frmEmployee.Gpf_Number.value=egpf;
                                    
                               var epho=baseResponse.getElementsByTagName("Photo")[0].firstChild.nodeValue; 
                // alert(epho);
                 if(epho=='null')
                 {
               //  alert('comes')
                     document.getElementById("upload").disabled=false; 
                     document.getElementById("delete").disabled=true; 
               //      alert('here')
                 }    
                 else
                    {
                     document.getElementById("delete").disabled=false; 
                     document.getElementById("upload").disabled=false; 
                   }  
                
                                    
                  /*  //if((epho.substr(epho.lastIndexOf(".jpg"))==".jpg")||(epho.substr(epho.lastIndexOf(".gif"))==".gif"))
                     var exist=(epho.substring(epho.length-3,epho.length)).toLowerCase();
                  //  alert(exist);
                     if((exist == "jpg") || (exist=="gif"))
                    {
                    document.getElementById("EmpImage").src=imgpath+epho;
                    alert(imgpath);
                   
                        
                    }
                    else
                    {
                        //alert("Only .jpg and .gif will be allowed");
                        epho="twad.jpg";
                        document.getElementById("EmpImage").src="sample_emp.bmp";
                        document.getElementById("delete").disabled=true;
                        //document.getElementById("Validate").disabled=true;
                        
                    }*/
                                    
                                   
                             }
                        
                             document.frmimageupload.EmpBrowse.disabled = false;
                           
                             
                     /*       }
                        else
                            {
                            //document.frmEmployee.Employee_Prefix.disabled=true;
                            //document.frmEmployee.Employee_Initial.disabled=true;
                            }*/
                        }
                    else if (flag=="freezed")
                        {
                        var epref=baseResponse.getElementsByTagName("EmpPref")[0].firstChild.nodeValue; 
                  if(epref==null)
                     epref='Mr';
                  else   
                     document.frmEmployee.Employee_Prefix.value=epref;
                  
                  var einit=baseResponse.getElementsByTagName("EmpInit")[0].firstChild.nodeValue; 
                   if(einit==null)
                     einit="";
                  else if(einit=='null')
                     einit="";
                 
                  document.frmEmployee.Employee_Initial.value=einit;
                  
                   
                  
                  
                  var ename=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue; 
                  if(ename==null)
                     ename="Not Specified";
                   else
                     document.frmEmployee.Employee_Name.value=ename;
                  
                  var egpf=baseResponse.getElementsByTagName("EmpGpf")[0].firstChild.nodeValue; 
                  if(egpf==0)
                     egpf=0;
                  else   
                     document.frmEmployee.Gpf_Number.value=egpf;
                     
    /*             var epho=baseResponse.getElementsByTagName("EmpPhoto")[0].firstChild.nodeValue; 
                 //alert(epho);
                
                    //if((epho.substr(epho.lastIndexOf(".jpg"))==".jpg")||(epho.substr(epho.lastIndexOf(".gif"))==".gif"))
                     var exist=(epho.substring(epho.length-3,epho.length)).toLowerCase();
                    //alert(exist);
                     if((exist == "jpg") || (exist=="gif"))
                    {
                    document.getElementById("EmpImage").src=imgpath+epho;
                    alert(imgpath);
                    }
                    else
                    {
                        //alert("Only .jpg and .gif will be allowed");
                        epho="twad.jpg";
                        document.getElementById("EmpImage").src="sample_emp.bmp";
                        document.getElementById("delete").disabled=true;
                        //document.getElementById("Validate").disabled=true;
                        
                    }*/
                     //   alert('Given Employee Id is already freezed');
                    // document.getElementById("divf").style.display='block';
                    //alert('check');
                     document.getElementById("divfreezed").style.display='block';
                    document.frmimageupload.upload.disabled = true;
                    document.frmimageupload.EmpBrowse.disabled = true;    
                        }
                    
              else
             {
                    document.frmEmployee.txtEmpId1.value="";
                    document.frmEmployee.Employee_Prefix.value="";
                    document.frmEmployee.Employee_Initial.value="";
                    document.frmEmployee.Employee_Name.value="";
                    document.frmEmployee.Gpf_Number.value="";
                    alert("No such Employee Id exists");
                    document.frmimageupload.EmpBrowse.disabled = true;
                     //alert("Failed to Load Values");
             }
}


function toFocus()
{
 
  if((document.frmEmployee.txtEmpId1.value=="") || (document.frmEmployee.txtEmpId1.value<=0))
  {
     alert("Please Enter Employee Id First");
     //document.frmEmployee.txtEmpId1.focus();
     
     return false;
  }
   
}

     
//This Coding for Date Validation and Checking     
function calins(e,t)
{
    var unicode=e.charCode? e.charCode : e.keyCode;
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )
        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                return false 
        }
       

}


function numbersonly1(e,t)
    {
    
    
       var unicode=e.charCode? e.charCode : e.keyCode;
       if(unicode==13)
        {
          try{t.blur();}catch(e){}
          return true;
        
        }
        if ( unicode!=8 && unicode !=9  )
        {
            if ((unicode<48||unicode>57 ) && (unicode<35||unicode>40 ) && unicode!=46 )
                return false 
        }
     }


function ClearAll()
{
document.getElementById("txtEmpId1").value="";
document.getElementById("Employee_Prefix").value="";

document.getElementById("Employee_Initial").value="";
document.getElementById("Employee_Name").value="";
document.getElementById("Gpf_Number").value="";
document.getElementById("upload").disabled=true;
document.getElementById("delete").disabled=true;
//document.getElementById("divf").style.display='none';
document.getElementById("divfreezed").style.display='none';
//alert("1");
document.getElementById("EmpImage").src="";
//alert("2");
}
     
    
     
     
    