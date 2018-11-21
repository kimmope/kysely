<script>

var defVals = [];
var rowLabels = [];

<#-- Create input tool -->
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

</script>
<input type="submit" class="answer-button" value="Lähetä vastaus">