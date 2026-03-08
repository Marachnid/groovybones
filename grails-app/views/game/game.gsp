<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>GAME</title>
</head>
<body>

  <h2>GAME</h2>
  <h3>Player 1: ${session['player'].userName}</h3>
  <h3>Board: ${session['playerBoard'].board}</h3>
  <h3>Score: ${session['playerBoard'].calculateScore()}</h3>

  <h3>Player 2: ${session['opponent'].userName}</h3>
  <h3>Board: ${session['opponentBoard'].board}</h3>
  <h3>Score: ${session['opponentBoard'].calculateScore()}</h3>



<h3>OPPONENT TABLE</h3>
<table>
  <g:each in="${session['opponentBoard'].board}" var="column">
    <tr class="boardColumn">
      <g:each in="${0..(session['opponentBoard'].columnMaxSize-1)}" var="i">
        <td>${column[i] == null ? '-' : column[i]}</td>
      </g:each>
    </tr>
  </g:each>
</table>

<g:link controller="gameAction" action="runOpponentBoard" params="[opponentBoard: session['opponentBoard']]">
  <button type="button">Add number to Opponent Board</button>
</g:link>



<h3>PLAYER TABLE</h3>
<table>
  <g:each in="${session['playerBoard'].board}" var="column" status="colIndex">

    <tr class="boardColumn"
        onclick="window.location='${createLink(
        controller:'gameAction', action:'runPlayerBoard',
        params:[col: colIndex], playerBoard: session['playerBoard'])}'"
        style="cursor:pointer;">

      <g:each in="${0..(session['playerBoard'].columnMaxSize-1)}" var="i">

        <td>${column[i] == null ? '-' : column[i]}</td>

      </g:each>

    </tr>

  </g:each>
</table>


<g:link controller="home" action="index">
  <button type="button">RETURN HOME</button>
</g:link>

</body>