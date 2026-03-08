<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>GAME</title>
</head>
<body>

  <h2>GAME</h2>
  <h3>Player: ${session['user'].userName}</h3>
  <h3>Board: ${session['userBoard'].board}</h3>


<h3>TEST TABLE</h3>
<table>
  <g:each in="${session['userBoard'].board}" var="column">
    <tr class="boardColumn">
      <g:each in="${0..(session['userBoard'].columnMaxSize-1)}" var="i">
        <td>${column[i] == null ? '-' : column[i]}</td>
      </g:each>
    </tr>
  </g:each>
</table>



<table>
  <g:each in="${session['userBoard'].board}" var="column" status="colIndex">

    <tr class="boardColumn"
        onclick="window.location='${createLink(controller:'gameAction', action:'addNumber', params:[col: colIndex])}'"
        style="cursor:pointer;">

      <g:each in="${0..(session['userBoard'].columnMaxSize-1)}" var="i">

        <td>${column[i] == null ? '-' : column[i]}</td>

      </g:each>

    </tr>

  </g:each>
</table>


<g:link controller="gameAction" action="runBoard" params="[userBoard: session['userBoard']]">
  <button type="button">Add a number to the board</button>
</g:link>


<g:link controller="home" action="index">
  <button type="button">RETURN HOME</button>
</g:link>

</body>