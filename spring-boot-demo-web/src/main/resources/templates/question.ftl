<html>
<#include "head.ftl">	
<body>
<div class="centerer">
	<h2>${uQ.question}</h2>
	<div id="tool-row">
		<form action="/answer" method="POST">
			<input type="hidden" name="uid" value="${uid}">
			<input type="hidden" name="qid" value="${uQ.qid}">
			<div id="input-tools"></div>
			<#if uQ.type == 1>
				<#include "input_tools/1_number.ftl">
			<#elseif uQ.type == 2>
				<#include "input_tools/2_range.ftl">
			<#elseif uQ.type == 3>
				<#include "input_tools/3_radio.ftl">
			<#elseif uQ.type == 4>
				<#include "input_tools/4_options.ftl">								
			</#if>
		</form>
	</div>
</div>
<span class="debug">Debug type = ${uQ.type}</span>
<span class="debug">Debug amount_answs = ${uQ.amntAnswTot}</span>
<span class="debug">Debug question.qid: ${uQ.qid}</span>
<span class="debug">Debug answer.uid: ${uid}</span>
</body>
</html>