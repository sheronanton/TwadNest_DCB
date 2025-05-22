function getValue(value)
{
var url="";
    alert("get Value");
    xmlhttp=getxmlhttpObject();
    if(xmlhttp==null)
    {
        alert("Your borwser doesnot support AJAX");
        return;
    }   
    if(value=='get'){
    alert("get");
        if((document.grant_closed.defanct_office.value=="")||(document.grant_closed.defanct_office.value.length==0))
                {
                    alert("Null Value not accepted...Select Office Number");
                    document.grant_closed.defanct_office.focus();
                    return false;
                }
            var acc_id=document.grant_closed.defanct_office.value;
            alert("accid="+acc_id);
             var tlist=document.getElementById("tlist");
             alert("****1");
                         try{
                              tist.innerHTML="";
                            }
                         catch(e)
                              {
                               tlist.innerText="";
                               }
           
            url="../../../../../Grant_Closed_Office.view?command=get&acc_id="+acc_id;
            //url=url+"&sid="+Math.random();
            xmlhttp.open("GET",url,true);
           xmlhttp.onreadystatechange=stateChanged;
            xmlhttp.send(null); 
        }
    }


function getxmlhttpObject()
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

function stateChanged()
{
alert("StateChanged");
    var flag,command,response;
    if(xmlhttp.readyState==4)
    {
    alert("readystate***4");
       if(xmlhttp.status==200)
       {
       alert("status***200");
            response=xmlhttp.responseXML.getElementsByTagName("response")[0];
            command=response.getElementsByTagName("command")[0].firstChild.nodeValue;
            alert("Command="+command);
            flag=response.getElementsByTagName("flag")[0].firstChild.nodeValue;
            if(command=="get")
            {
                if(flag=='success')
                {
                alert("Success");
                     var tlist=document.getElementById("tlist");
                     try{
                          tist.innerHTML="";
                        }
                     catch(e)
                          {
                           tlist.innerText="";
                           }
                    var office_level=response.getElementsByTagName("office_level")[0].firstChild.nodeValue;
                    var off_address=response.getElementsByTagName("office_address")[0].firstChild.nodeValue;
                    var off_address1=response.getElementsByTagName("office_address1")[0].firstChild.nodeValue;
                    var office_address=off_address+"\n"+off_address1;
                    alert("officelevel"+office_level);
                    alert("office address"+office_address);
                    document.getElementById("officeLevel").value=office_level;
                    document.getElementById("txtOffAddr").value=office_address;
                 }
             }
             }
        }
}