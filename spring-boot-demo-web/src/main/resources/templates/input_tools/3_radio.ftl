<script>

<#-- Input tool header row -->
var inputTable = document.createElement("TABLE");
inputTable.setAttribute("id","inputTable");
document.getElementById("input-tools").appendChild(inputTable);
var inputTableRow = document.createElement("TR");
inputTableRow.innerHTML = 
	`<th class="empty"></th>
	<#if uQ.colHead1??><th class='columnLabel'>${uQ.colHead1}</th></#if>
	<#if uQ.colHead2??><th class='columnLabel'>${uQ.colHead2}</th></#if>
	<#if uQ.colHead3??><th class='columnLabel'>${uQ.colHead3}</th></#if>
	<#if uQ.colHead4??><th class='columnLabel'>${uQ.colHead4}</th></#if>
	<#if uQ.colHead5??><th class='columnLabel'>${uQ.colHead5}</th></#if>`;
document.getElementById("inputTable").appendChild(inputTableRow);

<#-- Input tool content row(s) -->
for (i = 1; i <= ${uQ.amount}; i++){
	var inputTableRow = document.createElement("TR");
	inputTableRow.innerHTML = 
		`<#if uQ.rowHead1??><td class = 'rowLabel'>${uQ.rowHead1}</td>
		<#else><td class="empty"></td>
		</#if>
		<#if uQ.colHead1??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='${uQ.defVal1}'></td></#if>
		<#if uQ.colHead2??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='${uQ.defVal2}'></td></#if>
		<#if uQ.colHead3??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='${uQ.defVal3}'></td></#if>
		<#if uQ.colHead4??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='${uQ.defVal4}'></td></#if>
		<#if uQ.colHead5??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='${uQ.defVal5}'></td></#if>`;	
	document.getElementById("inputTable").appendChild(inputTableRow);
}	

</script>
<br>