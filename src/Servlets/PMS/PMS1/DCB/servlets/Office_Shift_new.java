package Servlets.PMS.PMS1.DCB.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Office_Shift_new extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int rs1;
		String sql=null;
		
		
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
			int New_off_id=Integer.parseInt(request.getParameter("New_off_id"));
			try
			{
				sql="select * from COM_MST_OFFICES where OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,New_off_id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<New_off_name>"+rs.getString("OFFICE_NAME")+"</New_off_name>";
					xml=xml+"<OFFICE_STATUS_ID>"+rs.getString("OFFICE_STATUS_ID")+"</OFFICE_STATUS_ID>";

				
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			}
		
		else if(command.equalsIgnoreCase("getdata4"))
		{
			xml=xml+"<response><command>getdata4</command>";
			int New_off_id=Integer.parseInt(request.getParameter("New_off_id4"));
			try
			{
				sql="select * from COM_MST_OFFICES where OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,New_off_id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<New_off_name4>"+rs.getString("OFFICE_NAME")+"</New_off_name4>";
					xml=xml+"<OFFICE_STATUS_ID4>"+rs.getString("OFFICE_STATUS_ID")+"</OFFICE_STATUS_ID4>";

	//				OFFICE_STATUS_ID  
				
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			}
		//BYpass the Dcb Frezze
		else if(command.equalsIgnoreCase("rld"))
		{
			System.out.println("reached rld");

			xml=xml+"<response><command>rld</command>";
			String flag="Y";
			int Office_id=Integer.parseInt(request.getParameter("Office_id"));
			int month=Integer.parseInt(request.getParameter("month"));
			int year=Integer.parseInt(request.getParameter("year"));
			int unit=Integer.parseInt(request.getParameter("unit"));
			String id=request.getParameter("id");
			try
			{

				sql = "insert into PMS_DCB_DATA_FREEZE (OFFICE_ID,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,CASHBOOK_YEAR,DCB_FREEZE,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,clock_timestamp())";
				ps=con.prepareStatement(sql);
				ps.setInt(1,Office_id);
				ps.setInt(2,unit);
				ps.setInt(3,Office_id);
				ps.setInt(4,month);
				ps.setInt(5,year);
				ps.setString(6,flag);
				ps.setString(7,id);
			    ps.executeQuery();
				System.out.println("insert Sucess");
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}}
		
		
		else if(command.equalsIgnoreCase("rldcheck_coll"))
		{
			xml=xml+"<response><command>rldcheck_coll</command>";
			int y=Integer.parseInt(request.getParameter("year"));
			int m=Integer.parseInt(request.getParameter("month"));
			int id=Integer.parseInt(request.getParameter("Office_id"));
			try
			{
				sql="select BENEFICIARY_NAME,BENEFICIARY_SNO,Diff from (SELECT (SUM(a.coln_cur_yr_wc) - SUM( b.coln_cur_yr_wc)) AS Diff,a.beneficiary_sno,c.BENEFICIARY_NAME FROM (SELECT bill_sno,coln_cur_yr_wc, beneficiary_sno FROM pms_dcb_trn_bill_dmd  WHERE bill_month     =?  AND bill_year        =?  AND beneficiary_sno IN  (SELECT beneficiary_sno  FROM pms_dcb_mst_beneficiary  WHERE status ='L'  AND office_id=?  ) )a LEFT OUTER JOIN (SELECT BILL_SNO ,  beneficiary_sno, SUM(coln_cur_yr_wc) AS coln_cur_yr_wc FROM pms_dcb_trn_bill_dmd_sch  group by BILL_SNO, beneficiary_sno )b ON a.bill_sno  =b.bill_sno AND b.beneficiary_sno=a.beneficiary_sno LEFT OUTER JOIN (SELECT beneficiary_sno ,BENEFICIARY_NAME   FROM pms_dcb_mst_beneficiary  WHERE status ='L'  AND office_id=?  )c ON a.beneficiary_sno        =c.beneficiary_sno GROUP BY a.beneficiary_sno, c.BENEFICIARY_NAME ) as shift where diff not in (0)";
				ps=con.prepareStatement(sql);
				ps.setInt(1,m);
				ps.setInt(2,y);
				ps.setInt(3,id);
				ps.setInt(4,id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("beneficiary_name")+"</beneficiary_name> <Diff>"+rs.getString("Diff")+"</Diff> <BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO")+"</BENEFICIARY_SNO> </count>";
					System.out.println("Ben is"+rs.getString("beneficiary_name")+"and diff is "+rs.getString("Diff"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		else if(command.equalsIgnoreCase("rldcheck_coll_current_month"))
		{
			xml=xml+"<response><command>rldcheck_coll_current_month</command>";
			int y=Integer.parseInt(request.getParameter("year"));
			int m=Integer.parseInt(request.getParameter("month"));
			int id=Integer.parseInt(request.getParameter("Office_id"));
			try
			{
				
				sql = " WITH FAS_COL AS (sELECT BENEFICIARY_CODE AS  BENEFICIARY_SNO , SUM(amount) AS WC_COL FROM (select "
						+ "	BEN.OFFICE_ID,  "
						+ "	off.OFFICE_NAME, "
						+ "	DST.district_name , "
						+ "	BCK.block_name , "
						+ "	PCH.panch_name  as PANCHAYAT_NAME, "
						+ "    Ben.beneficiary_type_id_sub ,  "
						+ "    TYP.BEN_TYPE_DESC as BENEFICIARY_TYPE, "
						+ "	REC.BENEFICIARY_SNO as BENEFICIARY_CODE , "
						+ "	BEN.beneficiary_name , "
						+ "	REC.CASHBOOK_YEAR, "
						+ "	case REC.CASHBOOK_MONTH	  "
						+ "			when 1 then 'JANUARY'  "
						+ "			when 2 then 'FEBRUARY'  "
						+ "			when 3 then 'MARCH'  "
						+ "			when 4 then 'APRIL'  "
						+ "			when 5 then 'MAY'  "
						+ "			when 6 then 'JUNE'  "
						+ "			when 7 then 'JULY'  "
						+ "			when 8 then 'AUGUST'  "
						+ "			when 9 then 'SEPTEMBER'  "
						+ "			when 10 then 'OCTOBER'  "
						+ "			when 11 then 'NOVEMBER'  "
						+ "			when 12 then 'DECEMBER'  "
						+ "		END  CASHBOOK_MONTH , "
						+ "	REC.receipt_date, "
						+ "	REC.receipt_no , "
						+ "	REC.account_head_code , "
						+ "	case "
						+ "		when REC.account_head_code::VARCHAR like '782%' then 'WC' "
						+ "		when REC.account_head_code::VARCHAR like '120601%' then 'INT' "
						+ "		when REC.account_head_code::VARCHAR like '780%' then 'TDA' "
						+ "		else 'MAINT' "
						+ "	end as type, "
						+ "	 "
						+ "	case "
						+ "		when REC.account_head_code::VARCHAR like '780%' then 'TDA' "
						+ "		else REC.mode "
						+ "	end as mode, "
						+ "	REC.amount  "
						+ " from "
						+ "	( "
						+ "	select "
						+ "		* "
						+ "	from "
						+ "		( "
						+ "		select "
						+ "			MST.sub_ledger_code as BENEFICIARY_SNO , "
						+ "			TRN.cashbook_year , "
						+ "			TRN.cashbook_month , "
						+ "			receipt_date as RECEIPT_DATE, "
						+ "			TRN.receipt_no , "
						+ "			TRN.account_head_code , "
						+ "			TRN.amount, "
						+ "			'RECEIPT' as mode "
						+ "		from "
						+ "			fas_receipt_master mst , "
						+ "			fas_receipt_transaction trn "
						+ "		where "
						+ "			mst.cashbook_year = trn.cashbook_year "
						+ "			and  "
						+ "	mst.cashbook_month = trn.cashbook_month "
						+ "			and  "
						+ "	mst.accounting_for_office_id = trn.accounting_for_office_id "
						+ "			and "
						+ "	mst.accounting_unit_id = trn.accounting_unit_id "
						+ "			and  "
						+ "	mst.receipt_no = trn.receipt_no "
						+ "		and mst.accounting_for_office_id = "+id+"::int "
						+ "			and mst.cashbook_year ="+y+"::int "
						+ "			and mst.cashbook_month = "+m+"::int "
						+ "			and MST.CREATED_BY_MODULE in  ('BR','CR') "
						+ "			and MST.RECEIPT_STATUS = 'L' "
						+ "			and MST.SUB_LEDGER_TYPE_CODE = 14 "
						+ "			and (TRN.account_head_code::VARCHAR like '78%' "
						+ "				or TRN.account_head_code::VARCHAR like '120601%' "
						+ "				or TRN.account_head_code in ( "
						+ "				select "
						+ "					account_head_code "
						+ "				from "
						+ "												PMS_DCB_RECEIPT_ACCOUNT_MAP "
						+ "				where "
						+ "												sch_type_id = 12 "
						+ "					and ACTIVE_STATUS = 'L' "
						+ "					and collection_type = 8 ) ) "
						+ "		order by "
						+ "			TRN.receipt_no  "
						+ " ) RECEIPT "
						+ " union all  "
						+ "( "
						+ "	select "
						+ "		beneficiary_sno , "
						+ "		cashbook_year , "
						+ "		cashbook_month , "
						+ "		to_date(VOUCHER_DATE::varchar,'dd/mm/yyyy') as VOUCHER_DATE, "
						+ "		voucher_no as RECEIPT_NO, "
						+ "		account_head_code , "
						+ "		amount, "
						+ "		'ADJUSTMENT' as mode "
						+ "	from "
						+ "		pms_dcb_other_charges pdoc "
						+ "	where "
						+ "		cashbook_year = "+y+"::int "
						+ "		and cashbook_month = "+m+"::int "
						+ "		and office_id::numeric = "+id+"::int "
						+ "		and cr_dr_indicator = 'CR' "
						+ " )				 "
						+ " ) REC "
						+ " left join pms_dcb_mst_beneficiary BEN on 	BEN.beneficiary_sno = REC.BENEFICIARY_SNO "
						+ " left join com_mst_districts DST on DST.district_code = BEN.district_code  "
						+ " left join com_mst_blocks BCK on BCK.block_sno = BEN.block_sno  "
						+ " left join com_mst_panchayats PCH on PCH.panch_sno = BEN.village_panchayat_sno  "
						+ " left join COM_MST_OFFICES off on off.OFFICE_ID = BEN.OFFICE_ID "
						+ " left join pms_dcb_ben_type TYP on TYP.BEN_TYPE_ID = BEN.BENEFICIARY_TYPE_ID_SUB  "
						+ " "
						+ " order by BEN.BENEFICIARY_TYPE_ID_SUB  "
						+ " ) AS OPT1  "
						+ " where type = 'WC' "
						+ " GROUP BY BENEFICIARY_CODE "
						+ " order by  "
						+ " BENEFICIARY_code "
						+ " ) ,  "
						+ " DCB_COL AS ( "
						+ " SELECT BENEFICIARY_SNO , sum(COLN_INCLUDE_CHARGES)+sum(COLN_FOR_MTH_YES_YR_WC) AS WC_COL FROM  "
						+ " PMS_DCB_LEDGER_ACTUAL  where month="+m+" and year="+y+" and office_id="+id+"   GROUP BY BENEFICIARY_SNO ORDER BY BENEFICIARY_SNO  ) "
						+ " SELECT BEN.BENEFICIARY_SNO, BEN.BENEFICIARY_NAME , (FAS.WC_COL - DCB.WC_COL) AS difference  FROM  FAS_COL FAS LEFT JOIN DCB_COL DCB ON  DCB.BENEFICIARY_SNO = FAS.BENEFICIARY_SNO "
						+ " LEFT JOIN pms_dcb_mst_beneficiary BEN ON BEN.BENEFICIARY_SNO = FAS.BENEFICIARY_SNO where (FAS.WC_COL - DCB.WC_COL) <> 0 ";
						
				
				
				ps=con.prepareStatement(sql);
//				ps.setInt(1,m);
//				ps.setInt(2,y);
//				ps.setInt(3,id);
//				ps.setInt(4,id);
				System.out.println("Beneficiary Wise Collection Check Query $$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("BENEFICIARY_NAME")+"</beneficiary_name>  <BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO")+"</BENEFICIARY_SNO> <difference>"+rs.getString("difference")+" </difference></count>";
					System.out.println("Ben is "+rs.getString("beneficiary_name")+"and difference is "+rs.getString("difference"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		else if(command.equalsIgnoreCase("rldcheck_dmd_current_month")) {
			
			xml=xml+"<response><command>rldcheck_dmd_current_month</command>";
			int y=Integer.parseInt(request.getParameter("year"));
			int m=Integer.parseInt(request.getParameter("month"));
			int id=Integer.parseInt(request.getParameter("Office_id"));
			try
			{
				
				sql = " Select * from ( "
						+ " select ledger.beneficiary_NAME, wc.beneficiary_sno , wc_dmd ,  ledger_demand , wc_dmd -  ledger_demand as difference from ( "
						+ " "
						+ " Select beneficiary_sno , sum(total_amt) as wc_dmd from pms_dcb_wc_billing where year="+y+" and month ="+m+" and office_id = "+id+"  "
						+ " group by beneficiary_sno "
						+ " ) wc left join ( "
						+ " SELECT beneficiary_sno, beneficiary_NAME ,  SUM "
						+ "			( A.DMD_FOR_MTH_WC ) AS ledger_demand, "
						+ "			office_id  "
						+ "		FROM "
						+ "			PMS_DCB_LEDGER_ACTUAL A  "
						+ "		WHERE "
						+ "			A.MONTH = "+m+" "
						+ "			AND A.YEAR = "+y+"  "
						+ "			AND office_id :: INT = "+id+"  "
						+ "		GROUP BY "
						+ "			office_id , "
						+ "			beneficiary_sno, "
						+ "			beneficiary_NAME "
						+ " ) ledger on ledger.beneficiary_sno = wc.beneficiary_sno "
						+ " "
						+ " order by abs (wc_dmd -  ledger_demand)  "
						+ " ) as fin where difference <> 0 ; ";
						
				
				
				ps=con.prepareStatement(sql);

				System.out.println("Beneficiary Wise Demand Check Query $$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("beneficiary_name")+"</beneficiary_name>  <beneficiary_sno>"+rs.getString("beneficiary_sno")+"</beneficiary_sno> <difference>"+rs.getString("difference")+" </difference></count>";
					System.out.println("Ben is "+rs.getString("beneficiary_name")+"and difference is "+rs.getString("difference"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			
			
			
		}
		
		else if(command.equalsIgnoreCase("rldcheck_dmd"))
		{
			xml=xml+"<response><command>rldcheck_dmd</command>";
			int y=Integer.parseInt(request.getParameter("year"));
			int m=Integer.parseInt(request.getParameter("month"));
			int id=Integer.parseInt(request.getParameter("Office_id"));
			try
			{
				sql="select BENEFICIARY_NAME,Diff,BENEFICIARY_SNO from (SELECT (SUM(a.WC_MTH_TOTAL) - SUM( b.WC_MTH_TOTAL)) AS Diff,a.beneficiary_sno,c.BENEFICIARY_NAME FROM (SELECT bill_sno,WC_MTH_TOTAL, beneficiary_sno FROM pms_dcb_trn_bill_dmd  WHERE bill_month     =?  AND bill_year        =?  AND beneficiary_sno IN  (SELECT beneficiary_sno  FROM pms_dcb_mst_beneficiary  WHERE status ='L'  AND office_id=?  ) )a LEFT OUTER JOIN (SELECT BILL_SNO ,  beneficiary_sno, SUM(WC_MTH_TOTAL) AS WC_MTH_TOTAL FROM pms_dcb_trn_bill_dmd_sch  group by BILL_SNO, beneficiary_sno )b ON a.bill_sno  =b.bill_sno AND b.beneficiary_sno=a.beneficiary_sno LEFT OUTER JOIN (SELECT beneficiary_sno ,BENEFICIARY_NAME   FROM pms_dcb_mst_beneficiary  WHERE status ='L'  AND office_id=?  )c ON a.beneficiary_sno        =c.beneficiary_sno GROUP BY a.beneficiary_sno, c.BENEFICIARY_NAME ) as opt1 where diff not in (0)";
				ps=con.prepareStatement(sql);
				ps.setInt(1,m);
				ps.setInt(2,y);
				ps.setInt(3,id);
				ps.setInt(4,id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("beneficiary_name")+"</beneficiary_name> <Diff>"+rs.getString("Diff")+"</Diff> <BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO")+"</BENEFICIARY_SNO> </count>";
					System.out.println("Ben is"+rs.getString("beneficiary_name")+"and diff is "+rs.getString("Diff"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		else if(command.equalsIgnoreCase("rldcheck_main_ob_coll"))
		{
			xml=xml+"<response><command>rldcheck_main_ob_coll</command>";
			int y=Integer.parseInt(request.getParameter("year"));
			int m=Integer.parseInt(request.getParameter("month"));
			int id=Integer.parseInt(request.getParameter("Office_id"));
			try
			{
				sql="select BENEFICIARY_NAME,Diff,BENEFICIARY_SNO from ( SELECT (SUM(a.coln_maint) + SUM(a.ob_maint_charges) - SUM( b.ob_maint_charges) - SUM( b.coln_maint) ) AS Diff,a.beneficiary_sno,c.BENEFICIARY_NAME FROM   (SELECT bill_sno,ob_maint_charges,coln_maint,beneficiary_sno FROM pms_dcb_trn_bill_dmd  WHERE bill_month     =? AND bill_year        =?  AND beneficiary_sno IN(SELECT beneficiary_sno  FROM pms_dcb_mst_beneficiary  WHERE status ='L'  AND office_id=?  ) )a LEFT OUTER JOIN (SELECT BILL_SNO ,  beneficiary_sno, SUM(ob_maint_charges) AS ob_maint_charges, SUM(coln_maint) AS coln_maint  FROM pms_dcb_trn_bill_dmd_sch  group by BILL_SNO, beneficiary_sno  )b ON a.bill_sno  =b.bill_sno AND b.beneficiary_sno=a.beneficiary_sno LEFT OUTER JOIN  (SELECT beneficiary_sno ,BENEFICIARY_NAME  FROM pms_dcb_mst_beneficiary  WHERE status ='L'    AND office_id=?  )c ON a.beneficiary_sno   =c.beneficiary_sno group by a.beneficiary_sno, c.BENEFICIARY_NAME ) as opt1 where diff not in (0)";
				ps=con.prepareStatement(sql);
				ps.setInt(1,m);
				ps.setInt(2,y);
				ps.setInt(3,id);
				ps.setInt(4,id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("beneficiary_name")+"</beneficiary_name> <Diff>"+rs.getString("Diff")+"</Diff> <BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO")+"</BENEFICIARY_SNO> </count>";
					System.out.println("Ben is"+rs.getString("beneficiary_name")+"and diff is "+rs.getString("Diff"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		else if(command.equalsIgnoreCase("rldcheck_int_out_coll"))
		{
			xml=xml+"<response><command>rldcheck_int_out_coll</command>";
			int y=Integer.parseInt(request.getParameter("year"));
			int m=Integer.parseInt(request.getParameter("month"));
			int id=Integer.parseInt(request.getParameter("Office_id"));
			try
			{
				sql="select BENEFICIARY_NAME,BENEFICIARY_SNO,Diff from ( SELECT (SUM(a.ob_int_amt_wc) + SUM(a.COLN_INT_WC) - SUM( b.COLN_INT_WC) - SUM( b.ob_int_amt_wc) ) AS Diff,a.beneficiary_sno,c.BENEFICIARY_NAME FROM (SELECT bill_sno, COLN_INT_WC,ob_int_amt_wc, beneficiary_sno  FROM pms_dcb_trn_bill_dmd WHERE bill_month     =?  AND bill_year        =?  AND beneficiary_sno IN  (SELECT beneficiary_sno  FROM pms_dcb_mst_beneficiary    WHERE status ='L' AND office_id=? ) )a LEFT OUTER JOIN  (SELECT BILL_SNO ,  beneficiary_sno, SUM(COLN_INT_WC) AS COLN_INT_WC, SUM(ob_int_amt_wc) AS ob_int_amt_wc  FROM pms_dcb_trn_bill_dmd_sch  group by BILL_SNO, beneficiary_sno)b ON a.bill_sno  =b.bill_sno AND b.beneficiary_sno=a.beneficiary_sno LEFT OUTER JOIN (SELECT beneficiary_sno ,BENEFICIARY_NAME  FROM pms_dcb_mst_beneficiary  WHERE status ='L'  AND office_id=? )c ON a.beneficiary_sno        =c.beneficiary_sno group by a.beneficiary_sno, c.BENEFICIARY_NAME) as opt1 where diff not in (0)";
				ps=con.prepareStatement(sql);
				ps.setInt(1,m);
				ps.setInt(2,y);
				ps.setInt(3,id);
				ps.setInt(4,id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("beneficiary_name")+"</beneficiary_name> <Diff>"+rs.getString("Diff")+"</Diff> <BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO")+"</BENEFICIARY_SNO>  </count>";
					System.out.println("Ben is"+rs.getString("beneficiary_name")+"and diff is "+rs.getString("Diff"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		else if(command.equalsIgnoreCase("rldcheck_int_dmd"))
		{
			xml=xml+"<response><command>rldcheck_int_dmd</command>";
			int y=Integer.parseInt(request.getParameter("year"));
			int m=Integer.parseInt(request.getParameter("month"));
			int id=Integer.parseInt(request.getParameter("Office_id"));
			try
			{
		//	sql="select BENEFICIARY_NAME,Diff,BENEFICIARY_SNO from (SELECT (SUM(a.DMD_INT_FOR_MTH_WC) - SUM( b.k) ) AS Diff,a.beneficiary_sno,c.BENEFICIARY_NAME FROM   (SELECT bill_sno, DMD_INT_FOR_MTH_WC, beneficiary_sno  FROM pms_dcb_trn_bill_dmd WHERE bill_month     =? AND bill_year        =?  AND beneficiary_sno IN  (SELECT beneficiary_sno  FROM pms_dcb_mst_beneficiary WHERE status ='L' AND office_id=?    )  )a LEFT OUTER JOIN ( SELECT BILL_SNO, beneficiary_sno ,k FROM ( SELECT BILL_SNO , beneficiary_sno, (cb_int_amt_wc-ob_int_amt_wc+COLN_INT_WC) AS k, ROW_NUMBER() OVER (PARTITION BY BILL_SNO,cb_int_amt_wc,ob_int_amt_wc ORDER BY BILL_SNO) num  FROM pms_dcb_trn_bill_dmd_sch ) inn WHERE inn.num = 1 )b ON a.bill_sno        =b.bill_sno AND b.beneficiary_sno=a.beneficiary_sno LEFT OUTER JOIN  (SELECT beneficiary_sno ,BENEFICIARY_NAME  FROM pms_dcb_mst_beneficiary  WHERE status ='L'  AND office_id=?  )c ON a.beneficiary_sno        =c.beneficiary_sno group by a.beneficiary_sno, c.BENEFICIARY_NAME ) where diff not in (0)";
			sql="SELECT BENEFICIARY_NAME,  Diff,  BENEFICIARY_SNO FROM (SELECT (SUM(a.DMD_INT_FOR_MTH_WC) - SUM( b.DMD_INT_FOR_MTH_WC) ) AS Diff, a.beneficiary_sno, c.BENEFICIARY_NAME FROM (SELECT bill_sno,DMD_INT_FOR_MTH_WC,beneficiary_sno   FROM pms_dcb_trn_bill_dmd   WHERE bill_month     =?  AND bill_year   =?  )a  LEFT OUTER JOIN  (select beneficiary_sno,sum(DMD_INT_FOR_MTH_WC) as DMD_INT_FOR_MTH_WC  from PMS_DCB_TRN_CB_MONTHLY where  FIN_YEAR=? and MONTH=? GROUP BY beneficiary_sno )b  ON  b.beneficiary_sno=a.beneficiary_sno LEFT OUTER JOIN  (SELECT beneficiary_sno , BENEFICIARY_NAME  FROM pms_dcb_mst_beneficiary   WHERE status ='L'  AND office_id=? )c ON a.beneficiary_sno =c.beneficiary_sno GROUP BY a.beneficiary_sno, c.BENEFICIARY_NAME ) as opt1 WHERE diff NOT IN (0) ";

				ps=con.prepareStatement(sql);
				ps.setInt(1,m);
				ps.setInt(2,y);
				ps.setInt(3,y);
				ps.setInt(4,m);
				ps.setInt(5,id);
			
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("beneficiary_name")+"</beneficiary_name> <Diff>"+rs.getString("Diff")+"</Diff> <BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO")+"</BENEFICIARY_SNO> </count>";
					System.out.println("Ben is"+rs.getString("beneficiary_name")+"and diff is "+rs.getString("Diff"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		
		else if(command.equalsIgnoreCase("Setting"))
		{
			xml=xml+"<response><command>Setting</command>";
			int off=Integer.parseInt(request.getParameter("off"));
			System.out.println("off is"+off);
			try
			{
				sql="select month,year from PMS_DCB_SETTING where OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,off);
				
				rs=ps.executeQuery();
				System.out.println("Query$$$"+sql);
				while(rs.next())
				{
					xml=xml+"<month>"+rs.getString("month")+"</month>";
					xml=xml+"<year>"+rs.getString("year")+"</year>";

	//				OFFICE_STATUS_ID  
				
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			}
		
		
		else if(command.equalsIgnoreCase("check2"))
		{
			xml=xml+"<response><command>check2</command>";
			int def_div=Integer.parseInt(request.getParameter("def_div"));
			try
			{
				sql="select Office_Name from COM_MST_OFFICES where Office_id=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,def_div);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<def_name>"+rs.getString("OFFICE_NAME")+"</def_name>";
								
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			}
		
		
		else if(command.equalsIgnoreCase("bencode1"))
		{
			xml=xml+"<response><command>bencode1</command>";
			int bencode1=Integer.parseInt(request.getParameter("bencode1"));
			int office_id=Integer.parseInt(request.getParameter("office_id"));
			
			try
			{
				sql="SELECT OLD_OFFICE_ID,BENEFICIARY_NAME ,OFFICE_ID,BENEFICIARY_TYPE_ID,OTHERS_PRIVATE_SNO,VILLAGE_PANCHAYAT_SNO,URBANLB_SNO,(SELECT OFFICE_NAME FROM com_mst_all_offices_view WHERE OFFICE_ID=PMS_DCB_MST_BENEFICIARY.OFFICE_ID ) AS OFFICE , (SELECT OFFICE_NAME FROM COM_MST_OFFICES WHERE OFFICE_ID=PMS_DCB_MST_BENEFICIARY.OLD_OFFICE_ID ) AS OLD_OFFICE , (SELECT OFFICE_STATUS_ID FROM COM_MST_OFFICES WHERE OFFICE_ID=PMS_DCB_MST_BENEFICIARY.OLD_OFFICE_ID ) AS OLD_OFFICE_STATUS , (SELECT BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE WHERE BEN_TYPE_ID=PMS_DCB_MST_BENEFICIARY.BENEFICIARY_TYPE_ID ) AS BEN_TYPE_DESC FROM PMS_DCB_MST_BENEFICIARY WHERE BENEFICIARY_SNO=? AND STATUS ='L'AND OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,bencode1);
				ps.setInt(2,office_id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				
				if(rs.next())
				{
					xml=xml+"<OFFICE>"+rs.getString("OFFICE")+"</OFFICE>";
					xml=xml+"<OLD_OFFICE>"+rs.getString("OLD_OFFICE")+"</OLD_OFFICE>";
					xml=xml+"<BEN_TYPE_DESC>"+rs.getString("BEN_TYPE_DESC")+"</BEN_TYPE_DESC>";
					xml=xml+"<OTHERS_PRIVATE_SNO>"+rs.getString("OTHERS_PRIVATE_SNO")+"</OTHERS_PRIVATE_SNO>";
					xml=xml+"<VILLAGE_PANCHAYAT_SNO>"+rs.getString("VILLAGE_PANCHAYAT_SNO")+"</VILLAGE_PANCHAYAT_SNO>";
					xml=xml+"<URBANLB_SNO>"+rs.getString("URBANLB_SNO")+"</URBANLB_SNO>";
					xml=xml+"<BENEFICIARY_NAME>"+rs.getString("BENEFICIARY_NAME")+"</BENEFICIARY_NAME>";
					xml=xml+"<OLD_OFFICE_STATUS>"+rs.getString("OLD_OFFICE_STATUS")+"</OLD_OFFICE_STATUS>";
				
					xml=xml+"<flag>success</flag></response>";
				}else
				{
					xml=xml+"<flag>NO_DATA</flag></response>";
				}
				
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		else if(command.equalsIgnoreCase("CheckData"))
		{
			xml=xml+"<response><command>CheckData</command>";
			int y=Integer.parseInt(request.getParameter("y"));
			int m=Integer.parseInt(request.getParameter("m"));
			int id=Integer.parseInt(request.getParameter("id"));
			try
			{
				sql="select b.beneficiary_name from PMS_DCB_TRN_BILL_DMD a,PMS_DCB_MST_BENEFICIARY b where a.office_id=? and a.BILL_MONTH=? and a.BILL_YEAR=? and a.COLN_MAINT is null and a.BENEFICIARY_SNO=b.BENEFICIARY_SNO";
				ps=con.prepareStatement(sql);
				ps.setInt(1,id);
				ps.setInt(2,m);
				ps.setInt(3,y);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("beneficiary_name")+"</beneficiary_name></count>";
					System.out.println("Ben is"+rs.getString("beneficiary_name"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		else if(command.equalsIgnoreCase("CheckMiss"))
		{
			xml=xml+"<response><command>CheckMiss</command>";
			int y=Integer.parseInt(request.getParameter("y"));
			int m=Integer.parseInt(request.getParameter("m"));
			int id=Integer.parseInt(request.getParameter("id"));
			try
			{
				sql="select BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where OFFICE_ID=? and STATUS='L' and BENEFICIARY_SNO not in (select BENEFICIARY_SNO from PMS_DCB_TRN_BILL_DMD where OFFICE_ID=? and BILL_MONTH=? and BILL_YEAR=?)";
				ps=con.prepareStatement(sql);
				ps.setInt(1,id);
				ps.setInt(2,id);
				ps.setInt(3,m);
				ps.setInt(4,y);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					xml=xml+"<count><beneficiary_name>"+rs.getString("BENEFICIARY_NAME")+"</beneficiary_name></count>";
					System.out.println("Ben is"+rs.getString("beneficiary_name"));
				}				
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
	//	else if(command.equalsIgnoreCase("CheckDup"))
	//	{
	//		xml=xml+"<response><command>CheckDup</command>";
	//		int y=Integer.parseInt(request.getParameter("y"));
	//		int m=Integer.parseInt(request.getParameter("m"));
	//		int id=Integer.parseInt(request.getParameter("id"));
	//		try
	//		{
	//			sql="SELECT beneficiary_name FROM pms_dcb_mst_beneficiary WHERE  office_id =? AND status = 'L' AND beneficiary_sno  IN ( SELECT beneficiary_sno FROM  pms_dcb_trn_bill_dmd  WHERE office_id =? AND bill_month =? AND bill_year =? group by beneficiary_sno HAVING COUNT(beneficiary_sno) > 1  )";
	//			ps=con.prepareStatement(sql);
	//			ps.setInt(1,id);
	//			ps.setInt(2,id);
	//			ps.setInt(3,m);
	//			ps.setInt(4,y);
	//			System.out.println("Query$$$"+sql);
	//			rs=ps.executeQuery();
				
	//			while(rs.next())
	//			{
	//				xml=xml+"<count><beneficiary_name>"+rs.getString("BENEFICIARY_NAME")+"</beneficiary_name></count>";
	//				System.out.println("Ben is"+rs.getString("beneficiary_name"));
	//			}				
	//			xml=xml+"<flag>success</flag></response>";
	//		}catch(Exception e)
	//		{
	//			System.out.println("Error:"+e);
	//			xml=xml+"<flag>failed</flag></response>";
	//		}
	//		}
		
		
		
		else if(command.equalsIgnoreCase("CheckDupdel"))
		{
			int rows=0;
			int rows1=0;
			xml=xml+"<response><command>CheckDupdel</command>";
			int y=Integer.parseInt(request.getParameter("y"));
			int m=Integer.parseInt(request.getParameter("m"));
			int id=Integer.parseInt(request.getParameter("id"));
			try
			{
				sql="  Delete from PMS_DCB_TRN_BILL_DMD_SCH where  office_id= ? and BILL_SNO in ( select BILL_SNO from PMS_DCB_TRN_BILL_DMD where  office_id = ? AND BILL_MONTH = ? AND BILL_YEAR = ? and beneficiary_sno in (SELECT beneficiary_sno  FROM pms_dcb_trn_bill_dmd  WHERE office_id    = ?  AND BILL_MONTH     =? AND BILL_YEAR      = ?   GROUP BY beneficiary_sno  HAVING COUNT(beneficiary_sno) > 1) ) ";
				ps=con.prepareStatement(sql);
				ps.setInt(1,id);
				ps.setInt(2,id);
				ps.setInt(3,m);
				ps.setInt(4,y);
				ps.setInt(5,id);
				ps.setInt(6,m);
				ps.setInt(7,y);
				rows1=ps.executeUpdate();   
			 	System.out.println("sql:"+sql);
	           	System.out.println("rows affected:"+rows1);
	        	
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				rows=0;
			}
			try
			{
				sql="  Delete from PMS_DCB_TRN_BILL_DMD where  office_id   = ?  AND BILL_MONTH     =? AND BILL_YEAR     = ?    and beneficiary_sno in (SELECT beneficiary_sno  FROM pms_dcb_trn_bill_dmd  WHERE office_id    = ?  AND BILL_MONTH     =? AND BILL_YEAR      = ?   GROUP BY beneficiary_sno  HAVING COUNT(beneficiary_sno) > 1) ";
				ps=con.prepareStatement(sql);
				ps.setInt(1,id);
				ps.setInt(2,m);
				ps.setInt(3,y);
				ps.setInt(4,id);
				ps.setInt(5,m);
				ps.setInt(6,y);
				 rows=ps.executeUpdate();  
				System.out.println("rows affected:"+rows);
	           	System.out.println("sql:"+sql);
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				rows1=0;
				}
			
			if(rows1>0 && rows>0)
			{
				xml=xml+"<flag>success</flag></response>";
				
			}else
			{
				xml=xml+"<flag>failed</flag></response>";
			}
				
			}
		
		
		
		else if(command.equalsIgnoreCase("bensch1"))
		{
			xml=xml+"<response><command>bensch1</command>";
			int bencode1=Integer.parseInt(request.getParameter("bencode1"));
			int office_id=Integer.parseInt(request.getParameter("office_id"));
			
			try
			{
				sql="select distinct SCHEME_SNO ,(SELECT SCH_NAME from PMS_SCH_MASTER WHERE SCH_SNO=PMS_DCB_MST_BENEFICIARY_METRE.SCHEME_SNO) AS Scheme from PMS_DCB_MST_BENEFICIARY_METRE where BENEFICIARY_SNO=? and METER_STATUS='L' and OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,bencode1);
				ps.setInt(2,office_id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<count><Scheme>"+rs.getString("Scheme")+"</Scheme></count>";
					
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			}
		
		else if(command.equalsIgnoreCase("CheckDatadel"))
		{
			int rows=0;
			int rows1=0;
			xml=xml+"<response><command>CheckDatadel</command>";
			int y=Integer.parseInt(request.getParameter("y"));
			int m=Integer.parseInt(request.getParameter("m"));
			int id=Integer.parseInt(request.getParameter("id"));
			try
			{
				sql="  Delete from PMS_DCB_TRN_BILL_DMD_SCH where  office_id    = ? and BILL_SNO in ( select BILL_SNO from PMS_DCB_TRN_BILL_DMD where  office_id    = ? AND BILL_MONTH     = ? AND BILL_YEAR      = ? AND COLN_MAINT  IS NULL) ";
				ps=con.prepareStatement(sql);
				ps.setInt(1,id);
				ps.setInt(2,id);
				ps.setInt(3,m);
				ps.setInt(4,y);
				rows1=ps.executeUpdate();   
			 	System.out.println("sql:"+sql);
	           	System.out.println("rows affected:"+rows1);
	        	
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				rows=0;
			}
			try
			{
				sql="  Delete from PMS_DCB_TRN_BILL_DMD where  office_id    = ? AND BILL_MONTH     = ? AND BILL_YEAR      = ? AND COLN_MAINT  IS NULL ";
				ps=con.prepareStatement(sql);
				ps.setInt(1,id);
				ps.setInt(2,m);
				ps.setInt(3,y);
				 rows=ps.executeUpdate();  
				System.out.println("rows affected:"+rows);
	           	System.out.println("sql:"+sql);
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				rows1=0;
				}
			
			if(rows1>0 && rows>0)
			{
				xml=xml+"<flag>success</flag></response>";
				
			}else
			{
				xml=xml+"<flag>failed</flag></response>";
			}
				
			}
		else if (command.equalsIgnoreCase("delete"))
         {
       	  System.out.println("Inside delete");
       	xml=xml+"<response><command>delete</command>";
       	int  month=Integer.parseInt(request.getParameter("month")); 
       	int  year=Integer.parseInt(request.getParameter("year")); 
       	int  office_id=Integer.parseInt(request.getParameter("office_id")); 
       	
       	  try
             {
           	 sql="delete from PMS_DCB_LEDGER_ACTUAL   where office_id=? and MONTH=? and YEAR=? and office_id not in (select ACCOUNTING_FOR_OFFICE_ID from PMS_DCB_DATA_FREEZE where CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and DCB_FREEZE = 'Y' and accounting_for_office_id=PMS_DCB_LEDGER_ACTUAL.office_id)" ;            	
           	ps=con.prepareStatement(sql);
           	ps.setInt(1,office_id);
           	ps.setInt(2,month);
           	ps.setInt(3,year);
           	ps.setInt(4,month);
           	ps.setInt(5,year);
           	System.out.println("Query$$$"+sql);
           	int rows=ps.executeUpdate();     
           	System.out.println("rows affected:"+rows);
           
           	  	if(rows>0)
           	  	{
           	  	xml =xml + "<flag>success</flag></response>";
           	 System.out.println("rows affected:"+rows);
           	 	}else
           	 	{
           	 	 xml =xml + "<flag>failure</flag></response>";
           	 	}
             }              
             catch(Exception e)  
             {
            	 xml =xml + "<flag>error</flag></response>";
            	 System.out.println("Error:"+e);
             }
       	         }
            
		
		else if (command.equalsIgnoreCase("Delete_Demand"))
        {
      	  System.out.println("Inside Delete_Demand");
      	xml=xml+"<response><command>Delete_Demand</command>";
      	int  month=Integer.parseInt(request.getParameter("month")); 
      	int  year=Integer.parseInt(request.getParameter("year")); 
      	int  Office_id=Integer.parseInt(request.getParameter("Office_id")); 
      	
      	  try
            {
          	 sql="delete from PMS_DCB_OTHER_CHARGES where office_id=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? " ;            	
          	ps=con.prepareStatement(sql);
          	ps.setInt(1,Office_id);
          	ps.setInt(2,month);
          	ps.setInt(3,year);
          	System.out.println("Query$$$"+sql);
          	ps.executeUpdate();     
          	xml =xml + "<flag>success</flag></response>";
          	           }              
            catch(Exception e)  
            {
           	 xml =xml + "<flag>error</flag></response>";
           	 System.out.println("Error:"+e);
            }
      	         }
		
		
		
		else if(command.equalsIgnoreCase("getname"))
		{
			xml=xml+"<response><command>getname</command>";
			int New_off_id=Integer.parseInt(request.getParameter("New_off_id"));
			try
			{
				sql="select * from COM_MST_OFFICES where OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,New_off_id);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<New_off_name>"+rs.getString("OFFICE_NAME")+"</New_off_name>";
							
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			}

		
		else if(command.equalsIgnoreCase("getscheme"))
		{
			xml=xml+"<response><command>getscheme</command>";
			int New_scheme=Integer.parseInt(request.getParameter("New_scheme"));
			try
			{
				sql="select distinct scheme_sno, (select SUBSTR(sch_name ,1,25) from pms_sch_master where sch_sno=PMS_DCB_MST_BENEFICIARY_METRE.scheme_sno) as sch_name from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno =? and    METER_STATUS='L'";
				ps=con.prepareStatement(sql);
				ps.setInt(1,New_scheme);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml=xml+"<sch_name>"+rs.getString("sch_name")+"</sch_name>";
							
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			}
		
		
		else if(command.equalsIgnoreCase("yearValidation"))
		{
			xml=xml+"<response><command>yearValidation</command>";
		
			try
			{
				sql=" SELECT  to_char(now(),'DD') AS DAYS, to_char(now(),'MM') AS mon, to_char(now(),   'YYYY') AS yr,clock_timestamp() As Da ";
				ps=con.prepareStatement(sql);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
			
	                //    day=rs.getInt("DAYS");
	                 
					
					xml=xml+"<Year>"+rs.getInt("yr")+"</Year>";
					xml=xml+"<month>"+rs.getInt("mon")+"</month>";
					xml=xml+"<Da>"+rs.getString("Da")+"</Da>";

	
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			}

		else if(command.equalsIgnoreCase("getdata5"))
		 {
			System.out.println("reached getdata5------->");
			xml=xml+"<response><command>getdata5</command>";
			int new_id=Integer.parseInt(request.getParameter("new_id"));
			
			System.out.println("new_id"+new_id);
			
			try
			{
				sql="SELECT OFFICE_NAME ,OFFICE_STATUS_ID FROM COM_MST_OFFICES WHERE OFFICE_ID = ?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,new_id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
	    			xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME").trim()+"</OFFICE_NAME>";
					xml=xml+"<OFFICE_STATUS_ID>"+rs.getString("OFFICE_STATUS_ID").trim()+"</OFFICE_STATUS_ID>";
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			
			System.out.println("xml----->"+xml);
				}
		
		
		else if(command.equalsIgnoreCase("getdata1"))
		 {
			System.out.println("reached getdata1------->");
			xml=xml+"<response><command>getdata1</command>";
			int new_id=Integer.parseInt(request.getParameter("new_id"));
			
			System.out.println("new_id"+new_id);
			
			try
			{
				sql="SELECT OFFICE_NAME ,OFFICE_STATUS_ID FROM COM_MST_OFFICES WHERE OFFICE_ID = ?";

			//	sql="select distinct SUB_DIV_ID , (select OFFICE_NAME from COM_MST_OFFICES )as office_name , (select OFFICE_STATUS_ID from COM_MST_OFFICES )as OFFICE_STATUS_ID from PMS_DCB_MST_BENEFICIARY_METRE where Sub_div_id = ? and  METER_STATUS='L'";
				
				
				ps=con.prepareStatement(sql);
				ps.setInt(1,new_id);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
	    			xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME").trim()+"</OFFICE_NAME>";
					xml=xml+"<OFFICE_STATUS_ID>"+rs.getString("OFFICE_STATUS_ID").trim()+"</OFFICE_STATUS_ID>";
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			
			System.out.println("xml----->"+xml);
				}
		
		
		
		else if(command.equalsIgnoreCase("getname4"))
		 {
			System.out.println("reached getname4------->");
			xml=xml+"<response><command>getname4</command>";
			int New_Sub_div_id=Integer.parseInt(request.getParameter("New_Sub_div_id"));
			
			System.out.println("New_Sub_div_id"+New_Sub_div_id);
			
			try
			{
				sql="select OFFICE_NAME,OFFICE_STATUS_ID from COM_MST_OFFICES where OFFICE_ID=? ";
				ps=con.prepareStatement(sql);
				ps.setInt(1,New_Sub_div_id);
				
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
					

	    			xml=xml+"<SUB_NAME>"+rs.getString("OFFICE_NAME").trim()+"</SUB_NAME>";
					xml=xml+"<SUB_STATUS_ID>"+rs.getString("OFFICE_STATUS_ID").trim()+"</SUB_STATUS_ID>";
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
			
			System.out.println("xml----->"+xml);
				}
		
		
		
		
			else if(command.equalsIgnoreCase("loadgrid"))
		{
			System.out.println("reached loadgrid");
			xml=xml+"<response><command>loadgrid</command>";
			int frm_off=Integer.parseInt(request.getParameter("frm_off"));
//			String name=request.getParameter("sname");
			System.out.println("name--->"+frm_off);
			
			try
			{
		//		PMS_DCB_MST_BENEFICIARY_METRE
	    //      sql="select distinct SUB_DIV_ID from PMS_DCB_MST_BENEFICIARY_METRE where office_id=? and METER_STATUS='L'";
		//		sql="select distinct SUB_DIV_ID, (select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as office_name from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID = ? and METER_STATUS='L' ";
				sql="select distinct SUB_DIV_ID , (select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as office_name , (select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID) as OFFICE_LEVEL_ID , (select OFFICE_STATUS_ID from COM_MST_OFFICES where OFFICE_ID=PMS_DCB_MST_BENEFICIARY_METRE.SUB_DIV_ID)as OFFICE_STATUS_ID from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID = ? and  METER_STATUS='L'";

				ps=con.prepareStatement(sql);
				ps.setInt(1,frm_off);
//				ps.setString(1, name);
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();
				while(rs.next())
				{
	    			xml=xml+"<count><SUB_DIV_ID>"+rs.getString("SUB_DIV_ID").trim()+"</SUB_DIV_ID>";
					xml=xml+"<OFFICE_NAME>"+rs.getString("OFFICE_NAME").trim()+"</OFFICE_NAME>";
					xml=xml+"<OFFICE_STATUS_ID>"+rs.getString("OFFICE_STATUS_ID").trim()+"</OFFICE_STATUS_ID></count>";
				}
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
			}
//			System.out.println("xml--->"+xml);
		}
		
			else if(command.equalsIgnoreCase("loadgrid1"))
			{
				System.out.println("reached loadgrid1");
				xml=xml+"<response><command>loadgrid1</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				System.out.println("name--->"+frm_off);
				
				try
				{
			
					sql="select distinct scheme_sno, (select SUBSTR(sch_name ,1,60) from pms_sch_master where sch_sno=PMS_DCB_MST_BENEFICIARY_METRE.scheme_sno) as sch_name from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID =? and    METER_STATUS='L'";

					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
						xml=xml+"<count><scheme_sno>"+rs.getString("scheme_sno").trim()+"</scheme_sno>";
						xml=xml+"<sch_name>"+rs.getString("sch_name").trim()+"</sch_name></count>";
						
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}

			}

		
			else if(command.equalsIgnoreCase("loadgrid4"))
			{
				System.out.println("reached loadgrid4");
				xml=xml+"<response><command>loadgrid4</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				System.out.println("name--->"+frm_off);
				
				try
				{
			
           		sql="select distinct scheme_sno, (select SUBSTR(sch_name ,1,60) from pms_sch_master where sch_sno=PMS_DCB_MST_BENEFICIARY_METRE.scheme_sno) as sch_name from PMS_DCB_MST_BENEFICIARY_METRE where OFFICE_ID =? and    METER_STATUS='L'";

					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
						xml=xml+"<count><scheme_sno>"+rs.getString("scheme_sno").trim()+"</scheme_sno>";
						xml=xml+"<sch_name>"+rs.getString("sch_name").trim()+"</sch_name></count>";
						
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}

		}

				
			else if(command.equalsIgnoreCase("getid"))
			{
				System.out.println("reached getid");

				xml=xml+"<response><command>getid</command>";
				int off_id=Integer.parseInt(request.getParameter("off_id"));
			//	int selmonth=Integer.parseInt(request.getParameter("selmonth"));
			//	int year=Integer.parseInt(request.getParameter("year"));
				
				try
				{
					sql="select CLOSED_OFFICE_ID,(select office_name from com_mst_offices where office_id=PMS_DCB_CLOSED_OFFICE_MAP.CLOSED_OFFICE_ID)office_name from PMS_DCB_CLOSED_OFFICE_MAP where ACTIVE_OFFICE_ID=?";
					ps=con.prepareStatement(sql);
				
					ps.setInt(1,off_id);
					rs=ps.executeQuery();
				
					while(rs.next())
					{
						xml=xml+"<count><old_office_id>"+rs.getString("CLOSED_OFFICE_ID")+"</old_office_id></count>";
						xml=xml+"<count><office_name>"+rs.getString("office_name")+"</office_name></count>";
				
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				}
			
		
			else if(command.equalsIgnoreCase("check1"))
			{
				System.out.println("reached check1");

				xml=xml+"<response><command>check1</command>";
				int def_div=Integer.parseInt(request.getParameter("def_div"));
				int month=Integer.parseInt(request.getParameter("month"));
				int year=Integer.parseInt(request.getParameter("year"));
				
				try
				
				{
					sql="SELECT COUNT(DISTINCT beneficiary_sno)AS twad FROM PMS_DCB_LEDGER_ACTUAL WHERE OFFICE_ID =? AND YEAR =? AND MONTH =? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,def_div);
					ps.setInt(2,year);
					ps.setInt(3,month);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
		    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				}
		
		
			else if(command.equalsIgnoreCase("closeddata"))
			{
				System.out.println("reached closeddata");

				xml=xml+"<response><command>closeddata</command>";
				int New_off_id=Integer.parseInt(request.getParameter("New_off_id"));
				int closed_off_id=Integer.parseInt(request.getParameter("closed_off_id"));
				int month=Integer.parseInt(request.getParameter("month"));
				int year=Integer.parseInt(request.getParameter("year"));
				
				try
				{

					sql = "insert into PMS_DCB_CLOSED_OFFICE_MAP (CLOSED_OFFICE_ID,CHANGE_OFFICE_ID,ACTIVE_OFFICE_ID,MONTH,YEAR) values (?,?,?,?,?)";
					ps=con.prepareStatement(sql);
					ps.setInt(1,closed_off_id);
					ps.setInt(2,New_off_id);
					ps.setInt(3,New_off_id);
					ps.setInt(4,month);
					ps.setInt(5,year);
					ps.executeQuery();
					System.out.println("insert Sucess");
					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag>";
				}
				try
				{
					sql="update PMS_DCB_CLOSED_OFFICE_MAP  set ACTIVE_OFFICE_ID=? where ACTIVE_OFFICE_ID = ? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id);
					ps.setInt(2,closed_off_id);
					System.out.println("Query$$$"+sql);
					ps.executeQuery();
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed1</flag></response>";
				 }
									
				
				}
		

			
			 if(command.equalsIgnoreCase("SetOffice"))
			{
				System.out.println("reached SetOffice");

				xml=xml+"<response><command>SetOffice</command>";
				int off=Integer.parseInt(request.getParameter("off"));
				int real=Integer.parseInt(request.getParameter("real"));		
				try
				{
				//	sql="update PMS_DCB_CLOSED_OFFICE_MAP set SWITCH_ID=? where CHANGE_OFFICE_ID=?";
				//	sql = "insert into PMS_DCB_CLOSED_OFFICE_MAP (CHANGE_OFFICE_ID,SWITCH_ID) values (?,?)";
					
					sql = "BEGIN  INSERT INTO PMS_DCB_CLOSED_OFFICE_MAP (CHANGE_OFFICE_ID) VALUES (?); EXCEPTION  WHEN DUP_VAL_ON_INDEX THEN   UPDATE PMS_DCB_CLOSED_OFFICE_MAP   SET    SWITCH_ID = ?   WHERE CHANGE_OFFICE_ID = ?; END ;";
					
					ps=con.prepareStatement(sql);
				  	ps.setInt(1,real);
					ps.setInt(2,off);
					ps.setInt(3,real);
				
					System.out.println("Query$$$"+sql);
					ps.executeQuery();
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
				 }
				}
		
		
			else if(command.equalsIgnoreCase("checkclosed"))
			{
				System.out.println("reached checkclosed");

				xml=xml+"<response><command>checkclosed</command>";
				int closed_off_id=Integer.parseInt(request.getParameter("closed_off_id"));
				int New_off_id=Integer.parseInt(request.getParameter("New_off_id"));		
				try
				{
					sql="select CLOSED_OFFICE_ID from PMS_DCB_CLOSED_OFFICE_MAP where CLOSED_OFFICE_ID=? and CHANGE_OFFICE_ID=? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,closed_off_id);
					ps.setInt(2,New_off_id);
					rs=ps.executeQuery();
				
					while(rs.next())
					{
						xml=xml+"<count><CLOSED_OFFICE_ID>"+rs.getString("CLOSED_OFFICE_ID")+"</CLOSED_OFFICE_ID></count>";
					
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				}
		
		
		
		
			else if(command.equalsIgnoreCase("get_ben_type"))
			{
				System.out.println("reached get_ben_type");

				xml=xml+"<response><command>get_ben_type</command>";
				
				int year=Integer.parseInt(request.getParameter("year"));
				int month=Integer.parseInt(request.getParameter("month"));
				int Frm_month=Integer.parseInt(request.getParameter("Frm_month"));
				int old_off_id=Integer.parseInt(request.getParameter("old_off_id"));
				int to_year=Integer.parseInt(request.getParameter("to_year"));
				System.out.println("year:"+year);
				System.out.println("old_off_id:"+old_off_id);
				try
				{
					sql="SELECT DISTINCT BEN_TYPE_ID,(SELECT BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE WHERE BEN_TYPE_ID=PMS_DCB_LEDGER_ACTUAL.BEN_TYPE_ID ) BEN_TYPE_DESC FROM PMS_DCB_LEDGER_ACTUAL WHERE TO_DATE((MONTH ||'-'|| YEAR),'mm-yyyy') BETWEEN TO_DATE(? ||'-' ||?,'mm-yyyy') AND to_date(? ||'-' ||?,'mm-yyyy') and old_office_id=?";
					ps=con.prepareStatement(sql);
					ps.setInt(1,Frm_month);
					ps.setInt(2,year);
					ps.setInt(3,month);
					ps.setInt(4,to_year);
					ps.setInt(5,old_off_id);
					rs=ps.executeQuery();
				
					while(rs.next())
					{
						xml=xml+"<count><BEN_TYPE_ID>"+rs.getString("BEN_TYPE_ID")+"</BEN_TYPE_ID></count>";
						xml=xml+"<count><BEN_TYPE_DESC>"+rs.getString("BEN_TYPE_DESC")+"</BEN_TYPE_DESC></count>";
				
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				}
		
		
			else if(command.equalsIgnoreCase("get_ben_name"))
			{
				System.out.println("reached get_ben_name");

				xml=xml+"<response><command>get_ben_name</command>";
				int year=Integer.parseInt(request.getParameter("year"));
				int month=Integer.parseInt(request.getParameter("month"));
				int Frm_month=Integer.parseInt(request.getParameter("Frm_month"));
				int old_off_id=Integer.parseInt(request.getParameter("old_off_id"));
				int to_year=Integer.parseInt(request.getParameter("to_year"));
				
				int ben_type=Integer.parseInt(request.getParameter("ben_type"));				
				
				try
				{
					sql="SELECT DISTINCT BENEFICIARY_NAME,BENEFICIARY_SNO FROM PMS_DCB_LEDGER_ACTUAL WHERE TO_DATE((MONTH  ||'-'  || YEAR),'mm-yyyy') BETWEEN TO_DATE(? ||'-' || ?,'mm-yyyy') AND to_date(?  ||'-'  ||?,'mm-yyyy') and old_office_id = ? AND ben_type_id   = ? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,Frm_month);
					ps.setInt(2,year);
					ps.setInt(3,month);
					ps.setInt(4,to_year);
					ps.setInt(5,old_off_id);
					ps.setInt(6,ben_type);
					rs=ps.executeQuery();
				
					while(rs.next())
					{
						xml=xml+"<count><BENEFICIARY_NAME>"+rs.getString("BENEFICIARY_NAME")+"</BENEFICIARY_NAME></count>";
						xml=xml+"<count><BENEFICIARY_SNO>"+rs.getString("BENEFICIARY_SNO")+"</BENEFICIARY_SNO></count>";
		
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				}
		
		
		
		
			
			else if(command.equalsIgnoreCase("getdefunt"))
			{
				System.out.println("reached getdefunt");

				xml=xml+"<response><command>getdefunt</command>";
				int off_id=Integer.parseInt(request.getParameter("off_id"));
				try
				{
					sql="select CLOSED_OFFICE_ID,(select office_name from com_mst_offices where office_id=PMS_DCB_CLOSED_OFFICE_MAP.CLOSED_OFFICE_ID)  office_name from PMS_DCB_CLOSED_OFFICE_MAP where NEW_OFFICE_ID=?";
					ps=con.prepareStatement(sql);
					ps.setInt(1,off_id);

					rs=ps.executeQuery();
					while(rs.next())
					{
						xml=xml+"<count><old_office_id>"+rs.getString("old_office_id")+"</old_office_id></count>";
						xml=xml+"<count><office_name>"+rs.getString("office_name")+"</office_name></count>";
				
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				}

			
		
		
		
		
		
		
			else if(command.equalsIgnoreCase("loadvalue"))
			{
				System.out.println("reached loadvalue");
				xml=xml+"<response><command>loadvalue</command>";
				int New_off_id=Integer.parseInt(request.getParameter("New_off_id"));
				int new_id=Integer.parseInt(request.getParameter("new_id"));
				int sub_div=Integer.parseInt(request.getParameter("sub_div"));
		//		String Da_te=request.getParameter("Da_te");
				String user_id=request.getParameter("user_id");

				
				try
				{
					sql="update PMS_DCB_MST_BENEFICIARY_METRE set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(), OLD_SUB_DIV_ID = ?, SUB_DIV_ID=? where office_id=? and sub_div_id = ?  and METER_STATUS='L' ";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
				//	ps.setString(2,Da_te);
					ps.setInt(2,sub_div);
					ps.setInt(3,new_id);
					ps.setInt(4,New_off_id);
					ps.setInt(5,sub_div);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
//					if(rs.next())
//					{
//					System.out.println("Record is updated"+rs);
//					}else{
//						System.out.println("Record is notupdated"+rs);
//					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}
					}
		
//      yearly ,monthly and ledger are extended only upto december so after dec 2018 extend it				
			else if(command.equalsIgnoreCase("Ben_Merge"))
		{
			System.out.println("reached Ben_Merge------->");

			xml=xml+"<response><command>Ben_Merge</command>";
			
			int month1=Integer.parseInt(request.getParameter("month1"));
			
			int closed_Ben=Integer.parseInt(request.getParameter("closed_Ben"));
			int open_Ben=Integer.parseInt(request.getParameter("open_Ben"));
			int Year1=Integer.parseInt(request.getParameter("Year1"));
			int month=Integer.parseInt(request.getParameter("month"));
			int Year=Integer.parseInt(request.getParameter("Year"));
			int office_id=Integer.parseInt(request.getParameter("office_id"));
			System.out.println("reached office_id------->"+office_id);
			String user_id=request.getParameter("user_id");
			try
			{
				sql="update PMS_DCB_MST_BENEFICIARY  set status='C' where BENEFICIARY_SNO = ? and OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1,closed_Ben);
				ps.setInt(2,office_id);
				System.out.println("Query$$$"+sql);
//				rs=ps.executeQuery();
				rs1=ps.executeUpdate();
				xml=xml+"<flag>success</flag>";
			}catch(Exception e){
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed1</flag>";
			 }
									
			try
			{
				sql="update PMS_DCB_MST_BENEFICIARY   set  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_BENEFICIARY_SNO= ? where BENEFICIARY_SNO = ? and OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setString(1,user_id);
				ps.setInt(2,closed_Ben);
				ps.setInt(3,open_Ben);
				ps.setInt(4,office_id);
				System.out.println("Query$$$"+sql);
//				rs=ps.executeQuery();
				rs1=ps.executeUpdate();
				xml=xml+"<flag>success</flag>";
			}catch(Exception e){
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed3</flag>";
			 }
			
			try
			{
				sql="update PMS_DCB_MST_BENEFICIARY_METRE set  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_BENEFICIARY_SNO= ?,BENEFICIARY_SNO= ? where BENEFICIARY_SNO=? and OFFICE_ID=?";
				ps=con.prepareStatement(sql);
				ps.setString(1,user_id);
				ps.setInt(2,closed_Ben);
				ps.setInt(3,open_Ben);
				ps.setInt(4,closed_Ben);
				ps.setInt(5,office_id);
				System.out.println("Query$$$"+sql);
//				rs=ps.executeQuery();
				rs1=ps.executeUpdate();
				xml=xml+"<flag>success</flag>";
			}catch(Exception e){
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed4</flag>";
			 }
			
			
			try
			{
				sql="update PMS_DCB_TARIFF_SLAB  set  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_BENEFICIARY_SNO= ?,BENEFICIARY_SNO= ? where BENEFICIARY_SNO=? and OFFICE_ID=? and active_status='A' ";
				ps=con.prepareStatement(sql);
				ps.setString(1,user_id);
				ps.setInt(2,closed_Ben);
				ps.setInt(3,open_Ben);
				ps.setInt(4,closed_Ben);
				ps.setInt(5,office_id);
				System.out.println("Query$$$"+sql);
//				rs=ps.executeQuery();
				rs1=ps.executeUpdate();
				xml=xml+"<flag>success</flag>";
			}catch(Exception e){
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed5</flag>";
			 }
			
			try
			{
				sql="update PMS_DCB_ALLOTTED set  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_BENEFICIARY_SNO= ?, BENEFICIARY_SNO= ? where BENEFICIARY_SNO=? and OFFICE_ID=? and active_status='A'";
				ps=con.prepareStatement(sql);
				ps.setString(1,user_id);
				ps.setInt(2,closed_Ben);
				ps.setInt(3,open_Ben);
				ps.setInt(4,closed_Ben);
				ps.setInt(5,office_id);
				System.out.println("Query$$$"+sql);
//				rs=ps.executeQuery();
				rs1=ps.executeUpdate();
				xml=xml+"<flag>success</flag>";
			}catch(Exception e){
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed4</flag>";
			 }
			
			try
			{
				sql="update PMS_DCB_OB_YEARLY set  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_BENEFICIARY_SNO= ?,BENEFICIARY_SNO= ? where BENEFICIARY_SNO=? and OFFICE_ID=? and month between 4 and  ?  and fin_year= ?  ";
				ps=con.prepareStatement(sql);
				ps.setString(1,user_id);
				ps.setInt(2,closed_Ben);
				ps.setInt(3,open_Ben);
				ps.setInt(4,closed_Ben);
				ps.setInt(5,office_id);
				ps.setInt(6,month);
				ps.setInt(7,Year);
				System.out.println("Query$$$"+sql);
//				rs=ps.executeQuery();
				rs1=ps.executeUpdate();
				xml=xml+"<flag>success</flag>";
			}catch(Exception e){
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed5</flag>";
			 }
			
			
			try
			{
				sql="update PMS_DCB_TRN_CB_MONTHLY  set  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_BENEFICIARY_SNO= ?, BENEFICIARY_SNO= ? where BENEFICIARY_SNO=? and OFFICE_ID=? and month between 3 and  ?  and fin_year= ?  ";
				ps=con.prepareStatement(sql);
				ps.setString(1,user_id);
				ps.setInt(2,closed_Ben);
				ps.setInt(3,open_Ben);
				ps.setInt(4,closed_Ben);
				ps.setInt(5,office_id);
				ps.setInt(6,month1);
				ps.setInt(7,Year1);
				System.out.println("Query$$$"+sql);
				rs1=ps.executeUpdate();
				xml=xml+"<flag>success</flag>";
			}catch(Exception e){
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed6</flag>";
			 }
			
			try
			{
				sql="update  PMS_DCB_LEDGER_ACTUAL"
						+ ""
						+ " set  OLD_BENEFICIARY_SNO= ?, BENEFICIARY_SNO= ? where BENEFICIARY_SNO=? and OFFICE_ID=? and month between 4 and  ?  and YEAR = ?   ";
				ps=con.prepareStatement(sql);
				ps.setInt(1,closed_Ben);
				ps.setInt(2,open_Ben);
				ps.setInt(3,closed_Ben);
				ps.setInt(4,office_id);
				ps.setInt(5,month);
				ps.setInt(6,Year);
				System.out.println("Query$$$"+sql);
//				rs=ps.executeQuery();
				rs1=ps.executeUpdate();
				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e){
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed7</flag></response>";
			 }
			
					
		}

		
			else if(command.equalsIgnoreCase("NewValue"))
			{
				System.out.println("reached NewValue------->");

				xml=xml+"<response><command>NewValue</command>";
				int month=Integer.parseInt(request.getParameter("month"));
				int year=Integer.parseInt(request.getParameter("year"));
				int Div=Integer.parseInt(request.getParameter("Div"));

				try
				{
					sql=" select DCB_FREEZE,CASHBOOK_MONTH,CASHBOOK_year from PMS_DCB_DATA_FREEZE where CASHBOOK_MONTH= ? and CASHBOOK_year=? and office_id=?";
					ps=con.prepareStatement(sql);
					ps.setInt(1,month);
					ps.setInt(2,year);
					ps.setInt(3,Div);

					rs=ps.executeQuery();
					if(rs.next())
					{
						xml=xml+"<DCB_FREEZE>"+rs.getString("DCB_FREEZE")+"</DCB_FREEZE>";
						xml=xml+"<flag>success</flag></response>";

					}else
					{
						xml=xml+"<flag>failed</flag></response>";

					}
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";

				}
			}
		
		
			else if(command.equalsIgnoreCase("SchemeProceed"))
			{
				System.out.println("reached SchemeProceed");
				xml=xml+"<response><command>SchemeProceed</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				int new_off_id=Integer.parseInt(request.getParameter("new_off_id"));
				int Year=Integer.parseInt(request.getParameter("Year"));
				int sche_no=Integer.parseInt(request.getParameter("sche_no"));
				int month=Integer.parseInt(request.getParameter("month"));
				int month1=Integer.parseInt(request.getParameter("month1"));
				int Year1=Integer.parseInt(request.getParameter("Year1"));
			//	String Da_te=request.getParameter("Da_te");
				String user_id=request.getParameter("user_id");
				int Year_min=Year-1;
				int Year_min1=Year1-1;

				
				System.out.println("frm_off"+frm_off);
				System.out.println("new_off_id"+new_off_id);
				System.out.println("month"+month);
				System.out.println("Year"+Year);
				System.out.println("month1"+month1);
				System.out.println("Year1"+Year1);

				try
				{
					sql="update PMS_DCB_MST_BENEFICIARY  set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),old_office_id=office_id,office_id=?, OLD_BENEFICIARY_SNO=BENEFICIARY_SNO where office_id=? and  beneficiary_sno in ( select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno=? and office_id=?  and METER_STATUS='L')  and STATUS='L'";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
				//	ps.setString(2,Da_te);
					ps.setInt(2,new_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,sche_no);
					ps.setInt(5,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					
					
					
					xml=xml+"<flag>success</flag>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed1</flag>";
				
			    }
				
				try
				{
					sql="update PMS_DCB_MST_BENEFICIARY_METRE set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=scheme_sno, old_office_id=office_id,office_id=?, OLD_BENEFICIARY_SNO=BENEFICIARY_SNO , OLD_METRE_SNO=METRE_SNO where office_id=? and SCHEME_SNO=? and METER_STATUS='L'  and  beneficiary_sno in ( select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno=? and office_id=?  and METER_STATUS='L') ";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
				//	ps.setString(2,Da_te);
					ps.setInt(2,new_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,sche_no);
					ps.setInt(5,sche_no);
					ps.setInt(6,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					xml=xml+"<flag>success</flag>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed2</flag>";
			    }
				
				try
				{
					sql="update PMS_DCB_ALLOTTED  set old_office_id=office_id,office_id=? where office_id=? and  beneficiary_sno in ( select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno=? and office_id=?  and METER_STATUS='L') ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,new_off_id);
					ps.setInt(2,frm_off);
					ps.setInt(3,sche_no);
					ps.setInt(4,new_off_id);
					
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed6</flag>";
			    }
				
				try
				{
					sql=" update PMS_DCB_TARIFF_SLAB set old_office_id=office_id,office_id=?, OLD_METRE_SNO=METRE_SNO, OLD_SCH_SNO=SCH_SNO,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO  where office_id=? and  beneficiary_sno in ( select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno=? and office_id=?  and METER_STATUS='L') ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,new_off_id);
					ps.setInt(2,frm_off);
					ps.setInt(3,sche_no);
					ps.setInt(4,new_off_id);
					
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed7</flag></response>";
			    }

				
				if(month<5)
				{
												
				
				try
				{
					sql="update PMS_DCB_OB_YEARLY set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(), OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO, old_office_id=office_id,office_id=? where office_id=? and month between 4 and 12 and fin_year=? and sch_sno=? and  beneficiary_sno in ( select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno=? and office_id=?  and METER_STATUS='L')";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,new_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,Year_min);
					ps.setInt(5,sche_no);
					ps.setInt(6,sche_no);
					ps.setInt(7,frm_off);

					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed3</flag>";
			    }

				
				try
				{
					sql="update PMS_DCB_OB_YEARLY set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(), OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO, old_office_id=office_id,office_id=? where office_id=? and month between 1 and 4 and fin_year=? and sch_sno=? and  beneficiary_sno in ( select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno=? and office_id=?  and METER_STATUS='L')";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,new_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,Year);
					ps.setInt(5,sche_no);
					ps.setInt(6,sche_no);
					ps.setInt(7,frm_off);

					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed4</flag>";
			    }
				
				try
				{
					sql="update PMS_DCB_TRN_CB_MONTHLY  set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO , old_office_id=office_id,office_id=? where office_id=? and month between 4 and 12 and fin_year=? and sch_sno=? and  beneficiary_sno in ( select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno=? and office_id=?  and METER_STATUS='L')";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,new_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,Year_min1);
					ps.setInt(5,sche_no);
					ps.setInt(6,sche_no);
					ps.setInt(7,frm_off);

					System.out.println("Query$$$"+sql);
					rs1=ps.executeUpdate();


					xml=xml+"<flag>success</flag>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed5</flag>";
			    }
				
				try
				{
					sql="update PMS_DCB_TRN_CB_MONTHLY  set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO , old_office_id=office_id,office_id=? where office_id=? and month between 1 and 3 and fin_year=? and sch_sno=? and  beneficiary_sno in ( select distinct beneficiary_sno from PMS_DCB_MST_BENEFICIARY_METRE where scheme_sno=? and office_id=?  and METER_STATUS='L')";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,new_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,Year1);
					ps.setInt(5,sche_no);
					ps.setInt(6,sche_no);
					ps.setInt(7,frm_off);

					System.out.println("Query$$$"+sql);
					rs1=ps.executeUpdate();


					xml=xml+"<flag>success</flag>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed6</flag>";
			    }
				}
				
				if(month>4)
				{
					try
					{
						
					sql="UPDATE PMS_DCB_OB_YEARLY SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND ? AND fin_year=? AND sch_sno=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE scheme_sno=? and office_id =?  AND METER_STATUS='L' )";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,new_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,month);
					ps.setInt(5,Year);
					ps.setInt(6,sche_no);
					ps.setInt(7,sche_no);
					ps.setInt(8,frm_off);

						
						System.out.println("Query$$$"+sql);
						rs=ps.executeQuery();

						xml=xml+"<flag>success</flag>";
					}catch(Exception e)
					{
						System.out.println("Error:"+e);
						xml=xml+"<flag>failed6</flag>";
					}
						
						
					try
					{
						
					sql="UPDATE PMS_DCB_TRN_CB_MONTHLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 3 AND ? AND fin_year=? AND sch_sno=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE scheme_sno=? and office_id =?  AND METER_STATUS='L' )";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,new_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,month1);
					ps.setInt(5,Year1);
					ps.setInt(6,sche_no);
					ps.setInt(7,sche_no);
					ps.setInt(8,frm_off);

						
						System.out.println("Query$$$"+sql);
						rs1=ps.executeUpdate();


						xml=xml+"<flag>success</flag>";
					}catch(Exception e)
					{
						System.out.println("Error:"+e);
						xml=xml+"<flag>failed7</flag>";
					}

				}
				
				if(month==4)
				{
					
					try
					{
						
					sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND YEAR=? AND sch_sno=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE scheme_sno=? and office_id =?  AND METER_STATUS='L' )";
					ps=con.prepareStatement(sql);
					ps.setInt(1,new_off_id);
					ps.setInt(2,frm_off);
					ps.setInt(3,Year_min);
					ps.setInt(4,sche_no);
					ps.setInt(5,sche_no);
					ps.setInt(6,frm_off);

						
						System.out.println("Query$$$"+sql);
						rs=ps.executeQuery();

						xml=xml+"<flag>success</flag>";
					}catch(Exception e)
					{
						System.out.println("Error:"+e);
						xml=xml+"<flag>failed9</flag>";
					}
				
				try
				{
					
				sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 4 AND YEAR=? AND sch_sno=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE scheme_sno=? and office_id =?  AND METER_STATUS='L' )";
				ps=con.prepareStatement(sql);
				ps.setInt(1,new_off_id);
				ps.setInt(2,frm_off);
				ps.setInt(3,Year);
				ps.setInt(4,sche_no);
				ps.setInt(5,sche_no);
				ps.setInt(6,frm_off);

					
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed10</flag></response>";
				}							
					
				}
				if(month<4)
				{
		try
		{
			
		sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND YEAR=? AND sch_sno=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE scheme_sno=? and office_id =?  AND METER_STATUS='L' )";
		ps=con.prepareStatement(sql);
		ps.setInt(1,new_off_id);
		ps.setInt(2,frm_off);
		ps.setInt(3,Year_min1);
		ps.setInt(4,sche_no);
		ps.setInt(5,sche_no);
		ps.setInt(6,frm_off);

			
			System.out.println("Query$$$"+sql);
			rs=ps.executeQuery();

			xml=xml+"<flag>success</flag></response>";
		}catch(Exception e)
		{
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed9</flag></response>";
		}
	
	try
	{
		
	sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 4 AND YEAR=? AND sch_sno=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE  scheme_sno=? and office_id =?  AND METER_STATUS='L' )";
	ps=con.prepareStatement(sql);
	ps.setInt(1,new_off_id);
	ps.setInt(2,frm_off);
	ps.setInt(3,Year1);
	ps.setInt(4,sche_no);
	ps.setInt(5,sche_no);
	ps.setInt(6,frm_off);

		
		System.out.println("Query$$$"+sql);
		rs=ps.executeQuery();

		xml=xml+"<flag>success</flag></response>";
	}catch(Exception e)
	{
		System.out.println("Error:"+e);
		xml=xml+"<flag>failed10</flag></response>";
	}

}	
			if(month>4)
			{
				try
				{
					
				sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND ? AND YEAR=? AND sch_sno=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE scheme_sno=? and office_id =?  AND METER_STATUS='L' )";
				ps=con.prepareStatement(sql);
				ps.setInt(1,new_off_id);
				ps.setInt(2,frm_off);
				ps.setInt(3,month1);
				ps.setInt(4,Year1);
				ps.setInt(5,sche_no);
				ps.setInt(6,sche_no);
				ps.setInt(7,frm_off);

					
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed10</flag></response>";
				}

			}			
				}	
				
		
			else if(command.equalsIgnoreCase("SchemeProceed6"))
			{
				System.out.println("reached SchemeProceed6");
				xml=xml+"<response><command>SchemeProceed6</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				int month=Integer.parseInt(request.getParameter("month"));
				int Year=Integer.parseInt(request.getParameter("Year"));
				int sche_no=Integer.parseInt(request.getParameter("sche_no"));
				int new_Scheme_no=Integer.parseInt(request.getParameter("new_Scheme_no"));
				int month1=Integer.parseInt(request.getParameter("month1"));
				int Year1=Integer.parseInt(request.getParameter("Year1"));
			//	String Da_te=request.getParameter("Da_te");
				String user_id=request.getParameter("user_id");
				try
				{
					

					sql="update PMS_DCB_MST_BENEFICIARY_METRE set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),scheme_sno=? , old_sch_sno= scheme_sno where scheme_sno=? and office_id= ? and meter_status='L'";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,new_Scheme_no);
					ps.setInt(3,sche_no);
					ps.setInt(4,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					xml=xml+"<flag>success</flag>";
				}
				catch(Exception e)
				{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed1</flag>";
				}
				try
				{
					sql="update PMS_DCB_TARIFF_SLAB set sch_sno=? where sch_sno=? and office_id=?";
					ps=con.prepareStatement(sql);
					ps.setInt(1,new_Scheme_no);
					ps.setInt(2,sche_no);
					ps.setInt(3,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					xml=xml+"<flag>success</flag>";
				}
				catch(Exception e)
				{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed2</flag>";
				}
				try
				{
					sql="update PMS_DCB_ALLOTTED set sch_sno=? where sch_sno=? and office_id= ?";
					ps=con.prepareStatement(sql);
					ps.setInt(1,new_Scheme_no);
					ps.setInt(2,sche_no);
					ps.setInt(3,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					xml=xml+"<flag>success</flag>";
				}
				catch(Exception e)
				{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed3</flag>";
				}
				
//      yearly ,monthly and ledger are extended only upto december so after dec 2018 extend it				
				
				try
				{
			  	sql="update PMS_DCB_OB_YEARLY set sch_sno=? where sch_sno=? and office_id= ? and month between 4 and  ?  and fin_year= ? ";

			//		sql="update PMS_DCB_OB_YEARLY set sch_sno=? where sch_sno=? and office_id= ? and month= ?  and fin_year= ? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,new_Scheme_no);
					ps.setInt(2,sche_no);
					ps.setInt(3,frm_off);
					ps.setInt(4,month);
					ps.setInt(5,Year);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					xml=xml+"<flag>success</flag>";
				}
				catch(Exception e)
				{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed4</flag>";
				}
				
				try
				{
					sql="update PMS_DCB_TRN_CB_MONTHLY set sch_sno=? where sch_sno=? and office_id= ? and month between 3 and   ?  and fin_year= ? ";
				//	sql="update PMS_DCB_TRN_CB_MONTHLY set sch_sno=? where sch_sno=? and office_id= ? and month=?  and fin_year= ? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,new_Scheme_no);
					ps.setInt(2,sche_no);
					ps.setInt(3,frm_off);
					ps.setInt(4,month1);
					ps.setInt(5,Year1);
					System.out.println("Query$$$"+sql);
					rs1=ps.executeUpdate();

					xml=xml+"<flag>success</flag>";
				}
				catch(Exception e)
				{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed5</flag>";
				}
				try
				{
			      sql="update  PMS_DCB_LEDGER_ACTUAL set sch_sno=? where sch_sno=? and office_id= ? and month between 4 and  ?  and YEAR = ? ";
			//		sql="update  PMS_DCB_LEDGER_ACTUAL set sch_sno=? where sch_sno=? and office_id= ? and month=?  and YEAR = ? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,new_Scheme_no);
					ps.setInt(2,sche_no);
					ps.setInt(3,frm_off);
					ps.setInt(4,month);
					ps.setInt(5,Year);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					xml=xml+"<flag>success</flag></response>";
				}
				catch(Exception e)
				{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed6</flag></response>";
				}
			
				
			}
							
			
			else if(command.equalsIgnoreCase("SchemeProceed4"))
			{
				System.out.println("reached SchemeProceed4");
				xml=xml+"<response><command>SchemeProceed4</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				int New_off_id4=Integer.parseInt(request.getParameter("New_off_id4"));
	//			int Year=Integer.parseInt(request.getParameter("Year"));
				int sche_no=Integer.parseInt(request.getParameter("sche_no"));
				int new_Sub_id=Integer.parseInt(request.getParameter("new_Sub_id"));
	//			int month1=Integer.parseInt(request.getParameter("month1"));
	//			int Year1=Integer.parseInt(request.getParameter("Year1"));
			//	String Da_te=request.getParameter("Da_te");
	//			String user_id=request.getParameter("user_id");
				
			
				try
				{
					sql="update  PMS_DCB_MST_BENEFICIARY_METRE  set office_id=? ,sub_div_id= ? where scheme_sno=? and office_id=? and METER_STATUS='L'";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id4);
					ps.setInt(2,new_Sub_id);
					ps.setInt(3,sche_no);
					ps.setInt(4,frm_off);
					System.out.println("Query$$$"+sql);
					rs1=ps.executeUpdate();

					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e){
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed</flag></response>";
				
			    }
			}
			
			
			
			else if(command.equalsIgnoreCase("proceed"))
			{
				System.out.println("reached proceed");
				xml=xml+"<response><command>proceed</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				int New_off_id=Integer.parseInt(request.getParameter("New_off_id"));
				int Year=Integer.parseInt(request.getParameter("Year"));
				int month=Integer.parseInt(request.getParameter("month"));
				int month1=Integer.parseInt(request.getParameter("month1"));
				int Year1=Integer.parseInt(request.getParameter("Year1"));
		//		String Da_te=request.getParameter("Da_te");
				String user_id=request.getParameter("user_id");
				
				int Year_min1=Year1-1;
				int Year_min=Year-1;
				
				System.out.println("frm_off"+frm_off);
				System.out.println("New_off_id"+New_off_id);
				System.out.println("month"+month);
				System.out.println("Year"+Year);
				System.out.println("month1"+month1);
				System.out.println("Year1"+Year1);

				try
				{
					sql="update PMS_DCB_DIV_DIST_MAP  set old_office_id= office_id,office_id=?  where office_id= ? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id);
					ps.setInt(2,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed1</flag>";
				}
				try
				{
					sql="update PMS_DCB_MST_BENEFICIARY   set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),old_office_id= office_id,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO, CHANGE_MONTH_WEF=? ,CHANGE_YEAR_WEF=?, office_id= ? where office_id=?  and status='L' ";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
				//	ps.setTimestamp(2,Da_te);
					ps.setInt(2,month);
					ps.setInt(3,Year);
					ps.setInt(4,New_off_id);
					ps.setInt(5,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed2</flag>";
				}
				
				try
				{
					sql=" update PMS_DCB_MST_BENEFICIARY_METRE  set UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),old_office_id= office_id,office_id= ? where office_id= ? and METER_STATUS='L'";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
				//	ps.setTimestamp(2,Da_te);
					ps.setInt(2,New_off_id);
					ps.setInt(3,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed3</flag>";
				}
				
				try
				{
					sql=" update PMS_DCB_ALLOTTED   set old_office_id= office_id,office_id=?  where office_id= ?";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id);
					ps.setInt(2,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed4</flag>";
				}
				
				try
				{
					sql=" update PMS_DCB_SETTING   set old_office_id= office_id,office_id=? where office_id= ?  ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id);
					ps.setInt(2,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed5</flag>";
				}
				try
				{
					sql=" update PMS_DCB_TARIFF_SLAB  set old_office_id= office_id,office_id= ? where office_id= ?  ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id);
					ps.setInt(2,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed6</flag>";
				}
				
				
				  if(month<5)
					{
						
											
				try
					{
						
					sql="UPDATE PMS_DCB_OB_YEARLY SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
                                                                                       	ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,New_off_id);
					ps.setInt(3,frm_off);
					ps.setInt(4,Year_min);
					ps.setInt(5,frm_off);

						
						System.out.println("Query$$$"+sql);
						rs=ps.executeQuery();

						xml=xml+"<flag>success</flag>";
					}catch(Exception e)
					{
						System.out.println("Error:"+e);
						xml=xml+"<flag>failed5</flag>";
					}
				
				try
				{
					
				sql="UPDATE PMS_DCB_OB_YEARLY SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 4 AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
				ps=con.prepareStatement(sql);
				ps.setString(1,user_id);
				ps.setInt(2,New_off_id);
				ps.setInt(3,frm_off);
				ps.setInt(4,Year);
				ps.setInt(5,frm_off);

					
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();

					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed6</flag>";
				}
					
					
				try
				{
					
				sql="UPDATE PMS_DCB_TRN_CB_MONTHLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
				ps=con.prepareStatement(sql);
				ps.setString(1,user_id);
				ps.setInt(2,New_off_id);
				ps.setInt(3,frm_off);
				ps.setInt(4,Year_min1);
				ps.setInt(5,frm_off);

					
					System.out.println("Query$$$"+sql);
					rs1=ps.executeUpdate();


					xml=xml+"<flag>success</flag>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed7</flag>";
				}
			
			try
			{
				
			sql="UPDATE PMS_DCB_TRN_CB_MONTHLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 3 AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
			ps=con.prepareStatement(sql);
			ps.setString(1,user_id);
			ps.setInt(2,New_off_id);
			ps.setInt(3,frm_off);
			ps.setInt(4,Year1);
			ps.setInt(5,frm_off);

				
				System.out.println("Query$$$"+sql);
				rs1=ps.executeUpdate();


				xml=xml+"<flag>success</flag>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed8</flag>";
			}
					}
					
					if(month>4)
					{
						
						try
						{
							
						sql="UPDATE PMS_DCB_OB_YEARLY SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND ? AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
						ps=con.prepareStatement(sql);
						ps.setString(1,user_id);
						ps.setInt(2,New_off_id);
						ps.setInt(3,frm_off);
						ps.setInt(4,month);
						ps.setInt(5,Year);
						ps.setInt(6,frm_off);

							
							System.out.println("Query$$$"+sql);
							rs=ps.executeQuery();

							xml=xml+"<flag>success</flag>";
						}catch(Exception e)
						{
							System.out.println("Error:"+e);
							xml=xml+"<flag>failed6</flag>";
						}
							
							
						try
						{
							
						sql="UPDATE PMS_DCB_TRN_CB_MONTHLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 3 AND ? AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
						ps=con.prepareStatement(sql);
						ps.setString(1,user_id);
						ps.setInt(2,New_off_id);
						ps.setInt(3,frm_off);
						ps.setInt(4,month1);
						ps.setInt(5,Year1);
						ps.setInt(6,frm_off);

							
							System.out.println("Query$$$"+sql);
							rs1=ps.executeUpdate();


							xml=xml+"<flag>success</flag>";
						}catch(Exception e)
						{
							System.out.println("Error:"+e);
							xml=xml+"<flag>failed7</flag>";
						}

					}
						
												
					if(month==4)
					{
						
						try
						{
							
						sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
						ps=con.prepareStatement(sql);
						ps.setInt(1,New_off_id);
						ps.setInt(2,frm_off);
						ps.setInt(3,Year_min);
						ps.setInt(4,frm_off);

							
							System.out.println("Query$$$"+sql);
							rs=ps.executeQuery();

							xml=xml+"<flag>success</flag>";
						}catch(Exception e)
						{
							System.out.println("Error:"+e);
							xml=xml+"<flag>failed9</flag>";
						}
					
					try
					{
						
					sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 4 AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id);
					ps.setInt(2,frm_off);
					ps.setInt(3,Year);
					ps.setInt(4,frm_off);

						
						System.out.println("Query$$$"+sql);
						rs=ps.executeQuery();

						xml=xml+"<flag>success</flag></response>";
					}catch(Exception e)
					{
						System.out.println("Error:"+e);
						xml=xml+"<flag>failed10</flag></response>";
					}							
						
					}
					if(month<4)
					{
			try
			{
				
			sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
			ps=con.prepareStatement(sql);
			ps.setInt(1,New_off_id);
			ps.setInt(2,frm_off);
			ps.setInt(3,Year_min1);
			ps.setInt(4,frm_off);

				
				System.out.println("Query$$$"+sql);
				rs=ps.executeQuery();

				xml=xml+"<flag>success</flag></response>";
			}catch(Exception e)
			{
				System.out.println("Error:"+e);
				xml=xml+"<flag>failed9</flag></response>";
			}
		
		try
		{
			
		sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 4 AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
		ps=con.prepareStatement(sql);
		ps.setInt(1,New_off_id);
		ps.setInt(2,frm_off);
		ps.setInt(3,Year1);
		ps.setInt(4,frm_off);

			
			System.out.println("Query$$$"+sql);
			rs=ps.executeQuery();

			xml=xml+"<flag>success</flag></response>";
		}catch(Exception e)
		{
			System.out.println("Error:"+e);
			xml=xml+"<flag>failed10</flag></response>";
		}

	}	
				if(month>4)
				{
					try
					{
						
					sql="UPDATE PMS_DCB_LEDGER_ACTUAL   SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND ? AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id);
					ps.setInt(2,frm_off);
					ps.setInt(3,month1);
					ps.setInt(4,Year1);
					ps.setInt(5,frm_off);

						
						System.out.println("Query$$$"+sql);
						rs=ps.executeQuery();

						xml=xml+"<flag>success</flag></response>";
					}catch(Exception e)
					{
						System.out.println("Error:"+e);
						xml=xml+"<flag>failed10</flag></response>";
					}

				}				
					}
		
		
		
		
		
						else if(command.equalsIgnoreCase("getBen1"))
			{
				System.out.println("reached getBen1");
				xml=xml+"<response><command>getBen1</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				
//				String name=request.getParameter("sname");
				
				
				try
				{
					sql="select count(distinct beneficiary_sno)as twad from PMS_DCB_MST_BENEFICIARY where office_id=? and status='L' ";

					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
		    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
											
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}
				System.out.println("xml--->"+xml);
			}
		
		
						else if(command.equalsIgnoreCase("proceed1"))
						{
							System.out.println("reached proceed1");
							xml=xml+"<response><command>proceed1</command>";
							int frm_off=Integer.parseInt(request.getParameter("frm_off"));
							int New_off_id4=Integer.parseInt(request.getParameter("New_off_id4"));
							int Year=Integer.parseInt(request.getParameter("Year"));
							int month=Integer.parseInt(request.getParameter("month"));
							int month1=Integer.parseInt(request.getParameter("month1"));
							int Year1=Integer.parseInt(request.getParameter("Year1"));
							int Year_min1=Year1-1;

							int Year_min=Year-1;
							
					//		String Da_te=request.getParameter("Da_te");
							String user_id=request.getParameter("user_id");
							
							System.out.println("frm_off"+frm_off);
							System.out.println("New_off_id4"+New_off_id4);
							System.out.println("month"+month);
							System.out.println("Year"+Year);
							System.out.println("month1"+month1);
							System.out.println("Year1"+Year1);
                                  try{
	
								sql="UPDATE PMS_DCB_MST_BENEFICIARY SET UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),old_office_id=office_id,office_id=?,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO WHERE office_id=? AND beneficiary_sno  IN  ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE    WHERE office_id =?   AND meter_STATUS='L' ) AND STATUS='L'";
								
								ps=con.prepareStatement(sql);
								ps.setString(1,user_id);
								ps.setInt(2,New_off_id4);
								ps.setInt(3,frm_off);
								ps.setInt(4,New_off_id4);

								System.out.println("Query$$$"+sql);
								rs1=ps.executeUpdate();

								xml=xml+"<flag>success</flag>";
							}catch(Exception e)
							{
								System.out.println("Error:"+e);
								xml=xml+"<flag>failed1</flag>";
							}
							try
							{
								
								sql="UPDATE PMS_DCB_ALLOTTED  SET UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),old_office_id=office_id,office_id=?,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO WHERE office_id=? AND beneficiary_sno  IN  ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE   WHERE office_id =?   AND meter_STATUS='L' )";
								
								ps=con.prepareStatement(sql);
								ps.setString(1,user_id);
								ps.setInt(2,New_off_id4);
								ps.setInt(3,frm_off);
								ps.setInt(4,New_off_id4);

														
								System.out.println("Query$$$"+sql);
								rs1=ps.executeUpdate();
								xml=xml+"<flag>success</flag>";
							}catch(Exception e)
							{
								System.out.println("Error:"+e);
								xml=xml+"<flag>failed2</flag>";
							}
							
							try
							{
								sql="UPDATE PMS_DCB_TARIFF_SLAB  SET UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),old_office_id=office_id,office_id=?,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO WHERE office_id=? AND beneficiary_sno  IN  ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE   WHERE office_id =?   AND meter_STATUS='L' ) AND ACTIVE_STATUS='A'";
								
								ps=con.prepareStatement(sql);
								ps.setString(1,user_id);
								ps.setInt(2,New_off_id4);
								ps.setInt(3,frm_off);
								ps.setInt(4,New_off_id4);

								
								
								
								System.out.println("Query$$$"+sql);
								rs1=ps.executeUpdate();

								xml=xml+"<flag>success</flag>";
							}catch(Exception e)
							{
								System.out.println("Error:"+e);
								xml=xml+"<flag>failed3</flag>";
							}
							
							try
							{
						sql="UPDATE PMS_DCB_DIV_DIST_MAP  SET old_office_id=office_id,office_id=? WHERE office_id=? ";
								
								ps=con.prepareStatement(sql);
								ps.setInt(1,New_off_id4);
								ps.setInt(2,frm_off);
																
								System.out.println("Query$$$"+sql);
								rs1=ps.executeUpdate();

								xml=xml+"<flag>success</flag>";
							}catch(Exception e)
							{
								System.out.println("Error:"+e);
								xml=xml+"<flag>failed4</flag>";
							}
							
							if(month<5)
							{
								
													
						try
							{
								
							sql="UPDATE PMS_DCB_OB_YEARLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE   WHERE office_id =?  AND METER_STATUS='L' )";
							ps=con.prepareStatement(sql);
							ps.setString(1,user_id);
							ps.setInt(2,New_off_id4);
							ps.setInt(3,frm_off);
							ps.setInt(4,Year_min);
							ps.setInt(5,New_off_id4);

								
								System.out.println("Query$$$"+sql);
								rs1=ps.executeUpdate();

								xml=xml+"<flag>success</flag>";
							}catch(Exception e)
							{
								System.out.println("Error:"+e);
								xml=xml+"<flag>failed5</flag>";
							}
						
						try
						{
							
						sql="UPDATE PMS_DCB_OB_YEARLY SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 4 AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
						ps=con.prepareStatement(sql);
						ps.setString(1,user_id);
						ps.setInt(2,New_off_id4);
						ps.setInt(3,frm_off);
						ps.setInt(4,Year);
						ps.setInt(5,New_off_id4);

							
							System.out.println("Query$$$"+sql);
							rs1=ps.executeUpdate();

							xml=xml+"<flag>success</flag>";
						}catch(Exception e)
						{
							System.out.println("Error:"+e);
							xml=xml+"<flag>failed6</flag>";
						}
							
							
						try
						{
							
						sql="UPDATE PMS_DCB_TRN_CB_MONTHLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
						ps=con.prepareStatement(sql);
						ps.setString(1,user_id);
						ps.setInt(2,New_off_id4);
						ps.setInt(3,frm_off);
						ps.setInt(4,Year_min1);
						ps.setInt(5,New_off_id4);

							
							System.out.println("Query$$$"+sql);
							rs1=ps.executeUpdate();


							xml=xml+"<flag>success</flag>";
						}catch(Exception e)
						{
							System.out.println("Error:"+e);
							xml=xml+"<flag>failed7</flag>";
						}
					
					try
					{
						
					sql="UPDATE PMS_DCB_TRN_CB_MONTHLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 3 AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
					ps=con.prepareStatement(sql);
					ps.setString(1,user_id);
					ps.setInt(2,New_off_id4);
					ps.setInt(3,frm_off);
					ps.setInt(4,Year1);
					ps.setInt(5,New_off_id4);

						
						System.out.println("Query$$$"+sql);
						rs1=ps.executeUpdate();


						xml=xml+"<flag>success</flag>";
					}catch(Exception e)
					{
						System.out.println("Error:"+e);
						xml=xml+"<flag>failed8</flag>";
					}
							}
							
							if(month>4)
							{
								
								try
								{
									
								sql="UPDATE PMS_DCB_OB_YEARLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND ? AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
								ps=con.prepareStatement(sql);
								ps.setString(1,user_id);
								ps.setInt(2,New_off_id4);
								ps.setInt(3,frm_off);
								ps.setInt(4,month);
								ps.setInt(5,Year);
								ps.setInt(6,New_off_id4);

									
									System.out.println("Query$$$"+sql);
									rs=ps.executeQuery();

									xml=xml+"<flag>success</flag>";
								}catch(Exception e)
								{
									System.out.println("Error:"+e);
									xml=xml+"<flag>failed6</flag>";
								}
									
									
								try
								{
									
								sql="UPDATE PMS_DCB_TRN_CB_MONTHLY  SET  UPDATED_BY_USER_ID=?,UPDATED_DATE=clock_timestamp(),OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 3 AND ? AND fin_year=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
								ps=con.prepareStatement(sql);
								ps.setString(1,user_id);
								ps.setInt(2,New_off_id4);
								ps.setInt(3,frm_off);
								ps.setInt(4,month1);
								ps.setInt(5,Year1);
								ps.setInt(6,New_off_id4);

									
									System.out.println("Query$$$"+sql);
									rs1=ps.executeUpdate();


									xml=xml+"<flag>success</flag>";
								}catch(Exception e)
								{
									System.out.println("Error:"+e);
									xml=xml+"<flag>failed7</flag>";
								}

							}
								
														
							if(month==4)
							{
								
								try
								{
									
								sql="UPDATE PMS_DCB_LEDGER_ACTUAL  SET OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE   WHERE office_id =?  AND METER_STATUS='L' )";
								ps=con.prepareStatement(sql);
								ps.setInt(1,New_off_id4);
								ps.setInt(2,frm_off);
								ps.setInt(3,Year_min);
								ps.setInt(4,New_off_id4);

									
									System.out.println("Query$$$"+sql);
									rs1=ps.executeUpdate();

									xml=xml+"<flag>success</flag>";
								}catch(Exception e)
								{
									System.out.println("Error:"+e);
									xml=xml+"<flag>failed9</flag>";
								}
							
							try
							{
								
							sql="UPDATE PMS_DCB_LEDGER_ACTUAL  SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 4 AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
							ps=con.prepareStatement(sql);
							ps.setInt(1,New_off_id4);
							ps.setInt(2,frm_off);
							ps.setInt(3,Year);
							ps.setInt(4,New_off_id4);

								
								System.out.println("Query$$$"+sql);
								rs1=ps.executeUpdate();

								xml=xml+"<flag>success</flag></response>";
							}catch(Exception e)
							{
								System.out.println("Error:"+e);
								xml=xml+"<flag>failed10</flag></response>";
							}							
								
							}
							if(month<4)
							{
					try
					{
						
					sql="UPDATE PMS_DCB_LEDGER_ACTUAL  SET OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND 12 AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
					ps=con.prepareStatement(sql);
					ps.setInt(1,New_off_id4);
					ps.setInt(2,frm_off);
					ps.setInt(3,Year_min1);
					ps.setInt(4,New_off_id4);

						
						System.out.println("Query$$$"+sql);
						rs1=ps.executeUpdate();

						xml=xml+"<flag>success</flag>";
					}catch(Exception e)
					{
						System.out.println("Error:"+e);
						xml=xml+"<flag>failed9</flag></response>";
					}
				
				try
				{
					
				sql="UPDATE PMS_DCB_LEDGER_ACTUAL  SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 1 AND 4 AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
				ps=con.prepareStatement(sql);
				ps.setInt(1,New_off_id4);
				ps.setInt(2,frm_off);
				ps.setInt(3,Year1);
				ps.setInt(4,New_off_id4);

					
					System.out.println("Query$$$"+sql);
					rs1=ps.executeUpdate();

					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed10</flag></response>";
				}
		
			}	
						if(month>4)
						{
							try
							{
								
							sql="UPDATE PMS_DCB_LEDGER_ACTUAL  SET  OLD_SCH_SNO=sch_sno,OLD_BENEFICIARY_SNO=BENEFICIARY_SNO,old_office_id=office_id,office_id=? WHERE office_id=? AND MONTH BETWEEN 4 AND ? AND YEAR=? AND beneficiary_sno IN ( SELECT DISTINCT beneficiary_sno  FROM PMS_DCB_MST_BENEFICIARY_METRE  WHERE office_id =?  AND METER_STATUS='L' )";
							ps=con.prepareStatement(sql);
							ps.setInt(1,New_off_id4);
							ps.setInt(2,frm_off);
							ps.setInt(3,month1);
							ps.setInt(4,Year1);
							ps.setInt(5,New_off_id4);

								
								System.out.println("Query$$$"+sql);
								rs1=ps.executeUpdate();

								xml=xml+"<flag>success</flag></response>";
							}catch(Exception e)
							{
								System.out.println("Error:"+e);
								xml=xml+"<flag>failed10</flag></response>";
							}

							
							
						}
						
						
						}		
		
		
					
									else if(command.equalsIgnoreCase("getBen1"))
						{
							System.out.println("reached getBen1");
							xml=xml+"<response><command>getBen1</command>";
							int frm_off=Integer.parseInt(request.getParameter("frm_off"));
							
//							String name=request.getParameter("sname");
							
							
							try
							{
								sql="select count(distinct beneficiary_sno)as twad from PMS_DCB_MST_BENEFICIARY where office_id=? and status='L' ";

								ps=con.prepareStatement(sql);
								ps.setInt(1,frm_off);
								System.out.println("Query$$$"+sql);
								rs=ps.executeQuery();
								while(rs.next())
								{
					    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
														
								}
								xml=xml+"<flag>success</flag></response>";
							}catch(Exception e)
							{
								System.out.println("Error:"+e);
								xml=xml+"<flag>failed</flag></response>";
							}
							System.out.println("xml--->"+xml);
						}

		
		
		
			else if(command.equalsIgnoreCase("getBen2"))
			{
				System.out.println("reached getBen2");
				xml=xml+"<response><command>getBen2</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				int month=Integer.parseInt(request.getParameter("month"));
				int Year=Integer.parseInt(request.getParameter("Year"));

							
//				String name=request.getParameter("sname");
				try
				{
					sql="select count(distinct BENEFICIARY_SNO)as twad from PMS_DCB_TRN_BILL_DMD where office_id=? and bill_month=? and bill_year=? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					ps.setInt(2,month);
					ps.setInt(3,Year);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
		    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
											
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}
				System.out.println("xml--->"+xml);
			}
			else if(command.equalsIgnoreCase("getBen3"))
			{
				System.out.println("reached getBen3");
				xml=xml+"<response><command>getBen3</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				int month=Integer.parseInt(request.getParameter("month"));
				int Year=Integer.parseInt(request.getParameter("Year"));

							
//				String name=request.getParameter("sname");
				try
				{
					sql="select count(distinct beneficiary_sno)as twad from PMS_DCB_TRN_CB_MONTHLY  where office_id=? and month=? and fin_year=?";
					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					ps.setInt(2,month);
					ps.setInt(3,Year);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
		    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
											
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}
				System.out.println("xml--->"+xml);
			}
			else if(command.equalsIgnoreCase("getBen4"))
			{
				System.out.println("reached getBen4");
				xml=xml+"<response><command>getBen4</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				int month=Integer.parseInt(request.getParameter("month"));
				int Year=Integer.parseInt(request.getParameter("Year"));

							
//				String name=request.getParameter("sname");
				try
				{
					sql="select count(distinct beneficiary_sno)as twad from PMS_DCB_OB_YEARLY  where office_id=? and month=? and fin_year=? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					ps.setInt(2,month);
					ps.setInt(3,Year);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
		    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
											
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}
				System.out.println("xml--->"+xml);
			}
			else if(command.equalsIgnoreCase("getBen5"))
			{
				System.out.println("reached getBen5");
				xml=xml+"<response><command>getBen5</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				int month=Integer.parseInt(request.getParameter("month"));
				int Year=Integer.parseInt(request.getParameter("Year"));

							
//				String name=request.getParameter("sname");
				try
				{
					sql="select count(distinct beneficiary_sno)as twad from PMS_DCB_LEDGER_ACTUAL where office_id=? and month=? and year=? ";
					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					ps.setInt(2,month);
					ps.setInt(3,Year);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
		    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
											
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}
				System.out.println("xml--->"+xml);
			}

			else if(command.equalsIgnoreCase("getBen6"))
			{
				System.out.println("reached getBen6");
				xml=xml+"<response><command>getBen6</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
			try
				{
					sql="select count(distinct beneficiary_sno)as twad from PMS_DCB_MST_BENEFICIARY_METRE where office_id=? and METER_STATUS='L' ";

					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
		    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}
				System.out.println("xml--->"+xml);
			}
		
			else if(command.equalsIgnoreCase("getBen7"))
			{
				System.out.println("reached getBen7");
				xml=xml+"<response><command>getBen7</command>";
				int frm_off=Integer.parseInt(request.getParameter("frm_off"));
				
				try
				{
					sql="select count(distinct beneficiary_sno)as twad from PMS_DCB_TARIFF_SLAB where office_id=? and ACTIVE_STATUS='A'";

					ps=con.prepareStatement(sql);
					ps.setInt(1,frm_off);
					System.out.println("Query$$$"+sql);
					rs=ps.executeQuery();
					while(rs.next())
					{
		    			xml=xml+"<BENEFICIARY_SNO>"+rs.getString("twad").trim()+"</BENEFICIARY_SNO>";
											
					}
					xml=xml+"<flag>success</flag></response>";
				}catch(Exception e)
				{
					System.out.println("Error:"+e);
					xml=xml+"<flag>failed</flag></response>";
				}
				System.out.println("xml--->"+xml);
			}
			//out.write(xml);
			out.println(xml);
			out.flush();
			out.close();
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
