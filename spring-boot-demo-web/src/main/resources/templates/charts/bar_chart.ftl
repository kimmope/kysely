
<canvas id="bar-canvas" width="500" height="300">
Vastausten lukumäärät:
<#if oldQuestion.colHead1??><h2>${oldQuestion.colHead1}</h2>${oldQuestion.amntAnswVal1}</#if>	
<#if oldQuestion.colHead2??><h2>${oldQuestion.colHead2}</h2>${oldQuestion.amntAnswVal2}</#if>
<#if oldQuestion.colHead3??><h2>${oldQuestion.colHead3}</h2>${oldQuestion.amntAnswVal3}</#if>
<#if oldQuestion.colHead4??><h2>${oldQuestion.colHead4}</h2>${oldQuestion.amntAnswVal4}</#if>
<#if oldQuestion.colHead5??><h2>${oldQuestion.colHead5}</h2>${oldQuestion.amntAnswVal5}</#if>
</canvas>

<script>
var originalValues = [];
<#if oldQuestion.colHead1??>originalValues[0] = ${oldQuestion.amntAnswVal1};</#if>
<#if oldQuestion.colHead2??>originalValues[1] = ${oldQuestion.amntAnswVal2};</#if>
<#if oldQuestion.colHead3??>originalValues[2] = ${oldQuestion.amntAnswVal3};</#if>
<#if oldQuestion.colHead4??>originalValues[3] = ${oldQuestion.amntAnswVal4};</#if>
<#if oldQuestion.colHead5??>originalValues[4] = ${oldQuestion.amntAnswVal5};</#if>

var labels = [];
<#if oldQuestion.colHead1??>labels[0] = '${oldQuestion.colHead1}';</#if>
<#if oldQuestion.colHead2??>labels[1] = '${oldQuestion.colHead2}';</#if>
<#if oldQuestion.colHead3??>labels[2] = '${oldQuestion.colHead3}';</#if>
<#if oldQuestion.colHead4??>labels[3] = '${oldQuestion.colHead4}';</#if>
<#if oldQuestion.colHead5??>labels[4] = '${oldQuestion.colHead5}';</#if>

var sumOfValues = 0;
originalValues.forEach(function(item){sumOfValues+=item;});

originalHeightValues = [];

var heightValues = [];
for(var i = 0; i < originalValues.length; i++){
	originalHeightValues[i] = Math.round((originalValues[i] * 200) / sumOfValues)
	heightValues[i] = 200-originalHeightValues[i];
	console.log(originalValues[i]);
}

// Function for wrapping the legends to correct width
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

var chartWidth = (originalValues.length * 100) + 25;

var c = document.getElementById("bar-canvas");
drawCanvas(c, chartWidth);

var barLeft = 25;
var barRight = 75;
var ctx = [];

var maxLegendWidth = 100;
var lineHeight = 20;

for (var i = 0; i < originalValues.length; i++){
	ctx[i] = c.getContext("2d");
   	ctx[i].fillStyle="#666";
	ctx[i].font = "14px sans serif";    
	ctx[i].fillText(originalValues[i], barLeft, heightValues[i]-10); 
	ctx[i].fillRect(barLeft, heightValues[i], barRight, originalHeightValues[i]);
	wrapText(ctx[i], labels[i], barLeft, 220, maxLegendWidth, lineHeight);    
	barLeft = barLeft + 100;
}

function drawCanvas(c,w){
	c.width = w;
}


</script>
<br>