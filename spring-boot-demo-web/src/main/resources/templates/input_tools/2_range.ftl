<script>
for (i = 1; i <= ${uQ.amount}; i++){
	var input_tool_container = document.createElement("input-tool-container");
	input_tool_container.innerHTML = "<input type='range' name='answer' min='${uQ.min}' max='${uQ.max}' value='${uQ.def_val}'>${uQ.value_1}${uQ.value_2}Type 2 range";
    var input_tools = document.getElementById("input-tools");
	input_tools.appendChild(input_tool_container);
}
</script>