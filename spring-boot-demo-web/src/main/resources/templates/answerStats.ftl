<html>
	<#include "head.ftl">	
	<body>
		<div class="container">
			<form action="/pastAnswers" method="POST" id="pastAmntOAnsws">
				<input type="hidden" name="uid" value="${answer.uid}">
				<input type="submit" value="Aikaisemmat vastaukset">
			</form>
			<#if unansweredQuestion.qid != 0>			
				Question was:
				<br>
				<h3>${oldQuestion.question}</h3>
				<br>
				Your answer was:
				<br>
				<h1>${answer.answer}</h1>
				<br>
				Average answer was:
				<br>
				<h2>${oldQuestion.average}</h2>
				<br>
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