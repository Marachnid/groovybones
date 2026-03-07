<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
  <meta name="layout" content="main"/>
  <title>GAME</title>
</head>
<body>

  <h2>GAME</h2>
  <h3>Player: ${user.userName}</h3>
  <h3>Board: ${board.board}</h3>


<h3>TEST TABLE</h3>
<table>
  <g:each in="${board.board}" var="column">
    <tr class="boardColumn">
      <g:each in="${0..(board.columnMaxSize-1)}" var="i">
        <td>${column[i] == null ? '-' : column[i]}</td>
      </g:each>
    </tr>
  </g:each>
</table>

<g:link controller="home" action="index">
  <button type="button">RETURN HOME</button>
</g:link>

</body>