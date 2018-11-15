<div class="page-wide">
	<h4 class="center">Aikaisempien vuosien yhteisvastaukset</h4>
</div>

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

// COUNTING THE SUM OF DATA
var dataTotal = 0;
data.forEach(function(item){dataTotal+=item;});

// INITIALISING TABLES AND VARIABLES
originalHeightValues = [];
var heightValues = [];
var barLowLimit = 250;
const chartHeight = 200;

// CANVAS WIDTH AMOUNT OF CLASSES + 50 PIXELS
var lineChartWidth = (data.length * 100) + 50;

// INITIALIZING CANVAS
// .GETCONTEXT-METHOD RETURNS OBJECT WHICH OFFERS METHODS AND PROPERTIES FOR DRAWING
var lineChartCanvas = document.getElementById("line-canvas"),
	context = lineChartCanvas.getContext("2d"),
	width = lineChartWidth;
	
// DRAW CANVAS
drawLineCanvas(lineChartCanvas, lineChartWidth);

// FIGURES FOR LINE CHART Y-AXLE
var yFigure = [];
var multiplier = 10;
var max = Math.max(...data);
var min = Math.min(...data);
var difference = max - min;

// SET CHART Y-AXLE TOP AND LOW LIMITS ACCORDING TO DATA
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
context.font = "12px sans serif";
context.fillStyle="${lightest}";
context.lineWidth=1;
context.strokeStyle="${lineChartBgLines}";
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

// TURNING ON FANCY STROKE SETTINGS FOR THE DATA LINE AND -POINTS
context.lineCap = 'round';
context.shadowBlur = 1;
context.shadowOffsetX = 2;
context.shadowOffsety = 2;
context.shadowColor = "#CCC";

// DRAWING THE CHART
for(var i = 1; i <= heightValues.length; i++){
	point = heightValues[i];
	context.beginPath();
	context.moveTo(startPointLeft, prevPoint);
	context.arc(startPointLeft, prevPoint, 3, 0, 2 * Math.PI, true);
	context.lineTo(startPointLeft + moveRight, point);
	context.stroke();
	prevPoint = point;
	startPointLeft += moveRight;
}
 
 // TURNING OFF FANCY STROKE SETTINGS
context.lineCap = 'butt';
context.shadowBlur = 0;
context.shadowOffsetX = 0;
context.shadowOffsety = 0;

// LINE CHART X-AXLE LABELS
var lclc= [];
var lineChartLabelLeft = 65;
var lineChartLabelRight = 100;
var lineChartFigureLeft = 80;
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
	lineChartLabelLeft = lineChartLabelLeft + 100;
	lineChartFigureLeft = lineChartFigureLeft + 100;
}

function drawLineCanvas(c,w){
	c.width = w;
}

</script>
<div class="divider-with-line"></div>
