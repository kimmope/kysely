<script>
document.getElementById("input-tools").innerHTML = 
	`<input class="number-input" type='number' name='answer1' maxlength='${uQ.maxLength}' 
	min='${uQ.min}' max='${uQ.max}' step='${uQ.step}' 
	<#if uQ.defVal1??>value = '${uQ.defVal1}'</#if>>
	<#if uQ.colHead1??><div id='numberInputValue'>${uQ.colHead1}</div></#if>`;
</script>
<input type="submit" class="answer-button" value="Vastaa">