package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class GPF_Subscription_Arrear
 */
public class GPF_Subscription_Arrear extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	    private static final String DOC_TYPE = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Subscription_Arrear() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	        DecimalFormat df=new DecimalFormat("#0.00");
	        
	        
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
	        
	        session =request.getSession(false);
	        String updatedby=(String)session.getAttribute("UserId");
	        long l=System.currentTimeMillis();
	        java.sql.Timestamp ts=new java.sql.Timestamp(l);
	            System.out.println("got");
	         if(command.equalsIgnoreCase("Get")) {
	            int gpf_no;
	            int design=0;
	            gpf_no = Integer.parseInt(request.getParameter("gpf_no"));
	            xml="<response><command>get</command>";
	            try{
	                   
	                    ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE GPF_NO=?");
	                    ps.setInt(1,gpf_no);
	                    rs=ps.executeQuery();
	                    
	                    if(!rs.next()) 
	                    {
	                        xml=xml+"<flag>failure</flag>";
	                    }
	                    else {
	                        
	                        ps=con.prepareStatement("SELECT e.EMPLOYEE_NAME,to_char(e.DATE_OF_BIRTH,'dd/mm/yyyy'),e.GPF_NO,f.DESIGNATION,e.EMPLOYEE_ID FROM HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING d,HRM_MST_DESIGNATIONS f WHERE e.EMPLOYEE_ID=d.EMPLOYEE_ID AND d.DESIGNATION_ID=f.DESIGNATION_ID AND e.GPF_NO=?");
	                        ps.setInt(1,gpf_no);
	                        rs=ps.executeQuery();
	                        if(rs.next()) {
	                          
	                            xml=xml+"<emp_name>"+rs.getString(1)+" </emp_name>";
	                            xml=xml+"<date_of_birth>"+rs.getString(2)+" </date_of_birth>";
	                            xml=xml+"<gpf_no>"+rs.getInt(3)+" </gpf_no>";
	                            xml=xml+"<designation>"+rs.getString(4)+" </designation>";
	                            xml=xml+"<emp_id>"+rs.getInt(5)+" </emp_id>";
	                            }
	                        
	                        /*ps=con.prepareStatement("select designation from hrm_mst_designations where designation_id=?");
	                        ps.setInt(1,design);
	                        rs=ps.executeQuery();
	                        if(rs.next()) {
	                           
	                         
	                            xml=xml+"<designation>"+rs.getInt(1)+" </designation>";
	                        }
	                        */
	                        ps=con.prepareStatement("select employee_id,ac_month,rel_month,type_trans,sub_amount,ac_year,rel_year,remarks,arr_amount,arr_inst_no,arr_tot_inst,arrear_serial_no,to_char(date_of_payment,'dd/mm/yyyy') from HRM_GPF_SUBSCRIPTION_ARREARNEW where GPF_NO=?");
	                        ps.setInt(1,gpf_no);
	                        rs=ps.executeQuery();  
	                        xml=xml+"<flag>success</flag>";
	                        
	                        while(rs.next()){
	                            xml=xml+"<emp_id>"+rs.getInt(1)+"</emp_id>";                
	                            xml=xml+"<ac_month>"+rs.getInt(2)+"</ac_month>";
	                            xml=xml+"<rel_month>"+rs.getInt(3)+"</rel_month>";
	                            xml=xml+"<type_trans>"+rs.getString(4)+"</type_trans>";
	                            xml=xml+"<amount>"+df.format(rs.getDouble(5))+"</amount>";
	                            xml=xml+"<ac_year>"+rs.getInt(6)+"</ac_year>";
	                            xml=xml+"<rel_year>"+rs.getInt(7)+"</rel_year>";
	                            xml=xml+"<remarks>"+rs.getString(8)+"</remarks>";
	                            xml=xml+"<rec_amount>"+df.format(rs.getInt(9))+"</rec_amount>";
	                            xml=xml+"<rec_no>"+rs.getInt(10)+"</rec_no>";
	                            xml=xml+"<rec_total>"+rs.getInt(11)+"</rec_total>";
	                            xml=xml+"<sno>"+rs.getInt(12)+"</sno>";
	                            xml=xml+"<date>"+rs.getString(13)+"</date>";
	                        }
	                    
	                    }
	                    }
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	           // System.out.println(xml);
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	        	 try{
	            int office_id=Integer.parseInt(request.getParameter("office_id"));
	            String division_id;
	            division_id =request.getParameter("division_id");
	            int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
	            int emp_id=Integer.parseInt(request.getParameter("emp_id").trim()); 
	            int ac_month=Integer.parseInt(request.getParameter("ac_month"));  
	            int ac_year=Integer.parseInt(request.getParameter("ac_year"));  
	            int rel_month=Integer.parseInt(request.getParameter("rel_month")); 
	            int rel_year=Integer.parseInt(request.getParameter("rel_year"));
	            String type_trans=request.getParameter("type_trans");  
	            double amount=Double.parseDouble(request.getParameter("amount"));
	            String remarks;
	            remarks = request.getParameter("remarks");
	          
	            double rec_amount=Double.parseDouble(request.getParameter("rec_amount")); 
	            int rec_no=Integer.parseInt(request.getParameter("rec_no")); 
	            int rec_total=Integer.parseInt(request.getParameter("rec_total")); 
	            String date=request.getParameter("date");
	           
	            xml="<response><command>Add</command>";
	            int sno=0;
	            ps=con.prepareStatement("select  decode(max(arrear_serial_no),null,0,max(arrear_serial_no)) as total from HRM_GPF_SUBSCRIPTION_ARREARNEW");
	            rs=ps.executeQuery();
	            if(rs.next()){
	            sno=rs.getInt("total");
	            if(sno==0){
	            	sno=1;
	            }
	            else{
	            	sno=sno+1;	
	            }
	            }
	            
	           
	            System.out.println("this is add");
	                ps=con.prepareStatement("insert into HRM_GPF_SUBSCRIPTION_ARREARNEW(gpf_no,ac_month,ac_year,employee_id,office_id,accounting_unit_id,rel_month,rel_year,type_trans,sub_amount,remarks,arr_amount,arr_inst_no,arr_tot_inst,updated_by,updated_date,arrear_serial_no,date_of_payment) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'))");
	                ps.setInt(1,gpf_no);
	                ps.setInt(2,ac_month);
	                ps.setInt(3,ac_year);
	                ps.setInt(4,emp_id);
	                ps.setInt(5,office_id);
	                ps.setString(6,division_id);
	                ps.setInt(7,rel_month);
	                ps.setInt(8,rel_year);
	                ps.setString(9,type_trans);
	                ps.setDouble(10,amount);                
	                ps.setString(11,remarks);
	               
	                ps.setDouble(12,rec_amount);
	                ps.setInt(13,rec_no);
	                ps.setInt(14,rec_total);
	                ps.setString(15,updatedby);
	                ps.setTimestamp(16,ts);
	                ps.setInt(17, sno);
	                ps.setString(18, date);
	                ps.executeUpdate();
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<emp_id>"+emp_id+"</emp_id>";  
	                xml=xml+"<ac_month>"+ac_month+"</ac_month>";
	                xml=xml+"<ac_year>"+ac_year+"</ac_year>";
	                xml=xml+"<rel_month>"+rel_month+"</rel_month>";
	                xml=xml+"<rel_year>"+rel_year+"</rel_year>";
	                xml=xml+"<type_trans>"+type_trans+"</type_trans>";
	                xml=xml+"<amount>"+df.format(amount)+"</amount>";
	                xml=xml+"<remarks>"+remarks+"</remarks>";
	               xml=xml+"<rec_amount>"+df.format(rec_amount)+"</rec_amount>";
	                xml=xml+"<rec_no>"+rec_no+"</rec_no>";
	                xml=xml+"<rec_total>"+rec_total+"</rec_total>";
	                xml=xml+"<sno>"+sno+"</sno>";
	                xml=xml+"<date>"+date+"</date>";
	              
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Update")) {
	            System.out.println("update");
	            int office_id=Integer.parseInt(request.getParameter("office_id"));
	            String division_id;
	            division_id =request.getParameter("division_id");
	            int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
	            int emp_id=Integer.parseInt(request.getParameter("emp_id").trim()); 
	            int ac_month=Integer.parseInt(request.getParameter("ac_month"));  
	            int ac_year=Integer.parseInt(request.getParameter("ac_year"));  
	            int rel_month=Integer.parseInt(request.getParameter("rel_month")); 
	            int rel_year=Integer.parseInt(request.getParameter("rel_year"));
	            String type_trans=request.getParameter("type_trans");  
	            double amount=Double.parseDouble(request.getParameter("amount"));
	            String remarks;
	            remarks = request.getParameter("remarks");
	            String date=request.getParameter("date");
	            double rec_amount=Double.parseDouble(request.getParameter("rec_amount")); 
	            int rec_no=Integer.parseInt(request.getParameter("rec_no")); 
	            int rec_total=Integer.parseInt(request.getParameter("rec_total")); 
	            int sno=Integer.parseInt(request.getParameter("sno")); 
	            System.out.println("update1");
	           
	            xml="<response><command>Update</command>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_SUBSCRIPTION_ARREARNEW set rel_month=?,rel_year=?,type_trans=?,sub_amount=?,remarks=?,arr_amount=?,arr_inst_no=?,arr_tot_inst=?,updated_by=?,updated_date=?, ac_month=?,ac_year=?,date_of_payment=to_date(?,'dd-mm-yyyy') where arrear_serial_no=?");
	                ps.setInt(1,rel_month);
	                ps.setInt(2,rel_year);   
	                ps.setString(3,type_trans);
	                ps.setDouble(4,amount);
	                ps.setString(5,remarks);
	                ps.setDouble(6,rec_amount);
	                ps.setInt(7,rec_no);
	                ps.setInt(8,rec_total);
	                ps.setString(9,updatedby);
	                ps.setTimestamp(10,ts);
	                ps.setInt(11,ac_month);
	                ps.setInt(12,ac_year);
	                ps.setString(13,date);
	                ps.setInt(14, sno);
	                // ps.setInt(17,office_id);
	                ps.executeUpdate();
	                
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<emp_id>"+emp_id+"</emp_id>";  
	                xml=xml+"<ac_month>"+ac_month+"</ac_month>";
	                xml=xml+"<ac_year>"+ac_year+"</ac_year>";
	                xml=xml+"<rel_month>"+rel_month+"</rel_month>";
	                xml=xml+"<rel_year>"+rel_year+"</rel_year>";
	                xml=xml+"<type_trans>"+type_trans+"</type_trans>";
	                xml=xml+"<amount>"+df.format(amount)+"</amount>";
	                xml=xml+"<remarks>"+remarks+"</remarks>";
	                xml=xml+"<rec_amount>"+df.format(rec_amount)+"</rec_amount>";
	                xml=xml+"<rec_no>"+rec_no+"</rec_no>";
	                xml=xml+"<rec_total>"+rec_total+"</rec_total>";
	                xml=xml+"<sno>"+sno+"</sno>";
	                xml=xml+"<date>"+date+"</date>";
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Delete")) {
	            System.out.println("delete");
	          //  int emp_id=Integer.parseInt(request.getParameter("emp_id")); 
	            int gpf_no=Integer.parseInt(request.getParameter("gpf_no").trim()); 
	            int ac_month=Integer.parseInt(request.getParameter("ac_month"));  
	            int ac_year=Integer.parseInt(request.getParameter("ac_year"));  
	            int sno=Integer.parseInt(request.getParameter("sno").trim());
	           // int office_id = Integer.parseInt(request.getParameter("office_id"));
	           
	            
	            xml="<response><command>Delete</command>";
	          try{
	                ps=con.prepareStatement("delete from HRM_GPF_SUBSCRIPTION_ARREARNEW where arrear_serial_no=? ");
	                ps.setInt(1,sno);
	                
	               // ps.setInt(4,office_id);
	              
	                ps.executeUpdate();
	                
	              ps=con.prepareStatement("select employee_id,ac_month,rel_month,type_trans,sub_amount,ac_year,rel_year,remarks,arr_amount,arr_inst_no,arr_tot_inst,arrear_serial_no,date_of_payment from HRM_GPF_SUBSCRIPTION_ARREARNEW where gpf_no=?");
	              ps.setInt(1,gpf_no);
	              rs=ps.executeQuery();  
	              xml=xml+"<flag>success</flag>";
	              
	              while(rs.next()){
	                  xml=xml+"<emp_id>"+rs.getInt(1)+"</emp_id>";                
	                  xml=xml+"<ac_month>"+rs.getInt(2)+"</ac_month>";
	                  xml=xml+"<rel_month>"+rs.getInt(3)+"</rel_month>";
	                  xml=xml+"<type_trans>"+rs.getString(4)+"</type_trans>";
	                  xml=xml+"<amount>"+df.format(rs.getDouble(5))+"</amount>";
	                  xml=xml+"<ac_year>"+rs.getInt(6)+"</ac_year>";
	                  xml=xml+"<rel_year>"+rs.getInt(7)+"</rel_year>";
	                  xml=xml+"<remarks>"+rs.getString(8)+"</remarks>";
	                  xml=xml+"<rec_amount>"+df.format(rs.getInt(9))+"</rec_amount>";
	                  xml=xml+"<rec_no>"+rs.getInt(10)+"</rec_no>";
	                  xml=xml+"<rec_total>"+rs.getInt(11)+"</rec_total>";
	                  xml=xml+"<sno>"+rs.getInt(12)+"</sno>";
	                  xml=xml+"<date>"+rs.getDate(13)+"</date>";
	              }
	              
	          }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("unit")) {
	            String unit_name;
	            System.out.println("unit");
	              int office_id=0;
	            unit_name = request.getParameter("unit_name");
	        xml="<response><command>unit</command>";
	            try{
	                
	                ps=con.prepareStatement("select accounting_unit_id from fas_mst_acct_units where accounting_unit_name=?" );
	                ps.setString(1,unit_name);
	                rs=ps.executeQuery();
	                xml=xml+"<flag>success</flag>";
	                if(rs.next()) 
	                     {
	                          xml=xml+"<unit_id>"+ rs.getInt(1) +"</unit_id>";
	                     
	                      }
	               
	                
	                
	            }
	            catch(SQLException e)
	            {
	                xml=xml+"<flag>failure</flag>";
	           e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        System.out.println(xml);
	        out.println(xml);
	        out.close();
	        

	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
