<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Sign-In</title>
</head>
<body>

  <h2>Sign In</h2>
  <g:form controller="signIn" action="doSignIn" method="post">
    <label for="username">Enter your username:</label>
    <input type="text" name="username" id="username" required />

    <button type="submit">Sign In</button>
  </g:form>


</body>