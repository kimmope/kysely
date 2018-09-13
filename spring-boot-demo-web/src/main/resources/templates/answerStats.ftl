<html>
	<head>
		<link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" />
		<title>Answer statistics</title>
	</head>
	<body>
		<div class="container">
			Question was:
			<h1>${question.question}</h1>
			Your answer was:
			<br>
			${answer.answer}
			<br>
			Average answer was:
			<br>
			${question.average}
			<br>
			<a href="/question">Next question</a>
			<br>
			<form>
				<input type="hidden" name="uid" value="${answer.uid}>
				<input type="submit" value="Next question">
			</form>
		</div>
	</body>
</html>