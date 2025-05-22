package Servlets.PMS.PMS1.DCB.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Testing_age extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		PreparedStatement ps=null,ps1=null,ps11=null,ps2=null,ps3=null,ps4=null,ps5=null,ps6=null,ps12=null,ps13=null;
		ResultSet rs=null,rs1=null,rs11=null,rs2=null,rs3=null,rs4=null,rs5=null,rs6=null,rs12=null,rs13=null;
		String sql=null,sql3=null,cond=null,val=null;
		
		
		Controller obj=new Controller();
	       
        try
        {
        	con=obj.con();
       	  System.out.println("Connected");
       		                        
        }
        catch(Exception e)
        {
             System.out.println("Exception in openeing connection:"+e);
        }
		
		
		
		
		
		String xml="";
		response.setContentType("text/xml");
		PrintWriter out=response.getWriter();
		String command=null;
		command=request.getParameter("command");
		System.out.println("command is "+command);

		if(command.equalsIgnoreCase("getdata"))
		{
			xml=xml+"<response><command>getdata</command>";
			int ben_id=Integer.parseInt(request.getParameter("id"));
			int ob=0,coll=0,amt=0,r_no=0,cmonth=0 ,cyear=0,status=0;
			int OB_BALANCE=0;
			Date r_date=null;
			int i=0;
			try
			{
				String sql1="select ben_sno,amt,r_no,r_date,MONTH,YEAR from pms_dcb_coll_age where ben_sno="+ben_id+" and (STATUS='N' OR STATUS is null)  order by  year,month,r_date,r_no ";
				 ps1=con.prepareStatement(sql1);
				 System.out.println("Query$$$sql1"+sql1);
					rs1=ps1.executeQuery();
					while(rs1.next())
					{
						coll=rs1.getInt("amt");
						System.out.println("coll_Amount----"+coll);											
				        r_date=rs1.getDate("R_DATE");
						System.out.println("r_date----"+r_date);
						amt=rs1.getInt("amt");
						System.out.println("amt----"+amt);
						r_no=rs1.getInt("r_no");
						cmonth=rs1.getInt("MONTH");
						cyear=rs1.getInt("YEAR");
						
						String sqll="select ben_sno,amt,r_no,r_date,year,month from PMS_DCB_COLL_BAL_AGE where ben_sno="+ben_id+" and STATUS='N' order by year,month,r_date,r_no ";
						ps11=con.prepareStatement(sqll);
						 System.out.println("Query$$$sql1"+sqll);
							rs11=ps11.executeQuery();
							if(rs11.next())
							{
								String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+amt+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
								ps4=con.prepareStatement(sql4);
								System.out.println("sql4>>>>>"+sql4);
								rs4=ps4.executeQuery();
								status=1;
								if(rs4.next())
								{
									String sql13="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
									System.out.println("sql3>>>"+sql13);
									ps13=con.prepareStatement(sql13);
									ps13.executeQuery();
								}
								
								coll=rs11.getInt("amt");
								System.out.println("coll_Amount----"+coll);											
						        r_date=rs11.getDate("R_DATE");
								System.out.println("r_date----"+r_date);
								amt=rs11.getInt("amt");
								System.out.println("amt----"+amt);
								r_no=rs11.getInt("r_no");	
								cmonth=rs11.getInt("MONTH");
								cyear=rs11.getInt("YEAR");
								
								
								
								
							}
						sql="select BEN_SNO,BILL_MONTH,BILL_YEAR,ob,OB_BALANCE from pms_dcb_ob_age where ben_sno="+ben_id+" and (BALANCE_CHECK='N' OR BALANCE_CHECK is null)  ";
						ps=con.prepareStatement(sql);
						System.out.println("Query$$$"+sql);
						rs=ps.executeQuery();
						if(rs.next())
						{
							
						 ob=rs.getInt("ob");
						 System.out.println("Opeing balance:"+ob);
						 
						  OB_BALANCE=rs.getInt("OB_BALANCE");
						    System.out.println("OB_BALANCE----"+OB_BALANCE);
						    
						}
						else
						{
							ob=0;
							 OB_BALANCE=0;
						}
						 //For OB
						 if(ob!=0 || OB_BALANCE!=0)
						 {
							 
							    int year=rs.getInt("BILL_YEAR");
							    System.out.println("year----"+year);
							    int month=rs.getInt("BILL_MONTH");
							    System.out.println("month----"+month);
								System.out.println("ob----"+ob);
								int Diff=ob-coll;
								System.out.println("Diff----"+Diff);
								if(OB_BALANCE==0)
								{

									if(Diff==0)
									{
										sql3="update pms_dcb_ob_age set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , COLL_AMT="+ amt +" , OB_BALANCE="+Diff+" , BALANCE_CHECK='Y', COLL_BALANCE=0 , r_no="+r_no+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year;
										System.out.println("sql3>>>"+sql3);
										ps3=con.prepareStatement(sql3);							
										int k=ps3.executeUpdate();
										if(k>0)
										{
											if(status==0){
											String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
											}
											else if(status==1)
											{
												String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
												System.out.println("sql3>>>"+sql11);
												ps3=con.prepareStatement(sql11);
												ps3.executeQuery();
											}
										}									

									}
									else if(Diff>0){
										 sql3="update pms_dcb_ob_age set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , COLL_AMT="+amt +" , OB_BALANCE="+Diff+" , BALANCE_CHECK='N',COLL_BALANCE=0 , r_no="+r_no+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year;
										System.out.println("sql3>>>"+sql3);
										ps3=con.prepareStatement(sql3);
									//	ps3.setString(1,r_date);
										int k=ps3.executeUpdate();
										if(k>0)
										{
											if(status==0){
											String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
											}
											else if(status==1){
												String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
												System.out.println("sql3>>>"+sql11);
												ps3=con.prepareStatement(sql11);
												ps3.executeQuery();
											}
										}
									}
									else if(Diff<0)
									{
										int bal=coll-ob;
										 sql3="update pms_dcb_ob_age set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , r_no="+r_no+", BALANCE_CHECK='Y',OB_BALANCE=0 ,COLL_BALANCE="+bal+" ,COLL_AMT="+ob+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year;
										System.out.println("sql3>>>"+sql3);
										ps3=con.prepareStatement(sql3);
										int k=ps3.executeUpdate();
										if(k>0)
										{
										
											String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+bal+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
											ps4=con.prepareStatement(sql4);
											System.out.println("sql4>>>>>"+sql4);
											rs4=ps4.executeQuery();
											if(rs4.next())
											{
												if(status==0)
												{
														String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
														System.out.println("sql3>>>"+sql11);
														ps3=con.prepareStatement(sql11);
														ps3.executeQuery();
												}
												else if(status==1)
												{
														String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
														System.out.println("sql3>>>"+sql11);
														ps3=con.prepareStatement(sql11);
														ps3.executeQuery();
														
												}
											
											}
											
											
										}
									}
									
								
								}
								
								
								//OB else part
								
								
								else
								{

									int coll_diff=OB_BALANCE-coll;
										System.out.println("coll_diff>>>>"+coll_diff);
										if(coll_diff==0)
										{
											 sql3="update pms_dcb_ob_age set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and BALANCE_CHECK='N'";
											System.out.println("sql3>>>"+sql3);
											ps3=con.prepareStatement(sql3);							
											int k=ps3.executeUpdate();
											if(k>0)
											{
												if(status==0){
												String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
												System.out.println("sql3>>>"+sql11);
												ps3=con.prepareStatement(sql11);
												ps3.executeQuery();
												}
												else if(status==1)
												{
													String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
												}
												
												
											}		

										}
										
										else if(coll_diff>0){
											 sql3="update pms_dcb_ob_age set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year;
											System.out.println("sql3>>>"+sql3);
											ps3=con.prepareStatement(sql3);
										//	ps3.setString(1,r_date);
											int k=ps3.executeUpdate();
											if(k>0)
											{
												if(status==0){
												String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
												System.out.println("sql3>>>"+sql11);
												ps3=con.prepareStatement(sql11);
												ps3.executeQuery();
												}
												else if(status==1){
													String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
												}
												String sql12="insert into pms_dcb_ob_age (BEN_SNO,BILL_MONTH,BILL_YEAR,OB,R_NO,R_DATE ,COLL_AMT,OB_BALANCE,BALANCE_CHECK,COLL_BALANCE) VALUES("+ben_id+","+month+","+year+",0,"+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), "+amt+","+coll_diff+",'N',0)";
														System.out.println("sql3>>>"+sql12);
														ps4=con.prepareStatement(sql12);
														ps4.executeQuery();
																											
											}
										}
										else if(coll_diff<0){
											int bal=coll-OB_BALANCE;
											 sql3="update pms_dcb_ob_age set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and BALANCE_CHECK='N'";
											System.out.println("sql3>>>"+sql3);
											ps3=con.prepareStatement(sql3);
											int k=ps3.executeUpdate();
											if(k>0)
											{
											
												String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+bal+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
												ps4=con.prepareStatement(sql4);
												System.out.println("sql4>>>>>"+sql4);
												rs4=ps4.executeQuery();
												if(rs4.next())
												{
													if(status==0)
													{
															String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
															System.out.println("sql3>>>"+sql11);
															ps3=con.prepareStatement(sql11);
															ps3.executeQuery();
													}
													else if(status==1)
													{
															String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
															System.out.println("sql3>>>"+sql11);
															ps3=con.prepareStatement(sql11);
															ps3.executeQuery();
															
													}
													String sql12="insert into pms_dcb_ob_age (BEN_SNO,BILL_MONTH,BILL_YEAR,OB,R_NO,R_DATE ,COLL_AMT,OB_BALANCE,BALANCE_CHECK,COLL_BALANCE) VALUES("+ben_id+","+month+","+year+",0,"+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), "+OB_BALANCE+",0,'Y',"+bal+")";
													System.out.println("sql3>>>"+sql12);
													ps4=con.prepareStatement(sql12);
													ps4.executeQuery();
										
									}
									
									}
								 }
								
								}
								
						
								
						 }
						 //OB==0
						 else
						 {
							 String sql2="select ben_sno,BILL_MONTH,BILL_YEAR,DMD,DMD_BALANCE,REF_TYPE from PMS_DCB_DMD_AGE where ben_sno="+ben_id+" and (BALANCE_CHECK='N' OR BALANCE_CHECK is null) and (DMD!=0 or DMD_BALANCE!=0 ) order by BILL_YEAR,BILL_MONTH,REF_TYPE ";
							  ps2=con.prepareStatement(sql2);
							  System.out.println("sql2>>>"+sql2);
							 rs2=ps2.executeQuery();
							if(rs2.next())
							{
								
							String REF_TYPE=rs2.getString("REF_TYPE");
							System.out.println("REF_TYPE----"+REF_TYPE);
							if(REF_TYPE.equals("J") || REF_TYPE.equals("j"))
							{
								System.out.println("inside journal");
								cond="REF_TYPE='J'";
								val="'J'";
							}
							else if(REF_TYPE.equals("R") || REF_TYPE.equals("r"))
							{
								System.out.println("inside receipt");
								cond="REF_TYPE='R'";
								val="'R'";
							}
							
						    int DMD_BALANCE=rs2.getInt("DMD_BALANCE");
						    System.out.println("DMD_BALANCE----"+DMD_BALANCE);
						    int year=rs2.getInt("BILL_YEAR");
						    System.out.println("year----"+year);
						    int month=rs2.getInt("BILL_MONTH");
						    System.out.println("month----"+month);
							int DMD=rs2.getInt("DMD");
							System.out.println("DMD----"+DMD);
							int Diff=DMD-coll;
							System.out.println("Diff----"+Diff);
							if(DMD_BALANCE==0)
							{
								if(Diff==0)
								{
									sql3="update PMS_DCB_DMD_AGE set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , COLL_AMT="+ amt +" , DMD_BALANCE="+Diff+" , BALANCE_CHECK='Y', COLL_BALANCE=0 , r_no="+r_no+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);							
									int k=ps3.executeUpdate();
									if(k>0)
									{
										if(status==0){
										String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
										System.out.println("sql3>>>"+sql11);
										ps3=con.prepareStatement(sql11);
										ps3.executeQuery();
										}
										else if(status==1)
										{
											String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
										}
									}									

								}
								else if(Diff>0){
									 sql3="update PMS_DCB_DMD_AGE set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , COLL_AMT="+amt +" , DMD_BALANCE="+Diff+" , BALANCE_CHECK='N',COLL_BALANCE=0 , r_no="+r_no+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);
								//	ps3.setString(1,r_date);
									int k=ps3.executeUpdate();
									if(k>0)
									{
										if(status==0){
										String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
										System.out.println("sql3>>>"+sql11);
										ps3=con.prepareStatement(sql11);
										ps3.executeQuery();
										}
										else if(status==1){
											String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
										}
									}
								}
								else if(Diff<0)
								{
									int bal=coll-DMD;
									 sql3="update PMS_DCB_DMD_AGE set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , r_no="+r_no+", BALANCE_CHECK='Y',DMD_BALANCE=0 ,COLL_BALANCE="+bal+" ,COLL_AMT="+DMD+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);
									int k=ps3.executeUpdate();
									if(k>0)
									{
									
										String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+bal+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
										ps4=con.prepareStatement(sql4);
										System.out.println("sql4>>>>>"+sql4);
										rs4=ps4.executeQuery();
										if(rs4.next())
										{
											if(status==0)
											{
													String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
											}
											else if(status==1)
											{
													String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
													
											}
										
										}
										
										
									}
								}
								
							}
							//DMD_BALANCE!=0
							else
							{
							int coll_diff=DMD_BALANCE-coll;
								System.out.println("coll_diff>>>>"+coll_diff);
								if(coll_diff==0)
								{
									 sql3="update PMS_DCB_DMD_AGE set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and BALANCE_CHECK='N' and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);							
									int k=ps3.executeUpdate();
									if(k>0)
									{
										if(status==0){
										String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
										System.out.println("sql3>>>"+sql11);
										ps3=con.prepareStatement(sql11);
										ps3.executeQuery();
										}
										else if(status==1)
										{
											String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
										}
										
										
									}		

								}
								
								else if(coll_diff>0){
									 sql3="update PMS_DCB_DMD_AGE set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);
								//	ps3.setString(1,r_date);
									int k=ps3.executeUpdate();
									if(k>0)
									{
										if(status==0){
										String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
										System.out.println("sql3>>>"+sql11);
										ps3=con.prepareStatement(sql11);
										ps3.executeQuery();
										}
										else if(status==1){
											String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
										}
										String sql12="insert into PMS_DCB_DMD_AGE (BEN_SNO,BILL_MONTH,BILL_YEAR,DMD,R_NO,R_DATE ,COLL_AMT,DMD_BALANCE,BALANCE_CHECK,COLL_BALANCE,REF_TYPE) VALUES("+ben_id+","+month+","+year+",0,"+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), "+amt+","+coll_diff+",'N',0,"+val+")";
												System.out.println("sql3>>>"+sql12);
												ps4=con.prepareStatement(sql12);
												ps4.executeQuery();
																									
									}
								}
								else if(coll_diff<0){
									int bal=coll-DMD_BALANCE;
									 sql3="update PMS_DCB_DMD_AGE set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and BALANCE_CHECK='N' and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);
									int k=ps3.executeUpdate();
									if(k>0)
									{
									
										String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+bal+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
										ps4=con.prepareStatement(sql4);
										System.out.println("sql4>>>>>"+sql4);
										rs4=ps4.executeQuery();
										if(rs4.next())
										{
											if(status==0)
											{
													String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
											}
											else if(status==1)
											{
													String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
													
											}
											String sql12="insert into PMS_DCB_DMD_AGE (BEN_SNO,BILL_MONTH,BILL_YEAR,DMD,R_NO,R_DATE ,COLL_AMT,DMD_BALANCE,BALANCE_CHECK,COLL_BALANCE,REF_TYPE) VALUES("+ben_id+","+month+","+year+",0,"+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), "+DMD_BALANCE+",0,'Y',"+bal+","+val+")";
											System.out.println("sql3>>>"+sql12);
											ps4=con.prepareStatement(sql12);
											ps4.executeQuery();
								
							}
							
							}
						 }
						}
					}
				
			}
						}
					
					
					for(i=0;i<=100;i++)
					{

						System.out.println("Inside for looppppppppppppppppppppppppppppppppppppppppppppppp");
						
						String sqll="select ben_sno,amt,r_no,r_date,month,year from PMS_DCB_COLL_BAL_AGE where ben_sno="+ben_id+" and STATUS='N' order by year,month,r_date,r_no ";
						ps11=con.prepareStatement(sqll);
						 System.out.println("Query$$$sql1"+sqll);
							rs11=ps11.executeQuery();
							if(rs11.next())
							{
								
								status=1;
								
								coll=rs11.getInt("amt");
								System.out.println("coll_Amount----"+coll);											
						        r_date=rs11.getDate("R_DATE");
								System.out.println("r_date----"+r_date);
								amt=rs11.getInt("amt");
								System.out.println("amt----"+amt);
								r_no=rs11.getInt("r_no");	
								cmonth=rs11.getInt("MONTH");
								cyear=rs11.getInt("YEAR");
								
								
							
						sql="select BEN_SNO,BILL_MONTH,BILL_YEAR,ob,OB_BALANCE from pms_dcb_ob_age where ben_sno="+ben_id+" and (BALANCE_CHECK='N' OR BALANCE_CHECK is null)  ";
						ps=con.prepareStatement(sql);
						System.out.println("Query$$$"+sql);
						rs=ps.executeQuery();
						if(rs.next())
						{
							
						 ob=rs.getInt("ob");
						 System.out.println("Opeing balance:"+ob);
						
						  OB_BALANCE=rs.getInt("OB_BALANCE");
						    System.out.println("OB_BALANCE----"+OB_BALANCE);
						}
						else
						{
							ob=0;
							 OB_BALANCE=0;
						}
						 //For OB
						 if(ob!=0 || OB_BALANCE!=0)
						 {
							 
							    int year=rs.getInt("BILL_YEAR");
							    System.out.println("year----"+year);
							    int month=rs.getInt("BILL_MONTH");
							    System.out.println("month----"+month);
								System.out.println("ob----"+ob);
								int Diff=ob-coll;
								System.out.println("Diff----"+Diff);
								if(OB_BALANCE==0)
								{

									if(Diff==0)
									{
										sql3="update pms_dcb_ob_age set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , COLL_AMT="+ amt +" , OB_BALANCE="+Diff+" , BALANCE_CHECK='Y', COLL_BALANCE=0 , r_no="+r_no+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year;
										System.out.println("sql3>>>"+sql3);
										ps3=con.prepareStatement(sql3);							
										int k=ps3.executeUpdate();
										if(k>0)
										{
											if(status==0){
											String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
											}
											else if(status==1)
											{
												String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
												System.out.println("sql3>>>"+sql11);
												ps3=con.prepareStatement(sql11);
												ps3.executeQuery();
											}
										}									

									}
									else if(Diff>0){
										 sql3="update pms_dcb_ob_age set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , COLL_AMT="+amt +" , OB_BALANCE="+Diff+" , BALANCE_CHECK='N',COLL_BALANCE=0 , r_no="+r_no+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year;
										System.out.println("sql3>>>"+sql3);
										ps3=con.prepareStatement(sql3);
									//	ps3.setString(1,r_date);
										int k=ps3.executeUpdate();
										if(k>0)
										{
											if(status==0){
											String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
											}
											else if(status==1){
												String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
												System.out.println("sql3>>>"+sql11);
												ps3=con.prepareStatement(sql11);
												ps3.executeQuery();
											}
										}
									}
									else if(Diff<0)
									{
										int bal=coll-ob;
										 sql3="update pms_dcb_ob_age set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , r_no="+r_no+", BALANCE_CHECK='Y',OB_BALANCE=0 ,COLL_BALANCE="+bal+" ,COLL_AMT="+ob+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year;
										System.out.println("sql3>>>"+sql3);
										ps3=con.prepareStatement(sql3);
										int k=ps3.executeUpdate();
										if(k>0)
										{
										
											String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+bal+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
											ps4=con.prepareStatement(sql4);
											System.out.println("sql4>>>>>"+sql4);
											rs4=ps4.executeQuery();
											if(rs4.next())
											{
												if(status==0)
												{
														String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
														System.out.println("sql3>>>"+sql11);
														ps3=con.prepareStatement(sql11);
														ps3.executeQuery();
												}
												else if(status==1)
												{
														String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
														System.out.println("sql3>>>"+sql11);
														ps3=con.prepareStatement(sql11);
														ps3.executeQuery();
														
												}
											
											}
											
											
										}
									}
									
								
								}
								
								
								//OB else part
								
								
								else
								{

									int coll_diff=OB_BALANCE-coll;
										System.out.println("coll_diff>>>>"+coll_diff);
										if(coll_diff==0)
										{
											 sql3="update pms_dcb_ob_age set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and BALANCE_CHECK='N'";
											System.out.println("sql3>>>"+sql3);
											ps3=con.prepareStatement(sql3);							
											int k=ps3.executeUpdate();
											if(k>0)
											{
												if(status==0){
												String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
												System.out.println("sql3>>>"+sql11);
												ps3=con.prepareStatement(sql11);
												ps3.executeQuery();
												}
												else if(status==1)
												{
													String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
												}
												
												
											}		

										}
										
										else if(coll_diff>0){
											 sql3="update pms_dcb_ob_age set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year;
											System.out.println("sql3>>>"+sql3);
											ps3=con.prepareStatement(sql3);
										//	ps3.setString(1,r_date);
											int k=ps3.executeUpdate();
											if(k>0)
											{
												if(status==0){
												String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
												System.out.println("sql3>>>"+sql11);
												ps3=con.prepareStatement(sql11);
												ps3.executeQuery();
												}
												else if(status==1){
													String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
												}
												String sql12="insert into pms_dcb_ob_age (BEN_SNO,BILL_MONTH,BILL_YEAR,OB,R_NO,R_DATE ,COLL_AMT,OB_BALANCE,BALANCE_CHECK,COLL_BALANCE) VALUES("+ben_id+","+month+","+year+",0,"+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), "+amt+","+coll_diff+",'N',0)";
														System.out.println("sql3>>>"+sql12);
														ps4=con.prepareStatement(sql12);
														ps4.executeQuery();
																											
											}
										}
										else if(coll_diff<0){
											int bal=coll-OB_BALANCE;
											 sql3="update pms_dcb_ob_age set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and BALANCE_CHECK='N'";
											System.out.println("sql3>>>"+sql3);
											ps3=con.prepareStatement(sql3);
											int k=ps3.executeUpdate();
											if(k>0)
											{
											
												String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+bal+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
												ps4=con.prepareStatement(sql4);
												System.out.println("sql4>>>>>"+sql4);
												rs4=ps4.executeQuery();
												if(rs4.next())
												{
													if(status==0)
													{
															String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
															System.out.println("sql3>>>"+sql11);
															ps3=con.prepareStatement(sql11);
															ps3.executeQuery();
													}
													else if(status==1)
													{
															String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
															System.out.println("sql3>>>"+sql11);
															ps3=con.prepareStatement(sql11);
															ps3.executeQuery();
															
													}
													String sql12="insert into pms_dcb_ob_age (BEN_SNO,BILL_MONTH,BILL_YEAR,OB,R_NO,R_DATE ,COLL_AMT,OB_BALANCE,BALANCE_CHECK,COLL_BALANCE) VALUES("+ben_id+","+month+","+year+",0,"+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), "+OB_BALANCE+",0,'Y',"+bal+")";
													System.out.println("sql3>>>"+sql12);
													ps4=con.prepareStatement(sql12);
													ps4.executeQuery();
										
									}
									
									}
								 }
								
								}
								
						
								
						 }
						 //OB==0
						 else
						 {
							 String sql2="select ben_sno,BILL_MONTH,BILL_YEAR,DMD,DMD_BALANCE,REF_TYPE from PMS_DCB_DMD_AGE where ben_sno="+ben_id+" and (BALANCE_CHECK='N' OR BALANCE_CHECK is null) and (DMD!=0 or DMD_BALANCE!=0 ) order by BILL_YEAR,BILL_MONTH,REF_TYPE ";
							  ps2=con.prepareStatement(sql2);
							  System.out.println("sql2>>>"+sql2);
							 rs2=ps2.executeQuery();
							if(rs2.next())
							{
								
							String REF_TYPE=rs2.getString("REF_TYPE");
							System.out.println("REF_TYPE----"+REF_TYPE);
							if(REF_TYPE.equals("J") || REF_TYPE.equals("j"))
							{
								System.out.println("inside journal");
								cond="REF_TYPE='J'";
								val="'J'";
							}
							else if(REF_TYPE.equals("R") || REF_TYPE.equals("r"))
							{
								System.out.println("inside receipt");
								cond="REF_TYPE='R'";
								val="'R'";
							}
							
						    int DMD_BALANCE=rs2.getInt("DMD_BALANCE");
						    System.out.println("DMD_BALANCE----"+DMD_BALANCE);
						    int year=rs2.getInt("BILL_YEAR");
						    System.out.println("year----"+year);
						    int month=rs2.getInt("BILL_MONTH");
						    System.out.println("month----"+month);
							int DMD=rs2.getInt("DMD");
							System.out.println("DMD----"+DMD);
							int Diff=DMD-coll;
							System.out.println("Diff----"+Diff);
							if(DMD_BALANCE==0)
							{
								if(Diff==0)
								{
									sql3="update PMS_DCB_DMD_AGE set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , COLL_AMT="+ amt +" , DMD_BALANCE="+Diff+" , BALANCE_CHECK='Y', COLL_BALANCE=0 , r_no="+r_no+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);							
									int k=ps3.executeUpdate();
									if(k>0)
									{
										if(status==0){
										String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
										System.out.println("sql3>>>"+sql11);
										ps3=con.prepareStatement(sql11);
										ps3.executeQuery();
										}
										else if(status==1)
										{
											String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
										}
									}									

								}
								else if(Diff>0){
									 sql3="update PMS_DCB_DMD_AGE set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , COLL_AMT="+amt +" , DMD_BALANCE="+Diff+" , BALANCE_CHECK='N',COLL_BALANCE=0 , r_no="+r_no+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);
								//	ps3.setString(1,r_date);
									int k=ps3.executeUpdate();
									if(k>0)
									{
										if(status==0){
										String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
										System.out.println("sql3>>>"+sql11);
										ps3=con.prepareStatement(sql11);
										ps3.executeQuery();
										}
										else if(status==1){
											String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
										}
									}
								}
								else if(Diff<0)
								{
									int bal=coll-DMD;
									 sql3="update PMS_DCB_DMD_AGE set R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') , r_no="+r_no+", BALANCE_CHECK='Y',DMD_BALANCE=0 ,COLL_BALANCE="+bal+" ,COLL_AMT="+DMD+" where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);
									int k=ps3.executeUpdate();
									if(k>0)
									{
									
										String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+bal+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
										ps4=con.prepareStatement(sql4);
										System.out.println("sql4>>>>>"+sql4);
										rs4=ps4.executeQuery();
										if(rs4.next())
										{
											if(status==0)
											{
													String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
											}
											else if(status==1)
											{
													String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
													
											}
										
										}
										
										
									}
								}
								
							}
							//DMD_BALANCE!=0
							else
							{
							int coll_diff=DMD_BALANCE-coll;
								System.out.println("coll_diff>>>>"+coll_diff);
								if(coll_diff==0)
								{
									 sql3="update PMS_DCB_DMD_AGE set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and BALANCE_CHECK='N' and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);							
									int k=ps3.executeUpdate();
									if(k>0)
									{
										if(status==0){
										String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
										System.out.println("sql3>>>"+sql11);
										ps3=con.prepareStatement(sql11);
										ps3.executeQuery();
										}
										else if(status==1)
										{
											String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+" and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
										}
										
										
									}		

								}
								
								else if(coll_diff>0){
									 sql3="update PMS_DCB_DMD_AGE set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);
								//	ps3.setString(1,r_date);
									int k=ps3.executeUpdate();
									if(k>0)
									{
										if(status==0){
										String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
										System.out.println("sql3>>>"+sql11);
										ps3=con.prepareStatement(sql11);
										ps3.executeQuery();
										}
										else if(status==1){
											String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
											System.out.println("sql3>>>"+sql11);
											ps3=con.prepareStatement(sql11);
											ps3.executeQuery();
										}
										String sql12="insert into PMS_DCB_DMD_AGE (BEN_SNO,BILL_MONTH,BILL_YEAR,DMD,R_NO,R_DATE ,COLL_AMT,DMD_BALANCE,BALANCE_CHECK,COLL_BALANCE,REF_TYPE) VALUES("+ben_id+","+month+","+year+",0,"+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), "+amt+","+coll_diff+",'N',0,"+val+")";
												System.out.println("sql3>>>"+sql12);
												ps4=con.prepareStatement(sql12);
												ps4.executeQuery();
																									
									}
								}
								else if(coll_diff<0){
									int bal=coll-DMD_BALANCE;
									 sql3="update PMS_DCB_DMD_AGE set  BALANCE_CHECK='Y' where ben_sno="+ben_id+" and BILL_MONTH="+month+" and BILL_YEAR="+year+" and BALANCE_CHECK='N' and "+cond;
									System.out.println("sql3>>>"+sql3);
									ps3=con.prepareStatement(sql3);
									int k=ps3.executeUpdate();
									if(k>0)
									{
									
										String sql4="insert into PMS_DCB_COLL_BAL_AGE(BEN_SNO,MONTH,YEAR,AMT,r_no,R_DATE,STATUS) values ("+ben_id+","+cmonth+","+cyear+","+bal+","+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), 'N')";
										ps4=con.prepareStatement(sql4);
										System.out.println("sql4>>>>>"+sql4);
										rs4=ps4.executeQuery();
										if(rs4.next())
										{
											if(status==0)
											{
													String sql11="update PMS_DCB_COLL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
											}
											else if(status==1)
											{
													String sql11="update PMS_DCB_COLL_BAL_AGE set STATUS='Y'  where ben_sno="+ben_id+" and R_DATE=TO_DATE('"+r_date+"','YYYY-MM-DD') and AMT="+amt+ " and  r_no="+r_no ;
													System.out.println("sql3>>>"+sql11);
													ps3=con.prepareStatement(sql11);
													ps3.executeQuery();
													
											}
											String sql12="insert into PMS_DCB_DMD_AGE (BEN_SNO,BILL_MONTH,BILL_YEAR,DMD,R_NO,R_DATE ,COLL_AMT,DMD_BALANCE,BALANCE_CHECK,COLL_BALANCE,REF_TYPE) VALUES("+ben_id+","+month+","+year+",0,"+r_no+",TO_DATE('"+r_date+"','YYYY-MM-DD'), "+DMD_BALANCE+",0,'Y',"+bal+","+val+")";
											System.out.println("sql3>>>"+sql12);
											ps4=con.prepareStatement(sql12);
											ps4.executeQuery();
								
							}
							
							}
						 }
						}
					}
				
			}
						
					
					}}
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			
		
		}
		out.println(xml);
		System.out.println("Xml>>>>>"+xml);
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
