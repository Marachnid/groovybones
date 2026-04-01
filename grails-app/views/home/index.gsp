<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>

<main class="util-main">
    <div class="menu-box">

        <g:if test="${session['player']}">
            <ul class="menu-buttons">
                <li><g:link controller="GameSetup" action="gameSetup">Play</g:link></li>
                <li><g:link controller="Profile" action="profile">Profile</g:link></li>
                <li><g:link controller="GameOver" action="gameOverAction">GameOver Test</g:link></li>
            </ul>
        </g:if>

        <g:else>
            <ul class="menu-buttons">
                <li><g:link controller="Login" action="login">Sign In</g:link></li>
                <li><g:link controller="Register" action="register">Register</g:link></li>
            </ul>
        </g:else>

    </div>
</main>
