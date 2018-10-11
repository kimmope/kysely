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
			<#if answer.answer == "v1">	<#-- Change answer value to text-format for viewing -->
				<h1>${oldQuestion.colHead1}</h1>
			<#elseif answer.answer == "v2">
				<h1>${oldQuestion.colHead2}</h1>
			<#elseif answer.answer == "v3">
				<h1>${oldQuestion.colHead3}</h1>
			<#elseif answer.answer == "v4">
				<h1>${oldQuestion.colHead4}</h1>
			<#elseif answer.answer == "v5">
				<h1>${oldQuestion.colHead5}</h1>
			<#else>
				<h1>${answer.answer}</h1>								
			</#if>			
			<br>
			Vastauksien lukumäärä:
			<br>
			<h2>${oldQuestion.amountAnswrs}</h2>
			<br>
			Vastauksien keskiarvo:
			<br>
			<h2>${oldQuestion.average}</h2>
			<br>
			Vastauksen tyyppi:
			<br>
			<h2>${oldQuestion.type}</h2>
			<br>
			Vastauksen qid:
			<br>
			<h2>${oldQuestion.qid}</h2>
			<br>
			Vastauksien summa:						
			<br>
			<h2>${oldQuestion.sumAnswers}</h2>			
			<br>
			<#if resubmitError != true>	<#-- Jos käyttäjä ei ole vielä vastannut kysymykseen -->
				<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
					<form action="/newQuestion" method="POST">
						<input type="hidden" name="uid" value="${answer.uid}">
						<input type="submit" value="Seuraava kysymys">
					</form>
				<#elseif unansweredQuestion.qid == 0>
					<h2>Hienoa! Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
				</#if>
			<#else>	<#-- Jos käyttäjä on jo vastannut kysymykseen (form resubmission) -->
				<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
					<h2>Olet jo vastannut tähän kysymykseen.</h2>
					<form action="/newQuestion" method="POST">
						<input type="hidden" name="uid" value="${answer.uid}">
						<input type="submit" value="Seuraava kysymys">
					</form>
				<#elseif unansweredQuestion.qid == 0>
					<h2>Olet jo vastannut tähän kysymykseen.</h2>
					<h2>Hienoa! Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
				</#if>
			</#if>
		</div>
	</body>
</html>