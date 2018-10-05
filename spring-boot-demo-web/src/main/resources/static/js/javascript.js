window.addEventListener("load", function() {
	document.getElementById("testi").innerHTML="Toimiiko tää";	
	var testi = "${uQ.amount}";
	document.getElementById("input-tools").innerHTML="Toimiiko: " + testi;
}, false);