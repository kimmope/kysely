<html>
	<#include "head.ftl">	
	<body class="historic">
		<div class="container">
			<h1>Old questions and answers</h1>
			<span id="nameField">Username: <span id="username">${user.username}</span></span>
			<span id="scoreField">Score: <span id="userScore">${user.score}</span></span>
			<br>
			<span id="amntOfAnswsField">Amount of answered questions: <span id="amntAnsws">${user.amount_answers}</span></span>
			<h2>View past questions and answers:</h2>
			<table id="oldQuestionsTable">
				<thead><tr><th>Past answered questions</th><th>Time of answer</th></tr></thead>
				<tbody>
					<#list pastQandAs as pastQandA>
					<tr>
						<td id="pastQuestion"><a href="/oldAnswer/${pastQandA.uid}/${pastQandA.qid}">${pastQandA.question}</a></td>
						<td id="answerDate">${pastQandA.time_of_answ}</td>
					</tr>
					</#list>
				</tbody>
			</table>
			<#-- Linkki seuraavaan kysymykseen jos sellainen on olemassa, muuten ei mitään--> 
		</div>
	</body>
</html>