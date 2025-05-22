package Servlets.AME.AME1.AMEM.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.converters.SqlDateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Calendar;

public class AME_Entry_Status_Monitoring extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public AME_Entry_Status_Monitoring() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj=null;
		Connection con=null;
		String xml="<response>";
		StringBuffer query_var=new StringBuffer();
		HttpSession session1 = request.getSession(false);
		response.setContentType(CONTENT_TYPE);  
		String	userid = (String) session1.getAttribute("UserId");
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		if (userid == null) {

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		try
		{
				obj=new Controller();
				con=obj.con();
				obj.createStatement(con);
				int process_code=Integer.parseInt(obj.setValue("process_code", request));
				int option=Integer.parseInt(obj.setValue("option", request));
				int process_year=Integer.parseInt(obj.setValue("year", request));
				int process_month=Integer.parseInt(obj.setValue("month", request));
				System.out.println("AME-------->AME_Entry_Status_Monitoring------option("+option+")---->PROCESSCODE("+process_code+")");
				String fin_year_value="";
				String pmonth=obj.setValue("pmonth", request);
				//int year2= (Integer.parseInt(obj.setValue("fin_year", request)));
				fin_year_value=obj.setValue("fin_year", request);
				String monthvalue=obj.month_val(pmonth);
				int year2=0; 
			    if (Integer.parseInt(pmonth)>3)  
					 year2=Integer.parseInt(fin_year_value.split("-")[0]);
				 else
					 year2=Integer.parseInt(fin_year_value.split("-")[1]);  
			    //	fin_year_value=(year2)+"-"+(year2+1);
			    System.out.println(fin_year_value);
			    System.out.println(year2);
				if (option==1)
				{
					if (process_code==1)
					{
							query_var.append("SELECT reg, " +
							"  REGION_OFFICE_ID, " +
							"  SUM(sch_count)          AS sch_count, " +
							"  SUM(bud_count)          AS bud_count, " +
							"  SUM(abs_count)          AS abs_count, " +
							"  SUM(year_count)         AS year_count, " +
							"  SUM(monthcount)         AS monthcount, " +
							"  SUM(scheme_details)     AS scheme_details, " +
							"  SUM(AME_AMOUNT)         AS AME_AMOUNT, " +
							"  SUM(BUD_AMOUNT)         AS BUD_AMOUNT, " +
							//"  SUM(schitem_count)      AS schitem_count, " +
							"  SUM(total_supplied_qty) AS total_supplied_qty, " +
							"  SUM(totalexp)           AS totalexp, " +
							"  SUM(amt)                AS amt, " +
							"  SUM(col)                AS col " +
							"FROM " +
							"  (SELECT Off1.OFFICE_NAME AS reg, " +
							"    off2.OFFICE_NAME       AS div, " +
							"    off2.OFFICE_ID, " +
							"    sch_count.ct AS sch_count, " +
							"    bud_count.ct AS bud_count, " +
							"    ABS.ct       AS abs_count, " +
							"    yearperform.year_count, " +
							"    monthwise.month_count AS monthcount, " +
							"    sch_details_ct        AS scheme_details, " +
							"    ameamount.amt         AS AME_AMOUNT, " +
							"    budamount.amt         AS BUD_AMOUNT, " +
							//"    schitem_count.ct      AS schitem_count, " +
							"    pqty.qty              AS total_supplied_qty, " +
							"    totalexp.amt          AS totalexp, " +
							"    wc.amt, " +
							"    collection.col, " +
							"    off2.REGION_OFFICE_ID " +
							"  FROM ( " +
							"    (SELECT REGION_OFFICE_ID, " +
							"      OFFICE_LEVEL_ID, " +
							"      OFFICE_ID, " +
							"      OFFICE_NAME " +
							"    FROM COM_MST_ALL_OFFICES_VIEW " +
							"    WHERE OFFICE_LEVEL_ID='RN' " +
							"    )Off1 " +
							"  JOIN " +
							"    (SELECT REGION_OFFICE_ID, " +
							"      OFFICE_LEVEL_ID, " + 
							"      OFFICE_ID, " +
							"      OFFICE_NAME " +
							"    FROM COM_MST_ALL_OFFICES_VIEW " +
							"    WHERE OFFICE_LEVEL_ID='DN' " +
							"    AND OFFICE_ID       IN " +
							"      (SELECT OFFICE_ID FROM PMS_DCB_DIV_DIST_MAP " +
							"      ) " + 
							"    )off2 " +
							"  ON off2.REGION_OFFICE_ID=off1.OFFICE_ID ) " +
							"  JOIN " +
							"    (SELECT COUNT(DISTINCT  sch_sno) AS ct, " +
							"      OFFICE_ID " +
							"    FROM PMS_DCB_DIV_SCHEME_MAP " +
							"    WHERE sch_sno IN " +
							"      (SELECT SCHEME_SNO FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE METER_STATUS='L' " +
							"      ) " +
							"    GROUP BY OFFICE_ID " +
							"    )sch_count " +
							"  ON sch_count.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT JOIN " +
							"    (SELECT COUNT(DISTINCT sch_sno) AS ct, " +
							"      OFFICE_ID " +
							"    FROM PMS_AME_TRN_BUDGET " +
							"    WHERE BUDGET_EST_AMT IS NOT NULL  AND FIN_YEAR='"+fin_year_value+"' " +
							"    GROUP BY OFFICE_ID " +
							"    )bud_count " +
							"  ON bud_count.OFFICE_ID=off2.OFFICE_ID " +
							/*" "  LEFT JOIN " +
							   (SELECT SUM(ct) AS ct, " +
							"      OFFICE_ID " + 
							"    FROM " +
							"      (SELECT COUNT(DISTINCT sch_sno) AS ct, " +
							"        sch_sno, " +
							"        OFFICE_ID " +
							"      FROM PMS_AME_TRN_SCHEME_ITEM " +
							"      WHERE FIN_YEAR='"+fin_year_value+"' " +
							"      GROUP BY OFFICE_ID, " +
							"        sch_sno " +
							"      ) " +
							"    GROUP BY OFFICE_ID " +
							"    )schitem_count " +
							"  ON schitem_count.OFFICE_ID=off2.OFFICE_ID " +*/
							"  LEFT JOIN " +
							"    (SELECT SUM(ct) ct , " +
							"      OFFICE_ID " +
							"    FROM " +
							"      (SELECT COUNT( DISTINCT sch_sno) AS ct, " +
							"        OFFICE_ID, " +
							"        sch_sno " +
							"      FROM PMS_AME_TRN_ABSTRACT a " +
							"      WHERE a.FIN_YEAR='"+fin_year_value+"' " +
							"      GROUP BY a.OFFICE_ID , " +
							"        a.sch_sno " +
							/*"      HAVING COUNT(sch_sno) >= " +
							"        (SELECT COUNT(*) AS ct " +
							"        FROM PMS_AME_TRN_SCHEME_ITEM b " +
							"        WHERE b.sch_sno=a.sch_sno " +
							"        AND b.OFFICE_ID=a.OFFICE_ID " +
							"        GROUP BY b.OFFICE_ID , " +
							"          b.sch_sno " +
							"        ) " +*/
							"      ) " +
							"    GROUP BY OFFICE_ID " +
							"    )ABS " +
							"  ON " +
							//" abs.OFFICE_ID =schitem_count.OFFICE_ID AND " +
							"   abs.OFFICE_ID=bud_count.OFFICE_ID " +
							"  AND abs.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT JOIN " +
							"    (SELECT SUM(ct) AS year_count, " +
							"      OFFICE_ID " +
							"    FROM " +
							"      (SELECT COUNT(DISTINCT SCH_SNO ) AS ct, " +
							"        OFFICE_ID " +
							"      FROM PMS_AME_TRN_SCH_PERFORM_YEAR " +
							"      WHERE FIN_YEAR='"+fin_year_value+"' " +
							"      GROUP BY OFFICE_ID, " +
							"        SCH_SNO " +
							"      ) " +
							"    GROUP BY OFFICE_ID " +
							"    )yearperform " + 
							"  ON yearperform.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT JOIN " +
							"    (SELECT SUM(ct) AS month_count, " +
							"      OFFICE_ID " +
							"    FROM " +
							"      (SELECT COUNT(DISTINCT SCH_SNO ) AS ct, " +
							"        OFFICE_ID " +
							"      FROM PMS_AME_TRN_SCH_PERFORM_MTH " +
							"      WHERE MONTH="+pmonth+
							"      AND YEAR   ="+year2+
							"      GROUP BY OFFICE_ID, " +
							"        SCH_SNO " +  
							"      ) " +
							"    GROUP BY OFFICE_ID " +
							"    )monthwise " +
							"  ON monthwise.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT JOIN " +
							"    (SELECT COUNT(*) AS sch_details_ct, " +
							"      OFFICE_ID " +
							"    FROM PMS_AME_MST_SCH_DETAILS " +
							"    GROUP BY OFFICE_ID " +
							"    )sch_details " +
							"  ON sch_details.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT JOIN " +
							"    (SELECT SUM(AM_EST_AMT) AS amt, " +
							"      office_id " +
							"    FROM PMS_AME_TRN_ABSTRACT where FIN_YEAR='"+fin_year_value+"' " +
							"    GROUP BY office_id " +
							"    )ameamount " +
							"  ON ameamount.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT JOIN " +
							"    (SELECT SUM(BUDGET_EST_AMT) AS amt, " +
							"      office_id " +
							"    FROM PMS_AME_TRN_BUDGET where FIN_YEAR='"+fin_year_value+"' " +
							"    GROUP BY office_id " +
							"    )budamount " +
							"  ON budamount.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT OUTER JOIN " +
							"    (SELECT SUM(QTY_CONSUMED_NET) /1000 AS qty, " +
							"      office_id, " +
							"      MONTH, " +
							"      YEAR " +
							"    FROM PMS_DCB_TRN_MONTHLY_PR " +
							"    WHERE MONTH    ="+pmonth+
							"    AND YEAR       ="+year2+
							"    AND METRE_SNO IN " +
							"      (SELECT METRE_SNO " +
							"      FROM pms_dcb_mst_beneficiary_metre " +
							"      WHERE meter_status = 'L' " +
							"      ) " +
							"    GROUP BY office_id, " +
							"      YEAR, " +
							"      MONTH " +
							"    )pqty " +
							"  ON pqty.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT JOIN " +
							"    (SELECT SUM(ACTUAL_EXP) / 100000 AS amt, " +
							"      office_id " +
							"    FROM PMS_AME_TRN_SCH_ACT_EXP_ITEM " +
							"    WHERE MONTH="+pmonth+
							"    AND YEAR   ="+year2+
							"    GROUP BY office_id " +
							"    )totalexp " +
							"  ON totalexp.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT OUTER JOIN " +
							"    (SELECT ROUND(SUM(TOTAL_AMT) / 100000,2) AS amt , " +
							"      office_id, " +
							"      MONTH, " +
							"      YEAR " +
							"    FROM PMS_DCB_WC_BILLING OFFICE_ID " +
							"    WHERE MONTH="+pmonth+
							"    AND YEAR   ="+year2+
							"    GROUP BY office_id, " +
							"      YEAR, " +
							"      MONTH " +
							"    ) wc " +
							"  ON wc.OFFICE_ID=off2.OFFICE_ID " +
							"  LEFT OUTER JOIN " +
							"    (SELECT ROUND(SUM(amount)/ 100000,2) AS col , " +
							"      ACCOUNTING_FOR_OFFICE_ID, " +
							"      cashbook_year, " +
							"      cashbook_month " +
							"    FROM FAS_RECEIPT_TRANSACTION a " +
							"    WHERE ACCOUNT_HEAD_CODE IN " +
							"      (SELECT ACCOUNT_HEAD_CODE " +
							"      FROM PMS_DCB_RECEIPT_ACCOUNT_MAP " +
							"      WHERE COLLECTION_TYPE IN (6,7,8,9) " +
							"      ) " +
							"    AND cashbook_month ="+pmonth+
							"    AND cashbook_year  ="+year2+
							"    AND receipt_no    IN " +
							"      (SELECT receipt_no " +
							"      FROM FAS_RECEIPT_MASTER b " +
							"      WHERE SUB_LEDGER_TYPE_CODE    = 14 " +
							"      AND cashbook_month            ="+pmonth+
							"      AND cashbook_year             ="+year2+
							"      AND RECEIPT_STATUS            ='L' " +
							"      AND a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID " +
							"      ) " +
							"    GROUP BY ACCOUNTING_FOR_OFFICE_ID, " +
							"      cashbook_year, " +
							"      cashbook_month " +
							"    )collection " +
							"  ON collection.ACCOUNTING_FOR_OFFICE_ID=off2.OFFICE_ID " +
							"  ORDER BY off1.OFFICE_NAME, " +
							"    off2.OFFICE_NAME " +
							"  ) " +
							"GROUP BY REGION_OFFICE_ID,  " +
							"  reg");
							 System.out.println(query_var.toString());
						 	xml=obj.resultXML(query_var.toString(),con,obj);
					  }  // process code 1
					else if (process_code==2)
					{ 
						String reg_id=obj.setValue("reg_id", request);
						StringBuffer SQL=new StringBuffer();					
						 
						SQL.append("SELECT Off1.OFFICE_NAME AS reg, off2.OFFICE_NAME  AS div,off2.OFFICE_ID,sch_count.ct AS sch_count, ");
						SQL.append("  bud_count.ct AS bud_count,ABS.ct AS abs_count, yearperform.year_count, ");
						SQL.append("  monthwise.month_count AS monthcount, sch_details_ct        AS scheme_details, ");
						SQL.append("  ameamount.amt         AS AME_AMOUNT,  budamount.amt         AS BUD_AMOUNT, ");
						//SQL.append("  --schitem_count.ct      AS schitem_count , " +
						SQL.append("  pqty.qty              AS total_supplied_qty, ");
						SQL.append("  totalexp.amt          AS totalexp,  wc.amt, ");
						SQL.append("  collection.col  FROM (   (SELECT REGION_OFFICE_ID, OFFICE_LEVEL_ID, OFFICE_ID, ");
						SQL.append("    OFFICE_NAME ");
						SQL.append("  FROM COM_MST_ALL_OFFICES_VIEW ");
						SQL.append("  WHERE OFFICE_LEVEL_ID='RN' ");
						SQL.append("  AND OFFICE_ID        ="+reg_id);
						SQL.append("  )Off1 ");
						SQL.append("JOIN ");
						SQL.append("  (SELECT REGION_OFFICE_ID, ");
						SQL.append("    OFFICE_LEVEL_ID, ");
						SQL.append("    OFFICE_ID, ");
						SQL.append("    OFFICE_NAME ");
						SQL.append("  FROM COM_MST_ALL_OFFICES_VIEW ");
						SQL.append("  WHERE OFFICE_LEVEL_ID='DN' ");
						SQL.append("  AND OFFICE_ID       IN ");
						SQL.append("    (SELECT OFFICE_ID FROM PMS_DCB_DIV_DIST_MAP ");
						SQL.append("    ) ");
						SQL.append("  )off2 ");
						SQL.append(" ON off2.REGION_OFFICE_ID=off1.OFFICE_ID ) ");
						SQL.append(" JOIN ");
						SQL.append("  (SELECT COUNT(*) AS ct, ");
						SQL.append("    OFFICE_ID ");
						SQL.append("  FROM PMS_DCB_DIV_SCHEME_MAP ");
						SQL.append("  WHERE sch_sno IN ");
						SQL.append("    (SELECT SCHEME_SNO FROM PMS_DCB_MST_BENEFICIARY_METRE WHERE METER_STATUS='L' ");
						SQL.append("    ) ");
						SQL.append("  GROUP BY OFFICE_ID ");
						SQL.append("  )sch_count ");
						SQL.append(" ON sch_count.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT COUNT(DISTINCT sch_sno) AS ct, ");
						SQL.append("    OFFICE_ID ");
						SQL.append("  FROM PMS_AME_TRN_BUDGET ");
						SQL.append("  WHERE BUDGET_EST_AMT IS NOT NULL AND FIN_YEAR='"+fin_year_value+"' ");
						SQL.append("  GROUP BY OFFICE_ID ");
						SQL.append("  )bud_count ");
						SQL.append(" ON bud_count.OFFICE_ID=off2.OFFICE_ID ");
					/*	SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT SUM(ct) AS ct, ");
						SQL.append("    OFFICE_ID ");
						SQL.append("  FROM ");
						SQL.append("    (SELECT COUNT(DISTINCT sch_sno) AS ct, ");
						SQL.append("      sch_sno, ");
						SQL.append("      OFFICE_ID ");
						SQL.append("    FROM PMS_AME_TRN_SCHEME_ITEM ");
						SQL.append("    WHERE FIN_YEAR='"+fin_year_value+"'");
						SQL.append("    GROUP BY OFFICE_ID, ");
						SQL.append("      sch_sno ");
						SQL.append("    ) ");
						SQL.append("  GROUP BY OFFICE_ID ");
						SQL.append("  )schitem_count ");
						SQL.append(" ON schitem_count.OFFICE_ID=off2.OFFICE_ID ");*/
						SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT SUM(ct) ct , ");
						SQL.append("    OFFICE_ID ");
						SQL.append("  FROM ");
						SQL.append("    (SELECT COUNT( DISTINCT sch_sno) AS ct, ");
						SQL.append("      OFFICE_ID, ");
						SQL.append("      sch_sno ");
						SQL.append("    FROM PMS_AME_TRN_ABSTRACT a ");
						SQL.append("    WHERE a.FIN_YEAR='"+fin_year_value+"'");
						SQL.append("    GROUP BY a.OFFICE_ID , ");
						SQL.append("      a.sch_sno ");
						/*SQL.append("    HAVING COUNT(sch_sno) >= ");
						SQL.append("      (SELECT COUNT(*) AS ct ");
						SQL.append("      FROM PMS_AME_TRN_SCHEME_ITEM b ");
						SQL.append("      WHERE b.sch_sno=a.sch_sno ");
						SQL.append("      AND b.OFFICE_ID=a.OFFICE_ID ");
						SQL.append("      GROUP BY b.OFFICE_ID , ");
						SQL.append("        b.sch_sno ");
						SQL.append("      ) ");
*/						SQL.append("    ) "); 
						SQL.append("  GROUP BY OFFICE_ID ");
						SQL.append("  )ABS ");
						SQL.append(" ON " );
						//		" -- abs.OFFICE_ID =schitem_count.OFFICE_ID AND ");
						SQL.append("   abs.OFFICE_ID=bud_count.OFFICE_ID ");
						SQL.append(" AND abs.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT SUM(ct) AS year_count, ");
						SQL.append("    OFFICE_ID ");
						SQL.append("  FROM ");
						SQL.append("    (SELECT COUNT(DISTINCT SCH_SNO ) AS ct, ");
						SQL.append("      OFFICE_ID ");
						SQL.append("    FROM PMS_AME_TRN_SCH_PERFORM_YEAR ");
						SQL.append("    WHERE FIN_YEAR='"+fin_year_value+"' ");
						SQL.append("    GROUP BY OFFICE_ID, ");
						SQL.append("      SCH_SNO ");
						SQL.append("    ) ");
						SQL.append("  GROUP BY OFFICE_ID ");
						SQL.append("  )yearperform ");
						SQL.append(" ON yearperform.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT SUM(ct) AS month_count, ");
						SQL.append("    OFFICE_ID ");
						SQL.append("  FROM ");
						SQL.append("    (SELECT COUNT(DISTINCT SCH_SNO ) AS ct, ");
						SQL.append("      OFFICE_ID ");
						SQL.append("    FROM PMS_AME_TRN_SCH_PERFORM_MTH ");
						SQL.append("    WHERE MONTH="+pmonth);
						SQL.append("    AND YEAR   ="+year2);
						SQL.append("    GROUP BY OFFICE_ID, ");
						SQL.append("      SCH_SNO ");
						SQL.append("    ) "); 
						SQL.append("  GROUP BY OFFICE_ID ");
						SQL.append("  )monthwise ");
						SQL.append(" ON monthwise.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT COUNT(*) AS sch_details_ct, ");
						SQL.append("    OFFICE_ID ");
						SQL.append("  FROM PMS_AME_MST_SCH_DETAILS ");
						SQL.append("  GROUP BY OFFICE_ID ");
						SQL.append("  )sch_details ");
						SQL.append(" ON sch_details.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT SUM(AM_EST_AMT) AS amt, ");
						SQL.append("    office_id ");
						SQL.append("  FROM PMS_AME_TRN_ABSTRACT where FIN_YEAR='"+fin_year_value+"' ");
						SQL.append("  GROUP BY office_id ");
						SQL.append("  )ameamount ");
						SQL.append(" ON ameamount.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT SUM(BUDGET_EST_AMT) AS amt, ");
						SQL.append("    office_id ");
						SQL.append("  FROM PMS_AME_TRN_BUDGET where FIN_YEAR='"+fin_year_value+"' ");
						SQL.append("  GROUP BY office_id ");
						SQL.append("  )budamount ");
						SQL.append(" ON budamount.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT OUTER JOIN ");
						SQL.append("  (SELECT SUM(QTY_CONSUMED_NET) /1000 AS qty, ");
						SQL.append("    office_id, ");
						SQL.append("    MONTH, ");
						SQL.append("    YEAR ");
						SQL.append("  FROM PMS_DCB_TRN_MONTHLY_PR ");
						SQL.append("  WHERE MONTH    ="+pmonth);
						SQL.append("  AND YEAR       ="+year2);
						SQL.append("  AND METRE_SNO IN ");
						SQL.append("    (SELECT METRE_SNO ");
						SQL.append("    FROM pms_dcb_mst_beneficiary_metre ");
						SQL.append("    WHERE meter_status = 'L' ");
						SQL.append("    ) ");
						SQL.append("  GROUP BY office_id, ");
						SQL.append("    YEAR, ");
						SQL.append("    MONTH ");
						SQL.append("  )pqty ");
						SQL.append(" ON pqty.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT JOIN ");
						SQL.append("  (SELECT SUM(ACTUAL_EXP) / 100000 AS amt, ");
						SQL.append("    office_id ");
						SQL.append("  FROM PMS_AME_TRN_SCH_ACT_EXP_ITEM ");
						SQL.append("  WHERE MONTH="+pmonth);
						SQL.append("  AND YEAR   ="+year2);
						SQL.append("  GROUP BY office_id ");
						SQL.append("  )totalexp ");
						SQL.append(" ON totalexp.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT OUTER JOIN ");
						SQL.append("  (SELECT ROUND(SUM(TOTAL_AMT) / 100000,2) AS amt , ");
						SQL.append("    office_id, ");
						SQL.append("    MONTH, ");
						SQL.append("    YEAR ");
						SQL.append("  FROM PMS_DCB_WC_BILLING OFFICE_ID ");
						SQL.append("  WHERE MONTH="+pmonth);
						SQL.append("  AND YEAR   ="+year2);
						SQL.append("  GROUP BY office_id, ");
						SQL.append("    YEAR, ");
						SQL.append("    MONTH ");
						SQL.append("  ) wc ");
						SQL.append(" ON wc.OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" LEFT OUTER JOIN ");
						SQL.append("  (SELECT ROUND(SUM(amount)/ 100000,2) AS col , ");
						SQL.append("    ACCOUNTING_FOR_OFFICE_ID, ");
						SQL.append("    cashbook_year, ");
						SQL.append("    cashbook_month ");
						SQL.append("  FROM FAS_RECEIPT_TRANSACTION a ");
						SQL.append("  WHERE ACCOUNT_HEAD_CODE IN ");
						SQL.append("    (SELECT ACCOUNT_HEAD_CODE ");
						SQL.append("    FROM PMS_DCB_RECEIPT_ACCOUNT_MAP ");
						SQL.append("    WHERE COLLECTION_TYPE IN (6,7,8,9) ");
						SQL.append("    ) ");
						SQL.append("  AND cashbook_month ="+pmonth);
						SQL.append("  AND cashbook_year  ="+year2);
						SQL.append("  AND receipt_no    IN ");
						SQL.append("    (SELECT receipt_no ");
						SQL.append("    FROM FAS_RECEIPT_MASTER b ");
						SQL.append("    WHERE SUB_LEDGER_TYPE_CODE    = 14 ");
						SQL.append("    AND cashbook_month            ="+pmonth);
						SQL.append("    AND cashbook_year             ="+year2);
						SQL.append("    AND RECEIPT_STATUS            ='L' ");
						SQL.append("    AND a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID ");
						SQL.append("    ) ");
						SQL.append("  GROUP BY ACCOUNTING_FOR_OFFICE_ID, ");
						SQL.append("    cashbook_year, ");
						SQL.append("    cashbook_month ");
						SQL.append("  )collection ");
						SQL.append(" ON collection.ACCOUNTING_FOR_OFFICE_ID=off2.OFFICE_ID ");
						SQL.append(" ORDER BY off1.OFFICE_NAME, ");
						SQL.append("  off2.OFFICE_NAME");
 						xml=obj.resultXML(SQL.toString(),con,obj);
					}
					
					else if (process_code==3)
					{
						String reg_id=obj.setValue("reg_id", request);
						StringBuffer SQL=new StringBuffer();			
						process_month=Integer.parseInt(obj.setValue("pmonth", request));
						SQL.append("SELECT sch.sch_name,  "); 
						SQL.append("  sch.sch_year,  ");
						SQL.append("  DECODE(ame.ameamt,NULL,0,ame.ameamt) AS amt, DECODE(bud.budamt,NULL,0,bud.budamt) AS budamt, ");
						SQL.append("  decode(wc.wcamt,null,0,wc.wcamt)  as wcamt, ");
						SQL.append("  decode(collection.col ,null,0,collection.col) as col, ");
						SQL.append("  decode(pqty.qty ,null,0,pqty.qty) as qty, ");
						SQL.append("  decode(totalexp.exp_amt ,null,0,totalexp.exp_amt) as exp_amt, ");
						SQL.append("  off.office_name  ");
						SQL.append("FROM  ");
						SQL.append("  (SELECT *  ");
						SQL.append("  FROM PMS_SCH_MASTER  ");
						SQL.append("  WHERE   ");
						SQL.append("    sch_sno   IN  ");
						SQL.append("    (SELECT sch_sno  ");
						SQL.append("    FROM PMS_AME_MST_SCH_DETAILS  "); 
						SQL.append("    WHERE office_id ="+reg_id+"  ");
						SQL.append("      "); 
						SQL.append("    )  ");  
						SQL.append("  )sch  ");
						SQL.append("LEFT outer JOIN  ");
						SQL.append("  (SELECT SUM(AM_EST_AMT) AS ameamt,  ");
						SQL.append("    OFFICE_ID,  ");
						SQL.append("    sch_sno  ");
						SQL.append("  FROM PMS_AME_TRN_ABSTRACT  ");
						SQL.append("  WHERE office_id ="+reg_id);
						SQL.append("  AND fin_year    ='"+fin_year_value+"'");
						SQL.append("  GROUP BY OFFICE_ID,  ");
						SQL.append("    sch_sno  ");
						SQL.append("  )ame  ");
						SQL.append(" ON ame.sch_sno=sch.sch_sno  ");						
						SQL.append(" LEFT outer  JOIN  ");
						SQL.append(" (SELECT SUM(BUDGET_EST_AMT) AS budamt	,  ");
						SQL.append("  OFFICE_ID,  ");
						SQL.append("  sch_sno  ");
						SQL.append("  FROM PMS_AME_TRN_BUDGET  ");
						SQL.append("   WHERE office_id ="+reg_id);
						SQL.append("   AND fin_year    ='"+fin_year_value+"'");
						SQL.append("   GROUP BY OFFICE_ID,  ");
						SQL.append("     sch_sno  ");
						SQL.append("   )bud  ");						
						SQL.append(" ON bud.sch_sno=sch.sch_sno  ");
						SQL.append(" LEFT OUTER JOIN  ");
						SQL.append(" ( SELECT ROUND(SUM(TOTAL_AMT) / 100000,2) AS wcamt , office_id,MONTH,YEAR,scheme_sno  ");
						SQL.append("    FROM PMS_DCB_WC_BILLING WHERE MONTH="+process_month+"  AND YEAR   ="+year2+"  AND office_id ="+reg_id+" GROUP BY office_id,YEAR,MONTH,scheme_sno  ");
						SQL.append(" ) wc  ");
						SQL.append(" ON sch.sch_sno=wc.scheme_sno   ");
						SQL.append("  LEFT OUTER JOIN (SELECT ROUND(SUM(amount)/ 100000,2) AS col ,ACCOUNTING_FOR_OFFICE_ID,  ");
						SQL.append("       cashbook_year,cashbook_month,SUB_LEDGER_CODE  ");
						SQL.append("     FROM FAS_RECEIPT_TRANSACTION a  ");
						SQL.append("     WHERE ACCOUNT_HEAD_CODE IN  ");
						SQL.append("       (SELECT ACCOUNT_HEAD_CODE FROM PMS_DCB_RECEIPT_ACCOUNT_MAP WHERE COLLECTION_TYPE IN (6,7,8,9)  ");
						SQL.append("       )AND cashbook_month ="+process_month+" AND cashbook_year  ="+year2+"  AND ACCOUNTING_FOR_OFFICE_ID   ="+reg_id+" AND receipt_no    IN  ");
						SQL.append("       (SELECT receipt_no   FROM FAS_RECEIPT_MASTER b  ");
						SQL.append("       WHERE SUB_LEDGER_TYPE_CODE= 14 AND cashbook_month="+process_month+" AND cashbook_year="+year2+"  ");
						SQL.append("       AND RECEIPT_STATUS='L'  ");
						SQL.append("       AND a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID  ");
						SQL.append("       ) GROUP BY ACCOUNTING_FOR_OFFICE_ID,cashbook_year,cashbook_month,SUB_LEDGER_CODE  ");
						SQL.append("     )collection  ");
						SQL.append("   ON   collection.SUB_LEDGER_CODE=sch.project_id  ");
						SQL.append("      LEFT OUTER JOIN (SELECT SUM(QTY_CONSUMED_NET) /1000 AS qty,office_id,MONTH,YEAR,sch_sno  ");
						SQL.append("     FROM PMS_DCB_TRN_MONTHLY_PR  ");
						SQL.append("     WHERE MONTH    ="+process_month+" AND YEAR="+year2+"   AND office_id  ="+reg_id+" AND METRE_SNO IN  ");
						SQL.append("       (SELECT METRE_SNO FROM pms_dcb_mst_beneficiary_metre WHERE meter_status = 'L'  ");
						SQL.append("       )  ");
						SQL.append("     GROUP BY office_id,YEAR,MONTH,sch_sno )pqty  ");
						SQL.append("   ON  pqty.sch_sno=sch.sch_sno  ");
						SQL.append("     LEFT JOIN (SELECT SUM(ACTUAL_EXP) / 100000 AS exp_amt, office_id,sch_sno,YEAR,  ");
						SQL.append("       MONTH FROM PMS_AME_TRN_SCH_ACT_EXP_ITEM WHERE MONTH="+process_month+" AND YEAR   ="+year2+" AND office_id ="+reg_id+" ");
						SQL.append("     GROUP BY office_id,YEAR,  ");
						SQL.append("       MONTH,sch_sno  ");
						SQL.append("     )totalexp  "); 
						SQL.append("   ON    totalexp.sch_sno=sch.sch_sno  ");
						SQL.append(" LEFT outer  JOIN  ");
						SQL.append("  (SELECT office_name,  ");
						SQL.append("    office_id  "); 
						SQL.append("  FROM com_mst_all_offices_view  ");
						SQL.append("  WHERE office_id ="+reg_id+" ");
						SQL.append("  )OFF  ");
						SQL.append("ON (1=1)");
						 System.out.println(SQL.toString());
 						xml=obj.resultXML(SQL.toString(),con,obj);
 						 System.out.println(SQL.toString());
					}
				}
				  PrintWriter pr = response.getWriter();
				  pr.write(xml); 
				  pr.flush();
				  pr.close();  
				
			
		}catch(Exception e)
		{
			System.out.println(e);
			//response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
	}
	
	 
}
