<html>
<head>
<script type="text/javascript" src="../scripts/jquery-3.6.0.js"></script>
<script type="text/javascript"> 
$(document).ready(function(){
  $("button").click(function(){
    $("div").animate({height:300},"slow");
    $("div").animate({width:300},"slow");
    $("div").animate({height:100},"slow");
    $("div").animate({width:100},"slow");
  });
});
</script> 
</head>
 
<body >
<button>Start Animation</button>
<br /><br />
<div style="background:#98bf21;height:100px;width:100px;position:relative">
</div>
</body>
</html>