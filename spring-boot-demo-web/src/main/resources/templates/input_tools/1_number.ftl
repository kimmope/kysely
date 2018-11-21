<script>
document.getElementById("input-tools").innerHTML = 
	`<input class="number-input" type="number" name="answer" maxlength="${uQ.maxLength?c}" 
	min="${uQ.min?c}" max="${uQ.max?c}" step="${uQ.step?c}" 
	<#if uQ.defVal1??>value = "${uQ.defVal1}"</#if>>
	<#if uQ.colHead1??><div id="numberInputValue">${uQ.colHead1}</div></#if>`;
	console.log("min = ",${uQ.min?c}," max = ",${uQ.max?c});
</script>
<input type="submit" class="answer-button" value="Lähetä vastaus">