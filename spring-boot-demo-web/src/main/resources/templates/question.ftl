<html>
	<#include "head.ftl">	
	<body>
		<span id="question">${uQ.question}</span><br>
		type = ${uQ.type}<br>
		amount_answs = ${uQ.amount_answs}<br>
		<form action="/answer" method="POST">
			<input type="hidden" name="uid" value="${uid}">
			<input type="hidden" name="qid" value="${uQ.qid}">
			<#include "inputTools.ftl">	
			<input type="submit" value="Vastaa">
		</form>
		<br>
		<br><span id="qid">Debug question.qid: ${uQ.qid}</span>
		<br><span id="qid">Debug answer.uid: ${uid}</span>
	</body>
</html>