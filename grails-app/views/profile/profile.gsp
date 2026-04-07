<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <asset:stylesheet src="profile.css"/>
  <title>Profile</title>
</head>
<body>

  <h2>PROFILE</h2>

  <div class="content-container">
    <div class="profile-lists">
      <ul class="menu-list profile-list">
        <li>${session['user'].username}</li>
        <li>Manage Account (eventual link)</li>
        <li>Manage Mods (eventual link)</li>
        <li>Delete Account (eventual link)</li>
      </ul>

      <ul class="menu-list profile-list">
        <li>Stats</li>
        <li>Total Score: ${session['user'].totalScore}</li>
        <li>Wins: ${session['user'].wins}</li>
        <li>Losses: ${session['user'].losses}</li>
        <li>Win Ratio: TBD</li>
      </ul>
    </div>
  </div>

</body>