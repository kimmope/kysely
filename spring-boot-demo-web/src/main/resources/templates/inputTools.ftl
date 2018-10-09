<div id="input-tools"></div>
<script>
window.addEventListener("load", function() {
	
	selectInputTool(${uQ.type});
	
	function selectInputTool(type){
		console.log("1");
		var inputTool;
		if (type == 1){
			inputTool = "fuuck";
			console.log("2");
		}
		else if (type == 2){
			inputTool = "shiit";
		}
		else if (type == 3){
			inputTool = "diik";
		}
		printInputTools(inputTool);
	}
	
	function selectLabel(round){
		var value ="";
		if (round == 1) {
			<#if (uQ.value_1)??>value = ${uQ.value_1}</#if>;
		}
		else if (round == 2) {
			<#if (uQ.value_2)??>value = ${uQ.value_2}</#if>;
		}
		return value;
	}	
	
	function printInputTools(inputTool){
		console.log("3");
		for (i = 1; i <= ${uQ.amount}; i++){
			var input_tool_container = document.createElement("input-tool-container");
			input_tool_container.innerHTML = inputTool;
		    var input_tools = document.getElementById("input-tools");
			input_tools.appendChild(input_tool_container);
			console.log("4");
		}
	}

}, false);
</script>