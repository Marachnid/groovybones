<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  %{-- can ignore this error --}%
  <asset:stylesheet src="game.css"/>
  <title>GAME</title>
</head>
<body>
  <h2>GAME</h2>
  <h3>Dice: ${session['dice']}</h3>

  <g:link controller="gameAction" action="runOpponentBoard" params="[opponentBoard: session['opponentBoard']]">
    <button type="button">Add number to Opponent Board</button>
  </g:link>
<div class="content-container">
  <div id="game-area">
    <div class="game-container">
      <table id="opponent-board" class="game-board">
        <g:each in="${session['opponentBoard'].board}" var="column">
          <tr class="boardColumn">
            <g:each in="${0..(session['opponentBoard'].columnMaxSize-1)}" var="i">
              <td>${column[i] == null ? '-' : column[i]}</td>
            </g:each>
          </tr>
        </g:each>
      </table>

      <div class="board-info">
        <h3>${session['opponentBoard'].boardName}</h3>
        <h3>Score: ${session['opponentBoard'].calculateScore()}</h3>
      </div>
    </div>

    <div class="game-container">
      <table id="player-board" class="game-board">
        <g:each in="${session['playerBoard'].board}" var="column" status="colIndex">

%{--Enables on-click action for player turn, else --}%
          <g:if test="${session['playerTurn'] == true}">
            <tr class="boardColumn"
                onclick="window.location='${createLink(
                controller:'gameAction', action:'runPlayerBoard',
                params:[col: colIndex])}'"
                style="cursor:pointer;"
            >
          </g:if>
          <g:else>
            <tr class="boardColumn" style="cursor:pointer">
          </g:else>


            <g:each in="${0..(session['playerBoard'].columnMaxSize-1)}" var="i">
              <td>${column[i] == null ? '-' : column[i]}</td>
            </g:each>
          </tr>
        </g:each>
      </table>

      <div class="board-info">
        <h3>${session['playerBoard'].boardName}</h3>
        <h3>Score: ${session['playerBoard'].calculateScore()}</h3>
      </div>
    </div>
  </div>
</div>
  <g:link controller="home" action="index">
    <button type="button">RETURN HOME</button>
  </g:link>
</body>