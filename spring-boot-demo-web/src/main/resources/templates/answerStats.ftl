<html>
	<#include "head.ftl">	
	<body>
		<div class="container">
			<br>
			<a href="/pastAnswers/${answer.uid}" id="pastAnswers">Past answers</a>
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
				<form action="/newQuestion" method="POST">
					<input type="hidden" name="uid" value="${answer.uid}">
					<input type="submit" value="Seuraava kysymys">
				</form>
			<#else>
				<h2>Hienoa! Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
			</#if>
		</div>
	</body>
</html>