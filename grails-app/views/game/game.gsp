<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  %{-- can ignore these errors --}%
  <asset:stylesheet src="main.css"/>
  <asset:stylesheet src="game.css"/>
  <title>Game</title>
</head>
<body>

  %{--unique to game.gsp--}%
  <div class="dropdown-navbar">
    <input type="checkbox" id="menuToggle" class="menu-toggle">
    <label for="menuToggle" class="navbar-header">
      GroovyBones <span class="arrow"></span>
    </label>

    <nav class="navbar-menu">
      <ul>
        <li><g:link controller="home" action="index">Home</g:link></li>
        <li><g:link controller="Tutorial" action="tutorial">Tutorial</g:link></li>
        <li><g:link controller="About" action="about">About</g:link></li>

        <g:if test="${session['player']}">
          <li><g:link controller="Login" action="logout">
            sign-out(${session['player'].username})
          </g:link></li>
        </g:if>
      </ul>
    </nav>
  </div>

  <h2>${session['player'].username.toUpperCase()} VS ${session['opponent'].username}</h2>


  %{-- IMPORTANT adds delay to the opponent placing a dice value, see GameController GameInitialization() for timeout --}%
  <g:if test="${!session['playerTurn']}">
    <script>
      setTimeout(() => {
        window.location.href = "${createLink(controller:'gameAction', action:'runOpponentBoard')}";
      }, ${session['timeout']});
    </script>
  </g:if>


  <div class="content-container">
    <div id="game-area">

      %{--opponent container--}%
      <div class="game-container">

        %{--opponent board--}%
        <table id="opponent-board" class="game-board">
          <g:each in="${session['opponentBoard'].board}" var="column">
            <tr class="opponent-column">
              <g:each in="${0..(session['opponentBoard'].columnMaxSize-1)}" var="i">
                <td>${column[i] == null ? ' ' : column[i]}</td>
              </g:each>
            </tr>
          </g:each>
        </table>

        %{--opponent info--}%
        <div class="board-info">
          <h3>${session['opponent'].username}</h3>
          <h3>Score: ${session['opponentBoard'].calculateScore()}</h3>
          <h3>${session['playerTurn'] ? '' : "Dice: ${session['dice']}"}</h3>
          <div class="thinking">${session['playerTurn'] ? '' : 'Opponent is plotting your demise'}</div>
        </div>
      </div>

      %{--player container--}%
      <div class="game-container">

        %{--player board--}%
        <table id="player-board" class="game-board">
          <g:each in="${session['playerBoard'].board}" var="column" status="colIndex">

            %{--creates opening <tr> with onclick action only if it's the player's turn--}%
            <g:if test="${session['playerTurn'] == true}">
              <tr class="player-column"
                  onclick="window.location='${createLink(
                  controller:'gameAction', action:'runPlayerBoard',
                  params:[col: colIndex])}'">
            </g:if>

            %{--generic <tr> rendered if not player's turn--}%
            <g:else><tr></g:else>

              <g:each in="${0..(session['playerBoard'].columnMaxSize-1)}" var="i">
                <td>${column[i] == null ? ' ' : column[i]}</td>
              </g:each>
            </tr>
          </g:each>
        </table>

        %{--player info--}%
        <div class="board-info">
          <h3>${session['player'].username.toUpperCase()}</h3>
          <h3>Score: ${session['playerBoard'].calculateScore()}</h3>
          <h3>${session['playerTurn'] ? "Dice: ${session['dice']}" : ''}</h3>
        </div>
      </div>
    </div>
  </div>
</body>