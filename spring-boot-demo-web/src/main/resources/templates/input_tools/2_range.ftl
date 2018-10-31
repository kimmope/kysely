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
	`<#if uQ.colHead1??><div class='rangeLabelLeft'>${uQ.colHead1}</div></#if>
	<#if uQ.colHead2??><div class='rangeLabelRight'>${uQ.colHead2}</div></#if>
	<#if uQ.rowHead1??><div id='rowLabel1'>${uQ.rowHead1}</div></#if>
	<input type='range' name='answer' id='rangeId' class='inputRange' oninput='rangeValue.value = rangeId.value'
		<#if uQ.min??>min='${uQ.min}'</#if>
		<#if uQ.min??>max='${uQ.max}'</#if>
		<#if uQ.step??>step='${uQ.step}'</#if>
		<#if uQ.defVal1??>value='${uQ.defVal1}'</#if>>
		<output name="NA" id="rangeValue">50</output>`;

<#-- Create rest of the input tools if available
for (i = 2; i <= ${uQ.subQuestAmnt}; i++){
	var input_tool_container = document.createElement("input-tool-container");
	input_tool_container.innerHTML = `<div id='rowLabel` + i + `'>` + rowLabels[i] + `</div>
		<input class='inputRadio' type='radio' name='answer' value='` + defVals[i] + `'>`;	
    var input_tools = document.getElementById("input-tools");
	input_tools.appendChild(input_tool_container);
}	-->

</script>
<input type="submit" class="answer-button" value="Lähetä vastauksesi">
