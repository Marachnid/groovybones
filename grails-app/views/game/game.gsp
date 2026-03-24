<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <asset:stylesheet src="main.css"/>
  <asset:stylesheet src="game.css"/>
  <title>Game</title>
</head>

<body>
  <div class="content-container">
    <h2>${session['player'].username.toUpperCase()} VS ${session['opponent'].username}</h2>

  <!--delay opponent from executing turn immediately-->
    <g:if test="${!session['playerTurn']}">
      <script>
        setTimeout(() => {
          window.location.href = "${createLink(controller:'gameAction', action:'runOpponentBoard')}";
        }, ${session['timeout']});
      </script>
    </g:if>

    <!--the overall game area containing player boards and info as 2 rows 3 wide-->
    <div id="game-area">

      <!--opponent row - opponent board and lefthand/righthand containers-->
      <div class="game-container opponent-row">

        <!--left-hand info-card for score display-->
        <div class="info-left">
          <div class="info-card">
            <h3>Score</h3>
            <h3>${session['player'].username}: ${session['playerBoard'].calculateScore()}</h3>
            <h3>${session['opponent'].username}: ${session['opponentBoard'].calculateScore()}</h3>
          </div>
        </div>

        <!--center table for opponent board-->
        <table id="opponent-board" class="game-board">
          <g:each in="${session['opponentBoard'].board}" var="column">
            <tr class="opponent-column">
              <g:each in="${0..(session['opponentBoard'].columnMaxSize-1)}" var="i">
                <td>${column[i] == null ? ' ' : column[i]}</td>
              </g:each>
            </tr>
          </g:each>
        </table>

        <!--right-hand opponent-card-->
        <div class="opponent-container flex-col border-dark">
          <div class="opponent-card flex-center">
            IMG
          </div>
            <h3>${session['opponent'].username}</h3>
        </div>
      </div>

      <!--player row - player board and lefthand/righthand containers-->
      <div class="game-container player-row">

      <!--WIP - will be adapting opponent-card into a player card-->
      <div class="opponent-container flex-col border-dark">
        <div class="opponent-card flex-center">
          IMG
        </div>
        <h3>${session['player'].username}</h3>
      </div>

        <!--center table for player board-->
        <table id="player-board" class="game-board">
          <g:each in="${session['playerBoard'].board}" var="column" status="colIndex">

            <!--clickable actions are disabled if not player turn-->
            <g:if test="${session['playerTurn'] == true}">
              <tr class="player-column"
                  onclick="window.location='${createLink(
                    controller:'gameAction',
                    action:'runPlayerBoard',
                    params:[col: colIndex])}'">
            </g:if><g:else>
              <tr>
            </g:else>

            <g:each in="${0..(session['playerBoard'].columnMaxSize-1)}" var="i">
              <td>${column[i] == null ? ' ' : column[i]}</td>
            </g:each>
            </tr>
          </g:each>
        </table>

        <!--right-hand info-card for turn# and random dice value-->
        <div class="info-right">
          <div class="info-card">
            <h3>Dice: ${session['dice']}</h3>
            <h3>Turn: ${session['turn']}</h3>

            <!--indicate opponent is 'thinking' (waiting via delay scriptlet above)-->
            <div class="thinking">
              ${session['playerTurn'] ? '' : 'Opponent thinking'}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!--TODO eventually need to style this in somewhere -->
  <!--TODO eventually need to add a save-game button -->
  <button><g:link controller="home" action="index">RETURN HOME</g:link></button>
</body>
</html>