<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,java.sql.*"%>
<%@page import="Servlets.PMS.PMS1.DCB.servlets.*" %> 
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill Demand List  | TWAD Nest - Phase II  </title>
<link href="../../../../../css/txtbox.css" rel="stylesheet" media="screen"/>
<style>
.FR
{
	scrollbar-face-color:#9898D0;
	scrollbar-arrow-color:indigo;
	scrollbar-track-color:#C0DCC0;
	scrollbar-shadow-color:'';
	scrollbar-highlight-color:'';
	scrollbar-3dlight-color:'';
	scrollbar-darkshadow-Color:'';
} 
</style>
<script type="text/javascript" src="../scripts/Beneficiary_DCB_ob.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand.js"></script>
<link href="../../../../../css/AME.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/msg.js"></script>
<script type="text/javascript" src="../scripts/json_js1.js"></script>
<script type="text/javascript" src="../scripts/Bill_Demand_Report.js"></script>
<script type="text/javascript" src="../scripts/RIW.js"></script>
<script type="text/javascript" src="../scripts/dcbvalidation.js"></script>
<script type="text/javascript" src="../scripts/Office_Shift_new.js"></script>
<script type="text/javascript">
 function rld()
{ 
	var mon=document.getElementById("pyear").value; 
	var smonth=document.getElementById("pmonth").value;
	var Child_div=0;
	try {
	var Child_div=document.getElementById("div").value; }catch(e){}
	var res=month_year_check(smonth,mon);
	if (res!=1)
	{  
		var res1=report_period_verify_2(document.getElementById("pmonth"),document.getElementById("pyear"));
		if (res1!=1)
		document.myform.submit();
	}
}
 function focusloss()
	{	
		 document.getElementById("bentype").selectedIndex = "0";
	} 
function del()
{ 
		demand_show("del","1","")
}
function del2()
{ 
		demand_show("del","2","")
}
function del3()
{ 
		demand_show("del","3","")  
}
function del4()
{ 
		demand_show("del","14","")  
}

function ck(a)
{
	var s = document.getElementById("pr_status").value;
	document.getElementById("bcount").value=a;
	 
		if (s==0  && a!=0 )      
		{
		document.getElementById("msg").innerHTML="<font size=5 color='red'><b>Bill List Processing...Please Wait</b></font>";
		try
		{
			var string = "../../../../../images/preload2.gif";
			document.getElementById("img").src=string;
			
		}catch(e)
		{ 
			
		}
		}
		else
		{    try { 
			     document.getElementById("msg").innerHTML="";
			     document.getElementById("bcount").value="";
				 }catch(e) {}
				 try
					{
						var string = "url('../../../../../../../images/preload.gif'";
						document.getElementById("img").src="";   
					}catch(e)
					{ 
					}  
		}
}  
</script>
</head>     
 <body onload="json(2,'bentype',2)">       
 <form action="Demand_Report_List.jsp" method="get" name="myform">
 
 	<input type="hidden" id="subdiv" value="0"/>  
	<input type="hidden" id="pr_status" name="pr_status" value="1"> 
	<input type="hidden" id="pr_status1" name="pr_status1" value="1"> 
	<table align="right" >
			<tr><td align="right"><center> <font size="5"><label id="msg"></label></font>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="text" id="bcount" name="bcount" value="0" maxlength="4" size="4" style="background-color: white; font-size: 15;border: 1em;" ></center></td><Td><img src="../../../../../images/preload2.gif" id="img" width="20" height="20" ></Td>
	</tr></table><br>
	
			
	 <%   
 		String new_cond=Controller.new_cond;  
		String amt="",billsno="",inp_month="",inp_year="",oid="";  
   		String combo="",block_cb="";  
   	 	combo="<select id='dmdlist' name='dmdlist' multiple  class=select  style='width:200pt;height: 200pt'>";
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String userid="0",Office_id="",Office_Name="";
		String Date_dis=day+"/"+month+"/"+year;
   		Controller obj=new Controller();
   		String OFFICE_LEVEL_ID="0";
   		String division_list="";
		Connection con;
		String Child_div="0";
		try 
		{
				con=obj.con();
				obj.createStatement(con);			  
				userid=(String)session.getAttribute("UserId");		
				if(userid==null)
				{
				 	response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				
		 		//  Office_id=obj.getValu("HRM_EMP_CURRENT_POSTING", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp()  THEN OLD_OFFICE_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

					Office_id=obj.getValu("PMS_DCB_COM_OFFICE_SWITCH", "CASE WHEN OLD_OFFICE_ID IS NULL and DATE_ALLOWED_UPTO IS NULL THEN OFFICE_ID WHEN DATE_ALLOWED_UPTO >= clock_timestamp() and SWITCH_ID is not null THEN SWITCH_ID ELSE OFFICE_ID END AS OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')","OFFICE_ID") ;

			//	Office_id=obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"+userid+"')") ;		 
				oid=obj.setValue("oid",request); 		
 				if (!oid.trim().equalsIgnoreCase("0"))
 				Office_id=oid;   
				 
				if (Office_id.equals("")) Office_id="0";
		 		Office_Name=obj.getValue("com_mst_offices", "OFFICE_NAME","where OFFICE_ID="+Office_id+ " ");
		 		inp_month=obj.setValue("pmonth",request);
		 		inp_year=obj.setValue("pyear",request);
		 		
		 		
		 		
			    if (Integer.parseInt(inp_year)==0) inp_year=Integer.toString(year);
		 		if (Integer.parseInt(inp_month)==0) inp_month=Integer.toString(0);
		 		ResultSet rs_Rs=null;  
		 		PreparedStatement ps_Rs=null;
		 		
		 		OFFICE_LEVEL_ID=obj.getValue("com_mst_offices", "OFFICE_LEVEL_ID","where OFFICE_ID="+Office_id+ "  ");
				
		 		if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
		 		{
		 			Child_div=obj.setValue("Child_div",request);
		 		}
		 		
				try
				{
				division_list=obj.combo_str("com_mst_offices","office_name","office_id","where   office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map)  ","div","class='select' onchange=focusloss()");

			//	division_list=obj.combo_str("com_mst_all_offices_view","office_name","office_id","where   region_office_id="+Office_id+" and office_id in (select office_id from pms_dcb_div_dist_map) and division_office_id is not null ","div","class='select' onchange=focusloss()");
				 
				}catch(Exception e) 
				{
				out.println(e);
				}				
		 		
				
				
				int count_=obj.getCount("PMS_DCB_TRN_BILL_DMD", "where AMT_WORDS is null and  BILL_MONTH="+inp_month+" and  OFFICE_ID="+Office_id +" and  BILL_YEAR="+inp_year+"");
				
		 		// if already words haden't 
		 		if (count_!=0)
		 		{
		 		
			 		ps_Rs=con.prepareStatement("select BILL_SNO,MONTH_BILL_AMT from  PMS_DCB_TRN_BILL_DMD where OFFICE_ID="+Office_id +" and  BILL_MONTH="+inp_month+" and BILL_YEAR="+inp_year+"");
			 		int billcount=0;
			 		 rs_Rs=ps_Rs.executeQuery();
			 		while (rs_Rs.next())
			 		{
			 			billsno=rs_Rs.getString(1);
			 			amt=rs_Rs.getString(2);		
			 			billcount++;
			 		%>
			 		 <script type="text/javascript">		 		 
			 			var billsno=<%=billsno%>
			 		 	var amtword=chequeAmount(<%=Math.round(Double.parseDouble(amt))%>)
			 		 	 	 document.getElementById("pr_status").value=0; 
		 		 	 	 	 ck(<%=billcount%>);
			 		   		 Rs_word(billsno,amtword)
			 		   		 ck(<%=billcount%>);
			 		 		 document.getElementById("pr_status").value=1;
			 		</script>  
			 		<%		 		
			 		}		 		
			 		%><script type="text/javascript"> ck(0)</script>		
		 			<%
		 		 }
		 		
		 		ps_Rs=null;
		 		String qry_="select dmd.BILL_SNO,ben.BENEFICIARY_NAME,"+
    				 " dmd.DIV_BILL_NO ,bk.BLOCK_NAME,ben.BENEFICIARY_TYPE_ID "+
					" from " +
					" ("+					 
    				"     select "+
    				" BILL_SNO,"+
		    		" BENEFICIARY_SNO,DIV_BILL_NO"+
    				" from"+
    				"  PMS_DCB_TRN_BILL_DMD where OFFICE_ID="+Office_id+" and BILL_MONTH="+inp_month+" and BILL_YEAR="+inp_year+""+
					" )dmd "+
					"  join"+
					" ( "+
   						"  select "+
   						"  BENEFICIARY_SNO,"+
   						"  BENEFICIARY_TYPE_ID,"+
   						"  BENEFICIARY_NAME,BLOCK_SNO  "+
   						"   FROM "+
  						"  PMS_DCB_MST_BENEFICIARY " +
  					//	" where status='L'" +
  						" order by BENEFICIARY_NAME"+
						"  )BEN "+
						" ON ben.BENEFICIARY_SNO=dmd.BENEFICIARY_SNO       left join  "+
						" ( "+
						"   select  "+
						"     BLOCK_SNO,BLOCK_NAME "+
  						" from  "+
   						" "+
  						" COM_MST_BLOCKS "+
 						" "+
						" )bk  "+
 						" "+
						" on bk.BLOCK_SNO=ben.BLOCK_SNO  "+
 						" ORDER BY bk.BLOCK_SNO,BENEFICIARY_TYPE_ID,BENEFICIARY_NAME ";
		 		
		 		 
		 		ps_Rs=con.prepareStatement(qry_);		 				 
		 		rs_Rs=ps_Rs.executeQuery();
		 		String bsno="",bname="",bno="";
		 		String bl_name;		 		
		 		while (rs_Rs.next())   
		 		{
		 			bsno=rs_Rs.getString(1);
		 			bname=rs_Rs.getString(2);
		 			bno=rs_Rs.getString(3);
		 			bl_name=obj.isNull(rs_Rs.getString("BLOCK_NAME"),3);;
		 			if(bl_name.equalsIgnoreCase("")) 
		 			combo+="<option value='"+bsno+"'>               "+bname+" </option>";
		 			else
		 			combo+="<option value='"+bsno+"'>"+bl_name+"---"+bname+"  </option>";		 		
		 		}
		 		rs_Rs.close();
		 		combo+="</select>";		 		
				obj.conClose(con);
				}catch(Exception e)
				{
					response.sendRedirect(request.getContextPath()+"/index.jsp");
				}
				//onload="year('pyear','ob'),month('pmonth','ob')"
				 //onload="json(1,'sd','')"
				String []monthArr = {"Select","January","February","March","April","May","June","July","August","September","October","November","December"};
				block_cb=obj.combo_str("com_mst_blocks","block_name","block_sno"," where block_sno in ( select BLOCK_SNO from PMS_DCB_MST_BENEFICIARY where OFFICE_ID="+Office_id+" and  STATUS ='L'  ) and   DISTRICT_CODE in ( select district_code from  com_mst_districts where district_code in ( select district_code from  PMS_DCB_DIV_DIST_MAP where OFFICE_ID="+Office_id+" ) ) ","blk","0"," class=select  " );
		 		String freeze_count= obj.getValue("PMS_DCB_DATA_FREEZE" ,"DCB_FREEZE","c","where CASHBOOK_YEAR="+inp_year+" and CASHBOOK_MONTH="+inp_month+" and  ACCOUNTING_FOR_OFFICE_ID="+Office_id );
		 		String fr_msg="";
            	String disable="";
            	if (freeze_count.equalsIgnoreCase("0")) 
            	{
            		disable="";
            	}else  
            	{
            		disable="disabled='disabled'";
            		fr_msg="<font color='red'>DCB Data Freezed...Bills Cannot be deleted....</font><font>Raise DCB Unfreeze Request </font>&nbsp;&nbsp;&nbsp;";
            	}
             
%> 
  <table width="90%"     align="center"  cellpadding="6" cellspacing="0"  height="50%"  style="border-color: skyblue;border-collapse: collapse">
  <tr>    
	 <th colspan="4" align="center" ><font color="	#408080"> Tamil Nadu Water Supply and Drainage Board</font></th>
  </tr>
  
  <tr> 
	 <td colspan="4" align="center"> <%=Office_Name%></td>
  </tr>
  
  <tr class="tdH">
	 <td colspan="3" align="center">Bill Demand List</td>			  
  </tr>
   <%
					if (!"DN".equalsIgnoreCase(OFFICE_LEVEL_ID))
					{
					%>  
					<tr>
				 	 <td  ><font class="tdText">  Division</font> </td>
				 	 <td>
				 	 <%=division_list%>
				 	</td>
				 	</tr>
				 	<%}%>
  <tr>
	 <td  align="left"> <font color="#0000A0"> Month And Year </font> </td>
	 <Td align="left">
	 			<select class="select" id="pmonth" name="pmonth"  style="width:90pt;"  onchange="rld()">
	 			<option value="0">-Select Month-</option>
				<% 
		 			for (int j=1;j<=12;j++) 
		 			{ 
			   			if (Integer.parseInt(inp_month)!=j) 
			   			{ 
			   	%> 
			    <option value="<%=j%>"><%=monthArr[j]%></option>
			    		<%} else { %>
			    <option value="<%=j%>" selected="selected"><%=monthArr[j]%></option> 
			  		  	<%} %> 
			  	 <%} %>  
			 </select> 
			 <select class="select"  id="pyear" name="pyear"  style="width:80pt;" onchange="rld()" > 
			  <option value="0">-Select Year-</option> 
			  <% 
			    System.out.println("inp_year"  +inp_year); 
			  		for (int i=year-9;i<=year;i++) 
			  		{	 
			    		if (Integer.parseInt(inp_year)!=i)  
			    		{ 
			  %>
			  	<option value="<%=i%>"><%=i%></option>
			  		  <%}else{%> 
			  	<option value="<%=i%>" selected><%=i%></option>			  
			  	   	  <%} %>
			  	<%} %>
			  </select>
			  &nbsp;<input type="button" value="CheckData"  onclick="CheckData()" />
			  </td>
	 		  <td  align="left"><input type="button" value="Print All Bills"  onclick="demand_show('pdf',2,0)"> </td> 
			  </tr>
          	<tr>
				<td  width="15%" align="left" valign="top"><font color="#0000A0">  Beneficiary Type</font></td>
		  		<td align="left"  width="30%"><select class="select" id="bentype" name="bentype"></select> <input type="button" value="Print Bills Beneficiary Type Wise" onclick="demand_show('pdf',3,0)"/>
		  		</td>
		  		<td  align="left"> <font color="#0000A0"> <img src="../../../../../images/new_.jpg" width="30" height="20"/> Select Union  </font>  <%=block_cb%> <input type="button" value="Print Bills Union wise"  onclick="demand_show('pdf',4,0)"/> </td>
          	</tr>        	
			<tr>			  
			 	<td align="left"><font color="#0000A0"> Beneficiary</font></td>	  	    
				<td  align="left"><font size=2 color=blue>Union Name---Beneficiary Name (Bill No)</font><Br><br><%=combo%></td>
				<td  align="left" width="30%"><input type="button" value="Print Selected Beneficiary "  onclick="demand_show('pdf',1,0)">	 
				<input type="button" value="Print Selected Beneficiary (Old Format) "  onclick="demand_show('pdf',6,0)">	 </td>
			 </tr>
			 <tr>
				<td colspan="3" align="center"><input type="hidden" value="Full Deposit Print" class="bprint" onclick="demand_show('pdf',5,0)"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<%=fr_msg%>
				</td>
				
			 </tr>
			  
			 <tr>
			 	<td  class="tdH" colspan="3" align="center">
			 		<% if (oid.trim().equalsIgnoreCase("0")){ %>
			 		<input type="button" value="Delete All Bills" onclick="del()" <%=disable%>>&nbsp;
			 		&nbsp;<input type="button" value="Delete Beneficiary Type Wise"  onclick="del2()"  <%=disable%>/>
			 		&nbsp; <input type="button" value="Delete Selected Beneficiary" onclick="del3()"  <%=disable%>>
			 		<% } %>
			 		<input type="hidden" value="Delete Selected Bene" onclick="del4()" >
				 </td> 
			 </tr>
			 <tr>
			 <td  class="tdH" colspan="3" align="center"><input type="button" class="fb2" value="Back" onclick="history.go(-1)"/>&nbsp; &nbsp;<input type="button" value="Refresh" class="blprint" onclick="javascript:window.location.reload();">&nbsp; &nbsp;<input type="button" value="Exit" class="bexit" onclick="javascript:window.close()">&nbsp; &nbsp;<img src="../../../../../images/help_images.jpg" height="18px" width="45px" style="vertical-align: bottom;" onClick="window.open('help1.jsp?fn=28','mywindow','scrollbars=yes')">  </td>
			 </tr>
		</table>
		<input type=hidden name="Child_div" id="Child_div" value=<%=Child_div%>/>
		<input type=hidden name="office" id="office" value=<%=Office_id%> />
 
</form>
</body>
</html>