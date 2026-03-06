<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>
    <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:stylesheet src="main.css"/>

    <g:layoutHead/>
</head>


<body>
<h1>GroovyBones</h1>
<nav class="navbar">
    <ul>
        <li><g:link controller="home" action="index">Home</g:link></li>
        <li><g:link controller="profile" action="profile">Profile</g:link></li>
        <li><g:link controller="Tutorial" action="tutorial">Tutorial</g:link></li>
        <li><g:link controller="About" action="about">About</g:link></li>
        <li><g:link controller="SignIn" action="signin">Sign In</g:link></li>
        <li><g:link controller="Register" action="register">Register</g:link></li>
    </ul>
</nav>
<g:layoutBody/>
</body>