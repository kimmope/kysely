<html>
	<#include "head.ftl">	
	<body class="historic">
	DEBUG: user.uid=${user.uid} <script>console.log(typeof ${user.uid});</script>
	<br>
	DEBUG: uid=${uid} <script>console.log(typeof ${uid});</script>
	<br>
		<div class="container">
			<h1>Old questions and answers</h1>
			<span id="nameField">Username: <span id="username">${user.username}</span></span>
			<span id="scoreField">Score: <span id="userScore">${user.score}</span></span>
			<br>
			<span id="amntOfAnswsField">Amount of answered questions: <span id="amntAnsws">${user.amntUserAnsw}</span></span>
			<h2>View past questions and answers:</h2>
			<table id="oldQuestionsTable">
				<thead><tr><th>Past answered questions</th><th>Time of answer</th></tr></thead>
				<tbody>
					<#list pastQandAs as pastQandA>
					<tr>
						<td>DEBUG: pastQandA.uid=${pastQandA.uid} <script>console.log(typeof ${pastQandA.uid});</script></td>
						<td id="pastQuestion">
							<form action="/oldAnswer" method="POST">
								<input type="hidden" name="uid" value="${pastQandA.uid}">
								<input type="hidden" name="qid" value="${pastQandA.qid}">
								<input type="submit" id="questionButton" value="${pastQandA.question}">
							</form>						
						</td>
						<td id="answerDate">${pastQandA.timeOfAnswer}</td>
					</tr>
					</#list>
				</tbody>
			</table>
			<#if unansweredQuestion.qid != 0>
			<form action="/newQuestion" method="POST">
				<input type="hidden" name="uid" value="${uid}">
				<input type="submit" value="Seuraava kysymys">
			</form>
			</#if>
		</div>
	</body>
</html>