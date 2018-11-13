<canvas id="line-canvas" width="500" height="380">
<#-- This section is shown if canvas is not supported -->
Vastausten lukumäärät:
<#if oldQuestion.colHead1??><h2>${oldQuestion.colHead1}</h2>${answerStats.amountP1}</#if>	
<#if oldQuestion.colHead2??><h2>${oldQuestion.colHead2}</h2>${answerStats.amountP2}</#if>
<#if oldQuestion.colHead3??><h2>${oldQuestion.colHead3}</h2>${answerStats.amountP3}</#if>
<#if oldQuestion.colHead4??><h2>${oldQuestion.colHead4}</h2>${answerStats.amountP4}</#if>
<#if oldQuestion.colHead5??><h2>${oldQuestion.colHead5}</h2>${answerStats.amountP5}</#if>
</canvas>

<div id="legend-line"></div>

<script>
var data = [];
<#-- 
<#if oldQuestion.colHead1??>data[0] = ${answerStats.class1Tot};</#if>
<#if oldQuestion.colHead2??>data[1] = ${answerStats.class2Tot};</#if>
<#if oldQuestion.colHead3??>data[2] = ${answerStats.class3Tot};</#if>
<#if oldQuestion.colHead4??>data[3] = ${answerStats.class4Tot};</#if>
<#if oldQuestion.colHead5??>data[4] = ${answerStats.class5Tot};</#if>
-->

var labels = [];
<#if oldQuestion.colHead1??>labels[0] = '${oldQuestion.colHead1}';</#if>
<#if oldQuestion.colHead2??>labels[1] = '${oldQuestion.colHead2}';</#if>
<#if oldQuestion.colHead3??>labels[2] = '${oldQuestion.colHead3}';</#if>
<#if oldQuestion.colHead4??>labels[3] = '${oldQuestion.colHead4}';</#if>
<#if oldQuestion.colHead5??>labels[4] = '${oldQuestion.colHead5}';</#if>

// TESTIDATAA
var years = [];
years[0] = 2013;
years[1] = 2014;
years[2] = 2015;
years[3] = 2016;
years[4] = 2017;

data[0] = 2031;
data[1] = 2084;
data[2] = 2150;
data[3] = 2082;
data[4] = 2076;

// COUNTING THE SUM OF DATA
var dataTotal = 0;
data.forEach(function(item){dataTotal+=item;});

// ALUSTETAAN TAULUKOT DATA-ARVOJEN KAAVIOON SIJOITTAMISEN LASKENTAA VARTEN
originalHeightValues = [];
var heightValues = [];
var barLowLimit = 250;
const chartHeight = 200;

<#-- FUNCTION FOR WRAPPING THE LONG LEGENDS -->
function wrapText(context, text, x, y, maxWidth, lineHeight){
<#-- IF MULTIPLE WORDS IN LABEL
 	var words = text.split(' ');   -->
	var words = text;
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

// CANVAS WIDTH AMOUNT OF CLASSES + 50 PIXELS
var lineChartWidth = (data.length * 100) + 50;

// INITIALIZING CANVAS
// .GETCONTEXT-METHOD RETURNS OBJECT WHICH OFFERS METHODS AND PROPERTIES FOR DRAWING
var lineChartCanvas = document.getElementById("line-canvas"),
	context = lineChartCanvas.getContext("2d"),
	width = lineChartWidth;
	
// DRAW CANVAS
drawLineCanvas(lineChartCanvas, lineChartWidth);

// FIGURES FOR LINE CHART Y-AXLE ACCORDING TO DATA
var yFigure = [];
var multiplier = 10;
var max = Math.max(...data);
var min = Math.min(...data);
var difference = max - min;

// SET CHART Y-AXLE LIMITS
while(true){
	if(difference < multiplier){
		max=Math.round(Math.ceil(max/(multiplier/10))*(multiplier/10));
		min=Math.round(Math.floor(min/(multiplier/10))*(multiplier/10));
		break;
	}
	else{
		multiplier *= 10;
	}
}

// COUNT THE FIGURE FOR EACH Y-AXLE LINE
const amountOfLineChartBgLines = 11;
var distance = max - min;
const classDistance = distance / 10;
var x = max;
for(var i = 0; i < amountOfLineChartBgLines; i++){
	if(max <= 10 && min >= -10){
		yFigure[i] = Math.round(x * 10) / 10;
	}
	else{
		yFigure[i] = Math.round(x);
	}
	x -= classDistance;
}

// RELATE THE FIGURES TO CHART HEIGHT AS PIXELS FROM TOP OF CHART
for(var i = 0; i < data.length; i++){
	yValue = (chartHeight * (data[i]-min)) / distance; 
	heightValues[i] = barLowLimit - yValue;
}

// DRAWING HORIZONTAL BACKGROUND LINES FOR GRAPH AND FIGURES ON THE Y-AXLE
const lineChartBgLineStartPointLeft = 50;
const yFigureLeft = 5;
const lineChartBgLineDistance = 20;
var lineChartBgLineStartPointY = 50;
context.font = "11px sans serif";
context.fillStyle="${lightest}";
context.lineWidth=1;
context.strokeStyle="#2BF";
for(var i = 0; i < amountOfLineChartBgLines; i++){
	context.beginPath();
	context.moveTo(lineChartBgLineStartPointLeft, lineChartBgLineStartPointY);
	context.lineTo(lineChartWidth-30, lineChartBgLineStartPointY);
	context.stroke();
	if(i == 0 || i == amountOfLineChartBgLines || i % 2 == 0){
		context.fillText(yFigure[i], yFigureLeft, lineChartBgLineStartPointY+3);
	}	
	lineChartBgLineStartPointY += lineChartBgLineDistance;
}

// DRAWING THE DATA LINE TO CHART
// Aloituspisteen etäisyys vasemmasta reunasta
var startPointLeft = 85;
// Edellisen datapisteen korkeus
var prevPoint = heightValues[0];
// Liikkumismäärä oikelle
const moveRight = 100;
// Dataviivan paksuus
context.lineWidth=2;
// Dataviivan väri
context.strokeStyle="#FFF";

// TURNING ON FANCY STROKE SETTINGS, ALL CAN BE REMOVED
context.lineCap = 'round';
context.shadowBlur=1;
context.shadowOffsetX=2;
context.shadowOffsety=2;
context.shadowColor="#CCC";

// DRAWING THE CHART
for(var i = 1; i <= heightValues.length; i++){
	point = heightValues[i];
	context.beginPath();
	context.moveTo(startPointLeft, prevPoint);
	context.arc(startPointLeft, prevPoint, 2, 0, 2 * Math.PI, true);
	context.lineTo(startPointLeft + moveRight, point);
	context.stroke();
	prevPoint = point;
	startPointLeft += moveRight;
 }
 
 // TURNING OFF FANCY STROKE SETTINGS, ALL CAN BE REMOVED
context.lineCap = 'round';
context.shadowBlur=0;
context.shadowOffsetX=0;
context.shadowOffsety=0;

// LINE CHART X-AXLE LABELS
var lclc= [];
var lineChartLabelLeft = 60;
var lineChartLabelRight = 100;
var lineChartFigureLeft = 85;
// Labelien yläreunan sijainti
const lineChartLabelTopLimit = 300;
// Label-tekstien maksimileveys
const lineChartMaxLegendWidth = 100;
// Tekstiviivan korkeus
const lineChartLineHeight = 20;

for (var i = 0; i < data.length; i++){
	lclc[i] = lineChartCanvas.getContext("2d");
   	lclc[i].fillStyle="${lightest}";
	lclc[i].font = "11px sans serif";    
	lclc[i].fillText(data[i], lineChartFigureLeft, heightValues[i]-15);
	lclc[i].font = "16px sans serif"; 
	lclc[i].fillText(years[i], lineChartLabelLeft, lineChartLabelTopLimit);
<#--	IF LONG LABEL TEXTS: wrapText(lclc[i], years[i], lineChartLabelLeft, lineChartLabelTopLimit, lineChartMaxLegendWidth, lineChartLineHeight);-->  
	lineChartLabelLeft = lineChartLabelLeft + 100;
	lineChartFigureLeft = lineChartFigureLeft + 100;
}
<#---->
function drawLineCanvas(c,w){
	c.width = w;
}

</script>
<div class="divider"></div>
