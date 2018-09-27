<html>
	<#include "head.ftl">	
	<body>
		<span id="question">${unansweredQuestion.question}</span>
		<form action="/answer" method="POST">
			<input type="hidden" name="uid" value="${uid}">
			<input type="hidden" name="qid" value="${unansweredQuestion.qid}">			
			<input type="text" name="answer">
			<br>
			<input type="submit" value="Vastaa">
		</form>
		<br>
		<br><span id="qid">Debug question.qid: ${unansweredQuestion.qid}</span>
		<br><span id="qid">Debug answer.uid: ${uid}</span>
	</body>
</html>