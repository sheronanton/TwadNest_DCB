package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dcb.reports.Ben_Cancel;
import dcb.reports.Ben_Details;

public class single_done_delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public single_done_delete() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj = new Controller();
		response.setContentType(CONTENT_TYPE);
		PrintWriter pr = response.getWriter();
		Connection con = null;
		Statement stmt = null;
		String userid="",xml="",html="",html2="";
		try {
				con = obj.con();
				stmt = con.createStatement();		
				HttpSession session = request.getSession(false);
				userid = (String) session.getAttribute("UserId");
				if (userid == null) 
				{
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				}
				
				String Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");
				if (Office_id.equalsIgnoreCase("0")) Office_id="5000";
				String type=obj.setValue("type", request);
				String District=obj.setValue("District", request);
				String block=obj.setValue("block", request);
				String pmonth=obj.setValue("pmonth", request);
				String pyear=obj.setValue("pyear", request);
				System.out.println("DCB->single_done_delete->type->" + type);
				if (Integer.parseInt(type)==1) 
				{
					xml+=obj.resultXML("select district_code,district_name from com_mst_districts where district_code in (select district_code from pms_dcb_div_dist_map where office_id="+Office_id+") ",con,obj);
				}
				if (Integer.parseInt(type)==2) 
				{
					xml+=obj.resultXML("select block_sno,block_name from com_mst_blocks where district_code="+District,con,obj);
				}
				if (Integer.parseInt(type)==3)
				{
					xml+=obj.resultXML("select a.BEN_TYPE_ID,a.BEN_TYPE_DESC from PMS_DCB_BEN_TYPE a where exists (select BENEFICIARY_TYPE_ID from pms_dcb_mst_beneficiary where status='L' and office_id="+Office_id+" and a.ben_type_id=BENEFICIARY_TYPE_ID) ",con,obj);
				}if (Integer.parseInt(type)==4)
				{  
					type=obj.setValue("btype", request);
					String cond=" ",cond2=""; 
					if (!block.equalsIgnoreCase("0") && !District.equalsIgnoreCase("0"))
						 cond=" and block_sno="+block+" and district_code="+District;
					else if (!block.equalsIgnoreCase("0"))
						cond=" and block_sno="+block+"";
					else if (!District.equalsIgnoreCase("0"))
						cond=" and district_code="+District;
					else
						cond="";    
					
					if (type!="0")
						cond2="and BENEFICIARY_TYPE_ID="+type+"  "; 
					 
					xml+=obj.resultXML("select BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where status='L' "+cond2+" and office_id="+Office_id+"  "+cond+" order by BENEFICIARY_NAME",con,obj);
				}
				if(Integer.parseInt(type)==11)
				{
						PreparedStatement ps =null;
						 int row=0;
						 int row1=0; 
						String ben_sno=obj.setValue("bensno", request);					
						try {
							ps = con.prepareStatement("update PMS_DCB_MST_BENEFICIARY_METRE  set UPDATED_BY_USER_ID='"+userid+"',UPDATED_DATE=clock_timestamp(),CANCEL_MONTH="+pmonth+",CANCEL_YEAR="+pyear+", meter_status='C'  where BENEFICIARY_SNO=? and meter_status='L'");
							ps.setString(1, ben_sno);  
							ps.setString(1, ben_sno);  
							ps.executeUpdate();
							try {
									con=obj.con();									   
									stmt=con.createStatement();
									obj.createStatement(con);
							 }catch(Exception e) {
								 System.out.println("Meter---Delete"+e);  
							 }
							  String sqlDelete = " Update PMS_DCB_MST_BENEFICIARY  set CANCEL_MONTH="+pmonth+",CANCEL_YEAR="+pyear+",status='C' " +
							  					 " WHERE BENEFICIARY_SNO = ? and status='L' and BENEFICIARY_SNO not in (Select BENEFICIARY_SNO from PMS_DCB_MST_BENEFICIARY_METRE where meter_status='L' )";
							  ps = con.prepareStatement(sqlDelete);
							  ps.setString(1, ben_sno);
							  row1 = ps.executeUpdate();
							  
					} catch (Exception e) 
					{ 
						 System.out.println("Meter---Delete"+e);  
					 
						
					}
					xml="<response><row1>"+row1+"</row1><row>"+row+"</row><row_count>1</row_count> </response>"; 
					
				}
				if (Integer.parseInt(type)==5)
				{  
					
					String ben=obj.isNull(obj.setValue("bensno",request),1);
					String cls_wc_charges="0.0";
					String cls_maint_charges="0.0";
					String cls_int_charges="0.0";
					String cls_yester_year="0.0",style="",msg="";
					String []cmonth ={"-select month-","January","February","March","April","May","June","July","August","September","October","November","December"};
					int ben_flag=0;
					int met_flag=0;
					if (!pmonth.equalsIgnoreCase("0") && !pyear.equalsIgnoreCase("0"))
					{
						
						Ben_Cancel b=new Ben_Cancel();
						ArrayList ar_list=b.execute(ben,pyear,pmonth);   			
						Iterator er=ar_list.iterator();
						html="<data><![CDATA[<table  width='95%' align='center' cellpadding='5' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>";
						if (er.hasNext()) 
						{
							Ben_Details ben_res=(Ben_Details)er.next();
							cls_wc_charges=obj.isNull(ben_res.getCls_wc_charges(),1);
							cls_maint_charges=obj.isNull(ben_res.getCls_maint_charges(),1);
							cls_int_charges=obj.isNull(ben_res.getCls_int_charges(),1);
							cls_yester_year=obj.isNull(ben_res.getCls_yester_year(),1);
							System.out.println(cls_wc_charges);
							System.out.println(cls_maint_charges);
							System.out.println(cls_int_charges);
							html+="<tr class='tdLText'><td> Beneficiary Name </td><td colspan='3'>"+ben_res.getBen_name()+"</td></tr>";
							html+="<tr  class='tdLText'><td colspan='4'><b><u>Closing Balance(in Rs.)</u></b></td></tr>";
							html+="<tr  class='tdLText'> <td> Water Charge   </td><td> Maintenance Charges   </td><td> Yester year Water Charge</td><td> Water Charges Interest </td><tr>";
							html+="<tr> <td align='right'>"+cls_wc_charges+"</td><td align='right'>"+cls_maint_charges+"</td><td align='right'>"+cls_yester_year+"</td><td align='right'>"+cls_int_charges+"</td><tr>";
						 
						}
						if ( Double.parseDouble(cls_wc_charges)==0  || Double.parseDouble(cls_maint_charges)==0 || Double.parseDouble(cls_int_charges)==0 || Double.parseDouble(cls_yester_year)==0)
						{
							ben_flag=1;
						}else
						{  
							ben_flag=0;
						}
							html+="<tr  class='tdLText'><td colspan='4'><b><u>Pumping Return Details(in KL.)</u></td></tr>";
						
						int prv_month=obj.prv_month(pmonth,pyear);
						int prv_year=obj.prv_year(pmonth,pyear);  
						
						int prv_month_2=obj.prv_month(Integer.toString(prv_month),Integer.toString(prv_year));
						int prv_year_2=obj.prv_year(Integer.toString(prv_month),Integer.toString(prv_year));
						
						double pumping_return1=0.0,pumping_return2=0.0;
						String cond="where BENEFICIARY_SNO="+ben+" and OFFICE_ID="+Office_id+" and MONTH="+prv_month+" and YEAR="+prv_year;
						String cond2="where BENEFICIARY_SNO="+ben+" and OFFICE_ID="+Office_id+" and MONTH="+prv_month_2+" and YEAR="+prv_year_2;
						try 
						{ 
							pumping_return1=Double.parseDouble(obj.getValue("PMS_DCB_TRN_MONTHLY_PR","sum(QTY_CONSUMED_NET)","qty",cond));
							pumping_return2=Double.parseDouble(obj.getValue("PMS_DCB_TRN_MONTHLY_PR","sum(QTY_CONSUMED_NET)","qty",cond2));
						}catch(Exception e) 
						{
							System.out.println(e);
						} 

						if ( pumping_return1!=0.0 )
						{
							met_flag=1;
						}else
						{
							met_flag=0;
						}
						html+="<tr> <td colspan='2' align='center'>"+cmonth[prv_month_2]+"&nbsp;"+prv_year_2+"</td><td colspan='2'  align='center'>"+cmonth[prv_month]+"&nbsp;"+prv_year+"</td></tr>";
						html+="<tr> <td colspan='2' align='center'>"+pumping_return2+"</td><td colspan='2'  align='center'>"+pumping_return1+"</td></tr>";
						html+="<input type='hidden' id='ben_flag' value='"+ben_flag+"'></table>]]></data>";
						
						
						
						 html2="<met_data><![CDATA[<table  width='95%' align='center' cellpadding='5' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'>";
						html2+="<tr><td>Sl.No</td><td>Meter Location</td><td>Scheme</td><td>Meter fixed</td><td>Meter Working</td><tr>";
						String qry=" select a.SCHEME_SNO," + 
								" case when upper(a.METRE_FIXED)=upper('y') then 'Yes' else 'No' end  AS METRE_FIXED,  "+
								" case when upper(a.METRE_WORKING)=upper('y') then 'Yes' else 'No' end  AS METRE_WORKING, "+
								" (select sch_name from pms_sch_master b where b.sch_sno=a.SCHEME_SNO) as sch_name,  a.sub_div_id, (SELECT c.office_name  FROM com_mst_all_offices_view c " +
								" WHERE a.sub_div_id=c.office_id  ) AS sub_div_name, a.METRE_LOCATION   from  PMS_DCB_MST_BENEFICIARY_METRE a where a.meter_status='L' " +
								" and a.beneficiary_sno="+ben;
						System.out.println(qry);
						PreparedStatement ps=con.prepareStatement(qry);
						int row=0;
						ResultSet rs=ps.executeQuery();
						while(rs.next())
						{
							row++;
							html2+="<tr><td>"+row+"</td><td>"+rs.getString("METRE_LOCATION")+"</td><td>"+rs.getString("sch_name")+"</td><td>"+rs.getString("METRE_FIXED")+"</td><td>"+rs.getString("METRE_WORKING")+"</td><tr>";
						}
						System.out.println("met_flag" +met_flag); 
						System.out.println("ben_flag" +ben_flag);
						if (met_flag==1 || ben_flag==1) 
							html2+="<tr><td align='center' colspan='5'><input type=button value='Beneficiary  Delete'  name='b2' id='b2'  disabled='disabled'></td></tr>";
						else
							html2+="<tr><td align='center' colspan='5'><input type=button value='Beneficiary  Delete' onclick='delete_fun(11)'></td></tr>";
						
						html2+="<input type='hidden' id='met_flag' value='"+met_flag+"'></table>]]></met_data>"; 
						if (cls_wc_charges.equalsIgnoreCase("0") && cls_maint_charges.equalsIgnoreCase("0") && cls_int_charges.equalsIgnoreCase("0") && cls_yester_year.equalsIgnoreCase("0"))
						{
							style=" style='display:display'";
						}else 
						{
							style=" style='display:none'";
							msg="<font color='red' size='3'>This Beneficiary  cannot be deleted as closing balance is not zero.....Please Contact Head Office</font> ";
						}
						
					}else
					{
						style=" style='display:none'";
					}
					xml="<response>"+html+html2+"<row_count>1</row_count> </response>";  
				} 
				System.out.println(xml);
				pr.write(xml); 
				pr.flush();
				pr.close();
				obj.conClose(con);
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
