<html>
	<#include "head.ftl">	
	<body class="historic">
		<div class="container">	
		<form action="/pastAnswers" method="POST" id="pastAmntOAnsws">
			<input type="hidden" name="uid" value="${pastQandA.uid}">
			<input type="submit" value="Aikaisemmat vastaukset">
		</form>	
		On ${pastQandA.timeOfAnswer} you answered question:
		<span class="historic" id="pastQuestion">${pastQandA.question}</span>
		<br>
		Your answer was:<span id="pastAnswer">${pastQandA.answer1}</span>
		<br>
		Statistics of answers:
		<span class="historic" id="pastAmntOAnsws"><AMOUNT OF ANSWERS></span>
		Average answer was: <span class="historicData"><AVERAGE></span>
		<br>
		<div class="chart">Here some nice charts</div>
		Factual answer was:<span class="historicData"><THE REAL ANSWER WAS THIS*></span>
		<p>* According to this and that source</p>
		<#if unansweredQuestion.qid != 0>
		<form action="/newQuestion" method="POST">
			<input type="hidden" name="uid" value="${pastQandA.uid}">
			<input type="submit" value="Seuraava kysymys">
		</form>
		</#if>	
		</div>	
	</body>
</html>