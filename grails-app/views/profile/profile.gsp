<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="profile.css"/>
<title>Game Setup</title>

<main class="util-main">
  <div class="menu-box">
    <h1 class="page-title">${session['username'].toUpperCase()}</h1>


    <div class="profile-row">

      <div id="profileLinks" class="profile-list">
        <ul class="stat-items">
          <li class="btn btn-large"><g:link controller="GameSetup" action="gameSetup">Game Setup</g:link></li>
          <li class="btn btn-large warning"><g:link controller="SavedGame" action="deleteAllSaves">Delete All Saved Games</g:link></li>
          <li class="btn btn-large warning"><g:link controller="Profile" action="resetStats">Reset Stats</g:link></li>
          <li class="btn btn-large warning"><g:link controller="Profile" action="resetProfile">Reset Profile</g:link></li>
        </ul>
      </div>


      <div id="profileStats" class="profile-list">
        <ul class="stat-items">
          <li>Total Games: ${session['userStats']?.totalGames}</li>
          <li>Total Score: ${session['userStats']?.totalScore}</li>
          <li>Wins: ${session['userStats']?.wins}</li>
          <li>Losses: ${session['userStats']?.losses}</li>
          <li>Grooviness: ${session['userStats']?.winPercentage}%</li>
        </ul>
      </div>

    </div>
  </div>
</main>
