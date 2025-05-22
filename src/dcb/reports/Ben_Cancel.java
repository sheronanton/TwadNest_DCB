package dcb.reports;
import java.sql.*;
import java.util.ArrayList; 
import Servlets.ASSET.ASSET1.ASSETM.servlets.Controller;
public class Ben_Cancel 
{
	ArrayList<Ben_Details> cr=new ArrayList<Ben_Details>();
	Connection con;
	Controller obj=new Controller();;
	public Ben_Cancel()
	{
		
	}
	public ArrayList<Ben_Details> getList()
	{
		return cr;
	}
	
	public void setList(ArrayList<Ben_Details> list)
	{
		cr=list;
	}
	public ArrayList<Ben_Details> execute(String bensno,String pyear,String pmonth)
	{ 
		PreparedStatement ps=null; 
		try {  	
		  StringBuffer br=new StringBuffer("");
		  br.append(" select a.BENEFICIARY_NAME,a.beneficiary_sno,b.CB_MAINT_CHARGES,b.CB_CUR_YR_WC, ");
		  br.append(" b.CB_YESTER_YR_WC,b.CB_INT_CUR_YR_MAINT,b.CB_INT_AMT_WC from PMS_DCB_MST_BENEFICIARY a, ");
		  br.append(" PMS_DCB_TRN_CB_MONTHLY b where a.status='L' and b.beneficiary_sno=a.beneficiary_sno ");
		  br.append(" and a.office_id=b.office_id and b.fin_year="+pyear+" and b.month="+pmonth+" and a.beneficiary_sno="+bensno);
		  System.out.println(br.toString());
			try {
				con=obj.con(); 
				ps = con.prepareStatement(br.toString());
			} catch (Exception e) {
				 
				e.printStackTrace();
			}
		  ResultSet rs=ps.executeQuery(); 
		  while(rs.next())
		  {    
		 
		   Ben_Details ben=new Ben_Details();  
		   ben.setBen_name(rs.getString(1));
		   ben.setBen_sno(rs.getString(2));
		   ben.setCls_maint_charges(rs.getString(3));
		   ben.setCls_wc_charges(rs.getString(4));
		   ben.setCls_yester_year(rs.getString(5));
		   ben.setCls_int_charges(rs.getString(6));
		   cr.add(ben);  
		  }  
		  con.close();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
		
		return cr;
	}
}
