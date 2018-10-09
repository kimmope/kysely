<script>

var defVals = [];
var rowLabels = [];

<#-- Values to JavaScript variables -->
<#if uQ.defVal2??>defVals[2] = ${uQ.defVal2};</#if>
<#if uQ.defVal3??>defVals[3] = ${uQ.defVal3};</#if>
<#if uQ.defVal4??>defVals[4] = ${uQ.defVal4};</#if>
<#if uQ.defVal5??>defVals[5] = ${uQ.defVal5};</#if>
<#if uQ.rowHead2??>rowLabels[2] = ${uQ.rowHead2};</#if>
<#if uQ.rowHead3??>rowLabels[3] = ${uQ.rowHead3};</#if>
<#if uQ.rowHead4??>rowLabels[4] = ${uQ.rowHead4};</#if>
<#if uQ.rowHead5??>rowLabels[5] = ${uQ.rowHead5};</#if>

<#-- Create first input tool -->
document.getElementById("input-tools").innerHTML = 
	`<#if uQ.colHead1??><div id = 'columnLabel'>${uQ.colHead1}</div></#if>
	<#if uQ.rowHead1??><div id = 'rowLabel'>${uQ.rowHead1}</div></#if>
	<input class = 'inputRange' type = 'range' name = 'answer' 
		<#if uQ.defVal1??>value='${uQ.defVal1}'</#if>
		<#if uQ.min??>min = '${uQ.min}'</#if>
		<#if uQ.min??>max = '${uQ.max}'</#if>
		<#if uQ.defVal1??>value = '${uQ.defVal1}'</#if>>`;

<#-- Create rest of the input tools if available-->
for (i = 2; i <= ${uQ.amount}; i++){
	var input_tool_container = document.createElement("input-tool-container");
	input_tool_container.innerHTML = `<div id='rowLabel'>` + rowLabels[i] + `</div>
		<input class='inputRadio' type='radio' name='answer' value='` + defVals[i] + `'>`;	
    var input_tools = document.getElementById("input-tools");
	input_tools.appendChild(input_tool_container);
}	

</script>
<br>
