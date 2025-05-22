package Servlets.AME.AME1.AMEM.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class sch_transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";    
    public sch_transaction() {
        super();
    }
	public void init(ServletConfig config) throws ServletException {
	}
	public void destroy() {
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	doPost(request, response);  
		
	}protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
		Controller obj = new Controller();
		Connection con = null;
		String res="";
		 try {
				con = obj.con();
				obj.createStatement(con);
				String process_code = obj.setValue("process_code", request);
				String type = obj.setValue("type", request);
				HttpSession session = request.getSession(false);
				String userid = (String) session.getAttribute("UserId");
				if (userid == null) {
				 response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				//String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
				String	Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

				System.out.println("AME-------->sch_transaction------TYPE("+type+")---->PROCESSCODE("+process_code+")");
				   res="<response>";
				if (Integer.parseInt(type) == 1) {
					//Annual Maintenance Estimate Entry - New Entry 
					if (Integer.parseInt(process_code) == 1) {
						 
						String SCH_CATEGORY_ID=obj.setValue("category_sno",request);
						String sch_year=obj.setValue("pyear",request);  
						String sch_code=obj.setValue("sch_code",request);
						String sch_name=obj.setValue("sch_name",request);
						String sch_type=obj.setValue("sch_type",request);
						String cond=" "; 
						String cond1=" ";
						 if (!SCH_CATEGORY_ID.equals("0"))
		                 {
		                	 cond = cond + (cond.length()>0 ? " and " : "") + "sm.SCH_CATEGORY_ID = " +SCH_CATEGORY_ID;
		                	 cond1 = cond1 + (cond1.length()>0 ? " and " : "") + "SCH_CATEGORY_ID = " +SCH_CATEGORY_ID;
		                 }
						if (!sch_code.equals("0"))
		                 {
		                	 cond = cond + (cond.length()>0 ? " and " : "") + "Upper(sm.sch_code) like '" + sch_code + "%'";
		                	 cond1 = cond1 + (cond1.length()>0 ? " and " : "") + "Upper(sch_code) like '" + sch_code + "%'";
		                 }  
		                 /*if (!sch_name.equals("0"))
		                 {
		                	 cond = cond + (cond.length()>0 ? " and " : "") + "Upper(sm.sch_name) like '" + sch_name + "%'";
		                	 cond1 = cond1+ (cond1.length()>0 ? " and " : "") + "Upper(sch_name) like '" + sch_name + "%'";
		                 }
		                
		                 if (!sch_year.equalsIgnoreCase("0"))
		                 {
		                	 cond = cond + (cond.length()>0 ? " and " : "") + "sm.SCH_YEAR like '" + sch_year + "'";
		                	 cond1= cond1 + (cond1.length()>0 ? " and " : "") + "SCH_YEAR like '" + sch_year + "'";
		                 } 
		                 if (!sch_type.equalsIgnoreCase("0"))
		                 {
		                	 cond = cond + (cond.length()>0 ? " and " : "") + "sm.SCH_TYPE_ID="+sch_type;
		                	 cond1= cond1 + (cond1.length()>0 ? " and " : "") + " SCH_TYPE_ID like '" + sch_type + "'";
		                 } 
		              		String search=obj.setValue("search",request);
		     			  
		      			  if (search.equalsIgnoreCase("SEARCH")) {
		      			  		cond=cond; 
		      			  }
		      			  else {
		      			  		cond="  AND sm.SCH_CATEGORY_ID= "+SCH_CATEGORY_ID+" AND sm.SCH_TYPE_ID    = "+sch_type;
		      			  		cond1="  AND SCH_CATEGORY_ID= "+SCH_CATEGORY_ID;
		      			  }
		      			  
		      			  */
		      			  //PAGINATION VARIABLES//
		      	        int page = Integer.parseInt(obj.setValue("page",request));;
		      	        int total = 2;
		      	        //and SCH_CATEGORY_ID= "+SCH_CATEGORY_ID+"  AND SCH_TYPE_ID    = "+sch_type
		      	        int records = obj.getCount("PMS_SCH_MASTER", "  WHERE     SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+")"	+cond1 );
		      	        int start = 1;
		      	        int limit = Integer.parseInt(obj.setValue("limit",request));
		      	        int end = 10;
		                  start = (page-1)*limit + 1;
		                  end   = start + limit - 1;
		                  total = (int)Math.ceil((float)records/limit);   
		                  
		               
						String qry="SELECT sc.* " 
							+"FROM ( " 
			//				+"  (SELECT ROWNUM AS ID, " 
							+"  (SELECT  " 
							
							+"  row_number() OVER () ROWNUM,"
							+"    sm.SCH_CATEGORY_ID, " 
							+"    sm.SCH_YEAR, " 
							+"    lp.SCH_TYPE_ID, " 
							+"    lp.SCH_TYPE_DESC, " 
							+"    sm.SCH_CODE,sm.SCH_SNO, " 
							+"    sm.SCH_NAME, " 
							+"    sm.SCH_SHORT_DESC, " 
							+"    DECODE(sm.DPR_AMOUNT, '', 0, sm.DPR_AMOUNT)                               AS DPR_AMOUNT, " 
							+"    DECODE(sm.ACTUAL_DATE_COMMENCEMENT, '','--', sm.ACTUAL_DATE_COMMENCEMENT) AS ACTUAL_DATE_COMMENCEMENT, " 
							+"    DECODE(sm.ACTUAL_DATE_COMPLETION, '','--', sm.ACTUAL_DATE_COMPLETION)     AS acompdate " 
							+"  FROM PMS_SCH_MASTER sm, " 
							+"    PMS_SCH_LKP_TYPE lp " 
							+"  WHERE lp.SCH_TYPE_ID =sm.SCH_TYPE_ID and SCH_SNO in ( select SCH_SNO from PMS_DCB_DIV_SCHEME_MAP where OFFICE_ID="+Office_id+") and sch_sno in (select scheme_sno from PMS_DCB_MST_BENEFICIARY_METRE where METER_STATUS='L' and office_id="+Office_id+")"						
		//					+ cond +"    order by SCH_SNO ) sc  ) WHERE ID BETWEEN  " + start + " AND " + end + "   order by SCH_NAME";
									+ cond +"    order by SCH_SNO ) as opt1  ) as opt2  WHERE ID BETWEEN  " + start + " AND " + end + "   order by SCH_NAME";
 					
 						     
						res=res+"<flag>success</flag>";		            	  
						res = res + "<page>"+page+"</page>";
		                res = res + "<total>"+total+"</total>";
		                res = res + "<records>"+records+"</records>";
						res+=obj.resultXML(qry,con,obj,1);						
						res+="</response>";
					}
				} else if (Integer.parseInt(type)==2)
				{
					String MONTH=obj.setValue("MONTH", request);
					String YEAR=obj.setValue("YEAR", request);
					int  rowcount=Integer.parseInt(obj.setValue("rowcount", request));
					Hashtable ht=new Hashtable();
					int r=0;
					for(int i=1;i<=rowcount;i++)
					{
						String SCH_SNO=obj.setValue("SCH_SNO"+i, request);
						String PUMPING_QTY=obj.setValue("PUMPING_QTY"+i, request);						
						String DESIGN_QTY=obj.setValue("DESIGN_QTY"+i, request);
						ht.put("OFFICE_ID",Office_id);	 			 
						ht.put("SCH_SNO",SCH_SNO);		    
						ht.put("PUMPING_QTY",PUMPING_QTY);
						ht.put("MONTH",MONTH);
						ht.put("YEAR",YEAR);
						
						String del_record=obj.delRecord("PMS_AME_TRN_SCH_DP_QTY","where SCH_SNO="+SCH_SNO+"	and  MONTH="+MONTH+" and YEAR="+YEAR+"  and OFFICE_ID="+Office_id,con);
     
						ht.put("UPDATED_BY_USER_ID", "'"+userid+"'");  
						ht.put("UPDATED_TIME", "clock_timestamp()"); 
						if (!PUMPING_QTY.equalsIgnoreCase("9999"))
						r=obj.recordSave(ht, "PMS_AME_TRN_SCH_DP_QTY", con);   
						   
						String del_record_2=obj.delRecord("PMS_AME_MST_SCH_DETAILS","where SCH_SNO="+SCH_SNO+"	and OFFICE_ID="+Office_id,con); 
						int count_=obj.getCount("PMS_AME_MST_SCH_DETAILS", "where OFFICE_ID="+Office_id+" and SCH_SNO="+SCH_SNO);
						int max_=obj.getMax("PMS_AME_MST_SCH_DETAILS", "SCH_DETAILS_SNO","");
						Hashtable ht1=new Hashtable();
						ht1.put("SCH_DETAILS_SNO",max_); 
						ht1.put("OFFICE_ID",Office_id);
						ht1.put("SCH_SNO", SCH_SNO);
						ht1.put("QTY_DESIGN", DESIGN_QTY);
 						ht1.put("UPDATED_BY_USER_ID", Office_id);
						ht1.put("UPDATED_TIME", "clock_timestamp()");     
					    //String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
						String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
					    ht1.put("PROJECT_ID",project_id);  
					    if (count_==0)
					     {     if (!DESIGN_QTY.equalsIgnoreCase("9999"))
					    	   r=obj.recordSave(ht1, "PMS_AME_MST_SCH_DETAILS ", con);  
					    	 if (r > 0 )
					    	 {
				 	    		 res="<response><rows>"+r+"</rows><msg>Record Succesfully Saved</msg></response>";
					    	 }
					     }   
						
					}  
					   
					  
					res="";  
	                res = res + "<records>"+r+"</records>";										
					res+="</response>";  
					
							
				}  
				obj.resposeWr(response, res);
		 }catch (Exception e) { 
			 obj.testQry(e.toString());
		 }
	}

}
