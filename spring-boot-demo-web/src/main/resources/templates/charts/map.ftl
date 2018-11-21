<link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>

<div class="page-wide">
	<div id="map"></div>
</div>

<#-- Kartan taustaväri tulee dynaamisesti karttaa kutsuvan sivun perusteella, siksi ei voi olla CSS-filessä -->
<style>.leaflet-container{background-color:${bg};}</style>
<script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js" integrity="sha512-nMMmRyTVoLYqjP9hrbed9S+FzjZHW5gY1TWCHA5ckwXZBadntCNs8kEqAWdrb9O7rxbCaA4lKTIWjDXZxflOcA==" crossorigin=""></script>
<script src="/maps/profinland.geojson" type="text/javascript"></script>

<script>

<#-- FREEMARKER LIST TO JAVASCRIPT HASHMAP OBJECT -->
var oDataProvinces = {<#list statisticses as stats>"${stats.id}" : "${stats.classMode}",</#list>};

<#-- REMOVE EXTRA CHARACTERS FROM THE BEGINNING OF THE CLASS NAME (E.G. AO02 = 2) -->
for (var data in oDataProvinces){
	oDataProvinces[data] = oDataProvinces[data].slice(3);
}		

<#-- FTL ANSWER LISTS TO JAVASCRIPT HASHMAP OBJECTS -->
var oAnswerTotal = {<#list statisticses as stats>"${stats.id}" : "${stats.amount}",</#list>};
var oAnswer1 = {<#list statisticses as stats>"${stats.id}" : "${stats.amntAnswVal1}",</#list>};
var oAnswer2 = {<#list statisticses as stats>"${stats.id}" : "${stats.amntAnswVal2}",</#list>};
var oAnswer3 = {<#list statisticses as stats>"${stats.id}" : "${stats.amntAnswVal3}",</#list>};
var oAnswer4 = {<#list statisticses as stats>"${stats.id}" : "${stats.amntAnswVal4}",</#list>};
var oAnswer5 = {<#list statisticses as stats>"${stats.id}" : "${stats.amntAnswVal5}",</#list>};

<#-- LUOKKIEN NIMET TAULUKKOON -->
var classes = [];
<#if oldQuestion.colHead1??>classes[0] = '${oldQuestion.colHead1}';</#if>
<#if oldQuestion.colHead2??>classes[1] = '${oldQuestion.colHead2}';</#if>
<#if oldQuestion.colHead3??>classes[2] = '${oldQuestion.colHead3}';</#if>
<#if oldQuestion.colHead4??>classes[3] = '${oldQuestion.colHead4}';</#if>
<#if oldQuestion.colHead5??>classes[4] = '${oldQuestion.colHead5}';</#if>

<#-- Return correct amounts of answers per province to info-window -->
function answerAmount(provinceId,x){
	for (var pid in oAnswerTotal){
		if(provinceId == pid){
			return oAnswerTotal[pid];
		}
	}
}

<#-- Load geoJSON -->
var finProvinces = L.geoJSON(profinland);

<#-- Create basemap -->
var map = L.map('map').setView([65.5, 30], null, { zoomControl:false });

<#-- Set up basemap -->
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    maxZoom: 5,
    minZoom: 5,
    id: 'mapbox.empty',
    accessToken: 'pk.eyJ1Ijoia2ltbW9rIiwiYSI6ImNqbmQ1N3VmMTA0NTczcG80bDQyMjY5aTgifQ.WKyJF6CXGleSTqE9gzuv3w'
}).addTo(map);

<#-- Remove Leaflet-label -->
document.getElementsByClassName( 'leaflet-control-attribution' )[0].style.display = 'none';
<#-- Remove zoom controls -->
map.zoomControl.remove();
<#-- Disable dragging -->
map.dragging.disable();

<#-- Select color according to the normalized value (1-5 - 1-2) and amount of classes -->
<#-- http://mathforum.org/library/drmath/view/60433.html -->
function getNormalizedColor(c){
	if(classes.length == 5){
		return c > 4.2 ? '${darkest}':
	   	c > 3.4  ? '${darker}':
	   	c > 2.6  ? '${plain}':
	   	c > 1.8  ? '${lighter}':
	   	'${lightest}';	
	}
	else if(classes.length == 4){
		return c > 3.25 ? '${darkerer}':
	   	c > 2.5  ? '${plain}':
	   	c > 1.75 ? '${lighter}':
	   	'${lightest}';		
	}
	else if(classes.length == 3){
		return c > 2.33 ? '${darker}':
	   	c > 1.66 ? '${plainer}':
	   	'${lighterer}';		
	}
	else if(classes.length == 2){
		return c > 1.5 ? '${darker}':
	   	'${lighterer}';		
	}
}

<#-- Select color according to amount of classes -->
function getColor(c){
	if(classes.length == 5){
		return c == 5 ? '${darkest}':
	   	c == 4 ? '${darker}':
	   	c == 3 ? '${plain}':
	   	c == 2 ? '${lighter}':
	   	'${lightest}';	
	}
	else if(classes.length == 4){
		return c == 4 ? '${darkerer}':
	   	c == 3  ? '${plain}':
	   	c == 2 ? '${lighter}':
	   	'${lightest}';		
	}
	else if(classes.length == 3){
		return c == 3 ? '${darker}':
	   	c == 2 ? '${plainer}':
	   	'${lighterer}';		
	}
	else if(classes.length == 2){
		return c == 2 ? '${darker}':
	   	'${lighterer}';		
	}
}

<#-- Loop polygons (features/layers/provinces) in multipolygon (featureset) for coloring provinces -->
finProvinces.eachLayer(function(layer){
	<#-- Set default borders and opacity for all layer -->
	layer.setStyle({stroke : true, weight : 3, color : '#FFF', opacity : 0.2, fillColor : '${bg}', fillOpacity : 1});
	<#--  -- Loop province data hashmap object -->
	for (var data in oDataProvinces){
		if(layer.feature.properties.ID_1 == data && oDataProvinces[data] != 0){
			layer.setStyle({fillColor : getColor(oDataProvinces[data])});
	    }
	}
});

<#-- Add provinces with colored data -->
finProvinces.addTo(map);


<#-- Add info-window to the map (top right corner) -->
var info = L.control();
info.onAdd = function(map){
	this._div = L.DomUtil.create('div','info');
	this.update();
	return this._div;
};

<#-- Add content to info-window -->
info.update = function(props){
	this._div.innerHTML = props ? '<span class="info-h"><b>' + props.VARNAME_1 + '</b></span><br />\
		<span class="info-h">Vastauksia: ' + oAnswerTotal[props.ID_1] + '</span><br />\
		<#if oldQuestion.colHead1??><span class="info-t">${oldQuestion.colHead1} : ' + oAnswer1[props.ID_1] + '</span><br /></#if>\
		<#if oldQuestion.colHead2??><span class="info-t">${oldQuestion.colHead2} : ' + oAnswer2[props.ID_1] + '</span><br /></#if>\
		<#if oldQuestion.colHead3??><span class="info-t">${oldQuestion.colHead3} : ' + oAnswer3[props.ID_1] + '</span><br /></#if>\
		<#if oldQuestion.colHead4??><span class="info-t">${oldQuestion.colHead4} : ' + oAnswer4[props.ID_1] + '</span><br /></#if>\
		<#if oldQuestion.colHead5??><span class="info-t">${oldQuestion.colHead5} : ' + oAnswer5[props.ID_1] + '</span><br /></#if>\
		' : '<span class="info-h">Vie hiiri läänin päälle</span>';
};
info.addTo(map);

<#-- Add onmouseover highlight region feature -->
function style(feature) {
	return {
		color : '#FFF',
		opacity: 0.2,
		fillOpacity : 0
	};
}

function highlightFeature(e) {
	var layer = e.target;
	layer.setStyle({
		color : '#FFF',
		opacity: 1,
		fillOpacity : 0
	});
	if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
		layer.bringToFront();
	}
	info.update(layer.feature.properties);
}

var geojson;

function resetHighlight(e) {
	geojson.resetStyle(e.target);
	info.update();
}

function onEachFeature(feature, layer) {
	layer.on({
		mouseover: highlightFeature,
		mouseout: resetHighlight
	});
}

<#-- Add onmouseover functionality to provinces -->
geojson = L.geoJson(profinland, {
    style: style,
    onEachFeature: onEachFeature
}).addTo(map);

<#-- Add legend to the map (bottom right corner) -->
var legend = L.control({position: 'bottomright'});
legend.onAdd = function (map) {
    var div = L.DomUtil.create('div', 'legend-map');
	<#-- loop through density intervals and generate a label with a colored square for each interval -->
    for (var i = 0; i < classes.length; i++) {
        div.innerHTML += '<div class="legend-item">\
        <i style="background:' + getColor(i+1) + '"></i>\
        <div class="legend-text">' + classes[i] + '</div></div>';
    }
   return div;
};
legend.addTo(map);

</script>
