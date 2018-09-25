<html>
	<head>
		<link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" />
		<title>Answered questions</title>
	</head>
	<body>
		<div class="container">
			<h1>Old questions and answers</h1>
			<div id="username">${user.username}</div>
			<div id="userScore">${user.score}</div>
			<div id="amntAnsws">${user.amount_answers}</div>
			<h2>View past questions and answers:</h2>
			<table id="oldQuestionsTable">
				<thead><tr><th>Past answered questions</th><th>Time of answer</th></tr></thead>
				<tbody>
					<#list pastQandAs as pastQandA>
					<tr>
						<td id="pastQuestion"><a href="/pastQuestion/${pastQandA.qid}">${pastQandA.question}</a></td>
						<td id="answerDate">${pastQandA.time_of_answ}</td>
					</tr>
					</#list>
				</tbody>
			</table>
			<#-- Linkki seuraavaan kysymykseen jos sellainen on olemassa, muuten ei mitään--> 
		</div>
	</body>
</html>