<html>
	<#include "head.ftl">	
	<body class="historic">
		<form action="/pastAnswers" method="POST" id="pastAmntOAnsws">
			<input type="hidden" name="uid" value="${pastQandA.uid}">
			<input type="image" id="menu" src="/img/hamburger_history_white.png" onmouseover="this.src='/img/hamburger_history_grey.png'" onmouseout="this.src='/img/hamburger_history_white.png'" border="0" alt="Aikaisemmat vastaukset" />
		</form>	
	<#if unansweredQuestion.qid != 0>
		<form action="/newQuestion" method="POST" id="nextQuestionForm">
			<input type="hidden" name="uid" value="${pastQandA.uid}">
			<input type="submit" class="next-button" value="Seuraava kysymys">
		</form>
	</#if>		
		<div class="centerer">
			<div class="page-wide">	
				<h2>Aikaisempi vastaus</h2>
				<h4>${pastQandA.timeOfAnswer?date} vastasit kysymykseen:</h4>
				<h3>${pastQandA.question}</h3>
				<h4>Vastauksesi oli:</h4>
				<h2>
				<#if pastQandA.answer == "AO01">	<#-- Change answer value to text-format for viewing -->
					${oldQuestion.colHead1}
				<#elseif pastQandA.answer == "AO02">
					${oldQuestion.colHead2}
				<#elseif pastQandA.answer == "AO03">
					${oldQuestion.colHead3}
				<#elseif pastQandA.answer == "AO04">
					${oldQuestion.colHead4}
				<#elseif pastQandA.answer == "AO05">
					${oldQuestion.colHead5}
				<#else>
					${pastQandA.answer}								
				</#if>
				</h2>
				<div id="spirit">
				<#if oldQuestion.type == 1 || oldQuestion.type == 2>
					<h4 class="center"><b>YHTEISARVAUS</b></h4><br>
					<h1 class="center">${answerStats.meanAll?c}</h1><div class="center">(keskiarvo)</div>
					<br>
					<h1 class="center">${answerStats.mediAll?c}</h1><div class="center">(mediaani)</div>
				<#elseif oldQuestion.type == 4 || oldQuestion.type == 5>
					<h4 class="center"><b>YHTEISARVAUS</b></h4><br>
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
			<div class="divider"></div>
			<div class="center">
				<h4>Vastausten lukumäärä</h4>
				<h2>${answerStats.amountTot}</h2>
			</div>
		</div>
	</body>
</html>