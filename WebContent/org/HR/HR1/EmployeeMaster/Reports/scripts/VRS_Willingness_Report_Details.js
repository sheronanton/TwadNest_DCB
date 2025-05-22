
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


function checkNull()
{
	
  if(document.Vrs_willingness_report.vrs_repo[0].checked==false && document.Vrs_willingness_report.vrs_repo[1].checked==false && document.Vrs_willingness_report.vrs_repo[2].checked==false && document.Vrs_willingness_report.vrs_repo[3].checked==false&& document.Vrs_willingness_report.vrs_repo[4].checked==false)
  {
	  alert("Please Select VRS Status");
	  return false;  
  }	  
  
  return true;
}

    