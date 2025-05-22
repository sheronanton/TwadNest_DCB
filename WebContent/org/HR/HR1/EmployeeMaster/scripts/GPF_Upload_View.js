function getTransport()
{
//alert("Transport");
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


function handleResponse(req)
{
   if(req.readyState==4)
    {
         if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
          // alert("*******1");
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
          // alert("*******2");
            var Command=tagcommand.firstChild.nodeValue;
            if(Command=="Add")
            {
                //alert("Hellooooooooooo");
                addRow(baseResponse);
            }
            else if(Command=="sessionout")
            {
                alert('Session is closed');
                try{
                //opener.document.write("<br><br><br><br><b>You need to Sign in for accessing this page,<br>please follow this link to <a href=\"index.jsp\" target='_parent'>Sign In</a></b>");
                }catch(e){}
                self.close();
                return;
            }
            else if(Command=="disp")
            {
                dispRow(baseResponse);
            }
            else if(Command=="dispEmp")
            {
                dispEmpRow(baseResponse);
            }
            
            else if(Command=="dispDesign")
            {
                dispDesignRow(baseResponse);
            }
            else if(Command=="Show")
            {
         //   alert("handleresponse for Show"+baseResponse);
                 loadRow(baseResponse);      
            }
            else if(Command=="Validate"){
            //loadRow(baseResponse);
            var result=baseResponse.getElementsByTagName("result")[0].firstChild.nodeValue;
            if(result=="Fail")
                alert("Validation Failure");
            else
                alert("Successfully Validated");
        }
    }
}
}
//to load values from the database to the grid
 function loadRow(baseResponse)
 {
            //  alert("loadRow");
                var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue;
                
              
                if(flag=="NoRecord")
                {
                alert("No Record for Corresponding Value");
                var tbody=document.getElementById("tblList");
                        var t=0;
                document.MenuForm.CmbMinorId.focus();
                document.MenuForm.txtMenuId.value="";        
                document.MenuForm.txtMenuDesc.value="";
                document.MenuForm.txtMenuSDesc.value="";
                document.MenuForm.txtMenuFilePath.value="";
                document.MenuForm.txtMenuId.disabled=false;
                 
                document.MenuForm.txtMenuCategorySeq.value="";
                document.MenuForm.CmbMenuCategory.value="";
                
                }
                else if(flag=="success")
                  {
             //  alert("Success");
             var filetype=baseResponse.getElementsByTagName("filetype")[0].firstChild.nodeValue;
              // alert("Filetype"+filetype);
                    var tbody=document.getElementById("tblList");
                
                     var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
                       if(filetype=="Subscribe"){
                       
                        //  alert("Subscribe");
                        /*document.getElementById("Withdrawl").display = "none";  
                         document.getElementById("Subscribe").display = "";*/
                          var Divn_Cd=baseResponse.getElementsByTagName("Divn_CD");
                          var Acct_MMYY=baseResponse.getElementsByTagName("Acct_MMYY");
                          var GPFNo=baseResponse.getElementsByTagName("GPFNo");
                          
                          var Initial1=baseResponse.getElementsByTagName("Initial1");
                          var Sl_No=baseResponse.getElementsByTagName("Sl_No");
                          var Sub_Amt=baseResponse.getElementsByTagName("Sub_Amt");
                          var Sub_AR_Amt=baseResponse.getElementsByTagName("Sub_AR_Amt");
                          var AR_Ins_No=baseResponse.getElementsByTagName("AR_Ins_No");
                          var AR_Ins_Tot=baseResponse.getElementsByTagName("AR_Ins_Tot");
                          var Ref_Amt=baseResponse.getElementsByTagName("Ref_Amt");
                          var Ref_Ins_No=baseResponse.getElementsByTagName("Ref_Ins_No");
                          var Ref_Ins_Tot=baseResponse.getElementsByTagName("Ref_Ins_Tot");
                          var Rel_MMYY=baseResponse.getElementsByTagName("Rel_MMYY");
                          var GPF_Type=baseResponse.getElementsByTagName("GPF_Type");
                          var Splind=baseResponse.getElementsByTagName("Splind");
                 
                      var j=0;
                      var l=Divn_Cd.length;
                   //   alert("Number of Records"+l);
                      for(j=0;j<l;j++)
                          {
                     
                          var Divn_Cd_Val=baseResponse.getElementsByTagName("Divn_CD")[j].firstChild.nodeValue;
                          var Acct_MMYY_Val=baseResponse.getElementsByTagName("Acct_MMYY")[j].firstChild.nodeValue;
                          var GPFNo_Val=baseResponse.getElementsByTagName("GPFNo")[j].firstChild.nodeValue;
                          var Initial1_Val=baseResponse.getElementsByTagName("Initial1")[j].firstChild.nodeValue;
                          var Sl_No_Val=baseResponse.getElementsByTagName("Sl_No")[j].firstChild.nodeValue;
                          var Sub_Amt_Val=baseResponse.getElementsByTagName("Sub_Amt")[j].firstChild.nodeValue;
                          var Sub_AR_Amt_Val=baseResponse.getElementsByTagName("Sub_AR_Amt")[j].firstChild.nodeValue;
                          var AR_Ins_No_Val=baseResponse.getElementsByTagName("AR_Ins_No")[j].firstChild.nodeValue;
                          var AR_Ins_Tot_Val=baseResponse.getElementsByTagName("AR_Ins_Tot")[j].firstChild.nodeValue;
                          var Ref_Amt_Val=baseResponse.getElementsByTagName("Ref_Amt")[j].firstChild.nodeValue;
                          var Ref_Ins_No_Val=baseResponse.getElementsByTagName("Ref_Ins_No")[j].firstChild.nodeValue;
                          var Ref_Ins_Tot_Val=baseResponse.getElementsByTagName("Ref_Ins_Tot")[j].firstChild.nodeValue;
                          var Rel_MMYY_Val=baseResponse.getElementsByTagName("Rel_MMYY")[j].firstChild.nodeValue;
                          var GPF_Type_Val,Splind_Val;
                          
                          var mycurrent_row=document.createElement("TR");
                          var cell1=document.createElement("TD");
                          var cell2=document.createElement("TD");
                          var cell3=document.createElement("TD");
                          var cell4=document.createElement("TD");
                          var cell5=document.createElement("TD");
                          var cell6=document.createElement("TD");
                          var cell7=document.createElement("TD");
                          var cell8=document.createElement("TD");
                          var cell9=document.createElement("TD");
                          var cell10=document.createElement("TD");
                          var cell11=document.createElement("TD");
                          var cell12=document.createElement("TD");
                          var cell13=document.createElement("TD");
                          var cell14=document.createElement("TD");
                          var cell15=document.createElement("TD");
                       
                          var Divn_Cd=document.createTextNode(Divn_Cd_Val);
                          var Acct_MMYY=document.createTextNode(Acct_MMYY_Val);
                          var GPFNo=document.createTextNode(GPFNo_Val);
                          var Initial1=document.createTextNode(Initial1_Val);
                          var Sl_No=document.createTextNode(Sl_No_Val);
                          var Sub_Amt=document.createTextNode(Sub_Amt_Val);
                          var Sub_AR_Amt=document.createTextNode(Sub_AR_Amt_Val);
                          var AR_Ins_No=document.createTextNode(AR_Ins_No_Val);
                          var AR_Ins_Tot=document.createTextNode(AR_Ins_Tot_Val);
                          var Ref_Amt=document.createTextNode(Ref_Amt_Val);
                          var Ref_Ins_No=document.createTextNode(Ref_Ins_No_Val);
                          var Ref_Ins_Tot=document.createTextNode(Ref_Ins_Tot_Val);
                          var Rel_MMYY=document.createTextNode(Rel_MMYY_Val);
                       /*   var GPF_Type=document.createTextNode(GPF_Type_Val);
                          var Splind=document.createTextNode(Splind_Val);*/
                          
                          cell1.appendChild(Divn_Cd);
                          cell2.appendChild(Acct_MMYY);
                          cell3.appendChild(GPFNo);
                          cell4.appendChild(Initial1);
                          cell5.appendChild(Sl_No);
                          cell6.appendChild(Sub_Amt);
                          cell7.appendChild(Sub_AR_Amt);
                          cell8.appendChild(AR_Ins_No);
                          cell9.appendChild(AR_Ins_Tot);
                          cell10.appendChild(Ref_Amt);
                          cell11.appendChild(Ref_Ins_No);
                          cell12.appendChild(Ref_Ins_Tot);
                          cell13.appendChild(Rel_MMYY);
                        /*  cell14.appendChild(GPF_Type);
                          cell15.appendChild(Splind);*/
                                                    
                          mycurrent_row.appendChild(cell1);
                          mycurrent_row.appendChild(cell2);
                          mycurrent_row.appendChild(cell3);
                          mycurrent_row.appendChild(cell4);
                          mycurrent_row.appendChild(cell5);
                          mycurrent_row.appendChild(cell6);
                          mycurrent_row.appendChild(cell7);
                          mycurrent_row.appendChild(cell8);
                          mycurrent_row.appendChild(cell9);
                          mycurrent_row.appendChild(cell10);
                          mycurrent_row.appendChild(cell11);
                          mycurrent_row.appendChild(cell12);
                          mycurrent_row.appendChild(cell13);
                       /*   mycurrent_row.appendChild(cell14);
                          mycurrent_row.appendChild(cell15);*/
                                          
                          tbody.appendChild(mycurrent_row);
                          
                          }
                       }
                    else if(filetype=="Withdrawl"){
                  //  alert("Withdrawl");
                        /* document.getElementById("Withdrawl").display = "";  
                         document.getElementById("Subscribe").display = "none"; */
                          var Divn_Cd=baseResponse.getElementsByTagName("Divn_CD");
                        /*  var Acct_MMYY=baseResponse.getElementsByTagName("Acct_MMYY");
                          var GPFNo=baseResponse.getElementsByTagName("GPFNo");
                          
                          var Initial1=baseResponse.getElementsByTagName("Initial1");
                          var Sl_No=baseResponse.getElementsByTagName("Sl_No");
                          var Sub_Amt=baseResponse.getElementsByTagName("Sub_Amt");
                          var Sub_AR_Amt=baseResponse.getElementsByTagName("Sub_AR_Amt");
                          var AR_Ins_No=baseResponse.getElementsByTagName("AR_Ins_No");
                          var AR_Ins_Tot=baseResponse.getElementsByTagName("AR_Ins_Tot");
                          var Ref_Amt=baseResponse.getElementsByTagName("Ref_Amt");
                          var Ref_Ins_No=baseResponse.getElementsByTagName("Ref_Ins_No");
                          var Ref_Ins_Tot=baseResponse.getElementsByTagName("Ref_Ins_Tot");
                          var Rel_MMYY=baseResponse.getElementsByTagName("Rel_MMYY");*/
                          
                         /* var GPF_Type=baseResponse.getElementsByTagName("GPF_Type");
                          var Splind=baseResponse.getElementsByTagName("Splind");*/
                 
                      var j=0;
                      var l=Divn_Cd.length;
                      //alert("Number of Records"+l);
                      for(j=0;j<l;j++)
                          {
                     
                          var Divn_Cd_Val=baseResponse.getElementsByTagName("Divn_CD")[j].firstChild.nodeValue;
                      //    alert("Divn_Cd_Val"+Divn_Cd_Val);
                          var Acct_MMYY_Val=baseResponse.getElementsByTagName("Acct_MMYY")[j].firstChild.nodeValue;
                      //    alert("Acct_MMYY_Val"+Acct_MMYY_Val);
                          var GPFNo_Val=baseResponse.getElementsByTagName("GPFNo")[j].firstChild.nodeValue;
                      //    alert("GPFNo_Val"+GPFNo_Val);
                          var Initial1_Val=baseResponse.getElementsByTagName("Initial1")[j].firstChild.nodeValue;
                      //    alert("Initial1_Val"+Initial1_Val);
                          var Adv_Amt_Val=baseResponse.getElementsByTagName("Adv_Amt")[j].firstChild.nodeValue;
                      //    alert("Adv_Amt_Val"+Adv_Amt_Val);
                          var Adv_Type_Val=baseResponse.getElementsByTagName("Adv_Type")[j].firstChild.nodeValue;
                      //    alert("Adv_Type_Val"+Adv_Type_Val);
                          var Con_Amt_Val=baseResponse.getElementsByTagName("Con_Amt")[j].firstChild.nodeValue;
                      //    alert("Con_Amt_Val"+Con_Amt_Val);
                          var No_Inst_Val=baseResponse.getElementsByTagName("No_Inst")[j].firstChild.nodeValue;
                       //   alert("No_Inst_Val"+No_Inst_Val);
                          var Inst_Amt_Val=baseResponse.getElementsByTagName("Inst_Amt")[j].firstChild.nodeValue;
                      //    alert("Inst_Amt_Val"+Inst_Amt_Val);
                          var Rel_MMYY_Val=baseResponse.getElementsByTagName("Rel_MMYY")[j].firstChild.nodeValue;
                       //   alert("Rel_MMYY_Val"+Rel_MMYY_Val);
                        /*  var GPF_Type_Val=baseResponse.getElementsByTagName("GPF_Type")[j].firstChild.nodeValue;
                          alert("GPF_Type_Val"+GPF_Type_Val);
                          var Splind_Val=baseResponse.getElementsByTagName("Splind")[j].firstChild.nodeValue;
                          alert("Splind_Val"+Splind_Val);*/
                      
                          var mycurrent_row=document.createElement("TR");
                          var cell1=document.createElement("TD");
                          var cell2=document.createElement("TD");
                          var cell3=document.createElement("TD");
                          var cell4=document.createElement("TD");
                          var cell5=document.createElement("TD");
                          var cell6=document.createElement("TD");
                          var cell7=document.createElement("TD");
                          var cell8=document.createElement("TD");
                          var cell9=document.createElement("TD");
                          var cell10=document.createElement("TD");
                          var cell11=document.createElement("TD");
                          var cell12=document.createElement("TD");
                                                                                                   
                        
                          var Divn_Cd=document.createTextNode(Divn_Cd_Val);
                          var Acct_MMYY=document.createTextNode(Acct_MMYY_Val);
                          var GPFNo=document.createTextNode(GPFNo_Val);
                          var Initial1=document.createTextNode(Initial1_Val);
                          var Con_Amt=document.createTextNode(Con_Amt_Val);
                          var Adv_Amt=document.createTextNode(Adv_Amt_Val);
                          var Adv_Type=document.createTextNode(Adv_Type_Val);
                          var No_Inst=document.createTextNode(No_Inst_Val);
                          var Inst_Amt=document.createTextNode(Inst_Amt_Val);
                          var Rel_MMYY=document.createTextNode(Rel_MMYY_Val);
                       /* var GPF_Type=document.createTextNode(GPF_Type_Val);
                          var Splind=document.createTextNode(Splind_Val);*/
                        
                          cell1.appendChild(Divn_Cd);
                          cell2.appendChild(Acct_MMYY);
                          cell3.appendChild(GPFNo);
                          cell4.appendChild(Initial1);
                          cell5.appendChild(Con_Amt);
                          cell6.appendChild(Adv_Amt);
                          cell7.appendChild(Adv_Type);
                          cell8.appendChild(No_Inst);
                          cell9.appendChild(Inst_Amt);
                          cell10.appendChild(Rel_MMYY);
                         /* cell11.appendChild(GPF_Type);
                          cell12.appendChild(Splind);*/
                                                    
                          mycurrent_row.appendChild(cell1);
                          mycurrent_row.appendChild(cell2);
                          mycurrent_row.appendChild(cell3);
                          mycurrent_row.appendChild(cell4);
                          mycurrent_row.appendChild(cell5);
                          mycurrent_row.appendChild(cell6);
                          mycurrent_row.appendChild(cell7);
                          mycurrent_row.appendChild(cell8);
                          mycurrent_row.appendChild(cell9);
                          mycurrent_row.appendChild(cell10);
                        /*  mycurrent_row.appendChild(cell11);
                          mycurrent_row.appendChild(cell12);*/
                                                                 
                          tbody.appendChild(mycurrent_row);
                          
                          }
                    
                    
                    }
                 }    
                     else
                     {
                     alert("Record does not exist. Insert a new Record");
                     document.MenuForm.CmdAdd.disabled=false;
                      var tbody=document.getElementById("tblList");
                        var t=0;
                        
                        for(t=tbody.rows.length-1;t>=0;t--)
                        {
                           tbody.deleteRow(0);
                        }
                     }
 
} 



//***************To show the values of DBF*******************
function getRecords(command)
{
alert("GetRecords");
    var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
    var file_type;
    if(command=="Show_Sub"){
        file_type="s";
    }
    else  if(command=="Show_Withdraw"){
        file_type="w";
    }
    else
        alert("File Type NotMatch");
    var url="../../../../../GPF_IMPOUND_SERVLET?command=Show&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year+"&file_type="+file_type;
    var req=getTransport();
    req.open("POST",url,true); 
    req.onreadystatechange=function(){
        handleResponse(req);
    }  
        req.send(null);
}

//****************DBF value is inserted to oracle table************
function setTableValue(Val_Type){
   
    var office_id=document.getElementById("txtOffId").value;
    var Acc_unit_id=document.getElementById("unit_name").value;
    var ac_month=document.getElementById("ac_month").value;
    var ac_year=document.getElementById("ac_year").value;
    //alert("**"+office_id+"***"+Acc_unit_id+"****"+ac_month+"****"+ac_year);
    var url="../../../../../GPF_Validate_Both?Val_Type="+Val_Type+"&txtOffId="+office_id+"&unit_name="+Acc_unit_id+"&ac_month="+ac_month+"&ac_year="+ac_year;
    var req=getTransport();
    req.open("POST",url,true);
    req.onreadystatechange=function(){
        handleResponse(req);
    } 
    req.send(null);
}
