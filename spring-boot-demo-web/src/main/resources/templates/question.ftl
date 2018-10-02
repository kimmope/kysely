<html>
	<#include "head.ftl">	
	<body>
		<span id="question">${uQ.question}</span>
		<form action="/answer" method="POST">
			<input type="hidden" name="uid" value="${uid}">
			<input type="hidden" name="qid" value="${uQ.qid}">
			<#if uQ.type==number>
				<input type="text" name="answer">
			<#elseif uq.type==range>
				<input type="range" min="1" max="100" value="50">
			</#if>
			<br>
			<input type="submit" value="Vastaa">
		</form>
		<br>
		<br><span id="qid">Debug question.qid: ${uQ.qid}</span>
		<br><span id="qid">Debug answer.uid: ${uid}</span>
	</body>
</html>