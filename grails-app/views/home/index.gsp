<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    %{-- can ignore this error --}%
    <asset:stylesheet src="index.css"/>
    <title>Home</title>
</head>
<body>
    <div class="content-container">
        <g:if test="${session['player']}">
            <ul class="home-list">
                <li><g:link controller="game" action="game">Play</g:link></li>
                <li><g:link controller="profile" action="profile">Profile</g:link></li>
            </ul>
        </g:if>
        <g:else>
            <ul class="home-list">
                <li><g:link controller="SignIn" action="signIn">Sign In</g:link></li>
                <li><g:link controller="Register" action="register">Register</g:link></li>
            </ul>
        </g:else>
    </div>
</body>