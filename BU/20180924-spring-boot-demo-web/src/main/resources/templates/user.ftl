<html>
	<head>
		<link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" />
		<title>User information</title>
	</head>
	<body>
		<div class="container">
			<h1>User stats</h1>
			<table>
				<thead>
					<tr><th>Username</th><th>Firstname</th><th>Lastname</th></tr>
				</thead>
				<tbody>
					<tr>
						<td>${user.username}</td>
						<td>${user.firstname}</td>
						<td>${user.lastname}</td>
					</tr>
				</tbody>
			</table>
			<a href="/userspage">Back</a>
			<p> This is some text</p>
		</div>
	</body>
</html>