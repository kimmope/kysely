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
			<#if answer.answer1 == "v1">	<#-- Change answer value to text-format for viewing -->
				<h1>${oldQuestion.colHead1}</h1>
			<#elseif answer.answer1 == "v2">
				<h1>${oldQuestion.colHead2}</h1>
			<#elseif answer.answer1 == "v3">
				<h1>${oldQuestion.colHead3}</h1>
			<#elseif answer.answer1 == "v4">
				<h1>${oldQuestion.colHead4}</h1>
			<#elseif answer.answer1 == "v5">
				<h1>${oldQuestion.colHead5}</h1>
			<#else>
				<h1>${answer.answer1}</h1>								
			</#if>			
			<br>
			Vastauksien lukumäärä:
			<br>
			<h2>${oldQuestion.amntAnswTot}</h2>
			<br>
			<#if oldQuestion.type == 1 || oldQuestion.type == 2>
				Vastauksien keskiarvo:
				<h2>${oldQuestion.average?c}</h2>
			<#elseif oldQuestion.type == 3>
				<#include "charts/bar_chart.ftl">
				<#include "charts/pie_chart.ftl">
				<#include "charts/map.ftl">
			</#if>
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
				<#elseif unansweredQuestion.qid == 0>	<#-- Jos kysymättömiä kysymyksiä ei ole enää -->
					<h2>Olet jo vastannut tähän kysymykseen.</h2>
					<h2>Hienoa! Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
				</#if>
			</#if>
		</div>
	</body>
</html>