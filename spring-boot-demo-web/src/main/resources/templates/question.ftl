<html>
	<#include "head.ftl">	
	<body>
		<span id="question">${uQ.question}</span><br>
		type = ${uQ.type}<br>
		amount_answs = ${uQ.amountAnswrs}<br>
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
			</#if>
			<input type="submit" value="Vastaa">
		</form>
		<br>
		<br><span id="qid">Debug question.qid: ${uQ.qid}</span>
		<br><span id="qid">Debug answer.uid: ${uid}</span>
	</body>
</html>