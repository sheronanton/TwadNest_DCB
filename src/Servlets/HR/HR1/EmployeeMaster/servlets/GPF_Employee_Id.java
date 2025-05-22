package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class GPF_Employee_Id extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    private static final String DOC_TYPE = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
        Connection con=null;
        try {
                    
        	LoadDriver driver=new LoadDriver();
            con=driver.getConnection();

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
        int emp_id;
        int design=0;
        emp_id = Integer.parseInt(request.getParameter("emp_id"));
        System.out.println("hi"+emp_id);
        xml="<response><command>emp</command>";

        System.out.println("hai id");   
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
        
    try
    {
        System.out.println("try");
        ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
        ps.setInt(1,emp_id);
        rs=ps.executeQuery();
        System.out.println("hi123");
        if(!rs.next()) 
        {
            xml=xml+"<flag>failure</flag>";
        }
        else {
            
            ps=con.prepareStatement("SELECT e.EMPLOYEE_NAME,to_char(e.DATE_OF_BIRTH,'dd/mm/yyyy'),e.GPF_NO,f.DESIGNATION FROM HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING d,HRM_MST_DESIGNATIONS f WHERE e.EMPLOYEE_ID=d.EMPLOYEE_ID AND d.DESIGNATION_ID=f.DESIGNATION_ID AND e.EMPLOYEE_ID=?");
            ps.setInt(1,emp_id);
            rs=ps.executeQuery();
            if(rs.next()) {
              
                xml=xml+"<emp_name>"+rs.getString(1)+" </emp_name>";
                xml=xml+"<date_of_birth>"+rs.getString(2)+" </date_of_birth>";
                xml=xml+"<gpf_no>"+rs.getInt(3)+" </gpf_no>";
                xml=xml+"<designation>"+rs.getString(4)+" </designation>";
                }
            
            /*ps=con.prepareStatement("select designation from hrm_mst_designations where designation_id=?");
            ps.setInt(1,design);
            rs=ps.executeQuery();
            if(rs.next()) {
               
             
                xml=xml+"<designation>"+rs.getInt(1)+" </designation>";
            }
            */
            ps=con.prepareStatement("select EMPLOYEE_ID,AC_MONTH,AC_YEAR,TYPE_OF_WITHDRAWAL,WITHDRWAL_AMOUNT,\n" + 
            "DATE_OF_PAYMENT,REMARKS,REL_MONTH,REL_YEAR from hrm_gpf_withdrawal where employee_id=?");
            ps.setInt(1,emp_id);
            rs=ps.executeQuery();  
            xml=xml+"<flag>success</flag>";
            
            while(rs.next()){
                xml=xml+"<emp_id>"+rs.getInt("EMPLOYEE_ID")+"</emp_id>";                
                xml=xml+"<ac_month>"+rs.getInt("AC_MONTH")+"</ac_month>";
                xml=xml+"<ac_year>"+rs.getInt("AC_YEAR")+"</ac_year>";
                xml=xml+"<impound_type>"+rs.getString("TYPE_OF_WITHDRAWAL")+"</impound_type>";
                xml=xml+"<amount>"+rs.getDouble("WITHDRWAL_AMOUNT")+"</amount>";
                xml=xml+"<date_trans>"+rs.getString("DATE_OF_PAYMENT")+"</date_trans>";
                xml=xml+"<rel_month>"+rs.getInt("REL_MONTH")+"</rel_month>";
                xml=xml+"<rel_year>"+rs.getInt("REL_YEAR")+"</rel_year>";
                xml=xml+"<remarks>"+rs.getString("REMARKS")+"</remarks>";
            }
        
        }
    }
    catch(SQLException e) {
        xml=xml+"<flag>failure</flag>";
        e.printStackTrace();
    }
        
         
            xml=xml+"</response>";
            out.println(xml);
            System.out.println(xml);
    }
}
