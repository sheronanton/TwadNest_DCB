package Servlets.AME.AME1.AMEM.servlets;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
  
public class CopyOfTransactions extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public CopyOfTransactions() {		super();	} 
	/*
	 *   
	 *Type-Code    
		2	1	
		2	2	
		2	3	
		2	4	
		2	5	
		2	6	
		2	7	
		2	8	
		2	9	
		2	10	Scheme Performance Item Wise Exp New
		  5 =>  Scheme Performance  New ,Edit
		  5-1 	Budget Estimate Entry : New Entry
		  5-2	Budget Estimate Entry : Edit 
		  5-3 
		  5-4
		  5-5
		  5-6
		  5-7  ->Actual Expenditure  Break Up Bill Wise New(data flag -1 and Edit (data flag-2)
		  6 1 -> Account Head Display 
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
			doPost(  request,response); 
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
	
			String res = "",row="0",process_f="";
			String pyear_sel="",pmonth_sel="",pmonth_sch="";
			String sch_sno="",pyear="",pmonth="";
			 String condition="",query="";
			int r = 0,tab_count_=0,count_=0;
			response.setContentType(CONTENT_TYPE);
			Controller obj = new Controller();
			Connection con = null;
		    String fyear_i="",sch_i="";
		    Hashtable ht = new Hashtable();
			Hashtable condht = new Hashtable();
			Hashtable tab1 = new Hashtable();
			Hashtable cols = new Hashtable();
			int tab_max_=0;
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
			String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING","OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
			System.out.println("AME-------->TRANSACTION------TYPE("+type+")---->PROCESSCODE("+process_code+")");
			// TYPE =========================				1 
			if (Integer.parseInt(type) == 1) 
			{
				//Annual Maintenance Estimate Entry - New Entry 
				if (Integer.parseInt(process_code) == 1)
				{
					  row = obj.setValue("rows", request);
					    for (int i = 1; i <= Integer.parseInt(row); i++) {
						int max_ = obj.getMax("PMS_AME_TRN_ABSTRACT", "EST_SNO","");
 						String MAIN_ITEM_SNO="", SUB_ITEM_SNO ="",GROUP_SNO="",SCH_SNO="";
						MAIN_ITEM_SNO = obj.setValue("MAIN_ITEM_SNO"+ i, request);
						SUB_ITEM_SNO = obj.setValue("SUB_ITEM_SNO"+ i, request);
						GROUP_SNO =  obj.setValue("GROUP_SNO" + i,request);
						SCH_SNO= obj.setValue("SCH_SNO", request);
						//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
						String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
						cols.put("EST_SNO", max_);
						cols.put("GROUP_SNO", GROUP_SNO);
						cols.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);
						cols.put("SUB_ITEM_SNO", SUB_ITEM_SNO);
						cols.put("SCH_SNO",SCH_SNO);
						cols.put("FIN_YEAR", "'"+ obj.setValue("pyear", request) + "'");
						cols.put("OFFICE_ID", Office_id);
						cols.put("AM_EST_AMT", obj.setValue("AM_EST_AMT" + i,request));					 
						cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
						cols.put("UPDATED_TIME", "clock_timestamp()");
						cols.put("ENTRY_DATE", "clock_timestamp()");
						cols.put("PROJECT_ID", project_id);
						String count=obj.getValue("PMS_AME_TRN_ABSTRACT", "count(*)", "where office_id="+Office_id+" and FIN_YEAR='"+obj.setValue("pyear", request)+"' and sch_sno="+SCH_SNO+" and MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and GROUP_SNO="+GROUP_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO); 
						  if (Integer.parseInt(count)==0)
						  {
						  r = obj.recordSave(cols, "PMS_AME_TRN_ABSTRACT ",con);
						  }
						if (r > 0) {
							res = "<response><rows>" + r + "</rows></response>";
						}	else
						{res = "<response><rows>" + r + "</rows></response>";}
					 }
				//Annual Maintenance Estimate Entry Edit     
				}else if (Integer.parseInt(process_code) == 2) 
					 {
						row = obj.setValue("rows", request); 
					    for (int i = 1; i <= Integer.parseInt(row); i++) 
					    {
						cols.put("GROUP_SNO", obj.setValue("GROUP_SNO" + i,request));
						cols.put("MAIN_ITEM_SNO", obj.setValue("MAIN_ITEM_SNO"+ i, request));
						cols.put("SUB_ITEM_SNO", obj.setValue("SUB_ITEM_SNO"+ i, request));
						cols.put("SCH_SNO", obj.setValue("SCH_SNO", request));
						cols.put("FIN_YEAR", "'"+ obj.setValue("pyear", request) + "'");
						cols.put("OFFICE_ID", Office_id);
						cols.put("AM_EST_AMT", obj.setValue("AM_EST_AMT" + i,request));
						cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
						cols.put("UPDATED_TIME", "clock_timestamp()");
						cols.put("ENTRY_DATE", "clock_timestamp()");
						condht.put("GROUP_SNO",obj.setValue("GROUP_SNO" + i,request));
						condht.put("MAIN_ITEM_SNO", obj.setValue("MAIN_ITEM_SNO"+ i, request));
						condht.put("SUB_ITEM_SNO", obj.setValue("SUB_ITEM_SNO"+ i, request));
						condht.put("SCH_SNO", obj.setValue("SCH_SNO", request));
						condht.put("FIN_YEAR", "'"+ obj.setValue("pyear", request) + "'");
						 //String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+obj.setValue("SCH_SNO", request));
						String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+obj.setValue("SCH_SNO", request)+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
						 int c=obj.getCount("PMS_AME_TRN_ABSTRACT", " where SCH_SNO="+obj.setValue("SCH_SNO", request) +"  and " +
						 		"   FIN_YEAR='"+ obj.setValue("pyear", request) + "' and  GROUP_SNO="+obj.setValue("GROUP_SNO" + i,request)+" and MAIN_ITEM_SNO="+ obj.setValue("MAIN_ITEM_SNO"+ i, request)+" and SUB_ITEM_SNO="+obj.setValue("SUB_ITEM_SNO"+ i, request)+" and office_id="+Office_id);
						 cols.put("PROJECT_ID",project_id);  
						//	if (!obj.setValue("EST_SNO"+i, request).equalsIgnoreCase("0"))
						    if (c > 0)  
							{
								r+= obj.recordSave(cols,condht, "PMS_AME_TRN_ABSTRACT ",con);
							}
							else
							{
								int max_ = obj.getMax("PMS_AME_TRN_ABSTRACT", "EST_SNO","");
								cols.put("EST_SNO", max_);
								r = obj.recordSave(cols, "PMS_AME_TRN_ABSTRACT ",con);
							}
						}
					    if (r > 0) {
					    	res = "<response><rows>" + r + "</rows></response>";
					    }
					    
					 //     
					 }else if (Integer.parseInt(process_code) == 5) 
					 {
						 
						String SCH_SNO_LOCAL= obj.setValue("sch_sno", request);
						  pyear= obj.setValue("pyear", request);
						 
						/* 		String qry = " SELECT G.GROUP_SNO, "
							+ "  m.MAIN_ITEM_SNO,m.APPLY_CENTAGE, "
							+ "  CASE "
							+ "  WHEN S.SUB_ITEM_SNO IS NULL "
							+ "  THEN 0 "
							+ "  ELSE S.SUB_ITEM_SNO "
							+ "  END AS SUB_ITEM_SNO, "
							+ "  G.GROUP_DESC, "
							+ "  m.MAIN_ITEM_DESC, "
							+ "  case  "
							+ "  when S.SUB_ITEM_DESC is null "
							+ "  then '' "
							+ "  else "
							+ "  S.SUB_ITEM_DESC "
							+ " end  "
							+ " as  "
							+ " SUB_ITEM_DESC "
							+ " FROM "
							+ " ( SELECT GROUP_SNO, GROUP_DESC FROM PMS_AME_MST_GROUP ORDER BY GROUP_SNO "
							+ " )G "
							+ " JOIN "
							+ " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC,APPLY_CENTAGE, GROUP_SNO FROM PMS_AME_MST_MAIN_ITEM "
							+ " )M "
							+ " ON M.GROUP_SNO=G.GROUP_SNO "
							+ " LEFT OUTER JOIN "
							+ " ( SELECT SUB_ITEM_SNO ,SUB_ITEM_DESC,MAIN_ITEM_SNO FROM PMS_AME_MST_SUB_ITEM "
							+ " )S "
							+ " ON S.MAIN_ITEM_SNO=M.MAIN_ITEM_SNO  ORDER BY g.GROUP_SNO, MAIN_ITEM_SNO  ,SUB_ITEM_SNO ";
							*/
						  int rcc=obj.getCount("PMS_AME_TRN_BUDGET"," where SCH_SNO="+SCH_SNO_LOCAL+" and OFFICE_ID="+Office_id+" and FIN_YEAR='"+pyear+"'");
							
							String cond="and ORG_REV='O'";
							if (rcc>1)
								cond="  and ORG_REV='R'";  
						String cond2="";
						String ref_vsch= obj.getValue("PMS_AME_TRN_BUDGET", "TECH_SANC_NO", " where  SCH_SNO="+SCH_SNO_LOCAL+" and OFFICE_ID="+Office_id+" and FIN_YEAR='"+pyear+"' "+cond+"");
						if (rcc==0)
							cond2="";  
						else  
							cond2=" and TECH_SANC_NO="+ref_vsch;  
						String qry = " SELECT gr as GROUP_SNO,(SELECT GROUP_DESC  FROM PMS_AME_MST_GROUP where group_sno=gr) as GROUP_DESC, "
							+ "  msno as MAIN_ITEM_SNO,(SELECT MAIN_ITEM_DESC  FROM PMS_AME_MST_MAIN_ITEM where MAIN_ITEM_SNO=msno) as MAIN_ITEM_DESC , "
							+ "   subsno as SUB_ITEM_SNO,(SELECT  SUB_ITEM_DESC  FROM PMS_AME_MST_SUB_ITEM where SUB_ITEM_SNO=subsno) as  SUB_ITEM_DESC, "
							+ "   (SELECT APPLY_CENTAGE  FROM PMS_AME_MST_MAIN_ITEM where MAIN_ITEM_SNO=msno) as APPLY_CENTAGE "
							+ "  FROM "
							+ "  (SELECT GROUP_SNO AS gr, "
							+ "  MAIN_ITEM_SNO   AS msno, "    
							+ "  SUB_ITEM_SNO    AS subsno "
							+ "  FROM PMS_AME_TRN_SCHEME_ITEM where office_id="+Office_id+"  "+cond2+"  and fin_year='"+pyear+"' and SCH_SNO="+SCH_SNO_LOCAL
							+ "  )mas  "
							+ "  ORDER BY  GROUP_SNO, "
							+ "  MAIN_ITEM_SNO , "
							+ "  SUB_ITEM_SNO ";
						res ="<response>"; 
						res+= obj.resultXML(qry, con, obj,1);		
						int rc = obj.getCount("PMS_AME_TRN_ABSTRACT", " where office_id="+Office_id+" and fin_year='"+pyear+"' and SCH_SNO="+SCH_SNO_LOCAL);
						res+="<rc>"+rc+"</rc>"; 
						qry="SELECT gr AS GROUP_SNO_D,msno AS MAIN_ITEM_SNO_D, subsno as SUB_ITEM_SNO_D ,abs.AM_EST_AMT_D "
							+"	FROM   (SELECT GROUP_SNO AS gr,MAIN_ITEM_SNO   AS msno,   SUB_ITEM_SNO    AS subsno "
							+" 	FROM PMS_AME_TRN_SCHEME_ITEM  WHERE office_id="+Office_id+" "+cond2+" AND fin_year   ='"+pyear+"' "
							+"	AND SCH_SNO    ="+SCH_SNO_LOCAL+"  )mas  right outer join  ( "
							+" select GROUP_SNO,MAIN_ITEM_SNO,SUB_ITEM_SNO,AM_EST_AMT as AM_EST_AMT_D from  PMS_AME_TRN_ABSTRACT "  
							+" WHERE fin_year   ='"+pyear+"' and office_id="+Office_id+" and  SCH_SNO    ="+SCH_SNO_LOCAL
							+" )abs on abs.GROUP_SNO=mas.gr and abs.MAIN_ITEM_SNO=mas.msno and abs.SUB_ITEM_SNO=mas.subsno"  
							+" ORDER BY GROUP_SNO, MAIN_ITEM_SNO ,  SUB_ITEM_SNO ";
						if (rc > 0)
						res+=obj.resultXML(qry, con, obj);  
					  	res+="</response>";    
					     
				}else if (Integer.parseInt(process_code) == 12  || Integer.parseInt(process_code) == 13)   
				 {
					 
					String SCH_SNO_LOCAL= obj.setValue("sch_sno", request);
					  pyear= obj.setValue("pyear", request);
					  String refno= obj.setValue("refno", request);
					 
					/* 		String qry = " SELECT G.GROUP_SNO, "
						+ "  m.MAIN_ITEM_SNO,m.APPLY_CENTAGE, "
						+ "  CASE "
						+ "  WHEN S.SUB_ITEM_SNO IS NULL "
						+ "  THEN 0 "
						+ "  ELSE S.SUB_ITEM_SNO "
						+ "  END AS SUB_ITEM_SNO, "
						+ "  G.GROUP_DESC, "
						+ "  m.MAIN_ITEM_DESC, "
						+ "  case  "
						+ "  when S.SUB_ITEM_DESC is null "
						+ "  then '' "
						+ "  else "
						+ "  S.SUB_ITEM_DESC "
						+ " end  "
						+ " as  "
						+ " SUB_ITEM_DESC "
						+ " FROM "
						+ " ( SELECT GROUP_SNO, GROUP_DESC FROM PMS_AME_MST_GROUP ORDER BY GROUP_SNO "
						+ " )G "
						+ " JOIN "
						+ " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC,APPLY_CENTAGE, GROUP_SNO FROM PMS_AME_MST_MAIN_ITEM "
						+ " )M "
						+ " ON M.GROUP_SNO=G.GROUP_SNO "
						+ " LEFT OUTER JOIN "
						+ " ( SELECT SUB_ITEM_SNO ,SUB_ITEM_DESC,MAIN_ITEM_SNO FROM PMS_AME_MST_SUB_ITEM "
						+ " )S "
						+ " ON S.MAIN_ITEM_SNO=M.MAIN_ITEM_SNO  ORDER BY g.GROUP_SNO, MAIN_ITEM_SNO  ,SUB_ITEM_SNO ";
						*/
					
					String qry = " SELECT gr as GROUP_SNO,(SELECT GROUP_DESC  FROM PMS_AME_MST_GROUP where group_sno=gr) as GROUP_DESC, "
						+ "  msno as MAIN_ITEM_SNO,(SELECT MAIN_ITEM_DESC  FROM PMS_AME_MST_MAIN_ITEM where MAIN_ITEM_SNO=msno) as MAIN_ITEM_DESC , "
						+ "   subsno as SUB_ITEM_SNO,(SELECT  SUB_ITEM_DESC  FROM PMS_AME_MST_SUB_ITEM where SUB_ITEM_SNO=subsno) as  SUB_ITEM_DESC, "
						+ "   (SELECT APPLY_CENTAGE  FROM PMS_AME_MST_MAIN_ITEM where MAIN_ITEM_SNO=msno) as APPLY_CENTAGE "
						+ "  FROM "
						+ "  (SELECT GROUP_SNO AS gr, "
						+ "  MAIN_ITEM_SNO   AS msno, "
						+ "  SUB_ITEM_SNO    AS subsno "
						+ "  FROM PMS_AME_TRN_SCHEME_ITEM where office_id="+Office_id+" and fin_year='"+pyear+"' and SCH_SNO="+SCH_SNO_LOCAL+" and TECH_SANC_NO="+refno
						+ "  )mas  "
						+ "  ORDER BY  GROUP_SNO, "
						+ "  MAIN_ITEM_SNO , "
						+ "  SUB_ITEM_SNO ";
						//System.out.println(qry);
					res ="<response>"; 
					res+= obj.resultXML(qry, con, obj,1);					
				  	res+="</response>";
				  	 
				 	//System.out.println(res);
			}else if (Integer.parseInt(process_code) == 7) 
				 {
					 
					String SCH_SNO_LOCAL= obj.setValue("sch_sno", request);
					 
					 		String qry = " SELECT G.GROUP_SNO, "
						+ "  m.MAIN_ITEM_SNO,m.APPLY_CENTAGE, "
						+ "  CASE "
						+ "  WHEN S.SUB_ITEM_SNO IS NULL "
						+ "  THEN 0 "
						+ "  ELSE S.SUB_ITEM_SNO "
						+ "  END AS SUB_ITEM_SNO, "
						+ "  G.GROUP_DESC, "
						+ "  m.MAIN_ITEM_DESC, "
						+ "  case  "
						+ "  when S.SUB_ITEM_DESC is null "
						+ "  then '' "
						+ "  else "
						+ "  S.SUB_ITEM_DESC "
						+ " end  "
						+ " as  "
						+ " SUB_ITEM_DESC "
						+ " FROM "
						+ " ( SELECT GROUP_SNO, GROUP_DESC FROM PMS_AME_MST_GROUP ORDER BY GROUP_SNO "
						+ " )G "
						+ " JOIN "
						+ " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC,APPLY_CENTAGE, GROUP_SNO FROM PMS_AME_MST_MAIN_ITEM "
						+ " )M "
						+ " ON M.GROUP_SNO=G.GROUP_SNO "
						+ " LEFT OUTER JOIN "
						+ " ( SELECT SUB_ITEM_SNO ,SUB_ITEM_DESC,MAIN_ITEM_SNO FROM PMS_AME_MST_SUB_ITEM "
						+ " )S "
						+ " ON S.MAIN_ITEM_SNO=M.MAIN_ITEM_SNO  ORDER BY g.GROUP_SNO, MAIN_ITEM_SNO  ,SUB_ITEM_SNO ";
					res ="<response>"; 
					res+= obj.resultXML(qry, con, obj,1);		
				
				  	res+="</response>";  
				 
			}else if (Integer.parseInt(process_code) == 3) 
			{ 
					  process_f= obj.setValue("pyear", request);
					  String SCH_SNO = obj.setValue("sch_sno", request);
					   
					/*  String qry = " SELECT G.GROUP_SNO, "
							+ "  m.MAIN_ITEM_SNO,m.APPLY_CENTAGE, "
							+ "  CASE "
							+ "  WHEN S.SUB_ITEM_SNO IS NULL "
							+ "  THEN 0 "
							+ "  ELSE S.SUB_ITEM_SNO " 
							+ "  END AS SUB_ITEM_SNO, "
							+ "  G.GROUP_DESC, "
							+ "  m.MAIN_ITEM_DESC, "
							+ "  case  "
							+ "  when S.SUB_ITEM_DESC is null "
							+ "  then '' "
							+ "  else "
							+ "  S.SUB_ITEM_DESC "
							+ " end  "
							+ " as  "
							+ " SUB_ITEM_DESC "
							+ " FROM "
							+ " ( SELECT GROUP_SNO, GROUP_DESC FROM PMS_AME_MST_GROUP ORDER BY GROUP_SNO "
							+ " )G "
							+ " JOIN "
							+ " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC,APPLY_CENTAGE, GROUP_SNO FROM PMS_AME_MST_MAIN_ITEM "
							+ " )M "
							+ " ON M.GROUP_SNO=G.GROUP_SNO "
							+ " LEFT OUTER JOIN "
							+ " ( SELECT SUB_ITEM_SNO ,SUB_ITEM_DESC,MAIN_ITEM_SNO FROM PMS_AME_MST_SUB_ITEM "
							+ " )S "
							+ " ON S.MAIN_ITEM_SNO=M.MAIN_ITEM_SNO  ORDER BY g.GROUP_SNO,  MAIN_ITEM_SNO ,SUB_ITEM_SNO ";
					    */
					  String qry = " SELECT gr as GROUP_SNO,(SELECT GROUP_DESC  FROM PMS_AME_MST_GROUP where group_sno=gr) as GROUP_DESC, "
							+ "  msno as MAIN_ITEM_SNO,(SELECT MAIN_ITEM_DESC  FROM PMS_AME_MST_MAIN_ITEM where MAIN_ITEM_SNO=msno) as MAIN_ITEM_DESC , "
							+ "   subsno as SUB_ITEM_SNO,(SELECT  SUB_ITEM_DESC  FROM PMS_AME_MST_SUB_ITEM where SUB_ITEM_SNO=subsno) as  SUB_ITEM_DESC, "
							+ "   (SELECT APPLY_CENTAGE  FROM PMS_AME_MST_MAIN_ITEM where MAIN_ITEM_SNO=msno) as APPLY_CENTAGE "
							+ "  FROM "
							+ "  (SELECT GROUP_SNO AS gr, "
							+ "  MAIN_ITEM_SNO   AS msno, "
							+ "  SUB_ITEM_SNO    AS subsno "
							+ "  FROM PMS_AME_TRN_SCHEME_ITEM where office_id="+Office_id+" and fin_year='"+pyear+"' and SCH_SNO="+SCH_SNO
							+ "  )mas  "
							+ "  ORDER BY  GROUP_SNO, "
							+ "  MAIN_ITEM_SNO , "
							+ "  SUB_ITEM_SNO ";
					  PreparedStatement ps=con.prepareStatement(qry);
					  ResultSet rs=ps.executeQuery();
					  res = "<response>";
					  String MAIN_ITEM_SNO="", SUB_ITEM_SNO ="",GROUP_SNO="";
					  String SUB_ITEM_DESC="", MAIN_ITEM_DESC ="",GROUP_DESC="",APPLY_CENTAGE="0";
					  int row_count=0;
					  while (rs.next())
					  {
						  row_count++; 
						  MAIN_ITEM_SNO = rs.getString("MAIN_ITEM_SNO");
						  SUB_ITEM_SNO =  rs.getString("SUB_ITEM_SNO" );
						  GROUP_SNO =  rs.getString("GROUP_SNO");
						  GROUP_DESC=  rs.getString("GROUP_DESC");
						  MAIN_ITEM_DESC=  rs.getString("MAIN_ITEM_DESC");
						  SUB_ITEM_DESC=  rs.getString("SUB_ITEM_DESC");
						  APPLY_CENTAGE=  rs.getString("APPLY_CENTAGE");
						  String amt=obj.getValue("PMS_AME_TRN_ABSTRACT", "AM_EST_AMT", "where office_id="+Office_id+" and FIN_YEAR='"+process_f+"' and sch_sno="+SCH_SNO+" and MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and GROUP_SNO="+GROUP_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);
						  String EST_SNO=obj.getValue("PMS_AME_TRN_ABSTRACT", "EST_SNO", "where office_id="+Office_id+" and FIN_YEAR='"+process_f+"' and sch_sno="+SCH_SNO+" and MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and GROUP_SNO="+GROUP_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);
						  res += "<SUB_ITEM_SNO>" + SUB_ITEM_SNO + "</SUB_ITEM_SNO>";
						  res += "<MAIN_ITEM_SNO>" + MAIN_ITEM_SNO + "</MAIN_ITEM_SNO>";
						  res += "<GROUP_SNO>" + GROUP_SNO + "</GROUP_SNO>";
						  res += "<APPLY_CENTAGE>" + APPLY_CENTAGE + "</APPLY_CENTAGE>";
						  res += "<GROUP_DESC>" + GROUP_DESC + "</GROUP_DESC>";
						  res += "<MAIN_ITEM_DESC>" + MAIN_ITEM_DESC + "</MAIN_ITEM_DESC>";
						  res+= "<SUB_ITEM_DESC>" + SUB_ITEM_DESC + "</SUB_ITEM_DESC>";
						  res+= "<EST_SNO>" + EST_SNO + "</EST_SNO>";
						  res += "<amt>" + amt + "</amt>";
					  }
					  	int y_count=obj.getCount("PMS_AME_TRN_ABSTRACT", "where office_id="+Office_id+" and FIN_YEAR='"+process_f+"' and sch_sno="+SCH_SNO);
					  	res+="<y_count>"+y_count+"</y_count>";
					    res+="<row_count>"+row_count+"</row_count>";  
			 		 	res += "</response>";
				// Break Up Entry of Scheme Performance 
					  	
				}
				else if (Integer.parseInt(process_code) == 4) { 
					  sch_sno= obj.setValue("sch_sno", request);
					  pyear= obj.setValue("pyear", request);
					
					res=obj.delRecord("PMS_AME_TRN_ABSTRACT", "where office_id="+Office_id+" and FIN_YEAR='"+pyear+"' and sch_sno="+sch_sno, con);
					if (r > 0) {
						res = "<response><rows>" + r + "</rows></response>";
					}	else
					{
						res = "<response><rows>" + r + "</rows></response>";
					}

				}
				if (Integer.parseInt(process_code) == 11 || Integer.parseInt(process_code) == 14)
				{
						row = obj.setValue("rows", request);
						String TECH_REF_NO= obj.setValue("TECH_REF_NO", request);
						String SCH_SNO= obj.setValue("SCH_SNO", request);
						
						if (Integer.parseInt(process_code) == 14)
						{
							String count1=obj.getValue("PMS_AME_TRN_SCHEME_ITEM", "count(*)", "where office_id="+Office_id+" and FIN_YEAR='"+obj.setValue("pyear", request)+"' and TECH_SANC_NO="+TECH_REF_NO+" and sch_sno="+SCH_SNO);
							if (Integer.parseInt(count1) > 0)    
							{
								obj.delRecord("PMS_AME_TRN_SCHEME_ITEM", "where office_id="+Office_id+" and FIN_YEAR='"+obj.setValue("pyear", request)+"' and TECH_SANC_NO="+TECH_REF_NO+" and sch_sno="+SCH_SNO, con);
							}
						}     
						
					    for (int i = 1; i <= Integer.parseInt(row); i++) 
					    { 
					    	int max_ = obj.getMax("PMS_AME_TRN_SCHEME_ITEM", "SCH_ITEM_SNO","");
					    	String MAIN_ITEM_SNO="", SUB_ITEM_SNO ="",GROUP_SNO="" ;
					    	MAIN_ITEM_SNO = obj.setValue("MAIN_ITEM_SNO"+ i, request);
					    	SUB_ITEM_SNO = obj.setValue("SUB_ITEM_SNO"+ i, request);
					    	GROUP_SNO =  obj.setValue("GROUP_SNO" + i,request);
					    	String chk=  obj.setValue("chk" + i,request);
					    	if (new Boolean(chk)==true)
					    	{
								String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
						    	//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
						    	cols.put("SCH_ITEM_SNO", max_);
						    	cols.put("GROUP_SNO", GROUP_SNO);
						    	cols.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);
						    	cols.put("SUB_ITEM_SNO", SUB_ITEM_SNO);
						    	cols.put("SCH_SNO",SCH_SNO);
						    	cols.put("FIN_YEAR", "'"+ obj.setValue("pyear", request) + "'");
						    	cols.put("OFFICE_ID", Office_id);
						    	cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
						    	cols.put("UPDATED_TIME", "clock_timestamp()");
						    	cols.put("ENTRY_DATE", "clock_timestamp()");						    	
						    	cols.put("TECH_SANC_NO", TECH_REF_NO);  
						    	String count=obj.getValue("PMS_AME_TRN_SCHEME_ITEM", "count(*)", "where office_id="+Office_id+" and FIN_YEAR='"+obj.setValue("pyear", request)+"' and TECH_SANC_NO="+TECH_REF_NO+" and sch_sno="+SCH_SNO+" and MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and GROUP_SNO="+GROUP_SNO+" and SUB_ITEM_SNO="+SUB_ITEM_SNO);						    	
						      	if (Integer.parseInt(count)==0)    
								{
						      		if (Integer.parseInt(process_code) == 14)
						      		{
						      			
						      		}
						      			
								  r+= obj.recordSave(cols, "PMS_AME_TRN_SCHEME_ITEM ",con);
								}
					    	}	 
							res = "<response><rows>" + r + "</rows></response>";}
				//Annual Maintenance Estimate Entry Edit     
				}
			}
			//  TYPE 	==================================   4 
		
			if (Integer.parseInt(type) == 2) 
			{
					String MAIN_ITEM_SNO="0",SUB_ITEM_SNO="0",GROUP_SNO="0";
				  // 3	Actual Expenditure incurred Update to Database  
				 if (Integer.parseInt(process_code) == 10)
				{ 
					 String dataflag=obj.setValue("dataflag", request);					
					 row = obj.setValue("rows", request);
					 
					    for (int i = 1; i <= Integer.parseInt(row); i++) 
					    {
					    	
					    	  tab_max_=obj.getMax("PMS_AME_TRN_SCH_ACT_EXP_ITEM","ACTUAL_EXP_SNO", "");
					    	  pyear_sel = obj.setValue("fyear", request);
					    	  pmonth_sel = obj.setValue("pmonth", request);
							  process_f = "";  
							  if (Integer.parseInt(pmonth_sel) <= 3) {
								  process_f = (Integer.parseInt(pyear_sel) - 1)+ "-" + pyear_sel;
							  } else {  
								  process_f = pyear_sel + "-"+ (Integer.parseInt(pyear_sel) + 1);
							  }
							 MAIN_ITEM_SNO=obj.setValue("MAIN_ITEM_SNO"+ i, request);
							 SUB_ITEM_SNO=obj.setValue("SUB_ITEM_SNO"+ i, request);
							 GROUP_SNO=obj.setValue("GROUP_SNO"+ i, request);  
							 String cond_add=" and SUB_ITEM_SNO="+SUB_ITEM_SNO+" and MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and GROUP_SNO="+GROUP_SNO;
							 String d_r=obj.delRecord("PMS_AME_TRN_SCH_ACT_EXP_ITEM", "where (1=1) "+cond_add+" and  PERFORM_DESC_SNO="+obj.setValue("PERFORM_DESC_SNO", request)+" and FIN_YEAR='"+process_f+"' and SCH_SNO="+obj.setValue("sch", request)+" and year ="+pyear_sel+"  and month="+pmonth_sel+" and  office_id="+Office_id, con);
						    	cols.put("ACTUAL_EXP_SNO",tab_max_ );
						    	cols.put("FIN_YEAR", "'"+process_f+ "'");
						    	cols.put("YEAR", pyear_sel);
						    	cols.put("MONTH",pmonth_sel);
						    	cols.put("SCH_SNO",obj.setValue("sch", request));
						    	cols.put("ACTUAL_EXP", obj.setValue("eamt" + i,request));
						    	cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
								cols.put("UPDATED_TIME", "clock_timestamp()");
								cols.put("OFFICE_ID", Office_id);
								cols.put("PERFORM_DESC_SNO",obj.setValue("PERFORM_DESC_SNO", request));
								cols.put("GROUP_SNO", GROUP_SNO);
								cols.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);
								cols.put("SUB_ITEM_SNO",SUB_ITEM_SNO );
								cols.put("ENTRY_DATE", "clock_timestamp()");
								String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+obj.setValue("sch", request)+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
								//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+obj.setValue("sch", request));
								cols.put("PROJECT_ID",project_id );  
								
								if (!obj.setValue("PERFORM_DESC_SNO", request).equalsIgnoreCase("0"))
								{
									r += obj.recordSave(cols,"PMS_AME_TRN_SCH_ACT_EXP_ITEM", con);
								}
 					    }
					    res = "<response><rows>" + r + "</rows></response>";
					    
				} else 	if (Integer.parseInt(process_code) == 1) 
				{

					row = obj.setValue("rows", request);
					r = 0;
					count_=0;					
					String SCH_SNO = obj.setValue("SCH_SNO", request);
					String flag_c = obj.setValue("flag", request);
					String fyear = obj.setValue("pyear", request);
					String dataflag = obj.setValue("dataflag", request);
					pmonth_sel = obj.setValue("pmonth", request);
					pyear_sel = obj.setValue("pyear", request);
					cols.put("SCH_SNO", SCH_SNO);
					cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
					cols.put("OFFICE_ID", Office_id);			
					
					if (Integer.parseInt(pmonth_sel) <= 3) {
						process_f = (Integer.parseInt(pyear_sel) - 1)+ "-" + pyear_sel;
					} else {
						process_f = pyear_sel + "-"+ (Integer.parseInt(pyear_sel) + 1);
					}
					String cond_qry=" where YEAR="+fyear+
					" and FIN_YEAR='"+process_f+"'"+
					" and OFFICE_ID="+Office_id+
					" and SCH_SNO="+SCH_SNO+ "" +
					" and MONTH ="+obj.setValue("pmonth", request);
					String cond_qry2=" where YEAR="+fyear+
					" and FIN_YEAR='"+process_f+"'"+
					" and OFFICE_ID="+Office_id+
					" and SCH_SNO="+SCH_SNO+ "" ;
					if (Integer.parseInt(dataflag)==2)
					{
						for (int i = 1; i <= Integer.parseInt(row); i++) 
						{
							String PERFORM_DESC_SNO = obj.setValue("PERFORM_DESC_SNO" + i, request);
							String DATATYPE_SNO = obj.setValue("DATATYPE" + i, request);
							
							tab_count_ = obj.getCount("PMS_AME_TRN_SCH_PERFORM_YEAR","where PERFORM_DESC_SNO!=7 and PERFORM_DESC_SNO="+ PERFORM_DESC_SNO
									+ " and OFFICE_ID=" + Office_id+ " and SCH_SNO=" + SCH_SNO
									+ " and FIN_YEAR= '" + process_f + "'");  
							if (tab_count_!=0 && Integer.parseInt(dataflag)==2)
							{
								Hashtable upd = new Hashtable();
								Hashtable cond = new Hashtable();
								cond.put("FIN_YEAR", "'" + process_f + "'");
								cond.put("PERFORM_DESC_SNO", PERFORM_DESC_SNO);
								cond.put("SCH_SNO", SCH_SNO);  
								cond.put("OFFICE_ID", Office_id);
								if (Integer.parseInt(DATATYPE_SNO) == 4) 
								{			 							 
									upd.put("REMARKS", "'"+ obj.setValue("oldamt" + i,request) + "'");
									upd.put("QTY_OR_AMOUNT","0");
								}
								else     
								{	upd.put("QTY_OR_AMOUNT", " QTY_OR_AMOUNT+"+ obj.setValue("oldamt" + i,request)); }
									upd.put("REMARKS", "''");
								int sr=obj.recordSave(upd, cond,"PMS_AME_TRN_SCH_PERFORM_YEAR", con);
								
								
								
								int rc=obj.getDistinctCount("PMS_AME_TRN_SCH_PERFORM_MTH","month",cond_qry2);
							 
								Hashtable upd_new = new Hashtable();
								String QTY_OR_AMOUNT=obj.getValue("PMS_AME_TRN_SCH_PERFORM_MTH", "sum(QTY_OR_AMOUNT)","a", " where FIN_YEAR="+"'"+process_f+"'" +
										" and PERFORM_DESC_SNO=4 and SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);
								
								double avg= Double.parseDouble(QTY_OR_AMOUNT)/rc;
								upd_new.put("QTY_OR_AMOUNT",avg);       
								Hashtable cond_new = new Hashtable();
								cond_new.put("FIN_YEAR", "'" + process_f + "'");
								cond_new.put("PERFORM_DESC_SNO", "4");  
								cond_new.put("SCH_SNO", SCH_SNO);  
								cond_new.put("OFFICE_ID", Office_id);  
								   sr=obj.recordSave(upd_new, cond_new,"PMS_AME_TRN_SCH_PERFORM_YEAR", con);
							}								
						}
						String del_qry=obj.delRecord("PMS_AME_TRN_SCH_PERFORM_MTH", cond_qry, con);				 
					}
					for (int i = 1; i <= Integer.parseInt(row); i++) 
					{
						String PERFORM_DESC_SNO = obj.setValue("PERFORM_DESC_SNO" + i, request);
						cols.put("PERFORM_DESC_SNO", PERFORM_DESC_SNO);
						cols.put("PROCESS", "'"+ obj.setValue("flag", request) + "'");
						cols.put("YEAR", "'"+ obj.setValue("pyear", request) + "'");
						cols.put("UPDATED_TIME", "clock_timestamp()");
						cols.put("MONTH",obj.setValue("pmonth", request) );
						String DATATYPE_SNO = obj.setValue("DATATYPE" + i, request);
						 
						if (flag_c.equalsIgnoreCase("Y")) 
						{
							int max_ = obj.getMax("PMS_AME_TRN_SCH_PERFORM_MTH","SCH_PERFORM_SNO", "");
							cols.put("SCH_PERFORM_SNO", max_);
							int count_qty = obj.getCount("PMS_AME_TRN_SCH_PERFORM_MTH","where PERFORM_DESC_SNO="
									+ PERFORM_DESC_SNO
									+ " and OFFICE_ID=" + Office_id
									+ " and SCH_SNO=" + SCH_SNO
									+ " and FIN_YEAR='"
									+ obj.setValue("pyear", request)
									+ "' ");
							String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
							//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
							cols.put("PROJECT_ID",project_id);
							
							if (count_qty == 0)
								r += obj.recordSave(cols,"PMS_AME_TRN_SCH_PERFORM_MTH ", con);
							else
								r = 0;

						} else 
						{
							pyear_sel = obj.setValue("pyear", request);
							pmonth_sel = obj.setValue("pmonth", request);
							process_f = "";
							if (Integer.parseInt(pmonth_sel) <= 3) {
								process_f = (Integer.parseInt(pyear_sel) - 1)+ "-" + pyear_sel;
							} else {
								process_f = pyear_sel + "-"+ (Integer.parseInt(pyear_sel) + 1);
							}

							int max_ = 0;
							count_ = obj.getCount("PMS_AME_TRN_SCH_PERFORM_MTH","where PERFORM_DESC_SNO="+ PERFORM_DESC_SNO
									+ " and OFFICE_ID=" + Office_id+ " and SCH_SNO=" + SCH_SNO
									+ " and YEAR= " + pyear_sel + " and MONTH="+pmonth_sel);
							int sr=0;
							int srr=0;
							 
							
							if (count_ == 0)   
							{
								Hashtable med = new Hashtable();
								med.put("PERFORM_DESC_SNO", PERFORM_DESC_SNO);
								med.put("SCH_SNO", SCH_SNO);
								med.put("OFFICE_ID", Office_id);
								med.put("UPDATED_BY_USER_ID", "'"+userid+"'");
								med.put("UPDATED_TIME", "clock_timestamp()");
								med.put("ENTRY_DATE", "clock_timestamp()");
								max_ = 0;
								max_ = obj.getMax("PMS_AME_TRN_SCH_PERFORM_MTH","SCH_PERFORM_SNO", "");
								med.put("SCH_PERFORM_SNO", max_);		
								
								if (Integer.parseInt(DATATYPE_SNO) == 4)
									med.put("REMARKS", "'"+ obj.setValue("QTY_OR_AMOUNT" + i,request) + "'");
								else
									med.put("QTY_OR_AMOUNT", obj.setValue("QTY_OR_AMOUNT" + i, request));

								med.put("FIN_YEAR", "'" + process_f + "'");
								med.put("MONTH", "" + pmonth_sel + "");
								med.put("PROCESS", "'"+ obj.setValue("flag", request) + "'");
								med.put("YEAR", " " + pyear_sel + "");
								String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
								//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
								med.put("PROJECT_ID",project_id);
								
								r += obj.recordSave(med,"PMS_AME_TRN_SCH_PERFORM_MTH ", con);
							}		  		  	

							tab_count_ = obj.getCount("PMS_AME_TRN_SCH_PERFORM_YEAR","where PERFORM_DESC_SNO="+ PERFORM_DESC_SNO
									+ " and OFFICE_ID=" + Office_id+ " and SCH_SNO=" + SCH_SNO
									+ " and FIN_YEAR= '" + process_f + "'");  
							//PMS_AME_TRN_SCH_PERFORM_YEAR   INSERT 	
							
							String amt_value="";
							
							if (tab_count_==0 )
							{
								
								Hashtable med = new Hashtable();
								tab_max_ = obj.getMax("PMS_AME_TRN_SCH_PERFORM_YEAR","SCH_PERFORM_SNO", "");
								med.put("SCH_PERFORM_SNO", tab_max_);
								med.put("FIN_YEAR", "'" + process_f + "'");
								med.put("SCH_SNO", SCH_SNO);
								med.put("PERFORM_DESC_SNO", PERFORM_DESC_SNO);
								String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
								//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
								amt_value="";
								if (Integer.parseInt(DATATYPE_SNO) == 4)
									amt_value="'"+ obj.setValue("QTY_OR_AMOUNT" + i,request) + "'";
								else
									amt_value=obj.setValue("QTY_OR_AMOUNT" + i,request);
								
								if (Integer.parseInt(DATATYPE_SNO) == 4)
									med.put("REMARKS", amt_value);
								else
									med.put("QTY_OR_AMOUNT", amt_value);
								
								med.put("PROCESS", "'"+ obj.setValue("flag", request) + "'");
								med.put("OFFICE_ID", Office_id);
								med.put("UPDATED_BY_USER_ID", "'"+userid+"'");
								med.put("UPDATED_TIME", "clock_timestamp()");
								med.put("PROJECT_ID", project_id);								
								srr= obj.recordSave(med,"PMS_AME_TRN_SCH_PERFORM_YEAR ", con);
								r+=srr;  
							}
							else 
							{   
								Hashtable upd = new Hashtable();
								if (Integer.parseInt(DATATYPE_SNO) == 4)
								{
									upd.put("REMARKS", "'"+ obj.setValue("QTY_OR_AMOUNT" + i,request) + "'");
									upd.put("QTY_OR_AMOUNT","0");
								}
								else
								{
									upd.put("QTY_OR_AMOUNT", " QTY_OR_AMOUNT+"+ obj.setValue("QTY_OR_AMOUNT" + i,request));
									upd.put("REMARKS","''");
								}

								Hashtable cond = new Hashtable();
								cond.put("FIN_YEAR", "'" + process_f + "'");
								cond.put("PERFORM_DESC_SNO", PERFORM_DESC_SNO);
								cond.put("SCH_SNO", SCH_SNO);
								cond.put("OFFICE_ID", Office_id);
								sr += obj.recordSave(upd, cond,"PMS_AME_TRN_SCH_PERFORM_YEAR", con);
							}
						}
					}//for loop end
					
					
					String cond_qry_new_year=" where " +
							"   FIN_YEAR='"+process_f+"'"+
					" and OFFICE_ID="+Office_id+
					" and SCH_SNO="+SCH_SNO+ "" ;
					    
					String del_qry=obj.delRecord("PMS_AME_TRN_SCH_PERFORM_YEAR", cond_qry_new_year, con);	
					for (int i = 1; i <= Integer.parseInt(row); i++) 
					{

						String PERFORM_DESC_SNO = obj.setValue("PERFORM_DESC_SNO" + i, request);
						Hashtable med = new Hashtable();
						tab_max_ = obj.getMax("PMS_AME_TRN_SCH_PERFORM_YEAR","SCH_PERFORM_SNO", "");
						med.put("SCH_PERFORM_SNO", tab_max_);
						med.put("FIN_YEAR", "'" + process_f + "'");
						med.put("SCH_SNO", SCH_SNO);
						med.put("PERFORM_DESC_SNO", PERFORM_DESC_SNO);
						String DATATYPE_SNO = obj.setValue("DATATYPE" + i, request);
						String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
					//	String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
						
						
						
						String amt_value="";  
						if (Integer.parseInt(DATATYPE_SNO) == 4)
							amt_value="'"+ obj.setValue("QTY_OR_AMOUNT" + i,request) + "'";
						else
							amt_value=obj.getValue("pms_ame_trn_sch_perform_mth" ,"sum(qty_or_amount)","qty_or_amount",cond_qry_new_year +" and PERFORM_DESC_SNO="+PERFORM_DESC_SNO);  
						if (Integer.parseInt(DATATYPE_SNO) == 4)
						med.put("REMARKS", amt_value);
						else
						med.put("QTY_OR_AMOUNT", amt_value);
						
						med.put("PROCESS", "'"+ obj.setValue("flag", request) + "'");
						med.put("OFFICE_ID", Office_id);
						med.put("UPDATED_BY_USER_ID", "'"+userid+"'");
						med.put("UPDATED_TIME", "clock_timestamp()");
						med.put("PROJECT_ID", project_id);								
						int srr= obj.recordSave(med,"PMS_AME_TRN_SCH_PERFORM_YEAR ", con);
						r+=srr;   
					}
						/*int rc=obj.getDistinctCount("PMS_AME_TRN_SCH_PERFORM_MTH","month",cond_qry2);
						Hashtable cond = new Hashtable();
						Hashtable upd_new = new Hashtable();
						String QTY_OR_AMOUNT=obj.getValue("PMS_AME_TRN_SCH_PERFORM_MTH", "sum(QTY_OR_AMOUNT)","a", " where FIN_YEAR="+"'"+process_f+"'" +
								" and PERFORM_DESC_SNO=4 and SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);
						double avg= Double.parseDouble(QTY_OR_AMOUNT)/rc;
						upd_new.put("QTY_OR_AMOUNT",avg);       
						Hashtable cond_new = new Hashtable();
						cond_new.put("FIN_YEAR", "'" + process_f + "'");
						cond_new.put("PERFORM_DESC_SNO", "4");  
						cond_new.put("SCH_SNO", SCH_SNO);  
						cond_new.put("OFFICE_ID", Office_id);  
						int  sr=obj.recordSave(upd_new, cond_new,"PMS_AME_TRN_SCH_PERFORM_YEAR", con);
					 
					 	*/
					   
					 
					if (r==0 && count_ > 0)
						res="<response><rows>"+r+"</rows><msg> \n Data Already Exists.. Use Edit option for Updation. </msg></response>";
					else
						res = "<response><rows>" + r + "</rows></response>";
				}

				if (Integer.parseInt(process_code) == 5) 
				{  
					String flag_c = obj.setValue("flag_c", request);    
					res = obj.resultXML("select perform_desc_SNO,perform_DESC,DLIMIT,READONLY, UNITS ,DATATYPE,ACTIVE_STATUS,PROCESS_CODE ,formula,DISPLAYORDER,TEXTBOX_NAME,MANDATORY	  from PMS_AME_MST_DESC where ACTIVE_STATUS='A' and  " 
							+" FYEAR_MTH_FLAG='"+flag_c+"' order by   DISPLAYORDER ",con, obj);
				}  
				if (Integer.parseInt(process_code) == 7)
				{
					pyear_sel = obj.setValue("pyear", request);
					pmonth_sel = obj.setValue("pmonth", request);
					pmonth_sch = obj.setValue("SCH_SNO", request);
					res="<response>";
					res+=obj.resultXML("SELECT (SUM(qty_consumed_net)/1000) as qty FROM PMS_DCB_TRN_MONTHLY_PR "
							+ " WHERE office_id="+Office_id
							+ " AND MONTH="+pmonth_sel+" AND YEAR="+pyear_sel+"AND sch_sno="+pmonth_sch, con, obj,1);
					
					// 22/12/2011
					int count_month = obj.getCount("PMS_AME_TRN_SCH_PERFORM_MTH","where   OFFICE_ID=" + Office_id+ " and SCH_SNO=" + pmonth_sch
							+ " and YEAR= " + pyear_sel + " and MONTH="+pmonth_sel); 

				    int tab_count_year = obj.getCount("PMS_AME_TRN_SCH_PERFORM_YEAR","where  OFFICE_ID=" + Office_id+ " and SCH_SNO=" + pmonth_sch
							+ " and FIN_YEAR= '" + process_f + "'");  
					   
				   
				    res+=obj.generateXML("count_month", Integer.toString(count_month),1, obj);
				    res+=obj.generateXML("tab_count_year", Integer.toString(tab_count_year),1, obj);
					res+=obj.resultXML("select TOTAL_NO_STAFF  from PMS_AME_MST_SCH_DETAILS "
							+ " WHERE office_id="+Office_id
							+ " AND sch_sno="+pmonth_sch, con, obj,1); 
					res+=obj.resultXML("select SUM(DESIGN_QTY) AS DESIGN_QTY  from PMS_AME_TRN_SCH_DP_QTY "
							+ " WHERE office_id="+Office_id  
							+ " AND sch_sno="+pmonth_sch+" and MONTH="+pmonth_sel+" and  YEAR="+pyear_sel, con, obj,1);
					res+=obj.resultXML("select SUM(PUMPING_QTY) AS PUMPING_QTY  from PMS_AME_TRN_SCH_DP_QTY "
							+ " WHERE office_id="+Office_id
							+ " AND sch_sno="+pmonth_sch+" and MONTH="+pmonth_sel+" and  YEAR="+pyear_sel, con, obj,1);  
					
					String QTY_DESIGN=obj.getValue("PMS_AME_MST_SCH_DETAILS", "QTY_DESIGN", "where SCH_SNO="+pmonth_sch+" and OFFICE_ID="+Office_id);

					String DESIGN_PUMPING_HOURS=obj.getValue("PMS_AME_MST_SCH_DETAILS", "DESIGN_PUMPING_HOURS", "where SCH_SNO="+pmonth_sch+" and OFFICE_ID="+Office_id);

					String PUMED_DPR=obj.getValue("PMS_AME_TRN_SCH_DP_QTY", "PUMPING_QTY", "where SCH_SNO="+pmonth_sch+"	and  MONTH="+pmonth_sel+" and YEAR="+pyear_sel+"  and OFFICE_ID="+Office_id);
					res+=obj.generateXML("PUMED_DPR", PUMED_DPR,1, obj);
					res+=obj.generateXML("QTY_DESIGN", QTY_DESIGN,1, obj);
					res+=obj.generateXML("DESIGN_PUMPING_HOURS", DESIGN_PUMPING_HOURS,1, obj);
					String daycout=obj.month_val2(Integer.parseInt(pmonth_sel), Integer.parseInt(pyear_sel));
					res+=obj.generateXML("totalday", daycout,1, obj);
					res+="</response>";   
				}
				if (Integer.parseInt(process_code) == 8) 
				{
					pyear_sel = obj.setValue("pyear", request);
					pmonth_sel = obj.setValue("pmonth", request);
					pmonth_sch = obj.setValue("SCH_SNO", request);
					res = obj.resultXML(" SELECT (SUM(qty_consumed_net)/1000) as netqty FROM PMS_DCB_TRN_MONTHLY_PR "
							+ " WHERE office_id="+Office_id+" AND MONTH="+pmonth_sel+" AND YEAR="+pyear_sel
							+ " AND sch_sno IN "
							+ " ( SELECT SCH_SNO FROM PMS_SCH_MASTER WHERE sch_type_id=12  )",con, obj);
				}
				if (Integer.parseInt(process_code) == 9) 
				{
					pyear_sel = obj.setValue("pyear", request);
					pmonth_sel = obj.setValue("pmonth", request);
					pmonth_sch = obj.setValue("SCH_SNO", request);
					res = obj.resultXML( " SELECT SUM(TOTAL_AMT)   as netamt FROM PMS_DCB_WC_BILLING "
							+ " WHERE office_id=" + Office_id
							+ " AND MONTH      =" + pmonth_sel
							+ " " + " AND YEAR="
							+ pyear_sel + ""
							+ " AND   SCHEME_SNO   = "
							+ pmonth_sch + " ", con, obj);
				}
				else if (Integer.parseInt(process_code) == 11) 
				{ 
					pyear_sel = obj.setValue("fyear", request);
					pmonth_sel = obj.setValue("pmonth", request);
					pmonth_sch = obj.setValue("sch", request);
					process_f = "";

					if (Integer.parseInt(pmonth_sel) <= 3) 
					{
						process_f = (Integer.parseInt(pyear_sel) - 1)+ "-" + pyear_sel;
					} else {
						process_f = pyear_sel + "-"+ (Integer.parseInt(pyear_sel) + 1);
					}

					query=" select a.SCH_PERFORM_SNO as SCH_PERFORM_SNO   ,  a.REMARKS as REMARKS  ,a.PERFORM_DESC_SNO as PERFORM_DESC_SNO, a.QTY_OR_AMOUNT as QTY_OR_AMOUNT,b.DATATYPE as DATATYPE  from PMS_AME_TRN_SCH_PERFORM_MTH a ,PMS_AME_MST_DESC b where b.ACTIVE_STATUS='A' and  a.perform_desc_sno= b.perform_desc_sno ";
					condition=" and	 OFFICE_ID="+Office_id+" and" +
					" MONTH ="+pmonth_sel+" and " +
					" FIN_YEAR ='"+process_f+"'" +
					" and YEAR="+pyear_sel+
					" and SCH_SNO="+pmonth_sch +" order by DISPLAYORDER ";       
					System.out.println(query+condition);    
					res = obj.resultXML(query+condition,con,obj);
				}
				else if (Integer.parseInt(process_code) == 13)
				{ 
					  pyear_sel = obj.setValue("fyear", request);
					  pmonth_sch = obj.setValue("sch", request);
					  process_f = pyear_sel;						 
					  query=" select SCH_PERFORM_SNO, PERFORM_DESC_SNO,  DECODE( PERFORM_DESC_SNO,7,remarks, QTY_OR_AMOUNT )AS QTY_OR_AMOUNT  from PMS_AME_TRN_SCH_PERFORM_YEAR ";
					  condition=" where OFFICE_ID="+Office_id+" and"+" FIN_YEAR ='"+process_f+"'"+" and SCH_SNO="+pmonth_sch +" order by SCH_PERFORM_SNO ";
					  res = obj.resultXML(query+condition,con,obj);
				}  
				else if (Integer.parseInt(process_code) == 6) {
					
					String flag_c = obj.setValue("flag_c", request);
					String fyear = obj.setValue("fyear", request);  
					String sch = obj.setValue("sch", request);

					if (flag_c.equalsIgnoreCase("M")) {
						String fmonth = obj.setValue("pmonth", request);
						res = obj.resultXML("SELECT EXP.REMARKS,EXP.ACTUAL_EXP,psno.perform_desc_SNO,psno.perform_DESC,psno.UNITS FROM ( select perform_desc_SNO,perform_DESC,UNITS from PMS_AME_MST_DESC where ACTIVE_STATUS='A' and FYEAR_MTH_FLAG='"
								+ flag_c
								+ "' )psno "
								+ " JOIN  (SELECT ACTUAL_EXP, "
								+ "   PERFORM_DESC_SNO ,REMARKS "
								+ "  FROM PMS_AME_TRN_SCH_ACTUAL_EXP "
								+ "  WHERE MONTH  ="
								+ fmonth
								+ "   AND YEAR     ="
								+ fyear
								+ "   AND SCH_SNO  = "
								+ sch
								+ "  AND office_id="
								+ Office_id
								+ "   ) EXP "
								+ " ON EXP.PERFORM_DESC_SNO=psno.PERFORM_DESC_SNO "
								+ " ORDER BY psno.perform_desc_SNO ",con, obj);



					} else {
						res = obj.resultXML("SELECT EXP.QTY_OR_AMOUNT,psno.perform_desc_SNO,psno.perform_DESC FROM ( select perform_desc_SNO,perform_DESC from PMS_AME_MST_DESC where ACTIVE_STATUS='A' and  FYEAR_MTH_FLAG='"
								+ flag_c
								+ "' )psno "
								+ " JOIN  (SELECT QTY_OR_AMOUNT, "
								+ "   PERFORM_DESC_SNO "
								+ "  FROM PMS_AME_TRN_SCH_PERFORM "
								+ "  WHERE   FIN_YEAR     ='"
								+ fyear
								+ "'"
								+ "   AND SCH_SNO  = "
								+ sch
								+ "  AND office_id="
								+ Office_id
								+ "   ) EXP "
								+ " ON EXP.PERFORM_DESC_SNO=psno.PERFORM_DESC_SNO "
								+ " ORDER BY psno.perform_desc_SNO ",con, obj);
					}
					if (res.equalsIgnoreCase("")) {
						res = "<response><row_count>0</row_count></response>";
					}
				}
				else if (Integer.parseInt(process_code) == 4) 
				{ 
					sch_sno= obj.setValue("sch_sno", request);
					pyear= obj.setValue("pyear", request);
					res=obj.delRecord("PMS_AME_TRN_SCH_PERFORM_YEAR", "where office_id="+Office_id+" and FIN_YEAR='"+pyear+"' and sch_sno="+sch_sno, con);
					if (r > 0) {
						res = "<response><rows>" + r + "</rows></response>";
					}	else
					{
						res = "<response><rows>" + r + "</rows></response>";
					}

				}else if (Integer.parseInt(process_code) == 15) 
				{  
					String flag_c = obj.setValue("flag_c", request);    
					res = obj.resultXML("select perform_desc_SNO,perform_DESC,DLIMIT,READONLY, UNITS ,DATATYPE,ACTIVE_STATUS,PROCESS_CODE ,formula,DISPLAYORDER,TEXTBOX_NAME,MANDATORY	  from PMS_AME_MST_DESC where ACTIVE_STATUS='A' and  " 
							+" FYEAR_MTH_FLAG='"+flag_c+"' order by   DISPLAYORDER ",con, obj);
				}  
			}  
			if (Integer.parseInt(type) == 3) {
				if (Integer.parseInt(process_code) == 1) 
				{
					 row = obj.setValue("rows", request);
					for (int i = 1; i <= Integer.parseInt(row); i++) 
					{
						String SCH_SNO=obj.setValue("SCH_SNO", request);
						int max_ = obj.getMax("PMS_AME_TRN_SCH_ACTUAL_EXP","ACTUAL_EXP_SNO", "");
 						cols.put("ACTUAL_EXP_SNO", max_);
						cols.put("PERFORM_DESC_SNO", obj.setValue("MAIN_ITEM_SNO" + i, request));
						cols.put("SCH_SNO", SCH_SNO);
						cols.put("FIN_YEAR", "'"+ obj.setValue("pyear", request) + "'");
						cols.put("OFFICE_ID", Office_id);
						cols.put("AM_EST_AMT", obj.setValue("AM_EST_AMT" + i,request));
						cols.put("UPDATED_BY_USER_ID", "'"+userid+"'");
						cols.put("ENTRY_DATE", "clock_timestamp()");
						cols.put("UPDATED_TIME", "clock_timestamp()");	
						String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
						//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
						cols.put("PROJECT_ID", project_id);	

						r = obj.recordSave(cols, "PMS_AME_TRN_ABSTRACT ", con);
						if (r > 0) {
							res = "<response><rows>" + r + "</rows></response>";
						}
					}
				}
				// 	ITEM WISE BREAK UP  NEW
				else if (Integer.parseInt(process_code) ==12) {
					String cond = obj.setValue("cond", request);
					String agvalue=obj.getValue("PMS_TENDER_AGREEMENT", "AGREE_AMOUNT", "where AGREE_SNO="+cond);
					res = "<response><agvalue>" + agvalue + "</agvalue></response>";
				}  
				else if (Integer.parseInt(process_code) == 6)
				{  
					String flag_c = obj.setValue("flag_c", request); 
				 	//res = obj.resultXML("select perform_desc_SNO,perform_DESC,READONLY, UNITS,DLIMIT,DATATYPE,DISPLAYORDER,formula,TEXTBOX_NAME  from PMS_AME_MST_DESC where ACTIVE_STATUS='A' and  FYEAR_MTH_FLAG='"
					//						+ flag_c+ "' order by   DISPLAYORDER ",con, obj);
					 res = obj.resultXML("select perform_desc_SNO,perform_DESC,DLIMIT,READONLY, UNITS ,DATATYPE,ACTIVE_STATUS,PROCESS_CODE ,formula,DISPLAYORDER,TEXTBOX_NAME,MANDATORY	  from PMS_AME_MST_DESC where ACTIVE_STATUS='A' and  " 
							 +" FYEAR_MTH_FLAG='"+flag_c+"' order by   DISPLAYORDER ",con, obj);
				}
				// Scheme Performance - delete
				else if (Integer.parseInt(process_code) == 4)
				{
					sch_sno=obj.setValue("sch_sno", request);
					pyear=obj.setValue("pyear", request);
					pmonth=obj.setValue("pmonth", request);
					int row_del=0;
					String flag_c = obj.setValue("flag_c", request);
					// Afftected Table PMS_AME_TRN_SCH_PERFORM_YEAR,PMS_AME_TRN_SCH_PERFORM_MTH,PMS_AME_TRN_SCH_ACT_EXP_ITEM,PMS_AME_EXP_ITEM_BREAKUP
					// Key for Delete :  OFFICE_ID,FIN_YEAR,	MONTH,SCH_SNO  
					try
					{
						if (Integer.parseInt(pmonth) <= 3) 
						{
							process_f = ""+(Integer.parseInt(pyear) - 1)+ "-" + pyear+"";
						} else {
							process_f = ""+pyear + "-"+ (Integer.parseInt(pyear) + 1)+"";
						}
						String qry="select * from PMS_AME_TRN_SCH_PERFORM_MTH where SCH_SNO="+sch_sno+"  and office_id="+Office_id+" and FIN_YEAR='"+process_f+"' and MONTH="+pmonth;
						ResultSet rs = obj.getRS(qry);
						String psno="",qty="";
						while (rs.next())   
						{
							psno=rs.getString("PERFORM_DESC_SNO");
							type=obj.getValue("PMS_AME_MST_DESC", "DATATYPE", "where PERFORM_DESC_SNO="+psno);
							qty= rs.getString("QTY_OR_AMOUNT");
							String up="";
							
							if (Integer.parseInt(psno)!=4)
							{
								if (type.equalsIgnoreCase("4"))
								up="UPDATE PMS_AME_TRN_SCH_PERFORM_YEAR set QTY_OR_AMOUNT='' where PERFORM_DESC_SNO="+psno+"  and SCH_SNO= "+sch_sno+" and office_id= "+Office_id+"  and FIN_YEAR='"+process_f+"'";
								else
								up="UPDATE PMS_AME_TRN_SCH_PERFORM_YEAR set QTY_OR_AMOUNT=(QTY_OR_AMOUNT-("+qty+")) where PERFORM_DESC_SNO="+psno+"  and SCH_SNO= "+sch_sno+" and office_id= "+Office_id+"  and FIN_YEAR='"+process_f+"'";
								PreparedStatement ps=con.prepareStatement(up);
								ps.execute();   
							}
						}
						row_del=obj.delRecord("PMS_AME_TRN_SCH_PERFORM_MTH", "where SCH_SNO= "+sch_sno+" and office_id= "+Office_id+"  and FIN_YEAR='"+process_f+"' and MONTH= "+pmonth, con,1);
						obj.delRecord("PMS_AME_EXP_ITEM_BREAKUP" ,"where SCH_SNO= "+sch_sno+" and office_id= "+Office_id+"  and YEAR='"+pyear+"' and MONTH= "+pmonth, con);  
						obj.delRecord("PMS_AME_TRN_SCH_ACT_EXP_ITEM","where SCH_SNO= "+sch_sno+" and office_id= "+Office_id+"  and FIN_YEAR='"+process_f+"' and MONTH= "+pmonth, con);
						int cc=2;
						ResultSet rs_new=obj.getRS("select count(distinct month) as ct from PMS_AME_TRN_SCH_PERFORM_MTH where SCH_SNO= "+sch_sno+" and office_id= "+Office_id+"  and FIN_YEAR='"+process_f+"'");								
						while (rs_new.next())
						{
							cc=rs_new.getInt("ct");
						}
						if (cc==0)
							obj.delRecord("PMS_AME_TRN_SCH_PERFORM_YEAR","where SCH_SNO= "+sch_sno+" and office_id= "+Office_id+"  and FIN_YEAR='"+process_f+"'",con);
						String cond_qry2=" where   FIN_YEAR='"+process_f+"'"+    
						" and OFFICE_ID="+Office_id+
						" and SCH_SNO="+sch_sno+ "" ;  
						int rc=obj.getDistinctCount("PMS_AME_TRN_SCH_PERFORM_MTH","month",cond_qry2);
						Hashtable cond = new Hashtable();
						Hashtable upd_new = new Hashtable();
						String QTY_OR_AMOUNT=obj.getValue("PMS_AME_TRN_SCH_PERFORM_MTH", "sum(QTY_OR_AMOUNT)","a", " where FIN_YEAR="+"'"+process_f+"'" +
								" and PERFORM_DESC_SNO=4 and SCH_SNO="+sch_sno+" and OFFICE_ID="+Office_id);
						double avg= Double.parseDouble(QTY_OR_AMOUNT)/rc;
						upd_new.put("QTY_OR_AMOUNT",avg);       
						Hashtable cond_new = new Hashtable();    
						cond_new.put("FIN_YEAR", "'" + process_f + "'");
						cond_new.put("PERFORM_DESC_SNO", "4");  
						cond_new.put("SCH_SNO", sch_sno);    
						cond_new.put("OFFICE_ID", Office_id);  
					   int  sr=obj.recordSave(upd_new, cond_new,"PMS_AME_TRN_SCH_PERFORM_YEAR", con);
						res = "<response><res>"+row_del+"</res><rows>1</rows></response>";
					}catch(Exception e )
					{
						res = "<response><res>"+row_del+"</res><rows>0</rows></response>";
						System.out.println("e"+e);  
					}
				}
				// 	ITEM WISE BREAK UP  EDIT  
				else if (Integer.parseInt(process_code) == 5 ||  Integer.parseInt(process_code)==11 || Integer.parseInt(process_code) == 7|| Integer.parseInt(process_code) == 14) 
				{
					condition="";
					String sub_cond="";      
					String sub_cond_value="";
					System.out.println("TEST");   
					Servlets.PMS.PMS1.DCB.servlets.Controller obj2=new  Servlets.PMS.PMS1.DCB.servlets.Controller();
					Connection con_local=obj2.con();
					obj2.createStatement(con_local);
				
					if (Integer.parseInt(process_code)==11 || Integer.parseInt(process_code)==5 ||   Integer.parseInt(process_code)==14)
					{
						pyear_sel = obj.setValue("fyear", request);
						pmonth_sel = obj.setValue("pmonth", request);
						pmonth_sch = obj.setValue("sch", request);
  
						process_f = "";
						if (Integer.parseInt(pmonth_sel) <= 3) 
						{
							process_f = (Integer.parseInt(pyear_sel) - 1)+ "-" + pyear_sel;
						} else {
							process_f = pyear_sel + "-"+ (Integer.parseInt(pyear_sel) + 1);
						}	  

						sub_cond_value=",exp. ACTUAL_EXP,exp.ACTUAL_EXP_SNO";
						sub_cond=" LEFT OUTER JOIN " +
						"  ( SELECT PERFORM_DESC_SNO,GROUP_SNO,MAIN_ITEM_SNO,SUB_ITEM_SNO,ACTUAL_EXP,ACTUAL_EXP_SNO " +
						"  FROM PMS_AME_TRN_SCH_ACT_EXP_ITEM " +
						"  WHERE FIN_YEAR='"+process_f+"' AND MONTH="+pmonth_sel+" AND YEAR="+pyear_sel+" AND SCH_SNO="+pmonth_sch+
						"  AND PERFORM_DESC_SNO=3 " +
						" )exp " +
						" ON exp.GROUP_SNO      =G.GROUP_SNO AND exp.MAIN_ITEM_SNO =M.MAIN_ITEM_SNO " +
						" AND ( exp.SUB_ITEM_SNO=S.SUB_ITEM_SNO OR exp.SUB_ITEM_SNO   =0)";
					}	 

					String qry = " SELECT G.GROUP_SNO,m.MAIN_ITEM_SNO,CASE WHEN S.SUB_ITEM_SNO IS NULL "
						+ " THEN 0 ELSE S.SUB_ITEM_SNO END AS SUB_ITEM_SNO, "
						+ "  G.GROUP_DESC,m.MAIN_ITEM_DESC,m.STATICFLAG,case  "
						+ "  when S.SUB_ITEM_DESC is null "
						+ "  then '' "
						+ "  else "
						+ "  S.SUB_ITEM_DESC "     
						+ " end  "
						+ " as  "  
						+ " SUB_ITEM_DESC "+ sub_cond_value 
						+ " FROM "
						+ " ( SELECT GROUP_SNO, GROUP_DESC FROM PMS_AME_MST_GROUP where GROUP_SNO=1 ORDER BY GROUP_SNO "
						+ " )G "  
						+ " JOIN "  
						+ " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC, GROUP_SNO,STATICFLAG FROM PMS_AME_MST_MAIN_ITEM "
						+ " )M "
						+ " ON M.GROUP_SNO=G.GROUP_SNO "
						+ " LEFT OUTER JOIN "
						+ " ( SELECT SUB_ITEM_SNO ,SUB_ITEM_DESC,MAIN_ITEM_SNO FROM PMS_AME_MST_SUB_ITEM "
						+ " )S "
						+ " ON S.MAIN_ITEM_SNO=M.MAIN_ITEM_SNO  " +sub_cond + 							
						"ORDER BY g.GROUP_SNO,  MAIN_ITEM_SNO  ,SUB_ITEM_SNO";
				 
					if (process_code.equalsIgnoreCase("5"))
					{
						String sch_sno1= obj.setValue("sch_sno", request);
						String pyear1= obj.setValue("pyear", request);
						String pmonth1= obj.setValue("pmonth", request);
						process_f = pyear1 + "-"+ (Integer.parseInt(pyear1) + 1);
						/*   qry = " SELECT gr as GROUP_SNO,(SELECT GROUP_DESC  FROM PMS_AME_MST_GROUP where group_sno=gr) as GROUP_DESC, "
								+ "  msno as MAIN_ITEM_SNO,(SELECT MAIN_ITEM_DESC  FROM PMS_AME_MST_MAIN_ITEM where MAIN_ITEM_SNO=msno) as MAIN_ITEM_DESC , "
								+ "   subsno as SUB_ITEM_SNO,(SELECT  SUB_ITEM_DESC  FROM PMS_AME_MST_SUB_ITEM where SUB_ITEM_SNO=subsno) as  SUB_ITEM_DESC, "
								+ "   (SELECT APPLY_CENTAGE  FROM PMS_AME_MST_MAIN_ITEM where MAIN_ITEM_SNO=msno) as APPLY_CENTAGE "
								+ "  FROM "
								+ "  (SELECT GROUP_SNO AS gr, "
								+ "  MAIN_ITEM_SNO   AS msno, "
								+ "  SUB_ITEM_SNO    AS subsno "
								+ "  FROM PMS_AME_TRN_SCHEME_ITEM where office_id="+Office_id+" and fin_year='"+process_f+"' and SCH_SNO="+sch_sno1
								+ "  )mas  "
								+ "  ORDER BY  GROUP_SNO, "
								+ "  MAIN_ITEM_SNO , "
						  		+ "  SUB_ITEM_SNO ";  
						*/
						res="<response>"; 						 
						res+=obj.resultXML(qry, con, obj,1);
						String head1="";
			 			String head2=""; 
			 			String head1_amount="0";
			 			String head2_amount="0";
			 			Double final_vlaue=0.0;
			 			BigDecimal bd = null  ;
			 			try
			 			{     
			 				String pid=obj.getValue("PMS_MST_PROJECTS_VIEW","PROJECT_ID"," where SCH_SNO="+sch_sno1);    	

			 			 head1=obj2.getValue("COM_MST_ACCOUNT_HEADS", "ACCOUNT_HEAD_DESC","where ACCOUNT_HEAD_CODE=211906");;
			 			 head2=obj2.getValue("COM_MST_ACCOUNT_HEADS", "ACCOUNT_HEAD_DESC","where ACCOUNT_HEAD_CODE=212006");;
			 			 String cond=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"  and SUB_LEDGER_CODE="+pid+" and CASHBOOK_YEAR="+pyear1
					 			+" and CASHBOOK_MONTH="+pmonth1+" and SUB_LEDGER_TYPE_CODE= 10 " 
					 			+" and ACCOUNT_HEAD_CODE in (222102,222103,222104,222105,222106,222107,222108,222109,222111) and VOUCHER_NO in ( " 
				    			+" select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
				    			+" and CASHBOOK_YEAR="+pyear1+" and CASHBOOK_MONTH="+pmonth1+" and JOURNAL_TYPE_CODE =11 " 
				      			+" and JOURNAL_STATUS='L')";
			 			  
			 			 String  ac=" 200607,200608,200609,200610,200611,200612,200613,200614,200615,200620,200621,200622,200623,200624,200625,200626,200627,200628,200629,200630,200631,200632,200633,200634,200635,200636,200637,200638,200639,200640,200642, 200651, 200652,  220601,200650 ";  
			 			 head1_amount=obj2.getValue("FAS_JOURNAL_TRANSACTION", "ROUND(( sum(AMOUNT)*(2/100) ) ,2) ","val1",cond);
			 			 head2_amount=obj2.getValue("FAS_JOURNAL_TRANSACTION", "ROUND(( sum(AMOUNT)*(1/100) )  ,2)","val2",cond);
			 			 String cond1=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"    and CASHBOOK_YEAR="+pyear1
				 			+" and CASHBOOK_MONTH="+pmonth1+"   " 
				 			+" and ACCOUNT_HEAD_CODE in ("+ac+") and VOUCHER_NO in ( " 
			    			+" select VOUCHER_NO from FAS_JOURNAL_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
			    			+" and CASHBOOK_YEAR="+pyear1+" and CASHBOOK_MONTH="+pmonth1//and JOURNAL_TYPE_CODE =11 " 
			      			+" and JOURNAL_STATUS='L')";
			 			String cond2=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"    and CASHBOOK_YEAR="+pyear1
			 			+" and CASHBOOK_MONTH="+pmonth1+"   " 
			 			+" and ACCOUNT_HEAD_CODE in ("+ac+") and VOUCHER_NO in ( " 
		    			+" select VOUCHER_NO from FAS_PAYMENT_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
		    			+" and CASHBOOK_YEAR="+pyear1+" and CASHBOOK_MONTH="+pmonth1//and JOURNAL_TYPE_CODE =11 " 
		      			+" and PAYMENT_STATUS='L')";
			 			 
			 			 String cond3=" where ACCOUNTING_FOR_OFFICE_ID="+Office_id+"    and CASHBOOK_YEAR="+pyear1
				 			+" and CASHBOOK_MONTH="+pmonth1+"   " 
				 			+" and ACCOUNT_HEAD_CODE in ("+ac+") and RECEIPT_NO in ( " 
			    			+" select RECEIPT_NO from FAS_RECEIPT_MASTER where ACCOUNTING_FOR_OFFICE_ID="+Office_id
			    			+" and CASHBOOK_YEAR="+pyear1+" and CASHBOOK_MONTH="+pmonth1//and JOURNAL_TYPE_CODE =11 " 
			      			+" and RECEIPT_STATUS='L')";
			 			 String head1_amount_j=obj2.getValue("FAS_JOURNAL_TRANSACTION", "sum(AMOUNT) ","val2",cond1);
			    			 String head1_amount_p=obj2.getValue("FAS_PAYMENT_TRANSACTION", "sum(AMOUNT) ","val3",cond2);
			 			 String head1_amount_r=obj2.getValue("fas_receipt_transaction", "sum(AMOUNT) ","val4",cond3);
			 			 String div_qty=obj2.getValue("PMS_DCB_TRN_MONTHLY_PR", "sum(QTY_CONSUMED_NET) ","val5"," where OFFICE_ID="+Office_id+" and month="+pmonth1+" and year="+pyear1);
			 			String div_sch_qty=obj2.getValue("PMS_DCB_TRN_MONTHLY_PR", "sum(QTY_CONSUMED_NET) ","val5"," where OFFICE_ID="+Office_id+" and month="+pmonth1+" and year="+pyear1+" and SCH_SNO="+sch_sno1);
			 			
			 			Double net_amt=( ( Double.parseDouble(head1_amount_j)+Double.parseDouble(head1_amount_p) ) -Double.parseDouble(head1_amount_r));			 			 
			 			final_vlaue= ( ( net_amt*Double.parseDouble(div_sch_qty) ) /Double.parseDouble(div_qty) ) ; // / 100000;
			 			 
			 			 bd = new BigDecimal(Double.toString(final_vlaue));
			 		     bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
			 			}catch(Exception e) {         
			 				System.out.println(e);
			 			}  
			 			res+="<sup_cost>"+bd+"</sup_cost>";
			 			res+="<head1_211906>"+head1_amount+"</head1_211906>";
			 			res+="<head1_212006>"+head2_amount+"</head1_212006>";
						res+= "</response>";
						con_local.close();
					}
					else
					{
					res = obj.resultXML(qry, con, obj);
					}
					ResultSet rs_acc=obj.getRS(qry);
					while (rs_acc.next())
					{
						String mitem=rs_acc.getString("MAIN_ITEM_SNO");
						String sitem=rs_acc.getString("SUB_ITEM_SNO");
						String ac=obj.getValue("PMS_AME_MST_ITEM_ACC_CODE", "ACCOUNT_HEAD_CODE", "where MAIN_ITEM_SNO="+mitem+" and SUB_ITEM_SNO="+sitem);
						String ac1=obj.getValue("PMS_AME_MST_ITEM_ACC_CODE", "ACCOUNT_HEAD_CODE", "where MAIN_ITEM_SNO="+mitem+" and SUB_ITEM_SNO="+sitem);
					}
				}   
			}
			if (Integer.parseInt(type) == 4)
			{
				  tab_count_=0;
				  row = obj.setValue("rows", request);
				  process_f= obj.setValue("pyear", request);
				  	for (int i = 1; i <= Integer.parseInt(row); i++) {
 					String PERFORM_DESC_SNO = obj.setValue("PERFORM_DESC_SNO" + i, request);
					String SCH_SNO = obj.setValue("SCH_SNO", request);
				    tab_count_ = obj.getCount("PMS_AME_TRN_SCH_PERFORM_YEAR","where PERFORM_DESC_SNO="+ PERFORM_DESC_SNO+ " and OFFICE_ID=" + Office_id+ " and SCH_SNO=" + SCH_SNO+ " and FIN_YEAR= '" + process_f + "'");  
				    //PMS_AME_TRN_SCH_PERFORM_YEAR   INSERT
				    String dataflag= obj.setValue("dataflag", request);
				    if (Integer.parseInt(dataflag)==2 &&  tab_count_!=0)
				    {
				    	String del_=obj.delRecord("PMS_AME_TRN_SCH_PERFORM_YEAR", "where PERFORM_DESC_SNO="+ PERFORM_DESC_SNO+ " and OFFICE_ID=" + Office_id+ " and SCH_SNO=" + SCH_SNO+ " and FIN_YEAR= '" + process_f + "'", con);
				    	tab_count_=0;
				    }
					if (tab_count_==0 )								
					{
						 tab_max_ = obj.getMax("PMS_AME_TRN_SCH_PERFORM_YEAR","SCH_PERFORM_SNO", "");
						 tab1.put("SCH_PERFORM_SNO", tab_max_);
						 tab1.put("FIN_YEAR", "'" + process_f + "'");
						 tab1.put("SCH_SNO", SCH_SNO);
						 tab1.put("PERFORM_DESC_SNO", PERFORM_DESC_SNO);
						 //String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
						 String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);

						if (Integer.parseInt(PERFORM_DESC_SNO) == 7)
							tab1.put("REMARKS", "'"+ obj.setValue("QTY_OR_AMOUNT" + i,request) + "'");
							else
							tab1.put("QTY_OR_AMOUNT", " "+ obj.setValue("QTY_OR_AMOUNT" + i,request));
							tab1.put("PROCESS", "'"+ obj.setValue("flag", request) + "'");
							tab1.put("OFFICE_ID", Office_id);
							tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
							tab1.put("UPDATED_TIME", "clock_timestamp()");
							tab1.put("PROJECT_ID", project_id);
							r += obj.recordSave(tab1,"PMS_AME_TRN_SCH_PERFORM_YEAR ", con);
					}else
					{
						r=0;
					}
			}
				if (r==0 && tab_count_ > 0)
					res="<response><rows>"+r+"</rows><msg> \n Record Already Present.Use Edit Option if you Want Edit. </msg></response>";
				else
					res = "<response><rows>" + r + "</rows></response>";
				
			}
			///////////////////   TYPE 5 ///////////////////////////////// 
			if (Integer.parseInt(type) == 5) {
				if (Integer.parseInt(process_code) == 1)
				{
					// Budget Estimate Entry : New Entry
					String SCH_SNO = obj.setValue("SCH_SNO", request);
					String FIN_YEAR= obj.setValue("pyear", request);
					String ref= obj.setValue("ref", request);
					String refdate= obj.setValue("refdate", request);
					String adminref= obj.setValue("adminref", request);
					String adminrefdate= obj.setValue("adminrefdate", request);
					String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+SCH_SNO+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
					//String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+SCH_SNO);
					String budamt= obj.setValue("budamt", request);
					String ORG_REV= obj.setValue("ORG_REV", request);
					tab_max_ = obj.getMax("PMS_AME_TRN_BUDGET","BUDGET_SNO", "");				
					int TECH_SANC_NO = obj.getMax("PMS_AME_TRN_BUDGET","TECH_SANC_NO", " where SCH_SNO="+SCH_SNO+" and FIN_YEAR='"+FIN_YEAR+"' and office_id="+Office_id);
					
					tab1.put("BUDGET_SNO", tab_max_);
					tab1.put("FIN_YEAR", "'" + FIN_YEAR + "'");
					tab1.put("SCH_SNO", SCH_SNO);
					tab1.put("TECH_REF_NO",  "'" +ref+ "'");  
					tab1.put("TECH_REF_DATE", "to_date('"+refdate+"','DD/MM/YYYY')");
					tab1.put("ADMIN_REF_NO",  "'" +adminref+ "'");
					tab1.put("ADMIN_REF_DATE", "to_date('"+adminrefdate+"','DD/MM/YYYY')");
					tab1.put("BUDGET_EST_AMT", budamt);
					tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
					tab1.put("UPDATED_TIME", "clock_timestamp()");
					tab1.put("ENTRY_DATE", "clock_timestamp()");
					tab1.put("OFFICE_ID", Office_id);
					tab1.put("PROJECT_ID", project_id); 
					tab1.put("ORG_REV", "'"+ORG_REV+"'");   
					tab1.put("TECH_SANC_NO", "'"+TECH_SANC_NO+"'");
					//if (TECH_SANC_NO==1) 
					r = obj.recordSave(tab1, "PMS_AME_TRN_BUDGET ", con);
					if (r > 0) {
						res = "<response><rows>" + r + "</rows></response>";
					}
				}	
				else if (Integer.parseInt(process_code) == 2)
				{
					// Budget Estimate Entry : Edit 
					String SCH_SNO = obj.setValue("SCH_SNO", request);
					String FIN_YEAR= obj.setValue("pyear", request);
					String ref= obj.setValue("ref", request);
					String refdate= obj.setValue("refdate", request);
				//	String revbudamt= obj.setValue("revbudamt", request);
					//String mainestamt= obj.setValue("mainestamt", request);
					String budamt= obj.setValue("budamt", request);
					String key_value= obj.setValue("key_value", request);
					String adminref= obj.setValue("adminref", request);
					String adminrefdate= obj.setValue("adminrefdate", request);
					tab1.put("FIN_YEAR", "'" + FIN_YEAR + "'");
					tab1.put("SCH_SNO", SCH_SNO);
					tab1.put("TECH_REF_NO",  "'" +ref+ "'");  
					tab1.put("TECH_REF_DATE", "to_date('"+refdate+"','DD/MM/YYYY')");
					tab1.put("ADMIN_REF_NO",  "'" +adminref+ "'");
					tab1.put("ADMIN_REF_DATE", "to_date('"+adminrefdate+"','DD/MM/YYYY')");
					tab1.put("BUDGET_EST_AMT", budamt);
					tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
					tab1.put("UPDATED_TIME", "clock_timestamp()");
					tab1.put("OFFICE_ID", Office_id);   

					condht.put("BUDGET_SNO", key_value);
					r = obj.recordSave(tab1, condht,"PMS_AME_TRN_BUDGET ", con);
					if (r > 0) {
						res = "<response><rows>" + r + "</rows></response>";
					}

				}	
				else if (Integer.parseInt(process_code) == 9) 
				{	
					String sch = obj.setValue("sch", request);
					pyear= obj.setValue("pyear", request);				
					pmonth= obj.setValue("pmonth", request);
					String drow= obj.delRecord("PMS_AME_EXP_ITEM_BREAKUP", " where OFFICE_ID="+Office_id+" and MONTH="+pmonth+"  and YEAR="+pyear+" and SCH_SNO="+sch+"", con);
				}	
			else if (Integer.parseInt(process_code) == 6) {
					
					fyear_i = obj.setValue("fyear", request);
					sch_i = obj.setValue("sch", request);
					String total_amt=obj.getValue("PMS_AME_TRN_ABSTRACT", "sum(AM_EST_AMT)"," where FIN_YEAR='"+fyear_i+"' and OFFICE_ID="+Office_id+" and SCH_SNO="+sch_i);
					String count_c=obj.getValue("PMS_AME_TRN_BUDGET", "count(*)"," where FIN_YEAR='"+fyear_i+"' and OFFICE_ID="+Office_id+" and SCH_SNO="+sch_i);
					String count_c1=obj.getValue("PMS_AME_TRN_BUDGET", "TECH_SANC_NO"," where FIN_YEAR='"+fyear_i+"' and OFFICE_ID="+Office_id+" and SCH_SNO="+sch_i+" and ORG_REV='O'");
					
				     res = "<response><count_c>"+count_c+"</count_c><count_c1>"+count_c1+"</count_c1><total_amt>"+total_amt+"</total_amt></response>";
				}  
				// Actual Expenditure  Break Up Data Add to DB
			else if (Integer.parseInt(process_code) == 7) {

				String  tot_row = obj.setValue("tot_row", request);
				int i=0;
				String sch = obj.setValue("sch", request);
				pyear= obj.setValue("pyear", request);				
				pmonth= obj.setValue("pmonth", request);
				String SUB_ITEM_SNO= obj.setValue("SUB_ITEM_SNO", request);
				String MAIN_ITEM_SNO= obj.setValue("MAIN_ITEM_SNO", request);
				String GROUP_SNO= obj.setValue("GROUP_SNO", request);
				String dataflag= obj.setValue("dataflag", request);
				int ins_row=0;
				String drow ;
		//		if (Integer.parseInt(dataflag)==2)  
					drow= obj.delRecord("PMS_AME_EXP_ITEM_BREAKUP", " where OFFICE_ID="+Office_id+" and MONTH="+pmonth+" and YEAR="+pyear+" and SCH_SNO="+sch+" and  GROUP_SNO="+GROUP_SNO+" and  MAIN_ITEM_SNO="+MAIN_ITEM_SNO+" and SUB_ITEM_SNO ="+SUB_ITEM_SNO, con);

				for (i=1;i<=Integer.parseInt(tot_row);i++)
				{
					tab_max_ = obj.getMax("PMS_AME_EXP_ITEM_BREAKUP","BILL_SNO", "");
					String billno=obj.setValue("billno"+i, request);
					String billdate=obj.setValue("billdate"+i, request);
					String part=obj.setValue("part"+i, request);
					String amt=obj.setValue("amt"+i, request);
					String vouno=obj.setValue("vouno"+i, request);
					String voudate=obj.setValue("voudate"+i, request);
					String camt=obj.setValue("camt"+i, request);
					String damt=obj.setValue("damt"+i, request);
					String cb1=obj.setValue("cb"+i, request);
					String crdr=obj.setValue("crdr"+i, request);
					process_f = "";
					if (Integer.parseInt(pmonth) <= 3) {
						process_f = (Integer.parseInt(pyear) - 1)+ "-" + pyear;
					} else {
						process_f = pyear + "-"+ (Integer.parseInt(pyear) + 1); 
					}	  
					if (!billno.equalsIgnoreCase("0"))  
					{
						tab1.put("BILL_SNO", tab_max_);
						tab1.put("SCH_SNO", sch);
						tab1.put("MONTH", pmonth);
						tab1.put("YEAR",pyear); 
						tab1.put("PERFORM_DESC_SNO", "3");
						tab1.put("MAIN_ITEM_SNO", MAIN_ITEM_SNO);
						tab1.put("SUB_ITEM_SNO", SUB_ITEM_SNO);
						tab1.put("GROUP_SNO", GROUP_SNO);					
						tab1.put("BILL_NO",  "'" +billno+ "'");
						tab1.put("BILL_DATE", "'"+billdate+"'");
						tab1.put("VOUCHER_NO",  "'" +vouno+ "'");
						tab1.put("VOUCHER_DATE", "'"+voudate+"'");
						tab1.put("BILL_AMT", amt);
						tab1.put("PARTICULARS","'" +part+ "'");
						tab1.put("UPDATED_BY_USER_ID", "'"+userid+"'");
						tab1.put("UPDATED_TIME", "clock_timestamp()");
						tab1.put("OFFICE_ID", Office_id);
						tab1.put("GROSS_AMT", camt);
						tab1.put("BILL_DEDN_ID", cb1);
						tab1.put("DEDN_AMT", damt);
						tab1.put("crdr", "'"+crdr+"'");  
						
						String project_id=obj.getValue("PMS_AME_MST_SCH_MAPPING", "FAS_PROJECT_ID", "where SCH_SNO="+sch+" and OFFICE_ID="+Office_id);//obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+sch);
						tab1.put("PROJECT_ID", project_id);

						ins_row+=obj.recordSave(tab1,"PMS_AME_EXP_ITEM_BREAKUP", con);
						res = "<response><ins_row>"+ins_row+"</ins_row></response>";
					}
				}
			}
			else if (Integer.parseInt(process_code)==10)  
			{
				fyear_i = obj.setValue("fyear", request);
				sch_i = obj.setValue("sch", request);
				//int ct=obj.getCount("PMS_AME_TRN_BUDGET", " where office_id="+Office_id+" and sch.sch_sno= bud.sch_sno and bud.FIN_YEAR='"+fyear_i +"' and  bud.SCH_SNO="+sch_i);
				res=obj.resultXML("SELECT bud.BUDGET_SNO, bud.SCH_SNO,  " +
							" bud.FIN_YEAR,bud.TECH_REF_NO," +
							" bud.BUDGET_EST_AMT," +       
							" bud.ORG_REV,bud.ADMIN_REF_NO,bud.MAINT_EST_AMT, " +
							" case WHEN	 ADMIN_REF_DATE=null " +
							"	then ''" +
							"	else " +
							"	to_char(bud.ADMIN_REF_DATE,'DD/MM/YYYY') " +
							" end  as ADMIN_REF_DATE ," +  
							"  case WHEN	 TECH_REF_DATE=null " +  
							"	then ''" +
							"	else " +
							"	to_char(bud.TECH_REF_DATE,'DD/MM/YYYY') " +
							" end  as TECH_REF_DATE ," +
							"	bud.FIN_YEAR," +
							" 	bud.OFFICE_ID, sch.sch_name FROM PMS_AME_TRN_BUDGET bud," +
							" pms_sch_master sch WHERE bud.office_id="+Office_id+" and sch.sch_sno= bud.sch_sno and bud.FIN_YEAR='"+fyear_i +"' and  bud.SCH_SNO="+sch_i
						+" order by bud.UPDATED_TIME DESC", con, obj);
				
				  
  
			}  
			else if (Integer.parseInt(process_code) == 4)     
			{ 
				sch_sno= obj.setValue("sch_sno", request);
				pyear= obj.setValue("pyear", request);
				String EST_SNO= obj.setValue("EST_SNO", request);
				res=obj.delRecord("PMS_AME_TRN_BUDGET", "where BUDGET_SNO="+EST_SNO+" and office_id="+Office_id+" and FIN_YEAR='"+pyear+"' and sch_sno="+sch_sno, con);
				if (r > 0) {
					res = "<response><rows>" + r + "</rows></response>";
				}	else
				{
					res = "<response><rows>" + r + "</rows></response>";
				}
			}
			}   
			
			if (Integer.parseInt(type) == 6)
			{
				 	if (Integer.parseInt(process_code) == 1) 
					{   
				 		res=obj.resultXML("	SELECT ac.*, "+  
				 						 " mitem.MAIN_ITEM_DESC, "+ 
				 						 " sitem.SUB_ITEM_DESC "+
				 						 " FROM "+  
				 						 " (SELECT ACC_CODE_SNO,CODE_W_E_F, "+
				 						 " MAIN_ITEM_SNO,"+
				 						 " SUB_ITEM_SNO,"+
				 		   " ACCOUNT_HEAD_CODE,"+
				 		   " ACTIVE_STATUS "+
				 		  " FROM PMS_AME_MST_ITEM_ACC_CODE "+
				 		 " WHERE ACTIVE_STATUS='A'  "+ 
				 		 " ) ac "+
				 		 " LEFT JOIN "+
				 		 " ( SELECT MAIN_ITEM_SNO, MAIN_ITEM_DESC FROM PMS_AME_MST_MAIN_ITEM "+
				 		 "  )mitem "+
				 		 " ON mitem.MAIN_ITEM_SNO=ac.MAIN_ITEM_SNO "+
				 		 " LEFT JOIN "+
				 		 " ( SELECT SUB_ITEM_SNO,SUB_ITEM_DESC FROM PMS_AME_MST_SUB_ITEM "+
				 		 "  )sitem "+     
				 		 " ON sitem.SUB_ITEM_SNO=ac.SUB_ITEM_SNO order by ac.MAIN_ITEM_SNO ,ac.SUB_ITEM_SNO ", con, obj);
				 }else if (Integer.parseInt(process_code) == 2) 
					{ 
				 	String mainitem= obj.setValue("mainitem", request);
					String subitem= obj.setValue("subitem", request);
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy"); 
					String wef=obj.setValue("wef", request);     
					java.util.Date date = dateFormat.parse(wef);
					String ac= obj.setValue("ac", request);
					String astatus= obj.setValue("astatus", request);				
					int c=obj.getCount("PMS_AME_MST_ITEM_ACC_CODE", "where MAIN_ITEM_SNO="+mainitem+" and SUB_ITEM_SNO="+subitem+" and ACTIVE_STATUS='A'"); 
					tab_max_ = obj.getMax("PMS_AME_MST_ITEM_ACC_CODE","ACC_CODE_SNO", "");
					tab1.put("ACC_CODE_SNO", tab_max_);
					tab1.put("MAIN_ITEM_SNO",mainitem);
					tab1.put("SUB_ITEM_SNO",subitem);   
					tab1.put("ACCOUNT_HEAD_CODE",ac);
					tab1.put("UPDATED_BY_USER_ID","'"+userid+"'");
					tab1.put("UPDATED_DATE", "clock_timestamp()");
					tab1.put("ACTIVE_STATUS", "'" +astatus+"'");  
					tab1.put("CODE_W_E_F", "'" +wef+"'");    
					int ins_row=0;  
					if (c==0)   
					{	
						ins_row=obj.recordSave(tab1,"PMS_AME_MST_ITEM_ACC_CODE", con);
					}
					else
					{
						ins_row=0;
						
					}
					res = "<response><ins_row>"+ins_row+"</ins_row></response>";
					} else if (Integer.parseInt(process_code) == 3) 
						{ 
								String ACC_CODE_SNO=obj.setValue("ACC_CODE_SNO", request);
								tab1.put("ACTIVE_STATUS", "'D'");
								tab1.put("UPDATED_DATE", "clock_timestamp()"); 
								
								condht.put("ACC_CODE_SNO", ACC_CODE_SNO);
								r = obj.recordSave(tab1, condht,"PMS_AME_MST_ITEM_ACC_CODE ", con);
								if (r > 0) {
									res = "<response><rows>" + r + "</rows></response>";
								}
						}  
			} 
			if (Integer.parseInt(type) == 7)
			{
				
				String TECH_REF_NO=obj.setValue("TECH_REF_NO", request);
				 sch_sno=obj.setValue("sch_sno", request);
				pyear=obj.setValue("pyear", request);
				String MAIN_ITEM_SNO=obj.setValue("MAIN_ITEM_SNO", request);
				String SUB_ITEM_SNO=obj.setValue("SUB_ITEM_SNO", request);
				String GROUP_SNO=obj.setValue("GROUP_SNO", request);
				if (MAIN_ITEM_SNO.equalsIgnoreCase("")) MAIN_ITEM_SNO="0"; 
				String cond=" where OFFICE_ID="+Office_id+" and  GROUP_SNO="+GROUP_SNO+" and MAIN_ITEM_SNO="+MAIN_ITEM_SNO;
					   cond+=" and FIN_YEAR='"+pyear+"' and SUB_ITEM_SNO="+SUB_ITEM_SNO+" and SCH_SNO="+sch_sno+" and TECH_SANC_NO="+TECH_REF_NO;
					   
					  ResultSet rs_new=obj.getRS("select * from PMS_AME_TRN_SCHEME_ITEM " + cond);
					   res= obj.resultXML("select count(*) as count from PMS_AME_TRN_SCHEME_ITEM " + cond, con, obj);				
				 
			}if (Integer.parseInt(type) == 8)
			{
					String TECH_REF_NO=obj.setValue("TECH_REF_NO", request);
				   sch_sno=obj.setValue("sch_sno", request);
				   pyear=obj.setValue("pyear", request);  
				   String cond=" where OFFICE_ID="+Office_id+" and FIN_YEAR='"+pyear+"'  and SCH_SNO="+sch_sno+" and TECH_SANC_NO="+TECH_REF_NO;
				   res=obj.delRecord("PMS_AME_TRN_SCHEME_ITEM", cond, con);
			}  
			obj.resposeWr(response, res);
			obj=null;
			con=null; 
		} catch (Exception e) {
			System.out.println(e);  
		}
	}
}    
