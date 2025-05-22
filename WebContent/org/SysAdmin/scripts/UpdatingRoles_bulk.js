var winemp;
var my_window;
var roleids=new Array();

var insert_role_id=new Array();
var insert_role_name=new Array();
var insert_seq=new Array();
var iframe;

/*  this file contains page specific ajax functions for FILE : UpdatingRoles.jsp  */ 


// code for creating XMLHTTPREQUEST object
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
 

 
 // function to clear all
 function clearAll()
 {        
        document.frmMasterSub.txtName.selectedIndex=0;
        document.frmMasterSub.txtSysId.disabled=false;
        document.frmMasterSub.txtSysId.selectedIndex=0;
        document.frmMasterSub.Grant.disabled=false;
        document.frmMasterSub.Revoke.disabled=true;
        document.frmMasterSub.txtOrderSeq.value="";
        document.frmMasterSub.txtEmpName.value="";
      
        document.frmMasterSub.txtUserId.value="";
        document.frmMasterSub.txtSysId.value="";
      
        var tbody=document.getElementById("tblList");
        var t=0;
        
        for(t=tbody.rows.length-1;t>=0;t--)
        {
           tbody.deleteRow(0);
        }
        
 }
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
 function addRecord()
 {
  var strCaption=document.frmMasterSub.cmdAdd.value;
    if(strCaption=="  Add  ")
    {
        document.frmMasterSub.cmdAdd.value=" Save ";
        alert("Please Select Master Major Details.");
        document.frmMasterSub.cmdEdit.disabled=true;                  
        document.frmMasterSub.txtSysId.focus();        
    }
    else
    {
        callServer("Add","null");
    }
 
 }
 
 // function to call servlet 
 
 

 function callServer(command,param)
 {     
       // alert(command);
          var strSysId;
          var strName;
          var strSysDesc;
          var strSysShortDesc;     
          
          if(command=="Add")
          {           
                

//                var flag=nullCheck();
        	  var surl="";
               var j=0;
     
        	 var flag=true;
           	  for(var a=0;a<roleids.length;a++)
        	  {
           		
        		  var id=roleids[a];
        		  //alert(id);
        		  
        		  if(document.getElementById(id).checked==true)
        		  {
        			  //alert("hai 3");
        			  var seq=document.getElementById("seq_no_"+id).value;
        			  
        			  insert_role_id[j]=document.getElementById(id).value;
        			  
        			  if(seq=="")
        			  {
        				  seq=0;
        				 // alert("Enter Seq No");
        				 // return false;
        				  
        			  }
        			  insert_seq[j]=seq;
        			  surl=surl+"&roleid="+id+"&seq="+seq;
        			  j++;
        			 
        		  }
        		  
        		 // alert(id);
        	  }
           	  if(surl=="")
           	  {
           		  alert("Atleast check one role name");
           		  flag=false;
           	  }
           	  
           	  
                if(flag==true)
                {
                    
//                    strSysId=document.frmMasterSub.txtSysId.value;
//                    strName=document.frmMasterSub.txtName.value;
//                    Orderseq=document.frmMasterSub.txtOrderSeq.value;
                     var txtUserId=document.frmMasterSub.txtUserId.value;
                     
                     var emp_id=document.getElementById("txtUserId").value;
                     
                     var n="";
                     
                     n=emp_id.substring(4);
                     
                   
                     startwaiting(document.frmMasterSub) ;
                   
                   //url="../../../ServletUpdatingRoles.con?command=Add&txtSysId="+strSysId+"&txtName=" + strName + "&txtDesc=" + strDesc;
                    url="../../../ServletUpdatingRoles_bulk?command=Sample&txtUserId="+n+surl;
                   // alert(url);
                    req.open("GET",url,true);        
                    req.onreadystatechange=processResponse;
                    req.send(null);
                }         
                
          }      
          
          else if(command=="Delete")
          {
                strSysId=document.frmMasterSub.txtSysId.value;
                strName=document.frmMasterSub.txtName.value;
                var txtUserId=document.frmMasterSub.txtUserId.value;
                startwaiting(document.frmMasterSub) ;
                //url="../../../ServletUpdatingRoles.con?command=Delete&txtSysId="+strSysId+"&txtName=" + strName+"&txtDesc="+strDesc;    
                url="../../../ServletUpdatingRoles.con?command=Delete&txtSysId="+strSysId+"&txtName=" + strName;    
                url=url+"&txtUserId="+txtUserId;
                //alert(url);
                req.open("GET",url,true);        
                req.onreadystatechange=processResponse;
                req.send(null);
                
                
          }
         /* else if(command="getName")
          {
        		 
        		 var emp_id=document.getElementById("txtUserId").value;
        		
        		 var n="";
        		 //alert(emp_id);
        		 n=emp_id.substring(4);
        		// alert(n);
        		 url="../../../ServletUpdatingRoles_bulk?command=getName&emp_id="+n ;	 
        		//alert(url);
        	     req.open("GET",url,true);        
        	     req.onreadystatechange=processResponse;
        	     req.send(null);
        	   
        	 }*/
          
         /* else if(command="getName")
          {
        	 
            var emp_id=document.getElementById("txtUserId").value;
            n=emp_id.substring(4);
            //var EmpId=document.frmMasterSub.EmpUserId.value;
//            startwaiting(document.frmMasterSub);
            url="../../../ServletUpdatingRoles_bulk?command=getName&emp_id="+n ;	
            alert(url);
            
            var req1=getTransport();
    	    req1.open("GET",url,true);
    	    req1.onreadystatechange=function()
    	    {
    	    	processResponse123(req1);
    	    
    	    };
    	    req1.send(null);
//            req.open("GET",url,true);        
//            req.onreadystatechange=processResponse;
//            req.send(null);    
          }*/
          else if(command=="combo")
          {
            startwaiting(document.frmMasterSub) ;
               url="../../../ServletUpdatingRoles.con?command=combo";
                req.open("GET",url,true);        
                req.onreadystatechange=processResponse;
                req.send(null);          
                
          }   
          else if(command=="Login" && param=="l")
          {
        	 
            var txtUserId=document.frmMasterSub.txtUserId.value;
            var major=document.getElementById("major_sys").value;
            //var EmpId=document.frmMasterSub.EmpUserId.value;
            startwaiting(document.frmMasterSub);
            url="../../../ServletUpdatingRoles.con?command=LoginUid&txtUserId="+txtUserId+"&major="+major;
            //alert(url);
            document.getElementById("major_sys").selectedIndex=0;
            req.open("GET",url,true);        
            req.onreadystatechange=processResponse;
            req.send(null);    
          }
          else if(command=="Login" && param=="f")
          {
        	 
            var txtUserId=document.frmMasterSub.txtUserId.value;
            var major=document.getElementById("major_sys").value;
            //var EmpId=document.frmMasterSub.EmpUserId.value;
            startwaiting(document.frmMasterSub);
            
            url="../../../ServletUpdatingRoles_bulk?command=LoginUid&txtUserId="+txtUserId+"&major="+major;
           // alert(url);
            req.open("GET",url,true);        
            req.onreadystatechange=processResponse1;
            req.send(null);    
          }
      
         
       
          
    }
    
    // code for processing the xml returned by servlet  
   
 
 function processResponse123(req1)
 {   
 	// alert(req.responseText);
   if(req1.readyState==4)
     {
       if(req1.status==200)
       { 
    	   
    	  // alert(req1.responseText);
           var baseResponse=req1.responseXML.getElementsByTagName("response")[0];
           var tagCommand=baseResponse.getElementsByTagName("command")[0];
           var command=tagCommand.firstChild.nodeValue; 
          // alert(command);
    	   
       }
     }
 }
 
 function processResponse1()
 {   
 	// alert(req.responseText);
     if(req.readyState==4)
       {
         if(req.status==200)
         {      
       	  
       	 // alert(req.responseText);
             var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tagCommand=baseResponse.getElementsByTagName("command")[0];
             var command=tagCommand.firstChild.nodeValue; 
           //  alert(command);
             if(command=="Add")
             {
               stopwaiting(document.frmMasterSub) ;
                 addRow(baseResponse);                 
             }
             else if(command=="Delete")
             {    
               stopwaiting(document.frmMasterSub) ;
                 deleteRow(baseResponse);                             
             }
                         
             else if(command=="combo")
             {
               stopwaiting(document.frmMasterSub) ;
                 combo(baseResponse);
             
             }
             else if(command=="Login")
             {
               stopwaiting(document.frmMasterSub) ;
                login(baseResponse);
             }
             else if(command=="LoginUid")
             {
               stopwaiting(document.frmMasterSub) ;
                loginnew(baseResponse);
             }
          
         }
       }
   }
    function processResponse()
    {   
    	// alert(req.responseText);
      if(req.readyState==4)
        {
          if(req.status==200)
          {      
        	  
        	 // alert(req.responseText);
              var baseResponse=req.responseXML.getElementsByTagName("response")[0];
              var tagCommand=baseResponse.getElementsByTagName("command")[0];
              var command=tagCommand.firstChild.nodeValue; 
              //alert(command);
              if(command=="Add")
              {
                stopwaiting(document.frmMasterSub) ;
                  addRow(baseResponse);                 
              }
              else if(command=="Delete")
              {    
                stopwaiting(document.frmMasterSub) ;
                  deleteRow(baseResponse);                             
              }
                          
              else if(command=="combo")
              {
                stopwaiting(document.frmMasterSub) ;
                  combo(baseResponse);
              
              }
              else if(command=="Login")
              {
                stopwaiting(document.frmMasterSub) ;
                 login(baseResponse);
              }
              else if(command=="LoginUid")
              {
                stopwaiting(document.frmMasterSub) ;
                 login(baseResponse);
              }
             else if(command=="getName")
              {
            	
            	 stopwaiting(document.frmMasterSub) ;
            	// alert("hai");
 	            
 	            	  var flag=baseresponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
 	            	  //alert(flag);
 	            	  if(flag=="success")
 	            	  {
 	            		  
 	            		  var EMPLOYEE_ID=baseresponse.getElementsByTagName("EMPLOYEE_ID")[0].firstChild.nodeValue;
 	            		  var EMPLOYEE_NAME=baseresponse.getElementsByTagName("EMPLOYEE_NAME")[0].firstChild.nodeValue;
 	            		  
 	            		  document.getElementById("txtSysId").value=EMPLOYEE_ID;
 	            		 document.getElementById("txtEmpName").value=EMPLOYEE_NAME;
 	              }
     		}
              
          }
        }
    }
    
    
    function deleteRow(baseResponse)
    {
      var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
      stopwaiting(document.frmMasterSub) ;
      if(flag=="success")
      {
           alert("Record Deleted Successfully.");
           var RoleId=baseResponse.getElementsByTagName("RoleId")[0].firstChild.nodeValue;
           var tbody=document.getElementById("Existing");                      
           var r=document.getElementById(RoleId);  
           var ri=r.rowIndex;               
           tbody.deleteRow(ri);
           
         // clearAll();
           //document.frmMasterSub.txtUserId.value="";
           //document.frmMasterSub.txtSysId.value="";
           document.frmMasterSub.txtOrderSeq.value="";
          // document.frmMasterSub.txtEmpName.value="";
           document.frmMasterSub.txtName.selectedIndex=0;
           document.frmMasterSub.major_sys.selectedIndex=0;
           
           document.frmMasterSub.Grant.disabled=false;
           document.frmMasterSub.Revoke.disabled=true;
      }
      else
      {
          alert("Unable to Delete The Record");
          alert("Please Select Employee Name");
      }      
    }
    
   // function to add a record
    function addRow(baseResponse)
    {
           // alert("hai");
              var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;          
              stopwaiting(document.frmMasterSub) ;
              if(flag=="success")
              {                        
                     alert("Record Inserted Into Database successfully.");
                     //get elements
                     for(var s=0;s<insert_role_id.length;s++)
                     {
                     var items=new Array(); 
                     
                    
                     document.frmMasterSub.txtName.value=insert_role_id[s];

                    var comboValue=document.frmMasterSub.txtName.value;
                     
                    var comboValue1=document.frmMasterSub.txtName.options[document.frmMasterSub.txtName.selectedIndex].text;
                    // items[1]=insert_role_id[s];
                    var tOrderSeqNO=insert_seq[s];                  
//                     alert(items[0]);
                     //append a row
                     var tbody=document.getElementById("tblList");
                    

                     var mycurrent_row=document.createElement("TR");
//                    var tOrderSeqNO=OrderSeqNO.item(i).firstChild.nodeValue;
                 //   var EmpName=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;
                    
                  //  document.frmMasterSub.txtEmpName.value=EmpName;
                    var cell1=document.createElement("TD");
                    var cell2=document.createElement("TD");
                    var cell3=document.createElement("TD");
//                    var comboValue=Role.item(i).firstChild.nodeValue;
//                    var comboValue1=RoleName.item(i).firstChild.nodeValue;
                    mycurrent_row.id=comboValue;
                    var cell=document.createElement("TD");
                    var anc=document.createElement("A");
                    anc.id="a"+comboValue;
                    anc.href="javascript:loadValuesFromTablenew('"+comboValue+"','"+tOrderSeqNO+"')";
                    var edit=document.createTextNode("Edit");
                    anc.appendChild(edit);
                    cell.appendChild(anc);
                    mycurrent_row.appendChild(cell);
                    
                    //var cell=document.createElement("TD");
                  //  alert(tOrderSeqNO);
                    var textnode=document.createTextNode(comboValue);
                    var textnode1=document.createTextNode(comboValue1);
                    var ctOrderSeqNO=document.createTextNode(tOrderSeqNO);
                    
                    cell1.appendChild(textnode);
                    cell2.appendChild(textnode1);
                     cell3.appendChild(ctOrderSeqNO);
                     
                    mycurrent_row.appendChild(cell);
                    mycurrent_row.appendChild(cell1);
                    mycurrent_row.appendChild(cell2);
                    mycurrent_row.appendChild(cell3);
                    
                    tbody.appendChild(mycurrent_row);
                  
                     }
                     //alert("hhh");
                   
                     document.frmMasterSub.txtName.selectedIndex=0;
//                     
                     document.frmMasterSub.txtOrderSeq.value="";
//                     
                     callServer('Login','f');
                    //document.getElementById("major_sys").selectedIndex=0;
                     
                    
             }
             else
             {
                      alert("RoleName Already Exists");
                      alert("Failed to Insert Values");
             }
        
    }
    function combo(baseResponse)
    {
          var txtSysId=document.getElementById("txtSysId");
          var value=baseResponse.getElementsByTagName("value");
          var EmpName=baseResponse.getElementsByTagName("EmpName");
          //var option=document.createElement("OPTION");
          for(var i=0;i<value.length;i++)
          {
              var comboValue=value.item(i).firstChild.nodeValue;
              var comboValue1=EmpName.item(i).firstChild.nodeValue;
              var option=document.createElement("OPTION");
              option.text=comboValue1;
              option.value=comboValue;
              //Making Browser Independent
              try
              {
                  txtSysId.add(option);
              }
              catch(errorObject)
              {
                  txtSysId.add(option,null);
              }
          }
          
   
    }
   function login(baseResponse)
    {
   stopwaiting(document.frmMasterSub) ;
    //alert('hai');
    
     var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
     if(flag=='failure')
     {
            alert('Can not retrive the User Id');
            var tbody=document.getElementById("tblList");
             if(tbody.rows.length >0)
                {        
                         if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.frmMasterSub.reset();
            return;
     }
     else if(flag=='NoRecord')
     {
            alert('Invalid User Id');
             var tbody=document.getElementById("tblList");
             if(tbody.rows.length >0)
                {        
                         if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                                tbody.innerText='';
                        else 
                            tbody.innerHTML='';
                }
            document.frmMasterSub.reset();
            return;
     }
      var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
      
      //var DESIGNATION=baseResponse.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
      if(baseResponse.getElementsByTagName("DESIGNATION")[0] == undefined){
		  var DESIGNATION="";
	  }else{
		        var DESIGNATION=baseResponse.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;

	  }
	  
	    if(baseResponse.getElementsByTagName("OFFICE_NAME")[0] == undefined){
		  var OFFICE_NAME="";
	  }else{
		        var OFFICE_NAME=baseResponse.getElementsByTagName("OFFICE_NAME")[0].firstChild.nodeValue;

	  }
      
 		if(baseResponse.getElementsByTagName("OFFICE_LEVEL_ID")[0] == undefined){
		  var OFFICE_LEVEL_ID="";
	 	 }else{
		        var OFFICE_LEVEL_ID=baseResponse.getElementsByTagName("OFFICE_LEVEL_ID")[0].firstChild.nodeValue;

	  }



    //  var OFFICE_LEVEL_ID=baseResponse.getElementsByTagName("OFFICE_LEVEL_ID")[0].firstChild.nodeValue;
      if(OFFICE_LEVEL_ID==null || OFFICE_LEVEL_ID=='null')
    	  off_name_leve=OFFICE_NAME;
      if(empid==0)
      {
        empid="";
      }
      //alert(empid)
      document.frmMasterSub.txtSysId.value=empid;
      
      document.frmMasterSub.EmpId.value=empid;
document.frmMasterSub.desig.value=DESIGNATION;
var off_name_leve=null;
if(OFFICE_LEVEL_ID!=null && OFFICE_LEVEL_ID!='null')
 off_name_leve=OFFICE_NAME + " - " + OFFICE_LEVEL_ID;
else
	  off_name_leve= OFFICE_NAME;
      document.frmMasterSub.Off_name.value=off_name_leve;
       var EmpName=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;
        
        document.frmMasterSub.txtEmpName.value=EmpName;
        var txtName=document.getElementById("txtName");
        txtName.innerHTML="";
//        var option=document.createElement("OPTION");
//        
//        option.text="--Select Role--";
//        try
//                {
//                txtName.add(option);                
//                }
//                catch(errorObject)
//                {
//                txtName.add(option,null);
//                }
        
        var items=new Array();
        var Role=baseResponse.getElementsByTagName("RoleId");
        var RoleName=baseResponse.getElementsByTagName("RoleName");
        
        var OrderSeqNO=baseResponse.getElementsByTagName("OrderSeqNO");
      /* alert(OrderSeqNO.length);
       var len=OrderSeqNO.length;
       for(var s=0;s<len;s++)
        {
            var tOrderSeqNO=OrderSeqNO.item(s).firstChild.nodeValue;
            alert(tOrderSeqNO);
            
        }*/
        
        
        //Getting RoleValue here
        var RoleValue=baseResponse.getElementsByTagName("RoleValue");
        var RoleName1=baseResponse.getElementsByTagName("RoleName1");
        html="<table cellpadding=0 cellspacing=0 border=0  width='100%' >";
        html=html+"<tr ><td colspan='2'><a name='monthlink' id='monthlink' href='javascript:MonthselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:Monthclose()'>Close</a></td></tr>";
        html=html+"<tr><td colspan='2'>Roles</td><td>Sequence</td></tr>"

        for(var k=0;k<RoleValue.length;k++)
        {
       var comboRole=RoleValue.item(k).firstChild.nodeValue;
        var comboName1=RoleName1.item(k).firstChild.nodeValue;
        
//        html=html+"<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='roles' value='"+comboRole+"'></td>";
//       html=html+"<td>"+comboName1+"</td><td><input type='text' size='6' name='seq_no'id='seq_no'></input> </td></tr>";
//       
        
        
        var option=document.createElement("OPTION");
        
        option.text=comboName1;
        option.value=comboRole;
                try
                {
                txtName.add(option);                
                }catch(errorObject)
                {
                txtName.add(option,null);
                }
        }
        
        html=html+"</table>";
        var iframe=document.getElementById("diviframestatus");
        iframe.style.visibility='visible';
        iframe.focus();

        if(navigator.appName.indexOf('Microsoft')!=-1)
        {
      	 // alert("this is IE");
      	  try{
      		  iframe.innerHTML=html;
      	  }catch(s){
      		  iframe.innerText=html;
      	  }
        }
        else
        {
      	  iframe.innerHTML=html;
        }
        var j=1;
       
         var tbody=document.getElementById("tblList");
        var table=document.getElementById("Existing");
       
        
        // code for clearing the table
     /*   var t=0;
        for(t=tbody.rows.length-1;t>=0;t--)
        {
            
            tbody.deleteRow(0);
        }
       */ 
       
         if(tbody.rows.length >0)
            {        
                     if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                            tbody.innerText='';
                    else 
                        tbody.innerHTML='';
            }
        
        for(var i=0;i<Role.length;i++)
        {
         var mycurrent_row=document.createElement("TR");
        var tOrderSeqNO=OrderSeqNO.item(i).firstChild.nodeValue;
     //   var EmpName=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;
        
      //  document.frmMasterSub.txtEmpName.value=EmpName;
        var cell1=document.createElement("TD");
        var cell2=document.createElement("TD");
        var cell3=document.createElement("TD");
        var comboValue=Role.item(i).firstChild.nodeValue;
        var comboValue1=RoleName.item(i).firstChild.nodeValue;
        mycurrent_row.id=comboValue;
        var cell=document.createElement("TD");
        var anc=document.createElement("A");
        anc.id="a"+comboValue;
        anc.href="javascript:loadValuesFromTable('"+comboValue+"')";
        var edit=document.createTextNode("Edit");
        anc.appendChild(edit);
        cell.appendChild(anc);
        mycurrent_row.appendChild(cell);
        
        //var cell=document.createElement("TD");
      //  alert(tOrderSeqNO);
        var textnode=document.createTextNode(comboValue);
        var textnode1=document.createTextNode(comboValue1);
        var ctOrderSeqNO=document.createTextNode(tOrderSeqNO);
        
        cell1.appendChild(textnode);
        cell2.appendChild(textnode1);
         cell3.appendChild(ctOrderSeqNO);
         
        mycurrent_row.appendChild(cell);
        mycurrent_row.appendChild(cell1);
        mycurrent_row.appendChild(cell2);
        mycurrent_row.appendChild(cell3);
        
        tbody.appendChild(mycurrent_row);
        table.appendChild(tbody);
         j++;
        }
        document.frmMasterSub.txtOrderSeq.value="";
        //document.frmMasterSub.txtEmpName.value="";
        
       document.getElementById("role").style.display='none';
       document.getElementById("seq").style.display='none';
       document.getElementById("bulk").style.display='';
  }

   
   
   
   function loginnew(baseResponse)
   {
  stopwaiting(document.frmMasterSub) ;
   //alert('hai');
   
    var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
    if(flag=='failure')
    {
           alert('Can not retrive the User Id');
           var tbody=document.getElementById("tblList");
            if(tbody.rows.length >0)
               {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                               tbody.innerText='';
                       else 
                           tbody.innerHTML='';
               }
           document.frmMasterSub.reset();
           return;
    }
    else if(flag=='NoRecord')
    {
           alert('Invalid User Id');
            var tbody=document.getElementById("tblList");
            if(tbody.rows.length >0)
               {        
                        if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                               tbody.innerText='';
                       else 
                           tbody.innerHTML='';
               }
           document.frmMasterSub.reset();
           return;
    }
     var empid=baseResponse.getElementsByTagName("empid")[0].firstChild.nodeValue;
     if(empid==0)
     {
       empid="";
     }
     //alert(empid)
     document.frmMasterSub.txtSysId.value=empid;
     
     document.frmMasterSub.EmpId.value=empid;
     
      var EmpName=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;
       
       document.frmMasterSub.txtEmpName.value=EmpName;
//       var txtName=document.getElementById("txtName");
//       txtName.innerHTML="";
//       var option=document.createElement("OPTION");
//       
//       option.text="--Select Role--";
//       try
//               {
//               txtName.add(option);                
//               }
//               catch(errorObject)
//               {
//               txtName.add(option,null);
//               }
       
       var items=new Array();
       var Role=baseResponse.getElementsByTagName("RoleId");
       var RoleName=baseResponse.getElementsByTagName("RoleName");
       
       var OrderSeqNO=baseResponse.getElementsByTagName("OrderSeqNO");
     /* alert(OrderSeqNO.length);
      var len=OrderSeqNO.length;
      for(var s=0;s<len;s++)
       {
           var tOrderSeqNO=OrderSeqNO.item(s).firstChild.nodeValue;
           alert(tOrderSeqNO);
           
       }*/
       
       
       //Getting RoleValue here
       var RoleValue=baseResponse.getElementsByTagName("RoleValue");
       var RoleName1=baseResponse.getElementsByTagName("RoleName1");
       html="<table cellpadding=0 cellspacing=0 border=0  width='100%' >";
       html=html+"<tr ><td colspan='2'><a name='monthlink' id='monthlink' href='javascript:MonthselectAllnew()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:Monthclose()'>Close</a></td></tr>";
       html=html+"<tr><td colspan='2'>Roles</td><td>Sequence</td></tr>"

       for(var k=0;k<RoleValue.length;k++)
       {
      var comboRole=RoleValue.item(k).firstChild.nodeValue;
       var comboName1=RoleName1.item(k).firstChild.nodeValue;
       roleids[k]=comboRole;
       //alert(comboRole);
       html=html+"<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='roles' id='"+comboRole+"' value='"+comboRole+"'></td>";
      html=html+"<td>"+comboName1+"</td><td><input type='text' size='6' value='0' name='seq_no_"+comboRole+"' id='seq_no_"+comboRole+"'></input> </td></tr>";
      
//       
//       
//       var option=document.createElement("OPTION");
//       
//       option.text=comboName1;
//       option.value=comboRole;
//               try
//               {
//               txtName.add(option);                
//               }catch(errorObject)
//               {
//               txtName.add(option,null);
//               }
       }
       
       html=html+"</table>";
       var iframe=document.getElementById("diviframestatus");
       iframe.style.visibility='visible';
       iframe.focus();

       if(navigator.appName.indexOf('Microsoft')!=-1)
       {
     	 // alert("this is IE");
     	  try{
     		  iframe.innerHTML=html;
     	  }catch(s){
     		  iframe.innerText=html;
     	  }
       }
       else
       {
     	  iframe.innerHTML=html;
       }
       var j=1;
      
        var tbody=document.getElementById("tblList");
       var table=document.getElementById("Existing");
      
       
       // code for clearing the table
    /*   var t=0;
       for(t=tbody.rows.length-1;t>=0;t--)
       {
           
           tbody.deleteRow(0);
       }
      */ 
      
        if(tbody.rows.length >0)
           {        
                    if(tbody.innerText !='undefined'  && tbody.innerText !=null  )
                           tbody.innerText='';
                   else 
                       tbody.innerHTML='';
           }
       
       for(var i=0;i<Role.length;i++)
       {
        var mycurrent_row=document.createElement("TR");
       var tOrderSeqNO=OrderSeqNO.item(i).firstChild.nodeValue;
    //   var EmpName=baseResponse.getElementsByTagName("EmpName")[0].firstChild.nodeValue;
       
     //  document.frmMasterSub.txtEmpName.value=EmpName;
       var cell1=document.createElement("TD");
       var cell2=document.createElement("TD");
       var cell3=document.createElement("TD");
       var comboValue=Role.item(i).firstChild.nodeValue;
       var comboValue1=RoleName.item(i).firstChild.nodeValue;
       mycurrent_row.id=comboValue;
       var cell=document.createElement("TD");
       var anc=document.createElement("A");
       anc.id="a"+comboValue;
       anc.href="javascript:loadValuesFromTable('"+comboValue+"')";
       var edit=document.createTextNode("Edit");
       anc.appendChild(edit);
       cell.appendChild(anc);
       mycurrent_row.appendChild(cell);
       
       //var cell=document.createElement("TD");
     //  alert(tOrderSeqNO);
       var textnode=document.createTextNode(comboValue);
       var textnode1=document.createTextNode(comboValue1);
       var ctOrderSeqNO=document.createTextNode(tOrderSeqNO);
       
       cell1.appendChild(textnode);
       cell2.appendChild(textnode1);
        cell3.appendChild(ctOrderSeqNO);
        
       mycurrent_row.appendChild(cell);
       mycurrent_row.appendChild(cell1);
       mycurrent_row.appendChild(cell2);
       mycurrent_row.appendChild(cell3);
       
       tbody.appendChild(mycurrent_row);
       table.appendChild(tbody);
        j++;
       }
       document.frmMasterSub.txtOrderSeq.value="";
       //document.frmMasterSub.txtEmpName.value="";
       
      document.getElementById("role").style.display='none';
      document.getElementById("seq").style.display='none';
      document.getElementById("bulk").style.display='';
 }
// code for loading the values from the table to the input boxes
    // functionality for edit anchor
   function loadValuesFromTablenew(rid,seq)
   {
	   document.getElementById("role").style.display='';
       document.getElementById("seq").style.display='';
       document.getElementById("bulk").style.display='none';
       
       document.frmMasterSub.txtName.value=rid;//rcells.item(1).firstChild.nodeValue;
       document.frmMasterSub.txtOrderSeq.value=seq;
       document.frmMasterSub.Revoke.disabled=false;
       document.frmMasterSub.Grant.disabled=true;
       document.frmMasterSub.Revoke.focus();
   }
    function loadValuesFromTable(rid)
    {      
    	
    	document.getElementById("role").style.display='';
        document.getElementById("seq").style.display='';
        document.getElementById("bulk").style.display='none';
    	
          var r=document.getElementById(rid); 
          var txtName=document.getElementById("txtName");
          var rcells=r.cells;
          var tbody=document.getElementById("tblList");
          var table=document.getElementById("Existing");
          var option=document.createElement("OPTION");
          option.text=rcells.item(2).firstChild.nodeValue;
          option.value=rcells.item(1).firstChild.nodeValue;
          //alert(rcells.item(3).firstChild.nodeValue);
          //alert(option.value);
          try
          {
        	 
          txtName.add(option);
          }catch(errorObject)
          {
          txtName.add(option,null);
          }
          //alert(document.frmMasterSub.txtName.length);
          document.frmMasterSub.txtName.selectedIndex=document.frmMasterSub.txtName.length-1;//rcells.item(1).firstChild.nodeValue;
          document.frmMasterSub.txtOrderSeq.value=rcells.item(3).firstChild.nodeValue;
          document.frmMasterSub.Revoke.disabled=false;
          document.frmMasterSub.Grant.disabled=true;
          document.frmMasterSub.Revoke.focus();
      
    }
    function fun1()
    {
    alert("hai");
    }


function servicepopup()
{
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
    else
    {
        winemp=null
    }
   
    winemp= window.open("../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

function doParentEmp(emp)
{
document.frmMasterSub.txtSysId.value=emp;
callServer('Login','null');

}
//this is the function to close the employee popup windows...
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
if (my_window && my_window.open && !my_window.closed) my_window.close();

}

function numbersonly1(e,t)
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
                return false 
        }
     }
     
     
      function Exit()
 {
    window.open('','_parent','');
    window.close();
 }
      
      
 //// for Bulk roles Menu test

      function loadMon1(){
      	 html="<table cellpadding=0 cellspacing=0 border=0  width='100%' >";
            html=html+"<tr ><td colspan='2'><a name='monthlink' id='monthlink' href='javascript:MonthselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:Monthclose()'>Close</a></td></tr>";

            html=html+"<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Jan'  ></td>";
            html=html+"<td >January</td></tr>";

            html=html+"<tr ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Feb' ></td>";
            html=html+"<td >February</td></tr>";
            html=html+"<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Mar'  ></td>";
            html=html+"<td >March</td></tr>";

            html=html+"<tr ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Apr' ></td>";
            html=html+"<td >April</td></tr>";
            html=html+"<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='May'  ></td>";
            html=html+"<td >May</td></tr>";

            html=html+"<tr ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Jun' ></td>";
            html=html+"<td >June</td></tr>";
            html=html+"<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Jul'  ></td>";
            html=html+"<td >July</td></tr>";

            html=html+"<tr ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Aug' ></td>";
            html=html+"<td >August</td></tr>";
            html=html+"<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Sep'  ></td>";
            html=html+"<td >September</td></tr>";

            html=html+"<tr ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Oct' ></td>";
            html=html+"<td >October</td></tr>";
            html=html+"<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Nov'  ></td>";
            html=html+"<td >November</td></tr>";

            html=html+"<tr ><td ><input type='checkbox' name='APPLICABLE_MONTHS' value='Dec' ></td>";
            html=html+"<td >December</td></tr>";

        html=html+"</table>";
        var iframe=document.getElementById("diviframestatus");
        iframe.style.visibility='visible';
        iframe.focus();

        if(navigator.appName.indexOf('Microsoft')!=-1)
        {
      	 // alert("this is IE");
      	  try{
      		  iframe.innerHTML=html;
      	  }catch(s){
      		  iframe.innerText=html;
      	  }
        }
        else
        {
      	  iframe.innerHTML=html;
        }
      }

      function MonthselectAll()
      {
      	
        if(document.updation_lic_delete.APPLICABLE_MONTHS)
           {
                 for(i=0;i<document.updation_lic_delete.APPLICABLE_MONTHS.length;i++)
                 {
                         document.updation_lic_delete.APPLICABLE_MONTHS[i].checked=true;
                 }
                //  regiononchange();
             }
      }
      function MonthselectAllnew()
      {
    	  alert("hai ");
    	  for(var a=0;a<roleids.length;a++)
    	  {
       		
    		  var id=roleids[a];
    		 
    		  
    		  document.getElementById(id).checked=true;
    		 
    			 
    		 
    		  
    		 // alert(id);
    	  }
      }

      function Monthclose()
      {
         var iframe=document.getElementById("diviframestatus");
         iframe.style.visibility='hidden';
      }