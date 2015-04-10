<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h2>Hello World!</h2>
<a href="testmethod">testMethodPage</a>
<a href="testpoi">testPoi</a>

<form:form action="testmethod">
    <input type="text" name="testInput" value="aaa"/>
    <input type="text" name="param1" value="bbb"/>
    <input type="text" name="param2" value="ccc"/>
    <input type="submit">Submit</input>
</form:form>

<form method="post" action="form" enctype="multipart/form-data">
    <input type="text" name="name"/>
    <input type="file" name="file"/>
    <input type="submit"/>
</form>

</body>
</html>
