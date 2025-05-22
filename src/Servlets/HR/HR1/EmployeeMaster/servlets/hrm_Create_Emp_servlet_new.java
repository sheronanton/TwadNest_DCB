package Servlets.HR.HR1.EmployeeMaster.servlets;


import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class hrm_Create_Emp_servlet_new extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection con = null;
        try {

        	LoadDriver driver=new LoadDriver();
            con=driver.getConnection();

        } catch (Exception e) {
            System.out.println("Exception in openeing connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        ResultSet rs = null, rs1 = null, rs2 = null, rs3 = null, rs4 = null;
        CallableStatement cs = null;
        PreparedStatement psnew1 = null;
        ResultSet rsnew1 = null;
        PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null, ps4 =
            null;
        String xml = "";
        
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        PreparedStatement psnew = null;
        ResultSet rsnew = null;

        String strCommand = "";
        Calendar c;
        String ename = "", desig = "";
        String relreasonid = "";
        Calendar c2;
        int eid = 0;
        try {
            eid = Integer.parseInt(request.getParameter("txtEmployeeid"));
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(eid);
        strCommand = request.getParameter("Command");
        System.out.println(strCommand);

        if (strCommand.equalsIgnoreCase("loademp")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int offid = 0;
            
            String rad_DORelieval="",cmbReason="",txtRemarks="";
            int txtOffId=0,txtEmployeeid=0,txtRel_SLNO=0;
            offid = Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>loademp</command>";
            try {

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, eid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else if (up.getEmployeeId() != eid) {
                    int OfficeId = 0;
                    
                    int OfficeId_ctrl=0;
                    String cntrl_Office_name="";
                    String desg_short_name="";
                    String desg_var="";
                    String type_rel="";
                    int noaccunit=0;
                  //  int OfficeIds=0;
                    String sql =
                        "select accounting_unit_id from fas_mst_acct_units where accounting_unit_office_id in (select office_id from hrm_emp_current_posting where employee_id=?)";
                    
                    
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, eid);
                  //  ps.setInt(2, eid);
                    rs = ps.executeQuery();
                    if(!rs.next()){
                    	noaccunit=1;
                    	System.out.println("inside"+noaccunit);
                    }
                    System.out.println(noaccunit);
                     sql =
                        "select ACCOUNTING_FOR_OFFICE_ID from HRM_PAY_BILL_GROUP_EMP_LINK where EMPLOYEE_ID=?";
                    
                    
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, eid);
                  //  ps.setInt(2, eid);
                    rs = ps.executeQuery();
                  //  System.out.println("sql control office id"+sql); 
                    if (!rs.next()) {
                        sql="select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, eid);
                        rs = ps.executeQuery();
                      
                        if(rs.next())
                        {
                        	OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
                        	type_rel="SR";
                        	  System.out.println("sql CONTROLLING_OFFICE_ID ......"+sql+"type_rel........"+type_rel);
                        }
                    }
                    else
                    {
                    	
                    	System.out.println("sql ACCOUNTING_FOR_OFFICE_ID ......"+sql); 
                    	OfficeId = rs.getInt("ACCOUNTING_FOR_OFFICE_ID");
                    	type_rel="PB";
                    	System.out.println("sql for ACCOUNTING_FOR_OFFICE_ID....."+sql+"type_rel........"+type_rel);
                    	System.out.println("----->"+OfficeId);
                    }
                    
                    if (OfficeId != 0 )
                    {
                    	String sqll =
                            "select office_id, office_name from com_mst_offices  where office_id=?";
                        ps = con.prepareStatement(sqll);
                        ps.setInt(1, OfficeId);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            cntrl_Office_name = rs.getString("office_name");
                           
                        }
                    }

                    if (OfficeId != 0 ) {
                        sql ="select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, up.getEmployeeId());
                        rs = ps.executeQuery();
                    //    System.out.println("sql HRM_EMP_CURRENT_POSTING office id"+sql);          
                        
                        if (rs.next()) {
                            int offid1 = rs.getInt("OFFICE_ID");
                           System.out.println("getting office id is"+offid1);
                           
                 String   sqlcl = "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
                        	                          ps = con.prepareStatement(sqlcl);
                        	                          ps.setInt(1, eid);
                        	                          rs = ps.executeQuery(); 
                        	                          
                        	                          if(rs.next())
                        	                          {
                        	                        	  OfficeId_ctrl = rs.getInt("CONTROLLING_OFFICE_ID");
                        	                          //	type_rel="SR";
                        	                          	  System.out.println("sql CONTROLLING_OFFICE_ID .for SE ....."+sqlcl+"OfficeId_ctrl........"+OfficeId_ctrl);
                        	                          }
                        	                          
                        	                          String   sqldseg = "SELECT designation_short_name " +
                        	                          "FROM " +
                        	                          "  (SELECT EMPLOYEE_ID, " +
                        	                          "    designation_id " +
                        	                          "  FROM hrm_emp_current_posting " +
                        	                          "  WHERE employee_id=? " +
                        	                          "  )a " +
                        	                          "LEFT OUTER JOIN " +
                        	                          "  (SELECT designation_id, " +
                        	                          "    designation, " +
                        	                          "    designation_short_name " +
                        	                          "  FROM hrm_mst_designations " +
                        	                          "  )B " +
                        	                          "ON a.designation_id=b.designation_id";
                        	                          ps = con.prepareStatement(sqldseg);
                        	                          ps.setInt(1, eid);
                        	                          rs = ps.executeQuery(); 
                        	                          
                        	                          if(rs.next())
                        	                          {
                        	                        	  desg_short_name = rs.getString("designation_short_name");
                        	                          //	type_rel="SR";
                        	                          	  System.out.println("sql  .for desg_short_name ....desg_short_name........"+desg_short_name);
                        	                          }
                        	                          
                        	                          if((desg_short_name.equals("DSE")) || (desg_short_name.equals("SE")) || (desg_short_name.equals("CE")) ||(desg_short_name.equals("EE")))
                        	                          {
                        	                        	  desg_var="Y";
                        	                          }
                        	                          else
                        	                          {
                        	                        	  desg_var="N";
                        	                          }
                        	                          
                                                      System.out.println("outside  If"+desg_var);
                        	                          if ((offid1 != OfficeId)  )  {
                        	                        	  System.out.println("offid1 "+offid1+"OfficeId"+OfficeId+"-"+OfficeId_ctrl+"noaccunit"+noaccunit);
                        	                        	  if ((offid1 == OfficeId_ctrl))
                        	                        	  {
                        	                        		System.out.println("same");  
                        	                        		 if (desg_var.equals("N")&&(noaccunit==0))
                        	                        		 {
                        	                        			 System.out.println("inside  If"+desg_var);
                               	                        	  
                               	                        	  xml = xml + "<flag>failurea</flag><cntrl_Office_name>"+cntrl_Office_name+"</cntrl_Office_name><cntrl_Office_id>"+OfficeId+"</cntrl_Office_id><type_rel>"+type_rel+"</type_rel>";
                                                          flag = false;
                        	                        		 }
                        	                        		 
                        	                          }
                        	                        	  
                        	                        	  else
                        	                        	  {
                        	                        		  xml = xml + "<flag>failurea</flag><cntrl_Office_name>"+cntrl_Office_name+"</cntrl_Office_name><cntrl_Office_id>"+OfficeId+"</cntrl_Office_id><type_rel>"+type_rel+"</type_rel>";
                                                              flag = false;
                        	                        	  }
                        	                        	 
                              
                            }
                        } else {
                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
                            xml = xml + "<flag>failureb</flag>";
                            flag = false;
                        }

                    } else {
                    	

                    
                        xml = xml + "<flag>failurec</flag>";
                        flag = false;
                  
                    }
                } else {

                    // xml=xml+"<flag>failured</flag>";
                    // flag=false;
                }

                if (flag) {
                    int log_offid = 0;
String sql="select office_id from hrm_emp_current_posting where employee_id=?";
                    ps4 =con.prepareStatement(sql);
                    ps4.setInt(1, up.getEmployeeId());
                    rs4 = ps4.executeQuery();
                 //   System.out.println("sql hrm_emp_current_posting office id"+sql);          
                    if (rs4.next()) {
                        log_offid = rs4.getInt("office_id");
                        System.out.println("login user office id..." +
                                           log_offid);
                    }

sql="select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ";
                    ps =con.prepareStatement(sql);
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();
                  //  System.out.println("sql EMPLOYEE_STATUS_ID office id"+sql);          
                    
                    if (!rs.next()) {
                        xml = xml + "<flag>failure1</flag>";
                    } else {
                        System.out.println("inside employee status id");

                        if (rs.getString("EMPLOYEE_STATUS_ID") !=
                            null) // && rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG"))
                        {
                            System.out.println("login office_id..." +
                                               log_offid);
sql="select EMPLOYEE_ID from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=? and office_id=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')";
                            ps =con.prepareStatement(sql);
                            ps.setInt(1, eid);
                            ps.setInt(2, log_offid);
                            rs = ps.executeQuery();
                        //    System.out.println("sql HRM_EMP_RELIEVAL_DETAILS office id"+sql);  
                            if (rs.next()) {
                            	
                            	//  New changes
                            	
                            	

                                ps=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION,c.DESIGNATION_ID,c.OFFICE_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=?");
                                ps.setInt(1,eid);
                               // ps.setInt(2,offid);
                                 rs=ps.executeQuery();
                               if(rs.next())
                               {
                                xml=xml+"<eid>"+eid+"</eid><ename>"+rs.getString("EMPLOYEE_NAME")+"</ename><desig>"+rs.getString("DESIGNATION")+"</desig><desid>"+rs.getInt("DESIGNATION_ID")+"</desid><offid>"+rs.getInt("OFFICE_ID")+"</offid>";
                                ps.close();
                                rs.close();
                                
                                ///////////////////////////////////////////////////////////////////
                                System.out.println("here is ok relieval");
//                                 try
//                                 {
                                 //ps=con.prepareStatement("select OFFICE_ID,EMPLOYEE_ID,RELIEVAL_SLNO,DATE_OF_RELIEVAL ,RELIEVAL_FN_AN, RELIEVAL_REASON_ID, REMARKS from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and EMPLOYEE_ID=? and RELIEVAL_SLNO=?");
                                 ps=con.prepareStatement("select OFFICE_ID,EMPLOYEE_ID,RELIEVAL_SLNO,DATE_OF_RELIEVAL ,RELIEVAL_FN_AN, RELIEVAL_REASON_ID, REMARKS,PROCESS_FLOW_STATUS_ID,PROCEEDING_NO,ORDER_NO,ORDER_DATE from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                                 ps.setInt(1,offid);
                                 ps.setInt(2,eid); 
                                 //ps.setInt(3,txtRel_SLNO);
                                 rs=ps.executeQuery();
                                 if(rs.next())
                                 {
                                 //xml=xml+"<flag>success</flag>"+"<re_slno>"+rs.getInt("RELIEVAL_SLNO")+"</re_slno><dor>"+rs.getDate("DATE_OF_RELIEVAL")+"</dor><r_noon>"+rs.getString("RELIEVAL_FN_AN")+"</r_noon><r_rid>"+rs.getString("RELIEVAL_REASON_ID")+"</r_rid><remark>"+rs.getString("REMARKS")+"</remark>";
                                  xml=xml+"<re_slno>"+rs.getInt("RELIEVAL_SLNO")+"</re_slno><dor>"+rs.getDate("DATE_OF_RELIEVAL")+"</dor><r_noon>"+rs.getString("RELIEVAL_FN_AN")+"</r_noon><r_rid>"+rs.getString("RELIEVAL_REASON_ID")+"</r_rid><remark>"+rs.getString("REMARKS")+"</remark><pro_status>"+rs.getString("PROCESS_FLOW_STATUS_ID")+"</pro_status>";
                                  xml=xml+"<proceed_no>"+rs.getString("PROCEEDING_NO")+"</proceed_no><ord_no>"+rs.getString("ORDER_NO")+"</ord_no><ord_date>"+rs.getDate("ORDER_DATE")+"</ord_date>";
                                 //  xml=xml+"<re_slno>"+rs.getInt("RELIEVAL_SLNO")+"</re_slno><dor>"+rs.getDate("DATE_OF_RELIEVAL")+"</dor><r_noon>"+rs.getString("RELIEVAL_FN_AN")+"</r_noon><r_rid>"+rs.getString("RELIEVAL_REASON_ID")+"</r_rid><remark>"+(rs.getString("REMARKS")==null?"null":rs.getString("REMARKS"))+"</remark>";
                                  
                                 
                                 txtRel_SLNO=rs.getInt("RELIEVAL_SLNO");
                                 cmbReason=rs.getString("RELIEVAL_REASON_ID");
                                 ps.close();
                                 rs.close();
                                 System.out.println(":::cmbReson:::"+cmbReason);
                                 /*      EFFECTIVE DATE   */
                                 
                                  
                                  
                                   ps=con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
                                                 
                                   ps.setInt(1,eid);
                                                  
                                   rs=ps.executeQuery();
                                   String maxfromdate="";
                                      String maxsession="";
                                   if(rs.next())
                                   {
                                       if(rs.getDate("DATE_EFFECTIVE_FROM")!=null)
                                       {
                                           maxfromdate=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_EFFECTIVE_FROM"));
                                           System.out.println("max from date :"+rs.getDate("DATE_EFFECTIVE_FROM"));
                                       }
                                       else {
                                           
                                           maxfromdate="empty";
                                       }
                                       if(rs.getString("DATE_EFFECTIVE_FROM_SESSION")!=null) {
                                           maxsession=rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                       }
                                       else {
                                           maxsession="FN";
                                       }
                                   }
                                      xml=xml+"<maxfromdate>"+maxfromdate+"</maxfromdate><maxsession>"+maxsession+"</maxsession>";
                                  
                                  /* END OF EFFECTIVE DATE  */
                                 
                                 
                                 
                                     if(cmbReason.equalsIgnoreCase("PRO"))
                                     {
                                     System.out.println("offid....."+offid+"eid....."+eid+"txtRel_SLNO...."+txtRel_SLNO);
                                     
                                     System.out.println("inside promotion");
                                     
                                     ps=con.prepareStatement("select  NEW_DESIGNATION_ID, OFFICE_TO_POSTED_ID,REPOSTING_REQD, PROMOTION_PROCEED_DATE, PROMOTION_PROCEED_NO, DEPT_ID  from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                                     ps.setInt(1,offid);
                                     ps.setInt(2,eid);
                                     ps.setInt(3,txtRel_SLNO);
                                     rs=ps.executeQuery();
                                         if(rs.next())
                                         {
                                         xml=xml+"<post_oid>"+rs.getInt("OFFICE_TO_POSTED_ID")+"</post_oid><des_id>"+rs.getInt("NEW_DESIGNATION_ID")+"</des_id><repost_req>"+rs.getString("REPOSTING_REQD")+"</repost_req><t_pr_date>"+rs.getDate("PROMOTION_PROCEED_DATE")+"</t_pr_date><t_pr_no>"+rs.getString("PROMOTION_PROCEED_NO")+"</t_pr_no><dep_id>"+rs.getString("DEPT_ID")+"</dep_id>";
                                         }
                                         int ofid=0;
                                         String depid="";
                                         ofid=rs.getInt("OFFICE_TO_POSTED_ID");
                                         System.out.println("ofid..."+ofid);
                                         depid=rs.getString("DEPT_ID");
                                         System.out.println("depid..."+depid);
                                         if(!(depid.equalsIgnoreCase("TWAD")))
                                         {
                                          ps2=con.prepareStatement("select OTHER_DEPT_OFFICE_ID, OTHER_DEPT_OFFICE_NAME as OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES " + 
                                                                   "where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?" );
                                          
                                             ps2.setString(1,depid);
                                             ps2.setInt(2,ofid);
                                          rs2=ps2.executeQuery();
                                         }
                                         else 
                                         {
                                         System.out.println("inside twad condition");
                                         ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
                                         ps2.setInt(1,ofid);
                                         rs2=ps2.executeQuery();
                                         }
                                         if(rs2.next()) 
                                            xml=xml+"<r_oname>"+rs2.getString("OFFICE_NAME")+"</r_oname>";
                                         ps2.close();
                                         rs2.close();
                                         int des_id=0;
                                         des_id=rs.getInt("NEW_DESIGNATION_ID");
                                         ps2=con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?" );
                                         ps2.setInt(1,des_id);
                                         rs2=ps2.executeQuery();
                                         if(rs2.next()) 
                                            xml=xml+"<r_designame>"+rs2.getString("DESIGNATION")+"</r_designame>";
                                         ps2.close();
                                         rs2.close();
                                     }
                                     else if((cmbReason).equalsIgnoreCase("TRN"))
                                     {
                                     ps=con.prepareStatement("select  TRANSFER_TO_OFFICE_ID,REPOSTING_REQD,TRANSFER_PROCEED_DATE,TRANSFER_PROCEED_NO  from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                                     ps.setInt(1,offid);
                                     ps.setInt(2,eid);
                                     ps.setInt(3,txtRel_SLNO);
                                     rs=ps.executeQuery();
                                         if(rs.next())
                                         {
                                         xml=xml+"<t_oid>"+rs.getInt("TRANSFER_TO_OFFICE_ID")+"</t_oid><repost_req>"+rs.getString("REPOSTING_REQD")+"</repost_req><t_pr_date>"+rs.getDate("TRANSFER_PROCEED_DATE")+"</t_pr_date><t_pr_no>"+rs.getString("TRANSFER_PROCEED_NO")+"</t_pr_no>";
                                         }
                                         int ofid=0;
                                         ofid=rs.getInt("TRANSFER_TO_OFFICE_ID");
                                         ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
                                         ps2.setInt(1,ofid);
                                         rs2=ps2.executeQuery();
                                         if(rs2.next()) 
                                            xml=xml+"<r_oname>"+rs2.getString("OFFICE_NAME")+"</r_oname>";
                                         ps2.close();
                                         rs2.close();
                                     }
                                     else if((cmbReason).equalsIgnoreCase("DVN"))
                                     {
                                     ps=con.prepareStatement("select  DIVERSION_TO_OFFICE_ID, DIVERSION_DATE from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                                     ps.setInt(1,offid);
                                     ps.setInt(2,eid);
                                     ps.setInt(3,txtRel_SLNO);
                                     rs=ps.executeQuery();
                                         if(rs.next())
                                         {
                                         xml=xml+"<dvn_oid>"+rs.getInt("DIVERSION_TO_OFFICE_ID")+"</dvn_oid><dvn_date>"+rs.getDate("DIVERSION_DATE")+"</dvn_date>";
                                         }
                                         int ofid=0;
                                         ofid=rs.getInt("DIVERSION_TO_OFFICE_ID");
                                         System.out.println(ofid);
                                         if(ofid!=0)
                                         {
                                         ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
                                         ps2.setInt(1,ofid);
                                         rs2=ps2.executeQuery();
                                         if(rs2.next()) 
                                            xml=xml+"<r_oname>"+rs2.getString("OFFICE_NAME")+"</r_oname>";
                                         ps2.close();
                                         rs2.close();
                                         }
                                         else
                                             xml=xml+"<r_oname>null</r_oname>";
                                         
                                     }
                                     else if((cmbReason).equalsIgnoreCase("DPN"))
                                     {
                                    // ps=con.prepareStatement("select  OTHER_DEPT_ID, OTHER_DEPT_OFFICE_ID,DEPUTATION_PERIOD,DEPUTATION_DATE from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                                     ps=con.prepareStatement("select  OTHER_DEPT_ID, OTHER_DEPT_OFFICE_ID,DEPUTATION_PERIOD,DEPUTATION_DATE from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                                     ps.setInt(1,offid);
                                     ps.setInt(2,eid);
                                     ps.setInt(3,txtRel_SLNO);
                                     rs=ps.executeQuery();
                                         if(rs.next())
                                         {
                                         xml=xml+"<od_id>"+rs.getString("OTHER_DEPT_ID")+"</od_id><od_oid>"+rs.getInt("OTHER_DEPT_OFFICE_ID")+"</od_oid><d_period>"+rs.getString("DEPUTATION_PERIOD")+"</d_period><d_date>"+rs.getDate("DEPUTATION_DATE")+"</d_date>";
                                         }
                                         String depid="";
                                         depid=rs.getString("OTHER_DEPT_ID");
                                         ps2=con.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?" );
                                         ps2.setString(1,depid);
                                         rs2=ps2.executeQuery();
                                         if(rs2.next()) 
                                            xml=xml+"<r_dname>"+rs2.getString("OTHER_DEPT_NAME")+"</r_dname>";
                                         ps2.close();
                                         rs2.close();
                                         int dep_offid=0;
                                         dep_offid=rs.getInt("OTHER_DEPT_OFFICE_ID");
                                         ps2=con.prepareStatement("select OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?" );
                                         ps2.setString(1,depid);
                                         ps2.setInt(2,dep_offid);
                                         rs2=ps2.executeQuery();
                                         if(rs2.next()) 
                                         {
                                            xml=xml+"<r_oname>"+rs2.getString("OTHER_DEPT_OFFICE_NAME")+"</r_oname>";
                                         }
                                         else {
                                             xml=xml+"<r_oname>"+"null"+"</r_oname>";
                                         }
                                         ps2.close();
                                         rs2.close();
                                     }
                                     /* Study leave   */
                                     
                                      else if((cmbReason).equalsIgnoreCase("STU"))
                                      {
                                        System.out.println("cmbReason");
                                          ps=con.prepareStatement("select INSTITUTION_NAME,INSTITUTION_LOCATION,COURSE_NAME,STUDY_PERIOD_FROM,STUDY_PERIOD_TO from HRM_EMP_RELIEVAL_STU where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                                          ps.setInt(1,offid);
                                          ps.setInt(2,eid);
                                          ps.setInt(3,txtRel_SLNO);
                                          rs=ps.executeQuery();
                                              if(rs.next())
                                              {
                                                xml=xml+"<inst_name>"+rs.getString("INSTITUTION_NAME")+"</inst_name><inst_location>"+rs.getString("INSTITUTION_LOCATION")+"</inst_location><course>"+rs.getString("COURSE_NAME")+"</course><from_date>"+rs.getDate("STUDY_PERIOD_FROM")+"</from_date><to_date>"+rs.getDate("STUDY_PERIOD_TO")+"</to_date>";
                                              }
                                      }
                                     
                                    /* end of the study leave */
                                     else if((cmbReason).equalsIgnoreCase("LLV"))
                                     {
                                     ps=con.prepareStatement("select  LEAVE_TYPE_ID, PURPOSE,PERIOD_FROM,PERIOD_TO from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and EMPLOYEE_ID=? and  RELIEVAL_SLNO=?");
                                     ps.setInt(1,offid);
                                     ps.setInt(2,eid);
                                     ps.setInt(3,txtRel_SLNO);
                                     rs=ps.executeQuery();
                                         if(rs.next())
                                         {
                                         xml=xml+"<l_tid>"+rs.getString("LEAVE_TYPE_ID")+"</l_tid><pur>"+rs.getString("PURPOSE")+"</pur><l_pfrom>"+rs.getDate("PERIOD_FROM")+"</l_pfrom><l_pto>"+rs.getDate("PERIOD_TO")+"</l_pto>";
                                         }
                                     }
                                 }
                                 else
                                 {
                                     System.out.println("no record found in relival table");
                                    // xml="<response><command>loademp</command><flag>failure2</flag>";
                                 }
//                                 }
//                                 catch(Exception e)
//                                 {
//                                  System.out.println("catch..HERE.in load emp."+e);
//                                     xml="<response><command>loademp</command><flag>failure2</flag>";
//                                 }
                                
                              }
                                else
                                {
                                   System.out.println("no master record found ");
                                  // xml="<response><command>loademp</command><flag>failure</flag>";
                                 }
                                System.out.println("im here");
                        
                                
                                // New changes ends here
                            	
                                xml = xml + "<flag>failure2</flag>";
                            } else {
                            	
                            	 System.out.println("in normal form................................" );
                            	
sql="select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ";
                                ps =con.prepareStatement(sql);
                                ps.setInt(1, eid);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                            //    System.out.println("sql e.EMPLOYEE_NAME office id"+sql);  
                                if (rs.next()) {


                                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig>";
                                    //xml=xml+"<dob>"+(rs.getDate("date_of_birth")).getYear()+"</dob>";


                                    /***************  16-08-2007    ***********************/


                                    ps =
  con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                    ps.setInt(1, eid);

                                    rs = ps.executeQuery();
                                    String maxfromdate = "";
                                    String maxsession = "";
                                    if (rs.next()) {
                                        if (rs.getDate("DATE_EFFECTIVE_FROM") !=
                                            null) {
                                            maxfromdate =
                                                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_EFFECTIVE_FROM"));
                                            System.out.println("max from date :" +
                                                               rs.getDate("DATE_EFFECTIVE_FROM"));
                                        } else {

                                            maxfromdate = "empty";
                                        }
                                        if (rs.getString("DATE_EFFECTIVE_FROM_SESSION") !=
                                            null) {
                                            maxsession =
                                                    rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                        } else {
                                            maxsession = "FN";
                                        }
                                    }
                                    xml =
 xml + "<maxfromdate>" + maxfromdate + "</maxfromdate><maxsession>" +
   maxsession + "</maxsession>";


                                    /********************************************************/

                                } else
                                    xml = xml + "<flag>failure</flag>";

                            }
                        }
                        /*      else if(rs.getString("EMPLOYEE_STATUS_ID")!=null && !(rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG")))
                                {
                                System.out.println("comes here checking........");
                                       try{
                                        psnew=con.prepareStatement("select EMPLOYEE_ID,relieval_reason_id from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=?");
                                        psnew.setInt(1,eid);
                                        rsnew=psnew.executeQuery();
                                        if(rsnew.next()) {

                                                relreasonid=rsnew.getString("relieval_reason_id");
                                             System.out.println("Relieval Reason Id is "+relreasonid);
                                         }
                                       }
                                       catch(Exception e) {
                                           System.out.println("error in getting reason id");
                                       }
                                         if(relreasonid.equalsIgnoreCase("SUS"))
                                            {
                                              //ok, proceed
                                               try{
                                               ps3=con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ");
                                               ps3.setInt(1,eid);
                                               //ps.setInt(2,offid);
                                               rs3=ps3.executeQuery();
                                               if(rs3.next())
                                               {


                                               xml=xml+"<flag>success</flag><eid>"+eid+"</eid><ename>"+rs3.getString("EMPLOYEE_NAME")+"</ename><desig>"+rs3.getString("DESIGNATION")+"</desig>";
                                               xml=xml+"<dob>"+rs3.getDate("date_of_birth")+"<dob>";
                                               }

                                                   psnew=con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                                   psnew.setInt(1,eid);

                                                   rsnew=psnew.executeQuery();
                                                   String maxfromdate="";
                                                      String maxsession="";
                                                   if(rsnew.next())
                                                   {
                                                       if(rsnew.getDate("DATE_EFFECTIVE_FROM")!=null)
                                                       {
                                                           maxfromdate=new SimpleDateFormat("dd/MM/yyyy").format(rsnew.getDate("DATE_EFFECTIVE_FROM"));
                                                           System.out.println("max from date :"+rsnew.getDate("DATE_EFFECTIVE_FROM"));
                                                       }
                                                       else {

                                                           maxfromdate="empty";
                                                       }
                                                       if(rsnew.getString("DATE_EFFECTIVE_FROM_SESSION")!=null) {
                                                           maxsession=rsnew.getString("DATE_EFFECTIVE_FROM_SESSION");
                                                       }
                                                       else {
                                                           maxsession="FN";
                                                       }
                                                   }
                                                      xml=xml+"<maxfromdate>"+maxfromdate+"</maxfromdate><maxsession>"+maxsession+"</maxsession>";




                                               }catch(Exception e) {
                                                  xml+="<flag>failure</flag>" ;
                                               }


                                            }



                                    }
                                else {
                                      xml=xml+"<flag>failure</flag>";
                                      System.out.println("Failure new ");
                                      sendMessage(response,"Reilval is not possible","ok");
                                  }*/


                    }
                } else {
                    xml = xml + "<flag>failure3</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }


            xml = xml + "</response>";
            out.println(xml);

        }
        else if(strCommand.equalsIgnoreCase("loademp_spl"))
        {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int offid = 0;
            offid = Integer.parseInt(request.getParameter("offid"));
            xml = "<response><command>loademp</command>";
            try {

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
//                boolean flag = true;
//                ps =
//  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
//                ps.setInt(1, eid);
//                rs = ps.executeQuery();
//                if (!rs.next()) {
//                    xml = xml + "<flag>failure</flag>";
//                    flag = false;
//                } 
//                
//                else if (up.getEmployeeId() != eid) {
//                    int OfficeId = 0;
//                    String sql =
//                        "select CONTROLLING_OFFICE_ID from HRM_EMP_CONTROLLING_OFFICE where employee_id=?";
//                    ps = con.prepareStatement(sql);
//                    ps.setInt(1, eid);
//                    rs = ps.executeQuery();
//
//                    if (rs.next()) {
//                        OfficeId = rs.getInt("CONTROLLING_OFFICE_ID");
//                    }
//
//                    if (OfficeId != 0) {
//                        sql =
// "select OFFICE_ID  from HRM_EMP_CURRENT_POSTING where employee_id=?";
//                        ps = con.prepareStatement(sql);
//                        ps.setInt(1, up.getEmployeeId());
//                        rs = ps.executeQuery();
//                        if (rs.next()) {
//                            int offid1 = rs.getInt("OFFICE_ID");
//                           /* if (offid1 != OfficeId) {
//                                //response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Can not see profile. Because Employee Id "+strEmpId+" is not under your Office!");
//                                xml = xml + "<flag>failurea</flag>";
//                                flag = false;
//                            }*/
//                        } else {
//                            // response.sendRedirect(request.getContextPath()+"/org/Library/jsps/Messenger.jsp?message=" + "Current Posting is not available. Can not see the profile for "+strEmpId+"!");
//                            xml = xml + "<flag>failureb</flag>";
//                            flag = false;
//                        }
//
//                    } else {
//                        xml = xml + "<flag>failurec</flag>";
//                        flag = false;
//                    }
//                } else {
//
//                    // xml=xml+"<flag>failured</flag>";
//                    // flag=false;
//                }

//                if (flag) {
//                    int log_offid = 0;
//
//                    ps4 =
// con.prepareStatement("select office_id from hrm_emp_current_posting where employee_id=?");
//                    ps4.setInt(1, up.getEmployeeId());
//                    rs4 = ps4.executeQuery();
//
//                    if (rs4.next()) {
//                        log_offid = rs4.getInt("office_id");
//                        System.out.println("login user office id..." +
//                                           log_offid);
//                    }
                    
                    


//                    ps =
//  con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
//                    ps.setInt(1, eid);
//                    rs = ps.executeQuery();
//                    if (!rs.next()) {
//                        xml = xml + "<flag>failure1</flag>";
//                    } 
                    
//                    else {
                        System.out.println("inside employee status id");

//                        if (rs.getString("EMPLOYEE_STATUS_ID") != null) // && rs.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG"))
//                        {
//                            System.out.println("login office_id..." +
//                                               log_offid);
//
//                            ps =
//  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=? and office_id=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
//                            ps.setInt(1, eid);
//                            ps.setInt(2, log_offid);
//                            rs = ps.executeQuery();
//                            if (rs.next()) {
//                                xml = xml + "<flag>failure2</flag>";
//                            } 
                            
//                            else {

                                ps =  con.prepareStatement("SELECT e.EMPLOYEE_ID, " +
                                		"  TO_CHAR(date_of_birth,'dd/mm/yyyy') AS date_of_birth, " +
                                		"  e.EMPLOYEE_NAME " +
                                		"  ||DECODE(e.EMPLOYEE_INITIAL,NULL,' ','.' " +
                                		"  ||e.EMPLOYEE_INITIAL) AS EMPLOYEE_NAME , " +
                                		"  d.DESIGNATION, " +
                                		"  c.office_id, " +
                                		"  c.employee_status_id, " +
                                		"  a.employee_status_desc " +
                                		"FROM HRM_MST_EMPLOYEES e, " +
                                		"  HRM_EMP_CURRENT_POSTING c, " +
                                		"  HRM_MST_DESIGNATIONS d, " +
                                		"  HRM_MST_EMPLOYEE_STATUS a " +
                                		"WHERE c.DESIGNATION_ID  =d.DESIGNATION_ID " +
                                		"AND e.EMPLOYEE_ID       =c.EMPLOYEE_ID " +
                                		"AND c.employee_status_id=a.employee_status_id " +
                                		"AND e.EMPLOYEE_ID       =?");
                                ps.setInt(1, eid);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                if (rs.next()) {


                                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><date_of_birth>"+
   rs.getString("date_of_birth") + "</date_of_birth><offid>"+
   rs.getInt("office_id")+"</offid><employee_status_id>"+
   rs.getString("employee_status_id") + "</employee_status_id><employee_status_desc>"+
   rs.getString("employee_status_desc") + "</employee_status_desc>";
   
                                    //xml=xml+"<dob>"+(rs.getDate("date_of_birth")).getYear()+"</dob>";


                                    /***************  16-08-2007    ***********************/


                                    ps =
  con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                    ps.setInt(1, eid);

                                    rs = ps.executeQuery();
                                    String maxfromdate = "";
                                    String maxsession = "";
                                    if (rs.next()) {
                                        if (rs.getDate("DATE_EFFECTIVE_FROM") !=
                                            null) {
                                            maxfromdate =
                                                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_EFFECTIVE_FROM"));
                                            System.out.println("max from date :" +
                                                               rs.getDate("DATE_EFFECTIVE_FROM"));
                                        } else {

                                            maxfromdate = "empty";
                                        }
                                        if (rs.getString("DATE_EFFECTIVE_FROM_SESSION") !=
                                            null) {
                                            maxsession =
                                                    rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                        } else {
                                            maxsession = "FN";
                                        }
                                    }
                                    xml =
 xml + "<maxfromdate>" + maxfromdate + "</maxfromdate><maxsession>" +
   maxsession + "</maxsession>";


                                    /********************************************************/

                                } else
                                    xml = xml + "<flag>failure</flag>";

//                            }
//                        }
                      

//                    }
//                } 
//                
//                else {
//                    xml = xml + "<flag>failure3</flag>";
//                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }


            xml = xml + "</response>";
            out.println(xml);

        
        }
        else if (strCommand.equalsIgnoreCase("Suspension")) {
            System.out.println("comes here checking........");
            // try{  eid=Integer.parseInt(request.getParameter("txtEmployeeid"));}catch(Exception e){System.out.println(e);}
            System.out.println(eid);
            xml = "<response><command>Suspension</command>";
            String reason = request.getParameter("reasonid");
            System.out.println("-------------->"+reason);
            System.out.println("employeeid " + eid);

            //whether working or death
            try {
                psnew1 =
                        con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
                psnew1.setInt(1, eid);
                rsnew1 = psnew1.executeQuery();
                if (rsnew1.next()) {
                    String rsnewid = rsnew1.getString("EMPLOYEE_STATUS_ID");
                    System.out.println("id" + rsnewid);
                    System.out.println("before that ");
                    if (rsnew1.getString("EMPLOYEE_STATUS_ID") != null &&
                        !(rsnew1.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("WKG")))
                    // if(rsnewid.equalsIgnoreCase("SUS"))// && (rsnew1.getString("EMPLOYEE_STATUS_ID").equalsIgnoreCase("SUS")))
                    {
                        System.out.println("comes here");


                        try {
                            psnew =
con.prepareStatement("select EMPLOYEE_ID,relieval_reason_id from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=?");
                            psnew.setInt(1, eid);

                            // psnew=con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
                            //   psnew.setInt(1,eid);
                            System.out.println("aaaaaaaa");
                            rsnew = psnew.executeQuery();
                            if (rsnew.next()) {

                                relreasonid =
                                        rsnew.getString("relieval_reason_id");
                                // relreasonid=rsnew.getString("EMPLOYEE_STATUS_ID");
                                System.out.println("Employee Status Id is " +
                                                   relreasonid);

                            }
                            if (reason.equalsIgnoreCase("DTH") || reason.equalsIgnoreCase("TRN") || reason.equalsIgnoreCase("TRT") || reason.equalsIgnoreCase("VRS") || reason.equalsIgnoreCase("LLV") || reason.equalsIgnoreCase("SUS") || reason.equalsIgnoreCase("DPN") || reason.equalsIgnoreCase("STU") || reason.equalsIgnoreCase("DIS") || reason.equalsIgnoreCase("PRO") || reason.equalsIgnoreCase("SAN") || reason.equalsIgnoreCase("MEV") || reason.equalsIgnoreCase("STT") || reason.equalsIgnoreCase("RES") || reason.equalsIgnoreCase("ABR") || reason.equalsIgnoreCase("UAL") || reason.equalsIgnoreCase("ABS")) {

                                xml = xml + "<flag>success</flag>";
                                //ok, proceed
                                /*   try{
                             ps3=con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=? ");
                             ps3.setInt(1,eid);
                             //ps.setInt(2,offid);
                             rs3=ps3.executeQuery();
                             if(rs3.next())
                             {


                             xml=xml+"<flag>success</flag><eid>"+eid+"</eid><ename>"+rs3.getString("EMPLOYEE_NAME")+"</ename><desig>"+rs3.getString("DESIGNATION")+"</desig>";
                             xml=xml+"<dob>"+rs3.getDate("date_of_birth")+"<dob>";
                             }

                                 psnew=con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                 psnew.setInt(1,eid);

                                 rsnew=psnew.executeQuery();
                                 String maxfromdate="";
                                    String maxsession="";
                                 if(rsnew.next())
                                 {
                                     if(rsnew.getDate("DATE_EFFECTIVE_FROM")!=null)
                                     {
                                         maxfromdate=new SimpleDateFormat("dd/MM/yyyy").format(rsnew.getDate("DATE_EFFECTIVE_FROM"));
                                         System.out.println("max from date :"+rsnew.getDate("DATE_EFFECTIVE_FROM"));
                                     }
                                     else {

                                         maxfromdate="empty";
                                     }
                                     if(rsnew.getString("DATE_EFFECTIVE_FROM_SESSION")!=null) {
                                         maxsession=rsnew.getString("DATE_EFFECTIVE_FROM_SESSION");
                                     }
                                     else {
                                         maxsession="FN";
                                     }
                                 }
                                    xml=xml+"<maxfromdate>"+maxfromdate+"</maxfromdate><maxsession>"+maxsession+"</maxsession>";




                             }catch(Exception e) {
                                xml+="<flag>failure</flag>" ;
                             }*/
                            } else {
                               xml += "<flag>failurenew</flag>";
                                // sendMessage(response,"Relieval is not possible","ok");
                            }
                        } catch (Exception e) {

                            System.out.println("error in getting reason id");
                        }
                        // if((relreasonid.equalsIgnoreCase("DTH")))


                    }
                    xml = xml + "<flag>success</flag>";
                }
            } catch (Exception e) {
                System.out.println("error");
                xml = xml + "<flag>fail</flag>";
            }
            //    else {

            //    }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);


        }

        else if (strCommand.equalsIgnoreCase("loademp1")) {
            xml = "<response><command>loademp1</command>";
            try {
                eid = Integer.parseInt(request.getParameter("eid"));
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                ps3 =
 con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND e.EMPLOYEE_ID=?  ");
                ps3.setInt(1, eid);
                //ps.setInt(2,offid);
                rs3 = ps3.executeQuery();
                if (rs3.next()) {


                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs3.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs3.getString("DESIGNATION") + "</desig>";
                    xml =
 xml + "<dob>" + rs3.getDate("date_of_birth") + "<dob>";
                }
            } catch (Exception e) {
                xml += "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("office")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>office</command>";
            try {
                int oid = 0;
                String oname = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                ps2 =
 con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps2.setInt(1, oid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><oid>" + oid + "</oid><oname>" +
   rs2.getString("OFFICE_NAME") + "</oname>";
                else
                    xml = xml + "<flag>failure</flag><oid>" + oid + "</oid>";
                ps2.close();
                rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("desig")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>desig</command>";
            try {
                int desigid = 0;
                String designame = "";
                try {
                    desigid =
                            Integer.parseInt(request.getParameter("desigid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                ps2 =
 con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                ps2.setInt(1, desigid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><designame>" + rs2.getString("DESIGNATION") +
   "</designame>";
                else
                    xml = xml + "<flag>failure</flag>";
                ps2.close();
                rs2.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";

            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("dept")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>dept</command>";
            try {
                int oid = 0;
                String oname = "", deptid = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }
                deptid = request.getParameter("deptid");
                ps2 =
 con.prepareStatement("select OTHER_DEPT_NAME from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?");
                ps2.setString(1, deptid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<flag>success</flag><dname>" + rs2.getString("OTHER_DEPT_NAME") +
   "</dname>";
                else
                    xml = xml + "<flag>failure</flag><err>did</err>";
                ps2.close();
                rs2.close();
                ps2 =
 con.prepareStatement("select OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?");
                ps2.setString(1, deptid);
                ps2.setInt(2, oid);
                rs2 = ps2.executeQuery();
                if (rs2.next())
                    xml =
 xml + "<oname>" + rs2.getString("OTHER_DEPT_OFFICE_NAME") + "</oname>";
                else
                    xml = xml + "<flag>failure</flag><err1>ofid</err1>";
                ps2.close();
                rs2.close();

            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";

            session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            int txtOffId = 0, txtEmployeeid = 0, txtRel_SLNO = 0;
            Date txtDORelieval = null;
            Date ord_dat = null;
            String rad_DORelieval = "", cmbReason = "", txtRemarks =
                "", Proc_Status = "";
            String pro_no = "", pres_off = "", copy_to = "", ord_no = "";
            System.out.println(xml);
            Proc_Status = request.getParameter("cmbStatus");
            System.out.println(Proc_Status);
            // strCommand=request.getParameter("from here");
            txtOffId = Integer.parseInt(request.getParameter("txtOffId"));
            txtEmployeeid =
                    Integer.parseInt(request.getParameter("txtEmployeeid"));

            String[] sd = request.getParameter("txtDORelieval").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtDORelieval = new Date(d.getTime());

            rad_DORelieval = request.getParameter("rad_DORelieval");

            cmbReason = request.getParameter("cmbReason");
            txtRemarks = request.getParameter("txtRemarks");

            pro_no = request.getParameter("txtPNo");
            System.out.println("pro no..." + pro_no);

            ord_no = request.getParameter("txtRO");
            System.out.println("order no..." + ord_no);

            try {
                if ((request.getParameter("txtRDat")) != null) {
                    String[] sd2 = request.getParameter("txtRDat").split("/");
                    c2 =
  new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,
                        Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c2.getTime();
                    ord_dat = new Date(d2.getTime());
                } else {
                    System.out.println("inside else");
                    ord_dat = null;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("ord dat..." + ord_dat);


            System.out.println(txtOffId);
            System.out.println(txtEmployeeid);
            System.out.println(txtRel_SLNO);
            System.out.println(txtDORelieval);
            System.out.println(cmbReason);
            System.out.println("from " +
                               request.getParameter("txtDORelieval"));
            try {
                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");

                int log_offid = 0;

                ps4 =
 con.prepareStatement("select office_id from hrm_emp_current_posting where employee_id=?");
                ps4.setInt(1, up.getEmployeeId());
                rs4 = ps4.executeQuery();

                if (rs4.next()) {
                    log_offid = rs4.getInt("office_id");
                    System.out.println("login user office id..." + log_offid);
                }


                ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=? and office_id=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                ps.setInt(1, txtEmployeeid);
                ps.setInt(2, log_offid);
                rs = ps.executeQuery();
                if (rs.next()) {
                	
                	
                	//  New Changes
                	
                	   
                 //   String CONTENT_TYPE = "text/html; charset=windows-1252";
                  //  response.setContentType(CONTENT_TYPE);
                 //    xml="<response><command>Add</command>";
                     
                     
                    session =request.getSession(false);
                 //   String updatedby=(String)session.getAttribute("UserId");
               //     long l=System.currentTimeMillis();
                 //   java.sql.Timestamp ts=new java.sql.Timestamp(l);
                 //   Date ord_dat=null;
                  //   
                     //int txtOffId=0,txtEmployeeid=0,txtRel_SLNO=0;
                     //Date  txtDORelieval=null;
                     //String rad_DORelieval="",cmbReason="",txtRemarks="";
                  //   String Proc_Status="";
                    System.out.println("xml is----"+xml);
                    System.out.println("Proc_Status is----"+Proc_Status);
                    if(request.getParameter("_status")!=null && request.getParameter("_status").equalsIgnoreCase("FR"))
                    {
                     Proc_Status="FR";
                    }
                    else
                    {
                    Proc_Status="MD";
                    }
                    System.out.println("Proc_Status............."+Proc_Status);
                     txtRel_SLNO=Integer.parseInt(request.getParameter("txtRel_SLNO"));
                    System.out.println(xml);
                    System.out.println(txtRel_SLNO);
                   // strCommand=request.getParameter("from here");
                    txtOffId=Integer.parseInt(request.getParameter("txtOffId"));             
                    txtEmployeeid=Integer.parseInt(request.getParameter("txtEmployeeid"));
                   
                  sd=request.getParameter("txtDORelieval").split("/");
                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                  d=c.getTime();
                    txtDORelieval=new Date(d.getTime());
                 
                    rad_DORelieval=request.getParameter("rad_DORelieval");
                   
                    cmbReason=request.getParameter("cmbReason");
                    System.out.println(cmbReason+"add--------------->");
                    
                 pro_no=request.getParameter("txtPNo");   
                    System.out.println("proceeding no..."+pro_no);
                    
                   ord_no=request.getParameter("txtRO");
                    System.out.println("order no..."+ord_no);
                    
                    try
                    {
                    String[] sd1=request.getParameter("txtRDat").split("/");
                    c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                    java.util.Date d1=c.getTime();
                    ord_dat=new Date(d1.getTime());
                    }
                    catch(Exception e)
                    {
                     System.out.println(e.getMessage());
                    }
                    System.out.println("order date..."+ord_dat);
                  
                    // cmbReason="FR";
                    txtRemarks=request.getParameter("txtRemarks");
                    System.out.println(txtOffId);
                    System.out.println(txtEmployeeid);
                    System.out.println(txtRel_SLNO);
                    System.out.println(txtDORelieval);
                    System.out.println(cmbReason);
                    System.out.println("from *************************"+request.getParameter("txtDORelieval"));
                    System.out.println("-------------->");
                   try
                   {
                        if(cmbReason.equalsIgnoreCase("TRN"))
                        { 
                                   System.out.println("TRN");
                           int i=0;
                            int txtT_OffId=0;
                            try{txtT_OffId=Integer.parseInt(request.getParameter("txtT_OffId"));}catch(Exception e){}
                            String radT_Repost=null;
                            radT_Repost=request.getParameter("radT_Repost");
                            Date txtT_proc_Date=null;
                            String s=null;
                            s=request.getParameter("txtT_proc_Date");
                            System.out.println("s value"+s);
                            if(!s.equals(""))
                            {
                            String sdTd[]=request.getParameter("txtT_proc_Date").split("/");
                            c=new GregorianCalendar(Integer.parseInt(sdTd[2]),Integer.parseInt(sdTd[1])-1,Integer.parseInt(sdTd[0]));
                            d=c.getTime();
                            txtT_proc_Date=new Date(d.getTime());
                            }
                            String txtT_proc_No=request.getParameter("txtT_proc_No");    
                            
                            String dep_id="TWAD";
                            System.out.println("dep_id..."+dep_id);
                                
                               
           
                                     try 
                                        {
                                             con.setAutoCommit(false);
                                             int desid=0;
                                              desid=Integer.parseInt(request.getParameter("txtdesid"));
                                             int officeid=0;
                                             officeid=Integer.parseInt(request.getParameter("txtofficeid"));
                                             
                                             boolean flag=true;
                                              System.out.println("proc No:"+Proc_Status);
                                             if(Proc_Status.equalsIgnoreCase("FR"))
                                             {
                                               
                                              
                                              
                                              /*   remove the SR Controlling office and moved it to log table  */
                                              System.out.println("step 1");
                                              PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                              "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                              "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                                              psorg.setInt(1,txtEmployeeid);
                                              ResultSet rsorg=psorg.executeQuery();
                                              if(rsorg.next()) {
                                                 System.out.println(rsorg.getInt("CONTROLLING_OFFICE_ID")+"step 2");
                                                 PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                                                 psins.setInt(1,txtEmployeeid);
                                                 ResultSet rsins=psins.executeQuery();
                                                 if(rsins.next()){
                                                     int sid=rsins.getInt("sid");
                                                     if(sid>0) {
                                                         sid+=1;
                                                     }
                                                     else{
                                                         sid=1;
                                                     }
                                                     System.out.println(sid+"step 3");
                                                     psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " + 
                                                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                                                     psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                     psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                                                     psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                                                     psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                                                     psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                                                     psins.setInt(6,sid);
                                                     
                                                     psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                                                     psins.setTimestamp(8,rsorg.getTimestamp("UPDATED_DATE"));
                                                     int s1=psins.executeUpdate();
                                                     System.out.println(s1+"step 4");
                                                 }
                                              }
                                              
                                                 System.out.println("step 5");
                                                 psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                                                 psorg.setInt(1,txtEmployeeid);
                                               int s1= psorg.executeUpdate();
                                                 System.out.println(s1+"step 6");
                                                 
                                                 psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                                                 psorg.setString(1,updatedby);
                                                 psorg.setTimestamp(2,ts);
                                                 psorg.setInt(3,txtEmployeeid);
                                                 psorg.executeUpdate();
                                                 System.out.println("step 7");
                                                 
                                             
                                                
                                                 
                                           /*      psorg=con.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                                                 psorg.setInt(1,txtEmployeeid);
                                                 psorg.setInt(2,22);
                                                 psorg.setString(3,updatedby);
                                                 psorg.setTimestamp(4,ts);
                                                 psorg.setInt(5,999);
                                                 psorg.executeUpdate();
                                                 System.out.println("step 10");
                                             */    
                                                 
                                              /* End of the comment:   remove the SR Controlling office and moved it to log table  */
                                              
                                              
                                              /* move the wing to log wing */
                                               psorg=con.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE from HRM_EMP_CURRENT_WING where employee_id=?");
                                               psorg.setInt(1,txtEmployeeid);
                                               rsorg=psorg.executeQuery();
                                               if(rsorg.next()) {
                                                  System.out.println("step 21");
                                                  PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CURRENT_WING_LOG where EMPLOYEE_ID =? ");
                                                  psins.setInt(1,txtEmployeeid);
                                                  ResultSet rsins=psins.executeQuery();
                                                  if(rsins.next()){
                                                      int sid=rsins.getInt("sid");
                                                      if(sid>0) {
                                                          sid+=1;
                                                      }
                                                      else{
                                                          sid=1;
                                                      }
                                                      System.out.println("step 31");
                                                      psins=con.prepareStatement("insert into HRM_EMP_CURRENT_WING_LOG(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,LOG_ID) values (?,?,?,?,?,?,?)");
                                                      psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                      psins.setInt(2,rsorg.getInt("OFFICE_ID"));
                                                      psins.setInt(3,rsorg.getInt("OFFICE_WING_SINO"));
                                                      psins.setDate(4,rsorg.getDate("DATE_EFFECTIVE_FROM"));
                                                      psins.setString(5,rsorg.getString("UPDATED_BY_USER_ID"));
                                                      psins.setTimestamp(6,rsorg.getTimestamp("UPDATED_DATE"));
                                                      psins.setInt(7,sid);
                                                      psins.executeUpdate();
                                                      System.out.println("step 41");
                                                  }
                                               }
                                               
                                                  System.out.println("step 51");
                                                  psorg=con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                                                  psorg.setInt(1,txtEmployeeid);
                                                  psorg.executeUpdate();
                                                  System.out.println("step 61");
                                               
                                               /* wing move to log */
                                                if((cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS")|| cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") ||cmbReason.equalsIgnoreCase("RES")))
                                                {
                                                ps=con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                                                ps.setInt(1,txtEmployeeid);
                                                ps.executeUpdate();
                                                System.out.println("step 8 new--------->");
                                                ps.close();
                                                ps=con.prepareStatement("delete from SEC_MST_USER_ROLES where EMPLOYEE_ID=? ");
                                                ps.setInt(1,txtEmployeeid);
                                                ps.executeUpdate();
                                                System.out.println("step 9");
                                                ps.close();
                                                }
                                                else {
                                                
                                                ps=con.prepareStatement("delete from sec_mst_user_roles where employee_id=? and  role_id in (select role_id from sec_mst_roles where role_category is null or role_category='C')");
                                                ps.setInt(1,txtEmployeeid);
                                                ps.executeUpdate();
                                                System.out.println("step 9 new --------->");
                                                ps.close();
                                                }
                                              
                                              
                                              
                                              
                                             }
                                               
                                             if(flag)  
                                             {
                                             
                                             
                                                 String sql="select RELIEVAL_REASON_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                                                         
                                                 ps=con.prepareStatement(sql);
                                                 ps.setInt(1,officeid);
                                                 ps.setInt(2,txtRel_SLNO);
                                                rs=ps.executeQuery();
                                                if(rs.next()) {
                                                   String rid=rs.getString("RELIEVAL_REASON_ID");
                                                   if(!rid.equalsIgnoreCase("TRN")) {
                                                       String sql1="";
                                                       if(rid.equalsIgnoreCase("DPN")) {
                                                           sql1="delete from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                       }
                                                       else  if(rid.equalsIgnoreCase("DVN")) {
                                                           sql1="delete from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                       }
                                                       else  if(rid.equalsIgnoreCase("LLV")) {
                                                           sql1="delete from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                       }
                                                       else  if(rid.equalsIgnoreCase("PRO")) {
                                                           sql1="delete from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                       }
                                                       else  if(rid.equalsIgnoreCase("STU")) {
                                                           sql1="delete from HRM_EMP_RELIEVAL_STU where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                       }
                                                       if(!(rid.equalsIgnoreCase("SUS") || rid.equalsIgnoreCase("VRS") || rid.equalsIgnoreCase("DIS")|| rid.equalsIgnoreCase("SAN")  || rid.equalsIgnoreCase("DTH")|| rid.equalsIgnoreCase("ABR")))
                                                       {
                                                       System.out.println("sql1:"+sql);
                                                       ps=con.prepareStatement(sql1);
                                                       ps.setInt(1,officeid);
                                                       ps.setInt(2,txtRel_SLNO);
                                                       ps.executeUpdate();
                                                       System.out.println("delete is ok");
                                                       ps.close();
                                                       }
                                                          
                                                       
                                                   }
                                                }
                                             
                                             
                                             
                                           
                                            cs=con.prepareCall("{call HRM_EMP_RELIEVAL_TRN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}"); 
                                            cs.setInt(1,txtOffId);
                                            cs.setInt(2,txtRel_SLNO);
                                            cs.setInt(3,txtEmployeeid);
                                            cs.setDate(4,txtDORelieval);
                                            cs.setString(5,rad_DORelieval);
                                            cs.setString(6,cmbReason);
                                            cs.setString(7,txtRemarks);
                                            //cs.setString(8,txtRemarks);
                                             cs.setInt(8,txtT_OffId);
                                             cs.setString(9,radT_Repost);
                                             cs.setDate (10,txtT_proc_Date);
                                             cs.setString(11,txtT_proc_No);
                               
                                             cs.setString(13,"update");
                                             cs.setString(14,Proc_Status);
                                            cs.setInt(15,desid);
                                                 cs.setInt(16,officeid);
                                                 
                                                 cs.setString(17,updatedby);
                                                 cs.setTimestamp(18,ts);
                                                 cs.setString(19,dep_id);
                                                 cs.setString(20,pro_no);
                                                 cs.setString(21,ord_no);
                                                 cs.setDate(22,ord_dat);
                                                 cs.setInt(23, 0);
                                            cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                                            cs.registerOutParameter(12,java.sql.Types.NUMERIC);
                                            cs.execute();
                                            txtRel_SLNO=cs.getInt(2);
                                            int errcode=cs.getInt(12);
                                            System.out.println("SQLCODE:::"+errcode);
                                            if(errcode!=0)
                                            {
                                            xml=xml+"<flag>failure</flag>";
                                            con.rollback();
                                            }
                                            else
                                                {
                                                con.commit();
                                                xml=xml+"<flag>success</flag><relieNo>"+txtRel_SLNO+"</relieNo>";
                                                sendMessage(response," The Relieval Serial Number  " + txtRel_SLNO + "  has been modified successfully.","ok");   
                                                }
                                             }
                                           else {
                                               xml=xml+"<flag>failure</flag><flag>There is no joining report for this Relieval report.Cann't Relive the employee.</flag>";
                                           }
                                         }
                                         catch(Exception e) 
                                         {
                                             System.out.println("insert exception  :"+e); 
                                             sendMessage(response,"Exception in updation due to."+e,"ok");   
                                             xml=xml+"<flag>failure</flag>";
                                             try{con.rollback();}catch(Exception e1){System.out.println(e1);}
                                             }
                                             finally {
                                             try{con.setAutoCommit(true);}catch(Exception e2){System.out.println(e2);}
                                             }
                               
                        }
                        else if(cmbReason.equalsIgnoreCase("DPN")) 
                        {
                            System.out.println(cmbReason);
                             String txtD_ODep_Id="";
                             int i=0;
                             try{txtD_ODep_Id=request.getParameter("txtD_ODep_Id");}catch(Exception e){}
                             int txtD_ODep_OffId=0;
                             try{txtD_ODep_OffId=Integer.parseInt(request.getParameter("txtD_ODep_OffId"));}catch(Exception e){}
                             String txtD_Period=request.getParameter("txtD_Period");
                             Date txtD_Date=null;
                            String s=null;
                            s=request.getParameter("txtD_Date");
                            System.out.println("s value"+s);
                            if(!s.equals(""))
                            {
                             String sdD[]=request.getParameter("txtD_Date").split("/");
                             c=new GregorianCalendar(Integer.parseInt(sdD[2]),Integer.parseInt(sdD[1])-1,Integer.parseInt(sdD[0]));
                             d=c.getTime();
                             txtD_Date=new Date(d.getTime());
                            }
                            
                            String dep_id=request.getParameter("txtDept_Id");
                            System.out.println("dep id..."+dep_id);
                            
                           
                                        try 
                                         {
                                              con.setAutoCommit(false);
                                              int desid=0;
                                               desid=Integer.parseInt(request.getParameter("txtdesid"));
                                              int officeid=0;
                                              officeid=Integer.parseInt(request.getParameter("txtofficeid"));
                                              
                                              boolean flag=true;
                                               System.out.println("proc No:"+Proc_Status);
                                          if(Proc_Status.equalsIgnoreCase("FR"))
                                          {
                                                
                                              
                                              
                                              /*   remove the SR Controlling office and moved it to log table  */
                                              System.out.println("step 1");
                                              PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                              "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                              "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                                              psorg.setInt(1,txtEmployeeid);
                                              ResultSet rsorg=psorg.executeQuery();
                                              if(rsorg.next()) {
                                                 System.out.println("step 2");
                                                 PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                                                 psins.setInt(1,txtEmployeeid);
                                                 ResultSet rsins=psins.executeQuery();
                                                 if(rsins.next()){
                                                     int sid=rsins.getInt("sid");
                                                     if(sid>0) {
                                                         sid+=1;
                                                     }
                                                     else{
                                                         sid=1;
                                                     }
                                                     System.out.println("step 3");
                                                     psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " + 
                                                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                                                     psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                     psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                                                     psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                                                     psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                                                     psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                                                     psins.setInt(6,sid);
                                                     psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                                                     psins.setTimestamp(8,rsorg.getTimestamp("UPDATED_DATE"));

                                                     psins.executeUpdate();
                                                     System.out.println("step 4");
                                                 }
                                              }
                                              
                                                 System.out.println("step 5");
                                                 psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                                                 psorg.setInt(1,txtEmployeeid);
                                                 psorg.executeUpdate();
                                                 System.out.println("step 6");
                                                 
                                                 
                                              psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                                              psorg.setString(1,updatedby);
                                              psorg.setTimestamp(2,ts);
                                              psorg.setInt(3,txtEmployeeid);
                                              psorg.executeUpdate();
                                              System.out.println("step 7");
                                                 
                                              if((cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS")|| cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") ||cmbReason.equalsIgnoreCase("RES")))
                                              {
                                              ps=con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                                              ps.setInt(1,txtEmployeeid);
                                              ps.executeUpdate();
                                              System.out.println("step 8 new--------->");
                                              ps.close();
                                              ps=con.prepareStatement("delete from SEC_MST_USER_ROLES where EMPLOYEE_ID=? ");
                                              ps.setInt(1,txtEmployeeid);
                                              ps.executeUpdate();
                                              System.out.println("step 9");
                                              ps.close();
                                              }
                                              else {
                                              
                                              ps=con.prepareStatement("delete from sec_mst_user_roles where employee_id=? and  role_id in (select role_id from sec_mst_roles where role_category is null or role_category='C')");
                                              ps.setInt(1,txtEmployeeid);
                                              ps.executeUpdate();
                                              System.out.println("step 9 new --------->");
                                              ps.close();
                                              }
                                              
                                            /*  psorg=con.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                                              psorg.setInt(1,txtEmployeeid);
                                              psorg.setInt(2,22);
                                              psorg.setString(3,updatedby);
                                              psorg.setTimestamp(4,ts);
                                              psorg.setInt(5,999);
                                              psorg.executeUpdate();
                                              System.out.println("step 10");*/
                                              /* End of the comment:   remove the SR Controlling office and moved it to log table  */
                                              
                                               /* move the wing to log wing */
                                                psorg=con.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE from HRM_EMP_CURRENT_WING where employee_id=?");
                                                psorg.setInt(1,txtEmployeeid);
                                                rsorg=psorg.executeQuery();
                                                if(rsorg.next()) {
                                                   System.out.println("step 21");
                                                   PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CURRENT_WING_LOG where EMPLOYEE_ID =? ");
                                                   psins.setInt(1,txtEmployeeid);
                                                   ResultSet rsins=psins.executeQuery();
                                                   if(rsins.next()){
                                                       int sid=rsins.getInt("sid");
                                                       if(sid>0) {
                                                           sid+=1;
                                                       }
                                                       else{
                                                           sid=1;
                                                       }
                                                       System.out.println("step 31");
                                                       psins=con.prepareStatement("insert into HRM_EMP_CURRENT_WING_LOG(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,LOG_ID) values (?,?,?,?,?,?,?)");
                                                       psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                       psins.setInt(2,rsorg.getInt("OFFICE_ID"));
                                                       psins.setInt(3,rsorg.getInt("OFFICE_WING_SINO"));
                                                       psins.setDate(4,rsorg.getDate("DATE_EFFECTIVE_FROM"));
                                                       psins.setString(5,rsorg.getString("UPDATED_BY_USER_ID"));
                                                       psins.setTimestamp(6,rsorg.getTimestamp("UPDATED_DATE"));
                                                       psins.setInt(7,sid);
                                                       psins.executeUpdate();
                                                       System.out.println("step 41");
                                                   }
                                                }
                                                
                                                   System.out.println("step 51");
                                                   psorg=con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                                                   psorg.setInt(1,txtEmployeeid);
                                                   psorg.executeUpdate();
                                                   System.out.println("step 61");
                                                
                                                /* wing move to log */
                                              
                                               flag=true;
                                          }
                                                
                                              if(flag)  
                                              {
                                              
                                              
                                              
                                              
                                              
                                                  String sql="select RELIEVAL_REASON_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                  ps=con.prepareStatement(sql);
                                                  ps.setInt(1,officeid);
                                                  ps.setInt(2,txtRel_SLNO);
                                                  rs=ps.executeQuery();
                                                  if(rs.next()) {
                                                    String rid=rs.getString("RELIEVAL_REASON_ID");
                                                    if(!rid.equalsIgnoreCase("DPN")) {
                                                        String sql1="";
                                                        if(rid.equalsIgnoreCase("TRN")) {
                                                            sql1="delete from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                        }
                                                        else  if(rid.equalsIgnoreCase("DVN")) {
                                                            sql1="delete from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                        }
                                                        else  if(rid.equalsIgnoreCase("LLV")) {
                                                            sql1="delete from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                        }
                                                        else  if(rid.equalsIgnoreCase("PRO")) {
                                                            sql1="delete from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                        }
                                                        else  if(rid.equalsIgnoreCase("STU")) {
                                                            sql1="delete from HRM_EMP_RELIEVAL_STU where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                        }
                                                        if(!(rid.equalsIgnoreCase("SUS") || rid.equalsIgnoreCase("VRS") || rid.equalsIgnoreCase("DIS")|| rid.equalsIgnoreCase("SAN")  || rid.equalsIgnoreCase("DTH")|| rid.equalsIgnoreCase("ABR")))
                                                        {
                                                        System.out.println("sql1:"+sql);
                                                        ps=con.prepareStatement(sql1);
                                                        ps.setInt(1,officeid);
                                                        ps.setInt(2,txtRel_SLNO);
                                                        ps.executeUpdate();
                                                        System.out.println("delete is ok");
                                                        }
                                                    }
                                                  }
                                              
                                              
                                              
                                              System.out.println("here is ok");
                                             cs=con.prepareCall("{call HRM_EMP_RELIEVAL_DPN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                                             cs.setInt(1,txtOffId);
                                               cs.setInt(2,txtRel_SLNO);
                                             cs.setInt(3,txtEmployeeid);
                                             cs.setDate(4,txtDORelieval);
                                             cs.setString(5,rad_DORelieval);
                                             cs.setString(6,cmbReason);
                                             cs.setString(7,txtRemarks);
                                                   //cs.setString(8,txtRemarks);
                                              cs.setString(8,txtD_ODep_Id);
                                              cs.setInt(9,txtD_ODep_OffId);
                                              cs.setString (10,txtD_Period);
                                              cs.setDate(11,txtD_Date);
                                              cs.setString(13,"update");
                                              cs.setString(14,Proc_Status);
                                                  cs.setInt(15,desid);
                                                  cs.setInt(16,officeid);
                                                  
                                                  cs.setString(17,updatedby);
                                                  cs.setTimestamp(18,ts);
                                                  cs.setString(19,dep_id);
                                                  cs.setString(20,pro_no);
                                                  cs.setString(21,ord_no);
                                                  cs.setDate(22,ord_dat);
                                                  cs.setInt(23,0);
                                             cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                                             cs.registerOutParameter(12,java.sql.Types.NUMERIC);
                                             cs.execute();
                                             txtRel_SLNO=cs.getInt(2);
                                             int errcode=cs.getInt(12);
                                             System.out.println("SQLCODE:::"+errcode);
                                             if(errcode!=0)
                                             {
                                             xml=xml+"<flag>failure</flag>";
                                             }
                                             else
                                              {
                                                  con.commit();
                                              xml=xml+"<flag>success</flag><relieNo>"+txtRel_SLNO+"</relieNo>";
                                              sendMessage(response," The Relieval Serial Number  " + txtRel_SLNO + "  has been modified successfully.","ok");   
                                              }
                                              }
                                                else {
                                                    xml=xml+"<flag>failure</flag><flag>There is no joining report for this Relieval report.Cann't Relive the employee.</flag>";
                                                }
                                          }
                                          catch(Exception e) 
                                          {
                                              System.out.println("insert exception  :"+e); 
                                              sendMessage(response,"Exception in updation due to."+e,"ok");   
                                              xml=xml+"<flag>failure</flag>";
                                              try{con.rollback();}catch(Exception e1){System.out.println(e1);}
                                              }
                                              finally {
                                              try{con.setAutoCommit(true);}catch(Exception e2){System.out.println(e2);}
                                              }
                           
                        }
                    else if(cmbReason.equalsIgnoreCase("PRO")) 
                    {
                        System.out.println(cmbReason);
                        String depid="";
                        
                         int txtP_OffId=0,i=0;
                         try{txtP_OffId=Integer.parseInt(request.getParameter("txtP_OffId"));}catch(Exception e){}
                         int txtP_desigId=0;
                         try{txtP_desigId=Integer.parseInt(request.getParameter("txtP_desigId"));}catch(Exception e){}
                        
                        try{depid=request.getParameter("txtP_depid");}catch(Exception e){System.out.println(e.getMessage());}
                        
                        String radP_Repost=null;
                        radP_Repost=request.getParameter("radP_Repost");
                        Date txtP_proc_Date=null;
                        
                        String s=null;
                        s=request.getParameter("txtP_proc_Date");
                        System.out.println("s value"+s);
                        if(!s.equals(""))
                        {
                        String sdTd[]=request.getParameter("txtP_proc_Date").split("/");
                        c=new GregorianCalendar(Integer.parseInt(sdTd[2]),Integer.parseInt(sdTd[1])-1,Integer.parseInt(sdTd[0]));
                        d=c.getTime();
                        txtP_proc_Date=new Date(d.getTime());
                        }
                        String txtP_proc_No=request.getParameter("txtP_proc_No");      
                            System.out.println("ok promotion");
                            
                        
                            
                                    try 
                                     {
                                          con.setAutoCommit(false);
                                          boolean flag=true;
                                          System.out.println("proc No:"+Proc_Status);
                                          int desid=0;
                                          desid=Integer.parseInt(request.getParameter("txtdesid"));
                                          int officeid=0;
                                          officeid=Integer.parseInt(request.getParameter("txtofficeid"));
                                          
                                     if(Proc_Status.equalsIgnoreCase("FR"))
                                     {
                                         
                                         
                                         /*   remove the SR Controlling office and moved it to log table  */
                                         System.out.println("step 1");
                                         PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                         "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                         "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                                         psorg.setInt(1,txtEmployeeid);
                                         ResultSet rsorg=psorg.executeQuery();
                                         if(rsorg.next()) {
                                            System.out.println("step 2");
                                            PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                                            psins.setInt(1,txtEmployeeid);
                                            ResultSet rsins=psins.executeQuery();
                                            if(rsins.next()){
                                                int sid=rsins.getInt("sid");
                                                if(sid>0) {
                                                    sid+=1;
                                                }
                                                else{
                                                    sid=1;
                                                }
                                                System.out.println("step 3");
                                                psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " + 
                                                "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                                                psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                                                psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                                                psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                                                psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                                                psins.setInt(6,sid);
                                                psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                                                psins.setTimestamp(8,rsorg.getTimestamp("UPDATED_DATE"));
                                                psins.executeUpdate();
                                                System.out.println("step 4");
                                            }
                                         }
                                         
                                            System.out.println("step 5");
                                            psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                                            psorg.setInt(1,txtEmployeeid);
                                            psorg.executeUpdate();
                                            System.out.println("step 6");
                                            
                                         psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                                         psorg.setString(1,updatedby);
                                         psorg.setTimestamp(2,ts);
                                         psorg.setInt(3,txtEmployeeid);
                                         psorg.executeUpdate();
                                            System.out.println("step 7");
                                            
                                         if((cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS")|| cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") ||cmbReason.equalsIgnoreCase("RES")||cmbReason.equalsIgnoreCase("UAL")))
                                         {
                                         ps=con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                                         ps.setInt(1,txtEmployeeid);
                                         ps.executeUpdate();
                                         System.out.println("step 8 new--------->");
                                         ps.close();
                                         ps=con.prepareStatement("delete from SEC_MST_USER_ROLES where EMPLOYEE_ID=? ");
                                         ps.setInt(1,txtEmployeeid);
                                         ps.executeUpdate();
                                         System.out.println("step 9");
                                         ps.close();
                                         }
                                         else {
                                         
                                         ps=con.prepareStatement("delete from sec_mst_user_roles where employee_id=? and  role_id in (select role_id from sec_mst_roles where role_category is null or role_category='C')");
                                         ps.setInt(1,txtEmployeeid);
                                         ps.executeUpdate();
                                         System.out.println("step 9 new --------->");
                                         ps.close();
                                         }
                                         
                                         
                                       /*  psorg=con.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                                         psorg.setInt(1,txtEmployeeid);
                                         psorg.setInt(2,22);
                                         psorg.setString(3,updatedby);
                                         psorg.setTimestamp(4,ts);
                                         psorg.setInt(5,999);
                                         psorg.executeUpdate();
                                         System.out.println("step 10");*/
                                         /* End of the comment:   remove the SR Controlling office and moved it to log table  */
                                         
                                         
                                          /* move the wing to log wing */
                                           psorg=con.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE from HRM_EMP_CURRENT_WING where employee_id=?");
                                           psorg.setInt(1,txtEmployeeid);
                                           rsorg=psorg.executeQuery();
                                           if(rsorg.next()) {
                                              System.out.println("step 21");
                                              PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CURRENT_WING_LOG where EMPLOYEE_ID =? ");
                                              psins.setInt(1,txtEmployeeid);
                                              ResultSet rsins=psins.executeQuery();
                                              if(rsins.next()){
                                                  int sid=rsins.getInt("sid");
                                                  if(sid>0) {
                                                      sid+=1;
                                                  }
                                                  else{
                                                      sid=1;
                                                  }
                                                  System.out.println("step 31");
                                                  psins=con.prepareStatement("insert into HRM_EMP_CURRENT_WING_LOG(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,LOG_ID) values (?,?,?,?,?,?,?)");
                                                  psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                  psins.setInt(2,rsorg.getInt("OFFICE_ID"));
                                                  psins.setInt(3,rsorg.getInt("OFFICE_WING_SINO"));
                                                  psins.setDate(4,rsorg.getDate("DATE_EFFECTIVE_FROM"));
                                                  psins.setString(5,rsorg.getString("UPDATED_BY_USER_ID"));
                                                  psins.setTimestamp(6,rsorg.getTimestamp("UPDATED_DATE"));
                                                  psins.setInt(7,sid);
                                                  psins.executeUpdate();
                                                  System.out.println("step 41");
                                              }
                                           }
                                           
                                              System.out.println("step 51");
                                              psorg=con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                                              psorg.setInt(1,txtEmployeeid);
                                              psorg.executeUpdate();
                                              System.out.println("step 61");
                                           
                                           /* wing move to log */
                                         
                                          flag=true;
                                     }
                                           System.out.println("ok1");
                                         if(flag)  
                                         {
                                         
                                         System.out.println(txtRel_SLNO);
                                              String sql="select RELIEVAL_REASON_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                              ps=con.prepareStatement(sql);
                                              ps.setInt(1,officeid);
                                              ps.setInt(2,txtRel_SLNO);
                                              rs=ps.executeQuery();
                                              System.out.println("test1");
                                              if(rs.next()) {
                                                String rid=rs.getString("RELIEVAL_REASON_ID");
                                                if(!rid.equalsIgnoreCase("PRO")) {
                                                    String sql1="";
                                                    if(rid.equalsIgnoreCase("TRN")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    else  if(rid.equalsIgnoreCase("DVN")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    else  if(rid.equalsIgnoreCase("LLV")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    else  if(rid.equalsIgnoreCase("DPN")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    else  if(rid.equalsIgnoreCase("STU")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_STU where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    
                                                    if(!(rid.equalsIgnoreCase("SUS") || rid.equalsIgnoreCase("VRS") || rid.equalsIgnoreCase("DIS")|| rid.equalsIgnoreCase("SAN")  || rid.equalsIgnoreCase("DTH")|| rid.equalsIgnoreCase("ABR")|| rid.equalsIgnoreCase("UAL")))
                                                    {
                                                    System.out.println("sql1:"+sql);
                                                    ps=con.prepareStatement(sql1);
                                                    ps.setInt(1,officeid);
                                                    ps.setInt(2,txtRel_SLNO);
                                                    ps.executeUpdate();
                                                    System.out.println("delete is ok");
                                                    }
                                                }
                                              }
                                         
                                              System.out.println("test2");
                                                                           
                                                     cs=con.prepareCall("{call HRM_EMP_RELIEVAL_PRO_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                                                     cs.setInt(1,txtOffId);
                                                     
                                                     cs.setInt(2,txtRel_SLNO);
                                                     cs.setInt(3,txtEmployeeid);
                                                     cs.setDate(4,txtDORelieval);
                                                     cs.setString(5,rad_DORelieval);
                                                     cs.setString(6,cmbReason);
                                                     cs.setString(7,txtRemarks);
                                                           //cs.setString(8,txtRemarks);
                                                      cs.setInt(8,txtP_OffId);
                                                      cs.setInt(9,txtP_desigId);
                                                      cs.setString(10,radP_Repost);
                                                      cs.setDate (11,txtP_proc_Date);
                                                      cs.setString(12,txtP_proc_No);
                                                      
                                                      cs.setString(14,"update");
                                                      cs.setString(15,Proc_Status);
                                                      cs.setInt(16,desid);
                                                       cs.setInt(17,officeid);
                                                       
                                              cs.setString(18,updatedby);
                                              cs.setTimestamp(19,ts);
                                              cs.setString(20,depid);
                                              cs.setString(21,pro_no);
                                              cs.setString(22,ord_no);
                                              cs.setDate(23,ord_dat);
                                              cs.setInt(24,0);
                                                     cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                                                     cs.registerOutParameter(13,java.sql.Types.NUMERIC);
                                                     cs.execute();
                                                     txtRel_SLNO=cs.getInt(2);
                                                     int errcode=cs.getInt(13);
                                                     System.out.println("SQLCODE:::"+errcode);
                                                     if(errcode!=0)
                                                     {
                                                     xml=xml+"<flag>failure</flag>";
                                                     }
                                                     else
                                                      {
                                                          con.commit();
                                                      xml=xml+"<flag>success</flag><relieNo>"+txtRel_SLNO+"</relieNo>";
                                                      sendMessage(response," The Relieval Serial Number  " + txtRel_SLNO + "  has been modified successfully.","ok");   
                                                      }
                                          }
                                          else {
                                              xml=xml+"<flag>failure</flag><flag>There is no joining report for this Relieval report.Cann't Relive the employee.</flag>";
                                          }
                                      }
                                      catch(Exception e) 
                                      {
                                          System.out.println("insert exception  :"+e); 
                                          xml=xml+"<flag>failure</flag>";
                                          try{con.rollback();}catch(Exception e1){System.out.println(e1);}
                                           sendMessage(response,"Exception in updation due to."+e,"ok");   
                                              
                                          }
                                          finally {
                                          try{con.setAutoCommit(true);}catch(Exception e2){System.out.println(e2);}
                                          }
                      
                    }
                    
                    /*   study leave    */
                    
                     else if(cmbReason.equalsIgnoreCase("STU")) 
                     {
                         System.out.println(cmbReason);
                         
                         String strInst_Name=request.getParameter("txtInst_Name");
                         String strInst_Location=request.getParameter("txtInst_Location");
                         String strInst_Course=request.getParameter("txtCourse_Name");
                         Date txtSDate_From=null;
                         String s=null;
                         s=request.getParameter("txtSDate_From");
                         System.out.println("s value"+s);
                         if(!s.equals(""))
                         {
                          String sdD[]=request.getParameter("txtSDate_From").split("/");
                          c=new GregorianCalendar(Integer.parseInt(sdD[2]),Integer.parseInt(sdD[1])-1,Integer.parseInt(sdD[0]));
                          d=c.getTime();
                          txtSDate_From=new Date(d.getTime());
                         }
                         
                         Date txtSDate_To=null;
                         s=null;
                         s=request.getParameter("txtSDate_To");
                         System.out.println("s value"+s);
                         if(!s.equals(""))
                         {
                          String sdD[]=request.getParameter("txtSDate_To").split("/");
                          c=new GregorianCalendar(Integer.parseInt(sdD[2]),Integer.parseInt(sdD[1])-1,Integer.parseInt(sdD[0]));
                          d=c.getTime();
                          txtSDate_To=new Date(d.getTime());
                         }
                         
                         String dep_id="TWAD";
                         System.out.println("dep id..."+dep_id);
                         
                           
                             System.out.println("ok promotion");
                             
                                     try 
                                      {
                                           con.setAutoCommit(false);
                                           boolean flag=true;
                                           System.out.println("proc No:"+Proc_Status);
                                           int desid=0;
                                           desid=Integer.parseInt(request.getParameter("txtdesid"));
                                           int officeid=0;
                                           officeid=Integer.parseInt(request.getParameter("txtofficeid"));
                                           
                                      if(Proc_Status.equalsIgnoreCase("FR"))
                                      {
                                          
                                          
                                          /*   remove the SR Controlling office and moved it to log table  */
                                          System.out.println("step 1");
                                          PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                          "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                          "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                                          psorg.setInt(1,txtEmployeeid);
                                          ResultSet rsorg=psorg.executeQuery();
                                          if(rsorg.next()) {
                                             System.out.println("step 2");
                                             PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                                             psins.setInt(1,txtEmployeeid);
                                             ResultSet rsins=psins.executeQuery();
                                             if(rsins.next()){
                                                 int sid=rsins.getInt("sid");
                                                 if(sid>0) {
                                                     sid+=1;
                                                 }
                                                 else{
                                                     sid=1;
                                                 }
                                                 System.out.println("step 3");
                                                 psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " + 
                                                 "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                                                 psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                 psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                                                 psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                                                 psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                                                 psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                                                 psins.setInt(6,sid);
                                                 psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                                                 psins.setTimestamp(8,rsorg.getTimestamp("UPDATED_DATE"));
                                                 psins.executeUpdate();
                                                 System.out.println("step 4");
                                             }
                                          }
                                          
                                            /* System.out.println("step 5");
                                             psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                                             psorg.setInt(1,txtEmployeeid);
                                             psorg.executeUpdate();*/
                                             System.out.println("step 6");
                                             
                                          psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                                          psorg.setString(1,updatedby);
                                          psorg.setTimestamp(2,ts);
                                          psorg.setInt(3,txtEmployeeid);
                                          psorg.executeUpdate();
                                             System.out.println("step 7");
                                             
                                          if((cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS")|| cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") ||cmbReason.equalsIgnoreCase("RES")))
                                          {
                                          ps=con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                                          ps.setInt(1,txtEmployeeid);
                                          ps.executeUpdate();
                                          System.out.println("step 8 new--------->");
                                          ps.close();
                                          ps=con.prepareStatement("delete from SEC_MST_USER_ROLES where EMPLOYEE_ID=? ");
                                          ps.setInt(1,txtEmployeeid);
                                          ps.executeUpdate();
                                          System.out.println("step 9");
                                          ps.close();
                                          }
                                          else {
                                          
                                          ps=con.prepareStatement("delete from sec_mst_user_roles where employee_id=? and  role_id in (select role_id from sec_mst_roles where role_category is null or role_category='C')");
                                          ps.setInt(1,txtEmployeeid);
                                          ps.executeUpdate();
                                          System.out.println("step 9 new --------->");
                                          ps.close();
                                          }
                                          
                                          
                                       /*   psorg=con.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                                          psorg.setInt(1,txtEmployeeid);
                                          psorg.setInt(2,22);
                                          psorg.setString(3,updatedby);
                                          psorg.setTimestamp(4,ts);
                                          psorg.setInt(5,999);
                                          psorg.executeUpdate();
                                          System.out.println("step 10");*/
                                          /* End of the comment:   remove the SR Controlling office and moved it to log table  */
                                          
                                           /* move the wing to log wing */
                                            psorg=con.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE from HRM_EMP_CURRENT_WING where employee_id=?");
                                            psorg.setInt(1,txtEmployeeid);
                                            rsorg=psorg.executeQuery();
                                            if(rsorg.next()) {
                                               System.out.println("step 21");
                                               PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CURRENT_WING_LOG where EMPLOYEE_ID =? ");
                                               psins.setInt(1,txtEmployeeid);
                                               ResultSet rsins=psins.executeQuery();
                                               if(rsins.next()){
                                                   int sid=rsins.getInt("sid");
                                                   if(sid>0) {
                                                       sid+=1;
                                                   }
                                                   else{
                                                       sid=1;
                                                   }
                                                   System.out.println("step 31");
                                                   psins=con.prepareStatement("insert into HRM_EMP_CURRENT_WING_LOG(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,LOG_ID) values (?,?,?,?,?,?,?)");
                                                   psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                   psins.setInt(2,rsorg.getInt("OFFICE_ID"));
                                                   psins.setInt(3,rsorg.getInt("OFFICE_WING_SINO"));
                                                   psins.setDate(4,rsorg.getDate("DATE_EFFECTIVE_FROM"));
                                                   psins.setString(5,rsorg.getString("UPDATED_BY_USER_ID"));
                                                   psins.setTimestamp(6,rsorg.getTimestamp("UPDATED_DATE"));
                                                   psins.setInt(7,sid);
                                                   psins.executeUpdate();
                                                   System.out.println("step 41");
                                               }
                                            }
                                            
                                               System.out.println("step 51");
                                               psorg=con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                                               psorg.setInt(1,txtEmployeeid);
                                               psorg.executeUpdate();
                                               System.out.println("step 61");
                                            
                                            /* wing move to log */
                                            
                                          
                                           flag=true;
                                      }
                                            
                                          if(flag)  
                                          {
                                          System.out.println("here is ok");
                                               String sql="select RELIEVAL_REASON_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                               ps=con.prepareStatement(sql);
                                               ps.setInt(1,officeid);
                                               ps.setInt(2,txtRel_SLNO);
                                               rs=ps.executeQuery();
                                               
                                               if(rs.next()) {
                                                 String rid=rs.getString("RELIEVAL_REASON_ID");
                                                 if(!rid.equalsIgnoreCase("STU")) {
                                                     String sql1="";
                                                     if(rid.equalsIgnoreCase("TRN")) {
                                                         sql1="delete from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                     }
                                                     else  if(rid.equalsIgnoreCase("DVN")) {
                                                         sql1="delete from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                     }
                                                     else  if(rid.equalsIgnoreCase("LLV")) {
                                                         sql1="delete from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                     }
                                                     else  if(rid.equalsIgnoreCase("DPN")) {
                                                         sql1="delete from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                     }
                                                     else  if(rid.equalsIgnoreCase("PRO")) {
                                                         sql1="delete from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                     }
                                                     
                                                     if(!(rid.equalsIgnoreCase("SUS") || rid.equalsIgnoreCase("VRS") || rid.equalsIgnoreCase("DIS")|| rid.equalsIgnoreCase("SAN")  || rid.equalsIgnoreCase("DTH")|| rid.equalsIgnoreCase("ABR")))
                                                     {
                                                     System.out.println("sql1:"+sql);
                                                     ps=con.prepareStatement(sql1);
                                                     ps.setInt(1,officeid);
                                                     ps.setInt(2,txtRel_SLNO);
                                                     ps.executeUpdate();
                                                     System.out.println("delete is ok");
                                                     }
                                                 }
                                               }
                                          
                                               System.out.println("here is ok1");                   
                                               cs=con.prepareCall("{call HRM_EMP_RELIEVAL_SDU_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ;
                                               cs.setInt(1,txtOffId);
                                               cs.setInt(2,txtRel_SLNO);
                                               cs.setInt(3,txtEmployeeid);
                                               cs.setDate(4,txtDORelieval);
                                               cs.setString(5,rad_DORelieval);
                                               cs.setString(6,cmbReason);
                                               cs.setString(7,txtRemarks);
                                               
                                               cs.setString(8,strInst_Name);
                                               cs.setString(9,strInst_Location);
                                               cs.setString (10,strInst_Course);
                                               cs.setDate(11,txtSDate_From);
                                               cs.setDate(12,txtSDate_To);
                                               
                                               cs.setString(14,"update");
                                               cs.setString(15,Proc_Status);
                                               cs.setInt(16,desid);
                                               //cs.setInt(16,officeid);
                                              
                                               cs.setInt(17,officeid);
                                               cs.setString(18,updatedby);
                                               cs.setTimestamp(19,ts);
                                               cs.setString(20,dep_id);
                                               cs.setString(21,pro_no);
                                               cs.setString(22,ord_no);
                                               cs.setDate(23,ord_dat);
                                               cs.setInt(24,0);
                                               cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                                               cs.registerOutParameter(13,java.sql.Types.NUMERIC);
                                               cs.execute();
                                               
                                               txtRel_SLNO=cs.getInt(2);
                                               int errcode=cs.getInt(13);
                                               
                                                      System.out.println("SQLCODE:::"+errcode);
                                                      if(errcode!=0)
                                                      {
                                                           xml=xml+"<flag>failure</flag>";
                                                      }
                                                      else
                                                       {
                                                           con.commit();
                                                       xml=xml+"<flag>success</flag><relieNo>"+txtRel_SLNO+"</relieNo>";
                                                       sendMessage(response," The Relieval Serial Number  " + txtRel_SLNO + "  has been modified successfully.","ok");   
                                                       }
                                           }
                                           else {
                                               xml=xml+"<flag>failure</flag><flag>There is no joining report for this Relieval report.Cann't Relive the employee.</flag>";
                                           }
                                       }
                                       catch(Exception e) 
                                       {
                                           System.out.println("insert exception  :"+e); 
                                           xml=xml+"<flag>failure</flag>";
                                           try{con.rollback();}catch(Exception e1){System.out.println(e1);}
                                            sendMessage(response,"Exception in updation due to."+e,"ok");   
                                               
                                           }
                                           finally {
                                           try{con.setAutoCommit(true);}catch(Exception e2){System.out.println(e2);}
                                           }
                       
                     }
                    
                    /*  study leave    */
                    else if(cmbReason.equalsIgnoreCase("LLV")) 
                    {
                        System.out.println(cmbReason);
                         String cmbLL_TypeId=null; int i=0;
                        cmbLL_TypeId=(request.getParameter("cmbLL_TypeId"));
                         String txtLL_purpose=null;
                        txtLL_purpose=(request.getParameter("txtLL_purpose"));
                        
                         Date txtL_Period_From=null;
                        String s=null;
                        s=request.getParameter("txtL_Period_From");
                        System.out.println("s value"+s);
                        if(!s.equals(""))
                        {
                         String sdD[]=request.getParameter("txtL_Period_From").split("/");
                         c=new GregorianCalendar(Integer.parseInt(sdD[2]),Integer.parseInt(sdD[1])-1,Integer.parseInt(sdD[0]));
                         d=c.getTime();
                         txtL_Period_From=new Date(d.getTime());
                        }
                        Date txtL_Period_To=null;
                        s=null;
                        s=request.getParameter("txtL_Period_To");
                        System.out.println("s value"+s);
                        if(!s.equals(""))
                        {
                        String sdD[]=request.getParameter("txtL_Period_To").split("/");
                        c=new GregorianCalendar(Integer.parseInt(sdD[2]),Integer.parseInt(sdD[1])-1,Integer.parseInt(sdD[0]));
                        d=c.getTime();
                        txtL_Period_To=new Date(d.getTime());
                        }
                        
                        String dep_id="TWAD";
                        System.out.println("dep id..."+dep_id);
                         
                        
                                    try 
                                     {
                                          con.setAutoCommit(false);
                                          int desid=0;
                                          desid=Integer.parseInt(request.getParameter("txtdesid"));
                                          int officeid=0;
                                          officeid=Integer.parseInt(request.getParameter("txtofficeid"));
                                          
                                          boolean flag=true;
                                          System.out.println("proc No:"+Proc_Status);
                                          if(Proc_Status.equalsIgnoreCase("FR"))
                                          {
                                              
                                              
                                              /*   remove the SR Controlling office and moved it to log table  */
                                              System.out.println("step 1");
                                              PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                              "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                              "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                                              psorg.setInt(1,txtEmployeeid);
                                              ResultSet rsorg=psorg.executeQuery();
                                              if(rsorg.next()) {
                                                 System.out.println("step 2");
                                                 PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                                                 psins.setInt(1,txtEmployeeid);
                                                 ResultSet rsins=psins.executeQuery();
                                                 if(rsins.next()){
                                                     int sid=rsins.getInt("sid");
                                                     if(sid>0) {
                                                         sid+=1;
                                                     }
                                                     else{
                                                         sid=1;
                                                     }
                                                     System.out.println("step 3");
                                                     psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " + 
                                                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                                                     psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                     psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                                                     psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                                                     psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                                                     psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                                                     psins.setInt(6,sid);
                                                     psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                                                     psins.setTimestamp(8,rsorg.getTimestamp("UPDATED_DATE"));
                                                     psins.executeUpdate();
                                                     System.out.println("step 4");
                                                 }
                                              }
                                              
                                                 System.out.println("step 5");
                                                 psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                                                 psorg.setInt(1,txtEmployeeid);
                                                 psorg.executeUpdate();
                                                 System.out.println("step 6");
                                                 
                                              psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                                              psorg.setString(1,updatedby);
                                              psorg.setTimestamp(2,ts);
                                              psorg.setInt(3,txtEmployeeid);
                                              psorg.executeUpdate();
                                                 System.out.println("step 7");
                                                 
                                              if((cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS")|| cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") ||cmbReason.equalsIgnoreCase("RES")))
                                              {
                                              ps=con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                                              ps.setInt(1,txtEmployeeid);
                                              ps.executeUpdate();
                                              System.out.println("step 8 new--------->");
                                              ps.close();
                                              ps=con.prepareStatement("delete from SEC_MST_USER_ROLES where EMPLOYEE_ID=? ");
                                              ps.setInt(1,txtEmployeeid);
                                              ps.executeUpdate();
                                              System.out.println("step 9");
                                              ps.close();
                                              }
                                              else {
                                              
                                              ps=con.prepareStatement("delete from sec_mst_user_roles where employee_id=? and  role_id in (select role_id from sec_mst_roles where role_category is null or role_category='C')");
                                              ps.setInt(1,txtEmployeeid);
                                              ps.executeUpdate();
                                              System.out.println("step 9 new --------->");
                                              ps.close();
                                              }
                                              
                                              
                                           /*   psorg=con.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                                              psorg.setInt(1,txtEmployeeid);
                                              psorg.setInt(2,22);
                                              psorg.setString(3,updatedby);
                                              psorg.setTimestamp(4,ts);
                                              psorg.setInt(5,999);
                                              psorg.executeUpdate();
                                              System.out.println("step 10");*/
                                              /* End of the comment:   remove the SR Controlling office and moved it to log table  */
                                              
                                               /* move the wing to log wing */
                                                psorg=con.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE from HRM_EMP_CURRENT_WING where employee_id=?");
                                                psorg.setInt(1,txtEmployeeid);
                                                rsorg=psorg.executeQuery();
                                                if(rsorg.next()) {
                                                   System.out.println("step 21");
                                                   PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CURRENT_WING_LOG where EMPLOYEE_ID =? ");
                                                   psins.setInt(1,txtEmployeeid);
                                                   ResultSet rsins=psins.executeQuery();
                                                   if(rsins.next()){
                                                       int sid=rsins.getInt("sid");
                                                       if(sid>0) {
                                                           sid+=1;
                                                       }
                                                       else{
                                                           sid=1;
                                                       }
                                                       System.out.println("step 31");
                                                       psins=con.prepareStatement("insert into HRM_EMP_CURRENT_WING_LOG(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,LOG_ID) values (?,?,?,?,?,?,?)");
                                                       psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                       psins.setInt(2,rsorg.getInt("OFFICE_ID"));
                                                       psins.setInt(3,rsorg.getInt("OFFICE_WING_SINO"));
                                                       psins.setDate(4,rsorg.getDate("DATE_EFFECTIVE_FROM"));
                                                       psins.setString(5,rsorg.getString("UPDATED_BY_USER_ID"));
                                                       psins.setTimestamp(6,rsorg.getTimestamp("UPDATED_DATE"));
                                                       psins.setInt(7,sid);
                                                       psins.executeUpdate();
                                                       System.out.println("step 41");
                                                   }
                                                }
                                                
                                                   System.out.println("step 51");
                                                   psorg=con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                                                   psorg.setInt(1,txtEmployeeid);
                                                   psorg.executeUpdate();
                                                   System.out.println("step 61");
                                                
                                                /* wing move to log */
                                              
                                              
                                          }
                                           
                                          if(flag)
                                          {
                                          
                                              String sql="select RELIEVAL_REASON_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                              ps=con.prepareStatement(sql);
                                              ps.setInt(1,officeid);
                                              ps.setInt(2,txtRel_SLNO);
                                              rs=ps.executeQuery();
                                              if(rs.next()) {
                                                String rid=rs.getString("RELIEVAL_REASON_ID");
                                                if(!rid.equalsIgnoreCase("LLV")) {
                                                    String sql1="";
                                                    if(rid.equalsIgnoreCase("TRN")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    else  if(rid.equalsIgnoreCase("DVN")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    else  if(rid.equalsIgnoreCase("PRO")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    else  if(rid.equalsIgnoreCase("DPN")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    else  if(rid.equalsIgnoreCase("STU")) {
                                                        sql1="delete from HRM_EMP_RELIEVAL_STU where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                                    }
                                                    if(!(rid.equalsIgnoreCase("SUS") || rid.equalsIgnoreCase("VRS") || rid.equalsIgnoreCase("DIS")|| rid.equalsIgnoreCase("SAN") || rid.equalsIgnoreCase("DTH")|| rid.equalsIgnoreCase("ABR")))
                                                    {
                                                    System.out.println("sql1:"+sql);
                                                    ps=con.prepareStatement(sql1);
                                                    ps.setInt(1,officeid);
                                                    ps.setInt(2,txtRel_SLNO);
                                                    ps.executeUpdate();
                                                    System.out.println("delete is ok");
                                                    }
                                                }
                                              }
                                          
                                          
                                          
                                          
                                         cs=con.prepareCall("{call HRM_EMP_RELIEVAL_LLV_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                                         cs.setInt(1,txtOffId);
                                           cs.setInt(2,txtRel_SLNO);
                                         cs.setInt(3,txtEmployeeid);
                                         cs.setDate(4,txtDORelieval);
                                         cs.setString(5,rad_DORelieval);
                                         cs.setString(6,cmbReason);
                                         cs.setString(7,txtRemarks);
                                               //cs.setString(8,txtRemarks);
                                          cs.setString(8,cmbLL_TypeId);
                                          cs.setString(9,txtLL_purpose);
                                          cs.setDate (10,txtL_Period_From);
                                          cs.setDate(11,txtL_Period_To);
                                          cs.setString(13,"update");
                                          cs.setString(14,Proc_Status);
                                           cs.setInt(15,desid);
                                              cs.setInt(16,officeid);
                                              
                                              cs.setString(17,updatedby);
                                              cs.setTimestamp(18,ts);
                                              cs.setString(19,dep_id);
                                              cs.setString(20,pro_no);
                                              cs.setString(21,ord_no);
                                              cs.setDate(22,ord_dat);
                                              cs.setInt(23, 0);
                                         cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                                         cs.registerOutParameter(12,java.sql.Types.NUMERIC);
                                         cs.execute();
                                         txtRel_SLNO=cs.getInt(2);
                                         int errcode=cs.getInt(12);
                                         System.out.println("SQLCODE:::"+errcode);
                                         if(errcode!=0)
                                         {
                                         xml=xml+"<flag>failure</flag>";
                                         }
                                         else
                                          {
                                              con.commit();
                                          xml=xml+"<flag>success</flag><relieNo>"+txtRel_SLNO+"</relieNo>";
                                          sendMessage(response," The Relieval Serial Number  " + txtRel_SLNO + "  has been modified successfully.","ok");   
                                          }
                                          }
                                          else {
                                              xml=xml+"<flag>failure</flag><flag>There is no joining report for this Relieval report.Cann't Relive the employee.</flag>";
                                          }
                                      }
                                      catch(Exception e) 
                                      {
                                          System.out.println("insert exception  :"+e); 
                                          sendMessage(response,"Exception in updation due to."+e,"ok");   
                                          xml=xml+"<flag>failure</flag>";
                                          try{con.rollback();}catch(Exception e1){System.out.println(e1);}
                                          }
                                          finally {
                                          try{con.setAutoCommit(true);}catch(Exception e2){System.out.println(e2);}
                                          }
                     
                    }
                    else if(cmbReason.equalsIgnoreCase("DVN")) 
                    {
                        System.out.println("DVN"+cmbReason);
                        int txtDv_OffId=0,i=0;
                        try{ txtDv_OffId=Integer.parseInt(request.getParameter("txtDv_OffId"));}catch(Exception e){}
                        Date txtDv_Date=null;
                        String s=null;
                        s=request.getParameter("txtDv_Date");
                        System.out.println("s value"+s);
                        if(!s.equals(""))
                        {
                        String sdD[]=request.getParameter("txtDv_Date").split("/");
                        c=new GregorianCalendar(Integer.parseInt(sdD[2]),Integer.parseInt(sdD[1])-1,Integer.parseInt(sdD[0]));
                        d=c.getTime();
                        txtDv_Date=new Date(d.getTime());
                        }
                        
                                    try 
                                     {
                                          con.setAutoCommit(false);
                                          int desid=0;
                                          desid=Integer.parseInt(request.getParameter("txtdesid"));
                                          int officeid=0;
                                          officeid=Integer.parseInt(request.getParameter("txtofficeid"));
                                          
                                          boolean flag=true;
                                              System.out.println("proc No:"+Proc_Status);
                                         if(Proc_Status.equalsIgnoreCase("FR"))
                                         {
                                               
                                               
                                               /*   remove the SR Controlling office and moved it to log table  */
                                               System.out.println("step 1");
                                               PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                               "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                               "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                                               psorg.setInt(1,txtEmployeeid);
                                               ResultSet rsorg=psorg.executeQuery();
                                               if(rsorg.next()) {
                                                  System.out.println("step 2");
                                                  PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                                                  psins.setInt(1,txtEmployeeid);
                                                  ResultSet rsins=psins.executeQuery();
                                                  if(rsins.next()){
                                                      int sid=rsins.getInt("sid");
                                                      if(sid>0) {
                                                          sid+=1;
                                                      }
                                                      else{
                                                          sid=1;
                                                      }
                                                      System.out.println("step 3");
                                                      psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " + 
                                                      "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                                                      psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                      psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                                                      psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                                                      psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                                                      psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                                                      psins.setInt(6,sid);
                                                      psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                                                      psins.setTimestamp(8,rsorg.getTimestamp("UPDATED_DATE"));
                                                      psins.executeUpdate();
                                                      System.out.println("step 4");
                                                  }
                                               }
                                               
                                                  System.out.println("step 5");
                                                  psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                                                  psorg.setInt(1,txtEmployeeid);
                                                  psorg.executeUpdate();
                                                  System.out.println("step 6");
                                                  
                                             psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                                             psorg.setString(1,updatedby);
                                             psorg.setTimestamp(2,ts);
                                             psorg.setInt(3,txtEmployeeid);
                                             psorg.executeUpdate();
                                                  System.out.println("step 7");
                                                  
                                             if((cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS")|| cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") ||cmbReason.equalsIgnoreCase("RES")))
                                             {
                                             ps=con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                                             ps.setInt(1,txtEmployeeid);
                                             ps.executeUpdate();
                                             System.out.println("step 8 new--------->");
                                             ps.close();
                                             ps=con.prepareStatement("delete from SEC_MST_USER_ROLES where EMPLOYEE_ID=? ");
                                             ps.setInt(1,txtEmployeeid);
                                             ps.executeUpdate();
                                             System.out.println("step 9");
                                             ps.close();
                                             }
                                             else {
                                             
                                             ps=con.prepareStatement("delete from sec_mst_user_roles where employee_id=? and  role_id in (select role_id from sec_mst_roles where role_category is null or role_category='C')");
                                             ps.setInt(1,txtEmployeeid);
                                             ps.executeUpdate();
                                             System.out.println("step 9 new --------->");
                                             ps.close();
                                             }
                                             
                                             
                                          /*   psorg=con.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                                             psorg.setInt(1,txtEmployeeid);
                                             psorg.setInt(2,22);
                                             psorg.setString(3,updatedby);
                                             psorg.setTimestamp(4,ts);
                                             psorg.setInt(5,999);
                                             psorg.executeUpdate();
                                             System.out.println("step 10");*/
                                               /* End of the comment:   remove the SR Controlling office and moved it to log table  */
                                               
                                               
                                                /* move the wing to log wing */
                                                 psorg=con.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE from HRM_EMP_CURRENT_WING where employee_id=?");
                                                 psorg.setInt(1,txtEmployeeid);
                                                 rsorg=psorg.executeQuery();
                                                 if(rsorg.next()) {
                                                    System.out.println("step 21");
                                                    PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CURRENT_WING_LOG where EMPLOYEE_ID =? ");
                                                    psins.setInt(1,txtEmployeeid);
                                                    ResultSet rsins=psins.executeQuery();
                                                    if(rsins.next()){
                                                        int sid=rsins.getInt("sid");
                                                        if(sid>0) {
                                                            sid+=1;
                                                        }
                                                        else{
                                                            sid=1;
                                                        }
                                                        System.out.println("step 31");
                                                        psins=con.prepareStatement("insert into HRM_EMP_CURRENT_WING_LOG(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,LOG_ID) values (?,?,?,?,?,?,?)");
                                                        psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                                        psins.setInt(2,rsorg.getInt("OFFICE_ID"));
                                                        psins.setInt(3,rsorg.getInt("OFFICE_WING_SINO"));
                                                        psins.setDate(4,rsorg.getDate("DATE_EFFECTIVE_FROM"));
                                                        psins.setString(5,rsorg.getString("UPDATED_BY_USER_ID"));
                                                        psins.setTimestamp(6,rsorg.getTimestamp("UPDATED_DATE"));
                                                        psins.setInt(7,sid);
                                                        psins.executeUpdate();
                                                        System.out.println("step 41");
                                                    }
                                                 }
                                                 
                                                    System.out.println("step 51");
                                                    psorg=con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                                                    psorg.setInt(1,txtEmployeeid);
                                                    psorg.executeUpdate();
                                                    System.out.println("step 61");
                                                 
                                                 /* wing move to log */
                                               
                                         }
                                               
                                             if(flag)  
                                             {
                                             
                                       String sql="select RELIEVAL_REASON_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                       ps=con.prepareStatement(sql);
                                       ps.setInt(1,officeid);
                                       ps.setInt(2,txtRel_SLNO);
                                       rs=ps.executeQuery();
                                       if(rs.next()) {
                                         String rid=rs.getString("RELIEVAL_REASON_ID");
                                         if(!rid.equalsIgnoreCase("DVN")) {
                                             String sql1="";
                                             if(rid.equalsIgnoreCase("TRN")) {
                                                 sql1="delete from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                             }
                                             else  if(rid.equalsIgnoreCase("LLV")) {
                                                 sql1="delete from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                             }
                                             else  if(rid.equalsIgnoreCase("PRO")) {
                                                 sql1="delete from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                             }
                                             else  if(rid.equalsIgnoreCase("DPN")) {
                                                 sql1="delete from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                             }
                                             else  if(rid.equalsIgnoreCase("STU")) {
                                                 sql1="delete from HRM_EMP_RELIEVAL_STU where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                             }
                                             if(!(rid.equalsIgnoreCase("SUS") || rid.equalsIgnoreCase("VRS") || rid.equalsIgnoreCase("DIS")|| rid.equalsIgnoreCase("SAN")  || rid.equalsIgnoreCase("DTH")|| rid.equalsIgnoreCase("ABR") || rid.equalsIgnoreCase("UAL")))
                                             {
                                             System.out.println("sql1:"+sql);
                                             ps=con.prepareStatement(sql1);
                                             ps.setInt(1,officeid);
                                             ps.setInt(2,txtRel_SLNO);
                                             ps.executeUpdate();
                                             System.out.println("delete is ok");
                                             }
                                         }
                                       }
                                             
                                         System.out.println("here is ok");    
                                         cs=con.prepareCall("{call HRM_EMP_RELIEVAL_DVN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                                         cs.setInt(1,txtOffId);
                                           cs.setInt(2,txtRel_SLNO);
                                         cs.setInt(3,txtEmployeeid);
                                         cs.setDate(4,txtDORelieval);
                                         cs.setString(5,rad_DORelieval);
                                         cs.setString(6,cmbReason);
                                         cs.setString(7,txtRemarks);
                                               //cs.setString(8,txtRemarks);
                                          cs.setInt(8,txtDv_OffId);
                                          cs.setDate(9,txtDv_Date);
                                          cs.setString(11,"update");
                                          cs.setString(12,Proc_Status);
                                          
                                       cs.setString(13,updatedby);
                                       cs.setTimestamp(14,ts);
                                       cs.setInt(15, 0);
                                      // cs.setInt(13,desid);
                                      // cs.setInt(14,officeid);
                                         cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                                         cs.registerOutParameter(10,java.sql.Types.NUMERIC);
                                         cs.execute();
                                         txtRel_SLNO=cs.getInt(2);
                                         int errcode=cs.getInt(10);
                                         System.out.println("SQLCODE:::"+errcode);
                                         if(errcode!=0)
                                         {
                                         xml=xml+"<flag>failure</flag>";
                                         }
                                         else
                                          {
                                              con.commit();
                                          xml=xml+"<flag>success</flag><relieNo>"+txtRel_SLNO+"</relieNo>";
                                          sendMessage(response," The Relieval Serial Number  " + txtRel_SLNO + "  has been modified successfully.","ok");   
                                          }
                                   }
                                    else {
                                        xml=xml+"<flag>failure</flag><flag>There is no joining report for this Relieval report.Cann't Relive the employee.</flag>";
                                    }
                                      }
                                      catch(Exception e) 
                                      {
                                          System.out.println("insert exception  :"+e); 
                                          sendMessage(response,"Exception in updation due to."+e,"ok");   
                                          xml=xml+"<flag>failure</flag>";
                                          try{con.rollback();}catch(Exception e1){System.out.println(e1);}
                                          }
                                          finally {
                                          try{con.setAutoCommit(true);}catch(Exception e2){System.out.println(e2);}
                                          }
                       
                    }
                   else if(cmbReason.equalsIgnoreCase("SUS") || cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS") || cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") || cmbReason.equalsIgnoreCase("ABR") || cmbReason.equalsIgnoreCase("MEV")||cmbReason.equalsIgnoreCase("UAL")) 
                   {
                   System.out.println("SUSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                       int i=0;
                           try 
                            {
                                 con.setAutoCommit(false);
                                 int desid=0;
                                 desid=Integer.parseInt(request.getParameter("txtdesid"));
                                 int officeid=0;
                                 officeid=Integer.parseInt(request.getParameter("txtofficeid"));
                                 
                                 boolean flag=true;
                                     System.out.println("proc No:"+Proc_Status);
                                 if(Proc_Status.equalsIgnoreCase("FR"))
                                 {
                                     
                                     
                                     /*   remove the SR Controlling office and moved it to log table  */
                                     System.out.println("step 1");
                                     PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                     "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                     "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                                     psorg.setInt(1,txtEmployeeid);
                                     ResultSet rsorg=psorg.executeQuery();
                                     if(rsorg.next()) {
                                        System.out.println("step 2");
                                        PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                                        psins.setInt(1,txtEmployeeid);
                                        ResultSet rsins=psins.executeQuery();
                                        if(rsins.next()){
                                            int sid=rsins.getInt("sid");
                                            if(sid>0) {
                                                sid+=1;
                                            }
                                            else{
                                                sid=1;
                                            }
                                            System.out.println("step 3");
                                            psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " + 
                                            "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                                            psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                            psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                                            psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                                            psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                                            psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                                            psins.setInt(6,sid);
                                            psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                                            psins.setTimestamp(8,rsorg.getTimestamp("UPDATED_DATE"));
                                            psins.executeUpdate();
                                            System.out.println("step 4");
                                        }
                                     }
                                     System.out.println(cmbReason+"jjjjjjjjjj");
                                     if(!cmbReason.equalsIgnoreCase("SUS") ) 
                                     {
                                        System.out.println("step 5");
                                        psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                                        psorg.setInt(1,txtEmployeeid);
                                        psorg.executeUpdate();
                                        System.out.println("step 6");
                                     }
                                     else
                                       System.out.println("others");
                                     psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                                     psorg.setString(1,updatedby);
                                     psorg.setTimestamp(2,ts);
                                     psorg.setInt(3,txtEmployeeid);
                                     psorg.executeUpdate();
                                        System.out.println("step 7");
                                    
                                     
                                     if((cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS")|| cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") ||cmbReason.equalsIgnoreCase("RES")))
                                     {
                                     ps=con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                                     ps.setInt(1,txtEmployeeid);
                                     ps.executeUpdate();
                                     System.out.println("step 8 new--------->");
                                     ps.close();
                                     ps=con.prepareStatement("delete from SEC_MST_USER_ROLES where EMPLOYEE_ID=? ");
                                     ps.setInt(1,txtEmployeeid);
                                     ps.executeUpdate();
                                     System.out.println("step 9");
                                     ps.close();
                                     }
                                     else {
                                     
                                     ps=con.prepareStatement("delete from sec_mst_user_roles where employee_id=? and  role_id in (select role_id from sec_mst_roles where role_category is null or role_category='C')");
                                     ps.setInt(1,txtEmployeeid);
                                     ps.executeUpdate();
                                     System.out.println("step 9 new --------->");
                                     ps.close();
                                     }
                                     
                                     
                                    /* psorg=con.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                                     psorg.setInt(1,txtEmployeeid);
                                     psorg.setInt(2,22);
                                     psorg.setString(3,updatedby);
                                     psorg.setTimestamp(4,ts);
                                     psorg.setInt(5,999);
                                     psorg.executeUpdate();
                                     System.out.println("step 10");*/
                                     /* End of the comment:   remove the SR Controlling office and moved it to log table  */
                                     
                                      /* move the wing to log wing */
                                       psorg=con.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE from HRM_EMP_CURRENT_WING where employee_id=?");
                                       psorg.setInt(1,txtEmployeeid);
                                       rsorg=psorg.executeQuery();
                                       if(rsorg.next()) {
                                          System.out.println("step 21");
                                          PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CURRENT_WING_LOG where EMPLOYEE_ID =? ");
                                          psins.setInt(1,txtEmployeeid);
                                          ResultSet rsins=psins.executeQuery();
                                          if(rsins.next()){
                                              int sid=rsins.getInt("sid");
                                              if(sid>0) {
                                                  sid+=1;
                                              }
                                              else{
                                                  sid=1;
                                              }
                                              System.out.println("step 31");
                                              psins=con.prepareStatement("insert into HRM_EMP_CURRENT_WING_LOG(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,LOG_ID) values (?,?,?,?,?,?,?)");
                                              psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                              psins.setInt(2,rsorg.getInt("OFFICE_ID"));
                                              psins.setInt(3,rsorg.getInt("OFFICE_WING_SINO"));
                                              psins.setDate(4,rsorg.getDate("DATE_EFFECTIVE_FROM"));
                                              psins.setString(5,rsorg.getString("UPDATED_BY_USER_ID"));
                                              psins.setTimestamp(6,rsorg.getTimestamp("UPDATED_DATE"));
                                              psins.setInt(7,sid);
                                              psins.executeUpdate();
                                              System.out.println("step 41");
                                          }
                                       }
                                       
                                          System.out.println("step 51");
                                          psorg=con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                                          psorg.setInt(1,txtEmployeeid);
                                          psorg.executeUpdate();
                                          System.out.println("step 61");
                                       
                                       /* wing move to log */
                                       
                                     
                                     
                                 }
                                      
                                    if(flag)  
                                    {
                                    
                                   String sql="select RELIEVAL_REASON_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                   ps=con.prepareStatement(sql);
                                   ps.setInt(1,officeid);
                                   ps.setInt(2,txtRel_SLNO);
                                   rs=ps.executeQuery();
                                   if(rs.next()) {
                                     String rid=rs.getString("RELIEVAL_REASON_ID");
                                     if(!(rid.equalsIgnoreCase("SUS")||rid.equalsIgnoreCase("VRS") || rid.equalsIgnoreCase("DIS") || rid.equalsIgnoreCase("SAN") || rid.equalsIgnoreCase("DTH") || rid.equalsIgnoreCase("RES")) ) {
                                         String sql1="";
                                         if(rid.equalsIgnoreCase("TRN")) { 
                                             sql1="delete from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                         }
                                         else  if(rid.equalsIgnoreCase("LLV")) {
                                             sql1="delete from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                         }
                                         else  if(rid.equalsIgnoreCase("PRO")) {
                                             sql1="delete from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                         }
                                         else  if(rid.equalsIgnoreCase("DPN")) {
                                             sql1="delete from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                         }
                                         else  if(rid.equalsIgnoreCase("DVN")) {
                                             sql1="delete from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                         }
                                         else  if(rid.equalsIgnoreCase("STU")) {
                                             sql1="delete from HRM_EMP_RELIEVAL_STU where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                         }
                                         if(!(rid.equalsIgnoreCase("SUS") || rid.equalsIgnoreCase("VRS") || rid.equalsIgnoreCase("DIS")|| rid.equalsIgnoreCase("SAN") || rid.equalsIgnoreCase("DTH")|| rid.equalsIgnoreCase("ABR") || rid.equalsIgnoreCase("RES")|| rid.equalsIgnoreCase("UAL")))
                                         {
                                         System.out.println("sql1:"+sql);
                                         ps=con.prepareStatement(sql1);
                                         ps.setInt(1,officeid);
                                         ps.setInt(2,txtRel_SLNO);
                                         ps.executeUpdate();
                                         System.out.println("delete is ok");
                                         }
                                     }
                                   }
                                    
                                   String dep_id="TWAD";
                                   System.out.println("dep id..."+dep_id);  
                               
                                    
                                 System.out.println("Befor call HRM_EMP_RELIEVAL_OTH_PROC");   
                                cs=con.prepareCall("{call HRM_EMP_RELIEVAL_OTH_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                                cs.setInt(1,txtOffId);
                                   cs.setInt(2,txtRel_SLNO);
                                cs.setInt(3,txtEmployeeid);
                                cs.setDate(4,txtDORelieval);
                                cs.setString(5,rad_DORelieval);
                                cs.setString(6,cmbReason);
                                cs.setString(7,txtRemarks);
                                      //cs.setString(8,txtRemarks);
                                 
                                       cs.setString(9,"update");
                                 cs.setString(10,Proc_Status);
                                   cs.setInt(11,desid);
                                   cs.setInt(12,officeid);
                                   
                                   cs.setString(13,updatedby);
                                   cs.setTimestamp(14,ts);
                                   cs.setString(15,dep_id);
                                   cs.setString(16,pro_no);
                                   cs.setString(17,ord_no);
                                   cs.setDate(18,ord_dat);
                                   cs.setInt(19,0);
                                cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                                cs.registerOutParameter(8,java.sql.Types.NUMERIC);
                                cs.execute();
                                txtRel_SLNO=cs.getInt(2);
                                
                                System.out.println("Office Id : "+ txtOffId);
                              
                                System.out.println("Relieval SlNo : "+txtRel_SLNO);
                            
                                System.out.println("Emp Id. : "+ txtEmployeeid); 
                                
                                System.out.println("Date of Relieval"+ txtDORelieval); 
                             
                                System.out.println("Relieval Session"+ rad_DORelieval); 
                                     
                                System.out.println("Reason for Relieval"+cmbReason); 
                                
                                System.out.println("Remarks"+txtRemarks); 
                                  
                                   
                                int errcode=cs.getInt(8);
                                System.out.println("SQLCODE:::"+errcode);
                                if(errcode!=0)
                                {
                                xml=xml+"<flag>failure</flag>";
                                }
                                else
                                 {
                                     con.commit();
                                 xml=xml+"<flag>success</flag><relieNo>"+txtRel_SLNO+"</relieNo>";
                                 sendMessage(response," The Relieval Serial Number  " + txtRel_SLNO + "  has been modified successfully.","ok");   
                                 }
                               }
                               else {
                                   xml=xml+"<flag>failure</flag><flag>There is no joining report for this Relieval report.Cann't Relive the employee.</flag>";
                               }
                             }
                             catch(Exception e) 
                             {
                                 System.out.println("insert exception  :"+e); 
                                   
                                 xml=xml+"<flag>failure</flag>";
                                 try{con.rollback();}catch(Exception e1){System.out.println(e1);}
                                 sendMessage(response,"Exception in updation due to."+e,"ok"); 
                             }
                             finally {
                                 try{con.setAutoCommit(true);}catch(Exception e2){System.out.println(e2);}
                             }
                        
                   }
                   
                    else if(cmbReason.equalsIgnoreCase("RES")) 
                    {
                       // int i=0;
                            try 
                             {
                                  con.setAutoCommit(false);
                                  int desid=0;
                                  desid=Integer.parseInt(request.getParameter("txtdesid"));
                                  int officeid=0;
                                  officeid=Integer.parseInt(request.getParameter("txtofficeid"));
                                  
                                  boolean flag=true;
                                      System.out.println("proc No:"+Proc_Status);
                                  if(Proc_Status.equalsIgnoreCase("FR"))
                                  {
                                      
                                      
                                      /*   remove the SR Controlling office and moved it to log table  */
                                      System.out.println("step 1");
                                      PreparedStatement psorg=con.prepareStatement(" select EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " +
                                      "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE " +
                                      "    from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=?");
                                      psorg.setInt(1,txtEmployeeid);
                                      ResultSet rsorg=psorg.executeQuery();
                                      if(rsorg.next()) {
                                         System.out.println("step 2");
                                         PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CONTROLLING_OFFICE_LOG where EMPLOYEE_ID =? ");
                                         psins.setInt(1,txtEmployeeid);
                                         ResultSet rsins=psins.executeQuery();
                                         if(rsins.next()){
                                             int sid=rsins.getInt("sid");
                                             if(sid>0) {
                                                 sid+=1;
                                             }
                                             else{
                                                 sid=1;
                                             }
                                             System.out.println("step 3");
                                             psins=con.prepareStatement("insert into HRM_EMP_CONTROLLING_OFFICE_LOG(EMPLOYEE_ID,CONTROLLING_OFFICE_ID,CONTROLLING_DEPARTMENT_ID, " + 
                                             "   PROCESS_FLOW_STATUS_ID,CONTROLLING_DATE_FROM,LOG_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
                                             psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                             psins.setInt(2,rsorg.getInt("CONTROLLING_OFFICE_ID"));
                                             psins.setString(3,rsorg.getString("CONTROLLING_DEPARTMENT_ID"));
                                             psins.setString(4,rsorg.getString("PROCESS_FLOW_STATUS_ID"));
                                             psins.setDate(5,rsorg.getDate("CONTROLLING_DATE_FROM"));
                                             psins.setInt(6,sid);
                                             psins.setString(7,rsorg.getString("UPDATED_BY_USER_ID"));
                                             psins.setTimestamp(8,rsorg.getTimestamp("UPDATED_DATE"));
                                             psins.executeUpdate();
                                             System.out.println("step 4");
                                         }
                                      }
                                      
                                         System.out.println("step 5");
                                         psorg=con.prepareStatement("delete from HRM_EMP_CONTROLLING_OFFICE where EMPLOYEE_ID=? ");
                                         psorg.setInt(1,txtEmployeeid);
                                         psorg.executeUpdate();
                                         System.out.println("step 6");
                                         
                                      psorg=con.prepareStatement("update  HRM_EMP_CONTROLLING_OFFICE_TMP set PROCESS_FLOW_STATUS_ID='MD',UPDATED_BY_USER_ID=?,UPDATED_DATE=?   where EMPLOYEE_ID=? ");
                                      psorg.setString(1,updatedby);
                                      psorg.setTimestamp(2,ts);
                                      psorg.setInt(3,txtEmployeeid);
                                      psorg.executeUpdate();
                                         System.out.println("step 7");
                                         
                                      if((cmbReason.equalsIgnoreCase("VRS") || cmbReason.equalsIgnoreCase("DIS")|| cmbReason.equalsIgnoreCase("SAN")  || cmbReason.equalsIgnoreCase("DTH") ||cmbReason.equalsIgnoreCase("RES")))
                                      {
                                      ps=con.prepareStatement("update SEC_MST_USERS set LOGIN_ENABLED='0' where EMPLOYEE_ID=?  ");
                                      ps.setInt(1,txtEmployeeid);
                                      ps.executeUpdate();
                                      System.out.println("step 8 new--------->");
                                      ps.close();
                                      ps=con.prepareStatement("delete from SEC_MST_USER_ROLES where EMPLOYEE_ID=? ");
                                      ps.setInt(1,txtEmployeeid);
                                      ps.executeUpdate();
                                      System.out.println("step 9");
                                      ps.close();
                                      }
                                      else {
                                      
                                      ps=con.prepareStatement("delete from sec_mst_user_roles where employee_id=? and  role_id in (select role_id from sec_mst_roles where role_category is null or role_category='C')");
                                      ps.setInt(1,txtEmployeeid);
                                      ps.executeUpdate();
                                      System.out.println("step 9 new --------->");
                                      ps.close();
                                      }
                                      
                                      
                                     /* psorg=con.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                                      psorg.setInt(1,txtEmployeeid);
                                      psorg.setInt(2,22);
                                      psorg.setString(3,updatedby);
                                      psorg.setTimestamp(4,ts);
                                      psorg.setInt(5,999);
                                      psorg.executeUpdate();
                                      System.out.println("step 10");*/
                                      /* End of the comment:   remove the SR Controlling office and moved it to log table  */
                                      
                                       /* move the wing to log wing */
                                        psorg=con.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE from HRM_EMP_CURRENT_WING where employee_id=?");
                                        psorg.setInt(1,txtEmployeeid);
                                        rsorg=psorg.executeQuery();
                                        if(rsorg.next()) {
                                           System.out.println("step 21");
                                           PreparedStatement psins=con.prepareStatement("select max(LOG_ID) sid from HRM_EMP_CURRENT_WING_LOG where EMPLOYEE_ID =? ");
                                           psins.setInt(1,txtEmployeeid);
                                           ResultSet rsins=psins.executeQuery();
                                           if(rsins.next()){
                                               int sid=rsins.getInt("sid");
                                               if(sid>0) {
                                                   sid+=1;
                                               }
                                               else{
                                                   sid=1;
                                               }
                                               System.out.println("step 31");
                                               psins=con.prepareStatement("insert into HRM_EMP_CURRENT_WING_LOG(EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO,DATE_EFFECTIVE_FROM,UPDATED_BY_USER_ID,UPDATED_DATE,LOG_ID) values (?,?,?,?,?,?,?)");
                                               psins.setInt(1,rsorg.getInt("EMPLOYEE_ID"));
                                               psins.setInt(2,rsorg.getInt("OFFICE_ID"));
                                               psins.setInt(3,rsorg.getInt("OFFICE_WING_SINO"));
                                               psins.setDate(4,rsorg.getDate("DATE_EFFECTIVE_FROM"));
                                               psins.setString(5,rsorg.getString("UPDATED_BY_USER_ID"));
                                               psins.setTimestamp(6,rsorg.getTimestamp("UPDATED_DATE"));
                                               psins.setInt(7,sid);
                                               psins.executeUpdate();
                                               System.out.println("step 41");
                                           }
                                        }
                                        
                                           System.out.println("step 51");
                                           psorg=con.prepareStatement("delete from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=? ");
                                           psorg.setInt(1,txtEmployeeid);
                                           psorg.executeUpdate();
                                           System.out.println("step 61");
                                        
                                        /* wing move to log */
                                        
                                      
                                      
                                  }
                                       
                                     if(flag)  
                                     {
                                     
                                    String sql="select RELIEVAL_REASON_ID from HRM_EMP_RELIEVAL_DETAILS where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                    ps=con.prepareStatement(sql);
                                    ps.setInt(1,officeid);
                                    ps.setInt(2,txtRel_SLNO);
                                    rs=ps.executeQuery();
                                    if(rs.next()) {
                                      String rid=rs.getString("RELIEVAL_REASON_ID");
                                      if(!(rid.equalsIgnoreCase("RES")) ) {
                                          String sql1="";
                                          if(rid.equalsIgnoreCase("TRN")) { 
                                              sql1="delete from HRM_EMP_RELIEVAL_TRN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                          }
                                          else  if(rid.equalsIgnoreCase("LLV")) {
                                              sql1="delete from HRM_EMP_RELIEVAL_LLV where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                          }
                                          else  if(rid.equalsIgnoreCase("PRO")) {
                                              sql1="delete from HRM_EMP_RELIEVAL_PRO where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                          }
                                          else  if(rid.equalsIgnoreCase("DPN")) {
                                              sql1="delete from HRM_EMP_RELIEVAL_DPN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                          }
                                          else  if(rid.equalsIgnoreCase("DVN")) {
                                              sql1="delete from HRM_EMP_RELIEVAL_DVN where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                          }
                                          else  if(rid.equalsIgnoreCase("STU")) {
                                              sql1="delete from HRM_EMP_RELIEVAL_STU where OFFICE_ID=? and RELIEVAL_SLNO=?";
                                          }
                                          if(!(rid.equalsIgnoreCase("RES")))
                                          {
                                          System.out.println("sql1:"+sql);
                                          ps=con.prepareStatement(sql1);
                                          ps.setInt(1,officeid);
                                          ps.setInt(2,txtRel_SLNO);
                                          ps.executeUpdate();
                                          System.out.println("delete is ok");
                                          }
                                      }
                                    }
                                     
                                    String dep_id="TWAD";
                                    System.out.println("dep id..."+dep_id);  
                                
                                    int cflag=0; 
                                  System.out.println("Befor call HRM_EMP_RELIEVAL_OTH_PROC");   
                                 cs=con.prepareCall("{call HRM_EMP_RELIEVAL_RES_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ; 
                                 cs.setInt(1,txtOffId);
                                    cs.setInt(2,txtRel_SLNO);
                                 cs.setInt(3,txtEmployeeid);
                                 cs.setDate(4,txtDORelieval);
                                 cs.setString(5,rad_DORelieval);
                                 cs.setString(6,cmbReason);
                                 cs.setString(7,txtRemarks);
                                       //cs.setString(8,txtRemarks);
                                  
                                        cs.setString(9,"update");
                                  cs.setString(10,Proc_Status);
                                    cs.setInt(11,desid);
                                    cs.setInt(12,officeid);
                                    
                                    cs.setString(13,updatedby);
                                    cs.setTimestamp(14,ts);
                                    cs.setString(15,dep_id);
                                    cs.setInt(16,cflag);
                                    //cs.setString(17,ord_no);
                                    //cs.setDate(18,ord_dat);
                                    
                                 cs.registerOutParameter(2,java.sql.Types.NUMERIC);
                                 cs.registerOutParameter(8,java.sql.Types.NUMERIC);
                                 cs.execute();
                                 txtRel_SLNO=cs.getInt(2);
                                 
                                 System.out.println("Office Id : "+ txtOffId);
                               
                                 System.out.println("Relieval SlNo : "+txtRel_SLNO);
                             
                                 System.out.println("Emp Id. : "+ txtEmployeeid); 
                                 
                                 System.out.println("Date of Relieval"+ txtDORelieval); 
                              
                                 System.out.println("Relieval Session"+ rad_DORelieval); 
                                      
                                 System.out.println("Reason for Relieval"+cmbReason); 
                                 
                                 System.out.println("Remarks"+txtRemarks); 
                                   
                                    
                                 int errcode=cs.getInt(8);
                                 System.out.println("SQLCODE:::"+errcode);
                                 if(errcode!=0)
                                 {
                                 xml=xml+"<flag>failure</flag>";
                                 }
                                 else
                                  {
                                      con.commit();
                                  xml=xml+"<flag>success</flag><relieNo>"+txtRel_SLNO+"</relieNo>";
                                  sendMessage(response," The Relieval Serial Number  " + txtRel_SLNO + "  has been modified successfully.","ok");   
                                  }
                                }
                                else {
                                    xml=xml+"<flag>failure</flag><flag>There is no joining report for this Relieval report.Cann't Relieve the employee.</flag>";
                                }
                              }
                              catch(Exception e) 
                              {
                                  System.out.println("insert exception  :"+e); 
                                    
                                  xml=xml+"<flag>failure</flag>";
                                  try{con.rollback();}catch(Exception e1){System.out.println(e1);}
                                  sendMessage(response,"Exception in updation due to."+e,"ok"); 
                              }
                              finally {
                                  try{con.setAutoCommit(true);}catch(Exception e2){System.out.println(e2);}
                              }
                         
                    }}
                   catch(Exception e)
                   {
                	   System.out.println("Exception"+e);
                   }
                           xml=xml+"</response>";
                           
                
                	
                	
                	
                	
                	
                	// New changes ends
                	
                	
                	
                	
                	
                    xml = xml + "<flag>failure2</flag>";
//                    System.out.println("Failure 2");
//                    sendMessage(response,
//                                "This Employee already has an unfrezeed Record. \n Inserting a new Reilval is not possible",
//                                "ok");
                } else {
                    if (cmbReason.equalsIgnoreCase("TRN")) {
                        System.out.println("TRN");
                        int i = 0;
                        int txtT_OffId = 0;
                        try {
                            txtT_OffId =
                                    Integer.parseInt(request.getParameter("txtT_OffId"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        String radT_Repost = null;
                        radT_Repost = request.getParameter("radT_Repost");
                        Date txtT_proc_Date = null;
                        String s = null;
                        s = request.getParameter("txtT_proc_Date");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdTd[] =
                                request.getParameter("txtT_proc_Date").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdTd[2]),
                         Integer.parseInt(sdTd[1]) - 1,
                         Integer.parseInt(sdTd[0]));
                            d = c.getTime();
                            txtT_proc_Date = new Date(d.getTime());
                        }
                        String txtT_proc_No =
                            request.getParameter("txtT_proc_No");
                        String dep_id = "TWAD";
                        System.out.println("dep_id..." + dep_id);
                        try {
                            System.out.println("inside query" + s);
int cflag=0;
                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_TRN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);
                            cs.setInt(8, txtT_OffId);
                            cs.setString(9, radT_Repost);
                            cs.setDate(10, txtT_proc_Date);
                            cs.setString(11, txtT_proc_No);
                            cs.setString(13, "insert");
                            cs.setString(14, Proc_Status);
                            cs.setInt(15, 0);
                            cs.setInt(16, 0);
                            cs.setString(17, updatedby);
                            cs.setTimestamp(18, ts);
                            cs.setString(19, dep_id);
                            cs.setString(20, pro_no);
                            cs.setString(21, ord_no);
                            cs.setDate(22, ord_dat);
                            cs.setInt(23, cflag);
                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(12,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(12);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";

                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number  " +
                                            txtRel_SLNO +
                                            "  has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else if (cmbReason.equalsIgnoreCase("DPN")) {
                        System.out.println(cmbReason);
                        String txtD_ODep_Id = "";
                        int i = 0;
                        try {
                            txtD_ODep_Id =
                                    request.getParameter("txtD_ODep_Id");
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        int txtD_ODep_OffId = 0;
                        try {
                            txtD_ODep_OffId =
                                    Integer.parseInt(request.getParameter("txtD_ODep_OffId"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        String txtD_Period =
                            request.getParameter("txtD_Period");
                        Date txtD_Date = null;
                        String s = null;
                        s = request.getParameter("txtD_Date");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtD_Date").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtD_Date = new Date(d.getTime());
                        }

                        String dep_id = request.getParameter("txtDept_Id");
                        System.out.println("dep id..." + dep_id);

                        try {
                            System.out.println("inside query" + s);


                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_DPN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            cs.setString(8, txtD_ODep_Id);
                            cs.setInt(9, txtD_ODep_OffId);
                            cs.setString(10, txtD_Period);
                            cs.setDate(11, txtD_Date);
                            cs.setString(13, "insert");
                            cs.setString(14, Proc_Status);
                            cs.setInt(15, 0);
                            cs.setInt(16, 0);

                            cs.setString(17, updatedby);
                            cs.setTimestamp(18, ts);
                            cs.setString(19, dep_id);
                            cs.setString(20, pro_no);
                            cs.setString(21, ord_no);
                            cs.setDate(22, ord_dat);
                            cs.setInt(23,0);
                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(12,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            System.out.println("heloo");
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(12);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    }


                    /*  study leave sk 14/10/2006   */
                    else if (cmbReason.equalsIgnoreCase("STU")) {
                        System.out.println(cmbReason);
                        String txtD_ODep_Id = "";
                        int i = 0;
                        String strInst_Name =
                            request.getParameter("txtInst_Name");
                        String strInst_Location =
                            request.getParameter("txtInst_Location");
                        String strInst_Course =
                            request.getParameter("txtCourse_Name");
                        Date txtSDate_From = null;
                        String s = null;
                        s = request.getParameter("txtSDate_From");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtSDate_From").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtSDate_From = new Date(d.getTime());
                        }

                        Date txtSDate_To = null;
                        s = null;
                        s = request.getParameter("txtSDate_To");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtSDate_To").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtSDate_To = new Date(d.getTime());
                        }

                        String dep_id = "TWAD";
                        System.out.println("dep id..." + dep_id);

                        try {
                            System.out.println("inside query" + s);

                            System.out.println("here is ok1");
                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_SDU_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);

                            cs.setString(8, strInst_Name);
                            cs.setString(9, strInst_Location);
                            cs.setString(10, strInst_Course);
                            cs.setDate(11, txtSDate_From);
                            cs.setDate(12, txtSDate_To);

                            cs.setString(14, "insert");
                            cs.setString(15, Proc_Status);
                            cs.setInt(16, 0);
                            cs.setInt(17, 0);

                            cs.setString(18, updatedby);
                            cs.setTimestamp(19, ts);
                            cs.setString(20, dep_id);
                            cs.setString(21, pro_no);
                            cs.setString(22, ord_no);
                            cs.setDate(23, ord_dat);
                            cs.setInt(24,0);
                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(13,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            System.out.println("heloo");
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(13);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    }

                    /*  end of study leave   */
                    else if (cmbReason.equalsIgnoreCase("PRO")) {
                        System.out.println(cmbReason);
                        int txtP_OffId = 0, i = 0;
                        try {
                            txtP_OffId =
                                    Integer.parseInt(request.getParameter("txtP_OffId"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        int txtP_desigId = 0;
                        try {
                            txtP_desigId =
                                    Integer.parseInt(request.getParameter("txtP_desigId"));
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        String radP_Repost = null;
                        radP_Repost = request.getParameter("radP_Repost");
                        Date txtP_proc_Date = null;
                        String s = null;
                        s = request.getParameter("txtP_proc_Date");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdTd[] =
                                request.getParameter("txtP_proc_Date").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdTd[2]),
                         Integer.parseInt(sdTd[1]) - 1,
                         Integer.parseInt(sdTd[0]));
                            d = c.getTime();
                            txtP_proc_Date = new Date(d.getTime());
                        }
                        String txtP_proc_No = request.getParameter("txtPNo");

                        String dep_id = request.getParameter("txtP_depid");
                        System.out.println("department id..." + dep_id);

                        System.out.println("proceeding no..." + pro_no);
                        System.out.println("order no..." + ord_no);
                        System.out.println("order date..." + ord_dat);

                        try {
                            System.out.println("inside query");


                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_PRO_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);
                            cs.setInt(8, txtP_OffId);
                            cs.setInt(9, txtP_desigId);

                            cs.setString(10, radP_Repost);
                            cs.setDate(11, txtP_proc_Date);
                            cs.setString(12, txtP_proc_No);

                            cs.setString(14, "insert");
                            cs.setString(15, Proc_Status);
                            cs.setInt(16, 0);
                            cs.setInt(17, 0);

                            cs.setString(18, updatedby);
                            cs.setTimestamp(19, ts);
                            cs.setString(20, dep_id);
                            cs.setString(21, pro_no);
                            cs.setString(22, ord_no);
                            cs.setDate(23, ord_dat);
                            cs.setInt(24, 0);
                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(13,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(13);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else if (cmbReason.equalsIgnoreCase("LLV")) {
                        System.out.println(cmbReason);
                        String cmbLL_TypeId = null;
                        int i = 0;
                        cmbLL_TypeId = (request.getParameter("cmbLL_TypeId"));
                        String txtLL_purpose = null;
                        txtLL_purpose =
                                (request.getParameter("txtLL_purpose"));
                        Date txtL_Period_From = null;
                        String s = null;
                        s = request.getParameter("txtL_Period_From");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtL_Period_From").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtL_Period_From = new Date(d.getTime());
                        }
                        Date txtL_Period_To = null;
                        s = null;
                        s = request.getParameter("txtL_Period_To");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtL_Period_To").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtL_Period_To = new Date(d.getTime());
                        }
                        String dep_id = "TWAD";
                        System.out.println("dep id..." + dep_id);

                        try {
                            System.out.println("inside query" + s);

                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_LLV_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);
                            cs.setString(8, cmbLL_TypeId);
                            cs.setString(9, txtLL_purpose);
                            cs.setDate(10, txtL_Period_From);
                            cs.setDate(11, txtL_Period_To);
                            cs.setString(13, "insert");
                            cs.setString(14, Proc_Status);
                            cs.setInt(15, 0);
                            cs.setInt(16, 0);

                            cs.setString(17, updatedby);
                            cs.setTimestamp(18, ts);
                            cs.setString(19, dep_id);
                            cs.setString(20, pro_no);
                            cs.setString(21, ord_no);
                            cs.setDate(22, ord_dat);
                            cs.setInt(23,0);

                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(12,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(12);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number  " +
                                            txtRel_SLNO +
                                            "  has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else if (cmbReason.equalsIgnoreCase("DVN")) {
                        System.out.println("DVN" + cmbReason);
                        int txtDv_OffId = 0, i = 0;
                        try {
                            txtDv_OffId =
                                    Integer.parseInt(request.getParameter("txtDv_OffId"));
                        } catch (Exception e) {
                        }
                        Date txtDv_Date = null;
                        String s = null;
                        s = request.getParameter("txtDv_Date");
                        System.out.println("s value" + s);
                        if (!s.equals("")) {
                            String sdD[] =
                                request.getParameter("txtDv_Date").split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sdD[2]), Integer.parseInt(sdD[1]) -
                         1, Integer.parseInt(sdD[0]));
                            d = c.getTime();
                            txtDv_Date = new Date(d.getTime());
                        }
                        try {
                            System.out.println("inside query" + s);

                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_DVN_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);
                            cs.setInt(8, txtDv_OffId);
                            cs.setDate(9, txtDv_Date);
                            cs.setString(11, "insert");
                            cs.setString(12, Proc_Status);
                            //cs.setInt(13,0);
                            //cs.setInt(14,0);

                            cs.setString(13, updatedby);
                            cs.setTimestamp(14, ts);
                            cs.setInt(15,0);
                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(10,
                                                    java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(10);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number  " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }

                    } else if (cmbReason.equalsIgnoreCase("SUS") ||
                               cmbReason.equalsIgnoreCase("VRS") ||
                               cmbReason.equalsIgnoreCase("DIS") ||
                               cmbReason.equalsIgnoreCase("SAN") ||
                               cmbReason.equalsIgnoreCase("DTH") ||
                               cmbReason.equalsIgnoreCase("ABR") ||
                               cmbReason.equalsIgnoreCase("MEV")||
                               cmbReason.equalsIgnoreCase("UAL")) {
                        // int i=0;

                        try {
                            System.out.println("inside query in sus");

                            String dep_id = "TWAD";
                            System.out.println("dep id..." + dep_id);

                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_OTH_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);

                            cs.setString(9, "insert");
                            cs.setString(10, Proc_Status);
                            cs.setInt(11, 0);
                            cs.setInt(12, 0);

                            cs.setString(13, updatedby);
                            cs.setTimestamp(14, ts);
                            cs.setString(15, dep_id);
                            cs.setString(16, pro_no);
                            cs.setString(17, ord_no);
                            cs.setDate(18, ord_dat);
                            cs.setInt(19,0);
                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(8, java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(8);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }


                    } else if (cmbReason.equalsIgnoreCase("RES")) {
                        // int i=0;

                        try {
                            System.out.println("inside query in resigning");

                            String dep_id = "TWAD";
                            System.out.println("dep id..." + dep_id);

                            cs =
  con.prepareCall("{call HRM_EMP_RELIEVAL_RES_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                            cs.setInt(1, txtOffId);
                            cs.setInt(2, txtRel_SLNO);
                            cs.setInt(3, txtEmployeeid);
                            cs.setDate(4, txtDORelieval);
                            cs.setString(5, rad_DORelieval);
                            cs.setString(6, cmbReason);
                            cs.setString(7, txtRemarks);
                            //cs.setString(8,txtRemarks);

                            cs.setString(9, "insert");
                            cs.setString(10, Proc_Status);
                            cs.setInt(11, 0);
                            cs.setInt(12, 0);

                            cs.setString(13, updatedby);
                            cs.setTimestamp(14, ts);
                            cs.setString(15, dep_id);
                            cs.setInt(16,0);
                            //cs.setString(17,ord_no);
                            //cs.setDate(18,ord_dat);

                            cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(8, java.sql.Types.NUMERIC);
                            cs.execute();
                            txtRel_SLNO = cs.getInt(2);
                            int errcode = cs.getInt(8);
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                                xml = xml + "<flag>failure</flag>";
                            } else {
                                xml =
 xml + "<flag>success</flag><relieNo>" + txtRel_SLNO + "</relieNo>";
                                sendMessage(response,
                                            " The Relieval Serial Number " +
                                            txtRel_SLNO +
                                            " has been created successfully.",
                                            "ok");
                            }
                        } catch (Exception e) {
                            System.out.println("insert exception  :" + e);
                            sendMessage(response,
                                        "Exception in insertion due to." + e,
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                        }


                    }
                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Error :" + e);
            }
            xml = xml + "</response>";

        }
        System.out.println(xml);
        //out.println(xml);
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
