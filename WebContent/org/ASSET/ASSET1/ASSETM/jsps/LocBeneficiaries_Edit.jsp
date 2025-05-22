
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.sql.*,java.util.ResourceBundle" %>
 			<%@ page session="false" contentType="text/html;charset=windows-1252"%>
 			<%@page import="java.util.Calendar"  %>
 			<%@page import="Servlets.ASSET.ASSET1.ASSETM.servlets.*" %>
  <head>   
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></meta>
    <meta http-equiv="cache-control" content="no-cache"/>
    <title>Scheme Master</title>
    <link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />
<script type="text/javascript" src="../scripts/LocBeneficiaries.js"></script>
<script type="text/javascript" src="../scripts/cellcreate.js"></script>
<script type="text/javascript" src="../scripts/master1.js"></script>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../scripts/CalendarCtrl.js"></script> 
     <script type="text/javascript" src="../scripts/Pagination.js"></script> 
 
 
 <script type="text/javascript"> 
 
 function hidePag()
 {
	  // document.getElementById('divnext').style.display="none";
	  // document.getElementById('divpre').style.display="none";
	   document.getElementById('divpage').style.display="none";
	   document.getElementById('divcmbpage').style.display="none";
	   
	   document.getElementById('nodata').style.display="inline";     // Display 'No Data Found' msg
 }
 function tech(row,c,a){
	 var sno = document.getElementById("PMS_ASSET_SR_SNO" + a).value;
	 var loc = document.getElementById("LOCATION" + a).innerHTML;
	 var cap = document.getElementById("CAPACITY" + a).innerHTML;
	 var qty = document.getElementById("QTY" + a).innerHTML;
	 var loc_no = document.getElementById("LOCATION_NO" + a).innerHTML;
	 document.getElementById("sid").value = sno;
	 document.getElementById("loc").value = loc;
	 document.getElementById("cap").value = cap;

	 document.getElementById("qty").value = qty;
	 document.getElementById("rowc"+row).value = loc_no;
	 
 }
	 
	 
 function paginate()
 
	{	 var xml_obj=createObject();
     unloadChildren('tbody');
     unloadCombo('cmbpage');
     var page = new Number(xml_obj.responseXML.getElementsByTagName('page')[0].firstChild.nodeValue);
     var totpg = new Number(xml_obj.responseXML.getElementsByTagName('total')[0].firstChild.nodeValue);
   
    // var page = new Number(baseResponse.getElementsByTagName('page')[0].firstChild.nodeValue);
     //var totpg = new Number(baseResponse.getElementsByTagName('total')[0].firstChild.nodeValue);
    // alert(totpg);

     
     
     /****** Load 'Page No.' Combo & 'Total Pages' **********/ 
     
     document.getElementById('divpage').innerHTML = totpg;
     var cmbpage = document.getElementById('cmbpage');
     
     for(var i=2; i<=totpg; i++)
     {
     	var opt = document.createElement('option');
     	opt.value = i;
     	opt.innerHTML = i;
     	cmbpage.appendChild(opt);
     }
     cmbpage.value = page; 
     
     /*******************************************************/
     
     
     
     
     
     /************* 'Next' & 'Previous' links **************/
     
     if(page<totpg)
     {
     	document.getElementById('divnext').style.display = 'inline';
     }
     else
     {
     	document.getElementById('divnext').style.display = 'none';
     }
     
     
     if(page>1)
     {
     	document.getElementById('divpre').style.display = 'inline';
     }
     else
     {
     	document.getElementById('divpre').style.display = 'none';
     }
     /*******************************************************/
     
     
     
     document.getElementById('nodata').style.display="none";     // Hide 'No Data Found' msg
     
     if(totpg==0)
     {
     	hidePag();
     }
     

	}
function funclo()
{
	window.close();
	
	
}

</script>
</head>

<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"
	media="screen" />
<body onload="">
	
	<form action="">
   <%

    String sch_name="";
   String ben_typeid="",type_ben="",id="",src_comp="",sub_src_comp="",Office_id="",page_label="";
	try 
	{
		Controller obj = new Controller();
		Controller obj1 = new Controller();
		Connection con = obj.con();
		obj.createStatement(con);
		obj1.createStatement(con);
		HttpSession session = request.getSession(false);
		String userid = (String) session.getAttribute("UserId");
		if (userid == null) 
		{   
			userid ="twad10950";
			 //response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		try {
			Office_id = obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID",
					"where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='"
							+ userid + "')");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(Office_id == null)
		 Office_id = "0";
		 
		 String type=obj.setValue("t1",request);
	 
		 id=obj.setValue("sch_sno",request);
		 src_comp=obj.setValue("src_comp",request);
			sub_src_comp=obj.setValue("sub_src_comp",request);
	 sch_name="SELECT met.SCHEME_SNO,"
			 +" ben.BENEFICIARY_TYPE_ID as ben_id,"
			 +" ben.BENEFICIARY_SNO as ben_sno,"
			 +" ben.BENEFICIARY_NAME as ben_name,"
			 +" btype.BEN_TYPE_DESC as ben_desc,"
			 +" sch.SCH_NAME"
			 +" FROM "
			 +" (SELECT BENEFICIARY_SNO,"
			 +"  BENEFICIARY_NAME,"
			 +" BENEFICIARY_TYPE_ID,"
			 +" OFFICE_ID"
			 +" FROM PMS_DCB_MST_BENEFICIARY"
			 +" WHERE STATUS            ='L'";
			 if (type.equalsIgnoreCase("1")) {
				 	page_label= "Local Body Beneficiaries";			 
				    sch_name+=" AND BENEFICIARY_TYPE_ID <=6";
				    }
				 else {
				 	page_label= "Private Beneficiaries";
				    sch_name+=" AND BENEFICIARY_TYPE_ID >6";
				    }
				    
				 sch_name+=" AND office_id           ="+Office_id+" and BENEFICIARY_SNO  in (select BENEFICIARY_SNO from PMS_SCH_ASSET_SR where OFFICE_ID=5982) )ben"
				 +" JOIN"
			 +"( SELECT DISTINCT SCHEME_SNO,"
			 +" BENEFICIARY_SNO,"
			 +" office_id"
			 +"  FROM PMS_DCB_MST_BENEFICIARY_METRE"
			 +" WHERE METER_STATUS='L'"
			 +" AND office_id     = 5982 )met"
			 +" ON met.BENEFICIARY_SNO=ben.BENEFICIARY_SNO"
			 +" AND met.office_id     =ben.office_id"
			 +" JOIN"
			 +" ( SELECT SCH_SNO,SCH_NAME FROM PMS_SCH_MASTER where SCH_SNO="+id+")sch"
			 +" ON sch.SCH_SNO=met.SCHEME_SNO"
			 +" JOIN"
			 +" (SELECT BEN_TYPE_ID, BEN_TYPE_DESC FROM PMS_DCB_BEN_TYPE)btype"
			 +" ON btype.BEN_TYPE_ID=ben.BENEFICIARY_TYPE_ID"
			 +" ORDER BY BEN_TYPE_DESC,"
			 +" BENEFICIARY_NAME,"
			 +" SCH_NAME";
		
	 
		 PreparedStatement ps = con.prepareStatement(sch_name);
		ResultSet rs = ps.executeQuery();
		// System.out.println(sch_name);
			//ben_typeid=obj.setValue("ben_typeid",request);
			 //type_ben=obj.getValue("PMS_DCB_MST_BENEFICIARY","BENEFICIARY_TYPE_ID"," where BENEFICIARY_NAME="++ "  ");
		
	%> 
			<table border="1" width="90%" cellpadding="7" cellspacing="0" align="left">
			<tr  class="tdH"><td colspan="6" align="center">Service Reservoirs(<%=page_label %>)</td></tr>
			<tr><td style="font-size:large;height: 2cm;">S.no</td>
			
			<td style="font-size:large;" width="20%">Name of Beneficiary:</td>
			
			<td style="font-size:large;" >
			<table border="1" width="100%" > <tr>
				<td width="20%" style="font-size:large;">
					Name of Habitation/Location
				</td>
				<td style="font-size:large;" width="20%">
					Capacity of OHT&nbsp;(LL)
				</td>
			<td style="font-size:large;" width="20%"> 
				Quantity&nbsp;(nos)
				</td>
			<td width="20%"></td></tr></table>
			</td>
			</tr>
			
	<%
	      
	int i=1;
	int row=0,innerrow=0;  
	String ben_sno="",ben_id="";
	String prs_sno="";
	String loc,cap,qty;
			while(rs.next()) 
			{
				row++;
				ben_sno=rs.getString("ben_sno");
				ben_id=rs.getString("ben_id");
				out.println("<tr><td>"+i+"</td><td>"+rs.getString("ben_name")+"&nbsp;&nbsp;"+rs.getString("ben_desc")+"</td><td>");
				ResultSet  rs2=obj1.getRS("select * from PMS_SCH_ASSET_SR where BENEFICIARY_SNO="+ben_sno);
				while(rs2.next()) 
				{
					innerrow++;
					loc=rs2.getString("LOCATION");
					cap=rs2.getString("CAPACITY");
					qty=rs2.getString("QTY");
					prs_sno=rs2.getString("PMS_ASSET_SR_SNO");
					String val="<table  border=1 width='100%'><tbody id='c"+row+"'><tr><td width='20%'>";
					 val+="<input type=text name='loc"+row+"1' id='loc"+row+""+innerrow+"' value='"+loc+"'></td>";
					 val+="<td  width='20%'><input type=text name='cap"+row+""+innerrow+"' id='cap"+row+""+innerrow+"' value='"+cap+"' ></td><td  width='20%'>";
					 val+="<input type='hidden' id='ben_id"+row+"' name='ben_id"+row+"' value="+ben_id+">";
					 val+="<input type='hidden' id='ben_sno"+row+"' name='ben_sno"+row+"' value="+ben_sno+">";
					 val+="<input type='hidden' id='prs_sno"+row+""+innerrow+"' name='prs_sno"+row+""+innerrow+"' value="+prs_sno+">";
					 val+="<input type='hidden' id='rowc"+row+"' name='rowc"+row+"' value="+innerrow+">";
					 val+="<input type=text name='qty"+row+"innerrow' id='qty"+row+""+innerrow+"' value='"+qty+"'></td>";
					 val+="<td width='20%'><input type=button value='Add Location' id=but"+row+" onclick=addrow("+row+","+innerrow+")>"+"&nbsp;";
					 val+="<input type='button' value='UPDATE' onclick='update("+row+","+innerrow+")'>"+"&nbsp;";
					// val+="<input type='button' value='DELETE' onclick='delete_loc("+row+","+innerrow+")'>"+"&nbsp;";
					 val+="</td></tr></tbody></table>";
					out.println(val);
				}
				out.println("</td></tr>");
				i+=1;
			}
			
	  }
	catch(Exception e)
	  {
		out.println(e);
	}

	%>


<!-- <input type="text" id="ben_id" value="<%=type_ben %>" name="ben_id">  -->	
<input type="hidden" id="sid" value="<%=id %>" name="sid"> 
<input type="hidden"text" id="comp_id" value="<%=src_comp %>" name="comp_id"> 
<input type="hidden" id="sub_id" value="<%=sub_src_comp %>" name="sub_id"> 
		<!--  <tr>
        		<td id="tdNoData" align="center" colspan="6" class="tdText">
        			<div id="nodata" style="display:none"> </div>
        		</td>
        	</tr>
       <tbody id="tblist"></tbody>
       
      
      </table><br>
      <table  class="fb2" cellspacing="0" cellpadding="2" border="1" width="70%" > 
            <tr class="tdH" name="pgbar" id="pgbar" >
              <td> 
                <table align="center" cellspacing="3" cellpadding="2"  class="fb2"
                       border="0" width="100%">
                  <tr>
                    <td width="30%">
                      <div align="left">
                        <div id="divpre" style="display:none"><a href="javascript:prev()"><label> << </label>Previous</a></div>
                      </div>
                    </td>
                    <td width="40%">
                      <div align="center">
                        <table border="0">
                          <tr>
                            <td>
                              <div id="divcmbpage" style="display:none">
                                Page&nbsp;&nbsp;<select name="cmbpage"
                                                        id="cmbpage"
                                                        onchange="paginate()"
                                                         class="select">
                                                	<option value="1">1</option>        
                                                </select>
                              </div><input type="button" name="b1" value="Exit" onclick="">
                            </td>
                            <td>
                              <div id="divpage" style="display:none" >1</div>
                            </td>
                          </tr>
                        </table>
                      </div>
                    </td>
                    <td width="30%">
                      <div align="right">
                        <div id="divnext" style="display:none"><a href="javascript:next()">Next <label> >> </label></a></div>
                      </div>
                    </td>
                  </tr>
                </table>
              </td>
            </tr> -->
            
            <tr><td colspan="6" align="center"><input type="button" name="b1" value="Exit" onclick="funclo()"></td></tr>
 		
 	<!--  	<tr><td colspan="6">
 		<table width="100%" align="center" cellspacing="0" cellpadding="0"
			style="BORDER-COLLAPSE: collapse" border="1" borderColor="#92c2d8">
		<tr class="tdH">
		<td class="tdText" align="center">Select</td>
		<td class="tdText" align="center">Location  No.</td>
		<td class="tdText" align="center">Habitation/Location</td>
		<td class="tdText" align="center">Capacity</td>
		<td class="tdText" align="center">Quantity</td>
		
		
		</tr>
		<tbody id="tblList" name="tblList" class="tdText">
		</tbody>
	</table>
 		
 		
 		</td></tr>-->
 		</table>
 			
</form>	
</body>
</html>