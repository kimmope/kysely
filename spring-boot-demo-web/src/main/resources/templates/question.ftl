<html>
	<head>
		<link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" />	
		<title>Question</title>
	</head>
	<body>
		<span id="question">${question.question}</span>
		<form action="/answerForm" method="POST">
			<input type="hidden" name="uid" value="7">
			<input type="hidden" name="qid" value="1">	
			<input type="text" name="answer">
			<br>
			<input type="submit" value="Vastaa">
		</form>
		<br><br><br><span id="qid">Debug question id: ${question.qid}</span>
	</body>
</html>