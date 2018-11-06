<canvas id="pie-canvas" width="380" height="300">
<#-- This section is shown if canvas is not supported -->
Vastausten lukumäärät:
<#if oldQuestion.colHead1??><h2>${oldQuestion.colHead1}</h2>${answerStats.amountP1}</#if>	
<#if oldQuestion.colHead2??><h2>${oldQuestion.colHead2}</h2>${answerStats.amountP2}</#if>
<#if oldQuestion.colHead3??><h2>${oldQuestion.colHead3}</h2>${answerStats.amountP3}</#if>
<#if oldQuestion.colHead4??><h2>${oldQuestion.colHead4}</h2>${answerStats.amountP4}</#if>
<#if oldQuestion.colHead5??><h2>${oldQuestion.colHead5}</h2>${answerStats.amountP5}</#if>
</canvas>
</div>
<div id="legend"></div>
<div class="center">
<script>
var data = [];
<#if oldQuestion.colHead1??>data[0] = ${answerStats.amountP1};</#if>
<#if oldQuestion.colHead2??>data[1] = ${answerStats.amountP2};</#if>
<#if oldQuestion.colHead3??>data[2] = ${answerStats.amountP3};</#if>
<#if oldQuestion.colHead4??>data[3] = ${answerStats.amountP4};</#if>
<#if oldQuestion.colHead5??>data[4] = ${answerStats.amountP5};</#if>

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
ctx.fillStyle = "#09D";
ctx.fillRect(0, 0, c.width, c.height);

// Variables
//var colors = ["#EFF3FF", "#BDD7E7", "#6BAED6", "#3182BD", "#08519C"];
var xCenter = 190;
var yCenter = 150;
var radius = 120;
var labelDistY = 140;
var labelDistXLeft = 165;
var labelDistXRight = 125;
var lastEnd = Math.PI * 1.5;

// Select color according to amount of classes
function getColor(c){
	if (data.length == 5){
		switch(c){
			case 0: return '#EFF3FF';
			case 1: return '#BDD7E7';	
			case 2: return '#6BAED6';
			case 3: return '#3182BD';
			case 4: return '#08519C';
		}
	}
	else if (data.length == 4){
		switch(c){
			case 0: return '#EFF3FF';	
			case 1: return '#BDD7E7';
			case 2: return '#6BAED6';
			case 3: return '#2171b5';
		}
	}
	else if (data.length == 3){
		switch(c){
			case 0: return '#DEEBF7';	
			case 1: return '#9ECAE1';
			case 2: return '#3182BD';
		}
	}
	else{
		switch(c){
			case 0: return '#DEEBF7';	
			case 1: return '#3182BD';
		}
	}
}

// DEBUG
console.log("data.length = " + data.length);

// Sectors
for (var i = 0; i < data.length; i++){
	ctx.fillStyle = getColor(i);
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
    ctx.strokeStyle='rgba(255,255,255,0.2)';
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
	legendHTML += "<div class='legend-text-pie'><span style='background-color:"+getColor(i)+";'>&nbsp;&nbsp;&nbsp;&nbsp;</span> "+labels[i]+"</div>";
}
document.getElementById("legend").innerHTML = legendHTML;

</script>