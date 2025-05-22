<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%-- <%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>--%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Employee&nbsp;Profile </title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"          media="screen"/>
    <link href="../../../../../../css/NewStyle.css" rel="stylesheet"          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
            <script src="<%=request.getContextPath()%>/jquerycalendar/jquery-1.6.2.js"></script>
  <script src="<%=request.getContextPath()%>/script/Proceeding_jquery-ui.min.js"></script>
 
 
  <script src="<%=request.getContextPath()%>/script/Proceeding_jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/jquery.magnifier.js"></script>
    <script type="text/javascript">
         
//////////////   FOR EMPLOYEE POPUP WINDOW //////////////////////
var winemp;
var my_window;
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
        
    winemp= window.open("../../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup_test.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}
function closeWin()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}
window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
};

function servicepopupSR()
{
   
    if (winemp && winemp.open && !winemp.closed) 
    {
       winemp.resizeTo(500,600);
       winemp.moveTo(200,200); 
       winemp.focus();
       return ;
    }
     //   alert('test');     
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpPopupSRCtrlOffice.jsp","Employeesearch","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}
function doParentEmp(emp)
{
document.frmview.txtEmpId.value=emp;
getEmp();
closeWin();

}
function IsMobileNumber(textmobile) {
	
    var mob = /^[7-9]{1}[0-9]{9}$/;
    var txtMobile = document.getElementById(textmobile);
    if (mob.test(txtMobile.value) == false) {
        alert("Please enter valid mobile number.");
        document.getElementById("mbl_no").value="";
        //document.getElementById("mbl_no").focus();
        return false;
    }
   // return true;
}

function emailvalid() {
    var x = document.frmview.E_mail_id.value;
    var atpos = x.indexOf("@");
    var dotpos = x.lastIndexOf(".");
    if (atpos< 1 || dotpos<atpos+2 || dotpos+2>=x.length) {
        alert("Not a valid e-mail address");
        document.getElementById("E_mail_id").value="";
        //document.getElementById("E_mail_id").focus();
        return false;
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
/*function call()
    {
    
    alert("photo");
    var empid=document.frmview.txtEmpId.value;
    alert("second"+empid);
    document.getElementById("EmpImage").src="ShowImageProfile.jsp?empid="+empid;
    //return ;
    }*/
function getEmp()
    {
    	
    	//var empid=document.getElementById("txtEmpId").value;

		var emp_id=document.getElementById("txtEmpId").value;
		var url="../../../../../../hrm_mst_emp_contact_updation?command=employee_check&emp_id="+emp_id;
		//alert(url)
		var req=getTransport();
		req.open("GET",url,true);
		req.onreadystatechange=function()
		{
		if(req.readyState==4)
			{
			if(req.status==200)
				{
				//alert(req.responseText)
				baseRe=req.responseXML.getElementsByTagName("response")[0];
				var command=baseRe.getElementsByTagName("command")[0].firstChild.nodeValue;
				var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
				if(command=="employee_check")
					{
					if(flag=="success")
						{
							var empname=baseRe.getElementsByTagName("ename")[0].firstChild.nodeValue;
							//var DESIGNATION=baseRe.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
							//var edob=baseRe.getElementsByTagName("edob")[0].firstChild.nodeValue;
							document.getElementById("ename").value=empname;
							getdata();
							//document.getElementById("desig").value=DESIGNATION;
							//document.getElementById("dob").value=edob;
						}
					 else if(flag=="failure")
						{
						alert("Please Enter Valid Employee Id");
						document.getElementById("txtEmpId").value="";
						//document.getElementById("emp_name").value="";
						//document.getElementById("desig").value="";
						//document.getElementById("dob").value="";
						}
						else if(flag=="failure1")
						{
						alert("Given Employee Id is not in your controlling office");
						document.getElementById("txtEmpId").value="";
						document.getElementById("txtEmpId").focus();
						//document.getElementById("emp_name").value="";
						//document.getElementById("desig").value="";
						//document.getElementById("dob").value="";
						}
			       }
		     }
	    }
    };
   req.send(null);
		
    }

    function same_data()
    {
    	
    	if(document.frmview.same_add_y_no.checked==true)
    		{
    		
    		var add1=document.getElementById("add1").value;
    		var add2=document.getElementById("add2").value;
    		var add3=document.getElementById("add3").value;
    		var p_code=document.getElementById("p_code").value;
    		var phone_no=document.getElementById("phone_no").value;
    		
    		
    		 document.getElementById("add1_per").value=add1;
    		 document.getElementById("add2_per").value=add2;
    		 document.getElementById("add3_per").value=add3;
    		 document.getElementById("p_code_per").value=p_code;
    		 document.getElementById("phone_no_per").value=phone_no;
    		
    		}
    	else
    		{
    		 document.getElementById("add1_per").value="";
    		 document.getElementById("add2_per").value="";
    		 document.getElementById("add3_per").value="";
    		 document.getElementById("p_code_per").value="";
    		 document.getElementById("phone_no_per").value="";
    		
    		}
    }
function add_data()
{
	
	var val=nullcheckvalid();
	if(val==true)
		{
	var emp_id=document.getElementById("txtEmpId").value;
	
	var add1=document.getElementById("add1").value;
	var add2=document.getElementById("add2").value;
	var add3=document.getElementById("add3").value;
	var p_code=document.getElementById("p_code").value;
	var phone_no=document.getElementById("phone_no").value;
	
	var add1_per="";
	add1_per=document.getElementById("add1_per").value;
	var add2_per="";
	add2_per=document.getElementById("add2_per").value;
	var add3_per="";
	add3_per=document.getElementById("add3_per").value;
	var p_code_per=0;
	p_code_per=document.getElementById("p_code_per").value;
	var phone_no_per=0;
	phone_no_per=document.getElementById("phone_no_per").value;
	
	
	var mbl_no=0;
	mbl_no= document.getElementById("mbl_no").value;
	
	
	
	if(p_code.length==0)
		p_code=0;
	  
	  if(p_code_per.length==0)
		  p_code_per=0;
	 
	  if(mbl_no.length==0)
		  mbl_no=0;
	  

	  if(phone_no.length==0)
		  phone_no=0;
	  
	  if(phone_no_per.length==0)
		  phone_no_per=0;
	  
	  if(p_code.length>0)
	  if(p_code.length!=6)
	  {
		  alert("Wrong PinCode");
		  document.getElementById("p_code").value="";
		  document.getElementById("p_code").focus();
		  return false;
		  
	  }
	  if(p_code_per.length>0)
	  if(p_code_per.length!=6)
	  {
		  alert("Wrong Permanent PinCode");
		  document.getElementById("p_code_per").value="";
		  document.getElementById("p_code_per").focus();
		  return false;
	  }
	  
	
	/*if(mbl_no.length>1)
	 {
	  if(!IsMobileNumber())
	  {
		  return false;
	  }
	 }*/
	

	 var E_mail_id=document.getElementById("E_mail_id").value;
	 /*if(E_mail_id.length>1)
		 {
		  if(!emailvalid())
		  {
			  return false;
		  }
		 }*/
	var url="../../../../../../hrm_mst_emp_contact_updation?command=add_data&emp_id="+emp_id+"&add1="+add1+"&add2="+add2+"&add3="+add3;
	var suburl="&p_code="+p_code+"&phone_no="+phone_no+"&add1_per="+add1_per+"&add2_per="+add2_per+"&add3_per="+add3_per+"&p_code_per="+p_code_per;
	var surl="&phone_no_per="+phone_no_per+"&mbl_no="+mbl_no+"&E_mail_id="+E_mail_id;
	var tot_url=url+suburl+surl;
	//alert(tot_url)
	
	var req=getTransport();
	req.open("GET",tot_url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
		if(req.status==200)
			{
			//alert(req.responseText)
			baseRe=req.responseXML.getElementsByTagName("response")[0];
			
			var command=baseRe.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
			//alert(command)
			//alert(flag)
			if(command=="add_data")
				{
				if(flag=="success")
					{
						alert("Record Inserted Successfully");
						
						
						document.getElementById("txtEmpId").value="";
						document.getElementById("ename").value="";
						
						document.getElementById("add1").value="";
						document.getElementById("add2").value="";
						document.getElementById("add3").value="";
						document.getElementById("p_code").value="";
						document.getElementById("phone_no").value="";
						
						 document.getElementById("add1_per").value="";
			    		 document.getElementById("add2_per").value="";
			    		 document.getElementById("add3_per").value="";
			    		 document.getElementById("p_code_per").value="";
			    		 document.getElementById("phone_no_per").value="";
			    		 
			    		 
			    		 document.getElementById("mbl_no").value="";
			    		 document.getElementById("E_mail_id").value="";
			    		 document.frmview.same_add_y_no.checked=false;
					}
				 else if(flag=="failure")
					{
					alert("Please Enter Valid Employee Id");
					document.getElementById("txtEmpId").value="";
					//document.getElementById("emp_name").value="";
					//document.getElementById("desig").value="";
					//document.getElementById("dob").value="";
					}
		       }
	     }
    }
};
		}
req.send(null);
}

function getdata()
{
	var emp_id=document.getElementById("txtEmpId").value;
	var url="../../../../../../hrm_mst_emp_contact_updation?command=getdata&emp_id="+emp_id;
	//alert(url)
	var req=getTransport();
	req.open("GET",url,true);
	req.onreadystatechange=function()
	{
	if(req.readyState==4)
		{
		if(req.status==200)
			{
			//alert(req.responseText)
			baseRe=req.responseXML.getElementsByTagName("response")[0];
			var command=baseRe.getElementsByTagName("command")[0].firstChild.nodeValue;
			var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
			if(command=="getdata")
				{
				if(flag=="success")
					{
						var PRESENT_RES_ADDRESS1=baseRe.getElementsByTagName("PRESENT_RES_ADDRESS1")[0].firstChild.nodeValue;
						var PRESENT_RES_ADDRESS2=baseRe.getElementsByTagName("PRESENT_RES_ADDRESS2")[0].firstChild.nodeValue;
						var PRESENT_RES_ADDRESS3=baseRe.getElementsByTagName("PRESENT_RES_ADDRESS3")[0].firstChild.nodeValue;
						var PRESENT_RES_PINCODE=baseRe.getElementsByTagName("PRESENT_RES_PINCODE")[0].firstChild.nodeValue;
						var PRESENT_RES_PHONE=baseRe.getElementsByTagName("PRESENT_RES_PHONE")[0].firstChild.nodeValue;
						
						
						var PERMANENT_RES_ADDRESS1=baseRe.getElementsByTagName("PERMANENT_RES_ADDRESS1")[0].firstChild.nodeValue;
						var PERMANENT_RES_ADDRESS2=baseRe.getElementsByTagName("PERMANENT_RES_ADDRESS2")[0].firstChild.nodeValue;
						var PERMANENT_RES_ADDRESS3=baseRe.getElementsByTagName("PERMANENT_RES_ADDRESS3")[0].firstChild.nodeValue;
						var PERMANENT_RES_PINCODE=baseRe.getElementsByTagName("PERMANENT_RES_PINCODE")[0].firstChild.nodeValue;
						var CONTACT_PHONE_NO=baseRe.getElementsByTagName("CONTACT_PHONE_NO")[0].firstChild.nodeValue;
						
						
						var CONTACT_CELL_NO=baseRe.getElementsByTagName("CONTACT_CELL_NO")[0].firstChild.nodeValue;
						var EMAIL_ADDR=baseRe.getElementsByTagName("EMAIL_ADDR")[0].firstChild.nodeValue;
						
						
						if(PRESENT_RES_ADDRESS1=="" || PRESENT_RES_ADDRESS1=="null")
							{
							PRESENT_RES_ADDRESS1="";
							}
						
						if(PRESENT_RES_ADDRESS2=="" || PRESENT_RES_ADDRESS2=="null")
						{
							PRESENT_RES_ADDRESS2="";
						}
					
						if(PRESENT_RES_ADDRESS3=="" || PRESENT_RES_ADDRESS3=="null")
						{
							PRESENT_RES_ADDRESS3="";
						}
					
						if(PRESENT_RES_PINCODE=="" || PRESENT_RES_PINCODE=="null")
						{
							PRESENT_RES_PINCODE="";
						}
					
						if(PRESENT_RES_PHONE=="" || PRESENT_RES_PHONE=="null")
						{
							PRESENT_RES_PHONE="";
						}
					
						if(PERMANENT_RES_ADDRESS1=="" || PERMANENT_RES_ADDRESS1=="null")
						{
							PERMANENT_RES_ADDRESS1="";
						}
					
						if(PERMANENT_RES_ADDRESS2=="" || PERMANENT_RES_ADDRESS2=="null")
						{
							PERMANENT_RES_ADDRESS2="";
						}
					
						if(PERMANENT_RES_ADDRESS3=="" || PERMANENT_RES_ADDRESS3=="null")
						{
							PERMANENT_RES_ADDRESS3="";
						}
					
						if(PERMANENT_RES_PINCODE=="" || PERMANENT_RES_PINCODE=="null")
						{
							PERMANENT_RES_PINCODE="";
						}
					
						if(CONTACT_PHONE_NO=="" || CONTACT_PHONE_NO=="null")
						{
							CONTACT_PHONE_NO="";
						}
						if(CONTACT_CELL_NO=="" || CONTACT_CELL_NO=="null")
						{
							CONTACT_CELL_NO="";
						}
					
						if(EMAIL_ADDR=="" || EMAIL_ADDR=="null")
						{
							EMAIL_ADDR="";
						}
					
						
						
						//var DESIGNATION=baseRe.getElementsByTagName("DESIGNATION")[0].firstChild.nodeValue;
						//var edob=baseRe.getElementsByTagName("edob")[0].firstChild.nodeValue;
						document.getElementById("add1").value=PRESENT_RES_ADDRESS1;
						document.getElementById("add2").value=PRESENT_RES_ADDRESS2;
						document.getElementById("add3").value=PRESENT_RES_ADDRESS3;
						document.getElementById("p_code").value=PRESENT_RES_PINCODE;
						document.getElementById("phone_no").value=PRESENT_RES_PHONE;
						
						
						document.getElementById("add1_per").value=PERMANENT_RES_ADDRESS1;
						document.getElementById("add2_per").value=PERMANENT_RES_ADDRESS2;
						document.getElementById("add3_per").value=PERMANENT_RES_ADDRESS3;
						document.getElementById("p_code_per").value=PERMANENT_RES_PINCODE;
						document.getElementById("phone_no_per").value=CONTACT_PHONE_NO;
						
						 
						document.getElementById("mbl_no").value=CONTACT_CELL_NO;
						document.getElementById("E_mail_id").value=EMAIL_ADDR;
						//document.getElementById("desig").value=DESIGNATION;
						//document.getElementById("dob").value=edob;
					}
				 else if(flag=="failure")
					{
					alert("Please Enter Valid Employee Id");
					document.getElementById("txtEmpId").value="";
					//document.getElementById("emp_name").value="";
					//document.getElementById("desig").value="";
					//document.getElementById("dob").value="";
					}
		       }
	     }
    }
};
req.send(null);
		
	
}

function nullcheckvalid() 
{
	if(document.getElementById("txtEmpId").value=="")
		{
		alert("Please Enter Employee Id");
		document.getElementById("txtEmpId").focus();
		return false;
		}
	else if((document.getElementById("add1").value =="") && (document.getElementById("add2").value=="") && (document.getElementById("add3").value==""))
	{
		
		alert("Please Enter Address");
		document.getElementById("add1").focus();
		return false;
	}
	return true;
		
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
     
     

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if ((charCode > 47 && charCode < 58)||(charCode ==45)||(charCode ==8))
          {                
              return true;
          }
	else
	{
		alert("Enter Only Numbers");
          return false;
	}
}
</script>
  </head>
  <body >
  <form name="frmview" method="post" action="">
  <%
      
      
    Connection con=null;
    Statement statement=null;
    PreparedStatement ps=null;
    ResultSet rs=null;
    String temp="",strCommunity="",strDist="",strTaluk="",strGpf="",strDef="",strCurStatus="",strDoj="";
    String strEmpPrefix="",strEmpInitial="",strOfficeId="",strOfficeName="",strSRDesignation="",strOffice_Grade="",strDob="";
    String strEmpName="",strGender="",strDesignation="",strMarital="",strRemarks="",strEmpStatus="",DeptId="",OffName="",OffDName="";
    String EmpImage="../../../../../images/sample_emp.jpg";
    String ctrlOfficeName="",strctrlOfficeId="";
    int ctrlOfficeId=0;
    String retiredate="";
    int gpfno=0; 
    String img="", strEmpId="";
    String dept=null;
    String olevelid="";
    int wing=0;
    int offid=0;
    if(request.getParameter("txtEmpId")!=null)
    {
        strEmpId=request.getParameter("txtEmpId");
        int empid=Integer.parseInt(strEmpId);
        String newpath="";
        try
        {
                 ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                String ConnectionString="";
               
                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                String strdsn=rs1.getString("Config.DSN");
                String strhostname=rs1.getString("Config.HOST_NAME");
                String strportno=rs1.getString("Config.PORT_NUMBER");
                String strsid=rs1.getString("Config.SID");
                String strdbusername=rs1.getString("Config.USER_NAME");
                String strdbpassword=rs1.getString("Config.PASSWORD");
                  
             //   ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 
            
                Class.forName(strDriver.trim());
                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                 
                
                String imagepath=rs1.getString("Config.EMPLOYEE_PHOTOS_PATH");
                String NewImage="";
                System.out.println("imaghe path : " + imagepath);
                String context=request.getContextPath();
                imagepath=rs1.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
                System.out.println("image path after is:"+imagepath);
                img=context+imagepath;
        }
        catch(Exception e)
        {
                System.out.println("Connection Error:"+e);
        }
              
       
        
        
        
         
        
        
        
        
   } 
%>


    <table border="1px" width="90%" align="center">
              <tr>
                <td align="left" class="tdH" colspan="2">
                  <center>
                    <b>Employee&nbsp;Profile </b>
                  </center>
                </td>
              </tr>
              <tr>
                <td >Employee Id:</td>
                <td  valign="center">
                  <table border="0" align="left">
                    <tr>
                      <td>
                        <input tabindex="1" type="text" name="txtEmpId"  id="txtEmpId" size="5"maxlength="5" 
                               onkeypress="return  numbersonly1(event,this)"  onblur="getEmp();"></input>
                               <img src="../../../../../../images/c-lovi.gif" width="20"
                     height="20" alt="empList" onclick="servicepopup();"></img>
                   
                    
                    
                     </td>
                      
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td >Employee Name</td>
                <td ><input type="text" name="ename" id="ename" ></td>
              </tr>
              
              
             
            
              <tr>
                <td align="left" class="tdH" colspan="2">
                  <center>
                    <b>Present Residential Address</b>
                  </center>
                </td>
              </tr>
         
               <tr>
                <td >Address 1</td>
                <td ><input type="text" name="add1" id="add1" size="50"></td>
              </tr>
               <tr>
                <td >Address 2</td>
                <td ><input type="text" name="add2" id="add2" size="50" ></td>
              </tr>
               <tr>
                <td >Address 3</td>
                <td ><input type="text" name="add3" id="add3" size="50" ></td>
              </tr>
               <tr>
                <td >Pin code</td>
                <td ><input type="text" name="p_code" id="p_code" maxlength="6" onkeypress="return isNumberKey(event);"></td>
              </tr>
               <tr>
                <td >Phone</td>
                <td ><input type="text" name="phone_no" id="phone_no" maxlength="10" onkeypress="return isNumberKey(event);"></td>
              </tr>
              
              
              <tr>
                <td >Permanent Residential Address same as Present Residential Address </td>
                <td ><input type="checkbox" name="same_add_y_no" id="same_add_y_no" onclick="same_data()"></td>
              </tr>
              
              
              <tr>
                <td align="left" class="tdH" colspan="2">
                  <center>
                    <b>Permanent Residential Address</b>
                  </center>
                </td>
              </tr>
         
               <tr>
                <td >Address 1</td>
                <td ><input type="text" name="add1_per" id="add1_per" size="50"></td>
              </tr>
               <tr>
                <td >Address 2</td>
                <td ><input type="text" name="add2_per" id="add2_per" size="50" ></td>
              </tr>
               <tr>
                <td >Address 3</td>
                <td ><input type="text" name="add3_per" id="add3_per" size="50" ></td>
              </tr>
               <tr>
                <td >Pin code</td>
                <td ><input type="text" name="p_code_per" id="p_code_per"  maxlength="6" onkeypress="return isNumberKey(event);"></td>
              </tr>
               <tr>
                <td >Phone</td>
                <td ><input type="text" name="phone_no_per" id="phone_no_per" maxlength="10" onkeypress="return isNumberKey(event);"></td>
              </tr>
              
               <tr>
                <td align="left" class="tdH" colspan="2">
                  <center>
                    <b>Employee Contact Details</b>
                  </center>
                </td>
              </tr>
               <tr>
                <td >Mobile</td>
                <td ><input type="text" name="mbl_no" id="mbl_no" size="10"  maxlength="10" onkeypress="return isNumberKey(event);" onblur="return IsMobileNumber('mbl_no')"></td>
              </tr>
               <tr>
                <td >Email Id</td>
                <td ><input type="text" name="E_mail_id" id="E_mail_id" size="50" onblur="emailvalid()"></td>
              </tr>
              <tr>
                <td colspan="2" class="tdH" align="center">
                 <input type="BUTTON" value=" Submit " onclick="add_data();"></input>
                  <input type="BUTTON" value=" Exit " onclick="self.close();"></input>
                  
                </td>
              </tr>
            </table>
     </form></body>
</html>