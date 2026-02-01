
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Display Page</title>
</head>

<body>
    <h1>Display Page</h1>
    <ul>
        <li>
            <g:link controller="home" action="index">HomeController</g:link>
        </li>
    </ul>

    <h2>Query Results</h2>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Username</th>
        </tr>
        </thead>
        <tbody>
            <g:each in="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.firstName}</td>
                    <td>${user.userName}</td>
                </tr>
            </g:each>
        </tbody>
    </table>
    <ul>
    </ul>
</body>
</html>