package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class GpfAuthMstModel {
	private static Connection connection=null;	
	
	public static Connection getDatabaseConnection() {
		return connection;
	}
	public  static synchronized void openConnection(){
		try {
			LoadDriver driver=new LoadDriver();
            connection=driver.getConnection();

					
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static  synchronized void closeConnection(){
		try {
			if(connection!=null){
				connection.close();
				connection=null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static Connection getConnectionObject(){
		return GpfAuthMstModel.getDatabaseConnection();	
	}
	
	public static GpfAuthMstBean getGpfAuthMstBean(int gpfNo){
		GpfAuthMstBean gpfAuthMstBean=new GpfAuthMstBean();
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn1.prepareStatement(" select GPF_NO,SETTLEMENT_REASON,to_char(RELIEVAL_DATE,'dd/mm/yyyy') as RELIEVAL_DATE,to_char(INT_TOBE_CALC_DATE,'dd/mm/yyyy') as INT_TOBE_CALC_DATE ," +
					" PROCESS_FLOW_STATUS_ID,to_char(UPDATED_DATE,'dd/mm/yyyy') as UPDATED_DATE ,UPDATED_BY_USER_ID" +
					" from HRM_GPF_INT_AUTH_MST where GPF_NO=?");
			ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next()){
            	gpfAuthMstBean.setGpfNo(rs.getInt(1));
            	gpfAuthMstBean.setSettlementReason(rs.getString(2));
            	gpfAuthMstBean.setRelievalDate(rs.getString(3));
            	gpfAuthMstBean.setIntToBeCalcDate(rs.getString(4));
            	gpfAuthMstBean.setStatusId(rs.getString(5));
            	
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		
		return gpfAuthMstBean;
	}
	public static boolean setGpfAuthMstBean(GpfAuthMstBean gpfAuthMstBean){
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;		
		System.out.println("came 88");
		boolean resultStatus=false;
		
		
		try {
			ps=conn1.prepareStatement(" update HRM_GPF_INT_AUTH_MST " +
					" set GPF_NO=?,SETTLEMENT_REASON=?,RELIEVAL_DATE=to_date(?,'dd-MM-yyyy'),INT_TOBE_CALC_DATE=to_date(?,'dd-MM-yyyy')," +
					" PROCESS_FLOW_STATUS_ID=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=?" +
					" where  GPF_NO=?");
			ps.setInt(1, gpfAuthMstBean.getGpfNo());
			ps.setString(2, gpfAuthMstBean.getSettlementReason());
			ps.setString(3, gpfAuthMstBean.getRelievalDate());
			ps.setString(4, gpfAuthMstBean.getIntToBeCalcDate());
			ps.setString(5, gpfAuthMstBean.getStatusId());
			ps.setTimestamp(6, gpfAuthMstBean.getUpdatedDate());
			ps.setString(7, gpfAuthMstBean.getUpdateByUserId());
			ps.setInt(8, gpfAuthMstBean.getGpfNo());
            int hh=ps.executeUpdate();
            if(hh!=0){
            	resultStatus=true;
            }            
            System.out.println("came here");
            ps.close();
            
            if(gpfAuthMstBean.getStatusId().equalsIgnoreCase("FR")){
            	updateGpfAuthTrn(gpfAuthMstBean);
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return resultStatus;
	}
	public static boolean deleteGpfAuthMstBean(int gpfNo){
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		boolean resultStatus=false;
		String processStatus=GpfAuthMstModel.getGpfAuthMstBean(gpfNo).getStatusId().trim();
		if(!processStatus.equals("FR")){
			try {
				ps=conn1.prepareStatement("delete from  HRM_GPF_INT_AUTH_MST where GPF_NO=?"   );				     
				ps.setInt(1,gpfNo);
	            int hh=ps.executeUpdate();
	            if(hh!=0){
	            	resultStatus=true;
	            }
	            
	            System.out.println("came here");
	            ps.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				
				try {				
					if(ps!=null){
						ps.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		return resultStatus;
	}
	public static boolean insertGpfAuthMstBean(GpfAuthMstBean gpfAuthMstBean){
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;		
		
		boolean resultStatus=false;
		
		try {
			ps=conn1.prepareStatement("insert into HRM_GPF_INT_AUTH_MST "    +
					" (GPF_NO,SETTLEMENT_REASON,RELIEVAL_DATE,INT_TOBE_CALC_DATE," +
					" PROCESS_FLOW_STATUS_ID,UPDATED_DATE,UPDATED_BY_USER_ID)" +
					" values(?,?,to_date(?,'dd-MM-yyyy'),to_date(?,'dd-MM-yyyy'),?,?,?)");
			ps.setInt(1, gpfAuthMstBean.getGpfNo());
			ps.setString(2, gpfAuthMstBean.getSettlementReason());
			ps.setString(3, gpfAuthMstBean.getRelievalDate());
			ps.setString(4, gpfAuthMstBean.getIntToBeCalcDate());
			ps.setString(5, gpfAuthMstBean.getStatusId());
			ps.setTimestamp(6, gpfAuthMstBean.getUpdatedDate());
			ps.setString(7, gpfAuthMstBean.getUpdateByUserId());  
			String testquery="insert into HRM_GPF_INT_AUTH_MST "    +
					" (GPF_NO,SETTLEMENT_REASON,RELIEVAL_DATE,INT_TOBE_CALC_DATE," +
					" PROCESS_FLOW_STATUS_ID,UPDATED_DATE,UPDATED_BY_USER_ID)" +
					" values("+gpfAuthMstBean.getGpfNo()+","+
					gpfAuthMstBean.getSettlementReason()+",to_date("+gpfAuthMstBean.getRelievalDate()+",'dd-MM-yyyy')," +
					"to_date("+gpfAuthMstBean.getIntToBeCalcDate()+",'dd-MM-yyyy'),"+
					gpfAuthMstBean.getStatusId()+","+
					gpfAuthMstBean.getUpdatedDate()+","+
					gpfAuthMstBean.getUpdateByUserId()+")";
			System.out.println("check query=======>"+testquery);
            System.out.println();
            int hh=ps.executeUpdate();
            //int hh=0;
            if(hh!=0){
            	resultStatus=true;
            }
            
            System.out.println("came here");
            ps.close();
            
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return resultStatus;
	}
	public static boolean updateGpfAuthTrn(GpfAuthMstBean gpfAuthMstBean){
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;		
		
		boolean resultStatus=false;
		List<String> financeYrList=GpfAuthMstModel.financialYearArray(gpfAuthMstBean);
		System.out.println("Size=  "+financeYrList.size());
		int u=0;
		for (u=0 ; u<financeYrList.size(); u++) {
			String financeYr = (String) financeYrList.get(u);
			int k=u;
			k=k+1;
			
			 System.out.println("financeYr.....b4 trn updation"+financeYr);
			
			try {
				ps=conn1.prepareStatement("insert into HRM_GPF_INT_AUTH_TRN "    +
						" (GPF_NO,AUTH_SLNO,FINANCIAL_YEAR,INT_AMT_AUTHORISED_REG," +
						" PROCESS_FLOW_STATUS_ID,UPDATED_DATE,UPDATED_BY_USER_ID,PROC_GENERATED,NOTE_GENERATED,INT_AMT_AUTHORISED_IMP,INT_CALCULATED)" +
						" values(?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, gpfAuthMstBean.getGpfNo());
				ps.setInt(2, k);
				ps.setString(3, financeYr);
				ps.setInt(4, 0);
				ps.setString(5, "CR");
				ps.setTimestamp(6, gpfAuthMstBean.getUpdatedDate());
				ps.setString(7, gpfAuthMstBean.getUpdateByUserId());
				ps.setString(8, "N");
				ps.setString(9, "N");
				ps.setInt(10, 0);
				ps.setString(11, "N");
	            int hh=ps.executeUpdate();
	            if(hh!=0){
	            	resultStatus=true;
	            }
	            
	            System.out.println("came here");
	            ps.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try {
					if(rs!=null){
						rs.close();
					}				
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {				
					if(ps!=null){
						ps.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		
		return resultStatus;
	}
	public static List<String> financialYearArray(GpfAuthMstBean gpfAuthMstBean){		
		List<String> lst=new ArrayList<String>();
		
		String currFinYear=GpfAuthMstModel.getCurrentFinanceYear();
		String []tmpYr=gpfAuthMstBean.getRelievalDate().split("/");
		String []tmpCurrFin=currFinYear.split("-");
		
		int startyear=0;
		int endYear=0;
		
		int mnthInt=0;
		int yrInt=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		System.out.println("Year=  ...."+yrInt);
		System.out.println("month=  ...."+mnthInt);
	  // if(currFinYear==(tmpCurrFin[0]-1))
		if(mnthInt>=1 && mnthInt<=3){
			endYear=(yrInt-1);
			System.out.println("got here 1");
		}
		else if(mnthInt>=4 && mnthInt<=12){
			System.out.println("got here 2");
			endYear=(yrInt);
		}
		System.out.println("Year434=  ...."+endYear);
		try {
			startyear=Integer.parseInt(tmpCurrFin[0].trim());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(startyear>endYear){
			lst.add(startyear+"-"+(startyear+1));
		}else{
			if (startyear!=0) {
				for (int i = startyear; i <= endYear; i++) {
					lst.add(i+"-"+(i+1));
				}
			}
			
		}
		System.out.println(" startyear= "+startyear+" endYear= "+endYear);
		for(String x : lst)
		{
			System.out.println("x Values ==  "+x);
		}
		
		return lst;		
	}
	public static String getCurrentFinanceYear(){
		String resFinYear="";
		
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		String tempFinyear="";
		int closeYr=0;
		String []splStr=null;
		
		try {			
			ps=conn1.prepareStatement("select SLIP_ISSUED_FIN_YEAR from HRM_GPF_LAST_SLIP_ISSUED");				
            rs=ps.executeQuery();
            if(rs.next()){
            	splStr=rs.getString(1).trim().split("-");
            	if(splStr.length==2){
            		try {
            			closeYr=Integer.parseInt(splStr[1].trim());
					} catch (Exception e) {
						// TODO: handle exception
					}
            	}
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		resFinYear=closeYr+"-"+(closeYr+1);
		System.out.println("getCurrentFinanceYear()::current finance year= "+resFinYear);
		
		
		return resFinYear;
	}
	
	public static String getSelectedInterestYear(GpfAuthMstBean gpfAuthMstBean){
		String selectyear="";
		
		String []tmpYr=gpfAuthMstBean.getIntToBeCalcDate().split("/");
		int mnthInt=0;
		int yrInt=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
				selectyear=tmpYr[2];
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return selectyear;
	}
	public static String getSelectedInterestMonth(GpfAuthMstBean gpfAuthMstBean){
		String selectMonth="";
		
		String []tmpYr=gpfAuthMstBean.getIntToBeCalcDate().split("/");
		int mnthInt=0;
		int yrInt=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
				selectMonth=(mnthInt+"").trim();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return selectMonth;			
	}
	
	
	public static boolean checkGpf(int gpfNo ){
		System.out.println("checkGpf() entry");
		boolean bl=false;
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn1.prepareStatement("select gpf_no from HRM_MST_EMPLOYEES where gpf_no=?");
            ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next())
            {
            		bl=true;
            		System.out.println("checkSettlementGpf() value "+bl +" :gpf value  exist");
            }
            else{
            	System.out.println("checkSettlementGpf() value "+bl +" :gpf value doesnt exist");
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return bl;
	}
	
	public static String xmlEmployee(int gpfNo){
		String xml="";
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn1.prepareStatement("SELECT e.EMPLOYEE_NAME,to_char(e.DATE_OF_BIRTH,'dd/mm/yyyy'),e.GPF_NO,f.DESIGNATION,e.EMPLOYEE_ID FROM HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING d,HRM_MST_DESIGNATIONS f WHERE e.EMPLOYEE_ID=d.EMPLOYEE_ID AND d.DESIGNATION_ID=f.DESIGNATION_ID AND e.GPF_NO=?");
            ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next()) {
              
                xml+="<emp_name>"+rs.getString(1)+" </emp_name>";
                xml+="<date_of_birth>"+rs.getString(2)+" </date_of_birth>";
                xml+="<gpf_no>"+rs.getString(3)+" </gpf_no>";
                xml+="<designation>"+rs.getString(4)+" </designation>";
                xml+="<emp_id>"+rs.getString(5)+" </emp_id>";
                
                }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println("xmlGetBasicData()="+xml);
		return xml;
	}
	
	
	public static String xmlRelieveDetails(GpfAuthMstBean gpfAuthMstBean){
		String xml="";
		xml+="<relieve_status>"+gpfAuthMstBean.getSettlementReason()+"</relieve_status>";
		xml+="<relieve_date>"+gpfAuthMstBean.getRelievalDate()+"</relieve_date>";	
		xml=xml+"<listYear>"+GpfAuthMstModel.xmlLoadListYear(gpfAuthMstBean.getRelievalDate())+"</listYear>";
		xml=xml+"<selectedYear>"+GpfAuthMstModel.getSelectedInterestYear(gpfAuthMstBean)+"</selectedYear>";
		xml=xml+"<listMonth>"+GpfAuthMstModel.xmlLoadListMonth(gpfAuthMstBean.getRelievalDate(), GpfAuthMstModel.getSelectedInterestYear(gpfAuthMstBean))+"</listMonth>"; 
		xml=xml+"<selectedMonth>"+GpfAuthMstModel.getSelectedInterestMonth(gpfAuthMstBean)+"</selectedMonth>";
		xml=xml+"<int_tobe_calc_date>"+gpfAuthMstBean.getIntToBeCalcDate()+"</int_tobe_calc_date>";
		return xml;
	}
	public static String xmlFirstRelieveDetails(int gpfNo){
		String xml="";
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String empStatusIdStr="";
		String dateOfBirthStr="";
		String relievedDate="";
		
		try {
			ps=conn1.prepareStatement("select A.EMPLOYEE_STATUS_ID,to_char(B.DATE_OF_BIRTH,'dd/mm/yyyy') as DATE_OF_BIRTH,to_char(A.DATE_EFFECTIVE_FROM,'dd/mm/yyyy') as DATE_EFFECTIVE_FROM " +
					" from HRM_EMP_CURRENT_POSTING A ,hrm_mst_employees B where " +					
					" B.GPF_NO =? and B.employee_id = A.employee_id");
            ps.setInt(1,gpfNo);
            rs=ps.executeQuery();
            if(rs.next()) {
            	empStatusIdStr=rs.getString(1).trim();
            	dateOfBirthStr=rs.getString(2).trim();  
            	//relievedDate=rs.getString(3).trim();
            	System.out.print("relievedDate"+relievedDate);
            	System.out.print("relievedDate"+relievedDate);
           }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		try {
			ps=conn1.prepareStatement("SELECT TO_CHAR(A.RETIREDATE,'dd/mm/yyyy') AS RETIREDATE " +
										"FROM ALLRETIREMENTVIEW A, " +
										"  HRM_MST_EMPLOYEES B " +
										"WHERE A.EMPLOYEE_ID = b.employee_id " +
										"AND b.gpf_no        =? " +
										"UNION " +
										"SELECT TO_CHAR(DATE_EFFECTIVE_FROM,'dd/mm/yyyy') AS RETIREDATE " +
										"FROM HRM_EMP_CURRENT_POSTING c, " +
										"  HRM_MST_EMPLOYEES d " +
										"WHERE c.EMPLOYEE_ID     = d.employee_id " +
										"AND d.employee_id       =? " +
										"AND employee_status_id IN ('SAN','DTH','RES','VRS','MEV','CMR','DIS')" +
										"order by RETIREDATE desc");
			
			 ps.setInt(1,gpfNo);
			 ps.setInt(2,gpfNo);
            rs=ps.executeQuery();
            if(rs.next()) {
            	relievedDate=rs.getString(1).trim(); 
            	 System.out.println("relievedDate------????????------>---"+relievedDate);
           }
          
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
System.out.println("empStatusIdStr   ===== "+empStatusIdStr);
		if(empStatusIdStr.equalsIgnoreCase("SAN")){
			if(checkRelieveDateFallsInAfterLastFinancialYear(relievedDate)){
				xml+="<firstRelieveStatus>SAN</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}else{
				
				System.out.println("in else ===================>");
				xml+="<firstRelieveStatus>FINISHED</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}
			
		}else if(empStatusIdStr.equalsIgnoreCase("DTH")){
			if(checkRelieveDateFallsInAfterLastFinancialYear(relievedDate)){
				xml+="<firstRelieveStatus>DTH</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";
			}else{
				xml+="<firstRelieveStatus>FINISHED</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}
				
		}else if(empStatusIdStr.equalsIgnoreCase("VRS")){
			if(checkRelieveDateFallsInAfterLastFinancialYear(relievedDate)){
				xml+="<firstRelieveStatus>VRS</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}else{
				xml+="<firstRelieveStatus>FINISHED</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}
			
		}
		else if(empStatusIdStr.equalsIgnoreCase("MEV")){
			if(checkRelieveDateFallsInAfterLastFinancialYear(relievedDate)){
				xml+="<firstRelieveStatus>MEV</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}else{
				xml+="<firstRelieveStatus>FINISHED</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}
		}
		else if(empStatusIdStr.equalsIgnoreCase("CMR")){
			if(checkRelieveDateFallsInAfterLastFinancialYear(relievedDate)){
				xml+="<firstRelieveStatus>CMR</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}else{
				xml+="<firstRelieveStatus>FINISHED</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}
		}
		else {
			String futureRetiredDate="";
			java.sql.Date sqlDate=null;
			
			try {
				ps=conn1.prepareStatement(" select to_char(A.RETIREDATE,'dd/mm/yyyy') as RETIREDATE  from ALLRETIREMENTVIEW A,HRM_MST_EMPLOYEES B" +
						" where A.EMPLOYEE_ID = b.employee_id and b.gpf_no=?");
				
	            ps.setInt(1,gpfNo);
	            
	            rs=ps.executeQuery();
	            if(rs.next()) {
	            	futureRetiredDate=rs.getString(1).trim(); 
	            	
	           }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				try {
					if(rs!=null){
						rs.close();
					}				
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {				
					if(ps!=null){
						ps.close();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			String STR_FORMAT="dd/MM/yyyy";
			SimpleDateFormat smpFormat=new SimpleDateFormat(STR_FORMAT);
			Calendar c1= Calendar.getInstance();
			System.out.println("today is ="+smpFormat.format(c1.getTime()));
			String todaysDate=smpFormat.format(c1.getTime());
			
			String []tmpArray=futureRetiredDate.split("/");
			String []todayArray=todaysDate.split("/");
			
			String elseRelieveDate="",slipyear="";
			
			int todayMonth=-1,todayYear=-2,relievedMonth=-3,relievedyear=-4;
			try {
				todayMonth=Integer.parseInt(todayArray[1].trim());
				todayYear=Integer.parseInt(todayArray[2].trim());
				
				relievedMonth=Integer.parseInt(tmpArray[1].trim());
				relievedyear=Integer.parseInt(tmpArray[2].trim());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try{  ps=conn1.prepareStatement("select to_char(SLIP_ISSUED_FIN_YEAR) as slip_year from HRM_GPF_LAST_SLIP_ISSUED");
			
            rs=ps.executeQuery();
            if(rs.next())
            {
           	 slipyear=rs.getString("slip_year").substring(5,8);
           	 System.out.println("slipyear------------>"+slipyear);
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		    // Get Date 1
		    Date d1 = null;
		    Date d3= null;Date d2= null;
		    String slips="01/04/"+slipyear;
			try {
				d1 = df.parse(slips);//slip
		   d3 = df.parse(relievedDate);//retire
		    // Get Date 2
		    d2 = df.parse(todaysDate);//current
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   System.out.println("Finn   "+(d1.equals(d3) ||d3.after(d1) &&  d3.before(d2) || d3.equals(d2)));
		    
			//if((todayMonth==relievedMonth) && (todayYear==relievedyear) ){
		   if (d1.equals(d3) ||d3.after(d1) &&  d3.before(d2) || d3.equals(d2) || ( (d2.getMonth())==d3.getMonth() && d2.getYear()==d3.getYear()) ){
				xml+="<firstRelieveStatus>ThisMonthSAN</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+futureRetiredDate+"</firstRelieveDate>";	
			}else{
				xml+="<firstRelieveStatus>NONE</firstRelieveStatus>";
				xml+="<firstRelieveDate>"+relievedDate+"</firstRelieveDate>";	
			}
		}
		return xml;
	}
	
	
	
	
	/* 
	 * To get last closing balance details 
	 * To get Added Subscription credits Details
	 * To get Added Subscription Debits Details
	 * To get Balance has on date.
	 */
	
	public static String xmlBalanceDetails(int gpfNo){
		String xml="";
		Connection conn1=GpfAuthMstModel.getDatabaseConnection();
		Connection con=GpfAuthMstModel.getDatabaseConnection();
		CallableStatement callableStatement = null;		
		ResultSet rs=null;
		PreparedStatement ps=null,ps1=null;
	    Statement st=null;
	    Connection connection=null;
	    ResultSet results=null;
	    ResultSet rs1=null;
	    ResultSet results2=null;
		
		
		
		
		
		
		
		
		int v1_excess=0,v2_excess=0,v3_excess=0,miss_amt_excess=0,sum_interest_excess=0;
		  int ImpReg=0,Imp2003=0,sum_interest=0,total=0,v1=0,v2=0,v3=0,emp_id=0,miss_amt=0,impreg_cr=0,impreg_db=0,imp2003_cr=0,imp2003_db=0,fimpreg=0,fimp2003=0;
		  int with_amount_DB=0;
		  int with_amount_CR=0;
		  String IMP2003_DB=null;
		  String IMP2003_CR=null;
		  String IMPREG_CR=null;
		  String IMPREG_DB=null;
		  String Ref_Amt=null;
		  String slip_fin_year="",gpf_no="",emp_name="",month_year="",type_trans_value="";
		  int close_bal=0,ac_month=0,ac_year=0,ref_amount=0,with_amount=0,arr_amount=0,sub_total=0,ref_total=0,with_total=0,with_total_CR=0,with_total_DB=0,with_total_null=0,with_amount_null=0;
		  String months[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
		  int[] sub_amount;
		  String[] relmonth_year;
		
		
		try {
			
			
			ps=con.prepareStatement("select SLIP_ISSUED_FIN_YEAR from HRM_GPF_LAST_SLIP_ISSUED" );
	          rs=ps.executeQuery();
	               if(rs.next()) 
	               {
	                  slip_fin_year=rs.getString(1);               
	               }
	          rs.close();
	          ps.close();
	         // System.out.println("Fin Year------------"+slip_fin_year);
	          
	          ps=con.prepareStatement("select CLOSING_BALANCE_REGULAR from HRM_GPF_REGULAR_CB where GPF_NO=? and FINANCIAL_YEAR=?" );
	          ps.setInt(1,gpfNo);
	          ps.setString(2,slip_fin_year);
	          rs=ps.executeQuery();
	               if(rs.next()) 
	               {
	            	   close_bal=rs.getInt(1);               
	               }
	          rs.close();
	          ps.close();
	          xml+="<Last_Slip_Closing_Balance>"+close_bal+" </Last_Slip_Closing_Balance>";
	          
	          
	          gpf_no=gpfNo+"";
	          
	          
	          
	          String slip[]=slip_fin_year.split("-");
	      	ac_year=Integer.parseInt(slip[1]);
	      	ac_month=3;
	      	
	      	java.util.Date d=new java.util.Date();
	        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        int curr_month=d.getMonth()+1;
	        int curr_year=d.getYear()+1900;
	      	
	      	do
	      	{
	      			if(ac_month==12)
	      			{
	      				ac_month=1;
	      				ac_year=ac_year+1;
	      			}
	      			else
	      				ac_month++;	
	      			System.out.println("ac_month---------------"+ac_month+'-'+ac_year);
	      			//st=con.createStatement(ResultSet.SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
	      			ps=con.prepareStatement("select sum(sub_amount+arr_amount) as sub_amount,rel_year,rel_month FROM HRM_GPF_SUBSCRIPTIONNEW_temp where gpf_no=? and process_flow_id='FR' and AC_MONTH=? and AC_YEAR=? group by rel_year, rel_month order by rel_year,rel_month", ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
	      			ps.setString(1,gpf_no);
	      			ps.setInt(2,ac_month);
	      			ps.setInt(3,ac_year);
	      			rs=ps.executeQuery();
	      			int i=0,size=0;
	      			if(rs.last())
	      				size=rs.getRow();
	      			if(size==0)
	      				size=1;
	      			sub_amount=new int[size];
	      			relmonth_year=new String[size];
	      		//	System.out.println("count="+size);
	      			try{
	      				rs.beforeFirst();
	      			     while(rs.next()){
	      			    	int subamt=rs.getInt("sub_amount");
	      			    	System.out.println("subamt******"+subamt);
	      			  	   	sub_amount[i]=subamt;
	      			  	  sub_total=sub_total+sub_amount[i];
	      			  	  if((months[rs.getInt("rel_month")-1]+"-"+rs.getInt("rel_year")).length()<5)
	      			  	 {
	      			  			ps1=con.prepareStatement("select Rel_Month,Rel_Year FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=?" );
	      						ps1.setString(1,gpf_no);
	      						ps1.setInt(2,ac_month);
	      						ps1.setInt(3,ac_year);
	      						rs1=ps1.executeQuery();
	      			    		if(rs1.next()) 
	      			     		{
	      			     			if((months[rs1.getInt("rel_month")-1]+"-"+rs1.getInt("rel_year")).length()<5)
	      			  					relmonth_year[i]="";
	      			  				else
	      			  					relmonth_year[i]=months[rs1.getInt("rel_month")-1]+"-"+rs1.getInt("rel_year");
	      			     		}
	      			     		else
	      			  					relmonth_year[i]="";
	      			  		}
	      			  	 	else{
	      			  	 		relmonth_year[i]=months[rs.getInt("rel_month")-1]+"-"+rs.getInt("rel_year");
	      			  			}
	      			  	   i++;			     
	      			     }
	      			     if(i==0){
	      			     ps1=con.prepareStatement("select Rel_Month,Rel_Year FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=?" );
	      						ps1.setString(1,gpf_no);
	      						ps1.setInt(2,ac_month);
	      						ps1.setInt(3,ac_year);
	      						rs1=ps1.executeQuery();
	      			    		if(rs1.next()) 
	      			     		{
	      			     			if((months[rs1.getInt("rel_month")-1]+"-"+rs1.getInt("rel_year")).length()<5)
	      			  					relmonth_year[i]="";
	      			  				else
	      			  					relmonth_year[i]=months[rs1.getInt("rel_month")-1]+"-"+rs1.getInt("rel_year");
	      			     		}
	      			     		else
	      			  					relmonth_year[i]="";
	      			  			i++;
	      			     }
	      			}
	      			catch(Exception e){
	      				System.out.println("Error in "+e);
	      			}
	      			
	      			rs.close();
	      			ps.close();
	      			//System.out.println("ref_amount------------"+arr_amount);
	      			ps=con.prepareStatement("select nvl(SUM(REF_AMOUNT),0) FROM HRM_GPF_SUBSCRIPTIONNEW_temp where gpf_no=? and AC_MONTH=? and AC_YEAR=?   and process_flow_id='FR' " );
	      			ps.setString(1,gpf_no);
	      			ps.setInt(2,ac_month);
	      			ps.setInt(3,ac_year);
	      			rs=ps.executeQuery();
	      			     if(rs.next()) 
	      			     {
	      			  	   ref_amount=rs.getInt(1);
	      			     
	      			     }
	      			rs.close();
	      			ps.close();
	      			ps=con.prepareStatement("select CASE WHEN type_trans='CR' and withdrwal_amount<>0 THEN 'CR' when type_trans='DB' and withdrwal_amount<>0 then 'DB'  when type_trans is null and withdrwal_amount<>0  then 'DB'  when type_trans is null and withdrwal_amount=' '  then ' '  ELSE ' ' END type_trans FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=? " );
	      			ps.setString(1,gpf_no);
	      			ps.setInt(2,ac_month);
	      			ps.setInt(3,ac_year);
	      			rs=ps.executeQuery();
	      			     if(rs.next()) 
	      			     {
	      			  	   type_trans_value=rs.getString(1);
	      			     
	      			     }
	      			rs.close();
	      			ps.close();		
	      			
	      			
	      			ps=con.prepareStatement("select nvl(SUM(withdrwal_amount),0)FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=? " );
	      			ps.setString(1,gpf_no);
	      			ps.setInt(2,ac_month);
	      			ps.setInt(3,ac_year);
	      			rs=ps.executeQuery();
	      			     if(rs.next()) 
	      			     {
	      			  	   with_amount=rs.getInt(1);
	      			     
	      			     }
	      			rs.close();
	      			ps.close();		
	      			
	      			
	      			ps=con.prepareStatement("select nvl(SUM(withdrwal_amount),0) FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=? and (type_trans is null or type_trans='DB') " );
	      			ps.setString(1,gpf_no);
	      			ps.setInt(2,ac_month);
	      			ps.setInt(3,ac_year);
	      			rs=ps.executeQuery();
	      			     if(rs.next()) 
	      			     {
	      			  	   with_amount_DB=rs.getInt(1);
	      			     
	      			     }
	      			rs.close();
	      			ps.close();		
	      			
	      			
	      			
	      			
	      			
	      				
	      			
	      			ps=con.prepareStatement("select nvl(SUM(withdrwal_amount),0)FROM HRM_GPF_WITHDRAWALNEW where gpf_no=? and AC_MONTH=? and AC_YEAR=? and type_trans='CR'" );
	      			ps.setString(1,gpf_no);
	      			ps.setInt(2,ac_month);
	      			ps.setInt(3,ac_year);
	      			rs=ps.executeQuery();
	      			     if(rs.next()) 
	      			     {
	      			  	   with_amount_CR=rs.getInt(1);
	      			     
	      			     }
	      			rs.close();
	      			ps.close();		
	      			
	      		
	      			
	      			
	      			month_year=months[ac_month-1]+"-"+ac_year;		
	      			//sub_total=sub_total+sub_amount;
	      			ref_total=ref_total+ref_amount;
	      			type_trans_value=type_trans_value;
	      			
	      				with_total_CR=with_total_CR+with_amount_CR;
	      				with_total_DB=with_total_DB+with_amount_DB;
	      				with_total=with_total_DB-with_total_CR;
	      			
	      				
	      			
	      			
	      				
	      					
	                        	
	      	}while(!(ac_month==curr_month&&ac_year==curr_year));
	      	
	          
	          
	          
	          
	          
	          
	          
	          
	          
	          
	          
	          int gpfNum=gpfNo;
	          int beagnYear = 0;
	          int beagnYear1 = 0;
	          int endYear = 0;
	          int endYear1 = 0;
	          String[] splitYear = slip_fin_year.split("-");
	         
	          beagnYear=Integer.parseInt(splitYear[0]);
	          beagnYear1=beagnYear+1;
	         
	          endYear=Integer.parseInt(splitYear[1]);
	          endYear1=endYear+1;
			 
			  
			  String fin_year = beagnYear1+"-"+endYear1;
	          System.out.println("fin_year==="+fin_year);
	          String sql = "";
	          sql = "{call HRM_GPF_IMPOUND_BAL_VIEW_NEW(?,?,?,?)}";
				callableStatement = con.prepareCall(sql);          
	          callableStatement.setInt(1,gpfNum);
	          callableStatement.setString(2,fin_year);
	          callableStatement.setInt(3,beagnYear1);
	          callableStatement.setInt(4,endYear1);
	          callableStatement.executeUpdate();
	          ps=con.prepareStatement("SELECT GPF_NO, " +
	        		  "  FINANCIAL_YEAR, " +        		  
	        		  "  IMP2003_CR, " +
	        		  "  IMP2003_DB, " +
	        		  "  IMPREG_CR, " +
	        		  "  IMPREG_DB, " +
	        		  "  IMP2003_OB, " +
	        		  "  IMPREG_OB, " +
	        		  "  IMP2003_CB, " +
	        		  "  IMPREG_CB " +
	        		  "FROM HRM_GPF_IMPOUND_BAL_TMP " +
	        		  "WHERE GPF_NO      =? " +
	        		  "AND FINANCIAL_YEAR=?"
					);          
	    		ps.setString(1,gpf_no);
	    		ps.setString(2,fin_year);
	    		rs=ps.executeQuery();
	    		     if(rs.next()) 
	    		     {
	    		  	   ImpReg=rs.getInt("IMPREG_OB");
	    		  	   System.out.println("ImpReg="+ImpReg);
	    		       impreg_cr=rs.getInt("IMPREG_CR");
	    		       impreg_db=rs.getInt("IMPREG_db");
	    		       fimpreg=rs.getInt("IMPREG_CB");
	    		       Imp2003=rs.getInt("IMP2003_OB");
	    		       fimp2003=rs.getInt("IMP2003_CB");
	    		       IMP2003_CR=rs.getString("IMP2003_CR");
	    		       IMP2003_DB=rs.getString("IMP2003_DB");
	    		     }    		     
	    		rs.close();
	    		ps.close();
	    		
	    		xml+="<Impound_Regular>"+ImpReg+" </Impound_Regular>";	          
	          
	          
	          
	          
	          
	          
	          ArrayList<String> impound=new ArrayList<String>();
	        String impamt=null;
	        String Query="SELECT NVL(a.IMP2003_CR,0) AS IMP2003_CR, " +
	        "  NVL(a.IMPREG_CR,0)       AS IMPREG_CR, " +
	        "  NVL(a.IMPREG_DB,0)       AS IMPREG_DB, " +
	        "  NVL(a.IMP2003_DB,0)      AS IMP2003_DB, " +

	        "  b.ac_month  as ac_month," +
	        "  b.type_trans  as type_trans,"+
	        "  b.impound_type  as impound_type,"+
	        "  b.ac_year  as ac_year" +
	        "FROM HRM_GPF_IMPOUND_BAL_TMP a, " +
	        "  HRM_GPF_impound_disbnew b " +
	        "WHERE a.gpf_no      =b.gpf_no  and b.Process_Flow_Id='FR' " +
	        "AND a.gpf_no        =? " +
	        "AND a.financial_year=?";
	        //System.out.println("Query"+Query);
	        			ps=con.prepareStatement("SELECT NVL(a.IMP2003_CR,0) AS IMP2003_CR, " +
	        "  NVL(a.IMPREG_CR,0)       AS IMPREG_CR, " +
	        "  NVL(a.IMPREG_DB,0)       AS IMPREG_DB, " +
	        "  NVL(a.IMP2003_DB,0)      AS IMP2003_DB, " +

	        "  b.ac_month  as ac_month," +
	        "  b.type_trans  as type_trans,"+
	        "  b.impound_type  as impound_type,"+
	        "  b.ac_year  as ac_year" +
	        " FROM HRM_GPF_IMPOUND_BAL_TMP a, " +
	        "  HRM_GPF_impound_disbnew b " +
	        "WHERE a.gpf_no      =b.gpf_no " +
	        "AND a.gpf_no        =? " +
	        "AND a.financial_year=?" );
	        			ps.setString(1,gpf_no);
	        			ps.setString(2,fin_year);
	        			
	        			rs=ps.executeQuery();
	        			     while(rs.next()) 
	        			     {
	        			    	         impound.add(months[rs.getInt("ac_month")-1]+"-"+rs.getInt("ac_year"));
	        					    	 IMPREG_CR=rs.getString("IMPREG_CR");
	        					    	 IMPREG_DB=rs.getString("IMPREG_DB");
	        					    	 
	        					    	
	        					    	 IMP2003_DB=rs.getString("IMP2003_DB");
	        					    	 IMP2003_CR=rs.getString("IMP2003_CR");
	        					       	 if(rs.getString("impound_type").equalsIgnoreCase("ImpReg"))
	        					    	 {
	        					       		//System.out.println("Test impreg");
	        							    		if(rs.getString("type_trans").equalsIgnoreCase("CR"))
	        							    			
	        							    			impound.add(IMPREG_CR);
	        							    		else
	        							    		{
	        							    			impamt="-"+IMPREG_DB;
	        							    			impound.add(impamt);
	        							    		}
	        							    	//	System.out.println("impreg");
	        							    		 impound.add("0");
	        					    	 }
	        					    	 else
	        					    	 {
	        					    		 impound.add("0");
	        					    		 if(rs.getString("impound_type").equalsIgnoreCase("Imp2003"))
	        						    	 {
	        					    		 //System.out.println("imp2003");			 
	        					    			   		if(rs.getString("type_trans").equalsIgnoreCase("CR"))
	        								    			impound.add(IMP2003_CR);
	        								    		else
	        								    		{
	        								    			impamt="-"+IMP2003_DB;
	        								    			impound.add(impamt);
	        								    		}
	        					    			   		//impound.add("0");
	        						    	 
	        						    	 }					    		 
	        					    			  
	        					    	 }
	        					    	 
	        					     
	        			     }
	        			rs.close();
	        			ps.close();
	        			
	        				int cnt=impound.size();
	        	for(int i=0; i<cnt; i++){
	        		System.out.println("get impound "+i+" "+impound.get(i));
	        	}
	        	System.out.println("cnt len "+cnt);
	          
	          
	          
	          v1=(close_bal+sub_total+ref_total)-with_total;
	          v2=fimpreg;
	          System.out.println("fimpreg="+fimpreg);
	          v3=fimp2003;
	          System.out.println("v1="+v1);
	          try
	          {
	          ps=con.prepareStatement("SELECT * " +
	          		"FROM " +
	          		"  (SELECT a.ho_jrnl_mst_id AS HO_JRNL_MST_ID, " +
	          		"    JOURNAL_REF_NO, " +
	          		"    REL_MONTH, " +
	          		"    REL_YEAR, " +
	          		"    AMOUNT, " +
	          		"    TRANS_TYPE, " +
	          		"    CATEGORY, " +
	          		"    SERIAL_NO " +
	          		"  FROM " +
	          		"    (SELECT ho_jrnl_mst_id, " +
	          		"      REL_MONTH, " +
	          		"      REL_YEAR, " +
	          		"      AMOUNT, " +
	          		"      TRANS_TYPE, " +
	          		"      CATEGORY, " +
	          		"      SERIAL_NO " +
	          		"    FROM hrm_gpf_missing_cr_db_con " +
	          		"    WHERE gpf_no                  =? " +
	          		"    AND INTEREST_CALCULATED_STATUS='Y' " +
	          		"    )a " +
	          		"  LEFT OUTER JOIN " +
	          		"    (SELECT ho_jrnl_mst_id, " +
	          		"      JOURNAL_REF_NO " +
	          		"    FROM hrm_gpf_missing_cr_db_mst " +
	          		"    WHERE PROCESS_FLOW_STATUS_ID='FR' " +
	          		"    )b " +
	          		"  ON a.ho_jrnl_mst_id=b.ho_jrnl_mst_id " +
	          		"  )aa " +
	          		"INNER JOIN " +
	          		"  (SELECT SUM(INTEREST_VALUE) AS miss_interest, " +
	          		"    serial_no " +
	          		"  FROM HRM_GPF_MISSING_INTEREST " +
	          		"  WHERE PROCESS_FLOW_STATUS_ID='FR' " +
	          		"  GROUP BY serial_no " +
	          		"  )bb " +
	          		"ON bb.serial_no=aa.serial_no" );
	          	ps.setString(1,gpf_no);
	          	rs=ps.executeQuery();
	          	     while(rs.next()) 
	          	     {
	          	    	 
	          	    	       miss_amt=rs.getInt("AMOUNT");
	          	    	     	 sum_interest=rs.getInt("miss_interest");
	          	    	     	System.out.println("CATEGORY1="+rs.getString("CATEGORY"));
	  	          	    	  System.out.println("TRANS_TYPE1="+rs.getString("TRANS_TYPE"));
	          	    	     
	          	    	  System.out.println("adfdaf"+miss_amt); 
	          
	          if(rs.getString("CATEGORY").equals("Regular")&&(rs.getString("TRANS_TYPE").equals("CR")))
		  	   {
		  		   v1=v1+miss_amt+sum_interest;
		  	   }
		  	   else if(rs.getString("CATEGORY").equals("Regular")&&(rs.getString("TRANS_TYPE").equals("DB")))
		  	   {
		  		   v1=v1-miss_amt-sum_interest;
		  	   }
		  	 else if(rs.getString("CATEGORY").equals("ImpReg")&&(rs.getString("TRANS_TYPE").equals("CR")))
		  	   {
		  		   v2=v2+miss_amt+sum_interest;
		  	   }
		  	else if(rs.getString("CATEGORY").equals("ImpReg")&&(rs.getString("TRANS_TYPE").equals("DB")))
		  	   {
		  		   v2=v2-miss_amt-sum_interest;
		  	   }
		  	else if(rs.getString("CATEGORY").equals("Imp2003")&&(rs.getString("TRANS_TYPE").equals("CR")))
		  	   {
		  		   v3=v3+miss_amt+sum_interest;
		  	   }
		  	else if(rs.getString("CATEGORY").equals("Imp2003")&&(rs.getString("TRANS_TYPE").equals("DB")))
		  	   {
		  		   v3=v3-miss_amt-sum_interest;
		  	   }
		  	 System.out.println("v1 now="+v1);  
		     
	}
	}
	catch(Exception e)
	{
		 System.out.println("miss"+e); 
	}

	          
	          
	          
	          
	          v1_excess=v1;
	          v2_excess=v2;
	          v3_excess=v3;
	          System.out.println("v1_excess="+v1_excess);
	          ps.close();
	          rs.close();

	          try
	          {
	          ps=con.prepareStatement("SELECT * " +
	          		"FROM " +
	          		"  (SELECT a.ho_jrnl_mst_id AS HO_JRNL_MST_ID, " +
	          		"    JOURNAL_REF_NO, " +
	          		"    REL_MONTH, " +
	          		"    REL_YEAR, " +
	          		"    AMOUNT, " +
	          		"    TRANS_TYPE, " +
	          		"    CATEGORY, " +
	          		"    SERIAL_NO " +
	          		"  FROM " +
	          		"    (SELECT ho_jrnl_mst_id, " +
	          		"      REL_MONTH, " +
	          		"      REL_YEAR, " +
	          		"      AMOUNT, " +
	          		"      TRANS_TYPE, " +
	          		"      CATEGORY, " +
	          		"      SERIAL_NO " +
	          		"    FROM HRM_GPF_EXCESS_CR_DB_CON " +
	          		"    WHERE gpf_no=? " +
	          		"    )a " +
	          		"  LEFT OUTER JOIN " +
	          		"    (SELECT ho_jrnl_mst_id, " +
	          		"      JOURNAL_REF_NO " +
	          		"    FROM HRM_GPF_EXCESS_CR_DB_MST " +
	          		"    WHERE PROCESS_FLOW_STATUS_ID='FR' " +
	          		"    )b " +
	          		"  ON a.ho_jrnl_mst_id=b.ho_jrnl_mst_id " +
	          		"  )aa " +
	          		"INNER JOIN " +
	          		"  (SELECT SUM(INTEREST_VALUE) as excess_int, " +
	          		"    SERIAL_NO " +
	          		"  FROM HRM_GPF_excess_INTEREST " +
	          		"  WHERE PROCESS_FLOW_STATUS_ID='FR' " +
	          		"  GROUP BY SERIAL_NO " +
	          		"  )bb " +
	          		"ON aa.SERIAL_NO=bb.SERIAL_NO" );
	          	ps.setString(1,gpf_no);
	          	rs=ps.executeQuery();
	          	     while(rs.next()) 
	          	     {	    	 
	          	    	
	          	    	     	 sum_interest_excess=rs.getInt("excess_int");
	          	    	      
	          	    	    miss_amt_excess=rs.getInt("AMOUNT");
	          	    	    System.out.println("miss_amt_excess"+miss_amt_excess);
	          	    	    
	          	    	    System.out.println("CATEGORY="+rs.getString("CATEGORY"));
	          	    	  System.out.println("TRANS_TYPE="+rs.getString("TRANS_TYPE"));
	          
	          System.out.println(rs.getString("CATEGORY")+":"+rs.getString("TRANS_TYPE"));
		  	   if(rs.getString("CATEGORY").equals("Regular")&&(rs.getString("TRANS_TYPE").equals("CR")))
		  	   {
		  		   v1_excess=v1_excess-miss_amt_excess-sum_interest_excess;
		  	   }
		  	   else if(rs.getString("CATEGORY").equals("Regular")&&(rs.getString("TRANS_TYPE").equals("DB")))
		  	   {
		  		   v1_excess=v1_excess+miss_amt_excess+sum_interest_excess;
		  	   }
		  	 else if(rs.getString("CATEGORY").equals("ImpReg")&&(rs.getString("TRANS_TYPE").equals("CR")))
		  	   {
		  		   v2_excess=v2_excess-miss_amt_excess-sum_interest_excess;
		  	   }
		  	else if(rs.getString("CATEGORY").equals("ImpReg")&&(rs.getString("TRANS_TYPE").equals("DB")))
		  	   {
		  		   v2_excess=v2_excess+miss_amt_excess+sum_interest_excess;
		  	   }
		  	else if(rs.getString("CATEGORY").equals("Imp2003")&&(rs.getString("TRANS_TYPE").equals("CR")))
		  	   {
		  		   v3_excess=v3_excess-miss_amt_excess-sum_interest_excess;
		  	   }
		  	else if(rs.getString("CATEGORY").equals("Imp2003")&&(rs.getString("TRANS_TYPE").equals("DB")))
		  	   {
		  		   v3_excess=v3_excess+miss_amt_excess+sum_interest_excess;
		  	   }
		  	 System.out.println("v1_excess now="+v1_excess);  
		     }
	}
	catch(Exception e)
	{
		 System.out.print("exceptyion in excess"+ e);
	}
		rs.close();
		ps.close();
	          
			  /*xml+="<Subscription_Credit>"+(close_bal+sub_total+ref_total)+" </Subscription_Credit>";
			  xml+="<Subscription_Debit>"+with_total+" </Subscription_Debit>";
	          xml+="<Balance_Has_On_Date>"+v1_excess+" </Balance_Has_On_Date>";
	          xml+="<Imp_Balance_Has_On_Date>"+v2_excess+" </Imp_Balance_Has_On_Date>";*/
		xml+="<Subscription_Credit>"+(sub_total+ref_total)+" </Subscription_Credit>";
		  xml+="<Subscription_Debit>"+with_total+" </Subscription_Debit>";
        xml+="<Balance_Has_On_Date>"+v1_excess+" </Balance_Has_On_Date>";
        xml+="<Imp_Balance_Has_On_Date>"+v2_excess+" </Imp_Balance_Has_On_Date>";
        xml+="<Imp_Balance2003>"+v3_excess+" </Imp_Balance2003>";      
			
			
	          
		} catch (Exception e) {
			System.out.println("Exception ---------->"+e);
			e.printStackTrace();
		}
		finally{
			try {
				if(rs!=null){
					rs.close();
				}				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {				
				if(ps!=null){
					ps.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		System.out.println("xmlGetBasicData()="+xml);
		return xml;
	}
	
	
	
	
	
	
	public static boolean checkRelieveDateFallsInAfterLastFinancialYear(String relieveDate){
		String []tmpYr=relieveDate.split("/");
		int mnthInt=0;
		int yrInt=0;
		boolean resultStatus=false;
		System.out.print("relieveDate====>"+relieveDate);
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		String currFinYear=GpfAuthMstModel.getCurrentFinanceYear();
		System.out.print("Current Finance Year Checking====>"+currFinYear);
		int currStartYr=1;
		try {
			currStartYr=Integer.parseInt((currFinYear.split("-"))[0].trim());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
			if((yrInt>currStartYr)){
				resultStatus=true;
			}
			else if(yrInt==currStartYr)
			     //if(mnthInt>3)
			     {
		                resultStatus=true;
		            }
			System.out.println("yrInt...in lastyear checking......"+yrInt+"currStartYr...."+currStartYr);
		System.out.println("resultStatus...in lastyear checking......"+resultStatus);
		return resultStatus;
	}
	
	public static String xmlLoadListYear(String relvDate){
		String xml="";
		String []tmpYr=relvDate.split("/");
		int mnthInt=0;
		int yrInt=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		if((mnthInt==10)||(mnthInt==11)||(mnthInt==12)){
			xml+="<year>"+yrInt+"</year>";
			xml+="<year>"+(yrInt+1)+"</year>";
		}
		else if((mnthInt>=1)||(mnthInt<=9)){
			xml+="<year>"+yrInt+"</year>";
		}
		System.out.println("year loading"+xml);
		return xml;
	}
	public static String xmlLoadListMonth(String relvDate,String Year){
		String xml="";
		
		String []tmpYr=relvDate.split("/");
		int mnthInt=0;
		int yrInt=0;
		int sentinel=0;
		int currentYear=0;
		
		if(tmpYr.length==3){
			try {
				mnthInt=Integer.parseInt(tmpYr[1].trim());
				yrInt=Integer.parseInt(tmpYr[2].trim());
				currentYear=Integer.parseInt(Year.trim());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if((mnthInt==10)||(mnthInt==11)||(mnthInt==12)){
			sentinel=1;
		}
		else if((mnthInt>=1)||(mnthInt<=9)){
			sentinel=2;
		}
		if(sentinel==1){
			if(mnthInt==10){
				if(currentYear==yrInt){
					xml+="<month><monthName>10</monthName><monthValue>"+monthInString(10)+"</monthValue></month>";
					xml+="<month><monthName>11</monthName><monthValue>"+monthInString(11)+"</monthValue></month>";
					xml+="<month><monthName>12</monthName><monthValue>"+monthInString(12)+"</monthValue></month>";
				}
				else {
					xml+="<month><monthName>1</monthName><monthValue>"+monthInString(1)+"</monthValue></month>";
				}
				
				
			}
			if(mnthInt==11){
				if(currentYear==yrInt){
					xml+="<month><monthName>11</monthName><monthValue>"+monthInString(11)+"</monthValue></month>";
					xml+="<month><monthName>12</monthName><monthValue>"+monthInString(12)+"</monthValue></month>";
				}
				else {
					xml+="<month><monthName>1</monthName><monthValue>"+monthInString(1)+"</monthValue></month>";
					xml+="<month><monthName>2</monthName><monthValue>"+monthInString(2)+"</monthValue></month>";
				}
				
				
			}
			if(mnthInt==12){
				if(currentYear==yrInt){
					xml+="<month><monthName>12</monthName><monthValue>"+monthInString(12)+"</monthValue></month>";
				}
				else {
					xml+="<month><monthName>1</monthName><monthValue>"+monthInString(1)+"</monthValue></month>";
					xml+="<month><monthName>2</monthName><monthValue>"+monthInString(2)+"</monthValue></month>";
					xml+="<month><monthName>3</monthName><monthValue>"+monthInString(3)+"</monthValue></month>";
				}

				
			}
			
		}
		else if(sentinel==2){
			xml+="<month><monthName>"+(mnthInt)+"</monthName><monthValue>"+monthInString(mnthInt)+"</monthValue></month>";
			xml+="<month><monthName>"+(mnthInt+1)+"</monthName><monthValue>"+monthInString(mnthInt+1)+"</monthValue></month>";
			xml+="<month><monthName>"+(mnthInt+2)+"</monthName><monthValue>"+monthInString(mnthInt+2)+"</monthValue></month>";
			xml+="<month><monthName>"+(mnthInt+3)+"</monthName><monthValue>"+monthInString(mnthInt+3)+"</monthValue></month>";
			
		}
		System.out.println("xmlLoadListMonth="+xml);
		return xml;
	}
	
	
	private static String monthInString(int mnth){
		String tmp="";
		if(mnth==1){
			tmp="January";
		}
		else if(mnth==2){
			tmp="February";
		}
		else if(mnth==3){
			tmp="March";
		}
		else if(mnth==4){
			tmp="April";
		}
		else if(mnth==5){
			tmp="May";
		}
		else if(mnth==6){
			tmp="June";
		}
		else if(mnth==7){
			tmp="July";
		}
		else if(mnth==8){
			tmp="August";
		}
		else if(mnth==9){
			tmp="September";
		}
		else if(mnth==10){
			tmp="October";
		}
		else if(mnth==11){
			tmp="November";
		}
		else if(mnth==12){
			tmp="December";
		}

		return tmp;
	}
	
	public  static String[] convertDateToMnthAndYr(String fullDate){
		String []arr=new String[2];
		String []tmpArr=fullDate.split("/");
		for (int i = 1; i < tmpArr.length; i++) {
			arr[i-1]=tmpArr[i];
		}			
		return arr;
	}
	public static String convertMnthAndYrToDate(String month,String year){
		int Idate=0;
		int Imonth=0;
		String date="28";
		try {
			Imonth=Integer.parseInt(month.trim());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		if(Imonth==1){
			date="31";
		}
		else if(Imonth==2){
			int yr=0;
			try {
				yr=Integer.parseInt(year.trim());
				if(yr%4==0){
					date="29";
				}
				else{
					date="28";
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		else if(Imonth==3){
			date="31";
		}
		else if(Imonth==4){
			date="30";
		}
		else if(Imonth==5){
			date="31";
		}
		else if(Imonth==6){
			date="30";
		}
		else if(Imonth==7){
			date="31";
		}
		else if(Imonth==8){
			date="31";
		}
		else if(Imonth==9){
			date="30";
		}
		else if(Imonth==10){
			date="31";
		}
		else if(Imonth==11){
			date="30";
		}
		else if(Imonth==12){
			date="31";
		}
		
		String fullDate="";			
		fullDate=date+"/"+month+"/"+year;
		
		return fullDate;
	}
	
}
