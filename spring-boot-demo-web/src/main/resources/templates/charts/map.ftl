<link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>

<div class="page-wide">
	<div id="map"></div>
</div>

<#-- Kartan taustaväri tulee dynaamisesti karttaa kutsuvan sivun perusteella, siksi ei voi olla CSS-filessä -->
<style>.leaflet-container{background-color:${bg};}</style>
<script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js" integrity="sha512-nMMmRyTVoLYqjP9hrbed9S+FzjZHW5gY1TWCHA5ckwXZBadntCNs8kEqAWdrb9O7rxbCaA4lKTIWjDXZxflOcA==" crossorigin=""></script>
<script src="/maps/profinland.geojson" type="text/javascript"></script>

<script>

<#-- KUNKIN LÄÄNIN ENITEN OSUMIA SAANEET LUOKAT TAULUKKOON -->
var dataProvinces = [<#list statisticses as statistics>"${statistics.classMode}",</#list>];

<#-- REMOVE FIRST (COUNTRY CODE) AND EXTRA CHARACTERS FROM THE BEGINNING OF THE CLASS NAME (E.G. AO02 = 2) -->
for (var i = 1; i < dataProvinces.length; i++) { 
	dataProvinces[i] = dataProvinces[i].slice(3);
}

<#-- KUNKIN LÄÄNIN VASTAUSTEN YHTEISLUKUMÄÄRÄT TAULUKKOON -->
var amntProvinces = [];
<#if answerStats.amountP1??>amntProvinces[0] = ${answerStats.amountP1};</#if>
<#if answerStats.amountP2??>amntProvinces[1] = ${answerStats.amountP2};</#if>
<#if answerStats.amountP3??>amntProvinces[2] = ${answerStats.amountP3};</#if>
<#if answerStats.amountP4??>amntProvinces[3] = ${answerStats.amountP4};</#if>
<#if answerStats.amountP5??>amntProvinces[4] = ${answerStats.amountP5};</#if>

<#-- LUOKKIEN NIMET TAULUKKOON -->
var classes = [];
<#if oldQuestion.colHead1??>classes[0] = '${oldQuestion.colHead1}';</#if>
<#if oldQuestion.colHead2??>classes[1] = '${oldQuestion.colHead2}';</#if>
<#if oldQuestion.colHead3??>classes[2] = '${oldQuestion.colHead3}';</#if>
<#if oldQuestion.colHead4??>classes[3] = '${oldQuestion.colHead4}';</#if>
<#if oldQuestion.colHead5??>classes[4] = '${oldQuestion.colHead5}';</#if>

<#-- Return correct amounts of answers per province to info-window -->
function answerAmount(provinceId,x){
	if(provinceId == 979){
		if(x == 0)return amntProvinces[0];
		if(x == 1){return ${answerStats.class1P1};}
		if(x == 2){return ${answerStats.class2P1};}
		if(x == 3){return ${answerStats.class3P1};}
		if(x == 4){return ${answerStats.class4P1};}
		if(x == 5){return ${answerStats.class5P1};}
	}
	else if(provinceId == 980){
		if(x == 0)return amntProvinces[1];
		if(x == 1){return ${answerStats.class1P2};}
		if(x == 2){return ${answerStats.class2P2};}
		if(x == 3){return ${answerStats.class3P2};}
		if(x == 4){return ${answerStats.class4P2};}
		if(x == 5){return ${answerStats.class5P2};}
	}
	else if(provinceId == 981){
		if(x == 0)return amntProvinces[2];
		if(x == 1){return ${answerStats.class1P3};}
		if(x == 2){return ${answerStats.class2P3};}
		if(x == 3){return ${answerStats.class3P3};}
		if(x == 4){return ${answerStats.class4P3};}
		if(x == 5){return ${answerStats.class5P3};}
	}
	else if(provinceId == 982){
		if(x == 0)return amntProvinces[3];
		if(x == 1){return ${answerStats.class1P4};}
		if(x == 2){return ${answerStats.class2P4};}
		if(x == 3){return ${answerStats.class3P4};}
		if(x == 4){return ${answerStats.class4P4};}
		if(x == 5){return ${answerStats.class5P4};}		
	}
	else if(provinceId == 983){
		if(x == 0)return amntProvinces[4];
		if(x == 1){return ${answerStats.class1P5};}
		if(x == 2){return ${answerStats.class2P5};}
		if(x == 3){return ${answerStats.class3P5};}
		if(x == 4){return ${answerStats.class4P5};}
		if(x == 5){return ${answerStats.class5P5};}		
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

<#-- Loop polygons (features/layers/provinces) in multipolygon (featureset) for coloring -->
<#-- NOTE : ID-order of layers makes the order of provinces in database -->
finProvinces.eachLayer(function(layer){
	// Set borders and opacity for all layers (provinces)
	layer.setStyle({stroke : true, weight : 3, color : '#FFF', opacity : 0.2, fillOpacity : 1});
	
	if(layer.feature.properties.ID_1 == 979 && dataProvinces[0] != 0){	
		layer.setStyle({fillColor : getColor(dataProvinces[0])});
		layer.bindTooltip("Itä-Suomen lääni", {sticky: true});
    }
    else if(layer.feature.properties.ID_1 == 980 && dataProvinces[1] != 0){	
		layer.setStyle({fillColor : getColor(dataProvinces[1])});
		layer.bindTooltip("Lapin lääni, vastauksia: " + classes, {sticky: true});
    }
    else if(layer.feature.properties.ID_1 == 981 && dataProvinces[2] != 0){	
		layer.setStyle({fillColor : getColor(dataProvinces[2])});
		layer.bindTooltip("Oulun lääni", {sticky: true});
    }
    else if(layer.feature.properties.ID_1 == 982 && dataProvinces[3] != 0){	
		layer.setStyle({fillColor : getColor(dataProvinces[3])});
		layer.bindTooltip("Etelä-Suomen lääni", {sticky: true});
    }
    else if(layer.feature.properties.ID_1 == 983 && dataProvinces[4] != 0){	
		layer.setStyle({fillColor : getColor(dataProvinces[4])});
		layer.bindTooltip("Länsi-Suomen lääni", {sticky: true});
    }
    else{
    	layer.setStyle({fillColor : '${bg}'})
    	layer.bindTooltip(layer.feature.properties.VARNAME_1+"Ei vastauksia", {sticky: true});
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
		<span class="info-h">Vastauksia: ' + answerAmount(props.ID_1,0) + '</span><br />\
		<#if oldQuestion.colHead1??><span class="info-t">${oldQuestion.colHead1} : ' + answerAmount(props.ID_1,1) + '</span><br /></#if>\
		<#if oldQuestion.colHead2??><span class="info-t">${oldQuestion.colHead2} : ' + answerAmount(props.ID_1,2) + '</span><br /></#if>\
		<#if oldQuestion.colHead3??><span class="info-t">${oldQuestion.colHead3} : ' + answerAmount(props.ID_1,3) + '</span><br /></#if>\
		<#if oldQuestion.colHead4??><span class="info-t">${oldQuestion.colHead4} : ' + answerAmount(props.ID_1,4) + '</span><br /></#if>\
		<#if oldQuestion.colHead5??><span class="info-t">${oldQuestion.colHead5} : ' + answerAmount(props.ID_1,5) + '</span><br /></#if>\
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

