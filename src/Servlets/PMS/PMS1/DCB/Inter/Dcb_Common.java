package Servlets.PMS.PMS1.DCB.Inter;

import javax.servlet.http.HttpSession;

import Servlets.PMS.PMS1.DCB.servlets.Controller;

public interface Dcb_Common {

	public  String Div_RegionWise(String Region_id,String name) throws Exception;	
	public  String regionId(String userid,Controller obj) throws Exception  ;
	public  double sub_div_wise_pr(int office_id,int sub_div,int month,int year,Controller obj) throws Exception;
	public  double schemewise_pr(int office_id,int schsno,int month,int year,Controller obj) throws Exception;
}
