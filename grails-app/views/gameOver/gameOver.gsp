<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="game.css"/>

<main class="util-main">
  <div class="menu-box">
    <h2 class="page-title">Game Over</h2>

    <!-- character card row -->
    <div class="character-row">

      <!-- player card -->

      <g:if test="${session['playerWon']}">
        <div class="character-card player-card winner-card">
      </g:if>
      <g:else>
        <div class="character-card player-card loser-card">
      </g:else>
        <div class="character-portrait"></div>
        <div class="character-bottom">
          <div class="character-label">${session['player'].username}</div>
          <div class="character-score">${session['playerScore']}</div>
        </div>
      </div>

    <!-- stats -->
    <div id="stats" class="character-card placeholder-card">

      <div class="stats-content">
        <div class="stats-row">
          <span class="stats-label">Winner:</span>
          <span class="stats-value">${(session['playerWon']) ? session['player'].username : session['opponent'].username}</span>
        </div>

      </div>

    </div>

      <!-- opponent card -->
    <g:if test="${session['playerWon']}">
      <div class="character-card opponent-card loser-card">
    </g:if>
    <g:else>
      <div class="character-card player-card winner-card">
    </g:else>
        <div class="character-portrait"></div>
        <div class="character-bottom">
          <div class="character-label">${session['opponent'].username}</div>
          <div class="character-score">${session['opponentScore']}</div>
        </div>
      </div>
    </div>

    <!-- play again -->
    <div class="play-button-container">
      <g:link controller="GameSetup" action="gameSetup" class="menu-action">Play Again</g:link>
    </div>

    <!-- profile -->
    <div class="play-button-container">
      <g:link controller="Profile" action="profile" class="menu-action">Profile</g:link>
    </div>

  </div>
</main>
