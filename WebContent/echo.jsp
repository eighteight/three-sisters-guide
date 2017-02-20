<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:a4j="https://ajax4jsf.dev.java.net/ajax">
	<html>
	<head>
	<title>repeater</title>
	</head>
	<body>
	<f:view>
		<h:form>
			<h:inputText size="50" value="#{bean.text}">
				<a4j:support event="onkeyup" reRender="rep" />
			</h:inputText>
			<h:outputText value="#{bean.text}" id="rep" />
		</h:form>
	</f:view>
	</body>
	</html>
</jsp:root>
