<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>

<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>Validate Joining Report Details</title>
   <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
   <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/New_Trans_JoinValid_Script_New.js"></script>
 <!-- <script type="text/javascript" src="../../../../../org/Library/scripts/CalendarControl.js"></script>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
   <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  </head>
  <body id="bodyid">
  <form name="frmEmployee" id="frmEmployee">
  
  <%
  
    Connection con=null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    ResultSet rss=null;
    PreparedStatement pss=null;
    
     Connection connection=null;

  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
    
   try
  {
  
             ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rb.getString("Config.DSN");
            String strhostname=rb.getString("Config.HOST_NAME");
            String strportno=rb.getString("Config.PORT_NUMBER");
            String strsid=rb.getString("Config.SID");
            String strdbusername=rb.getString("Config.USER_NAME");
            String strdbpassword=rb.getString("Config.PASSWORD");

        //    ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection 

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  %>
  
  
   <!-- OFFICE DETAILS -->
                <% 
    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
    int  oid=0;
    String user_id="twad"+empid;
    String oname="",oadd1="",oadd2="",ocity="",odist="",olid="",owid="",olevelname="";
    String olname="",olevelid=""; 
    String ownature="";
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
            ps=con.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.DISTRICT_CODE,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,b.DISTRICT_NAME,c.OFFICE_LEVEL_NAME from COM_MST_OFFICES a "+
            " left outer join com_mst_districts b on b.DISTRICT_CODE= a.DISTRICT_CODE "+
            " inner join com_mst_office_levels c on  c.office_level_id=a.office_level_id "+
            " where OFFICE_ID=?");
            ps.setInt(1,oid);
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
            results.close();
            ps.close();
               
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
  
      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>Validate Joining Report Details</strong>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">
                Office Details
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office ID
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffId" id="txtOffId" maxlength="6" value="<%=oid%>"
                       size="6"  class="disab"  readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office Name
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOffName" id="txtOffName"  value="<%=oname%>"
                       maxlength="40" size="43" class="disab"  readonly="readonly"/>
              </div>
            </td>
          </tr>
           <tr class="table">
            <td>
              <div align="left">
                Office Level
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtOfflevel" id="txtOffLevel"  value="<%=olevelname%>"
                       maxlength="30" size="43" class="disab" readonly="readonly"/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Office Address
              </div>
            </td>
            <td>
              <div align="left">
                
<textarea rows="4" cols="40"  name="txtOffAddr" id="txtOffAddr" readonly="readonly"
                class="disab"><%
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
                                
                %></textarea>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">
                Employee Details
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Employee ID
                  
                        <font color="#ff2121">
                          *
                        </font>
                     
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtEmpId1" id="txtEmpId1" onkeypress="return  numbersonly1(event,this)" maxlength="6" size="6" onchange="callServer1('Load','null')" />
                 <img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="servicepopup1();"></img>
             </div>
            </td>            
            </tr>
          <tr class="table">
            <td>
              <div align="left">
                Employee Name
              </div>
            </td>
            <td>
              <div align="left">
              <input type="text" name="Employee_Name" id="Employee_Name" class="disab" size="43" readonly/>
              
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                DOB
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="Date_Of_Birth" id="Date_Of_Birth" class="disab" maxlength="10" readonly/>
                      
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                GPF NO.
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="Gpf_Number" id="Gpf_Number" class="disab" maxlength="10"
                       size="10" readonly/>
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Cadre
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtcad" id="txtcad" maxlength="100" style="background-color: #ececec" readonly
                       size="40"/>
              </div>
            </td>
          </tr>
          

          
          <tr class="tdH">
            <td colspan="2">
              <div align="left">
                Details Of Joining Report
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Joining Report ID
              </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtJRId" id="txtJRId" size="13"
                        class="disab" readonly/>
                        <!--<img src="../../../../../images/c-lovi.gif"
                                                           width="20"
                                                           height="20"
                                                           alt="empList"
                                                           onclick="Joinpopup();"></img>-->
                       <input type="hidden" name="txtJR" id="txtJR" 
                       size="13" class="disab"/>
              </div>
            </td>
          </tr>
          
          
         <!--  <tr class="table" id="drcompdate" style="display:none">
            <td>
              <div align="left" id="divcompleted" >
                 Completed Date
              </div>
            </td>
            <td>
              <div align="left">
              <input type="hidden" name="txtempstatus" id="txtempstatus">
              <input type="text" name="txtDOC" id="txtDOC"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3'; return checkEID() "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);"/>
                 
                <img src="../../../../../images/calendr3.gif"
                     onclick=" if(toFocus()==true)showCalendarControl(document.frmEmployee.txtDOC);"
                     alt="Show Calendar"></img>
              
             <input type="radio" name="optFNAN" id="optFNAN1" value="FN" checked onfocus="return toFocus()" />FN
                         &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="optFNAN" id="optFNAN2" value="AN" onfocus="return toFocus()" />AN
                         
              </div>
            </td>
          </tr>
          
      -->   
          
          <tr class="table">
            <td>
              <div align="left">
                Date Of Joining<font color="#ff2121">
                  <font color="#ff2121">
                    <font color="#ff2121">
                      <font color="#ff2121">
                        <font color="#ff2121">
                          *
                        </font>
                      </font>
                    </font>
                  </font>
                </font>
              </div>
            </td>
            <td>
              <div align="left">
               <input type="hidden" name="txtempstatus" id="txtempstatus">
            <input type="text" name="txtDOJ" id="txtDOJ"
                       maxlength="10" size="11"
                       onfocus="javascript:vDateType='3'; return toFocus() "
                       onkeypress="return calins(event,this);"
                       onblur="if(checkcurdt(this)==true)return checkdt(this);" readonly/>
           <!-- <input type="text" name="txtDOJ" id="txtDOJ" maxlength="10" class="disab" onFocus="javascript:vDateType='3'" onkeypress="return  calins(event,this)" onblur="return checkdt(this);" readonly/>
            
                                                             <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.Hrm_TransJoinForm.txtDOJ);" alt="Show Calendar" ></img>-->
             <input type="hidden" name="txtDOJ1" id="txtDOJ1" maxlength="10" />
             <input type="radio" name="radFNAN" id="radFNAN" value="FN" checked onfocus="return toFocus()"/>FN
                         &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="radFNAN" id="radFNAN" value="AN" onfocus="return toFocus()" />AN
              </div>
            </td>
          </tr>
           <%if(olid.equalsIgnoreCase("DN")){%>
          <tr class="table">
            <td>
              <div align="left">
                Whether Joined in<font color="#ff2121">
                  <font color="#ff2121">
                    <font color="#ff2121">
                      <font color="#ff2121">
                        <font color="#ff2121">
                          *
                        </font>
                      </font>
                    </font>
                  </font>
                </font>
              </div>
            </td>
            <td>
              <div align="left">
              <input type="radio" name="optjoin" value="D" onclick="subdivisioncheck()" checked="checked"/> Division <input type="radio" name="optjoin" value="S" onclick="subdivisioncheck()" /> SubDivision
            <div style="display:none" id="divsubdivision"><select name="cmbsubdivision" id="cmbsubdivision">
            <option value="">Select SubDivision</option>
            
             <%
                        ResultSet rs1=null;
                        try
                        {
                        ps=con.prepareStatement("select SUBDIVISION_OFFICE_ID,SUBDIVISION_OFFICE_NAME from COM_MST_SUBDIVISIONS_HVIEW where DIVISION_OFFICE_ID=? order by SUBDIVISION_OFFICE_NAME");
                        ps.setInt(1,oid);
                        rs1=ps.executeQuery();
                        int strcode=0;
                        String strname="";    
                        while(rs1.next())
                        {
                        strcode=rs1.getInt("SUBDIVISION_OFFICE_ID");
                        strname=rs1.getString("SUBDIVISION_OFFICE_NAME");
                        out.println("<option value='"+strcode+"'>"+strname+"</option>");
                        }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in the service group id"+e);
                        }
                        
                        
                        %>
            
            
            </select>
            </div>
              </div>
            </td>
          </tr>
           
           <%}%>
           
           <tr class="table">
            <td>
              <div align="left">
                Office Joined    
                <font color="#ff2121">
                   *
                        </font>
                
              </div>
            </td>
            <td>
              <div align="left">
                <!--input type="text" name="txtOffice_Id" id="txtOffice_Id" maxlength="4" size="6"
                   readonly/-->
                  <!--img src="../../../../../images/c-lovi.gif" onclick="jobpopup()" alt=""></img-->  
                 <input type="text" name="txtOffice_Id" id="txtOffice_Id" maxlength="4" size="6"
                 onchange="loadOffice(document.frmEmployee.txtOffice_Id.value);" onkeypress="return  numbersonly1(event,this)"
                 onfocus="return toFocus(); return checkEID()" readonly/>
                 
                 <!--img src="../../../../../images/c-lovi.gif" onclick="jobpopup()" alt="" id="off_img" name="off_img"></img-->  
               
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">Office Name </div>
            </td>
            <td>
            <div align="left">
              <input type="text" name="txtOfficeName" id="txtOfficeName" size="45" maxlength="60" readonly style="background-color: #ececec"/>
              <input type="hidden" name="dept_id" id="dept_id"/>
            </div>
             </td>
          </tr>

          
          <tr class="table">
            <td>
              <div align="left">
                Designation<font color="#ff2121">
                   *
                        </font>
                      
              </div>
            </td>
            
                                               
                                                <td colspan="2">
                                                      <table cellspacing="2"
                                                             cellpadding="3"
                                                             border="0"
                                                             width="100%">
                                                            <tr>
                                                                  <td>Service
                                                                      Group</td>
                                                                  <td>
                                                                        <div id="divdes"
                                                                             style="visibility:hidden">Designation</div>
                                                                  </td>
                                                            </tr>
                                                            <tr>
                                                                  <td>
                                                                        <select name="cmbsgroup"
                                                                                id="cmbsgroup"
                                                                                onchange="getDesignation1()" onFocus="return toFocus();">
                                                                              <option value="0">Select
                                                                                                Service
                                                                                                Group</option>
     <%
           ResultSet rs1=null;
           try
           {
           ps=con.prepareStatement("select SERVICE_GROUP_ID,SERVICE_GROUP_NAME from HRM_MST_SERVICE_GROUP  order by SERVICE_GROUP_NAME");
            rs1=ps.executeQuery();
              int strcode=0;
            String strname="";    
            while(rs1.next())
            {
                strcode=rs1.getInt("SERVICE_GROUP_ID");
                strname=rs1.getString("SERVICE_GROUP_NAME");
                out.println("<option value='"+strcode+"'>"+strname+"</option>");
             }
          }
          catch(Exception e)
          {
            System.out.println("Exception in the service group id"+e);
          }
           
                
        %>
                                                                        </select>
                                                                  </td>
                                                                  <td>
                                                                        <select name="cmbdes"
                                                                                id="cmbdes" onchange="postcount()"
                                                                                style="visibility:hidden" onFocus="return toFocus();"></select>
                                                                  </td>
                                                            </tr>
                                                      </table>
                                                </td>
                                          


       <tr class="table">
            <td>
            <div align="left">
              Grade
              </div>                           
            </td>
            
            <td>
            <div align="left">
            <input type="radio" checked="CHECKED" value="Normal" name="Office_Grade" ></input>
              Normal&nbsp;
            <input type="radio" value="Selection" name="Office_Grade" ></input>
              Selection&nbsp;
            <input type="radio" value="Special" name="Office_Grade" ></input>
              Special
            <input type="radio" value="Super Grade" name="Office_Grade" ></input>
              Super Grade
            </div>
              </td>
             </tr>

             
          </tr>
          <tr class="table">
            <td>
              <div align="left">
                Post Counted Towards<font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  <font color="#ff2121">
                    <font color="#ff2121">
                      *
                    </font>
                  </font>
                </font>
              </font>
            </font>
          </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="comPostTow" id="comPostTow" onFocus="return toFocus();">
                <option>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- - - - Select - - - -
                </option>
                <%
               /* try
                {
                ps=con.prepareStatement("select POST_RANK_ID,POST_RANK_NAME from HRM_MST_POST_RANKS");
                rs=ps.executeQuery();
                while(rs.next())
                {
                int pos=rs.getInt("POST_RANK_ID");
                out.println("<option value="+pos+">"+rs.getString("POST_RANK_NAME")+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"&nbsp;"+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in post rank combo..."+e);
                }*/
                                 try
                {
                ps=con.prepareStatement("select DESIGNATION_id,Designation from HRM_MST_DESIGNATIONS order by Designation");
                rs=ps.executeQuery();
                while(rs.next())
                {
                int des=rs.getInt("DESIGNATION_id");
                out.println("<option value="+des+">"+rs.getString("DESIGNATION")+"</option>");
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in desig combo..."+e);
                }

               
                %>
                </select>
              </div>
            </td>
          </tr>
          
          
          
           <%if(olevelid!=null && olevelid.equalsIgnoreCase("HO") ){%>
          <tr class="table" id="wing" >
                          <td align="left">
                            Wing
                          </td>
                          <td align="left">
                            <select name="cmbWing">
                            <option value="0">Select Wing</option>
                            
                             <%
                        rs1=null;
                        try
                        {
                        ps=con.prepareStatement("select OFFICE_WING_SINO,WING_NAME from COM_OFFICE_WINGS where OFFICE_ID=?");
                        ps.setInt(1,oid);
                        rs1=ps.executeQuery();
                        int strcode=0;
                        String strname="";    
                        while(rs1.next())
                        {
                        strcode=rs1.getInt("OFFICE_WING_SINO");
                        strname=rs1.getString("WING_NAME");
                        out.println("<option value='"+strcode+"'>"+strname+"</option>");
                        }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in the service group id"+e);
                        }
                        
                        
                        %>
                            
                            </select>
                          </td>
                 </tr>
            <%}%>
          
          
          
          <tr class="table">
            <td>
              <div align="left">
                Remarks
              </div>
            </td>
            <td>
              <div align="left">
                <textarea name="txtRemarks" id="txtRemarks" cols="34" rows="2" onFocus="return toFocus();" onkeypress="return noEnter(event)"></textarea>
              </div>
            </td>
          </tr>
          
          <tr class="table">
            <td>
              <div align="left">
                Process Flow Status Id
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="comProcFlowId" id="comProcFlowId" onFocus="return toFocus();" disabled="disabled">
                <option>
                &nbsp;&nbsp;&nbsp;- - - - Select - - - -
                </option>
                <%
                try
                {
                pss=con.prepareStatement("select * from COM_MST_PROCESS_FLOW");
                rss=pss.executeQuery();
                while(rss.next())
                {
                
                out.println("<option value="+rss.getString("PROCESS_FLOW_STATUS_ID")+">"+rss.getString("PROCESS_FLOW_STATUS_DESC")+"</option>");
               
                }
                }
                catch(Exception e)
                {
                System.out.println("Exception in process flow status id..."+e);
                }
                %>
                </select>
              </div>
            </td>
          </tr>
             <tr class="tdH">
            <td colspan="2">
             
                 Pay Bill Group Updation   
              
            </td>
          </tr>
         
         
          <tr  class="table">
	<td align="left" >Pay Bill group Id</td> 
	<td align="left" ><select size="1" name="pay_group_id" id="pay_group_id" onchange="getsubgroup_new(0)" >
                      
                 
                      <%
                   
                  
              
                
                    try{
                       if(oid==5000)
                       {
                       
                       
                        String sql="SELECT a.pay_bill_group_id, " 
									+"  pay_bill_group_desc " 
									+"FROM " 
									+"  (SELECT Pay_Bill_Group_Id, " 
									+"    Office_Id, " 
									+"    User_Id " 
									+"  FROM Hrm_Pay_User_Auth_Mst " 
									+"  WHERE User_Id=? " 
									+"  AND Office_Id=? " 
									+"  )a " 
									+"LEFT OUTER JOIN " 
									+"  (SELECT PAY_BILL_GROUP_ID, " 
									+"    PAY_BILL_GROUP_DESC " 
									+"  FROM HRM_PAY_BILL_GROUP_MST " 
									+"  WHERE OFFICE_ID       =? " 
									
									+"  )b " 
									+"ON a. pay_bill_group_id=b.pay_bill_group_id";
                         
                            ps=con.prepareStatement(sql);
                            ps.setString(1,user_id);
                            ps.setInt(2,oid);
	                        ps.setInt(3,oid);
	                      
	                        rs=ps.executeQuery();
                         while(rs.next())
                        {
                            
                              out.println("<option value="+rs.getString("PAY_BILL_GROUP_ID")+">"+rs.getString("PAY_BILL_GROUP_DESC")+"</option>");                           
                            
                        }
                       
                       }
                      else
                      {
                    
                    
                    
                    
                        ps=con.prepareStatement("select PAY_BILL_GROUP_ID,PAY_BILL_GROUP_DESC from HRM_PAY_BILL_GROUP_MST where OFFICE_ID=? ");
                        ps.setInt(1,oid);
                     //   ps.setInt(2,unitid1);
                        rs=ps.executeQuery();
                        //out.println("<option value="+oid+">"+oname+"</option>");
                       
                            // used to load the bank details for the first office of combo box
                        while(rs.next())
                        {
                            System.out.println(rs.getString("PAY_BILL_GROUP_ID")+"PayGroup");
                              out.println("<option value="+rs.getString("PAY_BILL_GROUP_ID")+">"+rs.getString("PAY_BILL_GROUP_DESC")+"</option>");                           
                            
                        }
                   } } 
                catch(Exception e)
                {
                System.out.println("Exception in Office combo..."+e);
                }
                finally
                {
                	 ps.close();
                     rs.close();
                }  
                
                %>
                    </select></td>
</tr>
<tr  class="table">
	<td align="left" >Pay Bill Sub group Id</td> 
	<td align="left" ><select size="1" name="pay_subgroup_id" id="pay_subgroup_id" ">
	
  <option value="">---Select Paybill Subgroup--</option>
                    </select></td>
</tr>

          
      
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="Validate" onclick="callServer1('Update','null')"/>
                 &nbsp;&nbsp;&nbsp;
                <!-- <input type="BUTTON" name="cmdDelete" value="    Delete    " id="cmdDelete" onclick="callServer1('Delete','null')"/>-->
                <input type="button" name="butCan" id="butCan" onclick="self.close();" value="CANCEL"/>
                <input type="button" name="butclear" id="butclear" onclick="clearAll()" value="CLEARALL"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>