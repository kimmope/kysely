<html>
<#include "head.ftl">	
<body>
<div class="centerer">	
	<form action="/firstQuestionForm" method="POST">
		<div class="textLeft">
			<h4 class="left">Käyttäjätunnus</h4>
			<input type="text" name="username" class="text-input">
		</div>
		<div class="textLeft">
			<h4>Synnyinlääni</h4>
			<div class="custom-select">
			  <select>
			    <option value="0">Valitse</option>
			    <option value="1">Länsi-Suomen lääni</option>
			    <option value="2">Itä-Suomen lääni</option>
			    <option value="3">Lapin lääni</option>
			    <option value="4">Oulun lääni</option>
			    <option value="5">Etelä-Suomen lääni</option>
			  </select>
			</div>
		</div>			
		<div class="toolRow">
			<input type="submit" class="answer-button" value="Kirjaudu sisään">
		</div>
	</form>
</div>
<script src="/js/javascript.js"></script>
</body>
</html>