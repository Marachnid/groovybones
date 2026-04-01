<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  %{--  <meta name="layout" content="main"/>--}%
  %{-- can ignore these errors --}%
  <asset:stylesheet src="main.css"/>
  <title>Game Over</title>
</head>
<body>

  <h2>GAME OVER</h2>

%{--  <h3>Player: ${session['player'].username.toUpperCase()}</h3>--}%
%{--  <ul>--}%
%{--    <li>Score: ${session['playerBoard'].calculateScore()}</li>--}%
%{--  </ul>--}%


%{--  <h3>Opponent: ${session['opponent'].username}</h3>--}%
%{--  <ul>--}%
%{--    <li>Score: ${session['opponentBoard'].calculateScore()}</li>--}%
%{--  </ul>--}%

%{--TODO need to add logic for determining winner in GameOverController--}%

<g:link controller="home" action="index">
  <button type="button">RETURN HOME</button>
</g:link>

</body>