<link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>

<div id="map"></div>

<script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js" integrity="sha512-nMMmRyTVoLYqjP9hrbed9S+FzjZHW5gY1TWCHA5ckwXZBadntCNs8kEqAWdrb9O7rxbCaA4lKTIWjDXZxflOcA==" crossorigin=""></script>
<#--

<script src="/js/leaflet072/leaflet_ajax_min.js">
<script src="/maps/testi.geojson" type="text/javascript"></script>

-->
<script src="/maps/finlandProvinces.geojson"></script>

<script>

var map = L.map('map').setView([65.1, 25.46], 5);

<#-- var finlandProvinces = new L.GeoJSON.AJAX("maps/finland-provinces.json"); -->

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox.streets',
    accessToken: 'pk.eyJ1Ijoia2ltbW9rIiwiYSI6ImNqbmQ1N3VmMTA0NTczcG80bDQyMjY5aTgifQ.WKyJF6CXGleSTqE9gzuv3w'
}).addTo(map);

<#--
finlandProvinces.addTo(map);
map.zoomControl.remove();
map.dragging.disable();

var polygon = L.polygon([
    [65.1, 25.4],
    [65.8, 25.4],
    [65.4, 25.8]
]).addTo(map);
 
L.geoJson(testi).addTo(map);
var layer = L.geoJSON(testi).addTo(map);
--> 

L.geoJson(finlandProvinces).addTo(map);

</script>
<br>