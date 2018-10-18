<canvas id="pie-canvas" width="360" height="300">
Vastausten lukumäärät:
<#if oldQuestion.colHead1??><h2>${oldQuestion.colHead1}</h2>${oldQuestion.amntAnswVal1}</#if>	
<#if oldQuestion.colHead2??><h2>${oldQuestion.colHead2}</h2>${oldQuestion.amntAnswVal2}</#if>
<#if oldQuestion.colHead3??><h2>${oldQuestion.colHead3}</h2>${oldQuestion.amntAnswVal3}</#if>
<#if oldQuestion.colHead4??><h2>${oldQuestion.colHead4}</h2>${oldQuestion.amntAnswVal4}</#if>
<#if oldQuestion.colHead5??><h2>${oldQuestion.colHead5}</h2>${oldQuestion.amntAnswVal5}</#if>
</canvas>
<div id="legend"></div>

<script>
var data = [];
<#if oldQuestion.colHead1??>data[0] = ${oldQuestion.amntAnswVal1};</#if>
<#if oldQuestion.colHead2??>data[1] = ${oldQuestion.amntAnswVal2};</#if>
<#if oldQuestion.colHead3??>data[2] = ${oldQuestion.amntAnswVal3};</#if>
<#if oldQuestion.colHead4??>data[3] = ${oldQuestion.amntAnswVal4};</#if>
<#if oldQuestion.colHead5??>data[4] = ${oldQuestion.amntAnswVal5};</#if>

var labels = [];
<#if oldQuestion.colHead1??>labels[0] = '${oldQuestion.colHead1}';</#if>
<#if oldQuestion.colHead2??>labels[1] = '${oldQuestion.colHead2}';</#if>
<#if oldQuestion.colHead3??>labels[2] = '${oldQuestion.colHead3}';</#if>
<#if oldQuestion.colHead4??>labels[3] = '${oldQuestion.colHead4}';</#if>
<#if oldQuestion.colHead5??>labels[4] = '${oldQuestion.colHead5}';</#if>

var dataTotal = 0;
data.forEach(function(item){dataTotal+=item;});
console.log(dataTotal);

// Canvas with fill color
var c = document.getElementById("pie-canvas");
var ctx = c.getContext("2d");
ctx.fillStyle = "#679ef7";
ctx.fillRect(0, 0, c.width, c.height);

// Variables
var colors = ["#ACF", "#8AF", "#CEF", "#68F", "#46F"];
var xCenter = 150;
var yCenter = 150;
var radius = 100;
var labelDistY = 120;
var labelDistXLeft = 130;
var labelDistXRight = 105;
var lastEnd = Math.PI * 1.5;

// Sectors
for (var i = 0; i < data.length; i++){
	ctx.fillStyle = colors[i];
    ctx.beginPath();
	ctx.moveTo(xCenter, yCenter);
	ctx.arc(xCenter, yCenter, radius, lastEnd, lastEnd + (Math.PI * 2 * (data[i] / dataTotal)));
	ctx.fill();
	lastEnd += Math.PI * 2 * (data[i] / dataTotal);
}

// Sector outlines
for (var i = 0; i < data.length; i++){
	ctx.beginPath();
    ctx.moveTo(xCenter, yCenter);
    ctx.arc(xCenter, yCenter, radius, lastEnd, lastEnd + (Math.PI * 2 * (data[i] / dataTotal)));
    ctx.strokeStyle="#679ef7";
    ctx.lineWidth=4;
    ctx.stroke();
    lastEnd += Math.PI * 2 * (data[i] / dataTotal);
}

// Sector labels
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

// Legend
color_index = 0;
var legendHTML = "";
for (var i = 0; i < data.length; i++){
	legendHTML += "<div id='pie-chart-legend'><span style='background-color:"+colors[color_index++]+";'>&nbsp;</span> "+labels[i]+"</div>";
}
document.getElementById("legend").innerHTML = legendHTML;

</script>
<br>