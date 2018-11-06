<link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>
<div class="page-wide">
	<div id="map"></div>
</div>
<script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js" integrity="sha512-nMMmRyTVoLYqjP9hrbed9S+FzjZHW5gY1TWCHA5ckwXZBadntCNs8kEqAWdrb9O7rxbCaA4lKTIWjDXZxflOcA==" crossorigin=""></script>
<script src="/maps/profinland.geojson" type="text/javascript"></script>

<script>

// KUNKIN LÄÄNIN ENITEN OSUMIA SAANEET LUOKAT TAULUKKOON
var dataProvinces = [];
<#if answerStats.classModeP1??>dataProvinces[0] = "${answerStats.classModeP1}";</#if>
<#if answerStats.classModeP2??>dataProvinces[1] = "${answerStats.classModeP2}";</#if>
<#if answerStats.classModeP3??>dataProvinces[2] = "${answerStats.classModeP3}";</#if>
<#if answerStats.classModeP4??>dataProvinces[3] = "${answerStats.classModeP4}";</#if>
<#if answerStats.classModeP5??>dataProvinces[4] = "${answerStats.classModeP5}";</#if>
// REMOVE EXTRA CHARACTERS FROM THE BEGINNING OF THE CLASS NAME (E.G. AO02 = 2)
for (var i = 0; i < dataProvinces.length; i++) { 
	dataProvinces[i] = dataProvinces[i].slice(3);
}

// KUNKIN LÄÄNIN VASTAUSTEN YHTEISLUKUMÄÄRÄT TAULUKKOON
var amntProvinces = [];
<#if answerStats.amountP1??>amntProvinces[0] = ${answerStats.amountP1};</#if>
<#if answerStats.amountP2??>amntProvinces[1] = ${answerStats.amountP2};</#if>
<#if answerStats.amountP3??>amntProvinces[2] = ${answerStats.amountP3};</#if>
<#if answerStats.amountP4??>amntProvinces[3] = ${answerStats.amountP4};</#if>
<#if answerStats.amountP5??>amntProvinces[4] = ${answerStats.amountP5};</#if>

// LUOKKIEN NIMET TAULUKKOON
var classes = [];
<#if oldQuestion.colHead1??>classes[0] = '${oldQuestion.colHead1}';</#if>
<#if oldQuestion.colHead2??>classes[1] = '${oldQuestion.colHead2}';</#if>
<#if oldQuestion.colHead3??>classes[2] = '${oldQuestion.colHead3}';</#if>
<#if oldQuestion.colHead4??>classes[3] = '${oldQuestion.colHead4}';</#if>
<#if oldQuestion.colHead5??>classes[4] = '${oldQuestion.colHead5}';</#if>

// DEBUG
console.log("dataProvinces[0] = " + dataProvinces[0]);
console.log("dataProvinces[1] = " + dataProvinces[1]);
console.log("(dataProvinces[0]-1) = " + (dataProvinces[0]-1));
console.log("classes.length = " + classes.length);


// Return correct amounts of answers per province to info-window
function answerAmount(provinceId){
	if(provinceId == 979){
		return amntProvinces[0];
	}
	else if(provinceId == 980){
		return amntProvinces[1];
	}
	else if(provinceId == 981){
		return amntProvinces[2];
	}
	else if(provinceId == 982){
		return amntProvinces[3];
	}
	else if(provinceId == 983){
		return amntProvinces[4];
	}		
}

// Load geoJSON
var finProvinces = L.geoJSON(profinland);


// Create basemap
var map = L.map('map').setView([65.5, 29], null, { zoomControl:false });


// Set up basemap
L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    maxZoom: 5,
    minZoom: 5,
    id: 'mapbox.empty',
    accessToken: 'pk.eyJ1Ijoia2ltbW9rIiwiYSI6ImNqbmQ1N3VmMTA0NTczcG80bDQyMjY5aTgifQ.WKyJF6CXGleSTqE9gzuv3w'
}).addTo(map);


// Remove Leaflet-label
document.getElementsByClassName( 'leaflet-control-attribution' )[0].style.display = 'none';
// Remove zoom controls
map.zoomControl.remove();
// Disable dragging
map.dragging.disable();


// Select color according to the normalized value (1-5 - 1-2) and amount of classes
// http://mathforum.org/library/drmath/view/60433.html
function getNormalizedColor(c){
	if(classes.length == 5){
		return c > 4.2 ? '#08519C':
	   	c > 3.4  ? '#3182BD':
	   	c > 2.6  ? '#6BAED6':
	   	c > 1.8  ? '#BDD7E7':
	   	'#EFF3FF';	
	}
	else if(classes.length == 4){
		return c > 3.25 ? '#2171b5':
	   	c > 2.5  ? '#6BAED6':
	   	c > 1.75 ? '#BDD7E7':
	   	'#EFF3FF';		
	}
	else if(classes.length == 3){
		return c > 2.33 ? '#3182BD':
	   	c > 1.66 ? '#9ECAE1':
	   	'#DEEBF7';		
	}
	else if(classes.length == 2){
		return c > 1.5 ? '#3182BD':
	   	'#DEEBF7';		
	}
}

// Select color according to amount of classes
function getColor(c){
	if(classes.length == 5){
		return c == 4 ? '#08519C':
	   	c == 3 ? '#3182BD':
	   	c == 2 ? '#6BAED6':
	   	c == 1 ? '#BDD7E7':
	   	'#EFF3FF';	
	}
	else if(classes.length == 4){
		return c == 3 ? '#2171b5':
	   	c == 2  ? '#6BAED6':
	   	c == 1 ? '#BDD7E7':
	   	'#EFF3FF';		
	}
	else if(classes.length == 3){
		return c == 2 ? '#3182BD':
	   	c == 1 ? '#9ECAE1':
	   	'#DEEBF7';		
	}
	else if(classes.length == 2){
		return c == 1 ? '#3182BD':
	   	'#DEEBF7';		
	}
}


// Loop polygons (features/layers/provinces) in multipolygon (featureset) for coloring
// NOTE : ID-order of layers makes the order of provinces in database
finProvinces.eachLayer(function(layer){
	// Set borders and opacity for all layers (provinces)
	layer.setStyle({stroke : true, weight : 3, color : '#FFF', opacity : 0.2, fillOpacity : 1});
	if(layer.feature.properties.ID_1 == 979 && typeof dataProvinces[0] !== 'undefined'){	
		layer.setStyle({fillColor : getColor((dataProvinces[0]-1))});
		layer.bindTooltip("Itä-Suomen lääni", {sticky: true});
    }
    else if(layer.feature.properties.ID_1 == 980 && typeof dataProvinces[1] !== 'undefined'){	
		layer.setStyle({fillColor : getColor((dataProvinces[1]-1))});
		layer.bindTooltip("Lapin lääni, vastauksia: " + classes, {sticky: true});
    }
    else if(layer.feature.properties.ID_1 == 981 && typeof dataProvinces[2] !== 'undefined'){	
		layer.setStyle({fillColor : getColor((dataProvinces[2]-1))});
		layer.bindTooltip("Oulun lääni", {sticky: true});
    }
    else if(layer.feature.properties.ID_1 == 982 && typeof dataProvinces[3] !== 'undefined'){	
		layer.setStyle({fillColor : getColor((dataProvinces[3]-1))});
		layer.bindTooltip("Etelä-Suomen lääni", {sticky: true});
    }
    else if(layer.feature.properties.ID_1 == 983 && typeof dataProvinces[4] !== 'undefined'){	
		layer.setStyle({fillColor : getColor((dataProvinces[4]-1))});
		layer.bindTooltip("Länsi-Suomen lääni", {sticky: true});
    }
    else{
    	layer.setStyle({fillColor : '#679ef7'})
    	layer.bindTooltip(layer.feature.properties.VARNAME_1+"Ei vastauksia", {sticky: true});
    }
});


// Add provinces with colored data
finProvinces.addTo(map);


// Add info-window to the map (top right corner)
var info = L.control();

info.onAdd = function(map){
	this._div = L.DomUtil.create('div','info');
	this.update();
	return this._div;
};

info.update = function(props){
	this._div.innerHTML = props ? '<span class="legend-text"><b>' + props.VARNAME_1 + '</b></span><br /><span class="legend-text">Vastauksia: ' + answerAmount(props.ID_1) + '</span><br />'
        : '<span class="legend-text">Vie hiiri läänin päälle</span>';
};

info.addTo(map);

// Add onmouseover highlight region feature
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

// Add onmouseover functionality to provinces
geojson = L.geoJson(profinland, {
    style: style,
    onEachFeature: onEachFeature
}).addTo(map);


// Add legend to the map (bottom right corner)
//var legend = L.control({position: 'bottomright'});
//legend.onAdd = function (map) {
//    var div = L.DomUtil.create('div', 'info legend');
    // loop through our density intervals and generate a label with a colored square for each interval
//    for (var i = 0; i < classes.length; i++) {
//        div.innerHTML +=
//            '<i style="background:' + getColor(i) + '"></i><span class="legend-text">' + classes[i] + '</span><br>';
//    }
//   return div;
//};
//legend.addTo(map);

</script>

