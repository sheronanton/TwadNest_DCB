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
 * Servlet implementation class GPF_Int_Rate
 */
public class GPF_Int_Rate_Monthly extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	    private static final String DOC_TYPE = null;    
    /**
     * @see HttpServlet#HttpServlet()
     */
  
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
	           
	            xml="<response><command>get</command>";
	            try{             
	            	 System.out.println("inside while-------------");                
	            	 String year =request.getParameter("year");
	                        
	                        	ps=con.prepareStatement("SELECT Fin_Year,Interest_Rate,Int_Calculation_Type,to_char(Date_effective_from,'dd/mm/yyyy') as Date_effective ,Proceedings_No,to_char(Proceedings_Date,'dd/mm/yyyy') as Proceedings_Date ,Remarks FROM HRM_GPF_MST_INT_RATE_MONTHLY where Fin_Year=?");
	 	                        ps.setString(1, year);
		                        rs=ps.executeQuery();
		                        
	                     String remarks=null;
	                        if(rs.next()){
	                            xml=xml+"<flag>success</flag>";
	                          System.out.println("inside while");
	                            xml=xml+"<year>"+rs.getString("Fin_Year")+"</year>";
	                            xml=xml+"<caltype>"+rs.getString("Int_Calculation_Type")+"</caltype>";
	                            xml=xml+"<edate>"+rs.getString("Date_effective")+"</edate>";
	                            xml=xml+"<prono>"+rs.getString("Proceedings_No")+"</prono>";
	                            xml=xml+"<prodate>"+rs.getString("Proceedings_Date")+"</prodate>";
                                System.out.println(rs.getString("Proceedings_Date"));
	                            remarks=rs.getString("Remarks");
                                xml=xml+"<remarks>"+rs.getString("Remarks")+"</remarks>";
	                        }
	                        
	                        System.out.println("inside while-----------*****");    
	                    	ps=con.prepareStatement("SELECT INT_YEAR,INT_MONTH,Interest_Rate FROM HRM_GPF_MST_INT_RATE_MONTHLY where Fin_Year=?");
	                    	 ps.setString(1, year); 
	                        rs=ps.executeQuery();
	                        while(rs.next()){
	                        	System.out.println("while----------->");
                                if(rs.getInt("INT_Month")==4)                                	
                                	xml=xml+"<apr>"+rs.getFloat("Interest_Rate")+"</apr>";
                                else  if(rs.getInt("INT_Month")==5)
                                	xml=xml+"<may>"+rs.getFloat("Interest_Rate")+"</may>";
                                else  if(rs.getInt("INT_Month")==6)
                                	xml=xml+"<jun>"+rs.getFloat("Interest_Rate")+"</jun>";
                                else  if(rs.getInt("INT_Month")==7)
                                	xml=xml+"<jul>"+rs.getFloat("Interest_Rate")+"</jul>";
                                else  if(rs.getInt("INT_Month")==8)
                                	xml=xml+"<aug>"+rs.getFloat("Interest_Rate")+"</aug>";
                                else  if(rs.getInt("INT_Month")==9)
                                	xml=xml+"<sep>"+rs.getFloat("Interest_Rate")+"</sep>";
                                else  if(rs.getInt("INT_Month")==10)
                                	xml=xml+"<oct>"+rs.getFloat("Interest_Rate")+"</oct>";
                                else  if(rs.getInt("INT_Month")==11)
                                	xml=xml+"<nov>"+rs.getFloat("Interest_Rate")+"</nov>";
                                else  if(rs.getInt("INT_Month")==12)
                                	xml=xml+"<dec>"+rs.getFloat("Interest_Rate")+"</dec>";
                                else  if(rs.getInt("INT_Month")==1)
                                	xml=xml+"<jan>"+rs.getFloat("Interest_Rate")+"</jan>";
                                else  if(rs.getInt("INT_Month")==2)
                                	xml=xml+"<feb>"+rs.getFloat("Interest_Rate")+"</feb>";
                                else  if(rs.getInt("INT_Month")==3)
                                	xml=xml+"<mar>"+rs.getFloat("Interest_Rate")+"</mar>";
                                
                                System.out.println(xml);
	                            }                 
	                                        
	                
	                    }
	           
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	            System.out.println(xml);
	        }
	        else if(command.equalsIgnoreCase("Add")) {
	        	 try{
	           
	           String year =request.getParameter("year");
	            int rateOfInterest=Integer.parseInt(request.getParameter("rateofinterest").trim());
	            String calType=request.getParameter("caltype");
	            
	            String eDate=request.getParameter("edate");
	            String proDate=request.getParameter("prodate");
	            String proNo=request.getParameter("prono");
	            String remarks;
	            remarks = request.getParameter("remarks");
	            xml="<response><command>Add</command>";
	            ps=con.prepareStatement("select * from HRM_GPF_MST_INT_RATE where Financial_Year=?");
                ps.setString(1,year);
	            rs=ps.executeQuery();
	           if(rs.next())
	           {
	        	   xml=xml+"<flag>same</flag>";  
	           }else{
	            
	                      
	           
	            System.out.println("this is add");
	                ps=con.prepareStatement("insert into HRM_GPF_MST_INT_RATE(Financial_Year,Interest_Rate,Int_Calculation_Type,Date_effective_from,Proceedings_No,Proceedings_Date,Remarks,Process_Flow_Status_Id,Updated_Date,Updated_by_User_id) values(?,?,?,to_date(?,'dd-mm-yyyy'),?,to_date(?,'dd-mm-yyyy'),?,?,?,?)");
	                ps.setString(1,year);
	                ps.setInt(2,rateOfInterest);
	                ps.setString(3,calType);
	                ps.setString(4,eDate);
	                ps.setString(5,proNo);
	                ps.setString(6,proDate);
	                ps.setString(7,remarks);
	                ps.setString(8,"CR");
	                ps.setTimestamp(9,ts);
	                ps.setString(10,updatedby);
  	                ps.executeUpdate();
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<year>"+year+" </year>";
                    xml=xml+"<rateofinterest>"+rateOfInterest+" </rateofinterest>";
                    xml=xml+"<caltype>"+calType+" </caltype>";
                    xml=xml+"<edate>"+eDate+" </edate>";
                    xml=xml+"<prono>"+proNo+" </prono>";
                    xml=xml+"<prodate>"+proDate+" </prodate>";
                    xml=xml+"<remarks>"+remarks+" </remarks>";
	           }
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Update")) {
	            System.out.println("update");
	            String year =request.getParameter("year");
	            int rateOfInterest=Integer.parseInt(request.getParameter("rateofinterest").trim());
	            String calType=request.getParameter("caltype");
	            
	            String eDate=request.getParameter("edate");
	            String proDate=request.getParameter("prodate");
	            String proNo=request.getParameter("prono");
	            String remarks;
	            remarks = request.getParameter("remarks");
	            System.out.println("update1");
	           
	            xml="<response><command>Update</command>";
	            try{
	                ps=con.prepareStatement("update HRM_GPF_MST_INT_RATE set Interest_Rate=?,Int_Calculation_Type=?,Date_effective_from=to_date(?,'dd-mm-yyyy'),Proceedings_No=?,Proceedings_Date=to_date(?,'dd-mm-yyyy'),Remarks=?,Process_Flow_Status_Id=?,Updated_by_User_id=?,updated_date=? where Financial_Year=?");
	                ps.setInt(1,rateOfInterest);
	                ps.setString(2,calType);   
	                ps.setString(3,eDate);
	                ps.setString(4,proNo);
	                ps.setString(5,proDate);
	                ps.setString(6,remarks);
	                ps.setString(7,"MD");
	                ps.setString(8,updatedby);
	                ps.setTimestamp(9,ts);
	                ps.setString(10,year);
	               
	                ps.executeUpdate();
	                
	                xml=xml+"<flag>success</flag>";
	                xml=xml+"<year>"+year+" </year>";
                    xml=xml+"<rateofinterest>"+rateOfInterest+" </rateofinterest>";
                    xml=xml+"<caltype>"+calType+" </caltype>";
                    xml=xml+"<edate>"+eDate+" </edate>";
                    xml=xml+"<prono>"+proNo+" </prono>";
                    xml=xml+"<prodate>"+proDate+" </prodate>";
                    xml=xml+"<remarks>"+remarks+" </remarks>";
	            }
	            catch(SQLException e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	        }
	        else if(command.equalsIgnoreCase("Delete")) {
	            System.out.println("delete");
	          
	            String year =request.getParameter("year");
	            
	            xml="<response><command>Delete</command>";
	          try{
	                ps=con.prepareStatement("delete from HRM_GPF_MST_INT_RATE where Financial_Year=? ");
	                ps.setString(1,year);
	                
	               // ps.setInt(4,office_id);
	              
	                ps.executeUpdate();
	                
	                ps=con.prepareStatement("SELECT Financial_Year,Interest_Rate,Int_Calculation_Type,to_char(Date_effective_from,'dd/mm/yyyy'),Proceedings_No,to_char(Proceedings_Date,'dd/mm/yyyy'),Remarks FROM HRM_GPF_MST_INT_RATE");
                    
                    rs=ps.executeQuery();
                    xml=xml+"<flag>success</flag>";
                    while(rs.next()){
                      
                        xml=xml+"<year>"+rs.getString(1)+" </year>";
                        xml=xml+"<rateofinterest>"+rs.getInt(2)+" </rateofinterest>";
                        xml=xml+"<caltype>"+rs.getString(3)+" </caltype>";
                        xml=xml+"<edate>"+rs.getString(4)+" </edate>";
                        xml=xml+"<prono>"+rs.getString(5)+" </prono>";
                        xml=xml+"<prodate>"+rs.getString(6)+" </prodate>";
                        xml=xml+"<remarks>"+rs.getString(7)+" </remarks>";
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
