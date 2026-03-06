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
        <li><g:link controller="Tutorial" action="Tutorial">Tutorial</g:link></li>
        <li><g:link controller="About" action="About">About</g:link></li>
        <li><g:link controller="signin" action="signin">Sign In</g:link></li>
        <li><g:link controller="register" action="register">Register</g:link></li>
    </ul>
</nav>
<g:layoutBody/>
</body>