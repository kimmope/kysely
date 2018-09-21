<html>
	<#include "head.ftl">	
	<body>
		<div class="container">
			<br>
			<a href="/options/${answer.uid}" id="options">Options</a>
			<br>
			Question was:
			<br>
			<h3>${question.question}</h3>
			<br>
			Your answer was:
			<br>
			<h1>${answer.answer}</h1>
			<br>
			Average answer was:
			<br>
			<h2>${question.average}</h2>
			<br>
			<#if nextQuestion.qid != 0>
				<a href="/newQuestion/${answer.uid}">Next question</a>
			<#else>
				<h1>Vastasit viimeiseen kysymykseen. Tervetuloa uudelleen ensi viikolla!</h1>
			</#if>
			<br>
			<form><#-- For storing the uid-data. IS THIS REALLY USED???--> 
				<input type="hidden" name="uid" value="${answer.uid}>
			</form>
		</div>
	</body>
</html>