
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

function readData()
{

var pp="../../../../../GPF_UPLOAD_READ";
//alert(pp);
//var pp=
           var req=getTransport();
           alert(req);
            req.open("POST",pp,true); 
            req.onreadystatechange=function()
            {
               handleResponse(req);
            }   
                    if(window.XMLHttpRequest)
                        req.send(null);
                else req.send();
}


function handleResponse(req)
{
   if(req.readyState==4)
    {
         if(req.status==200)
        {
            var baseResponse=req.responseXML.getElementsByTagName("response")[0];
             var tbody=document.getElementById("tblList");
            var tagcommand=baseResponse.getElementsByTagName("command")[0];
             var Command=tagcommand.firstChild.nodeValue;
              var month=baseResponse.getElementsByTagName("acc_month")[0].firstChild.nodeValue;
               var year=baseResponse.getElementsByTagName("acc_year")[0].firstChild.nodeValue;
              if(Command=="show")
                {
                var mycurrent_row=document.createElement("TR");
                var cell=document.createElement("TD");
                var node=document.createTextNode("Acc. Month & Year");
                                cell.align="Right";
                                 cell.appendChild(node);
                                 mycurrent_row.appendChild(cell);
                mycurrent_row=document.createElement("TR");
                cell=document.createElement("TD");
                node=document.createTextNode(month+ " "+year);
                                cell.align="Right";
                                 cell.appendChild(node);
                                 mycurrent_row.appendChild(cell);
                                      tbody.appendChild(mycurrent_row);
                }
        }
}
}
