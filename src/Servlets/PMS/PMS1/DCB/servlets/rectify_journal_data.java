package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.types.Month;
public class rectify_journal_data extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
       
    public rectify_journal_data() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Controller obj = new Controller();
		response.setContentType(CONTENT_TYPE);
		PrintWriter pr = response.getWriter();
		Connection con = null;
		Statement stmt = null;
		String userid="";
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
		String xml = "<result>";
		String flag=obj.setValue("flag", request);
		String block_1=obj.setValue("block_1", request);
		String type=obj.setValue("type", request);
		System.out.println("DCB->rectify_journal_data->flag->" + flag);
		if (Integer.parseInt(flag)==1)
		{
			String cond="";
			if (!block_1.equalsIgnoreCase("0"))
				 cond=" and block_sno="+block_1;
			xml+=obj.resultXML("select BENEFICIARY_SNO,BENEFICIARY_NAME from PMS_DCB_MST_BENEFICIARY where BENEFICIARY_TYPE_ID="+type+" and status='L' and office_id="+Office_id+"  order by BENEFICIARY_NAME ",con,obj);
		}if (Integer.parseInt(flag)==2)
		{
			 Pumping_Value val=new Pumping_Value();
			 
		 	 String ben_sno=obj.setValue("ben_sno",request);
		 	 String rectmonth=obj.setValue("month",request);
		 	 String rectyear=obj.setValue("year",request);
		 	 
		 	 System.out.println(ben_sno);
		 	System.out.println(rectmonth);
		 	System.out.println(rectyear);
		 	
		 	 
		 	 StringBuffer sb=new StringBuffer();
		 	 sb.append(" select  f.beneficiary_name, d.sch_name, a.beneficiary_sno,a.metre_sno,a.scheme_sno,a.metre_location,b.QTY_CONSUMED_NET");
		 	 sb.append(" from PMS_DCB_MST_BENEFICIARY_METRE a join PMS_DCB_TRN_MONTHLY_PR b on b.beneficiary_sno=a.beneficiary_sno and "); 
		 	 sb.append(" b.sch_sno=a.scheme_sno and b.office_id=a.office_id and    a.beneficiary_sno=? "); 
		 	 sb.append(" and a.meter_status='L' and b.year=? and b.month=?  and a.metre_sno=b.metre_sno join pms_sch_master d on d.sch_sno= a.SCHEME_SNO  ");
		 	 sb.append(" join PMS_DCB_MST_BENEFICIARY f on f.beneficiary_sno=a.beneficiary_sno and f.status='L' ");
		 	 System.out.println(sb.toString());  
		 	 PreparedStatement ps=con.prepareStatement(sb.toString());
		 	 ps.setString(1,ben_sno );
		 	 ps.setString(2,rectyear );
		 	 ps.setString(3,rectmonth);
		 	 ResultSet rs=ps.executeQuery();
		 	 while (rs.next()) 
		 	 {
			 	 String tot_read=rs.getString("QTY_CONSUMED_NET");
			 	 String meter_sno=rs.getString("METRE_SNO");
			 	 String beneficiary_name=rs.getString("beneficiary_name");
			 	 String metre_location=rs.getString("metre_location");
			 	 String TARIFF_FLAG=obj.getValue("PMS_DCB_MST_BENEFICIARY_METRE","TARIFF_FLAG","where METRE_SNO="+ meter_sno+ " and BENEFICIARY_SNO="+ ben_sno+ " and Office_id="+Office_id+" and METER_STATUS='L'");
			 	 String scheme_sno=rs.getString("scheme_sno");
			 	 String old_amount="0";
			 	double new_amount =0.0;
			 	 if (TARIFF_FLAG.equals("S")) 
			 	 {  
			 		old_amount=obj.getValue("PMS_DCB_WC_BILLING", "TOTAL_AMT", " where SCHEME_SNO="+scheme_sno+" and METRE_SNO is null and BENEFICIARY_SNO="+ ben_sno+ " and Office_id="+Office_id+" and month="+rectmonth+" and year="+rectyear);
			 	//	new_amount =val.Value(Double.parseDouble(tot_read),TARIFF_FLAG,ben_sno,Office_id,meter_sno,rectmonth,rectyear);
			 		new_amount =val.value_scheme(tot_read,TARIFF_FLAG,   ben_sno,  Office_id,   scheme_sno,   rectmonth,   rectyear) ;
			 	 }
			 	 else   
			 	 { 
			 		old_amount=obj.getValue("PMS_DCB_WC_BILLING", "TOTAL_AMT", " where SCHEME_SNO="+scheme_sno+" and METRE_SNO="+meter_sno+" and  BENEFICIARY_SNO="+ ben_sno+ " and Office_id="+Office_id+" and month="+rectmonth+" and year="+rectyear);
			 		new_amount=val.Value(Double.parseDouble(tot_read),TARIFF_FLAG,ben_sno,Office_id,meter_sno,rectmonth,rectyear);
			 	 }
				 
				 String sch_name=rs.getString("sch_name");			
				 xml+=obj.generateXML("beneficiary_name",beneficiary_name, 0, obj);
				 xml+=obj.generateXML("sch_name",sch_name, 0, obj);				 
				 xml+=obj.generateXML("tot_read",tot_read, 0, obj);
				 xml+=obj.generateXML("metre_location",metre_location, 0, obj);				 
				 xml+="<new_amount>"+new_amount+"</new_amount>";
				 xml+="<old_amount>"+old_amount+"</old_amount>"; 
			 } 
		 	 
		 }  
		 xml += "</result>";	  
	 	 pr.write(xml);
	 	 pr.flush();
	 	 pr.close();
		obj.conClose(con);
		}catch (Exception e)  
		{
			System.out.println( e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
