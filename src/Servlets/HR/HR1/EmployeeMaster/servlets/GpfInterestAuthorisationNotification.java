package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

public class GpfInterestAuthorisationNotification extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        GpfAuthMstModel.openConnection();
	 }
    @Override
    public void destroy() {
    	// TODO Auto-generated method stub
    	GpfAuthMstModel.closeConnection();
		super.destroy();		
    }
    
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {    	 	
	
    	//GpfAuthMstModel.closeConnection();
    	GpfAuthMstModel.openConnection();
 
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
	 }catch(Exception e)
	 {
	        e.printStackTrace();
	 }
	 
	 String command="";
	 try {
	 	command = request.getParameter("command");
	} catch (Exception e) {
			// TODO: handle exception
	}
	 boolean gpfIntegerCheck=false;
	 boolean gpfValidity=false;
	 
	 session =request.getSession(false);
	 String updatedby=(String)session.getAttribute("UserId");
	 
	 long l=System.currentTimeMillis();
	 java.sql.Timestamp ts=new java.sql.Timestamp(l);
	     
	  if(command.equalsIgnoreCase("Get")) {
	     int gpfNo=0;	 
		 try {
		 	gpfNo = Integer.parseInt(request.getParameter("gpf_no").trim());
		 	gpfIntegerCheck=true;
		} catch (Exception e) {
			// TODO: handle exception
			gpfIntegerCheck=false;
		}
	     xml="<response><command>get</command>";
	     
	     GpfAuthMstBean gpfAuthMstBean=null;
	     int settlementStatus=0;
	     String tempStrCheck=GpfAuthMstModel.xmlEmployee(gpfNo);
	     if(tempStrCheck.length()>10)
	    	 gpfValidity=true;
	     
	     if(gpfIntegerCheck && gpfValidity){
	     	xml=xml+"<flag>success</flag>";
	     	if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()==0){
	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
	     		xml+=GpfAuthMstModel.xmlFirstRelieveDetails(gpfNo);
	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
	     		xml=xml+"<status>GpfNotExist</status>";	     		
	     	}
	     	else if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("CR")
	     			|| GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("ED") ){
	     		gpfAuthMstBean=GpfAuthMstModel.getGpfAuthMstBean(gpfNo);
	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
	     		xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);	
	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
	     		xml=xml+"<status>GpfEdited</status>";
	     	}
	     	else if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().equalsIgnoreCase("FR")){
	     		gpfAuthMstBean=GpfAuthMstModel.getGpfAuthMstBean(gpfNo);
	     		xml+=GpfAuthMstModel.xmlEmployee(gpfNo);	     		
	     		xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);	
	     		xml+=GpfAuthMstModel.xmlBalanceDetails(gpfNo);
	     		xml=xml+"<status>GpfValidated</status>";
	     	}
	     	
	     }else if(!gpfIntegerCheck){
	     	xml=xml+"<status>inValidInteger</status>";
	     	xml=xml+"<flag>success</flag>";
	     }else if(!gpfValidity){
		     	xml=xml+"<status>inValidGpf</status>";
		     	xml=xml+"<flag>success</flag>";
		 }
	     xml=xml+"</response>";
	     System.out.println(xml);
	 }
	 else if(command.equalsIgnoreCase("Add")) {
		int gpfNo=0;	     	
	 	String relieve_status="";
	 	String relieve_date="";
	 	String int_tobe_calc_month="";
	 	String int_tobe_calc_year="";
	 	String int_tobe_calc_date="";
	 	try {
	 		gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());	  
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_status=request.getParameter("relieve_status").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_date=request.getParameter("relieve_date").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	     
	 	int_tobe_calc_date="";
	     	
	     xml="<response><command>add</command>";
	     xml=xml+"<status>add</status>";
	     
	     GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();	     
	     String statusid=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId();
	     
	     if(GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getGpfNo()==0){	     	
	     	int_tobe_calc_date=GpfAuthMstModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
	     	
	     	gpfAuthMstBean.setGpfNo(gpfNo);
	     	gpfAuthMstBean.setSettlementReason(relieve_status);
	     	gpfAuthMstBean.setRelievalDate(relieve_date);
	     	gpfAuthMstBean.setIntToBeCalcDate(int_tobe_calc_date);	
	     	gpfAuthMstBean.setStatusId("CR");
	     	gpfAuthMstBean.setUpdatedDate(ts);
	     	gpfAuthMstBean.setUpdateByUserId(updatedby);
         	boolean result=GpfAuthMstModel.insertGpfAuthMstBean(gpfAuthMstBean);
            if(result){
            	xml=xml+"<flag>success</flag>";            	
            	xml+=GpfAuthMstModel.xmlEmployee(gpfNo);
             	xml+=GpfAuthMstModel.xmlRelieveDetails(gpfAuthMstBean);         		
            }
            else{
            	xml=xml+"<flag>failure</flag>";            	
            }
	     }
	     else {
	     	xml=xml+"<flag>failure</flag>";
	     }
	     xml=xml+"</response>";
	 }
	 else if(command.equalsIgnoreCase("Update")) {
		 
	    int gpfNo=0;	     	
	 	String relieve_status="";
	 	String relieve_date="";
	 	String int_tobe_calc_month="";
	 	String int_tobe_calc_year="";
	 	String int_tobe_calc_date="";
	 	try {
	 		gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());	  
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_status=request.getParameter("relieve_status").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		relieve_date=request.getParameter("relieve_date").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
	 	try {
	 		int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
		} catch (Exception e) {
			// TODO: handle exception
		}	     
	 	int_tobe_calc_date="";
	 	GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();	  
	 	String statusid=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId();
	    int_tobe_calc_date=GpfAuthMstModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
	     
	     xml="<response><command>update</command>";
	     xml=xml+"<status>update</status>";
	     if(statusid.equalsIgnoreCase("CR")||statusid.equalsIgnoreCase("ED")){	     	
    	 	gpfAuthMstBean.setGpfNo(gpfNo);
	     	gpfAuthMstBean.setSettlementReason(relieve_status);
	     	gpfAuthMstBean.setRelievalDate(relieve_date);
	     	gpfAuthMstBean.setIntToBeCalcDate(int_tobe_calc_date);	
	     	gpfAuthMstBean.setStatusId("ED");
	     	gpfAuthMstBean.setUpdatedDate(ts);
	     	gpfAuthMstBean.setUpdateByUserId(updatedby);
	 		boolean boolSt=false;
	 		boolSt=GpfAuthMstModel.setGpfAuthMstBean(gpfAuthMstBean);
	 		System.out.println("hi...1");	
     		if(boolSt){
     			xml=xml+"<flag>success</flag>";     			
	            xml+=GpfAuthMstModel.xmlEmployee(gpfNo);    	            	
	            System.out.println("hi...2");	
     		}else{
     			xml=xml+"<flag>failure</flag>";
     			System.out.println("hi...3");	
     		}
     	}
	        
	     else{
	     	xml=xml+"<flag>failure</flag>";
	     	System.out.println("hi...4");	
	     }
	     
	     
	     xml=xml+"</response>";
	 }
	     else if(command.equalsIgnoreCase("Validate")) {
	    	 int gpfNo=0;	     	
	 	 	String relieve_status="";
	 	 	String relieve_date="";
	 	 	String int_tobe_calc_month="";
	 	 	String int_tobe_calc_year="";
	 	 	String int_tobe_calc_date="";
	 	 	try {
	 	 		gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());	  
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 	 	try {
	 	 		relieve_status=request.getParameter("relieve_status").trim();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 	 	try {
	 	 		relieve_date=request.getParameter("relieve_date").trim();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 	 	try {
	 	 		int_tobe_calc_month=request.getParameter("int_tobe_calc_month").trim();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}
	 	 	try {
	 	 		int_tobe_calc_year=request.getParameter("int_tobe_calc_year").trim();
	 		} catch (Exception e) {
	 			// TODO: handle exception
	 		}	     
	 	 	int_tobe_calc_date="";
	 	 	GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();	  
	 	 	String statusid=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId();
	 	    int_tobe_calc_date=GpfAuthMstModel.convertMnthAndYrToDate(int_tobe_calc_month, int_tobe_calc_year);
	 	     
	 	     xml="<response><command>validate</command>";
	 	     xml=xml+"<status>validate</status>";
	 	     if(statusid.equalsIgnoreCase("CR")||statusid.equalsIgnoreCase("ED")){	     	
	     	 	gpfAuthMstBean.setGpfNo(gpfNo);
	 	     	gpfAuthMstBean.setSettlementReason(relieve_status);
	 	     	gpfAuthMstBean.setRelievalDate(relieve_date);
	 	     	gpfAuthMstBean.setIntToBeCalcDate(int_tobe_calc_date);	
	 	     	gpfAuthMstBean.setStatusId("FR");
	 	     	gpfAuthMstBean.setUpdatedDate(ts);
	 	     	gpfAuthMstBean.setUpdateByUserId(updatedby);
	 	 		boolean boolSt=false;
	 	 		System.out.println("relieve_status===="+relieve_status);
	 	 		boolSt=GpfAuthMstModel.setGpfAuthMstBean(gpfAuthMstBean);
	 	 		System.out.println("hi...1");	
	      		if(boolSt){
	      			xml=xml+"<flag>success</flag>";     			
	 	            xml+=GpfAuthMstModel.xmlEmployee(gpfNo);    	            	
	 	            System.out.println("hi...2");	
	      		}else{
	      			xml=xml+"<flag>failure</flag>";
	      			System.out.println("hi...3");	
	      		}
	      	}
	 	        
	 	     else{
	 	     	xml=xml+"<flag>failure</flag>";
	 	     	System.out.println("hi...4");	
	 	     }
	 	     
	 	     
	 	     xml=xml+"</response>";
	     }	            
	     else if(command.equalsIgnoreCase("Delete")) {
	         System.out.println("Delete");
	         int gpfNo=Integer.parseInt(request.getParameter("gpf_no").trim());
	         xml="<response><command>delete</command>";
	         xml=xml+"<status>delete</status>";
	         String processStatus=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().trim();
	         
	         
	         	if(!processStatus.equals("FR")){
	         		boolean boolDS=GpfAuthMstModel.deleteGpfAuthMstBean(gpfNo);
	         		if (boolDS) {
	         			xml=xml+"<flag>success</flag>"; 
						} else {
							xml=xml+"<flag>failure</flag>";
						}
	             	
	         	}else{
	         		xml=xml+"<flag>failure</flag>";
	         	}
	         	
				
				xml=xml+"</response>";
	     }	     
	     else if(command.equalsIgnoreCase("LoadYear")){
	     	
	     	xml="<response><command>LoadYear</command>";
	     	xml=xml+"<status>LoadYear</status>";
	     	xml=xml+"<flag>success</flag>"; 
	     	String relvDate="";
	     	try {
	     		relvDate=request.getParameter("relieve_date");
				} catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println("LoadYear:rel_date="+relvDate);
				
	     	
				xml=xml+"<listYear>"+GpfAuthMstModel.xmlLoadListYear(relvDate)+"</listYear>"; 
				
	     	xml=xml+"</response>";
	     	System.out.println("LoadYear xml="+xml);
	     }
	     else if(command.equalsIgnoreCase("LoadMonth")){
	     	xml="<response><command>LoadMonth</command>";
	     	xml=xml+"<status>LoadMonth</status>";
	     	xml=xml+"<flag>success</flag>"; 
	     	String relvDate="";
	     	String listYear="";
	     	
	     	try {
	     		relvDate=request.getParameter("relieve_date");
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {						
					listYear=request.getParameter("listYear");
				} catch (Exception e) {
					// TODO: handle exception
				}
				
	     	
				xml=xml+"<listMonth>"+GpfAuthMstModel.xmlLoadListMonth(relvDate, listYear)+"</listMonth>"; 
				
	     	xml=xml+"</response>";
	     }
	     
	     
	  	GpfAuthMstModel.closeConnection();
	  	
		 out.println(xml);
		 out.close();	        
	
	    }
}
