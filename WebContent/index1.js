

history.forward();
 
    function notNull()
    {
       
        if((document.frmindex.txtID.value==null)||(document.frmindex.txtID.value.length==0))
        {
            alert("Enter User Name");
            document.frmindex.reset();
           
            return false;
        }
        else  if((document.frmindex.txtPassword.value==null)||(document.frmindex.txtPassword.value.length==0))
        {
            alert("Enter Password");
            document.frmindex.reset();
            
            return false;
        }
        
        
        /*
          If the username and password is not null and undefined then the cursor is made to Hour Glass in the sense
          that the username and password is passed to the Uservalidation servlet for authentication.        
        */
        
        
        else
        {
             document.frmindex.action="UserValidation.ser";
             document.frmindex.method="POST";
             document.frmindex.submit();
        }
    }
    
    
    
    
    function buttonsubmit(e)
    {
        var unicode=e.charCode? e.charCode : e.keyCode
        if (unicode==13)
        {
            notNull();
        }
        
    }
    
    function stripQuotes(strWords)
    {
       
        strWords.value=strWords.value.replace( "'", "''");
   
    }
 

  function killChars(strWord)
  {

var strWords=strWord.value;
    var badChars=new Array("*", "|", "()", ";", "%", "/..", "../", "=", "\\", "/*", "*/", "%1", "%2", "%3", ".htm", ".html", "--", "select", "drop", "insert", "delete", "xp_", "script", "alert");

    var newChars=null;

   // badChars = array("*", "|", "()", ";", "%", "/..", "../", "=", "\\", "/*", "*/", "%1", "%2", "%3", ".htm", ".html", "--", "select", "drop", "insert", "delete", "xp_", "script", "alert")

    newChars = strWords;
     var len=badChars.length;
    for  (svr = 0;svr<len;svr++)
    {

        newChars=newChars.replace(badChars[svr], "");

    }

    strWord.value = newChars;

  }
  
  function framesclear(){

      parent.menu.location.href = 'testmenufile.jsp';

}