<html>
	<head>
		<title>User list</title>
	</head>
	<body>
		<table>
			<thead>
				<tr><th>Username</th><th>Firstname</th><th>Lastname</th></tr>
			</thead>
			<tbody>
				<#list users as user>
					<tr><td>${user.username}</td><td>${user.firstname}</td><td>${user.lastname}</td></tr>
				</#list>
			</tbody>
		</table>
	</body>
</html>