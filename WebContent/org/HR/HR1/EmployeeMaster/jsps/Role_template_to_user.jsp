<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%
  
  Connection con=null;
  
  
  
  try
  {
  
	  LoadDriver driver=new LoadDriver();
  	con=driver.getConnection();
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
     
     ResultSet rs=null;
Statement statement=null;
        PreparedStatement ps=null; List payl = new ArrayList();
        List vall =new ArrayList();

         try{

                    ps=con.prepareStatement("select distinct sr.role_template_id,st.role_template_name from sec_mst_template_roles sr,sec_mst_templates st where st.role_template_id = sr.role_template_id order by sr.role_template_id");
                    rs=ps.executeQuery();

                    while(rs.next()){

                    payl.add(rs.getString(2));
                    vall.add(rs.getInt(1));
                   // System.out.println("my output for display ::"+rs.getString(2)+"    value ---- "+rs.getInt(1));
                    }


                    }
            catch(SQLException e) {

                e.printStackTrace();
            }
  
  
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Role Assigning  </title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <script type="text/javascript"
            src="../scripts/Role_template_to_user.js"></script>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
      
    .navigation {
  height:100px; width:283px; 
  border-style:1px solid #666;
  border-width:1px; 
  background-color: #fff;
  float:left; 
  overflow: auto;
}

   
      
    </style>
  </head>
  <!--- action="../../../../../EmployeeDetailReportServ.rep" -->
  <body >
  <form   name="assigtemplatetouser" >
  
         
            
          
            
  
      <div align="center">
        <table border='2' width="100%">
          <tr>
            <td  align="left" colspan=2 valign="top" class="table" width="100%">
              <table border="0" width="100%">
             
                <tr>
                  <td  class="tdH" align="center" colspan=3><strong>Assignment of Role Template to Users</strong></td>
                </tr>
                
    
              </table>
            </td>
          
          </tr>
          <tr class="table">
             <td colspan="2"><strong>
            Employee ID Range</strong>
             </td></tr>
                <tr class="table">
    
                  <td>
                   From  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                    <input type="text" name="fromtxt" id="fromtxt" onkeypress="return isNumberKey(event)"/></td>
                  <td style="width: 529px">
                  To  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="totxt" id="totxt" onkeypress="return isNumberKey(event)" onblur="checking()" />
                             </td>
               </tr> 
           
          <tr class="table">
          <td align="right"> Select Template  </td>
              <td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <select name="template" id="template">
                  <option value="0">---Select Template---</option>
<%
List userList;
int nouser;
int i=1;

int j=1;
nouser = payl.size();
//vall
Iterator pyls= payl.iterator();
Iterator valu= vall.iterator();
while(pyls.hasNext()){
while(i<=nouser){%>
<option value="<%=valu.next()%>"><%=pyls.next()%></option>
<%
i++;
}
}
%>
                </select>
       </td></tr>
        
<tr>
                 
            <td   colspan=2 class="tdH" align="center" ><input type="button" value="Grant" onclick="frmtempgrant()" />
             <input type="button" id="revoke" name="revoke" value="Revoke" onclick="frmtemprevoke()" />
             <input type="reset"  value="Clear"  />
            <input type="button" id="cmdcancel" name="cmdcancel" value="Close" onclick="self.close();" /> </td>
          </tr>
        </table>
     
     </div>
     </form>
     <div id="PHPDATA" > </div>
    </body>
</html>
