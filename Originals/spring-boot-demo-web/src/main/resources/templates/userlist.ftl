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
					<tr><td><a href="/user/${user.username}">${user.username}</a></td>
					<td>${user.firstname}</td>
					<td>${user.lastname}</td>
					<td><a href="/modifyf/${user.username}">modify</a></td></tr>
				</#list>
			</tbody>
		</table>
		<a href="/add">Add new</a>
	</body>
</html>