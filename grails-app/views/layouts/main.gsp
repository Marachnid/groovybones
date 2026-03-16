<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>
    <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    %{-- can ignore this error --}%
    <asset:stylesheet src="main.css"/>

    <g:layoutHead/>
</head>


<body>
    <h1>GroovyBones</h1>
    <nav class="navbar">
        <ul>
            <li><g:link controller="home" action="index">Home</g:link></li>
            <li><g:link controller="Tutorial" action="tutorial">Tutorial</g:link></li>
            <li><g:link controller="About" action="about">About</g:link></li>

            <g:if test="${session['player']}">
                <li><g:link controller="Login" action="logout">sign-out(${session['player'].username})</g:link></li>
            </g:if>
        </ul>
    </nav>
    <g:layoutBody/>
</body>