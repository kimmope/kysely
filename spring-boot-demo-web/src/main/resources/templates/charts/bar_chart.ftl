<canvas id="bar-canvas" width="500" height="380">
<#-- This section is shown if canvas is not supported -->
Vastausten lukumäärät:
<#if oldQuestion.colHead1??><h2>${oldQuestion.colHead1}</h2>${answerStats.amountP1}</#if>	
<#if oldQuestion.colHead2??><h2>${oldQuestion.colHead2}</h2>${answerStats.amountP2}</#if>
<#if oldQuestion.colHead3??><h2>${oldQuestion.colHead3}</h2>${answerStats.amountP3}</#if>
<#if oldQuestion.colHead4??><h2>${oldQuestion.colHead4}</h2>${answerStats.amountP4}</#if>
<#if oldQuestion.colHead5??><h2>${oldQuestion.colHead5}</h2>${answerStats.amountP5}</#if>
</canvas>

<div id="legend"></div>

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

originalHeightValues = [];
var heightValues = [];
var barLowLimit = 250;

for(var i = 0; i < data.length; i++){
	originalHeightValues[i] = Math.round((data[i] * 200) / dataTotal)
	heightValues[i] = barLowLimit-originalHeightValues[i];
}

<#-- Function for wrapping the legends to correct width -->
function wrapText(context, text, x, y, maxWidth, lineHeight){
	var words = text.split(' ');
	var line = '';
	for(var n = 0; n < words.length; n++) {
		var testLine = line + words[n] + ' ';
		var metrics = context.measureText(testLine);
		var testWidth = metrics.width;
		if (testWidth > maxWidth && n > 0) {
			context.fillText(line, x, y);
			line = words[n] + ' ';
			y += lineHeight;
		}
		else{
			line = testLine;
		}
	}
	context.fillText(line, x, y);
}

var chartWidth = (data.length * 100) + 25;

var c = document.getElementById("bar-canvas");
drawCanvas(c, chartWidth);

var barLeft = 25;
var barRight = 75;
var ctx = [];
var labelTopLimit = 290;
var maxLegendWidth = 100;
var lineHeight = 20;

for (var i = 0; i < data.length; i++){
	ctx[i] = c.getContext("2d");
   	ctx[i].fillStyle="${lightest}";
	ctx[i].font = "14px sans serif";    
	ctx[i].fillText(data[i], barLeft, heightValues[i]-10); 
	ctx[i].fillRect(barLeft, heightValues[i], barRight, originalHeightValues[i]);
	wrapText(ctx[i], labels[i], barLeft, labelTopLimit, maxLegendWidth, lineHeight);    
	barLeft = barLeft + 100;
}

function drawCanvas(c,w){
	c.width = w;
}

</script>
<div class="divider"></div>
