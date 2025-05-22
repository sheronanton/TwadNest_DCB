package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.text.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GPF_Withdrawal_Details extends HttpServlet {
    private static final String CONTENT_TYPE = "application/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException
    {   
    
        Connection con=null;
        try {
                    
                                 ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                 String ConnectionString="";

                                 String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                 String strdsn=rs1.getString("Config.DSN");
                                 String strhostname=rs1.getString("Config.HOST_NAME");
                                 String strportno=rs1.getString("Config.PORT_NUMBER");
                                 String strsid=rs1.getString("Config.SID");
                                 String strdbusername=rs1.getString("Config.USER_NAME");
                                 String strdbpassword=rs1.getString("Config.PASSWORD");
                         //        ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Conenction 
                                 Class.forName(strDriver.trim());
                                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                }
                catch(Exception e)
                    {
                       System.out.println("Exception in openeing connection :"+e); 
                       //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");   
                       
                    }
        ResultSet rs=null,rs1=null,rs2=null;
        CallableStatement cs=null;
        PreparedStatement ps=null,ps1=null,ps2=null;
        String xml="";
        
        
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control","no-cache");  
        HttpSession session=request.getSession(false);
        try
        {
                if(session==null)
                {
                        xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
                        out.println(xml);
                        System.out.println(xml);
                        out.close();
                        return;

                    }
                    //System.out.println(session);

        }catch(Exception e)
        {
                //System.out.println("Redirect Error :"+e);
        }
        System.out.println("java");
        String command;
        command = request.getParameter("command");
        System.out.println(command);
        session =request.getSession(false);
        String updatedby=(String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        java.sql.Timestamp ts=new java.sql.Timestamp(l);
        if (command.equalsIgnoreCase("check")) {
            String date_trans=request.getParameter("date_trans");
            System.out.println("date_trans"+date_trans);
            int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
            xml="<response><command>check</command>";
            try{
                ps=con.prepareStatement("select gpf_no,DATE_OF_PAYMENT,add_months(DATE_OF_PAYMENT, 6) as date_after6,to_date(?,'dd-mm-yyyy') as cur_payment_date from HRM_GPF_WITHDRAWALNEW where GPF_NO=? ");
                ps.setString(1,date_trans);
                ps.setInt(2,gpf_no);
                rs=ps.executeQuery();
                if(rs.next()) {
                   System.out.println("date_trans"+rs.getDate("DATE_OF_PAYMENT"));
                   System.out.println("date_after6"+rs.getDate("date_after6"));
                    int compare=rs.getDate("date_after6").compareTo(rs.getDate("cur_payment_date"));
                    System.out.println("compare"+compare);
                  if((compare==-1)||(compare==0))
                      {
                          System.out.println("less or equals");
                          xml+="<flag>eligible</flag>";
                          
                      }
                   else if(compare==1)
                      {
                        System.out.println("greater");
                          xml+="<flag>noteligible</flag>";
                        
                      }
                }
            }
            catch(Exception e) {
                System.out.println("Exception in check:"+e);
            }
        }
    
    else if(command.equalsIgnoreCase("Add")) {
            System.out.println("inside --->add");
            int office_id=Integer.parseInt(request.getParameter("office_id"));
            int Acc_unit_id;
            Acc_unit_id =Integer.parseInt(request.getParameter("Acc_unit_id"));
            int emp_id=Integer.parseInt(request.getParameter("emp_id").trim()); 
            int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
            int ac_month=Integer.parseInt(request.getParameter("ac_month"));  
            int ac_year=Integer.parseInt(request.getParameter("ac_year"));  
            int rel_month=Integer.parseInt(request.getParameter("rel_month")); 
            int rel_year=Integer.parseInt(request.getParameter("rel_year"));
            int totInstall=Integer.parseInt(request.getParameter("totinstall").trim());
            int install=Integer.parseInt(request.getParameter("installno").trim());
            String type_withdraw=request.getParameter("type_withdraw");  
            Double amount=Double.parseDouble(request.getParameter("amount")); 
            String date_trans=request.getParameter("date_trans");
            String remarks;
            remarks = request.getParameter("remarks");
            String with_desc=request.getParameter("with_desc");
            System.out.println("got all data");
           
            xml="<response><command>Add</command>";
            try{
                ps=con.prepareStatement("select WITHDRAWAL_SL_NO,gpf_no,ac_month,ac_year,employee_id,office_id,Acc_unit_id,rel_month,rel_year,WITHDRAW_type,DATE_OF_PAYMENT,remarks,WITHDRWAL_AMOUNT,updated_by,updated_date,withd_tot_inst,withd_inst_no,WITHDRAWAL_SL_NO from HRM_GPF_WITHDRAWALNEW where GPF_NO=? ");
                ps.setInt(1,gpf_no);
               /* ps.setInt(2,ac_month);
                ps.setInt(3,ac_year);*/
                rs=ps.executeQuery();
               if (rs.next())
                {
                    System.out.println("exist---->");
                    xml=xml+"<eflag>exist</eflag>";
                    System.out.println(rs.getInt("gpf_no")+rs.getInt("ac_month")+rs.getInt("ac_year")+rs.getInt("employee_id")+rs.getInt("office_id"));
                    System.out.println(rs.getInt("Acc_unit_id")+rs.getInt("rel_month")+rs.getInt("rel_year")+rs.getString("WITHDRAW_type"));
                    System.out.println(rs.getString("DATE_OF_PAYMENT")+rs.getString("remarks")+rs.getFloat("WITHDRWAL_AMOUNT")+rs.getString("updated_by")+rs.getTimestamp("updated_date")+rs.getInt("withd_tot_inst")+rs.getInt("withd_inst_no"));
                    ps=con.prepareStatement("insert into HRM_GPF_Withdrawalnew_log(gpf_no,ac_month,ac_year,employee_id,office_id,Acc_unit_id,rel_month,rel_year,WITHDRAW_type,DATE_OF_PAYMENT,remarks,WITHDRWAL_AMOUNT,updated_by,updated_date,withd_tot_inst,withd_inst_no,WITHDRAWAL_SL_NO) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");      
                    ps.setInt(1,rs.getInt("gpf_no"));
                    ps.setInt(2,rs.getInt("ac_month"));
                    ps.setInt(3,rs.getInt("ac_year"));
                    ps.setInt(4,rs.getInt("employee_id"));
                    ps.setInt(5,rs.getInt("office_id"));
                    ps.setInt(6,rs.getInt("Acc_unit_id"));
                    ps.setInt(7,rs.getInt("rel_month"));
                    ps.setInt(8,rs.getInt("rel_year"));
                    ps.setString(9,rs.getString("WITHDRAW_type"));
                    ps.setDate(10,rs.getDate("DATE_OF_PAYMENT"));
                    ps.setString(11,rs.getString("remarks"));
                    ps.setFloat(12,rs.getFloat("WITHDRWAL_AMOUNT"));                              
                    ps.setString(13,rs.getString("updated_by"));
                    ps.setTimestamp(14,rs.getTimestamp("updated_date"));
                    ps.setInt(15,rs.getInt("withd_tot_inst"));
                    ps.setInt(16,rs.getInt("withd_inst_no"));
                    ps.setInt(17,rs.getInt("WITHDRAWAL_SL_NO"));
                    ps.executeUpdate();
                    
                    ps=con.prepareStatement("delete from HRM_GPF_WITHDRAWALNEW where gpf_no=?");
                    ps.setInt(1,gpf_no);
                    ps.executeUpdate();
                 //   ps.setInt(2,ac_month);
                  //  ps.setInt(3,ac_year);
                }
               
                rs.close();
                System.out.println("Deleted Previous data-------->");
                ps.close();
                ps=con.prepareStatement("insert into HRM_GPF_WithdrawalNEW(gpf_no,ac_month,ac_year,employee_id,office_id,Acc_unit_id,rel_month,rel_year,WITHDRAW_type,DATE_OF_PAYMENT,remarks,WITHDRWAL_AMOUNT,updated_by,updated_date,withd_tot_inst,withd_inst_no) values(?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,to_char(?,'999999999.99'),?,?,?,?)");
                ps.setInt(1,gpf_no);
                ps.setInt(2,ac_month);
                ps.setInt(3,ac_year);
                ps.setInt(4,emp_id);
                ps.setInt(5,office_id);
                ps.setInt(6,Acc_unit_id);
                ps.setInt(7,rel_month);
                ps.setInt(8,rel_year);
                ps.setString(9,type_withdraw);
                ps.setString(10,date_trans);
                ps.setString(11,remarks);
                ps.setDouble(12,amount);                              
                ps.setString(13,updatedby);
                ps.setTimestamp(14,ts);
                ps.setInt(15,totInstall);
                ps.setInt(16,install);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
                xml=xml+"<emp_id>"+emp_id+"</emp_id>";  
                xml=xml+"<ac_month>"+ac_month+"</ac_month>";
                xml=xml+"<ac_year>"+ac_year+"</ac_year>";
                xml=xml+"<rel_month>"+rel_month+"</rel_month>";
                xml=xml+"<rel_year>"+rel_year+"</rel_year>";
                xml=xml+"<type_trans>"+type_withdraw+"</type_trans>";
                xml=xml+"<amount>"+amount+"</amount>";
                xml=xml+"<date_trans>"+date_trans+"</date_trans>";
                xml=xml+"<remarks>"+remarks+"</remarks>";
                xml=xml+"<with_desc>"+with_desc+"</with_desc>";
                xml=xml+"<tot_install>"+totInstall+"</tot_install>";
                xml=xml+"<install_no>"+install+"</install_no>";
                
            }
            catch(SQLException e) {
                xml=xml+"<flag>failure</flag>";
                e.printStackTrace();
            }
        
        }
        else if(command.equalsIgnoreCase("Update")) {
            int office_id=Integer.parseInt(request.getParameter("office_id"));
            int Acc_unit_id;
            Acc_unit_id =Integer.parseInt(request.getParameter("Acc_unit_id"));
            int emp_id=Integer.parseInt(request.getParameter("emp_id").trim()); 
            int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
            int ac_month=Integer.parseInt(request.getParameter("ac_month"));  
            int ac_year=Integer.parseInt(request.getParameter("ac_year"));  
            int rel_month=Integer.parseInt(request.getParameter("rel_month")); 
            System.out.println("rel_month"+rel_month);
            int totInstall=Integer.parseInt(request.getParameter("totinstall").trim()); 
            int install=Integer.parseInt(request.getParameter("installno").trim()); 
            int rel_year=Integer.parseInt(request.getParameter("rel_year"));
            String type_withdraw=request.getParameter("type_withdraw");  
            System.out.println("type_withdraw"+type_withdraw);
           // String type_trans=request.getParameter("type_trans");  
            Double amount=Double.parseDouble(request.getParameter("amount")); 
            String date_trans=request.getParameter("date_trans");
            String with_desc=request.getParameter("with_desc");
            System.out.println("with_desc"+with_desc);
            String remarks;
            remarks = request.getParameter("remarks");
            System.out.println("update");
            xml="<response><command>Update</command>";
            try{
                ps=con.prepareStatement("update HRM_GPF_WithdrawalNEW set acc_unit_id=?,rel_month=?,rel_year=?,WITHDRAW_type=?,DATE_OF_PAYMENT=to_date(?,'dd/mm/yyyy'),WITHDRWAL_AMOUNT=?,remarks=?,updated_by=?,updated_date=?,withd_tot_inst=?,withd_inst_no=? where gpf_no=? and ac_month=? and ac_year=?");
               
               
                ps.setInt(1,Acc_unit_id);
                ps.setInt(2,rel_month);
                ps.setInt(3,rel_year);
                ps.setString(4,type_withdraw);
                ps.setString(5,date_trans);
                ps.setDouble(6,amount);   
                ps.setString(7,remarks);
                             
                   
                
                ps.setString(8,updatedby);
                ps.setTimestamp(9,ts);
                ps.setInt(10,totInstall);
                ps.setInt(11,install);
                ps.setInt(12,gpf_no);
                ps.setInt(13,ac_month);
                ps.setInt(14,ac_year);
                
                ps.executeUpdate();
                System.out.println(gpf_no+"Successfully...");
                xml=xml+"<flag>success</flag>";
                xml=xml+"<emp_id>"+emp_id+"</emp_id>";  
                xml=xml+"<ac_month>"+ac_month+"</ac_month>";
                xml=xml+"<ac_year>"+ac_year+"</ac_year>";
                xml=xml+"<rel_month>"+rel_month+"</rel_month>";
                xml=xml+"<rel_year>"+rel_year+"</rel_year>";
                xml=xml+"<type_withdraw>"+type_withdraw+"</type_withdraw>";
                xml=xml+"<amount>"+amount+"</amount>";
                xml=xml+"<date_trans>"+date_trans+"</date_trans>";
                xml=xml+"<remarks>"+remarks+"</remarks>";
                xml=xml+"<with_desc>"+with_desc+"</with_desc>";
                xml=xml+"<tot_install>"+totInstall+"</tot_install>";
                xml=xml+"<install_no>"+install+"</install_no>";
            }
            catch(SQLException e) {
                xml=xml+"<flag>failure</flag>";
                e.printStackTrace();
            }
         
        }
        else if(command.equalsIgnoreCase("Delete")) {
            System.out.println("delete");
            int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
            int ac_month=Integer.parseInt(request.getParameter("ac_month"));  
            int ac_year=Integer.parseInt(request.getParameter("ac_year"));  
           
            
            xml="<response><command>Delete</command>";
          try{
                ps=con.prepareStatement("delete from HRM_GPF_WithdrawalNEW where gpf_no=? and ac_month=? and ac_year=? ");
                ps.setInt(1,gpf_no);
                ps.setInt(2,ac_month);
                ps.setInt(3,ac_year);
                            
                ps.executeUpdate();
                
              ps=con.prepareStatement("select * from \n" + 
              "(\n" + 
              "select EMPLOYEE_ID,AC_MONTH,AC_YEAR,trim(WITHDRAW_type) as WITHDRAW_type,WITHDRWAL_AMOUNT,\n" + 
              "to_char(DATE_OF_PAYMENT,'dd/mm/yyyy') as date_of_payment,REMARKS,REL_MONTH,REL_YEAR,WITHD_TOT_INST,WITHD_INST_NO \n" + 
              "from hrm_gpf_withdrawalNEW where gpf_no=?\n" + 
              ")a\n" + 
              "left outer join\n" + 
              "(\n" + 
              "select  trim(WITHDRAW_TYPE) as type_with,WITHDRAW_TYPE_DESC from HRM_GPF_WITHDRAWAL_TYPE \n" + 
              ")b\n" + 
              "on a.WITHDRAW_TYPE=b.type_with");
              ps.setInt(1,gpf_no);
              rs=ps.executeQuery();  
              xml=xml+"<flag>success</flag>";
              
              while(rs.next()){
                  xml=xml+"<emp_id>"+rs.getInt("EMPLOYEE_ID")+"</emp_id>";                
                  xml=xml+"<ac_month>"+rs.getInt("AC_MONTH")+"</ac_month>";
                  xml=xml+"<ac_year>"+rs.getInt("AC_YEAR")+"</ac_year>";
                  xml=xml+"<type_withdraw>"+rs.getString("WITHDRAW_TYPE")+"</type_withdraw>";
                  xml=xml+"<amount>"+rs.getDouble("WITHDRWAL_AMOUNT")+"</amount>";
                  xml=xml+"<date_trans>"+rs.getString("DATE_OF_PAYMENT")+"</date_trans>";
                  xml=xml+"<rel_month>"+rs.getInt("REL_MONTH")+"</rel_month>";
                  xml=xml+"<rel_year>"+rs.getInt("REL_YEAR")+"</rel_year>";
                 
                  xml=xml+"<remarks>"+rs.getString("REMARKS")+"</remarks>";
                  xml=xml+"<with_desc>"+rs.getString("WITHDRAW_TYPE_DESC")+"</with_desc>";
                  xml=xml+"<tot_install>"+rs.getInt("WITHD_TOT_INST")+"</tot_install>";
                  xml=xml+"<install_no>"+rs.getInt("WITHD_INST_NO")+"</install_no>";
              }
              
             

              
          }
            catch(SQLException e) {
                xml=xml+"<flag>failure</flag>";
                e.printStackTrace();
            }
        }
            else if(command.equalsIgnoreCase("loademp")) { 
              int  gpf_no = Integer.parseInt(request.getParameter("gpf_no").trim());
                System.out.println("hi"+gpf_no);
                xml="<response><command>emp</command>";
                try
                {
                    System.out.println("try");
                    ps=con.prepareStatement("SELECT GPF_NO,employee_id FROM HRM_MST_EMPLOYEES WHERE GPF_NO=?");
                    ps.setInt(1,gpf_no);
                    rs=ps.executeQuery();
                    System.out.println("hi123");
                    if(!rs.next()) 
                    {
                        System.out.println("if");   
                        xml=xml+"<flag>failure</flag>";
                    }
                    else {
                        System.out.println("else");
                      int emp_id=rs.getInt("employee_id");
                      rs.close();
                      ps.close();
                      ps=con.prepareStatement(  "					select e.employee_id,e.employee_name,e.gpf_no,decode(e.dob,null,'',e.dob) as dob,e.designation_id,e.offid,e.office_name,decode(f.designation,null,'',f.designation) as designation from \n" + 
                              "					(select c.employee_id,c.employee_name,c.gpf_no,c.dob,c.designation_id,d.offid,d.office_name from\n" + 
                              "					(select a.employee_id,a.employee_name,a.gpf_no,a.dob,b.designation_id,b.office_id from\n" + 
                              "					(select employee_id,employee_name,gpf_no,to_char(DATE_OF_BIRTH,'dd/mm/yyyy') as dob from hrm_mst_employees where gpf_no=? )a\n" + 
                              "					left outer join\n" + 
                              "					(select employee_id as empid,designation_id ,office_id from hrm_emp_current_posting)b\n" + 
                              "					on a.employee_id=b.empid)c\n" + 
                              "					left outer join\n" + 
                              "					(select office_id as offid,office_name from com_mst_offices)d\n" + 
                              "					on c.office_id=d.offid)e\n" + 
                              "					left outer join\n" + 
                              "					(select designation_id as desig,DESIGNATION from hrm_mst_designations)f\n" + 
                              "					on f.desig=e.designation_id\n");
                      ps.setInt(1,gpf_no);
                      rs=ps.executeQuery();
                      if(rs.next()) {
                        
                          xml=xml+"<emp_name>"+rs.getString("employee_name")+" </emp_name>";
                          xml=xml+"<date_of_birth>"+rs.getString("dob")+" </date_of_birth>";
                          xml=xml+"<gpf_no>"+rs.getInt("gpf_no")+" </gpf_no>";
                          xml=xml+"<designation>"+rs.getString("designation")+" </designation>";
                          xml=xml+"<emp_id>"+rs.getInt("employee_id")+" </emp_id>";
                          }
                           /*ps=con.prepareStatement("select designation from hrm_mst_designations where designation_id=?");
                        ps.setInt(1,design);
                        rs=ps.executeQuery();
                        if(rs.next()) {
                           
                         
                            xml=xml+"<designation>"+rs.getInt(1)+" </designation>";
                        }
                        */
                        ps=con.prepareStatement("select * from \n" + 
                        "(\n" + 
                        "select EMPLOYEE_ID,AC_MONTH,AC_YEAR,trim(WITHDRAW_type) as WITHDRAW_type,WITHDRWAL_AMOUNT,\n" + 
                        "to_char(DATE_OF_PAYMENT,'dd/mm/yyyy') as date_of_payment,REMARKS,REL_MONTH,REL_YEAR,WITHD_TOT_INST,WITHD_INST_NO \n" + 
                        "from hrm_gpf_withdrawalnew where gpf_no=?\n" + 
                        ")a\n" + 
                        "left outer join\n" + 
                        "(\n" + 
                        "select  trim(WITHDRAW_TYPE) as type_with,WITHDRAW_TYPE_DESC from HRM_GPF_WITHDRAWAL_TYPE \n" + 
                        ")b\n" + 
                        "on a.WITHDRAW_TYPE=b.type_with");
                        ps.setInt(1,gpf_no);
                        rs=ps.executeQuery();  
                        xml=xml+"<flag>success</flag>";
                        System.out.println("sucess");
                        while(rs.next()){
                        System.out.println("while begins");
                            xml=xml+"<emp_id>"+rs.getInt("EMPLOYEE_ID")+"</emp_id>";                
                            xml=xml+"<ac_month>"+rs.getInt("AC_MONTH")+"</ac_month>";
                            xml=xml+"<ac_year>"+rs.getInt("AC_YEAR")+"</ac_year>";
                            xml=xml+"<type_withdraw>"+rs.getString("WITHDRAW_TYPE")+"</type_withdraw>";
                            xml=xml+"<amount>"+rs.getDouble("WITHDRWAL_AMOUNT")+"</amount>";
                            xml=xml+"<date_trans>"+rs.getString("DATE_OF_PAYMENT")+"</date_trans>";
                            xml=xml+"<rel_month>"+rs.getInt("REL_MONTH")+"</rel_month>";
                            xml=xml+"<rel_year>"+rs.getInt("REL_YEAR")+"</rel_year>";
                           
                            xml=xml+"<remarks>"+rs.getString("REMARKS")+"</remarks>";
                            xml=xml+"<with_desc>"+rs.getString("WITHDRAW_TYPE_DESC")+"</with_desc>";
                            xml=xml+"<tot_install>"+rs.getInt("WITHD_TOT_INST")+"</tot_install>";
                            xml=xml+"<install_no>"+rs.getInt("WITHD_INST_NO")+"</install_no>";
                            
                            System.out.println("while end");
                        }
                    System.out.println("loop end");
                    }
                }
                catch(SQLException e) {
                    xml=xml+"<flag>failure</flag>";
                    e.printStackTrace();
                }
                
                
            }
    
            xml=xml+"</response>";
        
    
        System.out.println(xml);
    
        out.println(xml);
        out.close();
    }

    }

  

