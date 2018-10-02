<html>
	<#include "head.ftl">	
	<body>
		<div class="container">
			<form action="/pastAnswers" method="POST" id="pastAmntOAnsws">
				<input type="hidden" name="uid" value="${answer.uid}">
				<input type="submit" value="Aikaisemmat vastaukset">
			</form>
				Kysymys oli:
				<br>
				<h3>${oldQuestion.question}</h3>
				<br>
				Vastauksesi oli:
				<br>
				<h1>${answer.answer}</h1>
				<br>
				Average answer was:
				<br>
				<h2>${oldQuestion.average}</h2>
				<br>
				<#if resubmitError != true>	<#-- Jos käyttäjä ei ole vielä vastannut kysymykseen -->
					<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
						<form action="/newQuestion" method="POST">
							<input type="hidden" name="uid" value="${answer.uid}">
							<input type="submit" value="Seuraava kysymys">
						</form>
					<#else>
						<h2>Hienoa! Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
					</#if>
				<#else>	<#-- Jos käyttäjä on jo vastannut kysymykseen (form resubmission) -->
					<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
						<h2>Olet jo vastannut tähän kysymykseen.</h2>
						<form action="/newQuestion" method="POST">
							<input type="hidden" name="uid" value="${answer.uid}">
							<input type="submit" value="Seuraava kysymys">
						</form>
					<#else>
						<h2>Hienoa! Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
					</#if>
				</#if>
		</div>
	</body>
</html>