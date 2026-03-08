<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Game Over</title>
</head>
<body>

  <h2>GAME OVER</h2>
  <h3>Player: ${session['user'].userName}</h3>
  <h3>Board: ${session['userBoard'].board}</h3>

  <ul>
    <li>${session['userBoard'].calculateScore()}</li>
  </ul>


<g:link controller="home" action="index">
  <button type="button">RETURN HOME</button>
</g:link>

</body>