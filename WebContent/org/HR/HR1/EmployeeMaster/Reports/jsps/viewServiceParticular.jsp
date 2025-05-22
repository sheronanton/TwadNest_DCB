<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%-- <%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>--%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Employee&nbsp;Profile </title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
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
        
    winemp= window.open("../../../../../org/HR/HR1/EmployeeMaster/jsps/EmpServicePopup.jsp","mywindow1","status=1,height=500,width=600,resizable=YES, scrollbars=yes"); 
    winemp.moveTo(250,250);  
    winemp.focus();
    
}

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
document.frmview.submit();
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
function validate()
{
//alert("called");
    if(document.frmview.txtEmpId.value.length==0)
    {
        alert('Select Employee Id');
        document.frmview.txtEmpId.focus();
        return false;
    }
    else
    {
    
        var strEmpId=document.frmview.txtEmpId.value;
        // startwaiting(document.frmEmployee) ; 
        
        //alert(strEmpId);
         <%
         HttpSession session=request.getSession(false);
         if(session.getAttribute("Admin")!=null && ((String)session.getAttribute("Admin")).equalsIgnoreCase("YES"))
         {
         %>
            var sr=0;
         <%}else{%>
            var sr=1;
         <%}%>
        
        
        
        var url;
        if(sr==0)
        {
        //alert("1");
           url="../../../../../InsertEmployeeProfile_db.con?command=Existgview&EmpId=" + strEmpId;   
           }
        else   
        {
        //alert("2");
           url="../../../../../InsertEmployeeProfile_db.con?command=ExistgviewSR&EmpId=" + strEmpId;   
           }
                     var req=getTransport();
            req.open("GET",url,true); 
            req.onreadystatechange=function()
            {
                if(req.readyState==4)
                    {
                      if(req.status==200)
                      {         
                      //alert("success");
                          //stopwaiting(document.frmEmployee) ;
                           var baseResponse=req.responseXML.getElementsByTagName("response")[0];
                           var flag=baseResponse.getElementsByTagName("flag")[0].firstChild.nodeValue; 
                           //alert(flag);
                            if(flag=="success")
                            {
                            
                                document.frmview.submit();
                                //call();
                               // document.getElementById("EmpImage").src="ShowImageProfile.jsp";
                                return true;
                            }
                            else
                            {
                                   //window.location="<%=request.getContextPath()%>/org/Library/jsps/Messenger.jsp?message=" + "Given Employee Id not Found!"; 
                                   alert('Enter a valid Employee Id');
                                   document.frmview.txtEmpId.value="";
                                   document.frmview.reset();
                                   document.frmview.txtEmpId.focus();
                                   return;
                           }
                       } 
                    }
         }
        req.send(null);
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
     
     

window.onunload=function()
{
if (winemp && winemp.open && !winemp.closed) winemp.close();
}

</script>
  </head>
  <body class="table" >
  <form name="frmview" method="post" action="">
  <%
  String sql="";
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
    	int count =0;
    	boolean flag = true;
        strEmpId=request.getParameter("txtEmpId");
        int empid=Integer.parseInt(strEmpId);
        
     // sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
   //  ps=con.prepareStatement(sql);
    // ps.setInt(1, empid);
    // while(rs.next())
    // {
    	// count ++;
    // }
    // if(count==0)
    // {
    	//response.sendRedirect("index.jsp");
    // }
     //else
    // {
        
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
                  
          //      ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
              
        try{
                 sql="SELECT a.EMPLOYEE_NAME, " +
                		"  a.EMPLOYEE_INITIAL, " +
                		"  a.GPF_NO, " +
                		"  a.DATE_OF_BIRTH, " +
                		"  a.GENDER, " +
                		"  a.REMARKS, " +
                		"  a.MARITAL_STATUS, " +
                		"  b.COMMUNITY_NAME, " +
                		"  c.District_Name, " +
                		"  d.Taluk_Name, " +
                		"  e.EMPLOYMENT_STATUS, " +
                		"  f.EMP_PHOTO_FILE_NAME , " +
                		"  TO_CHAR(g.retiredate,'dd/mm/yyyy') AS retiredate " +
                		"FROM HRM_MST_EMPLOYEES a " +
                		"LEFT OUTER JOIN HRM_MST_COMMUNITY b " +
                		"ON to_number(b.COMMUNITY_CODE)=to_number(a.COMMUNITY_ID) " +
                		"LEFT OUTER JOIN COM_MST_DISTRICTS c " +
                		"ON c.District_Code=a.NATIVE_DISTRICT_CODE " +
                		"LEFT OUTER JOIN COM_MST_TALUKS d " +
                		"ON d.Taluk_Code    =a.NATIVE_TALUK_CODE " +
                		"AND d.DISTRICT_CODE=a.NATIVE_DISTRICT_CODE " +
                		"LEFT OUTER JOIN HRM_MST_EMPLOYMENT_STATUS e " +
                		"ON e.EMPLOYMENT_STATUS_ID=a.EMPLOYMENT_STATUS_ID " +
                		"LEFT OUTER JOIN hrm_emp_addl_Photo_new_tmp f " +
                		"ON f.EMPLOYEE_ID=a.EMPLOYEE_ID " +
                		"LEFT OUTER JOIN HRM_PAY_RETIREMENT_VIEW G " +
                		"ON G.EMPLOYEE_ID   =a.EMPLOYEE_ID " +
                		"WHERE a.EMPLOYEE_ID=?";
               
                ps=con.prepareStatement(sql);
                ps.setInt(1,empid);
                rs=ps.executeQuery();
               
                while(rs.next())
                {
                    strEmpName=rs.getString("EMPLOYEE_NAME");
                    retiredate=rs.getString("retiredate");
                    if(rs.getString("EMPLOYEE_INITIAL")!=null)
                    {
                            strEmpName=rs.getString("EMPLOYEE_INITIAL")+"."+strEmpName;
                    }
                    System.out.println("GPF No:"+rs.getInt("GPF_NO"));
                    gpfno=rs.getInt("GPF_NO");
                    if(gpfno!=0)
                    {
                        strGpf=String.valueOf(gpfno);
                    }
                    if(rs.getDate("DATE_OF_BIRTH")!=null)
                    {
                        String[] sd=rs.getDate("DATE_OF_BIRTH").toString().split("-");
                        strDob=sd[2]+"/"+sd[1]+"/"+sd[0];
                    }
                    if(rs.getString("REMARKS")!=null){
                        strRemarks=rs.getString("REMARKS");
                    }
                    if(rs.getString("MARITAL_STATUS")!=null){
                        strMarital=rs.getString("MARITAL_STATUS");
                        if(strMarital.equalsIgnoreCase("M"))
                            strMarital="Married";
                        else
                            strMarital="Single";
                    }
                    if(rs.getString("GENDER")!=null){
                        strGender=rs.getString("GENDER");
                        if(strGender.equalsIgnoreCase("M"))
                            strGender="Male";
                        else if(strGender.equalsIgnoreCase("F"))
                            strGender="Female";
                       
                        
                    }
                    System.out.println("community:"+rs.getString("COMMUNITY_NAME"));
                     if(rs.getString("COMMUNITY_NAME")!=null){
                        strCommunity=rs.getString("COMMUNITY_NAME");
                    }
                    if(rs.getString("District_Name")!=null){
                            strDist=rs.getString("District_Name");
                    }
                    if(rs.getString("Taluk_Name")!=null){
                            strTaluk=rs.getString("Taluk_Name");
                    }
                    if(rs.getString("EMPLOYMENT_STATUS")!=null){
                        strEmpStatus=rs.getString("EMPLOYMENT_STATUS");
                    }
                     if(rs.getString("EMP_PHOTO_FILE_NAME")!=null){
                        EmpImage=rs.getString("EMP_PHOTO_FILE_NAME");
                        EmpImage=img+EmpImage;
                    }
                   
                    
                }
                
        
        }
        catch(Exception e)
        {
                System.out.println("Error in Employee Basic 1:"+e);
        }

        try{
        
                 sql="select a.DATE_EFFECTIVE_FROM,a.OFFICE_GRADE,a.DEPARTMENT_ID,a.OFFICE_ID,b.OFFICE_SHORT_NAME,c.DESIGNATION,"
                +" d.EMPLOYEE_STATUS_DESC,b.OFFICE_LEVEL_ID "
                +" from HRM_EMP_CURRENT_POSTING a "
                +" left outer join COM_MST_OFFICES b on b.OFFICE_ID=a.OFFICE_ID "
                +" left outer join HRM_MST_DESIGNATIONS c on c.DESIGNATION_ID=a.DESIGNATION_ID "
                +" left outer join HRM_MST_EMPLOYEE_STATUS d on d.EMPLOYEE_STATUS_ID = a.EMPLOYEE_STATUS_ID "
                +" where a.EMPLOYEE_ID=?";
               //System.out.println(sql);
                ps=con.prepareStatement(sql);
                ps.setInt(1,empid);
                rs=ps.executeQuery();
                if(rs.next())
                {
                        System.out.println("current posting is available");
                        if(rs.getDate("DATE_EFFECTIVE_FROM")!=null)
                        {
                            String[] sd=rs.getDate("DATE_EFFECTIVE_FROM").toString().split("-");
                            strDef=sd[2]+"/"+sd[1]+"/"+sd[0];
                        }
                        
                        if(rs.getString("OFFICE_GRADE")!=null){
                            strOffice_Grade=rs.getString("OFFICE_GRADE");
                        }
                        
                         if(rs.getString("DESIGNATION")!=null){
                            strDesignation=rs.getString("DESIGNATION");
                        }
                         if(rs.getString("EMPLOYEE_STATUS_DESC")!=null){
                            strCurStatus=rs.getString("EMPLOYEE_STATUS_DESC");
                        }
                        olevelid=rs.getString("OFFICE_LEVEL_ID");
                        offid=rs.getInt("OFFICE_ID");
                        dept=rs.getString("DEPARTMENT_ID");
                        System.out.println("DEPARTMENT_ID "+dept);
                         if(rs.getString("DEPARTMENT_ID")!=null){
                                if(rs.getString("DEPARTMENT_ID").equalsIgnoreCase("TWAD")){
                                     if(rs.getString("OFFICE_SHORT_NAME")!=null){
                                        OffName=rs.getString("OFFICE_SHORT_NAME");
                                    }
                                }
                                else
                                {
                                
                                   sql="select OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES "+
                                  " where OTHER_DEPT_OFFICE_ID=? and OTHER_DEPT_ID=?";
                                   //System.out.println(sql);
                                    ps=con.prepareStatement(sql);
                                    ps.setInt(1,offid);
                                    ps.setString(2,dept);
                                    rs=ps.executeQuery();
                                    if(rs.next())
                                    {
                                    System.out.println("other office name:"+rs.getString("OTHER_DEPT_OFFICE_NAME"));
                                        if(rs.getString("OTHER_DEPT_OFFICE_NAME")!=null){
                                            OffName=rs.getString("OTHER_DEPT_OFFICE_NAME");
                                        }

                                    }
                                    
                                    
                                   sql="select OTHER_DEPT_NAME	 from HRM_MST_OTHER_DEPTS "+
                                  " where OTHER_DEPT_ID	=? ";
                                   //System.out.println(sql);
                                    ps=con.prepareStatement(sql);
                                    ps.setString(1,dept);
                                    rs=ps.executeQuery();
                                    if(rs.next())
                                    {
                                   
                                        if(rs.getString("OTHER_DEPT_NAME")!=null){
                                            dept=rs.getString("OTHER_DEPT_NAME");
                                        }

                                    }
                                    
                                }
                        }
                        
                
                }
        }
        catch(Exception e)
        {
                System.out.println("Error in Current  Posting 2:"+e);
        }
        
         try{
        
                 sql="select b.DATE_FROM ,c.DESIGNATION"
                +" from HRM_EMP_SERVICE_DATA b"
                 +" left outer join HRM_MST_DESIGNATIONS c on c.DESIGNATION_ID=b.DESIGNATION_ID "
                +" where EMPLOYEE_ID=? and  b.DATE_FROM = (select min(a.DATE_FROM) from HRM_EMP_SERVICE_DATA a  where a.EMPLOYEE_ID=?)";
               //System.out.println(sql);
                ps=con.prepareStatement(sql);
                ps.setInt(1,empid);
                ps.setInt(2,empid);
                rs=ps.executeQuery();
                while(rs.next())
                {
                        System.out.println("SR is available");
                         if(rs.getDate("DATE_FROM")!=null)
                        {
                            String[] sd=rs.getDate("DATE_FROM").toString().split("-");
                            strDoj=sd[2]+"/"+sd[1]+"/"+sd[0];
                        }
                        if(rs.getString("DESIGNATION")!=null){
                            strSRDesignation=rs.getString("DESIGNATION");
                        }
                        
                
                }
        }
        catch(Exception e)
        {
                System.out.println("Error in Service Record 3:"+e);
        }
        
         try{
         
             
               
                //if(olevelid!=null)
                if(empid!=0)
                {        
                sql="select a.CONTROLLING_OFFICE_ID,b.OFFICE_NAME from HRM_EMP_CONTROLLING_OFFICE a,COM_MST_OFFICES b where b.OFFICE_ID=a.CONTROLLING_OFFICE_ID AND  a.EMPLOYEE_ID=?";
                }
                /*
                else if(olevelid.equalsIgnoreCase("SD") || olevelid.equalsIgnoreCase("AW"))
                {        
                sql="select a.CONTROLLING_OFFICE_ID,b.OFFICE_NAME from HRM_EMP_CONTROLLING_OFFICE a,COM_MST_OFFICES b where b.OFFICE_ID=a.CONTROLLING_OFFICE_ID AND  a.EMPLOYEE_ID=?";
                }                
                else
                {
                 sql="select b.controlling_office_id,c.office_name from hrm_emp_controlling_office a " +
                      " left outer join com_office_control b on b.office_id=a.controlling_office_id " +
                      " left outer join com_mst_offices c on c.office_id=b.controlling_office_id " +
                      " where a.employee_id=?"; 
                }*/
                //System.out.println(sql);
                ps=con.prepareStatement(sql);
                ps.setInt(1,empid);
                rs=ps.executeQuery();
                if(rs.next())
                {
                        System.out.println("SR Controlling Office is available");
                        
                        ctrlOfficeId=rs.getInt("CONTROLLING_OFFICE_ID");
                        if(ctrlOfficeId!=0)
                            strctrlOfficeId=String.valueOf(ctrlOfficeId);
                       
                        if(rs.getString("OFFICE_NAME")!=null){
                            ctrlOfficeName=rs.getString("OFFICE_NAME");
                        }
                        
                
                }
        }
        catch(Exception e)
        {
                System.out.println("Error in Service Record 3:"+e);
        }
        
        
         try{
                if(olevelid!=null && olevelid.equalsIgnoreCase("HO"))
                {
                         sql="select OFFICE_WING_SINO from HRM_EMP_CURRENT_WING where  EMPLOYEE_ID=?";
                        //System.out.println(sql);
                        ps=con.prepareStatement(sql);
                        ps.setInt(1,empid);
                        rs=ps.executeQuery();
                        if(rs.next())
                        {
                                System.out.println("Wing is available");
                                wing=rs.getInt("OFFICE_WING_SINO");
                        }
                }
        }
        catch(Exception e)
        {
                System.out.println("Error in Service Record 3:"+e);
        }
     //}
   } 
%>


    <table  width="90%" align="center" style="border:1px solid #0489B1">
              <tr style="height:30px" >
                <td align="left" colspan="2" style="color: #fff;padding-left:5px;" bgcolor="#006699">
                <table width="100%">
                <tr><td width="25%" ></td><td width="50%" ><center>
                    <b>Employee&nbsp;Profile </b>
                  </center></td><td width="25%" >
                  <img alt="Back" src="back.png" width="30" height="30" style="cursor:pointer" onclick="window.history.go(-1)"  title="Go Back" > &nbsp; &nbsp; 
                  <img alt="Back" src="close.png" width="30" height="30" style="cursor:pointer" onclick="self.close()" title="Close" >
                  </td></tr>
                </table>
                  
                </td>
              </tr>
              <tr>
                <td >Employee Id:</td>
                <td  valign="center">
                  <table border="0" align="left">
                    <tr>
                      <td><%=strEmpId%>
                        <input tabindex="1" type="hidden" name="txtEmpId"  id="txtEmpId" maxlength="5" value="<%=strEmpId%>"
                               onkeypress="return  numbersonly1(event,this)"  onchange="validate()"></input>
                    <br></br>
                    <%
                    String newpath="";
                              session=request.getSession(false);
                             %>
                    
                     </td>
                      <td align="right" >
                            <%
                            System.out.println("EmpId..........."+strEmpId);
                            newpath="ShowImageProfile.jsp?empid="+strEmpId;
                            %>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <img  src=<%=newpath%> align="top"  class='magnify' alt="Photo" width="90" height="90" id="EmpImage"></img>
                       </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td >Employee Name</td>
                <td ><b><%=strEmpName%>&nbsp;</b> </td>
              </tr>
              <tr height="30">
                <td  colspan="2"  colspan="2" style="color: #fff;padding-left:5px;" bgcolor="#006699">
                  <b>Designation and Office Details</b>
                </td>
              </tr>
              <tr>
                <td >Designation </td>
                <td ><%=strDesignation%>&nbsp;</td>
              </tr>
              <tr>
                <td >Grade </td>
                <td ><%=strOffice_Grade%>&nbsp; </td>
              </tr>
              <tr>
                <td >Office </td>
                <td ><%=OffName%>&nbsp;<%if(dept!=null){out.print("("+dept+")");}%>  </td>
              </tr>
              
              
              <%if(olevelid!=null && olevelid.equalsIgnoreCase("HO") ){%>
          <tr class="table" id="wing" >
                          <td align="left">
                            Wing
                          </td>
                          <td align="left">
                             <%
                        ResultSet rs1=null;
                        try
                        {
                        ps=con.prepareStatement("select WING_NAME from COM_OFFICE_WINGS where OFFICE_ID=? and OFFICE_WING_SINO=?");
                        ps.setInt(1,offid);
                        ps.setInt(2,wing);
                        rs1=ps.executeQuery();
                        String strname="";    
                        if(rs1.next())
                        {
                        strname=rs1.getString("WING_NAME");
                        if(strname!=null){
                            out.println(strname);
                        }
                        else
                        {
                            out.println("nbsp;");
                        }
                        }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in the service group id"+e);
                        }
                        
                        
                        %>
                          </td>
                 </tr>
            <%}%>
            
            
              <tr>
                <td >Date of Current Posting</td>
                <td ><%=strDef%>&nbsp;  </td>
              </tr>
               <tr>
                <td>Current Status</td>
                <td><%=strCurStatus%>&nbsp;</td>
              </tr>
              <tr height="30">
                <td colspan="2"  style="color: #fff;padding-left:5px;" bgcolor="#006699">
                  <b>General Particulars</b>
                </td>
              </tr>
              <tr>
                <td >Date Of Birth </td>
                <td ><%=strDob%>&nbsp; </td>
              </tr>
              <tr>
                <td >Date Of Retirement </td>
                <td ><%=retiredate%>&nbsp; </td>
              </tr>
              
              <tr>
                <td >Gender </td>
                <td ><%=strGender%>&nbsp; </td>
              </tr>
              <tr>
                <td >Community </td>
                <td ><%=strCommunity%>&nbsp;</td>
              </tr>
            <tr>
                <td >Native&nbsp;District</td>
                <td ><%=strDist%>&nbsp;</td>
              </tr>
              <tr>
                <td >Native Taluk Name</td>
                <td ><%=strTaluk%>&nbsp;</td>
              </tr>
              <!--  <tr>
                <td >Employment&nbsp;Status</td>
                <td ><%=strEmpStatus%>&nbsp;</td>
              </tr>
              <tr>
                <td >GPF Number</td>
                <td ><%=strGpf%>&nbsp; </td>
              </tr>
              <tr>
                <td >Date&nbsp;of&nbsp;Joining in TWAD Board (as per SR)</td>
                <td ><%=strDoj%>&nbsp;</td>
              </tr>
              <tr>
                <td >Designation&nbsp;at&nbsp;the&nbsp;Time&nbsp;of&nbsp;Joining&nbsp;(as per SR) </td>
                <td ><%=strSRDesignation%>&nbsp;</td>
              </tr>
              <tr>
                <td >Marital Status</td>
                <td ><%=strMarital%>&nbsp;</td>
              </tr>
              <tr>
                <td >Remarks</td>
                <td ><%=strRemarks%>&nbsp; </td>
              </tr>-->
              <tr height="30">
                <td colspan="2" style="color: #fff;padding-left:5px;" bgcolor="#006699">
                  <b>SR Controlling Office Detail</b>
                </td>
              </tr>
              <tr>
                <td >SR Controlling Office ID</td>
                <td ><%=strctrlOfficeId%>&nbsp; </td>
              </tr>
              <tr>
                <td >SR Controlling Office Name</td>
                <td ><%=ctrlOfficeName%>&nbsp; </td>
              </tr>
              <tr  style="color: #fff;padding-left:5px;" bgcolor="#006699">
              <td colspan="4" >Additional Charge Details</td>
              </tr>
              
               <tr>
              <td colspan="2">
              <table width="100%" border="1"  style="border:1px solid #0489B1">
              <tr style="color: #fff;padding-left:5px;" bgcolor="#006699"><th style="color: #fff;padding-left:5px;" bgcolor="#006699"> <b>S.No</th><th> <b>Designation</b></th><th> <b>Office Name</b></th><th> <b>Date Effective From</b></th></tr>
              <%
              try
              {
            	  String s="SELECT employee_id, " +
"  designation, " +
"  office_name, " +
"  date_effective_from, " +
"  slno, " +
"  addl_charge_type " +
"FROM " +
"  (SELECT employee_id, " +
"    office_id, " +
"    designation_id, " +
"    to_char(date_effective_from,'dd/mm/yyyy') as date_effective_from,  " +
"    slno, " +
"    addl_charge_type " +
"  FROM HRM_EMP_ADDL_CHARGE " +
"  WHERE employee_id            ="+strEmpId+" " +
"  AND (ADDL_CHARGE_STATUS NOT IN 'CL' " +
"  OR ADDL_CHARGE_STATUS       IS NULL) " +
"  ) a " +
"LEFT OUTER JOIN " +
"  ( SELECT office_id AS off_id,office_name FROM com_mst_offices " +
"  ) b " +
"ON a.office_id=b.off_id " +
"LEFT OUTER JOIN " +
"  ( SELECT designation_id AS desig_id,designation FROM hrm_mst_designations " +
"  ) c " +
"ON a.designation_id=c.desig_id " +
"ORDER BY slno";

            	  
            	  System.out.println("sss is---"+s);
            	  ps=con.prepareStatement(s);
            	  rs=ps.executeQuery();
            	  int a=0;
            	  while(rs.next())
            	  {
            		  a++;
            		  out.println(" <tr  ><td>"+a+"</td><td>"+rs.getString("designation")+"</td><td>"+rs.getString("office_name")+"</td><td>"+rs.getString("date_effective_from")+"</td></tr>");
            	  }
            	  
              }
              catch(Exception e)
              {
            	  
              }
              
              %>
              </table>
              
              
              </td>
              </tr>
              <tr  style="color: #fff;padding-left:5px;" bgcolor="#006699">
              <td colspan="4" >Service Details</td>
              </tr>
              <tr>
              <td colspan="2">
              <table width="100%" style="border:1px solid #0489B1" >
              <tr height="30" style="color:#fff" bgcolor="#4D94B8" align="center" ><th>S.No</th><th>Date From</th><th>Date To</th><th>Designation</th><th>Office Name</th><th>Status</th></tr>
              <%
              try 
              {
            	  String s="SELECT A.EMPLOYEE_ID, " +
            			  "  A.EMP_NAME, " +
            			  "  A.DATE_FROM, " +
            			  "  A.DATE_TO, " +
            			  "  A.DESIGNATION, " +
            			  "  DECODE(a.EMPLOYEE_STATUS_ID,'DPN', d.other_dept_office_name,DECODE(B.Office_Name,NULL,' ','null',' ' ,B.Office_Name )) AS Office_Name , " +
            			  "  C.EMPLOYEE_STATUS_DESC, " +
            			  "  A.SERVICE_LIST_SLNO " +
            			  "FROM " +
            			  "  (SELECT A.EMPLOYEE_ID, " +
            			  "    TO_CHAR(A.DATE_FROM,'dd/mm/yyyy') AS DATE_FROM , " +
            			  "    TO_CHAR(A.DATE_TO,'dd/mm/yyyy')   AS DATE_TO, " +
            			  "    C.EMPLOYEE_NAME " +
            			  "    ||'.' " +
            			  "    ||C.EMPLOYEE_INITIAL AS EMP_NAME , " +
            			  "    B.DESIGNATION, " +
            			  "    A.OFFICE_ID, " +
            			  "    A.EMPLOYEE_STATUS_ID, " +
            			  "    a.office_dept_id, " +
            			  "    A.SERVICE_LIST_SLNO " +
            			  "  FROM HRM_EMP_SERVICE_DATA A , " +
            			  "    HRM_MST_DESIGNATIONS B, " +
            			  "    HRM_MST_EMPLOYEES C " +
            			  "  WHERE A.EMPLOYEE_ID ="+strEmpId+" " +
            			  "  AND A.DESIGNATION_ID=B.DESIGNATION_ID " +
            			  "  AND A.EMPLOYEE_ID   =C.EMPLOYEE_ID " +
            			  "  )A " +
            			  "LEFT OUTER JOIN COM_MST_OFFICES B " +
            			  "ON B.OFFICE_ID=A.OFFICE_ID " +
            			  "LEFT OUTER JOIN HRM_MST_EMPLOYEE_STATUS C " +
            			  "ON C.EMPLOYEE_STATUS_ID=A.EMPLOYEE_STATUS_ID " +
            			  "LEFT OUTER JOIN Hrm_Mst_Other_Dept_Offices D " +
            			  "ON d.other_dept_id =a.office_dept_id AND d.OTHER_DEPT_OFFICE_ID=a.Office_Id  " +
            			  "ORDER BY SERVICE_LIST_SLNO";
            	  
            	  
            	   System.out.println("ss is-->"+s);
            	  
            	  System.out.println(s);
            	  ps=con.prepareStatement(s);
            	  rs=ps.executeQuery();
            	  int a=0;
            	  int j=0;
            	  String style="";
            	  while(rs.next())
            	  {
            		  if(j==0)
            			{
            				j++;
            				
            				style="background:#fff;" ;
            			}
            			else
            			{
            				j=0;
            				style="background:#B2D1F0; " ;
            			}
            		  
            		  a++;
            		  out.println(" <tr style='"+style+"' height='30px' ><td>"+a+"</td><td>"+rs.getString("DATE_FROM")+"</td><td>"+rs.getString("DATE_TO")+"</td><td>"+rs.getString("DESIGNATION")+"</td><td>"+rs.getString("OFFICE_NAME")+"</td><td>"+rs.getString("EMPLOYEE_STATUS_DESC")+"</td></tr>");
            	  }
            	  
              }
              catch(Exception e)
              {
            	  
              }
              
              %>
              </table>
              
              
              </td>
              </tr>
              
              <tr>
                <td colspan="2" class="tdH" align="center">
                  <input type="BUTTON" value=" Close " onclick="self.close();"></input>
                  <input type="Button" value=" Back "
                         onclick="window.history.back()"></input>
                </td>
              </tr>
            </table>
     </form></body>
</html>