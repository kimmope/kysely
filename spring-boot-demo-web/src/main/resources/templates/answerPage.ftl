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
				<#if answer.answer == "AO01">	<#-- Change answer value to text-format for viewing -->
					${oldQuestion.colHead1}
				<#elseif answer.answer == "AO02">
					${oldQuestion.colHead2}
				<#elseif answer.answer == "AO03">
					${oldQuestion.colHead3}
				<#elseif answer.answer == "AO04">
					${oldQuestion.colHead4}
				<#elseif answer.answer == "AO05">
					${oldQuestion.colHead5}
				<#else>
					${answer.answer}								
				</#if>
				</h2>	
			</div>
			<#if oldQuestion.type == 1 || oldQuestion.type == 2>
				<div class="column">
					<h4>Vastausten lukumäärä</h4>
					<h2>${oldQuestion.amntAnswTot}</h2>
					<h4>Vastausten keskiarvo</h4>
					<h1>${oldQuestion.average?c}</h1>
					<h4>Vastausten mediaani</h4>
					<h2>TODO</h2>
					<h2>TESTIARVOJA</h4>
					<h4>Keskiarvo:${answerStats.meanAll?c}</h2>
					<h4>Mediaani(VIELÄ MEAN):${answerStats.mediAll}</h2>
					<h4>Moodi:${answerStats.modeAll?c}</h2>
					<h4>Luokkamoodi:${answerStats.classModeAll}</h2>
					<h4>Kolmoslääni keskiarvo:${answerStats.meanP3?c}</h2>
					<h4>Kolmoslääni mediaani(VIELÄ MEAN):${answerStats.mediP3}</h2>
					<h4>Kolmoslääni moodi:${answerStats.modeP3?c}</h2>
					<h4>Kolmoslääni luokkamoodi:${answerStats.classModeP3}</h2>
				</div>
			<#elseif oldQuestion.type == 4 || oldQuestion.type == 5>
				<div class="column">
					<h4>Vastausten lukumäärä</h4>
					<h2>${oldQuestion.amntAnswTot}</h2>
					<h4>Vastausten moodi</h4>
					<h2>TODO</h2>
					<h2>TESTIARVOJA</h4>
					<h4>Keskiarvo:${answerStats.meanAll}</h2>
					<h4>Mediaani(VIELÄ MEAN):${answerStats.mediAll}</h2>
					<h4>Moodi:${answerStats.modeAll}</h2>
					<h4>Luokkamoodi:${answerStats.classModeAll}</h2>					
					<h4>Kolmoslääni keskiarvo:${answerStats.meanP3?c}</h2>
					<h4>Kolmoslääni mediaani(VIELÄ MEAN):${answerStats.mediP3}</h2>
					<h4>Kolmoslääni mode:${answerStats.modeP3?c}</h2>
					<h4>Kolmoslääni luokkamoodi:${answerStats.classModeP3}</h2>					
					<h4>Kolmoslääni luokka1:${answerStats.class1P3}</h2>
					<h4>Kolmoslääni luokka2:${answerStats.class2P3}</h2>	
					<h4>Kolmoslääni luokka3:${answerStats.class3P3}</h2>
					<h4>Kolmoslääni luokka4:${answerStats.class4P3}</h2>
					<h4>Kolmoslääni luokka5:${answerStats.class5P3}</h2>
				</div>
				<#if oldQuestion.type == 4>
					<#include "charts/pie_chart.ftl">
				<#elseif oldQuestion.type == 5>				
					<#include "charts/bar_chart.ftl">
				</#if>
				<#include "charts/map.ftl">
			</#if>
		</div>
	</body>
</html>