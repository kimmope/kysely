<html>
<#include "head.ftl">	
<body>
<div class="centerer">
	<div id="question">${uQ.question}</div>
	<div id="tool-row">
		<form action="/answer" method="POST">
			<input type="hidden" name="uid" value="${uid}">
			<input type="hidden" name="qid" value="${uQ.qid}">
			<div id="input-tools"></div> <#-- Tool(s) will be put in this div with JavaScript -->
			<#if uQ.type == 1>
				<#include "input_tools/1_number.ftl">
			<#elseif uQ.type == 2>
				<#include "input_tools/2_range.ftl">
			<#elseif uQ.type == 3>
				<#include "input_tools/3_radio.ftl">
			<#elseif uQ.type == 4>
				<#include "input_tools/4_options.ftl">										
			</#if>
		</form>
	</div>
</div>
</body>
</html>