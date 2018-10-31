<html>
	<#include "head.ftl">	
	<body>
		<form action="/pastAnswers" method="POST" id="pastAmntOAnsws">
			<input type="hidden" name="uid" value="${answer.uid}">
			<input type="image" id="menu" src="/img/hamburger_white.png" onmouseover="this.src='/img/hamburger_blue.png'" onmouseout="this.src='/img/hamburger_white.png'" border="0" alt="Aikaisemmat vastaukset" />
		</form>
		<div class="centerer">
			<div class="page-wide">
			<#if resubmitError != true>	<#-- Jos käyttäjä ei ole vielä vastannut kysymykseen -->
				<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
					<form action="/newQuestion" method="POST">
						<input type="hidden" name="uid" value="${answer.uid}">
						<input type="submit" class="answer-button" value="Seuraava kysymys">
					</form>
				<#elseif unansweredQuestion.qid == 0>
					<h2>Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
				</#if>
			<#else>	<#-- Jos käyttäjä on jo vastannut kysymykseen (form resubmission) -->
				<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
					<h3>Olet jo vastannut tähän kysymykseen</h3>
					<form action="/newQuestion" method="POST">
						<input type="hidden" name="uid" value="${answer.uid}">
						<input type="submit" class="answer-button" value="Seuraava kysymys">
					</form>
				<#elseif unansweredQuestion.qid == 0>	<#-- Jos kysymättömiä kysymyksiä ei ole enää -->
					<h2>Olet jo vastannut tähän kysymykseen</h2>
					<h2>Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
				</#if>
			</#if>
			</div>
			<div class="page-wide">
				<h4>Kysymys oli</h4>
				<h2>${oldQuestion.question}</h2>
				<h4>Vastauksesi oli</h4>
				<h2>
				<#if answer.answer1 == "v1">	<#-- Change answer value to text-format for viewing -->
					${oldQuestion.colHead1}
				<#elseif answer.answer1 == "v2">
					${oldQuestion.colHead2}
				<#elseif answer.answer1 == "v3">
					${oldQuestion.colHead3}
				<#elseif answer.answer1 == "v4">
					${oldQuestion.colHead4}
				<#elseif answer.answer1 == "v5">
					${oldQuestion.colHead5}
				<#else>
					${answer.answer1}								
				</#if>
				</h2>	
			</div>
			<#if oldQuestion.type == 1 || oldQuestion.type == 3>
				<div class="column">
					<h4>Vastausten lukumäärä</h4>
					<h2>${oldQuestion.amntAnswTot}</h2>			
					<h4>Vastausten keskiarvo</h4>
					<h1>${oldQuestion.average?c}</h1>
					<h4>Vastausten mediaani</h4>
					<h2>TODO</h2>
				</div>
				<#if oldQuestion.type == 3>
					<#include "charts/bar_chart.ftl">
				</#if>	
			<#elseif oldQuestion.type == 4>
				<div class="column">
					<h4>Vastausten lukumäärä</h4>
					<h2>${oldQuestion.amntAnswTot}</h2>			
					<h4>Vastausten moodi</h4>
					<h2>TODO</h2>
				</div>
				<#include "charts/pie_chart.ftl">
				<#include "charts/map.ftl">
			</#if>
		</div>
	</body>
</html>