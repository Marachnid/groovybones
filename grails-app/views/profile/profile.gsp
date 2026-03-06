<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>Profile</title>
</head>
<body>

  <h2>PROFILE</h2>

%{--eventually two side-by-side boxes--}%
<div>
  <h3>${user.userName}</h3>
  <p>Manage Account (eventual link)</p>
  <p>Manage Mods (eventual link)</p>
  <p>Delete Account (eventual link)</p>
</div>

<div>
  <h3>Stats</h3>
  <p>Total Score: ${user.totalScore}</p>
  <p>Wins: ${user.wins}</p>
  <p>Losses: ${user.losses}</p>
  <p>Win Percentage: (Eventual method calculation)</p>
</div>
</body>