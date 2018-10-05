<script>
for (i = 1; i <= ${uQ.amount}; i++){
	var input_tool_container = document.createElement("input-tool-container");
	input_tool_container.innerHTML = "<input type='radio' name='answer' value='${uq.value_"+i+"}'>Value = value_" + i + "<br>";
    var input_tools = document.getElementById("input-tools");
	input_tools.appendChild(input_tool_container);
}
</script>