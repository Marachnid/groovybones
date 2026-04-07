<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="game.css"/>
<title>Game</title>

<!--delay opponent from executing turn immediately-->
<g:if test="${!session['userTurn']}">
  <script>
    setTimeout(() => {
      window.location.href = "${createLink(controller:'gameAction', action:'runOpponentBoard')}";
    }, ${session['timeout']});
  </script>
</g:if>

<main class="util-main">
  <h1 hidden>Game</h1>
  <div class="menu-box">
    <div id="game-area">

      <!-- panel bar -->
      <div class="panel-toggle-bar">

        <div class="panel-col left">
          <ul>
            <li><g:link controller="home" action="index">Home</g:link></li>
            <li class="panel-toggle" data-panel="tutorial">Tutorial</li>
          </ul>

        </div>

        <div class="panel-col center">
          <div id="panel-symbol">✵</div>
        </div>

        <div class="panel-col right">
          <ul>
            <li class="panel-toggle" data-panel="stats">Stats</li>
            <li><g:link controller="SavedGame" action="saveExit">Save & Exit</g:link></li>
          </ul>
        </div>
      </div>


      <!-- OPPONENT row-->
      <div class="game-row opponent-row">

        <!-- LEFT panel col -->
        <div class="game-col left">

          <!-- tutorial panel -->
          <div class="info-panel panel-tutorial">
            <ul>
              <li>Click your columns to add a number</li>
              <li>Each number adds to your score</li>
              <li>Matching column numbers multiply</li>
            </ul>
          </div>

          <!-- stats panel -->
          <div class="info-panel panel-stats">
            <ul>
              <li>${session['opponentUsername']}</li>
              <li>Total Score: ${session['opponentStats'].totalScore}</li>
              <li>Wins: ${session['opponentStats'].wins}</li>
              <li>Losses: ${session['opponentStats'].losses}</li>
            </ul>
          </div>

        </div>


        <!-- CENTER opponent board col  -->
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

        <!-- RIGHT opponent card col -->
        <div class="game-col right">
          <g:if test="${session['userTurn']}">
            <div class="character-card">
              <div class="character-portrait">IMG</div>
              <div class="character-label">${session['opponentUsername']}</div>
            </div>
          </g:if>
          <g:else>
            <div class="character-card highlightTurn">
              <div class="character-portrait">IMG</div>
              <div class="character-label">${session['opponentUsername']}</div>
            </div>
          </g:else>
        </div>
      </div>


      <!-- SCORE row -->
      <div class="score-row">

        <!-- player score -->
        <div class="score-left">
          <span class="glyph">⟴</span>
          <span class="card-value">${session['userBoard'].calculateScore()}</span>
        </div>

        <!-- dice number -->
        <div class="score-center">
          <span class="card-value">${session['dice']}</span>
        </div>

        <!-- opponent score -->
        <div class="score-right">
          <span class="card-value">${session['opponentBoard'].calculateScore()}</span>
          <span class="glyph">⟴</span>
        </div>

      </div>


        <!-- PLAYER row -->
        <div class="game-row player-row">

          <!-- LEFT player card -->
          <div class="game-col left">
            <g:if test="${session['userTurn']}">
              <div class="character-card highlightTurn">
                <div class="character-portrait">IMG</div>
                <div class="character-label">${session['username']}</div>
              </div>
            </g:if>
            <g:else>
              <div class="character-card">
                <div class="character-portrait">IMG</div>
                <div class="character-label">${session['username']}</div>
              </div>
            </g:else>
          </div>

          <!-- CENTER player board -->
          <div class="game-col center">
            <table id="player-board" class="game-board">
              <g:each in="${session['userBoard'].board}" var="column" status="colIndex">

                <g:if test="${session['userTurn'] == true}">
                  <tr class="player-column"
                      onclick="window.location='${createLink(
                        controller:'gameAction',
                        action:'runPlayerBoard',
                        params:[col: colIndex])}'">

                    <g:each in="${0..(session['userBoard'].columnMaxSize-1)}" var="i">
                      <td>${column[i] == null ? ' ' : column[i]}</td>
                    </g:each>
                  </tr>
                </g:if>
                <g:else>
                  <tr>
                    <g:each in="${0..(session['userBoard'].columnMaxSize-1)}" var="i">
                      <td>${column[i] == null ? ' ' : column[i]}</td>
                    </g:each>
                  </tr>
                </g:else>
              </g:each>
            </table>
          </div>


          <!-- RIGHT panel col -->
          <div class="game-col right">
            <!-- tutorial panel -->
            <div class="info-panel panel-tutorial">
              <ul>
                <li>Numbers fall off if matches are placed in opposing columns</li>
                <li>The game ends once a board is full</li>
                <li>The highest score wins!</li>
              </ul>
            </div>

            <!-- stats panel -->
            <div class="info-panel panel-stats">
              <ul>
                <li>${session['username']}</li>
                <li>Total Score: ${session['userStats'].totalScore}</li>
                <li>Wins: ${session['userStats'].wins}</li>
                <li>Losses: ${session['userStats'].losses}</li>
              </ul>
            </div>

          </div>
        </div>

    </div>
  </div>

      <script>
        document.addEventListener("DOMContentLoaded", () => {
          const toggles = document.querySelectorAll(".panel-toggle");
          const tutorialPanels = document.querySelectorAll(".panel-tutorial");
          const statsPanels = document.querySelectorAll(".panel-stats");
          const panelBar = document.querySelector(".panel-toggle-bar");
          const panelSymbol = document.getElementById("panel-symbol");

          let active = null;

          panelSymbol.addEventListener("click", () => {
            panelBar.classList.toggle("collapsed");
          });

          toggles.forEach(btn => {
            btn.addEventListener("click", () => {
              const panel = btn.dataset.panel;

              if (active === panel) {
                active = null;
                toggles.forEach(b => b.classList.remove("active"));
                tutorialPanels.forEach(p => p.classList.remove("active"));
                statsPanels.forEach(p => p.classList.remove("active"));
                return;
              }

              active = panel;

              toggles.forEach(b => b.classList.remove("active"));
              btn.classList.add("active");

              tutorialPanels.forEach(p => p.classList.toggle("active", panel === "tutorial"));
              statsPanels.forEach(p => p.classList.toggle("active", panel === "stats"));
            });
          });
        });
      </script>

</main>
