<script>

<#-- Input tool header row -->
var inputTable = document.createElement("TABLE");
inputTable.setAttribute("id","inputTable");
document.getElementById("input-tool").appendChild(inputTable);
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
		`	<#if uQ.rowHead1??><td class = 'rowLabel'>${uQ.rowHead1}</td>
			<#else><td class="empty"></td>
			</#if>
			<#if uQ.colHead1??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='` + defVals[i] + `'></td></#if>
			<#if uQ.colHead2??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='` + defVals[i] + `'></td></#if>
			<#if uQ.colHead3??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='` + defVals[i] + `'></td></#if>
			<#if uQ.colHead4??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='` + defVals[i] + `'></td></#if>
			<#if uQ.colHead5??><td class='inputData'><input class='inputRadio' type='radio' name='answer' value='` + defVals[i] + `'></td></#if>`;	
    document.getElementById("inputTable").appendChild(inputTableRow);
}	

<#-- Close input tool table -->
document.getElementById("input-tools").appendChild(`</table>`);

</script>
<br>