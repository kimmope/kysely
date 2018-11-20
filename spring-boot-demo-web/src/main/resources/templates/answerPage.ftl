<#-- Värivakiot karttaan ja graafeihin -->
<#assign darkest = '#08519C'>
<#assign darkerer = '#2171B5'><#--  -->
<#assign darker = '#3182BD'>
<#assign plain = '#6BAED6'>
<#assign plainer = '#9ECAE1'><#--  -->
<#assign lighter = '#BDD7E7'>
<#assign lighterer = '#DEEBF7'><#--  -->
<#assign lightest = '#EFF3FF'>
<#assign bg = '#09D'>
<#assign lineChartBgLines = '#2BF'>
<html>
	<#include "head.ftl">
	<body>
		<form action="/pastAnswers" method="POST" id="pastAmntOAnsws">
			<input type="hidden" name="uid" value="${answer.uid}">
			<input type="image" id="menu" src="/img/hamburger_white.png" onmouseover="this.src='/img/hamburger_blue.png'" onmouseout="this.src='/img/hamburger_white.png'" border="0" alt="Aikaisemmat vastaukset" />
		</form>
		<form action="/newQuestion" method="POST" id="nextQuestionForm">
			<input type="hidden" name="uid" value="${answer.uid}">
			<input type="submit" class="next-button" value="Seuraava kysymys">
		</form>		
		<div class="centerer">
			<div class="page-wide">
			<#if resubmitError != true>	<#-- Jos käyttäjä ei ole vielä vastannut kysymykseen -->
				<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
				<#elseif unansweredQuestion.qid == 0>
					<h2 class="center">Olet vastannut kaikkiin kysymyksiin!</h2><br>
					<h2 class="center">Tervetuloa uudelleen ensi viikolla!</h2>
					<div class="divider-with-line"></div>
					<#-- Poistaa next-question-napin -->
					<script>document.getElementById("nextQuestionForm").outerHTML = "";</script>					
				</#if>
			<#else>	<#-- Jos käyttäjä on jo vastannut kysymykseen (form resubmission) -->
				<#if unansweredQuestion.qid != 0>	<#-- Jos kysymättömiä kysymyksiä on vielä -->
					<h3 class="center">Olet jo vastannut tähän kysymykseen!</h3>
				<#elseif unansweredQuestion.qid == 0>	<#-- Jos kysymättömiä kysymyksiä ei ole enää -->
					<h2 class="center">Olet jo vastannut kaikkiin kysymyksiin!</h2><br>
					<h2 class="center">Tervetuloa uudelleen ensi viikolla!</h2>
					<#-- Poistaa next-question-napin -->
					<script>document.getElementById("nextQuestionForm").outerHTML = "";</script>
				</#if>
				<div class="divider-with-line"></div>
			</#if>
			</div>
			debug:${statisticses[0].amount}<br>
			debug pid: ${statisticses[1].id}<br>
			debug contains: 
			<script>
			<#list statisticses as x>
				<#if x.id == "989">
					${x.amount}
				</#if>
			</#list>
			var arr=[<#list statisticses as statistics>"${statistics.classMode}",</#list>];
			for(var i = 1; i < arr.length; i++){
				console.log(arr[i]);
			}
			</script>
			<br>
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
				<div id="spirit">
				<#if oldQuestion.type == 1 || oldQuestion.type == 2>
					<h4 class="center"><b>YHTEISVASTAUS</b></h4>
					<br>
					<h1 class="center">${answerStats.meanAll?c}</h1>
					<div class="center">(keskiarvo)</div>
					<br>
					<h1 class="center">${answerStats.mediAll?c}</h1>
					<div class="center">(mediaani)</div>
				<#elseif oldQuestion.type == 3 || oldQuestion.type == 4>
					<h4 class="center"><b>YHTEISVASTAUS</b></h4>
					<br>
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
			</div>
			<div class="divider-with-line"></div>
			<#if oldQuestion.type == 3 || oldQuestion.type == 4>
				<#if oldQuestion.type == 3>
					<#include "charts/bar_chart.ftl">
				<#elseif oldQuestion.type == 4>				
					<#include "charts/pie_chart.ftl">
				</#if>
				<div class="divider-with-line"></div>
				<#include "charts/map.ftl">			
				<div class="divider-with-line"></div>	
			</#if>
			<div class="center">
				<h4>Vastausten lukumäärä</h4>
				<h2>${answerStats.amountTot}</h2>	
			</div>
		</div>	
		&nbsp;
	</body>
</html>