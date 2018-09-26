<html>
	<#include "head.ftl">	
	<body class="historic">
		On ${pastQandA.time_of_answ} you answered question:
		<span class="historic" id="pastQuestion">${pastQandA.question}</span>
		<br>
		Your answer was:<span id="pastAnswer">${pastQandA.answer}</span>
		<br>
		Statistics of answers:
		<span class="historic" id="pastAmntOAnsws"><AMOUNT OF ANSWERS></span>
		Average answer was: <span class="historicData"><AVERAGE></span>
		<br>
		<div class="chart">Here some nice charts</div>
		Factual answer was:<span class="historicData"><THE REAL ANSWER WAS THIS*></span>
		<p>* According to this and that source</p>
	</body>
</html>