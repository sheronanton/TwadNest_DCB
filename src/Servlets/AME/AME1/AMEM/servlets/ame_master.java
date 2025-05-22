package Servlets.AME.AME1.AMEM.servlets;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 

public class ame_master extends HttpServlet {
private static final long serialVersionUID = 1L;
   
	public ame_master() {	super(); 	}
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		
		    String res="";
			response.setContentType(CONTENT_TYPE);
			Controller obj=new Controller();
			DecimalFormat dc=new DecimalFormat("0.000");
			Connection con=null;
		  	try
			{
				con=obj.con();
				obj.createStatement(con); 
				String process_code=obj.setValue("process_code", request);
				String type=obj.setValue("type", request);
				HttpSession session = request.getSession(false);
				String	userid = (String) session.getAttribute("UserId");
				if (userid == null) 
				{ 
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				 Hashtable ht = new Hashtable();   
				 Hashtable condht = new Hashtable();
				String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
						"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
								+ userid + "')");
				System.out.println("AME-------->MASTER------TYPE("+type+")---->PROCESSCODE("+process_code+")");
				 if (Integer.parseInt(type)==1)
				{
								if (Integer.parseInt(process_code)==1)
								{
									 int max_=obj.getMax("PMS_AME_MST_GROUP", "GROUP_SNO","");
									 
									 Hashtable numbers = new Hashtable();
								     numbers.put("GROUP_SNO",max_);
								     numbers.put("GROUP_DESC", request.getParameter("desc"));
								     numbers.put("UPDATED_BY_USER_ID", "'"+userid+"'");
								     numbers.put("UPDATED_TIME", "clock_timestamp()");     
				  
								     int r=obj.recordSave(numbers, "PMS_AME_MST_GROUP ", con);
								     if (r > 0 )
								     {
								    	 res="<response><rows>"+r+"</rows></response>";
								     }
								     
								}else if (Integer.parseInt(process_code)==2)
								{
									 String row=obj.setValue("rowcount", request);
									 
									 for (int i=1;i<=Integer.parseInt(row);i++)
									 {
									 	 Hashtable columns = new Hashtable();								    
									 	 columns.put("GROUP_DESC", ""+obj.setValue("desc"+i, request)+"" );
								         Hashtable condition = new Hashtable();								    
								         condition.put("GROUP_SNO", "'"+obj.setValue("GROUP_SNO"+i, request)+"'" );
								         int r=obj.recordSave(columns,condition, "PMS_AME_MST_GROUP ", con);
								     
								    	 res="<response><rows>"+r+"</rows></response>";
								     }
								     
								}else if (Integer.parseInt(process_code)==3)
								{
										res=obj.resultXML("select GROUP_SNO,GROUP_DESC from PMS_AME_MST_GROUP order by  GROUP_SNO ", con, obj);
										
								}else if (Integer.parseInt(process_code)==4)
								{
										String GROUP_SNO=obj.setValue("GROUP_SNO", request);
										res=obj.delRecord("PMS_AME_MST_GROUP", " where GROUP_SNO="+GROUP_SNO,con);
								}
				}  
				// Main Item Master 
				// (2 , 1 )
				else if (Integer.parseInt(type)==2)
				{
					// New Main Item Insert				
					if (Integer.parseInt(process_code)==1)
					{
						int max_=obj.getMax("PMS_AME_MST_MAIN_ITEM", "MAIN_ITEM_SNO","");
						 
						 Hashtable numbers = new Hashtable();
					     numbers.put("MAIN_ITEM_SNO",max_);
					     numbers.put("MAIN_ITEM_DESC", "'"+obj.setValue("desc", request)+"'" );
					     numbers.put("GROUP_SNO", obj.setValue("group", request));		
					   //  numbers.put("APPLY_CENTAGE", obj.setValue("cap", request));	
					     numbers.put("UPDATED_BY_USER_ID", Office_id);
					     numbers.put("UPDATED_TIME", "clock_timestamp()");     
	  
					     int r=obj.recordSave(numbers, "PMS_AME_MST_MAIN_ITEM ", con);
					     if (r > 0 )
					     {
					    	 res="<response><rows>"+r+"</rows></response>";
					     }					     
					}
					// Update Old Main Item  
					else if (Integer.parseInt(process_code)==2)
					{
						
						 String desc=obj.setValue("desc", request);
						 String GROUP_DESC=obj.setValue("GROUP_DESC", request);
					 
						 String APPLY_CENTAGE=(obj.setValue("chkAPPLY_CENTAGE", request).equalsIgnoreCase("true")?"1":"0");
						 String key_value=obj.setValue("key_value", request);
						 ht.put("GROUP_SNO",GROUP_DESC); 
						 ht.put("MAIN_ITEM_DESC","'"+desc+"'");
						// ht.put("APPLY_CENTAGE",APPLY_CENTAGE);
						 
						 condht.put("MAIN_ITEM_SNO",key_value);
						 int r=obj.recordSave(ht,condht, "PMS_AME_MST_MAIN_ITEM ", con);					     
				    	 res="<response><rows>"+r+"</rows></response>";
				    }
					// List of Main Item  
					else if (Integer.parseInt(process_code)==3)
					{
						String local_qry="  SELECT main.APPLY_CENTAGE, main.MAIN_ITEM_SNO," +
										 "  main.MAIN_ITEM_DESC, " +
										 "  main. GROUP_SNO," + 
										 "  g.GROUP_DESC FROM " +
				 						 "  ( SELECT MAIN_ITEM_SNO,APPLY_CENTAGE," +
		  								 "     MAIN_ITEM_DESC, " +
										 "     GROUP_SNO  FROM  PMS_AME_MST_MAIN_ITEM " +
										 "  )main " +
										 "  JOIN  ( " +
										 "  SELECT GROUP_SNO, GROUP_DESC FROM PMS_AME_MST_GROUP " +
										 " ) g ON g.GROUP_SNO=main.GROUP_SNO  order by MAIN_ITEM_SNO" ;
										res=obj.resultXML(local_qry, con, obj);
						 
						 
				   }
					// Delete the Main item Record
					else if (Integer.parseInt(process_code)==4)
					{
						String key_value=obj.setValue("key_value", request);
						int count=obj.getCount("PMS_AME_MST_SUB_ITEM", "where MAIN_ITEM_SNO="+key_value);
						int count1=obj.getCount("PMS_AME_TRN_ABSTRACT", "where MAIN_ITEM_SNO="+key_value);
 						
						if (count==0 && count1==0  )
						res=obj.delRecord("PMS_AME_MST_MAIN_ITEM", " where MAIN_ITEM_SNO="+key_value,con);
						else
						res=obj.delRecord_err();
					}  
					
				}
				// Sub Item Master
				
				else if (Integer.parseInt(type)==3)
				{
					// New Record Insert					
					if (Integer.parseInt(process_code)==1)
					{
						int max_=obj.getMax("PMS_AME_MST_SUB_ITEM", "SUB_ITEM_SNO","");						 
						 Hashtable numbers = new Hashtable();
					     numbers.put("SUB_ITEM_SNO",max_);
					     numbers.put("SUB_ITEM_SDESC", "'"+obj.setValue("shortdesc", request)+"'" );
					     numbers.put("SUB_ITEM_DESC", "'"+obj.setValue("desc", request)+"'" );
					     numbers.put("MAIN_ITEM_SNO", obj.setValue("main", request));
					     numbers.put("APPLY_CENTAGE", obj.setValue("cap", request));	
					     numbers.put("UPDATED_BY_USER_ID", Office_id);
					     numbers.put("UPDATED_TIME", "clock_timestamp()"); 
					     
					     int r=obj.recordSave(numbers, "PMS_AME_MST_SUB_ITEM ", con);
					     if (r > 0 )
					     {
					    	 res="<response><rows>"+r+"</rows></response>";
					     }				     
					}
					// Update Old Sub Item  
					else if (Integer.parseInt(process_code)==2)
					{						
						 String desc=obj.setValue("desc", request);
						 String MAIN_ITEM_SNO=obj.setValue("MAIN_ITEM_SNO", request);
						 String key_value=obj.setValue("key_value", request);
						 String APPLY_CENTAGE=(obj.setValue("chkAPPLY_CENTAGE", request).equalsIgnoreCase("true")?"1":"0");
						 ht.put("MAIN_ITEM_SNO",MAIN_ITEM_SNO);
						 ht.put("APPLY_CENTAGE",APPLY_CENTAGE);
						 ht.put("SUB_ITEM_SDESC", "'"+obj.setValue("shortdesc", request)+"'" );
						 ht.put("SUB_ITEM_DESC","'"+desc+"'");
						 condht.put("SUB_ITEM_SNO",key_value);
						 int r=obj.recordSave(ht,condht, "PMS_AME_MST_SUB_ITEM ", con);					     
				    	 res="<response><rows>"+r+"</rows></response>";
				    }
					// List of All Sub item
					else if (Integer.parseInt(process_code)==3) 
					{
						String local_qry=" SELECT sub.SUB_ITEM_SNO, " +
								         " sub.SUB_ITEM_DESC," +
								         " sub.MAIN_ITEM_SNO,sub.APPLY_CENTAGE," + 
								         " main.MAIN_ITEM_DESC,sub.SUB_ITEM_SDESC " +
								         " FROM " +
								         " ( SELECT SUB_ITEM_SNO," +
								         "   SUB_ITEM_DESC,APPLY_CENTAGE," +
								         "   MAIN_ITEM_SNO ,SUB_ITEM_SDESC FROM PMS_AME_MST_SUB_ITEM " +
								         " )sub JOIN ( " +
								         " SELECT " +
								         "   MAIN_ITEM_SNO, " +
								         "   MAIN_ITEM_DESC " +
								         " FROM PMS_AME_MST_MAIN_ITEM " +
								         " ) main " +
								         " ON main.MAIN_ITEM_SNO=sub.MAIN_ITEM_SNO " +
								         " order by MAIN_ITEM_SNO,SUB_ITEM_DESC" ;						
										res=obj.resultXML(local_qry, con, obj);
				  } 
					// Delete the Sub item Record
					else if (Integer.parseInt(process_code)==4)
					{
						String key_value=obj.setValue("key_value", request);
 						int count1=obj.getCount("PMS_AME_TRN_ABSTRACT", "where SUB_ITEM_SNO="+key_value);
 						
						if ( count1==0  )
						res=obj.delRecord("PMS_AME_MST_SUB_ITEM", " where SUB_ITEM_SNO="+key_value,con);
						else
						res=obj.delRecord_err();
					}  
				}
				else if (Integer.parseInt(type)==4)
				{
								if (Integer.parseInt(process_code)==1)
								{
									//insert centage
									 int cen_count=obj.getCount("PMS_AME_MST_CENTAGE", "where sch_sno="+obj.setValue("sch_sno", request)+" and  ACTIVE_STATUS='"+obj.setValue("status", request)+"'");
									//String cen_val=obj.getValue("PMS_AME_MST_CENTAGE", "ACTIVE_STATUS", "where ACTIVE_STATUS='A' ");
									if(cen_count==0)
									{
										 int max_=obj.getMax("PMS_AME_MST_CENTAGE", "CENTAGE_SNO","");
									     ht.put("CENTAGE_SNO",max_);
									     ht.put("ACTIVE_STATUS", "'"+obj.setValue("status", request)+"'");
									     ht.put("UPDATED_BY_USER_ID", Office_id);
									     ht.put("UPDATED_TIME", "clock_timestamp()");
									     ht.put("sch_sno",obj.setValue("sch_sno", request));
									     ht.put("CENTAGE",obj.setValue("centage_Rate", request));
									     ht.put("CENTAGE_WEF","to_date('"+obj.setValue("centage_wef", request)+"','DD/MM/YYYY')");								     
									     int r=obj.recordSave(ht, "PMS_AME_MST_CENTAGE ", con);
									     if (r > 0 )
									     {  
									    	 res="<response><rows>"+r+"</rows><msg>Record Succesfully Updated</msg></response>";
									     }
									}
									else
									{
										res="<response><rows>0</rows><msg>Centage Rate Already Exists. Use Edit Option and Change Status as Defunct</msg></response>";
									}
								     
								}
								else if (Integer.parseInt(process_code)==2)
								{
									//update centage
									int cen_count=obj.getCount("PMS_AME_MST_CENTAGE", "where ACTIVE_STATUS='A' and CENTAGE_SNO="+obj.setValue("centage_Id", request));
									//String cen_val=obj.getValue("PMS_AME_MST_CENTAGE", "ACTIVE_STATUS", "where ACTIVE_STATUS='A' and CENTAGE_SNO="+obj.setValue("centage_Id", request));
									if(cen_count!=0)
									{
									 ht.put("ACTIVE_STATUS","'"+obj.setValue("status", request)+"'");
									 ht.put("CENTAGE",obj.setValue("centage_Rate", request));
									 ht.put("CENTAGE_WEF","to_date('"+obj.setValue("centage_wef", request)+"','DD/MM/YYYY')");
									 ht.put("UPDATED_BY_USER_ID", Office_id);
								     ht.put("UPDATED_TIME", "clock_timestamp()");
								     ht.put("sch_sno",obj.setValue("sch_sno", request));  
									 condht.put("CENTAGE_SNO",obj.setValue("centage_Id", request));
									 
									 int r=obj.recordSave(ht,condht, "PMS_AME_MST_CENTAGE ", con);					     
							    	 res="<response><rows>"+r+"</rows><msg>Record Succesfully Updated</msg></response>";
									}
									else
									{
									 res="<response><rows>0</rows><msg>Centage Rate Already Exists. Use Edit Option and Change Status as Defunct</msg></response>";
									}
								     
								}else if (Integer.parseInt(process_code)==3)
								{
									//onload centage
										res=obj.resultXML("select b.SCH_NAME as SCH_NAME ,a.CENTAGE_SNO as CENTAGE_SNO ,a.ACTIVE_STATUS as ACTIVE_STATUS ,a.CENTAGE as CENTAGE ,TO_CHAR(a.CENTAGE_WEF , 'DD/MM/YYYY') AS CENTAGE_WEF from PMS_AME_MST_CENTAGE a,PMS_SCH_MASTER b  where a.ACTIVE_STATUS='A' and a.SCH_SNO=b.SCH_SNO and b.office_id="+Office_id+"  order by  CENTAGE_WEF DESC ", con, obj);
								}
								
				}  else if (Integer.parseInt(type)==5)
				{
					// scheme quantity master insert
								int count_=obj.getCount("PMS_AME_MST_SCH_DETAILS", "where OFFICE_ID="+Office_id+" and SCH_SNO="+obj.setValue("sch_sno", request));
								if (Integer.parseInt(process_code)==1)
								{
									 int max_=obj.getMax("PMS_AME_MST_SCH_DETAILS", "SCH_DETAILS_SNO","");
								     ht.put("SCH_DETAILS_SNO",max_); 
								     ht.put("OFFICE_ID",Office_id);
								     ht.put("SCH_SNO", obj.setValue("sch_sno", request));
								     ht.put("QTY_DESIGN", obj.setValue("sch_qty", request));
							//	     ht.put("AME_PROJECT_ID", obj.setValue("sch_sno1", request));
								     //22/12/2011
								     ht.put("YEAR_OF_COMMISSION",obj.setValue("yearcomm", request));
								     ht.put("DESIGN_PUMPING_HOURS",obj.setValue("pumphours", request));
								     ht.put("TOTAL_NO_STAFF",obj.setValue("noofstaff", request));
								     ht.put("TOTAL_NO_HABITATION",obj.setValue("noofhab", request));
								     ht.put("UPDATED_BY_USER_ID", Office_id);
								     ht.put("UPDATED_TIME", "clock_timestamp()");     
								     String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+obj.setValue("sch_sno", request));
										ht.put("PROJECT_ID",project_id);  
								     if (count_==0)
								     {
								    	 int r=obj.recordSave(ht, "PMS_AME_MST_SCH_DETAILS ", con);
								    	 if (r > 0 )
								    	 {
							 	    		 res="<response><rows>"+r+"</rows><msg>Record Succesfully Saved</msg></response>";
								    	 }
								     }else
								     {
								    	 res="<response><rows>0</rows><msg>Data Already Exists</msg></response>";
								     }
								}
								else if (Integer.parseInt(process_code)==2)
								{
									//update design qty
									ht.put("OFFICE_ID",Office_id);
									ht.put("SCH_SNO", obj.setValue("sch_sno", request));
									ht.put("QTY_DESIGN", obj.setValue("sch_qty", request));
									ht.put("UPDATED_BY_USER_ID", Office_id);
									ht.put("UPDATED_TIME", "clock_timestamp()");
									// 22/12/2011
									 ht.put("QTY_DESIGN", obj.setValue("sch_qty", request));
								     ht.put("YEAR_OF_COMMISSION",obj.setValue("yearcomm", request));
								     ht.put("DESIGN_PUMPING_HOURS",obj.setValue("pumphours", request));
								     ht.put("TOTAL_NO_STAFF",obj.setValue("noofstaff", request));
								     ht.put("TOTAL_NO_HABITATION",obj.setValue("noofhab", request));
									
									String project_id=obj.getValue("PMS_MST_PROJECTS_VIEW", "PROJECT_ID", "where SCH_SNO="+obj.setValue("sch_sno", request));
									ht.put("PROJECT_ID",project_id);  
									 condht.put("SCH_DETAILS_SNO",obj.setValue("key_value", request));
									 	 int r=obj.recordSave(ht,condht, "PMS_AME_MST_SCH_DETAILS ", con);
										 res="<response><rows>"+r+"</rows><msg>Record Succesfully Updated</msg></response>";
								     
									} else if (Integer.parseInt(process_code)==7)
									{
										 
										 String r=obj.delRecord("PMS_AME_MST_SCH_DETAILS", "where OFFICE_ID="+Office_id+" and SCH_DETAILS_SNO="+obj.setValue("key_value", request),con);  
										 res="<response><rows>"+r+"</rows><msg>Record Succesfully Deleted</msg></response>";
									}
								else if (Integer.parseInt(process_code)==3)
								{
									// Date : 22/12/2011 		
									//onload centage
										res="<response>";
										String qry="SELECT qty.SCH_DETAILS_SNO, " 
														+"  qty.OFFICE_ID, "      
														+"  qty.SCH_SNO, " 
														+"  qty.QTY_DESIGN, YEAR_OF_COMMISSION," 
														+"  qty.DESIGN_PUMPING_HOURS,"   
														+"  qty.TOTAL_NO_STAFF,"    
														+"  qty.TOTAL_NO_HABITATION, " 
														+"  (sch.sch_name || '('  || sch.SCH_SNO  || ')') as sch_name " 
														+"FROM PMS_AME_MST_SCH_DETAILS qty, " 
														+"  pms_sch_master sch " 
														+"WHERE qty.OFFICE_ID="+Office_id
														+" AND qty.sch_sno    =sch.sch_sno order by sch.sch_name";																				
											PreparedStatement qry_stmt=con.prepareStatement(qry);
											String SCH_DETAILS_SNO="",SCH_SNO="",QTY_DESIGN="",YEAR_OF_COMMISSION="",DESIGN_PUMPING_HOURS="",TOTAL_NO_STAFF="",TOTAL_NO_HABITATION="",sch_name="";
											int row_count=0;
											ResultSet rs=qry_stmt.executeQuery();											
											while(rs.next())
											{
												row_count++;
												Office_id= obj.isNull(rs.getString("Office_id"),1);
												res+="<Office_id>"+Office_id+"</Office_id>";
												SCH_DETAILS_SNO= obj.isNull(rs.getString("SCH_DETAILS_SNO"),1);
												res+="<SCH_DETAILS_SNO>"+SCH_DETAILS_SNO+"</SCH_DETAILS_SNO>";
												SCH_SNO= obj.isNull(rs.getString("SCH_SNO"),1);
												res+="<SCH_SNO>"+SCH_SNO+"</SCH_SNO>";
												QTY_DESIGN= obj.isNull(rs.getString("QTY_DESIGN"),1);
												res+="<QTY_DESIGN>"+dc.format(Double.parseDouble(QTY_DESIGN))+"</QTY_DESIGN>";
												YEAR_OF_COMMISSION= obj.isNull(rs.getString("YEAR_OF_COMMISSION"),1);
												res+="<YEAR_OF_COMMISSION>"+YEAR_OF_COMMISSION+"</YEAR_OF_COMMISSION>";
												DESIGN_PUMPING_HOURS= obj.isNull(rs.getString("DESIGN_PUMPING_HOURS"),1);
												res+="<DESIGN_PUMPING_HOURS>"+DESIGN_PUMPING_HOURS+"</DESIGN_PUMPING_HOURS>";
												TOTAL_NO_STAFF= obj.isNull(rs.getString("TOTAL_NO_STAFF"),1);
												res+="<TOTAL_NO_STAFF>"+TOTAL_NO_STAFF+"</TOTAL_NO_STAFF>";
												TOTAL_NO_HABITATION= obj.isNull(rs.getString("TOTAL_NO_HABITATION"),1);
												res+="<TOTAL_NO_HABITATION>"+TOTAL_NO_HABITATION+"</TOTAL_NO_HABITATION>";
												sch_name= obj.isNull(rs.getString("sch_name"),1);
												res+="<sch_name>"+sch_name+"</sch_name>";
											}
											  res+="<row_count>"+row_count+"</row_count> ";
									//	res+=obj.resultXML(qry, con, obj,1);
										res+="<msg>success</msg>";
										res+="</response>";
								}    
				}else if (Integer.parseInt(type)==6)
				{
					if (Integer.parseInt(process_code)==1)
					{
						res=obj.combo_lkup("GROUP_SNO", "GROUP_DESC", "PMS_AME_MST_GROUP", "order by GROUP_SNO",1,"");
						 
					}
					if (Integer.parseInt(process_code)==2)
					{
						res=obj.combo_lkup("MAIN_ITEM_SNO", "MAIN_ITEM_DESC", "PMS_AME_MST_MAIN_ITEM", "order by MAIN_ITEM_SNO",1,"");
						 
					}
				}
			    else if (Integer.parseInt(type)==7)
				{
					//Accounts Group Master
					if (Integer.parseInt(process_code)==1)
					{ 
						 int max_=obj.getMax("PMS_AME_ACC_GROUP", "ACC_GROUP_SNO","");						 
						 Hashtable numbers = new Hashtable();
					     numbers.put("ACC_GROUP_SNO",max_);
					     numbers.put("ACC_GROUP_DESC", request.getParameter("desc"));
					     numbers.put("UPDATED_BY_USER_ID", "'"+userid+"'");
					     numbers.put("UPDATED_TIME", "clock_timestamp()");     	  
					     int r=obj.recordSave(numbers, "PMS_AME_ACC_GROUP ", con);
					     if (r > 0 )
					     {
					    	 res="<response><rows>"+r+"</rows></response>";
					     }
					     
					}else if (Integer.parseInt(process_code)==2)
					{
						 String row=obj.setValue("rowcount", request);						 
						 for (int i=1;i<=Integer.parseInt(row);i++)
						 {
						 	 Hashtable columns = new Hashtable();								    
						 	 columns.put("ACC_GROUP_DESC", ""+obj.setValue("desc"+i, request)+"" );						 	 
					         Hashtable condition = new Hashtable();								    
					         condition.put("ACC_GROUP_SNO", "'"+obj.setValue("ACC_GROUP_SNO"+i, request)+"'" );
					         int r=obj.recordSave(columns,condition, "PMS_AME_ACC_GROUP ", con);					     
					    	 res="<response><rows>"+r+"</rows></response>";
					     }					     
					}else if (Integer.parseInt(process_code)==3)
					{
							res=obj.resultXML("select ACC_GROUP_SNO,ACC_GROUP_DESC from PMS_AME_ACC_GROUP order by  ACC_GROUP_SNO ", con, obj);
					}else if (Integer.parseInt(process_code)==4)
					{
							String ACC_GROUP_SNO=obj.setValue("ACC_GROUP_SNO", request);
							res=obj.delRecord("PMS_AME_ACC_GROUP", " where ACC_GROUP_SNO="+ACC_GROUP_SNO,con);
					}
	}               
					   
					obj.resposeWr(response, res) ;   
			}catch (Exception e) {
					obj.testQry("Error----> AME----->"+ e);
			}
			    
	}
	private Object to_date(String parameter, String string) 
	{
		// TODO Auto-generated method stub
		return null;
	}
}