<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Game Over</title>
</head>
<body>

  <h2>GAME OVER</h2>

  <h3>Player: ${session['player'].userName}</h3>
  <ul>
    <li>Score: ${session['playerBoard'].calculateScore()}</li>
  </ul>


  <h3>Opponent: ${session['opponent'].userName}</h3>
  <ul>
    <li>Score: ${session['opponentBoard'].calculateScore()}</li>
  </ul>


<g:link controller="home" action="index">
  <button type="button">RETURN HOME</button>
</g:link>

</body>