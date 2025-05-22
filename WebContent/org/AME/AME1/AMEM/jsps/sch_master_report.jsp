<%@ page
	language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import = "java.util.List,
    		java.util.Iterator"%>
<%@page import="Servlets.AME.AME1.AMEM.servlets.Controller"%>
<%@page import="java.util.Calendar"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv = "Content-Type" content = "text/html; charset=ISO-8859-1">
		<meta http-equiv = "CACHE-CONTROL" content="NO-STORE">
		<meta http-equiv="CACHE-CONTROL" content="NO-CACHE">

	<link href="../../../../../css/pmsSchemeStyle.css" rel="stylesheet"media="screen" />
	<script type="text/javascript" src="../scripts/sch_master_report.js"></script>
	<script type="text/javascript" src="../scripts/cellcreate.js"></script>
		
		<title>Scheme Master Report</title>
	</head>

	<body onload = "load('RN','')" id = "reportBody">
		<form  name = "sch_master_report">
			<table id = "grid" align="center" class="table" border="1" width="95%"> 
				<thead>
					<tr class="tdH"> 
						<th colspan = "2" class="fnt">Scheme Master Report</th>
					</tr>
				</thead>
				
				<thead style = "height:1%;" id = "MSG" style ="display:none;">
				<!-- 	<tr>
						<td align = "left" style = "width:90%;">
							<div id = "errorMsg"></div>
						</td>
				
						<td align = "right" style = "width:10%;">
							<input type = "hidden" value = "Hide" onclick= "setVisibility('MSG','none');">
						</td>
						
					</tr> -->
				</thead>
			</table>
			
			<table id = "grid" align = "center"  class="table" border="1" width="95%">
				<thead>
					<tr class = "diff">
						<td align = "left" class="tdH">
							<label class="fnt">Office Jurisdiction :</label>
						</td>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td class="fnt">
							
							<input type = "hidden"  name ="officeLevel" id="officeLevel" value=" " />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type = "radio" name = "radOfficeLevel" id = "radOfficeLevel1" value = "HO">HO
							&nbsp;&nbsp;
							<input type = "radio" name = "radOfficeLevel" id = "radOfficeLevel2" value = "RN">Region
							&nbsp;&nbsp;
							<input type = "radio" name = "radOfficeLevel" id = "radOfficeLevel3" value = "CL">Circle
							&nbsp;&nbsp;
							<input type = "radio" name = "radOfficeLevel" id = "radOfficeLevel4" value = "DN">Division
							&nbsp;&nbsp;
						</td>
					</tr>
					
					<tr class = "diff" class="fnt"  valign="top"  >
						<td>
							Selection of Office :
	              		</td>
	              	</tr> 
				</tbody>
			</table>
			
			<table align = "center" id = "grid" style = "border-width:0;"  class="table" border="1" width="95%">
				<tbody id="OfficeSelection">
				
					
						<tr class="tdH">
							<td colspan = "4"  valign="top" class="fnt">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type = "checkbox" name = "includeHO" id = "includeHO"/>Include Head Office
								<input type = "hidden" name = "headoffice" value = " " id = "headoffice"/>
							</td>
						</tr>
					 
					<tr>
						<td style = "width:25%;"  valign="top" class="fnt">
							<table id = "grid" style ="width:100%;" border="0">
								<tr  class="tdH">
									<td colspan="2">
										Regions :
									</td>
								</tr>
								
								<tr >
									<td colspan="2">
										<span id = "rowRegion" >
											Select : <a href = "javascript:Select(1)" id = "ancSelectRegionAll">All</a>
											&nbsp;<a href = "javascript:Select(2)" id = "ancSelectRegionNone">None</a>
											&nbsp;|&nbsp;
											<a href = "javascript:load('CL','')" id = "ancGetAllCircle">Get Circles</a>
										</span>	&nbsp;|&nbsp;
										
										<input type ="checkbox" name ="includeRegion" id = "includeRegion" align = "right" />Include
									</td>
								</tr>	
							   <tr><td colspan="2" class="fnt">	
							   	<div style = "height : 150px; overflow:auto;" id="tblist1">
						       </div></td></tr>
							</table>
	                     </td>
	                     
	                     <td style = "width:25%;"  valign="top" class="fnt">
							<table id = "grid"  style ="width:100%;" border="0">
								<tr class="tdH">
									<td>
										Circles :
									</td>
								</tr>
								
								<tr>
									<td>
										<span id = "rowCircle"> 
											Select : <a href = "javascript:Select(3)" id = "ancSelectCircleAll">All</a>
											&nbsp;
											<a href = "javascript:Select(4)" id = "ancSelectCircleNone">None</a>
											</span>&nbsp;&nbsp; | &nbsp;&nbsp;
										<input type ="checkbox" name ="includeCircle" id ="includeCircle" align = "right"/>Include
									</td>
								</tr>	
								
								<tr><td colspan="2">	
							   	<div style = "height : 150px; overflow:auto;" id="tblist2">
						       </div></td></tr>
							</table>
	                     </td>
	                     
	                     <td style = "width:25%;"  valign="top" class="fnt">
							<table id = "grid"  style ="width:100%;" border="0">
								<tr  class="tdH">
									<td>
										Division Types :
									</td>
								</tr>
								
								<tr>
									<td>
										<span id = "rowType">
											Select : <a href = "javascript:Select(5)" id = "ancSelectTypeAll">All</a>&nbsp;&nbsp;
											<a href = "javascript:Select(6)" id = "ancSelectTypeNone">None</a>	&nbsp;&nbsp;|&nbsp;&nbsp;
											<a href = "javascript:load('DN','')" id = "ancGetAllDivision">Get Divisions</a>
										</span>
										
									</td>
								</tr>	
								
								<tr><td colspan="2">	
							   	<div style = "height : 150px; overflow:auto;" id="tblist3">
						       </div></td></tr>
							</table>
	                     </td>
	                     
	                     <td style = "width:25%;" valign="top" class="fnt">
							<table id = "grid"  style ="width:100%;border-width:0;" border="0">
								<tr  class="tdH">
									<td>
										Divisions :
									</td>
								</tr>
								
								<tr>
									<td>
										<span id = "rowDivision">
											Select : <a href = "javascript:Select(7)" id = "ancSelectDivisionAll">All</a>
											&nbsp;
											<a href = "javascript:Select(8)"  id = "ancSelectDivisionNone">None</a>
											</span>&nbsp;&nbsp; | &nbsp;&nbsp;
										<input type ="checkbox" name ="includeDivision" id ="includeDivision" align = "right" />Include
									</td>
								</tr>	
								
								<tr><td colspan="2">	
							   	<div style = "height : 150px; overflow:auto;" id="tblist4">
						       </div></td></tr>
							</table>
	                     </td>
					</tr>
				</tbody>
			</table>
			
	         
	        <table id = "grid" align = "center" width="95%" border="1"> 
	        	<tfoot>
	        		<tr  class="tdH">
	        			<td align = "center">
	        				<input type = "submit" value ="Submit" onclick = "return Submit();" />
	        				<input type = "button" value ="Exit" onclick = "self.close();"/>
	        				<input type="hidden" id="rowcount" name="rowcount">
	        			</td>
	        		</tr>
	        	</tfoot>
	        </table>
		</form>
	</body>
</html>