<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>

<main class="util-main">
    <h1 hidden>Home</h1>

    <div class="menu-box">
        <g:if test="${session['player']}">
            <ul class="menu-buttons menu-list">
                <li><g:link class="btn btn-large" controller="GameSetup" action="gameSetup">Play</g:link></li>
                <li><g:link class="btn btn-large" controller="Profile" action="profile">Profile</g:link></li>
                <li><g:link class="btn btn-large" controller="GameOver" action="gameOverAction">GameOver Test</g:link></li>
            </ul>
        </g:if>

        <g:else>
            <ul class="menu-buttons menu-list">
                <li><g:link class="btn btn-large" controller="Login" action="login">Sign In</g:link></li>
                <li><g:link class="btn btn-large" controller="Register" action="register">Register</g:link></li>
            </ul>
        </g:else>

    </div>
</main>
