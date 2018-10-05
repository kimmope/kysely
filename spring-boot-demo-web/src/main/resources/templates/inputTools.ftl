<div id="input-tools"></div>
<script>
window.addEventListener("load", function() {
	function selectInputTool(type){
		if (type == 1){
			inputTool = "<div class='input-top-label'>" + selectLabel(i) + "</div><input class="input-number" type='number' name='answer' maxlength='"${uQ.maxlength}"' min='${uQ.min}' max='${uQ.max}' step='${uQ.step}' value='${uQ.def_val}'>;
		}
		else if (type == 2){
			inputTool = "<div class='input-min-val'>${uQ.min_val}</div><input class="input-range" type='range' name='answer' min='${uQ.min}' max='${uQ.max}' value='"+selectDefVal(i)+"'><div class='input-max-val'>${uQ.max_val}</div>";
		}
		else if (type == 3){
			inputTool = "<input class="input-radio" type='radio' name='answer' value='"+selectDefVal(i)+"'><div class='input-radio-label'>" + selectLabel(i) + "</div>";
		}
		return inputTool;
	}
	
	function selectLabel(round){
		var value;
		if (round == 1) {
			value = ${uQ.label_1};
		}
		else if (round == 2) {
			value = ${uQ.label_2};
		}
		else if (round == 3) {
			value = ${uQ.label_3};
		}	
		else if (round == 4) {
			value = ${uQ.label_4};
		}	
		else if (round == 5) {
			value = ${uQ.label_5};
		}	
		else if (round == 6) {
			value = ${uQ.label_6};
		}	
		else if (round == 7) {
			value = ${uQ.label_7};
		}
		return value;	
	}
	
	function selectDefVal(round){
		var value;
		if (round == 1) {
			value = ${uQ.def_val_1};
		}
		else if (round == 2) {
			value = ${uQ.def_val_2};
		}
		else if (round == 3) {
			value = ${uQ.def_val_3};
		}	
		else if (round == 4) {
			value = ${uQ.def_val_4};
		}	
		else if (round == 5) {
			value = ${uQ.def_val_5};
		}	
		else if (round == 6) {
			value = ${uQ.def_val_6};
		}	
		else if (round == 7) {
			value = ${uQ.def_val_7};
		}
		return value;	
	}	
	
	function printInputTools(SelectInputTool(${uQ.type})){
		for (i = 1; i <= ${uq.amount}; i++){
			var input_tool_container = document.createElement("input-tool-container");
			input_tool_container.innerHTML = inputTool;
		    var input_tools = document.getElementById("input-tools");
			input_tools.appendChild(input_tool_container);
		}
	}
	
}, false);
</script>