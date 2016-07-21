<%--

     Licensed to the Apache Software Foundation (ASF) under one or more
     contributor license agreements.  See the NOTICE file distributed with
     this work for additional information regarding copyright ownership.
     The ASF licenses this file to You under the Apache License, Version 2.0
     (the "License"); you may not use this file except in compliance with
     the License.  You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

--%>
<%@ taglib prefix="wicket" uri="http://wicketstuff-jee-web.org/functions/jsp" %>
<div id="jsp">
	<% String s = "This is a String computed within a JSP"; %>
	<%=s%>
</div>

<div>
	<a href="<wicket:url page="org.wicketstuff.jeeweb.examples.TestPage2" query="param1=value1&param2=value2"/>">Link rendered by tag</a>
</div>

<form action="<wicket:url page="org.wicketstuff.jeeweb.examples.TestPage2"/>" method="POST">
	<input type="hidden" name="hiddenparam" value="hiddenvalue">
	<input type="submit" value="Submit">
</form>

<div>
	<a href="${wicket:url('org.wicketstuff.jeeweb.examples.TestPage2')}">This is a link rendered by EL</a>
</div>


<script type="text/javascript">
	function processCallBack(){
		var urlWithPrerenderedArgs = 
			'${wicket:ajaxCallbackUrlWithQuery("param=value")}';
			
		setTimeout(function(){			
		var url = '${wicket:ajaxCallbackUrl()}';
	    	console.log(url);
	    	var url = Wicket.Ajax.applyGetParameters(url,{"param":"This is the changed text."});
			Wicket.Ajax.wrapget(url);
		},3000);
	}
	processCallBack();
</script>

<a href="#" onClick="${wicket:ajaxGetWithQuery('param=This is the updated Text')}">Link to Update the label</a>
