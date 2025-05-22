

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.PMS.PMS1.DCB.servlets.Controller;
public class Receipt_Trouble_shoot extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	private Connection con = null;
    public Receipt_Trouble_shoot() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		response.setContentType(CONTENT_TYPE);
		Map parameters = new HashMap();
		Controller obj = new Controller();
		Controller obj2 = new Controller();
		try
		{
			con=obj.con();
			obj.createStatement(con);  
			obj2.createStatement(con);
		}catch(Exception e){}
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null) 
		{
				response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		String Office_id="",month="",year="";
		try
		{
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID","where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+ userid + "')");			
			
			month =obj.setValue("smonth", request);//obj.isNull(obj.getValue("PMS_DCB_SETTING", "MONTH"," where OFFICE_ID="+Office_id), 1);
			year =obj.setValue("syear", request);;//obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR","where OFFICE_ID="+Office_id), 1);
			
		}catch(Exception e) {}
		String new_cond=Controller.new_cond;
		String meter_status=Controller.meter_status;
		try     
		{  
			String monval=obj.month_val(month);
			String Office_Name=obj.getValue("com_mst_all_offices_view", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");		 
			String auid=obj.getValue("FAS_MST_ACCT_UNIT_OFFICES", "ACCOUNTING_UNIT_ID","where ACCOUNTING_FOR_OFFICE_ID="+Office_id+ " ");
	//	String qry="select CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,ACCOUNT_HEAD_CODE from FAS_RECEIPT_TRANSACTION where (accounting_for_office_id,CASHBOOK_MONTH,CASHBOOK_YEAR) =(select "+Office_id+
	//			","+month+" ,"+year+" from dual) and  exists (select receipt_no from FAS_RECEIPT_MASTER  where accounting_for_office_id="+Office_id+" and CASHBOOK_MONTH="+month+" and CASHBOOK_YEAR="+year+" and sub_ledger_type_code=14   and receipt_no=FAS_RECEIPT_TRANSACTION.receipt_no" +
	//						" ) and not exists (  select 'a' from PMS_MST_PROJECTS_VIEW a where a.office_id="+Office_id+" and a.project_id=FAS_RECEIPT_TRANSACTION.SUB_LEDGER_CODE)" ;
						//"   not exists ( select 'a' from PMS_DCB_RECEIPT_ACCOUNT_MAP a where COLLECTION_TYPE in (7,8,9) and a.ACCOUNT_HEAD_CODE=FAS_RECEIPT_TRANSACTION.ACCOUNT_HEAD_CODE )";
 		PrintWriter pr=response.getWriter();
 		String qry=" SELECT RECEIPT_NO, TO_CHAR(RDATE,'DD-MM-YYYY') as RDATE, AMOUNT, ACCOUNT_HEAD_CODE, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, CR_DR_INDICATOR, SCH_SNO,(select sch_name from pms_sch_master where sch_sno=a.sch_sno) as sch_name, "+
 		  " BENEFICIARY_NAME,BENEFICIARY_SNO, BENEFICIARY_TYPE_ID,(select BEN_TYPE_DESC from PMS_DCB_BEN_TYPE where BEN_TYPE_ID=a.BENEFICIARY_TYPE_ID) as ben_type_dec,	  case  "+
 		   " when ACCOUNT_HEAD_CODE not in (select ACCOUNT_HEAD_CODE from PMS_DCB_RECEIPT_ACCOUNT_MAP) then    '<font color=red>Wrong Receipt Acc.Head Code</font>' "+
 		   " when (SUB_LEDGER_CODE is null  or  SUB_LEDGER_CODE=0)  then "+
 		   " '<font color=red>SL Code Missing</font> '   when SUB_LEDGER_TYPE_CODE is null  then   '<font color=red>SL Type Code Missing</fond> ' "+    
 		    " when a.sch_sno not in (select distinct scheme_sno from pms_dcb_mst_beneficiary_metre where beneficiary_sno=a.BENEFICIARY_SNO) then "+ 
 		   " '<font color=red>SL Code Mis-Match</font> '   end as Error 	FROM PMS_DCB_FAS_RECEIPT_VIEW a		WHERE accounting_for_office_id="+Office_id+
 		   " AND cashbook_month="+month+"		AND cashbook_year="+year+" 		order by Error " ;   
 		String html="<table width='100%' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c298'>"+"<tr bgcolor='#81F7F3'><td colspan=10 align=center>"+Office_Name+" ("+auid+")</td></tr>"+
 				"<tr bgcolor='#81F7F3'><td colspan=10  align=center>Receipt Verificaition for "+monval+"-"+year+"</td></tr>" +
 				"<tr ><td colspan=10>";    
 		 html+="<div id='scroll_clipper' style='position:relative; width:100%; border-height:1px; height: 520px; overflow:auto;white-space:nowrap;'>";
 		 html+="<div id='scroll_text'  ><table  cellspacing='0' cellpadding='3' style='BORDER-COLLAPSE: collapse' border='1' borderColor='#92c2d8'   border=2><tr><td width='3%'>Sl.No</td><td>Beneficiary Name</td><td width='5%'>Receipt No </td><td width='5%'>Receipt Date</td><td width='5%'>Sub ledger Type Code</td><td>Sub ledger Code</td><td width='5%'>Account Head Code</td><td align=center width='7%'>Amount</td><td align=center>Issue</td><td align=center>Correct SL Description <br>( As Per Beneficiary Meter Record) </td></tr>";  
		String sch_sno="0",type="0",receipt_no="0",receipt_dt="",ben_sno="",amt="",oben_sno="0",ben_name="",ACCOUNT_HEAD_CODE="",desc="";  
		String innerHTML="";
		String qry_res="";
		ResultSet rs=obj.getRS(qry);  
		int i=0;   
		int c=0;
		while (rs.next()) 
		{                 
			i++;       
			desc=obj.isNull(rs.getString("Error"), 2);
			receipt_dt=obj.isNull(rs.getString("RDATE"), 1);
			amt=obj.isNull(rs.getString("AMOUNT"), 1);
			ben_name=obj.isNull(rs.getString("BENEFICIARY_NAME"), 1);
			ACCOUNT_HEAD_CODE=obj.isNull(rs.getString("ACCOUNT_HEAD_CODE"), 1);
			sch_sno=obj.isNull(rs.getString("sch_name"),3)+"("+obj.isNull(rs.getString("SUB_LEDGER_CODE"),3)+")";     
			type=obj.isNull(rs.getString("SUB_LEDGER_TYPE_CODE"),1);        
			type=obj.getValue("com_mst_sl_types", "SUB_LEDGER_TYPE_DESC", " where SUB_LEDGER_TYPE_CODE="+type);  
			receipt_no=obj.isNull(rs.getString("RECEIPT_NO"),1);
			ben_sno=obj.isNull(rs.getString("BENEFICIARY_SNO"),1);      
			String desc_new="";
			if (!desc.equalsIgnoreCase("-"))
			{
				desc_new=desc;
			}else
			{
				desc_new="<font color='green' size=3><center>Ok.</center></font>";
			}
				html+="<tr style='font-size: 0.91em;'  ><td   align='center' width='5%'>"+i+"</td><td> "+ben_name+"(&nbsp;"+ben_sno+"&nbsp;) </td><td  align='center' width='5%'>"+receipt_no+"</td><td  align='center' width='5%'>"+receipt_dt+"</td><td  align='center'>"+type+"</td><td  align='left'>"+sch_sno+"</td><td  align='center'>"+ACCOUNT_HEAD_CODE+"</td><td  align='right'>"+amt+"</td><td align=left>"+desc_new+"</td>" ;
				if (!desc.equalsIgnoreCase("-"))
				{     
					qry_res+="<td><table width='100%' cellspacing='0' cellpadding='3' style='BORDER-COLLAPSE: collapse' border='1' borderColor='darkblue'   border=2>";       
				String qry2="SELECT sch.SCH_NAME,sch.PROJECT_ID,met.SCHEME_SNO FROM (SELECT BENEFICIARY_SNO,OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY  WHERE status='L' "+
				  " and OFFICE_ID="+Office_id+"  AND BENEFICIARY_SNO="+ben_sno+" ) ben JOIN (SELECT distinct SCHEME_SNO,BENEFICIARY_SNO,OFFICE_ID  FROM PMS_DCB_MST_BENEFICIARY_METRE "+
				  " WHERE meter_status ='L' AND BENEFICIARY_SNO="+ben_sno+" ) met ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO AND met.OFFICE_ID=ben.OFFICE_ID "+
				  " JOIN  ( SELECT SCH_SNO,SCH_NAME, PROJECT_ID FROM PMS_SCH_MASTER   )sch ON sch.SCH_SNO=met.SCHEME_SNO    ";
				   c++;
					int r1=0;
					String project_id="";
					String SCHEME_SNO="";
					ResultSet rs2=obj2.getRS(qry2);           
					while(rs2.next())        
					{   
						SCHEME_SNO=obj2.isNull(rs2.getString("SCHEME_SNO"),1);
						project_id=obj2.isNull(rs2.getString("PROJECT_ID"),1);
						r1++;  
						qry_res+="<tr style='font-size: 0.91em;' >";      
						qry_res+="<td><font color=green>"+obj2.isNull(rs2.getString("SCH_NAME"),1)+"(&nbsp; "+project_id+" &nbsp; )</font></td>";  
						qry_res+=" </tr>";
					}   
					qry_res+="</table> ";
				}else
				{
					qry_res+="<td>&nbsp;</td>";
			   }
						
					html+=qry_res;  	
					html+="</td></div></div></tr>";
					qry_res="";
					
			}
		    String html2="";
		if (c ==0 )
		{
			html2+="<tr><td colospan=10 align=center><input type=submit style='font-size:14;font-weight:bold;color :Green' value='DCB Receipt Verified'>&nbsp;&nbsp;&nbsp;<input type=button value='Exit'  onclick='window.close()'></td></tr></table>";
		}
		else
		{
			html2+="<tr bgcolor='#81F7F3'><td colospan=10 align=center><input type=button value='Exit' onclick='window.close()'></td></tr></table>";
		}  
		html+="</table></td></tr>"+html2+"</table>";
		pr.println(html+"</form></body></html>");  
		html="";
 	
	}catch(Exception e)
	{   
		System.out.println(e);
	}
 		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
