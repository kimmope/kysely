window.addEventListener("load", function() {
    var f = document.getElementById('jstest');
    setInterval(function() {
        f.style.opacity = (f.style.opacity == '0' ? '1' : '0');
    }, 1000);

}, false);