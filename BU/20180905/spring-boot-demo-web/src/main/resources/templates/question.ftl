<html>
	<head>
		<title>Question</title>
		<link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" />
	</head>
	<body>
		<div id="userName">Käyttäjän nimi</div>
		<div id="userScore">Käyttäjän pistetilanne</div>
		<br>
		<span id="question">Onko tämä kysymys?</span>
		<br>
		<form action="/answerForm" method="POST">
			<input type="hidden" name="uid" value="1">
			<input type="hidden" name="qid" value="1">	
			<input type="text" name="answer">
			<br>
			<input type="submit" value="Vastaa">
		</form>		
	</body>
</html>