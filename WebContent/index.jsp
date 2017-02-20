<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head>
</head>
<body>

<!--  

This page allows the user to go to the context-path and get redirected
to the front page of the app.  For example,
http://localhost:8080/jsf-carstore/.  Note that we use "*.jsf" as the
page mapping.  Doing so allows us to just name our pages as "*.jsp",
refer to them as "*.jsf" and know that they will be properly picked up
by the container.

-->

<jsp:forward page="/front.jsf"/>
</body>
</html>
