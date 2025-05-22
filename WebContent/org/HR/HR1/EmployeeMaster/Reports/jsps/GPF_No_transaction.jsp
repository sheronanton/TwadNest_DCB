<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver,oracle.sql.*"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; 
charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>No Transaction Employees List</title>
    <script type="text/javascript"
src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../scripts/New_Trans_Joinedit_Script_New.js"></script>
    <script type="text/javascript" src="../../scripts/Hrm_TransJoinJS_New.js"></script>
     <script type="text/javascript" src="../../scripts/GPF_Withdrawal_Details.js">  </script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/HR/HR1/EmployeeMaster/Reports/scripts/NegativeOBlist.js">  </script>
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
  {
	  LoadDriver driver=new LoadDriver();
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

int cnt=0;
String file_type="";




%>

      <div align="center" id="xxx">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>No Transaction Employees List</strong>
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
                  
               <% 
               
               try
               {
            	
				CallableStatement stmt =con.prepareCall( "{call HRM_GPF_FIN_YEAR(?)}" );
				
				stmt.registerOutParameter( 1,Types.ARRAY,"ABC" );
				stmt.execute();
				oracle.sql.ARRAY finyear = (oracle.sql.ARRAY)stmt.getObject(1);
				String[] finyears = (String[])finyear.getArray();
				cnt=finyears.length;
				for( i=0;i<cnt;i++)
				{
						System.out.println("test"+finyears[i]);
				%>
				<option value="<%=finyears[i]%>"><%=finyears[i]%> </option>
				<%	}

				stmt.close();
				con.close();

            	  
            	  
               }catch(Exception e){
            	   System.out.println(e);}
               %>
               
                </select>
              </div>
            </td>
          </tr>
           <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="show" id="show" value="Show" onclick="callServer('GetNo',null)" />
                    </div>
            </td>
          </tr>
       <tr class="table">
       
           <td align="right">
                Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" onchange="changepagesize()">
                  <option value="5">5</option>
                  <option value="10" selected="selected">10</option>
                  <option value="15">15</option>
                  <option value="20">20</option>
                </select>
           </td>
          </tr>
        </table>
         <table id="mytable" align="center"  cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <th >
              Select All <input type="checkbox" name="chkall" onclick="sellectall()"><br/>
              <a href="javascript:inverseselect()">Inverse&nbsp;Select</a>
            </th>
            <th>
            Employee&nbsp;ID
            </th>
            <th>
            Employee&nbsp;Name
            </th>
            <th>
            Designation
            </th>
            <th>
            Office Name
            </th>
            <th>
            Status
         
            </th>
          </tr>
          <tbody id="tb" class="table">

          </tbody>
        </table>
     
      <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
 
   <tr class="tdH">
      <td>
       <table align="center"  cellspacing="3" cellpadding="2" border="0" width="100%" >
       <tr >
       <td width="30%">
          <div align="left"> <div id="divpre" style="display:none"></div> </div>
      </td>
       <td width="40%">
          <div align="center"><table border="0"><tr><td> <div id="divcmbpage" style="display:none" >
          Page&nbsp;&nbsp;<select name="cmbpage" id="cmbpage" onchange="changepage()"></select> <br></br><br>&nbsp;</br>
         &nbsp;&nbsp; 
          </div></td><td><div id="divpage"></div></td></tr></table> </div>
      </td>
      <td width="30%">
          <div align="right"> <div id="divnext" style="display:none"></div> </div>
      </td>
      </tr>
      </table>
      </tr>
      <tr class="tdH">
      <td >
          <div align="center">
     <input type="button" name="CmdAdd" value="Submit" id="CmdAdd" onclick="callServer('AddNotrn','null')" style="width: 85px"/>
        <input type="button" name="CmdClear" value="Clear All" id="CmdClear" onclick="clear_all()" style="width: 111px"/>
         
      </div>
       </td>
       </tr>
       
         
        </table>
    

      </div>      
   

    </form>
    </body>
</html>