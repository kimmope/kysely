<html>
	<#setting locale="fi_FI">
	<#include "head.ftl">	
	<body class="historic">
	<#if unansweredQuestion.qid != 0>
		<form action="/newQuestion" method="POST" id="nextQuestionForm">
			<input type="hidden" name="uid" value="${uid}">
			<input type="submit" class="next-button" value="Seuraava kysymys">
		</form>
	</#if>	
		<div class="centerer">
			<h2>Perustiedot</h2>
			<h4>Käyttäjätunnus: </h4><h3>${user.username}</h3>
			<h4>Pistemäärä: </h4><h3>${user.score}</h3>
			<h4>Vastattujen kysymysten lukumäärä: </h4><h3>${user.amntUserAnsw}</h3>
			<div class="divider-with-line"></div>
			<h2>Aikaisemmat kysymykset ja vastaukset</h2>
			<table class="historyTable">
				<thead>
					<tr><th class="q-no-title">№</th><th>Vastattu kysymys</th><th width="100">Päivämäärä</th></tr>
				</thead>
				<tbody>
					<#list pastQandAs as pastQandA>
					<tr>
						<td class="q-no">${pastQandA.qid}</td>
						<td>
							<form action="/oldAnswer" method="POST">
								<input type="hidden" name="uid" value="${pastQandA.uid}">
								<input type="hidden" name="qid" value="${pastQandA.qid}">
								<input type="submit" class="history-q-btn" value="${pastQandA.question}">
							</form>						
						</td>
						<td align=right>${pastQandA.timeOfAnswer?date}</td>
					</tr>
					</#list>
				</tbody>
			</table>
		</div>
	</body>
</html>