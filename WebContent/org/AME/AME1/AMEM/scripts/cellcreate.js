 /*
  * major - TR,TD,
  * item - input ,label
  * type -  text,redio,check
  * name -  item name
  * value -  display value
  * size  -  size of control
  * sclass - style class
  * url    - linked url 
  * style -  user writed style
  * tdwidth - td width
  * tdalign -  td align
  * funtype - function type (event)
  * funname - function name if (more then one user comma sepearated)
 
  */
function sleep(milliseconds) 
{
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) 
	  {
	    if ((new Date().getTime() - start) > milliseconds)
	    {
	      break;
	    }
	  }
}
function set_width(id)
{
	
	var w = window.innerWidth;  
    document.getElementById(id).width= w+"%";
}
var src="";
function cell(major,item,type,name,value,size,sclass,url,istyle,tdwidth,tdalign,funtype,funname)
 {  
	
	var style="";
	style=istyle;
	
	var name_cell=document.createElement(major);		 
	 
	
    if (major=="TR")
    {  
    name_cell.setAttribute("id", name);
    name_cell.setAttribute("style", style);
    name_cell.setAttribute("class", sclass);
    name_cell.setAttribute("bgcolor", funname);
   
    }
	  
   
	
	
	
	if (major=="TD")
    { 
		  
	// name_cell.setAttribute("colspan", "5");	
	name_cell.setAttribute("align",tdalign);
    name_cell.setAttribute("width",tdwidth); 
    name_cell.setAttribute("style", style); 
    name_cell.setAttribute("class", sclass);
    
    var name_label=document.createElement(item);  //label,text,combo
    
   
    if (window.navigator.appName.toLowerCase().indexOf("netscape") == -1)
    { 
    	 
    	   
    		if (item=="div")
    		{ 
    			name_label=document.createElement("<DIV id='"+name+"' name='"+name+"' style='"+style+"' class='"+sclass+"' >");
    			name_label.innerHTML=""+value+"";            		 
   	            name_cell.appendChild(name_label);
    			
    		}else  if (item=="select")
			{
		    	var fun1="";
		    	if (funtype!="")
		    		fun1= funtype+"='"+funname+"'";
          		  else
          			fun1="";	
		    		
			name_label=document.createElement("<SELECT id='"+name+"' name='"+name+"' style='"+style+"' class='"+sclass+"'  "+fun1+" >");
			name_label.setAttribute("id", name)
			name_label.setAttribute("name", name)
 			var optn1 = document.createElement("OPTION");
			theText=document.createTextNode("Yes");
			optn1.appendChild(theText);
			optn1.setAttribute("value","Y");
			
			if (value=="Yes") 
		    optn1.setAttribute("selected ","1");
			name_label.appendChild(optn1);
		    var optn2 = document.createElement("OPTION");	
		    theText=document.createTextNode("No");
		    optn2.appendChild(theText);
		    optn2.setAttribute("value","N");
		    if (value=="No")
		    optn2.setAttribute("selected","1");
		    name_label.appendChild(optn2);
		    name_cell.appendChild(name_label);
		    }   
		    if (item=="span" )
	           {
	           name_label=document.createElement("<span id='"+name+"' name='"+name+"' style='"+style+"' class='"+sclass+"'></span>");
	           
	           name_label.innerHTML=""+value+"";            		 
 	           name_cell.appendChild(name_label);
	              }
		    try
      	  {  
		    var fun="";
  		  if (funtype!="")
            fun= funtype+"="+funname+"";
  		  else
  			  fun="";
  		  var fs="";
  		    var fun_type=funtype.split("#");
            var fun_name=funname.split("#");
            
      	  var len=fun_type.length;
      	  
      	  if (funtype!="")
      	  {
      		  for (var i=0;i<len;i++)
      		  {
      			 
      			  fs+=fun_type[i]+"="+fun_name[i]+"   ";
      		  } 
      		  
      	  }
      	  }catch(e) {}
		    		if (item=="label" )
		    		{
        	   
		    		  name_label=document.createElement("<label id='"+name+"'  name='"+name+"' style='"+style+"' class='"+sclass+"' "+fs+" >");
		    		  name_label.innerHTML=""+value+"";            		 
            		  name_cell.appendChild(name_label);
		    		}
            	  else if(item=="input")
            	  {
            		  
            		  
            		  
            		 
            		 
            		if (type=="checkbox" || type=="radio")  {
            			var ck="";
            			if (value=='y')
            				ck="checked" ; 
              		  else
              			ck="";
            			  
            		    name_label=document.createElement("<INPUT type="+type+" id='"+name+"'  "+ck+"  name='"+name+"' value='"+value+"'  size='"+size+"' style='"+style+"'   class='"+sclass+"' "+fs+" "+url+">");
            		}
            		else if (type=="text")  
            		{    
            			 var sty=style.split("#");
            			  
            			  
			               		  if (sty[1]=="readonly")
			                   	  {    
				               			name_label=document.createElement("<INPUT type='"+type+"' readonly id='"+name+"'  size='"+size+"' value='"+value+"' name='"+name+"'    style='"+sty[0]+"'   class='"+sclass+"' "+fs+" >");

			                   	  }
			               		  else if (sty[1]=="disable")
			                   	  {    
				               			name_label=document.createElement("<INPUT type='"+type+"'  disabled='disabled'  id='"+name+"' value='"+value+"' name='"+name+"'    style='"+sty[0]+"'   class='"+sclass+"' "+fs+" >");

			                   	  }
			               		  else 
			               		  {
			               			
			               			name_label=document.createElement("<INPUT type='"+type+"' id='"+name+"' value='"+value+"' name='"+name+"'   size='"+size+"'   style='"+sty[0]+"'   class='"+sclass+"' "+fs+" >");
			               		  }
            			
            			
            			 				 
            			    			
            			    		
            		}
            		else
            		{	
            			
            			name_label=document.createElement("<INPUT type="+type+" id='"+name+"' value='"+value+"' name='"+name+"' size='"+size+"' style='"+style+"'   class='"+sclass+"' "+fs+" >");  
            		}
            		
            		 
            		  
             		  if(type!="hidden")
            		  name_cell.appendChild(name_label);
            		  else
            		  name_cell=name_label;
            		  
            		  
            		  
            		 
            	  }else if (item=="textarea")
                  {
            		  var name_label =document.createElement("<textarea   id='"+name+"' name='"+name+"'  size='"+size+"'   class='"+sclass+"'  "+fs+" ></textarea>");
            		  name_cell.appendChild(name_label);
                  }else if (item=="A")
                  {
            		  
                	 
            		  var name_label =document.createElement("<A href="+url+" id='"+name+"' name='"+name+"'  size='"+size+"'   class='"+sclass+"'  "+fs+" >"+value+"</a>");
            		//  var name_label=document.createElement("A");
                     // var url=url;
                    //  name_label.href=url;
            		  	
                       var edit=document.createTextNode(value);
                       name_label.appendChild(edit);
            		  
        	  		  name_cell.appendChild(name_label);
                  }
            	  
            	  	 
            	  	
            	  	  
              }else if (item=="div")
         	 {
            	  
         		 name_label=document.createElement("DIV");
         		 name_label.setAttribute("id", name)
              	 name_label.setAttribute("name", name)
              	  
              	 name_label.innerHTML=value;
         		 
         		 name_cell.appendChild(name_label);
         		 
         	 }  
              else
              {	 
            	 if (item=="select")
              	 {
            		 	
                 	 name_label=document.createElement("SELECT");
                 	 name_label.setAttribute("id", name)
                 	 name_label.setAttribute("name", name)
                 	  name_label.setAttribute(funtype, funname);
                 	
                 	 
                 	 
                 	 var optn = document.createElement("OPTION");
              	     	 optn.text = "Yes";
              	     	 optn.value = "Y";
              	     	 
              	     	if (value=="Yes")
                  	    optn.setAttribute("selected","1");	     
              	     	 
              	     	name_label.options.add(optn);     
              	                  	     	
              	 	 var optn2 = document.createElement("OPTION");
              	 	 	 optn2.text = "No";
              	 	 	 optn2.value = "N";
              	 	 	 
              	 	 	if (value=="No")
              	 	 	optn2.setAttribute("selected","1");	
              	 	 	 
              	 	 	name_label.options.add(optn2);              	 	 	 
              		    name_cell.appendChild(name_label);            		
              	} else         		
              	{  
            	  
            	  if (type!="label")
            	  name_label.setAttribute("type",type);
            	 
            	  name_label.setAttribute("value",value);
            	  name_label.setAttribute("name", name);
            	  name_label.setAttribute("id", name);
            	  name_label.setAttribute("size",size);
            	  name_label.setAttribute("class",sclass);
            	  
            	  name_label.setAttribute("style","'"+style+"'");  
            	  if (type=="text")
            	  {
            		  var sty=style.split("#");
            		   
            		  if (sty[1]=="readonly")
                	  {
            			  name_label.setAttribute("style",sty[0]);
            			  name_label.setAttribute("readonly","true");
                	  }else if (sty[1]=="disable")
                	  {
                     	 
          			  	
          			    name_label.setAttribute("style",sty[0]);
          			  	name_label.setAttribute("disabled","true");
                	  }
            		  else
            		  {
            			  name_label.setAttribute("style",sty[0]);	  
            		  }
            	  
            	  
            	  }
            	   
            	  
            	  if (type=="checkbox") 
            	  { 
            		   
            		  if (value=='y')
            			  name_label.checked = true; 	  
            		  else
            			  name_label.checked = false;;
            	  }
                  
            	  try
            	  {
                  var fun_type=funtype.split("#");
                  var fun_name=funname.split("#");
                  
            	  var len=fun_type.length;
                  
            	  if (funtype!="")
            	  {
            		  for (var i=0;i<len;i++)
            		  {
            			 
            		     name_label.setAttribute(fun_type[i], fun_name[i]);
            		  }
            		  
            	  }
            	  
            	  }catch(e) {}
            	  
            	  if (item=="A")
                  {
            		     
            		  name_label.href=url;
                      name_label.setAttribute("class",sclass);
                  }
            	  
            		  
            	  
                  var name_data=document.createTextNode(value);//value
                  name_label.appendChild(name_data);
            	  
            	 
                  
                  if(type!="hidden")
            		  name_cell.appendChild(name_label);
            		  else
            		  name_cell=name_label
                  
                  
              	}
              }
              
              
            } 
		       
              return name_cell;
 }
function createObject()
{	  
	try
	{
	var xmlHttp = false;
	   try {
		   xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")   // For Microsoft IE 6.0+
	   }
	   catch (e) {
		   
	     try {
	    	 xmlHttp = new ActiveXObject("Microsoft.XMLHTTP") 
	     // For Old Microsoft Browsers
	     }
	     catch (e2) {
	       xmlHttp = false;   // No Browser accepts the XMLHTTP Object then false
	     }
	   }
	   if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
	     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
	   }
	  
	   return xmlHttp;  // Mandatory Statement returning the ajax object created

	}catch(e)
	{
		alert("XML Object Not Created\n--------------------------")
	}
	
}
function addOption(selectbox,text,value)
{  
	 
	try
	{
			var optn = document.createElement("OPTION");
			optn.text = text;
			optn.value = value;			  
			selectbox.options.add(optn);
	}catch(e)
	{
		 
			alert("Option Creation Have Problem\n-------------------------------------")
		
	}
}





function monthselect(i)
{
	var month =new Array("Select","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec");	
return month[i];
}
function month(smonth,src)
{
var d=new Date();
var cm=d.getMonth()+1;
this.src=src;
var month =new Array("-select month-","Jan","Feb","Mar","Apr","May","Jun","July","Aug","Sep","Oct","Nov","Dec");	
for (i=0;i<month.length;i++)
{
	var sno =i;
    var name =month[i];
    addOption(document.getElementById(smonth),name,sno)
}
}

function year(year,src)
{
	this.src=src;
	var thetime=new Date();
	var FullYear=thetime.getFullYear();
	var Fullmonth=thetime.getMonth();
	
	
	
 
 
	
	 
	var year_v1 ="";
	
	if (Fullmonth>=11)
	{
	year_v1=new Array("-select year-" ,FullYear-1, FullYear );
	}
	else
	{
		year_v1=new Array("-select year-" ,FullYear-1, FullYear);
		
	}
	
	 
	 
	 
 	for (i=0;i<year_v1.length;i++)
	{
		
		var sno=year_v1[i];
		var name =year_v1[i]
	
		addOption(document.getElementById(year),name,sno)
}
}
function fld_clr(name)
{
	 
	document.getElementById(name).value="";
	 
}

function combo_sel_value(in_id,out_id)
{
	
	 var sel_value_res=document.getElementById(in_id).options[document.getElementById(in_id).selectedIndex].text;
 	 document.getElementById(out_id).value=sel_value_res;
}
function IsNumeric(inputVal,sErrorMsg) {
	
    if (isNaN(parseFloat(inputVal))) {
         alert(sErrorMsg)
         return false;
    }
    return true;
}

function comboRemove(id){
	document.getElementById(id).length = 0;
}

function vTest(a)
{
alert("TESTING\n====================\n"+a)	
}
 
function isInteger(s,dig,event)
{ 
         
	  
	 var i;
	  s = s.value.toString();
	  var ss="";
	  var pos=0;
      for (i = 0; i < s.length; i++)
      {
       var c = s.charAt(i);
       
       if (c=='.')
       {
    	   ss+=c;
       }else if (c=='-')
       { 
    	   ss+=c;
       }else
    	   {
	    	   if (isNaN(c)) 
	    	   	   {
	    		   alert("Given value is not a number");
	    		   ss=ss;
	    		   return 	false;
	    	   	   }
	    	   else
	    	   {
	    		   ss+=c;
	    	   
	    	   }
    	   }
       
      }
      
      
      return true;
    /* 
	
	var unicode=event.charCode? event.charCode : event.keyCode;
	
		if (unicode!=8 && unicode!=9 && unicode!=37 && unicode!=39 && unicode!=46)
    {
         if (unicode<48 || unicode>57 || txt.length>=dig)
            return false;
    }
		 */
}
 
function isInteger(s,dig,event,id)
{ 
       
      var i;
     
	  s = s.value.toString();
	  var ss="";
	  var pos=0;
      for (i = 0; i < s.length; i++)
      {
       var c = s.charAt(i);
       
       if (c=='.')
       {
    	   ss+=c;
       }else if (c=='-')
       { 
    	   ss+=c;
       }else
    	   {
	    	   if (isNaN(c)) 
	    	   	   {
	    		   alert("Given value is not a number");
	    		   ss=ss;
	    		   document.getElementById(id).value=ss;
	    		   
	    		   return 	false;
	    	   	   }
	    	   else
	    	   {
	    		   ss+=c;
	    	   
	    	   }
    	   }
       
      }
      
      
      return true;
    /* 
	
	var unicode=event.charCode? event.charCode : event.keyCode;
	
		if (unicode!=8 && unicode!=9 && unicode!=37 && unicode!=39 && unicode!=46)
    {
         if (unicode<48 || unicode>57 || txt.length>=dig)
            return false;
    }
		 */
}

 
function dec(input,count)
{
	 var input=new String(input);
	 
		
	if (input=="0.0" || input=="") input="0";
	//var d3=new Packages.java.text.DecimalFormat("0.00");
	
	var res=parseFloat(input).toFixed(count);//d3.format(input);
	
	
	return (res)
	
}
	
function date_val(first,second)
{
	try
	{
	var first=document.getElementById(first).value;
	var second=document.getElementById(second).value;
	 
	var browser=navigator.appName;
	
	if (browser=="Netscape")
	{
		var dd1=first.split('/');
		first=dd1[1]+"/"+dd1[0]+"/"+dd1[2];
		
		var dd2=second.split('/');
		second=dd2[1]+"/"+dd2[0]+"/"+dd2[2];
		
	}
	var a=first.split('/');
	var b=second.split('/');
	var fDate=new Date(a[2],a[0]-1,a[1]);
	var sDate=new Date(b[2],b[0]-1,b[1]);

	if (fDate >= sDate)
	{
		alert("Date Not Valid for Transaction");
	return 1;	
	}else
	{
		return 0;
	}
	}catch(e) {return 1;}
}

function flash()
{	   
	pr_status();
}
function pr_status()
{
	 
	var first=document.getElementById("pr_status").value;
	
	var msg="Processing...Please Wait";
	try
	{
		msg=document.getElementById("msg_hid").value;  
	}catch(e) {msg="Processing...Please Wait";};
	
	 
	
	if (first==0)
	{
	document.getElementById("msg").innerHTML="<font size=2 color='red'><b>"+msg+"</b></font>";
	
	}
	else
	{    try { 
		     document.getElementById("msg").innerHTML="";
			 }catch(e) {}
	}
	setTimeout("pr_status()",3);
}
function pr_status2()
{  
	 var msg=document.getElementById("message").value;
	var first=document.getElementById("pr_status2").value;
	if (first==0)
	{
	        document.getElementById("msg").innerHTML="<font size=4 color='red'><b>"+msg+"</b></font>";	
	}
	else    
	{    try { 
		     document.getElementById("msg").innerHTML="";
			 }catch(e) {}
	}
	setTimeout("pr_status2()",1);
}
function startclock(){
	
	 
	var thetime=new Date();
	var nhours=thetime.getHours()
	var nmins=thetime.getMinutes();
	var nsecn=thetime.getSeconds();
	var hr_d="";
	var hour=0;
	var ampm="";
	var AorP=" ";
	if (nhours>=24)
    nhours-=12;

	if (nhours==0)
   	nhours=12;

	
	
	if (nhours < 12 )
	{
		hr_d="AM"
		hour=nhours;
	}
	else
	{
		hr_d="PM"
		hour=nhours-12;
		
	}
	if (nsecn<10)
 	nsecn="0"+nsecn;
	if (nmins<10)
	 nmins="0"+nmins;
	 document.getElementById("time").innerHTML=nhours+": "+nmins+": "+nsecn;
 	setTimeout('startclock()',1000);
} 

function xmlValue(obj,name,place)
{
	var var_name="";
	try
	{
	  var_name=obj.getElementsByTagName(name)[parseInt(place)].firstChild.nodeValue;
	}catch(e) {var_name="";}
	 if (var_name=='null') var_name="0";
	 
	 return var_name;
}
function xmlValue(obj,name,place,type)
{
	var var_name="";
	try
	{
	  var_name=obj.getElementsByTagName(name)[parseInt(place)].firstChild.nodeValue;
	}catch(e) {var_name="";}
	if (type==1) {
		 if (var_name=='null') var_name="0";
	}
	 else
	 {
		 if (var_name=='null') var_name="";
		 
	 }
			 
	 
	 return var_name;
}
function digit_control_new(s1,v)
{
	var ss=Math.round(s1 * Math.pow(10, v)) / Math.pow(10, v);
	return ss;
}
function digit_control(s1,v)
{
	  var i;  
	  var fc=0;
	  var fcc=0;
	   var  s = document.getElementById(s1).value
	  
	  var ss="";
	  var pos=0;
      for (i = 0; i < s.length; i++)
      {
	       var c = s.charAt(i);		       
	       if (c=='.')
	       {
	    	   ss+=c;
	    	   fc=1;
	       }else
	       {
	    	   if (fcc>v)
		       {
		    	   alert(" Only "+v+" Decimal Allowed")
		    	   ss=ss;			    	   
		    	   document.getElementById(s1).value=ss;
		       }else
		    	{ ss+=c; }
	       }
		       if (fc==1)
		       {
		    	   fcc++;
		       }
      } 
	
	
}

function calins(e,t)
{
	
	var b=new Boolean;
	b=true;
    var unicode=e.charCode? e.charCode : e.keyCode;
        //alert(unicode);
        //if(unicode !=8)
        
        if (unicode!=8 && unicode !=9 && unicode!=37 && unicode !=39 && unicode !=46  && unicode !=35 && unicode !=36 )

        {
            if(t.value.length==2 || t.value.length==5)
                t.value=t.value + '/';
             if (unicode<48||unicode>57 ) 
                b= false ;

        }
       
        
        return b;
	
}

function key_pass(event, current, next) {

	     
	var ck = 0;
	var s = event.keyCode;
	 
	 
	if (s == 13) {	  
	
		ck = empty(current);
		 
				if (ck == 2)  
					{ 
						 document.getElementById(current).focus();
						// msgload("Please Enter the value",1);
						 alert("Please Enter the value");
					}	
				  else {			 
					  
					 document.getElementById(next).focus();
				}
				
		}
		
	   
	 
}
function empty(current) {
	var r = 0;
	 
	try {

		var v = document.getElementById(current).value;
 
		if (v == "" || v == "") {
			r = 2;
			//document.getElementById("flag").value = 1;
		} else {
			r = 1;  
			//document.getElementById("flag").value = 0;
		}
	} catch (e) {
		alert("test error" +e)
	}
	return r;

}

function frm_clear(fr,default_id)
{
	try {
		var f=fr;
		for (var n=0; n < f.elements.length; n++) 
		{
			var vt=f.elements[n].type.substring(0, 4);								   
			if (f.elements[n].type.substring(0, 4)=="text")
				f.elements[n].value=""; 
		}  
		document.getElementById(default_id).focus();
	}catch(e)
	{    
			 
	}	
}

/*
  document.onmousedown=disableclick;  
var status="Right Click Disabled";
function disableclick(e)  
{
	var browser=window.navigator.appName.toLowerCase().indexOf("netscape");  
	if (browser==0)	  
	{
	  if(e.button==2) { alert(status); return false; }		   
	}
	else
	{
		if(event.button==2)
		   {
		     alert(status);
		     return false;    
		  }
	}   
 }    

*/  


function report_period_verify(month,year)
{
	var v=new Date();
	var m=v.getMonth()+1;
	var y=v.getFullYear();
	var pro_month=month.value;
	var pro_year=year.value;
	if (pro_year!=0 && pro_month !=0)
	{ 
		if (pro_year < y ) // 2013  2014   
		{
		}
		else if (pro_year == y )
		{
			if (pro_month <=m)  // 5 	1
			{

			}
			else
			{
				alert("Select Valid month and year...")
				month.value=0;
				year.value=0;
			}
			
		}else
		{
			alert("Select Valid month and year...")
			month.value=0;
			year.value=0;
		}
	} 
}
function report_period_verify_2(month,year)
{
	var flag=0;
	var v=new Date();
	var m=v.getMonth()+1;
	var y=v.getFullYear();  
	var pro_month=month.value;
	var pro_year=year.value;
	if (pro_year!=0 && pro_month !=0)
	{ 
		if (pro_year < y ) // 2013  2014   
		{
			flag=0;
		}
		else if (pro_year == y )
		{
			if (pro_month <=m)  // 5 	1
			{
				flag=0;
			}
			else
			{
				flag=1;
				alert("Select Valid month and year...")
				month.value=0;
				year.value=0;
			}
			  
		}else
		{
			flag=1;
			alert("Select Valid month and year...")
			month.value=0;
			year.value=0;
		}
	}   
	return flag;
}
function month_year_check(month,year)
{
	var flag=2;
	if (parseInt(month)!=0 && parseInt(year) != 0)
	{
		flag=0;   
	}else  
	{
		flag=1;
		alert("Select Month and Year");
	}
	return flag;
}
