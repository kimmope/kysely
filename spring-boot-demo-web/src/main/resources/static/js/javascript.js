function test(){
	document.getElementById("input-tools").innerHTML = `<input class='input-number' type='number' name='answer' maxlength='${uQ.maxlength}' min='${uQ.min}' max='${uQ.max}' step='${uQ.step}' value='${uQ.def_val}'>`;	
}
