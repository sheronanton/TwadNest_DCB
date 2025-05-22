package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class ViewOfficeDetailsServlet_New extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    private Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        ResultSet results = null,rs1=null,rs2=null,rss1=null;
        PreparedStatement ps1=null,ps2=null,pss1=null;
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                /* response.setContentType("text/xml");
                response.setHeader("Cache-Control","no-cache");
               String xml="<response><command>session</command><flag>failure</flag><flag>Session already closed.</flag></response>";
               System.out.println(xml);
                out.println(xml);
                out.close();
                return;*/
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        try {
        	 LoadDriver driver=new LoadDriver();
         	connection=driver.getConnection();
            String xml = "";
            String jur_design="";
            String strName = "", strSName = "", strHeadCode = "", strAdd1 =
                "", strAdd2 = "", strAdd3 = "", strPincode = "", strDistrict =
                "", strStd = "", phone = "", strAPhone = "", strFax =
                "", strAFax = "", strEMail = "", strAEMail = "";
            String strLevel = "", strCOffId = "", strHCode = "", strOCode =
                "", strPrimaryId = "", strDateOfFormation = "", strHRAClassId =
                "", strCCAClassId = "", strIsAccountUnit =
                "", strWingsApplicable = "", strRemarks =
                "", strDateEffectiveFrom = "", strStatus =
                "", strofficestatus = "";
            int intHeadCode = 0, intCOffId = 0, intpincode = 0, districtcode =
                0;

            java.sql.Date DateOfFormation = null;
            String DateToBeDisplayed = "", ControllingOfficeName = "";
           // int officeid = Integer.parseInt(request.getParameter("officeid"));
            
            
            
int gpfNo=Integer.parseInt(request.getParameter("gpf_no"));
            
            System.out.println("THIS IS GPF_NO ==== "+gpfNo);
            
            
            
            int off_ID = 0;
   	     
   	     try
   	     {
   	    	 
   	    	 String s2="select office_id from HRM_EMP_SERVICE_DATA where employee_id=? and DATE_TO=(select max(DATE_TO) from HRM_EMP_SERVICE_DATA where employee_id=?) and office_id is not null";
   	    	
   	    	System.out.println("OFF_ID ==== " + s2);
   	    	ps1=connection.prepareStatement(s2);
   	    	 ps1.setInt(1, gpfNo);
   	    	 ps1.setInt(2, gpfNo);
   	    	 rs1=ps1.executeQuery();
   	    	 if(rs1.next())
   	    	 {
   	    		 off_ID=rs1.getInt("office_id");
   	    		 System.out.println("OFF_ID ==== " + off_ID);
   	    	 }
 //  	    	 else
//   	    	 {
//   	    	 try{
//   	    		 
//   	    		String s3="select office_id from HRM_EMP_SERVICE_DATA_RETD where employee_id=? and DATE_TO=(select max(DATE_TO) from HRM_EMP_SERVICE_DATA_RETD where employee_id=?) and office_id is not null";
//   	   	    	
//   	   	    	System.out.println("OFF_ID ==== " + s3);
//   	   	    	ps1=connection.prepareStatement(s3);
//   	   	    	 ps1.setInt(1, gpfNo);
//   	   	    	 ps1.setInt(2, gpfNo);
//   	   	    	 rs1=ps1.executeQuery();
//   	   	    	 if(rs1.next())
//   	   	    	 {
//   	   	    		 off_ID=rs1.getInt("office_id");
//   	   	    		 System.out.println("OFF_ID ==== " + off_ID);
//   	   	    	 }
//   	   	    	 else
//   	   	    	 {
//   	   	    		 off_ID=0;
//   	   	    	 }
//   	    	 } catch(Exception e)
//   	   	     {
//   	   	    	 
//   	   	     }
//   	    	 }
   	     
   	     }
   	     	          
   	     catch(Exception e)
   	     {
   	    	 
   	     }
            
            

            try {
            	if(off_ID!=0)
            	{
            		int controlling_office_id=0;
                String sql = "select * from COM_OFFICE_CONTROL where Office_Id=?";
            	
                PreparedStatement ps = connection.prepareStatement(sql);
            	
                ps.setInt(1, off_ID);
                results = ps.executeQuery();
                if (results.next()) {
                	controlling_office_id=results.getInt("CONTROLLING_OFFICE_ID");
                	System.out.println("CONTROLLING OFF_ID IS==== " + controlling_office_id);
                	
                	String sqql="select * from COM_MST_OFFICES where Office_Id=?";
                	pss1=connection.prepareStatement(sqql);
                	pss1.setInt(1, controlling_office_id);
                	rss1=pss1.executeQuery();
                	if(rss1.next())
                	{
                		System.out.println("CONTROLLING OFF_ID IS==== " + controlling_office_id);
                		System.out.println("CONTROLLING OFF_ID IS==== " + rss1.getInt("OFFICE_ID"));
                    strName = rss1.getString("OFFICE_NAME");
                    System.out.println("OfficeName" + strName);
                    strSName = rss1.getString("OFFICE_SHORT_NAME");
                    System.out.println("OfficeShorName" + strSName);
                    try {
                        intHeadCode = rss1.getInt("OFFICE_HEAD_CADRE_ID");
                    } catch (NumberFormatException mfe) {
                    }

                    strLevel = rss1.getString("Office_Level_Id");
                    
                    try{
                    	
                    	String sqls="select a.cadre_id,a.cadre_name from hrm_mst_cadre a,com_mst_office_levels b where a.cadre_id=b.office_head_cadre_id and b.office_level_id=?";
                    	ps2=connection.prepareStatement(sqls);
                    	ps2.setString(1,strLevel);  
                    	rs2=ps2.executeQuery();
                    	while(rs2.next())
                    	{
                    		jur_design=rs2.getString("cadre_name");
                    	}
                    	
                    }catch(Exception e)
                    {
                    	
                    }
                    
                  
                    //strOCode = rs.getString("Office_Old_Code");
                    strPrimaryId = rss1.getString("Primary_Work_Id");
                    DateOfFormation = rss1.getDate("Date_of_Formation");

                    if (DateOfFormation == null) {
                        DateToBeDisplayed = "null";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            DateToBeDisplayed = sdf.format(DateOfFormation);
                        } catch (Exception e) {
                            System.out.println("error while formatting date : " +
                                               e);
                        }
                    }
                    System.out.println("date : " + DateToBeDisplayed);

                    //strHRAClassId = rs.getString("HRA_Class_Id");
                    //strCCAClassId = rs.getString("CCA_Class_Id");
                    //strIsAccountUnit = rs.getString("Accounting_Unit");
                    //strWingsApplicable = rs.getString("Wings_Applicable");
                    strRemarks = rss1.getString("Remarks");
                    strStatus = rss1.getString("PROCESS_FLOW_STATUS_ID");
                    strAdd1 = rss1.getString("office_address1");
                    strAdd2 = rss1.getString("office_address2");
                    strAdd3 = rss1.getString("city_town_name");
                    intpincode = rss1.getInt("OFFICE_PIN_CODE");
                    districtcode = rss1.getInt("DISTRICT_CODE");
                    strStd = rss1.getString("OFFICE_STD_CODE");
                    phone = rss1.getString("OFFICE_PHONE_NO");
                    strAPhone = rss1.getString("ADDL_PHONE_NOS");
                    strFax = rss1.getString("OFFICE_FAX_NO");
                    strAFax = rss1.getString("ADDL_FAX_NOS");
                    strEMail = rss1.getString("OFFICE_EMAIL_ID");
                    strAEMail = rss1.getString("ADDL_EMAIL_IDS");
                    strofficestatus = rss1.getString("office_status_id");
                    System.out.println("remarks:" + strRemarks);
                    //strRemarks=strRemarks.trim();
                    System.out.println("remarks:" + strRemarks);
                    results.close();
                    try {
                        String sql1 =
                            "select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?";
                        PreparedStatement pss =
                            connection.prepareStatement(sql1);
                        System.out.println("officeid is:" + off_ID);
                        pss.setInt(1, off_ID);
                        results = pss.executeQuery();
                        if (results.next()) {
                            intCOffId =
                                    results.getInt("Controlling_Office_Id");
                        } else {
                            intCOffId = 0;
                        }
                    } catch (NumberFormatException mfe) {
                    }

                    System.out.println("-------------------------------------");
                    System.out.println(intHeadCode);
                    System.out.println(intCOffId);
                    System.out.println(strLevel);
                    System.out.println(strOCode);
                    System.out.println(strPrimaryId);
                    System.out.println(DateOfFormation);
                    System.out.println(strHRAClassId);
                    System.out.println(strCCAClassId);
                    System.out.println(strIsAccountUnit);
                    System.out.println(strWingsApplicable);
                    System.out.println("-------------------------------------");
                    

                    System.out.println("values retrived sucessfully");
                    xml =
 "<response><flag>success</flag><jur_design>"+jur_design+"</jur_design><officename>" + strName +
   "</officename><officeshortname>" + strSName +
   "</officeshortname><headcode>" + intHeadCode + "</headcode><officelevel>" +
   strLevel + "</officelevel><primaryid>" + strPrimaryId +
   "</primaryid><controlofficeid>" + intCOffId + "</controlofficeid><date>" +
   DateToBeDisplayed + "</date><remarks>" + strRemarks +
   "</remarks><recordstatus>" + strStatus + "</recordstatus><address1>" +
   strAdd1 + "</address1><address2>" + strAdd2 + "</address2><city>" +
   strAdd3 + "</city><pincode>" + intpincode + "</pincode><district>" +
   districtcode + "</district><std>" + strStd + "</std><phone>" + phone +
   "</phone><addphone>" + strAPhone + "</addphone><fax>" + strFax +
   "</fax><addfax>" + strAFax + "</addfax><email>" + strEMail +
   "</email><addemail>" + strAEMail + "</addemail><officestatus>" +
   strofficestatus + "</officestatus></response>";
                    response.setContentType(CONTENT_TYPE);
                    response.setHeader("cache-control", "no-cache");
                    System.out.println("xml is:" + xml);

                }
                }else {
                    xml = "<response><flag>failure</flag></response>";
                    System.out.println("OFF_ID IS ZERO..."+xml);
                }
            }else
            {
            	System.out.println("OFF_ID IS ZERO...");
            	xml = "<response><flag>failure</flag></response>";
                System.out.println("OFF_ID IS ZERO..."+xml);
            }
            }catch (Exception e) {
                System.out.println("Exception in Query:" + e);
            }


            out.write(xml);

        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);
        }

        out.close();
    }
}
