package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class GPF_Excess_DB_CR
 */
public class GPF_Excess_DB_CR extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GPF_Excess_DB_CR() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
   	  System.out.println("welcome to Excess details");
	        try{
	        	LoadDriver driver=new LoadDriver();
	        	con=driver.getConnection();
	                    
	        }
	        catch(Exception e){
	        	System.out.println("Exception in opening connection :"+e); 
	        }
	        ResultSet rs=null,rs1=null,rs2=null;
	        CallableStatement cs=null;
	        PreparedStatement ps=null,ps1=null,ps2=null;
	        String xml="";
	        DecimalFormat df=new DecimalFormat("#0.00");
	        int jounalId=0,sl_no=0;
	     //   int ac_month=Integer.parseInt(request.getParameter("acmonth").trim());  
        //   int ac_year=Integer.parseInt(request.getParameter("acyear").trim());  
        //   int unit=Integer.parseInt(request.getParameter("unit").trim());
           
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
	      
	        String command;
	        command = request.getParameter("command");
	        
	        session =request.getSession(false);
	        String updatedby=(String)session.getAttribute("UserId");
	        long l=System.currentTimeMillis();
	        java.sql.Timestamp ts=new java.sql.Timestamp(l);
	            
	           
 if(command.equalsIgnoreCase("add")) {
	            	System.out.println("ADD");	 
		            int gpf_no=0;
		            int unit=0;
		            if(request.getParameter("gpfno").trim()!=null || request.getParameter("gpfno").trim()!="")
		            gpf_no = Integer.parseInt(request.getParameter("gpfno").trim());
		            if(request.getParameter("unit").trim()!=null || request.getParameter("unit").trim()!="")
		            	unit=Integer.parseInt(request.getParameter("unit").trim());
		            int relMonth=Integer.parseInt(request.getParameter("rel_month").trim());
		            int relYear=Integer.parseInt(request.getParameter("rel_year").trim());
		            int int_year=Integer.parseInt(request.getParameter("int_year").trim());
		            int int_month=Integer.parseInt(request.getParameter("int_month").trim());
		            int amount=Integer.parseInt(request.getParameter("amount").trim());
		            int Install_no=0,totalNOInstall=0;
		            String missingType=request.getParameter("misstype");
		            String categoryType=request.getParameter("category_type");
		            String slipPrint=request.getParameter("slipPrint");
		            if(!slipPrint.equalsIgnoreCase("S"))
		            {
		            	  Install_no=Integer.parseInt(request.getParameter("Install_no").trim());
		            	   totalNOInstall=Integer.parseInt(request.getParameter("totalNOInstall").trim());
		            }
		            int jNo=Integer.parseInt(request.getParameter("jno").trim());
		            
		            String date=request.getParameter("date");
		          	
		            String remarks=request.getParameter("remark");
		            
		            xml="<response><command>add</command>";
		            try{
		            	 xml=xml+"<flag>success</flag>";
		            	/*System.out.println(gpf_no);   
		            	System.out.println(unit);   
		            	System.out.println(relMonth);   
		            	System.out.println(relYear);   
		            	System.out.println(amount);     
		            	System.out.println(missingType);     
		            	System.out.println(jNo);     
		            	System.out.println(pNo);     
		            	System.out.println(date);   
		            	System.out.println(prDate);*/
		            	
		            	 if(slipPrint.equalsIgnoreCase("S"))
				            {
		            		 System.out.println("Inside IF");
		            	 ps=con.prepareStatement("insert into HRM_GPF_Excess_CR_DB_MST(ACCOUNTING_UNIT_ID,Excess_TRANS_TYPE,GPF_NO,AMOUNT,RELATIVE_YEAR,RELATIVE_MONTH,JOURNAL_REF_NO,JOURNAL_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REMARKS,fund_category,PROCESS_FLOW_STATUS_ID,INT_CALCULATED_MONTH,INT_CALCULATED_YEAR,SLIP_PRINT_UNDER) values(?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?)");
			             ps.setInt(1,unit);
			             ps.setString(2,missingType);
			             ps.setInt(3,gpf_no);
			             ps.setInt(4,amount);
			             ps.setInt(5,relYear);
			             ps.setInt(6,relMonth);
			             ps.setInt(7,jNo);
			             ps.setString(8,date);                
			             ps.setString(9,updatedby);
			             ps.setTimestamp(10,ts);
			             ps.setString(11,remarks);
			             ps.setString(12,categoryType);
			             ps.setString(13,"CR");
			             ps.setInt(14,int_month);
			             ps.setInt(15,int_year);
			             ps.setString(16,slipPrint);
				            }
		            	 else
		            	 {
		            		 System.out.println("Inside else");
		            		 ps=con.prepareStatement("insert into HRM_GPF_Excess_CR_DB_MST (ACCOUNTING_UNIT_ID,Excess_TRANS_TYPE,GPF_NO,AMOUNT,RELATIVE_YEAR,RELATIVE_MONTH,JOURNAL_REF_NO,JOURNAL_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REMARKS,fund_category,PROCESS_FLOW_STATUS_ID,INT_CALCULATED_MONTH,INT_CALCULATED_YEAR,SLIP_PRINT_UNDER,INST_NO,TOT_INST) values(?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?)");
				             ps.setInt(1,unit);
				             ps.setString(2,missingType);
				             ps.setInt(3,gpf_no);
				             ps.setInt(4,amount);
				             ps.setInt(5,relYear);
				             ps.setInt(6,relMonth);
				             ps.setInt(7,jNo);
				             ps.setString(8,date);                
				             ps.setString(9,updatedby);
				             ps.setTimestamp(10,ts);
				             ps.setString(11,remarks);
				             ps.setString(12,categoryType);
				             ps.setString(13,"CR");
				             ps.setInt(14,int_month);
				             ps.setInt(15,int_year); 
				             ps.setString(16,slipPrint); 
				             ps.setInt(17,Install_no);
				             ps.setInt(18,totalNOInstall);
				             
				             
				            /* System.out.println("unit="+unit);
				             System.out.println("missingType="+missingType);
				             System.out.println("gpf_no="+gpf_no);
				             System.out.println("amount="+amount);
				             System.out.println("relYear="+relYear);
				             System.out.println("relMonth="+relMonth);
				             System.out.println("jNo="+jNo);
				             System.out.println("date="+date);
				             System.out.println("updatedby="+updatedby);
				             System.out.println("ts="+ts);
				             System.out.println("remarks="+remarks);
				             System.out.println("categoryType="+categoryType);
				             System.out.println("int_month="+int_month);
				             System.out.println("int_year="+int_year);
				             System.out.println("slipPrint="+slipPrint);
				             System.out.println("Install_no="+Install_no);
				             System.out.println("totalNOInstall="+totalNOInstall);*/
				             
				             
		            	 }
			             ps.executeUpdate();
		            
		            } catch(Exception e) {
	                xml=xml+"<flag>failure</flag>";
	                e.printStackTrace();
	            }
	            xml=xml+"</response>";
	           // System.out.println(xml);
	        }else if(command.equalsIgnoreCase("addtrn")) {
	        	 System.out.println("TRN");	 
	            int gpf_no=0;
	            int acHead=0;
	            int splhead=0;
	            String fund_cate=null;
	            int flag=0;
	            if(request.getParameter("other_gpfno").trim()!=null || request.getParameter("other_gpfno").trim()!="")
	            {
	            	flag=1;
	            gpf_no = Integer.parseInt(request.getParameter("other_gpfno").trim());
	            }
	            if(request.getParameter("achead").trim()!=null || request.getParameter("achead").trim()!="")
	            {  flag=1;
	             acHead=Integer.parseInt(request.getParameter("achead").trim());
	            }
	            if(flag==0)
	            	acHead=Integer.parseInt(request.getParameter("spl_ac_head").trim());
	            int relMonth=Integer.parseInt(request.getParameter("rel_month").trim());
	            int relYear=Integer.parseInt(request.getParameter("rel_year").trim());
	            int int_year=Integer.parseInt(request.getParameter("int_year").trim());
	            int int_month=Integer.parseInt(request.getParameter("int_month").trim());
	            int amount=Integer.parseInt(request.getParameter("other_amount").trim());
	            String transType=request.getParameter("missingtype");
	            int Install_no=0,totalNOInstall=0;
	            String slipPrint=request.getParameter("slipPrint");
	            if(!slipPrint.equalsIgnoreCase("S"))
	            {
	            	  Install_no=Integer.parseInt(request.getParameter("Install_no").trim());
	            	   totalNOInstall=Integer.parseInt(request.getParameter("totalNOInstall").trim());
	            }
	          //  xml="<response><command>addtrn</command>";
	            try{
	            /*	System.out.println(gpf_no);   
	            	System.out.println(acHead);   
	            	System.out.println(relMonth);   
	            	System.out.println(relYear);   
	            	System.out.println(amount);  */
	            	
	            	ps=con.prepareStatement("select max(HO_JRNL_MST_ID) as jouralid  from HRM_GPF_Excess_CR_DB_MST ");  
	            	rs=ps.executeQuery(); 
	            	if( rs.next()) 
	            	    		{
	            		jounalId=rs.getInt("jouralid");
	            		
	            		}
	            	rs.close();
	            	ps.close();
	            	ps=con.prepareStatement("select fund_category as fund_cate from HRM_GPF_Excess_CR_DB_MST where HO_JRNL_MST_ID=(select max(HO_JRNL_MST_ID) from HRM_GPF_Excess_CR_DB_MST) ");  
	            	rs=ps.executeQuery(); 
	            	if( rs.next()) 
	            	    		{
	            		
	            		fund_cate=rs.getString("fund_cate");
	            		}
	            	
	            	  System.out.println("jounalId");
	            	 ps=con.prepareStatement("select max(HO_JRNL_TRANS_SLNO) as joural_slno from HRM_GPF_Excess_CR_DB_TRN where HO_JRNL_MST_ID=?");
	            	 ps.setInt(1,jounalId);
		            	System.out.println("trn----?"+jounalId);
		            	rs=ps.executeQuery(); 
		            	rs.next(); 
		            	sl_no=rs.getInt("joural_slno");
		            	System.out.println("sl_no----?"+sl_no);
		            	sl_no=sl_no+1;
		            	System.out.println("sl_no----?"+sl_no);
	            	 ps=con.prepareStatement("insert into HRM_GPF_Excess_CR_DB_TRN(HO_JRNL_MST_ID,ASSOCIATE_AC_HEADCODE,ASSOCIATE_GPF_NO,ASSOCIATE_TRANS_TYPE,HO_JRNL_TRANS_SLNO,AMOUNT,RELATIVE_MONTH,RELATIVE_YEAR,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,fund_category,INT_CALCULATED_MONTH,INT_CALCULATED_YEAR,SLIP_PRINT_UNDER,INST_NO,TOT_INST) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	            	 ps.setInt(1,jounalId);  
	            	 ps.setInt(2,acHead);
		             ps.setInt(3,gpf_no);
		             ps.setString(4,transType);
		             ps.setInt(5,sl_no );
		             ps.setInt(6,amount);
		             ps.setInt(7,relMonth);
		             ps.setInt(8,relYear);
		             ps.setString(9,updatedby);
		             ps.setTimestamp(10,ts);
		             ps.setString(11,"CR");
		             ps.setString(12,fund_cate);
		             ps.setInt(13,int_month);
		             ps.setInt(14,int_year);
		             ps.setString(15,slipPrint); 
		             ps.setInt(16,Install_no);
		             ps.setInt(17,totalNOInstall);
		             ps.executeUpdate();   	
		      } catch(Exception e) {
             
                e.printStackTrace();
            }
           
        }
	            
	            
	            else if(command.equalsIgnoreCase("achead"))
	        {
	        	
	        	System.out.print("i am in achead");
	        	 int headCode=Integer.parseInt(request.getParameter("acheadcode"));
	        	 xml="<response><command>achead</command>";
	        	 try{
	        		 ps=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");  
	        		 ps.setInt(1, headCode);
	        		 rs=ps.executeQuery(); 
	        		 
	        		 if(rs.next()) {
	        		  xml=xml+"<flag>success</flag>";
	        		  xml=xml+"<hcocde>"+rs.getString("ACCOUNT_HEAD_DESC")+"</hcocde>";
	        		  //System.out.print("i am in achead"+rs.getString("ACCOUNT_HEAD_DESC"));
	        		  
	        		 }else{
	        			 xml=xml+"<flag>failure</flag>";
	        		 }
	        		 
	        		 xml=xml+"</response>";
	        	 }catch(Exception e){
	        		 System.out.println(e);
	        	 }
	        	 
	        	 
	        }
	            
	        else if(command.equalsIgnoreCase("checkgpf"))
	        {
	        	
	        	System.out.print("i am in CheckGPF");
	        	 int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
	        	 String missType=request.getParameter("misstype");
	        	 String categoryType=request.getParameter("categorytype");
	        	 String relmonth=request.getParameter("relmonth");
	        	 String relyear=request.getParameter("relyear");
	        	 xml="<response><command>checkgpf</command>";
	        	 boolean flag=false;
	        	 try{
	        		 ps=con.prepareStatement("select * from HRM_GPF_Excess_CR_DB_MST where GPF_NO=? and Excess_TRANS_TYPE=? and RELATIVE_YEAR=? and RELATIVE_MONTH=?");  
	        		 ps.setInt(1, gpfNo);
	        		 ps.setString(2, missType);
	        		 ps.setString(3, relyear);
	        		 ps.setString(4, relmonth);
	        		 rs=ps.executeQuery(); 
	        		 
	        		 if(rs.next()) {
	        		  xml=xml+"<flag>success</flag>";
	        		
	        		  
	        		 }else{
	        			 ps=con.prepareStatement("select * from HRM_GPF_Excess_CR_DB_TRN where ASSOCIATE_GPF_NO=? and ASSOCIATE_TRANS_TYPE=? and RELATIVE_YEAR=? and RELATIVE_MONTH=?");  
		        		 ps.setInt(1, gpfNo);
		        		 ps.setString(2, missType);
		        		 ps.setString(3, relyear);
		        		 ps.setString(4, relmonth);
		        		 rs=ps.executeQuery(); 
		        		 
		        		 if(rs.next()) {
		        		  xml=xml+"<flag>success</flag>";
		        		
		        		  
		        		 }else{
		        			 
		        			 ps=con.prepareStatement("select TO_CHAR(DATE_EFFECTIVE_FROM,'dd-MM-yyyy') as DATE_EFFECTIVE_FROM from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=? and EMPLOYEE_STATUS_ID in ('VRS','SAN','DIS','DTH','RES')");  
			        		 ps.setInt(1, gpfNo);
			        		 
			        		 rs=ps.executeQuery(); 
			        		 
			        		 if(rs.next()) {
			        			
			        			 String slipyear="";
			        			 String data []=rs.getString("DATE_EFFECTIVE_FROM").split("-");
			        			 ps=con.prepareStatement("select SLIP_ISSUED_FIN_YEAR from HRM_GPF_LAST_SLIP_ISSUED" );
			        	           
			        	         ResultSet results=ps.executeQuery();
			        	                 if(results.next()) 
			        	                 {
			        	                 slipyear=results.getString("SLIP_ISSUED_FIN_YEAR");
			        	                 }
			        	                 String slipdate []=slipyear.split("-");
			        			 if(Integer.parseInt(relyear)>Integer.parseInt(data [2]))
			        			 {
			        				 xml=xml+"<flag>allow</flag>";
			        				 xml=xml+"<date>Failentry</date>";
			        				 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
			        			 }
			        			 else if(Integer.parseInt(relyear)==Integer.parseInt(data [2]) && Integer.parseInt(relmonth)>Integer.parseInt(data [1]) )
			        			 {
			        			 xml=xml+"<flag>allow</flag><date>Failentry</date>";
			        			 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
			        			 }
			        			 else if(Integer.parseInt(slipdate [1])<Integer.parseInt(data [2]))
			        			 {
			        				 xml=xml+"<flag>allow</flag><date>entry</date>";
			        				 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
			        			 }
			        			 else if(Integer.parseInt(slipdate [1])==Integer.parseInt(data [2]) && Integer.parseInt(data [1])>3)
			        			 {
			        				 xml=xml+"<flag>allow</flag><date>entry</date>";
			        				 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
			        			 }
			        			 else
			        			 {
			        				 xml=xml+"<flag>allow</flag><date>entry</date>"; 
			        				 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
			        			 }
			        		 }
			        		 else
			        		 {
			        			 xml=xml+"<flag>allow</flag><date>Normalentry</date>"; 
			        		 }
			        		 
		        			 
		        			
		        			 
		        		 }
		        		 ps.close();
		        		 rs.close();
	        		 }
	        		 
	        		 xml=xml+"</response>";
	        	 }catch(Exception e){
	        		 System.out.println(e);
	        	 }
	        	 
	        	 
	        }
	        else if(command.equalsIgnoreCase("checkgpf1"))
	        {
	        	//System.out.print("i am in CheckGPF");
            int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
            String missType=request.getParameter("misstype");
            String categoryType=request.getParameter("categorytype");
            String relmonth=request.getParameter("relmonth");
            String relyear=request.getParameter("relyear");
            String jid=request.getParameter("jid");
            xml="<response><command>checkgpf</command>";
            try{
                ps=con.prepareStatement("select * from HRM_GPF_Excess_CR_DB_MST where GPF_NO=? and Excess_TRANS_TYPE=? and RELATIVE_YEAR=? and RELATIVE_MONTH=? and HO_JRNL_MST_ID not in ('"+jid+"')"); 
                ps.setInt(1, gpfNo);
                ps.setString(2, missType);
                ps.setString(3, relyear);
                ps.setString(4, relmonth);
                rs=ps.executeQuery();
                
                if(rs.next()) {
                 xml=xml+"<flag>success</flag>";
              
                
                }else{
                    ps=con.prepareStatement("select * from HRM_GPF_Excess_CR_DB_TRN where ASSOCIATE_GPF_NO=? and ASSOCIATE_TRANS_TYPE=? and RELATIVE_YEAR=? and RELATIVE_MONTH=? and HO_JRNL_MST_ID not in ('"+jid+"')"); 
                    ps.setInt(1, gpfNo);
                    ps.setString(2, missType);
                    ps.setString(3, relyear);
                    ps.setString(4, relmonth);
                    rs=ps.executeQuery();
                    
                    if(rs.next()) {
                     xml=xml+"<flag>success</flag>";
                  
                    
                    }else{
	        			 
	        			 ps=con.prepareStatement("select TO_CHAR(DATE_EFFECTIVE_FROM,'dd-MM-yyyy') as DATE_EFFECTIVE_FROM from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=? and EMPLOYEE_STATUS_ID in ('VRS','SAN','DIS','DTH','RES')");  
		        		 ps.setInt(1, gpfNo);
		        		 
		        		 rs=ps.executeQuery(); 
		        		 
		        		 if(rs.next()) {
		        			
		        			 String slipyear="";
		        			 String data []=rs.getString("DATE_EFFECTIVE_FROM").split("-");
		        			 ps=con.prepareStatement("select SLIP_ISSUED_FIN_YEAR from HRM_GPF_LAST_SLIP_ISSUED" );
		        	           
		        	         ResultSet results=ps.executeQuery();
		        	                 if(results.next()) 
		        	                 {
		        	                 slipyear=results.getString("SLIP_ISSUED_FIN_YEAR");
		        	                 }
		        	                 String slipdate []=slipyear.split("-");
		        			 if(Integer.parseInt(relyear)>Integer.parseInt(data [2]))
		        			 {
		        				 xml=xml+"<flag>allow</flag>";
		        				 xml=xml+"<date>Failentry</date>";
		        				 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
		        			 }
		        			 else if(Integer.parseInt(relyear)==Integer.parseInt(data [2]) && Integer.parseInt(relmonth)>Integer.parseInt(data [1]) )
		        			 {
		        			 xml=xml+"<flag>allow</flag><date>Failentry</date>";
		        			 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
		        			 }
		        			 else if(Integer.parseInt(slipdate [1])<Integer.parseInt(data [2]))
		        			 {
		        				 xml=xml+"<flag>allow</flag><date>entry</date>";
		        				 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
		        			 }
		        			 else if(Integer.parseInt(slipdate [1])==Integer.parseInt(data [2]) && Integer.parseInt(data [1])>3)
		        			 {
		        				 xml=xml+"<flag>allow</flag><date>entry</date>";
		        				 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
		        			 }
		        			 else
		        			 {
		        				 xml=xml+"<flag>allow</flag><date>entry</date>"; 
		        				 xml=xml+"<retriedDate>"+ rs.getString("DATE_EFFECTIVE_FROM")+"</retriedDate>";
		        			 }
		        		 }
		        		 else
		        		 {
		        			 xml=xml+"<flag>allow</flag><date>Normalentry</date>"; 
		        		 }
		        		 
	        			 
	        			
	        			 
	        		 }
                    ps.close();
                    rs.close();
                }
                
                xml=xml+"</response>";
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println(e);
            }
            }

	            System.out.println(xml);
	            
	            out.println(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
