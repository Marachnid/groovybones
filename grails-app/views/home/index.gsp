
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Home Page</title>
</head>

<body>
  <h1>Home Page</h1>

  <ul>
    <li>
      <g:link controller="message" action="message">MessageController</g:link>
    </li>
    <li>
      <g:link controller="display" action="display">DisplayController</g:link>
    </li>
  </ul>
</body>
</html>