
var regionflag=false;
//var regionflag=true;
var circleflag=true;
var divisionflag=true;
var offtypeflag=true;
var auditflag=true;
var labflag=true;
var baseRe;
function getTransport()
    {
         var req = false;
         try 
         {
               req= new ActiveXObject("Msxml2.XMLHTTP");
         }
         catch (e) 
         {
               try 
               {
                    req = new ActiveXObject("Microsoft.XMLHTTP");
               }
               catch (e2) 
               {
                    req = false;
               }
         }
         if (!req && typeof XMLHttpRequest != 'undefined') 
         {
               req = new XMLHttpRequest();
         }   
         return req;
    }
/*function getcadre()
{
	document.getElementById("pro_id").innerHTML="";
		var cadre_id="";
		cadre_id=document.getElementById("cadre_id").value;
		var req=getTransport();
		var url="../../../../../../HRM_Emp_Seni_Report?command=getcadre&cadre_id="+cadre_id ;
		req.open("GET",url,true);          

		req.onreadystatechange=function()
		{
		   
		    if(req.readyState==4)
		    { 
		        if(req.status==200)
		        {  
//alert(req.responseText)
        	var response=req.responseXML.getElementsByTagName("response")[0];
        	baseRe=req.responseXML.getElementsByTagName("response")[0];
      
           var tagcommand=baseRe.getElementsByTagName("command")[0];
          
           var tot=baseRe.getElementsByTagName("count").length;
           var Command=tagcommand.firstChild.nodeValue;
          
            if(Command=="getcadre")
           {
           	  var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
           
           	  if(flag=="success")
               	{
           		var pro_id=document.getElementById("pro_id");
           		var option=document.createElement("OPTION");
                option.text="--Select Proceeding No--";
                option.value="0";
                try
                {
                	pro_id.add(option);
                }catch(errorObject)
                {
                	pro_id.add(option,null);
                } 
           		  for(var i=0;i<tot;i++)
           			  {
		           			var PROCEEDINGS_REF_NO = baseRe.getElementsByTagName("PROCEEDINGS_REF_NO")[i].firstChild.nodeValue;	
		           			var option=document.createElement("OPTION");
		                    option.text=PROCEEDINGS_REF_NO;
		                    option.value=PROCEEDINGS_REF_NO;
		                    //alert(pro_id);
		                    //Making Browser Independent
		                    try
		                    {
		                    	pro_id.add(option);
		                    }
		                    catch(errorObject)
		                    {
		                    	pro_id.add(option,null);
		                    }
           			  }
               	}
           }
        }
    }
};
req.send(null);
}*/
function show()
{
document.getElementById("proc_no").style.display="";
}
function hide()
{
	document.getElementById("proc_no").style.display="none";	
}
function check()
{
	
	var url="../../../../../../HRM_Emp_Seni_Report?OLevel=submit";
	
	var cadre_id="";
	var pro_id="";
	cadre_id=document.getElementById("cadre_id").value;
	pro_id=document.getElementById("pro_id").value;
	
	url=url+"&cadre_id="+cadre_id;
	
	url=url+"&pro_id="+pro_id;
	
	if(document.hrm_seniority_rep.Seni_sta[0].checked==true)
    {
            url=url+"&Seni_sta=PRO";
           
            document.hrm_seniority_rep.Seni_sta.value='PRO';
    }
    else if(document.hrm_seniority_rep.Seni_sta[1].checked==true)
    {
            url=url+"&Seni_sta=All";
            document.hrm_seniority_rep.Seni_sta.value='All';
    }
    else if(document.hrm_seniority_rep.Seni_sta[2].checked==true)
    {
            url=url+"&Seni_sta=WRK";
             document.hrm_seniority_rep.Seni_sta.value='WRK';
    }
	
	
	if(document.hrm_seniority_rep.optoutputtype[0].checked==true)
    {
            url=url+"&optoutputtype=pdf";
            document.hrm_seniority_rep.outputtype.value='pdf';
    }
    else if(document.hrm_seniority_rep.optoutputtype[1].checked==true)
    {
            url=url+"&optoutputtype=excel";
            document.hrm_seniority_rep.outputtype.value='excel';
    }
    else if(document.hrm_seniority_rep.optoutputtype[2].checked==true)
    {
            url=url+"&optoutputtype=html";
             document.hrm_seniority_rep.outputtype.value='html';
    }
    


document.hrm_seniority_rep.action="../../../../../../HRM_Emp_Seni_Report";
document.hrm_seniority_rep.method="POST";
document.hrm_seniority_rep.submit();
}
function Getdata()
{
	
	var post_id=document.getElementById("post_id").value;
	var finyear=document.getElementById("finyear").value;
	var url="../../../../../../HRM_Emp_Seni_Report?command=Getdata&post_id="+post_id+"&finyear="+finyear;
	//alert(url)
	var req=getTransport();
	req.open("GET",url,true);          

	req.onreadystatechange=function()
	{
	   
	    if(req.readyState==4)
	    { 
	        if(req.status==200)
	        {  
	        	//alert(req.responseText);
	        	//var response=req.responseXML.getElementsByTagName("response")[0];
	        	baseRe=req.responseXML.getElementsByTagName("response")[0];
	      
	           var tagcommand=baseRe.getElementsByTagName("command")[0];
	          
	           var tot=baseRe.getElementsByTagName("count").length;
	           var Command=tagcommand.firstChild.nodeValue;
	           //alert(tot)
	          
	            if(Command=="Getdata")
	           {
	           	  var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
	           
	           	  if(flag=="success")
	               	{
	           		var data1 = new google.visualization.DataTable();
	    	        data1.addColumn('string', 'Topping');
	    	        data1.addColumn('number', 'Slices');
	    	        data1.addColumn({type: 'string', role: 'tooltip'});
	    	        var dataSet1= new Array();
	    	        
	    	        var dataFundProg = new google.visualization.DataTable();
	    	        dataFundProg.addColumn('string', 'Topping');
	    	        dataFundProg.addColumn('number', 'Slices');
	    	        dataFundProg.addColumn({type: 'string', role: 'tooltip'});
	    	        var dataSetFundProg= new Array();
	    	        
	    	    	var dataSet= new Array();
	    	    	dataSet.push(['office_name','No of Sanctionted Post','Filled Up','Vacancy']);
	    	       // var anc=document.createElement("A");
	    	        //anc.href="javascript:onClicktestn("+CIRCLE_OFFICE_ID+",'"+CIRCLE_OFFICE_NAME+"')";
	           		for(var i=0;i<tot;i++)
         			  {
		           			var region_office_id = baseRe.getElementsByTagName("region_office_id")[i].firstChild.nodeValue;
		           			var total = baseRe.getElementsByTagName("total")[i].firstChild.nodeValue;
		           			var fillup = baseRe.getElementsByTagName("fillup")[i].firstChild.nodeValue;
		           			var vacancy = baseRe.getElementsByTagName("vacancy")[i].firstChild.nodeValue;
		           			var office_name= baseRe.getElementsByTagName("office_name")[i].firstChild.nodeValue;
		           			
		           			 office_name= "O/o . " +office_name;
		           			
		           			dataSet.push([office_name, parseInt(total), parseInt(fillup),parseInt(vacancy)]);
		           			dataSet1.push([office_name,parseInt(fillup),region_office_id]);
		           			dataSetFundProg.push([office_name,parseInt(vacancy),region_office_id]);
		           			
         			  }
	           		//alert(dataSet1)
	           		data1.addRows(dataSet1);
	                var options1 = { titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14},title : 'Region wise Post Rank - Filled Up Report','width':600,
	                               'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000},
	                
	                               legend: {textStyle: {color: '#1330EE'}}};
	                var chart1 = new google.visualization.PieChart(document.getElementById('chart_div'));
	                chart1.draw(data1, options1);
	                
	                function eventHandler()
	                {
	                	var region_office_id=data1.getValue(chart1.getSelection()[0].row,2);
	                	var office_name = data1.getValue(chart1.getSelection()[0].row, 0);
	                	//var post_id=document.getElementById("post_id").value;
	                	//alert(post_id+"--------");
	                	location.href="Circle_wise_rank.jsp?region_office_id="+region_office_id+"&office_name="+office_name+"&post_id="+post_id+"&finyear="+finyear;
	                }
	                google.visualization.events.addListener(chart1, 'select', eventHandler); 
	                //PIE CHART-FUND PROG WISE
	                  dataFundProg.addRows(dataSetFundProg);
	                  var optionsFundProg = { titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14},title : 'Region wise Post Rank  - Vacancy Report','width':600,
	                                 'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000},
	                                 legend: {textStyle: {color: '#1330EE'}}};
	                  var chartFundProg = new google.visualization.PieChart(document.getElementById('chart_div_FundProgWise'));
	                  chartFundProg.draw(dataFundProg, optionsFundProg);
	                  
	                  
	                  
	                  
	                  
	               // BAR CHART -FUND PROG WISE
	          			 var data =google.visualization.arrayToDataTable(dataSet);  
	          			 var options = {
	          					titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14},title : 'Region wise Post Rank Report -Filled Up / Vacancy', 'height':400,'width':1200,
	                               vAxis: {title: "No of Sanction Post / Filled up / Vacancy", titleTextStyle: { italic: false, color:'green',fontweight:'bold',fontSize: 14},textStyle: {color: '#1330EE', fontName: 'Arial Black',fontSize: 16}},
	                              // hAxis: {textStyle: {color: 'black', fontName: 'Arial Black',fontSize: 35},is3D: true,title: 'Orders'}
	                               //hAxis: {textStyle: {color: '#330099', fontName: 'bitstream charter',fontSize: 13}},{title: "Office Name"},sliceVisibilityThreshold: 0,,
	                               hAxis: {title: "Office Name", titleTextStyle: { italic: false, color:'green',fontweight:'bold',fontSize: 14},textStyle: {color: '#1330EE', fontName: 'Arial Black',fontSize: 12,bold:100}},
	                               legend: {textStyle: {color: '#1330EE'}},
	                               //hAxis: {title: "Office Name"},sliceVisibilityThreshold: 0,
	                               seriesType: "bars", allowHtml: true
	                             };

	                             var chart = new google.visualization.ComboChart(document.getElementById('chart_div1'));
	                             chart.draw(data, options);
	               	}
	           }
	        }
	        
	        }
	}
	req.send(null);
}
function getCirlce() 
{

	var post_id=document.getElementById("rank_id").value;
	var region_id=document.getElementById("region_id").value;
	var finyear=document.getElementById("finyear").value;
	var region_office_id=document.getElementById("region_id").value;
	var url="../../../../../../HRM_Emp_Seni_Report?command=getCirlce&region_id="+region_id+"&finyear="+finyear+"&post_id="+post_id;

	var req=getTransport();
	req.open("GET",url,true);          

	req.onreadystatechange=function()
	{
	   
	    if(req.readyState==4)
	    { 
	        if(req.status==200)
	        {  
	        	//alert(req.responseText);
	        	//var response=req.responseXML.getElementsByTagName("response")[0];
	        	baseRe=req.responseXML.getElementsByTagName("response")[0];
	      
	           var tagcommand=baseRe.getElementsByTagName("command")[0];
	          
	           var tot=baseRe.getElementsByTagName("count").length;
	           var Command=tagcommand.firstChild.nodeValue;
	           //alert(tot)
	          
	            if(Command=="getCirlce")
	           {
	           	  var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
	           
	           	  if(flag=="success")
	               	{
	           		var data1 = new google.visualization.DataTable();
	    	        data1.addColumn('string', 'Topping');
	    	        data1.addColumn('number', 'Slices');
	    	        data1.addColumn({type: 'string', role: 'tooltip'});
	    	        var dataSet1= new Array();
	    	        
	    	        var dataFundProg = new google.visualization.DataTable();
	    	        dataFundProg.addColumn('string', 'Topping');
	    	        dataFundProg.addColumn('number', 'Slices');
	    	        dataFundProg.addColumn({type: 'string', role: 'tooltip'});
	    	        var dataSetFundProg= new Array();
	    	        
	    	        var dataSet= new Array();
	    	    	dataSet.push(['office_name','No of Sanctionted Post','Filled Up','Vacancy']);
	    	        
	    	       // var anc=document.createElement("A");
	    	        //anc.href="javascript:onClicktestn("+CIRCLE_OFFICE_ID+",'"+CIRCLE_OFFICE_NAME+"')";
	           		for(var i=0;i<tot;i++)
         			  {
		           			var office_id = baseRe.getElementsByTagName("office_id")[i].firstChild.nodeValue;
		           			var total = baseRe.getElementsByTagName("total")[i].firstChild.nodeValue;
		           			var fillup = baseRe.getElementsByTagName("fillup")[i].firstChild.nodeValue;
		           			var vacancy = baseRe.getElementsByTagName("vacancy")[i].firstChild.nodeValue;
		           			var office_name= baseRe.getElementsByTagName("office_name")[i].firstChild.nodeValue;
		           			dataSet.push([office_name, parseInt(total), parseInt(fillup),parseInt(vacancy)]);
		           			dataSet1.push([office_name,parseInt(fillup),office_id]);
		           			dataSetFundProg.push([office_name,parseInt(vacancy),office_id]);
		           			
         			  }
	           		//alert(dataSet1)
	           		data1.addRows(dataSet1);
	                var options1 = {titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14}, title : 'Circle wise Post Rank - Filled Up Report','width':600,
	                               'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000},
	                               legend: {textStyle: {color: '#1330EE'}}};
	                var chart1 = new google.visualization.PieChart(document.getElementById('chart_div'));
	                chart1.draw(data1, options1);
	                
	               function eventHandler()
	                {
	            	   
	                	var office_id=data1.getValue(chart1.getSelection()[0].row, 2);
	                	var office_name = data1.getValue(chart1.getSelection()[0].row, 0);
	                	
	                	location.href="Division_wise_rank.jsp?office_id="+office_id+"&office_name="+office_name+"&post_id="+post_id+"&finyear="+finyear+"&region_office_id="+region_office_id;
	                }
	                google.visualization.events.addListener(chart1, 'select', eventHandler); 
	                //PIE CHART-FUND PROG WISE
	                  dataFundProg.addRows(dataSetFundProg);
	                  var optionsFundProg = {titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14}, title : 'Circle wise Post Rank - Vacancy Report','width':600,
	                                 'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000},
	                                 legend: {textStyle: {color: '#1330EE'}}};
	                  var chartFundProg = new google.visualization.PieChart(document.getElementById('chart_div_FundProgWise'));
	                  chartFundProg.draw(dataFundProg, optionsFundProg);
	               // BAR CHART -FUND PROG WISE
	          			 var data =google.visualization.arrayToDataTable(dataSet);  
	          			 var options = {
		          					titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14},title : 'Circle wise Post Rank Report -Filled Up / Vacancy', 'height':400,'width':1000,
		                               vAxis: {title: "No of Sanction Post / Filled up / Vacancy", titleTextStyle: { italic: false, color:'green',fontweight:'bold',fontSize: 14},textStyle: {color: '#1330EE', fontName: 'Arial Black',fontSize: 16}},
		                              // hAxis: {textStyle: {color: 'black', fontName: 'Arial Black',fontSize: 35},is3D: true,title: 'Orders'}
		                               //hAxis: {textStyle: {color: '#330099', fontName: 'bitstream charter',fontSize: 13}},{title: "Office Name"},sliceVisibilityThreshold: 0,,
		                               hAxis: {title: "Office Name", titleTextStyle: { italic: false, color:'green',fontweight:'bold',fontSize: 14},textStyle: {color: '#1330EE', fontName: 'Arial Black',fontSize: 12}},
		                               legend: {textStyle: {color: '#1330EE'}},
		                               //hAxis: {title: "Office Name"},sliceVisibilityThreshold: 0,
		                               seriesType: "bars", allowHtml: true
		                             };

	                             var chart = new google.visualization.ComboChart(document.getElementById('chart_div1'));
	                             chart.draw(data, options);
	               	}
	           }
	        }
	        
	        }
	}
	req.send(null);

}
function onClicktestn(office_id,office_name,post_id,finyear)
{
	
	location.href="Circle_wise_rank.jsp?office_id="+office_id+"&office_name="+office_name+"&post_id="+post_id+"&finyear="+finyear;
}
function getdiv()
{


	var post_id=document.getElementById("rank_id").value;
	var circle_id=document.getElementById("circle_id").value;
	var finyear=document.getElementById("finyear").value;
	
	var url="../../../../../../HRM_Emp_Seni_Report?command=getDivision&circle_id="+circle_id+"&finyear="+finyear+"&post_id="+post_id;
	//alert(url)
	var req=getTransport();
	req.open("GET",url,true);          

	req.onreadystatechange=function()
	{
	   
	    if(req.readyState==4)
	    { 
	        if(req.status==200)
	        {  
	        	//alert(req.responseText);
	        	//var response=req.responseXML.getElementsByTagName("response")[0];
	        	baseRe=req.responseXML.getElementsByTagName("response")[0];
	      
	           var tagcommand=baseRe.getElementsByTagName("command")[0];
	          
	           var tot=baseRe.getElementsByTagName("count").length;
	           var Command=tagcommand.firstChild.nodeValue;
	           //alert(tot)
	          
	            if(Command=="getDivision")
	           {
	           	  var flag=baseRe.getElementsByTagName("flag")[0].firstChild.nodeValue;
	           
	           	  if(flag=="success")
	               	{
	           		var data1 = new google.visualization.DataTable();
	    	        data1.addColumn('string', 'Topping');
	    	        data1.addColumn('number', 'Slices');
	    	        data1.addColumn({type: 'string', role: 'tooltip'});
	    	        var dataSet1= new Array();
	    	        
	    	        var dataFundProg = new google.visualization.DataTable();
	    	        dataFundProg.addColumn('string', 'Topping');
	    	        dataFundProg.addColumn('number', 'Slices');
	    	        dataFundProg.addColumn({type: 'string', role: 'tooltip'});
	    	        var dataSetFundProg= new Array();
	    	        
	    	        var dataSet= new Array();
	    	    	dataSet.push(['office_name','No of Sanctionted Post','Filled Up','Vacancy']);
	    	        
	    	       // var anc=document.createElement("A");
	    	        //anc.href="javascript:onClicktestn("+CIRCLE_OFFICE_ID+",'"+CIRCLE_OFFICE_NAME+"')";
	           		for(var i=0;i<tot;i++)
         			  {
		           			var office_id = baseRe.getElementsByTagName("office_id")[i].firstChild.nodeValue;
		           			var total = baseRe.getElementsByTagName("total")[i].firstChild.nodeValue;
		           			var fillup = baseRe.getElementsByTagName("fillup")[i].firstChild.nodeValue;
		           			var vacancy = baseRe.getElementsByTagName("vacancy")[i].firstChild.nodeValue;
		           			var office_name= baseRe.getElementsByTagName("office_name")[i].firstChild.nodeValue;
		           			dataSet.push([office_name, parseInt(total), parseInt(fillup),parseInt(vacancy)]);
		           			dataSet1.push([office_name,parseInt(fillup),office_id]);
		           			dataSetFundProg.push([office_name,parseInt(vacancy),office_id]);
		           			
         			  }
	           	
	           		//alert(dataSet1)
	           		data1.addRows(dataSet1);
	                var options1 = {titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14}, title : 'Division wise Post Rank - Filled Up Report','width':600,
	                               'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000},
	                               legend: {textStyle: {color: '#1330EE'}}};
	                var chart1 = new google.visualization.PieChart(document.getElementById('chart_div'));
	                chart1.draw(data1, options1);
	                
	               /*function eventHandler()
	                {
	            	   
	                	var office_id=data1.getValue(chart1.getSelection()[0].row, 2);
	                	var office_name = data1.getValue(chart1.getSelection()[0].row, 0);
	                	
	                	window.open("Division_wise_rank.jsp?office_id="+office_id+"&office_name="+office_name);
	                }*/
	                //google.visualization.events.addListener(chart1, 'select', eventHandler); 
	                //PIE CHART-FUND PROG WISE
	                  dataFundProg.addRows(dataSetFundProg);
	                  var optionsFundProg = {titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14}, title : 'Division wise Post Rank - Vacancy Report','width':600,
	                                 'height':300,sliceVisibilityThreshold: 0, is3D: true, hAxis: {minValue: 1000},
	                                 legend: {textStyle: {color: '#1330EE'}}};
	                  var chartFundProg = new google.visualization.PieChart(document.getElementById('chart_div_FundProgWise'));
	                  chartFundProg.draw(dataFundProg, optionsFundProg);
	               // BAR CHART -FUND PROG WISE
	          			 var data =google.visualization.arrayToDataTable(dataSet);  
	          			 var options = {
		          					titleTextStyle: { italic: false, color:'brown',fontweight:'bold',fontSize: 14},title : 'Division wise Post Rank Report -Filled Up / Vacancy', 'height':400,'width':1000,
		                               vAxis: {title: "No of Sanction Post / Filled up / Vacancy", titleTextStyle: { italic: false, color:'green',fontweight:'bold',fontSize: 14},textStyle: {color: '#1330EE', fontName: 'Arial Black',fontSize: 16}},
		                              // hAxis: {textStyle: {color: 'black', fontName: 'Arial Black',fontSize: 35},is3D: true,title: 'Orders'}
		                               //hAxis: {textStyle: {color: '#330099', fontName: 'bitstream charter',fontSize: 13}},{title: "Office Name"},sliceVisibilityThreshold: 0,,
		                               hAxis: {title: "Office Name", titleTextStyle: { italic: false, color:'green',fontweight:'bold',fontSize: 14},textStyle: {color: '#1330EE', fontName: 'Arial Black',fontSize: 12}},
		                               legend: {textStyle: {color: '#1330EE'}},
		                               //hAxis: {title: "Office Name"},sliceVisibilityThreshold: 0,
		                               seriesType: "bars", allowHtml: true
		                             };

	                             var chart = new google.visualization.ComboChart(document.getElementById('chart_div1'));
	                             chart.draw(data, options);
	               	}
	           }
	        }
	        
	        }
	}
	req.send(null);

	
}
function onClicktestn(office_id,office_name,post_id,finyear)
{
	location.href="Division_wise_rank.jsp?office_id="+office_id+"&office_name="+office_name+"&post_id="+post_id+"&finyear="+finyear;
}
/*
function showofficelevel()
{
    document.frmValidationSummaryRep.optofficelevel[1].checked=true;
    var offlevel=document.getElementById("trofficelevel");
    offlevel.style.display="block";
    
}
*/



 



/*
function showoffice()
{
   
    document.frmValidationSummaryRep.optoffice[1].checked=true;
    var offlevel=document.getElementById("trofficeselection");
    offlevel.style.display="block";
    document.getElementById("divallhier").style.display='block';
    //alert('hai');
    var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
    if(type=='RN') 
    {
        officevisible('block','none','none','none');
        
    }
    else if(type=='CL')     {
       officevisible('block','block','none','none');
    }

    else if(type=='DN')     {
       officevisible('block','block','block','block','block');
    }
    document.frmValidationSummaryRep.cmbcircle.disabled=true;
   // document.frmValidationSummaryRep.cmbofftype.disabled=true;
    document.frmValidationSummaryRep.cmbdivision.disabled=true;
    
    var iframe=document.getElementById("diviframeregion");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframecircle");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframeofftype");
    iframe.style.visibility='hidden';
    var iframe=document.getElementById("diviframedivision");
    iframe.style.visibility='hidden';
    
    
    regionflag=false;
    circleflag=true;
    offtypeflag=true;
    divisionflag=true;
    
    
    
}
*/






var s=0;
var hier=true;
var level=true;
var city=true;
var  other=true;


/*
function getLevel()
    {
    //alert("show me");
        var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
        //alert('getoffice:'+type);
       officevisible('none','none','none','none'); 
       hideoffice()
      if(type=="")
      {
            var offlevel=document.getElementById("troffice");
            offlevel.style.display="none";
      }
      else
      {
            
             if(type!='HO') 
            {
                 var offlevel=document.getElementById("troffice");
                 offlevel.style.display="block";        
            }
            
             if(type=='DN')
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="block";
              }
              else
              {
                var agg=document.getElementById("aggreate");
                agg.style.display="none";
                document.frmValidationSummaryRep.aggid.checked=false;
              }
           
      }
    }
    */
    

    
    





















/*
function regiononchange()
{
     var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
   circlefalg=false;
    if(type=='CL' || type=='DN')
    {
        getCircle('circle');
    }

}

function circleonchange()
{
     var type=document.frmValidationSummaryRep.cmbolevel.options[document.frmValidationSummaryRep.cmbolevel.selectedIndex].value;
    divisionfalg=false;
    if(type=='DN')
    {
    getDivision('division');
    }

}
*/


    

    
    
 
    










//////////////////////////// for next purpose ///////////////////////
///////////////////////  for designation,cadre,rank   ///////////////////////


    

        
     









 





    


function test()
{
	
	post_rank_id=document.getElementById("rank_id").value;
	finyr=document.getElementById("finyear").value;
	location.href="Region_wise_rank.jsp?post_rank_id="+post_rank_id+"&finyr="+finyr;
	
}



function circle()
{
	var region_office_id=document.getElementById("region_id").value;
	post_id=document.getElementById("rank_id").value;
finyear=document.getElementById("finyear").value;

location.href="Circle_wise_rank.jsp?region_office_id="+region_office_id+"&post_id="+post_id+"&finyear="+finyear;
//alert(url)
}
function division()
{
var office_id=document.getElementById("circle_id").value;
post_id=document.getElementById("rank_id").value;
finyear=document.getElementById("finyear").value;
location.href="Division_wise_rank.jsp?office_id="+office_id+"&post_id="+post_id+"&finyear="+finyear;
//alert(url)
}