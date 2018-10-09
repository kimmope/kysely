<script>
document.getElementById("input-tools").innerHTML = 
	`<input class='inputNumber' type='number' name='answer' maxlength='${uQ.maxLength}' 
	min='${uQ.min}' max='${uQ.max}' step='${uQ.step}' 
	<#if uQ.defVal1??>value = '${uQ.defVal1}'</#if>>
	<#if uQ.colHead1??><div id='numberInputValue'>${uQ.colHead1}</div></#if>`;
</script>
<br>