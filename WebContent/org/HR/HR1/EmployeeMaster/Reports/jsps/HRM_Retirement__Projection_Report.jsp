<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Retirement Projection - Report</title>
 
  <meta http-equiv="Pragma" content="no-cache">
    
    <!-- MICROSOFT BROWSERS REQUIRE THIS ADDITIONAL META TAG AS WELL -->
    <meta http-equiv="Expires" content="-1">

    <meta name="archive" content="false">
    <meta http-equiv="imagetoolbar" content="no">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     
    <script type="text/javascript" src="../scripts/comJS.js"></script>
    <script type="text/javascript"       src="../scripts/checkDate.js"></script>
  
    <script type="text/javascript" src="../scripts/NominAjaxOfficeContactId.js"></script>
    <!-- <script type="text/javascript" src="../scripts/controllingOfficeContact.js"></script>-->
    <script type="text/javascript"     src="../scripts/Seniority_Proceeding_master_updation.js"></script>
   
    <style> 



  
</style>
   <script type="text/javascript">
   function Redirect()
   {
   window.history.go(-1);
   }
   </script>
    <%
     HttpSession session=request.getSession(false);
     
     %>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"         media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"         media="screen"/>
    
      <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
  </head>
  <body id="bodyid"  >
  <form name="Retirement_projection_report" >
      <p>
        <%
   int cyear1=0;
   int cyear2=0;
   int cyear3=0;
   int cyear4=0;
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
  try
  {
  
	  LoadDriver driver=new LoadDriver();
  	connection=driver.getConnection();
              
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
   String emp_sts_id="";
   String emplo_desc="";
   emp_sts_id=request.getParameter("employee_status_id");
   emplo_desc=request.getParameter("emplo_desc");
    Calendar c = new GregorianCalendar();
int fyr=c.get(Calendar.YEAR);
int fmnt=c.get(Calendar.MONTH)+1;
int fin1=0,fin2=0;

if(fmnt>3)
{
    fin2=fyr+1;
    fin1=fyr;
     cyear1=fin1+1;
    cyear2=cyear1+1;
    cyear3=cyear2+1;
    cyear4=cyear3+1;
    fyr=fyr;
    }
    else
    {
    fin2=fyr;
    fin1=fyr-1;
    cyear1=fyr+1;
    cyear2=cyear1+1;
    cyear3=cyear2+1;
    cyear4=cyear3+1;
    fyr=fyr;
   /* System.out.println("finyr-------------------->"+fyr);
    System.out.println("finyr-------------------->"+cyear1);
System.out.println("fin1-------------------->"+cyear2);
System.out.println("fin2-------------------->"+cyear3);
System.out.println("fin2-------------------->"+cyear4);*/
    }
String finyr=fin1+"-"+fin2;

   /*int cyear1=fin1+1;
   int cyear2=fin1+2;
   int cyear3=fin1+3;
   int cyear4=fin1+4;*/
   
  %>
     
      <div align="center">
        <table width="80%">
          <tr>
            <td>
              <table cellspacing="2" cellpadding="3" border="1" width="100%">
                <tr class="tdH" >
                  <th align="center" class="tdH"  colspan="11" >
                    Retirement Projection- Report</th>
                </tr>
                <!-- OFFICE DETAILS -->
                <% 
         session=request.getSession(false);
      UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
  // int empid=11263;
    int  oid=0;
    String oname="",oadd1="",oadd2="",ocity="",olid="",owid="",odist="";
    String olname=""; 
    String ownature="",deptid="",type="";
    String fulladd="";
    
    try
    {
           
            ps=connection.prepareStatement("select OFFICE_ID,DEPARTMENT_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps.setInt(1,empid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                    deptid=results.getString("DEPARTMENT_ID");
                 
                 }
            results.close();
            ps.close();
            System.out.println("office id:"+oid);
            System.out.println("dept id:"+deptid);
            if( deptid==null || deptid.equalsIgnoreCase("TWAD"))
            {
                    type="(TWAD)";
                    //ps=connection.prepareStatement("select OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME,OFFICE_LEVEL_ID,PRIMARY_WORK_ID from COM_MST_OFFICES where OFFICE_ID=?" );
                    ps=connection.prepareStatement("select a.OFFICE_NAME,a.OFFICE_ADDRESS1,a.OFFICE_ADDRESS2,a.CITY_TOWN_NAME,a.OFFICE_LEVEL_ID,a.PRIMARY_WORK_ID,d.DISTRICT_NAME  from COM_MST_OFFICES a left outer join com_mst_districts d on d.DISTRICT_CODE=a.DISTRICT_CODE  where a.OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OFFICE_NAME");
                            oadd1=results.getString("OFFICE_ADDRESS1");
                            if(oadd1==null)oadd1="";
                            oadd2=results.getString("OFFICE_ADDRESS2");
                            if(oadd2==null)oadd2="";
                            ocity=results.getString("CITY_TOWN_NAME");
                            if(ocity==null)ocity="";
                            olid=results.getString("OFFICE_LEVEL_ID");
                            if(olid==null)olid="";
                            owid=results.getString("PRIMARY_WORK_ID");
                            odist =results.getString("DISTRICT_NAME");
                            if(odist==null)odist="";
                            
                            if(oadd1.length()>0)
                                fulladd=oadd1;
                            if(oadd2.length()>0)
                                fulladd+="\n"+oadd2;
                            if(ocity.length()>0)
                                fulladd+="\n"+ocity;
                            if(odist.length()>0)
                                fulladd+="\n"+odist;
                            
                            System.out.println("Full address:"+fulladd);
                            
                            
                          }
                          //System.out.println("office name:"+oname);
                    results.close();
                    ps.close();
                  
                    ps=connection.prepareStatement("select OFFICE_LEVEL_NAME from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID=?" );
                    ps.setString(1,olid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            olname=results.getString("OFFICE_LEVEL_NAME");
                            
                          }
                    results.close();
                    ps.close();
                    
                    ps=connection.prepareStatement("select WORK_NATURE_DESC from COM_MST_WORK_NATURE where WORK_NATURE_ID=?" );
                    ps.setString(1,owid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            ownature=results.getString("WORK_NATURE_DESC");
                            
                          }
                    results.close();
                    ps.close();
            }
            else
            {
                System.out.println("other");
                System.out.println("off id::"+oid);
                    type="(OTHER="+deptid+")";
                    String otherdeptid="";
                    ps=connection.prepareStatement("select OTHER_DEPT_ID,OTHER_DEPT_OFFICE_NAME,ADDRESS1,ADDRESS2,CITY_TOWN from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            oname=results.getString("OTHER_DEPT_OFFICE_NAME");
                            System.out.println("oname::"+oname);
                            oadd1=results.getString("ADDRESS1");
                            if(oadd1==null)oadd1="";
                            oadd2=results.getString("ADDRESS2");
                            if(oadd2==null)oadd2="";
                            ocity=results.getString("CITY_TOWN");
                             if(ocity==null)ocity="";
                            otherdeptid=results.getString("OTHER_DEPT_ID");
                           // owid=results.getString("PRIMARY_WORK_ID");
                            if(oadd1.length()>0)
                                fulladd=oadd1;
                            if(oadd2.length()>0)
                                fulladd+="\n"+oadd2;
                            if(ocity.length()>0)
                                fulladd+="\n"+ocity;
                          }
                    results.close();
                    ps.close();
                  
                    ps=connection.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?" );
                    ps.setString(1,otherdeptid);
                    results=ps.executeQuery();
                         if(results.next()) 
                         {
                            olname=results.getString("OTHER_DEPT_NAME");
                            
                          }
                    results.close();
                    ps.close();
                    
                  
            }
            
     /* */      
                 
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
   
   %>
               <tr class="tdH"  align="center">
               <td>Sl.No</td>
               <td>Rank</td>
               <td>Sanction strength</td>
               <td>Total employees</td>
               <td><%=fyr %></td>
               <td><%=cyear1 %></td>
               <td><%=cyear2 %></td>
               <td><%=cyear3 %></td>
               <td><%=cyear4 %></td>
               <td>Total Retirement</td>
               <td>Vacancy After 5 years</td>
               
               </tr>
               <%
               
              
               
               
										  String sql="SELECT A1.POST_RANK_ID, " +
"  DECODE(POST_RANK_NAME,NULL, ' ',POST_RANK_NAME)                                                                                                          AS POST_RANK_NAME, " +
"  DECODE(SANCTIONED_NO_OF_POSTS,NULL,' ',SANCTIONED_NO_OF_POSTS)                                                                                           AS SANCTIONED_NO_OF_POSTS, " +
"  DECODE(FILLUP,NULL,' ',FILLUP)                                                                                                                           AS FILLUP, " +
"  DECODE(YEAR1_RETIRE,NULL, ' ',YEAR1_RETIRE)                                                                                                              AS CUR_YEAR, " +
"  DECODE(YEAR2_RETIRE,NULL, ' ',YEAR2_RETIRE)                                                                                                              AS CUR_YEAR1, " +
"  DECODE(YEAR3_RETIRE,NULL, ' ',YEAR3_RETIRE)                                                                                                              AS CUR_YEAR2, " +
"  DECODE(YEAR4_RETIRE,NULL, ' ',YEAR4_RETIRE)                                                                                                              AS CUR_YEAR3, " +
"  DECODE(YEAR5_RETIRE,NULL, ' ',YEAR5_RETIRE)                                                                                                              AS CUR_YEAR4, " +
"  NVL(FILLUP                      -(YEAR1_RETIRE+YEAR2_RETIRE+YEAR3_RETIRE+YEAR4_RETIRE+YEAR5_RETIRE),0)                                                   AS PAR_VAC, " +
"  ( (NVL(SANCTIONED_NO_OF_POSTS,0)-(NVL(FILLUP,0))+(NVL(YEAR1_RETIRE,0)+NVL(YEAR2_RETIRE,0)+NVL(YEAR3_RETIRE,0)+NVL(YEAR4_RETIRE,0)+NVL(YEAR5_RETIRE,0)))) AS TOT_VAC, " +
"  (NVL(YEAR1_RETIRE,0)            +NVL(YEAR2_RETIRE,0)+NVL(YEAR3_RETIRE,0)+NVL(YEAR4_RETIRE,0)+NVL(YEAR5_RETIRE,0))                                        AS tot_ret, " +
"  ORDERING_SEQ_NO " +
"FROM " +
"  ( SELECT post_rank_id ,post_rank_name,ORDERING_SEQ_NO FROM hrm_mst_post_ranks " +
"  )a1 " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    SUM(SANCTIONED_NO_OF_POSTS) AS SANCTIONED_NO_OF_POSTS " +
"  FROM HRM_SS_SANCTION_DETAILS " +
"  WHERE FIN_YEAR ='"+finyr+"' " +
"  AND OFFICE_ID IN " +
"    (SELECT OFFICE_ID FROM COM_MST_OFFICES WHERE OFFICE_STATUS_ID='CR' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  )A " +
"ON a1.post_rank_id=a.post_rank_id " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    COUNT(EMPLOYEE_ID) AS fillup " +
"  FROM HRM_EMP_CUR_POST_CADRE_VIEW " +
"  WHERE employee_status_id NOT IN ('VRS','DTH','MEV','RES','SAN','CMR','DIS') " +
"  AND EMPLOYEE_ID              IN " +
"    (SELECT EMPLOYEE_ID FROM ALLRETIREMENTVIEW WHERE RETIREDATE>SYSDATE " +
"    ) " +
"  AND employee_id IN " +
"    (SELECT employee_id FROM hrm_mst_employees WHERE is_consolidate='N' " +
"    ) " +
"  GROUP BY POST_RANK_ID " +
"  UNION " +
"  SELECT post_rank_id, " +
"    COUNT(*) AS fillup " +
"  FROM HRM_MST_DEPUTN_EMPLOYEES " +
"  WHERE process_flow_status_id='FR' " +
"  GROUP BY post_rank_id " +
"  )B " +
"ON A1.POST_RANK_ID=B.POST_RANK_ID " +
"LEFT OUTER JOIN " +
"  (SELECT COUNT( *) AS YEAR1_RETIRE, " +
"    POST_RANK_ID    AS RANK_ID1 " +
"  FROM ALLRETIREMENTVIEW " +
"  WHERE RETYEAR                      =EXTRACT(YEAR FROM SYSDATE) " +
"  AND (extract(MONTH FROM RETIREDATE)>EXTRACT(MONTH FROM SYSDATE) " +
"  OR (extract(DAY FROM RETIREDATE)   >EXTRACT(DAY FROM SYSDATE) " +
"  AND extract(MONTH FROM RETIREDATE) =EXTRACT(MONTH FROM SYSDATE)) ) " +
"  GROUP BY POST_RANK_ID " +
"  )C1 " +
"ON A1.POST_RANK_ID=C1.rank_id1 " +
"LEFT OUTER JOIN " +
"  (SELECT COUNT(*) AS YEAR2_RETIRE, " +
"    POST_RANK_ID   AS RANK_ID2 " +
"  FROM ALLRETIREMENTVIEW " +
"  WHERE RETYEAR=EXTRACT(YEAR FROM SYSDATE)+1 " +
"  GROUP BY POST_RANK_ID " +
"  )C2 " +
"ON A1.POST_RANK_ID=C2.RANK_ID2 " +
"LEFT OUTER JOIN " +
"  (SELECT COUNT(*) AS YEAR3_RETIRE, " +
"    POST_RANK_ID   AS RANK_ID3 " +
"  FROM ALLRETIREMENTVIEW " +
"  WHERE RETYEAR=EXTRACT(YEAR FROM SYSDATE)+2 " +
"  GROUP BY POST_RANK_ID " +
"  )C3 " +
"ON A1.POST_RANK_ID=C3.RANK_ID3 " +
"LEFT OUTER JOIN " +
"  (SELECT COUNT(*) AS YEAR4_RETIRE, " +
"    POST_RANK_ID   AS RANK_ID4 " +
"  FROM ALLRETIREMENTVIEW " +
"  WHERE RETYEAR=EXTRACT(YEAR FROM SYSDATE)+3 " +
"  GROUP BY POST_RANK_ID " +
"  )C4 " +
"ON A1.POST_RANK_ID=C4.rank_id4 " +
"LEFT OUTER JOIN " +
"  (SELECT COUNT(*) AS YEAR5_RETIRE, " +
"    POST_RANK_ID   AS RANK_ID5 " +
"  FROM ALLRETIREMENTVIEW " +
"  WHERE RETYEAR=EXTRACT(YEAR FROM SYSDATE)+4 " +
"  GROUP BY POST_RANK_ID " +
"  )C5 " +
"ON A.POST_RANK_ID=C5.RANK_ID5 " +
"LEFT OUTER JOIN " +
"  (SELECT POST_RANK_ID, " +
"    POST_RANK_NAME, " +
"    ORDERING_SEQ_NO " +
"  FROM HRM_MST_POST_RANKS " +
"  )S " +
"ON S.POST_RANK_ID              =A1.POST_RANK_ID " +
"WHERE (SANCTIONED_NO_OF_POSTS IS NOT NULL " +
"OR fillup                     IS NOT NULL) " +
"ORDER BY ORDERING_SEQ_NO";

System.out.println("sql is===>"+sql);
					ps=connection.prepareStatement(sql);
					results=ps.executeQuery();
					int i=0;
					int tot_sanc=0;
					int tot_filled=0;
					int tot_first=0;
					int tot_second=0;
					int tot_third=0;
					int tot_fourth=0;
					int tot_fivth=0;
					int tot_retire=0;
					int tot_after_five=0;
					while(results.next())
					{
					String p_name=results.getString("POST_RANK_NAME");
					String sanc=results.getString("SANCTIONED_NO_OF_POSTS");
					String filled=results.getString("FILLUP");
					String fill=filled;
					if(filled.equalsIgnoreCase(" "))
					{
					fill="0";
					}
					
					String CUR_YEAR=results.getString("CUR_YEAR");
					String first_fill=CUR_YEAR;
					if(CUR_YEAR.equalsIgnoreCase(" "))
					{
					first_fill="0";
					}
					String CUR_YEAR1=results.getString("CUR_YEAR1");
					String second_fill=CUR_YEAR1;
					if(CUR_YEAR1.equalsIgnoreCase(" "))
					{
					second_fill="0";
					}
					String CUR_YEAR2=results.getString("CUR_YEAR2");
					String third_fill=CUR_YEAR2;
					if(CUR_YEAR2.equalsIgnoreCase(" "))
					{
					third_fill="0";
					}
					String CUR_YEAR3=results.getString("CUR_YEAR3");
					String fourth_fill=CUR_YEAR3;
					if(CUR_YEAR3.equalsIgnoreCase(" "))
					{
					fourth_fill="0";
					}
					String CUR_YEAR4=results.getString("CUR_YEAR4");
					String fivth_fill=CUR_YEAR4;
					if(CUR_YEAR4.equalsIgnoreCase(" "))
					{
					fivth_fill="0";
					}
					String tot_ret=results.getString("tot_ret");
					String first_retire=tot_ret;
					if(tot_ret.equalsIgnoreCase(" "))
					{
					first_retire="0";
					}
					String tot_vac=results.getString("tot_vac");
					String tot_vac_after=tot_vac;
					if(tot_vac_after.equalsIgnoreCase(" "))
					{
					tot_vac_after="0";
					}
					int sancs=0;
					if(sanc.equalsIgnoreCase(" "))
					{
					sancs=0;
					}
					else
					sancs=Integer.parseInt(sanc);
					
					tot_sanc=sancs+tot_sanc;
					tot_filled=Integer.parseInt(fill)+tot_filled;
					tot_first=Integer.parseInt(first_fill)+tot_first;
					tot_second=Integer.parseInt(second_fill)+tot_second;
					tot_third=Integer.parseInt(third_fill)+tot_third;
					tot_fourth=Integer.parseInt(fourth_fill)+tot_fourth;
					tot_fivth=Integer.parseInt(fivth_fill)+tot_fivth;
					tot_retire=Integer.parseInt(first_retire)+tot_retire;
					tot_after_five=Integer.parseInt(tot_vac_after)+tot_after_five;
					i++;
					if((i % 2) ==0 )
					{
					
                          out.print("<tr bgcolor='#C2DFFF'><td>"+i+"</td><td>"+p_name+"</td><td>"+sanc+"</td><td><a href='HRM_Retire_emp_details.jsp?post_rank_id="+results.getInt("post_rank_id")+"'>"+filled+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=first&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=second&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR1+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=third&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR2+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=fourth&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR3+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=fifth&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR4+"</a></td><td>"+tot_ret+"</td><td>"+tot_vac+"</td></tr>");
					}
					else
					{
					
                          out.print("<tr ><td>"+i+"</td><td>"+p_name+"</td><td>"+sanc+"</td><td><a href='HRM_Retire_emp_details.jsp?post_rank_id="+results.getInt("post_rank_id")+"'>"+filled+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=first&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=second&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR1+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=third&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR2+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=fourth&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR3+"</a></td><td><a href='HRM_Retire_emp_cur_year_details.jsp?curr=fifth&post_rank_id="+results.getInt("post_rank_id")+"'>"+CUR_YEAR4+"</a></td><td>"+tot_ret+"</td><td>"+tot_vac+"</td></tr>");
					}
					}
					String post_name="";
					String ss="SELECT DECODE(POST_RANK_NAME,NULL,' ',POST_RANK_NAME) as POST_RANK_NAME,sub.POST_RANK_ID " +
						"FROM " +
						"  (SELECT POST_RANK_ID FROM HRM_MST_DEPUTN_EMPLOYEES where process_flow_status_id='FR'    " +
						"  )MST " +
						"LEFT OUTER JOIN " +
						"  (SELECT POST_RANK_ID,POST_RANK_NAME FROM HRM_MST_POST_RANKS " +
						"  )SUB " +
						"ON sub.POST_RANK_ID=mst.POST_RANK_ID order by POST_RANK_ID ";
							ps=connection.prepareStatement(ss);
							results=ps.executeQuery();
							while(results.next())
							{
							post_name=post_name+results.getString("POST_RANK_NAME")+" ,";
							}
					
					post_name=post_name.substring(0,post_name.length()-2);
					out.print("<tr ><td colspan='2' align='right'>Total</td><td>"+tot_sanc+"</td><td>"+tot_filled+"</td><td>"+tot_first+"</td><td>"+tot_second+"</td><td>"+tot_third+"</td><td>"+tot_fourth+"</td><td>"+tot_fivth+"</td><td>"+tot_retire+"</td><td>"+tot_after_five+"</td></tr>");
                    out.print("<tr><td colspan='11'><font color='brown'><strong>In total employees, the employees in deputed from TWAD also included. However the same in not considered in sancation strength</td></tr>");
                   out.print("<tr><td colspan='11'><font color='brown'><strong>Employee Deputed From Other Offices : <font color='green'>"+post_name+"</td></tr>");
                %>
      </table>
      <tr class="tdH" align="center">
      <td>
      
      <input type="button" name="exit" id="exit" value="Exit" onclick="self.close()" >
      </td>
      </tr>
    </form></body>
</html>