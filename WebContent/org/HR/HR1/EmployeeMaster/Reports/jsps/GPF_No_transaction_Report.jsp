<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; 
charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>List of Employees having No Transaction</title>
    <script type="text/javascript"
src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../scripts/New_Trans_Joinedit_Script_New.js"></script>
    <script type="text/javascript" src="../../scripts/Hrm_TransJoinJS_New.js"></script>
     <script type="text/javascript" src="../../scripts/GPF_Withdrawal_Details.js">  </script>
 <script>
 function f()
 {
 	if(document.getElementById("fin_year").value==0)
 	{
 		alert("Choose Financial Year");
 		return false;
 	}
	return true;
 }

 
 </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

    <style type="text/css">
.cal {font-family:verdana; font-size:12px;}
</style>
  </head>
  <body id="bodyid" >
  <form name="Hrm_TransJoinForm" method="Post" id="Hrm_TransJoinForm" action="../../../../../../GPF_Interest_OB" onsubmit="return f()" >
      <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    Connection connection=null;
    ResultSet results=null;
    ResultSet results1=null;
    ResultSet results2=null;
  try
  { LoadDriver driver=new LoadDriver();
	con=driver.getConnection();
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
      <!-- OFFICE DETAILS -->
    <% 
    ArrayList unitid=new ArrayList();
    ArrayList unitname=new ArrayList();
    ArrayList fin_year_lst=new ArrayList();

    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    int oid=0,unit_id=0,i=0;
    int acc_for[]=new int[5];
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="",olevelname="",oldcode="";
    String olname="",olevelid=""; 
    String ownature="",unit_name="";
    //ArrayList unit_name=new ArrayList();
    Calendar cal=Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    System.out.println("Current month & Year: "+month + " "+year );
  
    try
    {
           
            ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");                 
                 }
            results.close();
            ps.close();
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME,c.OFFICE_LEVEL_NAME from COM_MST_OFFICES a "+" left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE "+" inner join com_mst_office_levels c on  c.office_level_id=a.office_level_id "+" where OFFICE_ID=?");            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                    oadd1=results.getString("OFFICE_ADDRESS1");
                    oadd2=results.getString("OFFICE_ADDRESS2");
                    ocity=results.getString("CITY_TOWN_NAME");
                    odist=results.getString("DISTRICT_NAME");
                    olevelname=results.getString("OFFICE_LEVEL_NAME");
                    olevelid=results.getString("OFFICE_LEVEL_ID");
            }  
        }
        catch(SQLException e)
    {
        System.out.println(e);
    }
        
        
                try{
                 ps=con.prepareStatement("select accounting_unit_id,accounting_unit_name from fas_mst_acct_units where accounting_unit_office_id=?" );        
                 ps.setInt(1,oid);
                 results=ps.executeQuery();
                 while(results.next()) 
                 {
                    unit_id=results.getInt(1);
                    unitid.add(results.getString(1));
                    unit_name=results.getString(2);
                    unitname.add(results.getString(2));
                    System.out.println("unit_name ::"+unit_name);
                    
                  }
            results.close();
            ps.close();
               
            }
    catch(SQLException e)
    {
        System.out.println(e);
    }
   
   
   
   %>
   <!--**************For Show the DBF file Values************-->
<%
//Integer Curr_Year=new Date().getYear();

Calendar calen=Calendar.getInstance();
int Curr_Year=calen.get(Calendar.YEAR);
ArrayList records=new ArrayList();

int accId=0,Ac_mnth=0,Ac_year=0,officeId=0;
String file_type="";

if(request.getParameter("unit_name")!=null)
accId=Integer.parseInt(request.getParameter("unit_name"));
if(request.getParameter("ac_month")!=null)
Ac_mnth=Integer.parseInt(request.getParameter("ac_month"));
if(request.getParameter("ac_year")!=null)
Ac_year=Integer.parseInt(request.getParameter("ac_year"));
if(request.getParameter("file_type")!=null)
file_type=request.getParameter("file_type");

if(request.getParameter("ac_month")!=null)
    officeId=Integer.parseInt(request.getParameter("txtOffId"));
System.out.println("accId"+accId+"offId"+officeId+"month"+Ac_mnth+"year"+Ac_year);
%>

      <div align="center" id="xxx">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>List of Employees having No Transaction</strong>
              </div>
            </td>
          </tr>
         
          <tr class="table">
            <td colspan="2">
              <div align="left">Office Details</div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office ID</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffId" id="txtOffId" maxlength="6" value="<%=oid%>" size="6" class="disab" readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Name</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName"
                       value="<%=oname%>" maxlength="30" size="43" 
class="disab" readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Level</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOfflevel" id="txtOffLevel"
                       value="<%=olevelname%>" maxlength="30" size="43"
                       class="disab" readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Office Address</div>
            </td>
            <td>
              <div align="left">
                <textarea rows="4" cols="40" name="txtOffAddr" id="txtOffAddr"  readonly="readonly" class="disab">
                  <%
                String s=null;
                if(oadd1!=null)
                {
                    s=oadd1;
                }
                if(oadd2!=null)
                {
                    s+="\n"+oadd2;
                }
                if(ocity!=null)
                {
                    s+="\n"+ocity;
                }
                if(odist!=null)
                {
                    s+="\n"+odist;
                }
                if(s!=null)
                    out.print(s);   
                                
                %>
                </textarea>
              </div>
            </td>
          </tr>
          <tr class="table">
          
          <td>
              <div align="left">Financial Year</div>
            </td>
            <td>
              <div align="left">
                  <select name="fin_year" id="fin_year" >
                  <option >--Select Financial Year--</option>
                  
               <% try{ ps=con.prepareStatement("select SLIP_ISSUED_FIN_YEAR,(substr(SLIP_ISSUED_FIN_YEAR,6)||'-' ||(substr(SLIP_ISSUED_FIN_YEAR,0,4)+2)) as last_slip     from HRM_GPF_LAST_SLIP_ISSUED " );
               rs=ps.executeQuery();
               if(rs.next())
            	   {%>
            	     <option value="<%=rs.getString("last_slip") %>"><%=rs.getString("last_slip")  %></option>  
            	  
            	   <%}
               }catch(Exception e){out.println(e);}
               %>
               
                </select> <input type="hidden" name="form" id="form" value="No_Trn_Rep"/>
              </div>
            </td>
          </tr>
         <tr>
                    <td colspan="2" class="tdH">Report Output Format</td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" checked value="pdf"></input>PDF
                                                                         Format
                      
                    </td>
                  </tr>
                  <tr>
                    <td colspan="2" class="table" align="left">
                      
                        <input type="radio" name="optoutputtype" value="excel"></input>EXCEL
                                                                         Format
                      
                    </td>
                  </tr>
     
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="submit" name="show" id="show" value="Show"/>
                    </div>
            </td>
          </tr>
        </table>
    

      </div>      
   

    </form>
    </body>
</html>