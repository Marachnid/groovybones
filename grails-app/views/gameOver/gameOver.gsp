<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="gameOver.css"/>
<title>Game Over</title>

<main class="util-main">
  <div class="menu-box">
    <h1 class="page-title">Game Over</h1>


    <!-- character card row -->
    <div class="character-grid">

      <!-- player card -->
      <g:if test="${session['userWon']}">
        <div class="character-card player-card winner-card">
      </g:if>
      <g:else>
        <div class="character-card player-card loser-card">
      </g:else>
        <div class="character-portrait"></div>
        <div class="character-bottom">
          <div class="character-label">${session['user'].username}</div>
          <div class="card-value">${session['userScore']}</div>
        </div>
      </div>

      <!-- stats card -->
      <div class="character-card stats-card">
          <div class="stats-column">
            <span class="stats-label">Winner</span>
            <span class="card-value">${(session['userWon']) ? session['user'].username : session['opponentName']}</span>
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
          <div class="character-label">${session['opponentName']}</div>
          <div class="card-value">${session['opponentScore']}</div>
        </div>
      </div>
    </div>

    <!-- menu buttons -->
    <ul class="menu-buttons">
      <li><g:link class="btn" controller="GameSetup" action="gameSetup">Play Again</g:link></li>
      <li><g:link class="btn" controller="Profile" action="profile">Profile</g:link></li>
      <li><g:link class="btn" controller="home" action="index">Home</g:link></li>
    </ul>
  </div>
</main>
