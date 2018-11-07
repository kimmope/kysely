<canvas id="pie-canvas" width="380" height="300">
<#-- This section is shown if canvas is not supported -->
Vastausten lukumäärät:
<#if oldQuestion.colHead1??><h2>${oldQuestion.colHead1}</h2>${answerStats.amountP1}</#if>	
<#if oldQuestion.colHead2??><h2>${oldQuestion.colHead2}</h2>${answerStats.amountP2}</#if>
<#if oldQuestion.colHead3??><h2>${oldQuestion.colHead3}</h2>${answerStats.amountP3}</#if>
<#if oldQuestion.colHead4??><h2>${oldQuestion.colHead4}</h2>${answerStats.amountP4}</#if>
<#if oldQuestion.colHead5??><h2>${oldQuestion.colHead5}</h2>${answerStats.amountP5}</#if>
</canvas>

<div id="legend-pie"></div>

<script>
var data = [];
<#if oldQuestion.colHead1??>data[0] = ${answerStats.class1Tot};</#if>
<#if oldQuestion.colHead2??>data[1] = ${answerStats.class2Tot};</#if>
<#if oldQuestion.colHead3??>data[2] = ${answerStats.class3Tot};</#if>
<#if oldQuestion.colHead4??>data[3] = ${answerStats.class4Tot};</#if>
<#if oldQuestion.colHead5??>data[4] = ${answerStats.class5Tot};</#if>

var labels = [];
<#if oldQuestion.colHead1??>labels[0] = '${oldQuestion.colHead1}';</#if>
<#if oldQuestion.colHead2??>labels[1] = '${oldQuestion.colHead2}';</#if>
<#if oldQuestion.colHead3??>labels[2] = '${oldQuestion.colHead3}';</#if>
<#if oldQuestion.colHead4??>labels[3] = '${oldQuestion.colHead4}';</#if>
<#if oldQuestion.colHead5??>labels[4] = '${oldQuestion.colHead5}';</#if>

var dataTotal = 0;
data.forEach(function(item){dataTotal+=item;});

<#-- Canvas with fill color -->
var c = document.getElementById("pie-canvas");
var ctx = c.getContext("2d");
ctx.fillStyle = "${bg}";
ctx.fillRect(0, 0, c.width, c.height);

<#-- Variables -->
var xCenter = 190;
var yCenter = 150;
var radius = 120;
var labelDistY = 140;
var labelDistXLeft = 165;
var labelDistXRight = 125;
var lastEnd = Math.PI * 1.5;

<#-- Select color according to amount of classes -->
function getColor(c){
	if (data.length == 5){
		switch(c){
			case 0: return "${lightest}";
			case 1: return "${lighter}";	
			case 2: return "${plain}";
			case 3: return "${darker}";
			case 4: return "${darkest}";
		}
	}
	else if (data.length == 4){
		switch(c){
			case 0: return "${lightest}";	
			case 1: return "${lighter}";
			case 2: return "${plain}";
			case 3: return "${darkerer}";
		}
	}
	else if (data.length == 3){
		switch(c){
			case 0: return "${lighterer}";	
			case 1: return "${plainer}";
			case 2: return "${darker}";
		}
	}
	else{
		switch(c){
			case 0: return "${lighterer}";	
			case 1: return "${darker}";
		}
	}
}

<#-- Sectors -->
for (var i = 0; i < data.length; i++){
	ctx.fillStyle = getColor(i);
    ctx.beginPath();
	ctx.moveTo(xCenter, yCenter);
	ctx.arc(xCenter, yCenter, radius, lastEnd, lastEnd + (Math.PI * 2 * (data[i] / dataTotal)));
	ctx.fill();
	lastEnd += Math.PI * 2 * (data[i] / dataTotal);
}

<#-- Sector outlines -->
for (var i = 0; i < data.length; i++){
	ctx.beginPath();
    ctx.moveTo(xCenter, yCenter);
    ctx.arc(xCenter, yCenter, radius, lastEnd, lastEnd + (Math.PI * 2 * (data[i] / dataTotal)));
    ctx.strokeStyle='rgba(255,255,255,0.2)';
    ctx.lineWidth=4;
    ctx.stroke();
    lastEnd += Math.PI * 2 * (data[i] / dataTotal);
}

<#-- Sector labels -->
var start_angle = Math.PI * 1.5;
var slice_angle = 0;
for (var i = 0; i < data.length; i++){
	slice_angle = 2 * Math.PI * data[i] / dataTotal;
	if (Math.cos(start_angle + slice_angle/2) >= 0){
    	var labelX = xCenter + (labelDistXRight) * Math.cos(start_angle + slice_angle/2);
    }
    else{
      	var labelX = xCenter + (labelDistXLeft) * Math.cos(start_angle + slice_angle/2);
    }
    var labelY = yCenter + (labelDistY) * Math.sin(start_angle + slice_angle/2);
	var labelNo = Math.round(100 * data[i] / dataTotal);
	ctx.fillStyle = "white";
	ctx.font = "16px Arial, sans serif";
	ctx.fillText(labelNo+" %", labelX,labelY);
	start_angle += slice_angle;    
}

<#-- Legend -->
color_index = 0;
var legendHTML = "";
for (var i = 0; i < data.length; i++){
	legendHTML += "<div class='legend-item'><span style='background-color:"+getColor(i)+";'>&nbsp;&nbsp;&nbsp;&nbsp;</span> "+labels[i]+"</div>";
}
document.getElementById("legend-pie").innerHTML = legendHTML;

</script>
