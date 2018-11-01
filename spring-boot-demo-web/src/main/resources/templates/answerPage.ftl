<html>
	<#include "head.ftl">
	<body>
		<form action="/pastAnswers" method="POST" id="pastAmntOAnsws">
			<input type="hidden" name="uid" value="${answer.uid}">
			<input type="image" id="menu" src="/img/hamburger_white.png" onmouseover="this.src='/img/hamburger_blue.png'" onmouseout="this.src='/img/hamburger_white.png'" border="0" alt="Aikaisemmat vastaukset" />
		</form>
		<input type="submit" class="answer-button2" value="Seuraava kysymys">		
		<div class="centerer">
			<div class="page-wide">
			<#if resubmitError != true>	<#-- Jos käyttäjä ei ole vielä vastannut kysymykseen -->
				<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
					<form action="/newQuestion" method="POST">
						<input type="hidden" name="uid" value="${answer.uid}">
						<#-- <input type="submit" class="answer-button" value="Seuraava kysymys"> -->
					</form>
				<#elseif unansweredQuestion.qid == 0>
					<h2 class="center">Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
				</#if>
			<#else>	<#-- Jos käyttäjä on jo vastannut kysymykseen (form resubmission) -->
				<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
					<h3 class="center">Olet jo vastannut tähän kysymykseen!</h3>
					<form action="/newQuestion" method="POST">
						<input type="hidden" name="uid" value="${answer.uid}">
						<#-- <input type="submit" class="answer-button" value="Seuraava kysymys"> -->
					</form>
				<#elseif unansweredQuestion.qid == 0>	<#-- Jos kysymättömiä kysymyksiä ei ole enää -->
					<h2 class="center">Olet jo vastannut tähän kysymykseen!</h2>
					<h2 class="center">Olet vastannut kaikkiin kysymyksiin. Tervetuloa uudelleen ensi viikolla!</h2>
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
				<#if oldQuestion.type == 1 || oldQuestion.type == 2>
					<h4>Vastauksemme (keskiarvo)</h4>
					<h1 class="center">${answerStats.meanAll}</h1>
					<h4>Vastauksemme (mediaani)</h4>
					<h1 class="center">${answerStats.mediAll?c}</h1>
				<#elseif oldQuestion.type == 4 || oldQuestion.type == 5>
					<h4>Vastauksemme:</h4>
					<h1 class="center">
					<#if answerStats.classModeAll == "0">	
						${answerStats.modeAll?c}
					<#else>
						<#if answerStats.classModeAll == "AO01">	<#-- CHECK IF ANSWERS WERE CLASSES OR NUMBERS -->
							${oldQuestion.colHead1}
						<#elseif answerStats.classModeAll == "AO02">
							${oldQuestion.colHead2}
						<#elseif answerStats.classModeAll == "AO03">
							${oldQuestion.colHead3}
						<#elseif answerStats.classModeAll == "AO04">
							${oldQuestion.colHead4}
						<#else>
							${oldQuestion.colHead5}								
						</#if>			
					</#if>
					</h1>
				</#if>					
			</div>
			<div class="divider"></div>
			<#if oldQuestion.type == 4 || oldQuestion.type == 5>
				<div class="center">
				<#if oldQuestion.type == 4>
					<#include "charts/pie_chart.ftl">
				<#elseif oldQuestion.type == 5>				
					<#include "charts/bar_chart.ftl">
				</#if>
				<#include "charts/map.ftl">			
				</div>
			</#if>
			<div class="center">
				<h4>Vastausten lukumäärä</h4>
				<h2>${oldQuestion.amntAnswTot}</h2>
				<h2>${answerStats.amountTot}</h2>
			</div>
		</div>	
		&nbsp;
	</body>
</html>