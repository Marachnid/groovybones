<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="game.css"/>


<!--delay opponent from executing turn immediately-->
<g:if test="${!session['playerTurn']}">
  <script>
    setTimeout(() => {
      window.location.href = "${createLink(controller:'gameAction', action:'runOpponentBoard')}";
    }, ${session['timeout']});
  </script>
</g:if>

<main class="util-main">
  <h1 hidden>Game</h1>
  <div class="menu-box">

    <!--the overall game area containing player boards and info as 3x3 grid-->
    <div id="game-area">

      <!--opponent row - opponent board and lefthand/righthand containers-->
      <div class="game-row opponent-row">

        <div id="placeholder" class="game-col left">
          <div class="info-card">
            <h3>TEMP</h3>
          </div>
        </div>

        <!--center table for opponent board-->
        <div class="game-col center">
          <table id="opponent-board" class="game-board">
            <g:each in="${session['opponentBoard'].board}" var="column">
              <tr class="opponent-column">
                <g:each in="${0..(session['opponentBoard'].columnMaxSize-1)}" var="i">
                  <td>${column[i] == null ? ' ' : column[i]}</td>
                </g:each>
              </tr>
            </g:each>
          </table>
        </div>

        <!--right-hand opponent-card-->
        <div class="game-col right">
        <g:if test="${session['playerTurn']}">
          <div class="character-card">
        </g:if>
        <g:else>
          <div class="character-card highlightTurn">
        </g:else>
            <div class="character-portrait">IMG</div>
            <div class="character-label">${session['opponent'].username}</div>
          </div>
        </div>

      </div>

    <!--middle row - player score, turn, opponent score-->
    <div class="game-row score-row">

      <!--player score-->
      <div class="game-col left">
        <div class="info-card">
          <h3>${session['player'].username}:</h3>
          <h3>${session['playerBoard'].calculateScore()}</h3>
        </div>
      </div>

      <!--turn number-->
      <div class="game-col center">
        <div class="info-card">
          <h3>Turn: </h3>
          <h3>${session['turn']}</h3>
        </div>
      </div>

      <!--opponent score-->
      <div class="game-col right">
        <div class="info-card">
          <h3>${session['opponent'].username}:</h3>
          <h3>${session['opponentBoard'].calculateScore()}</h3>
        </div>
      </div>

    </div>


    <!--player row - player board and lefthand/righthand containers-->
      <div class="game-row player-row">

        <div class="game-col left">
          <g:if test="${session['playerTurn']}">
            <div class="character-card highlightTurn">
          </g:if>
          <g:else>
            <div class="character-card">
          </g:else>
            <div class="character-portrait">IMG</div>
            <div class="character-label">${session['player'].username}</div>
          </div>
        </div>

        <!--center table for player board-->
        <div class="game-col center">
          <table id="player-board" class="game-board">
            <g:each in="${session['playerBoard'].board}" var="column" status="colIndex">

              <!--clickable actions are disabled if not player turn-->
              <g:if test="${session['playerTurn'] == true}">
                <tr class="player-column"
                    onclick="window.location='${createLink(
                      controller:'gameAction',
                      action:'runPlayerBoard',
                      params:[col: colIndex])}'">
              </g:if>
              <g:else>
                <tr>
              </g:else>

              <g:each in="${0..(session['playerBoard'].columnMaxSize-1)}" var="i">
                <td>${column[i] == null ? ' ' : column[i]}</td>
              </g:each>
              </tr>

            </g:each>
          </table>
        </div>

        <!--right-hand card for random dice value-->
        <div class="character-card dice-card">
          <div class="character-portrait dice-display">
            <div class="dice-value">${session['dice']}</div>
          </div>
          <div class="character-label dice-label">Dice</div>
        </div>

        </div>
      </div>
    </div>

  </div>
  </div>
</main>